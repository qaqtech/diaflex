<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
 
<%@ page errorPage="../error_page.jsp" %>
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import="java.util.ArrayList,java.util.HashMap,java.sql.ResultSet,ft.com.*, java.util.Date, java.util.ArrayList, ft.com.dao.*"%>
<jsp:useBean id="util" class="ft.com.JspUtil" scope="page" />
<jsp:useBean id="info" class="ft.com.InfoMgr" scope="session" />
<%@taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>
<%@taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>Order Delivery</title>
    <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/bse.css?v=<%=info.getJsVersion()%>"/>
    <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/style.css?v=<%=info.getJsVersion()%>"/>
   <script language="javaScript" type="text/javascript" src="<%=info.getReqUrl()%>/calendar/calendar.js"></script>
   <link href="<%=info.getReqUrl()%>/calendar/calendar.css" rel="stylesheet" type="text/css" />
        <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/bse.js?v=<%=info.getJsVersion()%> " > </script>
        <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/ajax.js?v=<%=info.getJsVersion()%> " > </script>

  </head>
<%
HashMap dbinfo = info.getDmbsInfoLst();
String cnt = (String)dbinfo.get("CNT");
  String repPath = (String)dbinfo.get("REP_PATH");

        String logId=String.valueOf(info.getLogId());
        String onfocus="cook('"+logId+"')";
        %>
 <body onfocus="<%=onfocus%>"  >

<input type="hidden" name="CNT" id="CNT" value="<%=cnt%>" />
<input type="hidden" name="REQURL" id="REQURL" value="<%=info.getReqUrl()%>" />
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
  <img src="../images/line.jpg" border="0" /></td><td valign="middle"> <span class="pgHed">Order Delivery</span> </td>
  </tr></table></td></tr>
   <%
   String msg = (String)request.getAttribute("msg");
     if(msg!=null){%>
    <tr><td valign="top" class="tdLayout"><span class="redLabel"><%=msg%></span></td></tr>
   <%}%>
   <tr><td valign="top" class="tdLayout">
  <html:form action="order/orderDeliveryAction.do?method=fetch" method="post"  >

  <table>
  <tr>
  <td>Delivery Date: </td><td>
  <table>
  <tr>  <td nowrap="nowrap"> From : </td><td nowrap="nowrap">
    <html:text property="value(dtefr)"  name="orderDeliveryForm"  styleId="dtefr"  size="12"  /> <a href="#" style="margin-top:5px;"  onClick="setYears(1900, '<%=info.getCurrentYear()%>' );showCalender(this, 'dtefr');">
                <img src="<%=info.getReqUrl()%>/images/calender.png"></a></td>
                <td nowrap="nowrap">To : </td><td nowrap="nowrap">
    <html:text property="value(dteto)"  name="orderDeliveryForm"  styleId="dteto"  size="12"  /> <a href="#"  onClick="setYears(1900, '<%=info.getCurrentYear()%>' );showCalender(this, 'dteto');">
                <img src="<%=info.getReqUrl()%>/images/calender.png"></a></td> </tr>
  </table>
  </td>
  </tr>
  <tr>
  <td>Delivery Id:</td><td> <html:text property="value(deliveryIdn)"  name="orderDeliveryForm"    size="10"  /></td>
  </tr>
  
   <tr>
  <td>Packets :</td><td> <html:textarea property="value(vnmLst)"  name="orderDeliveryForm" cols="20"  rows="5"/></td>
  </tr>
  <tr><td colspan="2"> <html:submit property="value(submit)" value="Fetch" styleClass="submit"/></td><tr>
  </table>
