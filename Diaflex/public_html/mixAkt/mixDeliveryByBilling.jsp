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
ArrayList prpDspBlocked = info.getPageblockList();
 ArrayList  pageList = new ArrayList();
 HashMap pageDtlMap=new HashMap();
HashMap allPageDtl= (info.getPageDetails() == null)?new HashMap():(HashMap)info.getPageDetails();
HashMap pageDtl=(HashMap)allPageDtl.get("MIXDELIVERY_CONFIRMATION");
 String fld_nme="",fld_ttl="",val_cond="",dflt_val="",fld_typ="",form_nme="",lov_qry="";
        String logId=String.valueOf(info.getLogId());
        String onfocus="cook('"+logId+"')";
        %>
 <body onfocus="<%=onfocus%>">
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
     %>
  </table>
  </td>
  </tr>
  <tr><td valign="top" class="tdLayout">
<html:form action="/mixAkt/mixDeliveryByBillingAction.do?method=loadPkt" method="POST" >
<table><tr><td valign="top">
  <table class="grid1" >
  <tr><th colspan="2">Sale Search </th></tr>
 <tr>
   <td>Buyer Name :</td>
    <td>
   <html:select property="invByrIdn" name="mixDeliveryForm" onchange="getByrFromBill(this,'MIX')" styleId="byrId" >
    <html:option value="0">---select---</html:option>
    <html:optionsCollection property="invByrLst" name="mixDeliveryForm"  value="byrIdn" label="byrNme" />
    </html:select>
  </td></tr>
  <!--<tr><td colspan="2" ><div id="byr"></div> </td></tr>-->
  <tr><td>Packets. </td><td>
