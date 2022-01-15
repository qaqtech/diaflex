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
      <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/assortScript.js?v=<%=info.getJsVersion()%> " > </script>
    <script language="javaScript" type="text/javascript" src="<%=info.getReqUrl()%>/calendar/calendarFmt.js"></script>
   <link href="<%=info.getReqUrl()%>/calendar/calendar.css" rel="stylesheet" type="text/css">
 <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/bse.js?v=<%=info.getJsVersion()%> " > </script>
 </head>
        <%String logId=String.valueOf(info.getLogId());
        String onfocus="cook('"+logId+"')";
        %>
 <body onfocus="<%=onfocus%>">
  <%
   ArrayList stockList = (ArrayList)request.getAttribute("StockList");
  String mstkIdn = (String)request.getAttribute("mstkIdn");
  HashMap stockPrpUpdList = (HashMap)request.getAttribute("stockPrpUpdList");
   HashMap mprp = info.getMprp();
   HashMap prp = info.getPrp();
    if(stockList!=null && stockList.size() >0){
    HashMap stkUpd = null;
    String lprp =null;
    String flg = null;
    String lprpVal = null;
    String lprpIssuVal = null;
    HashMap dbInfoSys = info.getDmbsInfoLst();
    String col = util.nvl((String)dbInfoSys.get("COL"));
  String clr = util.nvl((String)dbInfoSys.get("CLR"));
  String cut = util.nvl((String)dbInfoSys.get("CUT"));
 
  %>
   <html:form action="lab/labComparisonRtn.do?method=savePrpUpd" method="post" >
 
  <html:hidden property="value(issIdn)" name="labComparisonRtnForm" styleId="issIdn" />
    <html:hidden property="value(mstkIdn)" name="labComparisonRtnForm" styleId="mstkIdn" />
     <html:hidden property="value(prcId)" name="labComparisonRtnForm"  />
  <table cellpadding="0" cellspacing="0">
 <%String msg = (String)request.getAttribute("msg");
  if(msg!=null){
  %>
  <tr><td><span class="redLabel"> <%=msg%></span></td></tr>
  <%}%>
 <tr><td> <html:submit property="value(issue)" styleClass="submit" value="Verify Changes" /></td></tr>
  
  <tr><td>
  
  <div class="memo">
  
 <table  cellpadding="0" cellspacing="2">
  
  <tr><td bgcolor="White">
  <table border="0" width="250" class="Orange" cellspacing="1" cellpadding="1" >
          
           <tr><th class="Orangeth">PROPERTY</th><th class="Orangeth" colspan="2">Value</th></tr>
           
  <%
  int count=0;
  int srchSize = (stockList.size()/3)+1;
  for(int i=0;i<stockList.size() ;i++){
  stkUpd = (HashMap)stockList.get(i);
   lprp = (String)stkUpd.get("mprp");
   flg = (String)stkUpd.get("flg");
    lprpVal = util.nvl((String)stockPrpUpdList.get(mstkIdn+"_"+lprp));
   if(count==srchSize){
             count=0;
   %></table></td><td valign="top">
  <table border="0"  width="250"  class="Orange" cellspacing="1" cellpadding="1" >
     <tr><th class="Orangeth">PROPERTY</th><th class="Orangeth" colspan="2">Value</th></tr>
     <% }
           count++;
           %>

 <tr> <td>
 <%if(flg.equals("COMP")){%>
 <span class="redLabel">*</span>
 <%}%>
 <%=lprp%></td><td><%=stkUpd.get("issVal")%></td><td >
<%if(!flg.equals("FTCH")){
  String typ = util.nvl((String)mprp.get(lprp+"T"));
  String  fldName = "value("+mstkIdn+"_"+lprp+")";
 
  if(typ.equals("C")){
  ArrayList prpVal = (ArrayList)prp.get(lprp+"V");
  if( (lprp.equalsIgnoreCase(col) || lprp.equals(clr) || lprp.equals(cut) )){
   lprpIssuVal = util.nvl((String)stkUpd.get("issVal"));
    lprpIssuVal = lprpIssuVal.replace('+',' ');
     lprpIssuVal = lprpIssuVal.replace('+',' ');
   // if(lprpVal.indexOf("-") > -1)
     lprpIssuVal = lprpIssuVal.replace('-', ' ');
     lprpIssuVal = lprpIssuVal.replace('-', ' ');
     lprpIssuVal = lprpIssuVal.trim();
  }
 %>
  <html:select property="<%=fldName%>" styleId="<%=lprp%>" name="labComparisonRtnForm" >
  <html:option value="">--select--</html:option>
  <%
  for(int j=0;j<prpVal.size();j++){
  String prpPrt = (String)prpVal.get(j);
   boolean isDispaly = false;
  if( (lprp.equalsIgnoreCase(col) || lprp.equals(clr) || lprp.equals(cut) )){
      String subStr = prpPrt;
     
//      subStr = subStr.replaceAll("+", "");
//      subStr = subStr.replaceAll("-", "");
//      
      
     // if(lprpVal.indexOf("+") > -1)
     subStr = subStr.replace('+',' ');
      subStr = subStr.replace('+',' ');
   // if(lprpVal.indexOf("-") > -1)
     subStr = subStr.replace('-', ' ');
     subStr = subStr.replace('-', ' ');
     subStr = subStr.trim();
     if(lprpIssuVal.equals(subStr))
        isDispaly = true;
  }else{
  isDispaly = true;
  }
  if(isDispaly){
    %>
  <html:option  value="<%=prpPrt%>"  ><%=prpPrt%></html:option>
  <%}}%>
  </html:select>
  <%}else if(typ.equals("D")){%>
  <html:text property="<%=fldName%>" styleId="<%=lprp%>" readonly="true"  name="labComparisonRtnForm" size="10" /><img src="<%=info.getReqUrl()%>/images/calender.png" onClick="setYears(2011, <%=info.getCurrentYear()%>);showCalender(this, '<%=lprp%>');"> 
  <%}else{%>
  <html:text property="<%=fldName%>" styleId="<%=lprp%>"   name="labComparisonRtnForm"/>
  <%}}else{%>
  &nbsp;&nbsp;&nbsp;&nbsp;
 <%}%>
  </td></tr>

 
  <%}%>
  </table></td></tr></table>
  
  </div>
  
   </td></tr>
  </table>
  
   </html:form>
  <%}%>
 
  </body>
    <%@include file="../calendar.jsp"%>
</html>