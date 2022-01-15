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
    <title>Single To Mix</title>
    <script language="javaScript" type="text/javascript" src="<%=info.getReqUrl()%>/scripts/ajax.js?v=<%=info.getJsVersion()%>"></script>
    <script src="<%=info.getReqUrl()%>/scripts/assortScript.js?v=<%=info.getJsVersion()%>" type="text/javascript"></script>
     <script src="<%=info.getReqUrl()%>/scripts/memoScript.js?v=<%=info.getJsVersion()%>" type="text/javascript"></script>
    <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/bse.css?v=<%=info.getJsVersion()%>"/>
    <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/general.css?v=<%=info.getJsVersion()%>"/>
     <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/style.css?v=<%=info.getJsVersion()%>"/>
     <script src="<%=info.getReqUrl()%>/scripts/priceChange.js?v=<%=info.getJsVersion()%>" type="text/javascript"></script>
     <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/bse.js?v=<%=info.getJsVersion()%> " > </script>
      <script type="text/javascript" src="<%=info.getReqUrl()%>/PopScripts/popup.js?v=<%=info.getJsVersion()%> " > </script>
     <script src="<%=info.getReqUrl()%>/PopScripts/jquery-1.2.6.min.js" type="text/javascript"></script>
        </head>
        <%String logId=String.valueOf(info.getLogId());
        String onfocus="cook('"+logId+"')";
        %>
 <body onfocus="<%=onfocus%>" onload="MM_preloadImages('images/faunalogo.png')" topmargin="0" leftmargin="0" rightmargin="0" bottommargin="0">
<input type="hidden" id="reqUrl" name="reqUrl" value="<%=info.getReqUrl()%>"/>

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
  <img src="../images/line.jpg" border="0" /></td><td valign="middle"> <span class="pgHed">Single To Mix Transfer</span> </td>
</tr></table>
  </td>
  </tr>
    <%
     String vnmNtFnd = (String)request.getAttribute("vnmNotFnd");
   String msg = (String)request.getAttribute("msg");
  ArrayList singletomixList =((ArrayList)util.getFromMem(info.getMem_ip(),info.getMem_port(),info.getCnt()+"_singletomixsttList") == null)?new ArrayList():(ArrayList)util.getFromMem(info.getMem_ip(),info.getMem_port(),info.getCnt()+"_singletomixsttList"); 
  int singletomixListsz=singletomixList.size();
     if(vnmNtFnd!=null){%>
    <tr><td valign="top" class="tdLayout"><span class="redLabel"><%=vnmNtFnd%></span></td></tr>
   <%}%>
   <%if(msg!=null){%>
    <tr><td valign="top" class="tdLayout"><span class="redLabel"><%=msg%></span></td></tr>
   <%}%>
  <tr ><td valign="top"  class="tdLayout">
   <html:form action="/mix/singletomixtrf.do?method=fetch" method="post">
   <table class="grid1">
   <tr><th>Generic Search
   <%
  ArrayList rolenmLst=(ArrayList)info.getRoleLst();
  String usrFlg=util.nvl((String)info.getUsrFlg());
  if(usrFlg.equals("SYS") || rolenmLst.contains("ADMIN")){
  %>
  <img src="../images/edit.jpg" title="Edit Properties Module" onclick="newWindow('<%=info.getReqUrl()%>/dynamicProperty.do?method=load&mdl=SINGLETOMIX_SRCH&sname=singleTomixGNCSrch&par=GENERIC')" border="0" width="15px" height="15px"/> 
  <%}%>
   </th><th></th></tr>
<%if(singletomixListsz>0){%>
     <tr><td colspan="2" valign="top">
      &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Status&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
      <html:select property="value(stt)" styleId="status">  
      <%for(int i=0;i<singletomixListsz;i++){
      ArrayList data=(ArrayList)singletomixList.get(i);
      String val=(String)data.get(0);
      String dsc=(String)data.get(1);
      %>
      <html:option value="<%=val%>" ><%=dsc%></html:option> 
      <%}%>
      </html:select>
      </td></tr>
      <%}%>
  <tr>
  <td valign="top"><jsp:include page="/genericSrch.jsp" /> </td>
  <td colspan="2" valign="top">
 <html:radio property="value(srchRef)"  styleId="vnm" value="vnm" /> Packet Code.
 <html:radio property="value(srchRef)"  styleId="vnm" value="cert_no" /> Cert No.
 <table>
 <tr>
<td>Packet Id: </td>
<td><html:textarea property="value(vnm)" name="singleToMixTrfForm" cols="30" rows="10" styleId="pid"/></td>
</tr>
 </table>
 </td>
  </tr>
