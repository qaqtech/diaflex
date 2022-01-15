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
    <title>Consignment Delivery</title>
    <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/bse.css?v=<%=info.getJsVersion()%>"/>
    <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/style.css?v=<%=info.getJsVersion()%>"/>
    <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/memoScript.js?v=<%=info.getJsVersion()%> " > </script>
    <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/bse.js?v=<%=info.getJsVersion()%> " > </script>
    <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/assortScript.js?v=<%=info.getJsVersion()%> " > </script>
    <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/Validation.js " > </script>
    <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/priceChange.js?v=<%=info.getJsVersion()%> " > </script>
    <script type="text/javascript" src="<%=info.getReqUrl()%>/PopScripts/popup.js?v=<%=info.getJsVersion()%> " > </script>
    <script src="<%=info.getReqUrl()%>/PopScripts/jquery-1.2.6.min.js" type="text/javascript"></script>
     <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/general.css?v=<%=info.getJsVersion()%>"/>
  </head>
<%
HashMap dbinfo = info.getDmbsInfoLst();
String cnt = (String)dbinfo.get("CNT");
  String repPath = (String)dbinfo.get("REP_PATH");
int accessidn=Integer.parseInt(util.nvl((String)request.getAttribute("accessidn"),"0"));
        String logId=String.valueOf(info.getLogId());
        String onfocus="cook('"+logId+"')";
        %>
 <body onfocus="<%=onfocus%>" >
 <div id="backgroundPopup"></div>
<div id="popupContactSave" align="center" >
<img src="../images/loading_flame.gif" height="150px" width="150px" border="0" />
</div>
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
  <img src="../images/line.jpg" border="0" /></td><td valign="middle"> <span class="pgHed">Consignment Delivery</span> </td>
</tr></table>
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
        <% if(request.getAttribute("CSMSG")!=null){
         String url =repPath+"/reports/rwservlet?"+cnt+"&report="+info.getRportUrl()+"\\reports\\sale_dlv_rep.jsp&p_access="+accessidn+"&p_dlv_idn="+request.getAttribute("dlvId")+"&destype=CACHE&desformat=pdf";
        %>
        <tr><td valign="top" class="tdLayout"><span class="redLabel"> <%=request.getAttribute("SLMSG")%></span></td></tr>
      
       <tr><td class="tdLayout"><div onclick="displayDiv('refDiv')"> <a href="<%=url%>"  target="_blank"><span class="txtLink" >Click Here for Report</span></a> </div></td></tr>
       <% }
        %>
  </table>
  </td>
  </tr>
 <tr><td valign="top" class="tdLayout">
  <table>
  <tr><td>
  <html:form action="/marketing/consignment.do?method=loadPkt" method="POST" onsubmit="return validate_sale()">
  <table class="grid1" >
  <tr><th colspan="2">Consignment Search </th></tr>
 <tr>
<td>Buyer Name :</td>
<td>
 <html:select property="byrIdn" name="consignmentForm" onchange="getFinalConsignmentByr(this,'CS')" styleId="byrId" >
    <html:option value="0">---select---</html:option>
    <html:optionsCollection property="byrLst" name="consignmentForm"  value="byrIdn" label="byrNme" />
                
  </html:select>
               
</td></tr> 
<tr>
<td>Billing  Party :</td><td>
<html:select property="prtyIdn" name="consignmentForm"  styleId="prtyId"  >
     
</html:select>
</td>
</tr>
<tr>
<td>Terms :</td><td>
<html:select property="relIdn" name="consignmentForm"  onchange="getConsignmentIdn('CS')" styleId="rlnId"  >
     
</html:select>

</td>
</tr>
   <tr><td colspan="2"><div id="memoIdn"></div> </td></tr>

