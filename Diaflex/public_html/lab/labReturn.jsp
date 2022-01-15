<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page errorPage="../error_page.jsp" %>
<%@ page contentType="text/html;charset=windows-1252"%>
<jsp:useBean id="info" class="ft.com.InfoMgr" scope="session" />
<jsp:useBean id="util" class="ft.com.JspUtil" scope="page" />
<jsp:useBean id="gtMgr" class="ft.com.GtMgr" scope="session" />

<%@ page import="java.util.ArrayList,java.util.LinkedHashMap, ft.com.dao.GtPktDtl,java.util.Set,java.util.HashMap,java.sql.ResultSet,ft.com.*, java.util.Date, java.util.ArrayList, ft.com.dao.*"%>
<%@taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>Lab Return</title>
    <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/bse.css?v=<%=info.getJsVersion()%>"/>
    <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/general.css?v=<%=info.getJsVersion()%>"/>
     <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/style.css?v=<%=info.getJsVersion()%>"/>
     <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/bse.js?v=<%=info.getJsVersion()%> " > </script>
      <script type="text/javascript" src="<%=info.getReqUrl()%>/PopScripts/popup.js?v=<%=info.getJsVersion()%> " > </script>
   <script src="<%=info.getReqUrl()%>/PopScripts/jquery-1.2.6.min.js" type="text/javascript"></script>
     <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/labScripts.js?v=<%=info.getJsVersion()%> " > </script>

  </head>
        <%String logId=String.valueOf(info.getLogId());
        String onfocus="cook('"+logId+"')";
        String lstNme = (String)gtMgr.getValue("lstNmeLABRTN");

        %>
 <body onfocus="<%=onfocus%>" onload="MM_preloadImages('images/faunalogo.png')" topmargin="0" leftmargin="0" rightmargin="0" bottommargin="0">
 <input type="hidden" id="reqUrl" name="reqUrl" value="<%=info.getReqUrl()%>"/>

<div id="backgroundPopup"></div>
<div id="popupContactASSFNL">
<table class="popUpTb">
 <tr><td height="5%">&nbsp;&nbsp;&nbsp;<html:button property="value(close)" styleClass="submit"  onclick="MKEmptyPopup('SC');disablePopupASSFNL()" value="Close"  /> </td></tr>
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
  <img src="../images/line.jpg" border="0" /></td><td valign="middle"> <span class="pgHed">Lab Return</span> </td>
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
   <table><tr><td>
   <html:form action="lab/labReturn.do?method=fecth" method="post" >
   <table><tr><td>Lab  </td>
   <td>
   <html:select property="value(lab)"  styleId="lab" name="labRtnForm"   >
           <html:option value="0" >--select--</html:option>
            <html:optionsCollection property="labList" name="labRtnForm" 
            label="labDesc" value="labVal" />
    </html:select>
   </td></tr>
   <tr><td>Packets </td><td><html:textarea property="value(vnmLst)" cols="30" name="labRtnForm"  /></td></tr>
     <tr>
   <td colspan="2"><html:submit property="value(srch)" value="Fetch" styleClass="submit" /></td>
   </tr>
   </table></html:form>
   
   </td></tr></table>
  </td></tr>
  
    <%
   String view = (String)request.getAttribute("view");
   if(view!=null){
  
  HashMap stockListMap = (HashMap)gtMgr.getValue(lstNme);
   if(stockListMap!=null && stockListMap.size()>0){
    int sr=0;
    ArrayList prpDspBlocked = info.getPageblockList();
    ArrayList vwPrpLst = (ArrayList)session.getAttribute("LabViewLst");
      HashMap totals = (HashMap)gtMgr.getValue(lstNme+"_TTL");
      HashMap mprp = info.getMprp();
      String prcIdn = (String)request.getAttribute("prcIdn");
      %>
     <tr><td valign="top" class="tdLayout">
   
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

   
     <html:form action="lab/labReturn.do?method=Return" method="post" onsubmit="return validate_issue('CHK_' , 'count')" >
        <html:hidden property="value(lstNme)" styleId="lstNme"  value="<%=lstNme%>"  name="labRtnForm" />

  <tr><td valign="top" class="tdLayout"><table><tr><td>
  <html:submit property="value(issue)" styleClass="submit" value="Return" /> </td>
  <%
  ArrayList rolenmLst=(ArrayList)info.getRoleLst();
  String usrFlg=util.nvl((String)info.getUsrFlg());
  if(usrFlg.equals("SYS") || rolenmLst.contains("ADMIN")){
  %>
  <td><img src="../images/edit.jpg" title="Edit Properties Module" onclick="newWindow('<%=info.getReqUrl()%>/dynamicProperty.do?method=load&mdl=LAB_VIEW&sname=LabViewLst&par=A')" border="0" width="15px" height="15px"/> </td>
  <%}%>
  </tr>
  </table></td></tr>
   <tr><td valign="top" class="tdLayout">
  
   <table class="grid1" id="dataTable">
   <tr> <th><input type="checkbox" name="checkAll" id="checkAll" onclick="GenSel(this,'ALL')" /></th><th>Sr</th>

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
<th> Edit</th>
</tr>
 <%
ArrayList stockIdnLst =new ArrayList();
   LinkedHashMap stockList = new LinkedHashMap(stockListMap); 
 Set<String> keys = stockList.keySet();
        for(String key: keys){
       stockIdnLst.add(key);
        }
 gtMgr.setValue(lstNme+"_ALLLST", stockIdnLst);

 int colSpan = vwPrpLst.size()+4;
 int lastpage=stockIdnLst.size()-1;
 for(int i=0; i < stockIdnLst.size(); i++ ){
  String stkIdn = (String)stockIdnLst.get(i);
  GtPktDtl stockPkt = (GtPktDtl)stockList.get(stkIdn);
  String cts = (String)stockPkt.getValue("cts");
  String issIdn = (String)stockPkt.getValue("issIdn");
  String target = "TR_"+stkIdn;
  sr = i+1;

String onclick = "GenSel(this,'"+stkIdn+"')";
 String checkFldId = "CHK_"+sr;
 String checkFldVal = "value("+stkIdn+")";
 String url =info.getReqUrl()+"/lab/labReturn.do?method=stockUpd&currentpage="+i+"&lastpage="+lastpage+"&mstkIdn="+stkIdn+"&prcId="+prcIdn;
 %>
<tr id="<%=target%>">
<td><html:checkbox property="<%=checkFldVal%>" styleId="<%=checkFldId%>" name="labRtnForm" onclick="<%=onclick%>" value="yes" /> 
</td>
<td><%=sr%><input type="hidden" id="CTS_<%=stkIdn%>"  value="<%=cts%>" /></td>

<td><%=stockPkt.getValue("vnm")%></td>
<%for(int j=0; j < vwPrpLst.size(); j++ ){
    String prp = (String)vwPrpLst.get(j);
    if(prpDspBlocked.contains(prp)){
    }else{
    %>
    <td><%=stockPkt.getValue(prp)%></td>
<%}}%>
<td> <a href="<%=url%>" id="LNK_<%=stkIdn%>" onclick="loadASSFNL('<%=target%>','LNK_<%=stkIdn%>')"  target="SC">Edit</a></td>
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