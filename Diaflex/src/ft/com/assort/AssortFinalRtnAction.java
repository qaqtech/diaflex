package ft.com.assort;

import ft.com.DBMgr;
import ft.com.DBUtil;
import ft.com.ExcelUtil;
import ft.com.GtMgr;
import ft.com.InfoMgr;
import ft.com.dao.GtPktDtl;
import ft.com.dao.Mprc;

import ft.com.generic.GenericImpl;
import ft.com.lab.LabComparisonRtnForm;

import ft.com.marketing.SearchQuery;

import ft.com.pri.PriceRtnForm;

import java.io.IOException;

import java.io.OutputStream;

import java.sql.CallableStatement;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

public class AssortFinalRtnAction extends DispatchAction {
    
   
    public ActionForward load(ActionMapping am,ActionForm form,HttpServletRequest req,HttpServletResponse res) throws Exception {
      HttpSession session = req.getSession(false);
      InfoMgr info = (InfoMgr)session.getAttribute("info");
      GtMgr gtMgr = (GtMgr)session.getAttribute("gtMgr");

      DBUtil util = new DBUtil();
      DBMgr db = new DBMgr();
      String rtnPg="sucess";
      if(info!=null){  
      db.setCon(info.getCon());
      util.setDb(db);
      util.setInfo(info);
      db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
      util.setLogApplNm(info.getLoApplNm());
      rtnPg=init(req,res,session,util);
      }else
      rtnPg="sessionTO";
      if(!rtnPg.equals("sucess")){
          return am.findForward(rtnPg);   
      }else{
            util.getOpenCursorConnection(db,util,info);
        AssortFinalRtnForm assortForm = (AssortFinalRtnForm)form;
        AssortFinalRtnInterface assortInt = new AssortFinalRtnImpl();
        util.updAccessLog(req,res,"Assort Final Return", "Load");

        assortForm.reset();
        ArrayList mprcList = assortInt.getPrc(req,res);
        assortForm.setMprcList(mprcList);
        
        ArrayList empList = assortInt.getEmp(req,res);
        assortForm.setEmpList(empList);
        
        //assortInt.isRepairIS(req, res);MAYUR
        util.updAccessLog(req,res,"Assort Final Return", "Emp list : "+empList.size());
        util.updAccessLog(req,res,"Assort Final Return", "End");
      return am.findForward("load");
        }
    }
    public ActionForward fecth(ActionMapping am,ActionForm form,HttpServletRequest req,HttpServletResponse res) throws Exception {
      HttpSession session = req.getSession(false);
      InfoMgr info = (InfoMgr)session.getAttribute("info");
      GtMgr gtMgr = (GtMgr)session.getAttribute("gtMgr");

      DBUtil util = new DBUtil();
      DBMgr db = new DBMgr();
      String rtnPg="sucess";
      if(info!=null){  
      db.setCon(info.getCon());
      util.setDb(db);
      util.setInfo(info);
      db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
      util.setLogApplNm(info.getLoApplNm());
      rtnPg=init(req,res,session,util);
      }else
      rtnPg="sessionTO";
      if(!rtnPg.equals("sucess")){
          return am.findForward(rtnPg);   
      }else{
        AssortFinalRtnForm assortForm = (AssortFinalRtnForm)form;
        AssortFinalRtnInterface assortInt = new AssortFinalRtnImpl();
        util.updAccessLog(req,res,"Assort Final Return", "Fetch");
        String mprcIdn = util.nvl((String)assortForm.getValue("mprcIdn"));
        String stoneId = util.nvl((String)assortForm.getValue("vnmLst"));
        String empId = util.nvl((String)assortForm.getValue("empIdn"));
        String issueId = util.nvl((String)assortForm.getValue("issueId"));
        HashMap prcBeansList = (HashMap)session.getAttribute("mprcBean");
        String prcStt = "";
        String pri_yn = "";
        if(!issueId.equals("") && mprcIdn.equals("0")){
            util.updAccessLog(req,res,"Assort Final Return", "Issue Id : "+issueId);
            ArrayList ary = new ArrayList();
            String issuStt = " select b.is_stt , b.idn,b.pri_yn from iss_rtn a , mprc b where a.prc_id = b.idn and a.iss_id= ? ";
            ary.add(issueId);
          ArrayList rsLst = db.execSqlLst("issueStt", issuStt, ary);
          PreparedStatement pst = (PreparedStatement)rsLst.get(0);
          ResultSet rs = (ResultSet)rsLst.get(1);
            while(rs.next()){
              prcStt = util.nvl(rs.getString("is_stt"));
              mprcIdn = util.nvl(rs.getString("idn"));
              pri_yn = util.nvl(rs.getString("pri_yn"));
            }
        rs.close();
        pst.close();
        }else{
        Mprc mprcDto = (Mprc)prcBeansList.get(mprcIdn);
        prcStt = mprcDto.getIs_stt();
        pri_yn= mprcDto.getPri_yn();
        util.updAccessLog(req,res,"Assort Final Return", "process Id : "+mprcIdn);
        }
        HashMap params = new HashMap();
        params.put("stt", prcStt);
        params.put("vnm",stoneId);
        params.put("empIdn", empId);
        params.put("issueId", issueId);
        HashMap stockList = assortInt.FecthResultGt(req,res, params);
        if(stockList.size()>0){
        HashMap totals = assortInt.GetTotal(req,res);
        req.setAttribute("totalMap", totals);
            ArrayList options = assortInt.getOptions( req ,res ,mprcIdn);
         
            req.setAttribute("OPTIONS", options);

        }
        req.setAttribute("view", "Y");
        assortForm.setValue("prcId", mprcIdn);
        assortForm.setValue("empId", empId);
        assortForm.setValue("pri_yn", pri_yn);
        req.setAttribute("prcId", mprcIdn);
          String lstNme = "ASFNRT_"+info.getLogId()+"_"+util.getToDteGiveFrmt("yyyyMMdd");
          assortForm.setValue("lstNme", lstNme);
          gtMgr.setValue(lstNme, stockList);
          req.setAttribute("lstNme", lstNme);
          gtMgr.setValue(lstNme+"_SEL", new ArrayList());
        HashMap allPageDtl= (info.getPageDetails() == null)?new HashMap():(HashMap)info.getPageDetails();
        HashMap pageDtl=(HashMap)allPageDtl.get("ASSORT_FINAL_RT");
        if(pageDtl==null || pageDtl.size()==0){
        pageDtl=new HashMap();
        pageDtl=util.pagedef("ASSORT_FINAL_RT");
        allPageDtl.put("ASSORT_FINAL_RT",pageDtl);
        }
        info.setPageDetails(allPageDtl);
        util.updAccessLog(req,res,"Assort Final Return", "End");
        return am.findForward("load");
        }
    }
    
