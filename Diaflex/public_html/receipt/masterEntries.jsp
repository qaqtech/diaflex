<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
 
<%@ page errorPage="../error_page.jsp" %>
<%@ page contentType="text/html;charset=windows-1252"%>
<jsp:useBean id="util" class="ft.com.JspUtil" scope="page" />
<jsp:useBean id="info" class="ft.com.InfoMgr" scope="session" />
<%@ page import="java.util.ArrayList,java.util.HashMap,java.sql.ResultSet,ft.com.*, java.util.Date, java.util.ArrayList, ft.com.dao.*"%>
<%@taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>Master Entries</title>
   <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/bse.css?v=<%=info.getJsVersion()%>"/>
      <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/style.css?v=<%=info.getJsVersion()%>"/>
      <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/bse.js?v=<%=info.getJsVersion()%> " > </script>
        <link href="<%=info.getReqUrl()%>/calendar/calendar.css" rel="stylesheet" type="text/css" />
   <script language="javaScript" type="text/javascript" src="<%=info.getReqUrl()%>/calendar/calendar.js"></script>
      <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/Validation.js?v=<%=info.getJsVersion()%> " > </script>


  </head>
<%
String logId=String.valueOf(info.getLogId());
        String onfocus="cook('"+logId+"')";
        DBUtil dbutil = new DBUtil();
        DBMgr db = new DBMgr(); 
        db.setCon(info.getCon());
        dbutil.setDb(db);
        dbutil.setInfo(info);
        db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));db.setDbUsr(info.getDbUsr());
        dbutil.setLogApplNm(info.getLoApplNm());
%>
    <body onfocus="<%=onfocus%>"  topmargin="0" leftmargin="0" rightmargin="0" bottommargin="0">
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
  <img src="../images/line.jpg" border="0" /></td><td valign="middle"> <span class="pgHed">Master Entries</span> </td>
</tr></table>
  </td>
  </tr>
  <% if(request.getAttribute("msg")!=null){%>
        <tr><td class="tdLayout" height="15"><span class="redLabel"> <%=request.getAttribute("msg")%></span></td></tr>
  <%}%>
  <tr>
  <td valign="top" class="tdLayout">
   <html:form action="receipt/masterEntriesAction.do?method=save" method="post" onsubmit="return validateMasterEntries()">
  <html:hidden property="value(IDN)" name="masterEntriesForm" />
  <table cellpadding="2" cellspacing="2">
   <tr>
   <td><span class="redLabel">*</span>Name</td> <td>:</td>
   <td> 
   <html:text property="value(nme)" styleClass="txtStyle"  styleId="nme"  name="masterEntriesForm" />
   </td></tr>
  <tr>
   <td><span class="redLabel">*</span>Short Name: </td> <td>:</td>
   <td><html:text property="value(cd)" styleClass="txtStyle"  styleId="cd" name="masterEntriesForm"  />
   </td></tr>
   <tr>
   <td>Trans YN </td><td>:</td><td>
   <html:select property="value(transYN)"  styleId="transYN" name="masterEntriesForm" >
   <html:option value="Y">Yes</html:option>
   <html:option value="N">No</html:option>
   </html:select>
   </td></tr>
   <tr>
   <td>Entry Points </td><td>:</td><td>
   <html:select property="value(entryPnt)"  styleId="entryPnt" name="masterEntriesForm" >
   <html:option value="MNL">Manual</html:option>
   <html:option value="DFLT">Defult</html:option>
   </html:select>
   </td></tr>
    <tr>
   <td> Mobile </td><td>:</td><td><html:text property="value(mob)" styleClass="txtStyle"  styleId="mob"  name="masterEntriesForm" /> </td></tr>
    <tr>
     <tr>
   <td>Email ID </td><td>:</td><td><html:text property="value(eml)" styleClass="txtStyle"  styleId="eml" name="masterEntriesForm"  /> </td></tr>
    <tr>
    <tr><td></td><td colspan="2" align="left">
   <html:submit property="value(submit)" value="Submit" styleClass="submit" />
   </td></tr>
   </table></html:form>
  </td></tr>
  <tr>
  <td valign="top" class="tdLayout">
  <%
  ArrayList dtlList = (ArrayList)request.getAttribute("DTLLIST");
  if(dtlList!=null && dtlList.size()>0){
  %>
 <b> Primary Account</b>
  <table class="grid1" >
  <tr><th>Name</th><th>Short Name</th><th>Typ</th><th>Entry Point</th><th>Edit</th></tr>
   <%for(int i=0;i<dtlList.size();i++){
   HashMap dtlMap = (HashMap)dtlList.get(i);
   String idn = (String)dtlMap.get("IDN");
   %>
   <tr>
   <td><%=util.nvl((String)dtlMap.get("nme"))%></td><td><%=util.nvl((String)dtlMap.get("cd"))%></td><td><%=util.nvl((String)dtlMap.get("transYN"))%></td>
   <td><%=util.nvl((String)dtlMap.get("entryPnt"))%></td>
   <td><A href="../receipt/masterEntriesAction.do?method=Edit&IDN=<%=idn%>"> Edit</a></td>
   </tr>
 <%}%>
  </table>
  <%}%>
  </td></tr>
  <tr>
   <td><jsp:include page="/footer.jsp" /> </td>
   </tr>
    </table>
    </body>
</html>