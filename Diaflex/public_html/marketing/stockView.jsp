<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import="java.util.ArrayList,java.util.ArrayList,java.util.LinkedHashMap ,java.util.Set,ft.com.dao.GtPktDtl, java.util.List,java.io.*, java.util.HashMap, java.sql.*, java.util.Enumeration, java.util.logging.Level"%>
<jsp:useBean id="util" class="ft.com.JspUtil" scope="page" />
<jsp:useBean id="info" class="ft.com.InfoMgr" scope="session" />
<jsp:useBean id="gtMgr" class="ft.com.GtMgr" scope="session" />
<%@taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>

<title>Search Result</title>
<script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/bse.js?v=<%=info.getJsVersion()%> " > </script>
<link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/bse.css?v=<%=info.getJsVersion()%>"/>
<link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/filter.css"/>
<script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/ajax.js?v=<%=info.getJsVersion()%> " > </script>
<link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/style.css?v=<%=info.getJsVersion()%>"/>
<link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/general.css?v=<%=info.getJsVersion()%>"/>
<script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/memoScript.js?v=<%=info.getJsVersion()%> " > </script>
<script type="text/javascript" src="<%=info.getReqUrl()%>/PopScripts/popup.js?v=<%=info.getJsVersion()%> " > </script>
<script src="<%=info.getReqUrl()%>/PopScripts/jquery-1.2.6.min.js" type="text/javascript"></script>

<script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/tableFilter/actb.js " > </script>
<script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/tableFilter/tablefilter.js " > </script>
<script language="javaScript" type="text/javascript" src="<%=info.getReqUrl()%>/calendar/calendar.js"></script>
<link href="<%=info.getReqUrl()%>/calendar/calendar.css" rel="stylesheet" type="text/css" />
 <script src="<%=info.getReqUrl()%>/jqueryScript/jqueryScript.js" type="text/javascript"></script>
 <script src="<%=info.getReqUrl()%>/scripts/newCalScript.js" type="text/javascript"></script>
 


</head>
<body>
<%
 ArrayList selectCountList = new ArrayList();
String setSelectStr=" <script type=\"text/javascript\">$(document).ready(function() {";
ArrayList itemHdr = new ArrayList();
String lstNme = util.nvl((String)gtMgr.getValue("lstNme"));
String ref = util.nvl((String)request.getAttribute("REF"));
String datafrm="";
if(ref.equals("YES")){
 lstNme = lstNme+"REF";
 datafrm="REF";
 %>
 <input type="hidden" name="REF" id="REF" value="YES" />
 <%}
 String MTPR = util.nvl((String)request.getAttribute("MTPR"));
if(MTPR.equals("YES")){
 lstNme = lstNme+"_MTPR";
 datafrm="_MTPR";
 %>
 <input type="hidden" name="MTPR" id="MTPR" value="YES" />
 <%}
 
 String srt = util.nvl((String)request.getAttribute("SRT"));
if(srt.equals("YES")){
 lstNme = lstNme+"_SRT";
  datafrm="_SRT";
 }
HashMap dbsysInfo = info.getDmbsInfoLst();
ArrayList roleNmeList = info.getRoleLst();
String usrFlg=util.nvl((String)info.getUsrFlg());
String view = info.getViewForm();
HashMap dtls=new HashMap();
String srchTyp = util.nvl(info.getIsFancy());
String VISIONVD = util.nvl((String)dbsysInfo.get("VISIONVD"),"Vision360.html?sd=50&z=1&v=2&sv=0&d=");
List subStockList = new ArrayList();
HashMap ttls = (HashMap)request.getAttribute("total");
String memoTyp = util.nvl((String)session.getAttribute("SrchMemoTyp"));
ArrayList vwPrpLst = info.getVwPrpLst();
String isMix = util.nvl((String)request.getAttribute("view"));
if(isMix.equals("MIX")){
 vwPrpLst = info.getMixPrpLst();
 view = "MIX";
 }
 if(util.nvl(info.getIsMix()).equals("SMX")){
  vwPrpLst = info.getSmxPrpLst();
  view = "SMX";
  
 }
 if(util.nvl(info.getIsMix()).equals("RGH")){
  vwPrpLst = info.getRghVwList();
  view = "RGH";
  isMix="RGH";
 }
     String srchSold = util.nvl((String)request.getAttribute("SOLDSRCH"));
     if(srchSold.equals("YES")){
        vwPrpLst = info.getSoldPrpLst();
         view = "SOLD";
        }
 ArrayList sttAlwList = (ArrayList)request.getAttribute("sttAlwLst");
 if(sttAlwList==null)
 sttAlwList = new ArrayList();
HashMap mprp = info.getMprp();
 String pgDef = "SEARCH_RESULT";
   if(isMix.equals("MX") || isMix.equals("MIX"))
       pgDef = "MIXSRCH_RESULT";
   if(isMix.equals("SMX"))
       pgDef = "SMXSRCH_RESULT";
          if(isMix.equals("RGH"))
        pgDef = "RGHSRCH_RESULT"; 
    HashMap allPageDtl= (info.getPageDetails() == null)?new HashMap():(HashMap)info.getPageDetails();
    HashMap pageDtl=(HashMap)allPageDtl.get(pgDef);
    String memolmt = util.nvl(info.getMemo_lmt());
    ArrayList pageList=new ArrayList();
    HashMap pageDtlMap=new HashMap();
    String fld_nme="",fld_ttl="",val_cond="",dflt_val="",fld_typ="",form_nme="",lov_qry="",flg1="";
String cnt = util.nvl((String)info.getDmbsInfoLst().get("CNT"));
String mixrtelmt = util.nvl((String)info.getDmbsInfoLst().get("MIX_RTE_LMT"));
String mixrteext = util.nvl((String)info.getDmbsInfoLst().get("MIX_RTE_EXT"));
ArrayList imagelistDtl= (info.getImagelistDtl() == null)?new ArrayList():(ArrayList)info.getImagelistDtl();
String cb_memo = null;
int vwPrpLstsz=vwPrpLst.size();
ArrayList prpDspBlocked = info.getPageblockList();
ArrayList sortPrpList = info.getSrtPrpLst();
String selBid = "";
String selstock = "";
String offerLmt = (String)dbsysInfo.get("OFFER_LMT");
String offerLmtto = util.nvl((String)dbsysInfo.get("OFFER_LMT_TO"),"0");
String basedontyp = util.nvl((String)dbsysInfo.get("OFFER_TYP"),"PRC");
if(view.equals("BID")){
selBid = "checked=\"checked\"";
 if(lstNme.indexOf("BID")==-1)
    lstNme = "BID"+lstNme;
datafrm="BID";
}
HashMap stockViewMap = info.getStockViewMap();
HashMap vwDtl = null;
String imagePath =(String)dbsysInfo.get("IMG_PATH");
String imageDir =(String)dbsysInfo.get("IMG_DIR");
String offerLmtAllow="N";
String offerLmtAllowDis="style=\"display:none\"";
vwDtl = (HashMap)stockViewMap.get("OFFERLMTALLOW");
if(vwDtl!=null){
String isOfferHis = util.nvl((String)vwDtl.get("nme"));
if(isOfferHis.equals("Y")){
offerLmtAllow="Y";
offerLmtAllowDis="";
}}
%>
<script type="text/javascript">
window.onload=loading();
</script>
<input type="hidden" name="isMix" value="<%=isMix%>" id="isMix" />
<input type="hidden" value="<%=offerLmt%>" name="offerLmt" id="offerLmt" />
<input type="hidden" name="VIEW" id="VIEW" value="<%=view%>" />

<table>

