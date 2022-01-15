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
        <title>consignmentByBilingParty</title>
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
ArrayList prpDspBlocked = info.getPageblockList();
 ArrayList  pageList = new ArrayList();
 HashMap pageDtlMap=new HashMap();
HashMap allPageDtl= (info.getPageDetails() == null)?new HashMap():(HashMap)info.getPageDetails();
HashMap pageDtl=(HashMap)allPageDtl.get("MEMO_SALE");
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
  <img src="../images/line.jpg" border="0" /></td><td valign="middle"> <span class="pgHed">Consignment By Biling Party</span> </td>
  </tr></table></td></tr>
   <tr><td valign="top" class="hedPg">
   <table cellpadding="2" cellspacing="2" >
   <% if(request.getAttribute("SLMSG")!=null){%>
   <tr><td>
   <span class="redLabel"> <%=request.getAttribute("SLMSG")%></span></td></tr>
   <% }%>
   <% if(request.getAttribute("RTMSG")!=null){%>
   <tr><td height="15"><span class="redLabel"> <%=request.getAttribute("RTMSG")%></span></td></tr>
   <%}%>
   </table></td></tr>
  <tr><td valign="top" class="tdLayout">
  <table><tr><td valign="top">
<html:form action="/marketing/consignByBilling.do?method=loadPkt" method="POST" >
  <table class="grid1" >
  <tr><th colspan="2">Memo Search</th></tr>
<tr>
                <td>Buyer Name :</td>
                <td>
   <html:select property="invByrIdn" name="consignmentByBilingPartyForm" onchange="getSaleByrFromBill(this)" styleId="byrId" >
    <html:option value="0">---select---</html:option>
    <html:optionsCollection property="invByrLst" name="consignmentByBilingPartyForm"  value="byrIdn" label="byrNme" />
    </html:select>
  </td>
                </tr>
                <tr><td colspan="2" ><div id="byr"></div> </td></tr>
  <tr><td>Packets. </td><td>