<tr>
<td colspan="2" align="center">
<html:submit property="mfg" value="Search Packet" styleClass="submit" />
</td>
</tr>
  </table>
   </html:form></td></tr>
    
    <tr><td valign="top" height="15px" class="tdLayout">
   
    </td></tr>
     <tr><td valign="top" class="tdLayout">
    <%
    ArrayList prpDspBlocked = info.getPageblockList();
    HashMap pktList = (HashMap)request.getAttribute("pktList");
    ArrayList pktStkIdnList = (ArrayList)session.getAttribute("pktStkIdnList");
    String view = (String)request.getAttribute("view");
    if(view!=null){
    HashMap totals = (HashMap)request.getAttribute("totalMap");
    if(pktList!=null && pktList.size()>0){
     ArrayList vwPrpLst = (ArrayList)session.getAttribute("singletomixViewLst");
    HashMap mprp = info.getMprp();
    int sr = 0;
    %>
    <table>
    <tr><td>
   <table>
   <tr><td>Total :&nbsp;&nbsp;</td><td><span id="ttlqty"> <%=totals.get("qty")%>&nbsp;&nbsp;</span></td><td>cts&nbsp;&nbsp;</td><td><span id="ttlcts"><%=totals.get("cts")%>&nbsp;&nbsp;</span></td><td>Selected:&nbsp;&nbsp;</td><td> Total :&nbsp;&nbsp;</td> <td><span id="qtyTtl">0&nbsp;&nbsp;</span></td><td>Cts&nbsp;&nbsp;</td><td><span id="ctsTtl">0</span> </td> </tr>
   </table>
   </td></tr>
   <tr><td>
   <html:form action="mix/singletomixtrf.do?method=transfer" onsubmit="return validate_issue('check_' , 'count')" method="post"  >
    <table cellpadding="0" cellspacing="0">
  <tr><td><html:submit property="value(transfer)" styleClass="submit" value="Transfer" /> 
   <%
  ArrayList rolenmLst=(ArrayList)info.getRoleLst();
  String usrFlg=util.nvl((String)info.getUsrFlg());
  if(usrFlg.equals("SYS") || rolenmLst.contains("ADMIN")){
  %>
  <img src="../images/edit.jpg" title="Edit Properties Module" onclick="newWindow('<%=info.getReqUrl()%>/dynamicProperty.do?method=load&mdl=SINGLETOMIX_VW&sname=singletomixViewLst&par=A')" border="0" width="15px" height="15px"/> 
  <%}%></td>
  </tr>
  </table>
    <table  class="grid1" align="left" id="dataTable">
<tr>
<th><label title="SR NO">Sr No.</label></th>
<th><label title="CheckAll"><input  type="checkbox" id="checkAll" name="checkAll" onclick="ALLCheckBoxDisable('checkAll','check_');assortVerificationExlAllDisable();" />Select </label></th>
<th><label title="Packet No">Packet No</label></th>
<%for(int j=0; j < vwPrpLst.size(); j++ ){
    String prp = (String)vwPrpLst.get(j);
     String hdr = (String)mprp.get(prp);
    String prpDsc = (String)mprp.get(prp+"D");
    if(hdr == null) {
        hdr = prp;
       }  
if(prpDspBlocked.contains(prp)){
}else{
%>
<th title="<%=prpDsc%>"><%=hdr%></th>

<%}}%>
</tr>

<tbody>
<%

String tableTD="";
int colSpan = vwPrpLst.size()+4;
for(int m=0;m< pktStkIdnList.size();m++){ 
if(m%2==0){
tableTD="even";
}else{
tableTD="odd";
}
String stkIdn = (String)pktStkIdnList.get(m);
HashMap pktData = (HashMap)pktList.get(stkIdn);
String vnm = (String)pktData.get("vnm");
String target = "DV_"+stkIdn;
String checkVal = "value(check_"+stkIdn+")";
String checkId = "check_"+m;
String cts =util.nvl((String)pktData.get("cts"));
sr = m+1;
String onclick = "AssortTotalCal(this,"+cts+",'','');assortVerificationExl("+stkIdn+", this);setBGColorOnCHK(this,'"+target+"')";
%>
<tr class="<%=tableTD%>">
<td align="center"><%=sr%></td>
<td><html:checkbox property="<%=checkVal%>" styleId="<%=checkId%>" name="singleToMixTrfForm" onclick="<%=onclick%>" value="Y"/> </td>
<td nowrap="nowrap"><%=pktData.get("vnm")%></a></td>
<%
 for(int l=0;l<vwPrpLst.size();l++){
   String prp = (String)vwPrpLst.get(l);
   if(prpDspBlocked.contains(prp)){
}else{
    String prpValue =  (String) pktData.get(prp);
    if(prpValue.equals("NA"))
     prpValue = "";
    String prpDsc = (String)mprp.get(prp+"D");
 
 %>
<td title="<%=prpDsc%>" nowrap="nowrap"><%=prpValue%></td>
<%}}%>       
 
</tr>
<%}%>        


</tbody>
</table>
<input type="hidden" name="count" id="count" value="<%=sr%>" />
</html:form>
</td></tr></table>
<%} else{%>
   Sorry no result found
   <%}}%>
    </td></tr>
     <tr>
    <td><jsp:include page="/footer.jsp" /> </td>
    </tr>
    </table></body></html>