<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
 
<%@ page errorPage="../error_page.jsp" %>
<%@ page contentType="text/html;charset=windows-1252"%>
<jsp:useBean id="util" class="ft.com.JspUtil" scope="page" />
<jsp:useBean id="info" class="ft.com.InfoMgr" scope="session" />
<%@ page import="java.util.ArrayList,java.util.HashMap,java.sql.ResultSet,ft.com.*, java.util.Date, java.util.ArrayList, ft.com.dao.*"%>
<%@taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>Price Change</title>
   <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/bse.css?v=<%=info.getJsVersion()%>"/>
      <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/style.css?v=<%=info.getJsVersion()%>"/>
      <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/ajax.js?v=<%=info.getJsVersion()%> " > </script>
      <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/bse.js?v=<%=info.getJsVersion()%> " > </script>
      <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/memoScript.js?v=<%=info.getJsVersion()%> " > </script>
         <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/priceChange.js?v=<%=info.getJsVersion()%> " > </script>
          <script language="javaScript" type="text/javascript" src="<%=info.getReqUrl()%>/calendar/calendar.js"></script>
   <link href="<%=info.getReqUrl()%>/calendar/calendar.css" rel="stylesheet" type="text/css" />
   <script src="<%=info.getReqUrl()%>/PopScripts/jquery-1.2.6.min.js" type="text/javascript"></script>
   <script type="text/javascript" src="<%=info.getReqUrl()%>/PopScripts/popup.js?v=<%=info.getJsVersion()%> " > </script>
  </head>
  <%
   boolean isdisUpload = false;
     int count = 0;
     String pktDtl="";
      ArrayList prpDspBlocked = info.getPageblockList();
      HashMap allPageDtl= (info.getPageDetails() == null)?new HashMap():(HashMap)info.getPageDetails();
      HashMap pageDtl=(HashMap)allPageDtl.get("PRICE_CHANGE");
      ArrayList pageList=new ArrayList();
      HashMap pageDtlMap=new HashMap();
      String fld_nme="",fld_ttl="",val_cond="",dflt_val="",fld_typ="",form_nme="",lov_qry="";
        String logId=String.valueOf(info.getLogId());
        String onfocus="cook('"+logId+"')";
        %>
 <body onfocus="<%=onfocus%>">
 <div id="backgroundPopup"></div>
<div id="popupContactSave" align="center" >
<img src="../images/loading_flame.gif" height="150px" width="150px" border="0" />
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
  <img src="../images/line.jpg" border="0" /></td><td valign="middle"> <span class="pgHed">
   <logic:equal property="value(typ)"  value="BID" name="priceChangeForm" >
   Offer
   </logic:equal>
    <logic:notEqual property="value(typ)"  value="BID" name="priceChangeForm" >
  Memo Price Change
  </logic:notEqual>
  </span> </td>
</tr></table>
  </td>
  </tr>
  <%if(request.getAttribute("msg")!=null){%>
   <tr>
  <td valign="top" class="tdLayout">
  <span class="redLabel"><%=request.getAttribute("msg")%> </span>
  </td></tr>
  <%}%>
    <%
   String vnmNtFnd = (String)request.getAttribute("vnmNotFnd");
     if(vnmNtFnd!=null){%>
    <tr><td valign="top" class="tdLayout"><span class="redLabel"><%=vnmNtFnd%></span></td></tr>
   <%}%>
 <%
    HashMap dbInfoSys = info.getDmbsInfoLst();
   String cnt = (String)dbInfoSys.get("CNT");
 ArrayList msgList = (ArrayList)request.getAttribute("msgList");
 if(msgList!=null && msgList.size()>0){%>
 <tr>
  <td valign="top" class="tdLayout">
 <table cellpadding="0" cellspacing="0">
 <%for(int k=0 ; k<msgList.size(); k++){
 %>
  <tr><td>
  <span class="redLabel"><%=msgList.get(k)%> </span>
 </td></tr>
  <%}%></table></td></tr>
  <%}%>
   
   <tr>
  <td valign="top" class="tdLayout">
  <html:form action="/marketing/memoPrice.do?method=pktList" method="post" enctype="multipart/form-data"  >
  <html:hidden property="value(typ)" name="priceChangeForm" />
  <html:hidden property="value(premiumLnk)" styleId="premiumLnk" name="priceChangeForm" />
