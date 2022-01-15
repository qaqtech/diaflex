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

    <title>Assort Return</title>
    
</head>
        <%String logId=String.valueOf(info.getLogId());
        String onfocus="cook('"+logId+"')";
        HashMap prp = info.getPrp();
        String lstNme = util.nvl((String)gtMgr.getValue("lstNmeMIXRTN"));

        ArrayList boxTypList= ((ArrayList)gtMgr.getValue(lstNme+"BOXLST") == null)?new ArrayList():(ArrayList)gtMgr.getValue(lstNme+"BOXLST");

        HashMap mixDataMap = ((HashMap)gtMgr.getValue(lstNme) == null)?new HashMap():(HashMap)gtMgr.getValue(lstNme);

        HashMap stockMap = ((HashMap)gtMgr.getValue(lstNme+"_CNLST") == null)?new HashMap():(HashMap)gtMgr.getValue(lstNme+"_CNLST");

        HashMap allPageDtl= (info.getPageDetails() == null)?new HashMap():(HashMap)info.getPageDetails();
       HashMap pageDtl= ((HashMap)allPageDtl.get("MIX_ASSORTRTN") == null)?new HashMap():(HashMap)allPageDtl.get("MIX_ASSORTRTN");

      String clint =(String)info.getDmbsInfoLst().get("CNT");
      DBUtil dbutil = new DBUtil();
      DBMgr db = new DBMgr();
      db.setCon(info.getCon());
      dbutil.setDb(db);


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
  <img src="../images/line.jpg" border="0" /></td><td valign="middle"> <span class="pgHed">Assort Return</span> </td>
</tr></table>
  </td>
  </tr>
   <%
   String msg = (String)request.getAttribute("msg");
 
     if(msg!=null){%>
    <tr><td valign="top" class="tdLayout"><span class="redLabel"><%=msg%></span></td></tr>
   <%}%>

<html:form action="/mixAkt/assortReturn.do?method=fetch" method="post" onsubmit="return sumbitFormConfirm('CHK_','0','Do you want to save changes','Please select packet','checkbox')" >
 
   <tr>
  <td valign="top" height="20px" class="tdLayout">
  Employee Name:&nbsp;&nbsp;
 <html:select property="value(empIdn)"  styleId="empIdn" name="mixAssortReturnForm" >
            <html:optionsCollection property="empList" name="mixAssortReturnForm" 
            label="emp_nme" value="emp_idn" />
            </html:select>
                &nbsp;&nbsp;
    <html:submit property="value(Fetch)" value="Fetch" styleClass="submit"/>

  </td></tr>
  <tr>
  <td valign="top" class="tdLayout">
  

<%
ArrayList pageList= ((ArrayList)pageDtl.get("STT") == null)?new ArrayList():(ArrayList)pageDtl.get("STT");


