<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page errorPage="../error_page.jsp" %>
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import="java.util.ArrayList,java.util.ArrayList,java.util.HashMap, java.sql.*, java.util.Enumeration, java.util.logging.Level"%>
	<jsp:useBean id="info" class="ft.com.InfoMgr" scope="session" />
	<jsp:useBean id="util" class="ft.com.JspUtil" scope="page" />
<%@taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>
 
<html>
  <head>
 <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
  
  <title>Sale Discount Report</title>
  <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/bse.js?v=<%=info.getJsVersion()%> " > </script>
  <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/bse.css?v=<%=info.getJsVersion()%>"/>
  <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/ajax.js?v=<%=info.getJsVersion()%> " > </script>
  <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/style.css?v=<%=info.getJsVersion()%>"/>
  <script language="javaScript" type="text/javascript" src="<%=info.getReqUrl()%>/calendar/calendar.js"></script>
   <link href="<%=info.getReqUrl()%>/calendar/calendar.css" rel="stylesheet" type="text/css" />
         <script src="<%=info.getReqUrl()%>/PopScripts/jquery-1.2.6.min.js" type="text/javascript"></script>
<script type="text/javascript" src="<%=info.getReqUrl()%>/PopScripts/popup.js?v=<%=info.getJsVersion()%> " > </script>
<link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/general.css?v=<%=info.getJsVersion()%>"/>
  </head>
  <%
  String view = util.nvl((String)request.getAttribute("view"));
  String viewFilter = util.nvl((String)request.getAttribute("viewFilter"));
  ArrayList pktList = ((ArrayList)session.getAttribute("pktListEMP")== null)?new ArrayList():(ArrayList)session.getAttribute("pktListEMP");
  ArrayList itemHdr = ((ArrayList)session.getAttribute("itemHdrEMP")== null)?new ArrayList():(ArrayList)session.getAttribute("itemHdrEMP");
  String prvEMP = "";
  String byrId = util.nvl((String)request.getAttribute("byrId"));
  String empId = util.nvl((String)request.getAttribute("empId"));
  String deptFrm = util.nvl((String)request.getAttribute("deptFrm"));
  String deptto = util.nvl((String)request.getAttribute("deptto"));
  ArrayList deptList=new ArrayList();
  HashMap prp = info.getPrp();
  deptList = (ArrayList)prp.get("DEPTV");
  
  %>
  
 <body topmargin="0" leftmargin="0" rightmargin="0" bottommargin="0">
<input type="hidden" id="reqUrl" name="reqUrl" value="<%=info.getReqUrl()%>"/>
<div id="popupContactASSFNL">
<table class="popUpTb">
<tr><td height="5%">&nbsp;&nbsp;&nbsp;<html:button property="value(close)" styleClass="submit"  onclick="MKEmptyPopup('SC');disablePopupASSFNL();" value="Close"  /></td></tr>
<tr><td valign="top" height="95%">
<iframe name="SC" class="frameStyle"   align="middle" frameborder="0">
</iframe></td></tr>
</table>
</div>
<div id="backgroundPopup"></div>


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
  <img src="../images/line.jpg" border="0" /></td><td valign="middle"> <span class="pgHed">Sale Discount Report</span> </td>
    </tr></table>
  </td>
  </tr>
  
  
  <tr><td valign="top" class="tdLayout">
  <html:form action="report/orclRptAction.do?method=fetchSaleDisReport" onsubmit="return validateSaleDisRpt();" method="post">
  <table class="grid1">
  <tr>
  <td>Date </td>  
  <td>From <html:text property="value(frmdte)" styleId="frmdte" name="orclReportForm" size="10" />&nbsp;<img src="<%=info.getReqUrl()%>/images/calender.png" onClick="setYears(2011, <%=info.getCurrentYear()%>);showCalender(this, 'frmdte');"> 
  &nbsp; To <html:text property="value(todte)" styleId="todte" name="orclReportForm"  size="10" />&nbsp;<img src="<%=info.getReqUrl()%>/images/calender.png" onClick="setYears(2011, <%=info.getCurrentYear()%>);showCalender(this, 'todte');"></td>
  <td colspan="5" align="center"><html:submit property="view" value="View" styleClass="submit"/></td>
  </tr>

  </table>
  </html:form>
  </td></tr>
  
