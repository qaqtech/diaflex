<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page errorPage="../error_page.jsp" %>
<%@ page contentType="text/html;charset=windows-1252"%>
<jsp:useBean id="info" class="ft.com.InfoMgr" scope="session" />
<jsp:useBean id="gtMgr" class="ft.com.GtMgr" scope="session" />

<jsp:useBean id="util" class="ft.com.JspUtil" scope="page" />
<%@ page import="java.util.ArrayList,java.util.LinkedHashMap, java.util.Set,java.util.HashMap,java.sql.ResultSet,ft.com.*, java.util.Date, java.util.ArrayList, ft.com.dao.*"%>
<%@taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>Process Issue</title>
    <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/bse.css?v=<%=info.getJsVersion()%>"/>
    <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/general.css?v=<%=info.getJsVersion()%>"/>
     <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/style.css?v=<%=info.getJsVersion()%>"/>
      <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/bse.js?v=<%=info.getJsVersion()%> " > </script>
            <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/ajax.js?v=<%=info.getJsVersion()%> " > </script>
       <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/assortScript.js?v=<%=info.getJsVersion()%> " > </script>
              <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/newCalScript.js?v=<%=info.getJsVersion()%> " > </script>

  </head>
        <%String logId=String.valueOf(info.getLogId());
        String onfocus="cook('"+logId+"')";
        %>
 <body onfocus="<%=onfocus%>" onload="MM_preloadImages('images/faunalogo.png')" topmargin="0" leftmargin="0" rightmargin="0" bottommargin="0">
<%
String lstNme = (String)request.getAttribute("lstNme");
String method = util.nvl(request.getParameter("method"));
HashMap stockListMap = new HashMap();
boolean disabled= false;
if(method.equals("fecth")){
if(lstNme!=null)
stockListMap = (HashMap)gtMgr.getValue(lstNme);
if(stockListMap!=null && stockListMap.size()>0)
disabled= true;
}
HashMap prpLst = info.getPrp();

     String allow_valid_prc=util.nvl((String)info.getDmbsInfoLst().get("ALLOW_VALID_PRC"),"N");
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
  <img src="../images/line.jpg" border="0" /></td><td valign="middle"> <span class="pgHed">Process Issue</span> </td>
