<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
 
<%@ page errorPage="../error_page.jsp" %>
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import="java.util.ArrayList,java.util.HashMap,java.sql.ResultSet,ft.com.*, java.util.Date, java.util.ArrayList, ft.com.dao.*"%>
<jsp:useBean id="util" class="ft.com.JspUtil" scope="page" />
<jsp:useBean id="info" class="ft.com.InfoMgr" scope="session" />
<%@taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>
<%@taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>


<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>MemoReturn</title>
       <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/assortScript.js?v=<%=info.getJsVersion()%> " > </script>
           <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/bse.css?v=<%=info.getJsVersion()%>"/>
      <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/style.css?v=<%=info.getJsVersion()%>"/>
       <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/general.css?v=<%=info.getJsVersion()%>"/>
       <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/ajax.js?v=<%=info.getJsVersion()%> " > </script>
       <script type="text/javascript" src="<%=info.getReqUrl()%>/PopScripts/jquery-1.2.6.min.js" > </script>
        <script type="text/javascript" src="<%=info.getReqUrl()%>/PopScripts/popup.js?v=<%=info.getJsVersion()%> " > </script>
       <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/bse.js?v=<%=info.getJsVersion()%> " > </script>
          <script language="javaScript" type="text/javascript" src="<%=info.getReqUrl()%>/calendar/calendar.js"></script>
   <link href="<%=info.getReqUrl()%>/calendar/calendar.css" rel="stylesheet" type="text/css" />
          <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/priceChange.js?v=<%=info.getJsVersion()%> " > </script>
 <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/memoScript.js?v=<%=info.getJsVersion()%> " > </script>
       <script src="<%=info.getReqUrl()%>/scripts/spacecode.js?v=<%=info.getJsVersion()%>"></script>
       <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/dispose.js?v=<%=info.getJsVersion()%> " > </script>
  </head>
<%
        String logId=String.valueOf(info.getLogId());
        String onfocus="cook('"+logId+"')";
        %>
 <body onfocus="<%=onfocus%>" onload="MM_preloadImages('images/faunalogo.png')" topmargin="0" leftmargin="0" rightmargin="0" bottommargin="0">
   <input type="hidden" id="deviceip" name="deviceip" value="<%=util.nvl((String)info.getSpaceCodeIp())%>"/>
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
  <img src="../images/line.jpg" border="0" /></td><td valign="middle"> <span class="pgHed">Memo Return In Bulk</span> </td>
