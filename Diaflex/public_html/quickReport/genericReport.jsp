<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page errorPage="../error_page.jsp" %>
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import="java.util.ArrayList,java.util.ArrayList,java.util.HashMap, java.sql.*, java.util.Enumeration, java.util.logging.Level"%>
<jsp:useBean id="info" class="ft.com.InfoMgr" scope="session" />
<jsp:useBean id="util" class="ft.com.JspUtil" scope="page" />
<%@taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>Generic Report</title>
   <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/bse.js?v=<%=info.getJsVersion()%> " > </script>
   <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/memoScript.js?v=<%=info.getJsVersion()%> " > </script>
   <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/bse.css?v=<%=info.getJsVersion()%>"/>
   <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/general.css?v=<%=info.getJsVersion()%>"/>
   <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/ajax.js?v=<%=info.getJsVersion()%> " > </script>
   <script type="text/javascript" src="<%=info.getReqUrl()%>/PopScripts/popup.js?v=<%=info.getJsVersion()%> " > </script>
   <script src="<%=info.getReqUrl()%>/PopScripts/jquery-1.2.6.min.js" type="text/javascript"></script>
   <script language="javaScript" type="text/javascript" src="<%=info.getReqUrl()%>/calendar/calendar.js"></script>
   <link href="<%=info.getReqUrl()%>/calendar/calendar.css" rel="stylesheet" type="text/css" />
  <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/memoScript.js?v=<%=info.getJsVersion()%> " > </script>
  <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/style.css?v=<%=info.getJsVersion()%>"/>
              <script src="<%=info.getReqUrl()%>/PopScripts/jquery-1.2.6.min.js" type="text/javascript"></script>
             <script src="<%=info.getReqUrl()%>/jqueryScript/jqueryScript.js?v=<%=info.getJsVersion()%>" type="text/javascript"></script>
              <script src="<%=info.getReqUrl()%>/jqueryScript/jquery.min.js" type="text/javascript"></script>
  </head>
  <%
  String logId=String.valueOf(info.getLogId());
        String onfocus="cook('"+logId+"')";
        %>
 <body onfocus="<%=onfocus%>" onload="MM_preloadImages('images/faunalogo.png')" topmargin="0" leftmargin="0" rightmargin="0" bottommargin="0">
