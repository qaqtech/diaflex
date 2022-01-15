<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page errorPage="../error_page.jsp" %>
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.ResultSet, java.util.ArrayList,java.util.Collections,java.util.ArrayList,java.util.HashMap,java.text.SimpleDateFormat, java.util.Calendar"%>
<jsp:useBean id="info" class="ft.com.InfoMgr" scope="session" />
<jsp:useBean id="util" class="ft.com.JspUtil" scope="page" />
<%@taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>
<%@taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<html> 
   <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
   <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/bse.css?v=<%=info.getJsVersion()%>"/>
   <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/general.css?v=<%=info.getJsVersion()%>"/>
   <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/ajax.js?v=<%=info.getJsVersion()%> " > </script>
   <script type="text/javascript" src="<%=info.getReqUrl()%>/PopScripts/popup.js?v=<%=info.getJsVersion()%> " > </script>
   <script src="<%=info.getReqUrl()%>/PopScripts/jquery-1.2.6.min.js" type="text/javascript"></script>
   <script language="javaScript" type="text/javascript" src="<%=info.getReqUrl()%>/calendar/calendar.js"></script>
   <link href="<%=info.getReqUrl()%>/calendar/calendar.css" rel="stylesheet" type="text/css" />

 
      <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/style.css?v=<%=info.getJsVersion()%>"/>

  <title>Mix Profit/Loss Report</title>
 
  </head>
  
  <%
        HashMap dbinfo = info.getDmbsInfoLst();
        String cnt = (String)dbinfo.get("CNT");
        String logId=String.valueOf(info.getLogId());
        String onfocus="cook('"+logId+"')";
        %>
 <body onfocus="<%=onfocus%>" onload="MM_preloadImages('images/faunalogo.png')" topmargin="0" leftmargin="0" rightmargin="0" bottommargin="0">
<input type="hidden" id="reqUrl" name="reqUrl" value="<%=info.getReqUrl()%>"/>
<div id="backgroundPopup"></div>
<div id="popupContactASSFNL">
<table class="popUpTb">
<tr><td height="5%">&nbsp;&nbsp;&nbsp;<html:button property="value(close)" styleClass="submit"  onclick="MKEmptyPopup('SC');disablePopupASSFNL();" value="Close"  /> </td></tr>
<tr><td valign="top" height="95%">
<iframe name="SC" class="frameStyle" align="middle" frameborder="0" >

</iframe></td></tr>
</table>
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
  <img src="../images/line.jpg" border="0" /></td><td valign="middle"> <span class="pgHed">Mix Profit/Loss Report</span> </td>
</tr></table>
  </td>
  </tr>
<tr><td valign="top" class="hedPg">
<html:form action="/report/orclRptAction.do?method=fetchmixprofit"  method="POST">
  <table  class="grid1">
   <tr><th colspan="2">Search</th></tr>
    <tr><td>Employee</td><td>
             <html:select property="value(empId)" name="orclReportForm"  onchange="getEmployeeGeneric(this)" styleId="empLst">
             <html:option value="" >--select--</html:option>
             <html:optionsCollection property="byrList" name="orclReportForm"  value="byrIdn" label="byrNme" />
             </html:select></td>
    </tr>
     <tr><td>Buyer</td>
     <td nowrap="nowrap">      
     <html:text  property="value(partytext)" name="orclReportForm" styleId="partytext" style="width:230px" styleClass="sugBox" onkeypress="return disableEnterKey(event);" 
     onkeyup="doCompletionPartyNMEGeneric('partytext', 'nmePop', '../ajaxAction.do?1=1&isSrch=Y&UsrTyp=BUYER','empLst', event)"
      onkeydown="setDown('nmePop', 'party', 'partytext',event)" onclick="document.getElementById('partytext').autocomplete='off'"  />
      <img src="../images/addrow.jpg" width="17" height="17" title="Click To get Names" onclick="doCompletionPartyNMEGeneric('partytext', 'nmePop', '../ajaxAction.do?1=1&isSrch=Y&UsrTyp=BUYER','empLst')">
      <html:hidden property="value(byrId)" styleId="party"  />
  <div style="position: absolute;">
      <select id="nmePop" name="nmePop"
        style="display:none;300px;" 
        class="sugBoxDiv" 
        onKeyDown="setDown('nmePop', 'party', 'partytext',event)" 
        onDblClick="setVal('nmePop', 'party', 'partytext', event);hideObj('nmePop');" 
        onClick="setVal('nmePop', 'party', 'partytext', event)" 
        size="10">
      </select>
