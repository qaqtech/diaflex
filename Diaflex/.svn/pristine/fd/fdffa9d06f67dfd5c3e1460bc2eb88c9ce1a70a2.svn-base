<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page errorPage="../error_page.jsp" %>
<%@ page contentType="text/html;charset=windows-1252"%>
<jsp:useBean id="info" class="ft.com.InfoMgr" scope="session" />
<jsp:useBean id="gtMgr" class="ft.com.GtMgr" scope="session" />

<jsp:useBean id="util" class="ft.com.JspUtil" scope="page" />
<%@ page import="java.util.ArrayList,java.util.LinkedHashMap, java.util.Set,java.util.HashMap,java.sql.ResultSet,ft.com.*, java.util.Date, java.util.ArrayList, ft.com.dao.*"%>
<%@taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>roughPolishComp</title>
    <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/bse.css?v=<%=info.getJsVersion()%>"/>
    <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/general.css?v=<%=info.getJsVersion()%>"/>
     <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/style.css?v=<%=info.getJsVersion()%>"/>
     <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/bse.js?v=<%=info.getJsVersion()%> " > </script>
      <script type="text/javascript" src="<%=info.getReqUrl()%>/PopScripts/popup.js?v=<%=info.getJsVersion()%> " > </script>
     <script type="text/javascript" src="<%=info.getReqUrl()%>/jqueryScript/jqueryScript.js?v=<%=info.getJsVersion()%> " > </script>
    <script src="<%=info.getReqUrl()%>/PopScripts/jquery-1.2.6.min.js" type="text/javascript"></script>
    <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/memoScript.js?v=<%=info.getJsVersion()%> " > </script>
    <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/assortScript.js?v=<%=info.getJsVersion()%> " > </script>
    <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/ajax.js?v=<%=info.getJsVersion()%> " > </script>
  <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/newCalScript.js?v=<%=info.getJsVersion()%> " > </script>

 </head>
  <body>
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
  <img src="../images/line.jpg" border="0" /></td><td valign="middle"> <span class="pgHed">Plan Comparison</span> </td>