<div id="backgroundPopup"></div>
<div id="popupContactSave" align="center" >
<img src="../images/loading_flame.gif" height="150px" width="150px" border="0" />
</div>
<%  HashMap dbinfo = info.getDmbsInfoLst();%>
<input type="hidden" id="cut" value="<%=dbinfo.get("CUT")%>" />
<input type="hidden" id="sym" value="<%=dbinfo.get("SYM")%>" />
<input type="hidden" id="pol" value="<%=dbinfo.get("POL")%>" />
<input type="hidden" id="shape" value="<%=dbinfo.get("SHAPE")%>" />
<input type="hidden" id="size" value="<%=dbinfo.get("SIZE")%>" />
<input type="hidden" id="col" value="<%=dbinfo.get("COL")%>" />
<input type="hidden" id="clr" value="<%=dbinfo.get("CLR")%>" />
 <html:form action="/quickReport/genericReport.do?method=save" method="post" target="" styleId="genFrm" onsubmit="return validateAnalysisRpt();"> 
 <%
  String btnKey = request.getParameter("btn");
  String rpt = util.nvl((String)request.getParameter("rpt"));
  HashMap allPageDtl= (info.getPageDetails() == null)?new HashMap():(HashMap)info.getPageDetails();
  HashMap pageDtl=(HashMap)allPageDtl.get("GENERIC_REPORT");
    ArrayList pageList=new ArrayList();
    HashMap pageDtlMap=new HashMap();
    String fld_nme="",fld_ttl="",val_cond="",lov_qry="",dflt_val="",fld_typ="",form_nme="",flg1="";
  String cnt = (String)dbinfo.get("CNT");
    ArrayList gridfmtLst = (ArrayList)session.getAttribute("gridfmtLst");
    ArrayList gridfmtPrp = (ArrayList)session.getAttribute("gridfmtPrp");
    ArrayList gridfmttyp=new ArrayList();
    gridfmttyp.add("COMM");
    gridfmttyp.add("ROW");
    gridfmttyp.add("COL");
    int countgrid=0;
 %>
  <table width="100%"  border="0" align="center" cellpadding="0" cellspacing="0" class="mainbg">
  <tr>
    <td height="103" valign="top">
   <jsp:include page="/header.jsp" />
    
    </td>
  </tr>
 
  <tr>
    <td height="5" valign="top" class="greyline"><img src="../images/grey_linebig.png"  width="685" height="5" align="right" /></td>
  </tr>
  <tr>
  <td valign="top" class="hedPg">
  <table cellpadding="1" cellspacing="5"><tr><td valign="middle">
  <img src="../images/line.jpg" border="0" /></td><td valign="middle"> 
  <span class="pgHed">Analysis Report
  </span></td></tr></table>
  </td></tr>
  
  <tr>
  <td valign="top" class="hedPg">
  <%
  ArrayList sttList = (ArrayList)session.getAttribute("sttLst");
  if(sttList!=null && sttList.size()>0){%>
  <table id="statusTbl"><tr><td>Status:</td>
 <% for(int n=0;n<sttList.size();n++){
  ArrayList sttDtl = (ArrayList)sttList.get(n);
  String grp1 = (String)sttDtl.get(0);
  String dsc = (String)sttDtl.get(1);
  String dscgrp1="value("+grp1+")";
  String chkid="STT_"+n;
  %>
  <!--<td><html:radio property="value(stt)" name="genericReportFormMG"   value="<%=grp1%>" /> </td><td><%=dsc%></td>-->
  <td><html:checkbox property="<%=dscgrp1%>" styleId="<%=chkid%>" value="<%=grp1%>" name="genericReportFormMG"/></td><td><%=dsc%></td>
  <%}%> <!--<%
      pageList=(ArrayList)pageDtl.get("DAYS");
      if(pageList!=null && pageList.size() >0){
      for(int j=0;j<pageList.size();j++){
      pageDtlMap=(HashMap)pageList.get(j);
      fld_nme="value("+(String)pageDtlMap.get("fld_nme")+")";
      dflt_val=(String)pageDtlMap.get("dflt_val");
      %>
      <td><html:text property="<%=fld_nme%>" size="2" name="genericReportFormMG" value="<%=dflt_val%>"/></td>
    <%}}
    %>
   <td>DAYS : <html:text property="value(DAYS)" size="2" name="genericReportFormMG"/></td>-->
  <td><table>
  <tr><td>Date :</td><td><table><tr><td>
                <html:text property="value(frmDte)" styleId="frmDte" size="10" />
                 <a href="#"  onClick="setYears(1900, <%=info.getCurrentYear()%>);showCalender(this, 'frmDte');">
                <img src="<%=info.getReqUrl()%>/images/calender.png"></a>
              </td><td>
               <html:text property="value(toDte)" styleId="toDte" size="10"/>
                 <a href="#"  onClick="setYears(1900, <%=info.getCurrentYear()%>);showCalender(this, 'toDte');">
                <img src="<%=info.getReqUrl()%>/images/calender.png"></a>
              </td> </tr>
              </table>
              </td></tr></table>
  </td>
  <td nowrap="nowrap">
  Search By: <!--<html:radio property="value(srchBy)"  styleId="bycriteria" value="C" /> Criteria.
 <html:radio property="value(srchBy)"  styleId="bypacket" value="P" /> Packet.-->
  <%pageList=(ArrayList)pageDtl.get("SRCHBY");
            if(pageList!=null && pageList.size() >0){
            for(int j=0;j<pageList.size();j++){
            pageDtlMap=(HashMap)pageList.get(j);
            fld_nme="value("+(String)pageDtlMap.get("fld_nme")+")";
            fld_ttl=(String)pageDtlMap.get("fld_ttl");
            fld_typ=(String)pageDtlMap.get("fld_typ");
            dflt_val=(String)pageDtlMap.get("dflt_val");
            val_cond=(String)pageDtlMap.get("val_cond"); %>
           <html:radio property="<%=fld_nme%>"  styleId="<%=fld_typ%>" value="<%=dflt_val%>" /> <%=fld_ttl%>.
            <%}}%>
  </td>
  </tr></table>
  
   <table cellpadding="0" cellspacing="0">
       <tr>
         <td> <html:checkbox property="value(is3Ex)" value="is3Ex"   styleId="is3Ex" name="genericReportFormMG" onclick="setAnalysEX('EX',this)" />3 EX</td>
         <td> <html:checkbox property="value(is3VG)" value="is3VG"   styleId="is3VG" name="genericReportFormMG" onclick="setAnalysEX('VG',this)" />3 VG</td>
       </tr>
   </table>
  <%}%>
  </td></tr>
  
   <tr>
  <td valign="top" class="hedPg">
  <%
 
  if(btnKey!=null){%>
     Upgrade Type : <html:radio property="value(upgrade)" name="genericReportFormMG"   value="COL" />&nbsp;Color&nbsp;
   &nbsp;&nbsp;<html:radio property="value(upgrade)" name="genericReportFormMG"   value="CLR" />&nbsp;Clarity&nbsp;
   &nbsp;&nbsp;<html:radio property="value(upgrade)" name="genericReportFormMG"   value="BOTH" />&nbsp;Both&nbsp;
   <html:submit property="value(colClrUpd)" value="Assort Upgrade" styleClass="submit" />
  <%}else{
  %>
  <!--<html:submit property="value(submit)" value="Submit" styleClass="submit" />
  <html:submit property="value(saledelation)" value="Sale Price Difference" styleClass="submit" />-->
<%}    
            if(rpt.equals("")){
            pageList=(ArrayList)pageDtl.get("SUBMIT");
            if(pageList!=null && pageList.size() >0){
            for(int j=0;j<pageList.size();j++){
            pageDtlMap=(HashMap)pageList.get(j);
            fld_nme=(String)pageDtlMap.get("fld_nme");
            if(fld_nme.equals("submit") || fld_nme.equals("pktdtl") ||  fld_nme.equals("companalysis")) {
            fld_nme="value("+fld_nme+")";
            fld_ttl=(String)pageDtlMap.get("fld_ttl");
            fld_typ=(String)pageDtlMap.get("fld_typ");
            lov_qry=(String)pageDtlMap.get("lov_qry");
            dflt_val=(String)pageDtlMap.get("dflt_val");
            val_cond="settarget('genFrm','fixed1'); ";
            if(fld_nme.indexOf("hitmap") > -1)
            val_cond="settarget('genFrm','hitmap11'); ";
            val_cond=val_cond+util.nvl((String)(String)pageDtlMap.get("val_cond")); 
            if(fld_typ.equals("S")){
            %>
    <html:submit property="<%=fld_nme%>" value="<%=fld_ttl%>" onclick="<%=val_cond%>" styleClass="submit"/>
    <%}else if(fld_typ.equals("B")){%>
    <html:button property="<%=fld_nme%>" value="<%=fld_ttl%>" onclick="<%=val_cond%>" styleClass="submit"/>
    <%}else if(fld_typ.equals("HB")){%>
    <button type="button" onclick="<%=val_cond%>" class="submit" accesskey="<%=lov_qry%>" id="<%=fld_nme%>" name="<%=fld_nme%>" ><%=fld_ttl%></button>   
    <%}}}
    }
    }else{%>
    <html:submit property="value(reports)" value="Get Report" onclick="settarget('genFrm','fixed')" styleClass="submit"/>
    <%}
    %> 
    </td></tr>
  <%
  ArrayList prpDspBlocked = info.getPageblockList();
  ArrayList prpNameList = (ArrayList)session.getAttribute("prpList");
  HashMap prpMap = (HashMap)session.getAttribute("prpMap");
  HashMap mprp = info.getSrchMprp();
  HashMap prp = info.getSrchPrp();
  String dfltDsp = "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;All&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
  String dfltVal = "0" ; 
  String grpNme="";
  String listTtl="";
  String listName="";
  String color="";
  %>
  <tr>
