<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.ResultSet, java.util.ArrayList,java.util.Collections,java.util.ArrayList,java.util.HashMap,java.text.SimpleDateFormat, java.util.Calendar"%>
<jsp:useBean id="util" class="ft.com.JspUtil" scope="page" />
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>Diaflex : Unauthorized Access</title>
        <%
    String requrl=(String)request.getAttribute("requrl"); 
    String jsversion=(String)request.getAttribute("jsversion");
    String usr=(String)request.getAttribute("usr");
    String logoutlogIdn=(String)request.getAttribute("logoutlogIdn");
    %>
  <script type="text/javascript" src="<%=requrl%>/scripts/bse.js?v=<%=jsversion%> " > </script>
<link type="text/css" rel="stylesheet" href="<%=requrl%>/css/bse.css?v=<%=jsversion%>"/>
<link type="text/css" rel="stylesheet" href="<%=requrl%>/css/general.css?v=<%=jsversion%>"/>
<script type="text/javascript" src="<%=requrl%>/scripts/ajax.js?v=<%=jsversion%> " > </script>
  <script type="text/javascript" src="<%=requrl%>/PopScripts/popup.js?v=<%=jsversion%> " > </script>
    <script src="<%=requrl%>/PopScripts/jquery-1.2.6.min.js" type="text/javascript"></script>
   <script language="javaScript" type="text/javascript" src="<%=requrl%>/calendar/calendar.js"></script>
   <link href="<%=requrl%>/calendar/calendar.css" rel="stylesheet" type="text/css" />
   <link type="text/css" rel="stylesheet" href="<%=requrl%>/css/style.css?v=<%=jsversion%>"/>
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
   <body onload="MM_preloadImages('images/faunalogo.png')" topmargin="0" leftmargin="0" rightmargin="0" bottommargin="0">
<table width="100%"  border="0" align="center" cellpadding="0" cellspacing="0" class="mainbg" height="600">
   <tr>
    <td height="103" valign="top">    
    <table width="100%" height="103" border="0" cellspacing="0" cellpadding="0">
    <tr>
        <td width="188"><img src="<%=requrl%>/images/diaflex_logo.png" width="108" height="59" hspace="40" /></td>
    <td width="924" valign="middle" height="103" align="left">
    </td>
    <td align="left" height="20" valign="middle" width="188"><div align="right"><a href="#"><img src="<%=requrl%>/images/flame.png" name="faunalogo"  hspace="40" border="0" id="faunalogo" onmouseover="MM_swapImage('faunalogo','','<%=requrl%>/images/faunalogo.png',1)" onmouseout="MM_swapImgRestore()"></a></div></td>
    </tr>
    
    </table>
    </td>
  </tr>
    <tr>
    <td height="5" valign="top" class="greyline"><img src="../images/grey_linebig.png" width="685" height="5" align="right" /></td>
  </tr>
  <tr><td valign="top" class="hedPg">
  <label class="pgTtl">Unauthorized Access Please Contact To Admin Team</label>
  <input type="hidden" name="refreshId" id="refreshId" value="<%=logoutlogIdn%>">
  </td></tr>
  <tr>
  <td height="80%" valign="bottom">&nbsp</td>
  </tr>
    <tr><td>
<jsp:include page="/footer.jsp" />
</td></tr>
  </table> 
  <%
    
  %>
       <script type="text/javascript">
       <!--
       setCookie("refresh","Y",365);
       -->
      </script>
  </body>
</html>