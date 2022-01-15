<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page errorPage="../error_page.jsp" %>
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import="java.util.ArrayList,java.util.LinkedHashMap,java.util.Set,ft.com.dao.*,java.util.ArrayList,java.util.HashMap,java.sql.ResultSet,ft.com.*, java.util.Date, java.net.URLEncoder"%>
<jsp:useBean id="util" class="ft.com.JspUtil" scope="page" />
<jsp:useBean id="info" class="ft.com.InfoMgr" scope="session" />
<jsp:useBean id="gtMgr" class="ft.com.GtMgr" scope="session" />
<%@taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
<link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/bse.css?v=<%=info.getJsVersion()%>"/>
<link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/general.css?v=<%=info.getJsVersion()%>"/>
      <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/style.css?v=<%=info.getJsVersion()%>"/>
  <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/newCalScript.js?v=<%=info.getJsVersion()%>" ></script>
    <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/bse.js?v=<%=info.getJsVersion()%>" ></script>
    <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/ajax.js?v=<%=info.getJsVersion()%>" ></script>
    <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/labScripts.js?v=<%=info.getJsVersion()%>" ></script>

    <title>Mix Transfer</title>
    
</head>
  <%String logId=String.valueOf(info.getLogId());
    String onfocus="cook('"+logId+"')";
     String lstNme =(String)gtMgr.getValue("lstNmeMIXTRN");
    %>
 <body onfocus="<%=onfocus%>" onload="MM_preloadImages('images/faunalogo.png')" topmargin="0" leftmargin="0" rightmargin="0" bottommargin="0">
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
  <img src="../images/line.jpg" border="0" /></td><td valign="middle"> <span class="pgHed">Mix Transfer</span> </td>
</tr></table>
  </td>
  </tr>
   <%
   String msg = (String)request.getAttribute("msg");
     if(msg!=null){%>
    <tr><td valign="top" class="tdLayout"><span class="redLabel"><%=msg%></span></td></tr>
   <%}%>
 <tr><td valign="top" class="tdLayout">
<html:form action="/mixAkt/mixTransferAction.do?method=fetch" method="post" >
<table class="grid1">
 <tr><th>Criteria Search</th><th>Packet No.</th></tr>
 <tr><td colspan="2">Transfer Type :
 &nbsp;
 <html:select property="value(typ)" name="mixTransferKapuForm" >
 <html:option value="SMX">Single To Mix (90 UP)</html:option> <html:option value="MIX">Single To MixMarge (90 Down)</html:option>

 </html:select>
 </td></tr>
   <tr>
  <td valign="top" height="10px" class="tdLayout">
    <jsp:include page="/genericSrch.jsp"/>
    </td>
   <td> <html:textarea property="value(vnmLst)" rows="7"  cols="30" name="mixTransferKapuForm"  /></td>
   </tr>
   <tr><td colspan="2" align="center"><html:submit property="value(srch)" value="Fetch" styleClass="submit" /></td> </tr>
    </table>
    </html:form>
