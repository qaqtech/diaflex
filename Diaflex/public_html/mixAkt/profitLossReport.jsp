<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page errorPage="../error_page.jsp" %>
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import="java.util.ArrayList,java.util.ArrayList,java.util.HashMap,java.sql.ResultSet,ft.com.*, java.util.Date, java.net.URLEncoder"%>

<jsp:useBean id="util" class="ft.com.JspUtil" scope="page" />
<jsp:useBean id="info" class="ft.com.InfoMgr" scope="session" />
<jsp:useBean id="gtMgr" class="ft.com.GtMgr" scope="session" />
<%@taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>


<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
<link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/bse.css?v=<%=info.getJsVersion()%>"/>
<link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/general.css?v=<%=info.getJsVersion()%>"/>
      <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/style.css?v=<%=info.getJsVersion()%>"/>
             <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/Validation.js?v=<%=info.getJsVersion()%> " > </script>
<script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/ajax.js?v=<%=info.getJsVersion()%> " > </script>
 <script language="javaScript" type="text/javascript" src="<%=info.getReqUrl()%>/calendar/calendar.js"></script>
   <link href="<%=info.getReqUrl()%>/calendar/calendar.css" rel="stylesheet" type="text/css" />

    <title>Profit Loss Report</title>
    
</head>
        <%String logId=String.valueOf(info.getLogId());
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
    <td height="5" valign="top" class="greyline"><img src="../images/grey_linebig.png" width="685" height="5" align="right" /></td>
  </tr>
  <tr>
  <td valign="top" class="hedPg">
  <table cellpadding="1" cellspacing="5"><tr><td valign="middle">
  <img src="../images/line.jpg" border="0" /></td><td valign="middle"> <span class="pgHed">Profit Loss Report</span> </td>
</tr></table>
  </td>
  </tr>
  
<tr>
  <td valign="top" class="tdLayout">
  <html:form action="mixAkt/profitLossReport.do?method=View" method="post" >
  <table>
  <tr><td>Date </td> <td>
  <html:text property="value(dtefr)" styleClass="txtStyle"  styleId="dtefr"  size="12"  /> <a href="#" style="margin-top:5px;"  onClick="setYears(1900, '<%=info.getCurrentYear()%>' );showCalender(this, 'dtefr');">
                <img src="<%=info.getReqUrl()%>/images/calender.png"></a>
  </td>
  <td>
  <html:text property="value(dteto)" styleClass="txtStyle"  styleId="dteto"  size="12"  /> <a href="#" style="margin-top:5px;"  onClick="setYears(1900, '<%=info.getCurrentYear()%>' );showCalender(this, 'dteto');">
                <img src="<%=info.getReqUrl()%>/images/calender.png"></a>
  </td> </tr>
  
  <tr> <td>Buyer</td>
      <td nowrap="nowrap" colspan="2">
    <%
    String sbUrl ="ajaxRptAction.do?method=buyerSugg";
    %>
    <input type="text" id="nme" name="nme" autocomplete="off" class="sugBox"
      onKeyUp="doCompletion('nme', 'nmePop', 'ajaxRptAction.do?method=buyerSugg', event)" 
      onKeyDown="setDown('nmePop', 'nmeID', 'nme',event)"/>
    <img src="../images/addrow.jpg" width="17" height="17" title="Click To get Names" onclick="doCompletionMobile('nme', 'nmePop', 'ajaxRptAction.do?method=buyerSugg')">
    <input type="hidden" name="nmeID" id="nmeID" value="">
     <div style="position: absolute;">
      <select id="nmePop" name="nmePop"
        style="display:none;300px;" 
        class="sugBoxDiv"
        onKeyDown="setDown('nmePop', 'nmeID', 'nme',event)" 
        onDblClick="setVal('nmePop', 'nmeID', 'nme', event);hideObj('nmePop')" 
        onClick="setVal('nmePop', 'nmeID', 'nme', event);" 
        size="10">
      </select>
   </div>
  </td></tr>
  <tr><td colspan="3"><jsp:include page="/genericSrch.jsp" /> </td> </tr>
 <tr><td colspan="3" align="center"><html:submit property="value(submit)" styleClass="submit" value="View" /> </td> </tr>
  </table>
  </html:form>
  </td></tr>
  <tr>
  <td valign="top" class="tdLayout">
  <% 
  String view = (String)request.getAttribute("view");
  if(view!=null){
  ArrayList pktDtlList = (ArrayList)request.getAttribute("PKTDTLLIST");
  if(pktDtlList!=null && pktDtlList.size()>0){%>
  <table class="grid1">
  <tr><th>Buyer Name</th><th>Box Type</th><th>Box Id</th><th>Sal Qty</th>
  <th>Sal Cts</th><th>Sal Avg</th><th>Sal Amt</th><th>Base Avg</th><th>Base Amt</th><th>Profit/Loss</th>
  </tr>
  <%for(int i=0;i<pktDtlList.size();i++){
  HashMap pktDtl = (HashMap)pktDtlList.get(i);
  String stt = (String)pktDtl.get("STT");
  String bgColor="";
  if(stt.equals("L"))
  bgColor="Red";
  
  %>
  <tr><td><%=pktDtl.get("NME")%></td><td><%=pktDtl.get("BOXTYP")%></td>
  <td><%=pktDtl.get("BOXID")%></td><td align="right"><%=pktDtl.get("SALQTY")%></td>
  <td align="right"><%=pktDtl.get("SALCTS")%></td><td align="right"><%=pktDtl.get("SALAVG")%></td>
  <td align="right"><%=pktDtl.get("SALAMT")%></td><td align="right"><%=pktDtl.get("BSEAVG")%></td>
  <td align="right"><%=pktDtl.get("BSEAMT")%></td>
  <td align="right" bgcolor="<%=bgColor%>"><%=pktDtl.get("DIFF")%></td>
  </tr>
  <%}%>
  </table>
  <%}else{%>
  Sorry no result found..
  <%}}%>
  </td></tr>
    <tr><td>
    <jsp:include page="/footer.jsp"/>
    </td></tr>
    </table>
     <%@include file="/calendar.jsp"%>
    
    </body>
</html>