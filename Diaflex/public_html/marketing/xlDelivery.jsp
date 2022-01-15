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
    <title>XL Delivery</title>
    <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/bse.css?v=<%=info.getJsVersion()%>"/>
    <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/style.css?v=<%=info.getJsVersion()%>"/>
    <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/memoScript.js?v=<%=info.getJsVersion()%> " > </script>
    <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/bse.js?v=<%=info.getJsVersion()%> " > </script>
    <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/assortScript.js?v=<%=info.getJsVersion()%> " > </script>
    <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/Validation.js " > </script>
    <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/priceChange.js?v=<%=info.getJsVersion()%> " > </script>
    <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/ajax.js?v=<%=info.getJsVersion()%> " > </script>
  </head>
<%
HashMap dbinfo = info.getDmbsInfoLst();
String cnt = (String)dbinfo.get("CNT");
String repPath = (String)dbinfo.get("REP_PATH");
int accessidn=Integer.parseInt(util.nvl((String)request.getAttribute("accessidn"),"0"));
        String logId=String.valueOf(info.getLogId());
        String onfocus="cook('"+logId+"')";
         HashMap allPageDtl= (info.getPageDetails() == null)?new HashMap():(HashMap)info.getPageDetails();
    HashMap pageDtl=(HashMap)allPageDtl.get("XLDELIVERY_CONFIRMATION");
    ArrayList pageList=new ArrayList();
    HashMap pageDtlMap=new HashMap();
    String fld_nme="",fld_ttl="",val_cond="",dflt_val="",fld_typ="",form_nme="",lov_qry="";
    HashMap prpList = info.getPrp();
    HashMap mprpList = info.getMprp();
        HashMap setcharge = (request.getAttribute("setcharge") == null)?new HashMap():(HashMap)request.getAttribute("setcharge");
        %>
 <body onfocus="<%=onfocus%>">
 
  <% pageList=(ArrayList)pageDtl.get("MEMODTL");
       if(pageList!=null && pageList.size() >0){%>
   <select id="memoDtl" name="memoDtl" style="display:none">
    <%   for(int j=0;j<pageList.size();j++){
            pageDtlMap=(HashMap)pageList.get(j);
            dflt_val=(String)pageDtlMap.get("dflt_val");
           fld_ttl=(String)pageDtlMap.get("fld_ttl");%>
       <option value="<%=dflt_val%>"><%=fld_ttl%></option>     
    <% }%>
    </select>
    <%}else{ %>
      <select id="memoDtl" name="memoDtl" style="display:none">
       <option value="id">Memo Id</option>     
        <option value="dte">Date</option>     
      </select>
    <%}%>
<input type="hidden" name="CNT" id="CNT" value="<%=cnt%>" />
<input type="hidden" name="REQURL" id="REQURL" value="<%=info.getReqUrl()%>" />
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
  <img src="../images/line.jpg" border="0" /></td><td valign="middle"> <span class="pgHed">Sales Delivery</span> </td>
