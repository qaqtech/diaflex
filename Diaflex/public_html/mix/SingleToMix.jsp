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
    <title>Single To Mix</title>
    <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/bse.css?v=<%=info.getJsVersion()%>"/>
    <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/general.css?v=<%=info.getJsVersion()%>"/>
     <script type="text/javascript" src="<%=info.getReqUrl()%>/PopScripts/jquery-1.2.6.min.js" > </script>
          <script type="text/javascript" src="<%=info.getReqUrl()%>/jqueryScript/jqueryScript.js" > </script>
           <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/priceChange.js?v=<%=info.getJsVersion()%> " > </script>

     <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/style.css?v=<%=info.getJsVersion()%>"/>
      <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/bse.js?v=<%=info.getJsVersion()%> " > </script>
          <script type="text/javascript" src="<%=info.getReqUrl()%>/PopScripts/popup.js?v=<%=info.getJsVersion()%> " > </script>
  </head>
        <%String logId=String.valueOf(info.getLogId());
        String onfocus="cook('"+logId+"')";
          String trnsTyp= util.nvl(request.getParameter("transmode"));
          String disCRT="display:none";
          String disPkt="display:block";
          String isPktChecked = "checked=\"checked\"";
          String isCRTChecked = "";
          if(trnsTyp.equals("CRT")){
            disCRT="display:block";
           disPkt="display:none";
           isCRTChecked = "checked=\"checked\"";
           isPktChecked ="";
          }
         String vwFMdl = (String)info.getValue("VWFMDL");
         String vwTMdl = (String)info.getValue("VWTMDL");
         String typ=(String)info.getValue("TYP");
                   String stt = util.nvl((String)request.getAttribute("stt"));
        String url="singleToMix.do?method=closePop&stt="+stt+"&transmode="+trnsTyp;
        String onclickBtn="MKEmptyPopup('SC');disablePopupASSFNL();windowOpen('"+url+"','_self');";
        String resetBtn = "windowOpen('singleToMix.do?method=load&typ="+typ+"','_self')";
        %>
 <body onfocus="<%=onfocus%>" onload="MM_preloadImages('images/faunalogo.png')" topmargin="0" leftmargin="0" rightmargin="0" bottommargin="0">
    <input type="hidden" id="reqUrl" name="reqUrl" value="<%=info.getReqUrl()%>"/>