    public ActionForward stockUpd(ActionMapping am,ActionForm form,HttpServletRequest req,HttpServletResponse res) throws Exception {
       HttpSession session = req.getSession(false);
       InfoMgr info = (InfoMgr)session.getAttribute("info");
       GtMgr gtMgr = (GtMgr)session.getAttribute("gtMgr");

       DBUtil util = new DBUtil();
       DBMgr db = new DBMgr();
       String rtnPg="sucess";
       if(info!=null){  
       db.setCon(info.getCon());
       util.setDb(db);
       util.setInfo(info);
       db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
       util.setLogApplNm(info.getLoApplNm());
       rtnPg=init(req,res,session,util);
       }else
       rtnPg="sessionTO";
       if(!rtnPg.equals("sucess")){
           return am.findForward(rtnPg);   
       }else{
         AssortFinalRtnForm assortForm = (AssortFinalRtnForm)form;
         AssortFinalRtnInterface assortInt = new AssortFinalRtnImpl();
        util.updAccessLog(req,res,"Assort Final Return", "stockUpd");
        HashMap dbinfo = info.getDmbsInfoLst();
        String cnt = (String)dbinfo.get("CNT");
        String adv_pri = util.nvl((String)dbinfo.get("ADV_PRI"),"N");
        String stt = util.nvl(req.getParameter("stt"));
        String prcId= (String)assortForm.getValue("prcId");
        String issId = req.getParameter("issIdn");
        String mstkIdn = req.getParameter("mstkIdn");
         String lastpage = util.nvl(req.getParameter("lastpage"));
         String currentpage = util.nvl(req.getParameter("currentpage"));
         String findVnm = util.nvl(req.getParameter("findVnm"));
           String vnm="";
           if(!currentpage.equals("") && findVnm.equals("")){
               String lstNme = (String)assortForm.getValue("lstNme");
               HashMap stockList = (HashMap)gtMgr.getValue(lstNme);
               ArrayList stkIdnList = (ArrayList)gtMgr.getValue(lstNme+"_ALLLST"); 
               mstkIdn = (String)stkIdnList.get(Integer.parseInt(currentpage));
               GtPktDtl pktDtl = (GtPktDtl)stockList.get(mstkIdn);
               vnm = util.nvl(pktDtl.getVnm());
               issId = util.nvl(pktDtl.getValue("issIdn"),issId);
           } 
           
        if(!findVnm.equals("")){
                    String lstNme = (String)assortForm.getValue("lstNme");
                    HashMap stockList = (HashMap)gtMgr.getValue(lstNme);
                    ArrayList stkIdnList = (ArrayList)gtMgr.getValue(lstNme+"_ALLLST");
                    String fromIdn = util.nvl((String)util.getMstkIdn(findVnm));
                    if(!fromIdn.equals("")){
                        int indexOfIdn=stkIdnList.indexOf(fromIdn);
                        if(indexOfIdn >= 0){
                        mstkIdn = (String)stkIdnList.get(indexOfIdn);
                        GtPktDtl pktDtl = (GtPktDtl)stockList.get(mstkIdn);
                        vnm = util.nvl(pktDtl.getVnm());
                        issId = util.nvl(pktDtl.getValue("issIdn"),issId);
                        currentpage=String.valueOf(indexOfIdn);
                        }
                    }else{
                        mstkIdn = (String)stkIdnList.get(Integer.parseInt(currentpage));
                        GtPktDtl pktDtl = (GtPktDtl)stockList.get(mstkIdn);
                        vnm = util.nvl(pktDtl.getVnm());
                        issId = util.nvl(pktDtl.getValue("issIdn"),issId);
                    }
        }
        HashMap params = new HashMap();
        params.put("prcId", prcId);
        params.put("issId", issId);
        params.put("mstkIdn", mstkIdn);
        ArrayList ary = new ArrayList();
        ArrayList stockPrpList = assortInt.StockUpdPrp(req,res, assortForm , params);
        req.setAttribute("StockList", stockPrpList);
        assortForm.setValue("prcId", prcId);
        assortForm.setValue("issIdn", issId);
        assortForm.setValue("mstkIdn", mstkIdn);
        String isRepair = util.nvl(req.getParameter("isRepair"));

             if(isRepair.equals("Y")){
             int prcChkIdn = 0 ;
             String newmstkIdn="";
             String getPrcChkIdn = " select prc_chk_pkg.get_idn from dual ";
             ResultSet rs = db.execSql(" Prc Chk Idn", getPrcChkIdn, new ArrayList());
             if(rs.next()) {
                 prcChkIdn = rs.getInt(1);
                 String insMst = " prc_chk_pkg.add_new(?, ?) ";
                 ary = new ArrayList();
                 ary.add(Integer.toString(prcChkIdn));
                 ary.add(mstkIdn);
                 
             db.execCall(" Prc Mst", insMst, ary);
             newmstkIdn=String.valueOf(prcChkIdn);  
             }
             rs.close(); 
             ary = new ArrayList();
             ary.add(newmstkIdn);
             int ct =  db.execCall("stk_rap_upd", "stk_rap_upd(pIdn => ?)", ary);
             
             ary = new ArrayList();
             ary.add(newmstkIdn);
             ct =  db.execCall("stk_rap_upd", "stk_rap_upd(pIdn => ?)", ary);
             String getPrcDtl = " select b.rap_rte, trunc(sum(a.pct), 2) dis, ceil(b.rap_rte*(100+sum(a.pct))/100) rte "+
                              " from prm_dis_v a , mstk b "+
                             " where a.mstk_idn = b.idn and mstk_idn=? " +
                             " group by a.mstk_idn, b.rap_rte ";
             ArrayList  outLst = db.execSqlLst(" get vals", getPrcDtl, ary);
             PreparedStatement pst = (PreparedStatement)outLst.get(0);
             rs = (ResultSet)outLst.get(1);
             while(rs.next()){
                 req.setAttribute("uprDis", util.nvl(rs.getString("dis")));
                 req.setAttribute("cmp", util.nvl(rs.getString("rte")));
             }
             rs.close();
             pst.close();
             assortForm.setValue("stkIdn", newmstkIdn);
         }
           

           if(cnt.equals("hk")){
               if(!adv_pri.equals("Y")){
               String sh="",sz="",co="",clr="",cmp="",fa_pri="";
               float raprte=0,mfg_rte=0;
               ArrayList grpList=util.sheetDtl(mstkIdn);
               String getmfgpri=util.GET_MFG_PRI(grpList);
               req.setAttribute("grpSheetList", grpList);
               
               String getPrcChkIdn = "select trunc(ms.rap_rte*(100 + "+getmfgpri+")/100,2) mfg_rte,ms.rap_rte,cmp.num cmp, fapri.num fa_pri\n" + 
               "from mstk ms,stk_dtl cmp,stk_dtl fapri\n" + 
               "where ms.idn= ? and\n" + 
               "ms.idn=cmp.mstk_idn and cmp.grp=1 and cmp.mprp='CMP_RTE' and \n" + 
               "ms.idn=fapri.mstk_idn and fapri.grp=1 and fapri.mprp='FA_PRI'";
               ary = new ArrayList();
               ary.add(mstkIdn);
                 ArrayList  outLst = db.execSqlLst("CMP_RTE", getPrcChkIdn, ary);
                 PreparedStatement pst = (PreparedStatement)outLst.get(0);
                 ResultSet rs = (ResultSet)outLst.get(1);
               while(rs.next()) {
                   cmp=util.nvl((String)rs.getString("cmp")); 
                   fa_pri=util.nvl((String)rs.getString("fa_pri")); 
                   mfg_rte = rs.getFloat("mfg_rte");
                   raprte = rs.getFloat("rap_rte");
               }
               rs.close();
               pst.close();
               assortForm.setValue("CMP_RTE_FIX", cmp);
               assortForm.setValue("FA_PRI_FIX", fa_pri);
               assortForm.setValue("MFG_RTE_FIX", mfg_rte);
               assortForm.setValue("RAP_RTE_FIX", raprte);
               }else{
                   int getmfgpri=0;
                   int getcmp=0;
                   String sheetappliedCmp="";
                   String sheetappliedmfg="";
                   ary = new ArrayList();
                   ary.add(mstkIdn);
                   ary.add("NR");
                   ArrayList out = new ArrayList();
                   out.add("I");
                   out.add("I");
                   out.add("V");
                   out.add("V");
                   out.add("I");
                   CallableStatement cst = null;
                   cst = db.execCall("dpp_pri.AryPri","dpp_pri.AryPri(pIdn =>?, pTyp =>?, NrmDis =>?, MfgDis => ?, StrDis =>?, StrMfg=>?,pRap =>?)", ary, out);
                   getcmp = cst.getInt(ary.size()+1);
                   getmfgpri = cst.getInt(ary.size()+2);
                   sheetappliedCmp = cst.getString(ary.size()+3);
                   sheetappliedmfg = cst.getString(ary.size()+4);
                   
                 cst.close();
                 cst=null;
                   String sh="",sz="",co="",clr="",cmp="",fa_pri="";
                   float raprte=0,mfg_rte=0;
                   
                   String getPrcChkIdn = "select trunc(ms.rap_rte*(100 + "+getmfgpri+")/100,2) mfg_rte,ms.rap_rte,cmp.num cmp, fapri.num fa_pri\n" + 
                   "from mstk ms,stk_dtl cmp,stk_dtl fapri\n" + 
                   "where ms.idn= ? and\n" + 
                   "ms.idn=cmp.mstk_idn and cmp.grp=1 and cmp.mprp='CMP_RTE' and \n" + 
                   "ms.idn=fapri.mstk_idn and fapri.grp=1 and fapri.mprp='FA_PRI'";
                   ary = new ArrayList();
                   ary.add(mstkIdn);
                 ArrayList  outLst = db.execSqlLst("CMP_RTE", getPrcChkIdn, ary);
                 PreparedStatement pst = (PreparedStatement)outLst.get(0);
                 ResultSet rs = (ResultSet)outLst.get(1);
                   while(rs.next()) {
                       cmp=util.nvl((String)rs.getString("cmp")); 
                       fa_pri=util.nvl((String)rs.getString("fa_pri")); 
                       mfg_rte = rs.getFloat("mfg_rte");
                       raprte = rs.getFloat("rap_rte");
                   }
                   rs.close();
                   pst.close();
                   assortForm.setValue("CMP_RTE_FIX", cmp);
                   assortForm.setValue("FA_PRI_FIX", fa_pri);
                   assortForm.setValue("MFG_RTE_FIX", mfg_rte);
                   assortForm.setValue("RAP_RTE_FIX", raprte);
                   assortForm.setValue("APPLIED_MFG_SHEET", sheetappliedmfg);
               }
           }
           if(isRepair.equals("Y"))
           session.setAttribute("ISREPAIR", "Y");
           else
           session.setAttribute("ISREPAIR", "N");
           
           assortForm.setValue("lastpage", lastpage);
           assortForm.setValue("isRepair", isRepair);
           assortForm.setValue("currentpage", currentpage);
           assortForm.setValue("stt", stt);
           assortForm.setValue("url", "assortFinalRtn.do?method=stockUpd&mstkIdn="+mstkIdn+"&issIdn="+issId+"&isRepair="+isRepair+"&stt="+stt);
           req.setAttribute("PopUpidn", mstkIdn);
           if(cnt.equals("hk"))
           assortForm.setValue("applyYN", "Y");
           util.updAccessLog(req,res,"Assort Final Return", mstkIdn);
           util.updAccessLog(req,res,"Assort Final Return", "End");
           return am.findForward("loadStock");
           
         }
     }
    public ActionForward bulkUpdate(ActionMapping am,ActionForm form,HttpServletRequest req,HttpServletResponse res) throws Exception {
      HttpSession session = req.getSession(false);
      InfoMgr info = (InfoMgr)session.getAttribute("info");
      GtMgr gtMgr = (GtMgr)session.getAttribute("gtMgr");

      DBUtil util = new DBUtil();
      DBMgr db = new DBMgr();
      String rtnPg="sucess";
      if(info!=null){  
      db.setCon(info.getCon());
      util.setDb(db);
      util.setInfo(info);
      db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
      util.setLogApplNm(info.getLoApplNm());
      rtnPg=init(req,res,session,util);
      }else
      rtnPg="sessionTO";
      if(!rtnPg.equals("sucess")){
          return am.findForward(rtnPg);   
      }else{
        AssortFinalRtnForm assortForm = (AssortFinalRtnForm)form;
       
        util.updAccessLog(req,res,"Assort Final Return", "bulkUpdate");
          String lstNme= (String)assortForm.getValue("lstNme");

       
        int ct=0;
        ArrayList ary = new ArrayList();
        ArrayList prpList = (ArrayList)session.getAttribute("prpList");
        String prcId= (String)assortForm.getValue("prcId");
        String grp = "select grp from mprc where idn=?";
        ary = new ArrayList();
        ary.add(prcId);
          
        String grpVal="";
          ArrayList  outLst = db.execSqlLst("grp", grp, ary);
          PreparedStatement pst = (PreparedStatement)outLst.get(0);
          ResultSet rs = (ResultSet)outLst.get(1);
           
        if(rs.next())
        grpVal = rs.getString("grp");
        rs.close();
         pst.close();
          ArrayList selectList = (ArrayList)gtMgr.getValue(lstNme+"_SEL");
          if(selectList!=null && selectList.size()>0) {
              for(int i=0;i<selectList.size();i++){
                  
              String stkIdn = (String)selectList.get(i);
               ary = new ArrayList();
            ary.add(stkIdn);
            ary.add(grpVal);
            int issIdn =0;
            String getIssIdn = "select ISS_RTN_PKG.LST_ISS_ID(?,?) issIdn from dual";
             rs = db.execSql("issIdn", getIssIdn, ary);
             if(rs.next())
             issIdn = rs.getInt(1);
            rs.close();
            for(int j=0 ; j< prpList.size() ; j++){
            String lprp = (String)prpList.get(j);
            String fldVal =util.nvl((String)assortForm.getValue(lprp));
            if(!fldVal.equals("") && !fldVal.equals("0")){
             ary = new ArrayList();
             ary.add(String.valueOf(issIdn));
             ary.add(stkIdn);
             ary.add(lprp);
             ary.add(fldVal);
            ct = db.execCall("updateAssortPrp", "ISS_RTN_PKG.RTN_PKT_PRP(pIssId => ?, pStkIdn =>?, pPrp =>?, pVal => ? )", ary);
           
            }}
        }
        }
        if(ct>0)
            req.setAttribute( "msg","Propeties Get update successfully");
        rs.close(); 
        util.updAccessLog(req,res,"Assort Final Return", "End");
        return am.findForward("multiPrp");
        }
    }
    public ActionForward savePrpUpd(ActionMapping am,ActionForm form,HttpServletRequest req,HttpServletResponse res) throws Exception {
          HttpSession session = req.getSession(false);
          InfoMgr info = (InfoMgr)session.getAttribute("info");
          GtMgr gtMgr = (GtMgr)session.getAttribute("gtMgr");
          DBUtil util = new DBUtil();
          DBMgr db = new DBMgr();
          String rtnPg="sucess";
          if(info!=null){  
          db.setCon(info.getCon());
          util.setDb(db);
          util.setInfo(info);
          db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
          util.setLogApplNm(info.getLoApplNm());
          rtnPg=init(req,res,session,util);
          }else
          rtnPg="sessionTO";
          if(!rtnPg.equals("sucess")){
              return am.findForward(rtnPg);   
          }else{
            AssortFinalRtnForm assortForm = (AssortFinalRtnForm)form;
            AssortFinalRtnInterface assortInt = new AssortFinalRtnImpl();
            util.updAccessLog(req,res,"Assort Final Return", "savePrpUpd");
            HashMap dbinfo = info.getDmbsInfoLst();
            String cnt = (String)dbinfo.get("CNT");
            String adv_pri = util.nvl((String)dbinfo.get("ADV_PRI"),"N");
            String prcId= (String)assortForm.getValue("prcId");
            String stt= (String)assortForm.getValue("stt");
            String issId = (String)assortForm.getValue("issIdn");
            String mstkIdn = (String)assortForm.getValue("mstkIdn");
            String apply=util.nvl((String) assortForm.getValue("apply"));
            String applyPri =util.nvl((String) assortForm.getValue("applyPri"));
            ArrayList ary = new ArrayList();
            HashMap mprp = info.getMprp();
            String lstNme = (String)assortForm.getValue("lstNme");
            HashMap stockList = (HashMap)gtMgr.getValue(lstNme);
            ArrayList stkIdnList = (ArrayList)gtMgr.getValue(lstNme+"_ALLLST"); 
            GtPktDtl pktDtl = (GtPktDtl)stockList.get(mstkIdn);
            
              
           int ct =0;
            String lastpage = util.nvl(req.getParameter("lastpage"));
            String currentpage = util.nvl(req.getParameter("currentpage"));
            if(lastpage.equals("") && currentpage.equals("")){
                lastpage = util.nvl((String)assortForm.getValue("lastpage"));
                currentpage = util.nvl((String)assortForm.getValue("currentpage"));
            }
            ArrayList assortPrpUpd = (ArrayList)session.getAttribute("assortUpdPrp");
            for(int i=0 ; i <assortPrpUpd.size();i++){
                
                String lprp = (String)assortPrpUpd.get(i);
                String fldVal =util.nvl((String)assortForm.getValue(mstkIdn+"_"+lprp));
               // String repVal =util.nvl((String)assortForm.getValue("RP_"+mstkIdn+"_"+lprp),"NA");
                
                if(!fldVal.equals("") && !fldVal.equals("0")){
                ary = new ArrayList();
                ary.add(issId);
                ary.add(mstkIdn);
                ary.add(lprp);
                ary.add(fldVal);
               // ary.add(repVal);
                 ct = db.execCall("updateAssortPrp", "ISS_RTN_PKG.RTN_PKT_PRP(pIssId => ?, pStkIdn =>?, pPrp =>?, pVal => ? )", ary);
    //            System.out.println("pktUpd:---"+ct);
                }
                
            }
            ArrayList prcPrpUpd = (ArrayList)session.getAttribute("prcPrpList");
            String isRepair = util.nvl((String)session.getAttribute("ISREPAIR"));
            if(isRepair.equals("Y")){
            for(int i=0 ; i < prcPrpUpd.size();i++){
                
                String lprp = (String)prcPrpUpd.get(i);
                String repVal =util.nvl((String)assortForm.getValue("RP_"+mstkIdn+"_"+lprp),"NA");
                String lprpTyp = util.nvl((String)mprp.get(lprp+"T"));
                if(lprpTyp.equals("N") && repVal.equals("NA"))
                repVal="";
                if(!repVal.equals("") && !repVal.equals("0")){
                ary = new ArrayList();
                ary.add(issId);
                ary.add(mstkIdn);
                ary.add(lprp);
                ary.add(repVal);
                 ct = db.execCall("updateRepPrp", "ISS_RTN_PKG.REP_PKT_PRP(pIssId => ?, pStkIdn =>?, pPrp =>?, pRepVal => ? )", ary);
                    
                }
              
            }}
              
                  if(!apply.equals("")){
                      ary = new ArrayList();
                             ary.add(issId);
                             ary.add(mstkIdn);
                             ct = db.execCall("apply_rtn_prp", "iss_rtn_pkg.apply_rtn_prp(?, ?, 'MOD')", ary);  
                             
//                             ary = new ArrayList();
//                             ary.add(mstkIdn);
//                             ct = db.execCall("apply_rtn_prp", "STK_SRT(?)", ary);
//                             ary = new ArrayList();
//                             ary.add(mstkIdn);
//                             ct = db.execCall("apply_rtn_prp", "STK_RAP_UPD(?)", ary);
//                             ary = new ArrayList();
//                             ary.add(mstkIdn);
//                             ct = db.execCall("apply_rtn_prp", "DP_UPD_MEAS(?)", ary);
//                             ary = new ArrayList();
//                             ary.add(mstkIdn);
//                             ct = db.execCall("apply_rtn_prp", "DP_UPD_LWR(?)", ary);
                            
                  }
                  if(!applyPri.equals("")){
                       ary = new ArrayList();
                      ary.add(issId);
                       ary.add(mstkIdn);
                      ct = db.execCall("apply_rtn_prp", "iss_rtn_pkg.apply_rtn_prp(?, ?, 'MOD')", ary);  

                      if(!adv_pri.equals("Y")){
                          ary = new ArrayList();
                          ary.add(mstkIdn);
                          ct = db.execCall("Itm_pri", "PRC.Itm_pri(?)", ary);
                      }
                   }          
                 if(cnt.equals("hk")){
                                 if(!adv_pri.equals("Y")){
                                 ArrayList grpList=util.sheetDtl(mstkIdn);
                                 String getmfgpri=util.GET_MFG_PRI(grpList);
                                 req.setAttribute("grpSheetList", grpList);
                                 String sh="",sz="",co="",clr="",cmp="",fa_pri="";
                                 float raprte=0,mfg_rte=0;
                                 String getPrcChkIdn = "select trunc(ms.rap_rte*(100 + "+getmfgpri+")/100,2) mfg_rte,ms.rap_rte,cmp.num cmp, fapri.num fa_pri\n" + 
                                 "from mstk ms,stk_dtl cmp,stk_dtl fapri\n" + 
                                 "where ms.idn= ? and\n" + 
                                 "ms.idn=cmp.mstk_idn and cmp.grp=1 and cmp.mprp='CMP_RTE' and \n" + 
                                 "ms.idn=fapri.mstk_idn and fapri.grp=1 and fapri.mprp='FA_PRI'";
                                 ary = new ArrayList();
                                 ary.add(mstkIdn);
                                   ArrayList  outLst = db.execSqlLst("CMP_RTE", getPrcChkIdn, ary);
                                   PreparedStatement pst = (PreparedStatement)outLst.get(0);
                                   ResultSet rs = (ResultSet)outLst.get(1);
                                 while(rs.next()) {
                                     cmp=util.nvl((String)rs.getString("cmp")); 
                                     fa_pri=util.nvl((String)rs.getString("fa_pri")); 
                                     mfg_rte = rs.getFloat("mfg_rte");
                                     raprte = rs.getFloat("rap_rte");
                                 }
                                 rs.close();
                                 pst.close();
                                 
                  //                ary = new ArrayList();
                  //                ary.add(sh);
                  //                ary.add(sz);
                  //                ary.add(co);
                  //                ary.add(clr);
                  //                ArrayList out = new ArrayList();
                  //                out.add("I");
                  //                out.add("I");
                  //                CallableStatement cst = db.execCall(
                  //                    "GET_NR_RTE ",
                  //                    "PRC.GET_NR_RTE(pShp => ?, pSz => ?, pCol => ?, pClr => ?, pRte => ?, pRapRte => ?)", ary, out);
                  //                mfg_rte = cst.getInt(ary.size()+1);
                  //                raprte = cst.getInt(ary.size()+2);
                  //                cst.close();
                                 
                                 assortForm.setValue("CMP_RTE_FIX", cmp);
                                 assortForm.setValue("FA_PRI_FIX", fa_pri);
                                 assortForm.setValue("MFG_RTE_FIX", mfg_rte);
                                 assortForm.setValue("RAP_RTE_FIX", raprte);
                                 }else{
                                     int getmfgpri=0;
                                     int getcmp=0;
                                     int pkt_rap_rte=0;
                                     String sheetappliedCmp="";
                                     String sheetappliedmfg="";
                                     String sh="",sz="",co="",clr="",cmp="",fa_pri="";
                                     float raprte=0,mfg_rte=0;
                                     ary = new ArrayList();
                                     ary.add(mstkIdn);
                                     ary.add("NR");
                                     ArrayList out = new ArrayList();
                                     out.add("I");
                                     out.add("I");
                                     out.add("V");
                                     out.add("V");
                                     out.add("I");
                                     CallableStatement cst = null;
                                     cst = db.execCall("dpp_pri.AryPri","dpp_pri.AryPri(pIdn =>?, pTyp =>?, NrmDis =>?, MfgDis => ?, StrDis =>?, StrMfg=>?,pRap =>?)", ary, out);
                                     getcmp = cst.getInt(ary.size()+1);
                                     getmfgpri = cst.getInt(ary.size()+2);
                                     sheetappliedCmp = cst.getString(ary.size()+3);
                                     sheetappliedmfg = cst.getString(ary.size()+4);
                                     pkt_rap_rte = cst.getInt(ary.size()+5);
                                   cst.close();
                                   cst=null;  
                                     
                                     ary = new ArrayList();
                                     ary.add(mstkIdn);
                                     ary.add("CMP_RTE");
                                     ary.add(String.valueOf(pkt_rap_rte));
                                     ary.add(String.valueOf(getcmp));
                                     String stockUpd = "stk_pkg.pkt_prp_upd(pIdn => ?, pPrp => ?, pVal=> Ceil(?*(100+?)/100) ,pPrpTyp => 'N', pgrp => 1 )";
                                     ct = db.execCall("stockUpd",stockUpd, ary);
                                     
                                     ary = new ArrayList();
                                     ary.add(mstkIdn);
                                     ary.add("CMP_DIS");
                                     ary.add(String.valueOf(getcmp));
                                     stockUpd = "stk_pkg.pkt_prp_upd(pIdn => ?, pPrp => ?, pVal=> ? ,pPrpTyp => 'N', pgrp => 1 )";
                                     ct = db.execCall("stockUpd",stockUpd, ary);
                                     
                                     String getPrcChkIdn = "select trunc(ms.rap_rte*(100 + "+getmfgpri+")/100,2) mfg_rte,ms.rap_rte,cmp.num cmp, fapri.num fa_pri\n" + 
                                     "from mstk ms,stk_dtl cmp,stk_dtl fapri\n" + 
                                     "where ms.idn= ? and\n" + 
                                     "ms.idn=cmp.mstk_idn and cmp.grp=1 and cmp.mprp='CMP_RTE' and \n" + 
                                     "ms.idn=fapri.mstk_idn and fapri.grp=1 and fapri.mprp='FA_PRI'";
                                     ary = new ArrayList();
                                     ary.add(mstkIdn);
                                   ArrayList  outLst = db.execSqlLst("CMP_RTE", getPrcChkIdn, ary);
                                   PreparedStatement pst = (PreparedStatement)outLst.get(0);
                                   ResultSet rs = (ResultSet)outLst.get(1);
                                     while(rs.next()) {
                                         cmp=util.nvl((String)rs.getString("cmp")); 
                                         fa_pri=util.nvl((String)rs.getString("fa_pri")); 
                                         mfg_rte = rs.getFloat("mfg_rte");
                                         raprte = rs.getFloat("rap_rte");
                                     }
                                     rs.close();
                                     pst.close();
                                     assortForm.setValue("CMP_RTE_FIX", cmp);
                                     assortForm.setValue("FA_PRI_FIX", fa_pri);
                                     assortForm.setValue("MFG_RTE_FIX", mfg_rte);
                                     assortForm.setValue("RAP_RTE_FIX", raprte);
                                     assortForm.setValue("APPLIED_MFG_SHEET", sheetappliedmfg);
                                 }
                 }
              
                             ary = new ArrayList();
                             ary.add(issId);
                             ary.add(mstkIdn);
                             ct = db.execUpd("update iss_rtn_dtl", "update iss_rtn_dtl set lot_id='Y' where iss_id=? and iss_stk_idn=?", ary) ;
                             pktDtl.setValue("rtnprpupdated", "Y");
              
                         HashMap params = new HashMap();
                         params.put("prcId", prcId);
                         params.put("issId", issId);
                         params.put("mstkIdn", mstkIdn);
                         req.setAttribute("PopUpidn", mstkIdn);
                         assortForm.setValue("lastpage", lastpage);
                         assortForm.setValue("currentpage", currentpage);
                         assortForm.setValue("isRepair", isRepair);
                         assortForm.setValue("stt", stt);
                         assortForm.setValue("url", "assortFinalRtn.do?method=stockUpd&mstkIdn="+mstkIdn+"&issIdn="+issId+"&isRepair="+isRepair+"&stt="+stt);
                         ArrayList stockPrpList = assortInt.StockUpdPrp(req,res , assortForm , params );
                         if(ct>0)
                         req.setAttribute( "msg","Propeties Get update successfully");
                         req.setAttribute("StockList", stockPrpList);
                         util.updAccessLog(req,res,"Assort Final Return", mstkIdn);
                         util.updAccessLog(req,res,"Assort Final Return", "End");
                         return am.findForward("loadStock");
                         }
    }
        
    
    
