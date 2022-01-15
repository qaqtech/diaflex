package ft.com.masters;

import ft.com.DBMgr;
import ft.com.DBUtil;
import ft.com.FormsUtil;
import ft.com.InfoMgr;
import ft.com.dao.GenDAO;
import ft.com.dao.Mprc;
import ft.com.dao.UIForms;
import ft.com.dao.UIFormsFields;

import ft.com.generic.GenericImpl;

import java.io.IOException;

import java.sql.Connection;
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

public class DeptPrcAction extends DispatchAction {
    


    public DeptPrcAction() {
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
                rtnPg=util.checkUserPageRights("masters/department.do?","");
                if(rtnPg.equals("unauthorized"))
                util.updAccessLog(req,res,"Dept Process", "Unauthorized Access");
                else
                util.updAccessLog(req,res,"Dept Process", "init");
            }
            }
            return rtnPg;
         }

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
            util.updAccessLog(req,res,"Dept Process", "load start");
        DeptPrcForm udf = (DeptPrcForm) af;
        ArrayList mprcList = new ArrayList();
        String processSql = "select idn , prc , dsc from mprc where stt='A'";
        ResultSet rs = db.execSql("processSql", processSql,new ArrayList());
        while(rs.next()){
            Mprc prc = new Mprc();
            prc.setMprcId(rs.getString("idn"));
            prc.setPrc(rs.getString("prc"));
            mprcList.add(prc);
        }
            rs.close();
       req.setAttribute("MprcList", mprcList);
        String deptIdn = req.getParameter("deptIdn");
        ArrayList params = new ArrayList();
        params.add(deptIdn);
        String keyQ = "select dept from mdept  where idn =? ";
         info.setValue("dept", util.getPageKeyNme("dept", keyQ, params));
         HashMap deptPrcMap = new HashMap();
         String sql = "select prc_idn , dept_idn from dept_prc_valid where dept_idn = ? and dte_to is null ";
         params = new ArrayList();
         params.add(deptIdn);
         rs= db.execSql("dept", sql, params);
         while(rs.next()){
             String prcIdn = util.nvl(rs.getString("prc_idn")).trim();
            udf.setValue(deptIdn+"_"+prcIdn, prcIdn);
             
         }
        rs.close();
        req.setAttribute("deptPrcMap", deptPrcMap);
        req.setAttribute("deptIdn",deptIdn);
            util.updAccessLog(req,res,"Dept Process", "load end");
        return am.findForward("view");
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
            util.updAccessLog(req,res,"Dept Process", "save start");
        DeptPrcForm udf = (DeptPrcForm) af;
        ArrayList ary = new ArrayList();
        String depIdn = req.getParameter("deptIdn");
        String prcIdn = req.getParameter("prcIdn");
        String stt = req.getParameter("stt");
        ary = new ArrayList();
        ary.add(depIdn);
        ary.add(prcIdn);
        int ct =0;
        
        if(stt.equals("true")){
            String updateDprc = "update dept_prc_valid set dte_to = null where  dept_idn = ? and prc_idn=? ";
            ct = db.execUpd("update DeptPrc", updateDprc, ary);
            if(ct < 1){
               String insert = "insert into dept_prc_valid( dept_idn , prc_idn ,  dte_fr ) select ? , ? , sysdate from dual";
               ct = db.execUpd("insert", insert, ary);
               }
        }else{
            String updateDprc = "update dept_prc_valid set dte_to = sysdate where  dept_idn = ? and prc_idn=? ";
            ct = db.execUpd("update DeptPrc", updateDprc, ary);
        }
            util.updAccessLog(req,res,"Dept Process", "save end");
        return load(am, af, req, res);
        }
    }
    
    public ActionForward delete(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res)
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
            util.updAccessLog(req,res,"Dept Process", "delete start");
        DeptPrcForm udf = (DeptPrcForm) af;
        String Idn = req.getParameter("Idn");
        String deleteCity = "update dept_prc_valid set dte_to=sysdate where idn= ?";
        ArrayList ary = new ArrayList();
        ary.add(Idn);
        int ct = db.execUpd("deleteCity", deleteCity, ary);
            util.updAccessLog(req,res,"Dept Process", "delete end");
        return load(am, af, req, res);
        }
    }
   
}
