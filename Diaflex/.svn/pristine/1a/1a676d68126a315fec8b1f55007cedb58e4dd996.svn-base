package ft.com.Repair;

import ft.com.DBMgr;
import ft.com.DBUtil;
import ft.com.InfoMgr;

import ft.com.assort.AssortReturnForm;
import ft.com.dao.Mprc;

import java.io.IOException;

import java.sql.CallableStatement;

import java.sql.Connection;

import java.sql.PreparedStatement;

import java.sql.ResultSet;

import java.util.ArrayList;

import java.util.HashMap;

import java.util.ArrayList;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

public class RepairingReturnAction extends DispatchAction {
    public ActionForward load(ActionMapping am,ActionForm form,HttpServletRequest req,HttpServletResponse res) throws Exception {
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
            util.getOpenCursorConnection(db,util,info);
            util.updAccessLog(req,res,"Repairing Return", "load start");
        RepairingReturnForm repairRtnForm = (RepairingReturnForm)form;
        RepairingReturnInterface repairInt=new RepairingReturnImpl();    
        repairRtnForm.reset();
        ArrayList empList = repairInt.getEmp(req,res);
        repairRtnForm.setEmpList(empList);
            util.updAccessLog(req,res,"Repairing Return", "load end");
     return am.findForward("load");
        }
    }
    
    public ActionForward fecth(ActionMapping am,ActionForm form,HttpServletRequest req,HttpServletResponse res) throws Exception {
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
            util.updAccessLog(req,res,"Repairing Return", "fetch start");
            RepairingReturnForm repairRtnForm = (RepairingReturnForm)form;
            RepairingReturnInterface repairInt=new RepairingReturnImpl(); 
       
        String stoneId = util.nvl((String)repairRtnForm.getValue("vnmLst"));
        String empId = util.nvl((String)repairRtnForm.getValue("empIdn"));
        String issueId = util.nvl((String)repairRtnForm.getValue("issueId"));
            String issStt = "'REP_IS','REP_RT'";
       String issueStr =  "select is_stt from iss_rtn r , mprc p\n" + 
       "where r.prc_id=p.idn and r.iss_id=?";
        ArrayList ary = new ArrayList();
        ary.add(issueId);
        ArrayList rsList = db.execSqlLst("issSql", issueStr, ary);
        PreparedStatement pst = (PreparedStatement)rsList.get(0);
        ResultSet rs = (ResultSet)rsList.get(1);
        while(rs.next()){
              issStt = issStt+",'"+rs.getString("is_stt")+"'" ;
         }
         rs.close();
        pst.close();
        session.setAttribute("AssortStockList", new ArrayList());
        HashMap params = new HashMap();
        params.put("stt",issStt );
        params.put("vnm",stoneId);
        params.put("empIdn", empId);
        params.put("issueId", issueId);
        ArrayList stockList = repairInt.FecthResult(req,res, params);
        int mprcIdn = repairInt.mprcIdn(req,res);
        if(stockList.size()>0){
        HashMap totals = repairInt.GetTotal(req,res);
        req.setAttribute("totalMap", totals);
            ArrayList options = repairInt.getOptions( req ,res ,mprcIdn);
           
            req.setAttribute("OPTIONS", options);

        }
        req.setAttribute("view", "Y");
       
        session.setAttribute("AssortStockList", stockList);
        repairRtnForm.setValue("prcId", String.valueOf(mprcIdn));
        HashMap allPageDtl= (info.getPageDetails() == null)?new HashMap():(HashMap)info.getPageDetails();
        HashMap pageDtl=(HashMap)allPageDtl.get("REPAIR_RETURN");
        if(pageDtl==null || pageDtl.size()==0){
        pageDtl=new HashMap();
        pageDtl=util.pagedef("REPAIR_RETURN");
        allPageDtl.put("REPAIR_RETURN",pageDtl);
        }
        info.setPageDetails(allPageDtl);
            util.updAccessLog(req,res,"Repairing Return", "fetch end");
        return am.findForward("load");
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
             util.updAccessLog(req,res,"Repairing Return", "stockUpd start");
             RepairingReturnForm repairRtnForm = (RepairingReturnForm)form;
             RepairingReturnInterface repairInt=new RepairingReturnImpl(); 
        String prcId= (String)repairRtnForm.getValue("prcId");
        String issId = req.getParameter("issIdn");
        String mstkIdn = req.getParameter("mstkIdn");
        HashMap params = new HashMap();
        params.put("prcId", prcId);
        params.put("issId", issId);
        params.put("mstkIdn", mstkIdn);
        
        ArrayList stockPrpList = repairInt.StockUpdPrp(req,res, repairRtnForm , params );
        req.setAttribute("StockList", stockPrpList);
         repairRtnForm.setValue("prcId", prcId);
        repairRtnForm.setValue("issIdn", issId);
        repairRtnForm.setValue("mstkIdn", mstkIdn);
             util.updAccessLog(req,res,"Repairing Return", "stockUpd end");
        return am.findForward("loadStock");
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
            util.updAccessLog(req,res,"Repairing Return", "savePrpUpd start");
            RepairingReturnForm repairRtnForm = (RepairingReturnForm)form;
            RepairingReturnInterface repairInt=new RepairingReturnImpl(); 
        String prcId= (String)repairRtnForm.getValue("prcId");
        String issId = (String)repairRtnForm.getValue("issIdn");
        String mstkIdn = (String)repairRtnForm.getValue("mstkIdn");
       
        ArrayList assortPrpUpd = (ArrayList)session.getAttribute("assortUpdPrp");
        for(int i=0 ; i <assortPrpUpd.size();i++){
            
            String lprp = (String)assortPrpUpd.get(i);
            String fldVal =(String)repairRtnForm.getValue(lprp);
            if(!fldVal.equals("") && !fldVal.equals("0") ){
            ArrayList ary = new ArrayList();
            ary.add(issId);
            ary.add(mstkIdn);
            ary.add(lprp);
            ary.add(fldVal);
            int ct = db.execCall("updateAssortPrp", "ISS_RTN_PKG.RTN_PKT_PRP(pIssId => ?, pStkIdn =>?, pPrp =>?, pVal => ? )", ary);
            }
            
        }
        
        HashMap params = new HashMap();
        params.put("prcId", prcId);
        params.put("issId", issId);
        params.put("mstkIdn", mstkIdn);
        
        ArrayList stockPrpList = repairInt.StockUpdPrp(req, res, repairRtnForm, params );
        req.setAttribute( "msg","Propeties Get update successfully");
        req.setAttribute("StockList", stockPrpList);
            util.updAccessLog(req,res,"Repairing Return", "savePrpUpd end");
        return am.findForward("loadStock");
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
            util.updAccessLog(req,res,"Repairing Return", "Return start");
            RepairingReturnForm repairRtnForm = (RepairingReturnForm)form;
            RepairingReturnInterface repairInt=new RepairingReturnImpl();
            ArrayList stkIdnList = new ArrayList();
            ArrayList issIdnList = new ArrayList();
            Enumeration reqNme = req.getParameterNames(); 
            while (reqNme.hasMoreElements()) {
                String paramNm = (String) reqNme.nextElement();
                if(paramNm.indexOf("cb_inv_") > -1) {
                    String val = req.getParameter(paramNm);
                    String[] valLst = val.split("_");
                    String iss_idn = valLst[0];
                    String stkIdn = valLst[1];
                    stkIdnList.add(stkIdn);
                    issIdnList.add(iss_idn);
                }}
        ArrayList RtnMsgList = new ArrayList();
        String msg = "";
        int cnt = 0;
        ArrayList params = null;
        String buttonPressed = "";
        ArrayList returnPkt = new ArrayList();
        if(!util.nvl((String)repairRtnForm.getValue("return")).equals(""))
        buttonPressed = "return";
        if (!util.nvl((String)repairRtnForm.getValue("transpertoprvprocess")).equals(""))
        buttonPressed = "transpertoprvprocess";
        if (!util.nvl((String)repairRtnForm.getValue("repairinstock")).equals(""))
        buttonPressed = "repairinstock";
            if(stkIdnList!=null && stkIdnList.size()>0){
        for(int i=0;i< stkIdnList.size();i++){
            String issIdn = (String)issIdnList.get(i);
             String stkIdn = (String)stkIdnList.get(i);
             ArrayList RtnMsg = new ArrayList();
                
                String lStkStt = util.nvl((String)repairRtnForm.getValue("STT_"+stkIdn), "NA");
                returnPkt.add(stkIdn);
                params=new ArrayList();
                params.add(issIdn);
                params.add(stkIdn);
                params.add("RT");
                if(buttonPressed.equals("transpertoprvprocess"))
                params.add("NA");
                else if(buttonPressed.equals("repairinstock"))
                params.add("MKAV");
                else
                params.add(lStkStt);
                ArrayList out = new ArrayList();
                out.add("I");
                out.add("V");
                String issuePkt = "ISS_RTN_PKG.RTN_PKT(pIssId => ?, pStkIdn => ? , pStt => ? ,  pStkStt => ? , pCnt=>? , pMsg => ? )";
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
        repairRtnForm.setValue("issue","");
        repairRtnForm.setValue("transpertoprvprocess","");
        util.updAccessLog(req,res,"Repairing Return", "Return end");
     return fecth(am, form, req, res);
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
                util.updAccessLog(req,res,"Repairing Return", "Unauthorized Access");
                else
                util.updAccessLog(req,res,"Repairing Return", "init");
            }
            }
            return rtnPg;
            }
    public RepairingReturnAction() {
        super();
    }
}