</tr></table></td></tr>
<tr><td valign="top" class="tdLayout">
  <table cellpadding="2" cellspacing="2" >
  <%
   String vnmNtFnd = (String)request.getAttribute("vnmNotFnd");
     if(vnmNtFnd!=null){%>
    <tr><td valign="top" class="tdLayout"><span class="redLabel"><%=vnmNtFnd%></span></td></tr>
   <%}%>
  <%
        if(request.getAttribute("error")!=null){%>
        <tr><td height="15"  valign="top" class="tdLayout"><span class="redLabel"> <%=request.getAttribute("error")%></span></td></tr>
       <% }
        %>
        <% if(request.getAttribute("PRMSG")!=null){%>
        <tr><td height="15"  valign="top" class="tdLayout"><span class="redLabel"> <%=request.getAttribute("PRMSG")%></span></td></tr>
       <% }
        %>
        <% if(request.getAttribute("RTMSG")!=null){%>
        <tr><td height="15"  valign="top" class="tdLayout"><span class="redLabel"> <%=request.getAttribute("RTMSG")%></span></td></tr>
       <% }
        %>
        <% if(request.getAttribute("CANMSG")!=null){%>
        <tr><td height="15"  valign="top" class="tdLayout"><span class="redLabel"> <%=request.getAttribute("CANMSG")%></span></td></tr>
       <% }
        %>
         <% if(request.getAttribute("ISMSG")!=null){%>
        <tr><td height="15"  valign="top" class="tdLayout"><span class="redLabel"> <%=request.getAttribute("ISMSG")%></span></td></tr>
       <% }
        %>
        <% if(request.getAttribute("DLVMSG")!=null){
         String url =repPath+"/reports/rwservlet?"+cnt+"&report="+info.getRportUrl()+"\\reports\\sale_dlv_rep.jsp&p_dlv_idn="+request.getAttribute("dlvIdDLV")+"&destype=CACHE&desformat=pdf&p_access="+accessidn;
        %>
        <tr><td valign="top" class="tdLayout">
        <input type="hidden" name="rptUrl" id="rptUrl" value="<%=url%>" />
        <span class="redLabel"> <%=request.getAttribute("DLVMSG")%></span></td></tr>
      
       <tr><td class="tdLayout">
       <table><tr><td>
       <div onclick="displayDiv('refDiv')"> <a href="<%=url%>"  target="_blank"><span class="txtLink" >Click Here for Report</span></a> </div></td>
        <%if(cnt.equals("xljf")){
        String url1 =repPath+"/reports/rwservlet?"+cnt+"&report="+info.getRportUrl()+"\\reports\\inv_report.jsp&p_access="+accessidn+"&p_dlv_idn="+request.getAttribute("dlvIdDLV")+"&destype=CACHE&desformat=pdf";
        String url2 =repPath+"/reports/rwservlet?"+cnt+"&report="+info.getRportUrl()+"\\reports\\Ind_Export_invoice_sale.jsp&p_access="+accessidn+"&p_dlv_idn="+request.getAttribute("dlvIdDLV")+"&destype=CACHE&desformat=pdf";
        String url3 =repPath+"/reports/rwservlet?"+cnt+"&report="+info.getRportUrl()+"\\reports\\Hk_Export_invoice_sale.jsp&p_access="+accessidn+"&p_dlv_idn="+request.getAttribute("dlvIdDLV")+"&destype=CACHE&desformat=pdf";
        String url4 =repPath+"/reports/rwservlet?"+cnt+"&report="+info.getRportUrl()+"\\reports\\Ind_performa_invoice_sale.jsp&p_access="+accessidn+"&p_dlv_idn="+request.getAttribute("dlvIdDLV")+"&destype=CACHE&desformat=pdf";
        String url5 =repPath+"/reports/rwservlet?"+cnt+"&report="+info.getRportUrl()+"\\reports\\HK_performa_invoice_sale.jsp&p_access="+accessidn+"&p_dlv_idn="+request.getAttribute("dlvIdDLV")+"&destype=CACHE&desformat=pdf";
        String url6 =repPath+"/reports/rwservlet?"+cnt+"&report="+info.getRportUrl()+"\\reports\\HK_Performa_Invoice_sale.jsp&p_access="+accessidn+"&P_DLV_SL="+request.getAttribute("dlvIdDLV")+"&destype=CACHE&desformat=pdf";
        %>
        <td><a href="<%=url1%>"  target="_blank"><span class="txtLink" >Print Report</span></a></td>
        <td><a href="<%=url2%>"  target="_blank"><span class="txtLink" >India Invoice sale post</span></a></td>
        <td><a href="<%=url3%>"  target="_blank"><span class="txtLink" >HK Invoice Sale post</span></a></td>
        <td><a href="<%=url4%>"  target="_blank"><span class="txtLink" >India_Proforma Invoice</span></a></td>
        <td><a href="<%=url5%>"  target="_blank"><span class="txtLink" >HK Proforma</span></a></td>
        <td><a href="<%=url6%>"  target="_blank"><span class="txtLink" >HK Proforma Inv for Partial Dlv
</span></a></td>
        
        <%}%>
        </tr></table>
       </td></tr>
       <% }
       String performaLinkDLV=(String)request.getAttribute("performaLinkDLV");
       System.out.println(performaLinkDLV);
        if(performaLinkDLV!=null){
        String url=info.getReqUrl()+"/marketing/performa.do?method=perInv&idn="+request.getAttribute("dlvIdDLV")+"&form=Y&typ=DLV&pktTyp=SINGLE";%>
        <tr><td height="15"  valign="top" class="tdLayout"><a href="<%=url%>"  target="_blank"><span class="txtLink" >Performa Invoice</span></a></td></tr>
       <% }%>
  </table>
  </td>
  </tr>
 <tr><td valign="top" class="tdLayout">
  <table>
  <tr><td><table><tr><td  valign="top">
  <html:form action="/marketing/xlSaleDelivery.do?method=loadPkt" method="POST" onsubmit="return validate_saleXl()" >
<table cellpadding="0" cellspacing="0" border="0"><tr><td valign="top">
<table class="grid1" >
<tr><th colspan="2">Sale Search </th></tr>
<tr><td>User Location</td>
<td><html:select property="value(usrLct)" onchange="userLocationXL(this,'DLV')" name="saleDeliveryForm" >
<html:option value="0">----select----</html:option>
<html:option value="IND">INDIA</html:option><html:option value="HK">HK</html:option>
</html:select>
</td></tr>
<tr>
<td>Buyer Name :</td>
<td>
<html:select property="byrIdn" name="saleDeliveryForm" onchange="getFinalByrXL(this,'SL')" styleId="byrId" >
<html:option value="0">---select---</html:option>
<html:optionsCollection property="byrLst" name="saleDeliveryForm" value="byrIdn" label="byrNme" />

</html:select>

</td></tr>
<tr>
<td>Billing Party :</td><td>
<html:select property="prtyIdn" name="saleDeliveryForm" onchange="getSaleTrmsXL(this.value,'SL')" styleId="prtyId" >
<html:option value="0">---select---</html:option>
</html:select>
</td>
</tr>
<tr>
<td>Terms :</td><td>
<html:select property="relIdn" name="saleDeliveryForm" onchange="getSaleIdnXL('SL')" styleId="rlnId" >
<html:option value="0">---select---</html:option>
</html:select>

</td>
</tr>

