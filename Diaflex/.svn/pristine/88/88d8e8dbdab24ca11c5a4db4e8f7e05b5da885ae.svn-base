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
    <title>Memo Sales</title>
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
   String view = util.nvl((String)request.getAttribute("view"));
ArrayList prpDspBlocked = info.getPageblockList();
 ArrayList  pageList = new ArrayList();
 HashMap pageDtlMap=new HashMap();
HashMap allPageDtl= (info.getPageDetails() == null)?new HashMap():(HashMap)info.getPageDetails();
HashMap pageDtl=(HashMap)allPageDtl.get("MEMO_SALE_BILLING");
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
  <img src="../images/line.jpg" border="0" /></td><td valign="middle"> <span class="pgHed">Memo Sales</span> </td>
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
<html:form action="/marketing/saleByBilling.do?method=loadPkt" method="POST" >
<table><tr><td valign="top">
  <table class="grid1" >
  <tr><th colspan="2">Memo Search</th></tr>
<tr>
                <td>Buyer Name :</td>
                <td>
   <html:select property="invByrIdn" name="saleByBillingForm" onchange="getSaleByrFromBill(this)" styleId="byrId" >
    <html:option value="0">---select---</html:option>
    <html:optionsCollection property="invByrLst" name="saleByBillingForm"  value="byrIdn" label="byrNme" />
    </html:select>
  </td>
                </tr>
                <!--<tr><td colspan="2" ><div id="byr"></div> </td></tr>-->
  <tr><td>Packets. </td><td>
<html:textarea property="value(vnmLst)" name="saleByBillingForm" styleId="vnmLst" /> </td> </tr>
<tr><td colspan="2"><html:submit property="submit" value="View" styleClass="submit"/></td></tr>
  </table></td>
  <td valign="top"><div id="byr"></div> </td>
  </tr></table>
  </html:form>
  </td>
  <!--<td valign="top" class="tdLayout" align="left">
     <%if(request.getAttribute("view")!=null){%>
    <table class="grid1">
    <tr> <td></td> <td><b> Total</b></td><td><b>Selected</b></td> </tr>
    <tr> <td><b>Qty</b></td> <td><bean:write property="qty" name="saleByBillingForm" /></td><td><span id="qty">0</span></td> </tr>
    <tr> <td><b>Cts</b> </td> <td><bean:write property="cts" name="saleByBillingForm" /></td><td><span id="cts">0</span></td>  </tr>
    <tr> <td><b>Avg Prc</b></td> <td><bean:write property="avgPrc" name="saleByBillingForm" /></td><td><span id="avgprc">0</span></td>  </tr>
    <tr> <td><b>Avg Dis</b> </td> <td><bean:write property="avgDis" name="saleByBillingForm" /></td><td><span id="avgdis">0</span></td>  </tr>
    <tr> <td><b>Value </b></td> <td><bean:write property="vlu" name="saleByBillingForm" /></td><td><span id="vlu">0</span></td>  </tr>
    </table>
    <%}%>
    </td>-->
