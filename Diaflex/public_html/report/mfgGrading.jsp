<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page errorPage="../error_page.jsp" %>
<%@ page contentType="text/html;charset=windows-1252"%>
	<jsp:useBean id="info" class="ft.com.InfoMgr" scope="session" />
	<jsp:useBean id="util" class="ft.com.JspUtil" scope="page" />

<%@ page import="java.util.ArrayList,java.util.HashMap,java.sql.ResultSet,ft.com.*, java.util.Date, java.util.ArrayList, ft.com.dao.*"%>
<%@taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>Grading Detail For Manufacturing</title>
    <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/bse.css?v=<%=info.getJsVersion()%>"/>
    <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/general.css?v=<%=info.getJsVersion()%>"/>
     <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/style.css?v=<%=info.getJsVersion()%>"/>
      <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/bse.js?v=<%=info.getJsVersion()%> " > </script>
  </head>
        <%String logId=String.valueOf(info.getLogId());
        String onfocus="cook('"+logId+"')";
        %>
 <body onfocus="<%=onfocus%>" onload="MM_preloadImages('images/faunalogo.png')" topmargin="0" leftmargin="0" rightmargin="0" bottommargin="0">
<%
ArrayList stockList = (ArrayList)session.getAttribute("stockList");
HashMap allPageDtl= (info.getPageDetails() == null)?new HashMap():(HashMap)info.getPageDetails();
HashMap pageDtl=(HashMap)allPageDtl.get("GRIDING_DTATYP");        

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
  <img src="../images/line.jpg" border="0" /></td><td valign="middle"> <span class="pgHed">Grading Detail For Manufacturing</span> </td>
</tr></table>
  </td>
  </tr>
   <tr>
   
   <td valign="top" class="tdLayout">
   <table><tr><td>
   <html:form action="/report/orclReportAction.do?method=fetchmfgGrading" method="post" onsubmit="return validate_assort()">
   <table class="grid1">
   <tr><th>Generic Search
   <%
  ArrayList rolenmLst=(ArrayList)info.getRoleLst();
  String usrFlg=util.nvl((String)info.getUsrFlg());
  if(usrFlg.equals("SYS") || rolenmLst.contains("ADMIN")){
  %>
  <img src="../images/edit.jpg" title="Edit Properties Module" onclick="newWindow('<%=info.getReqUrl()%>/dynamicProperty.do?method=load&mdl=GRAD_MFG_SRCH&sname=mfgGNCSrch&par=GENERIC')" border="0" width="15px" height="15px"/> 
  <%}%>
   </th><th></th></tr>
   <tr><td colspan="2">
   <%
  ArrayList sttList = (ArrayList)session.getAttribute("sttLst");
  if(sttList!=null && sttList.size()>0){%>
  <table id="statusTbl"><tr><td>Status:</td>
 <% for(int n=0;n<sttList.size();n++){
  ArrayList sttDtl = (ArrayList)sttList.get(n);
  String grp1 = (String)sttDtl.get(0);
  String dsc = (String)sttDtl.get(1);
  String dscgrp1="value("+grp1+")";
  String chkid="STT_"+n;
  %>
  <td><html:checkbox property="<%=dscgrp1%>" styleId="<%=chkid%>" value="<%=grp1%>" /></td><td><%=dsc%></td>
  <%}%></tr></table><%}%>
   </td></tr>
  
  <tr><td>
  <jsp:include page="/genericSrch.jsp" /> </td>
  <td>

 <table>
 <tr>
 <td colspan="2">
  <html:radio property="value(srchRef)"  styleId="vnm" value="vnm" /> Packet Code.
 <html:radio property="value(srchRef)"  styleId="vnm" value="cert_no" /> Cert No.
 </td>
 </tr>
 <tr>
<td>Packet Id: </td>
<td><html:textarea property="value(vnm)" name="orclReportForm" cols="30" rows="15" styleId="pid"/></td>
</tr>
 </table>
 </td>
  </tr>
