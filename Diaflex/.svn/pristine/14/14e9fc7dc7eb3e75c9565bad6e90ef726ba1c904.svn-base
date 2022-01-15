<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
 
<%@ page errorPage="../error_page.jsp" %>
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import="java.util.ArrayList,java.util.HashMap,java.sql.ResultSet,ft.com.*, java.util.Date, java.util.ArrayList, ft.com.dao.*"%>
<jsp:useBean id="util" class="ft.com.JspUtil" scope="page" />
<jsp:useBean id="info" class="ft.com.InfoMgr" scope="session" />
<%@taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>

<%@taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>Post Shipment</title>
    <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/bse.css?v=<%=info.getJsVersion()%>"/>
      <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/style.css?v=<%=info.getJsVersion()%>"/>
      <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/memoScript.js?v=<%=info.getJsVersion()%> " > </script>
      <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/bse.js?v=<%=info.getJsVersion()%> " > </script>
        <script language="javaScript" type="text/javascript" src="<%=info.getReqUrl()%>/calendar/calendar.js"></script>
        <link href="<%=info.getReqUrl()%>/calendar/calendar.css" rel="stylesheet" type="text/css">
        <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/ajax.js?v=<%=info.getJsVersion()%> " > </script>
        <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/priceChange.js?v=<%=info.getJsVersion()%> " > </script>
        </head>
        <%String logId=String.valueOf(info.getLogId());
        String onfocus="cook('"+logId+"')";
        %>
 <body onfocus="<%=onfocus%>" onload="MM_preloadImages('images/faunalogo.png')" topmargin="0" leftmargin="0" rightmargin="0" bottommargin="0">
 
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
  <img src="../images/line.jpg" border="0" /></td><td valign="middle"> <span class="pgHed">Post Shipment</span> </td>
   </tr></table></td></tr>
    <%
  String msg = (String)request.getAttribute("msg");
  if(msg!=null){
  %>
  <tr><td valign="top" class="hedPg" > <span class="redLabel" ><%=msg%></span></td></tr>
  <%}%>
   <tr>
  <td valign="top" class="tdLayout" >
   <html:form action="/marketing/postShipment.do?method=view" method="POST" >
  <table>
  <tr><td>Packets</td><td><html:textarea property="value(vnm)" cols="30" rows="8" name="postShipmentForm" />  </td></tr>
  <tr><td colspan="2"><html:submit property="value(submit)" value="View" styleClass="submit" /> </td> </tr>
  </table>
 </html:form>
  </td></tr>
  <tr>
  <td valign="top" class="tdLayout" >
  <%
    ArrayList prps = (info.getValue("MDL_MEMO_RTRN") == null)?new ArrayList():(ArrayList)info.getValue("MDL_MEMO_RTRN");
      ArrayList pkts = (ArrayList)session.getAttribute("PKTS"); 
      String view = (String)request.getAttribute("VIEW");
      if(view!=null){
      if(pkts!=null && pkts.size()>0){
   int sr =0;
  %>
    <html:form action="/marketing/postShipment.do?method=save" method="POST" >
    <p><html:submit property="value(submit)" value="Save" styleClass="submit" /></p>
      <table class="grid1">
                <tr>
                    <th>Sr</th>
                    <th><input type="checkbox" name="checkAll" id="checkAll" onclick="checkALL('CHK_','count')" /> Check All</th>
                    <th>dlv Id</th>
                      <th>Packet Code</th>
                     <%for(int j=0; j < prps.size(); j++) {%>
                        <th><%=(String)prps.get(j)%></th>
                    <%}%>
                    <th>RapRte</th>
                    <th>Dis</th>
                    <th>Prc / Crt </th>
                    <th>ByrDis</th>
                    <th>Quot</th>
                       
                </tr>
            <%for(int i=0; i < pkts.size(); i++) {
             sr = i+1;
             PktDtl pkt = (PktDtl)pkts.get(i);
             long pktIdn = pkt.getPktIdn();
             String checkFldId = "CHK_"+sr;
               String checkFldVal = "value("+pktIdn+")";
            %>
                <tr>
                <td><%=sr%></td>
                   <td align="left"><html:checkbox property="<%=checkFldVal%>" styleId="<%=checkFldId%>" name="postShipmentForm"   value="yes" /></td>
          
                <td><%=pkt.getSaleId()%></td>
                 <td><%=pkt.getVnm()%></td>
                <%for(int j=0; j < prps.size(); j++) {
                      String lprp = (String)prps.get(j);
                %>
                <td><%=util.nvl(pkt.getValue((String)prps.get(j)))%></td>
                <%}%>
                <td><%=pkt.getRapRte()%></td>
                <td><%=pkt.getDis()%></td>
                <td><%=pkt.getRte()%>
                
                </td>
                <td><%=pkt.getByrDis()%></td>
                <td><%=pkt.getMemoQuot()%></td>
                               
                </tr>
              <%  
                }
            %>
                <input type="hidden" id="count" value="<%=sr%>" />
            </table></html:form>
            <%}}%>
  </td></tr>
</table></body></html>