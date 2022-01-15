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
  <title>Stock Summary Report</title>
  <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/bse.js?v=<%=info.getJsVersion()%> " > </script>
  <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/bse.css?v=<%=info.getJsVersion()%>"/>
  <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/ajax.js?v=<%=info.getJsVersion()%> " > </script>
  <script language="javaScript" type="text/javascript" src="<%=info.getReqUrl()%>/calendar/calendar.js"></script>
  <link href="<%=info.getReqUrl()%>/calendar/calendar.css" rel="stylesheet" type="text/css" />
  <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/style.css?v=<%=info.getJsVersion()%>"/>
  </head>
<%  
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
  Stock Summary Report </span> </td>
</tr></table>
  </td>
  </tr>
  <tr><td valign="top" class="hedPg"> 
    <html:form action="/report/hkStockSummary.do?method=viewRt" method="post">
 <table>
  <tr>
   <td>
  <table  class="grid1">
  <tr><th>Generic Search </th></tr>
  <tr>
   <td>
 <jsp:include page="/genericSrch.jsp"/>
   </td></tr>
    <tr><td><table><tr>
   <td>Date From : </td><td>
   <html:text property="value(frmdte)" name="hkStockSummary" styleId="frmdte"/>
                <img src="<%=info.getReqUrl()%>/images/calender.png" onClick="setYears(2011, <%=info.getCurrentYear()%>);showCalender(this, 'frmdte');">
   </td><td>To : </td><td>
   <html:text property="value(todte)" name="hkStockSummary" styleId="todte"/>
                <img src="<%=info.getReqUrl()%>/images/calender.png" onClick="setYears(2011, <%=info.getCurrentYear()%>);showCalender(this, 'todte');">
   
   </td></tr></table> </td></tr>
    <tr><td><table><tr><td>Propeties : </td><td> <html:select property="value(prp)" name="hkStockSummary">
      <html:option value="SHAPE">Shape</html:option>
      <html:option value="SIZE">Size</html:option>
      <html:option value="COL">Color</html:option>
      <html:option value="CLR">Clarity</html:option>
   </html:select></td></tr></table> </td></tr>
   <tr><td align="center"><html:submit property="value(submit)" value="Fetch" styleClass="submit" /> </td> </tr>
   </table>
   </td></tr>
   </table></html:form>
  </td></tr>
  <tr>
  <td valign="top" class="hedPg">
  <%
  
  HashMap TTLMap = (HashMap)request.getAttribute("TTLMAP");
  ArrayList pktList = (ArrayList)request.getAttribute("dataList");
  if(pktList!=null && pktList.size()>0){
  ArrayList excelDataList = new ArrayList();
  ArrayList excelItmList = new ArrayList();
  excelItmList.add("DATEAMT");
  excelItmList.add("Propeties");
  excelItmList.add("Amt");
  excelItmList.add("Amt %");
  excelItmList.add("Lab-Avg Days");
   String ttlvlu = util.nvl((String)TTLMap.get("TTLVLU"));
  double ttlVluFl = 0;
  if(!ttlvlu.equals(""))
  ttlVluFl = Double.parseDouble(ttlvlu);
   String ttlDtevlu = util.nvl((String)TTLMap.get("TTLDTERNG"));
  double ttlDteVluFl = 0;
  if(!ttlDtevlu.equals(""))
  ttlDteVluFl = Double.parseDouble(ttlDtevlu);
  %><table><tr><td>Create Excel <img src="../images/ico_file_excel.png" onclick="goTo('<%=info.getReqUrl()%>/report/hkStockSummary.do?method=createXL','','')" /> </td></tr>
  <tr><td>
  <table class="grid1">
  <tr><th>Date selection To Curr Amt %</th> <th>Propeties </th><th>Amt</th><th>Amt %</th><th>Lab-Avg Days</th></tr>
  <%for(int i=0;i<pktList.size();i++){
  HashMap pktMap = (HashMap)pktList.get(i);
  HashMap excelMap = new HashMap();
  double ttlCurVlu = 0;
   double ttlCurDteVlu = 0;
   String vlu = util.nvl((String)pktMap.get("VLU"));
   if(!vlu.equals(""))
    ttlCurVlu = Double.parseDouble(vlu);
   String dteVlu = util.nvl((String)pktMap.get("DTERNGVLU"));
  if(!dteVlu.equals(""))
   ttlCurDteVlu = Double.parseDouble(dteVlu);
   String curAmt = Double.toString(util.getTtlPnt(ttlCurVlu, ttlVluFl));
   String curDteAmt = Double.toString(util.getTtlPnt(ttlCurDteVlu, ttlDteVluFl));
   excelMap.put("DATEAMT", curAmt);
   excelMap.put("Propeties",pktMap.get("DSC"));
   excelMap.put("Amt",pktMap.get("VLU"));
   excelMap.put("Amt %",Double.toString(util.getTtlPnt(ttlCurDteVlu, ttlDteVluFl)));
   excelMap.put("Lab-Avg Days",pktMap.get("AVGDAY"));
   excelDataList.add(excelMap); 
      %>
  <tr><td><%=curAmt%></td>
  <td><%=pktMap.get("DSC")%></td><td align="right"><%=pktMap.get("VLU")%></td><td align="right"><%=util.getTtlPnt(ttlCurDteVlu, ttlDteVluFl)%></td>
  <td align="right"><%=pktMap.get("AVGDAY")%></td>
  </tr>
   <%}%>
  </table></td></tr></table>
  <%
   session.setAttribute("itemHdr",excelItmList);
   session.setAttribute("pktList",excelDataList);
  }
 
  %>
  
  </td></tr>
   <tr>
    <td><jsp:include page="/footer.jsp" /> </td>
    </tr>
  </table>
   <%@include file="../calendar.jsp"%>
  </body>
</html>