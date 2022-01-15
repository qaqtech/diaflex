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

  <title>Kaccha Report</title>
 
  </head>
  
  <%
        String logId=String.valueOf(info.getLogId());
        String onfocus="cook('"+logId+"')";
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
  <img src="../images/line.jpg" border="0" /></td><td valign="middle"> <span class="pgHed">Kaccha Report</span> </td>
</tr></table>
  </td>
  </tr>
<tr><td valign="top" class="hedPg">
<html:form action="/report/orclReportAction.do?method=fetchKaccha"  method="POST">
  <table  class="grid1">
  <tr><th>Generic Search
  <%
  ArrayList rolenmLst=(ArrayList)info.getRoleLst();
  String usrFlg=util.nvl((String)info.getUsrFlg());
  if(usrFlg.equals("SYS") || rolenmLst.contains("ADMIN")){
  %>
  &nbsp;&nbsp;<img src="../images/edit.jpg" title="Edit Properties Module" onclick="newWindow('<%=info.getReqUrl()%>/dynamicProperty.do?method=load&mdl=KACCHA_SRCH&sname=kacchaGNCSrch&par=GENERIC')" border="0" width="15px" height="15px"/> 
  <%}%>
  </th></tr>
  <tr>
   <td>
    <jsp:include page="/genericSrch.jsp"/>
   </td></tr>
   <tr>
   <td colspan="2" align="center"><html:submit property="value(srch)" value="Kaccha Report" styleClass="submit" />
   <html:submit property="value(memo)" value="Memo Wise" styleClass="submit" /></td>
   </tr>
   </table>
