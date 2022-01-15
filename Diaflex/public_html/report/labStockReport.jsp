<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page errorPage="../error_page.jsp" %>
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import="java.util.ArrayList,java.util.ArrayList,java.util.HashMap,java.util.LinkedHashMap ,java.util.Set, java.sql.*, java.util.Enumeration, java.util.logging.Level"%>
<jsp:useBean id="info" class="ft.com.InfoMgr" scope="session" />
<jsp:useBean id="gtMgr" class="ft.com.GtMgr" scope="session" />
<jsp:useBean id="util" class="ft.com.JspUtil" scope="page" />
<%@taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
 
  <html>
  <head>
  <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
  <title>Lab Stock Report</title>
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
 Lab Stock Report </span> </td>
</tr></table>
  </td>
  </tr>
  
   <%
   String lstNme = (String)request.getAttribute("lstNme");
   HashMap labDtlMap = (HashMap)gtMgr.getValue(lstNme+"_DTLMAP"); 
   ArrayList giaSttList = (ArrayList)gtMgr.getValue(lstNme+"_GIASTT"); 
   ArrayList noSttList = (ArrayList)gtMgr.getValue(lstNme+"_NOGIASTT"); 
    ArrayList labList = (ArrayList)gtMgr.getValue(lstNme+"_LABLIST"); 
   ArrayList labLocList = (ArrayList)gtMgr.getValue(lstNme+"_LABLOC"); 
   ArrayList sttList = new ArrayList();
   sttList.add("LB_IS");
   sttList.add("LB_AU_IS");
   sttList.add("LB_RS");
   sttList.add("LB_RI");
   sttList.add("LB_CF");
   sttList.add("LB_CFRS");
   
   if(labDtlMap!=null && labDtlMap.size()>0){%>
   <tr>
   <td valign="top" class="hedPg" ><B>GIA STOCK:-</b></td></tr>
    <tr>
   <td valign="top" class="hedPg" >
   <%if(giaSttList!=null && giaSttList.size()>0){

   %>
   <table class="grid1"><tr><th>Location\Status</th>
   <%
  for(int i=0;i<sttList.size();i++){ 
   String lstt = (String)sttList.get(i);
  %>
   <th colspan="2"><%=lstt%></th>
  <%}%>
  <th colspan="2">Total</th>
  </tr>
 <tr><td></td>
  <%for(int i=0;i<sttList.size();i++){%>
  <td>QTY</td> <td>CTS</td>
  <%}%>
   <td>QTY</td> <td>CTS</td>
 </tr>
  <%
  HashMap colTtlMap = new HashMap();
  for(int i=0;i<labLocList.size();i++){
  String labLoc = (String)labLocList.get(i);%>
   <tr> <td><%=labLoc%></td>
  <% int ttlRQty=0;
     double ttlRCts=0;
     int ttlCQty=0;
     double ttlCCts=0;
  for(int j=0;j<sttList.size();j++){ 
   String lstt = (String)sttList.get(j);

    String lQty= util.nvl((String)labDtlMap.get(labLoc+"_"+lstt+"_QTYGIA"));
    if(lQty.equals(""))
    lQty="0";
   String lCts= util.nvl((String)labDtlMap.get(labLoc+"_"+lstt+"_CTSGIA"));
   if(lCts.equals(""))
    lCts="0";
    ttlRQty=ttlRQty+Integer.parseInt(lQty);
    ttlRCts=ttlRCts+Double.parseDouble(lCts);
    ttlCQty = Integer.parseInt(util.nvl((String)colTtlMap.get(lstt+"QTY"),"0"));
    ttlCCts = Double.parseDouble(util.nvl((String)colTtlMap.get(lstt+"CTS"),"0"));
    ttlCQty=ttlCQty+Integer.parseInt(lQty);
    ttlCCts = ttlCCts+Double.parseDouble(lCts);
    colTtlMap.put(lstt+"QTY", String.valueOf(ttlCQty));
    colTtlMap.put(lstt+"CTS", String.valueOf(ttlCCts));
   %>

 <%if(!lCts.equals("")){%>
  <td align="center">
<A href="labStockReport.do?method=PktDtl&lstNme=<%=lstNme%>&STT=<%=lstt%>&LAB_LOC=<%=labLoc%>&LAB=GIA" target="_blank"> <%=lQty%> </a> </td><td> <%=lCts%> 
</td>
<%}%>
  <%}%>
  <td align="center"><b><A href="labStockReport.do?method=PktDtl&lstNme=<%=lstNme%>&LAB_LOC=<%=labLoc%>&LAB=GIA" target="_blank"><%=ttlRQty %></a></b></td><td align="center"><b><%=util.roundToDecimals(ttlRCts,2)%></b></td>
  </tr>
  <%}%>
   <%
    int ttlRQty=0;
    String sQty=null;
     double ttlRCts=0;
     String sCts=null;
      String lstt=null;
     %>
  <tr><td><b>Total</b></td>
  <%
   for(int i=0;i<sttList.size();i++){ 
    lstt = (String)sttList.get(i);
    sQty =util.nvl((String)colTtlMap.get(lstt+"QTY"));
    if(sQty.equals(""))
    sQty="0";
    sCts =util.nvl((String)colTtlMap.get(lstt+"CTS"));
     if(sCts.equals(""))
    sCts="0";
    ttlRQty=ttlRQty+Integer.parseInt(sQty) ;
    ttlRCts=ttlRCts+Double.parseDouble(sCts);
   %>
   <td><b><A href="labStockReport.do?method=PktDtl&lstNme=<%=lstNme%>&STT=<%=lstt%>&LAB=GIA" target="_blank"><%=sQty%></a></b></td><td><b><%=util.roundToDecimals(Double.parseDouble(sCts),2)%></b></td>
  <%}%>
  <td><b><A href="labStockReport.do?method=PktDtl&lstNme=<%=lstNme%>&LAB=GIA" target="_blank"><%=ttlRQty%></a></b></td><td><b><%=util.roundToDecimals(ttlRCts,2)%></b></td>
  </tr>
  
    </table>
 
    <%}%>
       </td></tr>
       
       <tr>
   <td valign="top" class="hedPg" ><B>NONE GIA STOCK:-</b></td></tr>
    <tr>
       
         <tr>
   <td valign="top" class="hedPg" >
   <%if(noSttList!=null && noSttList.size()>0){
    
     %>
   <table class="grid1"><tr><th>Lab\Status</th>

    <%for(int i=0;i<sttList.size();i++){ 
   String lstt = (String)sttList.get(i);
  
   %>
   <th colspan="2"><%=lstt%></th>
  <%}%>
  <th colspan="2">Total</th>
  </tr>
 <tr><td></td>
  <%for(int i=0;i<sttList.size();i++){%>
  <td>QTY</td> <td>CTS</td>
  <%}%>
   <td>QTY</td> <td>CTS</td>
 </tr>
  <%
  HashMap colTtlMap = new HashMap();
  for(int i=0;i<labList.size();i++){
  String lab = (String)labList.get(i);%>
   <tr> <td><%=lab%></td>
  <%  
  int ttlRQty=0;
     double ttlRCts=0;
   int ttlCQty=0;
     double ttlCCts=0;
  for(int j=0;j<sttList.size();j++){ 
   String lstt = (String)sttList.get(j);
 
   String lQty= util.nvl((String)labDtlMap.get(lab+"_"+lstt+"_QTY"));
    if(lQty.equals(""))
    lQty="0";
   String lCts= util.nvl((String)labDtlMap.get(lab+"_"+lstt+"_CTS"));
   if(lCts.equals(""))
    lCts="0";
    ttlRQty=ttlRQty+Integer.parseInt(lQty);
    ttlRCts=ttlRCts+Double.parseDouble(lCts);
    ttlCQty = Integer.parseInt(util.nvl((String)colTtlMap.get(lstt+"QTY"),"0"));
    ttlCCts = Double.parseDouble(util.nvl((String)colTtlMap.get(lstt+"CTS"),"0"));
    ttlCQty=ttlCQty+Integer.parseInt(lQty);
    ttlCCts = ttlCCts+Double.parseDouble(lCts);
    colTtlMap.put(lstt+"QTY", String.valueOf(ttlCQty));
    colTtlMap.put(lstt+"CTS", String.valueOf(ttlCCts));
   %>
 <%if(!lCts.equals("")){%> 
  <td align="center">
 <A href="labStockReport.do?method=PktDtl&lstNme=<%=lstNme%>&STT=<%=lstt%>&LAB=<%=lab%>"> <%=lQty%> </a></td><td><%=lCts%></td>
  <%}%>
  <%}%>
  <td align="center"><B><A href="labStockReport.do?method=PktDtl&lstNme=<%=lstNme%>&LAB=<%=lab%>"><%=ttlRQty%></a></b></td><td align="center"><%=util.roundToDecimals(ttlRCts,2)%></td>

  </tr>
  <%}%>
   <%
    int ttlRQty=0;
    String sQty=null;
     double ttlRCts=0;
     String sCts=null;
      String lstt=null;
     %>
  <tr><td><b>Total</b></td>
  <%
   for(int i=0;i<sttList.size();i++){ 
    lstt = (String)sttList.get(i);
    sQty =util.nvl((String)colTtlMap.get(lstt+"QTY"));
    if(sQty.equals(""))
    sQty="0";
    sCts =util.nvl((String)colTtlMap.get(lstt+"CTS"));
     if(sCts.equals(""))
    sCts="0";
    ttlRQty=ttlRQty+Integer.parseInt(sQty) ;
    ttlRCts=ttlRCts+Double.parseDouble(sCts);
   %>
   <td><b><A href="labStockReport.do?method=PktDtl&lstNme=<%=lstNme%>&STT=<%=lstt%>&LAB=NOGIA"><%=sQty%></a></b></td><td><b><%=sCts%></b></td>
  <%}%>
  <td><b><A href="labStockReport.do?method=PktDtl&lstNme=<%=lstNme%>&LAB=NOGIA"><%=ttlRQty%></a></b></td><td><b><%=util.roundToDecimals(ttlRCts,2)%></b></td>
  </tr>
  
    </table>
 
    <%}%>
       </td></tr>
    
    <%}else{%>
    <tr>
   <td valign="top" class="hedPg" > Sorry no result found..</td></tr>
    <%}%>
    
    <tr><td>
    <jsp:include page="/footer.jsp" />
    </td></tr>
    
    </table>
    
    
    
    
    </body>
</html>