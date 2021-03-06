<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page errorPage="../error_page.jsp" %>
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import="java.util.ArrayList,java.util.ArrayList,java.util.HashMap, java.sql.*, java.util.Enumeration, java.util.logging.Level"%>
<jsp:useBean id="info" class="ft.com.InfoMgr" scope="session" />
<jsp:useBean id="util" class="ft.com.JspUtil" scope="page" />
<%@taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
 
<%
String status =util.nvl( (String)request.getAttribute("MSG"));
String sumQty =util.nvl( (String)request.getAttribute("sumQty"),"0");
String sumCts =util.nvl( (String)request.getAttribute("sumCts"),"0.0");
HashMap allPageDtl= (info.getPageDetails() == null)?new HashMap():(HashMap)info.getPageDetails();
 HashMap pageDtl=(HashMap)allPageDtl.get("TRNASACTION_RPT");
%>
<html>
  <head>
 <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
  
  <title>Transaction Reports</title>
  <script type="text/javascript" src="<%=info.getReqUrl()%>/jquery/jquery.min.js?v=<%=info.getJsVersion()%> " > </script>
  <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/bse.js?v=<%=info.getJsVersion()%> " > </script>
  <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/bse.css?v=<%=info.getJsVersion()%>"/>
  <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/ajax.js?v=<%=info.getJsVersion()%> " > </script>
  <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/style.css?v=<%=info.getJsVersion()%>"/>
  <script language="javaScript" type="text/javascript" src="<%=info.getReqUrl()%>/calendar/calendar.js"></script>
   <link href="<%=info.getReqUrl()%>/calendar/calendar.css" rel="stylesheet" type="text/css" />
  </head>
  
 <body onfocus="" onload="MM_preloadImages('images/faunalogo.png')" topmargin="0" leftmargin="0" rightmargin="0" bottommargin="0">
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
  <img src="../images/line.jpg" border="0" /></td><td valign="middle"> <span class="pgHed">
    Transaction Report
   </span> </td>
</tr></table>
  </td>
  </tr>
  
  <tr>
  <td valign="top" class="hedPg">
  <html:form action="report/transactionReport.do?method=fetch" method="post">

  <table>
 
 <tr>
   <td> Transaction Type : </td>
   <td>
   <html:select property="value(transRType)" styleId="transRType" name="transactionReportForm"   >
             <html:option value="IS" >Memo</html:option>
              <html:option value="AP" >Approve</html:option>
                <html:option value="CS" >Consignment</html:option>
               <html:option value="SL" >Sale</html:option>
                <html:option value="DLV" >Delivery</html:option>
               
   </html:select>
   </td></tr>
   <tr><td>Party Name : </td>
   <td nowrap="nowrap">
    <input type="text" id="nme" name="nme" autocomplete="off" class="sugBox"
      onKeyUp="doCompletion('nme', 'nmePop', '../ajaxAction.do?1=1&UsrTyp=VENDOR,BUYER', event)" 
      onKeyDown="setDown('nmePop', 'nmeID', 'nme',event)"/>
 <img src="../images/addrow.jpg" width="17" height="17" title="Click To get Names" onclick="doCompletionMobile('nme', 'nmePop', '../ajaxAction.do?1=1&UsrTyp=VENDOR,BUYER')">
 <input type="hidden" name="nmeID" id="nmeID" value="">
  <div style="position: absolute;">
      <select id="nmePop" name="nmePop"
        style="display:none;300px;" 
        class="sugBoxDiv"
        onKeyDown="setDown('nmePop', 'nmeID', 'nme',event)" 
        onDblClick="setVal('nmePop', 'nmeID', 'nme', event);hideObj('nmePop')" 
        onClick="setVal('nmePop', 'nmeID', 'nme', event);" 
        size="10">
      </select>
</div>
  </td></tr>
  <tr>
  <td>Transaction Date From : </td><td>
    <html:text property="value(dtefr)" styleClass="txtStyle"  styleId="dtefr"  size="12"  /> <a href="#" style="margin-top:5px;"  onClick="setYears(1900, '<%=info.getCurrentYear()%>' );showCalender(this, 'dtefr');">
                <img src="<%=info.getReqUrl()%>/images/calender.png"></a></td>
                <td>Transaction Date To : </td><td>
    <html:text property="value(dteto)" styleClass="txtStyle"  styleId="dteto"  size="12"  /> <a href="#"  onClick="setYears(1900, '<%=info.getCurrentYear()%>' );showCalender(this, 'dteto');">
                <img src="<%=info.getReqUrl()%>/images/calender.png"></a></td>
  </tr>
   <tr>
   <td> Order By : </td>
   <td>
   <html:select property="value(transOrder)" styleId="transRType" name="transactionReportForm"   >
    <html:option value="IDN" >Transaction Id</html:option>
             <html:option value="BYR" >Buyer</html:option>
              <html:option value="EMP" >Employee</html:option>
              
               <html:option value="DTE" >Date</html:option>
   </html:select>
   </td>
   </tr>
<tr><td> <html:submit property="value(submit)" styleClass="submit" value="Submit" /> 