</html:form>
</td>
</tr>
  <%
    HashMap dataDtl= (HashMap)session.getAttribute("dataDtl");
    ArrayList memoLst= (ArrayList)session.getAttribute("memoLst");
    ArrayList sttLst= (ArrayList)session.getAttribute("sttLst");
    String view= util.nvl((String)request.getAttribute("View"));
    HashMap data=new HashMap();
    HashMap datastt=new HashMap();
    ArrayList keystt=new ArrayList();
    String stt="";
    if(!view.equals("")){
    if(view.equals("Y")){
    if(dataDtl!=null && dataDtl.size()>0){
  %>
  <tr><td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Create Excel <img src="../images/ico_file_excel.png" onclick="goTo('orclReportAction.do?method=createKacchaXL','','')" border="0"/> </td></tr>
  <tr><td class="hedPg"><table class="grid1">
  
   
  <%for(int i=0;i<memoLst.size();i++){
  String memo=(String)memoLst.get(i);
  data=new HashMap();
  datastt=new HashMap();
  keystt=new ArrayList();
  %>
 <tr style="font-weight: bold;">
 <td>Status</td>
 <td>Pics</td>
 <td nowrap="nowrap">GiaCrt</td>
 <td nowrap="nowrap">GrdCrt</td>
 <td nowrap="nowrap">Ka Amt</td>
 <td nowrap="nowrap">Ka Avg</td>
 <td nowrap="nowrap">MnAmt</td>
 <td nowrap="nowrap">MnjAvg</td>
 <td nowrap="nowrap">Memo ID</td>
  <td nowrap="nowrap">Dept</td>
  <td nowrap="nowrap">TotCrt</td>
  <td nowrap="nowrap">TotIss-Rec Amt</td>
  <td nowrap="nowrap">TotIss-Rec Crt</td>
  <td nowrap="nowrap">TotK Amt</td>
  <td nowrap="nowrap">Akhi Avg</td>
  <td nowrap="nowrap">Send Vlu</td>
  <td nowrap="nowrap">Send Crt</td>
  <td nowrap="nowrap">Send %</td>
  <td nowrap="nowrap">Added Per</td>
  <td nowrap="nowrap">Baki Avg</td>
  <td nowrap="nowrap">Baki Amt</td>
 </tr>
 <%
 for(int j=0;j<sttLst.size();j++){
 stt=(String)sttLst.get(j);
 data=new HashMap();
 data=(HashMap)dataDtl.get(memo+"_"+stt);
 if(data!=null && data.size()>0){
 datastt.put(stt,data);
 keystt.add(stt);
 }}
 for(int k=0;k<datastt.size();k++){
 data=new HashMap();
 stt=(String)sttLst.get(k);
 data=(HashMap)datastt.get(stt);
 %>
 <tr>
  <td align="center"><%=stt%></td>
 <td align="right"><%=data.get("QTY")%></td>
 <td align="right"><%=data.get("GIACTS")%></td>
 <td align="right"><%=data.get("GRDCTS")%></td>
  <td align="right"><%=data.get("VLU")%></td>
   <td align="right"><%=data.get("AVG")%></td>
    <td align="right"><%=data.get("MNJAMT")%></td>
     <td align="right"><%=data.get("MNJAVG")%></td>
      <%if(k==(datastt.size()-1)){
  data=new HashMap();
  data=(HashMap)dataDtl.get(memo+"_TTL");
  if(data==null)
  data=new HashMap();
  %>
  <td align="center"><b><%=memo%></b></td>
 <td align="center"><%=util.nvl((String)data.get("DEPT"))%></td>
 <td align="right"><%=util.nvl((String)data.get("TCTS"))%></td>
  <td align="right"><%=util.nvl((String)data.get("TISS"))%></td>
   <td align="right"><%=util.nvl((String)data.get("TISSCTS"))%></td>
   <td align="right"><%=util.nvl((String)data.get("TKAMT"))%></td>
    <td align="right"><%=util.nvl((String)data.get("TAAVG"))%></td>
    <td align="right"><%=util.nvl((String)data.get("SVLU"))%></td>
    <td align="right"><%=util.nvl((String)data.get("SCTS"))%></td>
     <td align="right"><%=util.nvl((String)data.get("TBAKI"))%></td>
      <td align="right"><%=util.nvl((String)data.get("TADD"))%></td>
       <td align="right"><%=util.nvl((String)data.get("TBAKIAVG"))%></td>
        <td align="right"><%=util.nvl((String)data.get("TBAKIAMT"))%></td>
  <%}else{%>
  <td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td>
  <%}%>
 </tr>
 
 <%}%>
 <tr style="border:1px;"></tr>
  <%}%>
  
  
</table></td></tr>
<%}}else if(view.equals("N")){
ArrayList colList= (ArrayList)request.getAttribute("colList");
ArrayList clrList= (ArrayList)request.getAttribute("clrList");
ArrayList memoList= (ArrayList)request.getAttribute("memoList");
int colListsz=colList.size();
int clrListsz=clrList.size();
String key="",avg="",sumavg="",grandsumavg="";
double getavg=0;
double percent=0;
%>

<%for(int i=0;i<memoList.size();i++){
String memoVal=util.nvl((String)memoList.get(i));
key=memoVal+"_TTL";
grandsumavg=util.nvl2((String)dataDtl.get(key),"0");
double grandavg=Double.parseDouble(grandsumavg);
%>
<tr>
<td class="hedPg">
<table class="grid1">
<tr>
<td colspan="<%=(clrListsz*2)+3%>" align="center"><%=memoVal%></td>
</tr>
<tr>
<th></th>
<%for(int j=0;j<clrListsz;j++){%>
<th align="center" colspan="2"><%=util.nvl((String)clrList.get(j))%></th>
<%}%>
<th align="center" colspan="2">Total</th>
</tr>
<%for(int k=0;k<colListsz;k++){
String colVal=util.nvl((String)colList.get(k));%>
<tr><th><%=colVal%></th>
<%
for(int l=0;l<clrListsz;l++){
String clrVal=util.nvl((String)clrList.get(l));
key = memoVal+"_"+colVal+"_"+clrVal+"_AVG";
avg=util.nvl2((String)dataDtl.get(key),"0");
getavg=Double.parseDouble(avg);
percent=util.roundToDecimals(((getavg/grandavg)*100),1);
%>
<td><%=avg%></td><td><%=percent%>%</td>
<%}
key = memoVal+"_"+colVal+"_COLTTL";
sumavg=util.nvl2((String)dataDtl.get(key),"0");
getavg=Double.parseDouble(sumavg);
percent=util.roundToDecimals(((getavg/grandavg)*100),1);
%>
<td><%=sumavg%></td><td><%=percent%>%</td>
</tr>
<%}%>
<tr>
<th>Total</th>
<%
for(int l=0;l<clrListsz;l++){
String clrVal=util.nvl((String)clrList.get(l));
key = memoVal+"_"+clrVal+"_CLRTTL";
sumavg=util.nvl2((String)dataDtl.get(key),"0");
getavg=Double.parseDouble(sumavg);
percent=util.roundToDecimals(((getavg/grandavg)*100),1);
%>
<td><%=sumavg%></td><td><%=percent%>%</td>
<%}%>
<td><%=grandsumavg%></td><td>100%</td>
</tr>
</table>
</td>
</tr>
<%}%>
<%}else{%>
<tr><td valign="top" class="hedPg">
Sorry No Result Found</td></tr>
<%}}%>
  <tr><td>
<jsp:include page="/footer.jsp" />
</td></tr>
  </table>  
  </body>
</html>