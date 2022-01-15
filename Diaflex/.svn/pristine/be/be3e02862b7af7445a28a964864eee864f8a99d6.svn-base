<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
 
<%@ page errorPage="../error_page.jsp" %>
 
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import="java.util.ArrayList,java.util.ArrayList,java.util.List,java.util.Set, java.util.HashMap, java.sql.*, java.util.Enumeration, java.util.logging.Level"%>
<jsp:useBean id="util" class="ft.com.JspUtil" scope="page" />
<jsp:useBean id="info" class="ft.com.InfoMgr" scope="session" />
<jsp:useBean id="gtMgr" class="ft.com.GtMgr" scope="session" />
<%@taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>
<%@taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>

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
<script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/assortScript.js?v=<%=info.getJsVersion()%> " > </script>
<script type="text/javascript" src="<%=info.getReqUrl()%>/PopScripts/popup.js?v=<%=info.getJsVersion()%> " > </script>
<script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/memoScript.js?v=<%=info.getJsVersion()%> " > </script>
<script src="<%=info.getReqUrl()%>/PopScripts/jquery-1.2.6.min.js" type="text/javascript"></script>
<script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/tableFilter/actb.js " > </script>
<script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/tableFilter/tablefilter.js " > </script>
<script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/box.js?v=<%=info.getJsVersion()%> " > </script>


</head>
        <%String logId=String.valueOf(info.getLogId());
        String onfocus="cook('"+logId+"')";
        String cnt = (String)info.getDmbsInfoLst().get("CNT");
        HashMap dbinfo = info.getDmbsInfoLst();
        String lstNme = util.nvl((String)gtMgr.getValue("lstNme"));
       ArrayList vwPrpLst = info.getVwPrpLst();
       ArrayList sortPrpLst = info.getSortPrpLst();
       gtMgr.setValue(lstNme+"_SEL", new ArrayList());
       String pgDef = "SEARCH_RESULT";
       String isMix = util.nvl(info.getIsMix());
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
     String smmrydtlStr="";
     String ref = util.nvl((String)request.getAttribute("REF"));
if(ref.equals("YES"))
 lstNme = lstNme+"REF";
HashMap stockList = (HashMap)gtMgr.getValue(lstNme);
info.setViewForm("SRCH");
String  SLRBRTE = util.nvl((String)info.getDmbsInfoLst().get("SLRBRTE"));
 
        %>
 <body onfocus="<%=onfocus%>" onload="MM_preloadImages('images/faunalogo.png')" topmargin="0" leftmargin="0" rightmargin="0" bottommargin="0">
<input type="hidden" id="reqUrl" name="reqUrl" value="<%=info.getReqUrl()%>"/>
<input type="hidden" name="repUrl" id="repUrl" value="<%=dbinfo.get("HOME_DIR")%>"/>
<input type="hidden" name="webUrl" id="webUrl" value="<%=dbinfo.get("REP_URL")%>"/>
<input type="hidden" name="repPath" id="repPath" value="<%=dbinfo.get("REP_PATH")%>"/>
<input type="hidden" name="blockEnt" id="blockEnt" value="Y" />
<input type="hidden" id="CNT" name="CNT" value="<%=cnt%>"/>
<div id="popupContactMail">
<table align="center" cellpadding="10" cellspacing="10" class="pktTble1">
<tr><td>Email ID: </td><td><html:text property="value(emailId)" styleId="mail" size="30" value="<%=info.getByrEml()%>" name="searchForm" /> </td> </tr>
<tr><td align="right"><button type="button" onclick="validate_Mail()" Class="submit" >Send</button> </td>
<td align="left"><button type="button" onclick="disablePopupMail()" Class="submit" >Back</button> </td>
</tr>
</table>
</div>

<div id="backgroundPopup"></div>
<div id="popupContactSave" align="center" >
<img src="../images/loading_flame.gif" height="150px" width="150px" border="0" />
</div>
<div id="popupContactASSFNL">
<table class="popUpTb">
 <tr><td height="5%">&nbsp;&nbsp;&nbsp;<html:button property="value(close)" styleClass="submit"  onclick="MKEmptyPopup('SC');disablePopupASSFNL();" value="Close"  /> </td></tr>
<tr><td valign="top" height="95%">
<iframe name="SC" class="frameStyle"    align="middle" frameborder="0">

</iframe></td></tr>
</table>
</div>
<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0" class="mainbg">

