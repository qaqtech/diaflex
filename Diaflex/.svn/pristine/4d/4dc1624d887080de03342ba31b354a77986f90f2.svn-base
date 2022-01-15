package ft.com.contact;

import ft.com.DBMgr;
import ft.com.DBUtil;
import ft.com.GenMail;
import ft.com.ImageChecker;
import ft.com.InfoMgr;

import ft.com.MassMail;
import ft.com.marketing.SearchForm;

import java.io.File;

import java.io.FileOutputStream;

import java.io.IOException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.sql.SQLException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;
import org.apache.struts.upload.FormFile;


public class MailAction extends DispatchAction 

{
        
        public ActionForward load(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response) throws Exception {
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
                return mapping.findForward(rtnPg);   
            }else{
                util.updAccessLog(request,response,"Mail", "load start");
            MailForm mailform = (MailForm)form;
            HashMap countryList = new HashMap();
            String getCountryValue = "select COUNTRY_IDN, COUNTRY_NM from mcountry ";
            ArrayList ary1 = new ArrayList();
            try{

                ArrayList outLst = db.execSqlLst(" Fav Lst ", getCountryValue, ary1);
                PreparedStatement pst = (PreparedStatement)outLst.get(0);
                ResultSet rs = (ResultSet)outLst.get(1);
            while(rs.next())
            {
            countryList.put(rs.getString(1),rs.getString(2));
            }
                rs.close();
                pst.close();
            session.setAttribute("countryList", countryList);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
                util.updAccessLog(request,response,"Mail", "load end");
            return mapping.findForward("view");
            }
        }
            
    
    public ActionForward loadM(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response) throws Exception {
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
            return mapping.findForward(rtnPg);   
        }else{
            util.updAccessLog(request,response,"Mail", "loadM start");
            MailForm mailform = (MailForm)form;
            request.setAttribute("isMassMail", util.nvl(request.getParameter("isMassMail")));
            HashMap mailDtl = util.getMailFMT("DFLT_TXT_MAIL");
            String dfltmailtxt=util.nvl((String)mailDtl.get("MAILBODY"));
            mailform.setValue("longdesc",dfltmailtxt);
            util.updAccessLog(request,response,"Mail", "loadM end");
       return mapping.findForward("view");
        }
    }
    public ActionForward mailALL(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response) throws Exception {
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
            return mapping.findForward(rtnPg);   
        }else{
        MailForm mailform = (MailForm)form;
        ArrayList maillst = info.getNmemassmaillist();
        String stt = util.nvl(request.getParameter("stt"));
        ArrayList ary = new ArrayList();
        String upGtSrch = "update gt_nme_srch set flg = ?  ";
        String flg="N";
        if(stt.equals("true"))
            flg="M";
        ary = new ArrayList();
        ary.add(flg);
        db.execUpd("update gt_srch", upGtSrch, ary);
   

            ArrayList outLst = db.execSqlLst("gtfech", "select byr.get_eml(nme_idn) eml from gt_nme_srch", new ArrayList()) ;
            PreparedStatement pst = (PreparedStatement)outLst.get(0);
            ResultSet rs = (ResultSet)outLst.get(1);
        while(rs.next()){
            String eml = util.nvl(rs.getString("eml"));
            if(!eml.equals("")){
            if(stt.equals("true")){
             maillst.add(eml);
            }else{
            maillst.remove(eml);
               
            }
            }
        }
            rs.close();
            pst.close();
        
       info.setNmemassmaillist(maillst);
        response.setContentType("text/xml");
        response.setHeader("Cache-Control", "no-cache");
        response.getWriter().write("<msg>yes</msg>");
        return null;
        }
    }
    
    public ActionForward loadpg(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response) throws Exception {
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
            return mapping.findForward(rtnPg);   
        }else{
        MailForm mailform = (MailForm)form;
        ArrayList maillst = info.getNmemassmaillist();
        String upGtSrch = "update gt_nme_srch set flg = ? where nme_idn = ? ";
        String flg="N";
        ArrayList ary = new ArrayList();
        String nmeIdn = util.nvl(request.getParameter("nmeId"));
        String stt = util.nvl(request.getParameter("stt"));
        String sqlEml = "select byr.get_eml(?) eml from dual";
        ary.add(nmeIdn);

            ArrayList outLst = db.execSqlLst("sql",sqlEml, ary);
            PreparedStatement pst = (PreparedStatement)outLst.get(0);
            ResultSet rs = (ResultSet)outLst.get(1);
        while(rs.next()){
            String eml = util.nvl(rs.getString("eml"));
            if(!eml.equals("")){
            if(stt.equals("true")){
             maillst.add(eml);
            }else{
            maillst.remove(eml);
               
            }
            }
        }
            rs.close();
            pst.close();
        if(stt.equals("true"))
            flg="M";
        ary = new ArrayList();
        ary.add(flg);
        ary.add(nmeIdn);
        db.execUpd("update gt_srch", upGtSrch, ary);
       info.setNmemassmaillist(maillst);
        response.setContentType("text/xml");
        response.setHeader("Cache-Control", "no-cache");
        response.getWriter().write("<msg>yes</msg>");
        return null;
        }
    }
     
  public ActionForward mailListView(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response) throws Exception {
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
              return mapping.findForward(rtnPg);   
          }else{
              util.updAccessLog(request,response,"Mail", "mailListView start");
      MailForm mailform = (MailForm)form;
      request.setAttribute("Checkflg","Y");
      request.setAttribute("mail", "Y");
      String dfltmailtxt=util.nvl((String)request.getAttribute("dfltmailtxt"));
      mailform.setValue("longdesc",dfltmailtxt);
              util.updAccessLog(request,response,"Mail", "mailListView end");
      return mapping.findForward("viewmail");
          }
      }
  
    public ActionForward mailList(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response) throws Exception {
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
            return mapping.findForward(rtnPg);   
        }else{
            util.updAccessLog(request,response,"Mail", "mailList start");
        MailForm mailform = (MailForm)form;
        ArrayList emailIdList = new ArrayList();
        String sql = "select txt from nme_dtl where vld_dte is null and mprp='EMAIL' and nme_idn=?";
        ArrayList ary = new ArrayList();
      ArrayList ByrEmailIds=null;
        ary.add(String.valueOf(info.getByrId()));

            ArrayList outLst = db.execSqlLst("getMail", sql, ary);
            PreparedStatement pst = (PreparedStatement)outLst.get(0);
            ResultSet rs = (ResultSet)outLst.get(1);
        while(rs.next()){
            String eml = util.nvl(rs.getString("txt"));
            if(eml.indexOf("@")!=-1)
            emailIdList.add(eml);
        }
            rs.close();
            pst.close();
        request.setAttribute("emailIdList", emailIdList);        
        String typ= request.getParameter("typ");
        if(typ==null ||typ.equals("srch")){
          typ="srch";
         ByrEmailIds=(ArrayList)request.getAttribute("ByrEmailIds");
         if(ByrEmailIds.size()>0){
           request.setAttribute("Checkflg","Y");
         }
        }
        String formAction = "/contact/massmail.do?method=send";
        if(typ.equals("srch"))
            formAction="/marketing/StockSearch.do?method=mailExcel&typ=memo";
        if(typ.equals("mailSC"))
            formAction="/marketing/StockSearch.do?method=mailExcel&typ=SRCH";
        request.setAttribute("formaction", formAction);
            util.updAccessLog(request,response,"Mail", "mailList end");
        return mapping.findForward("view");
        }
    }
  public ActionForward send(ActionMapping am,ActionForm form,HttpServletRequest req,HttpServletResponse res) throws Exception {
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
      util.updAccessLog(req,res,"Mail", "send start");
  MailForm mailform = (MailForm)form;
  ArrayList fileNameV = (session.getAttribute("attAttachFilNme") == null)?new ArrayList():(ArrayList)session.getAttribute("attAttachFilNme");
  ArrayList fileContentV = (session.getAttribute("attAttachTyp") == null)?new ArrayList():(ArrayList)session.getAttribute("attAttachTyp");
  ArrayList attAttachFile = (session.getAttribute("attAttachFile") == null)?new ArrayList():(ArrayList)session.getAttribute("attAttachFile");
  String load=util.nvl(req.getParameter("load"));
  if(load.equals(""))
  load="success";
  FormFile myFile =(FormFile)mailform.getValue("theFile");
  String fileName = myFile.getFileName();

  String path = getServlet().getServletContext().getRealPath("/") + fileName;
  File readFile = new File(path);
  if(!readFile.exists()){
  FileOutputStream fileOutStream = new FileOutputStream(readFile);
  fileOutStream.write(myFile.getFileData());
  fileOutStream.flush();
  fileOutStream.close();

  fileNameV.add(fileName);
  fileContentV.add(myFile.getContentType());
  attAttachFile.add(path);
  }
  session.setAttribute("attAttachFilNme",fileNameV);
  session.setAttribute("attAttachTyp",fileContentV);
  session.setAttribute("attAttachFile",attAttachFile);
  ArrayList emlList = new ArrayList();
  String eml=util.nvl(req.getParameter("eml"));
      if(!eml.equals("")){
  String[] emlLst = eml.split(",");
  if(emlLst!=null){
  for(int i=0 ; i <emlLst.length; i++)
  {
  emlList.add(emlLst[i]);
  }}}
  String loop=util.nvl(req.getParameter("loopemailid"));
  if(loop!=null && !loop.equals("")){
  int loopid=Integer.parseInt(loop);
  if(loopid>0){

  for (int i=0; i < loopid; i++){
  String isChecked = util.nvl(req.getParameter("id_" +i));
  if(!isChecked.equals("")){
  emlList.add(isChecked);
  }
  }
  }}
  ArrayList massemailList=new ArrayList();
  massemailList=info.getNmemassmaillist();
  if(massemailList!=null && massemailList.size()>0){
  for (int i=0; i < massemailList.size(); i++){
  emlList.add(massemailList.get(i));
  }
  }
  session.setAttribute("emlList", emlList);
  String isMassMail = util.nvl(req.getParameter("isMassMail"));
  if(isMassMail.equals("YES") && emlList.size()>0){
  String refID=null;
  String refIdSql = "select mail_ref_idn.nextval from dual";

      ArrayList outLst = db.execSqlLst("refIDn", refIdSql, new ArrayList());
      PreparedStatement pst = (PreparedStatement)outLst.get(0);
      ResultSet rs = (ResultSet)outLst.get(1);
  while(rs.next()){
  refID=rs.getString(1);
  }
      rs.close();
      pst.close();
  req.setAttribute("REFIDN", refID);
    new MassMail(req).start();
  }else{
  sendto(am, form, req, res);
  }
      util.updAccessLog(req,res,"Mail", "send end");
  return am.findForward(load);
  }
  }


  public ActionForward sendto(ActionMapping am,ActionForm form,HttpServletRequest req,HttpServletResponse res) throws Exception {
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
      util.updAccessLog(req,res,"Mail", "sendto start");
  MailForm mailform = (MailForm)form;
  HashMap dbmsInfo = info.getDmbsInfoLst();
  HashMap logDetails=new HashMap();
  GenMail mail = new GenMail();
  String salExlEml="";
  ArrayList  salExlEmailLst=new ArrayList();
  String cnt = (String)dbmsInfo.get("CNT");
  HashMap mailDtl = util.getMailFMT("SIGNATURE");
  String salExl = "select b.txt emailid \n" + 
  "from NME_EMP_V a , nme_dtl b \n" + 
  "where a.emp_idn=b.nme_idn and a.nme_idn=? and b.mprp like 'EMAIL%' and b.vld_dte is null and b.txt is not null\n";
  if(cnt.equals("kj")){
      salExl = "With EMP_V as ( \n" + 
      "select emp_idn from mnme where nme_idn=?\n" + 
      ")\n" + 
      "select b.txt emailid \n" + 
      "from EMP_V a , nme_dtl b  \n" + 
      "where a.emp_idn=b.nme_idn and b.mprp like 'EMAIL%' and b.vld_dte is null and nvl(b.txt,'NA') <> 'NA'";
  }
  ArrayList ary = new ArrayList();
  ary.add(String.valueOf(info.getByrId()));

      ArrayList outLst = db.execSqlLst("salExl", salExl, ary);
      PreparedStatement pst = (PreparedStatement)outLst.get(0);
      ResultSet rs = (ResultSet)outLst.get(1);
  while(rs.next()){
  salExlEmailLst.add(util.nvl(rs.getString("emailid")));
  }
      rs.close();
      pst.close();
      
  if(salExlEmailLst.size()>0)
  salExlEml=util.nvl((String)salExlEmailLst.get(0));
      
  String bodymsg=util.nvl((String)mailDtl.get("MAILBODY"));
  String subject= util.nvl(req.getParameter("subject"));
  String msgpost= util.nvl(req.getParameter("msg"));

  ArrayList fileNameV = (session.getAttribute("attAttachFilNme") == null)?new ArrayList():(ArrayList)session.getAttribute("attAttachFilNme");
  ArrayList fileContentV = (session.getAttribute("attAttachTyp") == null)?new ArrayList():(ArrayList)session.getAttribute("attAttachTyp");
  ArrayList attAttachFile = (session.getAttribute("attAttachFile") == null)?new ArrayList():(ArrayList)session.getAttribute("attAttachFile");

  info.setSmtpHost((String)dbmsInfo.get("SMTPHost"));
  info.setSmtpPort(Integer.parseInt((String)dbmsInfo.get("SMTPPort")));
  info.setSmtpUsr((String)dbmsInfo.get("SMTPUsr"));
  info.setSmtpPwd((String)dbmsInfo.get("SMTPPwd"));
  String senderID =(String)dbmsInfo.get("SENDERID");
  if(senderID.equals("NA"))
  senderID = salExlEml;
  if(senderID.equals(""))
  senderID=util.nvl((String)dbmsInfo.get("SENDERIDIFNA"));  
  String senderNm =(String)dbmsInfo.get("SENDERNM");
  String MAILTO = (String)dbmsInfo.get("MAILTO");
  mail.setInfo(info);
  mail.init();

  mail.setSender(senderID, senderNm);
  mail.setSubject(subject);
  if(!bodymsg.equals(""))
  msgpost=msgpost+bodymsg;
  mail.setMsgText(msgpost);
  if(fileNameV!=null && fileNameV.size() > 0 ){

  mail.setFileName(fileNameV);
  mail.setAttachmentType(fileContentV);
  mail.setAttachments(attAttachFile);
  }
  int emlListSz = 0;
  ArrayList list = (ArrayList)session.getAttribute("emlList");
  if(list!=null && list.size()>0){
  emlListSz = list.size();
  for (int i=0; i < list.size(); i++){
  mail.setBCC((String)list.get(i));

  }}
  mail.setBCC(MAILTO);
  String Cceml=util.nvl(req.getParameter("Cc"));
  if(!Cceml.equals("")){
  String[] emlLst = Cceml.split(",");
  if(emlLst!=null){
  for(int i=0 ; i <emlLst.length; i++)
  {
    mail.setCC(emlLst[i]);
  }}}
      String Bcceml=util.nvl(req.getParameter("Bcc"));
      if(!Bcceml.equals("")){
      String[] emlLst = Bcceml.split(",");
      if(emlLst!=null){
      for(int i=0 ; i <emlLst.length; i++)
      {
        mail.setBCC(emlLst[i]);
      }}}
  if(!salExlEml.equals("")){
  mail.setTO(salExlEml);
  mail.setReplyTo(salExlEml);
  
  for(int sl=1;sl<salExlEmailLst.size();sl++){
      mail.setTO(util.nvl((String)salExlEmailLst.get(sl)));
  }
  }
  String eml = util.nvl((String)mailDtl.get("CCEML"));
  String[] emlLst = eml.split(",");
  for(int i=0 ; i <emlLst.length; i++)
  {
  mail.setCC(emlLst[i]);
  }

  String bcceml = util.nvl((String)mailDtl.get("BCCEML"));
  String[] bccemlLst = bcceml.split(",");
  for(int i=0 ; i <bccemlLst.length; i++)
  {
  mail.setBCC(bccemlLst[i]);
  }
      logDetails.put("BYRID","0");
      logDetails.put("RELID","0");
      logDetails.put("TYP","CONT_MAIL");
      logDetails.put("IDN",String.valueOf(emlListSz));
      String mailLogIdn=util.mailLogDetails(req,logDetails,"I");  
      logDetails.put("MSGLOGIDN",mailLogIdn);
      logDetails.put("MAILDTL",mail.send(""));
      util.mailLogDetails(req,logDetails,"U");
  session.setAttribute("attAttachFilNme",new ArrayList());
  session.setAttribute("attAttachTyp",new ArrayList());
  session.setAttribute("attAttachFile",new ArrayList());
  session.setAttribute("emlList", new ArrayList());
      util.updAccessLog(req,res,"Mail", "sendto end");
  return am.findForward("success");
  }
  }
    public  void sendmail(HttpServletRequest request,
                                  HttpServletResponse response,String usrIdn, String typ, String buyer ) throws Exception {
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
        HashMap dbmsInfo = info.getDmbsInfoLst();
        HashMap logDetails=new HashMap();
        HashMap mailDtl = util.getMailFMT(typ.trim());           
      if(mailDtl!=null && mailDtl.size()>0){
            String salNm ="";
            String salEml = "";
            String usr ="";
            String pwd = "";
            String byrNm = "";
            String byrEml="";
            ResultSet rs1 = null;
            PreparedStatement pst =null;
            ArrayList ary = new ArrayList();
            ary.add(usrIdn);   
            if(!buyer.equals("")){
             String   delByrQ ="select  byr.get_nm(emp_idn) salNm,byr.get_eml(emp_idn) salEml,byr.get_eml(nme_idn) byrEml,byr.get_nm(nme_idn) byr   from mnme where nme_idn =? ";

                ArrayList outLst = db.execSqlLst("Delete Buyer", delByrQ,ary);
                pst = (PreparedStatement)outLst.get(0);
                rs1 = (ResultSet)outLst.get(1);
                if(rs1.next()){
                  salNm = util.nvl(rs1.getString("salNm"));
                  salEml = util.nvl(rs1.getString("salEml"));
                  byrNm = util.nvl(rs1.getString("byr"));
                  byrEml = util.nvl(rs1.getString("byrEml"));
              }
            }else{
            String salEx = "select a.usr,a.pwd,byr.get_nm(c.emp_idn) salNm , byr.get_eml(c.emp_idn) salEml,byr.get_eml(c.nme_idn) byrEml,byr.get_nm(c.nme_idn) byr from web_usrs a , nmerel b, mnme c " + 
            "where a.rel_idn = b.nmerel_idn and c.nme_idn = b.nme_idn and c.vld_dte is null  " + 
            " and a.usr_id=? ";
                ArrayList outLst = db.execSqlLst("salEx", salEx,ary);
                pst = (PreparedStatement)outLst.get(0);
                rs1 = (ResultSet)outLst.get(1);
            if(rs1.next()){
            usr = util.nvl(rs1.getString("usr"));
            pwd = util.nvl(rs1.getString("pwd"));
            salNm = util.nvl(rs1.getString("salNm"));
            salEml = util.nvl(rs1.getString("salEml"));
            byrNm = util.nvl(rs1.getString("byr"));
            byrEml = util.nvl(rs1.getString("byrEml"));
            }
            }
            rs1.close();
            pst.close();
        GenMail mail = new GenMail();
        ft.com.contact.MailAction mailFt = new ft.com.contact.MailAction();
        info.setSmtpHost((String)dbmsInfo.get("SMTPHost"));    
        info.setSmtpPort(Integer.parseInt((String)dbmsInfo.get("SMTPPort")));    
        info.setSmtpUsr((String)dbmsInfo.get("SMTPUsr"));    
        info.setSmtpPwd((String)dbmsInfo.get("SMTPPwd"));  
        String tenderdatefrom =(String)dbmsInfo.get("TENDER_DATE_FROM");
        String tenderdateto =(String)dbmsInfo.get("TENDER_DATE_TO");
        mail.setInfo(info);
        mail.init();
            StringBuffer msg=new StringBuffer();
             String hdr = "<html><head><title>WebSite Details</title>\n"+
             "<style type=\"text/css\">\n"+
             "body{\n" + 
             "   margin-left: 10px;\n" + 
             "   margin-top: 10px;\n" + 
             "   margin-right: 0px;\n" + 
             "   margin-bottom: 0px;\n" + 
             "   font-family: Arial, Helvetica, sans-serif;\n" + 
             "   font-size: 12px;\n" + 
             "   color: #333333;\n" + 
             "}\n" +
             "</style>\n"+         
             "</head>";
             msg.append(hdr);
             msg.append("<body>");
        String bodymsg=util.nvl((String)mailDtl.get("MAILBODY"));
    
        if(bodymsg.indexOf("~SALEXC~") > -1 && !salNm.equals(""))
        bodymsg = bodymsg.replaceAll("~SALEXC~", "For any further assistance; please feel free to contact your Sales Executive : "+salNm);
            if(bodymsg.indexOf("~BYRNME~") > -1)
                  bodymsg = bodymsg.replaceAll("~BYRNME~", byrNm);
            if(bodymsg.indexOf("~BYREML~") > -1)
                  bodymsg = bodymsg.replaceFirst("~BYREML~", byrEml); 
            if(bodymsg.indexOf("~SALEXNME~") > -1)
                  bodymsg = bodymsg.replaceAll("~SALEXNME~", salNm);
            if(bodymsg.indexOf("~SALEXEML~") > -1)
                  bodymsg = bodymsg.replaceFirst("~SALEXEML~", salEml); 
            if(bodymsg.indexOf("~USR~") > -1)
            bodymsg = bodymsg.replaceAll("~USR~", usr);
            if(bodymsg.indexOf("~PASS~") > -1)
            bodymsg = bodymsg.replaceAll("~PASS~", pwd);
            if(bodymsg.indexOf("~WHO~") > -1)
            bodymsg = bodymsg.replaceFirst("~WHO~", info.getUsr());
            if(bodymsg.indexOf("~TENDER_DATE_FROM~") > -1)
            bodymsg = bodymsg.replaceFirst("~TENDER_DATE_FROM~", tenderdatefrom);
            if(bodymsg.indexOf("~TENDER_DATE_TO~") > -1)
            bodymsg = bodymsg.replaceFirst("~TENDER_DATE_TO~", tenderdateto);
        msg.append(bodymsg);
        msg.append("</body>");
        msg.append("</html>");
            String mailSub=util.nvl((String)mailDtl.get("SUBJECT"));
            if(mailSub.indexOf("~USR~") > -1)
            mailSub = mailSub.replaceFirst("~USR~", usr);
            if(mailSub.indexOf("~BYRNME~") > -1)
            mailSub = mailSub.replaceFirst("~BYRNME~", byrNm);
            if(mailSub.indexOf("~SALEXNME~") > -1)
            mailSub = mailSub.replaceFirst("~SALEXNME~", salNm);
            if(mailSub.indexOf("~WHO~") > -1)
            mailSub = mailSub.replaceFirst("~WHO~", info.getUsr());
            if(mailSub.indexOf("~DTE~") > -1)
            mailSub = mailSub.replaceFirst("~DTE~", util.getToDte());
            if(mailSub.indexOf("~TENDER_DATE_FROM~") > -1)
            mailSub = mailSub.replaceAll("~TENDER_DATE_FROM~", tenderdatefrom);
            if(mailSub.indexOf("~TENDER_DATE_TO~") > -1)
            mailSub = mailSub.replaceAll("~TENDER_DATE_TO~", tenderdateto);
            
            mail.setSubject(mailSub);
            String senderID =(String)dbmsInfo.get("SENDERID");
            String senderNm =(String)dbmsInfo.get("SENDERNM");
            if(senderID.equals("NA"))
            senderID = salEml;          
            mail.setSender(senderID, senderNm); 
            String toEml = util.nvl((String)mailDtl.get("TOEML"));
            if(toEml.indexOf("BYR")!=-1 && !byrEml.equals("")){
            mail.setTO(byrEml);
            }
            if(toEml.indexOf("SALEXC")!=-1 && !salEml.equals("")){
            mail.setTO(salEml);
            }
            String[] emlToLst = toEml.split(",");
            if(emlToLst!=null){
            for(int i=0 ; i <emlToLst.length; i++)
            {
            String to=util.nvl(emlToLst[i]);
            if(!to.equals("BYR") && !to.equals("SALEXC")){
            mail.setTO(to);
            }
            }
            }
            mail.setReplyTo(salEml);
            String cceml = util.nvl((String)mailDtl.get("CCEML"));
            String[] emlLst = cceml.split(",");
            if(emlLst!=null){
            for(int i=0 ; i <emlLst.length; i++)
            {
            mail.setCC(emlLst[i]);
            }
            }

             String bcceml = util.nvl((String)mailDtl.get("BCCEML"));
             String[] bccemlLst = bcceml.split(",");
             if(bccemlLst!=null){
              for(int i=0 ; i <bccemlLst.length; i++)
              {
              mail.setBCC(bccemlLst[i]);
              }
            }
            String mailMag = msg.toString();
            mail.setMsgText(mailMag);
            logDetails.put("BYRID","");
            logDetails.put("RELID","");
            logDetails.put("TYP",typ);
            logDetails.put("IDN",usrIdn);
            String mailLogIdn=util.mailLogDetails(request,logDetails,"I");  
            logDetails.put("MSGLOGIDN",mailLogIdn);
            logDetails.put("MAILDTL",mail.send(""));
            util.mailLogDetails(request,logDetails,"U");
        }
      
        if(!buyer.equals("")){
        String delQ   = " update mnme set vld_dte = sysdate where nme_idn = ? ";
        ArrayList params = new ArrayList();
        params.add(usrIdn);
        int cnt = db.execUpd(" Del " + usrIdn, delQ, params);
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
                util.updAccessLog(req,res,"Mail", "init");
            }
            }
            return rtnPg;
         }
}

