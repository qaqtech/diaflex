package ft.com.inward;

//~--- non-JDK imports --------------------------------------------------------

import ft.com.DBMgr;
import ft.com.DBUtil;
import ft.com.InfoMgr;
import ft.com.dao.MNme;
import ft.com.dao.Mprc;

import java.sql.Connection;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

//~--- JDK imports ------------------------------------------------------------

import java.sql.ResultSet;

import java.util.ArrayList;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import javax.xml.ws.Dispatch;

public class IssueResultAction extends DispatchAction {

    public IssueResultAction() {
        super();
    }

    public ActionForward load(ActionMapping am, ActionForm form, HttpServletRequest req, HttpServletResponse res)
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

        ResultSet rs = null;

        IssueResultForm issueFrm = (IssueResultForm) form;

        ArrayList mprcList = new ArrayList();
        String    mprcLst  = "select idn , prc from mprc where vld_till is null";

        rs = db.execSql("mprc", mprcLst, new ArrayList());

        while (rs.next()) {
            Mprc mprc = new Mprc();

            mprc.setMprcId(rs.getString("idn"));
            mprc.setPrc(rs.getString("prc"));
            mprcList.add(mprc);
        }
        rs.close();
        issueFrm.setMprcLst(mprcList);

        ArrayList emeList = new ArrayList();
        String    empSql  = "select nme_idn , nme from nme_v where typ = 'EMPLOYEE'";

        rs = db.execSql("empLst", empSql, new ArrayList());

        while (rs.next()) {
            MNme nme = new MNme();

            nme.setEmp_idn(rs.getString("nme_idn"));
            nme.setEmp_nme(rs.getString("nme"));
            emeList.add(nme);
        }
        rs.close();
        issueFrm.setNmeLst(emeList);

        return am.findForward("load");
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
        util.updAccessLog(req,res,"Action", "Unauthorized Access");
        else
    util.updAccessLog(req,res,"Action", "init");
    }
    }
    return rtnPg;
    }
}


//~ Formatted by Jindent --- http://www.jindent.com
