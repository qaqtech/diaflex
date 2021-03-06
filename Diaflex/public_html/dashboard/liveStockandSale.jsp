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
   ArrayList  deptList = (ArrayList)request.getAttribute("deptList_livestock");
   HashMap livedata = (HashMap)request.getAttribute("livedata_livestock");
   String hdrnme = (String)request.getAttribute("hdrnme_livestock");
   String  livestockrfsh= (String)request.getAttribute("livestockrfsh");
   String  view = (String)request.getAttribute("view");%>
  
  <title>Live Stock And Sale Reports</title>
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
<tr><td valign="top">
<b style="font-size:10px;"><%=hdrnme%>  </b>
</td></tr>
</table>
<table width="100%"  cellpadding="0" cellspacing="0" style="position:relative">
  <tr>  
<%if(view.equals("Y")){
  if(deptList!=null && deptList.size()>0){
  %>
  <tr>
  <td valign="top">
  <div id="main">
  <table class="boardgrid"   style="width:100%">
  <tr><th colspan="6">Live Stock</th><th colspan="2">Today Sale</th></tr>
  <tr>
  <th>Department</th><th>Carat</th><th>Avg</th><th>Days</th><th>Amount</th>
  <th>%</th><th>S.Amt</th><th>%</th>
  </tr>
  <%  int count =0;
  for (int i = 0; i< deptList.size(); i++) {
  String dept=util.nvl((String)deptList.get(i));
  
  count++;
   %> <tr>
   <td nowrap="nowrap" ><%=dept%></td>
   <td nowrap="nowrap" align="right"><%=util.nvl((String)livedata.get(dept+"CTS"))%></td>
    <td nowrap="nowrap" align="right"><%=util.nvl((String)livedata.get(dept+"AVG"))%></td>
    <td nowrap="nowrap" align="right"><%=util.nvl((String)livedata.get(dept+"DYS_AVG"))%></td>
    <td nowrap="nowrap" align="right"><%=util.nvl((String)livedata.get(dept+"VLU"))%></td>
     <td nowrap="nowrap" align="right"><%=util.nvl((String)livedata.get(dept+"DEPTSTKPCT"))%>%</td>   
     <td nowrap="nowrap" align="right"><%=util.nvl((String)livedata.get(dept+"SALVLU"),"0")%></td>
     <td nowrap="nowrap" align="right"><%=util.nvl((String)livedata.get(dept+"SALPCT"),"0")%>%</td>
  </tr>
  <%if(count==deptList.size()){%>
  <tr>
    <td><b>Total</b></td>
    <td align="right"><b><%=util.nvl((String)livedata.get("TCTS"))%></b></td>
    <td align="right"><%=util.nvl((String)livedata.get("TAVG"))%></b></td>
    <td align="right"><b><%=util.nvl((String)livedata.get("TDYS"))%></b></td>
    <td align="right"><b><%=util.nvl((String)livedata.get("TVLU"))%></b></td>
    <td align="right"><b>100%</b></td>
    <td align="right" bgcolor="Red"><b><%=util.nvl((String)livedata.get("STVLU"))%></b></td>
    <td align="right" bgcolor="Red"><b><%=util.nvl((String)livedata.get("SALTPCT"))%>%</b></td>
  </tr>
  <%}}%>
  </table></div>
  </td></tr>
  <%}else{%>
  <tr><td valign="top" class="hedPg">
Sorry No Result Found</td></tr>
  <%}}%>
  </table>
  </body>
</html> 