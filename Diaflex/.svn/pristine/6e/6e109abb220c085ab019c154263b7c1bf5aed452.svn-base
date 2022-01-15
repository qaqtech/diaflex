<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" 
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page isErrorPage = "true"%>
<%@ page import="java.util.ArrayList,java.util.HashMap,javax.servlet.http.Cookie,java.io.*,ft.com.GenMail" %>
<jsp:useBean id="util" class="ft.com.JspUtil" scope="page" />
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>error_page</title>
        <script type="text/javascript">
function changeHashOnLoad() {
window.location.href += '#';
setTimeout('changeHashAgain()', '50');
}

function changeHashAgain() {
window.location.href += '1';
}

var storedHash = window.location.hash;
window.setInterval(function () {
if (window.location.hash != storedHash) {
window.location.hash = storedHash;
}
}, 50);
window.onload=changeHashOnLoad;
</script>
  </head>
  <body>
  <%
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
    String connExists=(String)request.getAttribute("connExists");
    if(connExists!=null){
    System.out.println("Diaflex : connExists!=null");
%>
    <table cellpadding="5" cellspacing="1" border="1">
    <tr><td>Message : Sorry!! Your Connection has ended. Kindly relogin</td></tr>
    <tr><td> <A href="<%=url%>">For relogin </a> </td> </tr>
  </table>
  <%}else{
  System.out.println("Diaflex : connExists");
  %>
   <table cellpadding="5" cellspacing="1" border="1">
    <tr><td>Sorry!! The session has expired,You need to re_login</td></tr>
    <tr><td> <A href="<%=url%>">For relogin </a> </td> </tr>
  </table>
  <%}%>
  </body>
</html>

