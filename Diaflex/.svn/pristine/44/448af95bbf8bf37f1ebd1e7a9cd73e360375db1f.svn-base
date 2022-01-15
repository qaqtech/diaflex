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
    <title>stockTallyList</title>
    <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/bse.css?v=<%=info.getJsVersion()%>"/>
    <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/style.css?v=<%=info.getJsVersion()%>"/>
    <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/memoScript.js?v=<%=info.getJsVersion()%> " > </script>
          <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/bse.js?v=<%=info.getJsVersion()%> " > </script>
  </head>
        <%String logId=String.valueOf(info.getLogId());
        String onfocus="cook('"+logId+"')";
        %>
 <body onfocus="<%=onfocus%>">
  <table><tr><td valign="top">
  <%
ArrayList crtList =(ArrayList)request.getAttribute("crtList");
String dept =util.nvl((String)request.getAttribute("dept"));
String status =util.nvl((String)request.getAttribute("status"));
String stkloc =util.nvl((String)request.getAttribute("stkloc"));
String dsc =" OnApproval" ;
if(!status.equals("AP"))
 dsc ="OnIssue";

  if(crtList!=null && crtList.size() > 0){%>
  <table class="grid1">
  <tr><th colspan="8"><%=dsc%> <%=dept%> Tally</th></tr>
  <tr><th>Dsc</th><th>Status</th><th colspan="2">Total </th></tr>
  <tr><td></td><td></td><td><b>Qty </b></td><td><b>Cts</b></td></tr>
  <%
  for(int i=0;i< crtList.size();i++){
   HashMap pktDtl=(HashMap)crtList.get(i);
  String Qty = util.nvl((String)pktDtl.get("qty"));
  String Cts = util.nvl((String)pktDtl.get("cts"));
  String Stt = util.nvl((String)pktDtl.get("stt")); 
  %>
   <tr><td nowrap="nowrap"><%=dsc.toUpperCase()%></td> 
   <td><b> <%=Stt%></b></td>
   <td align="right"><A href="<%=info.getReqUrl()%>/marketing/stockTallyhk.do?method=PktDtlOnApprv&stt=<%=Stt%>&dept=<%=dept%>&status=<%=status%>&stkloc=<%=stkloc%>" target="_blank"><%=Qty%> </a></td>
   <td align="right"><%=Cts%></td> </tr>
  <%}%>
  </table>
  <%}%>
 </td>
 </tr>
 </table>
  </body>
</html>