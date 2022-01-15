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
    <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/box.js?v=<%=info.getJsVersion()%> " > </script>
    <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/bse.css?v=<%=info.getJsVersion()%>"/>
      <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/style.css?v=<%=info.getJsVersion()%>"/>
       <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/general.css?v=<%=info.getJsVersion()%>"/>
       <script type="text/javascript" src="<%=info.getReqUrl()%>/PopScripts/jquery-1.2.6.min.js" > </script>
        <script type="text/javascript" src="<%=info.getReqUrl()%>/PopScripts/popup.js?v=<%=info.getJsVersion()%> " > </script>
       <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/bse.js?v=<%=info.getJsVersion()%> " > </script>
          <script language="javaScript" type="text/javascript" src="<%=info.getReqUrl()%>/calendar/calendar.js"></script>
   <link href="<%=info.getReqUrl()%>/calendar/calendar.css" rel="stylesheet" type="text/css" />
          <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/priceChange.js?v=<%=info.getJsVersion()%> " > </script>
           <script language="javaScript" type="text/javascript" src="<%=info.getReqUrl()%>/scripts/ajax.js?v=<%=info.getJsVersion()%>"></script>
<script type="text/javascript" src="<%=info.getReqUrl()%>/chartscript/amcharts.js?v=<%=info.getJsVersion()%> " > </script>
<script type="text/javascript" src="<%=info.getReqUrl()%>/chartscript/chartajax.js?v=<%=info.getJsVersion()%> " > </script>
<script type="text/javascript" src="<%=info.getReqUrl()%>/chartscript/pie.js?v=<%=info.getJsVersion()%> " > </script>

  <title>Rough Lot Process Report</title>
 
  </head>

        <%
        HashMap dbmsInfo = info.getDmbsInfoLst();
        HashMap prp = info.getPrp();
        HashMap mprp = info.getMprp();
        String logId=String.valueOf(info.getLogId());
        String onfocus="cook('"+logId+"')";
        String cnt  = util.nvl((String)info.getDmbsInfoLst().get("CNT"));
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
  <img src="../images/line.jpg" border="0" /></td><td valign="middle"> <span class="pgHed">Rough Lot Process Report</span> </td>
</tr></table>
  </td>
  </tr>
  <% if(!util.nvl((String)request.getAttribute("msg")).equals("")){%>
<tr><td valign="top" class="tdLayout">
<span class="redLabel"><%=request.getAttribute("msg")%></span>
</td></tr>
<%}%>
<tr><td valign="top" class="hedPg">
<html:form action="/mixre/lotReport.do?method=fetchRghProcesslot"  method="POST">
  <table  class="grid1">
   <tr>
   <th colspan="2" align="center">Lot Search</th>
   </tr>
   <tr>
   <td>Lot</td>
   <td><html:text property="value(lot)" name="lotReportForm" styleId="lot"/></td>
   </tr>
   <tr>
   <td align="center" colspan="2">
   <html:submit property="value(srch)" value="Fetch" styleClass="submit" />&nbsp;&nbsp;
   </td>
   </tr>
   </table>
