<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page errorPage="../error_page.jsp" %>
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.ResultSet, java.util.ArrayList,java.util.Collections,java.util.ArrayList,java.util.HashMap,java.text.SimpleDateFormat, java.util.Calendar"%>
	<jsp:useBean id="info" class="ft.com.InfoMgr" scope="session" />
	<jsp:useBean id="util" class="ft.com.JspUtil" scope="page" />

<%@taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>
<%@taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<html> 
   <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/bse.css?v=<%=info.getJsVersion()%>"/>
    <script language="javaScript" type="text/javascript" src="<%=info.getReqUrl()%>/calendar/calendar.js"></script>
   <link href="<%=info.getReqUrl()%>/calendar/calendar.css" rel="stylesheet" type="text/css" />
   <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/bse.js?v=<%=info.getJsVersion()%> " > </script>
     <script language="javaScript" type="text/javascript" src="<%=info.getReqUrl()%>/scripts/ajax.js?v=<%=info.getJsVersion()%>"></script>
      <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/style.css?v=<%=info.getJsVersion()%>"/>
      <script type="text/javascript" src="<%=info.getReqUrl()%>/PopScripts/popup.js?v=<%=info.getJsVersion()%> " > </script>
   <script src="<%=info.getReqUrl()%>/PopScripts/jquery-1.2.6.min.js" type="text/javascript"></script>
    <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/general.css?v=<%=info.getJsVersion()%>"/>
        <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/assortScript.js?v=<%=info.getJsVersion()%> " > </script>

 
      <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/style.css?v=<%=info.getJsVersion()%>"/>

  <title>Feedback report</title>
 
  </head>
  
  <%
  HashMap prp = info.getPrp();
  ArrayList deptPrpDtl = (ArrayList)prp.get("DEPT"+"V");
      HashMap allPageDtl= (info.getPageDetails() == null)?new HashMap():(HashMap)info.getPageDetails();
    HashMap pageDtl=(HashMap)allPageDtl.get("CONTACT_SRCH");
    ArrayList pageList=new ArrayList();
    HashMap pageDtlMap=new HashMap();
    String fld_nme="",fld_ttl="",val_cond="",dflt_val="",fld_typ="",form_nme="",flg1="",lov_qry="";
  String logId=String.valueOf(info.getLogId());
        String onfocus="cook('"+logId+"')";
        %>
 <body onfocus="<%=onfocus%>" onload="MM_preloadImages('images/faunalogo.png')" topmargin="0" leftmargin="0" rightmargin="0" bottommargin="0">
  <input type="hidden" id="reqUrl" name="reqUrl" value="<%=info.getReqUrl()%>"/>
 <div id="backgroundPopup"></div>
<div id="popupDashPOP" >
<table class="popUpTb">
 <tr>
 <td height="5%" id="close">&nbsp;&nbsp;&nbsp;<html:button property="value(close)" styleClass="submit"  onclick="MKEmptyPopup('SC');disablePopupDashPOP();" value="Close"  /> </td>
 <td height="5%" id="closereload" style="display:none">&nbsp;&nbsp;&nbsp;<html:button property="value(close)" styleClass="submit"  onclick="MKEmptyPopup('SC'); disablePopupASSFNL(); reportUploadclose('CNTFEEDBACK');" value="Close"  /></td>
 </tr>
<tr><td valign="top" height="95%">
<iframe name="SC" class="frameStyle" width="500px" height="500px"   align="middle" frameborder="0">
<jsp:include page="/emptyPg.jsp" />
</iframe></td></tr>
</table>
</div>
  <table cellpadding="" cellspacing="" class="mainbg">
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
  <img src="../images/line.jpg" border="0" /></td><td valign="middle"> <span class="pgHed">Feedback report</span> </td>
</tr></table>
  </td>
  </tr>
<tr><td valign="top" class="hedPg">
<html:form action="/report/orclRptAction.do?method=fetchfeedbackRpt"  method="POST">
 <table class="grid1">
            <tr><th colspan="2">Search </th></tr>
    <tr><td>Employee</td><td>
             <html:select property="empLst" name="orclReportForm" style="height:200px; width:180px;" styleId="empLst"  multiple="true" size="15">
             <html:optionsCollection property="empList" name="orclReportForm"  value="emp_idn" label="emp_nme" />
             </html:select></td>
    </tr>
           <tr>
           <td>Period</td>
           <td>
           <html:select property="value(salefrmon)"  styleId="salefrmon" name="orclReportForm" >
           <html:optionsCollection property="monthList" name="orclReportForm"  value="nme" label="dsc" />
           </html:select>&nbsp;
           <html:select property="value(salefryr)"  styleId="salefryr" name="orclReportForm" >
           <html:optionsCollection property="yrList" name="orclReportForm"  value="nme" label="nme" />
           </html:select>&nbsp;
           </td>
           </tr>
      <tr><td colspan="2" align="center">
      <html:submit property="value(srch)" value="Details Report" styleClass="submit" />&nbsp;
      <html:submit property="value(summary)" value="Summary Report" styleClass="submit" />
      </td> </tr>
 </table>
