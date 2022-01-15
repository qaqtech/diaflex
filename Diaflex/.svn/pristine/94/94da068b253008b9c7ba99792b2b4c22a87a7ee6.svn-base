package ft.com.role;

import ft.com.DBMgr;
import ft.com.DBUtil;
import ft.com.FormsUtil;
import ft.com.InfoMgr;
import ft.com.dao.Role;
import ft.com.dao.UIForms;
import ft.com.dao.UIFormsFields;
import ft.com.masters.CountryFrm;

import java.io.IOException;

import java.sql.Connection;
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

public class UserRoleAction extends  DispatchAction {
    public ActionForward load(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res)
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
            util.updAccessLog(req,res,"User Role Detail", "load start");
        UserRoleForm udf = (UserRoleForm) af;
        ResultSet rs =null;
        String usrIdn = util.nvl(req.getParameter("usrIdn"));
        if(usrIdn.equals(""))
            usrIdn = (String)udf.getValue("usrIdn");
        
        String sql = "select df_role_idn from df_usr_role where df_usr_idn=? and stt='A'";
        ArrayList ary = new ArrayList();
        ary.add(usrIdn);

            ArrayList outLst = db.execSqlLst("usrRole", sql, ary);
            PreparedStatement pst = (PreparedStatement)outLst.get(0);
            rs = (ResultSet)outLst.get(1);
        while(rs.next()){
            String roleIdn = rs.getString("df_role_idn");
            udf.setValue(roleIdn, roleIdn);
        }
        rs.close(); pst.close();
        ArrayList roleList = new ArrayList();
        String roleDtl = "select df_role_idn , role_dsc from df_mrole where stt='A' and to_dte is null";

            outLst = db.execSqlLst("roleDtl", roleDtl,new ArrayList());
            pst = (PreparedStatement)outLst.get(0);
            rs = (ResultSet)outLst.get(1);
        while(rs.next()){
            Role role = new Role();
            role.setRoleIdn(rs.getInt("df_role_idn"));
            role.setRole_dsc(rs.getString("role_dsc"));
            roleList.add(role);
        }
        rs.close(); pst.close();
        udf.setRoleList(roleList);
        session.setAttribute("roleList", roleList);
        session.setAttribute("usrIdn",usrIdn);
        udf.setValue("usrIdn", usrIdn);
        util.updAccessLog(req,res,"User Role Detail", "load end");
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
        String stt = req.getParameter("stt");
        String roleIdn = req.getParameter("roleIdn");
        String usrIdn = req.getParameter("usrIdn");
        ArrayList ary = new ArrayList();
        ary.add(roleIdn);
        ary.add(usrIdn);
        if(stt.equals("true")){
            String checkRole = "select df_ur_idn from df_usr_role where  df_role_idn=? and df_usr_idn=? ";

            ArrayList outLst = db.execSqlLst("checkRole", checkRole, ary);
            PreparedStatement pst = (PreparedStatement)outLst.get(0);
            ResultSet rs = (ResultSet)outLst.get(1);
            if(rs.next()){
                String updateRole = "update df_usr_role set stt='A' where df_role_idn= ? and df_usr_idn=? ";
                int up = db.execUpd("update", updateRole,ary);
            }else{
                String insertRole = "insert into df_usr_role(df_ur_idn,df_role_idn,df_usr_idn,stt) "+
                    "select df_ur_idn_seq.nextval , ? , ?, ? from dual ";
                ary = new ArrayList();
                ary.add(roleIdn);
                ary.add(usrIdn);
                ary.add("A");
            int up = db.execUpd("insertRole",insertRole,ary);
                
            }
            rs.close(); pst.close();
        }else{
            String updateRole = "update df_usr_role set stt='IA'  where df_role_idn= ? and df_usr_idn=? ";
            int up = db.execUpd("update", updateRole,ary);
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
        rtnPg=util.checkUserPageRights("role/userDetail.do?","");
        if(rtnPg.equals("unauthorized"))
        util.updAccessLog(req,res,"User Role Detail", "Unauthorized Access");
        else
        util.updAccessLog(req,res,"User Role Detail", "init");
    }
    }
    return rtnPg;
    }
    public UserRoleAction() {
        super();
    }
}