<td valign="top" class="hedPg">
<div id="popupContactSale" >
<table align="center" cellpadding="0" cellspacing="0" >
<tr><td><table>
<tr>
<td>Ignore Memo</td>
<td><html:textarea property="value(ignoreememo)" name="genericReportFormMG" cols="20" rows="2" styleId="ignoreememo"/></td>
</tr>
<tr>
<td>Count Mfg_pri For Given Memo</td>
<td><html:textarea property="value(mfgprimemo)" name="genericReportFormMG" cols="20" rows="2" styleId="mfgprimemo"/></td>
</tr>
</table> </td> </tr>
<tr><td>
<html:submit property="value(giasummary)" value="Search" onclick="disablePopupSale()" styleClass="submit"/>&nbsp;
<button type="button" onclick="disablePopupSale()" Class="submit" >Back</button> </td>
</tr>
</table>
</div>
<div id="popupContactDmd" align="center" >
<table align="center" cellpadding="10" cellspacing="10">
<tr>
<td>Sold Date : </td>  
<td><html:text property="value(frmDtestkoenclose)" styleId="frmDtestkoenclose" name="genericReportFormMG" size="10" /><img src="<%=info.getReqUrl()%>/images/calender.png" onClick="setYears(2011, <%=info.getCurrentYear()%>);showCalender(this, 'frmDtestkoenclose');"> 
To&nbsp; <html:text property="value(toDtestkoenclose)" styleId="toDtestkoenclose" name="genericReportFormMG"  size="10"/> <img src="<%=info.getReqUrl()%>/images/calender.png" onClick="setYears(2011, <%=info.getCurrentYear()%>);showCalender(this, 'toDtestkoenclose');"></td>
</tr>
<tr>
<td>Date : </td>  
<td><html:text property="value(frmdtestkoenclose)" styleId="frmdtestkoenclose" name="genericReportFormMG" size="10" /><img src="<%=info.getReqUrl()%>/images/calender.png" onClick="setYears(2011, <%=info.getCurrentYear()%>);showCalender(this, 'frmdtestkoenclose');"> 
To&nbsp; <html:text property="value(todtestkoenclose)" styleId="todtestkoenclose" name="genericReportFormMG"  size="10"/> <img src="<%=info.getReqUrl()%>/images/calender.png" onClick="setYears(2011, <%=info.getCurrentYear()%>);showCalender(this, 'todtestkoenclose');"></td>
</tr>

