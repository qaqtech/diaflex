<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page errorPage="../error_page.jsp" %>
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import="java.util.ArrayList,java.util.HashMap,java.sql.ResultSet,ft.com.*, java.util.Date, java.util.ArrayList, ft.com.dao.*"%>
<jsp:useBean id="info" class="ft.com.InfoMgr" scope="session" />
<jsp:useBean id="util" class="ft.com.JspUtil" scope="page" />
<%@taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>
<%@taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>Contact Search</title>
<link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/bse.css?v=<%=info.getJsVersion()%>"/>
    <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/style.css?v=<%=info.getJsVersion()%>"/>
    <script language="javaScript" type="text/javascript" src="<%=info.getReqUrl()%>/calendar/calendar.js"></script>
    <link href="<%=info.getReqUrl()%>/calendar/calendar.css" rel="stylesheet" type="text/css" />
      <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/ajax.js?v=<%=info.getJsVersion()%>"></script> 
       <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/bse.js?v=<%=info.getJsVersion()%>"></script> 
           <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/Validation.js?v=<%=info.getJsVersion()%>"></script> 
    <script type="text/javascript" src="<%=info.getReqUrl()%>/PopScripts/popup.js?v=<%=info.getJsVersion()%> " > </script>
      <script src="<%=info.getReqUrl()%>/PopScripts/jquery-1.2.6.min.js" type="text/javascript"></script>
  <script type="text/javascript" src="<%=info.getReqUrl()%>/jquery/nicEdit-latest.js"></script> 
<script type="text/javascript" >
//<![CDATA[
        bkLib.onDomLoaded(function() { nicEditors.allTextAreas() });
  //]]>
  </script>
  </head>
        <%String logId=String.valueOf(info.getLogId());
        String onfocus="cook('"+logId+"')";
  HashMap allPageDtl= (info.getPageDetails() == null)?new HashMap():(HashMap)info.getPageDetails();
  HashMap pageDtl=(HashMap)allPageDtl.get("CONTACT_SRCH");
  ArrayList pageList=new ArrayList();
  HashMap pageDtlMap=new HashMap();
  String fld_nme="",fld_ttl="",val_cond="",dflt_val="",fld_typ="",form_nme="",flg1="",lov_qry="";
ArrayList params = new ArrayList();
String formName = "clientfeddbackform";
String editlink = "/contact/advcontact.do?method=editclientFeedback";
ArrayList feedbackList = (ArrayList) session.getAttribute("EmpFeedbackList");
String btn = util.nvl((String)request.getAttribute("btn"),"Add Feedback");
String byr = util.nvl((String)session.getAttribute("byr"));
               
%>
 <body onfocus="<%=onfocus%>" onload="MM_preloadImages('images/faunalogo.png')"  onkeypress="return disableEnterKey(event);" topmargin="0" leftmargin="0" rightmargin="0" bottommargin="0">

<table width="100%"  border="0" align="center" cellpadding="0" cellspacing="0">
<tr><th  title="Click Here to Hide/Display"  align="center" style="font-size: 13px;" onclick="displayDiv('fdb')">FeedBack For - <%=byr%></th></tr>
<tr></tr>
<tr><td valign="top"><div id="fdb" >
<html:form action="/contact/advcontact.do?method=saveclientFeedback" method="post" onsubmit="return validateFeedbackForm()" >
<html:hidden  property="value(emp_Idn)"/>
<html:hidden  property="value(nme_Idn)"/>
<html:hidden  property="value(idn)"/>
<html:hidden  property="value(starttm)" styleId="starttm"/>
<html:hidden  property="value(endtm)" styleId="endtm"/>
<table class="grid1">
<tr>
<td>
<table width="100%" cellpadding="2" cellspacing="2">
<tr>
<td>
<span id="start"><html:button property="value(start)" styleClass="submit"  onclick="currentTm('start','starttm');" value="Start Time"  /></span>
</td>
<td colspan="3" align="left"></td>
</tr>
<tr>          
        <%
            pageList=(ArrayList)pageDtl.get("FEEDMODE");
             if(pageList!=null && pageList.size() >0){%>
             <td>Mode </td>
             <td>
             <html:select property="value(mode)" styleId="mode" >
             <html:option value="">---Select---</html:option>
             <%
            for(int i=0;i<pageList.size();i++){
                 pageDtlMap=(HashMap)pageList.get(i);
                 fld_nme=(String)pageDtlMap.get("fld_nme");
                 fld_ttl=(String)pageDtlMap.get("fld_ttl");
                 %>
                <html:option value="<%=fld_nme%>"><%=fld_ttl%></html:option>
             <%}%>
            </html:select>
            </td>
            <%}%>
