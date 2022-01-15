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
    <title>Generate Proforma</title>
    <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/bse.css?v=<%=info.getJsVersion()%>"/>
     <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/general.css?v=<%=info.getJsVersion()%>"/>
      <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/style.css?v=<%=info.getJsVersion()%>"/>
      <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/ajax.js?v=<%=info.getJsVersion()%> " > </script>
      <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/bse.js?v=<%=info.getJsVersion()%> " > </script>
      <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/memoScript.js?v=<%=info.getJsVersion()%> " > </script>
          <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/priceChange.js?v=<%=info.getJsVersion()%> " > </script>
    <script type="text/javascript" src="<%=info.getReqUrl()%>/PopScripts/popup.js?v=<%=info.getJsVersion()%> " > </script>
<script src="<%=info.getReqUrl()%>/PopScripts/jquery-1.2.6.min.js" type="text/javascript"></script>
    <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/assortScript.js?v=<%=info.getJsVersion()%> " > </script>
    <script language="javaScript" type="text/javascript" src="<%=info.getReqUrl()%>/calendar/calendar.js"></script>
   <link href="<%=info.getReqUrl()%>/calendar/calendar.css" rel="stylesheet" type="text/css" />
  
  </head>
<%
HashMap dbinfo = info.getDmbsInfoLst();
String cnt = (String)dbinfo.get("CNT");
  String repPath = (String)dbinfo.get("REP_PATH");
    HashMap allPageDtl= (info.getPageDetails() == null)?new HashMap():(HashMap)info.getPageDetails();
    HashMap pageDtl=(HashMap)allPageDtl.get("PERFORMA_INV");
ArrayList pageList=new ArrayList();
HashMap pageDtlMap=new HashMap();
String fld_nme="",fld_ttl="",val_cond="",dflt_val="",fld_typ="",form_nme="";
String performaLink = util.nvl((String)request.getAttribute("performaLink"));
        String logId=String.valueOf(info.getLogId());
        String onfocus="cook('"+logId+"')";
        %>
 <body onfocus="<%=onfocus%>" onload="MM_preloadImages('images/faunalogo.png')" topmargin="0" leftmargin="0" rightmargin="0" bottommargin="0">
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
  <img src="../images/line.jpg" border="0" /></td><td valign="middle"> <span class="pgHed">Generate Performa</span> </td>