</html:form>
</td>
</tr>
<%
String view=util.nvl((String)request.getAttribute("view"));
ArrayList lotClusList = (session.getAttribute("LOT_CLUS") == null)?new ArrayList():(ArrayList)session.getAttribute("LOT_CLUS");
HashMap ttlPolishMap = (HashMap)request.getAttribute("TTLPOLISHDATALIST");
ArrayList polishDataList = (request.getAttribute("POLISHDATALIST") == null)?new ArrayList():(ArrayList)request.getAttribute("POLISHDATALIST");
ArrayList vWPrpList = (session.getAttribute("LOT_RGHPRC_VW") == null)?new ArrayList():(ArrayList)session.getAttribute("LOT_RGHPRC_VW");
ArrayList processLst = (request.getAttribute("lotRghPrcDataprocessLst") == null)?new ArrayList():(ArrayList)request.getAttribute("lotRghPrcDataprocessLst");
HashMap dataDtl = (request.getAttribute("lotRghPrcDatadataDtl") == null)?new HashMap():(HashMap)request.getAttribute("lotRghPrcDatadataDtl");
ArrayList pktLst = (dataDtl.get("PKTLIST") == null)?new ArrayList():(ArrayList)dataDtl.get("PKTLIST");
int processLstsz=processLst.size();
int vWPrpListsz=vWPrpList.size();
if(dataDtl!=null && view.equals("Y")){
%>
<tr><td valign="top" class="hedPg">
<table class="grid1">
<tr>
<th>Lot No</th>
<th>Qty</th>
<th>Carat</th>
<th>Invoice Value</th>
<th>Vender</th>
<th>Pur Date</th>
<th> Term</th>
<th>Aadat</th>
<th>Aadat Comm</th>
<th>Broker</th>
<th>Broker Comm</th>
<th>Final Total Value</th>
<th> Miner</th>
<th>Mine</th>
<th>Rough Type</th>
<th>Sight</th>
<th> Ex Rate</th>
<th>Shipping Charges</th>
<th>Other Exp</th>
</tr>
<tr>
<td align="right"><%=util.nvl((String)dataDtl.get("lot_dsc"))%></td>
<td align="right"><%=util.nvl((String)dataDtl.get("ttl_qty"))%></td>
<td align="right"><%=util.nvl((String)dataDtl.get("ttl_cts"))%></td>
<td align="right"><%=util.nvl((String)dataDtl.get("inv_vlu"))%></td>
<td align="right"><%=util.nvl((String)dataDtl.get("vndr"))%></td>
<td align="right"><%=util.nvl((String)dataDtl.get("pur_dte"))%></td>
<td align="right"><%=util.nvl((String)dataDtl.get("trms"))%></td>
<td align="right"><%=util.nvl((String)dataDtl.get("aadat"))%></td>
<td align="right"><%=util.nvl((String)dataDtl.get("aadat_comm"))%></td>
<td align="right"><%=util.nvl((String)dataDtl.get("mbrk1"))%></td>
<td align="right"><%=util.nvl((String)dataDtl.get("mbrk1_comm"))%></td>
<td align="right"><%=util.nvl((String)dataDtl.get("fnlttl_vlu"))%></td>
<td align="right"><%=util.nvl((String)dataDtl.get("miner"))%></td>
<td align="right"><%=util.nvl((String)dataDtl.get("mine"))%></td>
<td align="right"><%=util.nvl((String)dataDtl.get("rgh_typ"))%></td>
<td align="right"><%=util.nvl((String)dataDtl.get("sight"))%></td>
<td align="right"><%=util.nvl((String)dataDtl.get("exh_rte"))%></td>
<td align="right"><%=util.nvl((String)dataDtl.get("ship_chgs"))%></td>
<td align="right"><%=util.nvl((String)dataDtl.get("other_exp"))%></td>
</tr>
</table>
</td></tr>
<tr><td valign="top" class="hedPg">
<table class="grid1">
<tr><th colspan="10">Purchase Details</th></tr>
<tr>
<th>Packet No</th>
<th>Qty</th>
<th>Carat</th>
<th> Rate</th>
<th> CP</th>
<th> Value</th>
<th> CP Value</th>
<th> Article</th>
<th> Rough Sz</th>
<th>Trf Date</th>
</tr>
<%for(int h=0;h<pktLst.size();h++){
HashMap data=(HashMap)pktLst.get(h);%>
<tr>
<td align="right"><%=util.nvl((String)data.get("ref_idn"))%></td>
<td align="right"><%=util.nvl((String)data.get("qty"))%></td>
<td align="right"><%=util.nvl((String)data.get("cts"))%></td>
<td align="right"><%=util.nvl((String)data.get("rte"))%></td>
<td align="right"><%=util.nvl((String)data.get("cp"))%></td>
<td align="right"><%=util.nvl((String)data.get("vlu"))%></td>
<td align="right"><%=util.nvl((String)data.get("cpvlu"))%></td>

<td align="right"><%=util.nvl((String)data.get("article"))%></td>
<td align="right"><%=util.nvl((String)data.get("rgh_sz"))%></td>
<td align="right"><%=util.nvl((String)data.get("trf_dte"))%></td>
</tr>
<%}%>
</table></td></tr>
<tr><td valign="top" class="hedPg">
<table class="grid1">
<%for(int i=0;i<processLstsz;i++){
String prc=util.nvl((String)processLst.get(i));
ArrayList issueIdDtlLst=(dataDtl.get(prc) == null)?new ArrayList():(ArrayList)dataDtl.get(prc);
int issueIdDtlLstsz=issueIdDtlLst.size();
%>
<tr>
<th colspan="13"><%=prc%></th>
</tr>
<%for(int j=0;j<issueIdDtlLstsz;j++){
HashMap dtl=(HashMap)issueIdDtlLst.get(j);
String issue_id=util.nvl((String)dtl.get("ISS_ID"));
String BYR=util.nvl((String)dtl.get("BYR"));
ArrayList pktList=(dataDtl.get(issue_id) == null)?new ArrayList():(ArrayList)dataDtl.get(issue_id);
int pktListsz=pktList.size();
%>
<tr>
<td  colspan="12" align="left">
<b>Process :</b> <%=prc%>
<b>Party :</b> <%=BYR%>
<b>Issue Id :</b>
<%=util.nvl((String)dtl.get("ISS_ID"))%>
<b>Crtwt :</b>
<%=util.nvl((String)dtl.get("CTS"))%>
<b>Vlu :</b>
<%=util.nvl((String)dtl.get("VLU"))%>
<b>CP Vlu :</b>
<%=util.nvl((String)dtl.get("CPVLU"))%>
</td>
</tr>
<tr><td align="center">Sr No</td><td align="center">Packet Id</td>
<%for(int l=0;l<vWPrpListsz;l++){
String lprp = (String)vWPrpList.get(l);
String prpDsc = (String)mprp.get(lprp+"D");%>
<td align="center"><%=prpDsc%></td>
<%}%>
<td align="center">Crtwt</td>
<td align="center">Qty</td>
<td align="center">Rte</td>
<td align="center">Vlu</td>
<td align="center">Cp Vlu</td>
</tr>
<%for(int p=0;p<pktListsz;p++){
HashMap pktPrpMap=(HashMap)pktList.get(p);
%>
<tr>
<td align="right"><%=p+1%></td>
<td align="right"><%=util.nvl((String)pktPrpMap.get("vnm"))%></td>
<%for(int l=0;l<vWPrpListsz;l++){
String lprp = (String)vWPrpList.get(l);%>
<td align="right"><%=util.nvl((String)pktPrpMap.get(lprp))%></td>
<%}%>
<td align="right"><%=util.nvl((String)pktPrpMap.get("cts"))%></td>
<td align="right"><%=util.nvl((String)pktPrpMap.get("qty"))%></td>
<td align="right"><%=util.nvl((String)pktPrpMap.get("rte"))%></td>
<td align="right"><%=util.nvl((String)pktPrpMap.get("vlu"))%></td>
<td align="right"><%=util.nvl((String)pktPrpMap.get("cpvlu"))%></td>
</tr>
<%}
dtl=(dataDtl.get(issue_id+"_TTL") == null)?new HashMap():(HashMap)dataDtl.get(issue_id+"_TTL");
%>
<tr>
<td align="center"><b>Total</b></td>
<td></td>
<%for(int l=0;l<vWPrpListsz;l++){%>
<td align="right"></td>
<%}%>
<td align="right"><b><%=util.nvl((String)dtl.get("CTS"))%></b></td>
<td align="right"><b><%=util.nvl((String)dtl.get("QTY"))%></b></td>
<td></td>
<td align="right"><b><%=util.nvl((String)dtl.get("VLU"))%></b></td>
<td align="right"><b><%=util.nvl((String)dtl.get("CPVLU"))%></b></td>
</tr>
<%}%>
<%}%>
</table>
</td>
</tr>
<%
if(polishDataList!=null && polishDataList.size()>0){
int lotCSz = lotClusList.size();
int colSpan = lotCSz+10;
%>
<tr><td valign="top" class="hedPg">
<table class="grid1">
<tr><th colspan="<%=colSpan%>">Polish Detail</th></tr>
<tr>
<th>Issue Id</th>
<th>Party</th>
<th>Packet Id</th>
<TH>Status</th>
<th>Issue Dte</th>
<th>Issue Cts</th>
<th>RGH Cts</th>
<th>POL Cts</th>
<%if(lotClusList!=null && lotCSz >0){
for(int i=0;i<lotClusList.size();i++){
String lprp = (String)lotClusList.get(i); 
String lprpDsc = (String)mprp.get(lprp+"D");
if(lprpDsc==null)
lprpDsc=lprp;
%>
<th><%=lprpDsc%></th>
<%
if(lprp.equals("CP")){%>
<th>CPVLU</th>
<%}
}}%>
<th>P.CP</th>
</tr>
<%
for(int i=0;i<polishDataList.size();i++){
HashMap polishData = (HashMap)polishDataList.get(i);
%>
<tr>
<td><%=util.nvl((String)polishData.get("ISSIDN"))%></td>
<td><%=util.nvl((String)polishData.get("BYR"))%></td>

<td><%=util.nvl((String)polishData.get("VNM"))%></td>
<td><%=util.nvl((String)polishData.get("STT"))%></td>
<td><%=util.nvl((String)polishData.get("ISSDTE"))%></td>
<td><%=util.nvl((String)polishData.get("ISSCTS"))%></td>
<td><%=util.nvl((String)polishData.get("RGHCTS"))%></td>
<td><%=util.nvl((String)polishData.get("POLCTS"))%></td>
<%if(lotClusList!=null && lotCSz >0){
for(int j=0;j<lotClusList.size();j++){
String lprp = (String)lotClusList.get(j);
%>
<td><%=util.nvl((String)polishData.get(lprp))%></td>
<%
if(lprp.equals("CP")){%>
<td><%=util.nvl((String)polishData.get("CPVLU"))%></td>
<%}}}%>
<td><%=util.nvl((String)polishData.get("PCP"))%></td>
</tr>
<%}%>
<tr><td colspan="5" align="right"><b>Total</b></td><td><b><%=util.nvl((String)ttlPolishMap.get("TTLISSCTS"))%></b> </td>
<td><b><%=util.nvl((String)ttlPolishMap.get("TTLRGHCTS"))%></b> </td>
<td><b><%=util.nvl((String)ttlPolishMap.get("TTLPOLCTS"))%></b> </td>
<%for(int j=0;j<lotClusList.size();j++){
String lprp = (String)lotClusList.get(j);
if(lprp.equals("CP")){
%>
<td><b><%=util.nvl((String)ttlPolishMap.get("TTLCP"))%></b> </td>
<td><b><%=util.nvl((String)ttlPolishMap.get("TTLCPVAL"))%></b> </td>
<%}else{%>
<td></td>
<%}}%>
<td><b><%=util.nvl((String)ttlPolishMap.get("TTLPCP"))%></b> </td>
</tr>
</table></td></tr>
<%}%>

<%}%>
  <tr><td>
<jsp:include page="/footer.jsp" />
</td></tr>
  </table>  
       <%@include file="../calendar.jsp"%>
  </body>
</html>