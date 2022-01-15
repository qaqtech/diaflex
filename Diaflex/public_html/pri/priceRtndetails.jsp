<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page errorPage="../error_page.jsp" %>
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import="java.util.ArrayList,java.util.HashMap,java.sql.ResultSet,ft.com.*, java.util.Date, java.util.ArrayList, ft.com.dao.*"%>

<jsp:useBean id="util" class="ft.com.JspUtil" scope="page" />
<jsp:useBean id="info" class="ft.com.InfoMgr" scope="session" />
<%@taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>
<%@taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>Details</title>
     <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/bse.js?v=<%=info.getJsVersion()%> " > </script>
    <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/bse.css?v=<%=info.getJsVersion()%>"/>
      <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/style.css?v=<%=info.getJsVersion()%>"/>
      <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/memoScript.js?v=<%=info.getJsVersion()%> " > </script>
        </head>
        <%String logId=String.valueOf(info.getLogId());
        String onfocus="cook('"+logId+"')";
        %>
 <body onfocus="<%=onfocus%>">
  <%
  //util.updAccessLog(request,response,"Price Return Details", "Price Return Details");
   HashMap dataTable = (HashMap)request.getAttribute("dataTable");
   ArrayList pktList = (ArrayList)request.getAttribute("pktList");
   ArrayList vwPrpLst = (ArrayList)session.getAttribute("priDTLViewLst");
   ArrayList prpDspBlocked = info.getPageblockList();
   HashMap mprp = info.getMprp();
   if(dataTable!=null && dataTable.size() >0){
  %>
   
  <table cellpadding="0" cellspacing="0">
  <tr><td valign="top" class="hedPg">  <%
  ArrayList rolenmLst=(ArrayList)info.getRoleLst();
  String usrFlg=util.nvl((String)info.getUsrFlg());
  if(usrFlg.equals("SYS") || rolenmLst.contains("ADMIN")){
  %>
  <img src="../images/edit.jpg" title="Edit Properties Module" onclick="newWindow('<%=info.getReqUrl()%>/dynamicProperty.do?method=load&mdl=PRIRTNDTL_VW&sname=priDTLViewLst&par=V')" border="0" width="15px" height="15px"/> 
  <%}%></td></tr>
  <tr><td>
  <div class="memo">
  <table  width="300" cellpadding="0" cellspacing="2">
  
  <tr><td bgcolor="White">
  <table border="0" class="Orange" cellspacing="1" cellpadding="0" >
          
           <tr><th class="Orangeth" align="center">Stock</th>
           <th class="Orangeth" align="center">Quantity</th> 
           <th class="Orangeth" align="center"  width="85px;">Sale Rate</th> 
           </tr>
           <tr>
           <td>Marketing</td> 
           <td align="center" style="color:blue"><%=dataTable.get("MKT_QTY")%></td> 
           <td align="right"><%=dataTable.get("MKT_AVG")%></td> 
           </tr>
           <tr>
           <td>Sold</td> 
           <td align="center" style="color:red"><%=dataTable.get("SOLD_QTY")%></td> 
           <td align="right"><%=dataTable.get("SOLD_AVG")%></td> 
           </tr>
  </table></td></tr></table>
  </div>
  <div class="memo">
  <table cellpadding="0" cellspacing="2">
  <tr><td>
  <table border="0" class="Orange" cellspacing="1" cellpadding="0" >
           <%if(pktList!=null && pktList.size() >0){%>
           <tr><th class="Orangeth" align="center" nowrap="nowrap">Packet Id</th>
           <th class="Orangeth" align="center" nowrap="nowrap">Buyer</th><th class="Orangeth" align="center" nowrap="nowrap">Date</th><th class="Orangeth" align="center"   nowrap="nowrap" width="85px;">Sale Rate</th>
            <%for(int j=0; j < vwPrpLst.size(); j++ ){
            String prp = (String)vwPrpLst.get(j);
            if(prpDspBlocked.contains(prp)){
            }else{
             String hdr = util.nvl((String)mprp.get(prp));
            String prpDsc = util.nvl((String)mprp.get(prp+"D"));
            if(hdr == null) {
                hdr = prp;
            }%>
<th class="Orangeth" align="center" title="<%=prpDsc%>" nowrap="nowrap"><%=prp%></th>
<%}}%>   
           </tr>
           <%for(int g=0;g < pktList.size();g++){
           HashMap dataDtl=(HashMap)pktList.get(g);
           String status=util.nvl((String)dataDtl.get("STT"));
           String cla="color:blue";
           if(status.equals("SOLD"))
           cla="color:red";
           if(dataDtl!=null){
           %>
           <tr>
           <td align="center"  style="<%=cla%>" nowrap="nowrap"><%=dataDtl.get("VNM")%></td> 
           <td align="center" nowrap="nowrap"><%=dataDtl.get("BUYER")%></td> 
           <td align="center" nowrap="nowrap"><%=dataDtl.get("DTE")%></td> 
           <td align="right"><%=dataDtl.get("AVG")%></td>            
            <%for(int j=0; j < vwPrpLst.size(); j++ ){
                String prp = (String)vwPrpLst.get(j);
                if(prpDspBlocked.contains(prp)){
                }else{
                %>
                <td><%=dataDtl.get(prp)%></td>
            <%}}%>
           </tr>
           <%}}}%>
  </table></td></tr></table>
  </div>
  </td></tr>
  </table>
  
  <%}%>
  
  </body>
</html>