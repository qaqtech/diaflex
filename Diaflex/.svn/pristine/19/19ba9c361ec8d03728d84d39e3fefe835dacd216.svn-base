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
    <title>GenericPktDtl</title>
   <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/bse.css?v=<%=info.getJsVersion()%>"/>
    <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/general.css?v=<%=info.getJsVersion()%>"/>
     <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/style.css?v=<%=info.getJsVersion()%>"/>
     <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/bse.js?v=<%=info.getJsVersion()%> " > </script>
        <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/ajax.js?v=<%=info.getJsVersion()%> " > </script>
      <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/assortScript.js?v=<%=info.getJsVersion()%> " > </script>
     <script type="text/javascript" src="<%=info.getReqUrl()%>/PopScripts/popup.js?v=<%=info.getJsVersion()%> " > </script>
   <script src="<%=info.getReqUrl()%>/PopScripts/jquery-1.2.6.min.js" type="text/javascript"></script>
    <script type="text/javascript" src="<%=info.getReqUrl()%>/jqueryScript/jquery.js " > </script>

  </head>
        <%String logId=String.valueOf(info.getLogId());
        String onfocus="cook('"+logId+"')";
        %>
 <body onfocus="<%=onfocus%>">
  <%
    ArrayList grpOrderList = (ArrayList)session.getAttribute("grpOrderList");
    ArrayList pktStkIdnList = (ArrayList)request.getAttribute("pktStkIdnList");
    String view = (String)request.getAttribute("view");
    String selectstt = util.nvl((String)session.getAttribute("GenStt"));
    String psearch = util.nvl((String)session.getAttribute("psearch"));
    ArrayList statusLst = (ArrayList)session.getAttribute("statusLst");
    HashMap sttMap = (HashMap)request.getAttribute("sttMap");
    HashMap dataDtl = (HashMap)session.getAttribute("pktdataDtl");
    ArrayList pktList = new ArrayList();
   String  key = util.nvl((String)request.getAttribute("key"));
   String  row = util.nvl2((String)request.getAttribute("row"), "ALL");
   String  col = util.nvl2((String)request.getAttribute("col"), "ALL");
   String  status = util.nvl2((String)request.getAttribute("status"), "ALL");
   ArrayList vwPrpLst = (ArrayList)session.getAttribute("ANLS_VW");
      HashMap mprp = info.getMprp();
      ArrayList itemHdr = new ArrayList();
      String selectidstt="";
      String defaultstt=(String)statusLst.get(0);
      ArrayList prpDspBlocked = info.getPageblockList();
  HashMap allPageDtl= (info.getPageDetails() == null)?new HashMap():(HashMap)info.getPageDetails();
  HashMap pageDtl=(HashMap)allPageDtl.get("GENERIC_REPORT");
    ArrayList pageList=new ArrayList();
    HashMap pageDtlMap=new HashMap();
    String fld_nme="",fld_ttl="",val_cond="",dflt_val="",fld_typ="",form_nme="",flg1="",lov_qry="";
        String comparewith=util.nvl(info.getCompareWith(),"cmp");
        HashMap dbinfo = info.getDmbsInfoLst();
        String cnt = (String)dbinfo.get("CNT");
     %>
  <input type="hidden" id="reqUrl" name="reqUrl" value="<%=info.getReqUrl()%>"/>
  <select id="vwPrp" style="display:none;" >
  <%for(int i=0;i<vwPrpLst.size();i++){
  String prp = (String)vwPrpLst.get(i);
  if(prpDspBlocked.contains(prp)){
}else{
  String prpDsc = (String)mprp.get(prp+"D");
  String prpTyp = util.nvl((String)mprp.get(prp+"T"));
  if(prpTyp.equals("N")){
  %>
  <option value="<%=prp%>"></option>
  <%}}}%>
  </select>
<div id="backgroundPopup"></div>
<div id="popupContactASSFNL">
<table class="popUpTb">
 <tr><td height="5%">&nbsp;&nbsp;&nbsp;<html:button property="value(close)" styleClass="submit"  onclick="MKEmptyPopup('SC');disablePopupASSFNL();uncheckbox()" value="Close"  /> </td></tr>
<tr><td valign="top" height="95%">
<iframe name="SC" class="frameStyle" align="middle" frameborder="0">

