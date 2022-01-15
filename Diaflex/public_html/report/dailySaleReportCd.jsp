<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page errorPage="../error_page.jsp" %>
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.ResultSet, java.util.ArrayList,java.util.Collections,java.util.ArrayList,java.util.HashMap,java.text.SimpleDateFormat, java.util.Calendar"%>
<jsp:useBean id="info" class="ft.com.InfoMgr" scope="session" />
<jsp:useBean id="util" class="ft.com.JspUtil" scope="page" />
<%@taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
 

<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>dailySaleReportCd</title>
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
 <body onfocus="<%=onfocus%>"  >
   <%
     HashMap dbinfo = info.getDmbsInfoLst();
     String cnt =(String)dbinfo.get("CNT");
 %>
   <table cellpadding="" cellspacing="" width="80%" class="mainbg">
   <tr>
    <td height="103" valign="top">
   <jsp:include page="/header.jsp" />
    
    </td>
  </tr>
    <tr>
    <td height="5" valign="top" class="greyline"><img src="../images/grey_linebig.png" width="685" height="5" align="right" /></td>
  </tr>
   <tr><td valign="top" class="hedPg"  >
  <table cellpadding="1" cellspacing="5"><tr><td valign="middle">
  <img src="../images/line.jpg" border="0" /></td><td valign="middle"> <span class="pgHed">
     Daily Sale Report
 
  </span> </td>
</tr></table>
  </td>
  </tr><tr>
<tr>
   <td valign="top" class="hedPg">
       <html:form action="report/dailySaleReportCD.do?method=fetch" method="post">
   <table><tr>
    <td nowrap="nowrap">Date From : </td><td nowrap="nowrap">
    <html:text property="value(dtefr)" styleClass="txtStyle"  styleId="dtefr"  size="12"  /> <a href="#" style="margin-top:5px;"  onClick="setYears(1900, '<%=info.getCurrentYear()%>' );showCalender(this, 'dtefr');">
                <img src="<%=info.getReqUrl()%>/images/calender.png"></a></td>
                <td nowrap="nowrap">Date To : </td><td nowrap="nowrap">
    <html:text property="value(dteto)" styleClass="txtStyle"  styleId="dteto"  size="12"  /> <a href="#"  onClick="setYears(1900, '<%=info.getCurrentYear()%>' );showCalender(this, 'dteto');">
                <img src="<%=info.getReqUrl()%>/images/calender.png"></a></td>
       <td>Jangad Par:</td><td> <html:text property="value(jper)" styleClass="txtStyle"  styleId="jper"  size="8"  /></td>
       <td>Jangad selection :</td><td> <html:text property="value(jSel)" styleClass="txtStyle"  styleId="jSel"  size="8"  /></td>
                <td>  <html:submit property="value(submit)" value="Fetch" styleClass="submit"/></td>

