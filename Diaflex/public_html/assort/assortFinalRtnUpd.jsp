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
      <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/assortScript.js?v=<%=info.getJsVersion()%> " ></script>
      <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/pri.js?v=<%=info.getJsVersion()%> " > </script>
      <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/bse.js?v=<%=info.getJsVersion()%> " > </script>
        <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/ajax.js?v=<%=info.getJsVersion()%> " > </script>
       <script type="text/javascript" src="<%=info.getReqUrl()%>/PopScripts/popup.js?v=<%=info.getJsVersion()%> " > </script>
   <script src="<%=info.getReqUrl()%>/PopScripts/jquery-1.2.6.min.js" type="text/javascript"></script>
  <script language="javaScript" type="text/javascript" src="<%=info.getReqUrl()%>/calendar/calendarFmt.js"></script>
   <link href="<%=info.getReqUrl()%>/calendar/calendar.css" rel="stylesheet" type="text/css">
  </head>
  <%
   //util.updAccessLog(request,response,"AssortFinal Update", "AssortFinal Update");
    HashMap allPageDtl= (info.getPageDetails() == null)?new HashMap():(HashMap)info.getPageDetails();
    HashMap pageDtl=(HashMap)allPageDtl.get("ASSORT_FINAL_RT");
    ArrayList pageList=new ArrayList();
    HashMap pageDtlMap=new HashMap();
    String fld_nme="",fld_ttl="",val_cond="",dflt_val="",fld_typ="",form_nme="",flg1="";
   String PopUpidn = util.nvl((String)request.getAttribute("PopUpidn"));
   String load="";
   if(!PopUpidn.equals(""))
   load="loadASSFNL('TR_"+PopUpidn+"','LNK_"+PopUpidn+"')";
        String logId=String.valueOf(info.getLogId());
        String onfocus="cook('"+logId+"')";
        %>
 <body onfocus="<%=onfocus%>" onload="<%=load%>">
  <%
  String isRepair = util.nvl((String)session.getAttribute("ISREPAIR"));
    
   ArrayList stockList = (ArrayList)request.getAttribute("StockList");
  ArrayList empIdnList =(ArrayList)request.getAttribute("empIdnList");
  HashMap empPrpMainMap = (HashMap)request.getAttribute("empPrpMainMap");
  ArrayList assortUpdPrp = (ArrayList)session.getAttribute("assortUpdPrp");
  HashMap empNameMap = (HashMap)request.getAttribute("empNameMap");
  ArrayList diffPrpList = (ArrayList)request.getAttribute("diffPrpList");
  ArrayList grpSheetList =(ArrayList)request.getAttribute("grpSheetList");
  HashMap prpValMap = (HashMap)request.getAttribute("prpValMap");
  String mstkIdn = (String)request.getAttribute("mstkIdn");
  String vnm = (String)request.getAttribute("vnm");
   HashMap mprp = info.getMprp();
   HashMap prp = info.getPrp();
   boolean isAjax = true;
    if(stockList!=null && stockList.size() >0){
    HashMap stkUpd = null;
    String lprp =null;
    String flg = null;
    String typ = null;
    
  %>
   <html:form action="assort/assortFinalRtn.do?method=savePrpUpd" method="post" onsubmit="return validate_issue('CHK_' , 'count')" >
 
  <html:hidden property="value(issIdn)" name="assortFinalRtnForm" styleId="issIdn" />
    <html:hidden property="value(mstkIdn)" name="assortFinalRtnForm" styleId="mstkIdn" />
    <html:hidden property="value(prcId)" name="assortFinalRtnForm"  />
         <html:hidden property="value(lastpage)" name="assortFinalRtnForm" styleId="lastpage" />
     <html:hidden property="value(currentpage)" name="assortFinalRtnForm" styleId="currentpage" />
    <html:hidden property="value(url)" styleId="url" name="assortFinalRtnForm"  />
    <html:hidden property="value(stt)" styleId="stt" name="assortFinalRtnForm"  />
   <table><tr align="center"><td><a href="#"  class="first" data-action="first" title="First" onclick="pagniation('first');"><img src="../images/firstico.png" /></a></td>
    <td><a href="#" class="previous" data-action="previous" title="Previous" onclick="pagniation('previous');"><img src="../images/previousico.png" /></a></td>
    <td><a href="#" class="next" data-action="next" title="Next" onclick="pagniation('next');"><img src="../images/nextico.png" /></a></td>
    <td><a href="#" class="last" data-action="last" title="Last" onclick="pagniation('last');"><img src="../images/lastico.png" /></a></td>
    <td><html:text property="value(findvnm)" styleId="findvnm" size="15" name="assortFinalRtnForm" /></td>
    <td><input type="button" class="submit" value="Find Vnm" onclick="pagniation('findvnm')"/></td>
</tr></table> 
  <table cellpadding="0"  cellspacing="0" >
  <%String msg = (String)request.getAttribute("msg");
  if(msg!=null){
  %>
  <tr><td><span class="redLabel"> <%=msg%></span></td></tr>
  <%}%>
 <tr><td>&nbsp;&nbsp;<span class="txtLink" > <%=vnm%></span> 
  <logic:notEqual property="value(applyYN)"  name="assortFinalRtnForm"  value="Y" >
 <%
   pageList= ((ArrayList)pageDtl.get("AJAXRTN") == null)?new ArrayList():(ArrayList)pageDtl.get("AJAXRTN");
    if(pageList==null || pageList.size()==0){ 
    isAjax = false;
    %>
  <html:submit property="value(issue)" styleClass="submit" value="Verify Changes" /> 
  <%}%>
 </logic:notEqual>
  <logic:equal property="value(applyYN)"  name="assortFinalRtnForm"  value="Y" >
   <% isAjax = false;%>
 &nbsp; <html:submit property="value(apply)" styleClass="submit" value="Apply" />
  &nbsp; <html:submit property="value(applyPri)" styleClass="submit" value="Apply Priceing" />
     <span><span id="prcDtl">
    <b>Calculation Details</b>
    <b>CMP_RTE : </b><bean:write property="value(CMP_RTE_FIX)" name="assortFinalRtnForm" />
    <b>FA_PRI : </b><bean:write property="value(FA_PRI_FIX)" name="assortFinalRtnForm" />
    <b>MFG_RTE : </b><bean:write property="value(MFG_RTE_FIX)" name="assortFinalRtnForm" />
    <b>RAP_RTE : </b><bean:write property="value(RAP_RTE_FIX)" name="assortFinalRtnForm" />
    </span></span>
    <logic:equal property="value(APPLIED_MFG_SHEET)"  name="assortFinalRtnForm"  value="" >
    <%if(grpSheetList!=null && grpSheetList.size()>0){%>
    <span><span id="prcDtl">
    <b> || Sheet Details</b>
    <%for(int i=0;i<grpSheetList.size();i++){
    ArrayList grpDtl=(ArrayList)grpSheetList.get(i);%>
    <b><%=grpDtl.get(0)%> : </b><%=grpDtl.get(1)%> 
    <%}%>
    </span></span>
    <%}%>
    </logic:equal>
    <logic:notEqual property="value(APPLIED_MFG_SHEET)"  name="assortFinalRtnForm"  value="" >
    <b> || Sheet Details </b>
    <b><bean:write property="value(APPLIED_MFG_SHEET)" name="assortFinalRtnForm" /></b>
    </logic:notEqual>
 </logic:equal>
 <%if(isRepair.equals("Y")){%>
 <html:hidden property="value(stkIdn)" name="assortFinalRtnForm"  styleId="stkIdn"  />
    <span id="prcDis"><span id="prcDtl">
    <b>Calculation Details</b>
    <b>Rte : <%=util.nvl((String)request.getAttribute("cmp"))%></b>
    <b>Dis : <%=util.nvl((String)request.getAttribute("uprDis"))%></b>
    </span></span>
    <%}%>
 </td></tr>
  <tr><td>
  
  <div class="memo">
  <table width="800" >
  <tr><td>
  <table border="0" width="80%" class="grid1" cellspacing="1" cellpadding="1">
  <%
 
  if(empIdnList!=null && empIdnList.size() > 0){%>
  <tr><th>Emp Name</th>
  <%for(int n=0;n<assortUpdPrp.size();n++){
  String editPrp = util.nvl((String)assortUpdPrp.get(n));
  %>
  <th><%=editPrp%></th>
  <%}%></tr>
  
  <% for(int m=0 ; m<empIdnList.size();m++){
  String empIdn =(String)empIdnList.get(m);
  HashMap empPrp = (HashMap)empPrpMainMap.get(empIdn);
 
  %>
  <tr><td nowrap="nowrap"><%=empNameMap.get(empIdn)%></td>
  <%for(int n=0;n<assortUpdPrp.size();n++){
  String editPrp = (String)assortUpdPrp.get(n);
  String bgColor ="";
  if(diffPrpList.contains(editPrp))
   bgColor = "bgcolor=\"Lime\"";
  %>
  <td <%=bgColor%>  nowrap="nowrap"><%=util.nvl((String)empPrp.get(editPrp))%></td>
  <%}%>
  </tr>
  <%}}%></table>
  </td></tr>
  <tr><td>
  <table  cellpadding="0" cellspacing="2">
  
  <tr><td bgcolor="White">
  <table border="0" width="250" class="Orange" cellspacing="1" cellpadding="1" >
          
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
   lprp = (String)stkUpd.get("mprp");
   flg = (String)stkUpd.get("flg");
    typ = util.nvl((String)mprp.get(lprp+"T"));
 
   if(count==srchSize){
             count=0;
   %></table></td><td valign="top">
  <table border="0"  width="250"  class="Orange" cellspacing="1" cellpadding="1" >
     <tr><th class="Orangeth">PROPERTY</th><th class="Orangeth" colspan="2">Value</th>
     <%if(isRepair.equals("Y")){%>
           <th class="Orangeth">Rep Val</th>
           <%}%>
     </tr>
     <% }
           count++;
           %>

 <tr> <td>
 <%if(flg.equals("COMP")){%>
 <span class="redLabel">*</span>
 <%}%>
 <%=lprp%></td><td><%=stkUpd.get("issVal")%></td><td> <img src="../images/tick.png" id="<%=lprp%>_Lbl" style="display:none" />
<%if(!flg.equals("FTCH")){
  String  fldName = "value("+mstkIdn+"_"+lprp+")";
  String onChange ="";
  if(typ.equals("C")){
  if(isAjax)
    onChange = "updatePrp(this,'"+lprp+"','S')";
  ArrayList prpVal = (ArrayList)prp.get(lprp+"V");
 %>
  <html:select property="<%=fldName%>" styleId="<%=lprp%>" onblur="<%=onChange%>"  name="assortFinalRtnForm" >
  <html:option value="">--select--</html:option>
  <%
  for(int j=0;j<prpVal.size();j++){
  String prpPrt = (String)prpVal.get(j);
    %>
  <html:option  value="<%=prpPrt%>"  ><%=prpPrt%></html:option>
  <%}%>
  </html:select>
  <%}else if(typ.equals("D")){
   if(isAjax)
  onChange = "updatePrp(this,'"+lprp+"' ,'T')";
  %>
  <html:text property="<%=fldName%>" readonly="true" styleId="<%=lprp%>" onfocus="<%=onChange%>"  name="assortFinalRtnForm" size="8" /><img src="<%=info.getReqUrl()%>/images/calender.png" onClick="setYears(2011, <%=info.getCurrentYear()%>);showCalender(this, '<%=lprp%>');"> 
  <%}else{
   if(isAjax)
   onChange = "updatePrp(this,'"+lprp+"' ,'T')";
  %>
  <html:text property="<%=fldName%>" styleId="<%=lprp%>" onblur="<%=onChange%>"   name="assortFinalRtnForm"/>
  <%}}else{%>
  &nbsp;&nbsp;&nbsp;&nbsp;
 <%}%>
  </td>
  
   <%if(isRepair.equals("Y")){
  String rpairVal = "value(RP_"+mstkIdn+"_"+lprp+")";
  String rpairValId="RP_"+mstkIdn+"_"+lprp;
  String onClick="";
  String sid="";
  if(isAjax)
  onClick = "RepairPrpUpdate(this,'"+lprp+"','"+typ+"');priCalcAssort('"+lprp+"')";
  %>
    <td>
   <%  if(typ.equals("C")){
  
  ArrayList prpVal = (ArrayList)prp.get(lprp+"V");
  
  %>
  <html:select property="<%=rpairVal%>" onblur="<%=onClick%>" styleId="<%=rpairValId%>" name="assortFinalRtnForm">
  <html:option value="">--select--</html:option>
  <%
  for(int j=0;j<prpVal.size();j++){
  String prpPrt = (String)prpVal.get(j);
  %>
  <html:option value="<%=prpPrt%>"><%=prpPrt%></html:option>
  <%}%>
  </html:select>
  <%}else if(typ.equals("D")){%>
  <html:text property="<%=rpairVal%>" readonly="true" styleId="<%=rpairValId%>" onfocus="<%=onClick%>" name="assortFinalRtnForm" size="8" /><img src="<%=info.getReqUrl()%>/images/calender.png" onClick="setYears(2011, <%=info.getCurrentYear()%>);showCalender(this, '<%=rpairValId%>');"> 
  <%}else{
  
  %>
  <html:text property="<%=rpairVal%>" onblur="<%=onClick%>" styleId="<%=rpairValId%>" name="assortFinalRtnForm"/>
  <%}%>
    
    
    </td>
        <%}%>  
  </tr>

 
  <%}%>
  </table></td></tr></table>
  </td></tr>
 
  </table>
  </div>
  
   </td></tr>
  </table>
  
   </html:form>
  <%}%>
  <script type="text/javascript">
 
 </script>
  </body>
  <%@include file="../calendar.jsp"%>
</html>