package ft.com;

import java.io.File;
import java.io.FileOutputStream;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts.upload.FormFile;

public class MassMail extends Thread {
  DBMgr                db;
  InfoMgr              info;
  HttpSession          session;
  DBUtil               util;
  HttpServletRequest req ;
  public void run()
  {
  sendMail();
  }
  public MassMail(HttpServletRequest req) {
       session = req.getSession(false);
       info = (InfoMgr)session.getAttribute("info");
       util = new DBUtil();
       db = new DBMgr(); 
       db.setCon(info.getCon());
       util.setDb(db);
       util.setInfo(info);
       db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
       util.setLogApplNm(info.getLoApplNm());
       this.req=req;
   }
  
  void sendMail(){
    
    HashMap dbmsInfo = info.getDmbsInfoLst();
      HashMap logDetails=new HashMap();
    GenMail mail = new GenMail();
    ArrayList fileNameV = new ArrayList();
    ArrayList fileContentV = new ArrayList();
    ArrayList attAttachFile = new ArrayList();
    info.setSmtpHost((String)dbmsInfo.get("SMTPHost"));
    info.setSmtpPort(Integer.parseInt((String)dbmsInfo.get("SMTPPort")));
    info.setSmtpUsr((String)dbmsInfo.get("SMTPUsr"));
    info.setSmtpPwd((String)dbmsInfo.get("SMTPPwd"));
    String senderID= util.nvl(req.getParameter("senID"));
    if(senderID.equals(""))
      senderID =(String)dbmsInfo.get("SENDERID");
    String senderNm= util.nvl(req.getParameter("senNme"));
    if(senderNm.equals(""))
      senderNm =(String)dbmsInfo.get("SENDERNM");
   
    String subject= util.nvl(req.getParameter("subject"));
    String msgpost= util.nvl(req.getParameter("msg"));
    String refIdn= util.nvl((String)req.getAttribute("REFIDN"));
      ArrayList attAttachFilNme = (session.getAttribute("attAttachFilNme") == null)?new ArrayList():(ArrayList)session.getAttribute("attAttachFilNme");
      ArrayList attAttachFilTyp = (session.getAttribute("attAttachTyp") == null)?new ArrayList():(ArrayList)session.getAttribute("attAttachTyp");
      ArrayList attAttachFilFile = (session.getAttribute("attAttachFile") == null)?new ArrayList():(ArrayList)session.getAttribute("attAttachFile");
    mail.setInfo(info);
    mail.init();
    
    mail.setSender(senderID, senderNm);
    mail.setSubject(subject);
    mail.setMsgText(msgpost);
    if(attAttachFilNme!=null && attAttachFilNme.size() > 0 ){
        mail.setFileName(attAttachFilNme);
         mail.setAttachmentType(attAttachFilTyp);
        mail.setAttachments(attAttachFilFile);
    }
    ArrayList list = (ArrayList)session.getAttribute("emlList");
    
    if(list!=null && list.size()>0){
      for (int i=0; i < list.size(); i++){
        mail.setTO((String)list.get(i));
        logDetails=new HashMap();
        logDetails.put("BYRID","0");
        logDetails.put("RELID","0");
        logDetails.put("TYP","CONT_MAIL");
        logDetails.put("IDN",refIdn);
        String mailLogIdn=util.mailLogDetails(req,logDetails,"I");  
        logDetails.put("MSGLOGIDN",mailLogIdn);
        logDetails.put("MAILDTL",mail.send(""));
        util.mailLogDetails(req,logDetails,"U");
       mail.initRecipients();
    }}
    session.setAttribute("attAttachFilNme",new ArrayList());
          session.setAttribute("attAttachTyp",new ArrayList()); 
          session.setAttribute("attAttachFile",new ArrayList());  
              
    session.setAttribute("emlList", new ArrayList());
  }
//  public void init(HttpServletRequest req) {
//      session = req.getSession(false);
//      info = (InfoMgr)session.getAttribute("info");
//      util = new DBUtil();
//      db = new DBMgr();
//      db.setCon(info.getCon());
//      util.setDb(db);
//      util.setInfo(info);
//      db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
//      util.setLogApplNm(info.getLoApplNm());
//  }
}
