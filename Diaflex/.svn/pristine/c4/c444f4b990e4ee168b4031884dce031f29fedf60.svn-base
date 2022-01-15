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
   <script type="text/javascript" src="<%=info.getReqUrl()%>/PopScripts/popup.js?v=<%=info.getJsVersion()%> " > </script>
   <script src="<%=info.getReqUrl()%>/PopScripts/jquery-1.2.6.min.js" type="text/javascript"></script>

  </head>
  <%
  HashMap prp = info.getPrp();
  ArrayList prpV = (ArrayList)prp.get("DEPTV");
  HashMap mprp = info.getMprp();  
  %>
        <%String logId=String.valueOf(info.getLogId());
        String onfocus="cook('"+logId+"')";
        %>
 <body onfocus="<%=onfocus%>" onload="MM_preloadImages('images/faunalogo.png')" topmargin="0" leftmargin="0" rightmargin="0" bottommargin="0">
   <input type="hidden" id="reqUrl" name="reqUrl" value="<%=info.getReqUrl()%>"/>

<div id="backgroundPopup"></div>
<div id="popupContactRult">
<table class="popUpTb">
 <tr><td height="5%">&nbsp;&nbsp;&nbsp;<html:button property="value(close)" styleClass="submit"  onclick="MKEmptyPopup('SC');disablePopupRult()" value="Close"  /> </td></tr>
<tr><td valign="top" height="95%">
<iframe name="SC" class="frameStyle" align="middle" frameborder="0">

</iframe>
</td></tr>
</table>
</div>

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
  <img src="../images/line.jpg" border="0" /></td><td valign="middle"> <span class="pgHed">Weekly  Report </span> </td>
</tr></table>
  </td>
  </tr>
  <tr>
  <td valign="top" class="tdLayout">
  <html:form action="/report/weeklyReport.do?method=fetch" method="post" >
  <table>
   <tr><td> DepartMent :</td><td colspan="2">
   <html:select property="value(dept)" name="weeklyReportForm" >
   <%for(int i=0;i<prpV.size();i++){
   String dept = (String)prpV.get(i);
   if(dept.contains("GIA")){
   %>
   <html:option value="<%=dept%>"  key="<%=dept%>" ></html:option>
   <%}}%>
    </html:select>
   </td></tr>
<tr>
   <td>Shape </td><td>
             <html:select property="value(shape)" styleId="shape" name="weeklyReportForm" >
             <html:option value="ALL" >ALL</html:option>
             <html:option value="ROUND">ROUND</html:option>
             <html:option value="FANCY">FANCY</html:option>
            </html:select>
   </td>
