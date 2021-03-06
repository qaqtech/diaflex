<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page errorPage="../error_page.jsp" %>
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.ResultSet, java.util.ArrayList,java.util.Collections,java.util.ArrayList,java.util.HashMap,java.text.SimpleDateFormat, java.util.Calendar"%>
<jsp:useBean id="info" class="ft.com.InfoMgr" scope="session" />
<jsp:useBean id="util" class="ft.com.JspUtil" scope="page" />
<%@taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
 

<html>
   <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/bse.js?v=<%=info.getJsVersion()%> " > </script>
<link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/bse.css?v=<%=info.getJsVersion()%>"/>
<link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/general.css?v=<%=info.getJsVersion()%>"/>
<script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/ajax.js?v=<%=info.getJsVersion()%> " > </script>
  <script type="text/javascript" src="<%=info.getReqUrl()%>/PopScripts/popup.js?v=<%=info.getJsVersion()%> " > </script>
    <script src="<%=info.getReqUrl()%>/PopScripts/jquery-1.2.6.min.js" type="text/javascript"></script>
       <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/memoScript.js?v=<%=info.getJsVersion()%> " > </script>
   <script language="javaScript" type="text/javascript" src="<%=info.getReqUrl()%>/calendar/calendar.js"></script>
   <link href="<%=info.getReqUrl()%>/calendar/calendar.css" rel="stylesheet" type="text/css" />

 
      <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/style.css?v=<%=info.getJsVersion()%>"/>

  <title>Daily Purchase Report</title>
 
  </head>
        <%
    HashMap allPageDtl= (info.getPageDetails() == null)?new HashMap():(HashMap)info.getPageDetails();
    HashMap pageDtl=(HashMap)allPageDtl.get("DAILY_PUR");
    ArrayList pageList=new ArrayList();
    HashMap pageDtlMap=new HashMap();
    String fld_nme="",fld_ttl="",val_cond="",dflt_val="",fld_typ="",form_nme="",flg1="";
        String logId=String.valueOf(info.getLogId());
        String onfocus="cook('"+logId+"')";
        boolean isTerms=false;
        pageList=(ArrayList)pageDtl.get("TERMS");
      if(pageList!=null && pageList.size() >0)
         isTerms=true;
         
         
         String dateFilter="Y";
     pageList=(ArrayList)pageDtl.get("DATEFILTER");
     if(pageList!=null && pageList.size() >0){
         for(int k=0;k<pageList.size();k++){
         pageDtlMap=(HashMap)pageList.get(k);
         dflt_val = util.nvl((String)pageDtlMap.get("dflt_val"));
         ArrayList roleList = info.getRoleLst();
          String usrFlg=util.nvl((String)info.getUsrFlg());
         if(roleList==null)
         roleList=new ArrayList();
         if(roleList.indexOf(dflt_val)==-1)
           dateFilter="N";
          if(usrFlg.equals("SYS"))
             dateFilter="Y";
     }}
              String samepkt="N";
     pageList=(ArrayList)pageDtl.get("SAMEPKT");
     if(pageList!=null && pageList.size() >0){
         for(int k=0;k<pageList.size();k++){
         samepkt="Y";
     }}
     int loop=0;
        %>
 <body onfocus="<%=onfocus%>" onkeypress="return disableEnterKey(event);" >
     <%
       
       HashMap dbinfo = info.getDmbsInfoLst();
       String cnt =(String)dbinfo.get("CNT");
     %>
   <table cellpadding="" cellspacing="" width="80%" class="mainbg">
   <tr>
    <td height="103" valign="top">
   <jsp:include page="/header.jsp" />
    
    </td>
  </tr>
    <tr>
    <td height="5" valign="top" class="greyline"><img src="../images/grey_linebig.png" width="685" height="5" align="right" /></td>
  </tr>
   <tr><td valign="top" class="hedPg"  >
  <table cellpadding="1" cellspacing="5"><tr><td valign="middle">
  <img src="../images/line.jpg" border="0" /></td><td valign="middle"> <span class="pgHed">
Daily Purchase Report
 
  </span> </td>
