package ft.com.mix;

import ft.com.DBMgr;
import ft.com.DBUtil;
import ft.com.InfoMgr;

import ft.com.assort.AssortIssueForm;

import java.io.IOException;

import java.sql.CallableStatement;

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

public class MixReturnAction extends DispatchAction {
  
   
    public MixReturnAction() {
        super();
    }
    public ActionForward loadrtn(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res)
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
          util.updAccessLog(req,res,"Mix Return", "loadRtn");
          MixReturnInterface  mixInterface = new MixReturnImpl();
         MixReturnForm  udf = (MixReturnForm)af;
       
        udf.resetAll();
       ArrayList empList = mixInterface.getEmp(req, res);
       udf.setEmpList(empList);
       ArrayList prcList = mixInterface.getPrc(req, res);
       udf.setPrcList(prcList);
          util.updAccessLog(req,res,"Mix Return", "loadRtn end");
      return am.findForward("load");
        }
    }
    public ActionForward fetch(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res)
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
          util.updAccessLog(req,res,"Mix Return", "fetch");
          MixReturnInterface  mixInterface = new MixReturnImpl();
         MixReturnForm  udf = (MixReturnForm)af;
        String prcId = util.nvl((String)udf.getValue("mprcIdn"));
        String empId = util.nvl((String)udf.getValue("empIdn"));
        String issueId = util.nvl((String)udf.getValue("issueId"));
        
        HashMap params = new HashMap();
        params.put("stt", "MX_IS");
        params.put("empIdn", empId);
        params.put("issueId", issueId);
        params.put("mprcIdn", String.valueOf(prcId));
        ArrayList stockList = mixInterface.FecthResult(req,res, params);
        session.setAttribute("StockList", stockList);
        if(stockList.size()>0){
        HashMap totals =  mixInterface.GetTotal(req,res);
        req.setAttribute("totalMap", totals);
        }
        req.setAttribute("view", "Y");
        udf.setValue("prcId", String.valueOf(prcId));
        udf.setValue("empId", empId);
          util.updAccessLog(req,res,"Mix Return", "fetch end");
      return am.findForward("load");
        }
    }
    
    public ActionForward IssueRtn(ActionMapping am,ActionForm form,HttpServletRequest req,HttpServletResponse res) throws Exception {
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
          util.updAccessLog(req,res,"Mix Return", "issueRtn");
          MixReturnInterface  mixInterface = new MixReturnImpl();
         MixReturnForm  udf = (MixReturnForm)form;
        
        String prcId = (String)udf.getValue("prcId");
        String empId = (String)udf.getValue("empId");
        ArrayList params = null;
        int issueIdn = 0;
        ArrayList stockList = (ArrayList)session.getAttribute("StockList");
        for(int i=0;i< stockList.size();i++){
            HashMap stockPkt = (HashMap)stockList.get(i);
             String issIdn = (String)stockPkt.get("issIdn");
             String isChecked = util.nvl((String)udf.getValue(issIdn));
            if(isChecked.equals("yes")){
                String rtnCts=util.nvl((String)udf.getValue("RTNCTS_"+issIdn));
                String rtnQty=util.nvl((String)udf.getValue("RTNQTY_"+issIdn));
                params = new ArrayList();
                params.add(issIdn);
                params.add(rtnCts);
                params.add(rtnQty);
                String issuePkt = "MIX_PKG.MIX_ISS_ID_RTN(pIssId => ?, pRtnCts => ?,  pRtnQty => ?)";
                int ct = db.execCall("issuePkt", issuePkt, params);
                }
       }
    
     udf.reset();
          util.updAccessLog(req,res,"Mix Return", "issueRtn end");
        return am.findForward("load");
        }
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
          MixReturnInterface  mixInterface = new MixReturnImpl();
         MixReturnForm  udf = (MixReturnForm)af;
        ArrayList mixSizeList = mixInterface.MixSizeList(req, res);
        
        udf.setMixSizeList(mixSizeList);
    return am.findForward("load");
        }
    }
    public ActionForward loadGrid(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res)
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
          MixReturnInterface  mixInterface = new MixReturnImpl();
         MixReturnForm  udf = (MixReturnForm)af;
        String[] sizeLst = (String[])udf.getValue("sizeLst");
        HashMap sizeClrMap = new HashMap();
        for(int i=0 ; i < sizeLst.length ; i++){
            
        }
    
        return am.findForward("load");
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
              util.updAccessLog(req,res,"Mix Rtn", "Unauthorized Access");
              else
          util.updAccessLog(req,res,"Mix Rtn", "init");
          }
          }
          return rtnPg;
          }
}
