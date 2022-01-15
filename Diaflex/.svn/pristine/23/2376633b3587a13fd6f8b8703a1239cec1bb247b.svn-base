<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import="java.util.ArrayList,java.util.ArrayList,java.util.List, java.util.HashMap, java.sql.*, java.util.Enumeration, java.util.logging.Level"%>
	<jsp:useBean id="info" class="ft.com.InfoMgr" scope="session" />
	<jsp:useBean id="util" class="ft.com.JspUtil" scope="page" />


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
<script type="text/javascript" src="<%=info.getReqUrl()%>/chartscript/amcharts.js?v=<%=info.getJsVersion()%> " > </script>
<script type="text/javascript" src="<%=info.getReqUrl()%>/chartscript/chartajax.js?v=<%=info.getJsVersion()%> " > </script>
<script type="text/javascript" src="<%=info.getReqUrl()%>/chartscript/serial.js?v=<%=info.getJsVersion()%> " > </script>
</head>
<%
HashMap summaryList = (HashMap)session.getAttribute("summaryList");
HashMap summListdate = (HashMap)session.getAttribute("summListdate");
ArrayList weekList = (ArrayList)session.getAttribute("weekList");
 String typ = util.nvl(request.getParameter("typ"));
 String iosummary = (String)request.getAttribute("iosummary");
 ArrayList statusLst = (ArrayList)session.getAttribute("statusLst");
 String attbte="";
 int sttLstSize=statusLst.size();
 int inc=0;
        String logId=String.valueOf(info.getLogId());
        String onfocus="cook('"+logId+"')";
        %>
 <body onfocus="<%=onfocus%>">
  <%if(summaryList!=null && summaryList.size()>0){%>
  <table  border="0" align="left" class="Orange" cellspacing="0" cellpadding="0" width="100%" >
  <tr>
  <td class="memo" <%=attbte%> valign="top" width="10%">
  <table  border="0" align="left" class="Orange" style="width:450px;" id="ALL" cellspacing="0" cellpadding="0">
  <tr>
  <td style="background:white;"><input type="checkbox" name="AVGALL" id="AVGALL" value="" onclick="displayWeekAVG('AVGALL','AVG')"/>Avg Price &nbsp;
  <input type="checkbox" name="VLUALL" id="VLUALL" value="" onclick="displayWeekAVG('VLUALL','VLU')"/>Vlu</td>
  </tr>
  <tr>
    <th class="Orangeth" align="left">Week</th>
    <%for(int st=0;st<statusLst.size();st++){
  %>
 <th class="Orangeth" align="center" colspan="3"><%=statusLst.get(st)%></th>
  <%}%>
  </tr>
  <tr>
  <td class="Orangeth"></td>
  <%for(int st=0;st<statusLst.size();st++){
  String Stt=(String)statusLst.get(st);
  %>
 <td class="Orangeth" align="center">QTY</td>
 <td class="Orangeth" align="center"><span id="AVG_<%=st%>" style="display:none;">Avg Price</span></td>
 <td class="Orangeth" align="center"><span id="VLU_<%=st%>" style="display:none;">Vlu</span></td>
  <%}%>
  </tr>
   <%for(int j=0;j<weekList.size();j++){
    String week = (String)weekList.get(j);
    %>
    <tr>
    <td class="Orangeth"><%=util.nvl((String)summListdate.get(week))%></td>
    <%for(int st=0;st<statusLst.size();st++){
      String Stt=(String)statusLst.get(st);
      HashMap summDtl = (HashMap)summaryList.get(Stt+"_"+week);
      if(summDtl!=null && summDtl.size()>0){
      inc++;
      %>
      <td class="Orangeth" align="center"><%=summDtl.get("qty")%></td>
      <td class="Orangeth" align="center"><span style="display:none;" id="AVGStt_<%=inc%>"><%=summDtl.get("avg")%></span></td>
      <td class="Orangeth" align="center"><span style="display:none;" id="VLUStt_<%=inc%>"><%=summDtl.get("vlu")%></span></td>
      <%}else{%>
      <td class="Orangeth"></td>
      <td class="Orangeth"><span style="display:none;" id="AVGStt_<%=inc%>"></span></td>
      <td class="Orangeth"><span style="display:none;" id="VLUStt_<%=inc%>"></span></td>
      <%}}%>
    </tr>
    <%}%>
  </table>
  </td>
  </tr></table>
  <%  
  String url="ajaxRptAction.do?method=Weeksummary";
  String style="width: 100%; height: 362px; float:left;";
  String styleId="WEEK_HIDD";
    String barGrp=statusLst.toString();
    barGrp = barGrp.replaceAll("\\[", "");
    barGrp = barGrp.replaceAll("\\]", "");
    barGrp = barGrp.replaceAll("\\,", "_");
    barGrp = barGrp.replaceAll(" ", "");%>
  <div>
  <input type="hidden" id="<%=styleId%>" value="WEEK">
  <input type="hidden" id="barGrp" value="<%=barGrp%>">
  <div id="WEEK" style="<%=style%>"></div>
  </div>
<script type="text/javascript">
<!--
$(window).bind("load", function() {
callcreateMultipleBarGraph('<%=url%>','Week Wise Summary','50','362');
});
 -->
</script>  
  <%}else{%>
  <table><tr><td>Sorry No Result Found</td></tr></table>
  <%}%>
  </body>
</html>