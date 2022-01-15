<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page errorPage="../error_page.jsp" %>
<%@ page contentType="text/html;charset=windows-1252"%>
<jsp:useBean id="info" class="ft.com.InfoMgr" scope="session" />
<jsp:useBean id="util" class="ft.com.JspUtil" scope="page" />
<%@ page import="java.util.ArrayList,java.util.HashMap,java.sql.ResultSet,ft.com.*,, java.util.Collections, java.util.Date, java.util.ArrayList, ft.com.dao.*"%>
<%@taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
 
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>Sale Day Terms Wise</title>
  <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/bse.js?v=<%=info.getJsVersion()%> " > </script>
 <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/bse.css?v=<%=info.getJsVersion()%>"/>
 <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/ajax.js?v=<%=info.getJsVersion()%> " > </script>
  <script language="javaScript" type="text/javascript" src="<%=info.getReqUrl()%>/calendar/calendar.js"></script>
   <link href="<%=info.getReqUrl()%>/calendar/calendar.css" rel="stylesheet" type="text/css">
    <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/style.css?v=<%=info.getJsVersion()%>"/>
  </head>
        <%String logId=String.valueOf(info.getLogId());
        String onfocus="cook('"+logId+"')";
        %>
 <body onfocus="<%=onfocus%>" onload="MM_preloadImages('images/faunalogo.png')" topmargin="0" leftmargin="0" rightmargin="0" bottommargin="0">
<table width="100%"   align="center" cellpadding="0" cellspacing="0" class="mainbg">
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
  <img src="../images/line.jpg" border="0" /></td><td valign="middle"> <span class="pgHed">Sale Day Terms Wise</span> </td>
</tr></table>
  </td>
  </tr>
  <tr><td valign="top" class="hedPg">
  <html:form action="report/orclRptAction.do?method=fetchsaletermswise" method="post">
  <table class="grid1">
  <tr><th colspan="2">Search</th></tr>
         <tr>
<td>Packet Type :</td>
<td>
 <html:select property="value(pkt_ty)" name="orclReportForm" styleId="pkt_ty" >
    <html:option value="">select</html:option>
      <html:option value="NR"> Single </html:option>
      <html:option value="SMX"> Smx </html:option>
      <html:option value="MIX"> Mix </html:option>
</html:select>
               
</td>
</tr> 
<tr><td>Sale Date : </td>  
       <td><html:text property="value(issfrmdte)" styleId="issfrmdte" name="orclReportForm" size="8" /><img src="<%=info.getReqUrl()%>/images/calender.png" onClick="setYears(2011, <%=info.getCurrentYear()%>);showCalender(this, 'issfrmdte');"> 
       To&nbsp; <html:text property="value(isstodte)" styleId="isstodte" name="orclReportForm"  size="8"/> <img src="<%=info.getReqUrl()%>/images/calender.png" onClick="setYears(2011, <%=info.getCurrentYear()%>);showCalender(this, 'isstodte');"></td>
</tr>
<tr>
 <td nowrap="nowrap"><span class="txtBold" >Sale Person : </span></td>
 <td>
      <%
      ArrayList salepersonList = ((ArrayList)info.getSaleperson() == null)?new ArrayList():(ArrayList)info.getSaleperson();
      %>
      <html:select name="orclReportForm" property="value(empIdn)" styleId="saleEmp">
      <html:option value="0">---Select---</html:option>
      <%
      for(int i=0;i<salepersonList.size();i++)
      {
      ArrayList saleperson=(ArrayList)salepersonList.get(i);
      %>
      <html:option value="<%=(String)saleperson.get(0)%>"> <%=(String)saleperson.get(1)%> </html:option>
      <%
      }
      %>
      </html:select>
      
      </td></tr>
      <tr>
        <td>Buyer</td>
      <td nowrap="nowrap">
   
    <%
    String sbUrl ="ajaxRptAction.do?method=buyerSugg";
    %>
    <input type="text" id="nme" name="nme" autocomplete="off" class="sugBox"
      onKeyUp="doCompletion('nme', 'nmePop', 'ajaxRptAction.do?method=buyerSugg', event)" 
      onKeyDown="setDown('nmePop', 'nmeID', 'nme',event)"/>
 <img src="../images/addrow.jpg" width="17" height="17" title="Click To get Names" onclick="doCompletionMobile('nme', 'nmePop', 'ajaxRptAction.do?method=buyerSugg')">
 <input type="hidden" name="nmeID" id="nmeID" value="">
  <div style="position: absolute;">
      <select id="nmePop" name="nmePop"
        style="display:none;300px;" 
        class="sugBoxDiv"
        onKeyDown="setDown('nmePop', 'nmeID', 'nme',event)" 
        onDblClick="setVal('nmePop', 'nmeID', 'nme', event);hideObj('nmePop')" 
        onClick="setVal('nmePop', 'nmeID', 'nme', event);" 
        size="10">
      </select>
