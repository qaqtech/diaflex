<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page errorPage="../error_page.jsp" %>
<%@ page contentType="text/html;charset=windows-1252"%>
<jsp:useBean id="info" class="ft.com.InfoMgr" scope="session" />
<jsp:useBean id="util" class="ft.com.JspUtil" scope="page" />
<jsp:useBean id="gtMgr" class="ft.com.GtMgr" scope="session" />

<%@ page import="java.util.ArrayList,java.util.LinkedHashMap,java.util.HashMap,java.util.Set,java.sql.ResultSet,ft.com.*, java.util.Date, java.util.ArrayList, ft.com.dao.*"%>
<%@taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>

<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>Assort Return</title>
    <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/bse.css?v=<%=info.getJsVersion()%>"/>
    <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/general.css?v=<%=info.getJsVersion()%>"/>
     <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/style.css?v=<%=info.getJsVersion()%>"/>
     <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/bse.js?v=<%=info.getJsVersion()%> " > </script>
      <script type="text/javascript" src="<%=info.getReqUrl()%>/PopScripts/popup.js?v=<%=info.getJsVersion()%> " > </script>
     <script src="<%=info.getReqUrl()%>/PopScripts/jquery-1.2.6.min.js" type="text/javascript"></script>
          <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/newCalScript.js?v=<%=info.getJsVersion()%> " > </script>
<script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/assortScript.js?v=<%=info.getJsVersion()%> " > </script>
         <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/ajax.js?v=<%=info.getJsVersion()%> " > </script>
        <script src="<%=info.getReqUrl()%>/jqueryScript/jqueryScript.js?v=<%=info.getJsVersion()%>" type="text/javascript"></script>
              <script src="<%=info.getReqUrl()%>/jqueryScript/jquery.min.js" type="text/javascript"></script>
   
  </head>
        <%String logId=String.valueOf(info.getLogId());
        String onfocus="cook('"+logId+"')";
       String lstNme = (String)gtMgr.getValue("lstNmeRGHRTN");
       HashMap stockListMap = new HashMap();
         stockListMap = (HashMap)gtMgr.getValue(lstNme);
        String cnt = util.nvl((String)info.getDmbsInfoLst().get("CNT"));  
        String form = util.nvl(request.getParameter("form"),"MIX");
        String dfPg = "MIX_SPLIT";
        if(form.equals("RGH"))
        dfPg = "RGH_SPLIT";
           HashMap allPageDtl= (info.getPageDetails() == null)?new HashMap():(HashMap)info.getPageDetails();
            HashMap pageDtl=(HashMap)allPageDtl.get(dfPg);
    ArrayList pageList=new ArrayList();
    HashMap pageDtlMap=new HashMap();
    String fld_nme="",fld_ttl="",val_cond="",dflt_val="",fld_typ="",form_nme="",flg1="";%>
 <body onfocus="<%=onfocus%>" onload="MM_preloadImages('images/faunalogo.png')" topmargin="0" leftmargin="0" rightmargin="0" bottommargin="0">


<div id="backgroundPopup"></div>
<div id="popupContactASSFNL">
<table class="popUpTb">
 <tr><td height="5%">&nbsp;&nbsp;&nbsp;<html:button property="value(close)" styleClass="submit"  onclick="MKEmptyPopup('SC');disablePopupASSFNL()" value="Close"  /> </td></tr>
<tr><td valign="top" height="95%">
<iframe name="SC" class="frameStyle"   align="middle" frameborder="0">

</iframe></td></tr>
</table>
</div>
<input type="hidden" id="reqUrl" name="reqUrl" value="<%=info.getReqUrl()%>"/>
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
  <img src="../images/line.jpg" border="0" /></td><td valign="middle"> <span class="pgHed">Assort Return</span> </td>
</tr></table>
  </td>
  </tr>
   <tr>
  <td valign="top" class="hedPg">
