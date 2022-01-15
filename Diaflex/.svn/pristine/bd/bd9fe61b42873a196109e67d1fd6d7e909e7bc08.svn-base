<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page errorPage="../error_page.jsp" %>
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import="java.util.ArrayList,java.util.HashMap,java.sql.ResultSet,ft.com.*, java.util.Date, java.util.ArrayList, ft.com.dao.*"%>
<jsp:useBean id="info" class="ft.com.InfoMgr" scope="session" />
<jsp:useBean id="util" class="ft.com.JspUtil" scope="page" />
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
  <%
   HashMap stockList = (HashMap)request.getAttribute("StockList");
   String mstkIdn = (String)request.getAttribute("mstkIdn");
   ArrayList viewPrp = (ArrayList)session.getAttribute("LabViewLst");
    ArrayList grpList = (ArrayList)request.getAttribute("grpList");
   HashMap mprp = info.getMprp();
   HashMap prp = info.getPrp();
    if(stockList!=null && stockList.size() >0){
   
  %>
  
  <table cellpadding="0" cellspacing="0">
 
  <tr><td>
  
  <div class="memo">
  
  <table  border="0" width="400" class="Orange" cellspacing="1" cellpadding="1">
   <tr><th class="Orangeth">PROPERTY</th>
   <%for(int g=0;g < grpList.size();g++){
           String grp = (String)grpList.get(g);
           %>
           <th class="Orangeth">Grp <%=grp%> </th>
           <%}%>
   
   </tr>
   <%
   for(int i=0;i<viewPrp.size();i++){
   String lprp = (String)viewPrp.get(i);
   String prpDsc = (String)mprp.get(lprp+"D");
   %>
   <tr>
   <td><%=prpDsc%></td>
   <%for(int g=0;g < grpList.size();g++){
           String grp = (String)grpList.get(g);
           %>
           <td>  <%=stockList.get(mstkIdn+"_"+lprp+"_1")%></td>
           <%}%>
  
   </tr>
  <% }
   %>
   
  </table></div></td></tr></table>
  
  
  <%}else{%>
  sorry data not found
 <%}%>
  </body>
</html>