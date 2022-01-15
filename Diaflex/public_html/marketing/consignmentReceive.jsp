<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
 
<%@ page errorPage="../error_page.jsp" %>
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import="java.util.ArrayList,java.util.HashMap,java.sql.ResultSet,ft.com.*, java.util.Date, java.util.ArrayList, ft.com.dao.*"%>
<jsp:useBean id="util" class="ft.com.JspUtil" scope="page" />
<jsp:useBean id="info" class="ft.com.InfoMgr" scope="session" />
<%@taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>
<%@taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>Consignment Receive</title>
    <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/bse.css?v=<%=info.getJsVersion()%>"/>
    <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/style.css?v=<%=info.getJsVersion()%>"/>
    <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/memoScript.js?v=<%=info.getJsVersion()%> " > </script>
    <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/bse.js?v=<%=info.getJsVersion()%> " > </script>
     <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/ajax.js?v=<%=info.getJsVersion()%> " > </script>
   <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/assortScript.js?v=<%=info.getJsVersion()%> " > </script>
    <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/Validation.js " > </script>
    <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/priceChange.js?v=<%=info.getJsVersion()%> " > </script>
  </head>

  
    
    <%
HashMap dbinfo = info.getDmbsInfoLst();
String cnt = (String)dbinfo.get("CNT");
        String logId=String.valueOf(info.getLogId());
        String onfocus="cook('"+logId+"')";
    info.setBrnchDlvList(new ArrayList());
    info.setBrnchStkIdnLst(new ArrayList());
        %>
 <body onfocus="<%=onfocus%>" >
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
  <img src="../images/line.jpg" border="0" /></td><td valign="middle"> <span class="pgHed">Consignment Receive</span> </td>
</tr></table></td></tr>
 <% if(request.getAttribute("msg")!=null){%>
<tr><td valign="top" class="tdLayout">
<span class="redLabel"><%=request.getAttribute("msg")%></span>
</td></tr>
<%}%>
<% if(request.getAttribute("msgList")!=null){
ArrayList msgList = (ArrayList)request.getAttribute("msgList");
for(int i=0;i<msgList.size();i++){
String msg = (String)msgList.get(i);%>
<tr><td valign="top" class="tdLayout">
<span class="redLabel"><%=msg%></span>
</td></tr>
<%}}%>

  <html:form action="/marketing/consignReceive.do?method=save" method="POST" onsubmit="return sumbitFormConfirm('cb_memo_','0','Do you want to save changes','Please select Memo ','checkbox')">
<%
 ArrayList pktDtlList = (ArrayList)request.getAttribute("pktDtlList");
 if(pktDtlList!=null && pktDtlList.size()>0){
 %>
 <tr><td valign="top" class="tdLayout">
 <html:submit property="value(receive)" value="Receive" styleClass="submit"/>
 </td></tr>
  <tr><td valign="top" class="tdLayout"> &nbsp;&nbsp; </td></tr>
      <tr><td class="hedPg" style="padding-bottom: 5px;">
&nbsp;&nbsp;&nbsp;Packet Details <img src="../images/ico_file_excel.png" onclick="goToCreateConsReceExcel('consignReceive.do?method=pktDtlExcel','','')" border="0"/> 
</td></tr>
 <tr><td valign="top" class="tdLayout">
 <table class="grid1">
 <tr><th><b>Select ALL</b> <input type="checkbox"  value="IS" id="IS" onclick="CheckBOXALLForm('0',this,'cb_memo_')" />&nbsp;</th>
 <th>PktDtl</th><th>MemoID</th><th>DATE</th><th>Byr</th><th>Qty</th><th>Cts</th><th>Vlu</th><th>Avg_dis</th><th>Avg_rte</th>
 </tr>
 <%
 for(int i=0;i<pktDtlList.size();i++){
 HashMap pktDtl = (HashMap)pktDtlList.get(i);
 String memoId = (String)pktDtl.get("IDN");
 %>
 <tr>
 <td><input type="checkbox" name="cb_memo_<%=memoId%>" value="<%=memoId%>" id="cb_memo_<%=memoId%>"  /></td>
 <td><a href="consignReceive.do?method=pktDtlExcel&memoid=<%=memoId%>" target="_blank">PktDtl</a> </td>
 <td><%=pktDtl.get("IDN")%></td><td><%=pktDtl.get("DTE")%></td> <td><%=pktDtl.get("BYR")%></td> <td><%=pktDtl.get("QTY")%></td> <td><%=pktDtl.get("CTS")%></td> 
 <td><%=pktDtl.get("VLU")%></td><td><%=pktDtl.get("AVGDIS")%></td><td><%=pktDtl.get("AVGRTE")%></td>
 </tr>
 <%}%></table></td></tr>
 <%}else{%>
<tr><td valign="top" class="tdLayout"> Sorry no result found </td></tr>
 <%}%>
 </html:form>
<tr>
<td><jsp:include page="/footer.jsp" /> </td>
</tr>

</table>
    
    </body>
</html>