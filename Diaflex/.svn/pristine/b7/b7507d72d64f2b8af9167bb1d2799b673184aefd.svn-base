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
HashMap dbinfo = info.getDmbsInfoLst();
  HashMap mprp = info.getSrchMprp();
  HashMap prp = info.getSrchPrp();
  String  key = (String)request.getParameter(("key"));
    String row = util.nvl((String)request.getParameter("row"));
    String col = util.nvl((String)request.getParameter("col"));
    ArrayList statusLst = (ArrayList)session.getAttribute("statusLst");
    String  typ = (String)request.getParameter(("typ"));
    ArrayList rowList = (ArrayList)session.getAttribute("rowList");
    ArrayList colList  = (ArrayList)session.getAttribute("colList");
    String barGrp=statusLst.toString();
    barGrp = barGrp.replaceAll("\\[", "");
    barGrp = barGrp.replaceAll("\\]", "");
    barGrp = barGrp.replaceAll("\\,", "_").trim();
    barGrp = barGrp.replaceAll(" ", "");
  String url="ajaxRptAction.do?method=commonBarChartSelection&key="+key+"&row="+row+"&col="+col+"&typ="+typ;
  String style="width: 100%; height: 362px; float:left;";
  String styleId=key+"_HIDD";
  String curKey=key;
  curKey=curKey.replaceAll("\\~","+"); 
  if(curKey.indexOf("@") > -1)
  curKey=curKey.replaceAll("\\@"," ");
   String[] rowparam=null;
   String[] colparam=null;
   ArrayList rowListSelected = new ArrayList();
   ArrayList colListSelected  = new ArrayList();
   if(row.equals("ALL")){
   rowListSelected.addAll(rowList);
   rowListSelected.remove("ALL");
   rowListSelected.remove("ALL");
   }
   if(col.equals("ALL")){
   colListSelected.addAll(colList);
   colListSelected.remove("ALL");
   colListSelected.remove("ALL");
   }
   if(!row.equals("") && !row.equals("ALL")){
  rowparam = row.split(",");
  for(int k=0;k< rowparam.length;k++){
  String rowp=rowparam[k];
  rowListSelected.add(rowp);
  }
  }
   if(!col.equals("") && !col.equals("ALL")){
  colparam = col.split(",");
  for(int k=0;k< colparam.length;k++){
  String colp=colparam[k];
  colListSelected.add(colp);
  }}
    String rowSelected=rowListSelected.toString();
    rowSelected = rowSelected.replaceAll("\\[", "");
    rowSelected = rowSelected.replaceAll("\\]", "");
    rowSelected = rowSelected.replaceAll(" ", "");
    String colSelected=colListSelected.toString();
    colSelected = colSelected.replaceAll("\\[", "");
    colSelected = colSelected.replaceAll("\\]", "");
    colSelected = colSelected.replaceAll(" ", "");
    if(typ.equals("COL")){
    curKey+=" "+rowSelected;  
    }
    if(typ.equals("CLR")) {
    curKey+=" "+colSelected;  
    }
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