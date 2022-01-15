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

  <title>Daily Mix Transfer Report</title>
 
  </head>
  
  <%
        String logId=String.valueOf(info.getLogId());
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
  <img src="../images/line.jpg" border="0" /></td><td valign="middle"> <span class="pgHed">Daily Mix Transfer Report</span> </td>
</tr></table>
  </td>
  </tr>
<tr><td valign="top" class="hedPg">
<html:form action="/report/orclRptAction.do?method=fetchdailymixtrf"  method="POST">
  <table  class="grid1">
  <tr><th colspan="2">Generic Search </th></tr>
  <tr>
   <td colspan="2">
 <jsp:include page="/genericSrch.jsp"/>
   </td></tr>
  <tr><td>Transfer Date  </td>  
  <td><html:text property="value(dtefrtrf)" styleId="dtefrtrf" name="orclReportForm" size="8" /><img src="<%=info.getReqUrl()%>/images/calender.png" onClick="setYears(2011, <%=info.getCurrentYear()%>);showCalender(this, 'dtefrtrf');"> 
   To&nbsp; <html:text property="value(dtetotrf)" styleId="dtetotrf" name="orclReportForm"  size="8"/> <img src="<%=info.getReqUrl()%>/images/calender.png" onClick="setYears(2011, <%=info.getCurrentYear()%>);showCalender(this, 'dtetotrf');"></td>
   </tr>
  <tr>
   <td colspan="2"  align="center">
   <html:submit property="value(srch)" value="Fetch" styleClass="submit" />
   </td>
   </tr>
   </table>
</html:form>
</td>
</tr>
  <%
    HashMap dataDtl= (HashMap)session.getAttribute("dataDtl");
    ArrayList clrlist= (ArrayList)session.getAttribute("clrlist");
    ArrayList keylist= (ArrayList)session.getAttribute("keylist");
    String view= util.nvl((String)request.getAttribute("view"));
    if(!view.equals("")){
    if(keylist!=null && keylist.size()>0){
    HashMap prp = info.getPrp();
   ArrayList mixclrList = (ArrayList)prp.get("MIX_CLARITYV");
   int keylistsz=keylist.size();
   int mixclrListsz=mixclrList.size();
   %>
   <tr> <td valign="top" class="tdLayout">
   <table id="gridall">
    <tr>
    <td>ALL &nbsp;<img src="../images/ico_file_excel.png" onclick="goTo('orclRptAction.do?method=mixtrfcreateXL&loopindex=','','')" border="0"/> 
    </td>
    </tr>
    <%for(int i=0;i<keylistsz;i++){
    String key = (String)keylist.get(i);
    String keyLable = key.replaceAll("_", "  SHAPE :");
    ArrayList mixszList=new ArrayList();
    mixszList=(ArrayList)dataDtl.get(key);
    int mixszListsz=mixszList.size();
    %>
   <tr><td><b><%=keyLable%></b>&nbsp; 
  <img src="../images/ico_file_excel.png" onclick="goTo('orclRptAction.do?method=mixtrfcreateXL&loopindex=<%=i%>','','')" border="0"/> 
  &nbsp;&nbsp;
  <input type="checkbox" name="AVG_dis_<%=i%>" id="AVG_dis_<%=i%>" value="" onclick="displayReports('data_<%=i%>','AVG_dis_<%=i%>','AVG')"/>AVG&nbsp;
  </td></tr>
  <tr><td><table class="grid1" id="data_<%=i%>"><tr><th align="center">Mix Clarity/Mix Size </th>
  <%for(int j=0;j<mixszListsz;j++){
  String mixsz=util.nvl((String)mixszList.get(j));
  %>
  <th align="center" colspan="2"><%=mixsz%></th>
  <%}%>
  <th align="center" colspan="2">Total</th>
  </tr>
  <tr>
  <td></td>
  <%for(int j=0;j<mixszListsz;j++){
  String mixsz=util.nvl((String)mixszList.get(j));
  %>
  <td>CTS</td>
  <td><span id="AVG_<%=i%>_<%=j%>" style="display:none;" >AVG</span></td>
  <%}%>
  <td>CTS</td>
  <td><span id="TOTALAVG_<%=i%>_<%=i%>" style="display:none;" >AVG</span></td>
  
  </tr>
  <%
  for(int k=0;k<mixclrListsz;k++){
  String mixclarity=util.nvl((String)mixclrList.get(k));
  if(clrlist.contains(mixclarity)){
  %>
  <tr>
  <th><%=mixclarity%></th>
  <%for(int l=0;l<mixszListsz;l++){
  String mixsize=util.nvl((String)mixszList.get(l));
  %>
  <td align="right"><%=util.nvl((String)dataDtl.get(key+"_"+mixsize+"_"+mixclarity+"_CTS"))%></td>
    <td align="right"><span id="AVG_<%=i%>_<%=k%>_<%=l%>" style="display:none;"><%=util.nvl((String)dataDtl.get(key+"_"+mixsize+"_"+mixclarity+"_AVG"))%></span></td>
  <%}%>
  <td align="right"><b><%=util.nvl((String)dataDtl.get(key+"_"+mixclarity+"_CTS"))%></b></td>
  <td align="right"><span id="TOTALAVG_<%=i%>_<%=k%>" style="display:none;" ><b><%=util.nvl((String)dataDtl.get(key+"_"+mixclarity+"_AVG"))%></b></span></td>
  </tr>
  <%}}%>
  <tr>
  <th align="center">Total</th>
  <%for(int j=0;j<mixszListsz;j++){
  String mixsize=util.nvl((String)mixszList.get(j));
  %>
  <td align="right"><b><%=util.nvl((String)dataDtl.get(key+"_"+mixsize+"_CTS"))%></b></td>
  <td align="right"><span id="TTLAVG_<%=i%>_<%=j%>" style="display:none;"><b><%=util.nvl((String)dataDtl.get(key+"_"+mixsize+"_AVG"))%></b></span></td>
  <%}%>
  <td align="right"><b><%=util.nvl((String)dataDtl.get(key+"_CTS"))%></b></td>
  <td align="right"><span id="GTTLAVG_<%=i%>_<%=i%>" style="display:none;" ><b><%=util.nvl((String)dataDtl.get(key+"_AVG"))%></b></span></td>
  </tr>
  </table></td></tr>
  <%}%>
  </table></td></tr>
  <%}else{%>
<tr><td valign="top" class="hedPg">
Sorry No Result Found</td></tr>
<%}}%>
  <tr><td>
<jsp:include page="/footer.jsp" />
</td></tr>
  </table>  
  </body>
    <%@include file="../calendar.jsp"%>
</html>