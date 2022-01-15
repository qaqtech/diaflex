<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page errorPage="../error_page.jsp" %>
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import="java.util.ArrayList,java.util.ArrayList,java.util.HashMap, java.sql.*, java.util.Enumeration, java.util.logging.Level"%>
<jsp:useBean id="info" class="ft.com.InfoMgr" scope="session" />
<jsp:useBean id="util" class="ft.com.JspUtil" scope="page" />
<%@taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>

<html>
  <head>
 <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
  
  <title>Offer Reports</title>
  <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/bse.js?v=<%=info.getJsVersion()%> " > </script>
<link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/bse.css?v=<%=info.getJsVersion()%>"/>
<script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/ajax.js?v=<%=info.getJsVersion()%> " > </script>
  

 
      <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/style.css?v=<%=info.getJsVersion()%>"/>
  </head>
  <%
  request.setAttribute("EXCL", "NO");
  %>
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
    <td height="5" valign="top" class="greyline"><img src="../images/grey_linebig.png" width="685" height="5" align="right" /></td>
  </tr>
  <tr>
  <td valign="top" class="hedPg">
  <table cellpadding="1" cellspacing="5"><tr><td valign="middle">
  <img src="../images/line.jpg" border="0" /></td><td valign="middle"> <span class="pgHed">
 Result
 </span> </td>
</tr></table>
  </td>
  </tr>
  <tr><td valign="top" class="hedPg"  >
  <html:form action="/report/genericReport.do?method=ColClrUpFlt" method="post">
  <table><tr>
  <td>
  Create Excel &nbsp;&nbsp;&nbsp;</td><td> 
  <img src="../images/ico_file_excel.png" border="0" onclick="goTo('genericReport.do?method=createXL','','')"/>
  </td>
  <td>&nbsp;&nbsp; Upgrade Type :</td>
   <logic:equal property="value(upgrade)" name="genericReportForm"   value="COL" >
         <td>  <html:radio property="value(upgrade)"  name="genericReportForm" value="COL"  /> &nbsp;Color&nbsp;</td>
         <td><b> Color</b></td><td> From
  <html:text property="value(colFrmVal)" size="5" name="genericReportForm"/>
  </td><td>To <html:text property="value(colToVal)" size="5" name="genericReportForm"/></td>
   </logic:equal>
     <logic:equal property="value(upgrade)" name="genericReportForm"   value="CLR" >
       <td>    <html:radio property="value(upgrade)"  name="genericReportForm" value="CLR"  />&nbsp;Clarity&nbsp;</td>
         <td><B> Clarity </b></td><td> From : 
  <html:text property="value(clrFrmVal)" size="5" name="genericReportForm"/>
  </td><td> To: <html:text property="value(clrToVal)" size="5" name="genericReportForm"/></td>
   </logic:equal>
     <logic:equal property="value(upgrade)" name="genericReportForm"   value="BOTH" >
          <td> <html:radio property="value(upgrade)"  name="genericReportForm" value="BOTH"  /> &nbsp;Both&nbsp;</td>
          
            <td><B>Color</b></td><td> From:
  <html:text property="value(colFrmVal)" size="5" name="genericReportForm"/>
  </td><td> To: <html:text property="value(colToVal)" size="5" name="genericReportForm"/></td>
        <td> <B>Clarity</b> </td><td> From : 
  <html:text property="value(clrFrmVal)" size="5" name="genericReportForm"/>
  </td><td> To: <html:text property="value(clrToVal)" size="5" name="genericReportForm"/></td>
       <td><B>Both</b> </td><td> From : 
  <html:text property="value(bothFrmVal)" size="5" name="genericReportForm"/>
  </td><td> To: <html:text property="value(bothToVal)" size="5" name="genericReportForm"/></td>
   </logic:equal>
    
  
 
  <td><html:submit property="value(fetch)" value="Filter" styleClass="submit" /> </td>
  </tr></table></html:form>
  </td></tr>
  <tr><td valign="top" class="hedPg"  >
 <jsp:include page="/stockResult.jsp" />
  </td></tr>
  </table>
  </body>
</html>