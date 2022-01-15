<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page errorPage="../error_page.jsp" %>
<%@ page contentType="text/html;charset=windows-1252"%>
<jsp:useBean id="info" class="ft.com.InfoMgr" scope="session" />
<jsp:useBean id="util" class="ft.com.JspUtil" scope="page" />
<%@ page import="java.util.ArrayList,java.util.HashMap,java.sql.ResultSet,ft.com.*, java.util.Date, java.util.ArrayList, ft.com.dao.*,java.io.File"%>
<%@taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>Pricing History</title>
   </head>
    <body>
    <html:form action="pri/priHistory.do?method=Fetch" method="POST">
    <html:hidden property="value(grpNme)" styleId="grpNme"  />
  
    <table>
    <tr><td>
    <jsp:include page="/genericSrch.jsp" /></td></tr>
     <tr><td >
 <table><tr>
    <td nowrap="nowrap">Date From : </td><td nowrap="nowrap">
    <html:text property="value(dteFM)" styleClass="txtStyle" name="priceHistoryForm" styleId="dtefr"  size="12"  /> <a href="#" style="margin-top:5px;"  onClick="setYears(1900, '<%=info.getCurrentYear()%>' );showCalender(this, 'dtefr');">
                <img src="<%=info.getReqUrl()%>/images/calender.png"></a></td>
                <td nowrap="nowrap"> To : </td><td nowrap="nowrap">
    <html:text property="value(dteTO)" styleClass="txtStyle" name="priceHistoryForm" styleId="dteto"  size="12"  /> <a href="#"  onClick="setYears(1900, '<%=info.getCurrentYear()%>' );showCalender(this, 'dteto');">
                <img src="<%=info.getReqUrl()%>/images/calender.png"></a></td>
 </tr></table>
 </td></tr>
    <tr>
 <td> <html:submit property="value(fectch)" value="Fecth" styleClass="submit" /></td>
 </tr></table>
    </html:form>
    
    </body>
</html>