<table class="grid1" >

<tr><th colspan="2">Memo Search </th></tr>


<tr>
<td colspan="2"> <html:radio property="value(memoSrch)" value="ByrSrch" onclick="DisplayMemoSrch('MS_1')" styleId="MS_1" /> Memos Search By Buyer </td>
</tr>
<tr style="display:none" id="DMS_1"><td colspan="2">

<table cellpadding="5"><tr>
<td>Buyer</td>
<td>
<html:select property="value(byrIdn)" styleId="byrId" onchange="getTrms(this,'SALE')" >
<html:option value="0">---select---</html:option>
<html:optionsCollection property="byrLstFetch" value="byrIdn" label="byrNme" />

</html:select>
</td> </tr>
<tr>
<td> <span class="txtBold" >Terms </span></td><td>

<html:select property="value(byrTrm)" styleId="rlnId" onchange="GetTyppricechangeMemoRadioIdn()" >
<html:option value="0">---select---</html:option>
<html:optionsCollection property="trmsLst" name="priceChangeForm"
label="trmDtl" value="relId" />
</html:select>

</td>
</tr>
<tr>
<td>Memo Type</td>
<td>
<html:select property="typ" styleId="typId" name="priceChangeForm" onchange="GetTyppricechangeMemoRadioIdn()" >
<html:option value="ALL">ALL</html:option>
<html:optionsCollection property="memoList" name="priceChangeForm"
label="dsc" value="memo_typ" />
</html:select>
</td>
</tr>
<tr><td colspan="2"><div id="memoIdn"></div> </td></tr>
</table>

