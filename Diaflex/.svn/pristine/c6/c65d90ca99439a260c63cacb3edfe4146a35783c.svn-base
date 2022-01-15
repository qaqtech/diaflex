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
    <title>Repairing Return</title>
    <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/bse.css?v=<%=info.getJsVersion()%>"/>
    <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/general.css?v=<%=info.getJsVersion()%>"/>
     <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/style.css?v=<%=info.getJsVersion()%>"/>
     <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/bse.js?v=<%=info.getJsVersion()%> " > </script>
       <script src="<%=info.getReqUrl()%>/scripts/assortScript.js?v=<%=info.getJsVersion()%>" type="text/javascript"></script>
       <script type="text/javascript" src="<%=info.getReqUrl()%>/PopScripts/popup.js?v=<%=info.getJsVersion()%> " > </script>
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
 <tr><td height="5%">&nbsp;&nbsp;&nbsp;<html:button property="value(close)" styleClass="submit"  onclick="MKEmptyPopup('SC');disablePopupASSFNL();uncheckbox()" value="Close"  /> </td></tr>
<tr><td valign="top" height="95%">
<iframe name="SC" class="frameStyle"   align="middle" frameborder="0">

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
  <img src="../images/line.jpg" border="0" /></td><td valign="middle"> <span class="pgHed">Repairing Return</span> </td>
