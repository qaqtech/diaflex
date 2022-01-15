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
    <title>Pricing Detail</title>
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
          String pgDef = "PRICE_DETAIL";
          HashMap allPageDtl= (info.getPageDetails() == null)?new HashMap():(HashMap)info.getPageDetails();
          HashMap pageDtl=(HashMap)allPageDtl.get(pgDef);               
          ArrayList pageList=new ArrayList();
          HashMap pageDtlMap=new HashMap();
          ArrayList GncPrpLst=(ArrayList)info.getGncPrpLst();
          String fld_nme="",fld_ttl="",val_cond="",dflt_val="",fld_typ="",form_nme="",lov_qry="";
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
  <img src="../images/line.jpg" border="0" /></td><td valign="middle"> <span class="pgHed">Pricing Detail</span> </td>
</tr></table>
  </td>
  </tr>
    <%
   String vnmNtFnd = (String)request.getAttribute("vnmNotFnd");
     if(vnmNtFnd!=null){%>
    <tr><td valign="top" class="tdLayout"><span class="redLabel"><%=vnmNtFnd%></span></td></tr>
   <%}%>
     <%
  String  lseq =(String)request.getAttribute("seqNo");
  if(lseq!=null){%>
    <tr><td valign="top" class="tdLayout"> <span class="redLabel"><%=lseq%></span> </td></tr>
    <%}%>
      <%
  String  msg =(String)request.getAttribute("msg");
  if(msg!=null){%>
    <tr><td valign="top" class="tdLayout"> <span class="redLabel"><%=msg%></span> </td></tr>
    <%}%>
  <tr ><td valign="top"  class="tdLayout">
   <html:form action="/marketing/memoPrice.do?method=priceDtl"  method="POST">
       <table class="grid1">
<tr>
 <% if(GncPrpLst!=null && GncPrpLst.size()>0){ %>      
<th>Generic Search 
<%
  ArrayList rolenmLst=(ArrayList)info.getRoleLst();
  String usrFlg=util.nvl((String)info.getUsrFlg());
  if(usrFlg.equals("SYS") || rolenmLst.contains("ADMIN")){
  %>
  <img src="../images/edit.jpg" title="Edit Properties Module" onclick="newWindow('<%=info.getReqUrl()%>/dynamicProperty.do?method=load&mdl=PRI_DTL&sname=pridtlGNCSrch&par=GENERIC')" border="0" width="15px" height="15px"/>

  <%}%>
</th>
<%}%>
<th> Packets</th></tr>
<tr>
<% if(GncPrpLst!=null && GncPrpLst.size()>0){%>
<td>
<jsp:include page="/genericSrch.jsp"/>
</td>
<%}%>
<td valign="top">
<table>

<tr>
 <td>Status : </td>
 <td>
 <% pageList= ((ArrayList)pageDtl.get("SELECT") == null)?new ArrayList():(ArrayList)pageDtl.get("SELECT");
             if(pageList!=null && pageList.size() >0){%>
             <html:select property="value(stt)" styleId="Stt" name="priceChangeForm" style="width:100px;" >
               <html:option value="">--Select--</html:option>
             <%
               for(int i=0;i<pageList.size();i++){
                 pageDtlMap=(HashMap)pageList.get(i);
                 fld_nme="value("+(String)pageDtlMap.get("fld_nme")+")";
                 fld_ttl=(String)pageDtlMap.get("fld_ttl");
                 val_cond=(String)pageDtlMap.get("val_cond");
                 dflt_val=(String)pageDtlMap.get("dflt_val");
                 %>
                <html:option value="<%=dflt_val%>"><%=fld_ttl%></html:option>
             <%}%>
            </html:select>           
            <%}else{%>
<html:text property="value(stt)" /> 
<%}%>
</td></tr>
<tr><td>Memo Id </td><td><html:text property="value(memoIdn)"/></td>
 </tr>
<tr>
 <td valign="top">Packets. </td><td>
 <html:textarea property="value(vnmLst)"  cols="21" name="priceChangeForm" />
 </td>
 </tr>
