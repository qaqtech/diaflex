package ft.com.report;

import ft.com.DBMgr;
import ft.com.DBUtil;
import ft.com.InfoMgr;

import ft.com.dao.ByrDao;

import java.sql.ResultSet;

import java.util.ArrayList;
import java.util.HashMap;
import ft.com.generic.GenericImpl;
import ft.com.generic.GenericInterface;

import java.sql.Connection;

import java.sql.PreparedStatement;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

public class DailyBranchDlvAction extends DispatchAction {

    public DailyBranchDlvAction() {
      super();
  }

    public ActionForward load(ActionMapping am, ActionForm form,
                              HttpServletRequest req,
                              HttpServletResponse res) throws Exception {
       HttpSession session = req.getSession(false);
       InfoMgr info = (InfoMgr)session.getAttribute("info");
       DBUtil util = new DBUtil();
       DBMgr db = new DBMgr();
       String rtnPg="select a.nme_idn,initcap(trim(a.nme)) nme from nme_m a where typ = 'buyer' \n" + 
       "and a.co_idn=202 and a.stt=1 \n" + 
       "order by initcap(trim(a.nme)) limit 50";
       if(info!=null){
       Connection conn=info.getCon();
       if(conn!=null){
       db.setCon(info.getCon());
       util.setDb(db);
       util.setInfo(info);
       db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
       util.setLogApplNm(info.getLoApplNm());
       rtnPg=init(req,res,session,util);
       }else{
       rtnPg="connExists";   
       }
       }else
       rtnPg="sessionTO";
        if(!rtnPg.equals("sucess")){
            return am.findForward(rtnPg);   
        }else{
       util.getOpenCursorConnection(db,util,info);
       util.updAccessLog(req,res,"Branch Delivery Report", "load start");
        DailyBranchDlvForm dailyDlvForm = (DailyBranchDlvForm)form;
            
     ArrayList brnchList = new ArrayList();
     String sqlBranchNme="select distinct a.nme_idn , a.nme from nme_v a , brc_mdlv b,brc_dlv_dtl c " + 
     " where a.nme_idn = b.inv_nme_idn  and b.idn = c.idn and c.stt='DLV'";

       ArrayList outLst = db.execSqlLst("sqlQuery", sqlBranchNme, new ArrayList());
       PreparedStatement pst = (PreparedStatement)outLst.get(0);
       ResultSet rs = (ResultSet)outLst.get(1);
     while(rs.next()){
       ByrDao byr = new ByrDao();
       byr.setByrIdn(rs.getInt("nme_idn"));
       byr.setByrNme(rs.getString("nme"));
       brnchList.add(byr);
     }
       rs.close(); pst.close();
        dailyDlvForm.setBranchList(brnchList);
     String dtefrom = " trunc(sysdate) ";
     String dteto = " trunc(sysdate) ";
       String dfr = util.nvl((String)dailyDlvForm.getValue("dtefr"));
       String dto = util.nvl((String)dailyDlvForm.getValue("dteto"));
     String spersonId = util.nvl((String)dailyDlvForm.getValue("branchIdn"));
     String buyerId = util.nvl(req.getParameter("nmeID"));
      String sqlStr = "";
     if(!dfr.equals(""))
       dtefrom = "to_date('"+dfr+"' , 'dd-mm-yyyy')";
     
     if(!dto.equals(""))
       dteto = "to_date('"+dto+"' , 'dd-mm-yyyy')";
        
     if(!spersonId.equals("") && !spersonId.equals("0")){
       sqlStr = " and a.inv_nme_idn ='"+spersonId+"' ";
      }
     
     if(!buyerId.equals("") && !buyerId.equals("0")){
       sqlStr = sqlStr +" and ms.nme_idn= '"+buyerId+"' ";
     
     }   
     HashMap delivery = new HashMap();
        String dlvSql1="   select e.nme_idn delivery_id , e.nme delivery_name, d.nme byr , d.nme_idn byrid, 'DLV' typ , to_char(b.dte_dlv,'dd-mm-yyyy') dte, sum (b.qty) qty ,to_char(sum(trunc(b.cts,2)),'999,990.99') cts ," +
        "     to_char(sum(trunc(nvl(b.fnl_sal,b.quot)*b.cts,2)),'999,9999,999,990.00') vlu\n" + 
        "        from brc_mdlv a , brc_dlv_dtl b ,msal ms, mstk c , nme_v d , nme_v e\n" + 
        "        where a.idn = b.idn and b.sal_idn=ms.idn and b.mstk_idn = c.idn and ms.nme_idn = d.nme_idn   and b.stt='DLV' and a.inv_nme_idn = e.nme_idn and nvl(c.prt2,'NA')=decode(nvl(c.prt2,'NA'),'SPECIAL',nvl(PACK_VAR.Get_Special,'NA'),nvl(c.prt2,'NA'))\n" + 
        "        and  trunc(b.dte_dlv) between "+dtefrom+" and "+dteto+sqlStr +        
        "        group by  e.nme_idn, e.nme, d.nme, d.nme_idn, 'DLV', to_char(b.dte_dlv,'dd-mm-yyyy') order by e.nme, d.nme ";

       outLst = db.execSqlLst("loadqry", dlvSql1, new ArrayList());
       pst = (PreparedStatement)outLst.get(0);
       rs = (ResultSet)outLst.get(1);
     int i = 0;
     while (rs.next()) {
         HashMap deliverydtl = new HashMap();
         i++;
         deliverydtl.put("delivery_id", util.nvl(rs.getString("delivery_id")));
         deliverydtl.put("delivery_name", util.nvl(rs.getString("delivery_name")));
         deliverydtl.put("byr", util.nvl(rs.getString("byr")));
         deliverydtl.put("dte", util.nvl(rs.getString("dte")));
         deliverydtl.put("qty", util.nvl(rs.getString("qty")));
         deliverydtl.put("cts", util.nvl(rs.getString("cts")));
         deliverydtl.put("typ", util.nvl(rs.getString("typ")));
         deliverydtl.put("byrid", util.nvl(rs.getString("byrid")));
         deliverydtl.put("vlu", util.nvl(rs.getString("vlu")));

         delivery.put(i, deliverydtl);
     }
     rs.close(); pst.close();
     req.setAttribute("deliveryTbl", delivery);
            
     HashMap totaldelivery = new HashMap();
     String totalQry = " select  a.inv_nme_idn brancIdn , e.nme branc_name, sum (b.qty) qty ,to_char(sum(trunc(b.cts,2)),'999,990.99') cts ,to_char(sum(trunc(nvl(b.fnl_sal,b.quot)*b.cts,2)),'999,9999,999,990.00') vlu\n" + 
     "        from brc_mdlv a , brc_dlv_dtl b,msal ms, mstk c ,  nme_v e\n" + 
     "        where a.idn = b.idn and b.sal_idn=ms.idn and b.mstk_idn = c.idn and nvl(c.prt2,'NA')=decode(nvl(c.prt2,'NA'),'SPECIAL',nvl(PACK_VAR.Get_Special,'NA'),nvl(c.prt2,'NA'))  and b.stt='DLV' and a.inv_nme_idn = e.nme_idn\n" + 
     "        and  trunc(b.dte_dlv) between "+dtefrom+" and "+dteto+sqlStr +        
     "        group by a.inv_nme_idn, e.nme order by e.nme ";

       outLst = db.execSqlLst("totalqry", totalQry, new ArrayList());
       pst = (PreparedStatement)outLst.get(0);
       rs = (ResultSet)outLst.get(1);
     while (rs.next()) {
         HashMap totalDtl = new HashMap();
         totalDtl.put("delivery_id", util.nvl(rs.getString("brancIdn")));
         totalDtl.put("qty", util.nvl(rs.getString("qty")));
         totalDtl.put("cts", util.nvl(rs.getString("cts")));
         totalDtl.put("vlu", util.nvl(rs.getString("vlu")));
         totaldelivery.put(util.nvl(rs.getString("brancIdn")), totalDtl);
     }
     rs.close(); pst.close();
     req.setAttribute("totaldelivery", totaldelivery);
            
     HashMap grandDtl = new HashMap();
     String grandQry =" select  sum (b.qty) qty ,to_char(sum(trunc(b.cts,2)),'999,990.99') cts ,to_char(sum(trunc(nvl(b.fnl_sal,b.quot)*b.cts,2)),'999,9999,999,990.00') vlu\n" + 
     "        from brc_mdlv a , brc_dlv_dtl b ,msal ms, mstk c ,  nme_v e\n" + 
     "        where a.idn = b.idn and b.sal_idn=ms.idn and b.mstk_idn = c.idn   and b.stt='DLV' and nvl(c.prt2,'NA')=decode(nvl(c.prt2,'NA'),'SPECIAL',nvl(PACK_VAR.Get_Special,'NA'),nvl(c.prt2,'NA')) and a.inv_nme_idn = e.nme_idn "+
     "        and  trunc(b.dte_dlv) between "+dtefrom+" and "+dteto+sqlStr ;       

       outLst = db.execSqlLst("grand total", grandQry, new ArrayList());
       pst = (PreparedStatement)outLst.get(0);
       rs = (ResultSet)outLst.get(1);
     while (rs.next()) {
               
         grandDtl.put("qty", util.nvl(rs.getString("qty")));
         grandDtl.put("cts", util.nvl(rs.getString("cts")));
         grandDtl.put("vlu", util.nvl(rs.getString("vlu")));
      
     }
       rs.close(); pst.close();
     req.setAttribute("grandtotal", grandDtl);
     HashMap allPageDtl= (info.getPageDetails() == null)?new HashMap():(HashMap)info.getPageDetails();
     HashMap pageDtl=(HashMap)allPageDtl.get("DAILY_DLV_RPT");
     if(pageDtl==null || pageDtl.size()==0){
     pageDtl=new HashMap();
     pageDtl=util.pagedef("DAILY_DLV_RPT");
     allPageDtl.put("DAILY_DLV_RPT",pageDtl);
     }
     info.setPageDetails(allPageDtl);
      int accessidn=util.updAccessLog(req,res,"Branch Delivery Report", "load end");
      req.setAttribute("accessidn", String.valueOf(accessidn));;
     return am.findForward("load");
   }
   }  
    
