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
 String view = util.nvl((String)request.getAttribute("view"));
int accessidn=Integer.parseInt(util.nvl((String)request.getAttribute("accessidn"),"0"));
        String logId=String.valueOf(info.getLogId());
        String onfocus="cook('"+logId+"')";
    HashMap allPageDtl= (info.getPageDetails() == null)?new HashMap():(HashMap)info.getPageDetails();
    HashMap pageDtl=(HashMap)allPageDtl.get("MEMO_SALE");
    ArrayList prpDspBlocked = info.getPageblockList();
    ArrayList pageList=new ArrayList();
    HashMap pageDtlMap=new HashMap();
    String fld_nme="",fld_ttl="",val_cond="",dflt_val="",fld_typ="",form_nme="",lov_qry="";
    HashMap prpList = info.getPrp();
    HashMap mprpList = info.getMprp();
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
  <img src="../images/line.jpg" border="0" /></td><td valign="middle"> <span class="pgHed">Memo Sales</span> </td>
</tr></table>
  </td>
  </tr>
   <tr><td valign="top" class="hedPg">
   <table cellpadding="2" cellspacing="2" >
   <%
        if(request.getAttribute("error")!=null){%>
        <tr><td height="15"><span class="redLabel"> <%=request.getAttribute("error")%></span></td></tr>
       <% }
        %>
        <% if(request.getAttribute("RTMSG")!=null){%>
        <tr><td height="15"><span class="redLabel"> <%=request.getAttribute("RTMSG")%></span></td></tr>
       <% }
        %>
        <% if(request.getAttribute("SLMSG")!=null){
         String url =repPath+"/reports/rwservlet?"+cnt+"&report="+info.getRportUrl()+"\\reports\\sale_dlv_rep.jsp&p_access="+accessidn+"&p_sal_idn="+request.getAttribute("saleId")+"&destype=CACHE&desformat=pdf";
        %>
        <tr><td>
        <input type="hidden" name="rptUrl" id="rptUrl" value="<%=url%>" />
        <span class="redLabel"> <%=request.getAttribute("SLMSG")%></span></td>
        </tr>
      
        <tr><td>
        <table><tr><td>
        <div onclick="displayDiv('refDiv')"> <a href="<%=url%>"  target="_blank"><span class="txtLink" >Click Here for Report</span></a> </div></td>
        <%if(cnt.equals("xljf")){
        String url1 =repPath+"/reports/rwservlet?"+cnt+"&report="+info.getRportUrl()+"\\reports\\inv_report.jsp&p_access="+accessidn+"&p_sal_idn="+request.getAttribute("saleId")+"&destype=CACHE&desformat=pdf";
        String url2 =repPath+"/reports/rwservlet?"+cnt+"&report="+info.getRportUrl()+"\\reports\\Ind_Export_invoice_sale.jsp&p_access="+accessidn+"&p_sal_idn="+request.getAttribute("saleId")+"&destype=CACHE&desformat=pdf";
        String url3 =repPath+"/reports/rwservlet?"+cnt+"&report="+info.getRportUrl()+"\\reports\\Hk_Export_invoice_sale.jsp&p_access="+accessidn+"&p_sal_idn="+request.getAttribute("saleId")+"&destype=CACHE&desformat=pdf";
        String url4 =repPath+"/reports/rwservlet?"+cnt+"&report="+info.getRportUrl()+"\\reports\\Ind_performa_invoice_sale.jsp&p_access="+accessidn+"&p_sal_idn="+request.getAttribute("saleId")+"&destype=CACHE&desformat=pdf";
        String url5 =repPath+"/reports/rwservlet?"+cnt+"&report="+info.getRportUrl()+"\\reports\\HK_performa_invoice_sale.jsp&p_access="+accessidn+"&p_sal_idn="+request.getAttribute("saleId")+"&destype=CACHE&desformat=pdf";
        %>
        <td><a href="<%=url1%>"  target="_blank"><span class="txtLink" >Print Report</span></a></td>
        <td><a href="<%=url2%>"  target="_blank"><span class="txtLink" >India Invoice sale post</span></a></td>
        <td><a href="<%=url3%>"  target="_blank"><span class="txtLink" >HK Invoice Sale post</span></a></td>
        <td><a href="<%=url4%>"  target="_blank"><span class="txtLink" >India_Proforma Invoice</span></a></td>
        <td><a href="<%=url5%>"  target="_blank"><span class="txtLink" >HK Proforma</span></a></td>
        
        <%}%>
        </tr></table>
        </td>
        </tr>
        <%}
       String performaLink=(String)request.getAttribute("performaLink");
       System.out.println(performaLink);
        if(performaLink!=null){
        String url=info.getReqUrl()+"/marketing/performa.do?method=perInv&idn="+request.getAttribute("saleId")+"&form=Y&typ=SL&pktTyp=SINGLE";%>
        <tr><td height="15"  valign="top" class="tdLayout"><a href="<%=url%>"  target="_blank"><span class="txtLink" >Performa Invoice</span></a></td></tr>
       <% }%>
        </table></td></tr>
  <tr><td valign="top" class="tdLayout">
  <table><tr><td  valign="top">
  
  <bean:parameter id="reqIdn" name="memoIdn" value="0"/>
  <%
    String pgTtl = " Memo Sales" ;
   
    if(reqIdn=="0")
    reqIdn = request.getParameter("memoId");
  
    if(view.equals("Y")){
        pgTtl += " Id : " + reqIdn ;
    }
  %>
 
    <html:form action="/marketing/memoSaleSH.do?method=load" method="POST">
    <table><tr><td valign="top">
        <table class="grid1" >
       
        <tr><th colspan="2">Memo Search </th></tr>
          
            
             <tr>
           <td colspan="2"> <html:radio property="value(memoSrch)" value="ByrSrch"  onclick="DisplayMemoSrch('MS_1')"  styleId="MS_1" />  Memos Search By Buyer </td>
           </tr>
             <tr style="display:none" id="DMS_1"><td colspan="2">
             
              <table cellpadding="5">
              <%
              pageList= ((ArrayList)pageDtl.get("USRLOC") == null)?new ArrayList():(ArrayList)pageDtl.get("USRLOC");     
              if(pageList!=null && pageList.size() >0){%>
              
                <tr>
                <td>User Location</td>
                <td><html:select property="value(usrLct)" onchange="userLocation(this,'SALE')" name="memoSaleForm" >
                <html:option value="0">----select----</html:option>
                <html:option value="IND">INDIA</html:option><html:option value="HK">HK</html:option>
                </html:select>
                </td></tr>
              <%}%>
              <tr>
                <td>Buyer</td>
                <td>
                <html:select property="value(byrIdn)" styleId="byrId" onchange="getTrms(this,'SALE')" >
                <html:option value="0">---select---</html:option>
                <html:optionsCollection property="byrLstFetch"  value="byrIdn" label="byrNme" />
                </html:select>
                </td> </tr>
                 <tr>
            <td> <span class="txtBold" >Terms </span></td><td>
             
             <html:select property="value(byrTrm)"  styleId="rlnId" onchange="GetMemoIdn('SALE')" >
            <html:option value="0">---select---</html:option>
            <html:optionsCollection property="trmsLst" name="memoSaleForm"
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
            <td>Memo Ids</td><td><html:text property="memoIdn" name="memoSaleForm" styleId="memoid"/></td></tr>
           <tr><td>Packets. </td><td>
            <html:textarea property="value(vnmLst)" name="memoSaleForm" styleId="vnmLst" /> </td> </tr>
           </table>
            </td>
            </tr>
            <tr><td align="center"><html:submit property="submit" value="View" styleClass="submit" onclick="return validatememosale()" /></td></tr>
        </table></td><td valign="top">
        <div id="memoIdn"></div>
        </td></tr></table>
        
    </html:form>
    </td><td  valign="top">
     <%if(request.getAttribute("view")!=null && view.equals("Y")){%>
    <table class="grid1">
    <tr> <td></td> <td><b> Total</b></td><td><b>Selected</b></td> </tr>
    <tr> <td><b>Qty</b></td> <td><bean:write property="qty" name="memoSaleForm" /></td><td><span id="qty">0</span></td> </tr>
    <tr> <td><b>Cts</b> </td> <td><bean:write property="cts" name="memoSaleForm" /></td><td><span id="cts">0</span></td>  </tr>
    <tr> <td><b>Avg Prc</b></td> <td><bean:write property="avgPrc" name="memoSaleForm" /></td><td><span id="avgprc">0</span></td>  </tr>
    <tr> <td><b>Avg Dis</b> </td> <td><bean:write property="avgDis" name="memoSaleForm" /></td><td><span id="avgdis">0</span></td>  </tr>
    <tr> <td><b>Value </b></td> <td><bean:write property="vlu" name="memoSaleForm" /></td><td><span id="vlu">0</span></td>  </tr>
    <!--<tr> <td id="loyaltyHdr" style="display:none;"><b>Loyalty Dis</b></td> <td colspan="2"  id="loyaltyval" style="display:none;"><b><span id="loyalty">0</span></b></td>  </tr>-->
  
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
    String onchang="chargesSH('"+typ+"','"+i+"')";
    String onchangrmk="chargesmanualrmk('"+typ+"','"+i+"')";
    String chargesfixed="chargesfixedSH('"+typ+"');chargesSH('"+typ+"','"+i+"')";
    String fieldId = typ+"_rmk";
    if(flg.equals("MNL")){
    %>
    <tr>
    <td nowrap="nowrap"><b><%=dsc%></b></td>
    <td nowrap="nowrap"><b><span id="<%=typ%>_dis"></span></b></td>
    <td nowrap="nowrap"><html:text property="<%=field%>" size="6" styleId="<%=typ%>" onchange="<%=onchang%>" name="memoSaleForm"/></td>
    <td nowrap="nowrap">
    <%if(rmk.equals("Y")){%>
    Remark<html:text property="<%=fieldRmk%>" styleId="<%=fieldId%>" onchange="<%=onchangrmk%>" name="memoSaleForm" size="10"/>
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
    <html:hidden property="<%=field%>"  styleId="<%=typ%>" name="memoSaleForm"/>
    <!--<input type="button" name="charge_<%=typ%>" id="charge_<%=typ%>" value="Charge"  onclick="<%=onchang%>" class="submit" />-->
    <%}else{
    ArrayList dataLst=new ArrayList();
    ArrayList data=new ArrayList();
    dataLst=(ArrayList)fisalcharges.get(typ);
    %>
    <html:select property="<%=field%>" styleId="<%=typ%>" name="memoSaleForm" onchange="<%=chargesfixed%>">
    <html:option value="0" >Select</html:option>
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
        String formAction = "/marketing/memoSaleSH.do?method=save&memoIdn="+ reqIdn ; 
        
           String aadatcommdisplay="display:none",brk1commdisplay="display:none",brk2commdisplay="display:none",brk3commdisplay="display:none",brk4commdisplay="display:none";
    ArrayList itemHdr = new ArrayList();
    ArrayList pktList = new ArrayList();
    HashMap pktPrpMap = new HashMap();
    %>
        <html:form action="<%=formAction%>" method="POST"  >
        <html:hidden property="value(noofmemoid)" name="memoSaleForm" styleId="noofmemoid"  />
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
    <html:hidden property="<%=field%>" name="memoSaleForm" styleId="<%=fieldId%>" value="<%=fieldidval%>"  />
   <%if(rmk.equals("Y")){%>
    <html:hidden property="<%=fieldRmk%>" name="memoSaleForm" styleId="<%=fieldIdrmk%>"  />
    <%}
    if((flg.equals("AUTO") && autoopt.equals("OPT")) || (flg.equals("FIX") && autoopt.equals("OPT"))){
    String chk="N";
    if((typ.contains("IMP_DUTY") || typ.contains("SHP")) && cnt.equalsIgnoreCase("xljf"))
    chk="Y";
    %>
    <html:hidden property="<%=fieldauto%>" name="memoSaleForm" styleId="<%=fieldIdauto%>" value="<%=chk%>"  />
    <%}}%>
   <html:hidden property="value(vluamt)" name="memoSaleForm" styleId="vluamt"  />
    <%}}
    %>
        <div id="popupContactSale" >