</table>
</td>
</tr>
<tr><td colspan="2" align="center"><html:submit property="view" value="View" styleClass="submit"/>
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
    if(pktList!=null && pktList.size()>0){
    ArrayList vwPrpLst = (ArrayList)session.getAttribute("prpPrpMdl");
    HashMap mprp = info.getMprp();
    %>
 <html:form action="/marketing/memoPrice.do?method=reprc"  method="POST"> 
    <table  class="grid1" align="left" id="dataTable">
<tr><td valign="top" class="tdLayout" nowrap="nowrap" colspan="3">
           <%
            pageList=(ArrayList)pageDtl.get("BUTTON");
            if(pageList!=null && pageList.size() >0){
            for(int j=0;j<pageList.size();j++){
            pageDtlMap=(HashMap)pageList.get(j);
            fld_nme="value("+(String)pageDtlMap.get("fld_nme")+")";
            fld_ttl=(String)pageDtlMap.get("fld_ttl");
            fld_typ=(String)pageDtlMap.get("fld_typ");
            lov_qry=(String)pageDtlMap.get("lov_qry");
            dflt_val=(String)pageDtlMap.get("dflt_val");
            val_cond=(String)pageDtlMap.get("val_cond"); 
            if(fld_typ.equals("S")){ %>
            <html:submit property="<%=fld_nme%>" value="<%=fld_ttl%>"  onclick="<%=val_cond%>" styleClass="submit" />
            <%}else if(fld_typ.equals("B")){%>
            <html:button property="<%=fld_nme%>" value="<%=fld_ttl%>"  onclick="<%=val_cond%>" styleClass="submit" />
            <%}}}
            %>
        
<!--
<html:button property="value(excel)" value="Create Excel"  onclick="goTo('memoPrice.do?method=CrtExcel','','')" styleClass="submit" />
<html:button property="value(excel)" value="Create Excel Sheet"  onclick="goTo('memoPrice.do?method=CrtExcelSheet','','')" styleClass="submit" />
<html:submit property="value(save)" value="RePrice " styleClass="submit"/>
-->

    <%
  ArrayList rolenmLst=(ArrayList)info.getRoleLst();
  String usrFlg=util.nvl((String)info.getUsrFlg());
  if(usrFlg.equals("SYS") || rolenmLst.contains("ADMIN")){
  %>
  &nbsp;&nbsp;<img src="../images/edit.jpg" title="Edit Properties" onclick="newWindow('<%=info.getReqUrl()%>/dynamicProperty.do?method=load&mdl=PRC_PRP&sname=prpPrpMdl&par=V')" border="0" width="15px" height="15px"/> 
  <%}%>
    </td></tr>
<tr>

<th><label title="CheckAll"><input  type="checkbox" id="all" name="all" onclick="ALLCheckDirect('all','cb_stk_')" />Select </label></th>
<th><label title="Packet No">Packet No</label></th>
<th><label title="Price Detail">Detail</label></th>

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
String checkVal = "value("+stkIdn+")";
String checkId = "cb_stk_"+stkIdn;
String onchnge="ChangeFlgDirect("+stkIdn+" , this , 'NO' )";
%>
<tr class="<%=tableTD%>">
<td align="center"><html:checkbox property="<%=checkVal%>" styleId="<%=checkId%>"  onclick="<%=onchnge%>" name="priceChangeForm"/>
<td><A href="<%=info.getReqUrl()%>/marketing/PacketLookup.do?method=pktGrpDtl&mstkIdn=<%=stkIdn%>" id="LNK_<%=vnm%>" onclick="loadASSFNL('<%=target%>','LNK_<%=vnm%>')"  target="frame"><%=pktData.get("vnm")%></a></td>
<td><span  class="img"><img src="../images/plus.png"  id="IMG_<%=stkIdn%>"  onclick="PktPriceDtl('<%=stkIdn%>')" border="0"/></span></td>
<td><%=pktData.get("fquot")%></td>
<td><%=pktData.get("stt1")%></td>
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
</html:form>
    <%}%>
    </td></tr>
  
     <tr>
    <td><jsp:include page="/footer.jsp" /> </td>
    </tr>
    </table></body>