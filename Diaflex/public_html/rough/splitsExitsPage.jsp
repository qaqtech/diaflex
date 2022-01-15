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
    <title>Assort Issue</title>
    <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/bse.css?v=<%=info.getJsVersion()%>"/>
    <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/general.css?v=<%=info.getJsVersion()%>"/>
     <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/style.css?v=<%=info.getJsVersion()%>"/>
     <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/bse.js?v=<%=info.getJsVersion()%> " > </script>
     <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/assortScript.js?v=<%=info.getJsVersion()%> " > </script>
     <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/newCalScript.js?v=<%=info.getJsVersion()%> " > </script>

  </head>
  <%
  ArrayList sttList = (ArrayList)session.getAttribute("InsttList");
    String issID = util.nvl(request.getParameter("issID"));
       String form = util.nvl((String)request.getParameter("form")).trim();
       String msg = util.nvl((String)request.getAttribute("msg"));
     %>
  <body>
  <input type="hidden" id="issIdn" name="issIdn" value="<%=issID%>" />
  <input type="hidden" id="formNme" name="formNme" value="<%=form%>" />
 <% if(msg.equals("SUCCESS")){
  %>
  <script type="text/javascript">

 var issID = document.getElementById("issIdn").value;
 var form = document.getElementById("formNme").value;
 window.opener.location.href="roughReturnAction.do?method=fecth&issID="+issID+"&form="+form;
  window.close();
 </script>
  <%}%>
  <div id="backgroundPopup"></div>
<div id="popupContactSave" align="center" >
<img src="../images/loading_flame.gif" height="150px" width="150px" border="0" />
</div>
  <table>
  
  <%if(!msg.equals("")){%>
 <tr><td valign="top" class="tdLayout"><span class="redLabel"><%=msg%></span></td></tr>
   <%}%>
   <tr>
   <td valign="top" class="tdLayout">
   <html:form action="rough/roughReturnAction.do?method=fecthExit" method="post" onsubmit="loading()" >
   <input type="hidden" name="issID" id="issID" value="<%=issID%>" />
        <input type="hidden" name="form" id="form" value="<%=form%>" />

   <table class="grid1">
   <tr><th colspan="2">Generic Search </th></tr>
   <%if(sttList!=null && sttList.size()>0){%>
    <tr>
   <td colspan="2"> <table>
   <tr><td>
    Status </td>
   <td>
   <html:select property="value(stt)"  styleId="stt" name="splitReturnForm"  >
      <%for(int i=0;i<sttList.size();i++){
       HashMap sttMap = (HashMap)sttList.get(i);
       String key = (String)sttMap.get("key");
        String val = (String)sttMap.get("val");
      %>
        <html:option value="<%=val%>" ><%=key%></html:option>
           <%}%>
    </html:select></td></tr>
     </table>
     </td></tr>
  <%}%>
  
   <tr>
   <td>  
   <table>
   <tr>
   <td>Packet No:</td><td> <html:textarea property="value(vnmLst)" rows="7"  cols="30" name="splitReturnForm"  /></td>
   </tr>
   </table>
   </td>
   <td>  <jsp:include page="/genericSrch.jsp"/>  </td>
   
   </tr>
     <tr><td colspan="2" align="center"><html:submit property="value(srch)" value="Fetch" styleClass="submit" /></td> </tr>
   </table></html:form>
   </td>
  </tr>
  <tr>
   <td valign="top" class="tdLayout" height="10px;"></td></tr>
  <%
  String view = (String)request.getAttribute("view");
  if(view!=null){
  HashMap stockListMap =(HashMap)request.getAttribute("stockList");
  ArrayList vwPrpLst = (ArrayList)session.getAttribute("SPLITVIEWLST");
  if(stockListMap!=null && stockListMap.size()>0){
  HashMap mprp = info.getMprp();
  HashMap prp = info.getPrp();
  %>
   
    <html:form action="rough/roughReturnAction.do?method=IssueExitsPkt" method="post" onsubmit="return validate_issue('CHK_' , 'count');loading();" >
      <tr>
   <td valign="top" class="tdLayout">
   <html:submit property="value(select)" value="Select" styleClass="submit" />
   
   </td></tr>
    <tr>
   <td valign="top" class="tdLayout">
    <input type="hidden" name="issID" id="issID" value="<%=issID%>" />
     <input type="hidden" name="form" id="form" value="<%=form%>" />
    <table class="grid1">
   <tr> <th><input type="checkbox" name="checkAll" id="checkAll" onclick="ALLCheckBoxCal('checkAll','CHK_')" /> </th><th>Sr</th>
        <th>Packet No.</th>
      
    <%for(int j=0; j < vwPrpLst.size(); j++ ){
    String lprp = (String)vwPrpLst.get(j);
    
     String hdr = util.nvl((String)mprp.get(lprp));
    String prpDsc = util.nvl((String)mprp.get(lprp+"D"));
    if(hdr == null) {
        hdr = lprp;
       }  
if(prp.equals("CRTWT")){%>
 <th>Qty</th>      
<%}
%>
<th title="<%=prpDsc%>"><%=lprp%></th>
<%}%>       
</tr>
 <%
ArrayList stockIdnLst =new ArrayList();
 LinkedHashMap stockList = new LinkedHashMap(stockListMap); 
  Set<String> keys = stockList.keySet();
        for(String key: keys){
       stockIdnLst.add(key);
        }
int sr=0;
 for(int i=0; i < stockIdnLst.size(); i++ ){
  String stkIdn = (String)stockIdnLst.get(i);
 GtPktDtl stockPkt = (GtPktDtl)stockList.get(stkIdn);
 String cts = (String)stockPkt.getValue("cts");
 String qty = (String)stockPkt.getValue("qty");
 String onclick = "CalTtlOnChlick("+stkIdn+" , this , 'NO' )";
 sr = i+1;
 String checkFldId = "CHK_"+sr;
 String checkFldVal = "cb_stk_"+stkIdn ;
 %>
<tr>
<td><input type="checkbox" name="<%=checkFldVal%>" id="<%=checkFldId%>" value="<%=stkIdn%>" onclick="<%=onclick%>" /></td>
<td><%=sr%></td>
<td><%=stockPkt.getValue("vnm")%><input type="hidden" id="CTS_<%=stkIdn%>"  value="<%=cts%>" /></td>

<%for(int j=0; j < vwPrpLst.size(); j++ ){
    String lprp = (String)vwPrpLst.get(j);
    String val =stockPkt.getValue(lprp);
    
  if(prp.equals("CRTWT")){
   val=cts;
    %>
     <td><%=qty%><input type="hidden" id="QTY_<%=stkIdn%>"  value="<%=qty%>" /></td>
    <%}%>
    <td><%=val%></td>
<%}%>
</tr>
   <%}%>
   </table>
   <input type="hidden" name="count" id="count" value="<%=sr%>" />
    </td></tr>
    
   </html:form>
   
   
  
   <%}else{%>
    <tr>
   <td valign="top" class="tdLayout">
   Sorry no result found
   </td></tr>
  <% }}%>
  </table>
  </body>
</html>