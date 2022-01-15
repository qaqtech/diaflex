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
    <title>Branch Delivery</title>
    <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/bse.css?v=<%=info.getJsVersion()%>"/>
    <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/style.css?v=<%=info.getJsVersion()%>"/>
    <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/memoScript.js?v=<%=info.getJsVersion()%> " > </script>
    <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/bse.js?v=<%=info.getJsVersion()%> " > </script>
    <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/assortScript.js?v=<%=info.getJsVersion()%> " > </script>
    <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/Validation.js " > </script>
    <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/priceChange.js?v=<%=info.getJsVersion()%> " > </script>
   <script language="javaScript" type="text/javascript" src="<%=info.getReqUrl()%>/calendar/calendar.js"></script>
   <link href="<%=info.getReqUrl()%>/calendar/calendar.css" rel="stylesheet" type="text/css">
  </head>
  <%
  HashMap dbinfo = info.getDmbsInfoLst();
String cnt = (String)dbinfo.get("CNT");
String logId=String.valueOf(info.getLogId());
String onfocus="cook('"+logId+"')";
String pkt_ty = util.nvl(request.getParameter("PKT_TY"));
  %>
   <body onfocus="<%=onfocus%>" >
<%

HashMap pageDtlMap=new HashMap();
String fld_nme="",fld_ttl="",val_cond="",dflt_val="",fld_typ="",form_nme="",lov_qry="";
HashMap allPageDtl= (info.getPageDetails() == null)?new HashMap():(HashMap)info.getPageDetails();
HashMap pageDtl=(HashMap)allPageDtl.get("BRNCH_DELIVERY");
ArrayList pageList=new ArrayList();
      pageList= ((ArrayList)pageDtl.get("MEMODTL") == null)?new ArrayList():(ArrayList)pageDtl.get("MEMODTL");
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
       <option value="id">Dlv Id</option>     
        <option value="dte">Date</option>     
      </select>
    <%}%>
        

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
  <img src="../images/line.jpg" border="0" /></td><td valign="middle"> <span class="pgHed">Branch Delivery</span> </td>
</tr></table></td></tr>
 <% if(request.getAttribute("msg")!=null){%>
<tr><td valign="top" class="tdLayout">
<span class="redLabel"><%=request.getAttribute("msg")%></span>
</td></tr>
<%}%>
<tr><td valign="top" class="tdLayout">
  <html:form action="/marketing/branceDelivery.do?method=fetch" method="POST" >
  <input type="hidden" name="PKT_TY" id="PKT_TY" value="<%=pkt_ty%>"/>
   <table><tr><td valign="top">
  <table class="grid1" >
  <tr><th colspan="2">Branch Delivery</th></tr>
   <tr>
<td>Buyer Name :</td>
<td>
<html:select property="value(byrIdn)" name="branchDeliveryForm" styleId="byrId" >
<html:option value="0">---select---</html:option>
<html:optionsCollection property="byrList" name="branchDeliveryForm"  value="byrIdn" label="byrNme" />             
</html:select>         
</td></tr> 
<tr>
<td>Shipment Date :</td>
<td><html:text property="value(shpfrmdte)" styleId="shpfrmdte" name="branchDeliveryForm" size="8" /><img src="<%=info.getReqUrl()%>/images/calender.png" onClick="setYears(2011, <%=info.getCurrentYear()%>);showCalender(this, 'shpfrmdte');"> 
To&nbsp; <html:text property="value(shptodte)" styleId="shptodte" name="branchDeliveryForm"  size="8"/> <img src="<%=info.getReqUrl()%>/images/calender.png" onClick="setYears(2011, <%=info.getCurrentYear()%>);showCalender(this, 'shptodte');"></td>
</tr> 
<tr><td colspan="2" align="center"> Or</td></tr>
<!--<tr>
<td>Billing  Party :</td>
<td>
 <html:select property="value(prtyIdn)" name="branchDeliveryForm" onchange="getBrncTrms(this.value)" styleId="prtyId" >
    <html:option value="0">---select---</html:option>    
  </html:select>
</td></tr> 
<tr>
<td>Terms :</td><td>
<html:select property="value(relIdn)" name="branchDeliveryForm"  onchange="getBrnchIdn()" styleId="rlnId"  >
     <html:option value="0">---select---</html:option>
