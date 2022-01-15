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
  
  <title>Verification Packet Wise Verify Fail</title>
  <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/bse.js?v=<%=info.getJsVersion()%> " > </script>
 <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/bse.css?v=<%=info.getJsVersion()%>"/>
 <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/ajax.js?v=<%=info.getJsVersion()%> " > </script>
  <script language="javaScript" type="text/javascript" src="<%=info.getReqUrl()%>/calendar/calendar.js"></script>
   <link href="<%=info.getReqUrl()%>/calendar/calendar.css" rel="stylesheet" type="text/css">
    <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/style.css?v=<%=info.getJsVersion()%>"/>
  </head>
 
   <%
    
   ArrayList memoRtnLst = info.getMomoRtnLst();
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
  <img src="../images/line.jpg" border="0" /></td><td valign="middle"> <span class="pgHed">
 Verification Packet Wise Verify Fail</span> </td>
 <%String packetVerify = util.nvl((String)request.getAttribute("PacketVerify"));
if(!packetVerify.equals("")){
%>
  <td><span class="redLabel" style="font-size:12px;" >Verification Sucess of Above Packet: : <%=packetVerify%></span></td>
<%}%>
<%String Packetfail = util.nvl((String)request.getAttribute("Packetfail"));
if(!Packetfail.equals("")){
%>
  <td><span class="redLabel" style="font-size:12px;" >Verification fail of Above Packet: : <%=Packetfail%></span></td>
<%}%>
</tr></table>
  </td>
  </tr>
  <%
   String msg = (String)request.getAttribute("msg");
   if(msg!=null){%>
     <tr><td valign="top" class="tdLayout"><%=msg%></td></tr>
   <%}%>
  <%
  ArrayList prpDspBlocked = info.getPageblockList();
  ArrayList pktDtl= (ArrayList)request.getAttribute("pktDtl");   
  ArrayList vwPrpLst = (ArrayList)session.getAttribute("VRFailViewLst");
  HashMap mprp = info.getMprp();
  int colspn=(vwPrpLst.size())+3;
  if(pktDtl!=null && pktDtl.size()>0){
    %>
   <tr><td>&nbsp;</td></tr>  
 <tr><td valign="top" class="tdLayout">
<form action="verificationhk.do" name="verification">
  <input type="hidden" name="method" value="verifySIZEPKTFAIL"/>
<table class="dataTable">
 <tr> <td colspan="8">Packet Wise Verify/Fail</td> </tr>
 <tr> 
 <th>Pkt No.</th>
         <%for(int j=0; j < vwPrpLst.size(); j++ ){
    String prp = (String)vwPrpLst.get(j);
    if(prpDspBlocked.contains(prp)){
    }else{
     String hdr = util.nvl((String)mprp.get(prp));
    String prpDsc = util.nvl((String)mprp.get(prp+"D"));
    if(hdr == null) {
        hdr = prp;
       }  

%>
<th title="<%=prpDsc%>"><%=hdr%></th>

<%}}%>        
<th><input  value="None" name="Verify" id="None" type="radio" checked="checked"  onclick="CheckAllradioBtn('None' ,'None_')" />None</th>
 <th><input  value="Verify" name="Verify" id="Verify" type="radio" onclick="CheckAllradioBtn('Verify' ,'Verify_')" />Verify</th>
 <th><input value="fail"  name="Verify" id="fail" type="radio" onclick="CheckAllradioBtn('fail' ,'fail_')" />Fail.</th>
 </tr>
 <%for(int i=0;i<pktDtl.size();i++){
 HashMap pktPrpMap=(HashMap)pktDtl.get(i);
 String stkidn=(String)pktPrpMap.get("stk_idn");
 %>
 <tr>
 <td><%=pktPrpMap.get("vnm")%></td>
 <%for(int j=0; j < vwPrpLst.size(); j++ ){
    String prp = (String)vwPrpLst.get(j);
    if(prpDspBlocked.contains(prp)){
    }else{
    %>
    <td><%=pktPrpMap.get(prp)%></td>
<%}}%>
<td><input value="None_<%=stkidn%>" type="radio" name="VFY_<%=stkidn%>" checked="checked" id="None_<%=stkidn%>"/></td>
  <td><input value="Verify_<%=stkidn%>" type="radio" name="VFY_<%=stkidn%>" id="Verify_<%=stkidn%>"/></td>
 <td><input value="fail_<%=stkidn%>" type="radio" name="VFY_<%=stkidn%>" id="fail_<%=stkidn%>"/></td>
 </tr>
  <%}%>
 </table>
<input type="submit" value="Verification" class="submit"/>
</form>
</td>
</tr>  
<%}else{%>
<tr><td>Sorry No Result Found</td></tr>
<%}%>

  
<tr>
    <td><jsp:include page="/footer.jsp" /> </td>
    </tr>
  </table>
  
  </body>
  <%@include file="../calendar.jsp"%>
</html>