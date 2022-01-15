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
    <title>Outstanding Transaction</title>
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
  <img src="../images/line.jpg" border="0" /></td><td valign="middle"> <span class="pgHed">Outstanding Transaction Wise</span> </td>
</tr></table>
  </td>
  </tr>
  <tr>
   <td valign="top" class="tdLayout">
    Create Excel <img src="../images/ico_file_excel.png" onclick="goTo('receiptTransReport.do?method=OutstandingTransExcel','','')" border="0"/> 
   </td></tr>
   <tr>
   <td valign="top" class="tdLayout">
   <%
   HashMap dtlMap = (HashMap)session.getAttribute("OUTTANDTLMAP");
   ArrayList purLst = (ArrayList)dtlMap.get("PURLST");
   ArrayList soldLst = (ArrayList)dtlMap.get("SOLDLST");
   %>
   <table>
   <tr><td><b>Purchase:</b></td><td><b>Sold:</b></td></tr>
   <tr>
   
   <%if(purLst!=null && purLst.size()>0){
   %>
   <td valign="top">
   <table class="grid1">
   <tr><th>Buyer Name</th><th>Ref Dte</th><th>Ref Idn</th><th>Cnt Idn</th><th>Dys</th><th>Due Dte</th><th>End Dys</th><th>Amount($)</th><th>Amount</th></tr>
   <%for(int i=0;i<purLst.size();i++){
   HashMap purDtl = (HashMap)purLst.get(i);
   %>
   <tr><td><%=purDtl.get("NME")%></td><td><%=purDtl.get("REF_DTE")%></td>
   <td><%=purDtl.get("REF_IDN")%></td><td><%=util.nvl((String)purDtl.get("CNTIDN"))%></td><td><%=purDtl.get("DYS")%></td>
   <td><%=purDtl.get("DUE_DTE")%></td><td><%=purDtl.get("ENDDYS")%></td>
   <td align="right"><%=purDtl.get("CUR")%></td><td align="right"><%=purDtl.get("AMT")%></td></tr>
   <%}%>
   <tr><td><b>Total</b></td><td colspan="6"></td>
   <td align="right"><B><%=(String)dtlMap.get("PURUTTL")%> </b></td>
     <td  align="right"><B><%=util.priceFormatter((String)dtlMap.get("PURTTL"))%> </b></td>
   </tr>
   </table>
   </td>
   <%}else{%>
  <td valign="top"> Sorry no result found</td>
   <%}%>
    <%if(soldLst!=null && soldLst.size()>0){
   %>
   <td valign="top">
   <table class="grid1">
   <tr><th>Buyer Name</th><th>Ref Dte</th><th>Ref Idn</th><th>Cnt Idn</th><th>Dys</th><th>Due Dte</th><th>End Dys</th><th>Amount($)</th><th>Amount</th> </tr>
   <%for(int i=0;i<soldLst.size();i++){
   HashMap soldDtl = (HashMap)soldLst.get(i);
   %>
   <tr><td><%=soldDtl.get("NME")%></td><td><%=soldDtl.get("REF_DTE")%></td><td><%=soldDtl.get("REF_IDN")%></td>
    <td><%=util.nvl((String)soldDtl.get("CNTIDN"))%></td><td><%=soldDtl.get("DYS")%></td>
   <td><%=soldDtl.get("DUE_DTE")%></td><td><%=soldDtl.get("ENDDYS")%></td>
   <td align="right"><%=soldDtl.get("CUR")%></td> <td align="right"><%=soldDtl.get("AMT")%></td></tr>
   <%}%>
    <tr><td><b>Total</b></td><td colspan="6"></td>
   <td align="right"><B><%=(String)dtlMap.get("SOLDUTTL")%> </b></td>
     <td  align="right"><B><%=util.priceFormatter((String)dtlMap.get("SOLDTTL"))%> </b></td>
   </tr>   
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
    </body>
</html>