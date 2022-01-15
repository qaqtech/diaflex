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
    <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/bse.js?v=<%=info.getJsVersion()%> " > </script>
<link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/bse.css?v=<%=info.getJsVersion()%>"/>
<link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/general.css?v=<%=info.getJsVersion()%>"/>
<script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/ajax.js?v=<%=info.getJsVersion()%> " > </script>
  <script type="text/javascript" src="<%=info.getReqUrl()%>/PopScripts/popup.js?v=<%=info.getJsVersion()%> " > </script>
    <script src="<%=info.getReqUrl()%>/PopScripts/jquery-1.2.6.min.js" type="text/javascript"></script>
   <script language="javaScript" type="text/javascript" src="<%=info.getReqUrl()%>/calendar/calendar.js"></script>
   <link href="<%=info.getReqUrl()%>/calendar/calendar.css" rel="stylesheet" type="text/css" />

 
      <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/style.css?v=<%=info.getJsVersion()%>"/>

  <title>Custom Screen Report</title>
 
  </head>

        <%String logId=String.valueOf(info.getLogId());
        String onfocus="cook('"+logId+"')";
         String menu = util.nvl((String)request.getParameter("menu"));
        %>
 <body onfocus="<%=onfocus%>" onload="MM_preloadImages('images/faunalogo.png')" topmargin="0" leftmargin="0" rightmargin="0" bottommargin="0">
 
  <input type="hidden" id="reqUrl" name="reqUrl" value="<%=info.getReqUrl()%>"/>
<div id="popupContactASSFNL">
<table class="popUpTb">
<tr><td height="5%">&nbsp;&nbsp;&nbsp;<html:button property="value(close)" styleClass="submit"  onclick="MKEmptyPopup('SC');disablePopupASSFNL();" value="Close"  /></td></tr>
<tr><td valign="top" height="95%">
<iframe name="SC" class="frameStyle"   align="middle" frameborder="0">
</iframe></td></tr>
</table>
</div>
<div id="backgroundPopup"></div>

 
<table width="100%"  border="0" align="center" cellpadding="0" cellspacing="0" class="mainbg">
<% if(!menu.equals("no")){ %>
   <tr>
    <td height="103" valign="top">
   <jsp:include page="/header.jsp" />
    
    </td>
  </tr>
  <tr>
    <td height="5" valign="top" class="greyline"><img src="../images/grey_linebig.png" width="685" height="5" align="right" /></td>
  </tr>
  <% } %>
   <tr>
  <td valign="top" class="hedPg">
  <table cellpadding="1" cellspacing="5"><tr><td valign="middle">
  <img src="../images/line.jpg" border="0" /></td><td valign="middle"> <span class="pgHed">Custom Screen Report</span> </td>
</tr></table>
  </td>
  </tr>