</tr></table>
  </td>
  </tr>
  <%String msg = (String)request.getAttribute("msg");
  if(msg!=null){
  %>
  <tr><td valign="top" class="tdLayout"> <span class="redLabel" > <%=msg%></span></td></tr>
  <%}%>
  <%
   String vnmNtFnd = (String)request.getAttribute("vnmNotFnd");
     if(vnmNtFnd!=null){%>
    <tr><td valign="top" class="tdLayout"><span class="redLabel"><%=vnmNtFnd%></span></td></tr>
   <%}%>
   <%
  String rtnPtk = (String)request.getAttribute("rtnPtk");
  if(rtnPtk!=null){%>
  <tr><td valign="top" class="tdLayout"><span class="redLabel"><%=rtnPtk%></span></td></tr>
  <%}%>
  <tr><td valign="top" class="tdLayout">
   <html:form action="/marketing/memoBulkReturn.do?method=load"  method="POST">
  <table><tr><td valign="top">
 
  <html:hidden property="value(listname)" styleId="listname" value="vnmLst" />
    <html:hidden property="value(chkTyp)" styleId="chkTyp" value="checkbox" />

        <table class="grid1">
        <tr><th colspan="2">Memo Search </th></tr>
            <tr>
           <td>Buyer</td>
                <td>
               <html:select property="value(byrIdn)" styleId="byrId" multiple="true" size="5" style="height:100px" onclick="GetTypMemoReturnRadioIdn()" >
                  <html:option value="0">---select---</html:option>
                 <html:optionsCollection property="byrLstFetch" value="byrIdn" label="byrNme" /></html:select>
             </td> </tr>
            <tr>
            <td>Packets. </td><td>
            <html:textarea property="value(vnmLst)" cols="30" styleId="vnmLst"  onkeyup="getVnmCount('vnmLst','vnmLstCnt')" onchange="getVnmCount('vnmLst','vnmLstCnt')" name="memoReturnBulkForm" /><br><span id="vnmLstCnt">0</span>
            <label id="fldCtr" ></label>
            </td>
            </tr>
        </table>
        <p><html:submit property="submit" value="View"  styleClass="submit"/> 
        <%
 ArrayList rfiddevice = ((ArrayList)info.getRfiddevice() == null)?new ArrayList():(ArrayList)info.getRfiddevice();
 if(rfiddevice!=null && rfiddevice.size()>0){
 %>
 RFID Device: <html:select property="value(dvcLst)" styleId="dvcLst" name="memoReturnBulkForm"  >
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
 <html:button property="value(rfScan)" value="RF ID Scan" styleId="rfScan" onclick="doScan('vnmLst','fldCtr','dvcLst','SCAN')"  styleClass="submit" />
 <html:button property="value(autorfScan)" value="Auto Scan" styleId="autorfScan" onclick="doScan('vnmLst','fldCtr','dvcLst','AUTOSCAN')"  styleClass="submit" />
<%}%>
 <html:button property="value(stopAutorfScan)" value="Stop Auto Scan" onclick="doScan('vnmLst','fldCtr','dvcLst','STOPAUTOSCAN')"  styleClass="submit" />
 
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
             <%}%>      
        </p>
    </td><td valign="top">
    <div id="memoIdn"></div>
    </td></tr></table></html:form>
  </td>
  </tr>
  <tr><td valign="top" class="tdLayout">
    <html:form action="/marketing/memoBulkReturn.do?method=save" method="POST" onsubmit="return confirmChangesWithPopup()" >
    <html:hidden property="nmeIdn" name="memoReturnBulkForm" />
     <html:hidden property="relIdn" name="memoReturnBulkForm" />
     <html:hidden property="typ" name="memoReturnBulkForm" />
      <html:hidden property="value(exh_rte)" name="memoReturnBulkForm" />
    
  <%
  String view = (String)request.getAttribute("view");
  ArrayList prps = (info.getValue("MDL_MEMO_RTRN") == null)?new ArrayList():(ArrayList)info.getValue("MDL_MEMO_RTRN");
  if(view!=null){
  ArrayList prpDspBlocked = info.getPageblockList();
  ArrayList memoList = (ArrayList)session.getAttribute("memoList");
  HashMap memoPkt = (HashMap)session.getAttribute("pktMemoMap");
  Integer byrCount = (Integer)request.getAttribute("byrcont");
  HashMap byrDtl = (HashMap)request.getAttribute("byrDtl");
  boolean isApdisabled = true;
  if(byrCount==0)
  isApdisabled = false;
  %>
 
  
   
  <%
  if(memoList!=null && memoList.size()>0){%>
  <table>
    <tr>
    <td> <html:submit property="submit" value="Save" styleClass="submit"/></td>
    <td><html:button property="value(fullIssue)" value="Full Issue" onclick="ALLRedioBill('ST');" styleClass="submit"/>&nbsp;</td>
    <td><html:button property="value(fullReturn)" value="Full Return" onclick="ALLRedioBill('RT');" styleClass="submit"/>&nbsp;</td>
    <td><html:button property="value(fullApprove)" value="Full Approve" onclick="ALLRedioBill('AP');" styleClass="submit"/>&nbsp;</td>
    <td>  <b>Remark/Comment</b>&nbsp;<html:text property="value(rmk)" size="15"  /></td>
    <%ArrayList notepersonList = ((ArrayList)info.getNoteperson() == null)?new ArrayList():(ArrayList)info.getNoteperson();
    if(notepersonList.size()>0){%>
    <td>Note Person:</td>
    <td>
      <html:select name="memoReturnBulkForm" property="value(noteperson)" styleId="noteperson">
      <html:option value="">---Select---</html:option><%
      for(int i=0;i<notepersonList.size();i++){
      ArrayList noteperson=(ArrayList)notepersonList.get(i);%>
      <html:option value="<%=(String)noteperson.get(0)%>"> <%=(String)noteperson.get(1)%> </html:option>
      <%}%>
      </html:select>
</td>
<%}%>
    </tr>
    </table>
   <table>
  <%
  for(int k=0 ; k < memoList.size();k++){
  String memoIdn = (String)memoList.get(k);
  String isRd = "ALLRedioBK('isRd' ,'ST_','"+memoIdn+"')";
  String rtRd = "ALLRedioBK('rtRd' ,'RT_','"+memoIdn+"')";
  String apRd = "ALLRedioBK('apRd' ,'AP_','"+memoIdn+"')";
  String isRdId = "isRd_"+memoIdn;
  String rtRdId = "rtRd_"+memoIdn;
  String apRdId = "apRd_"+memoIdn;
  HashMap byrObj= ((HashMap)byrDtl.get(memoIdn) == null)?new HashMap():(HashMap)byrDtl.get(memoIdn);
  %>
  <tr><td><label class="pgTtl">Memo Packets : <%=memoIdn%></label> &nbsp;&nbsp;
  <label class="pgTtl">Buyer : <%=util.nvl((String)byrObj.get("byr"))%></label>&nbsp;&nbsp;
  <label class="pgTtl">Qty : <%=util.nvl((String)byrObj.get("qty"))%></label>&nbsp;&nbsp;
  <label class="pgTtl">Cts : <%=util.nvl((String)byrObj.get("cts"))%></label>&nbsp;&nbsp;
  <label class="pgTtl">Type : <%=util.nvl((String)byrObj.get("typ"))%></label>
  </td></tr>

  <tr><td>
     <table class="grid1">
                <tr>
                    <th>Sr</th>
                     <th><html:radio property="value(slRd)"  styleId="<%=isRdId%>"  onclick="<%=isRd%>"  value="ST"/>&nbsp;Issue</th>
                    <th><html:radio property="value(slRd)" styleId="<%=rtRdId%>"  onclick="<%=rtRd%>"  value="RT"/>&nbsp;Return</th>
                    <th><html:radio property="value(slRd)" styleId="<%=apRdId%>" onclick="<%=apRd%>" disabled="<%=isApdisabled%>" value="AP"/>&nbsp;Approve</th>
                   <th>Packet Code</th>
                    <%for(int j=0; j < prps.size(); j++) {
                     String lprp=(String)prps.get(j);
                     if(prpDspBlocked.contains(lprp)){
                     }else{
                     %>
                     <th><%=lprp%></th>
                    <%}}%>
                    <th>RapRte</th>
                    <th>RapVlu</th>
                    <th>Dis</th>
                    <th>Prc / Crt </th>
                    <th>ByrDis</th>
                    <th>Quot</th>                
                   
                </tr>
            <%
                ArrayList pkts =(ArrayList)memoPkt.get(memoIdn);
                //(info.getValue(reqIdn+ "_PKTS") == null)?new ArrayList():(ArrayList)info.getValue(reqIdn+ "_PKTS");
               int count = 0;
                for(int i=0; i < pkts.size(); i++) {%>
                <tr>
                <td><%=(i+1)%></td>
                <%    
                   count=i+1;
                    PktDtl pkt = (PktDtl)pkts.get(i);
                    long pktIdn = pkt.getPktIdn();
                  
                    String sttPrp = "value(stt_"+memoIdn+"_" + pktIdn + ")" ;
                    String rmkTxt =  "value(rmk_" + pktIdn + ")" ;
                    String rdSTId = "ST_"+count+"_"+memoIdn;
                    String rdRTId = "RT_"+count+"_"+memoIdn;
                    String rdAPId = "AP_"+count+"_"+memoIdn;
                   
                %>
                <td><html:radio property="<%=sttPrp%>" styleId="<%=rdSTId%>" name="memoReturnBulkForm" value="IS"/></td>
                <td><html:radio property="<%=sttPrp%>" styleId="<%=rdRTId%>" name="memoReturnBulkForm" value="RT"/> <html:text property="<%=rmkTxt%>" size="10"  /> </td>
                <td><html:radio property="<%=sttPrp%>" styleId="<%=rdAPId%>" name="memoReturnBulkForm" disabled="<%=isApdisabled%>" value="AP"/></td>
                <td><%=pkt.getVnm()%></td>
               
                <%for(int j=0; j < prps.size(); j++) {
                  String lprp=(String)prps.get(j);
                  if(prpDspBlocked.contains(lprp)){
                     }else{%>
                    <td><%=util.nvl(pkt.getValue(lprp))%></td>
                <%}}%>
                
                <td><%=pkt.getRapRte()%></td>
                <td><%=util.nvl(pkt.getValue("rapVlu"))%></td>
                <td><%=pkt.getDis()%></td>
                <td><%=pkt.getRte()%></td>
                <td><%=pkt.getByrDis()%></td>
                <td><%=pkt.getMemoQuot()%></td>
                 </tr>
              <%  
                }
                
            %>
          
            
            </table></td></tr>
          
    <%}%>
  </table>
  <%}else{%>
  Sorry no result found
 <% }
  }
  %>
 </html:form>
  </td></tr>
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
  </body></html>