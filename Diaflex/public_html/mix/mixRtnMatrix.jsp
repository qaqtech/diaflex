<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page errorPage="../error_page.jsp" %>
<%@ page contentType="text/html;charset=windows-1252"%>
<jsp:useBean id="info" class="ft.com.InfoMgr" scope="session" />
<jsp:useBean id="util" class="ft.com.JspUtil" scope="page" />
<%@ page import="java.util.ArrayList,java.util.HashMap,java.sql.ResultSet,ft.com.*, java.util.Date, java.util.ArrayList, ft.com.dao.*"%>
<%@taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>Mixing  Return Matrix</title>
    <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/bse.css?v=<%=info.getJsVersion()%>"/>
    <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/general.css?v=<%=info.getJsVersion()%>"/>
     <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/style.css?v=<%=info.getJsVersion()%>"/>
          <script language="javaScript" type="text/javascript" src="<%=info.getReqUrl()%>/scripts/ajax.js?v=<%=info.getJsVersion()%>"></script>
      <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/bse.js?v=<%=info.getJsVersion()%> " > </script>
  </head>
        <%String logId=String.valueOf(info.getLogId());
        String onfocus="cook('"+logId+"')";
        %>
 <body onfocus="<%=onfocus%>" onload="MM_preloadImages('images/faunalogo.png')"  onkeypress="return disableEnterKey(event);" topmargin="0" leftmargin="0" rightmargin="0" bottommargin="0">
 <%
 DBUtil dbutil = new DBUtil();
      DBMgr db = new DBMgr();
      db.setCon(info.getCon());
      dbutil.setDb(db);
 %>
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
  <img src="../images/line.jpg" border="0" /></td><td valign="middle"> <span class="pgHed">Mixing Return Matrix</span> </td>
