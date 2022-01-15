package ft.com.receipt;

import ft.com.DBMgr;
import ft.com.DBUtil;
import ft.com.GtMgr;
import ft.com.InfoMgr;

import ft.com.dao.ByrDao;

import ft.com.dao.ObjBean;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

public class ManualEntriesAction extends  DispatchAction {
    public ActionForward load(ActionMapping am,ActionForm form,HttpServletRequest req,HttpServletResponse res) throws Exception {
        HttpSession session = req.getSession(false);
        InfoMgr info = (InfoMgr)session.getAttribute("info");
        GtMgr gtMgr = (GtMgr)session.getAttribute("gtMgr");
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
            ManualEntriesForm udf =(ManualEntriesForm)form;
            ArrayList byrList = new ArrayList();
           String buyerSql="select idn,nm from gl_mst where stt='A' order by nm";
            ArrayList rsLst = db.execSqlLst("data sql", buyerSql, new ArrayList());
            PreparedStatement psmt = (PreparedStatement)rsLst.get(0);
            ResultSet rs = (ResultSet)rsLst.get(1);
            while (rs.next()) {
               ByrDao byr = new ByrDao();
                byr.setByrIdn(rs.getInt("idn"));
                byr.setByrNme(rs.getString("nm"));
                byrList.add(byr);
            }
            udf.setByrList(byrList);
            rs.close();
            psmt.close();
            ArrayList mtermLst = new ArrayList();
             String mtermsSql="select dys from mtrms where till_dt is null";
             rsLst = db.execSqlLst("data sql", mtermsSql, new ArrayList());
            psmt = (PreparedStatement)rsLst.get(0);
            rs = (ResultSet)rsLst.get(1);
            while (rs.next()) {
               ObjBean mterm = new ObjBean();
                mterm.setNme(rs.getString("dys"));
                mterm.setNme(rs.getString("dys"));
                mtermLst.add(mterm);
            }
            udf.setMtrmsList(mtermLst);
            rs.close();
            psmt.close();
            return am.findForward("load");   
        }
    }
    
    public ActionForward save(ActionMapping am,ActionForm form,HttpServletRequest req,HttpServletResponse res) throws Exception {
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
            String msg="";
            ManualEntriesForm udf =(ManualEntriesForm)form;
            String byrIdn = util.nvl((String)udf.getValue("byrIdn"));
            String type = util.nvl((String)udf.getValue("type"));
            String subType = util.nvl((String)udf.getValue("subtype"));
            String refdte = util.nvl((String)udf.getValue("refDte"));
            String refTyp = util.nvl((String)udf.getValue("refTyp"));
            String refIdn = util.nvl((String)udf.getValue("refIdn"));
            String dys = util.nvl((String)udf.getValue("dys"));
             String cts = util.nvl((String)udf.getValue("cts"));
            String amt = util.nvl((String)udf.getValue("amt"));
            String xrt = util.nvl((String)udf.getValue("xrt"));
            int ct=0;
            if(!amt.equals("")){
            try {
                db.setAutoCommit(false);
                String gl_ent_seq = "";
                String seqNme = "select GL_ENT_SEQ.nextval from dual";
                ResultSet rs = db.execSql("rfID", seqNme, new ArrayList());
                if (rs.next())
                    gl_ent_seq = rs.getString(1);
                rs.close();
                ArrayList params = new ArrayList();
                String gl_trns_smry =
                    "insert into gl_trns_smry(idn, gl_idn, typ, sub_typ, ref_dte, ref_typ, ref_idn, dys, cts, amt, xrt)" +
                    "select gl_trns_smry_seq.nextval, ? , ?, ?,to_date(?,'dd-MM-yyyy'), ?, ?, ?, ?, ?, ? from dual";
                params = new ArrayList();
                params.add(byrIdn);
                params.add(type);
                params.add(subType);
                params.add(refdte);
                params.add(refTyp);
                params.add(refIdn);
                params.add(dys);
                params.add(cts);
                params.add(amt);
                params.add(xrt);
                
                 ct = db.execCall("gl_trns_smry", gl_trns_smry, params);
                if (ct > 0) {
                    String gl_entries =
                        " insert into gl_entries(idn, gl_idn, t_dte, typ, sub_typ, fctr, ref_dte, ref_typ, ref_idn, dys,  cts, amt, xrt,ent_seq)" +
                        "select  gl_entries_seq.nextval, ? , sysdate , ?, ? ,?, to_date(?,'dd-MM-yyyy'), ?, ?, ?, ?, ?, ?, ? from dual";
                    params = new ArrayList();
                    params.add(byrIdn);
                    params.add(type);
                    params.add(subType);
                    if (type.equals("PURCHASE"))
                        params.add("1");
                    else
                        params.add("-1");
                    params.add(refdte);
                    params.add(refTyp);
                    params.add(refIdn);
                    params.add(dys);
                    params.add(cts);
                    params.add(amt);
                    params.add(xrt);
                    params.add(gl_ent_seq);
                    ct = db.execCall("gl_entries", gl_entries, params);
                }
                if (ct > 0) {
                    String gl_entries =
                        "insert into gl_entries(idn, gl_idn, t_dte, typ, sub_typ, fctr, ref_dte, ref_typ, ref_idn, dys,  cts, amt, xrt, ent_seq)" +
                        " select  gl_entries_seq.nextval, gl.idn , sysdate , ?, ? ,?, to_date(?,'dd-MM-yyyy'), ?, ?, ?, ?, ?, ?, ? from gl_mst gl where gl.nm =?";
                    params = new ArrayList();
                    params.add(type);
                    params.add("SELF");
                    if (type.equals("PURCHASE"))
                        params.add("-1");
                    else
                        params.add("1");
                    params.add(refdte);
                    params.add(refTyp);
                    params.add(refIdn);
                    params.add(dys);
                    params.add(cts);
                    params.add(amt);
                    params.add(xrt);
                    params.add(gl_ent_seq);
                    params.add(type);
                    ct = db.execCall("gl_entries", gl_entries, params);

                }
            } catch (SQLException sqle) {
                msg="Some error in process..";
                // TODO: Add catch code
                sqle.printStackTrace();
                db.doRollBack();
                }finally{
                    db.setAutoCommit(true);
                }
            }
            if(ct>0){
            msg="Process done succesfully..";
            udf.reset();
            db.doCommit();
            } else{db.doRollBack();
                
               msg="Some error in process..";
           }
            req.setAttribute("msg", msg);
            return am.findForward("load");   
        }
    }
    public ManualEntriesAction() {
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
                util.updAccessLog(req,res,"Manual Entry", "Unauthorized Access");
                else
                util.updAccessLog(req,res,"Manual Entry", "init");
            }
            }
            return rtnPg;
            }
    
}
