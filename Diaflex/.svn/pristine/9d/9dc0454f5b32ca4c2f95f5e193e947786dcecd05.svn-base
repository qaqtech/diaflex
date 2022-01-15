<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page errorPage="../error_page.jsp" %>
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import="java.util.ArrayList,java.util.HashMap,java.sql.ResultSet,ft.com.*, java.util.Date, java.util.ArrayList, ft.com.dao.*"%>
<jsp:useBean id="util" class="ft.com.JspUtil" scope="page" />
<jsp:useBean id="info" class="ft.com.InfoMgr" scope="session" />
<%@taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>
<%@taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
     <title>Price Changes</title>
   <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/bse.css?v=<%=info.getJsVersion()%>"/>
      <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/style.css?v=<%=info.getJsVersion()%>"/>
       <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/general.css?v=<%=info.getJsVersion()%>"/>
        <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/priceChange.js?v=<%=info.getJsVersion()%> " > </script>
        <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/ajax.js?v=<%=info.getJsVersion()%> " > </script>
        <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/memoScript.js?v=<%=info.getJsVersion()%> " > </script>
        <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/bse.js?v=<%=info.getJsVersion()%> " > </script>
                  <script src="<%=info.getReqUrl()%>/PopScripts/jquery-1.2.6.min.js" type="text/javascript"></script>

        <script type="text/javascript" src="<%=info.getReqUrl()%>/PopScripts/popup.js?v=<%=info.getJsVersion()%> " > </script>
   <link href="<%=info.getReqUrl()%>/calendar/calendar.css" rel="stylesheet" type="text/css" />
     

  </head>
  
  <%
  String reqUrl=info.getReqUrl();  %>
  
  <body onload="MM_preloadImages('images/faunalogo.png')" topmargin="0" leftmargin="0" rightmargin="0" bottommargin="0">
<input type="hidden" id="reqUrl" name="reqUrl" value="<%=info.getReqUrl()%>"/>
<div id="backgroundPopup"></div>
<div id="popupContactASSFNL">
<table class="popUpTb">
 <tr><td height="5%">&nbsp;&nbsp;&nbsp;<html:button property="value(close)" styleClass="submit"  onclick="MKEmptyPopup('frame');disablePopupASSFNL()" value="Close"  /> </td></tr>
<tr><td valign="top" height="95%">
<iframe  name="frame" class="frameStyle" align="middle" frameborder="0">

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
  <img src="../images/line.jpg" border="0" /></td><td valign="middle"> <span class="pgHed">Price Change</span> </td>
