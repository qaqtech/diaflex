<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page errorPage="../error_page.jsp" %>
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import="java.util.ArrayList,java.util.ArrayList,java.util.HashMap, java.sql.*, java.util.Enumeration, java.util.logging.Level"%>

<jsp:useBean id="info" class="ft.com.InfoMgr" scope="session" />
<jsp:useBean id="util" class="ft.com.JspUtil" scope="page" />
<%@taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<html>
  <head>
 <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
  
  <title>Approval For Price Change</title>
  <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/bse.js?v=<%=info.getJsVersion()%> " > </script>
<link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/bse.css?v=<%=info.getJsVersion()%>"/>
<script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/ajax.js?v=<%=info.getJsVersion()%> " > </script>
<script src="<%=info.getReqUrl()%>/scripts/Validation.js" type="text/javascript"></script>
  <script src="<%=info.getReqUrl()%>/scripts/assortScript.js?v=<%=info.getJsVersion()%>" type="text/javascript"></script>
   <script src="<%=info.getReqUrl()%>/scripts/memoScript.js?v=<%=info.getJsVersion()%>" type="text/javascript"></script>
<script language="javaScript" type="text/javascript" src="<%=info.getReqUrl()%>/calendar/calendar.js"></script>
   <link href="<%=info.getReqUrl()%>/calendar/calendar.css" rel="stylesheet" type="text/css" />

      <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/style.css?v=<%=info.getJsVersion()%>"/>
  </head>
    <body onload="MM_preloadImages('images/faunalogo.png')" topmargin="0" leftmargin="0" rightmargin="0" bottommargin="0">
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
  <img src="../images/line.jpg" border="0" /></td><td valign="middle"> <span class="pgHed">
  Approval For Price Change
 </span> </td>
