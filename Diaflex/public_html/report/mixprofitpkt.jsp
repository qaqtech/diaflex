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
<link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/bse.css?v=<%=info.getJsVersion()%>"/>
<link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/general.css?v=<%=info.getJsVersion()%>"/>
<script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/ajax.js?v=<%=info.getJsVersion()%> " > </script>
<script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/bse.js?v=<%=info.getJsVersion()%> " > </script>
  <script type="text/javascript" src="<%=info.getReqUrl()%>/PopScripts/popup.js?v=<%=info.getJsVersion()%> " > </script>
    <script src="<%=info.getReqUrl()%>/PopScripts/jquery-1.2.6.min.js" type="text/javascript"></script>
   <script language="javaScript" type="text/javascript" src="<%=info.getReqUrl()%>/calendar/calendar.js"></script>
   <link href="<%=info.getReqUrl()%>/calendar/calendar.css" rel="stylesheet" type="text/css" />

 
      <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/style.css?v=<%=info.getJsVersion()%>"/>

  <title>Mix Profit/Loss Report Packets</title>
 
  </head>
  
<body topmargin="0" leftmargin="0" rightmargin="0" bottommargin="0">
<table width="100%"  border="0" align="center" cellpadding="0" cellspacing="0">
  
  <%
    ArrayList pktList= (ArrayList)session.getAttribute("mixprofitpktListpkt");
    ArrayList itemHdr= (ArrayList)session.getAttribute("mixprofititemHdrpkt");
    String view= util.nvl((String)request.getAttribute("view"));
    
    if(!view.equals("")){
    if(pktList!=null && pktList.size()>0){
    %>
    <tr>
    <td class="hedPg">
    Create Excel <img src="../images/ico_file_excel.png" onclick="goTo('orclRptAction.do?method=mixprofitcreateXL&typ=PKT','','')" border="0"/> 
    </td>
    </tr>
    
    
<tr>
<td class="hedPg">
<table class="grid1">
<tr>
<%for(int i=0;i<itemHdr.size();i++){%>
<th><%=itemHdr.get(i)%></th>
<%}%>
</tr>
<%for(int i=0;i<pktList.size();i++){
HashMap pktPrpMap=(HashMap)pktList.get(i);
%>
<tr>
<%
for(int j=0;j<itemHdr.size();j++){
%>
<td align="right"><%=util.nvl((String)pktPrpMap.get((String)itemHdr.get(j)))%></td>
<%}%>
</tr>
<%}%>
</table>
</td>
</tr>
    <%}else{%>
<tr><td valign="top" class="hedPg">
Sorry No Result Found</td></tr>
<%}}%>
</table>  
</body>
</html>