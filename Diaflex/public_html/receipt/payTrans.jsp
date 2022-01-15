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
    <title>Payment Trans</title>
   <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/bse.css?v=<%=info.getJsVersion()%>"/>
      <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/style.css?v=<%=info.getJsVersion()%>"/>
      <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/bse.js?v=<%=info.getJsVersion()%> " > </script>
        <link href="<%=info.getReqUrl()%>/calendar/calendar.css" rel="stylesheet" type="text/css" />

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
  <img src="../images/line.jpg" border="0" /></td><td valign="middle"> <span class="pgHed">Recevice Invoice</span> </td>
</tr></table>
  </td>
  </tr>
    <tr><td valign="top" class="tdLayout">
    <html:form action="receipt/paymentTransAction.do?method=fetch" method="post">
     
  <table><tr><td>Buyer List </td> <td>:</td>
   <td> 
   <html:select property="value(byrIdn)"  styleId="byrIdn" name="paymentTransForm" onchange="RecRefIdn(this)"  >
           <html:option value="0" >--select--</html:option>
            <html:optionsCollection property="byrLst" name="paymentTransForm" 
            label="byrNme" value="byrIdn" />
    </html:select>
   </td>
   <td><html:submit property="value(submit)" value="Fetch" styleClass="submit" /> </td></tr>
   </table>
   
   </html:form>  
   </td></tr> 
    <tr><td valign="top" class="tdLayout"> 
   <%
   HashMap dtlMap = (HashMap)request.getAttribute("DTLMAP");
   if(dtlMap!=null && dtlMap.size()>0){ 
   ArrayList paidByLst = (ArrayList)dtlMap.get("PAID");
   
   %>
    <table cellpadding="2" cellspacing="2">
     <tr><td> 
     <table><tr><td>Paid By</td>
     <%for(int i=0;i<paidByLst.size();i++){
     HashMap paidMap = (HashMap)paidByLst.get(i);
     String NME = (String)paidMap.get("NME");
      String IDN = (String)paidMap.get("IDN");
     %>
     <td><input type="radio" name="paid" value="<%=IDN%>" /> </td><td><%=NME%></td>
     <%}%>
     </tr> 
     </table>
     </td></tr>
     <tr><td>
     <%
        ArrayList recByLst = (ArrayList)dtlMap.get("REC");
        if(recByLst!=null && recByLst.size()>0){
     %>
     <table class="grid1">
     <tr><th>Date</th><th>Type</th><th>Ref Type</th><th>Ref Idn</th><th>Amount</th>
     <th colspan="3">Payment Mode</th>
     <tr><td colspan="5"></td><td>Part</td><td>Full</td><td>Short</td>
     </tr>
     <%for(int i=0;i<recByLst.size();i++){
     HashMap recMap = (HashMap)recByLst.get(i);
     if(recMap!=null && recMap.size()>0){
     String IDN = util.nvl((String)recMap.get("IDN"));
     %>
     <tr><td><%=util.nvl((String)recMap.get("T_DTE"))%></td><td><%=util.nvl((String)recMap.get("TYP"))%></td>
     <td><%=util.nvl((String)recMap.get("REFTYP"))%></td><td><%=util.nvl((String)recMap.get("REFIDN"))%></td>
      <td><%=util.nvl((String)recMap.get("AMT"))%> &nbsp;&nbsp;<html:text property="value('PAMT_<%=IDN%>')" name="paymentTransForm" styleId="TXT_<%=IDN%>"  readonly="true" />
     </td>
     <td><input type="radio" name="paymd_<%=IDN%>" id="part" onclick="PaymentMode('false',<%=IDN%>)"  value="part"/> </td>
     <td><input type="radio" name="paymd_<%=IDN%>" id="full" onclick="PaymentMode('true',<%=IDN%>)" value="full"/></td>
     <td><input type="radio" name="paymd_<%=IDN%>" id="short" onclick="PaymentMode('false',<%=IDN%>)" value="short"/></td>
     </tr>
     <%}}%>
     </table>  
     <%}%>
     </td></tr>
     <%paidByLst = (ArrayList)dtlMap.get("PAID"); %>
    <table cellpadding="2" cellspacing="2">
     <tr><td> 
     <table><tr><td>Paid By</td>
     <%if(paidByLst!=null && paidByLst.size()>0){
     for(int i=0;i<paidByLst.size();i++){
     HashMap paidMap = (HashMap)paidByLst.get(i);
     String NME = (String)paidMap.get("NME");
      String IDN = (String)paidMap.get("IDN");
     %>
     <td><input type="radio" name="paid" value="<%=IDN%>" /> </td><td><%=NME%></td>
     <%}}%>
     </tr>
     </table>
     </td></tr>
     <tr><td>
     <%
        ArrayList payByLst = (ArrayList)dtlMap.get("PAY");
        if(payByLst!=null && payByLst.size()>0){
     %>
     <table class="grid1">
     <tr><th>Date</th><th>Type</th><th>Ref Type</th><th>Ref Idn</th><th>Amount</th>
     <th colspan="3">Payment Mode</th>
     <tr><td colspan="5"></td><td>Part</td><td>Full</td><td>Short</td>
     </tr> 
     <%for(int i=0;i<payByLst.size();i++){   
     HashMap payMap = (HashMap)payByLst.get(i);
        String IDN = util.nvl((String)payMap.get("IDN"));
     %>
     <tr><td><%=util.nvl((String)payMap.get("T_DTE"))%></td><td><%=util.nvl((String)payMap.get("TYP"))%></td>
     <td><%=util.nvl((String)payMap.get("REFTYP"))%></td><td><%=util.nvl((String)payMap.get("REFIDN"))%></td>
    <td><%=util.nvl((String)payMap.get("AMT"))%> &nbsp;&nbsp;<html:text property="value('PAMT_<%=IDN%>')" name="paymentTransForm" styleId="TXT_<%=IDN%>"  readonly="true" />
     </td>
     <td><input type="radio" name="paymd_<%=IDN%>" id="part" onclick="PaymentMode('false',<%=IDN%>)"  value="part"/> </td>
     <td><input type="radio" name="paymd_<%=IDN%>" id="full" onclick="PaymentMode('true',<%=IDN%>)" value="full"/></td>
     <td><input type="radio" name="paymd_<%=IDN%>" id="short" onclick="PaymentMode('false',<%=IDN%>)" value="short"/></td>
    </tr> 
     <%}%>
     </table>
     <%}%>
     </td></tr>
     
    </table>
  <%}%>
   </td></tr>
   <tr>
    <td><jsp:include page="/footer.jsp" /> </td>
    </tr>
    </table>
    </body>
</html>