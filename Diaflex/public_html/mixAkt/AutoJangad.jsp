<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page errorPage="../error_page.jsp" %>
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import="java.util.ArrayList,java.util.LinkedHashMap,java.util.Set,ft.com.dao.*,java.util.ArrayList,java.util.HashMap,java.sql.ResultSet,ft.com.*, java.util.Date, java.net.URLEncoder"%>
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
   <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/newCalScript.js?v=<%=info.getJsVersion()%>" ></script>
    <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/bse.js?v=<%=info.getJsVersion()%>" ></script>
    <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/ajax.js?v=<%=info.getJsVersion()%>" ></script>

    <title>Auto Jangad</title>
    
  </head>
  <%
    String logId=String.valueOf(info.getLogId());
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
  <img src="../images/line.jpg" border="0" /></td><td valign="middle"> <span class="pgHed">Auto Jangad</span> </td>
  </tr></table>
  </td>
  </tr>
  <html:form action="/mixAkt/autoJangad.do?method=Save" method="post" onsubmit="return confirmChanges()">
    <html:hidden property="value(NME_IDN)" name="autoJangadForm" />
      <html:hidden property="value(NMEREL_IDN)" name="autoJangadForm"/>
  <tr>
  <td valign="top" class="tdLayout" >
  
  <html:submit property="value(submit)" value="Save Changes" styleClass="submit"/>
  </td></tr>
  <tr>
  <td valign="top" class="tdLayout" height="10px"></td></tr>
  
  <%
  HashMap PKTDTLMAP = (HashMap)request.getAttribute("PKTDTLMAP");
  if(PKTDTLMAP!=null && PKTDTLMAP.size()>0){
  HashMap pktList = (HashMap)PKTDTLMAP.get("PKTDTL");
  %>
   <%if(pktList!=null && pktList.size()>0){
     String idn =util.nvl((String)pktList.get("STKIDN"));
   String cts =util.nvl((String)pktList.get("CTS"));
     String ctshd =util.nvl((String)pktList.get("CTSHD"));
     String qtyhd =util.nvl((String)pktList.get("QTYHD"));
   String ctsIss =util.nvl((String)pktList.get("CTS_ISS"));
   String qtyIss =util.nvl((String)pktList.get("QTY_ISS"));
    String qty =util.nvl((String)pktList.get("QTY"));
    String upr =util.nvl((String)pktList.get("UPR"));
   %>
   <tr>
  <td valign="top" class="tdLayout">
  <html:hidden property="value(qty)" value="<%=qty%>"  />
   <html:hidden property="value(pktidn)" value="<%=idn%>"  />
   <table class="grid1">
  <tr><th>Packet</th><th>Total Qty</th><th>On Hand Qty</th><th>Iss Qty</th><th>Total Cts</th><th>On Hand Cts</th><th>Iss Cts</th><th>Price</th></tr>
  <tr><td><%=util.nvl((String)pktList.get("BOXID"))%></td>
     <td><%=util.nvl((String)pktList.get("QTY"))%>
    <html:text property="value(pktqty)" value="<%=qty%>" styleId="pktqty"  onchange="qtyDiff('TTL')" styleClass="txtStyle"  style="width:100px"  name="autoJangadForm" />
  </td>
  <td><%=qtyhd%>
  <html:text property="value(pktqtyhd)" value="<%=qtyhd%>" styleId="pktqtyhd"  onchange="qtyDiff('HD')" styleClass="txtStyle"  style="width:100px"  name="autoJangadForm" />
  </td>
  <td><%=util.nvl((String)pktList.get("QTY_ISS"))%>
  <html:text property="value(pktqtyiss)" value="<%=qtyIss%>" styleId="pktqtyiss" onchange="qtyDiff('ISS')" styleClass="txtStyle"  style="width:100px"  name="autoJangadForm" />
  </td>
   <td><%=util.nvl((String)pktList.get("CTS"))%>
    <html:text property="value(pktcts)" value="<%=cts%>" styleId="pktcts"  onchange="caratDiff('TTL')" styleClass="txtStyle"  style="width:100px"  name="autoJangadForm" />
  </td>
  <td><%=ctshd%>
  <html:text property="value(pktctshd)" value="<%=ctshd%>" styleId="pktctshd"  onchange="caratDiff('HD')" styleClass="txtStyle"  style="width:100px"  name="autoJangadForm" />
  </td>
  <td><%=util.nvl((String)pktList.get("CTS_ISS"))%>
  <html:text property="value(pktctsiss)" value="<%=ctsIss%>" styleId="pktctsiss" onchange="caratDiff('ISS')" styleClass="txtStyle"  style="width:100px"  name="autoJangadForm" />
  </td>
  <td><%=upr%>
  <html:hidden property="value(quot)" value="<%=upr%>" name="autoJangadForm"  />
  </td></tr>
  </table>
   </td></tr>
   <%}%>
   <tr>
  <td valign="top" class="tdLayout" height="10px"></td></tr> 
    <%
     HashMap memoDTl = (HashMap)PKTDTLMAP.get("MEMODTL");
    if(memoDTl!=null && memoDTl.size()>0){
  
     String cts =util.nvl((String)memoDTl.get("CTS"));
     String ctsIss =util.nvl((String)memoDTl.get("CTSISS"));
   String ctsRtn =util.nvl((String)memoDTl.get("CTSRTN"));
        String qty =util.nvl((String)memoDTl.get("QTY"));
     String qtyIss =util.nvl((String)memoDTl.get("QTYISS"));
   String qtyRtn =util.nvl((String)memoDTl.get("QTYRTN"));
   String  quot = util.nvl((String)memoDTl.get("QUOT"));
   String mstkIdn =util.nvl((String)memoDTl.get("STKIDN"));
   String IDN = util.nvl((String)memoDTl.get("IDN"));
    %>
   <tr>
  <td valign="top" class="tdLayout">
   <html:hidden property="value(memoIdn)" value="<%=IDN%>"  />
      <html:hidden property="value(memomstkIdn)" value="<%=mstkIdn%>"  />
   <table class="grid1">
  <tr><th>Buyer Name</th><th>Memo Idn</th><th>Date</th>
  <th>Ttl Iss Qty</th><th>Cur Iss Qty</th><th>Rtn Qty</th>
  <th>Ttl Iss </th><th>Cur Iss Cts</th><th>Rtn Cts</th><th>Price</th></tr>
  <tr><td nowrap="nowrap"><%=util.nvl((String)memoDTl.get("NME"))%></td><td><%=util.nvl((String)memoDTl.get("IDN"))%></td>
  <td><%=util.nvl((String)memoDTl.get("DTE"))%></td>
    <td><%=util.nvl((String)memoDTl.get("QTY"))%>
   <html:text property="value(memoqty)" value="<%=qty%>"  styleId="memoqtyTtl"  styleClass="txtStyle" readonly="true" style="width:100px" name="autoJangadForm" />
  </td>
  <td><%=qtyIss%>
   <html:text property="value(memoqtyiss)" value="<%=qtyIss%>" styleId="memoqtyIss"  styleClass="txtStyle"  onchange="CalTotalIssqty()"  style="width:100px" name="autoJangadForm" />
  </td>
  <td><%=util.nvl((String)memoDTl.get("QTYRTN"))%>
  <html:text property="value(memoqtyrtn)" value="<%=qtyRtn%>" styleId="memoqtyRtn"  styleClass="txtStyle" onchange="CalTotalIssqty()"  style="width:100px"  name="autoJangadForm" />
  </td>
  <td><%=util.nvl((String)memoDTl.get("CTS"))%>
   <html:text property="value(memocts)" value="<%=cts%>"  styleId="memoctsTtl"  styleClass="txtStyle" readonly="true" style="width:100px" name="autoJangadForm" />
  </td>
  <td><%=ctsIss%>
   <html:text property="value(memoctsiss)" value="<%=ctsIss%>" styleId="memoctsIss"  styleClass="txtStyle"  onchange="CalTotalIsscarat()"  style="width:100px" name="autoJangadForm" />
  </td>
  <td><%=util.nvl((String)memoDTl.get("CTSRTN"))%>
  <html:text property="value(memoctsrtn)" value="<%=ctsRtn%>" styleId="memoctsRtn"  styleClass="txtStyle" onchange="CalTotalIsscarat()"  style="width:100px"  name="autoJangadForm" />
  </td>
  
  <td><%=util.nvl((String)memoDTl.get("QUOT"))%>
  <html:text property="value(memoctsprc)" value="<%=quot%>" styleClass="txtStyle"  style="width:100px" name="autoJangadForm" />
  </td>
  </tr>
  </table>
   </td></tr>
   <%}else{%>
     <tr>
  <td valign="top" class="tdLayout">
  R u want to Create Memo ? <b>Issue Carat &nbsp;: &nbsp;</b><input type="text"  name="memoIssCts" id="memoIssCts" size="8"  />  &nbsp;&nbsp;Issue Qty &nbsp;: &nbsp;</b><input type="text"  name="memoIssQty" id="memoIssQty" size="8"  /> <input type="radio" name="memoCr" id="YES" value="YES" checked="checked"/> YES &nbsp;&nbsp;
   <input type="radio" name="memoCr" id="NO" value="NO"/> NO 
   </td></tr>
   
   <%}%>
      
    <tr>
  <td valign="top" class="tdLayout" height="10px"></td></tr>
     <%
     ArrayList OTHMEMODTL = (ArrayList)PKTDTLMAP.get("OTHMEMODTL");
    if(OTHMEMODTL!=null && OTHMEMODTL.size()>0){%>
   <tr>
  <td valign="top" class="tdLayout">
    <table class="grid1">
  <tr><th>Buyer Name</th><th>Memo Idn</th><th>Date</th><th>Ttl Qty</th><th>Cur Iss Qty</th><th>Rtn Qty</th><th>Ttl Cts</th><th>Cur Iss Cts</th><th>Rtn Cts</th><th>Price</th></tr>
  <%for(int i=0;i<OTHMEMODTL.size();i++){
  HashMap memoDtl = (HashMap)OTHMEMODTL.get(i);
  %>
  <tr><td nowrap="nowrap"><%=util.nvl((String)memoDtl.get("NME"))%></td><td><%=util.nvl((String)memoDtl.get("IDN"))%></td>
  <td><%=util.nvl((String)memoDtl.get("DTE"))%></td>
  <td><%=util.nvl((String)memoDtl.get("QTY"))%></td><td><%=util.nvl((String)memoDtl.get("QTYISS"))%></td>
  <td><%=util.nvl((String)memoDtl.get("QTYRTN"))%></td>
  <td><%=util.nvl((String)memoDtl.get("CTS"))%></td><td><%=util.nvl((String)memoDtl.get("CTSISS"))%></td>
  <td><%=util.nvl((String)memoDtl.get("CTSRTN"))%></td><td><%=util.nvl((String)memoDtl.get("QUOT"))%></td>
  </tr>
  <%}%>
  <tr><td colspan="3"></td>
    <td><b><%=util.nvl((String)PKTDTLMAP.get("ISSQTY"))%></b> </td><td><b><%=util.nvl((String)PKTDTLMAP.get("BRCQTY"))%></b> </td>
  <td><B><%=util.nvl((String)PKTDTLMAP.get("RTNQTY"))%></b> </td>
  <td><b><%=util.nvl((String)PKTDTLMAP.get("ISSCTS"))%></b> </td><td><b><%=util.nvl((String)PKTDTLMAP.get("BRCCTS"))%></b> </td>
  <td><B><%=util.nvl((String)PKTDTLMAP.get("RTNCTS"))%></b> </td>
  <td></td> 
  </tr>
   </table>
   </td></tr>
   <%}%>
   
   
  <%}%>
  </html:form>
  
  
 
  
  
    
    
  <tr>
     <td><jsp:include page="/footer.jsp" /> </td>
  </tr>
  </table>
 </body>
</html>