</tr></table></html:form>
   </td></tr>
   <%
      ArrayList itemHer = new ArrayList();
  ArrayList pktDtlList = new ArrayList();
  HashMap dataDtl = (HashMap)request.getAttribute("DtlData");
      String view = (String)request.getAttribute("view");
  if(view!=null){
   if(dataDtl!=null && dataDtl.size()>0){
      ArrayList pktList = (ArrayList)dataDtl.get("pktList");
   if(pktList!=null && pktList.size()>0){

   String pByr="";
   %>
<tr>
   <td valign="top" class="hedPg">
   <table><tr><td>
  <table class="grid1">
  <%HashMap isDlyCD = new HashMap();
  isDlyCD.put("CDRPT", "Y");
  pktDtlList.add(isDlyCD);
  pktDtlList.add(util.nvl((String)dataDtl.get("JP")));%>
   <tr><th>Jangad Par:</th><td><%=util.nvl((String)dataDtl.get("JP"))%></td></tr>
     <%pktDtlList.add(util.nvl((String)dataDtl.get("JS")));%>

      <tr><th>Jangad selection :</th><td><%=util.nvl((String)dataDtl.get("JS"))%></td></tr>
      <%       HashMap B2CMAP = (HashMap)dataDtl.get("B2CMAP");
      pktDtlList.add(B2CMAP);
      %>
      <tr><th>JB2: </th><td><%=util.nvl((String)B2CMAP.get("ttlVlu"))%></td></tr>

      <%
      ArrayList NONB2CMAP = (ArrayList)dataDtl.get("NONB2CMAP");
      pktDtlList.add(NONB2CMAP);
      for(int i=0;i<NONB2CMAP.size();i++){
       HashMap nonB2c = (HashMap)NONB2CMAP.get(i);
      %>
         <tr><th><%=util.nvl((String)nonB2c.get("brcNme"))%></th><td><%=util.nvl((String)nonB2c.get("ttlVlu"))%></td></tr>

      <%}%>
   <% HashMap NC = (HashMap)dataDtl.get("NC");
   pktDtlList.add(NC);
   %>

   <tr><th>JMF ST :</th><td><%=util.nvl((String)NC.get("ttlVlu"))%></td></tr>
      <% HashMap CD = (HashMap)dataDtl.get("C");
     pktDtlList.add(CD);
      %>

   <tr><th>JDS ST :</th><td><%=util.nvl((String)CD.get("ttlVlu"))%></td></tr>

   </table></td><td valign="bottom">   Create Excel <img src="../images/ico_file_excel.png" border="0" onclick="goTo('<%=info.getReqUrl()%>/report/dailySaleReportCD.do?method=createDtlXL')"/>
</td></tr></table></td>
  </tr>
<tr>
   <td valign="top" class="hedPg">
  
    <table class="grid1">
   <tr><th>Buyer</th><th></th><th>PACK NO</th><th>CTS PAR</th><th>CTS SEL</th><th>RATE RS</th><th>RATE $</th><th>A/L%</th><th>A/L%</th>
   <th>X</th><th>REJ %</th><th>SEL PUR</th><th>SEL SEL</th><th>BROKER</th><th>DAYS</th><th>REMARKS</th></tr>
   
  
   <%
   itemHer.add("BUYER");
   itemHer.add("PACKETCODE");
   itemHer.add("PACK NO");
   itemHer.add("CTS PAR");
   itemHer.add("CTS SEL");
   itemHer.add("RATE RS");
   itemHer.add("RATE $");
   itemHer.add("A/L %");
   itemHer.add("A/L %");
   itemHer.add("X");
   itemHer.add("REJ %");
   itemHer.add("SEL PUR");
   itemHer.add("SEL SEL");
   itemHer.add("BROKER");
   itemHer.add("DAYS");
   itemHer.add("REMARKS");
   for(int i=0;i<pktList.size();i++){
   HashMap pktMap = (HashMap)pktList.get(i);
   String lbyr = util.nvl((String)pktMap.get("BYR"));
   String dByr="";
   if(!pByr.equals(lbyr)){
    dByr=lbyr;
    pByr=lbyr;
   }
     HashMap pktDtl = new HashMap();
     pktDtl.put("BUYER", dByr);
     pktDtl.put("PACKETCODE",util.nvl((String)pktMap.get("VNM")));
     pktDtl.put("PACK NO", util.nvl((String)pktMap.get("PACKNO")));
     pktDtl.put("CTS PAR", util.nvl((String)pktMap.get("CTSPUR")));
     pktDtl.put("CTS SEL",util.nvl((String)pktMap.get("CTSSEL")));
     pktDtl.put("RATE RS",util.nvl((String)pktMap.get("RTE")));
     pktDtl.put("RATE $",util.nvl((String)pktMap.get("RTEUSD")));
     pktDtl.put("A/L %",util.nvl((String)pktMap.get("AL1")));
     pktDtl.put("A/L %",util.nvl((String)pktMap.get("AL")));
     pktDtl.put("X",util.nvl((String)pktMap.get("EXHRTE")));
     pktDtl.put("REJ %",util.nvl((String)pktMap.get("REJPER")));
     pktDtl.put("SEL PUR",util.nvl((String)pktMap.get("SELPUR")));
     pktDtl.put("SEL SEL",util.nvl((String)pktMap.get("SEL")));
     pktDtl.put("BROKER",util.nvl((String)pktMap.get("SB")));
     pktDtl.put("DAYS",util.nvl((String)pktMap.get("TRM")));
     pktDtl.put("REMARKS", util.nvl((String)pktMap.get("RMK")));
  pktDtlList.add(pktDtl);

   %>
   <tr>
   <td><%=dByr%></td>
   <td><%=util.nvl((String)pktMap.get("VNM"))%></td>
   <td><%=util.nvl((String)pktMap.get("PACKNO"))%></td>
      <td><%=util.nvl((String)pktMap.get("CTSPER"))%></td>

      <td><%=util.nvl((String)pktMap.get("CTSSEL"))%></td>
       <td><%=util.nvl((String)pktMap.get("RTE"))%></td>
   <td><%=util.nvl((String)pktMap.get("RTEUSD"))%></td>
  
      <td><%=util.nvl((String)pktMap.get("AL1"))%></td>
   <td><%=util.nvl((String)pktMap.get("AL"))%></td>
   <td><%=util.nvl((String)pktMap.get("EXHRTE"))%></td>
   <td><%=util.nvl((String)pktMap.get("REJPER"))%></td>
   <td><%=util.nvl((String)pktMap.get("SELPUR"))%></td>
     <td><%=util.nvl((String)pktMap.get("SEL"))%></td>

   <td><%=util.nvl((String)pktMap.get("SB"))%></td>
   <td><%=util.nvl((String)pktMap.get("TRM"))%></td>
   <td><%=util.nvl((String)pktMap.get("RMK"))%></td>
   </tr>
   
   <%}%>
   </table></td></tr>
   <%
    session.setAttribute("itemHdr", itemHer);
    session.setAttribute("pktDtlList", pktDtlList);
         
   }else{%>
   <tr>
   <td valign="top" class="hedPg">
  Sorry no result found..</td></tr>
   <%}}}%>
  <tr>
  <td>    <jsp:include page="/footer.jsp" />
  </td>
  </tr>
  </table>
  
    <%@include file="/calendar.jsp"%>

  
  </body>
</html>