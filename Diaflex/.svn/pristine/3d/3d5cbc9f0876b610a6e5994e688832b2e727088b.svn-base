package ft.com.report;

import ft.com.DBMgr;
import ft.com.DBUtil;
import ft.com.InfoMgr;

import ft.com.generic.GenericImpl;
import ft.com.generic.GenericInterface;

import java.io.IOException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.HashMap;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;


public class DailyMemoReportAction extends DispatchAction {

    public ActionForward load(ActionMapping am, ActionForm form,
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
        util.updAccessLog(req,res,"Daily Memo Report", "load start");
        DailyMemoReportForm dailymemoReportForm = (DailyMemoReportForm)form;
        ResultSet rs = null;
        ArrayList ary = new ArrayList();
        String dtefrom = " trunc(sysdate) ";
        String dteto = " trunc(sysdate) ";
        String flg=util.nvl(req.getParameter("flg"));
        if(flg.equals("New")){
            dailymemoReportForm.reset();
        }
        String dfr = util.nvl((String)dailymemoReportForm.getValue("dtefr"));
        String dto = util.nvl((String)dailymemoReportForm.getValue("dteto"));
        String spersonId = util.nvl((String)dailymemoReportForm.getValue("styp"));
        String buyerId = util.nvl(req.getParameter("nmeID"));
        String group = util.nvl((String)dailymemoReportForm.getValue("group"));
        String typ = util.nvl((String)dailymemoReportForm.getValue("typ"));
        String sql="";
        String bql="";
        HashMap dbinfo = info.getDmbsInfoLst();
        String cnt = (String)dbinfo.get("CNT");
        ArrayList rolenmLst=(ArrayList)info.getRoleLst();
        String usrFlg=util.nvl((String)info.getUsrFlg());
        String dfgrpnmeidn=util.nvl(info.getDfGrpNmeIdn());
        String dfNmeIdn=util.nvl((String)info.getDfNmeIdn());
        String dfUsrtyp=util.nvl((String)info.getDfUsrTyp());
        if(!dfr.equals(""))
          dtefrom = "to_date('"+dfr+"' , 'dd-mm-yyyy')";
        
        if(!dto.equals(""))
          dteto = "to_date('"+dto+"' , 'dd-mm-yyyy')";
        
      if(!spersonId.equals("") && spersonId.length()>1){
        sql = "and a.emp_idn= ? ";
        ary.add(spersonId);
      }
      
      if(!buyerId.equals("")){
        bql =bql+  "and a.nme_idn= ? ";
        ary.add(buyerId);
      }
        if(!typ.equals("")){
        bql =bql+  "and a.typ= ? ";
        ary.add(typ);
        }
        if(!group.equals("")){
          bql = " and c.grp_nme_idn= ? ";
          ary.add(group);
        }else{
        if(usrFlg.equals("SYS") || rolenmLst.contains("ADMIN")){
        }else if(!dfgrpnmeidn.equals("") && !dfgrpnmeidn.equals("0")){
        bql =bql+" and c.grp_nme_idn=?"; 
        ary.add(dfgrpnmeidn);
        }  
        }        
            if(dfUsrtyp.equals("EMPLOYEE") && !dfNmeIdn.equals("") && !dfNmeIdn.equals("0")){
                    if(util.nvl((String)info.getDmbsInfoLst().get("EMP_LOCK")).equals("Y")){
                        bql += " and (c.emp_idn= ? or c.emp_idn in(select emp_idn from nme_v where typ='EMPLOYEE' and mgr_idn= ?)) ";  
                        ary.add(dfNmeIdn);
                        ary.add(dfNmeIdn);
                    }
            }
        
        String loadqry =
      "select to_char(a.dte,'dd-mm-yyyy') dte, a.nme_idn,a.emp, a.byr, a.typ"+
      ", decode(a.typ,'O','PBB Issue','BID','Bid Issue','K','KSelect', 'I', 'Internal','H','Happy Hours', 'E', 'External', 'WH', 'Web Hold','S', 'Show', 'Z', 'List') dsp_typ"+
      ", count(distinct a.idn) cnt, sum(b.qty) qty, trunc(sum(trunc(b.cts,2)),2) cts, to_char(sum(b.cts*(nvl(b.fnl_sal, b.quot)/nvl(a.exh_rte,1))),'9999999999990.00') vlu"+
      " from jan_v a, jandtl b,Nme_V c where a.idn = b.idn and c.Nme_Idn=A.Nme_Idn and trunc(a.dte) between "+dtefrom+" and "+dteto+" and a.typ in ('I', 'E','S', 'WH', 'Z','H') and b.stt not in ('APRT','RT','CS','ALC','MRG') "+sql+""+bql+
      "group by to_char(a.dte,'dd-mm-yyyy'),a.nme_idn,a.emp, a.byr, a.typ order by a.emp, a.byr, a.typ";


            ArrayList outLst = db.execSqlLst("loadqry", loadqry, ary);
            PreparedStatement pst = (PreparedStatement)outLst.get(0);
            rs = (ResultSet)outLst.get(1);
      HashMap memoDtl=new HashMap();
      HashMap byrDtl=new HashMap();
      HashMap byr_idn=new HashMap();
      ArrayList dtl=null;
      ArrayList empList=new ArrayList();
      ArrayList memotyp=new ArrayList();
      ArrayList Display_typ=new ArrayList();
      int i=0;
        while (rs.next()) {
              dtl=new ArrayList();
                if(!empList.contains(util.nvl(rs.getString("emp")))) {
                    empList.add(util.nvl(rs.getString("emp")));
                }
            
              byrDtl.put(util.nvl(rs.getString("emp"))+"_"+i,util.nvl(rs.getString("byr")));
              i++;
            
              dtl.add(util.nvl(rs.getString("cnt")));  
              dtl.add(util.nvl(rs.getString("qty")));
              dtl.add(util.nvl(rs.getString("cts")));
              dtl.add(util.nvl(rs.getString("vlu")));
              memoDtl.put(util.nvl(rs.getString("emp"))+"_"+util.nvl(rs.getString("byr"))+"_"+util.nvl(rs.getString("typ")),dtl);
            
              byr_idn.put(util.nvl(rs.getString("emp"))+"_"+util.nvl(rs.getString("byr")),util.nvl(rs.getString("nme_idn")));  
              byr_idn.put(util.nvl(rs.getString("emp"))+"_"+util.nvl(rs.getString("byr"))+"_DTE",util.nvl(rs.getString("dte")));  

                if(!memotyp.contains(util.nvl(rs.getString("typ")))) {
                  memotyp.add(util.nvl(rs.getString("typ")));
                }
            
                if(!Display_typ.contains(util.nvl(rs.getString("dsp_typ")))) {
                  Display_typ.add(util.nvl(rs.getString("dsp_typ")));
                }
            
      }
        rs.close(); pst.close();
      req.setAttribute("empList", empList); 
      req.setAttribute("byrDtl", byrDtl); 
      req.setAttribute("byr_idn", byr_idn); 
      req.setAttribute("memoDtl", memoDtl); 
      req.setAttribute("memotyp", memotyp);
      req.setAttribute("Display_typ", Display_typ);
            HashMap allPageDtl= (info.getPageDetails() == null)?new HashMap():(HashMap)info.getPageDetails();
            HashMap pageDtl=(HashMap)allPageDtl.get("DAILY_MEMO_RPT");
            if(pageDtl==null || pageDtl.size()==0){
            pageDtl=new HashMap();
            pageDtl=util.pagedef("DAILY_MEMO_RPT");
            allPageDtl.put("DAILY_MEMO_RPT",pageDtl);
            }
            info.setPageDetails(allPageDtl);
            util.updAccessLog(req,res,"Daily Memo Report", "load end");
            util.saleperson();
            if(cnt.equals("hk"))
            util.groupcompany();
        return am.findForward("load");
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
            util.updAccessLog(req,res,"Daily Sale Report", "pktDtlExcel start");
            GenericInterface genericInt = new GenericImpl();
        ArrayList vwprpLst = genericInt.genericPrprVw(req,res,"DAILY_VW","DAILY_VW");
        DailyMemoReportForm  dailyMemoReportForm  = (DailyMemoReportForm )form;
        String nmeidn = util.nvl(req.getParameter("nmeidn"));
        String dte = util.nvl(req.getParameter("dte"));
        ArrayList pktDtlList = new ArrayList();
        ArrayList itemHdr = new ArrayList();
        ArrayList ary = new ArrayList();
        ArrayList prpDspBlocked = info.getPageblockList();
        itemHdr.add("MEMOID");
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
                 if(prp.equals("RAP_RTE")){
                        itemHdr.add("RAPVLU");
                 }
                }              
            }
        nmeidn=nmeidn.replaceFirst(",", "");
        dte=dte.replaceFirst(",", "");
        String[] nmeidnLst=nmeidn.split(",");
        String[] dteLst=dte.split(",");
        System.out.println(nmeidn);
            String mprpStr = "";
            String mdlPrpsQ = " Select Decode ( Rank() Over ( Order By Srt),1,' ',',')||''''||Prp||''''||' '||Upper(Replace(Replace(Replace(Replace(Replace(Prp,'-','_'),'&','_'),'COMMENT','COM1'),'REMARKS','REM1'),'SIZE','SIZE1')) str From Rep_Prp Rp Where rp.MDL = ? order by srt " ;
            ary = new ArrayList();
            ary.add("DAILY_VW");

            ArrayList outLst = db.execSqlLst("mprp ", mdlPrpsQ , ary);
            PreparedStatement pst = (PreparedStatement)outLst.get(0);
            ResultSet rs = (ResultSet)outLst.get(1);
            while(rs.next()) {
            String val = util.nvl((String)rs.getString("str"));
            mprpStr = mprpStr +" "+val;
            }
            rs.close(); pst.close();
        for(int i=0;i<nmeidnLst.length;i++){
        nmeidn=nmeidnLst[i];  
        dte=dteLst[i];  
       String dtefrom = " trunc(sysdate) ";
       String dteto = " trunc(sysdate) ";
        if(!nmeidn.equals("") && !dte.equals("")){
              dtefrom = " to_date('"+dte+"' , 'dd-mm-yyyy') ";
              dteto = " to_date('"+dte+"' , 'dd-mm-yyyy') ";
         String  pktDtlSql=" with  STKDTL as  ( Select c.sk1,a.idn memoid,c.idn,nvl(nvl(nvl(st.txt,st.dte),st.num),st.prt1) atr , mprp,to_char(b.cts,'99999999990.99') cts,\n" + 
         " DECODE(upper(NVL(pack_var.get_usr, 'NA')), 'DEMO', dbms_random.STRING('X', 12), c.vnm) vnm,nvl(c.upr,c.cmp) rate,\n" + 
         " c.rap_rte raprte,byr.get_nm(nvl(d.emp_idn,0)) sale_name,byr.get_nm(nvl(d.nme_idn,0)) byr,nvl(b.fnl_sal, b.quot) memoQuot,to_char(trunc(b.cts,2) * c.rap_rte, '99999999990.00') rapvlu,\n" + 
         " to_char(trunc(b.cts,2) * Nvl(b.fnl_usd, b.quot), '9999999990.00') amt,byr.get_country(nvl(a.nme_idn,0)) country,to_char((((nvl(b.fnl_usd, b.quot))*b.cts/b.cts)/(c.rap_Rte*b.cts/b.cts)*100) - 100,'999999990.00') r_dis  \n" + 
         " from stk_dtl st,mjan a,JAN_PKT_DTL_V b, mstk c,mnme d \n" + 
         " where st.mstk_idn=c.idn and a.idn=b.idn and b.mstk_idn=c.idn and d.nme_idn=a.nme_idn and c.idn=b.mstk_idn \n" + 
         " And B.stt not in ('APRT','RT','CS','ALC','MRG') And B.Typ  in ('I', 'E','S','O', 'WH', 'Z','K','H','BID')  And D.Nme_Idn= ? \n" + 
         " and trunc(a.dte)  between  "+dtefrom+"  and  "+dteto +" \n" + 
         " and exists (select 1 from rep_prp rp where rp.MDL = 'DAILY_VW' and st.mprp = rp.prp)  And st.Grp = 1) " +
         " Select * from stkDtl PIVOT " +
         " ( max(atr) " +
         " for mprp in ( "+mprpStr+" ) ) order by 1 " ;
        
        ary = new ArrayList();
        ary.add(nmeidn);

            outLst = db.execSqlLst("pktDtl", pktDtlSql,ary);
            pst = (PreparedStatement)outLst.get(0);
            rs = (ResultSet)outLst.get(1);
        while(rs.next()){
            HashMap pktDtl = new HashMap();
            pktDtl.put("MEMOID", util.nvl(rs.getString("memoid")));
            pktDtl.put("VNM", util.nvl(rs.getString("vnm")));
            pktDtl.put("SALEPERSON", util.nvl(rs.getString("sale_name")));
            pktDtl.put("BYR", util.nvl(rs.getString("byr")));
            pktDtl.put("COUNTRY", util.nvl(rs.getString("country")));
            pktDtl.put("RTE", util.nvl(rs.getString("rate")));
            pktDtl.put("SALE RTE", util.nvl(rs.getString("memoQuot")));
            pktDtl.put("AMOUNT", util.nvl(rs.getString("amt")));           
            for (int v = 0; v < vwprpLst.size(); v++) {
            String vwPrp = (String)vwprpLst.get(v);
            String fldName = vwPrp;
            if(vwPrp.toUpperCase().equals("H&A"))
            fldName = "H_A";
            
            if(vwPrp.toUpperCase().equals("COMMENT"))
            fldName = "COM1";
            
            if(vwPrp.toUpperCase().equals("REMARKS"))
            fldName = "REM1";
            
            if(vwPrp.toUpperCase().equals("COL-DESC"))
            fldName = "COL_DESC";
            
            if(vwPrp.toUpperCase().equals("COL-SHADE"))
            fldName = "COL_SHADE";
            
            if(vwPrp.toUpperCase().equals("FL-SHADE"))
            fldName = "FL_SHADE";
            
            if(vwPrp.toUpperCase().equals("STK-CTG"))
            fldName = "STK_CTG";
            
            if(vwPrp.toUpperCase().equals("STK-CODE"))
            fldName = "STK_CODE";
            
            if(vwPrp.toUpperCase().equals("SUBSIZE"))
            fldName = "SUBSIZE1";
            
            if(vwPrp.toUpperCase().equals("SIZE"))
            fldName = "SIZE1";
            
            if(vwPrp.toUpperCase().equals("MIX_SIZE"))
            fldName = "MIX_SIZE1";
            
            if(vwPrp.toUpperCase().equals("STK-CTG"))
            fldName = "STK_CTG";
            
            if(vwPrp.toUpperCase().equals("CRN-OP"))
            fldName = "CRN_OP";
            
            if(vwPrp.toUpperCase().equals("CRTWT"))
            fldName = "cts";
            
            if(vwPrp.toUpperCase().equals("RAP_DIS"))
            fldName = "r_dis";
            
            if(vwPrp.toUpperCase().equals("RTE"))
            fldName = "rate";
            
            if (vwPrp.toUpperCase().equals("RAP_RTE"))
            fldName = "raprte";
                if(vwPrp.toUpperCase().equals("MEM_COMMENT"))
                fldName = "MEM_COM1";
            String fldVal = util.nvl((String)rs.getString(fldName));
            
            pktDtl.put(vwPrp, fldVal);
            }
            pktDtl.put("RTE",util.nvl(rs.getString("rate")));
            pktDtl.put("RAP_DIS",util.nvl(rs.getString("r_dis")));
            pktDtl.put("CRTWT",util.nvl(rs.getString("cts")));
            pktDtl.put("RAP_RTE",util.nvl(rs.getString("raprte")));
            pktDtl.put("RAPVLU",util.nvl(rs.getString("rapvlu")));
            pktDtlList.add(pktDtl);
        }
            rs.close(); pst.close();
            
        }
        }
        session.setAttribute("pktList", pktDtlList);
        session.setAttribute("itemHdr",itemHdr);
        util.updAccessLog(req,res,"Daily Sale Report", "pktDtlExcel end");
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
                rtnPg=util.checkUserPageRights("",util.getFullURL(req));
                if(rtnPg.equals("unauthorized"))
                util.updAccessLog(req,res,"Daily Memo Report", "Unauthorized Access");
                else
                util.updAccessLog(req,res,"Daily Memo Report", "init");
            }
            }
            return rtnPg;
            }
    public DailyMemoReportAction() {
        super();
    }
}
