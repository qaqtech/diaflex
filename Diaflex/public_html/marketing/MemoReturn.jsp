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
    <title>MemoReturn</title>
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
       <script src="<%=info.getReqUrl()%>/scripts/spacecode.js?v=<%=info.getJsVersion()%>"></script>
       <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/dispose.js?v=<%=info.getJsVersion()%> " > </script>
  </head>
<%
String view = "";
ArrayList pkts = (ArrayList)info.getValue("RTRN_PKTS");
HashMap prpList = info.getPrp();
        String logId=String.valueOf(info.getLogId());
        String onfocus="cook('"+logId+"')";
        String typ = (String)request.getAttribute("TYP");
        %>
 <body onfocus="<%=onfocus%>" onload="MM_preloadImages('images/faunalogo.png')" topmargin="0" leftmargin="0" rightmargin="0" bottommargin="0">
   <input type="hidden" id="deviceip" name="deviceip" value="<%=util.nvl((String)info.getSpaceCodeIp())%>"/>
<img src="../images/loadbig.gif" vspace="15" id="load" style="display:none;" class="loadpktDiv" border="0" />
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
  <img src="../images/line.jpg" border="0" /></td><td valign="middle"> <span class="pgHed">Memo Return</span> </td>
</tr></table>
  </td>
  </tr>
    <%String msg = (String)request.getAttribute("msg");
  if(msg!=null){
  %>
  <tr><td valign="top" class="tdLayout"> <span class="redLabel" > <%=msg%></span></td></tr>
  <%}%>
  
  <%
  String rtnPtk = (String)request.getAttribute("rtnPtk");
  if(rtnPtk!=null){%>
  <tr><td valign="top" class="tdLayout"><span class="redLabel"><%=rtnPtk%></span></td></tr>
  <%}%>
  <%
   String vnmNtFnd = (String)request.getAttribute("vnmNotFnd");
     if(vnmNtFnd!=null){%>
    <tr><td valign="top" class="tdLayout"><span class="redLabel"><%=vnmNtFnd%></span></td></tr>
   <%}%>
  <tr><td valign="top" class="tdLayout">
  <table><tr><td>
  <table><tr><td valign="top">
  <bean:parameter id="reqIdn" name="memoIdn" value="0"/>
  <%
    String pgTtl = " Memo Return " ;
    view = util.nvl(request.getParameter("view"));
  
    String lMemoIdn = "", viewAll="";
    
    viewAll = util.nvl((String)request.getAttribute("viewAll"));
    lMemoIdn = util.nvl((String)request.getAttribute("lMemoIdn"));
    if(lMemoIdn.length() == 0)
      lMemoIdn = reqIdn ;
    /*
    if(util.nvl((String)request.getAttribute("viewAll")).equalsIgnoreCase("yes")) {
      lMemoIdn = (String)request.getAttribute("lMemoIdn");
    }
    */
    /*
    if(reqIdn=="0")
      reqIdn = request.getParameter("memoId");
    */  
    if(view.equals("Y"))
        pgTtl += " Id : " + lMemoIdn ;
    ArrayList rfiddevices = ((ArrayList)info.getRfiddevice() == null)?new ArrayList():(ArrayList)info.getRfiddevice();  
  %>
 
    <html:form action="/marketing/memoReturn.do?method=load"  method="POST" onsubmit="return validatememoReturnRadio()">
    <html:hidden property="value(listname)" styleId="listname" value="vnmLst" />
    <html:hidden property="value(PKTTYP)" styleId="PKTTYP"  />
<table><tr><td valign="top">
<table class="grid1" >

<tr><th colspan="2">Memo Search </th></tr>


<tr>
<td colspan="2"> <html:radio property="value(memoSrch)" value="ByrSrch" onclick="DisplayMemoSrch('MS_1')" styleId="MS_1" /> Memos Search By Buyer </td>
</tr>
<tr style="display:none" id="DMS_1"><td colspan="2">

<table cellpadding="5"><tr>
<td>Buyer</td>
<td>
<html:select property="value(byrIdn)" styleId="byrId" onchange="getTrms(this,'SALE')" >
<html:option value="0">---select---</html:option>
<html:optionsCollection property="byrLstFetch" value="byrIdn" label="byrNme" />

