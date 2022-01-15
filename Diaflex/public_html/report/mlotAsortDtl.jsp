<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page errorPage="../error_page.jsp" %>
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import="java.util.ArrayList,java.util.ArrayList,java.util.HashMap, java.sql.*, java.util.Enumeration, java.util.logging.Level"%>
<jsp:useBean id="info" class="ft.com.InfoMgr" scope="session" />
<jsp:useBean id="util" class="ft.com.JspUtil" scope="page" />
<%@taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%  ArrayList prpDspBlocked = info.getPageblockList();%>
<html>
  <head>
 <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
  <title>Offer Reports</title>
  <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/bse.js?v=<%=info.getJsVersion()%> " > </script>
  <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/bse.css?v=<%=info.getJsVersion()%>"/>
  <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/ajax.js?v=<%=info.getJsVersion()%> " > </script>
  <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/style.css?v=<%=info.getJsVersion()%>"/>
  </head>
       <%String logId=String.valueOf(info.getLogId());
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
  <img src="../images/line.jpg" border="0" /></td><td valign="middle"> <span class="pgHed">
 Offer Report
 </span> </td>
</tr></table>
  </td>
  </tr>
  <tr>
  <td valign="top" class="hedPg">
  <% HashMap mgfDtl = (HashMap)request.getAttribute("ASSORTDTL"); 
  if(mgfDtl!=null && mgfDtl.size()>0){
  ArrayList mfgDtlList = (ArrayList)mgfDtl.get("MFGLIST");
 
  %>
  <table>
  <tr><td><b>MFG Detail </b></td><td></td> <td><b>Final Assort Details</b> </td></tr>
  <tr><td valign="top">
  <%if(mfgDtlList!=null && mfgDtlList.size()>0){%>
  <table class="grid1"><tr><th>Shape</th><th>Size</th><th>Cts</th><th>Cp</th><th>Cp Value</th></tr>
  <%for(int i=0;i<mfgDtlList.size();i++){
  HashMap pktDtl = (HashMap)mfgDtlList.get(i);
  %>
  <tr>
  <td><%=pktDtl.get("sh")%></td> <td><%=pktDtl.get("sz")%></td> <td><%=pktDtl.get("cts")%></td>
  <td><%=pktDtl.get("cp")%></td><td><%=pktDtl.get("cpVlu")%></td>
  </tr>
  <%}%>
 
   <tr>
  <td colspan="2" align="right"><B>Grand Total</b></td>
  <td><b> <%=mgfDtl.get("TTLCTS")%> </b></td><td><b><%=mgfDtl.get("TTLCP")%> </b></td>
  <td><b> <%=mgfDtl.get("TTLCPVLU")%></b></td>
  </tr></table>
  <%}%>
  </td>
  <td></td>
  <td valign="top">
   <%
    ArrayList FNLLIST = (ArrayList)mgfDtl.get("FNLLIST");
   if(FNLLIST!=null && FNLLIST.size()>0){
   
   %>
  <table class="grid1"><tr><th>Shape</th><th>Size</th><th>Mix Clarity</th><th>Cts</th><th>Cp</th><th>Cp Value</th></tr>
  <%for(int i=0;i<FNLLIST.size();i++){
  HashMap pktDtl = (HashMap)FNLLIST.get(i);
  %>
  <tr>
  <td><%=pktDtl.get("sh")%></td> <td><%=pktDtl.get("sz")%></td> <td><%=pktDtl.get("clr")%></td> <td><%=pktDtl.get("cts")%></td>
  <td><%=pktDtl.get("cp")%></td><td><%=pktDtl.get("cpVlu")%></td>
  </tr>
  <%}%>
  <tr>
  <td colspan="3" align="right"><B>Grand Total</b></td>
  <td><b><%=mgfDtl.get("FNLTTLCTS")%> </b></td><td><b><%=mgfDtl.get("FNLTTLCP")%></b> </td><td><b> <%=mgfDtl.get("FNLTTLCPVLU")%></b></td>
  </tr>
  </table>
  
  <%}%>
  
  
  </td></tr>
  </table>
  <%}%>
  </td></tr></table>
    
    
    
    </body>
</html>