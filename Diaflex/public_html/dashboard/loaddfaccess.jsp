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
  ArrayList  pgList = (ArrayList)request.getAttribute("pgList_DF");
  HashMap  dataDtl = (HashMap)request.getAttribute("dataDtl_DF");
  String hdrnme = (String)request.getAttribute("hdrnme_DF");
   String  dfaccessrfsh = (String)request.getAttribute("dfaccessrfsh");
  String  view = (String)request.getAttribute("view");
    %>  
  <title>Reports</title>
    <script language="javaScript" type="text/javascript" src="<%=info.getReqUrl()%>/scripts/ajax.js?v=<%=info.getJsVersion()%>"></script>
    <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/bse.css?v=<%=info.getJsVersion()%>"/>
    <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/style.css?v=<%=info.getJsVersion()%>"/>
    <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/bse.js?v=<%=info.getJsVersion()%>" ></script>
    <script src="<%=info.getReqUrl()%>/jquery/jquery.min.js"></script>
        <link rel="stylesheet" href="<%=info.getReqUrl()%>/css/jScrollbar.jquery.css" type="text/css" />
	<script src="<%=info.getReqUrl()%>/jquery/jquery.mousewheel.js"></script>
	<script src="<%=info.getReqUrl()%>/jquery/jquery-ui-draggable.js"></script>
        <script src="<%=info.getReqUrl()%>/jquery/jscroll.js"></script>
        
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
  if(pgList!=null && pgList.size()>0){
  %>
  <tr>
  <td valign="top">
  <div id="main">
  <table class="boardgrid"   style="width:100%">
  <tr>
  <th>Page</th><th>User</th><th>Hit</th>
  </tr>
  <%for (int i = 0; i< pgList.size(); i++) {
  String pg=(String)pgList.get(i);
  ArrayList usrList=(ArrayList)dataDtl.get(pg);
  %>
  <%for (int j = 0; j< usrList.size(); j++) {
  HashMap usrdata = new HashMap();
  usrdata=(HashMap)usrList.get(j);
  if(j==0){
  %>
  <tr><td><%=util.nvl((String)pg)%></td>
  <%}else{%>
  <tr><td></td>
  <%}%>
  <td><%=util.nvl((String)usrdata.get("usr"))%></td>
  <td align="right"><%=util.nvl((String)usrdata.get("cnt"))%></td>
  <%}%>
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
  
  