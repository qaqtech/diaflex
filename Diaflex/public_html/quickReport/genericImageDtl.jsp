<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import="java.util.ArrayList,java.util.ArrayList,java.util.HashMap, java.sql.*, java.util.Enumeration, java.util.logging.Level"%>
<jsp:useBean id="info" class="ft.com.InfoMgr" scope="session" />
<jsp:useBean id="util" class="ft.com.JspUtil" scope="page" />
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>Packet Detail</title>
    <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/bse.js?v=<%=info.getJsVersion()%> " > </script>
<link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/bse.css?v=<%=info.getJsVersion()%>"/>
<script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/ajax.js?v=<%=info.getJsVersion()%> " > </script>
  

 
      <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/style.css?v=<%=info.getJsVersion()%>"/>
  </head>
        <%String logId=String.valueOf(info.getLogId());
        String onfocus="cook('"+logId+"')";
        %>
 <body onfocus="<%=onfocus%>" onload="MM_preloadImages('images/faunalogo.png')" topmargin="0" leftmargin="0" rightmargin="0" bottommargin="0">
    <table>
   <%
   String excl = util.nvl((String)request.getAttribute("EXCL"));
  ArrayList pktDtlList = (ArrayList)session.getAttribute("pktList");
  if(pktDtlList!=null && pktDtlList.size()>0){
  ArrayList imagelistDtl= (info.getImagelistDtl() == null)?new ArrayList():(ArrayList)info.getImagelistDtl();
  ArrayList itemHdr = (ArrayList)session.getAttribute("itemHdr");
  HashMap dtls=new HashMap();
    if(!excl.equals("NO")){%>
 <tr>
 <td valign="top" class="hedPg">   Create Excel &nbsp;&nbsp;&nbsp;
  <img src="../images/ico_file_excel.png" border="0" onclick="goTo('<%=info.getReqUrl()%>/quickReport/genericReport.do?method=createXL&imgCol=Y','','')"/>
   &nbsp;&nbsp;Dowenload Images
  <img src="../images/ico_file_excel.png" border="0" onclick="goTo('<%=info.getReqUrl()%>/quickReport/genericReport.do?method=bulkDowenload','','')"/>
 </td>
 </tr>
  <%}%>
  <tr><td valign="top" class="hedPg">
  <table class="dataTable" align="left" id="dataTable">
  <thead>
  <tr>
  <%for(int i=0 ; i < itemHdr.size() ;i++){%>
  <th><%=itemHdr.get(i)%></th>
  <%}%>
<%if(imagelistDtl!=null && imagelistDtl.size() >0){
for(int j=0;j<imagelistDtl.size();j++){
dtls=new HashMap();
dtls=(HashMap)imagelistDtl.get(j);
String gtCol=util.nvl((String)dtls.get("GTCOL"));
String hdr=util.nvl((String)dtls.get("HDR"));
%>
<th><%=hdr.toUpperCase()%></th>
<%}}%>
  </tr>
  </thead>
  <tbody>
  <%for(int j=0 ; j <pktDtlList.size() ;j++){
  HashMap pktDtl = (HashMap)pktDtlList.get(j);
  %>
  <tr>
  <%for(int k=0;k<itemHdr.size() ;k++){
  String hdr = (String)itemHdr.get(k);
  
  %>
  <td><%=util.nvl((String)pktDtl.get(hdr))%></td>
  <%}%>
  <%if(imagelistDtl!=null && imagelistDtl.size() >0){
for(int k=0;k<imagelistDtl.size();k++){
dtls=new HashMap();
dtls=(HashMap)imagelistDtl.get(k);
String path=util.nvl((String)dtls.get("PATH"));
String gtCol=util.nvl((String)dtls.get("GTCOL")).toUpperCase();
String val=util.nvl((String)pktDtl.get(gtCol));%>
<td>
<%if(!val.equals("N") && !gtCol.equals("VIDEOS")){
if (val.indexOf("&") > -1) {
val = val.replaceAll("&", "%26");
}
if(path.indexOf("segoma") > -1 || path.indexOf("sarineplatform") > -1)
path=path+val;
else if(val.indexOf(".pdf") < 0){
path="../zoompic.jsp?fileNme="+path+val+"&ht=800&wd=1100";
}else{
path=path+val;
}
String url = "<A href='"+path+"' target=\"_blank\" ><img src=\"../images/viewimage_dia.png\" border=\"0\" /></a>";
%>
<%=url%>
<%}else{%>
<%=val%>
<%}%>
</td>
<%}}%>
  </tr>
  <%}%>
  </tbody>
  </table></td></tr>

  <%}%>
  </table>
  </body>
</html>