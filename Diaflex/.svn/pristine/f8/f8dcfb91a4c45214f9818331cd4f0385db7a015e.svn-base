<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page errorPage="../error_page.jsp" %>
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import="java.util.ArrayList,java.util.HashMap,java.sql.ResultSet,ft.com.*, java.util.Date, java.util.ArrayList, ft.com.dao.*"%>
<jsp:useBean id="util" class="ft.com.JspUtil" scope="page" />
<jsp:useBean id="info" class="ft.com.InfoMgr" scope="session" />
<%@taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>
<%@taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>Manufacturing Receive</title>
  <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/bse.css?v=<%=info.getJsVersion()%>"/>
    <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/style.css?v=<%=info.getJsVersion()%>"/>
    <script type="text/javascript" src="<%=info.getReqUrl()%>/PopScripts/popup.js?v=<%=info.getJsVersion()%> " > </script>
     <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/bse.js?v=<%=info.getJsVersion()%> " > </script>
     <script language="javaScript" type="text/javascript" src="<%=info.getReqUrl()%>/calendar/calendar.js"></script>
   <link href="<%=info.getReqUrl()%>/calendar/calendar.css" rel="stylesheet" type="text/css" />
    <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/general.css?v=<%=info.getJsVersion()%>"/>
    <script src="<%=info.getReqUrl()%>/PopScripts/jquery-1.2.6.min.js" type="text/javascript"></script>
  </head>
        <%String logId=String.valueOf(info.getLogId());
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
    <td height="5" valign="top" class="greyline"><img src="<%=info.getReqUrl()%>/images/grey_linebig.png" width="685" height="5" align="right" /></td>
  </tr>
  <tr>
  <td valign="top" class="hedPg">
  <table cellpadding="1" cellspacing="5"><tr><td valign="middle">
  <img src="../images/line.jpg" border="0" /></td><td valign="middle"> <span class="pgHed">
 Manufacturing Receive
 </span> </td>
</tr></table>
  </td>
  </tr>
  <%
   ArrayList msgLst = (ArrayList)request.getAttribute("msgList");
   if(msgLst!=null){%>
   <tr><td valign="top" class="tdLayout">
   <%for(int i=0;i<msgLst.size();i++){
   ArrayList msg = (ArrayList)msgLst.get(i);
   %>
    <span class="redLabel"><%=msg.get(1)%>,</span>
   <%}%>
   </td></tr>
   <%}%>
  <tr><td class="tdLayout" valign="top">
  <table>
  <%ArrayList stockList=(ArrayList)session.getAttribute("StockList");
  int stockListsz=stockList.size();
  HashMap pktPrpMap = new HashMap();
  int sr=0;
  if(stockListsz>0){
  %>
  <tr><td valign="top"><table class="grid1">
  <html:form action="fileloaders/mfgrecive.do?method=saveSheet" method="post" enctype="multipart/form-data">
  <tr><td colspan="5"><html:submit property="value(save)" value="Sheet Receive" styleClass="submit"/></td></tr>
  <tr><th><input type="checkbox" name="checkAll" id="checkAll" onclick="checkALL('CHK_','count')" /> </th>
  <th>Sr No</th><th>Qty</th><th>Send Date</th><th>Sheet No</th></tr>
  <%for(int i=0;i<stockListsz;i++){
  pktPrpMap = new HashMap();
  pktPrpMap=(HashMap)stockList.get(i);
   sr = i+1;
 String sheetno = util.nvl((String)pktPrpMap.get("SHEET"));
 String checkFldId = "CHK_"+sr;
 String checkFldVal = "value("+sheetno+")";
 String link1="/fileloaders/mfgrecive.do?method=loadPKT&sheet="+ sheetno;
  %>
  <tr>
  <td><html:checkbox property="<%=checkFldVal%>" styleId="<%=checkFldId%>" name="fileUploadForm" value="yes" /> </td>
  <td align="right"><%=sr%></td>
  <td align="right"><%=util.nvl((String)pktPrpMap.get("QTY"))%></td>
  <td align="right"><%=util.nvl((String)pktPrpMap.get("SDTE"))%></td>
  <td align="right"><html:link page="<%=link1%>" target="frame" title="Click Here To See Details"><%=sheetno%></html:link></td>
  </tr>
  <%}%>
  <input type="hidden" name="count" id="count" value="<%=sr%>" />
  </html:form>
  </table>
  </td>
  <td valign="top">
  <iframe name="frame" height="1000" width="800" align="left" frameborder="0" >
  </iframe>
  </td>
  </tr>
  <%}%>
  </table>
  </td>
  </tr>
  <tr>
    <td><jsp:include page="/footer.jsp" /> </td>
    </tr>
  </table>
   <%@include file="../calendar.jsp"%>
  </body></html>