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

  <title>Pending Approve</title>
 
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
  <img src="../images/line.jpg" border="0" /></td><td valign="middle"> <span class="pgHed">Pending Approve</span> </td>
</tr></table>
  </td>
  </tr>
   <%String msg = (String)request.getAttribute("rtnmsg");
  if(msg!=null){
  %>
  <tr><td valign="top" class="tdLayout"> <span class="redLabel" > <%=msg%></span></td></tr>
  <%}%>
  <%
ArrayList  paLst= (session.getAttribute("paLst") == null)?new ArrayList():(ArrayList)session.getAttribute("paLst");
int paLstsz=paLst.size();
if(paLstsz>0){%>
<html:form action="/contact/advcontact.do?method=savepa"  method="post" onsubmit="loading()">
<tr>
<td valign="top" class="hedPg"><html:submit property="value(save)" value="Approve" styleClass="submit" onclick="return validateUpdate()" /></td>
</tr>
<tr>
<td valign="top" class="hedPg">
<table class="grid1">
<tr>
<th><input type="checkbox" name="all" id="all" onclick="checkAllpage(this,'stt_')"/></th>
<th>Prefix</th>
<th>Company / Individual</th>
<th>Middle Name</th>
<th>Primary Contact</th>
<th>Group Company</th>
<th>Employee</th>
<th>From Date</th>
<th>Edit</th>
<th>Complete Profile</th>
</tr>
<%for(int i=0;i<paLstsz;i++){
HashMap paDtl=(HashMap)paLst.get(i);
String nme_idn=util.nvl((String)paDtl.get("nme_idn"));
String edtLnk = "<a target=\"fix\" href=\""+ info.getReqUrl() + "/contact/nme.do?method=load&frompa=Y&fromFeedback=Y&nmeIdn="+ nme_idn + "\">View / Edit</a>";
String chkFld = "value(stt_" + nme_idn + ")" ;
String chkFldId = "stt_"+nme_idn;
%>
<tr>
<td>
<html:checkbox property="<%=chkFld%>" styleId="<%=chkFldId%>" name="advContactForm" value="Y"></html:checkbox>
</td>
<td nowrap="nowrap"><%=util.nvl((String)paDtl.get("pfx"))%></td>
<td nowrap="nowrap"><%=util.nvl((String)paDtl.get("fnme"))%></td>
<td nowrap="nowrap"><%=util.nvl((String)paDtl.get("mnme"))%></td>
<td nowrap="nowrap"><%=util.nvl((String)paDtl.get("lnme"))%></td>
<td nowrap="nowrap"><%=util.nvl((String)paDtl.get("grp"))%></td>
<td nowrap="nowrap"><%=util.nvl((String)paDtl.get("emp"))%></td>
<td nowrap="nowrap"><%=util.nvl((String)paDtl.get("frm_dte"))%></td>
<td nowrap="nowrap"><%=edtLnk%></td>
<td><a href="<%=info.getReqUrl()%>/contact/NmeReport.jsp?view=Y&nmeIdn=<%=nme_idn%>" target="fix1" >Complete Profile</a></td>
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