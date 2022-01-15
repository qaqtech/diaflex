<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page errorPage="../error_page.jsp" %>
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import="java.util.ArrayList,java.util.ArrayList,java.util.HashMap, java.sql.*, java.util.Enumeration, java.util.logging.Level"%>
<jsp:useBean id="info" class="ft.com.InfoMgr" scope="session" />
<jsp:useBean id="util" class="ft.com.JspUtil" scope="page" />
<%@taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>
 
<html> 
   <head>
        <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/bse.js?v=<%=info.getJsVersion()%> " > </script>
<link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/bse.css?v=<%=info.getJsVersion()%>"/>
<link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/general.css?v=<%=info.getJsVersion()%>"/>
<script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/ajax.js?v=<%=info.getJsVersion()%> " > </script>
  <script type="text/javascript" src="<%=info.getReqUrl()%>/PopScripts/popup.js?v=<%=info.getJsVersion()%> " > </script>
    <script src="<%=info.getReqUrl()%>/PopScripts/jquery-1.2.6.min.js" type="text/javascript"></script>
   <script language="javaScript" type="text/javascript" src="<%=info.getReqUrl()%>/calendar/calendar.js"></script>
   <link href="<%=info.getReqUrl()%>/calendar/calendar.css" rel="stylesheet" type="text/css" />

 
      <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/style.css?v=<%=info.getJsVersion()%>"/>
  <title>Single Sale Report</title>
 
  </head>
  
  <%
    ArrayList deptList=new ArrayList();
    HashMap prp = info.getPrp();
    deptList = (ArrayList)prp.get("DEPTV");
  %>
        <%String logId=String.valueOf(info.getLogId());
        String onfocus="cook('"+logId+"')";
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
  <img src="../images/line.jpg" border="0" /></td><td valign="middle"> <span class="pgHed">Single Sale Report</span> </td>
</tr></table>
  </td>
  </tr>
<tr><td valign="top" class="hedPg">
<html:form action="/report/orclRptAction.do?method=singlrsalerpt"  method="POST">
  <table>
<tr>
<td>Buyer</td>
<td nowrap="nowrap">
<%
    String sbUrl ="ajaxRptAction.do?method=buyerSugg";
    %>
    <input type="text" id="nme" name="nme" autocomplete="off" class="sugBox"
      onKeyUp="doCompletion('nme', 'nmePop', 'ajaxRptAction.do?method=buyerSugg', event)" 
      onKeyDown="setDown('nmePop', 'nmeID', 'nme',event)"/>
 <img src="../images/addrow.jpg" width="17" height="17" title="Click To get Names" onclick="doCompletionMobile('nme', 'nmePop', 'ajaxRptAction.do?method=buyerSugg')">
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
  </td>
<td>Date From : </td><td>
    <html:text property="value(dtefr)" styleClass="txtStyle"  styleId="dtefr"  size="12"  /> <a href="#" style="margin-top:5px;"  onClick="setYears(1900, '<%=info.getCurrentYear()%>' );showCalender(this, 'dtefr');">
                <img src="<%=info.getReqUrl()%>/images/calender.png"></a></td>
                <td>Date To : </td><td>
    <html:text property="value(dteto)" styleClass="txtStyle"  styleId="dteto"  size="12"  /> <a href="#"  onClick="setYears(1900, '<%=info.getCurrentYear()%>' );showCalender(this, 'dteto');">
                <img src="<%=info.getReqUrl()%>/images/calender.png"></a></td>
   <td>Dept :</td>
    <td>
    <html:select property="value(dept)" styleId="dept"  >
    <html:option value="" >--Select--</html:option> 
    <%
    if(deptList!=null){
    for(int i=0;i<deptList.size();i++){
    String deptVal=(String)deptList.get(i);%>
    <html:option value="<%=deptVal%>" ><%=deptVal%></html:option> 
    <%}}%>   
    </html:select>
    </td>
    <td><html:submit property="value(srch)" value="fetch" styleClass="submit" /></td>
   </tr>
   </table>
