<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=windows-1252"%>
<jsp:useBean id="info" class="ft.com.InfoMgr" scope="session" />
<jsp:useBean id="util" class="ft.com.JspUtil" scope="page" />

<%@ page import="java.util.ArrayList,java.util.HashMap,java.util.ArrayList,ft.com.dao.Mprc"%>
<%@taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>

<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>DeptSmry</title>
    
    <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/bse.css?v=<%=info.getJsVersion()%>"/>
    <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/general.css?v=<%=info.getJsVersion()%>"/>
    <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/style.css?v=<%=info.getJsVersion()%>"/>
    <script src="../scripts/bse.js" type="text/javascript"></script>
    <script src="../scripts/ajax.js" type="text/javascript"></script>
    
    <script language="Javascript">
    function displayiFrame(link, i)
      {
      //alert(link);
          document.getElementById('tr_'+i).style.display='';
          window.open(link, 'ifrm_'+i);
      }
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
    <td height="5" valign="top" class="greyline" ><img src="../images/grey_linebig.png" width="685" height="5" align="right" /></td>
  </tr>
  <tr>
  <td valign="top" class="hedPg">
  <table cellpadding="1" cellspacing="5"><tr><td valign="middle">
  <img src="../images/line.jpg" border="0" /></td><td valign="middle"> <span class="pgHed">Dept. Process Summary </span> </td>
</tr></table>
  </td>
  </tr>
  <tr><td>
  
  <tr><td valign="top" class="tdLayout">
   <%

   HashMap pktDtlList = (HashMap)request.getAttribute("pktDtlList");
   if(pktDtlList!=null && pktDtlList.size()>0){
  
   %>
   <table class="grid1">
   <tr><th>Process</th>
   <%
   ArrayList deptList = (ArrayList)request.getAttribute("deptList");
   if(deptList!=null && deptList.size()>0){
   for(int i=0;i<deptList.size();i++){
   String dept = (String)deptList.get(i);
   %>
   <th colspan="4"><%=dept%></th>
   <%}%>
   </tr>
   <tr><td></td>
  <%for(int n=0;n<deptList.size();n++){%>
   <td colspan="2">Available </td><td colspan="2">Issue</td> 
   <%}%>
   </tr>
   <tr><td></td>
   <%for(int n=0;n<deptList.size();n++){%>
   <td>Pcs</td><td>Cts</td><td>Pcs</td><td>Cts</td>
   
   <%}%>
   </tr>
   
   <%
   ArrayList mprcList = (ArrayList)request.getAttribute("mprcList");
    for(int j=0;j<mprcList.size();j++){
    Mprc prc = (Mprc)mprcList.get(j);
    String prc_idn = prc.getMprcId();
    String prcDsc = prc.getPrc();
    %>
    <tr><td>
    <%if(prcDsc.equals("Pending for Receive")){%>
    <a href="pendingRcv.do?method=load&prc_id=<%=prc_idn%>"> <%=prc.getPrc()%></a>
    <%}else{%>
     <%=prc.getPrc()%>
    <%}%>
    </td>
    <%
   for(int k=0;k<deptList.size();k++){
   String deptNme = (String)deptList.get(k);
   String key = deptNme+"_"+prc_idn;
   %>
    <td><%=util.nvl((String)pktDtlList.get(key+"_AV_QTY"))%></td><td><%=util.nvl((String)pktDtlList.get(key+"_AV_CTS"))%></td>
    <td><%=util.nvl((String)pktDtlList.get(key+"_IS_QTY"))%></td><td><%=util.nvl((String)pktDtlList.get(key+"_IS_CTS"))%></td>
  
   <%}%>
   </tr>
   <%}%>
  
   <%}%>
   </table>
   <%}else{%>
   Sorry no result found.
   <%}%>
  </td></tr>
  
  <tr><td>&nbsp;</td></tr>
  <tr><td>&nbsp;</td></tr>
  
  </td></tr>
   <tr>
    <td><jsp:include page="/footer.jsp" /> </td>
    </tr>
  </table>

  </body>
</html>