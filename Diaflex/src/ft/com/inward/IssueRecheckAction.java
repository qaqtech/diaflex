package ft.com.inward;

import ft.com.DBMgr;
import ft.com.DBUtil;
import ft.com.InfoMgr;
import ft.com.assort.AssortIssueForm;

import ft.com.assort.AssortIssueImpl;
import ft.com.assort.AssortIssueInterface;

import java.io.IOException;

import java.sql.Connection;

import java.util.ArrayList;
import java.util.HashMap;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

public class IssueRecheckAction extends DispatchAction {
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
         util.updAccessLog(req,res,"Issue Recheck", "load start");
         IssueRecheckFrom issueForm = (IssueRecheckFrom)form;
         issueForm.reset();
         util.updAccessLog(req,res,"Issue Recheck", "load end");
        return am.findForward("load");
        }
    }
    public ActionForward view(ActionMapping am,ActionForm form,HttpServletRequest req,HttpServletResponse res) throws Exception {
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
        util.updAccessLog(req,res,"Issue Recheck", "view start");
        IssueRecheckFrom issueForm = (IssueRecheckFrom)form;
        IssueRecheckInterface issueInt=new IssueRecheckImpl() ;
        String issueId = util.nvl((String)issueForm.getValue("issueId"));
        String stoneId = util.nvl((String)issueForm.getValue("vnmLst"));
        HashMap params = new HashMap();
        params.put("issueId", issueId);
        params.put("vnm", stoneId);
        ArrayList stockList = issueInt.FecthResult(req,res, params);
        if(stockList.size()>0){
        HashMap totals = issueInt.GetTotal(req,res);
        req.setAttribute("totalMap", totals);
        }
        session.setAttribute("StockList", stockList);
        issueForm.setValue("issueId", issueId);
        req.setAttribute("view", "Y");
        util.updAccessLog(req,res,"Issue Recheck", "view end");
        return am.findForward("load");
        }
    }
    public ActionForward cancel(ActionMapping am,ActionForm form,HttpServletRequest req,HttpServletResponse res) throws Exception {
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
            util.updAccessLog(req,res,"Issue Recheck", "cancel start");
            IssueRecheckFrom issueForm = (IssueRecheckFrom)form;
        String cancalAll = util.nvl((String)issueForm.getValue("cancalAll"));
        String cancal = util.nvl((String)issueForm.getValue("cancal"));
        String issueId = util.nvl((String)issueForm.getValue("issueId"));
        ArrayList params = new ArrayList();
        int ct = 0;
        if(!cancalAll.equals("")){
            params = new ArrayList();
            params.add(issueId);
            ct = db.execCall("ISS_RTN_PKG", "ISS_RTN_PKG.ISS_CNCL(pIssId=>?)", params);
        }else{
            ArrayList stockList = (ArrayList)session.getAttribute("StockList");
            for(int i=0;i< stockList.size();i++){
                HashMap stockPkt = (HashMap)stockList.get(i);
                 String stkIdn = (String)stockPkt.get("stk_idn");
                String issIdn = (String)stockPkt.get("issIdn");
                 String isChecked = util.nvl((String)issueForm.getValue(stkIdn));
                if(isChecked.equals("yes")){
                    params = new ArrayList();
                    params.add(issIdn);
                    params.add(stkIdn);
                    ct = db.execCall("ISS_RTN_PKG", "ISS_RTN_PKG.ISS_CNCL_PKT(pIssId=>? , pStkIdn=> ?)", params);
                }
                }
        }
        req.setAttribute( "msg","Process done successfully");
        issueForm.reset();
            util.updAccessLog(req,res,"Issue Recheck", "cancel end");
        return am.findForward("load");
        }
    }
    public IssueRecheckAction() {
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
        util.updAccessLog(req,res,"Issue Recheck", "Unauthorized Access");
        else
        util.updAccessLog(req,res,"Issue Recheck", "init");
    }
    }
    return rtnPg;
    }
}
