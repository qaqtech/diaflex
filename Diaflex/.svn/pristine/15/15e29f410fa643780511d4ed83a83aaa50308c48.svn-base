<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd
%@ page errorPage="../error_page.jsp" %>
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import="java.util.ArrayList,java.util.HashMap,java.sql.ResultSet,ft.com.*, java.util.Date, java.util.ArrayList, ft.com.dao.*"%>
<jsp:useBean id="util" class="ft.com.JspUtil" scope="page" />
<jsp:useBean id="info" class="ft.com.InfoMgr" scope="session" />
<%@taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>
<%@taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
<title>Delete Receipt Stock</title>
<link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/bse.css?v=<%=info.getJsVersion()%>"/>
<link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/style.css?v=<%=info.getJsVersion()%>"/>
<script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/bse.js?v=<%=info.getJsVersion()%> " > </script>
</head>
<%String logId=String.valueOf(info.getLogId());
String onfocus="cook('"+logId+"')";
%>
<body onfocus="<%=onfocus%>" onload="MM_preloadImages('images/faunalogo.png')" topmargin="0" leftmargin="0" rightmargin="0" bottommargin="0">
<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0" class="mainbg">
<tr>
<td height="103" valign="top">
<jsp:include page="/header.jsp" />

</td>
</tr>

<tr>
<td height="5" valign="top" class="greyline"><img src="<%=info.getReqUrl()%>/images/grey_linebig.png" width="685" height="5" align="right" /></td>
</tr>
<tr>
<td valign="top" class="hedPg">
<table cellpadding="1" cellspacing="5"><tr><td valign="middle">
<img src="../images/line.jpg" border="0" /></td><td valign="middle"> <span class="pgHed">Delete Receipt Stock</span> </td>
</tr></table>
</td>
</tr>
<%
HashMap allPageDtl= (info.getPageDetails() == null)?new HashMap():(HashMap)info.getPageDetails();
HashMap pageDtl=(HashMap)allPageDtl.get("DELETE_RECEIPT");
ArrayList pageList=new ArrayList();
HashMap pageDtlMap=new HashMap();
String fld_nme="",fld_ttl="",val_cond="",dflt_val="",fld_typ="",form_nme="";
ArrayList msg = (ArrayList)request.getAttribute("msg");
if(msg!=null && msg.size()>0){
for(int i=0 ; i <msg.size(); i++){
%>
<tr><td class="tdLayout" valign="top"><span class="redLabel"> <%=msg.get(i)%></span></td></tr>
<%}}%>
<tr><td class="tdLayout" valign="top">
<html:form action="fileloaders/uploadFile.do?method=delStk" method="post" >
<table>
<tr><td><%
pageList= ((ArrayList)pageDtl.get("RADIO") == null)?new ArrayList():(ArrayList)pageDtl.get("RADIO");
if(pageList!=null && pageList.size() >0){
for(int j=0;j<pageList.size();j++){
pageDtlMap=(HashMap)pageList.get(j);
fld_nme="value("+(String)pageDtlMap.get("fld_nme")+")";
fld_ttl=(String)pageDtlMap.get("fld_ttl");
fld_typ=(String)pageDtlMap.get("fld_typ");
form_nme=(String)pageDtlMap.get("form_nme");
dflt_val=(String)pageDtlMap.get("dflt_val");
val_cond=(String)pageDtlMap.get("val_cond"); %>
<html:radio property="<%=fld_nme%>" name="<%=form_nme%>" styleId="<%=fld_typ%>" value="<%=dflt_val%>"/><%=fld_ttl%>
<%}}%>
</td></tr>
<tr><td><html:textarea property="value(recNum)" name="fileUploadForm" cols="24" rows="5" styleId="recNum"/> </td> </tr>
<tr><td><html:submit property="value(delStk)" styleClass="submit" value="Delete Receipt Stock" onclick="return validateRecNo() ;" /> </td></tr>
</table></html:form>
</td></tr>
<tr>
<td><jsp:include page="/footer.jsp" /> </td>
</tr>
</table>

</body>
</html>