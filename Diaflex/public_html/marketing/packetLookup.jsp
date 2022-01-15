<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
 
<%@ page errorPage="../error_page.jsp" %>
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import="java.util.ArrayList,java.util.ArrayList, java.util.Iterator, java.sql.Date,java.util.HashMap, ft.com.marketing.PacketLookupForm" %>
<jsp:useBean id="util" class="ft.com.JspUtil" scope="page" />
<jsp:useBean id="info" class="ft.com.InfoMgr" scope="session" />
<%@taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>
<%@taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>Packet Lookup</title>
    <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/bse.css?v=<%=info.getJsVersion()%>"/>
      <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/style.css?v=<%=info.getJsVersion()%>"/>
       <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/general.css?v=<%=info.getJsVersion()%>"/>
        <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/ajax.js?v=<%=info.getJsVersion()%> " > </script>
        <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/memoScript.js?v=<%=info.getJsVersion()%> " > </script>
        <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/bse.js?v=<%=info.getJsVersion()%> " > </script>
        <script type="text/javascript" src="<%=info.getReqUrl()%>/PopScripts/popup.js?v=<%=info.getJsVersion()%> " > </script>
        <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/tableFilter/actb.js " > </script>
        <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/tableFilter/tablefilter.js " > </script>
        <script src="<%=info.getReqUrl()%>/PopScripts/jquery-1.2.6.min.js" type="text/javascript"></script>
       <script src="<%=info.getReqUrl()%>/scripts/spacecode.js?v=<%=info.getJsVersion()%>"></script>
       <script src="<%=info.getReqUrl()%>/scripts/rfid.js?v=<%=info.getJsVersion()%>"></script>
       <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/dispose.js?v=<%=info.getJsVersion()%> " > </script>
        <%
        String prp = util.nvl(request.getParameter("prp"));
        String logId=String.valueOf(info.getLogId());
        String onfocus="cook('"+logId+"')";
       
        %>
 <body onfocus="<%=onfocus%>"  onload="MM_preloadImages('images/faunalogo.png')" topmargin="0" leftmargin="0" rightmargin="0" bottommargin="0">
 <input type="hidden" id="reqUrl" name="reqUrl" value="<%=info.getReqUrl()%>"/>
  <input type="hidden" id="deviceip" name="deviceip" value="<%=util.nvl((String)info.getSpaceCodeIp())%>"/>
 <img src="../images/loadbig.gif" vspace="15" id="load" style="display:none;" class="loadpktDiv" border="0" />
<div id="backgroundPopup"></div>
<div id="popupContactASSFNL">
<table class="popUpTb">
 <tr><td height="5%">&nbsp;&nbsp;&nbsp;<html:button property="value(close)" styleClass="submit"  onclick="MKEmptyPopup('frame');disablePopupASSFNL()" value="Close"  /> </td></tr>
<tr><td valign="top" height="95%">
<iframe  name="frame" class="frameStyle" align="middle" frameborder="0">

</iframe></td></tr>
</table>
</div>
<%
 ArrayList rolenmLst=(ArrayList)info.getRoleLst();
         String usrFlg=util.nvl((String)info.getUsrFlg());
%>
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
  <img src="../images/line.jpg" border="0" /></td><td valign="middle"> <span class="pgHed">Packet Lookup</span> </td>
