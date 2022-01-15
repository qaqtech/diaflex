<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page errorPage="../error_page.jsp" %>
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import="java.util.ArrayList,java.util.HashMap,java.sql.ResultSet,ft.com.*, java.util.Date, java.util.ArrayList, ft.com.dao.*"%>
<jsp:useBean id="info" class="ft.com.InfoMgr" scope="session" />
<jsp:useBean id="util" class="ft.com.JspUtil" scope="page" />

<%@taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<%@taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>Web User Roles</title>
  <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/bse.css?v=<%=info.getJsVersion()%>"/>
    <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/style.css?v=<%=info.getJsVersion()%>"/>
     <script language="javaScript" type="text/javascript" src="<%=info.getReqUrl()%>/scripts/ajax.js?v=<%=info.getJsVersion()%>"></script>
       <script language="javaScript" type="text/javascript" src="<%=info.getReqUrl()%>/calendar/calendar.js"></script>
   <link href="<%=info.getReqUrl()%>/calendar/calendar.css" rel="stylesheet" type="text/css" />

  </head>
        <%String logId=String.valueOf(info.getLogId());
        String onfocus="cook('"+logId+"')";
        %>
 <body onfocus="<%=onfocus%>" >
    <html:form action="/contact/webaccess.do?method=saveRole" method="post" onsubmit="return webUsrRole(this)" > 
<html:hidden property="value(usrIdn)" name="webAccessForm" />
<table   cellpadding="0" cellspacing="0" >
  
 <%  
 ArrayList roleList = (ArrayList)request.getAttribute("roleDtlList");
 String usrIdn = (String)session.getAttribute("usrIdn");
 %>
  
  <%if(request.getAttribute("msg")!=null){%>
  <tr><td><span class="redLabel"> <%=request.getAttribute("msg")%></span></td> </tr>
  <%}%>
  <tr><td class="tdLayout" valign="top">
  <table>
  <tr><td><B>Role Details</b></td> </tr>
  
  <tr><td align="left">
   <%
  if(roleList!=null && roleList.size()>0){
  String pTyp = "1";
  for(int i=0;i<roleList.size();i++){
  HashMap roleDtl = (HashMap)roleList.get(i);
  String roleIdn = (String)roleDtl.get("roleIdn");
  String fldNme = (String)roleDtl.get("FldNme");
  String roleDsc = (String)roleDtl.get("rolDsc");
  String Fldtyp = (String)roleDtl.get("FldTyp");
  String typ = (String)roleDtl.get("typ");
  String fldValue = "value("+fldNme+")" ;
  if(pTyp.equals("1"))
  pTyp = typ;
  
  if(typ.equals("") || !(pTyp.equals(typ))){
  %>
  <table> <tr>
  <%}%>
   <%if(!typ.equals("")){%><td><%=typ%></td><%}%>
   
   <%if(Fldtyp.equals("RD")){%>
   <td> <html:radio  property="<%=fldValue%>" value="<%=roleIdn%>"  name="webAccessForm"  /></td>
   <%}else{%>
   <td><html:checkbox  property="<%=fldValue%>" value="<%=roleIdn%>"  name="webAccessForm"  /></td>
   <%}%>
   <td><%=roleDsc%></td>
 <%  if(typ.equals("") || !(pTyp.equals(typ))){%>
   </tr></table>
   <%pTyp = typ;
   }%>
  <%}}%>
  </td></tr>
  <tr><td><html:submit property="value(submit)" value="Save Role Changes" styleClass="submit" /></td></tr>
 
  </table>
  </td>
  </tr>
   </table>
   </html:form>
</body>
</html>