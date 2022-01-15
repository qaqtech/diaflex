<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page errorPage="../error_page.jsp" %>
<%@ page contentType="text/html;charset=windows-1252"%>
<jsp:useBean id="info" class="ft.com.InfoMgr" scope="session" />
<jsp:useBean id="util" class="ft.com.JspUtil" scope="page" />
<jsp:useBean id="gtMgr" class="ft.com.GtMgr" scope="session" />
<%@ page import="java.util.ArrayList,java.util.HashMap,java.util.LinkedHashMap, ft.com.dao.GtPktDtl,java.util.Set,java.sql.ResultSet,ft.com.*, java.util.Date, java.util.ArrayList, ft.com.dao.*"%>
<%@taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>Lab Selection</title>
    <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/bse.css?v=<%=info.getJsVersion()%>"/>
    <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/general.css?v=<%=info.getJsVersion()%>"/>
     <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/style.css?v=<%=info.getJsVersion()%>"/>
      <script src="../scripts/bse.js" type="text/javascript"></script>
       <script src="<%=info.getReqUrl()%>/scripts/assortScript.js?v=<%=info.getJsVersion()%>" type="text/javascript"></script>
        <script src="<%=info.getReqUrl()%>/PopScripts/jquery-1.2.6.min.js" type="text/javascript"></script>
     <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/general.css?v=<%=info.getJsVersion()%>"/>
        <script type="text/javascript" src="<%=info.getReqUrl()%>/PopScripts/popup.js?v=<%=info.getJsVersion()%> " > </script>
        <script language="javaScript" type="text/javascript" src="<%=info.getReqUrl()%>/scripts/ajax.js?v=<%=info.getJsVersion()%>"></script>
         <script language="javaScript" type="text/javascript" src="<%=info.getReqUrl()%>/scripts/bse.js?v=<%=info.getJsVersion()%>"></script>
        <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/bse.css?v=<%=info.getJsVersion()%>"/>
     <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/labScripts.js?v=<%=info.getJsVersion()%> " > </script>

  </head>
        <%String logId=String.valueOf(info.getLogId());
        String onfocus="cook('"+logId+"')";
        String lstNme = (String)gtMgr.getValue("lstNmeSEL");
         gtMgr.setValue(lstNme+"_SEL",new ArrayList());
        %>
 <body onfocus="<%=onfocus%>" onload="MM_preloadImages('images/faunalogo.png')" topmargin="0" leftmargin="0" rightmargin="0" bottommargin="0">

<input type="hidden" id="reqUrl" name="reqUrl" value="<%=info.getReqUrl()%>"/>
 <div id="backgroundPopup"></div>
<div id="popupContactASSFNL">
<table class="popUpTb">
 <tr><td height="5%">&nbsp;&nbsp;&nbsp;<html:button property="value(close)" styleClass="submit"  onclick="MKEmptyPopup('SC');disablePopupASSFNL();" value="Close"  /> </td></tr>
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
    <td height="5" valign="top" class="greyline" ><img src="../images/grey_linebig.png" width="685" height="5" align="right" /></td>
  </tr>
  <tr>
  <td valign="top" class="hedPg">
  <table cellpadding="1" cellspacing="5"><tr><td valign="middle">
  <img src="../images/line.jpg" border="0" /></td><td valign="middle"> <span class="pgHed">Lab Selection</span> </td>