<tr>
<td>
Grid
</td>
<td>
      <%
      pageList=(ArrayList)pageDtl.get("LEVEL1LPRP");
      if(pageList!=null && pageList.size() >0){%>
      <html:select  property="value(level1openclose)" name="genericReportFormMG" styleId="level1openclose" style="width:100px"   > 
      <%for(int j=0;j<pageList.size();j++){
      pageDtlMap=(HashMap)pageList.get(j);
      fld_ttl=(String)pageDtlMap.get("fld_ttl");
      fld_nme=(String)pageDtlMap.get("fld_nme"); %>
      <html:option value="<%=fld_nme%>" ><%=fld_ttl%></html:option> 
    <%}%>
    </html:select> 
    <%}%>
</td>
</tr>
<tr><td colspan="3" align="center"><table><tr>
<td><html:submit property="value(stkoencloseBtn)" value="Search" onclick="disablePopupDmd()" styleClass="submit" /> </td>
<td><button type="button" onclick="disablePopupDmd()" Class="submit" >Back</button> </td>
</tr></table></td>
</tr>
</table>
</div>
  </td>
  </tr>
  <tr>
  <td align="left" style="padding:10px 0px 0px 20px;">
  <table><tr>
  <%
for(int i=0;i< prpNameList.size();i++){
  grpNme = (String)prpNameList.get(i);
  listName = (String)prpMap.get(grpNme);
  listTtl = (String)prpMap.get(grpNme+"TTL");
  if(i==0){
  color="color:#ffffff";
  }
  else{
  color="";
  }
  %>
 <td> <span class="txtLink" id="<%=grpNme%>_TAB" style="<%=color%>"  onclick="showHideDiv('.genericTB','<%=grpNme%>',this)"> <%=listTtl%></span></td>
  <%}%>
      <%
      pageList=(ArrayList)pageDtl.get("PKTINFO");
            if(pageList!=null && pageList.size() >0){
            for(int j=0;j<pageList.size();j++){
            pageDtlMap=(HashMap)pageList.get(j);
            dflt_val=(String)pageDtlMap.get("dflt_val"); %>
      <td nowrap="nowrap" style="<%=dflt_val%>"><span class="txtLink" id="PKTINFO_TAB" style="<%=color%>" onclick="showHideDiv('.genericTB','PKTINFO',this)"> Search By Packets</span></td>
    <%}}%>
  <td><span class="txtLink" id="ADDINFO_TAB" style="<%=color%>" onclick="showHideDiv('.genericTB','ADDINFO',this)"> Additional Filters</span></td>
    <%  pageList=(ArrayList)pageDtl.get("PSEARCH");
            if(pageList!=null && pageList.size() >0){
            for(int j=0;j<pageList.size();j++){
            pageDtlMap=(HashMap)pageList.get(j);
            dflt_val=(String)pageDtlMap.get("dflt_val"); %>
    <td nowrap="nowrap" style="<%=dflt_val%>"><span class="txtLink" id="PSEARCH_TAB" style="<%=color%>" onclick="showHideDiv('.genericTB','PSEARCH',this)"> Periodic Search</span></td>
    <%}}%>
    <%
      pageList=(ArrayList)pageDtl.get("GRIDFMT");
            if(pageList!=null && pageList.size() >0){
            for(int j=0;j<pageList.size();j++){
            pageDtlMap=(HashMap)pageList.get(j);
            dflt_val=(String)pageDtlMap.get("dflt_val"); %>
    <td nowrap="nowrap" style="<%=dflt_val%>"><span class="txtLink" id="GRIDFMT_TAB" style="<%=color%>" onclick="showHideDiv('.genericTB','GRIDFMT',this)"> Grid Format</span></td>
    <%}}%>
    <%if(!rpt.equals("")){%>
    <td nowrap="nowrap" style=""><span class="txtLink" id="GRIDRPT_TAB" style="<%=color%>" onclick="showHideDiv('.genericTB','GRIDRPT',this)"> Reports</span></td>
    <%}%>
  </tr>
  </table>
  </td>
  </tr>
  
  <tr>
  <td valign="top" class="hedPg">
  
  <table>
  <tr><td valign="top">
  <%for(int i=0;i< prpNameList.size();i++){
  grpNme = (String)prpNameList.get(i);
  listName = (String)prpMap.get(grpNme);
  listTtl = (String)prpMap.get(grpNme+"TTL");
  ArrayList prpList = (ArrayList)session.getAttribute(listName);
  String display="";
  String border="";
  if(grpNme.equals("BSC")){
  display="display:block;";
 
  }
  else{
  display="display:none;";
  
  }
   
  if(prpList!=null && prpList.size()>0){ %>
  <table id="<%=grpNme%>"  class="genericTB"  style="<%=display%>">
  <tr><td>
  
  <div>
<span class="pgHed"><%=listTtl%>&nbsp;&nbsp;<img src="../images/single_errow.png" border="0" width="5" height="8" /></span>
  <%
  int prpCount=0;
  for(int j=0;j<prpList.size();j++){
  ArrayList prpDtl = (ArrayList)prpList.get(j);
  String lprp= util.nvl((String)prpDtl.get(0));
  if(prpDspBlocked.contains(lprp)){
  }else{
  prpCount++;
  String mutiTextId = "MTXT_"+lprp;
  String prpDsc = util.nvl((String)mprp.get(lprp+"D"));
  String lprpTyp = util.nvl((String)mprp.get(lprp+"T"));
  if(prpDsc==null)
     prpDsc =lprp;
 ArrayList prpPrt = (ArrayList)prp.get(lprp+"P");
 ArrayList prpSrt = (ArrayList)prp.get(lprp+"S");
 ArrayList prpVal = (ArrayList)prp.get(lprp+"V");
 String fld1 = lprp+"_1";
 String fld2 = lprp+"_2";
 String prpFld1 = "value(" + fld1 + ")" ;
 String prpFld2 = "value(" + fld2 + ")" ; 
 String onChg1 = "setFrTo('"+ fld1 + "','" + fld2 + "');setFrToCheck('"+ fld1 + "','" + fld2 + "','"+lprp+"')\"";
 String onChg2 = "setFrTo('"+ fld1 + "','" + fld2 + "');setFrToCheck('"+ fld1 + "','" + fld2 + "','"+lprp+"')\"";
            
 if(prpCount==6){
 prpCount=1;
 %>
 </div><div class="InPrpDiv">
 
 <%}
  if(prpCount==1 && j==0){%>
  <div class="InPrpDiv">
<%  }
  
  %>
  
   <%if(prpSrt!=null){
   String loadStrL = "ALL";
   %>
    <div id="<%=lprp%>" class="prpDivDP" align="center" >
    <table cellpadding="3" width="100px"  cellspacing="2" class="bground">
             
            <tr><td colspan="2" align="left"><span class="txtBold"><%=prpDsc%>&nbsp;&nbsp;<img src="../images/single_errow.png" border="0" width="5" height="8" /></span></td></tr>
             <tr><td> 
             
           <html:select  property="<%=prpFld1%>" name="genericReportFormMG"  style="width:75px" onchange="<%=onChg1%>" styleId="<%=fld1%>"  > 
     
            <html:option value="<%=dfltVal%>"><%=dfltDsp%></html:option>
            <%
            for(int m=0; m < prpSrt.size(); m++) {
                String pPrt = (String)prpPrt.get(m);
                String pSrt = (String)prpSrt.get(m);
               
              
               
            %>
              <html:option value="<%=pSrt%>" ><%=pPrt%></html:option> 
            <%}%>
              </html:select>        </td><td>
             
           <html:select property="<%=prpFld2%>" name="genericReportFormMG"  style="width:75px" onchange="<%=onChg2%>" styleId="<%=fld2%>"  > 
     
            <html:option value="<%=dfltVal%>"><%=dfltDsp%></html:option>
            <%
            for(int m=0; m < prpSrt.size(); m++) {
                String pPrt = (String)prpPrt.get(m);
                String pSrt = (String)prpSrt.get(m);
               
            
             
            %>
              <html:option value="<%=pSrt%>" ><%=pPrt%></html:option> 
            <%}%>
              </html:select>   
             
             </td> </tr>
             <%for(int m=0;m<prpSrt.size();m++){
                String isSelected = "";
                String pPrtl = (String)prpPrt.get(m);
                String pSrtl = (String)prpSrt.get(m);
                String vall = (String)prpVal.get(m);
                String chFldNme = "value("+lprp+"_"+vall+")" ;
                String onclick= "checkPrp(this, 'MTXT_"+lprp+"','"+lprp+"')";
                String checId = lprp+"_"+pSrtl;
               
                
//                String fld = util.nvl((String)favMap.get(lprp+"_"+pSrtl));
//                 if(fld.equals(pSrtl+"_to_"+pSrtl)){
//                   isSelected = "checked=\"checked\"";
//                   loadStrL = loadStrL+" , "+pPrtl;
//                   }
             %>
             <tr><td align="center"><html:checkbox  property="<%=chFldNme%>"  name="genericReportFormMG" styleId="<%=checId%>"  onclick="<%=onclick%>" value="<%=vall%>"/></td><td align="center"><%=pPrtl%></td></tr>
             <%}%>
             </table></div>
             <input type="hidden" id="HID_<%=lprp%>" value="<%=loadStrL%>" />
             <input type="hidden" id="<%=mutiTextId%>" value="<%=loadStrL%>" />
     <%}else{%>
      <div id="<%=lprp%>" class="prpDivTxt" align="center" >
     <table cellpadding="3" width="150px"  cellspacing="3" class="bground">
     <tr><td colspan="2"><span class="txtBold"><%=prpDsc%>&nbsp;&nbsp;<img src="../images/single_errow.png" border="0" width="5" height="8" /></span></td></tr>
     <tr>
      <%if(lprpTyp.equals("D")){%>
        <td bgcolor="#FFFFFF" align="center">
        <table><tr>
        <td><html:text property="<%=prpFld1%>"  styleClass="txtStyle"  name="genericReportFormMG" styleId="<%=fld1%>"   size="9" maxlength="25" /> </td>
        <td><a href="#" id="cid" onClick="setYears(1900, '<%=info.getCurrentYear()%>' );showCalender(this, '<%=prpFld1%>');">
                <img src="<%=info.getReqUrl()%>/images/calender.png"></a></td>
        <td><html:text property="<%=prpFld2%>"  styleClass="txtStyle"  name="genericReportFormMG"   size="9" maxlength="25" /> </td>
        <td><a href="#"  onClick="setYears(1900, '<%=info.getCurrentYear()%>' );showCalender(this, '<%=prpFld2%>');">
                <img src="<%=info.getReqUrl()%>/images/calender.png"></a></td>
        </tr></table>
        </td>
        
        <%} else if(lprpTyp.equals("T")){%>
       
        <td bgcolor="#FFFFFF" align="center" colspan="2">
        <html:text property="<%=prpFld1%>"  styleClass="txtStyle"  name="genericReportFormMG" styleId="<%=fld1%>"  size="24" /> 
        </td>
       
        <%} else{%>
       
           <td bgcolor="#FFFFFF" align="center"><html:text property="<%=prpFld1%>"  styleClass="txtStyle"  name="genericReportFormMG" styleId="<%=fld1%>"  size="11" maxlength="25" onchange="isNumericDecimal(this.id)" /></td>
        <td bgcolor="#FFFFFF" align="center"><html:text property="<%=prpFld2%>"   styleClass="txtStyle" name="genericReportFormMG"   size="11" maxlength="25" onchange="isNumericDecimal(this.id)" /></td>
        
        <%}%>
        </tr></table></div>
     <%}%>
             
  <%}}%></div></div>
  </td>
  </tr>
  </table>
  <%}}%>
  <table id="PKTINFO"  class="genericTB"  style="display:none">
              <tr>
              <td>
              <table><tr>
              <td colspan="2" valign="top">
 <html:radio property="value(srchRef)"  styleId="vnm" value="vnm" /> Packet Code.
 <html:radio property="value(srchRef)"  styleId="cert_no" value="cert_no" /> Cert No.
 <table>
 <tr>
