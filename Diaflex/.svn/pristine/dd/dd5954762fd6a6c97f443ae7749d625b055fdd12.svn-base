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
    <title>Marge Mix Memo</title>
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
   <jsp:include page="/header.jsp" /></td></tr>
  <tr>
    <td height="5" valign="top" class="greyline"><img src="../images/grey_linebig.png" width="685" height="5" align="right" /></td>
  </tr>
  <tr>
  <td valign="top" class="hedPg">
  <table cellpadding="1" cellspacing="5"><tr><td valign="middle">
  <img src="../images/line.jpg" border="0" /></td><td valign="middle"> <span class="pgHed">Merge Mix Memo</span> </td>
</tr></table>
  </td>
  </tr><tr>
   <td valign="top" class="tdLayout">
     <html:form action="mix/mergeMemoMix.do?method=loadGrid" method="post" >
  <table  class="grid1">
  <tr><th>Generic Search </th></tr>
  <tr>
   <td>
    <jsp:include page="/genericSrch.jsp"/>
   </td></tr>
   <tr><td  align="center"><html:submit property="value(srch)" value="Fetch" styleClass="submit" /></td> </tr>
   </table>
   </html:form>
   </td></tr>
    <%
   
   ArrayList msgLst = (ArrayList)request.getAttribute("msgLst");
   if(msgLst!=null){
   for(int i=0;i<msgLst.size();i++){
   String msg = (String)msgLst.get(i);
   %>
    <tr><td valign="top" class="tdLayout"><span class="redLabel"><%=msg%></span></td></tr>
   <%}}%>
   <tr><td height="20">&nbsp;</td></tr>
   <tr>
   <td valign="top" class="tdLayout">
     <html:form action="mix/mergeMemoMix.do?method=marge" method="post"  onsubmit="return validate_SelectAll('1','merge_')" >
   <table>
   
   <%
   HashMap prp = info.getPrp();
        
   ArrayList shSzList = (ArrayList)session.getAttribute("SHSZLIST");
   ArrayList pktDtlList = (ArrayList)request.getAttribute("pktDtlList");
    HashMap pktTTLMap = (HashMap)request.getAttribute("PKTTTLMAP");
    HashMap TTL = (HashMap)request.getAttribute("TTL");
    ArrayList vwPrpLst = (ArrayList)session.getAttribute("MERGE_MIX_VW");
    String view  = (String)request.getAttribute("view");
    String memoLst = util.nvl((String)request.getAttribute("memoLst"));
    if(view!=null){
    HashMap dataTable=new HashMap();
   ArrayList shVal = (ArrayList)prp.get("SHAPEV");
   ArrayList szVal = (ArrayList)prp.get("MIX_SIZEV");
   ArrayList shSrtVal = (ArrayList)prp.get("SHAPES");
   ArrayList szSrtVal = (ArrayList)prp.get("MIX_SIZES");
   if(shSzList!=null && shSzList.size()>0){%>
   <input type="hidden" name="memoLst" value="<%=memoLst%>" id="memoLst" />
   <tr><td valign="top">
   <div style="margin-bottom:5px">
   <html:submit property="value(submit)" value="Marge" styleClass="submit" />
   </div>
     <table  class="grid1" cellpadding="1">
     <tr><th>Merge<input type="checkbox" name="All" onclick="CheckBOXALLForm('1',this,'merge_')"/></th><th>Shape</th><th>Mix_size</th><th>CTS</th><th>AVGPRC</th><th>VALUE</th></tr>
     <%for(int i=0;i<shSzList.size();i++){
     String keyStr = (String)shSzList.get(i);
     HashMap pktDtlTTL = (HashMap)pktTTLMap.get(keyStr);
     String[] key = keyStr.split("_");
     String shSrt = key[0];
     String szSrt = key[1];
     String fldId="merge_"+i;
     %>
     <tr><td><input type="checkbox" name="<%=keyStr%>" id="<%=fldId%>" value="yes"/></td><td><%=shVal.get(shSrtVal.indexOf(shSrt))%></td><td><%=szVal.get(szSrtVal.indexOf(szSrt))%> </td>
     <td align="right"><%=pktDtlTTL.get("CTS")%></td><td align="right"><%=pktDtlTTL.get("AVGPRC")%></td><td align="right"><%=pktDtlTTL.get("VAL")%> </td></tr>
     <%}%>
     </table>
     </td>
     <td valign="top">
     <table  class="grid1" cellpadding="1">
     <tr><td colspan="4" align="center">Memo Wise Summary</td></tr>
     <tr><th>Memo Id</th><th>Cts</th><th>Avg Prc</th><th>Value</th></tr>
     <%for(int i=0;i<pktDtlList.size();i++){
     dataTable=new HashMap();
     dataTable=(HashMap)pktDtlList.get(i);
     if(dataTable!=null && dataTable.size()>0){
     %>
     <tr><td><%=dataTable.get("MEMO")%></td><td align="right"><%=dataTable.get("CTS")%></td>
     <td align="right"><%=dataTable.get("AVGPRC")%></td><td align="right"><%=dataTable.get("VAL")%></td></tr>
     <%}}%>
     <tr><td><b>Total</b></td><td align="right"><b><%=TTL.get("CTS")%></b></td>
     <td align="right"><b><%=TTL.get("AVGPRC")%></b></td><td><b align="right"><%=TTL.get("VAL")%></b></td>
     
     </tr>
     </table>
     </td>
     </tr>
     <%}else{%>
   <tr><td>Sorry no result found</td></tr>
   <%}
   }%>
   </table></html:form>
   </td></tr>
      <tr>
    <td><jsp:include page="/footer.jsp" /> </td>
    </tr>
   </table>
  
</body>
</html>