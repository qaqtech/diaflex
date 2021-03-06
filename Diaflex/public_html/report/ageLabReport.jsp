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
  <title>Age / Lab  Report</title>
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
  Age Lab Report </span> </td>
</tr></table>
  </td>
  </tr>
   <tr>
   <td valign="top" class="hedPg" >
     <html:form action="/report/hkStockSummary.do?method=fetch" method="post">
  <table  class="grid1">
  <tr><th>Generic Search </th></tr>
  <tr>
   <td>
 <jsp:include page="/genericSrch.jsp"/>
   </td></tr>
   <tr><td colspan="2" align="center"><html:submit property="value(srch)" value="Fetch" styleClass="submit" /></td> </tr>
   </table>
   </html:form>
   </td></tr>
  <tr>
  <td valign="top" class="hedPg">
  <%
  ArrayList labList = (ArrayList)request.getAttribute("labList");
  ArrayList ageList = (ArrayList)request.getAttribute("ageList");
  HashMap pktList = (HashMap)request.getAttribute("pktList");
  ArrayList excelDataList = new ArrayList();
  ArrayList excelItmList = new ArrayList();
  if(pktList!=null && pktList.size()>0){
   String ttlvlu = util.nvl((String)pktList.get("TTLVLU"));
  double ttlVluFl = 0;
  if(!ttlvlu.equals(""))
  ttlVluFl = Double.parseDouble(ttlvlu);
  excelItmList.add("Day");
  %><table><tr><td>Create Excel <img src="../images/ico_file_excel.png" onclick="goTo('<%=info.getReqUrl()%>/report/hkStockSummary.do?method=createXL','','')" /> </td></tr>
  <tr><td>
  <table class="grid1"><tr>
  <th>Day</th>
  <%for(int i=0;i<labList.size();i++){
  String lab = (String)labList.get(i);
  excelItmList.add(lab);
  %><th><%=lab%></th>
  <%}%>
  <th>Pcs</th> <%excelItmList.add("Pcs");%>
  <th>Amt %</th> <%excelItmList.add("Amt %");%>
  <th>Price Revised from MktTrns to Curr</th> <%excelItmList.add("curr");%>
  </tr>
  <%
  for(int j=0;j<ageList.size();j++){
  String age = (String)ageList.get(j);
  HashMap excelMap = new HashMap();
  excelMap.put("Day", age);
  %>
  <tr><td><%=age%></td>
   <%
   double ttlQty = 0;
   for(int i=0;i<labList.size();i++){
  String lab = (String)labList.get(i);
  String labQty = util.nvl((String)pktList.get(age+"_"+lab+"_QTY"));
   if(labQty.equals(""))
  labQty = "0";
  ttlQty = ttlQty + Double.parseDouble(labQty);
 
   excelMap.put(lab, labQty);
  %><td align="right"><%=labQty%></td>
  <%}
   double amt = util.getTtlPnt(ttlQty, ttlVluFl);
  %>
  <td align="right"><%=ttlQty%> <%excelMap.put("Pcs", Double.toString(ttlQty));%> </td>
  <td align="right"><%=Double.toString(amt)%> <%excelMap.put("Amt %", Double.toString(amt));%></td>
  <td align="right"><%=util.nvl((String)pktList.get(age+"_DIFF"))%> <%excelMap.put("curr", util.nvl((String)pktList.get(age+"_DIFF")));%> </td>
  </tr>
  <%
  excelDataList.add(excelMap);
  } HashMap excelMp = new HashMap();
  excelMp.put("Day", "Total");
  %>
  <tr><td>Total</td>
   <%for(int i=0;i<labList.size();i++){
  String lab = (String)labList.get(i);
  excelMp.put(lab, util.nvl((String)pktList.get(lab+"TTL")));
  %><td align="right"><%=util.nvl((String)pktList.get(lab+"TTL"))%></td>
  <%}%>
  <td align="right"><%=util.nvl((String)pktList.get("TTLVLU"))%><%excelMp.put("Amt %", util.nvl((String)pktList.get("TTLVLU")));%> </td>
  <td align="right">100% <%excelMp.put("Pcs","100%");%></td>
  <td align="right"><%excelMp.put("curr","");%></td>
  </tr>
  <%excelDataList.add(excelMp);%>
  </table></td></tr></table>
  <%
  session.setAttribute("itemHdr",excelItmList);
   session.setAttribute("pktList",excelDataList);
  }%>
  </td></tr></table>
  
   </body>
</html>