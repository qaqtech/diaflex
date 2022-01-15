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
    <title>Cost Mgmt</title>
    <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/bse.css?v=<%=info.getJsVersion()%>"/>
      <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/style.css?v=<%=info.getJsVersion()%>"/>
      <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/memoScript.js?v=<%=info.getJsVersion()%> " > </script>
        <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/bse.js?v=<%=info.getJsVersion()%> " > </script>
          <script type="text/javascript" src="<%=info.getReqUrl()%>/PopScripts/popup.js?v=<%=info.getJsVersion()%> " > </script>
            <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/general.css?v=<%=info.getJsVersion()%>"/>
                <script src="<%=info.getReqUrl()%>/PopScripts/jquery-1.2.6.min.js" type="text/javascript"></script>
        </head>
        <%String logId=String.valueOf(info.getLogId());
        String onfocus="cook('"+logId+"')";
        %>
 <body onfocus="<%=onfocus%>" onload="MM_preloadImages('images/faunalogo.png')" topmargin="0" leftmargin="0" rightmargin="0" bottommargin="0">
<div id="backgroundPopup"></div>
<div id="popupContactASSFNL">
<table class="popUpTb">
 <tr><td height="5%">&nbsp;&nbsp;&nbsp;<html:button property="value(close)" styleClass="submit"  onclick="MKEmptyPopup('frame');disablePopupASSFNL()" value="Close"  /> </td></tr>
<tr><td valign="top" height="95%">
<iframe  name="frame" class="frameStyle" align="middle" frameborder="0">

</iframe></td></tr>
</table>
</div>
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
  <img src="../images/line.jpg" border="0" /></td><td valign="middle"> <span class="pgHed">Cost Management Report</span> </td>
</tr></table>
  </td>
  </tr>
    <%
   // util.updAccessLog(request,response,"Cost Management Report", "Cost Management Report");
   String vnmNtFnd = (String)request.getAttribute("vnmNotFnd");
     if(vnmNtFnd!=null){%>
    <tr><td valign="top" class="tdLayout"><span class="redLabel"><%=vnmNtFnd%></span></td></tr>
   <%}%>
  <tr ><td valign="top"  class="tdLayout">
   <html:form action="/pri/costmgmt.do?method=fetch" method="post">
   <table class="grid1">
   <tr><th>Generic Search
   <%
  ArrayList rolenmLst=(ArrayList)info.getRoleLst();
  String usrFlg=util.nvl((String)info.getUsrFlg());
  if(usrFlg.equals("SYS") || rolenmLst.contains("ADMIN")){
  %>
  <img src="../images/edit.jpg" title="Edit Properties Module" onclick="newWindow('<%=info.getReqUrl()%>/dynamicProperty.do?method=load&mdl=COST_SRCH&sname=COSTGNCSrch&par=GENERIC')" border="0" width="15px" height="15px"/> 
  <%}%>
   </th><th></th></tr>
  <tr>
  <td valign="top"><jsp:include page="/genericSrch.jsp" /> </td>
  <td colspan="2" valign="top">
 <html:radio property="value(srchRef)"  styleId="vnm" value="vnm" /> Packet Code.
 <html:radio property="value(srchRef)"  styleId="vnm" value="cert_no" /> Cert No.
 <html:radio property="value(srchRef)"  styleId="vnm" value="memo" /> Memo No.
 <table>
 <tr>
<td>Packet Id: </td>
<td><html:textarea property="value(vnm)" name="costMgmtForm" cols="30" rows="10" styleId="pid"/></td>
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
    ArrayList pktStkIdnList = (ArrayList)request.getAttribute("pktStkIdnList");
    String view = (String)request.getAttribute("view");
    if(view!=null){
    if(pktList!=null && pktList.size()>0){
     ArrayList vwPrpLst = (ArrayList)session.getAttribute("costViewLst");
    HashMap mprp = info.getMprp();
    
    %>
    
    <table  class="grid1" align="left" id="dataTable">
<tr><td valign="top" class="tdLayout">
    <%
  ArrayList rolenmLst=(ArrayList)info.getRoleLst();
  String usrFlg=util.nvl((String)info.getUsrFlg());
  if(usrFlg.equals("SYS") || rolenmLst.contains("ADMIN")){
  %>
  <img src="../images/edit.jpg" title="Edit Properties" onclick="newWindow('<%=info.getReqUrl()%>/dynamicProperty.do?method=load&mdl=COST_VW&sname=costViewLst&par=A')" border="0" width="15px" height="15px"/> 
  <%}%>
    </td></tr>
<tr>


<th><label title="Packet No">Packet No</label></th>
<th><label title="Price Detail">Detail</label></th>
<th><label title="CMP">CMP</label></th>
<th><label title="CMP">CMP Dis</label></th>
<th><label title="MNL">MNL</label></th>
<th><label title="Status">Status</label> </th>
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
%>
<tr class="<%=tableTD%>">
<td nowrap="nowrap"><A href="<%=info.getReqUrl()%>/pri/costmgmt.do?method=pktGrpDtl&mstkIdn=<%=stkIdn%>&vnm=<%=vnm%>" id="LNK_<%=vnm%>" onclick="loadASSFNL('<%=target%>','LNK_<%=vnm%>')"  target="frame"><%=pktData.get("vnm")%></a></td>
<td><span  class="img"><img src="../images/plus.png"  id="IMG_<%=stkIdn%>"  onclick="PktPriceDtl('<%=stkIdn%>')" border="0"/></span></td>

<td nowrap="nowrap"><%=pktData.get("quot")%></td>

<td nowrap="nowrap"><%=pktData.get("cmp_dis")%></td>
<td nowrap="nowrap"><%=pktData.get("fquot")%></td>
<td nowrap="nowrap"><%=pktData.get("stt1")%></td>
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
<tr id="<%=stkIdn%>" style="display:none"><td colspan="<%=colSpan%>">
<div id="<%=target%>"></div>
</td></tr>
<%}%>        


</tbody>
</table>
<%} else{%>
   Sorry no result found
   <%}}%>
    </td></tr>
     <tr>
    <td><jsp:include page="/footer.jsp" /> </td>
    </tr>
    </table></body></html>