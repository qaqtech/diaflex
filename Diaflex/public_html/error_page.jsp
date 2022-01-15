<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" 
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page isErrorPage = "true"%>
<%@ page import="ft.com.DBUtil,ft.com.DBMgr,java.util.ArrayList,java.util.HashMap,java.sql.Connection,javax.servlet.http.Cookie,java.io.*,ft.com.GenMail" %>
<jsp:useBean id="util" class="ft.com.JspUtil" scope="page" />
<jsp:useBean id="info" class="ft.com.InfoMgr" scope="session"/>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>error_page</title>
  </head>
  <body>
  <%
  System.out.println("Diaflex : error_page");
  String cookieName = "APPURL";
  Cookie cookies [] = request.getCookies();
  Cookie myCookie = null;
  if (cookies != null){
  for (int i = 0; i < cookies.length; i++) {
  if (cookies [i].getName().equals(cookieName)){
   myCookie = cookies[i];
   break;
  }}}
  String url = "#";
  if(myCookie != null)
    url = myCookie.getValue();
    
  String msg = "";
  String sessionswap="";
  boolean retry=false;
            Connection conn=info.getCon();
            if(conn!=null){
            DBUtil dbutil = new DBUtil();
            DBMgr db = new DBMgr();
            db.setCon(info.getCon());
            dbutil.setDb(db);
            dbutil.setInfo(info);
            db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));db.setDbUsr(info.getDbUsr());
            dbutil.setLogApplNm(info.getLoApplNm());
  try {
    msg = "Application Error";
        HashMap dbmsInfo = info.getDmbsInfoLst();
        GenMail mail = new GenMail();
        info.setSmtpHost((String)dbmsInfo.get("SMTPHost"));    
        info.setSmtpPort(Integer.parseInt((String)dbmsInfo.get("SMTPPort")));    
        info.setSmtpUsr((String)dbmsInfo.get("SMTPUsr"));    
        info.setSmtpPwd((String)dbmsInfo.get("SMTPPwd"));  
        mail.setInfo(info);
        mail.init();
        String trace = "";   
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        StringBuffer sb = new StringBuffer() ;
        sb = util.appendTo(sb, "<html><head><meta http-equiv=\"Content-Type\" content=\"text/html; charset=iso-8859-1\" /> </head><body>");
        exception.printStackTrace(pw);
        trace = sw.toString();
        String sessionid=request.getSession(false).getId();
        sb = util.appendTo(sb, "Diaflex Client : "+ util.nvl((String)dbmsInfo.get("SENDERNM"))+"<br/>");
        sb = util.appendTo(sb, "Diaflex User : "+ info.getUsr()+"<br/>");
        sb = util.appendTo(sb, "Log Id : "+ info.getLogId()+"<br/>");
        sb = util.appendTo(sb, "Set Session Id : "+ info.getSessionId()+"<br/>");
        sb = util.appendTo(sb, "Session Id From Request : "+ sessionid+"<br/>");
        sb = util.appendTo(sb, "Diaflex Url : "+ util.nvl((String)info.getReqUrl())+"<br/>");
        sb = util.appendTo(sb, "Error Details : "+ exception.getClass() + "<br/>");
        sb = util.appendTo(sb, "StackTrace Details : "+ trace +"<br/>");
        sb = util.appendTo(sb, "</body></html>");
        String senderID =(String)dbmsInfo.get("SENDERID");
        String senderNm =(String)dbmsInfo.get("SENDERNM");
        if(senderID.equals("NA"))
        senderID=util.nvl((String)dbmsInfo.get("SENDERIDIFNA"));  
        mail.setSender(senderID, senderNm); 
        mail.setSubject("Diaflex Error Mail");
        mail.setTO("puravrc@gmail.com");
        mail.setMsgText(sb.toString());
//        mail.send(sb.toString()); 
        } catch (Exception e) {
        sessionswap="Y";
        db.close();
        session.invalidate();
        }
        retry=true;
        String logid=util.nvl(String.valueOf(info.getLogId()));
    if(sessionswap.equals("")){
      dbutil.updAccessLog(request,response,"Error page", "Error page");
    }
if(!retry){
db.close();
session.invalidate();
}
  } else {
    msg = "Sorry!! The session has expired,You need to re_login";
  }
  String connExists = util.nvl(request.getParameter("connExists"));
  if(!connExists.equals("N")){
  %>
  <table cellpadding="5" cellspacing="1" border="1">
    <%if(!retry){%>
    <tr><td>Error Details</td></tr>
    <tr><td>Page : 
    <%try {%>
     <%=exception.getClass()%>
    <%} catch (Exception e) {
            // TODO: Add catch code
     }%>
    </td></tr>
    <tr><td>Message : <%=msg%></td></tr>
    <tr><td> <A href="<%=url%>">For relogin </a> </td> </tr>
    <%}else if(sessionswap.equals("")){%>
    <tr><td> <b><A href="javascript:history.back()">An application error has occurred, kindly Click here to retry. If reoccurs kindly inform team Fauna</a> </b></td> </tr>
    <%}else if(sessionswap.equals("Y")){%>
    <tr><td>Message : Sorry!! The session has expired,You need to re_login  </td></tr>
    <tr><td> <A href="<%=url%>">For relogin </a> </td> </tr>
     <% 
      }}%>
  </table>
  </body>
</html>

