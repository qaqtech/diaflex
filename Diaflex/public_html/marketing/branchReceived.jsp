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
    <title>Branch Receive</title>
    <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/bse.css?v=<%=info.getJsVersion()%>"/>
    <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/style.css?v=<%=info.getJsVersion()%>"/>
    <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/memoScript.js?v=<%=info.getJsVersion()%> " > </script>
    <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/bse.js?v=<%=info.getJsVersion()%> " > </script>
     <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/ajax.js?v=<%=info.getJsVersion()%> " > </script>
   <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/assortScript.js?v=<%=info.getJsVersion()%> " > </script>
    <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/Validation.js " > </script>
    <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/priceChange.js?v=<%=info.getJsVersion()%> " > </script>
  </head>
<%
HashMap dbinfo = info.getDmbsInfoLst();
String cnt = (String)dbinfo.get("CNT");
        String logId=String.valueOf(info.getLogId());
        String onfocus="cook('"+logId+"')";
    info.setBrnchDlvList(new ArrayList());
    info.setBrnchStkIdnLst(new ArrayList());
    ArrayList vwprpLst = (info.getValue("MDL_MEMO_RTRN") == null)?new ArrayList():(ArrayList)info.getValue("MDL_MEMO_RTRN");
      String pkt_ty = util.nvl(request.getParameter("PKT_TY"));   
          ArrayList prpDspBlocked = info.getPageblockList();

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
  <img src="../images/line.jpg" border="0" /></td><td valign="middle"> <span class="pgHed">Branch Receive</span> </td>
</tr></table></td></tr>
 <% if(request.getAttribute("msg")!=null){%>
<tr><td valign="top" class="tdLayout">
<span class="redLabel"><%=request.getAttribute("msg")%></span>
</td></tr>
<%}%>
<tr><td valign="top" class="tdLayout">
<html:form action="/marketing/branceReceive.do?method=fetch" method="post">
<input type="hidden" name="PKT_TY" id="PKT_TY" value="<%=pkt_ty%>"/>
<table>
<tr><td> Packet Id</td><td><html:textarea property="value(vnm)" name="branchReceivedForm" /> </td> </tr>
<tr><td colspan="2" align="center"> <html:submit property="value(submit)" value="Fetch" styleClass="submit" /> </td></tr>
</table>
</html:form>
</td></tr>
<tr><td valign="top" class="tdLayout">

