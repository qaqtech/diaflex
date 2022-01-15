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

  <title>Manage Pair</title>
 
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
  <img src="../images/line.jpg" border="0" /></td><td valign="middle"> <span class="pgHed">Manage Pair</span> </td>
     <%
        if(request.getAttribute("rtnMsg")!=null){%>
        <td height="15"><span class="redLabel"> <%=request.getAttribute("rtnMsg")%></span></td>
       <% }
        %>
</tr></table>
  </td>
  </tr>
<tr>
<td valign="top" class="hedPg">
<html:form action="/marketing/managepair.do?method=generate"  method="POST">
<html:submit property="value(resetall)"  onclick="" value="Reset All" styleClass="submit"/>
<html:submit property="value(loadall)"  onclick="" value="Load All" styleClass="submit"/>
</html:form>
</td>
</tr>
<tr><td valign="top" class="hedPg">
<html:form action="/marketing/managepair.do?method=generate"  method="POST">
<table class="grid1">
<tr><th colspan="3">Exceptional Pairs</th></tr>
<tr>
<td>
  <table  class="grid1">
             <tr>
             <td colspan="3"><table><tr>
            <td>Finish No 1<br/>
            <html:textarea property="value(vnmLst1)" cols="25" rows="2" styleId="vnmLst1"/>
            </td><td>Finish No 2<br/>
           <html:textarea property="value(vnmLst2)" cols="25"  rows="2" styleId="vnmLst2"/>
            </td>
            </tr></table></td></tr>
   </table>
</td>
</tr>
<tr><td colspan="3" align="center"><html:submit property="value(generate)"  onclick="" value="Generate" styleClass="submit"/></td></tr>
</table>
</html:form>
</td>
</tr>
  <%
  int sr=0;
  ArrayList pktDtlList = (ArrayList)session.getAttribute("PktListManagePair");
  if(pktDtlList!=null && pktDtlList.size()>0){
  ArrayList itemHdr = (ArrayList)session.getAttribute("itemHdrManagePair");%>
 <tr>
 <td><table>
 <html:form action="marketing/managepair.do?method=generate" method="post" onsubmit="return validate_issue('CHK_' , 'countCnt')">
 <tr>
 <td valign="top" class="hedPg">   
 <html:submit property="value(remove)"  onclick="" value="Remove" styleClass="submit"/>
  Create Excel &nbsp;&nbsp;&nbsp;
  <img src="../images/ico_file_excel.png" border="0" onclick="goTo('<%=info.getReqUrl()%>/marketing/managepair.do?method=createXL','','')"/>
 </td>
 </tr>
  <tr><td valign="top" class="hedPg">
  <table class="grid1">
  <thead>
  <tr>
  <th><input type="checkbox" name="all" id="all" onclick="checkAllpage(this,'CHK_')"/></th><th>Sr</th>
  
  <%for(int i=0 ; i < itemHdr.size() ;i++){%>
  <th><%=itemHdr.get(i)%></th>
  <%}%>
  </tr>
  </thead>
  <tbody>
  <%for(int j=0 ; j <pktDtlList.size() ;j++){
  HashMap pktDtl = (HashMap)pktDtlList.get(j);
  String pair_id=util.nvl((String)pktDtl.get("PAIR_ID"));
  String checkFldId = "CHK_"+sr;
  String checkFldVal = "cb_stk_"+pair_id ;
  %>
  <tr>
  <%if((sr%2)!=0){
    sr = j+1;%>
  <td><input type="checkbox" name="<%=checkFldVal%>" id="<%=checkFldId%>" value="<%=pair_id%>" /></td>
  <td align="right"><%=sr%></td>
  <%}else{%>
  <td></td><td></td>
  <%}%>
  <%for(int k=0;k<itemHdr.size() ;k++){
  String hdr = (String)itemHdr.get(k);
  
  %>
  <td align="right"><%=util.nvl((String)pktDtl.get(hdr))%></td>
  <%}%>
  </tr>
  <%}%>
  </tbody>
  </table>
  <input type="hidden" name="countCnt" id="countCnt" value="<%=sr%>" />
  </td></tr>
  </html:form>
  </table>
  <%}%>
  <tr><td>
<jsp:include page="/footer.jsp" />
</td></tr>
  </table>  
       <%@include file="../calendar.jsp"%>
  </body>
</html>