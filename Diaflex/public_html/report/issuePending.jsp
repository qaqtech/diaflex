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
  
  <title>Pending Issue Detail</title>
  <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/bse.js?v=<%=info.getJsVersion()%> " > </script>
<link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/bse.css?v=<%=info.getJsVersion()%>"/>
<script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/ajax.js?v=<%=info.getJsVersion()%> " > </script>
   <script language="javaScript" type="text/javascript" src="<%=info.getReqUrl()%>/calendar/calendar.js"></script>
   <link href="<%=info.getReqUrl()%>/calendar/calendar.css" rel="stylesheet" type="text/css">
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
  Pending Issue Detail </span> </td>
</tr></table>
  </td>
  </tr>
  <tr>
  <td valign="top" class="hedPg">
     <html:form action="/report/assortLabPending.do?method=fecth" method="post" >
 
   <table>
   <%ArrayList sttList = (ArrayList)session.getAttribute("sttLst");
   if(sttList!=null && sttList.size()>0){
   %>
    <tr><td>Status</td><td>
    <table><tr>
   <% for(int n=0;n<sttList.size();n++){
  ArrayList sttDtl = (ArrayList)sttList.get(n);
  String grp1 = (String)sttDtl.get(0);
  String dsc = (String)sttDtl.get(1);
  %>
  <td><html:radio property="value(stt)" name="assortLabPenForm"   value="<%=grp1%>" /> </td><td><%=dsc%></td>
  <%}%></tr></table>
    </tr>
   <%}%>
    <tr><td>Process </td>
   <td>
   <html:select property="value(mprcIdn)"  styleId="mprcIdn" name="assortLabPenForm"    >
           <html:option value="0" >--select--</html:option>
            <html:optionsCollection property="mprcList" name="assortLabPenForm" 
            label="prc" value="mprcId" />
    </html:select>
   </td></tr>
   <tr>
    <td>
    Employee : </td>
   <td>
   <html:select property="value(empIdn)"  styleId="empIdn" name="assortLabPenForm"  >
           <html:option value="0" >--select--</html:option>
            <html:optionsCollection property="empList" name="assortLabPenForm" 
            label="emp_nme" value="emp_idn" />
            </html:select>
   </td>
   </tr>
   <tr><td colspan="2">
   <table><tr>
   <td>Issue</td><td> From : </td><td>
   <html:text property="value(frmdte)" name="assortLabPenForm" styleId="frmdte"/>
                <img src="<%=info.getReqUrl()%>/images/calender.png" onClick="setYears(2011, <%=info.getCurrentYear()%>);showCalender(this, 'frmdte');">
   </td><td>To : </td><td>
   <html:text property="value(todte)" name="assortLabPenForm" styleId="todte"/>
                <img src="<%=info.getReqUrl()%>/images/calender.png" onClick="setYears(2011, <%=info.getCurrentYear()%>);showCalender(this, 'todte');">

   </td>
   
   </tr></table></td></tr>
    <tr><td colspan="2" align="center"><html:submit property="value(srch)" value="Fetch" styleClass="submit" /></td> </tr>
  
   </table>
 </html:form>
  </td></tr>
  <%
  String view = (String)request.getAttribute("view");
  ArrayList pktDtlList = (ArrayList)session.getAttribute("pktList");
  ArrayList itemHdr = new ArrayList();
  if(view!=null){
  if(pktDtlList!=null && pktDtlList.size()>0){
   ArrayList asViewPrp = (ArrayList)session.getAttribute("asViewLst");
   HashMap totals = (HashMap)request.getAttribute("totalMap");
   int lstSize = asViewPrp.size()+2;
   %>
   
   <tr><td valign="top" class="hedPg" >
   <table>
   <tr><td>Total :&nbsp;&nbsp;</td><td><span id="ttlqty"> <%=totals.get("qty")%>&nbsp;&nbsp;</span></td><td>&nbsp;Cts:&nbsp;</td><td><span id="ttlcts"><%=totals.get("cts")%>&nbsp;&nbsp;</span></td>
   <td>&nbsp;Value:&nbsp;</td><td><span id="ttlcts"><%=totals.get("vlu")%>&nbsp;&nbsp;</span></td>
   </tr>
   </table>
   </td></tr>
  
   <tr><td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Create Excel <img src="../images/ico_file_excel.png" onclick="goTo('assortLabPending.do?method=createXL','','')" border="0"/>
   <%
  ArrayList rolenmLst=(ArrayList)info.getRoleLst();
  String usrFlg=util.nvl((String)info.getUsrFlg());
  if(usrFlg.equals("SYS") || rolenmLst.contains("ADMIN")){
  %>
  &nbsp;&nbsp;<img src="../images/edit.jpg" title="Edit Properties Module" onclick="newWindow('<%=info.getReqUrl()%>/dynamicProperty.do?method=load&mdl=AS_VIEW&sname=asViewLst&par=A')" border="0" width="15px" height="15px"/> 
  <%}%>
   </td></tr>
  <tr><td valign="top" class="hedPg" >
  <table class="grid1"><tr>
  <th>Employee Name</th><th>Issue Id</th><th>Issue Date</th><th>Pending Days</th><th>Process</th><th>Packet Code</th><th>Status </th>
   <%
   itemHdr.add("emp");
   itemHdr.add("issIdn");
   itemHdr.add("issdte");
   itemHdr.add("pnddays");
   itemHdr.add("prc");
   itemHdr.add("vnm");
   itemHdr.add("stt");
   for(int i=0; i < asViewPrp.size() ; i++){
   String prp = (String)asViewPrp.get(i);
   %>
   <th><%=prp%> </th>
   <%
   itemHdr.add(prp);
   }
   itemHdr.add("rte");
   itemHdr.add("amt");
   session.setAttribute("itemHdr", itemHdr);
   %>
   <th>Rte</th>
    <th>Value</th>
   </tr>
  <%
  String pemp="";
  for(int j=0;j < pktDtlList.size();j++){ 
  HashMap pktDtl = (HashMap)pktDtlList.get(j);
  String lemp = (String)pktDtl.get("emp");
   String prc = (String)pktDtl.get("prc");
   String stt = (String)pktDtl.get("stt");
   String issIdn = (String)pktDtl.get("issIdn");
   String  dEmp="";
  if(pemp.equals("")){
  pemp = lemp;
  dEmp = lemp;
  }
  String vnm = (String)pktDtl.get("vnm");
  if(!pemp.equals(lemp)){
  %>
  <tr><td></td><td bgcolor="White" colspan="<%=lstSize%>">
  <table>
   <tr><td>Total :&nbsp;&nbsp;</td><td><span id="ttlqty"> <B><%=totals.get(pemp+"_Q")%></b></span></td><td>&nbsp;Cts:&nbsp;</td><td><span id="ttlcts"> <B><%=totals.get(pemp+"_C")%></b></span></td> 
   <td>&nbsp;Value:&nbsp;</td><td><span id="ttlcts"> <B><%=totals.get(pemp+"_A")%></b></span></td>
   </tr>
   </table>
  </td> </tr>
  <%
  pemp = lemp;
  dEmp = lemp;
  }%>
  <tr>
  <td><%=dEmp%> </td><td><%=issIdn%></td><td><%=util.nvl((String)pktDtl.get("issdte"))%></td><td><%=util.nvl((String)pktDtl.get("pnddays"))%></td><td><%=prc%></td><td><%=vnm%></td><td><%=stt%></td>
   <%
   for(int i=0; i < asViewPrp.size() ; i++){
   String prp = (String)asViewPrp.get(i);
   %>
   <td><%=pktDtl.get(prp)%></td>
   <%}%>
   <td><%=pktDtl.get("rte")%></td>
   <td><%=pktDtl.get("amt")%></td>
  </tr>
  <%}%>
  <%if(!pemp.equals("")){%>
   <tr><td></td><td bgcolor="White" colspan="<%=lstSize%>">
  <table>
   <tr><td>Total :&nbsp;&nbsp;</td><td><span id="ttlqty"> <B><%=totals.get(pemp+"_Q")%></b></span></td><td>cts&nbsp;&nbsp;</td><td><span id="ttlcts"> <B><%=totals.get(pemp+"_C")%></b></span></td>
     <td>Value&nbsp;&nbsp;</td><td><span id="ttlcts"> <B><%=totals.get(pemp+"_A")%></b></span></td>
 
   </tr>
   </table>
  <%}%>
  </table>
  
  </td></tr>
  <%}else{%><tr><td valign="top" class="hedPg">
  Sorry no result found.</td></tr>
  <%} }%>
  <tr>
    <td><jsp:include page="/footer.jsp" /> </td>
    </tr>
  </table>
  
  </body>
    <%@include file="../calendar.jsp"%>
</html>