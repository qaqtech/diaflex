<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page errorPage="../error_page.jsp" %>
<%@ page contentType="text/html;charset=windows-1252"%>
<jsp:useBean id="info" class="ft.com.InfoMgr" scope="session" />
<jsp:useBean id="gtMgr" class="ft.com.GtMgr" scope="session" />

<jsp:useBean id="util" class="ft.com.JspUtil" scope="page" />
<%@ page import="java.util.ArrayList,java.util.LinkedHashMap, java.util.Set,java.util.HashMap,java.sql.ResultSet,ft.com.*, java.util.Date, java.util.ArrayList, ft.com.dao.*"%>
<%@taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>Bidding Return</title>
    <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/bse.css?v=<%=info.getJsVersion()%>"/>
    <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/general.css?v=<%=info.getJsVersion()%>"/>
     <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/style.css?v=<%=info.getJsVersion()%>"/>
      <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/bse.js?v=<%=info.getJsVersion()%> " > </script>
       <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/assortScript.js?v=<%=info.getJsVersion()%> " > </script>
    <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/priceChange.js?v=<%=info.getJsVersion()%> " > </script>
    <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/newCalScript.js?v=<%=info.getJsVersion()%> " > </script>

  </head>
        <%
        
        String logId=String.valueOf(info.getLogId());
        String onfocus="cook('"+logId+"')";
        String lstNme = (String)gtMgr.getValue("lstNmeBIDRTN");
       HashMap stockListMap = (HashMap)gtMgr.getValue(lstNme);
      

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
  <img src="../images/line.jpg" border="0" /></td><td valign="middle"> <span class="pgHed">Bidding Return</span> </td>
</tr></table>
  </td>
  </tr>
   <%
   String msg = (String)request.getAttribute("msg");
   if(msg!=null){
     %>
    <tr><td valign="top" class="tdLayout"><span class="redLabel"><%=msg%></span></td></tr>
   <%}%>
   
   <tr>
   <td valign="top" class="tdLayout">
      <html:form action="marketing/biddingReturnAction.do?method=fecth" method="post" onsubmit="return validate_Assortreturn()" >
  <table><tr><td>Process </td>
   <td>
   <html:select property="value(mprcIdn)"  styleId="mprcIdn" name="biddingReturnForm"    >
            <html:optionsCollection property="mprcList" name="biddingReturnForm" 
            label="prc" value="mprcId" />
    </html:select>
   </td></tr>
   <tr>
    <td>
   Employee : </td>
   <td>
    <html:select property="value(empIdn)"  styleId="empIdn" name="biddingReturnForm"   >
           <html:option value="0" >--select--</html:option>
            <html:optionsCollection property="empList" name="biddingReturnForm" 
            label="emp_nme" value="emp_idn" />
            </html:select>
   
   </td></tr>
   <tr>
   <td>Issue Id:</td><td> <html:text property="value(issueId)" styleId="issueId"  name="biddingReturnForm"  /></td>
   </tr>
   <tr>
   <td>Packet No :</td><td> <html:textarea property="value(vnmLst)" rows="7"  cols="30" name="biddingReturnForm"  /></td>
   </tr>
      <tr><td colspan="2" align="center"><html:submit property="view" value="View" styleClass="submit"/></td></tr>

   </table>
   </html:form>
   </td></tr>
    <%
   ArrayList msgLst = (ArrayList)request.getAttribute("msgList");
   if(msgLst!=null){
   for(int i=0;i<msgLst.size();i++){
   ArrayList msgs = (ArrayList)msgLst.get(i);
   %>
    <tr><td valign="top" class="tdLayout"><span class="redLabel"><%=msgs.get(1)%></span></td></tr>
   <%}}%>
   <tr>
   <td valign="top" class="tdLayout">
 <html:form action="marketing/biddingReturnAction.do?method=save" method="post" onsubmit="return validate_issue('CHK_' , 'count')" >
 <table>
 
  <% String view = (String)request.getAttribute("view");
   if(view!=null){
    if(stockListMap!=null && stockListMap.size()>0){
    HashMap mprp = info.getMprp();
    ArrayList vwPrpLst = (ArrayList)session.getAttribute("mktViewLst");
    %>
    <tr><td>
 <html:submit property="value(returnbyOriginalPrc)" styleClass="submit" value="Return"  />
 &nbsp;&nbsp;
 <html:submit property="value(return)" styleClass="submit" value="Return By Issue Price"  />
 &nbsp;&nbsp;
 <html:submit property="value(apply)" styleClass="submit" value="Apply Price"  />
 
 </td></tr>
    <tr><td>
          <html:hidden property="value(lstNme)" name="biddingReturnForm" value="<%=lstNme%>" />

     <table class="grid1">
   <tr> <th><input type="checkbox" name="checkAll" id="checkAll" onclick="ALLCheckBoxCal('checkAll','CHK_')" /> </th><th>Sr</th>
       <th>Issue Id</th>
        <th>Packet No.</th>
      <%for(int j=0; j < vwPrpLst.size(); j++ ){
    String prp = (String)vwPrpLst.get(j);
    String hdr = util.nvl((String)mprp.get(prp));
    String prpDsc = util.nvl((String)mprp.get(prp+"D"));
    if(hdr == null) {
        hdr = prp;
       }   
     %>
    <th title="<%=prpDsc%>"><%=hdr%></th>
  
      <%}%> </tr>
      <%
  ArrayList stockIdnLst =new ArrayList();
  LinkedHashMap stockList = new LinkedHashMap(stockListMap); 
  Set<String> keys = stockList.keySet();
 for(String key: keys){
       stockIdnLst.add(key);
  }
  int sr=0;
 for(int i=0; i < stockIdnLst.size(); i++ ){
  String stkIdn = (String)stockIdnLst.get(i);
 GtPktDtl stockPkt = (GtPktDtl)stockList.get(stkIdn);
 String checkFldId = "CHK_"+sr;
 String checkFldVal = "cb_stk_"+stkIdn ;
 String onclick = "CalTtlOnChlick("+stkIdn+" , this , 'NO' )";
 String rapRte = util.nvl(stockPkt.getValue("RAP_RTE"));
  sr = i+1;
 %>
 <tr>
<td><input type="checkbox" name="<%=checkFldVal%>" id="<%=checkFldId%>" value="<%=stkIdn%>" onclick="<%=onclick%>" /></td>
<td><%=sr%></td><td><%=stockPkt.getValue("issIdn")%></td>
<td><%=stockPkt.getVnm()%>
<input type="hidden" name="rap_<%=stkIdn%>" value="<%=rapRte%>" id="rap_<%=stkIdn%>" />
</td>

<%for(int j=0; j < vwPrpLst.size(); j++ ){
    String prp = (String)vwPrpLst.get(j);
 %>
    <td><%=stockPkt.getValue(prp)%>
    <%if(prp.equals("RTE") || prp.equals("RAP_DIS")){
    String fldVal = "upr_"+stkIdn;
    String onChange="CalculateRapDis("+stkIdn+")";
    if(prp.equals("RAP_DIS")){
    fldVal = "uprDis_"+stkIdn;
     onChange="CalculateRte("+stkIdn+")";
    }
    %>
    <input type="text" name="<%=fldVal%>" size="6" id="<%=fldVal%>" onchange="<%=onChange%>" />
    <%}%></td>
<%}%>
</tr>
 <%}%>
    <input type="hidden" name="count" id="count" value="<%=sr%>" />

</table></td></tr>
    <%}}%></table>
   </html:form>
   </td></tr>
   </table>
    </body>
</html>