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
    <title>Lab Comparison Return</title>
    <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/bse.css?v=<%=info.getJsVersion()%>"/>
    <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/general.css?v=<%=info.getJsVersion()%>"/>
     <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/style.css?v=<%=info.getJsVersion()%>"/>
<script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/bse.js?v=<%=info.getJsVersion()%> " > </script>
       <script src="../scripts/assortScript.js" type="text/javascript"></script>
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
<iframe name="SC" class="frameStyle" align="middle" frameborder="0">

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
  <img src="../images/line.jpg" border="0" /></td><td valign="middle"> <span class="pgHed">Lab Final Check Return</span> </td>
</tr></table>
  </td>
  </tr>
    <%
   String vnmNtFnd = (String)request.getAttribute("vnmNotFnd");
     if(vnmNtFnd!=null){%>
    <tr><td valign="top" class="tdLayout"><span class="redLabel"><%=vnmNtFnd%></span></td></tr>
   <%}%>
   <tr>
   <td valign="top" class="tdLayout">
   <html:form  action="lab/labComparisonRtn.do?method=view" method="post" >
   <html:hidden property="prcId" name="labComparisonRtnForm" />
   <table>
    <tr>
    <td>
    Employee : </td>
   <td>
    <html:select property="value(empIdn)" styleId="empIdn" name="labComparisonRtnForm"   >
    <html:option value="0" >--select--</html:option>
    <html:optionsCollection property="empList" name="labComparisonRtnForm"  label="emp_nme" value="emp_idn" />
    </html:select>
   </td>
   </tr>
   <tr>
   <td>Issue Id:</td><td> <html:text property="value(issueId)"  name="labComparisonRtnForm"  /></td>
   </tr>
   <tr>
   <td>Barcode:</td><td> <html:textarea property="value(vnmLst)" cols="30" name="labComparisonRtnForm"  /></td>
   </tr>
    
   <tr>
   <td colspan="2"><html:submit property="value(srch)" value="Fetch" styleClass="submit" /></td>
   </tr></table>
  </html:form>
   </td></tr>
   <%
   String view = (String)request.getAttribute("view");
   if(view!=null){
   ArrayList stockList = (ArrayList)session.getAttribute("LabStockList");
   if(stockList!=null && stockList.size()>0){
    ArrayList vwPrpLst = (ArrayList)session.getAttribute("LbComViewLst");
      HashMap totals = (HashMap)request.getAttribute("totalMap");
     String prcId = util.nvl((String)request.getAttribute("prcId"));
        String openPopUp = "openPopUp('labComparisonRtn.do?method=Openpop&prcID="+prcId+"' ,'SC','multiprp')";
       ArrayList prpDspBlocked = info.getPageblockList();
       ArrayList options = (ArrayList)request.getAttribute("OPTIONS");
       if(options == null)
        options = new ArrayList();
    HashMap mprp = info.getMprp();
    int sr = 0;
   %>
  
 <tr><td valign="top" class="tdLayout">
   <table>
   <tr><td>Total :&nbsp;&nbsp;</td><td><span id="ttlqty"> <%=totals.get("qty")%>&nbsp;&nbsp;</span></td><td> cts&nbsp;&nbsp;</td><td><span id="ttlcts"><%=totals.get("cts")%>&nbsp;&nbsp;</span></td><td>Selected:&nbsp;&nbsp;</td><td> Total :&nbsp;&nbsp;</td> <td><span id="qtyTtl">0&nbsp;&nbsp;</span></td><td>Cts&nbsp;&nbsp;</td><td><span id="ctsTtl">0</span> </td> </tr>
  
   </table>
   </td></tr>
    <html:form action="lab/labComparisonRtn.do?method=Return" method="post" onsubmit="return validate_issue('CHK_' , 'count')" >
  

  
  <tr><td valign="top" class="tdLayout">
 
  <table><tr>
  <!--<td><html:submit property="value(issue)" styleClass="submit" value="Return" /></td>
  <td>  <html:button property="value(multi)" styleClass="submit" styleId="multiprp" onclick="<%=openPopUp%>"   value="Multiple Packet Update" /></td>
  <td><html:button property="value(excel)" styleClass="submit" onclick="ExcelGen('labComparisonRtn.do?method=excel')" value="Create Excel" /></td>
  <td><html:button property="value(comExcel)" styleClass="submit" onclick="ExcelGen('labComparisonRtn.do?method=comExcel')" value="Comparison Excel" /></td>
  <td><html:button property="value(singlecomExcel)" value="Lab Comparison Excel"  onclick="ExcelGen('labComparisonRtn.do?method=labComExcel')" styleClass="submit" />  </td>
  -->
  <%
            HashMap allPageDtl= (info.getPageDetails() == null)?new HashMap():(HashMap)info.getPageDetails();
            HashMap pageDtl=(HashMap)allPageDtl.get("LAB_FINALRETURN");
    ArrayList pageList=new ArrayList();
    HashMap pageDtlMap=new HashMap();
    String fld_nme="",fld_ttl="",val_cond="",dflt_val="",fld_typ="",form_nme="",flg1="";
            pageList= ((ArrayList)pageDtl.get("BUTTON") == null)?new ArrayList():(ArrayList)pageDtl.get("BUTTON");     
      if(pageList!=null && pageList.size() >0){
      for(int j=0;j<pageList.size();j++){
      pageDtlMap=(HashMap)pageList.get(j);
      fld_nme="value("+(String)pageDtlMap.get("fld_nme")+")";
      fld_ttl=(String)pageDtlMap.get("fld_ttl");
      val_cond=(String)pageDtlMap.get("val_cond");
      if(val_cond.indexOf("PRCID") !=  - 1)
      val_cond=val_cond.replaceAll("PRCID",prcId);
      fld_typ=(String)pageDtlMap.get("fld_typ");
      flg1=(String)pageDtlMap.get("flg1");
      if(fld_typ.equals("S")){ %>
<td><html:submit property="<%=fld_nme%>" value="<%=fld_ttl%>"  onclick="<%=val_cond%>" styleClass="submit" /></td>
      <%}else if(fld_typ.equals("B")){%>
<td><html:button property="<%=fld_nme%>" value="<%=fld_ttl%>"  onclick="<%=val_cond%>" styleClass="submit" styleId="<%=flg1%>" />          </td>
<%
}else if(fld_typ.equals("HB")){
%>
<td><button type="button" onclick="<%=val_cond%>" class="submit" accesskey="S" id="<%=fld_nme%>" name="<%=fld_nme%>" ><%=fld_ttl%></button></td>
<%
}}}
%>
  </tr></table>
  </td></tr>
  <tr><td valign="top" class="tdLayout">&nbsp;</td></tr>
   <tr><td valign="top" class="tdLayout">
  
   <table class="grid1" id="dataTable">
   <tr> <th>Sr</th>
        <th>Save<input type="checkbox" name="checkAll" id="checkAll" onclick="checkALL('CHK_','count');addInList(this,'ALL','LabStockList')" /></th>
<th> Edit</th>
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
<th >
<%
for(int k=0; k < options.size(); k++) {%>

<%
String lopt = (String)options.get(k);

%>
<div class="float">
<input type="radio" id="<%=lopt%>" name="sttth" onclick="ALLRedioLAB('<%=lopt%>','<%=lopt%>_')"/>&nbsp;<%=lopt%>
</div>
<%}
%>

</th>

</tr>
 <%
 int colSpan = vwPrpLst.size()+4;
 for(int i=0; i < stockList.size(); i++ ){
 HashMap stockPkt = (HashMap)stockList.get(i);
 String stkIdn = (String)stockPkt.get("stk_idn");
 String cts = (String)stockPkt.get("cts");
 String issIdn = (String)stockPkt.get("issIdn");
  String target = "TR_"+stkIdn;
 sr = i+1;

 String onclick = "AssortTotalCal(this,"+cts+",'','');addInList(this,'"+stkIdn+"','LabStockList')";
 String onclickEXC = "LabTotalCal(this,"+cts+",'','"+stkIdn+"');";
 String checkFldId = "CHK_"+sr;
 String checkEXCId = "EXC_"+sr;
 String checkFldVal = "value("+stkIdn+")";
 String checkEXCVal = "value(EXC_"+stkIdn+")";
 String url =info.getReqUrl()+"/marketing/StockPrpUpd.do?method=stockUpd&mstkIdn="+stkIdn+"&mdl=AS_PRC_EDIT&issueID="+issIdn;
 %>
<tr id=<%=target%>>
<td><%=sr%><input type="hidden" id="CTS_<%=stkIdn%>"  value="<%=cts%>" /></td>
<td><html:checkbox property="<%=checkFldVal%>" styleId="<%=checkFldId%>" name="labComparisonRtnForm" onclick="<%=onclick%>"  value="yes" /> </td> 
<td> <a href="labComparisonRtn.do?method=stockUpd&mstkIdn=<%=stkIdn%>&issIdn=<%=issIdn%>" id="LNK_<%=stkIdn%>" onclick="loadASSFNL('<%=target%>','LNK_<%=stkIdn%>')"  target="SC">Edit</a></td>
<td><%=issIdn%></td>
<td><%=stockPkt.get("vnm")%></td>
<%for(int j=0; j < vwPrpLst.size(); j++ ){
    String prp = (String)vwPrpLst.get(j);
    if(prpDspBlocked.contains(prp)){
    }else{
    %>
    <td><%=util.nvl((String)stockPkt.get(prp))%></td>
<%}}%>
<td><table><tr><%
for(int j=0; j < options.size(); j++) {%>

<%
String lopt = (String)options.get(j);
String rfld = "value(STT_"+ stkIdn + ")";
 String fldID = lopt+"_"+sr;
%>
<td><html:radio property="<%=rfld%>" value="<%=lopt%>"  styleId="<%=fldID%>"/>&nbsp;<%=lopt%></td>
<%}
%>
</tr></table>

</td>

</tr>


   <%}%>
   </table>
  
   </td></tr>
   <input type="hidden" name="count" id="count" value="<%=sr%>" />
  </html:form>
   <%}else{%>
   <tr><td>Sorry no result found </td></tr>
   <%}}%>
   <tr>
    <td><jsp:include page="/footer.jsp" /> </td>
    </tr>
   
   </table>
   </body>
  </html>