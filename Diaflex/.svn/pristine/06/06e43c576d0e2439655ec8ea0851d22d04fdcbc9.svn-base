<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page errorPage="../error_page.jsp" %>
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import="java.util.ArrayList,java.util.HashMap,java.sql.ResultSet,ft.com.*, java.util.Date, java.util.ArrayList, ft.com.dao.*"%>

<jsp:useBean id="util" class="ft.com.JspUtil" scope="page" />
<jsp:useBean id="info" class="ft.com.InfoMgr" scope="session" />

<%@taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
  <%
  String seqNo = (String)request.getAttribute("seq");
  String msg = (String)request.getAttribute("msg");
  String num = util.nvl((String)request.getAttribute("out"));
  String count =  util.nvl((String)request.getAttribute("count"),"0");
  String repClick = "prcReportPrint("+seqNo+" , 'REP')";
   String pntClick = "prcReportPrint("+seqNo+" , 'PNT')";
    String purClick = "prcReportPrint("+seqNo+" , 'PUR')";
   String rePrice = "goToRePrice("+seqNo+")";
   HashMap dbinfo = info.getDmbsInfoLst();
   String webUrl =(String)dbinfo.get("REP_URL");
   String cnt =(String)dbinfo.get("CNT");
   String reqUrl =(String)dbinfo.get("HOME_DIR");
    %>

<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <meta http-equiv="refresh" content="30;url=<%=info.getReqUrl()%>/pricing/transferMkt.do?method=status&seq=<%=seqNo%>">
    <title>Repriceing Status</title>
    <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/bse.css?v=<%=info.getJsVersion()%>"/>
      <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/style.css?v=<%=info.getJsVersion()%>"/>
       <script src="<%=info.getReqUrl()%>/scripts/bse.js?v=<%=info.getJsVersion()%>" type="text/javascript"></script>
  
  </head>
  <body>
 <input type="hidden" name="webUrl" id="webUrl" value="<%=dbinfo.get("REP_URL")%>"/>
<input type="hidden" name="cnt" id="cnt" value="<%=cnt%>"/>
<input type="hidden" name="repUrl" id="repUrl" value="<%=dbinfo.get("HOME_DIR")%>"/>

<input type="hidden" name="repPath" id="repPath" value="<%=dbinfo.get("REP_PATH")%>"/>

  <table width="100%"  border="0" align="center" cellpadding="0" cellspacing="0" >
  <tr><td height="20px"> &nbsp;</td></tr>
  <tr><td align="center">
  <table cellspacing="2" cellpadding="2">
  <tr><td>Page Refreshed : </td><td><%=util.getDtTm()%></td> <td> <A href="<%=info.getReqUrl()%>/pricing/transferMkt.do?method=status&seq=<%=seqNo%>"><img src="../images/refresh.gif" border="0"/></a> </td></tr>
  <tr><td colspan="3">
  Current Repricing Sequence Number : <%=seqNo%></td></tr>
  <tr><td colspan="3"><%=msg%></td></tr>
  <tr><td colspan="3">No. Of Stone pending in repricing : <%=count%> </td></tr>
  </table>
  
  </td></tr>
  <%if(num.equals("0")){%>
    <tr><!--<td align="center" colspan="3">
    <table><tr>
    <td><html:button property="value(pricingReport)" onclick="<%=repClick%>" value="New Goods Report" /></td><td><html:button property="value(pktPrint)" value="Paket Print" onclick="<%=pntClick%>"/></td>
    <td><html:button property="value(latestPrcUpd)" value="Re Pricing" onclick="<%=rePrice%>"  ></html:button></td>
     <td><html:button property="value(latestPrcUpd)" value="Purchase report" onclick="<%=purClick%>"  ></html:button></td>
    </tr> </table>
    </td>--></tr>
 <%}%>
  </table>
  
  </body>
</html>