</tr></table>
  </td>
  </tr>
  <tr><td valign="top" class="tdLayout">
  <html:form action="/marketing/performa.do?method=loadPkt"  method="POST">
 <input type="hidden" name="rptUrl" id="rptUrl" value="<%=performaLink%>" />       
        <table class="grid1" >
        <tr>
        <td>
        <!--<html:radio property="value(invoice)"   styleId="apRd"  value="DLV" name="saleDeliveryForm"/>&nbsp;Sale &nbsp;
        <html:radio property="value(invoice)" styleId="slRd" value="DLV" name="saleDeliveryForm"/>&nbsp;Delivery&nbsp;
        <html:radio property="value(invoice)"  styleId="boxRd" value="BOX" name="saleDeliveryForm"/>&nbsp;Mix-->
        <%pageList=(ArrayList)pageDtl.get("RADIO");
            if(pageList!=null && pageList.size() >0){
            for(int j=0;j<pageList.size();j++){
            pageDtlMap=(HashMap)pageList.get(j);
            fld_nme="value("+(String)pageDtlMap.get("fld_nme")+")";
            fld_ttl=(String)pageDtlMap.get("fld_ttl");
            fld_typ=(String)pageDtlMap.get("fld_typ");
            form_nme=(String)pageDtlMap.get("form_nme");
            dflt_val=(String)pageDtlMap.get("dflt_val");
            val_cond=(String)pageDtlMap.get("val_cond"); %>
            <html:radio property="<%=fld_nme%>" name="<%=form_nme%>" styleId="<%=fld_typ%>" value="<%=dflt_val%>"/><%=fld_ttl%>
            <%}}%>
        
        </td>
        </tr>
        <tr><td><table>
        <tr>
            <td>Company Name &nbsp;</td>
           <td><html:select property="value(grpIdn)" name="saleDeliveryForm"  styleId="grpIdn"  >
             <html:optionsCollection property="groupList" name="saleDeliveryForm"
             value="idn" label="addr" />
            </html:select>
          </td>
        </tr>
        <tr>
        <td>Format</td>
        <td><html:select property="value(fmt)"  name="saleDeliveryForm" styleId="fmt">
        <%pageList=(ArrayList)pageDtl.get("FMT");
            if(pageList!=null && pageList.size() >0){
            for(int j=0;j<pageList.size();j++){
            pageDtlMap=(HashMap)pageList.get(j);
            fld_nme="value("+(String)pageDtlMap.get("fld_nme")+")";
            fld_ttl=(String)pageDtlMap.get("fld_ttl");
            fld_typ=(String)pageDtlMap.get("fld_typ");
            form_nme=(String)pageDtlMap.get("form_nme");
            dflt_val=(String)pageDtlMap.get("dflt_val");
            val_cond=(String)pageDtlMap.get("val_cond"); %>
            <html:option value="<%=dflt_val%>"><%=fld_ttl%></html:option>
            <%}}%>
        </html:select></td>
        </tr>
                <tr>
        <td>Date</td>
        <td><html:select property="value(datePrt)"  name="saleDeliveryForm" styleId="datePrt">
            <html:option value="SYS">Current Date</html:option>
            <html:option value="TRNS">Transaction Date</html:option>
        </html:select></td>
        </tr>
        <tr>
        <td>Idn </td><td> <html:text property="value(saleid)" styleId="idn" size="20" name="saleDeliveryForm"  /></td>
        </tr>
         <%pageList=(ArrayList)pageDtl.get("BUYNMEDP");
            if(pageList!=null && pageList.size() >0){%>
         <tr>
        <td>Buyer Name</td>
        <td><html:select property="value(buy)"  name="saleDeliveryForm" styleId="buy">
       <% for(int j=0;j<pageList.size();j++){
            pageDtlMap=(HashMap)pageList.get(j);
            fld_nme="value("+(String)pageDtlMap.get("fld_nme")+")";
            fld_ttl=(String)pageDtlMap.get("fld_ttl");
            fld_typ=(String)pageDtlMap.get("fld_typ");
            form_nme=(String)pageDtlMap.get("form_nme");
            dflt_val=(String)pageDtlMap.get("dflt_val");
            val_cond=(String)pageDtlMap.get("val_cond"); %>
            <html:option value="<%=dflt_val%>"><%=fld_ttl%></html:option>
            <%}%>
        </html:select></td>
        </tr><%}%>
            <%
            String sty="display:none";
            pageList=(ArrayList)pageDtl.get("BANK_LOC");
            if(pageList!=null && pageList.size() >0){
            sty="";
            }%>
             <tr style="<%=sty%>"><td nowrap="nowrap">Bank Selection</td>
             <td>
             <html:select property="value(bankIdnLoc)" name="saleDeliveryForm" styleId="bankIdnLoc"  >
             <html:optionsCollection property="bankList" name="saleDeliveryForm"
             value="idn" label="addr" />
             </html:select>
            </td>
            </tr>
            
             <%pageList=(ArrayList)pageDtl.get("INVNO");
            if(pageList!=null && pageList.size() >0){%>
         <tr>
        <td>Invoice No.</td>
        <td>
         <html:text property="value(invno)" styleId="invno" size="20" name="saleDeliveryForm"  />
        </td></tr>
        <%}%>
        
        <%pageList=(ArrayList)pageDtl.get("CONSIGNDT");
            if(pageList!=null && pageList.size() >0){%>
         <tr>
        <td>Consignee</td>
        <td>
         <html:text property="value(consign)" styleId="consign" size="20" name="saleDeliveryForm"  />
        </td></tr>
        <%}%>
          <%pageList=(ArrayList)pageDtl.get("CONSIGNDP");
            if(pageList!=null && pageList.size() >0){%>
         <tr>
        <td>Consignee</td>
        <td><html:select property="value(consign)"  name="saleDeliveryForm" styleId="consign">
       <% for(int j=0;j<pageList.size();j++){
            pageDtlMap=(HashMap)pageList.get(j);
            fld_nme="value("+(String)pageDtlMap.get("fld_nme")+")";
            fld_ttl=(String)pageDtlMap.get("fld_ttl");
            fld_typ=(String)pageDtlMap.get("fld_typ");
            form_nme=(String)pageDtlMap.get("form_nme");
            dflt_val=(String)pageDtlMap.get("dflt_val");
            val_cond=(String)pageDtlMap.get("val_cond"); %>
            <html:option value="<%=dflt_val%>"><%=fld_ttl%></html:option>
            <%}%>
        </html:select></td>
        </tr><%}%>
            <%pageList=(ArrayList)pageDtl.get("FORMAT");
            if(pageList!=null && pageList.size() >0){%>
         <tr>
        <td>Format</td>
        <td><html:select property="value(format)"  name="saleDeliveryForm" styleId="format">
       <% for(int j=0;j<pageList.size();j++){
            pageDtlMap=(HashMap)pageList.get(j);
            fld_nme="value("+(String)pageDtlMap.get("fld_nme")+")";
            fld_ttl=(String)pageDtlMap.get("fld_ttl");
            fld_typ=(String)pageDtlMap.get("fld_typ");
            form_nme=(String)pageDtlMap.get("form_nme");
            dflt_val=(String)pageDtlMap.get("dflt_val");
            val_cond=(String)pageDtlMap.get("val_cond"); %>
            <html:option value="<%=dflt_val%>"><%=fld_ttl%></html:option>
            <%}%>
        </html:select></td>
        </tr><%}%>
