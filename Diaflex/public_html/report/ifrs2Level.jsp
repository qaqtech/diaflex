<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page errorPage="../error@Page.jsp" %>
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import="java.util.ArrayList,java.util.ArrayList,java.util.HashMap,ft.com.*, java.sql.*, java.util.Enumeration, java.util.logging.Level,java.math.BigDecimal, java.util.Collections"%>
<jsp:useBean id="info" class="ft.com.InfoMgr" scope="session" />
<jsp:useBean id="util" class="ft.com.JspUtil" scope="page" />
<%@taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>
 
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

  <title>Ifrs Report</title>
 
  </head>

   <%
       HashMap prp = info.getPrp();
       ArrayList prpPrtShape = (ArrayList)prp.get("SHAPEP");
       ArrayList prpValShape = (ArrayList)prp.get("SHAPEV");
       ArrayList prpPrtMixSize = (ArrayList)prp.get("MIX_SIZEP");
       ArrayList prpValMixSize = (ArrayList)prp.get("MIX_SIZEV");
       int prpPrtMixSizesz=prpPrtMixSize.size();
       int prpPrtShapesz=prpPrtShape.size();
       String dsp_stt=util.nvl((String)request.getAttribute("dsp_stt"));
        HashMap summaryMixMap= ((HashMap)session.getAttribute("summaryMix2LevelMap") == null)?new HashMap():(HashMap)session.getAttribute("summaryMix2LevelMap");
        System.out.println(summaryMixMap);
        DBUtil dbutil = new DBUtil();
        ArrayList keyLst=dbutil.getKeyInArrayList(summaryMixMap);
        HashMap uniqueMap=(HashMap)util.uniqueMap2Level(keyLst);
        ArrayList shLst=new ArrayList();
        ArrayList mixszLst=new ArrayList();
        shLst=((ArrayList)uniqueMap.get("SH") == null)?new ArrayList():(ArrayList)uniqueMap.get("SH");
        mixszLst=((ArrayList)uniqueMap.get("SZ") == null)?new ArrayList():(ArrayList)uniqueMap.get("SZ");
        String logId=String.valueOf(info.getLogId());
        String onfocus="cook('"+logId+"')";
        %>
 <body onfocus="<%=onfocus%>" onload="MM@PreloadImages('images/faunalogo.png')" topmargin="0" leftmargin="0" rightmargin="0" bottommargin="0">
 
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
<table width="100%"  border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td height="5" valign="top" class="greyline"><img src="../images/grey_linebig.png" width="685" height="5" align="right" /></td>
  </tr>
   <tr>
  <td valign="top" class="hedPg">
  <table cellpadding="1" cellspacing="5"><tr><td valign="middle">
  <img src="../images/line.jpg" border="0" /></td><td valign="middle"> <span class="pgHed">Report</span> </td>
</tr></table>
  </td>
  </tr>
  <tr>
  <td valign="top" class="hedPg">
  <table class="grid1">
  <tr>
  <th nowrap="nowrap">Shape/Mix Size</th>
  <%for(int m=0;m<prpPrtMixSizesz;m++){
  String mixsz=(String)prpValMixSize.get(m);
  if(mixszLst.contains(mixsz)){%>
  <th colspan="5" nowrap="nowrap"><%=prpPrtMixSize.get(m)%></th>
  <%}}%>
  </tr>
  <tr>
  <td></td>
  <%for(int m=0;m<prpPrtMixSizesz;m++){
  String mixsz=(String)prpValMixSize.get(m);
  if(mixszLst.contains(mixsz)){%>
    <td align="center" nowrap="nowrap">CTS</td>
    <td align="center" nowrap="nowrap">RTE</td>
    <td align="center" nowrap="nowrap">VLU</td>
    <td align="center" nowrap="nowrap">CP</td>
    <td align="center" nowrap="nowrap">CPVLU</td>
  <%}}%>
  </tr>
  
