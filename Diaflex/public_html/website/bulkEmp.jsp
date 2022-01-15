<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page errorPage="../error_page.jsp" %>
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import="java.util.ArrayList,java.util.HashMap,java.sql.ResultSet,ft.com.*, java.util.Date, java.util.Enumeration, java.util.ArrayList, ft.com.dao.*"%>

<jsp:useBean id="util" class="ft.com.JspUtil" scope="page" />
<jsp:useBean id="info" class="ft.com.InfoMgr" scope="session" />

<%@taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<%@taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>Web Menu Role Details</title>
  <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/bse.css?v=<%=info.getJsVersion()%>"/>
    <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/style.css?v=<%=info.getJsVersion()%>"/>
     <script language="javaScript" type="text/javascript" src="<%=info.getReqUrl()%>/scripts/ajax.js?v=<%=info.getJsVersion()%>"></script>
       <script language="javaScript" type="text/javascript" src="<%=info.getReqUrl()%>/calendar/calendar.js"></script>
   <link href="<%=info.getReqUrl()%>/calendar/calendar.css" rel="stylesheet" type="text/css" />
   <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/bse.js?v=<%=info.getJsVersion()%> " > </script>

  </head>
<%

ArrayList webroledscList = (ArrayList)request.getAttribute("webroledscList");
HashMap webroleDtl = (HashMap)request.getAttribute("webroleDtl");
ArrayList partydtl = (ArrayList)request.getAttribute("partydtl");
String empidn = (String)request.getAttribute("emp_idn");
String roleidn="";
String roledsc="";
String dsc="";
String emp_idn="";
        String logId=String.valueOf(info.getLogId());
        String onfocus="cook('"+logId+"')";
        %>
 <body onfocus="<%=onfocus%>" onload="MM_preloadImages('images/faunalogo.png')" topmargin="0" leftmargin="0" rightmargin="0" bottommargin="0">
<table width="100%"  border="0" align="center" cellpadding="0" cellspacing="0">
 
  <tr>
    <td height="5" valign="top" class="greyline"><img src="<%=info.getReqUrl()%>/images/grey_linebig.png" width="685" height="5" align="right" /></td>
  </tr>

  <tr><td class="tdLayout" valign="top">
 <table>
 <tr>
 <td>
 <%if(partydtl.size()<1) {%>
 <span class="redLabel" > Sorry No Employee In The List</span>
 <%
 }else{
 %>
<table class="grid1">
<tr><th>Employee List</th>
<%for(int i=0;i<webroledscList.size();i++){
roledsc=(String)webroledscList.get(i);
%>
<th><span style="padding-top:0px;"><%=roledsc%></span><input type="checkbox" name="checkAllRole" id="checkAll_<%=roledsc%>"  onclick="checkALLRole('<%=roledsc%>','<%=partydtl.size()%>','<%=i%>','<%=empidn%>')"  /></th>
<% }%>
</tr>

<%

for(int i=0;i<partydtl.size();i++)
{
HashMap pktDtl = (HashMap)partydtl.get(i);
String byrNme=(String)pktDtl.get("nme");
String usrIdn=(String)pktDtl.get("usrIdn");
%>
<tr>

<td><%=byrNme%></td>
<%
for(int j=0;j<webroledscList.size();j++){
roledsc=(String)webroledscList.get(j);
roleidn=String.valueOf(webroleDtl.get(roledsc));
String fldName = usrIdn+"_NA_"+roleidn ;
String checkRolemenu =roledsc+"_"+i;
           String onChange = "saveBulkWebMenuRole("+usrIdn+","+roleidn+",this)";
           String fldVal = "value("+fldName+")";
           %>
             <td><center><html:checkbox  property="<%=fldVal%>" value="<%=fldName%>" name="BulkRoleForm" onchange="<%=onChange%>" styleId="<%=checkRolemenu%>"/>
             </center></td>

<%}%>
</tr>
<%}
%>


</table>
 <%}%>
 </td>
 </tr>
 </table>
  </td>
  </tr>
  </table>
   
</body>
</html>