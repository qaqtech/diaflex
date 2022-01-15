<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page errorPage="../error_page.jsp" %>
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import="java.util.ArrayList,java.util.ArrayList,java.util.HashMap, java.sql.*, java.util.Enumeration, java.util.logging.Level"%>
	<jsp:useBean id="info" class="ft.com.InfoMgr" scope="session" />
	<jsp:useBean id="util" class="ft.com.JspUtil" scope="page" />
<%@taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>

<title>Price Graph Report</title>  
<link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/bse.css?v=<%=info.getJsVersion()%>"/>
<link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/style.css?v=<%=info.getJsVersion()%>"/>
<link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/general.css?v=<%=info.getJsVersion()%>"/>
<script src="<%=info.getReqUrl()%>/PopScripts/jquery-1.2.6.min.js" type="text/javascript"></script>
<script type="text/javascript" src="<%=info.getReqUrl()%>/chartscript/amcharts.js?v=<%=info.getJsVersion()%> " > </script>
<script type="text/javascript" src="<%=info.getReqUrl()%>/chartscript/chartajax.js?v=<%=info.getJsVersion()%> " > </script>
<script type="text/javascript" src="<%=info.getReqUrl()%>/chartscript/serial.js?v=<%=info.getJsVersion()%> " > </script>
<script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/bse.js?v=<%=info.getJsVersion()%> " > </script>
</head>
        <%String logId=String.valueOf(info.getLogId());
        String onfocus="cook('"+logId+"')";
        %>
 <body onfocus="<%=onfocus%>" onload="MM_preloadImages('images/faunalogo.png')" topmargin="0" leftmargin="0" rightmargin="0" bottommargin="0">
    <%
    HashMap dataDtl= (HashMap)session.getAttribute("dataDtl");
    ArrayList monthLst= (ArrayList)session.getAttribute("monthLst");
    ArrayList szLst= (ArrayList)session.getAttribute("szLst");
    String szVal=util.nvl((String)request.getParameter("szVal"));
    ArrayList grpLst=(ArrayList)dataDtl.get("GRP");
    String lineGrp=grpLst.toString();
    lineGrp = lineGrp.replaceAll("\\[", "");
    lineGrp = lineGrp.replaceAll("\\]", "");
    lineGrp = lineGrp.replaceAll("\\,", "_");
  String url="ajaxRptAction.do?method=createChartSizepurity";
  String style="width: 50%; height: 362px; float:left;";
  String styleId=szVal+"_HIDD";
  %>
  <div>
  <input type="hidden" id="lineGrp" value="<%=lineGrp%>">
  <input type="hidden" id="<%=szVal%>_VLUTTL" value="AVG">
  <input type="hidden" id="<%=szVal%>_MFG_VLUTTL" value="DIFF">
  <input type="hidden" id="<%=styleId%>" value="<%=szVal%>">
  <div id="<%=szVal%>" style="<%=style%>"></div>
  <div id="<%=szVal%>_MFG" style="<%=style%>"></div>
  </div>
<script type="text/javascript">
 <!--
$(window).bind("load", function() {
createChartSizepurity('<%=url%>',' Price Graph','50','362');
});
 -->
</script> 
  </body>
</html>