</html:select>
</td> </tr>
<tr>
<td> <span class="txtBold" >Terms </span></td><td>

<html:select property="value(byrTrm)" styleId="rlnId" onchange="GetTypMemoReturnRadioIdn()">
<html:option value="0">---select---</html:option>
<html:optionsCollection property="trmsLst" name="memoReturnForm"
label="trmDtl" value="relId" />
</html:select>

</td>
</tr>

</table>

</td>
</tr>
<tr>
<td colspan="2"> <html:radio property="value(memoSrch)" value="MemoSrch" styleId="MS_2" onclick="DisplayMemoSrch('MS_2')" /> Memos Search By Memo Ids</td>
</tr>
<tr style="display:none" id="DMS_2">
<td>
<table>
<tr>
<td>Memo Ids</td><td><html:text property="memoIdn" name="memoReturnForm" styleId="memoid"/></td></tr>
<tr><td>Packets. </td><td>
<html:textarea property="value(vnmLst)" name="memoReturnForm" styleId="vnmLst"  onkeyup="getVnmCount('vnmLst','vnmLstCnt')" onchange="getVnmCount('vnmLst','vnmLstCnt')"/><br><span id="vnmLstCnt">0</span>
<label id="fldCtr" ></label></td> </tr>
<tr> <td colspan="2"> <%
 ArrayList rfiddevice = ((ArrayList)info.getRfiddevice() == null)?new ArrayList():(ArrayList)info.getRfiddevice();
 if(rfiddevice!=null && rfiddevice.size()>0){
 %>
 RFID Device: <html:select property="value(dvcLst)" styleId="dvcLst" name="memoReturnForm"  >
 <%for(int j=0;j<rfiddevice.size();j++){
 ArrayList device=new ArrayList();
 device=(ArrayList)rfiddevice.get(j);
 String val=(String)device.get(0);
 String disp=(String)device.get(1);
 %>
 <html:option value="<%=val%>"><%=disp%></html:option>
 <%}%>
 </html:select>&nbsp;
 <%if(info.getRfid_seq().length()==0){%>
 <html:button property="value(rfScan)" value="RF ID Scan" styleId="rfScan" onclick="doScan('vnmLst','fldCtr','dvcLst','SCAN')"  styleClass="submit" />
 <html:button property="value(autorfScan)" value="Auto Scan" styleId="autorfScan" onclick="doScan('vnmLst','fldCtr','dvcLst','AUTOSCAN')"  styleClass="submit" />
<%}%>
 <html:button property="value(stopAutorfScan)" value="Stop Auto Scan" onclick="doScan('vnmLst','fldCtr','dvcLst','STOPAUTOSCAN')"  styleClass="submit" />
 
<%}%>
              <%if(util.nvl((String)info.getDmbsInfoLst().get("RFID_DNK"),"N").equals("Y")){%>
              Continue Scan <input type="checkbox" id="contscan" title="Select To Continue Scan"></input>
                        <span style="margin:0px; padding:0px; display:none;">
                        <label id="summary" style="display:none;"></label>&nbsp;&nbsp;
                        </span>
                        Count&nbsp;&nbsp;:&nbsp;<label id="count"></label>&nbsp;&nbsp;
                        <span style="margin:0px; padding:0px; display:none;">
                        Notify&nbsp;&nbsp;:&nbsp;<label id="notify"></label>
                        <input type="checkbox" id="accumulateMode" title="Accumulate Mode"></input>
                        </span>
                        <html:button property="value(myButton)" value="Scan" styleId="myButton" onclick="scanRf()"  styleClass="submit" />
                        <span id="loadingrfid"></span>
                        <!--<html:button property="value(myButton2)" value="Dispose" styleId="myButton2" onclick="dispose()"  styleClass="submit" />
                        <html:button property="value(myButton4)" value="LED On" styleId="myButton4" onclick="waitOn('pid')"  styleClass="submit" />
                        <html:button property="value(myButton5)" value="LED Off" styleId="myButton5" onclick="waitOff()"  styleClass="submit" />
                        <html:button property="value(myButton6)" value="TestDll" styleId="myButton6" onclick="testDll()"  styleClass="submit" />-->
             <%}%>   