</tr></table>
  </td>
  </tr>
   <%
   String msg = (String)request.getAttribute("msg");
   if(msg!=null){
     %>
    <tr><td valign="top" class="tdLayout"><span class="redLabel"><%=msg%></span></td></tr>
   <%}%>
   
    <tr>
   <td valign="top" class="tdLayout">
   <table><tr><td>
   <html:form action="rough/roughPolishPlanCompar.do?method=fetch" method="post" onsubmit="return validate_assort()">
  <table  class="grid1">
  <tr><th>Generic Search 
  <%
  ArrayList rolenmLst=(ArrayList)info.getRoleLst();
  String usrFlg=util.nvl((String)info.getUsrFlg());
  if(usrFlg.equals("SYS") || rolenmLst.contains("ADMIN")){
  %>
  <img src="../images/edit.jpg" title="Edit Properties Module" onclick="newWindow('<%=info.getReqUrl()%>/dynamicProperty.do?method=load&mdl=RGH_PLAN_SRCH&sname=RGH_PLAN_SRCH&par=GENERIC')" border="0" width="15px" height="15px"/> 
  <%}%>
  </th></tr>
  <tr>
   <td>
   
    <jsp:include page="/genericSrch.jsp"/>
   </td></tr>
   <tr><td align="center"><html:submit property="value(rough)" value="Rough Packets" styleClass="submit" />
   
   </td> </tr>
   </table>
   </html:form>
   </td></tr>
   <tr>
   <td valign="top" >
   <%
   String view = (String)request.getAttribute("view");
   if(view!=null){  
   HashMap mprp = info.getMprp();
   ArrayList rootPktList = (ArrayList)request.getAttribute("ROOTPKTLIST");
      HashMap polishList = (HashMap)request.getAttribute("POLISHPKTLIST");
   HashMap planList = (HashMap)request.getAttribute("PALNLIST");

   if(rootPktList!=null && rootPktList.size()>0){
   ArrayList vwPrpList = (ArrayList)session.getAttribute("RGH_VIEW"); 
   ArrayList planPrpList = (ArrayList)session.getAttribute("PLAN_ENTRY");
   int vwSize = vwPrpList.size();
   int colSpan = vwSize+3;
   int planSz = planPrpList.size();
   %>
   <table class="grid1" align="left">
   <tr><th>Packet Code</th><th>Status</th><th>Qty</th> 
   <%for(int i=0;i<vwSize;i++){
   String lprp = (String)vwPrpList.get(i);
   String lDsc = util.nvl((String)mprp.get(lprp+"D"),lprp);
   %>
   <th><%=lDsc%></th>
   <%}%>
   </tr>
   <%for(int i=0;i<rootPktList.size();i++){
   GtPktDtl rootPkt = (GtPktDtl)rootPktList.get(i);
   String rootstkIdn  = util.nvl((String)rootPkt.getValue("stk_idn"));
   %>
   <tr><td><%=util.nvl((String)rootPkt.getValue("vnm"))%></td><td><%=util.nvl((String)rootPkt.getValue("stt"))%></td><td><%=util.nvl((String)rootPkt.getValue("qty"))%></td>
    <%for(int j=0;j<vwSize;j++){
   String lprp = (String)vwPrpList.get(j);
   %>
   <td><%=util.nvl((String)rootPkt.getValue(lprp))%></td>
   <%}%>
  </tr>
  <tr>
   <td colspan="<%=colSpan%>" valign="top">
   <%
   if(polishList!=null && polishList.size()>0){
   ArrayList polishPktLst = (ArrayList)polishList.get(rootstkIdn);
   if(polishPktLst!=null && polishPktLst.size()>0){
   %>
  <table cellpadding="2" cellspacing="2">
  <tr><td><b>Polish Details</b></td><td><b>Plans Details</b></td></tr>
  <tr>
  <td valign="top">
  <div class="memo">
  <table  border="0" width="400" class="Orange" cellspacing="1" cellpadding="1">
   <tr><th class="Orangeth">Packet Code</th><th class="Orangeth">Status</th><th class="Orangeth">Qty</th>
    <%for(int j=0;j<planSz;j++){
   String lprp = (String)planPrpList.get(j);
    String lDsc = util.nvl((String)mprp.get(lprp+"D"),lprp);
   %>
   <th class="Orangeth"><%=lDsc%></th>
   <%}%>
   </tr>
   <%
   for(int x=0;x<polishPktLst.size();x++){
   HashMap polishMap  = (HashMap)polishPktLst.get(x);
   %>
  <tr><td><%=util.nvl((String)polishMap.get("VNM"))%></td><td><%=util.nvl((String)polishMap.get("STT"))%></td><td><%=util.nvl((String)polishMap.get("QTY"))%></td>
    <%for(int j=0;j<planSz;j++){
   String lprp = (String)planPrpList.get(j);
   %>
   <td><%=util.nvl((String)polishMap.get(lprp))%></td>
   <%}%>
  </tr>
   <%}%>
   
   </table></div>
  
  
  </td>
  <td valign="top">
  
     <%
   if(planList!=null && planList.size()>0){
   ArrayList planPktLst = (ArrayList)planList.get(rootstkIdn);
   %>
  <table cellpadding="2" cellspacing="2">
  <tr>
  <td>
  <%if(planPktLst!=null && planPktLst.size()>0){%>
  <div class="memo">
  <table  border="0" width="400" class="Orange" cellspacing="1" cellpadding="1">
   <tr><th class="Orangeth">Plan</th>
    <%for(int j=0;j<planSz;j++){
   String lprp = (String)planPrpList.get(j);
    String lDsc = util.nvl((String)mprp.get(lprp+"D"),lprp);
   %>
   <th class="Orangeth"><%=lDsc%></th>
   <%}%>
   </tr>
   <%
   for(int x=0;x<planPktLst.size();x++){
   HashMap planMap  = (HashMap)planPktLst.get(x);
   %>
  <tr><td><%=util.nvl((String)planMap.get("PLAN"))%></td>
    <%for(int j=0;j<planSz;j++){
   String lprp = (String)planPrpList.get(j);
   %>
   <td><%=util.nvl((String)planMap.get(lprp))%></td>
   <%}%>
  </tr>
   <%}%>
   
   </table></div>
  <%}%>
  </td></tr></table>
  <%}%>
  
  </td>
  </tr>
  </table>
  <%}}else{%>
    Sorry no result found.
  <%}%>
   </td>
  </tr>
   <%}%>
   </table>
   <%}else{%>
   Sorry no result found.
   <%}%>
   <%}%>
   
   </td></tr>
   </table></td></tr>
  </table>
  
  </body>
</html>