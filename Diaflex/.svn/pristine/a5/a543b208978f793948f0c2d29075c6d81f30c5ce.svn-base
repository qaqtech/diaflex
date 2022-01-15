<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.ResultSet, java.util.ArrayList,java.util.Collections,java.util.ArrayList,java.util.HashMap,java.text.SimpleDateFormat, java.util.Calendar"%>
<jsp:useBean id="util" class="ft.com.JspUtil" scope="page" />
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>Diaflex : logout</title>
    <%
    String requrl=(String)request.getAttribute("requrl"); 
    String jsversion=(String)request.getAttribute("jsversion");
    System.out.println("Diaflex : logout :"+requrl);
    %>
    <script type="text/javascript" src="<%=requrl%>/scripts/bse.js?v=<%=jsversion%> " > </script>
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
        String logoutlogIdn=(String)request.getAttribute("logoutlogIdn"); 
 %>
 <table align="center"><tr><td>
 <label class="pgTtl">Thank you for using Diaflex</label>
 </td></tr>
 <tr><td><A href="<%=url%>">FOR RELOGIN</A> 
 <input type="hidden" name="refreshId" id="refreshId" value="<%=logoutlogIdn%>">
 </td></tr>
 </table>
       <script type="text/javascript">
       <!--
       setCookie("refresh","Y",365);
       -->
      </script>
  </body>
</html>