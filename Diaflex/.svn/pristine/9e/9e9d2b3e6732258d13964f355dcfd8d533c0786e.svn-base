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
    <title>Post Buy Back</title>
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
String cnt = (String)dbinfo.get("CNT");
  String repPath = (String)dbinfo.get("REP_PATH");
int accessidn=Integer.parseInt(util.nvl((String)request.getAttribute("accessidn"),"0"));
 String view = util.nvl((String)request.getAttribute("view"));
        String logId=String.valueOf(info.getLogId());
        String onfocus="cook('"+logId+"')";
        HashMap allPageDtl= (info.getPageDetails() == null)?new HashMap():(HashMap)info.getPageDetails();
        HashMap pageDtl=(HashMap)allPageDtl.get("BUYBACK_SALE");
     ArrayList prpDspBlocked = info.getPageblockList();
    ArrayList pageList=new ArrayList();
    HashMap pageDtlMap=new HashMap();
    String fld_nme="",fld_ttl="",val_cond="",dflt_val="",fld_typ="",form_nme="",lov_qry="";
        %>
 <body onfocus="<%=onfocus%>" onload="MM_preloadImages('images/faunalogo.png')" topmargin="0" leftmargin="0" rightmargin="0" bottommargin="0">

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
<div id="backgroundPopup"></div>

<div id="popupContactSave" align="center" >
<img src="../images/loading_flame.gif" height="150px" width="150px" border="0" />
</div>
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
  <img src="../images/line.jpg" border="0" /></td><td valign="middle"> <span class="pgHed">Post Buy Back</span> </td>
