<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page errorPage="../error_page.jsp" %>
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import="java.util.ArrayList,java.util.ArrayList,java.util.HashMap, java.sql.*, java.util.Enumeration, java.util.logging.Level"%>
<jsp:useBean id="info" class="ft.com.InfoMgr" scope="session" />
<jsp:useBean id="util" class="ft.com.JspUtil" scope="page" />
<%@taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>
 
<html>
  <head>
     <%
  ArrayList  pktList = (ArrayList)request.getAttribute("dataDtl_WEBPND");
  String hdrnme = (String)request.getAttribute("hdrnme_WEBPND");
   String  webpendingrfsh = (String)request.getAttribute("webpendingrfsh");
  String  view = (String)request.getAttribute("view");%>
  <title>Reports</title>
    <script language="javaScript" type="text/javascript" src="<%=info.getReqUrl()%>/scripts/ajax.js?v=<%=info.getJsVersion()%>"></script>
    <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/bse.css?v=<%=info.getJsVersion()%>"/>
    <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/style.css?v=<%=info.getJsVersion()%>"/>
    <script src="<%=info.getReqUrl()%>/jquery/jquery.min.js"></script>
        <link rel="stylesheet" href="<%=info.getReqUrl()%>/css/jScrollbar.jquery.css" type="text/css" />
	<script src="<%=info.getReqUrl()%>/jquery/jquery.mousewheel.js"></script>
	<script src="<%=info.getReqUrl()%>/jquery/jquery-ui-draggable.js"></script>
        <script src="<%=info.getReqUrl()%>/jquery/jscroll.js"></script>
        <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/bse.js?v=<%=info.getJsVersion()%> " > </script>
  </head>
        <%String logId=String.valueOf(info.getLogId());
        String onfocus="cook('"+logId+"')";
        %>
 <body onfocus="<%=onfocus%>">
<table cellpadding="2" cellspacing="0" width="100%">
<tr><td valign="top"><b style="font-size:10px;"><%=hdrnme%>  </b></td></tr>
</table>
<table width="100%"  cellpadding="0" cellspacing="0">
  <tr>  
<%if(view.equals("Y")){
  if(pktList!=null && pktList.size()>0){
  %>
  <tr>
  <td valign="top">
  <div id="main">
  <table class="boardgrid"   style="width:100%">
  <tr>
  <th>Buyer</th><th>Date</th><th>Packet</th><th>Status</th><th>Shape</th><th>Cts</th>
  </tr>
  <%for (int i = 0; i< pktList.size(); i++) {
  HashMap byrdata = new HashMap();
  byrdata=(HashMap)pktList.get(i);
  %>
  <tr>
  <td><%=util.nvl((String)byrdata.get("byr"))%></td>
  <td><%=util.nvl((String)byrdata.get("dte"))%></td>
  <td><%=util.nvl((String)byrdata.get("vnm"))%></td>
  <td><%=util.nvl((String)byrdata.get("ct_stt"))%></td>
  <td><%=util.nvl((String)byrdata.get("sh"))%></td>
  <td align="right"><%=util.nvl((String)byrdata.get("cts"))%></td>
  </tr>
  <%}%>
  </table></div>
  </td></tr>
  <%}else{%>
  <tr><td valign="top" class="hedPg">
Sorry No Result Found</td></tr>
  <%}}%>
  </table>
  </body>
</html>
  
  