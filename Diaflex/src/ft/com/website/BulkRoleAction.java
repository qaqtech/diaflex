package ft.com.website;

import ft.com.DBMgr;
import ft.com.DBUtil;
import ft.com.FormsUtil;
import ft.com.InfoMgr;
import ft.com.dao.ByrDao;
import ft.com.dao.GenDAO;
import ft.com.dao.UIForms;
import ft.com.dao.UIFormsFields;

import ft.com.website.BulkRoleForm;
import ft.com.marketing.MemoReportForm;

import java.io.IOException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.sql.SQLException;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

public class BulkRoleAction extends DispatchAction {
   
    

    public BulkRoleAction() {
        super();
    }
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
        util.updAccessLog(req,res,"BulkRoleAction", "load");
        ResultSet rs           = null;
      
        HashMap saleperson=new HashMap();
        String sql = "  select nme_idn, nme from nme_v a where typ = 'EMPLOYEE' " + 
          "              and exists (select 1 from mnme a1, nmerel b where b.nme_idn = a1.nme_idn and a.nme_idn = a1.emp_idn and b.vld_dte is null and b.flg = 'CNF') " + 
          "             order by nme";
          ArrayList outLst = db.execSqlLst("Sale Person", sql, new ArrayList());
          PreparedStatement pst = (PreparedStatement)outLst.get(0);
          rs = (ResultSet)outLst.get(1);
        try {
            while (rs.next()) {
                String idn=String.valueOf(rs.getInt("nme_idn"));
                String nme=util.nvl(rs.getString("nme"));
              saleperson.put(idn, nme);
            }
            rs.close(); pst.close();
        } catch (SQLException sqle) {
            // TODO: Add catch code
            sqle.printStackTrace();
        }
   
   
        util.updAccessLog(req,res,"BulkRoleAction", "load end");
    req.setAttribute("saleperson",saleperson);
      return am.findForward("load");
      }
  } 
  
 
  
  public ActionForward selectEmp(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res)
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
        util.updAccessLog(req,res,"BulkRoleAction", "emp");
    BulkRoleForm udf = (BulkRoleForm)af;
    String emp_idn=req.getParameter("saleEmpidn");
    ArrayList params   = new ArrayList();
    ArrayList partydtl=new ArrayList();
    ArrayList webroledscList = new ArrayList();
    HashMap webroleDtl=new HashMap();
    String sql= "select  role_idn , nvl(role_dsc,role_nm) role_dsc from mrole where stt='A' and to_dte is null order by role_idn";

          ArrayList outLst = db.execSqlLst("webroleDtl", sql,new ArrayList());
          PreparedStatement pst = (PreparedStatement)outLst.get(0);
          ResultSet rs = (ResultSet)outLst.get(1);
    while(rs.next()){
      webroledscList.add(util.nvl(rs.getString("role_dsc")));
      webroleDtl.put(util.nvl(rs.getString("role_dsc")),rs.getInt("role_idn"));
      }
      rs.close(); pst.close();
    req.setAttribute("webroledscList",webroledscList);
    req.setAttribute("webroleDtl", webroleDtl);
    
    sql = "select usr_idn , role_idn from  usr_role where stt='A' and to_dte is null";
          outLst = db.execSqlLst("WebMenuRole", sql, new ArrayList());
          pst = (PreparedStatement)outLst.get(0);
          rs = (ResultSet)outLst.get(1);
    while(rs.next()){
        String usr_idn = rs.getString("usr_idn");
        String role_idn =  rs.getString("role_idn");
        String fldNme = usr_idn+"_NA_"+role_idn;
        udf.setValue(fldNme,fldNme);
    }
      rs.close(); pst.close();
    
   sql = "select a.nme nme, b.usr usr, b.usr_id usrid from nme_v a , web_usrs b , nmerel c " +
        "where a.nme_idn = c.nme_idn and b.rel_idn = c.nmerel_idn and b.to_dt is null " +
        "and c.vld_dte is null and c.flg = 'CNF' and a.emp_idn=? order by a.nme , b.usr";
    params.add(emp_idn);
          outLst = db.execSqlLst("Sale Person", sql, params);
          pst = (PreparedStatement)outLst.get(0);
          rs = (ResultSet)outLst.get(1);
    try {
        while (rs.next()) {
          HashMap prtyDtl = new HashMap();
          String nme=util.nvl(rs.getString("nme"));
          String usr=util.nvl(rs.getString("usr"));
          String usrid=util.nvl(rs.getString("usrid"));
          prtyDtl.put("nme", nme+"("+usr+")");
          prtyDtl.put("usrIdn",usrid);
          partydtl.add(prtyDtl);
          }
        rs.close(); pst.close();
    } catch (SQLException sqle) {
        // TODO: Add catch code
        sqle.printStackTrace();
    }
    req.setAttribute("partydtl",partydtl);
    req.setAttribute("emp_idn",emp_idn);
        util.updAccessLog(req,res,"BulkRoleAction", "emp end");
      return am.findForward("view");
      }
  } 
  
  public ActionForward checkAllBulk(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res)
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
        util.updAccessLog(req,res,"BulkRoleAction", "checkAllBulk");
    BulkRoleForm udf = (BulkRoleForm)af;
    String roleName=req.getParameter("roleName");
    String emp_idn=req.getParameter("emp_idn");
    String stt=req.getParameter("stt");
    ArrayList params   = new ArrayList();
    ArrayList ary   = new ArrayList();
    String sql="";
    String role_idn="";
    
    sql="select  role_idn , nvl(role_dsc,role_nm) role_dsc from mrole where stt='A' and role_dsc=? and to_dte is null order by role_idn";
    params.add(roleName);
          ArrayList outLst = db.execSqlLst("Role Idn", sql, params);
          PreparedStatement pst = (PreparedStatement)outLst.get(0);
          ResultSet rs = (ResultSet)outLst.get(1);
    try {
        while (rs.next()) {
            role_idn=String.valueOf(rs.getInt("role_idn"));
          }
        rs.close(); pst.close();
    } catch (SQLException sqle) {
        // TODO: Add catch code
        sqle.printStackTrace();
    }
    
    
    
    sql = "select a.nme nme, b.usr usr, b.usr_id usrid from nme_v a , web_usrs b , nmerel c " +
         "where a.nme_idn = c.nme_idn and b.rel_idn = c.nmerel_idn and b.to_dt is null " +
         "and c.vld_dte is null and c.flg = 'CNF' and a.emp_idn=? order by a.nme , b.usr";
      params   = new ArrayList();
     params.add(emp_idn);
          outLst = db.execSqlLst("Sale Person", sql, params);
          pst = (PreparedStatement)outLst.get(0);
          rs = (ResultSet)outLst.get(1);
     try {
         while (rs.next()) {
             ary.add(util.nvl(rs.getString("usrid")));
           }
         rs.close(); pst.close();
     } catch (SQLException sqle) {
         // TODO: Add catch code
         sqle.printStackTrace();
     }
      params   = new ArrayList();
     for(int i=0;i<ary.size();i++){
         sql = "update usr_role a set a.stt='IA' " + 
         "where a.role_idn in (select c.role_idn from mrole b , mrole c where b.typ = c.typ  and b.role_idn=?  and b.typ is not null ) " + 
         "and a.usr_idn = ? ";
         
         params.add(role_idn);
         params.add(ary.get(i));
         int up = db.execUpd("update", sql, params);
         
         
    sql="update usr_role set usr_idn=?,role_idn=?,stt=? where usr_idn=? and role_idn=?"; 
         params   = new ArrayList();
    params.add(ary.get(i));
    params.add(role_idn);
    params.add(stt);
    params.add(ary.get(i));
    params.add(role_idn);
    int ct = db.execUpd(" update usr_role ", sql, params);
         
    if(ct<1){
        params   = new ArrayList();
      params.add(ary.get(i));
      params.add(role_idn);
      params.add(stt);
      sql="insert into usr_role(ur_idn,usr_idn,role_idn,stt) values(seq_usr_role_idn.nextval,?,?,?)"; 
      ct = db.execUpd(" insert usr_role ", sql, params);
    }
     }
    
      return null;
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
        util.updAccessLog(req,res,"BulkRoleAction", "save");
      String stt = req.getParameter("stt");
      String usr_idn = req.getParameter("emp_idn");
      String roleIdn = req.getParameter("roleIdn");
      ArrayList    params  = new ArrayList();
      String sql="";
      sql = "update usr_role a set a.stt='IA' " + 
      "where a.role_idn in (select c.role_idn from mrole b , mrole c where b.typ = c.typ  and b.role_idn=?  and b.typ is not null ) " + 
      "and a.usr_idn = ? ";
    
      params.add(roleIdn);
      params.add(usr_idn);
      int up = db.execUpd("update", sql, params);
     
      if(stt.equals("true"))
      stt="A";
      else
        stt="IA";
    
    sql="update usr_role set usr_idn=?,role_idn=?,stt=? where usr_idn=? and role_idn=?"; 
     params  = new ArrayList();
    params.add(usr_idn);
    params.add(roleIdn);
    params.add(stt);
    params.add(usr_idn);
    params.add(roleIdn);
    int ct = db.execUpd(" update usr_role ", sql, params);
    if(ct<1){
      params.clear();
      params.add(usr_idn);
      params.add(roleIdn);
      params.add(stt);
      sql="insert into usr_role(ur_idn,usr_idn,role_idn,stt) values(seq_usr_role_idn.nextval,?,?,?)"; 
      ct = db.execUpd(" insert usr_role ", sql, params);
    }
    
      
      return null;
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
              util.updAccessLog(req,res,"BulkRoleAction", "Unauthorized Access");
              else
          util.updAccessLog(req,res,"BulkRoleAction", "init");
          }
          }
          return rtnPg;
          }