<table align="center" cellpadding="0" cellspacing="0" >
<tr><td>Do You Want To Confrim For delivery?</td></tr>
<tr><td><table><tr><td><html:radio property="value(isDLV)"   value="Yes"/>YES </td>
<td> <html:radio property="value(isDLV)" value="No"/>NO </td></tr></table> </td> </tr>
<tr><td><html:submit property="value(submit)" onclick="return confirmChangesWithPopup()"  value="Submit" styleClass="submit"/> </td> </tr>
</table>
</div>
        <html:hidden property="nmeIdn" name="memoSaleForm" styleId="nmeIdn"  />
          <html:hidden property="relIdn" name="memoSaleForm" />
          <html:hidden property="value(DLV_POPUP)" styleId="DLV_POPUP" name="memoSaleForm" />
         
          <table><tr><td>
          <table cellspacing="2">
          <tr><td> <span class="pgTtl" > Old Selection</span></td> <td><b>Buyer Name :</b></td><td> <bean:write property="byr" name="memoSaleForm"/> </td>
          <td><b>Email :</b></td> <td> <bean:write property="value(email)" name="memoSaleForm"/> </td>
          <td><b>Mobile No :</b></td> <td> <bean:write property="value(mobile)" name="memoSaleForm"/> </td>
          <td><b>Office No :</b></td> <td> <bean:write property="value(office)" name="memoSaleForm"/> </td>          
          </tr>
          </table></td></tr>
          <tr>
            <td><table><tr><td><span class="pgTtl" >Exchange Rate :</span> </td>
           <td>
           <logic:equal property="value(cur)"  name="memoSaleForm"  value="USD" >
           <html:text property="value(exhRte)" styleId="exhRte" readonly="true" size="5" name="memoSaleForm" />
           </logic:equal>
            <logic:notEqual property="value(cur)"  name="memoSaleForm"  value="USD" >
            <html:text property="value(exhRte)"  styleId="exhRte" size="5" name="memoSaleForm" />
            </logic:notEqual>
           </td>
           <logic:equal property="value(fnlexhRteDIS)"  name="memoSaleForm"  value="Y" >
           <td><span class="pgTtl" >Final Exchange Rate :</span> </td>
           <td>
            <html:text property="value(fnlexhRte)"  styleId="fnlexhRte" size="5" name="memoSaleForm" />
           </td>
           </logic:equal>
           <%ArrayList prpValLst = (ArrayList)prpList.get("SL_TYPV");
           if(prpValLst!=null && prpValLst.size()>0){
           %>
           <td>Sale Type:</td><td>
           <html:select property="value(sale_typ)" styleId="saleTyp" name="memoSaleForm" >
           <html:option value="">---select sale type---</html:option>
           <%for(int k=0;k<prpValLst.size();k++){ 
           String lprpVal = (String)prpValLst.get(k);
           %>
           <html:option value="<%=lprpVal%>"><%=lprpVal%></html:option>
           <%}%>
           </html:select>
           <input type="hidden" name="isSalTyp" id="isSalTyp" value="Y"  />
           </td><%}else{%><td><input type="hidden" name="isSalTyp" id="isSalTyp" value="N"  /></td><%}%>
            </tr></table></td></tr>
          <tr><td>
            <table>
                <tr>
               
                <td><span class="pgTtl" >buyer List</span></td>
                <td>
                <html:select property="byrIdn" name="memoSaleForm"  styleId="byrId1" onchange="GetADDR();GetBank()" >
               <html:option value="0" >---select---</html:option>
                <html:optionsCollection property="byrLst" name="memoSaleForm"  value="byrIdn" label="byrNme" />
                
                </html:select>
                </td> 
                
                <td><html:select property="value(addr)" styleId="addrId" name="memoSaleForm" >
                
                 <html:optionsCollection property="addrList" name="memoSaleForm"  value="idn" label="addr" />
                </html:select>
                
               </td>
              
                <td><span class="pgTtl" >Terms </span></td><td>
             
             <html:select property="value(byrTrm)" name="memoSaleForm"  styleId="rlnId1" onchange="invTermsDtls();"   >
         
             <html:optionsCollection property="trmsLst" name="memoSaleForm"
            label="trmDtl" value="relId" />
            
            </html:select>
            </td>
               <%
            pageList= ((ArrayList)pageDtl.get("DAYTERMS") == null)?new ArrayList():(ArrayList)pageDtl.get("DAYTERMS");  
            if(pageList!=null && pageList.size() >0){
            for(int j=0;j<pageList.size();j++){
            pageDtlMap=(HashMap)pageList.get(j);
            dflt_val=util.nvl((String)pageDtlMap.get("dflt_val"));
            if(dflt_val.equals("Y")){ %>
            <td><span class="pgTtl" >Day Terms </span></td>
            <td>
             <html:select property="value(byrDayTrm)" name="memoSaleForm"  styleId="byrDayTrm">
             <html:option value="0">-----select-----</html:option>
             <html:optionsCollection property="daytrmsLst" name="memoSaleForm"
            label="trmDtl" value="relId" />
            </html:select>
            </td>
            <%}}}%>
                </tr>
            </table></td></tr>
             <logic:present property="bankList" name="memoSaleForm" >
            <tr><td><table>
            <tr>
            <td nowrap="nowrap"><span class="pgTtl" >Company Name</span></td>
          <td>
           <html:select property="value(grpIdn)" name="memoSaleForm"  styleId="grpIdn"  >
             <html:optionsCollection property="groupList" name="memoSaleForm"
             value="idn" label="addr" />
            </html:select>
          </td>
                <td nowrap="nowrap"><span class="pgTtl" >Bank Selection</span></td>
               <td>
             
             <html:select property="value(bankIdn)" name="memoSaleForm" onchange="SetBankAddr(this)"  styleId="bankIdn"  >
             <html:optionsCollection property="bankList" name="memoSaleForm"
             value="idn" label="addr" />
            
            </html:select>
            </td>
            <td> <html:select property="value(bankAddr)"  name="memoSaleForm" style="dispaly:none" styleId="bankAddr">
            <html:optionsCollection property="bnkAddrList" name="memoSaleForm"
             value="idn" label="addr" />
            </html:select>
            
            </td>
            <td nowrap="nowrap"><table><tr><td><span class="pgTtl" >Courier :</span> </td>
            <td> <html:select property="value(courier)" name="memoSaleForm" style="dispaly:none" styleId="courier">
            <html:optionsCollection property="courierList" name="memoSaleForm"
            value="idn" label="addr" />
            </html:select>
            </td>
            <%if(cnt.equalsIgnoreCase("hk")){%>
            <td nowrap="nowrap"><span class="pgTtl" >Bank through :</span></td><td>
            <html:select property="value(throubnk)" name="memoSaleForm"  styleId="throubnk">
            <html:option value="0">-----select bank through-----</html:option>
            <html:optionsCollection property="thruBankList" name="memoSaleForm"
             value="idn" label="fnme" />
            </html:select>
            </td><%}%>
            </tr></table> </td>
            </tr>
            
            </table></td>
            
            </tr>
           </logic:present>
