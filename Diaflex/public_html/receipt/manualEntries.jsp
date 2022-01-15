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
    <title>Manual Entries</title>
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
  <img src="../images/line.jpg" border="0" /></td><td valign="middle"> <span class="pgHed">Manual Entries</span> </td>
</tr></table>
  </td>
  </tr>
  <% if(request.getAttribute("msg")!=null){%>
        <tr><td class="tdLayout" height="15"><span class="redLabel"> <%=request.getAttribute("msg")%></span></td></tr>
  <%}%>
  <tr>
  <td valign="top" class="tdLayout">
  <html:form action="receipt/manualEntriesAction.do?method=save" method="post" onsubmit="return validateManualEntries()">
  <table cellpadding="2" cellspacing="2">
   <tr>
   <td>Buyer List </td> <td>:</td>
   <td> 
   <html:select property="value(byrIdn)"  styleId="byrIdn" name="manualEntriesForm"   >
        <html:optionsCollection property="byrList" name="manualEntriesForm" label="byrNme" value="byrIdn" />
    </html:select>
   </td></tr>
  <tr>
   <td>Type </td> <td>:</td>
   <td><html:select property="value(type)"  styleId="type" name="manualEntriesForm" >
   <html:option value="SOLD">Sold</html:option><html:option value="PURCHASE">Purchase</html:option>
   </html:select>
   </td></tr>
   <tr>
   <td>Sub Type </td><td>:</td><td>
   <html:select property="value(subtype)"  styleId="subType" name="manualEntriesForm" >
   <html:option value="BUYER">Buyer</html:option>
   <html:option value="E">External</html:option>
   </html:select>
   </td></tr>
   
    <tr>
   <td>Ref Date </td><td>:</td><td>
     <html:text property="value(refDte)" styleClass="txtStyle"  styleId="refDte"  size="12"  /> <a href="#"  onClick="setYears(1900, '<%=info.getCurrentYear()%>' );showCalender(this, 'refDte');">
                <img src="<%=info.getReqUrl()%>/images/calender.png"></a>
   </td></tr>
     <tr>
   <td>Ref Type </td><td>:</td><td>
    <html:select property="value(refTyp)"  styleId="byrIdn" name="manualEntriesForm" >
   <html:option value="SAL">Sale</html:option>
   <html:option value="DLV">Delivery</html:option>
   <html:option value="PUR">PURCHASE</html:option>
   </html:select>
   </td></tr>
     <tr>
   <td> <span class="redLabel">*</span>Ref Idn </td><td>:</td><td>
   <html:text property="value(refIdn)" styleClass="txtStyle"  styleId="refIdn"  size="12" />
   </td></tr>
     <tr>
   <td>Days </td><td>:</td><td> 
    <html:select property="value(dys)"  styleId="dys" name="manualEntriesForm"   >
        <html:optionsCollection property="mtrmsList" name="manualEntriesForm" label="nme" value="nme" />
    </html:select>
   </td></tr>
    <tr>
   <td> <span class="redLabel">*</span>Carat </td><td>:</td><td><html:text property="value(cts)" styleClass="txtStyle"  styleId="cts"  size="12" /> </td></tr>
    <tr>
   <td><span class="redLabel">*</span>Amount($) </td><td>:</td><td><html:text property="value(amt)" styleClass="txtStyle"  styleId="amt"  size="12" />  </td></tr>
    <tr>
   <td>Exchange Rate </td><td>:</td><td>
   <html:text property="value(xrt)" styleClass="txtStyle"  styleId="xrt"  value="1" size="12" />
   </td></tr>
   <tr><td></td><td colspan="2" align="left">
   <html:submit property="value(submit)" value="Submit" styleClass="submit" />
   </td></tr>
  </table>
  </html:form>
   </td></tr>
   <tr>
   <td><jsp:include page="/footer.jsp" /> </td>
   </tr>
  </table>
    
    
    </body>
     <%@include file="/calendar.jsp"%>
</html>