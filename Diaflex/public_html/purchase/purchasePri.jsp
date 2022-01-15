<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page errorPage="../error_page.jsp" %>
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import="java.util.ArrayList,java.util.ArrayList,java.util.HashMap,java.sql.ResultSet,ft.com.*, java.util.Date, java.net.URLEncoder"%>
<jsp:useBean id="info" class="ft.com.InfoMgr" scope="session" />
<jsp:useBean id="util" class="ft.com.JspUtil" scope="page" />
<%@taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<%@taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>

<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/ajax.js?v=<%=info.getJsVersion()%> " > </script>
     <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/bse.js?v=<%=info.getJsVersion()%> " > </script>
      <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/assortScript.js?v=<%=info.getJsVersion()%> " > </script>
      <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/pri.js?v=<%=info.getJsVersion()%> " > </script>
      <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/bse.css?v=<%=info.getJsVersion()%>"/>
      <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/general.css?v=<%=info.getJsVersion()%>"/>
      <link href="<%=info.getReqUrl()%>/calendar/calendar.css" rel="stylesheet" type="text/css" />
      <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/style.css?v=<%=info.getJsVersion()%>"/>
       <script type="text/javascript" src="<%=info.getReqUrl()%>/PopScripts/popup.js?v=<%=info.getJsVersion()%> " > </script>
<script src="<%=info.getReqUrl()%>/PopScripts/jquery-1.2.6.min.js" type="text/javascript"></script>
    <title>purchase Pricing</title>
  </head>
        <%String logId=String.valueOf(info.getLogId());
        String onfocus="cook('"+logId+"')";
        %>
 <body onfocus="<%=onfocus%>" onload="MM_preloadImages('images/faunalogo.png')" topmargin="0" leftmargin="0" rightmargin="0" bottommargin="0">

<div id="backgroundPopup"></div>

