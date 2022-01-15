<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page errorPage="../error_page.jsp" %>
<%@ page contentType="text/html;charset=windows-1252"%>
<jsp:useBean id="info" class="ft.com.InfoMgr" scope="session" />
<jsp:useBean id="util" class="ft.com.JspUtil" scope="page" />
<jsp:useBean id="gtMgr" class="ft.com.GtMgr" scope="session" />
<%@ page import="java.util.ArrayList,java.util.LinkedHashMap, ft.com.dao.GtPktDtl,java.util.Set,java.util.HashMap,java.sql.ResultSet,ft.com.*, java.util.Date, java.util.ArrayList, ft.com.dao.*"%>
<%@taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>Lab Issue</title>
    <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/bse.css?v=<%=info.getJsVersion()%>"/>
    <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/general.css?v=<%=info.getJsVersion()%>"/>
     <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/style.css?v=<%=info.getJsVersion()%>"/>
<script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/bse.js?v=<%=info.getJsVersion()%> " > </script>
       <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/labScripts.js?v=<%=info.getJsVersion()%> " > </script>
       <script src="<%=info.getReqUrl()%>/scripts/spacecode.js?v=<%=info.getJsVersion()%>"></script>
       <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/dispose.js?v=<%=info.getJsVersion()%> " > </script>
       <script src="../scripts/assortScript.js" type="text/javascript"></script>
  </head>
        <%String logId=String.valueOf(info.getLogId());
        String onfocus="cook('"+logId+"')";
       String lstNme = (String)gtMgr.getValue("lstNmeLABAU");
        gtMgr.setValue(lstNme+"_SEL",new ArrayList());
        ArrayList itemHdr = new ArrayList();
        %>
 <body onfocus="<%=onfocus%>" onload="MM_preloadImages('images/faunalogo.png')" topmargin="0" leftmargin="0" rightmargin="0" bottommargin="0">
  <input type="hidden" id="deviceip" name="deviceip" value="<%=util.nvl((String)info.getSpaceCodeIp())%>"/>
<%
 int accessidn=Integer.parseInt(util.nvl((String)request.getAttribute("accessidn"),"0"));
HashMap prpLst = info.getPrp();
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
  <img src="../images/line.jpg" border="0" /></td><td valign="middle"> <span class="pgHed">Auto Lab Issue</span> </td>