</tr></table>
  </td>
  </tr>
  <%
       
  if(request.getAttribute("msg")!=null){%>
  <tr><td valign="top" class="tdLayout"><span class="redLabel"><%=request.getAttribute("msg")%></span>
  </td>
  </tr>
  <%}%>
  <tr><td valign="top" class="tdLayout">
   <html:form action="/pricing/priceChanges.do?method=view"  method="POST">
      <html:hidden property="value(premiumLnk)" styleId="premiumLnk" name="priceChangesForm" />
        <table class="grid1">
        <tr><th colspan="2"> Search Pakets</th></tr>
         <tr>
        <td>Memo Id</td><td><html:text property="value(memoIdn)"/></td>
        </tr>
        <tr>
         <td>Packets. </td><td>
        <html:textarea property="value(vnmLst)"  />
        </td></tr>
        <tr><td colspan="2"><html:submit property="submit" value="View" styleClass="submit"/></td> </tr>
        </table>
        
    </html:form>
  </td></tr>
  <tr><td valign="top" class="tdLayout" height="20px">
  </td>
  </tr>
   <html:form action="/pricing/priceChanges.do?method=save"  method="POST">
 <html:hidden property="memoIdn" styleId="memoId" name="priceChangesForm" value="0" />
 <html:hidden property="value(premiumLnk)" styleId="premiumLnk" name="priceChangesForm" />
   <%
   String view = (String)request.getAttribute("view");
   if(view!=null){
   int count = 0;
    ArrayList pktList = (ArrayList)session.getAttribute("pktList");
    ArrayList prpDspBlocked = info.getPageblockList();
    if(pktList!=null && pktList.size()>0){
    ArrayList vwPrpLst = (ArrayList)session.getAttribute("memoPrpList");;
    HashMap mprp = info.getMprp();
      HashMap allPageDtl= (info.getPageDetails() == null)?new HashMap():(HashMap)info.getPageDetails();
      HashMap pageDtl=(HashMap)allPageDtl.get("MOD_PRICE_CHANGE");
      ArrayList pageList=new ArrayList();
      HashMap pageDtlMap=new HashMap();
      String fld_nme="",fld_ttl="",val_cond="",dflt_val="",fld_typ="",form_nme="",lov_qry="";
    
    %>
            <tr><td valign="top" class="tdLayout">
            <table>
            <tr><td></td><td>Value</td><td>Discount</td><td>Avg</td></tr>
            <tr><td>Old Value</td>
            <td><html:text property="vlu" styleId="old_vlu" size="15" name="priceChangesForm" readonly="true"/></td>
            <td><html:text property="avgDis" styleId="old_dis" size="15" name="priceChangesForm" readonly="true"/></td>
            <td><html:text property="avg" styleId="old_avg" size="15" name="priceChangesForm" readonly="true"/></td>
            
            </tr>
            <tr><td>New Value</td>
              <td><html:text property="vlu" styleId="new_vlu" size="15" name="priceChangesForm" readonly="true"/></td>
            <td><html:text property="avgDis" styleId="new_dis" size="15" name="priceChangesForm" readonly="true"/></td>
            <td><html:text property="avg" styleId="new_avg" size="15" name="priceChangesForm" readonly="true"/></td>
                </tr>
            </table>
            </td>
            </tr> 
            <tr><td valign="top" class="tdLayout">
            <table>
            <tr><td>Update Price By </td><td> 
        <html:select property="value(val)" styleId="typ_ALL"  name="priceChangesForm" >
        <%pageList=(ArrayList)pageDtl.get("OPTION");
      if(pageList!=null && pageList.size() >0){
      for(int j=0;j<pageList.size();j++){
      pageDtlMap=(HashMap)pageList.get(j);
      fld_ttl=(String)pageDtlMap.get("fld_ttl");
      val_cond=(String)pageDtlMap.get("val_cond");
      fld_typ=(String)pageDtlMap.get("fld_typ");
      dflt_val = (String)pageDtlMap.get("dflt_val");
      %>
    <html:option value="<%=dflt_val%>"><%=fld_ttl%></html:option>
<%}}%>
        <!--<html:option value="AVG_DIS">Avg Dis</html:option>
                <html:option value="PER_CRT_USD">Per Crt USD</html:option>
                <html:option value="PER_CRT_PCT">Per Crt %</html:option>
         <html:option value="PER_CRT_DIS">Per Crt Rap dis</html:option>
                <html:option value="PER_STONE">Per Stone</html:option>
        <html:option value="PER_STONE_DIS">Per Stone Dis</html:option>-->
        </html:select></td> <td><html:text property="value(diff)" styleId="chng_ALL" size="10" name="priceChangesForm"/> </td>
  <td><button type="button" class="submit" onClick="PriceChangeMemo('ALL')" >Verify </button>
</td>
 <td><html:submit property="value(submit)" styleClass="submit" onclick="return validatepriceChgPrimumRP()"  /></td>
  </tr>
            </table>
           </td> </tr>
     <!--<tr> <td valign="top" class="tdLayout">
     <table> <tr>
     <td align="left" >
     <label id="UpdatePrice" for="UpdatePrice">Update Price</label>    
     </td>
     
     <td valign="middle">
     <select id="updPriceSel"  name="updPriceSel">  
   
     <option value="Price" >Price</option>
     <option value="RapDis">RapDis</option>
     </select>
     </td>       
       
     <td valign="top">
     <input type="text"  value="" id="UpdPrice" name="UpdPrice" size="10" />      
     </td>   
    
    <td><input type="button" value="Verify"  onclick="verifyPrice(this);" class="submit" /></td>
     
     </tr>
     </table>
     </td>
     
    </tr>-->
 
   <tr><td valign="top" class="tdLayout">   
   
    <table  class="grid1" align="left" id="dataTable">
<tr>

<th><label title="Packet No">Packet No</label></th>
<th>Pricing Detail</th>
<%for(int j=0; j < vwPrpLst.size(); j++ ){
    String prp = (String)vwPrpLst.get(j);
    if(prpDspBlocked.contains(prp)){
}else{
     String hdr = (String)mprp.get(prp);
    String prpDsc = (String)mprp.get(prp+"D");
    if(hdr == null) {
        hdr = prp;
       }  
%>
<th title="<%=prpDsc%>"><%=hdr%></th>

<%}}%>
<th>Rap Rte</th>
<th> UPR</th>
<th> UPR_DIS</th>
<th>Update Price By</th>
<th>New UPR</th>
<th>New UPR_DIS</th>
<th>Rap Val</th>
<th>Value</th>
</tr>

<tbody>

<%

String tableTD="";
int colSpan = vwPrpLst.size()+4;
for(int m=0;m< pktList.size();m++){
count=m+1;
if(m%2==0){
tableTD="even";
}else{
tableTD="odd";
}
HashMap pktData = (HashMap)pktList.get(m);
String stkIdn = (String)pktData.get("stk_idn");
String rapRte = (String)pktData.get("rap_rte");
String pktUserPrs= (String)pktData.get("upr");
String pktuprDis=(String) pktData.get("uprDis");

String uprFld = "value(upr_"+stkIdn+")";
String uprDisFld = "value(uprDis_"+stkIdn+")";
String rapRteFldId = "rap_"+stkIdn;
String uprDisFldId = "uprDis_"+stkIdn;
String uprFldId = "upr_"+stkIdn;
String stkIdnflds = "stk_idn_"+stkIdn;
String pktUsrPrFlds ="uprf_"+stkIdn;
String pktuprDisFlds ="uprDisc_"+stkIdn;
String onChangeUpr = "CalculateRapDis("+stkIdn+")";
String onChangeUprDis = "CalculateRapRte("+stkIdn+")";

                    String typId = "typ_"+stkIdn;
                    String diffId = "chng_"+stkIdn;
                    String finalPriceId =stkIdn+"_fnl";
                    String finalDisId = stkIdn+"_fnl_dis";
                    String rapRteId = stkIdn+"_rap";
                    String rapDisId = stkIdn+"_dis";
                    String quotId = count+"_quot";
                    String ctsId = count+"_cts";
                    String newPriceIdINR = count+"_fnl$";
                    String rapValIdn = count+"_rapVal";
                    
                    String onChange = "PriceChangeMemo("+stkIdn+")";
                    String rapValId = "rapVal_"+stkIdn;
                    String newPriceId = "nwprice_"+stkIdn;
                    String valId = "val_"+stkIdn;
                    String newDisId = "nwdis_"+stkIdn;
                    String typVal ="value(typ_"+stkIdn+")";
                    String diff = "value(chng_"+stkIdn+")";
                    String newPrice = "value("+newPriceId+")";
                    String newDis = "value("+newDisId+")";
                    String rapVal = "value("+rapValId+")";
                    String valFld = "value("+valId+")";
                    String dis = "";
                    String vnm = (String)pktData.get("vnm");
                    String url = reqUrl+"/marketing/PacketLookup.do?method=view&typ=PRI&vnm="+vnm+"&idn="+stkIdn ;
                     String target = "TR_"+stkIdn;
                    String targetSrc = "SRC_"+stkIdn;
                    String sltarget = "SL_"+stkIdn;
                    String getPriceInfo = "getPriceInfo("+stkIdn+")";
%>

<tr class="<%=tableTD%>" id="<%=target%>">

<td><A href="<%=url%>" id="<%=sltarget%>"  target="frame" onclick="loadASSFNL('<%=target%>','<%=sltarget%>')"><span><%=pktData.get("vnm")%></span></a></td>
<td style="text-align:center"><img src="../images/plus.png" border="0" onclick="<%=getPriceInfo%>" /></td>
<%
 for(int l=0;l<vwPrpLst.size();l++){
   String prp = (String)vwPrpLst.get(l);
   if(prpDspBlocked.contains(prp)){
}else{
    String prpValue =  (String) pktData.get(prp);
    if(prpValue.equals("NA"))
     prpValue = "";
    String prpDsc = (String)mprp.get(prp+"D");
 
 %>
<td title="<%=prpDsc%>"><%=prpValue%></td>
<%}}%>    
<td title="Rap Rate"><%=rapRte%></td>
 <td title="user price"><%=pktData.get("upr")%>
<!-- <html:text property="<%=uprFld%>" size="7" styleId="<%=uprFldId%>" onchange="<%=onChangeUpr%>" />-->

 </td>
 <td title="user Dis"><%=pktData.get("uprDis")%>
<!--<html:text property="<%=uprDisFld%>" styleId="<%=uprDisFldId%>" size="7" onchange="<%=onChangeUprDis%>" />-->
        </td>
        <td> 
        <table><tr><td>
        <html:select property="<%=typVal%>" styleId="<%=typId%>" onchange="<%=onChange%>"  name="priceChangesForm" >
        <%pageList=(ArrayList)pageDtl.get("OPTION");
      if(pageList!=null && pageList.size() >0){
      for(int j=0;j<pageList.size();j++){
      pageDtlMap=(HashMap)pageList.get(j);
      fld_ttl=(String)pageDtlMap.get("fld_ttl");
      val_cond=(String)pageDtlMap.get("val_cond");
      fld_typ=(String)pageDtlMap.get("fld_typ");
      dflt_val = (String)pageDtlMap.get("dflt_val");%>
    <html:option value="<%=dflt_val%>"><%=fld_ttl%></html:option>
    <%}}%>
        <!--<html:option value="AVG_DIS" >Avg Dis</html:option>
        <html:option value="PER_CRT_USD">Per Crt USD</html:option>
                <html:option value="PER_CRT_PCT">Per Crt %</html:option>
         <html:option value="PER_CRT_DIS">Per Crt Rap dis</html:option>
                <html:option value="PER_STONE">Per Stone</html:option>
        <html:option value="PER_STONE_DIS">Per Stone Dis</html:option>-->
        </html:select></td>
        <td><html:text property="<%=diff%>" styleId="<%=diffId%>"  value="<%=dis%>" onchange="<%=onChange%>" size="10" name="priceChangeForm"/>
             </td></tr></table></td> 
              <td><html:text property="<%=newPrice%>" styleId="<%=newPriceId%>"  size="10" name="priceChangesForm"/></td>
              <td><html:text property="<%=newDis%>" styleId="<%=newDisId%>" size="10" name="priceChangesForm"/></td>
              <td><html:text property="<%=rapVal%>" styleId="<%=rapValId%>" size="10" name="priceChangesForm"/></td>
              <td><html:text property="<%=valFld%>" styleId="<%=valId%>" size="10" name="priceChangesForm"/></td>
</tr>
 <tr><td colspan="<%=colSpan+1%>" id="TDOffer_<%=stkIdn%>"   style="display:none" align="left">
 <span id="BYR_<%=stkIdn%>" > </span>
</td>
<%}%>      
</tbody>
</table>
 <input type="hidden" name="ttl_cnt" id="ttl_cnt" value="<%=count%>"/>
</td></tr>
    <%}}%>
   <tr>
    <td><jsp:include page="/footer.jsp" /> </td>
    </tr> 
  </html:form>
  </table></body>
</html>
