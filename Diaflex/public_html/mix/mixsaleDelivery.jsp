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
 String accessidn=(String)request.getAttribute("accessidn");
     HashMap allPageDtl= (info.getPageDetails() == null)?new HashMap():(HashMap)info.getPageDetails();
     HashMap pageDtl=(HashMap)allPageDtl.get("MIXDELIVERY_CONFIRMATION");
ArrayList pageList=new ArrayList();
ArrayList prpDspBlocked = info.getPageblockList();
HashMap pageDtlMap=new HashMap();
String fld_nme="",fld_ttl="",val_cond="",dflt_val="",fld_typ="",form_nme="",lov_qry="";
 String view = util.nvl((String)request.getAttribute("view"));
 ArrayList setcharge = (request.getAttribute("setcharge") == null)?new ArrayList():(ArrayList)request.getAttribute("setcharge");
        String logId=String.valueOf(info.getLogId());
        String onfocus="cook('"+logId+"')";
        %>
 <body onfocus="<%=onfocus%>" >
 
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
         String url =repPath+"/reports/rwservlet?"+cnt+"&report="+info.getRportUrl()+"\\reports\\sale_dlv_rep.jsp&p_access="+accessidn+"&p_dlv_idn="+request.getAttribute("dlvId")+"&destype=CACHE&desformat=pdf";
        %>
        <tr><td valign="top" class="tdLayout">
        <input type="hidden" name="rptUrl" id="rptUrl" value="<%=url%>" />
        <span class="redLabel"> <%=request.getAttribute("SLMSG")%></span></td></tr>
      
       <tr><td class="tdLayout"><div onclick="displayDiv('refDiv')"> <a href="<%=url%>"  target="_blank"><span class="txtLink" >Click Here for Report</span></a> </div></td></tr>
       <% }
       String performaLink=(String)request.getAttribute("performaLink");
       System.out.println(performaLink);
        if(performaLink!=null){
        String url=info.getReqUrl()+"/marketing/performa.do?method=perInv&idn="+request.getAttribute("dlvId")+"&form=Y&typ=DLV&pktTyp=MIX";%>
        <tr><td height="15"  valign="top" class="tdLayout"><a href="<%=url%>"  target="_blank"><span class="txtLink" >Performa Invoice</span></a></td></tr>
       <% }%>
  </table>
  </td>
  </tr>
 <tr><td valign="top" class="tdLayout">
  <table>
  <tr><td><table><tr><td>
  <table><tr><td>
  <html:form action="/mix/saleDelivery.do?method=loadPkt" method="POST" onsubmit="return validate_sale()">
 
 <table cellpadding="0" cellspacing="0" border="0"><tr><td valign="top">
  <table class="grid1" >
  <tr><th colspan="2">Sale Search </th></tr>
 <tr>
<td>Buyer Name :</td>
<td>
 <html:select property="byrIdn" name="mixsaleDeliveryForm" onchange="getFinalByrMix(this,'SL')" styleId="byrId" >
    <html:option value="0">---select---</html:option>
    <html:optionsCollection property="byrLst" name="mixsaleDeliveryForm"  value="byrIdn" label="byrNme" />
                
  </html:select>
               
</td></tr> 
<tr>
<td>Billing  Party :</td><td>
<html:select property="prtyIdn" name="mixsaleDeliveryForm" onchange="getSaleTrmsMix(this.value , 'SL')"  styleId="prtyId"  >
 <html:option value="0">---select---</html:option>    
</html:select>
</td>
</tr>
<tr>
<td>Terms :</td><td>
<html:select property="relIdn" name="mixsaleDeliveryForm"  onchange="getSaleIdnMix('SL')" styleId="rlnId"  >
     <html:option value="0">---select---</html:option>
</html:select>

</td>
</tr>
   <tr><td>Packets. </td><td>
