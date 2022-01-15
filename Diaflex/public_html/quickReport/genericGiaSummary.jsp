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
<title>Gia Weekly Summary</title>
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
  <span class="pgHed">Gia Weekly Summary
  </span></td></tr></table>
  </td></tr>
    <%
    HashMap dataDtl= (HashMap)session.getAttribute("dataDtl");
    ArrayList colList= (ArrayList)session.getAttribute("colList");
     ArrayList clrList= (ArrayList)session.getAttribute("clrList");
     ArrayList shList= (ArrayList)session.getAttribute("shList");
    ArrayList szLst= (ArrayList)session.getAttribute("szLst");
    ArrayList statusLst= (ArrayList)session.getAttribute("statusLst");
    String cts="",avg="",age="",key="";
    int counter=0;
    if(dataDtl!=null && dataDtl.size()>0){%>
    <tr>
    <td valign="top" class="hedPg">
        <table class="grid1">
        <%for(int i=0;i<statusLst.size();i++){
            String stt=util.nvl((String)statusLst.get(i));
            counter=0;
            %>
            <tr><td colspan="4"><%=stt%></td></tr>
            <tr>
            <%for(int j=0;j<szLst.size();j++){
            String szval=util.nvl((String)szLst.get(j));
            counter++;
            if(counter==5){
            counter=1;
            %>
            </tr><tr>
            <%}%>
            <td valign="top" align="center">
                <table class="grid1">
                    <tr><th align="center" nowrap="nowrap" colspan="5"><%=szval%></th></tr>
                    <tr><th></th><th></th><th>CRTS</th><th>AVG</th><th>AGE</th></tr>
                    <%for(int k=0;k<clrList.size();k++){
                        String clrval=util.nvl((String)clrList.get(k));
                        boolean display=true;
                        %>
                        <%for(int l=0;l<colList.size();l++){
                            String colval=util.nvl((String)colList.get(l));
                            key=szval+"@"+stt+"@"+clrval+"@"+colval;
                            cts=util.nvl((String)dataDtl.get(key+"@CTS"));
                            avg=util.nvl((String)dataDtl.get(key+"@AVG"));
                            age=util.nvl((String)dataDtl.get(key+"@AGE"));
                            if(!cts.equals("")){%>
                                <tr>
                                <%if(display){
                                display=false;%>
                                <td align="center"><%=clrval%></td>
                                <%}else{%>
                                <td></td>
                                <%}%>
                                <td align="center"><%=colval%></td>
                                <td align="right"><%=cts%></td>
                                <td align="right"><%=avg%></td>
                                <td align="right"><%=age%></td>
                                </tr>
                            <%}
                        }%>
                    <%}%>
                    <%key = szval+"@"+stt;%>
            <tr><td align="center" colspan="2"><b>Total</b></td>
            <td align="right"><b><%=util.nvl((String)dataDtl.get(key+"@CTS"))%></b></td>
            <td align="right"><b><%=util.nvl((String)dataDtl.get(key+"@AVG"))%></b></td>
            <td align="right"><b><%=util.nvl((String)dataDtl.get(key+"@AGE"))%></b></td>
            </tr>
                </table>
            </td>
            
            
            <%}
            if(shList!=null && shList.size()>0){
            counter++;
            if(counter==5){
            counter=1;
            %>
            </tr><tr>
            <%}%>
            <td valign="top" align="center">
            <table class="grid1">
            <tr><th colspan="2"></th><th>CRTS</th><th>AVG</th><th>AGE</th></tr>
            <%for(int s=0;s<shList.size();s++){
            String shapeval=util.nvl((String)shList.get(s));
            key=shapeval+"@"+stt;
            cts=util.nvl((String)dataDtl.get(key+"@CTS"));
            avg=util.nvl((String)dataDtl.get(key+"@AVG"));
            age=util.nvl((String)dataDtl.get(key+"@AGE"));
            if(!cts.equals("")){%>
                <tr>
                <td align="center" colspan="2"><b><%=shapeval%></b></td>
                <td align="right"><b><%=cts%></b></td>
                <td align="right"><b><%=avg%></b></td>
                <td align="right"><b><%=age%></b></td>
                </tr>
            <%}
            }%>
            <tr>
                <td align="center" colspan="2"><b>TOTAL</b></td>
                <td align="right"><b><%=util.nvl((String)dataDtl.get("TOTAL@"+stt+"@CTS"))%></b></td>
                <td align="right"><b><%=util.nvl((String)dataDtl.get("TOTAL@"+stt+"@AVG"))%></b></td>
                <td align="right"><b><%=util.nvl((String)dataDtl.get("TOTAL@"+stt+"@AGE"))%></b></td>
                </tr>
            </table>
            </td>
            <%}%>
        </tr>
        <%}%>   
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