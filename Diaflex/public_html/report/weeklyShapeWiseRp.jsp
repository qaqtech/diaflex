<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page errorPage="../error_page.jsp" %>

<%@ page import="java.util.ArrayList,java.util.ArrayList,java.util.HashMap, java.sql.*, java.util.Enumeration, java.util.logging.Level"%>
<jsp:useBean id="info" class="ft.com.InfoMgr" scope="session" />
<jsp:useBean id="util" class="ft.com.JspUtil" scope="page" />
<%@taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>weekly Sale Report</title>
    <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/bse.css?v=<%=info.getJsVersion()%>"/>
<link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/style.css?v=<%=info.getJsVersion()%>"/>
   <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/bse.js?v=<%=info.getJsVersion()%> " > 
   
   </script>
<script language="javaScript" type="text/javascript" src="<%=info.getReqUrl()%>/calendar/calendar.js"></script>
<link href="<%=info.getReqUrl()%>/calendar/calendar.css" rel="stylesheet" type="text/css" />
    </head>
    <body>
    <%
     String shape = "FANCY";
     HashMap mprp = info.getMprp();
    %>
    <table>
     <tr><td valign="top" class="tdLayout">
     <table>
     
      <!--LAB Grid-->
  <tr>
 
  <td valign="top">
  
    <% HashMap  labWsDataMap = (HashMap)session.getAttribute("LabWsDataMap");
      ArrayList daysLabList= ((ArrayList)session.getAttribute("DaysLabList") == null)?new ArrayList():(ArrayList)session.getAttribute("DaysLabList");   %>
       <%=request.getAttribute("FNSHAPE")%>
       <table class="grid3"><tr><th>Days</th><th  >GIA</th><th  >IGI</th><th>Pcs</th><th>%</th><th>D-Rev</th> </tr>
     <% 
      
  
     for(int i=0 ; i < daysLabList.size() ;i++){
     String day = (String)daysLabList.get(i);
    
     %>
     <tr><td><%=day%></td>
     <td>
     <a   title="Packet Details" target="_blank" href="weeklyReport.do?method=pktDtlExcel&day=<%=day%>&shgrp=<%=shape%>&lab=GIA&flg=Z" >
     <%=util.nvl((String)labWsDataMap.get(day+"_"+shape+"_GIA_LABCNT"))%>
     </a></td>
     <td>
     <a   title="Packet Details" target="_blank" href="weeklyReport.do?method=pktDtlExcel&day=<%=day%>&shgrp=<%=shape%>&lab=IGI&flg=Z" >
     <%=util.nvl((String)labWsDataMap.get(day+"_"+shape+"_IGI_LABCNT"))%>
     </a></td>
     <td>
     <a   title="Packet Details" target="_blank" href="weeklyReport.do?method=pktDtlExcel&day=<%=day%>&shgrp=<%=shape%>&flg=Z" >
     <%=util.nvl((String)labWsDataMap.get(day+"_"+shape+"_QTY"))%>
     </a></td>
     <td><%=util.nvl((String)labWsDataMap.get(day+"_"+shape+"_PCT"))%></td>
     <td><%=util.nvl((String)labWsDataMap.get(day+"_"+shape+"_REVPCT"))%></td>
     </tr>
     <%}
     
     %>
     <tr><td class="ttrgt">Total</td><td class="ttrgt">
     <a   title="Packet Details" target="_blank" href="weeklyReport.do?method=pktDtlExcel&shgrp=<%=shape%>&lab=GIA&flg=Z" >
     <%=util.nvl((String)labWsDataMap.get(shape+"_GIA_TTLCNT"))%>
     </a>
     </td>
     <td class="ttrgt">
     <a   title="Packet Details" target="_blank" href="weeklyReport.do?method=pktDtlExcel&shgrp=<%=shape%>&lab=IGI&flg=Z" >
     <%=util.nvl((String)labWsDataMap.get(shape+"_IGI_TTLCNT"))%>
     </a></td>
     <td class="ttrgt">
     <a   title="Packet Details" target="_blank" href="weeklyReport.do?method=pktDtlExcel&shgrp=<%=shape%>&flg=Z" >
     <%=util.nvl((String)labWsDataMap.get(shape+"_TTLCNT"))%>
     </a></td>
    <td class="ttrgt">100%</td> <td class="ttrgt"><%=util.nvl((String)labWsDataMap.get(shape+"_TTLREVPCT"))%></td></tr>
  </table>
  </td>
  
  </tr>
  </table>
  
     </td></tr>
      
  <!--Properties Grid  -->
   <%
    ArrayList prpList = new ArrayList();
  prpList.add("SIZE");
  prpList.add("COL");
  prpList.add("CLR");
   for(int i=0;i<prpList.size();i++){
    String lprp = (String)prpList.get(i); %>
  <tr>
 
 
    <!--Propeties Grid-->
   <% HashMap  PrpWiseGd = (HashMap)session.getAttribute("PrpWiseGd");
       HashMap prpShValMap = (HashMap)session.getAttribute("PrpShMap");%>
  
    <td valign="top">
  <%
   ArrayList lprpValLst = (ArrayList)prpShValMap.get(""+shape+"_"+lprp);
   if(lprpValLst!=null && lprpValLst.size() > 0){%>
   <table cellpadding="0" cellspacing="0">
  <tr><td><B>   <%=request.getAttribute("FNSHAPE")%> </b></td></tr>
  <tr><td>
  <table class="grid3">
  <tr><th>ADD</th><th colspan="4" class="lft" ><b><%=mprp.get(lprp+"D")%></b></th><th colspan="2">W-S</th><th>4-S</th></tr>
   <%for(int j=0;j<lprpValLst.size();j++){
  String lprpVal = util.nvl((String)(String)lprpValLst.get(j));  %>
  <tr> <td>
 <a  title="Packet Details" target="_blank" href="weeklyReport.do?method=pktDtlExcel&shgrp=<%=shape%>&grp=NEW&prpgrp=<%=lprp%>&prpgrpVal=<%=lprpVal%>" >
 <%=util.nvl((String)PrpWiseGd.get(""+shape+"_"+lprpVal+"_ADD_PCT"))%> 
  </a>
  </td><td><b><%=lprpVal%></b></td>
   <td>
   <a   title="Packet Details" target="_blank" href="weeklyReport.do?method=pktDtlExcel&shgrp=<%=shape%>&flg=Z&prpgrp=<%=lprp%>&prpgrpVal=<%=lprpVal%>" >
   <%=util.nvl((String)PrpWiseGd.get(""+shape+"_Z_"+lprpVal+"_QTY"))%> 
   </a></td>
  <td><%=util.nvl((String)PrpWiseGd.get(""+shape+"_Z_"+lprpVal+"_PCT"))%></td>
  <td><%=util.nvl((String)PrpWiseGd.get(""+shape+"_Z_"+lprpVal+"_DYS"))%> </td>
  <td>
  <a   title="Packet Details" target="_blank" href="weeklyReport.do?method=pktDtlExcel&shgrp=<%=shape%>&flg=W&prpgrp=<%=lprp%>&prpgrpVal=<%=lprpVal%>" >
  <%=util.nvl((String)PrpWiseGd.get(""+shape+"_W_"+lprpVal+"_PCT"))%>
  </a></td>
  <td><%=util.nvl((String)PrpWiseGd.get(""+shape+"_W_"+lprpVal+"_DYS"))%> </td>
  <td>
   <a   title="Packet Details" target="_blank" href="weeklyReport.do?method=pktDtlExcel&shgrp=<%=shape%>&flg=S&prpgrp=<%=lprp%>&prpgrpVal=<%=lprpVal%>" >
  <%=util.nvl((String)PrpWiseGd.get(""+shape+"_S_"+lprpVal+"_PCT"))%>
  </a></td>
  </tr>
  <%}%>
  <tr ><td class="ttrgt">100%</td><td class="ttrgt">Total</td>
  <td class="ttrgt">
  <a   title="Packet Details" target="_blank" href="weeklyReport.do?method=pktDtlExcel&shgrp=<%=shape%>&flg=Z&prpgrp=<%=lprp%>" >
  <%=util.nvl((String)PrpWiseGd.get(""+shape+"_Z_"+lprp+"_TTLQTY"))%>
  </a></td>
  <td class="ttrgt">100%</td>
  <td class="ttrgt"><%=util.nvl((String)PrpWiseGd.get(""+shape+"_Z_"+lprp+"_AVGDYS"))%></td>
  <td class="ttrgt">
  <a   title="Packet Details" target="_blank" href="weeklyReport.do?method=pktDtlExcel&shgrp=<%=shape%>&flg=W&prpgrp=<%=lprp%>" >
  100%</a></td>
  <td class="ttrgt"><%=util.nvl((String)PrpWiseGd.get(""+shape+"_W_"+lprp+"_AVGDYS"))%></td>
  <td class="ttrgt">
  <a   title="Packet Details" target="_blank" href="weeklyReport.do?method=pktDtlExcel&shgrp=<%=shape%>&flg=S&prpgrp=<%=lprp%>" >
  100%
  </a></td>
  </tr>
  </table>
  </td></tr></table>
  <%}%>
  </td>
 
  </tr>
  <%}%>
  <tr>
  
  
  <td valign="top">
  <!---All stock Grid-->
  <%
    HashMap allStockMap= (HashMap)session.getAttribute("AllStockMap"); 
    ArrayList allStkPrpList =(ArrayList)session.getAttribute("AllStkPrpList");
    if(allStkPrpList!=null && allStkPrpList.size() > 0){
  %>
  <%=request.getAttribute("FNSHAPE")%>
  <table class="grid3">
  <tr><th>ADD</th><th colspan="4"  class="lft"><b>All Stock (Live)</b></th><th colspan="2">W-S</th><th>4-S</th> </tr>
  
   <%for(int i=0 ; i< allStkPrpList.size();i++){
  String lprpVal = util.nvl((String)(String)allStkPrpList.get(i));
  String lprpdsc="";
  if(lprpVal.indexOf("F-COL")!=-1){
  lprpdsc = lprpVal.replace("SH", "");
  lprpVal = lprpVal.replace("SH", shape);
 } else{
  lprpVal = lprpVal.replace("SH", shape);
   lprpdsc = lprpVal;
  }
  %>
  <tr><td><%=util.nvl((String)allStockMap.get(lprpVal+"_ADDPCT"))%></td><td><b><%=lprpdsc%></b></td>
 <td><%=util.nvl((String)allStockMap.get(lprpVal+"_Z_VAL"))%></td>  
 <td>
 <a   title="Packet Details" target="_blank" href="weeklyReport.do?method=pktDtlExcel&shgrp=<%=shape%>&allshgrp=<%=lprpVal%>&flg=Z" >
 <%=util.nvl((String)allStockMap.get(lprpVal+"_Z_PCT"))%>
 </a></td>  
 <td><%=util.nvl((String)allStockMap.get(lprpVal+"_Z_DYS"))%></td>
   <td>
    <a   title="Packet Details" target="_blank" href="weeklyReport.do?method=pktDtlExcel&shgrp=<%=shape%>&allshgrp=<%=lprpVal%>&flg=W" >
   <%=util.nvl((String)allStockMap.get(lprpVal+"_W_PCT"))%>
   </a></td>  
   <td><%=util.nvl((String)allStockMap.get(lprpVal+"_W_DYS"))%></td>
  <td>
  <a   title="Packet Details" target="_blank" href="weeklyReport.do?method=pktDtlExcel&shgrp=<%=shape%>&allshgrp=<%=lprpVal%>&flg=S" >
  <%=util.nvl((String)allStockMap.get(lprpVal+"_S_PCT"))%>
  </a></td> </tr>
  <%}%>
 <tr ><td class="ttrgt">100%</td><td class="ttrgt">Total</td><td class="ttrgt"><%=util.nvl((String)allStockMap.get(""+shape+"_Z_TTLVAL"))%></td>
  <td class="ttrgt">
  <a   title="Packet Details" target="_blank" href="weeklyReport.do?method=pktDtlExcel&shgrp=<%=shape%>&flg=Z" >
  100%
  </a></td>
  <td class="ttrgt">
  <%=util.nvl((String)allStockMap.get(""+shape+"_Z_AVGDYS"))%>
  </td>
  <td class="ttrgt">
  <a   title="Packet Details" target="_blank" href="weeklyReport.do?method=pktDtlExcel&shgrp=<%=shape%>&flg=W" >
  100%
  </a></td>
  <td class="ttrgt"><%=util.nvl((String)allStockMap.get(""+shape+"_W_AVGDYS"))%></td>
  <td class="ttrgt">
  <a   title="Packet Details" target="_blank" href="weeklyReport.do?method=pktDtlExcel&shgrp=<%=shape%>&flg=S" >
  100%</a>
  </td>
  </tr>
  </table>
  <%}%>
  </td>
 </tr>
 
     
     
     </table>
  
  </body>
  </html>