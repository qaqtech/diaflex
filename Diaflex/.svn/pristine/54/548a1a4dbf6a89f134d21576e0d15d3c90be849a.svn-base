<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page errorPage="../error_page.jsp" %>
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import="java.util.ArrayList,java.util.ArrayList,java.util.HashMap,java.sql.ResultSet,ft.com.*, java.util.Date, java.net.URLEncoder"%>
<jsp:useBean id="util" class="ft.com.JspUtil" scope="page" />
<jsp:useBean id="info" class="ft.com.InfoMgr" scope="session" />

<%@taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<%@taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<%
%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
     <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/bse.js?v=<%=info.getJsVersion()%> " > </script>
      <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/pri.js?v=<%=info.getJsVersion()%> " > </script>
<link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/bse.css?v=<%=info.getJsVersion()%>"/>
<link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/general.css?v=<%=info.getJsVersion()%>"/>
    <link href="<%=info.getReqUrl()%>/calendar/calendar.css" rel="stylesheet" type="text/css" />

 
      <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/style.css?v=<%=info.getJsVersion()%>"/>
    <title>Buying Average</title>
      <!--     <script type="text/javascript"  src="<%=info.getReqUrl()%>/scripts/bse_prc.js"></script>-->
    
    
  </head>
        <%String logId=String.valueOf(info.getLogId());
        String onfocus="cook('"+logId+"')";
        %>
 <body onfocus="<%=onfocus%>" onload="MM_preloadImages('images/faunalogo.png')" topmargin="0" leftmargin="0" rightmargin="0" bottommargin="0">

<table width="100%"  border="0"  cellpadding="0" cellspacing="0" class="mainbg">
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
  <table cellpadding="1"  cellspacing="5"><tr><td valign="middle">
  <img src="../images/line.jpg" border="0" /></td><td valign="middle"> <span class="pgHed">Buying Average</span> </td>
