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
  
  <title>Pending  Summary Reports</title>
  <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/bse.js?v=<%=info.getJsVersion()%> " > </script>
  <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/bse.css?v=<%=info.getJsVersion()%>"/>
  <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/ajax.js?v=<%=info.getJsVersion()%> " > </script>
  <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/style.css?v=<%=info.getJsVersion()%>"/>
  <script language="javaScript" type="text/javascript" src="<%=info.getReqUrl()%>/calendar/calendar.js"></script>
   <link href="<%=info.getReqUrl()%>/calendar/calendar.css" rel="stylesheet" type="text/css" />
  </head>
  
        <%String logId=String.valueOf(info.getLogId());
        String onfocus="cook('"+logId+"')";
        %>
 <body onfocus="<%=onfocus%>"onload="MM_preloadImages('images/faunalogo.png')" topmargin="0" leftmargin="0" rightmargin="0" bottommargin="0">
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
  <bean:write property="reportNme" name="orclReportForm" />
   </span> </td>
</tr></table>
  </td>
  </tr>
  <%
  ArrayList prpDspBlocked = info.getPageblockList();
  ArrayList repMemoLst =(ArrayList)session.getAttribute("dlvlocPrpList");
  ArrayList  byridnList = (ArrayList)request.getAttribute("byridnList");
  HashMap  byrDtl = (HashMap)request.getAttribute("byrDtl");
  HashMap  dataDtl = (HashMap)request.getAttribute("dataDtl");
  String  view = (String)request.getAttribute("view");
  String dfUsrtyp=util.nvl((String)info.getDfUsrTyp());
  if(view.equals("Y")){
  int colspan=repMemoLst.size()+2;%>
  <tr>
  <td valign="top" class="hedPg">
  <html:form action="report/orclRptAction.do?method=fetch" method="post">
  <html:hidden property="value(PKT_TYP)" name="orclReportForm" />

  <table>
  <%if(!dfUsrtyp.equals("EMPLOYEE")){%>
 <tr>
   <td> Employee : </td>
   <td>
   <html:select property="value(empIdn)" styleId="empIdn" name="orclReportForm"   >
           <html:option value="0" >--select--</html:option>
            <html:optionsCollection property="empList" name="orclReportForm" 
            label="byrNme" value="byrIdn" />
   </html:select>
   </td>
<td> <html:submit property="value(submit)" styleClass="submit" value="Submit" /> 
<%
  ArrayList rolenmLst=(ArrayList)info.getRoleLst();
  String usrFlg=util.nvl((String)info.getUsrFlg());
  if(usrFlg.equals("SYS") || rolenmLst.contains("ADMIN")){
  %>
  &nbsp;&nbsp;  <img src="../images/edit.jpg" title="Edit Properties Module" onclick="newWindow('<%=info.getReqUrl()%>/dynamicProperty.do?method=load&mdl=DLV_LOC&sname=dlvlocPrpList&par=A')" border="0" width="15px" height="15px"/>
  
  <%}%>
</td> </tr>
  <%}%>
  </table>
  </html:form>
  </td>
  </tr>
   <tr><td valign="top" class="hedPg">   Create Excel &nbsp;&nbsp;&nbsp;
  <img src="../images/ico_file_excel.png" border="0" onclick="goTo('<%=info.getReqUrl()%>/report/genericReport.do?method=createXL','','')"/>
 </td></tr>
  <%if(byridnList!=null && byridnList.size()>0){
  ArrayList itemHdr = new ArrayList();
  ArrayList pktExcelList = new ArrayList();
  itemHdr.add("Sale Ex");
  itemHdr.add("Buyer");
  itemHdr.add("Id");
  itemHdr.add("Date");
  itemHdr.add("Days");
  itemHdr.add("Packet Id");
  itemHdr.add("Qty");
  itemHdr.add("Cts");
  %>
  <tr>
  <td valign="top" class="hedPg">
  <table class="dataTable">
  <tr>
  <th>Sale Ex</th>
  <th>Buyer</th><th>Id</th><th>Date</th><th>Days</th><th>Packet Id</th><th>Qty</th><th>Cts</th>
   <% for (int h = 0; h < repMemoLst.size(); h++) {
     String prp = (String)repMemoLst.get(h);
     if(prpDspBlocked.contains(prp)){
     colspan=colspan-1;
    }else{
     itemHdr.add(prp);
    %>
  <th title="<%=prp%>"><%=prp%></th>
  <%}}
   itemHdr.add("Sale Rte"); itemHdr.add("Sale Dis"); itemHdr.add("Value");
  
  %>
  <th>Sale Rte</th>
  <th>Sale Dis</th>
  <th>Value</th>
  </tr>
  <%for (int i = 0; i< byridnList.size(); i++) {
  
  String byr_idn=(String)byridnList.get(i);
  ArrayList pktList=(ArrayList)dataDtl.get(byr_idn);
  %>
  <%for (int j = 0; j< pktList.size(); j++) {
  HashMap excelMap = new HashMap();
  HashMap pktPrpMap = new HashMap();
  pktPrpMap=(HashMap)pktList.get(j);
  if(j==0){
  excelMap.put("Sale Ex", util.nvl((String)byrDtl.get(byr_idn+"_EMP")));
  excelMap.put("Buyer", util.nvl((String)byrDtl.get(byr_idn)));
  %>
  <tr>
  <td><%=util.nvl((String)byrDtl.get(byr_idn+"_EMP"))%></td>
  <td><%=util.nvl((String)byrDtl.get(byr_idn))%></td>
  <%}else{
  excelMap.put("Sale Ex","");
  excelMap.put("Buyer","");
  %>
  <tr><td></td><td></td>
  <%}
  excelMap.put("Id",util.nvl((String)pktPrpMap.get("dlvIdn")));
  excelMap.put("Date",util.nvl((String)pktPrpMap.get("dte")));
  excelMap.put("Days",util.nvl((String)pktPrpMap.get("days")));
  excelMap.put("Packet Id",util.nvl((String)pktPrpMap.get("vnm")));
  excelMap.put("Qty",util.nvl((String)pktPrpMap.get("qty")));
  excelMap.put("Cts",util.nvl((String)pktPrpMap.get("cts")));
  %>
  <td><%=util.nvl((String)pktPrpMap.get("dlvIdn"))%></td>
    <td><%=util.nvl((String)pktPrpMap.get("dte"))%></td>
    <td><%=util.nvl((String)pktPrpMap.get("days"))%></td>
  <td><%=util.nvl((String)pktPrpMap.get("vnm"))%></td>
  <td align="right"><%=util.nvl((String)pktPrpMap.get("qty"))%></td>
  <td align="right"><%=util.nvl((String)pktPrpMap.get("cts"))%></td>
  <% for (int k = 0; k < repMemoLst.size(); k++) {
     String prp = (String)repMemoLst.get(k);
     if(prpDspBlocked.contains(prp)){
    }else{
    excelMap.put(prp,util.nvl((String)pktPrpMap.get(prp)));
    %>
  <td><%=util.nvl((String)pktPrpMap.get(prp))%></td>
  <%}}
  String color=util.nvl((String)pktPrpMap.get("color"));
   excelMap.put("Sale Rte",util.nvl((String)pktPrpMap.get("SALERTE")));
    excelMap.put("Sale Dis",util.nvl((String)pktPrpMap.get("SALEDIS")));
     excelMap.put("Value",util.nvl((String)pktPrpMap.get("vlu")));
  %>
    <td align="right"><%=util.nvl((String)pktPrpMap.get("SALERTE"))%></td>
    <td align="right"><%=util.nvl((String)pktPrpMap.get("SALEDIS"))%></td>
  <td align="right" style="background-color:<%=color%>"><%=util.nvl((String)pktPrpMap.get("vlu"))%></td>
  <%
  pktExcelList.add(excelMap);  }%>
  </tr>
  <%
  HashMap ttl=(HashMap)dataDtl.get(byr_idn+"_TTL"); 
  HashMap excelMap = new HashMap();
  excelMap.put("Sale Ex", "");
  excelMap.put("Buyer", "");
  excelMap.put("Id","");
  excelMap.put("Date","");
  excelMap.put("Days","");
  excelMap.put("Packet Id","");
  excelMap.put("Qty",util.nvl((String)ttl.get("qty")));
  excelMap.put("Cts",util.nvl((String)ttl.get("cts")));
   for (int k = 0; k < repMemoLst.size(); k++) {
     String prp = (String)repMemoLst.get(k);
     if(prpDspBlocked.contains(prp)){
    }else{
    excelMap.put(prp,"");
    }}
    excelMap.put("Sale Rte","");
    excelMap.put("Sale Dis","");
     excelMap.put("Value",util.nvl((String)ttl.get("vlu")));
     pktExcelList.add(excelMap);
  %>
  <tr>
  <td colspan="6"></td>
  <td align="right"><b><%=util.nvl((String)ttl.get("qty"))%></b></td>
  <td align="right"><b><%=util.nvl((String)ttl.get("cts"))%></b></td>
  <td colspan="<%=colspan%>"></td>
    <td align="right"><b><%=util.nvl((String)ttl.get("vlu"))%></b></td>
  </tr>
  <%}%>
  <%
  HashMap grandttl=(HashMap)dataDtl.get("GRANDTTL");
   HashMap excelMap = new HashMap();
  excelMap.put("Sale Ex", "Grand Total");
  excelMap.put("Buyer", "");
  excelMap.put("Id","");
  excelMap.put("Date","");
  excelMap.put("Days","");
  excelMap.put("Packet Id","");
  excelMap.put("Qty",util.nvl((String)grandttl.get("qty")));
  excelMap.put("Cts",util.nvl((String)grandttl.get("cts")));
   for (int k = 0; k < repMemoLst.size(); k++) {
     String prp = (String)repMemoLst.get(k);
     if(prpDspBlocked.contains(prp)){
    }else{
    excelMap.put(prp,"");
    }}
    excelMap.put("Sale Rte","");
    excelMap.put("Sale Dis","");
     excelMap.put("Value",util.nvl((String)grandttl.get("vlu")));
     pktExcelList.add(excelMap);
  %>
  <tr>
  <tr>
  <td><b>Grand Total</b></td>
  <td colspan="5"></td>
  <td align="right"><b><%=util.nvl((String)grandttl.get("qty"))%></b></td>
  <td align="right"><b><%=util.nvl((String)grandttl.get("cts"))%></b></td>r
  <td colspan="<%=colspan%>"></td>
    <td align="right"><b><%=util.nvl((String)grandttl.get("vlu"))%></b></td>
  </tr>
  
  </table>
  </td>
  </tr>
  <%
  session.setAttribute("itemHdr",itemHdr);
  session.setAttribute("pktList",pktExcelList);
  }else{%>
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
  
  