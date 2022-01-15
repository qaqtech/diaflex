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
     <%
ArrayList  grpPrpList = (ArrayList)request.getAttribute("grpPrpList");
HashMap  openCloseDtl = (HashMap)request.getAttribute("openCloseDtl");
String openclose = util.nvl((String)request.getAttribute("openclose"));
String  view = util.nvl((String)request.getAttribute("view"),"Y");
ArrayList sttList=new ArrayList();
sttList.add("OPEN");
sttList.add("NEW");
sttList.add("PURNEW");
sttList.add("SOLD");
sttList.add("CLOSE");
HashMap prp = info.getPrp();
ArrayList prpVal = (ArrayList)prp.get(openclose+"V");
int sttListsz=sttList.size();
  %> 
  <title>Reports</title>
    <script language="javaScript" type="text/javascript" src="<%=info.getReqUrl()%>/scripts/ajax.js?v=<%=info.getJsVersion()%>"></script>
    <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/bse.css?v=<%=info.getJsVersion()%>"/>
    <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/style.css?v=<%=info.getJsVersion()%>"/>
    <script src="<%=info.getReqUrl()%>/jquery/jquery.min.js"></script>
        <link rel="stylesheet" href="<%=info.getReqUrl()%>/css/jScrollbar.jquery.css" type="text/css" />
	<script src="<%=info.getReqUrl()%>/jquery/jquery.mousewheel.js"></script>
	<script src="<%=info.getReqUrl()%>/jquery/jquery-ui-draggable.js"></script>
        <script src="<%=info.getReqUrl()%>/jquery/jscroll.js"></script>
        <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/bse.js?v=<%=info.getJsVersion()%> " > </script>
        <script type="text/javascript" src="<%=info.getReqUrl()%>/chartscript/amcharts.js?v=<%=info.getJsVersion()%> " > </script>
        <script type="text/javascript" src="<%=info.getReqUrl()%>/chartscript/chartajax.js?v=<%=info.getJsVersion()%> " > </script>
        <script type="text/javascript" src="<%=info.getReqUrl()%>/chartscript/pie.js?v=<%=info.getJsVersion()%> " > </script>
        <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/bse.js?v=<%=info.getJsVersion()%> " > </script>
  </head>
        <%String logId=String.valueOf(info.getLogId());
        String onfocus="cook('"+logId+"')";
        %>
 <body onfocus="<%=onfocus%>">
<table cellpadding="2" cellspacing="0" width="45%">
<tr><td valign="top"><b style="font-size:10px;">Open/Close</b></td></tr>
</table>
<table  border="0" align="left" cellspacing="0" cellpadding="0" width="42%" >
<%if(view.equals("Y")){
  if(grpPrpList!=null && grpPrpList.size()>0){
  %>
  <tr>
  <td>
  <table class="boardgrid"  border="0" align="left" width="100%">
  <tr>
  <th><%=openclose%></th>
  <%for(int i=0;i<sttList.size();i++){%>
  <th colspan="3" align="center"><%=util.nvl((String)sttList.get(i))%></th>
  <%}%>
  </tr>
  <tr>
  <td></td>
  <%for(int i=0;i<sttList.size();i++){%>
  <td align="center">QTY</td><td align="center">CTS</td><td align="center">VLU</td>
  <%}%>
  </tr>
  <%for(int i=0;i<prpVal.size();i++){
  String val=util.nvl((String)prpVal.get(i));
  if(grpPrpList.contains(val)){%>
  <tr>
  <th><%=val%></th>
  <%for(int j=0;j<sttListsz;j++){
  String stt=(String)sttList.get(j);%>
  <td align="right"><%=util.nvl((String)openCloseDtl.get(val+"_"+stt+"_CNT"),"-")%></td>
  <td align="right"><%=util.nvl((String)openCloseDtl.get(val+"_"+stt+"_CTS"),"-")%></td>
  <td align="right"><%=util.nvl((String)openCloseDtl.get(val+"_"+stt+"_VLU"),"-")%></td>
  <%}%>
  </tr>
  <%}%>
  <%}%>
  <tr>
  <th>Total</th>
  <%for(int j=0;j<sttListsz;j++){
  String stt=(String)sttList.get(j);%>
  <td align="right"><%=util.nvl((String)openCloseDtl.get("TTL_"+stt+"_CNT"),"-")%></td>
  <td align="right"><%=util.nvl((String)openCloseDtl.get("TTL_"+stt+"_CTS"),"-")%></td>
  <td align="right"><%=util.nvl((String)openCloseDtl.get("TTL_"+stt+"_VLU"),"-")%></td>
  <%}%>
  </tr>
  </table>
</td>
</tr>
  <%}else{%>
  <tr><td valign="top" class="hedPg">
Sorry No Result Found</td></tr>
  <%}}%>
  </table>
 </body>
</html>
  