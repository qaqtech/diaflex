<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page errorPage="../error_page.jsp" %>
<%@ page contentType="text/html;charset=windows-1252"%>
<jsp:useBean id="info" class="ft.com.InfoMgr" scope="session" />
<jsp:useBean id="util" class="ft.com.JspUtil" scope="page" />
<%@ page import="java.util.ArrayList,java.util.HashMap,java.sql.ResultSet,ft.com.*, java.util.Date, java.util.ArrayList, ft.com.dao.*"%>
<%@taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
 
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>Discover Report</title>
    <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/bse.css?v=<%=info.getJsVersion()%>"/>
    <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/general.css?v=<%=info.getJsVersion()%>"/>
     <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/style.css?v=<%=info.getJsVersion()%>"/>
      <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/bse.js?v=<%=info.getJsVersion()%> " > </script>
      <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/ajax.js?v=<%=info.getJsVersion()%> " > </script>
       <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/assortScript.js?v=<%=info.getJsVersion()%> " > </script>
       <script src="<%=info.getReqUrl()%>/scripts/spacecode.js?v=<%=info.getJsVersion()%>"></script>
       <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/dispose.js?v=<%=info.getJsVersion()%> " > </script>
  
  
  
  </head>
  
  
        <%String logId=String.valueOf(info.getLogId());
        String onfocus="cook('"+logId+"')";
         ArrayList  pktList =  (ArrayList)session.getAttribute("PktList");
        %>
 <body onfocus="<%=onfocus%>" onload="MM_preloadImages('images/faunalogo.png')" topmargin="0" leftmargin="0" rightmargin="0" bottommargin="0">
  <input type="hidden" id="deviceip" name="deviceip" value="<%=util.nvl((String)info.getSpaceCodeIp())%>"/>
<%
 HashMap allPageDtl= (info.getPageDetails() == null)?new HashMap():(HashMap)info.getPageDetails();
    HashMap pageDtl=(HashMap)allPageDtl.get("DISCOVER");
    ArrayList pageList=new ArrayList();
    HashMap pageDtlMap=new HashMap();
    String fld_nme="",lov_qry="",fld_ttl="",val_cond="",dflt_val="",fld_typ="",form_nme="";
    pageList= ((ArrayList)pageDtl.get("STATUS") == null)?new ArrayList():(ArrayList)pageDtl.get("STATUS");
    String dfltstt="";
         if(pageList!=null && pageList.size() >0){
           pageDtlMap=(HashMap)pageList.get(0);
          dfltstt=(String)pageDtlMap.get("dflt_val");
          }
%>

<table width="100%"   align="center" cellpadding="0" cellspacing="0" class="mainbg">
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
  <img src="../images/line.jpg" border="0" /></td><td valign="middle"> <span class="pgHed">Discover Report</span> </td>
</tr></table>
  </td>
  </tr>
  <tr><td valign="top" class="hedPg">
  <html:form action="/quickReport/discoverReport.do?method=FECTHMG" method="post" onsubmit="return validate_assort()">
  <html:hidden property="value(listname)" styleId="listname" value="pid" />
<table class="grid1">
   <tr><th>Generic Search
   <%
  ArrayList rolenmLst=(ArrayList)info.getRoleLst();
  String usrFlg=util.nvl((String)info.getUsrFlg());
  if(usrFlg.equals("SYS") || rolenmLst.contains("ADMIN")){
  %>
   <img src="../images/edit.jpg" title="Edit Properties Module" onclick="newWindow('<%=info.getReqUrl()%>/dynamicProperty.do?method=load&mdl=DIS_SRCH&sname=DISGNCSrch&par=GENERIC')" border="0" width="15px" height="15px"/> 
  <%}%>
   </th><th></th></tr>
  <tr>
  <td><jsp:include page="/genericSrch.jsp" /> </td>
  <td colspan="2" valign="top">
 <html:radio property="value(srchRef)"  styleId="vnm" value="S" /> Packet Code/Rfid No.
 <html:radio property="value(srchRef)"  styleId="vnm" value="C" /> Cert No.
 <table>
