<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page errorPage="../error_page.jsp" %>
<%@ page contentType="text/html;charset=windows-1252"%>

<%@ page import="java.util.ArrayList,java.util.ArrayList,java.util.HashMap, java.sql.*, java.util.Enumeration, java.util.logging.Level"%>

<jsp:useBean id="info" class="ft.com.InfoMgr" scope="session" />
<jsp:useBean id="util" class="ft.com.JspUtil" scope="page" />
<%@taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>

<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
   
    <title>Diaflex : Admin Panel - Show Details</title>
     <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/style.css?v=<%=info.getJsVersion()%>"/>
    <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/bse.css?v=<%=info.getJsVersion()%>"/>
    <script language="javaScript" type="text/javascript" src="<%=info.getReqUrl()%>/calendar/calendar.js"></script>
   <link href="<%=info.getReqUrl()%>/calendar/calendar.css" rel="stylesheet" type="text/css" />
<script language="javaScript" type="text/javascript" src="<%=info.getReqUrl()%>/scripts/bse.js?v=<%=info.getJsVersion()%>"></script>
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
    <td height="5" valign="top" class="greyline"><img src="../images/grey_linebig.png" width="685" height="5" align="right" /></td>
  </tr>
  <tr>
  <td valign="top" class="hedPg">
  <table cellpadding="1" cellspacing="5"><tr><td valign="middle">
  <img src="../images/line.jpg" border="0" /></td><td valign="middle"> <span class="pgHed">
 Show Detail
 </span> </td>
</tr></table>
  </td>
  </tr>
  <tr><td valign="top" class="hedPg"  >
  <% session.setAttribute("uid", "100"); 
  %>
  
  
  <table class="grid1">
  
  <html:form action="website/adminShowDetails.do" method="post">
  
  <tr>
      <td>Show Name: </td>
      <td><html:text property="strShowName" name="adminShowDetailsForm"/></td>
  </tr>
  <tr>
      <td>Company Name: </td>
      <td><html:text property="strCompanyName" name="adminShowDetailsForm"/></td>
  </tr>
  <tr>
      <td>Start Date: </td>
      <td><html:text onfocus="showCalendarControl(this);" property="dtStartDate" name="adminShowDetailsForm"/></td>
  </tr>
  
  <tr>
      <td>End Date: </td>
      <td><html:text onfocus="showCalendarControl(this);" property="dtEndDate" name="adminShowDetailsForm"/></td>
  </tr>
  
  <tr>
      <td>Booth No: </td>
      <td><html:text property="strBoothNo" name="adminShowDetailsForm"/></td>
  </tr>
  <tr>
      <td>Venue Address line 1: </td>
      <td><html:text property="strVenue1" name="adminShowDetailsForm"/></td>
  </tr>
  <tr>
      <td>Venue Address line 2: </td>
      <td><html:text property="strVenue2" name="adminShowDetailsForm"/></td>
  </tr>
  <tr>
      <td>Venue Address line 3: </td>
      <td><html:text property="strVenue3" name="adminShowDetailsForm"/></td>
  </tr>
  
  <tr>
    <td>
        <html:submit property="adminShowDetails" value="Submit"  styleClass="submit"/>
    </td>
    <td>
        <input type="reset" value="Clear" class="submit">
    </td>
  </tr>
  
  <html:hidden value="1" property="flag"/>
  <html:hidden value="store" property="method"/>
  </html:form>
  
      <tr></tr>
      <tr></tr>
      <tr> <td><strong> 
  
  <%
  
  if(request.getAttribute("success")!= null)
  {
  String success = request.getAttribute("success").toString();
    if(success.equals("0"))
    {
    %>
      Upload Failed! 
  <% 
    }
    else if(success.equals("1"))
    {
    %>
       Upload Successful! 
  <% 
    }
  }
  %>
  </strong></td>
      </tr>
      </table></td></tr></table>
    </body>
</html>