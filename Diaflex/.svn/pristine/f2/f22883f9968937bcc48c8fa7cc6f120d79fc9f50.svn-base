 <!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
 
<%@ page errorPage="../error_page.jsp" %>
<%@ page contentType="text/html;charset=windows-1252"%>
<jsp:useBean id="util" class="ft.com.JspUtil" scope="page" />
<jsp:useBean id="info" class="ft.com.InfoMgr" scope="session" />
<%@ page import="java.util.ArrayList,java.util.HashMap,java.sql.ResultSet,ft.com.*, java.util.Date, java.util.ArrayList, ft.com.dao.*"%>
<%@taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>Stock Tally</title>
    <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/bse.css?v=<%=info.getJsVersion()%>"/>
    <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/general.css?v=<%=info.getJsVersion()%>"/>
     <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/style.css?v=<%=info.getJsVersion()%>"/>
      <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/bse.js?v=<%=info.getJsVersion()%> " > </script>
  <script type="text/javascript" src="<%=info.getReqUrl()%>/chartscript/amcharts.js?v=<%=info.getJsVersion()%> " > </script>
<script type="text/javascript" src="<%=info.getReqUrl()%>/chartscript/chartajax.js?v=<%=info.getJsVersion()%> " > </script>
<script type="text/javascript" src="<%=info.getReqUrl()%>/chartscript/serial.js?v=<%=info.getJsVersion()%> " > </script>
  </head>
        <%
        ArrayList seqList = (ArrayList)session.getAttribute("seqLst");
        String logId=String.valueOf(info.getLogId());
        String onfocus="cook('"+logId+"')";
        %>
 <body onfocus="<%=onfocus%>" onload="MM_preloadImages('images/faunalogo.png')" topmargin="0" leftmargin="0" rightmargin="0" bottommargin="0">
<div id="backgroundPopup"></div>
<div id="popupContactASSFNL">
<table class="popUpTb">
 <tr><td height="5%" id="close">&nbsp;&nbsp;&nbsp;<html:button property="value(close)" styleClass="submit"  onclick="MKEmptyPopup('SC');disablePopupASSFNL()" value="Close"  /> </td>
  <td height="5%" id="closereload" style="display:none">&nbsp;&nbsp;&nbsp;<html:button property="value(close)" styleClass="submit"  onclick="MKEmptyPopup('SC');disablePopupASSFNL();reportUploadclose('TALLYHK')" value="Close"  /> </td></tr>
<tr><td valign="top" height="95%">
<iframe name="SC" class="frameStyle"   align="middle" frameborder="0">

</iframe></td></tr>
</table>
</div>
<input type="hidden" id="reqUrl" name="reqUrl" value="<%=info.getReqUrl()%>"/>
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
  <img src="../images/line.jpg" border="0" /></td><td valign="middle"> <span class="pgHed">Stock Tally Setup</span> </td>
</tr></table>
  </td>
  </tr>
  <%
  if(request.getAttribute("msg")!=null){%>
   <tr> <td valign="top" class="tdLayout"><%=request.getAttribute("msg")%></td></tr>
  <%}%>
    <%if(request.getAttribute("seqMsg")!=null){
    if(seqList!=null && seqList.size()>0){%>
   <tr> <td valign="top" class="tdLayout"><span class="redLabel"> Sequence No :<%=seqList.get(0)%> </span></td></tr>
  <%}}%>
   <tr> <td valign="top" class="tdLayout">
   <table><tr><td>
   <html:form action="marketing/stockTallyhk.do?method=savesetupseq" method="post" >
  <table  class="grid1">
  <tr><th colspan="2">Setup</th></tr>
  <tr><td align="center">Sequence</td><td align="center">
  <%  ArrayList seqLst = (ArrayList)request.getAttribute("seqLst");
  if(seqLst!=null && seqLst.size()>0){
  
  %>
  <html:select property="value(seq)" name="stockTallyForm">
  <html:option value="" >ALL</html:option>
 <% for(int n=0;n<seqLst.size();n++){
  String seq = (String)seqLst.get(n);
   %>
   <html:option value="<%=seq%>" ><%=seq%></html:option>
  <%}%>
 </html:select>
  <%}%>
  </td> </tr>
   <tr><td colspan="2" align="center"><html:submit property="value(srch)" value="Setup" styleClass="submit" /></td> </tr>
   </table>
   </html:form>
   </td></tr>
   </table>
  </td></tr>
  </table>
  </body>
</html>