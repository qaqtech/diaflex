package ft.com;

import java.io.IOException;
import java.io.PrintWriter;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.HashMap;
import java.util.ArrayList;

import javax.servlet.*;
import javax.servlet.http.*;

public class WebUserMail extends HttpServlet {
    private static final String CONTENT_TYPE ="text/html; charset=windows-1252";
    DBMgr db = null;
    DBUtil util = null;
    InfoMgr info = null;
    LogMgr log = null;
    GenMail mail = null;
    HttpSession session;

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        db = new DBMgr();
        util = new DBUtil();
        info = new InfoMgr();
        log = new LogMgr();
        mail = new GenMail();
    }

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException,
                                                           IOException {
        session = request.getSession(false);
        if (session.getAttribute("db") == null) {
            db.setApplId(getServletContext().getInitParameter("ApplId"));
            db.setDbHost(getServletContext().getInitParameter("HostName"));
            db.setDbPort(getServletContext().getInitParameter("Port"));
            db.setDbSID(getServletContext().getInitParameter("SID"));
            db.setDbUsr(getServletContext().getInitParameter("UserName"));
            db.setDbPwd(getServletContext().getInitParameter("Password"));
            db.init();
            info.setId(session.getId());
            util.setInfo(info);
            util.setDb(db);
           
        } else {
            session = request.getSession(false);
            info = (InfoMgr)session.getAttribute("info");
            util = new DBUtil();
            db = new DBMgr();
            db.setCon(info.getCon());
            util.setDb(db);
            util.setInfo(info);
            db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
            util.setLogApplNm(info.getLoApplNm());
            mail = (GenMail)session.getAttribute("mail");
        }
        String mailSub = "";
        String mailBody = "";
        String logo = "";
        String eventIdn="";
        String mailContent = "select a.event_idn , b.subj , b.mail_body , b.logo  from mail_event a , mail_fmt b " + 
        "where a.fmt_idn = b.fmt_idn and a.vld_dte is null and b.vld_dte is null " + 
        "and a.event_cd='EXITUSRMAIL'";

     ArrayList outLst = db.execSqlLst("mail Content", mailContent, new ArrayList());
     PreparedStatement pst = (PreparedStatement)outLst.get(0);
     ResultSet rs = (ResultSet)outLst.get(1);
        try {
            while (rs.next()) {
               mailSub = util.nvl(rs.getString("subj"));
               mailBody = util.nvl(rs.getString("mail_body"));
               logo = util.nvl(rs.getString("logo"));
                eventIdn = util.nvl(rs.getString("event_idn"));
            }
            rs.close();
            pst.close();
        } catch (SQLException sqle) {
            // TODO: Add catch code
            sqle.printStackTrace();
        }
        response.setContentType(CONTENT_TYPE);
        String nmeIdn = util.nvl(request.getParameter("nmeIdn"));
        ArrayList ary = new ArrayList();
        String cusMail="select a.nmerel_idn , a.nme_idn , a.byr , a.eml , a.contact_person , sl_eml "+
                        " from BYR_REL_EML_V a where not exists (select 1 from web_usrs b where a.nmerel_idn = b.rel_idn) "+
                        " and a.eml <> 'NA' and sl_eml <> 'NA' ";
        if(!nmeIdn.equals("")){
            cusMail = cusMail+ " and a.nme_idn= ? ";
            ary.add(nmeIdn);
        }
        
    //        String cusMail = " select * from BYR_REL_EML_V where nme_idn = 101";
     boolean isSend = false;
     outLst = db.execSqlLst("cusMail", cusMail,ary);
     pst = (PreparedStatement)outLst.get(0);
     rs = (ResultSet)outLst.get(1);
        try {
           while(rs.next()) {
               String nme_idn = util.nvl(rs.getString("nme_idn"));
               String regIdn=util.nvl(rs.getString("nmerel_idn"));
               String emlId=util.nvl(rs.getString("eml"));
               String nmeNme=util.nvl(rs.getString("byr"));
               String contact = util.nvl(rs.getString("contact_person"));
               String saleEmlId = util.nvl(rs.getString("sl_eml"));
                StringBuffer sb = new StringBuffer();
                 sb = util.appendTo(sb, "<html><head>" +
                       "<meta http-equiv=\"Content-Type\" content=\"text/html; charset=iso-8859-1\" />" +
                                    "<style>\n"+
                                    "body {\n" + 
                                    "   margin-left: 0px;\n" + 
                                    "   margin-top: 0px;\n" + 
                                    "   margin-right: 0px;\n" + 
                                    "   margin-bottom: 0px;\n" + 
                                    "   background-color: #FFFFFF;\n" + 
                                    "   font-family: Geneva, Arial, Helvetica, sans-serif;\n" + 
                                    "   font-size: 9pt;\n" + 
                                    "   color: #000000;        \n" + 
                                    "  }   \n" + 
                                        "</style></head><body>");
               if(!logo.equals("")){
                   logo = "<p><img src="+logo+" border=\"0\"> </p>" ;
               }
                String message = logo+""+mailBody;             
                 message = message.replaceFirst("CSM", contact);   
                 sb = util.appendTo(sb,message);
                
           
                sb = util.appendTo(sb,"</body></html>");
                GenMail mail = new GenMail();
                HashMap dbmsInfo = info.getDmbsInfoLst();
                info.setSmtpHost((String)dbmsInfo.get("SMTPHost"));
                info.setSmtpPort(Integer.parseInt((String)dbmsInfo.get("SMTPPort")));
                info.setSmtpUsr((String)dbmsInfo.get("SMTPUsr"));
                info.setSmtpPwd((String)dbmsInfo.get("SMTPPwd"));
                mail.setInfo(info);    
                mail.init();
                String senderID =(String)dbmsInfo.get("SENDERID");
                if(senderID.equals("NA"))
                    senderID = saleEmlId;
                String senderNm =(String)dbmsInfo.get("SENDERNM");
                mail.setSender(senderID, senderNm);
                mail.setSubject(mailSub);
                mail.setTO(emlId);
                if(!senderID.equals("NA"))
                mail.setReplyTo(saleEmlId);
                mail.setTO(saleEmlId);
               // mail.setBCC("surekha.kandhare@faunatechnologies.com");
                mail.setMsgText(sb.toString());
                mail.send("");
               
               String insertLog = "insert into mail_log(mail_log_idn , event_idn , byr_idn , rel_idn , to_eml , cc_eml)" +
                                  "select mail_log_seq.nextval ,? ,? ,?,?,? from dual ";
               ary = new ArrayList();
               ary.add(eventIdn);
               ary.add(nme_idn);
               ary.add(regIdn);
               ary.add(emlId);
               ary.add(saleEmlId);
               int ct = db.execUpd("insert Log", insertLog, ary);
               isSend = true;
            }
            rs.close();
            pst.close();
        } catch (SQLException sqle) {
            // TODO: Add catch code
            sqle.printStackTrace();
        }
        
        PrintWriter out = response.getWriter();
        out.println("<html>");
        out.println("<head><title>REG Mail</title></head>");
        out.println("<body>");
        if(isSend)
        out.println("<p> Mail Send.... </p>");
        else
        out.println("<p> Mail Not send some basic information missing  </p>");
        out.println("</body></html");
        out.close();
       
 }     
    
}
