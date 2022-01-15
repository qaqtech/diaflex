package ft.com.website;

import ft.com.DBMgr;
import ft.com.DBUtil;
import ft.com.InfoMgr;
import ft.com.LogMgr;
import ft.com.dao.ByrDao;
import ft.com.marketing.SearchForm;

import java.io.IOException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

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

public class UserLoginInfoAction extends DispatchAction {
    
   
    HttpSession session ;
    DBMgr db ;
    InfoMgr info ;
    DBUtil util ;
    UserLoginInfoForm loginInfoForm ;
    public ActionForward load(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
      HttpSession session = request.getSession(false);
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
      rtnPg=init(request,response,session,util);
      }else
      rtnPg="sessionTO";
      if(!rtnPg.equals("sucess")){
          util.updAccessLog(request,response,"UserLoginInfoAction", "load");
      UserLoginInfoForm loginInfoForm = (UserLoginInfoForm)form;
       ArrayList byrList = new ArrayList();
       String webUser = "select a.nme_idn, a.nme from nme_v a " + 
           " where exists (select 1 from web_usrs b , nmerel c where b.rel_idn = c.nmerel_idn " +
           " and a.nme_idn = c.nme_idn and b.to_dt is null) order by nme ";
            ArrayList outLst = db.execSqlLst("webUser", webUser, new ArrayList());
            PreparedStatement pst = (PreparedStatement)outLst.get(0);
            ResultSet rs = (ResultSet)outLst.get(1);
       while(rs.next()){
           ByrDao byr = new ByrDao();
           byr.setByrNme(rs.getString("nme"));
           byr.setByrIdn(rs.getInt("nme_idn"));
           byrList.add(byr);
       }
        rs.close(); pst.close();
       loginInfoForm.setByrList(byrList);
          util.updAccessLog(request,response,"UserLoginInfoAction", "load end");
        }
      return mapping.findForward("load");
    }
    public ActionForward fetch(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
      HttpSession session = request.getSession(false);
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
      rtnPg=init(request,response,session,util);
      }else
      rtnPg="sessionTO";
      if(!rtnPg.equals("sucess")){
          util.updAccessLog(request,response,"UserLoginInfoAction", "fetch");
      UserLoginInfoForm loginInfoForm = (UserLoginInfoForm)form;
       String byrIdn = util.nvl(loginInfoForm.getByrId());
       String toDte = util.nvl(loginInfoForm.getToDte());
       String frmDte = util.nvl(loginInfoForm.getFrmDte());
       ArrayList usrDtlList = new ArrayList();
       ArrayList ary = new ArrayList();
       String sql = "select a.log_id , to_char( a.dt_tm ,'rrrr-mm-dd HH24:mi:ss') dt_tm , c.nme , c.contact_prsn , b.usr , a.cl_ip , a.cl_usr , a.cl_browser\n" + 
       " from web_login_log a , web_usrs b , nme_v c , nmerel d\n" + 
       " where a.usr_id = b.usr_id and b.rel_idn = d.nmerel_idn and c.nme_idn = d.nme_idn and b.to_dt is null ";
       if(!byrIdn.equals("0")){
           sql = sql +" and c.nme_idn=? ";
           ary.add(byrIdn);
       }
       if(!frmDte.equals("") || !toDte.equals("")){
           if(frmDte.equals(""))
               frmDte = toDte;
            if(toDte.equals(""))
                toDte = frmDte;
           sql = sql + " and trunc(a.dt_tm) between trunc(to_date('"+frmDte+"','dd-mm-rrrr')) and trunc(to_date('"+toDte+"','dd-mm-rrrr'))   order by a.dt_tm , c.nme " ;
       }
       int cnt=0;
            ArrayList outLst = db.execSqlLst("sql", sql, ary);
            PreparedStatement pst = (PreparedStatement)outLst.get(0);
            ResultSet rs = (ResultSet)outLst.get(1);
       while(rs.next()){
           cnt++;
           HashMap usrDtl = new HashMap();
           usrDtl.put("Count", String.valueOf(cnt));
           usrDtl.put("logId", util.nvl(rs.getString("log_id")));
           usrDtl.put("dteTm", util.nvl(rs.getString("dt_tm")));
           usrDtl.put("nme", util.nvl(rs.getString("nme")));
           usrDtl.put("cntPrsn", util.nvl(rs.getString("contact_prsn")));
           usrDtl.put("usr", util.nvl(rs.getString("usr")));
           usrDtl.put("cl_ip", util.nvl(rs.getString("cl_ip")));
           usrDtl.put("cl_usr", util.nvl(rs.getString("cl_usr")));
           usrDtl.put("cl_browser", util.nvl(rs.getString("cl_browser")));
           usrDtlList.add(usrDtl);
       }
        rs.close(); pst.close();
       loginInfoForm.setUsrDtlList(usrDtlList);
       loginInfoForm.setByrId("");
       loginInfoForm.setFrmDte("");
       loginInfoForm.setToDte("");
       request.setAttribute("view", "Y");
          util.updAccessLog(request,response,"UserLoginInfoAction", "fetch end");
      
        }
      return mapping.findForward("load");
    }
    public UserLoginInfoAction() {
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
              util.updAccessLog(req,res,"UserLoginInfoAction", "Unauthorized Access");
              else
          util.updAccessLog(req,res,"UserLoginInfoAction", "init");
          }
          }
          return rtnPg;
          }
    
}
