<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import="java.util.ArrayList,java.util.ArrayList,java.util.HashMap,ft.com.*, ft.com.dao.* , java.sql.*, java.util.Enumeration, java.util.logging.Level"%>
<jsp:useBean id="info" class="ft.com.InfoMgr" scope="session" />
<jsp:useBean id="util" class="ft.com.JspUtil" scope="page" />
<%@taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>
<%@taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>

<title>Mix Delivery Return</title>
<script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/bse.js?v=<%=info.getJsVersion()%> " > </script>
<link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/bse.css?v=<%=info.getJsVersion()%>"/>
<link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/general.css?v=<%=info.getJsVersion()%>"/>
  <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/memoScript.js?v=<%=info.getJsVersion()%> " > </script>
<script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/box.js?v=<%=info.getJsVersion()%> " > </script>
<link href="<%=info.getReqUrl()%>/calendar/calendar.css" rel="stylesheet" type="text/css" />
<link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/style.css?v=<%=info.getJsVersion()%>"/>
</head>
        <%String logId=String.valueOf(info.getLogId());
        String onfocus="cook('"+logId+"')";
        String pkt_ty =  util.nvl((String)request.getAttribute("PKT_TY"));
        HashMap allPageDtl= (info.getPageDetails() == null)?new HashMap():(HashMap)info.getPageDetails();
        HashMap pageDtl=(HashMap)allPageDtl.get("MIXDELIVERY_CONFIRMATION");
        ArrayList pageList=new ArrayList();
        HashMap pageDtlMap=new HashMap();
        String fld_nme="",fld_ttl="",val_cond="",dflt_val="",fld_typ="",form_nme="",lov_qry="";
        %>
 <body onfocus="<%=onfocus%>" onload="MM_preloadImages('images/faunalogo.png')" topmargin="0" leftmargin="0" rightmargin="0" bottommargin="0">
<% pageList=(ArrayList)pageDtl.get("MEMODTL");
       if(pageList!=null && pageList.size() >0){%>
   <select id="memoDtl" name="memoDtl" style="display:none">
    <%   for(int j=0;j<pageList.size();j++){
            pageDtlMap=(HashMap)pageList.get(j);
            dflt_val=(String)pageDtlMap.get("dflt_val");
           fld_ttl=(String)pageDtlMap.get("fld_ttl");%>
       <option value="<%=dflt_val%>"><%=fld_ttl%></option>     
    <% }%>
    </select>
    <%}else{ %>
      <select id="memoDtl" name="memoDtl" style="display:none">
       <option value="id">Memo Id</option>     
        <option value="dte">Date</option>     
      </select>
    <%}%>
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
  <img src="../images/line.jpg" border="0" /></td><td valign="middle"> <span class="pgHed">Mix Delivery Return</span> </td>
</tr></table>
  </td>
  </tr>
  <%
  String msg = (String)request.getAttribute("msg");
  if(msg!=null){
  %>
  <tr><td valign="top" class="tdLayout"><span class="redLabel" > 
  <%=msg%></span>
  </td></tr>
  <%}%>
  <tr><td valign="top" class="tdLayout">
  <html:form action="mix/mixDeliveryRtn.do?method=view" method="post" onsubmit="return validteDeliveryIdn()" >
  <table cellpadding="0" cellspacing="0" border="0"><tr><td valign="top">
  <html:hidden property="value(salTyp)" name="mixDeliveryReturnForm" />
   <table class="grid1" >
    <tr><th colspan="2">Delivery Search </th>
    </tr>
     <tr> <td>Buyer Name :</td>
     <td>
     <html:select property="byrIdn" name="mixDeliveryReturnForm" onchange="getFinalByrMix(this,'DLV')" styleId="byrId" >
    <html:option value="0">---select---</html:option>
    <html:optionsCollection property="byrLst" name="mixDeliveryReturnForm"  value="byrIdn" label="byrNme" />
                
  </html:select>
     </td>
     </tr>  
       <tr> <td>Billing Party :</td>
       <td>
       <html:select property="prtyIdn" name="mixDeliveryReturnForm" onchange="getSaleTrmsMix(this.value , 'DLV')"  styleId="prtyId"  >
        <html:option value="0">---select---</html:option>    
        </html:select>
       </td>
       </tr> 
        <tr> <td>Terms :</td>
        <td>
        <html:select property="relIdn" name="mixDeliveryReturnForm"  onchange="getSaleIdnMix('DLV')" styleId="rlnId"  >
     <html:option value="0">---select---</html:option>
</html:select>
        </td>
        </tr> 
    <tr> <td>Delivery Ids</td><td><html:text property="value(dlvIdn)" styleId="dlvIdn" name="mixDeliveryReturnForm"/></td> </tr>    
     <tr> <td colspan="2"><html:submit property="value(submit)"  value="View" styleClass="submit"/></td></tr>
   </table>
   </td><td valign="top">
