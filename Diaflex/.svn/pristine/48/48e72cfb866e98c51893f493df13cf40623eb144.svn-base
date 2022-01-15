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
    <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/bse.js?v=<%=info.getJsVersion()%> " > </script>
<link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/bse.css?v=<%=info.getJsVersion()%>"/>
<link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/general.css?v=<%=info.getJsVersion()%>"/>
<script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/ajax.js?v=<%=info.getJsVersion()%> " > </script>
  <script type="text/javascript" src="<%=info.getReqUrl()%>/PopScripts/popup.js?v=<%=info.getJsVersion()%> " > </script>
    <script src="<%=info.getReqUrl()%>/PopScripts/jquery-1.2.6.min.js" type="text/javascript"></script>
   <script language="javaScript" type="text/javascript" src="<%=info.getReqUrl()%>/calendar/calendar.js"></script>
   <link href="<%=info.getReqUrl()%>/calendar/calendar.css" rel="stylesheet" type="text/css" />

 
      <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/style.css?v=<%=info.getJsVersion()%>"/>

  <title>Week Wise Customer Buying</title>
 
  </head>
        <%String logId=String.valueOf(info.getLogId());
        String onfocus="cook('"+logId+"')";
        %>
 <body onfocus="<%=onfocus%>" onload="MM_preloadImages('images/faunalogo.png')" topmargin="0" leftmargin="0" rightmargin="0" bottommargin="0">
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
  <img src="../images/line.jpg" border="0" /></td><td valign="middle"> <span class="pgHed">Week Wise Customer Buying</span> </td>
</tr></table>
  </td>
  </tr>
<tr><td valign="top" class="hedPg">
<html:form action="/report/orclReportAction.do?method=fetchWeekBuyer" method="POST">
    <table class="grid1">
    <tr><th colspan="2">Employee/Year Search </th></tr>
    <tr><td>Employee</td><td>
             <html:select property="empLst" name="orclReportForm" style="height:75px; width:150px;" styleId="empLst"  multiple="true" size="5">
             <html:optionsCollection property="empList" name="orclReportForm"  value="byrIdn" label="byrNme" />
             </html:select></td>
    </tr>
     <tr><td>Year</td><td>
             <html:select property="yrLst" name="orclReportForm" style="height:75px; width:150px;" styleId="yrLst"  multiple="true" size="5">
             <html:optionsCollection property="yrList" name="orclReportForm"  value="nme" label="nme" />
             </html:select></td>
    </tr>
     <tr><td colspan="2" align="center"><html:submit property="value(submit)" value="Fetch" styleClass="submit"/></td></tr>
     </table>
</html:form>
</td>
</tr>
  <%
    String view= util.nvl((String)request.getAttribute("view")); 
    HashMap dataDtl= (HashMap)request.getAttribute("dataDtl");
    HashMap monthDtl= (HashMap)request.getAttribute("monthDtl");
    HashMap buyerDisplay= (HashMap)request.getAttribute("buyerDisplay");
    ArrayList yrList= (ArrayList)request.getAttribute("yrList");
    ArrayList monthList= (ArrayList)request.getAttribute("monthList");
    ArrayList empidnLst= (ArrayList)request.getAttribute("empidnLst");
    ArrayList nmeidnLst= new ArrayList();
    if(view.equals("Y")){
    if(dataDtl!=null && dataDtl.size()>0){
    for(int i=0;i<yrList.size();i++){
    String yr=(String)yrList.get(i);
    for(int j=0;j<empidnLst.size();j++){
    String empidn=(String)empidnLst.get(j);
    nmeidnLst= new ArrayList();
    nmeidnLst=(ArrayList)buyerDisplay.get(yr+"_"+empidn);
    String emp=(String)dataDtl.get(empidn);
    %>
    <tr><td valign="top" class="hedPg">Employee -<b><%=emp%></b>
    <table class="dataTable">
    <tr>
    <th>Buyer</th>
    <%for(int k=0;k<monthList.size();k++){
    String month=(String)monthList.get(k);
    String keymonth=yr+"_"+month;
    ArrayList Lst=(ArrayList)monthDtl.get(keymonth);
    int colspanhdr=Lst.size();
    %>
    <th colspan="<%=colspanhdr*3%>" align="center"><%=month%></th>
    <%}%>
    </tr>
    <tr>
    <td><b>Weeks</b></td>
    <%for(int k=0;k<monthList.size();k++){
    String month=(String)monthList.get(k);
    String keymonth=yr+"_"+month;
    ArrayList Lst=(ArrayList)monthDtl.get(keymonth);
    for(int l=0;l<Lst.size();l++){
    %>
    <td colspan="3" align="center"><b><%=Lst.get(l)%></b></td>
    <%}}%>
    </tr>
    
    <tr>
    <td></td>
     <%for(int k=0;k<monthList.size();k++){
    String month=(String)monthList.get(k);
    String keymonth=yr+"_"+month;
    ArrayList Lst=(ArrayList)monthDtl.get(keymonth);
    for(int l=0;l<Lst.size();l++){
    %>
    <td>QTY</td>
    <td>CTS</td>
    <td>VLU</td>
    <%}}%>
    </tr>
    <%for(int m=0;m<nmeidnLst.size();m++){
    String nmeidn=(String)nmeidnLst.get(m);
    String byr=(String)dataDtl.get(nmeidn);
    %>
    <tr>
    <td><%=byr%></td>
     <%for(int k=0;k<monthList.size();k++){
    String month=(String)monthList.get(k);
    String keymonth=yr+"_"+month;
    ArrayList Lst=(ArrayList)monthDtl.get(keymonth);
    for(int l=0;l<Lst.size();l++){
    String qty="",cts="",vlu="";
    String week=(String)Lst.get(l);
    String key=nmeidn+"_"+empidn+"_"+yr+"_"+month+"_"+week;
    HashMap data=new HashMap();
    data=(HashMap)dataDtl.get(key); 
    if(data!=null && data.size()>0){
      qty=util.nvl((String)data.get("QTY"));
      cts=util.nvl((String)data.get("CTS"));
      vlu=util.nvl((String)data.get("VLU"));
    }
    %>
    <td align="right"><%=qty%></td>
    <td align="right"><%=cts%></td>
    <td align="right"><%=vlu%></td>
    <%}}%>
    </tr>
    <%}%>
    </table>
    </td>
    </tr>
 
<%}}}else{%>
<tr><td valign="top" class="hedPg">
Sorry No Result Found</td></tr>
<%}}%>
  <tr><td>
<jsp:include page="/footer.jsp" />
</td></tr>
  </table>  
   <%@include file="/calendar.jsp"%>
  </body>
</html>