</tr></table>
  </td>
  </tr>
  <%
   String vnmNtFnd = (String)request.getAttribute("vnmNotFnd");
     if(vnmNtFnd!=null){%>
    <tr><td valign="top" class="tdLayout"><span class="redLabel"><%=vnmNtFnd%></span></td></tr>
   <%}%>
   <%
   HashMap dbInfoSys = info.getDmbsInfoLst();
   String msg = (String)request.getAttribute("msg");
   if(msg!=null){
   ArrayList assortViewMap= info.getAssortViewMap();
   String issueIdn = (String)request.getAttribute("issueIdn");
   String lab = util.nvl((String)request.getAttribute("Lab")).toUpperCase();
   String cnt = (String)dbInfoSys.get("CNT");
   String homeDir = (String)dbInfoSys.get("HOME_DIR");
    String webDir = (String)dbInfoSys.get("REP_URL");
      String repPath = (String)dbInfoSys.get("REP_PATH");
   String url = info.getReqUrl()+"/excel/labxl?issueIdn="+issueIdn+"&lab="+lab;
   String url2 = info.getReqUrl()+"/excel/labxl?issueIdn="+issueIdn+"&lab="+lab+"&xl=2";
    String pktPntLink = repPath+"/reports/rwservlet?"+cnt+"&report="+homeDir+"\\reports\\pktprint_lbiss.rdf&p_iss_id="+issueIdn+"&p_access="+accessidn;
   String assortRpt = repPath+"/reports/rwservlet?"+cnt+"&report="+homeDir+"\\reports\\assort_rpt.jsp&p_iss_id="+issueIdn+"&p_access="+accessidn;
   String printLink = info.getReqUrl()+"/lab/labIssue.do?method=pktPrint&issueIdn="+issueIdn+"&p_access="+accessidn;
   String labPdf = repPath+"/reports/rwservlet?"+cnt+"&report="+homeDir+"\\reports\\AFT_GIA_ISS.jsp&P_MEMO_NO="+issueIdn+"&P_LAB="+lab+"&p_access="+accessidn ;
   %>
    <tr><td valign="top" class="tdLayout"><span class="redLabel"><%=msg%></span></td></tr>
    <tr><td valign="top" class="tdLayout">
     <%         
        HashMap allPageDtl= (info.getPageDetails() == null)?new HashMap():(HashMap)info.getPageDetails();
        HashMap pageDtl=(HashMap)allPageDtl.get("LAB_ISSUE");
                ArrayList pageList=new ArrayList();
                HashMap pageDtlMap=new HashMap();
                String fld_ttl="";
                String dflt_val="";
                String lov_qry="";
                String flg1="",val_cond="";
                if(pageDtl!=null && pageDtl.size() >0){  
                     for(int i=0;i<pageDtl.size();i++){
                    pageList= ((ArrayList)pageDtl.get("LINK") == null)?new ArrayList():(ArrayList)pageDtl.get("LINK");
                    for(int j=0;j<pageList.size();j++){
                        pageDtlMap=(HashMap)pageList.get(j);
                        fld_ttl=(String)pageDtlMap.get("fld_ttl");
                        dflt_val=(String)pageDtlMap.get("dflt_val");
                        lov_qry=(String)pageDtlMap.get("lov_qry");
                        flg1=(String)pageDtlMap.get("flg1");
                        val_cond=(String)pageDtlMap.get("val_cond");    
                        if(val_cond.equals(""))
                        dflt_val = info.getReqUrl()+dflt_val.replaceAll("ISSIDN", issueIdn);
                        else
                        dflt_val = webDir+dflt_val.replaceAll("ISSIDN", issueIdn);
                        dflt_val = dflt_val.replaceAll("LAB", lab);
                        if(pageList.size()-1!=j){
                        %>
                        <span class="txtLink"> <a href="<%=dflt_val%>" target="_blank"><%=fld_ttl%></a></span> &nbsp;|&nbsp;
                        <%}else{%>
                            <span class="txtLink"> <a href="<%=dflt_val%>" target="_blank"><%=fld_ttl%></a></span>    
                        <%}
                 }
                }
            }
                %>
    </td></tr>
    
   <%}%>
   <%
   String msgEr = (String)request.getAttribute("Errmsg");
   if(msgEr!=null){
   %>
   <tr>
  <td valign="top" class="tdLayout"><span class="redLabel"><%=msgEr%></span></td></tr>
   <%}%>
  <tr>
  
 <td valign="top" class="tdLayout">
<table>

<tr>
<td valign="top" class="tdLayout">
<html:form action="lab/autolabIssue.do?method=fetch" method="post" onsubmit="return checkLab();">
<html:hidden property="value(LAB_PRC_1)" name="labIssueForm" />
<html:hidden property="value(FORMNME)" value="AUTO" name="labIssueForm" />
<html:hidden property="value(listname)" styleId="listname" value="pid" />
<table>
<tr><td> Process : </td><td>
<html:select property="value(mprcIdn)"  styleId="mprcIdn" name="labIssueForm"    >

<html:optionsCollection property="mprcList" name="labIssueForm"  label="prc" value="mprcId" />
 </html:select> </td></tr>
 <tr>
 <td> RF ID :</td><td>
<html:textarea property="value(vnmLst)" rows="10" name="labIssueForm" styleId="pid" />
</td>
 
 </tr>

<tr><td align="center" colspan="2"><html:submit property="view" value="View" styleClass="submit"/>


