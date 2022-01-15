<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page errorPage="../error_page.jsp" %>
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import="java.util.ArrayList,java.util.ArrayList,java.util.HashMap, java.sql.*, java.util.Enumeration, java.util.logging.Level"%>
<jsp:useBean id="info" class="ft.com.InfoMgr" scope="session" />
<jsp:useBean id="util" class="ft.com.JspUtil" scope="page" />
<%@taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>

<title>Shape Clarity Report</title>  
<link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/bse.css?v=<%=info.getJsVersion()%>"/>
<link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/style.css?v=<%=info.getJsVersion()%>"/>
<link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/general.css?v=<%=info.getJsVersion()%>"/>
<script src="<%=info.getReqUrl()%>/PopScripts/jquery-1.2.6.min.js" type="text/javascript"></script>
<script type="text/javascript" src="<%=info.getReqUrl()%>/chartscript/amcharts.js?v=<%=info.getJsVersion()%> " > </script>
<script type="text/javascript" src="<%=info.getReqUrl()%>/chartscript/chartajax.js?v=<%=info.getJsVersion()%> " > </script>
<script type="text/javascript" src="<%=info.getReqUrl()%>/chartscript/serial.js?v=<%=info.getJsVersion()%> " > </script>
<script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/bse.js?v=<%=info.getJsVersion()%> " > </script>
</head>
        <%String logId=String.valueOf(info.getLogId());
        String onfocus="cook('"+logId+"')";
        %>
 <body onfocus="<%=onfocus%>" onload="MM_preloadImages('images/faunalogo.png')" topmargin="0" leftmargin="0" rightmargin="0" bottommargin="0">
    <table width="100%"  border="0" align="center" cellpadding="0" cellspacing="0" >
  <tr>
    <td height="5" valign="top" class="greyline"><img src="../images/grey_linebig.png" width="685" height="5" align="right" /></td>
  </tr>
  <tr>
  <td valign="top" class="hedPg">
  <table cellpadding="1" cellspacing="5"><tr><td valign="middle">
  <img src="../images/line.jpg" border="0" /></td><td valign="middle"> 
  <span class="pgHed">Shape Clarity Report
  </span></td></tr></table>
  </td></tr></table>
<%
HashMap dbinfo = info.getDmbsInfoLst();
 HashMap prp = info.getSrchPrp();
  HashMap dataDtl= (HashMap)session.getAttribute("dataDtl");
  ArrayList statusLst= (ArrayList)session.getAttribute("statusLst");
  String key="",qty="",avg="",imageMap="";;
  if(dataDtl!=null && dataDtl.size()>0){
  ArrayList szLst= (ArrayList)dataDtl.get("SIZE");
  ArrayList grpLst= (ArrayList)dataDtl.get("GRP");
  ArrayList shapeLst= (ArrayList)dataDtl.get("SHAPE");
  ArrayList srchids= (ArrayList)request.getAttribute("srchids");
  String dsc=util.nvl((String)request.getAttribute("srchDscription"));
  String url="ajaxRptAction.do?method=shapeclarityBar";
  String style="width: 50%; height: 362px; float:left;";
  if(szLst!=null && szLst.size()>0){
  %>
  <table><tr><td valign="top" class="hedPg"><span class="txtLink" >Search Description : <%= dsc%></span>
  <td></tr></table>
  <%for(int i=0;i<statusLst.size();i++){
  String stt=util.nvl((String)statusLst.get(i));%>
  <div class="hedPg"><b><%=stt%></b>
  <%for(int j=0;j<szLst.size();j++){
  String szVal=util.nvl((String)szLst.get(j));
  for(int k=0;k<grpLst.size();k++){
  String grp=util.nvl((String)grpLst.get(k));
  key=stt+"@"+szVal+"@"+grp;
  String styleId=key+"@HIDD";
  String sizedsc=util.nvl((String)dataDtl.get(szVal));
  sizedsc=sizedsc+" "+grp;
  String styleIdTTl=key+"@TTL";
  %>
  <div>
  <input type="hidden" id="<%=styleId%>" value="<%=key%>">
  <input type="hidden" id="<%=styleIdTTl%>" value="<%=sizedsc%>">
  <div id="<%=key%>" style="<%=style%>">
  </div>
  </div>
   <%}}%> 
   </div>
   <%}%>
   <script type="text/javascript">
 <!--
$(window).bind("load", function() {
callrptshapeclarityBar('<%=url%>','','50','362');
});
 -->
</script> 
  <%}}else{%>
    <table><tr><td valign="top" class="hedPg">
    Sorry No Result Found</td></tr> </table>
    <%}%>

  </body>
</html>