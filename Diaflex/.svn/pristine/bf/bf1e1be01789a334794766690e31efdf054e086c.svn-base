<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=windows-1252"%>
<jsp:useBean id="db" class="ft.com.DBMgr" scope="session" />
<jsp:useBean id="log" class="ft.com.LogMgr" scope="session" />
<jsp:useBean id="util" class="ft.com.DBUtil" scope="session" />
<jsp:useBean id="info" class="ft.com.InfoMgr" scope="session" />
<%@taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>
<%@taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>SecurityPage</title>
   <link type="text/css" rel="stylesheet" href="css/style.css"/>
   <link type="text/css" rel="stylesheet" href="css/bse.css"/>
    <script type="text/javascript" src="scripts/bse.js" ></script>
  </head>
  <body>
  <table width="100%"  border="0" align="center" cellpadding="0" cellspacing="0" class="mainbg">
  <tr>
    <td height="103" valign="top" align="left" >
    <img src="images/diaflex_logo.png" width="108" height="59" hspace="40" vspace="20" />
    </td></tr>
     <tr><td valign="top" class="hedPg">
     <%
     util.updAccessLog(request,response,"Security Page", "Security Page");
     String msg = util.nvl((String)request.getAttribute("msg"));
     if(!msg.equals("")){
     %>
   <table align="center"><tr><td> <span class="redLabel" > <%=msg%></span></td></tr></table>
     <%}%>
     <form action="login.do?method=security" method="POST">
      <table cellspacing="5" align="center" cellpadding="5">
      <tr><td>Please Enter Security Code</td><td><input type="password" name="security"  /> </td></tr>
      <tr><td align="right"><input type="submit" class="submit" value="Verify" /> </td><td align="left"><input type="button" class="submit" onclick="goTo('logout.jsp')"  value="Logout"/> </td> </tr>
      </table>
      </form>
     </td></tr>
     <tr>
     <td><jsp:include page="/footer.jsp" /> </td>
     </tr>
    </table>
  
  
  </body>
</html>