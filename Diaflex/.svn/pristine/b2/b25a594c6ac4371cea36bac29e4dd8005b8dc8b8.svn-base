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
    <title>Stock Properties Update</title>
    <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/bse.css?v=<%=info.getJsVersion()%>"/>
      <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/style.css?v=<%=info.getJsVersion()%>"/>
      <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/memoScript.js?v=<%=info.getJsVersion()%> " > </script>
       <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/bse.js?v=<%=info.getJsVersion()%> " > </script>
        </head>
        <%String logId=String.valueOf(info.getLogId());
        String onfocus="cook('"+logId+"')";
        %>
 <body onfocus="<%=onfocus%>">
  <div class="memo">
  <%
    ArrayList pktDtlList = (ArrayList)request.getAttribute("pktDtlList");
   if(pktDtlList !=null && pktDtlList.size() > 0){
  %>
  <table border="0" class="Orange" cellspacing="0" cellpadding="0" >
  <tr><th class="Orangeth">Packet Code</th><th class="Orangeth">Remark</th></tr>
  <%for(int i=0;i<pktDtlList.size();i++){
    ArrayList pktList = (ArrayList)pktDtlList.get(i);
  %>
  <tr><td><%=util.nvl((String)pktList.get(0))%></td><td>
  <%=util.nvl((String)pktList.get(1))%>
  </td></tr>
  <%}%>
  </table>
  <%}else{%>
  sorry no result found.
  <%}%>
  </div>
  </body>
</html>