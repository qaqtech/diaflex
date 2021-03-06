<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page errorPage="../error_page.jsp" %>
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import="java.util.ArrayList,java.util.ArrayList,java.util.HashMap,java.util.Set, java.sql.*, java.util.Enumeration, java.util.logging.Level"%>
<jsp:useBean id="info" class="ft.com.InfoMgr" scope="session" />
<jsp:useBean id="util" class="ft.com.JspUtil" scope="page" />
<%@taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
 
<html>
  <head>
 <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
  
  <title>Price History</title>
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
  <img src="../images/line.jpg" border="0" /></td><td valign="middle"> <span class="pgHed">Price History</span> </td>
</tr></table>
  </td>
  </tr>
   <tr>
  <td valign="top" class="hedPg">
   <html:form action="report/priceHistory.do?method=fetch" method="post">
    <table><tr><td>Date From : </td><td>
    <html:text property="value(dtefr)" styleClass="txtStyle"  styleId="dtefr"  size="12"  /> <a href="#" style="margin-top:5px;"  onClick="setYears(1900, '<%=info.getCurrentYear()%>' );showCalender(this, 'dtefr');">
                <img src="<%=info.getReqUrl()%>/images/calender.png"></a></td>
                <td>Date To : </td><td>
    <html:text property="value(dteto)" styleClass="txtStyle"  styleId="dteto"  size="12"  /> <a href="#"  onClick="setYears(1900, '<%=info.getCurrentYear()%>' );showCalender(this, 'dteto');">
                <img src="<%=info.getReqUrl()%>/images/calender.png"></a></td>
                <td>Status : </td>
                <td>
               <html:select property="value(stt)"  >
               <% 
                  ArrayList sttList = (ArrayList)session.getAttribute("sttLst");
               for(int i=0 ; i < sttList.size();i++){
               ArrayList sttdtl = (ArrayList)sttList.get(i);
               String grp1 = (String)sttdtl.get(0);
               String dsc = (String)sttdtl.get(1);
               %>
               <html:option value="<%=grp1%>"><%=dsc%></html:option>
               
               <%}%>
               </html:select>
                </td>
                 <td><html:submit property="value(submit)" value="Get History" styleClass="submit"/></td>
                </tr></table>
   
   </html:form></td></tr>
 
  <%
  HashMap gridMap = (HashMap)request.getAttribute("GridData");
  if(gridMap!=null && gridMap.size()>0){ 
    HashMap clrMap = (HashMap)request.getAttribute("ClrMap");
    ArrayList viewPrpLst = (ArrayList)session.getAttribute("priHisViewLst");
  String sh = (String)viewPrpLst.get(0);
  String sz = (String)viewPrpLst.get(1);
  String col = (String)viewPrpLst.get(2);
  String clr = (String)viewPrpLst.get(3);
  Set<String> keySet = gridMap.keySet();
  ArrayList keyList = new ArrayList();
     for(String key: keySet){
          keyList.add(key);
     }
 String sb = keyList.toString();
  HashMap mprp = info.getMprp();
  HashMap prp = info.getPrp();
  ArrayList shapeLst = (ArrayList)prp.get(sh+"V");
  ArrayList szLst = (ArrayList)prp.get(sz+"V");
  ArrayList colLst = (ArrayList)prp.get(col+"V");
  ArrayList clrVLst = (ArrayList)prp.get(clr+"V");
  for(int i=0 ; i < shapeLst.size();i++){
  String shPrp = (String)shapeLst.get(i);
  ArrayList clrLst = (ArrayList)clrMap.get(shPrp);
  if(clrLst!=null){
  if(sb.indexOf(shPrp)!=-1){
  int colspan = clrLst.size()+2;
   int szcolspan = clrLst.size()+1;
  %>
    <tr>
  <td valign="top" class="hedPg">
  <table class="grid1">
 <tr><th colspan="<%=colspan%>"><%=shPrp%></th></tr>
 <tr><td>Size</td> <td colspan="<%=szcolspan%>">&nbsp;</td></tr>
 <%for(int j=0;j<szLst.size();j++){
 String szlprp  = (String)szLst.get(j);
   if(sb.indexOf(shPrp+"_"+szlprp)!=-1){ %>
   <tr><td><%=szlprp%></td><td>Color\Clarity</td>
   <%for(int k=0;k< clrVLst.size();k++){
    String clrLprp = (String)clrVLst.get(k);
    if(clrLst.contains(clrLprp)){
  %>
    <td><%=clrLprp%></td>
  <% }}%>
   </tr>
   <%
  
   for(int l=0;l<colLst.size();l++){
   String lcol = (String)colLst.get(l);
    if(sb.indexOf(shPrp+"_"+szlprp+"_"+lcol)!=-1){
   %>
   <tr><td></td><td><%=lcol%></td>
   <%for(int a=0;a<clrVLst.size();a++){
   String lclr = (String)clrVLst.get(a);
   if(clrLst.contains(lclr)){
     if(sb.indexOf(shPrp+"_"+szlprp+"_"+lcol)!=-1){
   String lavg = util.nvl((String)gridMap.get(shPrp+"_"+szlprp+"_"+lcol+"_"+lclr)).trim();
   float fAvg = 0;
   String styleClass="";
   if(!lavg.equals("")){
    fAvg = Float.parseFloat(lavg);
    if(fAvg < 0)
    styleClass="background-color:red";
    else
    styleClass="background-color:green";
   }
 %>
   <td style="<%=styleClass%>"><%=lavg%></td>
   <%}}}%>
   
   </tr><%}}%>
 <%}%>
<%}%>
  
  </table></td></tr>
  <%}}}}else{%>
  <tr>
  <td valign="top" class="hedPg">sorry no result found</td></tr>
  <%}%>
   
  <tr><td>
<jsp:include page="/footer.jsp" />
</td></tr>
  </table>
   <%@include file="/calendar.jsp"%>
  </body>

</html>