<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
 
<%@ page errorPage="../error_page.jsp" %>
<%@ page contentType="text/html;charset=windows-1252"%>
<jsp:useBean id="util" class="ft.com.JspUtil" scope="page" />
<jsp:useBean id="info" class="ft.com.InfoMgr" scope="session" />
<jsp:useBean id="gtMgr" class="ft.com.GtMgr" scope="session" />
<%@ page import="java.util.ArrayList,java.util.HashMap,java.sql.ResultSet,ft.com.*, java.util.Date, java.util.ArrayList, ft.com.dao.*"%>
<%@taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>Payment Trans</title>
   <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/bse.css?v=<%=info.getJsVersion()%>"/>
      <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/style.css?v=<%=info.getJsVersion()%>"/>
      <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/bse.js?v=<%=info.getJsVersion()%> " > </script>
        <link href="<%=info.getReqUrl()%>/calendar/calendar.css" rel="stylesheet" type="text/css" />
       <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/ajax.js?v=<%=info.getJsVersion()%> " > </script>
          <script language="javaScript" type="text/javascript" src="<%=info.getReqUrl()%>/calendar/calendar.js"></script>
  <script type="text/javascript" src="<%=info.getReqUrl()%>/PopScripts/popup.js?v=<%=info.getJsVersion()%> " > </script>
   <script src="<%=info.getReqUrl()%>/PopScripts/jquery-1.2.6.min.js" type="text/javascript"></script>

  </head>
     <%
        String logId=String.valueOf(info.getLogId());
        String onfocus="cook('"+logId+"')";
        DBUtil dbutil = new DBUtil();
        DBMgr db = new DBMgr(); 
        db.setCon(info.getCon());
        dbutil.setDb(db);
        dbutil.setInfo(info);
        db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));db.setDbUsr(info.getDbUsr());
        dbutil.setLogApplNm(info.getLoApplNm());
          
%>
<body onfocus="<%=onfocus%>"  topmargin="0" leftmargin="0" rightmargin="0" bottommargin="0">
<input type="hidden" id="reqUrl" name="reqUrl" value="<%=info.getReqUrl()%>"/>

 <div id="backgroundPopup"></div>
<div id="popupContactASSFNL">
<table class="popUpTb">
 <tr><td height="5%">&nbsp;&nbsp;&nbsp;<html:button property="value(close)" styleClass="submit"  onclick="MKEmptyPopup('SC');disablePopupASSFNL()" value="Close"  /> </td></tr>
<tr><td valign="top" height="95%">
<iframe name="SC" class="frameStyle" align="middle" frameborder="0">

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
  <img src="../images/line.jpg" border="0" /></td><td valign="middle"> <span class="pgHed">Receipt Payment</span> </td>