<div id="backgroundPopup"></div>
<div id="popupContactASSFNL">
<table class="popUpTb">
 <tr><td height="5%">&nbsp;&nbsp;&nbsp;<html:button property="value(close)" styleClass="submit"  onclick="<%=onclickBtn%>" value="Close"  /> </td></tr>
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
  <img src="../images/line.jpg" border="0" /></td><td valign="middle"> <span class="pgHed">Single to Mix </span> </td>
  </tr></table>
  </td>
  </tr>
    <%String msg = (String)request.getAttribute("msg");
  if(msg!=null){
  %>
  <tr><td valign="top" class="tdLayout"> <span class="redLabel" > <%=msg%></span></td></tr>
  <%}%>
  
   <tr>
  <td valign="top" class="tdLayout">
    <html:form action="/mix/singleToMix.do?method=fetch" method="post" >
    <table cellpadding="2" cellspacing="2">
 
  <tr>
  <td colspan="2">Transfer : <input type="radio" name="transmode"  value="PKT" onclick="showHidDiv('PKTDIV')" <%=isPktChecked%>/> Packets wise&nbsp;&nbsp;<input type="radio" name="transmode" value="CRT" onclick="showHidDiv('CRTDIV')" <%=isCRTChecked%>/> Criteria wise </td>
  </tr>
  <tr>
  <td colspan="2">
  <table cellpadding="2" cellspacing="2">
  <tr><td>
  <div id="PKTDIV" class="floating" style="<%=disPkt%>" >
  <table cellpadding="2" cellspacing="2" class="grid1"><tr> <th>From</th><th>To</th></tr>
  <tr><td>
  Status : &nbsp;   <html:select property="value(sttPFm)" >
        <html:optionsCollection property="sttList" label="FORM_TTL" value="FORM_NME" />
  </html:select>
  </td><td>   Status : &nbsp;   <html:select property="value(sttPTo)" >
        <html:optionsCollection property="sttToList" label="FORM_TTL" value="FORM_NME" />
  </html:select>
  </td></tr>
  <tr><td><html:textarea property="value(vnmFrm)" rows="2" cols="20" name="singleToMixForm" /> </td>
  <td><html:textarea property="value(vnmTo)" rows="2" cols="20" name="singleToMixForm" /></td> </tr>
  </table>
  
  </div>
  
  </td><td>
  
  <div id="CRTDIV" class="floating" style="<%=disCRT%>">
  <table cellpadding="2" cellspacing="2" class="grid1">
  <tr><th>From</th><th>To</th></tr>
  <tr><td>
   Status : &nbsp;   <html:select property="value(sttCFm)" >
        <html:optionsCollection property="sttList" label="FORM_TTL" value="FORM_NME" />
  </html:select>
 </td><td>  Status :&nbsp;

   <html:select property="value(sttCTo)" >
        <html:optionsCollection property="sttToList" label="FORM_TTL" value="FORM_NME" />
  </html:select>
 
  </td></tr>
  <tr>  <td>
  <%
   HashMap mprp = info.getMprp();
    HashMap prp = info.getPrp();
    ArrayList prpPrt =null;
    ArrayList prpSrt = null;
    ArrayList prpVal =null;
    String fld1 ="";
    String prpFld1 ="";
    ArrayList FrmSrchList = (ArrayList)session.getAttribute("FrmSrchList");
  if(FrmSrchList!=null && FrmSrchList.size()>0){%>
  <table cellpadding="2" cellspacing="2">
 <% for(int i=0;i<FrmSrchList.size();i++){
      ArrayList   prplist =(ArrayList)FrmSrchList.get(i);
      String lprp = util.nvl((String)prplist.get(0));
      String lprpDsc = util.nvl((String)mprp.get(lprp+"D"));
       prpPrt = (ArrayList)prp.get(lprp+"P");
         prpSrt = (ArrayList)prp.get(lprp+"S");
        
       fld1 = lprp;
       prpFld1 = "value(" + fld1 + ")" ;
       if(prpPrt!=null && prpPrt.size()>0){
  %>
  <tr><td><%=lprpDsc%></td><td> 
  
   <html:select  property="<%=prpFld1%>" style="width:75px" name="singleToMixForm"  styleId="<%=fld1%>"  > 
     
            <html:option value="0">---select----</html:option>
            <%
            for(int j=0; j < prpSrt.size(); j++) {
                String pPrt = util.nvl((String)prpPrt.get(j));
                String pSrt = util.nvl((String)prpSrt.get(j));
                        %>
              <html:option value="<%=pSrt%>" ><%=pPrt%></html:option> 
            <%}%>
              </html:select>
          
  </td></tr>
  <%}}%>
  </table>
  <%}%>
  </td> <td>
   
    <jsp:include page="/genericSrch.jsp"/>
   </td></tr>
   </table>
  </div>
  
  </td></tr>
  </table>
  
  </td>
  </tr>
  <tr><td colspan="2" align="center"><html:submit property="value(srch)" value="Fetch" styleClass="submit" />
  &nbsp;&nbsp;<input type="button" value="Reset" onclick="" class="submit" />
  </td> </tr>
 
  </table></html:form>

    </td></tr>
   
   <tr>
  <td valign="top" class="tdLayout">
  <%
  String view = (String)request.getAttribute("viewTo");
  if(view!=null){
  ArrayList stockIdnList = (ArrayList)request.getAttribute("ToPktList");
  HashMap pktMap = (HashMap)session.getAttribute("PktHashMap");

  HashMap mprp = info.getMprp();
  if(stockIdnList!=null && stockIdnList.size()>0){
  int sr=0;
  ArrayList vwToPrpLst = (ArrayList)session.getAttribute(vwTMdl);
   %>
       <html:form action="mix/singleToMix.do?method=Trans" method="post" onsubmit="return validate_selection('radio','1','CHK_')" >
<input type="hidden" name="transmode" value="<%=trnsTyp%>" />


   <table><tr><td>
      <html:submit property="value(select)" styleClass="submit" value="Select Packet" /></td> 

   </tr>
   <tr><td><b>Packets List of To criteria (Select Mix packet)</b></td></tr>
   <tr><td>
    <table class="grid1">
   <tr> <th></th><th>Sr</th>
        <th>Packet No.</th>
        <th>Qty</th>
    <%for(int j=0; j < vwToPrpLst.size(); j++ ){
    String prp = (String)vwToPrpLst.get(j);
     String hdr = util.nvl((String)mprp.get(prp));
    String prpDsc = util.nvl((String)mprp.get(prp+"D"));
    if(hdr == null) {
        hdr = prp;
       }  
%>
<th title="<%=prpDsc%>"><%=prp%></th>
<%}%>       
</tr>
 <%
 
 for(int i=0; i < stockIdnList.size(); i++ ){
 String stkIdn = (String)stockIdnList.get(i);
 HashMap stockPkt = (HashMap)pktMap.get(stkIdn);
 String cts = (String)stockPkt.get("cts");
  String qty = (String)stockPkt.get("qty");

 sr++;
 String checkFldId = "CHK_"+sr;
 String checkFldVal = "value("+stkIdn+")";
 
 %>
<tr>
<td><input type="radio" name="toID" id="<%=checkFldId%>" value="<%=stkIdn%>" /> </td>
<td><%=sr%></td>
<td><%=stockPkt.get("vnm")%><input type="hidden" id="CTS_<%=stkIdn%>"  value="<%=cts%>" /></td>
<td><%=qty%></td>

<%for(int j=0; j < vwToPrpLst.size(); j++ ){
    String prp = (String)vwToPrpLst.get(j);
    %>
    <td><%=stockPkt.get(prp)%></td>
<%}%>
</tr>
   <%}%>
  </table></td></tr></table></html:form>
  <%}else{%>
     <input type="hidden" name="stt" value="<%=stt%>" id="stt"/>

  Sorry there are no packets in selected criteria. plz checked...
  No of packets need to create <input type="text" name="noPkt" size="2" id="noPkt" value="1" readonly="readonly" />
   <input type="button" class="submit" id="creatBtn" onclick="CreateNewPkt()" value="Create New Packets" />
 <% }}%>
  </td></tr>
  
  
    <tr>
  <td valign="top" class="tdLayout">
         <html:form action="mix/singleToMix.do?method=save" method="post" onsubmit="return confirmChanges()" >
