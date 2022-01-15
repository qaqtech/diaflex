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
    <title>Lab Recheck</title>
    <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/bse.css?v=<%=info.getJsVersion()%>"/>
    <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/general.css?v=<%=info.getJsVersion()%>"/>
     <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/style.css?v=<%=info.getJsVersion()%>"/>
<script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/bse.js?v=<%=info.getJsVersion()%> " > </script>
        <script src="../scripts/ajax.js" type="text/javascript"></script>
            <script src="<%=info.getReqUrl()%>/scripts/assortScript.js?v=<%=info.getJsVersion()%>" type="text/javascript"></script>
                 <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/labScripts.js?v=<%=info.getJsVersion()%> " > </script>

  </head>
        <%String logId=String.valueOf(info.getLogId());
        String onfocus="cook('"+logId+"')";
        %>
 <body onfocus="<%=onfocus%>" onload="MM_preloadImages('images/faunalogo.png')" topmargin="0" leftmargin="0" rightmargin="0" bottommargin="0">
<%
String method = util.nvl(request.getParameter("method"));
ArrayList stockList = (ArrayList)session.getAttribute("StockList");
HashMap prpLst = info.getPrp();
boolean disabled= false;
if(method.equals("fecth")){
if(stockList!=null && stockList.size()>0)
disabled= true;
}
 String lstNme = (String)gtMgr.getValue("lstNmeRCK");
  gtMgr.setValue(lstNme+"_SEL",new ArrayList());
   
%>
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
  <img src="../images/line.jpg" border="0" /></td><td valign="middle"> <span class="pgHed">Lab Recheck</span> </td>