</tr></table>
  </td>
  </tr>
  <%
   String vnmNtFnd = (String)request.getAttribute("vnmNotFnd");
     if(vnmNtFnd!=null){%>
    <tr><td valign="top" class="tdLayout"><span class="redLabel"><%=vnmNtFnd%></span></td></tr>
   <%}%>
    <%
   String msg = (String)request.getAttribute("msg");
     if(msg!=null){%>
    <tr><td valign="top" class="tdLayout"><span class="redLabel"><%=msg%></span></td></tr>
   <%}%>
   

   
  <tr>
  <td valign="top" class="tdLayout">
  <html:form action="lab/labSelection.do?method=view" method="post">
 
 <table  class="grid1">
  <tr><th>Generic Search 
  <%
  ArrayList rolenmLst=(ArrayList)info.getRoleLst();
  String usrFlg=util.nvl((String)info.getUsrFlg());
  if(usrFlg.equals("SYS") || rolenmLst.contains("ADMIN")){
  %>
  <img src="../images/edit.jpg" title="Edit Properties Module" onclick="newWindow('<%=info.getReqUrl()%>/dynamicProperty.do?method=load&mdl=LB_SRCH&sname=LBGNCSrch&par=GENERIC')" border="0" width="15px" height="15px"/> 
  <%}%>
  </th><th> Packets</th></tr>
  <tr><td>
   <jsp:include page="/genericSrch.jsp"/>
  </td><td valign="top">
 <html:textarea property="value(vnmLst)" rows="10" name="labSelectionForm"  styleId="vnmLst"  onkeyup="getVnmCount('vnmLst','vnmLstCnt')" onchange="getVnmCount('vnmLst','vnmLstCnt')"/><br><span id="vnmLstCnt">0</span></td> </tr>
 <tr><td colspan="2" align="center"><html:submit property="view" value="View" styleClass="submit"/> 
  <html:submit property="viewAll" value="View All" styleClass="submit"/> </td>
  </tr>
  
  </table>
  </html:form>
  </td></tr>
  <html:form action="lab/labSelection.do?method=save" method="post"  onsubmit="return validate_issue('CHK_' , 'count')">
   <html:hidden property="value(lstNme)" styleId="lstNme"  value="<%=lstNme%>"  name="labSelectionForm" />
  <tr>
  <td valign="top" class="tdLayout">
  
  <table>
 
  
   <%
   String view = (String)request.getAttribute("view");
   String ctwt=null;
   if(view!=null){
   ArrayList prpDspBlocked = info.getPageblockList();
   HashMap stockListMap = (HashMap)gtMgr.getValue(lstNme);
   if(stockListMap!=null && stockListMap.size()>0){
    ArrayList vwPrpLst = (ArrayList)session.getAttribute("LabSelViewLst");
    int sr=0;
   
    int count=vwPrpLst.size();
    HashMap mprp = info.getMprp(); 
    
      HashMap totals = (HashMap)gtMgr.getValue(lstNme+"_TTL");
   
HashMap allPageDtl= (info.getPageDetails() == null)?new HashMap():(HashMap)info.getPageDetails();
HashMap pageDtl=(HashMap)allPageDtl.get("LAB_SELECTION");
    ArrayList pageList=new ArrayList();
    HashMap pageDtlMap=new HashMap();
    String fld_nme="",fld_ttl="",val_cond="",dflt_val="",fld_typ="",form_nme="",lov_qry="";
   %>
<tr><td>
<table>
<tr><td>Total :&nbsp;&nbsp;</td><td><span id="ttlqty"> <%=totals.get("ZQ")%>&nbsp;&nbsp;</span></td><td>cts&nbsp;&nbsp;</td><td><span id="ttlcts"><%=totals.get("ZW")%>&nbsp;&nbsp;</span></td>
<td>Avg&nbsp;&nbsp;</td><td><span id="ttlavg"><%=totals.get("ZA")%>&nbsp;&nbsp;</span></td>
<td>Vlu&nbsp;&nbsp;</td><td><span id="ttlvlu"><%=totals.get("ZV")%>&nbsp;&nbsp;</span></td>
<td>RapVlu&nbsp;&nbsp;</td><td><span id="ttlrapvlu"><%=totals.get("ZR")%>&nbsp;&nbsp;</span></td>
<td>Selected:&nbsp;&nbsp;</td><td> Total :&nbsp;&nbsp;</td> 
<td><span id="qtyTtl">0&nbsp;&nbsp;</span></td>
<td>Cts&nbsp;&nbsp;</td><td><span id="ctsTtl">0</span> </td>
<td>AvgRte&nbsp;&nbsp;</td><td><span id="avgTtl">0</span> </td>
<td>Vlu&nbsp;&nbsp;</td><td><span id="vluTtl">0</span> </td>
<td>RapVlu&nbsp;&nbsp;</td><td><span id="rapvluTtl">0</span> </td>
</tr>
</table>
</td></tr>
  <tr>
  <td>
  <table><tr><td>
  <html:submit property="value(pb_lab)" value="Save Changes" onclick="" styleClass="submit" /></td>
 <% 
pageList= ((ArrayList)pageDtl.get("BUTTON") == null)?new ArrayList():(ArrayList)pageDtl.get("BUTTON"); 
if(pageList!=null && pageList.size() >0){
for(int j=0;j<pageList.size();j++){
pageDtlMap=(HashMap)pageList.get(j);
fld_nme="value("+(String)pageDtlMap.get("fld_nme")+")";
fld_ttl=(String)pageDtlMap.get("fld_ttl");
fld_typ=(String)pageDtlMap.get("fld_typ");
lov_qry=(String)pageDtlMap.get("lov_qry");
dflt_val=(String)pageDtlMap.get("dflt_val");
val_cond=(String)pageDtlMap.get("val_cond");
if(fld_typ.equals("S")){
%>
<td><html:submit property="<%=fld_nme%>" value="<%=fld_ttl%>" onclick="<%=val_cond%>" styleClass="submit"/></td>
<%}else if(fld_typ.equals("B")){%>
<td><html:button property="<%=fld_nme%>" value="<%=fld_ttl%>" styleId="<%=dflt_val%>" onclick="<%=val_cond%>" styleClass="submit"/></td>
<%}else if(fld_typ.equals("HB")){%>
<td align="right"><button type="button" onclick="<%=val_cond%>" class="submit" accesskey="<%=lov_qry%>" id="<%=fld_nme%>" name="<%=fld_nme%>" ><%=fld_ttl%></button></td>
<%}}}
%>

  
  
 
  <td>Common Lab Selection : </td><td>
  
<html:select property="value(commLab)"   onchange="setLabToLabLst(this , 'lab_')" name="labSelectionForm">
       <html:option value="0">---select---</html:option>
          <html:optionsCollection property="labList" name="labSelectionForm" 
           label="labDesc" value="labVal" />
    </html:select>

  </td>
  <td><div class="red">&nbsp;</div></td><td>&nbsp;Lab Return&nbsp;</td>
  <td><div class="Blue">&nbsp;</div> </td><td>&nbsp;Lab Available&nbsp;</td>
  <%
  ArrayList rolenmLst=(ArrayList)info.getRoleLst();
  String usrFlg=util.nvl((String)info.getUsrFlg());
  if(usrFlg.equals("SYS") || rolenmLst.contains("ADMIN")){
  %>
  <td><img src="../images/edit.jpg" title="Edit Properties Module" onclick="newWindow('<%=info.getReqUrl()%>/dynamicProperty.do?method=load&mdl=LAB_SEL_VW&sname=LabSelViewLst&par=A')" border="0" width="15px" height="15px"/> </td>
  <%}%>
  </tr></table>
  </td></tr>
   <tr><td>
   <table class="grid1" id="dataTable">
   <tr> <th>Sr</th>
   <th>Select All<input type="checkbox" name="checkAll" id="checkAll" onclick="GenSel(this,'ALL')" /></th>
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
<%}}
pageList= ((ArrayList)pageDtl.get("RADIOHDR") == null)?new ArrayList():(ArrayList)pageDtl.get("RADIOHDR");
            if(pageList!=null && pageList.size() >0){
            for(int j=0;j<pageList.size();j++){
            pageDtlMap=(HashMap)pageList.get(j);
            fld_nme=(String)pageDtlMap.get("fld_nme");
            fld_ttl=(String)pageDtlMap.get("fld_ttl");
            fld_typ=(String)pageDtlMap.get("fld_typ");
            dflt_val=(String)pageDtlMap.get("dflt_val");
            val_cond=(String)pageDtlMap.get("val_cond"); %>
        
       <th><%=fld_ttl%> <input type="radio" id="<%=fld_typ%>" name="<%=fld_nme%>" onclick="<%=val_cond%>"/></th>
    <%
    }}
    %>
