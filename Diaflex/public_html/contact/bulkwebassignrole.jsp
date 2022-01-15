<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page errorPage="../error_page.jsp" %>
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import="java.util.ArrayList,java.util.HashMap,java.sql.ResultSet,ft.com.*, java.util.Date, java.util.Enumeration, java.util.ArrayList, ft.com.dao.*"%>
<jsp:useBean id="info" class="ft.com.InfoMgr" scope="session" />
<jsp:useBean id="util" class="ft.com.JspUtil" scope="page" />

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
ArrayList chkNmeIdnList = (info.getChkNmeIdnList() == null)?new ArrayList():(ArrayList)info.getChkNmeIdnList();
String roleidn="";
String roledsc="";
String dsc="";
String emp_idn="";
        String logId=String.valueOf(info.getLogId());
        String onfocus="cook('"+logId+"')";
        %>
 <body onfocus="<%=onfocus%>" onload="MM_preloadImages('images/faunalogo.png')" topmargin="0" leftmargin="0" rightmargin="0" bottommargin="0">
<table width="100%"  border="0" align="center" cellpadding="0" cellspacing="0">

  <tr><td class="tdLayout" valign="top">
 <table>
 <tr>
 <td>
 <%if(chkNmeIdnList.size()<1) {%>
 <span class="redLabel" > Sorry No Employee In The List</span>
 <%
 }else{
 %>
<table class="grid1">
<tr><th nowrap="nowrap">Employee List</th>
<th>
<input type="checkbox" name="checkAllRole" id="checkAll_webusers"  onclick="checkALLBulkwebusers('webusers','<%=partydtl.size()%>')" title="Bulk Activity Of Users A/IA"  />
<b>(Web User A/IA)</b>
</th>
<%for(int i=0;i<webroledscList.size();i++){
roledsc=(String)webroledscList.get(i);
%>
<th><span style="padding-top:0px;"><%=roledsc%></span>
<input type="checkbox" name="checkAllRole" id="checkAll_<%=roledsc%>"  onclick="checkALLBulkRole('<%=roledsc%>','<%=partydtl.size()%>','<%=i%>')"  />
</th>
<% }%>
</tr>

<%

for(int i=0;i<partydtl.size();i++)
{
HashMap pktDtl = (HashMap)partydtl.get(i);
String byrNme=(String)pktDtl.get("nme");
String usrIdn=(String)pktDtl.get("usrIdn");
String checkUsr ="webusers_"+i;
           String onChangeUsr = "checkBulkwebusers("+usrIdn+",this)";
           String fldValUsr = "value("+usrIdn+")";
%>
<tr>

<td nowrap="nowrap"><%=byrNme%></td>
<td>
<html:checkbox  property="<%=fldValUsr%>" value="A" name="advContactForm" onchange="<%=onChangeUsr%>" styleId="<%=checkUsr%>"  title="UnCheck To In Active User"/>
</td>
<%
for(int j=0;j<webroledscList.size();j++){
roledsc=(String)webroledscList.get(j);
roleidn=String.valueOf(webroleDtl.get(roledsc));
String fldName = usrIdn+"_NA_"+roleidn ;
String checkRolemenu =roledsc+"_"+i;
           String onChange = "saveBulkWebAdvRole("+usrIdn+","+roleidn+",this)";
           String fldVal = "value("+fldName+")";
           %>
             <td><center><html:checkbox  property="<%=fldVal%>" value="<%=fldName%>" name="advContactForm" onchange="<%=onChange%>" styleId="<%=checkRolemenu%>"/>
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