</tr></table>
  </td>
  </tr>
  <% if(request.getAttribute("msg")!=null){%>
        <tr><td height="15"  valign="top" class="tdLayout"><span class="redLabel"> <%=request.getAttribute("msg")%></span></td></tr>
       <% }
        %>
 <%if(request.getAttribute("saleId")!=null){%>
<tr><td valign="top" class="tdLayout">
 <%if(cnt.equals("xljf")){
        String url1 =repPath+"/reports/rwservlet?"+cnt+"&report="+info.getRportUrl()+"\\reports\\HK_Performa_postbyback.jsp&p_access="+accessidn+"&p_sal_idn="+request.getAttribute("saleId")+"&destype=CACHE&desformat=pdf";
        String url2 =repPath+"/reports/rwservlet?"+cnt+"&report="+info.getRportUrl()+"\\reports\\ind_Performa_postbyback.jsp&p_access="+accessidn+"&p_sal_idn="+request.getAttribute("saleId")+"&destype=CACHE&desformat=pdf";
         String url3 =repPath+"/reports/rwservlet?"+cnt+"&report="+info.getRportUrl()+"\\reports\\confirm_byback.jsp&p_access="+accessidn+"&p_idn="+request.getAttribute("saleId")+"&destype=CACHE&desformat=pdf";
        String url4 =repPath+"/reports/rwservlet?"+cnt+"&report="+info.getRportUrl()+"\\reports\\BBINV_report.jsp&p_access="+accessidn+"&p_sal_idn="+request.getAttribute("saleId")+"&destype=CACHE&desformat=pdf";
           %>
           <table><tr>
          <td><a href="<%=url2%>"  target="_blank"><span class="txtLink" >India Invoice sale post</span></a></td>
        <td><a href="<%=url1%>"  target="_blank"><span class="txtLink" >HK Invoice Sale post</span></a></td>
         <td><a href="<%=url3%>"  target="_blank"><span class="txtLink" >Confirm Buyback Page</span></a></td>
        <td><a href="<%=url4%>"  target="_blank"><span class="txtLink" >Buyback invoice report</span></a></td></tr></table>
<%}%>
</td></tr>
<%}%>
   <tr><td valign="top" class="hedPg">
   
    <table><tr><td valign="top">
 
    <html:form action="/marketing/postBuyBack.do?method=load" method="POST">
       <table><tr><td valign="top">
        <table class="grid1" >
       
        <tr><th colspan="2">Memo Search </th></tr>
          
            
             <tr>
           <td colspan="2"> <html:radio property="value(memoSrch)" value="ByrSrch"  onclick="DisplayMemoSrch('MS_1')"  styleId="MS_1" />  Memos Search By Buyer </td>
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
             
             <html:select property="value(byrTrm)"  styleId="rlnId" onchange="GetMemoIdn('BUY')" >
            <html:option value="0">---select---</html:option>
            <html:optionsCollection property="trmsLst" name="postBuyBackForm"
            label="trmDtl" value="relId" />
            </html:select>
            </td>
            </tr>
      
                </table>
                
               </td> 
            </tr>
            <tr>
           <td colspan="2"> <html:radio property="value(memoSrch)" value="MemoSrch"    styleId="MS_2" onclick="DisplayMemoSrch('MS_2')"  /> Memos Search By Memo Ids/Packet Code</td>
           </tr>
            <tr style="display:none" id="DMS_2">
            <td>
            <table>
            <tr>
            <td>Memo Ids</td><td><html:text property="memoIdn" name="postBuyBackForm" styleId="memoid"/></td></tr>
           <tr><td>Packets. </td><td>
            <html:textarea property="value(vnmLst)" name="postBuyBackForm" styleId="vnmLst" /> </td> </tr>
           </table>
            </td>
            </tr>
          <tr><td colspan="2" align="left">
            <table><tr><td>Type</td>
            <td>
          <html:select property="value(buytyp)" name="postBuyBackForm">
          <html:option value="ALL">ALL</html:option>
          <html:option value="WFR">Waiting For Return</html:option>
          <html:option value="AP">Approve</html:option>
          </html:select>
          </td></tr>
          </table>
          </td>
          </tr>
           <tr><td align="center"><html:submit property="submit" value="View" styleClass="submit" onclick="return validatememosale()" /></td></tr>
   
        </table>
      
   </td><td valign="top">
        <div id="memoIdn"></div>
        </td></tr></table>
     
    </html:form>
    </td>
     <%if(request.getAttribute("view")!=null && view.equals("Y")){%>
     <td  valign="top">
     <table class="grid1">
    <tr> <td></td><td><b>Selected(AP)</b></td><td><b>Selected(WFR)</b></td><td><b>Selected(SL)</b></td><td><b>Selected(RT)</b></td> </tr>
    <tr> <td><b>Qty</b></td> <td><span id="ap_qty"><bean:write property="value(ap_qty)" name="postBuyBackForm" /></span></td><td><span id="wfr_qty"><bean:write property="value(wfr_qty)" name="postBuyBackForm" /></span></td><td><span id="qty">0</span></td><td><span id="rt_qty">0</span></td> </tr>
    <tr> <td><b>Cts</b> </td> <td><span id="ap_cts"><bean:write property="value(ap_cts)"  name="postBuyBackForm" /></span></td> <td><span id="wfr_cts"><bean:write property="value(wfr_cts)"  name="postBuyBackForm" /></span></td><td><span id="cts">0</span></td><td><span id="rt_cts">0</span></td>  </tr>
    <tr> <td><b>Avg Prc</b></td> <td><span id="ap_avgPrc"><bean:write property="value(ap_avgPrc)"  name="postBuyBackForm" /></span></td><td><span id="wfr_avgPrc"><bean:write property="value(wfr_avgPrc)"  name="postBuyBackForm" /></span></td><td><span id="avgPrc">0</span></td> <td><span id="rt_avgPrc">0</span></td>  </tr>
    <tr> <td><b>Avg Dis</b> </td> <td><span id="ap_avgDis"><bean:write property="value(ap_avgDis)"  name="postBuyBackForm" /></span></td> <td><span id="wfr_avgDis"><bean:write property="value(wfr_avgDis)"  name="postBuyBackForm" /></span></td><td><span id="avgDis">0</span></td><td><span id="rt_avgDis">0</span></td>  </tr>
    <tr> <td><b>Value </b></td> <td><span id="ap_vlu"><bean:write property="value(ap_vlu)"   name="postBuyBackForm" /></span></td><td><span id="wfr_vlu"><bean:write property="value(wfr_vlu)"   name="postBuyBackForm" /></span></td><td><span id="vlu">0</span></td><td><span id="rt_vlu">0</span></td>   </tr>
   <tr><td><b>Rebate</b></td><td colspan="4">Total Rebate : <span id="REBATE_dis">0</span> , Grand Total= <span id="REBATEnet_dis">0</span></td></tr>
  <!--<tr><td><b>Buyer</b></td><td colspan="4"><bean:write property="byr" name="postBuyBackForm" /></td></tr>-->
   <tr><td><b>Date</b></td><td colspan="4"><bean:write property="dte" name="postBuyBackForm" /></td></tr>
   <tr><td><b>Type</b></td><td colspan="4"><bean:write property="typ" name="postBuyBackForm" /></td></tr>
      </table>
      </td>
      <%ArrayList transChrList = (ArrayList)request.getAttribute("TrnsCharge");
      if(transChrList!=null && transChrList.size()>0){
      String pmemoId="";
      %>
       <td valign="top">
       <table class="grid1">
       <tr><td colspan="3">Memo Charges</td></tr>
       <%for(int i=0;i<transChrList.size();i++){
       HashMap transDtl = (HashMap)transChrList.get(i);
       String memoId = (String)transDtl.get("MEMO");
       String dsc = (String)transDtl.get("DSC");
       String chg = (String)transDtl.get("CHG");
       String dMemoId = memoId;
        
        if(pmemoId.equals(dMemoId))
        dMemoId="";
       
        pmemoId=memoId;
       %>
       <tr><td><%=dMemoId%></td><td><%=dsc%></td><td><%=chg%></td></tr>
       <%}%>
       </table>
       </td>
      <%}%>
    <td valign="top">
    <table class="grid1">
    <tr><td colspan="3" align="center"><b>Buyer Details</b></td></tr>
    <tr>
    <td><b>Buyer</b></td><td><bean:write property="byr" name="postBuyBackForm" /></td>
    </tr>
    <tr>
    <td><b>Email</b></td><td><bean:write property="value(email)" name="postBuyBackForm" /></td>
    </tr>
        <tr>
    <td><b>Mobile No</b></td><td><bean:write property="value(mobile)" name="postBuyBackForm" /></td>
    </tr>
        <tr>
    <td><b>Office No</b></td><td><bean:write property="value(office)" name="postBuyBackForm" /></td>
    </tr>
    </table>
    </td>
    <%}%>

        <td  valign="top">
     <!--<%if(request.getAttribute("view")!=null && view.equals("Y")){
     ArrayList chargesLst=(ArrayList)session.getAttribute("chargesLst");
     
     if(chargesLst!=null && chargesLst.size()>0){%>
    <table class="grid1">
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
    String fieldId = typ+"_rmk";
    if(flg.equals("MNL")){
    %>
    <tr><td><b><%=dsc%></b></td>
    <td><b><span id="<%=typ%>_dis"></span></b></td>
    <td><html:text property="<%=field%>" size="6" styleId="<%=typ%>" onchange="<%=onchang%>" name="postBuyBackForm"/></td>
    <td>
    <%if(rmk.equals("Y")){%>
    Remark<html:text property="<%=fieldRmk%>" styleId="<%=fieldId%>" onchange="<%=onchangrmk%>" name="postBuyBackForm" size="10"/>
    <%}%>
    </td>
    </tr>
    <%}else{%>
    <tr><td><b><%=dsc%></b>
    <%if(flg.equals("AUTO") && autoopt.equals("OPT")){%>
    <input type="checkbox" name="<%=typ%>_AUTO" id="<%=typ%>_AUTO" onchange="validateAutoOpt('<%=typ%>_AUTO')" title="Auto Optional"/>
    <%}%>
    </td>
    <td><b><span id="<%=typ%>_dis"></span></b></td>
    <td>
    <html:hidden property="<%=field%>"  styleId="<%=typ%>" name="postBuyBackForm"/>
    <input type="button" name="charge_<%=typ%>" id="charge_<%=typ%>" value="Charge"  onclick="<%=onchang%>" class="submit" />
    </td><td></td></tr>
    <%}}%>
    <tr><td><b>Total</b></td><td><b><span id="net_dis"></span></b></td><td></td><td></td></tr>
    </table>
    <%}}%>-->
    </td>
    </tr></table>
  
 </td></tr>
 <tr><td class="tdLayout" valign="top">
   <%
    if(view.equals("Y")) {
        String formAction = "/marketing/postBuyBack.do?method=save"; 
     
    ArrayList itemHdr = new ArrayList();
    ArrayList pktList = new ArrayList();
    HashMap pktPrpMap = new HashMap();
    %>
        <html:form action="<%=formAction%>" method="POST" >
    <!--<%if(request.getAttribute("view")!=null && view.equals("Y")){
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
    <html:hidden property="<%=field%>" name="postBuyBackForm" styleId="<%=fieldId%>"  />
   <%if(rmk.equals("Y")){%>
    <html:hidden property="<%=fieldRmk%>" name="postBuyBackForm" styleId="<%=fieldIdrmk%>"  />
    <%}
    if(flg.equals("AUTO") && autoopt.equals("OPT")){%>
    <html:hidden property="<%=fieldauto%>" name="postBuyBackForm" styleId="<%=fieldIdauto%>" value="N"  />
    <%}}%>
   <html:hidden property="value(vluamt)" name="postBuyBackForm" styleId="vluamt"  />
    <%}}
    %>-->
    <html:hidden property="value(REBATE_save)" name="postBuyBackForm" styleId="REBATE_save"  />
    <html:hidden property="value(REBATEvluamt)" name="postBuyBackForm" styleId="REBATEvluamt"  />
        <div id="popupContactSale" >

