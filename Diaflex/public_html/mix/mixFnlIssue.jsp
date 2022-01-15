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
    <title>Mixing Issue</title>
    <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/bse.css?v=<%=info.getJsVersion()%>"/>
    <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/general.css?v=<%=info.getJsVersion()%>"/>
     <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/style.css?v=<%=info.getJsVersion()%>"/>
      <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/bse.js?v=<%=info.getJsVersion()%> " > </script>
  </head>
        <%String logId=String.valueOf(info.getLogId());
        String onfocus="cook('"+logId+"')";
        %>
 <body onfocus="<%=onfocus%>" onload="MM_preloadImages('images/faunalogo.png')" topmargin="0" leftmargin="0" rightmargin="0" bottommargin="0">
<%


String method = util.nvl(request.getParameter("method"));
ArrayList stockList = (ArrayList)session.getAttribute("StockList");
boolean disabled= false;
if(method.equals("fecth")){
if(stockList!=null && stockList.size()>0)
disabled= true;
}

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
  <img src="../images/line.jpg" border="0" /></td><td valign="middle"> <span class="pgHed">Mixing Final Issue</span> </td>
  </tr></table>
  </td>
  </tr>
    <%String msg = (String)request.getAttribute("msg");
    if(msg!=null){%>
    <tr><td valign="top" class="tdLayout"><span class="redLabel"><%=msg%></span></td></tr>
    <%}
   String vnmNtFnd = (String)request.getAttribute("vnmNotFnd");
     if(vnmNtFnd!=null){%>
    <tr><td valign="top" class="tdLayout"><span class="redLabel"><%=vnmNtFnd%></span></td></tr>
   <%}%>
   <tr>
   
   <td valign="top" class="tdLayout">
   <table><tr><td>
   <html:form action="mix/mixFnlissue.do?method=fetch" method="post" onsubmit="return repairIssue(' Employee.')">
  <table  class="grid1">
  <tr><th> </th><th>Generic Search </th></tr>
  <tr><td><table>
  <tr><td valign="top">
   Process</td><td>
    <html:select property="value(mprcIdn)"  styleId="mprcIdn"  name="mixFnlIssForm" disabled="<%=disabled%>"   >
         
            <html:optionsCollection property="mprcList"  name="mixFnlIssForm"
            label="prc" value="mprcId" />
    </html:select>
   </td> </tr>
   <tr>
    <td>
    Employee : </td>
   <td>
   <html:select property="value(empIdn)"  styleId="empIdn" name="mixFnlIssForm" disabled="<%=disabled%>"  >
           <html:option value="0" >--select--</html:option>
            <html:optionsCollection property="empList" name="mixFnlIssForm" 
            label="emp_nme" value="emp_idn" />
            </html:select>
   </td>
   </tr>
   <tr>
   <td>Packet No:</td><td> <html:textarea property="value(vnmLst)" rows="7"  cols="30" name="mixFnlIssForm"  /></td>
   </tr>
  </table></td>
   <td>
   
    <jsp:include page="/genericSrch.jsp"/>
   </td></tr>
   <tr><td colspan="2" align="center"><html:submit property="value(srch)" value="Fetch" styleClass="submit" /></td> </tr>
   </table>
   </html:form>
   </td></tr>
   
   <%
   String view = (String)request.getAttribute("view");
   if(view!=null){
   
   if(stockList!=null && stockList.size()>0){
    ArrayList vwPrpLst = (ArrayList)session.getAttribute("MixViewLst");
    HashMap totals = (HashMap)request.getAttribute("totalMap");
    HashMap mprp = info.getMprp();
    int sr = 0;
   %>
   <tr><td>
   <table>
   <tr><td>Total :&nbsp;&nbsp;</td><td><span id="ttlqty"> <%=totals.get("qty")%>&nbsp;&nbsp;</span></td><td>cts&nbsp;&nbsp;</td><td><span id="ttlcts"><%=totals.get("cts")%>&nbsp;&nbsp;</span></td><td>Selected:&nbsp;&nbsp;</td><td> Total :&nbsp;&nbsp;</td> <td><span id="qtyTtl">0&nbsp;&nbsp;</span></td><td>Cts&nbsp;&nbsp;</td><td><span id="ctsTtl">0</span> </td> </tr>
  
   </table>
   </td></tr>
   <html:form action="mix/mixFnlissue.do?method=Issue" method="post" onsubmit="return validate_issue('CHK_' , 'count')">
   <html:hidden property="value(empId)" name="mixFnlIssForm" />
   <html:hidden property="value(prcId)" name="mixFnlIssForm" />
   <tr><td><html:submit property="value(issue)" styleClass="submit" value="Issue" /> </td></tr>
   <tr><td>
   <table class="grid1">
   <tr> <th><input type="checkbox" name="checkAll" id="checkAll" onclick="checkALL('CHK_','count')" /> </th><th>Sr</th>
        <th>Qty</th>
       <th>Cts</th>
     
       <th>Shape</th>
        <th>Mix Size</th>
</tr>
 <%
 
 for(int i=0; i < stockList.size(); i++ ){
 HashMap stockPkt = (HashMap)stockList.get(i);
 
  String sh = (String)stockPkt.get("sh");
  String size = (String)stockPkt.get("size");
 String cts = (String)stockPkt.get("cts");
 String onclick = "AssortTotalCal(this,"+cts+",'','')";
 sr = i+1;
 String checkFldId = "CHK_"+sr;
 String checkFldVal = "value("+sh+"_"+size+")";
 %>
<tr>
<td><html:checkbox property="<%=checkFldVal%>" styleId="<%=checkFldId%>" name="mixFnlIssForm" onclick="<%=onclick%>" value="yes" /> </td>
<td><%=sr%></td>
<td><%=stockPkt.get("qty")%></td>
<td><%=cts%></td><td><%=sh%></td><td><%=size%></td>
</tr>
   <%}%>
   </table></td></tr>
   <input type="hidden" name="count" id="count" value="<%=sr%>" />
   </html:form>
   <%}
   else{%>
   <tr><td>Sorry no result found </td></tr>
   <%}
   }%>
   
   </table>
   
  
  
   <tr>
    <td><jsp:include page="/footer.jsp" /> </td>
    </tr>
  </table>

  </body>
</html>