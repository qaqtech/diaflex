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
ArrayList statusLst = (ArrayList)session.getAttribute("statusLst");
  HashMap mprp = info.getSrchMprp();
  HashMap prp = info.getSrchPrp();
  String  key = (String)request.getParameter(("key"));
    String row = util.nvl((String)request.getParameter("row"));
    String col = util.nvl((String)request.getParameter("col"));
  HashMap dataDtl = (HashMap)session.getAttribute("dataDtl");
   HashMap gridstructure = (HashMap)session.getAttribute("gridstructure");
    ArrayList gridcommLst=new ArrayList();
    ArrayList gridrowLst=new ArrayList();
    ArrayList gridcolLst=new ArrayList();
    gridcommLst=(ArrayList)gridstructure.get("COMM");
    gridrowLst=(ArrayList)gridstructure.get("ROW");
    gridcolLst=(ArrayList)gridstructure.get("COL");
  ArrayList rowList = (ArrayList)prp.get(gridrowLst.get(0)+"V");
  ArrayList colList = (ArrayList)prp.get(gridcolLst.get(0)+"V");
  String title="";
    if(row.equals("")) {
        title=" Chart By ";
        String prpDsc = util.nvl((String)mprp.get(gridrowLst.get(0)+"D"));
        title+=prpDsc;
  }else{
    title=" Chart By ";
    String prpDsc = util.nvl((String)mprp.get(gridcolLst.get(0)+"D"));
    title+=prpDsc;
  }
  String url="genericReport.do?method=commonpieChart&key="+key+"&row="+row+"&col="+col;
  String style="width: 50%; height: 362px; float:left;";
%>
        <%String logId=String.valueOf(info.getLogId());
        String onfocus="cook('"+logId+"')";
        %>
 <body onfocus="<%=onfocus%>">
  <%
  for(int i=0;i< statusLst.size();i++){
  String stt=(String)statusLst.get(i);
  String styleId=stt+"_DIV";
  int setting=i%2;
  if(setting==0)
  style="width: 50%; height: 362px; float:left;";
  else
  style="width: 50%; height: 362px; float:right;";
  %>
  <div>
    <input type="hidden" id="<%=styleId%>" value="<%=stt%>">
  <div id="<%=stt%>" style="<%=style%>">
  
  </div>
  </div>
  <%}%>
<script type="text/javascript">
 <!--
$(window).bind("load", function() {
callcommonpieChart('<%=url%>','<%=title%>','50','362');
});
 -->
</script>  
  </body>
</html>