</tr></table>
  </td>
  </tr>
  
   <%
    ArrayList prpDspBlocked = info.getPageblockList();
    HashMap allPageDtl= (info.getPageDetails() == null)?new HashMap():(HashMap)info.getPageDetails();
    HashMap pageDtl=(HashMap)allPageDtl.get("PACKET_LOOKUP");
    ArrayList pageList=new ArrayList();
    HashMap pageDtlMap=new HashMap();
    String fld_nme="",fld_ttl="",val_cond="",dflt_val="",fld_typ="",form_nme="",flg1="",lov_qry="";
      String nxttrns="N";
      pageList=(ArrayList)pageDtl.get("NEXTTRNS");
      if(pageList!=null && pageList.size() >0){
      for(int j=0;j<pageList.size();j++){
      pageDtlMap=(HashMap)pageList.get(j);
      fld_ttl=(String)pageDtlMap.get("fld_ttl");
      val_cond=(String)pageDtlMap.get("val_cond");
      fld_typ=(String)pageDtlMap.get("fld_typ");
      dflt_val = (String)pageDtlMap.get("dflt_val");
      nxttrns="Y";
      }}
   String vnmNtFnd = (String)request.getAttribute("vnmNotFnd");
           HashMap dbInfoSys = info.getDmbsInfoLst();
        String cnt = (String)dbInfoSys.get("CNT");
       if(request.getAttribute("RAPMSG")!=null){%>
        <tr><td height="15"  valign="top" class="tdLayout"><span class="redLabel"> <%=request.getAttribute("RAPMSG")%></span></td></tr>
       <% }
     if(vnmNtFnd!=null){%>
    <tr><td valign="top" class="tdLayout"><span class="redLabel"><%=vnmNtFnd%></span></td></tr>
   <%}%>
    <tr><td valign="top" class="tdLayout">
     <html:form action="marketing/PacketLookup.do?method=fetch" method="post">
     <input type="hidden" id="cnt" name="cnt" value="<%=cnt%>">
     <html:hidden property="value(listname)" styleId="listname" value="pid" />
  <input type="hidden" name="prp" value="<%=prp%>" />
  <table class="grid1">
  <tr>
  <td colspan="2"><jsp:include page="/genericSrch.jsp" /> </td>
  </tr>

<tr>
<td colspan="2">
 <html:radio property="value(srchRef)"  styleId="vnm" value="vnm" /> Packet Code.
 <html:radio property="value(srchRef)"  styleId="vnm" value="cert_no" /> Cert No.
  <html:radio property="value(srchRef)"  styleId="vnm" value="show_id" /> Show Id.
<%
  ArrayList asViewPrp = info.getGncPrpLst();

 
  if((usrFlg.equals("SYS") || rolenmLst.contains("ADMIN")) && asViewPrp!=null && asViewPrp.size()>0){
  %>
  &nbsp;&nbsp;<img src="../images/edit.jpg" title="Edit Properties Module" onclick="newWindow('<%=info.getReqUrl()%>/dynamicProperty.do?method=load&mdl=PKTLUP_SRCH&sname=PKTLUPGNCSrch&par=GENERIC')" border="0" width="15px" height="15px"/> 
  <%}%>

</td>
</tr>


<tr>
<td>Packet Id: </td>
<td><html:textarea property="vnm" name="packetLookupForm" styleId="pid"/>
<label id="fldCtr" ></label>
</td>
</tr>
<tr>
<td colspan="2" nowrap="nowrap">
<html:submit property="PacketLookup" value="Search Packet" styleClass="submit" />
<!--<html:submit property="value(recalRap)" value="Calculate Rap" styleClass="submit" />-->
<%
pageList= ((ArrayList)pageDtl.get("FETCH") == null)?new ArrayList():(ArrayList)pageDtl.get("FETCH");
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
            if(fld_typ.equals("S")){%>
            <html:submit property="<%=fld_nme%>" value="<%=fld_ttl%>" onclick="<%=val_cond%>" styleClass="submit"/>&nbsp;
            <%}else if(fld_typ.equals("B")){%>
            <html:button property="<%=fld_nme%>" value="<%=fld_ttl%>" onclick="<%=val_cond%>" styleClass="submit"/>&nbsp;
            <%}}}%>