<td colspan="2"><html:textarea property="value(vnm)" name="genericReportFormMG" cols="30" rows="15" styleId="pid"/></td>
</tr>
 </table>
 </td></tr>
              </table>
              </td>
              </tr>
              </table>
  <table id="ADDINFO"  class="genericTB"  style="display:none">
  <tr><td>
  <table><tr><td>Search Type:</td><td align="left">
              <%
      pageList=(ArrayList)pageDtl.get("GENRCFLT");
      if(pageList!=null && pageList.size() >0){%>
      <html:select  property="value(srchTyp)" name="genericReportFormMG" onchange="GenericSrchDW(this)" styleId="srchTyp" style="width:100px"   > 
     <html:option value="0">---select---</html:option>
      <%for(int j=0;j<pageList.size();j++){
      pageDtlMap=(HashMap)pageList.get(j);
      fld_ttl=(String)pageDtlMap.get("fld_ttl");
      dflt_val=(String)pageDtlMap.get("dflt_val"); %>
      <html:option value="<%=dflt_val%>" ><%=fld_ttl%></html:option> 
    <%}%>
    </html:select> 
    <%}%>
              </td></tr>
              <tr><td colspan="2" style="display:none" id="addInfo">
           
               
              <table>
              
              <tr><td colspan="2">
              <div style="display:none" id="idn"><table><tr>
              <td>Id </td><td>
              <div>
              <html:text property="value(idn)"  name="genericReportFormMG"   />
              </div>
              </td></tr></table>
              </div></td></tr>
               <tr>
              <td colspan="2">
              <div style="display:none" id="byr"><table><tr>
              <td> Name: </td><td nowrap="nowrap">
              
               <input type="text" id="nme" name="nme" autocomplete="off" class="sugBox" onkeypress="return disableEnterKey(event);"
                  onKeyUp="doCompletionGNR('nme', 'nmePop', '../ajaxAction.do?1=1', event)"
                  onKeyDown="setDown('nmePop', 'nmeID', 'nme',event)"/>
               <img src="../images/addrow.jpg" width="17" height="17" title="Click To get Names" onclick="doCompletionMobileGNR('nme', 'nmePop', '../ajaxAction.do?1=1')">
                <html:hidden property="value(byr)" styleId="nmeID"/>
               <div style="position: absolute;">
                 <select id="nmePop" name="nmePop"
                   style="display:none;300px;" 
                   class="sugBoxDiv"
                   onKeyDown="setDown('nmePop', 'nmeID', 'nme',event)" 
                  onDblClick="setVal('nmePop', 'nmeID', 'nme', event);hideObj('nmePop')" 
                  onClick="setVal('nmePop', 'nmeID', 'nme', event);" 
               size="10">
             </select>
               </div></td></tr></table></div>
              </td></tr>             
              </table></td></tr>
              </table>
            
              </td> </tr>
              <tr>
              <td>
              <table>
 <%String compwithDis="";
