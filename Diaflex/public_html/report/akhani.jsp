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

  <title>Akha Ni Report</title>
 
  </head>

        <%String logId=String.valueOf(info.getLogId());
        String onfocus="cook('"+logId+"')";
        %>
 <body onfocus="<%=onfocus%>" onload="MM_preloadImages('images/faunalogo.png')" topmargin="0" leftmargin="0" rightmargin="0" bottommargin="0">
 
  <input type="hidden" id="reqUrl" name="reqUrl" value="<%=info.getReqUrl()%>"/>
<div id="popupContactASSFNL">
<table class="popUpTb">
<tr><td height="5%">&nbsp;&nbsp;&nbsp;<html:button property="value(close)" styleClass="submit"  onclick="MKEmptyPopup('SC');disablePopupASSFNL();" value="Close"  /></td></tr>
<tr><td valign="top" height="95%">
<iframe name="SC" class="frameStyle"   align="middle" frameborder="0">
</iframe></td></tr>
</table>
</div>
<div id="backgroundPopup"></div>

 
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
  <img src="../images/line.jpg" border="0" /></td><td valign="middle"> <span class="pgHed">Memo Average Difference Report</span> </td>
</tr></table>
  </td>
  </tr>
<tr><td valign="top" class="hedPg">
<html:form action="/report/orclRptAction.do?method=fetchakhi"  method="POST">
  <table  class="grid1">
   <tr>
   <th colspan="2" align="center">Memo Search</th>
   </tr>
       <tr><td>Start </td>  
       <td>From&nbsp; <html:text property="value(startfrmdte)" styleId="startfrmdte" name="orclReportForm" size="8" /><img src="<%=info.getReqUrl()%>/images/calender.png" onClick="setYears(2011, <%=info.getCurrentYear()%>);showCalender(this, 'startfrmdte');"> 
       To&nbsp; <html:text property="value(starttodte)" styleId="starttodte" name="orclReportForm"  size="8"/> <img src="<%=info.getReqUrl()%>/images/calender.png" onClick="setYears(2011, <%=info.getCurrentYear()%>);showCalender(this, 'starttodte');"></td>
      </tr>
      <tr><td>Complete </td>  
       <td>From&nbsp; <html:text property="value(compfrdte)" styleId="compfrdte" name="orclReportForm" size="8" /><img src="<%=info.getReqUrl()%>/images/calender.png" onClick="setYears(2011, <%=info.getCurrentYear()%>);showCalender(this, 'compfrdte');"> 
       To&nbsp; <html:text property="value(comptodte)" styleId="comptodte" name="orclReportForm"  size="8"/> <img src="<%=info.getReqUrl()%>/images/calender.png" onClick="setYears(2011, <%=info.getCurrentYear()%>);showCalender(this, 'comptodte');"></td>
      </tr>
      <tr><td colspan="2">Or</td></tr>
   <tr>
   <td>Memo ID: </td>
   <td><html:textarea property="value(memo)" name="orclReportForm" styleId="memoId"/></td>
   </tr>
   <tr>
   <td colspan="2" align="center"><html:submit property="value(srch)" value="Akha Ni Fetch" styleClass="submit" />
   </td>
   </tr>
   </table>
