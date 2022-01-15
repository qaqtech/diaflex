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
    <title>stockTallyList</title>
    <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/bse.css?v=<%=info.getJsVersion()%>"/>
    <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/style.css?v=<%=info.getJsVersion()%>"/>
    <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/memoScript.js?v=<%=info.getJsVersion()%> " > </script>
          <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/bse.js?v=<%=info.getJsVersion()%> " > </script>
  </head>
        <%String logId=String.valueOf(info.getLogId());
        String onfocus="cook('"+logId+"')";
        %>
 <body onfocus="<%=onfocus%>">
   <%
ArrayList seqList = (ArrayList)session.getAttribute("seqLst");
%>
<table>
  <%
  if(request.getAttribute("RTNMSG")!=null){%>
   <tr> <td valign="top" class="hedPg" colspan="2"><span class="redLabel"><%=request.getAttribute("RTNMSG")%></span></td></tr>
  <%}else{%>
<%if(seqList.size()>0){%>
<tr>
  <html:form action="marketing/stockTallyhk.do?method=delete" method="post" >
  <td valign="top" class="hedPg">
  Select Sequence :
 <html:select property="value(seq)" name="stockTallyForm">
 <html:option value="" >-----All-----</html:option>
  <%for(int i=0 ; i < seqList.size();i++){
  String seq = (String)seqList.get(i);
 
  %>
  <html:option value="<%=seq%>"><%=seq%></html:option>
  <%}%>
  </html:select>
  </td><td>  <html:submit property="value(delete)" value="Delete" styleClass="submit" /></td>
  </html:form>
  </tr>
  <%}else{%>
<tr><td valign="top" class="hedPg" colspan="2">Sorry no result found </td></tr>
<%}}%>
</table>
 </body>
</html>