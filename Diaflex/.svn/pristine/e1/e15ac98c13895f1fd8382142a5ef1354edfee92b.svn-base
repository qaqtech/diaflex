<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page errorPage="../error_page.jsp" %>
<%@ page contentType="text/html;charset=windows-1252"%>
<jsp:useBean id="info" class="ft.com.InfoMgr" scope="session" />
<jsp:useBean id="util" class="ft.com.JspUtil" scope="page" />
<jsp:useBean id="gtMgr" class="ft.com.GtMgr" scope="session" />

<%@ page import="java.util.ArrayList,java.util.LinkedHashMap,java.util.HashMap,java.util.Set,java.sql.ResultSet,ft.com.*, java.util.Date, java.util.ArrayList, ft.com.dao.*"%>
<%@taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>

<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>Planning Return</title>
    <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/bse.css?v=<%=info.getJsVersion()%>"/>
    <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/general.css?v=<%=info.getJsVersion()%>"/>
     <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/style.css?v=<%=info.getJsVersion()%>"/>
     <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/bse.js?v=<%=info.getJsVersion()%> " > </script>
      <script type="text/javascript" src="<%=info.getReqUrl()%>/PopScripts/popup.js?v=<%=info.getJsVersion()%> " > </script>
     <script src="<%=info.getReqUrl()%>/PopScripts/jquery-1.2.6.min.js" type="text/javascript"></script>
             <script src="<%=info.getReqUrl()%>/jqueryScript/jqueryScript.js?v=<%=info.getJsVersion()%>" type="text/javascript"></script>
              <script src="<%=info.getReqUrl()%>/jqueryScript/jquery.min.js" type="text/javascript"></script>
                <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/newCalScript.js?v=<%=info.getJsVersion()%> " > </script>
<script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/assortScript.js?v=<%=info.getJsVersion()%> " > </script>
         <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/ajax.js?v=<%=info.getJsVersion()%> " > </script>

  </head>
        <%String logId=String.valueOf(info.getLogId());
        String onfocus="cook('"+logId+"')";
        String lstNme = (String)gtMgr.getValue("lstNmePLNRTN");
         HashMap stockListMap = new HashMap();
        stockListMap = (HashMap)gtMgr.getValue(lstNme);
        %>
 <body onfocus="<%=onfocus%>" onload="MM_preloadImages('images/faunalogo.png')" topmargin="0" leftmargin="0" rightmargin="0" bottommargin="0">
  <input type="hidden" id="reqUrl" name="reqUrl" value="<%=info.getReqUrl()%>"/> 
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
  <img src="../images/line.jpg" border="0" /></td><td valign="middle"> <span class="pgHed">Planing Return</span> </td>
</tr></table>
  </td>
  </tr>
   <tr>
  <td valign="top" class="hedPg">