</html:form>
   </td></tr>
    <html:form action="order/orderDeliveryAction.do?method=save" method="post" onsubmit="return  confirmChangesWithPopup()" >
        
    <%
     ArrayList prps = (info.getValue("MDL_MEMO_RTRN") == null)?new ArrayList():(ArrayList)info.getValue("MDL_MEMO_RTRN"); 
   ArrayList orderList = (ArrayList)request.getAttribute("orderList");   
   ArrayList pktList = (ArrayList)request.getAttribute("pktList");
    if(pktList!=null && pktList.size()>0){
    %>
     <tr><td valign="top" class="tdLayout" height="20px"></td></tr>
    <tr><td valign="top" class="tdLayout">
        <html:submit property="value(submit)" value="Save Changes" styleClass="submit"/>
        </td></tr>
         <tr><td valign="top" class="tdLayout" height="10px"></td></tr>
     <tr><td valign="top" class="tdLayout">
    <table class="grid1">
    <tr><th>Sr No</th><th><b>Select ALL</b> <input type="checkbox"  value="IS" id="IS" onclick="CheckBOXALLForm('1',this,'cb_inv_')" />&nbsp;</th> <th>Order</th> <th>Packet Code</th>
   <th>Qty</th>
    <%
    for(int j=0; j < prps.size(); j++) {
        String lprp=(String)prps.get(j);
    %>
    <th><%=lprp%></th>
    <%}%>
    </tr>
    <%
    for(int i=0;i < pktList.size();i++){
     PktDtl pkt = (PktDtl)pktList.get(i);
     long stkIdn = pkt.getPktIdn();
     int sr=i+1;
      String orderId = "ORDER_"+stkIdn;
     String orderFld = "value("+orderId+")";
     String orderitemId = "ORDERITEM_"+stkIdn;
     String orderittemFld = "value("+orderitemId+")";
     String onChange="setOrderItems(this,'"+orderitemId+"')";
     String qtyFld = "value(QTY_"+stkIdn+")";
     String ctsFld = "value(CTS_"+stkIdn+")";
     String rteFld = "value(RTE_"+stkIdn+")";
     String quotFld = "value(QUOT_"+stkIdn+")";
     String fnlFld = "value(FNLSAL_"+stkIdn+")";
     String qty = pkt.getQty();
     String cts = pkt.getCts();
     String  rte = pkt.getRte();
     String quot = pkt.getByrRte();
     String fnl_sal = pkt.getFnlRte();
    %>
    <tr> <td><%=sr%></td>
   <td><input type="checkbox" name="cb_inv_<%=stkIdn%>" value="<%=stkIdn%>" id="cb_inv_<%=stkIdn%>"  /></td>
    <td>
     <html:select property="<%=orderFld%>" styleId="<%=orderId%>" onchange="<%=onChange%>" name="orderDeliveryForm">
     <html:option value="0" >--select--</html:option>
    <% for(int j=0;j<orderList.size();j++){ 
    String orderIdn = (String)orderList.get(j);
    %>
    <html:option value="<%=orderIdn%>" ><%=orderIdn%></html:option>
    <%}%>
   </html:select>
   &nbsp; &nbsp;
   <html:select property="<%=orderittemFld%>" styleId="<%=orderitemId%>" name="orderDeliveryForm">
   
     
     </html:select>
   <html:hidden property="<%=qtyFld%>" name="orderDeliveryForm" value="<%=qty%>"  />
   <html:hidden property="<%=ctsFld%>" name="orderDeliveryForm" value="<%=cts%>"  />
   <html:hidden property="<%=rteFld%>" name="orderDeliveryForm" value="<%=rte%>"  />
   <html:hidden property="<%=quotFld%>" name="orderDeliveryForm" value="<%=quot%>"  />
    <html:hidden property="<%=fnlFld%>" name="orderDeliveryForm" value="<%=fnl_sal%>"  />
    </td>
    <td><%=pkt.getVnm()%></td>
     <td><%=qty%></td>
      <%
    for(int j=0; j < prps.size(); j++) {
        String lprp=(String)prps.get(j);
    %>
    <td><%=pkt.getValue(lprp)%></td>
    <%}%>
    
    </tr>
   <%}%>
    
    </table>
       </td></tr>
    <%}%>
 </html:form>

    <tr>
    <td><jsp:include page="/footer.jsp" /> </td>
    </tr>
</table>
  <%@include file="/calendar.jsp"%>
  </body>
</html>