</iframe></td></tr>
</table>
</div>
  <table width="100%"  border="0" align="center" cellpadding="0" cellspacing="0" >
  
 
  <tr>
    <td height="5" valign="top" class="greyline"><img src="../images/grey_linebig.png" width="685" height="5" align="right" /></td>
  </tr>
  <tr>
  <td valign="top" class="hedPg">
  <table cellpadding="1" cellspacing="5"><tr><td valign="middle">
  <img src="../images/line.jpg" border="0" /></td><td valign="middle"> 
  <span class="pgHed">Packet Detail
  </span></td></tr></table>
  </td></tr>
  <tr><td valign="top" class="tdLayout">
  <table>
  <tr> <td><span class="txtLink" onclick="GenericRT('ALL')" > ALL</span></td>
 <%
 if(status.equals("ALL")){
 for(int st=0;st<statusLst.size();st++){
 selectidstt=(String)statusLst.get(st);
 %>
  <td>
 <span class="txtLink" onclick="GenericRT('<%=selectidstt%>_')" > <%=selectidstt%></span>
  </td> 
  <%}}else{%>
 <td>
  <span class="txtLink" onclick="GenericRT('<%=status%>_')" ><%=status%></span>
  </td>
  <%if(statusLst.contains("SOLD") && !status.equals("SOLD")){%>
 <td>
  <span class="txtLink" onclick="GenericRT('SOLD')" >Sold</span>
  </td>
  <%}}%>
 
  </tr>
  </table>
  </td></tr>
  <tr><td valign="top" class="tdLayout">
  <table>
<tr>
<td> <table class="prcPrntTbl" id="sum" cellspacing="1" cellpadding="3">
<tr id="sumHed">
<th height="15">&nbsp;</th>
<th height="15"><div align="center"><strong>Pcs</strong></div></th>
<th height="15"><div align="center"><strong>Cts</strong></div></th>
<th height="15"><div align="center"><strong>Avg Price</strong></div></th>
<th height="15"><div align="center"><strong>Avg disc </strong></div></th>
<th height="15"><div align="center"><strong>Avg Sale disc </strong></div></th>
<th height="15"><div align="center"><strong>Vlu </strong></div></th>
<%          pageList=(ArrayList)pageDtl.get("SELECTED");
            if(pageList!=null && pageList.size() >0){
            for(int j=0;j<pageList.size();j++){
            pageDtlMap=(HashMap)pageList.get(j);
            fld_ttl=(String)pageDtlMap.get("fld_ttl");
            fld_nme=(String)pageDtlMap.get("fld_nme");%>
            <th height="15"><div align="center"><strong><%=fld_nme%></strong></div></th>
            <%}}%>
</tr>
<tr id="ALL" style="display:none">
<td><div align="center"><strong>Total</strong></div></td>
<td height="20">
<div align="center">
<%=util.nvl((String)sttMap.get("QTY"))%>

</div>
</td>
<td><div align="center">
<%=util.nvl((String)sttMap.get("CTS"))%>

</div></td>

<td><div align="center">
<%=util.nvl((String)sttMap.get("AVGNRD"))%>
</div></td>

<td><div align="center">
<%=util.nvl((String)sttMap.get("RAP"))%>
</div></td>
<td><div align="center">
<%=util.nvl((String)sttMap.get("SD"))%>
</div></td>
<td><div align="center">
<%=util.nvl((String)sttMap.get("VLU"))%>
</div></td>
<%pageList=(ArrayList)pageDtl.get("SELECTED");
if(pageList!=null && pageList.size() >0){
for(int j=0;j<pageList.size();j++){
pageDtlMap=(HashMap)pageList.get(j);
fld_ttl=(String)pageDtlMap.get("fld_ttl");
fld_nme=(String)pageDtlMap.get("fld_nme");%>
<td><div align="center">
<%=util.nvl((String)sttMap.get(fld_ttl))%>
</div></td>
<%}}%>
</tr>