</td></tr>

 
  <%
   String view = (String)request.getAttribute("view");
   String typ = (String)request.getAttribute("typ");
   if(view!=null){
   HashMap stockListMap = (HashMap)gtMgr.getValue(lstNme);
   HashMap totals  = (HashMap)gtMgr.getValue(lstNme+"_TTL");
   if(stockListMap!=null && stockListMap.size()>0){
    ArrayList vwPrpLst = (ArrayList)session.getAttribute("MIX_VW");
    HashMap mprp = info.getMprp();
    int sr = 0;
   %>
 <tr><td valign="top" class="tdLayout">
<table>
<tr><td>Total :&nbsp;&nbsp;</td><td><span id="ttlqty"> <%=totals.get("ZQ")%>&nbsp;&nbsp;</span></td><td>cts&nbsp;&nbsp;</td><td><span id="ttlcts"><%=totals.get("ZW")%>&nbsp;&nbsp;</span></td>
<td>Avg&nbsp;&nbsp;</td><td><span id="ttlavg"><%=totals.get("ZA")%>&nbsp;&nbsp;</span></td>
<td>Vlu&nbsp;&nbsp;</td><td><span id="ttlvlu"><%=totals.get("ZV")%>&nbsp;&nbsp;</span></td>

<td>Selected:&nbsp;&nbsp;</td><td> Total :&nbsp;&nbsp;</td> 
<td><span id="qtyTtl">0&nbsp;&nbsp;</span></td>
<td>Cts&nbsp;&nbsp;</td><td><span id="ctsTtl">0</span> </td>
<td>AvgRte&nbsp;&nbsp;</td><td><span id="avgTtl">0</span> </td>
<td>Vlu&nbsp;&nbsp;</td><td><span id="vluTtl">0</span> </td>

</tr>
</table>
</td></tr>
   <html:form action="mixAkt/mixTransferAction.do?method=save" method="post" onsubmit="return validate_issue('CHK_' , 'count')">
        <html:hidden property="value(lstNme)" name="mixTransferKapuForm" value="<%=lstNme%>" />
  
   <tr><td valign="top" class="tdLayout">
   <table><tr><td>
   <html:submit property="value(save)" styleClass="submit" value="Save Changes" /></td> 
  
   <%if(typ.equals("MIX")){%>
    <td>Box Type:</td><td>
   <html:select property="value(box_typ)" styleId="boxTyp" name="mixTransferKapuForm" onchange="SetBOXID(this,'boxId')" style="width:100px">
      <html:option value="0">---select---</html:option>
      <html:optionsCollection property="boxList" name="mixTransferKapuForm" 
            label="boxDesc" value="boxVal" />
  </html:select></td>
  <td>Box Id:</td><td>
  <html:select property="value(box_id)" styleId="boxId" name="mixTransferKapuForm" style="width:100px">
    <html:option value="0">---select---</html:option>
  </html:select></td>
  <td>Carat:</td>
  <td> <html:text property="value(fnlCts)" name="mixTransferKapuForm" style="width:90px" /> </td>
   <%}%>
   <%
  ArrayList rolenmLst=(ArrayList)info.getRoleLst();
  String usrFlg=util.nvl((String)info.getUsrFlg());
  if(usrFlg.equals("SYS") || rolenmLst.contains("ADMIN")){
  %>
  <td><img src="../images/edit.jpg" title="Edit Properties Module" onclick="newWindow('<%=info.getReqUrl()%>/dynamicProperty.do?method=load&mdl=AS_VIEW&sname=asViewLst&par=A')" border="0" width="15px" height="15px"/> </td>
  <%}%>
  </tr></table>
  </td>
   </tr>
  <tr><td valign="top" class="tdLayout">
   <table class="grid1">
   <tr> <th><input type="checkbox" name="checkAll" id="checkAll" onclick="GenSel(this,'ALL')" /> </th><th>Sr</th>
        <th>Packet No.</th>
       <th>Qty</th>
       <th>Cts</th>
       <th>Rte</th>
        <th>Amt</th>
    <%for(int j=0; j < vwPrpLst.size(); j++ ){
    String prp = (String)vwPrpLst.get(j);
    
     String hdr = util.nvl((String)mprp.get(prp));
    String prpDsc = util.nvl((String)mprp.get(prp+"D"));
    if(hdr == null) {
        hdr = prp;
       }  
%>
<th title="<%=prpDsc%>"><%=prp%></th>
<%}%> 
<%if(!typ.equals("MIX")){%>
<th>Box Type 
<html:select property="value(box_typ)" styleId="box_typ" onchange="chksrvAll('box_typ');SetBOXID(this,'box_id')" name="mixTransferKapuForm" style="width:100px">
      <html:optionsCollection property="boxList" name="mixTransferKapuForm" 
            label="boxDesc" value="boxVal" />
  </html:select>

</th>
<th>Box ID
<html:select property="value(box_id)" styleId="box_id"  onchange="chksrvAll('box_id')" name="mixTransferKapuForm" style="width:100px">
    <html:option value="0">---select---</html:option>
  </html:select>
</th>
<%}%>
      
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
 String cts = (String)stockPkt.getValue("cts");
String onclick = "GenSel(this,'"+stkIdn+"')";
 sr = i+1;
 String checkFldId = "CHK_"+sr;
 String checkFldVal = "cb_stk_"+stkIdn ;
  String ctsTxtId = "cb_cts_"+stkIdn ;
   String ctsTxtVal = "value(cb_cts_"+stkIdn+")" ;
 %>
<tr>
<td><input type="checkbox" name="<%=checkFldVal%>" id="<%=checkFldId%>" value="<%=stkIdn%>" onclick="<%=onclick%>" /></td>
<td><%=sr%></td>
<td><%=stockPkt.getValue("vnm")%><input type="hidden" id="CTS_<%=stkIdn%>"  value="<%=cts%>" /></td>
<td><%=stockPkt.getValue("qty")%></td><td><%=cts%>
&nbsp;&nbsp;
<html:text property="<%=ctsTxtVal%>" styleId="<%=ctsTxtId%>" value="<%=cts%>" name="mixTransferKapuForm" size="10" />
</td><td><%=stockPkt.getValue("USDVAL")%></td><td><%=stockPkt.getValue("AMT")%></td>
<%for(int j=0; j < vwPrpLst.size(); j++ ){
    String prp = (String)vwPrpLst.get(j);
    
    %>
    <td><%=stockPkt.getValue(prp)%></td>
<%}%>
 <%if(!typ.equals("MIX")){
 String boxTypFld="box_typ_"+stkIdn;
 String boxNme = "value("+boxTypFld+")";
 
  String boxIdFld="box_id_"+stkIdn;
 String boxIdNme = "value("+boxIdFld+")";
 String onChange="SetBOXID(this,'"+boxIdFld+"')";
 %>
 <td>
   <html:select property="<%=boxNme%>" styleId="<%=boxTypFld%>"  name="mixTransferKapuForm" onchange="<%=onChange%>" style="width:100px">
      <html:optionsCollection property="boxList" name="mixTransferKapuForm" 
            label="boxDesc" value="boxVal" />
  </html:select></td>
  
   <td>
   <html:select property="<%=boxIdNme%>" styleId="<%=boxIdFld%>" name="mixTransferKapuForm" style="width:100px">
      <html:optionsCollection property="boxIDList" name="mixTransferKapuForm" 
            label="boxDesc" value="boxVal" />
  </html:select></td>
   <%}%>
</tr>
   <%}%>
   </table></td></tr>
   <input type="hidden" name="count" id="count" value="<%=sr%>" />
   </html:form>
   
   <%}
   else{%>
   <tr><td valign="top" class="tdLayout">Sorry no result found </td></tr>
   <%}
   }%>
 </td></tr>
    <tr>
     <td><jsp:include page="/footer.jsp" /> </td>
</tr>
    </table>
    
    </body>
</html>