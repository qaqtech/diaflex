<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page errorPage="../error_page.jsp" %>
<%@ page contentType="text/html;charset=windows-1252"%>
<jsp:useBean id="info" class="ft.com.InfoMgr" scope="session" />
<jsp:useBean id="util" class="ft.com.JspUtil" scope="page" />
<jsp:useBean id="gtMgr" class="ft.com.GtMgr" scope="session" />

<%@ page import="java.util.ArrayList,java.util.LinkedHashMap,java.util.HashMap,java.util.Set,java.sql.ResultSet,ft.com.*, java.util.Date, java.util.ArrayList, ft.com.dao.*"%>
<%@taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>

<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>Certificate Return</title>
    <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/bse.css?v=<%=info.getJsVersion()%>"/>
    <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/general.css?v=<%=info.getJsVersion()%>"/>
     <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/style.css?v=<%=info.getJsVersion()%>"/>
     <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/bse.js?v=<%=info.getJsVersion()%> " > </script>
      <script type="text/javascript" src="<%=info.getReqUrl()%>/PopScripts/popup.js?v=<%=info.getJsVersion()%> " > </script>
     <script src="<%=info.getReqUrl()%>/PopScripts/jquery-1.2.6.min.js" type="text/javascript"></script>
          <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/newCalScript.js?v=<%=info.getJsVersion()%> " > </script>
       <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/ajax.js?v=<%=info.getJsVersion()%> " > </script>

  </head>
        <%String logId=String.valueOf(info.getLogId());
        String onfocus="cook('"+logId+"')";
        String lstNme = (String)gtMgr.getValue("lstNmeCRTRT");

        %>
 <body onfocus="<%=onfocus%>" onload="MM_preloadImages('images/faunalogo.png')" topmargin="0" leftmargin="0" rightmargin="0" bottommargin="0">

<div id="backgroundPopup"></div>
<div id="popupContactASSFNL">
<table class="popUpTb">
 <tr><td height="5%">&nbsp;&nbsp;&nbsp;<html:button property="value(close)" styleClass="submit"  onclick="MKEmptyPopup('SC');disablePopupASSFNL()" value="Close"  /> </td></tr>
<tr><td valign="top" height="95%">
<iframe name="SC" class="frameStyle"   align="middle" frameborder="0">

</iframe></td></tr>
</table>
</div>
<input type="hidden" id="reqUrl" name="reqUrl" value="<%=info.getReqUrl()%>"/>
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
  <img src="../images/line.jpg" border="0" /></td><td valign="middle"> <span class="pgHed">Cert Return</span> </td>
</tr></table>
  </td>
  </tr>
  <%
   String msg = (String)request.getAttribute("msg");
     if(msg!=null){%>
    <tr><td valign="top" class="tdLayout"><span class="redLabel"><%=msg%></span></td></tr>
   <%}%>
   <tr>
   
   <td valign="top" class="tdLayout">
   <table><tr><td>
   <html:form action="marketing/certRetrunAction.do?method=fecth" method="post" onsubmit="return validate_Assortreturn()">
   <table><tr><td>Process : </td>
   <td>
   <html:select property="value(mprcIdn)"  styleId="mprcIdn" name="certReturnFrom"   >
            <html:optionsCollection property="processLst" name="certReturnFrom" 
            label="prc" value="mprcId" />
    </html:select>
   </td></tr>
  <tr>
    <td>
    Buyer : </td>
   <td>
  <html:text  property="value(partytext)" name="certReturnFrom" styleId="partytext" style="width:180px" styleClass="sugBox" onkeypress="return disableEnterKey(event);" 
           onkeyup="doCompletion('partytext', 'nmePop', '../ajaxAction.do?1=1', event)"
      onkeydown="setDownSerchpage('nmePop', 'party', 'partytext',event)" onclick="document.getElementById('partytext').autocomplete='off'"  />
      <img src="../images/addrow.jpg" width="17" height="17" title="Click To get Names" onclick="doCompletionMobile('partytext', 'nmePop', '../ajaxAction.do?1=1')"/>
      <html:hidden property="value(empIdn)" styleId="party"  />
  <div style="position: absolute;">
      <select id="nmePop" name="nmePop"
        style="display:none;300px;" 
        class="sugBoxDiv" 
        onKeyDown="setDownSerchpage('nmePop', 'party', 'partytext',event)" 
        onDblClick="setVal('nmePop', 'party', 'partytext', event);hideObj('nmePop')" 
        onClick="setVal('nmePop', 'party', 'partytext', event)" 
        size="10">
      </select>
