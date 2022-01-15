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
    <title>Mix Assort Return</title>
    <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/bse.css?v=<%=info.getJsVersion()%>"/>
    <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/general.css?v=<%=info.getJsVersion()%>"/>
     <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/style.css?v=<%=info.getJsVersion()%>"/>
     <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/bse.js?v=<%=info.getJsVersion()%> " > </script>
      <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/assortScript.js?v=<%=info.getJsVersion()%> " > </script>
     <script type="text/javascript" src="<%=info.getReqUrl()%>/PopScripts/popup.js?v=<%=info.getJsVersion()%> " > </script>
   <script src="<%=info.getReqUrl()%>/PopScripts/jquery-1.2.6.min.js" type="text/javascript"></script>
        <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/ajax.js?v=<%=info.getJsVersion()%> " > </script>


  </head>
        <%String logId=String.valueOf(info.getLogId());
        String onfocus="cook('"+logId+"')";
        %>
 <body onfocus="<%=onfocus%>" onload="MM_preloadImages('images/faunalogo.png')" topmargin="0" leftmargin="0" rightmargin="0" bottommargin="0">
<input type="hidden" id="reqUrl" name="reqUrl" value="<%=info.getReqUrl()%>"/>
<div id="backgroundPopup"></div>
<div id="popupContactASSFNL">
<table class="popUpTb">
 <tr><td height="5%">&nbsp;&nbsp;&nbsp;<html:button property="value(close)" styleClass="submit"  onclick="MKEmptyPopup('SC');disablePopupASSFNL();" value="Close"  /> </td></tr>
<tr><td valign="top" height="95%">
<iframe name="SC" id="frame" class="frameStyle"   align="middle" frameborder="0">

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
  <img src="../images/line.jpg" border="0" /></td><td valign="middle"> <span class="pgHed">Mix Assort Return</span> </td>
