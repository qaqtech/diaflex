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
  <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
  <title>Daily Lab Issue Receive</title>
  <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/bse.js?v=<%=info.getJsVersion()%> " > </script>
  <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/bse.css?v=<%=info.getJsVersion()%>"/>
  <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/ajax.js?v=<%=info.getJsVersion()%> " > </script>
  <script language="javaScript" type="text/javascript" src="<%=info.getReqUrl()%>/calendar/calendar.js"></script>
  <link href="<%=info.getReqUrl()%>/calendar/calendar.css" rel="stylesheet" type="text/css" />
  <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/style.css?v=<%=info.getJsVersion()%>"/>
  </head>
        <%String logId=String.valueOf(info.getLogId());
        String onfocus="cook('"+logId+"')";
        %>
 <body onfocus="<%=onfocus%>" onload="MM_preloadImages('images/faunalogo.png')" topmargin="0" leftmargin="0" rightmargin="0" bottommargin="0">
<%

        HashMap prp = info.getPrp();
        ArrayList deptVLst = (ArrayList)prp.get("DEPTV");
        ArrayList deptSLst = (ArrayList)prp.get("DEPTS");
%>

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
  Daily Lab Issue Receive </span> </td>
</tr></table>
  </td>
  </tr>
   <tr>
  <td valign="top" class="hedPg">
  <html:form action="report/dailyLabIssueReceive.do?method=fetch" method="post">
  <table cellpadding="2" cellspacing="2">
  <tr><td>Type : </td><td><html:radio property="value(typ)" value="IS" name="dailyLabIssueReceiveForm"/> Issue </td> 
  <td><html:radio property="value(typ)" value="RT" name="dailyLabIssueReceiveForm" /> Return </td> </tr>
  <tr><td>Dept : </td><td colspan="2"> 
  <html:select property="value(dept)" name="dailyLabIssueReceiveForm" >
  <%for(int i=0;i<deptVLst.size();i++){
  String deptV = (String)deptVLst.get(i);
  String deptS = (String)deptSLst.get(i);
  %>
  <html:option value="<%=deptV%>" ><%=deptV%></html:option>
  <%}%>
  </html:select>
  </td> </tr>
  <tr><td>Date : </td><td> 
  <div class="float">   <html:text property="value(frmdate)"  styleId="frmdate" name="dailyLabIssueReceiveForm"/></div>
   <div class="float">   <a href="#"  onClick="setYears(1900, <%=info.getCurrentYear()%>);showCalender(this, 'frmdate');">
                <img src="<%=info.getReqUrl()%>/images/calender.png"></a></div> </td>
        <td> 
  <div class="float">   <html:text property="value(todate)"  styleId="todate" name="dailyLabIssueReceiveForm"/></div>
   <div class="float">   <a href="#"  onClick="setYears(1900, <%=info.getCurrentYear()%>);showCalender(this, 'todate');">
                <img src="<%=info.getReqUrl()%>/images/calender.png"></a></div> </td>
                        
                
                </tr>
    <tr><td colspan="3" align="center"><html:submit property="value(submit)" value="Fecth" styleClass="submit" /> </td></tr>
  </table>
   </html:form>
   </td></tr>
   <tr>
   <td valign="top" class="hedPg" height="20"></td></tr>
    <tr>
   <td valign="top" class="hedPg">
   <%
    String view = util.nvl((String)request.getAttribute("view"));
   ArrayList labList = (ArrayList)request.getAttribute("labList");
   HashMap pktDtlMap = (HashMap)request.getAttribute("pktDtlMap");
   int sr =0;
   if(labList!=null && labList.size()>0 && !view.equals("")){
     ArrayList srtLabList = (ArrayList)prp.get("LABV");%>
     <table  class="grid1">
     <tr><th colspan="6"><b><bean:write property="value(HED)" name="dailyLabIssueReceiveForm" /></b></th></tr>
     <tr><td align="center"><B>Sr No</b></td><td align="center"><B>Lab</b></td><td align="center"><B>Pcs</b></td>
     <td align="center"><b>Cts</b></td><td align="center"><B>Avg Rte</b></td><td align="center"><B>Amount</b></td></tr>
    <%
     for(int j=0;j<srtLabList.size();j++){
     String lab = (String)srtLabList.get(j);
     if(labList.contains(lab)){
     sr=sr+1;
    %>
   <tr><td align="right"><%=sr%></td><td><%=lab%></td><td align="right"><%=pktDtlMap.get(lab+"_CNT")%></td>
   <td align="right"><%=pktDtlMap.get(lab+"_CTS")%></td><td align="right"><%=pktDtlMap.get(lab+"_AVG")%></td>
   <td align="right"><%=pktDtlMap.get(lab+"_AMT")%></td>
   </tr>
  <%}}%>
  <tr><td colspan="2" align="center"><b>Total</b></td><td align="right"><%=pktDtlMap.get("TTLCNT")%></td>
   <td align="right"><%=pktDtlMap.get("TTLCTS")%></td><td align="right"><%=pktDtlMap.get("TTLAVG")%></td>
   <td align="right"><%=pktDtlMap.get("TTLAMT")%></td>
   </tr>
  </table>
  <%}%>
   </td></tr>
  
  </table>
   <%@include file="../calendar.jsp"%>
  </body>
</html>