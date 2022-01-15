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
    <title>Consignment Sales</title>
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
String mgmt_Dflt = util.nvl((String)dbinfo.get("MGMT_DFLT"));
String cnt = (String)dbinfo.get("CNT");
HashMap allPageDtl= (info.getPageDetails() == null)?new HashMap():(HashMap)info.getPageDetails();
HashMap pageDtl=(HashMap)allPageDtl.get("CONSIGNMENT_FORM");
ArrayList pageList=new ArrayList();
HashMap pageDtlMap=new HashMap();
String fld_nme="",fld_ttl="",val_cond="",dflt_val="",fld_typ="",form_nme="",lov_qry="";
String view = util.nvl((String)request.getAttribute("view")); 
ArrayList prpDspBlocked = info.getPageblockList();
        String logId=String.valueOf(info.getLogId());
        String onfocus="cook('"+logId+"')";
        %>
 <body onfocus="<%=onfocus%>" onload="MM_preloadImages('images/faunalogo.png')" topmargin="0" leftmargin="0" rightmargin="0" bottommargin="0">

<% 
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
       <option value="id">Memo Id</option>     
        <option value="dte">Date</option>     
      </select>
    <%}%>
<div id="backgroundPopup"></div>
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
  <img src="../images/line.jpg" border="0" /></td><td valign="middle"> <span class="pgHed">Consignment Sales</span> </td>
  </tr></table>
  </td>
  </tr>
   <tr><td valign="top" class="tdLayout">
   <table cellpadding="2" cellspacing="2" >
   <%
        if(request.getAttribute("error")!=null){%>
        <tr><td height="15"  valign="top" class="tdLayout"><span class="redLabel"> <%=request.getAttribute("error")%></span></td></tr>
       <% }
        if(request.getAttribute("RTMSG")!=null){%>
        <tr><td height="15"><span class="redLabel"> <%=request.getAttribute("RTMSG")%></span></td></tr>
       <% }
        if(request.getAttribute("SLMSG")!=null){%>
        <tr><td height="15"  valign="top" class="tdLayout"><span class="redLabel"> <%=request.getAttribute("SLMSG")%></span></td></tr>
       <% }
        %>
       
        </table></td></tr>
  <tr><td valign="top" class="tdLayout">
  <table><tr><td>
  
  <bean:parameter id="reqIdn" name="memoIdn" value="0"/>
  <%
    String pgTtl = " Memo Sales" ;
   
    if(reqIdn=="0")
    reqIdn = request.getParameter("memoId");
  
    if(view.equals("Y")){
        pgTtl += " Id : " + reqIdn ;
    }
  %>
 
    <html:form action="/marketing/consignmentSale.do?method=load" method="POST" enctype="multipart/form-data">
    <table><tr><td valign="top">
        <table class="grid1" >
       
        <tr><th colspan="2">Consignment Search </th></tr>
          
            
             <tr>
           <td colspan="2"> <html:radio property="value(memoSrch)" value="ByrSrch"  onclick="DisplayMemoSrch('MS_1')"  styleId="MS_1" />  Consignment Search By Buyer </td>
           </tr>
             <tr style="display:none" id="DMS_1"><td colspan="2">
             
              <table cellpadding="5"><tr>
                <td>Buyer</td>
                <td>
                <html:select property="value(byrIdn)" styleId="byrId" onchange="getTrms(this,'SALE')" >
                <html:option value="0">---select---</html:option>
                <html:optionsCollection property="byrLstFetch"  value="byrIdn" label="byrNme" />
                
                </html:select>
                </td> </tr>
                 <tr>
            <td> <span class="txtBold" >Terms </span></td><td>
             
             <html:select property="value(byrTrm)"  styleId="rlnId" onchange="GetConsignmentIdn()" >
            
            </html:select>
            </td>
            </tr>
      <%
      String displayupload="display:none";
      pageList=(ArrayList)pageDtl.get("UPLOADFILE");
      if(pageList!=null && pageList.size() >0){
      for(int j=0;j<pageList.size();j++){
      pageDtlMap=(HashMap)pageList.get(j);
      fld_ttl=(String)pageDtlMap.get("fld_ttl");
      val_cond=(String)pageDtlMap.get("val_cond");
      fld_typ=(String)pageDtlMap.get("fld_typ");
      dflt_val = (String)pageDtlMap.get("dflt_val");
      displayupload="";
      }}%>
      <tr style="<%=displayupload%>"><td>Upload Price File</td><td>
      <html:file property="loadfile" size="40" name="consignmentSaleForm"  onchange="check_file();" styleId="fileUpload" />
      </td></tr>
      </table>
                
            </td> 
            </tr>
            <tr>
           <td colspan="2"> <html:radio property="value(memoSrch)" value="MemoSrch"    styleId="MS_2" onclick="DisplayMemoSrch('MS_2')"  /> Consignment Search By Memo Ids</td>
           </tr>
            <tr style="display:none" id="DMS_2">
            <td>
            <table>
            <tr>
            <td>Memo Ids</td><td><html:text property="memoIdn" name="consignmentSaleForm" styleId="memoid"/></td></tr>
           <tr><td>Packets. </td><td>
            <html:textarea property="value(vnmLst)" name="consignmentSaleForm" styleId="vnmLst" /> </td> </tr>
           </table>
            </td></tr>
             <tr><td colspan="2"><html:submit property="submit" value="View" styleClass="submit" onclick="return validatememosale()"/></td></tr>
        </table></td><td>
      <div id="memoIdn"></div> 
        </td></tr></table>
       
    </html:form>
    </td><td valign="top">
     <%if(request.getAttribute("view")!=null || view.equals("Y")){%>
    <table class="grid1">
    <tr> <td></td> <td><b> Total</b></td><td><b>Selected</b></td> </tr>
    <tr> <td><b>Qty</b></td> <td><bean:write property="qty" name="consignmentSaleForm" /></td><td><span id="qty">0</span></td> </tr>
    <tr> <td><b>Cts</b> </td> <td><bean:write property="cts" name="consignmentSaleForm" /></td><td><span id="cts">0</span></td>  </tr>
    <tr> <td><b>Avg Prc</b></td> <td><bean:write property="avgPrc" name="consignmentSaleForm" /></td><td><span id="avgprc">0</span></td>  </tr>
    <tr> <td><b>Avg Dis</b> </td> <td><bean:write property="avgDis" name="consignmentSaleForm" /></td><td><span id="avgdis">0</span></td>  </tr>
    <tr> <td><b>Value </b></td> <td><bean:write property="vlu" name="consignmentSaleForm" /></td><td><span id="vlu">0</span></td>  </tr>
  
    </table>
    <%}%>
    </td>
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
    String onchang="chargesmanual('"+typ+"','"+i+"')";
    String onchangrmk="chargesmanualrmk('"+typ+"','"+i+"')";
    String chargesfixed="chargesfixed('"+typ+"')";
    String fieldId = typ+"_rmk";
    if(flg.equals("MNL")){
    %>
    <tr>
    <td nowrap="nowrap"><b><%=dsc%></b></td> 
    <td nowrap="nowrap"><b><span id="<%=typ%>_dis"></span></b></td>
    <td nowrap="nowrap"><html:text property="<%=field%>" size="6" styleId="<%=typ%>" onchange="<%=onchang%>" name="consignmentSaleForm"/></td>
    <td nowrap="nowrap">
    <%if(rmk.equals("Y")){%>
    Remark<html:text property="<%=fieldRmk%>" styleId="<%=fieldId%>" onchange="<%=onchangrmk%>" name="consignmentSaleForm" size="10"/>
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
    <html:hidden property="<%=field%>"  styleId="<%=typ%>" name="consignmentSaleForm"/>
    <!--<input type="button" name="charge_<%=typ%>" id="charge_<%=typ%>" value="Charge"  onclick="<%=onchang%>" class="submit" />-->
    <%}else{
    ArrayList dataLst=new ArrayList();
    ArrayList data=new ArrayList();
    dataLst=(ArrayList)fisalcharges.get(typ);
    %>
    <html:select property="<%=field%>" styleId="<%=typ%>" name="consignmentSaleForm" onchange="<%=chargesfixed%>">
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
    <%
    if(view.equals("Y")) {
        String formAction = "/marketing/consignmentSale.do?method=save&memoIdn="+ reqIdn ; 
        String memoIdn = util.nvl((String)request.getAttribute("memoIdn"));
        ArrayList itemHdr = new ArrayList();
        ArrayList pktList = new ArrayList();
        HashMap pktPrpMap = new HashMap();
    %>
        <html:form action="<%=formAction%>" method="POST" onsubmit="loading()" >
        
    <%ArrayList chargesLst=(ArrayList)session.getAttribute("chargesLst");
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
    if(typ.equals("MGMT") && !mgmt_Dflt.equals(""))
    fieldidval=mgmt_Dflt;
    if(flg.equals("FIX")){
    ArrayList dataLst=new ArrayList();
    ArrayList data=new ArrayList();
    dataLst=(ArrayList)fisalcharges.get(typ);
    data=(ArrayList)dataLst.get(0);
    fieldidval=util.nvl((String)data.get(1));
    }
    %>
    <html:hidden property="<%=field%>" name="consignmentSaleForm" styleId="<%=fieldId%>" value="<%=fieldidval%>"  />
   <%if(rmk.equals("Y")){%>
    <html:hidden property="<%=fieldRmk%>" name="consignmentSaleForm" styleId="<%=fieldIdrmk%>"  />
    <%}
    if((flg.equals("AUTO") && autoopt.equals("OPT")) || (flg.equals("FIX") && autoopt.equals("OPT"))){
    String chk="N";
    if((typ.contains("IMP_DUTY") || typ.contains("SHP")) && cnt.equalsIgnoreCase("xljf"))
    chk="Y";
    %>
    <html:hidden property="<%=fieldauto%>" name="consignmentSaleForm" styleId="<%=fieldIdauto%>" value="<%=chk%>"  />
    <%}}%>
   <html:hidden property="value(vluamt)" name="consignmentSaleForm" styleId="vluamt"  />
    <%}
    %>
        <div id="popupContactSale" >

<table align="center" cellpadding="0" cellspacing="0" >
<tr><td>Do You Want To Confrim For delivery?</td></tr>
<tr><td><table><tr><td><html:radio property="value(isDLV)"   value="Yes"/>YES </td>
<td> <html:radio property="value(isDLV)" value="No"/>NO </td></tr></table> </td> </tr>
<tr><td><html:submit property="value(submit)" value="Submit" styleClass="submit"/> </td> </tr>
</table>
</div>
        <html:hidden property="nmeIdn" name="consignmentSaleForm" styleId="nmeIdn"  />
          <html:hidden property="relIdn" name="consignmentSaleForm" />
          <table><tr><td>
          <table cellspacing="2">
          <tr><td> <span class="pgTtl" > Old Selection</span></td> <td><b>Buyer Name :</b></td><td> <bean:write property="byr" name="consignmentSaleForm"/> </td><td><b>Terms :</b></td> <td> <bean:write property="value(trmsLb)" name="consignmentSaleForm"/> </td>
          <td><b>Email :</b></td> <td> <bean:write property="value(email)" name="consignmentSaleForm"/> </td>
          <td><b>Mobile No :</b></td> <td> <bean:write property="value(mobile)" name="consignmentSaleForm"/> </td>
          <td><b>Office No :</b></td> <td> <bean:write property="value(office)" name="consignmentSaleForm"/> </td>
          <td><b>Remark/Comment</b>&nbsp;<html:text property="value(rmk)" size="15"  /></td>
          </tr>
          </table></td>
          </tr>
          <tr>
            <td><table><tr><td><span class="pgTtl" >Exchange Rate :</span> </td>
           <td>
           <logic:equal property="value(cur)"  name="consignmentSaleForm"  value="USD" >
           <html:text property="value(exhRte)" styleId="exhRte" readonly="true" size="5" name="consignmentSaleForm" />
           </logic:equal>
            <logic:notEqual property="value(cur)"  name="consignmentSaleForm"  value="USD" >
            <html:text property="value(exhRte)"  styleId="exhRte" size="5" name="consignmentSaleForm" />
            </logic:notEqual>
           </td>
            </tr></table></td></tr>
          <tr><td>
            <table>
                <tr>
               
              
                <td><span class="pgTtl" >buyer List</span></td>
                <td>
                <html:select property="byrIdn" name="consignmentSaleForm"  styleId="byrId1" onchange="GetADDR()" >
               <html:option value="0" >---select---</html:option>
                <html:optionsCollection property="byrLst" name="consignmentSaleForm"  value="byrIdn" label="byrNme" />
                
                </html:select>
                </td>  <td><span class="pgTtl" >Address</span></td>
                <td><html:select property="value(addr)" styleId="addrId" name="consignmentSaleForm" >
                
                 <html:optionsCollection property="addrList" name="consignmentSaleForm"  value="idn" label="addr" />
                </html:select>
                
               </td>
                 <td><span class="pgTtl" >Terms</span></td><td>
             
             <html:select property="value(byrTrm)" name="consignmentSaleForm"  styleId="rlnId1"  >
            <html:optionsCollection property="trmsLst" name="consignmentSaleForm"
            label="trmDtl" value="relId" />
            </html:select>
            </td>
                </tr>
            </table></td></tr>
             <logic:present property="bankList" name="consignmentSaleForm" >
            <tr><td><table>
            <tr>
                <td><span class="pgTtl" >Bank Selection</span></td>
               
               <td>
             
             <html:select property="value(comIdn)" name="consignmentSaleForm" onchange="SetBankAddr(this)"  styleId="bankIdn"  >
             <html:option value="0" >---select---</html:option>
             <html:optionsCollection property="bankList" name="consignmentSaleForm"
             value="idn" label="addr" />
            
            </html:select>
            </td>
               
              <td> <html:select property="value(bankIdn)" name="consignmentSaleForm" style="dispaly:none" 
            styleId="bankAddr">
            
            </html:select>
            
            </td>
            
            <td> <html:select property="value(bankAddr)" name="consignmentSaleForm" style="dispaly:none" styleId="bankAddr">
            
            </html:select>
            
            </td>
            </tr>
            
            </table></td>
            
            </tr>
           </logic:present>
            <tr>
            <td><table><tr><td><span class="pgTtl" >Broker Commission :</span> </td>
            <logic:present property="value(aadatcomm)" name="consignmentSaleForm" >
            <td><bean:write property="value(aadatcomm)" name="consignmentSaleForm" /> </td><td><html:radio property="value(aadatpaid)" value="Y"  name="consignmentSaleForm"/> </td><td>&nbsp;Yes</td> <td><html:radio property="value(aadatpaid)" value="N" name="consignmentSaleForm"/></td><td> &nbsp;No</td><td><html:text property="value(aadatcomm)" readonly="true" size="3" name="consignmentSaleForm" /> </td>
            </logic:present>
             <logic:present property="value(brk1comm)" name="consignmentSaleForm" >
            <td><bean:write property="value(brk1)" name="consignmentSaleForm" /></td><td><html:radio property="value(brk1paid)" value="Y" name="consignmentSaleForm"/></td><td>&nbsp;Yes</td>  <td><html:radio property="value(brk1paid)" value="N" name="consignmentSaleForm"/></td><td>&nbsp;No</td> <td><html:text property="value(brk1comm)" readonly="true" size="3" name="consignmentSaleForm" /> </td>
           </logic:present>
            <logic:present property="value(brk2comm)" name="consignmentSaleForm" >
            <td><bean:write property="value(brk2)" name="consignmentSaleForm" /></td><td><html:radio property="value(brk2paid)" value="Y" name="consignmentSaleForm"/> </td><td>&nbsp;Yes</td> <td><html:radio property="value(brk2paid)" value="N" name="consignmentSaleForm"/></td><td> &nbsp;No</td> <td><html:text property="value(brk2comm)" readonly="true" size="3" name="consignmentSaleForm" /> </td>
            </logic:present>
             <logic:present property="value(brk3comm)" name="consignmentSaleForm" >
            <td><bean:write property="value(brk3)" name="consignmentSaleForm" /></td><td><html:radio property="value(brk3paid)" value="Y" name="consignmentSaleForm"/> </td><td>&nbsp;Yes</td> <td><html:radio property="value(brk3paid)" value="N" name="consignmentSaleForm"/></td><td> &nbsp;No</td>  <td><html:text property="value(brk3comm)" readonly="true" size="3" name="consignmentSaleForm" /> </td>
           </logic:present>
            <logic:present property="value(brk4comm)" name="consignmentSaleForm" >
            <td><bean:write property="value(brk4)" name="consignmentSaleForm" /></td><td><html:radio property="value(brk4paid)" value="Y" name="consignmentSaleForm"/> </td><td>&nbsp;Yes</td> <td><html:radio property="value(brk4paid)" value="N" name="consignmentSaleForm"/></td><td> &nbsp;No</td> <td><html:text property="value(brk4comm)" readonly="true" size="3" name="consignmentSaleForm" /> </td>
           </logic:present>
            </tr></table> </td>
            </tr>
            <tr><td><span class="pgTtl" >Extra Charge : </span><input type="text" id="echarge" name="echarge" onchange="isNumericDecimal(this.id)"/></td></tr>
            <%if(cnt.equals("hk")){%>
            <tr>
            <td>
                <div class="float"><span class="pgTtl" >Delivery Date :</span> <html:text property="value(dlvDte)"  styleId="dlvDte" /></div>
                <div class="float"> <a href="#"  onClick="setYears(1900, <%=info.getCurrentYear()%>);showCalender(this, 'dlvDte');">
                <img src="<%=info.getReqUrl()%>/images/calender.png"></a></div>
            </td>
            </tr>
            <%}%>
            <tr><td nowrap="nowrap">
            
            <label class="pgTtl">Memo Packets</label>
            <%
    pageList= ((ArrayList)pageDtl.get("EXCEL") == null)?new ArrayList():(ArrayList)pageDtl.get("EXCEL");            
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
            <%
                ArrayList prps = (info.getValue("MDL_MEMO_RTRN") == null)?new ArrayList():(ArrayList)info.getValue("MDL_MEMO_RTRN");
             %>
             <html:submit property="submit" value="Save" onclick="return confirmChangesCONS()" styleClass="submit"/>&nbsp;
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
            val_cond=val_cond.replaceAll("URL",info.getReqUrl());
            val_cond=val_cond.replaceAll("~MEMOIDN~",memoIdn);
            if(fld_typ.equals("S")){
            %>
            <html:submit property="<%=fld_nme%>" value="<%=fld_ttl%>" onclick="<%=val_cond%>" styleClass="submit"/>&nbsp;
            <%}else if(fld_typ.equals("B")){%>
            <html:button property="<%=fld_nme%>" value="<%=fld_ttl%>" onclick="<%=val_cond%>" styleClass="submit"/>&nbsp;
            <%}}}
            %>
             </td>
             
             </tr>
             <tr><td>
            <table class="grid1">
                <tr>
                    <th>Sr</th>
                     <th><html:radio property="value(slRd)"  styleId="csRd"  onclick="ALLRedio('csRd' ,'CS_');calculationConsignmentNew('SL_')"  value="CS"/>&nbsp;CS</th>
                    <th><html:radio property="value(slRd)" styleId="slRd"  onclick="ALLRedio('slRd' ,'SL_');calculationConsignmentNew('SL')"  value="DL"/>&nbsp;SL</th>
                    <th><html:radio property="value(slRd)" styleId="rtRd" onclick="ALLRedio('rtRd' ,'RT_');calculationConsignmentNew('SL_')"  value="RT"/> Return</th>
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
        
            <th> <input type="radio" id="<%=fld_typ%>" name="<%=fld_nme%>" onclick="<%=val_cond%>"/><%=fld_ttl%> </th>
            <%
            }}
            itemHdr.add("Memo Id");itemHdr.add("Packet Code");itemHdr.add("Quot");
            itemHdr.add("ByrDis");itemHdr.add("Prc / Crt");itemHdr.add("Dis");
            %>
                    <th>Memo Id</th>
                    <th>Memo Date</th>
                    <th>Packet Code</th>
                    <logic:equal property="value(fnlFields)"  name="consignmentSaleForm"  value="Y" >
                    <th nowrap="nowrap">Byr Fnl Prc</td>
                    <th nowrap="nowrap">Byr Fnl Dis</td> 
                    </logic:equal>
                    <th>Quot</th>
                    <th>ByrDis</th>
                    <th>Prc / Crt </th>
                    <th>Dis</th>
                      <%for(int j=0; j < prps.size(); j++) {
                     String lprp=(String)prps.get(j);
                     if(prpDspBlocked.contains(lprp)){
                     }else{%>
                        <th><%=lprp%></th>
                    <%
                    itemHdr.add(lprp);
                    }}%>
                    <th>Live Status</th>
                    <th>RapRte</th>
                    <th>RapVlu</th>                  
            
                    
                    
                    
                </tr>
            <%
            itemHdr.add("Live Status");itemHdr.add("RapRte");itemHdr.add("RapVlu");
             int count =0 ;
                ArrayList pkts = (info.getValue("PKTS") == null)?new ArrayList():(ArrayList)info.getValue("PKTS");
                for(int i=0; i < pkts.size(); i++) {%>
                <tr>
                <td><%=(i+1)%></td>
                <%    count=i+1;
                    PktDtl pkt = (PktDtl)pkts.get(i);
                    long pktIdn = pkt.getPktIdn();
                    String cbPrp = "value(upd_" + pktIdn + ")";
                    String sttPrp = "value(stt_" + pktIdn + ")" ;
                    String rdCSId = "CS_"+count;
                     String rdSLId = "SL_"+count;
                    String rdRTId = "RT_"+count;
                    String rdInvId = "INV_"+count;
                    String onClickAp = "SetCalculation('"+pktIdn+"','CS','"+pkt.getMemoId()+"')";
                    String onClickSl = "SetCalculation('"+pktIdn+"','SL','"+pkt.getMemoId()+"')";
                    String onClickRt = "SetCalculation('"+pktIdn+"','RT','"+pkt.getMemoId()+"')";
                    String rmkTxt =  "value(rmk_" + pktIdn + ")" ;
                    pktPrpMap = new HashMap();
                    pktPrpMap.put("Memo Id",util.nvl(pkt.getMemoId()));pktPrpMap.put("Packet Code",util.nvl(pkt.getVnm()));
                    pktPrpMap.put("Quot",util.nvl(pkt.getMemoQuot()));pktPrpMap.put("ByrDis",util.nvl(pkt.getByrDis()));
                    pktPrpMap.put("Prc / Crt",util.nvl(pkt.getRte()));pktPrpMap.put("Dis",util.nvl(pkt.getDis()));
                    String jandtlstt = pkt.getStt();
                    boolean disstt=false;
                    if(jandtlstt.equals("AV"))
                    disstt=true;
             %>
                <td><html:radio property="<%=sttPrp%>" disabled="<%=disstt%>" styleId="<%=rdCSId%>" onclick="calculationConsignmentNew('SL_')"  value="CS"  /></td>
                <td><html:radio property="<%=sttPrp%>" disabled="<%=disstt%>" styleId="<%=rdSLId%>" onclick="calculationConsignmentNew('SL_')"  value="SL"/></td>
                <td nowrap="nowrap"><html:radio property="<%=sttPrp%>" styleId="<%=rdRTId%>" onclick="calculationConsignmentNew('SL_')" value="RT"/>   <html:text property="<%=rmkTxt%>" size="10"  /></td>
            <%
            pageList= ((ArrayList)pageDtl.get("RADIOBODY") == null)?new ArrayList():(ArrayList)pageDtl.get("RADIOBODY");
            if(pageList!=null && pageList.size() >0){
            for(int j=0;j<pageList.size();j++){
            pageDtlMap=(HashMap)pageList.get(j);
            form_nme=(String)pageDtlMap.get("form_nme");
            fld_nme="value("+(String)pageDtlMap.get("fld_nme")+""+pktIdn+")";
            fld_ttl=(String)pageDtlMap.get("fld_ttl");
            fld_typ=(String)pageDtlMap.get("fld_typ")+""+count;
            dflt_val=(String)pageDtlMap.get("dflt_val");
            val_cond=(String)pageDtlMap.get("val_cond"); %>
        
            <td><html:radio property="<%=fld_nme%>"  
            styleId="<%=fld_typ%>" value="<%=String.valueOf(pktIdn)%>" onclick="<%=val_cond%>"  /></td>
            <%}}%>
                <td><%=pkt.getMemoId()%><input type="hidden" id="<%=count%>_memoid" value="<%=pkt.getMemoId()%>" /></td>
                <td nowrap="nowrap"><%=pkt.getValue("dte")%></td>
                 <td><%=pkt.getVnm()%><input type="hidden" id="<%=count%>_vnm" value="<%=pktIdn%>" /></td>
                 <logic:equal property="value(fnlFields)"  name="consignmentSaleForm"  value="Y" >
                <td><b><%=util.nvl(pkt.getValue("fnlprc"))%></b></td>
                <td><b><%=util.nvl(pkt.getValue("fnldis"))%></b></td> 
                 </logic:equal>
                <td><%=pkt.getMemoQuot()%></td>   
                <td><%=pkt.getByrDis()%></td>
                <td><%=pkt.getRte()%> <input type="hidden" id="<%=count%>_quot" value="<%=pkt.getRte()%>" /> 
                <input type="hidden" id="<%=count%>_fnl" value="<%=pkt.getMemoQuot()%>" /> </td>
                <td><%=pkt.getDis()%></td>
                <%for(int j=0; j < prps.size(); j++) {
                  String lprp = (String)prps.get(j);
                  if(prpDspBlocked.contains(lprp)){
                  }else{
                %>
                    <td><%=util.nvl(pkt.getValue((String)prps.get(j)))%>
                    <%
                    pktPrpMap.put((String)prps.get(j),util.nvl(pkt.getValue((String)prps.get(j))));
                    }if(lprp.equals("CRTWT")){%>
                    <input type="hidden" id="<%=count%>_cts" value="<%=util.nvl(pkt.getValue((String)prps.get(j)))%>" /> </td>
                    <%}%>
                <%}
                %>
                <td><%=util.nvl(pkt.getValue("stkstt"))%></td>
                <td><%=pkt.getRapRte()%> <input type="hidden" id="<%=count%>_rap" value="<%=pkt.getRapRte()%>" /></td>
                <td><%=util.nvl(pkt.getValue("rapVlu"))%></td>               
                </tr>
              <%  
                pktPrpMap.put("Live Status",util.nvl(pkt.getValue("stkstt")));
                pktPrpMap.put("RapRte",util.nvl(pkt.getRapRte()));
                pktPrpMap.put("RapVlu",util.nvl(pkt.getValue("rapVlu")));
                 pktList.add(pktPrpMap);
                }
                session.setAttribute("pktList", pktList);
                session.setAttribute("itemHdr", itemHdr);
            %>
              <input type="hidden" id="rdCount" value="<%=count%>" />
            </table></td></tr>
            </table>
        </html:form>
    <%}
    %></td></tr>
    <tr>
    <td><jsp:include page="/footer.jsp" /> </td>
    </tr>
    </table>
    <%@include file="../calendar.jsp"%>
   
  </body>
</html>