<%
if(status.equals("ALL")){
for(int st=0;st<statusLst.size();st++) {
String stylestt="";
selectidstt=(String)statusLst.get(st);
if(defaultstt.equals(selectidstt))
stylestt="display:''";
else
stylestt="display:none";
%>

<tr id="<%=selectidstt%>_" style="<%=stylestt%>">
<td><div align="center"><strong>Total</strong></div></td>
<td height="20">
<div align="center">
<%=util.nvl((String)sttMap.get(selectidstt+"QTY"))%>

</div>
</td>
<td><div align="center">
<%=util.nvl((String)sttMap.get(selectidstt+"CTS"))%>

</div></td>

<td><div align="center">
<%=util.nvl((String)sttMap.get(selectidstt+"AVGNRD"))%>
</div></td>

<td><div align="center">
<%=util.nvl((String)sttMap.get(selectidstt+"RAP"))%>

</div></td>
<td><div align="center">
<%=util.nvl((String)sttMap.get(selectidstt+"SD"))%>

</div></td>
<td><div align="center">
<%=util.nvl((String)sttMap.get(selectidstt+"VLU"))%>

</div></td>
<%pageList=(ArrayList)pageDtl.get("SELECTED");
if(pageList!=null && pageList.size() >0){
for(int j=0;j<pageList.size();j++){
pageDtlMap=(HashMap)pageList.get(j);
fld_ttl=(String)pageDtlMap.get("fld_ttl");
fld_nme=(String)pageDtlMap.get("fld_nme");%>
<td><div align="center">
<%=util.nvl((String)sttMap.get(selectidstt+fld_ttl))%>
</div></td>
<%}}%>
</tr>
<%}} else
{
defaultstt=status;
%>
<tr id="<%=status%>" style="">
<td><div align="center"><strong>Total</strong></div></td>
<td height="20">
<div align="center">
<%=util.nvl((String)sttMap.get(status+"QTY"))%>

</div>
</td>
<td><div align="center">
<%=util.nvl((String)sttMap.get(status+"CTS"))%>

</div></td>

<td><div align="center">
<%=util.nvl((String)sttMap.get(status+"AVGNRD"))%>
</div></td>

<td><div align="center">
<%=util.nvl((String)sttMap.get(status+"RAP"))%>

</div></td>
<td><div align="center">
<%=util.nvl((String)sttMap.get(status+"SD"))%>

</div></td>
<td><div align="center">
<%=util.nvl((String)sttMap.get(status+"VLU"))%>

</div></td>
<%pageList=(ArrayList)pageDtl.get("SELECTED");
if(pageList!=null && pageList.size() >0){
for(int j=0;j<pageList.size();j++){
pageDtlMap=(HashMap)pageList.get(j);
fld_ttl=(String)pageDtlMap.get("fld_ttl");
fld_nme=(String)pageDtlMap.get("fld_nme");%>
<td><div align="center">
<%=util.nvl((String)sttMap.get(status+fld_ttl))%>
</div></td>
<%}}%>
</tr>
<%if(statusLst.contains("SOLD") && !status.equals("SOLD")){%>
<tr id="SOLD" style="display:none">
<td><div align="center"><strong>Total</strong></div></td>
<td height="20">
<div align="center">
<%=util.nvl((String)sttMap.get("SOLDQTY"))%>

</div>
</td>
<td><div align="center">
<%=util.nvl((String)sttMap.get("SOLDCTS"))%>

</div></td>

<td><div align="center">
<%=util.nvl((String)sttMap.get("SOLDAVG"))%>
</div></td>

<td><div align="center">
<%=util.nvl((String)sttMap.get("SOLDRAP"))%>

</div></td>
<td><div align="center">
<%=util.nvl((String)sttMap.get("SOLDSD"))%>

</div></td>
<td><div align="center">
<%=util.nvl((String)sttMap.get("SOLDVLU"))%>

</div></td>
<%pageList=(ArrayList)pageDtl.get("SELECTED");
if(pageList!=null && pageList.size() >0){
for(int j=0;j<pageList.size();j++){
pageDtlMap=(HashMap)pageList.get(j);
fld_ttl=(String)pageDtlMap.get("fld_ttl");
fld_nme=(String)pageDtlMap.get("fld_nme");%>
<td><div align="center">
<%=util.nvl((String)sttMap.get("SOLD"+fld_ttl))%>
</div></td>
<%}}%>
</tr>
<%}}%>
</table>