</tr></table>
  </td>
  </tr>
  <%
   String msg = (String)request.getAttribute("msg");
   if(msg!=null){
   HashMap dbInfoSys = info.getDmbsInfoLst();
   String issueIdn = util.nvl((String)request.getAttribute("issueidn"));
   String cnt = (String)dbInfoSys.get("CNT");
   String homeDir = (String)dbInfoSys.get("HOME_DIR");
    String webDir = (String)dbInfoSys.get("REP_URL");
     String repPath = (String)dbInfoSys.get("REP_PATH");
int accessidn=Integer.parseInt(util.nvl((String)request.getAttribute("accessidn"),"0"));
    String assortiss = repPath+"/reports/rwservlet?"+cnt+"&report="+homeDir+"\\reports\\Grading_slp.jsp&p_iss_id="+issueIdn+"&p_access="+accessidn;
   %>
    <tr><td valign="top" class="tdLayout"><span class="redLabel"><%=msg%></span></td></tr>
   <% if(cnt.equals("hk")){%>
    <tr><td valign="top" class="tdLayout"><span class="txtLink"><a href="<%=assortiss%>" target="_blank">Grading Slip</a></span></td></tr>
   <%}}%>
    <%
   String vnmNtFnd = (String)request.getAttribute("vnmNotFnd");
     if(vnmNtFnd!=null){%>
    <tr><td valign="top" class="tdLayout"><span class="redLabel"><%=vnmNtFnd%></span></td></tr>
   <%}%>
   <tr>
   
   <td valign="top" class="tdLayout">
   <table><tr><td>
   <html:form action="assort/assortIssue.do?method=fecth" method="post" onsubmit="return validate_assort()">
      <html:hidden property="value(allow_valid_prc)" styleId="allow_valid_prc" name="assortIssueForm" value="<%=allow_valid_prc%>" />
  <table  class="grid1">
  <tr><th> </th><th>Generic Search 
  <%
  ArrayList rolenmLst=(ArrayList)info.getRoleLst();
  String usrFlg=util.nvl((String)info.getUsrFlg());
  if(usrFlg.equals("SYS") || rolenmLst.contains("ADMIN")){
  %>
  <img src="../images/edit.jpg" title="Edit Properties Module" onclick="newWindow('<%=info.getReqUrl()%>/dynamicProperty.do?method=load&mdl=AS_SRCH&sname=asGNCSrch&par=GENERIC')" border="0" width="15px" height="15px"/> 
  <%}%>
  </th></tr>
  <tr><td valign="top">
   <table><tr><td>Process </td>
   <td>
   <html:select property="value(mprcIdn)"  styleId="mprcIdn" name="assortIssueForm" disabled="<%=disabled%>" onchange="getvalidPrcEmp('mprcIdn')"   >
           <html:option value="0" >--select--</html:option>
            <html:optionsCollection property="mprcList" name="assortIssueForm" 
            label="prc" value="mprcId" />
    </html:select>
   </td></tr>
   <tr>
    <td>
    Employee : </td>
   <td>
   <html:select property="value(empIdn)"  styleId="empIdn" name="assortIssueForm" disabled="<%=disabled%>"  >
           <html:option value="0" >--select--</html:option>
            <html:optionsCollection property="empList" name="assortIssueForm" 
            label="emp_nme" value="emp_idn" />
            </html:select>
   </td>
   </tr>
   <tr>
   <td>Issue Id</td><td><html:text property="value(issueIdn)" name="assortIssueForm" /> </td>
   </tr>
   <tr>
   <td>Packet No:</td><td> <html:textarea property="value(vnmLst)" rows="7" styleId="vnmLst"  cols="30" name="assortIssueForm"  onkeyup="getVnmCount('vnmLst','vnmLstCnt')" onchange="getVnmCount('vnmLst','vnmLstCnt')" /><br><span id="vnmLstCnt">0</span></td>
   </tr>
  </table></td>
   <td>
   
    <jsp:include page="/genericSrch.jsp"/>
   </td></tr>
   <tr><td colspan="2" align="center">
   <html:submit property="value(srch)" value="Fetch" styleClass="submit" />
   
   <%
 ArrayList rfiddevice = ((ArrayList)info.getRfiddevice() == null)?new ArrayList():(ArrayList)info.getRfiddevice();
 if(rfiddevice!=null && rfiddevice.size()>0){
 %>
 RFID Device: <html:select property="value(dvcLst)" styleId="dvcLst"   >
 <%for(int j=0;j<rfiddevice.size();j++){
 ArrayList device=new ArrayList();
 device=(ArrayList)rfiddevice.get(j);
 String val=(String)device.get(0);
 String disp=(String)device.get(1);
 %>
 <html:option value="<%=val%>"><%=disp%></html:option>
 <%}%>
 </html:select>&nbsp;
 <%if(info.getRfid_seq().length()==0){%>
 <html:button property="value(rfScan)" value="RF ID Scan" styleId="rfScan" onclick="doScan('pid','fldCtr','dvcLst','SCAN')"  styleClass="submit" />
 <html:button property="value(autorfScan)" value="Auto Scan" styleId="autorfScan" onclick="doScan('pid','fldCtr','dvcLst','AUTOSCAN')"  styleClass="submit" />
<%}%>
 <html:button property="value(stopAutorfScan)" value="Stop Auto Scan" onclick="doScan('pid','fldCtr','dvcLst','STOPAUTOSCAN')"  styleClass="submit" />
 
<%}%>
              <%if(util.nvl((String)info.getDmbsInfoLst().get("RFID_DNK"),"N").equals("Y")){%>
                        Continue Scan <input type="checkbox" id="contscan" title="Select To Continue Scan"></input>
                        <span style="margin:0px; padding:0px; display:none;">
                        <label id="summary" style="display:none;"></label>&nbsp;&nbsp;
                        </span>
                        Count&nbsp;&nbsp;:&nbsp;<label id="count"></label>&nbsp;&nbsp;
                        <span style="margin:0px; padding:0px; display:none;">
                        Notify&nbsp;&nbsp;:&nbsp;<label id="notify"></label>
                        <input type="checkbox" id="accumulateMode" title="Accumulate Mode" onchange="AcculateChanged()"></input>
                        </span>
                        <html:button property="value(myButton)" value="Scan" styleId="myButton" onclick="scanRf()"  styleClass="submit" />
                        <span id="loadingrfid"></span>
             <%}else if(util.nvl((String)info.getDmbsInfoLst().get("RFID_DNK_USB"),"N").equals("Y")){%>
                        <applet id="napp" code="RFApp.class" width="1" height="1" ARCHIVE = "rfq.jar,sdk.jar" CODEBASE = "http://gia.dnktechnologies.com/test/resource/" style="visibility:hidden;"></applet>
                        Continue Scan <input type="checkbox" id="contscan" title="Select To Continue Scan"></input>
                        <span style="margin:0px; padding:0px; display:none;">
                        <label id="summary" style="display:none;"></label>&nbsp;&nbsp;
                        </span>
                        Count&nbsp;&nbsp;:&nbsp;<label id="count"></label>&nbsp;&nbsp;
                        <span style="margin:0px; padding:0px; display:none;">
                        Notify&nbsp;&nbsp;:&nbsp;<label id="notify"></label>
                        <input type="checkbox" id="accumulateMode" title="Accumulate Mode" onchange="AcculateChangedUSB()"></input>
                        </span>
                        <html:button property="value(myButton)" value="Scan" styleId="myButton" onclick="scanRfUSB()"  styleClass="submit" />
             <%} %>   
   </td> </tr>
   </table>
   </html:form>
   </td></tr>
   
   <%
   String view = (String)request.getAttribute("view");
   if(view!=null){
   ArrayList prpDspBlocked = info.getPageblockList();
   if(stockListMap!=null && stockListMap.size()>0){
   ArrayList issEditPrp = (ArrayList)gtMgr.getValue("AssortIssueEditPRP");

    ArrayList vwPrpLst = (ArrayList)session.getAttribute("asViewLst");
    HashMap totals = (HashMap)request.getAttribute("totalMap");
    HashMap mprp = info.getMprp();
    int sr = 0;
   %>
   <tr><td>
   <table>
   <tr><td>Total :&nbsp;&nbsp;</td><td><span id="ttlqty"> <%=totals.get("qty")%>&nbsp;&nbsp;</span></td><td>cts&nbsp;&nbsp;</td><td><span id="ttlcts"><%=totals.get("cts")%>&nbsp;&nbsp;</span></td><td>Selected:&nbsp;&nbsp;</td><td> Total :&nbsp;&nbsp;</td> <td><span id="qtyTtl">0&nbsp;&nbsp;</span></td><td>Cts&nbsp;&nbsp;</td><td><span id="ctsTtl">0</span> </td> </tr>
  
   </table>
   </td></tr>
   <html:form action="assort/assortIssue.do?method=Issue" method="post" onsubmit="return validate_issue('CHK_' , 'countCnt')">
   <html:hidden property="value(prcId)" name="assortIssueForm" />
   <html:hidden property="value(empId)" name="assortIssueForm" />
      <html:hidden property="value(lstNme)" name="assortIssueForm" value="<%=lstNme%>" />

   
   <tr><td>
   <table><tr><td>
   <html:submit property="value(issue)" styleClass="submit" value="Issue" /></td> 
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
   <tr><td>
   <table class="grid1">
   <tr> <th><input type="checkbox" name="checkAll" id="checkAll" onclick="ALLCheckBoxCal('checkAll','CHK_')" /> </th><th>Sr</th>
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
<th title="<%=prpDsc%>"><%=prp%></th>
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
     <html:select  property="<%=prpFld%>" styleId="<%=selectALL%>" name="assortIssueForm" onchange="<%=chksrvAll%>"  style="width:100px"   > 
      <html:option value="0">--select--</html:option>
            <%
            for(int k=0; k < prpVal.size(); k++) {
                String pPrt = (String)prpVal.get(k);
             %>
              <html:option value="<%=pPrt%>" ><%=prpPrt.get(k)%></html:option> 
            <%}%>
     </html:select>  
     <%}else{%>
     <html:text property="<%=prpFld%>" styleId="<%=selectALL%>" name="assortIssueForm"  style="width:100px" onchange="<%=chksrvTxtAll%>" />
     <%}%>
     <%=prpDsc%>
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
 String cts = (String)stockPkt.getCts();
 String onclick = "CalTtlOnChlick("+stkIdn+" , this , 'NO' )";
 sr = i+1;
 String checkFldId = "CHK_"+sr;
 String checkFldVal = "cb_stk_"+stkIdn ;
 %>
<tr>
<td><input type="checkbox" name="<%=checkFldVal%>" id="<%=checkFldId%>" value="<%=stkIdn%>" onclick="<%=onclick%>" /></td>
<td><%=sr%></td>
<td><%=stockPkt.getVnm()%><input type="hidden" id="CTS_<%=stkIdn%>"  value="<%=cts%>" /></td>

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
String fldId = lprp+"_"+sr;
%>
<td>
<%if(prpTyp.equals("C")){%>
<html:select property="<%=fldVal%>" styleId="<%=fldId%>" name="assortIssueForm" style="width:100px">
<html:option value="0" >--select--</html:option>
<%for(int p=0;p<prpVal.size();p++){
String val = (String)prpVal.get(p);
%>
<html:option value="<%=val%>" ><%=prpDsc.get(p)%></html:option>
<%}%></html:select>
<%}else{%>
<html:text property="<%=fldVal%>" styleId="<%=fldId%>" style="width:100px" name="assortIssueForm" />
<%}%>
</td>
<%}%>
</tr>
   <%}%>
   </table></td></tr>
   <input type="hidden" name="countCnt" id="countCnt" value="<%=sr%>" />
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