package ft.com.contact;

import java.sql.ResultSet;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import ft.com.DBMgr;
import ft.com.DBUtil;
import ft.com.InfoMgr;

import ft.com.dao.TrmsDao;

import java.io.IOException;

import java.net.URLEncoder;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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

public class AjaxContactsAction extends DispatchAction {
 public ActionForward verifyName(ActionMapping mapping, ActionForm form,
                            HttpServletRequest req,
                            HttpServletResponse response) throws Exception {
      
        HttpSession session = req.getSession(false);
        InfoMgr info = (InfoMgr)session.getAttribute("info");
        DBUtil util = new DBUtil();
        DBMgr db = new DBMgr(); 
        db.setCon(info.getCon());
        util.setDb(db);
        util.setInfo(info);
        db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
        util.setLogApplNm(info.getLoApplNm());
    StringBuffer sb = new StringBuffer();
    String usr = req.getParameter("usr");
    ArrayList ary = new ArrayList();
    String sql = " select usr from web_usrs where upper(usr)=upper(?)" ;
    ary.add(usr);

        ArrayList outLst = db.execSqlLst("Verify Name", sql, ary);
        PreparedStatement pst = (PreparedStatement)outLst.get(0);
        ResultSet rs = (ResultSet)outLst.get(1);
    if(rs.next()){
      response.setContentType("text/xml"); 
      response.setHeader("Cache-Control", "no-cache"); 
      response.getWriter().write("<message>yes</message>");
    }
    else{
      response.setContentType("text/xml"); 
      response.setHeader("Cache-Control", "no-cache"); 
      response.getWriter().write("<message>No</message>");
    }

        rs.close();
        pst.close();
    return null;
    }
    public ActionForward verifyEmailid(ActionMapping mapping, ActionForm form,
                               HttpServletRequest req,
                               HttpServletResponse response) throws Exception {
           HttpSession session = req.getSession(false);
           InfoMgr info = (InfoMgr)session.getAttribute("info");
           DBUtil util = new DBUtil();
           DBMgr db = new DBMgr(); 
           db.setCon(info.getCon());
           util.setDb(db);
           util.setInfo(info);
           db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
           util.setLogApplNm(info.getLoApplNm());
       StringBuffer sb = new StringBuffer();
       String val = util.nvl(req.getParameter("val"));
       String mprp = util.nvl(req.getParameter("mprp"));
       String nmeIdn = util.nvl(req.getParameter("nmeIdn"));
       ArrayList ary = new ArrayList();
       String sql = " select b.txt emailid from nme_dtl b where b.mprp like '"+mprp+"%' and upper(trim(b.txt))=upper(trim(?))\n" + 
       "and b.vld_dte is null" ;
       if(mprp.indexOf("EMAIL")>-1){
           sql = " select b.txt emailid from nme_dtl b where b.nme_idn not in(?) and b.mprp like '"+mprp+"%' and upper(trim(b.txt))=upper(trim(?))\n" + 
                  "and b.vld_dte is null" ; 
           ary.add(nmeIdn);
       }
       ary.add(val);
           ArrayList outLst = db.execSqlLst("Verify Email", sql, ary);
           PreparedStatement pst = (PreparedStatement)outLst.get(0);
           ResultSet rs = (ResultSet)outLst.get(1);
       if(rs.next()){
         response.setContentType("text/xml"); 
         response.setHeader("Cache-Control", "no-cache"); 
         response.getWriter().write("<message>yes</message>");
       }
       else{
         response.setContentType("text/xml"); 
         response.setHeader("Cache-Control", "no-cache"); 
         response.getWriter().write("<message>No</message>");
       }

           rs.close();
           pst.close();
       return null;
       }
       
    public AjaxContactsAction() {
        super();
    }
}
