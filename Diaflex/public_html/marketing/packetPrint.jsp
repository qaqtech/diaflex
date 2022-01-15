<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
 
<%@ page errorPage="../error_page.jsp" %>
<%@ page contentType="text/html;charset=windows-1252"%>
<jsp:useBean id="util" class="ft.com.JspUtil" scope="page" />
<jsp:useBean id="info" class="ft.com.InfoMgr" scope="session" />
<%@ page import="java.util.ArrayList,java.util.HashMap,java.sql.ResultSet,ft.com.*, java.util.Date, java.util.ArrayList, ft.com.dao.*"%>
<%@taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>Packet Print</title>
    <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/bse.css?v=<%=info.getJsVersion()%>"/>
    <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/general.css?v=<%=info.getJsVersion()%>"/>
     <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/style.css?v=<%=info.getJsVersion()%>"/>
      <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/bse.js?v=<%=info.getJsVersion()%> " > </script>
           <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/assortScript.js?v=<%=info.getJsVersion()%> " > </script>
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
  <img src="../images/line.jpg" border="0" /></td><td valign="middle"> <span class="pgHed">Packet Print</span> </td>
</tr></table>
  </td>
  </tr>
  <%
int accessidn=Integer.parseInt(util.nvl((String)request.getAttribute("accessidn"),"0"));
   String vnmNtFnd = (String)request.getAttribute("vnmNotFnd");
     if(vnmNtFnd!=null){%>
    <tr><td valign="top" class="tdLayout"><span class="redLabel"><%=vnmNtFnd%></span></td></tr>
   <%}%>
   <tr>
   
   <td valign="top" class="tdLayout">
   <table><tr><td>
   <html:form action="marketing/packetPrint.do?method=view" method="post" >
  <table  class="grid1">
  <tr><th> </th><th>Generic Search
  <%
  ArrayList rolenmLst=(ArrayList)info.getRoleLst();
  String usrFlg=util.nvl((String)info.getUsrFlg());
  if(usrFlg.equals("SYS") || rolenmLst.contains("ADMIN")){
  %>
  <img src="../images/edit.jpg" title="Edit Properties Module" onclick="newWindow('<%=info.getReqUrl()%>/dynamicProperty.do?method=load&mdl=PKTPRT_SRCH&sname=PKTPRTSrch&par=GENERIC')" border="0" width="15px" height="15px"/> 
  <%}%>
  </th></tr>
  <tr><td valign="top">
   <table>
   <tr>
   <td>Packet Id:</td><td> <html:textarea property="value(vnmLst)" rows="13"  cols="30" name="packetPrintForm"  /></td>
   </tr>
  </table></td>
   <td>
   <table>
   <tr><td><span class="redLabel">*</span> Status:</td><td>
    <html:select property="value(stt)" name="packetPrintForm"   >
           <html:option value="0" >--select--</html:option>
            <html:optionsCollection property="sttList" name="packetPrintForm" 
            label="FORM_TTL" value="FORM_NME" />
    </html:select>
   </td></tr>
   <tr><td colspan="2">
    <jsp:include page="/genericSrch.jsp"/>
   </td></tr></table></td></tr>
   <tr><td colspan="2" align="center"><html:submit property="value(srch)" value="Fetch" styleClass="submit" /></td> </tr>
   </table>
   </html:form>
   </td></tr>
   </table></td></tr>
   
   <% 
    HashMap pktList = (HashMap)request.getAttribute("pktList");
    ArrayList pktStkIdnList = (ArrayList)request.getAttribute("pktStkIdnList");
    String view = (String)request.getAttribute("view");
    if(view!=null){
    if(pktList!=null && pktList.size()>0){
    int sr=0;
    ArrayList prpDspBlocked = info.getPageblockList();
     ArrayList vwPrpLst = (ArrayList)session.getAttribute("memoPrpList");
        HashMap totals = (HashMap)request.getAttribute("totalMap");
        String stt = (String)request.getAttribute("stt");   
        HashMap mprp = info.getMprp();
    HashMap allPageDtl= (info.getPageDetails() == null)?new HashMap():(HashMap)info.getPageDetails();
    HashMap pageDtl=(HashMap)allPageDtl.get("PACKET_PRINT");
    ArrayList pageList=new ArrayList();
    HashMap pageDtlMap=new HashMap();
    String fld_nme="",fld_ttl="",val_cond="",dflt_val="",fld_typ="",form_nme="";
    pageList=(ArrayList)pageDtl.get("SUBMIT");
    %>
   <html:form action="marketing/packetPrint.do?method=pktPrint" method="post" target="_blank" onsubmit="return validate_issue('CHK_' , 'count')" >
  <html:hidden property="value(stt)" value="<%=stt%>" name="packetPrintForm" />
  <html:hidden property="value(accessidn)" value="<%=String.valueOf(accessidn)%>" name="packetPrintForm" />
  <tr><td valign="top" class="tdLayout">
   <table>
   <tr><td>Total :&nbsp;&nbsp;</td><td><span id="ttlqty"> <%=totals.get("qty")%>&nbsp;&nbsp;</span></td><td>cts&nbsp;&nbsp;</td><td><span id="ttlcts"><%=totals.get("cts")%>&nbsp;&nbsp;</span></td><td>Selected:&nbsp;&nbsp;</td><td> Total :&nbsp;&nbsp;</td> <td><span id="qtyTtl">0&nbsp;&nbsp;</span></td><td>Cts&nbsp;&nbsp;</td><td><span id="ctsTtl">0</span> </td> </tr>
  
   </table>
   </td></tr>
   <!--<tr>
   <td valign="top" class="tdLayout">
 <html:submit property="value(save)" styleClass="submit" value="Packet Print" /> &nbsp;&nbsp;
  <html:submit property="value(assortSave)" styleClass="submit" value="Assort Packet Print" /> &nbsp;&nbsp;
  <html:submit property="value(labSave)" styleClass="submit" value="Lab Packet Print" />
   <html:submit property="value(labPktPrint)" styleClass="submit" value="Lab Packet Print" />
    <html:submit property="value(barPktPrint)" styleClass="submit" value="Barcode Packet Print" />
   </td></tr>-->
    <tr><td valign="top" class="tdLayout">
    <html:select property="value(repNme)" styleId="repNme" name="packetPrintForm" >
             <%pageList=(ArrayList)pageDtl.get("SELECT");
               if(pageList!=null && pageList.size() >0){
               for(int i=0;i<pageList.size();i++){
                 pageDtlMap=(HashMap)pageList.get(i);
                 fld_nme="value("+(String)pageDtlMap.get("fld_nme")+")";
                 fld_ttl=(String)pageDtlMap.get("fld_ttl");
                 val_cond=(String)pageDtlMap.get("val_cond");
                 dflt_val=(String)pageDtlMap.get("dflt_val");
                 %>
                <html:option value="<%=dflt_val%>"><%=fld_ttl%></html:option>
             <%}}%>
    </html:select>
  <%pageList=(ArrayList)pageDtl.get("SUBMIT");
    if(pageList!=null && pageList.size() >0){
     if(pageList!=null && pageList.size() >0){
       for(int j=0;j<pageList.size();j++){
         pageDtlMap=(HashMap)pageList.get(j);
         fld_nme="value("+(String)pageDtlMap.get("fld_nme")+")";
         fld_ttl=(String)pageDtlMap.get("fld_ttl");
         val_cond=(String)pageDtlMap.get("val_cond"); %>
        <html:submit property="<%=fld_nme%>" value="<%=fld_ttl%>" styleClass="submit" />
 <%
}}}
%>
  <!--<html:submit property="view" value="View" styleClass="submit"/>
   <html:submit property="viewAll" value="View All" styleClass="submit"/>-->
   </td>
  </tr>
  <tr><td valign="top" class="tdLayout">
  
  <table cellpadding="5" cellspacing="5">
    
    <tr><td valign="top">
   
    <table class="grid1" align="left" id="dataTable">
<thead>
<tr>
<th><label title="SR NO">Sr No.</label></th>
<th><label title="CheckAll"><input type="checkbox" name="checkAll" id="checkAll" onclick="ALLCheckBox('checkAll','CHK_')" /> </label></th>
<th><label title="Packet No">Packet No</label></th>

<%for(int j=0; j < vwPrpLst.size(); j++ ){
    String prp = (String)vwPrpLst.get(j);
    if(prpDspBlocked.contains(prp)){
       }else{
     String hdr = (String)mprp.get(prp);
    String prpDsc = (String)mprp.get(prp+"D");
    if(hdr == null) {
        hdr = prp;
       }  
 
%>
<th title="<%=prpDsc%>"><%=hdr%></th>

<%}}%>


      
 
</tr>
</thead>
<tbody>
<%


int colSpan = vwPrpLst.size()+8;
for(int m=0;m< pktStkIdnList.size();m++){ 
String stkIdn = (String)pktStkIdnList.get(m);
HashMap pktData = (HashMap)pktList.get(stkIdn);
String onchnge="ChangeFlg("+stkIdn+" , this , 'NO' )";
String checkVal = "value("+stkIdn+")";
String cts = util.nvl((String)pktData.get("cts"));
sr = m+1;
String checkId = "CHK_"+sr;
%>
<tr>

<td><%=sr%><input type="hidden" id="CTS_<%=stkIdn%>"  value="<%=cts%>" /></td>
<td><html:checkbox property="<%=checkVal%>" styleId="<%=checkId%>" value="yes" onclick="<%=onchnge%>" name="packetPrintForm"/> </td>

<td><%=pktData.get("vnm")%></td>



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
<td title="<%=prpDsc%>"><%=prpValue%></td>
<%}}%>       
 
</tr>

<%}%>        


</tbody>
</table>
  <input type="hidden" name="count" id="count" value="<%=sr%>" />
</td></tr></table>
   
   </td></tr>
   </html:form>
   <%}}%>
   <tr>
    <td><jsp:include page="/footer.jsp" /> </td>
    </tr>
   </table>
  
  
  </body>
</html>