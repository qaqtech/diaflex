<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page errorPage="../error_page.jsp" %>
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import="java.util.ArrayList,java.util.HashMap,java.sql.ResultSet,ft.com.*, java.util.Date, java.util.ArrayList, ft.com.dao.*"%>
<jsp:useBean id="info" class="ft.com.InfoMgr" scope="session" />
<jsp:useBean id="util" class="ft.com.JspUtil" scope="page" />
<%@taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>
<%@taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
 
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
     <title>Lab Location Report</title>
     <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/bse.css?v=<%=info.getJsVersion()%>"/>
      <script src="<%=info.getReqUrl()%>/scripts/bse.js?v=<%=info.getJsVersion()%>" type="text/javascript"></script>
     <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/style.css?v=<%=info.getJsVersion()%>"/>
    <script language="javaScript" type="text/javascript" src="<%=info.getReqUrl()%>/scripts/ajax.js?v=<%=info.getJsVersion()%>"></script>
       <script language="javaScript" type="text/javascript" src="<%=info.getReqUrl()%>/calendar/calendar.js"></script>
   <link href="<%=info.getReqUrl()%>/calendar/calendar.css" rel="stylesheet" type="text/css" />
 </head>
   <%
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
  <img src="../images/line.jpg" border="0" /></td><td valign="middle"> <span class="pgHed">Lab Location Report</span> </td>
</tr></table>
  </td>
  </tr>
 <tr><td></td></tr>
  <tr>
   <td valign="top" class="hedPg" >
     <html:form action="/report/assortLabPending.do?method=labReport" method="post" onsubmit="return validate_assort()">
  <table  class="grid1">
  <tr><th>Generic Search </th></tr>
  <tr>
   <td>
 <jsp:include page="/genericSrch.jsp"/>
   </td></tr>
   <tr>
   <td align="center"><html:submit property="value(loc)" value="Lab Location" styleClass="submit" />
   <html:submit property="value(daily)" value="Lab I/R" styleClass="submit" /></td>
   </tr>
   </table>
   </html:form>
   </td></tr>
   <%
   String method = util.nvl(request.getParameter("method"));
   String view = util.nvl((String)request.getAttribute("view"));
   ArrayList pktList = (ArrayList)session.getAttribute("pktList");
   ArrayList itemHdr = (ArrayList)session.getAttribute("itemHdr");
   if(pktList!=null && pktList.size()>0){
   %>
   <tr>
   <!--<td valign="top" class="hedPg" >
   Create Excel <img src="../images/ico_file_excel.png" onclick="goTo('orclReportAction.do?method=createXL','','')" 
   border="0"/> </td>-->
   <td valign="top" class="hedPg" >
   Create Pdf <img src="../images/PDFIconSmall.jpg" title="Click Here To Download Pdf" onclick="goTo('assortLabPending.do?method=createReportPDF','','')" 
   border="0"/> 
   </td>
   </tr>
   <%
   if(view.equals("Y")){
   %>
   <tr>
    <td valign="top" class="hedPg">
    
    <display:table name="sessionScope.assortLabPenForm.pktList" style="width:70%" class="displayTable">
     
    <display:column property="STATUS" style="text-align:center; font-weight: bold;" title="STATUS" group="1" />
    <display:column property="LAB" style="text-align:center; font-weight: bold;" title="LAB" group="2"  />
    <display:column property="LAB_LOC" style="text-align:center;" title="LAB_LOC"  sortable="false" />
    <display:column property="COUNT" style="text-align:right;" title="PCS"  sortable="false" />
    <display:column property="CTS" style="text-align:right;" title="CTS"  sortable="false" />
    <display:column property="ASRT_VLU" style="text-align:right;" title="ASRT_VLU"  sortable="false" />
    <display:column property="ASRT_AVG" style="text-align:right;" title="ASRT_AVG"  sortable="false" />
    
    
    </display:table>
    </td>
    </tr>
    <%}else if(view.equals("N")){%>
    <tr>
    <td valign="top" class="hedPg">
    <display:table name="sessionScope.assortLabPenForm.pktList" style="width:70%" class="displayTable">
    <display:column property="STATUS" style="text-align:center; font-weight: bold;" title="STATUS" group="1" />
    <display:column property="LAB" style="text-align:center; font-weight: bold;" title="LAB" group="2"  />
    <display:column property="COUNT" style="text-align:right;" title="PCS"  sortable="false" />
    <display:column property="CTS" style="text-align:right;" title="CTS"  sortable="false" />
    <display:column property="AVG" style="text-align:right;" title="AVG"  sortable="false" />
    <display:column property="AMT" style="text-align:right;" title="AMOUNT"  sortable="false" />
    <display:column property="ASRT_VLU" style="text-align:right;" title="ASRT_VLU"  sortable="false" />
    <display:column property="ASRT_AVG" style="text-align:right;" title="ASRT_AVG"  sortable="false" />
    </display:table>
    </td>
    </tr>
    <%}} else{%>
    <tr><td valign="top" class="hedPg">
   Sorry no result found
   </td></tr>
   <%}%>
<tr>
     <td><jsp:include page="/footer.jsp" /> </td>
</tr>
</table>
  <%@include file="/calendar.jsp"%>
</body>
</html>