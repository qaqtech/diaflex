<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page errorPage="../error_page.jsp" %>
<%@ page contentType="text/html;charset=windows-1252"%>
<jsp:useBean id="info" class="ft.com.InfoMgr" scope="session" />
<jsp:useBean id="gtMgr" class="ft.com.GtMgr" scope="session" />

<jsp:useBean id="util" class="ft.com.JspUtil" scope="page" />
<%@ page import="java.util.ArrayList,java.util.LinkedHashMap,java.util.Set,java.util.HashMap,java.sql.ResultSet,ft.com.*, java.util.Date, java.util.ArrayList, ft.com.dao.*"%>
<%@taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>

<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>Assort Final Return</title>
        <script language="javaScript" type="text/javascript" src="<%=info.getReqUrl()%>/scripts/ajax.js?v=<%=info.getJsVersion()%>"></script>
    <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/bse.css?v=<%=info.getJsVersion()%>"/>
    <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/general.css?v=<%=info.getJsVersion()%>"/>
     <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/style.css?v=<%=info.getJsVersion()%>"/>
     <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/bse.js?v=<%=info.getJsVersion()%> " > </script>
      <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/assortScript.js?v=<%=info.getJsVersion()%> " > </script>
     <script type="text/javascript" src="<%=info.getReqUrl()%>/PopScripts/popup.js?v=<%=info.getJsVersion()%> " > </script>
   <script src="<%=info.getReqUrl()%>/PopScripts/jquery-1.2.6.min.js" type="text/javascript"></script>
            <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/newCalScript.js?v=<%=info.getJsVersion()%> " > </script>
            <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/ajax.js?v=<%=info.getJsVersion()%> " > </script>
  </head>
        <%String logId=String.valueOf(info.getLogId());
        String onfocus="cook('"+logId+"')";
        String lstNme = (String)request.getAttribute("lstNme");
        String allow_valid_prc=util.nvl((String)info.getDmbsInfoLst().get("ALLOW_VALID_PRC"),"N");
  
        %>
 <body onfocus="<%=onfocus%>" onload="MM_preloadImages('images/faunalogo.png')" topmargin="0" leftmargin="0" rightmargin="0" bottommargin="0">
<input type="hidden" id="reqUrl" name="reqUrl" value="<%=info.getReqUrl()%>"/>
<div id="backgroundPopup"></div>
<div id="popupContactASSFNL">
<table class="popUpTb">
 <tr><td height="5%">&nbsp;&nbsp;&nbsp;<html:button property="value(close)" styleClass="submit"  onclick="MKEmptyPopup('SC');disablePopupASSFNL();uncheckbox();selectRtnPrppktFinalAsrt();" value="Close"  /> </td></tr>
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
  <img src="../images/line.jpg" border="0" /></td><td valign="middle"> <span class="pgHed">Final Assort Return</span> </td>
