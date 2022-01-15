<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page errorPage="../error_page.jsp" %>
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import="java.util.ArrayList,java.util.ArrayList, java.util.Iterator,java.util.List,java.io.*, java.sql.Date,java.util.HashMap, ft.com.marketing.PacketLookupForm" %>
<jsp:useBean id="info" class="ft.com.InfoMgr" scope="session" />
<jsp:useBean id="util" class="ft.com.JspUtil" scope="page" />
<%@taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>
<%@taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>Packet Details</title>
    <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/bse.css?v=<%=info.getJsVersion()%>"/>
      <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/style.css?v=<%=info.getJsVersion()%>"/>
      <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/memoScript.js?v=<%=info.getJsVersion()%> " > </script>
                    <script src="<%=info.getReqUrl()%>/PopScripts/jquery-1.2.6.min.js" type="text/javascript"></script>
             <script src="<%=info.getReqUrl()%>/jqueryScript/jqueryScript.js?v=<%=info.getJsVersion()%>" type="text/javascript"></script>
              <script src="<%=info.getReqUrl()%>/jqueryScript/jquery.min.js" type="text/javascript"></script>  
        <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/bse.js?v=<%=info.getJsVersion()%> " > </script>
        </head>
        <%String logId=String.valueOf(info.getLogId());
        String onfocus="cook('"+logId+"')";
        %>
 <body onfocus="<%=onfocus%>">
   <%
  String vnm = util.nvl((String)request.getAttribute("vnm"));
  String View = util.nvl((String)request.getAttribute("View"));
  HashMap pktDtl = (HashMap) request.getAttribute("pktDtl");
  ArrayList ViewPrp = (ArrayList)session.getAttribute("PLOT_VW");
  HashMap mprp = info.getMprp();
  HashMap dtls=new HashMap();
  ArrayList imagelistDtl= (info.getImagelistDtl() == null)?new ArrayList():(ArrayList)info.getImagelistDtl();
 if(View!=null){
 if(pktDtl!=null && pktDtl.size()>0){
 %>
 <div style="float:left; margin-left:50px;" class="tdLayout">
 <table class="grid1"><tr><th>Property</th><th>Value</th></tr>
 <tr><td>Packet Id</td><td><%=vnm%></td></tr>
 <%
 for(int i=0;i<ViewPrp.size();i++){
String prp = (String)ViewPrp.get(i); 
String prpDsc = (String)mprp.get(prp+"D");
String val=util.nvl2((String)pktDtl.get(prp),"-");
%>
<tr>
<td><%=prpDsc%></td>
<td><%=val%></td>
</tr>
<%}%>
</table></div>
<div style="float:right;">
<table><tr>
 <td align="center">
 <%if(imagelistDtl!=null && imagelistDtl.size() >0){
for(int j=0;j<imagelistDtl.size();j++){
dtls=new HashMap();
dtls=(HashMap)imagelistDtl.get(j);
String typ=util.nvl((String)dtls.get("TYP"));
String allowon=util.nvl((String)dtls.get("ALLOWON"));
if(typ.equals("I") && (allowon.indexOf("TRF") > -1)){
String path=util.nvl((String)dtls.get("PATH"));
String gtCol=util.nvl((String)dtls.get("GTCOL"));
String val=util.nvl((String)pktDtl.get(gtCol));
if(!val.equals("N")){
path=path+val;
%>
<img src="<%=path%>" class="imgEach" style="margin-left:80px;" onerror="this.onerror=null;this.src='';" />
<%}}}}%>
 </td>
 </tr>
 </table>
 </div>
 
 
<%}else{%>
 <table>
 <tr>
 <td class="tdLayout"> Sorry No Result Found</td>
 </tr>
 </table>
 
 <%}}%>
  
  </body>
</html>