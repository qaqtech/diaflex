<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page errorPage="../error_page.jsp" %>
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import="java.util.ArrayList,java.util.HashMap,java.sql.ResultSet,ft.com.*, java.util.Date, java.util.ArrayList, ft.com.dao.*"%>
<jsp:useBean id="info" class="ft.com.InfoMgr" scope="session" />
<jsp:useBean id="util" class="ft.com.JspUtil" scope="page" />
<%@taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>Purchase </title>
    <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/bse.css?v=<%=info.getJsVersion()%>"/>
    <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/style.css?v=<%=info.getJsVersion()%>"/>
    <script language="javaScript" type="text/javascript" src="<%=info.getReqUrl()%>/calendar/calendar.js"></script>
    <link href="<%=info.getReqUrl()%>/calendar/calendar.css" rel="stylesheet" type="text/css" />
      <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/ajax.js?v=<%=info.getJsVersion()%>"></script> 
       <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/bse.js?v=<%=info.getJsVersion()%>"></script> 
      <script src="<%=info.getReqUrl()%>/PopScripts/jquery-1.2.6.min.js" type="text/javascript"></script>
      <script src="<%=info.getReqUrl()%>/jqueryScript/jqueryScript.js?v=<%=info.getJsVersion()%>" type="text/javascript"></script>
      <script src="<%=info.getReqUrl()%>/jqueryScript/jquery.min.js" type="text/javascript"></script>  
<script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/newCalScript.js?v=<%=info.getJsVersion()%>"></script> 

  </head>
        <%String logId=String.valueOf(info.getLogId());
        String onfocus="cook('"+logId+"')";
        %>
 <body onfocus="<%=onfocus%>" onload="MM_preloadImages('images/faunalogo.png')"  onkeypress="return disableEnterKey(event);"  topmargin="0" leftmargin="0" rightmargin="0" bottommargin="0">
