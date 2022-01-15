<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page errorPage="../error_page.jsp" %>
<%@ page contentType="text/html;charset=windows-1252"%>
<jsp:useBean id="info" class="ft.com.InfoMgr" scope="session" />
<jsp:useBean id="util" class="ft.com.JspUtil" scope="page" />

<%@ page import="java.util.ArrayList,java.util.HashMap,java.sql.ResultSet,ft.com.*,java.math.BigDecimal, java.util.Date, java.util.ArrayList, ft.com.dao.*"%>
<%@taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title></title>
     <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/bse.css?v=<%=info.getJsVersion()%>"/>
      <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/style.css?v=<%=info.getJsVersion()%>"/>
      <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/assortScript.js?v=<%=info.getJsVersion()%> " > </script>
       <script type="text/javascript" src="<%=info.getReqUrl()%>/PopScripts/popup.js?v=<%=info.getJsVersion()%> " > </script>
       <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/bse.js?v=<%=info.getJsVersion()%> " > </script>
       <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/ajax.js?v=<%=info.getJsVersion()%> " > </script>
   
  </head>
        <%String logId=String.valueOf(info.getLogId());
        String onfocus="cook('"+logId+"')";
        %>
 <body onfocus="<%=onfocus%>"  onkeypress="return disableEnterKey(event);">
    <% 
      String cnt = util.nvl((String)info.getDmbsInfoLst().get("CNT"));
    ArrayList vwPrpLst = (ArrayList)session.getAttribute("NewPktViewLst");
     String rowCnt = util.nvl((String)request.getParameter("COUNT"));
     if(rowCnt.equals(""))
     rowCnt="10";
     int rowcount = Integer.parseInt(rowCnt);
      String view = (String)request.getAttribute("view");
      HashMap mprp = info.getMprp();
      HashMap prp = info.getPrp();
      String onchange="";

    %>
  <html:form action="mix/mixToSingle.do?method=genPkt" method="post" onsubmit="return validatecarets();">
   
  <html:hidden property="value(mstkIdn)" name="mixToSingleForm" styleId="mstkIdn" />
  <html:hidden property="value(cts)" name="mixToSingleForm" styleId="cts" />
    <html:hidden property="value(qty)" name="mixToSingleForm" styleId="qty" />
  <html:hidden property="value(rowcount)" name="mixToSingleForm" styleId="COUNT" value="<%=rowCnt%>" />
 <table>
  <%String msg = (String)request.getAttribute("msg"); 
 if(msg!=null){
 %>
    <tr><td valign="top" class="tdLayout"><span class="redLabel"><%=util.nvl(msg)%></span></td></tr>
 <%}
 if(view!=null){%>
 <tr>
 <td valign="top" class="hedPg">&nbsp;<span class="txtLink" > <bean:write property="value(vnm)" name="mixToSingleForm" /></span>&nbsp;
 &nbsp;<span class="txtLink" > Available Carets :<label id="TTLCTSMT"> <bean:write property="value(cts)" name="mixToSingleForm"/> </label></span>&nbsp;
  &nbsp;<span class="txtLink" > Split Carets :<label id="addcts"> 0</label></span>&nbsp;
   &nbsp;<span class="txtLink" > Balance Carets :<label id="balcts"> 0 </label></span>&nbsp;
 &nbsp;&nbsp;&nbsp;<html:submit property="value(save)" styleClass="submit"   value="Generate"  />&nbsp;&nbsp;&nbsp;  </td>
 </td></tr>
 <%if(msg==null){%>
 <tr><td>
 <select name="prpList" id="prpList" style="display:none">
 <%for(int j=0; j < vwPrpLst.size(); j++ ){
 String lprp = (String)vwPrpLst.get(j);
 %>
 <option value="<%=lprp%>"></option>
 <%}%>
 </select>
 
 <div class="memo">
 <table border="0" width="200" class="Orange"  cellspacing="1" cellpadding="1" >
 <tr><th class="Orangeth">Packet Id</th>
 <%
 for(int j=0; j < vwPrpLst.size(); j++ ){
    String lprp = (String)vwPrpLst.get(j);
    ArrayList prpVal = (ArrayList)prp.get(lprp+"V");
    ArrayList prpPrt = (ArrayList)prp.get(lprp+"P");
    String prpDsc = (String)mprp.get(lprp+"D");
    String prpTyp = util.nvl((String)mprp.get(lprp+"T"));
    String prpFld = "value("+lprp+")";
    String chksrvAll = "chksrvAllForm0('"+lprp+"')";
     String chksrvTxtAll = "chksrvTXTAllForm0('"+lprp+"')";
    String selectALL = lprp;
  if(lprp.equals("CRTWT"))
  chksrvTxtAll=";calttlCRTWT(this)";
    %>
    
    <th class="Orangeth">
    <%=prpDsc%><br>
    <%if(prpTyp.equals("C")){%>
     <html:select  property="<%=prpFld%>" styleId="<%=selectALL%>" name="mixToSingleForm" onchange="<%=chksrvAll%>"  style="width:100px"   > 
      <html:option value="0">--select--</html:option>
            <%
            for(int k=0; k < prpVal.size(); k++) {
                String pPrt = (String)prpVal.get(k);
             %>
              <html:option value="<%=pPrt%>" ><%=prpPrt.get(k)%></html:option> 
            <%}%>
     </html:select>  
     <%}else{%>
     <html:text property="<%=prpFld%>" styleId="<%=selectALL%>" name="mixToSingleForm"  style="width:100px" onchange="<%=chksrvTxtAll%>" />
     <%}%>
     </th>
<%}%>       
 </tr>
<%
for(int z=0;z<rowcount;z++){
String fldNameVnm = "value(VNM_"+z+")";
String styleIdVnm = "VNM_"+z;
String fldNameCts = "value(CTS_"+z+")";
String styleIdCts = "CTS_"+z;
String fldNamePkt = "value(PKT_"+z+")";
String styleIdPkt = "PKT_"+z;
String fldNamePktidn = "value(PKTIDN_"+z+")";
String styleIdPktidn = "PKTIDN_"+z;
String fldNameStt = "value(STT_"+z+")";
String styleIdStt = "STT_"+z;
onchange="verifyVnm('"+styleIdVnm+"','"+styleIdPkt+"','"+z+"')";
String pktVal="Y";


%>
<tr>
<td>
<html:text property="<%=fldNameVnm%>"  styleId="<%=styleIdVnm%>" size="10" onchange="<%=onchange%>" name="mixToSingleForm"/>
<html:hidden property="<%=fldNamePkt%>"  styleId="<%=styleIdPkt%>" name="mixToSingleForm" value="<%=pktVal%>" />
<html:hidden property="<%=fldNameStt%>"  styleId="<%=styleIdStt%>" name="mixToSingleForm" />
<html:hidden property="<%=fldNamePktidn%>"  styleId="<%=styleIdPktidn%>" name="mixToSingleForm" />
</td>
<%
for(int i=0;i<vwPrpLst.size();i++){
  String lprp = (String)vwPrpLst.get(i);
  String prpDsc = (String)mprp.get(lprp);
  String prpTyp = util.nvl((String)mprp.get(lprp+"T"));
  String fldName = "value("+lprp+"_"+z+")";
  String styleId=lprp+"_"+z;
   String onChange="";
  if(lprp.equals("CRTWT"))
  onChange="calttlCRTWT(this)";
  %>
  <td>
  <%if(prpTyp.equals("C")){
    ArrayList prpValLst =(ArrayList)prp.get(lprp+"V");%>
  <html:select property="<%=fldName%>" styleId="<%=styleId%>"  name="mixToSingleForm" >
  <html:option value="0">--select--</html:option>
  <%
  for(int j=0;j<prpValLst.size();j++){
  String prpVal = (String)prpValLst.get(j);
  %>
  <html:option value="<%=prpVal%>"><%=prpVal%></html:option>
  <%}%>
  </html:select>
  <%}else{%>
  <html:text property="<%=fldName%>" onchange="<%=onChange%>"  styleId="<%=styleId%>" size="5"  name="mixToSingleForm"/>
  <%}%>
  </td>
  <%}%>
  </tr>
  <%}%>
 </table>
 </div>
 </td></tr>
 <%}}%>
 </table>
  </html:form>
  </body>
</html>