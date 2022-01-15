<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page errorPage="../error_page.jsp" %>
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import="java.util.ArrayList,java.util.ArrayList,java.util.HashMap, java.sql.*, java.util.Enumeration, java.util.logging.Level"%>
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
  <title>Delivery Summary Report</title>
 
  </head>
        <%String logId=String.valueOf(info.getLogId());
        String onfocus="cook('"+logId+"')";
        HashMap dbinfo = info.getDmbsInfoLst();
        String cnt = (String)dbinfo.get("CNT");
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
  <img src="../images/line.jpg" border="0" /></td><td valign="middle"> <span class="pgHed">Delivery Summary Report</span> </td>
</tr></table>
  </td>
  </tr>
<tr><td valign="top" class="hedPg">
<html:form action="/report/orclRptAction.do?method=fetchdlvsummaryrpt"  method="POST">
  <table>
  <tr>
<td>Buyer Name </td>
<td>
 <html:select property="value(byrIdn)" name="orclReportForm" styleId="byrId" >
    <html:option value="">---select---</html:option>
    <html:optionsCollection property="byrList" name="orclReportForm"  value="byrIdn" label="byrNme" />
                
  </html:select>
</td>
  <td>Branch</td>
  <td>
<html:select property="value(typ)" styleId="typ">
<html:option value="" >Select</html:option>
<html:option value="INCLUDE">INCLUDE</html:option>
<html:option value="EXCLUDE">EXCLUDE</html:option>
</html:select>
  </td>
<td>Date From : </td><td>
    <html:text property="value(dtefr)" styleClass="txtStyle"  styleId="dtefr"  size="12"  /> <a href="#" style="margin-top:5px;"  onClick="setYears(1900, '<%=info.getCurrentYear()%>' );showCalender(this, 'dtefr');">
                <img src="<%=info.getReqUrl()%>/images/calender.png"></a></td>
                <td>Date To : </td><td>
    <html:text property="value(dteto)" styleClass="txtStyle"  styleId="dteto"  size="12"  /> <a href="#"  onClick="setYears(1900, '<%=info.getCurrentYear()%>' );showCalender(this, 'dteto');">
                <img src="<%=info.getReqUrl()%>/images/calender.png"></a></td>
    <td><html:submit property="value(srch)" value="fetch" styleClass="submit" /></td>
   </tr>
   </table>
</html:form>
</td>
</tr>
  <%String view= util.nvl((String)request.getAttribute("View"));
    if(!view.equals("")){
      ArrayList pktDtlList = (ArrayList)session.getAttribute("pktList");
  if(pktDtlList!=null && pktDtlList.size()>1){
  ArrayList itemHdr = (ArrayList)session.getAttribute("itemHdr");
    ArrayList itemHdrbyr = (ArrayList)session.getAttribute("itemHdrbyr");
    ArrayList byrDtlList = (ArrayList)session.getAttribute("byrDtlList");
    %>
    <tr>
    <td nowrap="nowrap">
    <table>
     <tr><td valign="top" class="hedPg">   Create Excel &nbsp;&nbsp;&nbsp;
  <img src="../images/ico_file_excel.png" border="0" onclick="goTo('<%=info.getReqUrl()%>/report/orclRptAction.do?method=createXLdlvSummary','','')"/>
  
  <%if(cnt.equals("kj")){ %>
     &nbsp;&nbsp;&nbsp; PacketList &nbsp;&nbsp;&nbsp;<img src="../images/ico_file_excel.png" border="0" onclick="goTo('<%=info.getReqUrl()%>/report/orclRptAction.do?method=createXLdlvSummary&packet=Y','','')"/>
     <%} %>
     
 </td></tr>
  <tr><td valign="top" class="hedPg">
  <table class="grid1">
  <thead>
  <tr>
  <td colspan="<%=itemHdr.size()%>" align="center">Delivery Id Wise Summary</td>
  </tr>
  <tr>
  <%for(int i=0 ; i < itemHdr.size() ;i++){%>
  <th><%=itemHdr.get(i)%></th>
  <%}%>
  </tr>
  </thead>
  <tbody>
  <%for(int j=0 ; j <pktDtlList.size() ;j++){
  HashMap pktDtl = (HashMap)pktDtlList.get(j);
  %>
  <tr>
  <%for(int k=0;k<itemHdr.size() ;k++){
  String hdr = (String)itemHdr.get(k);
  
  %>
  <td nowrap="nowrap" align="right"><%=util.nvl((String)pktDtl.get(hdr))%></td>
  <%}%>
  </tr>
  <%}%>
  </tbody>
  </table></td></tr>
  </table>
    <table>
     <tr><td valign="top" class="hedPg">   Create Excel &nbsp;&nbsp;&nbsp;
  <img src="../images/ico_file_excel.png" border="0" onclick="goTo('<%=info.getReqUrl()%>/report/orclRptAction.do?method=createXLdlvSummary&byr=Y','','')"/>
 </td></tr>
  <tr><td valign="top" class="hedPg">
  <table class="grid1">
  <thead>
  
    <tr>
  <td colspan="<%=itemHdrbyr.size()%>" align="center">Buyer Wise Summary</td>
  </tr>
  <tr>
  <%for(int i=0 ; i < itemHdrbyr.size() ;i++){%>
  <th><%=itemHdrbyr.get(i)%></th>
  <%}%>
  </tr>
  </thead>
  <tbody>
  <%for(int j=0 ; j <byrDtlList.size() ;j++){
  HashMap pktDtl = (HashMap)byrDtlList.get(j);
  %>
  <tr>
  <%for(int k=0;k<itemHdrbyr.size() ;k++){
  String hdr = (String)itemHdrbyr.get(k);
  
  %>
  <td nowrap="nowrap" align="right"><%=util.nvl((String)pktDtl.get(hdr))%></td>
  <%}%>
  </tr>
  <%}%>
  </tbody>
  </table></td></tr>
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
    <%@include file="../calendar.jsp"%>
</html>