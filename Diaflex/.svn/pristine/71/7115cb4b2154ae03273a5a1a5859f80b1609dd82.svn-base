<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page errorPage="../error_page.jsp" %>
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.ResultSet, java.util.ArrayList,java.util.Collections,java.util.ArrayList,java.util.HashMap,java.text.SimpleDateFormat, java.util.Calendar"%>
<jsp:useBean id="info" class="ft.com.InfoMgr" scope="session" />
<jsp:useBean id="util" class="ft.com.JspUtil" scope="page" />
<%@taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>
<%@taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<html> 
   <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/bse.js?v=<%=info.getJsVersion()%> " > </script>
<link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/bse.css?v=<%=info.getJsVersion()%>"/>
<link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/general.css?v=<%=info.getJsVersion()%>"/>
<script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/ajax.js?v=<%=info.getJsVersion()%> " > </script>
  <script type="text/javascript" src="<%=info.getReqUrl()%>/PopScripts/popup.js?v=<%=info.getJsVersion()%> " > </script>
    <script src="<%=info.getReqUrl()%>/PopScripts/jquery-1.2.6.min.js" type="text/javascript"></script>
   <script language="javaScript" type="text/javascript" src="<%=info.getReqUrl()%>/calendar/calendar.js"></script>
   <link href="<%=info.getReqUrl()%>/calendar/calendar.css" rel="stylesheet" type="text/css" />

 
      <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/style.css?v=<%=info.getJsVersion()%>"/>

  <title>Packet Rfid Box Allocation</title>
 
  </head>
  
  <%
    HashMap sttMap = (HashMap)request.getAttribute("sttMap");
  %>
        <%String logId=String.valueOf(info.getLogId());
        String onfocus="cook('"+logId+"')";
        %>
 <body onfocus="<%=onfocus%>" onload="MM_preloadImages('images/faunalogo.png')" topmargin="0" leftmargin="0" rightmargin="0" bottommargin="0">
<div id="backgroundPopup"></div>

<div id="popupContactSave" align="center" >
<img src="../images/loading_flame.gif" height="150px" width="150px" border="0" />
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
  <img src="../images/line.jpg" border="0" /></td><td valign="middle"> <span class="pgHed">Packet Rfid Box Allocation</span> </td>
</tr></table>
  </td>
  </tr>
  <%String msg = (String)request.getAttribute("msg");
  if(msg!=null){
  %>
  <tr><td valign="top"  class="tdLayout"><span class="redLabel"><%=msg%></span></td></tr>
  <%}%>
  <%String msg1 = (String)request.getAttribute("msg1");
  if(msg1!=null){
  %>
  <tr><td valign="top"  class="tdLayout"><span class="redLabel"><%=msg1%></span></td></tr>
  <%}%>
<tr><td valign="top" class="hedPg">
<html:form action="/rfid/pktrfidboxalloc.do?method=save"  method="POST" onsubmit="return validatepacketRfidBoxAlloc();">
  <html:hidden property="value(boxrfidval)" styleId="boxrfidval"/>
  <table  class="grid1">
   <tr>
   <th colspan="2" align="center">Assign</th>
   </tr>
    <tr>
    <td valign="top">Box Rfid<br/>
    <html:text property="value(boxrfid)" onchange="getBoxRfidVal();" styleId="boxrfid"/><br/>
    <button type="button" onclick="getBoxRfidVal();" Class="submit" >Get Box</button><br/>
    Assign To Box<br/>
    <span id="boxrfidvaldisplay"></span>
    </td><td>Packet Rfid<br/>
    <html:textarea property="value(val)" disabled="true" cols="25"  rows="8" styleId="prpVal"/>
    </td>
    </tr>
   <tr>
   <td colspan="2" align="center"><html:submit property="value(save)" value="Assign" styleClass="submit" />
   </td>
   </tr>
   </table>
</html:form>
</td>
</tr>
<% if(sttMap!=null){%>
<tr>
<td valign="top" class="hedPg">
<table  class="grid1">
<tr><th colspan="3" align="center">Allocation Summary</th></tr>
<tr>
<th>Total</th><th>Value Added</th><th>Value Updated</th>
</tr>

<tr>
<td><A href="<%=info.getReqUrl()%>/rfid/pktrfidboxalloc.do?method=SearchResult&flg="  target="pktDtl"><%=util.nvl((String)sttMap.get("T"))%></a></td>
<td><A href="<%=info.getReqUrl()%>/rfid/pktrfidboxalloc.do?method=SearchResult&flg=N"  target="pktDtl"><%=util.nvl((String)sttMap.get("N"))%></a></td>
<td><A href="<%=info.getReqUrl()%>/rfid/pktrfidboxalloc.do?method=SearchResult&flg=U" target="pktDtl"><%=util.nvl((String)sttMap.get("U"))%></a></td>
</tr>
</table>
</td>
</tr>
<%}%>
  <tr><td>
<jsp:include page="/footer.jsp" />
</td></tr>
  </table>  
  </body>
</html>