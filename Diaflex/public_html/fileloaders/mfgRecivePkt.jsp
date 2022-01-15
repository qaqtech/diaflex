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
    <title>Manufacturing Recive Packets</title>
  <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/bse.css?v=<%=info.getJsVersion()%>"/>
    <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/style.css?v=<%=info.getJsVersion()%>"/>
    <script type="text/javascript" src="<%=info.getReqUrl()%>/PopScripts/popup.js?v=<%=info.getJsVersion()%> " > </script>
     <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/bse.js?v=<%=info.getJsVersion()%> " > </script>
     <script language="javaScript" type="text/javascript" src="<%=info.getReqUrl()%>/calendar/calendar.js"></script>
   <link href="<%=info.getReqUrl()%>/calendar/calendar.css" rel="stylesheet" type="text/css" />
    <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/general.css?v=<%=info.getJsVersion()%>"/>
    <script src="<%=info.getReqUrl()%>/PopScripts/jquery-1.2.6.min.js" type="text/javascript"></script>
  </head>
<%String sheetno=(String)request.getAttribute("sheetno");
        String logId=String.valueOf(info.getLogId());
        String onfocus="cook('"+logId+"')";
        %>
 <body onfocus="<%=onfocus%>" onload="MM_preloadImages('images/faunalogo.png')" topmargin="0" leftmargin="0" rightmargin="0" bottommargin="0">
<table width="100%"  border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
  <td valign="top" class="hedPg">
  <table cellpadding="1" cellspacing="5"><tr><td valign="middle">
  <img src="../images/line.jpg" border="0" /></td><td valign="middle"> <span class="pgHed">
 Manufacturing Receive Packets for Sheet-<%=sheetno%>
 </span> </td>
</tr></table>
  </td>
  </tr>
  <%
   ArrayList msgLst = (ArrayList)request.getAttribute("msgList");
   if(msgLst!=null){%>
   <tr><td valign="top" class="tdLayout">
   <%for(int i=0;i<msgLst.size();i++){
   ArrayList msg = (ArrayList)msgLst.get(i);
   %>
    <span class="redLabel"><%=msg.get(1)%>,</span>
   <%}%>
   </td></tr>
   <%}%>
  <tr><td class="tdLayout" valign="top">
  <table class="grid1">
  <%ArrayList pktList=(ArrayList)session.getAttribute("pktList");
  ArrayList vwPrpLst = (ArrayList)session.getAttribute("mfgrecViewLst");
  HashMap mprp = info.getMprp();
  int pktListsz=pktList.size();
  int vwPrpLstsz=vwPrpLst.size();
  HashMap pktPrpMap = new HashMap();
  int sr=0;
  if(pktListsz>0){
  int col=5+vwPrpLstsz;
  %>
  <html:form action="fileloaders/mfgrecive.do?method=saveSheetpkt" method="post" enctype="multipart/form-data">
  <html:hidden property="value(sheet)"  />
  <tr><td valign="top" class="hedPg" colspan="<%=col%>"><html:submit property="value(save)" value="Packet Receive" styleClass="submit"/></td></tr>
  <tr>
  <th><input type="checkbox" name="checkAll" id="checkAll" onclick="checkALL('CHK_','count')" /> </th>
  <th>Sr No</th><th>Factory Id</th><th>Packet Id</th><th>Sheet No</th>
  <!--<th>shape</th><th>crtwt</th><th>col</th><th>clr</th><th>cut</th>-->
  <%for(int j=0; j < vwPrpLstsz; j++ ){
    String prp = (String)vwPrpLst.get(j);
     String hdr = util.nvl((String)mprp.get(prp));
    String prpDsc = util.nvl((String)mprp.get(prp+"D"));
    if(hdr == null) {
        hdr = prp;
       }%>
<th title="<%=prpDsc%>"><%=hdr%></th>
<%}%> 
  </tr>
  <%for(int i=0;i<pktListsz;i++){
  pktPrpMap = new HashMap();
  pktPrpMap=(HashMap)pktList.get(i);
   sr = i+1;
 String  pckt_id=util.nvl((String)pktPrpMap.get("pckt_id"));
 String checkFldId = "CHK_"+sr;
 String checkFldVal = "value("+pckt_id+")";
  %>
  <tr>
  <td><html:checkbox property="<%=checkFldVal%>" styleId="<%=checkFldId%>" name="fileUploadForm" value="yes" /> </td>
  <td align="right"><%=sr%></td>
  <td align="right"><%=util.nvl((String)pktPrpMap.get("fctr_id"))%></td>
  <td align="right"><%=pckt_id%></td>
  <td align="right"><%=util.nvl((String)pktPrpMap.get("sht_nmbr"))%></td>
  <!--<td><%=util.nvl((String)pktPrpMap.get("shape"))%></td>
  <td><%=util.nvl((String)pktPrpMap.get("crtwt"))%></td>
  <td><%=util.nvl((String)pktPrpMap.get("col"))%></td>
  <td><%=util.nvl((String)pktPrpMap.get("clr"))%></td>
  <td><%=util.nvl((String)pktPrpMap.get("cut"))%></td>-->
  <%for(int j=0; j < vwPrpLstsz; j++ ){
    String prp = (String)vwPrpLst.get(j);%>
    <td align="right"><%=pktPrpMap.get(prp)%></td>
<%}%>
  </tr>
  <%}%>
  <input type="hidden" name="count" id="count" value="<%=sr%>" />
</html:form>
  <%}%>
  </table>
  </td>
  </tr>
  </table>
  </body></html>