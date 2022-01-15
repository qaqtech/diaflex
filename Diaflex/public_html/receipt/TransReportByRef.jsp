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
    <title>Trans Report By Ref</title>
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
  <img src="../images/line.jpg" border="0" /></td><td valign="middle"> <span class="pgHed">Individual Transaction</span> </td>
</tr></table>
  </td>
  </tr>
   <tr>
  <td valign="top" class="tdLayout">
   <html:form action="receipt/receiptTransReport.do?method=RefWise" onsubmit="return validateTransRefRpt()" method="post">
   <table>
   <tr> 
   <td>Ref Type </td> <td>:</td>  
   <td><html:select property="value(refTyp)" styleId="refTyp" name="receiptTransReportForm"     >
            <html:optionsCollection property="invTypLst" name="receiptTransReportForm" 
            label="dsc" value="nme" />
    </html:select> </td></tr>
   <tr> 
   <td>Ref Idn </td> <td>:</td>  
   <td><html:text property="value(refIdn)" styleClass="txtStyle"  name="receiptTransReportForm"  styleId="refIdn"   /> </td></tr>
   <tr><td colspan="3"><html:submit property="value(submit)" value="Fetch" styleClass="submit" /></td> </tr>
   </table>
   </html:form>
  </td></tr>
   
   <%
   String view = (String)request.getAttribute("view");
   if(view!=null){
   ArrayList maintransDtlLst = (ArrayList)request.getAttribute("MAINTRANSLIST");
   if(maintransDtlLst!=null && maintransDtlLst.size()>0){ %>
    <tr> 
   <td valign="top" class="tdLayout"> 
    <table cellpadding="2" cellspacing="2">
    <tr><td><b>Main Transaction</b></td></tr>
    <tr><td>
    <table class="grid1">
   <tr>
   <th>Party Name</th><th>Trans Dte</th><th>Type</th><th>Sub Type</th><th>Ref Typ</th><th>Ref Idn</th><th>Due Dte</th><th>Credit</th><th>Debit</th><th>Xrt</th></tr>
   <% for(int i=0;i<maintransDtlLst.size();i++){
   HashMap transMap = (HashMap)maintransDtlLst.get(i); 
   String ent_seq = util.nvl((String)transMap.get("ENT_SEQ"));
    String xrt = util.nvl((String)transMap.get("XRT"));
    String typ = util.nvl((String)transMap.get("TYP"));
    String idn =  util.nvl((String)transMap.get("IDN"));
    %>  
 <tr>
 <td><%=transMap.get("NME")%></td><td><%=transMap.get("T_DTE")%></td><td><%=transMap.get("TYP")%></td><td><%=transMap.get("SUBTYP")%></td>
  <td><%=transMap.get("REF_TYP")%></td><td><%=transMap.get("REF_IDN")%></td><td><%=transMap.get("DUE_DTE")%></td>
  <td align="right"><%=util.nvl((String)transMap.get("CRAMT"))%></td><td align="right"><%=util.nvl((String)transMap.get("DBAMT"))%></td>
  <td><%=xrt%> </td>
  </tr>
  <% }%>

  </table>
  </td></tr></table></td></tr>
  <%}else{%>
   <tr> 
   <td valign="top" class="tdLayout">  Sorry no result found </td></tr>
  <%}
  
   ArrayList transDtlLst = (ArrayList)request.getAttribute("TRANSLIST");
   if(transDtlLst!=null && transDtlLst.size()>0){ %>
    <tr> 
   <td valign="top" class="tdLayout">
  <b> Sub Transaction</b>
   </td></tr>
     <tr> 
   <td valign="top" class="tdLayout"> 
    <table class="grid1">
   <tr><th>Party Name</th><th>Trans Dte</th><th>Type</th><th>Sub Type</th><th>Ref Typ</th><th>Ref Idn</th><th>Due Dte</th><th>Credit</th><th>Debit</th><th>Xrt</th></tr>
   <% for(int i=0;i<transDtlLst.size();i++){
   HashMap transMap = (HashMap)transDtlLst.get(i); 
   String ent_seq = util.nvl((String)transMap.get("ENT_SEQ"));
    String xrt = util.nvl((String)transMap.get("XRT"));
    String typ = util.nvl((String)transMap.get("TYP"));
    String idn =  util.nvl((String)transMap.get("IDN"));
    %>  
 <tr>
  <td><%=transMap.get("NME")%></td><td><%=transMap.get("T_DTE")%></td><td><%=transMap.get("TYP")%></td><td><%=transMap.get("SUBTYP")%></td>
  <td><%=transMap.get("REF_TYP")%></td><td><%=transMap.get("REF_IDN")%></td><td><%=transMap.get("DUE_DTE")%></td>
  <td align="right"><%=util.nvl((String)transMap.get("CRAMT"))%></td><td align="right"><%=util.nvl((String)transMap.get("DBAMT"))%></td><td><%=xrt%></td>
  </tr>
 <%}%>
    <tr><td colspan="7" align="right"><b>Total</b></td><td align="right"><B><%=util.priceFormatter((String)request.getAttribute("TTLCR"))%></b></td> <td align="right"><b><%=util.priceFormatter((String)request.getAttribute("TTLDB"))%></b></td><td></td></tr>

  </table>
  
  </td></tr>
 <%}else{%>
   <tr> 
   <td valign="top" class="tdLayout"> Sorry no result found </td></tr>
  <%}}%>
   
     <tr>
   <td><jsp:include page="/footer.jsp" /> </td>
   </tr>
    </table>
    </body>
</html>