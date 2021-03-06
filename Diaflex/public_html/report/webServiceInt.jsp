<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
 
<%@ page errorPage="../error_page.jsp" %>
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import="java.util.ArrayList,java.util.HashMap,java.sql.ResultSet,ft.com.*, java.util.Date, java.util.ArrayList, ft.com.dao.*"%>
<jsp:useBean id="util" class="ft.com.JspUtil" scope="page" />
<jsp:useBean id="info" class="ft.com.InfoMgr" scope="session" />
<%@taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>
<%@taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
 
<%@taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>

<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>Web service Integration</title>
    <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/bse.css?v=<%=info.getJsVersion()%>"/>
    <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/style.css?v=<%=info.getJsVersion()%>"/>
    <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/ajax.js?v=<%=info.getJsVersion()%>" ></script>
    <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/bse.js?v=<%=info.getJsVersion()%>" ></script>
<script language="javaScript" type="text/javascript" src="<%=info.getReqUrl()%>/calendar/calendar.js"></script>
   <link href="<%=info.getReqUrl()%>/calendar/calendar.css" rel="stylesheet" type="text/css" />  
  </head>
        <%String logId=String.valueOf(info.getLogId());
        String onfocus="cook('"+logId+"')";
        %>
 <body onfocus="<%=onfocus%>" onload="MM_preloadImages('images/faunalogo.png')"  onkeypress="return disableEnterKey(event);" topmargin="0" leftmargin="0" rightmargin="0" bottommargin="0">
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
  <img src="../images/line.jpg" border="0" /></td><td valign="middle"> <span class="pgHed">Web Service Integration</span> </td>
</tr></table>
  </td>
  </tr>
   <% if(request.getAttribute("msg")!=null){%>
<tr><td valign="top" class="tdLayout">
<span class="redLabel"><%=request.getAttribute("msg")%></span>
</td></tr>
<%}%>
  <tr>
  <td valign="top" class="hedPg">
  <html:form action="report/orclRptAction.do?method=callwebservice" method="post" onsubmit="return validatewebservice();">
  <table>
    <tr><td>Party</td><td nowrap="nowrap">
    <%
    String sbUrl = info.getReqUrl() + "/AjaxAction.do?UsrTyp=BUYER";
    %>
    <input type="text" id="nme" name="nme" autocomplete="off" class="sugBox"
      onKeyUp="doCompletion('nme', 'nmePop', '../ajaxAction.do?UsrTyp=BUYER', event)"
      onKeyDown="setDown('nmePop', 'nmeID', 'nme',event)" 
      />
      <img src="../images/addrow.jpg" width="17" height="17" title="Click To get Names" onclick="doCompletionMobile('nme', 'nmePop', '../ajaxAction.do?UsrTyp=BUYER')">
    <html:hidden property="value(nmeID)" styleId="nmeID"/>
  <div style="position: absolute;">
      <select id="nmePop" name="nmePop"
        style="display:none;300px;" 
        class="sugBoxDiv" 
        onKeyDown="setDown('nmePop', 'nmeID', 'nme',event)" 
        onDblClick="setVal('nmePop', 'nmeID', 'nme', event);hideObj('nmePop')" 
        onClick="setVal('nmePop', 'nmeID', 'nme', event)" 
        size="10">
      </select>
</div>
  </td></tr>
    <tr><td>Type</td><td>
  <html:select property="value(typ)" styleId="typ" name="orclReportForm" >
    <html:option value="WH">Hold</html:option>
    <html:option value="WA">Approve</html:option>
  </html:select>
  </td></tr>
  <tr><td>Packet Number</td><td><html:textarea property="value(vnm)" styleId="vnm" name="orclReportForm"/> </td></tr>
  <tr><td colspan="2" align="center"> <html:submit property="value(submit)" styleClass="submit" value="Call Web Service" /> </td> </tr>
  </table>
  </html:form>
  </td></tr>
  <% 
  String view = util.nvl((String)request.getAttribute("view"));
  if(!view.equals("")){
  
  %>
  <tr>
  <td valign="top" class="hedPg">
  <display:table name="sessionScope.orclReportForm.pktDtlList" requestURI="" decorator="ft.com.Decortor"  class="displayTable">
    <display:column property="sr"  title="SR NO."  />
    <display:column property="dte"  title="Date"  />
    <display:column property="memo"  title="Memo Id"   media="html" />
    <display:column property="memoidn"  title="Memo Id"   media="excel" />
    <display:column property="byr"  title="Party Name"  />
    <display:column property="typ"   title="Memo Type"  />
    <display:column property="stt"   title="Memo Status"  />
    <display:column property="qty"   title="Pcs"  />
    <display:column property="cts"   title="Cts"  />
    <display:column property="vlu"   title="Value"  />
    <display:column property="exp_dys" title="Expiry days"  />
    <display:column property="pktdtl" title="Packet Dtl"  media="html"  />
    
 </display:table>
  </td></tr>
  
  <%}%>
  <tr>
    <td><jsp:include page="/footer.jsp" /> </td>
    </tr>
  </table>
  <%@include file="../calendar.jsp"%>
  </body>
</html>
