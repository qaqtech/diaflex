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
    <title>Transaction Party Wise</title>
   <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/bse.css?v=<%=info.getJsVersion()%>"/>
      <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/style.css?v=<%=info.getJsVersion()%>"/>
      <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/bse.js?v=<%=info.getJsVersion()%> " > </script>
        <link href="<%=info.getReqUrl()%>/calendar/calendar.css" rel="stylesheet" type="text/css" />
   <script language="javaScript" type="text/javascript" src="<%=info.getReqUrl()%>/calendar/calendar.js"></script></head>
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
  <img src="../images/line.jpg" border="0" /></td><td valign="middle"> <span class="pgHed">Party Wise Transaction </span> </td>
</tr></table>
  </td>
  </tr> <tr> 
   <td valign="top" class="tdLayout">
   <html:form action="receipt/receiptTransReport.do?method=TransDtlDteWise" method="post">
   <table>
   <tr> 
   <td>Buyer List </td> <td>:</td>  
   <td> 
   <html:select property="value(byrIdn)"  styleId="byrIdn" name="receiptTransReportForm"   >
        <html:optionsCollection property="partyList" name="receiptTransReportForm" label="byrNme" value="byrIdn" />
    </html:select>
   </td></tr>
   <tr><td colspan="3">
   <table>
   <tr><td> Date From :</td>
   <td>
   <html:text property="value(dtefr)" styleClass="txtStyle"  styleId="dtefr"  size="12"  /> <a href="#" style="margin-top:5px;"  onClick="setYears(1900, '<%=info.getCurrentYear()%>' );showCalender(this, 'dtefr');">
                <img src="<%=info.getReqUrl()%>/images/calender.png"></a>
    </td>
    <td>  To :</td>
   <td>
   <html:text property="value(dteto)" styleClass="txtStyle"  styleId="dteto"  size="12"  /> <a href="#" style="margin-top:5px;"  onClick="setYears(1900, '<%=info.getCurrentYear()%>' );showCalender(this, 'dteto');">
                <img src="<%=info.getReqUrl()%>/images/calender.png"></a>
    </td>
    
    </tr>
   
   </table>
   </td> </tr>
   <tr><td colspan="3"><html:submit property="value(submit)" value="Fetch" styleClass="submit" /></td></tr>
   
   </table></html:form>
   </td></tr>
  
   
 
   
   <%
   String view = (String)request.getAttribute("view");
   if(view!=null){
    ArrayList transDtlLst = (ArrayList)session.getAttribute("TRANSLISTDTE");
   if(transDtlLst!=null && transDtlLst.size()>0){ %>
   <tr>
   <td valign="top" class="tdLayout">
    Create Excel <img src="../images/ico_file_excel.png" onclick="goTo('receiptTransReport.do?method=TransDtlDteExcel','','')" border="0"/> 
   </td></tr>
     <tr> 
   <td valign="top" class="tdLayout"> 
    <table class="grid1">
   <tr><th>Party Name</th><th>Trans Dte</th><th>Type</th><th>Sub Type</th><th>Ref Typ</th><th>Ref Idn</th><th>Cnt Idn</th><th>Due Dte</th><th>Credit($)</th><th>Debit($)</th><th>Credit</th><th>Debit</th><th>Xrt</th><th>Confirmed By</th><th>Remark</th></tr>
   <% for(int i=0;i<transDtlLst.size();i++){
   HashMap transMap = (HashMap)transDtlLst.get(i); 
   String ent_seq = util.nvl((String)transMap.get("ENT_SEQ"));
    String xrt = util.nvl((String)transMap.get("XRT"));
    String typ = util.nvl((String)transMap.get("TYP"));
    String idn =  util.nvl((String)transMap.get("IDN"));
    String flg = util.nvl((String)transMap.get("FLG"));
    if(flg.equals("M"))
    %>  
 <tr> 
   <% if(flg.equals("M")){%>
  <td><B><%=transMap.get("NME")%></b></td>
  <%}else{%>
   <td><%=transMap.get("NME")%></td>
  <%}%>
  <td><%=transMap.get("T_DTE")%></td><td><%=transMap.get("TYP")%></td><td><%=transMap.get("SUBTYP")%></td>
  <td><%=transMap.get("REF_TYP")%></td><td><%=transMap.get("REF_IDN")%></td><td><%=transMap.get("CNTIDN")%></td><td><%=transMap.get("DUE_DTE")%></td>
   <td align="right"><%=util.nvl((String)transMap.get("CRUAMT"))%></td><td align="right"><%=util.nvl((String)transMap.get("DBUAMT"))%></td>
 <td align="right"><%=util.nvl((String)transMap.get("CRAMT"))%></td><td align="right"><%=util.nvl((String)transMap.get("DBAMT"))%></td><td><%=xrt%></td>
  <td align="right"><%=util.nvl((String)transMap.get("CFM"))%></td><td align="right"><%=util.nvl((String)transMap.get("RMK"))%></td>
 
  </tr>
 <%}%>
 <tr><td colspan="7" align="right"><b>Total</b></td>
 <td align="right"><b><%=(String)session.getAttribute("TTLUCRD")%></b></td> <td align="right"><b><%=(String)session.getAttribute("TTLUDBD")%></b></td>
 <td align="right"><b><%=util.priceFormatter((String)session.getAttribute("TTLCRD"))%></b></td> <td align="right"><b><%=util.priceFormatter((String)session.getAttribute("TTLDBD"))%></b></td>
  <td colspan="3"></td></tr>
  </table>
  </td></tr>
 <%}else{%>
   <tr> 
   <td valign="top" class="tdLayout"> Sorry no result found
   </td></tr>
  <%}}%>
  
 
    
     <tr><td><jsp:include page="/footer.jsp"/> </td></tr>
   </table>
    <%@include file="/calendar.jsp"%>
    </body>
</html>