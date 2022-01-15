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
  
  <title> Packet Detail</title>
  <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/bse.js?v=<%=info.getJsVersion()%> " > </script>
  <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/bse.css?v=<%=info.getJsVersion()%>"/>
  <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/ajax.js?v=<%=info.getJsVersion()%> " > </script>
  <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/style.css?v=<%=info.getJsVersion()%>"/>
  </head>
        <%String logId=String.valueOf(info.getLogId());
        String onfocus="cook('"+logId+"')";
        String grp = (String)request.getAttribute("grp");
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
  Packet Detail
  </span> </td>
</tr></table>
  </td>
  </tr>
   <tr><td valign="top" class="hedPg"  >
   <A href="<%=info.getReqUrl()%>/report/assortLabPending.do?method=load&grp=<%=grp%>">Back To Report</a>
    &nbsp;&nbsp;Create Excel &nbsp;
  <img src="../images/ico_file_excel.png" border="0" onclick="goTo('<%=info.getReqUrl()%>/report/assortLabPending.do?method=createXLpkt','','')"/>
 <%
  ArrayList rolenmLst=(ArrayList)info.getRoleLst();
  String usrFlg=util.nvl((String)info.getUsrFlg());
  if(usrFlg.equals("SYS") || rolenmLst.contains("ADMIN")){
  %>
  &nbsp;&nbsp;<img src="../images/edit.jpg" title="Edit Properties Module" onclick="newWindow('<%=info.getReqUrl()%>/dynamicProperty.do?method=load&mdl=AS_VIEW&sname=asViewLst&par=A')" border="0" width="15px" height="15px"/> 
  <%}%>
 </td></tr>
   </tr>
  <tr><td valign="top" class="hedPg"  >
  <%
   ArrayList stockList = (ArrayList)session.getAttribute("pktList");
   ArrayList prpDspBlocked = info.getPageblockList();
   if(stockList!=null && stockList.size()>0){
     HashMap mprp = info.getMprp();
    ArrayList vwPrpLst = (ArrayList)session.getAttribute("asViewLst");
    %>
    <table class="grid1">
   <tr> <th>Sr</th>
      
        <th>Packet No.</th>
        <%for(int j=0; j < vwPrpLst.size(); j++ ){
    String prp = (String)vwPrpLst.get(j);
    if(prpDspBlocked.contains(prp)){
       }else{
     String hdr = (String)mprp.get(prp);
    String prpDsc = (String)mprp.get(prp+"D");
    if(hdr == null) {
        hdr = prp;
       }  

%>
<th title="<%=prpDsc%>"><%=hdr%></th>

<%}}%> </tr>
<% 
int sr=0;
for(int i=0; i < stockList.size(); i++ ){
 HashMap stockPkt = (HashMap)stockList.get(i);
 sr = i+1;
 %>
<tr>
<td><%=sr%></td>
<td><%=stockPkt.get("vnm")%></td>
<%for(int j=0; j < vwPrpLst.size(); j++ ){
    String prp = (String)vwPrpLst.get(j);
    if(prpDspBlocked.contains(prp)){
       }else{
    %>
    <td><%=stockPkt.get(prp)%></td>
<%}}%>
</tr>
<%}%>
    </table>
 <%  }%>
   </td></tr></table>
  
  
  
  </body>
</html>