<%  String fmstt="";
%>
  <table>
  <%
  String viewFM = (String)request.getAttribute("viewFm");
  if(viewFM!=null){
    HashMap mprp = info.getMprp();

  %>
    <tr><td>
   <%
   HashMap toPktDtl = (HashMap)session.getAttribute("ToPktDtl");
   if(toPktDtl!=null && toPktDtl.size()>0){
     ArrayList vwToPrpLst = (ArrayList)session.getAttribute(vwTMdl);
      String cts = (String)toPktDtl.get("cts");
      String qty = (String)toPktDtl.get("qty");
      String quot = (String)toPktDtl.get("quot");

      String stkIdn = (String)toPktDtl.get("stk_idn");
     String vnm = (String)toPktDtl.get("vnm");
   %>
   <input type="hidden" name="toPkt" value="<%=stkIdn%>" />
   <input type="hidden" name="stt" value="<%=stt%>" id="stt"/>

   <table class="grid1">
   <tr> 
        <th>Packet No.</th>
                  <th>Qty</th>
        <th>Cts</th>
        <th>Rate</th>
        <th>Final Qty</th>
        <th>Final Cts</th>
        <th>Final Rate</th>
    <%for(int j=0; j < vwToPrpLst.size(); j++ ){
    String prp = (String)vwToPrpLst.get(j);
     String hdr = util.nvl((String)mprp.get(prp));
    String prpDsc = util.nvl((String)mprp.get(prp+"D"));
    if(hdr == null) {
        hdr = prp;
       }  
%>
<th title="<%=prpDsc%>"><%=prp%></th>
<%}%>       
</tr>
 <tr>
 <td><%=vnm%></td>
 <td><label id="CUR_QTY"> <%=(String)toPktDtl.get("qty")%></label><input type="hidden" id="CU_QTY" name="CU_QTY" value="<%=(String)toPktDtl.get("qty")%>" size="8"  /> </td>
<td><label id="CUR_CTS"> <%=(String)toPktDtl.get("cts")%></label><input type="hidden" id="CU_CTS" name="CU_CTS" value="<%=(String)toPktDtl.get("cts")%>" size="8"  /> </td>
<td> <label id="CUR_RTE"><%=(String)toPktDtl.get("quot")%></label><input type="hidden" id="CU_RTE" name="CU_RTE" value="<%=(String)toPktDtl.get("quot")%>" size="8"  /> </td>
<td><html:text property="value(FNL_QTY)" readonly="true" size="8" value="<%=qty%>" styleId="FNL_QTY" name="singleToMixForm"/></td>
<td><html:text property="value(FNL_CTS)" readonly="true" size="8" value="<%=cts%>" styleId="FNL_CTS" name="singleToMixForm"/></td>
<td><html:text property="value(FNL_RTE)" readonly="true" size="8" value="<%=quot%>" styleId="FNL_RTE" name="singleToMixForm"/></td>

 <%for(int j=0; j < vwToPrpLst.size(); j++ ){
    String prp = (String)vwToPrpLst.get(j);
    %>
    <td><%=toPktDtl.get(prp)%></td>
<%}%>

 </tr>
   

  </table>
  <% }%>
   
   </td> </tr>
 <tr><td>
  
  <%
  ArrayList FrmPktList = (ArrayList)session.getAttribute("FrmPktList");
  if(FrmPktList!=null && FrmPktList.size()>0){
    int sr=0;
  ArrayList vwFmPrpLst = (ArrayList)session.getAttribute(vwFMdl);
      ArrayList stockIdnList = FrmPktList;
  HashMap pktMap = (HashMap)session.getAttribute("PktHashMap");
   stt = (String)request.getAttribute("stt");

   %>

   <table>
    <tr><td> &nbsp;&nbsp; </td></tr>
   <tr><td>
  
  <div id="TRANSBTN" style="float:left">        &nbsp;&nbsp;

      <html:submit property="value(transfer)"  styleClass="submit" styleId="transferBtn" value="Transfer" /></div>
   
  <div style="float:left">&nbsp;&nbsp;
  </div>
      </td> 

   </tr>
   <tr><td>
    <table class="grid1">
   <tr> <th><input type="checkbox" name="check" id="check" onclick="checkAllpage(this,'CHK_');SingleToMixCalculate()" /></th><th>Sr</th>
        <th>Packet No.</th>
          <th>Qty</th>
        <th>Cts</th>
        <th>Rate</th>
       
    <%for(int j=0; j < vwFmPrpLst.size(); j++ ){
    String prp = (String)vwFmPrpLst.get(j);
     String hdr = util.nvl((String)mprp.get(prp));
    String prpDsc = util.nvl((String)mprp.get(prp+"D"));
    if(hdr == null) {
        hdr = prp;
       }  
%>
<th title="<%=prpDsc%>"><%=prp%></th>
<%}%>       
</tr>
 <%
 
 for(int i=0; i < stockIdnList.size(); i++ ){
 String stkIdn = (String)stockIdnList.get(i);
 HashMap stockPkt = (HashMap)pktMap.get(stkIdn);
 String cts = (String)stockPkt.get("cts");
 String qty = (String)stockPkt.get("qty");
 String quot = (String)stockPkt.get("quot");
  fmstt = (String)stockPkt.get("stt");
      sr++;
 String checkFldId = "CHK_"+stkIdn;
 String checkFldVal = "value("+stkIdn+")";
 
 %>
<tr>
<td>
<input type="checkbox" name="<%=checkFldId%>" id="<%=checkFldId%>" onclick="SingleToMixCalculate()" value="<%=stkIdn%>" />
</td>
<td><%=sr%></td>
<td><%=stockPkt.get("vnm")%><input type="hidden" id="CTS_<%=stkIdn%>"  value="<%=cts%>" /></td>
<td><label id="CUR_QTY_<%=stkIdn%>"><%=(String)stockPkt.get("qty")%></label><input type="hidden" id="CU_QTY_<%=stkIdn%>" name="CU_QTY_<%=stkIdn%>" value="<%=(String)stockPkt.get("qty")%>" size="8"  /> </td>
<td><label id="CUR_CTS_<%=stkIdn%>"><%=(String)stockPkt.get("cts")%></label><input type="hidden" id="CU_CTS_<%=stkIdn%>" name="CU_CTS_<%=stkIdn%>" value="<%=(String)stockPkt.get("cts")%>" size="8"  /></td>
<td><label id="CUR_RTE_<%=stkIdn%>"><%=(String)stockPkt.get("quot")%></label><input type="hidden" id="CU_RTE_<%=stkIdn%>" name="CU_RTE_<%=stkIdn%>" value="<%=(String)stockPkt.get("quot")%>" size="8"  /></td>

<%for(int j=0; j < vwFmPrpLst.size(); j++ ){
    String prp = (String)vwFmPrpLst.get(j);
    %>
    <td><%=stockPkt.get(prp)%></td>
<%}%>
</tr>
   <%}%>
  </table></td></tr></table>

 <% }else{%>
 <div>
    Sorry there are no packets in single stones criteria. plz checked...</div>
    
 <%}}%></td></tr></table>
 <input type="hidden" value="<%=fmstt%>" name="fmStt" id="fmStt" />
 </html:form>
  </td></tr>
 <tr>
  <td valign="top" class="tdLayout">
  <%
  String viewFnl = (String)request.getAttribute("viewFNL");
  if(viewFnl!=null){
  
  ArrayList stkIdnList = (ArrayList)request.getAttribute("pktList");
  if(stkIdnList!=null && stkIdnList.size()>0){
    int sr=0;
  ArrayList vwToPrpLst = (ArrayList)session.getAttribute(vwTMdl);
    HashMap mprp = info.getMprp();
  HashMap pktMap = (HashMap)session.getAttribute("PktHashMap");
  %>
  <table class="grid1">
   <tr> 
        <th>Packet No.</th>
        <th>Qty</th>
        <th>Cts</th>
     
    <%for(int j=0; j < vwToPrpLst.size(); j++ ){
    String prp = (String)vwToPrpLst.get(j);
     String hdr = util.nvl((String)mprp.get(prp));
    String prpDsc = util.nvl((String)mprp.get(prp+"D"));
    if(hdr == null) {
        hdr = prp;
       }  
%>
<th title="<%=prpDsc%>"><%=prp%></th>
<%}%>       
</tr>
 <% for(int i=0; i < stkIdnList.size(); i++ ){
  String stkIdn = (String)stkIdnList.get(i);
 HashMap stockPkt = (HashMap)pktMap.get(stkIdn);
 String cts = (String)stockPkt.get("cts");
 String qty = (String)stockPkt.get("qty");
  String vnm = (String)stockPkt.get("vnm");

 %>
 <tr><td><%=vnm%></td><td><%=qty%></td><td><%=cts%></td>
<% for(int j=0; j < vwToPrpLst.size(); j++ ){
    String prp = (String)vwToPrpLst.get(j);
    %>
    <td><%=stockPkt.get(prp)%></td>
<%}%>
</tr>
 
  <% }%>
  </table>
  <%}}%>
</td></tr>
  <tr><td>
     <jsp:include page="/footer.jsp" />

  </td></tr>
 
  
   
   </table></body></html>