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
  <title>Rough Outstanding Report</title>
  <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/bse.js?v=<%=info.getJsVersion()%> " > </script>
  <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/bse.css?v=<%=info.getJsVersion()%>"/>
  <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/ajax.js?v=<%=info.getJsVersion()%> " > </script>
  <script language="javaScript" type="text/javascript" src="<%=info.getReqUrl()%>/calendar/calendar.js"></script>
  <link href="<%=info.getReqUrl()%>/calendar/calendar.css" rel="stylesheet" type="text/css" />
  <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/style.css?v=<%=info.getJsVersion()%>"/>
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
  <img src="../images/line.jpg" border="0" /></td><td valign="middle"> <span class="pgHed">
  Rough Outstanding Report </span> </td>
</tr></table>
  </td>
  </tr>
  <tr><td valign="top" class="hedPg">
  <%
  HashMap prp = info.getPrp();
  ArrayList lprpstightValList = (ArrayList)prp.get("SIGHTV");
  ArrayList lprpstightsrtList = (ArrayList)prp.get("SIGHTS");
  ArrayList lprpRGH_SZValList = (ArrayList)prp.get("RGH_SZV");
  ArrayList lprpRGH_SZsrtList = (ArrayList)prp.get("RGH_SZS");
  ArrayList lprpARTICLEValList = (ArrayList)request.getAttribute("ARTICLEValList");
  ArrayList lprpARTICLEsrtList = (ArrayList)request.getAttribute("ARTICLESrtList");
  HashMap gridDtlmap = (HashMap)request.getAttribute("gridDtlmap");
  int stightSize = lprpstightValList.size();
  int stightSizeD =stightSize * 2;
  if(gridDtlmap!=null && gridDtlmap.size()>0){
  %>
  <table class="grid1">
  <tr>
  <th colspan="2"></th>
  <%
  
  for(int i=0;i<stightSize;i++){
  String stightVal =(String) lprpstightValList.get(i);
   %>
  <th colspan="2">
  <%=stightVal%>
  </th>
  <%}%>
  </tr>
   <tr>
  <td colspan="2"></td>
  <%
  for(int i=0;i<stightSize;i++){ %>
  <td><b>QTY</b></td><td><b>CTS</b></td>
  <%}%>
  </tr>
   <%
  for(int i=0;i<lprpARTICLEValList.size();i++){
  String articleVal =(String) lprpARTICLEValList.get(i);
  String articleSrt = (String) lprpARTICLEsrtList.get(i);
  ArrayList sizeList = (ArrayList)gridDtlmap.get(articleSrt+"_RGHSIZE");
  %>
<tr>
  <td><%=articleVal%></td>
  <td></td>
  <td colspan="<%=stightSizeD%>"></td>
  </tr>
  
   <%
    for(int j=0;j<sizeList.size();j++){
        String sizeSrt = (String)sizeList.get(j);
        String sizeVal = (String)lprpRGH_SZValList.get(lprpRGH_SZsrtList.indexOf(sizeSrt));
         %>
  <tr>
  <td></td>
  <td><%=sizeVal%></td>
  <%
    for(int k=0;k<lprpstightsrtList.size();k++){
        String stightSrt = (String)lprpstightsrtList.get(k);
        String qty = util.nvl((String)gridDtlmap.get(stightSrt+"_"+articleSrt+"_"+sizeSrt+"_qty"));
        String cts =  util.nvl((String)gridDtlmap.get(stightSrt+"_"+articleSrt+"_"+sizeSrt+"_cts"));
        %>
       <td><%=qty%></td><td><a title="Packet Details" target="_blank"  href="<%=info.getReqUrl()%>/rough/roughGridingReport.do?method=pktDtl&stightSrt=<%=stightSrt%>&articleSrt=<%=articleSrt%>&sizeSrt=<%=sizeSrt%>"><%=cts%></a></td>
    <%  }%>
    </tr>
    <%} %>
  
  <%}%></table><%}else{%>
  Sorry no result found..
  <%}%>
  </td>
  </tr>

   <tr>
    <td><jsp:include page="/footer.jsp" /> </td>
    </tr>
  </table>
   <%@include file="../calendar.jsp"%>
  </body>
</html>