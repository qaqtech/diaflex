<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page errorPage="../error_page.jsp" %>
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.ResultSet, java.util.ArrayList,java.util.Collections,java.util.ArrayList,java.util.HashMap,java.text.SimpleDateFormat, java.util.Calendar"%>
<jsp:useBean id="info" class="ft.com.InfoMgr" scope="session" />
<jsp:useBean id="util" class="ft.com.JspUtil" scope="page" />
<%@taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>
<%@taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<html> 
   <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/bse.js?v=<%=info.getJsVersion()%> " > </script>
<link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/bse.css?v=<%=info.getJsVersion()%>"/>
<link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/general.css?v=<%=info.getJsVersion()%>"/>
<script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/ajax.js?v=<%=info.getJsVersion()%> " > </script>
  <script type="text/javascript" src="<%=info.getReqUrl()%>/PopScripts/popup.js?v=<%=info.getJsVersion()%> " > </script>
    <script src="<%=info.getReqUrl()%>/PopScripts/jquery-1.2.6.min.js" type="text/javascript"></script>
   <script language="javaScript" type="text/javascript" src="<%=info.getReqUrl()%>/calendar/calendar.js"></script>
   <link href="<%=info.getReqUrl()%>/calendar/calendar.css" rel="stylesheet" type="text/css" />
<link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/style.css?v=<%=info.getJsVersion()%>"/>
<title>Memo Shape Report</title>
</head>
        <%String logId=String.valueOf(info.getLogId());
        String onfocus="cook('"+logId+"')";
        %>
 <body onfocus="<%=onfocus%>" onload="MM_preloadImages('images/faunalogo.png')" topmargin="0" leftmargin="0" rightmargin="0" bottommargin="0">
    <table width="100%"  border="0" align="center" cellpadding="0" cellspacing="0" >
  <tr>
    <td height="5" valign="top" class="greyline"><img src="../images/grey_linebig.png" width="685" height="5" align="right" /></td>
  </tr>
  <tr>
  <td valign="top" class="hedPg">
  <table cellpadding="1" cellspacing="5"><tr><td valign="middle">
  <img src="../images/line.jpg" border="0" /></td><td valign="middle"> 
  <span class="pgHed">Memo Shape Report
  </span></td></tr></table>
  </td></tr>
    <%
    HashMap dataDtl= (HashMap)session.getAttribute("dataDtl");
    ArrayList memoLst= (ArrayList)session.getAttribute("memoLst");
    ArrayList srchids= (ArrayList)request.getAttribute("srchids");
    String key="",qty="",vlu="",dis="",shper="";
    if(memoLst!=null && memoLst.size()>0){
    ArrayList shapeLst=(ArrayList)dataDtl.get("SHAPE");
    int shapeLstsz=shapeLst.size();
    ArrayList szLst=new ArrayList();
    String grantvlu=util.nvl((String)dataDtl.get("TTLVLU"));
    double grantvludo=Double.parseDouble(grantvlu);
    double per=0;
    if(srchids!=null && srchids.size()>0){
    String dsc=util.nvl((String)request.getAttribute("srchDscription"));
    %>
    <tr><td valign="top" class="hedPg"><span class="txtLink" >Search Description : <%=dsc%></span>
    <td></tr>
    <%}%>
    <tr>
    <td valign="top" class="hedPg">
    <table class="grid1">
    <tr>
    <th></th>
    <%for(int j=0;j<shapeLstsz;j++){
    %>
    <th colspan="2"><%=util.nvl((String)shapeLst.get(j))%></th>
    <%}%>
    <th colspan="2">Grand Total</th>
    </tr>
    <%for(int k=0;k<memoLst.size();k++){
    String memoVal=util.nvl((String)memoLst.get(k));
    double grandmemovlu=0;
    double percent=0;
    String memowisevlu=util.nvl((String)dataDtl.get(memoVal+"@VLU"));
    grandmemovlu=Double.parseDouble(memowisevlu);
    %>
    <tr>
    <th><%=memoVal%></th>
    <%for(int l=0;l<shapeLstsz;l++){
    String shVal=util.nvl((String)shapeLst.get(l));
    key=memoVal+"@"+shVal;
    vlu=util.nvl((String)dataDtl.get(key+"@VLU"));
    dis=util.nvl((String)dataDtl.get(key+"@DIS"));
    shper="";
    if(!vlu.equals(""))
    shper=String.valueOf(util.roundToDecimals(((Double.parseDouble(vlu)/grandmemovlu)*100),2))+"%";
    %>
    <td align="right"><%=vlu%></td><td align="right"><%=shper%></td>
    <%}
    per=util.roundToDecimals(((grandmemovlu/grantvludo)*100),2);
    %>
    <td align="right"><%=memowisevlu%></td>
    <td align="right"><%=per%>%</td>
    </tr>
    <%szLst=new ArrayList();
    szLst=(ArrayList)dataDtl.get(memoVal+"@SZ");
    for(int p=0;p<szLst.size();p++){
    String szVal=util.nvl((String)szLst.get(p));
    %>
    <tr>
    <td><%=szVal%></td>
    <%for(int q=0;q<shapeLstsz;q++){
    String shVal=util.nvl((String)shapeLst.get(q));
    key=memoVal+"@"+shVal+"@"+szVal;
    vlu=util.nvl((String)dataDtl.get(key+"@VLU"));
    dis=util.nvl((String)dataDtl.get(key+"@DIS"));
    String szper="";
    if(!vlu.equals(""))
    szper=String.valueOf(util.roundToDecimals(((Double.parseDouble(vlu)/grandmemovlu)*100),2))+"%";
    %>
    <td align="right"><%=vlu%></td><td align="right"><%=szper%></td>
    <%}
    key=memoVal+"_"+szVal;
    vlu=util.nvl((String)dataDtl.get(key+"@VLU"));
    dis=util.nvl((String)dataDtl.get(key+"@DIS"));
    String shszper="";
    if(!vlu.equals(""))
    shszper=String.valueOf(util.roundToDecimals(((Double.parseDouble(vlu)/grandmemovlu)*100),2))+"%";
    %>
    <td align="right"><%=vlu%></td><td align="right"><%=shszper%></td>
    </tr>
    <%}%>
    <%}%>
    <tr>
    <th>Grand Total</th>
    <%for(int l=0;l<shapeLstsz;l++){
    String shVal=util.nvl((String)shapeLst.get(l));
    vlu=util.nvl((String)dataDtl.get(shVal+"@VLU"));
    shper="";
    if(!vlu.equals(""))
    shper=String.valueOf(util.roundToDecimals(((Double.parseDouble(vlu)/grantvludo)*100),2))+"%";
    %>
    <td align="right"><%=vlu%></td>
    <td align="right"><%=shper%></td>
    <%}%>
    <td align="right"><%=util.nvl((String)dataDtl.get("TTLVLU"))%></td>
    <td align="right">100%</td>
    </tr>
    </table>
    </td>
    
    </tr>
<%}else{%>
<tr><td valign="top" class="hedPg">
Sorry No Result Found</td></tr>
<%}%>

  </table>
    </body>
</html>