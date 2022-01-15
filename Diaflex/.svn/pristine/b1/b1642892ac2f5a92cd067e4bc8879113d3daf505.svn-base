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
<script type="text/javascript" src="<%=info.getReqUrl()%>/chartscript/pie.js?v=<%=info.getJsVersion()%> " > </script>
<script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/bse.js?v=<%=info.getJsVersion()%> " > </script>
</head>
<%
HashMap dataDtl = (HashMap)session.getAttribute("PIEdataDtl");
ArrayList grpLst=new ArrayList();
ArrayList deptLst=new ArrayList();
String memo=(String)request.getParameter("memo");
  String url="ajaxRptAction.do?method=creatememoPieChart";
  String style="width: 33%; height: 362px; float:left;";
        String logId=String.valueOf(info.getLogId());
        String onfocus="cook('"+logId+"')";
        %>
 <body onfocus="<%=onfocus%>">
  <%if(dataDtl!=null && dataDtl.size()>0){
  ArrayList prpLst=(ArrayList)dataDtl.get("PRO");
  for(int i=0;i<prpLst.size();i++){
  String prp=(String)prpLst.get(i);
  deptLst=new ArrayList();
  deptLst=(ArrayList)dataDtl.get(prp+"_DEPT");
  %>
  <div>
  <%
  for(int j=0;j<deptLst.size();j++){
  String dept=(String)deptLst.get(j);
  String styleId=prp+"_"+dept+"_HIDD";
  String styleval=prp+"_"+dept;
  %>
  <div>
  <input type="hidden" id="<%=styleId%>" value="<%=styleval%>">
  <div id="<%=styleval%>" style="<%=style%>">
  </div>
  </div>
  
  <%}%>
  </div>
  <%}}%>

<script type="text/javascript">
 <!--
$(window).bind("load", function() {
callcreatememoPieChart('<%=url%>',' Chart','50','362');
});
 -->
</script>  
  </body>
</html>