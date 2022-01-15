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
  
  <title>Verification Size/Packet Wise</title>
  <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/bse.js?v=<%=info.getJsVersion()%> " > </script>
 <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/bse.css?v=<%=info.getJsVersion()%>"/>
 <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/ajax.js?v=<%=info.getJsVersion()%> " > </script>
  <script language="javaScript" type="text/javascript" src="<%=info.getReqUrl()%>/calendar/calendar.js"></script>
   <link href="<%=info.getReqUrl()%>/calendar/calendar.css" rel="stylesheet" type="text/css">
    <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/style.css?v=<%=info.getJsVersion()%>"/>
  </head>
 
   <%
  
   ArrayList memoRtnLst = info.getMomoRtnLst();
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
 Verification Size/Packet Wise</span> </td>
 <%String SizeVerify = util.nvl((String)request.getAttribute("SizeVerify"));
if(!SizeVerify.equals("")){
%>
  <td><span class="redLabel" style="font-size:12px;" >Verification Sucess of Above Size: : <%=SizeVerify%></span></td>
<%}%>
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
  
   <tr><td valign="top" class="hedPg">
    <html:form action="/assorthk/verificationhk.do?method=fetchSizePkt" method="post" onsubmit="return validate_verify()">
     <table  class="grid1">
  <tr><th>Generic Search </th></tr>
  <tr>
   <td>
   <jsp:include page="/genericSrch.jsp"/>
   </td></tr>
   <tr>   <td>Option&nbsp;
            <html:radio property="value(sizepkt)" styleId="SUMMARY" value="SUMMARY"/>Summary&nbsp;&nbsp;
            <html:radio property="value(sizepkt)" styleId="DETAIL"  value="DETAIL"/>Detail
            </td></tr>
   <tr><td align="center"><html:submit property="value(srch)" value="Fetch" styleClass="submit" /></td> </tr>
   </table>
 </html:form>
   </td></tr>
   <%
   String view  = (String)request.getAttribute("view");
   String option  = util.nvl((String)request.getAttribute("option"));
   String mfg_typ= (String)request.getAttribute("mfg");
   if(view!=null){
      HashMap prp = info.getPrp();
      ArrayList PrpDtl = (ArrayList)prp.get("DEPT"+"V");
   if(option.equals("SUMMARY")){
   ArrayList sizeDtl= (ArrayList)request.getAttribute("sizeDtl");
   HashMap dataMap= (HashMap)session.getAttribute("dataMap");
   HashMap stkDtl=null;
   int colspn=(PrpDtl.size()*2)+2;
   String PCS="";
      String CTS="";
   if(dataMap!=null && dataMap.size()>0){
   %>
   
 <tr><td>&nbsp;</td></tr>  
<tr><td valign="top" class="tdLayout">
<form action="verificationhk.do" name="verification" onsubmit="return chkSubmit();">
  <input type="hidden" name="method" value="verifySizeWise"/>
  <input type="hidden" name="mfg_typ" value="<%=mfg_typ%>"/>
<table class="dataTable">
 <tr> <td colspan="<%=colspn%>">Size Wise Details</td> </tr>
 <tr><th><input name="Allsizewisechk" id="Allsizewisechk" type="checkbox" onclick="CheckAllChkbox(this.id, 'sizewisechk')" /></th>
 <th>Size</th>
 <%for(int i=0;i<PrpDtl.size();i++){%>
 <th colspan="2"><%=PrpDtl.get(i)%></th>
 <%}%>     
 </tr>
 <tr>
 <td></td><td></td>
 <%for(int i=0;i<PrpDtl.size();i++){%>
 <th>Pcs</th><th>Cts</th>
 <%}%>
 </tr>

 <%
 for(int i=0;i<sizeDtl.size();i++){
 String sz=(String)sizeDtl.get(i);
%>
  <tr>
  <td><input value="<%=sz%>" type="checkbox" name="sizewisechk" id="<%=sz%>_sizewisechk"/></td>
  <td><%=sz%></td>
  <%for(int j=0;j<PrpDtl.size();j++){
  String dept=(String)PrpDtl.get(j);
  String key=sz+"_"+dept;
  PCS="0";CTS="0.0"; 
  stkDtl=new HashMap();
  stkDtl=(HashMap)dataMap.get(key);
  if(stkDtl!=null){
  PCS=util.nvl2((String)stkDtl.get("qty"),"0");
  CTS=util.nvl2((String)stkDtl.get("cts"),"0");}
  %>
  <td><%=PCS%></td>
  <td><%=CTS%></td>
   <%}
 }%>
 </tr>
</table>
<input type="submit" value="Proceed Verification" class="submit" onclick=""/>
</form>
</td>
</tr>
    <%}%>
    <tr><td>Sorry No Result Found</td></tr>
    <%}else{
ArrayList pktDtl= (ArrayList)request.getAttribute("pktDtl");  
if(pktDtl!=null && pktDtl.size()>0){
    %>
   <tr><td>&nbsp;</td></tr>  
 <tr><td valign="top" class="tdLayout">
<form action="verificationhk.do" name="verification">
  <input type="hidden" name="method" value="verifyPktWise"/>
   <input type="hidden" name="mfg_typ" value="<%=mfg_typ%>"/>
<table class="dataTable">
 <tr> <td colspan="8">Packet Wise Details</td> </tr>
  <tr><th><input  value="None" name="Verify" id="None" type="radio" checked="checked"  onclick="CheckAllradioBtn('None' ,'None_')" />None</th>
<th><input  value="Verify" name="Verify" id="Verify" type="radio" onclick="CheckAllradioBtn('Verify' ,'Verify_')" />Verify</th>
 <th><input value="fail"  name="Verify" id="fail" type="radio" onclick="CheckAllradioBtn('fail' ,'fail_')" />Fail.</th>
 
 <th>Memo No.</th>
 <th>Pkt No.</th>
 <th>Cts</th>
 <th>Dept.</th>
 <th>Size</th>   
 </tr>
 <%for(int i=0;i<pktDtl.size();i++){
 HashMap pktPrpMap=(HashMap)pktDtl.get(i);
 String stkidn=(String)pktPrpMap.get("stk_idn");
 %>
 <tr>
 <td><input value="None_<%=stkidn%>" type="radio" name="VFY_<%=stkidn%>" checked="checked" id="None_<%=stkidn%>"/></td>
 <td><input value="Verify_<%=stkidn%>" type="radio" name="VFY_<%=stkidn%>" id="Verify_<%=stkidn%>"/></td>
 <td><input value="fail_<%=stkidn%>" type="radio" name="VFY_<%=stkidn%>" id="fail_<%=stkidn%>"/></td>
 <td><%=pktPrpMap.get("memo")%></td>
 <td><%=pktPrpMap.get("vnm")%></td>
 <td><%=pktPrpMap.get("cts")%></td>
 <td><%=pktPrpMap.get("dept")%></td>
 <td><%=pktPrpMap.get("sz")%></td>
 </tr>

 <%}%>
 </table>
<input type="submit" value="Proceed Verification" class="submit"/>
</form>
</td>
</tr>  
 <%}%>
 <tr><td>Sorry No Result Found</td></tr>
 <%}%>
<%}%>




  <tr>
    <td><jsp:include page="/footer.jsp" /> </td>
    </tr>
  </table>
  
  </body>
  <%@include file="../calendar.jsp"%>
</html>