</html:select>

</td>
</tr>-->
<tr><td>Packets. </td><td>
<html:textarea property="value(vnmLst)" name="branchDeliveryForm" styleId="vnmLst" /> </td> </tr>
<tr><td colspan="2"><html:submit property="submit" value="View" styleClass="submit"/></td></tr>           
</table>
</td><td valign="top">
<div id="memoIdn"></div>
</td>
 <%if(request.getAttribute("view")!=null ){%>
     <td  valign="top">
    <table class="grid1">
    <tr> <td></td><td><b>Received</b></td><td><b>Payment Received</b></td><td><b>Branch AV</b></td><td><b>Delivery</b></td> </tr>
    <tr> <td><b>Qty</b></td> <td><span id="rc_qty"><bean:write property="value(rc_qty)" name="branchDeliveryForm" /></span></td><td><span id="pr_qty"><bean:write property="value(pr_qty)" name="branchDeliveryForm" /></span></td><td><span id="av_qty">0</span></td><td><span id="qty">0</span></td> </tr>
    <tr> <td><b>Cts</b> </td> <td><span id="rc_cts"><bean:write property="value(rc_cts)"  name="branchDeliveryForm" /></span></td> <td><span id="pr_cts"><bean:write property="value(pr_cts)"  name="branchDeliveryForm" /></span></td><td><span id="av_cts">0</span></td><td><span id="cts">0</span></td>  </tr>
    <tr> <td><b>Avg Prc</b></td> <td><span id="rc_avgPrc"><bean:write property="value(rc_avgPrc)"  name="branchDeliveryForm" /></span></td><td><span id="pr_avgPrc"><bean:write property="value(pr_avgPrc)"  name="branchDeliveryForm" /></span></td><td><span id="av_avgPrc">0</span></td> <td><span id="avgPrc">0</span></td>  </tr>
    <tr> <td><b>Avg Dis</b> </td> <td><span id="rc_avgDis"><bean:write property="value(rc_avgDis)"  name="branchDeliveryForm" /></span></td> <td><span id="pr_avgDis"><bean:write property="value(pr_avgDis)"  name="branchDeliveryForm" /></span></td><td><span id="av_avgDis">0</span></td><td><span id="avgDis">0</span></td>  </tr>
    <tr> <td><b>Value </b></td> <td><span id="rc_vlu"><bean:write property="value(rc_vlu)"   name="branchDeliveryForm" /></span></td><td><span id="pr_vlu"><bean:write property="value(pr_vlu)"   name="branchDeliveryForm" /></span></td><td><span id="av_vlu">0</span></td><td><span id="vlu">0</span></td>   </tr>
  <tr><td><b>Buyer</b></td><td colspan="4"><bean:write property="value(byr)" name="branchDeliveryForm" /></td></tr>
   <tr><td><b>Date</b></td><td colspan="4"><bean:write property="value(dte)" name="branchDeliveryForm" /></td></tr>
     </table>
      </td><%}%>
</tr></table>

</html:form>
</td></tr>
<tr><td valign="top" class="tdLayout">

     <html:form action="/marketing/branceDelivery.do?method=save" method="POST"  >