<tr>
<td>Packet Id: </td>
<td><html:textarea property="value(vnm)" name="discoverReportFormMG" cols="40" rows="8" styleId="pid"/></td>
</tr>
<tr>
<td>Packet Type</td>
<td>
<html:select property="value(pkt_ty)" name="discoverReportFormMG">
   <%    pageList= ((ArrayList)pageDtl.get("PKT_TY") == null)?new ArrayList():(ArrayList)pageDtl.get("PKT_TY");
         if(pageList!=null && pageList.size() >0){
         for(int k=0;k<pageList.size();k++){
         pageDtlMap=(HashMap)pageList.get(k);
         dflt_val=(String)pageDtlMap.get("dflt_val");
         fld_ttl=(String)pageDtlMap.get("fld_ttl");
         %>
         <html:option value="<%=dflt_val%>"><%=fld_ttl%></html:option>  
         <%}}%>
</html:select>
</td>
</tr>
<%if(util.nvl((String)info.getDmbsInfoLst().get("RFID_DNK"),"N").equals("Y")){%>
    <tr>
        <td colspan="2">
            <table class="grid1">
                    <tr>
                    <td>Continue Scan <input type="checkbox" id="contscan" title="Select To Continue Scan"></input></td>
                        <td>
                        <div style="margin:0px; padding:0px; display:none;">
                        <label id="summary" style="display:none;"></label>&nbsp;&nbsp;
                        </div>
                        Count&nbsp;&nbsp;:&nbsp;<label id="count"></label>&nbsp;&nbsp;
                        <div style="margin:0px; padding:0px; display:none;">
                        Notify&nbsp;&nbsp;:&nbsp;<label id="notify"></label>
                        </div>
                        </td>
                    </tr>
                    <tr>
                        <td>
                        <input type="checkbox" id="accumulateMode" title="Accumulate Mode"></input>
                        <html:button property="value(myButton)" value="Scan" styleId="myButton" onclick="scanRf()"  styleClass="submit" />
                        <span id="loadingrfid"></span>
                        <!--<html:button property="value(myButton2)" value="Dispose" styleId="myButton2" onclick="dispose()"  styleClass="submit" />
                        <html:button property="value(myButton4)" value="LED On" styleId="myButton4" onclick="waitOn('pid')"  styleClass="submit" />
                        <html:button property="value(myButton5)" value="LED Off" styleId="myButton5" onclick="waitOff()"  styleClass="submit" />
                        <html:button property="value(myButton6)" value="TestDll" styleId="myButton6" onclick="testDll()"  styleClass="submit" />-->
                        </td>
                    </tr>
            </table>
        </td>
    </tr>
    <%}%>
 </table>
 </td>
  </tr>
   <%    pageList=(ArrayList)pageDtl.get("P1P2");
         if(pageList!=null && pageList.size() >0){
         for(int k=0;k<pageList.size();k++){
         pageDtlMap=(HashMap)pageList.get(k);
         fld_nme="value("+(String)pageDtlMap.get("fld_nme")+")";
         fld_ttl=(String)pageDtlMap.get("fld_ttl");
         val_cond=(String)pageDtlMap.get("val_cond");
    %>
   <tr>
    <td><%=fld_ttl%> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;P1
    <html:text property="value(dtefr)" styleClass="txtStyle"  styleId="dtefr"  size="12"  /> <a href="#" style="margin-top:5px;"  onClick="setYears(1900, '<%=info.getCurrentYear()%>' );showCalender(this, 'dtefr');">
                <img src="<%=info.getReqUrl()%>/images/calender.png"></a>
     P2 
    <html:text property="value(dteto)" styleClass="txtStyle"  styleId="dteto"  size="12"  /> <a href="#"  onClick="setYears(1900, '<%=info.getCurrentYear()%>' );showCalender(this, 'dteto');">
                <img src="<%=info.getReqUrl()%>/images/calender.png"></a>
    </td>
    </tr>
      <%}}%>
   <tr> 
  <td>
  <table id="all_grp">
  <tr>
