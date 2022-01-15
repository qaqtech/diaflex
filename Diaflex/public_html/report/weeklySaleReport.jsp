<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page errorPage="../error_page.jsp" %>

<%@ page import="java.util.ArrayList,java.util.ArrayList,java.util.HashMap, java.sql.*, java.util.Enumeration, java.util.logging.Level"%>
<jsp:useBean id="info" class="ft.com.InfoMgr" scope="session" />
<jsp:useBean id="util" class="ft.com.JspUtil" scope="page" />
<%@taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
 
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
  <%
  HashMap prp = info.getPrp();
  ArrayList prpV = (ArrayList)prp.get("DEPTV");
  HashMap mprp = info.getMprp();  
  %>
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
  <img src="../images/line.jpg" border="0" /></td><td valign="middle"> <span class="pgHed">Weekly Sale Report </span> </td>
</tr></table>
  </td>
  </tr>
  <tr>
  <td valign="top" class="tdLayout">
  <html:form action="/report/weeklySaleReport.do?method=fetch" method="post" >
  <table>
   <tr><td> DepartMent :</td><td colspan="2">
   <html:select property="value(dept)" name="weeklySaleReportForm" >
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
             <html:select property="value(shape)" styleId="shape" name="weeklySaleReportForm" >
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
   
  <table>
   <tr><td>
  Create Excel &nbsp;&nbsp;&nbsp;
  <img src="../images/ico_file_excel.png" border="0" onclick="goTo('<%=info.getReqUrl()%>/report/weeklySaleReport.do?method=createXL&shape=<%=shapefetch%>','','')"/>
 
  </td></tr>
   <!--LAB Grid-->
  <tr>
  <%for(int k=0;k<shapeList.size();k++){
  shape = (String)shapeList.get(k);
  %>
  <td valign="top">
  
    <% HashMap  labWsDataMap = (HashMap)session.getAttribute("LabWsDataMap");
       ArrayList daysLabList = (ArrayList)session.getAttribute("DaysLabList");%>
       <%=shape%>
       <table class="grid3"><tr><th>Days</th><th  >GIA</th><th  >IGI</th><th>Pcs</th><th>Amt</th><th>%</th><th>D-Rev</th> </tr>
     <% 
      
     int giaCnt = Integer.parseInt(util.nvl((String)labWsDataMap.get(shape+"_GIA_CNT"),"0"));
     int igicnt = Integer.parseInt(util.nvl((String)labWsDataMap.get(shape+"_IGI_CNT"),"0"));
     int labCnt = giaCnt + igicnt;
     int ttlVlu =0;
     for(int i=0 ; i < daysLabList.size() ;i++){
     String day = (String)daysLabList.get(i);
     ttlVlu = ttlVlu + Integer.parseInt(util.nvl((String)labWsDataMap.get(day+"_"+shape+"_VAL"),"0"));
    
     %>
     <tr><td><%=day%></td>
     <td ><a  title="Packet Details" target="_blank" href="weeklySaleReport.do?method=pktDtlExcel&labsh=<%=shape%>&days=<%=day%>&lab=GIA" ><%=util.nvl((String)labWsDataMap.get(day+"_"+shape+"_GIA_CNT"))%></a></td>
     <td  ><a   title="Packet Details" target="_blank" href="weeklySaleReport.do?method=pktDtlExcel&labsh=<%=shape%>&days=<%=day%>&lab=IGI" ><%=util.nvl((String)labWsDataMap.get(day+"_"+shape+"_IGI_CNT"))%></a></td>
     <td ><a   title="Packet Details" target="_blank" href="weeklySaleReport.do?method=pktDtlExcel&labsh=<%=shape%>&days=<%=day%>&lab=ALL" ><%=util.nvl((String)labWsDataMap.get(day+"_"+shape+"_TTLCNT"))%></a></td>
    
     <td ><%=util.nvl((String)labWsDataMap.get(day+"_"+shape+"_VAL"))%></td>
        <td ><%=util.nvl((String)labWsDataMap.get(day+"_"+shape+"_PCT"))%></td>
        <td ><%=util.nvl((String)labWsDataMap.get(day+"_"+shape+"_PRI"))%></td>
     </tr>
     <%}
     
     %>
     <tr><td></td><td class="ttrgt">
     <a   title="Packet Details" target="_blank" href="weeklySaleReport.do?method=pktDtlExcel&labsh=<%=shape%>&days=ALL&lab=GIA" ><%=util.nvl((String)labWsDataMap.get(shape+"_GIA_CNT"))%></a></td>
     <td class="ttrgt">
          <a   title="Packet Details" target="_blank" href="weeklySaleReport.do?method=pktDtlExcel&labsh=<%=shape%>&days=ALL&lab=IGI" ><%=util.nvl((String)labWsDataMap.get(shape+"_IGI_CNT"))%></a></td>
     <td class="ttrgt">
     <a   title="Packet Details" target="_blank" href="weeklySaleReport.do?method=pktDtlExcel&labsh=<%=shape%>&days=ALL&lab=ALL" ><%=labCnt%></a></td>
     <td class="ttrgt"><%=ttlVlu%></td><td class="ttrgt">100%</td> <td><%=util.nvl((String)labWsDataMap.get(shape+"_AVG_PRI"))%></td></tr>
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
  <tr><td>ADD</td> <td colspan="4" class="lft" ><b><%=mprp.get(lprp+"D")%></b></td><td colspan="2">W-S</td><td colspan="2">4-S</td><td>GIA-S</td> </tr>
  <tr><th></th><th></th><th >Pcs</th><th >%</th><th></th><th>%</th><th></th><th>%</th><th></th><th></th> </tr>
  <%for(int j=0;j<lprpValLst.size();j++){
  String lprpVal = util.nvl((String)(String)lprpValLst.get(j));  %>
  <tr><td><%=util.nvl((String)PrpWiseGd.get(""+shape+"_Stock_"+lprpVal+"_ADD_PCT"))%></td><td><b><%=lprpVal%></b></td>
  <td>
   <a   title="Packet Details" target="_blank" href="weeklySaleReport.do?method=pktDtlExcel&grpsh=<%=shape%>&grplprp=<%=lprp%>&grpval=<%=lprpVal%>&flg=Z" >
   <%=util.nvl((String)PrpWiseGd.get(""+shape+"_Stock_"+lprpVal+"_CNT"))%></a> </td>
   <td><%=util.nvl((String)PrpWiseGd.get(""+shape+"_Stock_"+lprpVal+"_PCT"))%> </td>
   <td><%=util.nvl((String)PrpWiseGd.get(""+shape+"_Stock_"+lprpVal+"_DYS"))%> </td>
    <td><%=util.nvl((String)PrpWiseGd.get(""+shape+"_P1_"+lprpVal+"_PCT"))%> </td>
    <td>
    <a   title="Packet Details" target="_blank" href="weeklySaleReport.do?method=pktDtlExcel&grpsh=<%=shape%>&grplprp=<%=lprp%>&grpval=<%=lprpVal%>&flg=W" >
    <%=util.nvl((String)PrpWiseGd.get(""+shape+"_P1_"+lprpVal+"_DYS"))%> </a></td>
     <td><%=util.nvl((String)PrpWiseGd.get(""+shape+"_P2_"+lprpVal+"_PCT"))%> </td>
    <td>
    <a   title="Packet Details" target="_blank" href="weeklySaleReport.do?method=pktDtlExcel&grpsh=<%=shape%>&grplprp=<%=lprp%>&grpval=<%=lprpVal%>&flg=S" >
    <%=util.nvl((String)PrpWiseGd.get(""+shape+"_P2_"+lprpVal+"_DYS"))%></a> </td>
    <td><%=util.nvl((String)PrpWiseGd.get(""+shape+"_"+lprpVal+"_ASRTPCT"))%> </td>
  </tr>
  <%}%>
  <tr><td>100%</td><td class="ttrgt">TOTAL</td> 
  <td class="ttrgt">
  <a   title="Packet Details" target="_blank" href="weeklySaleReport.do?method=pktDtlExcel&grpsh=<%=shape%>&grplprp=<%=lprp%>&grpval=ALL&flg=Z" >
  <%=util.nvl((String)PrpWiseGd.get(""+shape+"_Stock_"+lprp+"_TTLPCS"))%></a></td><td>100%</td> 
  <td class="ttrgt">
  <%=util.nvl((String)PrpWiseGd.get(""+shape+"_Stock_"+lprp+"_TTLAVGDAY"))%></td><td class="ttrgt">100%</td>
  <td class="ttrgt">
  <a   title="Packet Details" target="_blank" href="weeklySaleReport.do?method=pktDtlExcel&grpsh=<%=shape%>&grplprp=<%=lprp%>&grpval=ALL&flg=W" >
  <%=util.nvl((String)PrpWiseGd.get(""+shape+"_P1_"+lprp+"_TTLAVGDAY"))%></a></td><td class="ttrgt">100%</td>
  <td class="ttrgt">
  <a   title="Packet Details" target="_blank" href="weeklySaleReport.do?method=pktDtlExcel&grpsh=<%=shape%>&grplprp=<%=lprp%>&grpval=ALL&flg=S" >
  <%=util.nvl((String)PrpWiseGd.get(""+shape+"_P2_"+lprp+"_TTLAVGDAY"))%></a></td>
  <td class="ttrgt">100%</td>
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
  <tr><td>ADD</td><td colspan="4"  class="lft"><b>All Stock (Live)</b></td><td colspan="2">W-S</td><td colspan="2">4-S</td> </tr>
  <tr><th></th><th></th><th >Pcs</th><th >%</th><th></th><th>%</th><th></th><th>%</th><th></th> </tr>
   <%for(int i=0 ; i< allStkPrpList.size();i++){
  String lprpVal = util.nvl((String)(String)allStkPrpList.get(i));
  lprpVal = lprpVal.replace("SH", shape);
  %>
  <tr><td><%=util.nvl((String)allStockMap.get(""+shape+"_Stock_"+lprpVal+"_ADD_PCT"))%></td><td><b><%=lprpVal%></b></td>
  <td>
  <a title="Packet Details" target="_blank" href="weeklySaleReport.do?method=pktDtlExcel&allsh=<%=shape%>&alllab=<%=lprpVal%>&flg=Z" ><%=util.nvl((String)allStockMap.get(""+shape+"_Stock_"+lprpVal+"_CNT"))%> </a></td>
   <td><%=util.nvl((String)allStockMap.get(""+shape+"_Stock_"+lprpVal+"_PCT"))%> </td>
   <td><%=util.nvl((String)allStockMap.get(""+shape+"_Stock_"+lprpVal+"_DYS"))%> </td>
    <td>
    <%=util.nvl((String)allStockMap.get(""+shape+"_P1_"+lprpVal+"_PCT"))%> </td>
    <td>
        <a title="Packet Details" target="_blank" href="weeklySaleReport.do?method=pktDtlExcel&allsh=<%=shape%>&alllab=<%=lprpVal%>&flg=W" >
    <%=util.nvl((String)allStockMap.get(""+shape+"_P1_"+lprpVal+"_DYS"))%> </a></td>
     <td>
     <%=util.nvl((String)allStockMap.get(""+shape+"_P2_"+lprpVal+"_PCT"))%></td>
    <td>
    <a title="Packet Details" target="_blank" href="weeklySaleReport.do?method=pktDtlExcel&allsh=<%=shape%>&alllab=<%=lprpVal%>&flg=S" >
    <%=util.nvl((String)allStockMap.get(""+shape+"_P2_"+lprpVal+"_DYS"))%> </a> </td>
  </tr>
  <%}%>
  <tr><td>100%</td><td class="ttrgt">TOTAL</td> 
  <td class="ttrgt">
  <a title="Packet Details" target="_blank" href="weeklySaleReport.do?method=pktDtlExcel&allsh=<%=shape%>&alllab=ALL&flg=Z" ><%=util.nvl((String)allStockMap.get(""+shape+"_Stock_TTLPCS"))%></a></td><td>100%</td> 
  <td class="ttrgt"><%=util.nvl((String)allStockMap.get(""+shape+"_Stock_TTLAVGDAY"))%></td><td class="ttrgt">100%</td>
  <td class="ttrgt">
    <a title="Packet Details" target="_blank" href="weeklySaleReport.do?method=pktDtlExcel&allsh=<%=shape%>&alllab=ALL&flg=W" >
  <%=util.nvl((String)allStockMap.get(""+shape+"_P1_TTLAVGDAY"))%></a></td><td class="ttrgt">100%</td>
  <td class="ttrgt">
      <a title="Packet Details" target="_blank" href="weeklySaleReport.do?method=pktDtlExcel&allsh=<%=shape%>&alllab=ALL&flg=S" >
  <%=util.nvl((String)allStockMap.get(""+shape+"_P2_TTLAVGDAY"))%></a></td>
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