</div>            
   </td>
   </tr>
   <tr>
   <td>Issue Id:</td><td> <html:text property="value(issueId)" styleId="issueId"  name="certReturnFrom"  /></td>
   </tr>
   <tr>
   <td>Packet No/Cert No :</td><td> <html:textarea property="value(vnmLst)" rows="7"  cols="30" name="certReturnFrom"  /></td>
   </tr>
   <tr><td>
   <html:select property="value(TYP)" name="certReturnFrom">
   <html:option value="MEMO">MEMO ISSUE</html:option>
    <html:option value="AP">APPROVE</html:option>
    <html:option value="SALE">SALE</html:option>
    <html:option value="DLV">DELIVERY</html:option>
   </html:select>
   </td><td> <html:text property="value(idn)" name="certReturnFrom" /> </td></tr> 
   <tr>
   <td colspan="2">  <html:submit property="value(srch)" value="Fetch" styleClass="submit" /></td>
   </tr></table>
   </html:form>
   </td></tr></table></td></tr>
   <tr><td valign="top" class="tdLayout">
    <%
   String view = (String)request.getAttribute("view");
   if(view!=null){
   ArrayList prpDspBlocked = info.getPageblockList();
   HashMap stockListMap = (HashMap)gtMgr.getValue(lstNme);
   if(stockListMap!=null && stockListMap.size()>0){
    ArrayList vwPrpLst = (ArrayList)session.getAttribute("mktViewLst");
     HashMap totals = (HashMap)gtMgr.getValue(lstNme+"_TTL");
      HashMap mprp = info.getMprp();
    int sr = 0;
    
   %>
  
  <html:form action="marketing/certRetrunAction.do?method=Return" method="post" onsubmit="return validate_issue('CHK_' , 'count')" >

    <table>
    <tr><td>
   <table>
   <tr><td>Total :&nbsp;&nbsp;</td><td><span id="ttlqty"><%=totals.get("MQ")%></span> &nbsp;&nbsp;</td><td>cts&nbsp;&nbsp;</td><td><span id="ttlcts"> <%=totals.get("MW")%></span> &nbsp;&nbsp;</td><td>Selected:&nbsp;&nbsp;</td><td> Total :&nbsp;&nbsp;</td> <td><span id="qtyTtl">0&nbsp;&nbsp;</span></td><td>Cts&nbsp;&nbsp;</td><td><span id="ctsTtl">0</span> </td> </tr>
  
   </table>
   </td></tr>
  <tr><td>
  <html:submit property="value(issue)" styleClass="submit" value="Return" /> 
  
   </td></tr>
   
   
     <tr><td>
      <html:hidden property="value(lstNme)" name="certReturnFrom" styleId="lstNme"  />
<% 
ArrayList itemHdr = new ArrayList();
HashMap hdrDtl = new HashMap();
  hdrDtl.put("hdr", "issIdn");
  hdrDtl.put("typ", "N");
  itemHdr.add(hdrDtl);
  hdrDtl = new HashMap();
  hdrDtl.put("hdr", "vnm");
  hdrDtl.put("typ", "N");
  itemHdr.add(hdrDtl);
  hdrDtl = new HashMap();
  hdrDtl.put("hdr", "emp");
  hdrDtl.put("typ", "N");
  itemHdr.add(hdrDtl);
%>
   <table class="grid1" id="dataTable">
   <tr> <th><input type="checkbox" name="checkAll" id="checkAll" onclick="ALLCheckBoxCalLst('checkAll','CHK_')" /> </th><th>Sr</th>
       <th>Issue Id</th>
        <th>Packet No.</th>
        <th>Employee</th>
        <%for(int j=0; j < vwPrpLst.size(); j++ ){
    String prp = (String)vwPrpLst.get(j);
     String hdr = util.nvl((String)mprp.get(prp));
    String prpDsc = util.nvl((String)mprp.get(prp+"D"));
    if(prpDspBlocked.contains(prp)){
       }else{
    if(hdr == null) {
        hdr = prp;
       }  
      hdrDtl = new HashMap();
  hdrDtl.put("hdr", prp);
  hdrDtl.put("typ", "P");
    hdrDtl.put("dp", "D");

  itemHdr.add(hdrDtl);
%>
<th title="<%=prpDsc%>"><%=hdr%></th>

<%}}%> 



</tr>
 <%
 int colSpan = vwPrpLst.size()+4;
 int lastpage=stockListMap.size()-1;
  LinkedHashMap stockList = new LinkedHashMap(stockListMap); 

 ArrayList stockIdnLst =new ArrayList();
 Set<String> keys = stockList.keySet();
        for(String key: keys){
       stockIdnLst.add(key);
        }
  gtMgr.setValue(lstNme+"_ALLLST", stockIdnLst);
 for(int i=0; i < stockIdnLst.size(); i++ ){
 String stkIdn = (String)stockIdnLst.get(i);
 GtPktDtl stockPkt = (GtPktDtl)stockList.get(stkIdn);
 String cts = (String)stockPkt.getCts();
 String issIdn = (String)stockPkt.getValue("issIdn");
 String onclick = "CalTtlOnChlickLst("+stkIdn+" , this , 'NO' )";
 String target = "SC_"+stkIdn;
 sr = i+1;
 String checkFldId = "CHK_"+sr;
 String checkFldVal = "value("+stkIdn+")";
 %>
<tr id="<%=target%>">
<td><input type="checkbox" name="<%=checkFldVal%>" id="<%=checkFldId%>" value="<%=stkIdn%>" onclick="<%=onclick%>" /> </td>
<td><%=sr%><input type="hidden" id="CTS_<%=stkIdn%>"  value="<%=cts%>" /></td>
<td><%=stockPkt.getValue("issIdn")%></td>
<td><%=stockPkt.getVnm()%></td>
<td><%=stockPkt.getValue("emp")%></td>
<%for(int j=0; j < vwPrpLst.size(); j++ ){
    String prp = (String)vwPrpLst.get(j);
    if(prpDspBlocked.contains(prp)){
       }else{
    %>
    <td><%=stockPkt.getValue(prp)%></td>
<%}}%>

</tr>
<%}%>
</table>
</td></tr></table></html:form>
<%}else{%>
<tr><td>Sorry no result found.</td></tr>
<%}%>

<%}%></td></tr>
  <tr>
    <td><jsp:include page="/footer.jsp" /> </td>
    </tr> 
   </table>
   
   </body></html>
  