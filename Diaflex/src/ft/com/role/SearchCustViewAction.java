package ft.com.role;

import ft.com.DBMgr;
import ft.com.DBUtil;
import ft.com.InfoMgr;

import ft.com.dao.RepPrp;

import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.ArrayDeque;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

public class SearchCustViewAction extends  DispatchAction {

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
             SearchCustViewForm udf = (SearchCustViewForm)af;
        
             String mdl="MEMO_VW";
             String usrID = util.nvl(req.getParameter("usrIdn"));
             if(usrID.equals(""))
                 usrID = (String)udf.getValue("usrIdn");
             String cstMdlXl=usrID + "_MEMO_VW";
             ArrayList lefXlPrp = new ArrayList();
             ArrayList rtXlPrp = new ArrayList();
                 String xlSql ="select prp from rep_prp where mdl =? and flg = 'Y' "+
                              "and prp not in (select prp from rep_prp where mdl = ? and flg = 'Y') "+
                              "order by rnk, srt";
               ArrayList  ary = new ArrayList();
               ary.add(mdl);
               ary.add(cstMdlXl);

             ArrayList outLst = db.execSqlLst(" left xl Prp ",xlSql, ary);
             PreparedStatement pst = (PreparedStatement)outLst.get(0);
             ResultSet rs = (ResultSet)outLst.get(1);
               while(rs.next()) {
                    String prp = rs.getString("prp");
                   RepPrp repPrp = new RepPrp();
                   repPrp.setPrp(prp);
                  lefXlPrp.add(repPrp); 
                   
                }
             rs.close(); pst.close();
             udf.setLefXlPrp(lefXlPrp);
             String memoMdl ="select prp from rep_prp where mdl =? and flg = 'Y' "+
                           "order by rnk, srt";
              ary = new ArrayList();
             ary.add(cstMdlXl);

             outLst = db.execSqlLst(" right xl Prp ",memoMdl, ary);
             pst = (PreparedStatement)outLst.get(0);
             rs = (ResultSet)outLst.get(1);
             while(rs.next()) {
                String prp = rs.getString("prp");
                 RepPrp repPrp = new RepPrp();
                 repPrp.setPrp(prp);
              rtXlPrp.add(repPrp); 
                 
             }
             rs.close(); pst.close();
             udf.setRtXlPrp(rtXlPrp);
             udf.setValue("usrIdn", usrID);
             return am.findForward("load");
         }
         }
    public SearchCustViewAction() {
        super();
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
             SearchCustViewForm udf = (SearchCustViewForm)af;
             String usrIdn = util.nvl((String)udf.getValue("usrIdn"));
             String cstMdlXl=usrIdn+"_MEMO_VW";
             String dltXl = "delete from rep_prp WHERE mdl=? ";
              ArrayList  ary = new ArrayList();
              ary.add(cstMdlXl);
             int  ct = db.execUpd("DLT xl PRP",dltXl,ary);  
              String[] rightPrp = udf.getRightPrp();
             if(rightPrp.length>0){
                 for(int i=0;i<rightPrp.length;i++){
                     String lprp = rightPrp[i];
                          ary = new ArrayList();
                          int srt = (i+1)*10;
                          ary.add(cstMdlXl);
                          ary.add(lprp);
                          ary.add(Integer.toString(srt));
                          String sqlXl = "insert into rep_prp(mdl, prp, srt, flg) select ?,?,?, 'Y' from dual";
                          ct = db.execUpd("xlPRP",sqlXl, ary);
                  }
                 if(ct>0){
                         String updXlPrp = "UPD_REP_PRP_RNK(?)";
                         ary = new ArrayList();
                         ary.add(cstMdlXl);
                         ct = db.execCall("UpdateXLPrp",updXlPrp,ary);
                 }
                 
             }
              
             return load(am, af, req, res);
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
        util.updAccessLog(req,res,"Box to Box", "Unauthorized Access");
        else
        util.updAccessLog(req,res,"Menu Role Deatail", "init");
    }
    }
    return rtnPg;
    }
}