<!--<th>None <input type="radio" id="None" name="sttth" onclick="ALLRedioLAB('None','None_')"/> </th>
<th>REP <input type="radio" id="REP" name="sttth" onclick="ALLRedioLAB('REP','REP_')"/>  </th>
<th>MIX <input type="radio" id="MIX" name="sttth" onclick="ALLRedioLAB('MIX','MIX_')"/></th>
<th>Proportion<input type="radio" id="PRO" name="sttth" onclick="ALLRedioLAB('PRO','PRO_')"/></th>-->
<th>LAB <input type="radio" id="LB" name="sttth" onclick="ALLRedioLAB('LB','LB_')"/> </th>
<th>Edit</th>
</tr>
 <%
 ArrayList stockIdnLst =new ArrayList();
   LinkedHashMap stockList = new LinkedHashMap(stockListMap); 
 Set<String> keys = stockList.keySet();
        for(String key: keys){
       stockIdnLst.add(key);
        }
for(int i=0; i < stockIdnLst.size(); i++ ){

String stkIdn = (String)stockIdnLst.get(i);
GtPktDtl stockPkt = (GtPktDtl)stockList.get(stkIdn);
 String styleCode = "";

 
 String stt = util.nvl((String)stockPkt.getValue("stt"));
 String rte = util.nvl((String)stockPkt.getValue("quot"));
 if(stt.equals("LB_RT"))
  styleCode = "style=\"color: red;\"";
  if(stt.equals("AS_FN"))
   styleCode = "style=\"color: Blue;\"";
 sr = i+1;
String checkFldId = "CHK_"+sr;
String checkFldVal = "value("+checkFldId+")";
 String sttPrp = "value(stt_" +stkIdn+ ")";
 String labVal = "value(lab_" +stkIdn+ ")";
 String labIdn = "lab_"+stkIdn ;
String onclick = "GenSel(this,'"+stkIdn+"')";
 String lbID = "LB_"+sr;
 String repID = "REP_"+sr;
 String mixID = "MIX_"+sr;
 String proID = "PRO_"+sr;
  String noneID = "None_"+sr;
  String target = "SC_"+stkIdn;
  String url =info.getReqUrl()+"/marketing/StockPrpUpd.do?method=stockUpd&mstkIdn="+stkIdn+"&mdl=SARIN_PRP"; 

 String cts = (String)stockPkt.getValue("cts");
 %>
<tr <%=styleCode%> id="<%=target%>">

<td><%=sr%><input type="hidden" id="CTS_<%=stkIdn%>"  value="<%=cts%>" /></td>

<td align="center">
<html:checkbox property="<%=checkFldVal%>" styleId="<%=checkFldId%>" name="labSelectionForm" onclick="<%=onclick%>" value="<%=stkIdn%>" /> 
<td><%=stockPkt.getValue("vnm")%></td>
<%
String cellStyle ="";
float qout = 0;
if(!rte.equals(""))
qout = Float.valueOf(rte);
for(int j=0; j < vwPrpLst.size(); j++ ){
    String prp = (String)vwPrpLst.get(j);
    if(prpDspBlocked.contains(prp)){
       }else{
     if(prp.equals("MIX_RTE")){
     cellStyle = "";
     String mixRte = util.nvl((String)stockPkt.getValue(prp),"0");
    
     if(mixRte.equals("NA"))
        mixRte = "0";
       float mixIntRTE = Float.valueOf(mixRte);
       if(mixIntRTE >qout)
       cellStyle ="style=\"background-color:Lime;\"";
     }
    %>
    <td <%=cellStyle%> ><%=stockPkt.getValue(prp)%></td>
    <%}}%>

<!--<td><html:radio property="<%=sttPrp%>" name="labSelectionForm"  styleId="<%=noneID%>" value="None"/></td>
<td><html:radio property="<%=sttPrp%>" name="labSelectionForm"  styleId="<%=repID%>" value="REP"/></td>
<td><html:radio property="<%=sttPrp%>" name="labSelectionForm"  styleId="<%=mixID%>" value="MX"/></td>
<td><html:radio property="<%=sttPrp%>" name="labSelectionForm"  styleId="<%=proID%>" value="PRO"/></td>-->

<%
pageList= ((ArrayList)pageDtl.get("RADIOBODY") == null)?new ArrayList():(ArrayList)pageDtl.get("RADIOBODY");
            if(pageList!=null && pageList.size() >0){
            for(int j=0;j<pageList.size();j++){
            pageDtlMap=(HashMap)pageList.get(j);
            form_nme=(String)pageDtlMap.get("form_nme");
            fld_nme="value("+(String)pageDtlMap.get("fld_nme")+""+stkIdn+")";
            fld_ttl=(String)pageDtlMap.get("fld_ttl");
            fld_typ=(String)pageDtlMap.get("fld_typ")+""+sr;
            dflt_val=(String)pageDtlMap.get("dflt_val");
            val_cond=(String)pageDtlMap.get("val_cond"); %>
        
        <td><html:radio property="<%=fld_nme%>" name="<%=form_nme%>" styleId="<%=fld_typ%>" value="<%=fld_ttl%>"/></td>
          <%}}
    %>       
<td><html:radio property="<%=sttPrp%>" name="labSelectionForm" styleId="<%=lbID%>" value="LB"/> 

<html:select property="<%=labVal%>"   styleId="<%=labIdn%>" name="labSelectionForm">
          <html:optionsCollection property="labList" name="labSelectionForm" 
           label="labDesc" value="labVal"  />
    </html:select>


</td>
    <td> <a href="<%=url%>"  id="LNK_<%=stkIdn%>" onclick="loadASSFNL('<%=target%>','LNK_<%=stkIdn%>')"  target="SC">Edit</a></td>
 
</tr>
   <%}%>
   </table>
   <input type="hidden" name="count" value="<%=sr%>" id="count" />
   </td></tr>
   
   
  
   <%}else{%>
  <tr>
   <td>Sorry Result not found </td></tr>
   <%}}%>
   
  
  </table>
 
  </td></tr>
  </html:form>
   <tr>
    <td><jsp:include page="/footer.jsp" /> </td>
    </tr>
  </table>
  </body>
</html>