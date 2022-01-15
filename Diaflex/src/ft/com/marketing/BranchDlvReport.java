package ft.com.marketing;

import ft.com.DBMgr;
import ft.com.DBUtil;
import ft.com.ExcelUtil;
import ft.com.InfoMgr;

import ft.com.dao.ByrDao;

import ft.com.generic.GenericImpl;
import ft.com.generic.GenericInterface;

import java.io.OutputStream;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.sql.SQLException;

import java.util.ArrayList;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

public class BranchDlvReport extends DispatchAction {
    public BranchDlvReport() {
        super();
    }
    
  public ActionForward load(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res)
          throws Exception {
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
          util.updAccessLog(req,res,"Branch Pending", "load");
          BranchDlvReportForm    udf;
          udf = (BranchDlvReportForm)af;
          udf.resetAll();
          String dfUsrtyp=util.nvl((String)info.getDfUsrTyp());
          String dfNmeIdn=util.nvl((String)info.getDfNmeIdn()); 
          String isMix = util.nvl(req.getParameter("ISMIX"));
          String conQ="";
          if(isMix.equals("Y"))
              conQ = " and d.pkt_ty <> 'NR'";
          else
             conQ = " and d.pkt_ty = 'NR'";
          ArrayList ary = new ArrayList();
          if(dfUsrtyp.equals("EMPLOYEE") && !dfNmeIdn.equals("") && !dfNmeIdn.equals("0")){
          conQ += " and b.emp_idn= ? ";  
          ary.add(dfNmeIdn);
          }
        ArrayList byrList = new ArrayList();
          String sqlQury="select distinct a.nme_idn, a.nme from nme_v a, msal b, brc_dlv_dtl c,mstk d\n" + 
          "where a.nme_idn = b.nme_idn  and b.idn = c.sal_idn and c.stt not in ('DLV','AV','RT','IS','CL') and c.mstk_idn=d.idn "+conQ;
        ArrayList  outLst = db.execSqlLst("sqlQuery", sqlQury,ary);
        PreparedStatement pst = (PreparedStatement)outLst.get(0);
        ResultSet rs = (ResultSet)outLst.get(1);
          while(rs.next()){
            ByrDao byr = new ByrDao();
            byr.setByrIdn(rs.getInt("nme_idn"));
            byr.setByrNme(rs.getString("nme"));
            byrList.add(byr);
          }
          rs.close();
          pst.close();
          udf.setByrList(byrList);
        ArrayList brnchList = new ArrayList();
        String sqlBranchNme="select distinct a.nme_idn , a.nme from nme_v a , brc_mdlv b,brc_dlv_dtl c,mstk d " + 
        " where a.nme_idn = b.inv_nme_idn  and b.idn = c.idn and c.mstk_idn=d.idn and c.stt not in ('DLV','AV','RT','IS','CL')"+conQ;
         outLst = db.execSqlLst("sqlQuery", sqlBranchNme, ary);
         pst = (PreparedStatement)outLst.get(0);
         rs = (ResultSet)outLst.get(1);
        while(rs.next()){
          ByrDao byr = new ByrDao();
          byr.setByrIdn(rs.getInt("nme_idn"));
          byr.setByrNme(rs.getString("nme"));
          brnchList.add(byr);
        }
          rs.close();
          pst.close();
          udf.setBranchList(brnchList);
          udf.setValue("isMix", isMix);
          fetchData(req,res,udf);
      }
       util.updAccessLog(req,res,"Branch Pending", "End");
       util.setMdl("Branch Delivery");
       finalizeObject(db, util);
     return am.findForward("load");
   }
  
  public ActionForward fetch(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res)
          throws Exception {
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
          BranchDlvReportForm    udf;
          udf = (BranchDlvReportForm)af;
          util.updAccessLog(req,res,"Branch Pending", "fetch");
        fetchData(req,res,udf);
      }
      util.updAccessLog(req,res,"Branch Pending", "End");
      util.setMdl("Branch Delivery");
      finalizeObject(db, util);
    return am.findForward("load");
  }
  
    public ActionForward createXL(ActionMapping am,ActionForm form,HttpServletRequest req,HttpServletResponse res) throws Exception {
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
            util.updAccessLog(req,res,"Stock Tally", "Stock Tally Create Excel");
        ExcelUtil xlUtil = new ExcelUtil();
        xlUtil.init(db, util, info);
        OutputStream out = res.getOutputStream();
        String CONTENT_TYPE = "getServletContext()/vnd-excel";
        String fileNm = "BranchPending"+util.getToDteTime()+".xls";
        res.setContentType(CONTENT_TYPE);
        res.setHeader("Content-Disposition", "attachment;filename="+fileNm);
        ArrayList pktList = (ArrayList)session.getAttribute("pktList");
        ArrayList itemHdr = (ArrayList)session.getAttribute("itemHdr");
        HSSFWorkbook hwb = xlUtil.getGenXl(itemHdr, pktList);
        hwb.write(out);
        out.flush();
        out.close();
        return null;
        }
    }
  public void fetchData(HttpServletRequest req, HttpServletResponse res,BranchDlvReportForm    udf) {
      HttpSession session = req.getSession(false);
      InfoMgr info = (InfoMgr)session.getAttribute("info");
      DBUtil util = new DBUtil();
      DBMgr db = new DBMgr();
      Connection conn=info.getCon();
      db.setCon(info.getCon());
      util.setDb(db);
      util.setInfo(info);
      db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
      util.setLogApplNm(info.getLoApplNm()); 
      int ct = db.execUpd("delete gt", "delete from gt_srch_multi", new ArrayList());
    String branchIdn = util.nvl((String)udf.getValue("branchIdn"));
    String byrIdn = util.nvl((String)udf.getValue("byrIdn"));
      String isMix = util.nvl((String)udf.getValue("isMix"));
      String dfUsrtyp=util.nvl((String)info.getDfUsrTyp());
      String dfNmeIdn=util.nvl((String)info.getDfNmeIdn()); 
      ArrayList ary = new ArrayList();
      String conQ="";
    if(isMix.equals("Y"))
        conQ = " and c.pkt_ty <> 'NR'";
    else
       conQ = " and c.pkt_ty = 'NR'";
        if(!byrIdn.equals("0") && !byrIdn.equals("")){
        conQ = conQ+ " and nv.nme_idn = ? ";
        ary.add(byrIdn);
      }
        
    if(!branchIdn.equals("0") && !branchIdn.equals("")){
       conQ = conQ+ " and a.inv_nme_idn = ? ";
        ary.add(branchIdn);
      }
      if(dfUsrtyp.equals("EMPLOYEE") && !dfNmeIdn.equals("") && !dfNmeIdn.equals("0")){
      conQ += " and nv.emp_idn= ? ";  
      ary.add(dfNmeIdn);
      }
    String srchRef = "Insert Into gt_srch_multi(Pkt_Ty, Stk_Idn,Srch_Id,Rln_Idn,pair_id,mrte,Byr,emp, Vnm, Stt , Flg , Qty , Cts,Quot ,prte,Cmp,Rap_Rte,Rap_Dis, Sk1,pkt_dte,img,rmk )\n" + 
    "select c.Pkt_Ty , c.Idn,b.dlv_idn,nv.nme_idn , a.inv_nme_idn ,ms.idn,nv.nme byr , byr.get_nm(nv.emp_idn) emp ,c.Vnm ,c.Stt , 'Z' , b.qty, b.cts , nvl(b.fnl_sal,b.quot) , b.fnl_sal , c.cmp, c.rap_rte , c.rap_dis,c.sk1, \n" + 
    "nvl(b.dte_dlv,b.dte) , decode(nvl(b.flg,'NO'),'PR','YES','NO' ),ms.rmk \n" + 
    "from brc_mdlv a , brc_dlv_dtl b ,msal ms, mstk c,nme_v nv\n" + 
    "where a.idn = b.idn  and b.sal_idn=ms.idn and b.mstk_idn= c.idn and ms.nme_idn=nv.nme_idn and b.stt not in ('AV','RT','DLV','IS','CL')  "+conQ ;
    
     ct = db.execUpd("insert gt", srchRef, ary);
    
    String pktPrp = "srch_pkg.POP_PKT_PRP_TEST(pMdl=>?,pTbl=>'GT_SRCH_MULTI')";
    ary = new ArrayList();
    ary.add("DLV_LOC");
     ct = db.execCall(" Srch Prp ", pktPrp, ary);

    
      ArrayList pktList = new ArrayList();
      ArrayList byridnList = new ArrayList();
      HashMap byrDtl = new HashMap();
      HashMap dataDtl = new HashMap();
      GenericInterface genericInt = new GenericImpl();
      ArrayList vwPrpLst=genericInt.genericPrprVw(req,res,"dlvlocPrpList","DLV_LOC");
     
      String rsltQ =
          " select Stk_Idn ,rmk, Srch_Id dlv_idn ,mrte sal_idn,img, Rln_Idn byr_idn ,emp,byr,Vnm,Qty,to_char(Cts,'99999990.00') cts,prte ,to_char(quot, '9999999999990.00') salrte,to_char(decode(rap_rte, 1, '', trunc(((quot/greatest(rap_rte,1))*100)-100,2)), '99999990.00') saldis, to_char(trunc(cts,2) * quot, '99999990.00') amt,to_char(pkt_dte, 'dd-Mon-rrrr') pkt_dte,round(trunc(sysdate)-trunc(pkt_dte)) days,decode(nvl(exh_rte,1),1,'','#00CC66') color   ";
      for (int i = 0; i < vwPrpLst.size(); i++) {
          String fld = "prp_";
          int j = i + 1;
          if (j < 10)
              fld += "00" + j;
          else if (j < 100)
              fld += "0" + j;
          else if (j > 100)
              fld += j;
          rsltQ += ", " + fld;

      }
      rsltQ = rsltQ + " from gt_srch_multi " + " where flg= ? order by emp,byr,sk1  ";

      ary = new ArrayList();
      ary.add("Z");
     
     
      try {
          
        ArrayList  outLst = db.execSqlLst("search Result", rsltQ, ary);
        PreparedStatement pst = (PreparedStatement)outLst.get(0);
        ResultSet rs = (ResultSet)outLst.get(1);
          while (rs.next()) {
              String byr_idn = util.nvl(rs.getString("byr_idn"));
              if (!byridnList.contains(byr_idn)) {
                  byridnList.add(byr_idn);
                  byrDtl.put(byr_idn, util.nvl(rs.getString("byr")));
                  byrDtl.put(byr_idn+"_EMP", util.nvl(rs.getString("emp")));
                  pktList = new ArrayList();
              }
              HashMap pktPrpMap = new HashMap();
              pktPrpMap.put("dlvIdn", util.nvl(rs.getString("dlv_idn")));
              pktPrpMap.put("salIdn", util.nvl(rs.getString("sal_idn")));
              pktPrpMap.put("qty", util.nvl(rs.getString("Qty")));
              pktPrpMap.put("cts", util.nvl(rs.getString("Cts")));
              pktPrpMap.put("vlu", util.nvl(rs.getString("amt")));
              pktPrpMap.put("vnm", util.nvl(rs.getString("Vnm")));
              pktPrpMap.put("dte", util.nvl(rs.getString("pkt_dte")));
              pktPrpMap.put("days",util.nvl(rs.getString("days")));
              pktPrpMap.put("payStt",util.nvl(rs.getString("img")));
              pktPrpMap.put("rmk",util.nvl(rs.getString("rmk")));
              pktPrpMap.put("color", util.nvl(rs.getString("color")));
              pktPrpMap.put("SALERTE", util.nvl(rs.getString("salrte")));
              pktPrpMap.put("SALEDIS", util.nvl(rs.getString("saldis")));
              for (int j = 0; j < vwPrpLst.size(); j++) {
                  String prp = (String)vwPrpLst.get(j);
                  String fld = "prp_";
                  if (j < 9)
                      fld = "prp_00" + (j + 1);
                  else
                      fld = "prp_0" + (j + 1);
                  String val = util.nvl(rs.getString(fld));
                  pktPrpMap.put(prp, val);
              }
              pktList.add(pktPrpMap);
              dataDtl.put(byr_idn, pktList);
          }
          rs.close();
          pst.close();
          String ttlQ =
              "select Rln_Idn byr_idn,to_char(sum(cts),'99999990.00') cts,sum(qty) qty,trunc(sum(trunc(cts,2)* quot), 2) vlu from gt_srch_multi group by Rln_Idn";
          outLst = db.execSqlLst("Totals", ttlQ, new ArrayList());
         pst = (PreparedStatement)outLst.get(0);
         rs = (ResultSet)outLst.get(1);
          while (rs.next()) {
              String byr_idn = util.nvl(rs.getString("byr_idn"));
              HashMap ttlDtl = new HashMap();
              ttlDtl.put("cts", util.nvl(rs.getString("cts")));
              ttlDtl.put("vlu", util.nvl(rs.getString("vlu")));
              ttlDtl.put("qty", util.nvl(rs.getString("qty")));
              dataDtl.put(byr_idn + "_TTL", ttlDtl);
          }
          rs.close();
          pst.close();
          ttlQ =
  "select to_char(sum(cts),'99999990.00') cts,sum(qty) qty,trunc(sum(trunc(cts,2)* quot), 2) vlu from gt_srch_multi where flg= ?  ";
        outLst = db.execSqlLst("Grand Totals", ttlQ, ary);
        pst = (PreparedStatement)outLst.get(0);
        rs = (ResultSet)outLst.get(1);
          while (rs.next()) {
              HashMap ttlDtl = new HashMap();
              ttlDtl.put("cts", util.nvl(rs.getString("cts")));
              ttlDtl.put("vlu", util.nvl(rs.getString("vlu")));
              ttlDtl.put("qty", util.nvl(rs.getString("qty")));
              dataDtl.put("GRANDTTL", ttlDtl);
          }
          rs.close();
          pst.close();
          req.setAttribute("dataDtl", dataDtl);
          req.setAttribute("byrDtl", byrDtl);
          req.setAttribute("byridnList", byridnList);
          req.setAttribute("view", "Y");
      } catch (SQLException sqle) {
          // TODO: Add catch code
          sqle.printStackTrace();
      }
      finalizeObject(db, util);
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
                rtnPg=util.checkUserPageRights("",util.getFullURL(req));
                if(rtnPg.equals("unauthorized"))
                util.updAccessLog(req,res,"Branch Pending", "Unauthorized Access");
                else
            util.updAccessLog(req,res,"Branch Pending", "init");
            }
            }
            return rtnPg;
            }
      
    public void finalizeObject(DBMgr db, DBUtil util){
        try {
            db=null;
            util=null;
        } catch (Throwable t) {
            // TODO: Add catch code
            t.printStackTrace();
        }
       
    }
    
}
