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
    <title>Stock Properties Update</title>
    <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/bse.css?v=<%=info.getJsVersion()%>"/>
      <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/style.css?v=<%=info.getJsVersion()%>"/>
      <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/memoScript.js?v=<%=info.getJsVersion()%> " > </script>
        <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/bse.js?v=<%=info.getJsVersion()%> " > </script>
         <script type="text/javascript" src="<%=info.getReqUrl()%>/PopScripts/popup.js?v=<%=info.getJsVersion()%> " > </script>
     <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/general.css?v=<%=info.getJsVersion()%>"/>
     <script src="<%=info.getReqUrl()%>/scripts/assortScript.js?v=<%=info.getJsVersion()%>" type="text/javascript"></script>
       <script src="<%=info.getReqUrl()%>/PopScripts/jquery-1.2.6.min.js" type="text/javascript"></script>

        </head>
        <%String logId=String.valueOf(info.getLogId());
        String onfocus="cook('"+logId+"')";
        %>
 <body onfocus="<%=onfocus%>" onload="MM_preloadImages('images/faunalogo.png')" topmargin="0" leftmargin="0" rightmargin="0" bottommargin="0">
<input type="hidden" id="reqUrl" name="reqUrl" value="<%=info.getReqUrl()%>"/>
<div id="backgroundPopup"></div>
<div id="popupContactASSFNL">
<table class="popUpTb">
 <tr>
 <td height="5%" id="close">&nbsp;&nbsp;&nbsp;<html:button property="value(close)" styleClass="submit"  onclick="MKEmptyPopup('SC');disablePopupASSFNL();" value="Close"  /> </td>
 <td height="5%" id="closereload" style="display:none">&nbsp;&nbsp;&nbsp;<html:button property="value(close)" styleClass="submit"  onclick="MKEmptyPopup('SC');disablePopupASSFNL();reportUploadclose('STK')" value="Close"  /> </td>
 </tr>
<tr><td valign="top" height="95%">
<iframe  name="SC" class="frameStyle" align="middle" frameborder="0">

</iframe></td></tr>
</table>
</div>
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
  <img src="../images/line.jpg" border="0" /></td><td valign="middle"> <span class="pgHed">Stock Properties Update</span> </td>