<tr><td>Packets. </td><td>
<html:textarea property="value(vnmLst)" name="consignmentForm" styleId="vnmLst" /> </td> </tr>
<tr><td colspan="2"><html:submit property="submit" value="View" styleClass="submit"/></td></tr>           
</table></html:form>
  </td></tr>
  
   <html:form action="/marketing/consignment.do?method=save" method="POST"  onsubmit="return confirmChangesWithPopup()">
   <% if(request.getAttribute("view")!=null){
     ArrayList pkts = (ArrayList)info.getValue("PKTS");
   if(pkts!=null && pkts.size()>0){
  %>
   <tr><td>
    <html:hidden property="value(exhRte)"  styleId="exhRte" name="consignmentForm" />
   <table>
            
                <tr>
                <td><span class="pgTtl" >Exiting Selection</span></td>
                <td> <b>Terms : </b> </span></td><td>
                <bean:write property="value(trmsLb)" name="consignmentForm" />
                <html:hidden property="relIdn" name="consignmentForm" />
               
                </td>
                 <td> <b>Buyer :</b> </span></td><td>
                <bean:write property="byr" name="consignmentForm" />
                 <html:hidden property="nmeIdn" name="consignmentForm" />
                </td>
                
                
                </tr></table>
   
   </td></tr>
  
   <tr><td>
            <table>
            
                <tr>
               
            <td><span class="pgTtl" >Buyer List</span></td>
                <td>
                <html:select property="value(invByrIdn)" name="consignmentForm"  styleId="byrId1" onchange="GetADDR()" >
               <html:option value="0" >---select---</html:option>
                <html:optionsCollection property="invByrLst" name="consignmentForm"  value="byrIdn" label="byrNme" />
                
                </html:select>
                </td> <td><span class="pgTtl" >Address</span></td>
                <td><html:select property="value(invAddr)" styleId="addrId" name="consignmentForm"  >
                <html:optionsCollection property="invAddLst" name="consignmentForm"   value="idn" label="addr" />
                </html:select> </td>
                <td><span class="pgTtl" >Terms </span></td><td>
             
             <html:select property="value(invByrTrm)" name="consignmentForm"  styleId="rlnId1"  >
         
             <html:optionsCollection property="invTrmsLst" name="consignmentForm"
            label="trmDtl" value="relId" />
            
            </html:select>
            </td>
            </tr></table></td></tr>
           
            <tr><td><table>
            <tr>
                <td><span class="pgTtl" >Bank Selection</span></td>
               <td>
             
             <html:select property="value(comIdn)" name="consignmentForm" onchange="SetBankAddr(this)"  styleId="bankIdn"  >
             <html:option value="0" >---select---</html:option>
             <html:optionsCollection property="bankList" name="consignmentForm"
             value="idn" label="addr" />
            
            </html:select>
            </td>
            <td> <html:select property="value(bankIdn)" name="consignmentForm" style="dispaly:none" styleId="bankAddr">
            
            </html:select>
            
            </td>
            </tr>
            
            </table></td>
            
            </tr>
       
                <tr>
            <td>
            
            <table><tr><td><span class="pgTtl" >Broker Commission :</span> </td>
            <logic:present property="value(aadatcomm)" name="consignmentForm" >
            <td><bean:write property="value(aadatcomm)" name="consignmentForm" /> </td><td><html:radio property="value(aadatpaid)" value="Y"  name="consignmentForm"/> </td><td>&nbsp;Yes</td> <td><html:radio property="value(aadatpaid)" value="N" name="consignmentForm"/></td><td> &nbsp;No</td><td><html:text property="value(aadatcomm)" readonly="true" size="3" name="consignmentForm" /> </td>
            </logic:present>
             <logic:present property="value(brk1comm)" name="consignmentForm" >
            <td><bean:write property="value(brk1)" name="consignmentForm" /></td><td><html:radio property="value(brk1paid)" value="Y" name="consignmentForm"/></td><td>&nbsp;Yes</td>  <td><html:radio property="value(brk1paid)" value="N" name="consignmentForm"/></td><td>&nbsp;No</td> <td><html:text property="value(brk1comm)" readonly="true" size="3" name="consignmentForm" /> </td>
           </logic:present>
            <logic:present property="value(brk2comm)" name="consignmentForm" >
            <td><bean:write property="value(brk2)" name="consignmentForm" /></td><td><html:radio property="value(brk2paid)" value="Y" name="consignmentForm"/> </td><td>&nbsp;Yes</td> <td><html:radio property="value(brk2paid)" value="N" name="consignmentForm"/></td><td> &nbsp;No</td> <td><html:text property="value(brk2comm)" readonly="true" size="3" name="consignmentForm" /> </td>
            </logic:present>
             <logic:present property="value(brk3comm)" name="consignmentForm" >
            <td><bean:write property="value(brk3)" name="consignmentForm" /></td><td><html:radio property="value(brk3paid)" value="Y" name="consignmentForm"/> </td><td>&nbsp;Yes</td> <td><html:radio property="value(brk3paid)" value="N" name="consignmentForm"/></td><td> &nbsp;No</td>  <td><html:text property="value(brk3comm)" readonly="true" size="3" name="consignmentForm" /> </td>
           </logic:present>
            <logic:present property="value(brk4comm)" name="consignmentForm" >
            <td><bean:write property="value(brk4)" name="consignmentForm" /></td><td><html:radio property="value(brk4paid)" value="Y" name="consignmentForm"/> </td><td>&nbsp;Yes</td> <td><html:radio property="value(brk4paid)" value="N" name="consignmentForm"/></td><td> &nbsp;No</td> <td><html:text property="value(brk4comm)" readonly="true" size="3" name="consignmentForm" /> </td>
           </logic:present>
           
            </tr></table> </td>
            </tr>
            <tr>
            <td><div id="totalDiv">
            <table>
            <tr><td><span class="pgTtl" >Delivery Selection</span></td><td>Pcs:</td><td><b> <label id="qty"></label></b></td>
            <td>Cts:</td><td><b><label id="cts"></label></b></td>
            <td>Discount:</td><td><b><label id="avgdis"></label></b></td>
            <td>Avg Prc:</td><td><b><label id="avgprc"></label></b></td>
            <td>Value:</td><td><b><label id="vlu"></label></b></td>
            </tr>
            </table>
            </div></td>
            </tr>
           
 <tr><td><span class="pgTtl" >Extra Charge : </span><input type="text" id="echarge" name="echarge" onchange="isNumericDecimal(this.id)"/></td></tr>
  <tr><td>
            <label class="pgTtl">Consignment Packets</label>
            <%
                ArrayList prps = (info.getValue("MDL_MEMO_RTRN") == null)?new ArrayList():(ArrayList)info.getValue("MDL_MEMO_RTRN");
             %>  </td></tr>
             <tr><td>
            <table class="grid1">
                <tr>
                    <th>Sr</th>
                    <th><html:radio property="value(csRd)"  styleId="csRd" name="consignmentForm" onclick="ALLRedio('csRd' ,'CS_');calculation('DLV_')"  value="CS"/>&nbsp;Consignment</th>
                    <th><html:radio property="value(csRd)" styleId="dlvRd" name="consignmentForm" onclick="ALLRedio('dlvRd' ,'DLV_');calculation('DLV_')"  value="DLV"/>&nbsp;Delivery</th>
                    <th><html:radio property="value(csRd)" styleId="rtRd" name="consignmentForm" onclick="ALLRedio('rtRd' ,'RT_');calculation('DLV_')"  value="RT"/>&nbsp;Return</th>
                    <th> <html:radio property="value(csRd)" styleId="invRd" name="consignmentForm" onclick="ALLRedio('invRd' ,'INV_');calculation('DLV_')"  value="INV"/>&nbsp;Performa Inv </th>
                    <th>Consignment Id</th>
                      <th>Packet Code</th>
                     <%for(int j=0; j < prps.size(); j++) {%>
                        <th><%=(String)prps.get(j)%></th>
                    <%}%>
                    <th>RapRte</th>
                    <th>Dis</th>
                    <th>Prc / Crt </th>
                    <th>ByrDis</th>
                    <th>Quot</th>                   
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
                    String rdISId = "DLV_"+count;
                    String rdCSId = "CS_"+count;
                    String rdRTId = "RT_"+count;
                    String rdInvId = "INV_"+count;
                    String onClickAp = "SetCalculationDlv('"+pktIdn+"','DLV','"+pkt.getSaleId()+"')";
                    String onClickCs = "SetCalculationDlv('"+pktIdn+"','CS','"+pkt.getSaleId()+"')";
                    String onClickRt = "SetCalculationDlv('"+pktIdn+"','RT','"+pkt.getSaleId()+"')";
             %>
                <td><html:radio property="<%=sttPrp%>" styleId="<%=rdCSId%>" name="consignmentForm" onclick="<%=onClickCs%>"  value="CS"/></td>
                <td><html:radio property="<%=sttPrp%>" styleId="<%=rdISId%>" name="consignmentForm" onclick="<%=onClickAp%>"  value="DLV"/></td>
                <td><html:radio property="<%=sttPrp%>" styleId="<%=rdRTId%>" name="consignmentForm" onclick="<%=onClickRt%>" value="RT"/></td>
                <td><html:radio property="<%=sttPrp%>" styleId="<%=rdInvId%>" name="consignmentForm"  value="<%=String.valueOf(pktIdn)%>"/></td>
                <td><%=pkt.getSaleId()%></td>
                 <td><%=pkt.getVnm()%></td>
                <%for(int j=0; j < prps.size(); j++) {
                      String lprp = (String)prps.get(j);
                %>
                    <td><%=util.nvl(pkt.getValue((String)prps.get(j)))%>
                      <%if(lprp.equals("CRTWT")){%>
                    <input type="hidden" id="<%=count%>_cts" value="<%=util.nvl(pkt.getValue((String)prps.get(j)))%>" /> 
                    <%}%>
                    </td>
                    
                <%}
                %>
                <td><%=pkt.getRapRte()%><input type="hidden" id="<%=count%>_rap" value="<%=pkt.getRapRte()%>" /></td>
                <td><%=pkt.getDis()%></td>
                <td><%=pkt.getRte()%>
                <input type="hidden" id="<%=count%>_quot" value="<%=pkt.getRte()%>" /> 
                <input type="hidden" id="<%=count%>_fnl" value="<%=pkt.getMemoQuot()%>" />
                </td>
                <td><%=pkt.getByrDis()%></td>
                <td><%=pkt.getMemoQuot()%></td>              
                </tr>
              <%  
                }
            %>
              <input type="hidden" id="rdCount" value="<%=count%>" />
            </table></td></tr>
             <tr><td>
            <p>
                <html:submit property="submit" value="Save" styleClass="submit"/>&nbsp;
                <html:button property="value(perInv)" onclick="GenPerformInv('CS')" value="Perform Invice" styleClass="submit"/>&nbsp;
                         
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
  
  <%if(request.getAttribute("CSMSG")!=null){%>
 <script type="text/javascript">
 <!--
 windowOpen('<%=info.getWebUrl()%>/reports/rwservlet?<%=cnt%>&report=<%=info.getRportUrl()%>\\reports\\sale_dlv_rep.jsp&p_access=<%=accessidn%>&p_dlv_idn=<%=request.getAttribute("dlvId")%>&destype=CACHE&desformat=pdf','_blank')
 -->
 </script>
 <%}%>
  </body></html>