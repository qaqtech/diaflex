<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page errorPage="../error_page.jsp" %>
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import="java.util.ArrayList,java.util.ArrayList,java.util.HashMap, java.sql.*, java.util.Enumeration, java.util.logging.Level"%>
<jsp:useBean id="util" class="ft.com.JspUtil" scope="page" />
<jsp:useBean id="info" class="ft.com.InfoMgr" scope="session" />
<%@taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<html>
  <head>
 <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
  
  <title>Search Form</title>
  <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/bse.js?v=<%=info.getJsVersion()%> " > </script>
    <link href="<%=info.getReqUrl()%>/auto-search/auto-search.css?v=<%=info.getJsVersion()%>"
                rel="stylesheet" type="text/css"/>
<link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/bse.css?v=<%=info.getJsVersion()%>"/>
<link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/general.css?v=<%=info.getJsVersion()%>"/>
<script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/ajax.js?v=<%=info.getJsVersion()%> " > </script>
  <script type="text/javascript" src="<%=info.getReqUrl()%>/PopScripts/popup.js?v=<%=info.getJsVersion()%> " > </script>
    <script src="<%=info.getReqUrl()%>/PopScripts/jquery-1.2.6.min.js" type="text/javascript"></script>
     <script src="<%=info.getReqUrl()%>/PopScripts/jquery-1.11.3.min.js" type="text/javascript"></script>
   <script language="javaScript" type="text/javascript" src="<%=info.getReqUrl()%>/calendar/calendar.js"></script>
   <link href="<%=info.getReqUrl()%>/calendar/calendar.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/memoScript.js?v=<%=info.getJsVersion()%> " > </script>
       <script src="<%=info.getReqUrl()%>/scripts/spacecode.js?v=<%=info.getJsVersion()%>"></script>
       <script src="<%=info.getReqUrl()%>/scripts/rfid.js?v=<%=info.getJsVersion()%>"></script>
       <script src="<%=info.getReqUrl()%>/jquery/select2.min.js?v=129020"></script>
     <script src="<%=info.getReqUrl()%>/jquery/select2.js?v=129020" type="text/javascript"></script>

       <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/dispose.js?v=<%=info.getJsVersion()%> " > </script>
                            <script src="<%=info.getReqUrl()%>/jqueryScript/jqueryScript.js?v=<%=info.getJsVersion()%>" type="text/javascript"></script>
       <script src="<%=info.getReqUrl()%>/auto-search/auto-search.js?v=<%=info.getJsVersion()%>" type="text/javascript"></script>
           <script src="<%=info.getReqUrl()%>/auto-search/autoajaxjs.js?v=<%=info.getJsVersion()%>" type="text/javascript"></script>
     
      <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/style.css?v=<%=info.getJsVersion()%>"/>
      <style type="text/css">
     div label input {
   margin-right:100px;
}
#ck-button {
    margin:2px;
    background-color:#FFFFFF;
    border-radius:6px;
    border: 2px solid #ACACAC;
    overflow:auto;
    float:left;
}

#ck-button label {
   float:left;
   width: 70px;
   position:relative;
}

#ck-button label span {
    text-align:center;
    padding:3px 0px;
    display:block;
    border-radius:2px;
    font-size:11px;
}

#ck-button label input {
    position:absolute;
    top:-20px;
}

#ck-button input:hover + span {
    background-color:#ACACAC;
}

#ck-button input:checked + span {
    background-color:#5A5A5A;
    color:#fff;
}

#ck-button input:checked:hover + span {
    background-color:#5A5A5A;
    color:#fff;
}
.selectnew{
     border:1px solid  #ACACAC;
     /*1px rgb(99,99,99) solid ;*/
     font-family: Calibri, Arial, Helvetica, sans-serif;
     font-size: 12px;
     overflow: hidden; 
      height:18px;
     /*height:20px;*/
}
.textNew{
     border:1px solid  #ACACAC; /*1px rgb(99,99,99) solid ;*/
     font-family: Calibri, Arial, Helvetica, sans-serif;
     font-size: 12px;
     height:15px;
}
.txtBoldNew{
     font-family: Calibri, Arial, Helvetica, sans-serif;
     font-size:14px;
    
    color: #5A5A5A;
}
.lft-rgt-border{
border-left:1px solid #E6A16D;
border-right:1px solid #E6A16D
}
</style>

<script type="text/javascript">
window.onload = function()
{ 
for(var i=0; i<25; i++){
 initCheckBoxes('table'+i);
}
};
/* Click-n-Drag Checkboxes */
var gCheckedValue = null;
function initCheckBoxes(sTblId)
{
 xTableIterate(sTblId,
   function(td, isRow) {
   if (!isRow) {
      var cb = td.getElementsByTagName('input');
      if (cb && cb[0].type.toLowerCase() == 'checkbox') {
       td.checkBoxObj = cb[0];
        td.onmousedown = tdOnMouseDown;
         td.onmouseover = tdOnMouseOver;
       td.onclick = tdOnClick;
      }
    }
   }
 );
}
function tdOnMouseDown(ev)
{
 if (this.checkBoxObj) {
   gCheckedValue = this.checkBoxObj.checked = !this.checkBoxObj.checked;
   document.onmouseup = docOnMouseUp;
   document.onselectstart = docOnSelectStart; // for IE
   xPreventDefault(ev); // cancel text selection
 }
}
function tdOnMouseOver(ev)
{
 if (gCheckedValue != null && this.checkBoxObj) {
   this.checkBoxObj.checked = gCheckedValue;
 }
}
function docOnMouseUp()
{
 document.onmouseup = null;
 document.onselectstart = null;
 gCheckedValue = null;
}
function tdOnClick()
{
 // Cancel a click on the checkbox itself. Let it bubble up to the TD
 return false;
}
function docOnSelectStart(ev)
{
 return false; // cancel text selection
}
</script>
  </head>
 <%
 String  suggScript="";
 int counterstt=1;
 String isMix = util.nvl(info.getIsMix(),"NR");
  String searchView = util.nvl(info.getSearchView(),"searchView");
  HashMap dbinfo = info.getDmbsInfoLst();
  ArrayList ary = new ArrayList();
  String sz = (String)dbinfo.get("SIZE");
  ArrayList plusMiAlwList = info.getAllowPlMnLst();
  if(plusMiAlwList==null)
  plusMiAlwList=new ArrayList();
  plusMiAlwList.add("MIX_CLARITY");
    plusMiAlwList.add("BOX_ID");
      plusMiAlwList.add("BOX_TYP");
  int ctscountinline=Integer.parseInt(util.nvl((String)dbinfo.get("CTS_COUNT_INLINE"),"13"));
    int prpcountinline=Integer.parseInt(util.nvl((String)dbinfo.get("PRP_COUNT_INLINE"),"13"));
    int numcountinline=Integer.parseInt(util.nvl((String)dbinfo.get("NUM_COUNT_INLINE"),"15"));
    int advctscountinline=Integer.parseInt(util.nvl((String)dbinfo.get("ADV_CTS_COUNT_INLINE"),"13"));
    int advprpcountinline=Integer.parseInt(util.nvl((String)dbinfo.get("ADV_PRP_COUNT_INLINE"),"13"));
    int advnumcountinline=Integer.parseInt(util.nvl((String)dbinfo.get("ADV_NUM_COUNT_INLINE"),"15"));
    String  setDisplayFromMod= util.nvl((String)request.getAttribute("setDisplayFromMod"));
    String ColTabBasic="color:#ffffff";
    String ColTabpkt="";
    String displaybasic="";
    String displaypkt="display:none;";
    if(setDisplayFromMod.equals("Y")){
    String srchref=info.getSrchBaseOn();
    if(!srchref.equals("NA")){
    ColTabBasic="";
    displaybasic="display:none;";
    ColTabpkt="color:#ffffff";
    displaypkt="";
    }else{
    ColTabBasic="color:#ffffff";
    displaybasic="";
    ColTabpkt="";
    displaypkt="display:none;";
    }
    }
  HashMap mprp = info.getSrchMprp();
  ResultSet rs = null;
  ArrayList multiSrchLst = info.getMultiSrchLst();
  ArrayList dmdDis=new ArrayList();
  HashMap prp = info.getSrchPrp();
  ArrayList prpDspBlocked = info.getPageblockList();
  ArrayList srchPrp = info.getSrchPrpLst();
   ArrayList advPrpList = info.getAdvPrpLst();
    int topPrp = 0;
   if(isMix.equals("MIX")){
    srchPrp = info.getMixScrhLst();
    advPrpList = new ArrayList();
   }
   if(isMix.equals("SMX")){
    srchPrp = info.getSmxScrhLst();
    advPrpList = new ArrayList();
   }
    if(isMix.equals("RGH")){
    srchPrp = info.getRghScrhLst();
    advPrpList = new ArrayList();
   }
   ArrayList srchTypList = (session.getAttribute("srchTypList_"+isMix) == null)?new ArrayList():(ArrayList)session.getAttribute("srchTypList_"+isMix);
   ArrayList includeexcludeList = (session.getAttribute("INCLUDE_EXEC_VW") == null)?new ArrayList():(ArrayList)session.getAttribute("INCLUDE_EXEC_VW");
   ArrayList srchTypListDtl=new ArrayList();
  int counter=1;
  String lprp = "";
  String lprpTyp = "";
  String prpDsc ="";           
  String srt = "";
  String val="";
  String prt="";
  ArrayList prpPrt=null;
  ArrayList prpVal=null;
  ArrayList prpSrt = null;
  ArrayList fanCy = new ArrayList();
//    fanCy.add("INTENSITY");MAYUR FANCY
//     fanCy.add("OVERTONE");
//     fanCy.add("FANCY COLOR");
 ArrayList srchCy = new ArrayList();
    srchCy.add("COL");
    srchCy.add("COL-SHADE");
    String method = util.nvl(request.getParameter("method"));
  String SrchTyp = util.nvl(request.getParameter("Typ"));
     ArrayList selectedPrp = (ArrayList)request.getAttribute("selectedPrp");

  if(SrchTyp.equals("FCY")){
     topPrp = 3;
     info.setIsFancy("FCY");
  }
  if(info.getIsFancy().equals("FCY")){
    SrchTyp ="FCY";
    topPrp=3;
    }
    String isModify="N";
 if(util.nvl(request.getParameter("modify")).length() > 0 || request.getParameter("srchId")!=null)
   isModify = "Y";
   if(method.equals("loadDmd"))
    isModify = "Y";
     String Modify = (String)request.getAttribute("Modify");
    if(Modify!=null)
    isModify = "Y";
     String fld1 = "";
            String fld2 = "";
            String prpFld1="";
            String prpFld2="";
             String val1 = "";
             String val2= "";
            String dfltVal = "0" ;
             String onChg1 = "";
             String onChg2= "";
            String dfltDsp = "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;All&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
            int prpCount=0;
            String flg = "";
            String tdColor ="odd";
            String pgDef = "SEARCH_RESULT";
       if(isMix.equals("MIX"))
           pgDef = "MIXSRCH_RESULT";  
       if(isMix.equals("SMX"))
           pgDef = "SMXSRCH_RESULT"; 
        if(isMix.equals("RGH"))
        pgDef = "RGHSRCH_RESULT"; 
     HashMap allPageDtl= (info.getPageDetails() == null)?new HashMap():(HashMap)info.getPageDetails();
     HashMap pageDtl=(HashMap)allPageDtl.get(pgDef);
             ArrayList pageList=new ArrayList();
             HashMap pageDtlMap=new HashMap();
    String fld_nme="",fld_ttl="",val_cond="",dflt_val="",fld_typ="",form_nme="",lov_qry="";
        String logId=String.valueOf(info.getLogId());
        String onfocus="cook('"+logId+"')";
        %>
 <body onfocus="<%=onfocus%>" onload="MM_preloadImages('images/faunalogo.png')" topmargin="0" leftmargin="0" rightmargin="0" bottommargin="0">
    <input type="hidden" id="reqUrl" name="reqUrl" value="<%=info.getReqUrl()%>"/>
   <input type="hidden" id="deviceip" name="deviceip" value="<%=util.nvl((String)info.getSpaceCodeIp())%>"/>
<input type="hidden" id="cut" value="<%=dbinfo.get("CUT")%>" />
<input type="hidden" id="sym" value="<%=dbinfo.get("SYM")%>" />
<input type="hidden" id="pol" value="<%=dbinfo.get("POL")%>" />
<input type="hidden" id="cntac" value="<%=dbinfo.get("CNT")%>" />
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
  <img src="../images/line.jpg" border="0" /></td><td valign="middle"> <span class="pgHed">
 <%
 if(SrchTyp.equals("FCY")){%>
   Stock Search-Fancy Color
   <%}else{%>Stock Search<%}%>
 
  </span> </td>
</tr></table>
  </td>
  </tr>
  <%
  String msgDmd =request.getParameter("dmdMsg");
  if(msgDmd!=null){%>
    <tr><td valign="top" class="hedPg">
    <%=msgDmd%>
    </td></tr>
  <%}
  String memoMsg =(String)request.getAttribute("memoMsg");
  if(memoMsg!=null){%>
    <tr><td valign="top" class="hedPg">
     <span class="redLabel" ><%=memoMsg%></span>
    </td></tr>
  <%}%>
   <%
  String msgErr = (String)request.getAttribute("dmdMsg");
  if(msgErr!=null){
  isModify = "Y";
  %>
    <tr><td valign="top" class="hedPg">
   <span class="redLabel"> <%=msgErr%></span>
    </td></tr>
  <%}%>
  <tr><td valign="top" class="hedPg">
  
  
