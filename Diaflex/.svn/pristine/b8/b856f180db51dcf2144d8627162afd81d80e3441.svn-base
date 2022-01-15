<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page errorPage="../error_page.jsp" %>
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import="java.util.ArrayList,java.util.HashMap,java.sql.ResultSet,ft.com.*, java.util.Date, java.util.ArrayList, ft.com.dao.*"%>
<jsp:useBean id="util" class="ft.com.JspUtil" scope="page" />
<jsp:useBean id="info" class="ft.com.InfoMgr" scope="session" />
<%@taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>
<%@taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>File Upload</title>
  <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/bse.css?v=<%=info.getJsVersion()%>"/>
    <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/style.css?v=<%=info.getJsVersion()%>"/>
     <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/bse.js?v=<%=info.getJsVersion()%> " > </script>
    
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
    <td height="5" valign="top" class="greyline"><img src="<%=info.getReqUrl()%>/images/grey_linebig.png" width="685" height="5" align="right" /></td>
  </tr>
  <tr>
  <td valign="top" class="hedPg">
  <table cellpadding="1" cellspacing="5"><tr><td valign="middle">
  <img src="../images/line.jpg" border="0" /></td><td valign="middle"> <span class="pgHed">File Load Status</span> </td>
   </tr></table>
  </td>
  </tr>
  <tr><td class="tdLayout" valign="top">
    <html:form action="fileloaders/uploadFile.do?method=save" method="post">
  <table>
  <tr><td>Sequence Number: </td><td><html:text property="value(seqNum)"  name="fileUploadForm" /> </td> 
  <td><html:submit property="value(sttRt)" styleClass="submit" value="Status Report" /> </td>
  <td><html:submit property="value(errRt)" styleClass="submit" value="Error Report" /> </td>
  </tr>
  </table></html:form>
  </td></tr>
   <tr><td class="tdLayout" valign="top">
   <%
   String sttRT = util.nvl((String)request.getAttribute("STTRT"));
    ArrayList pktDtlList = (ArrayList)request.getAttribute("pktDtlList");
   String view = util.nvl((String)request.getAttribute("View"));
  if(!view.equals("")){
  
     if(!sttRT.equals("")){
      if(pktDtlList !=null && pktDtlList.size() > 0){
   %>
   
   <table class="grid1"> <tr><th>STG</th><th> MINDTE </th><th> MAXDTE</th><th>MIN</th> </tr>
  <% for(int i=0;i < pktDtlList.size() ;i++){
   HashMap pktDtl = (HashMap)pktDtlList.get(i);
   %>
   <tr>
   <td><%=pktDtl.get("stg")%></td><td><%=pktDtl.get("minDte")%> </td> <td><%=pktDtl.get("maxDte")%></td>
    <td><%=pktDtl.get("min")%> </td>
   </tr>
   <%}%>
   </table>
   <%}else{%>
    Sorry no result found.
   <%}%>
   <%}else{
     if(pktDtlList !=null && pktDtlList.size() > 0){
   %>
   <table class="grid1"> <tr><th>Packet</th><th>Error </th> </tr>
  <% 
  
  for(int i=0;i < pktDtlList.size() ;i++){
   HashMap pktDtl = (HashMap)pktDtlList.get(i);
   %>
   <tr>
   <td><%=pktDtl.get("vnm")%></td><td><%=pktDtl.get("rem")%> </td> 
   
   </tr>
   <%}%>
   </table>
   <%}else{%>
   No error in this Report.
   <%}}}%>
  
   </td></tr>
   <tr>
    <td><jsp:include page="/footer.jsp" /> </td>
    </tr>
  </table>
  
  
  </body>
</html>