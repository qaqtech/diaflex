package ft.com.masters;

import ft.com.DBMgr;
import ft.com.DBUtil;
import ft.com.InfoMgr;
import ft.com.assort.AssortIssueImpl;
import ft.com.assort.AssortIssueInterface;
import ft.com.dao.UIForms;
import ft.com.mixakt.MixAssortReturnAction;
import ft.com.report.AssortLabPendingForm;

import java.sql.Connection;

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

    public class MatchPairAction extends DispatchAction {
    public MatchPairAction() {
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
         util.updAccessLog(req,res,"Match Pair", "load start");
         MatchPairForm udf = (MatchPairForm)af;
         udf.reset();
         ArrayList matchpairprpList=new ArrayList();
         HashMap dtl=new HashMap();
         ArrayList matchpairPrp=matchpairPrp(req,res);
         udf.setValue("matchpairPrp", matchpairPrp);
         HashMap mprp = info.getSrchMprp();
         HashMap prp = info.getSrchPrp();
         String sqlQ="select idn,mprp,val_frm,val_to,var_frm,var_to,slab from MATCH_PAIR_CRT where vld_to is null order by idn"  ;
         ResultSet rs = db.execSql("MATCH_PAIR_CRT", sqlQ, new ArrayList());   
         while (rs.next()) {
             dtl=new HashMap();
             String idn=util.nvl(rs.getString("idn"));
             String lprp=util.nvl(rs.getString("mprp"));
             String lprpTyp = util.nvl((String)mprp.get(lprp+"T")); 
             dtl.put("idn", idn);
             dtl.put("mprp", lprp);
             udf.setValue(idn+"_mprp", util.nvl(rs.getString("mprp")));
             if(lprpTyp.equals("C")){
             udf.setValue(idn+"_val_frmC", util.nvl(rs.getString("val_frm")));
             udf.setValue(idn+"_val_toC", util.nvl(rs.getString("val_to")));
             }else{
             udf.setValue(idn+"_val_frm", util.nvl(rs.getString("val_frm")));
             udf.setValue(idn+"_val_to", util.nvl(rs.getString("val_to")));  
             }
             udf.setValue(idn+"_var_frm", util.nvl(rs.getString("var_frm")));
             udf.setValue(idn+"_var_to", util.nvl(rs.getString("var_to")));
             udf.setValue(idn+"_slab", util.nvl(rs.getString("slab")));
             matchpairprpList.add(dtl);
         }
         rs.close();
         session.setAttribute("matchpairprpList", matchpairprpList);
         util.updAccessLog(req,res,"Match Pair", "load end");
         return am.findForward("load");
         }
     }
    
    public ActionForward add(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res)
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
         util.updAccessLog(req,res,"Match Pair", "add start");
         MatchPairForm udf = (MatchPairForm)af;
         HashMap mprp = info.getSrchMprp();
         HashMap prp = info.getSrchPrp();
         ArrayList ary = new ArrayList();
                     String insertQ="insert into MATCH_PAIR_CRT(idn,slab,mprp,val_frm,val_to,var_frm,var_to,vld_frm)\n" + 
                     "values(MATCH_PAIR_CRT_SEQ.nextval,?,?,?,?,?,?,sysdate)";   
                     String lprp=util.nvl((String)udf.getValue("mprp"));
                     String lprpTyp = util.nvl((String)mprp.get(lprp+"T"));
                     ary = new ArrayList();
                     ary.add(util.nvl((String)udf.getValue("slab")));
                     ary.add(lprp);
                     if(lprpTyp.equals("C")){
                     ary.add(util.nvl((String)udf.getValue("val_frmC")));
                     ary.add(util.nvl((String)udf.getValue("val_toC")));
                     }else{
                     ary.add(util.nvl((String)udf.getValue("val_frm")));
                     ary.add(util.nvl((String)udf.getValue("val_to")));   
                     }
                     ary.add(util.nvl((String)udf.getValue("var_frm"),"0"));
                     ary.add(util.nvl((String)udf.getValue("var_to"),"0"));
                     int ct = db.execUpd("insrt", insertQ, ary);
                     if(ct>0)
                     req.setAttribute("rtnmsg", "Criteria Save Sucessfully");
         util.updAccessLog(req,res,"Match Pair", "add end");
         return load(am,af,req,res);
         }
     }
    
    public ActionForward update(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res)
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
         util.updAccessLog(req,res,"Match Pair", "update start");
         MatchPairForm udf = (MatchPairForm)af;
         HashMap mprp = info.getSrchMprp();
         HashMap prp = info.getSrchPrp();
         ArrayList ary = new ArrayList();
         ArrayList  matchpairprpList= (session.getAttribute("matchpairprpList") == null)?new ArrayList():(ArrayList)session.getAttribute("matchpairprpList");
         String updateQ="update MATCH_PAIR_CRT set mprp=?,val_frm=?,val_to=?,var_frm=?,var_to=?,slab=? where idn=?";   
             if(matchpairprpList.size()>0){
                 for(int i=0;i<matchpairprpList.size();i++){
                 HashMap dtl=(HashMap)matchpairprpList.get(i);
                 String idn=util.nvl((String)dtl.get("idn"));
                 String chk=util.nvl((String)udf.getValue(idn));
                 if(chk.equals("Y")){
                     String lprp=util.nvl((String)udf.getValue(idn+"_mprp"));
                     String lprpTyp = util.nvl((String)mprp.get(lprp+"T"));
                     ary = new ArrayList();
                     ary.add(lprp);
                     if(lprpTyp.equals("C")){
                     ary.add(util.nvl((String)udf.getValue(idn+"_val_frmC")));
                     ary.add(util.nvl((String)udf.getValue(idn+"_val_toC")));
                     }else{
                     ary.add(util.nvl((String)udf.getValue(idn+"_val_frm")));
                     ary.add(util.nvl((String)udf.getValue(idn+"_val_to")));   
                     }
                     ary.add(util.nvl((String)udf.getValue(idn+"_var_frm"),"0"));
                     ary.add(util.nvl((String)udf.getValue(idn+"_var_to"),"0"));
                     ary.add(util.nvl((String)udf.getValue(idn+"_slab"),"0"));
                     ary.add(idn);
                     int ct = db.execUpd("UpdateCriteria", updateQ, ary);
                     if(ct>0)
                     req.setAttribute("rtnmsg", "Criteria Save Sucessfully");
                 }
                 }
             }
         util.updAccessLog(req,res,"Match Pair", "update end");
         return load(am,af,req,res);
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
         util.updAccessLog(req,res,"Match Pair", "delete start");
             ArrayList ary = new ArrayList();
             String crtId = util.nvl((String)req.getParameter("idn"));
             ary.add(crtId);
             String removeCrt =" update MATCH_PAIR_CRT set vld_to=sysdate where idn=?";
             int ct = db.execUpd("RemoveCriteria", removeCrt, ary);
             if(ct>0)
             req.setAttribute("rtnmsg", "Criteria Idn :-"+crtId+" Deleted Sucessfully");
             util.updAccessLog(req,res,"Match Pair", "delete end");
             return load(am,af,req,res);
         }
     }
    public ArrayList matchpairPrp( HttpServletRequest req , HttpServletResponse res){
        HttpSession session = req.getSession(false);
        InfoMgr info = (InfoMgr)session.getAttribute("info");
        DBUtil util = new DBUtil();
        DBMgr db = new DBMgr(); 
        db.setCon(info.getCon());
        util.setDb(db);
        util.setInfo(info);
        db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
        util.setLogApplNm(info.getLoApplNm());
        ArrayList matchpairPrp = (session.getAttribute("matchpairPrp") == null)?new ArrayList():(ArrayList)session.getAttribute("matchpairPrp");
        try {
        if(matchpairPrp.size() == 0) {
                String    memoPrntOptn = "select a.prp ,a.prt_nme txt from mprp a,rep_prp b where a.prp=b.prp and b.mdl='MATCH_PAIRVW' order by a.srt";
        ResultSet rs = db.execSql("memoPrint", memoPrntOptn, new ArrayList());
            while (rs.next()) {
                UIForms memoOpn = new UIForms();
                memoOpn.setFORM_NME(rs.getString("prp"));
                memoOpn.setFORM_TTL(rs.getString("txt"));
                matchpairPrp.add(memoOpn);
            }
            rs.close();
                session.setAttribute("matchpairPrp", matchpairPrp);
            }
        } catch (SQLException sqle) {
            // TODO: Add catch code
            sqle.printStackTrace();
        }
        
        return matchpairPrp;
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
                util.updAccessLog(req,res,"Match Pair", "Unauthorized Access");
                else
            util.updAccessLog(req,res,"Match Pair", "init");
            }
            }
            return rtnPg;
            }
}
