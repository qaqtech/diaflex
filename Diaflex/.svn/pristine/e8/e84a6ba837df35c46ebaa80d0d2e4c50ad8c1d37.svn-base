<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
 
<%@ page errorPage="../error_page.jsp" %>
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import="java.util.ArrayList,java.util.HashMap,java.sql.ResultSet,ft.com.*, java.util.Date, java.util.ArrayList, ft.com.dao.*"%>
<jsp:useBean id="util" class="ft.com.JspUtil" scope="page" />
<jsp:useBean id="info" class="ft.com.InfoMgr" scope="session" />
<%@taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>
<%@taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>Lab Result View</title>
    <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/bse.css?v=<%=info.getJsVersion()%>"/>
      <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/style.css?v=<%=info.getJsVersion()%>"/>
      <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/assortScript.js?v=<%=info.getJsVersion()%> " > </script>
      <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/bse.js?v=<%=info.getJsVersion()%> " > </script>
  </head>
        <%String logId=String.valueOf(info.getLogId());
        String onfocus="cook('"+logId+"')";
        %>
 <body onfocus="<%=onfocus%>">
   <table cellpadding="0" cellspacing="0" valign="top" class="tdLayout">
  <%
   ArrayList issueList = (request.getAttribute("issueList") == null)?new ArrayList():(ArrayList)request.getAttribute("issueList");
   HashMap stockHisMap = (request.getAttribute("StockHisList") == null)?new HashMap():(HashMap)request.getAttribute("StockHisList");
   ArrayList viewPrp = (session.getAttribute("pktViewLabPrpLst") == null)?new ArrayList():(ArrayList)session.getAttribute("pktViewLabPrpLst");
   HashMap mprp = info.getMprp();
   HashMap prp = info.getPrp();
   HashMap dbinfo = info.getDmbsInfoLst();
   String cnt = (String)dbinfo.get("CNT");
    if(issueList!=null && issueList.size() >0){
  %>
  <tr><td align="left"><span class="txtLink" > <%=request.getAttribute("vnm")%></span></td></tr>
  <tr><td>
  <table><tr><td>
  <div class="memo">
  
  
  <table border="0" width="150" class="Orange"  cellspacing="1" cellpadding="1" >
          
           <tr>
           <th class="Orangeth">PROPERTY</th>
            <%for(int g=0;g < issueList.size();g++){
           String issue = (String)issueList.get(g);
           %>
           <th class="Orangeth"><%=util.nvl((String)stockHisMap.get(issue+"_HDR"))%>(<%=util.nvl((String)stockHisMap.get(issue+"_DTE"))%>)</th>
           <%}%>
           </tr>
  <%for(int i=0;i<viewPrp.size() ;i++){
 
   String lprp = (String)viewPrp.get(i);%>
 <tr>
  <td><%=lprp%></td>
  <%for(int g=0;g < issueList.size();g++){
           String issue = (String)issueList.get(g);
           %>
           <td width="100px">  <%=util.nvl((String)stockHisMap.get(issue+"_"+lprp))%></td>
    <%}%>
 </tr>
  <%}%>
  </table>
  </div>
  </td></tr></table>
   </td></tr>
  <%}else{%>
  <tr><td>Sorry No Result Found</td></tr>
 <%}%>
   </table>
  </body>
</html>