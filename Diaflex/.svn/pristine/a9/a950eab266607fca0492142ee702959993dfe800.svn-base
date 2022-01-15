<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page errorPage="../error_page.jsp" %>
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.ResultSet, java.util.ArrayList,java.util.Collections,java.util.ArrayList,java.util.HashMap,java.text.SimpleDateFormat, java.util.Calendar"%>
<jsp:useBean id="db" class="ft.com.DBMgr" scope="session"/>
<jsp:useBean id="log" class="ft.com.LogMgr" scope="session"/>
<jsp:useBean id="util" class="ft.com.DBUtil" scope="session"/>
<jsp:useBean id="info" class="ft.com.InfoMgr" scope="session"/>

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

  <title>Group Status Lab Report</title>
 
  </head>
        <%String logId=String.valueOf(info.getLogId());
        String onfocus="cook('"+logId+"')";
        %>
 <body onfocus="<%=onfocus%>" onload="MM_preloadImages('images/faunalogo.png')" topmargin="0" leftmargin="0" rightmargin="0" bottommargin="0">
   <%
       util.updAccessLog(request,response,"Stock Summary", "Group Status Lab Summary");
    ArrayList labList=(ArrayList)request.getAttribute("labList");
    ArrayList grpList=(ArrayList)request.getAttribute("grpList");
    HashMap labGrpList=(HashMap)request.getAttribute("labGrpList");
    HashMap ttlGrpList=(HashMap)request.getAttribute("ttlGrpList");
    HashMap ttlLabList=(HashMap)request.getAttribute("ttlLabList");
    HashMap ttlanddscList=(HashMap)request.getAttribute("ttlanddscList");
   %>
  <table cellpadding="" cellspacing="" class="mainbg">
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
Group Status Lab Report
 
  </span> </td>
</tr></table>
  </td>
  </tr>
  
  <tr><td class="hedPg"><table class="dataTable" >
  <tr><th>GROUP/LAB</th>
  <%
 
  for(int i=0;i<labList.size();i++)
  {
  %>
  <th colspan="2"><%=labList.get(i) %></th>
  <%
  }
  %>
   <th colspan="2">Total</th>
  </tr>
 <tr>
  <td></td>
  <%

  for(int i=0;i<=labList.size();i++)
  {
  %>

  <td> <span>Quantity</span></td>
 
  <td ><span>Cts</span></td>
  <%
  }
  %>
 </tr>
<%for(int i=0;i<grpList.size();i++){
String grp=(String)grpList.get(i);
String dsc=(String)ttlanddscList.get(grp);
%>
<tr>
<td><%=dsc%></td>
<%for(int j=0;j<labList.size();j++){
String lab=(String)labList.get(j);
String qty=util.nvl((String)labGrpList.get(lab+"_"+grp+"_QTY"));
String cts=util.nvl((String)labGrpList.get(lab+"_"+grp+"_CTS"));
%>
<td   align="center"><span><a  title="Shape-Size report" href="stockSummary.do?method=loadShSize&group=<%=grp%>&lab=<%=lab%>" ><%=qty%></a></span></td>
<td align="center"><span ><a title="Packet Details"  href="stockSummary.do?method=pktDtl&group=<%=grp%>&lab=<%=lab%>" ><%=cts%></a></span></td> 
<%}
String grpQty=util.nvl((String)ttlGrpList.get(grp+"_QTY"));
String grpCts=util.nvl((String)ttlGrpList.get(grp+"_CTS"));

%>
<td align="center"><span><a  title="Shape-Size report" href="stockSummary.do?method=loadShSize&group=<%=grp%>">  <%=grpQty%></a></span></td>
<td align="center"><span ><a title="Packet Details" href="stockSummary.do?method=pktDtl&group=<%=grp%>"><%=grpCts%></a></span></td>
</tr>
<%}%>
<tr>
<td>Total</td>
<%for(int k=0;k<labList.size();k++){
String lab=(String)labList.get(k);
String qty=util.nvl((String)ttlLabList.get(lab+"_QTY"));
String cts=util.nvl((String)ttlLabList.get(lab+"_CTS"));
%>
<td  align="center"><span><a  title="Shape-Size report" href="stockSummary.do?method=loadShSize&group=ALL&lab=<%=lab%>" > <%=qty%></a></span></td>
<td  align="center"><span ><a   title="Packet Details"  href="stockSummary.do?method=pktDtl&group=ALL&lab=<%=lab%>"><%=cts%></a></span></td>
<%}
%>
<td><span ><a   title="Packet Details"  ><%=util.nvl((String)ttlanddscList.get("grand_QTY"))%></a></span></td>
<td><span><a  title="Shape-Size report"> <%=util.nvl((String)ttlanddscList.get("grand_CTS"))%></a></span></td>
</tr>

</table></td></tr>
  <tr><td>
<jsp:include page="/footer.jsp" />
</td></tr>
  </table>  
  </body>
</html>