<tr><td colspan="2">OR </td></tr>
<tr><td>Sale Idns</td><td>
<html:text property="value(saleIdn)" name="saleDeliveryForm" styleId="saleIdn"/>
</td></tr>
<tr><td>Packets. </td><td>
<html:textarea property="value(vnmLst)" name="saleDeliveryForm" styleId="vnmLst" /> </td> </tr>
<tr><td colspan="2"><table><tr><td>Type :</td><td>
<html:select property="value(typ)" name="saleDeliveryForm">
<html:option value="ALL">ALL</html:option>
<html:option value="SL">Sale</html:option>
<html:option value="PR">Payment Recevice</html:option>
</html:select>
</td></tr></table> </td></tr>
<tr><td colspan="2"><html:submit property="submit" value="View" styleClass="submit"/></td></tr>
</table>
</td><td valign="top">
<div id="memoIdn"></div>
</td></tr></table>
</html:form>
  </td><td  valign="top">
     <%if(request.getAttribute("view")!=null ){%>
    <table class="grid1">
    <tr> <td></td><td><b>SL</b></td><td><b>PR</b></td><td><b>DWP</b></td><td><b>DLV</b></td><td><b>RT</b></td><td><b>CL</b></td> </tr>
    <tr> <td><b>Qty</b></td> <td><span id="sl_qty"><bean:write property="value(sl_qty)" name="saleDeliveryForm" /></span></td><td><span id="pr_qty"><bean:write property="value(pr_qty)" name="saleDeliveryForm" /></span></td><td><span id="dwp_qty">0</span></td><td><span id="dlv_qty">0</span></td> <td><span id="rt_qty">0</span></td><td><span id="cl_qty">0</span></td></tr>
    <tr> <td><b>Cts</b> </td> <td><span id="sl_cts"><bean:write property="value(sl_cts)"  name="saleDeliveryForm" /></span></td> <td><span id="pr_cts"><bean:write property="value(pr_cts)"  name="saleDeliveryForm" /></span></td><td><span id="dwp_cts">0</span></td><td><span id="dlv_cts">0</span></td> <td><span id="rt_cts">0</span></td><td><span id="cl_cts">0</span></td> </tr>
    <tr> <td><b>Avg Prc</b></td> <td><span id="sl_avgPrc"><bean:write property="value(sl_avgPrc)"  name="saleDeliveryForm" /></span></td><td><span id="pr_avgPrc"><bean:write property="value(pr_avgPrc)"  name="saleDeliveryForm" /></span></td><td><span id="dwp_avgPrc">0</span></td> <td><span id="dlv_avgPrc">0</span></td> <td><span id="rt_avgPrc">0</span></td><td><span id="cl_avgPrc">0</span></td> </tr>
    <tr> <td><b>Avg Dis</b> </td> <td><span id="sl_avgDis"><bean:write property="value(sl_avgDis)"  name="saleDeliveryForm" /></span></td> <td><span id="pr_avgDis"><bean:write property="value(pr_avgDis)"  name="saleDeliveryForm" /></span></td><td><span id="dwp_avgDis">0</span></td><td><span id="dlv_avgDis">0</span></td><td><span id="rt_avgDis">0</span></td> <td><span id="cl_avgDis">0</span></td>   </tr>
    <tr> <td><b>Value </b></td> <td><span id="sl_vlu"><bean:write property="value(sl_vlu)"   name="saleDeliveryForm" /></span></td><td><span id="pr_vlu"><bean:write property="value(pr_vlu)"   name="saleDeliveryForm" /></span></td><td><span id="dwp_vlu">0</span></td><td><span id="dlv_vlu">0</span></td> <td><span id="rt_vlu">0</span></td><td><span id="cl_vlu">0</span></td>  </tr>
  <tr><td><b>Buyer</b></td><td colspan="6"><bean:write property="byr" name="saleDeliveryForm" /></td></tr>
   <tr><td><b>Date</b></td><td colspan="6"><bean:write property="dte" name="saleDeliveryForm" /></td></tr>
   <tr><td><b>Type</b></td><td colspan="6"><bean:write property="typ" name="saleDeliveryForm" /></td></tr>
      </table>
    <%}%>
    </td>
      <td  valign="top">
     <%if(request.getAttribute("view")!=null){
     ArrayList chargesLst=(ArrayList)session.getAttribute("chargesLst");
     if(chargesLst!=null && chargesLst.size()>0){%>
    <table class="grid1">
    <tr>
    <td colspan="5">
    <span class="redLabel">*Uncheck if don't want to apply</span>
    </td>
    </tr>
    <tr><th>Disc Type</th><th>Sale Charge</th><th colspan="3"></th></tr>
    <%for(int i=0;i<chargesLst.size();i++){
    HashMap dtl=new HashMap();
    dtl=(HashMap)chargesLst.get(i);
    String dsc=(String)dtl.get("dsc");
    String autoopt=(String)dtl.get("autoopt");
    String flg=(String)dtl.get("flg");
    String typ=(String)dtl.get("typ");
    String fctr=(String)dtl.get("fctr");
    String fun=(String)dtl.get("fun");
    String rmk=(String)dtl.get("rmk");
    String field = "value("+typ+")";
    String salCharge = "value("+typ+"_TTL)";
    String fieldRmk = "value("+typ+"_rmk)";
    String onchang="chargesmanualXlDelivery('"+typ+"','"+i+"')";
    String onchangrmk="chargesmanualrmk('"+typ+"','"+i+"')";
    String fieldId = typ+"_rmk";
    if(flg.equals("MNL")){
    String charges=util.nvl((String)setcharge.get(typ));
    %>
    <tr><td nowrap="nowrap"><b><%=dsc%></b></td>
     <td nowrap="nowrap"><b><span id="<%=typ%>_dis"></span></b></td>
     <td nowrap="nowrap"><bean:write property="<%=salCharge%>" name="saleDeliveryForm" /></td>
    <td nowrap="nowrap"><html:text property="<%=field%>" size="6" styleId="<%=typ%>" onchange="<%=onchang%>" value="<%=charges%>" name="saleDeliveryForm"/></td>
    <td nowrap="nowrap">
    <%if(rmk.equals("Y")){%>
    Remark<html:text property="<%=fieldRmk%>" styleId="<%=fieldId%>"  onchange="<%=onchangrmk%>" name="saleDeliveryForm" size="10"/>
    <%}%>
    </td>
    </tr>
    <%}else{%>
    <tr><td nowrap="nowrap"><b><%=dsc%></b>
    <%if(flg.equals("AUTO") && autoopt.equals("OPT")){
    String chk="checked=\"checked\"";
    String charges=util.nvl((String)setcharge.get(typ));
    if(!charges.equals(""))
    chk="";%>
    <input type="checkbox" name="<%=typ%>_AUTO" id="<%=typ%>_AUTO" <%=chk%>  onchange="validateAutoOpt('<%=typ%>_AUTO')" title="Uncheck To Optional"/>
    <%}%>
    </td>
      <td nowrap="nowrap"><bean:write property="<%=salCharge%>" name="saleDeliveryForm" /></td>
    <td nowrap="nowrap"><b><span id="<%=typ%>_dis"></span></b></td>
    
    <td nowrap="nowrap">
    <html:hidden property="<%=field%>"  styleId="<%=typ%>" name="saleDeliveryForm"/>
    <!--<input type="button" name="charge_<%=typ%>" id="charge_<%=typ%>" value="Charge"  onclick="<%=onchang%>" class="submit" />-->
    </td><td nowrap="nowrap"></td></tr>
    <%}}%>
    <tr><td nowrap="nowrap"><b>Total(DWP)</b></td>
    <td nowrap="nowrap"><b><span id="IS_net_dis"></span></b></td>
    <td nowrap="nowrap"><b>Total(DLV)</b></td>
    <td nowrap="nowrap"><b><span id="DLV_net_dis"></span></b></td>
    </tr>
    </table>
    <%}}%>
    </td>
  </tr>
  
  
  
  
  </table>
  </td> </tr>
 
  </table>