</tr></table>
  </td>
  </tr>
  <%
     HashMap allPageDtl= (info.getPageDetails() == null)?new HashMap():(HashMap)info.getPageDetails();
     HashMap pageDtl=(HashMap)allPageDtl.get("BULK_PRP_UPD");
      ArrayList pageList=new ArrayList();
      HashMap pageDtlMap=new HashMap();
      String fld_nme="",fld_ttl="",val_cond="",dflt_val="",fld_typ="",form_nme="",lov_qry="";
   String vnmNtFnd = (String)request.getAttribute("vnmNotFnd");
     if(vnmNtFnd!=null){%>
    <tr><td valign="top" class="tdLayout"><span class="redLabel"><%=vnmNtFnd%></span></td></tr>
   <%}%>
  <tr><td valign="top" class="tdLayout">
   <html:form action="/marketing/StockPrpUpd.do?method=view"  method="POST">
        <table class="grid1">
        <tr><th colspan="2">Memo Search </th></tr>
            <tr>
                <td>Memo Id</td><td><html:text property="value(memoIdn)"/></td>
            </tr>
             <tr>
            <td>Packets. </td><td>
            <html:textarea property="value(vnmLst)" />
            </td>
            </tr>
           
            <tr>
            <td> <html:submit property="submit" value="View " styleClass="submit"/></td>
            </tr>
        </table>
       
    </html:form></td></tr>
    
    <tr><td valign="top" class="tdLayout">
    
    </td></tr>
    <tr><td valign="top" class="tdLayout">
    <%
    ArrayList prpDspBlocked = info.getPageblockList();
    HashMap pktList = (HashMap)request.getAttribute("pktList");
    ArrayList pktStkIdnList = (ArrayList)request.getAttribute("pktStkIdnList");
    if(pktList!=null && pktList.size()>0){
    ArrayList vwPrpLst = (ArrayList)info.getValue("prpUpdViewLst");
    HashMap mprp = info.getMprp();
    
    %>
    <table cellpadding="5" cellspacing="5">
    <%pageList=(ArrayList)pageDtl.get("BUTTON");
      if(pageList!=null && pageList.size() >0){%>
      <tr><td>
      <%
      for(int j=0;j<pageList.size();j++){
      pageDtlMap=(HashMap)pageList.get(j);
      fld_nme="value("+(String)pageDtlMap.get("fld_nme")+")";
      fld_ttl=(String)pageDtlMap.get("fld_ttl");
      val_cond=(String)pageDtlMap.get("val_cond");
      fld_typ=(String)pageDtlMap.get("fld_typ");
     String fld_id = (String)pageDtlMap.get("dflt_val");
      if(fld_typ.equals("S")){ %>
<html:submit property="<%=fld_nme%>" value="<%=fld_ttl%>"  onclick="<%=val_cond%>" styleClass="submit" />
      <%}else if(fld_typ.equals("B")){%>
<html:button property="<%=fld_nme%>" value="<%=fld_ttl%>" styleId="<%=fld_id%>" onclick="<%=val_cond%>" styleClass="submit" />           
<%}else if(fld_typ.equals("HB")){%>
<input type="button" id="<%=fld_id%>" onclick="<%=val_cond%>"  value="<%=fld_ttl%>"  class="submit"/>
<%}}%></td></tr>
<%}%>
    <!--<tr><td><input type="button" id="summary" class="submit" onclick="purlab('summary','SC','STK');"  value="Upload"/></td></tr>-->
    <tr><td valign="top">
    <table class="grid1" align="left" id="dataTable">
<thead>
<tr>
<%pageList=(ArrayList)pageDtl.get("CHKHDR");
      if(pageList!=null && pageList.size() >0){
      for(int j=0;j<pageList.size();j++){
      pageDtlMap=(HashMap)pageList.get(j);
      fld_nme=(String)pageDtlMap.get("fld_nme");
      fld_ttl=(String)pageDtlMap.get("fld_ttl");
      val_cond=(String)pageDtlMap.get("val_cond");
      fld_typ=(String)pageDtlMap.get("fld_typ");
     dflt_val=(String)pageDtlMap.get("dflt_val");
     %>
<th><label title="CheckAll"><input  type="checkbox" id="<%=fld_nme%>" name="<%=fld_nme%>" onclick="<%=val_cond%>" /><%=dflt_val%> </label></th>
     <%}}%>
<!--<th><label title="CheckAll"><input  type="checkbox" id="all" name="all" onclick="ALLCheckDirect('all','cb_stk_')" />Select </label></th>-->


<th><label title="stock Idn">StkIdn</label></th>

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
<th> Edit</th>

      
 
</tr>
</thead>
<tbody>
<%


int colSpan = vwPrpLst.size()+3;
for(int m=0;m< pktStkIdnList.size();m++){ 
String stkIdn = (String)pktStkIdnList.get(m);
HashMap pktData = (HashMap)pktList.get(stkIdn);
 String vnm = (String)pktData.get("vnm");
    String upr = util.nvl((String)pktData.get("quot"));
    String cmp = util.nvl((String)pktData.get("cmp"));
    String cmpdis = util.nvl((String)pktData.get("cmp_dis"));
String target = "TR_"+stkIdn;
String checkVal = "value("+stkIdn+")";
String checkId = "cb_stk_"+stkIdn;
String onchnge="ChangeFlgDirect("+stkIdn+" , this , 'NO' )";
%>
<tr id="<%=target%>">
<%pageList=(ArrayList)pageDtl.get("CHKBDY");
      if(pageList!=null && pageList.size() >0){
      for(int j=0;j<pageList.size();j++){
      pageDtlMap=(HashMap)pageList.get(j);
      fld_nme=(String)pageDtlMap.get("fld_nme");
      fld_nme=fld_nme.replaceAll("STKIDN",stkIdn);
      fld_ttl=(String)pageDtlMap.get("fld_ttl");
      fld_ttl=fld_ttl.replaceAll("STKIDN",stkIdn);
      val_cond=(String)pageDtlMap.get("val_cond");
      val_cond=val_cond.replaceAll("STKIDN",stkIdn);
      fld_typ=(String)pageDtlMap.get("fld_typ");
     dflt_val=(String)pageDtlMap.get("dflt_val");
     form_nme=(String)pageDtlMap.get("form_nme");
     %>
<td><html:checkbox property="<%=fld_nme%>" styleId="<%=fld_ttl%>"  onclick="<%=val_cond%>" name="<%=form_nme%>"/> </td>
     <%}}%>
<!--<td><html:checkbox property="<%=checkVal%>" styleId="<%=checkId%>"  onclick="<%=onchnge%>" name="stockPrpUpdForm"/> </td>-->

<td><%=stkIdn%></td>
<td><%=pktData.get("vnm")%></td>
<%
 for(int l=0;l<vwPrpLst.size();l++){
   String prp = (String)vwPrpLst.get(l);
    String prpValue =  (String) pktData.get(prp);
    if(prpValue.equals("NA"))
     prpValue = "";
    String prpDsc = (String)mprp.get(prp+"D");
 if(prpDspBlocked.contains(prp)){
}else{
 %>
<td title="<%=prpDsc%>"><%=prpValue%></td>
<%}}%>       
 <td> <a href="StockPrpUpd.do?method=stockUpd&mstkIdn=<%=stkIdn%>&mdl=PRP_UPD"  id="LNK_<%=stkIdn%>" onclick="loadASSFNL('<%=target%>','LNK_<%=stkIdn%>')"  target="SC"  >Edit</a></td>

</tr>

<%}%>        


</tbody>
</table>
</td></tr></table>
    <%}%>
    </td></tr>
  <tr>
    <td><jsp:include page="/footer.jsp" /> </td>
    </tr>
  </table>
 
 
 </body>
</html>