<%pageList=(ArrayList)pageDtl.get("MAILSEND");

        if(pageList!=null && pageList.size() >0){%>
         <tr>
        <td>Mail</td>
        <td><html:select property="value(mail)"  name="saleDeliveryForm" styleId="mail">
       <% for(int j=0;j<pageList.size();j++){
            pageDtlMap=(HashMap)pageList.get(j);
            fld_nme="value("+(String)pageDtlMap.get("fld_nme")+")";
            fld_ttl=(String)pageDtlMap.get("fld_ttl");
            fld_typ=(String)pageDtlMap.get("fld_typ");
            form_nme=(String)pageDtlMap.get("form_nme");
            dflt_val=(String)pageDtlMap.get("dflt_val");
            val_cond=(String)pageDtlMap.get("val_cond"); %>
            <html:option value="<%=dflt_val%>"><%=fld_ttl%></html:option>
            <%}%>
        </html:select></td>
        </tr><%}%>
        </table></td></tr>
        <tr>
        <td align="center">
        <%  pageList=(ArrayList)pageDtl.get("BUTTON");
            if(pageList!=null && pageList.size() >0){
            for(int j=0;j<pageList.size();j++){
            pageDtlMap=(HashMap)pageList.get(j);
            fld_nme="value("+(String)pageDtlMap.get("fld_nme")+")";
            fld_ttl=(String)pageDtlMap.get("fld_ttl");
            fld_typ=(String)pageDtlMap.get("fld_typ");
            dflt_val=(String)pageDtlMap.get("dflt_val");
            val_cond=(String)pageDtlMap.get("val_cond"); 
            if(fld_typ.equals("S")){%>
    <html:submit property="<%=fld_nme%>" value="<%=fld_ttl%>" onclick="<%=val_cond%>" styleClass="submit"/>&nbsp;
    <%}else if(fld_typ.equals("B")){%>
    <html:button property="<%=fld_nme%>" value="<%=fld_ttl%>" onclick="<%=val_cond%>" styleClass="submit"/>&nbsp;
    <%}}}%>
       <!--<html:button property="value(perInv)" onclick="GenPerformInvSaleID()" value="Performa Invoice" styleClass="submit"/>&nbsp;
        <html:submit property="value(viewPkt)" value="View Packets" styleClass="submit"/>&nbsp;-->
        </td></tr>
        </table>
        </html:form>
    </td>
    </tr>
    <%
    String msg = util.nvl((String)request.getAttribute("msg"));
    String view = util.nvl((String)request.getAttribute("view"));
    String typ = util.nvl((String)request.getAttribute("typ"));
    ArrayList pktList = (ArrayList)session.getAttribute("pktPerformaList");
    ArrayList chargeLst = (ArrayList)session.getAttribute("pktchargeLst");
    ArrayList perFormInvLst =(ArrayList)session.getAttribute("perInvViewLst");
    HashMap dtl =(HashMap)request.getAttribute("dtl");
    if(typ.equals("BOX"))
    perFormInvLst=(ArrayList)session.getAttribute("perInvBoxViewLst");
    if(!view.equals("")){
    if(pktList!=null && pktList.size() > 0){
    int perFormInvLstsz=perFormInvLst.size();
    int sr=0;
    String ttlQty=util.nvl((String)dtl.get("ttlQty"));
    String ttlCts=util.nvl((String)dtl.get("ttlCts"));
    String ttlVlu=util.nvl((String)dtl.get("ttlVlu"));
    String grandttlVlu=util.nvl((String)dtl.get("grandttlVlu"));
    int chargeLstsz=chargeLst.size();
    %>
    <tr><td><table>
    <html:form action="/marketing/performa.do?method=pktPerforma"  method="POST">
    <html:hidden property="value(typ)" styleId="typ" name="saleDeliveryForm" />
    <html:hidden property="value(grpIdn)" styleId="grpIdn" name="saleDeliveryForm" />
    <html:hidden property="value(location)" styleId="loacation" name="saleDeliveryForm" />
    <html:hidden property="value(stt)" styleId="stt" name="saleDeliveryForm" />
    <html:hidden property="value(pktTyp)" styleId="pktTyp" name="saleDeliveryForm" />
    <html:hidden property="value(consign)" styleId="consign" name="saleDeliveryForm" />
    
    <html:hidden property="value(ttlQty)" styleId="ttlQty" value="<%=ttlQty%>" name="saleDeliveryForm" />
    <html:hidden property="value(ttlCts)" styleId="ttlCts"  value="<%=ttlCts%>" name="saleDeliveryForm" />
    <html:hidden property="value(ttlVlu)" styleId="ttlVlu"  value="<%=ttlVlu%>" name="saleDeliveryForm" />
    <html:hidden property="value(grandttlVlu)"  styleId="grandttlVlu"  value="<%=grandttlVlu%>" name="saleDeliveryForm" />
    <tr><td valign="top" class="hedPg"><table><tr>
    <td>Total :&nbsp;&nbsp;</td>
    <td><span id="ttlqty"> <%=ttlQty%>&nbsp;&nbsp;</span></td>
    <td>cts&nbsp;&nbsp;</td>
    <td><span id="ttlcts"><%=ttlCts%>&nbsp;&nbsp;</span></td>
    <td>Vlu&nbsp;&nbsp;</td>
    <td><span id="grandttlvlu"><%=grandttlVlu%>&nbsp;&nbsp;</span></td>
    <td>Selected:&nbsp;&nbsp;</td>
    <td> Total :&nbsp;&nbsp;</td> 
    <td><span id="qtyTtl"><%=ttlQty%>&nbsp;&nbsp;</span></td>
    <td>Cts&nbsp;&nbsp;</td><td><span id="ctsTtl"><%=ttlCts%></span> </td>
    <td>Vlu&nbsp;&nbsp;</td><td><span id="grandttlVluTtl"><%=grandttlVlu%></span> </td>
    </tr></table></td></tr>
    <tr>
    <td valign="top" class="hedPg">
    <table class="grid1">
    <tr>
    <th><input type="checkbox" name="checkAll" id="checkAll" checked="checked" onclick="checkALLPerforma('CHK_','count')" /> </th>
    <th>Sr No.</th>
    <th>Idn</th>
    <%for(int j=0; j < perFormInvLstsz; j++) {
    String lprp=(String)perFormInvLst.get(j);%>
    <th><%=lprp%></th>
    <%}%>
    <th>Qty</th><th>Cts</th><th>Rate</th><th>Amount</th>
    </tr>
    <%for(int i=0;i<pktList.size();i++){
  HashMap pktDtl = (HashMap)pktList.get(i);
  String stkIdn = (String)pktDtl.get("stkidn");
  String idn = (String)pktDtl.get("idn");
  sr=i+1;
 String checkFldId = "CHK_"+sr;
 String checkFldVal = "value("+stkIdn+")";
 String cts=util.nvl((String)pktDtl.get("cts"));
 String vlu=util.nvl((String)pktDtl.get("vlu"));
 String onclick = "PerformaTotalCal(this,"+cts+",'"+vlu+"','"+stkIdn+"')";
  %>
  <tr>
  <td><html:checkbox property="<%=checkFldVal%>" styleId="<%=checkFldId%>" name="saleDeliveryForm" onclick="<%=onclick%>" value="yes" /> </td>
  <td><%=sr%></td>
  <td><%=idn%></td>
  <%for(int j=0; j < perFormInvLstsz; j++) {
   String prp = (String)perFormInvLst.get(j);
   prp=util.nvl((String)pktDtl.get(prp));
   if(cnt.equals("kj")){
   if(prp.indexOf("+") > -1){
    prp=prp.replaceAll("\\+"," ");
   }
   
   if(prp.indexOf("-") > -1){
    prp=prp.replaceAll("\\-"," ");
   }}
  %>
  <td><%=prp%></td>
  <%}%>
    <td align="right"><%=util.nvl((String)pktDtl.get("qty"))%></td>
  <td align="right"><%=cts%></td>

  <td align="right"><%=util.nvl((String)pktDtl.get("quot"))%></td>
  <td align="right"><%=vlu%></td>
  </tr>
 <%}%>
  <%if(chargeLst!=null && chargeLst.size()>0){
 for(int k=0; k < chargeLstsz; k++) {
 HashMap charData=new HashMap();
 charData=(HashMap)chargeLst.get(k);
 String charge=util.nvl((String)charData.get("CHARGE"));
 String chargetyp=util.nvl((String)charData.get("TYP"));
 String rmk=util.nvl((String)charData.get("RMK"));
 if(rmk.length()>0){
 rmk="("+rmk+")";
 }
 sr=sr+1;
 String checkFldVal = "value("+chargetyp+")";
 String checkFldId = "CHK_"+sr;
 String onclick = "PerformachargeCal(this,'"+charge+"')";
 %>
<tr><td colspan="<%=(perFormInvLstsz+5)%>" style="border-bottom: 1px solid white;">
<html:checkbox property="<%=checkFldVal%>" styleId="<%=checkFldId%>" name="saleDeliveryForm" onclick="<%=onclick%>" value="yes" />
<%=util.nvl((String)charData.get("DSC"))%><%=rmk%></td>
<td align="right"></td><td align="right">
<b><%=charge%></b></td>
</tr>
<%}}%>
<tr>
<td colspan="<%=(perFormInvLstsz+7)%>" align="right"><%=grandttlVlu%></td>
</tr>
    </table>
    <input type="hidden" name="count" id="count" value="<%=sr%>" />
    </td>
    </tr>
    <tr><td valign="top" class="hedPg"><html:submit property="value(perInvPkt)" value="Performa Invoice" styleClass="submit"/>&nbsp;</td></tr>
    </html:form>
    </table>
    </td></tr>
    <%}else{%>
    <tr><td valign="top" class="hedPg">
    <%if(msg.equals("")){%>
  Please select pakets...
  <%}else{%>
  <%=msg%>
  <%}%>
  </td></tr>
    <%}}%>
    
    <tr>
    <td><jsp:include page="/footer.jsp" /> </td>
    </tr>
    </table>
    
    <%@include file="../calendar.jsp"%>
  <%if(!performaLink.equals("")){%>
 <script type="text/javascript">
 <!--
 var url = document.getElementById('rptUrl').value;
 windowOpen(url,'_blank')
 -->
 </script>
 <%}%>
  </body>
</html>
