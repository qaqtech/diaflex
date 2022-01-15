<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page errorPage="../error_page.jsp" %>
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import="ft.com.*,java.util.ArrayList,java.util.ArrayList,java.util.HashMap, java.sql.*, java.util.Enumeration, java.util.logging.Level"%>

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
<%  HashMap dbinfo = info.getDmbsInfoLst();%>
<input type="hidden" id="cut" value="<%=dbinfo.get("CUT")%>" />
<input type="hidden" id="sym" value="<%=dbinfo.get("SYM")%>" />
<input type="hidden" id="pol" value="<%=dbinfo.get("POL")%>" />
<input type="hidden" id="shape" value="<%=dbinfo.get("SHAPE")%>" />
<input type="hidden" id="size" value="<%=dbinfo.get("SIZE")%>" />
<input type="hidden" id="col" value="<%=dbinfo.get("COL")%>" />
<input type="hidden" id="clr" value="<%=dbinfo.get("CLR")%>" />
 <html:form action="/mix/mixgenericReport.do?method=save" method="post" target="fixed" onsubmit="return validateAnalysisRpt();">
 <%
  String btnKey = request.getParameter("btn");
  HashMap allPageDtl= (info.getPageDetails() == null)?new HashMap():(HashMap)info.getPageDetails();
  HashMap pageDtl=(HashMap)allPageDtl.get("MIXGENERIC_REPORT");
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
  <span class="pgHed">Mix Analysis Report
  </span></td></tr></table>
  </td></tr>
  
  <tr>
  <td valign="top" class="hedPg">
  <table id="statusTbl"><tr><td>Status:</td>
  <%pageList=(ArrayList)pageDtl.get("STTLST");
            if(pageList!=null && pageList.size() >0){
            for(int j=0;j<pageList.size();j++){
            pageDtlMap=(HashMap)pageList.get(j);
            fld_nme="value("+(String)pageDtlMap.get("fld_nme")+")";
            fld_ttl=(String)pageDtlMap.get("fld_ttl");
            fld_typ=(String)pageDtlMap.get("fld_typ");
            dflt_val=(String)pageDtlMap.get("dflt_val");
            val_cond=(String)pageDtlMap.get("val_cond");
            String chkid="STT_"+j;
            %>
            <td><html:checkbox property="<%=fld_nme%>" styleId="<%=chkid%>" value="<%=dflt_val%>" name="mixGenericReportForm"/></td><td><%=fld_ttl%></td>
 <%}}%>
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
  
   <!--<table cellpadding="0" cellspacing="0">
       <tr>
         <td> <html:checkbox property="value(is3Ex)" value="is3Ex"   styleId="is3Ex" name="mixGenericReportForm" onclick="setAnalysEX('EX',this)" />3 EX</td>
         <td> <html:checkbox property="value(is3VG)" value="is3VG"   styleId="is3VG" name="mixGenericReportForm" onclick="setAnalysEX('VG',this)" />3 VG</td>
       </tr>
   </table>-->
  </td></tr>
  
   <tr>
  <td valign="top" class="hedPg">
  <%
 
  if(btnKey!=null){%>
     Upgrade Type : <html:radio property="value(upgrade)" name="mixGenericReportForm"   value="COL" />&nbsp;Color&nbsp;
   &nbsp;&nbsp;<html:radio property="value(upgrade)" name="mixGenericReportForm"   value="CLR" />&nbsp;Clarity&nbsp;
   &nbsp;&nbsp;<html:radio property="value(upgrade)" name="mixGenericReportForm"   value="BOTH" />&nbsp;Both&nbsp;
   <html:submit property="value(colClrUpd)" value="Assort Upgrade" styleClass="submit" />
  <%}else{
  %>
  <html:submit property="value(submit)" value="Submit" styleClass="submit" />
<%}    
            pageList=(ArrayList)pageDtl.get("SUBMIT");
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
    <html:submit property="<%=fld_nme%>" value="<%=fld_ttl%>" onclick="<%=val_cond%>" styleClass="submit"/>
    <%}else if(fld_typ.equals("B")){%>
    <html:button property="<%=fld_nme%>" value="<%=fld_ttl%>" onclick="<%=val_cond%>" styleClass="submit"/>
    <%}else if(fld_typ.equals("HB")){%>
    <button type="button" onclick="<%=val_cond%>" class="submit" accesskey="<%=lov_qry%>" id="<%=fld_nme%>" name="<%=fld_nme%>" ><%=fld_ttl%></button>   
    <%}}}
    %> </td></tr>
  <%
  ArrayList prpDspBlocked = info.getPageblockList();
  ArrayList prpNameList = (ArrayList)session.getAttribute("mixprpList");
  HashMap prpMap = (HashMap)session.getAttribute("mixprpMap");
  HashMap mprp = info.getSrchMprp();
  HashMap prp = info.getSrchPrp();
  if(prp==null){
  DBUtil dbutil = new DBUtil();
      DBMgr db = new DBMgr();
      db.setCon(info.getCon());
      dbutil.setDb(db);
//  dbutil.initPrpSrch();
  prp = info.getSrchPrp();
  mprp = info.getSrchMprp();
  }
  String dfltDsp = "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;All&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
  String dfltVal = "0" ; 
  String grpNme="";
  String listTtl="";
  String listName="";
  String color="";
  %>
  
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
    <td style="<%=dflt_val%>"><span class="txtLink" id="GRIDFMT_TAB" style="<%=color%>" onclick="showHideDiv('.genericTB','GRIDFMT',this)"> Grid Format</span></td>
    <%}}%>
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
    <table cellpadding="3" width="100px"  cellspacing="3" class="bground">
             
            <tr><td colspan="2" align="left"><span class="txtBold"><%=prpDsc%>&nbsp;&nbsp;<img src="../images/single_errow.png" border="0" width="5" height="8" /></span></td></tr>
             <tr><td> 
             
           <html:select  property="<%=prpFld1%>" name="mixGenericReportForm"  style="width:75px" onchange="<%=onChg1%>" styleId="<%=fld1%>"  > 
     
            <html:option value="<%=dfltVal%>"><%=dfltDsp%></html:option>
            <%
            for(int m=0; m < prpSrt.size(); m++) {
                String pPrt = (String)prpPrt.get(m);
                String pSrt = (String)prpSrt.get(m);
               
              
               
            %>
              <html:option value="<%=pSrt%>" ><%=pPrt%></html:option> 
            <%}%>
              </html:select>        </td><td>
             
           <html:select property="<%=prpFld2%>" name="mixGenericReportForm"  style="width:75px" onchange="<%=onChg2%>" styleId="<%=fld2%>"  > 
     
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
             <tr><td align="center"><html:checkbox  property="<%=chFldNme%>"  name="mixGenericReportForm" styleId="<%=checId%>"  onclick="<%=onclick%>" value="<%=vall%>"/></td><td align="center"><%=pPrtl%></td></tr>
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
        <td><html:text property="<%=prpFld1%>"  styleClass="txtStyle"  name="mixGenericReportForm" styleId="<%=fld1%>"   size="9" maxlength="25" /> </td>
        <td><a href="#" id="cid" onClick="setYears(1900, '<%=info.getCurrentYear()%>' );showCalender(this, '<%=prpFld1%>');">
                <img src="<%=info.getReqUrl()%>/images/calender.png"></a></td>
        <td><html:text property="<%=prpFld2%>"  styleClass="txtStyle"  name="mixGenericReportForm"   size="9" maxlength="25" /> </td>
        <td><a href="#"  onClick="setYears(1900, '<%=info.getCurrentYear()%>' );showCalender(this, '<%=prpFld2%>');">
                <img src="<%=info.getReqUrl()%>/images/calender.png"></a></td>
        </tr></table>
        </td>
        
        <%} else if(lprpTyp.equals("T")){%>
       
        <td bgcolor="#FFFFFF" align="center" colspan="2">
        <html:text property="<%=prpFld1%>"  styleClass="txtStyle"  name="mixGenericReportForm" styleId="<%=fld1%>"  size="24" maxlength="20" /> 
        </td>
       
        <%} else{%>
       
           <td bgcolor="#FFFFFF" align="center"><html:text property="<%=prpFld1%>"  styleClass="txtStyle"  name="mixGenericReportForm" styleId="<%=fld1%>"  size="11" maxlength="25" onchange="isNumericDecimal(this.id)" /></td>
        <td bgcolor="#FFFFFF" align="center"><html:text property="<%=prpFld2%>"   styleClass="txtStyle" name="mixGenericReportForm"   size="11" maxlength="25" onchange="isNumericDecimal(this.id)" /></td>
        
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
<td colspan="2"><html:textarea property="value(vnm)" name="mixGenericReportForm" cols="30" rows="15" styleId="pid"/></td>
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
      <html:select  property="value(srchTyp)" name="mixGenericReportForm" onchange="GenericSrchDW(this)" styleId="srchTyp" style="width:100px"   > 
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
              <html:text property="value(idn)"  name="mixGenericReportForm"   />
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
              </table>
  </td></tr></table>
  </td></tr>
  </table>
   </td></tr>
  </table></html:form>
  </body>
  <%@include file="../calendar.jsp"%>
</html>