<!--<input type="button" name="packetLookupForm" value="Reset" onclick="reset()" class="submit" />-->
<input type="button" name="Reset" value="Reset" onclick="goTo('PacketLookup.do?method=load','', '');" class="submit">&nbsp;
 <%
 ArrayList rfiddevice = ((ArrayList)info.getRfiddevice() == null)?new ArrayList():(ArrayList)info.getRfiddevice();
 if(rfiddevice!=null && rfiddevice.size()>0){
 %>
 RFID Device: <html:select property="value(dvcLst)" styleId="dvcLst" name="packetLookupForm"  >
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
 <html:button property="value(rfScan)" value="RF ID Scan" styleId="rfScan" onclick="doScan('pid','fldCtr','dvcLst','SCAN')"  styleClass="submit" />
 <html:button property="value(autorfScan)" value="Auto Scan" styleId="autorfScan" onclick="doScan('pid','fldCtr','dvcLst','AUTOSCAN')"  styleClass="submit" />
<%}%>
 <html:button property="value(stopAutorfScan)" value="Stop Auto Scan" onclick="doScan('pid','fldCtr','dvcLst','STOPAUTOSCAN')"  styleClass="submit" />
 
<%}%>
              <%if(util.nvl((String)info.getDmbsInfoLst().get("RFID_DNK"),"N").equals("Y")){%>
                        Continue Scan <input type="checkbox" id="contscan" title="Select To Continue Scan"></input>
                        <span style="margin:0px; padding:0px; display:none;">
                        <label id="summary" style="display:none;"></label>&nbsp;&nbsp;
                        </span>
                        Count&nbsp;&nbsp;:&nbsp;<label id="count"></label>&nbsp;&nbsp;
                        <span style="margin:0px; padding:0px; display:none;">
                        Notify&nbsp;&nbsp;:&nbsp;<label id="notify"></label>
                        <input type="checkbox" id="accumulateMode" title="Accumulate Mode" onchange="AcculateChanged()"></input>
                        </span>
                        <html:button property="value(myButton)" value="Scan" styleId="myButton" onclick="scanRf()"  styleClass="submit" />
                        <span id="loadingrfid"></span>
             <%}else if(util.nvl((String)info.getDmbsInfoLst().get("RFID_DNK_USB"),"N").equals("Y")){%>
                        <!--<applet id="napp" code="RFApp.class" width="1px" height="1px" ARCHIVE = "rfq.jar,sdk.jar" style="" codebase="<%=util.nvl((String)info.getDmbsInfoLst().get("CODEBASE"),"N")%>"></applet>-->
                        <applet id="napp" code="RFApp.class" width="1" height="1" ARCHIVE = "rfq.jar,sdk.jar" CODEBASE = "http://gia.dnktechnologies.com/test/resource/" style="visibility:hidden;"></applet>
                        Continue Scan <input type="checkbox" id="contscan" title="Select To Continue Scan"></input>
                        <span style="margin:0px; padding:0px; display:none;">
                        <label id="summary" style="display:none;"></label>&nbsp;&nbsp;
                        </span>
                        Count&nbsp;&nbsp;:&nbsp;<label id="count"></label>&nbsp;&nbsp;
                        <span style="margin:0px; padding:0px; display:none;">
                        Notify&nbsp;&nbsp;:&nbsp;<label id="notify"></label>
                        <input type="checkbox" id="accumulateMode" title="Accumulate Mode" onchange="AcculateChangedUSB()"></input>
                        </span>
                        <html:button property="value(myButton)" value="Scan" styleId="myButton" onclick="scanRfUSB()"  styleClass="submit" />
             <%} %>   
