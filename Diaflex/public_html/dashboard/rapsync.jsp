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
  String hdrnme = (String)request.getAttribute("hdrnme_rapsync");
  String  dsalerfsh = (String)request.getAttribute("rapsyncrfsh");
  HashMap rapsyncdata = (HashMap)request.getAttribute("rapsyncdata");
  ArrayList  countitemHdr = (ArrayList)request.getAttribute("countitemHdr");
  ArrayList  rapsyncdataLst = (ArrayList)request.getAttribute("rapsyncdataLst");
  String  view = (String)request.getAttribute("view");
  boolean dta=true;
  %>

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
<tr><td valign="top"><b style="font-size:10px;"><%=hdrnme%> </b></td></tr>
</table>
<table width="100%"  cellpadding="0" cellspacing="0">
  <tr>  
<%if(view.equals("Y")){
if(countitemHdr!=null && countitemHdr.size()>0){
dta=false;
%>
  <tr>
  <td valign="top">
  <div id="main">
  <table class="boardgrid"   style="width:100%">
  <tr><th colspan="3">Summary Details</th></tr>
  <tr>
  <th>Status</th><th>Count</th><th>Packet Id's</th></tr>
  <%for(int i=0;i<countitemHdr.size();i++){
  String stt=util.nvl((String)countitemHdr.get(i));
  %>
  <tr>
  <td align="center"><%=stt%></td>
  <td align="right"><%=util.nvl((String)rapsyncdata.get(stt+"_COUNT"))%></td>
   <td align="right"><%=util.nvl((String)rapsyncdata.get(stt+"_VNM"))%></td>
  </tr>
  <%}%>
  </table></div>
  </td></tr>
  <%}%>


<%if(rapsyncdataLst!=null && rapsyncdataLst.size()>0){
dta=false;
%>
  <tr>
  <td valign="top">
  <div id="main">
  <table class="boardgrid"   style="width:100%">
    <tr><th colspan="4">Details</th></tr>
  <tr>
  <th>Sr No</th><th>Status</th><th>Packet Id</th><th>Log Time</th></tr>
  <%for(int i=0;i<rapsyncdataLst.size();i++){
  ArrayList rapsyncdatadtl=(ArrayList)rapsyncdataLst.get(i);
  %>
  <tr>
  <td align="center"><%=i+1%></td>
  <td align="center"><%=util.nvl((String)rapsyncdatadtl.get(0))%></td>
  <td align="center"><%=util.nvl((String)rapsyncdatadtl.get(1))%></td>
   <td align="center"><%=util.nvl((String)rapsyncdatadtl.get(2))%></td>
  </tr>
  <%}%>
  </table></div>
  </td></tr>
  <%}%>
  <%if(dta){%>
  <tr><td valign="top" class="hedPg">
  Sorry No Result Found</td></tr>
  <%}}%>
  </table>
  </body>
</html>
  
  