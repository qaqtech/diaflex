<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page errorPage="../error_page.jsp" %>
<%@ page contentType="text/html;charset=windows-1252"%>
<jsp:useBean id="info" class="ft.com.InfoMgr" scope="session" />
<jsp:useBean id="util" class="ft.com.DBUtil" scope="session" />
<%@ page import="java.util.ArrayList,java.util.HashMap,java.sql.ResultSet,ft.com.*, java.util.Date, java.util.ArrayList, ft.com.dao.*"%>
<%@taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>Mixing Return</title>
    <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/bse.css?v=<%=info.getJsVersion()%>"/>
    <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/general.css?v=<%=info.getJsVersion()%>"/>
     <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/style.css?v=<%=info.getJsVersion()%>"/>
      <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/bse.js?v=<%=info.getJsVersion()%> " > </script>
  </head>
        <%String logId=String.valueOf(info.getLogId());
        String onfocus="cook('"+logId+"')";
        %>
 <body onfocus="<%=onfocus%>" onload="MM_preloadImages('images/faunalogo.png')" topmargin="0" leftmargin="0" rightmargin="0" bottommargin="0">
  <%
String method = util.nvl(request.getParameter("method"));
ArrayList stockList = (ArrayList)session.getAttribute("StockList");
boolean disabled= false;
if(method.equals("fecth")){
if(stockList!=null && stockList.size()>0)
disabled= true;
}

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
  <img src="../images/line.jpg" border="0" /></td><td valign="middle"> <span class="pgHed">Mixing Return</span> </td>
