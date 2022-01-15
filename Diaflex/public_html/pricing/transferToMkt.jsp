<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page errorPage="../error_page.jsp" %>
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import="java.util.ArrayList,java.util.HashMap,java.sql.ResultSet,ft.com.*, java.util.Date, java.util.ArrayList, ft.com.dao.*"%>

<jsp:useBean id="util" class="ft.com.JspUtil" scope="page" />
<jsp:useBean id="info" class="ft.com.InfoMgr" scope="session" />
<%@taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>
<%@taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>


<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>Transfer to Marketing</title>
    <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/bse.css?v=<%=info.getJsVersion()%>"/>
      <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/style.css?v=<%=info.getJsVersion()%>"/>
      <script src="<%=info.getReqUrl()%>/scripts/memoScript.js?v=<%=info.getJsVersion()%>" type="text/javascript"></script>
       <script src="<%=info.getReqUrl()%>/scripts/bse.js?v=<%=info.getJsVersion()%>" type="text/javascript"></script>
       <script src="<%=info.getReqUrl()%>/scripts/priceChange.js?v=<%=info.getJsVersion()%>" type="text/javascript"></script>
         <script src="<%=info.getReqUrl()%>/scripts/Validation.js" type="text/javascript"></script>
        <script src="<%=info.getReqUrl()%>/scripts/assortScript.js?v=<%=info.getJsVersion()%>" type="text/javascript"></script>
         <script type="text/javascript" src="<%=info.getReqUrl()%>/PopScripts/popup.js?v=<%=info.getJsVersion()%> " > </script>
   <script src="<%=info.getReqUrl()%>/PopScripts/jquery-1.2.6.min.js" type="text/javascript"></script>
       <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/general.css?v=<%=info.getJsVersion()%>"/>
  </head>
<%
    String accessidn=(String)request.getAttribute("accessidn");
    HashMap allPageDtl= (info.getPageDetails() == null)?new HashMap():(HashMap)info.getPageDetails();
    HashMap pageDtl=(HashMap)allPageDtl.get("TRANS_TO_MKT");
    ArrayList pageList=new ArrayList();
    HashMap pageDtlMap=new HashMap();
    String fld_nme="",fld_ttl="",val_cond="",dflt_val="",fld_typ="",form_nme="", fld_id="";
String form1Url = info.getReqUrl()+"/pricing/transferMkt.do?method=view";
String foem2Url = info.getReqUrl()+"/pricing/transferMkt.do?method=transMkt";
HashMap dbInfoSys = info.getDmbsInfoLst();
%>
 <body onload="MM_preloadImages('images/faunalogo.png')" topmargin="0" leftmargin="0" rightmargin="0" bottommargin="0">

<input type="hidden" id="reqUrl" name="reqUrl" value="<%=info.getReqUrl()%>"/>
 <div id="backgroundPopup"></div>