//    public String init(HttpServletRequest req , HttpServletResponse res) {
//         
//        String rtnPg="sucess";
//        String invalide="";
//        session = req.getSession(false);
//        db = (DBMgr)session.getAttribute("db");
//        info = (InfoMgr)session.getAttribute("info");
//        util = ((DBUtil)session.getAttribute("util") == null)?new DBUtil():(DBUtil)session.getAttribute("util");
//            String connExists=util.nvl(util.getConnExists());  
//            if(!connExists.equals("N"))
//            invalide=util.nvl(util.chkTimeOut(),"N");
//            if(session.isNew() || db==null)
//            rtnPg="sessionTO";   
//            if(connExists.equals("N"))
//            rtnPg="connExists";     
//            if(invalide.equals("Y"))
//            rtnPg="chktimeout";
//            if(rtnPg.equals("sucess")){
//                boolean sout=util.getLoginsession(req,res,session.getId());
//                if(!sout){
//                rtnPg="sessionTO";
//                System.out.print("New Session Id :="+session.getId());
//                }
//            }
//        if(rtnPg.equals("sucess")){
//            formFields = info.getFormFields(formName);
//
//            if ((formFields == null) || (formFields.size() == 0)) {
//                formFields = util.getFormFields(formName);
//            }
//
//            uiForms = (UIForms) formFields.get("DAOS");
//            daos    = uiForms.getFields();
//            errors  = new ArrayList();
//            helper  = new FormsUtil();
//            helper.init(db, util, info);
//        }
//        return rtnPg;
//        }
}
