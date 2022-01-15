package ft.com.lab;

import ft.com.DBMgr;
import ft.com.DBUtil;
import ft.com.ExcelUtil;
import ft.com.InfoMgr;

import ft.com.assort.AssortFinalRtnForm;
import ft.com.assort.AssortReturnForm;
import ft.com.dao.Mprc;

import ft.com.generic.GenericImpl;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import java.sql.CallableStatement;
import java.sql.ResultSet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.ArrayList;

import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class LabComparisonRtnAction extends DispatchAction{
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
            util.updAccessLog(req,res,"Lab Comparison Rtn", "load start");
        LabComparisonRtnForm comparisonForm = (LabComparisonRtnForm)af;
        LabComparisonRtnInterface comparisonInt=new LabComparisonRtnImpl();
        comparisonForm.resetAll();
        ArrayList empList = comparisonInt.getEmp(req,res);
        comparisonForm.setEmpList(empList);
        int prcId = 0;

            ArrayList outLst = db.execSqlLst("mprcId", "select idn from mprc where is_stt='LB_CHK_IS' and stt='A'", new ArrayList());
            PreparedStatement pst = (PreparedStatement)outLst.get(0);
            ResultSet rs = (ResultSet)outLst.get(1);
        if(rs.next()) {
         prcId = rs.getInt(1);
        }
        rs.close(); pst.close();
        comparisonForm.setPrcId(prcId);
            util.updAccessLog(req,res,"Lab Comparison Rtn", "load end");
        return am.findForward("load");
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
            util.updAccessLog(req,res,"Lab Comparison Rtn", "Openpop start");
            LabComparisonRtnForm comparisonForm = (LabComparisonRtnForm)af;
        ArrayList prpList=new ArrayList();
        
        String prcId = req.getParameter("prcID");
        String sql="select mprp from prc_prp_alw where prc_idn=? and flg='EDIT' order by srt";
        ArrayList ary = new ArrayList();
        ary.add(prcId);

            ArrayList outLst = db.execSqlLst("sql", sql, ary);
            PreparedStatement pst = (PreparedStatement)outLst.get(0);
            ResultSet rs = (ResultSet)outLst.get(1);
        while(rs.next()){
          String lprp = rs.getString("mprp");
           prpList.add(lprp);
            comparisonForm.setValue(lprp,"");
        }
        rs.close(); pst.close();
        session.setAttribute("prpList",prpList);
            util.updAccessLog(req,res,"Lab Comparison Rtn", "Openpop end");
        return am.findForward("multiPrp");
        }
    }
    public ActionForward update(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res)
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
            util.updAccessLog(req,res,"Lab Comparison Rtn", "update start");
        boolean isUpdate = false;
            LabComparisonRtnForm comparisonForm = (LabComparisonRtnForm)af;
        HashMap mprp = info.getMprp();
        ArrayList prpUpdList = info.getPrpUpdTempList();
        ArrayList viewPrp = (ArrayList)session.getAttribute("MULT_PKT_UPD");
        ArrayList stockList = (ArrayList)session.getAttribute("LabStockList");
        for(int i=0;i< stockList.size();i++){
            HashMap stockPkt = (HashMap)stockList.get(i);
             String stkIdn = (String)stockPkt.get("stk_idn");
             String lab = (String)stockPkt.get("cert_lab");
              String issueId = (String)stockPkt.get("issIdn");
            if(prpUpdList.contains(stkIdn)){
                for(int j=0 ; j<viewPrp.size();j++){
                String lprp = (String)viewPrp.get(j);
                String lprpTyp = util.nvl((String)mprp.get(lprp+"T"));
                String fldVal = util.nvl((String)comparisonForm.getValue(lprp));
                if(!fldVal.equals("") && !fldVal.equals("0")){
                     ArrayList ary = new ArrayList();
                     ary.add(stkIdn);
                     ary.add(lprp);
                     ary.add(fldVal);
                     ary.add(lab);
                     ary.add(lprpTyp);
                     String stockUpd = "stk_pkg.pkt_prp_upd(pIdn => ?, pPrp => ?, pVal=> ? , pLab=> ?, pPrpTyp => ? )";
                   int ct = db.execCall("stockUpd",stockUpd, ary);
                     ary = new ArrayList();
                     ary.add(issueId);
                     ary.add(stkIdn);
                     ary.add(lprp);
                     ary.add(fldVal);
                      ct = db.execCall("updateAssortPrp", "ISS_RTN_PKG.RTN_PKT_PRP(pIssId => ?, pStkIdn =>?, pPrp =>?, pVal => ? )", ary);
                     
                   isUpdate = true;
                 }
           
        }}}
        info.setPrpUpdTempList(new ArrayList());
        if(isUpdate)
        req.setAttribute("msg","Propeties Get update successfully");
            util.updAccessLog(req,res,"Lab Comparison Rtn", "update end");
        return am.findForward("multiPop");
        }
    }
    
    public ActionForward stockUpd(ActionMapping am,ActionForm form,HttpServletRequest req,HttpServletResponse res) throws Exception {
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
             util.updAccessLog(req,res,"Lab Comparison Rtn", "stockUpd start");
             LabComparisonRtnForm comparisonForm = (LabComparisonRtnForm)form;
             LabComparisonRtnInterface comparisonInt=new LabComparisonRtnImpl();
        String prcId= (String)comparisonForm.getValue("prcId");
       
        String issId = req.getParameter("issIdn");
        String mstkIdn = req.getParameter("mstkIdn");
        HashMap params = new HashMap();
        params.put("prcId", prcId);
        params.put("issId", issId);
        params.put("mstkIdn", mstkIdn);
       
        ArrayList stockPrpList = comparisonInt.StockUpdPrp(req,res, comparisonForm , params);
        req.setAttribute("StockList", stockPrpList);
        comparisonForm.setValue("prcId", prcId);
        comparisonForm.setValue("issIdn", issId);
        comparisonForm.setValue("mstkIdn", mstkIdn);
        req.setAttribute("mstkIdn", mstkIdn);
             util.updAccessLog(req,res,"Lab Comparison Rtn", "stockUpd end");
        return am.findForward("loadStock");
         }
     }
    
    public ActionForward bulkUpdate(ActionMapping am,ActionForm form,HttpServletRequest req,HttpServletResponse res) throws Exception {
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
            util.updAccessLog(req,res,"Lab Comparison Rtn", "bulkUpdate start");
            LabComparisonRtnForm comparisonForm = (LabComparisonRtnForm)form;
        ResultSet rs = null;
        int ct=0;
        ArrayList ary = new ArrayList();
        ArrayList prpList = (ArrayList)session.getAttribute("prpList");
        String prcId= (String)comparisonForm.getValue("prcId");
        String grp = "select grp from mprc where idn=?";
        ary = new ArrayList();
        ary.add(prcId);

            ArrayList outLst = db.execSqlLst("grp", grp, ary);
            PreparedStatement pst = (PreparedStatement)outLst.get(0);
            rs = (ResultSet)outLst.get(1);
        String grpVal="";
        if(rs.next())
        grpVal = rs.getString("grp");
        rs.close(); pst.close();
        ArrayList prpUpdList = info.getPrpUpdTempList();
        for(int i=0 ; i < prpUpdList.size() ; i++){
            String stkIdn = util.nvl((String)prpUpdList.get(i));
            ary = new ArrayList();
            ary.add(stkIdn);
            ary.add(grpVal);
            int issIdn =0;
            String getIssIdn = "select ISS_RTN_PKG.LST_ISS_ID(?,?) issIdn from dual";

            outLst = db.execSqlLst("issIdn", getIssIdn, ary);
            pst = (PreparedStatement)outLst.get(0);
            rs = (ResultSet)outLst.get(1);
             if(rs.next())
             issIdn = rs.getInt(1);
            rs.close(); pst.close();
            for(int j=0 ; j< prpList.size() ; j++){
            String lprp = (String)prpList.get(j);
            String fldVal =util.nvl((String)comparisonForm.getValue(lprp));
            if(!fldVal.equals("") && !fldVal.equals("0")){
             ary = new ArrayList();
             ary.add(String.valueOf(issIdn));
             ary.add(stkIdn);
             ary.add(lprp);
             ary.add(fldVal);
           ct = db.execCall("updateAssortPrp", "ISS_RTN_PKG.RTN_PKT_PRP(pIssId => ?, pStkIdn =>?, pPrp =>?, pVal => ? )", ary);
           
            }}
        }
        if(ct>0)
            req.setAttribute( "msg","Propeties Get update successfully");
            util.updAccessLog(req,res,"Lab Comparison Rtn", "bulkUpdate end");
         return am.findForward("multiPrp");
        }
    }
    public ActionForward savePrpUpd(ActionMapping am,ActionForm form,HttpServletRequest req,HttpServletResponse res) throws Exception {
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
            util.updAccessLog(req,res,"Lab Comparison Rtn", "savePrpUpd start");
            LabComparisonRtnForm comparisonForm = (LabComparisonRtnForm)form;
            LabComparisonRtnInterface comparisonInt=new LabComparisonRtnImpl();
        String prcId= (String)comparisonForm.getValue("prcId");
        String issId = (String)comparisonForm.getValue("issIdn");
        String mstkIdn = (String)comparisonForm.getValue("mstkIdn");
        int ct =0 ;
        ArrayList assortPrpUpd = (ArrayList)session.getAttribute("assortUpdPrp");
        for(int i=0 ; i <assortPrpUpd.size();i++){
            
            String lprp = (String)assortPrpUpd.get(i);
            String fldVal =util.nvl((String) comparisonForm.getValue(mstkIdn+"_"+lprp));
            if(!fldVal.equals("") && !fldVal.equals("0")){
            ArrayList ary = new ArrayList();
            ary.add(issId);
            ary.add(mstkIdn);
            ary.add(lprp);
            ary.add(fldVal);
            ct = db.execCall("updateAssortPrp", "ISS_RTN_PKG.RTN_PKT_PRP(pIssId => ?, pStkIdn =>?, pPrp =>?, pVal => ? )", ary);
            System.out.println("pktUpd:---"+ct);
            }
            
        }
        
        HashMap params = new HashMap();
        params.put("prcId", prcId);
        params.put("issId", issId);
        params.put("mstkIdn", mstkIdn);
        
        ArrayList stockPrpList =  comparisonInt.StockUpdPrp(req,res ,comparisonForm , params );
        if(ct>0)
        req.setAttribute("msg","Propeties Get update successfully");
        req.setAttribute("StockList", stockPrpList);
        req.setAttribute("mstkIdn", mstkIdn);
            util.updAccessLog(req,res,"Lab Comparison Rtn", "savePrpUpd end");
        return am.findForward("loadStock");
        }
    }
    public ActionForward view(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res)
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
            util.updAccessLog(req,res,"Lab Comparison Rtn", "view start");
            LabComparisonRtnForm comparisonForm = (LabComparisonRtnForm)af;
            LabComparisonRtnInterface comparisonInt=new LabComparisonRtnImpl();
        int prcId = comparisonForm.getPrcId();
        String stoneId = util.nvl((String)comparisonForm.getValue("vnmLst"));
        String empId = util.nvl((String)comparisonForm.getValue("empIdn"));
        String issueId = util.nvl((String)comparisonForm.getValue("issueId"));
      
        HashMap params = new HashMap();
        params.put("stt", "LB_CHK_IS");
        params.put("vnm",stoneId);
        params.put("empIdn", empId);
        params.put("issueId", issueId);
        params.put("mprcIdn", String.valueOf(prcId));
        ArrayList stockList = comparisonInt.FecthResult(req,res, params);
        if(stockList.size()>0){
        HashMap totals = comparisonInt.GetTotal(req,res);
        req.setAttribute("totalMap", totals);
            ArrayList options = comparisonInt.getOptionsPrc(req ,res, String.valueOf(prcId));
          
            req.setAttribute("OPTIONS", options);
        }
        req.setAttribute("view", "Y");
        comparisonForm.setValue("prcId", String.valueOf(prcId));
        comparisonForm.setValue("empId", empId);
        session.setAttribute("LabStockList", stockList);
        req.setAttribute("prcId", String.valueOf(prcId));
        info.setPrpUpdTempList(new ArrayList());
        
        HashMap allPageDtl= (info.getPageDetails() == null)?new HashMap():(HashMap)info.getPageDetails();
        HashMap pageDtl=(HashMap)allPageDtl.get("LAB_FINALRETURN");
        if(pageDtl==null || pageDtl.size()==0){
        pageDtl=new HashMap();
        pageDtl=util.pagedef("LAB_FINALRETURN");
        allPageDtl.put("LAB_FINALRETURN",pageDtl);
        }
        info.setPageDetails(allPageDtl);
            util.updAccessLog(req,res,"Lab Comparison Rtn", "view end");
       return am.findForward("load");
        }
    }
    
    public ActionForward labComExcel(ActionMapping am,ActionForm form,HttpServletRequest req,HttpServletResponse res) throws Exception {
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
            util.updAccessLog(req,res,"Lab Comparison Rtn", "labComExcel start");
        int ct = db.execUpd("delete gt_srch", "Delete from gt_srch_rslt", new ArrayList());
        ct =db.execUpd(" Del Old Pkts ", "Delete from gt_srch_multi", new ArrayList());
        ArrayList selectList = info.getPrpUpdTempList();
        String vnmPcs = selectList.toString();
        vnmPcs = vnmPcs.replace('[','(');
        vnmPcs = vnmPcs.replace(']' ,')');
        
        String srchRefQ = 
        "    Insert into gt_srch_rslt (pkt_ty, stk_idn, vnm, pkt_dte, stt , flg , qty , cts , sk1 , rmk , srch_id , cert_lab ) " + 
        " select  b.pkt_ty, b.idn, b.vnm,  b.dte, b.stt , 'M' , b.qty , b.cts , b.sk1 , b.tfl3  , a.iss_id , cert_lab   "+
        "     from mstk b , "+
         " iss_rtn_dtl a , mx_lab_iss_v m where b.idn = a.iss_stk_idn and a.iss_id = m.iss_id and a.iss_stk_idn = m.iss_stk_idn "+
         " and b.idn in "+vnmPcs ;
        ct = db.execUpd(" Srch Prp ", srchRefQ, new ArrayList());
        ArrayList ary=new ArrayList();
        ExcelUtil xlUtil = new ExcelUtil();
        xlUtil.init(db, util, info);
        String pktPrp = "SRCH_PKG.POP_PKT_PRP(?)";
         ary = new ArrayList();
         ary.add("LAB_RS");
         ct = db.execCall(" Srch Prp ", pktPrp, ary);
         HSSFWorkbook hwb = xlUtil.GetLabComparisionExcel(req);
         OutputStream out = res.getOutputStream();
         String CONTENT_TYPE = "getServletContext()/vnd-excel";
         String fileNm = "LabFinalReturnComparision"+util.getToDteTime()+".xls";
         res.setContentType(CONTENT_TYPE);
         res.setHeader("Content-Disposition", "attachment;filename=" + fileNm);
         hwb.write(out);
         out.flush();
         out.close();
            util.updAccessLog(req,res,"Lab Comparison Rtn", "labComExcel end");
        return null;
        }
    }
    
    public ActionForward Return(ActionMapping am,ActionForm form,HttpServletRequest req,HttpServletResponse res) throws Exception {
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
            util.updAccessLog(req,res,"Lab Comparison Rtn", "return start");
            LabComparisonRtnForm comparisonForm = (LabComparisonRtnForm)form;
        ArrayList RtnMsgList = new ArrayList();
        String msg = "";
        int cnt = 0;
        ArrayList params = null;
        ArrayList returnPkt = new ArrayList();
        ArrayList stockList = (ArrayList)session.getAttribute("LabStockList");
        for(int i=0;i< stockList.size();i++){
            HashMap stockPkt = (HashMap)stockList.get(i);
             String stkIdn = (String)stockPkt.get("stk_idn");
             String isChecked = util.nvl((String)comparisonForm.getValue(stkIdn));
            if(isChecked.equals("yes")){
                ArrayList RtnMsg = new ArrayList();
                returnPkt.add(stkIdn);
                String lStkStt = util.nvl((String)comparisonForm.getValue("STT_"+stkIdn), "NA");
                params = new ArrayList();
                params.add(stockPkt.get("issIdn"));
                params.add(stkIdn);
                params.add("RT");
                params.add(lStkStt);
                ArrayList out = new ArrayList();
                out.add("I");
                out.add("V");
                String issuePkt ="ISS_RTN_PKG.RTN_PKT(pIssId => ?, pStkIdn => ? , pStt => ? , pStkStt => ? ,  pCnt=>? , pMsg => ? )";
                CallableStatement ct = db.execCall("issue Rtn", issuePkt, params, out);
                cnt = ct.getInt(5);
                msg = ct.getString(6);
                RtnMsg.add(cnt);
                RtnMsg.add(msg);
                RtnMsgList.add(RtnMsg);
                ct.close();
            }
       }
       
        
        
    if(returnPkt.size()>0)
        req.setAttribute("msgList",RtnMsgList);
     else
        comparisonForm.reset();
            util.updAccessLog(req,res,"Lab Comparison Rtn", "return end");
     return view(am, form, req, res);
        }
    }
    public ActionForward returnExcel(ActionMapping am,ActionForm form,HttpServletRequest req,HttpServletResponse res) throws Exception {
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
            util.updAccessLog(req,res,"Lab Comparison Rtn", "returnExcel start");
            LabComparisonRtnForm comparisonForm = (LabComparisonRtnForm)form;
            LabResultInterface labResultInt=new LabResultImpl();
        HashMap dbinfo = info.getDmbsInfoLst();
        String zipallowyn = util.nvl((String)dbinfo.get("ZIP_DWD_YN"),"N"); 
            int zipdwnsz = Integer.parseInt(util.nvl((String)dbinfo.get("ZIP_DWD_SZ"),"100")); 
        ExcelUtil xlUtil = new ExcelUtil();
        xlUtil.init(db, util, info);
        ArrayList pktDtl = labResultInt.pktList(req, res,"Z");
        HSSFWorkbook hwb = null;
        ArrayList itemHdr = (ArrayList)req.getAttribute("ItemHdr");
        hwb =  xlUtil.getGenXl(itemHdr, pktDtl);
            int pktListsz=pktDtl.size();
            if(zipallowyn.equals("Y") && pktListsz>zipdwnsz){
            String contentTypezip = "application/zip";
            String fileNmzip = "AssotLabCompExcel"+util.getToDteTime();
            OutputStream outstm = res.getOutputStream();
            ZipOutputStream zipOut = new ZipOutputStream(outstm);
            ZipEntry entry = new ZipEntry(fileNmzip+".xls");
            zipOut.putNextEntry(entry);
            res.setHeader("Content-Disposition","attachment; filename="+fileNmzip+".zip");
            res.setContentType(contentTypezip);
              ByteArrayOutputStream bos = new ByteArrayOutputStream();
               hwb.write(bos);
               bos.writeTo(zipOut);      
              zipOut.closeEntry();
               zipOut.flush();
               zipOut.close();
               outstm.flush();
               outstm.close();  
            }else{
            String CONTENT_TYPE = "getServletContext()/vnd-excel";
            String fileNm = "AssotLabCompExcel"+util.getToDteTime()+".xls";
            res.setContentType(CONTENT_TYPE);
            res.setHeader("Content-Disposition", "attachment;filename=" + fileNm);
            OutputStream out = res.getOutputStream();
            hwb.write(out);
            out.flush();
            out.close();
            }
            util.updAccessLog(req,res,"Lab Comparison Rtn", "returnExcel end");
        return null;
        }
    }
    
    
    public ActionForward excel(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res)
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
              util.updAccessLog(req,res,"Lab Comparison Rtn", "excel start");
          int ct = db.execUpd("delete gt_srch", "Delete from gt_srch_rslt", new ArrayList());
          ct =db.execUpd(" Del Old Pkts ", "Delete from gt_srch_multi", new ArrayList());
          ArrayList selectList = info.getPrpUpdTempList();
          String vnmPcs = selectList.toString();
          vnmPcs = vnmPcs.replace('[','(');
          vnmPcs = vnmPcs.replace(']',')');
        
          String srchRefQ = 
          "    Insert into gt_srch_rslt (pkt_ty, stk_idn, vnm, pkt_dte, stt , flg , qty , cts , sk1 , rmk , srch_id , cert_lab , rap_rte ) " + 
          " select  b.pkt_ty, b.idn, b.vnm,  b.dte, b.stt , 'M' , b.qty , b.cts , b.sk1 , b.tfl3  , a.iss_id , cert_lab , rap_rte   "+
          "     from mstk b , "+
           " iss_rtn_dtl a where b.idn = a.iss_stk_idn and a.stt='IS'  "+
           " and b.idn in "+vnmPcs ;
          ct = db.execUpd(" Srch Prp ", srchRefQ, new ArrayList());
          String pktPrp = "pkgmkt.pktPrp(0,?)";
         ArrayList  ary = new ArrayList();
          ary.add("LAB_FNL_XL");
          ct = db.execCall(" Srch Prp ", pktPrp, ary);
          
         String CONTENT_TYPE = "getServletContext()/vnd-excel";
          String fileNm = "LabInExcel"+util.getToDteTime()+".xls";
          HSSFWorkbook hwb = new HSSFWorkbook();
          res.setContentType(CONTENT_TYPE);
          res.setHeader("Content-Disposition", "attachment;filename=" + fileNm);
          ExcelUtil excelUtil = new ExcelUtil();
          excelUtil.init(db, util, info);
          HashMap dbinfo = info.getDmbsInfoLst();
          String cnt = (String)dbinfo.get("CNT");
          OutputStream out = res.getOutputStream();
          
          
          hwb = excelUtil.getInXl("memo", req, "LAB_FNL_XL");
          hwb.write(out);
          out.flush();
          out.close();
              util.updAccessLog(req,res,"Lab Comparison Rtn", "excel end");
          return null;
          }
      }
    
    public ActionForward comExcel(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res)
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
              util.updAccessLog(req,res,"Lab Comparison Rtn", "comExcel start");
          int ct = db.execUpd("delete gt_srch", "Delete from gt_srch_rslt", new ArrayList());
          ct =db.execUpd(" Del Old Pkts ", "Delete from gt_srch_multi", new ArrayList());
          ArrayList selectList = info.getPrpUpdTempList();
          String vnmPcs = selectList.toString();
          vnmPcs = vnmPcs.replace('[','(');
          vnmPcs = vnmPcs.replace(']' ,')');
          
          String srchRefQ = 
          "    Insert into gt_srch_rslt (pkt_ty, stk_idn, vnm, pkt_dte, stt , flg , qty , cts , sk1 , rmk , srch_id , cert_lab ) " + 
          " select  b.pkt_ty, b.idn, b.vnm,  b.dte, b.stt , 'M' , b.qty , b.cts , b.sk1 , b.tfl3  , a.iss_id , cert_lab   "+
          "     from mstk b , "+
           " iss_rtn_dtl a where b.idn = a.iss_stk_idn and a.stt='IS'  "+
           " and b.idn in "+vnmPcs ;
          ct = db.execUpd(" Srch Prp ", srchRefQ, new ArrayList());
         
          String mutiPRp ="Lab_pkg.Multi_Comp(?,?)";
         ArrayList  ary = new ArrayList();
          ary.add("LAB_COM_XL");
          ary.add("-1");
          ct = db.execCall("Multi Comp", mutiPRp, ary);
         String CONTENT_TYPE = "getServletContext()/vnd-excel";
          String fileNm = "LabInExcel"+util.getToDteTime()+".xls";
          HSSFWorkbook hwb = new HSSFWorkbook();
          res.setContentType(CONTENT_TYPE);
          res.setHeader("Content-Disposition", "attachment;filename=" + fileNm);
          ExcelUtil excelUtil = new ExcelUtil();
          excelUtil.init(db, util, info);
          HashMap dbinfo = info.getDmbsInfoLst();
          String cnt = (String)dbinfo.get("CNT");
          OutputStream out = res.getOutputStream();
          hwb = excelUtil.getInXl("memo", req, "LAB_COM_XL");
          ct = db.execUpd("delete gt_multi", "Delete from gt_srch_multi", new ArrayList());
          hwb.write(out);
          out.flush();
          out.close();
              util.updAccessLog(req,res,"Lab Comparison Rtn", "comExcel end");
          return null;
          }
        
      }
    public LabComparisonRtnAction() {
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
                util.updAccessLog(req,res,"Lab Comparison Rtn", "Unauthorized Access");
                else
            util.updAccessLog(req,res,"Lab Comparison Rtn", "init");
            }
            }
            return rtnPg;
            }
}
