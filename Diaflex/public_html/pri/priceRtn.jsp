<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page errorPage="../error_page.jsp" %>
<%@ page contentType="text/html;charset=windows-1252"%>
<jsp:useBean id="info" class="ft.com.InfoMgr" scope="session" />
<jsp:useBean id="util" class="ft.com.JspUtil" scope="page" />
<%@ page import="java.util.ArrayList,java.util.HashMap,java.sql.ResultSet,ft.com.*, java.util.Date, java.util.ArrayList, ft.com.dao.*,java.io.File"%>
<%@taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>Pricing Return</title>
    <script language="javaScript" type="text/javascript" src="<%=info.getReqUrl()%>/scripts/ajax.js?v=<%=info.getJsVersion()%>"></script>
    <script src="<%=info.getReqUrl()%>/scripts/assortScript.js?v=<%=info.getJsVersion()%>" type="text/javascript"></script>
     <script src="<%=info.getReqUrl()%>/scripts/memoScript.js?v=<%=info.getJsVersion()%>" type="text/javascript"></script>
    <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/bse.css?v=<%=info.getJsVersion()%>"/>
    <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/general.css?v=<%=info.getJsVersion()%>"/>
     <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/style.css?v=<%=info.getJsVersion()%>"/>
     <script src="<%=info.getReqUrl()%>/scripts/priceChange.js?v=<%=info.getJsVersion()%>" type="text/javascript"></script>
     <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/bse.js?v=<%=info.getJsVersion()%> " > </script>
      <script type="text/javascript" src="<%=info.getReqUrl()%>/PopScripts/popup.js?v=<%=info.getJsVersion()%> " > </script>
     <script src="<%=info.getReqUrl()%>/PopScripts/jquery-1.2.6.min.js" type="text/javascript"></script>
    <script language="javaScript" type="text/javascript" src="<%=info.getReqUrl()%>/calendar/calendarFmt.js"></script>
   <link href="<%=info.getReqUrl()%>/calendar/calendar.css" rel="stylesheet" type="text/css">
  </head>
        <%String logId=String.valueOf(info.getLogId());
        String onfocus="cook('"+logId+"')";
        %>
 <body onfocus="<%=onfocus%>" onload="MM_preloadImages('images/faunalogo.png')" topmargin="0" leftmargin="0" rightmargin="0" bottommargin="0">

<div id="backgroundPopup"></div>
<div id="popupContactASSFNL">
<table class="popUpTb">
 <tr><td height="5%">&nbsp;&nbsp;&nbsp;<html:button property="value(close)" styleClass="submit"  onclick="MKEmptyPopup('SC');disablePopupASSFNL();selectRtnPrppkt();" value="Close"  /> </td></tr>
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
  <img src="../images/line.jpg" border="0" /></td><td valign="middle"> <span class="pgHed">Pricing Return</span> </td>
