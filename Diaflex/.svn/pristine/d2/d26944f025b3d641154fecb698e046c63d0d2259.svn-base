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
    <title>Transfer  Packet</title>
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
        String url="transferPktAction.do?method=closePop&stt="+stt+"&transmode="+trnsTyp;
        String onclickBtn="MKEmptyPopup('SC');disablePopupASSFNL();windowOpen('"+url+"','_self');";
        String resetBtn = "windowOpen('transferPktAction.do?method=load&typ="+typ+"','_self')";
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
  <img src="../images/line.jpg" border="0" /></td><td valign="middle"> <span class="pgHed">Transfer To Packets </span> </td>
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
    <html:form action="/mix/transferPktAction.do?method=fetch" method="post" >
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
  <tr><td><html:textarea property="value(vnmFrm)" rows="2" cols="20" name="transferPktForm" /> </td>
  <td><html:textarea property="value(vnmTo)" rows="2" cols="20" name="transferPktForm" /></td> </tr>
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
  
   <html:select  property="<%=prpFld1%>" style="width:75px" name="transferPktForm"  styleId="<%=fld1%>"  > 
     
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
  String view = (String)request.getAttribute("viewFm");
  if(view!=null){
  ArrayList stockIdnList = (ArrayList)request.getAttribute("FrmPktList");
  HashMap pktMap = (HashMap)session.getAttribute("PktHashMap");
  String TOSrchId = util.nvl((String)request.getAttribute("TOSrchId"),"0");
    String vnmTo = util.nvl((String)request.getAttribute("vnmTo"));

  HashMap mprp = info.getMprp();
  if(stockIdnList!=null && stockIdnList.size()>0){
  int sr=0;
  ArrayList vwFmPrpLst = (ArrayList)session.getAttribute(vwFMdl);
   %>
       <html:form action="mix/transferPktAction.do?method=Trans" method="post" onsubmit="return validate_selection('radio','1','CHK_')" >
<input type="hidden" name="ToSrchID" value="<%=TOSrchId%>"/>
<input type="hidden" name="transmode" value="<%=trnsTyp%>" />
<input type="hidden" name="vnmTo" value="<%=vnmTo%>" />


   <table><tr><td>
      <html:submit property="value(select)" styleClass="submit" value="Select Packet" /></td> 

   </tr>
   <tr><td><b>Packets List of from criteria (select transfer packet)</b></td></tr>
   <tr><td>
    <table class="grid1">
   <tr> <th></th><th>Sr</th>
        <th>Packet No.</th>
        <th>Qty</th>
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

 sr++;
 String checkFldId = "CHK_"+sr;
 String checkFldVal = "value("+stkIdn+")";
 
 %>
<tr>
<td><input type="radio" name="frmID" id="<%=checkFldId%>" value="<%=stkIdn%>" /> </td>
<td><%=sr%></td>
<td><%=stockPkt.get("vnm")%><input type="hidden" id="CTS_<%=stkIdn%>"  value="<%=cts%>" /></td>
<td><%=qty%></td>

<%for(int j=0; j < vwFmPrpLst.size(); j++ ){
    String prp = (String)vwFmPrpLst.get(j);
    %>
    <td><%=stockPkt.get(prp)%></td>
<%}%>
</tr>
   <%}%>
  </table></td></tr></table></html:form>
  <%}else{%>
  Sorry there are no packets in selected criteria. plz checked...
 <% }}%>
  </td></tr>
  <tr>
  <td valign="top" class="tdLayout">
  <html:form action="mix/transferPktAction.do?method=save" method="post" onsubmit="return confirmChanges()" >
  <table>
  <%
  String viewTo = (String)request.getAttribute("viewTo");
  if(viewTo!=null){
    HashMap mprp = info.getMprp();

  %>
    <tr><td>
   <%
   HashMap FrmPktDtl = (HashMap)session.getAttribute("FrmPktDtl");
   if(FrmPktDtl!=null && FrmPktDtl.size()>0){
     ArrayList vwFrmPrpLst = (ArrayList)session.getAttribute(vwFMdl);
      String cts = (String)FrmPktDtl.get("cts");
      String qty = (String)FrmPktDtl.get("qty");
      String quot = (String)FrmPktDtl.get("quot");

      String stkIdn = (String)FrmPktDtl.get("stk_idn");
     String vnm = (String)FrmPktDtl.get("vnm");
   %>
   <input type="hidden" name="frmPkt" value="<%=stkIdn%>" />
   <input type="hidden" name="stt" value="<%=stt%>" id="stt"/>

   <table class="grid1">
   <tr> 
        <th>Packet No.</th>
                  <th>Qty</th>
        <th>Cts</th>
        <th>Rate</th>
        <th>Adj Qty</th>
        <th>Adj Cts</th>
        <th>Adj Rate</th>
        <th>Final Qty</th>
        <th>Final Cts</th>
        <th>Final Rate</th>
    <%for(int j=0; j < vwFrmPrpLst.size(); j++ ){
    String prp = (String)vwFrmPrpLst.get(j);
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
 <td><label id="CUR_QTY"> <%=(String)FrmPktDtl.get("qty")%></label><input type="hidden" id="CU_QTY" name="CU_QTY" value="<%=(String)FrmPktDtl.get("qty")%>" size="8"  /> </td>
<td><label id="CUR_CTS"> <%=(String)FrmPktDtl.get("cts")%></label><input type="hidden" id="CU_CTS" name="CU_CTS" value="<%=(String)FrmPktDtl.get("cts")%>" size="8"  /> </td>
<td> <label id="CUR_RTE"><%=(String)FrmPktDtl.get("quot")%></label><input type="hidden" id="CU_RTE" name="CU_RTE" value="<%=(String)FrmPktDtl.get("quot")%>" size="8"  /> </td>
<td><html:text property="value(ADJ_QTY)" size="8"  onchange="addAdjVal('QTY')" styleId="TRF_QTY" name="transferPktForm"/> </td>
<td><html:text property="value(ADJ_CTS)" size="8" onchange="addAdjVal('CTS')"  styleId="TRF_CTS" name="transferPktForm"/></td>
<td><html:text property="value(ADJ_RTE)" size="8"  onchange="addAdjVal('RTE')" styleId="TRF_RTE" name="transferPktForm"/></td>
<td><html:text property="value(FNL_QTY)" readonly="true" size="8" value="<%=qty%>" styleId="FNL_QTY" name="transferPktForm"/></td>
<td><html:text property="value(FNL_CTS)" readonly="true" size="8" value="<%=cts%>" styleId="FNL_CTS" name="transferPktForm"/></td>
<td><html:text property="value(FNL_RTE)" readonly="true" size="8" value="<%=quot%>" styleId="FNL_RTE" name="transferPktForm"/></td>

 <%for(int j=0; j < vwFrmPrpLst.size(); j++ ){
    String prp = (String)vwFrmPrpLst.get(j);
    %>
    <td><%=FrmPktDtl.get(prp)%></td>
<%}%>

 </tr>
   

  </table>
  <% }%>
   
   </td> </tr>
 <tr><td>
  
  <%
  ArrayList ToPktList = (ArrayList)session.getAttribute("ToPktList");
  if(ToPktList!=null && ToPktList.size()>0){
    int sr=0;
  ArrayList vwToPrpLst = (ArrayList)session.getAttribute(vwTMdl);
      ArrayList stockIdnList = (ArrayList)session.getAttribute("ToPktList");
  HashMap pktMap = (HashMap)session.getAttribute("PktHashMap");
   stt = (String)request.getAttribute("stt");


   %>
       

   <table>
    <tr><td> &nbsp;&nbsp; </td></tr>
   <tr><td>
    <div id="VRFYBTN" style="float:left">      &nbsp;&nbsp;
  <input type="button" class="submit" onclick="TRFPktCalculate()" value="Verify" /></div>

  <div id="TRANSBTN" style="display:none;float:left">        &nbsp;&nbsp;

      <html:submit property="value(transfer)"  styleClass="submit" styleId="transferBtn" value="Transfer" /></div>
   
  <div style="float:left">&nbsp;&nbsp;No of packets need to create <input type="text" name="noPkt" size="2" id="noPkt" value="1"  />
   <input type="button" class="submit" id="creatBtn" onclick="CreateNewPkt()" value="Create New Packets" />
  </div>
      </td> 

   </tr>
   <tr><td>
    <table class="grid1">
   <tr> <th><input type="checkbox" name="check" id="check" onclick="checkAllpage(this,'CHK_')" /></th><th>Sr</th>
        <th>Packet No.</th>
          <th>Qty</th>
        <th>Cts</th>
        <th>Rate</th>
        <th>Trf Qty</th>
        <th>Trf Cts</th>
        <th>Trf Rate</th>
        <th>Final Qty</th>
        <th>Final Cts</th>
        <th>Final Rate</th>
        <th><input type="checkbox" name="checkAll" id="checkAll" onclick="checkAllpage(this,'APP_')" /> &nbsp; Apply As Sale</th>

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
 String quot = (String)stockPkt.get("quot");

      sr++;
 String checkFldId = "CHK_"+stkIdn;
 String checkFldVal = "value("+stkIdn+")";
 
 %>
<tr>
<td><html:checkbox property="<%=checkFldVal%>" styleId="<%=checkFldId%>" value="<%=stkIdn%>" name="transferPktForm"/> </td>
<td><%=sr%></td>
<td><%=stockPkt.get("vnm")%><input type="hidden" id="CTS_<%=stkIdn%>"  value="<%=cts%>" /></td>
<td><label id="CUR_QTY_<%=stkIdn%>"><%=(String)stockPkt.get("qty")%></label><input type="hidden" id="CU_QTY_<%=stkIdn%>" name="CU_QTY_<%=stkIdn%>" value="<%=(String)stockPkt.get("qty")%>" size="8"  /> </td>
<td><label id="CUR_CTS_<%=stkIdn%>"><%=(String)stockPkt.get("cts")%></label><input type="hidden" id="CU_CTS_<%=stkIdn%>" name="CU_CTS_<%=stkIdn%>" value="<%=(String)stockPkt.get("cts")%>" size="8"  /></td>
<td><label id="CUR_RTE_<%=stkIdn%>"><%=(String)stockPkt.get("quot")%></label><input type="hidden" id="CU_RTE_<%=stkIdn%>" name="CU_RTE_<%=stkIdn%>" value="<%=(String)stockPkt.get("quot")%>" size="8"  /></td>
<td><input type="text" id="TRF_QTY_<%=stkIdn%>" name="TRF_QTY_<%=stkIdn%>" size="8"  /></td>
<td><input type="text" id="TRF_CTS_<%=stkIdn%>" name="TRF_CTS_<%=stkIdn%>" size="8"  /></td>
<td><input type="text" id="TRF_RTE_<%=stkIdn%>" name="TRF_RTE_<%=stkIdn%>" size="8" /></td>
<td><input type="text" id="FNL_QTY_<%=stkIdn%>" readonly="true" name="FNL_QTY_<%=stkIdn%>" size="8" value="<%=qty%>" /></td>
<td><input type="text" id="FNL_CTS_<%=stkIdn%>" readonly="true" name="FNL_CTS_<%=stkIdn%>" size="8" value="<%=cts%>" /></td>
<td><input type="text" id="FNL_RTE_<%=stkIdn%>" readonly="true" name="FNL_RTE_<%=stkIdn%>" size="8" value="<%=quot%>" /></td>
<td><input type="checkbox" name="APP_<%=stkIdn%>" id="APP_<%=stkIdn%>" value="Yes"/> </td>

<%for(int j=0; j < vwToPrpLst.size(); j++ ){
    String prp = (String)vwToPrpLst.get(j);
    %>
    <td><%=stockPkt.get(prp)%></td>
<%}%>
</tr>
   <%}%>
  </table></td></tr></table>

 <% }else{%>
 <div>
    Sorry there are no packets in Trnasfer criteria. plz checked...</div>
      <div>&nbsp;&nbsp; <input type="text" name="noPkt" size="5" id="noPkt" value="5"  />
   <input type="button" class="submit" id="creatBtn" onclick="CreateNewPkt()" value="Create New Packets" />
  </div>
  
 <%}}%></td></tr></table></html:form>
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
    </table>
   </body>
</html>