<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page errorPage="../error_page.jsp" %>
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import="java.util.ArrayList,java.util.ArrayList,java.util.HashMap,java.sql.ResultSet,ft.com.*, java.util.Date, java.net.URLEncoder"%>

<jsp:useBean id="util" class="ft.com.JspUtil" scope="page" />
<jsp:useBean id="info" class="ft.com.InfoMgr" scope="session" />
<jsp:useBean id="gtMgr" class="ft.com.GtMgr" scope="session" />
<%@taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
<link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/bse.css?v=<%=info.getJsVersion()%>"/>
<link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/general.css?v=<%=info.getJsVersion()%>"/>
      <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/style.css?v=<%=info.getJsVersion()%>"/>
     <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/Validation.js?v=<%=info.getJsVersion()%> " > </script>

    <title>Assort Pending</title>
    
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
  <img src="../images/line.jpg" border="0" /></td><td valign="middle"> <span class="pgHed">Assort Pending</span> </td>
</tr></table>
  </td>
  </tr>
   <%
   String msg = (String)request.getAttribute("msg");
     if(msg!=null){%>
    <tr><td valign="top" class="tdLayout"><span class="redLabel"><%=msg%></span></td></tr>
   <%}%>
<tr>
  <td valign="top" class="tdLayout">
  <% HashMap stockDtl = (HashMap)gtMgr.getValue("stockDtl");
   if(stockDtl!=null && stockDtl.size()>0){
  %>
  <html:form action="mixAkt/assortPending.do?method=assort" method="post" onsubmit="return sumbitFormConfirm('CHK_','0','Do you want to save changes','Please select Box type for assort','checkbox')">
<table cellpadding="2" cellspacing="2">
<tr><td>Employee Name:&nbsp;&nbsp;
 <html:select property="value(empIdn)"  styleId="empIdn" name="verificationForm" >
           <html:option value="0" >--select--</html:option>
            <html:optionsCollection property="empList" name="verificationForm" 
            label="emp_nme" value="emp_idn" />
            </html:select>
            &nbsp;&nbsp;

<html:submit property="value(assort)" value="Assort" onclick="return checkEmpSelection()" styleClass="submit"/></td></tr>
 <%
 String iskg =(String)request.getAttribute("KG");
 HashMap prp = info.getPrp();
 ArrayList boxValLst = (ArrayList)prp.get("BOX_TYPV");

 ArrayList stockSttLst = new ArrayList();
 stockSttLst.add("OPN");
 stockSttLst.add("CUR");
 stockSttLst.add("TTL");
  HashMap stockSttDsp = new HashMap();
 stockSttDsp.put("OPN","Opening");
 stockSttDsp.put("CUR","Current");
 stockSttDsp.put("TTL","Total");
%>
 <tr><td>
 <%if(iskg!=null){%>
 <table class="grid1"><tr><th><th>Box Type</th><th>QTY</th><th>CTS</th><th>AVG</th></tr>
 <% for(int j=0;j<boxValLst.size();j++){
 String boxTyp = (String)boxValLst.get(j);
 String ttlCts = (String)stockDtl.get(boxTyp+"_CTS");
  String ttlQty = (String)stockDtl.get(boxTyp+"_CNT");
   String ttlAvg = (String)stockDtl.get(boxTyp+"_AVG");
  String fldId = "CHK_"+boxTyp;
   if(ttlCts!=null && ttlCts.length()>0){
  %>
<tr><td><input type="checkbox" name="<%=fldId%>" id="<%=fldId%>" value="<%=boxTyp%>" />  </td>
 <td><%=boxTyp%></td>
 <td><%=ttlQty%></td><td><%=ttlCts%></td>
 <td><%=ttlAvg%></td>
 </tr>
 <%}}%></table>
 <%}else{%>
 <table class="grid1"><tr><th><th>Box Type\Status</th>
 <%for(int j=0;j<stockSttLst.size();j++){
 String stt = (String)stockSttLst.get(j);
 String dspStt = (String)stockSttDsp.get(stt);
  %>
 <th><%=dspStt%></th>
 <%}%>
</tr>


<% for(int j=0;j<boxValLst.size();j++){
 String boxTyp = (String)boxValLst.get(j);
 String ttlCts = (String)stockDtl.get("TTL_"+boxTyp+"_CTS");
  String fldId = "CHK_"+boxTyp;

 if(ttlCts!=null && ttlCts.length()>0){
 %>
 <tr><td><input type="checkbox" name="<%=fldId%>" id="<%=fldId%>" value="<%=boxTyp%>" />  </td>
 <td><%=boxTyp%></td>
  <%for(int k=0;k<stockSttLst.size();k++){
 String stt = (String)stockSttLst.get(k);
 String cts = util.nvl((String)stockDtl.get(stt+"_"+boxTyp+"_CTS"),"0");
 String qty = util.nvl((String)stockDtl.get(stt+"_"+boxTyp+"_CNT"),"0");
  String rte = util.nvl((String)stockDtl.get(stt+"_"+boxTyp+"_RTE"),"0");
 %>
 <td> <%=qty%>&nbsp;|&nbsp; 
 <%if(!cts.equals("0")){%>
 <a href="assortPending.do?method=loadPkt&Box_typ=<%=boxTyp%>&stt=<%=stt%>">
  <%=cts%> </a>
  <%}else{%>
   <%=cts%>
  <%}%>&nbsp;|&nbsp;  <%=rte%>
  </td>
<%}%>
 </tr>
 <%}}%>
</table>
<%}%></td></tr>
</table>
 </html:form>
  <%}else{%>
  Sorry no result found.
  <%}%>
 </td></tr>
 
 </table>
   
    
    </body>
</html>