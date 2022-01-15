package ft.com.website;

import ft.com.DBMgr;

import ft.com.DBUtil;
import ft.com.InfoMgr;

import ft.com.LogMgr;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.ArrayList;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

public class AjaxAction extends DispatchAction {
    
    

    public AjaxAction() {
        super();
    }
    public ActionForward populateUserNames(ActionMapping am, ActionForm a, HttpServletRequest req, HttpServletResponse res) throws Exception {
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
        //String typ = req.getParameter("typ");
        String initial = req.getParameter("param");
        System.out.println("");

        System.out.println("@----initial-----@" + initial);
        String match = "" + initial + "%";
        match = match.toLowerCase();
        ArrayList ary = new ArrayList();
        int ctr = 0;
        String sql = "select nme_idn , form_url_encode(nme) nme from nme_v where lower(nme) like lower(?) " + " and nme is not null order by nme";

        

        ary.add(match);


        ArrayList outLst = db.execSqlLst("column nme", sql, ary);
        PreparedStatement pst = (PreparedStatement)outLst.get(0);
        ResultSet rs = (ResultSet)outLst.get(1);
        while (rs.next()) {
            ++ctr;
            String nme = util.nvl(rs.getString(2), "0").replaceAll("&", "&amp;");
            sb.append("<nmes>");
            sb.append("<nmeid>" + util.nvl(rs.getString(1)) + "</nmeid>");
            sb.append("<nme>" + nme + "</nme>");
            sb.append("</nmes>");
            /*
            if(ctr == 20)
                break;
            */
        }
        rs.close();
        pst.close();
        res.setContentType("text/xml");
        res.setHeader("Cache-Control", "no-cache");
        res.getWriter().write("<mnme>" + sb.toString() + "</mnme>");

        return null;
    }

    
   
   
}
