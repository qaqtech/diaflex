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
    <title>Estimates Dues</title>
   <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/bse.css?v=<%=info.getJsVersion()%>"/>
    <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/style.css?v=<%=info.getJsVersion()%>"/>
    <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/bse.js?v=<%=info.getJsVersion()%> " > </script>
    <link href="<%=info.getReqUrl()%>/calendar/calendar.css" rel="stylesheet" type="text/css" />
   <script language="javaScript" type="text/javascript" src="<%=info.getReqUrl()%>/calendar/calendar.js"></script>
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
  <img src="../images/line.jpg" border="0" /></td><td valign="middle"> <span class="pgHed">Estimated Dues </span> </td>
</tr></table>
  </td>
  </tr>
  <tr>
   <td valign="top" class="tdLayout">
    <html:form action="receipt/receiptTransReport.do?method=EstimatedDues" method="post">
    <table>
    <tr><td>Date : </td><td>From</td>
    <td> <html:text property="value(dtefr)" styleClass="txtStyle"  styleId="dtefr"  size="12"  /> <a href="#" style="margin-top:5px;"  onClick="setYears(1900, '<%=info.getCurrentYear()%>' );showCalender(this, 'dtefr');">
    <img src="<%=info.getReqUrl()%>/images/calender.png"></a></td>
    <td>To</td>
    <td> <html:text property="value(dteto)" styleClass="txtStyle"  styleId="dteto"  size="12"  /> <a href="#" style="margin-top:5px;"  onClick="setYears(1900, '<%=info.getCurrentYear()%>' );showCalender(this, 'dteto');">
                <img src="<%=info.getReqUrl()%>/images/calender.png"></a></td>
                </tr>
    <tr><td colspan="2"><html:submit property="value(submit)" value="Fetch" styleClass="submit" /> </td></tr>
    </table>
  </html:form></td></tr>
   <tr>
   <td valign="top" class="tdLayout">
   <%
   HashMap dtlMap = (HashMap)session.getAttribute("ESDTLMAP");
   ArrayList recLst = (ArrayList)dtlMap.get("RECLST");
   ArrayList payLst = (ArrayList)dtlMap.get("PAYLST");
   %>
   <table>
   <tr><td><b>Received:</b></td><td><b>Pay:</b></td></tr>
  
   <%if((recLst!=null && recLst.size()>0) || (payLst!=null && payLst.size()>0)){ %>
   <tr><td colspan="2">
   Create Excel <img src="../images/ico_file_excel.png" onclick="goTo('receiptTransReport.do?method=EstimatedDuesExcel','','')" border="0"/> 
   </td></tr>
   <%}%>
    <tr>
   <%if(recLst!=null && recLst.size()>0){
   %>
   <td valign="top">
   <table class="grid1">
   <tr><th>Buyer Name</th><th>Ref Dte</th><th>Ref Idn</th><th>Cnt Idn</th><th>Dys</th><th>Due Dte</th><th>End Dys</th><th>Amount($)</th><th>Amount</th></tr>
   <%for(int i=0;i<recLst.size();i++){
   HashMap recDtl = (HashMap)recLst.get(i);
   %>
   <tr><td><%=recDtl.get("NME")%></td><td><%=recDtl.get("REF_DTE")%></td>
   <td><%=recDtl.get("REF_IDN")%></td><td><%=util.nvl((String)recDtl.get("CNTIDN"))%></td><td><%=recDtl.get("DYS")%></td>
   <td><%=recDtl.get("DUE_DTE")%></td><td><%=recDtl.get("ENDDYS")%></td>
  <td align="right"><%=recDtl.get("CUR")%></td> <td align="right"><%=recDtl.get("AMT")%></td></tr>
   <%}%>
   <tr><td><b>Total</b></td><td colspan="6"></td>
    <td align="right"><B><%=util.priceFormatter((String)dtlMap.get("RECUTTL"))%> </b></td>
   <td align="right"><B><%=util.priceFormatter((String)dtlMap.get("RECTTL"))%> </b></td></tr>
   </table>
   </td>
   <%}else{%>
  <td valign="top"> Sorry no result found</td>
   <%}%>
    <%if(payLst!=null && payLst.size()>0){
   %>
   <td valign="top">
   <table class="grid1">
   <tr><th>Buyer Name</th><th>Ref Dte</th><th>Ref Idn</th><th>Cnt Idn</th><th>Dys</th><th>Due Dte</th><th>End Dys</th><th>Amount($)</th><th>Amount</th></tr>
   <%for(int i=0;i<payLst.size();i++){
   HashMap payDtl = (HashMap)payLst.get(i);
   %>
   <tr><td><%=payDtl.get("NME")%></td><td><%=payDtl.get("REF_DTE")%></td><td><%=payDtl.get("REF_IDN")%></td>
     <td><%=util.nvl((String)payDtl.get("CNTIDN"))%></td><td><%=payDtl.get("DYS")%></td>
   <td><%=payDtl.get("DUE_DTE")%></td><td><%=payDtl.get("ENDDYS")%></td>
   <td align="right"><%=payDtl.get("CUR")%></td><td align="right"><%=payDtl.get("AMT")%></td></tr>
   <%}%>
 <tr><td><b>Total</b></td><td colspan="6"></td>
    <td align="right"><B><%=util.priceFormatter((String)dtlMap.get("PAYUTTL"))%> </b></td>
   <td align="right"><B><%=util.priceFormatter((String)dtlMap.get("PAYTTL"))%> </b></td></tr>
   </table>
   </td>
  <%}else{%>
  <td valign="top"> Sorry no result found</td>
   <%}%>
   </tr>
   </table>
  
   </td></tr>
   <tr><td><jsp:include page="/footer.jsp"/> </td></tr>
   </table>
    <%@include file="/calendar.jsp"%>
    </body>
</html>