<tr><td>
<%
if(stockList!=null && stockList.size()>0){
pageList= ((ArrayList)pageDtl.get("SORT") == null)?new ArrayList():(ArrayList)pageDtl.get("SORT");
if(pageList!=null && pageList.size() >0){
pageDtlMap=(HashMap)pageList.get(0);
dflt_val=util.nvl((String)pageDtlMap.get("dflt_val"));
if(dflt_val.equals("Y")){%>
<div id="mainRefDv">
  <div id="sortDv" style="display:none">
  <form action="StockSearch.do?method=sortSrch" method="post" >
  <table cellpadding="2" cellspacing="2"><tr><td colspan="2" nowrap="nowrap">
  <button type="submit" class="submit" value="YES" name="sort_srch" >Sort</button>
  &nbsp;<button type="submit" class="submit" value="YES" name="back_nor" >Back To Default</button>
  &nbsp;<button type="button" onclick="return resetSort('sorting_','5','0');" class="submit">Reset</button>
  </td></tr>
  <%for(int i=1;i<=5;i++){
  String fldnme="value(srt_"+i+")";
  String fldId="sorting_"+i;
  %>
  <tr><td><%=i%>.</td><td>
  <html:select property="<%=fldnme%>" styleId="<%=fldId%>"  name="searchForm" >
  <html:option value="0">---select---</html:option>
  <%for(int j=0;j<sortPrpLst.size();j++){
  String lprp = (String)sortPrpLst.get(j);
  %>
  <html:option value="<%=lprp%>" ><%=lprp%></html:option>
  <%}%>
  </html:select>
  </td></tr>
  <%}%>
  </table></form>
  </div>
  <div id="sortcloseDv" onclick="sortDiv()" ><img src="../images/sort.png" id="refSortImg"  />
  <img src="../images/Close.png" id="closeSortImg" style="display:none" /></div>
</div>
<%}}}%>
</td></tr>
<tr>
<td height="103" valign="top">
<jsp:include page="/header.jsp" />

</td>
</tr>

<tr>
<td height="5" valign="top" class="greyline"><img src="../images/grey_linebig.png" width="685" height="5" align="right" /></td>
</tr>
<tr>
<td>
<table>
<tr valign="bottom">
<td valign="">
  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<img src="../images/line.jpg" border="0" /></td><td valign="middle"> <span class="pgHed">Search Result</span> </td>
<td>&nbsp;&nbsp;</td>
<td><div class="Blue">&nbsp;</div> </td><td>&nbsp;New Good&nbsp;</td>
<td><div class="red">&nbsp;</div></td><td>&nbsp; HOLD&nbsp; </td>
<td><div class="gray">&nbsp;</div></td><td>&nbsp; New Good And Hold&nbsp; </td>
<%if(!info.getDmbsInfoLst().get("CNT").equals("svk")){%>
<td><div class="OrangeDV">&nbsp;</div></td><td>&nbsp; Offer Good&nbsp; </td>
<%}%>
<%if(info.getDmbsInfoLst().get("CNT").equals("hk") || info.getDmbsInfoLst().get("CNT").equals("ad")){%>
<td><div class="violet">&nbsp;</div></td><td>&nbsp; In Lab&nbsp; </td>
<%}%>
<td><div class="green">&nbsp;</div></td><td>&nbsp; Sold&nbsp; </td>
<%
String desc=(String)request.getAttribute("searchCrt");
if(desc!=null){
%>

<td><span class="txtLink" >Search Description : <%=desc %></span>
<td>
<%}%>
</tr>
</table>
</td>



</tr>
<tr><td>
<%
if(stockList!=null && stockList.size()>0){
pageList= ((ArrayList)pageDtl.get("REFINE") == null)?new ArrayList():(ArrayList)pageDtl.get("REFINE");
if(pageList!=null && pageList.size() >0){
pageDtlMap=(HashMap)pageList.get(0);
dflt_val=util.nvl((String)pageDtlMap.get("dflt_val"));
if(dflt_val.equals("Y")){%>
<div id="mainRefDv">
  <div id="refineDv" style="display:none"><jsp:include page="/marketing/refineSearch.jsp" /> </div><div id="closeDv" onclick="refineDiv()"><img src="../images/refineSrch.png" id="refImg"  /><img src="../images/Close.png" id="closeImg" style="display:none" /></div>
</div>
<%}}}%>
</td></tr>
<tr><td valign="top" class="hedPg">

