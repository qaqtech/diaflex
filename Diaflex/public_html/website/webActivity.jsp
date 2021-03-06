<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page errorPage="../error_page.jsp" %>
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import="java.util.ArrayList,java.util.ArrayList,java.util.HashMap, java.sql.*, java.util.Enumeration, java.util.logging.Level"%>

<jsp:useBean id="info" class="ft.com.InfoMgr" scope="session" />
<jsp:useBean id="util" class="ft.com.JspUtil" scope="page" />
<%@taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>
 
<html>
  <head>
 <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
  <title>WEB Activity Reports</title>
  <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/bse.js?v=<%=info.getJsVersion()%> " > </script>
  <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/bse.css?v=<%=info.getJsVersion()%>"/>
  <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/ajax.js?v=<%=info.getJsVersion()%> " > </script>
  <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/style.css?v=<%=info.getJsVersion()%>"/>
  <script language="javaScript" type="text/javascript" src="<%=info.getReqUrl()%>/calendar/calendar.js"></script>
   <link href="<%=info.getReqUrl()%>/calendar/calendar.css" rel="stylesheet" type="text/css" />
  </head>
  
  <%

  String logId=String.valueOf(info.getLogId());
        String onfocus="cook('"+logId+"')";
     
        %>
 <body onfocus="<%=onfocus%>" onload="MM_preloadImages('images/faunalogo.png')" topmargin="0" leftmargin="0" rightmargin="0" bottommargin="0">
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
  <img src="../images/line.jpg" border="0" /></td><td valign="middle"> <span class="pgHed">
 Web Activity
   </span> </td>
</tr></table>
  </td>
  </tr>
  <tr>
  <td valign="top" class="hedPg">
  <html:form action="website/webLoginAndSrchDtl.do?method=webactivitydtl" method="post">
  <table  class="grid1" cellpadding="3" cellspacing="5">
  <tr><th colspan="2" align="center">Web Activity Search </th></tr>
 
  
    <tr>
   <td>Type</td><td>
   <html:select property="value(typ)" styleId="typ" name="webLoginAndSrchDtlForm"  onchange="webactivityshowdiv();" >
           <html:option value="" >--select--</html:option>
           <html:option value="APPOINTMENT" >APPOINTMENT</html:option>
           <html:option value="CAREERZONE" >CAREERZONE</html:option>
           <html:option value="FEEDBACK" >FEEDBACK</html:option>
           <html:option value="ENQUIRY" >ENQUIRY</html:option>
           <html:option value="SUGGESTION">SUGGESTION</html:option>
        
   </html:select>
      <span id="logintyp" style="display:none; padding-top:10px;" align="center">
      <html:select property="value(flg)" name="webLoginAndSrchDtlForm" styleId="flg" >
             <html:option value="">--select--</html:option>            
             <html:option value="BL">Before Login</html:option>
            <html:option value="AL">Post Login</html:option>
     </html:select>
     </span>
   </td>
   </tr>
   <tr>
       <td>From</td><td>
        <div class="float" > <html:text property="value(frmDte)"  styleId="frmDte" size="15"/></div>
        <div class="float"> <a href="#"  onClick="setYears(1900, <%=info.getCurrentYear()%>);showCalender(this, 'frmDte');">
          <img src="<%=info.getReqUrl()%>/images/calender.png"></a></div>
      </td></tr>
      <tr>
      <td>To</td><td>
        <div class="float"> <html:text property="value(toDte)"  styleId="toDte" size="15"/></div>
        <div class="float"> <a href="#"  onClick="setYears(1900, <%=info.getCurrentYear()%>);showCalender(this, 'toDte');">
          <img src="<%=info.getReqUrl()%>/images/calender.png"></a></div>
      </td>
  </tr>
   <tr><td colspan="2" align="center"> <html:submit property="value(submit)" styleClass="submit" value="Fetch" /> </td> </tr>
  
  </table>
  </html:form></td>
  
  </tr>
  <%
  String view=util.nvl((String)request.getAttribute("view"));
  if(!view.equals("")){
  ArrayList itemHdr = (ArrayList)request.getAttribute("itemHdr");
  ArrayList actList = (ArrayList)request.getAttribute("actList");

  %>
    
    <% if(actList!=null && actList.size()>0) { %>
    <tr>
    <td valign="top" class="hedPg"> <table  class="grid1"><tr>
    <%for(int i=0;i<itemHdr.size();i++){%>
    <th nowrap="nowrap"><%=util.nvl((String)itemHdr.get(i))%></th>
    <%}%></tr>
     <%for(int j=0;j<actList.size();j++){%>
     <tr>
     <%HashMap  actMap=(HashMap)actList.get(j);  
     for(int k=0;k<itemHdr.size();k++){
     String itm=(String)itemHdr.get(k);
     String itmval=util.nvl((String)actMap.get(itm));
     if(!itm.equals("Attachment")){%>
     <td nowrap="nowrap"><%=itmval%></td>
     <%}else if(!itmval.equals("") && !itmval.equals("null")){%>
   <td>
   <button type="button"  class="submit" onclick="goTo('<%=info.getReqUrl()%>/contact/nmedocs.do?method=download&path=http://s3-ap-southeast-1.amazonaws.com/hkfauna/hkdoc/<%=itmval%>')">Download</button>
   </td>
   <%}%>
   <%}%>
   </tr>
   <%}%>
   </table>
   </td>
   </tr>
   <%}else{%>
      <tr><td valign="top" class="hedPg">Sorry no result found </td></tr>
   <%}}%>

  <tr>
    <td><jsp:include page="/footer.jsp" /> </td>
    </tr>
  </table>
   <%@include file="../calendar.jsp"%>
  </body>
</html>