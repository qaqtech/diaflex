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
  
  <title>Sale Base Price</title>
  <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/bse.js?v=<%=info.getJsVersion()%> " > </script>
  <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/bse.css?v=<%=info.getJsVersion()%>"/>
  <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/ajax.js?v=<%=info.getJsVersion()%> " > </script>
  <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/style.css?v=<%=info.getJsVersion()%>"/>
  <script language="javaScript" type="text/javascript" src="<%=info.getReqUrl()%>/calendar/calendar.js"></script>
   <link href="<%=info.getReqUrl()%>/calendar/calendar.css" rel="stylesheet" type="text/css" />
   <script src="<%=info.getReqUrl()%>/PopScripts/jquery-1.2.6.min.js" type="text/javascript"></script>
   <script type="text/javascript" src="<%=info.getReqUrl()%>/jqueryScript/jquery.js " > </script>
   <script type="text/javascript" src="<%=info.getReqUrl()%>/chartscript/amcharts.js?v=<%=info.getJsVersion()%> " > </script>
<script type="text/javascript" src="<%=info.getReqUrl()%>/chartscript/chartajax.js?v=<%=info.getJsVersion()%> " > </script>
<script type="text/javascript" src="<%=info.getReqUrl()%>/chartscript/pie.js?v=<%=info.getJsVersion()%> " > </script>
<script type="text/javascript" src="<%=info.getReqUrl()%>/chartscript/serial.js?v=<%=info.getJsVersion()%> " > </script>
  </head>
  <%
  String view = util.nvl((String)request.getAttribute("view"));
  ArrayList pktList = ((ArrayList)request.getAttribute("pktList")== null)?new ArrayList():(ArrayList)request.getAttribute("pktList");
  %>
  
 <body onload="MM_preloadImages('images/faunalogo.png')" topmargin="0" leftmargin="0" rightmargin="0" bottommargin="0">
 <input type="hidden" id="reqUrl" name="reqUrl" value="<%=info.getReqUrl()%>"/>
<div id="backgroundPopup"></div>
<div id="popupContactASSFNL">
<table class="popUpTb">
 <tr><td height="5%">&nbsp;&nbsp;&nbsp;<html:button property="value(close)" styleClass="submit"  onclick="MKEmptyPopup('SC');disablePopupASSFNL();uncheckbox()" value="Close"  /> </td></tr>
<tr><td valign="top" height="95%">
<iframe name="SC" class="frameStyle" align="middle" frameborder="0">

</iframe></td></tr>
</table>
</div>
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
  <img src="../images/line.jpg" border="0" /></td><td valign="middle"> <span class="pgHed">Sale Base Price</span> </td>
    </tr></table>
  </td>
  </tr>
  
  
  <tr><td valign="top" class="tdLayout">
  <html:form action="report/orclRptAction.do?method=fetchSaleBasePrc" method="post">
   <table  class="grid1">
  <tr><td>Date </td>  
  <td>From</td><td> <html:text property="value(frmdte)" styleId="frmdte" name="orclReportForm" size="8" />&nbsp;<img src="<%=info.getReqUrl()%>/images/calender.png" onClick="setYears(2011, <%=info.getCurrentYear()%>);showCalender(this, 'frmdte');"> </td>
  <td>To</td><td><html:text property="value(todte)" styleId="todte" name="orclReportForm"  size="8" />&nbsp;<img src="<%=info.getReqUrl()%>/images/calender.png" onClick="setYears(2011, <%=info.getCurrentYear()%>);showCalender(this, 'todte');"></td>
  </tr>
  <tr><td colspan="5" align="center"><html:submit property="view" value="View" styleClass="submit"/></td></tr>
  </table>
  </html:form>
  </td></tr>
  
  <% if(!view.equals("")){%>
  
  
  <tr><td valign="top" class="hedPg" nowrap="nowrap">
  <display:table name="sessionScope.orclReportForm.pktList" class="grid1" requestURI="" export="true"  sort="list" decorator="ft.com.Decortor">
  <display:column property="emp" style="font-weight: bold;" title="Sale Person" group="1" sortable="true" />
  <display:column property="byr" style="font-weight: bold;" title="Buyer" group="2" sortable="true" />
  <display:column property="qty"  title="QTY" group="3"  />
  <display:column property="cts"  title="CTS"   />
  <display:column property="vlu"  title="Value"   />
  <display:setProperty name="export.excel.filename" value="Details.xls"/>
  <display:setProperty name="export.xls" value="true" />
  <display:setProperty name="export.csv" value="false" />
  <display:setProperty name="export.xml" value="false" />
  </display:table>
  </td></tr>
  
  <%if(pktList.size()>0){%>
  
  <%  
      HashMap allPageDtl= (info.getPageDetails() == null)?new HashMap():(HashMap)info.getPageDetails();
      HashMap pageDtl=(HashMap)allPageDtl.get("BASE_PRC_REPORT");
      int defaultdisplay=0;
      String basedon="emp";
      String piettl="";
      defaultdisplay=Integer.parseInt(util.nvl((String)((HashMap)((ArrayList)pageDtl.get("EMPWISEDFLT")).get(0)).get("dflt_val"),"10"));
      piettl=util.nvl((String)((HashMap)((ArrayList)pageDtl.get("EMPWISEDFLT")).get(0)).get("fld_ttl"),"Pie");
      
  %>
    <tr><td>
    <table>
    <%ArrayList list=new ArrayList();
    list.add("vlu");
    for(int i=0;i<list.size();i++){
    String itm=(String)list.get(i);
    piettl=piettl.replaceAll("ITM",itm);
    String url="ajaxRptAction.do?method=createempwisePieChart&basedon="+basedon+"&itm="+itm+"&defaultdisplay="+defaultdisplay;
    %>
    <tr><td valign="top">
    <script type="text/javascript">
    <!--
    $(window).bind("load", function() {
    createbuyerwisePieChart('<%=url%>','<%=piettl%>','chartdiv','50','362');
    });
    -->
    </script>  
    </td></tr>
    <%}%>
    </table>
    </td></tr>  
    
    
  <tr><td valign="top" class="hedPg">
  <div id="chartdiv" style="width: 60%; height: 500px;"></div>
  </td></tr>
    
 <%}%>   
<%}%>

  
  
  
  <tr>
    <td><jsp:include page="/footer.jsp" /> </td>
    </tr>
  </table>
   <%@include file="../calendar.jsp"%>
  </body>
</html>
  
  