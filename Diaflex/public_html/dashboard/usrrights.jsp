<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page errorPage="../error_page.jsp" %>
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import="java.util.ArrayList,java.util.HashMap,java.sql.ResultSet,ft.com.*, java.util.Date, java.util.ArrayList, ft.com.dao.*"%>
<jsp:useBean id="info" class="ft.com.InfoMgr" scope="session" />
<jsp:useBean id="util" class="ft.com.JspUtil" scope="page" />
<%@taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>
<%@taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>User Page Role Details</title>
  <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/bse.css?v=<%=info.getJsVersion()%>"/>
    <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/style.css?v=<%=info.getJsVersion()%>"/>
     <script language="javaScript" type="text/javascript" src="<%=info.getReqUrl()%>/calendar/calendar.js"></script>
   <link href="<%=info.getReqUrl()%>/calendar/calendar.css" rel="stylesheet" type="text/css" />
   <script language="javaScript" type="text/javascript" src="<%=info.getReqUrl()%>/scripts/bse.js?v=<%=info.getJsVersion()%>"></script>
  <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/ajax.js?v=<%=info.getJsVersion()%>" ></script>
  <link href="<%=info.getReqUrl()%>/css/style123.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="<%=info.getReqUrl()%>/jquery/jquery-freezar.min.js"></script>
  <script language="javaScript" type="text/javascript" src="<%=info.getReqUrl()%>/jquery/jquery.freezeheader.js?v=<%=info.getJsVersion()%>"></script>
  <script type="text/javascript">
   $(document).ready(function () {
	    $("#table2").freezeHeader({ 'height': '500px' });
    })
 </script>
  </head>
        <%String logId=String.valueOf(info.getLogId());
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
    <td height="5" valign="top" class="greyline"><img src="<%=info.getReqUrl()%>/images/grey_linebig.png" width="685" height="5" align="right" /></td>
  </tr>
   <tr>
  <td valign="top" class="hedPg">
  <table cellpadding="1" cellspacing="5"><tr><td valign="middle">
  <img src="../images/line.jpg" border="0" /></td><td valign="middle"> <span class="pgHed">User Page Role Details</span> </td>
</tr></table>
  </td>
  </tr>
  <%
  ArrayList dfpgdtl = (session.getAttribute("DFPG") == null)?new ArrayList():(ArrayList)session.getAttribute("DFPG");
  ArrayList dfusersdtl = (session.getAttribute("DFUSRS") == null)?new ArrayList():(ArrayList)session.getAttribute("DFUSRS");
  ArrayList dfpgitmdtl = (session.getAttribute("DFPGITM") == null)?new ArrayList():(ArrayList)session.getAttribute("DFPGITM");
  String view = util.nvl((String)request.getAttribute("view"));
  int dfpgdtlsz=dfpgdtl.size();
  HashMap dtl=new HashMap();
  int sr=0;
  %>
  <html:form action="dashboard/usrright.do?method=fetchDf" method="post" enctype="multipart/form-data">
  <tr>
  <td valign="top" class="hedPg">
  <table><tr><td> Pages :
  <html:select property="value(pg)" styleId="pg"  name="userrightsform">
  <%for(int i=0;i<dfpgdtlsz;i++){
  dtl=new HashMap();
  dtl=(HashMap)dfpgdtl.get(i);
  String lKey = (String)dtl.get("PGIDN");
  String ldspVal = (String)dtl.get("DSC");%>
  <html:option value="<%=lKey%>"><%=ldspVal%></html:option>
  <%}%>
  </html:select>
  </td>
  <td>&nbsp;&nbsp;<html:submit property="value(srch)" value="Fetch" styleClass="submit" /></td>
  </tr>
  </table></td></tr>
  </html:form>
  <%if(!view.equals("")){
      int dfpgitmdtlsz=dfpgitmdtl.size();
      int dfusersdtlsz=dfusersdtl.size();
  %>
   <tr>
  <td class="tdLayout" valign="top">
  <span class="redTxt">*</span>
  <span>Please Check Checkbox To InActive</span>
  </td>
  </tr>
   <tr><td>
 <table>
 <tr>
 <td valign="top" class="hedPg">
<table class="grid1" id="table2">
<thead>
  <tr>
  <th>Users/Items</th>
  <%for(int i=0;i<dfpgitmdtlsz;i++){
  dtl=new HashMap();
  dtl=(HashMap)dfpgitmdtl.get(i);
  String ttl=(String)dtl.get("FLDTTL");
  String pgitmidn=(String)dtl.get("PGITMIDN");
  String fldVal = "value("+pgitmidn+")";
  String checkFldId = "CHK_"+pgitmidn;
  String onChange = "savepageUserRightAll("+pgitmidn+",this)";
  %>
  <th nowrap="nowrap"><%=ttl%>
  <html:checkbox  property="<%=fldVal%>" value="<%=pgitmidn%>" styleId="<%=checkFldId%>" name="userrightsform" onchange="<%=onChange%>"/>
  </th>
  <%
  }
  %>
  </tr>
  </thead>
<tbody>
  <%for(int i=0;i<dfusersdtlsz;i++){
  dtl=new HashMap();
  dtl=(HashMap)dfusersdtl.get(i);
  String USR=(String)dtl.get("USR");
  String usrIdn=(String)dtl.get("USR_ID");
  String target="TR"+usrIdn;
  %>
  <tr  id="<%=target%>" ondblclick="setBGColorOnClickTR('<%=target%>')">
  <td><%=USR%></td>
  <%for(int j=0;j<dfpgitmdtlsz;j++){
  dtl=new HashMap();
  dtl=(HashMap)dfpgitmdtl.get(j);
  String pgitmidn=(String)dtl.get("PGITMIDN");
  String ttl=(String)dtl.get("FLDTTL");
String fldName = usrIdn+"_"+pgitmidn;
String fldVal = "value("+fldName+")";
String checkFldId = "CHK_"+usrIdn+"_"+pgitmidn;
String onChange = "savepageUserRight("+pgitmidn+","+usrIdn+",this)";
sr++;
  %>
  <td align="center">
  <html:checkbox  property="<%=fldVal%>" value="<%=fldName%>" styleId="<%=checkFldId%>" name="userrightsform" onchange="<%=onChange%>"/>
  </td>
  <%}%>
  </tr>
  <%}%>
  </tbody>
  </table>
  <input type="hidden" name="count" id="count" value="<%=sr%>" />
  </td>
  </tr>
  <%}%>
  <tr>
    <td><jsp:include page="/footer.jsp" /> </td>
    </tr>
</table></td></tr></table>
 <%@include file="../calendar.jsp"%>
</body>
</html>