</tr></table>
  </td>
  </tr>
  <%String msg = (String)request.getAttribute("msg1");
    if(msg!=null){%>
    <tr><td valign="top" class="tdLayout"><span class="redLabel"><%=util.nvl(msg)%></span></td></tr>
    <%}%>
   <tr><td valign="top" class="tdLayout">
   <html:form action="mix/mixRtnMatrixAction.do?method=loadGrid" method="post" onsubmit="return validateRtn_memo()"  enctype="multipart/form-data">
  <table  class="grid1">
  <tr><th colspan="2">Generic Search </th></tr>
  <tr><td align="center">Process</td><td align="center">
    <html:select property="value(mprcIdn)"  styleId="mprcIdn"  name="mixrtnMatrixForm"   >
       <html:optionsCollection property="prcList"  name="mixrtnMatrixForm"
            label="prc" value="mprcId" />
    </html:select>
   </td> </tr>
  <tr><td align="center">Issue Id</td><td align="center"><html:text property="value(issueId)" name="mixrtnMatrixForm" styleId="issueId" /> </td> </tr>
    <tr>
   <td colspan="2">
    <jsp:include page="/genericSrch.jsp"/>
   </td></tr>
         <tr><td>Load file in CSV</td>
        <td><html:file property="mixFile"  name="mixrtnMatrixForm"/> </td>
      </tr> 
   <tr><td colspan="2" align="center"><html:submit property="value(srch)" value="Fetch" styleClass="submit" />
   </td> 
   </tr>
   </table>
   </html:form>
   </td></tr> 
   <tr><td>&nbsp;</td></tr>
  
  <tr> <td valign="top" class="tdLayout">
  <html:form action="mix/mixRtnMatrixAction.do?method=save" method="post" onsubmit="return confirmChanges()">
  <input type="hidden" name="lockId" id="lockId" value="" />
  <html:hidden property="value(file)" styleId="file" name="mixrtnMatrixForm" />
  <html:hidden property="value(srchId)" styleId="srchIdn" name="mixrtnMatrixForm" />
  <html:hidden property="value(MEMO)" styleId="memo" name="mixrtnMatrixForm" />
  <html:hidden property="value(SHAPE)" styleId="sh" name="mixrtnMatrixForm" />
  <html:hidden property="value(INSTT)" styleId="instt" name="mixrtnMatrixForm" />
  <html:hidden property="value(ISSTT)" styleId="isstt" name="mixrtnMatrixForm" />
  <html:hidden property="value(OTSTT)" styleId="otstt" name="mixrtnMatrixForm" />
  <%
  ArrayList dprngLst = (ArrayList)session.getAttribute("dpRngLst");
  String view = util.nvl((String)request.getAttribute("view"));
  String shape = util.nvl((String)request.getAttribute("shape"));
  ArrayList sizeLst = (ArrayList)session.getAttribute("sizeLst");
  ArrayList clrLst = (ArrayList)session.getAttribute("clrLst");
  HashMap sizeClrMap = (HashMap)request.getAttribute("sizeClrMap");
  HashMap maxCrtMap = (HashMap)request.getAttribute("maxCrtMap");
  HashMap dataRte = (HashMap)request.getAttribute("dataRte");
  if(view.equals("Y")){
  if((maxCrtMap!=null && maxCrtMap.size()>0) && (sizeClrMap!=null && sizeClrMap.size()>0)){
  HashMap prp = info.getPrp();
  HashMap mprp = info.getMprp();
  ArrayList prpPrtSize=null;
  ArrayList prpPrt2Size=null;
  ArrayList prpValSize=null;
  ArrayList prpSrtSize = null;
  ArrayList prpPrtClr=null;
  ArrayList prpValClr=null;
  ArrayList prpSrtClr = null;
  prpPrtSize = (ArrayList)prp.get("MIX_SIZE"+"P");
  prpSrtSize = (ArrayList)prp.get("MIX_SIZE"+"S");
  prpValSize = (ArrayList)prp.get("MIX_SIZE"+"V");
  prpPrt2Size = (ArrayList)prp.get("MIX_SIZE"+"P2");
  
  prpPrtClr = (ArrayList)prp.get("MIX_CLARITY"+"P");
  prpSrtClr = (ArrayList)prp.get("MIX_CLARITY"+"S");
  prpValClr = (ArrayList)prp.get("MIX_CLARITY"+"V");
  
  ArrayList prpShSrtSize = (ArrayList)prp.get("SHAPES");
  ArrayList prpShValSize = (ArrayList)prp.get("SHAPEV");
  if(prpShSrtSize.indexOf(shape)!=-1)
  shape=(String)prpShValSize.get(prpShSrtSize.indexOf(shape));
  %>
  <label id="msg" class="redLabel"></label>
  <table>
 <tr>
 <td><input type="submit" name="Save"  class="submit" value="Save Changes" id="Save" style="display:none"/></td>
 <td><input type="button" name="reset"  class="submit" value="Enable Edit" onclick="UnabledText()" id="reset" style="display:none"/></td>
   <td><input type="button" name="verifySelected" style="display:none" class="submit" onclick="MixVerify()" value="Verify Changes" id="verify"/></td>
  <td><input type="button" name="unlock" style="display:''" class="submit" onclick="MixUnlock()" value="UnLock" id="unlock"/></td>
   <td><img src="../images/loadbig.gif" id="process" style="position:absolute;display:none"  border="0"/></td>
   
   </tr>
  </table>
   <table class="grid1">
   
   <tr>
   <th>Clarity/Size</th>
   <%for (int i = 0; i < sizeLst.size(); i++) {
   String sz = (String)sizeLst.get(i);
   String isDp = util.nvl((String)prpPrt2Size.get(prpValSize.indexOf(sz)));
   if(isDp.equals("DP")){
     for(int y=0;y<dprngLst.size();y++){
   ArrayList dpDscLst = (ArrayList)dprngLst.get(y);
   String dpDsc = (String)dpDscLst.get(0);
   String dpVal = (String)dpDscLst.get(1);
   String dp="65";
   if(dpDsc.indexOf("<")!=-1)
      dp="0";
   %>
    <th><%=sz%>&nbsp;Max Crt:<%=util.nvl((String)maxCrtMap.get(sz+"_DP_"+dp),"0")%> </th>
    <% }}else{%>
   <th><%=sz%>&nbsp;Max Crt:<%=util.nvl((String)maxCrtMap.get(sz+"_DP_0"),"0")%> </th>
   <%}}%>
   </tr>
   <tr><td></td>
   <%for (int i = 0; i < sizeLst.size(); i++) {
   String sz = (String)sizeLst.get(i);
   String lbIdn = (String)prpSrtSize.get(prpValSize.indexOf(sz));
   
    String isDp = util.nvl((String)prpPrt2Size.get(prpValSize.indexOf(sz)));
    if(isDp.equals("DP")){
    for(int x=0;x<dprngLst.size();x++){
   ArrayList dpDscLst = (ArrayList)dprngLst.get(x);
   String dpDsc = (String)dpDscLst.get(0);
   String dpVal = (String)dpDscLst.get(1);
    String lbDpIdn=lbIdn+"_DP_"+dpDsc;
   %>
  <td> <div><B> DP:<%=dpDsc%></b></div><label id="<%=lbDpIdn%>" class="redTxt"></label></td>
   <%}}else{
   lbIdn=lbIdn+"_DP_0";
   %>
     <td><label id="<%=lbIdn%>" class="redTxt"></label></td>
    <%}}%>
   </tr>
   <%for (int j = 0; j < clrLst.size(); j++) {
   String mix_clarity=(String)clrLst.get(j);
   String nextmix_clarity="0";
   String nextmixClrsrt="0";
   int n=j+1;
   if(n < clrLst.size()){
   nextmix_clarity=(String)clrLst.get(n);
   nextmixClrsrt=(String)prpSrtClr.get(prpValClr.indexOf(nextmix_clarity));
   }%>
   <tr>
   <th><%=mix_clarity%></th>
   <%for (int k = 0; k < sizeLst.size(); k++) {
   String mix_Size=(String)sizeLst.get(k);
   String isDp = util.nvl((String)prpPrt2Size.get(prpValSize.indexOf(mix_Size)));
   String colspan = "";
   if(isDp.equals("DP"))
   colspan = "colspan=\"2\"";
  
   String mixSizesrt=(String)prpSrtSize.get(prpValSize.indexOf(mix_Size));
   String mixClrsrt=(String)prpSrtClr.get(prpValClr.indexOf(mix_clarity));
   String rte="";
   String fldName = mixClrsrt+"_SZCLR_"+mixSizesrt+"_DP_0";
   String fldVal = "value("+fldName+")";
    String fldValIS = "value(IS_"+fldName+")";
   String fldValRte = "value(PRI_"+fldName+")";
    String styleCts = mixClrsrt+"_CTS_"+mixSizesrt+"_DP_0";
    String styleRte = "PRI_"+styleCts;
    if(dataRte!=null && dataRte.size()>0)
    rte=util.nvl((String)dataRte.get("PRI_"+fldName));
    if(rte.equals(""))
    rte=dbutil.getMixPriRte(shape,mix_Size,mix_clarity);
   String onChange = "saveSizeClrText("+mixClrsrt+","+mixSizesrt+",'0','"+styleCts+"', 'DP_0' ,"+rte+",this)";
   String onChangePri = "saveSizeClrPriText("+mixClrsrt+","+mixSizesrt+",'0','"+styleCts+"', 'DP_0' ,"+rte+",this)";
   String keypresscts="keypressMix(this.id,'"+nextmixClrsrt+"','CTS',event)";
   String keypressPri="keypressMix(this.id,'"+nextmixClrsrt+"','PRI',event)";  
   if(isDp.equals("DP")){
   for(int i=0;i<dprngLst.size();i++){
   ArrayList dpDscLst = (ArrayList)dprngLst.get(i);
   String dpDsc = (String)dpDscLst.get(0);
   String dpVal = (String)dpDscLst.get(1);
   fldName = mixClrsrt+"_SZCLR_"+mixSizesrt+"_DP_"+dpDsc;
   fldVal = "value("+fldName+")";
    fldValIS = "value(IS_"+fldName+")";
   fldValRte = "value(PRI_"+fldName+")";
   styleCts = mixClrsrt+"_CTS_"+mixSizesrt+"_DP_"+dpDsc;
   styleRte="PRI_"+styleCts;
   if(dataRte!=null && dataRte.size()>0){
   String filerte=util.nvl((String)dataRte.get("PRI_"+fldName));
    if(!filerte.equals(""))
    rte=filerte;
   }
   onChange = "saveSizeClrText('"+mixClrsrt+"','"+mixSizesrt+"','"+dpVal+"','"+styleCts+"' ,'DP_"+dpDsc+"','"+rte+"',this)";
   onChangePri = "saveSizeClrPriText('"+mixClrsrt+"','"+mixSizesrt+"','"+dpVal+"','"+styleCts+"' ,'DP_"+dpDsc+"','"+rte+"', this)";
    String tbox=util.nvl((String)sizeClrMap.get(mix_clarity+"_"+mix_Size));
   if(tbox.equals("T")){
   %>
    <td><html:text  property="<%=fldVal%>" disabled="true"  styleId="<%=styleCts%>" name="mixrtnMatrixForm" size="10" onchange="<%=onChange%>" onkeypress="<%=keypresscts%>"/>
  <label class="red"> <bean:write property="<%=fldValIS%>" name="mixrtnMatrixForm" /></label> &nbsp; <%=rte%><div style="float:right;"><html:text  property="<%=fldValRte%>" disabled="true"   styleId="<%=styleRte%>" onchange="<%=onChangePri%>" name="mixrtnMatrixForm" size="10"  value="<%=rte%>"  onkeypress="<%=keypressPri%>"/></div></td>
   <%}else{%><td></td> <%}%>
   
   <%}%>
   <% }else{
     String tbox=util.nvl((String)sizeClrMap.get(mix_clarity+"_"+mix_Size));
   if(tbox.equals("T")){
   %>
   <td><html:text  property="<%=fldVal%>" disabled="true"  styleId="<%=styleCts%>" name="mixrtnMatrixForm" size="10" onchange="<%=onChange%>" onkeypress="<%=keypresscts%>"/>
   <label class="red"> <bean:write property="<%=fldValIS%>" name="mixrtnMatrixForm" /></label> &nbsp;<%=rte%><div style="float:right;"><html:text  property="<%=fldValRte%>" disabled="true"  styleId="<%=styleRte%>" onchange="<%=onChangePri%>" name="mixrtnMatrixForm" size="10" value="<%=rte%>"  onkeypress="<%=keypressPri%>"/></div></td>
   <%}else{%>
   <td></td>
   <%}%>
   
   <%}%>
   
   <%}%>
   </tr>
   <%}%>
   
   </table>
   <%}else{%>
   Sorry No Result Found
   <%}}%>
   </html:form>
   </td>
   </tr>
    <%msg = (String)request.getAttribute("msg");
  if(msg!=null){
  %>
  <tr><td valign="top" class="tdLayout"><span class="redLabel"><%=msg%></span> </td></tr>
  <%}%>
   <tr><td valign="top" class="tdLayout">
   <%
   ArrayList pktList = (ArrayList)request.getAttribute("pktDtlList");
   if(pktList!=null && pktList.size()>0){%>
   <table class="grid1"><tr><th>Idn</th><th>Memo</th><th>Shape</th><th>Cts</th><th>Mix Size</th> 
   <th>Mix clarity</th><th>DP</th><th>UPR</th><th>CMP</th> </tr>
   <%for(int i=0;i<pktList.size();i++){
   HashMap pktDtl = (HashMap)pktList.get(i);
   %>
   <tr><td><%=pktDtl.get("idn")%></td>
   <td><%=pktDtl.get("memo")%></td><td><%=pktDtl.get("sh")%></td><td><%=pktDtl.get("cts")%></td><td><%=pktDtl.get("mixsz")%></td>
   <td><%=pktDtl.get("mixclr")%></td>
   <td><%=pktDtl.get("dp")%></td>  <td><%=pktDtl.get("upr")%></td>  <td><%=pktDtl.get("cmp")%></td>
   </tr>
   <%}%>
   
   </table>
   
   <%}%>
   </td></tr>
   
  </table>
  
  </body>
</html>