<html:form action="/marketing/StockSearch.do?method=srch" method="POST"  enctype="multipart/form-data" onsubmit="return checkRln()">
<html:hidden property="value(oldsrchId)" name="searchForm" styleId="oldsrchId"  />
<html:hidden property="value(listname)" styleId="listname" value="srchRef" />
<html:hidden property="value(createExlBtn)" name="searchForm" styleId="createExlBtn" value="N"  />
<html:hidden property="srchTyp" value="<%=SrchTyp%>" name="searchForm"  />
<input type="hidden"  name="svsrchId" id="svsrchId"/>
<div id="popupContactDmd" align="center" >
<html:hidden property="value(oldDmdId)" name="searchForm" />
<table align="center" cellpadding="10" cellspacing="10" class="pktTble1">
<tr><td>Demand Description </td><td><html:text property="value(dmdDsc)" size="30" onchange="checkDmdDsc()" styleId="dmdNme" name="searchForm" /> </td> </tr>
<%
if(multiSrchLst.size() > 0){
%>
<tr><td align="center"><table><tr>
<td><html:submit property="value(sv_dmd)" value="Save" onclick="" styleClass="submit" /> </td>
<td><button type="button" onclick="disablePopupDmd()" Class="submit" >Back</button> </td>
</tr></table></td>
</tr>
<%}
else{
%>
<tr><td colspan="3" align="center"><table><tr>
<td><html:submit property="value(pb_dmd)" value="Save" onclick="return valDmddsc();"  styleClass="submit" /> </td>
<td><html:submit property="value(upd_dmd)" value="Update" onclick="return valDmddsc();"  styleClass="submit" /> </td>
<td><button type="button" onclick="disablePopupDmd()" Class="submit" >Back</button> </td>
</tr></table></td>
</tr>
<%}%>
</table>
</div>
<select style="display:none" name="multiPrp" id="multiPrp">
<%
for(int s=0;s<srchPrp.size();s++){
   ArrayList srchLst = (ArrayList)srchPrp.get(s);
        lprp =(String)srchLst.get(0);
      flg=(String)srchLst.get(1);
      if(fanCy.contains(lprp)){
      }else{
       if(flg.equals("M")){
%>
<option value="<%=lprp%>"></option>
<%}}}
%>
</select>
<select style="display:none" name="fncyPrp" id="fncyPrp" >
<%
ArrayList fcVal = (ArrayList)prp.get("FANCY COLORV");
if(fcVal!=null){
for(int j=0;j<fcVal.size();j++){
String fcPrp =(String)fcVal.get(j);
if(fcPrp.indexOf(" ")>-1){
%>
<option value="<%=fcPrp%>"></option>
<%}}}%>
</select>
<select style="display:none" name="fncySnPrp" id="fncySnPrp" >
<%
ArrayList fcVal1 = (ArrayList)prp.get("FANCY COLORV");
if(fcVal1!=null){
for(int j=0;j<fcVal1.size();j++){
String fcPrp1 =(String)fcVal1.get(j);
if(fcPrp1.indexOf(" ")==-1){
%>
<option value="<%=fcPrp1%>"></option>
<%}}}%>
</select>

<table cellpadding="0" width="100%" class="topPrpTbl" cellspacing="3">
       <!--Start-->
<tr>
<td valign="top">
<table>
<tr>
<td><table>
<tr>
             <%String setUpbyrNm=(String)info.getSetUpbyrNm();
             if(setUpbyrNm.equals("")){%>
           <td nowrap="nowrap"><span class="txtBold">Employee : </span> </td>
           <td nowrap="nowrap"><html:select property="byrId" styleId="byrId" onchange="getEmployee(this)"  name="searchForm"   >
            <html:option value="0" >--select--</html:option>
            <html:optionsCollection property="byrList" name="searchForm" 
            label="byrNme" value="byrIdn" />
            </html:select></td>
             <td nowrap="nowrap"> <span class="txtBold" >Party: </span></td>
              <td nowrap="nowrap">      
         <html:text  property="value(partytext)" name="searchForm" styleId="partytext" style="width:180px" styleClass="sugBox" onkeypress="return disableEnterKey(event);" 
           onkeyup="doCompletionPartyNME('partytext', 'nmePop', '../ajaxAction.do?1=1&isSrch=Y&UsrTyp=BUYER', event)"
      onkeydown="setDownSerchpage('nmePop', 'party', 'partytext',event)" onclick="document.getElementById('partytext').autocomplete='off'"  />
      <img src="../images/addrow.jpg" width="17" height="17" title="Click To get Names" onclick="doCompletionMobilePartyNME('partytext', 'nmePop', '../ajaxAction.do?1=1&isSrch=Y&UsrTyp=BUYER')">
      <img src="../images/Delete.png" width="15" height="15" title="Click To Clear Names List" onclick="clearsugBoxDiv('partytext','party','nmePop','SRCH');">
      <html:hidden property="party" styleId="party"  />
  <div style="position: absolute;">
      <select id="nmePop" name="nmePop"
        style="display:none;300px;" 
        class="sugBoxDiv" 
        onKeyDown="setDownSerchpage('nmePop', 'party', 'partytext',event)" 
        onDblClick="setVal('nmePop', 'party', 'partytext', event);hideObj('nmePop'),getTrmsNME('party','SRCH');" 
        onClick="setVal('nmePop', 'party', 'partytext', event)" 
        size="10">
      </select>
</div>            
</td>
            <td nowrap="nowrap"> <span class="txtBold" >Terms: </span></td>
            <td nowrap="nowrap">
             <html:select property="byrRln" name="searchForm" styleId="rlnId" onchange="getDropDwonXrt(this)">
           <html:option value="0" >---select---</html:option>
             <html:optionsCollection property="trmList" name="searchForm" 
            label="trmDtl" value="relId" />
            </html:select>
            </td>
           <%}else{
            String setUppartyNm="",setUpTermNm="";
            String setUpbyrId,setUppartyId,setUpTermId;
            setUpbyrId=String.valueOf(info.getSetUpbyrId());
            setUppartyNm=info.getSetUppartyNm();
            setUppartyId=String.valueOf(info.getSetUppartyId());
            setUpTermId=String.valueOf(info.getSetUpTermId());
            setUpTermNm=info.getSetUpTermNm();
            %>
           <td nowrap="nowrap">
           <span class="txtBold">Employee : </span> </td>
           <td nowrap="nowrap">
           <label><b><%=setUpbyrNm%></b></label>
           <html:hidden property="byrId" name="searchForm" value="<%=setUpbyrId%>" />
           </td>
             <td nowrap="nowrap"> <span class="txtBold" >Party: </span></td>
             <td nowrap="nowrap">
             <label><b><%=setUppartyNm%></b></label>
           <html:hidden property="party" name="searchForm" value="<%=setUppartyId%>" />
            </td>
            <td nowrap="nowrap"> <span class="txtBold" >Terms: </span></td>
            <td nowrap="nowrap">
             <label><b><%=setUpTermNm%></b></label>
           <html:hidden property="byrRln" name="searchForm" value="<%=setUpTermId%>" />
            </td>
           <%}%>
           <td nowrap="nowrap" align="left">Exchange Rate: </td>
           <%if(setUpbyrNm.equals("")){%>
           <td nowrap="nowrap" align="left">
           <html:text property="value(xrt)" styleId="xrtId" size="5"  onchange="isNumericDecimal(this.id)" readonly="true"  name="searchForm" />
           </td>
           <%}else{
           String setUpEX=info.getSetUpEX();%>
           <td nowrap="nowrap">
           <label><b><%=setUpEX%></b></label>
           <html:hidden property="value(xrt)" name="searchForm" value="<%=setUpEX%>" />
           </td>
           <%}
           pageList= ((ArrayList)pageDtl.get("INBCHECK") == null)?new ArrayList():(ArrayList)pageDtl.get("INBCHECK");
             if(pageList!=null && pageList.size() >0){
              for(int j=0;j<pageList.size();j++){
                pageDtlMap=(HashMap)pageList.get(j);
                fld_nme="value("+(String)pageDtlMap.get("fld_nme")+")";
                fld_ttl=(String)pageDtlMap.get("fld_ttl");
                val_cond=(String)pageDtlMap.get("val_cond");
                dflt_val=(String)pageDtlMap.get("dflt_val");
             %>
          <td nowrap="nowrap"><%=fld_ttl%></td>  <td nowrap="nowrap"> <html:checkbox property="<%=fld_nme%>" value="<%=dflt_val%>" name="searchForm"   /> </td>
            <%}}
             pageList= ((ArrayList)pageDtl.get("FEEDBACK") == null)?new ArrayList():(ArrayList)pageDtl.get("FEEDBACK");
             if(pageList!=null && pageList.size() >0){
              for(int j=0;j<pageList.size();j++){
                pageDtlMap=(HashMap)pageList.get(j);
                fld_nme="value("+(String)pageDtlMap.get("fld_nme")+")";
                fld_ttl=(String)pageDtlMap.get("fld_ttl");
                val_cond=(String)pageDtlMap.get("val_cond");
                dflt_val=(String)pageDtlMap.get("dflt_val");
             %>
          <td nowrap="nowrap"><A onclick="openfeedbackfromsrch('party');" title="Feedback report">Feedback</td>
            <%}}%>  
</tr>
</table>
</td>
</tr>

<tr>
           <td colspan="2" nowrap="nowrap">
           <table cellpadding="0" cellspacing="0">
           <!--<tr>
           <td nowrap="nowrap" colspan="4" align="left"><span class="txtBold"> Search :</span></td>
           </tr>-->
           <tr>
           <td><input type="checkbox" name="AllStt" id="AllStt" onclick="checkAllpage(this,'SRCHSTT_')"/></td>
           <td>Check All&nbsp;</td>
           <%
           counterstt=1;
           for(int i=0;i<srchTypList.size();i++){
           srchTypListDtl=new ArrayList();
           srchTypListDtl=(ArrayList)srchTypList.get(i);
           String srchsttdsc=util.nvl((String)srchTypListDtl.get(0));
           String srchstt="value(SRCHSTT_"+util.nvl((String)srchTypListDtl.get(1))+")";
           String styleid="SRCHSTT_"+util.nvl((String)srchTypListDtl.get(1));%>
           <%if(counterstt==10){
           counterstt=0;%>
           </tr><tr>
           <%}
           counterstt++;%>
           <td nowrap="nowrap"> <html:checkbox property="<%=srchstt%>" styleId="<%=styleid%>" value="<%=util.nvl((String)srchTypListDtl.get(1))%>" name="searchForm"   /> </td>
           <td nowrap="nowrap"><%=srchsttdsc%> &nbsp;&nbsp;&nbsp;&nbsp;</td>
           <%}%>
           </tr>
           </table>
           </td>
</tr>
</table>
</td>
</tr>
<tr>
<td colspan="2">
<span class="txtLink" id="SRCH_TAB" style="<%=ColTabBasic%>" onclick="showHideDiv('.genericTB','SRCH',this)">Basic Search</span>
<% if(advPrpList!=null && advPrpList.size()>0){%>
<span class="txtLink" id="ADVSRCH_TAB" style="" onclick="showHideDiv('.genericTB','ADVSRCH',this)">Fancy/Advance Search</span>
<%}%>
<span class="txtLink" id="PACKET_TAB" style="<%=ColTabpkt%>" onclick="showHideDiv('.genericTB','PACKET',this)">Packet Search / Other Details</span>
<%pageList=(ArrayList)pageDtl.get("OPTION");
      if(pageList!=null && pageList.size() >0){%>
      Update Price By &nbsp;
      <html:select property="value(PRI_CHNG_TYP)" styleId="PRI_CHNG_TYP"  name="searchForm" >
      <%
      for(int j=0;j<pageList.size();j++){
      pageDtlMap=(HashMap)pageList.get(j);
      fld_ttl=(String)pageDtlMap.get("fld_ttl");
      val_cond=(String)pageDtlMap.get("val_cond");
      fld_typ=(String)pageDtlMap.get("fld_typ");
      dflt_val = (String)pageDtlMap.get("dflt_val");
      %>
      <html:option value="<%=dflt_val%>"><%=fld_ttl%></html:option>
      <%}%>
      </html:select>&nbsp;<html:text property="value(PRI_CHNG_VAL)" styleId="PRI_CHNG_VAL" onchange="validatePricerangeSearch();" size="10" name="searchForm"/>
      <%}%>
</td>
</tr>

