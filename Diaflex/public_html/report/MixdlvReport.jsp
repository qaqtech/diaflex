<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page errorPage="../error_page.jsp" %>
<%@ page contentType="text/html;charset=windows-1252"%>
<jsp:useBean id="info" class="ft.com.InfoMgr" scope="session" />
<jsp:useBean id="util" class="ft.com.JspUtil" scope="page" />
<%@ page import="java.util.ArrayList,java.util.HashMap,java.sql.ResultSet,ft.com.*, java.util.Date, java.util.ArrayList, ft.com.dao.*"%>
<%@taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
 
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>Mix Delivery Report</title>
    <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/bse.css?v=<%=info.getJsVersion()%>"/>
    <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/general.css?v=<%=info.getJsVersion()%>"/>
     <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/style.css?v=<%=info.getJsVersion()%>"/>
      <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/bse.js?v=<%=info.getJsVersion()%> " > </script>
       <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/ajax.js?v=<%=info.getJsVersion()%> " > </script>
  </head>
        <%String logId=String.valueOf(info.getLogId());
        String onfocus="cook('"+logId+"')";
        %>
 <body onfocus="<%=onfocus%>" onload="MM_preloadImages('images/faunalogo.png')" topmargin="0" leftmargin="0" rightmargin="0" bottommargin="0">
<%
  String cnt = util.nvl((String)info.getDmbsInfoLst().get("CNT")).toUpperCase();
  %>

<table width="100%"   align="center" cellpadding="0" cellspacing="0" class="mainbg">
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
  <img src="../images/line.jpg" border="0" /></td><td valign="middle"> <span class="pgHed">Mix Delivery Report</span> </td>
</tr></table>
  </td>
  </tr>
  <tr><td valign="top" class="hedPg">
  
  <input type="hidden" name="cnt" value="<%=cnt%>" id="cnt" >
  <html:form action="report/mixDlvReport.do?method=fetch" method="post" onsubmit="return validate_assort()">
    <table  class="grid1">
      <tr><th colspan="2" align="center">Generic Search 
  <%
  ArrayList rolenmLst=(ArrayList)info.getRoleLst();
  String usrFlg=util.nvl((String)info.getUsrFlg());
  if(usrFlg.equals("SYS") || rolenmLst.contains("ADMIN")){%>
  <img src="../images/edit.jpg" title="Edit Properties Module" onclick="newWindow('<%=info.getReqUrl()%>/dynamicProperty.do?method=load&mdl=MIX_SAL_SRCH&sname=MIXSALSrch&par=GENERIC')" border="0" width="15px" height="15px"/> 
  <%}%>
  </th></tr>
  <tr>
  <td colspan="2">
  <jsp:include page="/genericSrch.jsp"/>
   </td>
  </tr>
  <tr>
  <td colspan="2" align="center">
  Or
  </td>
  </tr>
 <tr>
