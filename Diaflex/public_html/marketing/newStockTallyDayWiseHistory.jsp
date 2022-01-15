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
   <%
  HashMap pktDtl = (HashMap)request.getAttribute("pktDtl");
  ArrayList grpList = (ArrayList)request.getAttribute("grpList");
  ArrayList dteList= (request.getAttribute("dteList")== null)?new ArrayList():(ArrayList)request.getAttribute("dteList");
  HashMap grp3list = (HashMap)session.getAttribute("grp3List"); 
  HashMap dbinfo = info.getDmbsInfoLst();
    HashMap prp = info.getPrp();
%>
<table>
<%if(dteList.size()>0){
  ArrayList reqsttList = (ArrayList)request.getAttribute("reqsttList");
  ArrayList locList = (ArrayList)request.getAttribute("locList");
  String clientlocprp=util.nvl((String)dbinfo.get("LOC"));
  ArrayList prpValLoc = (ArrayList)prp.get(clientlocprp+"V");
  ArrayList prpPValLoc = (ArrayList)prp.get(clientlocprp+"P");
  HashMap reqsttListDsc=new HashMap();
  reqsttListDsc.put("IS","Issue");
  reqsttListDsc.put("RT","Return");
  int grpListsz=grpList.size()-1;
  int reqsttListsz=reqsttList.size();
  int colspan=reqsttListsz*2;
  
  for(int l=0;l<prpValLoc.size();l++){
  String loc = (String)prpValLoc.get(l);
  if(locList.contains(loc)){
  String locPval=util.nvl((String)prpPValLoc.get(prpValLoc.indexOf(loc)));
  %>
  <tr><td valign="top" class="hedPg"><table  class="grid1" valign="top" class="hedPg">
  <tr><th align="center" colspan="<%=((grpListsz*colspan)+1+(colspan))%>"><%=locPval%></th></tr>
  <tr>
  <th>Date</th>
  <%
  for(int i=0;i<grpListsz;i++){
  String mstkStt = (String)grpList.get(i);%>
  <th nowrap="nowrap" colspan="<%=colspan%>"><%=grp3list.get(mstkStt)%></th>
  <%}%>
  <th nowrap="nowrap" colspan="<%=colspan%>">Total</th>
</tr>
<tr>
<td></td>
  <%
  for(int i=0;i<grpListsz;i++){
  for(int j=0;j<reqsttListsz;j++){
  String stt = (String)reqsttList.get(j);
  %>
  <th nowrap="nowrap" colspan="2" align="center"><%=util.nvl((String)reqsttListDsc.get(stt))%></th>
  <%}}
  for(int i=0;i<reqsttListsz;i++){
  String stt = (String)reqsttList.get(i);
  %>
  <th nowrap="nowrap" colspan="2" align="center"><%=util.nvl((String)reqsttListDsc.get(stt))%></th>
  <%}%>
</tr>
<tr>
<td></td>
  <%
  for(int i=0;i<grpListsz;i++){
    for(int j=0;j<reqsttListsz;j++){
  %>
  <td nowrap="nowrap" align="center">Qty</td>
  <td nowrap="nowrap" align="center">Cts</td>
  <%}}
  for(int i=0;i<reqsttListsz;i++){
  %>
  <td nowrap="nowrap" align="center">Qty</td>
  <td nowrap="nowrap" align="center">Cts</td>
  <%}%>
</tr>
<%for(int i=0;i<dteList.size();i++){
String dte=util.nvl((String)dteList.get(i));
%>
<tr>
<th><%=dte%></th>
  <%for(int j=0;j<grpListsz;j++){
  String grp = (String)grpList.get(j);
  for(int k=0;k<reqsttListsz;k++){
  String stt = (String)reqsttList.get(k);
  String key=loc+"_"+dte+"_"+grp+"_"+stt;
  %>
  <td nowrap="nowrap" align="right"><%=util.nvl((String)pktDtl.get(key+"_Q"))%></td>
  <td nowrap="nowrap" align="right"><%=util.nvl((String)pktDtl.get(key+"_C"))%></td>
  <%}}
  for(int k=0;k<reqsttListsz;k++){
  String stt = (String)reqsttList.get(k);%>
  <td nowrap="nowrap" align="right"><%=util.nvl((String)pktDtl.get(loc+"_"+dte+"_"+stt+"_Q"))%></td>
  <td nowrap="nowrap" align="right"><%=util.nvl((String)pktDtl.get(loc+"_"+dte+"_"+stt+"_C"))%></td>
<%}%>
</tr>
<%}%>
<tr>
<th>Total</th>
  <%for(int j=0;j<grpListsz;j++){
  String grp = (String)grpList.get(j);
  for(int k=0;k<reqsttListsz;k++){
  String stt = (String)reqsttList.get(k);
  String key=loc+"_"+grp+"_"+stt;
  %>
  <td nowrap="nowrap" align="right"><%=util.nvl((String)pktDtl.get(key+"_Q"))%></td>
  <td nowrap="nowrap" align="right"><%=util.nvl((String)pktDtl.get(key+"_C"))%></td>
  <%}}
    for(int k=0;k<reqsttListsz;k++){
  String stt = (String)reqsttList.get(k);%>
  <td nowrap="nowrap" align="right"><%=util.nvl((String)pktDtl.get(loc+"_"+"TTL_"+stt+"_Q"))%></td>
  <td nowrap="nowrap" align="right"><%=util.nvl((String)pktDtl.get(loc+"_"+"TTL_"+stt+"_C"))%></td>
<%}%>
</tr>
</table></td></tr>
<%}}}else{%>
<tr><td valign="top" class="hedPg">Sorry no result found </td></tr>
<%}%>
</table>
 </body>
</html>