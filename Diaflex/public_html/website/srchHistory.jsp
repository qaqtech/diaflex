<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page errorPage="../error_page.jsp" %>
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.ResultSet, java.util.ArrayList,java.util.Collections,java.util.ArrayList,java.util.HashMap,java.text.SimpleDateFormat, java.util.Calendar"%>

<jsp:useBean id="util" class="ft.com.JspUtil" scope="page" />
<jsp:useBean id="info" class="ft.com.InfoMgr" scope="session"/>

<%@taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
 

<html>
   <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/bse.js?v=<%=info.getJsVersion()%> " > </script>
<link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/bse.css?v=<%=info.getJsVersion()%>"/>
<link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/general.css?v=<%=info.getJsVersion()%>"/>
 <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/memoScript.js?v=<%=info.getJsVersion()%> " > </script>
<script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/ajax.js?v=<%=info.getJsVersion()%> " > </script>
  <script type="text/javascript" src="<%=info.getReqUrl()%>/PopScripts/popup.js?v=<%=info.getJsVersion()%> " > </script>
    <script src="<%=info.getReqUrl()%>/PopScripts/jquery-1.2.6.min.js" type="text/javascript"></script>
   <script language="javaScript" type="text/javascript" src="<%=info.getReqUrl()%>/calendar/calendar.js"></script>
   <link href="<%=info.getReqUrl()%>/calendar/calendar.css" rel="stylesheet" type="text/css" />

 
      <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/style.css?v=<%=info.getJsVersion()%>"/>

  <title>Search History Report</title>
 
  </head>
        <%String logId=String.valueOf(info.getLogId());
        String onfocus="cook('"+logId+"')";
        %>
 <body onfocus="<%=onfocus%>" onkeypress="return disableEnterKey(event);" >
       <%

  ArrayList itemHdr = (ArrayList)request.getAttribute("itemHdr");
  ArrayList dtlsList = (ArrayList)request.getAttribute("dtlList");
  String view = util.nvl((String)request.getAttribute("view"));
     HashMap dbinfo = info.getDmbsInfoLst();
   String cnt = (String)dbinfo.get("CNT");
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
Search History Report 
  </span> </td>
</tr></table>
  </td>
  </tr>
   <tr>
   <td valign="top" class="hedPg">
    <html:form action="website/webLoginAndSrchDtl.do?method=loadsrchHistory" method="post" >
