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

<title>Search Result</title>   
   <script src="<%=info.getReqUrl()%>/PopScripts/jquery-1.2.6.min.js" type="text/javascript"></script>
<script type="text/javascript" src="<%=info.getReqUrl()%>/chartscript/amcharts.js?v=<%=info.getJsVersion()%> " > </script>
<script type="text/javascript" src="<%=info.getReqUrl()%>/chartscript/chartajax.js?v=<%=info.getJsVersion()%> " > </script>
<script type="text/javascript" src="<%=info.getReqUrl()%>/chartscript/serial.js?v=<%=info.getJsVersion()%> " > </script>
<script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/bse.js?v=<%=info.getJsVersion()%> " > </script>
</head>
<%
  String  key = (String)request.getParameter(("key"));
  String row = util.nvl((String)request.getParameter("row"));
  String col = util.nvl((String)request.getParameter("col")); 
  String url="genericReport.do?method=commonBarChart&key="+key+"&row="+row+"&col="+col;
  String style="width: 100%; height: 362px; float:left;";
  String styleId=key+"_HIDD";
  ArrayList statusLst = (ArrayList)session.getAttribute("statusLst");
    String barGrp=statusLst.toString();
    barGrp = barGrp.replaceAll("\\[", "");
    barGrp = barGrp.replaceAll("\\]", "");
    barGrp = barGrp.replaceAll("\\,", "_").trim();
    barGrp = barGrp.replaceAll(" ", "");
           String curKey=key;
           curKey=curKey.replaceAll("\\~","+"); 
           if(curKey.indexOf("@") > -1)
               curKey=curKey.replaceAll("\\#"," ");
           if(!row.equals("")){
               curKey+=" "+row;  
           }
           if(!col.equals(""))    
               curKey+=" "+col;  
        String logId=String.valueOf(info.getLogId());
        String onfocus="cook('"+logId+"')";
        %>
 <body onfocus="<%=onfocus%>">
  <div>
  <input type="hidden" id="<%=styleId%>" value="<%=key%>">
  <input type="hidden" id="barGrp" value="<%=barGrp%>">
  <div id="<%=key%>" style="<%=style%>"></div>
  </div>
<script type="text/javascript">
<!--
$(window).bind("load", function() {
callcreateMultipleBarGraph('<%=url%>','<%=curKey%>','50','362');
});
 -->
</script>  
  </body>
</html>