<tr>
<td><table><tr><td><span class="pgTtl" >Commission :</span> </td>
<html:hidden property="value(aadatcommval)" styleId="aadatcommval" name="memoSaleForm" value="0"/>
<html:hidden property="value(brk1commval)" styleId="brk1commval" name="memoSaleForm" value="0"/>
<html:hidden property="value(brk2commval)" styleId="brk2commval" name="memoSaleForm" value="0"/>
<html:hidden property="value(brk3commval)" styleId="brk3commval" name="memoSaleForm" value="0"/>
<html:hidden property="value(brk4commval)" styleId="brk4commval" name="memoSaleForm" value="0"/>
            <logic:present property="value(aadatcomm)" name="memoSaleForm" >
            <%aadatcommdisplay="display:block";%>
            </logic:present>
            <td nowrap="nowrap">
            <div style="<%=aadatcommdisplay%>" id="aadatcommdisplay">
            <table>
            <tr>
            <td><span class="pgTtl" >Aadat :</span> </td>
            <td>
            <span id="aaDatNme"><bean:write property="value(aaDat)" name="memoSaleForm" /></span> :
            <span id="aaDatComm"><bean:write property="value(aadatcomm)" name="memoSaleForm" /></span> </td>
            <td><html:radio property="value(aadatpaid)" onchange="setBrokerComm('aadatcomm','Y')" styleId="aadatpaid1"  value="Y"  name="memoSaleForm"/> </td>
            <td>&nbsp;Yes</td> 
            <td><html:radio property="value(aadatpaid)"  onchange="setBrokerComm('aadatcomm','N')" styleId="aadatpaid2" value="N" name="memoSaleForm"/></td>
            <td>&nbsp;No</td>
            <td><html:text property="value(aadatcomm)" styleId="aadatcomm" readonly="true" size="3" name="memoSaleForm" /> </td>
            </tr>
            </table>
            </div>
            </td>
            <logic:present property="value(brk1comm)" name="memoSaleForm" >
            <%brk1commdisplay="display:block";%>
           </logic:present>
           <td nowrap="nowrap">
           <div  style="<%=brk1commdisplay%>" id="brk1commdisplay">
           <table>
           <tr>
           <td><span class="pgTtl" >Broker :</span> </td>
           <td id="brk1Nme"><bean:write property="value(brk1)" name="memoSaleForm" /></td><td><html:radio property="value(brk1paid)" styleId="brk1paid1" value="Y" name="memoSaleForm"/></td><td>&nbsp;Yes</td>  <td><html:radio property="value(brk1paid)"  styleId="brk1paid1"  value="N" name="memoSaleForm"/></td><td>&nbsp;No</td> <td><html:text property="value(brk1comm)"  styleId="brk1comm"  readonly="true" size="3" name="memoSaleForm" /> </td>
           </tr>
           </table>
           </div>
           </td>
            <logic:present property="value(brk2comm)" name="memoSaleForm" >
            <%brk2commdisplay="display:block";%>
            </logic:present>
           <td  nowrap="nowrap">
           <div  style="<%=brk2commdisplay%>" id="brk2commdisplay">
           <table>
           <tr>
           <td id="brk2Nme"><bean:write property="value(brk2)" name="memoSaleForm" /></td><td><html:radio property="value(brk2paid)"  styleId="brk2paid1" value="Y" name="memoSaleForm"/> </td><td>&nbsp;Yes</td> <td><html:radio property="value(brk2paid)"  styleId="brk2paid2"   value="N" name="memoSaleForm"/></td><td> &nbsp;No</td> <td><html:text property="value(brk2comm)" styleId="brk2comm" readonly="true" size="3" name="memoSaleForm" /> </td>
           </tr>
           </table>
           </div>
           </td>
           <logic:present property="value(brk3comm)" name="memoSaleForm" >
           <%brk3commdisplay="display:block";%> 
           </logic:present>
           <td  nowrap="nowrap">
           <div style="<%=brk3commdisplay%>" id="brk3commdisplay">
           <table>
           <tr>
           <td id="brk3Nme"><bean:write property="value(brk3)" name="memoSaleForm" /></td><td><html:radio property="value(brk3paid)"   styleId="brk3paid1" value="Y" name="memoSaleForm"/> </td><td>&nbsp;Yes</td> <td><html:radio property="value(brk3paid)"  styleId="brk3paid2" value="N" name="memoSaleForm"/></td><td> &nbsp;No</td>  <td><html:text property="value(brk3comm)" styleId="brk3comm" readonly="true" size="3" name="memoSaleForm" /> </td>
           </tr>
           </table>
           </div>
           </td>
            <logic:present property="value(brk4comm)" name="memoSaleForm" >
           <%brk4commdisplay="display:block";%>  
           </logic:present>
           <td  nowrap="nowrap">
           <div style="<%=brk4commdisplay%>" id="brk4commdisplay">
           <table>
           <tr>
           <td id="brk4Nme"><bean:write property="value(brk4)" name="memoSaleForm" /></td><td><html:radio property="value(brk4paid)"   styleId="brk4paid1"  value="Y" name="memoSaleForm"/> </td><td>&nbsp;Yes</td> <td><html:radio property="value(brk4paid)"   styleId="brk4paid2" value="N" name="memoSaleForm"/></td><td> &nbsp;No</td> <td><html:text property="value(brk4comm)" styleId="brk4comm" readonly="true" size="3" name="memoSaleForm" /> </td>
           </tr>
           </table>
           </div>
           </td>
            </tr></table> </td>
            </tr>
            <!--<tr><td><span class="pgTtl" >Extra Charge : </span><input type="text" id="echarge" name="echarge" onchange="isNumericDecimal(this.id)"/></td></tr>-->
            <%if(cnt.equals("hk")){%>
            <tr>
            <td>
                <div class="float"><span class="pgTtl" >Delivery Date :</span> <html:text property="value(dlvDte)"  styleId="dlvDte" /></div>
                <div class="float"> <a href="#"  onClick="setYears(1900, <%=info.getCurrentYear()%>);showCalender(this, 'dlvDte');">
                <img src="<%=info.getReqUrl()%>/images/calender.png"></a></div>
            </td>
            </tr>
            <%}%>
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
    %>
           <!-- <html:submit property="submit" value="Save" onclick="return confirmChangesSL()" styleClass="submit"/>&nbsp;
                <html:submit property="value(save)" value="Save" onclick="return confirmChangesSL()" styleClass="submit"/>&nbsp;
              
                 <html:button property="value(perInv)" onclick="GenPerformInv('SL')" value="Perform Invice" styleClass="submit"/>&nbsp;
                 <html:button property="value(loalty)" onclick="loyaltyDis()" value="Loyalty Discount" styleClass="submit"/>&nbsp;-->
            </td></tr>
            <tr><td>
            
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
            <!--Create Excel <img src="../images/ico_file_excel.png" onclick="goTo('<%=info.getReqUrl()%>/marketing/memoSale.do?method=createXL','','')" />
            Mail Excel <img src="../images/ico_file_excel.png" onclick="newWindow('<%=info.getReqUrl()%>/marketing/memoSale.do?method=createXL&mail=Y')" />-->
            <%
                ArrayList prps = (info.getValue("MDL_MEMO_RTRN") == null)?new ArrayList():(ArrayList)info.getValue("MDL_MEMO_RTRN");
             itemHdr.add("Memo Id");itemHdr.add("appdte");itemHdr.add("Packet Code");
             %>  </td></tr>
             <tr><td>
            <table class="grid1">
                <tr>
                    <th>Sr</th>
                    <th>Memo Id</th>
                    <th>Approve Dte</th>
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
                    <!--<th><html:radio property="value(slRd)"  styleId="apRd"  onclick="ALLRedio('apRd' ,'AP_');calculationMemoSaleAll('AP','ALL',this)"  value="AP"/>&nbsp;App</th>
                    <th><html:radio property="value(slRd)" styleId="slRd"  onclick="ALLRedio('slRd' ,'SL_');calculationMemoSaleAll('SL','ALL',this)"  value="SL"/>&nbsp;Sale</th>
                    <th><html:radio property="value(slRd)" styleId="rtRd" onclick="ALLRedio('rtRd' ,'RT_');calculationMemoSaleAll('RT','ALL',this)"  value="RT"/> Return</th>
                    <th> <html:radio property="value(slRd)" styleId="invRd"  onclick="ALLRedio('invRd' ,'INV_');calculationNew('SL_')"  value="INV"/>&nbsp;Performa Inv </th>-->
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
            <th><html:radio property="<%=fld_nme%>" styleId="<%=fld_typ%>"  onclick="<%=val_cond%>"  value="<%=dflt_val%>"/><%=fld_ttl%> </th>
            <%}}%>
          
                </tr>
            <%
            itemHdr.add("RapRte");itemHdr.add("RapVlu");itemHdr.add("Dis");itemHdr.add("Prc / Crt");itemHdr.add("ByrDis");itemHdr.add("Quot");
             int count =0 ;
                ArrayList pkts = (info.getValue("PKTS") == null)?new ArrayList():(ArrayList)info.getValue("PKTS");
                for(int i=0; i < pkts.size(); i++) {%>
                <tr>
                <td><%=(i+1)%></td>
                <%    count=i+1;
                    PktDtl pkt = (PktDtl)pkts.get(i);
                    long pktIdn = pkt.getPktIdn();
                    String pktstt=util.nvl(pkt.getValue("pktstt"));
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
                    pktPrpMap.put("appdte",util.nvl(pkt.getValue("appdte")));
             %>
                 <td><%=pkt.getMemoId()%><input type="hidden" id="<%=count%>_memoid" value="<%=pkt.getMemoId()%>" /></td>
                 <td><%=util.nvl(pkt.getValue("appdte"))%></td>
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
                <%
    pageList= ((ArrayList)pageDtl.get("RADIOBODY") == null)?new ArrayList():(ArrayList)pageDtl.get("RADIOBODY");                 
            if(pageList!=null && pageList.size() >0){
            for(int j=0;j<pageList.size();j++){
            pageDtlMap=(HashMap)pageList.get(j);
            fld_nme="value("+(String)pageDtlMap.get("fld_nme")+""+pktIdn+")";
            fld_ttl=(String)pageDtlMap.get("fld_ttl");
            fld_typ=(String)pageDtlMap.get("fld_typ")+count;
            dflt_val=(String)pageDtlMap.get("dflt_val");
            if(dflt_val.equals("PKT"))
            dflt_val=String.valueOf(pktIdn);
            val_cond=(String)pageDtlMap.get("val_cond"); 
            val_cond=val_cond.replaceAll("CNT", String.valueOf(count));
            boolean isNtAPdisabled = false;
            if(pktstt.equals("LB_PRI_AP") && !dflt_val.equals("AP") && !dflt_val.equals("RT"))
            isNtAPdisabled = true;
            %>
            <td nowrap="nowrap"><html:radio property="<%=fld_nme%>" styleId="<%=fld_typ%>"  onclick="<%=val_cond%>" disabled="<%=isNtAPdisabled%>"  value="<%=dflt_val%>"/>
            <%if(dflt_val.equals("RT")){%>
            <html:text property="<%=rmkTxt%>" size="10"  />
            <%}%>
            </td>
            <%}}%>
                </tr>
              <%  
                pktPrpMap.put("RapRte",util.nvl((String)pkt.getRapRte()));
                pktPrpMap.put("RapVlu",util.nvl((String)pkt.getValue("rapVlu")));
                
                pktPrpMap.put("Dis",util.nvl((String)pkt.getDis()));pktPrpMap.put("Prc / Crt",util.nvl((String)pkt.getRte()));pktPrpMap.put("ByrDis",util.nvl((String)pkt.getByrDis()));pktPrpMap.put("Quot",util.nvl((String)pkt.getMemoQuot()));pktPrpMap.put("SalAmt",util.nvl((String)pkt.getValue("SalAmt")));
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
    <tr>
<td>
<div style="display:none;">
<%
String externalLnk=util.nvl((String)request.getAttribute("externalLnk"));
System.out.println(externalLnk);
if(externalLnk.length()>0){%>
<iframe name="loadhkurl" src="<%=externalLnk%>" id="loadhkurl" style="display:none;" align="middle" frameborder="0">
</iframe>
<%}%>
</div>
</td>
</tr>
    </table>
    
    <%@include file="../calendar.jsp"%>
     <%
     String allrpt="Y";
    pageList= ((ArrayList)pageDtl.get("ALLOWRPT") == null)?new ArrayList():(ArrayList)pageDtl.get("ALLOWRPT");  
            if(pageList!=null && pageList.size() >0){
            for(int j=0;j<pageList.size();j++){
            pageDtlMap=(HashMap)pageList.get(j);
            allrpt=util.nvl((String)pageDtlMap.get("dflt_val"));
     }
     }
     
     if(request.getAttribute("SLMSG")!=null && allrpt.equals("Y")){%>
 <script type="text/javascript">
 <!--
 var url = document.getElementById('rptUrl').value;
 windowOpen(url,'_blank')
 -->
 </script>
 <%}%>
  </body>
</html>