</td>
</tr>
</table></td></tr>

  <tr><td valign="top" class="tdLayout">
   <%
    if(grpOrderList!=null && grpOrderList.size()>0){
    %>
    <table cellpadding="5" cellspacing="5">
    <tr><td nowrap="nowrap">Create Excel <img src="../images/ico_file_excel.png" onclick="goTo('genericReport.do?method=createXL','','')" border="0"/> 
             <%pageList=(ArrayList)pageDtl.get("FORMATED_EXL");
             if(pageList!=null && pageList.size() >0){
               for(int j=0;j<pageList.size();j++){
                 pageDtlMap=(HashMap)pageList.get(j);
                 fld_ttl=(String)pageDtlMap.get("fld_ttl");
                 dflt_val=(String)pageDtlMap.get("dflt_val");
                 if(dflt_val.equals("Y")){
                 %>
            <!--&nbsp;&nbsp;Formated Excel <img src="../images/ico_file_excel.png" onclick="goTo('genericReport.do?method=createXLDIFFFMT','','')" border="0"/>-->
             <%}}%>
            <%}%>
    <%
  ArrayList rolenmLst=(ArrayList)info.getRoleLst();
  String usrFlg=util.nvl((String)info.getUsrFlg());
  if(usrFlg.equals("SYS") || rolenmLst.contains("ADMIN")){
  %>
  &nbsp;&nbsp;<img src="../images/edit.jpg" title="Edit Properties Module" onclick="newWindow('<%=info.getReqUrl()%>/dynamicProperty.do?method=load&mdl=ANLS_VW&sname=ANLS_VW&par=A')" border="0" width="15px" height="15px"/>
  <%}
  String orderbyrow=row.replaceAll("\\'", "");
  String orderbycol=col.replaceAll("\\'", "");
  String callAnalysisGrpby="callAnalysisGrpby('"+key+"','"+orderbyrow+"','"+orderbycol+"','"+status+"')";
            pageList=(ArrayList)pageDtl.get("GRPBY");
             if(pageList!=null && pageList.size() >0){%>
             <!--Group by <html:select property="value(grpby)" styleId="grpby" onchange="<%=callAnalysisGrpby%>" name="genericReportFormMG" >
             <html:option value="">--Select--</html:option>
             <%
               for(int j=0;j<pageList.size();j++){
                 pageDtlMap=(HashMap)pageList.get(j);
                 fld_ttl=(String)pageDtlMap.get("fld_ttl");
                 dflt_val=(String)pageDtlMap.get("dflt_val");
                 %>
                <html:option value="<%=dflt_val%>"><%=fld_ttl%></html:option>
             <%}%>
            </html:select>-->
            <%}%>
            
    <%orderbyrow=row.replaceAll("\\'", "");
  orderbycol=col.replaceAll("\\'", "");
  callAnalysisGrpby="callAnalysisOrderby('"+key+"','"+orderbyrow+"','"+orderbycol+"','"+status+"')";
            pageList=(ArrayList)pageDtl.get("ORDERBY");
             if(pageList!=null && pageList.size() >0){%>
             <!--Order by <html:select property="value(orderby)" styleId="orderby" onchange="<%=callAnalysisGrpby%>" name="genericReportFormMG" >
             <html:option value="">--Select--</html:option>
             <%
               for(int j=0;j<pageList.size();j++){
                 pageDtlMap=(HashMap)pageList.get(j);
                 fld_ttl=(String)pageDtlMap.get("fld_ttl");
                 dflt_val=(String)pageDtlMap.get("dflt_val");
                 %>
                <html:option value="<%=dflt_val%>"><%=fld_ttl%></html:option>
             <%}%>
            </html:select>-->
            <%}%>
    </td>
    </tr>
    <tr ><td valign="top">
   
    <table class="dataTable" align="left" id="dataTable">
    <thead>
    <%int emphedspan=1;
    pageList=(ArrayList)pageDtl.get("DISPLAYDF");
    if(pageList!=null && pageList.size() >0){
    emphedspan=emphedspan+pageList.size();
    }
    %>
    <input type="hidden" name="calStt" id="calStt" value=""/>
     <tr id="emphed"><td colspan="<%=emphedspan%>"></td>
     <%for(int j=0; j < vwPrpLst.size(); j++ ){
    String prp = (String)vwPrpLst.get(j);
    if(prpDspBlocked.contains(prp)){
    }else{
     %>
     <%if(prp.equals("RTE")){
      pageList=(ArrayList)pageDtl.get("FORMATCOL");
      if(pageList!=null && pageList.size() >0){
      for(int c=0;c<pageList.size();c++){
      pageDtlMap=(HashMap)pageList.get(c); %>
      <td><div id="CAL_<%=util.nvl((String)pageDtlMap.get("fld_nme"))%>"></div></td>
     <%}}%>
     <!--<td><div id="CAL_<%=prp%>"></div> </td>
     <td><div id="CAL_quot"></div> </td>
     <td><div id="CAL_slback"></div> </td>
     <td><div id="CAL_amt"></div> </td>
     <td><div id="CAL_diff"></div> </td>
     <td><div id="CAL_pl"></div> </td>
     <td><div id="CAL_Rate"></div> </td>
     <td><div id="CAL_RateAmt"></div> </td>
     <td><div id="CAL_RateDisc"></div> </td>-->
    <%}else{%>
    <td><div id="CAL_<%=prp%>"></div> </td>
    <%
    if(prp.equals("LAB_CHARGES")){%>
    <td><div id="CAL_Fnlpri"></div> </td>
    <%}
    }}}%>
    <td></td>
     </tr>
     <tr id="hed">
     <th><label title="Status">Status</label></th>
     <!--<th>Byr</th>
     <th>Sale Ex</th>
     <th>Packet Code</th>
      <th>Packet Date</th>
      <th>Sale Date</th>-->
      
      <%
      itemHdr.add("stt");
      pageList=(ArrayList)pageDtl.get("DISPLAYDF");
      if(pageList!=null && pageList.size() >0){
      for(int j=0;j<pageList.size();j++){
      pageDtlMap=(HashMap)pageList.get(j);
      fld_ttl=(String)pageDtlMap.get("fld_ttl");
      dflt_val=(String)pageDtlMap.get("dflt_val");
      itemHdr.add(dflt_val);
      %>
      <th><%=fld_ttl%></th>
    <%}}
    for(int j=0; j < vwPrpLst.size(); j++ ){
    String prp = (String)vwPrpLst.get(j);
     String hdr = (String)mprp.get(prp);
     String prpDsc = (String)mprp.get(prp+"D");
     String prpTyp = util.nvl((String)mprp.get(prp+"T"));
    if(hdr == null) {
        hdr = prp;
       }
    if(prpDspBlocked.contains(prp)){
    }else{  
    String cursor = "";
    if(prpTyp.equals("N"))
    cursor="class=\"img\"";
    if(!prp.equals("RTE")){
    itemHdr.add(prp);
  %>
<th title="<%=prpDsc%>" <%=cursor%> onclick="displayDiv('<%=prp%>')" >
<%=hdr%>

<%if(prpTyp.equals("N")){
String fprp=prp;
 pageList=(ArrayList)pageDtl.get(prp);
 if(pageList!=null && pageList.size()>0)
        fprp=util.nvl((String)((HashMap)pageList.get(0)).get("dflt_val"));
%>
<div class="hideCalDiv" id="<%=prp%>" style="display:none;">
<div align="left"><input type="radio" name="RD_<%=prp%>" onclick="CalculateAvgTotal('<%=prp%>','<%=defaultstt%>','<%=fprp%>','AVG')" value="AVGNRD" />&nbsp;Average</div>
<%if(prp.indexOf("DIS")==-1){
fprp=prp;
%>
<div align="left"><input type="radio" name="RD_<%=prp%>" onclick="CalculateAvgTotal('<%=prp%>','<%=defaultstt%>','<%=fprp%>','TTL')"  value="TTL" />&nbsp;Total</div>
<%}%>
</div>
<img src="../images/arrow_downact.png" border="0"  />
<%}%>
</th>
    <%if(prp.equals("LAB_CHARGES")){%>
 <th title="Final Price" <%=cursor%> onclick="displayDiv('Fnlpri')" >
Fnlpri
<div class="hideCalDiv" id="Fnlpri" style="display:none;">
<div align="left"><input type="radio" name="RD_Fnlpri" onclick="CalculateAvgTotal('Fnlpri','<%=defaultstt%>','Fnlpri','AVG')" value="AVGNRD" />&nbsp;Average</div>
<div align="left"><input type="radio" name="RD_Fnlpri" onclick="CalculateAvgTotal('Fnlpri','<%=defaultstt%>','Fnlpri','TTL')"  value="TTL" />&nbsp;Total</div>
</div>
<img src="../images/arrow_downact.png" border="0"  />
</th>
    <%itemHdr.add("Fnlpri");
    }
 }else{
      pageList=(ArrayList)pageDtl.get("FORMATCOL");
      if(pageList!=null && pageList.size() >0){
      for(int c=0;c<pageList.size();c++){
      pageDtlMap=(HashMap)pageList.get(c);
      fld_nme=(String)pageDtlMap.get("fld_nme");
      fld_ttl=(String)pageDtlMap.get("fld_ttl");
      val_cond=(String)pageDtlMap.get("val_cond");
      dflt_val=(String)pageDtlMap.get("dflt_val");
        lov_qry=util.nvl((String)pageDtlMap.get("lov_qry"));
      String onclk="displayDiv('"+fld_nme+"')";
      %>
<th title="<%=dflt_val%>"  <%=cursor%> onclick="<%=onclk%>"><%=fld_ttl%>
<div class="hideCalDiv" id="<%=fld_nme%>" style="display:none;">
<div align="left"><input type="radio" name="RD_<%=fld_nme%>" onclick="CalculateAvgTotal('<%=fld_nme%>','<%=defaultstt%>','<%=lov_qry%>','AVG')" value="AVGNRD" />&nbsp;Average</div>
<%if(val_cond.equals("")){%>
<div align="left"><input type="radio" name="RD_<%=fld_nme%>" onclick="CalculateAvgTotal('<%=fld_nme%>','<%=defaultstt%>','<%=lov_qry%>','TTL')"  value="TTL" />&nbsp;Total</div>
<%}%>
</div>
<img border="0" src="../images/arrow_downact.png"></img></th>
     <%itemHdr.add(fld_nme);
     }}%>
     <%}%>
<%}}
itemHdr.add("Remark");%>
<th>Remark</th>
</tr>
</thead>
<tbody>
<%
int colSpan = vwPrpLst.size()+8;
String tableTD="";
for(int z=0;z< grpOrderList.size();z++){
String grpby =(String)grpOrderList.get(z);
ArrayList pkt=(ArrayList)dataDtl.get(grpby);
for (int m = 0; m< pkt.size(); m++) {
HashMap pktData = (HashMap)pkt.get(m);
pktList.add(pktData);
String stkIdn = (String)pktData.get("stk_idn");
String stt = util.nvl((String)pktData.get("stt"));
String vnm = util.nvl((String)pktData.get("vnm"));
String cmp = util.nvl((String)pktData.get("cmp"),"0");
String quot = util.nvl((String)pktData.get("quot"),"0");
String fnlusd = util.nvl((String)pktData.get("Rate"),"0");
String ID=stt+"_"+stkIdn;
String hithistory="hithistory('"+stkIdn+"');";
String hitrowID="HITROW_"+stkIdn;
if(m%2==0){
tableTD="even";
}else{
tableTD="odd";
}
String style = "display:none";
if(stt.equals(defaultstt))
 style="display:''";
 
if(stt.equals("SOLD"))
 style = style+ " ;color:Blue";
 

 
%>
<tr class="<%=tableTD%>"  id="<%=ID%>" style="<%=style%>">

<td><%=stt%></td>
<!--<td><%=util.nvl((String)pktData.get("byr"))%></td>
<td><%=util.nvl((String)pktData.get("emp"))%></td>
<td><%=util.nvl((String)pktData.get("vnm"))%></td>
<td><%=util.nvl((String)pktData.get("pkt_dte"))%></td>
<td><%=util.nvl((String)pktData.get("sl_dte"))%></td>-->
<%pageList=(ArrayList)pageDtl.get("DISPLAYDF");
      if(pageList!=null && pageList.size() >0){
      for(int j=0;j<pageList.size();j++){
      pageDtlMap=(HashMap)pageList.get(j);
      dflt_val=(String)pageDtlMap.get("dflt_val"); %>
      <td><%=util.nvl((String)pktData.get(dflt_val))%></td>
    <%}}
 for(int l=0;l<vwPrpLst.size();l++){
  String tdstyle = "";
   String prp = (String)vwPrpLst.get(l);
    String prpValue =  util.nvl((String) pktData.get(prp));
    if(prpDspBlocked.contains(prp)){
}else{
    if(prpValue.equals("NA"))
     prpValue = "";
    String prpDsc = (String)mprp.get(prp+"D");
    if(prp.equals("RTE")){
    if(cmp.equals(""))
    cmp="0";
    if(fnlusd.equals(""))
    fnlusd="0";
    if(quot.equals(""))
    quot="0";
    float cmpFl = Float.parseFloat(cmp);
    float fnlusdFl = Float.parseFloat(fnlusd);
    float quotFl = Float.parseFloat(quot);
    if(!util.nvl((String)pktData.get("diff")).equals("0") || !util.nvl((String)pktData.get("plSalrteper")).equals("0")){
    if(comparewith.equals("cmp")){
    if(cmpFl > fnlusdFl){
     tdstyle = "background-color:red;color:white";
    }else if(cmpFl < fnlusdFl){
     tdstyle = "background-color:Green;color:white";
    }
    }else{
    String trfcmp =  util.nvl((String) pktData.get(comparewith));
    if(trfcmp.equals("") || trfcmp.equals("NA"))
    trfcmp="0";
    float trfcmpFl = Float.parseFloat(trfcmp);
    if(trfcmpFl > fnlusdFl){
     tdstyle = "background-color:red;color:white";
    }else if(trfcmpFl < fnlusdFl){
     tdstyle = "background-color:Green;color:white";
    }
    }
   }
    
    prpValue = String.valueOf(cmpFl);
      pageList=(ArrayList)pageDtl.get("FORMATCOL");
      if(pageList!=null && pageList.size() >0){
      for(int d=0;d<pageList.size();d++){
      pageDtlMap=(HashMap)pageList.get(d);
      fld_nme=(String)pageDtlMap.get("fld_nme");
      fld_ttl=(String)pageDtlMap.get("fld_ttl");
      if(fld_nme.equals("RTE")){%>
      <td  style="<%=tdstyle%>"  title="<%=prpDsc%>" ><%=prpValue%></td>
     <%}else if(fld_nme.equals("quot")){%>
     <td title="<%=fld_ttl%>"><%=quotFl%></td>
     <%}else{%>
      <td title="<%=fld_ttl%>"><%=util.nvl((String)pktData.get(fld_nme))%></td>
     <%}
     }}%>
    <!--<td  style="<%=tdstyle%>"  title="<%=prpDsc%>" ><%=prpValue%></td>
     <td title="sale rate"><%=quotFl%></td>
     <td title="sale Back"><%=util.nvl((String)pktData.get("slback"))%></td>
     <td title="Sale Amount"><%=util.nvl((String)pktData.get("amt"))%></td>
    <td  title="Diff"><%=util.nvl((String)pktData.get("diff"))%></td>
    <td  title="Profit Loss"><%=util.nvl((String)pktData.get("pl"))%></td>
    <td  title="Rate"><%=util.nvl((String)pktData.get("Rate"))%></td>
    <td  title="RateAmt"><%=util.nvl((String)pktData.get("RateAmt"))%></td>
    <td  title="RateDisc"><%=util.nvl((String)pktData.get("RateDisc"))%></td>-->
   
   <%}else{
 %>
<td  style="<%=tdstyle%>"  title="<%=prpDsc%>" ><%=prpValue%>
<%if(prp.equals("HIT") && !prpValue.equals("")){%>
<img src="../images/plus.png" border="0" onclick="<%=hithistory%>"/>
<%}%>
</td>
<%if(prp.equals("LAB_CHARGES")){%>
<td  style="<%=tdstyle%>"  title="Final Price" ><%=util.nvl((String) pktData.get("Fnlpri"))%></td>
<%}}}}%>       
<td><%=util.nvl((String)pktData.get("Remark"))%></td>
</tr>
<tr id="<%=hitrowID%>"   style="display:none">
<td colspan="10" align="left">
<span id="HIT_<%=stkIdn%>" style="width:500px" > </span>
</td>
</tr>
<%}
if(grpOrderList.size()>1){
pkt=new ArrayList();
pkt=(ArrayList)dataDtl.get(grpby+"_TTL");
for (int m = 0; m< pkt.size(); m++) {
HashMap pktData = (HashMap)pkt.get(m);
pktList.add(pktData);
String stt = util.nvl((String)pktData.get("stt"));
String cmp = util.nvl((String)pktData.get("cmp"),"0");
String quot = util.nvl((String)pktData.get("quot"),"0");
String ID=stt+"_"+grpby;
if(m%2==0){
tableTD="even";
}else{
tableTD="odd";
}
String style = "display:none";
if(stt.equals(defaultstt))
 style="display:''";
 
if(stt.equals("SOLD"))
 style = style+ " ;color:Blue";
 

 
%>
<tr class="<%=tableTD%>"  id="<%=ID%>" style="<%=style%>">

<td></td>
<%pageList=(ArrayList)pageDtl.get("DISPLAYDF");
      if(pageList!=null && pageList.size() >0){
      for(int j=0;j<pageList.size();j++){
      pageDtlMap=(HashMap)pageList.get(j);
      dflt_val=(String)pageDtlMap.get("dflt_val"); %>
      <td></td>
    <%}}
 for(int l=0;l<vwPrpLst.size();l++){
  String tdstyle = "";
   String prp = (String)vwPrpLst.get(l);
    String prpValue =  util.nvl((String) pktData.get(prp));
    if(prpDspBlocked.contains(prp)){
}else{
    if(prpValue.equals("NA"))
     prpValue = "";
    String prpDsc = (String)mprp.get(prp+"D");
    if(prp.equals("RTE")){
    if(cmp.equals(""))
    cmp="0";
    if(quot.equals(""))
    quot="0";
    float cmpFl = Float.parseFloat(cmp);
    float quotFl = Float.parseFloat(quot);
    if(!util.nvl((String)pktData.get("diff")).equals("0")){
    if(comparewith.equals("cmp")){
    if(cmpFl > quotFl){
     tdstyle = "";
    }else if(cmpFl < quotFl){
     tdstyle = "";
    }
    }else{
    String trfcmp =  util.nvl((String) pktData.get(comparewith));
    if(trfcmp.equals("") || trfcmp.equals("NA"))
    trfcmp="0";
    float trfcmpFl = Float.parseFloat(trfcmp);
    if(trfcmpFl > quotFl){
     tdstyle = "background-color:red;color:white";
    }else if(trfcmpFl < quotFl){
     tdstyle = "background-color:Green;color:white";
    }
    }
   }
    
    prpValue = String.valueOf(cmpFl);
      pageList=(ArrayList)pageDtl.get("FORMATCOL");
      if(pageList!=null && pageList.size() >0){
      for(int d=0;d<pageList.size();d++){
      pageDtlMap=(HashMap)pageList.get(d);
      fld_nme=(String)pageDtlMap.get("fld_nme");
      fld_ttl=(String)pageDtlMap.get("fld_ttl");
      if(fld_nme.equals("RTE")){%>
      <td  style="<%=tdstyle%>"  title="<%=prpDsc%>" ></td>
     <%}else if(fld_nme.equals("quot")){%>
     <td title="<%=fld_ttl%>"><%=quotFl%></td>
     <%}else{%>
      <td title="<%=fld_ttl%>"><%=util.nvl((String)pktData.get(fld_nme))%></td>
     <%}
     }}%>
    <!--<td  style="<%=tdstyle%>"  title="<%=prpDsc%>" ></td>
     <td title="Net rate"><%=quotFl%></td>
     <td title="Net Back"><%=util.nvl((String)pktData.get("slback"))%></td>
     <td title="Net Amount"><%=util.nvl((String)pktData.get("amt"))%></td>
    <td  title="Diff"><%=util.nvl((String)pktData.get("diff"))%></td>
    <td  title="Profit Loss"><%=util.nvl((String)pktData.get("pl"))%></td>
    <td  title="Sale Rte"><%=util.nvl((String)pktData.get("Rate"))%></td>
    <td  title="Sale Amt"><%=util.nvl((String)pktData.get("RateAmt"))%></td>
    <td  title="Sale Disc"><%=util.nvl((String)pktData.get("RateDisc"))%></td>-->
   
   <% }else{
 %>
<td  style="<%=tdstyle%>"  title="<%=prpDsc%>" ><%=prpValue%></td>
<%if(prp.equals("LAB_CHARGES")){%>
<td  style="<%=tdstyle%>"  title="Final Price" ><%=util.nvl((String) pktData.get("Fnlpri"))%></td>
<%}}}}%>       
<td><%=util.nvl((String)pktData.get("Remark"))%></td>
</tr>

<%}}%>
<%}%>
</tbody>
</table>
</td></tr></table>
   <% 
  session.setAttribute("itemHdr", itemHdr);
  session.setAttribute("pktList", pktList);
   }else{%>
   Sorry no result found
   <%}%>
   </td></tr>
   
  
  </table>
  </body>
</html>