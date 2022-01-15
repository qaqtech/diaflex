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
    <title>Mix Issue</title>
    <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/bse.css?v=<%=info.getJsVersion()%>"/>
    <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/general.css?v=<%=info.getJsVersion()%>"/>
     <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/style.css?v=<%=info.getJsVersion()%>"/>
     <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/bse.js?v=<%=info.getJsVersion()%> " > </script>
     <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/assortScript.js?v=<%=info.getJsVersion()%> " > </script>
     <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/newCalScript.js?v=<%=info.getJsVersion()%> " > </script>

  </head>
  <%
String lstNme = (String)gtMgr.getValue("lstNmeMIXISS");
HashMap stockListMap = new HashMap();
stockListMap = (HashMap)gtMgr.getValue(lstNme);

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
  <img src="../images/line.jpg" border="0" /></td><td valign="middle"> <span class="pgHed">Mix Assort Issue</span> </td>
</tr></table>
  </td>
  </tr>
   <%
   String msg = (String)request.getAttribute("msg");
   if(msg!=null){
     %>
    <tr><td valign="top" class="tdLayout"><span class="redLabel"><%=msg%></span></td></tr>
   <%}%>
    <tr>
   
   <td valign="top" class="tdLayout">
   <table><tr><td>
   <html:form action="mixLkhi/mixAssortIssue.do?method=fecth" method="post" onsubmit="return validate_assort()">
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
   <html:select property="value(mprcIdn)"  styleId="mprcIdn" name="mixAssortIssueLKForm"    >
           <html:option value="0" >--select--</html:option>
            <html:optionsCollection property="mprcList" name="mixAssortIssueLKForm" 
            label="prc" value="mprcId" />
    </html:select>
   </td></tr>
   <tr>
    <td>
    Employee : </td>
   <td>
   <html:select property="value(empIdn)"  styleId="empIdn" name="mixAssortIssueLKForm"  >
           <html:option value="0" >--select--</html:option>
            <html:optionsCollection property="empList" name="mixAssortIssueLKForm" 
            label="emp_nme" value="emp_idn" />
            </html:select>
   </td>
   </tr>
   
   <tr>
   <td>Packet No:</td><td> <html:textarea property="value(vnmLst)" rows="7"  cols="30" name="mixAssortIssueLKForm"  /></td>
   </tr>
  </table></td>
   <td>
   
    <jsp:include page="/genericSrch.jsp"/>
   </td></tr>
   <tr><td colspan="2" align="center"><html:submit property="value(srch)" value="Fetch" styleClass="submit" /></td> </tr>
   </table>
   </html:form>
   </td></tr></table></td></tr>
 <%
   String view = (String)request.getAttribute("view");
   if(view!=null){
   ArrayList prpDspBlocked = info.getPageblockList();
   if(stockListMap!=null && stockListMap.size()>0){
    ArrayList vwPrpLst = (ArrayList)session.getAttribute("MIX_VIEW");
    HashMap totals = (HashMap)gtMgr.getValue(lstNme+"_TTL");
    HashMap mprp = info.getMprp();
    int sr = 0;
   %>
   <tr><td valign="top" class="tdLayout">
   <table>
   <tr><td>Total :&nbsp;&nbsp;</td><td><span id="ttlqty"> <%=totals.get("MQ")%>&nbsp;&nbsp;</span></td><td>cts&nbsp;&nbsp;</td><td><span id="ttlcts"><%=totals.get("MW")%>&nbsp;&nbsp;</span></td><td>Selected:&nbsp;&nbsp;</td><td> Total :&nbsp;&nbsp;</td> <td><span id="qtyTtl">0&nbsp;&nbsp;</span></td><td>Cts&nbsp;&nbsp;</td><td><span id="ctsTtl">0</span> </td> </tr>
  
   </table>
   </td></tr>
   <html:form action="mixLkhi/mixAssortIssue.do?method=Issue" method="post" onsubmit="return validate_issue('CHK_' , 'count')">
   <html:hidden property="value(prcId)" name="mixAssortIssueLKForm" />
   <html:hidden property="value(empId)" name="mixAssortIssueLKForm" />
      <html:hidden property="value(lstNme)" name="mixAssortIssueLKForm" value="<%=lstNme%>" />

   
   <tr><td valign="top" class="tdLayout">
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
   <tr><td valign="top" class="tdLayout">
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
    <tr>
    <td><jsp:include page="/footer.jsp" /> </td>
    </tr>
   </table>
   
  
  
  

</html>