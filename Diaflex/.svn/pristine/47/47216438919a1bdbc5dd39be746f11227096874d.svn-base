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
    <title>Stock Tally</title>
    <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/bse.css?v=<%=info.getJsVersion()%>"/>
      <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/style.css?v=<%=info.getJsVersion()%>"/>
      <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/memoScript.js?v=<%=info.getJsVersion()%> " > </script>
        <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/bse.js?v=<%=info.getJsVersion()%> " > </script>
        </head>
        <%String logId=String.valueOf(info.getLogId());
        String onfocus="cook('"+logId+"')";
        %>
 <body onfocus="<%=onfocus%>" onload="MM_preloadImages('images/faunalogo.png')" topmargin="0" leftmargin="0" rightmargin="0" bottommargin="0">
<%
HashMap pktDtl = (HashMap)request.getAttribute("pktDtl");
ArrayList sttList =(ArrayList)request.getAttribute("sttList");
ArrayList tlyList = new ArrayList();
tlyList.add("IS");
tlyList.add("RT");
%>
<table width="100%"  border="0" align="center" cellpadding="0" cellspacing="0" class="mainbg">
  <tr>
    <td height="103" valign="top">
   <jsp:include page="/header.jsp" />
  
    </td>
  </tr>
 
  <tr>
    <td height="5" valign="top" class="greyline"><img src="../images/grey_linebig.png" width="685" height="5" align="right" /></td>
  </tr>
  <tr>
  <td valign="top" class="hedPg">
  <table cellpadding="1" cellspacing="5"><tr><td valign="middle">
  <img src="../images/line.jpg" border="0" /></td><td valign="middle"> <span class="pgHed">Stock Tally</span> </td>
</tr></table>
  </td>
  </tr>
  <tr><td valign="top" class="tdLayout">
  <html:form action="marketing/stockTally.do?method=loadSt" method="post" onsubmit="return confirmStockTally()" >
  <html:submit property="value(loadST)" styleClass="submit" value="Genrate Stock Tally"/>
  </html:form>
  </td></tr>
  <tr><td valign="top" class="tdLayout" height="20"></td></tr>
   <tr><td valign="top" class="tdLayout" ><b> Stock Tally Summary</b></td></tr>
  <tr><td valign="top" class="tdLayout">
  <table><tr><td valign="top">
  <%if(pktDtl!=null && pktDtl.size() > 0){%>
  <table class="grid1"><tr><th>Status</th><th colspan="2">Total </th><th colspan="2">Issue </th><th colspan="2">Return </th></tr>
  <tr><td></td><td><b>Qty </b></td><td><b>Cts</b></td><td><b>Qty</b></td><td><b>Cts</b></td><td><b>Qty</b></td><td><b>Cts</b></td></tr>
  <%
  for(int i=0;i<sttList.size();i++){
  String mstkStt = (String)sttList.get(i);
  String issQ = util.nvl((String)pktDtl.get(mstkStt+"_IS_Q"));
  String issC = util.nvl((String)pktDtl.get(mstkStt+"_IS_C"));
  String rtQ = util.nvl((String)pktDtl.get(mstkStt+"_RT_Q"));
  String rtC = util.nvl((String)pktDtl.get(mstkStt+"_RT_C"));
  String  ttlQ ="";
  if(issQ.length()>0 && rtQ.length()>0){
  ttlQ = String.valueOf((Integer.parseInt(issQ) +Integer.parseInt(rtQ)));
  }else if(issQ.length()>0){
   ttlQ = String.valueOf(Integer.parseInt(issQ));
  }else if(rtQ.length()>0){
   ttlQ = String.valueOf(Integer.parseInt(rtQ));
  }
  String  ttlC ="";
  if(issC.length()>0 && rtC.length()>0){
  ttlC = String.valueOf(Float.valueOf(issC) + Float.valueOf(rtC));
  }else if(issC.length()>0){
   ttlC = String.valueOf(Float.valueOf(issC));
  }else  if(rtC.length()>0){
    ttlC = String.valueOf(Float.valueOf(rtC));
  }
  %>
   <tr><td><b><A href="<%=info.getReqUrl()%>/marketing/stockTally.do?method=TallyCRT&stt=<%=mstkStt%>" target="pktDtl"> <%=mstkStt%></a> </b> </td>
   <td><%=ttlQ%> </td>
   <td><%=ttlC%></td>
    <td><%=util.nvl((String)pktDtl.get(mstkStt+"_IS_Q"))%></td>
   <td><%=util.nvl((String)pktDtl.get(mstkStt+"_IS_C"))%></td>
   <td><%=util.nvl((String)pktDtl.get(mstkStt+"_RT_Q"))%></td>
   <td><%=util.nvl((String)pktDtl.get(mstkStt+"_RT_C"))%></td>
  
  </tr>
  <%}%>
  </table>
  <%}%></td>
  <td>
   <iframe name="pktDtl"  height="800" width="600" frameborder="0">

   </iframe>
  </td></tr></table>
  </td></tr>
  
  </table>
  
  
  </body>
</html>