<td  valign="top">
     <%if(request.getAttribute("view")!=null && view.equals("Y")){
     ArrayList chargesLst=(ArrayList)session.getAttribute("chargesLst");
     HashMap fisalcharges=(HashMap)session.getAttribute("fisalcharges");
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
    String autoopt=(String)dtl.get("autoopt");
    String flg=(String)dtl.get("flg");
    String typ=(String)dtl.get("typ");
    String fctr=(String)dtl.get("fctr");
    String fun=(String)dtl.get("fun");
    String rmk=(String)dtl.get("rmk");
    String field = "value("+typ+")";
    String fieldRmk = "value("+typ+"_rmk)";
    String onchang="chargesmanualSaleByBilling('"+typ+"','"+i+"')";
    String onchangrmk="chargesmanualrmk('"+typ+"','"+i+"')";
    String chargesfixed="chargesfixed('"+typ+"')";
    String fieldId = typ+"_rmk";
    if(flg.equals("MNL")){
    %>
    <tr>
    <td nowrap="nowrap"><b><%=dsc%></b></td> 
    <td nowrap="nowrap"><b><span id="<%=typ%>_dis"></span></b></td>
    <td nowrap="nowrap"><html:text property="<%=field%>" size="6" styleId="<%=typ%>" onchange="<%=onchang%>" name="saleByBillingForm"/></td>
    <td nowrap="nowrap">
    <%if(rmk.equals("Y")){%>
    Remark<html:text property="<%=fieldRmk%>" styleId="<%=fieldId%>" onchange="<%=onchangrmk%>" name="saleByBillingForm" size="10"/>
    <%}%>
    </td>
    </tr>
    <%}else{%>
    <tr nowrap="nowrap"><td nowrap="nowrap"><b><%=dsc%></b>
    <%if((flg.equals("AUTO") && autoopt.equals("OPT")) || (flg.equals("FIX") && autoopt.equals("OPT"))){
    String chk="checked=\"checked\"";
    if((typ.contains("IMP_DUTY") || typ.contains("SHP")) && cnt.equalsIgnoreCase("xljf"))
    chk="";
    %>
    <input type="checkbox" name="<%=typ%>_AUTO" id="<%=typ%>_AUTO" <%=chk%> onchange="validateAutoOpt('<%=typ%>_AUTO')" title="Uncheck if don't wont apply"/>
    <%}%>
    </td>
    <td nowrap="nowrap"><b><span id="<%=typ%>_dis"></span></b></td>
    <td nowrap="nowrap">
    <%if(!flg.equals("FIX")){%>
    <html:hidden property="<%=field%>"  styleId="<%=typ%>" name="saleByBillingForm"/>
    <!--<input type="button" name="charge_<%=typ%>" id="charge_<%=typ%>" value="Charge"  onclick="<%=onchang%>" class="submit" />-->
    <%}else{
    ArrayList dataLst=new ArrayList();
    ArrayList data=new ArrayList();
    dataLst=(ArrayList)fisalcharges.get(typ);
    %>
    <html:select property="<%=field%>" styleId="<%=typ%>" name="saleByBillingForm" onchange="<%=chargesfixed%>">
    <%for(int c=0;c<dataLst.size();c++){
    data=new ArrayList();
    data=(ArrayList)dataLst.get(c);
    String fldval=util.nvl((String)data.get(1));
    String flddsp=util.nvl((String)data.get(0));
    %>
    <html:option value="<%=fldval%>" ><%=flddsp%></html:option>
    <%}%>
    </html:select>
    <%}%>
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
    
    <tr style="<%=ttlDisplay%>"><td nowrap="nowrap"><b>Total</b></td><td nowrap="nowrap"><b><span id="net_dis"></span></b></td><td></td><td></td></tr>
    
    </table>
    <%}}%>
    </td>
  </tr></table>
  </td></tr>
  <%
  ArrayList prps = (info.getValue("MDL_MEMO_RTRN") == null)?new ArrayList():(ArrayList)info.getValue("MDL_MEMO_RTRN");
  HashMap pktMemoMap =(HashMap)session.getAttribute("pktMemoMap");
  ArrayList byrIdnLst =(ArrayList)session.getAttribute("byrIdnLst");
  HashMap byrDtl =(HashMap)session.getAttribute("byrDtl");
  if(!view.equals("")){
  if(byrIdnLst!=null && byrIdnLst.size()>0){
  int byrIdnLstsz=byrIdnLst.size();
  %>
  <html:form action="/marketing/saleByBilling.do?method=save" method="POST" >
          <%if(request.getAttribute("view")!=null && view.equals("Y")){
    ArrayList chargesLst=(ArrayList)session.getAttribute("chargesLst");
    HashMap fisalcharges=(HashMap)session.getAttribute("fisalcharges");
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
    String fieldidval="";
    if(flg.equals("FIX")){
    ArrayList dataLst=new ArrayList();
    ArrayList data=new ArrayList();
    dataLst=(ArrayList)fisalcharges.get(typ);
    data=(ArrayList)dataLst.get(0);
    fieldidval=util.nvl((String)data.get(1));
    }
    %>
    <html:hidden property="<%=field%>" name="saleByBillingForm" styleId="<%=fieldId%>" value="<%=fieldidval%>"  />
   <%if(rmk.equals("Y")){%>
    <html:hidden property="<%=fieldRmk%>" name="saleByBillingForm" styleId="<%=fieldIdrmk%>"  />
    <%}
    if((flg.equals("AUTO") && autoopt.equals("OPT")) || (flg.equals("FIX") && autoopt.equals("OPT"))){
    String chk="N";
    if((typ.contains("IMP_DUTY") || typ.contains("SHP")) && cnt.equalsIgnoreCase("xljf"))
    chk="Y";
    %>
    <html:hidden property="<%=fieldauto%>" name="saleByBillingForm" styleId="<%=fieldIdauto%>" value="<%=chk%>"  />
    <%}}%>
   <html:hidden property="value(vluamt)" name="saleByBillingForm" styleId="vluamt"  />
    <%}}
    %>
        <html:hidden property="value(invByrIdn)" name="saleByBillingForm" styleId="invByrIdn" />       
                <tr><td valign="top" class="tdLayout"><table>
            <tr>
            <td nowrap="nowrap"><span class="pgTtl" >Company Name</span></td>
          <td>
           <html:select property="value(grpIdn)" name="saleByBillingForm"  styleId="grpIdn"  >
             <html:optionsCollection property="groupList" name="saleByBillingForm"
             value="idn" label="addr" />
            </html:select>
          </td>
                <td nowrap="nowrap"><span class="pgTtl" >Bank Selection</span></td>
               <td>
             
             <html:select property="value(bankIdn)" name="saleByBillingForm" onchange="SetBankAddr(this)"  styleId="bankIdn"  >
             <html:optionsCollection property="bankList" name="saleByBillingForm"
             value="idn" label="addr" />
            
            </html:select>
            </td>
            <td> <html:select property="value(bankAddr)" name="saleByBillingForm" style="dispaly:none" styleId="bankAddr">
             <html:optionsCollection property="bnkAddrList" name="saleByBillingForm"
             value="idn" label="addr" />
            </html:select>
            
            </td>
            <td valign="top" nowrap="nowrap"><table><tr><td><span class="pgTtl" >Courier :</span> </td>
             <td> <html:select property="value(courier)" name="saleByBillingForm" style="dispaly:none" styleId="courier">
            <html:optionsCollection property="courierList" name="saleByBillingForm"
             value="idn" label="addr" />
            </html:select>
            </td>
            </tr></table> </td>
            </tr>
            </table></td>
        </tr>
  <tr><td valign="top" class="tdLayout">
  <table><tr><td>
  <label class="pgTtl">Memo Packets</label>
  </td>
    <td>
  <html:submit property="submit" value="Save" onclick="return confirmChangesMsg('Are You Sure You Want To Save Changes?');" styleClass="submit"/>
  <html:button property="value(fullapprove)" onclick="ALLRedioBill('AP_');calculationNewSaleByBIll('SL_')" value="Full Approve" styleClass="submit"/>&nbsp;
  <html:button property="value(fullSale)" onclick="ALLRedioBill('SL_');calculationNewSaleByBIll('SL_')" value="Full Sale" styleClass="submit"/>&nbsp;
  <html:button property="value(fullreturn)" onclick="ALLRedioBill('RT_');calculationNewSaleByBIll('SL_')" value="Full Return" styleClass="submit"/>&nbsp;
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
  String apRd = "ALLRedioBK('apRd' ,'AP_','"+nmeidn+"');calculationNewSaleByBIll('SL_')";
  String slRd = "ALLRedioBK('slRd' ,'SL_','"+nmeidn+"');calculationNewSaleByBIll('SL_')";
  String rtRd = "ALLRedioBK('rtRd' ,'RT_','"+nmeidn+"');calculationNewSaleByBIll('SL_')";
  String apRdId = "apRd_"+nmeidn;
  String slRdId = "slRd_"+nmeidn;
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
  <html:select property="<%=trms%>" name="saleByBillingForm">
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
  <table class="grid1">
  <tr>
  <th>Sr</th>
