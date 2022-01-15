<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
 
<%@ page errorPage="../error_page.jsp" %>
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import="java.util.ArrayList,java.util.ArrayList, java.util.Iterator,java.util.List,java.io.*, java.sql.Date,java.util.HashMap, ft.com.marketing.PacketLookupForm" %>
<jsp:useBean id="util" class="ft.com.JspUtil" scope="page" />
<jsp:useBean id="info" class="ft.com.InfoMgr" scope="session" />
<%@taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>
<%@taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<html>
  <head>
    <meta name="Keywords" content="javascript,dhtml,html,navigation,pop ups,web tools,graphics" />
  <meta name="Description" content="free javascrip, software and graphic programs,pop up windows,menus,mouseover effects" />
  <meta http-equiv="Content-Language" content="en-gb"/>
  <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>Packet Details</title>
    <script src="../scripts/bse.js?v=<%=info.getJsVersion()%>" type="text/javascript"></script>
    <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/bse.css?v=<%=info.getJsVersion()%>"/>
      <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/style.css?v=<%=info.getJsVersion()%>"/>
      <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/memoScript.js?v=<%=info.getJsVersion()%> " > </script>
      <script src="<%=info.getReqUrl()%>/PopScripts/jquery-1.2.6.min.js" type="text/javascript"></script>
      <script src="<%=info.getReqUrl()%>/jqueryScript/jqueryScript.js?v=<%=info.getJsVersion()%>" type="text/javascript"></script>
      <script src="<%=info.getReqUrl()%>/jqueryScript/jquery.min.js" type="text/javascript"></script>  
      <meta name="GENERATOR" content="Microsoft FrontPage 4.0">
      <meta name="ProgId" content="FrontPage.Editor.Document"><META HTTP-EQUIV="imagetoolbar" CONTENT="no">
  </head>
        <%String logId=String.valueOf(info.getLogId());
        String onfocus="cook('"+logId+"')";
        %>
 <body onfocus="<%=onfocus%>">
   <%
  String vnm = util.nvl((String)request.getAttribute("vnm"));
  String mstkidn = util.nvl((String)request.getAttribute("mstkidn"));
  String View = util.nvl((String)request.getAttribute("View"));
  String LOOK = util.nvl((String)request.getAttribute("LOOK"));
  String cert_no = util.nvl((String)request.getAttribute("cert_no"));
  HashMap mprp = info.getMprp();
  HashMap pktDtl = (HashMap) request.getAttribute("pktDtl");
  ArrayList diaViewPrp = (ArrayList)session.getAttribute("DIA_DTL_VW");
  ArrayList makViewPrp = (ArrayList)session.getAttribute("MAK_DTL_VW");
  ArrayList incViewPrp = (ArrayList)session.getAttribute("INC_DTL_VW");
  ArrayList parViewPrp = (ArrayList)session.getAttribute("PAR_DTL_VW");
  int cnt=0;
  String style = "display:none;";
  String color="color:#ffffff";
String isMix = util.nvl(info.getIsMix());
String pgDef = "SEARCH_RESULT";
if(!isMix.equals(""))
pgDef = "MIXSRCH_RESULT";
HashMap allPageDtl= (info.getPageDetails() == null)?new HashMap():(HashMap)info.getPageDetails();
HashMap pageDtl=(HashMap)allPageDtl.get(pgDef);
String memolmt = util.nvl(info.getMemo_lmt());
ArrayList pageList=new ArrayList();
HashMap pageDtlMap=new HashMap();
String fld_nme="",fld_ttl="",val_cond="",dflt_val="",fld_typ="",form_nme="",lov_qry="",flg1="";

