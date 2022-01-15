<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page errorPage="../error_page.jsp" %>
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import="java.util.ArrayList,java.util.HashMap,java.sql.ResultSet,ft.com.*, java.util.Date, java.util.ArrayList, ft.com.dao.*"%>
<jsp:useBean id="info" class="ft.com.InfoMgr" scope="session" />
<jsp:useBean id="util" class="ft.com.JspUtil" scope="page" />
<%@taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>
<%@taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>assortRtnUpd</title>
    <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/bse.css?v=<%=info.getJsVersion()%>"/>
      <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/style.css?v=<%=info.getJsVersion()%>"/>
      <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/bse.js?v=<%=info.getJsVersion()%> " > </script>
      <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/assortScript.js?v=<%=info.getJsVersion()%> " > </script>
       <script language="javaScript" type="text/javascript" src="<%=info.getReqUrl()%>/calendar/calendarFmt.js"></script>
   <link href="<%=info.getReqUrl()%>/calendar/calendar.css" rel="stylesheet" type="text/css">
  </head>
   <%
   String PopUpidn = util.nvl((String)request.getAttribute("PopUpidn"));
   String load="";
   if(!PopUpidn.equals(""))
   load="loadASSFNL('TR_"+PopUpidn+"','LNK_"+PopUpidn+"')";
   
        String logId=String.valueOf(info.getLogId());
        String onfocus="cook('"+logId+"')";
        %>
 <body onfocus="<%=onfocus%>" onload="<%=load%>">
  <%
   ArrayList stockList = (ArrayList)request.getAttribute("StockList");

   HashMap mprp = info.getMprp();
   HashMap prp = info.getPrp();
    if(stockList!=null && stockList.size() >0){
    HashMap stkUpd = null;
    String lprp =null;
    String flg = null;
  %>
   <html:form action="lab/labReturn.do?method=savePrpUpd" method="post" onsubmit="return validate_issue('CHK_' , 'count')" >
 
  <html:hidden property="value(issIdn)" name="labRtnForm" styleId="issIdn" />
    <html:hidden property="value(mstkIdn)" name="labRtnForm" styleId="mstkIdn" />
     <html:hidden property="value(lastpage)" name="labRtnForm" styleId="lastpage" />
     <html:hidden property="value(currentpage)" name="labRtnForm" styleId="currentpage" />
    <html:hidden property="value(prcId)" styleId="prcId" name="labRtnForm"  />
    <html:hidden property="value(url)" styleId="url" name="labRtnForm"  />
<table><tr align="center"><td><a href="#"  class="first" data-action="first" title="First" onclick="pagniation('first');"><img src="../images/firstico.png" /></a></td>
    <td><a href="#" class="previous" data-action="previous" title="Previous" onclick="pagniation('previous');"><img src="../images/previousico.png" /></a></td>
    <td><a href="#" class="next" data-action="next" title="Next" onclick="pagniation('next');"><img src="../images/nextico.png" /></a></td>
    <td><a href="#" class="last" data-action="last" title="Last" onclick="pagniation('last');"><img src="../images/lastico.png" /></a></td>
</tr></table> 
  <table cellpadding="0" cellspacing="0">
 <%String msg = (String)request.getAttribute("msg");
  if(msg!=null){
  %>
  <tr><td><span class="redLabel"> <%=msg%></span></td></tr>
  <%}%>
<tr><td>&nbsp;<span class="txtLink" > <bean:write property="value(vnm)" name="labRtnForm" /></span>&nbsp;
<html:submit property="value(issue)" styleClass="submit" value="Verify Changes" /></td></tr>
  
  <tr><td>
  
  <div class="memo">
  
  <table  width="700" cellpadding="0" cellspacing="2">
  
  <tr><td bgcolor="White" valign="top">
  <table border="0" class="Orange" cellspacing="1" cellpadding="1" >
          
           <tr><th class="Orangeth">PROPERTY</th><th class="Orangeth" colspan="2">Value</th></tr>
           
  <%
  int count=0;
  int srchSize = (stockList.size()/3)+1;
  for(int i=0;i<stockList.size() ;i++){
  stkUpd = (HashMap)stockList.get(i);
   lprp = (String)stkUpd.get("mprp");
   flg = (String)stkUpd.get("flg");
  
  
    if(count==srchSize){
             count=0;
             %></table></td><td valign="top">
            <table border="0" class="Orange" cellspacing="1" cellpadding="1" >
          
         <tr><th class="Orangeth">PROPERTY</th><th class="Orangeth" colspan="2">Value</th></tr>
          
           <% }
           count++;
           %>

 <tr><td>
 <%if(flg.equals("COMP")){%>
 <span class="redLabel">*</span>
 <%}%>
 <%=lprp%></td><td><%=stkUpd.get("issVal")%></td><td >
 <%if(!flg.equals("FTCH")){
  String typ = util.nvl((String)mprp.get(lprp+"T"));
  String  fldName = "value("+lprp+")";
  
  if(typ.equals("C")){
 
  ArrayList prpVal = (ArrayList)prp.get(lprp+"V");
  
  %>
  <html:select property="<%=fldName%>" styleId="<%=lprp%>" name="labRtnForm" >
  <html:option value="">--select--</html:option>
  <%
  for(int j=0;j<prpVal.size();j++){
  String prpPrt = (String)prpVal.get(j);
  %>
  <html:option value="<%=prpPrt%>"><%=prpPrt%></html:option>
  <%}%>
  </html:select>
  <%}else if(typ.equals("D")){%>
  <html:text property="<%=fldName%>" styleId="<%=lprp%>" readonly="true"  name="labRtnForm" size="10" /><img src="<%=info.getReqUrl()%>/images/calender.png" onClick="setYears(2011, <%=info.getCurrentYear()%>);showCalender(this, '<%=lprp%>');"> 
  <%}else{
 
  %>
  <html:text property="<%=fldName%>" styleId="<%=lprp%>"   name="labRtnForm"/>
  <%}}else{%>
  &nbsp;&nbsp;&nbsp;&nbsp;
 <%}%>
  </td></tr>

 
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