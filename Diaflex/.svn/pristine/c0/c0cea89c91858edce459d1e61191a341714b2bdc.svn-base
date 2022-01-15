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
 <body onfocus="<%=onfocus%>" topmargin="0" leftmargin="0" rightmargin="0" bottommargin="0">
<div id="backgroundPopup"></div>

<table cellpadding="" cellspacing="">
<tr>
<td height="5" valign="top" class="greyline"><img src="../images/grey_linebig.png" width="685" height="5" align="right" /></td>
</tr>
<tr>
<td valign="top" class="hedPg">
<table cellpadding="1" cellspacing="5"><tr><td valign="middle">
<img src="../images/line.jpg" border="0" /></td><td valign="middle"> <span class="pgHed">
Packet Details Report
</span> </td> 
</tr></table>
</td>
</tr>
<tr><td class="hedPg">Create Excel <img src="../images/ico_file_excel.png" onclick="goTo('group.do?method=createPKTDtlXL','','')" border="0"/>
<%
  ArrayList rolenmLst=(ArrayList)info.getRoleLst();
  String usrFlg=util.nvl((String)info.getUsrFlg());
  if(usrFlg.equals("SYS") || rolenmLst.contains("ADMIN")){
  %>
  &nbsp;&nbsp;<img src="../images/edit.jpg" title="Edit Properties Module" onclick="newWindow('<%=info.getReqUrl()%>/dynamicProperty.do?method=load&mdl=GROUP_VIEW&sname=GROUP_REPORT&par=A')" border="0" width="15px" height="15px"/>
  <%}%>
</td></tr>
<tr><td class="hedPg"><table class="grid1" >
<%
HashMap mprp = info.getMprp();
ArrayList prpDspBlocked = info.getPageblockList();
ArrayList vwPrpList=(ArrayList)session.getAttribute("GROUP_REPORT");
ArrayList pktList=(ArrayList)session.getAttribute("pktList");
String valueDisplay= util.nvl((String)request.getAttribute("valueDisplay"));
HashMap allPageDtl= (info.getPageDetails() == null)?new HashMap():(HashMap)info.getPageDetails();
HashMap pageDtl=(HashMap)allPageDtl.get("STOCK_SUMMARY");
ArrayList pageList=new ArrayList();
HashMap pageDtlMap=new HashMap();
    String fld_nme="",fld_ttl="",val_cond="",dflt_val="",fld_typ="",form_nme="",flg1="",lov_qry="";
ArrayList itemHdr = new ArrayList();
itemHdr.add("stt");
itemHdr.add("vnm");
itemHdr.add("qty");
%>
<tr>
<th>Sr No</th>
<th>Status</th>
<th>Packet Id</th>
<th>QTY</th>

<%
for(int i=0;i<vwPrpList.size();i++){
String prp = (String)vwPrpList.get(i);
if(prpDspBlocked.contains(prp)){
}else{
String hdr = (String)mprp.get(prp);
String prpDsc = (String)mprp.get(prp+"D");
itemHdr.add(prp);
%>
<th><%=prpDsc%></th>
<%}}
    pageList=(ArrayList)pageDtl.get("RAP_DIS");
    if(pageList!=null && pageList.size() >0){
    for(int j=0;j<pageList.size();j++){
    pageDtlMap=(HashMap)pageList.get(j);
    fld_nme=(String)pageDtlMap.get("fld_nme");
    fld_typ=(String)pageDtlMap.get("fld_typ");
    fld_ttl=(String)pageDtlMap.get("fld_ttl");
    val_cond=(String)pageDtlMap.get("val_cond");
    dflt_val=(String)pageDtlMap.get("dflt_val");
    itemHdr.add(fld_nme);
    %>
    <th><%=fld_ttl%></th>
    <%}
    } 
%>
<%if(!valueDisplay.equals("")){%>
<th>Rate</th>
<th>Amount</th>
<%
itemHdr.add("Rte");
itemHdr.add("Amount");
}
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
<%
}}
    pageList=(ArrayList)pageDtl.get("RAP_DIS");
    if(pageList!=null && pageList.size() >0){
    for(int k=0;k<pageList.size();k++){
    pageDtlMap=(HashMap)pageList.get(k);
    fld_nme=(String)pageDtlMap.get("fld_nme");
    fld_typ=(String)pageDtlMap.get("fld_typ");
    fld_ttl=(String)pageDtlMap.get("fld_ttl");
    val_cond=(String)pageDtlMap.get("val_cond");
    dflt_val=(String)pageDtlMap.get("dflt_val");
    %>
    <td align="right"><%=util.nvl((String)pktmap.get(fld_nme))%></td>
    <%}
    } 
if(!valueDisplay.equals("")){%>
<td align="right"><%=util.nvl((String)pktmap.get("Rte"))%></td>
<td align="right"><%=util.nvl((String)pktmap.get("Amount"))%></td>
<%}%>
</tr>
<%
}
%>
</table></td></tr>
</table>
</body>
</html>