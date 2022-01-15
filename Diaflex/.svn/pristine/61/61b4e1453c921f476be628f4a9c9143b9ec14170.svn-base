<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page errorPage="../error_page.jsp" %>
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import="java.util.ArrayList,java.util.HashMap,java.sql.ResultSet,ft.com.*, java.util.Date, java.util.ArrayList, ft.com.dao.*"%>
<jsp:useBean id="info" class="ft.com.InfoMgr" scope="session" />
<jsp:useBean id="util" class="ft.com.JspUtil" scope="page" />
<%@taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>
<%@taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>Copy Mapping</title>
        <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/bse.css?v=<%=info.getJsVersion()%>"/>
    <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/style.css?v=<%=info.getJsVersion()%>"/>
    <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/ajax.js?v=<%=info.getJsVersion()%>" ></script>
    <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/bse.js?v=<%=info.getJsVersion()%>" ></script>
<script language="javaScript" type="text/javascript" src="<%=info.getReqUrl()%>/calendar/calendar.js"></script>
   <link href="<%=info.getReqUrl()%>/calendar/calendar.css" rel="stylesheet" type="text/css" />
  </head>
        <%String logId=String.valueOf(info.getLogId());
        String onfocus="cook('"+logId+"')";
        %>
 <body onfocus="<%=onfocus%>" onkeypress="return disableEnterKey(event);" >
  <table width="100%"  border="0" align="center" cellpadding="0" cellspacing="0" >
   <%
   String del = (String)request.getAttribute("del");
   String copy = (String)request.getAttribute("copy");
   if(del!=null){
   %>
   <tr><td valign="top" class="hedPg"><span class="redLabel"><%=del%></span></td></tr>
   <%}if(copy!=null){%>
   <tr><td valign="top" class="hedPg"><span class="redLabel"><%=copy%></span></td></tr>
   <%}%>
   <tr>
   <td valign="top" class="hedPg">
   <html:form action="/contact/columnMap.do?method=copymap" method="post" enctype="multipart/form-data">
   <html:hidden property="value(currentnmeIdn)"  />
   <table>
   <tr><td align="center">Name :</td><td nowrap="nowrap">
    <input type="text" id="nme" name="nme" autocomplete="off" class="sugBox"
      onKeyUp="doCompletion('nme', 'nmePop', '../ajaxAction.do?1=1', event)"
      onKeyDown="setDown('nmePop', 'nmeID', 'nme',event)" 
      />
      <img src="../images/addrow.jpg" width="17" height="17" title="Click To get Names" onclick="doCompletionMobile('nme', 'nmePop', '../ajaxAction.do?1=1')">
    <html:hidden property="nmeID" styleId="nmeID"/>
  <div style="position: absolute;">
      <select id="nmePop" name="nmePop"
        style="display:none;300px;" 
        class="sugBoxDiv" 
        onKeyDown="setDown('nmePop', 'nmeID', 'nme',event)" 
        onDblClick="setVal('nmePop', 'nmeID', 'nme', event);hideObj('nmePop')" 
        onClick="setVal('nmePop', 'nmeID', 'nme', event)" 
        size="10">
      </select>
</div>
  </td></tr>
  <tr><td colspan="2" align="center"> <html:submit property="value(submit)" styleClass="submit" value="Copy Mapping" /> </td> </tr>
  </table>
   </html:form>
   </td>
   </tr>
   </table>
  </body>
</html>