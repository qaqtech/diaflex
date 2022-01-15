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

  <title>Move To Pending Registration</title>
 
  </head>

        <%String logId=String.valueOf(info.getLogId());
        String onfocus="cook('"+logId+"')";
        %>
 <body onfocus="<%=onfocus%>" onload="MM_preloadImages('images/faunalogo.png')" topmargin="0" leftmargin="0" rightmargin="0" bottommargin="0">
 
  <input type="hidden" id="reqUrl" name="reqUrl" value="<%=info.getReqUrl()%>"/>
<div id="popupContactASSFNL">
<table class="popUpTb">
<tr><td height="5%">&nbsp;&nbsp;&nbsp;<html:button property="value(close)" styleClass="submit"  onclick="MKEmptyPopup('SC');disablePopupASSFNL();" value="Close"  /></td></tr>
<tr><td valign="top" height="95%">
<iframe name="SC" class="frameStyle"   align="middle" frameborder="0">
</iframe></td></tr>
</table>
</div>
<div id="backgroundPopup"></div>

 
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
  <img src="../images/line.jpg" border="0" /></td><td valign="middle"> <span class="pgHed">Move To Pending Registration</span> </td>
</tr></table>
  </td>
  </tr>
   <%String msg = (String)request.getAttribute("rtnmsg");
  if(msg!=null){
  %>
  <tr><td valign="top" class="tdLayout"> <span class="redLabel" > <%=msg%></span></td></tr>
  <%}%>
  <%
ArrayList  paLst= (session.getAttribute("rejLst") == null)?new ArrayList():(ArrayList)session.getAttribute("rejLst");
int paLstsz=paLst.size();
if(paLstsz>0){%>
<html:form action="/website/AcceptRegistration.do?method=savereject"  method="post" onsubmit="loading()">
<tr>
<td valign="top" class="hedPg"><html:submit property="value(save)" value="Move To Pending Registration" styleClass="submit" />&nbsp;&nbsp;
<html:submit property="value(delete)" value="Move To Delete" styleClass="submit" /></td>
</tr>
<tr>
<td valign="top" class="hedPg">
<table class="grid1">
<tr>
<th><input type="checkbox" name="all" id="all" onclick="checkAllpage(this,'stt_')"/></th>
<th>User Name</th>
<th>Name</th>
<th>Company Name</th>
<th>Address</th>
<th>Telephone</th>
<th>Mobile</th>
<th>Email</th>
<th>Biz</th>
<th>Registration Date</th>
</tr>
<%for(int i=0;i<paLstsz;i++){
HashMap paDtl=(HashMap)paLst.get(i);
String reg_id=util.nvl((String)paDtl.get("reg_id"));
String chkFld = "value(stt_" + reg_id + ")" ;
String chkFldId = "stt_"+reg_id;
%>
<tr>
<td>
<html:checkbox property="<%=chkFld%>" styleId="<%=chkFldId%>" name="pendingRegForm" value="Y"></html:checkbox>
</td>
<td nowrap="nowrap"><%=util.nvl((String)paDtl.get("usr"))%></td>
<td nowrap="nowrap"><%=util.nvl((String)paDtl.get("name"))%></td>
<td nowrap="nowrap"><%=util.nvl((String)paDtl.get("co_nme"))%></td>
<td nowrap="nowrap"><%=util.nvl((String)paDtl.get("address"))%></td>
<td nowrap="nowrap"><%=util.nvl((String)paDtl.get("tel"))%></td>
<td nowrap="nowrap"><%=util.nvl((String)paDtl.get("mbl"))%></td>
<td nowrap="nowrap"><%=util.nvl((String)paDtl.get("eml"))%></td>
<td nowrap="nowrap"><%=util.nvl((String)paDtl.get("biz"))%></td>
<td nowrap="nowrap"><%=util.nvl((String)paDtl.get("dt_tm"))%></td>
</tr>
<%}%>
</table>
</td>
</tr>
</html:form>
<%}else{%>
<tr><td valign="top" class="hedPg">
Sorry No Result Found</td></tr>
<%}%>
  <tr><td>
<jsp:include page="/footer.jsp" />
</td></tr>
  </table>  
       <%@include file="../calendar.jsp"%>
  </body>
</html>