</tr></table>
  </td>
  </tr>
  <%
   String msg = (String)request.getAttribute("msg");
   if(msg!=null){%>
    <tr><td valign="top" class="tdLayout"><span class="redLabel"><%=msg%></span></td></tr>
   <%}%>
    <%
   String vnmNtFnd = (String)request.getAttribute("vnmNotFnd");
     if(vnmNtFnd!=null){%>
    <tr><td valign="top" class="tdLayout"><span class="redLabel"><%=vnmNtFnd%></span></td></tr>
   <%}%>
   <tr>
     <td valign="top" class="tdLayout">
   
   <html:form action="mix/mixAction.do?method=fetch" method="post" >
  
   <table>
   <tr><td>Process </td>
   <td>
   <html:select property="value(mprcIdn)"  styleId="mprcIdn" name="mixForm"    >
           
            <html:optionsCollection property="prcList" name="mixForm" 
            label="prc" value="mprcId" />
    </html:select>
   </td></tr>
   <tr>
    <td>
    Employee : </td>
   <td>
   <html:select property="value(empIdn)"  styleId="empIdn" name="mixForm"   >
           <html:option value="0" >--select--</html:option>
            <html:optionsCollection property="empList" name="mixForm" 
            label="emp_nme" value="emp_idn" />
            </html:select>
   </td>
   </tr>
   <tr>
   <td>Issue Id</td><td><html:text property="value(issueIdn)" name="mixForm" /> </td>
   </tr>
   <tr>
   <td>Packets No:</td><td> <html:textarea property="value(vnmLst)" rows="7"  cols="30" name="mixForm"  /></td>
   </tr>
 
    <tr><td colspan="2" align="center"><html:submit property="value(srch)" value="Fetch" styleClass="submit" /></td> </tr>
   </table>
   </html:form>
   </td></tr>
 
   </td></tr>
   <tr> <td valign="top" class="tdLayout">
   <table>
     
      <%
   String view = (String)request.getAttribute("view");
   if(view!=null){
   
   if(stockList!=null && stockList.size()>0){
    ArrayList vwPrpLst = (ArrayList)session.getAttribute("MixViewLst");
    HashMap totals = (HashMap)request.getAttribute("totalMap");
    HashMap mprp = info.getMprp();
    int sr = 0;
   %>
   <tr> <td >
   <table>
   <tr><td>Total :&nbsp;&nbsp;</td><td><span id="ttlqty"> <%=totals.get("qty")%>&nbsp;&nbsp;</span></td><td>cts&nbsp;&nbsp;</td><td><span id="ttlcts"><%=totals.get("cts")%>&nbsp;&nbsp;</span></td>
 
   </tr>
  
   </table>
   </td></tr>
   <html:form action="mix/mixAction.do?method=Issue" method="post" onsubmit="return validate_issue('CHK_' , 'count')">
   <html:hidden property="value(prcId)" name="mixForm" />
   <html:hidden property="value(empId)" name="mixForm" />
   <tr><td><html:submit property="value(issue)" styleClass="submit" value="Issue" /> </td></tr>
   <tr><td>
   <table class="grid1">
   <tr> <th><input type="checkbox" name="checkAll" id="checkAll" onclick="checkALL('CHK_','count')" /> </th><th>Sr</th>
        <th>Packet No.</th>
       <th>Qty</th>
       <th>Cts</th>
       <th>Rtn Qty</th>
       <th>Rtn Cts</th>
        <%for(int j=0; j < vwPrpLst.size(); j++ ){
    String prp = (String)vwPrpLst.get(j);
     String hdr = util.nvl((String)mprp.get(prp));
    String prpDsc = util.nvl((String)mprp.get(prp+"D"));
    if(hdr == null) {
        hdr = prp;
       }  

%>
<th title="<%=prpDsc%>"><%=hdr%></th>

<%}%>       
</tr>
 <%
 
 for(int i=0; i < stockList.size(); i++ ){
 HashMap stockPkt = (HashMap)stockList.get(i);
 String stkIdn = (String)stockPkt.get("stk_idn");
 String cts = (String)stockPkt.get("CRTWT");
 String onclick = "AssortTotalCal(this,"+cts+",'','')";
 sr = i+1;
 String checkFldId = "CHK_"+sr;
 String checkFldVal = "value("+stkIdn+")";
 String rtnQty = "value(RTNQTY_"+stkIdn+")";
 String rtnCts = "value(RTNCTS_"+stkIdn+")";
 %>
<tr>
<td><html:checkbox property="<%=checkFldVal%>" styleId="<%=checkFldId%>" name="mixForm"  value="yes" /> </td>
<td><%=sr%></td>
<td><%=stockPkt.get("vnm")%><input type="hidden" id="CTS_<%=stkIdn%>"  value="<%=cts%>" /></td>
<td><%=util.nvl((String)stockPkt.get("qty"))%></td>
<td><%=util.nvl((String)stockPkt.get("cts"))%></td>
<td><html:text property="<%=rtnQty%>" size="10" name="mixForm" /> </td>
<td><html:text property="<%=rtnCts%>" size="10" name="mixForm" /></td>
<%for(int j=0; j < vwPrpLst.size(); j++ ){
    String prp = (String)vwPrpLst.get(j);
    %>
    <td><%=stockPkt.get(prp)%></td>
<%}%>
</tr>
<%}%>
  <%
  HashMap prp = info.getPrp();
  for(int n=1;n<=10;n++){ 
  String pktVnm = "value(vnm_"+n+")";
  String pktQty = "value(qty_"+n+")";
  String pktCts = "value(cts_"+n+")";
  String pktChkVal = "value(chk_"+n+")";
  String trId = "TR_"+n;
  %>
  <tr id="<%=trId%>" style="display:none" ><td><html:checkbox property="<%=pktChkVal%>"  name="mixForm"  value="yes" /></td><td></td>
  <td><html:text property="<%=pktVnm%>" size="10" name="mixForm" /> </td>
  <td><html:text property="<%=pktQty%>"  size="5" name="mixForm" /> </td>
  <td><html:text property="<%=pktCts%>"  size="5" name="mixForm" /> </td>
  <td></td><td></td>
  <%for(int j=0; j < vwPrpLst.size(); j++ ){
    String lprp = (String)vwPrpLst.get(j);
    String pktPrp = "value("+lprp+"_"+n+")";
    String typ = util.nvl((String)mprp.get(lprp+"T"));
    %>
    <td>
    <%
    if(typ.equals("C")){
     ArrayList prpVal = (ArrayList)prp.get(lprp+"V");
     if(prpVal!=null){
    %>
     <html:select property="<%=pktPrp%>"  name="mixForm" >
  <html:option value="0">--select--</html:option>
  <%for(int m=0;m<prpVal.size();m++){
  String prpPrt = (String)prpVal.get(m);
  %>
   <html:option value="<%=prpPrt%>"><%=prpPrt%></html:option>
  <%}%>
  </html:select>
    <%}}else{%>
     <html:text property="<%=pktPrp%>" size="5" name="mixForm" />
    <%}%>
    </td>
    <%}%>
  </tr>
   <%}%>
<tr><td colspan="<%=vwPrpLst.size()+7%>"><input type="button" class="submit" value="Add More Packets" onclick="addRow('10', this)"/> <input type="hidden" id="trcount" value="1" /> </td> </tr>

   </table></td></tr>
   <input type="hidden" name="count" id="count" value="<%=sr%>" />
   </html:form>
   <%}
   else{%>
   <tr><td>Sorry no result found </td></tr>
   <%}
   }%>
   </table></td></tr>
  
 <tr>
    <td><jsp:include page="/footer.jsp" /> </td>
    </tr>
     
   
   </table>
  
  </body>
</html>