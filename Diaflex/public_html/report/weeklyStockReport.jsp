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
    <title>weeklyStockReport</title>
    <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/bse.css?v=<%=info.getJsVersion()%>"/>
<link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/style.css?v=<%=info.getJsVersion()%>"/>
   <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/bse.js?v=<%=info.getJsVersion()%> " > </script>


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
  <img src="../images/line.jpg" border="0" /></td><td valign="middle"> <span class="pgHed">Weekly Stock Report </span> </td>
</tr></table>
  </td>
  </tr>
  <tr>
  <td valign="top" class="tdLayout">
  <html:form action="/report/weeklyStockReport.do?method=fetch" method="post" >
  <table>
   <tr><td> DepartMent :</td><td>
   <html:select property="dept" name="weeklyReportForm" >
   <%for(int i=0;i<prpV.size();i++){
   String dept = (String)prpV.get(i);
    if(dept.contains("GIA")){
   %>
   <html:option value="<%=dept%>"  key="<%=dept%>" ></html:option>
   <%}}%>
    </html:select>
   </td>
   <td>Shape </td><td>
             <html:select property="value(shape)" styleId="shape" name="weeklyReportForm" >
             <html:option value="ALL" >ALL</html:option>
             <html:option value="ROUND">ROUND</html:option>
             <html:option value="FANCY">FANCY</html:option>
            </html:select>
   </td>
   <td> <html:submit value="Fetch" property="value(submit)" styleClass="submit" ></html:submit> </td>
   </tr></table></html:form>
   <tr>
  <td valign="top" class="tdLayout">
   <% String view =(String)request.getAttribute("view");
  if(view!=null){
  String shape="";
  HashMap dayGrpSh = (HashMap)session.getAttribute("DayShMap");
   HashMap ttShMap = (HashMap)session.getAttribute("ttlShMap");
  ArrayList shList = (ArrayList)session.getAttribute("shList");
  String shapefetch =util.nvl((String)request.getAttribute("shape"));
  ArrayList prpList = new ArrayList();
  prpList.add("SIZE");
  prpList.add("COL");
  prpList.add("CLR");
   ArrayList shapeList = new ArrayList();
   if(shapefetch.equals("ALL")){
   shapeList.add("ROUND");
   shapeList.add("FANCY");
   }else
   shapeList.add(shapefetch);
  int ttlPcs = (Integer)session.getAttribute("ttlPcs");
  ArrayList dayGrpList = (ArrayList)session.getAttribute("dayGrpList");
  %>
  <table>
  <tr><td>
  Create Excel &nbsp;&nbsp;&nbsp;
  <img src="../images/ico_file_excel.png" border="0" onclick="goTo('<%=info.getReqUrl()%>/report/weeklyStockReport.do?method=createXL&shape=<%=shapefetch%>','','')"/>
 
  </td></tr>
  <tr><td>
 <!-- Master Grid :---->
<% if(dayGrpSh!=null && dayGrpSh.size()>0){%>
<table class="grid3"><tr><th >Day</th>
 <% for(int i=0 ; i < shList.size() ; i++){
  String sh = util.nvl((String)shList.get(i));%>
  <th ><%=sh%></th>
  <%}%><th >Pcs</th><th >%</th></tr>
  <% for(int j=0 ; j < dayGrpList.size() ; j++){
  String day = util.nvl((String)dayGrpList.get(j));%>
  <tr> <td ><%=day%></td>
  <%
  int totalCntDy = 0;
   for(int i=0 ; i < shList.size() ; i++){
  String sh = util.nvl((String)shList.get(i));
  String cnt  = util.nvl((String)dayGrpSh.get(day+"_"+sh));
 if(!cnt.equals(""))
  totalCntDy = totalCntDy + Integer.parseInt(cnt);
  %>
  <td ><a  title="Packet Details" target="_blank" href="weeklyStockReport.do?method=pktDtlExcel&sh=<%=sh%>&days=<%=day%>" ><%=cnt%></a></td>
  <%}%>
  <td class="ttrgt">
  <a   title="Packet Details" target="_blank" href="weeklyStockReport.do?method=pktDtlExcel&sh=ALL&days=<%=day%>" ><%=totalCntDy%></a></td>
  <%double ttlDyPer = (Double.parseDouble(String.valueOf(totalCntDy))/Double.parseDouble(String.valueOf(ttlPcs)))*100 ;%>
  <td class="ttrgt"><%=Math.round(ttlDyPer)%> %</td>
  </tr>
  <%}%>
   <tr><td></td>
   <% for(int i=0 ; i < shList.size() ; i++){
  String sh = util.nvl((String)shList.get(i));
  String cnt = util.nvl((String)ttShMap.get(sh));
  %>
  <td class="ttrgt">
  <a   title="Packet Details" target="_blank" href="weeklyStockReport.do?method=pktDtlExcel&sh=<%=sh%>&days=ALL" ><%=cnt%></a>
  </td>
  <%}%><td class="ttrgt">
  <a   title="Packet Details" target="_blank" href="weeklyStockReport.do?method=pktDtlExcel&sh=ALL&days=ALL" ><%=ttlPcs%></a>
  </td><td class="ttrgt">100%</td></tr>
  </table>
  <%}%>
  </td></tr>
 
   <tr><td>
  <!--FANCY SHAPEWISE GRID -->
   <%
    HashMap fncyShMap= (HashMap)session.getAttribute("FncyShMap"); 
    ArrayList fncyShList =(ArrayList)session.getAttribute("FncyShList");
    if(fncyShList!=null && fncyShList.size() > 0){
  %>
  <table class="grid3">
 
  <tr><th >Fancy Shape</th><th >Amt</th><th >D.Damt</th><th >%</th><th >Days</th></tr>
  <%for(int i=0 ; i< fncyShList.size();i++){
  String lprpVal = util.nvl((String)fncyShList.get(i));
  %>
  <tr><td ><%=lprpVal%></td> 
  <td > 
  <a   title="Packet Details" target="_blank" href="weeklyStockReport.do?method=pktDtlExcel&sh=<%=lprpVal%>&days=ALL" ><%=util.nvl((String)fncyShMap.get("FANCY_"+lprpVal+"_VAL"))%></a>
  </td>
   <td ><%=util.nvl((String)fncyShMap.get("FANCY_"+lprpVal+"_DYSVAL"))%> </td>
    <td ><%=util.nvl((String)fncyShMap.get("FANCY_"+lprpVal+"_PCT"))%> </td>
    <td ><%=util.nvl((String)fncyShMap.get("FANCY_"+lprpVal+"_DYS"))%> </td> </tr>
  <%}%>
   <tr><td></td> 
  <td class="ttrgt">
  <a   title="Packet Details" target="_blank" href="weeklyStockReport.do?method=pktDtlExcel&sh=FANCY&days=ALL" ><%=util.nvl((String)fncyShMap.get("FANCY_TTLVAL"))%></a>
  </td> 
  <td class="ttrgt"><%=util.nvl((String)fncyShMap.get("FANCY_TTLDAYVAL"))%></td><td class="ttrgt">100%</td>
  <td class="ttrgt"><%=util.nvl((String)fncyShMap.get("FANCY_TTLDYS"))%></td>
  </tr>
  </table>
  <%}%>
  </td></tr>
  <tr><td>
  <table>
   <!--LAB Grid-->
  <tr>
  <%for(int k=0;k<shapeList.size();k++){
  shape = (String)shapeList.get(k);
  %>
  <td valign="top">
  
    <% HashMap  labWsDataMap = (HashMap)session.getAttribute("LabWsDataMap");
       ArrayList daysLabList = (ArrayList)session.getAttribute("DaysLabList");%>
       <%=shape%>
       <table class="grid3"><tr><th>Days</th><th  >GIA</th><th  >IGI</th><th>Pcs</th><th>Amt</th><th>%</th> </tr>
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
     <td>
     <a   title="Packet Details" target="_blank" href="weeklyStockReport.do?method=pktDtlExcel&labsh=<%=shape%>&days=<%=day%>&lab=GIA" ><%=util.nvl((String)labWsDataMap.get(day+"_"+shape+"_GIA_CNT"))%></a>
     </td>
     <td>
     <a   title="Packet Details" target="_blank" href="weeklyStockReport.do?method=pktDtlExcel&labsh=<%=shape%>&days=<%=day%>&lab=IGI" ><%=util.nvl((String)labWsDataMap.get(day+"_"+shape+"_IGI_CNT"))%></a>
     </td>
     <td >
     <a   title="Packet Details" target="_blank" href="weeklyStockReport.do?method=pktDtlExcel&labsh=<%=shape%>&days=<%=day%>&lab=ALL" ><%=util.nvl((String)labWsDataMap.get(day+"_"+shape+"_TTLCNT"))%></a>
     </td>
    
     <td ><%=util.nvl((String)labWsDataMap.get(day+"_"+shape+"_VAL"))%></td>
        <td ><%=util.nvl((String)labWsDataMap.get(day+"_"+shape+"_PCT"))%></td>
     </tr>
     <%}
     
     %>
     <tr><td></td>
     <td class="ttrgt">
     <a   title="Packet Details" target="_blank" href="weeklyStockReport.do?method=pktDtlExcel&labsh=<%=shape%>&days=ALL&lab=GIA" ><%=util.nvl((String)labWsDataMap.get(shape+"_GIA_CNT"))%></a>
     </td>
     <td class="ttrgt">
     <a   title="Packet Details" target="_blank" href="weeklyStockReport.do?method=pktDtlExcel&labsh=<%=shape%>&days=ALL&lab=IGI" ><%=util.nvl((String)labWsDataMap.get(shape+"_IGI_CNT"))%></a>
     </td>
     <td class="ttrgt">
     <a   title="Packet Details" target="_blank" href="weeklyStockReport.do?method=pktDtlExcel&labsh=<%=shape%>&days=ALL&lab=ALL" ><%=labCnt%></a>
     </td>
     <td class="ttrgt"><%=ttlVlu%></td><td class="ttrgt">100%</td></tr>
  </table>
  </td>
  <%}%>
  </tr>
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
  <tr><td colspan="6" class="lft" ><b><%=mprp.get(lprp+"D")%></b></td></tr>
  <tr><th></th><th >Pcs</th><th >Amt</th><th >D.Damt</th><th >%</th><th >Days</th> </tr>
  <%for(int j=0;j<lprpValLst.size();j++){
  String lprpVal = util.nvl((String)(String)lprpValLst.get(j));  %>
  <tr><td><b><%=lprpVal%></b></td>
  <td >
  <a   title="Packet Details" target="_blank" href="weeklyStockReport.do?method=pktDtlExcel&grpsh=<%=shape%>&grplprp=<%=lprp%>&grpval=<%=lprpVal%>" ><%=util.nvl((String)PrpWiseGd.get(""+shape+"_"+lprpVal+"_CNT"))%></a>
  </td>
   <td ><%=util.nvl((String)PrpWiseGd.get(""+shape+"_"+lprpVal+"_VAL"))%> </td>
   <td ><%=util.nvl((String)PrpWiseGd.get(""+shape+"_"+lprpVal+"_DYSVAL"))%> </td>
    <td ><%=util.nvl((String)PrpWiseGd.get(""+shape+"_"+lprpVal+"_PCT"))%> </td>
    <td ><%=util.nvl((String)PrpWiseGd.get(""+shape+"_"+lprpVal+"_DYS"))%> </td>
  </tr>
  <%}%>
  <tr><td></td>
  <td class="ttrgt">
  <a   title="Packet Details" target="_blank" href="weeklyStockReport.do?method=pktDtlExcel&grpsh=<%=shape%>&grplprp=<%=lprp%>&grpval=ALL" ><%=util.nvl((String)PrpWiseGd.get(""+shape+"_"+lprp+"_TTLCNT"))%></a>
  </td> 
  <td class="ttrgt"><%=util.nvl((String)PrpWiseGd.get(""+shape+"_"+lprp+"_TTLVAL"))%></td> 
  <td class="ttrgt"><%=util.nvl((String)PrpWiseGd.get(""+shape+"_"+lprp+"_TTLDAYVAL"))%></td><td class="ttrgt">100%</td>
  <td class="ttrgt"><%=util.nvl((String)PrpWiseGd.get(""+shape+"_"+lprp+"_TTLDYS"))%></td>
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
  <tr><td colspan="5"  class="lft"><b>All Stock (Live)</b></td></tr>
  <tr><th></th><th>Amt</th><th >D.Damt</th><th >%</th><th >Days</th></tr>
  <%for(int i=0 ; i< allStkPrpList.size();i++){
  String lprpVal = util.nvl((String)(String)allStkPrpList.get(i));
  lprpVal = lprpVal.replace("SH", shape);
  %>
  <tr><td ><%=lprpVal%></td> 
  <td >
  <a title="Packet Details" target="_blank" href="weeklyStockReport.do?method=pktDtlExcel&allsh=<%=shape%>&alllab=<%=lprpVal%>" ><%=util.nvl((String)allStockMap.get(""+shape+"_"+lprpVal+"_VAL"))%></a> 
  </td>
   <td ><%=util.nvl((String)allStockMap.get(""+shape+"_"+lprpVal+"_DYSVAL"))%> </td>
    <td ><%=util.nvl((String)allStockMap.get(""+shape+"_"+lprpVal+"_PCT"))%> </td>
    <td > <%=util.nvl((String)allStockMap.get(""+shape+"_"+lprpVal+"_DYS"))%> </td> </tr>
  <%}%>
   <tr><td></td> 
  <td class="ttrgt">
  <a title="Packet Details" target="_blank" href="weeklyStockReport.do?method=pktDtlExcel&allsh=<%=shape%>&alllab=ALL" ><%=util.nvl((String)allStockMap.get(""+shape+"_TTLVAL"))%></a> 
  </td> 
  <td class="ttrgt"><%=util.nvl((String)allStockMap.get(""+shape+"_TTLDAYVAL"))%></td><td class="ttrgt">100%</td>
  <td class="ttrgt"><%=util.nvl((String)allStockMap.get(""+shape+"_TTLDYS"))%></td>
  </tr>
  </table>
  <%}%>
  </td>
  <%}%>
  </tr>
  
  <!--Pricee diff -->
  
    <tr>
      <%for(int k=0;k<shapeList.size();k++){
  shape = (String)shapeList.get(k);
   %>
  <td valign="top">
  
  <%
    HashMap dys_grpMap= (HashMap)session.getAttribute("Dys_grpMap"); 
    ArrayList dys_grpList =(ArrayList)session.getAttribute("Dys_grpList");
    if(dys_grpList!=null && dys_grpList.size() > 0){
  %>
  <%=shape%>
  <table class="grid3">
 <tr><th colspan="3">Price Difference</th></tr>
  <tr><th>Days</th><th >%</th><th>FANCY</th></tr>
  <%for(int i=0 ; i< dys_grpList.size();i++){
  String lprpVal = util.nvl((String)(String)dys_grpList.get(i));
  %>
  <tr><td><%=lprpVal%></td>
    <td ><%=util.nvl((String)dys_grpMap.get(""+shape+"_"+lprpVal))%> </td>
    <td></td> </tr>
  <%}%>
   <tr>
  <td></td> 
  <td class="ttrgt"><%=util.nvl((String)dys_grpMap.get(""+shape+"_AVGPCT"))%></td>
  <td>0</td>
  </tr>
  </table>
  <%}%>
  </td><%}%>
  </tr>
  
  </table>
  
  
  
  </td></tr>
  </table>
  <%}%>
  

  
   
   
   
   <tr>
    <td><jsp:include page="/footer.jsp" /> </td>
    </tr>
  
  </table>
  
  
  
  </body>
</html>