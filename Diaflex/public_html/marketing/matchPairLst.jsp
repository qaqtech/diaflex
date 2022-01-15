 
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
        %>
 <body onfocus="<%=onfocus%>">
   <%
        String cnt = (String)info.getDmbsInfoLst().get("CNT");

  info.setViewForm("MP");
  String isMix = util.nvl(info.getIsMix());
String pgDef = "SEARCH_RESULT";
   String smmrydtlStr="";
HashMap allPageDtl= (info.getPageDetails() == null)?new HashMap():(HashMap)info.getPageDetails();
HashMap pageDtl=(HashMap)allPageDtl.get(pgDef);
String memolmt = util.nvl(info.getMemo_lmt());
    ArrayList pageList=new ArrayList();
    HashMap pageDtlMap=new HashMap();
    String fld_nme="",fld_ttl="",val_cond="",dflt_val="",fld_typ="",form_nme="",lov_qry="",flg1="";
     String lstNme = util.nvl((String)gtMgr.getValue("lstNme"));
  if(lstNme!=null){
  lstNme = lstNme+"_MTPR";
  HashMap ttls= (HashMap)gtMgr.getValue(lstNme+"_TTL");
  HashMap stockList = (HashMap)gtMgr.getValue(lstNme);
 
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
int pktListSize=pktStkIdnList.size();
String isByrCbDay = "style=\"display:none\"";
if(memotyp.equals("I"))
isByrCbDay ="";

String srchTyp = util.nvl(info.getIsFancy());
List subStockList = new ArrayList();
       ArrayList vwPrpLst = info.getVwPrpLst();

HashMap mprp = info.getMprp();
HashMap prp = info.getPrp();
String selBkt = "", selWL = "",selML = "",selPEP="", selected = "checked=\"checked\"" ;
String cb_memo = null;
ArrayList prpDspBlocked = new ArrayList();
ArrayList sortPrpList = info.getSrtPrpLst();

int totalRecord = 0;
int startR=0;
int endR = 0;
String flg="Z";
int iTotalSearchRecords = 10;
int tdIndex = 5;
String chkSelFun="chkSele("+pktListSize+",'memo')";

  %>
 <form action="StockSearch.do?method=memo" method="post" onsubmit="" name="stock">
<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0" >

<tr>
<td class="tdLayout" valign="top">
<input type="hidden" name="<%=info.getReqUrl()%>" id="reqUrl" />
<table width="100%" align="left">

<tr><td valign="top" height="15">
<table>
<tr>
<td> <table class="prcPrntTbl" cellspacing="1" cellpadding="3">
<tr>
<th height="15">&nbsp;</th>
<!--<th height="15"><div align="center"><strong>Pcs</strong></div></th>
<th height="15"><div align="center"><strong>Cts</strong></div></th>
<th height="15"><div align="center"><strong>Base</strong></div></th>
<th height="15"><div align="center"><strong>Avg disc </strong></div></th>
<th height="15"><div align="center"><strong>Avg Price</strong></div></th>
<th height="15"><div align="center"><strong>Value</strong></div></th>-->
<% pageList=(ArrayList)pageDtl.get("MATHSELECTED");
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
<label><%=util.nvl((String)ttls.get("PQ"))%></label>

</div>
</td>
<td><div align="center">
<label><%=util.nvl((String)ttls.get("PW"))%></label>

</div></td>

<td><div align="center">
<label><%=util.nvl((String)ttls.get("PB"))%></label>

</div></td>


<td><div align="center">
<label><%=util.nvl((String)ttls.get("PD"))%></label>

</div></td>
<td><div align="center">
<label><%=util.nvl((String)ttls.get("PA"))%></label>

</div></td>
<td><div align="center">
<label><%=util.nvl((String)ttls.get("PV"))%></label>

</div></td>-->
<% pageList=(ArrayList)pageDtl.get("MATHSELECTED");
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
<label id="selectpcs"><%=util.nvl((String)ttls.get("MTQ"))%></label>

</div>
</td>
<td><div align="center">
<label id="selectcts"><%=util.nvl((String)ttls.get("MTW"))%></label>

</div></td>


<td><div align="center">
<label id="base"><%=util.nvl((String)ttls.get("MTB"))%></label>

</div></td>

<td><div align="center">
<label id="avgdis"><%=util.nvl((String)ttls.get("MTD"))%></label>

</div></td>
<td><div align="center">
<label id="avgprice"><%=util.nvl((String)ttls.get("MTA"))%></label>

</div></td>
<td><div align="center">
<label id="values" ><%=util.nvl((String)ttls.get("MTV"))%></label>

</div></td>-->
<% pageList=(ArrayList)pageDtl.get("MATHSELECTED");
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
<% pageList=(ArrayList)pageDtl.get("MATHSELECTED");
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
</table></td>
<td>
<table class="prcPrntTbl">
<tr><th colspan="4">Customer Information</th></tr>
<tr><td align="left"><B>Customer name:</b></td><td align="left"><%=info.getByrNm()%></td><td align="left"><b>Terms :</b></td><td align="left"><%=info.getTrms()%></td></tr>
<tr><td align="left">

<b>Email ID:</b></td><td align="left"><input type="hidden" name="byrEml" value="Y" /> <%=info.getByrEml()%></td><td align="left"><b>Sales person :</b></td><td align="left"><%=info.getSaleExNme()%></td></tr>
<tr>
<td align="left"><b>Mobile No:</b></td><td align="left"><bean:write property="value(mobile)" name="searchForm" /></td>
<td colspan="2">
<table cellpadding="2"><tr>
<td align="left"><b>Office No:</b></td><td align="left"><bean:write property="value(office)" name="searchForm" /></td>
<logic:notEqual property="value(trDis)"  name="searchForm"  value="" >
<td align="left"><b>Trade Discount:</b></td><td align="left"><bean:write property="value(trDis)" name="searchForm" /></td>
</logic:notEqual>
</tr></table></td>
</tr>
</table>
</td>


</tr>
</table>





</td>

</tr>





<tr>
<td valign="top">

<table><tr>
<% 
String exday="displayExDay(this,'"+cnt+"');";
%>
<td><span class="txtBold" > Select Memo Type : </span></td><td>
<html:select property="value(typ)" styleId="memoTyp" name="searchForm" onchange="<%=exday%>" >
<html:optionsCollection property="memoList" name="searchForm"
label="dsc" value="memo_typ" />
</html:select></td>
<td valign="top"><div id="expDay" <%=isExpDay%>>
<logic:present property="value(ExpDayList)" name="searchForm" >
<table><tr><td>
<span class="txtBold"> Expiry days : </span></td><td>
<html:select property="value(day)" name="searchForm" >
<html:optionsCollection property="value(ExpDayList)" name="searchForm" value="FORM_NME" label="FORM_TTL" />
</html:select></td>
<%if(util.nvl((String)info.getDmbsInfoLst().get("EXTME")).equals("Y")){ %>
<td><span class="txtBold">Expiry Time: </span></td><td>

<html:text property="value(extme)" name="searchForm" size="8" /> 

</td>
<%}else{%>
<html:hidden property="value(extme)" name="searchForm"  /> 
<%}%>
<%if(util.nvl((String)info.getDmbsInfoLst().get("EXTCONF")).equals("Y")){ %>
<td><span class="txtBold">Ext Conf %:</span></td><td><html:text property="value(extconf)" name="searchForm" size="10" /> </td> 
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
ArrayList prpValLst = (ArrayList)prp.get("WEB_BLOCKV");
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

<td valign="top"><div id="rmkOn" style="display:none">
<b>Remark/Comment</b>&nbsp;<html:text property="value(rmk)" styleId="rmkval" size="15" name="searchForm"  />
</div></td>

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
    <td nowrap="nowrap"><html:text property="<%=field%>" size="6" styleId="<%=typ%>" onchange="<%=onchang%>" name="searchForm"/></td>
    <td nowrap="nowrap">
    <%if(rmk.equals("Y")){%>
    Remark<html:text property="<%=fieldRmk%>" styleId="<%=fieldId%>"  onchange="<%=onchangrmk%>" name="searchForm" size="10"/>
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
            form_nme=util.nvl((String)pageDtlMap.get("form_nme"));
            fld_typ=(String)pageDtlMap.get("fld_typ");
            lov_qry=(String)pageDtlMap.get("lov_qry");
            dflt_val=(String)pageDtlMap.get("dflt_val");
            val_cond=(String)pageDtlMap.get("val_cond"); 
            val_cond=val_cond.replaceAll("PKTIDNLST",String.valueOf(pktListSize));
            if(form_nme.equals("MP")){
            if(fld_typ.equals("S")){
            %>
    <td><html:submit property="<%=fld_nme%>" value="<%=fld_ttl%>" onclick="<%=val_cond%>" styleClass="submit"/></td>
    <%}else if(fld_typ.equals("B")){%>
    <td><html:button property="<%=fld_nme%>" value="<%=fld_ttl%>" onclick="<%=val_cond%>" styleClass="submit"/></td>
    <%}else if(fld_typ.equals("HB")){%>
    <td align="right"><button type="button" onclick="<%=val_cond%>" class="submit" accesskey="<%=lov_qry%>" id="<%=fld_nme%>" name="<%=fld_nme%>" ><%=fld_ttl%></button></td>    
    <%}}}}
    %>

<td><a onclick="chkSele('<%=pktListSize%>','excel');"> Create Excel <img src="../images/ico_file_excel.png" id="img" title="Click here to create excel"/></a></td>
<td>Thru Person:</td><td><input type="text"  name="thruPer" size="20" /> <input type="hidden" id="CUR" value="<%=info.getRln()%>"/>  </td>
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

</tr></table> 

</td></tr>



<%
String srchRefMsg = (String)request.getAttribute("vnmNotFnd");
if(srchRefMsg!=null){%>
<tr><td><label class="redLabel" ><%=srchRefMsg%></label></td></tr>
<%}%>
<tr>
<td>
<jsp:include page="/marketing/stockView.jsp" />
</td></tr>
</table>

</td></tr></table></form><%}else{%>
Sorry no result found.
<%}}%>
</body>
</html>
  
  </body>
</html>