<td>Group</td>
<td>
  <%
  ArrayList sttList = (ArrayList)session.getAttribute("sttLst");
  if(sttList!=null && sttList.size()>0){
  int counter=0;%>
  <table><tr>
 <% for(int n=0;n<sttList.size();n++){
  ArrayList sttDtl = (ArrayList)sttList.get(n);
  String grp1 = (String)sttDtl.get(0);
  String dsc = (String)sttDtl.get(1);
  String dscgrp1="value("+grp1+")";
  String chkid="STT_"+n;
  String onclick="getStatusbyGrpQuick('"+grp1+"','"+chkid+"')";
  if(counter==4){%>
  </tr><tr>
  <%}counter++;%>
  <td><html:checkbox property="<%=dscgrp1%>" styleId="<%=chkid%>" value="<%=grp1%>" name="discoverReportFormMG" onclick="<%=onclick%>"/></td><td><%=dsc%></td>
  <%}%>
  <td><span id="loading"></span></td>
  </tr>
</table>
<%}%>
</td>
</tr>
</table>
  </td>
    <td valign="top" align="center">Status :<html:textarea property="value(stt)" cols="40" rows="2" styleId="sttLst" value="<%=dfltstt%>" name="discoverReportFormMG" /> </td>
  </tr>
<tr>
<td colspan="2" align="center">
<!--<html:submit property="value(mfg)" value="Search Packet" styleClass="submit" />
<html:submit property="value(dwldExcel)" value="Create Excel" styleClass="submit" />-->
<%pageList=(ArrayList)pageDtl.get("SUBMIT");
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
    %> 
