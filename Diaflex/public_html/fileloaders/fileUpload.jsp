<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page errorPage="../error_page.jsp" %>
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import="java.util.ArrayList,java.util.HashMap,java.sql.ResultSet,ft.com.*, java.util.Date, java.util.ArrayList, ft.com.dao.*"%>
<jsp:useBean id="util" class="ft.com.JspUtil" scope="page" />
<jsp:useBean id="info" class="ft.com.InfoMgr" scope="session" />
<%@taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>
<%@taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>File Upload</title>
  <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/bse.css?v=<%=info.getJsVersion()%>"/>
    <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/style.css?v=<%=info.getJsVersion()%>"/>
    <script type="text/javascript" src="<%=info.getReqUrl()%>/PopScripts/popup.js?v=<%=info.getJsVersion()%> " > </script>
     <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/bse.js?v=<%=info.getJsVersion()%> " > </script>
     <script language="javaScript" type="text/javascript" src="<%=info.getReqUrl()%>/calendar/calendar.js"></script>
   <link href="<%=info.getReqUrl()%>/calendar/calendar.css" rel="stylesheet" type="text/css" />
    <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/general.css?v=<%=info.getJsVersion()%>"/>
    <script src="<%=info.getReqUrl()%>/PopScripts/jquery-1.2.6.min.js" type="text/javascript"></script>
  </head>
        <%
        String logId=String.valueOf(info.getLogId());
        String onfocus="cook('"+logId+"')";
        %>
 <body onfocus="<%=onfocus%>" onload="MM_preloadImages('images/faunalogo.png')" topmargin="0" leftmargin="0" rightmargin="0" bottommargin="0">
 <img src="../images/loadbig.gif" vspace="15" id="load" style="display:none;" class="loadpktDiv" border="0" />
  <div id="backgroundPopup"></div>
<div id="popupContactSave" align="center" >
<img src="../images/loading_flame.gif" height="150px" width="150px" border="0" />
</div>
<table width="100%"  border="0" align="center" cellpadding="0" cellspacing="0" class="mainbg">
  <tr>
    <td height="103" valign="top">
   <jsp:include page="/header.jsp" />
    
    </td>
  </tr>
 
  <tr>
    <td height="5" valign="top" class="greyline"><img src="<%=info.getReqUrl()%>/images/grey_linebig.png" width="685" height="5" align="right" /></td>
  </tr>
  <%
  String msg = (String)request.getAttribute("fileMsg");
  if(msg!=null){
  int accessidn=Integer.parseInt(util.nvl((String)request.getAttribute("accessidn"),"0"));
  String url = (String)request.getAttribute("url");
  url=url+"&p_access="+accessidn;
  %>
   <tr>
  <td valign="top" class="hedPg">
  <div><span class="redLabel"> No of Line of File upload = <%=request.getAttribute("lineNo")%></span></div>
  <div>
  <span class="redLabel">
  <%=msg%></span>
  </div>
  </td></tr>
  <tr>
  <td valign="top" class="hedPg">
   <a href="<%=url%>"  target="_blank"><span class="txtLink" >Click Here for Report</span></a> 
  </td></tr>
  
  <%}
    ArrayList fileUploadList=(ArrayList)request.getAttribute("fileUploadList");
    %>
  <tr>
  <td valign="top" class="hedPg">
  <table cellpadding="1" cellspacing="5"><tr><td valign="middle">
  <img src="../images/line.jpg" border="0" /></td><td valign="middle"> <span class="pgHed">
 File Upload
 </span> </td>
</tr></table>
  </td>
  </tr>
  <tr><td class="tdLayout" valign="top">
  <table>
  <html:form action="fileloaders/uploadFile.do?method=upload" method="post" enctype="multipart/form-data" onsubmit="loading();">
<html:hidden property="value(filetyp)" />
  <%
  for(int i=0;i<fileUploadList.size();i++){
  FileUploadDao dao=(FileUploadDao)fileUploadList.get(i);
  %> 
  <input type="hidden" name="<%=dao.getFileTyp()%>" id="<%=dao.getFileTyp()%>" value="<%=dao.getFileExt()%>">
 
  <%
  
  }
  %>
  <tr>
  <td>File Upload Type: </td>
  <td><html:select property="value(typ)" name="fileUploadForm" styleId="filety" >
  <html:optionsCollection property="uploadList" name="fileUploadForm" value="fileTyp" label="fileDsc" />
  
  </html:select>
  </td>
  
  
  
  <td>
  <html:file property="fileUpload" name="fileUploadForm"  styleId="fileUpload"  onchange="check_file();"/>
  
  
  </td>
 </tr>
 <%if(info.getDmbsInfoLst().get("CNT").equals("jb")){%>
 <tr>
  <td>Reciept Date :</td><td colspan="2"> <div class="float"><html:text property="value(dte)"  styleId="dte" /></div>
  <div style="float:left">     <a href="#"  onClick="setYears(1900, <%=info.getCurrentYear()%>);showCalender(this, 'dte');">
  <img src="<%=info.getReqUrl()%>/images/calender.png"></a></div>
  </td>
  </tr>
  <tr>
  <td>Dispatch Date:</td><td colspan="2"> <div class="float"><html:text property="value(disdte)"  styleId="disdte" /></div>
  <div style="float:left">     <a href="#"  onClick="setYears(1900, <%=info.getCurrentYear()%>);showCalender(this, 'disdte');">
  <img src="<%=info.getReqUrl()%>/images/calender.png"></a></div>
  </td>
  </tr>
   <tr>
  <td>Dispatch Location  :</td><td colspan="2"> 
  <html:text property="value(rctLoc)"  styleId="rctLoc" />
  </td>
  </tr>
 
  <%}%>
  <tr>
  <td colspan="2"> <html:submit property="upload" value="File Upload" styleClass="submit"/> </td>
  </tr>
  </html:form>
  </table>
  </td>
  </tr>
  <tr>
    <td><jsp:include page="/footer.jsp" /> </td>
    </tr>
  </table>
   <%@include file="../calendar.jsp"%>
  </body></html>