<html:textarea property="value(vnmLst)" name="mixsaleDeliveryForm" styleId="vnmLst" /> </td> </tr>
<tr><td colspan="2"><html:submit property="submit" value="View" styleClass="submit"/></td></tr>           
</table>
</td><td valign="top">
<div id="memoIdn"></div>
</td></tr></table>
</html:form>
  </td>
          <td  valign="top">
     <%if(request.getAttribute("view")!=null && view.equals("Y")){
     ArrayList chargesLst=(ArrayList)session.getAttribute("chargesLst");
     if(chargesLst!=null && chargesLst.size()>0){%>
    <table class="grid1">
        <tr>
    <td colspan="4">
    <span class="redLabel">*Uncheck if don't want to apply</span>
    </td>
    </tr>
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
    String fieldRmk = "value("+typ+"_rmk)";
    String onchang="chargesmanual('"+typ+"','"+i+"')";
    String onchangrmk="chargesmanualrmk('"+typ+"','"+i+"')";
    String fieldId = typ+"_rmk";
    if(flg.equals("MNL")){
    %>
    <tr><td nowrap="nowrap"><b><%=dsc%></b></td>
    <td nowrap="nowrap"><b><span id="<%=typ%>_dis"></span></b></td>
    <td nowrap="nowrap"><html:text property="<%=field%>" size="6" styleId="<%=typ%>" onchange="<%=onchang%>" name="mixsaleDeliveryForm"/></td>
    <td nowrap="nowrap">
    <%if(rmk.equals("Y")){%>
    Remark<html:text property="<%=fieldRmk%>" styleId="<%=fieldId%>" onchange="<%=onchangrmk%>" name="mixsaleDeliveryForm" size="10"/>
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
    <html:hidden property="<%=field%>"  styleId="<%=typ%>" name="mixsaleDeliveryForm"/>
    <!--<input type="button" name="charge_<%=typ%>" id="charge_<%=typ%>" value="Charge"  onclick="<%=onchang%>" class="submit" />-->
    </td><td></td></tr>
    <%}}%>
    <tr><td nowrap="nowrap"><b>Total</b></td><td nowrap="nowrap"><b><span id="net_dis"></span></b></td><td></td><td></td></tr>
    </table>
    <%}}%>
    </td>
  </tr></table>
  </td>
  
  </tr>
  
   <html:form action="/mix/saleDelivery.do?method=save" method="POST"  onsubmit="return confirmChanges()">
   <% if(request.getAttribute("view")!=null){
     String saleIdnLst="";
     ArrayList pkts = (ArrayList)info.getValue("PKTS");
     HashMap avgdtl=(HashMap)request.getAttribute("avgdtl");
     String aadatcommdisplay="display:none",brk1commdisplay="display:none",brk2commdisplay="display:none",brk3commdisplay="display:none",brk4commdisplay="display:none";
   if(pkts!=null && pkts.size()>0){
    ArrayList itemHdr = new ArrayList();
    ArrayList pktList = new ArrayList();
    HashMap pktPrpMap = new HashMap();
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
    <html:hidden property="<%=field%>" name="mixsaleDeliveryForm" styleId="<%=fieldId%>"  />
   <%if(rmk.equals("Y")){%>
    <html:hidden property="<%=fieldRmk%>" name="mixsaleDeliveryForm" styleId="<%=fieldIdrmk%>"  />
    <%}
    if(flg.equals("AUTO") && autoopt.equals("OPT")){
    String chk="N";
    if(setcharge.contains(typ))
    chk="Y";%>
    <html:hidden property="<%=fieldauto%>" name="mixsaleDeliveryForm" styleId="<%=fieldIdauto%>" value="<%=chk%>"  />
    <%}
    }%>
   <html:hidden property="value(vluamt)" name="mixsaleDeliveryForm" styleId="vluamt"  />
    <%}}
    %>
   <tr><td>
    <html:hidden property="value(exhRte)"  styleId="exhRte" name="mixsaleDeliveryForm" />
   <table>
            
                <tr>
                <td><span class="pgTtl" >Exiting Selection</span></td>
                <td> <b>Terms : </b> </span></td><td>
                <bean:write property="value(trmsLb)" name="mixsaleDeliveryForm" />
                <html:hidden property="relIdn" name="mixsaleDeliveryForm" />
               
                </td>
                 <td> <b>Buyer :</b> </span></td><td>
                <bean:write property="byr" name="mixsaleDeliveryForm" />
                 <html:hidden property="nmeIdn" name="mixsaleDeliveryForm"  styleId="nmeIdn"  />
                </td>
          <td><b>Email :</b></td> <td> <bean:write property="value(email)" name="mixsaleDeliveryForm"/> </td>
          <td><b>Mobile No :</b></td> <td> <bean:write property="value(mobile)" name="mixsaleDeliveryForm"/> </td>
          <td><b>Office No :</b></td> <td> <bean:write property="value(office)" name="mixsaleDeliveryForm"/> </td>
          
                
                </tr></table>
   
   </td></tr>
  
   <tr><td>
            <table>
                <tr>
                
                
            <td><span class="pgTtl" >Buyer List</span></td>
                <td>
                <html:select property="value(invByrIdn)" name="mixsaleDeliveryForm"  styleId="byrId1" onchange="GetADDR();GetBank()" >
               <html:option value="0" >---select---</html:option>
                <html:optionsCollection property="invByrLst" name="mixsaleDeliveryForm"  value="byrIdn" label="byrNme" />
                
                </html:select>
                </td>   <td><span class="pgTtl" >Address </span></td>
                <td><html:select property="value(invAddr)" styleId="addrId" name="mixsaleDeliveryForm"  >
                
                 <html:optionsCollection property="invAddLst" name="mixsaleDeliveryForm"   value="idn" label="addr" />
                </html:select>
                
               </td>
                  <td><span class="pgTtl" >Terms </span></td><td>
             
             <html:select property="value(invByrTrm)" name="mixsaleDeliveryForm"  styleId="rlnId1"  onchange="invTermsDtls();" >
         
             <html:optionsCollection property="invTrmsLst" name="mixsaleDeliveryForm"
            label="trmDtl" value="relId" />
            
            </html:select>
            </td>
            </tr></table></td></tr>
           
            <tr><td><table>
            <tr>
            <td nowrap="nowrap"><span class="pgTtl" >Company Name</span></td>
          <td>
           <html:select property="value(grpIdn)" name="mixsaleDeliveryForm"  styleId="grpIdn"  >
             <html:optionsCollection property="groupList" name="mixsaleDeliveryForm"
             value="idn" label="addr" />
            </html:select>
          </td>
                <td nowrap="nowrap"><span class="pgTtl" >Bank Selection</span></td>
               <td>
             
             <html:select property="value(bankIdn)" name="mixsaleDeliveryForm" onchange="SetBankAddr(this)"  styleId="bankIdn"  >
             <html:optionsCollection property="bankList" name="mixsaleDeliveryForm"
             value="idn" label="addr" />
            
            </html:select>
            </td>
            <td> <html:select property="value(bankAddr)" name="mixsaleDeliveryForm" style="dispaly:none" styleId="bankAddr">
             <html:optionsCollection property="bnkAddrList" name="mixsaleDeliveryForm"
             value="idn" label="addr" />
            </html:select>
            
            </td>
            </tr>
            
            </table></td>
            
            </tr>
       
                <tr>
            <td nowrap="nowrap"><table><tr><td><span class="pgTtl" >Courier :</span> </td>
             <td> <html:select property="value(courier)" name="mixsaleDeliveryForm" style="dispaly:none" styleId="courier">
            <html:optionsCollection property="courierList" name="mixsaleDeliveryForm"
             value="idn" label="addr" />
            </html:select>
            </td>
            <%if(cnt.equalsIgnoreCase("hk")){%>
            <td nowrap="nowrap"><span class="pgTtl" >Bank through :</span></td><td>
            <html:select property="value(throubnk)" name="mixsaleDeliveryForm"  styleId="throubnk">
            <html:option value="0">-----select bank through-----</html:option>
            <html:optionsCollection property="thruBankList" name="mixsaleDeliveryForm"
             value="idn" label="fnme" />
            </html:select>
            </td><%}%>
            </tr></table> </td>
            </tr>
            <tr>
            <td><table><tr><td><span class="pgTtl" >Commission :</span> </td>
            <html:hidden property="value(aadatcommval)" styleId="aadatcommval" name="mixsaleDeliveryForm" value="0"/>
            <html:hidden property="value(brk1commval)" styleId="brk1commval" name="mixsaleDeliveryForm" value="0"/>
            <html:hidden property="value(brk2commval)" styleId="brk2commval"  name="mixsaleDeliveryForm" value="0"/>
            <html:hidden property="value(brk3commval)" styleId="brk3commval"  name="mixsaleDeliveryForm" value="0"/>
            <html:hidden property="value(brk4commval)" styleId="brk4commval"  name="mixsaleDeliveryForm" value="0"/>
            <logic:present property="value(aadatcomm)" name="mixsaleDeliveryForm" >
            <%aadatcommdisplay="display:block";%>
            </logic:present>
            <td nowrap="nowrap">
            <div style="<%=aadatcommdisplay%>" id="aadatcommdisplay">
            <table>
            <tr>
            <td><span class="pgTtl" >Aadat :</span> </td>
            <td>
            <span id="aaDatNme"><bean:write property="value(aaDat)" name="mixsaleDeliveryForm" /></span> :
            <span id="aaDatComm"><bean:write property="value(aadatcomm)" name="mixsaleDeliveryForm" /></span> </td>
            <td><html:radio property="value(aadatpaid)" onchange="setBrokerComm('aadatcomm','Y')" styleId="aadatpaid1"  value="Y"  name="mixsaleDeliveryForm"/> </td>
            <td>&nbsp;Yes</td> 
            <td><html:radio property="value(aadatpaid)"  onchange="setBrokerComm('aadatcomm','N')" styleId="aadatpaid2" value="N" name="mixsaleDeliveryForm"/></td>
            <td>&nbsp;No</td>
            <td><html:text property="value(aadatcomm)" styleId="aadatcomm" readonly="true" size="3" name="mixsaleDeliveryForm" /> </td>
            </tr>
            </table>
            </div>
            </td>
            <logic:present property="value(brk1comm)" name="mixsaleDeliveryForm" >
            <%brk1commdisplay="display:block";%>
           </logic:present>
           <td nowrap="nowrap">
           <div  style="<%=brk1commdisplay%>" id="brk1commdisplay">
           <table>
           <tr>
           <td><span class="pgTtl" >Broker :</span> </td>
           <td id="brk1Nme"><bean:write property="value(brk1)" name="mixsaleDeliveryForm" /></td><td><html:radio property="value(brk1paid)" styleId="brk1paid1" value="Y" name="mixsaleDeliveryForm"/></td><td>&nbsp;Yes</td>  <td><html:radio property="value(brk1paid)"  styleId="brk1paid2"  value="N" name="mixsaleDeliveryForm"/></td><td>&nbsp;No</td> <td><html:text property="value(brk1comm)"  styleId="brk1comm"  readonly="true" size="3" name="mixsaleDeliveryForm" /> </td>
           </tr>
           </table>
           </div>
           </td>
            <logic:present property="value(brk2comm)" name="mixsaleDeliveryForm" >
            <%brk2commdisplay="display:block";%>
            </logic:present>
           <td  nowrap="nowrap">
           <div  style="<%=brk2commdisplay%>" id="brk2commdisplay">
           <table>
           <tr>
           <td id="brk2Nme"><bean:write property="value(brk2)" name="mixsaleDeliveryForm" /></td><td><html:radio property="value(brk2paid)"  styleId="brk2paid1" value="Y" name="mixsaleDeliveryForm"/> </td><td>&nbsp;Yes</td> <td><html:radio property="value(brk2paid)"  styleId="brk2paid2"   value="N" name="mixsaleDeliveryForm"/></td><td> &nbsp;No</td> <td><html:text property="value(brk2comm)" styleId="brk2comm" readonly="true" size="3" name="mixsaleDeliveryForm" /> </td>
           </tr>
           </table>
           </div>
           </td>
           <logic:present property="value(brk3comm)" name="mixsaleDeliveryForm" >
           <%brk3commdisplay="display:block";%> 
           </logic:present>
           <td  nowrap="nowrap">
           <div style="<%=brk3commdisplay%>" id="brk3commdisplay">
           <table>
           <tr>
           <td id="brk3Nme"><bean:write property="value(brk3)" name="mixsaleDeliveryForm" /></td><td><html:radio property="value(brk3paid)"   styleId="brk3paid1" value="Y" name="mixsaleDeliveryForm"/> </td><td>&nbsp;Yes</td> <td><html:radio property="value(brk3paid)"  styleId="brk3paid2" value="N" name="mixsaleDeliveryForm"/></td><td> &nbsp;No</td>  <td><html:text property="value(brk3comm)" styleId="brk3comm" readonly="true" size="3" name="mixsaleDeliveryForm" /> </td>
           </tr>
           </table>
           </div>
           </td>
            <logic:present property="value(brk4comm)" name="mixsaleDeliveryForm" >
           <%brk4commdisplay="display:block";%>  
           </logic:present>
           <td  nowrap="nowrap">
           <div style="<%=brk4commdisplay%>" id="brk4commdisplay">
           <table>
           <tr>
           <td id="brk4Nme"><bean:write property="value(brk4)" name="mixsaleDeliveryForm" /></td><td><html:radio property="value(brk4paid)"   styleId="brk4paid1"  value="Y" name="mixsaleDeliveryForm"/> </td><td>&nbsp;Yes</td> <td><html:radio property="value(brk4paid)"   styleId="brk4paid2" value="N" name="mixsaleDeliveryForm"/></td><td> &nbsp;No</td> <td><html:text property="value(brk4comm)" styleId="brk4comm" readonly="true" size="3" name="mixsaleDeliveryForm" /> </td>
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
            
            
            <td><span class="pgTtl" >Remark :</span></td><td>  <html:text property="value(rmk1)" name="mixsaleDeliveryForm"/> </td>
            </tr>
            </table>
            </div></td>
            </tr>
           
 <!--<tr><td><span class="pgTtl" >Extra Charge : </span><input type="text" id="echarge" name="echarge" onchange="isNumericDecimal(this.id)"/></td></tr>-->
  <tr><td>
            <label class="pgTtl">Sale Packets</label>
            Create Excel <img src="../images/ico_file_excel.png" onclick="goTo('<%=info.getReqUrl()%>/mix/saleDelivery.do?method=createXL','','')" />
            <%
             ArrayList prps = (session.getAttribute("mixdlvViewLst") == null)?new ArrayList():(ArrayList)(ArrayList)session.getAttribute("mixdlvViewLst");
             itemHdr.add("sale Id");itemHdr.add("Packet Code");
             %>  </td></tr>
             <tr><td>
            <table class="grid1">
                <tr>
                    <th>Sr</th>
                    <th>sale Id</th>
                     <th>Date</th>
                      <th>Packet Code</th>
                      <th>Qty</th>
                     <%for(int j=0; j < prps.size(); j++) {
                     String lprp=(String)prps.get(j);
                     if(prpDspBlocked.contains(lprp)){
                     }else{
                     itemHdr.add(lprp);%>
                        <th><%=lprp%></th>
                    <%}}%>
                    <th>RapRte</th>
                    <th>Dis</th>
                    <th>Prc / Crt </th>
                    <th>ByrDis</th>
                    <th>Quot</th>
        
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
        
              
                
                    <!--<th><html:radio property="value(slRd)"  styleId="slRd" name="mixsaleDeliveryForm" onclick="ALLRedio('slRd' ,'SL_');SetCalculationDlvTll('SL')"  value="SL"/>&nbsp;Sale</th>
                    <th><html:radio property="value(slRd)" styleId="dlvRd" name="mixsaleDeliveryForm" onclick="ALLRedio('dlvRd' ,'DLV_');SetCalculationDlvTll('DLV')"  value="DLV"/>&nbsp;Delivery</th>
                    <th><html:radio property="value(slRd)" styleId="rtRd" name="mixsaleDeliveryForm" onclick="ALLRedio('rtRd' ,'RT_');SetCalculationDlvTll('RT')"  value="RT"/>&nbsp;Return</th>
                    <th> <html:radio property="value(slRd)" styleId="invRd" name="mixsaleDeliveryForm" onclick="ALLRedio('invRd' ,'INV_');SetCalculationDlvTll('INV')"  value="INV"/>&nbsp;Performa Inv </th>-->
            <%pageList=(ArrayList)pageDtl.get("RADIOHDR");
            if(pageList!=null && pageList.size() >0){
            for(int j=0;j<pageList.size();j++){
            pageDtlMap=(HashMap)pageList.get(j);
            fld_nme="value("+(String)pageDtlMap.get("fld_nme")+")";
            fld_ttl=(String)pageDtlMap.get("fld_ttl");
            fld_typ=(String)pageDtlMap.get("fld_typ");
            dflt_val=(String)pageDtlMap.get("dflt_val");
            val_cond=(String)pageDtlMap.get("val_cond"); %>
            <th><html:radio property="<%=fld_nme%>" styleId="<%=fld_typ%>" name="mixsaleDeliveryForm" onclick="<%=val_cond%>"  value="<%=dflt_val%>"/><%=fld_ttl%> </th>
            <%}}%>
                   
                </tr>
            <%
            itemHdr.add("RapRte");itemHdr.add("Dis");itemHdr.add("Prc / Crt");itemHdr.add("orgDis");itemHdr.add("ByrDis");itemHdr.add("org Quot");itemHdr.add("Byr Quot");itemHdr.add("orgamt");itemHdr.add("byramt");
             int count =0 ;
             
             
                for(int i=0; i < pkts.size(); i++) {%>
                <tr>
                <td><%=(i+1)%></td>
                <%    count=i+1;
                    PktDtl pkt = (PktDtl)pkts.get(i);
                    long pktIdn = pkt.getPktIdn();
                    String saleIdn = pkt.getSaleId();
                    String cbPrp = "value(upd_" +saleIdn+"_"+ pktIdn + ")";
                    String sttPrp = "value(stt_" +saleIdn+"_"+ pktIdn + ")" ;
                    String invPrp = "value(INV_" + pktIdn + ")" ;
                    String rdISId = "DLV_"+count;
                    String rdSLId = "SL_"+count;
                    String rdRTId = "RT_"+count;
                    String rdInvId = "INV_"+count;
                    String onClickAp = "SetCalculationDlvMix('"+pktIdn+"','DLV','"+pkt.getSaleId()+"')";
                    String onClickSl = "SetCalculationDlvMix('"+pktIdn+"','SL','"+pkt.getSaleId()+"')";
                    String onClickRt = "SetCalculationDlvMix('"+pktIdn+"','RT','"+pkt.getSaleId()+"')";
                    String rmkTxt =  "value(rmk_" +saleIdn+"_"+ pktIdn + ")" ;
                    if(saleIdnLst.indexOf(saleIdn)<=-1){
                    if(saleIdnLst.equals("")){
                    saleIdnLst=saleIdn;
                    }else
                    saleIdnLst+=","+saleIdn;
                    }
                    
                    
                    pktPrpMap = new HashMap();
                    pktPrpMap.put("sale Id",util.nvl(pkt.getSaleId()));pktPrpMap.put("Packet Code",util.nvl(pkt.getVnm()));
             %>
                <td><%=pkt.getSaleId()%>    
                <input type="hidden" id="STKIDN_<%=count%>" value="<%=pktIdn%>" />
               </td>
                <td nowrap="nowrap"><%=pkt.getValue("dte")%></td>
                 <td><%=pkt.getVnm()%></td>
                 <td><%=pkt.getQty()%></td>
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
                <td><%=util.nvl(pkt.getDis())%></td>
                <td><%=util.nvl(pkt.getRte())%>
                <input type="hidden" id="<%=count%>_quot" value="<%=pkt.getRte()%>" /> 
                <input type="hidden" id="<%=count%>_fnl" value="<%=pkt.getMemoQuot()%>" />
                </td>
                <td><%=util.nvl(pkt.getByrDis())%></td>
                <td><%=util.nvl(pkt.getMemoQuot())%></td>
                
                
                
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
                
                
                <!--<td><html:radio property="<%=sttPrp%>" styleId="<%=rdSLId%>" name="mixsaleDeliveryForm" onclick="<%=onClickSl%>"  value="SL"/></td>
                <td><html:radio property="<%=sttPrp%>" styleId="<%=rdISId%>" name="mixsaleDeliveryForm" onclick="<%=onClickAp%>"  value="DLV"/></td>
                 <td nowrap="nowrap"><html:radio property="<%=sttPrp%>" styleId="<%=rdRTId%>" name="mixsaleDeliveryForm" onclick="<%=onClickRt%>" value="RT"/>   <html:text property="<%=rmkTxt%>" size="10"  /></td>
                   <td><html:radio property="<%=sttPrp%>" styleId="<%=rdInvId%>" name="mixsaleDeliveryForm"  value="<%=String.valueOf(pktIdn)%>"/></td>
               -->
            <%pageList=(ArrayList)pageDtl.get("RADIOBODY");
            if(pageList!=null && pageList.size() >0){
            for(int j=0;j<pageList.size();j++){
            pageDtlMap=(HashMap)pageList.get(j);
            fld_nme="value("+(String)pageDtlMap.get("fld_nme")+saleIdn+"_"+pktIdn+")";
            fld_ttl=(String)pageDtlMap.get("fld_ttl");
            fld_typ=(String)pageDtlMap.get("fld_typ")+count;
            dflt_val=util.nvl((String)pageDtlMap.get("dflt_val")).trim();
            if(dflt_val.equals("PKT"))
            dflt_val=String.valueOf(pktIdn);
            val_cond=(String)pageDtlMap.get("val_cond");
            val_cond=val_cond.replaceAll("PKTIDN",String.valueOf(pktIdn));
            val_cond=val_cond.replaceAll("SALEID",pkt.getSaleId());%>
            <td nowrap="nowrap"><html:radio property="<%=fld_nme%>" styleId="<%=fld_typ%>"  name="mixsaleDeliveryForm"  onclick="<%=val_cond%>"  value="<%=dflt_val%>"/>
            <%if(dflt_val.equals("RT")){%>
            <html:text property="<%=rmkTxt%>" size="10"  />
            <%}%>
            </td>
            <%}}%>
            
                </tr>
              <% pktPrpMap.put("RapRte",util.nvl((String)pkt.getRapRte()));pktPrpMap.put("Dis",util.nvl((String)pkt.getDis()));pktPrpMap.put("Prc / Crt",util.nvl((String)pkt.getRte()));pktPrpMap.put("ByrDis",util.nvl((String)pkt.getByrDis()));
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
              <input type="hidden" id="saleIdnLst" value="<%=saleIdnLst%>" />
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

</table></td></tr>
<tr>
    <td><jsp:include page="/footer.jsp" /> </td>
    </tr></table>
  
  <%if(request.getAttribute("SLMSG")!=null){%>
 <script type="text/javascript">
 <!--
 var url = document.getElementById('rptUrl').value;
 windowOpen(url,'_blank')
 -->
 </script>
 <%}%>
  </body></html>
