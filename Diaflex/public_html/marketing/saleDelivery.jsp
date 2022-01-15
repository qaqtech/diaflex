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
    <title>Sale Delivery</title>
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
HashMap allPageDtl= (info.getPageDetails() == null)?new HashMap():(HashMap)info.getPageDetails();
HashMap pageDtl=(HashMap)allPageDtl.get("DELIVERY_CONFIRMATION");
ArrayList pageList=new ArrayList();
ArrayList prpDspBlocked = info.getPageblockList();
HashMap pageDtlMap=new HashMap();
HashMap prpList = info.getPrp();
String fld_nme="",fld_ttl="",val_cond="",dflt_val="",fld_typ="",form_nme="",lov_qry="";
 ArrayList setcharge = (request.getAttribute("setcharge") == null)?new ArrayList():(ArrayList)request.getAttribute("setcharge");
 ArrayList loclock = (request.getAttribute("loclock") == null)?new ArrayList():(ArrayList)request.getAttribute("loclock");
 String view = util.nvl((String)request.getAttribute("view"));
        String logId=String.valueOf(info.getLogId());
        String onfocus="cook('"+logId+"')";
        %>
 <body onfocus="<%=onfocus%>"  >
 
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
        <% if(request.getAttribute("RTMSG")!=null){%>
        <tr><td height="15"  valign="top" class="tdLayout"><span class="redLabel"> <%=request.getAttribute("RTMSG")%></span></td></tr>
       <% }
        %>
        <% if(request.getAttribute("CANMSG")!=null){%>
        <tr><td height="15"  valign="top" class="tdLayout"><span class="redLabel"> <%=request.getAttribute("CANMSG")%></span></td></tr>
       <% }
        %>
        <% if(request.getAttribute("SLMSG")!=null){
         String url =repPath+"/reports/rwservlet?"+cnt+"&report="+info.getRportUrl()+"\\reports\\sale_dlv_rep.jsp&p_dlv_idn="+request.getAttribute("dlvId")+"&destype=CACHE&desformat=pdf&p_access="+accessidn;
        %>
        <tr><td valign="top" class="tdLayout">
        <input type="hidden" name="rptUrl" id="rptUrl" value="<%=url%>" />
        <span class="redLabel"> <%=request.getAttribute("SLMSG")%></span></td></tr>
      
       <tr><td class="tdLayout"><div onclick="displayDiv('refDiv')"> <a href="<%=url%>"  target="_blank"><span class="txtLink" >Click Here for Report</span></a> </div></td></tr>
       <% }
       String performaLink=(String)request.getAttribute("performaLink");
       System.out.println(performaLink);
        if(performaLink!=null){
        String url=info.getReqUrl()+"/marketing/performa.do?method=perInv&idn="+request.getAttribute("dlvId")+"&form=Y&typ=DLV&pktTyp=SINGLE";
        String urlgenerate=info.getReqUrl()+"/marketing/performa.do?method=loadFromForm&idn="+request.getAttribute("dlvId")+"&form=Y&typ=DLV&pktTyp=SINGLE";%>
        <tr><td height="15"  valign="top" class=""><a href="<%=url%>"  target="_blank"><span class="txtLink" >Performa Invoice</span></a>&nbsp;&nbsp;
        <a href="<%=urlgenerate%>"  target="_blank"><span class="txtLink" >Generate Performa</span></a></td></tr>
       <% }%>
  </table>
  </td>
  </tr>
 <tr><td valign="top" class="tdLayout">
  <table>
  <tr><td><table><tr><td>

  <html:form action="/marketing/saleDelivery.do?method=loadPkt" method="POST" onsubmit="return validate_sale()">
   <html:hidden property="value(PKTTYP)" styleId="PKTTYP"  />
   <table><tr><td valign="top">
  <table class="grid1" >
  <tr><th colspan="2">Sale Search </th></tr>
 <tr>
<td>Buyer Name :</td>
<td>
 <html:select property="byrIdn" name="saleDeliveryForm" onchange="getFinalByr(this,'SL')" styleId="byrId" >
    <html:option value="0">---select---</html:option>
    <html:optionsCollection property="byrLst" name="saleDeliveryForm"  value="byrIdn" label="byrNme" />
                
  </html:select>
               
