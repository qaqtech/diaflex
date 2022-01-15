<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page errorPage="../error_page.jsp" %>
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import="java.util.ArrayList,java.util.ArrayList,java.util.HashMap, java.sql.*, java.util.Enumeration, java.util.logging.Level"%>
<jsp:useBean id="info" class="ft.com.InfoMgr" scope="session" />
<jsp:useBean id="util" class="ft.com.JspUtil" scope="page" />
<%@taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
 
<html>
  <head>
 <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
  
  <title>Allocation Report</title>
  <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/bse.js?v=<%=info.getJsVersion()%> " > </script>
 <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/bse.css?v=<%=info.getJsVersion()%>"/>
 <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/ajax.js?v=<%=info.getJsVersion()%> " > </script>
  <script language="javaScript" type="text/javascript" src="<%=info.getReqUrl()%>/calendar/calendar.js"></script>
   <link href="<%=info.getReqUrl()%>/calendar/calendar.css" rel="stylesheet" type="text/css">
    <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/style.css?v=<%=info.getJsVersion()%>"/>
  </head>
 
   <%
            HashMap dbinfo = info.getDmbsInfoLst();
         String cnt = (String)dbinfo.get("CNT");
        String logId=String.valueOf(info.getLogId());
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
    <td height="5" valign="top" class="greyline"><img src="../images/grey_linebig.png" width="685" height="5" align="right" /></td>
  </tr>
  <tr>
  <td valign="top" class="hedPg">
  <table cellpadding="1" cellspacing="5"><tr><td valign="middle">
  <img src="../images/line.jpg" border="0" /></td><td valign="middle"> <span class="pgHed">
 Allocation Report</span> </td>
</tr></table>
  </td>
  </tr>
  <%
   String msg = (String)request.getAttribute("msg");
   if(msg!=null){%>
     <tr><td valign="top" class="tdLayout"><%=msg%></td></tr>
   <%}%>
  
   <tr><td valign="top" class="hedPg"  >
    <html:form action="/report/orclRptAction?method=ppAlcReport" method="post"  >
    <table>
    <tr><td>Date : </td>  
       <td><html:text property="value(frmdte)" styleId="frmdte" name="orclReportForm" size="10" /><img src="<%=info.getReqUrl()%>/images/calender.png" onClick="setYears(2011, <%=info.getCurrentYear()%>);showCalender(this, 'frmdte');"> 
       To&nbsp; <html:text property="value(todte)" styleId="todte" name="orclReportForm"  size="10"/> <img src="<%=info.getReqUrl()%>/images/calender.png" onClick="setYears(2011, <%=info.getCurrentYear()%>);showCalender(this, 'todte');"></td>
      </tr>
   <tr>
   <td>Packet:</td><td> <html:textarea property="value(vnmLst)" styleId="vnm" rows="2"  cols="20" name="orclReportForm"  /></td>
   </tr>
  <tr><td>Report Type</td><td align="center">
  <html:select  property="value(alcstt)" name="orclReportForm" > 
   <html:option value="ALC" >Allocated</html:option> 
   <html:option value="ALL" >Detail</html:option> 
  </html:select>
  </td></tr>
  <tr><td>Type</td><td align="center">
  <html:select  property="value(typ)" name="orclReportForm" > 
   <html:option value="ALL" >--Select--</html:option> 
   <html:option value="PEP" >Offer</html:option> 
   <html:option value="BID" >Bid</html:option> 
   <%if(cnt.equals("kj")){%>
   <html:option value="KS" >E-Bid Prime</html:option> 
   <html:option value="KO" >PBB</html:option>
   <%}%>
  </html:select>
  </td></tr>
    <tr><td colspan="2" align="center"><html:submit property="value(srch)" value="Fetch" styleClass="submit" /></td> </tr>
  
   </table>
 </html:form>
   </td></tr>
   <%
   String view  = (String)request.getAttribute("view");
   if(view!=null){
   ArrayList pktList = (ArrayList)session.getAttribute("pktListPPC");
   ArrayList itemHdr = (ArrayList)session.getAttribute("itemHdrPPC");
   if(pktList!=null && pktList.size()>0){
   HashMap pktPrpMap = new HashMap();
   %>
  <tr>
  <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Create Excel <img src="../images/ico_file_excel.png" onclick="goTo('orclRptAction.do?method=createPpcXL','','')" border="0"/> 
  </td>
  </tr>
   <tr>
   <td valign="top" class="tdLayout">
   <table  class="grid1" align="left">
        <tr>
        <th>Sr No.</th>
        <%for(int i=0;i<itemHdr.size();i++){%>
        <th><%=itemHdr.get(i)%></th>
        <%}%>
        </tr>
        <%for(int j=0;j<pktList.size();j++){
        pktPrpMap=(HashMap)pktList.get(j);%>
        <tr>
        <td nowrap="nowrap" align="right"><%=(j+1)%></td>
        <%
        for(int i=0;i<itemHdr.size();i++){
        String itmt=(String)itemHdr.get(i);
        if(itmt.equals("Buyer") || itmt.equals("Terms") || itmt.equals("Invoice Type") || itmt.equals("Alc Status")){%>
        <td nowrap="nowrap" align="center"><%=pktPrpMap.get(itemHdr.get(i))%></td>
        <%}else{%>
         <td nowrap="nowrap" align="right"><%=pktPrpMap.get(itemHdr.get(i))%></td>
        <%}}%>
        </tr>
        <%}%>
    </table>
    </td> </tr>
    <%} else{%>
    <tr><td valign="top" class="hedPg">
   Sorry No Result Found
   </td></tr>
   <%}}%>




  <tr>
    <td><jsp:include page="/footer.jsp" /> </td>
    </tr>
  </table>
  
  </body>
  <%@include file="../calendar.jsp"%>
</html>