<div id="popupContactASSFNL">
<table class="popUpTb">
 <tr><td height="5%">&nbsp;&nbsp;&nbsp;<html:button property="value(close)" styleClass="submit"  onclick="MKEmptyPopup('SC');disablePopupASSFNL();" value="Close"  /> </td></tr>
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
  <html:form action="pricing/transferMkt.do?method=view" method="post">
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
  <tr>
   <%
                pageList=(ArrayList)pageDtl.get("SELECT");
             if(pageList!=null && pageList.size() >0){%>
             <td>Status:
             <html:select property="value(Stt)" styleId="Stt" name="transferToMktForm" >
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
           <%}%>
  </tr>

  <tr>
     <td align="left">Pricing Sequence No 
    <html:text property="value(seq)" name="transferToMktForm" />
     </td>
    </tr>
  <tr>
  <td>
   <jsp:include page="/genericSrch.jsp"/></td></tr></table>
  </td><td valign="top">
  <html:textarea property="value(vnmLst)" rows="13" name="transferToMktForm" />
  </td></tr>
  <tr><td  colspan="2" align="center"><html:submit property="view" value="View" styleClass="submit"/>
   <html:submit property="viewAll" value="View All" styleClass="submit"/> </td>
  </tr>
  </table>
  </html:form>
  </td></tr>
 
 <% HashMap pktList = (HashMap)request.getAttribute("pktList");
    ArrayList pktStkIdnList = (ArrayList)request.getAttribute("pktStkIdnList");
    String view = (String)request.getAttribute("view");
    if(view!=null){
    ArrayList prpDspBlocked = info.getPageblockList();
    if(pktList!=null && pktList.size()>0){
     HashMap totals = (HashMap)request.getAttribute("totalMap");
     String cnt = (String)dbInfoSys.get("CNT");
    %>
    <tr><td valign="top" class="tdLayout">
   <table>
   <tr><td>Total :&nbsp;&nbsp;</td><td><span id="ttlqty"> <%=totals.get("qty")%>&nbsp;&nbsp;</span></td><td>cts&nbsp;&nbsp;</td><td><span id="ttlcts"><%=totals.get("cts")%>&nbsp;&nbsp;</span></td><td>Selected:&nbsp;&nbsp;</td><td> Total :&nbsp;&nbsp;</td> <td><span id="qtyTtl">0&nbsp;&nbsp;</span></td><td>Cts&nbsp;&nbsp;</td><td><span id="ctsTtl">0</span> </td> 
   <td> <div class="Blue">&nbsp;</div></td><td> Lab Confirm</td>
   
   </tr>
  
   </table>
   </td></tr>
    
   <html:form action="pricing/transferMkt.do?method=transMkt" method="post"  >
    <input type="hidden" name="accessidn" id="accessidn" value="<%=accessidn%>"/>
    <%
  
    %>
  <tr><td valign="top" class="tdLayout">
  <table cellpadding="2" cellspacing="2">
  <tr>
   <%
   
    pageList=(ArrayList)pageDtl.get("BUTTON");
      if(pageList!=null && pageList.size() >0){
      for(int j=0;j<pageList.size();j++){
      pageDtlMap=(HashMap)pageList.get(j);
      fld_nme="value("+(String)pageDtlMap.get("fld_nme")+")";
      fld_ttl=(String)pageDtlMap.get("fld_ttl");
      val_cond=(String)pageDtlMap.get("val_cond");
      fld_typ=(String)pageDtlMap.get("fld_typ");
      fld_id = (String)pageDtlMap.get("dflt_val");
      if(fld_typ.equals("S")){ %>
<td><html:submit property="<%=fld_nme%>" value="<%=fld_ttl%>"  onclick="<%=val_cond%>" styleClass="submit" /></td>
      <%}else if(fld_typ.equals("B")){%>
<td><html:button property="<%=fld_nme%>" value="<%=fld_ttl%>" styleId="<%=fld_id%>" onclick="<%=val_cond%>" styleClass="submit" /></td>            
<%
}}}
%>
<!--<td><html:button property="value(reprc)" styleClass="submit" onclick="return confirmREPRC('prc')"  value="Pricing"  /> </td>
  <td><html:button property="value(saveLab)" styleClass="submit" onclick="return confirmREPRC('lab')" value="Apply Lab Value" /> </td>
  <td><html:submit property="value(saveChg)" styleClass="submit" onclick="return validate_prc('cb_prc_')"  value="Save Changes" /> </td>
   <td><html:button property="value(memoPrint)" onclick="return confirmREPRC('memo')" styleClass="submit" value="Memo Print" /> </td>-->
   
  <td><A href="<%=info.getReqUrl()%>/pricing/transferMkt.do?method=load" ><img src="../images/refresh.gif" title="Refresh Page" border="0"/></a> </td>
  <td>Select Status For All:</td>
  <td>
  
   <%pageList=(ArrayList)pageDtl.get("CSELECT");
             if(pageList!=null && pageList.size() >0){%>
             <html:select property="value(Status)" styleId="Stt" name="transferToMktForm"  onchange="setLabToLabLst(this , 'STT_')">
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
    
    ArrayList vwPrpLst = (ArrayList)session.getAttribute("tfmktPrpList");
    HashMap mprp = info.getMprp();
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
<%if(cnt.equalsIgnoreCase("sd")){%>
<th><label title="Sal Avg">SL Avg</label></th>
<%}%>
<th><label title="Packet No">Packet No</label></th>
<th><label title="Rap RTE">Rap Rte</label></th>
<th><label title="Manual">MNL</label></th>
<th><label title="cmp">CMP</label></th>
<th><label title="Rap dis">Rap Dis</label></th>
<th><label title="Modify Discount">Modify Dis</label></th>
<th><label title="Difference Amount">Diff Amt</label></th>
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
 if(prp.equals("EXTRADISCAMT")){
   }else{
%>
<th title="<%=prpDsc%>"><%=hdr%></th>

<%}}}%>
<% pageList=(ArrayList)pageDtl.get("LINKHDR");
            if(pageList!=null && pageList.size() >0){
            for(int j=0;j<pageList.size();j++){
            pageDtlMap=(HashMap)pageList.get(j);
              fld_nme=(String)pageDtlMap.get("fld_nme");
    %>
  <th>  <%=fld_nme%> </th>
 <%}}%>
</tr>
</thead>
<tbody>
<%
for(int m=0;m< pktStkIdnList.size();m++){ 
String stkIdn = (String)pktStkIdnList.get(m);
HashMap pktData = (HashMap)pktList.get(stkIdn);
String cts =util.nvl((String)pktData.get("cts"));
String rap_dis = (String)pktData.get("r_dis");

float rapDis = 0;
String cellCol ="";
if(!rap_dis.equals("") && rap_dis.indexOf("#")==-1){
  rapDis = Float.parseFloat(rap_dis);
  if(rapDis < -50 || rapDis > 50)
    cellCol ="style=\"color:red\"";
  }
String checkVal = "value("+stkIdn+")";
String checkId = "cb_prc_"+stkIdn;
String onchnge="ChangeFlg("+stkIdn+" , this , 'NO' )";
String rdVal = "value(STT_"+stkIdn+")";
String modDis = "value(modDis_"+stkIdn+")";
String diffAmt = "value(DAMT_"+stkIdn+")";
String modDisId="modDis_"+stkIdn;
String diffAmtId = "DAMT_"+stkIdn;
String rdSttId = "STT_"+stkIdn;
String target = "SC_"+stkIdn;
String mnl = util.nvl((String)pktData.get("quot"));
String mnlFld = "value(mnl_"+stkIdn+")";
String onModifyChg ="CalculateDiff('"+stkIdn+"',this)";
String extra = util.nvl((String)pktData.get("EXTRADISCAMT")).trim();
String sltarget = "SL_"+stkIdn;
String stt=util.nvl((String)pktData.get("stt1"));
double exT = 0;
if(!extra.equals(""))
  exT = Double.parseDouble(extra);
if(exT==0)
 extra="";
 String cssCls = "";
 if(stt.equals("LB_CF"))
 cssCls ="color:blue";
 
%>
<tr id="<%=target%>" style="<%=cssCls%>">

<td><%=m+1%></td>
<td><html:checkbox property="<%=checkVal%>" styleId="<%=checkId%>"  onclick="<%=onchnge%>" name="transferToMktForm"/> </td>
<td>
<%if(!stt.equals("LB_CF")){%>

<html:select property="<%=rdVal%>" styleId="<%=rdSttId%>" >
<html:optionsCollection property="tfrSttList" label="labDesc" value="labVal" />
</html:select>

<%}else{%>


<html:select property="<%=rdVal%>" styleId="<%=rdSttId%>" >
<html:optionsCollection property="tfrSttPRIList" label="labDesc" value="labVal" />
</html:select>

<%}%>
</td>

<td><span <%=cellCol%>> <%=pktData.get("vnm")%></span><input type="hidden" id="CTS_<%=stkIdn%>"  value="<%=cts%>" />
<input type="hidden" id="VNM_<%=stkIdn%>"  value="<%=pktData.get("vnm")%>" />
</td>
<td><%=pktData.get("rap_rte")%><input type="hidden" name="rap_<%=stkIdn%>" value="<%=pktData.get("rap_rte")%>" id="rap_<%=stkIdn%>" /></td>
<td>
<%=mnl%>
<input type="hidden"  value="<%=mnl%>" id="mnlOld_<%=stkIdn%>" />
<!--<html:text property="<%=mnlFld%>" size="8"  />-->
<% pageList=(ArrayList)pageDtl.get("MNL");
            if(pageList!=null && pageList.size() >0){
            for(int j=0;j<pageList.size();j++){
            pageDtlMap=(HashMap)pageList.get(j);
            fld_nme="value("+(String)pageDtlMap.get("fld_nme")+")";
            fld_nme=fld_nme.replaceAll("STKIDN",stkIdn);
            fld_ttl=(String)pageDtlMap.get("fld_ttl");
            fld_ttl=fld_ttl.replaceAll("STKIDN",stkIdn);
            val_cond=(String)pageDtlMap.get("val_cond");
            val_cond=val_cond.replaceAll("STKIDN",stkIdn);
            fld_typ=(String)pageDtlMap.get("fld_typ");
    %>
  <html:text property="<%=fld_nme%>" styleId="<%=fld_ttl%>" size="8"  onchange="<%=val_cond%>"  />
 <%}}%>
</td>
<td><%=pktData.get("cmp")%>
<input type="hidden" name="qout_<%=stkIdn%>" value="<%=pktData.get("cmp")%>" id="qout_<%=stkIdn%>" /> </td>
<td><span <%=cellCol%>><%=pktData.get("r_dis")%> </span><input type="hidden" name="dis_<%=stkIdn%>" value="<%=pktData.get("r_dis")%>" id="dis_<%=stkIdn%>" /> </td>
<% pageList=(ArrayList)pageDtl.get("DIS_DIFF");
            if(pageList!=null && pageList.size() >0){
            for(int j=0;j<pageList.size();j++){
            pageDtlMap=(HashMap)pageList.get(j);
            fld_nme="value("+(String)pageDtlMap.get("fld_nme")+")";
            fld_nme=fld_nme.replaceAll("STKIDN",stkIdn);
            fld_ttl=(String)pageDtlMap.get("fld_ttl");
            fld_ttl=fld_ttl.replaceAll("STKIDN",stkIdn);
            val_cond=(String)pageDtlMap.get("val_cond");
            val_cond=val_cond.replaceAll("STKIDN",stkIdn);
            fld_typ=(String)pageDtlMap.get("fld_typ");
            if(fld_typ.equals("")){
    %>
  <td><html:text property="<%=fld_nme%>" size="8" styleId="<%=fld_ttl%>" onchange="<%=val_cond%>"/></td>
 <%}else{%>
<td><html:text property="<%=fld_nme%>" size="8" styleId="<%=fld_ttl%>" onchange="<%=val_cond%>"/>
<input type="hidden"  value="<%=extra%>" id="amt_<%=stkIdn%>" /></td> 
 <%}}}%>
<!--<td><html:text property="<%=modDis%>" size="8" styleId="<%=modDisId%>" onchange="<%=onModifyChg%>"/></td>
<td><html:text property="<%=diffAmt%>" size="8" readonly="true" value="<%=extra%>" styleId="<%=diffAmtId%>"/>
<input type="hidden"  value="<%=extra%>" id="amt_<%=stkIdn%>" />
</td>-->

<%
String cellStyle ="";
float qout = 0;
if(!mnl.equals(""))
 qout = Float.valueOf(mnl);
 for(int l=0;l<vwPrpLst.size();l++){
   String prp = (String)vwPrpLst.get(l);
   if(prpDspBlocked.contains(prp)){
    }else{
    String prpValue =  (String) pktData.get(prp);
    if(prpValue.equals("NA"))
     prpValue = "";
    String prpDsc = (String)mprp.get(prp+"D");
     if(prp.equals("MIX_RTE")){
   
     if(prpValue.equals(""))
       prpValue = "0";
      
       float mixIntRTE =  Float.valueOf(prpValue);
       if(mixIntRTE >qout)
       cellStyle ="style=\"background-color:Lime;\"";
     }
    
   if(prp.equals("EXTRADISCAMT")){
   }else{
 %>
<td title="<%=prpDsc%>" <%=cellStyle%>><%=prpValue%></td>
<%}}}%>       
 <% pageList=(ArrayList)pageDtl.get("LINK");
      if(pageList!=null && pageList.size() >0){
      for(int j=0;j<pageList.size();j++){
      pageDtlMap=(HashMap)pageList.get(j);
      String dsc=(String)pageDtlMap.get("fld_ttl");
      String url = (String)pageDtlMap.get("lov_qry");
      url = url.replace("STKID", stkIdn);
      url = info.getReqUrl()+""+url;
      dsc = dsc.replace("URL", url);
      dsc= dsc.replace("ID", "LNK_"+stkIdn);
      dsc= dsc.replace("ONCLICK", "loadASSFNL('"+target+"','LNK_"+stkIdn+"')");
      %>
   <td>  <%=dsc%></td>
      <%}}%>
</tr>

<%if(cnt.equalsIgnoreCase("sd")){%>
<tr id="<%=stkIdn%>" style="display:none"><td colspan="<%=colSpan%>">
<div id="<%=sltarget%>"></div>
</td></tr>
<%}%>
<%}%>        


</tbody>
</table>
</td></tr></table>
   
   
   </td></tr>
   
   </html:form>
    <%}else{%>
     <tr><td>Sorry no result found </td></tr>
  <% } }%>
  <tr>
    <td><jsp:include page="/footer.jsp" /> </td>
    </tr>
   </table>
   </body>
</html>