 <!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
 
<%@ page errorPage="../error_page.jsp" %>
<%@ page contentType="text/html;charset=windows-1252"%>
<jsp:useBean id="util" class="ft.com.JspUtil" scope="page" />
<jsp:useBean id="info" class="ft.com.InfoMgr" scope="session" />
<%@ page import="java.util.ArrayList,java.util.HashMap,java.sql.ResultSet,ft.com.*, java.util.Date, java.util.ArrayList, ft.com.dao.*"%>
<%@taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
 
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>Mix Inward Report</title>
    <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/bse.css?v=<%=info.getJsVersion()%>"/>
    <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/general.css?v=<%=info.getJsVersion()%>"/>
     <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/style.css?v=<%=info.getJsVersion()%>"/>
      <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/bse.js?v=<%=info.getJsVersion()%> " > </script>
    <script language="javaScript" type="text/javascript" src="<%=info.getReqUrl()%>/calendar/calendar.js"></script>
   <link href="<%=info.getReqUrl()%>/calendar/calendar.css" rel="stylesheet" type="text/css">
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
  <img src="../images/line.jpg" border="0" /></td><td valign="middle"> <span class="pgHed">Mix Inward Report</span> </td>
</tr></table>
  </td>
  </tr>
    <tr><td valign="top" class="tdLayout">
      <html:form action="mixre/mixInwardAction.do?method=fetch" method="post" >
    <table>
    <tr><td>From Date : </td>  
       <td><html:text property="value(frmdte)" styleId="frmdte" name="mixInwardReportForm" size="15" />&nbsp;<img src="<%=info.getReqUrl()%>/images/calender.png" onClick="setYears(2011, <%=info.getCurrentYear()%>);showCalender(this, 'frmdte');"> 
       </td>
      <td>To Date : </td>  
       <td><html:text property="value(todte)" styleId="todte" name="mixInwardReportForm" size="15" />&nbsp;<img src="<%=info.getReqUrl()%>/images/calender.png" onClick="setYears(2011, <%=info.getCurrentYear()%>);showCalender(this, 'todte');"> 
       </td>
       <td> <html:submit property="value(fetch)" value="Fetch" styleClass="submit" /></td>
      </tr>
      
    </table>
    </html:form>
    </td></tr>
    <tr> <td valign="top" class="tdLayout" height="10px">
  </td></tr>
     <tr> <td valign="top" class="tdLayout">
     <%
     String view = (String)request.getAttribute("view");
     if(view!=null){
    %>
      <display:table name="sessionScope.mixInwardReportForm.PKTLIST" requestURI="" export="true"  style="width:60%"   sort="list" class="displayTable">
       <display:column property="TYP"  title="Type"  />
        <display:column property="DTE"  title="Date"  />
         <display:column property="BOXTYP"  title="Stock Ctg"  /><!--  -->
           <display:column property="QTY"  title="Qty"  />
          <display:column property="CTS"  title="Cts"  />
           <display:column property="AVGRTE"  title="Avg Rate"  />
    <display:setProperty name="export.excel.filename" value="MixInwardReport.xls"/>
  <display:setProperty name="export.xls" value="true" />
      </display:table>
    
     <%}%>
     </td></tr>
     <tr><td>
     <jsp:include page="/footer.jsp" />
     </td></tr>
    </table>
         <%@include file="/calendar.jsp"%>
    </body>
</html>