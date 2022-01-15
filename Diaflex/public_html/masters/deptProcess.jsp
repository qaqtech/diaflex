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
    <title>Department To Process</title>
  <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/bse.css?v=<%=info.getJsVersion()%>"/>
    <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/style.css?v=<%=info.getJsVersion()%>"/>
     <script language="javaScript" type="text/javascript" src="<%=info.getReqUrl()%>/scripts/ajax.js?v=<%=info.getJsVersion()%>"></script>
   <link href="<%=info.getReqUrl()%>/calendar/calendar.css" rel="stylesheet" type="text/css" />
    <script language="javaScript" type="text/javascript" src="<%=info.getReqUrl()%>/calendar/calendar.js"></script>
<script language="javaScript" type="text/javascript" src="<%=info.getReqUrl()%>/scripts/bse.js?v=<%=info.getJsVersion()%>"></script>
  </head>
   <%

   ArrayList mprcList = (ArrayList)request.getAttribute("MprcList");
    String deptIdn =(String)request.getAttribute("deptIdn");
    HashMap deptPrcMap = (HashMap)request.getAttribute("deptPrcMap");
  %>
        <%String logId=String.valueOf(info.getLogId());
        String onfocus="cook('"+logId+"')";
        %>
 <body onfocus="<%=onfocus%>" onload="MM_preloadImages('images/faunalogo.png')" topmargin="0" leftmargin="0" rightmargin="0" bottommargin="0">
<table>
  <tr>
   <td valign="top" class="hedPg">
   <%=info.getValue("dept")%>
   </td></tr>
   <tr>
   <td valign="top" class="hedPg">
   <input type="hidden" name="deptIdn" id="deptIdn" value="<%=deptIdn%>" />
   <%
  
   if(mprcList!=null && mprcList.size() > 0){%>
   <table cellpadding="5" cellspacing="5">
  <% for(int i=0 ; i < mprcList.size() ;i++ ){
    String isChecked = "";
    Mprc prc = (Mprc)mprcList.get(i);
    String mprcIdn = prc.getMprcId();
    String fldId = deptIdn+"_"+mprcIdn ;
    String fldVal = "value("+deptIdn+"_"+mprcIdn+")";
    String onChanges = "addProcess(this,"+mprcIdn+")";
   %>
   <tr>
   <td><html:checkbox property="<%=fldVal%>" onchange="<%=onChanges%>" name="deptPrcForm" value="<%=mprcIdn%>" /> </td><td><%=prc.getPrc()%> </td>
 

   </tr>
  <%}%></table>
  <%}%>
    </td></tr>
   
    </table>
 
</body>
</html>