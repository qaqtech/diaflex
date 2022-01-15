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
    <title>Location Delivery</title>
    <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/bse.css?v=<%=info.getJsVersion()%>"/>
    <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/style.css?v=<%=info.getJsVersion()%>"/>
    <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/memoScript.js?v=<%=info.getJsVersion()%> " > </script>
    <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/bse.js?v=<%=info.getJsVersion()%> " > </script>
    <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/assortScript.js?v=<%=info.getJsVersion()%> " > </script>
    <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/Validation.js " > </script>
    <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/priceChange.js?v=<%=info.getJsVersion()%> " > </script>
  </head>
<%
HashMap dbinfo = info.getDmbsInfoLst();
String cnt = (String)dbinfo.get("CNT");
        String logId=String.valueOf(info.getLogId());
        String onfocus="cook('"+logId+"')";
        %>
 <body onfocus="<%=onfocus%>" >
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
  <img src="../images/line.jpg" border="0" /></td><td valign="middle"> <span class="pgHed">Location Delivery</span> </td>
</tr></table>
<% if(request.getAttribute("msgrecive")!=null){%>
<tr><td valign="top" class="tdLayout">
<span class="redLabel"><%=request.getAttribute("msgrecive")%></span>
</td></tr>
<%}%>
<% if(request.getAttribute("msgdlv")!=null){%>
<tr><td valign="top" class="tdLayout">
<span class="redLabel"><%=request.getAttribute("msgdlv")%></span>
</td></tr>
<%}%>
 <tr><td valign="top" class="tdLayout">
  <table>
  <tr><td valign="top">
  <html:form action="/marketing/locDelivery.do?method=loadPkt" method="POST" onsubmit="return validate_locationDlv()">
  <html:hidden property="value(location)" styleId="location"  name="locationDlvForm" />
  <table class="grid1" >
  <tr><th colspan="2">Search </th></tr>
 <tr>
<td>Buyer Name :</td>
<td>
 <html:select property="byrIdn" name="locationDlvForm" styleId="byrId" onchange="getlocationIdn()" >
    <html:option value="0">---select---</html:option>
    <html:optionsCollection property="byrLst" name="locationDlvForm"  value="byrIdn" label="byrNme" />        
  </html:select>         
</td></tr> 

<tr><td>Packets. </td><td>
<html:textarea property="value(vnmLst)" name="locationDlvForm" styleId="vnmLst" /> </td> </tr>
<tr><td colspan="2"><html:submit property="submit" value="View" styleClass="submit"/></td></tr>           
</table></html:form>
  </td><td valign="top"><div id="memoIdn"></div></td></tr>
  </table>
</td></tr>
<%String view = (String)request.getAttribute("view");