    public ActionForward Openpop(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res)
            throws Exception {
      HttpSession session = req.getSession(false);
      InfoMgr info = (InfoMgr)session.getAttribute("info");
      DBUtil util = new DBUtil();
      DBMgr db = new DBMgr();
      String rtnPg="sucess";
      if(info!=null){  
      db.setCon(info.getCon());
      util.setDb(db);
      util.setInfo(info);
      db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
      util.setLogApplNm(info.getLoApplNm());
      rtnPg=init(req,res,session,util);
      }else
      rtnPg="sessionTO";
      if(!rtnPg.equals("sucess")){
          return am.findForward(rtnPg);   
      }else{
        AssortFinalRtnForm assortForm = (AssortFinalRtnForm)af;

        util.updAccessLog(req,res,"Assort Final Return", "Openpop");
     
        ArrayList prpList=new ArrayList();
            String lstNme = req.getParameter("lstNme");
        String prcId = req.getParameter("prcID");
        String sql="select mprp from prc_prp_alw where prc_idn=? and flg <> 'FTCH' order by srt";
        ArrayList ary = new ArrayList();
        ary.add(prcId);
          ArrayList  outLst = db.execSqlLst("sql", sql, ary);
          PreparedStatement pst = (PreparedStatement)outLst.get(0);
          ResultSet rs = (ResultSet)outLst.get(1);
        while(rs.next()){
          String lprp = rs.getString("mprp");
           prpList.add(lprp);
            assortForm.setValue(lprp,"");
        }
        rs.close(); 
          pst.close();
        session.setAttribute("prpList",prpList);
            req.setAttribute("prcID", prcId);
              req.setAttribute("lstNme", lstNme);
        util.updAccessLog(req,res,"Assort Final Return", "prpList :"+prpList.size());
        util.updAccessLog(req,res,"Assort Final Return", "End");
        return am.findForward("multiPrp");
        }
    }
    public ActionForward Return(ActionMapping am,ActionForm form,HttpServletRequest req,HttpServletResponse res) throws Exception {
      HttpSession session = req.getSession(false);
      InfoMgr info = (InfoMgr)session.getAttribute("info");
      GtMgr gtMgr = (GtMgr)session.getAttribute("gtMgr");

      DBUtil util = new DBUtil();
      DBMgr db = new DBMgr();
      String rtnPg="sucess";
      if(info!=null){  
      db.setCon(info.getCon());
      util.setDb(db);
      util.setInfo(info);
      db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
      util.setLogApplNm(info.getLoApplNm());
      rtnPg=init(req,res,session,util);
      }else
      rtnPg="sessionTO";
      if(!rtnPg.equals("sucess")){
          return am.findForward(rtnPg);   
      }else{
        AssortFinalRtnForm assortForm = (AssortFinalRtnForm)form;
        String premktrpt = util.nvl((String)req.getParameter("premktrpt"));
        String reprc = util.nvl((String)assortForm.getValue("reprc"));
        if(!premktrpt.equals("")){
               return loadPreMktRpt(am, form, req, res);
        }else{  
        boolean isPri = false;
        util.updAccessLog(req,res,"Assort Final Return", "Return");
        if(((String)info.getDmbsInfoLst().get("CNT")).equalsIgnoreCase("JB") || ((String)info.getDmbsInfoLst().get("CNT")).equalsIgnoreCase("HK"))
            isPri = true;
        db.execUpd("delete gt_pkt", "delete from gt_pkt", new ArrayList());
        ArrayList RtnMsgList = new ArrayList();
        String msg = "";
        HashMap dbinfo = info.getDmbsInfoLst();
        int cnt = 1;
        String lstNme = (String)assortForm.getValue("lstNme");
        String insQ = " insert into gt_pkt(mstk_idn) select ? from dual ";

        ArrayList params = null;
        ArrayList returnPkt = new ArrayList();
          ArrayList selectList = (ArrayList)gtMgr.getValue(lstNme+"_SEL");
          HashMap stockList = (HashMap)gtMgr.getValue(lstNme);

          
            if(selectList!=null && selectList.size()>0) {
                for(int i=0;i<selectList.size();i++){
              String stkIdn = (String)selectList.get(i);
              GtPktDtl pktDtl = (GtPktDtl)stockList.get(stkIdn);
              String issId = pktDtl.getValue("issIdn");
              if(reprc.equals("")){    
                String lStkStt = util.nvl((String)assortForm.getValue("STT_"+stkIdn), "NA");
                ArrayList RtnMsg = new ArrayList();
                returnPkt.add(stkIdn);
                params = new ArrayList();
                params.add(issId);
                params.add(stkIdn);
                params.add("RT");
                params.add(lStkStt);
                ArrayList out = new ArrayList();
                out.add("I");
                out.add("V");
                String issuePkt = "ISS_RTN_PKG.RTN_PKT(pIssId => ?, pStkIdn => ? , pStt => ? , pStkStt => ? , pCnt=>? , pMsg => ? )";
                CallableStatement ct = db.execCall("issue Rtn", issuePkt, params, out);
                cnt = ct.getInt(5);
                msg = ct.getString(6);
                RtnMsg.add(cnt);
                RtnMsg.add(msg);
                RtnMsgList.add(RtnMsg);
                assortForm.setValue(stkIdn,"");
              if(cnt > 0 && isPri && !reprc.equals("")){
                  params = new ArrayList();
                  params.add(stkIdn);
                  int ct1 = db.execUpd(" reprc memo", insQ, params);
              }
              }else{
                    params = new ArrayList();
                    params.add(issId);
                    params.add(stkIdn);
                    int ctv = db.execCall("apply_rtn_prp", "iss_rtn_pkg.apply_rtn_prp(?, ?, 'MOD')", params);
                    
                    params = new ArrayList();
                    params.add(stkIdn);
                    ctv = db.execUpd("reprc", insQ, params);
                }
        }
            if(!reprc.equals("")){
                String  jbCntDb = "1";
                 ArrayList out = new ArrayList();
                 out.add("I");
                 String reprcProc = "reprc(num_job => ?, lstt1 => 'AS_PRC', lstt2 => 'FORM',lSeq=> ?)";
                 ArrayList reprcParams = new ArrayList();
                 int jobCnt = Integer.parseInt(jbCntDb);
                 reprcParams.add(String.valueOf(jobCnt));
                 int lseq = 0;
                 CallableStatement cnt1 = db.execCall(" reprc : ",reprcProc, reprcParams,out );
                 lseq = cnt1.getInt(reprcParams.size()+1);
                 req.setAttribute("seqNo","Current Repricing Sequence Number :  "+String.valueOf(lseq));
                isPri=false;
            }
        }
        if(isPri){
            String reprcp = "reprc(num_job => ?, lstt1 => ?, lstt2 => ?,lSeq=> ? )";
             params = new ArrayList();
             params.add("5");
             params.add("AS_PRC");
             params.add("FORM");
             ArrayList out = new ArrayList();
             out.add("I");
             CallableStatement ct = db.execCall(" reprc : ",reprcp, params,out);
            int  lseq = ct.getInt(params.size()+1);
            ct.close();
            ct=null;
        }
    if(returnPkt.size()>0)
        req.setAttribute("msgList",RtnMsgList);
        util.updAccessLog(req,res,"Assort Final Return", "End");
          gtMgr.reset();
        }
     return fecth(am, form, req, res);
        }
    }
    