<input type="hidden" name="PKT_TY" id="PKT_TY" value="<%=pkt_ty%>"/>
<%
String view = (String)request.getAttribute("view");
if(view!=null && view.equals("Y")){
ArrayList pkts = (ArrayList)request.getAttribute("pktsList");
if(pkts!=null && pkts.size() >0){
String aadatcommdisplay="display:none",brk1commdisplay="display:none",brk2commdisplay="display:none",brk3commdisplay="display:none",brk4commdisplay="display:none";

 ArrayList prpDspBlocked = info.getPageblockList();
%>
 <html:hidden property="value(location)" styleId="location" name="branchDeliveryForm" />
 <html:hidden property="value(nmeIdn)" name="branchDeliveryForm" />
<html:hidden property="value(saleIdns)" styleId="saleIdns" name="branchDeliveryForm" />  
         
            <html:hidden property="value(exh_rte)" styleId="exhRte" name="branchDeliveryForm" />
    
           <table>
           <tr><td>
           <table>
   <tr><td>
   
   <table>
            
                <tr>
                <td><span class="pgTtl" >Exiting Selection</span></td>
                <td> <b>Terms : </b> </span></td><td>
                <bean:write property="value(trmsLb)" name="branchDeliveryForm" />
                <html:hidden property="relIdn" name="branchDeliveryForm" />
               
                </td>
                 <td> <b>Buyer :</b> </span></td><td>
                <bean:write property="byr" name="branchDeliveryForm" />
                 <html:hidden property="nmeIdn" name="branchDeliveryForm" />
                </td>
        
                
                </tr></table>
   
   </td></tr>
  
   <tr><td>
            <table>
                <tr>
                
            <td><span class="pgTtl" >Buyer List</span></td>
                <td>
                <html:select property="value(invByrIdn)" name="branchDeliveryForm"  styleId="byrId1" onchange="GetADDR()" >
               <html:option value="0" >---select---</html:option>
                <html:optionsCollection property="invByrLst" name="branchDeliveryForm"  value="byrIdn" label="byrNme" />
                
                </html:select>
                </td>  <td><span class="pgTtl" >Address</span></td>
                <td><html:select property="value(invAddr)" styleId="addrId" name="branchDeliveryForm"  >
                
                 <html:optionsCollection property="invAddLst" name="branchDeliveryForm"   value="idn" label="addr" />
                </html:select>
                
               </td>
              
                <td><span class="pgTtl" >Terms </span></td><td>
             
             <html:select property="value(invByrTrm)" name="branchDeliveryForm"  styleId="rlnId1"  onchange="invTermsDtls();" >
         
             <html:optionsCollection property="invTrmsLst" name="branchDeliveryForm"
            label="trmDtl" value="relId" />
            
            </html:select>
            </td>
            </tr></table></td></tr>
           
            <tr><td><table>
            <tr>
            <td nowrap="nowrap"><span class="pgTtl" >Company Name</span></td>
          <td>
           <html:select property="value(grpIdn)" name="branchDeliveryForm"  styleId="grpIdn"  >
             <html:optionsCollection property="groupList" name="branchDeliveryForm"
             value="idn" label="addr" />
            </html:select>
          </td>
                <td nowrap="nowrap"><span class="pgTtl" >Bank Selection</span></td>
               <td>
             
             <html:select property="value(comIdn)" name="branchDeliveryForm" onchange="SetBankAddr(this)"  styleId="bankIdn"  >
             <html:optionsCollection property="bankList" name="branchDeliveryForm"
             value="idn" label="addr" />
            
            </html:select>
            </td>
            <td> <html:select property="value(bankIdn)" name="branchDeliveryForm" style="dispaly:none" styleId="bankAddr">
             <html:optionsCollection property="bnkAddrList" name="branchDeliveryForm"
             value="idn" label="addr" />
            </html:select>
            
            </td>
            </tr>
            
            </table></td>
            
            </tr>
       
                <tr>
                 <tr>
            <td nowrap="nowrap"><table><tr><td><span class="pgTtl" >Courier :</span> </td>
             <td> <html:select property="value(courier)" name="branchDeliveryForm" style="dispaly:none" styleId="courier">
            <html:optionsCollection property="courierList" name="branchDeliveryForm"
             value="idn" label="addr" />
            </html:select>
            </td>
            </tr></table> </td>
            </tr>
            <tr>
            <td><table><tr><td><span class="pgTtl" >Commission :</span> </td>
            <html:hidden property="value(aadatcommval)" styleId="aadatcommval" name="branchDeliveryForm" value="0"/>
            <html:hidden property="value(brk1commval)" styleId="brk1commval" name="branchDeliveryForm" value="0"/>
            <html:hidden property="value(brk2commval)" styleId="brk2commval"  name="branchDeliveryForm" value="0"/>
            <html:hidden property="value(brk3commval)" styleId="brk3commval"  name="branchDeliveryForm" value="0"/>
            <html:hidden property="value(brk4commval)" styleId="brk4commval"  name="branchDeliveryForm" value="0"/>
                        <logic:present property="value(aadatcomm)" name="branchDeliveryForm" >
            <%aadatcommdisplay="display:block";%>
            </logic:present>
            <td nowrap="nowrap">
            <div style="<%=aadatcommdisplay%>" id="aadatcommdisplay">
            <table>
            <tr>
            <td><span class="pgTtl" >Aadat :</span> </td>
            <td>
            <span id="aaDatNme"><bean:write property="value(aaDat)" name="branchDeliveryForm" /></span> :
            <span id="aaDatComm"><bean:write property="value(aadatcomm)" name="branchDeliveryForm" /></span> </td>
            <td><html:radio property="value(aadatpaid)" onchange="setBrokerComm('aadatcomm','Y')" styleId="aadatpaid1"  value="Y"  name="branchDeliveryForm"/> </td>
            <td>&nbsp;Yes</td> 
            <td><html:radio property="value(aadatpaid)"  onchange="setBrokerComm('aadatcomm','N')" styleId="aadatpaid2" value="N" name="branchDeliveryForm"/></td>
            <td>&nbsp;No</td>
            <td><html:text property="value(aadatcomm)" styleId="aadatcomm" readonly="true" size="3" name="branchDeliveryForm" /> </td>
            </tr>
            </table>
            </div>
            </td>
            <logic:present property="value(brk1comm)" name="branchDeliveryForm" >
            <%brk1commdisplay="display:block";%>
           </logic:present>
           <td nowrap="nowrap">
           <div  style="<%=brk1commdisplay%>" id="brk1commdisplay">
           <table>
           <tr>
           <td><span class="pgTtl" >Broker :</span> </td>
           <td id="brk1Nme"><bean:write property="value(brk1)" name="branchDeliveryForm" /></td><td><html:radio property="value(brk1paid)" styleId="brk1paid1" value="Y" name="branchDeliveryForm"/></td><td>&nbsp;Yes</td>  <td><html:radio property="value(brk2paid)"  styleId="brk1paid2"  value="N" name="branchDeliveryForm"/></td><td>&nbsp;No</td> <td><html:text property="value(brk1comm)"  styleId="brk1comm"  readonly="true" size="3" name="branchDeliveryForm" /> </td>
           </tr>
           </table>
           </div>
           </td>
            <logic:present property="value(brk2comm)" name="branchDeliveryForm" >
            <%brk2commdisplay="display:block";%>
            </logic:present>
           <td  nowrap="nowrap">
           <div  style="<%=brk2commdisplay%>" id="brk2commdisplay">
           <table>
           <tr>
           <td id="brk2Nme"><bean:write property="value(brk2)" name="branchDeliveryForm" /></td><td><html:radio property="value(brk2paid)"  styleId="brk2paid1" value="Y" name="branchDeliveryForm"/> </td><td>&nbsp;Yes</td> <td><html:radio property="value(brk2paid)"  styleId="brk2paid2"   value="N" name="branchDeliveryForm"/></td><td> &nbsp;No</td> <td><html:text property="value(brk2comm)" styleId="brk2comm" readonly="true" size="3" name="branchDeliveryForm" /> </td>
           </tr>
           </table>
           </div>
           </td>
           <logic:present property="value(brk3comm)" name="branchDeliveryForm" >
           <%brk3commdisplay="display:block";%> 
           </logic:present>
           <td  nowrap="nowrap">
           <div style="<%=brk3commdisplay%>" id="brk3commdisplay">
           <table>
           <tr>
           <td id="brk3Nme"><bean:write property="value(brk3)" name="branchDeliveryForm" /></td><td><html:radio property="value(brk3paid)"   styleId="brk3paid1" value="Y" name="branchDeliveryForm"/> </td><td>&nbsp;Yes</td> <td><html:radio property="value(brk3paid)"  styleId="brk3paid2" value="N" name="branchDeliveryForm"/></td><td> &nbsp;No</td>  <td><html:text property="value(brk3comm)" styleId="brk3comm" readonly="true" size="3" name="branchDeliveryForm" /> </td>
           </tr>
           </table>
           </div>
           </td>
            <logic:present property="value(brk4comm)" name="branchDeliveryForm" >
           <%brk4commdisplay="display:block";%>  
           </logic:present>
           <td  nowrap="nowrap">
           <div style="<%=brk4commdisplay%>" id="brk4commdisplay">
           <table>
           <tr>
           <td id="brk4Nme"><bean:write property="value(brk4)" name="branchDeliveryForm" /></td><td><html:radio property="value(brk4paid)"   styleId="brk4paid1"  value="Y" name="branchDeliveryForm"/> </td><td>&nbsp;Yes</td> <td><html:radio property="value(brk4paid)"   styleId="brk4paid2" value="N" name="branchDeliveryForm"/></td><td> &nbsp;No</td> <td><html:text property="value(brk4comm)" styleId="brk4comm" readonly="true" size="3" name="branchDeliveryForm" /> </td>
           </tr>
           </table>
           </div>
           </td>
            </tr></table> </td>
            </tr>
 <tr><td><span class="pgTtl" >Extra Charge : </span><input type="text" id="echarge" name="echarge" onchange="isNumericDecimal(this.id)"/>
             <%
            pageList= ((ArrayList)pageDtl.get("BENIFIT") == null)?new ArrayList():(ArrayList)pageDtl.get("BENIFIT"); 
            if(pageList!=null && pageList.size() >0){
            for(int j=0;j<pageList.size();j++){
            pageDtlMap=(HashMap)pageList.get(j);
            fld_nme="value("+(String)pageDtlMap.get("fld_nme")+")";
            fld_ttl=(String)pageDtlMap.get("fld_ttl");
            fld_typ=(String)pageDtlMap.get("fld_typ");
            dflt_val=(String)pageDtlMap.get("dflt_val");
            val_cond=(String)pageDtlMap.get("val_cond"); %>
             <span class="pgTtl" ><%=fld_ttl%> : </span><input type="text" id="benefit" name="benefit" onchange="isNumericDecimal(this.id)"/>
            <%}}%> 
 </td>
 </tr>
</table>
          </td> </tr>
            <tr><td>
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
    <html:submit property="<%=fld_nme%>" value="<%=fld_ttl%>" onclick="<%=val_cond%>" styleClass="submit"/>&nbsp;
    <%}else if(fld_typ.equals("B")){%>
    <html:button property="<%=fld_nme%>" value="<%=fld_ttl%>" onclick="<%=val_cond%>" styleClass="submit"/>&nbsp;
    <%}}}
    %></td></tr>
                <!--<html:submit property="submit" value="Save"  styleClass="submit"/>&nbsp;
                 <html:submit property="value(consignment)" value="Save & Consignment" onclick="" styleClass="submit"/>&nbsp;
                <html:button property="fullReturn" onclick="SelectRD('RT');calculation('AP_')" value="Full Return" styleClass="submit"/>&nbsp;
                <html:button property="fullApprove" onclick="SelectRD('AP');calculation('AP_')" value="Full Approve" styleClass="submit"/>&nbsp;-->
  <tr><td>
            <label class="pgTtl">Memo Packets</label>
              Create Excel <img src="../images/ico_file_excel.png" onclick="goTo('<%=info.getReqUrl()%>/marketing/memoReturn.do?method=createXL','','')" />

            <%
             String memoIdconQ="";
                ArrayList prps = (info.getValue("MDL_MEMO_RTRN") == null)?new ArrayList():(ArrayList)info.getValue("MDL_MEMO_RTRN");
               ArrayList itemHdr = new ArrayList();
               ArrayList pktList = new ArrayList();
               HashMap  pktPrpMap = new HashMap();
               itemHdr.add("BYR");
               itemHdr.add("DlVIDN");
               itemHdr.add("Packet Code");
             %>  
            <table class="grid1">
                <tr>
                    <th>Sr</th>
                    <th>Is PAY RC</th>
            <%
            pageList= ((ArrayList)pageDtl.get("RADIOHDR") == null)?new ArrayList():(ArrayList)pageDtl.get("RADIOHDR"); 
            if(pageList!=null && pageList.size() >0){
            for(int j=0;j<pageList.size();j++){
            pageDtlMap=(HashMap)pageList.get(j);
            fld_nme="value("+(String)pageDtlMap.get("fld_nme")+")";
            fld_ttl=(String)pageDtlMap.get("fld_ttl");
            fld_typ=(String)pageDtlMap.get("fld_typ");
            dflt_val=(String)pageDtlMap.get("dflt_val");
            val_cond=(String)pageDtlMap.get("val_cond"); %>
            <th><html:radio property="<%=fld_nme%>" styleId="<%=fld_typ%>" name="branchDeliveryForm" onclick="<%=val_cond%>"  value="<%=dflt_val%>"/><%=fld_ttl%> </th>
            <%}}%>
                   <th>Idn</th>
                   <th>Dlv Idn</th>
                    <th>Packet Code</th>
                     <%for(int j=0; j < prps.size(); j++) {
                     String lprp=(String)prps.get(j);
                     if(prpDspBlocked.contains(lprp)){
                     }else{
                       itemHdr.add(lprp);
                     %>
                        <th><%=lprp%></th>
                    <%}}%>
                    <th>RapRte</th>
                    <th>Dis</th>
                    <th>Prc / Crt </th>
                    <th>ByrDis</th>
                    <th>Quot</th>
                     <th>Byr Amt</th>
              <% itemHdr.add("RapRte");itemHdr.add("Dis");itemHdr.add("RTE");itemHdr.add("BYRDIS");itemHdr.add("QUOT");itemHdr.add("AMT");
                %>
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
                    pktPrpMap = new HashMap();
                    pktPrpMap.put("BYR",util.nvl(pkt.getValue("ByrNme")));
                    pktPrpMap.put("DlVIDN",util.nvl(pkt.getMemoId()));pktPrpMap.put("Packet Code",util.nvl(pkt.getVnm()));
                
                    String fnlsal=util.nvl(pkt.getMemoQuot(),pkt.getRte());
                     memoIdconQ+=","+util.nvl(pkt.getMemoId());
                   
                %>
            <td><%=pkt.getValue("flg")%></td>
            <%
            String lStt = pkt.getStt();
            pageList= ((ArrayList)pageDtl.get("RADIOBODY") == null)?new ArrayList():(ArrayList)pageDtl.get("RADIOBODY");
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
            
             <html:radio property="<%=fld_nme%>" styleId="<%=fld_typ%>" name="branchDeliveryForm" onclick="<%=val_cond%>"  value="<%=dflt_val%>"/>
           <%if(dflt_val.equals("RT")){%>
            <html:text property="<%=rmkTxt%>" size="10"  />
            <%}%>
            </td>
            <%}}%>
                <td><%=pkt.getIdn()%></td>
                <td><%=pkt.getMemoId()%></td>
               <td><%=pkt.getVnm()%></td>
                <%for(int j=0; j < prps.size(); j++) {
                String lprp = (String)prps.get(j);
                if(prpDspBlocked.contains(lprp)){
                }else{
                pktPrpMap.put(lprp,util.nvl(pkt.getValue((String)prps.get(j))));
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
                <td><%=pkt.getValue("byramt")%></td>
                </tr>
              <%  
                 pktPrpMap.put("RapRte",util.nvl((String)pkt.getRapRte()));pktPrpMap.put("Dis",util.nvl((String)pkt.getDis()));pktPrpMap.put("RTE",util.nvl((String)pkt.getRte()));pktPrpMap.put("BYRDIS",util.nvl((String)pkt.getByrDis()));
                 pktPrpMap.put("QUOT",util.nvl(pkt.getMemoQuot()));
                 pktPrpMap.put("AMT",util.nvl(pkt.getValue("byramt")));
                 pktList.add(pktPrpMap); 
                }
                  session.setAttribute("pktList", pktList);
                 session.setAttribute("itemHdr", itemHdr);
  
                  memoIdconQ=memoIdconQ.replaceFirst("\\,", "");
            %>
             <input type="hidden" id="rdCount" value="<%=count%>" />
               <html:hidden property="value(memoIdn)"  name="branchDeliveryForm"  styleId="memoIdnlst" value="<%=memoIdconQ%>"  />
         
            
            </table>
            </td></tr>
       </table>
<%}else{%>
Sorry no result found...
<%}%>
<%}%></html:form>
</td></tr>
<tr>
    <td><jsp:include page="/footer.jsp" /> </td>
    </tr>
</table>  
  
  </body>
    <%@include file="../calendar.jsp"%>
</html>