ArrayList prpDspBlocked = info.getPageblockList();
if(view!=null){
String aadatcommdisplay="display:none",brk1commdisplay="display:none",brk2commdisplay="display:none",brk3commdisplay="display:none",brk4commdisplay="display:none";
%>
<tr><td valign="top" class="tdLayout">
<html:form action="/marketing/locDelivery.do?method=RtnPkt" method="post">
<html:hidden property="value(location)" styleId="location"  name="locationDlvForm" />
<%  
ArrayList pkts = (ArrayList)session.getAttribute("PktList");
if(pkts!=null && pkts.size()>0){
%>
<table>
   <tr><td>
    <html:hidden property="value(exhRte)"  styleId="exhRte" name="locationDlvForm" />
   <table>
            
                <tr>
                <td><span class="pgTtl" >Exiting Selection</span></td>
                <td> <b>Terms : </b> </span></td><td>
                <bean:write property="value(trmsLb)" name="locationDlvForm" />
                <html:hidden property="relIdn" name="locationDlvForm" />
               
                </td>
                 <td> <b>Buyer :</b> </span></td><td>
                <bean:write property="byr" name="locationDlvForm" />
                 <html:hidden property="nmeIdn" name="locationDlvForm" />
                </td>
          <td><b>Email :</b></td> <td> <bean:write property="value(email)" name="locationDlvForm"/> </td>
          <td><b>Mobile No :</b></td> <td> <bean:write property="value(mobile)" name="locationDlvForm"/> </td>
          <td><b>Office No :</b></td> <td> <bean:write property="value(office)" name="locationDlvForm"/> </td>
          
                
                </tr></table>
   
   </td></tr>
  
   <tr><td>
            <table>
                <tr>
                
            <td><span class="pgTtl" >Buyer List</span></td>
                <td>
                <html:select property="value(invByrIdn)" name="locationDlvForm"  styleId="byrId1" onchange="GetADDR()" >
               <html:option value="0" >---select---</html:option>
                <html:optionsCollection property="invByrLst" name="locationDlvForm"  value="byrIdn" label="byrNme" />
                
                </html:select>
                </td>  <td><span class="pgTtl" >Address</span></td>
                <td><html:select property="value(invAddr)" styleId="addrId" name="locationDlvForm"  >
                
                 <html:optionsCollection property="invAddLst" name="locationDlvForm"   value="idn" label="addr" />
                </html:select>
                
               </td>
              
                <td><span class="pgTtl" >Terms </span></td><td>
             
             <html:select property="value(invByrTrm)" name="locationDlvForm"  styleId="rlnId1"  onchange="invTermsDtls();" >
         
             <html:optionsCollection property="invTrmsLst" name="locationDlvForm"
            label="trmDtl" value="relId" />
            
            </html:select>
            </td>
            </tr></table></td></tr>
           
            <tr><td><table>
            <tr>
            <td nowrap="nowrap"><span class="pgTtl" >Company Name</span></td>
          <td>
           <html:select property="value(grpIdn)" name="locationDlvForm"  styleId="grpIdn"  >
             <html:optionsCollection property="groupList" name="locationDlvForm"
             value="idn" label="addr" />
            </html:select>
          </td>
                <td nowrap="nowrap"><span class="pgTtl" >Bank Selection</span></td>
               <td>
             
             <html:select property="value(comIdn)" name="locationDlvForm" onchange="SetBankAddr(this)"  styleId="bankIdn"  >
             <html:optionsCollection property="bankList" name="locationDlvForm"
             value="idn" label="addr" />
            
            </html:select>
            </td>
            <td> <html:select property="value(bankIdn)" name="locationDlvForm" style="dispaly:none" styleId="bankAddr">
             <html:optionsCollection property="bnkAddrList" name="locationDlvForm"
             value="idn" label="addr" />
            </html:select>
            
            </td>
            </tr>
            
            </table></td>
            
            </tr>
       
                <tr>
                 <tr>
            <td nowrap="nowrap"><table><tr><td><span class="pgTtl" >Courier :</span> </td>
             <td> <html:select property="value(courier)" name="locationDlvForm" style="dispaly:none" styleId="courier">
            <html:optionsCollection property="courierList" name="locationDlvForm"
             value="idn" label="addr" />
            </html:select>
            </td>
            </tr></table> </td>
            </tr>
            <tr>
            <td><table><tr><td><span class="pgTtl" >Commission :</span> </td>
            <html:hidden property="value(aadatcommval)" styleId="aadatcommval" name="locationDlvForm" value="0"/>
            <html:hidden property="value(brk1commval)" styleId="brk1commval" name="locationDlvForm" value="0"/>
            <html:hidden property="value(brk2commval)" styleId="brk2commval"  name="locationDlvForm" value="0"/>
            <html:hidden property="value(brk3commval)" styleId="brk3commval"  name="locationDlvForm" value="0"/>
            <html:hidden property="value(brk4commval)" styleId="brk4commval"  name="locationDlvForm" value="0"/>
                        <logic:present property="value(aadatcomm)" name="locationDlvForm" >
            <%aadatcommdisplay="display:block";%>
            </logic:present>
            <td nowrap="nowrap">
            <div style="<%=aadatcommdisplay%>" id="aadatcommdisplay">
            <table>
            <tr>
            <td><span class="pgTtl" >Aadat :</span> </td>
            <td>
            <span id="aaDatNme"><bean:write property="value(aaDat)" name="locationDlvForm" /></span> :
            <span id="aaDatComm"><bean:write property="value(aadatcomm)" name="locationDlvForm" /></span> </td>
            <td><html:radio property="value(aadatpaid)" onchange="setBrokerComm('aadatcomm','Y')" styleId="aadatpaid1"  value="Y"  name="locationDlvForm"/> </td>
            <td>&nbsp;Yes</td> 
            <td><html:radio property="value(aadatpaid)"  onchange="setBrokerComm('aadatcomm','N')" styleId="aadatpaid2" value="N" name="locationDlvForm"/></td>
            <td>&nbsp;No</td>
            <td><html:text property="value(aadatcomm)" styleId="aadatcomm" readonly="true" size="3" name="locationDlvForm" /> </td>
            </tr>
            </table>
            </div>
            </td>
            <logic:present property="value(brk1comm)" name="locationDlvForm" >
            <%brk1commdisplay="display:block";%>
           </logic:present>
           <td nowrap="nowrap">
           <div  style="<%=brk1commdisplay%>" id="brk1commdisplay">
           <table>
           <tr>
           <td><span class="pgTtl" >Broker :</span> </td>
           <td id="brk1Nme"><bean:write property="value(brk1)" name="locationDlvForm" /></td><td><html:radio property="value(brk1paid)" styleId="brk1paid1" value="Y" name="locationDlvForm"/></td><td>&nbsp;Yes</td>  <td><html:radio property="value(brk2paid)"  styleId="brk1paid2"  value="N" name="locationDlvForm"/></td><td>&nbsp;No</td> <td><html:text property="value(brk1comm)"  styleId="brk1comm"  readonly="true" size="3" name="locationDlvForm" /> </td>
           </tr>
           </table>
           </div>
           </td>
            <logic:present property="value(brk2comm)" name="locationDlvForm" >
            <%brk2commdisplay="display:block";%>
            </logic:present>
           <td  nowrap="nowrap">
           <div  style="<%=brk2commdisplay%>" id="brk2commdisplay">
           <table>
           <tr>
           <td id="brk2Nme"><bean:write property="value(brk2)" name="locationDlvForm" /></td><td><html:radio property="value(brk2paid)"  styleId="brk2paid1" value="Y" name="locationDlvForm"/> </td><td>&nbsp;Yes</td> <td><html:radio property="value(brk2paid)"  styleId="brk2paid2"   value="N" name="locationDlvForm"/></td><td> &nbsp;No</td> <td><html:text property="value(brk2comm)" styleId="brk2comm" readonly="true" size="3" name="locationDlvForm" /> </td>
           </tr>
           </table>
           </div>
           </td>
           <logic:present property="value(brk3comm)" name="locationDlvForm" >
           <%brk3commdisplay="display:block";%> 
           </logic:present>
           <td  nowrap="nowrap">
           <div style="<%=brk3commdisplay%>" id="brk3commdisplay">
           <table>
           <tr>
           <td id="brk3Nme"><bean:write property="value(brk3)" name="locationDlvForm" /></td><td><html:radio property="value(brk3paid)"   styleId="brk3paid1" value="Y" name="locationDlvForm"/> </td><td>&nbsp;Yes</td> <td><html:radio property="value(brk3paid)"  styleId="brk3paid2" value="N" name="locationDlvForm"/></td><td> &nbsp;No</td>  <td><html:text property="value(brk3comm)" styleId="brk3comm" readonly="true" size="3" name="locationDlvForm" /> </td>
           </tr>
           </table>
           </div>
           </td>
            <logic:present property="value(brk4comm)" name="locationDlvForm" >
           <%brk4commdisplay="display:block";%>  
           </logic:present>
           <td  nowrap="nowrap">
           <div style="<%=brk4commdisplay%>" id="brk4commdisplay">
           <table>
           <tr>
           <td id="brk4Nme"><bean:write property="value(brk4)" name="locationDlvForm" /></td><td><html:radio property="value(brk4paid)"   styleId="brk4paid1"  value="Y" name="locationDlvForm"/> </td><td>&nbsp;Yes</td> <td><html:radio property="value(brk4paid)"   styleId="brk4paid2" value="N" name="locationDlvForm"/></td><td> &nbsp;No</td> <td><html:text property="value(brk4comm)" styleId="brk4comm" readonly="true" size="3" name="locationDlvForm" /> </td>
           </tr>
           </table>
           </div>
           </td>
            </tr></table> </td>
            </tr>
 <tr><td><span class="pgTtl" >Extra Charge : </span><input type="text" id="echarge" name="echarge" onchange="isNumericDecimal(this.id)"/></td></tr>
</table>
<table><tr><td>
<table>
            <tr><td><span class="pgTtl" >Delivery Selection</span></td><td>Pcs:</td><td><b> <label id="ttlqtyDLV"></label></b></td>
            <td>Cts:</td><td><b><label id="ttlctsDLV"></label></b></td>
            <td>Discount:</td><td><b><label id="ttldisDLV"></label></b></td>
           <td>Value:</td><td><b><label id="ttlvluDLV"></label></b></td>
            </tr>
</table></td><td>
<table>
            <tr><td><span class="pgTtl" >Recive Selection</span></td><td>Pcs:</td><td><b> <label id="ttlqtyRECIVE"></label></b></td>
            <td>Cts:</td><td><b><label id="ttlctsRECIVE"></label></b></td>
            <td>Discount:</td><td><b><label id="ttldisRECIVE"></label></b></td>
           <td>Value:</td><td><b><label id="ttlvluRECIVE"></label></b></td>
            </tr>
</table></td></tr></table>
<table>
<tr><td>
<label class="pgTtl">Packets</label>
<% ArrayList prps = (info.getValue("MDL_MEMO_RTRN") == null)?new ArrayList():(ArrayList)info.getValue("MDL_MEMO_RTRN"); %>  </td></tr>
<tr><td>
<table class="grid1">
                <tr>
                    <th>Sr</th>
                    <th>Delivery Id</th>
                      <th>Packet Code</th>
                     <%for(int j=0; j < prps.size(); j++) {
                     String lprp=(String)prps.get(j);
                     if(prpDspBlocked.contains(lprp)){
                     }else{%>
                        <th><%=lprp%></th>
                    <%}}%>
                    <th>RapRte</th>
                    <th>Dis</th>
                    <th>Prc / Crt </th>
                    <th>ByrDis</th>
                    <th>Quot</th>
                    <th><html:radio property="value(slRd)"  styleId="dpRd" name="locationDlvForm" onclick="ALLRedio('dpRd' ,'DP_');SetCalculationDlvLocAll('DP')"  value="RT"/>&nbsp;None</th>
                     <th><html:radio property="value(slRd)" styleId="reciveRd" name="locationDlvForm" onclick="ALLRedio('reciveRd' ,'RECIVE_');SetCalculationDlvLocAll('RECIVE')"  value="RT"/>&nbsp;Recive</th>
                   <th><html:radio property="value(slRd)" styleId="dlvRd" name="locationDlvForm" onclick="ALLRedio('dlvRd' ,'DLV_');SetCalculationDlvLocAll('DLV')"  value="RT"/>&nbsp;Delivery</th>
                   <th> <html:radio property="value(slRd)" styleId="invRd" name="locationDlvForm" onclick="ALLRedio('invRd' ,'INV_');SetCalculationDlvLocAll('INV')"  value="RT"/>&nbsp;Performa Inv </th>
                </tr>
            <%
             int count =0 ;
             
              
                for(int i=0; i < pkts.size(); i++) {%>
                <tr>
                <td><%=(i+1)%></td>
                <%    count=i+1;
                    PktDtl pkt = (PktDtl)pkts.get(i);
                    long pktIdn = pkt.getPktIdn();
                    String cbPrp = "value(upd_" + pktIdn + ")";
                    String sttPrp = "value(stt_" + pktIdn + ")" ;
                    String invPrp = "value(INV_" + pktIdn + ")" ;
                    
                    String rdRTId = "RECIVE_"+count;
                    String rdDPId = "DP_"+count;
                    String rdDLVId = "DLV_"+count;
                    String rdINVId = "INV_"+count;
                     String onClickRt = "SetCalculationDlvLoc('"+pktIdn+"','RECIVE','"+pkt.getSaleId()+"')";
                       String onClickDp = "SetCalculationDlvLoc('"+pktIdn+"','DP','"+pkt.getSaleId()+"')";
                        String onClickDlv = "SetCalculationDlvLoc('"+pktIdn+"','DLV','"+pkt.getSaleId()+"')";
                        String onClickper = "SetCalculationDlvLoc('"+pktIdn+"','INV','"+pkt.getSaleId()+"')";
             %>
                <td><%=pkt.getSaleId()%></td>
                <input type="hidden" id="STKIDN_<%=count%>" value="<%=pktIdn%>" />
                 <td><%=pkt.getVnm()%></td>
                <%for(int j=0; j < prps.size(); j++) {
                String lprp=(String)prps.get(j);
                if(prpDspBlocked.contains(lprp)){
                  }else{%>
                    <td><%=util.nvl(pkt.getValue((String)prps.get(j)))%></td>
                <%}}%>
                <td><%=pkt.getRapRte()%></td>
                <td><%=pkt.getDis()%></td>
                <td><%=pkt.getRte()%></td>
                <td><%=pkt.getByrDis()%></td>
                <td><%=pkt.getMemoQuot()%></td>
                <td><html:radio property="<%=sttPrp%>" styleId="<%=rdDPId%>" name="locationDlvForm" onclick="<%=onClickDp%>" value="DP"/></td>
                <td><html:radio property="<%=sttPrp%>" styleId="<%=rdRTId%>" name="locationDlvForm" onclick="<%=onClickRt%>" value="REC"/></td>
                <td><html:radio property="<%=sttPrp%>" styleId="<%=rdDLVId%>" name="locationDlvForm" onclick="<%=onClickDlv%>" value="DLV"/></td>
               <td><html:radio property="<%=sttPrp%>" styleId="<%=rdINVId%>" name="locationDlvForm"  onclick="<%=onClickper%>" value="<%=String.valueOf(pktIdn)%>"/></td>
                </tr>
              <%  
                }
            %>
              <input type="hidden" id="rdCount" value="<%=count%>" />
            </table></td></tr>
             <tr><td>
           <html:submit property="submit" value="Save" styleClass="submit"/>&nbsp;
           <html:button property="value(perInv)" onclick="GenPerformInvLoc()" value="Perform Invice" styleClass="submit"/>&nbsp;
          <!--  <html:button property="value(perInv)" onclick="GenPerformInv('DLY')" value="Perform Invice" styleClass="submit"/>
             --></td></tr>
             </table>
           <%}else{%>
            Sorry no result found.
            <%}%>
            </html:form>
</td>
</tr>
<%}%>
<tr>
    <td><jsp:include page="/footer.jsp" /> </td>
    </tr>
</table>
  </body></html>