<html:form action="rough/planingReturnAction.do?method=fecth" method="post" onsubmit="return validate_Assortreturn()">
   <html:hidden property="value(grpList)" name="planingReturnForm" />
   <table><tr><td>Process : </td>
   <td>
   <html:select property="value(mprcIdn)"  styleId="mprcIdn" name="planingReturnForm"   >
            <html:optionsCollection property="mprcList" name="planingReturnForm" 
            label="prc" value="mprcId" />
    </html:select>
   </td></tr>
   <tr>
    <td>
    Employee : </td>
   <td>
   <html:select property="value(empIdn)" styleId="empIdn" name="planingReturnForm"   >
           <html:option value="0" >--select--</html:option>
            <html:optionsCollection property="empList" name="planingReturnForm" 
            label="emp_nme" value="emp_idn" />
   </html:select>
   </td>
   </tr>
   <tr>
   <td>Issue Id:</td><td> <html:text property="value(issueId)" styleId="issueId"  name="planingReturnForm"  /></td>
   </tr>
   <tr>
   <td>Lot No:</td><td> <html:text property="value(lotNo)"  name="planingReturnForm"  /></td>
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
   String msg = (String)msgLst.get(i);
   %>
    <tr><td valign="top" class="tdLayout"><span class="redLabel"><%=msg%></span></td></tr>
   <%}}%>
    <%
      String view = (String)request.getAttribute("view");
   if(view!=null){
   ArrayList prpDspBlocked = info.getPageblockList();
   if(stockListMap!=null && stockListMap.size()>0){
   
     ArrayList vwPrpLst = (ArrayList)session.getAttribute("RGH_VIEW");
    HashMap totals = (HashMap)gtMgr.getValue(lstNme+"_TTL");
     HashMap planDtl = (HashMap)gtMgr.getValue(lstNme+"_PLN");
    HashMap mprp = info.getMprp();
     int colSpan =vwPrpLst.size()+5;
    %>
     <tr><td valign="top" class="tdLayout">
<table>
   <tr><td>Total :&nbsp;&nbsp;</td><td><span id="ttlqty"> <%=totals.get("MQ")%>&nbsp;&nbsp;</span></td><td>cts&nbsp;&nbsp;</td><td><span id="ttlcts"><%=totals.get("MW")%>&nbsp;&nbsp;</span></td><td>Selected:&nbsp;&nbsp;</td><td> Total :&nbsp;&nbsp;</td> <td><span id="qtyTtl">0&nbsp;&nbsp;</span></td><td>Cts&nbsp;&nbsp;</td><td><span id="ctsTtl">0</span> </td> </tr>
  
   </table>
     </td></tr>
    <html:form action="rough/planingReturnAction.do?method=Return" method="post"  onsubmit="return validate_issue('CHK_' , 'count')" >
  <tr><td valign="top" height="30px" class="tdLayout"><html:submit property="value(return)" value="Return" styleClass="submit" /> 
  Create Excel <img src="../images/ico_file_excel.png" border="0" onclick="CreateExcelPlaningReturn('<%=info.getReqUrl()%>/rough/planingReturnAction.do?method=createXL','','')"/>
  </td></tr>
  
  <tr><td valign="top" class="tdLayout">
  
   <table class="grid1" id="dataTable">
   <tr> <th><input type="checkbox" name="checkAll" id="checkAll" onclick="ALLCheckBoxDisable('checkAll','CHK_')" /> </th><th>Sr</th>
 
       <th>Issue Id</th>
        <th>Packet No.</th>
        <th>Plan</th>
        <th>Plan Dtl</th>
        <%for(int j=0; j < vwPrpLst.size(); j++ ){
    String prp = (String)vwPrpLst.get(j);
     String hdr = util.nvl((String)mprp.get(prp));
    String prpDsc = util.nvl((String)mprp.get(prp+"D"));
    if(prpDspBlocked.contains(prp)){
       }else{
    if(hdr == null) {
        hdr = prp;
       }   %>
          <th title="<%=prpDsc%>"><%=hdr%></th>

     <%}}%> </tr>
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
  String vnm = (String)stockPkt.getValue("vnm");
 String stt = util.nvl((String)stockPkt.getValue("stt"));
 String cts = (String)stockPkt.getValue("cts");
 String issIdn = (String)stockPkt.getValue("issIdn");
  String textId = "TXT_"+stkIdn;
  String target = "SC_"+stkIdn;
  String checkBoxId="CHK_"+stkIdn;
  String checkboxNme="value("+checkBoxId+")";
  String onclick ="AssortTotalCal(this,"+cts+",'','')";
  int sr=i+1;
  String planId="plan_"+sr;
  String planfld="value(plan_"+sr+")";
  String planchg="createplan('"+sr+"','"+stkIdn+"','"+issIdn+"')";
 %>
 <tr id="<%=target%>"><td> <html:checkbox  property="<%=checkboxNme%>"  onclick="<%=onclick%>" styleId="<%=checkBoxId%>" value="yes" />
 <input type="hidden" value="<%=cts%>" id="CTS_<%=stkIdn%>" />
 </td><td><%=sr%></td> 

 <td><%=issIdn%></td><td><%=vnm%></td>
 <td>
   <html:select property="<%=planfld%>" styleId="<%=planId%>"  name="planingReturnForm" onchange="<%=planchg%>">
    <html:option value="">Select</html:option>
     <option value="1">1</option>
     <option value="2">2</option>
     <option value="3">3</option>
     <option value="4">4</option>
     <option value="5">5</option>
    </html:select>
 </td>
 <td>
 <table>
 <tr><td>Plan</td><td>QTY</td><td>P.CTS </td><td>P.VLU </td><td>P.RTE </td><td>R.RTE </td> </tr>
 <%for(int k=1;k<=5;k++){
 String isDisplay="display:none";
  HashMap calDtl = new HashMap();
 if(planDtl!=null && planDtl.size()>0)
   calDtl = (HashMap)planDtl.get(stkIdn+"_"+k);
  if(calDtl ==null)
   calDtl =  new HashMap();
  if(calDtl!=null && calDtl.size()>0)
  isDisplay="";
 
 
 %>
 <tr id="PLANTR_<%=k%>_<%=stkIdn%>" style="<%=isDisplay%>"><td> <input type="radio" name="FNPLN_<%=stkIdn%>" checked="checked" id="FNPLN_<%=k%>_<%=stkIdn%>" value="<%=k%>"/> &nbsp;<%=k%> </td> <td><label id="QTY_<%=k%>_<%=stkIdn%>" class="redLabel"><%=calDtl.get("QTY")%></label> </td><td><label id="CTS_<%=k%>_<%=stkIdn%>" class="redLabel"><%=calDtl.get("CTS")%></label> </td>
  <td><label id="VLU_<%=k%>_<%=stkIdn%>" class="redLabel"><%=calDtl.get("VLU")%></label> </td><td><label id="RTE_<%=k%>_<%=stkIdn%>" class="redLabel"><%=calDtl.get("RTE")%></label> </td>
   <td><label id="RRTE_<%=k%>_<%=stkIdn%>" class="redLabel"><%=calDtl.get("RRTE")%></label> </td>
 </tr>
 <%}%>

</table>
</td>
 <%for(int j=0; j < vwPrpLst.size(); j++ ){
    String prp = (String)vwPrpLst.get(j);
    %>
    <td><%=stockPkt.getValue(prp)%></td>
 <%}%>


</tr>
<tr>
<td colspan="<%=colSpan%>" class="floating" width="950" height="600"  id="PLANDIV_<%=sr%>" style="display:none">

<iframe id="PLANFRAME_<%=sr%>" width="950" height="600"  name="PLANFRAME_<%=sr%>" class="frameStyle"  align="middle" frameborder="0"></iframe>

</td>
</tr>
 <%}%>
  
   
   </table>
   </td></tr>
  </html:form>
  <%}}%>
  <tr>
    <td><jsp:include page="/footer.jsp" /> </td>
    </tr>
  </table> </body>
</html>