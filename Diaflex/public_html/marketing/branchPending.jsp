<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
 
<%@ page errorPage="../error_page.jsp" %>
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import="java.util.ArrayList,java.util.HashMap,java.sql.ResultSet,ft.com.*, java.util.Date, java.util.ArrayList, ft.com.dao.*"%>
<jsp:useBean id="util" class="ft.com.JspUtil" scope="page" />
<jsp:useBean id="info" class="ft.com.InfoMgr" scope="session" />
<%@taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>
<%@taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>

<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>Branch Pending</title>
    <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/bse.css?v=<%=info.getJsVersion()%>"/>
    <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/style.css?v=<%=info.getJsVersion()%>"/>
    <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/memoScript.js?v=<%=info.getJsVersion()%> " > </script>
    <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/bse.js?v=<%=info.getJsVersion()%> " > </script>
    <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/assortScript.js?v=<%=info.getJsVersion()%> " > </script>
    <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/Validation.js " > </script>
    <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/priceChange.js?v=<%=info.getJsVersion()%> " > </script>
  </head>
  <%
  HashMap dbinfo = info.getDmbsInfoLst();
String cnt = (String)dbinfo.get("CNT");
String logId=String.valueOf(info.getLogId());
String onfocus="cook('"+logId+"')";
  %>
   <body onfocus="<%=onfocus%>" >
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
  <img src="../images/line.jpg" border="0" /></td><td valign="middle"> <span class="pgHed">Branch Pending</span> </td>
</tr></table></td></tr>
<tr><td valign="top" class="tdLayout">
<html:form action="/marketing/branceReport.do?method=fetch" method="POST" >
<table>
<tr>
<td>Branch List</td>
<td> <html:select property="value(branchIdn)" name="branchDlvReportForm"  styleId="branchIdn" >
    <html:option value="0">---select---</html:option>
    <html:optionsCollection property="branchList" name="branchDlvReportForm"   value="byrIdn" label="byrNme" />
                
  </html:select></td>
<td>Buyer Name :</td>
<td>
 <html:select property="value(byrIdn)" name="branchDlvReportForm"  styleId="byrId" >
    <html:option value="0">---select---</html:option>
    <html:optionsCollection property="byrList" name="branchDlvReportForm"  value="byrIdn" label="byrNme" />
                
  </html:select>