</html:form>
</td>
</tr>
<tr   id="DTL_1">
</tr>
  <%
    ArrayList bryLstFeedbk=new ArrayList();
    String view= util.nvl((String)request.getAttribute("view"));
    String rpt= util.nvl((String)request.getAttribute("rpt"));
    ArrayList empLstFeedbk= (ArrayList)request.getAttribute("empLstFeedbk");
    HashMap dataDtlFeedbk= (HashMap)request.getAttribute("dataDtlFeedbk");
    if(view.equals("Y")){
    if(rpt.equals("DETAIL")){
    if(empLstFeedbk!=null && empLstFeedbk.size()>0){
    for(int i=0;i<empLstFeedbk.size();i++){
    String emp=util.nvl((String)empLstFeedbk.get(i));
    String emp_idn=util.nvl((String)dataDtlFeedbk.get(emp));
    bryLstFeedbk=new ArrayList();
    bryLstFeedbk=(ArrayList)dataDtlFeedbk.get(emp+"_BYR");
  %>
  <tr><td valign="top" class="hedPg">
  <table class="grid1">
  <tr><th colspan="32"><%=emp%></th></tr>
  <tr>
  <th>Buyer</th>
  <%for(int h=1;h<=31;h++){%>
  <th><%=h%></th>
  <%}%>
  </tr>
  <%for(int b=0;b<bryLstFeedbk.size();b++){
  String byr=util.nvl((String)bryLstFeedbk.get(b));
  String byr_idn=util.nvl((String)dataDtlFeedbk.get(byr));
  String id="BYR_"+byr_idn;
  %>
  <tr><th><%=byr%>&nbsp;
    <img src="../images/plus.png" border="0" onclick="clientFeedbackrpt('<%=emp_idn%>','<%=byr_idn%>')" id="<%=id%>"></img>
  </th>
  <%
  for(int h=1;h<=31;h++){
  String dy="";
  if(h<10)
  dy="0"+String.valueOf(h);
  else
  dy=String.valueOf(h);
  %>
  <td><%=util.nvl((String)dataDtlFeedbk.get(emp+"_"+byr+"_"+dy))%>
  </td>
  <%}%>
  </tr>
  <%}%>
  </table>
  </td>
  </tr>
<%
}}else{%>
<tr><td valign="top" class="hedPg">
Sorry No Result Found</td></tr>
<%}}else{
if(empLstFeedbk!=null && empLstFeedbk.size()>0){%>
<tr><td valign="top" class="hedPg">
  <table class="grid1">
  <tr><th>Employee</th>
          <%
            pageList=(ArrayList)pageDtl.get("FEEDMODE");
             if(pageList!=null && pageList.size() >0){%>
             <%
            for(int i=0;i<pageList.size();i++){
                 pageDtlMap=(HashMap)pageList.get(i);
                 fld_nme=(String)pageDtlMap.get("fld_nme");
                 fld_ttl=(String)pageDtlMap.get("fld_ttl");
                 %>
                <th><%=fld_ttl%></th>
             <%}%>
            <%}%>  
  </tr>
  <%for(int i=0;i<empLstFeedbk.size();i++){
  String emp=util.nvl((String)empLstFeedbk.get(i));%>
  <tr>
  <td><%=emp%></td>
  <%
            pageList=(ArrayList)pageDtl.get("FEEDMODE");
             if(pageList!=null && pageList.size() >0){%>
             <%
            for(int p=0;p<pageList.size();p++){
                 pageDtlMap=(HashMap)pageList.get(p);
                 fld_nme=(String)pageDtlMap.get("fld_nme");
                 fld_ttl=(String)pageDtlMap.get("fld_ttl");
                 %>
                  <td align="right"><%=util.nvl((String)dataDtlFeedbk.get(emp+"_"+fld_nme))%></td>
             <%}%>
            <%}%> 

  </tr>
  <%}%>
  </table>
  </td>
  </tr>
<%}else{%>
<tr><td valign="top" class="hedPg">
Sorry No Result Found</td></tr>
<%}}}%>
  <tr><td>
<jsp:include page="/footer.jsp" />
</td></tr>
  </table>  
  </body>
  <%@include file="../calendar.jsp"%>
</html>