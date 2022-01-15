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
     <title>History</title>
     <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/bse.css?v=<%=info.getJsVersion()%>"/>
      <script src="<%=info.getReqUrl()%>/scripts/bse.js?v=<%=info.getJsVersion()%>" type="text/javascript"></script>
     <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/style.css?v=<%=info.getJsVersion()%>"/>
    <script language="javaScript" type="text/javascript" src="<%=info.getReqUrl()%>/scripts/ajax.js?v=<%=info.getJsVersion()%>"></script>
 </head>
        <%String logId=String.valueOf(info.getLogId());
        String onfocus="cook('"+logId+"')";
        %>
 <body onfocus="<%=onfocus%>" onload="MM_preloadImages('images/faunalogo.png')" topmargin="0" leftmargin="0" rightmargin="0" bottommargin="0">

<table width="100%"  border="0" align="center" cellpadding="0" cellspacing="0"> 
  <% if(request.getAttribute("msg")!=null){%>
<tr><td valign="top" class="tdLayout">
<span class="redLabel"><%=request.getAttribute("msg")%></span>
</td></tr>
<%}%>

   <%
  // util.updAccessLog(request,response,"Packet History form", "Packet History form");
   ArrayList pktList = (ArrayList)session.getAttribute("pktList");
   ArrayList itemHdr = (ArrayList)session.getAttribute("itemHdr");
   String view = (String)request.getAttribute("view");
   if(view!=null){
   if(pktList!=null && pktList.size()>0){
   HashMap pktPrpMap = new HashMap();
   %>
   <tr><td id="excel">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Create Excel <img src="../images/ico_file_excel.png" onclick="goToHistory('PriceCalc.do?method=createXL','','');" border="0"/> 
   <%
  ArrayList rolenmLst=(ArrayList)info.getRoleLst();
  String usrFlg=util.nvl((String)info.getUsrFlg());
  if(usrFlg.equals("SYS") || rolenmLst.contains("ADMIN")){
  %>
  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<img src="../images/edit.jpg" title="Edit Properties Module" onclick="newWindow('<%=info.getReqUrl()%>/dynamicProperty.do?method=load&mdl=PKT_HISVW&sname=PktHisLst&par=A')" border="0" width="15px" height="15px"/> 
  <%}%>
  </td>
   </tr>
   <tr>
   <td valign="top" class="tdLayout">
   <table  class="grid1" align="left" id="dataTable">
        <tr>
        <%for(int i=0;i<itemHdr.size();i++){%>
        <th><%=itemHdr.get(i)%></th>
        <%}%>
        </tr>
        <%for(int j=0;j<pktList.size();j++){
        pktPrpMap=(HashMap)pktList.get(j);%>
        <tr>
        <%
        for(int i=0;i<itemHdr.size();i++){%>
        <td><%=pktPrpMap.get(itemHdr.get(i))%></td>
        <%}%>
        </tr>
        <%}%>
    </table>
    </td> </tr>
    <%} else{%>
    <tr><td>
   Sorry no result found
   </td></tr>
   <%}}%>
</table></body>
</html>