    public ActionForward loadPreMktRpt(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res)
            throws Exception {
      HttpSession session = req.getSession(false);
      GtMgr gtMgr = (GtMgr)session.getAttribute("gtMgr");
      InfoMgr info = (InfoMgr)session.getAttribute("info");
      DBUtil util = new DBUtil();
      DBMgr db = new DBMgr();
      String rtnPg="sucess";
      if(info!=null){  
      db.setCon(info.getCon());
      util.setDb(db);
      util.setInfo(info);
      db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
      util.setLogApplNm(info.getLoApplNm());
      rtnPg=init(req,res,session,util);
      }else
      rtnPg="sessionTO";
      if(!rtnPg.equals("sucess")){
          return am.findForward(rtnPg);   
      }else{
              util.updAccessLog(req,res,"PriceRtn", "pktPtint");
              AssortFinalRtnForm assortForm = (AssortFinalRtnForm)af;
              String delQ = " Delete from mkt_prc ";
              int ct =db.execUpd(" Del Old Pkts ", delQ, new ArrayList());
              ArrayList ary = new ArrayList();
              HashMap dbinfo = info.getDmbsInfoLst();
              String lstNme = (String)assortForm.getValue("lstNme");
              ArrayList selectList = (ArrayList)gtMgr.getValue(lstNme+"_SEL");
              HashMap stockList = (HashMap)gtMgr.getValue(lstNme);
              String accessidn = util.nvl(req.getParameter("accessidn"));
              String cnt = (String)dbinfo.get("CNT");
              String webUrl = (String)dbinfo.get("REP_URL");
              String reqUrl = (String)dbinfo.get("HOME_DIR");
              String repPath = (String)dbinfo.get("REP_PATH");
              String repNme = "pre_mark.jsp";
              int mkt_prc = 0;
              ResultSet rs = db.execSql("mkt_prc", "select  seq_mkt_prc.nextval from dual ", new ArrayList());
              String insertPrc = " insert into mkt_prc(prc_idn ,mstk_idn ,rep_dte) \n"+
              "select ?, ?, sysdate from dual"; 
              if(rs.next())
              mkt_prc = rs.getInt(1);
              rs.close();
              String issIdnParam="";
          
              for(int m=0;m< selectList.size();m++){ 
                String stkIdn = (String)selectList.get(m);
                        GtPktDtl pktDtl = (GtPktDtl)stockList.get(stkIdn);
                        String iss_idn=util.nvl((String)pktDtl.getValue("issIdn"));
                        ary = new ArrayList();
                        ary.add(String.valueOf(mkt_prc));
                        ary.add(stkIdn);
                        ct = db.execUpd("insert mkt_prc", insertPrc, ary);
                        if(issIdnParam.equals(""))
                        issIdnParam=iss_idn;
                        else if(issIdnParam.indexOf(iss_idn) < 0)
                        issIdnParam+=","+iss_idn;
                  assortForm.setValue(stkIdn,"");
              }
              assortForm.setValue("lstNme", lstNme);
              if(ct>0){
                 String url = repPath+"/reports/rwservlet?"+cnt+"&report="+reqUrl+"\\reports\\"+repNme+"&P_ISS_ID="+issIdnParam+"&P_PRC_IDN="+String.valueOf(mkt_prc) ;
                  res.sendRedirect(url);    
              }
            return null;
        }
    }
  public ActionForward createXL(ActionMapping am,ActionForm form,HttpServletRequest req,HttpServletResponse res) throws Exception {
    HttpSession session = req.getSession(false);
    InfoMgr info = (InfoMgr)session.getAttribute("info");
    DBUtil util = new DBUtil();
    DBMgr db = new DBMgr();
    String rtnPg="sucess";
    if(info!=null){  
    db.setCon(info.getCon());
    util.setDb(db);
    util.setInfo(info);
    db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
    util.setLogApplNm(info.getLoApplNm());
    rtnPg=init(req,res,session,util);
    }else
    rtnPg="sessionTO";
    if(!rtnPg.equals("sucess")){
        return am.findForward(rtnPg);   
    }else{
        AssortFinalRtnForm assortForm = (AssortFinalRtnForm)form;

        String lstNme= (String)assortForm.getValue("lstNme");

          util.updAccessLog(req,res,"Assort Return", "createXL");
      String CONTENT_TYPE = "getServletContext()/vnd-excel";
      String fileNm = "AssortReturnExcel"+util.getToDteTime()+".xls";
      res.setContentType(CONTENT_TYPE);
      res.setHeader("Content-Disposition", "attachment;filename=" + fileNm);
      ArrayList itemHdr = (ArrayList)session.getAttribute("itemHdr");
        HashMap excelDtl = new HashMap();
        excelDtl.put("HDR", itemHdr);
        excelDtl.put("lstNme", lstNme);
      HSSFWorkbook hwb = null;
      ExcelUtil xlUtil = new ExcelUtil();
      xlUtil.init(db, util, info);
      hwb = xlUtil.getGenXlObj(excelDtl,req);
      OutputStream out = res.getOutputStream();
      hwb.write(out);
      out.flush();
      out.close();
          util.updAccessLog(req,res,"Assort Return", "End");
      return null;
      }
  }
  
