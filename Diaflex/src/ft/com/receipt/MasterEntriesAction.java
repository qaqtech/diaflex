package ft.com.receipt;

import ft.com.DBMgr;
import ft.com.DBUtil;
import ft.com.GtMgr;
import ft.com.InfoMgr;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

public class MasterEntriesAction extends  DispatchAction {
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
            MasterEntriesForm udf = (MasterEntriesForm)form;
            udf.resetAll();
            ArrayList dtlList=GL_MST(db, util);
            req.setAttribute("DTLLIST", dtlList);
            return am.findForward("load");
        }
    }
    public ActionForward save(ActionMapping am,ActionForm form,HttpServletRequest req,HttpServletResponse res) throws Exception {
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
            MasterEntriesForm udf = (MasterEntriesForm)form;
            String idn=util.nvl((String)udf.getValue("IDN"));
            String nme=util.nvl((String)udf.getValue("nme"));
            String cd=util.nvl((String)udf.getValue("cd"));
            String transYN=util.nvl((String)udf.getValue("transYN"));
            String entryPnt=util.nvl((String)udf.getValue("entryPnt"));
            String mob=util.nvl((String)udf.getValue("mob"));
            String eml=util.nvl((String)udf.getValue("eml"));
           
            String gl_mst=" INSERT INTO GL_MST(IDN, TYP, TRNS_YN, ENTRY_POINT, NM, CD,MBL,EML) values(gl_mst_seq.nextval, 'NR', ?, ?, ?,?,?,? )";
            ArrayList ary = new ArrayList();
            ary.add(transYN);
            ary.add(entryPnt);
            ary.add(nme);
            ary.add(cd);
            ary.add(mob);
            ary.add(eml);
            if(!idn.equals("")){
                gl_mst=" UPDATE  GL_MST set  TRNS_YN=? , ENTRY_POINT=? , NM = ? , CD = ? ,MBL =? ,EML =? where idn=?";
                ary = new ArrayList();
                ary.add(transYN);
                ary.add(entryPnt);
                ary.add(nme);
                ary.add(cd);
                ary.add(mob);
                ary.add(eml);
                ary.add(idn);
            }
           int ct = db.execUpd("gl_mst", gl_mst, ary);
            if(ct>0)
                req.setAttribute("msg", "Record save successfully!!!");
            else
                req.setAttribute("msg", "Some error in process!!!");
            return am.findForward("load");
        }
    }
    public ActionForward Edit(ActionMapping am,ActionForm form,HttpServletRequest req,HttpServletResponse res) throws Exception {
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
            MasterEntriesForm udf = (MasterEntriesForm)form;
            udf.resetAll();
            String idn = req.getParameter("IDN");
            String glMstSql="select idn,nm,cd , mbl,eml,TRNS_YN,ENTRY_POINT  from GL_MST where idn=?";
            ArrayList ary = new ArrayList();
            ary.add(idn);
            ArrayList rsLst = db.execSqlLst("data sql", glMstSql,ary);
            PreparedStatement psmt = (PreparedStatement)rsLst.get(0);
            ResultSet rs = (ResultSet)rsLst.get(1);
            while (rs.next()) {
                udf.setValue("nme", util.nvl(rs.getString("nm")));
                udf.setValue("cd", util.nvl(rs.getString("cd")));
                udf.setValue("transYN", util.nvl(rs.getString("TRNS_YN")));
                udf.setValue("mob", util.nvl(rs.getString("mbl")));
                udf.setValue("eml", util.nvl(rs.getString("eml")));
                udf.setValue("entryPnt", util.nvl(rs.getString("ENTRY_POINT")));
                udf.setValue("IDN", idn);
                
            }
            rs.close();
            psmt.close();
            ArrayList dtlList=GL_MST(db, util);
            req.setAttribute("DTLLIST", dtlList);
            return am.findForward("load");
        }
    }
    public ArrayList GL_MST(DBMgr db,DBUtil util){
        
        ArrayList dtlList = new ArrayList();
        String glMstSql="select idn,nm,cd , mbl,eml,TRNS_YN,ENTRY_POINT  from GL_MST where ENTRY_POINT=? order by typ";
        ArrayList ary = new ArrayList();
        ary.add("DFLT");
        ArrayList rsLst = db.execSqlLst("data sql", glMstSql,ary);
        try {
            PreparedStatement psmt = (PreparedStatement)rsLst.get(0);
            ResultSet rs = (ResultSet)rsLst.get(1);
            while (rs.next()) {
                HashMap dtlMap = new HashMap();
                dtlMap.put("nme", util.nvl(rs.getString("nm")));
                dtlMap.put("cd", util.nvl(rs.getString("cd")));
                dtlMap.put("transYN", util.nvl(rs.getString("TRNS_YN")));
                dtlMap.put("entryPnt", util.nvl(rs.getString("ENTRY_POINT")));
                dtlMap.put("IDN", util.nvl(rs.getString("idn")));
                dtlList.add(dtlMap);
            }
            rs.close();
            psmt.close();
        } catch (SQLException sqle) {
            // TODO: Add catch code
            sqle.printStackTrace();
        } finally {

        }
        
        return dtlList;
    }
    public MasterEntriesAction() {
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
                util.updAccessLog(req,res,"Master Entry", "Unauthorized Access");
                else
                util.updAccessLog(req,res,"Master Entry", "init");
            }
            }
            return rtnPg;
            }
    
}
