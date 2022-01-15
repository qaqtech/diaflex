<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page errorPage="../error_page.jsp" %>
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import="java.util.ArrayList,java.util.ArrayList,java.util.HashMap,java.sql.ResultSet,ft.com.*, java.util.Date, java.net.URLEncoder"%>
<jsp:useBean id="info" class="ft.com.InfoMgr" scope="session" />
<jsp:useBean id="util" class="ft.com.JspUtil" scope="page" />
<jsp:useBean id="mail" class="ft.com.GenMail" scope="page" />
<%@taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<%@taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>

<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/priceChange.js?v=<%=info.getJsVersion()%> " > </script>
    <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/ajax.js?v=<%=info.getJsVersion()%> " > </script>
     <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/bse.js?v=<%=info.getJsVersion()%> " > </script>
      <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/assortScript.js?v=<%=info.getJsVersion()%> " > </script>
      <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/pri.js?v=<%=info.getJsVersion()%> " > </script>
      <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/bse.css?v=<%=info.getJsVersion()%>"/>
      <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/general.css?v=<%=info.getJsVersion()%>"/>
      <link href="<%=info.getReqUrl()%>/calendar/calendar.css" rel="stylesheet" type="text/css" />
      <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/style.css?v=<%=info.getJsVersion()%>"/>
       <script type="text/javascript" src="<%=info.getReqUrl()%>/PopScripts/popup.js?v=<%=info.getJsVersion()%> " > </script>
<script src="<%=info.getReqUrl()%>/PopScripts/jquery-1.2.6.min.js" type="text/javascript"></script>
    <title>Purchase Action</title>
  </head>
        <%String logId=String.valueOf(info.getLogId());
        String onfocus="cook('"+logId+"')";
        %>
 <body onfocus="<%=onfocus%>" onload="MM_preloadImages('images/faunalogo.png')" topmargin="0" leftmargin="0" rightmargin="0" bottommargin="0">

<div id="backgroundPopup"></div>