<td>Reference</td><td>
<html:text property="value(memoref)" styleClass="txtStyle"  styleId="memoref" size="45"  />
</td>
</tr>
           

<tr>
<td align="left">
Buyer Feed
</td>
<td align="left" bgcolor="White">
<html:textarea property="value(byrcomm)" cols="38" rows="5" styleId="byrcomm" />
</td>
<td align="left">Emp Feed
</td>
<td align="left" bgcolor="White">
<html:textarea property="value(empcomm)" cols="38" rows="5" styleId="empcomm" />

</td>
</tr>

<tr>
<td>Points Discussed</td><td>
<html:text property="value(dicuss)" styleClass="txtStyle"  styleId="dicuss" size="53" />
</td>
       <td>Next FollowUp</td><td>
        <div class="float" > <html:text property="value(flDte)"  styleId="flDte" size="10"/></div>
        <div class="float"> <a href="#"  onClick="setYears(1900,<%=info.getCurrentYear()%>);showCalender(this, 'flDte');">
          <img src="<%=info.getReqUrl()%>/images/calender.png"></a></div>
          &nbsp;&nbsp 
          <span id="end"><html:button property="value(end)" styleClass="submit"  onclick="currentTm('end','endtm');" value="End Time"  /></span>
      </td></tr>
</table>

</td></tr>
<tr>
<td align="center">
 <logic:notEqual property="value(disButton)"  name="advContactForm"  value="Update" >
<html:submit property="value(btnAdd)" value="Add"  styleClass="submit"/>
</logic:notEqual>
<logic:equal property="value(disButton)"  name="advContactForm"  value="Update" >
<html:submit property="value(btnUpd)" value="Update"  styleClass="submit"/>
</logic:equal>
</td>
</tr>
</table></html:form>
</div>
</td></tr>

<%
   if(feedbackList.size()>0){%>

  <tr><td valign="top">
  <br>
  <table class="grid1" id="dataTable">
  <thead>
  <tr>
  <th>Sr No</th><th nowrap="nowrap">Mode</th> <th nowrap="nowrap">Date</th>   <th nowrap="nowrap"> Discussed</th><th nowrap="nowrap">Reference Memo/List</th>  <th nowrap="nowrap">Next FollowUp</th>  <th nowrap="nowrap">Buyer FeedBack</th> <th nowrap="nowrap">Employee FeedBack</th>
    <th nowrap="nowrap">Start Time</th> <th nowrap="nowrap">End Time</th>
    <th nowrap="nowrap">Action</th>
  </tr>
</thead>
<tbody>
<%for(int j=0; j<feedbackList.size(); j++){
HashMap flist =(HashMap)feedbackList.get(j);
%>
  <tr> <td><%=j+1%></td>
      <td nowrap="nowrap" align="left"><%=util.nvl((String)flist.get("Mode"))%></td>
       <td nowrap="nowrap" align="left"><%=util.nvl((String)flist.get("Dte"))%></td>
      <td nowrap="nowrap" align="left"><%=util.nvl((String)flist.get("ByrstkComm"))%></td>
      <td nowrap="nowrap" align="right"><%=util.nvl((String)flist.get("MemoRef"))%></td>
      <td nowrap="nowrap" align="left"><%=util.nvl((String)flist.get("NxtDte"))%></td>
       <td nowrap="nowrap" align="left"><%=util.nvl((String)flist.get("ByrComm"))%></td>
       <td nowrap="nowrap" align="left"><%=util.nvl((String)flist.get("EmpComm"))%></td>
       <td nowrap="nowrap" align="left"><%=util.nvl((String)flist.get("starttm"))%></td>
       <td nowrap="nowrap" align="left"><%=util.nvl((String)flist.get("endtm"))%></td>
       <%String edit =editlink+"&edit='Y'&idn="+util.nvl((String)flist.get("Idn"));%>
             <td nowrap="nowrap" align="left"><a href="<%=info.getReqUrl()%><%=edit%>" >Edit</a></td>
  <%}%>
  </tr>
</tbody>
</table></td>
</tr>
<%
}%>
</table></td></tr>


</table>
<%
pageList=(ArrayList)pageDtl.get("FEED_LIVE");
if(pageList!=null && pageList.size() >0){%>
<script type="text/javascript">
<!--
$(window).bind("load", function() {
currentTm('start','starttm');
currentTm('end','endtm');
});
-->
</script>  
<%}%>
<%@include file="../calendar.jsp"%>
</body>
</html>