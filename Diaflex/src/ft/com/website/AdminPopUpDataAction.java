package ft.com.website;

import ft.com.DBMgr;

import ft.com.DBUtil;
import ft.com.InfoMgr;

import java.io.File;

import java.io.FileOutputStream;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import org.apache.struts.upload.FormFile;



public class AdminPopUpDataAction extends DispatchAction{
  
  
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
        util.updAccessLog(request,response,"AdminPopUpData", "store");
    AdminPopUpDataForm popupform = (AdminPopUpDataForm)af;
    
    
    //*****get files and store on server*****
    
    //process background image 
    String filename = uploadFile(popupform.getBgimg());
    
    //process header logo image 
    String headerFileName = uploadFile(popupform.getHeaderlogo());
    
    //process event image 
    String eventFileName = uploadFile(popupform.getEventimage());
    
    //store the names on db
    String sql = "Insert into SHOWDTL\n" + 
    "   (POPUPPAGEFLAG, BACKGDIMG, HEADERLOGO, EVENTLABL, \n" + 
    "    EVENTIMAGE, STARTDATE, ENDDATE, \n" + 
    "    LOGDATE, NME_IDN, SHOWDETAILSFLAG, valid)\n" + 
    " Select " + 
    "   ?, ?, ?, ?, ?, TO_DATE(?, 'DD-MM-YYYY'),\n" + 
    "   TO_DATE(?, 'DD-MM-YYYY'),  \n" + 
    "   sysdate,?, 0, 1  from DUAL";
    
    
    ArrayList params = new ArrayList();
    params.add("1");
    params.add(filename);
    params.add(headerFileName);
    params.add(popupform.getEventLabel().trim());
    params.add(eventFileName);
    params.add(popupform.getValidfrom().trim());
    params.add(popupform.getValidtill().trim());
    params.add(session.getAttribute("uid").toString());
    
    System.out.println("query: "+sql);
    
    try {
      int ct = db.execUpd("Ins pop up dtl", sql , params);
    }
    catch(Exception e) {
      request.setAttribute("success","0" );
      System.out.println("******************exception display********************");
      e.printStackTrace();
    }
    //System.out.println("out of try block, before return");
    
    request.setAttribute("success","1" );
        util.updAccessLog(request,response,"AdminPopUpData", "store end");
    return am.findForward("store");
      }
  }
  
  public String uploadFile(FormFile myFile) throws Exception{
      
      
      String fileName  = myFile.getFileName();
      byte[] fileData  = myFile.getFileData();  
      //Get the servers upload directory real path name  
      //String filePath = "C:\\JDeveloper\\mywork\\TestJBBrosApplication\\JButil\\public_html";  
      String filePath = "D:\\websites\\jbbros\\WWW\\uploadedfiles";
      ///* Save file on the server */  
      
      if(!fileName.equals(""))
      {    
          //System.out.println("Server path:" +filePath);  
          
          //Create file  
          File fileToCreate = new File(filePath, fileName);  
          Boolean filenotcreated = true;
          //If file does not exists create file   
          while(filenotcreated) 
          {
            if(!fileToCreate.exists())
            {  
                FileOutputStream fileOutStream = new FileOutputStream(fileToCreate);  
                fileOutStream.write(myFile.getFileData());  
                fileOutStream.flush();  
                fileOutStream.close();
                filenotcreated = false;
                
            }
            else 
            {
                //filenotcreated = true;
                fileName = "a" + fileName;
                fileToCreate = new File(filePath, fileName); 
            }
          }
          
        System.out.println("filename "+ fileName);
      }
      
      return fileName;
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
              util.updAccessLog(req,res,"AdminPopUpData", "Unauthorized Access");
              else
          util.updAccessLog(req,res,"AdminPopUpData", "init");
          }
          }
          return rtnPg;
          }
    public AdminPopUpDataAction() {
        super();
    }
}
