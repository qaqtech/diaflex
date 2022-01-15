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

  <title>Lot Process Report</title>
 
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
  <img src="../images/line.jpg" border="0" /></td><td valign="middle"> <span class="pgHed">Lot Process Report</span> </td>
</tr></table>
  </td>
  </tr>
  <% if(!util.nvl((String)request.getAttribute("msg")).equals("")){%>
<tr><td valign="top" class="tdLayout">
<span class="redLabel"><%=request.getAttribute("msg")%></span>
</td></tr>
<%}%>
<tr><td valign="top" class="hedPg">
<html:form action="/mixre/lotReport.do?method=fetchProcesslot"  method="POST">
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
ArrayList vWPrpList = (session.getAttribute("LOT_PRC_VW") == null)?new ArrayList():(ArrayList)session.getAttribute("LOT_PRC_VW");
ArrayList processLst = (session.getAttribute("lotPrcDataprocessLst") == null)?new ArrayList():(ArrayList)session.getAttribute("lotPrcDataprocessLst");
HashMap dataDtl = (session.getAttribute("lotPrcDatadataDtl") == null)?new HashMap():(HashMap)session.getAttribute("lotPrcDatadataDtl");
int processLstsz=processLst.size();
int vWPrpListsz=vWPrpList.size();
if(processLstsz>0 && view.equals("Y")){
%>
<tr><td valign="top" class="hedPg">
<table class="grid1">
<tr>
<th>Lot No</th>
<th>Rough Cts</th>
<th>Conversion</th>
<th>Lp Cts</th>
<th>Pol Pcs</th>
<th>Rgh Rtn</th>
<th>Tot Lab</th>
<th>$ Vlu</th>
</tr>
<tr>
<td><%=util.nvl((String)dataDtl.get("LOTNO"))%></td>
<td><%=util.nvl((String)dataDtl.get("RGH_CTS"))%></td>
<td><%=util.nvl((String)dataDtl.get("EXH_RTE"))%></td>
<td><%=util.nvl((String)dataDtl.get("LP_CTS"))%></td>
<td><%=util.nvl((String)dataDtl.get("TTL_STONE"))%></td>
<td><%=util.nvl((String)dataDtl.get("REJ_CTS"))%></td>
<td><%=util.nvl((String)dataDtl.get("LBPERSTONE"))%></td>
<td><%=util.nvl((String)dataDtl.get("TTL_VLU"))%></td>
</tr>
</table>
</td></tr>
<tr><td valign="top" class="hedPg">

<%for(int i=0;i<processLstsz;i++){
String prc=util.nvl((String)processLst.get(i));
ArrayList issueIdDtlLst=(dataDtl.get(prc) == null)?new ArrayList():(ArrayList)dataDtl.get(prc);
int issueIdDtlLstsz=issueIdDtlLst.size();
%>
<table class="grid1">
<tr>
<th colspan="12"><%=prc%></th>
</tr>
<%for(int j=0;j<issueIdDtlLstsz;j++){
HashMap dtl=(HashMap)issueIdDtlLst.get(j);
String issue_id=util.nvl((String)dtl.get("ISS_ID"));
ArrayList pktList=(dataDtl.get(issue_id) == null)?new ArrayList():(ArrayList)dataDtl.get(issue_id);
int pktListsz=pktList.size();
%>
<tr>
<td  colspan="12" align="left"><b>Issue Id :</b>
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
<%}%></table>

<%}%>
</td>
</tr>
<%}%>
  <tr><td>
<jsp:include page="/footer.jsp" />
</td></tr>
  </table>  
       <%@include file="../calendar.jsp"%>
  </body>
</html>