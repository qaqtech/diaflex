<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page errorPage="../error_page.jsp" %>
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import="java.util.ArrayList,java.util.HashMap,java.sql.ResultSet,ft.com.*, java.util.Date, java.util.ArrayList, ft.com.dao.*"%>
<jsp:useBean id="info" class="ft.com.InfoMgr" scope="session" />
<jsp:useBean id="util" class="ft.com.JspUtil" scope="page" />
<%@taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>
<%@taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>


<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>Transfer to Marketing</title>
    <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/bse.css?v=<%=info.getJsVersion()%>"/>
    <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/style.css?v=<%=info.getJsVersion()%>"/>
    <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/general.css?v=<%=info.getJsVersion()%>"/>
      <script src="<%=info.getReqUrl()%>/scripts/memoScript.js?v=<%=info.getJsVersion()%>" type="text/javascript"></script>
       <script src="<%=info.getReqUrl()%>/scripts/bse.js?v=<%=info.getJsVersion()%>" type="text/javascript"></script>
       <script src="<%=info.getReqUrl()%>/scripts/ajax.js?v=<%=info.getJsVersion()%>" type="text/javascript"></script>
       <script src="<%=info.getReqUrl()%>/scripts/priceChange.js?v=<%=info.getJsVersion()%>" type="text/javascript"></script>
        <script src="<%=info.getReqUrl()%>/scripts/assortScript.js?v=<%=info.getJsVersion()%>" type="text/javascript"></script>
           <script type="text/javascript" src="<%=info.getReqUrl()%>/PopScripts/popup.js?v=<%=info.getJsVersion()%> " > </script>
   <script src="<%=info.getReqUrl()%>/PopScripts/jquery-1.2.6.min.js" type="text/javascript"></script>
 <script src="<%=info.getReqUrl()%>/scripts/Validation.js" type="text/javascript"></script>
 
 <script type="text/javascript">
$(window).load(function(){
$("input").keydown(function (e) {
    if (e.keyCode === 9){
     var id=$(this).attr("id");
     if(id.indexOf('modDis_')!=-1){
       var splitLst = id.split("_");
       var stkIdn = splitLst[1];
       var nextIdn =$("#next_"+stkIdn).val();
       var objId = "modDis_"+nextIdn;
       var  obj = document.getElementById(objId);
       var x = window.scrollX, y = window.scrollY;
      setTimeout(function () {
           obj.focus();
       })
        setTimeout(function() { window.scrollTo(x, y); }, 100);
       
     }
    }
});});
</script>
  </head>
<%
ArrayList imagelistDtl= (info.getImagelistDtl() == null)?new ArrayList():(ArrayList)info.getImagelistDtl();
String form1Url = info.getReqUrl()+"/Inward/transferMktSH.do?method=view";
String foem2Url = info.getReqUrl()+"/Inward/transferMktSH.do?method=transMkt";
HashMap dbInfoSys = info.getDmbsInfoLst();
  String gialink = util.nvl((String)dbInfoSys.get("GIA_LINK"));
  String igilink = util.nvl((String)dbInfoSys.get("IGI_LINK"));
  String hrdlink = util.nvl((String)dbInfoSys.get("HRD_LINK"));
        String logId=String.valueOf(info.getLogId());
        String onfocus="cook('"+logId+"')";
        %>
 <body onfocus="<%=onfocus%>" onload="MM_preloadImages('images/faunalogo.png')" topmargin="0" leftmargin="0" rightmargin="0" bottommargin="0">
 <input type="hidden" id="reqUrl" name="reqUrl" value="<%=info.getReqUrl()%>"/>
 <div id="backgroundPopup"></div>
