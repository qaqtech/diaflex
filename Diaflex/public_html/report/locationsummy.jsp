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
 <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
  
  <title>Location Pending Report</title>
  <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/bse.js?v=<%=info.getJsVersion()%> " > </script>
  <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/bse.css?v=<%=info.getJsVersion()%>"/>
  <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/ajax.js?v=<%=info.getJsVersion()%> " > </script>
  <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/style.css?v=<%=info.getJsVersion()%>"/>
  <script language="javaScript" type="text/javascript" src="<%=info.getReqUrl()%>/calendar/calendar.js"></script>
   <link href="<%=info.getReqUrl()%>/calendar/calendar.css" rel="stylesheet" type="text/css" />
  </head>
  
  <%
   ArrayList repMemoLst =(ArrayList)session.getAttribute("dlvlocPrpList");
        String logId=String.valueOf(info.getLogId());
        String onfocus="cook('"+logId+"')";
        String dfUsrtyp=util.nvl((String)info.getDfUsrTyp());
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
  <img src="../images/line.jpg" border="0" /></td><td valign="middle"> <span class="pgHed">
  Location Pending Report
   </span> </td>
</tr></table>
  </td>
  </tr>
  <tr>
  <td valign="top" class="hedPg">
  <html:form action="report/orclRptAction.do?method=fetchloc" method="post">

  <table>
 <tr>
   <%if(!dfUsrtyp.equals("EMPLOYEE")){%>
   <td> Employee : </td>
   <td>
   <html:select property="value(empIdn)" styleId="empIdn" name="orclReportForm"   >
           <html:option value="0" >--select--</html:option>
            <html:optionsCollection property="empList" name="orclReportForm" 
            label="byrNme" value="byrIdn" />
   </html:select>
   </td>
   <%}%>
<td>Buyer</td>
<td>
 <html:select property="byrIdn" name="orclReportForm" styleId="byrId" >
    <html:option value="0">---select---</html:option>
    <html:optionsCollection property="byrList" name="orclReportForm"  value="byrIdn" label="byrNme" />
                
  </html:select>
               
</td>
<td> <html:submit property="value(submit)" styleClass="submit" value="Submit" /> 
<%
  ArrayList rolenmLst=(ArrayList)info.getRoleLst();
  String usrFlg=util.nvl((String)info.getUsrFlg());
  if(usrFlg.equals("SYS") || rolenmLst.contains("ADMIN")){
  %>
  &nbsp;&nbsp;<img src="../images/edit.jpg" title="Edit Properties Module" onclick="newWindow('<%=info.getReqUrl()%>/dynamicProperty.do?method=load&mdl=DLV_LOC&sname=dlvlocPrpList&par=A')" border="0" width="15px" height="15px"/>
  
  <%}%>
</td> </tr>
  </table>
  </html:form>
  </td>
  </tr>
  <%
  ArrayList prpDspBlocked = info.getPageblockList();
  ArrayList  byridnList = (ArrayList)request.getAttribute("byridnList");
  HashMap  byrDtl = (HashMap)request.getAttribute("byrDtl");
  HashMap  dataDtl = (HashMap)request.getAttribute("dataDtl");
  String  view = (String)request.getAttribute("view");
  if(view.equals("Y")){
  int colspan=repMemoLst.size()+2;
  if(byridnList!=null && byridnList.size()>0){
  %>
  <tr>
  <td valign="top" class="hedPg">
  <table class="dataTable">
  <tr>
  <th>Sale Ex</th>
  <th>Buyer</th><th>Delivery Date</th><th>Days</th><th>Delivery Id</th><th>Packet Id</th><th>Qty</th><th>Cts</th>
   <% for (int h = 0; h < repMemoLst.size(); h++) {
     String prp = (String)repMemoLst.get(h);
    if(prpDspBlocked.contains(prp)){
     colspan=colspan-1;
    }else{%>
  <th title="<%=prp%>"><%=prp%></th>
  <%}}%>
  <th>Sale Rte</th>
  <th>Sale Dis</th>
  <th>Value</th>
  </tr>
  <%for (int i = 0; i< byridnList.size(); i++) {
  String byr_idn=(String)byridnList.get(i);
  ArrayList pktList=(ArrayList)dataDtl.get(byr_idn);
  %>
  <%for (int j = 0; j< pktList.size(); j++) {
  HashMap pktPrpMap = new HashMap();
  pktPrpMap=(HashMap)pktList.get(j);
  if(j==0){
  %>
  <tr>
  <td><%=util.nvl((String)byrDtl.get(byr_idn+"_EMP"))%></td>
  <td><%=util.nvl((String)byrDtl.get(byr_idn))%></td>
  <%}else{%>
  <tr><td></td><td></td>
  <%}%>
  <td><%=util.nvl((String)pktPrpMap.get("dte"))%></td>
  <td><%=util.nvl((String)pktPrpMap.get("days"))%></td>
  <td><%=util.nvl((String)pktPrpMap.get("dlvIdn"))%></td>
  <td><%=util.nvl((String)pktPrpMap.get("vnm"))%></td>
  <td align="right"><%=util.nvl((String)pktPrpMap.get("qty"))%></td>
  <td align="right"><%=util.nvl((String)pktPrpMap.get("cts"))%></td>
  <% for (int k = 0; k < repMemoLst.size(); k++) {
     String prp = (String)repMemoLst.get(k);
    if(prpDspBlocked.contains(prp)){
    }else{
    %>
  <td><%=util.nvl((String)pktPrpMap.get(prp))%></td>
  <%}}%>
      <td align="right"><%=util.nvl((String)pktPrpMap.get("SALERTE"))%></td>
    <td align="right"><%=util.nvl((String)pktPrpMap.get("SALEDIS"))%></td>
  <td align="right"><%=util.nvl((String)pktPrpMap.get("vlu"))%></td>
  <%}%>
  </tr>
  <%
  HashMap ttl=(HashMap)dataDtl.get(byr_idn+"_TTL");%>
  <tr>
  <td colspan="6"></td>
  <td align="right"><b><%=util.nvl((String)ttl.get("qty"))%></b></td>
  <td align="right"><b><%=util.nvl((String)ttl.get("cts"))%></b></td>
  <td colspan="<%=colspan%>"></td>
    <td align="right"><b><%=util.nvl((String)ttl.get("vlu"))%></b></td>
  </tr>
  <%}%>
  <%
  HashMap grandttl=(HashMap)dataDtl.get("GRANDTTL");%>
  <tr>
  <tr>
  <td><b>Grand Total</b></td>
  <td colspan="5"></td>
  <td align="right"><b><%=util.nvl((String)grandttl.get("qty"))%></b></td>
  <td align="right"><b><%=util.nvl((String)grandttl.get("cts"))%></b></td>
  <td colspan="<%=colspan%>"></td>
    <td align="right"><b><%=util.nvl((String)grandttl.get("vlu"))%></b></td>
  </tr>
  
  </table>
  </td>
  </tr>
  <%}else{%>
  <tr><td valign="top" class="hedPg">
Sorry No Result Found</td></tr>
  <%}}%>
  <tr>
    <td><jsp:include page="/footer.jsp" /> </td>
    </tr>
  </table>
   <%@include file="../calendar.jsp"%>
  </body>
</html>
  
  