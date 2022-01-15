<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page errorPage="../error_page.jsp" %>
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import="java.util.ArrayList,java.util.HashMap,java.sql.ResultSet,ft.com.*, java.util.Date, java.util.ArrayList, ft.com.dao.*"%>

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
<script language="javaScript" type="text/javascript" src="<%=info.getReqUrl()%>/scripts/bse.js?v=<%=info.getJsVersion()%>"></script>
  </head>
<%
ArrayList webroledscList = (ArrayList)request.getAttribute("webroledscList");
ArrayList webmainmenuList = (ArrayList)request.getAttribute("webmainmenuList");
ArrayList webidnmenuList = (ArrayList)request.getAttribute("webidnmenuList");
HashMap webroleDtl = (HashMap)request.getAttribute("webroleDtl");
HashMap webmenuDtl = (HashMap)request.getAttribute("webmenuDtl");
HashMap websubmenuDtl = (HashMap)request.getAttribute("websubmenuDtl");
String mainmenu="";
String menuidn="";
String roleidn="";
String roledsc="";
String idn="";
String mainmenuidn="";
String submenu="";
String submenudtl="";

        String logId=String.valueOf(info.getLogId());
        String onfocus="cook('"+logId+"')";
        %>
 <body onfocus="<%=onfocus%>" onload="MM_preloadImages('images/faunalogo.png')" topmargin="0" leftmargin="0" rightmargin="0" bottommargin="0">
<table width="100%"  border="0" align="center" cellpadding="0" cellspacing="0" class="mainbg">
  <tr>
    <td height="103" valign="top">
   <jsp:include page="/header.jsp" />
    </td>
  </tr>
 
  <tr>
    <td height="5" valign="top" class="greyline"><img src="<%=info.getReqUrl()%>/images/grey_linebig.png" width="685" height="5" align="right" /></td>
  </tr>
  <tr>
  <td valign="top" class="hedPg">
  <table cellpadding="1" cellspacing="5"><tr><td valign="middle">
  <img src="../images/line.jpg" border="0" /></td><td valign="middle"> <span class="pgHed">Assign Web Roles</span> </td>
</tr></table>
  </td>
  </tr>
  <tr><td class="tdLayout" valign="top">
 <table>
 <tr>
 <td>
<table class="grid1">
<tr><th>Menu List</th>
<%for(int i=0;i<webroledscList.size();i++){

%>
<th><%=webroledscList.get(i)%></th>
<%}%>
</tr>
	<%
        for(int i =0 ; i<webmainmenuList.size();i++){
       mainmenu=(String)webmainmenuList.get(i);
        %>
       <tr><td><span><b><%=mainmenu%></b></span></td>
       <%for(int j=0;j<webroledscList.size();j++){
      %>
             <td></td>
      <%}%>
       </tr>
	
        <%
        if(websubmenuDtl!=null)
        for(int j=0 ; j< websubmenuDtl.size();j++){
       submenu=(String)websubmenuDtl.get(mainmenu+"_"+webidnmenuList.get(j));
        if(submenu!=null){
        %>
        <tr><td>&nbsp;&nbsp;&nbsp;&nbsp;<%=submenu%></td>
        <%for(int n=0;n<webroledscList.size();n++){
          roledsc=(String)webroledscList.get(n);
          roleidn=String.valueOf(webroleDtl.get(roledsc));
           for(int x =0 ; x<webidnmenuList.size();x++){
           mainmenuidn=String.valueOf(webidnmenuList.get(x));
           mainmenuidn=mainmenu+"_"+mainmenuidn;
           menuidn=String.valueOf(webmenuDtl.get(mainmenuidn));
           submenudtl=(String)websubmenuDtl.get(mainmenuidn);
           if(!menuidn.equals("") && submenu.equals(submenudtl)){
           x=webidnmenuList.size();
           }
           }
           String fldName = menuidn+"_NA_"+roleidn ;
           String onChange = "saveWebMenuRole("+menuidn+","+roleidn+",this)";
           String fldVal = "value("+fldName+")";
           %>
             <td><center><html:checkbox  property="<%=fldVal%>" value="<%=fldName%>" name="WebMenuRoleForm" onchange="<%=onChange%>"/></center></td>
      <%}%>
        
        </tr>
      <%}
      }
      }
      %>
</table>
 
 </td>
 </tr>
 </table>
  </td>
  </tr>
  </table>
   
</body>
</html>