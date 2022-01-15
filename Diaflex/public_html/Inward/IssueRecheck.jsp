<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page errorPage="../error_page.jsp" %>
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import="java.util.ArrayList,java.util.HashMap,java.sql.ResultSet,ft.com.*, java.util.Date, java.util.ArrayList, ft.com.dao.*"%>
<jsp:useBean id="info" class="ft.com.InfoMgr" scope="session" />
<jsp:useBean id="util" class="ft.com.JspUtil" scope="page" />

<%@taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>Issue Cancellation </title>
    <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/bse.css?v=<%=info.getJsVersion()%>"/>
    <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/style.css?v=<%=info.getJsVersion()%>"/>
       <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/bse.js?v=<%=info.getJsVersion()%> " > </script>
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
    <td height="5" valign="top" class="greyline"><img src="<%=info.getReqUrl()%>/images/grey_linebig.png" width="685" height="5" align="right" /></td>
  </tr>
<tr>
  <td valign="top" class="hedPg">
  <table cellpadding="1" cellspacing="5"><tr><td valign="middle">
  <img src="../images/line.jpg" border="0" /></td><td valign="middle"> <span class="pgHed">Issue Cancellation</span> </td>
</tr></table>
  </td>
  </tr>
  <%
  HashMap dbInfoSys = info.getDmbsInfoLst();
  String cnt = (String)dbInfoSys.get("CNT");
   String msg = (String)request.getAttribute("msg");
   if(msg!=null){%>
     <tr><td valign="top" class="tdLayout"><%=msg%></td></tr>
   <%}%>
  <tr><td valign="top" class="tdLayout">
   <html:form action="Inward/IssueRecheck.do?method=view" method="post" onsubmit="return Issue_cancel()" >
 
  <table>
   <tr>
   <td><span class="redLabel">*</span> Issue Id:</td><td> <html:text property="value(issueId)" styleId="issueId" name="issueRckForm"  /></td>
   </tr>
   <tr>
   <td>Barcode:</td><td> <html:textarea property="value(vnmLst)" rows="7"  cols="30" name="issueRckForm"  /></td>
   </tr>
   <tr>
   <td colspan="2" align="center">
   <html:submit property="value(srch)" value="Fetch" styleClass="submit" />
   </td>
   </tr>
   </table></html:form>
  </td></tr>
    <%
   String view = (String)request.getAttribute("view");
   if(view!=null){
   ArrayList stockList = (ArrayList)session.getAttribute("StockList");
   if(stockList!=null && stockList.size()>0){
   ArrayList prpDspBlocked = info.getPageblockList();
    ArrayList vwPrpLst = (ArrayList)session.getAttribute("asViewLst");
      HashMap totals = (HashMap)request.getAttribute("totalMap");
      


    HashMap mprp = info.getMprp();
    int sr = 0;
   %>
  
 <tr><td  valign="top" class="tdLayout">
   <table>
   <tr><td>Total :&nbsp;&nbsp;</td><td><span id="ttlqty"><%=totals.get("qty")%></span> &nbsp;&nbsp;</td><td>cts&nbsp;&nbsp;</td><td><span id="ttlcts"> <%=totals.get("cts")%></span> &nbsp;&nbsp;</td><td>Selected:&nbsp;&nbsp;</td><td> Total :&nbsp;&nbsp;</td> <td><span id="qtyTtl">0&nbsp;&nbsp;</span></td><td>Cts&nbsp;&nbsp;</td><td><span id="ctsTtl">0</span> </td> </tr>
  
   </table>
   </td></tr>
    <html:form action="Inward/IssueRecheck.do?method=cancel" method="post">
 <html:hidden property="value(issueId)" name="issueRckForm" />
  <tr><td  valign="top" class="tdLayout"><table><tr><td>
  
    <html:submit property="value(cancal)" styleClass="submit" value="Cancal Selected" onclick="return validate_issue('CHK_' , 'count')" />
  </td></tr></table>
  
  </td></tr>
  
   <tr><td  valign="top" class="tdLayout">
  
   <table class="grid1">
   <tr> <th><input type="checkbox" name="checkAll" id="checkAll" onclick="checkALL('CHK_','count')" /></th>
   <th>Sr</th>
       <th>Issue Id</th>
        <th>Packet No.</th>
        <%for(int j=0; j < vwPrpLst.size(); j++ ){
    String prp = (String)vwPrpLst.get(j);
    if(prpDspBlocked.contains(prp)){
       }else{
     String hdr = util.nvl((String)mprp.get(prp));
    String prpDsc = util.nvl((String)mprp.get(prp+"D"));
    if(hdr == null) {
        hdr = prp;
       }  

%>
<th title="<%=prpDsc%>"><%=hdr%></th>

<%}}%> 


</tr>
 <%
 int colSpan = vwPrpLst.size()+4;
 for(int i=0; i < stockList.size(); i++ ){
 HashMap stockPkt = (HashMap)stockList.get(i);
 String stkIdn = (String)stockPkt.get("stk_idn");
 String cts = (String)stockPkt.get("cts");
 String issIdn = (String)stockPkt.get("issIdn");
 String onclick = "AssortTotalCal(this,"+cts+",'','')";
 String target = "SC_"+stkIdn;
 sr = i+1;
 String checkFldId = "CHK_"+sr;
 String checkFldVal = "value("+stkIdn+")";
 %>
<tr>
<td><html:checkbox property="<%=checkFldVal%>" styleId="<%=checkFldId%>" name="issueRckForm" onclick="<%=onclick%>"  value="yes" /> </td>
<td><%=sr%><input type="hidden" id="CTS_<%=stkIdn%>"  value="<%=cts%>" /></td>
<td><%=stockPkt.get("issIdn")%></td>
<td><%=stockPkt.get("vnm")%></td>
<%for(int j=0; j < vwPrpLst.size(); j++ ){
    String prp = (String)vwPrpLst.get(j);
    if(prpDspBlocked.contains(prp)){
       }else{
    %>
    <td><%=stockPkt.get(prp)%></td>
<%}}%>

 
</tr>

   <%}%>
   </table>
  
   </td></tr>
   <input type="hidden" name="count" id="count" value="<%=sr%>" />
  </html:form>
   <%}else{%>
   <tr><td valign="top" class="hedPg">Sorry no result found </td></tr>
   <%}}%>
  <tr>
    <td><jsp:include page="/footer.jsp" /> </td>
    </tr>
  </table>
  </body>
</html>