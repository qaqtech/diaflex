<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=windows-1252"%>
<jsp:useBean id="info" class="ft.com.InfoMgr" scope="session" />
<jsp:useBean id="util" class="ft.com.JspUtil" scope="page" />

<%@ page import="java.util.ArrayList,java.util.HashMap,java.sql.ResultSet,ft.com.*, java.util.Date, java.util.ArrayList, ft.com.dao.*"%>
<%@taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>Lab Issue</title>
    <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/bse.css?v=<%=info.getJsVersion()%>"/>
    <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/general.css?v=<%=info.getJsVersion()%>"/>
     <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/style.css?v=<%=info.getJsVersion()%>"/>
      <script src="../scripts/bse.js" type="text/javascript"></script>
       <script src="../scripts/assortScript.js" type="text/javascript"></script>
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
  <img src="../images/line.jpg" border="0" /></td><td valign="middle"> <span class="pgHed">Box</span> </td>
</tr></table>
  </td>
  </tr>
  <%
   String vnmNtFnd = (String)request.getAttribute("vnmNotFnd");
     if(vnmNtFnd!=null){%>
    <tr><td valign="top" class="tdLayout"><span class="redLabel"><%=vnmNtFnd%></span></td></tr>
   <%}%>
   <%
   String msg = (String)request.getAttribute("msg");
   if(msg!=null){
   String issueIdn = (String)request.getAttribute("issueIdn");
   String lab = (String)request.getAttribute("Lab");
   String url = info.getReqUrl()+"/excel/labxl?issueIdn="+issueIdn+"&lab="+lab;
   %>
    <tr><td valign="top" class="tdLayout"><span class="redLabel"><%=msg%></span></td></tr>
    <tr><td><span class="txtLink"><a href="<%=url%>" target="_blank"> Click here to genrate Lab Excel File </a></span></td></tr>
   <%}%>
  <tr>
  
  <td valign="top" class="tdLayout">
  <table>
  <tr><td>
  
  </td></tr>
  
   <%
   String view = (String)request.getAttribute("view");
   String ctwt=null;
   if(view!=null){
   ArrayList prpDspBlocked = info.getPageblockList();
   ArrayList stockList = (ArrayList)session.getAttribute("StockList");
  if(stockList!=null && stockList.size()>0){
    ArrayList vwPrpLst = (ArrayList)session.getAttribute("LabViewLst");
     HashMap totals = (HashMap)request.getAttribute("totalMap");
    HashMap mprp = info.getMprp();
    int sr = 0;
   %>
   
   <html:form action="lab/labIssue.do?method=Issue" onsubmit="return validate_issue('CHK_' , 'count')"  method="post">
   <html:hidden property="value(prcId)" name="labIssueForm" />
   <html:hidden property="value(empId)" name="labIssueForm" />
   <tr>
   <td>
   <table>
  
   <tr><td>
   <table>
   <tr><td>Total :&nbsp;&nbsp;</td><td><span id="ttlqty"> <%=totals.get("qty")%>&nbsp;&nbsp;</span></td><td>cts&nbsp;&nbsp;</td><td><span id="ttlcts"><%=totals.get("cts")%>&nbsp;&nbsp;</span></td><td>Selected:&nbsp;&nbsp;</td><td> Total :&nbsp;&nbsp;</td> <td><span id="qtyTtl">0&nbsp;&nbsp;</span></td><td>Cts&nbsp;&nbsp;</td><td><span id="ctsTtl">0</span> </td> 
 
    </tr>
  </table>
   </td></tr>
   <tr><td>
   </td></tr>
   </table></td></tr>
   <tr><td>
   <table class="grid1">
   <tr> <th><input type="checkbox" name="checkAll" id="checkAll"  onclick="checkALL('CHK_','count')"  /> </th><th>Sr</th>
        <th>Packet No.</th>
        <%for(int j=0; j < vwPrpLst.size(); j++ ){
    String prp = (String)vwPrpLst.get(j);
    if(prpDspBlocked.contains(prp)){
    }else{
     String hdr = (String)mprp.get(prp);
    String prpDsc = (String)mprp.get(prp+"D");
    if(hdr == null) {
        hdr = prp;
       }  

%>
<th title="<%=prpDsc%>"><%=hdr%></th>

<%}}%>
<th>Service </th>
</tr>
 <%
 
 for(int i=0; i < stockList.size(); i++ ){
 HashMap stockPkt = (HashMap)stockList.get(i);
 String stkIdn = (String)stockPkt.get("stk_idn");
 
 sr = i+1;
 String checkFldId = "CHK_"+sr;
 String checkFldVal = "value("+stkIdn+")";
 String serviceVal = "value(svc_"+stkIdn+")";
 String serviceId = "svc_"+stkIdn;
 String cts = (String)stockPkt.get("cts");
 String onclick = "AssortTotalCal(this,"+cts+",'','')";
 %>
<tr>
<td><html:checkbox property="<%=checkFldVal%>" styleId="<%=checkFldId%>" name="labIssueForm"  onclick="<%=onclick%>" value="yes" /> </td>
<td><%=sr%></td>
<td><%=stockPkt.get("vnm")%></td>
<%for(int j=0; j < vwPrpLst.size(); j++ ){
    String prp = (String)vwPrpLst.get(j);
   if(prpDspBlocked.contains(prp)){
   }else{
    %>
    <td><%=stockPkt.get(prp)%></td>
    <%
    
}}%>
<td>
 <html:select property="<%=serviceVal%>" styleId="<%=serviceId%>"  name="labIssueForm">
           <html:option value="0" >--select--</html:option>
            <html:optionsCollection property="serviceList" name="labIssueForm" 
            value="labVal" label="labDesc" />
    </html:select>
</td>
</tr>
   <%}%>
   </table></td></tr>
   <input type="hidden" name="count" id="count" value="<%=sr%>" />
    
    
   
   </html:form>
   <%}}%>
  
  
  
  
  
  </table></td></tr>
   <tr>
    <td><jsp:include page="/footer.jsp" /> </td>
    </tr>
  </table>
  </body>
</html>