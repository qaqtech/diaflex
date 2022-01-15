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
    <title>assortRtnUpd</title>
    <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/bse.css?v=<%=info.getJsVersion()%>"/>
      <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/style.css?v=<%=info.getJsVersion()%>"/>
      <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/assortScript.js?v=<%=info.getJsVersion()%> " > </script>
      <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/bse.js?v=<%=info.getJsVersion()%> " > </script>
       <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/pri.js?v=<%=info.getJsVersion()%> " > </script>
   <script language="javaScript" type="text/javascript" src="<%=info.getReqUrl()%>/calendar/calendarFmt.js"></script>
   <link href="<%=info.getReqUrl()%>/calendar/calendar.css" rel="stylesheet" type="text/css">
      <script src="<%=info.getReqUrl()%>/scripts/priceChange.js?v=<%=info.getJsVersion()%>" type="text/javascript"></script>
  </head>
   <%
   // util.updAccessLog(request,response,"Price Return", "Price Return");
   String PopUpidn = util.nvl((String)request.getAttribute("PopUpidn"));
   String load="";
   if(!PopUpidn.equals(""))
   load="loadASSFNL('TR_"+PopUpidn+"','LNK_"+PopUpidn+"')";
   %>
        <%String logId=String.valueOf(info.getLogId());
        String onfocus="cook('"+logId+"')";
        %>
 <body onfocus="<%=onfocus%>" onload="<%=load%>">
  <%
   ArrayList stockList = (ArrayList)request.getAttribute("StockList");
    String isRepair = util.nvl((String)session.getAttribute("ISREPAIR"));
    ArrayList grpSheetList =(ArrayList)request.getAttribute("grpSheetList");
    String mstkIdn = (String)request.getAttribute("mstkIdn");   
    HashMap pricePrpDtl=(HashMap)session.getAttribute("pricePrpDtl");
    ArrayList prcPrpIssueEditList =(ArrayList)request.getAttribute("prcPrpIssueEditList");
   HashMap mprp = info.getMprp();
   HashMap prp = info.getPrp();
   HashMap pktList = (HashMap)session.getAttribute("pktList");  
   HashMap pktData = (HashMap)pktList.get(mstkIdn);
   String rap_rte = util.nvl((String)pktData.get("rap_rte"));
    if(stockList!=null && stockList.size() >0){
    HashMap stkUpd = null;
    String lprp =null;
    String flg = null;
    String typ = null;
  %>
   <html:form action="pri/pricertn.do?method=savePrpUpd" method="post" onsubmit="return validate_issue('CHK_' , 'count')" >
 
  <html:hidden property="value(issIdn)" name="priceRtnForm" styleId="issIdn" />
    <html:hidden property="value(mstkIdn)" name="priceRtnForm" styleId="mstkIdn" />
    <html:hidden property="value(prcId)" styleId="prcId" name="priceRtnForm"  />
     <html:hidden property="value(lastpage)" name="priceRtnForm" styleId="lastpage" />
     <html:hidden property="value(currentpage)" name="priceRtnForm" styleId="currentpage" />
    <html:hidden property="value(url)" styleId="url" name="priceRtnForm"  />
    <html:hidden property="value(stt)" styleId="stt" name="priceRtnForm"  />
    <html:hidden property="value(rap_rte)" styleId="rap_rte" name="priceRtnForm" value="<%=rap_rte%>"  />
<table><tr align="center"><td><a href="#"  class="first" data-action="first" title="First" onclick="pagniation('first');"><img src="../images/firstico.png" /></a></td>
    <td><a href="#" class="previous" data-action="previous" title="Previous" onclick="pagniation('previous');"><img src="../images/previousico.png" /></a></td>
    <td><a href="#" class="next" data-action="next" title="Next" onclick="pagniation('next');"><img src="../images/nextico.png" /></a></td>
    <td><a href="#" class="last" data-action="last" title="Last" onclick="pagniation('last');"><img src="../images/lastico.png" /></a></td>
    <td><html:text property="value(findvnm)" styleId="findvnm" size="15" name="priceRtnForm" /></td>
    <td><input type="button" class="submit" value="Find Vnm" onclick="pagniation('findvnm')"/></td>
</tr></table> 
  <table cellpadding="0" cellspacing="0">
 <%String msg = (String)request.getAttribute("msg");
  if(msg!=null){
  %>
  <tr><td><span class="redLabel"> <%=msg%></span></td></tr>
  <%}%>
 <tr><td>&nbsp;<span class="txtLink" > <bean:write property="value(vnm)" name="priceRtnForm" /></span>&nbsp; <html:submit property="value(issue)" styleClass="submit" value="Verify Changes" />
 <logic:equal property="value(applyYN)"  name="priceRtnForm"  value="PRI_FN_IS" >
 &nbsp; <html:submit property="value(apply)" styleClass="submit" value="Apply" />
     <span><span id="prcDtl">
    <b>Calculation Details</b>
    <b>CMP_RTE : </b><bean:write property="value(CMP_RTE_FIX)" name="priceRtnForm" />
    <b>FA_PRI : </b><bean:write property="value(FA_PRI_FIX)" name="priceRtnForm" />
    <b>MFG_RTE : </b><bean:write property="value(MFG_RTE_FIX)" name="priceRtnForm" />
    <b>RAP_RTE : </b><bean:write property="value(RAP_RTE_FIX)" name="priceRtnForm" />
    </span></span>
    <logic:equal property="value(APPLIED_MFG_SHEET)"  name="priceRtnForm"  value="" >
    <%if(grpSheetList.size()>0){%>
    <span><span id="prcDtl">
    <b> || Sheet Details</b>
    <%for(int i=0;i<grpSheetList.size();i++){
    ArrayList grpDtl=(ArrayList)grpSheetList.get(i);%>
    <b><%=grpDtl.get(0)%> : </b><%=grpDtl.get(1)%> 
    <%}%>
    </span></span>
    <%}%>
    </logic:equal>
    <logic:notEqual property="value(APPLIED_MFG_SHEET)"  name="priceRtnForm"  value="" >
    <b> || Sheet Details </b>
    <b><bean:write property="value(APPLIED_MFG_SHEET)" name="priceRtnForm" /></b>
    </logic:notEqual>
 </logic:equal>
  <%if(isRepair.equals("Y")){%>
 <html:hidden property="value(stkIdn)" name="priceRtnForm"  styleId="stkIdn"  />
    <span id="prcDis"><span id="prcDtl">
    <b>Calculation Details</b>
    <b>Rte : <%=util.nvl((String)request.getAttribute("cmp"))%></b>
    <b>Dis : <%=util.nvl((String)request.getAttribute("uprDis"))%></b>
    </span></span>
    <%}%>
 
 </td></tr>
  
  <tr><td>
  
  <div class="memo">
  
  <table  width="700" cellpadding="0" cellspacing="2">
  
  <tr><td bgcolor="White" valign="top">
  <table border="0" class="Orange" cellspacing="1" cellpadding="1" >
          
           <tr><th class="Orangeth">PROPERTY</th><th class="Orangeth" colspan="2">Value</th>
           <%if(isRepair.equals("Y")){%>
           <th class="Orangeth">Rep Val</th>
           <%}%>
           </tr>
           
  <%
  int count=0;
//  int srchSize = (stockList.size()/3)+1;
int srchSize = 12;
  for(int i=0;i<stockList.size() ;i++){
  stkUpd = (HashMap)stockList.get(i);
   lprp = util.nvl((String)stkUpd.get("mprp"));
   flg = util.nvl((String)stkUpd.get("flg"));
   typ = util.nvl((String)mprp.get(lprp+"T"));
 
  
    if(count==srchSize){
             count=0;
             %></table></td><td valign="top">
            <table border="0" class="Orange" cellspacing="1" cellpadding="1" >
          
         <tr><th class="Orangeth">PROPERTY</th><th class="Orangeth" colspan="2">Value</th>
          <%if(isRepair.equals("Y")){%>
           <th class="Orangeth">Rep Val</th>
           <%}%>
         </tr>
     <% }
           count++;
           %>

 <tr><td>
 <%if(flg.equals("COMP")){%>
 <span class="redLabel">*</span>
 <%}%>
 <%=lprp%></td><td><%=stkUpd.get("issVal")%></td>
  <td ><img src="../images/tick.png" id="<%=lprp%>_Lbl" style="display:none" />
    <%if(!flg.equals("FTCH")){ %>
 <%
  String  fldName = "value("+lprp+")";
  String onChange = "";
  if(typ.equals("C")){
  onChange = "updatePrp(this,'"+lprp+"','S')";
  ArrayList prpVal = (ArrayList)prp.get(lprp+"V");
  
  %>
  <html:select property="<%=fldName%>" styleId="<%=lprp%>" name="priceRtnForm" onchange="<%=onChange%>">
  <html:option value="">--select--</html:option>
  <%
  for(int j=0;j<prpVal.size();j++){
  String prpPrt = (String)prpVal.get(j);
  %>
  <html:option value="<%=prpPrt%>"><%=prpPrt%></html:option>
  <%}%>
  </html:select>
  <%} else if(typ.equals("N")){
  String onchange="";
  String getRtnPrp=util.nvl((String)pricePrpDtl.get(lprp));
  if(!getRtnPrp.equals("")){
  if(prcPrpIssueEditList.contains(getRtnPrp)){
  onchange+="getviceversaRTEDISpricingissue('"+lprp+"','"+getRtnPrp+"');";
  }
  }
  onchange+="updatePrp(this,'"+lprp+"' ,'N')";
  
  %>
   <html:text property="<%=fldName%>" styleId="<%=lprp%>" onchange="<%=onchange%>" size="4" name="priceRtnForm"/>
   <%}else if(typ.equals("D")){
  onChange = "updatePrp(this,'"+lprp+"' ,'T')";%>
  <html:text property="<%=fldName%>" styleId="<%=lprp%>" readonly="true" onfocus="<%=onChange%>" name="priceRtnForm" size="10" /><img src="<%=info.getReqUrl()%>/images/calender.png" onClick="setYears(2011, <%=info.getCurrentYear()%>);showCalender(this, '<%=lprp%>');"> 
  <%}else{
   onChange = "updatePrp(this,'"+lprp+"' ,'T')";
  %>
  <html:text property="<%=fldName%>" styleId="<%=lprp%>" onchange="<%=onChange%>"  name="priceRtnForm"/>
  <%}
  }else{%>
  &nbsp;&nbsp;&nbsp;&nbsp;
 <%}%>
  </td>
  <%if(isRepair.equals("Y")){
  String rpairVal = "value(RP_"+lprp+")";
  String rpairValId="RP_"+mstkIdn+"_"+lprp;
  String onClick="";
  String sid="";
  onClick = "priCalcAssort('"+lprp+"')";
  %>
    <td nowrap="nowrap">
   <%  if(typ.equals("C")){
  
  ArrayList prpVal = (ArrayList)prp.get(lprp+"V");
  
  %>
  <html:select property="<%=rpairVal%>" onchange="<%=onClick%>" styleId="<%=rpairValId%>" name="priceRtnForm">
  <html:option value="">--select--</html:option>
  <%
  for(int j=0;j<prpVal.size();j++){
  String prpPrt = (String)prpVal.get(j);
  %>
  <html:option value="<%=prpPrt%>"><%=prpPrt%></html:option>
  <%}%>
  </html:select>
  <%}else if(typ.equals("D")){%>
  <html:text property="<%=rpairVal%>" styleId="<%=rpairValId%>" readonly="true" onfocus="<%=onClick%>" name="priceRtnForm" size="10" /><img src="<%=info.getReqUrl()%>/images/calender.png" onClick="setYears(2011, <%=info.getCurrentYear()%>);showCalender(this, '<%=rpairValId%>');"> 
  <%}else{
 
  %>
  <html:text property="<%=rpairVal%>" onchange="<%=onClick%>" styleId="<%=rpairValId%>" name="priceRtnForm"/>
  <%}%>
    
    
    </td>
           
  <%}%>
   </tr>
 
 <%}%>
  </table></td>
 </tr></table>
  
  </div>
   </td></tr>
  </table>
  </html:form>
  <%}%>
 
  </body>
    <%@include file="../calendar.jsp"%>
</html>