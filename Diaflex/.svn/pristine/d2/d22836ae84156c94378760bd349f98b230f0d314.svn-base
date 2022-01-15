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
    <title>Mix To Single</title>
    <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/bse.css?v=<%=info.getJsVersion()%>"/>
    <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/general.css?v=<%=info.getJsVersion()%>"/>
     <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/style.css?v=<%=info.getJsVersion()%>"/>
      <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/bse.js?v=<%=info.getJsVersion()%> " > </script>
       <script type="text/javascript" src="<%=info.getReqUrl()%>/PopScripts/popup.js?v=<%=info.getJsVersion()%> " > </script>
     <script src="<%=info.getReqUrl()%>/PopScripts/jquery-1.2.6.min.js" type="text/javascript"></script>
  </head>
        <%String logId=String.valueOf(info.getLogId());
        String onfocus="cook('"+logId+"')";
        %>
 <body onfocus="<%=onfocus%>" onload="MM_preloadImages('images/faunalogo.png')" topmargin="0" leftmargin="0" rightmargin="0" bottommargin="0">
  <input type="hidden" id="reqUrl" name="reqUrl" value="<%=info.getReqUrl()%>"/>
  
  <div id="backgroundPopup"></div>
<div id="popupContactASSFNL">
<table class="popUpTb">
 <tr><td height="5%">&nbsp;&nbsp;&nbsp;<html:button property="value(close)" styleClass="submit"  onclick="MKEmptyPopup('SC');disablePopupASSFNL();windowOpen('mixToSingle.do?method=popRslt','_self')" value="Close"  /> </td></tr>
<tr><td valign="top" height="95%">
<iframe name="SC" class="frameStyle"   align="middle" frameborder="0">

</iframe></td></tr>
</table>
</div>
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
  <img src="../images/line.jpg" border="0" /></td><td valign="middle"> <span class="pgHed">Mix To Single</span> </td> </tr></table>
  </td>
  </tr>
  <%ArrayList mixtoSingleList = (session.getAttribute("mixtoSinglesttList") == null)?new ArrayList():(ArrayList)session.getAttribute("mixtoSinglesttList");
  int mixtoSingleListsz=mixtoSingleList.size();
  %>
   <tr>
    <td valign="top" class="tdLayout">
    <html:form action="mix/mixToSingle.do?method=fetch" method="post" >
    <table class="grid1">
     <tr><th colspan="2">Generic Search 
       <%
  ArrayList rolenmLst=(ArrayList)info.getRoleLst();
  String usrFlg=util.nvl((String)info.getUsrFlg());

   
  if(usrFlg.equals("SYS") || rolenmLst.contains("ADMIN")){
  %>
  <img src="../images/edit.jpg" title="Edit Properties Module" onclick="newWindow('<%=info.getReqUrl()%>/dynamicProperty.do?method=load&mdl=MIXSIG_SRCH&sname=MIXSIG_SRCH&par=GENERIC')" border="0" width="15px" height="15px"/> 
  <%}%>
     </th></tr>
     <%if(mixtoSingleListsz>0){%>
     <tr><td colspan="2">
      Status : <html:select property="value(stt)" styleId="status">  
      <%for(int i=0;i<mixtoSingleListsz;i++){
      ArrayList data=(ArrayList)mixtoSingleList.get(i);
      String val=(String)data.get(0);
      String dsc=(String)data.get(1);
      %>
      <html:option value="<%=val%>" ><%=dsc%></html:option> 
      <%}%>
      </html:select>
      </td></tr>
      <%}%>
     <tr> <td>
   <jsp:include page="/genericSrch.jsp"/>
   </td><td> 
     <html:textarea property="value(vnmLst)" rows="7"  cols="30" />
     </td></tr>
   <tr><td align="center"><html:submit property="value(srch)" value="Fetch" styleClass="submit" /></td> </tr>
   </table>
   </html:form>
   </td></tr>
   <tr>
    <td valign="top" class="tdLayout" height="30px">&nbsp;</td></tr>
   <tr>
    <td valign="top" class="tdLayout">
    <%
    String view = (String)request.getAttribute("view");
    if(view!=null){
    ArrayList stockList = (ArrayList)session.getAttribute("stockList");
    HashMap mprp = info.getMprp();
    if(stockList!=null && stockList.size()>0){
     ArrayList vwPrpLst = (ArrayList)session.getAttribute("MixViewLst");
    %>
   <table class="grid1" id="dataTable">
   <tr><th><input type="checkbox" name="checkAll" id="checkAll" onclick="checkALL('CHK_','count')" /> </th><th>Sr</th>
      <th></th>
       <th>Split</th>
        <th>Packet No.</th>
       <%for(int j=0; j < vwPrpLst.size(); j++ ){
        String prp = (String)vwPrpLst.get(j);
        String hdr = util.nvl((String)mprp.get(prp));
        String prpDsc = util.nvl((String)mprp.get(prp+"D"));
         if(prp.equals("CRTWT")){%>
    <th>Qty</th>
    <%} if(hdr == null) {
        hdr = prp;
         }%>
  <th title="<%=prpDsc%>"><%=hdr%></th>
   <%}%>       
  </tr>
  <%
  int sr=0;
  for(int i=0; i < stockList.size(); i++ ){
 HashMap stockPkt = (HashMap)stockList.get(i);
 String stkIdn = (String)stockPkt.get("stk_idn");
 String cts = util.nvl((String)stockPkt.get("cts"));
 String qty = util.nvl((String)stockPkt.get("qty"));
 String vnm = (String)stockPkt.get("vnm");
  sr = i+1;
   String textId = "TXT_"+stkIdn;
  String checkFldId = "CHK_"+sr;
 String checkFldVal = "value("+stkIdn+")";
 String target = "TR_"+stkIdn;
 %>
    <tr id="<%=target%>">
     <td><html:checkbox property="<%=checkFldVal%>" styleId="<%=checkFldId%>" name="mixToSingleForm" onclick="" value="yes" /> </td>
     <td><%=sr%></td>
     <td><input type="text" name="notext" id="<%=textId%>" size="4" value="10"  class="txtStyle" /></td>

     <td><a href="mixToSingle.do?method=SplitPkt&mstkIdn=<%=stkIdn%>&vnm=<%=vnm%>&cts=<%=cts%>&qty=<%=qty%>&COUNT=10"  id="LNK_<%=stkIdn%>" onclick="loadASSFNL('<%=target%>','LNK_<%=stkIdn%>');setCountURL(this,'TXT_<%=stkIdn%>');"  target="SC" >Split</a></td>
     <td><%=stockPkt.get("vnm")%></td>
     <%for(int j=0; j < vwPrpLst.size(); j++ ){
    String prp = (String)vwPrpLst.get(j);
    if(prp.equals("CRTWT")){%>
    <td><%=stockPkt.get("qty")%></td>
    <%}
    %>
    <td><%=stockPkt.get(prp)%></td>
    <%}%>
    </tr>
 <%}%>
</table>
    <%}else{%>
    Sorry no result found.
    <%}}%>
    </td></tr>
    </table>
    
   
    </td></tr></table>
  
  </body>
</html>