</td>
</tr>
<tr>
<td colspan="2"> <html:radio property="value(memoSrch)" value="MemoSrch" styleId="MS_2" onclick="DisplayMemoSrch('MS_2')" /> Memos Search By Memo Ids</td>
</tr>
<tr style="display:none" id="DMS_2">
<td>
<table>
<tr>
<td>Memo Ids</td><td><html:text property="memoIdn" name="priceChangeForm" styleId="memoId" onblur="return checkMemoBuyer()"/></td></tr>
<tr><td>Packet Id</td><td><html:textarea property="value(vnm)" styleId="vnm" name="priceChangeForm"   /> </td></tr>
   <%pageList=(ArrayList)pageDtl.get("UPLOADOFFER");
      if(pageList!=null && pageList.size() >0){
      for(int j=0;j<pageList.size();j++){
      pageDtlMap=(HashMap)pageList.get(j);
      fld_ttl=(String)pageDtlMap.get("fld_ttl");
      val_cond=(String)pageDtlMap.get("val_cond");
      fld_typ=(String)pageDtlMap.get("fld_typ");
      dflt_val = (String)pageDtlMap.get("dflt_val");%>
    <tr><td>Upload Offer</td><td>
   <html:file property="loadOffer" size="40" name="priceChangeForm"  onchange="check_file();" styleId="fileUpload" />
   </td></tr>
<%}}%>
</table>
</td>
</tr>
<tr><td colspan="2" align="center"><button type="submit" class="submit" onclick="return validatpriceChange();" name="verify" >Fetch</button>&nbsp;&nbsp; </td></tr>
</table>
  </html:form>
  </td></tr>
  <tr><td valign="top" class="tdLayout">
  <logic:present property="view" name="priceChangeForm" >
    <% 
    String cpstyle="";
    String cpdisstyle="";
    if(prpDspBlocked.contains("CP"))
    cpstyle="display:none";
    if(prpDspBlocked.contains("CP_DIS"))
    cpdisstyle="display:none";
    ArrayList pkts = (info.getValue("PKTS") == null)?new ArrayList():(ArrayList)info.getValue("PKTS"); 
    if(pkts.size()>0){
    String exhRte = (String)request.getAttribute("exhRte");%>
        <html:form action="/marketing/memoPrice.do?method=save" method="POST" onsubmit="return validate_prc('ch_')">
          <html:hidden property="value(typ)" name="priceChangeForm" />
          <html:hidden property="value(premiumLnk)" styleId="premiumLnk" name="priceChangeForm" />
        <table>
        <tr><td>
            <table class="grid1" >
                <tr>
                    <th>Buyer</th>
                    <th>Qty</th>
                    <th>Cts</th>
                    <th>Rap Value</th>
                    <th>Exchange Rate</th>
                <%if(cnt.equals("ri")){%>
                <th style="<%=cpstyle%>">CP</th>
                <th style="<%=cpdisstyle%>">CP Dis</th>
                <%}%>
                </tr>
                <tr>
                    <td><html:text property="byr"  name="priceChangeForm" readonly="true"/></td> 
                    <td><html:text property="qty" size="5" name="priceChangeForm" readonly="true"/></td> 
                    <td><html:text property="cts" styleId="glbl_cts" size="8" name="priceChangeForm" readonly="true"/></td> 
                    <td><html:text property="rapVlu" styleId="glbl_rap_vlu" size="8" name="priceChangeForm" readonly="true"/>
                     <html:hidden property="memoIdn" name="priceChangeForm" />
                    <html:hidden property="nmeIdn" name="priceChangeForm" />
                      <html:hidden property="relIdn" name="priceChangeForm" />
                    </td> 
                    <td><html:text property="value(exhRte)"  styleId="exhRte" name="priceChangeForm" readonly="true"/></td>
                <%if(cnt.equals("ri")){%>
                <td style="<%=cpstyle%>"><html:text property="value(cp)"  name="priceChangeForm" readonly="true"/></td> 
                <td style="<%=cpdisstyle%>"><html:text property="value(cpdis)"  name="priceChangeForm" readonly="true"/></td> 
                <%}%>
                </tr>
            </table></td></tr>
            
            <tr><td>
            <table>
            <tr><td></td><td>Value</td><td>Discount</td><td>Avg</td></tr>
            <tr><td>Memo Value</td>
            <td><html:text property="value(ttlOrgVlu)" styleId="memo_vlu" size="15" name="priceChangeForm" readonly="true"/></td>
            <td><html:text property="value(org_avg_dis)" styleId="memo_dis" size="15" name="priceChangeForm" readonly="true"/></td>
            <td><html:text property="value(avgOrg)" styleId="memo_avg" size="15" name="priceChangeForm" readonly="true"/></td>
            
            </tr>
            <tr><td>Old Value</td>
            <td><html:text property="vlu" styleId="old_vlu" size="15" name="priceChangeForm" readonly="true"/></td>
            <td><html:text property="avgDis" styleId="old_dis" size="15" name="priceChangeForm" readonly="true"/></td>
            <td><html:text property="avg" styleId="old_avg" size="15" name="priceChangeForm" readonly="true"/></td>
            
            </tr>
            <tr><td>New Value</td>
              <td><html:text property="vlu" styleId="new_vlu" size="15" name="priceChangeForm" readonly="true"/></td>
            <td><html:text property="avgDis" styleId="new_dis" size="15" name="priceChangeForm" readonly="true"/></td>
            <td><html:text property="avg" styleId="new_avg" size="15" name="priceChangeForm" readonly="true"/></td>
                </tr>
            </table>
            </td>
            </tr>
            <tr><td>
            <table>
            <tr><td>Update Price By </td><td> 
        <html:select property="value(val)" styleId="typ_ALL"  name="priceChangeForm" >
        <%pageList=(ArrayList)pageDtl.get("OPTION");
      if(pageList!=null && pageList.size() >0){
      for(int j=0;j<pageList.size();j++){
      pageDtlMap=(HashMap)pageList.get(j);
      fld_ttl=(String)pageDtlMap.get("fld_ttl");
      val_cond=(String)pageDtlMap.get("val_cond");
      fld_typ=(String)pageDtlMap.get("fld_typ");
      dflt_val = (String)pageDtlMap.get("dflt_val");
      if((dflt_val.equals("XRT") && !exhRte.equals("1")) || !dflt_val.equals("XRT")){
      %>
    <html:option value="<%=dflt_val%>"><%=fld_ttl%></html:option>
<%}}}%>
        <!--<html:option value="AVG_DIS">Avg Dis</html:option>
                <html:option value="PER_CRT_USD">Per Crt USD</html:option>
                <html:option value="PER_CRT_PCT">Per Crt %</html:option>
         <html:option value="PER_CRT_DIS">Per Crt Rap dis</html:option>
                <html:option value="PER_STONE">Per Stone</html:option>
        <html:option value="PER_STONE_DIS">Per Stone Dis</html:option>-->
        </html:select></td> <td><html:text property="value(diff)" styleId="chng_ALL" size="10" name="priceChangeForm"/> </td>
  <td><button type="button" class="submit" onClick="PriceChangeMemo('ALL')" >Verify </button>
</td>
  </tr>
            </table>
           </td> </tr>
           <tr><td><html:submit  value="Confirm" styleClass="submit" onclick="return validatepriceChgPrimum()" /> 
           
                      <%
pageList= ((ArrayList)pageDtl.get("BUTTON") == null)?new ArrayList():(ArrayList)pageDtl.get("BUTTON");             
            if(pageList!=null && pageList.size() >0){
            for(int j=0;j<pageList.size();j++){
            pageDtlMap=(HashMap)pageList.get(j);
            lov_qry=(String)pageDtlMap.get("lov_qry");
            if(lov_qry.equals(""))
            fld_nme="value("+(String)pageDtlMap.get("fld_nme")+")";
            else
            fld_nme=(String)pageDtlMap.get("fld_nme");
            fld_ttl=(String)pageDtlMap.get("fld_ttl");
            fld_typ=(String)pageDtlMap.get("fld_typ");
            dflt_val=(String)pageDtlMap.get("dflt_val");
            val_cond=(String)pageDtlMap.get("val_cond"); 
            if(fld_typ.equals("S")){
            %>
    &nbsp;<html:submit property="<%=fld_nme%>" value="<%=fld_ttl%>" onclick="<%=val_cond%>" styleClass="submit"/>&nbsp;
    <%}else if(fld_typ.equals("B")){%>
    &nbsp;<html:button property="<%=fld_nme%>" value="<%=fld_ttl%>" onclick="<%=val_cond%>" styleClass="submit"/>&nbsp;
    <%}}}
    %>
           
           </td></tr>
            <tr><td>
            <label class="pgTtl">Memo Packets :<bean:write property="memoIdnData" name="priceChangeForm" /></label>
            <%
                HashMap fileDateMap = (HashMap)request.getAttribute("fileDataLst");
                ArrayList prps = (session.getAttribute("PRICE_CHNG") == null)?new ArrayList():(ArrayList)session.getAttribute("PRICE_CHNG");
             %>  
            <table class="grid1">
                <tr>
                    <th>Sr</th>
                     <th>Check <input type="checkbox" name="chAll" id="chAll" onclick="checkedALL()" /> </th>
                    <th>Packet Code</th>
                    <%for(int j=0; j < prps.size(); j++) {
                     String lprp=(String)prps.get(j);
                     if(prpDspBlocked.contains(lprp)){
                     }else{
                     %>
                        <th><%=lprp%></th>
                    <%
                    if(lprp.equals("COL") || lprp.equals("CO")){%>
                    <th><html:select property="value(updatePrice)" styleId="updatePrice" onchange="setAllPktUpdatePriceBy('updatePrice')"  name="priceChangeForm" >
        <%pageList=(ArrayList)pageDtl.get("OPTION");
      if(pageList!=null && pageList.size() >0){
      for(int k=0;k<pageList.size();k++){
      pageDtlMap=(HashMap)pageList.get(k);
      fld_ttl=(String)pageDtlMap.get("fld_ttl");
      val_cond=(String)pageDtlMap.get("val_cond");
      fld_typ=(String)pageDtlMap.get("fld_typ");
      dflt_val = (String)pageDtlMap.get("dflt_val");
      if((dflt_val.equals("XRT") && !exhRte.equals("1")) || !dflt_val.equals("XRT")){%>
    <html:option value="<%=dflt_val%>"><%=fld_ttl%></html:option>
    <%}}}%>
        <!--<html:option value="AVG_DIS" >Avg Dis</html:option>
        <html:option value="PER_CRT_USD">Per Crt USD</html:option>
                <html:option value="PER_CRT_PCT">Per Crt %</html:option>
         <html:option value="PER_CRT_DIS">Per Crt Rap dis</html:option>
                <html:option value="PER_STONE">Per Stone</html:option>
        <html:option value="PER_STONE_DIS">Per Stone Dis</html:option>-->
        </html:select>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; Update Price By</th>
                    <th>New Price</th>
                    <th>New Discout</th>
                    <th>Rap Val</th>
                    <th>Value</th>
                    <%}}}%>
                    <th>RapRte</th>
                    <th> Rap Value</th>
                    <th>Dis</th>
                    <th>Prc / Crt </th>
                    <th>ByrDis</th>
                    <th>Quot</th>
                    <th> Quot USD</th>
                    <th> Value</th>
                    <th>memo Quot</th>
                     <th>Update By</th>
                     <th>Val</th>
                     <logic:equal property="value(typ)"  value="BID" name="priceChangeForm" >
                       <th>Valid Till</th>
                     </logic:equal>
                </tr>
            <%
                //(info.getValue(reqIdn+ "_PKTS") == null)?new ArrayList():(ArrayList)info.getValue(reqIdn+ "_PKTS");
             
              
                for(int i=0; i < pkts.size(); i++) {
                  PktDtl pkt = (PktDtl)pkts.get(i);
                %>
                <tr style="<%=util.nvl(pkt.getValue("color"))%>">
                <td><%=(i+1)%></td>
                <%    
                   count=i+1;
                  
                    long pktIdn = pkt.getPktIdn();
                    String typId = "typ_"+pktIdn;
                    String diffId = "chng_"+pktIdn;
                    String finalPriceId =pktIdn+"_fnl";
                    String finalDisId = pktIdn+"_fnl_dis";
                    String rapRteId = pktIdn+"_rap";
                    String rapDisId = pktIdn+"_dis";
                    String quotId = count+"_quot";
                    String ctsId = count+"_cts";
                    String newPriceIdINR = count+"_fnl$";
                    String rapValIdn = count+"_rapVal";
                    pktDtl =pktDtl+","+String.valueOf(pktIdn);
                    String onChange = "PriceChangeMemo("+pktIdn+")";
                    String rapValId = "rapVal_"+String.valueOf(pktIdn);
                    String newPriceId = "nwprice_"+String.valueOf(pktIdn);
                    String valId = "val_"+String.valueOf(pktIdn);
                    String newDisId = "nwdis_"+String.valueOf(pktIdn);
                    String typVal ="value(typ_"+String.valueOf(pktIdn)+")";  
                    String diff = "value(chng_"+String.valueOf(pktIdn)+")";
                    String newPrice = "value("+newPriceId+")";
                    String newDis = "value("+newDisId+")";
                    String rapVal = "value("+rapValId+")";
                    String valFld = "value("+valId+")";
                    String newPriceINR = "value(nwprice$_"+String.valueOf(pktIdn)+")";
                     String dteFld = "value(dte_"+String.valueOf(pktIdn)+")"; 
                    String dteFldID = "dte_"+String.valueOf(pktIdn);
                     String check = "value("+String.valueOf(pktIdn)+")";
                    String checkID = "ch_"+i;
                    String vnm = pkt.getVnm();
                    String prc = "";
                    String dis = "";
                    if(fileDateMap!=null){
                    HashMap fileDate = (HashMap)fileDateMap.get(vnm);
                    if(fileDate!=null){
                     dis = util.nvl((String)fileDate.get("prc"));
                     if(!(isdisUpload) && !dis.equals(""))
                       isdisUpload = true;
                    }}
                %>
                 <td><html:checkbox property="<%=check%>" styleId="<%=checkID%>" value="Yes" />
                 <input type="hidden" name="IDN_<%=count%>" id="IDN_<%=count%>" value="<%=pktIdn%>" />
                 </td>
                <td ><%=pkt.getVnm()%></td>
                 <%for(int j=0; j < prps.size(); j++) {
                  String lprp=(String)prps.get(j);
                  if(prpDspBlocked.contains(lprp)){
                     }else{%>
                    <td nowrap="nowrap"><%=util.nvl(pkt.getValue(lprp))%></td>
                <%
                if(lprp.equals("COL") || lprp.equals("CO")){%>
                <td> 
                <table><tr><td>
        <html:select property="<%=typVal%>" styleId="<%=typId%>" onchange="<%=onChange%>"  name="priceChangeForm" >
        <%pageList=(ArrayList)pageDtl.get("OPTION");
      if(pageList!=null && pageList.size() >0){
      for(int k=0;k<pageList.size();k++){
      pageDtlMap=(HashMap)pageList.get(k);
      fld_ttl=(String)pageDtlMap.get("fld_ttl");
      val_cond=(String)pageDtlMap.get("val_cond");
      fld_typ=(String)pageDtlMap.get("fld_typ");
      dflt_val = (String)pageDtlMap.get("dflt_val");
      if((dflt_val.equals("XRT") && !exhRte.equals("1")) || !dflt_val.equals("XRT")){%>
    <html:option value="<%=dflt_val%>"><%=fld_ttl%></html:option>
    <%}}}%>
        <!--<html:option value="AVG_DIS" >Avg Dis</html:option>
        <html:option value="PER_CRT_USD">Per Crt USD</html:option>
                <html:option value="PER_CRT_PCT">Per Crt %</html:option>
         <html:option value="PER_CRT_DIS">Per Crt Rap dis</html:option>
                <html:option value="PER_STONE">Per Stone</html:option>
        <html:option value="PER_STONE_DIS">Per Stone Dis</html:option>-->
        </html:select></td>
        <td><html:text property="<%=diff%>" styleId="<%=diffId%>" value="<%=dis%>" onchange="<%=onChange%>" size="10" name="priceChangeForm"/>
             </td></tr></table>   </td>
              <td><html:text property="<%=newPrice%>" styleId="<%=newPriceId%>" readonly="true"   size="10" name="priceChangeForm"/></td>
              <td><html:text property="<%=newDis%>" styleId="<%=newDisId%>" size="10" name="priceChangeForm"/></td>
              <td><html:text property="<%=rapVal%>" styleId="<%=rapValId%>" size="10" name="priceChangeForm"/></td>
              <td><html:text property="<%=valFld%>" styleId="<%=valId%>" size="10" name="priceChangeForm"/></td>
                <%}}}%>
                <td><%=pkt.getRapRte()%></td>
                <td><%=pkt.getValue("rapVal")%></td>
                <td><%=pkt.getDis()%></td>
                <td><%=pkt.getRte()%></td>
                <td><%=pkt.getByrDis()%></td>
                <td><%=pkt.getMemoQuot()%></td>
                <td><%=pkt.getValue("byrQuot")%></td>
                
                <td><%=pkt.getValue("val")%></td>
                 <td><%=pkt.getValue("oldQuot")%></td>
                  <td><%=pkt.getValue("updby")%></td>
                <td><%=pkt.getValue("oldRte")%></td>
             <logic:equal property="value(typ)"  value="BID" name="priceChangeForm" >
              <td> <div class="float">   <html:text property="<%=dteFld%>"  styleId="<%=dteFldID%>" /></div>
            <div class="float">     
                <img src="<%=info.getReqUrl()%>/images/calender.png" onClick="setYears(1900, <%=info.getCurrentYear()%>);showCalender(this, '<%=dteFldID%>');"></div> </td>
             </logic:equal>
              </tr>
              <%  
                }
            pktDtl =pktDtl.replaceFirst(",","");
            %>
              
            
            </table>
          <input type="hidden" name="ttl_cnt" id="ttl_cnt" value="<%=count%>"/>
          <input type="hidden" name="pktDtl" id="pktDtl" value="<%=pktDtl%>"/>
           </td></tr>
           
           </table>
        </html:form>
        <%}else{%>
     Sorry no result found
     <%}%>
        </logic:present>
    </td></tr>
    <tr>
    <td><jsp:include page="/footer.jsp" /> </td>
    </tr>
    </table>
      <%@include file="../calendar.jsp"%>
     <%if(isdisUpload){%>
<script type="text/javascript">
<!--
$(window).bind("load", function() {
calPRC();
});
-->
</script>  
     <%}%>
 </body>
</html>
