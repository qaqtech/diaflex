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
   
    <title>Web Login Report</title>
     <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/style.css?v=<%=info.getJsVersion()%>"/>
    <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/bse.css?v=<%=info.getJsVersion()%>"/>
    <script language="javaScript" type="text/javascript" src="<%=info.getReqUrl()%>/calendar/calendar.js"></script>
   <link href="<%=info.getReqUrl()%>/calendar/calendar.css" rel="stylesheet" type="text/css" />
<script language="javaScript" type="text/javascript" src="<%=info.getReqUrl()%>/scripts/bse.js?v=<%=info.getJsVersion()%>"></script>
  </head>
 <%
 
        String logId=String.valueOf(info.getLogId());
        String onfocus="cook('"+logId+"')";
        %>
 <body onfocus="<%=onfocus%>"onload="MM_preloadImages('images/faunalogo.png')" topmargin="0" leftmargin="0" rightmargin="0" bottommargin="0">
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
   Web Login Report
  </span> </td>
</tr></table>
  </td>
  </tr>
  <tr><td valign="top" class="hedPg"  >
  <html:form action="website/userLoginInfo.do?method=fetch" method="post">
  <table>
  <tr><td>Byr List : </td><td>
  <html:select property="byrId" styleId="byrId"  name="userLoginInfoForm"   >
            <html:option value="0" >--select--</html:option>
            <html:optionsCollection property="byrList" name="userLoginInfoForm" 
            label="byrNme" value="byrIdn" />
  </html:select>
  </td></tr>
  <tr>
  <td>Date : </td><td>
  <table><tr><td>
   <div class="float">   <html:text property="frmDte"  styleId="frmDte" name="userLoginInfoForm"/></div>
            <div class="float">     <a href="#"  onClick="setYears(1900, <%=info.getCurrentYear()%>);showCalender(this, 'frmDte');">
                <img src="<%=info.getReqUrl()%>/images/calender.png"></a></div>
                </td><td>To</td>
                <td>
                 <div class="float">   <html:text property="toDte"  styleId="toDte" name="userLoginInfoForm"/></div>
            <div class="float">     <a href="#"  onClick="setYears(1900, <%=info.getCurrentYear()%>);showCalender(this, 'toDte');">
                <img src="<%=info.getReqUrl()%>/images/calender.png"></a></div>
    </td></tr></table>
  </td>
  </tr>
  <tr><td colspan="2"><html:submit property="submit" value="Submit" styleClass="submit" /> </td> </tr>
  </table>
  </html:form>
  </td></tr>
  <tr><td valign="top" class="hedPg"  >
  <%String view = (String)request.getAttribute("view");
  if(view!=null){
  %>
   <display:table name="sessionScope.userLoginInfoForm.usrDtlList" requestURI=""   sort="list"   class="displayTable">
    <display:column property="Count"  title="SR NO."  />
    <display:column property="logId"  title="Log Id"  />
    <display:column property="dteTm"  title="Date" />
    <display:column property="nme"  title="Name" />
    <display:column property="cntPrsn" title="Contact Prsn" />
    <display:column property="usr"   title="User"  />
    <display:column property="cl_ip"   title="CL Ip"  />
    <display:column property="cl_usr"   title="CL Usr"  />
    <display:column property="cl_browser"   title="CL Browser"  />
    
  <display:setProperty name="export.pdf.filename" value="Login_Details.pdf"/>
  <display:setProperty name="export.pdf" value="true" />
  
    </display:table>
    <%}%>
  </td></tr>
    <tr>
    <td><jsp:include page="/footer.jsp" /> </td>
    </tr>
  </table>
  
  <%@include file="../calendar.jsp"%>
  </body>
</html>