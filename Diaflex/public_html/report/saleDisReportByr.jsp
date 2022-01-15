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
  
  <title>Sale Discount Report Buyerwise</title>
  <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/bse.js?v=<%=info.getJsVersion()%> " > </script>
  <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/bse.css?v=<%=info.getJsVersion()%>"/>
  <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/ajax.js?v=<%=info.getJsVersion()%> " > </script>
  <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/style.css?v=<%=info.getJsVersion()%>"/>
  <script language="javaScript" type="text/javascript" src="<%=info.getReqUrl()%>/calendar/calendar.js"></script>
   <link href="<%=info.getReqUrl()%>/calendar/calendar.css" rel="stylesheet" type="text/css" />
   <script src="<%=info.getReqUrl()%>/PopScripts/jquery-1.2.6.min.js" type="text/javascript"></script>
   <script type="text/javascript" src="<%=info.getReqUrl()%>/jqueryScript/jquery.js " > </script>
      <script type="text/javascript" src="<%=info.getReqUrl()%>/chartscript/amcharts.js?v=<%=info.getJsVersion()%> " > </script>
<script type="text/javascript" src="<%=info.getReqUrl()%>/chartscript/chartajax.js?v=<%=info.getJsVersion()%> " > </script>
<script type="text/javascript" src="<%=info.getReqUrl()%>/chartscript/pie.js?v=<%=info.getJsVersion()%> " > </script>
<script type="text/javascript" src="<%=info.getReqUrl()%>/chartscript/serial.js?v=<%=info.getJsVersion()%> " > </script>
 <script language="javaScript" type="text/javascript" src="<%=info.getReqUrl()%>/jquery/jquery.freezeheader.js?v=<%=info.getJsVersion()%>"></script>
  <script type="text/javascript">
   $(document).ready(function () {
	    $("#table2").freezeHeader({ 'height': '350px' });
    })
 </script>
  </head>
  <%
  ArrayList pktList = ((ArrayList)session.getAttribute("pktListBYR")== null)?new ArrayList():(ArrayList)session.getAttribute("pktListBYR");
  ArrayList itemHdr = ((ArrayList)session.getAttribute("itemHdrBYR")== null)?new ArrayList():(ArrayList)session.getAttribute("itemHdrBYR");
  %>
  
<body topmargin="0" leftmargin="0" rightmargin="0" bottommargin="0">
<input type="hidden" id="reqUrl" name="reqUrl" value="<%=info.getReqUrl()%>"/>
<table width="100%"  border="0" align="center" cellpadding="0" cellspacing="0" >

<% if(pktList.size()>0) { %>

<tr>
<td class="hedPg">
Create Excel <img src="../images/ico_file_excel.png" onclick="goTo('orclRptAction.do?method=createXLSaleDsc&typ=BYR','','')" border="0"/>
</td>
</tr>
<tr>
<td class="hedPg">
<table class="grid1"  id="table2">
<thead>
<tr>
<%for(int i=0;i<itemHdr.size();i++){%>
<th><%=itemHdr.get(i)%></th>
<%}%>
</tr>
</thead>
 <tbody>	
<%for(int i=0;i<pktList.size();i++){
HashMap pktPrpMap=(HashMap)pktList.get(i);
%>
<tr>
<%for(int j=0;j<itemHdr.size();j++){
String itm = (String)itemHdr.get(j);
%>
<td align="right"><%=util.nvl((String)pktPrpMap.get(itm))%></td>
<%} %>
</tr>
<% } %>
</tbody>
</table>
</td>
</tr>

<% } else { %>

<tr><td class="hedPg">Sorry no result found</td></tr>

<% } %>

</table>
</body>
</html>
  
  