<div id="memoIdn"></div>
</td></tr></table>
   </html:form>
  </td></tr>
  <tr><td valign="top" class="tdLayout" height="10">
  
  </td></tr>
  <html:form action="mix/mixDeliveryRtn.do?method=Return" method="post" onsubmit="return boxConfirm()" >
   
  <% int sr=0;
    String view = util.nvl((String)request.getAttribute("view"));
    if(!view.equals("")){
    ArrayList prps = (session.getAttribute("BOX_SAL_RTN") == null)?new ArrayList():(ArrayList)session.getAttribute("BOX_SAL_RTN");
    ArrayList pktList = (ArrayList)info.getValue("PKTS");
   if(pktList!=null && pktList.size()>0){
%>

  <tr><td valign="top" class="tdLayout" >
   <html:submit property="value(rtn)" value="Return" styleClass="submit"/>
   <%
  ArrayList rolenmLst=(ArrayList)info.getRoleLst();
  String usrFlg=util.nvl((String)info.getUsrFlg());
  if(usrFlg.equals("SYS") || rolenmLst.contains("ADMIN")){
  %>
  &nbsp;&nbsp;<img src="../images/edit.jpg" title="Edit Properties Module" onclick="newWindow('<%=info.getReqUrl()%>/dynamicProperty.do?method=load&mdl=BOX_SAL_RTN&sname=BOX_SAL_RTN&par=A')" border="0" width="15px" height="15px"/>
  <%}%>
   </td></tr>
    <tr><td valign="top" class="tdLayout" height="10">
  
  </td></tr>
  <tr><td valign="top" class="tdLayout">
  <table class="grid1">
<tr>
<th><input type="checkbox" name="checkAll" id="checkAll" onclick="checkALL('CHK_','count')" /></th>
<th>Sr</th><th>Packet Code</th>
<th>qty</th><th>cts</th>

<th>Prc</th>

<th colspan="3">Return</th>
<%for(int j=0; j < prps.size(); j++) {%>
<th><%=(String)prps.get(j)%></th>
<%}%>
</tr>
<tr>
<td colspan="6"></td>

<td><B>Rtn Qty</b></td><td><B>Rtn Cts</b></td><td><B>Rtn Prc</b></td>
<%for(int j=0; j < prps.size(); j++) {%>
<td></td>
<%}%>
</tr>
<%


for(int i=0;i<pktList.size();i++){
sr = sr +1;
PktDtl pktDtl = (PktDtl)pktList.get(i);
long pktIdn = pktDtl.getPktIdn();
String mstkIdn = String.valueOf(pktIdn);
String qtyRtnId = "QR_"+mstkIdn;
String ctsRtnId = "CR_"+mstkIdn;
String qtySalId = "QS_"+mstkIdn;
String ctsSalID = "CS_"+mstkIdn;
String amtSalId = "AS_"+mstkIdn;
String prcSalId = "PS_"+mstkIdn;
String prcRtnId = "PR_"+mstkIdn;
String qtyRtn = "value(QR_"+mstkIdn+")";
String ctsRtn = "value(CR_"+mstkIdn+")";
String prcRtn = "value(PRCR_"+mstkIdn+")";
String qtySal = "value(QS_"+mstkIdn+")";
String ctsSal = "value(CS_"+mstkIdn+")";
String prcSal = "value(PRCS_"+mstkIdn+")";
String checkFld = "value("+mstkIdn+")";
String checkId = "CHK_"+sr;
String onChangeQS = "calculateQty('QS','QR',"+mstkIdn+")";
String onChangeCS = "calculateCts('CS','CR',"+mstkIdn+")";
String onChangeQR = "calculateQty('QR','QS',"+mstkIdn+")";
String onChangeCR = "calculateCts('CR','CS',"+mstkIdn+")";
String onChangeAmt = "calculateAmt("+mstkIdn+")";
%>
<tr>
<td><html:checkbox property="<%=checkFld%>" styleId="<%=checkId%>" value="yes" name="mixDeliveryReturnForm" /> </td>
<td><%=sr%></td>
<td><%=pktDtl.getVnm()%></td>
<td><%=pktDtl.getQty()%>
<input type="hidden" name="qty_<%=mstkIdn%>"  id="qty_<%=mstkIdn%>" value="<%=pktDtl.getQty()%>" /> </td><td><%=pktDtl.getCts()%> <input type="hidden" name="cts_<%=mstkIdn%>"  id="cts_<%=mstkIdn%>" value="<%=pktDtl.getCts()%>" /></td><td><%=pktDtl.getMemoQuot()%>
<%if(!pkt_ty.equals("RGH")){%>
<input type="hidden" name="prc_<%=mstkIdn%>"  id="prc_<%=mstkIdn%>" value="<%=pktDtl.getMemoQuot()%>" />
<%}%>
</td>

<td><html:text property="<%=qtyRtn%>" styleId="<%=qtyRtnId%>" readonly="true" onchange="<%=onChangeQR%>" size="9" styleClass="txtStyle" name="mixDeliveryReturnForm" /></td>
<td><html:text property="<%=ctsRtn%>" size="9" readonly="true" styleId="<%=ctsRtnId%>" onchange="<%=onChangeCR%>" styleClass="txtStyle" name="mixDeliveryReturnForm" /></td>
<td><html:text property="<%=prcRtn%>" size="9" readonly="true" styleClass="txtStyle" name="mixDeliveryReturnForm" /></td>
<%for(int j=0; j < prps.size(); j++) {
String lprp = (String)prps.get(j);
%>
<td><%=util.nvl(pktDtl.getValue((String)prps.get(j)))%></td>
<%}%>
</tr>
<%}%>

</table>

 <input type="hidden" name="count" id="count" value="<%=sr%>" />
  </td></tr>
 
  <%}else{%>
 <tr><td valign="top" class="tdLayout">Sorry result not found.</td></tr>
 <% }}%>
 </html:form>
 
 <tr>
    <td><jsp:include page="/footer.jsp" /> </td>
    </tr>
  </table>
  

  </body>
</html>