</tr></table>
  </td>
  </tr>
  <%
    HashMap allPageDtl= (info.getPageDetails() == null)?new HashMap():(HashMap)info.getPageDetails();
    HashMap pageDtl=(HashMap)allPageDtl.get("REPAIR_RETURN");
  ArrayList pageList=new ArrayList();
  HashMap pageDtlMap=new HashMap();
    String fld_nme="",fld_ttl="",val_cond="",dflt_val="",fld_typ="",form_nme="",flg1="",lov_qry=""; 
   String vnmNtFnd = (String)request.getAttribute("vnmNotFnd");
     if(vnmNtFnd!=null){%>
    <tr><td valign="top" class="tdLayout"><span class="redLabel"><%=vnmNtFnd%></span></td></tr>
   <%}%>
   <tr>
   
   <td valign="top" class="tdLayout">
   <table><tr><td>
   <html:form action="repair/repairReturn.do?method=fecth" method="post" onsubmit="return validate_return()">
   <table>
   <tr>
    <td>
   Party : </td>
   <td>
   <html:select property="value(empIdn)" styleId="empIdn" name="repairReturnForm"   >
           <html:option value="0" >--select--</html:option>
            <html:optionsCollection property="empList" name="repairReturnForm" 
            label="emp_nme" value="emp_idn" />
            </html:select>
   </td>
   </tr>
   <tr>
   <td>Issue Id:</td><td> <html:text property="value(issueId)"  name="repairReturnForm"  /></td>
   </tr>
   <tr>
   <td>Barcode:</td><td> <html:textarea property="value(vnmLst)" cols="30" name="repairReturnForm"  /></td>
   </tr>
    
   <tr>
   <td colspan="2">  <html:submit property="value(srch)" value="Fetch" styleClass="submit" /></td>
   </tr></table>
   </html:form>
   </td></tr>
   <%if(request.getAttribute("msg")!=null){%>
     <tr><td valign="top" class="tdLayout"><span class="redLabel"><%=request.getAttribute("msg")%></span></td></tr>
   <%}%>
    <%
   ArrayList msgLst = (ArrayList)request.getAttribute("msgList");
   if(msgLst!=null){
   for(int i=0;i<msgLst.size();i++){
   ArrayList msg = (ArrayList)msgLst.get(i);
   %>
    <tr><td valign="top" class="tdLayout"><span class="redLabel"><%=msg.get(1)%></span></td></tr>
   <%}}%>
   <%
   String view = (String)request.getAttribute("view");
   if(view!=null){
   ArrayList stockList = (ArrayList)session.getAttribute("AssortStockList");
   if(stockList!=null && stockList.size()>0){
   ArrayList prpDspBlocked = info.getPageblockList();
    ArrayList vwPrpLst = (ArrayList)session.getAttribute("repViewLst");
      HashMap totals = (HashMap)request.getAttribute("totalMap");
    HashMap mprp = info.getMprp();
    ArrayList options = (ArrayList)request.getAttribute("OPTIONS");
    int sr = 0;
   %>
  
 <tr><td>
   <table>
   <tr><td>Total :&nbsp;&nbsp;</td><td><span id="ttlqty"><%=totals.get("qty")%></span> &nbsp;&nbsp;</td><td>cts&nbsp;&nbsp;</td><td><span id="ttlcts"> <%=totals.get("cts")%></span> &nbsp;&nbsp;</td><td>Selected:&nbsp;&nbsp;</td><td> Total :&nbsp;&nbsp;</td> <td><span id="qtyTtl">0&nbsp;&nbsp;</span></td><td>Cts&nbsp;&nbsp;</td><td><span id="ctsTtl">0</span> </td> </tr>
   </table>
   </td></tr>
    <html:form action="repair/repairReturn.do?method=Return" method="post" onsubmit="return validate_issue('CHK_' , 'count')" enctype="multipart/form-data" >
  <tr>  <td>
     <!--<html:submit property="value(return)" styleClass="submit" value="Return" />
   <html:submit property="value(transpertoprvprocess)" styleClass="submit" value="Transfer To Previous Process" />
-->
  <%
      pageList=(ArrayList)pageDtl.get("BUTTON");
      if(pageList!=null && pageList.size() >0){
      for(int j=0;j<pageList.size();j++){
      pageDtlMap=(HashMap)pageList.get(j);
      fld_nme="value("+(String)pageDtlMap.get("fld_nme")+")";
      val_cond=(String)pageDtlMap.get("val_cond");
      fld_ttl=(String)pageDtlMap.get("fld_ttl");
      val_cond=(String)pageDtlMap.get("val_cond");
      fld_typ=(String)pageDtlMap.get("fld_typ");
      flg1=(String)pageDtlMap.get("flg1");
      if(fld_typ.equals("S")){ %>
     <html:submit property="<%=fld_nme%>" value="<%=fld_ttl%>"  onclick="<%=val_cond%>" styleClass="submit" />
      <%}else{%>
    <html:button property="<%=fld_nme%>" value="<%=fld_ttl%>"  onclick="<%=val_cond%>" styleClass="submit"  />  
  <%
  }}}
  %>
  <%
  ArrayList rolenmLst=(ArrayList)info.getRoleLst();
  String usrFlg=util.nvl((String)info.getUsrFlg());
  if(usrFlg.equals("SYS") || rolenmLst.contains("ADMIN")){
  %>
  <img src="../images/edit.jpg" title="Edit Properties Module" onclick="newWindow('<%=info.getReqUrl()%>/dynamicProperty.do?method=load&mdl=REP_VIEW&sname=repViewLst&par=A')" border="0" width="15px" height="15px"/> 
  <%}%>
  </td>
  
  </tr>
  
   <tr><td>
  
   <table class="grid1" id="dataTable">
   <tr> <th><input type="checkbox" name="checkAll" id="checkAll" onclick="CheckBOXALLForm('1',this,'cb_inv_')" /> </th><th>Sr</th>
       <th>Issue Id</th>
        <th>Packet No.</th>
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

<%
for(int j=0; j < options.size(); j++) {
String lopt = (String)options.get(j);
String onClick="ALLRedio('"+lopt+"','"+lopt+"_')";
%>
<th><input type="radio" id="<%=lopt%>" name="sttth" onclick="<%=onClick%>"/><%=lopt%></th>
<%}%>
<th> Edit</th>
</tr>
 <%
 int colSpan = vwPrpLst.size()+options.size()+4;
  
      
 for(int i=0; i < stockList.size(); i++ ){
 HashMap stockPkt = (HashMap)stockList.get(i);
 String stkIdn = (String)stockPkt.get("stk_idn");
 String cts = (String)stockPkt.get("cts");
 String issIdn = (String)stockPkt.get("issIdn");
 String onclick = "AssortRtnTotalCal(this,"+cts+","+issIdn+","+stkIdn+")";
 String target = "TR_"+stkIdn;
 sr = i+1;
 String checkFldId = "CHK_"+sr;
 String checkFldVal = "value("+stkIdn+")";
  if(options == null)
        options = new ArrayList();
   
 %>
<tr id="<%=target%>">
  <td><input type="checkbox" name="cb_inv_<%=stkIdn%>" value="<%=issIdn%>_<%=stkIdn%>" id="cb_inv_<%=stkIdn%>"  /></td>
<td><%=sr%><input type="hidden" id="CTS_<%=stkIdn%>"  value="<%=cts%>" /></td>
<td><%=stockPkt.get("issIdn")%></td>
<td><%=stockPkt.get("vnm")%></td>
<%for(int j=0; j < vwPrpLst.size(); j++ ){
    String prp = (String)vwPrpLst.get(j);
    if(prpDspBlocked.contains(prp)){
    }else{
    %>
    <td><%=stockPkt.get(prp)%></td>
<%}}%>
<%
for(int j=0; j < options.size(); j++) {%>

<%
String lopt = (String)options.get(j);
String rfld = "value(STT_"+ stkIdn + ")";
String rId = lopt+"_"+stkIdn;
%>
<td><html:radio property="<%=rfld%>" styleId="<%=rId%>" value="<%=lopt%>"/>&nbsp;<%=lopt%></td>
<%}
%>

 <td> <a href="repairReturn.do?method=stockUpd&mstkIdn=<%=stkIdn%>&issIdn=<%=issIdn%>" id="LNK_<%=stkIdn%>" onclick="loadASSFNL('<%=target%>','LNK_<%=stkIdn%>')"  target="SC">Edit</a></td>

</tr>

   <%}%>
   </table>
  
   </td></tr>
   <input type="hidden" name="count" id="count" value="<%=sr%>" />
  </html:form>
   <%}else{%>
   <tr><td>Sorry no result found </td></tr>
   <%}}%>
   
   
   </table>
   
  
  
   <tr>
    <td><jsp:include page="/footer.jsp" /> </td>
    </tr>
  </table>

  </body>
</html>