<tr><td valign="top">
<table class="genericTB" id="PACKET"  style="<%=displaypkt%>">
<tr><td>
<table>
<tr>
             <%pageList=(ArrayList)pageDtl.get("FILE");
             if(pageList!=null && pageList.size() >0){
              for(int j=0;j<pageList.size();j++){
                pageDtlMap=(HashMap)pageList.get(j);
                fld_nme="value("+(String)pageDtlMap.get("fld_nme")+")";
                fld_ttl=(String)pageDtlMap.get("fld_ttl");
                val_cond=(String)pageDtlMap.get("val_cond");
                dflt_val=(String)pageDtlMap.get("dflt_val");
             %>
            <tr>
            <td nowrap="nowrap"> <span class="txtBold" ><%=fld_ttl%></span></td>
            <td nowrap="nowrap"><html:file property="fileUpload" size="40"   onchange="<%=val_cond%>" styleId="fileUpload"  name="searchForm" /> 
            </td>
            </tr>
            <tr>
            <td class="linebottom" valign="middle" colspan="2">&nbsp;</td>
            </tr>
            <%}}%>
            <!--<tr>
            <td nowrap="nowrap"> <span class="txtBold" >Excel/Offer Upload: </span></td>
            <td nowrap="nowrap"><html:file property="fileUpload" size="40"   onchange="check_file();" styleId="fileUpload"  name="searchForm" /> 
            </td>
            </tr>
            <tr>
            <td class="linebottom" valign="middle" colspan="2">&nbsp;</td>
            </tr>-->
            
            <tr><td colspan="2">
            <table  cellpadding="0" cellspacing="0"><tr>
            <td valign="top">
            <table cellpadding="0" cellspacing="0" >
            <tr><td><table cellpadding="1" cellspacing="0">
            <tr>
            <!--<td><html:radio property="value(srchRef)" styleId="vnm" value="vnm" /> </td><td>Packet Code</td>
            <td><html:radio property="value(srchRef)"  styleId="memoId"  value="memoId" /></td><td>List Number</td>
            <td><html:radio property="value(srchRef)"  styleId="cert_no" value="cert_no" /></td><td>Certificate Number</td>
            <td><html:radio property="value(srchRef)"  styleId="cert_no" value="none" /></td><td>None</td>-->
            <%pageList=(ArrayList)pageDtl.get("RADIO");
            if(pageList!=null && pageList.size() >0){
            for(int j=0;j<pageList.size();j++){
            pageDtlMap=(HashMap)pageList.get(j);
            fld_nme="value("+(String)pageDtlMap.get("fld_nme")+")";
            fld_ttl=(String)pageDtlMap.get("fld_ttl");
            fld_typ=(String)pageDtlMap.get("fld_typ");
            form_nme=(String)pageDtlMap.get("form_nme");
            dflt_val=(String)pageDtlMap.get("dflt_val");
            val_cond=(String)pageDtlMap.get("val_cond"); %>
            <td><html:radio property="<%=fld_nme%>" styleId="<%=fld_typ%>" value="<%=dflt_val%>"/></td><td nowrap="nowrap"><%=fld_ttl%></td>
            <%}}%>
                        <%pageList=(ArrayList)pageDtl.get("SOLD_SRCH");
            if(pageList!=null && pageList.size() >0){
            for(int j=0;j<pageList.size();j++){
            pageDtlMap=(HashMap)pageList.get(j);
            fld_nme=util.nvl((String)pageDtlMap.get("fld_nme"));
            fld_ttl=util.nvl((String)pageDtlMap.get("fld_ttl"),"Search + sold");
            fld_typ=(String)pageDtlMap.get("fld_typ");
            lov_qry=(String)pageDtlMap.get("lov_qry");
            dflt_val=(String)pageDtlMap.get("dflt_val");
            val_cond=(String)pageDtlMap.get("val_cond"); 
            if(fld_nme.equals("Y")){
            %>
            <td valign="top"> &nbsp;&nbsp;<span class="txtLink"><B> <%=fld_ttl%>  </b>   <input type="checkbox"  name="soldsrchPkt" id="soldsrchPkt" value="YES" onclick="" /></span></td>
            <%}}}%>
            </tr>
            </table>
            </td>
            </tr>
            <tr><td><table cellpadding="0" cellspacing="0"><tr><td colspan="2" nowrap="nowrap">Enter Number&nbsp;: <span id="srchRefCnt">0</span><br>
            </td><td colspan="6">
            <table><tr><td>
            &nbsp;<html:textarea property="value(srchRefVal)" styleId="srchRef"  onkeyup="getVnmCount('srchRef','srchRefCnt')" onchange="getVnmCount('srchRef','srchRefCnt')"  name="searchForm"  />
            <label id="fldCtr" ></label>
            </td>
            <td valign="middle">
            <%if(!isMix.equals("MIX")){%>
            <html:submit property="value(srchpkt)" value="Search"  styleClass="submit" />
            <%}else{%>
            <html:submit property="value(mixsrchpkt)" value="Search"  styleClass="submit" />
            <%}%>
            </td>
            </tr></table>
            </td> </tr></table></td></tr>
            <tr>
            <td valign="top">
            <%ArrayList rfiddevice=info.getRfiddevice();
             if(rfiddevice!=null && rfiddevice.size()>0){
             %>
            RFID Device: <html:select property="value(dvcLst)" styleId="dvcLst" name="searchForm"  >
             <%for(int j=0;j<rfiddevice.size();j++){
             ArrayList device=new ArrayList();
             device=(ArrayList)rfiddevice.get(j);
             String vale=(String)device.get(0);
             String disp=(String)device.get(1);
             %>
             <html:option value="<%=vale%>"><%=disp%></html:option>
             <%}%>
             </html:select>&nbsp;
             <%if(info.getRfid_seq().length()==0){%>
             <html:button property="value(rfScan)" value="RF ID Scan" styleId="rfScan" onclick="doScan('srchRef','fldCtr','dvcLst','SCAN')"  styleClass="submit" />
             <html:button property="value(autorfScan)" value="Auto Scan" styleId="autorfScan" onclick="doScan('srchRef','fldCtr','dvcLst','AUTOSCAN')"  styleClass="submit" />
            <%}%>
             <html:button property="value(stopAutorfScan)" value="Stop Auto Scan" onclick="doScan('srchRef','fldCtr','dvcLst','STOPAUTOSCAN')"  styleClass="submit" />
 
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
            </table></td></tr></table>
            </td>
            </tr>
            <%pageList=(ArrayList)pageDtl.get("FINDNOTSEEN");
            if(pageList!=null && pageList.size() >0){
            for(int j=0;j<pageList.size();j++){%>
            <tr>
            <td width="1px" class="linebottom" valign="middle" colspan="2">&nbsp;</td>
            </tr>
            <tr><td id="findNotseenPmDiv" colspan="2"> 
            <table cellspacing="1" cellpadding="1" id="findNotseenPmDiv">
            <tr>
            <td colspan="2" align="left">Find Not Seen
             <html:select property="value(callnotseenDrop)" name="searchForm" styleId="callnotseenDrop" onchange="enableFindNotSeenOption()"  >
             <html:option value="INCLUDE" >Include</html:option>
             <html:option value="EXCLUDE" >Exclude</html:option>
            </html:select>
            </td>
            </tr>
            <tr id="srchRefFindNotSeenExclude" style="display:none">
            <td>
            <html:select property="value(srchRefFindNotSeen)" name="searchForm" styleId="srchRefFindNotSeen" onchange=""  >
             <html:option value="vnm" >vnm</html:option>
             <html:option value="memoId" >memoId</html:option>
             <html:option value="cert_no" >cert_no</html:option>
            </html:select>
            </td>
            <td align="left">
            <html:textarea property="value(srchRefValFindNotSeen)" styleId="srchRefValFindNotSeen" onchange=""  name="searchForm"  />
            </td>
            </tr>
            </table>
            </td>
            </tr>
            <%}}%>
            
            <tr>
            <td width="1px" class="linebottom" valign="middle" colspan="2">&nbsp;</td>
            </tr>
            
            <tr><td id="wrDiv" colspan="2"> 
            <table cellspacing="1" cellpadding="1" id="WRTABLE"><tr><td>
            <%
            ArrayList webReqList= (request.getAttribute("webReqList") == null)?new ArrayList():(ArrayList)request.getAttribute("webReqList");
            if(webReqList!=null && webReqList.size()>0){%>
            <b>Web Request</b><input type="checkbox" id="WRALL" onclick="checkalladdInSrchRefBox()" /> ALL
           <% for(int i=0 ; i < webReqList.size() ; i++){
              HashMap webList = (HashMap)webReqList.get(i);
              String flg3=util.nvl((String)webList.get("flg3"));
              %>
           <input type="checkbox" onclick="addInSrchRefBox('WR_<%=util.nvl((String)webList.get("idn"))%>')" id="WR_<%=util.nvl((String)webList.get("idn"))%>" value="<%=util.nvl((String)webList.get("idn"))%>" /> <%=util.nvl((String)webList.get("idn"))%>(<%=util.nvl((String)webList.get("dte"))%>)
           <%if(!flg3.equals("NA")){%>
           (<%=util.nvl((String)webList.get("flg3"))%>)
            <%}}}%>
            </td>
            </tr> </table>
            </td></tr>
            
            
            <tr><td id="hrDiv" colspan="2"> 
            <table cellspacing="1" cellpadding="1" id="HRTABLE"><tr><td>
            <%
            ArrayList hrReqList= (request.getAttribute("hrReqList") == null)?new ArrayList():(ArrayList)request.getAttribute("hrReqList");
            if(hrReqList!=null && hrReqList.size()>0){%>
            <b>HH Request</b><input type="checkbox" id="HRALL" onclick="checkalladdInSrchRefBoxHR()" /> ALL
           <% for(int i=0 ; i < hrReqList.size() ; i++){
              HashMap hrList = (HashMap)hrReqList.get(i);
              String flg3=util.nvl((String)hrList.get("flg3"));
              %>
           <input type="checkbox" onclick="addInSrchRefBox('HR_<%=util.nvl((String)hrList.get("idn"))%>')" id="HR_<%=util.nvl((String)hrList.get("idn"))%>" value="<%=util.nvl((String)hrList.get("idn"))%>" /> <%=util.nvl((String)hrList.get("idn"))%>(<%=util.nvl((String)hrList.get("dte"))%>)
           <%if(!flg3.equals("NA")){%>
           (<%=util.nvl((String)hrList.get("flg3"))%>)
            <%}}}%>
            </td>
            </tr> </table>
            </td></tr>
            
            <tr>
            <td colspan="2">
            <%
            ArrayList grpNmeList = (ArrayList)request.getAttribute("grpNmeList");
            if(grpNmeList!=null && grpNmeList.size()>0){%>
             <table cellspacing="1" cellpadding="1"><tr><td>Watch List : </td><td>
             <html:select property="value(grpNme)" name="searchForm" styleId="grpNme"  >
             <%for(int a=0;a<grpNmeList.size();a++){
             String grp = (String)grpNmeList.get(a);
             %>
           <html:option value="<%=grp%>" ><%=grp%></html:option>
             <%}%>
             <html:option value="0" >--------Selet--------</html:option>
            </html:select></td>
            <td><html:submit property="value(pb_watch)"  styleClass="submit"  value="Load" /> </td> 
            </tr></table>
            <%}else{%>
            <div id="grpNmeDiv" >
            <table><tr><td>Watch List : </td><td>&nbsp;
            <html:select property="value(grpNme)" name="searchForm" styleId="grpNme" >
            <html:option value="0">--------Selet--------</html:option>
            </html:select></td>
            <td><html:submit property="value(pb_watch)" styleClass="submit" value="Load" /> </td>
            </tr></table> </div>
            <%}%>
            </td>
            </tr>
            
            </table>
            </td></tr>
            <!--Mayur-->
            
            <tr><td id="favDmd" colspan="2"> 
            <%
            ArrayList dmdList = (ArrayList)request.getAttribute("dmdList");
            if(dmdList!=null && dmdList.size()>0){%>
            <table cellspacing="1" cellpadding="1">
              <tr>
              <%--Start - changes for displaying demand list for ticket 3950--%>
                <td nowrap="nowrap"><span class="txtBold">Demand : </span></td>
                <td align="right">
                  <div class="multiplePrpdiv" id="dmdlist" align="center" style="display:none; overflow:auto; margin-left:0px; margin-top:20px; height:200px; width=240px; padding:0px;">
                  <table cellpadding="3" width="220px" class="multipleBg" cellspacing="3">
                  <tr><td colspan="3">
                    <input type="button" value="Select ALL" onclick="checkselect('dmdlist')" style="font-size:10px;" />
                    <input type="button" value="Clear All" style="font-size:10px; margin-bottom:5px;" onclick="uncheck('dmdlist')" />
                    <input type="button" id="cls" value="Close" onclick="Hide('dmdlist')" style="font-size:10px;"  />
                  </td>
                  </tr>
           <% for(int i=0 ; i < dmdList.size() ; i++){
              ArrayList dmd = (ArrayList)dmdList.get(i);
              String dmdId = (String)dmd.get(0);
              String dmdDsc = (String)dmd.get(1);
              String dmdChID = "Dmdch_"+dmdId;
              String dmdClick = "loadDmdPrp('','"+dmdId+"')";
              String onclick= "checkDMD(this,'"+dmdId+"')";
              String dmdDscID= "dmdDsc"+dmdId;


            %>
                  <tr>
                    <td><input type="checkbox" value="<%=dmdId%>" onclick="<%=onclick%>"  id="<%=dmdChID%>"/> </td>
                    <td><A onclick="<%=dmdClick%>"><label id="<%=dmdDscID%>"><%=dmdDsc%></label></a></td>
                  </tr>
            <%}%>
                  </table>
                  </div>
                  <table cellpadding="0" cellspacing="0">
                  <tr><td><input type="text" value="" disabled="disabled" id="multiTxtdm"  class="txtStyle" size="22" /></td><td>&nbsp;&nbsp;</td>
                    <td><img src="../images/plus.jpg" id="IMGD_dmdlist" class="img" onclick="DisplayDiv('dmdlist')" style="display:block" border="0"/></td>
                    <td><img src="../images/minus.jpg" id="IMGU_dmdlist" class="img" onclick="Hide('dmdlist')" style="display:none" border="0"/></td>
                  </tr>
                  </table>
                </td>
            <td><input type="button" value="View" class="submit" onclick="srchDmd('<%=info.getSrchPryID()%>','<%=info.getRlnId()%>')"  /> </td>
           </tr> </table>
            <%}%>
            </td> </tr>
            <tr><td id="lstMemoDiv" colspan="2" width="100%"> 
            
            
            <%
            counter=1;
            ArrayList lstMemoList= (request.getAttribute("lstMemoList") == null)?new ArrayList():(ArrayList)request.getAttribute("lstMemoList");
            if(lstMemoList!=null && lstMemoList.size()>0){%>
            <table  cellspacing="1" cellpadding="1" class="grid1" width="100%">
            <tr><td colspan="5" align="center" onclick="displayDiv('LstMemo')"><b>Last Memo's </b></td></tr>
            <tr><td><div id="LstMemo" style="margin:0px; padding:0px;"><table class="grid1" width="100%">
            <tr><th align="center">Sr No.</th><th align="center">Memo Id</th><th align="center">Qty</th><th align="center">Cts</th><th align="center">Typ</th><th align="center">Date</th></tr>
           <% for(int i=0 ; i < lstMemoList.size() ; i++){
            HashMap lstList = (HashMap)lstMemoList.get(i);%>
            <tr><td align="center"><%=i+1%></td>
            <td align="center"><%=util.nvl((String)lstList.get("idn"))%></td>
            <td align="right"><%=util.nvl((String)lstList.get("qty"))%></td>
            <td align="right"><%=util.nvl((String)lstList.get("cts"))%></td>
            <td align="right"><%=util.nvl((String)lstList.get("typ"))%></td>
            <td align="right"><%=util.nvl((String)lstList.get("dte"))%></td>
            </tr>
            <%}%>
            </table></div></td></tr>
            </table>
            <%}%>
            
            </td></tr>
            
            
            <tr><td id="lstMemoNGDiv" colspan="2" width="100%"> 
             <%
            counter=1;
            ArrayList lstMemoListNG= (request.getAttribute("lstMemoListNG") == null)?new ArrayList():(ArrayList)request.getAttribute("lstMemoListNG");
            if(lstMemoListNG!=null && lstMemoListNG.size()>0){%>
            <table  cellspacing="1" cellpadding="1" class="grid1" width="100%" id="memoNG">
            <tr><td colspan="5" align="center" onclick="displayDiv('LstMemoNG')"><b>Last New Goods Memo's</b></td></tr>
            <tr><td><div id="LstMemoNG" style="margin:0px; padding:0px;"><table class="grid1" width="100%">
            <tr><th align="center">Sr No.</th><th><input type="checkbox" name="ALL" id="ALL"  onclick="CheckAllMemo('ALL')"  /> </th>
            <th align="center">Memo Id</th><th align="center">Qty</th><th align="center">Cts</th><th align="center">Date</th></tr>
           <% for(int i=0 ; i < lstMemoListNG.size() ; i++){
            HashMap lstList = (HashMap)lstMemoListNG.get(i);
            String memoId = util.nvl((String)lstList.get("idn"));
            String cb_memo = "cb_memo_"+memoId; %>
            <tr><td align="center"><%=i+1%></td>
            <td align="center"><input type="checkbox" id="<%=cb_memo%>" value="<%=memoId%>" onclick="CheckAllMemo('<%=cb_memo%>')" /></td>
            <td align="center"><%=memoId%></td>
            <td align="right"><%=util.nvl((String)lstList.get("qty"))%></td>
            <td align="right"><%=util.nvl((String)lstList.get("cts"))%></td>
            <td align="right"><%=util.nvl((String)lstList.get("dte"))%></td>
            </tr>
            <%}%>
            </table></div></td></tr>
            </table>
            <%}%>
            
            
            
            </td></tr>

            <tr>
            <td>
            <%
            
            HashMap multiSrchMap = info.getMultiSrchMap();
              
            if(multiSrchLst.size() > 0 && (!method.equals("loadDmd"))){
            %>
            <table class="grid1" cellpadding="2"  cellspacing="0">
              <thead><tr><th nowrap="nowrap">Select</th><th nowrap="nowrap">Search Id</th><th nowrap="nowrap">Demand</th><th>Description</th></tr></thead>
              <tbody>
            <%
            for(int s=0;s<multiSrchLst.size();s++){
              String srchId = (String)multiSrchLst.get(s);
              String mutil = "ml_ch_"+srchId ;
              String srchDscr = util.nvl((String)multiSrchMap.get(srchId));
            %>
            
            <tr><td nowrap="nowrap"><input type="checkbox" name="<%=mutil%>" value="<%=srchId%>" checked="checked" /> </td> 
              <td nowrap="nowrap"><a onclick="loadSrch('<%=srchId%>')" class="img" title="Click here for update" href="#" > 
                <%=srchId%> </a> </td>
                <%dmdDis=info.getSvdmdDis();
                if(dmdDis.size()==0 || (!dmdDis.contains(String.valueOf(srchId)))){
                %>
                <td nowrap="nowrap">
                <a onclick="loadSVDmd('<%=srchId%>')" class="img" title="Click here to Save Demand" href="#" > Save</a> </td>
                <%}else{%>
                <td></td>
                <%}%>
                <td><%=srchDscr%></td> </tr>
           <% } %>
            </tbody>
            </table>
          <%  }
            %>
            </td>
            </tr>
             <tr><td  colspan="2" width="100%"> 
             <div id="conDtlDiv">
            <%
           
            ArrayList contDtlList= (request.getAttribute("contDtlList") == null)?new ArrayList():(ArrayList)request.getAttribute("contDtlList");
            if(contDtlList!=null && contDtlList.size()>0){%>
            <table class="grid1" width="100%">
            <% for(int i=0 ; i < contDtlList.size() ; i++){
            HashMap contDtl = (HashMap)contDtlList.get(i);%>
            <tr><td> <b>Sales person :</b></td><td><%=contDtl.get("emp")%> </td></tr>
            <tr><td><b>Email Id :</b></td><td><%=contDtl.get("eml")%> </td></tr>
            <tr><td><b>Mobile :</b></td><td><%=contDtl.get("mbl")%> </td></tr>
            <tr><td><b>Office No :</b></td><td><%=contDtl.get("ofc")%> </td></tr>
            <%}%>
            </table>
            <%}%>
            </div>
            </td></tr>
</table>
</td>
</tr>
</table>

<table class="genericTB" id="SRCH" style="<%=displaybasic%>">
     <tr><td>
      <table><tr>
            <%if(!isMix.equals("MIX") && !isMix.equals("SMX") && !isMix.equals("RGH")){%>
            <td>
            <table  cellpadding="0" cellspacing="0"><tr>
            <td valign="top">
            <table align="left" cellpadding="1" cellspacing="1" class="pktTble">
            
            <!--<tr><td>Excel Upload </td></tr>
            <tr><td><html:file property="fileUpload" size="40"   onchange="check_file();" styleId="fileUpload"  name="searchForm" /> </td> </tr>-->
             <tr><td>
             <table cellpadding="0" cellspacing="0">
         <tr>
         <!--<td> <html:checkbox property="value(is2Ex)" value="is2Ex"   styleId="is2Ex" name="searchForm" onclick="setEX('is2Ex')" />2 EX</td>
         <td> <html:checkbox property="value(is3Ex)" value="is3Ex"   styleId="is3Ex" name="searchForm" onclick="setEX('is3Ex')" />3 EX</td>
         <td> <html:checkbox property="value(is3VG)" value="is3VG"   styleId="is3VG" name="searchForm" onclick="setEX('is3VG')" />3 VG</td>-->
             <%pageList=(ArrayList)pageDtl.get("CHECKBOX_EXVG");
            if(pageList!=null && pageList.size() >0){
            for(int j=0;j<pageList.size();j++){
            pageDtlMap=(HashMap)pageList.get(j);
            fld_nme="value("+(String)pageDtlMap.get("fld_nme")+")";
            fld_ttl=(String)pageDtlMap.get("fld_ttl");
            fld_typ=(String)pageDtlMap.get("fld_typ");
            lov_qry=(String)pageDtlMap.get("lov_qry");
            dflt_val=(String)pageDtlMap.get("dflt_val");
            val_cond=(String)pageDtlMap.get("val_cond"); 
            %>
            <td> <html:checkbox property="<%=fld_nme%>" value="<%=dflt_val%>"   styleId="<%=fld_typ%>" name="searchForm" onclick="<%=val_cond%>" /><%=fld_ttl%></td>
             <%}}%>
             
             <%pageList=(ArrayList)pageDtl.get("SOLD_SRCH");
            if(pageList!=null && pageList.size() >0){
            for(int j=0;j<pageList.size();j++){
            pageDtlMap=(HashMap)pageList.get(j);
            fld_nme=util.nvl((String)pageDtlMap.get("fld_nme"));
            fld_ttl=util.nvl((String)pageDtlMap.get("fld_ttl"),"Search + sold");
            fld_typ=(String)pageDtlMap.get("fld_typ");
            lov_qry=(String)pageDtlMap.get("lov_qry");
            dflt_val=(String)pageDtlMap.get("dflt_val");
            val_cond=(String)pageDtlMap.get("val_cond"); 
            if(fld_nme.equals("Y")){
            %>
          <td valign="top"> &nbsp;&nbsp;<span class="txtLink"><B> <%=fld_ttl%>  </b>   <input type="checkbox"  name="soldsrch" id="soldsrch" value="YES" onclick="ShowHIDDiv('soldDay_div')" /></span>
        </td><td><div id="soldDay_div" style="display:none"> <b> Last</b> <input  type="text" name="sold_day"  size="4"  id="sold_day" value="<%=dflt_val%>" />
         <b> Days</b> </div>
          </td>
            <%}}}%>
             
             </tr>
             </table>
             </td></tr>
            </table>
            </td>
            </tr></table></td>
            <%}%>
      <%if(!isMix.equals("MIX") &&  !isMix.equals("RGH")){
        if(multiSrchLst.size() <= 0){
        %>
      <!--<td valign="top"><html:submit property="value(srch)" value="Search"  styleClass="submit" /></td>
      <td valign="top"><html:reset property="value(srch)" value="Reset"  styleClass="submit" /></td>
      <%if(info.getDmbsInfoLst().get("CNT").equals("hz")){%>
      <td valign="top"><html:submit property="value(pairs)" value="Match Pairs"  styleClass="submit" /></td>
      <%}%>-->
      
      
      <%pageList=(ArrayList)pageDtl.get("PG_BUTTON_1");
            if(pageList!=null && pageList.size() >0){
            for(int j=0;j<pageList.size();j++){
            pageDtlMap=(HashMap)pageList.get(j);
            fld_nme="value("+(String)pageDtlMap.get("fld_nme")+")";
            fld_ttl=(String)pageDtlMap.get("fld_ttl");
            fld_typ=(String)pageDtlMap.get("fld_typ");
            lov_qry=(String)pageDtlMap.get("lov_qry");
            dflt_val=(String)pageDtlMap.get("dflt_val");
            val_cond=(String)pageDtlMap.get("val_cond"); 
            if(fld_typ.equals("S")){
            %>
    <td valign="top"><html:submit property="<%=fld_nme%>" value="<%=fld_ttl%>" onclick="<%=val_cond%>" styleClass="submit"/></td>
    <%}else if(fld_typ.equals("B")){%>
    <td valign="top"><html:button property="<%=fld_nme%>" value="<%=fld_ttl%>" onclick="<%=val_cond%>" styleClass="submit"/></td>
    <%}else if(fld_typ.equals("R")){%>
   <td valign="top"><html:reset property="<%=fld_nme%>" value="<%=fld_ttl%>"  styleClass="submit" /></td>
    <%}}}%>
    <%}%>
    <!--<td valign="top" > <html:submit property="value(pb_multiSrch)" value="Add Search Criteria" onclick="" styleClass="submit" /></td>-->
    <%pageList=(ArrayList)pageDtl.get("PG_BUTTON_2");
            if(pageList!=null && pageList.size() >0){
            for(int j=0;j<pageList.size();j++){
            pageDtlMap=(HashMap)pageList.get(j);
            fld_nme="value("+(String)pageDtlMap.get("fld_nme")+")";
            fld_ttl=(String)pageDtlMap.get("fld_ttl");
            fld_typ=(String)pageDtlMap.get("fld_typ");
            lov_qry=(String)pageDtlMap.get("lov_qry");
            dflt_val=(String)pageDtlMap.get("dflt_val");
            val_cond=(String)pageDtlMap.get("val_cond"); 
            if(fld_typ.equals("S")){
            %>
    <td valign="top"><html:submit property="<%=fld_nme%>" value="<%=fld_ttl%>" onclick="<%=val_cond%>" styleClass="submit"/></td>
    <%}else if(fld_typ.equals("B")){%>
    <td valign="top"><html:button property="<%=fld_nme%>" value="<%=fld_ttl%>" onclick="<%=val_cond%>" styleClass="submit"/></td>
    <%}else if(fld_typ.equals("R")){%>
   <td valign="top"><html:reset property="<%=fld_nme%>" value="<%=fld_ttl%>"  styleClass="submit" /></td>
    <%}}}%>
    <%
    if(multiSrchLst.size() > 0){
    %>
    <!--<td valign="top"><html:submit property="value(pb_updateSrch)" value="Update Search Criteria" onclick="return checkoldSrchId()" styleClass="submit" />  </td>
   <td valign="top"><html:submit property="value(pb_rslt)" value="View Result" onclick="" styleClass="submit" /> </td>-->
   <%pageList=(ArrayList)pageDtl.get("PG_BUTTON_3");
            if(pageList!=null && pageList.size() >0){
            for(int j=0;j<pageList.size();j++){
            pageDtlMap=(HashMap)pageList.get(j);
            fld_nme="value("+(String)pageDtlMap.get("fld_nme")+")";
            fld_ttl=(String)pageDtlMap.get("fld_ttl");
            fld_typ=(String)pageDtlMap.get("fld_typ");
            lov_qry=(String)pageDtlMap.get("lov_qry");
            dflt_val=(String)pageDtlMap.get("dflt_val");
            val_cond=(String)pageDtlMap.get("val_cond"); 
            if(fld_typ.equals("S")){%>
    <td valign="top"><html:submit property="<%=fld_nme%>" value="<%=fld_ttl%>" onclick="<%=val_cond%>" styleClass="submit"/></td>
    <%}else if(fld_typ.equals("B")){%>
    <td valign="top"><html:button property="<%=fld_nme%>" value="<%=fld_ttl%>" onclick="<%=val_cond%>" styleClass="submit"/></td>
    <%}else if(fld_typ.equals("R")){%>
   <td valign="top"><html:reset property="<%=fld_nme%>" value="<%=fld_ttl%>"  styleClass="submit" /></td>
    <%}}}%>
   <%}
   if(multiSrchLst.size() <= 0){
        %>
  <!--<td valign="top"><html:button property="value(pbDmd)" value="Save/Update Demand" onclick="loadDmd()" styleClass="submit" /> </td>
  <td><html:submit property="value(pb_watch)" value="Load Watch List" onclick="" styleClass="submit" /></td>-->
   <%pageList=(ArrayList)pageDtl.get("PG_BUTTON_4");
            if(pageList!=null && pageList.size() >0){
            for(int j=0;j<pageList.size();j++){
            pageDtlMap=(HashMap)pageList.get(j);
            fld_nme="value("+(String)pageDtlMap.get("fld_nme")+")";
            fld_ttl=(String)pageDtlMap.get("fld_ttl");
            fld_typ=(String)pageDtlMap.get("fld_typ");
            lov_qry=(String)pageDtlMap.get("lov_qry");
            dflt_val=(String)pageDtlMap.get("dflt_val");
            val_cond=(String)pageDtlMap.get("val_cond"); 
            if(fld_typ.equals("S")){%>
    <td valign="top"><html:submit property="<%=fld_nme%>" value="<%=fld_ttl%>" onclick="<%=val_cond%>" styleClass="submit"/></td>
    <%}else if(fld_typ.equals("B")){%>
    <td valign="top"><html:button property="<%=fld_nme%>" value="<%=fld_ttl%>" onclick="<%=val_cond%>" styleClass="submit"/></td>
    <%}else if(fld_typ.equals("R")){%>
   <td valign="top"><html:reset property="<%=fld_nme%>" value="<%=fld_ttl%>"  styleClass="submit" /></td>
    <%}}}%>
  <% }%>
    <!--<td align="left"><html:submit property="value(crt_excel)" value="Create Excel"  styleClass="submit" /> </td>
    <td align="left"><html:submit property="value(mail_excel)"  value="Mail Excel"  styleClass="submit" /> </td>
            <%
              if(info.getDmbsInfoLst().get("CNT").equals("sd")){%>
<td align="left"><html:button property="value(rap_excel)" onclick="newWindow('StockSearch.do?method=genRapExcel')" value="Mail Rap Excel" styleClass="submit" /> </td>
    <%}%>-->
    <%pageList=(ArrayList)pageDtl.get("PG_BUTTON_5");
            if(pageList!=null && pageList.size() >0){
            for(int j=0;j<pageList.size();j++){
            pageDtlMap=(HashMap)pageList.get(j);
            fld_nme="value("+(String)pageDtlMap.get("fld_nme")+")";
            fld_ttl=(String)pageDtlMap.get("fld_ttl");
            fld_typ=(String)pageDtlMap.get("fld_typ");
            lov_qry=(String)pageDtlMap.get("lov_qry");
            dflt_val=(String)pageDtlMap.get("dflt_val");
            val_cond=(String)pageDtlMap.get("val_cond"); 
            if(fld_typ.equals("S")){%>
    <td valign="top"><html:submit property="<%=fld_nme%>" value="<%=fld_ttl%>" onclick="<%=val_cond%>" styleClass="submit"/></td>
    <%}else if(fld_typ.equals("B")){%>
    <td valign="top"><html:button property="<%=fld_nme%>" value="<%=fld_ttl%>" onclick="<%=val_cond%>" styleClass="submit"/></td>
    <%}else if(fld_typ.equals("R")){%>
   <td valign="top"><html:reset property="<%=fld_nme%>" value="<%=fld_ttl%>"  styleClass="submit" /></td>
    <%}}}%>
    <%}else if(isMix.equals("RGH")){%>
    
    <td valign="top"><html:submit property="value(rghSrch)" value="View Rough Result" styleClass="submit" /></td>
     
   <% }else{%>
      <td valign="top"><html:submit property="value(mixsrch)" value="View Mix Result" styleClass="submit" /></td>
     
       <%pageList=(ArrayList)pageDtl.get("PG_BUTTON_MIX");
            if(pageList!=null && pageList.size() >0){
            for(int j=0;j<pageList.size();j++){
            pageDtlMap=(HashMap)pageList.get(j);
            fld_nme="value("+(String)pageDtlMap.get("fld_nme")+")";
            fld_ttl=(String)pageDtlMap.get("fld_ttl");
            fld_typ=(String)pageDtlMap.get("fld_typ");
            lov_qry=(String)pageDtlMap.get("lov_qry");
            dflt_val=(String)pageDtlMap.get("dflt_val");
            val_cond=(String)pageDtlMap.get("val_cond"); 
            if(fld_typ.equals("S")){%>
    <td valign="top"><html:submit property="<%=fld_nme%>" value="<%=fld_ttl%>" onclick="<%=val_cond%>" styleClass="submit"/></td>
    <%}else if(fld_typ.equals("B")){%>
    <td valign="top"><html:button property="<%=fld_nme%>" value="<%=fld_ttl%>" onclick="<%=val_cond%>" styleClass="submit"/></td>
    <%}else if(fld_typ.equals("R")){%>
   <td valign="top"><html:reset property="<%=fld_nme%>" value="<%=fld_ttl%>"  styleClass="submit" /></td>
    <%}}}%>
     <%}%>
     
     <%
  ArrayList rolenmLst=(ArrayList)info.getRoleLst();
  String usrFlg=util.nvl((String)info.getUsrFlg());
  if(usrFlg.equals("SYS") || rolenmLst.contains("ADMIN")){
  if(isMix.equals("") || isMix.equals("NR")){
  %>
  <td><img src="../images/edit.jpg" title="Edit Properties" onclick="newWindow('<%=info.getReqUrl()%>/dynamicProperty.do?method=load&mdl=MEMO_SRCH&sname=&par=SRCH')" border="0" width="15px" height="15px"/> </td>
  <%}else if(isMix.equals("SMX")){%>
  <td><img src="../images/edit.jpg" title="Edit Properties" onclick="newWindow('<%=info.getReqUrl()%>/dynamicProperty.do?method=load&mdl=SMX_SRCH&sname=&par=SMX')" border="0" width="15px" height="15px"/> </td>
  <%}else if(isMix.equals("RGH")){%>
    <td><img src="../images/edit.jpg" title="Edit Properties" onclick="newWindow('<%=info.getReqUrl()%>/dynamicProperty.do?method=load&mdl=RGHMKT_SRCH&sname=&par=RGH')" border="0" width="15px" height="15px"/> </td>
    <%} else{%>
   <td><img src="../images/edit.jpg" title="Edit Properties" onclick="newWindow('<%=info.getReqUrl()%>/dynamicProperty.do?method=load&mdl=MIX_SRCH&sname=&par=MIX')" border="0" width="15px" height="15px"/> </td>
  <%}}%>  

<td>
<span class="" id="" style="" onclick="" title="Search Form By Grid View"><img src="../images/grid.jpg" id="" class="img" width="20" height="24" onclick="goTo('StockSearch.do?method=load&form=<%=util.nvl(info.getIsMix(),"NR")%>&searchView=GRID','','')" style="display:block" border="0"/></span>
</td>
<td>
<span class="" id="" style="" onclick="" title="Search Form By List View"><img src="../images/ListView.png" id="" width="20" height="20" class="img" onclick="goTo('StockSearch.do?method=load&form=<%=util.nvl(info.getIsMix(),"NR")%>&searchView=LIST','','')" style="display:block" border="0"/></span>
</td>
</tr></table></td></tr>

<%if(searchView.equals("LIST")){%>
<!--Criteria-->
<% if(srchPrp!=null && srchPrp.size()>0){%>
<tr>
<td>
<table cellspacing="0px" bgcolor="#ededed" class="genericOddEven" >
<%for(int i=topPrp ;i<srchPrp.size();i++){
             ArrayList srchLst = (ArrayList)srchPrp.get(i);
             lprp = (String)srchLst.get(0);
             flg=(String)srchLst.get(1);
             lprpTyp = util.nvl((String)mprp.get(lprp+"T"));   
             if(!flg.equals("CTA")){
             if(lprpTyp.equals("C") || lprp.equals("CRTWT") || flg.equals("KT")){
             if(!flg.equals("KT"))
             flg="M";
             prpDsc = (String)mprp.get(lprp+"D");
             if(prpDsc==null)
                prpDsc =lprp;
             prpPrt = (ArrayList)prp.get(lprp+"P");
             prpSrt = (ArrayList)prp.get(lprp+"S");
             prpVal = (ArrayList)prp.get(lprp+"V");
               fld1 = lprp+"_1";
              fld2 = lprp+"_2";

              prpFld1 = "value(" + fld1 + ")" ;
              prpFld2 = "value(" + fld2 + ")" ; 
             
              onChg1 = "setFrTo('"+ fld1 + "','" + fld2 + "')";
              onChg2 = "setFrTo('"+ fld1 + "','" + fld2 + "')";
              String blockprp="";
             if(prpDspBlocked.contains(lprp)){
             blockprp="style=\"display:none\"";
             }else{
             prpCount++;
             }
            %>
            <tr <%=blockprp%>>
            <td align="center" nowrap="nowrap"><span class="txtBoldNew"> <%=prpDsc%> </span>
            <%
            if(includeexcludeList.contains(lprp) || flg.equals("KT")){
            String incexc= "value(INC_"+ lprp +")";
            String incexcName= "INC_"+ lprp;
            String checkedI="checked=\"checked\"";
            String checkedE="";
            if(flg.equals("KT"))
            checkedE="checked=\"checked\"";
            checkedI="";
            %>
            <br>
            <span title="Select Checkbox To Exclude">
            <input type="radio" name="<%=incexcName%>" id="red" value="I" <%=checkedI%>>&nbsp;Include &nbsp;
            <input type="radio" name="<%=incexcName%>" id="red" value="E" <%=checkedE%>>&nbsp; Exclude
            </span>
            <%}%>
            </td>
              <% if(flg.equals("M") || flg.equals("KT")){
              if(flg.equals("KT")){
              HashMap ktsViewPrpDtls = (info.getKtsViewPrpDtls() == null)?new HashMap():(HashMap)info.getKtsViewPrpDtls();
              prpPrt = (ArrayList)ktsViewPrpDtls.get("KTSCUSTOMP");
              prpSrt = (ArrayList)ktsViewPrpDtls.get("KTSCUSTOMS");
              prpVal = (ArrayList)ktsViewPrpDtls.get("KTSCUSTOMV");
              }
              onChg1 = "setFrTo('"+ fld1 + "','" + fld2 + "');setFrToCheck('"+ fld1 + "','" + fld2 + "','"+lprp+"')\"";
              onChg2 = "setFrTo('"+ fld1 + "','" + fld2 + "');setFrToCheck('"+ fld1 + "','" + fld2 + "','"+lprp+"')\"";
             String mutiTextId = "MTXT_"+lprp;
             String mutiText = "value("+ mutiTextId +")";
             if(lprp.equals("CRTWT")){
              String hidCwt="MTXT_"+lprp;
              String loadStr="ALL";
              String txtBoxOnChg1="writeText(this,'"+hidCwt+"','1');";
               String txtBoxOnChg2="writeText(this,'"+hidCwt+"','2');";%>
               <td class="lft-rgt-border">
                 <%if(isModify.equals("Y")){%>
                <html:hidden property="<%=mutiText%>"  name="searchForm"  style="width:100px" styleId="<%=mutiTextId%>" styleClass="txtStyle" />
               <%}else{%>
                <html:hidden property="<%=mutiText%>" value="<%=loadStr%>" style="width:100px" name="searchForm"   styleId="<%=mutiTextId%>" styleClass="txtStyle" />
               <%}%>
               <table>
               <tr>
               <td><html:text property="value(wt_fr_c)"  style="width:100px" styleId="wt_fr_c" onchange="<%=txtBoxOnChg1%>"  styleClass="textNew" name="searchForm"/></td>
               <td><html:text property="value(wt_to_c)"  style="width:97px" styleId="wt_to_c" onchange="<%=txtBoxOnChg2%>" styleClass="textNew" name="searchForm"/></td>
               </tr>
               </table>
               </td>
             <%int cnt=0;
             ArrayList crtwtPrp = (info.getCrtwtPrpLst() == null)?new ArrayList():(ArrayList)info.getCrtwtPrpLst();
            for(int crt=0;crt<crtwtPrp.size();crt++) {
            ArrayList crtPrp=(ArrayList)crtwtPrp.get(crt);
            String srtWt = util.nvl((String)crtPrp.get(0));
            String valWt = util.nvl((String)crtPrp.get(1));
            String wt_fr = util.nvl((String)crtPrp.get(2));
            String wt_to = util.nvl((String)crtPrp.get(3));
             String val_fr = "" ;
            String val_to = "" ;
            String isChecked = "" ;
            String szPrp = "CRTWT";
            String cwtFr ="value("+ szPrp + "_1_"+srtWt+")";
            String cwtTo ="value("+ szPrp + "_2_"+srtWt+")";
            String checkId = szPrp + "_"+srtWt;
            String cwtToTxtId = szPrp + "_2_"+srtWt;
            String cwtFrTxtId = szPrp + "_1_"+srtWt;
            String chkFldId = "value("+ checkId + ")";
            String onclick = "setCrtWtSrch("+srtWt+","+wt_fr+", "+wt_to+",this, MTXT_"+lprp+" )";
            if(cnt==ctscountinline){%>
            </tr><tr><td></td><td class="lft-rgt-border"></td>
            <% cnt=0;}
            cnt++;
            %>
            <td nowrap="nowrap">
            <table>
            <tr>
            <td>
             <div id="ck-button" >
             <label style="">
            <html:checkbox property="<%=chkFldId%>"  name="searchForm" styleId="<%=checkId%>" value="<%=valWt%>"  onclick="<%=onclick%>" ></html:checkbox>
            <span><%=valWt%></span>
            </label>
            </div>
            </td>
            </tr>
            <tr>
            <td align="center">
            <html:text property="<%=cwtFr%>" size="6" styleId="<%=cwtFrTxtId%>" styleClass="textNew"  />
            </td>
            </tr>
            <tr>
            <td align="center">
            <html:text property="<%=cwtTo%>" size="6" styleId="<%=cwtToTxtId%>" styleClass="textNew"   />
            </td>
            </tr>
            </table>
            </td>
            <%}%>
               <%}else{%>
               <% if(prpSrt != null) {
               String loadStrL = "ALL";
               %>
               <td class="lft-rgt-border">
               <table>
               <tr>
               <%
               String displaySelectNone="";
               if(flg.equals("KT")){
               displaySelectNone="display:none";
               }%>
               <td style="<%=displaySelectNone%>">
                   <%if(isModify.equals("Y")){%>
                <html:hidden property="<%=mutiText%>"  name="searchForm"  style="width:100px" styleId="<%=mutiTextId%>" styleClass="txtStyle" />
                <%}else{%>
                <html:hidden property="<%=mutiText%>" value="<%=loadStrL%>" name="searchForm"  style="width:100px" styleId="<%=mutiTextId%>" styleClass="txtStyle" />
                <%}%>
             <html:select  property="<%=prpFld1%>" name="searchForm"  style="width:100px" onchange="<%=onChg1%>" styleId="<%=fld1%>" styleClass="selectnew"> 
     
            <html:option value="<%=dfltVal%>"><%=dfltDsp%></html:option>
            <%
            for(int j=0; j < prpSrt.size(); j++) {
                String pPrt = (String)prpPrt.get(j);
                String pSrt = (String)prpSrt.get(j);
                               String vall = (String)prpVal.get(j);
                String plusminusvall =(vall.replaceAll("\\+","")).replaceAll("\\-","");
                int indexplusminusvall=prpVal.indexOf((vall.replaceAll("\\+","")).replaceAll("\\-",""));
                if(plusMiAlwList.contains(lprp))
                indexplusminusvall=-1;
                                
                if(indexplusminusvall==-1 || vall.equals(plusminusvall)){
                  String isSelected = "" ;
               if(pSrt.equals(val1))
                   isSelected = "selected=\"selected\"";
            %>
              <html:option value="<%=pSrt%>" ><%=pPrt%></html:option> 
            <%}}%>
              </html:select>
              </td>
             <td style="<%=displaySelectNone%>">
            <html:select property="<%=prpFld2%>" name="searchForm"  style="width:100px" onchange="<%=onChg2%>" styleId="<%=fld2%>"  styleClass="selectnew" > 
     
            <html:option value="<%=dfltVal%>"><%=dfltDsp%></html:option>
            <%
            for(int j=0; j < prpSrt.size(); j++) {
                String pPrt = (String)prpPrt.get(j);
                String pSrt = (String)prpSrt.get(j);
                String vall = (String)prpVal.get(j);
                String plusminusvall =(vall.replaceAll("\\+","")).replaceAll("\\-","");
                int indexplusminusvall=prpVal.indexOf((vall.replaceAll("\\+","")).replaceAll("\\-",""));
                 if(plusMiAlwList.contains(lprp))
                indexplusminusvall=-1;
                if(indexplusminusvall==-1 || vall.equals(plusminusvall)){
                  String isSelected = "" ;
               if(pSrt.equals(val1))
                   isSelected = "selected=\"selected\"";
            %>
              <html:option value="<%=pSrt%>" ><%=pPrt%></html:option> 
            <%}}%>
              </html:select>   
              </td>
              </tr>
              </table>
               </td>
             <%
             int listCnt=0;
             for(int m=0;m<prpSrt.size();m++){
                String isSelected = "";
                String pPrtl = (String)prpPrt.get(m);
                String pSrtl = (String)prpSrt.get(m);
                String vall = (String)prpVal.get(m);
                String plusminusvall =(vall.replaceAll("\\+","")).replaceAll("\\-","");
                int indexplusminusvall=prpVal.indexOf((vall.replaceAll("\\+","")).replaceAll("\\-",""));
               if(plusMiAlwList.contains(lprp))
                indexplusminusvall=-1;
                if(indexplusminusvall==-1 || vall.equals(plusminusvall)){
                String chFldNme = "value("+lprp+"_"+vall+")" ;
                String onclick= "checkPrp(this, 'MTXT_"+lprp+"','"+lprp+"')";
                String checId = lprp+"_"+pSrtl;
               if(m==0){
                %>
                <%}
                if(listCnt==prpcountinline){%>
                </tr><tr><td></td><td class="lft-rgt-border"></td>
                <% listCnt=0;} 
                listCnt++;
//                String fld = util.nvl((String)favMap.get(lprp+"_"+pSrtl));
//                 if(fld.equals(pSrtl+"_to_"+pSrtl)){
//                   isSelected = "checked=\"checked\"";
//                   loadStrL = loadStrL+" , "+pPrtl;
//                   }
             %>
             <td align="left">
             <div id="ck-button" >
             <label style="">
             <html:checkbox  property="<%=chFldNme%>"  name="searchForm" styleId="<%=checId%>"  onclick="<%=onclick%>" value="<%=vall%>"/>
             <span><%=pPrtl%></span>
             </label>
             </div>
             </td>
             <%}}%>
             <input type="hidden" id="HID_<%=lprp%>" value="<%=loadStrL%>" />
               <%}%>
               <%}%>
               <%}%>
             </tr>
             <%}}%>
<%}%>
<%}%>
<% if(srchPrp!=null && srchPrp.size()>0){%>
<tr>
<td colspan="20">
<table>
<tr>
<%int listCnt=0;
            for(int i=topPrp ;i<srchPrp.size();i++){
             ArrayList srchLst = (ArrayList)srchPrp.get(i);
             lprp = (String)srchLst.get(0);
             flg=(String)srchLst.get(1);
             lprpTyp = util.nvl((String)mprp.get(lprp+"T"));   
             if(!lprpTyp.equals("C") && !lprp.equals("CRTWT") &&  !flg.equals("KT") || flg.equals("CTA")){
             prpDsc = (String)mprp.get(lprp+"D");
             if(prpDsc==null)
                prpDsc =lprp;
             prpPrt = (ArrayList)prp.get(lprp+"P");
             prpSrt = (ArrayList)prp.get(lprp+"S");
             prpVal = (ArrayList)prp.get(lprp+"V");
               fld1 = lprp+"_1";
              fld2 = lprp+"_2";

              prpFld1 = "value(" + fld1 + ")" ;
              prpFld2 = "value(" + fld2 + ")" ; 
             
              onChg1 = "setFrTo('"+ fld1 + "','" + fld2 + "')";
              onChg2 = "setFrTo('"+ fld1 + "','" + fld2 + "')";
              String blockprp="";
             if(prpDspBlocked.contains(lprp)){
             blockprp="style=\"display:none\"";
             }else{
             prpCount++;
             }
                if(listCnt==numcountinline){%>
                </tr><tr>
                <% listCnt=0;} 
                listCnt=listCnt+3;
            %>
            <td align="center" nowrap="nowrap"><span class="txtBold"> <%=prpDsc%> </span></td>
            <%if(lprpTyp.equals("D")){%>
        <td bgcolor="#FFFFFF" nowrap="nowrap" >
        <html:text property="<%=prpFld1%>"  styleClass="textNew" style="width:90px" name="searchForm" styleId="<%=fld1%>"    /> <a href="#!" id="cid" onClick="setYears(1900, '<%=info.getCurrentYear()%>' );showCalender(this, '<%=prpFld1%>');">
                <img src="<%=info.getReqUrl()%>/images/calender.png"></a></td>
        <td bgcolor="#FFFFFF" align="center" nowrap="nowrap"><html:text property="<%=prpFld2%>" style="width:90px" styleClass="textNew"  name="searchForm"  /> <a href="#!"  onClick="setYears(1900, '<%=info.getCurrentYear()%>' );showCalender(this, '<%=prpFld2%>');">
                <img src="<%=info.getReqUrl()%>/images/calender.png"></a></td>
        
        <%} else if(lprpTyp.equals("T") || flg.equals("CTA")){%>
        <td bgcolor="#FFFFFF" align="center" colspan="2" nowrap="nowrap">
        <html:text property="<%=prpFld1%>"  styleClass="textNew"  style="width:150px"  name="searchForm" styleId="<%=fld1%>"  size="40" /> 
        </td>
       
        <%} else{%>
           <td bgcolor="#FFFFFF" nowrap="nowrap" ><html:text property="<%=prpFld1%>" style="width:60px" styleClass="textNew"  name="searchForm" styleId="<%=fld1%>"    onchange="isNumericDecimal(this.id)" /></td>
        <td bgcolor="#FFFFFF" nowrap="nowrap" ><html:text property="<%=prpFld2%>"  style="width:60px" styleClass="textNew" name="searchForm"    onchange="isNumericDecimal(this.id)" />&nbsp;&nbsp;</td>
<%}}}%>
</tr>
</table>
</td>
</tr>
</table>
</td>
</tr>
<%}%>
<%}else{%>
             <% if(srchPrp!=null && srchPrp.size()>0){%>
            <tr>
            <td valign="top">
            <table>
            <tr><td valign="top">
            <table border="0" cellspacing="0" cellpadding="3" width="330" class="srch">
            <thead>
           <tr><th>PROPERTY</th><th>FROM</th><th>TO</th></tr>
           </thead>
            <%
           
            if(srchPrp!=null && srchPrp.size()>0){
                int divis= (((srchPrp.size())-topPrp)%3);
            int basicPrpSz=((srchPrp.size())-topPrp);
            int srchSize=0;
            if(basicPrpSz<10)
            srchSize=10;
            else
            srchSize = (((srchPrp.size())-topPrp)/3)+1;
            for(int i=topPrp ;i<srchPrp.size();i++){
            
            ArrayList srchLst = (ArrayList)srchPrp.get(i);
             lprp = (String)srchLst.get(0);
             if(srchCy.contains(lprp) && SrchTyp.equals("FCY")){
             }else{
             flg=(String)srchLst.get(1);
             lprpTyp = util.nvl((String)mprp.get(lprp+"T"));
             if(!fanCy.contains(lprp)){
             
             prpDsc = (String)mprp.get(lprp+"D");
             if(prpDsc==null)
                prpDsc =lprp;
             prpPrt = (ArrayList)prp.get(lprp+"P");
             prpSrt = (ArrayList)prp.get(lprp+"S");
             prpVal = (ArrayList)prp.get(lprp+"V");
               fld1 = lprp+"_1";
              fld2 = lprp+"_2";

              prpFld1 = "value(" + fld1 + ")" ;
              prpFld2 = "value(" + fld2 + ")" ; 
             
              onChg1 = "setFrTo('"+ fld1 + "','" + fld2 + "')";
              onChg2 = "setFrTo('"+ fld1 + "','" + fld2 + "')";
//               val1 = util.nvl((String)favMap.get(fld1));
//                val2 = util.nvl((String)favMap.get(fld2));
             if(prpCount==srchSize){
             prpCount=0;
             %></table></td><td valign="top">
              <table border="0" cellspacing="0" cellpadding="3" width="330" class="srch">
            <thead>
           <tr><th>PROPERTY</th><th>FROM</th><th>TO</th></tr>
           </thead>
           <%  }
             String blockprp="";
             if(prpDspBlocked.contains(lprp)){
             blockprp="style=\"display:none\"";
             }else{
             prpCount++;
             }
            %>
            <tr <%=blockprp%>><td >
            <span class="txtBold"> <%=prpDsc%> </span>
            <%
            if(includeexcludeList.contains(lprp) || flg.equals("KT")){
            String incexc= "value(INC_"+ lprp +")";
            String incexcName= "INC_"+ lprp;
            String checkedI="checked=\"checked\"";
            String checkedE="";
            if(flg.equals("KT"))
            checkedE="checked=\"checked\"";
            checkedI="";
            %>
            <br>
            <span title="Select Checkbox To Exclude">
            <input type="radio" name="<%=incexcName%>" id="red" value="I" <%=checkedI%>>&nbsp;Inc &nbsp;
            <input type="radio" name="<%=incexcName%>" id="red" value="E" <%=checkedE%>>&nbsp; Exc
            </span>
            <%}%>
            </td>
              <% if(flg.equals("M") || flg.equals("KT")){
              if(flg.equals("KT")){
              HashMap ktsViewPrpDtls = (info.getKtsViewPrpDtls() == null)?new HashMap():(HashMap)info.getKtsViewPrpDtls();
              prpPrt = (ArrayList)ktsViewPrpDtls.get("KTSCUSTOMP");
              prpSrt = (ArrayList)ktsViewPrpDtls.get("KTSCUSTOMS");
              prpVal = (ArrayList)ktsViewPrpDtls.get("KTSCUSTOMV");
              }
              onChg1 = "setFrTo('"+ fld1 + "','" + fld2 + "');setFrToCheck('"+ fld1 + "','" + fld2 + "','"+lprp+"')\"";
              onChg2 = "setFrTo('"+ fld1 + "','" + fld2 + "');setFrToCheck('"+ fld1 + "','" + fld2 + "','"+lprp+"')\"";
             String mutiTextId = "MTXT_"+lprp;
             String mutiText = "value("+ mutiTextId +")";
             if(lprp.equals("CRTWT")){
              String hidCwt="MTXT_"+lprp;
              String loadStr="ALL";
              String txtBoxOnChg1="writeText(this,'"+hidCwt+"','1');";
               String txtBoxOnChg2="writeText(this,'"+hidCwt+"','2');";
            %>
             <td colspan="2" align="center">
           
                        <div class="multiplePrpdiv" id="<%=lprp%>" align="center" style="display:none; padding-top:2px; margin-top:20px;">
             <table cellpadding="0"  class="multipleBg" cellspacing="0">
            <tr><td><table>
             <tr>
            <td>Carat</td> 
          <!--  <td><html:text property="value(wt_fr_c)"  size="6" styleId="wt_fr_c" onchange="<%=txtBoxOnChg1%>"  styleClass="txtStyle" name="searchForm"  /></td>
            <td><html:text property="value(wt_to_c)"  size="6" styleId="wt_to_c" onchange="<%=txtBoxOnChg2%>" styleClass="txtStyle" name="searchForm"/></td>
           -->   <td>
           <%if(isModify.equals("Y")){%>
    <html:text property="<%=mutiText%>"  name="searchForm"  style="width:100px" styleId="<%=mutiTextId%>" styleClass="txtStyle" />
   <%}else{%>
    <html:text property="<%=mutiText%>" value="<%=loadStr%>" style="width:100px" name="searchForm"   styleId="<%=mutiTextId%>" styleClass="txtStyle" />
   <%}%>
            </td>
            <td><img src="../images/cross.png" border="0" onclick="Hide('<%=lprp%>')" /> </td>
            </tr></table></td></tr>
            <tr><td >
            <table ><tr>
             <%
             int cnt=0;
             ArrayList crtwtPrp = (info.getCrtwtPrpLst() == null)?new ArrayList():(ArrayList)info.getCrtwtPrpLst();
            for(int crt=0;crt<crtwtPrp.size();crt++) {
            ArrayList crtPrp=(ArrayList)crtwtPrp.get(crt);
            String srtWt = util.nvl((String)crtPrp.get(0));
            String valWt = util.nvl((String)crtPrp.get(1));
            String wt_fr = util.nvl((String)crtPrp.get(2));
            String wt_to = util.nvl((String)crtPrp.get(3));
             String val_fr = "" ;
            String val_to = "" ;
            String isChecked = "" ;
            String szPrp = "CRTWT";
            String cwtFr ="value("+ szPrp + "_1_"+srtWt+")";
            String cwtTo ="value("+ szPrp + "_2_"+srtWt+")";
            String checkId = szPrp + "_"+srtWt;
            String cwtToTxtId = szPrp + "_2_"+srtWt;
            String cwtFrTxtId = szPrp + "_1_"+srtWt;
            String chkFldId = "value("+ checkId + ")";
            String onclick = "setCrtWtSrch("+srtWt+","+wt_fr+", "+wt_to+",this, MTXT_"+lprp+" )";
           
//             if(favMap.get("CRTWT_"+valWt) != null){
//              String vwVal = (String)favMap.get("CRTWT_"+valWt);
//              val_fr = vwVal.substring(0, vwVal.indexOf("_to_"));
//               val_to = vwVal.substring(vwVal.indexOf("_to_")+4);
//              isChecked = "checked";  
//              if(loadStr.equals("")){
//              loadStr = valWt;
//              }else{
//               loadStr = loadStr+" , "+valWt;
//              }
//             }
        if(cnt==3){%>
        </tr><tr>
        <% cnt=0;}
        cnt++;
        %>
        <td><html:checkbox property="<%=chkFldId%>"  name="searchForm" styleId="<%=checkId%>" value="<%=valWt%>"  onclick="<%=onclick%>" ></html:checkbox> &nbsp;<%=valWt%></td>
        <td><html:text property="<%=cwtFr%>" size="6" styleId="<%=cwtFrTxtId%>" styleClass="txtStyle"  /></td>
        <td ><html:text property="<%=cwtTo%>" size="6" styleId="<%=cwtToTxtId%>" styleClass="txtStyle"   /></td>
        <%}%>
        </tr></table>
        </td></tr>
             
             </table>
             </div>
     <table cellpadding="0" cellspacing="0"><tr><td>&nbsp;
            <html:text property="value(wt_fr_c)"  style="width:100px" styleId="wt_fr_c" onchange="<%=txtBoxOnChg1%>"  styleClass="txtStyle" name="searchForm"  />&nbsp;</td>
            <td>&nbsp;&nbsp;<html:text property="value(wt_to_c)"  style="width:97px" styleId="wt_to_c" onchange="<%=txtBoxOnChg2%>" styleClass="txtStyle" name="searchForm"/></td>
       
     <td>&nbsp;&nbsp;</td>
     <td><img src="../images/plus.jpg" id="IMGD_<%=lprp%>" class="img" onclick="DisplayDiv('<%=lprp%>')" style="display:block" border="0"/></td><td><img src="../images/minus.jpg" id="IMGU_<%=lprp%>" class="img" onclick="Hide('<%=lprp%>')" style="display:none" border="0"/></td>
       <% if(selectedPrp != null && selectedPrp.size() > 0) {
     if(selectedPrp.contains(lprp)){ %>
     <td><img src="../images/down_arrow.png"  style="padding-left:2px;" title="Click + Sign To View Selected Criteria" border="0" id="<%=lprp%>_SELECTED"/></td>
     <% } } %>
     </tr></table>

             </td>
             <%
     
             }else{
             %>
             <td colspan="2" align="center">
            
             <% if(prpSrt != null) {
               String loadStrL = "ALL";
             %>
             <div  class="multiplePrpdiv" id="<%=lprp%>" align="center" style="display:none; padding-top:2px; margin-top:20px;">
             <table cellpadding="0"  class="multipleBg" cellspacing="0">
             <tr><td>
             <table>
             <tr>
             <td> 
             <%if(!flg.equals("KT")){%>
             <input type="checkbox" name="selectALL" id="SALL_<%=lprp%>" onclick="CheckAlllprp('<%=lprp%>',this)" > Select All &nbsp; 
             <%}%>
             </td>
            
             <td>
    <%if(isModify.equals("Y")){%>
    <html:text property="<%=mutiText%>"  name="searchForm"  style="width:100px" styleId="<%=mutiTextId%>" styleClass="txtStyle" />
    <%}else{%>
    <html:text property="<%=mutiText%>" value="<%=loadStrL%>" name="searchForm"  style="width:100px" styleId="<%=mutiTextId%>" styleClass="txtStyle" />
    <%}%>
    </td>
            <td><img src="../images/cross.png" border="0" onclick="Hide('<%=lprp%>')" /> </td>
            </tr>
            </table>
            </td></tr>
            <tr><td><hr style="color:White;"></td></tr>
            <tr>
            <td>
             <%
             int listCnt=0;
             for(int m=0;m<prpSrt.size();m++){
                String isSelected = "";
                String pPrtl = (String)prpPrt.get(m);
                String pSrtl = (String)prpSrt.get(m);
                String vall = (String)prpVal.get(m);
                String chFldNme = "value("+lprp+"_"+vall+")" ;
                String onclick= "checkPrp(this, 'MTXT_"+lprp+"','"+lprp+"')";
                String checId = lprp+"_"+pSrtl;
               if(m==0){
                %>
                <table>
                <tr>
                <%}
                String plusminusvall =(vall.replaceAll("\\+","")).replaceAll("\\-","");
                int indexplusminusvall=prpVal.indexOf((vall.replaceAll("\\+","")).replaceAll("\\-",""));
                if(plusMiAlwList.contains(lprp))
                indexplusminusvall=-1;
                if(indexplusminusvall==-1 || vall.equals(plusminusvall)){
                if(listCnt==7){%>
                </tr><tr>
                <% listCnt=0;} 
                listCnt++;
             %>
             <td align="center"><html:checkbox  property="<%=chFldNme%>"  name="searchForm" styleId="<%=checId%>"  onclick="<%=onclick%>" value="<%=vall%>"/></td>
             <td align="left"><span style="margin-left:10px;"><%=pPrtl%></span></td>
             <%}}%>
             </tr></table>
            </td>
            </tr>
            </table>
             <input type="hidden" id="HID_<%=lprp%>" value="<%=loadStrL%>" />
              
             </div>
              <table cellpadding="0" cellspacing="0">
              <tr>
              <%if(!flg.equals("KT")){%>
           <td>  &nbsp; 
             
           <html:select  property="<%=prpFld1%>" name="searchForm"  style="width:100px" onchange="<%=onChg1%>" styleId="<%=fld1%>"  > 
     
            <html:option value="<%=dfltVal%>"><%=dfltDsp%></html:option>
            <%
            for(int j=0; j < prpSrt.size(); j++) {
                String pPrt = (String)prpPrt.get(j);
                String pSrt = (String)prpSrt.get(j);
                String vall = (String)prpVal.get(j);
                String plusminusvall =(vall.replaceAll("\\+","")).replaceAll("\\-","");
                int indexplusminusvall=prpVal.indexOf((vall.replaceAll("\\+","")).replaceAll("\\-",""));
                 if(plusMiAlwList.contains(lprp))
                indexplusminusvall=-1;
                if(indexplusminusvall==-1 || vall.equals(plusminusvall)){
                  String isSelected = "" ;
               if(pSrt.equals(val1))
                   isSelected = "selected=\"selected\"";
            %>
              <html:option value="<%=pSrt%>" ><%=pPrt%></html:option> 
            <%}}%>
              </html:select>
              </td><td >
            &nbsp;  &nbsp;
             
           <html:select property="<%=prpFld2%>" name="searchForm"  style="width:100px" onchange="<%=onChg2%>" styleId="<%=fld2%>"  > 
     
            <html:option value="<%=dfltVal%>"><%=dfltDsp%></html:option>
            <%
            for(int j=0; j < prpSrt.size(); j++) {
                String pPrt = (String)prpPrt.get(j);
                String pSrt = (String)prpSrt.get(j);
                String vall = (String)prpVal.get(j);
                String plusminusvall =(vall.replaceAll("\\+","")).replaceAll("\\-","");
                int indexplusminusvall=prpVal.indexOf((vall.replaceAll("\\+","")).replaceAll("\\-",""));
          if(plusMiAlwList.contains(lprp))
                indexplusminusvall=-1;
                if(indexplusminusvall==-1 || vall.equals(plusminusvall)){
                  String isSelected = "" ;
               if(pSrt.equals(val1))
                   isSelected = "selected=\"selected\"";
            %>
              <html:option value="<%=pSrt%>" ><%=pPrt%></html:option> 
            <%}}%>
              </html:select>   
             </td>
             <td>&nbsp;&nbsp;</td>
              <td><img src="../images/plus.jpg" id="IMGD_<%=lprp%>" class="img" onclick="DisplayDiv('<%=lprp%>')" style="display:block" border="0"/></td><td><img src="../images/minus.jpg" id="IMGU_<%=lprp%>" class="img" onclick="Hide('<%=lprp%>')" style="display:none" border="0"/></td>
               <% if(selectedPrp != null && selectedPrp.size() > 0) {
     if(selectedPrp.contains(lprp)){ %>
     <td><img src="../images/down_arrow.png"  style="padding-left:2px;" title="Click + Sign To View Selected Criteria" border="0" id="<%=lprp%>_SELECTED"/></td>
     <% } } %>
              <%}else{%>
              <td colspan="5">
              <select onclick="DisplayDiv('<%=lprp%>')"   style="width:200px">
              <option style="font-size:0pt;">Select an option</option>
              </select>
              </td>
              <%}%>
              </tr></table>
             <%}%>
             </td>
             <%}%>
               

           <% }else if(flg.equals("SM")){
     suggScript=suggScript+"$('#"+lprp+"').on('keyup',function() {  "+
       " var timer = setTimeout(autoSearchData('"+lprp+"','"+lprp+"','true'), 300);  });"+
       " $('#"+lprp+"').keyup();";
       String proprtyVal = "value("+lprp+")";
      String suggValue ="";
      String suggKey="";     
      ArrayList  suggeArrList =(ArrayList)request.getAttribute("suggBoxArrList");
      if(suggeArrList !=null && suggeArrList.size()>0){
      for(int l=0;l<suggeArrList.size();l++){
      String comma="";
      if(l<(suggeArrList.size()-1)){
      comma=",";
      }
      HashMap suggeMap = (HashMap)suggeArrList.get(l);
      suggValue= util.nvl((String)suggeMap.get(lprp+"_V"));
      
       suggKey += util.nvl((String)suggeMap.get(lprp+"_K"))+comma;
       
      
      }
      }
   
           %>
           <td colspan="2">
           <div>
           <html:hidden property="<%=proprtyVal%>" styleId="hidden_select" value="<%=suggKey%>"/>
            <input type="text"  name="" id="<%=lprp%>" class="magicsearch txtStyle"   autocomplete="off" style="width:200px;height:20px;"  value="" placeholder="" data-id="<%=suggKey%>" ></input>
            
             </div>
             </td>

          <% } else{
             
              if(prpSrt != null && (!flg.equals("CTA"))) {
            %>
            <td>&nbsp;
            <html:select property="<%=prpFld1%>" name="searchForm"  style="width:100px" onchange="<%=onChg1%>" styleId="<%=fld1%>"  > 
     
            <html:option value="<%=dfltVal%>"><%=dfltDsp%></html:option>
            <%
            for(int j=0; j < prpSrt.size(); j++) {
                String pPrt = (String)prpPrt.get(j);
                String pSrt = (String)prpSrt.get(j);
               
                  String isSelected = "" ;
               if(pSrt.equals(val1))
                   isSelected = "selected=\"selected\"";
            %>
              <html:option value="<%=pSrt%>" ><%=pPrt%></html:option> 
            <%}%>
              </html:select>
            
            </td>
            <td >
           
               <html:select property="<%=prpFld2%>" name="searchForm"  style="width:100px" onchange="<%=onChg2%>" styleId="<%=fld2%>"  > 
     
            <html:option value="<%=dfltVal%>"><%=dfltDsp%></html:option>
            <%
            for(int j=0; j < prpSrt.size(); j++) {
                String pPrt = (String)prpPrt.get(j);
                String pSrt = (String)prpSrt.get(j);
               
                  String isSelected = "" ;
               if(pSrt.equals(val1))
                   isSelected = "selected=\"selected\"";
            %>
              <html:option value="<%=pSrt%>" ><%=pPrt%></html:option> 
            <%}%>
              </html:select>
            &nbsp;  &nbsp;   </td>
            
        <%}else{%>
        <%if(lprpTyp.equals("D")){%>
        <td bgcolor="#FFFFFF" >&nbsp;
        <html:text property="<%=prpFld1%>"  styleClass="txtStyle" style="width:90px" name="searchForm" styleId="<%=fld1%>"    /> <a href="#" id="cid" onClick="setYears(1900, '<%=info.getCurrentYear()%>' );showCalender(this, '<%=prpFld1%>');">
                <img src="<%=info.getReqUrl()%>/images/calender.png"></a></td>
        <td bgcolor="#FFFFFF" align="center"><html:text property="<%=prpFld2%>" style="width:90px" styleClass="txtStyle"  name="searchForm"  /> <a href="#"  onClick="setYears(1900, '<%=info.getCurrentYear()%>' );showCalender(this, '<%=prpFld2%>');">
                <img src="<%=info.getReqUrl()%>/images/calender.png"></a></td>
        
        <%} else if(lprpTyp.equals("T") || flg.equals("CTA")   ){%>
        <td bgcolor="#FFFFFF" align="center" colspan="2">
        <%if(flg.equals("IE")){
        String lprpRdFld = lprp+"IE";
        String lprpRdFldVlu = "value("+lprp+"_IE)";
        
        %>
    <html:radio property="<%=lprpRdFldVlu%>"  name="searchForm" value="I"  /> &nbsp;Include &nbsp;&nbsp;
      <html:radio property="<%=lprpRdFldVlu%>"  name="searchForm" value="E"  />&nbsp; Exclude
        <%}%>
        <html:text property="<%=prpFld1%>"  styleClass="txtStyle"  style="width:100px"  name="searchForm" styleId="<%=fld1%>"  size="30" /> 
        </td>
       
        <%}else if(flg.equals("IE")){%>
        <td bgcolor="#FFFFFF" align="center" colspan="2">
       <html:select  property="<%=prpFld1%>" name="searchForm" styleId="<%=fld2%>" styleClass="taggingSelect2" multiple="true"> 
       <html:option value="<%=dfltVal%>"><%=dfltDsp%></html:option>
            <%
            for(int j=0; j < prpSrt.size(); j++) {
                String pPrt = (String)prpPrt.get(j);
                String pSrt = (String)prpSrt.get(j);
               
                  String isSelected = "" ;
               if(pSrt.equals(val1))
                   isSelected = "selected=\"selected\"";
            %>
              <html:option value="<%=pSrt%>" ><%=pPrt%></html:option> 
            <%}%>
       </html:select> 
        </td>
         <%}else{%>
           <td bgcolor="#FFFFFF"><html:text property="<%=prpFld1%>" style="width:100px" styleClass="txtStyle"  name="searchForm" styleId="<%=fld1%>"    onchange="isNumericDecimal(this.id)" /></td>
        <td bgcolor="#FFFFFF"><html:text property="<%=prpFld2%>"  style="width:100px" styleClass="txtStyle" name="searchForm"    onchange="isNumericDecimal(this.id)" />&nbsp;&nbsp;</td>
        <%}}
            
             }%>
            
            
            
           </tr> 
            <%}}}}%>
             
            
            </table>
                                </td></tr>
        
              
                </table>              
                                                </td></tr>
                <%}%>
<%}%>
<!--end-->
</table>

<table class="genericTB" id="ADVSRCH"  style="display:none">
<tr>
<td><html:submit property="value(srchpktfancy)" value="Search"  styleClass="submit" />&nbsp;
     <%
  rolenmLst=(ArrayList)info.getRoleLst();
  usrFlg=util.nvl((String)info.getUsrFlg());
  if(usrFlg.equals("SYS") || rolenmLst.contains("ADMIN")){
  if(isMix.equals("") || isMix.equals("NR")){
  %>
  <img src="../images/edit.jpg" title="Edit Properties" onclick="newWindow('<%=info.getReqUrl()%>/dynamicProperty.do?method=load&mdl=MEMO_ADV&sname=&par=ADVSRCH')" border="0" width="15px" height="15px"/> 
  <%}}%>
  </td>
</tr>
     <tr><td>
      <table>
<% if(advPrpList!=null && advPrpList.size()>0){%>
<tr>
<td>
<table cellspacing="0px" bgcolor="#ededed">
<%for(int i=topPrp ;i<advPrpList.size();i++){
             ArrayList srchLst = (ArrayList)advPrpList.get(i);
             lprp = (String)srchLst.get(0);
             flg=(String)srchLst.get(1);
             lprpTyp = util.nvl((String)mprp.get(lprp+"T"));   
             if(lprpTyp.equals("C") || lprp.equals("CRTWT")){
             flg="M";
             prpDsc = (String)mprp.get(lprp+"D");
             if(prpDsc==null)
                prpDsc =lprp;
             prpPrt = (ArrayList)prp.get(lprp+"P");
             prpSrt = (ArrayList)prp.get(lprp+"S");
             prpVal = (ArrayList)prp.get(lprp+"V");
               fld1 = lprp+"_1";
              fld2 = lprp+"_2";

              prpFld1 = "value(" + fld1 + ")" ;
              prpFld2 = "value(" + fld2 + ")" ; 
             
              onChg1 = "setFrTo('"+ fld1 + "','" + fld2 + "')";
              onChg2 = "setFrTo('"+ fld1 + "','" + fld2 + "')";
              String blockprp="";
             if(prpDspBlocked.contains(lprp)){
             blockprp="style=\"display:none\"";
             }else{
             prpCount++;
             }
            %>
            <tr <%=blockprp%>>
            <td align="left" nowrap="nowrap"><span class="txtBoldNew"> <%=prpDsc%> </span>
            </td>
              <% if(flg.equals("M")){
              onChg1 = "setFrTo('"+ fld1 + "','" + fld2 + "');setFrToCheck('"+ fld1 + "','" + fld2 + "','"+lprp+"')\"";
              onChg2 = "setFrTo('"+ fld1 + "','" + fld2 + "');setFrToCheck('"+ fld1 + "','" + fld2 + "','"+lprp+"')\"";
             String mutiTextId = "MTXT_"+lprp;
             String mutiText = "value("+ mutiTextId +")";
             if(lprp.equals("CRTWT")){
              String hidCwt="MTXT_"+lprp;
              String loadStr="ALL";
              String txtBoxOnChg1="writeText(this,'"+hidCwt+"','1');";
               String txtBoxOnChg2="writeText(this,'"+hidCwt+"','2');";%>
               <td class="lft-rgt-border">
                 <%if(isModify.equals("Y")){%>
                <html:hidden property="<%=mutiText%>"  name="searchForm"  style="width:100px" styleId="<%=mutiTextId%>" styleClass="txtStyle" />
               <%}else{%>
                <html:hidden property="<%=mutiText%>" value="<%=loadStr%>" style="width:100px" name="searchForm"   styleId="<%=mutiTextId%>" styleClass="txtStyle" />
               <%}%>
               <table>
               <tr>
               <td><html:text property="value(wt_fr_c)"  style="width:100px" styleId="wt_fr_c" onchange="<%=txtBoxOnChg1%>"  styleClass="textNew" name="searchForm"/></td>
               <td><html:text property="value(wt_to_c)"  style="width:97px" styleId="wt_to_c" onchange="<%=txtBoxOnChg2%>" styleClass="textNew" name="searchForm"/></td>
               </tr>
               </table>
               </td>
             <%int cnt=0;
             ArrayList crtwtPrp = (info.getCrtwtPrpLst() == null)?new ArrayList():(ArrayList)info.getCrtwtPrpLst();
            for(int crt=0;crt<crtwtPrp.size();crt++) {
            ArrayList crtPrp=(ArrayList)crtwtPrp.get(crt);
            String srtWt = util.nvl((String)crtPrp.get(0));
            String valWt = util.nvl((String)crtPrp.get(1));
            String wt_fr = util.nvl((String)crtPrp.get(2));
            String wt_to = util.nvl((String)crtPrp.get(3));
             String val_fr = "" ;
            String val_to = "" ;
            String isChecked = "" ;
            String szPrp = "CRTWT";
            String cwtFr ="value("+ szPrp + "_1_"+srtWt+")";
            String cwtTo ="value("+ szPrp + "_2_"+srtWt+")";
            String checkId = szPrp + "_"+srtWt;
            String cwtToTxtId = szPrp + "_2_"+srtWt;
            String cwtFrTxtId = szPrp + "_1_"+srtWt;
            String chkFldId = "value("+ checkId + ")";
            String onclick = "setCrtWtSrch("+srtWt+","+wt_fr+", "+wt_to+",this, MTXT_"+lprp+" )";
            if(cnt==advctscountinline){%>
            </tr><tr><td></td><td class="lft-rgt-border"></td>
            <% cnt=0;}
            cnt++;
            %>
            <td nowrap="nowrap">
            <table>
            <tr>
            <td>
             <div id="ck-button" >
             <label style="">
            <html:checkbox property="<%=chkFldId%>"  name="searchForm" styleId="<%=checkId%>" value="<%=valWt%>"  onclick="<%=onclick%>" ></html:checkbox>
            <span><%=valWt%></span>
            </label>
            </div>
            </td>
            </tr>
            <tr>
            <td align="center">
            <html:text property="<%=cwtFr%>" size="6" styleId="<%=cwtFrTxtId%>" styleClass="textNew"  />
            </td>
            </tr>
            <tr>
            <td align="center">
            <html:text property="<%=cwtTo%>" size="6" styleId="<%=cwtToTxtId%>" styleClass="textNew"   />
            </td>
            </tr>
            </table>
            </td>
            <%}%>
               <%}else{%>
               <% if(prpSrt != null) {
               String loadStrL = "ALL";
               %>
               <td class="lft-rgt-border">
               <table>
               <tr>
               <td>
                   <%if(isModify.equals("Y")){%>
                <html:hidden property="<%=mutiText%>"  name="searchForm"  style="width:100px" styleId="<%=mutiTextId%>" styleClass="txtStyle" />
                <%}else{%>
                <html:hidden property="<%=mutiText%>" value="<%=loadStrL%>" name="searchForm"  style="width:100px" styleId="<%=mutiTextId%>" styleClass="txtStyle" />
                <%}%>
             <html:select  property="<%=prpFld1%>" name="searchForm"  style="width:100px" onchange="<%=onChg1%>" styleId="<%=fld1%>" styleClass="selectnew"> 
     
            <html:option value="<%=dfltVal%>"><%=dfltDsp%></html:option>
            <%
            for(int j=0; j < prpSrt.size(); j++) {
                String pPrt = (String)prpPrt.get(j);
                String pSrt = (String)prpSrt.get(j);
                String vall = (String)prpVal.get(j);
                String plusminusvall =(vall.replaceAll("\\+","")).replaceAll("\\-","");
                int indexplusminusvall=prpVal.indexOf((vall.replaceAll("\\+","")).replaceAll("\\-",""));
                if(plusMiAlwList.contains(lprp))
                indexplusminusvall=-1;
                if(indexplusminusvall==-1 || vall.equals(plusminusvall)){
                  String isSelected = "" ;
               if(pSrt.equals(val1))
                   isSelected = "selected=\"selected\"";
            %>
              <html:option value="<%=pSrt%>" ><%=pPrt%></html:option> 
            <%}}%>
              </html:select>
              </td>
             <td>
            <html:select property="<%=prpFld2%>" name="searchForm"  style="width:100px" onchange="<%=onChg2%>" styleId="<%=fld2%>"  styleClass="selectnew" > 
     
            <html:option value="<%=dfltVal%>"><%=dfltDsp%></html:option>
            <%
            for(int j=0; j < prpSrt.size(); j++) {
                String pPrt = (String)prpPrt.get(j);
                String pSrt = (String)prpSrt.get(j);
                String vall = (String)prpVal.get(j);
                String plusminusvall =(vall.replaceAll("\\+","")).replaceAll("\\-","");
                int indexplusminusvall=prpVal.indexOf((vall.replaceAll("\\+","")).replaceAll("\\-",""));
                if(plusMiAlwList.contains(lprp))
                indexplusminusvall=-1;
                if(indexplusminusvall==-1 || vall.equals(plusminusvall)){
                  String isSelected = "" ;
               if(pSrt.equals(val1))
                   isSelected = "selected=\"selected\"";
            %>
              <html:option value="<%=pSrt%>" ><%=pPrt%></html:option> 
            <%}}%>
              </html:select>   
              </td>
              </tr>
              </table>
               </td>
             <%
             int listCnt=0;
             for(int m=0;m<prpSrt.size();m++){
                String isSelected = "";
                String pPrtl = (String)prpPrt.get(m);
                String pSrtl = (String)prpSrt.get(m);
                String vall = (String)prpVal.get(m);
                String plusminusvall =(vall.replaceAll("\\+","")).replaceAll("\\-","");
                int indexplusminusvall=prpVal.indexOf((vall.replaceAll("\\+","")).replaceAll("\\-",""));
                if(plusMiAlwList.contains(lprp))
                indexplusminusvall=-1;
                if(indexplusminusvall==-1 || vall.equals(plusminusvall)){
                String chFldNme = "value("+lprp+"_"+vall+")" ;
                String onclick= "checkPrp(this, 'MTXT_"+lprp+"','"+lprp+"')";
                String checId = lprp+"_"+pSrtl;
               if(m==0){
                %>
                <%}
                if(listCnt==advprpcountinline){%>
                </tr><tr><td></td><td class="lft-rgt-border"></td>
                <% listCnt=0;} 
                listCnt++;
//                String fld = util.nvl((String)favMap.get(lprp+"_"+pSrtl));
//                 if(fld.equals(pSrtl+"_to_"+pSrtl)){
//                   isSelected = "checked=\"checked\"";
//                   loadStrL = loadStrL+" , "+pPrtl;
//                   }
             %>
             <td align="left">
             <div id="ck-button" >
             <label style="">
             <html:checkbox  property="<%=chFldNme%>"  name="searchForm" styleId="<%=checId%>"  onclick="<%=onclick%>" value="<%=vall%>"/>
             <span><%=pPrtl%></span>
             </label>
             </div>
             </td>
             <%}}%>
             <input type="hidden" id="HID_<%=lprp%>" value="<%=loadStrL%>" />
               <%}%>
               <%}%>
               <%}%>
             </tr>
             <%}%>
<%}%>
<%}%>
<% if(advPrpList!=null && advPrpList.size()>0){%>
<tr>
<td colspan="20">
<table>
<tr>
<%int listCnt=0;
            for(int i=topPrp ;i<advPrpList.size();i++){
             ArrayList srchLst = (ArrayList)advPrpList.get(i);
             lprp = (String)srchLst.get(0);
             flg=(String)srchLst.get(1);
             lprpTyp = util.nvl((String)mprp.get(lprp+"T"));   
             if(!lprpTyp.equals("C") && !lprp.equals("CRTWT")){
             prpDsc = (String)mprp.get(lprp+"D");
             if(prpDsc==null)
                prpDsc =lprp;
             prpPrt = (ArrayList)prp.get(lprp+"P");
             prpSrt = (ArrayList)prp.get(lprp+"S");
             prpVal = (ArrayList)prp.get(lprp+"V");
               fld1 = lprp+"_1";
              fld2 = lprp+"_2";

              prpFld1 = "value(" + fld1 + ")" ;
              prpFld2 = "value(" + fld2 + ")" ; 
             
              onChg1 = "setFrTo('"+ fld1 + "','" + fld2 + "')";
              onChg2 = "setFrTo('"+ fld1 + "','" + fld2 + "')";
              String blockprp="";
             if(prpDspBlocked.contains(lprp)){
             blockprp="style=\"display:none\"";
             }else{
             prpCount++;
             }
                if(listCnt==advnumcountinline){%>
                </tr><tr>
                <% listCnt=0;} 
                listCnt=listCnt+3;
            %>
            <td align="center" nowrap="nowrap"><span class="txtBold"> <%=prpDsc%> </span></td>
            <%if(lprpTyp.equals("D")){%>
        <td bgcolor="#FFFFFF" nowrap="nowrap" >
        <html:text property="<%=prpFld1%>"  styleClass="textNew" style="width:90px" name="searchForm" styleId="<%=fld1%>"    /> <a href="#" id="cid" onClick="setYears(1900, '<%=info.getCurrentYear()%>' );showCalender(this, '<%=prpFld1%>');">
                <img src="<%=info.getReqUrl()%>/images/calender.png"></a></td>
        <td bgcolor="#FFFFFF" align="center" nowrap="nowrap"><html:text property="<%=prpFld2%>" style="width:90px" styleClass="textNew"  name="searchForm"  /> <a href="#"  onClick="setYears(1900, '<%=info.getCurrentYear()%>' );showCalender(this, '<%=prpFld2%>');">
                <img src="<%=info.getReqUrl()%>/images/calender.png"></a></td>
        
        <%} else if(lprpTyp.equals("T")){%>
        <td bgcolor="#FFFFFF" align="center" colspan="2" nowrap="nowrap">
        <html:text property="<%=prpFld1%>"  styleClass="txtStyle"  style="width:100px"  name="searchForm" styleId="<%=fld1%>"  size="30" /> 
        </td>
       
        <%} else{%>
           <td bgcolor="#FFFFFF" nowrap="nowrap" ><html:text property="<%=prpFld1%>" style="width:100px" styleClass="textNew"  name="searchForm" styleId="<%=fld1%>"    onchange="isNumericDecimal(this.id)" /></td>
        <td bgcolor="#FFFFFF" nowrap="nowrap" ><html:text property="<%=prpFld2%>"  style="width:100px" styleClass="textNew" name="searchForm"    onchange="isNumericDecimal(this.id)" />&nbsp;&nbsp;</td>
<%}}}%>
</tr>
</table>
</td>
</tr>
</table>
</td>
</tr>
<%}%>
<!--end-->
      </table>
      </td>
      </tr>
</table>
</td>
</tr>
</table>
 </html:form>
 </td></tr>
 <tr><td>
<jsp:include page="/footer.jsp" />
</td></tr>
 </table>
 <%@include file="../calendar.jsp"%>
 <%if(!util.nvl(info.getIsEx()).equals("")){%>
 <input type="hidden" name="exTyp" id="exTyp" value="<%=info.getIsEx()%>" />
 <script type="text/javascript">
 <!--
 setExOnLoad();
 -->
 </script>
 <%}%>
 <%if(request.getAttribute("mail")!=null){%>
 <script type="text/javascript">
 <!--
 windowOpen('<%=info.getReqUrl()%>/contact/massmail.do?method=mailList&typ=srch','_blank')
 -->
 </script>
 <%}
 String multiSugg="<script language=\"javascript\">"+"   "+suggScript+ "</script>";%>
 <%=multiSugg%>
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
        
        $(document).on('click','.magicsearch-box ul li',function() {
        var hidVal=$('.magicsearch').attr('data-id');
        $('#hidden_select').val(hidVal);
        });
        
        $(document).on('click','.multi-item-close',function() {
         setTimeout(function(){
         var hidVal=$('.magicsearch').attr('data-id');
        $('#hidden_select').val(hidVal);
         },500);
        });
        
           		//device.connect();
</script>
       <%@include file="../calendar.jsp"%>
</body>
</html>