<%
 ArrayList rfiddevice = ((ArrayList)info.getRfiddevice() == null)?new ArrayList():(ArrayList)info.getRfiddevice();
 if(rfiddevice!=null && rfiddevice.size()>0){
 %>
 RFID Device: <html:select property="value(dvcLst)" styleId="dvcLst" name="labIssueForm"  >
 <%for(int j=0;j<rfiddevice.size();j++){
 ArrayList device=new ArrayList();
 device=(ArrayList)rfiddevice.get(j);
 String val=(String)device.get(0);
 String disp=(String)device.get(1);
 %>
 <html:option value="<%=val%>"><%=disp%></html:option>
 <%}%>
 </html:select>&nbsp;
 <%if(info.getRfid_seq().length()==0){%>
 <html:button property="value(rfScan)" value="RF ID Scan" styleId="rfScan" onclick="doScan('pid','fldCtr','dvcLst','SCAN')"  styleClass="submit" />
 <html:button property="value(autorfScan)" value="Auto Scan" styleId="autorfScan" onclick="doScan('pid','fldCtr','dvcLst','AUTOSCAN')"  styleClass="submit" />
<%}%>
 <html:button property="value(stopAutorfScan)" value="Stop Auto Scan" onclick="doScan('pid','fldCtr','dvcLst','STOPAUTOSCAN')"  styleClass="submit" />
 
<%}%>
              <%if(util.nvl((String)info.getDmbsInfoLst().get("RFID_DNK"),"N").equals("Y")){%>
                        Continue Scan <input type="checkbox" id="contscan" title="Select To Continue Scan"></input>
                        <span style="margin:0px; padding:0px; display:none;">
                        <label id="summary" style="display:none;"></label>&nbsp;&nbsp;
                        </span>
                        Count&nbsp;&nbsp;:&nbsp;<label id="count"></label>&nbsp;&nbsp;
                        <span style="margin:0px; padding:0px; display:none;">
                        Notify&nbsp;&nbsp;:&nbsp;<label id="notify"></label>
                        <input type="checkbox" id="accumulateMode" title="Accumulate Mode" onchange="AcculateChanged()"></input>
                        </span>
                        <html:button property="value(myButton)" value="Scan" styleId="myButton" onclick="scanRf()"  styleClass="submit" />
                        <span id="loadingrfid"></span>
             <%}else if(util.nvl((String)info.getDmbsInfoLst().get("RFID_DNK_USB"),"N").equals("Y")){%>
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

</table>
</html:form>
</td>
</tr>

<%
String view = (String)request.getAttribute("view");
String ctwt=null;
if(view!=null){
ArrayList prpDspBlocked = info.getPageblockList();
ArrayList issEditPrp = (ArrayList)gtMgr.getValue("LabIssueEditPRP");
HashMap stockListMap = (HashMap)gtMgr.getValue(lstNme);
if(stockListMap!=null && stockListMap.size()>0){
ArrayList vwPrpLst = (ArrayList)session.getAttribute("LabViewLst");
HashMap totals = (HashMap)gtMgr.getValue(lstNme+"_TTL");
HashMap mprp = info.getMprp();
int sr = 0;
%>
  <%
   ArrayList MappingMiss = (ArrayList)request.getAttribute("MappingMiss");
   if(MappingMiss!=null){%>
   <tr><td valign="top" class="tdLayout"><span class="redLabel">*Please Do mapping before Issue Stone</span></td></tr>
  <% for(int i=0;i<MappingMiss.size();i++){
   
   %>
    <tr><td valign="top" class="tdLayout"><span class="redLabel"><%=MappingMiss.get(i)%></span></td></tr>
   <%}}%>
<html:form action="lab/autolabIssue.do?method=Issue" onsubmit="return validate_issue('CHK_' , 'countcnt')" method="post">
<html:hidden property="value(prcId)" name="labIssueForm" />
<html:hidden property="value(empId)" name="labIssueForm" />
<html:hidden property="value(lstNme)" styleId="lstNme"  value="<%=lstNme%>" name="labIssueForm" />
<input type="hidden" name="accessidn" id="accessidn" value="<%=accessidn%>"/>
<tr>
<td>
<table>

<tr><td>
<table>
<tr><td>Total :&nbsp;&nbsp;</td><td><span id="ttlqty"> <%=totals.get("qty")%>&nbsp;&nbsp;</span></td><td>cts&nbsp;&nbsp;</td><td><span id="ttlcts"><%=totals.get("cts")%>&nbsp;&nbsp;</span></td>
<td>Avg&nbsp;&nbsp;</td><td><span id="ttlavg"><%=totals.get("avg_Rte")%>&nbsp;&nbsp;</span></td>
<td>Vlu&nbsp;&nbsp;</td><td><span id="ttlvlu"><%=totals.get("vlu")%>&nbsp;&nbsp;</span></td>
<td>RapVlu&nbsp;&nbsp;</td><td><span id="ttlrapvlu"><%=totals.get("rapvlu")%>&nbsp;&nbsp;</span></td>
<td>Selected:&nbsp;&nbsp;</td><td> Total :&nbsp;&nbsp;</td> 
<td><span id="qtyTtl">0&nbsp;&nbsp;</span></td>
<td>Cts&nbsp;&nbsp;</td><td><span id="ctsTtl">0</span> </td>
<td>AvgRte&nbsp;&nbsp;</td><td><span id="avgTtl">0</span> </td>
<td>Vlu&nbsp;&nbsp;</td><td><span id="vluTtl">0</span> </td>
<td>RapVlu&nbsp;&nbsp;</td><td><span id="rapvluTtl">0</span> </td>
</tr>
</table>
</td></tr>
<tr><td><table>
<tr><td><table><tr><td>
<html:submit property="value(issue)" styleClass="submit" value="Issue" /> </td>
<td><html:button property="value(excel)" value="Create Excel"  onclick="goTo('autolabIssue.do?method=createXL','','')" styleClass="submit" /></td>
<%
  ArrayList rolenmLst=(ArrayList)info.getRoleLst();
  String usrFlg=util.nvl((String)info.getUsrFlg());
  if(usrFlg.equals("SYS") || rolenmLst.contains("ADMIN")){
  %>
  <td><img src="../images/edit.jpg" title="Edit Properties Module" onclick="newWindow('<%=info.getReqUrl()%>/dynamicProperty.do?method=load&mdl=LAB_VIEW&sname=LabViewLst&par=A')" border="0" width="15px" height="15px"/> </td>
  <%}%>
</tr></table>
</td>

</tr></table></td></tr>
</table></td></tr>
<tr><td>
<table class="grid1">
<tr> <th><input type="checkbox" name="checkAll" id="checkAll" onclick="GenSel(this,'ALL')" /> </th><th>Sr</th>
<th>Packet No.</th>
<%
  HashMap hdrDtl = new HashMap();
  hdrDtl.put("hdr", "vnm");
  hdrDtl.put("typ", "N");
  hdrDtl.put("htyp", "C");
  itemHdr.add(hdrDtl);

%>
<%for(int j=0; j < vwPrpLst.size(); j++ ){
String prp = (String)vwPrpLst.get(j);
if(prpDspBlocked.contains(prp)){
}else{
String hdr = (String)mprp.get(prp);
String prpDsc = (String)mprp.get(prp+"D");
String prpTyp = util.nvl((String)mprp.get(prp+"T"));
if(hdr == null) {
hdr = prp;
}
      hdrDtl = new HashMap();
      hdrDtl.put("hdr", prp);
      hdrDtl.put("typ", "P");
      hdrDtl.put("dp", "D");
      hdrDtl.put("htyp",prpTyp);
       itemHdr.add(hdrDtl); 
%>
<th title="<%=prpDsc%>"><%=hdr%></th>

<%}}%>
<%for(int s=0; s < issEditPrp.size();s++){
    String lprp = (String)issEditPrp.get(s);
    ArrayList prpVal = (ArrayList)prpLst.get(lprp+"V");
    ArrayList prpPrt = (ArrayList)prpLst.get(lprp+"P");
    String prpDsc = (String)mprp.get(lprp+"D");
    String prpTyp = util.nvl((String)mprp.get(lprp+"T"));
    String prpFld = "value("+lprp+")";
    String chksrvAll = "chksrvAll('"+lprp+"')";
     String chksrvTxtAll = "chksrvTXTAll('"+lprp+"')";
    String selectALL = lprp;
    
    %>
    <th><%if(prpTyp.equals("C")){%>
     <html:select  property="<%=prpFld%>" styleId="<%=selectALL%>" name="labIssueForm" onchange="<%=chksrvAll%>"  style="width:100px"   > 
      <html:option value="0">--select--</html:option>
            <%
            for(int k=0; k < prpVal.size(); k++) {
                String pPrt = (String)prpVal.get(k);
                if(lprp.equals("SERVICE")){
                if(pPrt.indexOf("G.")!=-1) {%>
                     <html:option value="<%=pPrt%>" ><%=prpPrt.get(k)%></html:option> 
             <%}%>
                     <%}else{%>
                      <html:option value="<%=pPrt%>" ><%=prpPrt.get(k)%></html:option> <%}%>
                 <%}%>
          
     </html:select>  
     <%}else{%>
     <html:text property="<%=prpFld%>" styleId="<%=selectALL%>" name="labIssueForm"  style="width:100px" onchange="<%=chksrvTxtAll%>" />
     <%}%>
     
     
     <%=prpDsc%>
     </th>
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
sr = i+1;
String checkFldId = "CHK_"+sr;
String checkFldVal = "value("+stkIdn+")";
String serviceVal = "value(svc_"+stkIdn+")";
String serviceId = "svc_"+stkIdn;
String cts = (String)stockPkt.getValue("cts");
String amt = util.nvl((String)stockPkt.getValue("amt")).trim();
String rapvlu = util.nvl((String)stockPkt.getValue("rapvlu")).trim();
String onclick = "GenSel(this,'"+stkIdn+"')";
%>
<tr>
<td><html:checkbox property="<%=checkFldVal%>" styleId="<%=checkFldId%>" name="labIssueForm" onclick="<%=onclick%>" value="yes" /> </td>
<td><%=sr%></td>
<td><%=stockPkt.getValue("vnm")%></td>
<%for(int j=0; j < vwPrpLst.size(); j++ ){
String prp = (String)vwPrpLst.get(j);
if(prpDspBlocked.contains(prp)){
       }else{
%>
<td><%=stockPkt.getValue(prp)%></td>
<%

}}%>
<%for(int s=0; s < issEditPrp.size();s++){
String lprp = (String)issEditPrp.get(s);
String prpTyp = util.nvl((String)mprp.get(lprp+"T"));
ArrayList prpVal = (ArrayList)prpLst.get(lprp+"V");
ArrayList prpDsc = (ArrayList)prpLst.get(lprp+"P");
String fldVal = "value("+lprp+"_"+stkIdn+")";
String fldId = lprp+"_"+stkIdn;
%>
<td>
<%if(prpTyp.equals("C")){%>
<html:select property="<%=fldVal%>" styleId="<%=fldId%>" name="labIssueForm" style="width:100px">
<html:option value="0" >--select--</html:option>
<%for(int p=0;p<prpVal.size();p++){
String val = (String)prpVal.get(p);
if(lprp.equals("SERVICE")){
if(val.indexOf("G.")!=-1){%>
<html:option value="<%=val%>" ><%=prpDsc.get(p)%></html:option>
<%}%>
<%}else{%>
 <html:option value="<%=val%>" ><%=prpDsc.get(p)%></html:option><%}%>
<%}%></html:select>
<%}else{%>
<html:text property="<%=fldVal%>" styleId="<%=fldId%>" style="width:100px" name="labIssueForm" />
<%}%>
</td>
<%}%>
</tr>
<%}%>
</table></td></tr>
<input type="hidden" name="countcnt" id="countcnt" value="<%=sr%>" />



</html:form>
<%}else{%>
<tr><td>
<div style="margin-left:30px;">
Sorry Result not found
</div></td></tr>
<%}}%>





</table></td></tr>
<tr>
<td><jsp:include page="/footer.jsp" /> </td>
</tr>
</table>
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
		
	//device.connect();
</script>
</body>
</html>