<%
for(int s=0;s<prpPrtShapesz;s++){
String shape=(String)prpValShape.get(s);
if(shLst.contains(shape)){
%>
<tr>
<td nowrap="nowrap"><%=shape%></td>
<%for(int m=0;m<prpPrtMixSizesz;m++){
String mixsz=(String)prpValMixSize.get(m);
if(mixszLst.contains(mixsz)){
double newMixQty= ((Double)summaryMixMap.get(shape+"@"+mixsz+"@QTY@NEW") == null)?0:(Double)summaryMixMap.get(shape+"@"+mixsz+"@QTY@NEW");
double newMixCts= ((Double)summaryMixMap.get(shape+"@"+mixsz+"@CTS@NEW") == null)?0:(Double)summaryMixMap.get(shape+"@"+mixsz+"@CTS@NEW");
double newMixVlu= ((Double)summaryMixMap.get(shape+"@"+mixsz+"@VLU@NEW") == null)?0:(Double)summaryMixMap.get(shape+"@"+mixsz+"@VLU@NEW");
double newMixCPVlu= ((Double)summaryMixMap.get(shape+"@"+mixsz+"@CPVLU@NEW") == null)?0:(Double)summaryMixMap.get(shape+"@"+mixsz+"@CPVLU@NEW");
double newMixAvg=0.0,newMixCPAvg=0.0;
if(newMixVlu > 0 && newMixCts > 0){
newMixAvg=Math.round(newMixVlu/newMixCts);
}
if(newMixCPVlu > 0 && newMixCts > 0){
newMixCPAvg=Math.round(newMixCPVlu/newMixCts);
}

double purnewMixQty= ((Double)summaryMixMap.get(shape+"@"+mixsz+"@QTY@PURNEW") == null)?0:(Double)summaryMixMap.get(shape+"@"+mixsz+"@QTY@PURNEW");
double purnewMixCts= ((Double)summaryMixMap.get(shape+"@"+mixsz+"@CTS@PURNEW") == null)?0:(Double)summaryMixMap.get(shape+"@"+mixsz+"@CTS@PURNEW");
double purnewMixVlu= ((Double)summaryMixMap.get(shape+"@"+mixsz+"@VLU@PURNEW") == null)?0:(Double)summaryMixMap.get(shape+"@"+mixsz+"@VLU@PURNEW");
double purnewMixCPVlu= ((Double)summaryMixMap.get(shape+"@"+mixsz+"@CPVLU@PURNEW") == null)?0:(Double)summaryMixMap.get(shape+"@"+mixsz+"@CPVLU@PURNEW");
double purnewMixAvg=0.0,purnewMixCPAvg=0.0;
if(purnewMixVlu > 0 && purnewMixCts > 0){
purnewMixAvg=Math.round(purnewMixVlu/purnewMixCts);
}
if(purnewMixCPVlu > 0 && purnewMixCts > 0){
purnewMixCPAvg=Math.round(purnewMixCPVlu/purnewMixCts);
}

//DEPTIN
double deptinnewMixQty= ((Double)summaryMixMap.get(shape+"@"+mixsz+"@QTY@DEPTIN") == null)?0:(Double)summaryMixMap.get(shape+"@"+mixsz+"@QTY@DEPTIN");
double deptinnewMixCts= ((Double)summaryMixMap.get(shape+"@"+mixsz+"@CTS@DEPTIN") == null)?0:(Double)summaryMixMap.get(shape+"@"+mixsz+"@CTS@DEPTIN");
double deptinnewMixVlu= ((Double)summaryMixMap.get(shape+"@"+mixsz+"@VLU@DEPTIN") == null)?0:(Double)summaryMixMap.get(shape+"@"+mixsz+"@VLU@DEPTIN");
double deptinnewMixCPVlu= ((Double)summaryMixMap.get(shape+"@"+mixsz+"@CPVLU@DEPTIN") == null)?0:(Double)summaryMixMap.get(shape+"@"+mixsz+"@CPVLU@DEPTIN");
double deptinnewMixAvg=0.0,deptinnewMixCPAvg=0.0;
if(deptinnewMixVlu > 0 && deptinnewMixCts > 0){
deptinnewMixAvg=Math.round(deptinnewMixVlu/deptinnewMixCts);
}
if(deptinnewMixCPVlu > 0 && deptinnewMixCts > 0){
deptinnewMixCPAvg=Math.round(deptinnewMixCPVlu/deptinnewMixCts);
}
//DEPTIN

double soldMixQty= ((Double)summaryMixMap.get(shape+"@"+mixsz+"@QTY@SOLD") == null)?0:(Double)summaryMixMap.get(shape+"@"+mixsz+"@QTY@SOLD");
double soldMixCts= ((Double)summaryMixMap.get(shape+"@"+mixsz+"@CTS@SOLD") == null)?0:(Double)summaryMixMap.get(shape+"@"+mixsz+"@CTS@SOLD");
double soldMixVlu= ((Double)summaryMixMap.get(shape+"@"+mixsz+"@VLU@SOLD") == null)?0:(Double)summaryMixMap.get(shape+"@"+mixsz+"@VLU@SOLD");
double soldMixCPVlu= ((Double)summaryMixMap.get(shape+"@"+mixsz+"@CPVLU@SOLD") == null)?0:(Double)summaryMixMap.get(shape+"@"+mixsz+"@CPVLU@SOLD");
double soldMixAvg=0.0,soldMixCPAvg=0.0;
if(soldMixVlu > 0 && soldMixCts > 0){
soldMixAvg=Math.round(soldMixVlu/soldMixCts);
}
if(soldMixCPVlu > 0 && soldMixCts > 0){
soldMixCPAvg=Math.round(soldMixCPVlu/soldMixCts);
}

double stkMixQty= ((Double)summaryMixMap.get(shape+"@"+mixsz+"@QTY@STK") == null)?0:(Double)summaryMixMap.get(shape+"@"+mixsz+"@QTY@STK");
double stkMixCts= ((Double)summaryMixMap.get(shape+"@"+mixsz+"@CTS@STK") == null)?0:(Double)summaryMixMap.get(shape+"@"+mixsz+"@CTS@STK");
double stkMixVlu= ((Double)summaryMixMap.get(shape+"@"+mixsz+"@VLU@STK") == null)?0:(Double)summaryMixMap.get(shape+"@"+mixsz+"@VLU@STK");
double stkMixCPVlu= ((Double)summaryMixMap.get(shape+"@"+mixsz+"@CPVLU@STK") == null)?0:(Double)summaryMixMap.get(shape+"@"+mixsz+"@CPVLU@STK");

double p2newMixQty= ((Double)summaryMixMap.get(shape+"@"+mixsz+"@QTY@P2NEW") == null)?0:(Double)summaryMixMap.get(shape+"@"+mixsz+"@QTY@P2NEW");
double p2newMixCts= ((Double)summaryMixMap.get(shape+"@"+mixsz+"@CTS@P2NEW") == null)?0:(Double)summaryMixMap.get(shape+"@"+mixsz+"@CTS@P2NEW");
double p2newMixVlu= ((Double)summaryMixMap.get(shape+"@"+mixsz+"@VLU@P2NEW") == null)?0:(Double)summaryMixMap.get(shape+"@"+mixsz+"@VLU@P2NEW");
double p2newMixCPVlu= ((Double)summaryMixMap.get(shape+"@"+mixsz+"@CPVLU@P2NEW") == null)?0:(Double)summaryMixMap.get(shape+"@"+mixsz+"@CPVLU@P2NEW");

double p2soldMixQty= ((Double)summaryMixMap.get(shape+"@"+mixsz+"@QTY@P2SOLD") == null)?0:(Double)summaryMixMap.get(shape+"@"+mixsz+"@QTY@P2SOLD");
double p2soldMixCts= ((Double)summaryMixMap.get(shape+"@"+mixsz+"@CTS@P2SOLD") == null)?0:(Double)summaryMixMap.get(shape+"@"+mixsz+"@CTS@P2SOLD");
double p2soldMixVlu= ((Double)summaryMixMap.get(shape+"@"+mixsz+"@VLU@P2SOLD") == null)?0:(Double)summaryMixMap.get(shape+"@"+mixsz+"@VLU@P2SOLD");
double p2soldMixCPVlu= ((Double)summaryMixMap.get(shape+"@"+mixsz+"@CPVLU@P2SOLD") == null)?0:(Double)summaryMixMap.get(shape+"@"+mixsz+"@CPVLU@P2SOLD");

double closeMixQty=0.0,closeMixCts=0.0,closeMixVlu=0.0,closeMixCPVlu=0.0,closeMixAvg=0.0,closeMixCPAvg=0.0;;

closeMixQty=(stkMixQty+p2soldMixQty)-p2newMixQty;
closeMixCts=(stkMixCts+p2soldMixCts)-p2newMixCts;
closeMixVlu=(stkMixVlu+p2soldMixVlu)-p2newMixVlu;
closeMixCPVlu=(stkMixCPVlu+p2soldMixCPVlu)-p2newMixCPVlu;
if(closeMixVlu > 0 && closeMixCts > 0){
closeMixAvg=Math.round(closeMixVlu/closeMixCts);
}
if(closeMixCPVlu > 0 && closeMixCts > 0){
closeMixCPAvg=Math.round(closeMixCPVlu/closeMixCts);
}

double openMixQty=0.0,openMixCts=0.0,openMixVlu=0.0,openMixCPVlu=0.0,openMixAvg=0.0,openMixCPAvg=0.0;;
openMixQty=(closeMixQty+soldMixQty)-(newMixQty+purnewMixQty+deptinnewMixQty);
openMixCts=(closeMixCts+soldMixCts)-(newMixCts+purnewMixCts+deptinnewMixCts);
openMixVlu=(closeMixVlu+soldMixVlu)-(newMixVlu+purnewMixVlu+deptinnewMixVlu);
openMixCPVlu=(closeMixCPVlu+soldMixCPVlu)-(newMixCPVlu+purnewMixCPVlu+deptinnewMixCPVlu);
if(openMixVlu > 0 && openMixCts > 0){
openMixAvg=Math.round(openMixVlu/openMixCts);
}
if(openMixCPVlu > 0 && openMixCts > 0){
openMixCPAvg=Math.round(openMixCPVlu/openMixCts);
}
%>
<%if(dsp_stt.equals("OPEN")){%>
<td align="right"><%=util.roundToDecimals(openMixCts,2)%></td>
<td align="right"><%=util.roundToDecimals(openMixAvg,2)%></td>
<td align="right" title="Vlu / 100000"><%=util.roundToDecimals(openMixVlu/100000,2)%></td>
<td align="right"><%=util.roundToDecimals(openMixCPAvg,2)%></td>
<td align="right" title="Vlu / 100000"><%=util.roundToDecimals(openMixCPVlu/100000,2)%></td>
<%}%>
<%if(dsp_stt.equals("NEW")){%>
<td align="right">
<%=util.roundToDecimals(newMixCts,2)%>
</td>
<td align="right"><%=util.roundToDecimals(newMixAvg,2)%></td>
<td align="right" title="Vlu / 100000"><%=util.roundToDecimals(newMixVlu/100000,2)%></td>
<td align="right"><%=util.roundToDecimals(newMixCPAvg,2)%></td>
<td align="right" title="Vlu / 100000"><%=util.roundToDecimals(newMixCPVlu/100000,2)%></td>
<%}%>
<%if(dsp_stt.equals("PURNEW")){%>
<td align="right">
<%=util.roundToDecimals(purnewMixCts,2)%>
</td>
<td align="right"><%=util.roundToDecimals(purnewMixAvg,2)%></td>
<td align="right" title="Vlu / 100000"><%=util.roundToDecimals(purnewMixVlu/100000,2)%></td>
<td align="right"><%=util.roundToDecimals(purnewMixCPAvg,2)%></td>
<td align="right" title="Vlu / 100000"><%=util.roundToDecimals(purnewMixCPVlu/100000,2)%></td>
<%}%>
<%if(dsp_stt.equals("DEPTIN")){%>
<td align="right">
<%=util.roundToDecimals(deptinnewMixCts,2)%>
</td>
<td align="right"><%=util.roundToDecimals(deptinnewMixAvg,2)%></td>
<td align="right" title="Vlu / 100000"><%=util.roundToDecimals(deptinnewMixVlu/100000,2)%></td>
<td align="right"><%=util.roundToDecimals(deptinnewMixCPAvg,2)%></td>
<td align="right" title="Vlu / 100000"><%=util.roundToDecimals(deptinnewMixCPVlu/100000,2)%></td>
<%}%>
<%if(dsp_stt.equals("SOLD")){%>
<td align="right">
<%=util.roundToDecimals(soldMixCts,2)%>
</td>
<td align="right"><%=util.roundToDecimals(soldMixAvg,2)%></td>
<td align="right" title="Vlu / 100000"><%=util.roundToDecimals(soldMixVlu/100000,2)%></td>
<td align="right"><%=util.roundToDecimals(soldMixCPAvg,2)%></td>
<td align="right" title="Vlu / 100000"><%=util.roundToDecimals(soldMixCPVlu/100000,2)%></td>
<%}%>
<%if(dsp_stt.equals("CLOSE")){%>
<td align="right"><%=util.roundToDecimals(closeMixCts,2)%></td>
<td align="right"><%=util.roundToDecimals(closeMixAvg,2)%></td>
<td align="right" title="Vlu / 100000"><%=util.roundToDecimals(closeMixVlu/100000,2)%></td>
<td align="right"><%=util.roundToDecimals(closeMixCPAvg,2)%></td>
<td align="right" title="Vlu / 100000"><%=util.roundToDecimals(closeMixCPVlu/100000,2)%></td>
      <%}%>
      <%}}%>
   </tr>
    <%}}%>
  </table>
  </td>
  </tr>


  <tr><td>
<jsp:include page="/footer.jsp" />
</td></tr>
  </table>  
       <%@include file="../calendar.jsp"%>
  </body>
</html>