</tr></table>
  </td>
  </tr>
   <% if(request.getAttribute("msg")!=null){%>
        <tr><td class="tdLayout" height="15"><span class="redLabel"> <%=request.getAttribute("msg")%></span></td></tr>
  <%}%>
  
    <tr><td valign="top" class="tdLayout">
    <html:form action="receipt/paymentTransAction.do?method=fetch" method="post">
   <table>
   <tr><td>Mode </td> <td>:</td>
   <td> 
   <html:select property="value(payMode)"  styleId="payMode" onchange="SetBuyerList(this)" name="paymentTransForm"   >
           <html:option value="ALL" >ALL</html:option>
            <html:option value="P" >Pay</html:option>
            <html:option value="R" >Recevied</html:option>
    </html:select>
    </td></tr>
   
   <tr>
   <td>Buyer List </td> <td>:</td>
   <td> 
   <html:select property="value(byrIdn)"  styleId="byrIdn" name="paymentTransForm"   >
        <html:optionsCollection property="byrLst" name="paymentTransForm" label="byrNme" value="byrIdn" />
    </html:select>
   </td></tr>
   <tr><td>Currency </td> <td>:</td>
   <td> 
   <html:select property="value(cur)"  styleId="cur"   name="paymentTransForm"   >
            <html:option value="LOC" >Local</html:option>
            <html:option value="FOR" >Foreign</html:option>
    </html:select>
    </td></tr>
   <tr><td colspan="3"><html:submit property="value(submit)" value="Fetch" styleClass="submit" /> </td></tr>
   </table>
    </html:form>  
   </td></tr> 
    <tr><td valign="top" class="tdLayout"> 
     <html:form action="receipt/paymentTransAction.do?method=save" method="post" onsubmit="return VerifiedTransAmt()" >
     <html:hidden name="paymentTransForm" property="value(cur)" styleId="CUR" />
          <html:hidden name="paymentTransForm" property="value(curXrt)" styleId="curXrt" />
     <%
   HashMap dtlMap = (HashMap)gtMgr.getValue("DTLMAP");
   if(dtlMap!=null && dtlMap.size()>0){ 
   ArrayList paidByLst = (ArrayList)dtlMap.get("PAID");
   ArrayList payableList = (ArrayList)dtlMap.get("PAY");
      ArrayList recByLst = (ArrayList)dtlMap.get("REC");
      String cur = util.nvl((String)dtlMap.get("CUR"));
      if((recByLst!=null && recByLst.size()>0)||(payableList!=null && payableList.size()>0)){
   %>
    <table cellpadding="1" cellspacing="1">
    <tr><td><html:submit property="value(submit)" value="Save Changes" styleClass="submit" /></td></tr>
    <tr><td><table cellpadding="2" cellspacing="2">
    <tr><td>Party Name</td><td><b><%=util.nvl((String)dtlMap.get("PARTYNME"))%></b></td>
    <td>
    <%if(cur.equals("LOC")){%>
    Currency : Local
    <%}else{%>
    Currency : Foreign
    <%}%></td>
    <td> <b>Confirmed By :</b> </td><td>
    <input type="text" name="confirmBy" id="confirmBy" value="" />
    </td>
    <td> <b>Remark :</b> </td><td>
    <input type="text" name="rmk" id="rmk" value="" />
    </td>
    </tr></table>
    </td></tr>
    <tr><td>
      <%
     
        if(recByLst!=null && recByLst.size()>0){
     %>
      
     <table cellpadding="0" cellspacing="0"><tr> <td valign="top">
     <table><tr><td>
     <table><tr><td>Received Via</td>
    <td><input type="radio" name="REC" value="A"  onclick="SetPartyList(this,'REC')" /></td><td>A</td>
    <td><input type="radio" name="REC" value="B" onclick="SetPartyList(this,'REC')" /></td><td>B</td>
    <td><input type="radio" name="REC" value="C" onclick="SetPartyList(this,'REC')" /></td><td>C</td>
    <td><input type="radio" name="REC" value="T" onclick="SetPartyList(this,'REC')" /></td><td>T</td></tr> 
     </table>
    </td></tr>
   <tr><td>
     <table class="grid1"  id="dataTableREC">
     <tr><th> </th><th>Ref Type</th><th>Ref Idn</th><th>CNT IDN</th><th>Acc Typ</th>
     <th colspan="2">Xrt</th><th colspan="2">Cur($)</th><th colspan="2">Amount</th>
     <th colspan="3">Payment Mode</th></tr>
     <tr><td colspan="11"></td><td>Part</td><td>Full</td><td>Short</td>
     </tr>
     <%
     int cnt=0;
      
     for(int i=0;i<recByLst.size();i++){
     HashMap recMap = (HashMap)recByLst.get(i);
     if(recMap!=null && recMap.size()>0){
     cnt++;
     String amt =util.nvl((String)recMap.get("AMT"));
     String invId =util.nvl((String)recMap.get("REFIDN"));
     String ref_typ =util.nvl((String)recMap.get("REFTYP"));
     String cnt_idn=util.nvl((String)recMap.get("CNTIDN"));
     String xrt =util.nvl((String)recMap.get("XRT"));
     String payPartyId="RECPRTY_"+cnt;
     String payPartyNme = "value("+payPartyId+")";
       String target = "TR_"+invId;
     %>
     <tr id="<%=target%>">
     <td><input type="radio" name="CB_REC" value="<%=cnt%>" id="CB_REC_<%=cnt%>"  />
     </td>
     <td><%=ref_typ%> </td><td><a href="receviceInvAction.do?method=PktDtl&refIdn=<%=invId%>&TYP=<%=ref_typ%>" id="LNK_<%=invId%>" onclick="setBGColorSelectTR1('<%=target%>','dataTableREC');loadASSFNLPop('LNK_<%=invId%>')"  target="SC" > <%=invId%> </a>  </td>
     <td>
     <input type="hidden" name="RECCNTIDN_<%=cnt%>" id="RECCNTIDN_<%=cnt%>" value="<%=cnt_idn%>" />
     <%=util.nvl((String)recMap.get("CNTIDN"))%></td> <td><%=util.nvl((String)recMap.get("ACCTYP"))%></td>
         
      <td align="right"><%=util.nvl((String)recMap.get("XRT"))%> 
       <input type="hidden" name="RECXRT_<%=cnt%>" id="RECXRT_<%=cnt%>" value="<%=xrt%>" />
      </td>
             <td> <input type="text" name="RECXRTTXT_<%=cnt%>" id="RECXRTTXT_<%=cnt%>" readonly="true" size="5" />
             
             </td>

       <td align="right"><%=util.nvl((String)recMap.get("CUR"))%><input type="hidden" name="RECCUR_<%=cnt%>" id="RECCUR_<%=cnt%>" value="<%=util.nvl((String)recMap.get("CUR"))%>" /> </td>
        <td> <input type="text" name="RECCURTXT_<%=cnt%>" id="RECCURTXT_<%=cnt%>" readonly="true" size="10" /></td>
      <td><%=amt%> </td>
      <td> 
      <input type="hidden" name="RECAMT_<%=cnt%>" id="RECAMT_<%=cnt%>" value="<%=amt%>" />
      <input type="text" name="RECTXT_<%=cnt%>" id="RECTXT_<%=cnt%>"  readonly="true" size="12" />
     </td>
     <td><input type="radio" name="REC_<%=cnt%>" id="RECPART_<%=cnt%>" checked="checked" onclick="PaymentMode(this,'F','<%=cnt%>','REC')"  value="part"/> </td>
     <td><input type="radio" name="REC_<%=cnt%>" id="RECFULL_<%=cnt%>" onclick="PaymentMode(this,'T','<%=cnt%>','REC')" value="full"/></td>
     <td><input type="checkbox" name="RECSHRT_<%=cnt%>" id="RECSHRT_<%=cnt%>" onclick="unabletxt(this,'RECSHRTTXT_<%=cnt%>')" disabled="disabled" value="short"/>
      &nbsp;&nbsp; <input type="text" name="RECSHRTTXT_<%=cnt%>" id="RECSHRTTXT_<%=cnt%>" size="5" onchange="chnagesFullAmt(this,'REC','<%=cnt%>')" disabled="disabled" />
     </td>
    
     </tr>
     <%}}%>
     <tr><td> <input type="hidden" id="RECCNT" value="<%=cnt%>" /></td></tr>
     </table></td></tr></table>
     
     </td>   <td valign="top">
     <div id="RECBY"></div>
     </td>   </tr></table>  <%}%>
     </td></tr>
     <tr><td>
      <%
     if(payableList!=null && payableList.size()>0){
     %>
   <table><tr><td valign="top">
     <table><tr><td>
     <table><tr><td>Pay Via</td>
    <td><input type="radio" name="PAY" value="A"  onclick="SetPartyList(this,'PAY')" /></td><td>A</td>
    <td><input type="radio" name="PAY" value="B" onclick="SetPartyList(this,'PAY')" /></td><td>B</td>
    <td><input type="radio" name="PAY" value="C" onclick="SetPartyList(this,'PAY')" /></td><td>C</td>
    <td><input type="radio" name="PAY" value="T" onclick="SetPartyList(this,'PAY')" /></td><td>T</td></tr> 
     </table>
    </td></tr>
   <tr><td>
     <table class="grid1"  id="dataTablePAY">
     <tr><th> </th><th>Ref Type</th><th>Ref Idn</th><th>Cnt Idn</th><th>Acc Typ</th><th colspan="2">Xrt</th><th colspan="2">Cur($)</th><th colspan="2">Amount</th>
     <th colspan="3">Payment Mode</th></tr>
     <tr><td colspan="11"></td><td>Part</td><td>Full</td><td>Short</td>
     </tr>
     <%
     int cnt=0;
     for(int i=0;i<payableList.size();i++){
     HashMap payMap = (HashMap)payableList.get(i);
     if(payMap!=null && payMap.size()>0){
     cnt++;
     String amt =util.nvl((String)payMap.get("AMT"));
      String recPartyId="PAYPRTY_"+cnt;
     String recPartyNme = "value("+recPartyId+")";
     String refTyp =util.nvl((String)payMap.get("REFTYP"));
     String xrt =util.nvl((String)payMap.get("XRT"));
     String invId=util.nvl((String)payMap.get("REFIDN"));
     String cnt_idn=util.nvl((String)payMap.get("CNTIDN"));
     String target = "TR_"+invId;
     %>
     <tr id="<%=target%>"><td><input type="radio" name="CB_PAY" value="<%=cnt%>" id="CB_PAY_<%=cnt%>"  /></td>
     <td><%=util.nvl((String)payMap.get("REFTYP"))%></td><td>
     <a href="receviceInvAction.do?method=PktDtl&refIdn=<%=invId%>&TYP=<%=refTyp%>" id="LNK_<%=invId%>" onclick="setBGColorSelectTR1('<%=target%>','dataTablePAY');loadASSFNLPop('LNK_<%=invId%>')"  target="SC" > <%=util.nvl((String)payMap.get("REFIDN"))%></a></td>
       <td>
          <input type="hidden" name="PAYCNTIDN_<%=cnt%>" id="PAYCNTIDN_<%=cnt%>" value="<%=cnt_idn%>" />
  
       <%=util.nvl((String)payMap.get("CNTIDN"))%></td><td ><%=util.nvl((String)payMap.get("ACCTYP"))%> </td> <td align="right"><%=util.nvl((String)payMap.get("XRT"))%> 
       <input type="hidden" name="PAYXRT_<%=cnt%>" id="PAYXRT_<%=cnt%>" value="<%=xrt%>" />
       </td>
       <td> <input type="text" name="PAYXRTTXT_<%=cnt%>" id="PAYXRTTXT_<%=cnt%>" readonly="true" size="5" /></td>
       <td align="right"><%=util.nvl((String)payMap.get("CUR"))%> </td>
    <td> <input type="text" name="PAYCURTXT_<%=cnt%>" id="PAYCURTXT_<%=cnt%>" readonly="true" size="10" />
    <input type="hidden" name="PAYCUR_<%=cnt%>" id="PAYCUR_<%=cnt%>" value="<%=util.nvl((String)payMap.get("CUR"))%>" />
    </td>
      <td align="right"><%=amt%> </td>
      <td> 
      <input type="hidden" name="PAYAMT_<%=cnt%>" id="PAYAMT_<%=cnt%>" value="<%=amt%>" />
      <input type="text" name="PAYTXT_<%=cnt%>" id="PAYTXT_<%=cnt%>" readonly="true" size="12" />
     </td>
     <td><input type="radio" name="PAY_<%=cnt%>" id="PAYPART_<%=cnt%>" checked="checked" onclick="PaymentMode(this,'F','<%=cnt%>','PAY')"  value="part"/> </td>
     <td><input type="radio" name="PAY_<%=cnt%>" id="PAYFULL_<%=cnt%>" onclick="PaymentMode(this,'T','<%=cnt%>','PAY')" value="full"/></td>
     <td><input type="checkbox" name="PAYSHRT_<%=cnt%>" id="PAYSHRT_<%=cnt%>" onclick="unabletxt(this,'PAYSHRTTXT_<%=cnt%>')"  disabled="disabled" value="short"/>
      &nbsp;&nbsp;<input type="text" name="PAYSHRTTXT_<%=cnt%>" id="PAYSHRTTXT_<%=cnt%>" onchange="chnagesFullAmt(this,'PAY','<%=cnt%>')" size="5" disabled="disabled" /></td>
     </tr>
     <%}}%>
     <tr><td> <input type="hidden" id="PAYCNT" value="<%=cnt%>" /></td></tr>
       </table></td></tr></table>
     
     </td><td valign="top">
     <div id="PAYBY"></div>
     </td>
     </tr></table>
    
     <%}%>
     
     </td></tr></table><%}}%>
     </html:form>
     
    </td></tr>
    
    <tr><td>
    <jsp:include page="/footer.jsp" />
    </td></tr>
    </table>
    </body>
</html>