<table align="center" cellpadding="0" cellspacing="0" >
<tr><td>Do You Want To Confrim For delivery?</td></tr>
<tr><td><table><tr><td><html:radio property="value(isDLV)"   value="Yes"/>YES </td>
<td> <html:radio property="value(isDLV)" value="No"/>NO </td></tr></table> </td> </tr>
<tr><td><html:submit property="value(submit)" onclick="return confirmChangesWithPopup()"  value="Submit" styleClass="submit"/> </td> </tr>
</table>
</div>
          <html:hidden property="relIdn" name="postBuyBackForm" />
          <html:hidden property="nmeIdn" name="postBuyBackForm" styleId="nmeIdn"  />
          <table>
          
            <tr><td>
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
           <!-- <html:submit property="submit" value="Save" onclick="return confirmChangesSL()" styleClass="submit"/>&nbsp;
                <html:submit property="value(save)" value="Save" onclick="return confirmChangesSL()" styleClass="submit"/>&nbsp;
              
                 <html:button property="value(perInv)" onclick="GenPerformInv('SL')" value="Perform Invice" styleClass="submit"/>&nbsp;
                 <html:button property="value(loalty)" onclick="loyaltyDis()" value="Loyalty Discount" styleClass="submit"/>&nbsp;-->
            </td></tr>
            <tr><td>
            
            <label class="pgTtl">Memo Packets</label>
            Create Excel <img src="../images/ico_file_excel.png" onclick="goTo('<%=info.getReqUrl()%>/marketing/postBuyBack.do?method=createXL','','')" />
            Mail Excel <img src="../images/ico_file_excel.png" onclick="newWindow('<%=info.getReqUrl()%>/marketing/postBuyBack.do?method=createXL&mail=Y&nmeIdn=<%=util.nvl((String)request.getAttribute("NMEIDN"))%>')" />
            <%
                ArrayList prps = (info.getValue("MDL_MEMO_RTRN") == null)?new ArrayList():(ArrayList)info.getValue("MDL_MEMO_RTRN");
             itemHdr.add("Memo Id");itemHdr.add("Packet Code");
             %>  </td></tr>
             <tr><td>
            <table class="grid1">
                <tr>
                    <th>Sr</th>
                    <th>Memo Id</th>
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
                    <!--<th><html:radio property="value(slRd)"  styleId="apRd"  onclick="ALLRedio('apRd' ,'AP_');calculationNew('SL_')"  value="AP"/>&nbsp;App</th>
                    <th><html:radio property="value(slRd)" styleId="slRd"  onclick="ALLRedio('slRd' ,'SL_');calculationNew('SL_')"  value="SL"/>&nbsp;Sale</th>
                    <th><html:radio property="value(slRd)" styleId="rtRd" onclick="ALLRedio('rtRd' ,'RT_');calculationNew('SL_')"  value="RT"/> Return</th>
                    <th> <html:radio property="value(slRd)" styleId="invRd"  onclick="ALLRedio('invRd' ,'INV_');calculationNew('SL_')"  value="INV"/>&nbsp;Performa Inv </th>-->
            <%pageList=(ArrayList)pageDtl.get("RADIOHDR");
            if(pageList!=null && pageList.size() >0){
            for(int j=0;j<pageList.size();j++){
            pageDtlMap=(HashMap)pageList.get(j);
            fld_nme="value("+(String)pageDtlMap.get("fld_nme")+")";
            fld_ttl=(String)pageDtlMap.get("fld_ttl");
            fld_typ=(String)pageDtlMap.get("fld_typ");
            dflt_val=(String)pageDtlMap.get("dflt_val");
            val_cond=(String)pageDtlMap.get("val_cond"); %>
            <th><html:radio property="<%=fld_nme%>" styleId="<%=fld_typ%>"  onclick="<%=val_cond%>"  value="<%=dflt_val%>"/><%=fld_ttl%> </th>
            <%}}%>
                </tr>
            <%
            itemHdr.add("RapRte");itemHdr.add("RapVlu");itemHdr.add("Dis");itemHdr.add("Prc / Crt");itemHdr.add("ByrDis");itemHdr.add("Quot");
             int count =0 ;
             String memoIdconQ="";
              PktDtl pkt=null;
                ArrayList pkts = (info.getValue("PKTS") == null)?new ArrayList():(ArrayList)info.getValue("PKTS");
                for(int i=0; i < pkts.size(); i++) {%>
                <tr>
                <td><%=(i+1)%></td>
                <%  count=i+1;
                     pkt = (PktDtl)pkts.get(i);
                    long pktIdn = pkt.getPktIdn();
                    String cbPrp = "value(upd_" + pktIdn + ")";
                    String sttPrp = "value(stt_" + pktIdn + ")" ;
                    String rdISId = "AP_"+count;
                     String rdSLId = "SL_"+count;
                    String rdRTId = "RT_"+count;
                    String rdInvId = "INV_"+count;
                    String onClickAp = "SetCalculation('"+pktIdn+"','AP','"+pkt.getMemoId()+"')";
                    String onClickSl = "SetCalculation('"+pktIdn+"','SL','"+pkt.getMemoId()+"')";
                    String onClickRt = "SetCalculation('"+pktIdn+"','RT','"+pkt.getMemoId()+"')";
                    String rmkTxt =  "value(rmk_" + pktIdn + ")" ;
                    pktPrpMap = new HashMap();
                    pktPrpMap.put("Memo Id",util.nvl(pkt.getMemoId()));pktPrpMap.put("Packet Code",util.nvl(pkt.getVnm()));
                    memoIdconQ+=","+util.nvl(pkt.getMemoId());
             %>
                 <td><%=pkt.getMemoId()%><input type="hidden" id="<%=count%>_memoid" value="<%=pkt.getMemoId()%>" /></td>
                 <td><%=pkt.getVnm()%><input type="hidden" id="<%=count%>_vnm" value="<%=pktIdn%>" /></td>
              
                <%for(int j=0; j < prps.size(); j++) {
                  String lprp = (String)prps.get(j);
                  if(prpDspBlocked.contains(lprp)){
                  }else{
                  pktPrpMap.put(lprp,util.nvl(pkt.getValue((String)prps.get(j))));
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
                <!--<td><html:radio property="<%=sttPrp%>" styleId="<%=rdISId%>" onclick="calculationNew('SL_')"  value="AP"/></td>
                <td><html:radio property="<%=sttPrp%>" styleId="<%=rdSLId%>" onclick="calculationNew('SL_')"  value="SL"/></td>
                <td nowrap="nowrap"><html:radio property="<%=sttPrp%>" styleId="<%=rdRTId%>" onclick="calculationNew('SL_')" value="RT"/>   <html:text property="<%=rmkTxt%>" size="10"  /></td>
                <td><html:radio property="<%=sttPrp%>" styleId="<%=rdInvId%>"  value="<%=String.valueOf(pktIdn)%>"/></td>-->               
                <%pageList=(ArrayList)pageDtl.get("RADIOBODY");
            if(pageList!=null && pageList.size() >0){
            for(int j=0;j<pageList.size();j++){
            pageDtlMap=(HashMap)pageList.get(j);
            fld_nme="value("+(String)pageDtlMap.get("fld_nme")+""+pktIdn+")";
            fld_ttl=(String)pageDtlMap.get("fld_ttl");
            fld_typ=(String)pageDtlMap.get("fld_typ")+count;
            dflt_val=(String)pageDtlMap.get("dflt_val");
            if(dflt_val.equals("PKT"))
            dflt_val=String.valueOf(pktIdn);
            dflt_val=dflt_val.replaceAll("PKTIDN",String.valueOf(pktIdn));
            val_cond=(String)pageDtlMap.get("val_cond"); %>
            <td nowrap="nowrap"><html:radio property="<%=fld_nme%>" styleId="<%=fld_typ%>"  onclick="<%=val_cond%>"  value="<%=dflt_val%>"/>
            <%if(dflt_val.equals("RT")){%>
            <html:text property="<%=rmkTxt%>" size="10"  />
            <%}%>
            </td>
            <%}}%>
                </tr>
              <% pktPrpMap.put("RapVlu",util.nvl((String)pkt.getValue("rapVlu")));  
                pktPrpMap.put("RapRte",util.nvl((String)pkt.getRapRte()));pktPrpMap.put("Dis",util.nvl((String)pkt.getDis()));pktPrpMap.put("Prc / Crt",util.nvl((String)pkt.getRte()));pktPrpMap.put("ByrDis",util.nvl((String)pkt.getByrDis()));pktPrpMap.put("Quot",util.nvl((String)pkt.getMemoQuot()));
                pktList.add(pktPrpMap);
                }
                session.setAttribute("pktList", pktList);
                session.setAttribute("itemHdr", itemHdr);
                memoIdconQ=memoIdconQ.replaceFirst("\\,", "");
            %>
            <html:hidden property="value(memoIdn)" name="postBuyBackForm" styleId="memoIdnlst" value="<%=memoIdconQ%>"  />
              <input type="hidden" id="rdCount" value="<%=count%>" />
            </table></td></tr>
            </table>
        </html:form>
    <%}%>
 
 </td></tr>
    <tr>
    <td><jsp:include page="/footer.jsp" /> </td>
    </tr>
 </table> 
  
  </body>
</html>