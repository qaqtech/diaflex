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
<title>50 Down Pointer</title>
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
  <span class="pgHed">50 Down Pointer
  </span></td></tr></table>
  </td></tr>
    <%
    HashMap dataDtl= (HashMap)session.getAttribute("dataDtl");
    ArrayList monthLst= (ArrayList)session.getAttribute("monthLst");
     ArrayList szLst= (ArrayList)session.getAttribute("szLst");
    ArrayList statusLst= (ArrayList)session.getAttribute("statusLst");
    ArrayList srchids= (ArrayList)request.getAttribute("srchids");
    ArrayList prpLst=new ArrayList();
    HashMap dbinfo = info.getDmbsInfoLst();
    String cutval = (String)dbinfo.get("CUT");
    String polval = (String)dbinfo.get("POL");
    String symval = (String)dbinfo.get("SYM");
    int monthLstsz=monthLst.size();
    String key="";
    int counter=0;
    prpLst.add(cutval);
    prpLst.add(polval);
    prpLst.add(symval);
    String sz=szLst.toString();
    sz = sz.replace('[','(');
    sz = sz.replace(']',')');
    sz = sz.replace(',','-');
    if(szLst!=null && szLst.size()>0){
    if(srchids!=null && srchids.size()>0){
    String dsc=util.nvl((String)request.getAttribute("srchDscription"));
    %>
    <tr><td valign="top" class="hedPg"><span class="txtLink" >Search Description : <%= dsc%></span>
    <td></tr>
    <%}%>
    <tr>
    <td valign="top" class="hedPg">
    <table class="grid1">
    <%for(int i=0;i<statusLst.size();i++){
    String stt=util.nvl((String)statusLst.get(i));%>
    <tr><td colspan="<%=(monthLstsz+2)%>"><%=stt%>&nbsp;<%=sz%></td></tr>
    <tr>
    <th colspan="2">Total Pcs</th>
    <%for(int j=0;j<monthLstsz;j++){
    String month=util.nvl((String)monthLst.get(j));
    key=stt+"@"+month;  
    %>
    <th><%=util.nvl((String)dataDtl.get(key))%></th>
    <%}%>
    </tr>
    <tr>
    <th colspan="2"></th>
    <%for(int j=0;j<monthLstsz;j++){
    %>
    <th><%=util.nvl((String)monthLst.get(j))%></th>
    <%}%>
    </tr>
    <%for(int k=0;k<prpLst.size();k++){
    String typ=util.nvl((String)prpLst.get(k));
    ArrayList prpValgrpLst=new ArrayList();
    prpValgrpLst=(ArrayList)dataDtl.get(typ);
    boolean display=true;
    for(int l=0;l<prpValgrpLst.size();l++){
    String grp=util.nvl((String)prpValgrpLst.get(l));
    %>
    <tr>
    <%if(display){%>
    <td rowspan="<%=prpValgrpLst.size()%>" valign="top"><%=typ%></td>
    <%display=false;
    }%>
    <td><%=grp%></td>
    <%for(int m=0;m<monthLstsz;m++){
    String month=util.nvl((String)monthLst.get(m));
    key=stt+"@"+typ+"@"+month+"@"+grp;
    String grandkey=stt+"@"+month;  
    String val=util.nvl2((String)dataDtl.get(key),"0");
    String gval=util.nvl2((String)dataDtl.get(grandkey),"0");
    double getval=Double.parseDouble(val);
    double grandval=Double.parseDouble(gval);
    double percent=util.roundToDecimals((getval/grandval)*100,1);
    
    %>
    <td align="right"><%=percent%>%</td>
    <%}%>
    </tr>
    <%}}%>
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