<% if(!view.equals("")) { 
  
if(pktList.size()>0 || !viewFilter.equals("")) { %>
  
<tr>
<td class="hedPg">
Create Excel <img src="../images/ico_file_excel.png" onclick="goTo('orclRptAction.do?method=createXLSaleDsc&typ=EMP','','')" border="0"/>
<a href="orclRptAction.do?method=SaleDscPktList&byrId=<%=byrId%>&empId=<%=empId%>&deptFrm=<%=deptFrm%>&deptto=<%=deptto%>" target="_blank">Pkt Dtl</a>
</td>
</tr>

<tr><td valign="top" class="tdLayout">
<html:form action="report/orclRptAction.do?method=fetchSaleDisReportUsingFilter" onsubmit="" method="post">
    <table class="grid1">
    <tr><th colspan="7">Filter Search</th></tr>
    <tr>
    <td>Employee</td><td>
             <html:select property="value(empId)" name="orclReportForm"  onchange="getEmployeeGeneric(this)" styleId="empLst">
             <html:option value="" >--select--</html:option>
             <html:optionsCollection property="byrList" name="orclReportForm"  value="byrIdn" label="byrNme" />
             </html:select></td>
    <td>Buyer</td>
     <td nowrap="nowrap">      
     <html:text  property="value(partytext)" name="orclReportForm" styleId="partytext" style="width:230px" styleClass="sugBox" onkeypress="return disableEnterKey(event);" 
     onkeyup="doCompletionPartyNMEGeneric('partytext', 'nmePop', '../ajaxAction.do?1=1&isSrch=Y&UsrTyp=BUYER','empLst', event)"
      onkeydown="setDown('nmePop', 'party', 'partytext',event)" onclick="document.getElementById('partytext').autocomplete='off'"  />
      <img src="../images/addrow.jpg" width="17" height="17" title="Click To get Names" onclick="doCompletionPartyNMEGeneric('partytext', 'nmePop', '../ajaxAction.do?1=1&isSrch=Y&UsrTyp=BUYER','empLst')">
      <html:hidden property="value(byrId)" styleId="party"  />
  <div style="position: absolute;">
      <select id="nmePop" name="nmePop"
        style="display:none;300px;" 
        class="sugBoxDiv" 
        onKeyDown="setDown('nmePop', 'party', 'partytext',event)" 
        onDblClick="setVal('nmePop', 'party', 'partytext', event);hideObj('nmePop');" 
        onClick="setVal('nmePop', 'party', 'partytext', event)" 
        size="10">
      </select>
</div>       
</td>
  <td nowrap="nowrap"><jsp:include page="/genericSrch.jsp" /> </td>
  <td align="center"><html:submit property="value(filter)" value="Filter Data" styleClass="submit"/></td>
  <td align="center"><html:button property="value(reload)" value="Reset" styleClass="submit" onclick="goTo('orclRptAction.do?method=fetchSaleDisReportUsingFilter&reset=Y','','')"/></td>
  </tr>
  </table>
<table class="grid1">  
<tr>
<td class="hedPg">
<table class="grid1" id="dataTable">
<tr>
<%for(int i=0;i<itemHdr.size();i++){%>
<th><%=itemHdr.get(i)%></th>
<%}%>
</tr>

<%for(int i=0;i<pktList.size();i++){
String trId = "TR_"+(i+1);
HashMap pktPrpMap=(HashMap)pktList.get(i);
%>
<tr id=<%=trId%>>
<%for(int j=0;j<itemHdr.size();j++){
String emp = util.nvl((String)pktPrpMap.get("EMP"));
String itm = (String)itemHdr.get(j);
if(itm.equals("EMP") && !emp.equals("TOTAL")){ %>
<td align="center">
<a href="orclRptAction.do?method=fetchSaleDisReportByr&emp=<%=emp%>" id="DTL_<%=emp%>"  target="SC" title="Click Here To See Details" onclick="loadASSFNLPop('DTL_<%=emp%>')">
<%=util.nvl((String)pktPrpMap.get(itm))%>
</a></td>
<% } else { %>
<td align="right"><%=util.nvl((String)pktPrpMap.get(itm))%></td>
<% } } %>
</tr>
<% } %>
</table>
</td>
</tr>
</table>
</html:form>
</td>
</tr>
  
  
  <%} else { %>  
    
  <tr><td class="hedPg">Sorry no result found</td></tr>

  <% } } %>
  
  
  
  <tr>
    <td><jsp:include page="/footer.jsp" /> </td>
    </tr>
  </table>
  
   <%@include file="../calendar.jsp"%>
  </body>
</html>
  
  