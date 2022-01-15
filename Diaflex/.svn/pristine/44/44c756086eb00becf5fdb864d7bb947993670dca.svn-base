<!DOCTYPE html>
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page errorPage="../error_page.jsp" %>
<%@ page import="java.util.ArrayList,java.util.ArrayList, java.util.Iterator, java.sql.Date,java.util.HashMap, ft.com.marketing.PacketLookupForm" %>
<jsp:useBean id="util" class="ft.com.JspUtil" scope="page" />
<jsp:useBean id="info" class="ft.com.InfoMgr" scope="session" />
<%@taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>
<%@taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<%
ArrayList hhbpfList = new ArrayList();
 hhbpfList =(ArrayList)request.getAttribute("hhbpfList");
 String reqUrl=info.getReqUrl();
%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
         <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/bse.css?v=<%=info.getJsVersion()%>"/>
      <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/style.css?v=<%=info.getJsVersion()%>"/>
      <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/memoScript.js?v=<%=info.getJsVersion()%> " > </script>
      <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/bse.js?v=<%=info.getJsVersion()%> " > </script>
    </head>
    <body>
     <table class="grid1" align="left" id="dataTable" style="margin:15px 0 0 10px;">
 
    <tr>
    <th><label title="SR NO">Sr No.</label></th>
    <th><label title="Vendor">Buyer Name</label></th>
    <th><label title="Vendor">Date</label></th>
    </tr>
    <% 
    if(hhbpfList != null && hhbpfList.size() >0 ){
    int count = 0;
    for (int i = 0; i< hhbpfList.size(); i++) { 
    HashMap hhbpfMap = (HashMap) hhbpfList.get(i);
    String pg = (String) hhbpfMap.get("pg");
    String dt_tm = (String) hhbpfMap.get("dt_tm");
    String byr = (String) hhbpfMap.get("byr");
   
    %>
    <tr>
    

    <td><%= ++count%></td>
    <td><%=byr%></td>
    <td><%=dt_tm%></td>
</tr>
    
     <% } %>
     <% } else { %>
  <tr> <td>  No Result fount</td></tr>
     <% } %>
     
   
  </table>
    
    </body>
</html>