<div id="popupContactASSFNL">
<table class="popUpTb">
<tr>
<td height="5%" id="close">&nbsp;&nbsp;&nbsp;<html:button property="value(close)" styleClass="submit"  onclick="MKEmptyPopup('frame');disablePopupASSFNL();" value="Close"  /> </td>
<td height="5%" id="closereload" style="display:none">&nbsp;&nbsp;&nbsp;<html:button property="value(close)" styleClass="submit"  onclick="MKEmptyPopup('frame');disablePopupASSFNL();reportUploadclose('TRF')" value="Close"  /> </td>
</tr>
<tr><td valign="top" height="95%">
<iframe name="frame" class="frameStyle"   align="middle" frameborder="0">

</iframe></td></tr>
</table>
</div>

 <%      
      HashMap allPageDtl= (info.getPageDetails() == null)?new HashMap():(HashMap)info.getPageDetails();
      HashMap pageDtl=(HashMap)allPageDtl.get("Trans_To_MktSH");
      ArrayList pageList=new ArrayList();
      HashMap pageDtlMap=new HashMap();
      String fld_nme="",fld_ttl="",val_cond="",dflt_val="",fld_typ="",form_nme="",lov_qry="";%>
<table width="100%"  border="0" align="center" cellpadding="0" cellspacing="0" class="mainbg">
  <tr>
    <td height="103" valign="top">
   <jsp:include page="/header.jsp" />
    
    </td>
  </tr>
 
  <tr>
    <td height="5" valign="top"  class="greyline"><img src="../images/grey_linebig.png" width="685" height="5" align="right" /></td>
  </tr>
  <tr>
  <td valign="top" class="hedPg">
  <table cellpadding="1" cellspacing="5"><tr><td valign="middle">
  <img src="../images/line.jpg" border="0" /></td><td valign="middle"> <span class="pgHed">Transfer To Marketing </span> </td>