</tr></table>
  </td>
  </tr>
  <%
  String  lseq =(String)request.getAttribute("seqNo");
  if(lseq!=null){%>
    <tr><td valign="top" class="tdLayout"> <span class="redLabel"><%=lseq%></span> </td></tr>
    <%}%>
  <%  
   String accessidn=(String)request.getAttribute("accessidn");
   String vnmNtFnd = (String)request.getAttribute("vnmNotFnd");
     String view = (String)request.getAttribute("view");
     String allow_valid_prc=util.nvl((String)info.getDmbsInfoLst().get("ALLOW_VALID_PRC"),"N");
     boolean disabled= false;
     if(view!=null)
     disabled= true;
     if(vnmNtFnd!=null){%>
    <tr><td valign="top" class="tdLayout"><span class="redLabel"><%=vnmNtFnd%></span></td></tr>
   <%}%>
   
   
   <tr>
   
   <td valign="top" class="tdLayout">
   <table><tr><td>
   <html:form action="pri/pricertn.do?method=fecth" enctype="multipart/form-data" method="post" onsubmit="return validate_Assortreturn()">
   <html:hidden property="value(allow_valid_prc)" styleId="allow_valid_prc" name="priceRtnForm" value="<%=allow_valid_prc%>" />
   <table><tr><td>Process : </td>
   <td>
   <html:select property="value(mprcIdn)"  styleId="mprcIdn" name="priceRtnForm"  disabled="<%=disabled%>"  onchange="getvalidPrcEmp('mprcIdn')" >
           <html:option value="0" >--select--</html:option>
            <html:optionsCollection property="mprcList" name="priceRtnForm" 
            label="prc" value="mprcId" />
    </html:select>
   </td></tr>
   <tr>
    <td>
    Employee : </td>
   <td>
   <html:select property="value(empIdn)" styleId="empIdn" name="priceRtnForm"  disabled="<%=disabled%>"  >
           <html:option value="0" >--select--</html:option>
            <html:optionsCollection property="empList" name="priceRtnForm" 
            label="emp_nme" value="emp_idn" />
            </html:select>
   </td>
   </tr>
   <tr>
   <td>Issue Id:</td><td> <html:text property="value(issueId)" styleId="issueId"  name="priceRtnForm"  disabled="<%=disabled%>" /></td>
   </tr>
   <tr>
   <td>Packet No:</td><td> <html:textarea property="value(vnmLst)" cols="30" name="priceRtnForm"  disabled="<%=disabled%>" /></td>
   </tr>
  <tr>
  <td>Upload File</td>
  <td nowrap="nowrap"><html:file property="fileUpload" size="40"  styleId="fileUpload"   disabled="<%=disabled%>"  name="priceRtnForm" /> </td>
  </tr>
   <tr>
   <td colspan="2">  
   <html:submit property="value(srch)" value="Fetch" styleClass="submit"  disabled="<%=disabled%>"/>
   <%if(disabled){%>
   <html:button property="value(reload)" value="Reload" styleClass="submit" onclick="goTo('pricertn.do?method=load&grp=PRICING','','')"/>
   <%}%>
   </td>
   
   </tr></table>
   </html:form>
   </td></tr>
    <%
   String  msgLst = (String)request.getAttribute("msgList");
   if(msgLst!=null){
   %>
    <tr><td valign="top" class="tdLayout"><span class="redLabel"><%=msgLst%></span></td></tr>
   <%}%>