<table width="100%"  border="0" align="center" cellpadding="0" cellspacing="0" class="mainbg">
  <tr>
    <td height="103" valign="top">
   <jsp:include page="/header.jsp" />
    
    </td>
  </tr>
 
  <tr>
    <td height="5" valign="top" class="greyline"><img src="<%=info.getReqUrl()%>/images/grey_linebig.png" width="685" height="5" align="right" /></td>
  </tr>
  
  <tr>
  <td valign="top" class="hedPg">
  <table cellpadding="1"  cellspacing="5"><tr><td valign="middle">
  <img src="../images/line.jpg" border="0" /></td><td valign="middle"> <span class="pgHed">Purchase confirmation</span> </td></tr></table>
  </td>
  </tr>
  <% if(request.getAttribute("msg")!=null){%>
        <tr><td class="tdLayout" height="15"><span class="redLabel"> <%=request.getAttribute("msg")%></span></td></tr>
  <%}%>
   <tr>
  <td class="tdLayout" valign="top">
     <html:form action="purchase/purConfrim.do?method=fetch" method="post" >
    <html:hidden property="inStt" name="purchaseConfrimForm" />
   <table><tr><td>Purchase Vendor </td>
   <td>
   <html:select property="value(pur_idn)"  styleId="pur_idn" name="purchaseConfrimForm"   >
           <html:option value="0" >--select--</html:option>
            <html:optionsCollection property="venderList" name="purchaseConfrimForm" 
            label="dsc" value="srt" />
    </html:select>
   </td></tr>
   <tr>
    <td>
   <html:submit property="value(srch)" value="Fetch" styleClass="submit" />
   </td></tr></table>
  </html:form>
  </td></tr>
  
  <tr>
  <td class="tdLayout" valign="top">
   <html:form action="purchase/purConfrim.do?method=save" method="post" onsubmit="return verifyPurConfrim()">
  <%
  String view = (String)request.getAttribute("view");
  ArrayList purPktList = (ArrayList)request.getAttribute("purPktDtlList");
   HashMap assortDtlList = (HashMap)session.getAttribute("assortDtlList");
     HashMap ttlMapDtl = (HashMap)request.getAttribute("ttlMapDtl");
  if(view!=null){
  HashMap mprp = info.getMprp();
  if(purPktList!=null && purPktList.size()>0){
  ArrayList viewPrpList =(ArrayList)session.getAttribute("PUR_CNF_VW");
  ArrayList asPrpList =(ArrayList)session.getAttribute("AS_CNF_VW");
  ArrayList stkIdnLst = new ArrayList();
   ArrayList vnmLst = new ArrayList();
  %><table>
  <tr><td> <html:submit property="value(srch)" value="Save Changes" styleClass="submit" />&nbsp;&nbsp;
<b> Purchase Details View </b> <%
   ArrayList rolenmLst=(ArrayList)info.getRoleLst();
  String usrFlg=util.nvl((String)info.getUsrFlg());
  if(usrFlg.equals("SYS") || rolenmLst.contains("ADMIN")){
  %>
  <img src="../images/edit.jpg" title="Edit Properties Module" onclick="newWindow('<%=info.getReqUrl()%>/dynamicProperty.do?method=load&mdl=PUR_CNF_VW&sname=PUR_CNF_VW&par=VW')" border="0" width="15px" height="15px"/> 
  <%}%>
  <b> Assort Details View </b>
  <% if(usrFlg.equals("SYS") || rolenmLst.contains("ADMIN")){
  %>
  <img src="../images/edit.jpg" title="Edit Properties Module" onclick="newWindow('<%=info.getReqUrl()%>/dynamicProperty.do?method=load&mdl=AS_CNF_VW&sname=AS_CNF_VW&par=VW')" border="0" width="15px" height="15px"/> 
  <%}%>
  <b>Next Process : </b>
    <html:select property="value(outprc)"  styleId="outprc"   onchange="chksrvAll('outprc')" name="purchaseConfrimForm"   >
          
            <html:optionsCollection property="outPrcList" name="purchaseConfrimForm" 
            label="dsc" value="srt" />
    </html:select>
  
  </td> </tr>
  
  
    <%for(int i=0;i<purPktList.size();i++){
   HashMap pktDtl = (HashMap)purPktList.get(i);
   String purstkIdn = (String)pktDtl.get("mstk_idn");
    String vnm = (String)pktDtl.get("vnm");
   stkIdnLst.add(purstkIdn);
   vnmLst.add(vnm);
   HashMap ttlDtl = (HashMap)ttlMapDtl.get(purstkIdn);
   int vwSize = viewPrpList.size();
   int colspan=vwSize+4;
   %>
  <tr><td>
  <table class="grid1">
  <tr><th></th> <th>Pur Id</th><th>Date</th> <th>Ref No</th>
  <%for(int j=0;j<vwSize;j++){
  String lprp = (String)viewPrpList.get(j);
   String hdr = (String)mprp.get(lprp);
    String prpDsc = (String)mprp.get(lprp+"D");
    if(hdr == null) {
        hdr = lprp;
       }  
  %>
  <th title="<%=prpDsc%>"><%=hdr%></th>
 
 <% }%><th>Cts</th><th>Rte</th><th>Amount</th><th>Final Rte</th>
  <th>Sel Cts</th><th>Sel Vlu</th><th>Rej Cts</th><th>Rej Vlu</th>
   </tr>
 
   <tr><td>
   <input type="checkbox" name="cb_pur_<%=purstkIdn%>" id="cb_pur_<%=purstkIdn%>" value="<%=purstkIdn%>" /> 
   <input type="hidden" name="VNM@<%=purstkIdn%>" id="VNM@<%=purstkIdn%>" value="<%=pktDtl.get("vnm")%>" />
   </td> <td><%=pktDtl.get("pur_idn")%> </td><td><%=pktDtl.get("dte")%> </td><td><%=pktDtl.get("vnm")%> </td>
    <%for(int j=0;j<viewPrpList.size();j++){
  String lprp = (String)viewPrpList.get(j);
  %>
  <td><%=pktDtl.get(lprp)%> </td>
  
  <%}%><td><%=pktDtl.get("cts")%> </td><td><%=pktDtl.get("quot")%> </td><td><%=pktDtl.get("vlu")%> </td>
   <td><input type="text" size="5" name="FNLRTE_<%=purstkIdn%>" id="FNLRTE_<%=purstkIdn%>" onchange="changePurVlue(this,'<%=purstkIdn%>')" value="<%=pktDtl.get("quot")%>" />  </td>
  <td><input type="text" size="5" name="SELCTS_<%=purstkIdn%>" readonly="readonly" id="SELCTS_<%=purstkIdn%>"  value="<%=pktDtl.get("cts")%>" />  </td>
 <td><input type="text" size="10" name="SELVLU_<%=purstkIdn%>" readonly="readonly" id="SELVLU_<%=purstkIdn%>"  value="<%=pktDtl.get("vlu")%>"/>  </td>
 <td><input type="text" size="5" name="REJCTS_<%=purstkIdn%>" readonly="readonly" id="REJCTS_<%=purstkIdn%>"  />  </td>
  <td><input type="text" size="10" name="REJVLU_<%=purstkIdn%>" readonly="readonly" id="REJVLU_<%=purstkIdn%>"  />  </td>
   </tr><%
    
    if(ttlDtl!=null && ttlDtl.size() > 0){
     String qty = (String)ttlDtl.get("QTY");
     String cts = (String)ttlDtl.get("CTS");
      String vlu = (String)ttlDtl.get("VLU");
      String avg = (String)ttlDtl.get("AVG");%>
   <tr><td colspan="<%=colspan%>" align="right"><b>Assort Summary</b></td><td><%=cts%></td><td><%=avg%></td><td><%=vlu%></td>
   <td><input type="text" size="5" name="AFNLRTE_<%=purstkIdn%>" readonly="readonly" id="AFNLRTE_<%=purstkIdn%>" value="<%=avg%>"  /> </td>
    <td><input type="text" size="5" name="ASELCTS_<%=purstkIdn%>" readonly="readonly" id="ASELCTS_<%=purstkIdn%>"  value="<%=cts%>"/>  </td>
 <td><input type="text" size="10" name="ASELVLU_<%=purstkIdn%>" readonly="readonly" id="ASELVLU_<%=purstkIdn%>"  value="<%=vlu%>" />  </td><td></td><td></td>
   </tr>
  <%}%>
  </table>
  </td></tr>
  <tr><td>
  <table>
   
  <tr><td>
   <table>
   <% 
    if(ttlDtl!=null && ttlDtl.size() > 0){
    
       ArrayList pktListDtl = (ArrayList)assortDtlList.get(purstkIdn);
    
    %>
  <tr><td>
   <%if(pktListDtl!=null && pktListDtl.size()>0){%>
  <table class="Orange">
  <tr><th class="Orangeth">Select</th><th class="Orangeth">Reject</th> <th class="Orangeth">Packet Code</th><th class="Orangeth">qty</th>
  <%for(int x=0;x<asPrpList.size();x++){
  String lprp = (String)asPrpList.get(x);
   String hdr = (String)mprp.get(lprp);
    String prpDsc = (String)mprp.get(lprp+"D");
    if(hdr == null) {
        hdr = lprp;
       }  
  %>
  <th title="<%=prpDsc%>" class="Orangeth"><%=hdr%></th>
  <%
  if(lprp.equals("RTE")){%>
  <th title="Amount" class="Orangeth">Amount</th>
  <%}
  }%>
 <th  title="Process" class="Orangeth">Next Process</th>
   </tr>
  <%
  for(int y=0; y < pktListDtl.size();y++){
  HashMap pktDtlMap =(HashMap) pktListDtl.get(y);
  
  String stk_idn = (String)pktDtlMap.get("stk_idn");
  String pvnm = (String)pktDtlMap.get("vnm");
  String pqty = (String)pktDtlMap.get("qty");
  String pcts = (String)pktDtlMap.get("cts");
   String pvlu = (String)pktDtlMap.get("vlu");
    String rte = (String)pktDtlMap.get("rte");
    String prcId = "outprc_"+stk_idn;
    String prcFldVal = "value("+prcId+")";
    
  %>
  <tr>
  <td><input type="radio" name="RD@<%=purstkIdn%>@<%=stk_idn%>" id="RDSEL@<%=purstkIdn%>@<%=stk_idn%>" onclick="calculatepurselrej('<%=purstkIdn%>')" checked="checked" value="SEL_<%=stk_idn%>" />
  <input type="hidden" name="CTS@<%=purstkIdn%>@<%=stk_idn%>" id="CTS@<%=purstkIdn%>@<%=stk_idn%>" value="<%=pcts%>" />
   <input type="hidden" name="RTE@<%=purstkIdn%>@<%=stk_idn%>" id="RTE@<%=purstkIdn%>@<%=stk_idn%>" value="<%=rte%>" />
   <input type="hidden" name="VLU@<%=purstkIdn%>@<%=stk_idn%>" id="VLU@<%=purstkIdn%>@<%=stk_idn%>" value="<%=pvlu%>" />
  </td>
  <td><input type="radio" name="RD@<%=purstkIdn%>@<%=stk_idn%>" id="RDREJ@<%=purstkIdn%>@<%=stk_idn%>" onclick="calculatepurselrej('<%=purstkIdn%>')" value="REJ_<%=stk_idn%>" /> </td>
  <td><%=pvnm%></td><td><%=pqty%></td>
   <%for(int x=0;x<asPrpList.size();x++){
  String lprp = (String)asPrpList.get(x);
  %>
  <td><%=pktDtlMap.get(lprp)%></td>
  <%}%>
  <td><%=pvlu%></td>
  <td>
  <html:select property="<%=prcFldVal%>"  styleId="<%=prcId%>" name="purchaseConfrimForm"   >
        
            <html:optionsCollection property="outPrcList" name="purchaseConfrimForm" 
            label="dsc" value="srt" />
    </html:select>
  </td>
  </tr>
  <%}%></table>
  <%}%>
    
  
  <%}else{%>
  <tr><td>Sorry no Assort packets</td></tr>
  <%}%>
  </table>
  </td></tr>
  </table>
  </td></tr>
  
  <%}%>
  
</table>
  <%}else{%>
  Sorry no result found..
  <%}%>
    <%}%>
    </html:form>
  </td></tr>
    <tr>
  <td><jsp:include page="/footer.jsp" /> </td>
  </tr>
  </table>
  </body>
</html>