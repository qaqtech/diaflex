package ft.com.dashboard;
import ft.com.DBMgr;
import ft.com.DBUtil;
import ft.com.InfoMgr;

import ft.com.generic.GenericImpl;
import ft.com.marketing.SearchQuery;

import java.io.IOException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.ArrayList;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

public class AjaxDashAction extends DispatchAction {
    public ActionForward save(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res)
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
        String stt = req.getParameter("stt");
        String pgidn = req.getParameter("pgidn");
        String pgitmidn = req.getParameter("pgitmidn");
        String usrid = req.getParameter("usrid");    
        ArrayList params  = new ArrayList();
        String sql="";
            if(stt.equals("true"))
            stt="A";
            else
            stt="IA";
      sql="update df_pg_itm_usr set stt=? where pg_idn=? and itm_idn=? and usr_idn=?";
      params  = new ArrayList();
      params.add(stt);
      params.add(pgidn);
      params.add(pgitmidn);
      params.add(usrid);
      int ct = db.execDirUpd(" update df_pg_itm_usr ", sql, params);
      if(ct<1){
        params.clear();
        params.add(pgitmidn);
        params.add(pgidn);
        params.add(usrid);
        params.add(stt);
        sql="insert into df_pg_itm_usr(idn,itm_idn,pg_idn,usr_idn,stt) values(df_pg_itm_usr_seq.nextval,?,?,?,?)"; 
        ct = db.execDirUpd(" insert df_pg_itm_usr ", sql, params);
      }
      return null;
    }

    public ActionForward allowRight(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res)
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
    String stt = req.getParameter("stt");
    String pgitmidn = req.getParameter("pgitmidn");
    ArrayList params = new ArrayList();
    String sql="update df_pg_itm_usr set alw_yn=? where itm_idn=? and usr_idn=?";
    params = new ArrayList();
    params.add(stt);
    params.add(pgitmidn);
    params.add(String.valueOf(info.getUsrId()));
    int ct = db.execDirUpd(" update df_pg_itm_usr ", sql, params);
    HashMap allPageDtl= (info.getPageDetails() == null)?new HashMap():(HashMap)info.getPageDetails();
    HashMap pageDtl=util.pagedefdashboard("DASH_BOARD");
    allPageDtl.put("DASH_BOARD",pageDtl);
    info.setPageDetails(allPageDtl);

    return null;
    }
    
    public ActionForward saveVisibility(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res)
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
    String idn = util.nvl(req.getParameter("idn"));
    ArrayList params = new ArrayList();
    String[] par= idn.split("-");
    String sql="update df_pg_itm_usr set alw_yn=? where pg_idn=? and itm_idn=? and usr_idn=?";
    params = new ArrayList();
    params.add("Y");
    params.add(par[0]);
    params.add(par[1]);
    params.add(String.valueOf(info.getUsrId()));
    int ct = db.execDirUpd(" update df_pg_itm_usr ", sql, params);
    HashMap allPageDtl= (info.getPageDetails() == null)?new HashMap():(HashMap)info.getPageDetails();
    HashMap pageDtl=util.pagedefdashboard("DASH_BOARD");
    allPageDtl.put("DASH_BOARD",pageDtl);
    info.setPageDetails(allPageDtl);

    return null;
    }
    public ActionForward dashbuyerwisePieChart(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res) throws Exception {
            HttpSession session = req.getSession(false);
            InfoMgr info = (InfoMgr)session.getAttribute("info");
            DBUtil util = new DBUtil();
            DBMgr db = new DBMgr(); 
            db.setCon(info.getCon());
            util.setDb(db);
            util.setInfo(info);
            db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
            util.setLogApplNm(info.getLoApplNm());
          res.setContentType("text/xml"); 
          res.setHeader("Cache-Control", "no-cache"); 
            String defaultdisplay = util.nvl((String)req.getParameter("defaultdisplay"));
             ArrayList dtlList=(ArrayList)session.getAttribute("dtlList");
               StringBuffer sb = new StringBuffer();
                ArrayList list=new ArrayList();
                ArrayList listDtl=new ArrayList();
                String ttl="0";
                String ttlother="0";
            for(int i=0;i<dtlList.size();i++){
            HashMap byrData=(HashMap)dtlList.get(i);
           String  vlu =util.nvl((String)byrData.get("VLU"));
                    vlu =vlu.replaceAll(",","");
                if(!vlu.equals("") && !vlu.equals("0")){
                if(i<Integer.parseInt(defaultdisplay)){
                listDtl=new ArrayList();
                listDtl.add(vlu);   
                listDtl.add(util.nvl((String)byrData.get("BYR")));
                list.add(listDtl);
                }else{
                ttlother=String.valueOf((Double.parseDouble(ttlother)+Double.parseDouble(vlu)));
                }
                ttl=String.valueOf(Double.parseDouble(ttl)+Double.parseDouble(vlu));
                }
                }
                if(!ttlother.equals("0")){
                listDtl=new ArrayList();
                listDtl.add(String.valueOf(util.roundToDecimals(Double.parseDouble(ttlother),2)));   
                listDtl.add("Others");
                list.add(listDtl);    
                }         
                for(int k=0;k<list.size();k++){
                listDtl=new ArrayList();
                listDtl=(ArrayList)list.get(k);
                String vlu = util.nvl((String)listDtl.get(0));
                String on = util.nvl((String)listDtl.get(1));
                if (on.indexOf("&") > -1)
                on = on.replaceAll("&", "%26");
                sb.append("<subTag>");
                sb.append("<attrNme>"+on+"</attrNme>");
                sb.append("<attrVal>"+vlu+"</attrVal>");
                sb.append("</subTag>");
                }
                
                res.getWriter().write("<materTag>" +sb.toString()+ "</materTag>");

            return null;
        }
    
    public AjaxDashAction() {
        super();
    }
    
}
