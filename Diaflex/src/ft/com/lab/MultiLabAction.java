package ft.com.lab;

import ft.com.DBMgr;
import ft.com.DBUtil;
import ft.com.InfoMgr;
import ft.com.dao.labDet;
import java.sql.Connection;
import ft.com.generic.GenericImpl;
import ft.com.generic.GenericInterface;

import ft.com.marketing.SearchQuery;

import java.io.IOException;

import java.sql.CallableStatement;

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

public class MultiLabAction extends DispatchAction{
    public ActionForward load(ActionMapping am,ActionForm form,HttpServletRequest req,HttpServletResponse res) throws Exception {
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
            util.updAccessLog(req,res,"Multi Lab Selection", "load start");
        MultiLabForm labSelectForm = (MultiLabForm)form;
        GenericInterface genericInt = new GenericImpl();
        labSelectForm.resetAll();
            util.updAccessLog(req,res,"Multi Lab Selection", "load end");
        return am.findForward("load");
        }
    }
    public ActionForward view(ActionMapping am,ActionForm form,HttpServletRequest req,HttpServletResponse res) throws Exception {
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
            util.updAccessLog(req,res,"Multi Lab Selection", "view start");
        MultiLabForm labSelectForm = (MultiLabForm)form;
        MultiLabInterface labSelectInt=new MultiLabImpl();   
        labSelectInt.FetchResult(req ,res, labSelectForm );
        req.setAttribute("view", "Y");
        util.updAccessLog(req,res,"Multi Lab Selection", "view end");
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
            util.updAccessLog(req,res,"Multi Lab Selection", "save start");
        MultiLabForm labSelectForm = (MultiLabForm)form;
        ArrayList stkIdnLst = (ArrayList)session.getAttribute("multilabstkIdnLst");
        HashMap pktDtlMap =  (HashMap)session.getAttribute("multilabpktDtlMap");
        ArrayList ary = new ArrayList();
        String msg="";
        if(stkIdnLst!=null && stkIdnLst.size()>0){
            for(int i=0;i<stkIdnLst.size();i++){
                String stkIdn=util.nvl((String)stkIdnLst.get(i));
                ArrayList pktList=(ArrayList)pktDtlMap.get(stkIdn);
                String ischeck = util.nvl((String)labSelectForm.getValue(stkIdn));
                if(ischeck.equals("yes")){
                    String labgrp = util.nvl((String)labSelectForm.getValue("RD_"+stkIdn));
                    labgrp = labgrp.substring(0,labgrp.indexOf("_"));
                    ArrayList out = new ArrayList();
                    out.add("I");
                    ary = new ArrayList();
                    ary.add(stkIdn);
                    ary.add(labgrp);
                    ary.add("MKAV");
                    ary.add("Y");
                   CallableStatement ct = db.execCall("final lab sel", "ISS_RTN_PKG.FNL_LAB_SEL(pStkIdn =>?, pGrp => ? ,  pStt =>? ,pPktLabUpd => ?, lCnt=> ?)", ary , out);
                  int cnt = ct.getInt(ary.size()+1);
                  if(cnt > 0){
                      if(pktList !=null && pktList.size() > 0){
                          for(int l=0;l<pktList.size();l++){
                          HashMap pktPrpMap = (HashMap)pktList.get(l);
                          String lab = util.nvl((String)pktPrpMap.get("lab"));
                          String grp = util.nvl((String)pktPrpMap.get("grp"));
                          String fldNm = lab+"_"+stkIdn+"_"+grp;
                          String fldVal = util.nvl((String)labSelectForm.getValue(fldNm));
                          ary = new ArrayList();
                          if(fldVal.equals("yes")){
                              ary.add("A");
                         }else{
                             ary.add("IA");       
                         }
                            ary.add(stkIdn);
                            ary.add(grp);
                            ary.add(lab);
                        String updateSTKRte = "update PKT_LAB_DTL set stt = ? where stk_idn = ? and grp=? and lab=? and grp <> 1 ";
                        int ct1 = db.execUpd("update", updateSTKRte, ary);
                      }}
                  }
                }
                
            }
            
        }
        
       req.setAttribute("msg", msg);
            util.updAccessLog(req,res,"Multi Lab Selection", "save end");
      return load(am, form, req, res);
        }
    }
    public MultiLabAction() {
        super();
    }
    public ActionForward labDtl(ActionMapping am,ActionForm form,HttpServletRequest req,HttpServletResponse res) throws Exception {
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
            util.updAccessLog(req,res,"Multi Lab Selection", "loadDtl start");
            MultiLabForm labSelectForm = (MultiLabForm)form;
            MultiLabInterface labSelectInt=new MultiLabImpl();
       String stkIdn = req.getParameter("mstkIdn");
       String mdl = util.nvl(req.getParameter("mdl"));
       ArrayList ary = new ArrayList();
       if(mdl.equals("")){
           String sql = "select * from mstk where pkt_ty <> 'NR' and idn = ?";
           ary.add(stkIdn);
           ArrayList outLst = db.execSqlLst("pktTy", sql, ary);
           PreparedStatement pst = (PreparedStatement)outLst.get(0);
           ResultSet rs = (ResultSet)outLst.get(1);
           if(rs.next())
               mdl="BOX_VIEW";
            else
               mdl="MEMO_VW";
           rs.close(); pst.close();
       }
         
       HashMap param = new HashMap();
       param.put("stkIdn", stkIdn);
       param.put("mdl", mdl);
       labSelectInt.labDetail(req,res , param);
       req.setAttribute("mdl", mdl);
        ArrayList labViewList =LabPrprViwMdl(req,res,mdl);
            util.updAccessLog(req,res,"Multi Lab Selection", "loadDtl end");
        return am.findForward("loadDtl");
        }
    }
    
    public ArrayList LabPrprViwMdl(HttpServletRequest req , HttpServletResponse res , String mdl){
        HttpSession session = req.getSession(false);
        InfoMgr info = (InfoMgr)session.getAttribute("info");
        DBUtil util = new DBUtil();
        DBMgr db = new DBMgr(); 
        db.setCon(info.getCon());
        util.setDb(db);
        util.setInfo(info);
        db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
        util.setLogApplNm(info.getLoApplNm());
        ArrayList viewPrp = null;
        try {
            if (viewPrp == null) {

                viewPrp = new ArrayList();
                ResultSet rs1 =
                    db.execSql(" Vw Lst ", "Select prp  from rep_prp where mdl = '"+mdl+"' and flg='Y' order by rnk ",
                               new ArrayList());
                while (rs1.next()) {

                    viewPrp.add(rs1.getString("prp"));
                }
                rs1.close();
                session.setAttribute(mdl, viewPrp);
            }

        } catch (SQLException sqle) {
            // TODO: Add catch code
            sqle.printStackTrace();
        }
        
        return viewPrp;
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
                util.updAccessLog(req,res,"Multi Lab Selection", "Unauthorized Access");
                else
                util.updAccessLog(req,res,"Multi Lab Selection", "init");
            }
            }
            return rtnPg;
            }
}
