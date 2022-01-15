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
    <title>purchasePrp</title>
     <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/bse.css?v=<%=info.getJsVersion()%>"/>
      <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/style.css?v=<%=info.getJsVersion()%>"/>
      <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/memoScript.js?v=<%=info.getJsVersion()%> " > </script>
        <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/bse.js?v=<%=info.getJsVersion()%> " > </script>
     
  </head>
        <%String logId=String.valueOf(info.getLogId());
        String onfocus="cook('"+logId+"')";
        %>
 <body onfocus="<%=onfocus%>">
   <html:form action="rough/roughPurDtlAction.do?method=addPrp" method="post">
    <html:hidden property="value(purIdn)" name="roughPurchaseDtlForm" />
     <html:hidden property="value(purDtlIdn)" name="roughPurchaseDtlForm"/>
  <table>
  <tr><td>
  <html:submit property="value(submit)" value="Update Stock Properties " styleClass="submit"/>
  <%
  ArrayList rolenmLst=(ArrayList)info.getRoleLst();
  String usrFlg=util.nvl((String)info.getUsrFlg());
  if(usrFlg.equals("SYS") || rolenmLst.contains("ADMIN")){
  %>
  &nbsp;&nbsp;<img src="../images/edit.jpg" title="Edit Properties Module" onclick="newWindow('<%=info.getReqUrl()%>/dynamicProperty.do?method=load&mdl=PUR_PRP_UPD&sname=purPrpLst&par=A')" border="0" width="15px" height="15px"/>
  <%}%>  
  </td></tr>
  <%
  String msg = (String)request.getAttribute("msg");
  if(msg!=null){
  %>
  <tr><td><%=msg%></td></tr>
  <%}%>
 <tr><td>
  <%
  ArrayList purPrpList = (ArrayList)session.getAttribute("purPrpLst");
  HashMap mprp = info.getMprp();
  HashMap prp = info.getPrp();
   ArrayList prpDspBlocked = info.getPageblockList();
  if(purPrpList!=null && purPrpList.size() > 0){%>
  <div class="memo">
  <table  cellpadding="0" cellspacing="2">
  
  <tr><td bgcolor="White" valign="top">
  <table border="0" class="Orange" cellspacing="0" cellpadding="0">
  <tr><th class="Orangeth">PROPERTY</th><th class="Orangeth">Value</th></tr>
  <%
    int count=0;
   int srchSize = 12;
   String lprp = "";
   String prpDsc ="";
    String typ ="";
     String  fldName  ="";
  for(int i=0 ; i < purPrpList.size();i++){
   lprp = util.nvl((String)purPrpList.get(i));
   if(prpDspBlocked.contains(lprp)){
  }else{
   prpDsc = util.nvl((String)mprp.get(lprp+"D"));
   typ = util.nvl((String)mprp.get(lprp+"T"));
    fldName = "value("+lprp+")";
   if(count==srchSize){
             count=0;
   %></table></td><td valign="top">
  <table border="0"  width="250"  class="Orange" cellspacing="1" cellpadding="1" >
     <tr><th class="Orangeth">PROPERTY</th><th class="Orangeth" colspan="2">Value</th>
     
     <% }
           count++;
           
  
  if(prpDsc.equals(""))
  prpDsc = lprp;
  %>
  <tr><td><%=prpDsc%> </td>
  <td>
 <% if(typ.equals("C")){
    ArrayList prpVal = (ArrayList)prp.get(lprp+"V");
  
  %>
  <html:select property="<%=fldName%>" name="roughPurchaseDtlForm" >
  <html:option value="">--select--</html:option>
  <%
  for(int j=0;j<prpVal.size();j++){
  String prpPrt = (String)prpVal.get(j);
  %>
  <html:option value="<%=prpPrt%>"><%=prpPrt%></html:option>
  <%}%>
  </html:select>
  <%}else if(typ.equals("T")){%>
   <html:text property="<%=fldName%>" size="10" name="roughPurchaseDtlForm"/>
  <%}else{%>
  <html:text property="<%=fldName%>" size="5" name="roughPurchaseDtlForm"/>
  <%}%>
  
  </td> </tr>
  <%}}%>
  </table></td></tr></table>
  </div>
  <%}%>
 </td></tr></table> </html:form>
  </body>
</html>