<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
 
<%@ page errorPage="../error_page.jsp" %>
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import="java.util.ArrayList,java.util.HashMap,java.sql.ResultSet,ft.com.*, java.util.Date, java.util.ArrayList, ft.com.dao.*"%>
<jsp:useBean id="util" class="ft.com.JspUtil" scope="page" />
<jsp:useBean id="info" class="ft.com.InfoMgr" scope="session" />
<%@taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>
<%@taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>Order Mgmt</title>
    <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/bse.css?v=<%=info.getJsVersion()%>"/>
    <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/style.css?v=<%=info.getJsVersion()%>"/>
   <script language="javaScript" type="text/javascript" src="<%=info.getReqUrl()%>/calendar/calendar.js"></script>
   <link href="<%=info.getReqUrl()%>/calendar/calendar.css" rel="stylesheet" type="text/css" />
        <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/bse.js?v=<%=info.getJsVersion()%> " > </script>
        <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/ajax.js?v=<%=info.getJsVersion()%> " > </script>

  </head>
<%
HashMap dbinfo = info.getDmbsInfoLst();
String cnt = (String)dbinfo.get("CNT");
  String repPath = (String)dbinfo.get("REP_PATH");
  ArrayList pktDtlList = (ArrayList)session.getAttribute("openorderList");
  ArrayList itemHdr = (ArrayList)session.getAttribute("openitemHdr");
  String view=util.nvl((String)request.getAttribute("view"));
        String logId=String.valueOf(info.getLogId());
        String onfocus="cook('"+logId+"')";
        %>
 <body onfocus="<%=onfocus%>"  >

<input type="hidden" name="CNT" id="CNT" value="<%=cnt%>" />
<input type="hidden" name="REQURL" id="REQURL" value="<%=info.getReqUrl()%>" />
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
  <img src="../images/line.jpg" border="0" /></td><td valign="middle"> <span class="pgHed">Open/Close Order</span> </td>
  </tr></table></td></tr>
   <%
   String msg = (String)request.getAttribute("msg");
     if(msg!=null){%>
    <tr><td valign="top" class="tdLayout"><span class="redLabel"><%=msg%></span></td></tr>
   <%}%>
   
<tr><td valign="top" class="hedPg">
<html:form action="/order/openOrder.do?method=load"  method="POST">
  <table  class="grid1">
   <tr>
   <th colspan="2" align="center">Order Search</th>
   </tr>
       <tr><td>Order Date </td>  
       <td>From&nbsp; <html:text property="value(frmtrns_dt)" styleId="frmtrns_dt" name="orderOrderForm" size="8" /><img src="<%=info.getReqUrl()%>/images/calender.png" onClick="setYears(2011, <%=info.getCurrentYear()%>);showCalender(this, 'frmtrns_dt');"> 
       To&nbsp; <html:text property="value(totrns_dt)" styleId="totrns_dt" name="orderOrderForm"  size="8"/> <img src="<%=info.getReqUrl()%>/images/calender.png" onClick="setYears(2011, <%=info.getCurrentYear()%>);showCalender(this, 'totrns_dt');"></td>
      </tr>
      <tr><td>Order Due Date </td>  
       <td>From&nbsp; <html:text property="value(frmtrns_due_dt)" styleId="frmtrns_due_dt" name="orderOrderForm" size="8" /><img src="<%=info.getReqUrl()%>/images/calender.png" onClick="setYears(2011, <%=info.getCurrentYear()%>);showCalender(this, 'frmtrns_due_dt');"> 
       To&nbsp; <html:text property="value(totrns_due_dt)" styleId="totrns_due_dt" name="orderOrderForm"  size="8"/> <img src="<%=info.getReqUrl()%>/images/calender.png" onClick="setYears(2011, <%=info.getCurrentYear()%>);showCalender(this, 'totrns_due_dt');"></td>
      </tr>
      <tr><td>Est Complete Date </td>  
       <td>From&nbsp; <html:text property="value(frmest_cmp_dt)" styleId="frmest_cmp_dt" name="orderOrderForm" size="8" /><img src="<%=info.getReqUrl()%>/images/calender.png" onClick="setYears(2011, <%=info.getCurrentYear()%>);showCalender(this, 'frmest_cmp_dt');"> 
       To&nbsp; <html:text property="value(toest_cmp_dt)" styleId="toest_cmp_dt" name="orderOrderForm"  size="8"/> <img src="<%=info.getReqUrl()%>/images/calender.png" onClick="setYears(2011, <%=info.getCurrentYear()%>);showCalender(this, 'toest_cmp_dt');"></td>
      </tr>
    <tr>
   <td>Group By</td>
   <td>
   <html:select name="orderOrderForm" property="value(groupby)" styleId="groupby" >
   <html:option value="TRNS_IDN">Order Idn</html:option>
   <html:option value="DTLS">Details</html:option>
   </html:select>
   </td>
   </tr>
   <tr>
   <td>Open/Close</td>
   <td>
   <html:select name="orderOrderForm" property="value(openClose)" styleId="openClose" >
   <html:option value="OPEN">Open</html:option>
   <html:option value="CLOSE">Close</html:option>
   </html:select>
   </td>
   </tr>
   <tr>
   <td colspan="2" align="center"><html:submit property="value(srch)" value="Fetch" styleClass="submit" />
   </td>
   </tr>
   </table>
</html:form>
</td>
</tr>
   <tr><td>
    <table>
    <%  if(pktDtlList!=null && pktDtlList.size()>0 && view.equals("Y")){%>
  <tr><td valign="top" class="hedPg">
  <table class="grid1">
  <thead>
  <tr>
  <%for(int i=0 ; i < itemHdr.size() ;i++){%>
  <th><%=itemHdr.get(i)%></th>
  <%}%>
  </tr>
  </thead>
  <tbody>
  <%for(int j=0 ; j <pktDtlList.size() ;j++){
  HashMap pktDtl = (HashMap)pktDtlList.get(j);
  %>
  <tr>
  <%for(int k=0;k<itemHdr.size() ;k++){
  String hdr = (String)itemHdr.get(k);
  %>
  <td align="right"><%=util.nvl((String)pktDtl.get(hdr))%></td>
  <%}%>
  </tr>
  <%}%>
  </tbody>
  </table></td></tr>

  <%}else{%>
<tr><td valign="top" class="hedPg">
Sorry No Result Found</td></tr>
  <%}%>
  </table>
  </td>
  </tr>
    <tr>
    <td><jsp:include page="/footer.jsp" /> </td>
    </tr>
</table>
  <%@include file="/calendar.jsp"%>
  </body>
</html>