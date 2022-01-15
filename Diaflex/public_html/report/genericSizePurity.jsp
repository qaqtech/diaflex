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
<title>Size Purity Report</title>
</head>
        <%String logId=String.valueOf(info.getLogId());
        String onfocus="cook('"+logId+"')";
        %>
 <body onfocus="<%=onfocus%>" onload="MM_preloadImages('images/faunalogo.png')" topmargin="0" leftmargin="0" rightmargin="0" bottommargin="0">
    <table width="100%"  border="0" align="center" cellpadding="0" cellspacing="0" >
  <tr>
    <td height="5" valign="top" class="greyline"><img src="../images/grey_linebig.png" width="685" height="5" align="right" /></td>
  </tr>
  <tr>
  <td valign="top" class="hedPg">
  <table cellpadding="1" cellspacing="5"><tr><td valign="middle">
  <img src="../images/line.jpg" border="0" /></td><td valign="middle"> 
  <span class="pgHed">Size Purity Report
  </span></td></tr></table>
  </td></tr>
    <%
    HashMap dataDtl = ((HashMap)session.getAttribute("dataDtl") == null)?new HashMap():(HashMap)session.getAttribute("dataDtl");
    String currentkey="",currentqty="",currentavg="",firstmonth="",firstkey="",firstavg="";
    if(dataDtl!=null && dataDtl.size()>0){
    ArrayList monthLst= ((ArrayList)session.getAttribute("monthLst") == null)?new ArrayList():(ArrayList)session.getAttribute("monthLst");
    ArrayList szLst= (ArrayList)session.getAttribute("szLst");
    ArrayList statusLst= (ArrayList)session.getAttribute("statusLst");
    ArrayList srchids= (ArrayList)request.getAttribute("srchids");
    int monthLstsz=monthLst.size();
    ArrayList grpLst=(ArrayList)dataDtl.get("GRP");
    double favg=0;
    double cavg=0;
    if(srchids!=null && srchids.size()>0){
    String dsc=util.nvl((String)request.getAttribute("srchDscription"));
    %>
    <tr><td valign="top" class="hedPg"><span class="txtLink" >Search Description : <%= dsc%></span>
    <td></tr>
    <%}%>
    <tr>
    <td valign="top" class="hedPg">
    <table class="grid1">
    <%for(int i=0;i<statusLst.size();i++){
    String stt=util.nvl((String)statusLst.get(i));%>
    <tr><td colspan="<%=((monthLstsz*3)+1)%>"><%=stt%></td></tr>
    <%for(int j=0;j<szLst.size();j++){
    String szVal=util.nvl((String)szLst.get(j));%>
    <tr>
    <th><%=szVal%></th>
    <%for(int k=0;k<monthLstsz;k++){
    %>
    <th colspan="3"><%=util.nvl((String)monthLst.get(k))%></th>
    <%}%>
    </tr>
    <tr>
    <td></td>
    <%for(int k=0;k<monthLstsz;k++){
    %>
    <td>QTY</td><td>AVG</td><td>DIFF</td>
    <%}%>
    </tr>
    <%for(int l=0;l<grpLst.size();l++){
    String grp=util.nvl((String)grpLst.get(l));%>
    <tr>
    <td><%=grp%></td>
    <%for(int m=0;m<monthLstsz;m++){
    firstmonth=util.nvl((String)monthLst.get(0));
    String currentmonth=util.nvl((String)monthLst.get(m));
    firstkey=stt+"@"+szVal+"@"+firstmonth+"@"+grp;
    currentkey=stt+"@"+szVal+"@"+currentmonth+"@"+grp;
    currentqty=util.nvl((String)dataDtl.get(currentkey+"@QTY"));
    currentavg=util.nvl((String)dataDtl.get(currentkey+"@AVG"));
    firstavg=util.nvl((String)dataDtl.get(firstkey+"@AVG"));
    String percent="";
    if(!currentavg.equals("") && !firstavg.equals("")){
    favg=Double.parseDouble(firstavg);
    cavg=Double.parseDouble(currentavg);
    percent=String.valueOf(util.roundToDecimals((100-((favg/cavg)*100)),1));
    }
    %>
    <td><%=currentqty%></td>
    <td><%=currentavg%></td>
    <td><%=percent%></td>
    <%}%>
    </tr>
    <%}%>
    <%}}%>
    </table>
    </td>
    </tr>
<%}else{%>
<tr><td valign="top" class="hedPg">
Sorry No Result Found</td></tr>
<%}%>

  </table>
    </body>
</html>