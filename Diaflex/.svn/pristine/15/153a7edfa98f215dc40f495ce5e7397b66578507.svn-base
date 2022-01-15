<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page errorPage="../error_page.jsp" %>
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import="java.util.Vector,java.util.HashMap,java.sql.ResultSet,org.apache.commons.collections.iterators.IteratorEnumeration,ft.com.*, java.util.Date, java.util.ArrayList, ft.com.dao.*, java.util.Enumeration"%>
<jsp:useBean id="info" class="ft.com.InfoMgr" scope="session" />
<jsp:useBean id="util" class="ft.com.JspUtil" scope="page" />

<%@taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>Properties</title>
  <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/bse.css?v=<%=info.getJsVersion()%>"/>
    <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/style.css?v=<%=info.getJsVersion()%>"/>
    <script language="javaScript" type="text/javascript" src="<%=info.getReqUrl()%>/scripts/bse.js?v=<%=info.getJsVersion()%>"></script>
     <script language="javaScript" type="text/javascript" src="<%=info.getReqUrl()%>/calendar/calendar.js"></script>
   <link href="<%=info.getReqUrl()%>/calendar/calendar.css" rel="stylesheet" type="text/css" />

  </head>
        <%String logId=String.valueOf(info.getLogId());
        String onfocus="cook('"+logId+"')";
        %>
 <body onfocus="<%=onfocus%>" onload="MM_preloadImages('images/faunalogo.png')" topmargin="0" leftmargin="0" rightmargin="0" bottommargin="0">
<table width="100%"  border="0" align="center" cellpadding="0" cellspacing="0">
   <tr>
  <td valign="top" class="hedPg">
  <table cellpadding="1" cellspacing="5"><tr><td valign="middle">
  <img src="../images/line.jpg" border="0" /></td><td valign="middle"> <span class="pgHed">Properties</span> </td>
</tr></table>
  </td>
  </tr>
  <%

  HashMap propertyList = (HashMap)session.getAttribute("propertyList");
   
 %>
 <tr>
  <td valign="top" class="hedPg">
  <html:form action="masters/mpairproperty.do?method=save" method="post" >
  <html:hidden property="value(rule_idn)" />
  
  <%
  ArrayList listtable=(ArrayList)session.getAttribute("listtable");
  ArrayList listBox=new ArrayList();
  Enumeration e=new IteratorEnumeration(propertyList.keySet().iterator());
  if(propertyList!=null && propertyList.size() > 0){
  int size = propertyList.size();
  String pid=null;
  String srt_fr="";
  String srt_to="";
  %>
  <table class="grid1">

    <tr><th>Sr</th>
        <th>Property</th>
        <th>From</th>
        <th>To</th>
        </tr>
<%for(int i=0;i<size;i++){
String prp=(String)e.nextElement();
String ptyName=(String)propertyList.get(prp);
String fldId1 = "frm_"+(i+1);
String fldId2 = "to_"+(i+1);
if(listtable!=null && listtable.size()>0){
for(int j=0;j<listtable.size();j++){
listBox=(ArrayList)listtable.get(j);
 pid = (String)listBox.get(0);
 if(prp.equals(pid)) {
srt_fr= (String)listBox.get(1);
srt_to= (String)listBox.get(2);
j=listtable.size();
} else{
srt_fr="";
srt_to="";
 }
}
}
%>
<tr><td><%=i+1%></td><td> <%=ptyName%></td><td><input type="text" name="<%=fldId1%>" value="<%=srt_fr%>"></td> 
<td><input type="text" name="<%=fldId2%>" value="<%=srt_to%>"></td></tr>
<%}%>
<tr> <td colspan="3" align="center"> <html:submit property="value(save)" styleClass="submit" value="Save" /> 
</td></tr>
</table>
  <%}%>
  </html:form>
 </td>
 </tr></table>
 <%@include file="../calendar.jsp"%>
</body>
</html>
