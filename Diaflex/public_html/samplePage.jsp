<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page contentType="text/html;charset=windows-1252"%>
<jsp:useBean id="db" class="ft.com.DBMgr" scope="session" />
<jsp:useBean id="log" class="ft.com.LogMgr" scope="session" />
<jsp:useBean id="util" class="ft.com.DBUtil" scope="session" />
<jsp:useBean id="info" class="ft.com.InfoMgr" scope="session" />
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>samplePage</title>
     <link type="text/css" rel="stylesheet" href="css/style.css"/>
     <script type="text/javascript" src="scripts/bse.js" ></script>
  </head>

  <%
 int port=request.getServerPort();
             String servPath = "http://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath();
            if(port==443)
             servPath = "https://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath();

             info.setReqUrl(servPath); 
  %>
  
  
<body onload="MM_preloadImages('images/faunalogo.png')" topmargin="0" leftmargin="0" rightmargin="0" bottommargin="0">
<table width="100%"  border="0" align="center" cellpadding="0" cellspacing="0" class="mainbg">
  <tr>
    <td height="103"  valign="top">
   <jsp:include page="/header.jsp" />
    
    </td>
  </tr>
 
  <tr>
    <td height="5" valign="top" class="greyline"><img src="images/grey_linebig.png" width="685" height="5" align="right" /></td>
  </tr>
  <tr>
    <td valign="top">
    
    </td></tr>
  <tr>
    <td>&nbsp;</td>
  </tr>
</table>
</body>

  
  
</html>