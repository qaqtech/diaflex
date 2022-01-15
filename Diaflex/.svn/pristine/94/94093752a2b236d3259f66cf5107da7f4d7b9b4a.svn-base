package ft.com.website;

import ft.com.DBMgr;
import ft.com.DBUtil;
import ft.com.FormsUtil;
import ft.com.InfoMgr;
import ft.com.dao.DFMenu;
import ft.com.dao.Role;
import ft.com.dao.UIForms;


import ft.com.website.WebMenuRoleForm;

import java.io.IOException;

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

public class WebMenuRoleAction  extends  DispatchAction {
   
    public ActionForward load(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res)
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
           util.updAccessLog(req,res,"WebMenuRoleAction", "load");
        WebMenuRoleForm udf = (WebMenuRoleForm)af;
         ArrayList webroledscList = new ArrayList();
         HashMap webroleDtl=new HashMap();
         String sql = "select  role_idn , nvl(role_dsc,role_nm) role_dsc from mrole where stt='A' and to_dte is null order by role_idn";

             ArrayList outLst = db.execSqlLst("webroleDtl", sql,new ArrayList());
             PreparedStatement pst = (PreparedStatement)outLst.get(0);
             ResultSet rs = (ResultSet)outLst.get(1);
         while(rs.next()){
           webroledscList.add(util.nvl(rs.getString("role_dsc")));
           webroleDtl.put(util.nvl(rs.getString("role_dsc")),rs.getInt("role_idn"));
           }
             rs.close();
             pst.close();
       req.setAttribute("webroledscList",webroledscList);
       req.setAttribute("webroleDtl", webroleDtl);
       
       String menuRole = "select menu_idn , role_idn from  menu_role where stt='A' and to_dte is null";
             outLst = db.execSqlLst("WebMenuRole", menuRole, new ArrayList());
             pst = (PreparedStatement)outLst.get(0);
             rs = (ResultSet)outLst.get(1);
       while(rs.next()){
           String menu_idn = rs.getString("menu_idn");
           String role_idn =  rs.getString("role_idn");
           String fldNme = menu_idn+"_NA_"+role_idn;
           udf.setValue(fldNme,fldNme);
       }
             rs.close();
             pst.close();
       
       ArrayList webmainmenuList = new ArrayList();
       ArrayList webidnmenuList = new ArrayList();
       HashMap webmenuDtl=new HashMap();
       HashMap websubmenuDtl=new HashMap();
       sql = "select menu_idn,hdr,hsrt,itm from menu where stt='A' and to_dte is null order by hsrt,isrt";

             outLst = db.execSqlLst("webmenuDtl", sql,new ArrayList());
             pst = (PreparedStatement)outLst.get(0);
             rs = (ResultSet)outLst.get(1);
                while(rs.next()){
                  String hdr=util.nvl(rs.getString("hdr"));
                  if(!webmainmenuList.contains(hdr)){
                      webmainmenuList.add(hdr);
                    }
                  webidnmenuList.add(rs.getInt("menu_idn"));
                  webmenuDtl.put(util.nvl(rs.getString("hdr"))+"_"+rs.getInt("menu_idn"),rs.getInt("menu_idn"));  
                  websubmenuDtl.put(util.nvl(rs.getString("hdr"))+"_"+rs.getInt("menu_idn"),util.nvl(rs.getString("itm")));  
                  }
             rs.close();
             pst.close();
              req.setAttribute("webmainmenuList", webmainmenuList);
              req.setAttribute("webidnmenuList", webidnmenuList);
              req.setAttribute("webmenuDtl", webmenuDtl);
              req.setAttribute("websubmenuDtl", websubmenuDtl);
       
           util.updAccessLog(req,res,"WebMenuRoleAction", "load end");
         return am.findForward("load");
         }
     }
    
    public ActionForward save(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res)
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
          util.updAccessLog(req,res,"WebMenuRoleAction", "save");
        String stt = req.getParameter("stt");
        String manuIdn = req.getParameter("menuIdn");
        String roleIdn = req.getParameter("roleIdn");
        String sql="";
        ArrayList    params  = new ArrayList();
        if(stt.equals("true"))
        stt="A";
        else
          stt="IA";
      
      sql="update menu_role set menu_idn=?,role_idn=?,stt=? where menu_idn=? and role_idn=?"; 
      params.add(manuIdn);
      params.add(roleIdn);
      params.add(stt);
      params.add(manuIdn);
      params.add(roleIdn);
      int ct = db.execUpd(" update menu_role ", sql, params);
      if(ct<1){
        params.clear();
        params.add(manuIdn);
        params.add(roleIdn);
        params.add(stt);
        sql="insert into menu_role(mr_idn,menu_idn,role_idn,stt) values(menu_role_seq.nextval,?,?,?)"; 
        ct = db.execUpd(" insert menu_role ", sql, params);
      }
      
          util.updAccessLog(req,res,"WebMenuRoleAction", "save end");
        return null;
        }
    }
    public String nvl(String pval){
       pval = pval.replaceAll(" ", "");
        if(pval.equals("NA"))
            pval = null;
        return pval;
        
    }
 
    public String nvl2(String pval){
       
        if(pval==null)
            pval = "NA";
        return pval;
        
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
              util.updAccessLog(req,res,"WebMenuRoleAction", "Unauthorized Access");
              else
          util.updAccessLog(req,res,"WebMenuRoleAction", "init");
          }
          }
          return rtnPg;
          }
    public WebMenuRoleAction() {
        super();
    }
}