</tr>
   <tr><td>Weekly Sale (P1) :</td>
   <td>  <html:text property="value(p1dtefr)" styleClass="txtStyle"  styleId="p1dtefr"  size="12"  /> <a href="#" style="margin-top:5px;"  onClick="setYears(1900, '<%=info.getCurrentYear()%>' );showCalender(this, 'p1dtefr');">
                <img src="<%=info.getReqUrl()%>/images/calender.png"></a></td>
   <td> <html:text property="value(p1dteto)" styleClass="txtStyle"  styleId="p1dteto"  size="12"  /> <a href="#" style="margin-top:5px;"  onClick="setYears(1900, '<%=info.getCurrentYear()%>' );showCalender(this, 'p1dteto');">
                <img src="<%=info.getReqUrl()%>/images/calender.png"></a></td></tr>
    <tr><td>4 Week Sale (P2) :</td> <td>  <html:text property="value(p2dtefr)" styleClass="txtStyle"  styleId="p2dtefr"  size="12"  /> <a href="#" style="margin-top:5px;"  onClick="setYears(1900, '<%=info.getCurrentYear()%>' );showCalender(this, 'p2dtefr');">
                <img src="<%=info.getReqUrl()%>/images/calender.png"></a></td>
   <td> <html:text property="value(p2dteto)" styleClass="txtStyle"  styleId="p2dteto"  size="12"  /> <a href="#" style="margin-top:5px;"  onClick="setYears(1900, '<%=info.getCurrentYear()%>' );showCalender(this, 'p2dteto');">
                <img src="<%=info.getReqUrl()%>/images/calender.png"></a></td></tr>
     <tr><td>Transfer Date :</td>
    <td>  <html:text property="value(trfdtefr)" styleClass="txtStyle"  styleId="trfdtefr"  size="12"  /> <a href="#" style="margin-top:5px;"  onClick="setYears(1900, '<%=info.getCurrentYear()%>' );showCalender(this, 'trfdtefr');">
                <img src="<%=info.getReqUrl()%>/images/calender.png"></a></td>
   <td> <html:text property="value(trfdteto)" styleClass="txtStyle"  styleId="trfdteto"  size="12"  /> <a href="#" style="margin-top:5px;"  onClick="setYears(1900, '<%=info.getCurrentYear()%>' );showCalender(this, 'trfdteto');">
                <img src="<%=info.getReqUrl()%>/images/calender.png"></a></td>
     </tr>
     <tr> <td colspan="3"> <html:submit value="Fetch" property="value(submit)" styleClass="submit" ></html:submit> </td>
   </tr>
   </table></html:form>
   <tr>
  <td valign="top" class="tdLayout">
   <% String view =(String)request.getAttribute("view");
  if(view!=null){
   ArrayList prpList = new ArrayList();
  prpList.add("SIZE");
  prpList.add("COL");
  prpList.add("CLR");
  String shapefetch =util.nvl((String)request.getAttribute("shape"));
   ArrayList shapeList = new ArrayList();
   if(shapefetch.equals("ALL")){
   shapeList.add("ROUND");
   shapeList.add("FANCY");
   }else
   shapeList.add(shapefetch);
   String shape="";
   %>
   <%
   String dept = util.nvl((String)request.getAttribute("dept"));
   String salep1 = util.nvl((String)request.getAttribute("salep1"));
   String salep2 = util.nvl((String)request.getAttribute("salep2"));
    String trnDte =  util.nvl((String)request.getAttribute("trnDte"));
   
   %>
  <table>
   <tr><td colspan="2" align="left">
  Create Excel &nbsp;&nbsp;&nbsp;
  <img src="../images/ico_file_excel.png" border="0" onclick="goTo('<%=info.getReqUrl()%>/report/weeklyReport.do?method=createXL&shape=<%=shapefetch%>&dept=<%=dept%>&salep1=<%=salep1%>&salep2=<%=salep2%>&trnDte=<%=trnDte%>','','')"/>
 
  </td></tr>
  <tr><td>
  <table>
  <tr><td><b> DepartMent :</b></td><td></td><td><%=dept%></td><td></td></tr>
   <tr><td><b>Weekly Sale (P1) :</b></td><td></td><td><%=salep1%></td><td></td></tr>
    <tr><td><b>4 Week Sale (P2) :</b></td><td></td><td><%=salep2%></td><td></td></tr>
     <tr><td><b>Transfer Date :</b></td><td></td><td><%=trnDte%></td><td></td></tr>
  </table>
  </td></tr>
    <tr><td colspan="2" align="left">
   
  <!---FANCY Shape-->
  <%
    HashMap shapeDateMap= (HashMap)session.getAttribute("ShapeDateMap"); 
    ArrayList fancyShapeList =(ArrayList)session.getAttribute("ShapeList");
    if(shapeList.contains("FANCY")){
    if(fancyShapeList!=null && fancyShapeList.size() > 0){
  %>
  <b>FANCY</b>
  <table class="grid3">
  <tr><th>ADD</th><th colspan="4"  class="lft"><b>Shape</b></th><th colspan="2">W-S</th><th>4-S</th> </tr>
  
   <%for(int i=0 ; i< fancyShapeList.size();i++){
  String lprpVal = util.nvl((String)fancyShapeList.get(i));
 
  %>
  <tr>
 <td>
 <%=util.nvl((String)shapeDateMap.get("FANCY_"+lprpVal+"_ADDPCT"))%>
 </td><td><A href="weeklyReport.do?method=GridByShapeWise&FNSHAPE=<%=lprpVal%>" target="SC" onclick="loadFunRult()"> <b><%=lprpVal%></b></a></td>
 <td><%=util.nvl((String)shapeDateMap.get("FANCY_"+lprpVal+"_Z_VAL"))%></td>  
 <td>
  <a   title="Packet Details" target="_blank" href="weeklyReport.do?method=pktDtlExcel&shgrp=FANCY&shape=<%=lprpVal%>&flg=Z" >
 <%=util.nvl((String)shapeDateMap.get("FANCY_"+lprpVal+"_Z_PCT"))%>
 </a></td>  
 <td><%=util.nvl((String)shapeDateMap.get("FANCY_"+lprpVal+"_Z_DYS"))%></td>
 <td>
 <a   title="Packet Details" target="_blank" href="weeklyReport.do?method=pktDtlExcel&shgrp=FANCY&shape=<%=lprpVal%>&flg=W" >
 <%=util.nvl((String)shapeDateMap.get("FANCY_"+lprpVal+"_W_PCT"))%>
 </a></td> 
 <td><%=util.nvl((String)shapeDateMap.get("FANCY_"+lprpVal+"_W_DYS"))%></td>
 <td>
  <a   title="Packet Details" target="_blank" href="weeklyReport.do?method=pktDtlExcel&shgrp=FANCY&shape=<%=lprpVal%>&flg=S" >
 <%=util.nvl((String)shapeDateMap.get("FANCY_"+lprpVal+"_S_PCT"))%>
 </a></td>
  </tr>
  <%}%>
 <tr><td class="ttrgt">100%</td><td class="ttrgt">Total</td><td class="ttrgt"><%=util.nvl((String)shapeDateMap.get("FANCY_Z_TTLVAL"))%></td>
  <td class="ttrgt">
  <a   title="Packet Details" target="_blank" href="weeklyReport.do?method=pktDtlExcel&shgrp=FANCY&flg=Z" >
  100%
  </a></td>
  <td class="ttrgt"><%=util.nvl((String)shapeDateMap.get("FANCY_Z_AVGDYS"))%></td>
  <td class="ttrgt">
   <a   title="Packet Details" target="_blank" href="weeklyReport.do?method=pktDtlExcel&shgrp=FANCY&flg=W" >
  100%
  </a></td>
  <td class="ttrgt"><%=util.nvl((String)shapeDateMap.get("FANCY_W_AVGDYS"))%></td>
  <td class="ttrgt">
  <a   title="Packet Details" target="_blank" href="weeklyReport.do?method=pktDtlExcel&shgrp=FANCY&flg=S" >
  100%
  </a></td>
  </tr>
  </table>
  <%}}%>
  </td>
 
  </tr>
  
   <!--LAB Grid-->
  <tr>
  <%for(int k=0;k<shapeList.size();k++){
  shape = (String)shapeList.get(k);
  %>
  <td valign="top">
  
    <% HashMap  labWsDataMap = (HashMap)session.getAttribute("LabWsDataMap");
      ArrayList daysLabList= ((ArrayList)session.getAttribute("DaysLabList") == null)?new ArrayList():(ArrayList)session.getAttribute("DaysLabList");   %>
       <%=shape%>
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
  <%}%>
  </tr>
  
  
  <!--Properties Grid  -->
   <%for(int i=0;i<prpList.size();i++){
    String lprp = (String)prpList.get(i); %>
  <tr>
 
 
    <!--Propeties Grid-->
   <% HashMap  PrpWiseGd = (HashMap)session.getAttribute("PrpWiseGd");
       HashMap prpShValMap = (HashMap)session.getAttribute("PrpShMap");%>
   <%for(int k=0;k<shapeList.size();k++){
  shape = (String)shapeList.get(k);
   %>
    <td valign="top">
  <%
   ArrayList lprpValLst = (ArrayList)prpShValMap.get(""+shape+"_"+lprp);
   if(lprpValLst!=null && lprpValLst.size() > 0){%>
   <table cellpadding="0" cellspacing="0">
  <tr><td><B><%=shape%> </b></td></tr>
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
  <tr ><td class="ttrgt">
   <a  title="Packet Details" target="_blank" href="weeklyReport.do?method=pktDtlExcel&shgrp=<%=shape%>&grp=NEW&prpgrp=<%=lprp%>" >
  100%
  </a>
  </td><td class="ttrgt">Total</td>
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
  <%}%>
  </tr>
  <%}%>
  
  
     <tr>
     <%for(int k=0;k<shapeList.size();k++){
  shape = (String)shapeList.get(k);
   %>
    
  <td valign="top">
  <!---All stock Grid-->
  <%
    HashMap allStockMap= (HashMap)session.getAttribute("AllStockMap"); 
    ArrayList allStkPrpList =(ArrayList)session.getAttribute("AllStkPrpList");
    if(allStkPrpList!=null && allStkPrpList.size() > 0){
  %>
  <%=shape%>
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
  <tr><td>
  <a   title="Packet Details" target="_blank" href="weeklyReport.do?method=pktDtlExcel&shgrp=<%=shape%>&allshgrp=<%=lprpVal%>&grp=NEW" >
  <%=util.nvl((String)allStockMap.get(lprpVal+"_ADDPCT"))%>
  </a></td><td><b><%=lprpdsc%></b></td>
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
 <tr ><td class="ttrgt">
 <a   title="Packet Details" target="_blank" href="weeklyReport.do?method=pktDtlExcel&grp=NEW" >
 100%
 </a>
 </td><td class="ttrgt">Total</td><td class="ttrgt"><%=util.nvl((String)allStockMap.get(""+shape+"_Z_TTLVAL"))%></td>
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
  <%}%>
  </tr>
  </table>
  
  
  <%}%>
  </td></tr>
  
  
   <tr>
    <td><jsp:include page="/footer.jsp" /> </td>
    </tr>
  </table>
  <%@include file="../calendar.jsp"%>
  </body>
</html>