<%
ArrayList DlvIdnLst = (ArrayList)request.getAttribute("DlvIdnLst");
HashMap BrchDlvDataMap = (HashMap)request.getAttribute("BrchDlvDataMap");
if(DlvIdnLst!=null && DlvIdnLst.size()>0){
%>
<html:form action="/marketing/branceReceive.do?method=save" method="post" onsubmit="return validate_SelectAll('1','RC_')" >
<input type="hidden" name="PKT_TY" id="PKT_TY" value="<%=pkt_ty%>"/>
<table cellpadding="2" cellspacing="2"><tr><td>
<b>Select ALL</b> <input type="checkbox"  value="RC" id="RC" onclick="checkAllpage(this,'RC');BranchReceivedTotal('1','RC_')"/>&nbsp; <html:submit property="value(submit)" value="Recevie" styleClass="submit" /> Total :- Qty :<b> <%=BrchDlvDataMap.get("TTLQTY")%></b> &nbsp;&nbsp;Cts :<b> <%=util.roundToDecimals(Double.parseDouble((String)BrchDlvDataMap.get("TTLCTS")),2)%></b> &nbsp;&nbsp;Value:<b> <%=BrchDlvDataMap.get("TTLVLU")%></b> &nbsp;&nbsp;| Selected :- Qty : <b> <label id="ttlQty">0</label></b> &nbsp;&nbsp;Cts : <b><label id="ttlCts">0</label></b> &nbsp;&nbsp;Value : <b><label id="ttlVlu">0</label></b>&nbsp;&nbsp;&nbsp;Create Excel  <img src="../images/ico_file_excel.png" onclick="goTo('branceReceive.do?method=createXL','','')" border="0">   </td></tr>
<tr><td>
<table  class="grid1">
<%
for(int i=0;i<DlvIdnLst.size();i++){
String dlvIdn = (String)DlvIdnLst.get(i);
HashMap hdrDtl = (HashMap)BrchDlvDataMap.get(dlvIdn+"_HDR");
ArrayList dataDtl = (ArrayList)BrchDlvDataMap.get(dlvIdn+"_DTL");
String idn = (String)hdrDtl.get("IDN");
%>
<tr><td><input type="checkbox"  value="<%=dlvIdn%>" id="RC<%=dlvIdn%>" onclick="checkAllpage(this,'<%=dlvIdn%>');BranchReceivedTotal('1','RC_')"/> </td> <td colspan="13"><b>Dlv Idn : <%=dlvIdn%> &nbsp;&nbsp;BranchName : <%=hdrDtl.get("brnNme")%> &nbsp;&nbsp;Date : <%=hdrDtl.get("Dte")%></b> </td></tr>
<tr><th></th><th>Buyer</th><th>Sale Id</th><th>Pkt Id</th><th>Qty</th>

 <%for(int j=0; j < vwprpLst.size(); j++) {
     String lprp=(String)vwprpLst.get(j);
     if(!prpDspBlocked.contains(lprp)){
      %>
        <th><%=lprp%></th>
        <%}%>
<%}%>
<th>Final Sale</th><th>Sale Dis</th>
<%if(cnt.equalsIgnoreCase("NJ")){%>
<th>CP</th><th>CP DIS</th>
<%}%>
<th>Amount</th></tr>
<%for(int j=0;j<dataDtl.size();j++){
HashMap pktDtl = (HashMap)dataDtl.get(j);
String stk_idn = (String)pktDtl.get("mstkIdn");
String brc_idn = (String)pktDtl.get("brc_idn");
String chkId = "RC_"+dlvIdn+"_"+stk_idn;
String chkVlu= brc_idn+"_"+stk_idn;

%>
<tr><td><input type="checkbox" value="<%=chkVlu%>" onclick="BranchReceivedTotal('1','RC_')" id="<%=chkId%>" name="<%=chkId%>" /> </td>
<td><%=pktDtl.get("byr")%></td>
<td><%=pktDtl.get("sal_idn")%></td>
<td><%=pktDtl.get("vnm")%></td><td><%=pktDtl.get("qty")%><input type="hidden" id="qty_<%=chkVlu%>" value="<%=pktDtl.get("qty")%>" /> </td>
<%for(int x=0;  x< vwprpLst.size(); x++) {
     String lprp=(String)vwprpLst.get(x);
     if(!prpDspBlocked.contains(lprp)){
      %>
      <td><%=pktDtl.get(lprp)%>
      <%}%>
      <%if(lprp.equals("CRTWT")){%>
      <input type="hidden" id="cts_<%=chkVlu%>" value="<%=pktDtl.get("CRTWT")%>" />
      <%}%></td>
<%} %>

<td><%=pktDtl.get("FNLSAL")%></td>
<td><%=pktDtl.get("FNLSALDIS")%></td>
<%if(cnt.equalsIgnoreCase("NJ")){%>
<td><%=pktDtl.get("CPRTE")%></td><td><%=pktDtl.get("CPDIS")%></td>
<%}%>

<td><%=pktDtl.get("amt")%><input type="hidden" id="amt_<%=chkVlu%>" value="<%=pktDtl.get("amt")%>" /></td>

</tr>
<%}%>

<%}%></table>
</td></tr></table>

</html:form>
<%
ArrayList itemHdr =new ArrayList();
itemHdr.add("Buyer");
itemHdr.add("Sale Id");
itemHdr.add("Pkt Id");
itemHdr.add("Qty");
itemHdr.add("Shape");
itemHdr.add("Cts");
itemHdr.add("Color");
itemHdr.add("Clarity");
itemHdr.add("Final Sale");
itemHdr.add("Sale Dis");
itemHdr.add("Amount");
session.setAttribute("itemHdr",itemHdr);
session.setAttribute("DlvIdnLst",DlvIdnLst);
session.setAttribute("BrchDlvDataMap",BrchDlvDataMap);
}%>
</td></tr></table>
</body>
</html>