<html:form action="rough/roughReturnAction.do?method=fecth" method="post" onsubmit="return validate_Assortreturn()">
   <html:hidden property="value(grpList)" name="splitReturnForm" />
     <html:hidden property="value(mdl)" name="splitReturnForm" />
     <input type="hidden" name="form" id="form" value="<%=form%>" />
   <table><tr><td>Process : </td>
   <td>
   <html:select property="value(mprcIdn)"  styleId="mprcIdn" onchange="getPrcIssueIdn(this,'issueId')" name="splitReturnForm"   >
           <html:option value="0" >--select--</html:option>
            <html:optionsCollection property="mprcList" name="splitReturnForm" 
            label="prc" value="mprcId" />
    </html:select>
   </td></tr>
     <%
      pageList= ((ArrayList)pageDtl.get("BULKSPLIT") == null)?new ArrayList():(ArrayList)pageDtl.get("BULKSPLIT");
      if(pageList!=null && pageList.size() >0){
      for(int j=0;j<pageList.size();j++){
      pageDtlMap=(HashMap)pageList.get(j);
      fld_nme=(String)pageDtlMap.get("fld_nme");
      if(fld_nme.equals("Y")){
      %>
    <tr>
   <td>Issue Id:</td><td> 
   <html:select property="value(issueId)"  styleId="issueId"  name="splitReturnForm"   >
           <html:option value="">--select--</html:option>
           
    </html:select>
   
   </td>
   </tr><%}}}else{%>
   <tr>
   <td>Issue Id:</td><td> <html:text property="value(issueId)" styleId="issueId"  name="splitReturnForm"  /></td>
   </tr>
   <%}%>
   <tr>
    <td>
    Employee : </td>
   <td>
   <html:select property="value(empIdn)" styleId="empIdn" name="splitReturnForm"   >
           <html:option value="0" >--select--</html:option>
            <html:optionsCollection property="empList" name="splitReturnForm" 
            label="emp_nme" value="emp_idn" />
   </html:select>
   </td>
   </tr>
   
   <%if(cnt.equalsIgnoreCase("re")){%>
   <tr>
   <td>Lot No:</td><td> <html:text property="value(lotNo)"  name="splitReturnForm"  /></td>
   </tr>
   <tr>
   <td>Atr Lot No:</td><td> <html:text property="value(AtrlotNo)"  name="splitReturnForm"  /></td>
   </tr>
    
    <%}%>
   <tr>
   <td colspan="2">  <html:submit property="value(srch)" value="Fetch" styleClass="submit" /></td>
   </tr></table>
   </html:form>
   </td></tr>
   <%
   ArrayList msgLst = (ArrayList)request.getAttribute("msgList");
   if(msgLst!=null){
   for(int i=0;i<msgLst.size();i++){
   String msg = (String)msgLst.get(i);
   %>
    <tr><td valign="top" class="tdLayout"><span class="redLabel"><%=msg%></span></td></tr>
   <%}}%>
    
   <%
      String view = (String)request.getAttribute("view");
   if(view!=null){
   ArrayList prpDspBlocked = info.getPageblockList();
   if(stockListMap!=null && stockListMap.size()>0){
   String grp= (String)request.getAttribute("grp");
    String mdl= util.nvl((String)request.getAttribute("mdl"));
   String IsStt = (String)request.getAttribute("IsStt");
    HashMap pktCountMap = (HashMap)request.getAttribute("pktCountMap");
     ArrayList vwPrpLst = (ArrayList)session.getAttribute(mdl);
      String issueId = util.nvl((String)request.getAttribute("issueId"));
    HashMap totals = (HashMap)gtMgr.getValue(lstNme+"_TTL");
 HashMap planDtl = (HashMap)gtMgr.getValue(lstNme+"_PLN");
    HashMap mprp = info.getMprp();
   ArrayList rtnEditPrp = (ArrayList)gtMgr.getValue("AssortRtnEditPRP");
HashMap prpLst = info.getPrp();
 int colSpan =vwPrpLst.size()+10;
    %>
     <tr><td valign="top" class="tdLayout">
<table>
   <tr><td>Total :&nbsp;&nbsp;</td><td><span id="ttlqty"> <%=totals.get("MQ")%>&nbsp;&nbsp;</span></td><td>cts&nbsp;&nbsp;</td><td><span id="ttlcts"><%=totals.get("MW")%>&nbsp;&nbsp;</span></td><td>Selected:&nbsp;&nbsp;</td><td> Total :&nbsp;&nbsp;</td> <td><span id="qtyTtl">0&nbsp;&nbsp;</span></td><td>Cts&nbsp;&nbsp;</td><td><span id="ctsTtl">0</span> </td> </tr>
  
   </table>
     </td></tr>
    <html:form action="rough/roughReturnAction.do?method=Return" method="post" onsubmit="return validate_issue('CHK_' , 'count')" >
 
  <tr><td valign="top" height="30px" class="tdLayout">
      <input type="hidden" name="form" id="form" value="<%=form%>" />
  <html:submit property="value(return)" value="Return" styleClass="submit" />
    <%
    
      pageList= ((ArrayList)pageDtl.get("BULKSPLIT") == null)?new ArrayList():(ArrayList)pageDtl.get("BULKSPLIT");
      if(pageList!=null && pageList.size() >0){
      for(int j=0;j<pageList.size();j++){
      pageDtlMap=(HashMap)pageList.get(j);
      fld_nme=(String)pageDtlMap.get("fld_nme");
      if(fld_nme.equals("Y") && !issueId.equals("")){
      String url="roughReturnAction.do?method=bulkSplit&issID="+issueId+"&stt="+IsStt+"&mdl="+mdl+"&form="+form;
      String openPop= "openPopUpDirect('"+url+"' ,'SC','Split')";
      %>
        <html:button property="value(bulkSplit)" value="Bulk Split" styleId="Split" onclick="<%=openPop%>" styleClass="submit" />
        
    <% }}}%>
    
     <%
      pageList= ((ArrayList)pageDtl.get("SPLITEXIT") == null)?new ArrayList():(ArrayList)pageDtl.get("SPLITEXIT");
      if(pageList!=null && pageList.size() >0){
      for(int j=0;j<pageList.size();j++){
      pageDtlMap=(HashMap)pageList.get(j);
      fld_nme=(String)pageDtlMap.get("fld_nme");
      fld_ttl=(String)pageDtlMap.get("fld_ttl");
      if(fld_nme.equals("Y") && !issueId.equals("")){
      String url="roughReturnAction.do?method=ExitsSplit&issID="+issueId+"&stt="+IsStt+"&mdl="+mdl+"&form="+form;
      String openPop= "openPopUpDirect('"+url+"' ,'SC','Split')";
      %>
        <html:button property="value(ExitsSplit)" value="<%=fld_ttl%>" styleId="eSplit" onclick="<%=openPop%>" styleClass="submit" />
        
    <% }}}%>
  
  <%
    ArrayList rolenmLst=(ArrayList)info.getRoleLst();
  String usrFlg=util.nvl((String)info.getUsrFlg());
     if(usrFlg.equals("SYS") || rolenmLst.contains("ADMIN")){
  %>
  <img src="../images/edit.jpg" title="Edit Properties Module" onclick="newWindow('<%=info.getReqUrl()%>/dynamicProperty.do?method=load&mdl=<%=mdl%>&sname=<%=mdl%>&par=GENERIC')" border="0" width="15px" height="15px"/> 
  <%}%>
  </td></tr>
  
  <tr><td valign="top" class="tdLayout">
  
   <table class="grid1" id="dataTable">
   <tr> <th><input type="checkbox" name="checkAll" id="checkAll" onclick="ALLCheckBoxDisable('checkAll','CHK_')" /> </th><th>Sr</th>
       <th>Issue Id</th>
        <th>Packet No.</th>
        
        <%for(int j=0; j < vwPrpLst.size(); j++ ){
    String prp = (String)vwPrpLst.get(j);
     String hdr = util.nvl((String)mprp.get(prp));
    String prpDsc = util.nvl((String)mprp.get(prp+"D"));
    if(prpDspBlocked.contains(prp)){
       }else{
        if(hdr == null) {
        hdr = prp;
         } 
        if(prp.equals("CRTWT")) {%>
          <th>Qty</th>
        <%} %>
         
          <th title="<%=prpDsc%>"><%=hdr%></th>
          
          <% if(prp.equals("RTE")) {%>
          
          <th>Amount</th>
        <%} %>

     <%}}%>
     
     <%for(int s=0; s < rtnEditPrp.size();s++){
    String lprp = (String)rtnEditPrp.get(s);
   
    String prpDsc = (String)mprp.get(lprp+"D");
    String prpTyp = util.nvl((String)mprp.get(lprp+"T"));
    String prpFld = "value("+lprp+")";
    String chksrvAll = "chksrvAll('"+lprp+"')";
     String chksrvTxtAll = "chksrvTXTAll('"+lprp+"')";
    String selectALL = lprp;
    %>
    <th><%if(prpTyp.equals("C")){
     ArrayList prpVal = (ArrayList)prpLst.get(lprp+"V");
    ArrayList prpPrt = (ArrayList)prpLst.get(lprp+"P");
    %>
     <html:select  property="<%=prpFld%>" styleId="<%=selectALL%>" name="splitReturnForm" onchange="<%=chksrvAll%>"  style="width:100px"   > 
      <html:option value="0">--select--</html:option>
            <%
            for(int k=0; k < prpVal.size(); k++) {
                String pPrt = (String)prpVal.get(k);
             %>
              <html:option value="<%=pPrt%>" ><%=prpPrt.get(k)%></html:option> 
            <%}%>
     </html:select>  
     <%}else{%>
     <html:text property="<%=prpFld%>" styleId="<%=selectALL%>" name="splitReturnForm"  style="width:100px" onchange="<%=chksrvTxtAll%>" />
     <%}%>
     <%=prpDsc%>
     </th>
    <%}%>
    
      <%
         pageList= ((ArrayList)pageDtl.get("RGH_TTL") == null)?new ArrayList():(ArrayList)pageDtl.get("RGH_TTL");
      if(pageList!=null && pageList.size() >0){
      for(int j=0;j<pageList.size();j++){
      pageDtlMap=(HashMap)pageList.get(j);
      dflt_val=util.nvl((String)pageDtlMap.get("dflt_val"));
      if(dflt_val.equals("Y")){
      %>
      <th>TTL RGH QTY</th>
      <th>TTL RGH CTS</th>
      <%}}}%>
      
      
       <%
         pageList= ((ArrayList)pageDtl.get("PLANDTL") == null)?new ArrayList():(ArrayList)pageDtl.get("PLANDTL");
      if(pageList!=null && pageList.size() >0){
      for(int j=0;j<pageList.size();j++){
      pageDtlMap=(HashMap)pageList.get(j);
      dflt_val=util.nvl((String)pageDtlMap.get("dflt_val"));
      if(dflt_val.equals("Y")){
      %>
       <th>Plan</th>
        <th>Plan Dtl</th>
      <%}}}%>
    
    
    <%
         pageList= ((ArrayList)pageDtl.get("PRTRTN") == null)?new ArrayList():(ArrayList)pageDtl.get("PRTRTN");
      if(pageList!=null && pageList.size() >0){
      for(int j=0;j<pageList.size();j++){
      pageDtlMap=(HashMap)pageList.get(j);
      dflt_val=util.nvl((String)pageDtlMap.get("dflt_val"));
      if(dflt_val.equals("Y")){
      %>
      <th>Split</th>
  
     
     <%}else{%>
      <th>Split Count</th>
       <th>Split</th>
     <%}}}else{%>
        <th>Split Count</th>
     <th>Split</th>
     <%}%>
     </tr>
 <%
 
 ArrayList stockIdnLst =new ArrayList();
 LinkedHashMap stockList = new LinkedHashMap(stockListMap); 
 Set<String> keys = stockList.keySet();
        for(String key: keys){
       stockIdnLst.add(key);
        }

 for(int i=0; i < stockIdnLst.size(); i++ ){
  String stkIdn = (String)stockIdnLst.get(i);
 GtPktDtl stockPkt = (GtPktDtl)stockList.get(stkIdn);
 
  String vnm = (String)stockPkt.getValue("vnm");
 String stt = util.nvl((String)stockPkt.getValue("stt"));
 String cts = (String)stockPkt.getValue("cts");
 String qty = (String)stockPkt.getValue("qty");
  String quot = (String)stockPkt.getValue("upr");
  String Cp = util.nvl((String)stockPkt.getValue("CP"));
 String issIdn = (String)stockPkt.getValue("issIdn");
  String pktCnt =util.nvl((String)pktCountMap.get(issIdn+"_"+stkIdn),"0");
 String textId = "TXT_"+stkIdn;
  String target = "SC_"+stkIdn;
  String checkBoxId="CHK_"+stkIdn;
  String checkboxNme="value("+checkBoxId+")";
  boolean isDisabled = true;
  String LNKStyle="display:block";
  String BTNStyle="display:none";
  String readOnly = "";
  String onclick ="AssortTotalCal(this,"+cts+",'','')";
  if(stt.equals("LK")){
    isDisabled=false;
    LNKStyle = "display:none";
    BTNStyle = "display:block";
    readOnly = "readonly=\"readOnly\"";
    }
  int sr=i+1;
  
     String rghqty = util.nvl((String)pktCountMap.get(stkIdn+"_QTY"));
       String rghcts = util.nvl((String)pktCountMap.get(stkIdn+"_CTS"));
  String planId="plan_"+sr;
  String planfld="value(plan_"+sr+")";
  String planchg="createplan('"+sr+"','"+stkIdn+"','"+issIdn+"')";
 %>
 <tr id="<%=target%>"><td> <html:checkbox  property="<%=checkboxNme%>" disabled="<%=isDisabled%>" onclick="<%=onclick%>" styleId="<%=checkBoxId%>" value="yes" /></td><td><%=sr%></td> <td><%=issIdn%></td><td><%=vnm%></td>
 <%for(int j=0; j < vwPrpLst.size(); j++ ){
    String prp = (String)vwPrpLst.get(j);

     if(prp.equals("CRTWT")) {%>
          <td><%=stockPkt.getValue("qty")%>
          <input type="hidden" id="QTY_<%=stkIdn%>"  value="<%=qty%>" />
          </td>
        <%} %>
    <td><%=stockPkt.getValue(prp)%></td>
   <% if(prp.equals("RTE")) {%>
             
          <td><%=stockPkt.getValue("amt")%>
          </td>
        <%} %>
 <%}%>
 <%if(rtnEditPrp!=null && rtnEditPrp.size()>0){
 for(int s=0; s < rtnEditPrp.size();s++){
String lprp = (String)rtnEditPrp.get(s);
String prpTyp = util.nvl((String)mprp.get(lprp+"T"));

String fldVal = "value("+lprp+"_"+stkIdn+")";
String fldId = lprp+"_"+sr;
String onChange="";
   
if(lprp.equals("REJ_CTS"))
onChange="VerifedRGHTtl(this,'"+stkIdn+"','"+cts+"','"+sr+"')";
%>
<td>
<%if(prpTyp.equals("C")){
ArrayList prpVal = (ArrayList)prpLst.get(lprp+"V");
ArrayList prpDsc = (ArrayList)prpLst.get(lprp+"P");
%>
<html:select property="<%=fldVal%>" styleId="<%=fldId%>" name="splitReturnForm" style="width:100px">
<html:option value="0" >--select--</html:option>
<%for(int p=0;p<prpVal.size();p++){
String val = (String)prpVal.get(p);
%>
<html:option value="<%=val%>" ><%=prpDsc.get(p)%></html:option>
<%}%></html:select>
<%}else{


%>
<html:text property="<%=fldVal%>" styleId="<%=fldId%>" style="width:100px" onchange="<%=onChange%>" size="4"  name="splitReturnForm" />
<%}%>
</td>
<%}}%>

  <%
         pageList= ((ArrayList)pageDtl.get("RGH_TTL") == null)?new ArrayList():(ArrayList)pageDtl.get("RGH_TTL");
      if(pageList!=null && pageList.size() >0){
      for(int j=0;j<pageList.size();j++){
      pageDtlMap=(HashMap)pageList.get(j);
      dflt_val=util.nvl((String)pageDtlMap.get("dflt_val"));
      if(dflt_val.equals("Y")){
 
      %>
      <td>
      <input type="text" name="<%=stkIdn%>_RGH_QTY" size="5" id="<%=stkIdn%>_RGH_QTY" readonly="readonly" value="<%=rghqty%>" /></td>
      
       <td>
         <input type="text" name="<%=stkIdn%>_RGH_CTS" size="5" id="<%=stkIdn%>_RGH_CTS" readonly="readonly" value="<%=rghcts%>" />
       </td>
      <%}}}%>
       <%
         pageList= ((ArrayList)pageDtl.get("PLANDTL") == null)?new ArrayList():(ArrayList)pageDtl.get("PLANDTL");
      if(pageList!=null && pageList.size() >0){
      for(int j=0;j<pageList.size();j++){
      pageDtlMap=(HashMap)pageList.get(j);
      dflt_val=util.nvl((String)pageDtlMap.get("dflt_val"));
      if(dflt_val.equals("Y")){
      %>
     <td>
   <html:select property="<%=planfld%>" styleId="<%=planId%>"  name="splitReturnForm" onchange="<%=planchg%>">
    <html:option value="">Select</html:option>
     <option value="1">1</option>
     <option value="2">2</option>
     <option value="3">3</option>
     <option value="4">4</option>
     <option value="5">5</option>
    </html:select>
 </td>
 <td>
 <table>
 <tr><td>Plan</td><td>QTY</td><td>P.CTS </td><td>P.VLU </td><td>P.RTE </td><td>R.RTE </td> </tr>
 <%for(int k=1;k<=5;k++){
 String isDisplay="display:none";
  HashMap calDtl = new HashMap();
 if(planDtl!=null && planDtl.size()>0)
   calDtl = (HashMap)planDtl.get(stkIdn+"_"+k);
  if(calDtl ==null)
   calDtl =  new HashMap();
  if(calDtl!=null && calDtl.size()>0)
  isDisplay="";
 
  String isChecked = "";
 String dflYn = util.nvl((String)calDtl.get("DFLT_YN"));
 if(dflYn.equals("Y"))
 isChecked="checked=\"checked\"";
 
  String onValCondn = "updatePlanStatus('"+stkIdn+"','"+k+"','"+issIdn+"')";
 %>

 <tr id="PLANTR_<%=k%>_<%=stkIdn%>" style="<%=isDisplay%>"><td> <input type="radio" name="FNPLN_<%=stkIdn%>" <%=isChecked%>  id="FNPLN_<%=k%>_<%=stkIdn%>" value="<%=k%>" onclick="<%=onValCondn%>"/> &nbsp;<%=k%> </td> <td><label id="QTY_<%=k%>_<%=stkIdn%>" class="redLabel"><%=calDtl.get("QTY")%></label> </td><td><label id="CTS_<%=k%>_<%=stkIdn%>" class="redLabel"><%=calDtl.get("CTS")%></label> </td>

  <td><label id="VLU_<%=k%>_<%=stkIdn%>" class="redLabel"><%=calDtl.get("VLU")%></label> </td><td><label id="RTE_<%=k%>_<%=stkIdn%>" class="redLabel"><%=calDtl.get("RTE")%></label> </td>
   <td><label id="RRTE_<%=k%>_<%=stkIdn%>" class="redLabel"><%=calDtl.get("RRTE")%></label> </td>
 </tr>
 <%}%>

</table>
</td>
      <%}}}%>

 <%
         pageList= ((ArrayList)pageDtl.get("PRTRTN") == null)?new ArrayList():(ArrayList)pageDtl.get("PRTRTN");
      if(pageList!=null && pageList.size() >0){
      for(int j=0;j<pageList.size();j++){
      pageDtlMap=(HashMap)pageList.get(j);
      dflt_val=util.nvl((String)pageDtlMap.get("dflt_val"));
      if(dflt_val.equals("Y")){
      %>
<td><div id="LNKD_<%=stkIdn%>" style="<%=LNKStyle%>">
<a href="roughReturnAction.do?method=bulkSplit&mstkIdn=<%=stkIdn%>&issID=<%=issIdn%>&ttlcts=<%=cts%>&ttlQty=<%=qty%>&vnm=<%=vnm%>&grp=<%=grp%>&stt=<%=IsStt%>&rghQty=<%=rghqty%>&rghCts=<%=rghcts%>&form=<%=form%>&cp=<%=Cp%>" id="LNK_<%=stkIdn%>" onclick="loadASSFNL('<%=target%>','LNK_<%=stkIdn%>');"  target="SC">Split</a>
</div></td>
<%}else{%>
<td><input type="text" name="notext" id="<%=textId%>" size="4" value="<%=pktCnt%>"  onchange="setCountURL(this,'TXT_<%=stkIdn%>')" class="txtStyle" <%=readOnly%>/></td>

<td>
<div id="LNKD_<%=stkIdn%>" style="<%=LNKStyle%>">
<a href="roughReturnAction.do?method=SplitStone&mstkIdn=<%=stkIdn%>&issIdn=<%=issIdn%>&ttlcts=<%=cts%>&ttlQty=<%=qty%>&vnm=<%=vnm%>&grp=<%=grp%>&stt=<%=IsStt%>&mdl=<%=mdl%>&avgRte=<%=quot%>&cp=<%=Cp%>" id="LNK_<%=stkIdn%>" onclick="loadASSFNL('<%=target%>','LNK_<%=stkIdn%>');setCountURL(this,'TXT_<%=stkIdn%>');"  target="SC">Split</a>
</div>
<div id="BTN_<%=stkIdn%>" style="<%=BTNStyle%>">
<input type="button" name="unlock" value="Unlock" onclick="unlockLnk('<%=issIdn%>','<%=stkIdn%>')" class="submit" />
</div>
</td>

<%}}}else{%>


<td><input type="text" name="notext" id="<%=textId%>" size="4" value="<%=pktCnt%>"  onchange="setCountURL(this,'TXT_<%=stkIdn%>')" class="txtStyle" <%=readOnly%>/></td>

<td>
<div id="LNKD_<%=stkIdn%>" style="<%=LNKStyle%>">
<a href="roughReturnAction.do?method=SplitStone&mstkIdn=<%=stkIdn%>&issIdn=<%=issIdn%>&ttlcts=<%=cts%>&ttlQty=<%=qty%>&vnm=<%=vnm%>&grp=<%=grp%>&stt=<%=IsStt%>&mdl=<%=mdl%>&cp=<%=Cp%>" id="LNK_<%=stkIdn%>" onclick="loadASSFNL('<%=target%>','LNK_<%=stkIdn%>');setCountURL(this,'TXT_<%=stkIdn%>');"  target="SC">Split</a>
</div>
<div id="BTN_<%=stkIdn%>" style="<%=BTNStyle%>">
<input type="button" name="unlock" value="Unlock" onclick="unlockLnk('<%=issIdn%>','<%=stkIdn%>')" class="submit" />
</div>
</td>

<%}%>
</tr>

  <%
         pageList= ((ArrayList)pageDtl.get("PLANDTL") == null)?new ArrayList():(ArrayList)pageDtl.get("PLANDTL");
      if(pageList!=null && pageList.size() >0){
      for(int j=0;j<pageList.size();j++){
      pageDtlMap=(HashMap)pageList.get(j);
      dflt_val=util.nvl((String)pageDtlMap.get("dflt_val"));
      if(dflt_val.equals("Y")){
      %>
      <tr>
<td colspan="<%=colSpan%>" class="floating" width="950" height="600"  id="PLANDIV_<%=sr%>" style="display:none">

<iframe id="PLANFRAME_<%=sr%>" width="1500" height="600"  name="PLANFRAME_<%=sr%>" class="frameStyle"  align="middle" frameborder="0"></iframe>

</td>
</tr>
      <%}}}%>
 <%}%>
  
   
   </table>
   </td></tr>
  </html:form>
  <%}}%>
  <tr>
    <td><jsp:include page="/footer.jsp" /> </td>
    </tr>
  </table> </body>
</html>