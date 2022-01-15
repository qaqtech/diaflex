<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page errorPage="../error_page.jsp" %>
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import="java.util.ArrayList,java.util.HashMap, java.util.Date, java.util.ArrayList, java.util.Enumeration, ft.com.dao.*"%>

<jsp:useBean id="util" class="ft.com.JspUtil" scope="page" />
<jsp:useBean id="info" class="ft.com.InfoMgr" scope="session" />
 

<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>Web Login And Search Dtl </title>
    <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/bse.css?v=<%=info.getJsVersion()%>"/>
    <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/style.css?v=<%=info.getJsVersion()%>"/>
    <script language="javaScript" type="text/javascript" src="<%=info.getReqUrl()%>/scripts/bse.js?v=<%=info.getJsVersion()%>"></script>
 </head>
        <%String logId=String.valueOf(info.getLogId());
        String onfocus="cook('"+logId+"')";
        %>
 <body onfocus="<%=onfocus%>">
   <%
  
  ArrayList itemHdr = (ArrayList)request.getAttribute("itemHdrSrch");
  ArrayList dtlsList = (ArrayList)request.getAttribute("dtlListSrch");
    %>
    <table><tr><td><b><span style="margin-left:30px;">Last Refresh :<%=util.getTime()%></span></b></td></tr></table>
    <display:table name="sessionScope.webLoginAndSrchDtlForm.srchPktList" requestURI=""   sort="list" class="displayTable" >
    <%for(int i=0;i<itemHdr.size();i++){
    String hdr = util.nvl((String)itemHdr.get(i));
    if(hdr.equals("Byr Name")||hdr.equals("Sale EX")){%>
    <display:column property="<%=hdr%>"   style="text-align:left;"  title="<%=hdr%>"  sortable="true"/>
    <%}else{
    %>
    <display:column property="<%=hdr%>"   style="text-align:left;"  title="<%=hdr%>"  />
    <%}}%>
    
    </display:table>
    <!--
  <table class="grid1" >
  <thead>
  <tr>
  <%for(int i=0;i<itemHdr.size();i++){%>
  <th><%=itemHdr.get(i)%></th>
  <%}%>
  </tr></thead>
  <tbody>
  </tr>
  
  <%for(int i=0;i<dtlsList.size();i++){
  HashMap dtls = (HashMap)dtlsList.get(i);
  %>
  <tr>
  <%for(int j=0;j<itemHdr.size();j++){
  String key = (String)itemHdr.get(j);
  %>
  
  <td class="lft"><%=dtls.get(key)%></td>
  <%}%>
  </tr>
  <%}%>
  </tbody></table>-->
  
  </body>
</html>