</td></tr> 
<tr>
<td>Billing  Party :</td><td>
<html:select property="prtyIdn" name="saleDeliveryForm" onchange="getSaleTrms(this.value ,'SL');"  styleId="prtyId"  >
 <html:option value="0">---select---</html:option>    
</html:select>
</td>
</tr>
<tr>
<td>Terms :</td><td>
<html:select property="relIdn" name="saleDeliveryForm"  onchange="getSaleIdn('SL')" styleId="rlnId"  >
     <html:option value="0">---select---</html:option>
</html:select>

</td>
</tr>


<tr><td>Enter Number. </td><td>
<p>
<a href="javascript:void(0)"><html:radio property="value(srchRef)" value="vnm"  name="saleDeliveryForm" /></a>
<span> Packet Code</span>
<a href="javascript:void(0)"><html:radio property="value(srchRef)" value="cert" name="saleDeliveryForm" /></a><span> Certificate</span>
<a href="javascript:void(0)"><html:radio property="value(srchRef)" value="none" name="saleDeliveryForm" /></a><span> None</span></p>
<html:textarea property="value(vnmLst)" name="saleDeliveryForm" styleId="vnmLst" /><label id="fldCtr" ></label> </td> </tr>
   <tr><td colspan="2">
            <%
 ArrayList rfiddevice = ((ArrayList)info.getRfiddevice() == null)?new ArrayList():(ArrayList)info.getRfiddevice();
 if(rfiddevice!=null && rfiddevice.size()>0){
 %>
 RFID Device: <html:select property="value(dvcLst)" styleId="dvcLst" name="saleDeliveryForm"  >
 <%for(int j=0;j<rfiddevice.size();j++){
 ArrayList device=new ArrayList();
 device=(ArrayList)rfiddevice.get(j);
 String val=(String)device.get(0);
 String disp=(String)device.get(1);
 %>
 <html:option value="<%=val%>"><%=disp%></html:option>
 <%}%>
 </html:select>&nbsp;
 <%if(info.getRfid_seq().length()==0){%>
 <html:button property="value(rfScan)" value="RF ID Scan" styleId="rfScan" onclick="doScan('vnmLst','fldCtr','dvcLst','SCAN')"  styleClass="submit" />
 <html:button property="value(autorfScan)" value="Auto Scan" styleId="autorfScan" onclick="doScan('vnmLst','fldCtr','dvcLst','AUTOSCAN')"  styleClass="submit" />
<%}%>
 <html:button property="value(stopAutorfScan)" value="Stop Auto Scan" onclick="doScan('vnmLst','fldCtr','dvcLst','STOPAUTOSCAN')"  styleClass="submit" />
 
<%}%>
            </td></tr>
<tr><td colspan="2"><html:submit property="submit" value="View" styleClass="submit"/></td></tr>           
</table>
</td><td valign="top">
<div id="memoIdn"></div>
</td></tr></table>

