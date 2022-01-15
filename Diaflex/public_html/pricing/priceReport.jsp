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
  
  <title>Pricing Reports</title>
  <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/bse.js?v=<%=info.getJsVersion()%> " > </script>
<link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/bse.css?v=<%=info.getJsVersion()%>"/>
<script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/ajax.js?v=<%=info.getJsVersion()%> " > </script>
  

 
      <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/style.css?v=<%=info.getJsVersion()%>"/>
  </head>
    <body onload="MM_preloadImages('images/faunalogo.png')" topmargin="0" leftmargin="0" rightmargin="0" bottommargin="0">
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
  REPRICING REPORT
 </span> </td>
</tr></table>
  </td>
  </tr>
  <tr><td valign="top" class="hedPg"> 
  
  <%
  String isPen = "";
  String isApp ="";
  String stt = util.nvl(request.getParameter("stt"));
  if(stt.equals("Approved"))
   isApp="checked=\"checked\"";
   else
   isPen="checked=\"checked\"";
  %>
  <input type="radio" name="prcStt" value="Pending" <%=isPen%>  onclick="goTo('rePrice.do?parameter=loadRT&stt=Pending')" />Pending &nbsp;&nbsp;
  <input type="radio" name="prcStt" value="Approved" <%=isApp%> onclick="goTo('rePrice.do?parameter=loadRT&stt=Approved')"/>Approved &nbsp;&nbsp;
  
  
  </td></tr>
  <tr><td valign="top" class="hedPg">
  <%
  ArrayList pktList = (ArrayList)request.getAttribute("pktList");
  if(pktList!=null && pktList.size()>0){%>
  <table class="grid1"><tr><th>Pri Seq </th><th>Status</th> <th>Count </th><th>From Date</th>
  <th>To Date</th>
  </tr>
  <%for(int i=0;i<pktList.size();i++){
  HashMap pktDtl = (HashMap)pktList.get(i);
  String seq =util.nvl((String)pktDtl.get("seq"));
  String url = "rePrice.do?parameter=status&lseq="+seq;
  String appValUrl = "priceChanges.do?method=fetchPC&lseq="+seq ;
  %>
  <tr><td> <A href="<%=url%>" target="_blank"><%=seq%></a> </td>
  <td> <A href="<%=appValUrl%>" target="_self"><%=util.nvl((String)pktDtl.get("stt"))%></a> </td>
  <td><%=util.nvl((String)pktDtl.get("cnt"))%></td>
  <td><%=util.nvl((String)pktDtl.get("FrmDte"))%></td><td><%=util.nvl((String)pktDtl.get("ToDte"))%></td>
  </tr>
  <%}%></table>
  <%}%>
  </td></tr></table>
  
  
  </body>
</html>