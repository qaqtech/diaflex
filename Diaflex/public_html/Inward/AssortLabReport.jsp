<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page errorPage="../error_page.jsp" %>
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import="java.util.ArrayList,java.util.HashMap, java.util.Date, java.util.ArrayList, ft.com.dao.*"%>
<jsp:useBean id="info" class="ft.com.InfoMgr" scope="session" />
<jsp:useBean id="util" class="ft.com.JspUtil" scope="page" />

<%@taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>Assort Lab Report </title>
    <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/bse.css?v=<%=info.getJsVersion()%>"/>
    <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/style.css?v=<%=info.getJsVersion()%>"/>
     <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/assortScript.js?v=<%=info.getJsVersion()%> " > </script>
       <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/memoScript.js?v=<%=info.getJsVersion()%> " > </script>
       <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/bse.js?v=<%=info.getJsVersion()%> " > </script>
 </head>
<%
int accessidn=Integer.parseInt(util.nvl((String)request.getAttribute("accessidn"),"0"));
HashMap dbinfo = info.getDmbsInfoLst();
String cnt = (String)dbinfo.get("CNT");
        String logId=String.valueOf(info.getLogId());
        String onfocus="cook('"+logId+"')";
        %>
 <body onfocus="<%=onfocus%>" onload="MM_preloadImages('images/faunalogo.png')" topmargin="0" leftmargin="0" rightmargin="0" bottommargin="0">
<table width="100%"  border="0" align="center" cellpadding="0" cellspacing="0" class="mainbg">
<%    String p_iss_id = util.nvl((String)request.getAttribute("issueId"));
    if(p_iss_id.equals("")){%>
  <tr>
    <td height="103" valign="top">
   <jsp:include page="/header.jsp" />
    
    </td>
  </tr><%}%>
 <%
    String s="123";
 %>
  <tr>
    <td height="5" valign="top" class="greyline"><img src="<%=info.getReqUrl()%>/images/grey_linebig.png" width="685" height="5" align="right" /></td>
  </tr>
<tr>
<td valign="top" class="hedPg">
<table cellpadding="1" cellspacing="5"><tr><td valign="middle">
<img src="../images/line.jpg" border="0" /></td><td valign="middle"> <span class="pgHed">Assort_Lab_Report</span> </td>
</tr></table>
</td>
</tr>
<tr><td>
<html:form  action="Inward/assortlabreport.do?method=save" >
<input type="hidden" name="webUrl" id="webUrl" value="<%=dbinfo.get("REP_URL")%>"/>
<input type="hidden" name="repPath" id="repPath" value="<%=dbinfo.get("REP_PATH")%>"/>
<input type="hidden" name="cnt" id="cnt" value="<%=cnt%>"/>
<input type="hidden" name="repUrl" id="repUrl" value="<%=dbinfo.get("HOME_DIR")%>"/>
  <input type="hidden" name="accessidn" id="accessidn" value="<%=accessidn%>"/>
  <table class="grid1" style="margin-left:30px;">
      <tr><td>Issue Id</td><td><html:text property="value(p_iss_id)"  styleId="p_iss_id"  onchange="getdetails(this)" name="AssortLabReportForm" /> </td></tr>
      <tr><td>Qty</td><td><html:text property="value(qty)" readonly="true" styleId="qty" name="AssortLabReportForm" /> </td></tr>
      <tr><td>Cts</td><td><html:text property="value(cts)" readonly="true" styleId="cts" name="AssortLabReportForm" /> </td></tr>
      <tr><td><html:radio property="value(all)" styleId="sttC" value="CUR"/>Current </td> <td>
      <html:radio property="value(all)" styleId="sttA"  value="ALL"/>ALL
      </td></tr>
      <tr><td>Report Page</td><td>
      <html:select property="value(memoPag)" name="AssortLabReportForm" styleId="memoPg">
      <html:optionsCollection property="issuePrintList" label="FORM_TTL" value="FORM_NME" />
      </html:select>
      </td></tr>
      <tr><td colspan="2"><html:button styleClass="submit" property="value(openWn)" value="Create Report" onclick="openIssueReport()" /> </td></tr>
      
  </table>
</html:form>
</td></tr>
  <tr>
    <td><jsp:include page="/footer.jsp" /> </td>
    </tr>
  </table>
  </body>
</html>