</tr></table>
  </td>
  </tr>
   <%
   String vnmNtFnd = (String)request.getAttribute("vnmNotFnd");
     if(vnmNtFnd!=null){%>
    <tr><td valign="top" class="tdLayout"><span class="redLabel"><%=vnmNtFnd%></span></td></tr>
   <%}%>
   <% String msg = (String)request.getAttribute("msg");
  if(msg!=null){
  %>
  <tr><td valign="top" class="tdLayout"><span class="redLabel"> <%=msg%></span> </td></tr>
  <%}%>
   <% String errormsg = (String)request.getAttribute("errormsg");
  if(errormsg!=null){
  %>
  <tr><td valign="top" class="tdLayout"><span class="redLabel"> <%=errormsg%></span> </td></tr>
  <%}%>
  <tr><td valign="top" class="tdLayout">
  <html:form action="Inward/transferMktSH.do?method=view" method="post">
   <table  class="grid1">
  <tr><th>Generic Search 
  <%
  ArrayList rolenmLst=(ArrayList)info.getRoleLst();
  String usrFlg=util.nvl((String)info.getUsrFlg());
  if(usrFlg.equals("SYS") || rolenmLst.contains("ADMIN")){
  %>
  <img src="../images/edit.jpg" title="Edit Properties Module" onclick="newWindow('<%=info.getReqUrl()%>/dynamicProperty.do?method=load&mdl=TFMKT_SRCH&sname=TFMKTSrch&par=GENERIC')" border="0" width="15px" height="15px"/> 
  <%}%>
  </th><th> Packets</th></tr>
  <tr><td>
  <table>
  
  
  <%
  pageList= ((ArrayList)pageDtl.get("STATUS") == null)?new ArrayList():(ArrayList)pageDtl.get("STATUS");
         if(pageList!=null && pageList.size() >0){
           for(int j=0;j<pageList.size();j++){
             pageDtlMap=(HashMap)pageList.get(j);
             fld_nme="value("+(String)pageDtlMap.get("fld_nme")+")";
             fld_ttl=(String)pageDtlMap.get("fld_ttl");
             val_cond=(String)pageDtlMap.get("val_cond"); %>
            <tr><td colspan="2"><table><tr><td><%=fld_ttl%> </td>
      <%}}%>
           
            <%
              pageList= ((ArrayList)pageDtl.get("SELECT") == null)?new ArrayList():(ArrayList)pageDtl.get("SELECT");
             if(pageList!=null && pageList.size() >0){%>
             <td>
             <html:select property="value(Stt)" styleId="Stt" name="transferToMktSHForm" >
             <%
               for(int i=0;i<pageList.size();i++){
                 pageDtlMap=(HashMap)pageList.get(i);
                 fld_nme="value("+(String)pageDtlMap.get("fld_nme")+")";
                 fld_ttl=(String)pageDtlMap.get("fld_ttl");
                 val_cond=(String)pageDtlMap.get("val_cond");
                 dflt_val=(String)pageDtlMap.get("dflt_val");
                 %>
                <html:option value="<%=dflt_val%>"><%=fld_ttl%></html:option>
             <%}%>
            </html:select>
            </td>
            </tr>
            </table></td></tr>
            <%} 
         pageList= ((ArrayList)pageDtl.get("DATE") == null)?new ArrayList():(ArrayList)pageDtl.get("DATE");
         if(pageList!=null && pageList.size() >0){%>
         <tr><td colspan="2"><table>
         <%
           for(int j=0;j<pageList.size();j++){
             pageDtlMap=(HashMap)pageList.get(j);
             fld_nme=util.nvl((String)pageDtlMap.get("fld_nme"));             
             fld_ttl=util.nvl((String)pageDtlMap.get("fld_ttl"));
             val_cond=util.nvl((String)pageDtlMap.get("val_cond")); }
           if(fld_nme.equals("yes"))  {%>
      <tr><td>Date </td>  
       <td>From</td><td> <html:text property="value(frmdte)" styleId="frmdte" name="transferToMktSHForm" size="8" /><img src="<%=info.getReqUrl()%>/images/calender.png" onClick="setYears(2011, <%=info.getCurrentYear()%>);showCalender(this, 'frmdte');"> </td>
       <td>To</td><td><html:text property="value(todte)" styleId="todte" name="transferToMktSHForm"  size="8"/> <img src="<%=info.getReqUrl()%>/images/calender.png" onClick="setYears(2011, <%=info.getCurrentYear()%>);showCalender(this, 'todte');"></td>
      </tr>
  <% }%>   
  </table></td>
  </tr>
  <%}%>
 
 <!-- <tr>
     <td align="left">Sequence No 
    <html:text property="value(seq)" name="transferToMktSHForm" />
     </td>
    </tr>-->
  <tr>
  <td>
   <jsp:include page="/genericSrch.jsp"/></td></tr></table>
  </td>
  
  <td valign="top">
  <table>
  <tr>
  <td> <html:radio property="value(srchRef)"  styleId="vnm" value="vnm" /> Packet Code.
      <html:radio property="value(srchRef)"  styleId="vnm" value="cert_no" /> Cert No. </td>
  </tr>
  <tr>
  <td>
  <html:textarea property="value(vnmLst)" rows="13" name="transferToMktSHForm" styleId="vnmLst" onkeyup="getVnmCount('vnmLst','vnmLstCnt')" onchange="getVnmCount('vnmLst','vnmLstCnt')" /><br><span id="vnmLstCnt">0</span>
  </td>
  </tr>
  </table>
  </td>
  
  </tr>
  <tr><td  colspan="2" align="center"><html:submit property="view" value="View" styleClass="submit"/>
   <html:submit property="viewAll" value="View All" styleClass="submit"/>
   <!--<input type="button" name="packetLookupForm" value="Reset" onclick="reset()" class="submit" />-->
