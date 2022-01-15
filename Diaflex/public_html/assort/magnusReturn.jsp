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
    <title>Assort Return</title>
    <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/bse.css?v=<%=info.getJsVersion()%>"/>
    <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/general.css?v=<%=info.getJsVersion()%>"/>
     <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/style.css?v=<%=info.getJsVersion()%>"/>
     <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/bse.js?v=<%=info.getJsVersion()%> " > </script>
     
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
  <img src="../images/line.jpg" border="0" /></td><td valign="middle"> <span class="pgHed">Magnus
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                             Return</span> </td>
</tr></table>
  </td>
  </tr>
  <%
  //util.updAccessLog(request,response,"Magnus Return", "Magnus Return");
   String vnmNtFnd = (String)request.getAttribute("vnmNotFnd");
     if(vnmNtFnd!=null){%>
    <tr><td valign="top" class="tdLayout"><span class="redLabel"><%=vnmNtFnd%></span></td></tr>
   <%}%>
   <tr>
   
   <td valign="top" class="tdLayout">
   <table><tr><td>
   <html:form action="assort/magnusReturn.do?method=fecth" method="post" onsubmit="return validate_Assortreturn()">
   <table><tr><td>Process : </td>
   <td>
   <html:select property="value(mprcIdn)"  styleId="mprcIdn" name="magnusReturnForm"   >
           <html:option value="0" >--select--</html:option>
            <html:optionsCollection property="mprcList" name="magnusReturnForm" 
            label="prc" value="mprcId" />
    </html:select>
   </td></tr>
   <tr>
    <td>
    Employee : </td>
   <td>
   <html:select property="value(empIdn)" styleId="empIdn" name="magnusReturnForm"   >
           <html:option value="0" >--select--</html:option>
            <html:optionsCollection property="empList" name="magnusReturnForm" 
            label="emp_nme" value="emp_idn" />
            </html:select>
   </td>
   </tr>
   <tr>
   <td>Issue Id:</td><td> <html:text property="value(issueId)" styleId="issueId"  name="magnusReturnForm"  /></td>
   </tr>
   <tr>
   <td>Barcode:</td><td> <html:textarea property="value(vnmLst)" cols="30" name="magnusReturnForm"  /></td>
   </tr>
    
   <tr>
   <td colspan="2">  <html:submit property="value(srch)" value="Fetch" styleClass="submit" /></td>
   </tr></table>
   </html:form>
   </td></tr>
    <%
   ArrayList msgLst = (ArrayList)request.getAttribute("msgList");
   if(msgLst!=null){
   for(int i=0;i<msgLst.size();i++){
   ArrayList msg = (ArrayList)msgLst.get(i);
   %>
    <tr><td valign="top" class="tdLayout"><span class="redLabel"><%=msg.get(1)%></span></td></tr>
   <%}}%>
   <%
   String view = (String)request.getAttribute("view");
   if(view!=null){
   ArrayList stockList = (ArrayList)session.getAttribute("AssortStockList");
   if(stockList!=null && stockList.size()>0){
   ArrayList prpDspBlocked = info.getPageblockList();
    ArrayList vwPrpLst = (ArrayList)session.getAttribute("asViewLst");
      HashMap totals = (HashMap)request.getAttribute("totalMap");
      ArrayList options = (ArrayList)request.getAttribute("OPTIONS");
       if(options == null)
        options = new ArrayList();


    HashMap mprp = info.getMprp();
    int sr = 0;
     int colSpan = vwPrpLst.size()+4;
   %>
  
 <tr><td>
   <table>
   <tr><td>Total :&nbsp;&nbsp;</td><td><span id="ttlqty"><%=totals.get("qty")%></span> &nbsp;&nbsp;</td><td>cts&nbsp;&nbsp;</td><td><span id="ttlcts"> <%=totals.get("cts")%></span> &nbsp;&nbsp;</td><td>Selected:&nbsp;&nbsp;</td><td> Total :&nbsp;&nbsp;</td> <td><span id="qtyTtl">0&nbsp;&nbsp;</span></td><td>Cts&nbsp;&nbsp;</td><td><span id="ctsTtl">0</span> </td> </tr>
  
   </table>
   </td></tr>
    <html:form action="assort/assortReturn.do?method=Return" method="post" onsubmit="return validate_issue('CHK_' , 'count')" >
  <tr><td><html:submit property="value(issue)" styleClass="submit" value="Return" /> </td></tr>
  
   <tr><td>
  
   <table class="grid1">
   <tr> <th><input type="checkbox" name="checkAll" id="checkAll" onclick="checkALL('CHK_','count')" /> </th><th>Sr</th>
       <th>Issue Id</th>
        <th>Packet No.</th>
        <%for(int j=0; j < vwPrpLst.size(); j++ ){
    String prp = (String)vwPrpLst.get(j);
    if(prpDspBlocked.contains(prp)){
        colSpan=colSpan-1;
       }else{
     String hdr = (String)mprp.get(prp);
    String prpDsc = (String)mprp.get(prp+"D");
    if(hdr == null) {
        hdr = prp;
       }  

%>
<th title="<%=prpDsc%>"><%=hdr%></th>

<%}}%> 
<th>Status</th>
<th> Edit</th>
</tr>
 <%
 for(int i=0; i < stockList.size(); i++ ){
 HashMap stockPkt = (HashMap)stockList.get(i);
 String stkIdn = (String)stockPkt.get("stk_idn");
 String cts = (String)stockPkt.get("cts");
 String issIdn = (String)stockPkt.get("issIdn");
 String onclick = "AssortRtnTotalCal(this,"+cts+","+issIdn+","+stkIdn+")";
 String target = "SC_"+stkIdn;
 sr = i+1;
 String checkFldId = "CHK_"+sr;
 String checkFldVal = "value("+stkIdn+")";
 %>
<tr>
<td><html:checkbox property="<%=checkFldVal%>" styleId="<%=checkFldId%>" name="magnusReturnForm" onclick="<%=onclick%>"  value="yes" /> </td>
<td><%=sr%><input type="hidden" id="CTS_<%=stkIdn%>"  value="<%=cts%>" /></td>
<td><%=stockPkt.get("issIdn")%></td>
<td><%=stockPkt.get("vnm")%></td>
<%for(int j=0; j < vwPrpLst.size(); j++ ){
    String prp = (String)vwPrpLst.get(j);
    if(prpDspBlocked.contains(prp)){
       }else{
    %>
    <td><%=stockPkt.get(prp)%></td>
<%}}%>
<td><table><tr><%
for(int j=0; j < options.size(); j++) {%>

<%
String lopt = (String)options.get(j);
String rfld = "value(STT_"+ stkIdn + ")";
%>
<td><html:radio property="<%=rfld%>" value="<%=lopt%>"/>&nbsp;<%=lopt%></td>
<%}
%>
</tr></table>

</td>
 <td> <a href="assortReturn.do?method=stockUpd&mstkIdn=<%=stkIdn%>&issIdn=<%=issIdn%>" onclick="setBGColor('<%=stkIdn%>','DV_')" target="<%=target%>">Edit</a></td>

</tr>
<tr><td colspan="<%=colSpan%>" style="display:none;" id="DV_<%=stkIdn%>">

<iframe name="<%=target%>" height="400" width="700" frameborder="0">

</iframe>
</td> </tr>
   <%}%>
   </table>
  
   </td></tr>
   <input type="hidden" name="count" id="count" value="<%=sr%>" />
  </html:form>
   <%}else{%>
   <tr><td>Sorry no result found </td></tr>
   <%}}%>
   
   
   </table>
   
  
  
   <tr>
    <td><jsp:include page="/footer.jsp" /> </td>
    </tr>
  </table>

  </body>
</html>