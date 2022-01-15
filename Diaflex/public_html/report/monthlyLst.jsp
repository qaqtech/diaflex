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

  <title>Monthly List Report</title>
 
  </head>
        <%
    ArrayList deptList=new ArrayList();
    HashMap prp = info.getPrp();
    deptList = (ArrayList)prp.get("DEPTV");
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
  <img src="../images/line.jpg" border="0" /></td><td valign="middle"> <span class="pgHed">Monthly List Report</span> </td>
</tr></table>
  </td>
  </tr>
<tr><td valign="top" class="hedPg">
<html:form action="/report/orclRptAction.do?method=fetchdmonthly"  method="POST">
        <table>
  <tr>
    <td>Dept :</td>
    <td>
    <html:select property="value(dept)" styleId="dept"  >
    <%
    if(deptList!=null){
    for(int i=0;i<deptList.size();i++){
    String deptVal=(String)deptList.get(i);%>
    <html:option value="<%=deptVal%>" ><%=deptVal%></html:option> 
    <%}}%>   
    </html:select>
    </td>
   <td align="center"> &nbsp;&nbsp;<html:submit property="value(srch)" value="Fetch" styleClass="submit" /></td>
    </tr>
   </table>
</html:form>
</td>
</tr>
  <%
    String view= util.nvl((String)request.getAttribute("view"));
    if(!view.equals("")){
    ArrayList commshLst= (ArrayList)request.getAttribute("commshLst");
    ArrayList shLst= (ArrayList)request.getAttribute("shLst");
    ArrayList szLst= (ArrayList)request.getAttribute("szLst");
    ArrayList clrLst= (ArrayList)request.getAttribute("clrLst");
    HashMap dataDtl= (HashMap)request.getAttribute("dataDtl");
    if(commshLst!=null && commshLst.size()>0){
    int szLstsz=szLst.size();
    int clrLstsz=clrLst.size();
    ArrayList prpShapeList=new ArrayList();
    ArrayList prpmixClrList=new ArrayList();
    ArrayList prpmixSzList=new ArrayList();
    ArrayList allclrmkavLst=new ArrayList();
    prpShapeList = (ArrayList)prp.get("SHAPEV");
    prpmixClrList = (ArrayList)prp.get("MIX_CLARITYV");
    prpmixSzList = (ArrayList)prp.get("MIX_SIZEV");
    String dept=util.nvl((String)dataDtl.get("DEPT"));
    String key="";
    allclrmkavLst.add("REP");allclrmkavLst.add("RSP");allclrmkavLst.add("MIX");
    allclrmkavLst.add("FcCut");allclrmkavLst.add("FcCol");allclrmkavLst.add("SAM");
    %>
<tr>
<td class="hedPg">
<table class="grid1small">
<tr><th colspan="<%=(szLstsz*3)+2%>"><b>ESTIMATE LIST OF <%=dept%> DEPARTMENT</b> Date-<%=util.getToDteMarker()%> &nbsp;&nbsp;
GRAND TOTAL : CTS : <%=util.roundToDecimals(((Double.parseDouble(util.nvl((String)dataDtl.get("GTTL_CTS")))+Double.parseDouble(util.nvl((String)dataDtl.get("M.SERI_CTS"))))),2)%>&nbsp;
AVG : <%=util.roundToDecimals(((Double.parseDouble(util.nvl((String)dataDtl.get("M.SERI_AVG")))+Double.parseDouble(util.nvl((String)dataDtl.get("GTTL_AVG"))))/2),2)%>&nbsp;
R-M : <%=util.nvl((String)dataDtl.get("GTTL_RM"))%>

</th></tr>
<%for(int i=0;i<prpShapeList.size();i++){
String sh=(String)prpShapeList.get(i);%>
<%if(commshLst.contains(sh)){%>
<tr><th colspan="<%=(szLstsz*3)+2%>"><%=sh%>
TOTAL : CTS : <%=util.nvl((String)dataDtl.get(sh+"_TTL_TTL_CTS"))%>&nbsp;
AVG : <%=util.nvl((String)dataDtl.get(sh+"_TTL_TTL_AVG"))%>&nbsp;
R-M : <%=util.nvl((String)dataDtl.get(sh+"_TTL_TTL_RM"))%>

</th></tr>
<tr>
<td></td>
<%
for(int mz=0;mz<prpmixSzList.size();mz++){
String mixsz=(String)prpmixSzList.get(mz);
if(szLst.contains(mixsz)){
if(mixsz.equals("1/2DN")){
%>
<td></td>
<%}%>
<th colspan="3" align="center"><%=mixsz%></th>
<%}}%>
</tr>
<tr>
<td></td>
<%for(int mz=0;mz<prpmixSzList.size();mz++){
String mixsz=(String)prpmixSzList.get(mz);
if(szLst.contains(mixsz)){
if(mixsz.equals("1/2DN")){
%>
<td></td>
<%}%>
<td align="center">CTS</td><td align="center">AVG</td><td align="center">R-M</td>
<%}}%>
</tr>
<%for(int mc=0;mc<prpmixClrList.size();mc++){
String mixclr=(String)prpmixClrList.get(mc);
if(clrLst.contains(mixclr)){
key=sh+"_"+mixclr;
%>
<tr>
<th align="center"><%=mixclr%></th>
<%
for(int mz=0;mz<prpmixSzList.size();mz++){
String mixsz=(String)prpmixSzList.get(mz);
if(szLst.contains(mixsz)){
if(mixsz.equals("1/2DN")){
%>
<th align="center"><%=mixclr%></th>
<%}%>
<td align="right"><%=util.nvl((String)dataDtl.get(key+"_"+mixsz+"_CTS"))%></td>
<td align="right"><%=util.nvl((String)dataDtl.get(key+"_"+mixsz+"_AVG"))%></td>
<td align="right"><%=util.nvl((String)dataDtl.get(key+"_"+mixsz+"_RM"))%></td>
<%}}%>
</tr>
<%}%>
<%}%>
<tr>
<th>Total</th>
<%
for(int mz=0;mz<prpmixSzList.size();mz++){
String mixsz=(String)prpmixSzList.get(mz);
if(szLst.contains(mixsz)){
key=sh+"_TTL_"+mixsz;
if(mixsz.equals("1/2DN")){
%>
<th align="center">Total</th>
<%}%>
<td align="right"><%=util.nvl((String)dataDtl.get(key+"_CTS"))%></td>
<td align="right"><%=util.nvl((String)dataDtl.get(key+"_AVG"))%></td>
<td align="right"><%=util.nvl((String)dataDtl.get(key+"_RM"))%></td>
<%}}%>
</tr>

<%}%>
<%}%>
</table>
</td>
</tr>
<tr>
<td class="hedPg">
<table>
<tr>
<td valign="top">
<table class="grid1small">
<tr><th colspan="4"></th></tr>
<tr><td></td><td align="center">CTS</td><td align="center">AVG</td><td align="center">R-M</td></tr>
<%
for(int s=0;s<prpShapeList.size();s++){
String shape=(String)prpShapeList.get(s);
if(shLst.contains(shape)){
%>
<tr>
<td align="center"><%=shape%></td>
<td align="right"><%=util.nvl((String)dataDtl.get(shape+"_CTS"))%></td>
<td align="right"><%=util.nvl((String)dataDtl.get(shape+"_AVG"))%></td>
<td align="right"><%=util.nvl((String)dataDtl.get(shape+"_RM"))%></td>
</tr>
<%}}%>
<tr>
<th>Total</th>
<td align="right"><%=util.nvl((String)dataDtl.get("FANCYSHTTL_CTS"))%></td>
<td align="right"><%=util.nvl((String)dataDtl.get("FANCYSHTTL_AVG"))%></td>
<td align="right"><%=util.nvl((String)dataDtl.get("FANCYSHTTL_RM"))%></td>
</tr>
</table>
</td>

<td valign="top">
<table class="grid1small">
<tr><th colspan="4"></th></tr>
<tr><td></td><td align="center">CTS</td><td align="center">AVG</td><td align="center">R-M</td></tr>
<tr>
<td>NATTS</td>
<td align="right"><%=util.nvl((String)dataDtl.get("NATTS_CTS"))%></td>
<td align="right"><%=util.nvl((String)dataDtl.get("NATTS_AVG"))%></td>
<td align="right"><%=util.nvl((String)dataDtl.get("NATTS_RM"))%></td>
</tr>
<tr>
<td>TLB</td>
<td align="right"><%=util.nvl((String)dataDtl.get("TLB_CTS"))%></td>
<td align="right"><%=util.nvl((String)dataDtl.get("TLB_AVG"))%></td>
<td align="right"><%=util.nvl((String)dataDtl.get("TLB_RM"))%></td>
</tr>
<tr>
<td>TLC</td>
<td align="right"><%=util.nvl((String)dataDtl.get("TLC_CTS"))%></td>
<td align="right"><%=util.nvl((String)dataDtl.get("TLC_AVG"))%></td>
<td align="right"><%=util.nvl((String)dataDtl.get("TLC_RM"))%></td>
</tr>
<tr>
<th>Total</th>
<td align="right"><%=util.nvl((String)dataDtl.get("ROUNDTTTL_CTS"))%></td>
<td align="right"><%=util.nvl((String)dataDtl.get("ROUNDTTTL_AVG"))%></td>
<td align="right"><%=util.nvl((String)dataDtl.get("ROUNDTTTL_RM"))%></td>
</tr>
</table>
</td>

<td valign="top">
<table class="grid1small">
<tr><th colspan="4"></th></tr>
<tr><td></td><td align="center">CTS</td><td align="center">AVG</td><td align="center">R-M</td></tr>
<%for(int a=0;a<allclrmkavLst.size();a++){
String allclr=(String)allclrmkavLst.get(a);
%>
<tr>
<td><%=allclr%></td>
<td align="right"><%=util.nvl((String)dataDtl.get(allclr+"_CTS"))%></td>
<td align="right"><%=util.nvl((String)dataDtl.get(allclr+"_AVG"))%></td>
<td align="right"><%=util.nvl((String)dataDtl.get(allclr+"_RM"))%></td>
</tr>
<%}%>
<tr>
<th>Total</th>
<td align="right"><%=util.nvl((String)dataDtl.get("ALLCLRTTL_CTS"))%></td>
<td align="right"><%=util.nvl((String)dataDtl.get("ALLCLRTTL_AVG"))%></td>
<td align="right"><%=util.nvl((String)dataDtl.get("ALLCLRTTL_RM"))%></td>
</tr>
</table>
</td>
<td valign="top">
<table class="grid1small">
<tr><th colspan="4"></th></tr>
<tr><td></td><td align="center">CTS</td><td align="center">AVG</td><td align="center">R-M</td></tr>
<tr>
<th>M.SERI</th>
<td align="right"><%=util.nvl((String)dataDtl.get("M.SERI_CTS"))%></td>
<td align="right"><%=util.nvl((String)dataDtl.get("M.SERI_AVG"))%></td>
<td align="right"></td>
</tr>
</table>
</td>
</tr>
</table>
</td>
</tr>
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