<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page errorPage="../error_page.jsp" %>
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import="java.util.ArrayList,java.util.ArrayList,java.util.HashMap, java.util.Enumeration, java.util.logging.Level"%>
<jsp:useBean id="info" class="ft.com.InfoMgr" scope="session" />
<jsp:useBean id="util" class="ft.com.JspUtil" scope="page" />
<%@taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
 
<html>
  <head>
 <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
  
  <title>Pending Mix Report</title>
  <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/bse.js?v=<%=info.getJsVersion()%> " > </script>
<link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/bse.css?v=<%=info.getJsVersion()%>"/>
<script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/ajax.js?v=<%=info.getJsVersion()%> " > </script>
     <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/general.css?v=<%=info.getJsVersion()%>"/>
     <script type="text/javascript" src="<%=info.getReqUrl()%>/PopScripts/popup.js?v=<%=info.getJsVersion()%> " > </script>
   <script src="<%=info.getReqUrl()%>/PopScripts/jquery-1.2.6.min.js" type="text/javascript"></script>

 
      <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/style.css?v=<%=info.getJsVersion()%>"/>
  </head>
        <%String logId=String.valueOf(info.getLogId());
        String onfocus="cook('"+logId+"')";
        %>
 <body onfocus="<%=onfocus%>" onload="MM_preloadImages('images/faunalogo.png')" topmargin="0" leftmargin="0" rightmargin="0" bottommargin="0">
<div id="backgroundPopup"></div>
<div id="popupContactASSFNL">
<table class="popUpTb">
 <tr><td height="5%">&nbsp;&nbsp;&nbsp; </td></tr>
<tr><td valign="top" height="95%">
<%

  String formAction = info.getReqUrl()+"/genericexcelutil" ;
   String mdl =util.nvl((String)request.getAttribute("mdl"));
  %>
  <form action="<%=formAction%>" method="POST">
  <input type="hidden" name="filename" value="PendingMixFile" />
 
<jsp:include page="/excelFilter.jsp" />
</form>
</td></tr>
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
  <img src="../images/line.jpg" border="0" /></td><td valign="middle"> <span class="pgHed">
 Pending Mix Report</span> </td>
</tr></table>
  </td>
  </tr>
   <tr><td valign="top" class="hedPg"  >Create Excel <img src="../images/ico_file_excel.png" id="img" onclick="loadASSFNLPop('img')" /> 
   <%
  ArrayList rolenmLst=(ArrayList)info.getRoleLst();
  String usrFlg=util.nvl((String)info.getUsrFlg());
  if(usrFlg.equals("SYS") || rolenmLst.contains("ADMIN")){
  %>
  &nbsp;&nbsp;<img src="../images/edit.jpg" title="Edit Properties Module" onclick="newWindow('<%=info.getReqUrl()%>/dynamicProperty.do?method=load&mdl=BOX_MIX&sname=BOX_MIX&par=A')" border="0" width="15px" height="15px"/>
  <%}%>
   </td></tr>
  <tr><td valign="top" class="hedPg"  >
  <display:table name="sessionScope.pendingMixForm.pktDtlList"   class="displayTable">
  
    <display:column property="Count"  title="SR NO."  />
   
    <display:column property="vnm"  title="Packet Code" sortable="true"/>
   
    <display:column property="qty"   title="Qty"  />
   
    
    <%
    ArrayList vwPrpLst = (ArrayList)session.getAttribute("BOX_MIX");
    ArrayList prpDspBlocked = info.getPageblockList();
    for(int i = 0; i < vwPrpLst.size(); i++)
    {
     String fld = util.nvl((String)vwPrpLst.get(i));
     if(prpDspBlocked.contains(fld)){
     }else{
     if(fld.equals("BOX_TYP")){%>
      <display:column property="<%=fld%>"   title="<%=fld%>"  group="1" />
     <%}else{%>
    <display:column property="<%=fld%>"   title="<%=fld%>"  />
    <%}
    }}
    %>

  </display:table>
  </td></tr>
  <tr>
    <td><jsp:include page="/footer.jsp" /> </td>
    </tr>
  </table>
  
  </body>
</html>