</html:form>
</td>
</tr>
  <%String view= util.nvl((String)request.getAttribute("View"));
    ArrayList prpDspBlocked = info.getPageblockList();
    if(!view.equals("")){
    %>
    <%
  ArrayList rolenmLst=(ArrayList)info.getRoleLst();
  String usrFlg=util.nvl((String)info.getUsrFlg());
  if(usrFlg.equals("SYS") || rolenmLst.contains("ADMIN")){
  %><tr><td valign="top" class="hedPg">
  <img src="../images/edit.jpg" title="Edit Properties Module" onclick="newWindow('<%=info.getReqUrl()%>/dynamicProperty.do?method=load&mdl=MIXSALE_VW&sname=mixsaleprpLst&par=A')" border="0" width="15px" height="15px"/>
  </td></tr>
  <%}%>
  <tr><td valign="top" class="hedPg" nowrap="nowrap"  >
  <display:table name="sessionScope.orclReportForm.pktList" class="displayTable" requestURI="" export="true"  sort="list" >
   <display:column property="Sr" title="Sr" />
    <display:column property="VchYN" title="VchYN"/>
    <display:column property="InvNo" title="InvNo"/>
    
    <display:column property="TransDate" title="TransDate"/>
     
    <display:column property="PartyCode"  title="PartyCode"/>
    <display:column property="nme"  title="C/O Rem"  group="1"/>
    <display:column property="PrimaryContact"  title="PrimaryContact"/>
    <display:column property="Transaction"  title="Transaction"  />
     <display:column property="RndNo"  title="RndNo"  />
     <display:column property="DueDays"  title="DueDays"  />
     <display:column property="DueDate"  title="DueDate"  />
     <display:column property="PartyCnvRt"  title="PartyCnvRt"  />
    <display:column property="StkCnvRt"  title="StkCnvRt"  />
    <display:column property="termscode"  title="TermsCode"  />
    <display:column property="TermRem"  title="TermRem"  />
    <display:column property="StkRiskFact"  title="StkRiskFact"  />
    <display:column property="StkTermsPer"  title="StkTermsPer"  />
    <display:column property="CordCode"  title="CordCode"  />
    <display:column property="Cord"  title="Cord%"  />
    <display:column property="CordCnvRt"  title="CordCnvRt"  />
    <display:column property="Com1"  title="Com1"  />
    <display:column property="Com2"  title="Com2"  />
    <display:column property="Com3"  title="Com3"  />
    <display:column property="Com4"  title="Com4"  />
    <display:column property="EntSrNo"  title="EntSrNo"  />
    <display:column property="size"  title="Size"  />
    <display:column property="Quality"  title="Quality"  />
    <display:column property="Shape"  title="Shape"  />
    <display:column property="Dept"  title="Dept"  />
    <display:column property="cts"  title="Carat"  />
     <display:column property="UsRate"  title="Us$ Rate"  />
    <display:column property="FixRate"  title="FixRate$"  />
    <display:column property="FixCnvRt"  title="FixCnvRt"  />
    <display:column property="RemOut"  title="Rem. Out"  />
    <display:column property="RemNoChange"  title="Rem.NoChange"  />
    <display:column property="MFG_PRI"  title="Rem. SRTAvg"  />
    <display:column property="Expthr"  title="Exp through"  />
    <display:column property="ExpPer"  title="Exp Per"  />
    <display:column property="emp"  title="Handle"  />
    <display:column property="blind"  title="Blind"  />
    <display:column property="net"  title="Net"  />
    <display:column property="adat"  title="ADAT"  />
  <display:setProperty name="export.excel.filename" value="Details.xls"/>
  <display:setProperty name="export.xls" value="true" />
    <display:setProperty name="export.csv" value="false" />
     <display:setProperty name="export.xml" value="false" />
  </display:table>
  </td></tr>
  <%}else{%>
<tr><td valign="top" class="hedPg">
Sorry No Result Found</td></tr>
<%}%>
  <tr><td>
<jsp:include page="/footer.jsp" />
</td></tr>
  </table>  
  </body>
    <%@include file="../calendar.jsp"%>
</html>