</tr></table>
  </td>
  </tr>
    <%
  String  lseq =(String)request.getAttribute("seqNo");
  if(lseq!=null){%>
    <tr><td valign="top" class="tdLayout"> <span class="redLabel"><%=lseq%></span> </td></tr>
    <%}%>
  <%
  //util.updAccessLog(request,response,"Assort Final Return", "Assort Final Return");
   String vnmNtFnd = (String)request.getAttribute("vnmNotFnd");
     if(vnmNtFnd!=null){%>
    <tr><td valign="top" class="tdLayout"><span class="redLabel"><%=vnmNtFnd%></span></td></tr>
   <%}%>
   <tr>
   
   <td valign="top" class="tdLayout">
   <table><tr><td>
   <html:form action="assort/assortFinalRtn.do?method=fecth" method="post" onsubmit="return validate_Assortreturn()">
      <html:hidden property="value(allow_valid_prc)" styleId="allow_valid_prc" name="assortFinalRtnForm" value="<%=allow_valid_prc%>" />
   <table><tr><td>Process : </td>
   <td>
   <html:select property="value(mprcIdn)"  styleId="mprcIdn" name="assortFinalRtnForm"  onchange="getvalidPrcEmp('mprcIdn')"  >
           <html:option value="0" >--select--</html:option>
            <html:optionsCollection property="mprcList" name="assortFinalRtnForm" 
            label="prc" value="mprcId" />
    </html:select>
   </td></tr>
   <tr>
    <td>
    Employee : </td>
   <td>
   <html:select property="value(empIdn)" styleId="empIdn" name="assortFinalRtnForm"   >
           <html:option value="0" >--select--</html:option>
            <html:optionsCollection property="empList" name="assortFinalRtnForm" 
            label="emp_nme" value="emp_idn" />
            </html:select>
   </td>
   </tr>
   <tr>
   <td>Issue Id:</td><td> <html:text property="value(issueId)" styleId="issueId"  onchange="return CheckDecimal('issueId');"  name="assortFinalRtnForm"  /></td>
   </tr>
   <tr>
   <td>Packet No:</td><td> <html:textarea property="value(vnmLst)" cols="30" name="assortFinalRtnForm"  /></td>
   </tr>
    
   <tr>
   <td colspan="2">  <html:submit property="value(srch)" value="Fetch" styleClass="submit" /></td>
   </tr></table>
   </html:form>
   </td></tr>
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
   HashMap dbInfoSys = info.getDmbsInfoLst();
  String cnt = (String)dbInfoSys.get("CNT");
   ArrayList prpDspBlocked = info.getPageblockList();
   HashMap stockListMap = (HashMap)gtMgr.getValue(lstNme);
   if(stockListMap!=null && stockListMap.size()>0){
    ArrayList vwPrpLst = (ArrayList)session.getAttribute("asViewLst");
       HashMap totals= (request.getAttribute("totalMap") == null)?new HashMap():(HashMap)request.getAttribute("totalMap");
       ArrayList options = (ArrayList)request.getAttribute("OPTIONS");
       String prcId = util.nvl((String)request.getAttribute("prcId"));
       String openPopUp = "openPopUp('assortFinalRtn.do?method=Openpop&prcID="+prcId+"&lstNme="+lstNme+"' ,'SC','multiprp')";
       if(options == null)
        options = new ArrayList();
    HashMap mprp = info.getMprp();
    int sr = 0;
    HashMap allPageDtl= (info.getPageDetails() == null)?new HashMap():(HashMap)info.getPageDetails();
    HashMap pageDtl=(HashMap)allPageDtl.get("ASSORT_FINAL_RT");
    ArrayList pageList=new ArrayList();
    HashMap pageDtlMap=new HashMap();
    String fld_nme="",fld_ttl="",val_cond="",dflt_val="",fld_typ="",form_nme="",flg1="";
   %>
  
 <tr><td>
   <table>
   <tr><td>Total :&nbsp;&nbsp;</td><td><span id="ttlqty"><%=util.nvl((String)totals.get("qty"))%></span> &nbsp;&nbsp;</td><td>cts&nbsp;&nbsp;</td><td><span id="ttlcts"><%=util.nvl((String)totals.get("cts"))%></span> &nbsp;&nbsp;</td><td>Selected:&nbsp;&nbsp;</td><td> Total :&nbsp;&nbsp;</td> <td><span id="qtyTtl">0&nbsp;&nbsp;</span></td><td>Cts&nbsp;&nbsp;</td><td><span id="ctsTtl">0</span> </td> </tr>
  
   </table>
   </td></tr>
    <html:form action="assort/assortFinalRtn.do?method=Return" method="post" onsubmit="return validate_issue('CHK_' , 'count')" >
  <tr><td>
  <table><tr><td>
  <html:submit property="value(issue)"  onclick="this.form.target='_self';" styleClass="submit" value="Return" /> 
  
  </td>
  <logic:equal property="value(pri_yn)"  name="assortFinalRtnForm"  value="Y" >
  <td><html:submit property="value(reprc)" onclick="this.form.target='_self';" styleClass="submit" styleId="reprc" value="RePrice" /> </td>
  </logic:equal>
 <%if(cnt.equals("sg")){%>
 <td><input type="submit" class="submit" onclick="this.form.target='_blank';" value="Premarketing report" name="premktrpt" id="premktrpt"></input></td>
 <%}%>
  <td> <html:button property="value(multi)" styleClass="submit" styleId="multiprp" onclick="<%=openPopUp%>"   value="Multiple Packet Update" /></td>
  <%
  ArrayList rolenmLst=(ArrayList)info.getRoleLst();
  String usrFlg=util.nvl((String)info.getUsrFlg());
  if(usrFlg.equals("SYS") || rolenmLst.contains("ADMIN")){
  %>
  <td><img src="../images/edit.jpg" title="Edit Properties Module" onclick="newWindow('<%=info.getReqUrl()%>/dynamicProperty.do?method=load&mdl=AS_VIEW&sname=asViewLst&par=A')" border="0" width="15px" height="15px"/> </td>
  <%}%>
  </tr></table>
  </td></tr>
  
   <tr><td>
        <html:hidden property="value(lstNme)" name="assortFinalRtnForm" styleId="lstNme"  />
   <table class="grid1" id="dataTable">
   <tr><th><input type="checkbox" name="checkAll" id="checkAll" onclick="ALLCheckBoxCalLst('checkAll','CHK_')" />  </th><th>Sr</th>
   
      <%pageList= ((ArrayList)pageDtl.get("HEADER") == null)?new ArrayList():(ArrayList)pageDtl.get("HEADER");
      if(pageList!=null && pageList.size() >0){
      for(int j=0;j<pageList.size();j++){
      pageDtlMap=(HashMap)pageList.get(j);
      fld_ttl=(String)pageDtlMap.get("fld_ttl");%>
      <th><%=fld_ttl%></th> 
      <%}}%>
     <th>Issue Id</th>
     <th>Packet No.</th>
      <th>Employee</th>
    <%
    ArrayList itemHdr = new ArrayList();
HashMap hdrDtl = new HashMap();
  hdrDtl.put("hdr", "issIdn");
  hdrDtl.put("typ", "N");
  itemHdr.add(hdrDtl);
  hdrDtl = new HashMap();
  hdrDtl.put("hdr", "vnm");
  hdrDtl.put("typ", "N");
  itemHdr.add(hdrDtl);
  hdrDtl = new HashMap();
  hdrDtl.put("hdr", "emp");
  hdrDtl.put("typ", "N");
  itemHdr.add(hdrDtl);
    for(int j=0; j < vwPrpLst.size(); j++ ){
    String prp = (String)vwPrpLst.get(j);
     if(prpDspBlocked.contains(prp)){
       }else{
     String hdr = util.nvl((String)mprp.get(prp));
    String prpDsc = util.nvl((String)mprp.get(prp+"D"));
    if(hdr == null) {
        hdr = prp;
       } 
       
             hdrDtl = new HashMap();
  hdrDtl.put("hdr", prp);
  hdrDtl.put("typ", "P");
    hdrDtl.put("dp", "D");

  itemHdr.add(hdrDtl);
       %>
    <th title="<%=prpDsc%>"><%=hdr%></th>
<%}}%> 
<th>
<%if(options.size()>0){ %>
<div class="float">Status</div> <div class="float">
<%
for(int j=0; j < options.size(); j++) {


String lopt = (String)options.get(j);

%>
<div class="float"><input type="radio" name="STT" id="<%=lopt%>" onclick="ALLRedio('<%=lopt%>','<%=lopt%>_')" />&nbsp;<%=lopt%></div>
<%}%>
</div>
<%}%></th>
  <%session.setAttribute("itemHdr", itemHdr);%>
<!--<th> Edit</th>-->
</tr>
 <%
  int lastpage=stockListMap.size()-1;
 int colSpan = vwPrpLst.size()+4;
 ArrayList stockIdnLst =new ArrayList();
   LinkedHashMap stockList = new LinkedHashMap(stockListMap); 
   Set<String> keys = stockList.keySet();
        for(String key: keys){
       stockIdnLst.add(key);
        }
        
 gtMgr.setValue(lstNme+"_ALLLST", stockIdnLst);

 for(int i=0; i < stockIdnLst.size(); i++ ){
 String stkIdn = (String)stockIdnLst.get(i);
 GtPktDtl stockPkt = (GtPktDtl)stockList.get(stkIdn);
 String cts = (String)stockPkt.getCts();
 String issIdn = (String)stockPkt.getValue("issIdn");
 String rtnprpupdated = util.nvl((String)stockPkt.getValue("rtnprpupdated"),"N");
 String stt = (String)stockPkt.getStt();
  String target = "TR_"+stkIdn;
 sr = i+1;
 String url="assortFinalRtn.do?method=stockUpd&mstkIdn="+stkIdn+"&issIdn="+issIdn ;
 String onclick = "CalTtlOnChlickLst("+stkIdn+" , this , 'NO' )";
 String checkFldId = "CHK_"+sr;
 String checkFldVal = "value("+stkIdn+")";
 String chgVnmColor="";
if(rtnprpupdated.equals("Y")){
chgVnmColor="#FFC977";
}
 %>
<tr id="<%=target%>">
<td><html:checkbox property="<%=checkFldVal%>" styleId="<%=checkFldId%>" name="assortFinalRtnForm" onclick="<%=onclick%>"  value="yes" /> </td>
<td><%=sr%><input type="hidden" id="CTS_<%=stkIdn%>"  value="<%=cts%>" /></td>
<%
      pageList= ((ArrayList)pageDtl.get("BODY") == null)?new ArrayList():(ArrayList)pageDtl.get("BODY");
      if(pageList!=null && pageList.size() >0){
      for(int j=0;j<pageList.size();j++){
      pageDtlMap=(HashMap)pageList.get(j);
      fld_ttl=(String)pageDtlMap.get("fld_ttl");
      dflt_val=(String)pageDtlMap.get("dflt_val");
      dflt_val = dflt_val.replaceAll("STKIDN", stkIdn);
      dflt_val = dflt_val.replaceAll("ISSIDN", issIdn);
      dflt_val=dflt_val+"&currentpage="+i+"&lastpage="+lastpage+"&stt="+stt;
      %>
      <td><a href="<%=dflt_val%>" id="LNK_<%=stkIdn%>" onclick="loadASSFNL('<%=target%>','LNK_<%=stkIdn%>')"  target="SC">Edit</a></td> 
      <%}}%>
<td><%=stockPkt.getValue("issIdn")%></td>
<td bgcolor="<%=chgVnmColor%>" id="TD_<%=stkIdn%>"><%=stockPkt.getVnm()%></td>
<td><%=stockPkt.getValue("emp")%></td>
<%for(int j=0; j < vwPrpLst.size(); j++ ){
    String prp = (String)vwPrpLst.get(j);
    if(prpDspBlocked.contains(prp)){
       }else{
    %>
    <td><%=stockPkt.getValue(prp)%></td>
<%}}%>
<td><table><tr><%
for(int j=0; j < options.size(); j++) {%>

<%
String lopt = (String)options.get(j);
String rfld = "value(STT_"+ stkIdn + ")";
String fldId=lopt+"_"+stkIdn;
%>
<td><html:radio property="<%=rfld%>" styleId="<%=fldId%>" value="<%=lopt%>"/>&nbsp;<%=lopt%></td>
<%}
%>
</tr></table>

</td>
<!-- <td> 
 <a href="assortFinalRtn.do?method=stockUpd&mstkIdn=<%=stkIdn%>&issIdn=<%=issIdn%>" id="LNK_<%=stkIdn%>" onclick="loadASSFNL('<%=target%>','LNK_<%=stkIdn%>')"  target="SC">Edit</a></td>-->
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