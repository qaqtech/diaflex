<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page errorPage="../error_page.jsp" %>
<%@ page contentType="text/html;charset=windows-1252"%>
<jsp:useBean id="info" class="ft.com.InfoMgr" scope="session" />
<jsp:useBean id="util" class="ft.com.JspUtil" scope="page" />
<%@ page import="java.util.ArrayList,java.util.HashMap,java.sql.ResultSet,ft.com.*, java.util.Date, java.util.ArrayList, ft.com.dao.*"%>
<%@taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>Transfer To Mkt Mix</title>
    <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/bse.css?v=<%=info.getJsVersion()%>"/>
    <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/general.css?v=<%=info.getJsVersion()%>"/>
     <script type="text/javascript" src="<%=info.getReqUrl()%>/PopScripts/jquery-1.2.6.min.js" > </script>
     
     <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/style.css?v=<%=info.getJsVersion()%>"/>
      <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/bse.js?v=<%=info.getJsVersion()%> " > </script>
          <script type="text/javascript" src="<%=info.getReqUrl()%>/PopScripts/popup.js?v=<%=info.getJsVersion()%> " > </script>
  </head>
        <%String logId=String.valueOf(info.getLogId());
        String onfocus="cook('"+logId+"')";
        %>
 <body onfocus="<%=onfocus%>" onload="MM_preloadImages('images/faunalogo.png')" topmargin="0" leftmargin="0" rightmargin="0" bottommargin="0">
<div id="backgroundPopup"></div>

<div id="popupContactSave" align="center" >
<img src="../images/loading_flame.gif" height="150px" width="150px" border="0" />
</div>
<%
 HashMap prp = info.getPrp();
 
%>
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
  <img src="../images/line.jpg" border="0" /></td><td valign="middle"> <span class="pgHed">Transfer To Marketing </span> </td>
  </tr></table>
  </td>
  </tr>
   <tr>
  <td valign="top" class="tdLayout">
  
     <html:form action="mix/mixMktTransferAction.do?method=fetch"  method="post">
   <table  class="grid1">
  <tr><th colspan="2">Generic Search </th></tr>
  <tr>
   <td colspan="2">
    <jsp:include page="/genericSrch.jsp"/>
   </td></tr>
   <tr><td  align="right">
   <input type="submit" name="fetch" value="Fetch" class="submit" />
  </td>
   <td align="left">
   <input type="submit" name="fetchall" value="Fetch All" class="submit" />
   ></td> </tr>
   </table>
   </html:form>
 
  </td></tr>
   <% ArrayList msgLst = (ArrayList)request.getAttribute("msgList");
   if(msgLst!=null){
   for(int i=0;i<msgLst.size();i++){
   String msg = (String)msgLst.get(i);
   %>
    <tr><td valign="top" class="tdLayout"><span class="redLabel"><%=msg%></span></td></tr>
   <%}}%>
  <tr>
  <td valign="top" height="20px" class="tdLayout">
  </td></tr>
  <tr>
  <td valign="top" class="tdLayout">
  <%String view =(String)request.getAttribute("view");
  if(view!=null){
  ArrayList stockList = (ArrayList)request.getAttribute("StockList");
  ArrayList vwPrpLst = (ArrayList)session.getAttribute("TRF_MKT_MIX");
  HashMap mprp = info.getMprp();
  if(stockList!=null && stockList.size()>0){
  %>
       <html:form action="mix/mixMktTransferAction.do?method=save" onsubmit="return validate_selection('checkbox','1','cb_stk_')"  method="post">

  <table><tr><td>
  <html:submit property="value(trans)"  styleClass="submit" onclick="return confirmChanges()" value="Transfer" />&nbsp;&nbsp;
  <html:submit property="value(transMrg)"  styleClass="submit" onclick="return confirmChanges()" value="Transfer with Merge" />
  </td></tr>
  <tr><td>
   <table class="grid1" id="dataTable">
   <tr><th><input type="checkbox" name="checkAll" id="checkAll" onclick="ALLCheckBoxTyp('checkAll','cb_stk_','1')" />  </th>
     <th>Packet No.</th>
     <th>Qty</th>
     <th>Cts</th>
    <%for(int j=0; j < vwPrpLst.size(); j++ ){
    String lprp = (String)vwPrpLst.get(j);
     String hdr = util.nvl((String)mprp.get(lprp));
    String prpDsc = util.nvl((String)mprp.get(lprp+"D"));
    if(hdr == null) {
        hdr = lprp;
       } %>
    <th title="<%=prpDsc%>"><%=hdr%></th>
  <%}%></tr>
  <%for(int i=0;i<stockList.size();i++){
  HashMap pktDtl = (HashMap)stockList.get(i);
  String stk_idn = (String)pktDtl.get("stk_idn");
  String vnm = (String)pktDtl.get("vnm");
  String qty = (String)pktDtl.get("qty");
  String cts = (String)pktDtl.get("cts");
  %>
  <tr><td><input type="checkbox" name="cb_stk_<%=stk_idn%>" id="cb_stk_<%=stk_idn%>" value="<%=stk_idn%>" /> </td>
<td><%=vnm%></td>
<td><%=qty%></td>
<td><%=cts%></td>
<%for(int j=0; j < vwPrpLst.size(); j++ ){
    String lprp = (String)vwPrpLst.get(j);
    %>
    <td><%=pktDtl.get(lprp)%></td>
    <%}%>
  </tr>
 <%}%>
  </table></td></tr></table></html:form>
  <%}else{%>
  Sorry no found
  <%}}%>
  </td></tr>
  <tr>
    <td><jsp:include page="/footer.jsp" /> </td>
    </tr>
  </table>
  </body>
</html>