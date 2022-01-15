package ft.com.website;

import ft.com.DBMgr;

import ft.com.DBUtil;
import ft.com.InfoMgr;

import java.sql.Date;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;



public class AdminShowDetailsAction extends DispatchAction{
    
  
 public ActionForward load(ActionMapping am, ActionForm af, HttpServletRequest request, HttpServletResponse response) throws Exception {
      HttpSession session = request.getSession(false);
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
      rtnPg=init(request,response,session,util);
      }else
      rtnPg="sessionTO";
      if(!rtnPg.equals("sucess")){
          return am.findForward(rtnPg);   
      }else{
        return am.findForward("store");
        }
    }
  public ActionForward store(ActionMapping am, ActionForm af, HttpServletRequest request, HttpServletResponse response) throws Exception {
    HttpSession session = request.getSession(false);
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
    rtnPg=init(request,response,session,util);
    }else
    rtnPg="sessionTO";
    if(!rtnPg.equals("sucess")){
        return am.findForward(rtnPg);   
    }else{
        util.updAccessLog(request,response,"AdminShowDetails", "store");
    AdminShowDetailsForm showform = (AdminShowDetailsForm)af;
    
    ArrayList params = new ArrayList();
    params.add("1");
    params.add(showform.getStrShowName().trim());
    params.add(showform.getStrCompanyName().trim());
    params.add(showform.getStrBoothNo().trim());
    params.add(showform.getDtStartDate().trim());
    params.add(showform.getDtEndDate().trim());
    params.add(showform.getStrVenue1().trim());
    params.add(showform.getStrVenue2().trim());
    params.add(showform.getStrVenue3().trim());
    params.add(session.getAttribute("uid").toString());
    
    /*String strShowName = showform.getStrShowName();
    String strCompanyName = showform.getStrCompanyName();
    String strBoothNo = showform.getStrBoothNo();
    String strVenue1 = showform.getStrVenue1();
    String strVenue2 = showform.getStrVenue2();
    String strVenue3 = showform.getStrVenue3();
    String dtStartDate = showform.getDtStartDate();
    String dtEndDate = showform.getDtEndDate();
    String strUser = session.getAttribute("uid").toString();
    int flag;*/
    
    /*String sql = "Insert into SHOWDTL\n" + 
    "   (SHOWDETAILSFLAG, SHOWNAME, COMPANYNAME, BOOTHNO, SHOWSTARTDATE, \n" + 
    "    SHOWENDDATE, VENUELINE1, VENUELINE2, VENUELINE3, POPUPPAGEFLAG, \n" + 
    "    LOGDATE, NME_IDN)\n" + 
    " Values\n" + 
    "   (1, '"+strShowName+"', '"+strCompanyName+"', '"+strBoothNo+"', TO_DATE('"+dtStartDate+"', 'DD-MM-YYYY'),\n" + 
    "   TO_DATE('"+dtEndDate+"', 'DD-MM-YYYY'), '"+strVenue1+"', '"+strVenue2+"', '"+strVenue3+"', 0, \n" + 
    "   sysdate, "+strUser+")";*/
    
    String sql = "Insert into SHOWDTL\n" + 
    "   (SHOWDETAILSFLAG, SHOWNAME, COMPANYNAME, BOOTHNO, STARTDATE, \n" + 
    "    ENDDATE, VENUELINE1, VENUELINE2, VENUELINE3, POPUPPAGEFLAG, \n" + 
    "    LOGDATE, NME_IDN, valid)\n" + 
    " Select " + 
    "   ?, ?, ?, ?, TO_DATE(?, 'DD-MM-YYYY'),\n" + 
    "   TO_DATE(?, 'DD-MM-YYYY'), ?,?,?, 0, \n" + 
    "   sysdate,?, 1  from DUAL";
    
    System.out.println("query: "+sql);
    
    try {
      int ct = db.execUpd("Ins showdtl", sql , params);
    }
    catch(Exception e) {
      request.setAttribute("success","0" );
      System.out.println("******************exception display********************");
      e.printStackTrace();
    }
    //System.out.println("out of try block, before return");
    
    request.setAttribute("success","1" );
        util.updAccessLog(request,response,"AdminShowDetails", "store end");
    return am.findForward("store");
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
              util.updAccessLog(req,res,"AdminShowDetails", "Unauthorized Access");
              else
          util.updAccessLog(req,res,"AdminShowDetails", "init");
          }
          }
          return rtnPg;
          }
    
    public AdminShowDetailsAction() {
        super();
    }
}
