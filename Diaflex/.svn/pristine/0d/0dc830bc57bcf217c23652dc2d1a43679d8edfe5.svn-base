package ft.com;

//~--- non-JDK imports --------------------------------------------------------

import java.io.IOException;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

//~--- JDK imports ------------------------------------------------------------

import java.sql.ResultSet;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class AjaxContactAction extends Action {

    public AjaxContactAction() {
        super();
    }

    public StringBuffer getNme(HttpServletRequest req) throws Exception {
        HttpSession session = req.getSession(false);
        InfoMgr info = (InfoMgr)session.getAttribute("info");
        DBUtil util = new DBUtil();
        DBMgr db = new DBMgr(); 
        db.setCon(info.getCon());
        util.setDb(db);
        util.setInfo(info);
        db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
        util.setLogApplNm(info.getLoApplNm());
        StringBuffer sb      = new StringBuffer();
        String       initial = req.getParameter("param");
        String       match   = "%" + initial + "%";

        match = match.toLowerCase();

        ArrayList ary = new ArrayList();

        ary.add(match);

        int       ctr = 0;
        String    sql =
            "select nme_idn , nme from nme_v where lower(nme) like lower(?) and nme is not null order by nme";

        ArrayList outLst = db.execSqlLst("column nme", sql, ary);
        PreparedStatement pst = (PreparedStatement)outLst.get(0);
        ResultSet rs = (ResultSet)outLst.get(1);
        while (rs.next()) {
            ++ctr;
            sb.append("<nmes>");
            sb.append("<nmeid>" + util.nvl(rs.getString(1)) + "</nmeid>");
            sb.append("<nme>" + util.nvl(rs.getString(2), "0") + "</nme>");
            sb.append("</nmes>");

            if (ctr == 20) {
                break;
            }
        }
        rs.close();
        pst.close();

        return sb;
    }

    public ActionForward execute(ActionMapping am, ActionForm a, HttpServletRequest req, HttpServletResponse res)
            throws Exception {
        HttpSession session = req.getSession(false);
        InfoMgr info = (InfoMgr)session.getAttribute("info");
        DBUtil util = new DBUtil();
        DBMgr db = new DBMgr(); 
        db.setCon(info.getCon());
        util.setDb(db);
        util.setInfo(info);
        db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
        util.setLogApplNm(info.getLoApplNm());

        String       typ = util.nvl(req.getParameter("typ"), "city");
        StringBuffer sb  = null;

        if (typ.equals("nme")) {
            sb = getCityCountry(req);
        }

        if (typ.equals("city")) {
            sb = getCityCountry(req);
        }
        if (typ.equals("check")) {
            String fnme = util.nvl(req.getParameter("fnme"));
            sb = checkFnme(req ,fnme);
        }
        if (typ.equals("DPT")) {
         
            sb = loadEmp(req);
        }
        if (sb == null) {
            sb = new StringBuffer();
        }

        res.setContentType("text/xml");
        res.setHeader("Cache-Control", "no-cache");
        res.getWriter().write("<mnme>" + sb.toString() + "</mnme>");

        return null;
    }
   
    public StringBuffer loadEmp(HttpServletRequest req) throws Exception {
        HttpSession session = req.getSession(false);
        InfoMgr info = (InfoMgr)session.getAttribute("info");
        DBUtil util = new DBUtil();
        DBMgr db = new DBMgr(); 
        db.setCon(info.getCon());
        util.setDb(db);
        util.setInfo(info);
        db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
        util.setLogApplNm(info.getLoApplNm());
        StringBuffer sb      = new StringBuffer();
        String str="";
        String deptIdn = util.nvl(req.getParameter("deptIdn"));
        String dept = util.nvl(req.getParameter("dept")).replaceAll(" ", "");
        ArrayList ary = new ArrayList();
        if(!deptIdn.equals("") ){
          str= " and a.dept_idn=? ";
         ary.add(deptIdn);
        }
        if(dept.equals("ALL")){
            str="";
            ary = new ArrayList();
        }
      
        int       ctr = 0;
        String    sql =
            "select b.nme_idn , b.fnme from dept_emp_valid a , mnme b  " +
            " where a.stt='A' and b.vld_dte is null and a.emp_idn = b.nme_idn "+str+" order by 2 ";

        ArrayList outLst = db.execSqlLst("column nme", sql, ary);
        PreparedStatement pst = (PreparedStatement)outLst.get(0);
        ResultSet rs = (ResultSet)outLst.get(1);
        while (rs.next()) {
            ++ctr;
            sb.append("<nmes>");
            sb.append("<nmeid>" + util.nvl(rs.getString(1)) + "</nmeid>");
            sb.append("<nme>" + util.nvl(rs.getString(2), "0") + "</nme>");
            sb.append("</nmes>");

           
        }
        rs.close();
        pst.close();

        return sb;
    }
    public StringBuffer checkFnme(HttpServletRequest req , String fnme){
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
        String checkFnme = "select nme_idn from mnme where vld_dte is null and upper(trim(fnme))= upper(trim(?))";
        ArrayList ary = new ArrayList();
        ary.add(fnme);

        ArrayList outLst = db.execSqlLst("checkFnme", checkFnme , ary);
        PreparedStatement pst = (PreparedStatement)outLst.get(0);
        ResultSet rs = (ResultSet)outLst.get(1);
        try {
            if(rs.next()) {
                sb.append("<msg>"+rs.getString(1)+"</msg>");
               
               
            }else{
                sb.append("<msg>YES</msg>");
                
            }
            rs.close();
            pst.close();
        } catch (SQLException sqle) {
            // TODO: Add catch code
            sqle.printStackTrace();
        }
        
        return sb;
    }
    public StringBuffer getCityCountry(HttpServletRequest req) throws Exception {
        HttpSession session = req.getSession(false);
        InfoMgr info = (InfoMgr)session.getAttribute("info");
        DBUtil util = new DBUtil();
        DBMgr db = new DBMgr(); 
        db.setCon(info.getCon());
        util.setDb(db);
        util.setInfo(info);
        db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
        util.setLogApplNm(info.getLoApplNm());
        StringBuffer sb      = new StringBuffer();
        String       initial = req.getParameter("param");
        String       match   =  initial + "%";

        match = match.toLowerCase();

        ArrayList ary = new ArrayList();

        ary.add(match);

        int    ctr = 0;
        String sql =
            "select idn, nme from country_city_v where lower(nme) like lower(?) and nme is not null order by nme";

        ArrayList outLst = db.execSqlLst("column nme", sql, ary);
        PreparedStatement pst = (PreparedStatement)outLst.get(0);
        ResultSet rs = (ResultSet)outLst.get(1);
        while (rs.next()) {
            ++ctr;
            sb.append("<nmes>");
            sb.append("<nmeid>" + util.nvl(rs.getString(1), "0") + "</nmeid>");
            sb.append("<nme>" + util.nvl(rs.getString(2), "0") + "</nme>");
            sb.append("</nmes>");

            
        }

        rs.close();
        pst.close();

        return sb;
    }

}


//~ Formatted by Jindent --- http://www.jindent.com
