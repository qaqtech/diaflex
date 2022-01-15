<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page errorPage="../error_page.jsp" %>
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.ResultSet, java.util.ArrayList,java.util.Collections,java.util.ArrayList,java.util.HashMap,java.text.SimpleDateFormat, java.util.Calendar"%>
<jsp:useBean id="info" class="ft.com.InfoMgr" scope="session" />
<jsp:useBean id="util" class="ft.com.JspUtil" scope="page" />
<%@taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>
<%@taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<html> 
   <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/bse.js?v=<%=info.getJsVersion()%> " > </script>
<link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/bse.css?v=<%=info.getJsVersion()%>"/>
<link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/general.css?v=<%=info.getJsVersion()%>"/>
<script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/ajax.js?v=<%=info.getJsVersion()%> " > </script>
  <script type="text/javascript" src="<%=info.getReqUrl()%>/PopScripts/popup.js?v=<%=info.getJsVersion()%> " > </script>
    <script src="<%=info.getReqUrl()%>/PopScripts/jquery-1.2.6.min.js" type="text/javascript"></script>
   <script language="javaScript" type="text/javascript" src="<%=info.getReqUrl()%>/calendar/calendar.js"></script>

   <link href="<%=info.getReqUrl()%>/calendar/calendar.css" rel="stylesheet" type="text/css" />

 
      <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/style.css?v=<%=info.getJsVersion()%>"/>

  <title>Tender Pkt Entry</title>
 
  </head>

        <%
        HashMap prpList = info.getPrp();
        HashMap mprp = info.getMprp();
        ArrayList vwPrpLst = (ArrayList)session.getAttribute("PLAN_ENTRY");
        
        int loop=Integer.parseInt((String)info.getDmbsInfoLst().get("PLAN_ENTRY_DFLT"));
        String logId=String.valueOf(info.getLogId());
        String onfocus="cook('"+logId+"')";
        ArrayList prpValLst=new ArrayList();
        String savePlan = util.nvl((String)request.getAttribute("savePlan"));
         String stkIdn = util.nvl((String)request.getAttribute("stkIdn"));
          String planSeq = util.nvl((String)request.getAttribute("planSeq"));
          String currloop = util.nvl((String)request.getAttribute("currloop"));
        String onload = "";
        if(savePlan.equals("Y"))
        onload = "parent.SetParentWindow('"+stkIdn+"','"+planSeq+"')";
        %>
 <body onload="<%=onload%>">
  <input type="hidden" id="reqUrl" name="reqUrl" value="<%=info.getReqUrl()%>"/> 
<table width="100%"  border="0" align="center" cellpadding="0" cellspacing="0" id="planningTable">
   <tr>
  <%String msg = (String)request.getAttribute("msg");
  if(msg!=null){
  %>
  <tr><td valign="top" class="tdLayout"> <span class="redLabel" > <%=msg%></span></td></tr>
  <%}%>
  </tr>
<tr><td valign="top">
<html:form action="rough/planingReturnAction.do?method=savePlan"  method="POST">
<html:hidden property="value(stkIdn)" styleId="stkIdn"  name="planingReturnForm"/>
<html:hidden property="value(planSeq)" styleId="planSeq"  name="planingReturnForm"/>
<html:hidden property="value(issueIdn)" styleId="issueIdn"  name="planingReturnForm"/>

<table  class="grid1">
<tr>
<td >
<label>Set Default</label>
</td>
<td colspan="5">
<input type="radio" name="defaultStatus">Yes
<input type="radio" name="defaultStatus" checked="checked" />No
</td>
</tr>
<tr>
<th></th>
<%for (int i=1;i<=loop;i++){
String planId="plan_"+i;
String planFldfld="value(plan_"+i+")";
String planFldId="plan_"+i;
String planMasterFld="value(plan_m_idn_"+i+")";
String planMasterFldId="plan_m_idn_"+i;
String planpktFld="value(plan_pkt_idn_"+i+")";
String planpktFldId="plan_pkt_idn_"+i;
%>
<th title="Select checkBox to save data">Plan <html:checkbox property="<%=planFldfld%>"  name="planingReturnForm" styleId="<%=planFldId%>" value="Y"/>
<html:hidden property="<%=planMasterFld%>" styleId="planMasterFldId"  name="planingReturnForm"/>
<html:hidden property="<%=planpktFld%>" styleId="planpktFldId"  name="planingReturnForm"/>
</th>
<%}%>
</tr>