</div>
  </td></tr>
<tr><td align="center" colspan="2">
<html:submit property="value(submit)" styleClass="submit" value="Submit" />
</td>
</tr>

  </table>
  
    </html:form>
   </td></tr>
 
 <% 
    String view  = (String)request.getAttribute("view");
   if(view!=null){
ArrayList dayssaletermLst= (request.getAttribute("dayssaletermLst") == null)?new ArrayList():(ArrayList)request.getAttribute("dayssaletermLst");
ArrayList termsaletermLst= (request.getAttribute("termsaletermLst") == null)?new ArrayList():(ArrayList)request.getAttribute("termsaletermLst");
int dayssaletermLstsz=dayssaletermLst.size();
int termsaletermLstsz=termsaletermLst.size();
HashMap datasaletermLst= (request.getAttribute("datasaletermLst") == null)?new HashMap():(HashMap)request.getAttribute("datasaletermLst");
if(dayssaletermLstsz>0){
%>
<tr>
<td valign="top" class="hedPg">
<table class="grid1">
<tr>
<th>Days Group/Terms</th>
<%for(int i=0;i<termsaletermLstsz;i++){%>
<th><%=util.nvl((String)termsaletermLst.get(i))%></th>
<%}%>
<th>Total</th>
</tr>

<%for(int i=0;i<dayssaletermLstsz;i++){
String days=util.nvl((String)dayssaletermLst.get(i));
%>
<tr>
<th><%=days%></th>
<%for(int j=0;j<termsaletermLstsz;j++){
String term=util.nvl((String)termsaletermLst.get(j));
%>
<td align="right" title="CTS*SALE_RTE/1000000"><a href="orclRptAction.do?method=fetchsaletermswisebyr&grp=<%=days%>&terms=<%=term%>" target="fix"><%=util.nvl((String)datasaletermLst.get(days+"_"+term))%></a></td>
<%}%>
<td align="right"><a href="orclRptAction.do?method=fetchsaletermswisebyr&grp=<%=days%>" target="fix"><%=datasaletermLst.get(days+"_TTL")%></a></td>
</tr>
<%}%>

<tr>
<th>Total</th>
<%for(int j=0;j<termsaletermLstsz;j++){
String term=util.nvl((String)termsaletermLst.get(j));%>
<td align="right"><a href="orclRptAction.do?method=fetchsaletermswisebyr&terms=<%=term%>" target="fix"><%=datasaletermLst.get(term+"_TTL")%></a></td>
<%}%>
<td align="right"><a href="orclRptAction.do?method=fetchsaletermswisebyr" target="fix"><b><%=datasaletermLst.get("TOTAL_TTL")%></b></a></td>
</tr>

</table>
</td>
</tr>

<%}else{%>
<tr><td  valign="top" class="hedPg">Sorry no result found.</td></tr>
<%}}%>
  
  <tr>
    <td><jsp:include page="/footer.jsp" /> </td>
    </tr>
  </table>

  </body>
    <%@include file="../calendar.jsp"%>
</html>