<input type="button" name="Reset" value="Reset" onclick="goTo('transferMktSH.do?method=load','', '');" class="submit">&nbsp;
   </td>
  </tr>
  </table>
  </html:form>
  </td></tr>
 
 <% 
    String view = (String)request.getAttribute("view");
    if(view!=null){
     HashMap pktList= (session.getAttribute("pktList") == null)?new HashMap():(HashMap)session.getAttribute("pktList");
    ArrayList pktStkIdnList = (ArrayList)request.getAttribute("pktStkIdnList");
    if(pktList!=null && pktList.size()>0){
     HashMap totals = (HashMap)request.getAttribute("totalMap");
     String cnt = (String)dbInfoSys.get("CNT");
      String openPopUp = "openPopValid('StockPrpUpd.do?method=Openpop&mdl=AS_PRC_EDIT','frame','multiprp','cb_prc_')";
    %>
    <tr><td valign="top" class="tdLayout">
   <table>
   <tr><td>Total :&nbsp;&nbsp;</td>
   <td><span id="ttlqty"> <%=totals.get("qty")%>&nbsp;&nbsp;</span></td>
   <td>cts&nbsp;&nbsp;</td><td><span id="ttlcts"><%=totals.get("cts")%>&nbsp;&nbsp;</span></td>
   <td>Amount&nbsp;&nbsp;</td><td><span id="ttlvlu"><%=totals.get("vlu")%>&nbsp;&nbsp;</span></td>
   <td>Selected:&nbsp;&nbsp;</td><td> Total :&nbsp;&nbsp;</td>
   <td><span id="qtyTtl">0&nbsp;&nbsp;</span></td><td>Cts&nbsp;&nbsp;</td>
   <td><span id="ctsTtl">0</span> </td><td>Amount&nbsp;&nbsp;</td> <td><span id="vluTtl">0</span> </td> </tr>
  
   </table>
   </td></tr>
    
   <html:form action="Inward/transferMktSH.do?method=transMkt" method="post" onsubmit="return validate_prc('cb_prc_')"  >
  <input type="hidden" id="cnt" name="cnt" value="<%=cnt%>">
  <tr><td valign="top" class="tdLayout">
  <table cellpadding="2" cellspacing="2">
  <tr>
    <td><html:submit property="value(saveChg)" styleClass="submit" value="Save Changes" /> </td>
  <!--  <%if(info.getDmbsInfoLst().get("CNT").equals("ri")){%>
<td><html:button property="value(reprc)" styleClass="submit" onclick="return confirmREPRCSH('prc')"  value="Pricing"  /> </td>
  <%}%>-->
  <td>Select Status For All:</td>
  <td>
  <!--<html:select property="value(Status)"  onchange="setLabToLabLst(this , 'STT_')">
  <html:optionsCollection property="tfrSttList" label="labDesc" value="labVal" />
  </html:select>-->
  <%
  pageList= ((ArrayList)pageDtl.get("CSELECT") == null)?new ArrayList():(ArrayList)pageDtl.get("CSELECT");
             if(pageList!=null && pageList.size() >0){%>
             <html:select property="value(Status)" styleId="Stt" name="transferToMktSHForm"  onchange="setLabToLabLst(this , 'STT_')">
             <%
               for(int i=0;i<pageList.size();i++){
                 pageDtlMap=(HashMap)pageList.get(i);
                 fld_nme="value("+(String)pageDtlMap.get("fld_nme")+")";
                 fld_ttl=(String)pageDtlMap.get("fld_ttl");
                 val_cond=(String)pageDtlMap.get("val_cond");
                 dflt_val=(String)pageDtlMap.get("dflt_val");
                 %>
                <html:option value="<%=dflt_val%>"><%=fld_ttl%></html:option>
             <%}%>
            </html:select>
            
            <%}%>
  </td>

  <td>
  <html:button property="value(excel)" styleClass="submit" onclick="validateSelection('cb_prc_','Please Select Stone','transferMktSH.do?method=CrtExcel')" value="Create Excel" />
  </td>
   <td><html:button property="value(bulkupate)" styleClass="submit" styleId="multiprp" onclick="<%=openPopUp%>" value="Bulk Update" /> </td>
  
    <%
      pageList= ((ArrayList)pageDtl.get("BUTTON") == null)?new ArrayList():(ArrayList)pageDtl.get("BUTTON");
      if(pageList!=null && pageList.size() >0){
      for(int j=0;j<pageList.size();j++){
      pageDtlMap=(HashMap)pageList.get(j);
      fld_nme="value("+(String)pageDtlMap.get("fld_nme")+")";
      fld_ttl=(String)pageDtlMap.get("fld_ttl");
      val_cond=(String)pageDtlMap.get("val_cond");
      fld_typ=(String)pageDtlMap.get("fld_typ");
     String fld_id = (String)pageDtlMap.get("dflt_val");
      if(fld_typ.equals("S")){ %>
<td><html:submit property="<%=fld_nme%>" value="<%=fld_ttl%>"  onclick="<%=val_cond%>" styleClass="submit" /></td>
      <%}else if(fld_typ.equals("B")){%>
<td><html:button property="<%=fld_nme%>" value="<%=fld_ttl%>" styleId="<%=fld_id%>" onclick="<%=val_cond%>" styleClass="submit" /></td>            
<%}else if(fld_typ.equals("HB")){%>
<td><input type="button" id="<%=fld_id%>" onclick="<%=val_cond%>"  value="<%=fld_ttl%>"  class="submit"/></td>
<%}}}
%>
<!--<td><input type="button" id="summary" class="submit" onclick="purlab('summary','frame','TRF');"  value="Upload"/></td>-->
  <%
  ArrayList rolenmLst=(ArrayList)info.getRoleLst();
  String usrFlg=util.nvl((String)info.getUsrFlg());
  if(usrFlg.equals("SYS") || rolenmLst.contains("ADMIN")){
  %>
  <td><img src="../images/edit.jpg" title="Edit Properties Module" onclick="newWindow('<%=info.getReqUrl()%>/dynamicProperty.do?method=load&mdl=TFMKT_VW&sname=tfmktPrpList&par=V')" border="0" width="15px" height="15px"/> </td>
  <%}%>
  </tr>
  
  </table>
  </td></tr>
   <tr><td valign="top" class="tdLayout">
   <%
    ArrayList prpDspBlocked = info.getPageblockList();
    ArrayList vwPrpLst = (ArrayList)session.getAttribute("tfmktPrpList");
       ArrayList editPrp = (ArrayList)session.getAttribute("TFMKTEDITList");
    HashMap mprp = info.getMprp();
    HashMap  prpLst = info.getPrp();
   int colSpan = vwPrpLst.size()+8;
    %>
    <table cellpadding="5" cellspacing="5">
    
    <tr><td valign="top">
   
    <table class="grid1" align="left" id="dataTable">
<thead>
<tr>
<th><label title="SR NO">Sr No.</label></th>
<th><label title="CheckAll"><input  type="checkbox" id="all" name="all" onclick="ALLCheckBox('all','cb_prc_')" />Select </label></th>
<th><label title="Status">Status</label></th>
<th><label title="Detail">Detail</label></th>
<th><label title="Packet No">Packet No</label></th>
<th><label title="Rap RTE">Rap Rte</label></th>
<th><label title="Manual">MNL</label></th>
<%if(cnt.equals("ri")){%>
<th><label title="Asking Price">Asking Price</label></th>
<%}else{%>
<th><label title="cmp">CMP</label></th>

<%}%>
<th><label title="cmp">CMP Dis</label></th>
<th><label title="Rap dis">Rap Dis</label></th>
<th><label title="Modify Discount">Modify Dis</label></th>

<%for(int j=0; j < vwPrpLst.size(); j++ ){
    String prp = (String)vwPrpLst.get(j);
     if(prpDspBlocked.contains(prp)){
        colSpan=colSpan-1;
       }else{
     String hdr = (String)mprp.get(prp);
    String prpDsc = (String)mprp.get(prp+"D");
    if(hdr == null) {
        hdr = prp;
       }  
 if(prp.equals("EXTRADISCAMT")|| editPrp.contains(prp)){
   }else{
%>
<th title="<%=prpDsc%>"><%=hdr%></th>
<%}}}
%>
<%
if(editPrp!=null && editPrp.size()>0){
for(int i=0;i<editPrp.size();i++){
 String prp = (String)editPrp.get(i);
 if(prpDspBlocked.contains(prp)){
 }else{
 String prpDsc = (String)mprp.get(prp+"D");
  ArrayList prpVal = (ArrayList)prpLst.get(prp+"V");
 String txtId = "UPD_"+prp;
 String txtVal = "value("+txtId+")";
 String scriptFun = "AllSelectOne(this,'"+prp+"','1')";
%>
<th>
<%if(prpVal!=null && prpVal.size()>0){%>
     <html:select  property="<%=txtVal%>" styleId="<%=txtId%>" onchange="<%=scriptFun%>"  style="width:75px"   > 
         <html:option value="0">--select--</html:option>
         <% for(int k=0; k < prpVal.size(); k++) {
                String pPrt = (String)prpVal.get(k);
          %>
              <html:option value="<%=pPrt%>" ><%=pPrt%></html:option> 
        <%}%>
    </html:select>  
     <%}else{%>
     <html:text property="<%=txtVal%>"  styleClass="txtStyle" onchange="<%=scriptFun%>" size="8"/>
     <%}%><%=prp%>
</th>
<%}}}%>
<th>Edit</th>
</tr>
</thead>
<tbody>
<%
int lastno= pktStkIdnList.size()-1;
int size = pktStkIdnList.size();
for(int m=0;m<size;m++){ 
String stkIdn = (String)pktStkIdnList.get(m);
HashMap pktData = (HashMap)pktList.get(stkIdn);
String cts =util.nvl((String)pktData.get("cts"));
String cert_lab = util.nvl((String)pktData.get("cert_lab"));
String checkVal = "value("+stkIdn+")";
String checkId = "cb_prc_"+stkIdn;
String onchnge="ChangeFlgTRF("+stkIdn+" , this , 'NO' )";
String rdVal = "value(STT_"+stkIdn+")";
String modDis = "value(modDis_"+stkIdn+")";
String diffAmt = "value(DAMT_"+stkIdn+")";
String modDisId="modDis_"+stkIdn;
String mnlId = "MNL_"+stkIdn;
String diffAmtId = "DAMT_"+stkIdn;
String rdSttId = "STT_"+stkIdn;
String target = "SC_"+stkIdn;
String mnl = util.nvl((String)pktData.get("quot"));
String mnlFld = "value(mnl_"+stkIdn+")";
String onModifyChg ="CalculateDiffSH('"+stkIdn+"',this)";
String onModifyUprChg ="CalculateUPRDiffSH('"+stkIdn+"',this)";
String extra = util.nvl((String)pktData.get("EXTRADISCAMT")).trim();
String sltarget = "SL_"+stkIdn;
String cert_no =util.nvl((String)pktData.get("cert_no"));
String stt=util.nvl((String)pktData.get("stt1"));
  String link="";
  if(cert_lab.toUpperCase().equals("GIA"))
  link=gialink;
  if(cert_lab.toUpperCase().equals("IGI"))
  link=igilink;
  if(cert_lab.toUpperCase().equals("HRD"))
  link=hrdlink;
       link=link.replaceAll("~CERTNO~", cert_no);
       link=link.replaceAll("~CRTWT~", cts);
  String linkId ="<a href=\'"+link+"'\" target=\"_blank\">"+cert_no+"</a>";
double exT = 0;
if(!extra.equals(""))
  exT = Double.parseDouble(extra);
if(exT==0)
 extra="";
String url ="transferMktSH.do?method=stockUpdTrfMkt&mstkIdn="+stkIdn+"&mdl=AS_PRC_EDIT&currentpage="+m+"&lastpage="+lastno;
%>
<tr id="<%=target%>">

<td><%=m+1%></td>
<td><html:checkbox property="<%=checkVal%>" styleId="<%=checkId%>"  onclick="<%=onchnge%>" name="transferToMktSHForm"/> </td>

<%if(!stt.equals("LB_CF")){%>
<td>
<html:select property="<%=rdVal%>" styleId="<%=rdSttId%>" >
<html:optionsCollection property="tfrSttList" label="labDesc" value="labVal" />
</html:select>
</td>
<%}else{%>

<td>
<html:select property="<%=rdVal%>" styleId="<%=rdSttId%>" >
<html:optionsCollection property="tfrSttPRIList" label="labDesc" value="labVal" />
</html:select>
</td>
<%}%>
<td>
<img src="../images/plus.png"  id="IMG_<%=stkIdn%>" border="0" title="Buyers of similar stones" onclick="TtlBuyerSH('<%=stkIdn%>')"/>
</td>

<td nowrap="nowrap">
<%
      pageList= ((ArrayList)pageDtl.get("VNMLINK") == null)?new ArrayList():(ArrayList)pageDtl.get("VNMLINK");
      if(pageList!=null && pageList.size() >0){
      for(int j=0;j<pageList.size();j++){
      pageDtlMap=(HashMap)pageList.get(j);
      fld_typ=util.nvl((String)pageDtlMap.get("fld_typ"));
      if(fld_typ.equals("LINK")){ %>
        <a href="transferMktSH.do?method=details&mstkidn=<%=stkIdn%>&vnm=<%=pktData.get("vnm")%>" id="DTL_<%=stkIdn%>"  target="frame" title="Click Here To See Details" onclick="loadASSFNL('<%=stkIdn%>','DTL_<%=stkIdn%>')">
        <%=pktData.get("vnm")%></a>
      <%}else {%>
    <%=pktData.get("vnm")%>
<%
}}}
%>
<input type="hidden" id="CTS_<%=stkIdn%>"  value="<%=cts%>" /> 
 <input type="hidden" id="LAB_<%=stkIdn%>"  value="<%=cert_lab%>" />
</td>


<td><%=pktData.get("rap_rte")%><input type="hidden" name="rap_<%=stkIdn%>" value="<%=pktData.get("rap_rte")%>" id="rap_<%=stkIdn%>" /></td>
<td>
<%=mnl%>
<html:text property="<%=mnlFld%>" size="8" onchange="<%=onModifyUprChg%>" styleId="<%=mnlId%>"  />

</td>
<td><%=pktData.get("cmp")%><input type="hidden" name="qout_<%=stkIdn%>" value="<%=pktData.get("cmp")%>" id="qout_<%=stkIdn%>" /> </td>
<td><%=pktData.get("cmp_dis")%></td>
<td><%=pktData.get("r_dis")%> <input type="hidden" name="dis_<%=stkIdn%>" value="<%=pktData.get("r_dis")%>" id="dis_<%=stkIdn%>" /> </td><td>
<html:text property="<%=modDis%>" size="8" styleId="<%=modDisId%>" onchange="<%=onModifyChg%>"/> 
<%if(lastno!=m){
String nextstkIdn = (String)pktStkIdnList.get(m+1);
%>
<input type="hidden" name="next_<%=stkIdn%>" id="next_<%=stkIdn%>" value="<%=nextstkIdn%>" />
<%}else{%>
<input type="hidden" name="next_<%=stkIdn%>" id="next_<%=stkIdn%>" value="NO" />
<%}%>
</td>

<%
 for(int l=0;l<vwPrpLst.size();l++){
   String prp = (String)vwPrpLst.get(l);
   if(prpDspBlocked.contains(prp)){
       }else{
    String prpValue =  (String) pktData.get(prp);
    if(prpValue.equals("NA"))
     prpValue = "";
    String prpDsc = (String)mprp.get(prp+"D");
  if(prp.equals("EXTRADISCAMT")||editPrp.contains(prp)){
   }else{
   if(prp.equals("CERT_NO")){%>
   <td align="right" title="Click here to see Certificate"><%=linkId%></td>
 <%}else if(prp.equals("LAB")){
if(imagelistDtl!=null && imagelistDtl.size() >0){
for(int j=0;j<imagelistDtl.size();j++){
HashMap dtls=new HashMap();
dtls=(HashMap)imagelistDtl.get(j);
String looptyp=util.nvl((String)dtls.get("TYP"));
String allowon=util.nvl((String)dtls.get("ALLOWON"));
String gtCol=util.nvl((String)dtls.get("GTCOL"));
if(looptyp.equals("C")){
String path=util.nvl((String)dtls.get("PATH"));
String val=util.nvl((String)pktData.get(gtCol));
if(!val.equals("N")){
if(val.indexOf(".pdf")> -1)
path=path+val;
else
path="../zoompic.jsp?fileNme="+path+val+"&ht=800&wd=1100&cnt="+cnt;
url = "<A href='"+path+"' target=\"_blank\" ><b>"+prpValue+"</b></a>";
prpValue = url;
}}}}%>
<td title="<%=prpDsc%>" nowrap="nowrap"><%=prpValue%></td>
<%}else{%>
<td title="<%=prpDsc%>" nowrap="nowrap">
<%
if(prp.equals("NET_PUR_RTE")){
%>
<input type="hidden" name="netrte_<%=stkIdn%>" value="<%=prpValue%>" id="netrte_<%=stkIdn%>" />
<%}%>
<%=prpValue%></td>
<%}}}}%>
<%
if(editPrp!=null && editPrp.size()>0){
for(int i=0;i<editPrp.size();i++){
 String prp = (String)editPrp.get(i);
 if(prpDspBlocked.contains(prp)){
 }else{
 String prpDsc = (String)mprp.get(prp+"D");
  ArrayList prpVal = (ArrayList)prpLst.get(prp+"V");
 String txtId = "UPD_"+prp+"_"+stkIdn;
 String txtVal = "value("+txtId+")";
 String onchange = "stkUpd('"+cert_lab+"','"+stkIdn+"','"+prp+"',this)";
  String lprpVal = util.nvl((String)pktData.get(prp));
%>
<td>
<%if(prpVal!=null && prpVal.size()>0){%>
     <html:select  property="<%=txtVal%>" styleId="<%=txtId%>" onchange="<%=onchange%>"  style="width:75px"   > 
         <html:option value="0">--select--</html:option>
         <% for(int k=0; k < prpVal.size(); k++) {
                String pPrt = (String)prpVal.get(k);
          %>
              <html:option value="<%=pPrt%>" ><%=pPrt%></html:option> 
        <%}%>
    </html:select>  
     <%}else{%>
     <html:text property="<%=txtVal%>" styleId="<%=txtId%>" value="<%=lprpVal%>" onchange="<%=onchange%>" styleClass="txtStyle" size="8"/>
     <%}%>
     <%=lprpVal%>
</td>
<%}}}%>
<td> <a href="<%=url%>"  id="LNK_<%=stkIdn%>" onclick="loadASSFNL('<%=target%>','LNK_<%=stkIdn%>')"  target="frame">Edit</a></td>
</tr>


<tr id="<%=stkIdn%>" style="display:none">
<td  colspan="<%=colSpan%>">
<div id="<%=sltarget%>"></div>
</td>
</tr>

<%}
session.setAttribute("pktStkIdnList", pktStkIdnList);
%>        


</tbody>
</table>
</td></tr></table>
   
   
   </td></tr>
   
   </html:form>
    <%}else{%>
     <tr><td valign="top" class="hedPg">Sorry no result found </td></tr>
  <% } }%>
  <tr>
    <td><jsp:include page="/footer.jsp" /> </td>
    </tr>
   </table>
   </body>
</html>