<th><html:radio property="value(slRd)"  styleId="<%=apRdId%>"  onclick="<%=apRd%>"  value="AP"/>&nbsp;App</th>
<th><html:radio property="value(slRd)" styleId="<%=slRdId%>"  onclick="<%=slRd%>"  value="SL"/>&nbsp;Sale</th>
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
                       <%
                    if(k==0){
                    itemHdr.add("RapRte");itemHdr.add("RapVlu");itemHdr.add("Dis");itemHdr.add("RTE");itemHdr.add("BYRDIS");itemHdr.add("QUOT");
                    }%>
  </tr>
<%
                ArrayList pkts =(ArrayList)pktMemoMap.get(nmeidn);
                int count = 0;
                for(int i=0; i < pkts.size(); i++) {
                  HashMap pktDtl = new HashMap();
                  PktDtl pkt = (PktDtl)pkts.get(i);
                %>
                <tr>
                <td><%=(i+1)%></td>
                <%  count=count+1;
                    
                   
                  pktDtl.put("BYR", byrDtl.get(nmeidn+"_BYR"));
                   pktDtl.put("MEMO", pkt.getMemoId());
                   pktDtl.put("Packet Code",pkt.getVnm());
                    long pktIdn = pkt.getPktIdn();
                    String pktstt=util.nvl(pkt.getValue("pktstt"));
                    String cbPrp = "value(upd_" + pktIdn + ")";
                    String sttPrp = "value(stt_" + pktIdn + ")" ;
                    String rdISId = "AP_"+count+"_"+nmeidn;
                    String rdSLId = "SL_"+count+"_"+nmeidn;
                    String rdRTId = "RT_"+count+"_"+nmeidn;
                    String rmkTxt =  "value(rmk_" + pktIdn + ")" ;
                    boolean isNtAPdisabled = false;
                    if(pktstt.equals("LB_PRI_AP"))
                    isNtAPdisabled = true;
             %>
<td><html:radio property="<%=sttPrp%>" styleId="<%=rdISId%>"  value="AP" onclick="calculationNewSaleByBIll('SL_')"/></td>
<td><html:radio property="<%=sttPrp%>" styleId="<%=rdSLId%>"  value="SL" disabled="<%=isNtAPdisabled%>"  onclick="calculationNewSaleByBIll('SL_')"/></td>
<td nowrap="nowrap"><html:radio property="<%=sttPrp%>" styleId="<%=rdRTId%>" onclick="calculationNewSaleByBIll('SL_')" value="RT"/>   <html:text property="<%=rmkTxt%>" size="10"  /></td>
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
                </tr>
              <%
                 pktDtl.put("RapRte",util.nvl(pkt.getRapRte()));
                pktDtl.put("RapVlu",util.nvl(pkt.getValue("rapVlu")));
                 pktDtl.put("Dis",util.nvl(pkt.getDis()));
                  pktDtl.put("RTE",util.nvl(pkt.getRte()));
                   pktDtl.put("BYRDIS",util.nvl(pkt.getByrDis()));
                 pktDtl.put("QUOT",util.nvl(pkt.getMemoQuot()));
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
  <%}else{%>
  <tr><td valign="top" class="hedPg">
Sorry No Result Found</td></tr>
  <%}}%>
  <tr>
    <td><jsp:include page="/footer.jsp" /> </td>
    </tr>
  </table>
  </body>
</html>