<%HashMap customdataTbl = (request.getAttribute("customdataTbl") == null)?new HashMap():(HashMap)request.getAttribute("customdataTbl");
ArrayList gridList = (customdataTbl.get("GRID1") == null)?new ArrayList():(ArrayList)customdataTbl.get("GRID1");
if(gridList.size()>0){
HashMap gridTtl = (HashMap)customdataTbl.get("GRID1TTL");
String ttlCts = util.nvl((String)gridTtl.get("GRID1CTS"),"0");
%>
<tr>
<td class="hedPg">
<table class="grid1">
<tr>
<td><table class="grid1">
<tr><th colspan="6">Stock Wise Summary</th></tr>
<tr>
<th>Type</th><th>Explanation</th><th>Pcs</th><th>Carats</th><th>Value</th><th>Per%</th>
</tr>
<%
String url1=info.getReqUrl()+"/report/orclRptAction.do?method=loadPktCustomScreen&grid=GRID1&stt=ALL";

%>
<tr>
<td><b>Total</b></td><td><b>FULL STOCK IN WORLD</b></td>
<td><b><a href="<%=url1%>" target="_blank" > <%=gridTtl.get("GRID1QTY")%></a></b></td>
<td><b><%=gridTtl.get("GRID1CTS")%></b></td><td><b><%=gridTtl.get("GRID1VLU")%></b></td><td><b>100%</b></td>
</tr>
<%for(int i=0;i<gridList.size();i++){
HashMap dtlMap=(HashMap)gridList.get(i);
if(dtlMap!=null){
String GRP = util.nvl((String)dtlMap.get("GRP"));
String lab = util.nvl((String)dtlMap.get("LAB"));
 String url=info.getReqUrl()+"/report/orclRptAction.do?method=loadPktCustomScreen&grid=GRID1&lab="+lab+"&stt="+GRP;
String type = util.nvl((String)dtlMap.get("FLDTTL"));
type=type.replace("LABNME", lab);
String cts=util.nvl((String)dtlMap.get("CTS"));

String dsc = util.nvl((String)dtlMap.get("DSC"));
dsc=dsc.replace("LABNME", lab);
String per="0";
if(!ttlCts.equals("0") && !cts.equals("0"))
per=String.valueOf((util.roundToDecimals(((Double.parseDouble(cts)/Double.parseDouble(ttlCts))*100),2)));    
          
%>
<tr>
<td><%=type%></td>
<td><%=dsc%></td>
<td align="right">
<A href="<%=url%>" target="_blank">
<%=util.nvl((String)dtlMap.get("QTY"))%></a>
</td>
<td align="right"><%=cts%></td>
<td align="right"><%=util.nvl((String)dtlMap.get("VLU"))%></td>
<td align="right"><%=per%>%</td>
</tr>
<%}}%>
</table>
</td>
<%
gridList = (customdataTbl.get("GRIDAPSD") == null)?new ArrayList():(ArrayList)customdataTbl.get("GRIDAPSD");
String gridApsd_ttlCts = (String)customdataTbl.get("GRIDAPSDCTS");
String gridApsd_ttlQty=(String)customdataTbl.get("GRIDAPSDQTY");
String gridApsd_ttlvlu=(String)customdataTbl.get("GRIDAPSDVLU");
int gridApsd_per = 0;
%>
<td valign="top"><table class="grid1">
<tr><th colspan="5">Stock Wise Summary(SOLD)</th></tr>
<tr>
<th>Process</th><th>Qty</th><th>Cts</th><th>Vlu</th><th>Per%</th>
</tr>
<tr>
<td><b>Total</b></td><td><b><%=gridApsd_ttlQty%></b></td><td><b><%=gridApsd_ttlCts%></b></td><td><b><%=gridApsd_ttlvlu%></b></td><td align="right"><b><%=gridApsd_per%></b></td>
</tr>
<%for(int i=0;i<gridList.size();i++){
HashMap dtlMap=(HashMap)gridList.get(i);
if(dtlMap!=null){
 String url=info.getReqUrl()+"/report/orclRptAction.do?method=loadPktCustomScreen&grid=GRIDAPSD&lab="+util.nvl((String)dtlMap.get("LAB"))+"&stt="+util.nvl((String)dtlMap.get("STT"))+"&process="+util.nvl((String)dtlMap.get("PROCESS"));
%>
<tr>
<td align="center"><A href="<%=url%>" target="_blank"><%=util.nvl((String)dtlMap.get("PROCESS"))%></a></td>
<td align="right">
<A href="<%=url%>" target="_blank">
<%=util.nvl((String)dtlMap.get("QTY"))%></a>
</td>
<td align="right"><%=util.nvl((String)dtlMap.get("CTS"))%></td>
<td align="right"><%=util.nvl((String)dtlMap.get("VLU"))%></td>
<td align="right"><%=util.nvl((String)dtlMap.get("PER"))%></td>
</tr>
<%}}%>
</table>
<%
gridList = (customdataTbl.get("GRID6") == null)?new ArrayList():(ArrayList)customdataTbl.get("GRID6");
String grid6ttlCts = (String)customdataTbl.get("GRID6CTS");
String grid6ttlQty = (String)customdataTbl.get("GRID6QTY");
String grid6ttlRate =(String) customdataTbl.get("GRID6RTE");
String grid6per = "0";
%>
<table class="grid1" style="margin: 5px 0 0 0">
<tr><th colspan="5">Stock Wise Summary(Fancy + Dossier)</th></tr>
<tr>
<th>Type</th><th>Qty</th><th>Cts</th><th>Vlu</th><th>Per%</th>
</tr>
<tr>
<td><b>Total</b></td><td><b><%=grid6ttlQty%></b></td><td><b><%=grid6ttlCts%></b></td><td><b><%=grid6ttlRate%></b></td><td align="right"><b><%=grid6per%></b></td>
</tr>
<%for(int i=0;i<gridList.size();i++){
HashMap dtlMap=(HashMap)gridList.get(i);
String typ=util.nvl((String)dtlMap.get("Type"));
if(dtlMap!=null){
String url=info.getReqUrl()+"/report/orclRptAction.do?method=loadPktCustomScreen&grid=GRID6&stt="+typ;
%>
<tr>
<td ><%=typ%></td>
<td align="right">
<A href="<%=url%>" target="_blank">
<%=util.nvl((String)dtlMap.get("QTY"))%></a>
</td>
<td align="right"><%=util.nvl((String)dtlMap.get("CTS"))%></td>
<td align="right"><%=util.nvl((String)dtlMap.get("RTE"))%></td>
<td align="right">0</td>
</tr>
<% }} %>
</table>
</td>



<%
gridList = (customdataTbl.get("GRID2") == null)?new ArrayList():(ArrayList)customdataTbl.get("GRID2");
String grid2ttlCts = (String)customdataTbl.get("GRID2CTS");
String grid2ttlQty = (String)customdataTbl.get("GRID2QTY");
String grid2ttlRate =(String) customdataTbl.get("GRID2VLU");
String grid2per = "0";
%>
<td valign="top"><table class="grid1">
<tr><th colspan="4">Stock Status</th></tr>
<tr>
<th>Process</th><th>Qty</th><th>Cts</th><th>Vlu</th>
</tr>

<tr>
<td align="center"><b>Total</b></td><td><b><%=grid2ttlQty%></b></td><td><b><%=grid2ttlCts%></b></td><td><b><%=grid2ttlRate%></b></td>
</tr>
<%for(int i=0;i<gridList.size();i++){
HashMap dtlMap=(HashMap)gridList.get(i);
if(dtlMap!=null){
String url=info.getReqUrl()+"/report/orclRptAction.do?method=loadPktCustomScreen&grid=GRID2&lab="+util.nvl((String)dtlMap.get("LAB"))+"&stt="+util.nvl((String)dtlMap.get("STT"))+"&process="+util.nvl((String)dtlMap.get("PROCESS"));
%>
<tr>
<td align="center"><%=util.nvl((String)dtlMap.get("PROCESS"))%></td>
<td align="right">
<A href="<%=url%>" target="_blank">
<%=util.nvl((String)dtlMap.get("QTY"))%></a>
</td>
<td align="right"><%=util.nvl((String)dtlMap.get("CTS"))%></td>
<td align="right"><%=util.nvl((String)dtlMap.get("VLU"))%></td>
</tr>
<%}}%>
</table>
</td>
</tr>
</table>
</td>
</tr>
<%gridList = (customdataTbl.get("GRID3") == null)?new ArrayList():(ArrayList)customdataTbl.get("GRID3");
String grid3ttlCts = (String)customdataTbl.get("GRID3CTS");
String grid3ttlQty = (String)customdataTbl.get("GRID3QTY");
String grid3ttlRate =(String) customdataTbl.get("GRID3VLU");
String grid3per = "0";
%>
<tr>
<td class="hedPg">
<table>
<tr>
<td class="hedPg">
<table class="grid1">
<tr><th colspan="5">Location Wise Summary</th></tr>
<tr>
<th>Process</th><th>Qty</th><th>Cts</th><th>Vlu</th><th>Per%</th>
</tr>
<tr>
<td align="center"><b>Total</b></td><td><b><%=grid3ttlQty%></b></td><td><b><%=grid3ttlCts%></b></td><td><b><%=grid3ttlRate%></b></td><td align="right"><b><%=grid3per%></b></td>
</tr>
<%for(int i=0;i<gridList.size();i++){
HashMap dtlMap=(HashMap)gridList.get(i);
if(dtlMap!=null){
String url=info.getReqUrl()+"/report/orclRptAction.do?method=loadPktCustomScreen&grid=GRID3&lab="+util.nvl((String)dtlMap.get("LAB"))+"&stt="+util.nvl((String)dtlMap.get("STT"))+"&process="+util.nvl((String)dtlMap.get("PROCESS"));
%>
<tr>
<td align="center"><%=util.nvl((String)dtlMap.get("PROCESS"))%></td>
<td align="right">
<A href="<%=url%>" target="_blank">
<%=util.nvl((String)dtlMap.get("QTY"))%></a>
</td>
<td align="right"><%=util.nvl((String)dtlMap.get("CTS"))%></td>
<td align="right"><%=util.nvl((String)dtlMap.get("VLU"))%></td>
<td align="right"><%=util.nvl((String)dtlMap.get("PER"))%></td>
</tr>
<%}}%>
</table>
</td>
</tr>
</table>
</td>
</tr>

<tr>
<td class="hedPg">
<table>
<tr>
<%gridList = (customdataTbl.get("GRID4") == null)?new ArrayList():(ArrayList)customdataTbl.get("GRID4");
String grid4ttlCts = (String)customdataTbl.get("GRID4CTS");
String grid4ttlQty = (String)customdataTbl.get("GRID4QTY");
String grid4ttlRate =(String) customdataTbl.get("GRID4VLU");
String grid4per = "0";
%>
<td class="hedPg" valign="top">
<table class="grid1">
<tr><th colspan="5">Stock Holding-Today</th></tr>
<tr>
<th>Process</th><th>Qty</th><th>Cts</th><th>Vlu</th>
</tr>
<tr>
<td align="center"><b>Total</b></td><td><b><%=grid4ttlQty%></b></td><td><b><%=grid4ttlCts%></b></td><td><b><%=grid4ttlRate%></b></td>
</tr>
<%for(int i=0;i<gridList.size();i++){
HashMap dtlMap=(HashMap)gridList.get(i);
if(dtlMap!=null){
String url=info.getReqUrl()+"/report/orclRptAction.do?method=loadPktCustomScreen&grid=GRID4&lab="+util.nvl((String)dtlMap.get("LAB"))+"&stt="+util.nvl((String)dtlMap.get("STT"))+"&process="+util.nvl((String)dtlMap.get("PROCESS"));
%>
<tr>
<td align="center"><%=util.nvl((String)dtlMap.get("PROCESS"))%></td>
<td align="right">
<A href="<%=url%>" target="_blank">
<%=util.nvl((String)dtlMap.get("QTY"))%></a>
</td>
<td align="right"><%=util.nvl((String)dtlMap.get("CTS"))%></td>
<td align="right"><%=util.nvl((String)dtlMap.get("VLU"))%></td>
</tr>
<%}}%>
</table>
</td>
<%gridList = (customdataTbl.get("GRID5") == null)?new ArrayList():(ArrayList)customdataTbl.get("GRID5");
String grid5ttlCts = (String)customdataTbl.get("GRID5CTS");
String grid5ttlQty = (String)customdataTbl.get("GRID5QTY");
String grid5ttlRate =(String) customdataTbl.get("GRID5VLU");
String grid5per = "0";
%>
<td class="hedPg" valign="top">
<table class="grid1">
<tr><th colspan="5">Stock Status-Today</th></tr>
<tr>
<th>Process</th><th>Qty</th><th>Cts</th><th>Vlu</th>
</tr>
<tr>
<td align="center"><b>Total</b></td><td><b><%=grid5ttlQty%></b></td><td><b><%=grid5ttlCts%></b></td><td><b><%=grid5ttlRate%></b></td>
</tr>
<%for(int i=0;i<gridList.size();i++){
HashMap dtlMap=(HashMap)gridList.get(i);
if(dtlMap!=null){
String url=info.getReqUrl()+"/report/orclRptAction.do?method=loadPktCustomScreen&grid=GRID5&lab="+util.nvl((String)dtlMap.get("LAB"))+"&stt="+util.nvl((String)dtlMap.get("STT"))+"&process="+util.nvl((String)dtlMap.get("PROCESS"));
%>
<tr>
<td align="center"><%=util.nvl((String)dtlMap.get("PROCESS"))%></td>
<td align="right">
<A href="<%=url%>" target="_blank">
<%=util.nvl((String)dtlMap.get("QTY"))%></a>
</td>
<td align="right"><%=util.nvl((String)dtlMap.get("CTS"))%></td>
<td align="right"><%=util.nvl((String)dtlMap.get("VLU"))%></td>
</tr>
<%}}%>
</table>
</td>
</tr>
</table>
</td>
</tr>
<%}else{%>
<tr><td valign="top" class="hedPg">Sorry No Result Found</td></tr>
<%}%>
  <tr><td>
<jsp:include page="/footer.jsp" />
</td></tr>
  </table>  
       <%@include file="../calendar.jsp"%>
  </body>
</html>