  public ActionForward pktDtl(ActionMapping am, ActionForm form,
                            HttpServletRequest req,
                            HttpServletResponse res) throws Exception {
      HttpSession session = req.getSession(false);
      InfoMgr info = (InfoMgr)session.getAttribute("info");
      DBUtil util = new DBUtil();
      DBMgr db = new DBMgr();
      String rtnPg="sucess";
      if(info!=null){
      Connection conn=info.getCon();
      if(conn!=null){
      db.setCon(info.getCon());
      util.setDb(db);
      util.setInfo(info);
      db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
      util.setLogApplNm(info.getLoApplNm());
      rtnPg=init(req,res,session,util);
      }else{
      rtnPg="connExists";   
      }
      }else
      rtnPg="sessionTO";
      if(!rtnPg.equals("sucess")){
          return am.findForward(rtnPg);   
      }else{
          util.updAccessLog(req,res,"Branch Delivery Report", "pktDtl start");
      String salId = util.nvl(req.getParameter("salId"));
      ArrayList pktDtlList = new ArrayList();
      ArrayList itemHdr = new ArrayList();
      int sr=1;
      itemHdr.add("SRNO");
      itemHdr.add("DLVID");
              itemHdr.add("VNM");
              itemHdr.add("BRANCHNAME");
              itemHdr.add("BYR");
              itemHdr.add("COUNTRY");
      ArrayList ary = new ArrayList();
      ArrayList prpDspBlocked = info.getPageblockList();
      if(!salId.equals("")){
          String pktDtlSql =" select b.dlv_idn , c.idn, \n" + 
          "                        to_char(b.cts,'99999999990.99') cts,C.Vnm,Nvl(C.Upr,C.Cmp) rte,C.Rap_Rte,e.nme  branchNme,d.nme Byr,nvl(b.fnl_sal, b.quot) memoQuot, \n" + 
          "                         to_char(trunc(b.cts,2) * Nvl(b.fnl_sal, b.quot), '9999999990.00') amt,byr.get_country(nvl(a.nme_idn,0)) country,to_char((((nvl(b.fnl_sal, b.quot))*b.cts/b.cts)/(c.rap_Rte*b.cts/b.cts)*100) - 100,'999999990.00') r_dis \n" + 
          "                        from brc_mdlv a , brc_dlv_dtl b ,msal ms, mstk c , nme_v d , nme_v e\n" + 
          "                        Where A.Idn = B.Idn and b.sal_idn=ms.idn And B.Mstk_Idn=C.Idn and d.nme_idn=ms.nme_idn and nvl(c.prt2,'NA')=decode(nvl(c.prt2,'NA'),'SPECIAL',nvl(PACK_VAR.Get_Special,'NA'),nvl(c.prt2,'NA')) and a.inv_nme_idn= e.nme_idn\n" + 
          "                        And B.sal_idn=? And B.Stt ='DLV' \n" + 
          "                        order by c.sk1";
      ary.add(salId);

          ArrayList outLst = db.execSqlLst("pktDtl", pktDtlSql,ary);
          PreparedStatement pst = (PreparedStatement)outLst.get(0);
          ResultSet rs = (ResultSet)outLst.get(1);
      while(rs.next()){
          HashMap pktDtl = new HashMap();
          pktDtl.put("SRNO", String.valueOf(sr++));
          pktDtl.put("DLVID", util.nvl(rs.getString("dlv_idn")));
          pktDtl.put("VNM", util.nvl(rs.getString("vnm")));
          pktDtl.put("BRANCHNAME", util.nvl(rs.getString("branchNme")));
          pktDtl.put("BYR", util.nvl(rs.getString("byr")));
          pktDtl.put("COUNTRY", util.nvl(rs.getString("country")));
          pktDtl.put("RTE", util.nvl(rs.getString("rte")));
          pktDtl.put("RAP_RTE", util.nvl(rs.getString("rap_rte")));
          pktDtl.put("SALE RTE", util.nvl(rs.getString("memoQuot")));
          pktDtl.put("AMOUNT", util.nvl(rs.getString("amt")));
          String pktIdn = util.nvl(rs.getString("idn"));
          String getPktPrp =
              " select a.mprp, decode(b.dta_typ, 'C', a.prt1, 'N', a.num, 'D', a.dte, a.txt) val "
              + " from stk_dtl a, mprp b, rep_prp c "
              + " where a.mprp = b.prp and b.prp = c.prp and c.mdl = 'DAILY_VW' and a.mstk_idn = ? and a.grp=1 and  c.flg <> 'N' "
              + " order by c.rnk, c.srt ";

          ary = new ArrayList();
          ary.add(pktIdn);

          ArrayList outLst1 = db.execSqlLst(" Pkt Prp", getPktPrp, ary);
          PreparedStatement pst1 = (PreparedStatement)outLst1.get(0);
          ResultSet rs1 = (ResultSet)outLst1.get(1);
           while (rs1.next()) {
              String lPrp = util.nvl(rs1.getString("mprp"));
              String lVal = util.nvl(rs1.getString("val"));
              pktDtl.put(lPrp, lVal);
              if(prpDspBlocked.contains(lPrp)){
              }else if(!itemHdr.contains(lPrp)){
              itemHdr.add(lPrp);
              if(lPrp.equals("RTE")){
              itemHdr.add("SALE RTE");
              itemHdr.add("AMOUNT");
              }
              }
          }
          rs1.close(); pst1.close();
          pktDtl.put("RTE",util.nvl(rs.getString("rte")));
          pktDtl.put("RAP_DIS",util.nvl(rs.getString("r_dis")));
          pktDtl.put("CRTWT",util.nvl(rs.getString("cts")));
          pktDtl.put("RAP_RTE",util.nvl(rs.getString("rap_rte")));
          pktDtlList.add(pktDtl);
      }
          rs.close(); pst.close();
      }
     session.setAttribute("pktList", pktDtlList);
     session.setAttribute("itemHdr",itemHdr);
          util.updAccessLog(req,res,"Branch Delivery Report", "pktDtl end");
      return am.findForward("pktDtl");
      }
  }
      