</td></tr>

</table>
</td>
</tr>
<tr><td colspan="2"> <html:submit property="submit" value="View" styleClass="submit" /></td></tr>
</table></td><td valign="top"><div id="memoIdn"></div></td></tr></table>
       
    </html:form>
    </td>
    <%if(request.getAttribute("view")!=null || view.equals("Y")){
 
      if( pkts!=null && pkts.size()>0){
    %>
    <td valign="top">
    <table class="grid1">
    <tr> <td></td> <td><b>Final Total</b></td><td><b>Selected</b></td><td><b>Quot Total</b></td> </tr>
    <tr> <td><b>Qty</b></td> <td><bean:write property="qty" name="memoReturnForm" /></td><td><span id="qty">0</span></td><td><bean:write property="qty" name="memoReturnForm" /></td> </tr>
    <tr> <td><b>Cts</b> </td> <td><bean:write property="cts" name="memoReturnForm" /></td><td><span id="cts">0</span></td> <td><bean:write property="cts" name="memoReturnForm" /></td> </tr>
    <tr> <td><b>Avg Prc</b></td> <td><bean:write property="avgPrc" name="memoReturnForm" /></td><td><span id="avgprc">0</span></td><td><bean:write property="avgPrcQuot" name="memoReturnForm" /></td>  </tr>
    <tr> <td><b>Avg Dis</b> </td> <td><bean:write property="avgDis" name="memoReturnForm" /></td><td><span id="avgdis">0</span></td> <td><bean:write property="avgDisQuot" name="memoReturnForm" /></td> </tr>
    <tr> <td><b>Value </b></td> <td><bean:write property="vlu" name="memoReturnForm" /></td><td><label id="vlu">0</label></td>  <td><bean:write property="vluQuot" name="memoReturnForm" /></td></tr>
   <!--<tr><td><b>Buyer</b></td><td colspan="2"><bean:write property="byr" name="memoReturnForm" /></td></tr>-->
   <tr><td><b>Date</b></td><td colspan="3"><bean:write property="dte" name="memoReturnForm" /></td></tr>
    <tr><td><b>Memo Id</b></td><td colspan="3"><bean:write property="value(memoIdn)" name="memoReturnForm" /></td></tr>
  <tr><td><b>Type</b></td><td colspan="3"><bean:write property="typ" name="memoReturnForm" /></td></tr>
 <% 
  String cnt = util.nvl((String)info.getDmbsInfoLst().get("CNT"));
 if(cnt.equalsIgnoreCase("KJ")){
    String ttlMemoVal=util.nvl((String)request.getAttribute("ttlMemoVal"),"0");
    ttlMemoVal=ttlMemoVal.replaceAll(" ", "");
    long memoCrt = Long.parseLong(ttlMemoVal);%>
   <tr>
   <td><b>Credit Limit</b></td>
   <td colspan="3">
   <label id="creditlimit">  <bean:write property="value(crtLimit)" name="memoReturnForm" />
   <input type="hidden"  name="ttlByrVal" id="ttlByrVal" value="<%=memoCrt%>" />
   </label>
   </td></tr>

 <%}%>
 </table>
    </td>
    <td valign="top">
    <table class="grid1">
    <tr><td colspan="3" align="center"><b>Buyer Details</b></td></tr>
    <tr>
    <td><b>Buyer</b></td><td><bean:write property="byr" name="memoReturnForm" /></td>
    </tr>
    <tr>
    <td><b>Email</b></td><td><bean:write property="value(email)" name="memoReturnForm" /></td>
    </tr>
    <tr>
    <td><b>Terms</b></td>
    <td>
    <bean:write property="value(trmsdtls)" name="memoReturnForm" />
    <logic:notEqual property="value(shortTrms)"  name="memoReturnForm"  value="N" >
    &nbsp;(<bean:write property="value(shortTrms)" name="memoReturnForm"/>)
    </logic:notEqual>
    </td>
    </tr>
        <tr>
    <td><b>Mobile No</b></td><td><bean:write property="value(mobile)" name="memoReturnForm" /></td>
    </tr>
        <tr>
    <td><b>Office No</b></td><td><bean:write property="value(office)" name="memoReturnForm" /></td></tr>
    <tr>
    <td><b>Sale Person</b></td><td><bean:write property="value(SALEMP)" name="memoReturnForm" /></td>
    </tr>
    </table>
    </td>
    <%}}%>
    </tr></table>
    </td></tr>
    </table>
   
    <%
    if(view.equals("Y")) {
    if( pkts!=null && pkts.size()>0){
    ArrayList itemHdr = new ArrayList();
    ArrayList pktList = new ArrayList();
    HashMap pktPrpMap = new HashMap();
    String pktTy=util.nvl((String)request.getAttribute("PKTTY"));    
    ArrayList prpDspBlocked = info.getPageblockList();
    HashMap allPageDtl= (info.getPageDetails() == null)?new HashMap():(HashMap)info.getPageDetails();
    HashMap pageDtl=(HashMap)allPageDtl.get("MEMO_RETURN");
    ArrayList pageList=new ArrayList();
    HashMap pageDtlMap=new HashMap();
    String fld_nme="",fld_ttl="",val_cond="",dflt_val="",fld_typ="",form_nme="",lov_qry="";
        String formAction = "/marketing/memoReturn.do?method=save&memoIdn="+lMemoIdn ; 
    String memoIdn = util.nvl((String)request.getAttribute("memoIdn"));
    %>
        <html:form action="<%=formAction%>" method="POST">
<div id="popupContactSale" >
<table align="center" cellpadding="0" cellspacing="0" >
<tr><td>Do You Want To Block Packet From Website?</td></tr>
<tr><td><table><tr><td><html:radio property="value(isBLOCK)"   value="YES"/>YES </td>
<td> <html:radio property="value(isBLOCK)" value="NO"/>NO </td>
</tr></table> </td> </tr>
<tr><td>
<html:submit property="value(consignment)" value="Save" onclick="" styleClass="submit"/>&nbsp;
<button type="button" onclick="disablePopupSale()" Class="submit" >Back</button> </td>
</tr>
</table>
</div> 
<html:hidden property="value(BLOCK_POPUP)" styleId="BLOCK_POPUP" name="memoReturnForm" />
           <html:hidden property="oldMemoIdn" name="memoReturnForm" />
                    <html:hidden property="nmeIdn" name="memoReturnForm" />
                      <html:hidden property="relIdn" name="memoReturnForm" />
                      <html:hidden property="typ" name="memoReturnForm" />
                      <html:hidden property="value(exh_rte)" styleId="exhRte" name="memoReturnForm" />          
            <table>
            <tr><td nowrap="nowrap">
<b>Remark/Comment</b>&nbsp;<html:text property="value(rmk)" size="15"  />
<%ArrayList notepersonList = ((ArrayList)info.getNoteperson() == null)?new ArrayList():(ArrayList)info.getNoteperson();
if(notepersonList.size()>0){%>
&nbsp;Note Person:&nbsp;
      <html:select name="memoReturnForm" property="value(noteperson)" styleId="noteperson">
      <html:option value="">---Select---</html:option><%
      for(int i=0;i<notepersonList.size();i++){
      ArrayList noteperson=(ArrayList)notepersonList.get(i);%>
      <html:option value="<%=(String)noteperson.get(0)%>"> <%=(String)noteperson.get(1)%> </html:option>
      <%}%>
      </html:select>
<%}%>
<%ArrayList burCbList = ((ArrayList)session.getAttribute("burCbList") == null)?new ArrayList():(ArrayList)session.getAttribute("burCbList");
if(burCbList.size()>0){%>
&nbsp;Buyer Cabin:&nbsp;
<html:select property="value(cabin)" name="memoReturnForm" >
<html:optionsCollection property="value(ByrCbList)" name="memoReturnForm" value="FORM_NME" label="FORM_TTL" />
</html:select>
<%}%>
<%ArrayList prpValLst = (ArrayList)prpList.get("LOC_OF_BUSINESSV");
           if(prpValLst!=null && prpValLst.size()>0){ 
           %>
           Location of Business:
           <html:select property="value(LOC_OF_BUSINESS)" styleId="LOC_OF_BUSINESS" name="memoReturnForm" >
           <html:option value="">-Location of Business-</html:option>
           <%for(int k=0;k<prpValLst.size();k++){ 
           String lprpVal = (String)prpValLst.get(k);
           %>
           <html:option value="<%=lprpVal%>"><%=lprpVal%></html:option>
           <%}%>
           </html:select>
           <%}
           prpValLst = (ArrayList)prpList.get("BYR_CONTACTEDV");
           if(prpValLst!=null && prpValLst.size()>0){ 
           %>
           Contacted:
           <html:select property="value(BYR_CONTACTED)" styleId="BYR_CONTACTED" name="memoReturnForm" >
           <html:option value="">-Contacted--</html:option>
           <%for(int k=0;k<prpValLst.size();k++){ 
           String lprpVal = (String)prpValLst.get(k);
           %>
           <html:option value="<%=lprpVal%>"><%=lprpVal%></html:option>
           <%}%>
           </html:select>
           <%}
           prpValLst = (ArrayList)prpList.get("BYR_STATUSV");
           if(prpValLst!=null && prpValLst.size()>0){ 
           %>
           Status:
           <html:select property="value(BYR_STATUS)" styleId="BYR_STATUS" name="memoReturnForm" >
           <html:option value="">-Status-</html:option>
           <%for(int k=0;k<prpValLst.size();k++){ 
           String lprpVal = (String)prpValLst.get(k);
           %>
           <html:option value="<%=lprpVal%>"><%=lprpVal%></html:option>
           <%}%>
           </html:select>
           <%}
           prpValLst = (ArrayList)prpList.get("BYR_MOD_OF_SALEV");
           if(prpValLst!=null && prpValLst.size()>0){ 
           %>
           Mode of sales:
           <html:select property="value(BYR_MOD_OF_SALE)" styleId="BYR_MOD_OF_SALE" name="memoReturnForm" >
           <html:option value="">-Mode of sales-</html:option>
           <%for(int k=0;k<prpValLst.size();k++){ 
           String lprpVal = (String)prpValLst.get(k);
           %>
           <html:option value="<%=lprpVal%>"><%=lprpVal%></html:option>
           <%}%>
           </html:select>
           <%}
           
             prpValLst = (ArrayList)prpList.get("SYMBOLV");
           if(prpValLst!=null && prpValLst.size()>0){ 
           %>
          Symbol:
           <html:select property="value(SYMBOL)" styleId="SYMBOL" name="memoReturnForm" >
           <html:option value="">-select Symbol-</html:option>
           <%for(int k=0;k<prpValLst.size();k++){ 
           String lprpVal = (String)prpValLst.get(k);
           %>
           <html:option value="<%=lprpVal%>"><%=lprpVal%></html:option>
           <%}%>
           </html:select>
           <%}%>
    </td></tr>
            <tr><td nowrap="nowrap">
           <%
           if(pktTy.equals("SMX")){
        pageList= ((ArrayList)pageDtl.get("SMXBUTTON") == null)?new ArrayList():(ArrayList)pageDtl.get("SMXBUTTON");             
           }else{     
        pageList= ((ArrayList)pageDtl.get("BUTTON") == null)?new ArrayList():(ArrayList)pageDtl.get("BUTTON");             
          }
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
    </td></tr>
    
    <tr><td nowrap="nowrap">
            <label class="pgTtl">Memo Packets :<bean:write property="value(memoIdn)" name="memoReturnForm" /></label>
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
            <!--   Create Excel <img src="../images/ico_file_excel.png" onclick="goTo('<%=info.getReqUrl()%>/marketing/memoReturn.do?method=createXL','','')" />
            Mail Excel <img src="../images/ico_file_excel.png" onclick="newWindow('<%=info.getReqUrl()%>/marketing/memoReturn.do?method=createXL&mail=Y&nmeIdn=<%=util.nvl((String)request.getAttribute("NMEIDN"))%>')" /> -->
            <%
                ArrayList prps = (info.getValue("MDL_MEMO_RTRN") == null)?new ArrayList():(ArrayList)info.getValue("MDL_MEMO_RTRN");
                itemHdr.add("memoIdn");
                itemHdr.add("Packet Code");
             %>  
            <table class="grid1">
                <tr>
                    <th>Sr</th>
                    <th>Issue</th>
                    <th>Issue Date</th>
                    <th>Return</th>
                    <th>Approve</th>
                    <th>Cancel</th>
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
                    <th>RapVlu</th>
                    <th>Dis</th>
                    <th>Prc / Crt </th>
                    <th>ByrDis</th>
                    <th>Quot</th>
                    <th>AMT</th>
                    <th>Profit %</th>
                </tr>
            <%
               
                //(info.getValue(reqIdn+ "_PKTS") == null)?new ArrayList():(ArrayList)info.getValue(reqIdn+ "_PKTS");
                itemHdr.add("RapRte");itemHdr.add("RapVlu");itemHdr.add("Dis");itemHdr.add("Prc / Crt");itemHdr.add("ByrDis");itemHdr.add("Quot");
               int count = 0;
                PktDtl pkt = null;
                for(int i=0; i < pkts.size(); i++) {%>
                <tr>
                <td><%=(i+1)%></td>
                <%    
                    count=i+1;
                    pkt = (PktDtl)pkts.get(i);
                    long pktIdn = pkt.getPktIdn();
                    String cbPrp = "value(upd_" + pktIdn + ")";
                    String sttPrp = "value(stt_" + pktIdn + ")" ;
                    String rmkTxt =  "value(rmk_" + pktIdn + ")" ;
                    String rdSTId = "ST_"+count;
                    String rdRTId = "RT_"+count;
                    String rdAPId = "AP_"+count;
                    String rdCLId = "CL_"+count;
                    String fnlsal=util.nvl(pkt.getMemoQuot(),pkt.getRte());
                    pktPrpMap = new HashMap();
                    pktPrpMap.put("memoIdn",util.nvl(pkt.getValue("memoIdn")));
                    pktPrpMap.put("Packet Code",util.nvl(pkt.getVnm()));
                   
                %>
                <td><html:radio property="<%=sttPrp%>" styleId="<%=rdSTId%>" onclick="calculation('AP_')" value="IS"/></td>
                <td nowrap="nowrap"><%=pkt.getValue("dte")%></td>
                <td><html:radio property="<%=sttPrp%>" styleId="<%=rdRTId%>" onclick="calculation('AP_')" value="RT"/> <html:text property="<%=rmkTxt%>" size="10"  /> </td>
                <td><html:radio property="<%=sttPrp%>" styleId="<%=rdAPId%>" onclick="calculation('AP_')" value="AP"/></td>
                <td><html:radio property="<%=sttPrp%>" styleId="<%=rdCLId%>" onclick="calculation('AP_')" value="CL"/></td>
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
                <td><%=util.nvl(pkt.getValue("rapVlu"))%></td>
                <td><%=pkt.getDis()%></td>
                <td><%=pkt.getRte()%>  <input type="hidden" id="<%=count%>_quot" value="<%=pkt.getRte()%>" /> 
                <input type="hidden" id="<%=count%>_fnl" value="<%=fnlsal%>" /> 
                </td>
                <td><%=pkt.getByrDis()%></td>
                <td><%=pkt.getMemoQuot()%></td>
                <td><%=util.nvl(pkt.getValue("AMT"))%></td>
                <td><%=util.nvl(pkt.getValue("plper"))%></td>
                </tr>
              <%
                 pktPrpMap.put("RapVlu",util.nvl((String)pkt.getValue("rapVlu")));
                pktPrpMap.put("RapRte",util.nvl((String)pkt.getRapRte()));pktPrpMap.put("Dis",util.nvl((String)pkt.getDis()));pktPrpMap.put("Prc / Crt",util.nvl((String)pkt.getRte()));pktPrpMap.put("ByrDis",util.nvl((String)pkt.getByrDis()));pktPrpMap.put("Quot",util.nvl((String)pkt.getMemoQuot()));
                    pktPrpMap.put("Description", "CPD");
                    pktPrpMap.put("Type", "P");
                    pktPrpMap.put("Pcs", "1");
                    pktPrpMap.put("Carats", pkt.getCts());
                    pktPrpMap.put("Rate", util.nvl(pkt.getFnlRte(),pkt.getByrRte()));
                    pktPrpMap.put("Est.Amount", util.nvl(pkt.getValue("AMT")));
                    pktPrpMap.put("ID No",  util.nvl(pkt.getVnm()));
                pktList.add(pktPrpMap);
                }
                session.setAttribute("pktList", pktList);
                session.setAttribute("itemHdr", itemHdr);
            %>
            <input type="hidden" id="rdCount" value="<%=count%>" />
            
            </table>
            </td></tr>
        </html:form>
    <%}else{%>
   <tr><td> Sorry no result found</td></tr>
  <%  }
    
    }
    %>
    </table>
    </td></tr>
    <tr>
    <td><jsp:include page="/footer.jsp" /> </td>
    </tr>
    </table>
     <%@include file="../calendar.jsp"%>
     