</html:form>
</td>
</tr>
  <%
    HashMap dataDtl= (HashMap)session.getAttribute("dataDtl");
    ArrayList memoList= (ArrayList)request.getAttribute("memoList");
    String view= util.nvl((String)request.getAttribute("View"));
    if(!view.equals("")){
    if(dataDtl!=null && dataDtl.size()>0){
    double getttlcts=0,getaakhaold=0,getaakhasrt=0,getmanojnew=0,getmanojold=0,getaakhanewvlu=0,getaakhaoldvlu=0,getmnjnewvlu=0,getmnjoldvlu=0;
    double vlujama=0,vlunew=0,vlunewavg=0;
    String percentgia="",percentnew="",percentper="",diffaakhanew="";
    double aakhanew=0;
String diffmnj="0",olddiff="0",srtdiff="0",gdavgdiff="0",diffmnjcolor="",olddiffcolor="",srtdiffcolor="",gdavgdiffcolor="";
    %>
<tr><td class="hedPg">Create Excel <img src="../images/ico_file_excel.png" title="Click here to Generate Excel" onclick="goTo('orclRptAction.do?method=akhaniavgDiffXL','','')" border="0"/> 
<a href="orclRptAction.do?method=fetchakhiSH" id="DTL_0"  target="SC" title="Click Here To ShapeWise Details" onclick="loadASSFNLPop('DTL_0')">ShapeWise Details</a> 
</td></tr>    
<tr>
<td class="hedPg">
<table class="grid1">
<tr>
<th>Memo</th><th>Carat</th>
<th>96-UP-GIA CTS</th>
<th>Aakha ni K.OLD</th><th>Aakha ni GD SRT</th><th>Aakha New</th>
<th>OLD</th><th>Old Avg SRT</th><th>New Avg</th>
<th>Manoj Old</th><th>Manoj New</th><th></th>
<th>Manoj Old</th><th>Manoj New</th>
<th>Diff Manoj</th><th>K.Old Diff %</th>
<th>GD SRT Diff%</th><th>GD AVG DIFF</th>
<th>%</th><th>96 up GIA</th>
</tr>
<%for(int i=0;i<memoList.size();i++){
String memoVal=util.nvl((String)memoList.get(i));
String ttlcts=util.nvl2((String)dataDtl.get(memoVal+"_CTS"),"0").trim();
String deptcts=util.nvl2((String)dataDtl.get(memoVal+"_96-UP-GIA_CTS"),"0").trim();
String aakhaold=util.nvl2((String)dataDtl.get(memoVal+"_AAKHAOLD"),"0");
String aakhasrt=util.nvl2((String)dataDtl.get(memoVal+"_AAKHASRT"),"0");
String aakhaoldvlu=util.nvl2((String)dataDtl.get(memoVal+"_AAKHAOLDVLU"),"0");
String aakhasrtvlu=util.nvl2((String)dataDtl.get(memoVal+"_AAKHASRTVLU"),"0");
String mnjold=util.nvl2((String)dataDtl.get(memoVal+"_96-UP-GIA_MNJOLD"),"0");
String mnjnew=util.nvl2((String)dataDtl.get(memoVal+"_96-UP-GIA_MNJNEW"),"0");
String mnjoldvlu=util.nvl2((String)dataDtl.get(memoVal+"_96-UP-GIA_MNJOLDVLU"),"0");
String mnjnewvlu=util.nvl2((String)dataDtl.get(memoVal+"_96-UP-GIA_MNJNEWVLU"),"0");
percentper=util.nvl2((String)dataDtl.get(memoVal+"_BKPER"),"0");
aakhanew=0;
diffmnj="0";olddiff="0";srtdiff="0";gdavgdiff="0";diffmnjcolor="";olddiffcolor="";srtdiffcolor="";gdavgdiffcolor="";
getmnjnewvlu=Double.parseDouble(mnjnewvlu);
getmnjoldvlu=Double.parseDouble(mnjoldvlu);
getttlcts=Double.parseDouble(ttlcts);
getaakhaold=Double.parseDouble(aakhaold);
getaakhasrt=Double.parseDouble(aakhasrt);
getmanojnew=Double.parseDouble(mnjnew);
getmanojold=Double.parseDouble(mnjold);
getaakhaoldvlu=Double.parseDouble(aakhaoldvlu);
if(!mnjnew.equals("0") && !mnjold.equals("0")){
diffmnj=String.valueOf(util.roundToDecimals(Math.round((((getmanojnew-getmanojold)*100)/getmanojold)),1));
}
if(!mnjoldvlu.equals("0") && !mnjnewvlu.equals("0")){
diffaakhanew=String.valueOf(Math.round(util.roundToDecimals(((getmnjnewvlu-getmnjoldvlu)/getttlcts),1)));
aakhanew=Double.parseDouble(aakhaold)+Double.parseDouble(diffaakhanew);
vlunewavg=util.roundToDecimals(aakhanew*getttlcts,1);
}
if(!aakhaold.equals("0")){ 
olddiff=String.valueOf(Math.round(util.roundToDecimals((((aakhanew-getaakhaold)*100)/getaakhaold),1)));
srtdiff=String.valueOf(Math.round(util.roundToDecimals((((aakhanew-getaakhasrt)*100)/getaakhasrt),1)));
gdavgdiff=String.valueOf(Math.round(util.roundToDecimals((((getaakhasrt-getaakhaold)*100)/getaakhaold),1)));
percentgia=String.valueOf(Math.round(util.roundToDecimals(((getmnjoldvlu/getaakhaoldvlu)*100),1)));
}

if(Double.parseDouble(diffmnj)>=3)
diffmnjcolor="green";

if(Double.parseDouble(diffmnj)<=-3)
diffmnjcolor="red";

if(Double.parseDouble(olddiff)>=3)
olddiffcolor="green";

if(Double.parseDouble(olddiff)<=-3)
olddiffcolor="red";

if(Double.parseDouble(srtdiff)>=3)
srtdiffcolor="green";

if(Double.parseDouble(srtdiff)<=-3)
srtdiffcolor="red";

if(Double.parseDouble(gdavgdiff)>=3)
gdavgdiffcolor="green";

if(Integer.parseInt(gdavgdiff)<=-3)
gdavgdiffcolor="red";

if(!diffmnj.equals("") && !diffmnj.equals("0"))
diffmnj=diffmnj+"%";

if(!percentper.equals("") && !percentper.equals("0"))
percentper=percentper+"%";

if(!percentgia.equals("") && !percentgia.equals("0"))
percentgia=percentgia+"%";

if(!olddiff.equals("") && !olddiff.equals("0"))
olddiff=olddiff+"%";

if(!srtdiff.equals("") && !srtdiff.equals("0"))
srtdiff=srtdiff+"%";

if(!gdavgdiff.equals("") && !gdavgdiff.equals("0"))
gdavgdiff=gdavgdiff+"%";
%>
<tr>
<td><%=memoVal%></td>
<td><%=ttlcts%></td>
<td><%=deptcts%></td>
<td><%=aakhaold%></td>
<td><%=aakhasrt%></td>
<td><%=aakhanew%></td>
<td><%=aakhaoldvlu%></td>
<td><%=aakhasrtvlu%></td>
<td><%=vlunewavg%></td>
<td><%=mnjold%></td>
<td><%=mnjnew%></td>
<td><%=diffaakhanew%></td>
<td><%=mnjoldvlu%></td>
<td><%=mnjnewvlu%></td>
<td bgcolor="<%=diffmnjcolor%>"><%=diffmnj%></td>
<td bgcolor="<%=olddiffcolor%>"><%=olddiff%></td>
<td bgcolor="<%=srtdiffcolor%>"><%=srtdiff%></td>
<td bgcolor="<%=gdavgdiffcolor%>"><%=gdavgdiff%></td>
<td><%=percentper%></td>
<td><%=percentgia%></td>
</tr>
<%}%>
<%
String ttlcts=util.nvl2((String)dataDtl.get("TOTAL_CTS"),"0").trim();
String deptcts=util.nvl2((String)dataDtl.get("TOTAL_96-UP-GIA_CTS"),"0").trim();
String aakhaold=util.nvl2((String)dataDtl.get("TOTAL_AAKHAOLD"),"0");
String aakhasrt=util.nvl2((String)dataDtl.get("TOTAL_AAKHASRT"),"0");
String aakhaoldvlu=util.nvl2((String)dataDtl.get("TOTAL_AAKHAOLDVLU"),"0");
String aakhasrtvlu=util.nvl2((String)dataDtl.get("TOTAL_AAKHASRTVLU"),"0");
String mnjold=util.nvl2((String)dataDtl.get("TOTAL_96-UP-GIA_MNJOLD"),"0");
String mnjnew=util.nvl2((String)dataDtl.get("TOTAL_96-UP-GIA_MNJNEW"),"0");
String mnjoldvlu=util.nvl2((String)dataDtl.get("TOTAL_96-UP-GIA_MNJOLDVLU"),"0");
String mnjnewvlu=util.nvl2((String)dataDtl.get("TOTAL_96-UP-GIA_MNJNEWVLU"),"0");
percentper=util.nvl2((String)dataDtl.get("TOTAL_BKPER"),"0");
aakhanew=0;
diffmnj="0";olddiff="0";srtdiff="0";gdavgdiff="0";diffmnjcolor="";olddiffcolor="";srtdiffcolor="";gdavgdiffcolor="";
getmnjnewvlu=Double.parseDouble(mnjnewvlu);
getmnjoldvlu=Double.parseDouble(mnjoldvlu);
getttlcts=Double.parseDouble(ttlcts);
getaakhaold=Double.parseDouble(aakhaold);
getaakhasrt=Double.parseDouble(aakhasrt);
getmanojnew=Double.parseDouble(mnjnew);
getmanojold=Double.parseDouble(mnjold);
getaakhaoldvlu=Double.parseDouble(aakhaoldvlu);
if(!mnjnew.equals("0") && !mnjold.equals("0")){
diffmnj=String.valueOf(util.roundToDecimals(Math.round((((getmanojnew-getmanojold)*100)/getmanojold)),1));
}
if(!mnjoldvlu.equals("0") && !mnjnewvlu.equals("0")){
diffaakhanew=String.valueOf(Math.round(util.roundToDecimals(((getmnjnewvlu-getmnjoldvlu)/getttlcts),1)));
aakhanew=Double.parseDouble(aakhaold)+Double.parseDouble(diffaakhanew);
vlunewavg=util.roundToDecimals(aakhanew*getttlcts,1);
}
if(!aakhaold.equals("0")){ 
olddiff=String.valueOf(Math.round(util.roundToDecimals((((aakhanew-getaakhaold)*100)/getaakhaold),1)));
srtdiff=String.valueOf(Math.round(util.roundToDecimals((((aakhanew-getaakhasrt)*100)/getaakhasrt),1)));
gdavgdiff=String.valueOf(Math.round(util.roundToDecimals((((getaakhasrt-getaakhaold)*100)/getaakhaold),1)));
percentgia=String.valueOf(Math.round(util.roundToDecimals(((getmnjoldvlu/getaakhaoldvlu)*100),1)));
}


if(Double.parseDouble(diffmnj)>=3)
diffmnjcolor="green";

if(Double.parseDouble(diffmnj)<=-3)
diffmnjcolor="red";

if(Double.parseDouble(olddiff)>=3)
olddiffcolor="green";

if(Double.parseDouble(olddiff)<=-3)
olddiffcolor="red";

if(Double.parseDouble(srtdiff)>=3)
srtdiffcolor="green";

if(Double.parseDouble(srtdiff)<=-3)
srtdiffcolor="red";

if(Double.parseDouble(gdavgdiff)>=3)
gdavgdiffcolor="green";

if(Double.parseDouble(gdavgdiff)<=-3)
gdavgdiffcolor="red";

if(!diffmnj.equals("") && !diffmnj.equals("0"))
diffmnj=diffmnj+"%";

if(!percentper.equals("") && !percentper.equals("0"))
percentper=percentper+"%";

if(!percentgia.equals("") && !percentgia.equals("0"))
percentgia=percentgia+"%";

if(!olddiff.equals("") && !olddiff.equals("0"))
olddiff=olddiff+"%";


if(!srtdiff.equals("") && !srtdiff.equals("0"))
srtdiff=srtdiff+"%";

if(!gdavgdiff.equals("") && !gdavgdiff.equals("0"))
gdavgdiff=gdavgdiff+"%";
%>
<tr>
<td>TOTAL</td>
<td><%=ttlcts%></td>
<td><%=deptcts%></td>
<td><%=aakhaold%></td>
<td><%=aakhasrt%></td>
<td><%=aakhanew%></td>
<td><%=aakhaoldvlu%></td>
<td><%=aakhasrtvlu%></td>
<td><%=vlunewavg%></td>
<td><%=mnjold%></td>
<td><%=mnjnew%></td>
<td><%=diffaakhanew%></td>
<td><%=mnjoldvlu%></td>
<td><%=mnjnewvlu%></td>
<td bgcolor="<%=diffmnjcolor%>"><%=diffmnj%></td>
<td bgcolor="<%=olddiffcolor%>"><%=olddiff%></td>
<td bgcolor="<%=srtdiffcolor%>"><%=srtdiff%></td>
<td bgcolor="<%=gdavgdiffcolor%>"><%=gdavgdiff%></td>
<td><%=percentper%></td>
<td><%=percentgia%></td>
</tr>
</table>
</td>
</tr>
<%
session.setAttribute("memoList",memoList);
%>
    <%}else{%>
<tr><td valign="top" class="hedPg">
Sorry No Result Found</td></tr>
<%}}%>
  <tr><td>
<jsp:include page="/footer.jsp" />
</td></tr>
  </table>  
       <%@include file="../calendar.jsp"%>
  </body>
</html>