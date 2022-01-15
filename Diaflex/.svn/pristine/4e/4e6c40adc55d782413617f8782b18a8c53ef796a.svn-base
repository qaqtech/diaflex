<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
 
<%@ page errorPage="../error_page.jsp" %>
<%@ page contentType="text/html;charset=windows-1252"%>
<jsp:useBean id="util" class="ft.com.JspUtil" scope="page" />
<jsp:useBean id="info" class="ft.com.InfoMgr" scope="session" />
<%@ page import="java.util.ArrayList,java.util.HashMap,java.sql.ResultSet,ft.com.*, java.util.Date, java.util.ArrayList, ft.com.dao.*"%>
<%@taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>Master Entries</title>
   <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/bse.css?v=<%=info.getJsVersion()%>"/>
      <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/style.css?v=<%=info.getJsVersion()%>"/>
      <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/bse.js?v=<%=info.getJsVersion()%> " > </script>
        <link href="<%=info.getReqUrl()%>/calendar/calendar.css" rel="stylesheet" type="text/css" />
   <script language="javaScript" type="text/javascript" src="<%=info.getReqUrl()%>/calendar/calendar.js"></script>
      <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/Validation.js?v=<%=info.getJsVersion()%> " > </script>


  </head>
<%
String logId=String.valueOf(info.getLogId());
        String onfocus="cook('"+logId+"')";
        DBUtil dbutil = new DBUtil();
        DBMgr db = new DBMgr(); 
        db.setCon(info.getCon());
        dbutil.setDb(db);
        dbutil.setInfo(info);
        db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));db.setDbUsr(info.getDbUsr());
        dbutil.setLogApplNm(info.getLoApplNm());
%>
    <body onfocus="<%=onfocus%>"  topmargin="0" leftmargin="0" rightmargin="0" bottommargin="0">
   <table width="100%"  border="0" align="center" cellpadding="0" cellspacing="0" class="mainbg">
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
  <img src="../images/line.jpg" border="0" /></td><td valign="middle"> <span class="pgHed">Payment Parties</span> </td>
</tr></table>
  </td>
  </tr>
  
  <tr>
  <td valign="top" class="tdLayout">
     <table class="grid1">
  <tr>

  <th>Party ID</th><th>Party Name</th><th>Short Name</th><th>Mobile</th><th>Email</th><th>Action</th></tr>
  <%
  ArrayList GlMstList = (ArrayList)request.getAttribute("GlMstList");
  ArrayList TransFullList = (ArrayList)request.getAttribute("TransFullList");
  for(int i=0;i<GlMstList.size();i++){
  HashMap glMst = (HashMap)GlMstList.get(i);
  String glIdn =util.nvl((String)glMst.get("IDN"));
  %>
  <tr><td><%=glIdn%></td>
  <td><%=util.nvl((String)glMst.get("NME"))%></td>
  <td><%=util.nvl((String)glMst.get("CD"))%></td><td><%=util.nvl((String)glMst.get("MBL"))%></td>
  <td><%=util.nvl((String)glMst.get("EML"))%></td>
  <td align="center"><A href="masterEntriesAction.do?method=Edit&IDN=<%=glIdn%>"> Edit </a>
   <%if(TransFullList.indexOf(glIdn)!=-1){%>
   &nbsp;|&nbsp;<a href="masterEntriesAction.do?method=Edit">Delete</a>
   <%}%>
   </td>
  </tr>
   <%}%>
    </table>
    <tr>
   <td><jsp:include page="/footer.jsp" /> </td>
   </tr>
    </table>
    
    </body>
</html>