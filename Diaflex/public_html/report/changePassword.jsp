<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page errorPage="../error_page.jsp" %>
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.ResultSet, java.util.ArrayList,java.util.Collections,java.util.ArrayList,java.util.HashMap,java.text.SimpleDateFormat, java.util.Calendar"%>
<jsp:useBean id="info" class="ft.com.InfoMgr" scope="session" />
<jsp:useBean id="util" class="ft.com.JspUtil" scope="page" />
<%@taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>
<html> 
   <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/bse.js?v=<%=info.getJsVersion()%> " > </script>
<link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/bse.css?v=<%=info.getJsVersion()%>"/>
<link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/general.css?v=<%=info.getJsVersion()%>"/>
<script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/ajax.js?v=<%=info.getJsVersion()%> " > </script>
  <script type="text/javascript" src="<%=info.getReqUrl()%>/PopScripts/popup.js?v=<%=info.getJsVersion()%> " > </script>
    <script src="<%=info.getReqUrl()%>/PopScripts/jquery-1.2.6.min.js" type="text/javascript"></script>
   <script language="javaScript" type="text/javascript" src="<%=info.getReqUrl()%>/calendar/calendar.js"></script>
   <link href="<%=info.getReqUrl()%>/calendar/calendar.css" rel="stylesheet" type="text/css" />
  <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/style.css?v=<%=info.getJsVersion()%>"/>
  <title>Change Password</title>
  </head>
        <%String logId=String.valueOf(info.getLogId());
        String onfocus="cook('"+logId+"')";
        %>
 <body onfocus="<%=onfocus%>" onload="MM_preloadImages('images/faunalogo.png')" topmargin="0" leftmargin="0" rightmargin="0" bottommargin="0">
  <table cellpadding="" cellspacing="" class="mainbg">
   <tr>
    <td height="103" valign="top">
   <jsp:include page="/header.jsp" />
    
    </td>
  </tr>
  <tr>
    <td height="5" valign="top" class="greyline"><img src="../images/grey_linebig.png" width="685" height="5" align="right" /></td>
  </tr>
   <tr>
  <td valign="top" class="hedPg">
  <table cellpadding="1" cellspacing="5"><tr><td valign="middle">
  <img src="../images/line.jpg" border="0" /></td><td valign="middle"> <span class="pgHed">
Change Password
  </span> </td>
</tr></table>
  </td>
  </tr>
    <%
   String msg = (String)request.getAttribute("msg");
   if(msg!=null){
   %>
    <tr><td valign="top" class="tdLayout"><span class="redLabel"><%=msg%></span></td></tr>
   <%}%>
  <tr><td valign="top" class="hedPg">
  <html:form action="/report/common.do?method=save" method="post" onsubmit="return validateChangePass()">
  <table  class="grid1">
  <tr><th  colspan="2">Change Password</th>
  <tr><td>Current Password</td><td><html:password property="value(current)" styleId="current" readonly="true" /></td></tr>
  <tr><td>New Password</td><td><html:password property="value(new)" styleId="new" /></td></tr>
  <tr><td>Confirm Password</td><td><html:password property="value(confirm)" styleId="confirm" /></td></tr>
  <tr><td colspan="2" align="center"><html:submit property="value(submit)" value="Submit" styleClass="submit" /></td> </tr>
  </table>
  </html:form>
  </td></tr>
  <tr><td>
<jsp:include page="/footer.jsp" />
</td></tr>
  </table>  
  </body>
</html>