</tr></table>
  </td>
  </tr>
   <tr>
  <td valign="top" class="hedPg">
    <html:form action="report/dailyPurReport.do?method=load" method="post" >
    <table><tr>
    <%if(dateFilter.equals("Y")){%>
    <td>Date From : </td><td>
    <html:text property="value(dtefr)" styleClass="txtStyle"  styleId="dtefr"  size="12"  /> <a href="#" style="margin-top:5px;"  onClick="setYears(1900, '<%=info.getCurrentYear()%>' );showCalender(this, 'dtefr');">
                <img src="<%=info.getReqUrl()%>/images/calender.png"></a></td>
                <td>Date To : </td><td>
    <html:text property="value(dteto)" styleClass="txtStyle"  styleId="dteto"  size="12"  /> <a href="#"  onClick="setYears(1900, '<%=info.getCurrentYear()%>' );showCalender(this, 'dteto');">
                <img src="<%=info.getReqUrl()%>/images/calender.png"></a></td>
                <%}%>
         <%pageList=(ArrayList)pageDtl.get("EMPLOYEE");
      if(pageList!=null && pageList.size() >0){
      for(int j=0;j<pageList.size();j++){
      pageDtlMap=(HashMap)pageList.get(j);
       dflt_val = util.nvl((String)pageDtlMap.get("dflt_val"));
       fld_ttl = util.nvl((String)pageDtlMap.get("fld_ttl"));
       if(dflt_val.equals("Y")){%>
      <td nowrap="nowrap"><span class="txtBold" >Sale Person : </span>
      <%
      ArrayList salepersonList = ((ArrayList)info.getSaleperson() == null)?new ArrayList():(ArrayList)info.getSaleperson();
      %>
      <html:select name="dailyPurReportForm" property="value(styp)" styleId="saleEmp">
      <html:option value="0">---Select---</html:option>
      <%
      for(int i=0;i<salepersonList.size();i++)
      {
      ArrayList saleperson=(ArrayList)salepersonList.get(i);
      %>
      <html:option value="<%=(String)saleperson.get(0)%>"> <%=(String)saleperson.get(1)%> </html:option>
      <%
      }
      %>
      </html:select>
      
      </td>
      <%}}}%>
      <%pageList=(ArrayList)pageDtl.get("LOCATION");
      if(pageList!=null && pageList.size() >0){
      HashMap prp = info.getPrp();
      ArrayList locVal = (ArrayList)prp.get("LOCV");
      %>
      <td>Location : &nbsp;&nbsp;
       <html:select name="dailyPurReportForm" property="value(loc)" styleId="loc">
       <html:option value="">--Select-- </html:option>
        <%
      for(int i=0;i<locVal.size();i++)
      {
      
      %>
      <html:option value="<%=(String)locVal.get(i)%>"> <%=(String)locVal.get(i)%> </html:option>
      <%
      }
      %>
       
       </html:select></td>
      <%}%>
      <td>Vendor</td>
      <td nowrap="nowrap">
    <input type="text" id="nme" name="nme" autocomplete="off" class="sugBox"
      onKeyUp="doCompletion('nme', 'nmePop', '../ajaxAction.do?1=1&UsrTyp=VENDOR,BUYER', event)" 
      onKeyDown="setDown('nmePop', 'nmeID', 'nme',event)"/>
 <img src="../images/addrow.jpg" width="17" height="17" title="Click To get Names" onclick="doCompletionMobile('nme', 'nmePop', '../ajaxAction.do?1=1&UsrTyp=VENDOR,BUYER')">
 <input type="hidden" name="nmeID" id="nmeID" value="">
  <div style="position: absolute;">
      <select id="nmePop" name="nmePop"
        style="display:none;300px;" 
        class="sugBoxDiv"
        onKeyDown="setDown('nmePop', 'nmeID', 'nme',event)" 
        onDblClick="setVal('nmePop', 'nmeID', 'nme', event);hideObj('nmePop')" 
        onClick="setVal('nmePop', 'nmeID', 'nme', event);" 
        size="10">
      </select>
