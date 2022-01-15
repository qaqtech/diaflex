<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page errorPage="../error_page.jsp" %>
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import="java.util.ArrayList,java.util.ArrayList,java.util.HashMap, java.sql.*, java.util.Enumeration, java.util.logging.Level"%>
<jsp:useBean id="info" class="ft.com.InfoMgr" scope="session" />
<jsp:useBean id="util" class="ft.com.JspUtil" scope="page" />
<%@taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>
 
<html>
  <head>
     <%
  ArrayList  byridnLst = (ArrayList)request.getAttribute("byridnLst_WEB");
  HashMap  byrdtl = (HashMap)request.getAttribute("byrdtl_WEB");
  HashMap  dataDtl = (HashMap)request.getAttribute("dataDtl_WEB");
  String hdrnme = (String)request.getAttribute("hdrnme_WEB");
   String  offerwebrfsh = (String)request.getAttribute("offerwebrfsh");
  String  view = (String)request.getAttribute("view");%>
<title>Reports</title>
    <script language="javaScript" type="text/javascript" src="<%=info.getReqUrl()%>/scripts/ajax.js?v=<%=info.getJsVersion()%>"></script>
    <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/bse.css?v=<%=info.getJsVersion()%>"/>
    <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/style.css?v=<%=info.getJsVersion()%>"/>
    <script src="<%=info.getReqUrl()%>/jquery/jquery.min.js"></script>
        <link rel="stylesheet" href="<%=info.getReqUrl()%>/css/jScrollbar.jquery.css" type="text/css" />
	<script src="<%=info.getReqUrl()%>/jquery/jquery.mousewheel.js"></script>
	<script src="<%=info.getReqUrl()%>/jquery/jquery-ui-draggable.js"></script>
        <script src="<%=info.getReqUrl()%>/jquery/jscroll.js"></script>
        <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/bse.js?v=<%=info.getJsVersion()%> " > </script>
  </head>
        <%String logId=String.valueOf(info.getLogId());
        String onfocus="cook('"+logId+"')";
        %>
 <body onfocus="<%=onfocus%>">
<table cellpadding="2" cellspacing="0" width="100%">
<tr><td valign="top"><b style="font-size:10px;"><%=hdrnme%>  </b></td></tr>
</table>
<table width="100%"  cellpadding="0" cellspacing="0">
  <tr>  
<%if(view.equals("Y")){
  if(byridnLst!=null && byridnLst.size()>0){
  %>
  <tr>
  <td valign="top">
    <div id="main">
  <table class="boardgrid"   style="width:100%">
  <tr>
  <th>Buyer</th><th>Packet No.</th><th>Dis</th><th>Offer Discout</th><th>Mem Dis</th>
  <th>Net dis</th><th>Quot</th><th>Offer Price</th><th>Mem Price</th><th>Net Price</th>
  </tr>
  <%for (int i = 0; i< byridnLst.size(); i++) {
  String nme_idn=(String)byridnLst.get(i);
  ArrayList byrdtlLst=(ArrayList)dataDtl.get(nme_idn);
  %>
  <%for (int j = 0; j< byrdtlLst.size(); j++) {
  HashMap byrdata = new HashMap();
  byrdata=(HashMap)byrdtlLst.get(j);
  if(j==0){
  %>
  <tr><td><%=util.nvl((String)byrdtl.get(nme_idn))%></td>
  <%}else{%>
  <tr><td></td>
  <%}%>
  <td><label style="color:Maroon"> <%=byrdata.get("vnm")%></label></td>
<td align="right"> <label style="color:Maroon"> <%=byrdata.get("dis")%></label></td>
<td align="right"><label style="color:Blue"><%=byrdata.get("offer_dis")%></label></td>
<td align="right"><label style="color:red"><%=byrdata.get("mr_dis")%></label></td>
<td align="right"><label ><%=byrdata.get("net_dis")%></label></td>
<td align="right"><%=byrdata.get("quot")%></td>
<td align="right"><%=byrdata.get("offer_rte")%></td>
<td align="right"><%=byrdata.get("cmp")%></td>
<td align="right"><%=byrdata.get("net_rte")%></td>
  <%}%>
  </tr>
  <%}%>
  </table></div>
  </td></tr>
  <%}else{%>
  <tr><td valign="top" class="hedPg">
Sorry No Result Found</td></tr>
  <%}}%>
  </table>
  </body>
</html>
  
  