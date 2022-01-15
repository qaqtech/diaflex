<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page errorPage="../error_page.jsp" %>
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.ResultSet, java.util.ArrayList,java.util.Collections,java.util.ArrayList,java.util.HashMap,java.text.SimpleDateFormat, java.util.Calendar"%>
	<jsp:useBean id="info" class="ft.com.InfoMgr" scope="session" />
	<jsp:useBean id="util" class="ft.com.JspUtil" scope="page" />
<%@taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>
<%@taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
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

  <title>Weekly List Report</title>
 
  </head>
  
  <%

  %>
        <%
    ArrayList deptList=new ArrayList();
    HashMap prp = info.getPrp();
    deptList = (ArrayList)prp.get("DEPTV");
        String logId=String.valueOf(info.getLogId());
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
    <td height="5" valign="top" class="greyline"><img src="../images/grey_linebig.png" width="685" height="5" align="right" /></td>
  </tr>
   <tr>
  <td valign="top" class="hedPg">
  <table cellpadding="1" cellspacing="5"><tr><td valign="middle">
  <img src="../images/line.jpg" border="0" /></td><td valign="middle"> <span class="pgHed">Weekly List Report</span> </td>
</tr></table>
  </td>
  </tr>
<tr><td valign="top" class="hedPg">
<html:form action="/report/orclRptAction.do?method=weeklyList"  method="POST">
     <table class="grid1">
    <tr><th></th><th>From</th><th>To</th></tr>
   <tr>
    <td nowrap="nowrap">1 week</td><td nowrap="nowrap">
    <html:text property="value(dte1fr)" styleClass="txtStyle"  styleId="dte1fr"  size="12"  /> <a href="#" style="margin-top:5px;"  onClick="setYears(1900, '<%=info.getCurrentYear()%>' );showCalender(this, 'dte1fr');">
     <img src="<%=info.getReqUrl()%>/images/calender.png"></a></td>
              <td nowrap="nowrap">
    <html:text property="value(dte1to)" styleClass="txtStyle"  styleId="dte1to"  size="12"  /> <a href="#"  onClick="setYears(1900, '<%=info.getCurrentYear()%>' );showCalender(this, 'dte1to');">
                <img src="<%=info.getReqUrl()%>/images/calender.png"></a></td></tr>
                <tr>
    <td nowrap="nowrap">4 week</td><td nowrap="nowrap">
    <html:text property="value(dte4fr)" styleClass="txtStyle"  styleId="dte4fr"  size="12"  /> <a href="#" style="margin-top:5px;"  onClick="setYears(1900, '<%=info.getCurrentYear()%>' );showCalender(this, 'dte4fr');">
     <img src="<%=info.getReqUrl()%>/images/calender.png"></a></td>
              <td nowrap="nowrap">
    <html:text property="value(dte4to)" styleClass="txtStyle"  styleId="dte4to"  size="12"  /> <a href="#"  onClick="setYears(1900, '<%=info.getCurrentYear()%>' );showCalender(this, 'dte4to');">
                <img src="<%=info.getReqUrl()%>/images/calender.png"></a></td></tr>

<tr>
    <td nowrap="nowrap">8 week</td><td nowrap="nowrap">
    <html:text property="value(dte8fr)" styleClass="txtStyle"  styleId="dte8fr"  size="12"  /> <a href="#" style="margin-top:5px;"  onClick="setYears(1900, '<%=info.getCurrentYear()%>' );showCalender(this, 'dte8fr');">
     <img src="<%=info.getReqUrl()%>/images/calender.png"></a></td>
              <td nowrap="nowrap">
    <html:text property="value(dte8to)" styleClass="txtStyle"  styleId="dte8to"  size="12"  /> <a href="#"  onClick="setYears(1900, '<%=info.getCurrentYear()%>' );showCalender(this, 'dte8to');">
                <img src="<%=info.getReqUrl()%>/images/calender.png"></a></td></tr>
<tr><td colspan="3" align="center"> <html:submit property="value(srch)" value="Fetch" styleClass="submit" /> </td> </tr>
   
    </table>
</html:form></td></tr></table>
 </body>
   <%@include file="/calendar.jsp"%>

</html>