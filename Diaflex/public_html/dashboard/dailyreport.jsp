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
 <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
  
  <title>Reports</title>
    <script language="javaScript" type="text/javascript" src="<%=info.getReqUrl()%>/scripts/ajax.js?v=<%=info.getJsVersion()%>"></script>
    <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/bse.css?v=<%=info.getJsVersion()%>"/>
    <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/style.css?v=<%=info.getJsVersion()%>"/>
    <script type="text/javascript" src="<%=info.getReqUrl()%>/PopScripts/popup.js?v=<%=info.getJsVersion()%> " > </script>
     <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/bse.js?v=<%=info.getJsVersion()%> " > </script>
     <script>  
          var asdf = false;  
          function StartTime(){  
            if(asdf)clearTimeout(asdf)  
            asdf = setTimeout("RefreshPage()",1000);  
          }  
          function RefreshPage(){		  
             clearTimeout(asdf)  
            if(document.getElementById('CB').checked)  
             window.location.href = document.getElementById('link').value+'&Chec';
          }  
          function LoadPage(){  
            var findCheck = document.location.href.split("&Chec");  
            if(findCheck.length == 2){  
              document.getElementById('CB').checked=true;  
              StartTime()  
            }  
          }  
        </script>  
  </head>
        <%String logId=String.valueOf(info.getLogId());
        String onfocus="cook('"+logId+"')";
        %>
 <body onfocus="<%=onfocus%>" onload="LoadPage()">
   <%
  ArrayList  empidnLst = (ArrayList)request.getAttribute("empidnLst");
  HashMap  empdtl = (HashMap)request.getAttribute("empdtl");
  HashMap  dataDtl = (HashMap)request.getAttribute("dataDtl");
  String link = (String)request.getAttribute("link");
  String pgitmidn = (String)request.getAttribute("pgitmidn");
  link=link.replaceAll("&Chec","");
  String  view = (String)request.getAttribute("view");%>
    <input type="hidden" name="link" id="link" value="<%=link%>">
<table cellpadding="2" cellspacing="0">
<tr><td valign="top"><b style="font-size:10px;"><bean:write property="reportNme" name="userrightsform" /></b></td></tr>
</table>
<table width="100%"  cellpadding="0" cellspacing="0">
  <tr>  
<%if(view.equals("Y")){
  if(empidnLst!=null && empidnLst.size()>0){
  %>
  <tr>
  <td valign="top">
  <table class="boardgrid"   style="width:100%">
  <tr>
  <th>Sales Person</th><th>Buyer</th><th>Qty</th><th>Cts</th><th>Value</th>
  </tr>
  <%for (int i = 0; i< empidnLst.size(); i++) {
  String emp_idn=(String)empidnLst.get(i);
  ArrayList byrdtlLst=(ArrayList)dataDtl.get(emp_idn);
  %>
  <%for (int j = 0; j< byrdtlLst.size(); j++) {
  HashMap byrdata = new HashMap();
  byrdata=(HashMap)byrdtlLst.get(j);
  if(j==0){
  %>
  <tr><td><%=util.nvl((String)empdtl.get(emp_idn))%></td>
  <%}else{%>
  <tr><td></td>
  <%}%>
  <td><%=util.nvl((String)byrdata.get("BYR"))%></td>
  <td align="right"><%=util.nvl((String)byrdata.get("QTY"))%></td>
  <td align="right"><%=util.nvl((String)byrdata.get("CTS"))%></td>
  <td align="right"><%=util.nvl((String)byrdata.get("VLU"))%></td>
  <%}%>
  </tr>
  <%}%>
  </table>
  </td></tr>
  <%}else{%>
  <tr><td valign="top" class="hedPg">
Sorry No Result Found</td></tr>
  <%}}%>
  </table>
  </body>
</html>
  
  