<% 
  HashMap stockListMap = (HashMap)gtMgr.getValue(lstNme);
  LinkedHashMap stockList = new LinkedHashMap(stockListMap); 
    ArrayList stockIdnLst =new ArrayList();
 Set<String> keys = stockList.keySet();
        for(String key: keys){
       stockIdnLst.add(key);
        }
  gtMgr.setValue(lstNme+"_ALLLST", stockIdnLst);
 String filterrow="";
 int colCount=1;
if(stockIdnLst.size()>0){%>

<tr><td valign="top">
<input type="hidden" name="lstNme" id="lstNme" value="<%=lstNme%>" />



<table class="js-dynamitable dataTable" id="dataTable" align="left" width="100%">
  <thead>
<tr>
<%filterrow=filterrow+"<th><input type=\"button\" name=\"filter\" class=\"OrangeBtn\" onclick=\"ClearFilter()\" value=\"Clear Filter\"/></th>"; colCount++;%>
<th>Select All <input name="ALL" id="ALL" type="checkbox" onclick="srchChkSel(this,'ALL','<%=view%>')" /></th>
<%
vwDtl = (HashMap)stockViewMap.get("SRCHOFFER");
if(vwDtl!=null){
String offer = util.nvl((String)vwDtl.get("nme"));
if(offer.equals("Y") && !view.equals("BID") && !view.equals("MP")){%>
<%filterrow=filterrow+"<th></th>";colCount++;%>
<th>Offer</th>
<%}}

pageList= ((ArrayList)pageDtl.get("LINK") == null)?new ArrayList():(ArrayList)pageDtl.get("LINK");
if(pageList!=null && pageList.size() >0){
pageDtlMap=(HashMap)pageList.get(0);
%>
<%filterrow=filterrow+"<th></th>";colCount++;%>
<th>History</th>
<%}
if(!view.equals("MIX") && !view.equals("SMX") ){

%>
<%filterrow=filterrow+"<th></th>";colCount++;%>
<th>View Image</th>
<%}%>
<%

String actPrp =util.nvl(request.getParameter("prp"));
String ordD = util.nvl(request.getParameter("order"));
String sortImg="../images/sort_off.png";
if(ordD.equals("desc") && actPrp.equals("VNM") ){
sortImg="../images/sort_up.png";
}
if(ordD.equals("asc") && actPrp.equals("VNM")){
sortImg="../images/sort_down.png";
}
String linkAsc="StockSearch.do?method=sort&prp=VNM&order=asc&datafrm="+datafrm+"&lstNme="+lstNme;
String linkDsc = "StockSearch.do?method=sort&prp=VNM&order=desc&datafrm="+datafrm+"&lstNme="+lstNme;
String useMap = "#VNM";
 ArrayList rolenmLst=(ArrayList)info.getRoleLst();
 colCount++;
filterrow=filterrow+"<th style=\"background-color: White;\"><input  class=\"js-filter  form-control txtStyle\"  style=\"width:90px\" type=\"text\"  id=\"FR_VNM\" value=\"\"></th>";
%>

<th title="Packet No">
Packet No
<%if(view.equals("SRCH")){%>
<img src="<%=sortImg%>" id="VNM1"  hspace="2" border="0" usemap="<%=useMap%>" />
<map name="VNM" id="VNM">
<area shape="rect" coords="0,0,6,5" href="<%=linkDsc%>" />
<area shape="rect" coords="0,8,5,11" href="<%=linkAsc%>" />
 <%}%>
</map>
</th>

<%if(view.equals("BID")){%>
<th>Valid Till</th>
<th>Offer Price</th>
<th>Offer Discount</th>
<th>Offer Amount</th>
<th>Comment</th>
<th <%=offerLmtAllowDis%>>Offer Limit</th>
<%filterrow=filterrow+"<th></th><th></th><th></th><th></th><th></th><th></th>";colCount=colCount+6;%>
<%
}%>
<th><label title="Status">Status</label>
<%  

setSelectStr=setSelectStr+"useNthColumn('"+colCount+"','FR_STT');" ;
colCount++;
filterrow=filterrow+"<th><select id=\"FR_STT\" class=\"js-filter  form-control txtStyle\"><option value=\"\"></option></select>  </th>";%>
<%if(view.equals("SRCH")){
linkAsc="StockSearch.do?method=sort&prp=DSP_STT&order=asc&datafrm="+datafrm;
linkDsc = "StockSearch.do?method=sort&prp=DSP_STT&order=desc&datafrm="+datafrm;
useMap = "#DSP_STT";
sortImg="../images/sort_off.png";
if(ordD.equals("desc") && actPrp.equals("DSP_STT") ){
sortImg="../images/sort_up.png";
}
if(ordD.equals("asc") && actPrp.equals("DSP_STT")){
sortImg="../images/sort_down.png";
}%>
<img src="<%=sortImg%>" id="DSP_STT"  hspace="2" border="0" usemap="<%=useMap%>" />
<map name="DSP_STT" id="DSP_STT">
<area shape="rect" coords="0,0,6,5" href="<%=linkDsc%>" />
<area shape="rect" coords="0,8,5,11" href="<%=linkAsc%>" />
 <%}%>
 </map>
</th>
<%
HashMap hdrDtl = new HashMap();
 hdrDtl.put("hdr", "vnm");
  hdrDtl.put("typ", "N");
  hdrDtl.put("htyp", "C");
  itemHdr.add(hdrDtl);
  
 hdrDtl = new HashMap();
 hdrDtl.put("hdr", "stt");
  hdrDtl.put("typ", "N");
  hdrDtl.put("htyp", "C");
  itemHdr.add(hdrDtl);


vwDtl = (HashMap)stockViewMap.get("MATPR");
if(vwDtl!=null){
String isMAt = util.nvl((String)vwDtl.get("nme"));
if(isMAt.equals("Y")){
if(!view.equals("MP")){
if(cnt.equals("kj")){%>

<th><label title="Similar">Similar</label></th><%filterrow=filterrow+"<th></th>";colCount++;%>

<%}else{%>
<th><label title="Match Pair">Match Pair</label></th><%filterrow=filterrow+"<th></th>";colCount++;%>
<%}}}}%>
<%if(view.equals("MIX") || util.nvl(info.getIsMix()).equals("RGH")){
 hdrDtl = new HashMap();
 hdrDtl.put("hdr", "qty");
  hdrDtl.put("typ", "N");
  hdrDtl.put("htyp", "C");
  itemHdr.add(hdrDtl);
  
   hdrDtl = new HashMap();
  hdrDtl.put("hdr", "cts");
  hdrDtl.put("typ", "N");
  hdrDtl.put("htyp", "N");
  itemHdr.add(hdrDtl);
  vwDtl = (HashMap)stockViewMap.get("SPLIT");
if(vwDtl!=null){
%><th>Split</th><%filterrow=filterrow+"<th></th>";colCount++;%><%}%>
<th colspan="2">qty</th><%filterrow=filterrow+"<th></th><th></th>";colCount++;colCount++;  %>
<th colspan="2">Cts</th><%filterrow=filterrow+"<th></th><th></th>";colCount++;colCount++;  %>

<%}%>
<%for(int j=0; j < vwPrpLstsz; j++ ){
String prp = (String)vwPrpLst.get(j);

String hdr = (String)mprp.get(prp);
String prpDsc = (String)mprp.get(prp+"P");
String prpTyp = util.nvl((String)mprp.get(prp+"T"));
String filterStr="";
if(prpTyp.equals("C")){

setSelectStr=setSelectStr+"useNthColumn('"+colCount+"','FR_"+prp+"');";
filterStr="<th style=\"background-color: White;\"><select id=\"FR_"+prp+"\" class=\"js-filter  form-control txtStyle\"><option value=\"\"></option></select></th>";
colCount++;
}else{

filterStr="<th  style=\"background-color: White;\"><input class=\"js-filter   form-control txtStyle\" style=\"width:50px\" type=\"text\"  id=\"FR_"+prp+"\" value=\"\"></th>";
colCount++;
}



if(hdr == null) {
hdr = prp;
}
if(prpDspBlocked.contains(prp)){
colCount--;
}else if(sortPrpList.contains(prp)){
sortImg="../images/sort_off.png";
if(actPrp.equals(prp) && ordD.equals("desc") ){
sortImg="../images/sort_up.png";
}
if(actPrp.equals(prp) && ordD.equals("asc") ){

sortImg="../images/sort_down.png";
}
linkAsc="StockSearch.do?method=sort&prp="+prp+"&order=asc&datafrm="+datafrm;
linkDsc = "StockSearch.do?method=sort&prp="+prp+"&order=desc&datafrm="+datafrm;
useMap = "#"+prp;
%>

<th title="<%=prpDsc%>">
<%filterrow=filterrow+filterStr;%>
<%=hdr%>
<%if(view.equals("SRCH")){%>
<img src="<%=sortImg%>" id="<%=prp%>1"  hspace="2" border="0" usemap="<%=useMap%>" />
<map name="<%=prp%>" id="<%=prp%>">

<area shape="rect" coords="0,0,6,5" href="<%=linkDsc%>" />
<area shape="rect" coords="0,8,5,11" href="<%=linkAsc%>" />

 <%}%>

</map>


</th>

 <%
 
      hdrDtl = new HashMap();
      hdrDtl.put("hdr", prp);
      hdrDtl.put("typ", "P");
      hdrDtl.put("dp", "P");
      hdrDtl.put("htyp",prpTyp);
       itemHdr.add(hdrDtl); 
 if(prp.equals("RAP_DIS")){%>

<%vwDtl = (HashMap)stockViewMap.get("ISCMPDIS");
 if(vwDtl!=null){
 String byrIdn = util.nvl((String)vwDtl.get("url"));
 if(byrIdn.equals(String.valueOf(info.getByrId()))){
     hdrDtl = new HashMap();
      hdrDtl.put("hdr", "cmp_dis");
      hdrDtl.put("typ", "P");
      hdrDtl.put("dp", "P");
      hdrDtl.put("htyp","N");
       itemHdr.add(hdrDtl); 
 %>
 <%filterrow=filterrow+"<th style=\"background-color: White;\"><input  class=\"js-filter  form-control txtStyle\" style=\"width:90px\" type=\"text\"  id=\"FR_"+prp+"\" value=\"\"></th>";colCount++;%>
 <th title="<%=prpDsc%>" style="cursor: pointer;" >Cmp Dis</th>
 <%}}%>

<%}else if (prp.equals("RAP_RTE")){
vwDtl = (HashMap)stockViewMap.get("ISRAPVAL");
if(vwDtl!=null){
String isRV = util.nvl((String)vwDtl.get("nme"));
if(isRV.equals("Y")){
  hdrDtl = new HashMap();
      hdrDtl.put("hdr", "RAPVAL");
      hdrDtl.put("typ", "N");
      hdrDtl.put("dp", "P");
      hdrDtl.put("htyp","N");
       itemHdr.add(hdrDtl); 
      
%>
  <%filterrow=filterrow+"<th style=\"background-color: White;\"><input  class=\"js-filter  form-control txtStyle\" style=\"width:90px\" type=\"text\"  id=\"FR_"+prp+"\" value=\"\"></th>";colCount++;%>

<th title="Amount" >Rap Vlu</th>
<%}}
}else if(prp.equals("RTE")){
      hdrDtl = new HashMap();
      hdrDtl.put("hdr", "AMT");
      hdrDtl.put("typ", "N");
      hdrDtl.put("dp", "P");
      hdrDtl.put("htyp","N");
       itemHdr.add(hdrDtl); 
    %>
 <%vwDtl = (HashMap)stockViewMap.get("ISCMP");
 if(vwDtl!=null){
  String byrIdn = util.nvl((String)vwDtl.get("url"));
 if(byrIdn.equals(String.valueOf(info.getByrId()))){
      hdrDtl = new HashMap();
      hdrDtl.put("hdr", "cmp");
      hdrDtl.put("typ", "N");
      hdrDtl.put("dp", "P");
      hdrDtl.put("htyp","N");
       itemHdr.add(hdrDtl); 
 %>
 <%filterrow=filterrow+"<th style=\"background-color: White;\"><input  class=\"js-filter  form-control txtStyle\" style=\"width:90px\" type=\"text\"  id=\"FR_"+prp+"\" value=\"\"></th>";colCount++;%>
 <th title="<%=prpDsc%>" style="cursor: pointer;">Cmp</th>
 <%}}
 vwDtl = (HashMap)stockViewMap.get("ISAMT");
if(vwDtl!=null){
String isAMT = util.nvl((String)vwDtl.get("nme"));
if(isAMT.equals("Y")){
 
 filterrow=filterrow+"<th style=\"background-color: White;\"><input  class=\"js-filter  form-control txtStyle\" style=\"width:90px\" type=\"text\"  id=\"FR_"+prp+"\" value=\"\"></th>";     

%>
<th title="Amount" >Amount</th>
<%}}
 }else if(prp.equals("CP")){
 vwDtl = (HashMap)stockViewMap.get("ISCPDIS");
if(vwDtl!=null){
String ISCPDIS = util.nvl((String)vwDtl.get("nme"));
if(ISCPDIS.equals("Y")){
   hdrDtl = new HashMap();
      hdrDtl.put("hdr", "cpDis");
      hdrDtl.put("typ", "N");
      hdrDtl.put("dp", "P");
      hdrDtl.put("htyp","N");
       itemHdr.add(hdrDtl); 
       colCount++;
filterrow=filterrow+"<th style=\"background-color: White;\"><input  class=\"js-filter  form-control\" type=\"text\"  id=\"FR_"+prp+"\" value=\"\"></th>";
%>
<th title="CP Discount " >CP Dis</th>
<%}}}
}else if(prp.equals("RTE")){
      hdrDtl = new HashMap();
      hdrDtl.put("hdr", "RTE");
      hdrDtl.put("typ", "P");
      hdrDtl.put("dp", "P");
      hdrDtl.put("htyp","N");
       itemHdr.add(hdrDtl); 
     
      hdrDtl = new HashMap();
      hdrDtl.put("hdr", "AMT");
      hdrDtl.put("typ", "N");
      hdrDtl.put("dp", "P");
      hdrDtl.put("htyp","N");
       itemHdr.add(hdrDtl); 

filterrow=filterrow+"<th style=\"background-color: White;\"><input  class=\"js-filter  form-control txtStyle\" style=\"width:90px\" type=\"text\"  id=\"FR_"+prp+"\" value=\"\"></th>";
colCount++;
%>
<th title="<%=prpDsc%>" style="cursor: pointer;" ><%=hdr%></th>
 <% vwDtl = (HashMap)stockViewMap.get("ISCMP");
 if(vwDtl!=null){
  String byrIdn = util.nvl((String)vwDtl.get("url"));
 if(byrIdn.equals(String.valueOf(info.getByrId()))){
      hdrDtl = new HashMap();
      hdrDtl.put("hdr", "cmp");
      hdrDtl.put("typ", "N");
      hdrDtl.put("dp", "P");
      hdrDtl.put("htyp","N");
       itemHdr.add(hdrDtl); 
       colCount++;
filterrow=filterrow+"<th style=\"background-color: White;\"><input  class=\"js-filter  form-control txtStyle\" style=\"width:90px\" type=\"text\"  id=\"FR_"+prp+"\" value=\"\"></th>";
 %>
 <th title="<%=prpDsc%>" style="cursor: pointer;">Cmp</th>
 <%}}
 vwDtl = (HashMap)stockViewMap.get("ISAMT");
if(vwDtl!=null){
String isAMT = util.nvl((String)vwDtl.get("nme"));
if(isAMT.equals("Y")){
 
    filterrow=filterrow+"<th style=\"background-color: White;\"><input  class=\"js-filter  form-control txtStyle\" style=\"width:90px\" type=\"text\"  id=\"FR_"+prp+"\" value=\"\"></th>";  

%>
<th title="Amount" >Amount</th>
<%}}
 }else if(prp.equals("CP")){
 vwDtl = (HashMap)stockViewMap.get("ISCPDIS");
if(vwDtl!=null){
String ISCPDIS = util.nvl((String)vwDtl.get("nme"));
if(ISCPDIS.equals("Y")){
   hdrDtl = new HashMap();
      hdrDtl.put("hdr", "cpDis");
      hdrDtl.put("typ", "N");
      hdrDtl.put("dp", "P");
      hdrDtl.put("htyp","N");
       itemHdr.add(hdrDtl); 
       colCount++;
filterrow=filterrow+"<th style=\"background-color: White;\"><input  class=\"js-filter  form-control txtStyle\" style=\"width:90px\" type=\"text\"  id=\"FR_"+prp+"\" value=\"\"></th>";
%>
<th title="CP Discount " >CP Dis</th>
<%}}%>
 <th title="<%=prpDsc%>" style="cursor: pointer;" ><%=hdr%></th>
 
 <%
    hdrDtl = new HashMap();
      hdrDtl.put("hdr", "CP");
      hdrDtl.put("typ", "N");
      hdrDtl.put("dp", "P");
      hdrDtl.put("htyp","N");
       itemHdr.add(hdrDtl); 
 vwDtl = (HashMap)stockViewMap.get("ISCPTOTAL");
if(vwDtl!=null){
String ISCPTOTAL = util.nvl((String)vwDtl.get("nme"));
if(ISCPTOTAL.equals("Y")){
 hdrDtl = new HashMap();
      hdrDtl.put("hdr", "cpTotal");
      hdrDtl.put("typ", "N");
      hdrDtl.put("dp", "P");
      hdrDtl.put("htyp","N");
       itemHdr.add(hdrDtl); 

filterrow=filterrow+"<th style=\"background-color: White;\"><input  class=\"js-filter  form-control txtStyle\" style=\"width:90px\"type=\"text\"  id=\"FR_"+prp+"\" value=\"\"></th>";

%>
<th title="Amount" >Amount</th>
<%}}
 }else{
   hdrDtl = new HashMap();
      hdrDtl.put("hdr", prp);
      hdrDtl.put("typ", "P");
      hdrDtl.put("dp", "P");
      hdrDtl.put("htyp",prpTyp);
       itemHdr.add(hdrDtl); 
filterrow=filterrow+filterStr;
 %>
<th title="<%=prpDsc%>" style="cursor: pointer;"><%=hdr%></th>
<%}
if(view.equals("SMX")  && prp.equals("CRTWT") && cnt.equals("kj")){%><th>Weight Change</th>
 <%}
}
vwDtl = (HashMap)stockViewMap.get("ISPKTRMK");
if(vwDtl!=null && view.equals("MIX")){
colCount++;
filterrow=filterrow+"<th></th>";
%>
<th>Remark</th>
<%}
if(!view.equals("MIX")){
hdrDtl = new HashMap();
      hdrDtl.put("hdr", "RAPVAL");
      hdrDtl.put("typ", "N");
      hdrDtl.put("dp", "P");
      hdrDtl.put("htyp","N");
       itemHdr.add(hdrDtl); 
       }

%>

<% colCount++; %>
<th>&nbsp;&nbsp;</th><%filterrow=filterrow+"<th></th></tr>";%>
</tr>
<%
pageList= ((ArrayList)pageDtl.get("HIDRSLT_FILTTER") == null)?new ArrayList():(ArrayList)pageDtl.get("HIDRSLT_FILTTER");
if(pageList==null){%>
<!---row for filter  -->
<%=filterrow%>
<!---row for filter end -->
<%}%>


</thead>
<tbody>
<%
 
String lapPrp = (String)dbsysInfo.get("LAB");
HashMap pepMap = (HashMap)request.getAttribute("bidMap");
HashMap xlFormat = new HashMap();
String tableTD=null;
String pStkIdn = "";
int count = 1;
for(int m=0;m< stockIdnLst.size();m++){
String cbMemoId="";
int tdcount=3;
if(!view.equals("BID")){
tdcount++;
}
String certNoLink = util.nvl((String)xlFormat.get("CERTNOLINK")).trim();
String stkIdn = (String)stockIdnLst.get(m);
String displayNone= "";
String disabled="";

tableTD="even";

String macthFrm = "PAIR_"+stkIdn;

GtPktDtl pktDtl = (GtPktDtl)stockList.get(stkIdn);

if(pStkIdn.equals(stkIdn)){
displayNone = "style=\"display:none\"";
}
pStkIdn = stkIdn;
String pairId = (String) pktDtl.getValue("pairId");
String cla = (String) pktDtl.getValue("class");
String stt = (String) pktDtl.getValue("stt1");
String cts = (String) pktDtl.getValue("cts");
String fl=(String)pktDtl.getValue("flg");
String certNo = (String) pktDtl.getValue("cert_no");
String prte = util.nvl((String)pktDtl.getValue("cpRte"));
if(prpDspBlocked.contains("CP"))
  prte="NA";
String prtetotal = util.nvl((String)pktDtl.getValue("cpTotal"));
String newHoldClass = "",color="";
cbMemoId = "cb_memo_"+stt+"_"+stkIdn;
if(!sttAlwList.contains(stt))
disabled = "disabled=\"disabled\"";

if(memoTyp.equals("Z"))
 disabled = "";
if(fl.equals("M"))
 selstock = "checked=\"checked\"";
else
selstock = "";
boolean isEdit = false;
cb_memo = "cb_memo_"+stkIdn;
String quot_hidden = "quot_"+stkIdn;
String stt_flg = "stt_"+stkIdn;
String cert_lab = util.nvl((String) pktDtl.getValue("cert_lab"));

String vnm = (String) pktDtl.getValue("vnm");
String rte =  (String) pktDtl.getValue("RTE");
String dis =  (String) pktDtl.getValue("RAP_DIS");
String cmp =  (String) pktDtl.getValue("cmp");
String upr =  (String) pktDtl.getValue("quot");
String target = "TR_"+stkIdn;
String targetSrc = "SRC_"+stkIdn;
String sltarget = "SL_"+stkIdn;
String onChangePrp = "onchange=\"checkPepPrice('"+stkIdn+"','"+rte+"','','"+offerLmt+"','"+offerLmtto+"','"+basedontyp+"')\"";
String onChangeDis = "onchange=\"checkPepDis('"+stkIdn+"','"+rte+"','','"+offerLmt+"','"+offerLmtto+"','"+basedontyp+"')\"";
String onChangeMemoPrp = "onchange=\"checkMemoPrice('"+stkIdn+"','"+rte+"','','"+offerLmt+"')\"";
String onChangeMemoDis = "onchange=\"checkMemoDis('"+stkIdn+"','"+rte+"','','"+offerLmt+"')\"";
String onChangeMyRteOffer = "onchange=\"checkMyOfferPrice('"+stkIdn+"','"+cmp+"')\"";
String onChangeMyDisOffer = "onchange=\"checkMyOfferDis('"+stkIdn+"','"+cmp+"')\"";
if(stt.equals("MKPP")||stt.equals("MKLB")){
  onChangePrp = "onchange=\"checkPepPriceBID('"+stkIdn+"','"+rte+"','')\"";
  onChangeDis = "onchange=\"checkPepDisBID('"+stkIdn+"','"+rte+"','')\"";
  isEdit = true;
  }
String biddte = "" , bidRte = "" , bidDsc="" , bidcomm="",bidlmtRte="",bidfrmDte="",offRte = "" ,offDsc="",offerCol="",offerlmtRte="";
if(pepMap!=null){
  HashMap pepPkt = (HashMap)pepMap.get(stkIdn);
   if(pepPkt!=null){
   if(!stt.equals("MKIS") && !stt.equals("MKEI") && !stt.equals("SHIS") && !stt.equals("MKAP") && !stt.equals("MKWA") && !stt.equals("MKSA"))
    cla = "color:rgb(255,132,0)";
      biddte = util.nvl((String)pepPkt.get("toDte"));
      bidRte = util.nvl((String)pepPkt.get("rte"));
      bidDsc = util.nvl((String)pepPkt.get("dis"));
      bidcomm = util.nvl((String)pepPkt.get("rmk"));
      bidlmtRte = util.nvl((String)pepPkt.get("lmtRte"));
      bidfrmDte = util.nvl((String)pepPkt.get("frmDte"));
}}
offRte=util.nvl((String) pktDtl.getValue("ofr_rte"));
offDsc=util.nvl((String) pktDtl.getValue("ofr_dis"));
offerCol=util.nvl((String) pktDtl.getValue("offerCol"));
offerlmtRte=util.nvl((String) pktDtl.getValue("offerlmtRte"));
offerCol="background-color:"+util.nvl((String) pktDtl.getValue("offerCol"));
String gtFlg ="Z";
if(view.equals("MP"))
 gtFlg = "P";
if(view.equals("BID"))
disabled = "";
if(fl.equals("M")){
cla="background-color:#FFC977";
}
String saleHistory="";
vwDtl = (HashMap)stockViewMap.get("SALEHISTORY");
if(vwDtl!=null){
String isSaleHis = util.nvl((String)vwDtl.get("nme"));
if(isSaleHis.equals("Y")){
saleHistory=" salehistory('"+stkIdn+"')";
}}
String offerHistory="";
vwDtl = (HashMap)stockViewMap.get("OFFERHISTORY");
if(vwDtl!=null){
String isOfferHis = util.nvl((String)vwDtl.get("nme"));
if(isOfferHis.equals("Y")){
offerHistory="offerhistory('"+stkIdn+"','"+offerLmtAllow+"'); ";
}}
String getHoldByr = "getHoldByr('"+vnm+"','"+stkIdn+"','IS','"+prte+"'); "+offerHistory+saleHistory;
if(stt.equals("MKAP") ||stt.equals("MKWA") )
getHoldByr = "getHoldByr('"+vnm+"','"+stkIdn+"','AP','"+prte+"'); "+offerHistory+saleHistory;

%>
<tr class="<%=tableTD%>" style="<%=cla%>" id="<%=stkIdn%>">
<td style="text-align:left;">
<input type="hidden" name="cb_pr_<%=stkIdn%>" id="cb_pr_<%=stkIdn%>" value="<%=pairId%>"/>
<input type="hidden" name="vnm_<%=stkIdn%>" id="vnm_<%=stkIdn%>" value="<%=vnm%>"/>
<input type="hidden" name="ctsval_<%=stkIdn%>" id="ctsval_<%=stkIdn%>" value="<%=cts%>"/>
<input type="hidden" name="<%=stt_flg%>" id="STT_<%=stkIdn%>" value="<%=stt%>"/>
<input type="hidden" name="dis_<%=stkIdn%>" id="dis_<%=stkIdn%>" value="<%=dis%>" />
<input type="hidden" name="rap_<%=stkIdn%>" id="rap_<%=stkIdn%>" value="<%=pktDtl.getValue("rap_rte")%>" />
<% if(util.nvl(info.getIsMix()).equals("RGH")){%>
<input type="radio"  style="margin-left:30px;" name="cb_memo_" id="<%=cbMemoId%>"   value="<%=stkIdn%>" onclick="srchChkSel(this,'<%=stkIdn%>','<%=view%>');setBGColorOnCHK(this,'<%=stkIdn%>')" /> 

<%}else{
if(cnt.equalsIgnoreCase("kj")){%>
<input type="checkbox" <%=selBid%> <%=selstock%> style="margin-left:30px;" name="<%=cb_memo%>" id="<%=cbMemoId%>" <%=disabled%>  value="<%=stkIdn%>" onclick="srchChkSelPair(this,'<%=stkIdn%>','<%=view%>');setBGColorOnCHK(this,'<%=stkIdn%>')" /> 

<%}else{
%>
<input type="checkbox" <%=selBid%> <%=selstock%> style="margin-left:30px;" name="<%=cb_memo%>" id="<%=cbMemoId%>" <%=disabled%>  value="<%=stkIdn%>" onclick="srchChkSel(this,'<%=stkIdn%>','<%=view%>');setBGColorOnCHK(this,'<%=stkIdn%>')" /> 
<%}}%>
<img src="../images/plus.png" border="0" onclick="<%=getHoldByr%>"/>
</td>
<%
vwDtl = (HashMap)stockViewMap.get("SRCHOFFER");
if(vwDtl!=null){
String offer = util.nvl((String)vwDtl.get("nme"));
if(offer.equals("Y") && !view.equals("BID") && !view.equals("MP")){%>
<td><input type="checkbox" name="cb_memo_offer_<%=stkIdn%>" id="cb_memo_offer_<%=stkIdn%>" onchange="offerdisplay('<%=stkIdn%>')" value="<%=stkIdn%>"></td>
<%}} %>
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


for(int i=0;i<pageList.size();i++){
pageDtlMap=(HashMap)pageList.get(i);
fld_nme="value("+(String)pageDtlMap.get("fld_nme")+")";
dflt_val=util.nvl((String)pageDtlMap.get("dflt_val"));
dflt_val=dflt_val.replaceAll("VNM",vnm);
dflt_val=dflt_val.replaceAll("STKIDN",stkIdn);
dflt_val=dflt_val.replaceAll("UPR",upr);
dflt_val=dflt_val.replaceAll("CMP",cmp);
dflt_val=dflt_val.replaceAll("URL",info.getReqUrl());
fld_ttl=(String)pageDtlMap.get("fld_ttl");
fld_ttl=fld_ttl.replaceAll("VNM",vnm);
val_cond=util.nvl((String)pageDtlMap.get("val_cond"));
val_cond=val_cond.replaceAll("TARGET",stkIdn);
val_cond=val_cond.replaceAll("VNM",vnm);
fld_typ=util.nvl((String)pageDtlMap.get("fld_typ"));
fld_typ=fld_typ.replaceAll("VNM",vnm);
fld_typ=fld_typ.replaceAll("VNM",vnm);
String roleStr=util.nvl((String)pageDtlMap.get("lov_qry"));
boolean isDis = true;


if(isDis){
%>
<li><A href="<%=dflt_val%>" id="<%=fld_typ%>" onclick="<%=val_cond%>" target="SC"><span><%=fld_ttl%></span></a></li>
<%}

}%>
</ul>
</li>
</ul>
</div>
</td>

<%}%>


<%
if(!view.equals("MIX") && !view.equals("SMX")){%>
<td>
<%if(imagelistDtl!=null && imagelistDtl.size() >0){
for(int j=0;j<imagelistDtl.size();j++){
dtls=new HashMap();
dtls=(HashMap)imagelistDtl.get(j);
String typ=util.nvl((String)dtls.get("TYP"));
String allowon=util.nvl((String)dtls.get("ALLOWON"));
if(typ.equals("I") && (allowon.indexOf("SRCH") > -1)){
String path=util.nvl((String)dtls.get("PATH"));
String gtCol=util.nvl((String)dtls.get("GTCOL"));
String val=util.nvl((String)pktDtl.getValue(gtCol));
String url ="";
if(!val.equals("N")){
if(path.indexOf("segoma") > -1 || path.indexOf("sarineplatform") > -1){
path=path+val;
url = "<A href='"+path+"' target=\"_blank\" ><img src=\"../images/segoma.jpg\" border=\"0\" /></a>";

}else{
path=path+val;
url="<A href='"+path+"' target=\"_blank\" ><img src=\"../images/viewimage_dia.png\" border=\"0\" /></a>";

}
 %>
<%=url%>
<%}}else if(typ.equals("V") && (allowon.indexOf("SRCH") > -1)){
String path=util.nvl((String)dtls.get("PATH"));
String gtCol=util.nvl((String)dtls.get("GTCOL"));
String val=util.nvl((String)pktDtl.getValue(gtCol));
if(!val.equals("N")){
path=path+val;
if(val.indexOf("json") > -1) {
String imgPath = util.nvl((String)info.getDmbsInfoLst().get("IMG_PATH"));
                String conQ=vnm;
                if(val.indexOf("/") > -1){
                conQ=val.substring(0,val.indexOf("/"));
                }
path = imgPath+""+VISIONVD+""+conQ;
}
String url = "<A href='"+path+"' target=\"_blank\" ><img src=\"../images/video.png\" border=\"0\" /></a>";
%>
<%=url%>
<%}
}else if(typ.equals("S") && (allowon.indexOf("SRCH") > -1)){
String path=util.nvl((String)dtls.get("PATH"));
String gtCol=util.nvl((String)dtls.get("GTCOL"));
String val=util.nvl((String)pktDtl.getValue(gtCol));
String url = "";
if(!val.equals("N")){
if(path.indexOf("segoma") > -1 || path.indexOf("sarineplatform") > -1){
path=path+val;
url = "<A href='"+path+"' target=\"_blank\" ><img src=\"../images/segoma.jpg\" border=\"0\" /></a>";

}else{
path="../zoompic.jsp?fileNme="+path+val+"&ht=600&cnt="+cnt;
url = "<A href='"+path+"' target=\"_blank\" ><img src=\"../images/viewimage_dia.png\" border=\"0\" /></a>";

}
%>
<%=url%>
<%}
}}}%>
</td>
<%}%>

<td <%=newHoldClass%>>
<!--<%=(String) pktDtl.getValue("vnm")%>-->
<%String vnmid=(String) pktDtl.getValue("vnm");%>
<A href="StockSearch.do?method=details&mstkidn=<%=stkIdn%>&vnm=<%=vnmid%>" id="DTL_<%=stkIdn%>"  target="SC" title="Click Here To See Details" onclick="loadASSFNL('<%=stkIdn%>','DTL_<%=stkIdn%>')">
<%=vnmid%></a>

</td>
<%if(view.equals("BID")){

%>
<td>
<%
if(isEdit || (biddte.equals(""))){
%>
<input type="text" name="DTE_<%=stkIdn%>" id="DTE_<%=stkIdn%>" size="7" value="<%=biddte%>"  />  <a href="#"  onClick="setYears(1900, <%=info.getCurrentYear()%>);showCalender(this, 'DTE_<%=stkIdn%>');">
                <img src="<%=info.getReqUrl()%>/images/calender.png"></a>
<%}else{%>
<b><%=biddte%></b>
<%}%>
</td>
<td style="<%=offerCol%>">

<%
if(isEdit || bidRte.equals("")  || (!offRte.equals("") && bidRte.equals(""))){
if(!offRte.equals(""))
bidRte=offRte;
%>
<input type="text" id="bid_prc_<%=stkIdn%>"   name="bid_prc_<%=stkIdn%>" value="<%=bidRte%>"  size="7" <%=onChangePrp%> />
<input type="hidden" name="<%=quot_hidden%>" id="<%=quot_hidden%>" value="<%=rte%>"/>

<input type="hidden" name="bid_vnm_<%=stkIdn%>" value="<%=vnm%>"/>
<input type="hidden" name="bid_dis_<%=stkIdn%>" id="bid_dis_<%=stkIdn%>" value="" />
<%}else{%>
<b><%=bidRte%></b>
<%}%>
</td>
<td>
<%
if(isEdit || bidDsc.equals("") || (!offDsc.equals("") && bidDsc.equals(""))){
if(!offDsc.equals(""))
bidDsc=offDsc;
%>
<input type="text" id="bid_dis_txt_<%=stkIdn%>" value="<%=bidDsc%>"   name="bid_dis_txt_<%=stkIdn%>"   size="7" <%=onChangeDis%> />
<%}else{%>
<b><%=bidDsc%></b>
<%}%>
</td>

<%
if(isEdit || (bidcomm.equals(""))){
%>
<td><input type="text" id="bid_amount_txt_<%=stkIdn%>" readonly="readonly"   name="bid_amount_txt_<%=stkIdn%>"   size="7" <%=onChangeDis%> /></td>
<td><input type="text" size="25" id="comm_<%=stkIdn%>"  value="<%=bidcomm%>" name="comm_<%=stkIdn%>" />
</td><%}else{%>
<td><b><%=bidcomm%></b></td>
<%}%>

<td <%=offerLmtAllowDis%>>
<%
if(isEdit || bidlmtRte.equals("") || (!offerlmtRte.equals("") && bidlmtRte.equals(""))){
if(!offerlmtRte.equals(""))
bidlmtRte=offerlmtRte;
%>
<input type="text" size="25" id="lmtRte_<%=stkIdn%>"  value="<%=bidlmtRte%>" name="lmtRte_<%=stkIdn%>" />
<%}else{%>
<b><%=bidlmtRte%></b>
<%}%>
</td>
<%}%>
<td><%=(String)pktDtl.getValue("stt")%></td>
<%
vwDtl = (HashMap)stockViewMap.get("MATPR");
if(vwDtl!=null){
String isMAt = util.nvl((String)vwDtl.get("nme"));
if(isMAt.equals("Y")){
if(!view.equals("MP")){%>
<td>
<a  onclick="WindowName('StockSearch.do?method=pairsrch&mstkIdn=<%=stkIdn%>','MATPR')" >
<%if(cnt.equals("kj")){%>
Similar
<%}else{%>
Match Pair
<%}%>
</a>
</td>
<%}}}%>
<%if(view.equals("MIX") || util.nvl(info.getIsMix()).equals("RGH")){
String mixcts=(String)pktDtl.getValue("cts");
String mixqty=(String)pktDtl.getValue("qty");
double mcts=0.0;
String dfgetpcs="";
if(info.getDmbsInfoLst().get("CNT").equals("hk")){
dfgetpcs="dfgetpcs('"+stkIdn+"');";
}
if(!mixcts.equals(""))
mcts=util.Round(Double.parseDouble(mixcts.trim())+Double.parseDouble(mixrteext),2);
  vwDtl = (HashMap)stockViewMap.get("SPLIT");
if(vwDtl!=null){
%>
<td><span class="txtLink" onclick="ShowHIDDiv('TRSP_<%=stkIdn%>')">SPLIT</span> </td>
<%}%>
<td><%=mixqty%></td>
<td>
<input type="hidden" name="cur_qty_<%=stkIdn%>" id="cur_qty_<%=stkIdn%>" value="<%=mixqty%>" />
<input type="text" onkeypress="return disableEnterKey(event);" size="8" id="qty_<%=stkIdn%>"   name="qty_<%=stkIdn%>" value="<%=mixqty.trim()%>"  onchange="updateGtCTSQt('<%=stkIdn%>','qty_<%=stkIdn%>','QTY','<%=mixqty%>','<%=mixrtelmt%>')" /></td>
<td><%=mixcts%>
</td>
<td>
<input type="hidden" name="cur_cts_<%=stkIdn%>" id="cur_cts_<%=stkIdn%>" value="<%=mixcts%>" />
<input type="text" onkeypress="return disableEnterKey(event);" size="8" id="cts_<%=stkIdn%>"  name="cts_<%=stkIdn%>" value="<%=mixcts.trim()%>" onchange="updateGtCTSQt('<%=stkIdn%>','cts_<%=stkIdn%>','CTS','<%=mcts%>','<%=mixrtelmt%>');<%=dfgetpcs%>" /></td>
<%}%>
<%
for(int l=0;l<vwPrpLstsz;l++){
String prp = (String)vwPrpLst.get(l);
String prpValue = (String) pktDtl.getValue(prp);
if(prpValue.equals("NA"))
prpValue = "";
String prpDsc = (String)mprp.get(prp+"D");
color=util.nvl((String)dbsysInfo.get("COLOR_"+prp));
if(prpDspBlocked.contains(prp)){
}else if(prp.equals("RAP_DIS")){ 
tdcount++;%>
<td title="<%=prpDsc%>" style="<%=color%>"><b><%=prpValue%></b>
<%if((roleNmeList!=null && roleNmeList.contains("PRIEDIT"))){%>
<input type="text" size="8" id="memo_dis_<%=stkIdn%>" value="<%=dis%>"   name="memo_dis_<%=stkIdn%>" <%=onChangeMemoDis%> /> 
<%}%>
<%if(cnt.equalsIgnoreCase("sd") && view.equals("BID")){ %>
<input type="text" size="8" id="BIDDis_<%=stkIdn%>"   name="BIDDis_<%=stkIdn%>" <%=onChangeMyDisOffer%> /> 
<%}%>
</td>
<%vwDtl = (HashMap)stockViewMap.get("ISCMPDIS");
 if(vwDtl!=null){
  String byrIdn = util.nvl((String)vwDtl.get("url"));
 if(byrIdn.equals(String.valueOf(info.getByrId()))){
 tdcount=tdcount+1;
 %>
 <td title="<%=prpDsc%>" style="cursor: pointer;"><%=pktDtl.getValue("cmp_dis")%></td>
 <%}}%>
<%}else if(prp.equals("RTE") || prp.equals("MIX_RTE")){tdcount=tdcount+1;%>
<td title="<%=prpDsc%>" style="<%=color%>"><%=prpValue%>
<%if((roleNmeList!=null && roleNmeList.contains("PRIEDIT"))){%>
<input type="text" size="8" id="memo_prc_<%=stkIdn%>" value="<%=rte%>"  name="memo_prc_<%=stkIdn%>" <%=onChangeMemoPrp%> /> 
<%}%>
<%if(cnt.equalsIgnoreCase("sd") && view.equals("BID")){ %>
<input type="text" size="8" id="BIDRte_<%=stkIdn%>"   name="BIDRte_<%=stkIdn%>" <%=onChangeMyRteOffer%> /> 
<input type="hidden" id="cmp_<%=stkIdn%>" name="cmp_<%=stkIdn%>" value="<%=pktDtl.getValue("cmp")%>"/>
<%}%>
<%if(view.equals("MIX")){%>
<input type="text" size="8" id="MixRte_<%=stkIdn%>"  onkeypress="return disableEnterKey(event);" name="MixRte_<%=stkIdn%>" value="<%=prpValue%>"  onchange="updateGtCTSQt('<%=stkIdn%>','MixRte_<%=stkIdn%>','QUOT','<%=prpValue%>','<%=mixrtelmt%>');"  /> 
<%}%></td>
<%vwDtl = (HashMap)stockViewMap.get("ISCMP");
 if(vwDtl!=null){
  String byrIdn = util.nvl((String)vwDtl.get("url"));
 if(byrIdn.equals(String.valueOf(info.getByrId()))){
  tdcount=tdcount+1;
 %>
 <td title="<%=prpDsc%>"><%=pktDtl.getValue("cmp")%></td>
 <%}
 
 
 }
 vwDtl = (HashMap)stockViewMap.get("ISAMT");
if(vwDtl!=null){
String isAMT = util.nvl((String)vwDtl.get("nme"));
if(isAMT.equals("Y")){%>
<td title="Amount"><%=pktDtl.getValue("AMT")%></td>
<%}}
}else if(prp.equals("RAP_RTE")){
vwDtl = (HashMap)stockViewMap.get("ISRAPVAL");
if(vwDtl!=null){
String ISCPTOTAL = util.nvl((String)vwDtl.get("nme"));
if(ISCPTOTAL.equals("Y")){tdcount=tdcount+1;%>
<td title="Rap Val"><%=pktDtl.getValue("RAPVAL")%></td>
<%}}
tdcount=tdcount+1;%>
<td title="<%=prpDsc%>" style="<%=color%>"><%=prpValue%></td>
<%}else if(prp.equals("CP")){
vwDtl = (HashMap)stockViewMap.get("ISCPDIS");
if(vwDtl!=null){
String ISCPTOTAL = util.nvl((String)vwDtl.get("nme"));
if(ISCPTOTAL.equals("Y")){tdcount=tdcount+1;%>
<td title="CP discount"><%=pktDtl.getValue("cpDis")%></td>
<%}}
tdcount=tdcount+1;%>
<td title="<%=prpDsc%>" style="<%=color%>"><%=prpValue%></td>
<%
 vwDtl = (HashMap)stockViewMap.get("ISCPTOTAL");
if(vwDtl!=null){
String ISCPTOTAL = util.nvl((String)vwDtl.get("nme"));
if(ISCPTOTAL.equals("Y")){tdcount=tdcount+1;%>
<td title="Amount"><%=pktDtl.getValue("cpTotal")%></td>
<%}}
}else{%>
<td title="<%=prpDsc%>" style="<%=color%>">
<%
tdcount++;
 String url ="";
 String typ="";
 String  pNme ="";
 String nwWin = "";
 String viewPRP="";
 String dir="";
  vwDtl = (HashMap)stockViewMap.get(prp);
  if(vwDtl!=null){
   url = util.nvl((String)vwDtl.get("url"));
   typ = util.nvl((String)vwDtl.get("typ"));
   dir = util.nvl((String)vwDtl.get("dir"),"certImg");
   viewPRP = util.nvl((String)vwDtl.get("view"));
   if(prp.equals("CERT NO.") || prp.equals("CERT_NO")){
   if(cert_lab.equals("IGI"))
    url="http://www.igiworldwide.com/search_report.aspx?PrintNo=REPNO&Wght=WT";
    if(cert_lab.equals("HRD"))
    url="https://my.hrdantwerp.com/?record_number=REPNO&weight=WT";
     if(cert_lab.equals("HRD"))
    url="https://my.hrdantwerp.com/?record_number=REPNO&weight=WT";
     if(cert_lab.equals("GCAL"))
     url="http://gemfacts.com/Cert/CertSearch.aspx?certNo=REPNO";
    if(!prpValue.equals("") && (cert_lab.equals("IGI")||cert_lab.equals("GIA") || cert_lab.equals("HRD") ||cert_lab.equals("GCAL") )){
   
     url = url.replace("WT",(String)pktDtl.getValue("cts"));
   
     url = url.replace("REPNO", prpValue);
     url.replaceAll(" ", "");
     if(typ.equals("NEW"))
       nwWin="target=\"_blank\"";
     if(viewPRP.equals("1"))
     pNme =prpValue ;
     else
     pNme = prpValue;
     url = "<A href='"+url+"' "+nwWin+" >"+pNme+"</a>";
      }else{
     url = prpValue;
     }
     
     
     prpValue = url;
     }else{
if(imagelistDtl!=null && imagelistDtl.size() >0){
for(int j=0;j<imagelistDtl.size();j++){
dtls=new HashMap();
dtls=(HashMap)imagelistDtl.get(j);
String looptyp=util.nvl((String)dtls.get("TYP"));
String allowon=util.nvl((String)dtls.get("ALLOWON"));
String gtCol=util.nvl((String)dtls.get("GTCOL"));
if((looptyp.equals("C") || looptyp.equals("V")) && (allowon.indexOf("SRCH") > -1) && gtCol.equals(dir)){
String path=util.nvl((String)dtls.get("PATH"));
String val=util.nvl((String)pktDtl.getValue(gtCol));
if(!val.equals("N")){
if(path.indexOf("Arrow_Images")> -1)
path=url+"&stkidn="+stkIdn+"&column="+dir;
else{
if(val.indexOf(".pdf")> -1)
path=path+val;
else
path="../zoompic.jsp?fileNme="+path+val+"&ht=800&wd=1100&cnt="+cnt;
}
url = "<A href='"+path+"' target=\"_blank\" ><b>"+prpValue+"</b></a>";
prpValue = url;
}}}}
}
}
%>
<%=prpValue%>
<%}%>
</td>
<%if(view.equals("SMX")  && prp.equals("CRTWT") && cnt.equals("kj")){%>
<td><input type="text" size="8" id="memo_wtdiff_<%=stkIdn%>" value=""  name="memo_wtdiff_<%=stkIdn%>" onchange="updateGtCTSQt('<%=stkIdn%>','memo_wtdiff_<%=stkIdn%>','memo_wtdiff','','')" /> </td>
<%}
}%>
<%
 vwDtl = (HashMap)stockViewMap.get("ISPKTRMK");

if(vwDtl!=null && view.equals("MIX")){%>
<td style="<%=color%>"><input type="text" size="25" id="rmk_<%=stkIdn%>"   name="rmk_<%=stkIdn%>" value=""  /> </td>
<%}%>
<td>&nbsp;&nbsp;</td>
</tr>
 <tr><td colspan="<%=tdcount+1%>" id="TDOffer_<%=stkIdn%>"   style="display:none" align="left">
<div align="left">
&nbsp;Valid Till&nbsp;<input type="text" name="DTE_<%=stkIdn%>" id="DTE_<%=stkIdn%>" size="7" value="<%=biddte%>"  />  
<a href="#"  onClick="setYears(1900, <%=info.getCurrentYear()%>);showCalender(this, 'DTE_<%=stkIdn%>');">
<img src="<%=info.getReqUrl()%>/images/calender.png"></a>
&nbsp;Offer Price&nbsp;&nbsp;<input type="text" id="bid_prc_<%=stkIdn%>"   name="bid_prc_<%=stkIdn%>" value="<%=bidRte%>"  size="7" <%=onChangePrp%> />
<input type="hidden" name="<%=stt_flg%>" value="<%=stt%>"/>
<input type="hidden" name="bid_vnm_<%=stkIdn%>" value="<%=vnm%>"/>
<input type="hidden" name="bid_dis_<%=stkIdn%>" id="bid_dis_<%=stkIdn%>" value="" />
&nbsp;Offer Discount&nbsp;&nbsp;<input type="text" id="bid_dis_txt_<%=stkIdn%>"   name="bid_dis_txt_<%=stkIdn%>"   size="7" <%=onChangeDis%> />
&nbsp;Offer Amount &nbsp;&nbsp;<input type="text" id="bid_amount_txt_<%=stkIdn%>"   name="bid_amount_txt_<%=stkIdn%>"   size="7" readonly="readonly" />
&nbsp;Offer Comment&nbsp;&nbsp;<input type="text" size="25" id="comm_<%=stkIdn%>"  value="<%=bidcomm%>" name="comm_<%=stkIdn%>" />
<span <%=offerLmtAllowDis%>>&nbsp;Offer Limit&nbsp;&nbsp;<input type="text" size="25" id="lmtRte_<%=stkIdn%>"  value="<%=bidlmtRte%>"  name="lmtRte_<%=stkIdn%>" /></span>
</div>
</td> 
</tr>
<tr><td colspan="<%=tdcount+1%>" id="TD_<%=stkIdn%>"   style="display:none" align="left">
<span id="BYR_<%=stkIdn%>" > </span>
<span id="OFFER_<%=stkIdn%>" style="width:500px" > </span>
<span id="SALE_<%=stkIdn%>" style="width:500px" > </span>
</td> 
</tr>
<%if(view.equals("MIX")){
String qtySalId = "QS_"+stkIdn;
String ctsSalID = "CS_"+stkIdn;
String amtSalId = "AS_"+stkIdn;
String prcSalId = "PS_"+stkIdn;
String ONclick="MixRtnCalCulSRCHRSLT('"+stkIdn+"')";
%>
<tr><td colspan="<%=tdcount+1%>" id="TRSP_<%=stkIdn%>"   style="display:none" align="left">
<table>
<tr><td>Total Sale Qty : <label id="<%=qtySalId%>" class="redLabel"></label>  Cts : <label id="<%=ctsSalID%>" class="redLabel"></label> Avg: <label id="<%=prcSalId%>" class="redLabel"></label> Amount:  <label id="<%=amtSalId%>" class="redLabel"></label>  </td></tr>
<tr><td>
<table class="Orange" cellpadding="1" cellspacing="1">
<tr><th class="Orangeth">Sr</th><th class="Orangeth">Qty</th><th class="Orangeth">Cts</th>
<th class="Orangeth">Price</th><th class="Orangeth">Amount</th><th class="Orangeth">Remark</th></tr>
<%for(int j=1;j<=10;j++){

String fldId=stkIdn+"_"+j;
String qtyId="QTY_"+fldId;
String ctsId="CTS_"+fldId;
String rteId="PRC_"+fldId;
String amtId="AMT_"+fldId;
String rmkId = "RMK_"+fldId;
%>
<tr><td><%=j%></td>
<td><input type="text" name="<%=qtyId%>" size="10" id="<%=qtyId%>" onchange="<%=ONclick%>" class="txtStyle" /></td>
<td><input type="text" name="<%=ctsId%>" size="15" id="<%=ctsId%>" onchange="<%=ONclick%>" class="txtStyle" /></td>
<td><input type="text" name="<%=rteId%>" size="15" id="<%=rteId%>" onchange="<%=ONclick%>" class="txtStyle" /></td>
<td><input type="text" name="<%=amtId%>" size="15" id="<%=amtId%>" onchange="<%=ONclick%>" class="txtStyle" /></td>
<td><input type="text" name="<%=rmkId%>" size="20" id="<%=rmkId%>" onchange="<%=ONclick%>" class="txtStyle" /></td>

</tr>
<%}%>
</table>
</td></tr></table>
</td> 
</tr>
<%}%>
<%}%>