</tr></table>
  </td>
  </tr>
  <%
  //    util.updAccessLog(request,response,"Approval For Price Change", "Approval For Price Change");
  String msg = util.nvl((String)request.getAttribute("msg"));
  if(!msg.equals("")){
  %>
  <tr><td valign="top" class="hedPg"><span class="redLabel"><%=msg%></span></td></tr>
  <%}%>
  <tr><td valign="top" class="hedPg">
  <html:form action="pricing/priceChanges.do?method=fetchPC" method="post" >
  <table>
  <tr><td>Date :</td>  <td bgcolor="#FFFFFF" align="center">
        <html:text property="value(FrmDte)" styleClass="txtStyle"   name="priceChangesForm" styleId="FrmDte"  maxlength="25" /> <a href="#"  onClick="setYears(1900, '<%=info.getCurrentYear()%>' );showCalender(this, 'FrmDte');">
                <img src="<%=info.getReqUrl()%>/images/calender.png"></a></td>
        <td bgcolor="#FFFFFF" align="center"><html:text property="value(toDte)"   name="priceChangesForm" styleId="toDte" styleClass="txtStyle"     maxlength="25"  /> <a href="#"  onClick="setYears(1900, '<%=info.getCurrentYear()%>' );showCalender(this, 'toDte');">
                <img src="<%=info.getReqUrl()%>/images/calender.png"></a></td>
       </tr> 
    <tr><td>Diff % :</td> <td><html:text property="value(frmDiff)" styleId="frmDiff"  styleClass="txtStyle"  name="priceChangesForm"/> </td>
    <td><html:text property="value(toDiff)"   styleClass="txtStyle" styleId="toDiff"  name="priceChangesForm"/></td> </tr>
    <tr><td>Sequence No :</td><td colspan="2">
    <html:text property="value(seq)"  styleId="seq" name="priceChangesForm"/>
    </td></tr>
    <tr><td>Packet Code :</td><td colspan="2">
    <html:textarea property="value(vnm)" styleId="vnm" cols="28" rows="8" name="priceChangesForm"/>
    </td></tr>
    <tr> <td></td><td colspan="2"><html:submit property="value(fetch)" styleClass="submit" onclick="return approvalVal()" value="View"/>
    &nbsp; &nbsp;<html:submit property="value(acptAll)" styleClass="submit" onclick="return confirmChangesMsg('Are You Sure You Want To Accept All ?')" value="Accept All"/>
        &nbsp; &nbsp;<html:submit property="value(rjctAll)" styleClass="submit" onclick="return confirmChangesMsg('Are You Sure You Want To Reject All ?')" value="Reject All"/>
    </td></tr>
  </table>
  </html:form>
  
  </td></tr>
  
  <html:form action="pricing/priceChanges.do?method=savePC" method="post" >
  
  <%
  String view = (String)request.getAttribute("view");
  if(view!=null){
  HashMap mprp = info.getMprp();
 ArrayList vwPrpLst = (ArrayList)session.getAttribute("prpPrpMdl");
 int vwSize = vwPrpLst.size()+6;
  HashMap pktPrpLst= (HashMap)request.getAttribute("pktPrpLst");
  ArrayList pktStkIdnList = (ArrayList)request.getAttribute("pktStkIdnList");
  if(pktPrpLst!=null && pktPrpLst.size()>0){
  %>
 <tr><td valign="top" class="hedPg">
 <html:submit property="value(submit)" styleClass="submit"  value="Save Changes" />
   &nbsp; &nbsp;<html:submit property="value(acptAll)" styleClass="submit" onclick="return confirmChangesMsg('Are You Sure You Want To Accept All ?')" value="Accept All Result"/>
        &nbsp; &nbsp;<html:submit property="value(rjctAll)" styleClass="submit" onclick="return confirmChangesMsg('Are You Sure You Want To Reject All ?')" value="Reject All Result"/>

 </td></tr>
 <tr><td valign="top" class="hedPg">
  <table class="grid1" align="left" >
<thead>
<tr>
<th><label title="SR NO">Sr No.</label></th>
<th><label title="Accept">Accept </label><input type="radio" id="AC" name="sttth" onclick="ALLRedio('AC','AC_')"/></th>
<th><label title="Reject">Reject</label><input type="radio" id="RJ" name="sttth" onclick="ALLRedio('RJ','RJ_')"/></th>
<th><label title="None">None </label><input type="radio" id="NN" name="sttth" onclick="ALLRedio('NN','NN_')"/></th>
<th>Compare</th>
<th>Price Dtl</th>
<th>Packet Code</th>
<th>Old Rte</th>
<th>New Rte</th>
<%
for(int j=0; j < vwPrpLst.size(); j++ ){
    String prp = (String)vwPrpLst.get(j);
     String hdr = (String)mprp.get(prp);
    String prpDsc = (String)mprp.get(prp+"D");
 %>   
 <th title="<%=prpDsc%>"><%=hdr%></th>
    <%}%>
    </tr></thead>
    <tbody>
<%for(int m=0;m< pktStkIdnList.size();m++){ 
String stkIdn = (String)pktStkIdnList.get(m);
HashMap pktDtl = (HashMap)pktPrpLst.get(stkIdn);
String vnm = (String)pktDtl.get("vnm");
String quot = (String)pktDtl.get("quot");
String cmp = (String)pktDtl.get("cmp");
String rdFldVal = "value(PC_"+stkIdn+")";
String target = "CV_"+stkIdn;
String acFldId = "AC_"+stkIdn;
String rjFldId = "RJ_"+stkIdn;
String nnFldId = "NN_"+stkIdn;
String pdFldId = "DV_"+stkIdn;
String url =info.getReqUrl()+"/pricing/priceChanges.do?method=priceDtl&mstkIdn="+stkIdn+"&mdl=AS_PRC_EDIT";
%><tr><td><%=m+1%></td>
<td><html:radio property="<%=rdFldVal%>" name="priceChangesForm" styleId="<%=acFldId%>" value="A" /></td>
<td><html:radio property="<%=rdFldVal%>" name="priceChangesForm" styleId="<%=rjFldId%>" value="R" /></td>
<td><html:radio property="<%=rdFldVal%>" name="priceChangesForm" styleId="<%=nnFldId%>"  value="NN" /></td>
<td><span  class="img"><a href="<%=url%>" onclick="setBGColor('<%=stkIdn%>','CV_')" target="<%=target%>" title="click here for Compare"><img src="../images/plus.png"  border="0"/></a></span></td>
<td><span  class="img"><a href="#"  title="click here for Price Detail"><img src="../images/plus.png" onclick="PktPriceDtl('<%=stkIdn%>')" border="0"/></a></span></td>

<td><%=vnm%></td>
<td><%=cmp%></td>
<td><%=quot%></td>
<%for(int j=0; j < vwPrpLst.size(); j++ ){
    String prp = (String)vwPrpLst.get(j);%>
    <td><%=pktDtl.get(prp)%> </td>
    <%}%>
    </tr>
    <tr id="<%=stkIdn%>" style="display:none"><td colspan="<%=vwSize%>">
<div id="<%=pdFldId%>"></div>
</td></tr>
    <tr><td colspan="<%=vwSize%>" style="display:none;" id="CV_<%=stkIdn%>">
<iframe name="<%=target%>" height="320" width="800" frameborder="0">

</iframe>
    </td> </tr>
    
    <%}%>
    </tbody></table></td></tr>
  <%}else{%>
  <tr><td valign="top" class="hedPg">
  Sorry no result found</td></tr>
 <% }}%>
 </html:form>
  
    <tr>
    <td><jsp:include page="/footer.jsp" /> </td>
    </tr>
  </table></body>
  
  
  
  </body>
   <%@include file="../calendar.jsp"%>
</html>