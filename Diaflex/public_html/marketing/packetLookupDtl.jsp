<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
 
<%@ page errorPage="../error_page.jsp" %>
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import="java.util.ArrayList,java.util.ArrayList, java.util.Iterator, java.sql.Date,java.util.HashMap, ft.com.marketing.PacketLookupForm" %>
<jsp:useBean id="util" class="ft.com.JspUtil" scope="page" />
<jsp:useBean id="info" class="ft.com.InfoMgr" scope="session" />
<%@taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>
<%@taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>Packet Lookup Details</title>
    <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/bse.css?v=<%=info.getJsVersion()%>"/>
      <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/style.css?v=<%=info.getJsVersion()%>"/>
      <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/memoScript.js?v=<%=info.getJsVersion()%> " > </script>
      <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/bse.js?v=<%=info.getJsVersion()%> " > </script>
        </head>
        <%String logId=String.valueOf(info.getLogId());
        String onfocus="cook('"+logId+"')";
        %>
 <body onfocus="<%=onfocus%>">
  <table>
  <%
  String vnms = util.nvl((String)request.getAttribute("vnm"));
  HashMap stockPkt=null;
  HashMap mprp = info.getMprp();
  ArrayList stockList = (ArrayList) request.getAttribute("packetdtllst");
  ArrayList vwPrpLst = (ArrayList)session.getAttribute("PKT_PRP_VW");
  ArrayList prpDspBlocked = info.getPageblockList();
  for(int i=0; i < stockList.size(); i++ ){
    String styleCode = "";
    int sr = i +1;
    stockPkt = (HashMap)stockList.get(i);
    String certno = util.nvl((String)stockPkt.get("cert_no"));
    String weight = util.nvl((String)stockPkt.get("cts"));
  %>
    <tr>
    <td valign="top" class="tdLayout" height="15" align="left"><span class="txtLink" > <%=vnms%></span></td>
    <td valign="top" class="tdLayout" height="15" align="left"><span class="txtLink" > 
    <a style="text-decoration: none; color: Black;" href="http://www.gia.edu/report-check?fuseaction=home.showReportVerification&reportno=<%=certno%>&weight=<%=weight%>"  
     target="_blank">Click For Certificate</a></span></td>
    </tr>
    <tr></tr>
    <tr>
    <td valign="top" class="tdLayout">
  
  <table class="grid1">
  <tr><th>Property</th><th>Value</th></tr>
 
 <%
    for(int j=0; j < vwPrpLst.size(); j++){
    String prp = (String)vwPrpLst.get(j); 
    String prpValue =  util.nvl((String)stockPkt.get(prp));
    String prpDsc = (String)mprp.get(prp+"D");
    if(prpDspBlocked.contains(prp)){
    }else{
%>

<tr <%=styleCode%>><td><%=util.nvl(prpDsc)%></td><td><%=util.nvl(prpValue)%></td></tr>
<%}}%>
   </table>
  
 </td>
 <td valign="top">
 
 <table class="packDia" border="0" >
<tr><td align="center" valign="top">
<label title="table %" style="padding-left:20px;"><%= util.nvl((String)stockPkt.get("DIA_MX"))%>-<%= util.nvl((String)stockPkt.get("DIA_MN"))%></label>
</td></tr>
<tr><td valign="top" align="center">
<label title="table %" style="padding-left:25px;" ><%= util.nvl((String)stockPkt.get("TBL"))%>%</label>
</td></tr>
<tr><td valign="top" height="10px" align="center">
<div style="float:left;padding-left:30px;"><label title="table %" ><%= util.nvl((String)stockPkt.get("CH"))%></label></div>
<div style="float:right;padding-right:30px;"><label title="table %" style="" ><%= util.nvl((String)stockPkt.get("CA" ))%></label></div>
</td></tr>
<tr><td>
<label title="table %" ><%= util.nvl((String)stockPkt.get("GD_MX"))%>%</label>    
</td></tr>
<tr> <td>
<label title="table %" ><%= util.nvl((String)stockPkt.get("GD_MN"))%>%</label>
</td></tr>
<tr><td valign="top">
<div style="float:right;padding-right:30px;"><label title="table %" ><%= util.nvl((String)stockPkt.get("PA"))%>%</label></div>
</td></tr>
<tr><td>
<div style="float:left;padding-left:30px;"><label title="table %" ><%= util.nvl((String)stockPkt.get("PD"))%>%</label></div>
<div style="float:right;padding-right:10px;"><label title="table %" ><%= util.nvl((String)stockPkt.get("DEPTH"))%>mm &nbsp;<%= util.nvl((String)stockPkt.get("DP"))%> </label></div>
</td></tr>
<tr><td>
<label title="table %" >&nbsp;</label>
</td></tr>
<tr><td>
<div style="float:right;padding-right:70px;"><label title="table %" >Pointed</label></div>
</td></tr>
<tr><td>
<label title="table %" >&nbsp;</label>
</td></tr>
</table>
 
    
 </td>
 <%
 } 
 %>
 </tr></table> 
 
  
  </body>
</html>