if(boxTypList!=null && boxTypList.size()>0){
ArrayList sttLst=new ArrayList();
%>
<table class="grid1">
<tr><th><input type="checkbox" name="checkAll" id="checkAll" onclick="checkAllpage(this,'CHK_')" /></th><th>Box Typ</th>
<% for(int j=0;j<pageList.size();j++){
    HashMap pageDtlMap=(HashMap)pageList.get(j);
    String fld_ttl=(String)pageDtlMap.get("fld_ttl");
      String stt=(String)pageDtlMap.get("form_nme");
      sttLst.add(stt);
    %>
<th><%=fld_ttl%></th>
<%}%>
<th>Total</th></tr>
<%for(int i=0;i<boxTypList.size();i++){
 String boxTyp = (String)boxTypList.get(i);
  String fldId = "CHK_"+boxTyp;

  
 %>
<tr><td><input type="checkbox" name="<%=fldId%>" id="<%=fldId%>" value="<%=boxTyp%>" /> </td><td><%=boxTyp%></td> 
<% 
float ttlCts = 0;
int ttlQty = 0;
double avgRte=0;
for(int j=0;j<pageList.size();j++){
    HashMap pageDtlMap=(HashMap)pageList.get(j);
    String stt=(String)pageDtlMap.get("form_nme");
     String qty =  util.nvl((String)mixDataMap.get(boxTyp+"_"+stt+"_QTY"),"0");
     if(qty.equals(""))
     qty="0";
     String cts =  util.nvl((String)mixDataMap.get(boxTyp+"_"+stt+"_CTS"),"0");
       if(cts.equals(""))
        cts="0";
     String avg =  util.nvl((String)mixDataMap.get(boxTyp+"_"+stt+"_AVG"),"0");
      if(avg.equals(""))
        avg="0";
      avgRte =((Float.parseFloat(cts) * Double.parseDouble(avg)) + (ttlCts * avgRte))/(Float.parseFloat(cts)+ttlCts);
      ttlCts=ttlCts+Float.parseFloat(cts);
       ttlQty=ttlQty+Integer.parseInt(qty);
    %>
<td><A href="assortReturn.do?method=pktDtl&box_typ=<%=boxTyp%>&stt=<%=stt%>"> <%=qty%>| <%=cts%> | <%=avg%> </a></td>
<%}%>
<td><a href="assortReturn.do?method=pktDtl&box_typ=<%=boxTyp%>&stt=ALL"> <%=String.valueOf(ttlQty)%> | <%=String.valueOf(ttlCts)%> | <%=String.valueOf(avgRte)%></a></td>
</tr>
<%}%>
<tr></tr>
</table>
<%
gtMgr.setValue(lstNme+"STTLST", sttLst);
}%></td></tr>
</html:form>

  <%
  String view = (String)request.getAttribute("view");
  if(view!=null){
  String TTLCTS = util.nvl((String)request.getAttribute("TTLCTS"));
  String TTLQTY = util.nvl((String)request.getAttribute("TTLQTY"));
  String TrfIssueId = util.nvl((String)request.getAttribute("TrfIssueId"));
  if(!TTLCTS.equals("0.0") && !TTLCTS.equals("")){
  String TTLMKTCTS = util.nvl((String)request.getAttribute("TTLMKTCTS"));
   String TTLMKTQTY = util.nvl((String)request.getAttribute("TTLMKTQTY"));
  String AvgRte=util.nvl((String)request.getAttribute("AvgRte"));
    String WT_DIFF = util.nvl((String)request.getAttribute("WT_DIFF"),"0");
  if(TTLMKTCTS.equals(""))
  TTLMKTCTS="0";
  float rmnCrt =Float.parseFloat(TTLCTS)-Float.parseFloat(TTLMKTCTS);
  %>
      <html:form action="/mixAkt/assortReturn.do?method=Return" method="post" onsubmit="return verifedTtlVal()" >
<input type="hidden" name="WT_DIFF" id="WT_DIFF" value="<%=WT_DIFF%>" />
 <tr>
  <td valign="top" class="tdLayout" height="10px"></td></tr>
  <tr>
   <tr>
  <td valign="top" class="tdLayout">
  <html:submit property="value(return)" value="Return" styleClass="submit"/>
    &nbsp;&nbsp;<span class="redLabel"> Total QTY :</span><label id="TTLQTYMT" class="redLabel"> <%=TTLQTY%></label>
  &nbsp;&nbsp;<span class="redLabel"> Total Carat :</span><label id="TTLCTSMT" class="redLabel"> <%=TTLCTS%></label>
  &nbsp;&nbsp;<span class="redLabel">Old AVG RTE :</span><label id="oldAvgRte" class="redLabel"> <%=AvgRte%></label>
  &nbsp;&nbsp;<span class="redLabel">Varified Qty:</span> <label id="ttlqty" class="redLabel"><%=TTLMKTQTY%></label> 
   &nbsp;&nbsp;<span class="redLabel">Varified Carat:</span> <label id="ttlcts" class="redLabel"><%=TTLMKTCTS%></label>
  &nbsp;&nbsp;<span class="redLabel">Remaining Carat:</span> <label id="rmncts" class="redLabel"><%=rmnCrt%></label> 
  &nbsp;&nbsp;<span class="redLabel">New Avg Rte:</span> <label id="AVGRTE" class="redLabel">0</label> 
 
  </td></tr>
   <tr>
  <td valign="top" class="tdLayout">
  <input type="hidden" name="TrfIssueId" id="TrfIssueId" value="<%=TrfIssueId%>" />
   <table><tr>
   <%
   HashMap BOXTYPDTL = (HashMap)gtMgr.getValue("BOXTYPDTL");
   ArrayList BOXTYPRTNLST = (ArrayList)gtMgr.getValue("BOXTYPLST");
    ArrayList boxIdLst = (ArrayList)prp.get("BOX_IDV");
   ArrayList boxIdRteLst = (ArrayList)prp.get("BOX_IDN");
    ArrayList boxIdPrtLst = (ArrayList)prp.get("BOX_IDP");
  ArrayList  disBoxIdnLst = new ArrayList();
  String ctsId="",ctsVal="",qtyId="",qtyVal="",prcId="",prcVal="",rteVal="",diffId="",diffVal="",oldCts="",oldCtsVal="",diffQtyId="",diffQtyVal="",oldQty="",oldQtyVal="";  

   for(int j=0;j<BOXTYPRTNLST.size();j++){
   String box_typ = (String)BOXTYPRTNLST.get(j);
     String boxCts = (String)BOXTYPDTL.get(box_typ+"_CTS");
   %>
   <td valign="top">
  <table class="grid1">
   <tr>
   <th>Box ID/Box Type</th><th colspan="5"><%=box_typ%>( <%=boxCts%>) </th></tr>
     <tr> <th></th>
      <%if(clint.equalsIgnoreCase("AG")){%>
     <th>old Qty</th><%}%>
     <th>Qty</th>
      <%if(clint.equalsIgnoreCase("AG")){%>
     <th>Qty Diff</th>
     <%}%>
     <%if(clint.equalsIgnoreCase("AG")){%>
     <th>old Cts</th><%}%>
    <th>Cts</th>
     <%if(clint.equalsIgnoreCase("AG")){%>
     <th>Cts Diff</th>
     <%}%>
     <th>Rate</th>
   </tr>
   <tr><td></td><td></td> <td></td><td></td><td></td> <td> </td><td></td></tr>
    <tr><td>NA</td>
    <%
      ctsId = "CTS_"+box_typ+"_NA";
      ctsVal = "value("+ctsId+")";
      qtyId = "QTY_"+box_typ+"_NA";
      qtyVal = "value("+qtyId+")";
       prcId = "RTE_"+box_typ+"_NA";
       prcVal = "value("+prcId+")";
       rteVal = dbutil.getMixPriBOX(box_typ,"NA");
       diffId = "DIFF_"+box_typ+"_NA";
       diffVal = "value(DIFF_"+box_typ+"_NA)";
        oldCts="OLD_"+box_typ+"_NA";
       oldCtsVal="value("+oldCts+")";
       
       diffQtyId = "QTYDIFF_"+box_typ+"_NA";
       diffQtyVal = "value(QTYDIFF_"+box_typ+"_NA)";
       oldQty="OLDQTY_"+box_typ+"_NA";
       oldQtyVal="value("+oldQty+")";
    %>
     <%if(clint.equalsIgnoreCase("AG")){%>
   <td><html:text property="<%=oldQtyVal%>" styleId="<%=oldQty%>" styleClass="txtStyle" size="6" readonly="true"  name="mixAssortReturnForm" /></td>
   <%}%>
   <td><html:text  property="<%=qtyVal%>" styleId="<%=qtyId%>"  styleClass="txtStyle" size="6" name="mixAssortReturnForm" /> </td>
   <%if(clint.equalsIgnoreCase("AG")){%>
   <td><input type="text" name="<%=diffQtyId%>" id="<%=diffQtyId%>" onchange="DiffCtscal('<%=oldQty%>','<%=diffQtyId%>','<%=qtyId%>')" value="0" class="txtStyle" size="6" /> </td>
  <%}%>
  <%if(clint.equalsIgnoreCase("AG")){%>
   <td><html:text property="<%=oldCtsVal%>" styleId="<%=oldCts%>" styleClass="txtStyle" size="6" readonly="true"  name="mixAssortReturnForm" /></td>
   <%}%>
   <td><html:text property="<%=ctsVal%>" styleId="<%=ctsId%>" onchange="calCullateTtt(this,'TTLCTSMT','ttlcts','CTS_','F','1')" styleClass="txtStyle" size="9" name="mixAssortReturnForm" /></td>
    <%if(clint.equalsIgnoreCase("AG")){%>
   <td><input type="text" name="<%=diffVal%>" id="<%=diffId%>" onchange="DiffCtscal('<%=oldCts%>','<%=diffId%>','<%=ctsId%>')" value="0" class="txtStyle" size="6" /> </td>
  <%}%>
   <td><html:text property="<%=prcVal%>" styleId="<%=prcId%>" styleClass="txtStyle" size="11" onchange="calCullateTtt(this,'TTLCTSMT','ttlcts','CTS_','F','1')" value="<%=rteVal%>"  name="mixAssortReturnForm" /></td>
   </tr>
   <%
   if(boxIdLst!=null && boxIdLst.size()>0){
   for(int i=0;i<boxIdLst.size();i++){
   String boxId = (String)boxIdLst.get(i);
   if(boxId.indexOf(box_typ+"-")!=-1 ||boxId.indexOf(box_typ+"_")!=-1 ){
   boxId = (String)boxIdPrtLst.get(i);
   String boxVal = (String)boxIdLst.get(i);
   disBoxIdnLst.add(boxId);
    ctsId = "CTS_"+box_typ+"_"+boxId;
   ctsVal = "value("+ctsId+")";
   qtyId = "QTY_"+box_typ+"_"+boxId;
   qtyVal = "value("+qtyId+")";
   prcId = "RTE_"+box_typ+"_"+boxId;
   prcVal = "value("+prcId+")";
   rteVal = dbutil.getMixPriBOX(box_typ,boxVal);
   diffId = "DIFF_"+box_typ+"_"+boxId;
   diffVal = "value(DIFF_"+box_typ+"_"+boxId+")";
   oldCts="OLD_"+box_typ+"_"+boxId;
   oldCtsVal="value("+oldCts+")";
   
   diffQtyId = "QTYDIFF_"+box_typ+"_"+boxId;;
       diffQtyVal = "value(QTYDIFF_"+box_typ+"_"+boxId+")";
       oldQty="OLDQTY_"+box_typ+"_"+boxId;;
       oldQtyVal="value("+oldQty+")";
   
   %>
   <tr><td><%=boxId%></td>
      <%if(clint.equalsIgnoreCase("AG")){%>
   <td><html:text property="<%=oldQtyVal%>" styleId="<%=oldQty%>" styleClass="txtStyle" size="6" readonly="true"  name="mixAssortReturnForm" /></td>
   <%}%>
   <td><html:text property="<%=qtyVal%>" styleId="<%=qtyId%>" onchange="calCullateTtt(this,'TTLCTSMT','ttlcts','CTS_','F','1')"   styleClass="txtStyle" size="6" name="mixAssortReturnForm" /> </td>
     <%if(clint.equalsIgnoreCase("AG")){%>
   <td><input type="text" name="<%=diffQtyId%>" id="<%=diffQtyId%>" onchange="DiffCtscal('<%=oldQty%>','<%=diffQtyId%>','<%=qtyId%>')" value="0" class="txtStyle" size="6" /> </td>
  <%}%>
   <%if(clint.equalsIgnoreCase("AG")){%>
   <td><html:text property="<%=oldCtsVal%>" styleId="<%=oldCts%>" styleClass="txtStyle" size="6" readonly="true"  name="mixAssortReturnForm" /></td>
  <%}%>
   <td><html:text property="<%=ctsVal%>" styleId="<%=ctsId%>" onchange="calCullateTtt(this,'TTLCTSMT','ttlcts','CTS_','F','1')" styleClass="txtStyle" size="9" name="mixAssortReturnForm" /></td>
  <%if(clint.equalsIgnoreCase("AG")){%>
   <td><input type="text" name="<%=diffVal%>" id="<%=diffId%>" onchange="DiffCtscal('<%=oldCts%>','<%=diffId%>','<%=ctsId%>')" value="0" class="txtStyle" size="6" /> </td>
   <%}%>
   <td><html:text property="<%=prcVal%>" styleId="<%=prcId%>" styleClass="txtStyle" size="11" onchange="calCullateTtt(this,'TTLCTSMT','ttlcts','CTS_','F','1')" value="<%=rteVal%>"  name="mixAssortReturnForm" /></td>
   </tr>
   <%}}}
   gtMgr.setValue(lstNme+"BOXIDNLST", disBoxIdnLst);
   %>
   </table></td><%}%>
   <td valign="top">
   <%
   ArrayList pageList= ((ArrayList)pageDtl.get("CNT") == null)?new ArrayList():(ArrayList)pageDtl.get("CNT");
  if(pageList!=null && pageList.size()>0){
   HashMap pageDtlMap=(HashMap)pageList.get(0);
    String cnt=util.nvl((String)pageDtlMap.get("dflt_val"),"0");
    if(!cnt.equals("0")){
   
   %>
   <input type="hidden" name="COUNT" value="5" id="COUNT" />
    <table class="grid1">
   <tr>
   <th colspan="2">Box ID</th><th colspan="3"> </th></tr>
     <tr> <th colspan="2"></th><th>Qty</th><th>Cts</th><th>Rate</th>
   </tr>
   <tr><td></td><td></td><td> </td><td></td></tr>
    <%for(int i=1;i<=30;i++){
    String boxId = "BOX_ID_"+i;
    String boxTxtId = "BOXTXT_ID_"+i;
    String boxTxtId1 = "BOXTXT_ID1_"+i;
     qtyVal = "QTY_"+i;
     ctsVal = "CTS_"+i;
    String RteVal = "RTE_"+i;
    String isDisplay = "display:none;";
    String txtId = "TR_"+i;
    if(i<=5)
    isDisplay = "display:'';";
    %>
     <tr style="<%=isDisplay%>" id="<%=txtId%>">
     
     <td>
     
      <% 
              String setDown = "setDown('"+boxId+"', '"+boxTxtId1+"', '"+ boxTxtId +"',event)";
              String keyStr = "doCompletion('"+ boxTxtId +"', '" + boxId + "', 'mixAktAjaxAction.do?method=BoxIdns', event)";
        %>
                  <input type="text" name="<%=boxTxtId%>" id="<%=boxTxtId%>" class="sugBox"
                  onKeyUp="<%=keyStr%>"   onKeyDown="<%=setDown%>" size="9" />
                  
                <div style="position: absolute;">
                  <select id="<%=boxId%>" name="<%=boxId%>" class="sugBoxDiv" 
                    style="display:none;"  
                    onKeyDown="<%=setDown%>"
                    onDblClick="setVal('<%=boxId%>', '<%=boxTxtId1%>', '<%=boxTxtId%>', event);hideObj('<%=boxId%>')" 
                    onClick="setVal('<%=boxId%>', '<%=boxTxtId1%>','<%=boxTxtId%>', event);" 
                    size="10">
                  </select>
                </div>
     
     </td>
     <td><input type="text" name="<%=boxTxtId1%>" readonly="readonly" id="<%=boxTxtId1%>" size="9" /></td>
     <td><input type="text" name="<%=qtyVal%>" id="<%=qtyVal%>" size="6" class="txtStyle"/> </td>
     <td><input type="text" name="<%=ctsVal%>" id="<%=ctsVal%>" size="9" onchange="checkBoxIDFld(this,'<%=boxTxtId1%>');calCullateTtt(this,'TTLCTSMT','ttlcts','CTS_','F','1')"  class="txtStyle"/> </td>
     <td><input type="text" name="<%=RteVal%>" id="<%=RteVal%>" size="11" onchange="calCullateTtt(this,'TTLCTSMT','ttlcts','CTS_','F','1')" class="txtStyle"/> </td>

     </tr>
   <%}%>
   <tr><td colspan="4"><input type="button" value="Add More.." onclick="diaplyBoxTr()" class="submit" /> </td> </tr>
  </table>
   <%}}%>
   </td></tr>
   </table>
  </td></tr>
 
 </html:form>
  <%
 
  }else{%>
  <tr>
  <td valign="top" class="tdLayout">
  Sorry not stones on issue for selected box type.</td></tr>
 <% }}%>
 <tr><td valign="top" class="tdLayout" height="10px"></td></tr>
    <%String cancel = (String)request.getAttribute("cancel");
  if(cancel!=null){
  HashMap mprp = info.getMprp();
  HashMap pktDtl = (HashMap)gtMgr.getValue(lstNme+"_CNLST");
  ArrayList stockIdnLst =new ArrayList();
  LinkedHashMap stockList = new LinkedHashMap(pktDtl); 
  Set<String> keys = stockList.keySet();
       for(String key: keys){
       stockIdnLst.add(key);
        }
    gtMgr.setValue(lstNme+"_SEL", stockIdnLst);
  if(pktDtl!=null && pktDtl.size()>0){
  ArrayList boxViewLst = (ArrayList)session.getAttribute("boxViewLst");
   %>
   <html:form action="/mixAkt/assortReturn.do?method=pktDtl" method="post" onsubmit="return verifedTtlVal()" >
    <html:hidden property="value(stt)" name="mixAssortReturnForm" />
    <html:hidden property="value(box_typ)" name="mixAssortReturnForm" />
    <tr><td valign="top" class="tdLayout">
    <table>
    <tr><td>Packets</td>
    <td><html:textarea property="value(vnm)" styleId="vnm" /> </td>
    <td>  <html:submit property="value(fetch)" value="Filter" styleClass="submit"/> </td>
    </tr>
    </table>
    </td></tr>
    </html:form>
 <html:form action="/mixAkt/assortReturn.do?method=Cancel" method="post" onsubmit="return verifedTtlVal()" >
 <tr><td valign="top" class="tdLayout">
 <table cellpadding="2" cellspacing="2">
  <tr><td> 
    <html:submit property="value(cancelA)" value="Cancel Issue" styleClass="submit"/>
   </td></tr>
    <tr><td>
  <table class="grid1">
   <tr><th>Sr</th>
   <th><input type="checkbox" name="checkAll" id="checkAll" onclick="checkAllpage(this,'CHK_')" />ALL </th>
   <th>Packet No.</th><th>Status</th><th>Cts</th>
       <%for(int j=0; j < boxViewLst.size(); j++ ){
    String lprp = (String)boxViewLst.get(j);
   String hdr = util.nvl((String)mprp.get(lprp));
    String prpDsc = util.nvl((String)mprp.get(lprp+"D"));
    if(hdr == null) {
        hdr = lprp;
       }  %>
     <th title="<%=prpDsc%>"><%=lprp%></th>
     <%}%> </tr>
 
   <%for(int i=0;i<stockIdnLst.size();i++){
  String stkIdn = (String)stockIdnLst.get(i);
  GtPktDtl stockPkt = (GtPktDtl)stockList.get(stkIdn);
   String fldId = "CHK_"+stkIdn;

  int cnt = i+1;
  %>
  <tr>
   <td><%=cnt%></td> 
        <td><input type="checkbox" name="<%=fldId%>" id="<%=fldId%>" value="<%=stkIdn%>" /> </td>

   <td><%=stockPkt.getValue("vnm")%></td><td><%=stockPkt.getStt()%></td><td><%=stockPkt.getCts()%></td>
   
  <%for(int j=0; j < boxViewLst.size(); j++ ){
    String lprp = (String)boxViewLst.get(j);%>
    <td><%=stockPkt.getValue(lprp)%></td>
  <%}%></tr>
  <%}%>
 </table></td></tr>
 </table></td></tr>
 </html:form>
 <%}else{%>
  <tr>
  <td valign="top" class="tdLayout">Sorry no result found.</td></tr>
 <%}%>
 <%}%>
  <tr>
  <td valign="top" class="tdLayout">
  <%
  ArrayList summryDtl = (ArrayList)request.getAttribute("summryDtl");
  if(summryDtl!=null && summryDtl.size()>0){
  %><table class="grid1">
   <tr><th>Box Type</th><th>Box ID</th><th>Cts</th></tr>
 <% for(int i=0;i<summryDtl.size();i++){
  HashMap summryMap = (HashMap)summryDtl.get(i);
  %>
  <tr><td><%=summryMap.get("BOXTYP")%></td>
  <td><%=summryMap.get("BOXID")%></td><td><%=summryMap.get("CTS")%></td>
  </tr>
  <% }%>
 </table>
 <%}%>
  </td></tr>
<tr>
     <td><jsp:include page="/footer.jsp" /> </td>
</tr>
  </table>
 </body>
</html>