</td></tr>
        <tr><td valign="top" class="hedPg"><table>
   
      <html:form action="/marketing/xlSaleDelivery.do?method=save" method="POST"  onsubmit="return confirmChanges()">
   <% if(request.getAttribute("view")!=null){
    ArrayList prpDspBlocked = info.getPageblockList();
     ArrayList pkts = (ArrayList)info.getValue("PKTS");
     HashMap avgdtl=(HashMap)request.getAttribute("avgdtl");
   if(pkts!=null && pkts.size()>0){
    ArrayList itemHdr = new ArrayList();
    ArrayList pktList = new ArrayList();
    HashMap pktPrpMap = new HashMap();
    String aadatcommdisplay="display:none",brk1commdisplay="display:none",brk2commdisplay="display:none",brk3commdisplay="display:none",brk4commdisplay="display:none";
   if(request.getAttribute("view")!=null ){
    ArrayList chargesLst=(ArrayList)session.getAttribute("chargesLst");
    if(chargesLst!=null && chargesLst.size()>0){
    for(int i=0;i<chargesLst.size();i++){
    HashMap dtl=new HashMap();
    dtl=(HashMap)chargesLst.get(i);
    String dsc=(String)dtl.get("dsc");
    String autoopt=(String)dtl.get("autoopt");
    String flg=(String)dtl.get("flg");
    String typ=(String)dtl.get("typ");
    String fctr=(String)dtl.get("fctr");
    String fun=(String)dtl.get("fun");
    String rmk=(String)dtl.get("rmk");
    String field = "value("+typ+"_save)";
    String fieldRmk = "value("+typ+"_rmksave)";
    String fieldId = typ+"_save";
    String fieldIdrmk = typ+"_rmksave";
    String fieldauto = "value("+typ+"_AUTOOPT)";
    String fieldIdauto = typ+"_AUTOOPT";
    String chargesval="";
    if(flg.equals("MNL"))
    chargesval=util.nvl((String)setcharge.get(typ));
    %>
    <html:hidden property="<%=field%>" name="saleDeliveryForm" styleId="<%=fieldId%>" value="<%=chargesval%>" />
   <%if(rmk.equals("Y")){%>
    <html:hidden property="<%=fieldRmk%>" name="saleDeliveryForm" styleId="<%=fieldIdrmk%>"  />
    <%}
    if(flg.equals("AUTO") && autoopt.equals("OPT")){
    String chk="N";
    String charges=util.nvl((String)setcharge.get(typ));
    if(!charges.equals(""))
    chk="Y";%>
    <html:hidden property="<%=fieldauto%>" name="saleDeliveryForm" styleId="<%=fieldIdauto%>" value="<%=chk%>"   />
    <%}
    }%>
   <html:hidden property="value(DLV_vluamt)" name="saleDeliveryForm" styleId="DLV_vluamt"  />
   <html:hidden property="value(IS_vluamt)" name="saleDeliveryForm" styleId="IS_vluamt"  />
    <%}}
    %>
   <tr><td>
    <html:hidden property="value(exhRte)"  styleId="exhRte" name="saleDeliveryForm" />
   <table>
            
                <tr>
                <td><span class="pgTtl" >Exiting Selection</span></td>
                <td> <b>Terms : </b> </span></td><td>
                <bean:write property="value(trmsLb)" name="saleDeliveryForm" />
                <html:hidden property="relIdn" name="saleDeliveryForm" />
               
                </td>
                 <td> <b>Buyer :</b> </span></td><td>
                <bean:write property="byr" name="saleDeliveryForm" />
                 <html:hidden property="nmeIdn" name="saleDeliveryForm"  styleId="nmeIdn"  />
                </td>
          <td><b>Email :</b></td> <td> <bean:write property="value(email)" name="saleDeliveryForm"/> </td>
          <td><b>Mobile No :</b></td> <td> <bean:write property="value(mobile)" name="saleDeliveryForm"/> </td>
          <td><b>Office No :</b></td> <td> <bean:write property="value(office)" name="saleDeliveryForm"/> </td>
           <%ArrayList prpValLst = (ArrayList)prpList.get("SL_TYPV");
           if(prpValLst!=null && prpValLst.size()>0){
           %>
           <td><b>Sale Type:</b></td><td>
           <html:select property="value(sale_typ)" styleId="saleTyp" name="saleDeliveryForm" >
           <html:option value="">---select sale type---</html:option>
           <%for(int k=0;k<prpValLst.size();k++){ 
           String lprpVal = (String)prpValLst.get(k);
           %>
           <html:option value="<%=lprpVal%>"><%=lprpVal%></html:option>
           <%}%>
           </html:select>
           <input type="hidden" name="isSalTyp" id="isSalTyp" value="Y"  />
           </td><%}else{%><td><input type="hidden" name="isSalTyp" id="isSalTyp" value="N"  /></td><%}%>
                
           </tr></table>
   
   </td></tr>
  
   <tr><td>
            <table>
                <tr>
               
            <td><span class="pgTtl" >Buyer List</span></td>
                <td>
                <html:select property="value(invByrIdn)" name="saleDeliveryForm"  styleId="byrId1" onchange="GetADDR();GetBank()" >
               <html:option value="0" >---select---</html:option>
                <html:optionsCollection property="invByrLst" name="saleDeliveryForm"  value="byrIdn" label="byrNme" />
                
                </html:select>
                </td>  <td><span class="pgTtl" >Address</span></td>
                <td><html:select property="value(invAddr)" styleId="addrId" name="saleDeliveryForm"  >
                
                 <html:optionsCollection property="invAddLst" name="saleDeliveryForm"   value="idn" label="addr" />
                </html:select>
                
               </td>
              
                <td><span class="pgTtl" >Terms </span></td><td>
             
             <html:select property="value(invByrTrm)" name="saleDeliveryForm"  styleId="rlnId1" onchange="invTermsDtls();"  >
         
             <html:optionsCollection property="invTrmsLst" name="saleDeliveryForm"
            label="trmDtl" value="relId" />
            
            </html:select>
            </td>
            </tr></table></td></tr>
           
            <tr><td><table>
            <tr>
            <td nowrap="nowrap"><span class="pgTtl" >Company Name</span></td>
          <td>
           <html:select property="value(grpIdn)" name="saleDeliveryForm"  styleId="grpIdn"  >
             <html:optionsCollection property="groupList" name="saleDeliveryForm"
             value="idn" label="addr" />
            </html:select>
          </td>
                <td nowrap="nowrap"><span class="pgTtl" >Bank Selection</span></td>
               <td>
             
             <html:select property="value(bankIdn)" name="saleDeliveryForm" onchange="SetBankAddr(this)"  styleId="bankIdn"  >
             <html:optionsCollection property="bankList" name="saleDeliveryForm"
             value="idn" label="addr" />
            
            </html:select>
            </td>
            <td> <html:select property="value(bankAddr)" name="saleDeliveryForm" style="dispaly:none" styleId="bankAddr">
             <html:optionsCollection property="bnkAddrList" name="saleDeliveryForm"
             value="idn" label="addr" />
            </html:select>
            
            </td>
            </tr>
            
            </table></td>
            
            </tr>
       
            <tr>
            <td nowrap="nowrap"><table><tr><td><span class="pgTtl" >Courier :</span> </td>
             <td> <html:select property="value(courier)" name="saleDeliveryForm" style="dispaly:none" styleId="courier">
            <html:optionsCollection property="courierList" name="saleDeliveryForm"
             value="idn" label="addr" />
            </html:select>
            </td>
            <%if(cnt.equalsIgnoreCase("hk")){%>
            <td nowrap="nowrap"><span class="pgTtl" >Bank through :</span></td><td>
            <html:select property="value(throubnk)" name="saleDeliveryForm"  styleId="throubnk">
            <html:option value="0">-----select bank through-----</html:option>
            <html:optionsCollection property="thruBankList" name="saleDeliveryForm"
             value="idn" label="fnme" />
            </html:select>
            </td><%}%>
            </tr></table> </td>
            </tr>
            <tr>
            <td><table><tr>
            <%
            pageList=(ArrayList)pageDtl.get("ISAADAT");
            String ISAADAT = "N";
            boolean isdisabled= true;
            if(pageList!=null && pageList.size() >0){
             pageDtlMap=(HashMap)pageList.get(0);
             ISAADAT=util.nvl((String)pageDtlMap.get("fld_nme"),"N");
             if(ISAADAT.equals("Y"))
              isdisabled= false;
            }
            %>
            <td><span class="pgTtl" >Commission :</span> </td>
            <html:hidden property="value(aadatcommval)" styleId="aadatcommval" name="saleDeliveryForm" value="0"/>
            <html:hidden property="value(brk1commval)" styleId="brk1commval" name="saleDeliveryForm" value="0"/>
            <html:hidden property="value(brk2commval)" styleId="brk2commval"  name="saleDeliveryForm" value="0"/>
            <html:hidden property="value(brk3commval)" styleId="brk3commval"  name="saleDeliveryForm" value="0"/>
            <html:hidden property="value(brk4commval)" styleId="brk4commval"  name="saleDeliveryForm" value="0"/>
                        <logic:present property="value(aadatcomm)" name="saleDeliveryForm" >
            <%aadatcommdisplay="display:block";%>
            </logic:present>
            <td nowrap="nowrap">
            <div style="<%=aadatcommdisplay%>" id="aadatcommdisplay">
            <table>
            <tr>
            <td><span class="pgTtl" >Aadat :</span> </td>
            <td>
            <span id="aaDatNme"><bean:write property="value(aaDat)" name="saleDeliveryForm" /></span> :
            <span id="aaDatComm"><bean:write property="value(aadatcomm)" name="saleDeliveryForm" /></span> </td>
            <td><html:radio property="value(aadatpaid)" onchange="setBrokerComm('aadatcomm','Y')" styleId="aadatpaid1"  value="Y"  name="saleDeliveryForm" disabled="<%=isdisabled%>"/> </td>
            <td>&nbsp;Yes</td> 
            <td><html:radio property="value(aadatpaid)"  onchange="setBrokerComm('aadatcomm','N')" styleId="aadatpaid2" value="N" name="saleDeliveryForm" disabled="<%=isdisabled%>"/></td>
            <td>&nbsp;No</td>
            <td><html:text property="value(aadatcomm)" styleId="aadatcomm" readonly="true" size="3" name="saleDeliveryForm" /> </td>
            </tr>
            </table>
            </div>
            </td>
            <logic:present property="value(brk1comm)" name="saleDeliveryForm" >
            <%brk1commdisplay="display:block";%>
           </logic:present>
           <td nowrap="nowrap">
           <div  style="<%=brk1commdisplay%>" id="brk1commdisplay">
           <table>
           <tr>
           <td><span class="pgTtl" >Broker :</span> </td>
           <td id="brk1Nme"><bean:write property="value(brk1)" name="saleDeliveryForm" /></td><td><html:radio property="value(brk1paid)" styleId="brk1paid1" value="Y" name="saleDeliveryForm" disabled="<%=isdisabled%>"/></td><td>&nbsp;Yes</td>  <td><html:radio property="value(brk2paid)"  styleId="brk1paid2"  value="N" name="saleDeliveryForm" disabled="<%=isdisabled%>"/></td><td>&nbsp;No</td> <td><html:text property="value(brk1comm)"  styleId="brk1comm"  readonly="true" size="3" name="saleDeliveryForm" /> </td>
           </tr>
           </table>
           </div>
           </td>
            <logic:present property="value(brk2comm)" name="saleDeliveryForm" >
            <%brk2commdisplay="display:block";%>
            </logic:present>
           <td  nowrap="nowrap">
           <div  style="<%=brk2commdisplay%>" id="brk2commdisplay">
           <table>
           <tr>
           <td id="brk2Nme"><bean:write property="value(brk2)" name="saleDeliveryForm" /></td><td><html:radio property="value(brk2paid)"  styleId="brk2paid1" value="Y" name="saleDeliveryForm" disabled="<%=isdisabled%>"/> </td><td>&nbsp;Yes</td> <td><html:radio property="value(brk2paid)"  styleId="brk2paid2"   value="N" name="saleDeliveryForm" disabled="<%=isdisabled%>"/></td><td> &nbsp;No</td> <td><html:text property="value(brk2comm)" styleId="brk2comm" readonly="true" size="3" name="saleDeliveryForm" /> </td>
           </tr>
           </table>
           </div>
           </td>
           <logic:present property="value(brk3comm)" name="saleDeliveryForm" >
           <%brk3commdisplay="display:block";%> 
           </logic:present>
           <td  nowrap="nowrap">
           <div style="<%=brk3commdisplay%>" id="brk3commdisplay">
           <table>
           <tr>
           <td id="brk3Nme"><bean:write property="value(brk3)" name="saleDeliveryForm" /></td><td><html:radio property="value(brk3paid)"   styleId="brk3paid1" value="Y" name="saleDeliveryForm" disabled="<%=isdisabled%>"/> </td><td>&nbsp;Yes</td> <td><html:radio property="value(brk3paid)"  styleId="brk3paid2" value="N" name="saleDeliveryForm" disabled="<%=isdisabled%>"/></td><td> &nbsp;No</td>  <td><html:text property="value(brk3comm)" styleId="brk3comm" readonly="true" size="3" name="saleDeliveryForm" /> </td>
           </tr>
           </table>
           </div>
           </td>
            <logic:present property="value(brk4comm)" name="saleDeliveryForm" >
           <%brk4commdisplay="display:block";%>  
           </logic:present>
           <td  nowrap="nowrap">
           <div style="<%=brk4commdisplay%>" id="brk4commdisplay">
           <table>
           <tr>
           <td id="brk4Nme"><bean:write property="value(brk4)" name="saleDeliveryForm" /></td><td><html:radio property="value(brk4paid)"   styleId="brk4paid1"  value="Y" name="saleDeliveryForm" disabled="<%=isdisabled%>"/> </td><td>&nbsp;Yes</td> <td><html:radio property="value(brk4paid)"   styleId="brk4paid2" value="N" name="saleDeliveryForm" disabled="<%=isdisabled%>"/></td><td> &nbsp;No</td> <td><html:text property="value(brk4comm)" styleId="brk4comm" readonly="true" size="3" name="saleDeliveryForm" /> </td>
           </tr>
           </table>
           </div>
           </td>
            </tr></table> </td>
            </tr>
            <!--<tr>
            <td><div id="totalDiv">
            <table>
            <tr><td><span class="pgTtl" >Delivery Selection</span></td><td>Pcs:</td><td><b> <label id="ttlqty"></label></b></td>
            <td>Cts:</td><td><b><label id="ttlcts"></label></b></td>
            <td>Discount:</td><td><b><label id="ttldis"></label></b></td>
           <td>Value:</td><td><b><label id="vlu"></label></b></td>
            
            <%        
            pageList=(ArrayList)pageDtl.get("LABEL");
            if(pageList!=null && pageList.size() >0){
            for(int j=0;j<pageList.size();j++){
            pageDtlMap=(HashMap)pageList.get(j);
            fld_nme="value("+(String)pageDtlMap.get("fld_nme")+")";
            fld_ttl=(String)pageDtlMap.get("fld_ttl");
            fld_typ=(String)pageDtlMap.get("fld_typ");
            dflt_val=(String)pageDtlMap.get("dflt_val");
            val_cond=(String)pageDtlMap.get("val_cond"); 
        %>    
            <td>Inc Value:</td><td><b><label id="<%=fld_typ%>"></label></b></td>
                
        <%  
            }}
        %>
            
            
            
            </tr>
            </table>
            </div></td>
            </tr>-->
           
 <!--<tr><td><span class="pgTtl" >Extra Charge : </span><input type="text" id="echarge" name="echarge" onchange="isNumericDecimal(this.id)"/></td></tr>-->
  <tr><td>
            <label class="pgTtl">Sale Packets</label>
            Create Excel <img src="../images/ico_file_excel.png" onclick="goTo('<%=info.getReqUrl()%>/marketing/saleDelivery.do?method=createXL','','')" />
            <%
                ArrayList prps = (info.getValue("MDL_MEMO_RTRN") == null)?new ArrayList():(ArrayList)info.getValue("MDL_MEMO_RTRN");
             itemHdr.add("sale Id");itemHdr.add("Packet Code");
             %>  </td></tr>
               <tr><td>
            <table class="grid1">
                <tr>
                    <th>Sr</th>
                   
                    <th>Packet Code</th>
                     <%for(int j=0; j < prps.size(); j++) {
                     String lprp=(String)prps.get(j);
                     if(prpDspBlocked.contains(lprp)){
                     }else{
                     %>
                        <th><%=lprp%></th>
                    <%}}%>
                    <th>RapRte</th>
                    <th>Dis</th>
                    <th>Prc / Crt </th>
                    <th>ByrDis</th>
                    <th>Quot</th>
                       <%pageList=(ArrayList)pageDtl.get("RADIOHDR");
            if(pageList!=null && pageList.size() >0){
            for(int j=0;j<pageList.size();j++){
            pageDtlMap=(HashMap)pageList.get(j);
            fld_nme="value("+(String)pageDtlMap.get("fld_nme")+")";
            fld_ttl=(String)pageDtlMap.get("fld_ttl");
            fld_typ=(String)pageDtlMap.get("fld_typ");
            dflt_val=(String)pageDtlMap.get("dflt_val");
            val_cond=(String)pageDtlMap.get("val_cond"); %>
            <th><html:radio property="<%=fld_nme%>" styleId="<%=fld_typ%>" name="saleDeliveryForm" onclick="<%=val_cond%>"  value="<%=dflt_val%>"/><%=fld_ttl%> </th>
            <%}}%>
                </tr>
            <%
               
                //(info.getValue(reqIdn+ "_PKTS") == null)?new ArrayList():(ArrayList)info.getValue(reqIdn+ "_PKTS");
               int count = 0;
                for(int i=0; i < pkts.size(); i++) {%>
                <tr>
                <td><%=(i+1)%></td>
                <%    
                    count=i+1;
                    PktDtl pkt = (PktDtl)pkts.get(i);
                    long pktIdn = pkt.getPktIdn();
                    String cbPrp = "value(upd_" + pktIdn + ")";
                    String sttPrp = "value(stt_" + pktIdn + ")" ;
                    String rmkTxt =  "value(rmk_" + pktIdn + ")" ;
                   
                    String fnlsal=util.nvl(pkt.getMemoQuot(),pkt.getRte());
                   
                %>
               <td><%=pkt.getVnm()%><input type="hidden" id="STKIDN_<%=count%>" value="<%=pktIdn%>" /></td>
                <%for(int j=0; j < prps.size(); j++) {
                String lprp = (String)prps.get(j);
                if(prpDspBlocked.contains(lprp)){
                }else{
                %>
                <td><%=util.nvl(pkt.getValue((String)prps.get(j)))%>
                <%}if(lprp.equals("CRTWT")){%>
                <input type="hidden" id="<%=count%>_cts" value="<%=util.nvl(pkt.getValue((String)prps.get(j)))%>" /> 
                <%}%></td>
                <%}
                %>
                <td><%=pkt.getRapRte()%> <input type="hidden" id="<%=count%>_rap" value="<%=pkt.getRapRte()%>" /> </td>
                <td><%=pkt.getDis()%></td>
                <td><%=pkt.getRte()%>  <input type="hidden" id="<%=count%>_quot" value="<%=pkt.getRte()%>" /> 
                <input type="hidden" id="<%=count%>_fnl" value="<%=fnlsal%>" /> 
                </td>
                <td><%=pkt.getByrDis()%></td>
                <td><%=pkt.getMemoQuot()%></td>
            <%
            String lStt = pkt.getStt();
            pageList=(ArrayList)pageDtl.get("RADIOBODY");
            if(pageList!=null && pageList.size() >0){
            for(int j=0;j<pageList.size();j++){
            pageDtlMap=(HashMap)pageList.get(j);
            fld_nme="value("+(String)pageDtlMap.get("fld_nme")+""+pktIdn+")";
            fld_ttl=(String)pageDtlMap.get("fld_ttl");
            fld_typ=(String)pageDtlMap.get("fld_typ")+count;
            dflt_val=util.nvl((String)pageDtlMap.get("dflt_val")).trim();
            if(dflt_val.equals("PKT"))
            dflt_val=String.valueOf(pktIdn);
            dflt_val=dflt_val.replaceAll("PKTIDN",String.valueOf(pktIdn));
            val_cond=(String)pageDtlMap.get("val_cond");
            val_cond=val_cond.replaceAll("PKTIDN",String.valueOf(pktIdn));
            %>
            <td nowrap="nowrap">
            <%if(lStt.equals("IS") && dflt_val.indexOf("DLV")!=-1){%>
            <html:radio property="<%=fld_nme%>" styleId="<%=fld_typ%>" name="saleDeliveryForm" disabled="true"  onclick="<%=val_cond%>"  value="<%=dflt_val%>"/>
            <%}else{%>
             <html:radio property="<%=fld_nme%>" styleId="<%=fld_typ%>" name="saleDeliveryForm" onclick="<%=val_cond%>"  value="<%=dflt_val%>"/>
           
            <%}%>
            <%if(dflt_val.equals("RT")){%>
            <html:text property="<%=rmkTxt%>" size="10"  />
            <%}%>
            </td>
            <%}}%>
                </tr>
              <%  
                }
                
            %>
            <input type="hidden" id="rdCount" value="<%=count%>" />
            
            </table>
            </td></tr>
             <tr><td>
            <p>
                <!--<html:submit property="submit" value="Save" styleClass="submit"/>&nbsp;
                <html:button property="value(perInv)" onclick="GenPerformInv('DLY')" value="Perform Invice" styleClass="submit"/>&nbsp;-->
            <%
            pageList=(ArrayList)pageDtl.get("BUTTON");
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
            if(fld_typ.equals("S")){%>
            <html:submit property="<%=fld_nme%>" value="<%=fld_ttl%>" onclick="<%=val_cond%>" styleClass="submit"/>&nbsp;
            <%}else if(fld_typ.equals("B")){%>
            <html:button property="<%=fld_nme%>" value="<%=fld_ttl%>" onclick="<%=val_cond%>" styleClass="submit"/>&nbsp;
            <%}}}
            %>
    
                         
            </p></td></tr>
            
            <%}else{%>
           <tr><td valign="top" class="tdLayout"> Sorry no result found</td></tr>
          <%}}%>  
            </html:form>
  
     </table></td></tr>
<tr>
    <td><jsp:include page="/footer.jsp" /> </td>
    </tr>
</table>
  
  <%
  String allrpt="Y";
     pageList=(ArrayList)pageDtl.get("ALLOWRPT");
            if(pageList!=null && pageList.size() >0){
            for(int j=0;j<pageList.size();j++){
            pageDtlMap=(HashMap)pageList.get(j);
            allrpt=util.nvl((String)pageDtlMap.get("dflt_val"));
     }
     }  
  if(request.getAttribute("SLMSG")!=null && allrpt.equals("Y")){%>
 <script type="text/javascript">
 <!--
 var url = document.getElementById('rptUrl').value;
 windowOpen(url,'_blank')
 -->
 </script>
 <%}%>
  </body></html>
