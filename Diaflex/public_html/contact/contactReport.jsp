<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page errorPage="../error_page.jsp" %>
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import="java.util.ArrayList,java.util.ArrayList,ft.com.*,java.util.HashMap, java.sql.*, java.util.Enumeration, java.util.logging.Level"%>
<jsp:useBean id="info" class="ft.com.InfoMgr" scope="session" />
<jsp:useBean id="util" class="ft.com.JspUtil" scope="page" />
<%@taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<html>
  <head>
 <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
  
  <title>Contact Report</title>
  <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/bse.js?v=<%=info.getJsVersion()%> " > </script>
<link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/bse.css?v=<%=info.getJsVersion()%>"/>
<link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/general.css?v=<%=info.getJsVersion()%>"/>


 
      <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/style.css?v=<%=info.getJsVersion()%>"/>
  </head>
  <%
  HashMap dbinfo = info.getDmbsInfoLst();
      DBUtil dbutil = new DBUtil();
    DBMgr db = new DBMgr(); 
    db.setCon(info.getCon());
    dbutil.setDb(db);
    dbutil.setInfo(info);
    db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));db.setDbUsr(info.getDbUsr());
    dbutil.setLogApplNm(info.getLoApplNm());	
    int accessidn=dbutil.updAccessLog(request,response,"Contact", "Contact Srch Form");
        String logId=String.valueOf(info.getLogId());
        String onfocus="cook('"+logId+"')";
        %>
 <body onfocus="<%=onfocus%>" onload="MM_preloadImages('images/faunalogo.png')" topmargin="0" leftmargin="0" rightmargin="0" bottommargin="0">

  <div id="backgroundPopup"></div>
<input type="hidden" name="webUrl" id="webUrl" value="<%=dbinfo.get("REP_URL")%>"/>
<input type="hidden" name="cnt" id="cnt" value="<%=dbinfo.get("CNT")%>"/>
<input type="hidden" name="repUrl" id="repUrl" value="<%=dbinfo.get("HOME_DIR")%>"/>
<input type="hidden" name="repPath" id="repPath" value="<%=dbinfo.get("REP_PATH")%>"/>
<input type="hidden" name="accessidn" id="accessidn" value="<%=accessidn%>"/>
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
  <img src="../images/line.jpg" border="0" /></td><td valign="middle"> <span class="pgHed">
  Contact Report
  </span></td></tr></table></td></tr>
   <tr>
  <td valign="top" class="hedPg">
   <table >
  <tr><td> Sales persons </td>
  <td><%
   ResultSet rs = db.execSql("emp", "select distinct nvl(emp_idn, 0) emp_idn, byr.get_nm(emp_idn) emp from mnme order by 2", new ArrayList());
    %>
  <select name="emp" id="emp">
   <option value="-1">ALL</option>
 <%     while(rs.next()){
   String empIdn = rs.getString(1);
    String empNme = rs.getString(2);
    %>  
   
          
          <option value="<%=empIdn%>"><%=empNme%></option>
      <%}
      rs.close();
      %>
      </select>
  
  
    </td></tr>
    <tr><td>Contact Type</td>
   <td>
   
    <%
      
     ResultSet rs1 = db.execSql("typ", "select distinct typ from mnme where typ <> 'EMPLOYEE' order by typ", new ArrayList());
    %>
    <select name="typ" id="typ">
      <option value="ALL">ALL</option>
 <%     while(rs1.next()){
   String typ = rs1.getString(1);
    %>  
    
          
          <option value="<%=typ%>"><%=typ%></option>
      <%}
      rs1.close();
      %>
      </select>
     </td></tr>
     <tr><td colspan="2"><input type="button" Class="submit" value="Create Report" onclick="openCumReport()" /></td></tr>
    </table>
  </td></tr></table>
  </body>
</html>