<td id="loading"></td>
</tr></table>
  </td>
  </tr>
   <!--<tr>
  <td class="tdLayout" valign="top">
  <html:form action="pri/PriceCalc.do?method=fetch" method="POST">
 
    <table cellpadding="5" cellspacing="0" border="0" class="tbl_prc_calc">
      <tr>
        <td><strong>Copy Value From VNM</strong></td>
        <td><html:text property="value(vnm)"  name="priceCalcMultiForm" /></td>
        <td><html:submit property="submit" value="Fecth" styleClass="submit"/></td>
      </tr>
    </table>
 
 </html:form></td></tr>-->
 <tr>
  <td class="tdLayout" valign="top">
  <table cellpadding="5" cellspacing="0" border="0" class="tbl_prc_calc">
      <tr>
        <td><strong>Vendor</strong></td>
        <td><html:text property="value(Vender)" styleId="Vender"  name="priceCalcMultiForm" />
  </td></tr>
  </table>
  </td></tr>
  <html:form action="pri/PriceCalcMulti.do?method=generate" method="POST">
  <!--<html:hidden property="value(stkIdn)" styleId="stkIdn" name="priceCalcMultiForm" />-->
  <%
 // util.updAccessLog(request,response,"Price Calculator Multi", "Price Calculator Multi");
 String view = util.nvl((String)request.getAttribute("view"));
  ArrayList prpCalcList = (ArrayList)session.getAttribute("prpCalcList");
  String vlu = util.nvl((String)request.getAttribute("vlu"));
  String avg = util.nvl((String)request.getAttribute("avg"));
  String Vendervlu = util.nvl((String)request.getAttribute("Vendervlu"));
  String Venderavg = util.nvl((String)request.getAttribute("Venderavg"));
  HashMap mprp = info.getMprp();
  HashMap prp = info.getPrp();
  if(prpCalcList!=null && prpCalcList.size()>0){%>
    <tr>
  <td class="tdLayout" valign="top">
  <html:submit property="value(submit)" styleClass="submit" value="Calculate"  onclick="return validate_priCalMulti('CHK_' , 'count')" />&nbsp;&nbsp;
  <!--<html:submit property="value(reset)" styleClass="submit" value="Reset" />-->

  
  <%if(!vlu.equals("") && !avg.equals("")){%>
  <span><b>Computed : Total Value :</b> <%=vlu%>
  <b>Avg Price : </b><%=avg%></span>
  <span><b>Vender : Total Value : </b> <%=Vendervlu%>
  <b>Avg Price : </b><%=Venderavg%></span>
  <%}%>
  </td>
  </tr>
    <!--<tr>
  <td class="tdLayout" height="30" valign="middle">
  
    <logic:present property="value(vnm)"  name="priceCalcMultiForm" >
     <input type="radio" id="AU" value="AU" checked="checked" name="changes" />Auto&nbsp;&nbsp;&nbsp;
     <input type="radio" id="MN" value="MN"  name="changes" />Manual
    </logic:present>
   </td></tr>-->
  <tr>
  <td class="tdLayout" valign="top">
  <table cellpadding="3px" cellspacing="3px" class="grid1">
  <tr>
  <th>Sr.No</th>
  <th><input type="checkbox" name="checkAll" id="checkAll" onclick="checkALL('CHK_','count')" /> </th>
  <%for(int p=0 ; p < prpCalcList.size() ; p++){%>
  <th><%=prpCalcList.get(p)%></th>
  <%}
  
  %>
   <html:hidden property="value(view)" name="priceCalcMultiForm" value="<%=view%>" />
   <logic:present property="value(view)"  name="priceCalcMultiForm">
  <%
  %>
  <th>Computed Rate</th>
  <th>Value </th>
  <th>Vender Rate</th>
  <th>Value</th>
  </logic:present>
  </tr>
  <%
  int sr=0;
  for(int m=1 ; m <=15 ; m++){
  String fldIdn = "value(Idn_"+m+")";
  String idnId="Idn_"+m;
 String checkFldId = "CHK_"+m;
 String checkFldVal = "value(CHK_"+m+")";
 sr=m;
  %>
  <tr>
  <td><%=m%></td>
  <td><html:checkbox property="<%=checkFldVal%>" styleId="<%=checkFldId%>" name="priceCalcMultiForm"  value="yes" /> </td>
  <html:hidden property="<%=fldIdn%>" styleId="<%=idnId%>" name="priceCalcMultiForm" />
  <%
  int cnt=0;
  String fldValCBtn = "value(cal_"+m+")";
  for(int i=0 ; i < prpCalcList.size() ; i++){
  cnt++;
  String lprp =(String)prpCalcList.get(i);
  String lprpDsc = util.nvl((String)mprp.get(lprp+"D"));
  ArrayList prpLst = (ArrayList)prp.get(lprp+"V");
  ArrayList prpSrt = (ArrayList)prp.get(lprp+"S");
  String fldVal = "value("+lprp+"_"+m+")";
  String onClick = "priCalc('"+lprp+"')";
  String sId=lprp+"_"+m;
  if(lprpDsc.equals(""))
   lprpDsc = lprp;
  %>
  <!--<td><%=lprpDsc%></td>-->
  <td>
  <%if(prpLst!=null && prpLst.size()>0){%>
  <html:select property="<%=fldVal%>" name="priceCalcMultiForm" styleId="<%=sId%>" onchange="<%=onClick%>" style="width:75px" >
  <%
  for(int j=0 ; j< prpLst.size() ; j++){
  String lprpVal = (String)prpLst.get(j);
  String lprpSrt = (String)prpSrt.get(prpLst.indexOf(lprpVal));
  %>
 <html:option value="<%=lprpVal%>" ><%=lprpVal%></html:option>
  <% }%>
 </html:select>
  <%}else{%>
  <html:text property="<%=fldVal%>" name="priceCalcMultiForm" styleId="<%=sId%>" onchange="<%=onClick%>" size="10"/> 
  <%}%>
  </td>
  <% }%>
  <logic:notEqual property="<%=fldIdn%>"  name="priceCalcMultiForm" value="">
  <%String crteVal = "value(rte_"+m+")";
  String crteId="rte_"+m;
  String cvalRte = "value(val_"+m+")";
  String cvalId="val_"+m;
  String cavgVal= "value(avg_"+m+")";
  String cavgId="avg_"+m;
  String vrteVal = "value(vrte_"+m+")";
  String vrteId="vrte_"+m;
  String vvalRte = "value(vval_"+m+")";
  String vvalId="vval_"+m;
  String vavgVal= "value(vavg_"+m+")";
  String vavgId="vavg_"+m;
  %>
  <td><html:text property="<%=crteVal%>" name="priceCalcMultiForm" styleId="<%=crteId%>" onchange="" size="10"/> </td>
  <td><html:text property="<%=cvalRte%>" name="priceCalcMultiForm" styleId="<%=cvalId%>" onchange="" size="10"/> </td>
  <!--<td>Avg :<html:text property="<%=cavgVal%>" name="priceCalcMultiForm" styleId="<%=cavgId%>" onchange="" size="10"/> </td>-->
  <td><html:text property="<%=vrteVal%>" name="priceCalcMultiForm" styleId="<%=vrteId%>" onchange="" size="10"/> </td>
  <td><html:text property="<%=vvalRte%>" name="priceCalcMultiForm" styleId="<%=vvalId%>" onchange="" size="10"/> </td>
  <!--<td>Avg :<html:text property="<%=vavgVal%>" name="priceCalcMultiForm" styleId="<%=vavgId%>" onchange="" size="10"/> </td>-->
  </logic:notEqual>
 
  </tr>
  <%}%>
  <input type="hidden" name="count" id="count" value="<%=sr%>" />
  </table>
  </td></tr>
  <%}%>
  </html:form>
  <!--<tr>
  <td class="tdLayout" valign="top">
  <div id="prcDis">
  <%
   String view =(String)request.getAttribute("view");
  if(view!=null){
  ArrayList pktDtlList = (ArrayList)request.getAttribute("pktDtlList"); 
  
  %>
  <table><tr><td>
  <B><label id="prcStn"> Pricing Details For Packet :- <%=util.nvl((String)request.getAttribute("mstkIdn"))%></label></b>
  </td></tr>
 <% if(pktDtlList!=null && pktDtlList.size()>0){%>
  <tr><td>
  <div id="prcDtl">
  <table>
  <tr><td><b>Calculation Details</b></td>
  <td>Rte : </td><td><%=util.nvl((String)request.getAttribute("cmp"))%></td>
  <td>Dis : </td><td><%=util.nvl((String)request.getAttribute("uprDis"))%></td></tr>
  </table>
  </div>
  </td></tr>
  <tr>
  <td>
  <table class="grid1">
  <tr><th>Grp</th><th>Name</th><th>Pct/Value</th> </tr>
  <%for(int i=0;i<pktDtlList.size();i++){
  HashMap pktDtl = (HashMap)pktDtlList.get(i);
  %>
  <tr>
  <td><%=pktDtl.get("grp")%></td><td><%=pktDtl.get("prmnme")%></td><td><%=pktDtl.get("vlu")%></td>
  </tr>
  <%}%>
  </table>
  </td></tr>
   <%}else{%>
  <tr><td> no data found.</td></tr>
  <%}%>
  </table>
 <%}%>
  </div>
  </td></tr>-->
  </table>
  
  
  
  </body>
</html>