<html:textarea property="value(vnmLst)" name="consignmentByBilingPartyForm" styleId="vnmLst" /> </td> </tr>
<tr><td colspan="2"><html:submit property="submit" value="View" styleClass="submit"/></td></tr>
  </table>
  </html:form>
  </td>
  <!--<td valign="top" class="tdLayout" align="left">
     <%if(request.getAttribute("view")!=null){%>
    <table class="grid1">
    <tr> <td></td> <td><b> Total</b></td><td><b>Selected</b></td> </tr>
    <tr> <td><b>Qty</b></td> <td><bean:write property="qty" name="consignmentByBilingPartyForm" /></td><td><span id="qty">0</span></td> </tr>
    <tr> <td><b>Cts</b> </td> <td><bean:write property="cts" name="consignmentByBilingPartyForm" /></td><td><span id="cts">0</span></td>  </tr>
    <tr> <td><b>Avg Prc</b></td> <td><bean:write property="avgPrc" name="consignmentByBilingPartyForm" /></td><td><span id="avgprc">0</span></td>  </tr>
    <tr> <td><b>Avg Dis</b> </td> <td><bean:write property="avgDis" name="consignmentByBilingPartyForm" /></td><td><span id="avgdis">0</span></td>  </tr>
    <tr> <td><b>Value </b></td> <td><bean:write property="vlu" name="consignmentByBilingPartyForm" /></td><td><span id="vlu">0</span></td>  </tr>
    </table>
    <%}%>
    </td>-->
  </tr></table>
  </td></tr>
  <%
  ArrayList prps = (info.getValue("MDL_MEMO_RTRN") == null)?new ArrayList():(ArrayList)info.getValue("MDL_MEMO_RTRN");
  HashMap pktMemoMap =(HashMap)session.getAttribute("pktMemoMap");
  ArrayList byrIdnLst =(ArrayList)session.getAttribute("byrIdnLst");
  HashMap byrDtl =(HashMap)session.getAttribute("byrDtl");
  String view=util.nvl((String)request.getAttribute("view"));
  if(!view.equals("")){
  String aadatcommdisplay="display:none",brk1commdisplay="display:none",brk2commdisplay="display:none",brk3commdisplay="display:none",brk4commdisplay="display:none";
  
  if(byrIdnLst!=null && byrIdnLst.size()>0){
  int byrIdnLstsz=byrIdnLst.size();
  %>
  <html:form action="/marketing/consignByBilling.do?method=save" method="POST" >
        <html:hidden property="value(invByrIdn)" name="consignmentByBilingPartyForm" />       
                <tr><td valign="top" class="tdLayout"><table>
            <tr>
            <td nowrap="nowrap"><span class="pgTtl" >Company Name</span></td>
          <td>
           <html:select property="value(grpIdn)" name="consignmentByBilingPartyForm"  styleId="grpIdn"  >
             <html:optionsCollection property="groupList" name="consignmentByBilingPartyForm"
             value="idn" label="addr" />
            </html:select>
          </td>
            <td nowrap="nowrap"> <span class="pgTtl" >Terms </span></td><td>
             
             <html:select property="value(invByrTrm)" name="consignmentByBilingPartyForm"  styleId="rlnId1"  onchange="invTermsDtls();" >
         
             <html:optionsCollection property="invTrmsLst" name="consignmentByBilingPartyForm"
            label="trmDtl" value="relId" />
            
            </html:select>
            </td>
                <td nowrap="nowrap"><span class="pgTtl" >Bank Selection</span></td>
               <td>
             
             <html:select property="value(bankIdn)" name="consignmentByBilingPartyForm" onchange="SetBankAddr(this)"  styleId="bankIdn"  >
             <html:optionsCollection property="bankList" name="consignmentByBilingPartyForm"
             value="idn" label="addr" />
            
            </html:select>
            </td>
            <td> <html:select property="value(bankAddr)" name="consignmentByBilingPartyForm" style="dispaly:none" styleId="bankAddr">
             <html:optionsCollection property="bnkAddrList" name="consignmentByBilingPartyForm"
             value="idn" label="addr" />
            </html:select>
            
            </td>
            <td valign="top" nowrap="nowrap"><table><tr><td><span class="pgTtl" >Courier :</span> </td>
             <td> <html:select property="value(courier)" name="consignmentByBilingPartyForm" style="dispaly:none" styleId="courier">
            <html:optionsCollection property="courierList" name="consignmentByBilingPartyForm"
             value="idn" label="addr" />
            </html:select>
            </td>
            </tr></table> </td>
            </tr>
            </table></td>
        </tr>
          <tr><td valign="top" class="tdLayout">
           <%
            
            boolean  isdisabled= false;
            
            %>
          <table><tr><td><span class="pgTtl" >Commission :</span> </td>
            <html:hidden property="value(aadatcommval)" styleId="aadatcommval" name="consignmentByBilingPartyForm" value="0"/>
            <html:hidden property="value(brk1commval)" styleId="brk1commval" name="consignmentByBilingPartyForm" value="0"/>
            <html:hidden property="value(brk2commval)" styleId="brk2commval"  name="consignmentByBilingPartyForm" value="0"/>
            <html:hidden property="value(brk3commval)" styleId="brk3commval"  name="consignmentByBilingPartyForm" value="0"/>
            <html:hidden property="value(brk4commval)" styleId="brk4commval"  name="consignmentByBilingPartyForm" value="0"/>
                        <logic:present property="value(aadatcomm)" name="consignmentByBilingPartyForm" >
            <%aadatcommdisplay="display:block";%>
            </logic:present>
            <td nowrap="nowrap">
            <div style="<%=aadatcommdisplay%>" id="aadatcommdisplay">
            <table>
            <tr>
            <td><span class="pgTtl" >Aadat :</span> </td>
            <td>
            <span id="aaDatNme"><bean:write property="value(aaDat)" name="consignmentByBilingPartyForm" /></span> :
            <span id="aaDatComm"><bean:write property="value(aadatcomm)" name="consignmentByBilingPartyForm" /></span> </td>
            <td><html:radio property="value(aadatpaid)" onchange="setBrokerComm('aadatcomm','Y')" styleId="aadatpaid1"  value="Y"  name="consignmentByBilingPartyForm" disabled="<%=isdisabled%>"/> </td>
            <td>&nbsp;Yes</td> 
            <td><html:radio property="value(aadatpaid)"  onchange="setBrokerComm('aadatcomm','N')" styleId="aadatpaid2" value="N" name="consignmentByBilingPartyForm" disabled="<%=isdisabled%>"/></td>
            <td>&nbsp;No</td>
            <td><html:text property="value(aadatcomm)" styleId="aadatcomm" readonly="true" size="3" name="consignmentByBilingPartyForm" /> </td>
            </tr>
            </table>
            </div>
            </td>
            <logic:present property="value(brk1comm)" name="consignmentByBilingPartyForm" >
            <%brk1commdisplay="display:block";%>
           </logic:present>
           <td nowrap="nowrap">
           <div  style="<%=brk1commdisplay%>" id="brk1commdisplay">
           <table>
           <tr>
           <td><span class="pgTtl" >Broker :</span> </td>
           <td id="brk1Nme"><bean:write property="value(brk1)" name="consignmentByBilingPartyForm" /></td><td><html:radio property="value(brk1paid)" styleId="brk1paid1" value="Y" name="consignmentByBilingPartyForm" disabled="<%=isdisabled%>"/></td><td>&nbsp;Yes</td>  <td><html:radio property="value(brk2paid)"  styleId="brk1paid2"  value="N" name="consignmentByBilingPartyForm" disabled="<%=isdisabled%>"/></td><td>&nbsp;No</td> <td><html:text property="value(brk1comm)"  styleId="brk1comm"  readonly="true" size="3" name="consignmentByBilingPartyForm" /> </td>
           </tr>
           </table>
           </div>
           </td>
            <logic:present property="value(brk2comm)" name="consignmentByBilingPartyForm" >
            <%brk2commdisplay="display:block";%>
            </logic:present>
           <td  nowrap="nowrap">
           <div  style="<%=brk2commdisplay%>" id="brk2commdisplay">
           <table>
           <tr>
           <td id="brk2Nme"><bean:write property="value(brk2)" name="consignmentByBilingPartyForm" /></td><td><html:radio property="value(brk2paid)"  styleId="brk2paid1" value="Y" name="consignmentByBilingPartyForm" disabled="<%=isdisabled%>"/> </td><td>&nbsp;Yes</td> <td><html:radio property="value(brk2paid)"  styleId="brk2paid2"   value="N" name="consignmentByBilingPartyForm" disabled="<%=isdisabled%>"/></td><td> &nbsp;No</td> <td><html:text property="value(brk2comm)" styleId="brk2comm" readonly="true" size="3" name="consignmentByBilingPartyForm" /> </td>
           </tr>
           </table>
           </div>
           </td>
           <logic:present property="value(brk3comm)" name="consignmentByBilingPartyForm" >
           <%brk3commdisplay="display:block";%> 
           </logic:present>
           <td  nowrap="nowrap">
           <div style="<%=brk3commdisplay%>" id="brk3commdisplay">
           <table>
           <tr>
           <td id="brk3Nme"><bean:write property="value(brk3)" name="consignmentByBilingPartyForm" /></td><td><html:radio property="value(brk3paid)"   styleId="brk3paid1" value="Y" name="consignmentByBilingPartyForm" disabled="<%=isdisabled%>"/> </td><td>&nbsp;Yes</td> <td><html:radio property="value(brk3paid)"  styleId="brk3paid2" value="N" name="consignmentByBilingPartyForm" disabled="<%=isdisabled%>"/></td><td> &nbsp;No</td>  <td><html:text property="value(brk3comm)" styleId="brk3comm" readonly="true" size="3" name="consignmentByBilingPartyForm" /> </td>
           </tr>
           </table>
           </div>
           </td>
            <logic:present property="value(brk4comm)" name="consignmentByBilingPartyForm" >
           <%brk4commdisplay="display:block";%>  
           </logic:present>
           <td  nowrap="nowrap">
           <div style="<%=brk4commdisplay%>" id="brk4commdisplay">
           <table>
           <tr>
           <td id="brk4Nme"><bean:write property="value(brk4)" name="consignmentByBilingPartyForm" /></td><td><html:radio property="value(brk4paid)"   styleId="brk4paid1"  value="Y" name="consignmentByBilingPartyForm" disabled="<%=isdisabled%>"/> </td><td>&nbsp;Yes</td> <td><html:radio property="value(brk4paid)"   styleId="brk4paid2" value="N" name="consignmentByBilingPartyForm" disabled="<%=isdisabled%>"/></td><td> &nbsp;No</td> <td><html:text property="value(brk4comm)" styleId="brk4comm" readonly="true" size="3" name="consignmentByBilingPartyForm" /> </td>
           </tr>
           </table>
           </div>
           </td>
            </tr></table></td></tr>
  <tr><td valign="top" class="tdLayout">
  <table><tr><td>
  <label class="pgTtl">Memo Packets</label>
  </td>
    <td>
  <html:submit property="submit" value="Save" onclick="return confirmChangesMsg('Are You Sure You Want To Save Changes?');" styleClass="submit"/>
    <html:button property="value(fullSale)" onclick="ALLRedioBill('CS_');calculationNewSaleByBIll('CS_')" value="Full Consignment" styleClass="submit"/>&nbsp;
  <html:button property="value(fullreturn)" onclick="ALLRedioBill('RT_');calculationNewSaleByBIll('CS_')" value="Full Return" styleClass="submit"/>&nbsp;
  </td>
    <td><b>Qty</b></td> <td><span id="qty">0</span></td>
    <td><b>Cts</b> </td> <td><span id="cts">0</span></td>
    <td><b>Avg Prc</b></td> <td><span id="avgprc">0</span></td>
    <td><b>Avg Dis</b> </td> <td><span id="avgdis">0</span></td>
    <td><b>Value </b></td><td><span id="vlu">0</span></td>
   <td>Create Excel</td> <td><img src="../images/ico_file_excel.png" onclick="goTo('<%=info.getReqUrl()%>/marketing/memoReturn.do?method=createXL','','')" /></td>
  </tr></table>
  </td></tr>
  <tr><td valign="top" class="tdLayout">
  <table>
  <%
  ArrayList itemHdr = new ArrayList();
   ArrayList pktList = new ArrayList();
  itemHdr.add("BYR");
  itemHdr.add("MEMO");
   itemHdr.add("Packet Code");
  for(int k=0 ; k < byrIdnLstsz;k++){
  String nmeidn = (String)byrIdnLst.get(k);
  String apRd = "ALLRedioBK('apRd' ,'AP_','"+nmeidn+"');calculationNewSaleByBIll('CS_')";
  String csRd = "ALLRedioBK('csRd' ,'CS_','"+nmeidn+"');calculationNewSaleByBIll('CS_')";
  String rtRd = "ALLRedioBK('rtRd' ,'RT_','"+nmeidn+"');calculationNewSaleByBIll('CS_')";
  String apRdId = "apRd_"+nmeidn;
  String csRdId = "csRd_"+nmeidn;
  String rtRdId = "rtRd_"+nmeidn;
  String trms = "value("+nmeidn+")";
  ArrayList byrrln=(ArrayList)byrDtl.get(nmeidn+"_RLN");
  ArrayList byrrlndtl=new ArrayList();
  %>
  <tr><td>
  <label class="pgTtl">Buyer : <%=byrDtl.get(nmeidn+"_BYR")%></label>&nbsp;&nbsp;
  <label class="pgTtl">Qty : <%=byrDtl.get(nmeidn+"_QTY")%></label>&nbsp;&nbsp;
  <label class="pgTtl">Cts : <%=byrDtl.get(nmeidn+"_CTS")%></label>&nbsp;&nbsp;
  <label class="pgTtl">Trms : 
  <html:select property="<%=trms%>" name="consignmentByBilingPartyForm">
  <%for (int i=0;i<byrrln.size();i++){
  byrrlndtl=new ArrayList();
  byrrlndtl=(ArrayList)byrrln.get(i);
   String disp=(String)byrrlndtl.get(0);
   String vale=(String)byrrlndtl.get(1);
  %>
  <html:option value="<%=vale%>" ><%=disp%></html:option>
  <%}%>
  </html:select>
  </label>
  </td></tr>
  <tr><td>
  <%
  
  %>
  <table class="grid1">
  <tr>
  <th>Sr</th>
<th><html:radio property="value(slRd)"  styleId="<%=apRdId%>"  onclick="<%=apRd%>"  value="AP"/>&nbsp;App</th>
<th><html:radio property="value(slRd)" styleId="<%=csRdId%>"  onclick="<%=csRd%>"  value="CS"/>&nbsp;ConsignMent</th>
<th><html:radio property="value(slRd)" styleId="<%=rtRdId%>" onclick="<%=rtRd%>" value="RT"/>&nbsp;Return</th>
  <th>Memo Id</th>
  <th>Packet Code</th>
  <%for(int j=0; j < prps.size(); j++) {
  String lprp=(String)prps.get(j);
  if(prpDspBlocked.contains(lprp)){
  }else{
  if(k==0)
  itemHdr.add(lprp);
  %>
  <th><%=lprp%></th>
  <%}}%>
  <th>RapRte</th><th>RapVlu</th>
                    <th>Dis</th>
                    <th>Prc / Crt </th>
                    <th>ByrDis</th>
                    <th>Quot</th>
                    <th>Note Person</th>
                    <%
                    if(k==0){
                    itemHdr.add("RapRte");itemHdr.add("RapVlu");itemHdr.add("Dis");itemHdr.add("RTE");itemHdr.add("BYRDIS");itemHdr.add("QUOT");itemHdr.add("NOTE_PERSON");
                    }%>
  </tr>
<%
                ArrayList pkts =(ArrayList)pktMemoMap.get(nmeidn);
                int count = 0;
                for(int i=0; i < pkts.size(); i++) {
                PktDtl pkt = (PktDtl)pkts.get(i);
                HashMap pktDtl = new HashMap();
                  pktDtl.put("BYR", byrDtl.get(nmeidn+"_BYR"));
                   pktDtl.put("MEMO", pkt.getMemoId());
                   pktDtl.put("Packet Code",pkt.getVnm());
                %>
                <tr>
                <td><%=(i+1)%></td>
                <%  count=count+1;
                    
                    long pktIdn = pkt.getPktIdn();
                    String pktstt=util.nvl(pkt.getValue("pktstt"));
                    String cbPrp = "value(upd_" + pktIdn + ")";
                    String sttPrp = "value(stt_" + pktIdn + ")" ;
                    String rdISId = "AP_"+count+"_"+nmeidn;
                    String rdCSId = "CS_"+count+"_"+nmeidn;
                    String rdRTId = "RT_"+count+"_"+nmeidn;
                    String rmkTxt =  "value(rmk_" + pktIdn + ")" ;
                    boolean isNtAPdisabled = false;
                    if(pktstt.equals("LB_PRI_AP"))
                    isNtAPdisabled = true;
             %>
             <td><html:radio property="<%=sttPrp%>" styleId="<%=rdISId%>"  value="AP" onclick="calculationNewSaleByBIll('CS_')"/></td>
<td><html:radio property="<%=sttPrp%>" styleId="<%=rdCSId%>"  value="CS"  onclick="calculationNewSaleByBIll('CS_')"/></td>
<td nowrap="nowrap"><html:radio property="<%=sttPrp%>" styleId="<%=rdRTId%>" onclick="calculationNewSaleByBIll('CS_')" value="RT"/>   <html:text property="<%=rmkTxt%>" size="10"  /></td>
                 <td><%=pkt.getMemoId()%><input type="hidden" id="<%=count%>_<%=nmeidn%>_memoid" value="<%=pkt.getMemoId()%>" /></td>
                 <td><%=pkt.getVnm()%><input type="hidden" id="<%=count%>_<%=nmeidn%>_vnm" value="<%=pktIdn%>" /></td>
              
                <%for(int j=0; j < prps.size(); j++) {
                  String lprp = (String)prps.get(j);
                  if(prpDspBlocked.contains(lprp)){
                  }else{
                  pktDtl.put(lprp,util.nvl(pkt.getValue((String)prps.get(j))));
                %>
                    <td nowrap="nowrap"><%=util.nvl(pkt.getValue((String)prps.get(j)))%>
                      <%}if(lprp.equals("CRTWT")){%>
                    <input type="hidden" id="<%=count%>_cts" value="<%=util.nvl(pkt.getValue((String)prps.get(j)))%>" /> </td>
                    <%}%>
                <%}
                %>
                <td><%=util.nvl(pkt.getRapRte())%> <input type="hidden" id="<%=count%>_rap" value="<%=pkt.getRapRte()%>" /></td>
                <td><%=util.nvl(pkt.getValue("rapVlu"))%></td>
                <td><%=util.nvl(pkt.getDis())%></td>
                <td><%=util.nvl(pkt.getRte())%> <input type="hidden" id="<%=count%>_quot" value="<%=pkt.getRte()%>" /> 
                <input type="hidden" id="<%=count%>_fnl" value="<%=pkt.getMemoQuot()%>" /> </td>
                <td><%=util.nvl(pkt.getByrDis())%></td>
                <td><%=util.nvl(pkt.getMemoQuot())%></td>
                <td><%=util.nvl(pkt.getValue("NOTE_PERSON"))%></td>
                </tr>
              <%
               pktDtl.put("RapRte",util.nvl(pkt.getRapRte()));
                pktDtl.put("RapVlu",util.nvl(pkt.getValue("rapVlu")));
                 pktDtl.put("Dis",util.nvl(pkt.getDis()));
                  pktDtl.put("RTE",util.nvl(pkt.getRte()));
                   pktDtl.put("BYRDIS",util.nvl(pkt.getByrDis()));
                 pktDtl.put("QUOT",util.nvl(pkt.getMemoQuot()));
                 pktDtl.put("NOTE_PERSON",util.nvl(pkt.getValue("NOTE_PERSON")));
                 pktList.add(pktDtl);
                 }%>
  </table>
  </td></tr>
  <%}
     session.setAttribute("pktList", pktList);
     session.setAttribute("itemHdr", itemHdr);
  %>
  </table></td></tr>
  </html:form>
  <%

  }else{%>
  <tr><td valign="top" class="hedPg">
Sorry No Result Found</td></tr>
  <%}}%>
  <tr>
    <td><jsp:include page="/footer.jsp" /> </td>
    </tr>
  </table>
  </body>
</html>