</div>       
</td>
    </tr>
    
     <%
   if(cnt.equals("hk")){
   ArrayList grpList = ((ArrayList)info.getGroupcompany() == null)?new ArrayList():(ArrayList)info.getGroupcompany();
   if(grpList!=null && grpList.size()> 0){
   %>
      <tr>
      <td>Group Company</td>
      <td><html:select name="orclReportForm" property="value(group)" styleId="grp">
      <html:option value="">---Select---</html:option>
      <%
      for(int i=0;i<grpList.size();i++)
      {
      ArrayList grp=(ArrayList)grpList.get(i);
      %>
      <html:option value="<%=(String)grp.get(0)%>"> <%=(String)grp.get(1)%> </html:option>
      <%
      }
      %>
      </html:select>
      </td></tr>
      <%}}%>
<tr>
   <tr>
   <td>Packet Id: </td>
   <td><html:textarea property="value(vnm)" name="orclReportForm" styleId="vnm"/></td>
   </tr>
 <tr>
 <td>Date</td><td>
 <table><tr>
 <td>
    <html:text property="value(frmdte)" styleClass="txtStyle"  styleId="frmdte"  size="12"  /> <a href="#" style="margin-top:5px;"  onClick="setYears(1900, '<%=info.getCurrentYear()%>' );showCalender(this, 'frmdte');">
                <img src="<%=info.getReqUrl()%>/images/calender.png"></a></td>
               <td>
    <html:text property="value(todte)" styleClass="txtStyle"  styleId="todte"  size="12"  /> <a href="#"  onClick="setYears(1900, '<%=info.getCurrentYear()%>' );showCalender(this, 'todte');">
                <img src="<%=info.getReqUrl()%>/images/calender.png"></a></td></tr></table></td>
 
 </tr>
   <tr>
   <td colspan="2" align="center"><html:submit property="value(srch)" value="Fetch" styleClass="submit" />
   </td>
   </tr>
   </table>
</html:form>
</td>
</tr>
  <%
    ArrayList pktList= (ArrayList)session.getAttribute("mixprofitpktList");
    ArrayList itemHdr= (ArrayList)session.getAttribute("mixprofititemHdr");
    String view= util.nvl((String)request.getAttribute("view"));
    
    if(!view.equals("")){
    if(pktList!=null && pktList.size()>0){
        String frmdte= util.nvl((String)request.getAttribute("frmdte"));
        String todte= util.nvl((String)request.getAttribute("todte"));
    %>
    <tr>
    <td class="hedPg">
    Create Excel <img src="../images/ico_file_excel.png" onclick="goTo('orclRptAction.do?method=mixprofitcreateXL','','')" border="0"/> 
    </td>
    </tr>
<tr>
<td class="hedPg">
<table class="grid1" id="dataTable">
<tr>
<%for(int i=0;i<itemHdr.size();i++){%>
<th><%=itemHdr.get(i)%></th>
<%}%>
</tr>
<%for(int i=0;i<pktList.size();i++){
HashMap pktPrpMap=(HashMap)pktList.get(i);
String idn=util.nvl((String)pktPrpMap.get("IDN"));
String trmidn=util.nvl((String)pktPrpMap.get("TRMS_IDN"));
String link=info.getReqUrl()+"/report/orclRptAction.do?method=loadmixprofitpkt&idn="+idn+"&trmidn="+trmidn+"&frmdte="+frmdte+"&todte="+todte;
String target = "SC_"+i;
String targetLnk = "LNK_"+i;
%>
<tr id="<%=target%>">    
<%
for(int j=0;j<itemHdr.size();j++){
String itm = (String)itemHdr.get(j);
if(itm.equals("VNM") && !idn.equals("TOTAL")){%>
<td align="right"><a href="<%=link%>" onclick="loadASSFNL('<%=target%>','<%=targetLnk%>')" id="<%=targetLnk%>" target="SC"  ><%=util.nvl((String)pktPrpMap.get(itm))%></a></td>
<% }else if(!idn.equals("TOTAL")){%>
<td align="right"><%=util.nvl((String)pktPrpMap.get(itm))%></td>
<%}else { %>
<td align="right"><%=util.nvl((String)pktPrpMap.get(itm))%></td>
<%}%>
<%}%>
</tr>
<%}%>
</table>
</td>
</tr>
    <%}else{%>
<tr><td valign="top" class="hedPg">
Sorry No Result Found</td></tr>
<%}}%>
  <tr><td>
<jsp:include page="/footer.jsp" />
</td></tr>
  </table>  
  <%@include file="/calendar.jsp"%>
  </body>
</html>