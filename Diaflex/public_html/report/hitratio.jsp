<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page errorPage="../error_page.jsp" %>
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.ResultSet, java.util.ArrayList,java.util.Collections,ft.com.dao.MemoType,java.util.ArrayList,java.util.HashMap,java.text.SimpleDateFormat, java.util.Calendar"%>
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

  <title>Memo Hit Ratio Report</title>
 
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
  <img src="../images/line.jpg" border="0" /></td><td valign="middle"> <span class="pgHed">Memo Hit Ratio Report</span> </td>
</tr></table>
  </td>
  </tr>
  <%
  HashMap allPageDtl= (info.getPageDetails() == null)?new HashMap():(HashMap)info.getPageDetails();
     HashMap pageDtl=(HashMap)allPageDtl.get("HITRATIO");
     ArrayList pageList=new ArrayList();
     HashMap pageDtlMap=new HashMap();
    String fld_nme="",fld_ttl="",val_cond="",dflt_val="",fld_typ="",form_nme="",lov_qry="";
      ArrayList repMemoLst =(session.getAttribute("HIT_RATIO_VW") == null)?new ArrayList():(ArrayList)session.getAttribute("HIT_RATIO_VW");
      ArrayList prpDspBlocked = info.getPageblockList();
   HashMap dbinfo = info.getDmbsInfoLst();
   String cnt = (String)dbinfo.get("CNT");
     String  view = util.nvl((String)request.getAttribute("view"));
       HashMap prp = info.getPrp();
  HashMap mprp = info.getMprp();
  %>
<tr><td valign="top" class="hedPg">
<html:form action="/report/orclRptAction.do?method=fetchhitratio" method="POST" onsubmit="return validateHitratio();">
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
<td>Memo Type</td>
<td>
<html:select property="value(typ)" styleId="typId" name="orclReportForm" onchange="" >
<html:option value="">---All---</html:option>
<html:optionsCollection property="memoList" name="orclReportForm"
label="dsc" value="memo_typ" />
</html:select>
</td>
</tr>
    <tr><td>Date : </td>  
       <td><html:text property="value(frmdte)" styleId="frmdte" name="orclReportForm" size="8" /><img src="<%=info.getReqUrl()%>/images/calender.png" onClick="setYears(2011, <%=info.getCurrentYear()%>);showCalender(this, 'frmdte');"> 
       To&nbsp; <html:text property="value(todte)" styleId="todte" name="orclReportForm"  size="8"/> <img src="<%=info.getReqUrl()%>/images/calender.png" onClick="setYears(2011, <%=info.getCurrentYear()%>);showCalender(this, 'todte');"></td>
    </tr>
    <tr>
    <td>Row</td>
    <td>
      <html:select name="orclReportForm" property="value(row)" styleId="row">
      <%for(int i=0;i<repMemoLst.size();i++){
      String lprp=(String)repMemoLst.get(i);
      %>
      <html:option value="<%=lprp%>"> <%=util.nvl((String)mprp.get(lprp+"D"))%> </html:option>
      <%} %>
      </html:select>
    </td>
    </tr>
    <tr>
    <td>Column</td>
    <td>
      <html:select name="orclReportForm" property="value(column)" styleId="column">
      <%for(int i=0;i<repMemoLst.size();i++){
      String lprp=(String)repMemoLst.get(i);
      %>
      <html:option value="<%=lprp%>"> <%=util.nvl((String)mprp.get(lprp+"D"))%> </html:option>
      <%} %>
      </html:select>
    </td>
    </tr>
     <tr><td colspan="2" align="center"><html:submit property="value(submit)" value="Fetch" styleClass="submit"/></td></tr>
     </table>
</html:form>
</td>
</tr>
  <%if(view.equals("Y")){
         ArrayList  memoTypeList = (ArrayList)session.getAttribute("HitmemoTypeList");
       HashMap hitratiodata = (HashMap)request.getAttribute("hitratiodata");
       ArrayList  hitratiorowprpValLst = (ArrayList)request.getAttribute("hitratiorowprpValLst");
       ArrayList  hitratiocolprpValLst = (ArrayList)request.getAttribute("hitratiocolprpValLst");
       ArrayList  hitratiomemoTyp = (ArrayList)request.getAttribute("hitratiomemoTyp");
       String  row = (String)request.getAttribute("row");
       String  column = (String)request.getAttribute("column");
  ArrayList rowprpval = (ArrayList)prp.get(row+"V");
  ArrayList rowprpPrt = (ArrayList)prp.get(row+"P");
  ArrayList columnprpval = (ArrayList)prp.get(column+"V");
  ArrayList columnprpPrt = (ArrayList)prp.get(column+"P");
  String rowprpDsc = (String)mprp.get(row+"D");
  String columnprpDsc = (String)mprp.get(column+"D");
  int hitratiocolprpValLstsz=hitratiocolprpValLst.size();
  int hitratiorowprpValLstsz=hitratiorowprpValLst.size();
    int columnprpvalsz=columnprpval.size();
  int rowprpvalsz=rowprpval.size();
  if(hitratiomemoTyp!=null && hitratiomemoTyp.size()>0){
  for (int i=0;i<memoTypeList.size();i++){
  MemoType memotypDtl=(MemoType)memoTypeList.get(i);
  String typ=(String)memotypDtl.getMemo_typ();
  if(hitratiomemoTyp.contains(typ)){
  String memotypDsc=(String)memotypDtl.getDsc();
  %>
  <tr><td valign="top" class="hedPg" nowrap="nowrap"  >
  <table class="grid1">
  <tr>
  <th colspan="<%=hitratiocolprpValLstsz+1%>"><%=memotypDsc%></th>
  </tr>
  <tr>
  <th><%=rowprpDsc%>/<%=columnprpDsc%></th>
  <%for (int c=0;c<columnprpvalsz;c++){
  String columnprp=util.nvl((String)columnprpval.get(c));
  if(hitratiocolprpValLst.contains(columnprp)){%>
  <td><%=util.nvl((String)columnprpPrt.get(c))%></td>
  <%}}%>
  </tr>
  <%for (int r=0;r<rowprpvalsz;r++){
  String rowprp=util.nvl((String)rowprpval.get(r));
  if(hitratiorowprpValLst.contains(rowprp)){%>
  <tr>
  <th><%=util.nvl((String)rowprpPrt.get(r))%></th>
  <%for (int c=0;c<columnprpvalsz;c++){
  String columnprp=util.nvl((String)columnprpval.get(c));
  if(hitratiocolprpValLst.contains(columnprp)){%>
  <td><%=util.nvl((String)hitratiodata.get(typ+"_"+rowprp+"_"+columnprp))%></td>
  <%}}%>
  </tr>
  <%}}%>
  </table>
  </td></tr>
  <%}}%>
  <%}else{%>
  <tr><td valign="top" class="hedPg" nowrap="nowrap"  >
    Sorry no Result Found
  </td></tr>
  <%}}%>
  <tr><td>
<jsp:include page="/footer.jsp" />
</td></tr>
  </table>  
   <%@include file="/calendar.jsp"%>
  </body>
</html>