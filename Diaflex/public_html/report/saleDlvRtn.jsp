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
    <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/bse.js?v=<%=info.getJsVersion()%> " > </script>
<link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/bse.css?v=<%=info.getJsVersion()%>"/>
<link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/general.css?v=<%=info.getJsVersion()%>"/>
<script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/ajax.js?v=<%=info.getJsVersion()%> " > </script>
  <script type="text/javascript" src="<%=info.getReqUrl()%>/PopScripts/popup.js?v=<%=info.getJsVersion()%> " > </script>
    <script src="<%=info.getReqUrl()%>/PopScripts/jquery-1.2.6.min.js" type="text/javascript"></script>
   <script language="javaScript" type="text/javascript" src="<%=info.getReqUrl()%>/calendar/calendar.js"></script>
   <link href="<%=info.getReqUrl()%>/calendar/calendar.css" rel="stylesheet" type="text/css" />

 
  <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/style.css?v=<%=info.getJsVersion()%>"/>

  <title>Return Report</title>
 
  </head>
        <%String logId=String.valueOf(info.getLogId());
        String onfocus="cook('"+logId+"')";
        %>
 <body onfocus="<%=onfocus%>" onload="MM_preloadImages('images/faunalogo.png')" topmargin="0" leftmargin="0" rightmargin="0" bottommargin="0">
  <table cellpadding="" cellspacing="" class="mainbg">
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
  <img src="../images/line.jpg" border="0" /></td><td valign="middle"> <span class="pgHed">Return Report</span> </td>
</tr></table>
  </td>
  </tr>
  <%
  HashMap allPageDtl= (info.getPageDetails() == null)?new HashMap():(HashMap)info.getPageDetails();
     HashMap pageDtl=(HashMap)allPageDtl.get("REPORT_RTN");
     ArrayList pageList=new ArrayList();
     HashMap pageDtlMap=new HashMap();
    String fld_nme="",fld_ttl="",val_cond="",dflt_val="",fld_typ="",form_nme="",lov_qry="";
      ArrayList repMemoLst =(info.getValue("MDL_MEMO_RTRN") == null)?new ArrayList():(ArrayList)info.getValue("MDL_MEMO_RTRN");
      ArrayList prpDspBlocked = info.getPageblockList();
  %>
<tr><td valign="top" class="hedPg">
<html:form action="/report/orclRptAction.do?method=fetchloadrtn" method="POST">
    <table class="grid1">
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
    <tr>
    <td>
    Type
    </td>
    <td>
    <html:select property="value(typ)" styleId="typ" name="orclReportForm" >
             <%pageList=(ArrayList)pageDtl.get("TYPE");
               for(int i=0;i<pageList.size();i++){
                 pageDtlMap=(HashMap)pageList.get(i);
                 fld_ttl=(String)pageDtlMap.get("fld_ttl");
                 val_cond=(String)pageDtlMap.get("val_cond");
                 dflt_val=(String)pageDtlMap.get("dflt_val");
                 %>
                <html:option value="<%=dflt_val%>"><%=fld_ttl%></html:option>
             <%}%>
    </html:select>
    </td>
    </tr>
    <tr><td>Return Date : </td>  
       <td><html:text property="value(rtnfrmdte)" styleId="rtnfrmdte" name="orclReportForm" size="8" /><img src="<%=info.getReqUrl()%>/images/calender.png" onClick="setYears(2011, <%=info.getCurrentYear()%>);showCalender(this, 'rtnfrmdte');"> 
       To&nbsp; <html:text property="value(rtntodte)" styleId="rtntodte" name="orclReportForm"  size="8"/> <img src="<%=info.getReqUrl()%>/images/calender.png" onClick="setYears(2011, <%=info.getCurrentYear()%>);showCalender(this, 'rtntodte');"></td>
    </tr>
    <tr>
    <td>
    Packet Type
    </td>
        <td>
    <html:select property="value(pkttyp)" styleId="pkttyp" name="orclReportForm" >
             <%pageList=(ArrayList)pageDtl.get("PKTTY");
               for(int i=0;i<pageList.size();i++){
                 pageDtlMap=(HashMap)pageList.get(i);
                 fld_ttl=(String)pageDtlMap.get("fld_ttl");
                 val_cond=(String)pageDtlMap.get("val_cond");
                 dflt_val=(String)pageDtlMap.get("dflt_val");
                 %>
                <html:option value="<%=dflt_val%>"><%=fld_ttl%></html:option>
             <%}%>
    </html:select>
    </td>
    </tr>
     <tr><td colspan="2" align="center"><html:submit property="value(submit)" value="Fetch" styleClass="submit"/></td></tr>
     </table>
</html:form>
</td>
</tr>
  <%
    String view= util.nvl((String)request.getAttribute("view")); 
    if(view.equals("Y")){%>
  
  <tr><td valign="top" class="hedPg" nowrap="nowrap"  >
  <display:table name="sessionScope.orclReportForm.pktList" class="displayTable" requestURI="" export="true"  sort="list" decorator="ft.com.Decortor">
   <display:column property="sal" style="font-weight: bold;" title="Sale Person" group="1" />
    <display:column property="byr" style="font-weight: bold;" title="Buyer" group="2" />
    <display:column property="memoIdn"  title="Idn" group="3" />
    <display:column property="typ"  title="Typ"  />
    <display:column property="vnm"  title="Packet No."  />
     <display:column property="dte"  title="Date"  />
     <display:column property="rtn_dte"  title="Rtn Date"  />
     <display:column property="memoQuot"  title="Byr Rte"  />
     <display:column property="byrdis"  title="Byr Dis"  />
     <display:column property="rte"  title="Prc / Crt"  />
     <display:column property="amt"  title="Amount"  media="html" />
      <display:column property="vlu"  title="Amount"  media="excel" />
    <% for (int j = 0; j < repMemoLst.size(); j++) {
     String prp = (String)repMemoLst.get(j);
    if(prpDspBlocked.contains(prp)){
    }else{%>
  <display:column property="<%=prp%>" title="<%=prp%>"  /> 
  <%}}%>
  <display:column property="rmk"  title="Remark"  />
    <display:setProperty name="export.excel.filename" value="Details.xls"/>
  <display:setProperty name="export.xls" value="true" />
    <display:setProperty name="export.csv" value="false" />
     <display:setProperty name="export.xml" value="false" />
  </display:table>
  </td></tr>
  <%}%>
  <tr><td>
<jsp:include page="/footer.jsp" />
</td></tr>
  </table>  
   <%@include file="/calendar.jsp"%>
  </body>
</html>