<script language="javascript">
	var device = new Spacecode.Device(document.getElementById("deviceip").value);
	var lastInventory = null;
	var tagscanList = [];


	device.on('connected', function ()
	{
		if(device.isInitialized())
		{
			device.requestScan();		
			// Connection with the remote device established
			console.log('Connected to Device: '+ device.getDeviceType() + ' ('+device.getSerialNumber()+')');
		}
	});

	device.on('disconnected', function ()
	{
		if(device.isInitialized())
		{
			// The connection to the remote device has been lost
			console.log(device.getSerialNumber() + ' - Disconnected');
			return;
		}
	});

	device.on('scanstarted', function ()
	{
		count =0;
		console.log("Scan started.");
	});

	device.on('scancompleted', function ()
	{
		device.getLastInventory(function(inventory)
		{
			lastInventory = inventory;
			summaryElem2 = document.getElementById("notify");
			summaryElem2.innerHTML = inventory.getNumberTotal() +" tags have been scanned.";				
			tagscanList = inventory.getTagsAll() || [];
            var str = tagscanList.join();
                        var chk =document.getElementById("contscan").checked;
                        var listnametextContent=document.getElementById(document.getElementById("listname").value).textContent;
                        if(chk && listnametextContent!=''){
                          if(str!=''){
                              str=listnametextContent+","+str;
                          }
                        }
                        document.getElementById(document.getElementById("listname").value).textContent = str;
                        var listnametextContentCount=document.getElementById(document.getElementById("listname").value).textContent;
                        if(listnametextContentCount!=''){
                        var resCount = listnametextContentCount.split(",");
                        document.getElementById("count").innerHTML=resCount.length;
                        }
                        document.getElementById('loadingrfid').innerHTML = "";
			// Note: with remote devices, getLastInventory can return 'null' in case of communication errors
			console.log(inventory.getNumberTotal() +" tags have been scanned.");
			// prints: "X tags have been scanned." (with X the result of getNumberTotal)
		});	
		// the inventory (resulting from the scan) is ready
		console.log("Scan completed.");
	});

	device.on('tagadded', function (tagUid)
	{
		// a tag has been detected during the scan
		console.log("Tag scanned: "+ tagUid);
	});	
	

	
	function scanRf()
	{	
		document.getElementById('loadingrfid').innerHTML = "<img src=\"../images/processing1.gif\" align=\"middle\" border=\"0\" />";
                count =0;
		if(device.isConnected() == false)
		{
			device.connect();			
		}
		else
		{
			device.requestScan();		
		}
	}
	
	function callBackLED(result)
	{
		if(!result)
		{
			console.debug("Could not start the lighting operation.");
		}

		// let the LED's blink untill User press OK.
		alert("Press OK to Turn LED Off");
		
		device.stopLightingTagsLed(function(result)
		{
			if(!result)
			{
				console.debug("Could not stop the lighting operation.");
			}
		});		
	}	
	
	function ligtOn(txtarea)
	{
                var li = document.getElementById(txtarea).value;
                var arr = li.split(',');
		device.startLightingTagsLed(callBackLED,arr);	
		
	}
	
	function ligtOff()
	{
		device.stopLightingTagsLed(function(result)
		{
			if(!result)
			{
				console.debug("Could not stop the lighting operation.");
			}
		});
	}	
		
	//device.connect();
</script>
  </body>
</html>