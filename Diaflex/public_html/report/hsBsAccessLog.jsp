<!DOCTYPE html>
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page errorPage="../error_page.jsp" %>
<%@ page import="java.util.ArrayList,java.util.ArrayList,java.util.HashMap, java.sql.*, java.util.Enumeration, java.util.logging.Level"%>
<jsp:useBean id="info" class="ft.com.InfoMgr" scope="session" />
<jsp:useBean id="util" class="ft.com.JspUtil" scope="page" />
<%@taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
 


<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
        <title>hsBsAccessLog</title>
         <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/bse.js?v=<%=info.getJsVersion()%> " > </script>
 <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/bse.css?v=<%=info.getJsVersion()%>"/>
 <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/ajax.js?v=<%=info.getJsVersion()%> " > </script>
  <script language="javaScript" type="text/javascript" src="<%=info.getReqUrl()%>/calendar/calendar.js"></script>
        <script type="text/javascript" src="<%=info.getReqUrl()%>/PopScripts/popup.js?v=<%=info.getJsVersion()%> " > </script>
   <link href="<%=info.getReqUrl()%>/calendar/calendar.css" rel="stylesheet" type="text/css">
    <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/style.css?v=<%=info.getJsVersion()%>"/>
    <script src="<%=info.getReqUrl()%>/PopScripts/jquery-1.2.6.min.js" type="text/javascript"></script>
    </head>
    <%
    String logId=String.valueOf(info.getLogId());
        String onfocus="cook('"+logId+"')";
 ArrayList hhbpfList =(ArrayList)request.getAttribute("hhbpfList");
 String toDte = (String)request.getAttribute("toDte");
 String fromDte =(String) request.getAttribute("fromDte");
 String reqUrl=info.getReqUrl();
        %>
 <body onfocus="<%=onfocus%>" onload="MM_preloadImages('images/faunalogo.png')" topmargin="0" leftmargin="0" rightmargin="0" bottommargin="0">

    <div id="backgroundPopup"></div>
<div id="popupContactASSFNL">
<table class="popUpTb">
 <tr><td height="5%">&nbsp;&nbsp;&nbsp;<html:button property="value(close)" styleClass="submit"  onclick="MKEmptyPopup('frame');disablePopupASSFNL()" value="Close"  /> </td></tr>
<tr><td valign="top" height="95%">
<iframe  name="frame" class="frameStyle" align="middle" frameborder="0">

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
  <img src="../images/line.jpg" border="0" /></td><td valign="middle"> <span class="pgHed">
Happy Hours and BPF Access Report</span> </td>
</tr></table>
  </td>
  </tr>
   <tr><td valign="top" class="hedPg"  >
    <html:form action="/report/orclRptAction.do?method=loadHhBsAccessLog" method="post" >
      <table>
       <tr><td colspan="2" align="center">
   <table><tr>
   <td>Date:</td><td> From : </td><td>
   <html:text property="value(frmdte)" name="orclReportForm" styleId="frmdte"/>
                <img src="<%=info.getReqUrl()%>/images/calender.png" onClick="setYears(2011, <%=info.getCurrentYear()%>);showCalender(this, 'frmdte');">
   </td><td>To : </td><td>
   <html:text property="value(todte)" name="orclReportForm" styleId="todte"/>
                <img src="<%=info.getReqUrl()%>/images/calender.png" onClick="setYears(2011, <%=info.getCurrentYear()%>);showCalender(this, 'todte');">

   </td>
   
   </tr></table></td></tr>
   <tr>
   <td colspan="2">
   <table><tr>
    <td > <html:checkbox property="value(hhStatus)" value="loadHH"    styleId="MS_2"  name="orclReportForm" /></td><td> Happy Hours</td>
     <td > <html:checkbox property="value(bpfStatus)" value="loadBPF"    styleId="MS_2" name="orclReportForm" /></td><td> BPF</td>
  </tr></table></td>
   </tr>
      <tr><td colspan="2" align="center"><html:submit property="value(srch)" value="Fetch" styleClass="submit" /></td> </tr>
      </table>
    </html:form>
   </td></tr>
   
   <tr>
  <td valign="top" class="hedPg" nowrap="nowrap">
  <% if(hhbpfList!=null && hhbpfList.size() >0){
  int count = 0;
  %>
  <table class="grid1" align="left" id="dataTable" style="margin-top:15px;">
 
    <tr>
    <th><label title="SR NO">Sr No.</label></th>
    <th><label title="Vendor">Buyer Name</label></th>
    <th><label title="Vendor">Count+</label></th>
    </tr>
    <% for (int i = 0; i< hhbpfList.size(); i++) { 
    HashMap hhbpfMap = (HashMap) hhbpfList.get(i);
    String cnt = (String) hhbpfMap.get("cnt");
    String pgtyp = (String) hhbpfMap.get("pgtyp");
    String byr = (String) hhbpfMap.get("byr");
    String usr_id = (String) hhbpfMap.get("usr_id");
    String nme_idn = (String) hhbpfMap.get("nme_idn");
    String hhBpsUrl = reqUrl+"/report/orclRptAction.do?method=fetchHsBsAccessDtl&usr_id="+usr_id+"&nme_idn="+nme_idn+"&fromDte="+fromDte+"&toDte="+toDte+"&pgtyp="+pgtyp;
    String target = "TR_"+count;
                    String targetSrc = "SRC_"+count;
                    String sltarget = "SL_"+count;
    %>
    <tr id="<%=target%>">
    

    <td><%= ++count%></td>
    <td><%=byr%></td>
    <td><A href="<%=hhBpsUrl%>" id="<%=sltarget%>"  target="frame" onclick="loadASSFNL('<%=target%>','<%=sltarget%>')"><span><%=cnt%></span></a></td>
</tr>
    
     <% } %>
     
   
  </table>
  
  <% } else {%>
  No Result Found
  <% } %>
  </td></tr>
  </table>
   <%@include file="../calendar.jsp"%>
    </body>
</html>