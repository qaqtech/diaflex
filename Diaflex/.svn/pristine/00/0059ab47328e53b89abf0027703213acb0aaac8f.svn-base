<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page errorPage="../error_page.jsp" %>
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.ResultSet, java.util.ArrayList,java.util.Collections,java.util.ArrayList,java.util.HashMap,java.text.SimpleDateFormat, java.util.Calendar"%>
<jsp:useBean id="info" class="ft.com.InfoMgr" scope="session" />
<jsp:useBean id="util" class="ft.com.JspUtil" scope="page" />
<%@taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
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
   <script language="javaScript" type="text/javascript" src="<%=info.getReqUrl()%>/scripts/memoScript.js?v=<%=info.getJsVersion()%>"></script>
   <link href="<%=info.getReqUrl()%>/calendar/calendar.css" rel="stylesheet" type="text/css" />

 
      <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/style.css?v=<%=info.getJsVersion()%>"/>

  <title>Combination Report View</title>
 
  </head> 
        <%String logId=String.valueOf(info.getLogId());
        String onfocus="cook('"+logId+"')";
        %>
 <body onfocus="<%=onfocus%>">
  <form action="kjll" name="CombinationReportForm" method="post">
   <table cellpadding="" cellspacing="" width="80%" class="mainbg">
    <tr>
    <td height="103" valign="top">
   <jsp:include page="/header.jsp" />
   </td>
  </tr>
  
 
    <tr>
    <td height="5" valign="top" class="greyline"><img src="../images/grey_linebig.png" width="685" height="5" align="right" /></td>
  </tr>
  <tr><td>
  <div style="overflow:scroll;width:1300px;height:500px;">
  <table>
  <%
  String selCriteria=util.nvl((String)request.getAttribute("selCriteria"));
  %>
   <tr>
  <td valign="top" class="hedPg">
  <table cellpadding="1" cellspacing="5"><tr><td valign="middle">
  <img src="../images/line.jpg" border="0" /></td><td valign="middle"> <span class="pgHed">
Combination Report View
 
  </span> </td><td><b>Selected Criteria:</b></td><td><%=selCriteria%></td>
</tr></table>
  </td>
  </tr>
  <%
  HashMap keyvaltbl=(HashMap) request.getAttribute("keyvaltbl");
  ArrayList  fnRowArray=(ArrayList) request.getAttribute("fnRowArray");
  ArrayList  fnColArray=(ArrayList) request.getAttribute("fnColArray"); 
  ArrayList rowList=(ArrayList) request.getAttribute("RowList");
  ArrayList colList=(ArrayList) request.getAttribute("ColList");
if(keyvaltbl==null)
{
%>
<tr><td> Sorry no result found</td></tr>
<%
}else
{
 %>
 
 <tr><td class="hedPg"><table cellpadding="5" cellspacing="5" class="dataTable" style=" white-space: nowrap;">
 <tr>
 <td  style="width:5px;"></td>
 <%
 for(int i=0;i<rowList.size();i++)  // To display row name
 {
 %>
 <th  style="width:5px;"><%=rowList.get(i) %></th>
<% }
 %>
 </tr>
 <tr>
 <td><table  style="border:0px;" class="dataTable">
 <% for(int i=0;i<colList.size();i++)  //To display column name
{
%> <tr><th style="width:5px;" align="center" ><%=colList.get(i)%></th></tr> <%
 }
 %></table></td>
 
 
 <%
if(rowList!=null) 
{
%>
<td colspan="<%=rowList.size() %>"></td>
<%
}
%>

<%
if(fnColArray!=null)
{
 for(int i=0;i<fnColArray.size();i++)         //  create columns
 {
  String col=(String)fnColArray.get(i);
  if(!col.contains("_"))
  {
  %>
 <td style="width:5px;" ><table style="border:0px;" class="dataTable"><tr><th align="center" ><%= col %></th></tr></table></td> 
<%  }
  else
  {
  %>
  <td><table style="border:0px;" class="dataTable">
  <%
 do
 {
 String tdcol="";
 if(col.contains("_"))
 {
 tdcol=col.substring(0,col.indexOf("_"));
 }
 else 
 {
 tdcol=col;
 }
 col=col.substring(col.indexOf("_")+1, col.length());
 %>
  <tr><th align="center"><%=tdcol %></th></tr>
 <%
 if(!col.contains("_"))
 {
 %>
  <tr><th align="center"><%=col %></th></tr>
<% }
 }
 while(col.contains("_"));
 %>
</table> </td>
<% }
 }
 }
 %>
 </tr>

 
 <%
 if(fnRowArray!=null)
 {
  String rowval="";
 for(int i=0;i<fnRowArray.size();i++)
 {
 %>
 <tr>
 <td></td>
 <%
 String row=util.nvl((String)fnRowArray.get(i));
  rowval=row;
  if(!row.contains("_"))
  {
  %>
 <th style="width:5px;" align="center"><%= row %></th> 
<%  }
else
{
do
{
String tdrow="";
tdrow=row.substring(0, row.indexOf("_"));
%>
<th align="center" style="width:5px;"><%= tdrow %></th>
<%
 row=row.substring(row.indexOf("_")+1,row.length());
 if(!row.contains("_"))
 {
 %>
<th align="center" style="width:5px;"><%= row %></th> 
<% }
}
while(row.contains("_"));
%>

<%
  
 }
 // end of else
 if(fnColArray!=null)
 {
 for(int j=0;j<fnColArray.size();j++)
 {
 String colval=util.nvl((String)fnColArray.get(j));
 String cellName=rowval+"_"+colval;
 String cellvalue=util.nvl((String)keyvaltbl.get(cellName));
 %>
 <td align="center" style="width:5px;"><span><a  title="Packet Details" href="combinationReport.do?method=pktDtl&cellname=<%=cellName%>" ><%= cellvalue%> </a></span></td>
<!-- <%= cellvalue%> </td>          add these links on 27/03/2012-->
 <%
 }
 }else
 {
 String cellName=rowval;
 String cellvalue=util.nvl((String)keyvaltbl.get(cellName));
  %>
 <td align="center" style="width:5px;"><span><a  title="Packet Details" href="combinationreport.do?method=pktDtl&cellname=<%=cellName%>" ><%= cellvalue%> </a></span></td>
<!-- <%= cellvalue%> </td>-->
 <%
 }
 %>
 
</tr> 
<%
 }
 }
  else if(fnColArray!=null)
 {
 %>
 <tr>
 <td></td>
 <%
 for(int j=0;j<fnColArray.size();j++)
 {
 String colval=util.nvl((String)fnColArray.get(j));
 String cellName=colval;
 String cellvalue=util.nvl((String)keyvaltbl.get(cellName));
 %>
 <td align="center" style="width:5px;"><span><a  title="Packet Details" href="combinationreport.do?method=pktDtl&cellname=<%=cellName%>" ><%= cellvalue%> </a></span></td>
 <!--<%= cellvalue%> </td>-->
 <%
 }
 %>
 </tr>
 <%
 }

 
 
 %>


 
 </table></td></tr>
 <%
 }
 %>
 </table></div>
 </td></tr>
 
  
  
   </table>
  </form>
  </body>
</html>