<tr>
<th><span class="redTxt">*</span>Plan Seq</th>
<%for (int i=1;i<=loop;i++){
String planseqId="plan_seq_"+i;
String planfld="value(plan_seq_"+i+")";
%>
<td><html:text property="<%=planfld%>" name="planingReturnForm" readonly="true" value="<%=planSeq%>" styleId="planseqId" size="10" /></td>
<%}%>
</tr>

<tr>
<th><span class="redTxt">*</span>Sub Plan Seq</th>
<%for (int i=1;i<=loop;i++){
String subplanseqId="sub_plan_seq_"+i;
String subplanfld="value(sub_plan_seq_"+i+")";
String subPlan = String.valueOf(i);
%>
<td><html:text property="<%=subplanfld%>" name="planingReturnForm"  readonly="true" styleId="subplanseqId" value="<%=subPlan%>" size="10"/></td>
<%}%>
</tr>

<%for(int j=0; j < vwPrpLst.size(); j++ ){
    String lprp = (String)vwPrpLst.get(j);
     String hdr = util.nvl((String)mprp.get(lprp));
    String prpDsc = util.nvl((String)mprp.get(lprp+"D"));
    String prpTyp = util.nvl((String)mprp.get(lprp+"T"));
    if(hdr == null) {
        hdr = lprp;
       }  
%>
<tr><th title="<%=prpDsc%>"><%=prpDsc%></th>
  <%
  for (int i=1;i<=loop;i++){
   
    String fldName = "value("+lprp+"_"+i+")";
  if(prpTyp.equals("C")){
    ArrayList lprpValLst =(ArrayList)prpList.get(lprp+"V");%>
  <td><html:select property="<%=fldName%>" styleId="<%=lprp%>"  name="planingReturnForm" >
  <html:option value="0">--select--</html:option>
  <%
  for(int k=0;k<lprpValLst.size();k++){
  String prpVal = (String)lprpValLst.get(k);
  %>
  <html:option value="<%=prpVal%>"><%=prpVal%></html:option>
  <%}%>
  </html:select>
  </td>
  <%}else{%>
   <td><html:text property="<%=fldName%>" size="10"  name="planingReturnForm"/></td>
  <%}}%>
  </tr>
<%}%>  

<tr>
<th>Rmk</th>
<%for (int i=1;i<=loop;i++){
String rmkId="rmk_"+i;
String rmkfld="value(rmk_"+i+")";
%>
<td><html:textarea property="<%=rmkfld%>" name="planingReturnForm" styleId="<%=rmkId%>" cols="16"/></td>
<%}%>
</tr>

<tr>
<th>Rap</th>
<%for (int i=1;i<=loop;i++){
String rap_rteId="rap_rte_"+i;
String rap_rtefld="value(rap_rte_"+i+")";
%>
<td><bean:write property="<%=rap_rtefld%>" name="planingReturnForm"/></td>
<%}%>
</tr>

<tr>
<th>Rte</th>
<%for (int i=1;i<=loop;i++){
String rteId="rte_"+i;
String rtefld="value(rte_"+i+")";
%>
<td><bean:write property="<%=rtefld%>" name="planingReturnForm"/></td>
<%}%>
</tr>

<tr>
<th>Dis</th>
<%for (int i=1;i<=loop;i++){
String disId="dis_"+i;
String disfld="value(dis_"+i+")";
%>
<td><bean:write property="<%=disfld%>" name="planingReturnForm"/></td>
<%}%>
</tr>

<tr>
<th>Vlu</th>
<%for (int i=1;i<=loop;i++){
String vluId="vlu_"+i;
String vlufld="value(vlu_"+i+")";
%>
<td><bean:write property="<%=vlufld%>" name="planingReturnForm"/></td>
<%}%>
</tr>

<tr>
<th>Rap Vlu</th>
<%for (int i=1;i<=loop;i++){
String rapvluId="rap_vlu_"+i;
String rapfld="value(rap_vlu_"+i+")";
%>
<td><bean:write property="<%=rapfld%>" name="planingReturnForm"/></td>
<%}%>
</tr>

<tr><td colspan="<%=loop%>" align="center">
<html:submit property="value(save)" value="Save" onclick="return saveplan();" styleClass="submit"/>
<input type="button" class="submit" onclick="closePlannigIframe(<%=currloop%>)" value="Close">
</td>
</tr>
</table>
</html:form>
</td>
</tr>
  </table>  
       <%@include file="../calendar.jsp"%>
  </body>
</html>