</tr></table>
  </td>
  </tr>
  <%
  //util.updAccessLog(request,response,"Assort Final Return", "Assort Final Return");
   String vnmNtFnd = (String)request.getAttribute("vnmNotFnd");
     if(vnmNtFnd!=null){%>
    <tr><td valign="top" class="tdLayout"><span class="redLabel"><%=vnmNtFnd%></span></td></tr>
   <%}%>
   
   <tr>
   
   <td valign="top" class="tdLayout">
   <table><tr><td>
   <html:form action="mix/mixAssortRtnAction.do?method=fecth" method="post" onsubmit="return validate_Assortreturn()">
   <table><tr><td>Process : </td>
   <td>
   <html:select property="value(mprcIdn)"  styleId="mprcIdn" name="mixAssortRtnForm"   >
           <html:option value="0" >--select--</html:option>
            <html:optionsCollection property="mprcList" name="mixAssortRtnForm" 
            label="prc" value="mprcId" />
    </html:select>
   </td></tr>
   <tr>
    <td>
    Employee : </td>
   <td>
   <html:select property="value(empIdn)" styleId="empIdn" name="mixAssortRtnForm"   >
           <html:option value="0" >--select--</html:option>
            <html:optionsCollection property="empList" name="mixAssortRtnForm" 
            label="emp_nme" value="emp_idn" />
            </html:select>
   </td>
   </tr>
   <tr>
   <td>Issue Id:</td><td> <html:text property="value(issueId)" styleId="issueId"  name="mixAssortRtnForm"  /></td>
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
   String msg = (String)msgLst.get(i);
   %>
    <tr><td valign="top" class="tdLayout"><span class="redLabel"><%=msg%></span></td></tr>
   <%}}%>
    
   <%
     String view = (String)request.getAttribute("view");
   if(view!=null){
   ArrayList prpDspBlocked = info.getPageblockList();
   ArrayList stockList = (ArrayList)session.getAttribute("AssortStockList");
   if(stockList!=null && stockList.size()>0){
    HashMap pktCountMap = (HashMap)request.getAttribute("pktCountMap");
    ArrayList vwPrpLst = (ArrayList)session.getAttribute("MIX_VIEW");
    HashMap totals = (HashMap)request.getAttribute("totalMap");
    HashMap mprp = info.getMprp();

    %>
     <tr><td>
   <table>
   <tr><td>Total :&nbsp;&nbsp;</td><td><span id="ttlqty"><%=totals.get("qty")%></span> &nbsp;&nbsp;</td><td>cts&nbsp;&nbsp;</td><td><span id="ttlcts"> <%=totals.get("cts")%></span> &nbsp;&nbsp;</td><td>Selected:&nbsp;&nbsp;</td><td> Total :&nbsp;&nbsp;</td> <td><span id="qtyTtl">0&nbsp;&nbsp;</span></td><td>Cts&nbsp;&nbsp;</td><td><span id="ctsTtl">0</span> </td> </tr>
  
   </table>
   </td></tr>
    <html:form action="mix/mixAssortRtnAction.do?method=Return" method="post" onsubmit="return validate_issue('CHK_' , 'count')" >
  <tr><td><html:submit property="value(return)" value="Return" styleClass="submit" /> </td></tr>
  <tr><td>
  
   <table class="grid1" id="dataTable">
   <tr> <th><input type="checkbox" name="checkAll" id="checkAll" onclick="ALLCheckBoxDisable('checkAll','CHK_')" /> </th><th>Sr</th>
       <th>Issue Id</th>
        <th>Packet No.</th>
        
        <%for(int j=0; j < vwPrpLst.size(); j++ ){
    String prp = (String)vwPrpLst.get(j);
     String hdr = util.nvl((String)mprp.get(prp));
    String prpDsc = util.nvl((String)mprp.get(prp+"D"));
    if(prpDspBlocked.contains(prp)){
       }else{
    if(hdr == null) {
        hdr = prp;
       }   %>
          <th title="<%=prpDsc%>"><%=hdr%></th>

     <%}}%><th>Split Count</th> <th></th></tr>
 <% for(int i=0; i < stockList.size(); i++ ){
 HashMap stockPkt = (HashMap)stockList.get(i);
 String stkIdn = (String)stockPkt.get("stk_idn");
  String pktCnt =util.nvl((String)pktCountMap.get(stkIdn),"0");
  String vnm = (String)stockPkt.get("vnm");
 String stt = util.nvl((String)stockPkt.get("stt"));
 String cts = (String)stockPkt.get("cts");
 String issIdn = (String)stockPkt.get("issIdn");
 String textId = "TXT_"+stkIdn;
  String target = "SC_"+stkIdn;
  String checkBoxId="CHK_"+stkIdn;
  String checkboxNme="value("+checkBoxId+")";
  boolean isDisabled = true;
  String LNKStyle="display:block";
  String BTNStyle="display:none";
  String readOnly = "";
  String onclick ="AssortTotalCal(this,"+cts+",'','')";
  if(stt.equals("LK")){
    isDisabled=false;
    LNKStyle = "display:none";
    BTNStyle = "display:block";
    readOnly = "readonly=\"readOnly\"";
    }
  int sr=i+1;
 %>
 <tr id="<%=target%>"><td> <html:checkbox  property="<%=checkboxNme%>" disabled="<%=isDisabled%>" onclick="<%=onclick%>" styleId="<%=checkBoxId%>" value="yes" /></td><td><%=sr%></td> <td><%=issIdn%></td><td><%=vnm%></td>
 <%for(int j=0; j < vwPrpLst.size(); j++ ){
    String prp = (String)vwPrpLst.get(j);
    %>
    <td><%=stockPkt.get(prp)%></td>
 <%}%>
<td><input type="text" name="notext" id="<%=textId%>" size="4" value="<%=pktCnt%>"  onchange="setCountURL(this,'TXT_<%=stkIdn%>')" class="txtStyle" <%=readOnly%>/></td>
<td>
<div id="LNKD_<%=stkIdn%>" style="<%=LNKStyle%>">
<a href="mixAssortRtnAction.do?method=SplitStone&mstkIdn=<%=stkIdn%>&issIdn=<%=issIdn%>&ttlcts=<%=cts%>" id="LNK_<%=stkIdn%>" onclick="loadASSFNL('<%=target%>','LNK_<%=stkIdn%>');setCountURL(this,'TXT_<%=stkIdn%>');"  target="SC">Split</a>
</div>
<div id="BTN_<%=stkIdn%>" style="<%=BTNStyle%>">
<input type="button" name="unlock" value="Unlock" onclick="unlockLnk('<%=issIdn%>','<%=stkIdn%>')" class="submit" />
</div>
</td>

</tr>
 <%}%>
  
   
   </table>
   </td></tr>
  </html:form>
  <%}}%>
  </table></td></tr>
   <tr>
    <td><jsp:include page="/footer.jsp" /> </td>
    </tr>
  </table>

  </body>
</html>