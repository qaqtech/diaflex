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
<script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/bse.js?v=<%=info.getJsVersion()%> " > </script>
<link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/bse.css?v=<%=info.getJsVersion()%>"/>
<script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/ajax.js?v=<%=info.getJsVersion()%> " > </script>
<link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/style.css?v=<%=info.getJsVersion()%>"/>
<link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/general.css?v=<%=info.getJsVersion()%>"/>
<script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/memoScript.js?v=<%=info.getJsVersion()%> " > </script>
<script type="text/javascript" src="<%=info.getReqUrl()%>/PopScripts/popup.js?v=<%=info.getJsVersion()%> " > </script>
 <link rel="stylesheet" media="screen" type="text/css" href="css/zoomimage.css" />
 <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/tableFilter/actb.js " > </script>    
    <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/jquery.js"></script>
   <script src="<%=info.getReqUrl()%>/PopScripts/jquery-1.2.6.min.js" type="text/javascript"></script>
   <script src="<%=info.getReqUrl()%>/PopScripts/jquery-1.2.6.min.js" type="text/javascript"></script>
<script type="text/javascript" src="<%=info.getReqUrl()%>/chartscript/amcharts.js?v=<%=info.getJsVersion()%> " > </script>
<script type="text/javascript" src="<%=info.getReqUrl()%>/chartscript/chartajax.js?v=<%=info.getJsVersion()%> " > </script>
<script type="text/javascript" src="<%=info.getReqUrl()%>/chartscript/serial.js?v=<%=info.getJsVersion()%> " > </script>
<script type="text/javascript" src="<%=info.getReqUrl()%>/chartscript/pie.js?v=<%=info.getJsVersion()%> " > </script>
</head>
<%
ArrayList AgeDtl = (ArrayList)session.getAttribute("AgeDtl");
  String  key = (String)request.getParameter(("key"));
  if(key.indexOf("~") > -1)
    key=key.replaceAll("\\~","+"); 
    String row = util.nvl((String)request.getParameter("row"));
    String col = util.nvl((String)request.getParameter("col"));
  String url="ajaxRptAction.do?method=callageGraph&key="+key+"&row="+row+"&col="+col;
  String style="width: 70%; height: 400px; float:left;";
  String pieUrl="ajaxRptAction.do?method=agepie&key="+key+"&row="+row+"&col="+col;
  HashMap mprp = info.getSrchMprp();
  HashMap prp = info.getSrchPrp();
            HashMap gridstructure = (HashMap)session.getAttribute("gridstructure");
            ArrayList gridcommLst=new ArrayList();
            ArrayList gridrowLst=new ArrayList();
            ArrayList gridcolLst=new ArrayList();
            gridcommLst=(ArrayList)gridstructure.get("COMM");
            gridrowLst=(ArrayList)gridstructure.get("ROW");
            gridcolLst=(ArrayList)gridstructure.get("COL");
            String rowDsc = (String)mprp.get(gridrowLst.get(0)+"D");
            String colDsc = (String)mprp.get(gridcolLst.get(0)+"D");
            String styleId=key+"_HIDD";
              String curKey=key;
            if(curKey.indexOf("_") > -1)
                curKey=curKey.replaceAll("\\_"," ");
            if(!curKey.contains("QTY")){
            if(!row.equals("") && !row.equals("ALL")){
                if(row.indexOf("'") > -1)
                    row=row.replaceAll("'", "");   
                curKey+=" "+rowDsc+" :"+row;   
            }
            if(!col.equals("") && !col.equals("ALL")) { 
                if(col.indexOf("'") > -1)
                    col=col.replaceAll("'", ""); 
                curKey+=" "+colDsc+" :"+col; 
            }
            }
%>
        <%String logId=String.valueOf(info.getLogId());
        String onfocus="cook('"+logId+"')";
        %>
 <body onfocus="<%=onfocus%>">
  <%if(AgeDtl!=null && AgeDtl.size()>0){%>
<div>
<div>
<div id="chartdiv" style="width: 1050px; height: 200px; margin-left: 38px;"></div>
<div id="chartdiv1" style="width: 1085px; height: 200px;"></div>
</div>
<div>
  <div id="chartdivPie" style="<%=style%>"></div>
</div>
</div>
<script type="text/javascript">
<!--
$(window).bind("load", function() {
callageGraph('<%=url%>','Ageing of Current Marketing <%=curKey%>','');
agepieChart('<%=pieUrl%>','Ageing Pie Chart of Current Marketing','chartdivPie');
});
 -->
</script>  
<%}else{%>
    <table><tr><td valign="top" class="hedPg">
    Sorry No Result Found</td></tr> </table>
<%}%>
  </body>
</html>