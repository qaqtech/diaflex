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
    <title>Transfer To Mkt Mix</title>
    <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/bse.css?v=<%=info.getJsVersion()%>"/>
    <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/general.css?v=<%=info.getJsVersion()%>"/>
     <script type="text/javascript" src="<%=info.getReqUrl()%>/PopScripts/jquery-1.2.6.min.js" > </script>
     
     <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/style.css?v=<%=info.getJsVersion()%>"/>
      <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/bse.js?v=<%=info.getJsVersion()%> " > </script>
          <script type="text/javascript" src="<%=info.getReqUrl()%>/PopScripts/popup.js?v=<%=info.getJsVersion()%> " > </script>
  </head>
        <%String logId=String.valueOf(info.getLogId());
        String onfocus="cook('"+logId+"')";
        %>
 <body onfocus="<%=onfocus%>" onload="MM_preloadImages('images/faunalogo.png')" topmargin="0" leftmargin="0" rightmargin="0" bottommargin="0">
<div id="backgroundPopup"></div>

<div id="popupContactSave" align="center" >
<img src="../images/loading_flame.gif" height="150px" width="150px" border="0" />
</div>
<%
 
 HashMap prp = info.getPrp();
 
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
  <img src="../images/line.jpg" border="0" /></td><td valign="middle"> <span class="pgHed">Transfer To Marketing </span> </td>
  </tr></table>
  </td>
  </tr>
   <tr>
  <td valign="top" class="tdLayout">
  <%
  ArrayList msgList = (ArrayList)request.getAttribute("msg");
  if(msgList!=null && msgList.size()>0){
  String msgStr = msgList.toString();
  msgStr = msgStr.replace("[","");
   msgStr = msgStr.replace("]","");
  %>
  <span class="redLabel"><%=msgStr%></span> 
  <%}%>
  </td></tr>
   <tr><td valign="top" class="tdLayout">
   <html:form action="mix/tranMktMixAction.do?method=fetch" onsubmit="return validate_trfToMkt();" method="post">
   <table  class="grid1">
  <tr><th colspan="2">Generic Search </th></tr>
  <tr>
   <td colspan="2">
    <jsp:include page="/genericSrch.jsp"/>
   </td></tr>
   <tr><td colspan="2" align="center"><html:submit property="value(srch)" value="Fetch" styleClass="submit" /></td> </tr>
   </table>
   </html:form>
  </td></tr>
   <tr>
  <td valign="top" class="tdLayout" height="10">
  </td></tr>
  
  <html:form action="mix/tranMktMixAction.do?method=save" onsubmit="loading()" method="post">
  <html:hidden property="value(shVal)"  name="trnMktMixForm" />
  <%  String view = (String)request.getAttribute("View");
    if(view!=null){
    %>
      <tr>
  <td valign="top" class="tdLayout">
  <html:submit property="value(submit)" styleClass="submit"  onclick="return validatetrfSave();" value="Save Changes" />
  </td></tr>
    <tr>
  <td valign="top" class="tdLayout" height="10"></td></tr>
  <tr>
  <td valign="top" class="tdLayout">
  <%
     String Zlive="",Slive="",Znew="",Snew="";  
     ArrayList szPrtLst = (ArrayList)prp.get("MIX_SIZEP2");
      ArrayList szValLst = (ArrayList)prp.get("MIX_SIZEV");
      ArrayList szSrtLst = (ArrayList)prp.get("MIX_SIZES");
        ArrayList szPrtValLst = (ArrayList)prp.get("MIX_SIZEP");
    ArrayList asViewPrp = (ArrayList)session.getAttribute("TRFMKTMIXLst");
    HashMap ttlCtsMap = (HashMap)session.getAttribute("ttCtsMap");
    ArrayList shList = (ArrayList)session.getAttribute("shList");
    ArrayList szList = (ArrayList)session.getAttribute("szList");
    ArrayList clrList = (ArrayList)session.getAttribute("clrList");
     ArrayList szSrtList = (ArrayList)session.getAttribute("szSrtList");
    ArrayList clrSrtList = (ArrayList)session.getAttribute("clrSrtList");
      int szcount=szList.size();
      int clrcount=clrList.size();
    if(ttlCtsMap!=null && ttlCtsMap.size() >0){
    for(int i=0 ; i < shList.size(); i++){
    String sh = (String)shList.get(i);%>
     <input type="hidden" name="szcount" id="szcount" value="<%=szcount%>">
     <input type="hidden" name="clrcount" id="clrcount" value="<%=clrcount%>">
    <table class="dataTable"><tr>
    <th>Clarity<input type="checkbox" name="All" onclick="CheckBOXALLForm('1',this,'clr_')"/>
    /Size<input type="checkbox" name="All" onclick="CheckBOXALLForm('1',this,'sz_')"/></th>
    <% for(int j=0 ; j < szList.size(); j++){
    String szVal = util.nvl((String)szList.get(j));
    String szSrt = util.nvl((String)szSrtList.get(j));
    String szId="sz_"+(j+1);
    String colspan = "2";
    String fldVal = "value("+szVal+")";
    String isDp = (String)szPrtLst.get(szSrtLst.indexOf(szSrt));
    if(isDp.equals("DP"))
    colspan = "4";
    %>
    <th colspan="<%=colspan%>">
    <html:checkbox  property="<%=fldVal%>" value="<%=szVal%>" styleId="<%=szId%>" name="trnMktMixForm" />
    <%=szList.get(j)%></th>
    <%}%></tr>
    <tr><td></td>
    <% for(int j=0 ; j < szList.size(); j++){
    String szVal = util.nvl((String)szList.get(j));
     String szSrt = util.nvl((String)szSrtList.get(j));
      String isDp = (String)szPrtLst.get(szSrtLst.indexOf(szSrt));
    if(isDp.equals("DP")){
    %>
    <td>Live/Rate</td><td>New/Rate</td><td>Live/Rate</td><td>New/Rate</td>
    <%}else{%>
    <td>Live/Rate</td><td>New/Rate</td>
   <% }}%></tr>
    <tr><td></td>
    <% for(int j=0 ; j < szList.size(); j++){
    String szVal = util.nvl((String)szList.get(j));
     String szSrt = util.nvl((String)szSrtList.get(j));
     String isDp = (String)szPrtLst.get(szSrtLst.indexOf(szSrt));
    if(isDp.equals("DP")){
    %>
    <td>DP:< 65</td><td>DP:< 65</td><td>DP:>= 65</td><td>DP:>= 65</td>
    <%}else{%>
    <td></td><td></td>
    <%}%>
    <%}%></tr>
    <% for(int x=0 ; x < clrList.size(); x++){
    String clrVal = (String)clrList.get(x);
    String fldVal = "value("+clrVal+")";
    String clrId="clr_"+(x+1);
    %>
    <tr><td style="text-align:left;"> <html:checkbox   property="<%=fldVal%>"  styleId="<%=clrId%>" value="<%=clrVal%>" name="trnMktMixForm" /><%=clrVal%></td>
    <%
    for(int y=0 ; y < szList.size(); y++){
    String szVal = (String)szList.get(y);
     String szSrt = util.nvl((String)szSrtList.get(y));
       String isDp = (String)szPrtLst.get(szSrtLst.indexOf(szSrt));
     Zlive=util.nvl((String)ttlCtsMap.get(sh+"_"+szVal+"_"+clrVal+"_0_LIVE_RTE"));
     Znew=util.nvl((String)ttlCtsMap.get(sh+"_"+szVal+"_"+clrVal+"_0_NEW_RTE"));
    if(isDp.equals("DP")){
    Slive="";
    Snew="";
    %>
    <td><%=util.nvl((String)ttlCtsMap.get(sh+"_"+szVal+"_"+clrVal+"_0_LIVE"))%>
    <%if(!Zlive.equals("")){%>/<%=Zlive%><%}%>
    </td>
    <td><%=util.nvl((String)ttlCtsMap.get(sh+"_"+szVal+"_"+clrVal+"_0_NEW"))%>
    <%if(!Znew.equals("")){%>/<%=Znew%><%}%>
    </td>
    <td><%=util.nvl((String)ttlCtsMap.get(sh+"_"+szVal+"_"+clrVal+"_65_LIVE"))%>
     <%if(!Slive.equals("")){%>/<%=Slive%><%}%>
    </td><td><%=util.nvl((String)ttlCtsMap.get(sh+"_"+szVal+"_"+clrVal+"_65_NEW"))%>
    <%if(!Snew.equals("")){%>/<%=Snew%><%}%>
    </td>
    <%}else{%>
     <td><%=util.nvl((String)ttlCtsMap.get(sh+"_"+szVal+"_"+clrVal+"_0_LIVE"))%>
     <%if(!Zlive.equals("")){%>/<%=Zlive%><%}%>
     </td><td><%=util.nvl((String)ttlCtsMap.get(sh+"_"+szVal+"_"+clrVal+"_0_NEW"))%>
      <%if(!Znew.equals("")){%>/<%=Znew%><%}%>
      </td>
    <%}}%>
    </tr>
    <%}%>
    <tr>
    <td align="left"><b>Total</b></td>
     <%
    for(int y=0 ; y < szList.size(); y++){
    String szVal = (String)szList.get(y);
     String szSrt = util.nvl((String)szSrtList.get(y));
      String isDp = (String)szPrtLst.get(szSrtLst.indexOf(szSrt));
     Zlive=util.nvl((String)ttlCtsMap.get(szVal+"_0_LIVE_RTE_TTL"));
     Znew=util.nvl((String)ttlCtsMap.get(szVal+"_0_NEW_RTE_TTL"));
    if(isDp.equals("DP")){
    Slive="";
    Snew="";
    %>
    <td><b><%=util.nvl((String)ttlCtsMap.get(szVal+"_0_LIVE_TTL"))%>
    <%if(!Zlive.equals("")){%>/<%=Zlive%><%}%></b>
    </td>
    <td><b><%=util.nvl((String)ttlCtsMap.get(szVal+"_0_NEW_TTL"))%>
    <%if(!Znew.equals("")){%>/<%=Znew%><%}%></b>
    </td>
    <td><b><%=util.nvl((String)ttlCtsMap.get(szVal+"_65_LIVE_TTL"))%>
     <%if(!Slive.equals("")){%>/<%=Slive%><%}%></b>
    </td><td><b><%=util.nvl((String)ttlCtsMap.get(szVal+"_65_NEW_TTL"))%>
    <%if(!Snew.equals("")){%>/<%=Snew%><%}%></b>
    </td>
    <%}else{%>
     <td><b><%=util.nvl((String)ttlCtsMap.get(szVal+"_0_LIVE_TTL"))%>
     <%if(!Zlive.equals("")){%>/<%=Zlive%><%}%></b>
     </td><td><b><%=util.nvl((String)ttlCtsMap.get(szVal+"_0_NEW_TTL"))%>
      <%if(!Znew.equals("")){%>/<%=Znew%><%}%></b>  
      </td>
    <%}}%>
    </tr>
 </table>
  <%}}}%>
  </td></tr>
   <tr>
    <td><jsp:include page="/footer.jsp" /> </td>
    </tr>
  </html:form>
  </table>
  
  </body>
</html>