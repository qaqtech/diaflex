<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page errorPage="../error_page.jsp" %>
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.ResultSet, java.util.ArrayList,java.util.Collections,java.util.ArrayList,java.util.HashMap,java.text.SimpleDateFormat, java.util.Calendar"%>
<jsp:useBean id="info" class="ft.com.InfoMgr" scope="session" />
<jsp:useBean id="util" class="ft.com.JspUtil" scope="page" />

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

<title>packet Details Report</title>

</head>
        <%String logId=String.valueOf(info.getLogId());
        String onfocus="cook('"+logId+"')";
        %>
 <body onfocus="<%=onfocus%>" onload="MM_preloadImages('images/faunalogo.png')" topmargin="0" leftmargin="0" rightmargin="0" bottommargin="0">
<div id="backgroundPopup"></div>

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
Packet Details Report
</span> </td> <td> Criteria : </td>

</tr></table>
</td>
</tr>
<tr><td class="hedPg"><table class="dataTable" >
<%
ArrayList prpAry=(ArrayList)session.getAttribute("prpArray");
ArrayList pktList=(ArrayList)session.getAttribute("pktList");
%>
<tr>
<th>Status</th>
<th>VNM</th>
<th>StockId</th>
<th>QTY</th>
<th>CTS</th>
<%
for(int i=0;i<prpAry.size();i++)
{
%>
<th><%=prpAry.get(i)%>
</th>
<%
}
%>
</tr>
<%
for(int j=0;j<pktList.size();j++)
{
HashMap pktmap =(HashMap)pktList.get(j);
String styleClass=null;
int cls=j % 2;
if(cls==0)
{
styleClass = "odd" ;
}else
{
styleClass = "even" ;
}
%>
<tr class="<%=styleClass%>">

<td><%=pktmap.get("stt")%></td>
<td><%=pktmap.get("vnm")%></td>
<td><%=pktmap.get("stk_idn")%></td>
<td><%=pktmap.get("qty")%></td>
<td><%=pktmap.get("cts")%></td>
<%
for(int k=0;k<prpAry.size();k++)
{
String prp=(String)prpAry.get(k);
%>
<td><%=pktmap.get(prp)%></td>
<%
}
%>
</tr>
<%
}
%>
</table></td></tr>
</table>
</body>
</html>