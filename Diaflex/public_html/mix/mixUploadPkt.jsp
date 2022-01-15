<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import="java.util.ArrayList,java.util.HashMap"%>

<%@taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<jsp:useBean id="info" class="ft.com.InfoMgr" scope="session" />
<jsp:useBean id="util" class="ft.com.JspUtil" scope="page" />

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
        <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/bse.js?v=<%=info.getJsVersion()%> " > </script>

    </head>
    <body>
    <%String noPkt = request.getParameter("noPkt");
    String lotNo = request.getParameter("lotNo");
    String lotIdn = request.getParameter("lotIdn");
    if(noPkt!=null){
    int npkt = Integer.parseInt(noPkt);
     HashMap prp = info.getPrp();
     HashMap mprp = info.getMprp();
    ArrayList asViewPrp = (ArrayList)session.getAttribute("MIX_IN");

    
    %>
    <html:form action="mix/mixManufacturingAction.do?method=save" onsubmit="return confirmManufacturing()" method="post">
    <table cellpadding="2" cellspacing="2">
    <tr><td>
    <table><tr><td>
    <input type="hidden" name="pkt_cnt" id="pkt_cnt" value="<%=noPkt%>" />
     <html:submit property="value(save)" value="Save"  styleClass="submit" /> 
    
      </td><td>
   
<%
    ArrayList rolenmLst=(ArrayList)info.getRoleLst();
  String usrFlg=util.nvl((String)info.getUsrFlg());
     if(usrFlg.equals("SYS") || rolenmLst.contains("ADMIN")){
  %>
  <img src="../images/edit.jpg" title="Edit Properties Module" onclick="newWindow('<%=info.getReqUrl()%>/dynamicProperty.do?method=load&mdl=MIX_IN&sname=MIX_IN&par=VW')" border="0" width="15px" height="15px"/> 
  <%}%></td>
  <td>Selected </td><td> QTY :</td><td><label id="selectQty">0</label> </td>
   <td> Cts:</td><td><label id="selectCts">0</label> </td>
   </tr></table>
    </td></tr>
    <tr><td>
    <table class="grid1">
    <tr>
   
     <%for(int j=0;j<asViewPrp.size();j++){
    String lprp = (String)asViewPrp.get(j);
    if(lprp.equals("CRTWT")){%>
     <th>Qty</th>
    <%}
    
    if(lprp.equals("RTE") || lprp.equals("CRTWT") ){%>
    <th><%=lprp%> <span class="redLabel">*</span> </th>
    <%}else{
    %>
    <th><%=lprp%></th>
    <%}}%>
    </tr>
    <% int cnt=0;
    for(int i=0;i<npkt;i++){
    cnt=i+1;
    
    %>
    <tr>
       <%for(int j=0;j<asViewPrp.size();j++){
    String lprp = (String)asViewPrp.get(j);
    String typ = (String)mprp.get(lprp+"T");
    ArrayList prpLst = (ArrayList)prp.get(lprp+"V");
    String value="";
    String onchange="";
     if(lprp.equals("CRTWT")){
     onchange="lotInwardTotalCts("+cnt+")";
     %>
       <td>
        <input type="text" name="qty_<%=cnt%>" id="qty_<%=cnt%>" value="" onchange="<%=onchange%>" size="5" /> </td>
    <%}
    if(typ.equals("C")){
    if(prpLst!=null && prpLst.size()>0){
    %>
     <td>  <select name="<%=lprp%>_<%=cnt%>" id="<%=lprp%>_<%=cnt%>">
     <%for(int k=0;k<prpLst.size();k++){
     String val = (String)prpLst.get(k);
     %>
     <option value="<%=val%>"><%=val%></option>
     <%}%></select></td>
    <%}}else{%>
    <td>
        <input type="text" name="<%=lprp%>_<%=cnt%>" id="<%=lprp%>_<%=cnt%>" onchange="<%=onchange%>"  value="<%=value%>" size="8" /> </td>
   <%}%>
    
    <%}%>
    
      </tr>
    <%}%></table>
    <input type="hidden" name="count" id="count" value="<%=npkt%>" />
    
    </td></tr></table></html:form>
    <%}else{%>
    Please enter number of packets found.
    <%}%>
    </body>
</html>