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
    <title>Light On Screen</title>
    <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/bse.css?v=<%=info.getJsVersion()%>"/>
    <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/general.css?v=<%=info.getJsVersion()%>"/>
     <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/style.css?v=<%=info.getJsVersion()%>"/>
      <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/bse.js?v=<%=info.getJsVersion()%> " > </script>
       <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/assortScript.js?v=<%=info.getJsVersion()%> " > </script>
       <script src="<%=info.getReqUrl()%>/scripts/spacecode.js?v=<%=info.getJsVersion()%>"></script>
  </head>
        <%String logId=String.valueOf(info.getLogId());
        String onfocus="cook('"+logId+"')";
        %>
 <body onfocus="<%=onfocus%>" onload="MM_preloadImages('images/faunalogo.png')" topmargin="0" leftmargin="0" rightmargin="0" bottommargin="0">
  <input type="hidden" id="deviceip" name="deviceip" value="<%=util.nvl((String)info.getSpaceCodeIp())%>"/>
<%
 HashMap allPageDtl= (info.getPageDetails() == null)?new HashMap():(HashMap)info.getPageDetails();
    HashMap pageDtl=(HashMap)allPageDtl.get("LIGHT_SCREEN");
    ArrayList lightPktLst= (request.getAttribute("lightPktLst") == null)?new ArrayList():(ArrayList)request.getAttribute("lightPktLst");
    String lightrfids="";
    if(lightPktLst.size()>0){
    lightrfids = lightPktLst.toString();
    lightrfids = lightrfids.replaceAll("\\[","");
    lightrfids = lightrfids.replaceAll("\\]","");
    lightrfids = lightrfids.replaceAll(" ","");
    }
    ArrayList pageList=new ArrayList();
    HashMap pageDtlMap=new HashMap();
    String fld_nme="",lov_qry="",fld_ttl="",val_cond="",dflt_val="",fld_typ="",form_nme="";
    pageList= ((ArrayList)pageDtl.get("STATUS") == null)?new ArrayList():(ArrayList)pageDtl.get("STATUS");
         if(pageList!=null && pageList.size() >0){
           pageDtlMap=(HashMap)pageList.get(0);
          form_nme=(String)pageDtlMap.get("dflt_val");
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
  <img src="../images/line.jpg" border="0" /></td><td valign="middle"> <span class="pgHed">Light On Screen</span> </td>
</tr></table>
  </td>
  </tr>
  <tr><td valign="top" class="hedPg">
  <html:form action="report/lightscreen.do?method=fetch" method="post">
  <html:hidden property="value(listname)" styleId="listname" value="pid" />
<table class="grid1">
   <tr><th>Generic Search
   <%
  ArrayList rolenmLst=(ArrayList)info.getRoleLst();
  String usrFlg=util.nvl((String)info.getUsrFlg());
  if(usrFlg.equals("SYS") || rolenmLst.contains("ADMIN")){
  %>
   <img src="../images/edit.jpg" title="Edit Properties Module" onclick="newWindow('<%=info.getReqUrl()%>/dynamicProperty.do?method=load&mdl=LIGHT_SRCH&sname=LIGHT_SRCH&par=GENERIC')" border="0" width="15px" height="15px"/> 
  <%}%>
   </th><th></th></tr>
  <tr>
  <td valign="top"><jsp:include page="/genericSrch.jsp" /> </td>
  <td colspan="2" valign="top" nowrap="nowrap">
            <%pageList=(ArrayList)pageDtl.get("RADIO");
            if(pageList!=null && pageList.size() >0){
            for(int j=0;j<pageList.size();j++){
            pageDtlMap=(HashMap)pageList.get(j);
            fld_nme="value("+(String)pageDtlMap.get("fld_nme")+")";
            fld_ttl=(String)pageDtlMap.get("fld_ttl");
            fld_typ=(String)pageDtlMap.get("fld_typ");
            String onclk="displayTypAllCur('"+fld_typ+"')";
            dflt_val=(String)pageDtlMap.get("dflt_val");
            val_cond=(String)pageDtlMap.get("val_cond"); %>
            <html:radio property="<%=fld_nme%>" styleId="<%=fld_typ%>" value="<%=dflt_val%>" onclick="<%=onclk%>"/><%=fld_ttl%>
            <%}}%>
            <html:radio property="value(srchRef)"  styleId="None" value="NONE"  onclick="displayTypAllCur('None');" /> None.
    <span style="display:none;" id="allcurrent">
     Data Type
    <html:radio property="value(typ)"  styleId="ALL" value="ALL" /> All.
    <html:radio property="value(typ)"  styleId="Current" value="Current" /> Current.
    </span>
 <table>
<tr>
<td>Idn's: </td>
<td><html:textarea property="value(idns)" name="lightScreenForm" cols="50" rows="3" styleId="idns"/></td>
</tr>
 <%if(!lightrfids.equals("")){%>
 <tr>
<td>Light On Rfid: </td>
<td><html:textarea property="value(lightonrfid)" name="lightScreenForm" cols="50" rows="3" value="<%=lightrfids%>" styleId="lightonrfid"/></td>
</tr>
<%}%>
<%if(!lightrfids.equals("")){%>
 <tr>
<td>Packet Scan Rfid: </td>
<td><html:textarea property="value(vnm)" name="lightScreenForm" cols="50" rows="3" styleId="pid"/></td>
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
                        <!--<html:button property="value(myButton2)" value="Dispose" styleId="myButton2" onclick="dispose()"  styleClass="submit" />-->
                        <html:button property="value(myButton4)" value="LED On" styleId="myButton4" onclick="ligtOn('lightonrfid')"  styleClass="submit" />
                        <span id="loadingrfid"></span>
                        <!--<html:button property="value(myButton5)" value="LED Off" styleId="myButton5" onclick="ligtOff()"  styleClass="submit" />-->
                        </td>
                    </tr>
            </table>
        </td>
    </tr>
    <%}%>
    <%}%>
 </table>
 </td>
  </tr>
   <tr> 
  <td valign="top" align="center">Status :<html:textarea property="value(stt)" cols="40" rows="2" value="<%=form_nme%>" name="lightScreenForm" /> </td>
  <td>(Add Multiple status with comma separated)</td>
  </tr>
<tr>
<td colspan="2" align="center">
<%if(lightrfids.equals("")){%>
<html:submit property="value(fetch)" value="Search Packet" styleClass="submit" />
<%}%>
<html:button property="value(reload)" value="Reload" styleClass="submit" onclick="goTo('lightscreen.do?method=load','','')"/>
</td>
</tr>
  </table>
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
  </body>
</html>