<%
   if(view!=null){
int sr = 0; 

HashMap dbInfoSys = info.getDmbsInfoLst();
HashMap prpLst = info.getPrp();
    HashMap pktList = (HashMap)session.getAttribute("pktList");
    ArrayList pktStkIdnList = (ArrayList)session.getAttribute("pktStkIdnList");
    ArrayList prpList = (ArrayList)session.getAttribute("labSVCPrpList");
    ArrayList options = (ArrayList)request.getAttribute("OPTIONS");
    ArrayList vnmImgLst = (request.getAttribute("vnmImgLst") == null)?new ArrayList():(ArrayList)request.getAttribute("vnmImgLst");
    ArrayList vnmStkCtgLst = (request.getAttribute("vnmStkCtgLst") == null)?new ArrayList():(ArrayList)request.getAttribute("vnmStkCtgLst");
    String generate = util.nvl((String)request.getAttribute("generate"));
    String prcisstt = util.nvl((String)request.getAttribute("prcisstt"));
    ArrayList prpDspBlocked = info.getPageblockList();
 
    if(pktList!=null && pktList.size()>0){
     HashMap totals = (HashMap)request.getAttribute("totalMap");
     String cnt = (String)dbInfoSys.get("CNT");
     String imgLoc = (String)dbInfoSys.get("IMG_PATH");
     String imgchecker = util.nvl((String)dbInfoSys.get("IMG_CHECKER"));
     String imageDir = "";
     String vnmPath = "";
     if(options == null)
        options = new ArrayList();
    %>
    <tr><td>
   <table>
   <tr><td>Total :&nbsp;&nbsp;</td><td><span id="ttlqty"> <%=totals.get("qty")%>&nbsp;&nbsp;</span></td><td>cts&nbsp;&nbsp;</td><td><span id="ttlcts"><%=totals.get("cts")%>&nbsp;&nbsp;</span></td><td>Selected:&nbsp;&nbsp;</td><td> Total :&nbsp;&nbsp;</td> <td><span id="qtyTtl">0&nbsp;&nbsp;</span></td><td>Cts&nbsp;&nbsp;</td><td><span id="ctsTtl">0</span> </td> </tr>
  
   </table>
   </td></tr>
    
   <html:form action="pri/pricertn.do?method=priRtn" onsubmit="return validate_issue('check_' , 'count')" method="post"  >
  <html:hidden property="value(prcId)" name="priceRtnForm" />
   <html:hidden property="value(packetId)" name="priceRtnForm" />
   <html:hidden property="value(empId)" name="priceRtnForm" />
   <html:hidden property="value(issueIdn)" name="priceRtnForm" />
   <input type="hidden" name="prcisstt" id="prcisstt" value="<%=prcisstt%>" />
  <tr><td>
  <table cellpadding="0" cellspacing="0">
  <tr><td><html:submit property="value(issue)" onclick="this.form.target='_self';" styleClass="submit" styleId="return" value="Return" /> 
  <logic:equal property="value(pri_yn)"  name="priceRtnForm"  value="Y" >
  <html:submit property="value(reprc)" onclick="this.form.target='_self';" styleClass="submit" styleId="reprc" value="RePrice" /> 
  </logic:equal>
 <%if(cnt.equals("sg")){%>
 <input type="submit" class="submit" onclick="this.form.target='_blank';" value="Premarketing report" name="premktrpt" id="premktrpt">
 <%}%>
  <%if(generate.equals("Y")){%>
  <html:submit property="value(generate)" onclick="this.form.target='_self';" styleClass="submit" value="Generate PacketId" />
  <html:button property="value(pktprint)" value="Packet Print"  onclick="newWindow('pricertn.do?method=pktPrint&p_access=<%=accessidn%>')" styleClass="submit" />
  <%}%>
  <html:button property="value(excel)" value="Create Excel"  onclick="goTo('pricertn.do?method=createXL','','')" styleClass="submit" />
   <%
  ArrayList rolenmLst=(ArrayList)info.getRoleLst();
  String usrFlg=util.nvl((String)info.getUsrFlg());
  if(usrFlg.equals("SYS") || rolenmLst.contains("ADMIN")){
  %>
  <img src="../images/edit.jpg" title="Edit Properties Module" onclick="newWindow('<%=info.getReqUrl()%>/dynamicProperty.do?method=load&mdl=PRI_VW&sname=priViewLst&par=A')" border="0" width="15px" height="15px"/> 
  <%}%></td>
  </tr>
  </table>
  </td></tr>
   <tr><td>
   <%
    
    ArrayList vwPrpLst = (ArrayList)session.getAttribute("priViewLst");
    HashMap mprp = info.getMprp();
    
    %>
    <table cellpadding="0" cellspacing="0">
    
    <tr ><td valign="top">
   
    <table class="grid1" id="dataTable">
<thead>
<tr>
<th><label title="SR NO">SN.</label></th>
<th><label title="CheckAll"><input  type="checkbox" id="checkAll" name="checkAll" onclick="ALLCheckBoxDisable('checkAll','check_');assortVerificationExlAllDisable();" />S</label></th>
<th><label title="Details">Dtl</label></th>
<th>Edit</th>
<th>Edit+Repairing</th>
<th><label title="Issue Id">Issue Id</label></th>
<th><label title="Packet No">Packet No</label></th>
<th><label title="Rap RTE">Rap Rte</label></th>
<th><label title="Manual">MNL</label></th>
<th><label title="cmp">CMP</label></th>
<th><label title="Rap dis">Rap Dis</label></th>
<th><label title="Modify Discount">Modify Dis</label></th>
<th><label title="Difference Amount">Diff Amt</label></th>
<%for(int j=0; j < vwPrpLst.size(); j++ ){
    String prp = (String)vwPrpLst.get(j);
    if(prpDspBlocked.contains(prp)){
       }else{
     String hdr = (String)mprp.get(prp);
    String prpDsc = (String)mprp.get(prp+"D");
    if(hdr == null) {
        hdr = prp;
       }  
 if(prp.equals("EXTRADISCAMT")){
 }else if(prp.equals("RTE")){
 %>
 <th title="<%=prpDsc%>"><%=hdr%></th>
 <th title="Back Diff">Back Diff</th>
 <%  }else{
%>
<th title="<%=prpDsc%>"><%=hdr%></th>

<%}}}%>
<th nowrap="nowrap">
<%if(options.size()>0){ %>
<%
for(int j=0; j < options.size(); j++) {


String lopt = (String)options.get(j);

%>
<div class="float"><input type="radio" name="STT" id="<%=lopt%>" onclick="ALLRedio('<%=lopt%>','<%=lopt%>_')" />&nbsp;<%=lopt%></div>
<%}
}%>
</th>
<%for(int j=0; j < prpList.size(); j++ ){
     String lprp = (String)prpList.get(j);
     ArrayList prpVal = (ArrayList)prpLst.get(lprp+"V");
    String prpDsc = util.nvl((String)mprp.get(lprp+"D"));
    String prpFld = "value("+lprp+")";
    String chksrvAll = "chksrvAll('"+lprp+"')";
    String selectALL = lprp;
    %>
    <th><%=prpDsc%>
    <%if(prpVal!=null && prpVal.size()>0){%>
     <html:select  property="<%=prpFld%>" styleId="<%=selectALL%>" name="priceRtnForm" onchange="<%=chksrvAll%>"  style="width:75px"   > 
     
            <html:option value="0">--select--</html:option>
            <%
            for(int k=0; k < prpVal.size(); k++) {
                String pPrt = (String)prpVal.get(k);
            %>
              <html:option value="<%=pPrt%>" ><%=pPrt%></html:option> 
            <%}%>
              </html:select>  
              <%}%>
    </th>
    <%}%>
</tr>
</thead>
<tbody>
<%


int colSpan = vwPrpLst.size()+8;
int lastno= pktStkIdnList.size()-1;
for(int m=0;m< pktStkIdnList.size();m++){ 
String stkIdn = (String)pktStkIdnList.get(m);
HashMap pktData= (pktList.get(stkIdn) == null)?new HashMap():(HashMap)pktList.get(stkIdn);
if(pktData!=null && pktData.size()>0){
String cts =util.nvl((String)pktData.get("cts"));
String stt =util.nvl((String)pktData.get("stt1"));
 String issIdn = (String)pktData.get("iss_idn");
String rap_dis = (String)pktData.get("r_dis");
String mfg_pri = util.nvl((String)pktData.get("MFG_PRI"),"0");
String rtnprpupdated = util.nvl((String)pktData.get("rtnprpupdated"),"N");
String rte = util.nvl((String)pktData.get("RTE"),"0");
double mfg_c = Double.parseDouble(mfg_pri);
double rte_c = Double.parseDouble(rte);
String backDiff  = String.valueOf(Math.round(((mfg_c/rte_c*100)- 100)));
sr = m+1;
float rapDis = 0;
String cellCol ="";
if(!rap_dis.equals("")){
  rapDis = Float.parseFloat(rap_dis);
  if(rapDis < -50 || rapDis > 50)
    cellCol ="style=\"color:red\"";
  }

String chgVnmColor="";
if(rtnprpupdated.equals("Y")){
chgVnmColor="#FFC977";
}
String checkVal = "value(check_"+stkIdn+")";
String checkId = "check_"+m;
String rdVal = "value(STT_"+stkIdn+")";
String modDis = "value(modDis_"+stkIdn+")";
String modDisId = "uprDis_"+stkIdn;
String modDisIdfun = "trfToMktRapRtePri('"+stkIdn+"');GetMFG_PRI('"+stkIdn+"')";
String diffAmt = "value(DAMT_"+stkIdn+")";
String diffAmtId = "DAMT_"+stkIdn;
String rdSttId = "STT_"+stkIdn;
String target = "SC_"+stkIdn;
String mnl = util.nvl((String)pktData.get("quot"));
String mnlFld = "value(mnl_"+stkIdn+")";
String mnlFldID = "upr_"+stkIdn;
String mnlFldIDFun = "trfToMktRapDis('"+stkIdn+"');GetMFG_PRI('"+stkIdn+"')";
String onModifyChg ="CalculateDiff('"+stkIdn+"',this)";
String extra = util.nvl((String)pktData.get("EXTRADISCAMT")).trim();
String sltarget = "SL_"+stkIdn;
String onclick = "AssortTotalCal(this,"+cts+",'','');assortVerificationExl("+stkIdn+", this);setBGColorOnClickCHK(this,'"+target+"')";
String vnm=util.nvl((String)pktData.get("vnm"));
disabled=false;
if(vnmStkCtgLst.contains(vnm)){
disabled=true;
}
if(vnmImgLst.contains(vnm) && !vnm.equals("") && imgchecker.equals("Y") && !disabled){
imageDir = imgLoc+"pics/diamondImg/";
vnmPath = imageDir+vnm+".jpg";
System.out.print("image:"+vnmPath);
disabled = util.isExistS3(vnmPath);
//File vnmFile = new File(vnmPath);
//if((vnmFile != null) && vnmFile.exists()){
//}else{
//disabled=true;
//}
}
double exT = 0;
if(!extra.equals(""))
  exT = Double.parseDouble(extra);
if(exT==0)
 extra="";
%>
<tr id="<%=target%>" onclick="setBGColorOnClickTR('<%=target%>')">

<td><%=sr%></td>
<td><html:checkbox property="<%=checkVal%>" styleId="<%=checkId%>" name="priceRtnForm" onclick="<%=onclick%>" value="Y" disabled="<%=disabled%>"/> </td>
<td align="center"><a href="pricertn.do?method=details&mstkIdn=<%=stkIdn%>" id="LNK_<%=stkIdn%>" onclick="loadASSFNL('<%=target%>','LNK_<%=stkIdn%>')"  target="SC"><img src="../images/magicon.png" title="Click Here To Get Details"></a></td>
<td><a href="pricertn.do?method=stockUpd&mstkIdn=<%=stkIdn%>&issIdn=<%=issIdn%>&currentpage=<%=m%>&lastpage=<%=lastno%>&stt=<%=stt%>&isRepair=N" id="LNK_<%=stkIdn%>" onclick="loadASSFNL('<%=target%>','LNK_<%=stkIdn%>')"  target="SC">Edit</a></td> 
<td><a href="pricertn.do?method=stockUpd&mstkIdn=<%=stkIdn%>&issIdn=<%=issIdn%>&currentpage=<%=m%>&lastpage=<%=lastno%>&stt=<%=stt%>&isRepair=Y" id="LNK_<%=stkIdn%>" onclick="loadASSFNL('<%=target%>','LNK_<%=stkIdn%>')"  target="SC">Edit</a></td> 
<td><%=pktData.get("iss_idn")%></td>
<td bgcolor="<%=chgVnmColor%>" id="TD_<%=stkIdn%>">
<a <%=cellCol%> href="<%=info.getReqUrl()%>/marketing/PacketLookup.do?method=PktDetail&mstkIdn=<%=stkIdn%>" id="LNK_<%=stkIdn%>" onclick="loadASSFNL('<%=target%>','LNK_<%=stkIdn%>')"  target="SC">
<%=vnm%></a>
<input type="hidden" id="CTS_<%=stkIdn%>"  value="<%=cts%>" /> </td>
<td><%=pktData.get("rap_rte")%><input type="hidden" name="rap_<%=stkIdn%>" value="<%=pktData.get("rap_rte")%>" id="rap_<%=stkIdn%>" /></td>
<td>
<%=mnl%>
<input type="hidden"  value="<%=mnl%>" id="mnlOld_<%=stkIdn%>" />
<!--<html:text property="<%=mnlFld%>" size="8"  />-->
<%
%>
  <html:text property="<%=mnlFld%>" styleId="<%=mnlFldID%>" size="8"  onchange="<%=mnlFldIDFun%>"  />
</td>
<td><%=pktData.get("cmp")%>
<input type="hidden" name="qout_<%=stkIdn%>" value="<%=pktData.get("cmp")%>" id="qout_<%=stkIdn%>" /> </td>
<td><span <%=cellCol%>><%=pktData.get("r_dis")%> </span><input type="hidden" name="dis_<%=stkIdn%>" value="<%=pktData.get("r_dis")%>" id="dis_<%=stkIdn%>" /> </td>
<td><html:text property="<%=modDis%>" size="8" styleId="<%=modDisId%>" onchange="<%=modDisIdfun%>"/></td>
<td><html:text property="<%=diffAmt%>" size="8" readonly="true" styleId="<%=diffAmtId%>"/>
<input type="hidden"  value="<%=extra%>" id="amt_<%=stkIdn%>" />
</td>

<%
String cellStyle ="";
float qout = 0;
if(!mnl.equals(""))
 qout = Float.valueOf(mnl);
 for(int l=0;l<vwPrpLst.size();l++){
   String prp = (String)vwPrpLst.get(l);
   if(prpDspBlocked.contains(prp)){
       }else{
    String prpValue =  (String) pktData.get(prp);
    if(prpValue.equals("NA"))
     prpValue = "";
    String prpDsc = (String)mprp.get(prp+"D");
     if(prp.equals("MIX_RTE")){
   
     if(prpValue.equals(""))
       prpValue = "0";
      
       float mixIntRTE =  Float.valueOf(prpValue);
       if(mixIntRTE >qout)
       cellStyle ="style=\"background-color:Lime;\"";
     }
    
   if(prp.equals("EXTRADISCAMT")){
   }else if(prp.equals("RTE")){
   %>
   <td title="<%=prpDsc%>" <%=cellStyle%>><%=prpValue%></td>
   <td title="<%=prpDsc%>" <%=cellStyle%>><%=backDiff%></td>
   <% }else{
 %>
<td title="<%=prpDsc%>" <%=cellStyle%>><%=prpValue%></td>
<%}}}%> 
<td><table><tr><%
for(int j=0; j < options.size(); j++) {%>

<%
String lopt = (String)options.get(j);
String rfId = lopt+"_"+stkIdn;
String rfldNme = "value(STT_"+ stkIdn + ")";
%>
<td><html:radio property="<%=rfldNme%>" styleId="<%=rfId%>" value="<%=lopt%>"/>&nbsp;<%=lopt%></td>
<%}%>
</tr></table>
</td>
<%for(int j=0; j < prpList.size(); j++ ){
     String lprp = (String)prpList.get(j);
     ArrayList prpVal = (ArrayList)prpLst.get(lprp+"V");
   
    String prpFld = "value("+lprp+"_"+stkIdn+")";
    String selectApp = lprp+"_"+stkIdn;
    String typ = util.nvl((String)mprp.get(lprp+"T"));
    %>
    <td nowrap="nowrap">
    <%if(typ.equals("C")){%>
     <html:select  property="<%=prpFld%>" styleId="<%=selectApp%>" name="priceRtnForm"  style="width:75px"   > 
     
            <html:option value="0">--select--</html:option>
            <%
            for(int k=0; k < prpVal.size(); k++) {
                String pPrt = (String)prpVal.get(k);
               
            %>
              <html:option value="<%=pPrt%>" ><%=pPrt%></html:option> 
            <%}%>
              </html:select>  
     <%}else if(typ.equals("D")){%>
  <html:text property="<%=prpFld%>" readonly="true" styleId="<%=selectApp%>" styleClass="txtStyle" name="priceRtnForm" size="8" /><img src="<%=info.getReqUrl()%>/images/calender.png" onClick="setYears(2011, <%=info.getCurrentYear()%>);showCalender(this, '<%=selectApp%>');"> 
  <%}else{%>
     <html:text property="<%=prpFld%>" styleId="<%=selectApp%>"  styleClass="txtStyle" name="priceRtnForm"  style="width:75px"/>
     <%}%>
    </td>
    <%}%>
</tr>
<%}}%>        
</tbody>
</table>
</td></tr></table>
   
   
   </td></tr>
   <input type="hidden" name="count" id="count" value="<%=sr%>" />
   </html:form>
    <%}else{%>
     <tr><td>Sorry no result found </td></tr>
  <% } }%>
   
   
   </table>
   
  
  
   <tr>
    <td><jsp:include page="/footer.jsp" /> </td>
    </tr>
  </table>
  </body>
    <%@include file="../calendar.jsp"%>
</html>