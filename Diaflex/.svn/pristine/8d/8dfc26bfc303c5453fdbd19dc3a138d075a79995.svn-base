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
    <title>Holiday Master</title>
  <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/bse.css?v=<%=info.getJsVersion()%>"/>
    <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/style.css?v=<%=info.getJsVersion()%>"/>
     <script language="javaScript" type="text/javascript" src="<%=info.getReqUrl()%>/calendar/calendar.js"></script>
     <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/bse.js?v=<%=info.getJsVersion()%> " > </script>
   <link href="<%=info.getReqUrl()%>/calendar/calendar.css" rel="stylesheet" type="text/css" />
     <script language="javaScript" type="text/javascript" src="<%=info.getReqUrl()%>/scripts/Validation.js"></script>

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
    <td height="5" valign="top" class="greyline"><img src="<%=info.getReqUrl()%>/images/grey_linebig.png" width="685" height="5" align="right" /></td>
  </tr>
  <tr>
  <td valign="top" class="hedPg">
  <table cellpadding="1" cellspacing="5"><tr><td valign="middle">
  <img src="../images/line.jpg" border="0" /></td><td valign="middle"> <span class="pgHed">Holiday Master</span> </td>
</tr></table>
  </td>
  </tr>
  <tr>
  <td valign="top" class="hedPg">
  <html:form action="/masters/holidayAction.do?method=save" method="post">
  <table class="grid1">

    <tr><th colspan="4">Holiday Form</th></tr>
    <tr><td>From Date </td><td>
      <div class="float">   <html:text property="value(frmDte)" name="holidayFrm"  styleId="frmDte" /></div>
            <div class="float"> <a href="#"  onClick="setYears(1900,2020);showCalender(this, 'frmDte');">
                <img src="<%=info.getReqUrl()%>/images/calender.png"></a></div>
    
    </td> <td> To Date</td> <td>
    <div class="float">   <html:text property="value(toDte)" name="holidayFrm" styleId="toDte" /></div>
            <div class="float">     <a href="#"  onClick="setYears(1900, 2020);showCalender(this, 'toDte');">
                <img src="<%=info.getReqUrl()%>/images/calender.png"></a></div>
    
    </td></tr>
    <tr><td> Holiday Type</td><td colspan="3"> 
    <html:select property="value(typ)"  name="holidayFrm" styleId="typ" >
   <html:option value="0" >---select---</html:option>
  <html:optionsCollection property="holidayList" label="FORM_TTL" value="FORM_NME" />
  </html:select> 
    </td></tr>
     <tr><td >Remark</td><td colspan="3"><html:text property="value(rmk)" name="holidayFrm"/> </td></tr>
    <tr><td colspan="4" align="center"><html:submit property="value(add)" onclick="return validateHoliDay()" styleClass="submit"  value="save Changes"/> &nbsp;&nbsp;<html:submit property="value(view)"  styleClass="submit"  value="View"/> </td></tr></table>
  </html:form>
  
  </td></tr>
  <tr>
  <td valign="top" height="30px" class="hedPg">
  </td></tr>
  <tr>
  <td valign="top" class="hedPg">
  <%  ArrayList holidayList = (ArrayList)request.getAttribute("holidayList");
  if(holidayList!=null && holidayList.size()>0){
  %>
 <table class="grid1">

    <tr><th>Sr No</th><th>Date</th><th>Holiday Type</th><th>Remark</th><th>Action</th></tr>
    <%for(int j=0 ; j < holidayList.size() ;j++){
    ArrayList holiday = (ArrayList)holidayList.get(j);
    String link = info.getReqUrl() + "/masters/holidayAction.do?method=delete&idn="+holiday.get(3);
    String onclick = "return setDeleteConfirm('"+link+"')";
    %>
    <tr><td><%=j+1%></td>
    <td><%=holiday.get(0)%></td><td><%=holiday.get(1)%></td><td><%=holiday.get(2)%></td>
    <td><A href="<%=link%>" onclick="<%=onclick%>">Delete</a></td>
    </tr>
    <%}%>
    </table>
  <%}%>
  
  </td></tr>
  </table>
  <%@include file="../calendar.jsp"%>
  
  </body>
</html>