<input type="hidden" name="webUrl" id="webUrl" value="<%=dbinfo.get("WEB_PATH")%>"/>
<input type="hidden" name="cnt" id="cnt" value="<%=cnt%>"/>
<input type="hidden" name="repUrl" id="repUrl" value="<%=dbinfo.get("HOME_DIR")%>"/>
<input type="hidden" name="repPath" id="repPath" value="<%=dbinfo.get("REP_PATH")%>"/>
    <table><tr><td>Date From : </td><td>
    <html:text property="value(dtefr)" styleClass="txtStyle"  styleId="dtefr"  size="30"  /> <a href="#" style="margin-top:5px;"  onClick="setYears(1900, '<%=info.getCurrentYear()%>' );showCalender(this, 'dtefr');">
                <img src="<%=info.getReqUrl()%>/images/calender.png"></a></td>
                <td>Date To : </td><td>
    <html:text property="value(dteto)" styleClass="txtStyle"  styleId="dteto"  size="30"  /> <a href="#"  onClick="setYears(1900, '<%=info.getCurrentYear()%>' );showCalender(this, 'dteto');">
                <img src="<%=info.getReqUrl()%>/images/calender.png"></a></td>
      <td>Buyer</td>
      <td nowrap="nowrap">
   
    <%
    String sbUrl ="ajaxRptAction.do?method=buyerSugg";
    %>
    <input type="text" id="nme" name="nme" autocomplete="off" class="sugBox"
      onKeyUp="doCompletion('nme', 'nmePop', 'ajaxRptAction.do?method=buyerSugg', event)" 
      onKeyDown="setDown('nmePop', 'nmeID', 'nme',event)"/>
 <img src="../images/addrow.jpg" width="17" height="17" title="Click To get Names" onclick="doCompletionMobile('nme', 'nmePop', 'ajaxRptAction.do?method=buyerSugg')">
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
  <td>User Name : <html:text property="value(usr)" name="webLoginAndSrchDtlForm" /></td>
       <td><html:submit property="value(submit)" value="Fetch" styleClass="submit"/>
       </td>
                </tr></table>
  </html:form>
  </td>
  </tr>
  <%
      HashMap mprp = info.getMprp();
    HashMap prp = info.getPrp();
   ArrayList srchPrp = (ArrayList)session.getAttribute("asHistorySrch");
   ArrayList srchIds=(ArrayList) session.getAttribute("srchIdist");
 // HashMap srchHistMap=(HashMap)request.getAttribute("srchHistMap"); 
   HashMap srchList=(HashMap)session.getAttribute("srchList"); 
  HashMap srchVal=(HashMap)session.getAttribute("srchVal");
  if(!view.equals("")){
  if(srchIds!=null && srchIds.size()>0 ){
  %>
  
  
  <tr><td class="hedPg">Create Excel <img src="../images/ico_file_excel.png" onclick="goTo('webLoginAndSrchDtl.do?method=createXLSearchHistory','','')" border="0"/> </td></tr>
  
    <tr>
    <td valign="top" class="hedPg"><table class="grid1">
 <tr>
    <th>User</th>
    <th nowrap="nowrap">Buyer name</th>
    <th>Date</th>
    <th>Id</th>
   
<%
for(int i=0; i < srchPrp.size(); i++) {
      ArrayList prpVec = (ArrayList)srchPrp.get(i);
        String lprp = (String)prpVec.get(0);
         String flag = (String)prpVec.get(1);
        String prpDsc = (String)mprp.get(lprp);
       if(flag.equals("M"))
        {
    %>
    <th  align="center"><%=prpDsc%></th>
<%    
        }
        else
        {
     %>
    <th colspan="2" align="center"><%=prpDsc%></th>
<%   
        }
}%>
</tr>
<tr>
<td></td>
<td></td>
<td></td>
<td></td>
<%for(int j=0; j < srchPrp.size(); j++) {
    ArrayList prpVec = (ArrayList)srchPrp.get(j);
        String lprp = (String)prpVec.get(0);
         String flag = (String)prpVec.get(1);
        if(flag.equals("M"))
        {
        %>
     <td>&nbsp;</td>
     <%
        }
        else
        {
%>
<td> <b>From</b></td>
<td><b>&nbsp;To&nbsp;</b></td>
<%}}%>
</tr>
 
  
   <% 
   for(int i=0; i < srchIds.size(); i++)    {
        String l_srchId = (String)srchIds.get(i);
        HashMap msrcList =(HashMap)srchList.get(l_srchId);
        String dte = util.nvl((String)srchVal.get(l_srchId+"_DTE"));
        String usr = util.nvl((String)srchVal.get(l_srchId+"_USR"));
        String byr = util.nvl((String)srchVal.get(l_srchId+"_BYR"));
        if(msrcList!=null)
        {
    %>   
     <tr>
     <td nowrap="nowrap"><%=usr%></td>
     <td nowrap="nowrap"><%=byr%></td>
          <td nowrap="nowrap"><%=dte%></td>
          <td><a href="<%=info.getReqUrl()%>/marketing/StockSearch.do?method=loadSrchPrp&srchId=<%=l_srchId%>" target="_self"><%=l_srchId%></a></td>
    <%for(int j=0; j < srchPrp.size(); j++) {
    
        ArrayList prpVec = (ArrayList)srchPrp.get(j);
        String lprp = (String)prpVec.get(0);
        String flag = (String)prpVec.get(1);
        if(flag.equals("M"))
        {%>
        <td nowrap="nowrap"><%=util.nvl2((String)msrcList.get(util.nvl(lprp)),"&nbsp;")%></td>
     <%   }
     else if(flag.equals("S"))
     {
        String vfr = util.nvl2((String)srchVal.get(l_srchId+"_"+lprp+"_fr"), "&nbsp;"); 
        String vto = util.nvl2((String)srchVal.get(l_srchId+"_"+lprp+"_to"), "&nbsp;"); 
        
    %>
        <td nowrap="nowrap"><%=vfr%></td>
        <td nowrap="nowrap"><%=vto%></td>
    <%
    }}%>
    </tr>
    <%}
    }%>
    </table>
    </td></tr>
    <%}else{%>
    <tr><td>Sorry No Result Found</td></tr>
    <%}}%>
   <tr>
    <td><jsp:include page="/footer.jsp" /> </td>
    </tr>
  </table>
  <%@include file="/calendar.jsp"%>
  </body>
</html>