</tbody>
</table>





</td></tr>
<tr><td> </td></tr>


<%
session.setAttribute("itemHdr", itemHdr);
}else{%>
<tr><td align="center"><h3>Sorry no result found</h3></td></tr>
<%}%></table>

<%setSelectStr=setSelectStr+"});</script>";%>

 <script type="text/javascript">
 function useNthColumn(n,colId) {


   var data = [],
       i,
       yourSelect,
       unique;

   $("#dataTable tr td:nth-child("+n+")").each(function () {
        data.push($(this).text());           
   });

   // https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Global_Objects/Array/filter
   // Use this function if your table is not large as the time complexity is O(n^2)
   unique = data.filter(function(item, i, arr) {
       return i == arr.indexOf(item);
   });

   yourSelect = $('#'+colId);
   for (i = 0; i < unique.length; i += 1) {
        yourSelect.append("<option>"+unique[i]+"</option>");
   }
}
 </script>

<%=setSelectStr%>

<script src="<%=info.getReqUrl()%>/PopScripts/jquery-1.11.3.min.js" type="text/javascript"></script>
 <script src="<%=info.getReqUrl()%>/dyanamitable/dynamitable.jquery.min.js" type="text/javascript"></script>
  

<script type="text/javascript">
<!--
$(window).bind("load", function() {
closeloading();
});
-->


</script>  
<%@include file="../calendar.jsp"%>
</body>
</html>