</div>
  </td>
           <td nowrap="nowrap">Pkt Type</td>
           <td nowrap="nowrap">
             <html:select property="value(pkt_ty)" styleId="pkt_ty">
             <html:option value="" >--select--</html:option>
             <html:option value="S">Single</html:option>
             <html:option value="M">Mix</html:option>
            </html:select>
            </td>
       <td><html:submit property="value(submit)" value="Fetch" styleClass="submit"/>
       </td>
                </tr></table>
  </html:form>
  </td></tr>
  <%

  String view = util.nvl((String)request.getAttribute("view"));
  if(view.equals("Y")){
  ArrayList  venderidnList = (ArrayList)request.getAttribute("venderidnList");
  HashMap  venderDtl = (HashMap)request.getAttribute("venderDtl");
  HashMap  dataDtl = (HashMap)request.getAttribute("dataDtl");
   ArrayList colNmeLst=(ArrayList)request.getAttribute("colNmeLst");
      ArrayList colTtlList=(ArrayList) request.getAttribute("colTtlList");
  if(venderidnList!=null && venderidnList.size()>0){
   ArrayList itemHdr = new ArrayList();
   ArrayList pktList = new ArrayList();
   ArrayList vwPrpLst = (ArrayList)session.getAttribute("DailyPurViewLst");
   HashMap mprp = info.getMprp();
   HashMap totals = (HashMap)request.getAttribute("totalMap");
   ArrayList prpDspBlocked = info.getPageblockList();
   itemHdr.add("Vendor");
   itemHdr.add("pur_idn");
      itemHdr.add("purdte");
      itemHdr.add("vnm");
      itemHdr.add("qty");
      itemHdr.add("cts");

      int sr=1;
      int colspan=vwPrpLst.size();%>
<tr>
<td class="tdLayout" valign="top"><table><tr>
    <%
    pageList=(ArrayList)pageDtl.get("EXCEL");
            if(pageList!=null && pageList.size() >0){
            for(int j=0;j<pageList.size();j++){%>
            <td>Create Excel <img src="../images/ico_file_excel.png" onclick="goTo('<%=info.getReqUrl()%>/report/dailyPurReport.do?method=createXL','','')" /></td>
            <%}}%>
                <%
            pageList=(ArrayList)pageDtl.get("CUSTOM_EXCEL");
            if(pageList!=null && pageList.size() >0){
            for(int j=0;j<pageList.size();j++){%>
            <td>Purchase Report <img src="../images/ico_file_excel.png" onclick="goTo('<%=info.getReqUrl()%>/report/dailyPurReport.do?method=createPurchaseReportXL','','')" /></td>
            <%}}%>
  <%ArrayList rolenmLst=(ArrayList)info.getRoleLst();
  String usrFlg=util.nvl((String)info.getUsrFlg());
  if(usrFlg.equals("SYS") || rolenmLst.contains("ADMIN")){
  %>
  <td><img src="../images/edit.jpg" title="Edit Properties Module" onclick="newWindow('<%=info.getReqUrl()%>/dynamicProperty.do?method=load&mdl=DAILYPUR_VW&sname=DailyPurViewLst&par=A')" border="0" width="15px" height="15px"/> </td>
  <%}%>
</tr></table>
</td>
</tr>
  <tr>
  <td class="tdLayout" valign="top">
<table class="grid1" align="left" id="dataTable">
<tr>
<th><label title="SR NO">Sr No.</label></th>
<th><label title="Vendor">Vendor</label></th>
<th><label title="Vendor">Pur Idn</label></th>
<th><label title="Vendor">Pur Date</label></th>
<%if(isTerms){%>
<th>Terms</th>
<%}%>
<th>Ref No</th>
<th>Qty</th>
<th>Cts</th>
<%for(int j=0; j < vwPrpLst.size(); j++ ){
    String prp = (String)vwPrpLst.get(j);
    if(prpDspBlocked.contains(prp)){
    colspan=colspan-1;
    }else{
     String hdr = (String)mprp.get(prp);
    String prpDsc = (String)mprp.get(prp+"D");
    if(hdr == null) {
        hdr = prp;
       }
     itemHdr.add(prp);%>
<th title="<%=prpDsc%>"><%=hdr%></th>
<%
if(prp.equals("CP_DIS") && cnt.equalsIgnoreCase("nj")){
     itemHdr.add("purdis");
     colspan=colspan+1;
     %>
     <th title="Pur Discount">Pur Dis</th>
<%}%>
     
<%}
if(prp.equals("NET_PUR_RTE")){
itemHdr.add("netAmt");
itemHdr.add("netvluInr");
itemHdr.add("addComm");
colspan=colspan+3;
%>
<th title="Net Amount" >Net Amt</th>
<th title="Net Amount" >Net Amt(INR)</th>
<th>Aadat comm</th>
<%}}%>
<%
itemHdr.add("rte");itemHdr.add("dis");itemHdr.add("exh_rte");itemHdr.add("exh_vlu");itemHdr.add("vlu");
%>
     <th title="Rate">Rate</th>
     <th title="Dis">Dis</th>
     <th title="Exchange Rte">Exchange Rte</th>
     <th title="Exchange Vlu">Vlu As per Exh_Rte</th>
     <th title="Vlu">Value</th>
     <%if(colTtlList!=null && colTtlList.size()>0){
     for (int i = 0; i< colTtlList.size(); i++) {
  String colttl=(String)colTtlList.get(i);
  String colnme=(String)colNmeLst.get(i);
  itemHdr.add(colnme);
     %>
     <th title="<%=colttl%>"><%=colttl%></th>
     <%}}%>
</tr>
<%

for (int i = 0; i< venderidnList.size(); i++) {
  String vndr_idn=(String)venderidnList.get(i);
  ArrayList pkt=(ArrayList)dataDtl.get(vndr_idn);
  for (int k = 0; k< pkt.size(); k++) {
HashMap pktDtl = (HashMap)pkt.get(k);
pktList.add(pktDtl);
String stkIdn = (String)pktDtl.get("stk_idn");
String cts = (String)pktDtl.get("cts");
%>
<tr>

<td><%=sr++%></td>
  <%if(k==0){
  %>
  <td nowrap="nowrap">
   <%=util.nvl((String)pktDtl.get("Vendor"))%>
   <%if(cnt.equalsIgnoreCase("cd")){%>
  <img src="../images/ico_file_excel.png" title="Purchase Order" onclick="goTo('<%=info.getReqUrl()%>/report/dailyPurReport.do?method=createPurchaseOrderXL&purId=<%=util.nvl((String)pktDtl.get("pur_idn"))%>','','')" />
  <img src="../images/ico_file_excel.png" title="Purchase Report" onclick="goTo('<%=info.getReqUrl()%>/report/dailyPurReport.do?method=createPurchaseReportXL&purId=<%=util.nvl((String)pktDtl.get("pur_idn"))%>','','')" />
  <%}%>
  </td>
  <%}else{%>
  <td></td>
  <%}%>
      <td><%=util.nvl((String)pktDtl.get("pur_idn"))%></td>
    <td nowrap="nowrap"><%=util.nvl((String)pktDtl.get("purdte"))%></td>
    <%if(isTerms){%>
     <td nowrap="nowrap"><%=util.nvl((String)pktDtl.get("terms"))%></td>
     <%}%>
<td nowrap="nowrap"><%=util.nvl((String)pktDtl.get("vnm"))%>
<%if(samepkt.equals("Y")){%>
<a onclick="callSimilarPurchase('<%=stkIdn%>','<%=loop%>')" title="Click Here To See Details" style="text-decoration:underline">Similar</a>
<%}%>
</td>
<td align="right" nowrap="nowrap"><%=util.nvl((String)pktDtl.get("qty"))%></td>
<td align="right" nowrap="nowrap"><%=util.nvl((String)pktDtl.get("cts"))%></td>
<%for(int j=0; j < vwPrpLst.size(); j++ ){
    String prp = (String)vwPrpLst.get(j);
    if(!prpDspBlocked.contains(prp)){  %>
<td nowrap="nowrap"><%=util.nvl((String)pktDtl.get(prp))%></td>
<%
if(prp.equals("CP_DIS") && cnt.equalsIgnoreCase("nj")){
    
     %>
   <td nowrap="nowrap"><%=util.nvl((String)pktDtl.get("purdis"))%></td>
<%}%>
<%}
 if(prp.equals("NET_PUR_RTE")){%>
 <td nowrap="nowrap"><%=util.nvl((String)pktDtl.get("netAmt"))%></td>  
 <td nowrap="nowrap"><%=util.nvl((String)pktDtl.get("netvluInr"))%></td>  
 <td align="right" nowrap="nowrap"><%=util.nvl((String)pktDtl.get("addComm"))%></td>
<%}%>

<%}%>
<td align="right" nowrap="nowrap"><%=util.nvl((String)pktDtl.get("rte"))%></td>
<td align="right" nowrap="nowrap"><%=util.nvl((String)pktDtl.get("dis"))%></td>
<td align="right" nowrap="nowrap"><%=util.nvl((String)pktDtl.get("exh_rte"))%></td>
<td align="right" nowrap="nowrap"><%=util.nvl((String)pktDtl.get("exh_vlu"))%></td>
<td align="right" nowrap="nowrap"><%=util.nvl((String)pktDtl.get("vlu"))%></td>
<%for (int j = 0; j< colNmeLst.size(); j++) {
  String colnme=(String)colNmeLst.get(j);%>
  <td align="right" nowrap="nowrap"><%=util.nvl((String)pktDtl.get(colnme))%></td>
  <%}%>

</tr>
                    <tr id="BYRTRDIV_<%=loop%>" style="display:none">
                      <td colspan="18"> 
                      <div id="BYR_<%=loop%>"  align="center">
                      </div>
                      </td>
                 </tr>
<%  loop++;}%>

  <%

  HashMap ttl=(HashMap)dataDtl.get(vndr_idn+"_TTL");
  pktList.add(ttl);
  %>
  <tr>
  <td colspan="5"></td>
  <td align="right" nowrap="nowrap"><b><%=util.nvl((String)ttl.get("qty"))%></b></td>
  <td align="right" nowrap="nowrap"><b><%=util.nvl((String)ttl.get("cts"))%></b></td>
  <%for(int j=0; j < vwPrpLst.size(); j++ ){
      String prp = util.nvl((String)vwPrpLst.get(j));
      if(prp.equals("CP_VLU")){%>
       <td><b><%=util.nvl((String)ttl.get("cpVlu"))%></b></td>
    <%  }else{
  %>
    <td>&nbsp;</td>
  <%}}%>
  <td colspan="3">&nbsp;</td>
  <td align="right" nowrap="nowrap"><b><%=util.nvl((String)ttl.get("exh_vlu"))%></b></td>
  <td align="right" nowrap="nowrap"><b><%=util.nvl((String)ttl.get("vlu"))%></b></td>
  </tr>
<%}%>
                 
<%
  HashMap grandttl=(HashMap)dataDtl.get("GRANDTTL");
  pktList.add(grandttl);
  session.setAttribute("pktList", pktList);
  session.setAttribute("itemHdr", itemHdr);%>
  <tr>
  <tr>
  <td nowrap="nowrap"  colspan="5" align="left"><b>Grand Total</b><input type="hidden" id="count" value="<%=loop%>" /></td>
  <td align="right" nowrap="nowrap"><b><%=util.nvl((String)grandttl.get("qty"))%></b></td>
  <td align="right" nowrap="nowrap"><b><%=util.nvl((String)grandttl.get("cts"))%></b></td>
  <%for(int j=0; j < vwPrpLst.size(); j++ ){
      String prp = util.nvl((String)vwPrpLst.get(j));
      if(prp.equals("CP_VLU")){%>
       <td><b><%=util.nvl((String)grandttl.get("cpVlu"))%></b></td>
    <%  }else{
  %>
    <td>&nbsp;</td>
  <%}}%>
  <td colspan="3">&nbsp;</td>
  <td align="right" nowrap="nowrap"><b><%=util.nvl((String)grandttl.get("exh_vlu"))%></b></td>
  <td align="right" nowrap="nowrap"><b><%=util.nvl((String)grandttl.get("vlu"))%></b></td>
  </tr>
</table>
</td>
  </tr>
  <%}else{%>
  <tr><td class="tdLayout" valign="top">Sorry no result found </td></tr>
  <%}}%>
   <tr>
    <td><jsp:include page="/footer.jsp" /> </td>
    </tr>
  </table>
  <%@include file="/calendar.jsp"%>
  </body>
</html>