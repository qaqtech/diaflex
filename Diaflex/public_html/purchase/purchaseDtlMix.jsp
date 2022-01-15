<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
 
<%@ page errorPage="../error_page.jsp" %>
<%@ page contentType="text/html;charset=windows-1252"%>
<jsp:useBean id="util" class="ft.com.JspUtil" scope="page" />
<jsp:useBean id="info" class="ft.com.InfoMgr" scope="session" />
<%@ page import="java.util.ArrayList,java.util.HashMap,java.util.List, java.sql.ResultSet,ft.com.*, java.util.Collections , java.util.Date, java.util.ArrayList, ft.com.dao.*"%>
<%@taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<%@taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>
<html>
  <head>
   <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>Purchase Details</title>
   <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/bse.css?v=<%=info.getJsVersion()%>"/>
      <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/style.css?v=<%=info.getJsVersion()%>"/>
      <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/bse.js?v=<%=info.getJsVersion()%> " > </script>
      <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/ajax.js?v=<%=info.getJsVersion()%> " > </script>
       <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/Validation.js " > </script>
         <script type="text/javascript" src="<%=info.getReqUrl()%>/PopScripts/popup.js?v=<%=info.getJsVersion()%> " > </script>
      <script src="<%=info.getReqUrl()%>/PopScripts/jquery-1.2.6.min.js" type="text/javascript"></script>
   </head>
        <%String logId=String.valueOf(info.getLogId());
        String onfocus="cook('"+logId+"')";
        %>
 <body onfocus="<%=onfocus%>" onload="MM_preloadImages('images/faunalogo.png')" topmargin="0" leftmargin="0" rightmargin="0" bottommargin="0">
<div id="backgroundPopup"></div>
<div id="popupContactASSFNL">
<table class="popUpTb">
 <tr><td height="5%">&nbsp;&nbsp;&nbsp;<html:button property="value(close)" styleClass="submit"  onclick="MKEmptyPopup('SC');disablePopupASSFNL();" value="Close"  /> </td></tr>
<tr><td valign="top" height="95%">
<iframe name="SC" class="frameStyle"    align="middle" frameborder="0">

</iframe></td></tr>
</table>
</div>
<div id="popupContactSave" align="center" >
<img src="../images/loading_flame.gif" height="150px" width="150px" border="0" />
</div>
<%
HashMap dbinfo = info.getDmbsInfoLst();
String cnt = (String)dbinfo.get("CNT");
HashMap mprp = info.getMprp();
HashMap prp = info.getPrp();
ArrayList asViewPrp= null;
String rughForm = util.nvl(request.getParameter("rughForm"),"N");
String dfPg="RGH_PUR_DTL";
if(rughForm.equals("Y"))
asViewPrp= (session.getAttribute("RUGH_MIX_VW") == null)?new ArrayList():(ArrayList)session.getAttribute("RUGH_MIX_VW");
else{
 asViewPrp= (session.getAttribute("PUR_MIX_VW") == null)?new ArrayList():(ArrayList)session.getAttribute("PUR_MIX_VW");
 dfPg="MIX_PUR_DTL";
 }
Integer incCount = (Integer)request.getAttribute("lstCnt");
incCount++;
String lotDsc = util.nvl((String)request.getAttribute("lotDsc"));
int asViewPrpsz=asViewPrp.size();
ArrayList purdtlidnLst= (session.getAttribute("purdtlidnLst") == null)?new ArrayList():(ArrayList)session.getAttribute("purdtlidnLst");
int purdtlidnLstsz=purdtlidnLst.size();
ArrayList rolenmLst=(ArrayList)info.getRoleLst();
String usrFlg=util.nvl((String)info.getUsrFlg());
HashMap avgDtl = ((HashMap)request.getAttribute("avgDtl") == null)?new HashMap():(HashMap)request.getAttribute("avgDtl");
 HashMap allPageDtl= (info.getPageDetails() == null)?new HashMap():(HashMap)info.getPageDetails();
  HashMap pageDtl=(HashMap)allPageDtl.get(dfPg);
  ArrayList pageList=new ArrayList();
    HashMap pageDtlMap=new HashMap();
    String fld_nme="",fld_ttl="",val_cond="",dflt_val="",fld_typ="",form_nme="",flg1="",lov_qry="";
   boolean isSplit=false;
    if(pageDtl==null)
    pageDtl = new HashMap();
    pageList=(ArrayList)pageDtl.get("SPLIT");
     if(pageList!=null && pageList.size() >0){
      for(int j=0;j<pageList.size();j++){
           pageDtlMap=(HashMap)pageList.get(j);
            dflt_val=util.nvl((String)pageDtlMap.get("dflt_val"));
            if(dflt_val.equals("Y"))
             isSplit=true;
     }}
    


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
  <img src="../images/line.jpg" border="0" /></td><td valign="middle"> 
  <span class="pgHed">Purchase Details (<bean:write property="value(purIdn)" name="purchaseDtlForm" />)</span> 
