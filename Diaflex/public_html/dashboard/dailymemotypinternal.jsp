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
ArrayList  dtlList = (ArrayList)request.getAttribute("dailyMemoR_I");
  String hdrnme = (String)request.getAttribute("hdrnme_I");
   String  memotypIrfsh = (String)request.getAttribute("memotypIrfsh");
  String  view = (String)request.getAttribute("view");%>
  <title>Daily Memo Reports</title>
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
  if(dtlList!=null && dtlList.size()>0){
  %>
  <tr>
  <td valign="top">
  <div id="main">
  <table class="boardgrid"   style="width:100%">
  <tr>
  <th nowrap="nowrap">Sale Person</th><th>Buyer</th><th>Cnt</th><th>Qty</th><th>Cts</th><th>Vlu</th>
  </tr>
  <%for (int i = 0; i< dtlList.size(); i++) {
  HashMap byrdata=new HashMap();
  byrdata=(HashMap)dtlList.get(i);
   %>
  <tr>
   <td><%=util.nvl((String)byrdata.get("EMP"))%></td>
   <td><%=util.nvl((String)byrdata.get("BYR"))%></td>
    <td align="right"><%=util.nvl((String)byrdata.get("CNT"))%></td>
    <td align="right"><%=util.nvl((String)byrdata.get("QTY"))%></td>
    <td align="right"><%=util.nvl((String)byrdata.get("CTS"))%></td>
    <td align="right"><%=util.nvl((String)byrdata.get("VLU"))%></td>
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
  
  