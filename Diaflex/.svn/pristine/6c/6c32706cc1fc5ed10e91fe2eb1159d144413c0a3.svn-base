<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page errorPage="../error_page.jsp" %>
<%@ page contentType="text/html;charset=windows-1252"%>
<jsp:useBean id="info" class="ft.com.InfoMgr" scope="session" />
<jsp:useBean id="util" class="ft.com.JspUtil" scope="page" />
<%@ page import="java.util.ArrayList,java.util.HashMap,java.sql.ResultSet,ft.com.*, java.util.Date, java.util.ArrayList, ft.com.dao.*"%>
<%@taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>Merge Single To Mix Memo</title>
    <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/bse.css?v=<%=info.getJsVersion()%>"/>
    <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/general.css?v=<%=info.getJsVersion()%>"/>
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
    <td height="5" valign="top" class="greyline"><img src="../images/grey_linebig.png" width="685" height="5" align="right" /></td>
  </tr>
  <tr>
  <td valign="top" class="hedPg">
  <table cellpadding="1" cellspacing="5"><tr><td valign="middle">
  <img src="../images/line.jpg" border="0" /></td><td valign="middle"> <span class="pgHed">Merge Single To Mix Memo</span> </td>
</tr></table>
  </td>
  </tr><tr>
   <td valign="top" class="tdLayout">
   <%
   String msg = (String)request.getAttribute("msg");
   if(msg!=null){%>
   <span class="redLabel"><%=msg%></span>
   <%}%></td></tr>
   <tr>
   <td valign="top" class="tdLayout">
   </td></tr>
  <tr>
   <td valign="top" class="tdLayout">
  
   <%ArrayList pktList = (ArrayList)request.getAttribute("pktList");
   HashMap TTL = (HashMap)request.getAttribute("TTL");
   if(pktList!=null && pktList.size()>0){%>
    <html:form action="mix/merge96upmix.do?method=merge" method="post" onsubmit="return validate_SelectAll('0','cb_memo_')">
   <table><tr><td><html:submit property="value(submit)" value="Merge Memo" styleClass="submit"/> </td></tr>
   <tr><td>
   <table class="grid1"><tr><th><input type="checkbox" name="checkAll" onclick="CheckBOXALLForm('0',this,'cb_memo_')" /> </th>
   <th>Memo</th><th>Cts</th><th>Avg Prc</th><th>Value</th>
   </tr>
   <%
   for(int i=0 ; i < pktList.size() ;i++){
   HashMap pktMap = (HashMap )pktList.get(i);
   String memo = util.nvl((String)pktMap.get("MEMO"));
   String chTyp = "cb_memo_"+memo;
   %>
   <tr>
   <td><input type="checkbox" name="<%=chTyp%>" id="<%=chTyp%>" value="<%=memo%>"/> </td>
   <td><%=pktMap.get("MEMO")%></td><td align="right"><%=pktMap.get("CTS")%></td><td align="right"><%=pktMap.get("AVGPRC")%></td>
   <td align="right"><%=pktMap.get("VAL")%></td>
   </tr>
   <%}%>
   <tr><td colspan="2" align="center"><b>Total</b></td><td align="right"><b><%=TTL.get("CTS")%></b></td>
     <td align="right"><b><%=TTL.get("AVGPRC")%></b></td><td align="right"><b><%=TTL.get("VAL")%></b></td>
     </tr>

   </table></td></tr></table></html:form>
   <%}else{%>
   Sorry there are no stones for merging......
   <%}%>
  </td></tr>
  <tr>
    <td><jsp:include page="/footer.jsp" /> </td>
    </tr>
  </table>
  </body>
</html>