<tr>
<td colspan="2" align="center">
<html:submit property="mfg" value="Search Packet" styleClass="submit" />
</td>
</tr>
  </table>
   </html:form>
   </td></tr>
   
   <%
   String view = (String)request.getAttribute("view");
     ArrayList asViewPrp = (ArrayList)session.getAttribute("mfgViewLst");
     ArrayList rptTypList = (ArrayList)request.getAttribute("rptTypList");
     
   if(view!=null){
   
   if(stockList!=null && stockList.size()>0){
   %>
  <tr><td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Create Excel <img src="../images/ico_file_excel.png" onclick="goTo('orclReportAction.do?method=createGradingXL','','')" border="0"/> </td></tr>
   <tr>
   <td>
   <table class="grid1">
   <tr>
   <td rowspan="2" align="center" nowrap="nowrap">Packet Code</td>
   <td rowspan="2" align="center">Carat</td>
   <td rowspan="2" align="center">Shape</td>
   <td rowspan="2" align="center">PKTDATE</td>
   <%  if(rptTypList.contains("MFG")) {%>
   <td colspan="<%=asViewPrp.size()-4%>" align="center">SURAT GRADING</td>
   <% } 
   if(rptTypList.contains("FA")) {
   %>
   <td colspan="<%=asViewPrp.size()-4%>" align="center">MUMBAI GRADING</td>
   <% } 
   if(rptTypList.contains("LAB")) {
   %>
   <td colspan="<%=asViewPrp.size()-4%>" align="center">GIA/IGI GRADING</td>
   <% } %>
   <td>&nbsp;</td><td>&nbsp;</td>
   </tr>
   <tr>
   <%for(int i=0;i<rptTypList.size();i++){
    String lrpt = (String)rptTypList.get(i);
   for(int j=0;j<asViewPrp.size();j++){
   String lprp = (String)asViewPrp.get(j);
    if(!lprp.equals("SHAPE") && !lprp.equals("MEMO") && !lprp.equals("PKTDATE")){
    if((!lrpt.equals("MFG") || !lprp.equals("LAB")) && ((!lrpt.equals("FA") || !lprp.equals("LAB"))) && ((!lrpt.equals("LAB") || !lprp.equals("RTE")))){
   %>
   <td><%=lprp%></td>
   <%}}}}%>
   
   <td>status</td>
   <td>Employee</td>
   </tr>
<%for(int i=0;i<stockList.size();i++){
   HashMap dataDtl=new HashMap();
   dataDtl=(HashMap)stockList.get(i);
   String cert_lab=util.nvl((String)dataDtl.get("cert_lab"));
   %>
   <tr>
   <td nowrap="nowrap"><%=util.nvl((String)dataDtl.get("vnm"))%></td>
   <td align="right"><%=util.nvl((String)dataDtl.get("cts"))%></td>
   <td><%=util.nvl((String)dataDtl.get("shape"))%></td>
   <td><%=util.nvl((String)dataDtl.get("pktdate"))%></td>
    <%for(int x=0;x<rptTypList.size();x++){
    String lrpt = (String)rptTypList.get(x);
   for(int y=0;y<asViewPrp.size();y++){
   String lprp = (String)asViewPrp.get(y);
       if(!lprp.equals("SHAPE") && !lprp.equals("MEMO") && !lprp.equals("PKTDATE")){
          if((!lrpt.equals("MFG") || !lprp.equals("LAB")) && ((!lrpt.equals("FA") || !lprp.equals("LAB"))) && ((!lrpt.equals("LAB") || !lprp.equals("RTE")))){
                   if(lprp.indexOf("&A") > -1)
                    lprp=lprp.replaceAll("\\&A","A");
                   if(lprp.indexOf("-S") > -1)
                    lprp=lprp.replaceAll("\\-S","S");
   String val = util.nvl((String)dataDtl.get(lrpt+"_"+lprp));
   if((cert_lab.equals("NA") || cert_lab.equals("FAITH") || cert_lab.equals("HK")) && lrpt.equals("LAB")){
   val="";
   }
   %>
   <td><%=val%></td>
   <%}}}}%>
   
   <td><%=util.nvl((String)dataDtl.get("status"))%></td>
   <td nowrap="nowrap"><%=util.nvl((String)dataDtl.get("emp"))%></td>
   </tr>
   <%}%>
</table>
   </td>
   </tr>
   <%} else{%>
   <tr><td>Sorry no result found </td></tr>
   <%}
   }%>
   
   </table>
   
  
  
   <tr>
    <td><jsp:include page="/footer.jsp" /> </td>
    </tr>
  </table>

  </body>
</html>