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
  
  <title>Web Users List</title>
  <script type="text/javascript" src="<%=info.getReqUrl()%>/jquery/jquery-freezar.min.js"></script>
  <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/bse.js?v=<%=info.getJsVersion()%> " > </script>
<link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/bse.css?v=<%=info.getJsVersion()%>"/>
<script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/ajax.js?v=<%=info.getJsVersion()%> " > </script>
  
 <script language="javaScript" type="text/javascript" src="<%=info.getReqUrl()%>/jquery/jquery.freezeheader.js?v=<%=info.getJsVersion()%>"></script>
 
      <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/style.css?v=<%=info.getJsVersion()%>"/>
       <script type="text/javascript">
   $(document).ready(function () {
	    $("#table2").freezeHeader({ 'height': '600px' });
    })
 </script>
  </head>
 <%%>
        <%String logId=String.valueOf(info.getLogId());
        String onfocus="cook('"+logId+"')";
        %>
 <body onfocus="<%=onfocus%>" onload="MM_preloadImages('images/faunalogo.png')" topmargin="0" leftmargin="0" rightmargin="0" bottommargin="0">
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
 
  Web User List
 
 
  </span> </td>
</tr></table>
  </td>
  </tr>
  <tr>
  <td><label id="msgLbl" class="redLabel"></label> </td>
  </tr>
  <tr><td valign="top" class="hedPg"  >
 <%
 ArrayList USRDTLLST = (ArrayList)request.getAttribute("USRDTLLST");
 HashMap allPageDtl= (info.getPageDetails() == null)?new HashMap():(HashMap)info.getPageDetails();
 HashMap pageDtl=(HashMap)allPageDtl.get("WEB_USER_REPORT");

 if(USRDTLLST!=null && USRDTLLST.size()>0){
 ArrayList MROLELST = (ArrayList)request.getAttribute("MROLEMapLST");
 %>
 <table class="grid1" id="table2">
 <thead>
 <tr>
 <th>Sr No.</th><th>Buyer</th><th>UserName</th><th>Password</th>
 <th>Act. Date</th><th>Excutive</th>
 <th>Terms</th><th>Terms Pct</th>
 <%if(MROLELST!=null && MROLELST.size()>0){
 for(int i=0;i<MROLELST.size();i++){
 HashMap roleDTl = (HashMap)MROLELST.get(i);
 String rlNme = (String)roleDTl.get("ROLENME");
 %>
 <th><%=rlNme%> </th><%}}%>
 <!--<th> Aadat1</th><th>Aadat1Comm</th><th>Broker1</th><th>Broker1Comm</th>-->
 <th>Email</th>
 <%if(pageDtl!=null && pageDtl.size()>0){
 ArrayList pageList=new ArrayList();
HashMap pageDtlMap=new HashMap();
String fld_nme="",fld_ttl="";
 for(int i=0;i<pageDtl.size();i++){
  pageList = (ArrayList)pageDtl.get("COLUMN");
  if(pageList!=null && pageList.size() >0){
      for(int j=0;j<pageList.size();j++){
      pageDtlMap=(HashMap)pageList.get(j);
      fld_ttl=(String)pageDtlMap.get("fld_ttl");
%>
 <th><%=fld_ttl%></th>
 
 <%} }
 }}
 %>
 
 <th>Pct</th><th>RapPct</th>	
 </tr>
  </thead>
 <tbody>
 <% for(int i=0;i<USRDTLLST.size();i++){
  HashMap usrDtl = (HashMap)USRDTLLST.get(i);
  String nmeIdn = (String)usrDtl.get("NME_IDN");
   String usrIdn = (String)usrDtl.get("USR_IDN");
  ArrayList  usrRolLst = (ArrayList)usrDtl.get("USRLST");
  String url=info.getReqUrl() +"/contact/nme.do?method=load&fromFeedback=Y&nmeIdn="+nmeIdn;
 %>
 <tr>
 <td><%=util.nvl((String)usrDtl.get("Count"))%></td>
 <td nowrap="nowrap"><a href="<%=url%>" target="_blank"> <%=util.nvl((String)usrDtl.get("byr"))%></a></td><td nowrap="nowrap"><%=util.nvl((String)usrDtl.get("usr"))%></td><td><%=util.nvl((String)usrDtl.get("pwd"))%></td>
  <td nowrap="nowrap"><%=util.nvl((String)usrDtl.get("dte"))%></td><td nowrap="nowrap"><%=util.nvl((String)usrDtl.get("sl_prsn"))%></td><td nowrap="nowrap"><%=util.nvl((String)usrDtl.get("term"))%>&nbsp;<%=util.nvl((String)usrDtl.get("cur"))%></td>
  <td nowrap="nowrap"><%=util.nvl((String)usrDtl.get("ttl_trm_pct"))%></td>
  <%if(MROLELST!=null && MROLELST.size()>0){
 for(int j=0;j<MROLELST.size();j++){
 HashMap roleDTl = (HashMap)MROLELST.get(j);
 String rlNme = (String)roleDTl.get("ROLENME");
 String rlIdn = (String)roleDTl.get("ROLEIDN");
 String isChecked="";
 if(usrRolLst.contains(rlNme))
   isChecked = "checked=\"checked\"";
 
 %>
 <td>
 <input type="checkbox" onclick="WebUserRole(this,'<%=usrIdn%>','<%=rlIdn%>')" <%=isChecked%> />
 </td><%}}%>
  <!--<td nowrap="nowrap"><%=util.nvl((String)usrDtl.get("aadat1"))%></td><td><%=util.nvl((String)usrDtl.get("aadat_comm"))%></td><td><%=util.nvl((String)usrDtl.get("broker1"))%></td>
 <td nowrap="nowrap"><%=util.nvl((String)usrDtl.get("broker1comm"))%></td>-->
 <td><%=util.nvl((String)usrDtl.get("eml"))%></td>
  <%if(pageDtl!=null && pageDtl.size()>0){
 ArrayList pageList=new ArrayList();
HashMap pageDtlMap=new HashMap();
String fld_nme="",fld_ttl="";
 for(int k=0;k<pageDtl.size();k++){
  pageList = (ArrayList)pageDtl.get("COLUMN");
  if(pageList!=null && pageList.size() >0){
      for(int j=0;j<pageList.size();j++){
      pageDtlMap=(HashMap)pageList.get(j);
      fld_nme=(String)pageDtlMap.get("fld_nme");
      String fld_dtl=util.nvl((String)usrDtl.get(fld_nme));
%>
 <td nowrap="nowrap"><%=fld_dtl%></td>
 
 <%} }
 }}
 %>
 <td><%=util.nvl((String)usrDtl.get("ext_pct"))%></td><td><%=util.nvl((String)usrDtl.get("rap_pct"))%></td>
 
 </tr>
 <%}%>
 </tbody>
 </table>
 <%}%>
  </td></tr>
  
  </table>
  
  </body>
</html>