ArrayList imagelistDtl= (info.getImagelistDtl() == null)?new ArrayList():(ArrayList)info.getImagelistDtl();
HashMap dtls=new HashMap();
HashMap dbinfo = info.getDmbsInfoLst();
String client = (String)dbinfo.get("CNT");
int counter=0;
 if(View.equals("Y")){
 %>
  <table><tr><td>
  <table>
 
  <tr>
  <td><span class="txtLink" id="PKT_TAB" style="<%=color%>" onclick="showHideDiv('.pktDtl','PKT',this)">Packet Details</span></td>
<%pageList= ((ArrayList)pageDtl.get("DNA_TAB") == null)?new ArrayList():(ArrayList)pageDtl.get("DNA_TAB");
if(pageList!=null && pageList.size() >0){
pageDtlMap=(HashMap)pageList.get(0);
fld_ttl=util.nvl((String)pageDtlMap.get("fld_ttl"));
val_cond=util.nvl((String)pageDtlMap.get("val_cond"));
val_cond=val_cond.replaceAll("STKIDN", mstkidn);
%>
<td><span class="txtLink" id="DNA_TAB" style="" onclick="<%=val_cond%>"><%=fld_ttl%></span></td>
<%}%>
  <td><span class="txtLink" id="IMG_TAB" style="" onclick="showHideDiv('.pktDtl','IMG',this)">Diamond Images</span></td>
  <td><span class="txtLink" id="VID_TAB" style="" onclick="showHideDiv('.pktDtl','VID',this)">Diamond Video</span></td>
  <td><span class="txtLink" id="CERT_TAB" style="" onclick="showHideDiv('.pktDtl','CERT',this)">Diamond Grading Report</span></td>
  </tr>
</table></td>
</tr>
<tr>
<tr><td>
<div id="PKT" class="pktDtl"> 
<table class="grid1">
<%if(pktDtl!=null){%>
<tr>
<th colspan="10">Diamond Details</th></tr>
<tr>
<%for(int i=0;i<diaViewPrp.size();i++){
String prp = (String)diaViewPrp.get(i); 
String prpDsc = (String)mprp.get(prp+"D");
String val=util.nvl2((String)pktDtl.get("DIA_DTL_VW_"+prp),"-");
cnt++;
if(cnt==6){
%>
</tr><tr>
<%}%>
<td class="prpDsc"><%=prpDsc%></td>
<td><%=val%></td>
<%}%>
</tr>
<tr>
<th colspan="10">Make Details</th></tr>
<tr>
<%cnt=0;
for(int i=0;i<makViewPrp.size();i++){
String prp = (String)makViewPrp.get(i); 
String prpDsc = (String)mprp.get(prp+"D");
String val=util.nvl2((String)pktDtl.get("MAK_DTL_VW_"+prp),"-");
cnt++;
if(cnt==6){
%>
</tr><tr>
<%}%>
<td class="prpDsc"><%=prpDsc%></td>
<td><%=val%></td>
<%}%>
</tr>
<tr>
<th colspan="10">Inclusion Details</th></tr>
<tr>
<%cnt=0;
for(int i=0;i<incViewPrp.size();i++){
String prp = (String)incViewPrp.get(i); 
String prpDsc = (String)mprp.get(prp+"D");
String val=util.nvl2((String)pktDtl.get("INC_DTL_VW_"+prp),"-");
cnt++;
if(cnt==6){
%>
</tr><tr>
<%}%>
<td class="prpDsc"><%=prpDsc%></td>
<td><%=val%></td>
<%}%>
</tr>
<tr>
<th colspan="10">Parameter Details</th></tr>
<tr>
<%cnt=0;
for(int i=0;i<parViewPrp.size();i++){
String prp = (String)parViewPrp.get(i); 
String prpDsc = (String)mprp.get(prp+"D");
String val=util.nvl2((String)pktDtl.get("PAR_DTL_VW_"+prp),"-");
cnt++;
if(cnt==6){
%>
</tr><tr>
<%}%>
<td class="prpDsc"><%=prpDsc%></td>
<td><%=val%></td>
<%}%>
</tr>
<%}%>
</table>
</div>

<div id="IMG" style="display:none;"  class="pktDtl">
<table>
<tr><td>

<%
String first="";
%>
<div>
<button id="value(pb_mail)" class="submit" name="value(pb_mail)" accesskey="S" onclick="return SendMailimages('PacketLookup.do?method=mailExcelMass');" type="button">Send Mail</button>
</div>
<div style="margin-left:20px">
<div style="float:left; margin:20px;">
<%
if(imagelistDtl!=null && imagelistDtl.size() >0){
for(int j=0;j<imagelistDtl.size();j++){
dtls=new HashMap();
dtls=(HashMap)imagelistDtl.get(j);
String typ=util.nvl((String)dtls.get("TYP"));
String allowon=util.nvl((String)dtls.get("ALLOWON"));
%>
<%if(typ.equals("I") && (allowon.indexOf("SRCH") > -1 || allowon.indexOf("PKT") > -1)){
String path=util.nvl((String)dtls.get("PATH"));
String gtCol=util.nvl((String)dtls.get("GTCOL"));
String val=util.nvl((String)pktDtl.get(gtCol));
if(!val.equals("N") && path.indexOf("segoma") < 0 && path.indexOf("sarineplatform") < 0){
path=path+val;
if(first.equals(""))
first=path;
counter++;
if (path.indexOf("&") > -1) {
path = path.replaceAll("&", "%26");
}
%>
<div style="padding:5px;">
<div style="float:left; padding:5px">
<input type="checkbox" id="counter_<%=counter%>" name="counter_<%=counter%>" checked="checked" value="<%=path%>" title="Check To Attached For mail">
</div>
<div style="float:left; padding:5px">
<%if(val.indexOf(".pdf") < 0){%>
<img src="<%=path%>" onclick="changeImg('<%=path%>')" width="100px" height="100px" >
<%}%>
<img src="../images/download.jpg" onclick="newWindow('<%=info.getReqUrl()%>/contact/nmedocs.do?method=download&dwusingurl=Y&path=<%=path%>')" width="20px" height="20px" title="Click Here To Download" />
</div>
</div>
<%}}}}%>
</div>
<%
if(!first.equals("")){%>
<div style="float:right;margin-left:200px;">
<input type="hidden" id="zoomUrl" name="zoomUrl" value="<%=info.getReqUrl()%>/zoompic.jsp?ht=600&fileNme=">
<A href="<%=info.getReqUrl()%>/zoompic.jsp?ht=600&fileNme=<%=first%>" target="_blank" id="zoom" title="Click Here To Zoom">
<img src="<%=first%>" id="change" width="400px"  height="400px" >
</a>
</div>
<%}%>
</div>
</td>
</tr>
</table>
</div>
<div id="VID" style="display:none;" class="pktDtl">
<table>
<tr>
<td valign="top">
<%
if(imagelistDtl!=null && imagelistDtl.size() >0){
for(int j=0;j<imagelistDtl.size();j++){
dtls=new HashMap();
dtls=(HashMap)imagelistDtl.get(j);
String typ=util.nvl((String)dtls.get("TYP"));
String allowon=util.nvl((String)dtls.get("ALLOWON"));
if(typ.equals("V") && (allowon.indexOf("SRCH") > -1 || allowon.indexOf("PKT") > -1)){
String path=util.nvl((String)dtls.get("PATH"));
String gtCol=util.nvl((String)dtls.get("GTCOL"));
String val=util.nvl((String)pktDtl.get(gtCol));
if(!val.equals("N")){
if(val.indexOf(".js") <=-1 || val.indexOf(".mov") > -1){
counter++;
path=path+val;
path=path.replaceAll("SPECIAL", ".mov");
path=path.replaceAll("imaged", "videos");
%>
<div>
<button id="value(pb_mail)" class="submit" name="value(pb_mail)" accesskey="S" onclick="return SendMailimages('PacketLookup.do?method=mailExcelMass');" type="button">Send Mail</button>
</div>
<div style="margin-left:200px;">
<div style="float:left;">
<%if(val.indexOf(".html") ==-1){%>
<input type="checkbox" id="counter_<%=counter%>" name="counter_<%=counter%>" checked="checked" value="<%=path%>" title="Check To Attached For mail">
<%}%>
<a href="<%=path%>" target="_blank">
Click To View</a>
<%if(val.indexOf(".html") ==-1){%>

<img src="../images/download.jpg" onclick="newWindow('<%=info.getReqUrl()%>/contact/nmedocs.do?method=download&dwusingurl=Y&path=<%=path%>')" width="20px" height="20px" title="Click Here To Download" />
<%}%>
</div>
<div style="float:left; padding:5px">
<object classid='clsid:02BF25D5-8C17-4B23-BC80-D3488ABDDC6B' style="margin-left:40px;"  
    width="700" height="392" codebase='http://www.apple.com/qtactivex/qtplugin.cab'>
    <param name='src' value="<%=path%>">
    <param name='autoplay' value="false">
    <param name='controller' value="true">
    <param name='loop' value="false">
    <embed src="<%=path%>" width="700" height="392" 
    scale="tofit" autoplay="false" 
    controller="true" loop="false" bgcolor="#000000" 
    pluginspage='http://www.apple.com/quicktime/download/'>
    </embed>
    </object>
</div>
</div>
<%}else{
String imgPath = (String)dbinfo.get("IMG_PATH");
String videoFoundUrl = imgPath+"XraySample/video.jpg";
String serPathVid = "";
if(val.equals("3.js")){
serPathVid = imgPath+"viewer.html?d="+vnm+"&sm=1&sd=50&sv=0,1,2,3,4&z=1&v=3";
}else if(val.equals("2.js")) {
serPathVid = imgPath+"viewer.html?d="+vnm+"&sm=1&sd=50&sv=0,1,2,3,4&z=1&v=2";
}else if(val.equals("1.js")) {
serPathVid = imgPath+"viewer.html?d="+vnm+"&sm=1&sd=50&sv=0,1,2,3,4&z=1&v=1";
}else if(val.equals("0.js")) {
serPathVid = imgPath+"viewer.html?d="+vnm+"&sm=1&sd=50&sv=0,1,2,3,4&z=1&v=1";
}else if(val.indexOf("json") > -1) {
String conQ=vnm;
if(val.indexOf("/") > -1){
conQ=val.substring(0,val.indexOf("/"));
}
serPathVid = imgPath+"Vision360.html?sd=50&z=1&v=2&sv=0&d="+conQ;
}else {
serPathVid = "";
}
System.out.println(serPathVid);
%>
<div>
<!--<iframe src="<%=serPathVid%>" height="700px" width="750px" style="border:none;">
</iframe>-->
<a href="<%=serPathVid%>" target="_blank">
Click To View</a>
</div>
<%}}}}}%>



</td>
</tr>
</table>
</div>
<div id="CERT" style="display:none;"  class="pktDtl">
<%
if(imagelistDtl!=null && imagelistDtl.size() >0){
for(int j=0;j<imagelistDtl.size();j++){
dtls=new HashMap();
dtls=(HashMap)imagelistDtl.get(j);
String typ=util.nvl((String)dtls.get("TYP"));
String allowon=util.nvl((String)dtls.get("ALLOWON"));
if(typ.equals("C") && (allowon.indexOf("SRCH") > -1 || allowon.indexOf("PKT") > -1)){
String path=util.nvl((String)dtls.get("PATH"));
String gtCol=util.nvl((String)dtls.get("GTCOL"));
String val=util.nvl((String)pktDtl.get(gtCol));
if(!val.equals("N")){
counter++;
path=path+val;
if (path.indexOf("&") > -1) {
path = path.replaceAll("&", "%26");
}
%>
<table>
<%
%>
<tr>
<td valign="top">
<input type="checkbox" id="counter_<%=counter%>" name="counter_<%=counter%>" checked="checked"  value="<%=path%>" title="Check To Attached For mail">
<a href="<%=path%>" target="_blank">
Click To View</a>


<img src="../images/download.jpg" onclick="newWindow('<%=info.getReqUrl()%>/contact/nmedocs.do?method=download&dwusingurl=Y&path=<%=path%>')" width="20px" height="20px" title="Click Here To Download" />

</td>
<%if(val.indexOf(".pdf") < 0){%>
<td>
<div style="margin-left:150px">
<a href="<%=path%>" target="_blank">
<img src="<%=path%>" width="400px" height="500px" ></a>
</div>
</td>
<%}%>
</tr></table>
<%}}%>
<%}}%>
</div>
<div id="DNA" style="display:none;" class="pktDtl">
</div>
</td>
</tr>
</table>
<%}else{%>
 <table>
 <tr>
 <td class="tdLayout"> Sorry No Result Found</td>
 </tr>
 </table>
 
 <%}%>
  
  </body>
</html>