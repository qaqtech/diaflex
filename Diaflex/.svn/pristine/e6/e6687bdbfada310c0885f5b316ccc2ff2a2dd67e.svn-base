package ft.com.dashboard;

import ft.com.DBMgr;
import ft.com.DBUtil;
import ft.com.InfoMgr;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

public class DashBoardHomeAction extends DispatchAction {
    public DashBoardHomeAction() {
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
        HashMap dbinfo = info.getDmbsInfoLst();
        String dashboardon =util.nvl((String)dbinfo.get("DASHBOARDON"));
        if(dashboardon.equals("Y")){
        HashMap allPageDtl= (info.getPageDetails() == null)?new HashMap():(HashMap)info.getPageDetails();
        HashMap pageDtl=(HashMap)allPageDtl.get("DASH_BOARD");
        if(pageDtl==null || pageDtl.size()==0){
            pageDtl=new HashMap();
            pageDtl=util.pagedefdashboard("DASH_BOARD");
            allPageDtl.put("DASH_BOARD",pageDtl);
        }
        info.setPageDetails(allPageDtl);
        
           ArrayList pageLst=util.dashboardvisibility();
            info.setDashboardvisibility(pageLst);
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
          util.updAccessLog(req,res,"User Rights", "init");
          }
          }
          return rtnPg;
          }
}
