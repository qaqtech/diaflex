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

<title>Mix Packet Details Report</title>

</head>
        <%
HashMap dbinfo = info.getDmbsInfoLst();
String cnt = (String)dbinfo.get("CNT");
        String logId=String.valueOf(info.getLogId());
        String onfocus="cook('"+logId+"')";
          String typ=util.nvl(request.getParameter("typ"));
        %>
 <body onfocus="<%=onfocus%>" onload="MM_preloadImages('images/faunalogo.png')" topmargin="0" leftmargin="0" rightmargin="0" bottommargin="0">

<table>
<tr><td class="hedPg">
<% String cts = request.getParameter("cts");
   String avg = request.getParameter("avg");
   if(cts!=null && avg!=null){
%>
Total Cts : <b><%=cts%></b> Avg : <b><%=avg%></b>
<%}%> </td></tr>
<tr><td class="hedPg">Create Excel <img src="../images/ico_file_excel.png" onclick="goTo('mixStockSummary.do?method=createPKTDtlXL','','')" border="0"/>
 <%
  ArrayList rolenmLst=(ArrayList)info.getRoleLst();
  String usrFlg=util.nvl((String)info.getUsrFlg());
  if(usrFlg.equals("SYS") || rolenmLst.contains("ADMIN")){
  %>
 <img src="../images/edit.jpg" title="Edit Properties Module" onclick="newWindow('<%=info.getReqUrl()%>/dynamicProperty.do?method=load&mdl=GROUP_MIX_VIEW&sname=GROUP_MIX_REPORT&par=A')" border="0" width="15px" height="15px"/> 
  <%}%></td>
</tr>
<tr><td class="hedPg"><table class="dataTable" >
<%
HashMap mprp = info.getMprp();
ArrayList vwPrpList=(ArrayList)session.getAttribute("GROUP_MIX_REPORT");
if(typ.equals("RGH"))
vwPrpList=(ArrayList)session.getAttribute("GROUP_RGH_VIEW");
ArrayList pktList=(ArrayList)session.getAttribute("pktList");
ArrayList prpDspBlocked = info.getPageblockList();
ArrayList itemHdr = new ArrayList();
itemHdr.add("dsc");
itemHdr.add("stt");
itemHdr.add("vnm");
itemHdr.add("qty");
%>
<tr>
<th>Sr No</th>
<th>Desc</th>
<th>Status</th>
<th>Packet Id</th>
<th>QTY</th>

<%
for(int i=0;i<vwPrpList.size();i++)
{
String prp = (String)vwPrpList.get(i);
if(prpDspBlocked.contains(prp)){
}else{
String hdr = (String)mprp.get(prp);
String prpDsc = (String)mprp.get(prp+"D");
itemHdr.add(prp);
%>
<th><%=prpDsc%></th>
<%if(prp.equals("BOX_ID") && (cnt.equals("kj") || cnt.equals("ag"))){%>
<th>BoxDsc</th>
<%
itemHdr.add("BoxDsc");
}}}
session.setAttribute("itemHdr", itemHdr);
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
<td><%=j+1%></td>
<td><%=pktmap.get("dsc")%></td>
<td><%=pktmap.get("stt")%></td>
<td><%=pktmap.get("vnm")%></td>
<td><%=pktmap.get("qty")%></td>

<%
for(int k=0;k<vwPrpList.size();k++)
{
String prp=(String)vwPrpList.get(k);
if(prpDspBlocked.contains(prp)){
}else{
%>
<td><%=pktmap.get(prp)%></td>
<%if(prp.equals("BOX_ID") && (cnt.equals("kj") || cnt.equals("ag"))){%>
<td><%=pktmap.get("BoxDsc")%></td>
<%}}}%>
</tr>
<%
}
%>
</table></td></tr>
</table>
</body>
</html>