<td>Packet Id: </td>
<td><html:textarea property="value(vnm)" name="mixdlvReportForm" styleId="vnm"/>
</td>
</tr> 
 <tr>
 <td>Delivery Date</td><td>
 <table><tr>
 <td>
    <html:text property="value(dtefr)" styleClass="txtStyle"  styleId="dtefr"  size="12"  /> <a href="#" style="margin-top:5px;"  onClick="setYears(1900, '<%=info.getCurrentYear()%>' );showCalender(this, 'dtefr');">
                <img src="<%=info.getReqUrl()%>/images/calender.png"></a></td>
               <td>
    <html:text property="value(dteto)" styleClass="txtStyle"  styleId="dteto"  size="12"  /> <a href="#"  onClick="setYears(1900, '<%=info.getCurrentYear()%>' );showCalender(this, 'dteto');">
                <img src="<%=info.getReqUrl()%>/images/calender.png"></a></td></tr></table></td>
 
 </tr>
  
 <tr><td  align="center" colspan="2"><html:submit property="value(srch)" value="Fetch" styleClass="submit" /></td></tr> 
  </table>
    </html:form>
   </td></tr>
   <tr><td valign="top" class="hedPg"></td></tr>
   <%
   ArrayList pktDtlList = (ArrayList)request.getAttribute("PktList");
    ArrayList viewPrpLst = (ArrayList)session.getAttribute("MIX_SL_VW");
    int colspan = 7;
    if(pktDtlList!=null && pktDtlList.size()>0){
       String qty = util.nvl((String)request.getAttribute("qty"));
    String cts = util.nvl((String)request.getAttribute("cts"));
    String vlu = util.nvl((String)request.getAttribute("vlu"));
   %>
   <tr><td valign="top" class="hedPg">
   <table><tr><td>Total:</td>
     <%if(!cnt.equalsIgnoreCase("HK")){%>
   <td>Qty</td><td><%=qty%></td>
   <%}%>
   <td>Carat</td><td><%=cts%></td><td>Value</td><td><%=vlu%></td> </tr></table>
   </td></tr>
  
  <tr><td valign="top" class="hedPg">
  <table class="dataTable" align="left" id="dataTable">
  <thead>
  <tr><th>Packet Code</th><th>Detail</th>
  <%if(!cnt.equalsIgnoreCase("HK")){
  colspan++;
  %>
  <th>qty</th>
  
  <%}%>
  <th style="text-align:right;">Current Cts</th><th style="text-align:right;">Stock Rte</th>
   <%if(!cnt.equalsIgnoreCase("HK")){
   colspan++;
   %>
  <th style="text-align:right;">Sale Qty</th>
  <%}%>
  <th style="text-align:right;">Sale Cts</th><th style="text-align:right;">Avg Sale Rte</th><th style="text-align:right;">Value</th>
  <%for(int i=0 ; i < viewPrpLst.size() ;i++){
  colspan++;
  %>
  <th><%=viewPrpLst.get(i)%></th>
  <%}%>
  </tr>
  </thead>
  <tbody>
  <%for(int j=0 ; j <pktDtlList.size() ;j++){
  HashMap pktDtl = (HashMap)pktDtlList.get(j);
  String stkIdn = (String)pktDtl.get("stkIdn");
  %>
  <tr>
  <td><%=pktDtl.get("vnm")%></td><td>
   <span  class="img"><img src="../images/plus.png" id="IMG_IS_<%=j%>" title="click here for Detail" onclick="MixSaleHis('<%=stkIdn%>','<%=j%>','DLV',this)" border="0"/></span>
       
  </td> <%if(!cnt.equalsIgnoreCase("HK")){%><td style="text-align:right;"><%=pktDtl.get("qty")%></td><%}%><td style="text-align:right;"><%=pktDtl.get("cts")%></td><td style="text-align:right;"><%=pktDtl.get("quot")%></td>
 <%if(!cnt.equalsIgnoreCase("HK")){%><td style="text-align:right;"><%=pktDtl.get("qtySal")%></td><%}%> <td style="text-align:right;"><%=pktDtl.get("salcts")%></td><td style="text-align:right;"><%=pktDtl.get("salrte")%></td><td style="text-align:right;"><%=pktDtl.get("vlu")%></td>
  <%for(int k=0;k<viewPrpLst.size() ;k++){
  String hdr = (String)viewPrpLst.get(k);
  
  %>
  <td><%=util.nvl((String)pktDtl.get(hdr))%></td>
  <%}%>
  </tr>
  <tr><td colspan="<%=colspan%>"><div id="BYR_<%=j%>" align="center" style="display:none;" >
      </div></td> </tr>
  <%}%>
  </tbody>
  </table></td></tr>
  <%}%>
     <tr>
    <td><jsp:include page="/footer.jsp" /> </td>
    </tr>
 </table>
  
    <%@include file="/calendar.jsp"%>
  
  </body>
</html>