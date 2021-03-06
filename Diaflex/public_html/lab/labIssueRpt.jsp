<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"

"http://www.w3.org/TR/html4/loose.dtd">

<%@ page errorPage="../error_page.jsp" %>

<%@ page contentType="text/html;charset=windows-1252"%>

<%@ page import="java.sql.ResultSet, java.util.ArrayList,java.util.Collections,java.util.ArrayList,java.util.HashMap,java.text.SimpleDateFormat, java.util.Calendar"%>

<jsp:useBean id="util" class="ft.com.JspUtil" scope="page" />

<jsp:useBean id="info" class="ft.com.InfoMgr" scope="session" />


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

       <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/memoScript.js?v=<%=info.getJsVersion()%> " > </script>

   <script language="javaScript" type="text/javascript" src="<%=info.getReqUrl()%>/calendar/calendar.js"></script>

   <link href="<%=info.getReqUrl()%>/calendar/calendar.css" rel="stylesheet" type="text/css" />

 

      <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/style.css?v=<%=info.getJsVersion()%>"/>

 

  <title>Lab Issue Status Report</title>

  </head>

        <%String logId=String.valueOf(info.getLogId());

        String onfocus="cook('"+logId+"')";

         ArrayList issueSttLst =(ArrayList)request.getAttribute("issueSttLst");

        %>

<body onfocus="<%=onfocus%>" onkeypress="return disableEnterKey(event);" >

<table width="100%"  border="0" align="center" cellpadding="0" cellspacing="0" class="mainbg">

   <tr>

    <td height="103" valign="top">

   <jsp:include page="/header.jsp" />

    

    </td>

  </tr>

    <tr>

    <td height="5" valign="top" class="greyline"><img src="../images/grey_linebig.png" width="685" height="5" align="right" /></td>

  </tr>

   <tr><td valign="top" class="hedPg"  >

  <table cellpadding="1" cellspacing="5"><tr><td valign="middle">

  <img src="../images/line.jpg" border="0" /></td><td valign="middle"> <span class="pgHed">Lab Issue Report

  </span> </td>

</tr></table>

  </td>

  </tr>

<tr>

<td valign="top" class="hedPg">

<html:form  action="lab/labIssueRes.do?method=fatch"  >

<table >

<tr>

<td>

<table class="grid1">

<tr><th align="center" colspan="2">Lab Issue Report</th></tr>

   <tr>

    <td>Issue Date :</td><td>

    <html:text property="value(dtefr)" styleClass="txtStyle"  styleId="dtefr"  size="12"  /> <a href="#" style="margin-top:5px;"  onClick="setYears(1900, '<%=info.getCurrentYear()%>' );showCalender(this, 'dtefr');">

                <img src="<%=info.getReqUrl()%>/images/calender.png"></a>

     To

    <html:text property="value(dteto)" styleClass="txtStyle"  styleId="dteto"  size="12"  /> <a href="#"  onClick="setYears(1900, '<%=info.getCurrentYear()%>' );showCalender(this, 'dteto');">

                <img src="<%=info.getReqUrl()%>/images/calender.png"></a>

    </td>

    </tr>

<tr>

<td nowrap="nowrap"> <span class="txtBold" >Issue Id :</span></td><td> <html:text styleId="issuid" size="14" property="value(issuid)" styleClass="txtStyle" name="labIssueResSttForm" />   </td>

</tr>

  <tr><td><span class="txtBold" >Status :</span></td><td><html:select property="value(resstt)"  styleId="resstt" name="labIssueResSttForm" >

             <html:option value="" >----Select----</html:option>

              <html:option value="PENDING" >PENDING</html:option>

              <html:option value="SUCCESS" >SUCCESS</html:option>

                <html:option value="FAILURE" >FAILUR</html:option>

       

          </html:select>  </td>   

    </tr>

    <tr><td align="center" colspan="2"><button type="submit"  name="fetch" class="submit">Fetch</button></td>

</tr>

</table>

</tr></table>

</html:form>

</td>

</tr>

<% if(issueSttLst.size()>0 && issueSttLst!=null){

%>

<tr><td class="hedPg">

<table class="grid1">

<tr><th nowrap="nowrap">Issue Id </th><th nowrap="nowrap">Issue Date</th><th nowrap="nowrap">Response Status</th><th nowrap="nowrap">Response Error</th><th>Response Message </th></tr>

    <% for(int i=0;i< issueSttLst.size();i++){

                     HashMap Dtl=(HashMap)issueSttLst.get(i);
                     String issId =(String)Dtl.get("IssueId");
                     String url="labIssueRes.do?method=DownloadXml&issID="+issId;

%> 

<tr><td>
<a href="#" onclick="directLoad('<%=url%>')">
<%=Dtl.get("IssueId")%></a> </td><td><%=Dtl.get("IssueDate")%></td><td align="center"><%=Dtl.get("ResStatus")%></td><td  width="250px"><%=Dtl.get("ResError")%></td><td width="400px"><%=Dtl.get("ResMsg")%></td>


</tr>

<%}%>

</table>

</td>

</tr>

<%}%>

</table>

<%@include file="/calendar.jsp"%>

</body>

</html>

