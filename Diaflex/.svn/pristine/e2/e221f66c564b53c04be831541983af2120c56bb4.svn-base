<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page errorPage="../error_page.jsp" %>
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import="java.util.ArrayList,java.util.HashMap,java.sql.ResultSet,ft.com.*, java.util.Date, java.util.ArrayList, ft.com.dao.*"%>
<jsp:useBean id="info" class="ft.com.InfoMgr" scope="session" />
<jsp:useBean id="util" class="ft.com.JspUtil" scope="page" />

<%@taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>Issue Result </title>
  <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/bse.css?v=<%=info.getJsVersion()%>"/>
    <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/style.css?v=<%=info.getJsVersion()%>"/>
   <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/bse.js?v=<%=info.getJsVersion()%> " > </script>

  </head>
        <%String logId=String.valueOf(info.getLogId());
        String onfocus="cook('"+logId+"')";
        %>
 <body onfocus="<%=onfocus%>" onload="MM_preloadImages('images/faunalogo.png')" topmargin="0" leftmargin="0" rightmargin="0" bottommargin="0">
<table width="100%"  border="0" align="center" cellpadding="0" cellspacing="0" class="mainbg">
  <tr>
    <td height="103" valign="top">
   <jsp:include page="/header.jsp" />
    
    </td>
  </tr>
 
  <tr>
    <td height="5" valign="top" class="greyline"><img src="<%=info.getReqUrl()%>/images/grey_linebig.png" width="685" height="5" align="right" /></td>
  </tr>
<tr>
  <td valign="top" class="hedPg">
  <table cellpadding="1" cellspacing="5"><tr><td valign="middle">
  <img src="../images/line.jpg" border="0" /></td><td valign="middle"> <span class="pgHed">Issue Result</span> </td>
</tr></table>
  </td>
  </tr>
  <tr>
  <td>
  <table class="grid1">

    <tr><th colspan="2">Issue Result</th></tr>
    <tr><td>Process</td><td>
    <html:select property="value(mprcId)" name="issueForm" >
    <html:optionsCollection property="mprcLst" name="issueForm" label="prc" value="mprcId" ></html:optionsCollection>
    </html:select>
    </td></tr>
    <tr><td>Employee </td><td>
    <html:select property="value(emp)" name="issueForm" >
    <html:optionsCollection property="nmeLst" name="issueForm" label="emp_nme" value="emp_idn" ></html:optionsCollection>
    </html:select>
    </td></tr>
    <tr><td></td><td><html:textarea property="value(issueGood)"  /> </td></tr>
    <tr><td colspan="2">
    <html:submit value="Issue Goods"  />
    </td></tr>
  </table>
  </td>
  </tr>
  
</table>

</body>
</html>