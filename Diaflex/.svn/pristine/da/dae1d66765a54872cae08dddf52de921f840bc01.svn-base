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
    <title>User Rights</title>
  <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/bse.css?v=<%=info.getJsVersion()%>"/>
    <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/style.css?v=<%=info.getJsVersion()%>"/>
     <script language="javaScript" type="text/javascript" src="<%=info.getReqUrl()%>/scripts/ajax.js?v=<%=info.getJsVersion()%>"></script>
       <script language="javaScript" type="text/javascript" src="<%=info.getReqUrl()%>/calendar/calendar.js"></script>
   <link href="<%=info.getReqUrl()%>/calendar/calendar.css" rel="stylesheet" type="text/css" />
   <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/bse.js?v=<%=info.getJsVersion()%> " > </script>

  </head>
<%
 String usrIdn = (String)request.getAttribute("usrIdn");
 ArrayList boarditemLst = (ArrayList)request.getAttribute("boarditemLst");
 HashMap data=new HashMap();
        String logId=String.valueOf(info.getLogId());
        String onfocus="cook('"+logId+"')";
        %>
 <body onfocus="<%=onfocus%>" onload="MM_preloadImages('images/faunalogo.png')" topmargin="0" leftmargin="0" rightmargin="0" bottommargin="0">
<table width="100%"  border="0" align="center" cellpadding="0" cellspacing="0">
   <tr>
    <td height="103" valign="top">
   <jsp:include page="/header.jsp" />
    
    </td>
  </tr>
  <tr>
    <td height="5" valign="top" class="greyline"><img src="../images/grey_linebig.png" width="685" height="5" align="right" /></td>
  </tr>
  <tr>
  <td valign="top" class="hedPg">
  <table cellpadding="1" cellspacing="5"><tr><td valign="middle">
  <img src="../images/line.jpg" border="0" /></td><td valign="middle"> <span class="pgHed">Assign Dashboard Page Rights</span> </td>
</tr></table>
  </td>
  </tr>
  <tr><td class="tdLayout" valign="top">
 <table><tr><td>
 <%if(boarditemLst!=null && boarditemLst.size()>0){%>
<table class="grid1">
<tr> 
<th>Pages</th><th></th>
</tr>
<%for(int k=0;k<boarditemLst.size();k++){
data=new HashMap();
data=(HashMap)boarditemLst.get(k);
String pgidn=(String)data.get("PGIDN");
String pgitmidn=(String)data.get("PGITMIDN");
String fldName = usrIdn+"_"+pgitmidn+"_"+pgidn;
String fldVal = "value("+fldName+")";
String onChange = "saveUserRight("+pgidn+","+pgitmidn+","+usrIdn+",this)";
%>
<tr><td><%=(String)data.get("ITM")%></td><td align="center">
<html:checkbox  property="<%=fldVal%>" value="<%=fldName%>" name="userrightsform" onchange="<%=onChange%>"/></td>
</tr>
<%}%>
</table>
<%}%>
</td>
</tr>
</table>
</td>
</tr>
   <tr>
    <td><jsp:include page="/footer.jsp" /> </td>
    </tr>
</table>  
</body>
</html>