  public ActionForward pktDtlExcel(ActionMapping am, ActionForm form,
                            HttpServletRequest req,
                            HttpServletResponse res) throws Exception {
      HttpSession session = req.getSession(false);
      InfoMgr info = (InfoMgr)session.getAttribute("info");
      DBUtil util = new DBUtil();
      DBMgr db = new DBMgr();
      String rtnPg="sucess";
      if(info!=null){
      Connection conn=info.getCon();
      if(conn!=null){
      db.setCon(info.getCon());
      util.setDb(db);
      util.setInfo(info);
      db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
      util.setLogApplNm(info.getLoApplNm());
      rtnPg=init(req,res,session,util);
      }else{
      rtnPg="connExists";   
      }
      }else
      rtnPg="sessionTO";
      if(!rtnPg.equals("sucess")){
          return am.findForward(rtnPg);   
      }else{
          util.updAccessLog(req,res,"Branch Delivery Report", "pktDtlExcel start");
      GenericInterface genericInt = new GenericImpl();
      ArrayList vwprpLst = genericInt.genericPrprVw(req,res,"DAILY_VW","DAILY_VW");
      DailyBranchDlvForm dailyDlvForm = (DailyBranchDlvForm)form;
      String nmeidn = util.nvl(req.getParameter("nmeidn"));
      String dte = util.nvl(req.getParameter("dte"));
      ArrayList pktDtlList = new ArrayList();
      ArrayList itemHdr = new ArrayList();
      ArrayList ary = new ArrayList();
      ArrayList prpDspBlocked = info.getPageblockList();
      int sr=1;
      itemHdr.add("SRNO");
      itemHdr.add("DLVID");
      itemHdr.add("VNM");
      itemHdr.add("SALEPERSON");
      itemHdr.add("BYR");
      itemHdr.add("COUNTRY");
          for(int i=0;i<vwprpLst.size();i++){
              String prp=util.nvl((String)vwprpLst.get(i));
              if(prpDspBlocked.contains(prp)){
              }else if(!itemHdr.contains(prp)){
              itemHdr.add(prp);
              if(prp.equals("RTE")){
                  itemHdr.add("SALE RTE");
                  itemHdr.add("AMOUNT");
               }
              }              
          }
      nmeidn=nmeidn.replaceFirst(",", "");
      dte=dte.replaceFirst(",", "");
      String[] nmeidnLst=nmeidn.split(",");
      String[] dteLst=dte.split(",");
      for(int i=0;i<nmeidnLst.length;i++){
      nmeidn=nmeidnLst[i];  
      dte=dteLst[i];  
      String dtefrom = " trunc(sysdate) ";
      String dteto = " trunc(sysdate) ";
      if(!nmeidn.equals("") && !dte.equals("")){
            dtefrom = " to_date('"+dte+"' , 'dd-mm-yyyy') ";
            dteto = " to_date('"+dte+"' , 'dd-mm-yyyy') ";
       String pktDtlSql =
           " select b.dlv_idn , c.idn, " + 
           "  to_char(b.cts,'99999999990.99') cts,C.Vnm,Nvl(C.Upr,C.Cmp) rte,C.Rap_Rte,e.nme  branchNme,d.nme Byr,nvl(b.fnl_sal, b.quot) memoQuot," + 
           "  to_char(trunc(b.cts,2) * Nvl(b.fnl_sal, b.quot), '9999999990.00') amt,byr.get_country(nvl(a.nme_idn,0)) country,to_char((((nvl(b.fnl_sal, b.quot))*b.cts/b.cts)/(c.rap_Rte*b.cts/b.cts)*100) - 100,'999999990.00') r_dis " + 
           "  from brc_mdlv a , brc_dlv_dtl b ,msal ms, mstk c , nme_v d , nme_v e " + 
           "   Where A.Idn = B.Idn and b.sal_idn=ms.idn And B.Mstk_Idn=C.Idn and d.nme_idn=ms.nme_idn and a.inv_nme_idn= e.nme_idn and nvl(c.prt2,'NA')=decode(nvl(c.prt2,'NA'),'SPECIAL',nvl(PACK_VAR.Get_Special,'NA'),nvl(c.prt2,'NA')) " + 
          "  And Trunc(b.dte_dlv) Between "+dtefrom+" And "+dteto+"\n" + 
           "  And d.nme_idn=? And B.Stt ='DLV'  " + 
           "  order by c.sk1 ";
      ary = new ArrayList();
      ary.add(nmeidn);

          ArrayList outLst = db.execSqlLst("pktDtl", pktDtlSql,ary);
          PreparedStatement pst = (PreparedStatement)outLst.get(0);
          ResultSet rs = (ResultSet)outLst.get(1);
      while(rs.next()){
          HashMap pktDtl = new HashMap();
          pktDtl.put("SRNO", String.valueOf(sr++));
          pktDtl.put("DLVID", util.nvl(rs.getString("dlv_idn")));
          pktDtl.put("VNM", util.nvl(rs.getString("vnm")));
          pktDtl.put("SALEPERSON", util.nvl(rs.getString("branchNme")));
          pktDtl.put("BYR", util.nvl(rs.getString("byr")));
          pktDtl.put("COUNTRY", util.nvl(rs.getString("country")));
          pktDtl.put("RTE", util.nvl(rs.getString("rte")));
          pktDtl.put("RAP_RTE", util.nvl(rs.getString("rap_rte")));
          pktDtl.put("SALE RTE", util.nvl(rs.getString("memoQuot")));
          pktDtl.put("AMOUNT", util.nvl(rs.getString("amt")));
          String pktIdn = util.nvl(rs.getString("idn"));
          String getPktPrp =
              " select a.mprp, decode(b.dta_typ, 'C', a.prt1, 'N', a.num, 'D', a.dte, a.txt) val "
              + " from stk_dtl a, mprp b, rep_prp c "
              + " where a.mprp = b.prp and b.prp = c.prp and c.mdl = 'DAILY_VW' and a.mstk_idn = ? and a.grp=1 and c.flg <> 'N' "
              + " order by c.rnk, c.srt ";

          ary = new ArrayList();
          ary.add(pktIdn);

          ArrayList outLst1 = db.execSqlLst(" Pkt Prp", getPktPrp, ary);
          PreparedStatement pst1 = (PreparedStatement)outLst1.get(0);
          ResultSet rs1 = (ResultSet)outLst1.get(1);
           while (rs1.next()) {
              String lPrp = util.nvl(rs1.getString("mprp"));
              String lVal = util.nvl(rs1.getString("val"));
              pktDtl.put(lPrp, lVal);
          }
          rs1.close(); pst1.close();
          pktDtl.put("RTE",util.nvl(rs.getString("rte")));
          pktDtl.put("RAP_DIS",util.nvl(rs.getString("r_dis")));
          pktDtl.put("CRTWT",util.nvl(rs.getString("cts")));
          pktDtl.put("RAP_RTE",util.nvl(rs.getString("rap_rte")));
          pktDtlList.add(pktDtl);

      }
          rs.close(); pst.close();
          
      }
      }
      session.setAttribute("pktList", pktDtlList);
      session.setAttribute("itemHdr",itemHdr);
          util.updAccessLog(req,res,"Branch Delivery Report", "pktDtlExcel end");
      return am.findForward("pktDtl");  
      }
  }  
    public String init(HttpServletRequest req , HttpServletResponse res,HttpSession session,DBUtil util) {
            String rtnPg="sucess";
            String invalide="";
            String connExists=util.nvl(util.getConnExists());  
            if(!connExists.equals("N"))
            invalide=util.nvl(util.chkTimeOut(),"N");
            if(session.isNew())
            rtnPg="sessionTO";    
            if(connExists.equals("N"))
            rtnPg="connExists";     
            if(invalide.equals("Y"))
            rtnPg="chktimeout";
            if(rtnPg.equals("sucess")){
            boolean sout=util.getLoginsession(req,res,session.getId());
            if(!sout){
            rtnPg="sessionTO";
            System.out.print("New Session Id :="+session.getId());
            }else{
                rtnPg=util.checkUserPageRights("report/dailyBranchDlv.do?","");
                if(rtnPg.equals("unauthorized"))
                util.updAccessLog(req,res,"Branch Delivery Report", "Unauthorized Access");
                else
                util.updAccessLog(req,res,"Branch Delivery Report", "init");
            }
            }
            return rtnPg;
            }
   
}