</td>
</tr>
  </table>
 
  </html:form>
    </td>
    
    </tr>
  
  
   <tr><td valign="top" class="tdLayout" >
   <table><tr><td valign="top">
    <%
    HashMap pktList = (HashMap)request.getAttribute("pktCurrent");
    ArrayList pktStkIdnList = (ArrayList)request.getAttribute("pktStkIdnList");
    HashMap totals = (HashMap)request.getAttribute("totalMap");

    if(pktList!=null && pktList.size()>0){
      ArrayList vwPrpLst= (session.getAttribute("PKTLKUP_VW") == null)?new ArrayList():(ArrayList)session.getAttribute("PKTLKUP_VW");
      int vWsize = vwPrpLst.size()+8;

    %>
    
<table  class="grid1" id="dataTable">
<tr>
<td colspan="3">
<table>
<tr>
<td>Total :&nbsp;Qty&nbsp;</td>
<td><span id="ttlqty"> <%=totals.get("qty")%></span></td>
<td>Cts&nbsp;</td>
<td><span id="ttlcts"><%=totals.get("cts")%></span></td>
</tr>
</table>
</td>
</tr>
  

     <tr>  
  <!--<tr><td><html:button property="value(excel)" value="Create Excel"  onclick="goTo('PacketLookup.do?method=createXL','','')" styleClass="submit" /> </td></tr>
  -->
  <%
      pageList= ((ArrayList)pageDtl.get("BUTTON") == null)?new ArrayList():(ArrayList)pageDtl.get("BUTTON");
      if(pageList!=null && pageList.size() >0){
      for(int j=0;j<pageList.size();j++){
      pageDtlMap=(HashMap)pageList.get(j);
      fld_typ=(String)pageDtlMap.get("fld_typ");
      if(!fld_typ.equals("HB"))
      fld_nme="value("+(String)pageDtlMap.get("fld_nme")+")";
      else
      fld_nme=(String)pageDtlMap.get("fld_nme");
      val_cond=(String)pageDtlMap.get("val_cond");
      fld_ttl=(String)pageDtlMap.get("fld_ttl");
      val_cond=(String)pageDtlMap.get("val_cond");
      flg1=(String)pageDtlMap.get("flg1");
      
      val_cond=val_cond.replaceAll("URL",info.getReqUrl());
      if(fld_typ.equals("S")){ %>
<td> <html:submit property="<%=fld_nme%>" value="<%=fld_ttl%>"  onclick="<%=val_cond%>" styleClass="submit" /></td>
      <%}else if(fld_typ.equals("B")){%>
      <td>
     Format </td>
     <td><html:select property="value(mdl)" styleId="mdlLst" name="packetLookupForm">
      <html:optionsCollection value="mdl" property="value(memoXlList)"  name="packetLookupForm" label="mdl"/>
      </html:select></td>
<td><html:button property="<%=fld_nme%>" value="<%=fld_ttl%>"  onclick="<%=val_cond%>" styleClass="submit" styleId="<%=flg1%>" />  </td>        
<%
}else if(fld_typ.equals("HB")){
%>
<td colspan="2" nowrap="nowrap"><button type="button" onclick="<%=val_cond%>" class="submit" accesskey="S" id="<%=fld_nme%>" name="<%=fld_nme%>" ><%=fld_ttl%></button></td>
<%
}}}
%> 
<%


  if(usrFlg.equals("SYS") || rolenmLst.contains("ADMIN")){
  %>
  <td><img src="../images/edit.jpg" title="Edit Properties Module" onclick="newWindow('<%=info.getReqUrl()%>/dynamicProperty.do?method=load&mdl=PKTLKUP_VW&sname=PKTLKUP_VW&par=V')" border="0" width="15px" height="15px"/></td> 
  <%}%>
</tr>
      <tr>
        <th>Sr. No.</th>
         <th>&nbsp;</th> 
        <th>Packet id</th>
       <th>Status</th>
       <%if(cnt.equalsIgnoreCase("sd")){
       vWsize++;
       %>
  <th><label title="Sal Avg">SL Avg</label></th>
   <%}%>
        
         <%for(int j=0; j < vwPrpLst.size(); j++){
            prp = (String)vwPrpLst.get(j);
            if(prpDspBlocked.contains(prp)){
            vWsize=vWsize-1;
            }else{ %>
                <th><%=prp%></th>
                <%}if(prp.equals("RTE")){%>
                <%
                pageList= ((ArrayList)pageDtl.get("HDR") == null)?new ArrayList():(ArrayList)pageDtl.get("HDR");
if(pageList!=null && pageList.size() >0){
for(int c=0;c<pageList.size();c++){
pageDtlMap=(HashMap)pageList.get(c);
fld_ttl=(String)pageDtlMap.get("fld_ttl");
%>
<th nowrap="nowrap"><%=fld_ttl%></th>
<%}}
                }}%>
      </tr>
      
      <%
     for(int m=0;m< pktStkIdnList.size();m++){ 
String stkIdn = (String)pktStkIdnList.get(m);
HashMap pktPrpMap = (HashMap)pktList.get(stkIdn);
    String vnm = (String)pktPrpMap.get("vnm");
    String upr = util.nvl((String)pktPrpMap.get("quot"));
    String cmp = util.nvl((String)pktPrpMap.get("cmp"));
    String cmpdis = util.nvl((String)pktPrpMap.get("cmp_dis"));
         String target = "TR_"+stkIdn;
         String targetSrc = "SRC_"+stkIdn;
         String sltarget = "SL_"+stkIdn;
  %>
      <tr id="<%=target%>">
      <td><%=m+1%></td>
      <%
      pageList= ((ArrayList)pageDtl.get("LINK") == null)?new ArrayList():(ArrayList)pageDtl.get("LINK");
if(pageList!=null && pageList.size() >0){%>
<td nowrap="nowrap">
<div id="common" style="visibility:visible;position:relative;" align="left" >
<ul class="css3menu11">
<li>
<a href="#">Details.....</a>
<ul>
<%


for(int j=0;j<pageList.size();j++){
pageDtlMap=(HashMap)pageList.get(j);
fld_nme="value("+(String)pageDtlMap.get("fld_nme")+")";
dflt_val=(String)pageDtlMap.get("dflt_val");
dflt_val=dflt_val.replaceAll("VNM",vnm);
dflt_val=dflt_val.replaceAll("STKIDN",stkIdn);
dflt_val=dflt_val.replaceAll("UPR",upr);
dflt_val=dflt_val.replaceAll("CMP",cmp);
dflt_val=dflt_val.replaceAll("URL",info.getReqUrl());
fld_ttl=(String)pageDtlMap.get("fld_ttl");
fld_ttl=fld_ttl.replaceAll("VNM",vnm);
val_cond=(String)pageDtlMap.get("val_cond");
val_cond=val_cond.replaceAll("TARGET",target);
val_cond=val_cond.replaceAll("VNM",vnm);
fld_typ=(String)pageDtlMap.get("fld_typ");
fld_typ=fld_typ.replaceAll("VNM",vnm);
fld_typ=fld_typ.replaceAll("VNM",vnm);
String roleStr=util.nvl((String)pageDtlMap.get("lov_qry"));
boolean isDis = true;
if(!roleStr.equals("") && !usrFlg.equals("SYS")){
isDis = false;
String [] roleLst = roleStr.split(",");
for(int i=0;i<roleLst.length;i++){
    if(rolenmLst.contains(roleLst[i])){
    isDis = true;
    break;
    }
 }
}

if(isDis){
%>
<li><A href="<%=dflt_val%>" id="<%=fld_typ%>" onclick="<%=val_cond%>" target="frame"><span><%=fld_ttl%></span></a></li>
<%}

}%>
</ul>
</li>
</ul>
</div>
</td>
<%}
%>

<%
pageList= ((ArrayList)pageDtl.get("VNMLINK") == null)?new ArrayList():(ArrayList)pageDtl.get("VNMLINK");
if(pageList!=null && pageList.size() >0){
for(int j=0;j<pageList.size();j++){
pageDtlMap=(HashMap)pageList.get(j);
fld_nme="value("+(String)pageDtlMap.get("fld_nme")+")";
dflt_val=(String)pageDtlMap.get("dflt_val");
dflt_val=dflt_val.replaceAll("VNM",vnm);
dflt_val=dflt_val.replaceAll("STKIDN",stkIdn);
dflt_val=dflt_val.replaceAll("UPR",upr);
dflt_val=dflt_val.replaceAll("CMP",cmp);
dflt_val=dflt_val.replaceAll("URL",info.getReqUrl());
fld_ttl=(String)pageDtlMap.get("fld_ttl");
fld_ttl=fld_ttl.replaceAll("VNM",vnm);
val_cond=(String)pageDtlMap.get("val_cond");
val_cond=val_cond.replaceAll("TARGET",target);
val_cond=val_cond.replaceAll("VNM",vnm);
fld_typ=(String)pageDtlMap.get("fld_typ");
fld_typ=fld_typ.replaceAll("VNM",vnm);
flg1=(String)pageDtlMap.get("flg1");
%>
<td><A href="<%=dflt_val%>" id="<%=fld_typ%>" onclick="<%=val_cond%>" target="frame"><%=fld_ttl%></a></td>
<%}}else{%>
<td><%=vnm%></td>
<%}

String nexttrns=util.nvl((String)pktPrpMap.get("stt1"));

if(nxttrns.equals("Y")){
if(nexttrns.equals("MKIS") || nexttrns.equals("MKEI")  || nexttrns.equals("MKBDIS") || nexttrns.equals("MKHS") || nexttrns.equals("MKWH") || nexttrns.equals("MKCS") || nexttrns.equals("SHIS") || nexttrns.equals("MKSA") || nexttrns.equals("MKAP") || nexttrns.equals("MKWA") || nexttrns.equals("MKSL"))
nexttrns = "<A href='"+info.getReqUrl()+"/marketing/memoPrice.do?method=loadnexttrns&stt="+nexttrns+"&stkIdn="+stkIdn+"' target=\"fixtrs\" >"+nexttrns+"</a>";
}
%>
<!--<td><A href="<%=info.getReqUrl()%>/lab/finalLabSelection.do?method=labDtl&mstkIdn=<%=stkIdn%>" onclick="setBGColor('<%=stkIdn%>','DV_')" target="<%=targetSrc%>" ><%=pktPrpMap.get("vnm")%></a></td>-->
   
<td nowrap="nowrap" title="Next Transaction"><%=nexttrns%></td>
<%if(cnt.equalsIgnoreCase("sd")){%>
<td><span  class="img"><a href="#"  title="click here for sale Avg">
<img src="../images/plus.png" id="IMG_<%=stkIdn%>" onclick="TtlBuyerSH('<%=stkIdn%>')" border="0"/></a></span></td>
<%}%>
     
      
       <% for(int j=0; j < vwPrpLst.size(); j++){
               prp = (String)vwPrpLst.get(j);
        if(prpDspBlocked.contains(prp)){
            }else{
        %>
        <td nowrap="nowrap"><%=util.nvl((String)pktPrpMap.get(prp))%></td>
       <%}if(prp.equals("RTE")){%>
         <%
               pageList= ((ArrayList)pageDtl.get("BODY") == null)?new ArrayList():(ArrayList)pageDtl.get("BODY");
if(pageList!=null && pageList.size() >0){
for(int c=0;c<pageList.size();c++){
pageDtlMap=(HashMap)pageList.get(c);
fld_ttl=util.nvl((String)(String)pageDtlMap.get("fld_ttl"));
fld_ttl=fld_ttl.replaceAll("CMP DIS",cmpdis);
fld_ttl=fld_ttl.replaceAll("CMP",cmp);
%>
<td><%=fld_ttl%></td>
<%}}
        }%>
        <%} %>
        </tr>
        <%
        String sttMsg = util.nvl((String)request.getAttribute("sttMsg"));
        if(!sttMsg.equals("")){%>
         <tr><td colspan="<%=vWsize%>"  >
         <%=sttMsg%>
         </td></tr>
        <%}%>
      <tr><td colspan="<%=vWsize%>"  style="display:none;" id="DV_<%=stkIdn%>" >
<iframe name="<%=targetSrc%>" height="320" width="800" frameborder="0">

</iframe>
</td></tr>
<%if(cnt.equalsIgnoreCase("sd")){%>
<tr id="<%=stkIdn%>" style="display:none"><td colspan="<%=vWsize%>">
<div id="<%=sltarget%>"></div>
</td></tr>
<%}%>
      <%
      }
      %>
      
      </table>
    <%}%>
    </td></tr></table>
    </td></tr>
   <tr>
    <td><jsp:include page="/footer.jsp" /> </td>
    </tr>
    </table>
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