<html:textarea property="value(vnmLst)" name="mixDeliveryForm" styleId="vnmLst" /> </td> </tr>
<tr><td colspan="2"><html:submit property="submit" value="View" styleClass="submit"/></td></tr>
  </table>


  </td>
  <td valign="top">
        <div id="byr"></div>
  </td>
  </tr></table>  </html:form></td>
  </tr>
  
  <tr><td valign="top" class="tdLayout">
   <html:form action="/mixAkt/mixDeliveryByBillingAction.do?method=save" method="POST"  onsubmit="return confirmChanges()">
  <html:hidden property="value(exhRte)"  styleId="exhRte" name="mixDeliveryForm" />
 
  <table>
  <%if(request.getAttribute("view")!=null){
  ArrayList itemHdr = new ArrayList();
             ArrayList pktList = new ArrayList();
  String aadatcommdisplay="display:none",brk1commdisplay="display:none",brk2commdisplay="display:none",brk3commdisplay="display:none",brk4commdisplay="display:none";
     ArrayList pkts = (ArrayList)info.getValue("PKTS");
     HashMap avgdtl=(HashMap)request.getAttribute("avgdtl");
   if(pkts!=null && pkts.size()>0){%>
  <tr><td><table> <tr>
    <td><span class="pgTtl" >Exiting Selection</span></td>
     <td> <b>Terms : </b> </span></td><td>
          <bean:write property="value(trmsLb)" name="mixDeliveryForm" />
          <html:hidden property="relIdn" name="mixDeliveryForm" />
      </td>
                 <td> <b>Buyer :</b> </span></td><td>
                <bean:write property="byr" name="mixDeliveryForm" />
                 <html:hidden property="nmeIdn" name="mixDeliveryForm"  styleId="nmeIdn"  />
                </td>
    </tr></table>
  </td>
  </tr>
    <tr><td>
            <table>
                <tr>
              
             
            <td><span class="pgTtl" >Buyer List</span></td>
                <td>
                <html:select property="value(invByrIdn)" name="mixDeliveryForm"  styleId="byrId1" onchange="GetADDR();GetBank()" >
               <html:option value="0" >---select---</html:option>
                <html:optionsCollection property="invByrLst" name="mixDeliveryForm"  value="byrIdn" label="byrNme" />
                
                </html:select>
                </td>  <td> <span class="pgTtl" >Address </span></td>
                <td><html:select property="value(invAddr)" styleId="addrId" name="mixDeliveryForm"  >
                
                 <html:optionsCollection property="invAddLst" name="mixDeliveryForm"   value="idn" label="addr" />
                </html:select>
                
               </td>
                  <td> <span class="pgTtl" >Terms </span></td><td>
             
             <html:select property="value(invByrTrm)" name="mixDeliveryForm"  styleId="rlnId1"  onchange="invTermsDtls();" >
         
             <html:optionsCollection property="invTrmsLst" name="mixDeliveryForm"
            label="trmDtl" value="relId" />
            
            </html:select>
            </td>
            </tr></table></td></tr>
            
              <tr><td><table>
            <tr>
            <td nowrap="nowrap"><span class="pgTtl" >Company Name</span></td>
          <td>
           <html:select property="value(grpIdn)" name="mixDeliveryForm"  styleId="grpIdn"  >
             <html:optionsCollection property="groupList" name="mixDeliveryForm"
             value="idn" label="addr" />
            </html:select>
          </td>
                <td nowrap="nowrap"><span class="pgTtl" >Bank Selection</span></td>
               <td>
             
             <html:select property="value(bankIdn)" name="mixDeliveryForm" onchange="SetBankAddr(this)"  styleId="bankIdn"  >
             <html:optionsCollection property="bankList" name="mixDeliveryForm"
             value="idn" label="addr" />
            
            </html:select>
            </td>
            <td nowrap="nowrap"> <html:select property="value(bankAddr)" name="mixDeliveryForm" style="dispaly:none" styleId="bankAddr">
             <html:optionsCollection property="bnkAddrList" name="mixDeliveryForm"
             value="idn" label="addr" />
            </html:select>
            
            </td>
            </tr>
            
            </table></td>
        </tr>
        
         <tr>
            <td nowrap="nowrap"><table><tr><td><span class="pgTtl" >Courier :</span> </td>
             <td> <html:select property="value(courier)" name="mixDeliveryForm" style="dispaly:none" styleId="courier">
            <html:optionsCollection property="courierList" name="mixDeliveryForm"
             value="idn" label="addr" />
            </html:select>
            </td>
            <%if(cnt.equalsIgnoreCase("hk")){%>
            <td nowrap="nowrap"><span class="pgTtl" >Bank through :</span></td><td>
            <html:select property="value(throubnk)" name="mixDeliveryForm"  styleId="throubnk">
            <html:option value="0">-----select bank through-----</html:option>
            <html:optionsCollection property="thruBankList" name="mixDeliveryForm"
             value="idn" label="fnme" />
            </html:select>
            </td><%}%>
            </tr></table> </td> </tr>
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
            <tr>
              <td><table><tr><td><span class="pgTtl" >Commission :</span> </td>
            <html:hidden property="value(aadatcommval)" styleId="aadatcommval" name="mixDeliveryForm" value="0"/>
            <html:hidden property="value(brk1commval)" styleId="brk1commval" name="mixDeliveryForm" value="0"/>
            <html:hidden property="value(brk2commval)" styleId="brk2commval"  name="mixDeliveryForm" value="0"/>
            <html:hidden property="value(brk3commval)" styleId="brk3commval"  name="mixDeliveryForm" value="0"/>
            <html:hidden property="value(brk4commval)" styleId="brk4commval"  name="mixDeliveryForm" value="0"/>
                        <logic:present property="value(aadatcomm)" name="mixDeliveryForm" >
            <%aadatcommdisplay="display:block";%>
            </logic:present>
            <td nowrap="nowrap">
            <div style="<%=aadatcommdisplay%>" id="aadatcommdisplay">
            <table>
            <tr>
            <td><span class="pgTtl" >Aadat :</span> </td>
            <td>
            <span id="aaDatNme"><bean:write property="value(aaDat)" name="mixDeliveryForm" /></span> :
            <span id="aaDatComm"><bean:write property="value(aadatcomm)" name="mixDeliveryForm" /></span> </td>
            <td><html:radio property="value(aadatpaid)" onchange="setBrokerComm('aadatcomm','Y')" styleId="aadatpaid1"  value="Y"  name="mixDeliveryForm" disabled="<%=isdisabled%>"/> </td>
            <td>&nbsp;Yes</td> 
            <td><html:radio property="value(aadatpaid)"  onchange="setBrokerComm('aadatcomm','N')" styleId="aadatpaid2" value="N" name="mixDeliveryForm" disabled="<%=isdisabled%>"/></td>
            <td>&nbsp;No</td>
            <td><html:text property="value(aadatcomm)" styleId="aadatcomm" readonly="true" size="3" name="mixDeliveryForm" /> </td>
            </tr>
            </table>
            </div>
            </td>
            <logic:present property="value(brk1comm)" name="mixDeliveryForm" >
            <%brk1commdisplay="display:block";%>
           </logic:present>
           <td nowrap="nowrap">
           <div  style="<%=brk1commdisplay%>" id="brk1commdisplay">
           <table>
           <tr>
           <td><span class="pgTtl" >Broker :</span> </td>
           <td id="brk1Nme"><bean:write property="value(brk1)" name="mixDeliveryForm" /></td><td><html:radio property="value(brk1paid)" styleId="brk1paid1" value="Y" name="mixDeliveryForm" disabled="<%=isdisabled%>"/></td><td>&nbsp;Yes</td>  <td><html:radio property="value(brk2paid)"  styleId="brk1paid2"  value="N" name="mixDeliveryForm" disabled="<%=isdisabled%>"/></td><td>&nbsp;No</td> <td><html:text property="value(brk1comm)"  styleId="brk1comm"  readonly="true" size="3" name="mixDeliveryForm" /> </td>
           </tr>
           </table>
           </div>
           </td>
            <logic:present property="value(brk2comm)" name="mixDeliveryForm" >
            <%brk2commdisplay="display:block";%>
            </logic:present>
           <td  nowrap="nowrap">
           <div  style="<%=brk2commdisplay%>" id="brk2commdisplay">
           <table>
           <tr>
           <td id="brk2Nme"><bean:write property="value(brk2)" name="mixDeliveryForm" /></td><td><html:radio property="value(brk2paid)"  styleId="brk2paid1" value="Y" name="mixDeliveryForm" disabled="<%=isdisabled%>"/> </td><td>&nbsp;Yes</td> <td><html:radio property="value(brk2paid)"  styleId="brk2paid2"   value="N" name="mixDeliveryForm" disabled="<%=isdisabled%>"/></td><td> &nbsp;No</td> <td><html:text property="value(brk2comm)" styleId="brk2comm" readonly="true" size="3" name="mixDeliveryForm" /> </td>
           </tr>
           </table>
           </div>
           </td>
           <logic:present property="value(brk3comm)" name="mixDeliveryForm" >
           <%brk3commdisplay="display:block";%> 
           </logic:present>
           <td  nowrap="nowrap">
           <div style="<%=brk3commdisplay%>" id="brk3commdisplay">
           <table>
           <tr>
           <td id="brk3Nme"><bean:write property="value(brk3)" name="mixDeliveryForm" /></td><td><html:radio property="value(brk3paid)"   styleId="brk3paid1" value="Y" name="mixDeliveryForm" disabled="<%=isdisabled%>"/> </td><td>&nbsp;Yes</td> <td><html:radio property="value(brk3paid)"  styleId="brk3paid2" value="N" name="mixDeliveryForm" disabled="<%=isdisabled%>"/></td><td> &nbsp;No</td>  <td><html:text property="value(brk3comm)" styleId="brk3comm" readonly="true" size="3" name="mixDeliveryForm" /> </td>
           </tr>
           </table>
           </div>
           </td>
            <logic:present property="value(brk4comm)" name="mixDeliveryForm" >
           <%brk4commdisplay="display:block";%>  
           </logic:present>
           <td  nowrap="nowrap">
           <div style="<%=brk4commdisplay%>" id="brk4commdisplay">
           <table>
           <tr>
           <td id="brk4Nme"><bean:write property="value(brk4)" name="mixDeliveryForm" /></td><td><html:radio property="value(brk4paid)"   styleId="brk4paid1"  value="Y" name="mixDeliveryForm" disabled="<%=isdisabled%>"/> </td><td>&nbsp;Yes</td> <td><html:radio property="value(brk4paid)"   styleId="brk4paid2" value="N" name="mixDeliveryForm" disabled="<%=isdisabled%>"/></td><td> &nbsp;No</td> <td><html:text property="value(brk4comm)" styleId="brk4comm" readonly="true" size="3" name="mixDeliveryForm" /> </td>
           </tr>
           </table>
           </div>
           </td>
            </tr></table> </td>
            </tr>
              <tr><td><span class="pgTtl" > Remark: &nbsp;</span> <html:text property="value(rmk)" name="mixDeliveryForm" />  </td></tr>
          
            <tr>
            <td><div id="totalDiv">
            <table>
            <tr><td><span class="pgTtl" >Delivery Selection</span></td><td>Pcs:</td><td><b> <label id="ttlqty"></label></b></td>
            <td>Cts:</td><td><b><label id="ttlcts"></label></b></td>
            <td>Discount:</td><td><b><label id="ttldis"></label></b></td>
           <td>Value:</td><td><b><label id="vlu"></label></b></td>
                  <td>Create Excel</td> <td><img src="../images/ico_file_excel.png" onclick="goTo('<%=info.getReqUrl()%>/marketing/memoReturn.do?method=createXL','','')" /></td>

           </tr></table></div></td></tr>
                 <tr><td>
                 <%
                ArrayList prps = (ArrayList)session.getAttribute("MIX_VIEW");%>
            <table class="grid1">
                <tr>
                    <th>Sr</th>
             <%
              itemHdr.add("BYR");
             itemHdr.add("SALID");
             itemHdr.add("SALEDTE");
             itemHdr.add("Packet Code");
             pageList=(ArrayList)pageDtl.get("RADIOHDR");
            if(pageList!=null && pageList.size() >0){
            for(int j=0;j<pageList.size();j++){
            pageDtlMap=(HashMap)pageList.get(j);
            fld_nme="value("+(String)pageDtlMap.get("fld_nme")+")";
            fld_ttl=(String)pageDtlMap.get("fld_ttl");
            fld_typ=(String)pageDtlMap.get("fld_typ");
            dflt_val=(String)pageDtlMap.get("dflt_val");
            val_cond=(String)pageDtlMap.get("val_cond"); %>
            <th><html:radio property="<%=fld_nme%>" styleId="<%=fld_typ%>" name="mixDeliveryForm" onclick="<%=val_cond%>"  value="<%=dflt_val%>"/><%=fld_ttl%> </th>
            <%}}%>
                    <th>sale Id</th>
                     <th nowrap="nowrap">sale Date</th>
                      <th>Packet Code</th>
                     <%for(int j=0; j < prps.size(); j++) {
                     String lprp=(String)prps.get(j);
                     if(prpDspBlocked.contains(lprp)){
                     }else{
                     
                     itemHdr.add(lprp);
                  %>
                        <th><%=lprp%></th>
                    <%}}%>
                   
                    <th>Quot</th>
                    <th>Byr Amt</th>
        <%                   itemHdr.add("BYRDIS");itemHdr.add("QUOT");itemHdr.add("AMT");
   %>
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
        
              
                
                    <!--<th><html:radio property="value(slRd)"  styleId="slRd" name="mixDeliveryForm" onclick="ALLRedio('slRd' ,'SL_');SetCalculationDlvTll('SL')"  value="SL"/>&nbsp;Sale</th>
                    <th><html:radio property="value(slRd)" styleId="dlvRd" name="mixDeliveryForm" onclick="ALLRedio('dlvRd' ,'DLV_');SetCalculationDlvTll('DLV')"  value="DLV"/>&nbsp;Delivery</th>
                    <th><html:radio property="value(slRd)" styleId="rtRd" name="mixDeliveryForm" onclick="ALLRedio('rtRd' ,'RT_');SetCalculationDlvTll('RT')"  value="RT"/>&nbsp;Return</th>
                    <th> <html:radio property="value(slRd)" styleId="invRd" name="mixDeliveryForm" onclick="ALLRedio('invRd' ,'INV_');SetCalculationDlvTll('INV')"  value="INV"/>&nbsp;Performa Inv </th>-->                  
                </tr>
            <%
           
             int count =0 ;

            for(int i=0; i < pkts.size(); i++) {
                  HashMap pktDtl = new HashMap();
                  PktDtl pkt = (PktDtl)pkts.get(i);
            %>
                <tr>
                <td><%=(i+1)%></td>
                <%    count=i+1;
                    long pktIdn = pkt.getPktIdn();
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
                     pktDtl.put("BYR", pkt.getValue("ByrNme"));
                     pktDtl.put("SALID", pkt.getSaleId());
                     pktDtl.put("SALEDTE", pkt.getValue("SALEDTE"));
                     pktDtl.put("Packet Code",pkt.getVnm());
                    
             %>
            <%pageList=(ArrayList)pageDtl.get("RADIOBODY");
            if(pageList!=null && pageList.size() >0){
            for(int j=0;j<pageList.size();j++){
            pageDtlMap=(HashMap)pageList.get(j);
            fld_nme="value("+(String)pageDtlMap.get("fld_nme")+""+pktIdn+")";
            fld_ttl=(String)pageDtlMap.get("fld_ttl");
            fld_typ=(String)pageDtlMap.get("fld_typ")+count;
            dflt_val=util.nvl((String)pageDtlMap.get("dflt_val")).trim();
            if(dflt_val.equals("PKT"))
            dflt_val=String.valueOf(pktIdn);
            val_cond=(String)pageDtlMap.get("val_cond");
            val_cond=val_cond.replaceAll("PKTIDN",String.valueOf(pktIdn));
            val_cond=val_cond.replaceAll("SALEID",pkt.getSaleId());%>
            <td nowrap="nowrap"><html:radio property="<%=fld_nme%>" styleId="<%=fld_typ%>"  name="mixDeliveryForm"  onclick="<%=val_cond%>"  value="<%=dflt_val%>"/>
            <%if(dflt_val.equals("RT")){%>
            <html:text property="<%=rmkTxt%>" size="10"  />
            <%}%>
            </td>
            <%}}%>
                <td><%=pkt.getSaleId()%>    
                <input type="hidden" id="STKIDN_<%=count%>" value="<%=pktIdn%>" />
               </td>
                 <td nowrap="nowrap"><%=pkt.getValue("SALEDTE")%></td>
                 <td><%=pkt.getVnm()%></td>
                <%for(int j=0; j < prps.size(); j++) {
                      String lprp = (String)prps.get(j);
                      if(prpDspBlocked.contains(lprp)){
                      }else{
                     pktDtl.put(lprp,util.nvl(pkt.getValue((String)prps.get(j))));
                %>
                    <td nowrap="nowrap"><%=util.nvl(pkt.getValue((String)prps.get(j)))%>
                      <%}if(lprp.equals("CRTWT")){%>
                    <input type="hidden" id="<%=count%>_cts" value="<%=util.nvl(pkt.getValue((String)prps.get(j)))%>" /> 
                    <%}%>
                    </td>
                    
                <%}
                %>
               
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
                
                
                <!--<td><html:radio property="<%=sttPrp%>" styleId="<%=rdSLId%>" name="mixDeliveryForm" onclick="<%=onClickSl%>"  value="SL"/></td>
                <td><html:radio property="<%=sttPrp%>" styleId="<%=rdISId%>" name="mixDeliveryForm" onclick="<%=onClickAp%>"  value="DLV"/></td>
                 <td nowrap="nowrap"><html:radio property="<%=sttPrp%>" styleId="<%=rdRTId%>" name="mixDeliveryForm" onclick="<%=onClickRt%>" value="RT"/>   <html:text property="<%=rmkTxt%>" size="10"  /></td>
                   <td><html:radio property="<%=sttPrp%>" styleId="<%=rdInvId%>" name="mixDeliveryForm"  value="<%=String.valueOf(pktIdn)%>"/></td>
               -->

            
                </tr>
                    
              <%
              
               pktDtl.put("RapRte",util.nvl(pkt.getRapRte()));
                pktDtl.put("RapVlu",util.nvl(pkt.getValue("rapVlu")));
                 pktDtl.put("Dis",util.nvl(pkt.getDis()));
                  pktDtl.put("RTE",util.nvl(pkt.getRte()));
                   pktDtl.put("BYRDIS",util.nvl(pkt.getByrDis()));
                   pktDtl.put("QUOT",util.nvl(pkt.getMemoQuot()));
                   pktDtl.put("AMT",util.nvl(pkt.getValue("byramt")));
                 pktList.add(pktDtl);
                }
               
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
            if(fld_typ.equals("S")){
            %>
    <html:submit property="<%=fld_nme%>" value="<%=fld_ttl%>" onclick="<%=val_cond%>" styleClass="submit"/>&nbsp;
    <%}else if(fld_typ.equals("B")){%>
    <html:button property="<%=fld_nme%>" value="<%=fld_ttl%>" onclick="<%=val_cond%>" styleClass="submit"/>&nbsp;
    <%}}}
    
    %>
                         
            </p></td></tr>
            
  <%}else{%>
   <tr><td></td>
  </tr>
  <%}
     session.setAttribute("pktList", pktList);
     session.setAttribute("itemHdr", itemHdr);
  
  }%>
  </table>
  </html:form>
  </td></tr>
  <tr>
    <td><jsp:include page="/footer.jsp" /> </td>
    </tr>
  </table>
  </body>
</html>