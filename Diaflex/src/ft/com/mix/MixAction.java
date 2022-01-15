package ft.com.mix;

import ft.com.DBMgr;
import ft.com.DBUtil;
import ft.com.InfoMgr;

import ft.com.generic.GenericImpl;
import ft.com.lab.LabComparisonRtnForm;

import java.io.IOException;

import java.util.ArrayList;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

public class MixAction extends DispatchAction {
   
    public MixAction() {
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
          util.updAccessLog(req,res,"MixAction", "load");
        MixInterface     mixInterface = new MixActionImpl();
        MixForm  mixForm = (MixForm)af;
        mixForm.resetALL();
       ArrayList empList = mixInterface.getEmp(req, res);
       mixForm.setEmpList(empList);
       ArrayList prcList = mixInterface.getPrc(req, res);
       mixForm.setPrcList(prcList);
          util.updAccessLog(req,res,"MixAction", "load end");
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
          util.updAccessLog(req,res,"MixAction", "fecth");
       MixForm mixForm = (MixForm)af;
          MixInterface     mixInterface = new MixActionImpl();
        String prcId = util.nvl((String)mixForm.getValue("mprcIdn"));
        String stoneId = util.nvl((String)mixForm.getValue("vnmLst"));
        String empId = util.nvl((String)mixForm.getValue("empIdn"));
        String issueId = util.nvl((String)mixForm.getValue("issueId"));
        
        HashMap params = new HashMap();
        params.put("stt", "MX_IS");
        params.put("vnm",stoneId);
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
        mixForm.setValue("prcId", String.valueOf(prcId));
        mixForm.setValue("empId", empId);
          util.updAccessLog(req,res,"MixAction", "fetch end");
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
              util.updAccessLog(req,res,"Mix Action", "Unauthorized Access");
              else
          util.updAccessLog(req,res,"Mix Action", "init");
          }
          }
          return rtnPg;
       }
   
}