</td><td><html:submit property="value(submit)" value="View" styleClass="submit"/></td>
</tr>
</table></html:form>
</td></tr>

 <%
 ArrayList itemHdr = new ArrayList();
  ArrayList pktDtlList = new ArrayList();
  ArrayList prpDspBlocked = info.getPageblockList();
  ArrayList  byridnList = (ArrayList)request.getAttribute("byridnList");
  HashMap  byrDtl = (HashMap)request.getAttribute("byrDtl");
  HashMap  dataDtl = (HashMap)request.getAttribute("dataDtl");
  String  view = (String)request.getAttribute("view");
     ArrayList repMemoLst =(ArrayList)session.getAttribute("dlvlocPrpList");
  if(view.equals("Y")){
  int colspan=repMemoLst.size()+2;
  if(byridnList!=null && byridnList.size()>0){
  itemHdr.add("Sale Ex");itemHdr.add("Buyer");
  itemHdr.add("Delivery Date");itemHdr.add("Days");
  itemHdr.add("Delivery Id");itemHdr.add("Sale Id");
  itemHdr.add("Packet Id");itemHdr.add("Payment");
  itemHdr.add("Qty");itemHdr.add("Cts");
  %>
<tr><td valign="top" class="hedPg">Create Excel <img src="../images/ico_file_excel.png" onclick="goTo('branceReport.do?method=createXL','','')" border="0"/> 
</td></tr>
  <tr>
  <td valign="top" class="hedPg">
  <table class="dataTable">
  <tr>
  <th>Sale Ex</th>
  <th>Buyer</th><th>Delivery Date</th><th>Days</th><th>Delivery Id</th><th>Sale Id</th><th>Packet Id</th><th>Payment</th><th>Qty</th><th>Cts</th>
   <% for (int h = 0; h < repMemoLst.size(); h++) {
     String prp = (String)repMemoLst.get(h);
     itemHdr.add(prp);
    if(prpDspBlocked.contains(prp)){
     colspan=colspan+1;
    }else{
    itemHdr.add(prp);
    %>
  <th title="<%=prp%>"><%=prp%></th>
  <%}}
    
  %>
  <th>Sale Rte</th>
  <th>Sale Dis</th>
  <th>Value</th>
  <th>Remark</th>
  </tr>
  <% itemHdr.add("Sale Rte");
    itemHdr.add("Sale Dis");itemHdr.add("Value");itemHdr.add("Remark");
   
  for (int i = 0; i< byridnList.size(); i++) {

  String byr_idn=(String)byridnList.get(i);
  ArrayList pktList=(ArrayList)dataDtl.get(byr_idn);
  %>
  <%for (int j = 0; j< pktList.size(); j++) {
    HashMap pktDtl = new HashMap();
  HashMap pktPrpMap = new HashMap();
  pktPrpMap=(HashMap)pktList.get(j);
  if(j==0){
  pktDtl.put("Sale Ex", util.nvl((String)byrDtl.get(byr_idn+"_EMP")));
   pktDtl.put("Buyer",util.nvl((String)byrDtl.get(byr_idn)));
  %>
  <tr>
  <td><%=util.nvl((String)byrDtl.get(byr_idn+"_EMP"))%></td>
  <td><%=util.nvl((String)byrDtl.get(byr_idn))%></td>
  <%}else{
   pktDtl.put("Sale Ex","");
    pktDtl.put("Buyer","");
  %>
 <tr> <td></td><td></td>
  <%}
    pktDtl.put("Delivery Date",util.nvl((String)pktPrpMap.get("dte")));
    pktDtl.put("Days",util.nvl((String)pktPrpMap.get("days")));
    pktDtl.put("Delivery Id",util.nvl((String)pktPrpMap.get("dlvIdn")));
    pktDtl.put("Sale Id",util.nvl((String)pktPrpMap.get("salIdn")));
    pktDtl.put("Packet Id",util.nvl((String)pktPrpMap.get("vnm")));
     pktDtl.put("Payment",util.nvl((String)pktPrpMap.get("payStt")));
    pktDtl.put("Qty",util.nvl((String)pktPrpMap.get("qty")));
    pktDtl.put("Cts",util.nvl((String)pktPrpMap.get("cts")));
  
  %>
  <td><%=util.nvl((String)pktPrpMap.get("dte"))%></td>
  <td><%=util.nvl((String)pktPrpMap.get("days"))%></td>
  <td><%=util.nvl((String)pktPrpMap.get("dlvIdn"))%></td>
    <td><%=util.nvl((String)pktPrpMap.get("salIdn"))%></td>
  <td><%=util.nvl((String)pktPrpMap.get("vnm"))%></td>
   <td><%=util.nvl((String)pktPrpMap.get("payStt"))%></td>
  <td align="right"><%=util.nvl((String)pktPrpMap.get("qty"))%></td>
  <td align="right"><%=util.nvl((String)pktPrpMap.get("cts"))%></td>
  <% for (int k = 0; k < repMemoLst.size(); k++) {
     String prp = (String)repMemoLst.get(k);
    if(prpDspBlocked.contains(prp)){
    }else{
    pktDtl.put(prp,util.nvl((String)pktPrpMap.get(prp)));
    %>
    
  <td><%=util.nvl((String)pktPrpMap.get(prp))%></td>
  <%}}%>
      <td align="right"><%=util.nvl((String)pktPrpMap.get("SALERTE"))%></td>
    <td align="right"><%=util.nvl((String)pktPrpMap.get("SALEDIS"))%></td>
  <td align="right"><%=util.nvl((String)pktPrpMap.get("vlu"))%></td>
  <td align="right"><%=util.nvl((String)pktPrpMap.get("rmk"))%></td>
  <%
  pktDtl.put("Sale Rte",util.nvl((String)pktPrpMap.get("SALERTE")));
  pktDtl.put("Sale Dis",util.nvl((String)pktPrpMap.get("SALEDIS")));
  pktDtl.put("Value",util.nvl((String)pktPrpMap.get("vlu")));
  pktDtl.put("Remark",util.nvl((String)pktPrpMap.get("rmk")));
   pktDtlList.add(pktDtl);
  }
 
  
  %>
  </tr>
  <%
   HashMap ttl=(HashMap)dataDtl.get(byr_idn+"_TTL");
    HashMap pktDtl = new HashMap();
 
    pktDtl.put("Sale Ex","");
    pktDtl.put("Buyer","");
    pktDtl.put("Delivery Date","");
    pktDtl.put("Days","");
    pktDtl.put("Delivery Id","");
    pktDtl.put("Sale Id","");
    pktDtl.put("Packet Id","");
     pktDtl.put("Payment","");
    pktDtl.put("Qty",util.nvl((String)ttl.get("qty")));
    pktDtl.put("Cts",util.nvl((String)ttl.get("cts")));
  %>
  <tr>
  <td colspan="8"></td>
  <td align="right"><b><%=util.nvl((String)ttl.get("qty"))%></b></td>
  <td align="right"><b><%=util.nvl((String)ttl.get("cts"))%></b></td>
  <td colspan="<%=colspan%>"></td>
  <%for (int x = 0; x < colspan-2; x++) {
   String prp = (String)repMemoLst.get(x);
   pktDtl.put(prp,"");
     }
      pktDtl.put("Sale Rte","");
      pktDtl.put("Sale Dis","");
      pktDtl.put("Value",util.nvl((String)ttl.get("vlu")));
       pktDtlList.add(pktDtl);
     %>
    <td align="right"><b><%=util.nvl((String)ttl.get("vlu"))%></b></td>
  </tr>
  <%}%>
  <%
  HashMap grandttl=(HashMap)dataDtl.get("GRANDTTL");
   HashMap pktDtl = new HashMap();
 
    pktDtl.put("Sale Ex","");
    pktDtl.put("Buyer","");
    pktDtl.put("Delivery Date","");
    pktDtl.put("Days","");
    pktDtl.put("Delivery Id","");
    pktDtl.put("Sale Id","");
    pktDtl.put("Packet Id","");
     pktDtl.put("Payment","");
    pktDtl.put("Qty",util.nvl((String)grandttl.get("qty")));
    pktDtl.put("Cts",util.nvl((String)grandttl.get("cts")));
  %>
  <tr>
  <tr>
  <td><b>Grand Total</b></td>
  <td colspan="7"></td>
  <td align="right"><b><%=util.nvl((String)grandttl.get("qty"))%></b></td>
  <td align="right"><b><%=util.nvl((String)grandttl.get("cts"))%></b></td>
  <td colspan="<%=colspan%>"></td>
   <%for (int x = 0; x < colspan-2; x++) {
   String prp = (String)repMemoLst.get(x);
   pktDtl.put(prp,"");
     }
      pktDtl.put("Sale Rte","");
      pktDtl.put("Sale Dis","");
      pktDtl.put("Value",util.nvl((String)grandttl.get("vlu")));
       pktDtlList.add(pktDtl);
     %>
    <td align="right"><b><%=util.nvl((String)grandttl.get("vlu"))%></b></td>
  </tr>
  
  </table>
  </td>
  </tr>
  <%
   session.setAttribute("itemHdr", itemHdr);
  session.setAttribute("pktList", pktDtlList);
  }else{%>
  <tr><td valign="top" class="hedPg">
Sorry No Result Found</td></tr>
  <%}}%>
  <tr>
    <td><jsp:include page="/footer.jsp" /> </td>
    </tr>

  </table>
  
  </body>
</html>