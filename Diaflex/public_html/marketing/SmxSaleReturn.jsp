<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import="java.util.ArrayList,java.util.HashMap,java.sql.ResultSet,ft.com.*, java.util.Date, java.util.ArrayList, ft.com.dao.*"%>
<jsp:useBean id="info" class="ft.com.InfoMgr" scope="session" />
<jsp:useBean id="util" class="ft.com.JspUtil" scope="page" />
<%@taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>
<%@taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>Smx Sale Return</title>
      <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/bse.css?v=<%=info.getJsVersion()%>"/>
      <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/style.css?v=<%=info.getJsVersion()%>"/>
      <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/general.css?v=<%=info.getJsVersion()%>"/>
      <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/bse.js?v=<%=info.getJsVersion()%> " > </script>
          <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/assortScript.js?v=<%=info.getJsVersion()%> " > </script>
          <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/Validation.js?v=<%=info.getJsVersion()%> " > </script>

      <script language="javaScript" type="text/javascript" src="<%=info.getReqUrl()%>/calendar/calendar.js"></script>
      <link href="<%=info.getReqUrl()%>/calendar/calendar.css" rel="stylesheet" type="text/css" />
      <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/memoScript.js?v=<%=info.getJsVersion()%> " > </script>
     <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/ajax.js?v=<%=info.getJsVersion()%> " > </script>
 </head>
<%  
   int accessidn=Integer.parseInt(util.nvl((String)request.getAttribute("accessidn"),"0"));
    String logId=String.valueOf(info.getLogId());
     String onfocus="cook('"+logId+"')";
           %>
 <body onfocus="<%=onfocus%>" onload="MM_preloadImages('images/faunalogo.png')" topmargin="0" leftmargin="0" rightmargin="0" bottommargin="0">
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
  <img src="../images/line.jpg" border="0" /></td><td valign="middle"> <span class="pgHed">SMX Sale Return</span> </td>
</tr></table>
  </td>
  </tr>
  <%
  String msg = (String)request.getAttribute("msg");
  if(msg!=null){%>
  <tr><td valign="top" class="tdLayout"><span class="redLabel" ><%=msg%></span></td></tr>
  <%}%>
   <tr><td valign="top" class="tdLayout">
   <html:form action="/marketing/smxSaleReturnAction.do?method=View" method="POST" >
  <table><tr><td valign="top">
   <table class="grid1">
   <tr><th colspan="2"> Memo Search</th></tr>
   <tr>
<td>Buyer</td>
<td>
<html:select property="value(byrIdn)" styleId="byrId" name="smxSaleReturnForm" onchange="getTrms(this,'SALE')" >
<html:option value="0">---select---</html:option>
<html:optionsCollection property="byrLst" value="byrIdn" name="smxSaleReturnForm" label="byrNme" />

</html:select>
</td> </tr>
<tr>
<td> <span class="txtBold" >Terms </span></td><td>

<html:select property="value(byrTrm)" styleId="rlnId" name="smxSaleReturnForm" onchange="GetMemoSMXSALE()">
<html:option value="0">---select---</html:option>

</html:select>

</td>
</tr>
<tr><td>Memo Ids</td><td><html:text property="value(memoIdn)" name="smxSaleReturnForm" styleId="memoid"/></td></tr>
<tr><td colspan="2"> <html:submit property="value(submit)" value="View" styleClass="submit"/></td></tr>
   </table>
   </td><td valign="top"><div id="memoIdn"></div></td></tr></table>
   </html:form>
   </td></tr>
   
   <tr><td valign="top" class="tdLayout">
   <html:form action="/marketing/smxSaleReturnAction.do?method=Save" method="POST" onsubmit="return sumbitFormConfirm('RT_','1','Do you want to save changes','Please Select Packets for return','radio')" >
   <table cellspacing="1" cellpadding="1">
   
  <%
   ArrayList summryDtl = (ArrayList)request.getAttribute("SUMMRYPKTDTL");  
   if(summryDtl!=null && summryDtl.size()>0){%>
    <tr><td><b>Packet Summary :-</b></td></tr>
    <tr><td>
   <table class="grid1">
   <tr><th>Box Type</th><th>Box Id</th><th>Date </th><th>Qty </th><th>Cts</th><th>Rate</th></tr>
  <% for(int i=0;i<summryDtl.size();i++){
   HashMap pktDtl = (HashMap)summryDtl.get(i);
   %>
   <tr>
   <td><%=pktDtl.get("BOXTYP")%></td><td><%=pktDtl.get("BOXID")%></td><td><%=pktDtl.get("DTE")%></td>
      <td><%=pktDtl.get("QTY")%></td><td><%=pktDtl.get("CTS")%></td><td><%=pktDtl.get("RTE")%></td>

   </tr>
   <%}%>
   </table></td></tr>
   <%}else{%>
    <tr><td>Sorry no result found..</td></tr>
   <%}%>
   
    <%
   ArrayList PktDtlList = (ArrayList)request.getAttribute("PktDtlList"); 
   int cnt=0;
   if(PktDtlList!=null && PktDtlList.size()>0){%>
    
    <tr><td><b>Packet Details :-</b></td></tr>
    <tr><td> <html:submit property="value(submit)" value="Return" styleClass="submit"/>
    </td></tr>
    <tr><td>
   <table class="grid1">
   <tr><th><html:radio property="value(slRd)" styleId="noneRd"  onclick="ALLRedio('noneRd' ,'NONE_')"  value="NONE"/>&nbsp;&nbsp;None </th>
   <th><html:radio property="value(slRd)" styleId="rtRd"  onclick="ALLRedio('rtRd' ,'RT_')"  value="RT"/>&nbsp;&nbsp;Return</th><th>Memo ID</th><th>Date </th><th>Packet Code</th><th>Box Type</th><th>Box Id</th><th>Qty </th><th>Cts</th><th>Rate</th></tr>
  <% 
  for(int i=0;i<PktDtlList.size();i++){
  cnt=cnt+1;
   HashMap pktDtl = (HashMap)PktDtlList.get(i);
   String stkIdn = (String)pktDtl.get("STKIDN");
     String rdRTId = "RT_"+cnt;
     String rdRTFld="value(STT_"+stkIdn+")";
     String rdNONEId = "NONE_"+cnt;
       String rdNoneFld="value(STT_"+stkIdn+")";
                    
   %>
   <tr> 
    <td><html:radio property="<%=rdNoneFld%>" styleId="<%=rdNONEId%>" name="smxSaleReturnForm"  value="NONE"/></td>
    <td><html:radio property="<%=rdRTFld%>" styleId="<%=rdRTId%>" name="smxSaleReturnForm"  value="RT"/></td>
   <td><%=pktDtl.get("IDN")%></td> <td><%=pktDtl.get("DTE")%></td> <td><%=pktDtl.get("VNM")%></td>
   <td><%=pktDtl.get("BOXTYP")%></td><td><%=pktDtl.get("BOXID")%></td>
      <td><%=pktDtl.get("QTY")%></td><td><%=pktDtl.get("CTS")%></td><td><%=pktDtl.get("RTE")%></td>

   </tr>
   <%}%>
   
   </table>
   <input type="hidden" id="rdCount" name="rdCount" value="<%=cnt%>" />
   </td></tr>
   <%}else{%>
    <tr><td>Sorry no result found..</td></tr>
   <%}%>
   </table>
   </html:form>
   
   
   </td></tr>
    <tr><td>
    <jsp:include page="/footer.jsp" />
    </td></tr>
    </table>
    </body>
</html>