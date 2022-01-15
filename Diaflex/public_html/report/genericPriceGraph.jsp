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

<title>Price Graph Report</title>  
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
  <span class="pgHed">Price Graph Report
  </span></td></tr></table>
  </td></tr></table>
<%
HashMap dbinfo = info.getDmbsInfoLst();
 HashMap prp = info.getSrchPrp();
  HashMap dataDtl= (HashMap)session.getAttribute("dataDtl");
  ArrayList statusLst= (ArrayList)session.getAttribute("statusLst");
  String key="",qty="",avg="",imageMap="";;
  if(dataDtl!=null && dataDtl.size()>0){
  ArrayList grpLst= (ArrayList)dataDtl.get("GRP");
  ArrayList shapeLst= (ArrayList)dataDtl.get("SHAPE");
  ArrayList srchids= (ArrayList)request.getAttribute("srchids");
  String dsc=util.nvl((String)request.getAttribute("srchDscription"));
  String url="ajaxRptAction.do?method=pricelinegraph";
  String style="width: 51%; height: 400px; float:left;";
  if(shapeLst!=null && shapeLst.size()>0){
  %>
  <table><tr><td valign="top" class="hedPg"><span class="txtLink" >Search Description : <%= dsc%></span>
  </td></tr></table>
  <%for(int i=0;i<statusLst.size();i++){
  String stt=util.nvl((String)statusLst.get(i));%>
  <%for(int j=0;j<shapeLst.size();j++){
  String shVal=util.nvl((String)shapeLst.get(j));
  key=stt+"@"+shVal;
  String styleId=key+"@HIDD";
  String styleIdTTl=key+"@TTL";
  %>
  <input type="hidden" id="<%=key%>@VLUTTL" value="AVG">
  <input type="hidden" id="<%=styleId%>" value="<%=key%>">
  <input type="hidden" id="<%=styleIdTTl%>" value="<%=stt%> <%=shVal%>">
  <div id="<%=key%>" style="<%=style%>">
  </div>
   <%}}
    String lineGrp=grpLst.toString();
    lineGrp = lineGrp.replaceAll("\\[", "");
    lineGrp = lineGrp.replaceAll("\\]", "");
    lineGrp = lineGrp.replaceAll("\\,", "_");%>
    <input type="hidden" id="lineGrp" value="<%=lineGrp%>">
<script type="text/javascript">
 <!--
$(window).bind("load", function() {
pricelinegraph('<%=url%>',' Price Graph','50','362');
});
 -->
</script> 
  <%}}else{%>
    <table><tr><td valign="top" class="hedPg">
    Sorry No Result Found</td></tr>
    <%}%>
 </table>
  </body>
</html>