<div id="popupContactSave" align="center" >
<img src="../images/loading_flame.gif" height="150px" width="150px" border="0" />
</div>
<table width="100%"  border="0" align="center" cellpadding="0" cellspacing="0" class="mainbg">
  <tr>
    <td height="103" valign="top">
   <jsp:include page="/header.jsp" /> </td> </tr>
  <tr>
    <td height="5" valign="top" class="greyline"><img src="<%=info.getReqUrl()%>/images/grey_linebig.png" width="685" height="5" align="right" /></td>
  </tr>
   <tr>
  <td valign="top" class="hedPg">
  <table cellpadding="1"  cellspacing="5"><tr><td valign="middle">
  <img src="../images/line.jpg" border="0" /></td><td valign="middle"> <span class="pgHed">Purchase Action</span> </td></tr></table>
  </td>
  </tr>
  <%
  String msg = (String)request.getAttribute("msg");
  String trf = (String)request.getAttribute("trf");
  String offer = (String)request.getAttribute("offer");
  String flg =util.nvl((String)request.getAttribute("flg"));
      HashMap allPageDtl= (info.getPageDetails() == null)?new HashMap():(HashMap)info.getPageDetails();
    HashMap pageDtl=(HashMap)allPageDtl.get("PUR_ACTION");
    ArrayList pageList=new ArrayList();
    HashMap pageDtlMap=new HashMap();
    String fld_nme="",fld_ttl="",val_cond="",dflt_val="",fld_typ="",form_nme="",flg1="",lov_qry="";
         String samepkt="N";
     pageList=(ArrayList)pageDtl.get("SAMEPKT");
     if(pageList!=null && pageList.size() >0){
         for(int k=0;k<pageList.size();k++){
         samepkt="Y";
     }}
  if(msg!=null){
  %>
  <tr>
  <td class="tdLayout" valign="top">
 <span class="redLabel">  <%=msg%></span>
  </td></tr><%}
    if(trf!=null){
  %>
  <tr>
  <td class="tdLayout" valign="top">
 <span class="redLabel">  <%=trf%></span>
  </td></tr><%}
     if(offer!=null){
  %>
  <tr>
  <td class="tdLayout" valign="top">
 <span class="redLabel">  <%=offer%></span>
  </td></tr><%}%>
   <tr>
  <td class="tdLayout" valign="top">
  <html:form action="purchase/purPrice.do?method=view" method="post" onsubmit="return validatePurPricing()">
  <table  class="grid1">
  <tr><th> </th><th>Generic Search 
  <%
  ArrayList rolenmLst=(ArrayList)info.getRoleLst();
  String usrFlg=util.nvl((String)info.getUsrFlg());
  if(usrFlg.equals("SYS") || rolenmLst.contains("ADMIN")){
  %>
  <img src="../images/edit.jpg" title="Edit Properties Module" onclick="newWindow('<%=info.getReqUrl()%>/dynamicProperty.do?method=load&mdl=PURACT_SRCH&sname=purGNCSrch&par=GENERIC')" border="0" width="15px" height="15px"/> 
  <%}%>
  </th></tr>
  <tr><td valign="top">
   <table> <tr>
                <td>Vendor</td>
                <td>
                <html:select property="value(vender)" styleId="venId" onchange="getPurTyp(this)" >
                <html:option value="0">---select---</html:option>
                <html:optionsCollection property="venderList"  name="purchasePriceForm"   value="byrIdn" label="byrNme" />
                
                </html:select>
                </td> </tr>
            <tr>
            <td> <span class="txtBold" >Type </span></td><td>
            <html:select property="value(typ)" styleId="typ"  onchange="getPurIdsTyp(this)" >
            <html:option value="0">---select---</html:option>
            <html:optionsCollection property="typList"  name="purchasePriceForm"   value="idn" label="nmeIdn" />
            </html:select>            
            </td>
            </tr>
           <tr>
            <td> <span class="txtBold" >Purchase Ids </span></td><td>
             
            <html:select property="value(purIdn)"  styleId="purIdn" >
            <html:option value="0">---select---</html:option>
            <html:optionsCollection property="purIdnList" name="purchasePriceForm"
            label="idn" value="nmeIdn" />
            </html:select>
            
            </td>
            </tr>
   <tr>
   <td>Ref No</td><td> <html:textarea property="value(refno)" rows="6"  cols="30" name="purchasePriceForm"  /></td>
   </tr>
  </table></td>
   <td valign="top">
    <jsp:include page="/genericSrch.jsp"/>
   </td></tr>
   <tr><td colspan="2" align="center"><html:submit property="value(srch)" value="Fetch" styleClass="submit" /></td> </tr>
   </table>
  </html:form>
  
  </td></tr>
  <html:form action="purchase/purPrice.do?method=save" method="post" onsubmit="loading()">
  <html:hidden property="memoIdn" styleId="memoId" name="purchasePriceForm" value="0" />
  <%String view = (String)request.getAttribute("view");
  if(view!=null){
  ArrayList pktList = (ArrayList)session.getAttribute("pktList");
  String type = (String)request.getAttribute("type");
  if(pktList!=null && pktList.size()>0){
   ArrayList itemHdr = new ArrayList();
   ArrayList vwPrpLst = (ArrayList)session.getAttribute("PurViewLst");
   HashMap mprp = info.getMprp();
   HashMap totals = (HashMap)request.getAttribute("totalMap");
   HashMap avgDtl = (HashMap)request.getAttribute("avgDtl");
   ArrayList prpDspBlocked = info.getPageblockList();
   itemHdr.add("vnm");
   int pktListsz=pktList.size();
  %>
  <tr>
  <td class="tdLayout" valign="top">
  <table>
   <tr><td>Total :&nbsp;&nbsp;</td><td><span id="ttlqty"> <%=totals.get("qty")%>&nbsp;&nbsp;</span></td><td>cts&nbsp;&nbsp;</td><td><span id="ttlcts"><%=totals.get("cts")%>&nbsp;&nbsp;</span></td><td>Selected:&nbsp;&nbsp;</td><td> Total :&nbsp;&nbsp;</td> <td><span id="qtyTtl">0&nbsp;&nbsp;</span></td><td>Cts&nbsp;&nbsp;</td><td><span id="ctsTtl">0</span> </td> </tr>
  
   </table>
  </td></tr>
   <tr>
  <td class="tdLayout" valign="top">
  <table><tr>
  <!--<td><html:submit property="value(cmptPri)" value="Compute Price" styleClass="submit" onclick="return confirmChangesSL()" /></td>
  <%if(type.equals("B") || type.equals("O")){%>
  <td><html:submit property="value(trfToStk)" value="Transfer To Stock" styleClass="submit" onclick="return confirmChangesSL()" /></td>
  <%}if(type.equals("R") || type.equals("O")){%>
  <td><html:submit property="value(offer)" value="Offer" styleClass="submit" onclick="return confirmChangesSL()" /></td>
  <%}
  if(type.equals("R")){%>
  <td><html:submit property="value(trfToBuy)" value="Transfer To Buy" styleClass="submit" onclick="return confirmChangesSL()" /></td>
  <%}%>-->
  <!--<td><html:submit property="value(cmptPri)" value="Compute Price" styleClass="submit" onclick="return confirmChangesSL()" /></td>
  <td><html:submit property="value(trfToStk)" value="Transfer To Stock" styleClass="submit" onclick="return confirmChangesSL()" /></td>
  <td><html:submit property="value(offer)" value="Offer" styleClass="submit" onclick="return confirmChangesSL()" /></td>
  <td><html:submit property="value(trfToBuy)" value="Transfer To Buy" styleClass="submit" onclick="return confirmChangesSL()" /></td>
  <td><html:button property="value(mail)" value="Mail Excel" onclick="purActionExmail('<%=pktListsz%>','mail')" styleClass="submit"/>&nbsp;</td>-->
      <%pageList=(ArrayList)pageDtl.get("BUTTON");
      if(pageList!=null && pageList.size() >0){
      for(int j=0;j<pageList.size();j++){
      pageDtlMap=(HashMap)pageList.get(j);
      fld_nme="value("+(String)pageDtlMap.get("fld_nme")+")";
      fld_ttl=(String)pageDtlMap.get("fld_ttl");
      val_cond=(String)pageDtlMap.get("val_cond");
      val_cond=val_cond.replaceAll("SIZE", String.valueOf(pktListsz));
      fld_typ=(String)pageDtlMap.get("fld_typ");
      flg1=(String)pageDtlMap.get("flg1");
      lov_qry=(String)pageDtlMap.get("fld_nme");
      if(fld_typ.equals("S")){
      %>
      <td><html:submit property="<%=fld_nme%>" value="<%=fld_ttl%>"  onclick="<%=val_cond%>" styleClass="submit" /></td>
      <%}else if(fld_typ.equals("B")){%>
      <td><html:button property="<%=fld_nme%>" value="<%=fld_ttl%>" onclick="<%=val_cond%>" styleClass="submit"/></td>
      <%}}}%>
  <%
  ArrayList rolenmLst=(ArrayList)info.getRoleLst();
  String usrFlg=util.nvl((String)info.getUsrFlg());
  if(usrFlg.equals("SYS") || rolenmLst.contains("ADMIN")){
  %>
  <td><img src="../images/edit.jpg" title="Edit Properties Module" onclick="newWindow('<%=info.getReqUrl()%>/dynamicProperty.do?method=load&mdl=PUR_VW&sname=PurViewLst&par=A')" border="0" width="15px" height="15px"/> </td>
  <%}%>
  <td>Create Excel <img src="../images/ico_file_excel.png" onclick="purActionExmail('<%=pktListsz%>','excel')" /></td>
  
  <%pageList=(ArrayList)pageDtl.get("STK_STT"+flg);
  if(pageList==null)
  pageList=(ArrayList)pageDtl.get("STK_STT");
  if(pageList!=null && pageList.size() >0){%>
  <td>Stock Stt</td>
  <td>
   <html:select property="value(stk_stt)" styleId="stk_stt"  name="purchasePriceForm" >
      <%for(int j=0;j<pageList.size();j++){
      pageDtlMap=(HashMap)pageList.get(j);
      fld_nme=(String)pageDtlMap.get("fld_nme");
      fld_ttl=(String)pageDtlMap.get("fld_ttl");%>
      <html:option value="<%=fld_nme%>"><%=fld_ttl%></html:option>
      <%}%>
  </html:select>
  </td>
  <%}%>
  </tr></table>
  </td></tr>
   <tr><td class="tdLayout" valign="top">
            <table>
            <tr><td></td><td>Value</td><td>Discount</td><td>Avg</td></tr>
            <tr><td><b>Purchase</b></td>
            <td><label><%=util.nvl((String)avgDtl.get("vlu"))%></label></td>
            <td><label><%=util.nvl((String)avgDtl.get("avg_dis"))%></label></td>
            <td><label><%=util.nvl((String)avgDtl.get("avg"))%></label></td>
            
            </tr>
            <tr><td><b>Cmp</b></td>
            <td><label><%=util.nvl((String)avgDtl.get("cmpvlu"))%></label></td>
            <td><label><%=util.nvl((String)avgDtl.get("cmpavg_dis"))%></label></td>
            <td><label><%=util.nvl((String)avgDtl.get("cmpavg"))%></label></td>
            
            </tr>
            <tr><td><b>New</b></td>
              <td><html:text property="vlu" styleId="new_vlu" size="15" name="purchasePriceForm" readonly="true"/></td>
            <td><html:text property="avgDis" styleId="new_dis" size="15" name="purchasePriceForm" readonly="true"/></td>
            <td><html:text property="avg" styleId="new_avg" size="15" name="purchasePriceForm" readonly="true"/></td>
                </tr>
            </table>
            </td>
            </tr>
            <tr><td class="tdLayout" valign="top">
            <table>
            <tr><td>Update Price By </td><td> 
        <html:select property="value(val)" styleId="typ_ALL"  name="purchasePriceForm" >
        <html:option value="AVG_DIS">Avg Dis</html:option>
	<html:option value="PER_CRT_USD">Per Crt USD</html:option>
	<html:option value="PER_CRT_PCT">Per Crt %</html:option>
         <html:option value="PER_CRT_DIS">Per Crt Rap dis</html:option>
	<html:option value="PER_STONE">Per Stone</html:option>
        <html:option value="PER_STONE_DIS">Per Stone Dis</html:option>
        </html:select></td> <td><html:text property="value(diff)" styleId="chng_ALL" size="10" name="purchasePriceForm"/> </td>
  <td><button type="button" class="submit" onClick="PriceChangeMemo('ALL')" >Verify </button>
</td>
  </tr>
            </table>
           </td> </tr>
  <tr>
  <td class="tdLayout" valign="top">
  
  <table class="grid1" align="left" id="dataTable">
<thead>
<tr>
<th><label title="SR NO">Sr No.</label></th>
<th><label title="CheckAll"><input  type="checkbox" id="all" name="all" onclick="ALLCheckBox('all','cb_prc_')" />Select </label></th>
<%if(samepkt.equals("Y")){%>
<th><label title="Similar">Similar</label></th>
<%}%>
<th>Ref No</th>
<%for(int j=0; j < vwPrpLst.size(); j++ ){
    String prp = (String)vwPrpLst.get(j);
    if(prpDspBlocked.contains(prp)){
    }else{
     String hdr = (String)mprp.get(prp);
    String prpDsc = (String)mprp.get(prp+"D");
    if(hdr == null) {
        hdr = prp;
       }  
    if(prp.equals("RTE")){%>
     <th title="<%=prpDsc%>">Rate</th>
     <th title="<%=prpDsc%>">Dis</th>
     <th title="">Vlu</th>
     <th title="">Currency Vlu</th>
     <%
     itemHdr.add("quot");itemHdr.add("dis");itemHdr.add("vlu");itemHdr.add("inrvlu");
     }else if(prp.equals("CP_DIS")){%>
     <th title="<%=prpDsc%>"><%=hdr%></th>
     <th title="Cp Vlu">Cp Vlu</th>
     <%
     itemHdr.add(prp);
     itemHdr.add("cpVlu");
     }else{
     itemHdr.add(prp);%>
<th title="<%=prpDsc%>"><%=hdr%></th>
<%}}}%>
<th>Update Price By</th>
 <th>New Price</th>
 <th>New Discout</th>
 <!--<th>Rap Val</th>
 <th>Value</th>-->
</tr>
<%
int loop=0;
for(int i=0;i<pktList.size();i++){
HashMap pktDtl = (HashMap)pktList.get(i);
String stkIdn = (String)pktDtl.get("stk_idn");
String cts = (String)pktDtl.get("cts");
String checkVal = "value("+stkIdn+")";
String checkId = "cb_prc_"+stkIdn;
String target = "SC_"+stkIdn;
String onchnge="ChangeFlg("+stkIdn+" , this , 'NO' );setBGColorOnCHK(this,'"+target+"')";
String typVal ="value(typ_"+String.valueOf(stkIdn)+")"; 
String typId = "typ_"+stkIdn;
String onChangepri = "PriceChangeMemo("+stkIdn+")";
String diff = "value(chng_"+String.valueOf(stkIdn)+")";
String diffId = "chng_"+stkIdn;
String dis = "";
String rapValId = "rapVal_"+String.valueOf(stkIdn);
String newPriceId = "nwprice_"+String.valueOf(stkIdn);
String valId = "val_"+String.valueOf(stkIdn);
String newDisId = "nwdis_"+String.valueOf(stkIdn);
String newPrice = "value("+newPriceId+")";
String newDis = "value("+newDisId+")";
String rapVal = "value("+rapValId+")";
String valFld = "value("+valId+")";
%>
<tr id="<%=target%>">

<td><%=i+1%><input type="hidden" id="CTS_<%=stkIdn%>"  value="<%=cts%>" /></td>
<td><html:checkbox property="<%=checkVal%>" styleId="<%=checkId%>"  onclick="<%=onchnge%>" name="purchasePriceForm" value="yes" /> </td>
<%if(samepkt.equals("Y")){%>
<td><a onclick="callSimilarPurchase('<%=stkIdn%>','<%=i%>')" title="Click Here To See Details" style="text-decoration:underline">Similar</a></td>
<%}%>
<td><%=util.nvl((String)pktDtl.get("vnm"))%></td>
<%for(int j=0; j < vwPrpLst.size(); j++ ){
    String prp = (String)vwPrpLst.get(j);
    if(prp.equals("QTY"))
    prp="qty";
    
    if(prpDspBlocked.contains(prp)){
}else{
     if(prp.equals("RTE")){%>
    <td align="right"><%=util.nvl((String)pktDtl.get("quot"))%></td>
     <td align="right"><%=util.nvl((String)pktDtl.get("dis"))%></td>
     <td align="right"><%=util.nvl((String)pktDtl.get("vlu"))%></td>
     <td align="right"><%=util.nvl((String)pktDtl.get("inrvlu"))%></td>
     <%}else if(prp.equals("CP_DIS")){%>
     <td align="right"><%=util.nvl((String)pktDtl.get(prp))%></td>
     <td align="right"><%=util.nvl((String)pktDtl.get("cpVlu"))%></td>
     <%}else{%>
  
    <td nowrap="nowrap"><%=util.nvl((String)pktDtl.get(prp))%></td>
   
    <%}%>


<%}}%>
<td> 
        <table><tr><td>
        <html:select property="<%=typVal%>" styleId="<%=typId%>" onchange="<%=onChangepri%>"  name="purchasePriceForm" >
        <html:option value="AVG_DIS" >Avg Dis</html:option>
        <html:option value="PER_CRT_USD">Per Crt USD</html:option>
	<html:option value="PER_CRT_PCT">Per Crt %</html:option>
         <html:option value="PER_CRT_DIS">Per Crt Rap dis</html:option>
	<html:option value="PER_STONE">Per Stone</html:option>
        <html:option value="PER_STONE_DIS">Per Stone Dis</html:option>
        </html:select></td>
        <td><html:text property="<%=diff%>" styleId="<%=diffId%>"  value="<%=dis%>" onchange="<%=onChangepri%>" size="10" name="purchasePriceForm"/>
        </td></tr></table>   </td>
        <td><html:text property="<%=newPrice%>" styleId="<%=newPriceId%>"  size="10" name="purchasePriceForm"/></td>
        <td><html:text property="<%=newDis%>" styleId="<%=newDisId%>" size="10" name="purchasePriceForm"/></td>
        <html:hidden property="<%=rapVal%>" styleId="<%=rapValId%>" name="purchasePriceForm"/>
        <html:hidden property="<%=valFld%>" styleId="<%=valId%>" name="purchasePriceForm"/>
        
</tr>
                  <tr id="BYRTRDIV_<%=i%>" style="display:none">
                      <td colspan="18"> 
                      <div id="BYR_<%=i%>"  align="center">
                      </div>
                      </td>
                 </tr>
<%
loop++;
}%>
<input type="hidden" id="count" value="<%=loop%>" />

</thead></table>


  </td></tr>
  <%session.setAttribute("itemHdr", itemHdr);
  } else{%>
   <tr><td class="tdLayout" valign="top">Sorry no result found </td></tr>
   <%}
  }%>
  </html:form>
  <tr>
  <td><jsp:include page="/footer.jsp" /> </td>
  </tr>
  </table>
  
  
  </body>
</html>