<div id="popupContactSave" align="center" >
<img src="../images/loading_flame.gif" height="150px" width="150px" border="0" />
</div>
<table width="100%"  border="0" align="center" cellpadding="0" cellspacing="0" class="mainbg">
  <tr>
    <td height="103" valign="top">
   <jsp:include page="/header.jsp" /> </td> </tr>
  <tr>
    <td height="5" valign="top" class="greyline"><img src="<%=info.getReqUrl()%>/images/grey_linebig.png" width="685" height="5" align="right" /></td>
  </tr>
   <tr>
  <td valign="top" class="hedPg">
  <table cellpadding="1"  cellspacing="5"><tr><td valign="middle">
  <img src="../images/line.jpg" border="0" /></td><td valign="middle"> <span class="pgHed">Purchase Pricing</span> </td></tr></table>
  </td>
  </tr>
  <%
  String msg = (String)request.getAttribute("msg");
  if(msg!=null){
  %>
  <tr>
  <td class="tdLayout" valign="top">
 <span class="redLabel">  <%=msg%></span>
  </td></tr><%}%>
   <tr>
  <td class="tdLayout" valign="top">
  <html:form action="purchase/purchasePriceAction.do?method=fetch" method="post">
  <table class="grid1"  cellpadding="5">
                <tr>
                <td>Vender</td>
                <td>
                <html:select property="value(vender)" styleId="venId" onchange="getPurIds(this)" >
                <html:option value="0">---select---</html:option>
                <html:optionsCollection property="venderList"  name="purchasePriceForm"   value="byrIdn" label="byrNme" />
                
                </html:select>
                </td> </tr>
                 <tr>
            <td> <span class="txtBold" >Purchase Ids </span></td><td>
             
             <html:select property="value(purIdn)"  styleId="purIdn" >
            <html:option value="0">---select---</html:option>
            <html:optionsCollection property="purIdnList" name="purchasePriceForm"
            label="idn" value="nmeIdn" />
            </html:select>
            
            </td>
            </tr>
        <tr><td colspan="2"><html:submit property="submit" value="View" styleClass="submit"/></td> </tr>
        </table>
  </html:form>
  
  </td></tr>
  <html:form action="purchase/purchasePriceAction.do?method=reprc" method="post">
  <%
  ArrayList pktList = (ArrayList)request.getAttribute("pktList");
  if(pktList!=null && pktList.size()>0){
   ArrayList prpDspBlocked = info.getPageblockList();
   ArrayList vwPrpLst = (ArrayList)session.getAttribute("PurViewLst");
   HashMap mprp = info.getMprp();
   HashMap totals = (HashMap)request.getAttribute("totalMap");
  %>
  <tr>
  <td class="tdLayout" valign="top">
  <table>
   <tr><td>Total :&nbsp;&nbsp;</td><td><span id="ttlqty"> <%=totals.get("qty")%>&nbsp;&nbsp;</span></td><td>cts&nbsp;&nbsp;</td><td><span id="ttlcts"><%=totals.get("cts")%>&nbsp;&nbsp;</span></td><td>Selected:&nbsp;&nbsp;</td><td> Total :&nbsp;&nbsp;</td> <td><span id="qtyTtl">0&nbsp;&nbsp;</span></td><td>Cts&nbsp;&nbsp;</td><td><span id="ctsTtl">0</span> </td> </tr>
  
   </table>
  </td></tr>
   <tr>
  <td class="tdLayout" valign="top">
  <table><tr><td>
  <html:submit property="value(submit)" value="Compute Price" styleClass="submit" onclick="return confirmChangesSL()" />
  </td>
    <%
  ArrayList rolenmLst=(ArrayList)info.getRoleLst();
  String usrFlg=util.nvl((String)info.getUsrFlg());
  if(usrFlg.equals("SYS") || rolenmLst.contains("ADMIN")){
  %>
  <td><img src="../images/edit.jpg" title="Edit Properties Module" onclick="newWindow('<%=info.getReqUrl()%>/dynamicProperty.do?method=load&mdl=PUR_VW&sname=PurViewLst&par=A')" border="0" width="15px" height="15px"/> </td>
  <%}%>
  </tr></table>
  </td></tr>
  
  <tr>
  <td class="tdLayout" valign="top">
  
  <table class="grid1" align="left" id="dataTable">
<thead>
<tr>
<th><label title="SR NO">Sr No.</label></th>
<th><label title="CheckAll"><input  type="checkbox" id="all" name="all" onclick="ALLCheckBox('all','cb_prc_')" />Select </label></th>
<%for(int j=0; j < vwPrpLst.size(); j++ ){
    String prp = (String)vwPrpLst.get(j);
    if(prpDspBlocked.contains(prp)){
    }else{
     String hdr = (String)mprp.get(prp);
    String prpDsc = (String)mprp.get(prp+"D");
    if(hdr == null) {
        hdr = prp;
       }  
    if(prp.equals("RTE")){%>
     <th title="<%=prpDsc%>">Rate</th>
    <th title="<%=prpDsc%>">Dis</th>
     <%}else  if(prp.equals("CMP_RTE")){%>
      <th title="<%=prpDsc%>">CMP</th>
     <th title="<%=prpDsc%>">CMP DIS</th>
     <%}else{%>
<th title="<%=prpDsc%>"><%=hdr%></th>
<%}}}%>
</tr>
<%for(int i=0;i<pktList.size();i++){
HashMap pktDtl = (HashMap)pktList.get(i);
String stkIdn = (String)pktDtl.get("stk_idn");
String cts = (String)pktDtl.get("cts");
String checkVal = "value("+stkIdn+")";
String checkId = "cb_prc_"+stkIdn;
String onchnge="ChangeFlg("+stkIdn+" , this , 'NO' )";
%>
<tr>

<td><%=i+1%><input type="hidden" id="CTS_<%=stkIdn%>"  value="<%=cts%>" /></td>
<td><html:checkbox property="<%=checkVal%>" styleId="<%=checkId%>"  onclick="<%=onchnge%>" name="purchasePriceForm" /> </td>
<%for(int j=0; j < vwPrpLst.size(); j++ ){
    String prp = (String)vwPrpLst.get(j);
    if(prpDspBlocked.contains(prp)){
    }else{
     if(prp.equals("RTE")){%>
    <td><%=util.nvl((String)pktDtl.get("quot"))%></td>
     <td><%=util.nvl((String)pktDtl.get("dis"))%></td>
     <%}else if(prp.equals("CMP_RTE")){%>
      <td><%=util.nvl((String)pktDtl.get("cmp"))%></td>
     <td><%=util.nvl((String)pktDtl.get("cmpdis"))%></td>
     <%}else{%>
    <td><%=util.nvl((String)pktDtl.get(prp))%></td>
    <%}%>


<%}}%>
</tr>
<%}%>


</thead></table>


  </td></tr>
  <%}%>
  </html:form>
  <tr>
  <td><jsp:include page="/footer.jsp" /> </td>
  </tr>
  </table>
  
  
  </body>
</html>