</td>
</tr>
  </table>
    </html:form>
   </td></tr>
 
 <% 
  String  view =  util.nvl((String)request.getAttribute("view"));
  if(!view.equals("")){
   ArrayList itemList = new ArrayList();
  ArrayList vwPrpLst = (ArrayList)session.getAttribute("DIS_VW");
 if(pktList!=null && pktList.size()>0){

HashMap prpDsc=new HashMap();
prpDsc.put("rap_val", "Rap Value");
prpDsc.put("total_val", "Total Value");
prpDsc.put("quot", "PriEsti");
prpDsc.put("sr", "SR NO.");
prpDsc.put("vnm", "Pkt Code");
prpDsc.put("stt", "Status");
prpDsc.put("CERT NO", "CERT NO");
prpDsc.put("flg", "Flg");
prpDsc.put("cst_val", "CstVlu");
prpDsc.put("net_vlu", "NetVlu");
prpDsc.put("ofr_rte", "Ofr Rte");
prpDsc.put("ofr_dis", "Ofr_dis");
//prpDsc.put("sl_dte", "SALE DTE");
HashMap totals = (HashMap)request.getAttribute("totalMap");
HashMap mprp = info.getMprp();
int sr = 0;
%>
<tr><td valign="top" class="hedPg">
<table >
<tr><td>Total :&nbsp;&nbsp;</td><td><span id="ttlqty"> <%=totals.get("qty")%>&nbsp;&nbsp;</span></td><td>cts&nbsp;&nbsp;</td><td><span id="ttlcts"><%=totals.get("cts")%>&nbsp;&nbsp;</span></td><td>Vlu&nbsp;&nbsp;</td><td><span id="ttlvlu"><%=totals.get("vlu")%>&nbsp;&nbsp;</span></td>
<%
ArrayList rolenmLst=(ArrayList)info.getRoleLst();
  String usrFlg=util.nvl((String)info.getUsrFlg());
 
  if(usrFlg.equals("SYS") || rolenmLst.contains("ADMIN")){
  itemList.add("sr"); itemList.add("vnm"); itemList.add("stt");itemList.add("qty");
  
  %>
  <td><img src="../images/edit.jpg" title="Edit Properties Module" onclick="newWindow('<%=info.getReqUrl()%>/dynamicProperty.do?method=load&mdl=DIS_VIEW&sname=DIS_VW&par=V')" border="0" width="15px" height="15px"/></td> 
  <%}%>
</tr>
</table>
</td></tr>
 <tr><td valign="top" class="hedPg">
  Create Excel &nbsp;&nbsp;&nbsp;
  <img src="../images/ico_file_excel.png" border="0" onclick="goTo('<%=info.getReqUrl()%>//quickReport/discoverReport.do?method=createXL','','')"/>
 
  </td></tr>
<tr><td valign="top" class="hedPg">

<table class="dataTable">
<thead>
<tr><th>Sr</th><th>Packet Code</th><th>Status</th><th>Qty</th>
<%for(int j=0; j < vwPrpLst.size(); j++ ){
   String prp = (String)vwPrpLst.get(j);
   
   String hdr = util.nvl((String)mprp.get(prp));
   if(hdr.equals(""))
   hdr = util.nvl((String)prpDsc.get(prp),prp);
    itemList.add(prp);
   %>
 <th><%=hdr%></th>
 <%if(prp.equals("RTE")){
  itemList.add("total_val");
 %>
   <th>total_val</th>
 <%}%>
   <%if(prp.equals("RAP_RTE")){
    itemList.add("rap_val");
   %>
   <th>rap_val</th>
 <%}%>
  <%}
  session.setAttribute("itemHdr", itemList);
  %>
</tr>
</thead>
<tbody id="results">


</tbody>
</table></td></tr>
<tr><td valign="top" class="hedPg" align="left">
<img src="../images/loadbig.gif" class="load_image" />
</td></tr>
<%}else{%>
<tr><td  valign="top" class="hedPg">Sorry no result found.</td></tr>
<%}
}%> 
  
  <tr>
    <td><jsp:include page="/footer.jsp" /> </td>
    </tr>
  </table>
  <%  if(pktList!=null && pktList.size()>0){
  int size = pktList.size();
   HashMap dbSysInfo = info.getDmbsInfoLst();
   int groupRecord =Integer.parseInt(util.nvl((String)dbSysInfo.get("GROUPRECORD"),"50"));
  
     if(size < groupRecord)
     groupRecord=size;
    int group_no =  size/groupRecord;
    int reminder =size % groupRecord;
    if(reminder > 0)
      group_no=group_no+1;
      int startR = 0;
      int endR = groupRecord;
  %>
    <input type="hidden" name="startR" id="start" value="<%=startR%>" />
    <input type="hidden" name="endR" id="end" value="<%=endR%>" />
    <input type="hidden" name="groupRecord" id="groupRecord" value="<%=groupRecord%>" />
    <input type="hidden" name="group_no" id="group_no" value="<%=group_no%>" />
    <input type="hidden" name="ttlRecord" id="ttlRecord" value="<%=size%>" />
  <script type="text/javascript">
$(document).ready(function() {
	var track_load = 0; //total loaded record group(s)
	var loading  = false; //to prevents multipal ajax loads
      
	var total_groups = $('#group_no').val(); //total record group(s)
	var startR = $('#start').val();
        var endR = $('#end').val();
	$('#results').load("discoverResult.jsp", {'startR':startR,'endR':endR}, function() {++track_load;}); //load first group
	$('#start').val(parseInt(endR));
	$(window).scroll(function() { //detect page scroll
		
		if(($(window).scrollTop() + $(window).height())+20 >= $(document).height())  //user scrolled to bottom of the page?
		{
		
                        var startR = $('#start').val();
                        var groupRecord = $('#groupRecord').val();
                          var ttlRecord = $('#ttlRecord').val();
			if(track_load <= total_groups && loading==false) //there's more data to load
			{
                              var endR =parseInt(startR)+parseInt(groupRecord);
                             if(endR > ttlRecord)
                             endR =ttlRecord;
                             
				loading = true; //prevent further ajax loading
				//$('.load_image').show(); //show loading image
				
				//load data from the server using a HTTP POST request
				$.post("discoverResult.jsp", {'startR':startR,'endR':endR}, function(data){
								
					$("#results").append(data); //append received data into the element

					//hide loading image
					$('.load_image').hide(); //hide loading image once data is received
					
                                      
				     $('#start').val(parseInt(endR));
					track_load++; //loaded group increment
					loading = false; 
				
				}).fail(function(xhr, ajaxOptions, thrownError) { //any errors?
					
					alert(thrownError); //alert with HTTP error
					$('.load_image').hide(); //hide loading image
					loading = false;
				
				});
				
			}
		}
	});
});
</script><%}%>
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