    public ActionForward selectRtnPrppktFinalAsrt(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res) throws Exception {
        HttpSession session = req.getSession(false);
        InfoMgr info = (InfoMgr)session.getAttribute("info");
        GtMgr gtMgr = (GtMgr)session.getAttribute("gtMgr");
        DBUtil util = new DBUtil();
        DBMgr db = new DBMgr(); 
        db.setCon(info.getCon());
        util.setDb(db);
        util.setInfo(info);
        db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
        util.setLogApplNm(info.getLoApplNm());
        res.setContentType("text/xml"); 
        res.setHeader("Cache-Control", "no-cache"); 
        AssortFinalRtnForm assortForm = (AssortFinalRtnForm)af;
        AssortFinalRtnInterface assortInt = new AssortFinalRtnImpl();
        String lstNme = (String)assortForm.getValue("lstNme");
        HashMap stockList = (HashMap)gtMgr.getValue(lstNme);
        ArrayList stkIdnList = (ArrayList)gtMgr.getValue(lstNme+"_ALLLST"); 
        StringBuffer sb = new StringBuffer();
        boolean setdflt=false;
        if(stkIdnList.size()>0){
        for(int i=0;i<stkIdnList.size();i++){
            String mstkIdn = (String)stkIdnList.get(i);
            GtPktDtl pktDtl = (GtPktDtl)stockList.get(mstkIdn);
            String rtnprpupdated= util.nvl((String)pktDtl.getValue("rtnprpupdated"));
            if(rtnprpupdated.equals("Y")){
            sb.append("<detail>");
            sb.append("<stkidn>"+mstkIdn+"</stkidn>");
            sb.append("</detail>");
            setdflt=true;
            }
        }
        }
        
        if(!setdflt){
            sb.append("<detail>");
            sb.append("<stkidn>1</stkidn>");
            sb.append("</detail>"); 
        }
        res.getWriter().write("<details>" +sb.toString()+ "</details>");
        return null;
    }
  