pageList=(ArrayList)pageDtl.get("COMPWITHDIS");
if(pageList!=null && pageList.size() >0){
for(int j=0;j<pageList.size();j++){
pageDtlMap=(HashMap)pageList.get(j);
compwithDis=(String)pageDtlMap.get("dflt_val");
}
}
%>
<tr style="<%=compwithDis%>">
<td>Compare By:</td><td align="left">
<%pageList=(ArrayList)pageDtl.get("COMPWITH");
if(pageList!=null && pageList.size() >0){%>
<html:select property="value(compareWith)" name="genericReportFormMG" styleId="compareWith" style="width:100px" >
<%for(int j=0;j<pageList.size();j++){
pageDtlMap=(HashMap)pageList.get(j);
fld_ttl=(String)pageDtlMap.get("fld_ttl");
dflt_val=(String)pageDtlMap.get("dflt_val"); %>
<html:option value="<%=dflt_val%>" ><%=fld_ttl%></html:option>
<%}%>
</html:select>
<%}%>
</td>
</tr>
              </table>
              </td>
              </tr>
              </table>
 <table id="PSEARCH"  class="genericTB"  style="display:none">
 <tr><td>
              <div id="psearch"><table>
              <tr>
              <td>
              Period 
              </td>
              <td>
              <html:select  property="value(period)" name="genericReportFormMG" styleId="period" > 
              <html:option value="ALL" >Period Wise</html:option> 
              <html:option value="W" >Weekly</html:option> 
              <html:option value="M" >Monthly</html:option> 
              <html:option value="Q" >Quarterly</html:option> 
              </html:select> 
              </td>
              </tr>
              <tr>
              <td>
              Periodic Comparison
              </td>
              <td>
               <html:radio property="value(periodcomp)"  styleId="periodcompY" value="Y" /> YES
               <html:radio property="value(periodcomp)"  styleId="periodcompN" value="N" /> NO
              </td>
              </tr>
               <tr>
           <td>P1</td>
           <td>
            From &nbsp;<html:text property="value(p1frm)" styleClass="txtStyle"  styleId="p1frm"  size="15" onblur="analysisperiod(this,'p1to')"  /> <a href="#" style="margin-top:5px;"  onClick="setYears(1900, '<%=info.getCurrentYear()%>' );showCalender(this, 'p1frm');">
                <img src="<%=info.getReqUrl()%>/images/calender.png"></a>
            &nbsp;To &nbsp;<html:text property="value(p1to)" styleClass="txtStyle"  styleId="p1to"  size="15"  /> <a href="#" style="margin-top:5px;"  onClick="setYears(1900, '<%=info.getCurrentYear()%>' );showCalender(this, 'p1to');">
                <img src="<%=info.getReqUrl()%>/images/calender.png"></a>
           </td>
           </tr>
           <tr>
           <td>P2</td>
           <td>
            From&nbsp; <html:text property="value(p2frm)" styleClass="txtStyle"  styleId="p2frm"  size="15" onblur="analysisperiod(this,'p2to')"  /> <a href="#" style="margin-top:5px;"  onClick="setYears(1900, '<%=info.getCurrentYear()%>' );showCalender(this, 'p2frm');">
                <img src="<%=info.getReqUrl()%>/images/calender.png"></a>
            &nbsp;To &nbsp;<html:text property="value(p2to)" styleClass="txtStyle"  styleId="p2to"  size="15"  /> <a href="#" style="margin-top:5px;"  onClick="setYears(1900, '<%=info.getCurrentYear()%>' );showCalender(this, 'p2to');">
                <img src="<%=info.getReqUrl()%>/images/calender.png"></a>
           </td>
           </tr>
           <tr>
            <td>P3</td>
           <td>
            From &nbsp;<html:text property="value(p3frm)" styleClass="txtStyle"  styleId="p3frm"  size="15" onblur="analysisperiod(this,'p3to')"  /> <a href="#" style="margin-top:5px;"  onClick="setYears(1900, '<%=info.getCurrentYear()%>' );showCalender(this, 'p3frm');">
                <img src="<%=info.getReqUrl()%>/images/calender.png"></a>
            &nbsp;To &nbsp;<html:text property="value(p3to)" styleClass="txtStyle"  styleId="p3to"  size="15"  /> <a href="#" style="margin-top:5px;"  onClick="setYears(1900, '<%=info.getCurrentYear()%>' );showCalender(this, 'p3to');">
                <img src="<%=info.getReqUrl()%>/images/calender.png"></a>
           </td>
           </tr>
           </table>
              </div></td></tr>
  </table>
  <table id="GRIDFMT"  class="genericTB"  style="display:none">
  <tr><td>
  <table>
  <tr><td align="left">Grid Format:
    <html:select  property="value(gridfmt)" name="genericReportFormMG" styleId="gridfmt" > 
     <%if(gridfmtLst!=null && gridfmtLst.size()>0){
     for(int i=0;i<gridfmtLst.size();i++){
     String gridfmtval=(String)gridfmtLst.get(i);%>
     <html:option value="<%=gridfmtval%>" ><%=gridfmtval%></html:option> 
     <%}}%>
    </html:select> 
    </td></tr>
  </table>
  </td> </tr>
  </table>
  <%if(!rpt.equals("")){%>
  <table id="GRIDRPT"  class="genericTB"  style="display:none">
  <%
  ArrayList ANLS_GROUP=(ArrayList)session.getAttribute("ANLS_GROUP");
  ArrayList mprcIdnAll=(ArrayList)session.getAttribute("mprcIdnAll");
  HashMap mprcBeanAll=(HashMap)session.getAttribute("mprcBeanAll");%>
  <tr><td>
  <table>
  <tr>
  <td>
  <table>
  <tr>
  <td>Process</td>
  </tr>
  <tr>
  <%
  int count=0;
  for(int p=0;p<mprcIdnAll.size();p++){
  String mprcId= (String)mprcIdnAll.get(p);
  String chFldNme = "value(PRC_"+mprcId+")" ;
  String checId = "PRC_"+mprcId;
  if(count==5){
  count=0;%>
  </tr>
  <tr>
  <%}
  count++;%>
  <td><html:checkbox  property="<%=chFldNme%>"  name="genericReportFormMG" styleId="<%=checId%>"  onclick="" value="<%=mprcId%>"/> <%=util.nvl((String)mprcBeanAll.get(mprcId))%></td>
  <%}%>
  </tr>
  <tr>
  <td>Process Status</td>
  <td>
  <html:select property="value(prcstt)" styleId="prcstt" name="genericReportFormMG" >
  <html:option value="" >All</html:option>
  <html:option value="IS" >Issue</html:option>
  <html:option value="RT">Return</html:option>
  </html:select>
  </td>
  </tr>
  <tr>
  <td>Type</td>
  <td>
  <html:select property="value(rpttype)" styleId="rpttype" name="genericReportFormMG" >
  <html:option value="N" >Summary</html:option>
  <html:option value="Y">Detail</html:option>
  </html:select>
  </td>
  </tr>
  <%for(int p=1;p<3;p++){
  String chFldNme = "value(GRP_"+p+")" ;
  String checId = "GRP_"+p;%>
    <tr>
  <td>Group <%=p%></td>
  <td>
  <html:select property="<%=chFldNme%>" styleId="<%=checId%>" name="genericReportFormMG" >
  <%for(int a=0;a<ANLS_GROUP.size();a++){
  String grplprpval=(String)ANLS_GROUP.get(a);
  String grplprpDsc = util.nvl((String)mprp.get(grplprpval+"D"));
  %>
  <html:option value="<%=grplprpval%>" ><%=grplprpDsc%></html:option>
  <%}%>
  </html:select>
  </td>
  <td>
  By &nbsp;&nbsp;
  <%
  chFldNme = "value(GRPBY_"+p+")" ;
  checId = "GRPBY_"+p;%>
  <html:select property="<%=chFldNme%>" styleId="<%=checId%>" name="genericReportFormMG" >
  <html:option value="PRP" >Property Wise</html:option>
  <html:option value="GRP" >Group Wise</html:option>
  </html:select>
  </td>
  </tr>
  <%}%>
  <tr>
  <td>Report</td>
  <td>
  <html:select property="value(reportNme)" styleId="reportNme" name="genericReportFormMG" >
    <%pageList=(ArrayList)pageDtl.get("RPT");
    if(pageList!=null && pageList.size() >0){%>
    <%for(int j=0;j<pageList.size();j++){
    pageDtlMap=(HashMap)pageList.get(j);
    fld_ttl=(String)pageDtlMap.get("fld_ttl");
    dflt_val=(String)pageDtlMap.get("dflt_val"); %>
    <html:option value="<%=dflt_val%>" ><%=fld_ttl%></html:option>
    <%}%>
    <%}%>
  </html:select>
  </td>
  </tr>
  </table>
  </td>
  </tr>
  </table>
  </td> </tr>
  </table>
  <%}%>
  </td></tr></table>
  </td></tr>
  </table>
   </td></tr>
  </table></html:form>
  </body>
  <%@include file="../calendar.jsp"%>
</html>