<%
  if(usrFlg.equals("SYS") || rolenmLst.contains("ADMIN")){
  %>
  <img src="../images/edit.jpg" title="Edit Properties Module" onclick="newWindow('<%=info.getReqUrl()%>/dynamicProperty.do?method=load&mdl=PUR_MIX_VW&sname=PUR_MIX_VW&par=V')" border="0" width="15px" height="15px"/> 
  <%}%>  
  </td>
</tr></table>
  </td>
  </tr>
  <%String msgdis = (String)request.getAttribute("msg");
  if(msgdis!=null){
  %>
  <tr><td valign="top"  class="tdLayout"><span class="redLabel"><%=msgdis%></span></td></tr>
  <%}
     ArrayList msgLst = (ArrayList)request.getAttribute("msgList");
   if(msgLst!=null){%>
   <tr><td valign="top" class="tdLayout">
   <%for(int i=0;i<msgLst.size();i++){
   String msg = (String)msgLst.get(i);
   %>
    <span class="redLabel"><%=msg%>,</span>
   <%}%>
   </td></tr>
   <%}%> 
   
   <tr>
  <td valign="top" class="hedPg">
  <table class="grid1">
  <tr>
    <th></th>
   
    <th height="15"><div align="center"><strong>Cts</strong></div></th>
    <th height="15"><div align="center"><strong>Avg Rate</strong></div></th>
    <th height="15"><div align="center"><strong>Vlu </strong></div></th>
    <th height="15"><div align="center"><strong>QTY </strong></div></th>
     <td></td>
     <th></th>
     
     <th height="15"><div align="center"><strong>Cts</strong></div></th>
    <th height="15"><div align="center"><strong>Vlu </strong></div></th>
      <th height="15"><div align="center"><strong>QTY </strong></div></th>
  </tr>
 
   <tr>
  
    <td>Purchase Detail</td>
    <td align="right"><label id="purCts"> <%=util.nvl((String)avgDtl.get("PUR_cts"))%></label></td>
    <td align="right"><%=util.nvl((String)avgDtl.get("PUR_avgrte"))%></td>
    <td align="right"><label id="purVal"><%=util.nvl((String)avgDtl.get("PUR_vlu"))%></label></td>
    <td align="right"><label id="purQty"><%=util.nvl((String)avgDtl.get("PUR_qty"))%></label></td>
    <td></td>
    <td>Current Detail</td>
    <td align="right"><label id="curCts"> <%=util.nvl((String)avgDtl.get("CUR_cts"),"0")%></label></td>
    <td align="right"><label id="curVal"><%=util.nvl((String)avgDtl.get("CUR_vlu"),"0")%></label></td>
     <td align="right"><label id="curqty"><%=util.nvl((String)avgDtl.get("CUR_qty"),"0")%></label></td>
  </tr>
   
    <tr>
   <td>Transfer Detail</td>
    <td align="right"><label id="trfCts"> <%=util.nvl((String)avgDtl.get("TRF_cts"),"0")%></label></td>
    <td align="right"><%=util.nvl((String)avgDtl.get("TRF_avgrte"))%></td>
    <td align="right"><label id="trfVal"><%=util.nvl((String)avgDtl.get(""),"0")%></label></td>
     <td align="right"><label id="trfqty"><%=util.nvl((String)avgDtl.get("TRF_qty"),"0")%></label></td>
    <td></td>
     <td>Remaining Detail</td>
     <%
     String PUR_cts = util.nvl((String)avgDtl.get("PUR_cts"),"0");
     String CUR_cts = util.nvl((String)avgDtl.get("CUR_cts"),"0");
     String TRF_cts = util.nvl((String)avgDtl.get("TRF_cts"),"0");
     
     String PUR_vlu = util.nvl((String)avgDtl.get("PUR_vlu"),"0");
     String CUR_vlu = util.nvl((String)avgDtl.get("CUR_vlu"),"0");
     String TRF_vlu = util.nvl((String)avgDtl.get("TRF_vlu"),"0");
     
     String PUR_qty = util.nvl((String)avgDtl.get("PUR_qty"),"0");
     String CUR_qty = util.nvl((String)avgDtl.get("CUR_qty"),"0");
     String TRF_qty = util.nvl((String)avgDtl.get("TRF_qty"),"0");
     
     double remCts = Double.parseDouble(PUR_cts)-(Double.parseDouble(TRF_cts)+Double.parseDouble(CUR_cts));
      double remVal = Double.parseDouble(PUR_vlu)-(Double.parseDouble(TRF_vlu)+Double.parseDouble(CUR_vlu));
       double remqty = Double.parseDouble(PUR_qty)-(Double.parseDouble(TRF_qty)+Double.parseDouble(CUR_qty));
      
       remCts=util.roundToDecimals(remCts, 2);
        remVal=util.roundToDecimals(remVal, 2);
         remqty=util.roundToDecimals(remqty, 2);
     %>
    <td align="right"><label id="remCts"> <%=remCts%></label></td>
    <td align="right"><label id="remVal"><%=remVal%></label></td>
    <td align="right"><label id="remVal"><%=remqty%></label></td>
    </tr>
  </table>
  </td>
  </tr>
   
   <tr>
   <td>
   <table id="dataTable"><tr><td class="tdLayout" valign="top">
   <html:form action="/purchase/purchaseDtlAction.do?method=savemixForm" method="post" styleId="multiPrp">
   <input type="hidden" id="reqUrl" name="reqUrl" value="<%=info.getReqUrl()%>"/>
   <input type="hidden" name="rughForm" id="rughForm" value="<%=rughForm%>" />
   <table class="grid1">
   <tr>
    <th>Sr No</th>
    <th></th>
    <%for(int j=0;j<asViewPrpsz;j++){
    String lprp = (String)asViewPrp.get(j);
    if(lprp.equals("RTE") || lprp.equals("CRTWT") || lprp.equals("VNM")){%>
    <th><%=lprp%> <span class="redLabel">*</span> </th>
    
    <%}else{
    %>
    <th><%=lprp%></th>
    
    <%}%>
    <% if(lprp.equals("RTE")) {%>
     <th>Value</th>
     <% } %>
  <%  }%>
    <th>Action</th>
   </tr>
    <%
    for(int i=1;i<5;i++){%>
    <tr>
    <td><%=i%></td>
    <td></td>
    <%for(int j=0;j<asViewPrpsz;j++){
    String lprp = (String)asViewPrp.get(j);
    String typ = (String)mprp.get(lprp+"T");
    ArrayList prpLst = (ArrayList)prp.get(lprp+"V");
    String value="";
    String idn = lprp+"_"+i;
    String valIdn = "VLU_"+i;
    String onchange="";
    String vonchange="";
    if(lprp.equals("RTE") || lprp.equals("CRTWT")){
   onchange ="CalPValue('CRTWT_"+i+"','RTE_"+i+"','"+valIdn+"')";
    vonchange = "CalPRateByTtlVal('CRTWT_"+i+"','RTE_"+i+"','"+valIdn+"');calAvlValue('VLU_"+i+"')";
    }
     if(lprp.equals("CRTWT")){
     onchange ="CalPValue('CRTWT_"+i+"','RTE_"+i+"','"+valIdn+"');calAvlCts('CRTWT_"+i+"');";
    }
    String fldNm="value("+i+"_"+lprp+")";
    
    if(typ.equals("C")){
    if(prpLst!=null && prpLst.size()>0){
    %>
     <td>
     <html:select property="<%=fldNm%>">
     <%for(int k=0;k<prpLst.size();k++){
     String val = (String)prpLst.get(k);
     %>
     <html:option value="<%=val%>"><%=val%></html:option>
     <%}%>
     </html:select></td>
    <%}}else{
    String val="";
    if(lprp.equals("VNM"))
    val=lotDsc+"_"+incCount++;
    %>
    <td>
    <html:text property="<%=fldNm%>" value="<%=val%>" onchange="<%=onchange%>" styleId="<%=idn%>"/>
    </td>
    <% if(lprp.equals("RTE")) {
     fldNm="value("+i+"_VLU)";
    %>
     <td>
      <html:text property="<%=fldNm%>"  value="<%=val%>" styleId="<%=valIdn%>" onchange="<%=vonchange%>"/>
      </td>
     <% } %>
   <%}%>
    
    <%}%>
      <td></td>
      </tr>
    <%}%>
    <tr><td colspan="<%=asViewPrpsz+3%>" align="center"> <html:submit property="value(addnew)" value="Add New"  styleClass="submit"/></td></tr>
   <%if(purdtlidnLstsz>0){%>
   <%for(int l=0;l<purdtlidnLstsz;l++){
     String purDtlIdn = (String)purdtlidnLst.get(l);
     String cboxPrp = "value(upd_"+ purDtlIdn + ")" ;
     String cboxId = "upd_"+purDtlIdn;
     String vnm = util.nvl((String)avgDtl.get("VNM_"+purDtlIdn));
      String cts = util.nvl((String)avgDtl.get("CTS_"+purDtlIdn));
     %>
   <tr id="<%=purDtlIdn%>">
   <td><%=(l+1)%></td>
   <td><html:checkbox property="<%=cboxPrp%>" styleId="<%=cboxId%>"/></td>
    <%for(int j=0;j<asViewPrpsz;j++){
    String lprp = (String)asViewPrp.get(j);
    String typ = (String)mprp.get(lprp+"T");
    ArrayList prpLst = (ArrayList)prp.get(lprp+"V");
    String idn = lprp+"_update_"+l;
    String valIdn = "VLU_update_"+l;
    String value="";
    String onchange="";
    String vonchange="";
    if(lprp.equals("RTE") || lprp.equals("CRTWT")){
   onchange ="CalPValue('CRTWT_update_"+l+"','RTE_update_"+l+"','"+valIdn+"')";
    vonchange = "CalPRateByTtlVal('CRTWT_update_"+l+"','RTE_update_"+l+"','"+valIdn+"');calAvlValue('VLU_update_"+l+"')";
    }
    if(lprp.equals("CRTWT")){
     onchange ="CalPValue('CRTWT_update_"+l+"','RTE_update_"+l+"','"+valIdn+"');calAvlCts('CRTWT_update_"+l+"');";
    }
    
    String fldNm="value("+purDtlIdn+"_"+lprp+")";
    if(typ.equals("C")){
    if(prpLst!=null && prpLst.size()>0){
    %>
     <td>
     <html:select property="<%=fldNm%>">
     <%for(int k=0;k<prpLst.size();k++){
     String val = (String)prpLst.get(k);
     %>
     <html:option value="<%=val%>"><%=val%></html:option>
     <%}%>
     </html:select></td>
    <%}}else{
    %>
    <td>
    <html:text property="<%=fldNm%>" onchange="<%=onchange%>" styleId="<%=idn%>"/>
    </td>
     <% if(lprp.equals("RTE")) {
     fldNm="value("+purDtlIdn+"_VLU)";
    %>
     <td>
      <html:text property="<%=fldNm%>" styleId="<%=valIdn%>" onchange="<%=vonchange%>" />
      </td>
     <% } %>
     
   <%}%>
    
    <%}
    String link1= "/purchase/purchaseDtlAction.do?method=deletemixForm&purDtlIdn="+purDtlIdn ;%>
    <td><html:link page="<%=link1%>">Delete</html:link>
    <%if(isSplit){
    String splitUrl1="purchaseDtlAction.do?method=LoadSplitStone&purDtlIdn="+purDtlIdn+"&purIdn="+util.nvl((String)info.getValue("purIdn"))+"&rughForm=Y&lotDsc="+vnm+"&ttlcts="+cts;
    %>
    &nbsp;|&nbsp;
    <A href="<%=splitUrl1%>" id="DTL_<%=purDtlIdn%>"  target="SC" title="Click Here To See Details" onclick="loadASSFNL('<%=purDtlIdn%>','DTL_<%=purDtlIdn%>')">
Split</a>
    
    <%}%>
    
    </td>
   </tr>
   <%}%>
   <tr><td colspan="<%=asViewPrpsz+3%>" align="center"> 
   <html:submit property="value(save)" value="Save" onclick="return validateUpdate()"  styleClass="submit"/>&nbsp;
  <html:submit property="value(trfToStk)" value="Transfer To Stock" onclick="return validateUpdate()"  styleClass="submit"/>
  </td>
   </tr>
   </table>
   <%}%>
   </html:form>
   </td>
   </tr>
   </table>
   </td></tr>
    <tr><td>
    <jsp:include page="/footer.jsp" />
    </td></tr></table>
  </body>
</html>