    public AssortFinalRtnAction() {
        super();
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
              util.updAccessLog(req,res,"Assort Final Rtn", "Unauthorized Access");
              else
          util.updAccessLog(req,res,"Assort Final Rtn", "init");
          }
          }
          return rtnPg;
        }
//    public String init(HttpServletRequest req , HttpServletResponse res) {
//         
//        String rtnPg="sucess";
//        String invalide="";
//        session = req.getSession(false);
//        db = (DBMgr)session.getAttribute("db");
//        info = (InfoMgr)session.getAttribute("info");
//        util = (DBUtil)session.getAttribute("util");
//        assortInt = new AssortFinalRtnImpl();
//        if(util!=null){
//            String connExists=util.nvl(util.getConnExists());  
//            if(!connExists.equals("N"))
//            invalide=util.nvl(util.chkTimeOut(),"N");
//            if(session.isNew() || db==null || info==null)
//            rtnPg="sessionTO";   
//            if(connExists.equals("N"))
//            rtnPg="connExists";     
//            if(invalide.equals("Y"))
//            rtnPg="chktimeout";
//            if(rtnPg.equals("sucess")){
//                boolean sout=util.getLoginsession(req,res,session.getId());
//                if(!sout){
//                rtnPg="sessionTO";
//                System.out.print("New Session Id :="+session.getId());
//                }else{
//                util.updAccessLog(req,res,"Assort Final Return", "init");
//                }
//            }
//        }else{
//            rtnPg="sessionTO";
//        }
//        return rtnPg;
//        }
    
   
}

 