</tr></table>
  </td>
  </tr>
    <%String msg = (String)request.getAttribute("msg");
    String url = (String)request.getAttribute("url");
    if(msg!=null){%>
    <tr><td valign="top" class="tdLayout"><span class="redLabel"><%=msg%></span></td></tr>
     <tr><td valign="top" class="tdLayout"><span class="redLabel"><A href="<%=url%>" target="_blank">Click here For Packing List</a></span></td></tr>
    <%}
   String vnmNtFnd = (String)request.getAttribute("vnmNotFnd");
     if(vnmNtFnd!=null){%>
    <tr><td valign="top" class="tdLayout"><span class="redLabel"><%=vnmNtFnd%></span></td></tr>
   <%}%>
   <tr>
   
   <td valign="top" class="tdLayout">
   <table><tr><td>
   <html:form action="lab/labReChk.do?method=fetch" method="post" onsubmit="return validate_reChk()">
  <table  class="grid1">
  <tr><th> </th><th>Generic Search 
  <%
  ArrayList rolenmLst=(ArrayList)info.getRoleLst();
  String usrFlg=util.nvl((String)info.getUsrFlg());
  if(usrFlg.equals("SYS") || rolenmLst.contains("ADMIN")){
  %>
  <img src="../images/edit.jpg" title="Edit Properties Module" onclick="newWindow('<%=info.getReqUrl()%>/dynamicProperty.do?method=load&mdl=LB_SRCH&sname=LBGNCSrch&par=GENERIC')" border="0" width="15px" height="15px"/> 
  <%}%>
  </th></tr>
  <tr><td valign="top">
   <table>
   <tr>
   <td>Packet No:</td><td> <html:textarea property="value(vnmLst)" rows="9"  cols="30" name="labrecheckForm"  /></td>
   </tr>
  </table></td>
   <td>
   
    <jsp:include page="/genericSrch.jsp"/>
   </td></tr>
   <tr><td colspan="2" align="center"><html:submit property="value(srch)" value="Fetch" styleClass="submit" /></td> </tr>
   </table>
   </html:form>
   </td></tr>
   
   <%
   String view = (String)request.getAttribute("view");
   if(view!=null){
HashMap stockListMap = (HashMap)gtMgr.getValue(lstNme);
if(stockListMap!=null && stockListMap.size()>0){
   ArrayList prpDspBlocked = info.getPageblockList();
   ArrayList issEditPrp = (ArrayList)gtMgr.getValue("LabIssueEditPRP");
    ArrayList vwPrpLst = (ArrayList)session.getAttribute("MixViewLst");
   HashMap totals = (HashMap)gtMgr.getValue(lstNme+"_TTL");
    HashMap mprp = info.getMprp();
    int sr = 0;
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
   <html:form action="lab/labReChk.do?method=Issue" method="post" onsubmit="return validate_issue('CHK_' , 'count')">
   <html:hidden property="value(lab)" name="labrecheckForm" />
           <html:hidden property="value(lstNme)" styleId="lstNme"  value="<%=lstNme%>"  name="labrecheckForm" />

   <tr><td><html:submit property="value(issue)" styleClass="submit" value="Issue" /> </td></tr>
   <tr><td>
   <table class="grid1" id="dataTable">
   <tr> <th><input type="checkbox" name="checkAll" id="checkAll" onclick="GenSel(this,'ALL')" /> </th><th>Sr</th>
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
<%for(int s=0; s < issEditPrp.size();s++){
    String lprp = (String)issEditPrp.get(s);
    ArrayList prpVal = (ArrayList)prpLst.get(lprp+"V");
    ArrayList prpPrt = (ArrayList)prpLst.get(lprp+"P");
    String prpDsc = (String)mprp.get(lprp+"D");
    String prpTyp = util.nvl((String)mprp.get(lprp+"T"));
    String prpFld = "value("+lprp+")";
    String chksrvAll = "chksrvAll('"+lprp+"')";
     String chksrvTxtAll = "chksrvTXTAll('"+lprp+"')";
    String selectALL = lprp;
    %>
    <th><%if(prpTyp.equals("C")){%>
     <html:select  property="<%=prpFld%>" styleId="<%=selectALL%>" name="labrecheckForm" onchange="<%=chksrvAll%>"  style="width:100px"   > 
      <html:option value="0">--select--</html:option>
            <%
            for(int k=0; k < prpVal.size(); k++) {
                String pPrt = (String)prpVal.get(k);
             %>
              <html:option value="<%=pPrt%>" ><%=prpPrt.get(k)%></html:option> 
            <%}%>
     </html:select>  
     <%}else{%>
     <html:text property="<%=prpFld%>" styleId="<%=selectALL%>" name="labrecheckForm"  style="width:100px" onchange="<%=chksrvTxtAll%>" />
     <%}%>
     <%=prpDsc%>
     </th>
    <%}%>
<th>View</th>
</tr>
 <%
 String target=""; 
 ArrayList stockIdnLst =new ArrayList();
 Set<String> keys = stockListMap.keySet();
        for(String key: keys){
       stockIdnLst.add(key);
        }
for(int i=0; i < stockIdnLst.size(); i++ ){

String stkIdn = (String)stockIdnLst.get(i);
GtPktDtl stockPkt = (GtPktDtl)stockListMap.get(stkIdn);
 String cts = (String)stockPkt.getValue("CRTWT");
String onclick = "GenSel(this,'"+stkIdn+"')";
 String stt = (String)stockPkt.getValue("stt");
 sr = i+1;
 String checkFldId = "CHK_"+sr;
 String checkFldVal = "value("+stkIdn+")";
 target = "TR_"+stkIdn;
 String vnm = (String)stockPkt.getValue("vnm");
 %>
<tr id="<%=target%>">
<td><html:checkbox property="<%=checkFldVal%>" styleId="<%=checkFldId%>" name="labrecheckForm" onclick="<%=onclick%>" value="yes" /> </td>
<td><%=sr%></td>
<td><%=vnm%><input type="hidden" id="CTS_<%=stkIdn%>"  value="<%=cts%>" /></td>

<%for(int j=0; j < vwPrpLst.size(); j++ ){
    String prp = (String)vwPrpLst.get(j);
    if(prpDspBlocked.contains(prp)){
    }else{
    %>
    <td><%=stockPkt.getValue(prp)%></td>
<%}}%>
<%for(int s=0; s < issEditPrp.size();s++){
String lprp = (String)issEditPrp.get(s);
String prpTyp = util.nvl((String)mprp.get(lprp+"T"));
ArrayList prpVal = (ArrayList)prpLst.get(lprp+"V");
ArrayList prpDsc = (ArrayList)prpLst.get(lprp+"P");
String fldVal = "value("+lprp+"_"+stkIdn+")";
String fldId = lprp+"_"+stkIdn;
%>
<td>
<%if(prpTyp.equals("C")){%>
<html:select property="<%=fldVal%>" styleId="<%=fldId%>" name="labrecheckForm"  style="width:100px">
<html:option value="0" >--select--</html:option>
<%for(int p=0;p<prpVal.size();p++){
String val = (String)prpVal.get(p);
%>
<html:option value="<%=val%>" ><%=prpDsc.get(p)%></html:option>
<%}%></html:select>
<%}else{%>
<html:text property="<%=fldVal%>" styleId="<%=fldId%>" style="width:100px" name="labrecheckForm"  />
<%}%>
</td>
<%}%>
<td><a href="labReChk.do?method=viewRS&mstkIdn=<%=stkIdn%>&stt=<%=stt%>&vnm=<%=vnm%>" id="LNK_<%=stkIdn%>" onclick="loadASSFNL('<%=target%>','LNK_<%=stkIdn%>')"  target="SC" >View</a></td>

</tr>
   <%}%>
   </table></td></tr>
   <input type="hidden" name="count" id="count" value="<%=sr%>" />
   </html:form>
   <%}
   else{%>
   <tr><td>Sorry no result found </td></tr>
   <%}
   }%>
   
   </table>
   
  
  
   <tr>
    <td><jsp:include page="/footer.jsp" /> </td>
    </tr>
  </table>

  </body>
</html>