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
     <input type="hidden" id="reqUrl" name="reqUrl" value="<%=info.getReqUrl()%>"/>
<div id="backgroundPopup"></div>
<div id="popupContactASSFNL">
<table class="popUpTb">
 <tr><td height="5%">&nbsp;&nbsp;&nbsp;<html:button property="value(close)" styleClass="submit"  onclick="MKEmptyPopup('SC');disablePopupASSFNL();uncheckbox()" value="Close"  /> </td></tr>
<tr><td valign="top" height="95%">
<iframe name="SC" class="frameStyle"   align="middle" frameborder="0">

</iframe></td></tr>
</table>
</div>
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
    HashMap dataDtl= (HashMap)session.getAttribute("dataDtl");
    ArrayList monthLst= (ArrayList)session.getAttribute("monthLst");
     ArrayList szLst= (ArrayList)session.getAttribute("szLst");
    int monthLstsz=monthLst.size();
    String currentkey="",currentqty="",currentavg="",firstmonth="",firstkey="",firstavg="";
    if(dataDtl!=null && dataDtl.size()>0){
    ArrayList grpLst=(ArrayList)dataDtl.get("GRP");
    double favg=0;
    double cavg=0;
    HashMap dbinfo = info.getDmbsInfoLst();
    String sizeval = (String)dbinfo.get("SIZE");
    HashMap prp = info.getPrp();
        ArrayList prpPrtSize=null;
        ArrayList prpValSize=null;
        ArrayList prpSrtSize = null;
        prpPrtSize = (ArrayList)prp.get(sizeval+"P");
        prpSrtSize = (ArrayList)prp.get(sizeval+"S");
        prpValSize = (ArrayList)prp.get(sizeval+"V");
    %>
    <tr>
    <td valign="top" class="hedPg">
    <table class="grid1">
    <%for(int j=0;j<szLst.size();j++){
    String szVal=util.nvl((String)szLst.get(j));%>
    <tr><td><table class="grid1" id="dataTable" style="padding:15px 0px 15px 0px;">
    <tr id="<%=j%>">
    <td>
    <A href="<%=info.getReqUrl()%>/report/sizePurityLineGraph.jsp?szVal=<%=szVal%>" id="DTL_<%=j%>"  target="SC" title="Click Here To See Line Graph" onclick="loadASSFNL('<%=j%>','DTL_<%=j%>')">
     Line Graph</a>
    </td>
    </tr>
    <tr>
    <th><%=szVal%></th>
    <%for(int k=0;k<monthLstsz;k++){
    %>
    <th><%=util.nvl((String)monthLst.get(k))%></th>
    <%}%>
    </tr>
    <%for(int l=0;l<grpLst.size();l++){
    String grp=util.nvl((String)grpLst.get(l));%>
    <tr>
    <td><%=grp%></td>
    <%for(int m=0;m<monthLstsz;m++){
    String currentmonth=util.nvl((String)monthLst.get(m));
    currentkey=szVal+"_"+currentmonth+"_"+grp;
    currentavg=util.nvl((String)dataDtl.get(currentkey+"_AVG"));
    %>
    <td><%=currentavg%></td>
    <%}%>
    </tr>
    <%}%>
    <tr>
    <th><%=szVal%></th>
    <%for(int k=0;k<monthLstsz;k++){
    %>
    <th><%=util.nvl((String)monthLst.get(k))%></th>
    <%}%>
    </tr>
        <%for(int l=0;l<grpLst.size();l++){
    String grp=util.nvl((String)grpLst.get(l));%>
    <tr>
    <td><%=grp%></td>
    <%for(int m=0;m<monthLstsz;m++){
    firstmonth=util.nvl((String)monthLst.get(0));
    String currentmonth=util.nvl((String)monthLst.get(m));
    firstkey=szVal+"_"+firstmonth+"_"+grp;
    currentkey=szVal+"_"+currentmonth+"_"+grp;
    currentavg=util.nvl((String)dataDtl.get(currentkey+"_AVG"));
    firstavg=util.nvl((String)dataDtl.get(firstkey+"_AVG"));
    String percent="";
    if(!currentavg.equals("") && !firstavg.equals("")){
    favg=Double.parseDouble(firstavg);
    cavg=Double.parseDouble(currentavg);
    percent=String.valueOf(util.roundToDecimals((100-((favg/cavg)*100)),2));
    }
    %>
    <td><%=percent%></td>
    <%}%>
    </tr>
    <%}%>
    </table></td></tr>
    <%}%>
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