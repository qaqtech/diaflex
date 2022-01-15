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
    <title>Rap Sync</title>
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
  ArrayList dtls = (ArrayList) request.getAttribute("dtls");
  %>
     <tr>
    <td valign="top" class="tdLayout" height="15" align="left">
    <table><tr><td>
    <span class="txtLink" > <%=vnms%></span></td>
    </tr></table>
    </td></tr>
    <tr>
    <td valign="top" class="tdLayout">
  <%  if(dtls!= null && dtls.size()>0){
  %>
  <table class="grid1" id="dataTable" >
        <tr>
        <th>Sr. No.</th>
        <th>Log Time</th>
        <th>Action</th>
        <th>Update Time</th>
        <th>Rapnet</th>
        <th>Rapnet Message</th>
        </tr>
        
  <%
    int iCounter=0;
    for(int i=0; i < dtls.size(); i++) {
        iCounter++;
        HashMap dtl = (HashMap)dtls.get(i);
        %>    
        <tr>
        <td><%=iCounter%></td>
       <td><%=util.nvl((String)dtl.get("log_tm"))%></td> 
       <td><%=util.nvl((String)dtl.get("act_typ"))%></td> 
       <td><%=util.nvl((String)dtl.get("upd_tm"))%></td> 
       <td><%=util.nvl((String)dtl.get("rapnet"))%></td> 
       <td><%=util.nvl((String)dtl.get("rapnet_msg"))%></td> 
        </tr>
    <%}%>
  </table>
  <%} else {%>
    No Data Found
  <%}%>
 </td></tr></table> 
 
  
  </body>
</html>