</html:form>
  </td>
          <td  valign="top">
      <%
     if(request.getAttribute("view")!=null && view.equals("Y")){
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
    String flg=(String)dtl.get("flg");
    String autoopt=(String)dtl.get("autoopt");
    String typ=(String)dtl.get("typ");
    String fctr=(String)dtl.get("fctr");
    String fun=(String)dtl.get("fun");
    String rmk=(String)dtl.get("rmk");
    String field = "value("+typ+")";
     String salCharge = "value("+typ+"_TTL)";
    String fieldRmk = "value("+typ+"_rmk)";
    String onchang="chargesmanual('"+typ+"','"+i+"')";
    String onchangrmk="chargesmanualrmk('"+typ+"','"+i+"')";
    String fieldId = typ+"_rmk";
    if(flg.equals("MNL")){
    %>
    <tr><td nowrap="nowrap"><b><%=dsc%></b></td>
    <td nowrap="nowrap"><b><span id="<%=typ%>_dis"></span></b></td>
      <td nowrap="nowrap"><bean:write property="<%=salCharge%>" name="saleDeliveryForm" /></td>
    <td nowrap="nowrap"><html:text property="<%=field%>" size="6" styleId="<%=typ%>" onchange="<%=onchang%>" name="saleDeliveryForm"/></td>
    <td nowrap="nowrap">
    <%if(rmk.equals("Y")){%>
    Remark<html:text property="<%=fieldRmk%>" styleId="<%=fieldId%>" onchange="<%=onchangrmk%>" name="saleDeliveryForm" size="10"/>
    <%}%>
    </td>
    </tr>
    <%}else{%>
    <tr><td nowrap="nowrap"><b><%=dsc%></b>
    <%if(flg.equals("AUTO") && autoopt.equals("OPT")){
     String chk="checked=\"checked\"";
    if(setcharge.contains(typ))
    chk="";%>
    <input type="checkbox" name="<%=typ%>_AUTO" id="<%=typ%>_AUTO" <%=chk%> onchange="validateAutoOpt('<%=typ%>_AUTO')" title="Uncheck To Optional"/>
    <%}%>
    </td>
    <td nowrap="nowrap"><b><span id="<%=typ%>_dis"></span></b></td>
    <td nowrap="nowrap">
    <html:hidden property="<%=field%>"  styleId="<%=typ%>" name="saleDeliveryForm"/>
    <!--<input type="button" name="charge_<%=typ%>" id="charge_<%=typ%>" value="Charge"  onclick="<%=onchang%>" class="submit" />-->
    </td><td></td></tr>
    <%}}
        String ttlDisplay="";
          pageList=(ArrayList)pageDtl.get("TTL_DISPLAY");
            if(pageList!=null && pageList.size() >0){
            for(int j=0;j<pageList.size();j++){
            pageDtlMap=(HashMap)pageList.get(j);
            ttlDisplay=(String)pageDtlMap.get("dflt_val"); 
    }}
    %>
    <tr><td nowrap="nowrap" style="<%=ttlDisplay%>"><b>Total</b></td><td nowrap="nowrap"><b><span id="net_dis"></span></b></td><td></td><td></td></tr>
    </table>
    <%}}%>
    </td>
  </tr></table>
  </td>
  
  </tr>
  
   <html:form action="/marketing/saleDelivery.do?method=save" method="POST"  onsubmit="return confirmChanges()">
   <% if(request.getAttribute("view")!=null){
     ArrayList pkts = (ArrayList)info.getValue("PKTS");
     HashMap avgdtl=(HashMap)request.getAttribute("avgdtl");
   if(pkts!=null && pkts.size()>0){
    ArrayList itemHdr = new ArrayList();
    ArrayList pktList = new ArrayList();
    HashMap pktPrpMap = new HashMap();
    String aadatcommdisplay="display:none",brk1commdisplay="display:none",brk2commdisplay="display:none",brk3commdisplay="display:none",brk4commdisplay="display:none";
   if(request.getAttribute("view")!=null && view.equals("Y")){
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
    %>
    <html:hidden property="<%=field%>" name="saleDeliveryForm" styleId="<%=fieldId%>"  />
   <%if(rmk.equals("Y")){%>
    <html:hidden property="<%=fieldRmk%>" name="saleDeliveryForm" styleId="<%=fieldIdrmk%>"  />
    <%}
    if(flg.equals("AUTO") && autoopt.equals("OPT")){
    String chk="N";
    if(setcharge.contains(typ))
    chk="Y";%>
    <html:hidden property="<%=fieldauto%>" name="saleDeliveryForm" styleId="<%=fieldIdauto%>" value="<%=chk%>"   />
    <%}
    }%>
   <html:hidden property="value(vluamt)" name="saleDeliveryForm" styleId="vluamt"  />
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
                 <b>-</b><bean:write property="value(cuIdn)" name="saleDeliveryForm" />
                </td>
                 <td> <b>Sale Person :</b> </span></td><td>
                <bean:write property="value(SALEMP)" name="saleDeliveryForm" />
                </td>
          <td><b>Email :</b></td> <td> <bean:write property="value(email)" name="saleDeliveryForm"/> </td>
          <td><b>Mobile No :</b></td> <td> <bean:write property="value(mobile)" name="saleDeliveryForm"/> </td>
          <td><b>Office No :</b></td> <td> <bean:write property="value(office)" name="saleDeliveryForm"/> </td>
           <td><b>Remark/Comment</b>&nbsp;<html:text property="value(rmk)" size="15"  /></td>
            <%ArrayList notepersonList = ((ArrayList)info.getNoteperson() == null)?new ArrayList():(ArrayList)info.getNoteperson();
            if(notepersonList.size()>0){%>
            <td>&nbsp;Note Person:&nbsp;</td>
            <td>
                  <html:select name="saleDeliveryForm" property="value(noteperson)" styleId="noteperson">
                  <html:option value="">---Select---</html:option><%
                  for(int i=0;i<notepersonList.size();i++){
                  ArrayList noteperson=(ArrayList)notepersonList.get(i);%>
                  <html:option value="<%=(String)noteperson.get(0)%>"> <%=(String)noteperson.get(1)%> </html:option>
                  <%}%>
                  </html:select>
            </td>
            <%}%>
          
                </tr></table>
   
   </td></tr>
   <tr>
   <td>
   <table><tr>
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
           
           <%if(cnt.equalsIgnoreCase("asha") || cnt.equalsIgnoreCase("pm")){
           %>
           <td nowrap="nowrap"><b>Dlv Id:</b></td><td>
           <html:text property="value(mnldlv_id)" styleId="mnldlv_id" name="saleDeliveryForm" size="10" />
           </td><%}%>
           <% pageList= ((ArrayList)pageDtl.get("FNLEXRTE") == null)?new ArrayList():(ArrayList)pageDtl.get("FNLEXRTE");            
            if(pageList!=null && pageList.size() >0){
            for(int j=0;j<pageList.size();j++){
            pageDtlMap=(HashMap)pageList.get(j);
            
            dflt_val=util.nvl((String)pageDtlMap.get("dflt_val"));
            if(dflt_val.equals("Y")){%>
         <td><span class="pgTtl" >Final Exchange Rate :</span> </td>
         
           <td>
            <html:text property="value(fnlexhRte)"   styleId="fnlexhRte" size="5" name="saleDeliveryForm" />
           </td><%}}}%>
            <% pageList= ((ArrayList)pageDtl.get("DAYTERMS") == null)?new ArrayList():(ArrayList)pageDtl.get("FNLEXRTE");            
            if(pageList!=null && pageList.size() >0){
            for(int j=0;j<pageList.size();j++){
            pageDtlMap=(HashMap)pageList.get(j);
            
            dflt_val=util.nvl((String)pageDtlMap.get("dflt_val"));
            if(dflt_val.equals("Y")){%>
            <td><span class="pgTtl" >Terms </span></td><td>
             
             <html:select property="value(dayTerms)" name="saleDeliveryForm"  styleId="dayTerms"   >
         
             <html:optionsCollection property="dayTrmsLst" name="saleDeliveryForm"
            label="trmDtl" value="relId" />
            
            </html:select>
            </td>
            <%}}}%>
            
   </tr></table>
   </td>
   
   
   </tr>
  
   <tr><td>
            <table>
                <tr>
               
            <td><span class="pgTtl" >Buyer List</span></td>
                <td>
                <html:select property="value(invByrIdn)" name="saleDeliveryForm"  styleId="byrId1" onchange="GetADDR();setDfltGrpBank();GetBank()" >
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
            <td>
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
            <table><tr><td><span class="pgTtl" >Commission :</span> </td>
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
            <td><html:radio property="value(aadatpaid)" onchange="setBrokerComm('aadatcomm','Y')" styleId="aadatpaid1"  value="Y"   name="saleDeliveryForm" disabled="<%=isdisabled%>"  /> </td>
            <td>&nbsp;Yes</td> 
            <td><html:radio property="value(aadatpaid)"  onchange="setBrokerComm('aadatcomm','N')" styleId="aadatpaid2" value="N" name="saleDeliveryForm" disabled="<%=isdisabled%>"  /></td>
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
           <td id="brk1Nme"><bean:write property="value(brk1)" name="saleDeliveryForm" /></td><td><html:radio property="value(brk1paid)" styleId="brk1paid1" value="Y" name="saleDeliveryForm" disabled="<%=isdisabled%>" /></td><td>&nbsp;Yes</td>  <td><html:radio property="value(brk2paid)"  styleId="brk1paid2"  value="N"  name="saleDeliveryForm" disabled="<%=isdisabled%>" /></td><td>&nbsp;No</td> <td><html:text property="value(brk1comm)"  styleId="brk1comm"  readonly="true" size="3" name="saleDeliveryForm" /> </td>
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
           <td id="brk2Nme"><bean:write property="value(brk2)" name="saleDeliveryForm" /></td><td><html:radio property="value(brk2paid)"  styleId="brk2paid1" value="Y" name="saleDeliveryForm" disabled="<%=isdisabled%>" /> </td><td>&nbsp;Yes</td> <td><html:radio property="value(brk2paid)"  styleId="brk2paid2"   value="N" name="saleDeliveryForm" disabled="<%=isdisabled%>" /></td><td> &nbsp;No</td> <td><html:text property="value(brk2comm)" styleId="brk2comm" readonly="true" size="3" name="saleDeliveryForm" /> </td>
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
           <td id="brk3Nme"><bean:write property="value(brk3)" name="saleDeliveryForm" /></td><td><html:radio property="value(brk3paid)"   styleId="brk3paid1" value="Y" name="saleDeliveryForm" disabled="<%=isdisabled%>" /> </td><td>&nbsp;Yes</td> <td><html:radio property="value(brk3paid)"  styleId="brk3paid2" value="N" name="saleDeliveryForm" disabled="<%=isdisabled%>" /></td><td> &nbsp;No</td>  <td><html:text property="value(brk3comm)" styleId="brk3comm" readonly="true" size="3" name="saleDeliveryForm" /> </td>
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
           <td id="brk4Nme"><bean:write property="value(brk4)" name="saleDeliveryForm" /></td><td><html:radio property="value(brk4paid)"   styleId="brk4paid1"  value="Y" name="saleDeliveryForm" disabled="<%=isdisabled%>" /> </td><td>&nbsp;Yes</td> <td><html:radio property="value(brk4paid)"   styleId="brk4paid2" value="N" name="saleDeliveryForm" disabled="<%=isdisabled%>" /></td><td> &nbsp;No</td> <td><html:text property="value(brk4comm)" styleId="brk4comm" readonly="true" size="3" name="saleDeliveryForm" /> </td>
           </tr>
           </table>
           </div>
           </td>
            </tr></table> </td>
            </tr>
            <tr>
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
            </tr>
           
 <!--<tr><td><span class="pgTtl" >Extra Charge : </span><input type="text" id="echarge" name="echarge" onchange="isNumericDecimal(this.id)"/></td></tr>-->
  <tr><td>
            <label class="pgTtl">Sale Packets</label>
            <%pageList=(ArrayList)pageDtl.get("EXCEL");
            if(pageList!=null && pageList.size() >0){
            for(int j=0;j<pageList.size();j++){
            pageDtlMap=(HashMap)pageList.get(j);
            fld_nme=(String)pageDtlMap.get("fld_nme");
            fld_ttl=(String)pageDtlMap.get("fld_ttl");
            fld_typ=(String)pageDtlMap.get("fld_typ");
            dflt_val=(String)pageDtlMap.get("dflt_val");
            val_cond=(String)pageDtlMap.get("val_cond"); 
            val_cond=val_cond.replaceAll("NMEIDN", util.nvl((String)request.getAttribute("NMEIDN")));
            val_cond=val_cond.replaceAll("URL", info.getReqUrl());
            if(fld_typ.equals("E")){
            %>
    <%=fld_ttl%> <img src="../images/ico_file_excel.png" onclick="<%=val_cond%>" />&nbsp;
    <%}else if(fld_typ.equals("M")){%>
    <%=fld_ttl%> <img src="../images/ico_file_excel.png" onclick="<%=val_cond%>" />&nbsp;
    <%}}}%>
            <!--Create Excel <img src="../images/ico_file_excel.png" onclick="goTo('<%=info.getReqUrl()%>/marketing/saleDelivery.do?method=createXL','','')" />
            Mail Excel <img src="../images/ico_file_excel.png" onclick="newWindow('<%=info.getReqUrl()%>/marketing/saleDelivery.do?method=createXL&mail=Y')" />-->
            <%
                ArrayList prps = (info.getValue("MDL_MEMO_RTRN") == null)?new ArrayList():(ArrayList)info.getValue("MDL_MEMO_RTRN");
            itemHdr.add("BYR"); itemHdr.add("sale Id");itemHdr.add("saledte");itemHdr.add("Packet Code");
             %>  </td></tr>
             <tr><td>
            <table class="grid1">
                <tr>
                    <th>Sr</th>
                    <%pageList=(ArrayList)pageDtl.get("RADIOHDR");
                    if(pageList!=null && pageList.size() >0){
                    for(int j=0;j<pageList.size();j++){
                    pageDtlMap=(HashMap)pageList.get(j);
                    fld_nme="value("+(String)pageDtlMap.get("fld_nme")+")";
                    fld_ttl=(String)pageDtlMap.get("fld_ttl");
                    fld_typ=(String)pageDtlMap.get("fld_typ");
                    dflt_val=(String)pageDtlMap.get("dflt_val");
                    val_cond=(String)pageDtlMap.get("val_cond"); %>
                    <th><html:radio property="<%=fld_nme%>"  styleId="<%=fld_typ%>" name="saleDeliveryForm" onclick="<%=val_cond%>"  value="<%=dflt_val%>"/><%=fld_ttl%> </th>
                    <%}}%>
                    <th>Sale Id</th>
                    <th>Sale Dte</th>
                      <th>Packet Code</th>
                     <%for(int j=0; j < prps.size(); j++) {
                     String lprp=(String)prps.get(j);
                     if(prpDspBlocked.contains(lprp)){
                     }else{
                     itemHdr.add(lprp);%>
                        <th><%=lprp%></th>
                    <%}}%>
                    <th>RapRte</th>
                    <th>RapVlu</th>
                    <th>Dis</th>
                    <th>Prc / Crt </th>
                    <th>ByrDis</th>
                    <th>Quot</th>
                     <th>Byr Amt</th>
        
        <%        
            pageList=(ArrayList)pageDtl.get("TBL_HD");
            if(pageList!=null && pageList.size() >0){
            for(int j=0;j<pageList.size();j++){
            pageDtlMap=(HashMap)pageList.get(j);
            fld_nme="value("+(String)pageDtlMap.get("fld_nme")+")";
            fld_ttl=(String)pageDtlMap.get("fld_ttl");
            fld_typ=(String)pageDtlMap.get("fld_typ");
            dflt_val=(String)pageDtlMap.get("dflt_val");
            val_cond=(String)pageDtlMap.get("val_cond"); %>
                
            <th><%=fld_ttl%></th>
                
        <%
            }}
            %>
        
              
                
                    <!--<th><html:radio property="value(slRd)"  styleId="slRd" name="saleDeliveryForm" onclick="ALLRedio('slRd' ,'SL_');SetCalculationDlvTll('SL')"  value="SL"/>&nbsp;Sale</th>
                    <th><html:radio property="value(slRd)" styleId="dlvRd" name="saleDeliveryForm" onclick="ALLRedio('dlvRd' ,'DLV_');SetCalculationDlvTll('DLV')"  value="DLV"/>&nbsp;Delivery</th>
                    <th><html:radio property="value(slRd)" styleId="rtRd" name="saleDeliveryForm" onclick="ALLRedio('rtRd' ,'RT_');SetCalculationDlvTll('RT')"  value="RT"/>&nbsp;Return</th>
                    <th> <html:radio property="value(slRd)" styleId="invRd" name="saleDeliveryForm" onclick="ALLRedio('invRd' ,'INV_');SetCalculationDlvTll('INV')"  value="INV"/>&nbsp;Performa Inv </th>-->
                   
                </tr>
            <%
            itemHdr.add("RapRte");itemHdr.add("RapVlu");itemHdr.add("Dis");itemHdr.add("Prc / Crt");itemHdr.add("orgDis");itemHdr.add("ByrDis");itemHdr.add("org Quot");itemHdr.add("Byr Quot");itemHdr.add("orgamt");itemHdr.add("byramt");
             int count =0 ;
             
             
                for(int i=0; i < pkts.size(); i++) {
               PktDtl pkt = (PktDtl)pkts.get(i);
                    long pktIdn = pkt.getPktIdn();
                    String pktstt=util.nvl(pkt.getValue("pktstt"));
                    String PRINTBLK=util.nvl(pkt.getValue("PRINTBLK"));
                      String style="";
                    if(PRINTBLK.equals("YES"))
                     style="style=\"color:red\"";
                    
                %>
                <tr style="<%=style%>">
                <td><%=(i+1)%></td>
                <%    count=i+1;
                  
                    String cbPrp = "value(upd_" + pktIdn + ")";
                    String sttPrp = "value(stt_" + pktIdn + ")" ;
                    String invPrp = "value(INV_" + pktIdn + ")" ;
                    String rdISId = "DLV_"+count;
                    String rdSLId = "SL_"+count;
                    String rdRTId = "RT_"+count;
                    String rdInvId = "INV_"+count;
                    String onClickAp = "SetCalculationDlv('"+pktIdn+"','DLV','"+pkt.getSaleId()+"')";
                    String onClickSl = "SetCalculationDlv('"+pktIdn+"','SL','"+pkt.getSaleId()+"')";
                    String onClickRt = "SetCalculationDlv('"+pktIdn+"','RT','"+pkt.getSaleId()+"')";
                    String rmkTxt =  "value(rmk_" + pktIdn + ")" ;
                    String dlvrmkTxt =  "value(dlvrmk_" + pktIdn + ")" ;
                    pktPrpMap = new HashMap();
                    pktPrpMap.put("BYR",util.nvl(pkt.getValue("ByrNme")));
                    pktPrpMap.put("sale Id",util.nvl(pkt.getSaleId()));pktPrpMap.put("Packet Code",util.nvl(pkt.getVnm()));
                    pktPrpMap.put("saledte",util.nvl(pkt.getValue("saledte")));
             %>
                         <%
                         pageList=(ArrayList)pageDtl.get("RADIOBODY");
            if(pageList!=null && pageList.size() >0){
            for(int j=0;j<pageList.size();j++){
            pageDtlMap=(HashMap)pageList.get(j);
            fld_nme="value("+(String)pageDtlMap.get("fld_nme")+""+pktIdn+")";
            fld_ttl=(String)pageDtlMap.get("fld_ttl");
            fld_typ=(String)pageDtlMap.get("fld_typ")+count;
            dflt_val=util.nvl((String)pageDtlMap.get("dflt_val")).trim();
            lov_qry=util.nvl((String)pageDtlMap.get("lov_qry")).trim();
            if(dflt_val.equals("PKT"))
            dflt_val=String.valueOf(pktIdn);
            val_cond=(String)pageDtlMap.get("val_cond");
            val_cond=val_cond.replaceAll("PKTIDN",String.valueOf(pktIdn));
            val_cond=val_cond.replaceAll("SALEID",pkt.getSaleId());
            boolean disableStt=false;
            if(loclock.contains(String.valueOf(pktIdn)) && dflt_val.equals("DLV"))
            disableStt=true;
            %>
            <td nowrap="nowrap"><html:radio property="<%=fld_nme%>"  disabled="<%=disableStt%>" styleId="<%=fld_typ%>"  name="saleDeliveryForm"  onclick="<%=val_cond%>"  value="<%=dflt_val%>"/>
            <%if(dflt_val.equals("RT")){%>
            <html:text property="<%=rmkTxt%>" size="10"  />
            <%}%>
            <%if(dflt_val.equals("DLV") && lov_qry.equals("RMK")){%>
            <html:text property="<%=dlvrmkTxt%>" size="10"  />
            <%}%>
            </td>
            <%}}%>
                <td><%=pkt.getSaleId()%>    
                <input type="hidden" id="STKIDN_<%=count%>" value="<%=pktIdn%>" />
               </td>
                 <td><%=util.nvl(pkt.getValue("saledte"))%></td>
                 <td><%=pkt.getVnm()%></td>
                <%for(int j=0; j < prps.size(); j++) {
                      String lprp = (String)prps.get(j);
                      if(prpDspBlocked.contains(lprp)){
                      }else{
                      pktPrpMap.put(lprp,util.nvl(pkt.getValue((String)prps.get(j))));
                %>
                    <td nowrap="nowrap"><%=util.nvl(pkt.getValue((String)prps.get(j)))%>
                      <%}if(lprp.equals("CRTWT")){%>
                    <input type="hidden" id="<%=count%>_cts" value="<%=util.nvl(pkt.getValue((String)prps.get(j)))%>" /> 
                    <%}%>
                    </td>
                    
                <%}
                %>
                <td><%=util.nvl(pkt.getRapRte())%><input type="hidden" id="<%=count%>_rap" value="<%=pkt.getRapRte()%>" /></td>
                <td><%=util.nvl(pkt.getValue("rapVlu"))%></td>
                <td><%=util.nvl(pkt.getDis())%></td>
                <td><%=util.nvl(pkt.getRte())%>
                <input type="hidden" id="<%=count%>_quot" value="<%=pkt.getRte()%>" /> 
                <input type="hidden" id="<%=count%>_fnl" value="<%=pkt.getMemoQuot()%>" />
                </td>
                <td><%=util.nvl(pkt.getByrDis())%></td>
                <td><%=util.nvl(pkt.getMemoQuot())%></td>
                  <td><%=util.nvl(pkt.getValue("byramt"))%></td>
                
                
        <%        
            pageList=(ArrayList)pageDtl.get("TBL_DT");
            if(pageList!=null && pageList.size() >0){
            for(int j=0;j<pageList.size();j++){
            pageDtlMap=(HashMap)pageList.get(j);
            fld_nme="value("+(String)pageDtlMap.get("fld_nme")+")";
            fld_ttl=(String)pageDtlMap.get("fld_ttl");
            fld_typ=(String)pageDtlMap.get("fld_typ");
            dflt_val=(String)pageDtlMap.get("dflt_val");
            val_cond=(String)pageDtlMap.get("val_cond"); 
        %>    
            <td><%=pkt.getValue(fld_ttl)%></td>
                
        <%  
            }}
        %>          
          <input type="hidden" id="inc_vlu_<%=pktIdn%>" value="<%=pkt.getInc_vlu()%>" />      
                
                
                <!--<td><html:radio property="<%=sttPrp%>" styleId="<%=rdSLId%>" name="saleDeliveryForm" onclick="<%=onClickSl%>"  value="SL"/></td>
                <td><html:radio property="<%=sttPrp%>" styleId="<%=rdISId%>" name="saleDeliveryForm" onclick="<%=onClickAp%>"  value="DLV"/></td>
                 <td nowrap="nowrap"><html:radio property="<%=sttPrp%>" styleId="<%=rdRTId%>" name="saleDeliveryForm" onclick="<%=onClickRt%>" value="RT"/>   <html:text property="<%=rmkTxt%>" size="10"  /></td>
                   <td><html:radio property="<%=sttPrp%>" styleId="<%=rdInvId%>" name="saleDeliveryForm"  value="<%=String.valueOf(pktIdn)%>"/></td>
               -->

            
                </tr>
              <% 
              pktPrpMap.put("RapVlu",util.nvl((String)pkt.getValue("rapVlu")));
              pktPrpMap.put("RapRte",util.nvl((String)pkt.getRapRte()));pktPrpMap.put("Dis",util.nvl((String)pkt.getDis()));pktPrpMap.put("Prc / Crt",util.nvl((String)pkt.getRte()));pktPrpMap.put("ByrDis",util.nvl((String)pkt.getByrDis()));
                 pktPrpMap.put("orgDis",util.nvl((String)pkt.getValue("orgDis")));
                 pktPrpMap.put("Byr Quot",util.nvl((String)pkt.getMemoQuot()));
                 pktPrpMap.put("orgamt",util.nvl((String)pkt.getValue("orgamt")));pktPrpMap.put("byramt",util.nvl((String)pkt.getValue("byramt")));
                 pktPrpMap.put("org Quot",util.nvl((String)pkt.getByrRte()));
                pktList.add(pktPrpMap); 
                }
                pktList.add(avgdtl);
                session.setAttribute("pktList", pktList);
                session.setAttribute("itemHdr", itemHdr);
            %>
              <input type="hidden" id="rdCount" value="<%=count%>" />
            </table></td></tr>
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
           <tr><td> Sorry no result found</td></tr>
          <%}}%>  
            </html:form>
  </table>
</td></tr>
<tr>
    <td><jsp:include page="/footer.jsp" /> </td>
    </tr>
</table>
  
  <%if(request.getAttribute("SLMSG")!=null){%>
 <script type="text/javascript">
 <!--
 var url = document.getElementById('rptUrl').value;
 windowOpen(url,'_blank')
 -->
 </script>
 <%}%>
  </body></html>