<%
if(stockList!=null && stockList.size()>0){


    ArrayList pktStkIdnList =new ArrayList();
 Set<String> keys = stockList.keySet();
        for(String key: keys){
       pktStkIdnList.add(key);
        }
  gtMgr.setValue(lstNme+"_ALLLST", pktStkIdnList);
 String memotyp = util.nvl((String)request.getAttribute("memoTyp"));
ArrayList chargesLst= ((ArrayList)session.getAttribute("chargesLst") == null)?new ArrayList():(ArrayList)session.getAttribute("chargesLst");
String isExpDay = "style=\"display:none\"";
if(memotyp.equals("E"))
 isExpDay ="";
 
 String isSymbol = "style=\"display:none\"";
if(memotyp.indexOf("AP")!=-1)
 isSymbol ="";
int pktListSize=pktStkIdnList.size();
String isByrCbDay = "style=\"display:none\"";
if(memotyp.equals("I"))
isByrCbDay ="";

String srchTyp = util.nvl(info.getIsFancy());
List subStockList = new ArrayList();
HashMap ttls= (HashMap)gtMgr.getValue(lstNme+"_TTL");
info.setViewForm("SRCH");

HashMap mprp = info.getMprp();
HashMap prp = info.getPrp();
String selBkt = "", selWL = "",selML = "",selPEP="", selected = "checked=\"checked\"" ;
String cb_memo = null;
ArrayList prpDspBlocked = new ArrayList();
ArrayList sortPrpList = info.getSrtPrpLst();
if(!srchTyp.equals("FCY") ){
prpDspBlocked.add("INTENSITY");
prpDspBlocked.add("OVERTONE");
prpDspBlocked.add("FANCY COLOR");
}
int totalRecord = 0;
int startR=0;
int endR = 0;
String flg="Z";
int iTotalSearchRecords = 10;
int tdIndex = 5;
String chkSelFun="chkSele("+pktListSize+",'memo')";


%>


<form action="StockSearch.do?method=memo" method="post" name="stock">
<div id="popupContactSale" >
<table align="center" cellpadding="0" cellspacing="0" >
<tr><td>Do You Want To Confrim For delivery?</td></tr>
<tr><td><table><tr><td><html:radio property="value(isDLV)"  name="searchForm" value="Yes"/>YES </td>
<td> <html:radio property="value(isDLV)" name="searchForm" value="No"/>NO </td></tr></table> </td> </tr>
<tr><td><html:submit property="value(submitsal)" onclick="return confirmChangesWithPopup()"  value="Submit" styleClass="submit"/>
<html:button property="value(submitbtn)" onclick="disablePopupSale()"  value="Back" styleClass="submit"/>

</td> </tr>
</table>
</div>
<%
boolean ismemoLmt = false;
String ismemoLmtBasedOntype = "N";
double xrt = info.getXrt();
pageList= ((ArrayList)pageDtl.get("ISMEMOLMT") == null)?new ArrayList():(ArrayList)pageDtl.get("ISMEMOLMT");
if(pageList!=null && pageList.size() >0){
pageDtlMap=(HashMap)pageList.get(0);
fld_nme=util.nvl((String)pageDtlMap.get("fld_nme"));
if(fld_nme.equals("Y")){
  ismemoLmt = true;
  ismemoLmtBasedOntype=util.nvl((String)pageDtlMap.get("dflt_val"),"N");
  }
}
if(ismemoLmt){%>
<input type="hidden" name="isMemoCrt" id="isMemoCrt" value="<%=info.getMemo_lmt()%>" />
<input type="hidden" name="isMemoCrtBasedOnMemotyp" id="isMemoCrtBasedOnMemotyp" value="<%=ismemoLmtBasedOntype%>" />
<%}else{%>
<input type="hidden" name="isMemoCrt" id="isMemoCrt" value="N" />
<input type="hidden" name="isMemoCrtBasedOnMemotyp" id="isMemoCrtBasedOnMemotyp" value="N" />
<%}%>
<html:hidden property="srchTyp" value="<%=srchTyp%>" name="searchForm" />
<input type="hidden" name="<%=info.getReqUrl()%>" id="reqUrl" />
<table width="100%" align="left">

<tr><td valign="top" height="15">
<table>
<tr>
<td valign="top"> <table class="prcPrntTbl" id="sum" cellspacing="1" cellpadding="3">
<tr>
<th height="15">&nbsp;</th>
<!--<th height="15"><div align="center"><strong>Pcs</strong></div></th>
<th height="15"><div align="center"><strong>Cts</strong></div></th>
<th height="15"><div align="center"><strong>Base</strong></div></th>
<th height="15"><div align="center"><strong>Rap Value</strong></div></th>
<th height="15"><div align="center"><strong>Avg disc </strong></div></th>
<th height="15"><div align="center"><strong>Avg Price</strong></div></th>
<th height="15"><div align="center"><strong>Value</strong></div></th>-->
<% pageList=(ArrayList)pageDtl.get("SELECTED");
if(pageList!=null && pageList.size() >0){
for(int j=0;j<pageList.size();j++){
pageDtlMap=(HashMap)pageList.get(j);
fld_ttl=(String)pageDtlMap.get("fld_ttl");
flg1=(String)pageDtlMap.get("flg1");
if(flg1.equals("")){
boolean isDisplay=true;
fld_nme=(String)pageDtlMap.get("fld_nme");
if(!fld_nme.equals("") && vwPrpLst.indexOf(fld_nme)==-1)
  isDisplay=false;

if(isDisplay){

%>
<th height="15"><div align="center"><strong><%=fld_ttl%></strong></div></th>
<%}}}}%>
</tr>
<tr>
<td><div align="center"><strong>Total</strong></div></td>
<!--<td height="20">
<div align="center">
<label><%=util.nvl((String)ttls.get("ZQ"))%></label>
</div>
</td>

<td><div align="center">
<label><%=util.nvl((String)ttls.get("ZW"))%></label>
</div></td>

<td><div align="center">
<label><%=util.nvl((String)ttls.get("ZB"))%></label>

</div></td>
<td><div align="center">
<label><%=util.nvl((String)ttls.get("ZR"))%></label>

</div></td>
<td><div align="center">
<label><%=util.nvl((String)ttls.get("ZD"))%></label>
</div></td>
<td><div align="center">
<label><%=util.nvl((String)ttls.get("ZA"))%></label>

</div></td>
<td><div align="center">
<label><%=util.nvl((String)ttls.get("ZV"))%></label>

</div></td>-->
<% pageList=(ArrayList)pageDtl.get("SELECTED");
if(pageList!=null && pageList.size() >0){
for(int j=0;j<pageList.size();j++){
pageDtlMap=(HashMap)pageList.get(j);
fld_typ=(String)pageDtlMap.get("fld_typ");
flg1=(String)pageDtlMap.get("flg1");
if(flg1.equals("")){
boolean isDisplay=true;
fld_nme=(String)pageDtlMap.get("fld_nme");
if(!fld_nme.equals("") && vwPrpLst.indexOf(fld_nme)==-1)
  isDisplay=false;

if(isDisplay){

%>
<td height="20"><div align="center"><label><%=util.nvl((String)ttls.get(fld_typ))%></label></div></td>
<%}}}}%>
</tr>

<tr>
<td><div align="center"><strong>Selected</strong></div></td>
<!--<td height="20">
<div align="center">
<label id="selectpcs"><%=util.nvl((String)ttls.get("MQ"))%></label>
</div>
</td>
<td><div align="center">
<label id="selectcts"><%=util.nvl((String)ttls.get("MW"))%></label>

</div></td>

<td><div align="center">
<label id="selectbase"><%=util.nvl((String)ttls.get("MB"))%></label>

</div></td>
<td><div align="center">
<label id="selectrapvlu"><%=util.nvl((String)ttls.get("MR"))%></label>
</div></td>

<td><div align="center">
<label id="selectavgdis"><%=util.nvl((String)ttls.get("MD"))%></label>
</div></td>
<td><div align="center">
<label id="selectavgprice"><%=util.nvl((String)ttls.get("MA"))%></label>

</div></td>
<td><div align="center">
<label id="selectvalues" ><%=util.nvl((String)ttls.get("MV"))%></label>

</div></td>-->
<% pageList=(ArrayList)pageDtl.get("SELECTED");
if(pageList!=null && pageList.size() >0){
for(int j=0;j<pageList.size();j++){
pageDtlMap=(HashMap)pageList.get(j);
val_cond=(String)pageDtlMap.get("val_cond"); 
lov_qry=(String)pageDtlMap.get("lov_qry");
flg1=(String)pageDtlMap.get("flg1");
if(flg1.equals("")){
smmrydtlStr+="_"+val_cond;
boolean isDisplay=true;
fld_nme=(String)pageDtlMap.get("fld_nme");
if(!fld_nme.equals("") && vwPrpLst.indexOf(fld_nme)==-1)
  isDisplay=false;

if(isDisplay){
%>
<td height="20" style="background-color:Orange;"><div align="center"><B><label id="<%=val_cond%>"><%=util.nvl((String)ttls.get(lov_qry))%></label></b></div></td>
<%}}else{
%><label id="<%=val_cond%>" style="display:none;" ></label>
<%}}}%>
</tr>
<tr>
<td><div align="center"><strong>Rejected</strong></div></td>
<!--<td height="20">
<div align="center">
<label id="regpcs"><%=util.nvl((String)ttls.get("ZQ"))%></label>
</div>
</td>
<td><div align="center">
<label id="regcts"><%=util.nvl((String)ttls.get("ZW"))%></label>

</div></td>

<td><div align="center">
<label id="regbase"><%=util.nvl((String)ttls.get("ZB"))%></label>

</div></td>
<td><div align="center">
<label id="regrapvlu"><%=util.nvl((String)ttls.get("ZR"))%></label>
</div></td>

<td><div align="center">
<label id="regavgdis"><%=util.nvl((String)ttls.get("ZD"))%></label>
</div></td>
<td><div align="center">
<label id="regavgprice"><%=util.nvl((String)ttls.get("ZA"))%></label>

</div></td>
<td><div align="center">
<label id="regvalues" ><%=util.nvl((String)ttls.get("ZV"))%></label>

</div></td>-->
<% pageList=(ArrayList)pageDtl.get("SELECTED");
if(pageList!=null && pageList.size() >0){
for(int j=0;j<pageList.size();j++){
pageDtlMap=(HashMap)pageList.get(j);
dflt_val=(String)pageDtlMap.get("dflt_val");
fld_typ=(String)pageDtlMap.get("fld_typ");
flg1=(String)pageDtlMap.get("flg1");
if(flg1.equals("")){
boolean isDisplay=true;
fld_nme=(String)pageDtlMap.get("fld_nme");
if(!fld_nme.equals("") && vwPrpLst.indexOf(fld_nme)==-1)
  isDisplay=false;

if(isDisplay){
%>
<td height="20"><div align="center"><label id="<%=dflt_val%>"><%=util.nvl((String)ttls.get(fld_typ))%></label></div></td>
<%}}else{%><label id="<%=dflt_val%>" style="display:none;" ></label>
<%}}}%>
<input type="hidden" name="smmrydtlStr" id="smmrydtlStr" value="<%=smmrydtlStr%>" />
</tr>

<%
   pageList=(ArrayList)pageDtl.get("SOLD_SRCH");
 if(pageList!=null && pageList.size() >0){
HashMap soldTtl = (HashMap)request.getAttribute("SOLDTTL");
if(soldTtl!=null && soldTtl.size()>0){%>
<tr><td><b>Sold</b></td>
<%
pageList=(ArrayList)pageDtl.get("SOLD");
if(pageList!=null && pageList.size() >0){
for(int j=0;j<pageList.size();j++){
pageDtlMap=(HashMap)pageList.get(j);
dflt_val=(String)pageDtlMap.get("dflt_val");
fld_typ=(String)pageDtlMap.get("fld_typ");
flg1=(String)pageDtlMap.get("flg1");
%>
<td height="20"><div align="center"><label id="<%=dflt_val%>"><%=util.nvl((String)soldTtl.get(fld_typ))%></label></div></td>
<%}%></tr>
<%}}}%>
</table></td>
<%if(cnt.equalsIgnoreCase("kj")){
String displayloy="";
String displaymem="";%>
<logic:notEqual property="value(loyallow)"  name="searchForm"  value="YES" >
<%displayloy="display:none";%>
</logic:notEqual>
<logic:notEqual property="value(memallow)"  name="searchForm"  value="YES" >
<%displaymem="display:none";%>
</logic:notEqual>
<td valign="top">
<table class="prcPrntTblSmall">
<tr><th colspan="3">Loyalty/Member</th></tr>
<tr style="<%=displaymem%>"><td><b>Member</b></td><td style="background-color:Orange;"><B><bean:write property="value(mem_dis)" name="searchForm" /> %</b></td><td style="background-color:Orange;"><b>$<label id="memVlu">0</label></b> </td></tr>
<tr style="<%=displayloy%>"><td><b>Loyalty</b></td>
<td style="background-color:Orange;"><b><label id="lCtg"> <bean:write property="value(loyPct)" name="searchForm" /> % <bean:write property="value(loyCtg)" name="searchForm" /></label></b></td>
<td style="background-color:Orange;"><B>$<label id="loyVlu"><bean:write property="value(loyVlu)" name="searchForm" /></label></b> </td></tr>
<tr style="<%=displayloy%>"><td><b>Diff</b></td>
<td style="background-color:Orange;">
<b><label id="diff"> <bean:write property="value(loyDiff)" name="searchForm" /></label></b>
</td>
<td style="background-color:Orange;" title="Sale Value"><B>365 Days Purchase $ <label id="ttlSalVlu"><bean:write property="value(ttlSalVlu)" name="searchForm" /></label></b> </td>
</tr>
<tr><td><b>O/S Amount</b></td>
<td style="background-color:Orange;">
<b><label id="osamount"> <bean:write property="value(osamount)" name="searchForm" /></label></b>
</td>
<td style="background-color:Orange;">
<b>Web Discount :</b> <b><label id="webdiscPct">0%</label></b>
<b>$:</b> <b><label id="webdiscvlu">0</label></b>
</td>
</tr>
</table>
</td><%}%>
<%if(cnt.equalsIgnoreCase("kj") || cnt.equalsIgnoreCase("KU")){%>
<td valign="top">
<table class="prcPrntTblSmall">
<tr><th colspan="2">Payable</th></tr>
<tr><td><b>Final Disc</b></td><td style="background-color:Orange;"><B><label id="fnlDisc">0</label></b></td></tr>
<tr><td><b>Final Price</b></td><td style="background-color:Orange;"><b><label id="fnlPrice">0</label></b></td></tr>
<tr><td><b>Final Amount</b></td><td style="background-color:Orange;"><b><label id="fnlAmt">0</label></b></td></tr>
<tr><td><b></b></td><td style="background-color:Orange;"><b></b></td></tr>
</table>
</td>

<%}%>
<% pageList=(ArrayList)pageDtl.get("MEM_DIS");
if(pageList!=null && pageList.size() >0){%>
<td valign="top">
<table class="prcPrntTblSmall">
<tr><th colspan="3">Member Discount</th></tr>
<tr><td><b>Member</b></td><td style="background-color:Orange;"><B><%=dbinfo.get("MEMBER_DISCOUNT")%> %</b></td><td style="background-color:Orange;"><b>$<label id="memVlu">0</label></b> </td></tr>

</table></td>
<%}%>
<td valign="top">
<table class="prcPrntTbl">
<tr><th colspan="4">Customer Information</th>
<%
if(!memolmt.equals("N") && ismemoLmt){%>
<th colspan="4">Limit</th>
<%}
%>
</tr>
<tr><td align="left"><B>Customer name:</b></td><td align="left"><%=info.getByrNm()%></td><td align="left"><b>Terms :</b></td>
<td align="left"><%=info.getTrms()%>
<%if(!util.nvl(info.getShorttrms()).equals("N")){%>
&nbsp<b>(<%=info.getShorttrms()%>)</b>
<%}%>
</td>
<%if(!memolmt.equals("N") && ismemoLmt){%>
<td>Total Memo/Credit</td><td><label id="ttlMemoCrt" > <%=info.getMemo_lmt()%></label></td>
<%}%>
</tr>
<tr><td align="left">
<b>Email ID:</b></td><td align="left"><input type="hidden" name="byrEml" value="Y" /> <%=info.getByrEml()%></td><td align="left"><b>Sales person :</b></td><td align="left"><%=info.getSaleExNme()%></td>
<%if(!memolmt.equals("N") && ismemoLmt){
String ttlMemoVal=util.nvl((String)request.getAttribute("ttlMemoVal"),"0");
ttlMemoVal=ttlMemoVal.replaceAll(" ", "");
long memoCrt = Long.parseLong(ttlMemoVal);
long ttlMemoCrt = Long.parseLong(info.getMemo_lmt());
long avlCrt = ttlMemoCrt-memoCrt;
%>
<td>
<input type="hidden"  name="xrt" id="xrt" value="<%=xrt%>" />
Available Memo/Credit</td><td><label id="avMemoCrt" > <%=avlCrt%></label></td>
<%}%>
</tr>
<tr>
<td align="left"><b>Mobile No:</b></td><td align="left"><bean:write property="value(mobile)" name="searchForm" /></td>
<td colspan="2">
<table cellpadding="2"><tr>
<td align="left"><b>Office No:</b></td><td align="left"><bean:write property="value(office)" name="searchForm" /></td>
 <logic:equal property="value(otherref)"  name="searchForm"  value="NA" >
<td align="left"><b>GSTIN:</b></td><td align="left"><bean:write property="value(gstin)" name="searchForm" /></td>
</logic:equal>
<logic:notEqual property="value(trDis)"  name="searchForm"  value="" >
<td align="left"><b>Trade Discount:</b></td><td align="left"><bean:write property="value(trDis)" name="searchForm" /></td>
</logic:notEqual>
<logic:notEqual property="value(otherref)"  name="searchForm"  value="NA" >
<td align="left"><b>Other References:</b></td><td align="left"><bean:write property="value(otherref)" name="searchForm" /></td>
</logic:notEqual>

</tr></table></td>
</tr>
</table>
</td>


</tr>
</table>





</td>

</tr>



<%
String msg =(String)request.getAttribute("memoMsg");
if(msg!=null){%>
<tr>
<td valign="top">
<label class="redLabel" ><%=msg%></label>
</td></tr>
<%}
%>


<tr><td>
<table><tr>
<% pageList=(ArrayList)pageDtl.get("DEAL");
if(pageList!=null && pageList.size() >0){
for(int j=0;j<pageList.size();j++){
pageDtlMap=(HashMap)pageList.get(j);
fld_nme=(String)pageDtlMap.get("fld_nme");
fld_ttl=(String)pageDtlMap.get("fld_ttl");
fld_typ=(String)pageDtlMap.get("fld_typ");
lov_qry=(String)pageDtlMap.get("lov_qry");
dflt_val=(String)pageDtlMap.get("dflt_val");
val_cond=(String)pageDtlMap.get("val_cond");
%>
<td><input type="checkbox" name="<%=fld_nme%>" value="<%=dflt_val%>" /></td><td><span class="txtBold"><%=fld_ttl%></span></td>
<%}}
String exday="displayExDay(this,'"+cnt+"');";
ArrayList prpValLst = (ArrayList)prp.get("SYMBOLV");

%>
<td><span class="txtBold" > Select Memo Type : </span></td><td>
<html:select property="value(typ)" styleId="memoTyp" name="searchForm" onchange="<%=exday%>" >
<html:optionsCollection property="memoList" name="searchForm"
label="dsc" value="memo_typ" />
</html:select></td>
  <% 
 if(prpValLst!=null && prpValLst.size()>0){ 
           %><td><div id="symbol" <%=isSymbol%>><table><tr>
      <td><span class="txtBold" >    Symbol:</span></td><td>
           <html:select property="value(SYMBOL)" styleId="SYMBOL" name="searchForm" >
           <html:option value="">-select Symbol-</html:option>
           <%for(int k=0;k<prpValLst.size();k++){ 
           String lprpVal = (String)prpValLst.get(k);
           %>
           <html:option value="<%=lprpVal%>"><%=lprpVal%></html:option>
           <%}%>
           </html:select></td></tr></table></div></td>
 <%}%>


<td valign="top"><div id="expDay" <%=isExpDay%>>
<logic:present property="value(ExpDayList)" name="searchForm" >
<table><tr><td>
<span class="txtBold"> Expiry days : </span></td><td>
<html:select property="value(day)" name="searchForm" >
<html:optionsCollection property="value(ExpDayList)" name="searchForm" value="FORM_NME" label="FORM_TTL" />
</html:select></td>
<%if(util.nvl((String)info.getDmbsInfoLst().get("EXTME")).equals("Y")){ %>
<td><span class="txtBold">Expiry Time: </span></td><td>

<html:text property="value(extme)" onkeypress="return disableEnterKey(event);" name="searchForm" size="8" /> 

</td>
<%}else{%>
<html:hidden property="value(extme)" name="searchForm"  /> 
<%}%>
<%if(util.nvl((String)info.getDmbsInfoLst().get("EXTCONF")).equals("Y")){ %>
<td><span class="txtBold">Ext Conf %:</span></td><td><html:text property="value(extconf)" onkeypress="return disableEnterKey(event);" name="searchForm" size="10" /> </td> 
<%}else{%>
<html:hidden property="value(extconf)" name="searchForm"  /> 
<%}%>
</tr></table>

</logic:present>

</div></td>


<td valign="top"><div id="webBlockDiv" style="display:none;">
<table><tr>
<td>
<span class="txtBold"> Web Block : </span></td><td>
<html:select property="value(web_block)" styleId="web_block" name="searchForm" >
<html:option value="NO">NO</html:option>
<%
 prpValLst = (ArrayList)prp.get("WEB_BLOCKV");
if(prpValLst!=null){
for(int k=0;k<prpValLst.size();k++){ 
String lprpVal = (String)prpValLst.get(k);
if(!lprpVal.equals("NA") && !lprpVal.equals("NO")){
%>
<html:option value="<%=lprpVal%>"><%=lprpVal%></html:option>
<%}}}%>
</html:select></td>
</tr></table>
</div></td>

<td valign="top"><div id="byrCb" <%=isByrCbDay%>>
<logic:present property="value(ByrCbList)" name="searchForm" >
<table><tr>
<td>
<span class="txtBold"> Buyer Cabin : </span></td><td>
<html:select property="value(cabin)" name="searchForm" >
<html:optionsCollection property="value(ByrCbList)" name="searchForm" value="FORM_NME" label="FORM_TTL" />
</html:select></td>
</tr></table>
</logic:present>
</div></td>

<td valign="top">
<%String slrb = util.nvl((String)request.getAttribute("SLRB"));
if(slrb.equals("Y")){
%>
SLRB Premium : &nbsp;<input type="checkbox" name="SLRBRTE" id="SLRBRTE" value="<%=SLRBRTE%>" />
<%}%></td>

<td valign="top"><div id="rmkOn" style="">
<b>Rmk/Cmnt</b>&nbsp;<html:text property="value(rmk)" styleId="rmkval" size="15"  onkeypress="return disableEnterKey(event);" name="searchForm"  />
<%if(cnt.equalsIgnoreCase("asha")){%>
<b>Rmk/Cmnt2</b>&nbsp;<html:text property="value(rmk2)" styleId="rmk2val" size="15"  onkeypress="return disableEnterKey(event);" name="searchForm"  />
<%}%>
</div></td>
<%if(isMix.equals("SMX")){%>
<td valign="top">
<b>WT Diff:</b>&nbsp;<html:text property="value(wtdiff)" styleId="wtdiff" onkeypress="return disableEnterKey(event);" size="10" name="searchForm"  />
</td>
<%}%>

<%  pageList=(ArrayList)pageDtl.get("ISPKTRMK");
if(pageList!=null && pageList.size() >0){if(isMix.equals("MIX")){%>
<td valign="top"><div id="rmkOn" style="">
<b>Rmk/Cmnt 2</b>&nbsp;<html:text property="value(rmk1)" styleId="rmkval2" size="15" onkeypress="return disableEnterKey(event);" name="searchForm"  />
</div></td>
<%}}%>
<td valign="top"><div id="charge" style="display:none">
<table><tr>
    <%
    if(chargesLst!=null && chargesLst.size()>0){
    for(int i=0;i<chargesLst.size();i++){
    HashMap dtl=new HashMap();
    dtl=(HashMap)chargesLst.get(i);
    String dsc=(String)dtl.get("dsc");
    String autoopt=(String)dtl.get("autoopt");
    String flgflg=(String)dtl.get("flg");
    String typ=(String)dtl.get("typ");
    String fctr=(String)dtl.get("fctr");
    String fun=(String)dtl.get("fun");
    String rmk=(String)dtl.get("rmk");
    String field = "value("+typ+")";
    String salCharge = "value("+typ+"_TTL)";
    String fieldRmk = "value("+typ+"_rmk)";
    String onchang="chargesmanualsrch('"+typ+"','"+i+"')";
    String onchangrmk="chargesmanualrmk('"+typ+"','"+i+"')";
    String fieldId = typ+"_rmk";
    
    String fieldsave = "value("+typ+"_save)";
    String fieldRmksave = "value("+typ+"_rmksave)";
    String fieldIdsave = typ+"_save";
    String fieldIdrmksave = typ+"_rmksave";
    String fieldautosave = "value("+typ+"_AUTOOPT)";
    String fieldIdautosave = typ+"_AUTOOPT";
    if(flgflg.equals("MNL")){
    %>
    <td nowrap="nowrap"><b><%=dsc%></b></td>
     <td nowrap="nowrap"><b><span id="<%=typ%>_dis" style="display:none"></span></b></td>
     <td nowrap="nowrap"><bean:write property="<%=salCharge%>" name="searchForm" /></td>
    <td nowrap="nowrap"><html:text property="<%=field%>" size="6" styleId="<%=typ%>" onkeypress="return disableEnterKey(event);" onchange="<%=onchang%>" name="searchForm"/></td>
    <td nowrap="nowrap">
    <%if(rmk.equals("Y")){%>
    Remark<html:text property="<%=fieldRmk%>" styleId="<%=fieldId%>"  onchange="<%=onchangrmk%>" onkeypress="return disableEnterKey(event);" name="searchForm" size="10"/>
    <html:hidden property="<%=fieldRmksave%>" name="searchForm" styleId="<%=fieldIdrmksave%>"  />
    <%}%>
    </td>
    <%}else{%>
   <td nowrap="nowrap"><b><%=dsc%></b>
    <%if(flgflg.equals("AUTO") && autoopt.equals("OPT")){%>
    <input type="checkbox" name="<%=typ%>_AUTO" checked="checked" id="<%=typ%>_AUTO" onchange="validateAutoOpt('<%=typ%>_AUTO')" title="*Uncheck if don't want to apply"/>
    <html:hidden property="<%=fieldautosave%>" name="searchForm" styleId="<%=fieldIdautosave%>" value="N"  />
    <%}%>
    </td>
      <td nowrap="nowrap"><bean:write property="<%=salCharge%>" name="searchForm" /></td>
    <td nowrap="nowrap"><b><span id="<%=typ%>_dis" style="display:none"></span></b></td>
    
    <td nowrap="nowrap">
    <html:hidden property="<%=field%>"  styleId="<%=typ%>" name="searchForm"/>
    </td><td nowrap="nowrap"></td>
    <%}%>
    <html:hidden property="<%=fieldsave%>" name="searchForm" styleId="<%=fieldIdsave%>"  />
    <%}%>
    <td id="loading"></td>
    <%}%>
    <html:hidden property="value(vluamt)" name="searchForm" styleId="vluamt"  />
    <input type="hidden" id="nmeIdn" name="nmeIdn" value="<%=info.getByrId()%>" />
    <span id="net_dis" style="display:none"></span>
    </tr>
</table>
</div></td>

<%    pageList=(ArrayList)pageDtl.get("BUTTON");
            if(pageList!=null && pageList.size() >0){
            for(int j=0;j<pageList.size();j++){
            pageDtlMap=(HashMap)pageList.get(j);
            fld_nme="value("+(String)pageDtlMap.get("fld_nme")+")";
            fld_ttl=(String)pageDtlMap.get("fld_ttl");
            fld_typ=(String)pageDtlMap.get("fld_typ");
            lov_qry=(String)pageDtlMap.get("lov_qry");
            dflt_val=(String)pageDtlMap.get("dflt_val");
            val_cond=(String)pageDtlMap.get("val_cond"); 
            val_cond=val_cond.replaceAll("PKTIDNLST",String.valueOf(pktListSize));
            if(fld_typ.equals("S")){
            %>
    <td><html:submit property="<%=fld_nme%>" value="<%=fld_ttl%>" onclick="<%=val_cond%>" styleClass="submit"/></td>
    <%}else if(fld_typ.equals("B")){%>
    <td><html:button property="<%=fld_nme%>" value="<%=fld_ttl%>" onclick="<%=val_cond%>" styleClass="submit"/></td>
    <%}else if(fld_typ.equals("HB")){%>
    <td align="right"><button type="button" onclick="<%=val_cond%>" class="submit" accesskey="<%=lov_qry%>" id="<%=fld_nme%>" name="<%=fld_nme%>" ><%=fld_ttl%></button></td>    
    <%}}}
    %>

<td><a onclick="chkSele('<%=pktListSize%>','excel');"> Create Excel <img src="../images/ico_file_excel.png" id="img" title="Click here to create excel"/></a></td>
<td>Thru Person:</td><td><input type="text" onkeypress="return disableEnterKey(event);" name="thruPer" size="20" /> <input type="hidden" id="CUR" value="<%=info.getRln()%>"/>  </td>
<%ArrayList notepersonList = ((ArrayList)info.getNoteperson() == null)?new ArrayList():(ArrayList)info.getNoteperson();
if(notepersonList.size()>0){%>
<td>Note Person:</td>
<td>
      <html:select name="searchForm" property="value(noteperson)" styleId="noteperson">
      <html:option value="">---Select---</html:option><%
      for(int i=0;i<notepersonList.size();i++){
      ArrayList noteperson=(ArrayList)notepersonList.get(i);%>
      <html:option value="<%=(String)noteperson.get(0)%>"> <%=(String)noteperson.get(1)%> </html:option>
      <%}%>
      </html:select>
</td>
<%}%>
<% pageList=(ArrayList)pageDtl.get("CHECKBOXSP");
if(pageList!=null && pageList.size() >0){
for(int j=0;j<pageList.size();j++){
pageDtlMap=(HashMap)pageList.get(j);
fld_nme=(String)pageDtlMap.get("fld_nme");
fld_ttl=(String)pageDtlMap.get("fld_ttl");
fld_typ=(String)pageDtlMap.get("fld_typ");
lov_qry=(String)pageDtlMap.get("lov_qry");
dflt_val=(String)pageDtlMap.get("dflt_val");
val_cond=(String)pageDtlMap.get("val_cond");
%>
<td><input type="checkbox" name="<%=fld_nme%>" value="<%=dflt_val%>" /></td><td><span class="txtBold"><%=fld_ttl%></span></td>
<%}}%>
<%
  ArrayList rolenmLst=(ArrayList)info.getRoleLst();
  String usrFlg=util.nvl((String)info.getUsrFlg());
  if(usrFlg.equals("SYS") || rolenmLst.contains("ADMIN")){
  if(isMix.equals("") || isMix.equals("NR")){
  %>
  <td><img src="../images/edit.jpg" title="Edit Properties" onclick="newWindow('<%=info.getReqUrl()%>/dynamicProperty.do?method=load&mdl=MEMO_VW&sname=&par=VW')" border="0" width="15px" height="15px"/> </td>
  <%}else if(isMix.equals("SMX")){%>
  <td><img src="../images/edit.jpg" title="Edit Properties" onclick="newWindow('<%=info.getReqUrl()%>/dynamicProperty.do?method=load&mdl=SMX_VW&sname=&par=VW')" border="0" width="15px" height="15px"/> </td>
  <%}else{%>
  <td><img src="../images/edit.jpg" title="Edit Properties" onclick="newWindow('<%=info.getReqUrl()%>/dynamicProperty.do?method=load&mdl=MIX_VW&sname=&par=VW')" border="0" width="15px" height="15px"/> </td>
  <%}}%>
</tr></table> 
</td></tr>
<%
String srchRefMsg = (String)request.getAttribute("vnmNotFnd");
if(srchRefMsg!=null){%>
<tr><td><label class="redLabel" ><%=srchRefMsg%></label></td></tr>
<%}%>
<tr>
<td>
<%if(pktStkIdnList!=null && pktStkIdnList.size()>0){
%>
<jsp:include page="/marketing/stockView.jsp" />
<%}else{%>
Sorry no result found...
<%}%>
</td></tr>
</table>
</form>
<%}else{%>
Sorry no result found... 
<html:button property="value(memo)" value="Modify Search" onclick="goTo('StockSearch.do?method=loadSrchPrp&modify=yes','','')" styleClass="submit"/>
<%}%>
</td></tr></table>
</body>
</html>
