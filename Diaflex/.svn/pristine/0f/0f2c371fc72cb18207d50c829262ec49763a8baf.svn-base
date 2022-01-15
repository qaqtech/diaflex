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
    <title>Assort Selection</title>
    <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/bse.css?v=<%=info.getJsVersion()%>"/>
    <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/general.css?v=<%=info.getJsVersion()%>"/>
     <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/style.css?v=<%=info.getJsVersion()%>"/>
     <script language="javaScript" type="text/javascript" src="<%=info.getReqUrl()%>/scripts/ajax.js?v=<%=info.getJsVersion()%>"></script>
      <script src="../scripts/bse.js" type="text/javascript"></script>
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
    <td height="5" valign="top" class="greyline" ><img src="../images/grey_linebig.png" width="685" height="5" align="right" /></td>
  </tr>
  <tr>
  <td valign="top" class="hedPg">
  <table cellpadding="1" cellspacing="5"><tr><td valign="middle">
  <img src="../images/line.jpg" border="0" /></td><td valign="middle"> <span class="pgHed">Verification</span> </td>
</tr></table>
  </td>
  </tr>
   <%
    //util.updAccessLog(request,response,"Assort Selection", "Assort Selection");
   String msg = (String)request.getAttribute("msg");
   if(msg!=null){%>
    <tr><td valign="top" class="tdLayout"><span class="redLabel"><%=msg%></span></td></tr>
   <%}%>
   <%
   String vnmNtFnd = (String)request.getAttribute("vnmNotFnd");
     if(vnmNtFnd!=null){%>
    <tr><td valign="top" class="tdLayout"><span class="redLabel"><%=vnmNtFnd%></span></td></tr>
   <%}%>
  <tr>
  <td valign="top" class="tdLayout">
  <html:form action="assort/assortSelection.do?method=view" method="post">
  <html:hidden property="value(stt)"  />
  <table  class="grid1">
  <tr><th>Generic Search <%
  ArrayList rolenmLst=(ArrayList)info.getRoleLst();
  String usrFlg=util.nvl((String)info.getUsrFlg());
  if(usrFlg.equals("SYS") || rolenmLst.contains("ADMIN")){
  %>
  <img src="../images/edit.jpg" title="Edit Properties Module" onclick="newWindow('<%=info.getReqUrl()%>/dynamicProperty.do?method=load&mdl=AS_SRCH&sname=asGNCSrch&par=GENERIC')" border="0" width="15px" height="15px"/> 
  <%}%>
  </th><th> Packets</th></tr>
  <tr><td>
   <jsp:include page="/genericSrch.jsp"/>
  </td><td> <html:textarea property="value(vnmLst)" rows="10" name="assortSelectionForm" styleId="vnmLst"  onkeyup="getVnmCount('vnmLst','vnmLstCnt')" onchange="getVnmCount('vnmLst','vnmLstCnt')" /><br><span id="vnmLstCnt">0</span></td> </tr>
 
  <tr><td colspan="2" align="center"><html:submit property="view" value="View" styleClass="submit"/> 
  <html:submit property="viewAll" value="View All" styleClass="submit"/> </td>
  </tr>
  </table>
  </html:form>
  </td></tr>
  <html:form action="assort/assortSelection.do?method=save" method="post" onsubmit="return validate_issue('CHK_' , 'count')" >
   
   
   
  
  
  <tr>
  <td valign="top" class="tdLayout">
  
  <table>
 
  
   <%
   int sr = 0;
   String view = (String)request.getAttribute("view");
   String ctwt=null;
   if(view!=null){
   ArrayList prpDspBlocked = info.getPageblockList();
   ArrayList stockList = (ArrayList)session.getAttribute("StockList");
   if(stockList!=null && stockList.size()>0){
    ArrayList vwPrpLst = (ArrayList)session.getAttribute("asViewLst");
     HashMap totals = (HashMap)request.getAttribute("totalMap");
    HashMap mprp = info.getMprp();
    HashMap allPageDtl= (info.getPageDetails() == null)?new HashMap():(HashMap)info.getPageDetails();
    HashMap pageDtl=(HashMap)allPageDtl.get("ASSORT_VERTION");
    ArrayList pageList=new ArrayList();
    HashMap pageDtlMap=new HashMap();
    String fld_nme="",fld_ttl="",val_cond="",dflt_val="",fld_typ="",form_nme="";
   %>
   <tr>
   <td>
    <table>
   <tr><td>Total :&nbsp;&nbsp;</td><td><span id="ttlqty"> <%=totals.get("qty")%>&nbsp;&nbsp;</span></td><td>cts&nbsp;&nbsp;</td><td><span id="ttlcts"><%=totals.get("cts")%>&nbsp;&nbsp;</span></td><td>Selected:&nbsp;&nbsp;</td><td> Total :&nbsp;&nbsp;</td> <td><span id="qtyTtl">0&nbsp;&nbsp;</span></td><td>Cts&nbsp;&nbsp;</td><td><span id="ctsTtl">0</span> </td> </tr>
  
   </table>
   </td>
   </tr>
   <tr>
  <td >
  <html:submit property="value(pb_lab)" value="Save Changes" onclick="" styleClass="submit" />
      <%
      pageList= ((ArrayList)pageDtl.get("BUTTON") == null)?new ArrayList():(ArrayList)pageDtl.get("BUTTON");
     if(pageList!=null && pageList.size() >0){
       for(int j=0;j<pageList.size();j++){
         pageDtlMap=(HashMap)pageList.get(j);
         fld_nme="value("+(String)pageDtlMap.get("fld_nme")+")";
         fld_ttl=(String)pageDtlMap.get("fld_ttl");
         val_cond=(String)pageDtlMap.get("val_cond"); %>
        <html:button property="<%=fld_nme%>" value="<%=fld_ttl%>"  onclick="<%=val_cond%>" styleClass="submit" /> 

 <%
}}
%><%
  ArrayList rolenmLst=(ArrayList)info.getRoleLst();
  String usrFlg=util.nvl((String)info.getUsrFlg());
  if(usrFlg.equals("SYS") || rolenmLst.contains("ADMIN")){
  %>
  <img src="../images/edit.jpg" title="Edit Properties Module" onclick="newWindow('<%=info.getReqUrl()%>/dynamicProperty.do?method=load&mdl=AS_VIEW&sname=asViewLst&par=A')" border="0" width="15px" height="15px"/> 
  <%}%>
  <!--<html:button property="value(excel)" value="Create Excel"  onclick="goTo('assortSelection.do?method=createXL','','')" styleClass="submit" /></td>-->
  </td>
  </tr>
   <tr><td>
   <table class="grid1">
   
   <tr> 
   
   <th>Sr</th>
   <th><input type="checkbox" name="checkAll" id="checkAll" onclick="checkALL('CHK_','count');assortVerificationExlAll();" /> </th>
        <th>Packet No.</th>
        <%for(int j=0; j < vwPrpLst.size(); j++ ){
    String prp = (String)vwPrpLst.get(j);
    if(prpDspBlocked.contains(prp)){
       }else{
     String hdr = util.nvl((String)mprp.get(prp));
    String prpDsc = util.nvl((String)mprp.get(prp+"D"));
    if(hdr == null) {
        hdr = prp;
       }  

%>
<th title="<%=prpDsc%>"><%=hdr%></th>

<%}}%>
<th nowrap="nowrap"> 
<!--<html:radio property="value(assver)"  styleId="mfgRd"  onclick="ALLRedio('mfgRd' ,'MF_');"  value="MF_FL"/>&nbsp;MFG
<html:radio property="value(assver)"  styleId="asavRd"  onclick="ALLRedio('asavRd' ,'AS_');"  value="AS_AV"/>&nbsp;&nbsp;Assort
<html:radio property="value(assver)" styleId="mxavRd"  onclick="ALLRedio('mxavRd' ,'MX_');"  value="MX_AV"/>&nbsp;&nbsp;Mix-->
  <%
        pageList= ((ArrayList)pageDtl.get("RADIOHDR") == null)?new ArrayList():(ArrayList)pageDtl.get("RADIOHDR");
            if(pageList!=null && pageList.size() >0){
            for(int j=0;j<pageList.size();j++){
            pageDtlMap=(HashMap)pageList.get(j);
            fld_nme="value("+(String)pageDtlMap.get("fld_nme")+")";
            fld_ttl=(String)pageDtlMap.get("fld_ttl");
            fld_typ=(String)pageDtlMap.get("fld_typ");
            dflt_val=(String)pageDtlMap.get("dflt_val");
            val_cond=(String)pageDtlMap.get("val_cond"); %>
        <html:radio property="<%=fld_nme%>"  styleId="<%=fld_typ%>"  onclick="<%=val_cond%>"  value="<%=dflt_val%>"/><%=fld_ttl%>
    <%
    }}
    %>
  
  </th>   
</tr>
 <%
 
 for(int i=0; i < stockList.size(); i++ ){
 HashMap stockPkt = (HashMap)stockList.get(i);
 String stkIdn = (String)stockPkt.get("stk_idn");
 String stt = util.nvl((String)stockPkt.get("stt"));
 String cts = (String)stockPkt.get("cts");
 sr = i+1;
 String checkFldId = "CHK_"+sr;
 String sttPrp = "value("+stkIdn+ ")";
 String sttasmx = "value(stt_" + stkIdn + ")" ;
 String onclick = "AssortTotalCal(this,"+cts+",'','');assortVerificationExl("+stkIdn+", this);";
 String checkid ="check_"+i;
 String rdAVId = "AS_"+i;
 String rdMXId = "MX_"+i;
  String rdMFId = "MF_"+i;
 %>
<tr>

<td><%=sr%></td>
<td><html:checkbox property="<%=sttPrp%>" styleId="<%=checkFldId%>"  name="assortSelectionForm" onclick="<%=onclick%>" value="yes"  /> </td>
<td><%=stockPkt.get("vnm")%></td>
<%for(int j=0; j < vwPrpLst.size(); j++ ){
    String prp = (String)vwPrpLst.get(j);
   if(prpDspBlocked.contains(prp)){
       }else{
    %>
    <td><%=stockPkt.get(prp)%></td>
    <%
    
}}%>
<td nowrap="nowrap">
<!--<html:radio property="<%=sttasmx%>" name="assortSelectionForm" styleId="<%=rdMFId%>" value="MF_FL"/>MF_FL&nbsp;
<html:radio property="<%=sttasmx%>" name="assortSelectionForm" styleId="<%=rdAVId%>" value="AS_AV"/>AS_AV&nbsp;
<html:radio property="<%=sttasmx%>" name="assortSelectionForm" styleId="<%=rdMXId%>" value="MX_AV"/>  MX_AV</td>-->
<%
        pageList= ((ArrayList)pageDtl.get("RADIOBODY") == null)?new ArrayList():(ArrayList)pageDtl.get("RADIOBODY");
            if(pageList!=null && pageList.size() >0){
            for(int j=0;j<pageList.size();j++){
            pageDtlMap=(HashMap)pageList.get(j);
            form_nme=(String)pageDtlMap.get("form_nme");
            fld_nme="value("+(String)pageDtlMap.get("fld_nme")+""+stkIdn+")";
            fld_ttl=(String)pageDtlMap.get("fld_ttl");
            fld_typ=(String)pageDtlMap.get("fld_typ")+""+i;
            dflt_val=(String)pageDtlMap.get("dflt_val");
            val_cond=(String)pageDtlMap.get("val_cond"); %>
        
        <html:radio property="<%=fld_nme%>" name="<%=form_nme%>" styleId="<%=fld_typ%>"  value="<%=dflt_val%>"/><%=fld_ttl%>
          <%}}
    %>       
</tr>
   <%}%>
   </table>
    <input type="hidden" name="count" id="count" value="<%=sr%>" />
   </td></tr>
   
   
  
   <%}else{%>
    <tr>
   <td>Sorry Result not found </td></tr>
  <% }}%>
  
  
  
  
  
  </table>
 
  </td></tr>
   
  </html:form>
   <tr>
    <td><jsp:include page="/footer.jsp" /> </td>
    </tr>
  </table>
  </body>
</html>