</td> 
</tr>
  </table>
  </html:form>
  </td>
  </tr>
   <%
  String view = util.nvl((String)request.getAttribute("view"));
  if(!view.equals("")){%>
  <tr><td valign="top" class="hedPg">Grand Total = <b>Qty: <%=sumQty%> Cts: <%=sumCts%></b></td></tr>
  <tr>
  <td valign="top" class="hedPg" nowrap="nowrap">
 <%
   ArrayList pageList=null;
  if(pageDtl!=null && pageDtl.size()>0)
 pageList=(ArrayList)pageDtl.get("LIST");
 if(pageList!=null && pageList.size()>0){%>
 <display:table name="sessionScope.transactionReportForm.transDtlList" class="displayTable" requestURI="" export="true"  sort="list" decorator="ft.com.Decortor" >

  <logic:equal property="value(transOrder)"  name="transactionReportForm"  value="BYR" >
    <display:column property="buyer"  title="Buyer" group="1"  />
     <display:column property="IDN"  title="Trans Id"    />
    <display:column property="emp"  title="Employee"   />
    <display:column property="iss_dte"  title="Issue Date"   />
  
    
   </logic:equal>
    <logic:equal property="value(transOrder)"  name="transactionReportForm"  value="IDN" >
    <display:column property="IDN"  title="Trans Id"  group="1" />
    <display:column property="buyer"  title="Buyer"   />
    
    <display:column property="emp" style="font-weight: bold;" title="Employee"   />
    <display:column property="iss_dte" style="font-weight: bold;" title="Issue Date" />
   </logic:equal>
   
   
    <logic:equal property="value(transOrder)"  name="transactionReportForm"  value="EMP" >
    <display:column property="emp"  title="Employee" group="1" />
      <display:column property="IDN"  title="Trans Id" />
    <display:column property="buyer"  title="Buyer"   />
    
       <display:column property="iss_dte"  title="Issue Date"   />
 

    
   </logic:equal>
   <logic:equal property="value(transOrder)"  name="transactionReportForm"  value="DTE" >
   <display:column property="iss_dte"  title="Issue Date" group="1"  />
   <display:column property="IDN"  title="Trans Id" />
    <display:column property="emp"  title="Employee"/>
    <display:column property="buyer"  title="Buyer" />
   
   </logic:equal>
   
   <% for(int j=0;j<pageList.size();j++){ 
      HashMap pageListDtl = (HashMap)pageList.get(j);
      String fld_nme = (String)pageListDtl.get("fld_nme");
      String fld_ttl= (String)pageListDtl.get("fld_ttl");
      %>
       <display:column property="<%=fld_nme%>"  title="<%=fld_ttl%>"  />
    <% }%>
 <display:setProperty name="transactionReport.excel.filename" value="Details.xls"/>
  <display:setProperty name="transactionReport.xls" value="true" />
    <display:setProperty name="transactionReport.csv" value="false" />
     <display:setProperty name="transactionReport.xml" value="false" />
     </display:table>
 
 
 
 <%}else{%>
 <display:table name="sessionScope.transactionReportForm.transDtlList" class="displayTable" requestURI="" export="true"  sort="list" decorator="ft.com.Decortor" >

  <logic:equal property="value(transOrder)"  name="transactionReportForm"  value="BYR" >
    <display:column property="buyer"  title="Buyer" group="1"  />
     <display:column property="IDN"  title="Trans Id"    />
    <display:column property="emp"  title="Employee"   />
    <display:column property="iss_dte"  title="Issue Date"   />
  
    
   </logic:equal>
    <logic:equal property="value(transOrder)"  name="transactionReportForm"  value="IDN" >
    <display:column property="IDN"  title="Trans Id"  group="1" />
    <display:column property="buyer"  title="Buyer"   />
    
    <display:column property="emp" style="font-weight: bold;" title="Employee"   />
    <display:column property="iss_dte" style="font-weight: bold;" title="Issue Date" />
   </logic:equal>
   
   
    <logic:equal property="value(transOrder)"  name="transactionReportForm"  value="EMP" >
    <display:column property="emp"  title="Employee" group="1" />
      <display:column property="IDN"  title="Trans Id" />
    <display:column property="buyer"  title="Buyer"   />
    
       <display:column property="iss_dte"  title="Issue Date"   />
 

    
   </logic:equal>
   <logic:equal property="value(transOrder)"  name="transactionReportForm"  value="DTE" >
   <display:column property="iss_dte"  title="Issue Date" group="1"  />
   <display:column property="IDN"  title="Trans Id" />
    <display:column property="emp"  title="Employee"/>
    <display:column property="buyer"  title="Buyer" />
   
   
    
   </logic:equal>
 
 <display:column property="typ"  title="Type"  />
    
    <display:column property="og_qty" style="text-align:right"  title="QTY"  />
    <display:column property="og_cts"  style="text-align:right" title="Cts"  />
    <display:column property="og_vlu" style="text-align:right" title="Vlu"  />
    
   
    <display:column property="rtl_qty" style="text-align:right" title="RT Qty"  />
    <display:column property="rtl_cts" style="text-align:right" title="RT Cts"  />
    
    <display:column property="cm_qty" style="text-align:right"  title="CFM QTY"  />
    <display:column property="cm_cts" style="text-align:right" title="CFM Cts"  />
     <display:column property="cm_vlu" style="text-align:right" title="CFM Vlu"  />
    <display:column property="cm_fnl_vlu" style="text-align:right" title="CFM Fnl_Vlu"  />
    
    <display:column property="diff" style="text-align:right" title="Diff %"  />
    <display:column property="diffrap" style="text-align:right" title="Diff Rap %"  />
   <display:setProperty name="transactionReport.excel.filename" value="Details.xls"/>
  <display:setProperty name="transactionReport.xls" value="true" />
    <display:setProperty name="transactionReport.csv" value="false" />
     <display:setProperty name="transactionReport.xml" value="false" />
     </display:table>
 
<%}}%>
 

  </td>
  </tr>
    </table>
   <%@include file="../calendar.jsp"%>
  </body>
</html>

  
  