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
     <script src="<%=info.getReqUrl()%>/PopScripts/jquery-1.2.6.min.js" type="text/javascript"></script>
   <link href="<%=info.getReqUrl()%>/calendar/calendar.css" rel="stylesheet" type="text/css">
      
  </head>
   <%
    //util.updAccessLog(request,response,"Assort Return", "Assort Return");
   String PopUpidn = util.nvl((String)request.getAttribute("PopUpidn"));
   String wetLoss =   util.nvl(request.getParameter("wt"));
   String load="";
   if(!PopUpidn.equals(""))
   load="loadASSFNL('TR_"+PopUpidn+"','LNK_"+PopUpidn+"')";
   String logId=String.valueOf(info.getLogId());
        String onfocus="cook('"+logId+"')";
        %>
 <body onfocus="<%=onfocus%>" onload="<%=load%>">
  <%
   ArrayList stockList = (ArrayList)request.getAttribute("StockList");
    String isRepair = util.nvl((String)session.getAttribute("ISREPAIR"));
    String mstkIdn = (String)request.getAttribute("mstkIdn");   
   HashMap mprp = info.getMprp();
   HashMap prp = info.getPrp();
    if(stockList!=null && stockList.size() >0){
    HashMap stkUpd = null;
    String lprp =null;
    String flg = null;
    String typ = null;
  %>
   <html:form action="assort/assortReturn.do?method=savePrpUpd" method="post" onsubmit="return validate_issue('CHK_' , 'count')" >
 <input type="hidden" name="wt" id="wt" value="<%=wetLoss%>"/>
  <html:hidden property="value(issIdn)" name="assortReturnForm" styleId="issIdn" />
    <html:hidden property="value(mstkIdn)" name="assortReturnForm" styleId="mstkIdn" />
    <html:hidden property="value(prcId)" styleId="prcId" name="assortReturnForm"  />
     <html:hidden property="value(lastpage)" name="assortReturnForm" styleId="lastpage" />
     <html:hidden property="value(currentpage)" name="assortReturnForm" styleId="currentpage" />
    <html:hidden property="value(url)" styleId="url" name="assortReturnForm"  />
<table><tr align="center"><td><a href="#"  class="first" data-action="first" title="First" onclick="pagniation('first');"><img src="../images/firstico.png" /></a></td>
    <td><a href="#" class="previous" data-action="previous" title="Previous" onclick="pagniation('previous');"><img src="../images/previousico.png" /></a></td>
    <td><a href="#" class="next" data-action="next" title="Next" onclick="pagniation('next');"><img src="../images/nextico.png" /></a></td>
    <td><a href="#" class="last" data-action="last" title="Last" onclick="pagniation('last');"><img src="../images/lastico.png" /></a></td>
        <td><html:text property="value(findvnm)" styleId="findvnm" size="15" name="assortReturnForm" /></td>
    <td><input type="button" class="submit" value="Find Vnm" onclick="pagniation('findvnm')"/></td>
</tr></table> 
  <table cellpadding="0" cellspacing="0">
 <%String msg = (String)request.getAttribute("msg");
  if(msg!=null){
  %>
  <tr><td><span class="redLabel"> <%=msg%></span></td></tr>
  <%}%>
 <tr><td>&nbsp;<span class="txtLink" > <bean:write property="value(vnm)" name="assortReturnForm" /></span>&nbsp; <html:submit property="value(issue)" styleClass="submit" value="Verify Changes" />
  <%if(isRepair.equals("Y")){%>
 <html:hidden property="value(stkIdn)" name="assortReturnForm"  styleId="stkIdn"  />
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
  
  <tr><td bgcolor="White">
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

 <tr>
 <% String prpIdn=lprp+"_0";%>
 <td >
 <%if(flg.equals("COMP")){%>
 <span class="redLabel">*</span>
 <%}%>
 <%=lprp%></td><td id="<%=prpIdn%>"><%=stkUpd.get("issVal")%></td><td><img src="../images/tick.png" id="<%=lprp%>_Lbl" style="display:none" />
 <%if(!flg.equals("FTCH")){
 String calWeightLoss="";
 if(wetLoss.equals("Y") && lprp.equals("CRTWT")){
 calWeightLoss ="calWeightLoss('"+lprp+"')";
 }
  String  fldName = "value("+lprp+")";
  String onChange = "";
  if(typ.equals("C")){
  onChange = "updatePrp(this,'"+lprp+"','S')";
  ArrayList prpVal = (ArrayList)prp.get(lprp+"V");
 
  %>
  <html:select property="<%=fldName%>" styleId="<%=lprp%>" name="assortReturnForm" onchange="<%=onChange%>">
  <html:option value="">--select--</html:option>
  <%
  for(int j=0;j<prpVal.size();j++){
  String prpPrt = (String)prpVal.get(j);
  %>
  <html:option value="<%=prpPrt%>"><%=prpPrt%></html:option>
  <%}%>
  </html:select>
  <%}else if(typ.equals("D")){
  onChange = "updatePrp(this,'"+lprp+"' ,'T');"+calWeightLoss;%>
  <html:text property="<%=fldName%>" styleId="<%=lprp%>" readonly="true" onfocus="<%=onChange%>" name="assortReturnForm" size="10" /><img src="<%=info.getReqUrl()%>/images/calender.png" onClick="setYears(2011, <%=info.getCurrentYear()%>);showCalender(this, '<%=lprp%>');"> 
  <%}else{
   onChange = "updatePrp(this,'"+lprp+"' ,'T');"+calWeightLoss;
  %>
  <html:text property="<%=fldName%>" styleId="<%=lprp%>" onchange="<%=onChange%>"  name="assortReturnForm"/>
  <%}}else{%>
  &nbsp;&nbsp;&nbsp;&nbsp;
 <%}%>
 <%if(wetLoss.equals("Y") && lprp.equals("CRTWT")){
 String wetLossVlu="value(weightLoss)";
 %>
 &nbsp&nbsp Weight Loss <html:text property="<%=wetLossVlu%>" styleId="wetLossIdn"   name="assortReturnForm" size="10"/>
 
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
  <html:select property="<%=rpairVal%>" onchange="<%=onClick%>" styleId="<%=rpairValId%>" name="assortReturnForm">
  <html:option value="">--select--</html:option>
  <%
  for(int j=0;j<prpVal.size();j++){
  String prpPrt = (String)prpVal.get(j);
  %>
  <html:option value="<%=prpPrt%>"><%=prpPrt%></html:option>
  <%}%>
  </html:select>
  <%}else if(typ.equals("D")){%>
  <html:text property="<%=rpairVal%>" styleId="<%=rpairValId%>" readonly="true" onfocus="<%=onClick%>" name="assortReturnForm" size="10" /><img src="<%=info.getReqUrl()%>/images/calender.png" onClick="setYears(2011, <%=info.getCurrentYear()%>);showCalender(this, '<%=rpairValId%>');"> 
  <%}else{
  
  %>
  <html:text property="<%=rpairVal%>" onchange="<%=onClick%>" styleId="<%=rpairValId%>" name="assortReturnForm"/>
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