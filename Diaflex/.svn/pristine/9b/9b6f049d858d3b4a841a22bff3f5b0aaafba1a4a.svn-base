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
     <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/bse.js?v=<%=info.getJsVersion()%> " > </script>
    
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
  <img src="../images/line.jpg" border="0" /></td><td valign="middle"> <span class="pgHed">File Load Status</span> </td>
   </tr></table>
  </td>
  </tr>
  <tr><td class="tdLayout" valign="top">
    <html:form action="fileloaders/uploadFile.do?method=getUploadStt" method="post">
  <table>
  <tr><td colspan="2"><table><tr><td> <html:radio property="value(typ)"  value="MFG" /> </td><td>
  MFG File</td><td><html:radio property="value(typ)" value="LAB" /></td>
  <td>LAB File </td> 
  <td><html:radio property="value(typ)" value="MKT" /></td>
  <td>MKT File </td> 
  <%if(info.getDmbsInfoLst().get("CNT").equals("va")){%>
  <td><html:radio property="value(typ)" value="MKTG_FTP" /></td>
  <td>MKT Ftp File </td> 
  <td><html:radio property="value(typ)" value="MKTG_UPD" /></td>
  <td>Marketing Update</td> 
  <%}%>
  </tr></table></td>
  </tr>
  <tr><td>Sequence Number: </td><td><html:text property="value(seqNum)"  name="fileUploadForm" /> </td> </tr>
  
 <tr> <td><html:submit property="value(sttRt)" styleClass="submit" value="Status" /> </td>
  </tr>
  </table></html:form>
  </td></tr>
  <tr><td class="tdLayout" valign="top" height="5"></td></tr>
  <tr><td class="tdLayout" valign="top">
  
  <%
  ArrayList pktDtlList = (ArrayList)request.getAttribute("pktDtlList");
  String view = (String)request.getAttribute("view");
  if(view!=null){
  if(pktDtlList!=null && pktDtlList.size()>0){
  String typ = util.nvl((String)request.getAttribute("typ"));
  %>
  <table><tr><td valign="top">
  <table class="grid1">
  <tr><th><B>Status</b></th><th><B>No. Of Stone</b></th> </tr>
  <%for(int i=0;i<pktDtlList.size();i++){
  ArrayList pktDtl = (ArrayList)pktDtlList.get(i);
  String stt = util.nvl((String)pktDtl.get(0));
  String flg = util.nvl((String)pktDtl.get(2));
  %>
  <tr><td><%if(flg.equals("E") || flg.equals("I") ){%>
  <A href="uploadFile.do?method=uploadError&flg=<%=flg%>" target="frame"> <%=stt%></a>
  <%}else{%> 
  <%=stt%>
  <%}%>
  </td><td><%=pktDtl.get(1)%></td> </tr>
  <%}%>
  </table>
  </td><td class="tdLayout" valign="top">
  <iframe name="frame" frameborder="0" width="300" height="400">
  </iframe>
  </td></tr></table>
  <%}else{%>
  Sorry no data found.
  <%}}%>
  </td></tr>
  <tr>
    <td><jsp:include page="/footer.jsp" /> </td>
    </tr>
  </table>
  
  
  </body>
</html>