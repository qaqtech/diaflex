<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page errorPage="../error_page.jsp" %>
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import="java.util.ArrayList,java.util.ArrayList, java.util.Iterator, java.sql.Date,java.util.HashMap, ft.com.marketing.PacketLookupForm" %>
<jsp:useBean id="info" class="ft.com.InfoMgr" scope="session" />
<jsp:useBean id="util" class="ft.com.JspUtil" scope="page" />
<%@taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>
<%@taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<html>
      <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>Report Check</title>
  <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/bse.css?v=<%=info.getJsVersion()%>"/>
    <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/style.css?v=<%=info.getJsVersion()%>"/>
    <script type="text/javascript" src="<%=info.getReqUrl()%>/PopScripts/popup.js?v=<%=info.getJsVersion()%> " > </script>
     <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/bse.js?v=<%=info.getJsVersion()%> " > </script>
     <script language="javaScript" type="text/javascript" src="<%=info.getReqUrl()%>/calendar/calendar.js"></script>
   <link href="<%=info.getReqUrl()%>/calendar/calendar.css" rel="stylesheet" type="text/css" />
    <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/general.css?v=<%=info.getJsVersion()%>"/>
    <script src="<%=info.getReqUrl()%>/PopScripts/jquery-1.2.6.min.js" type="text/javascript"></script>
  </head>
        <%String logId=String.valueOf(info.getLogId());
        String onfocus="cook('"+logId+"')";
        %>
 <body onfocus="<%=onfocus%>">
<table width="100%"  border="0" align="center" cellpadding="0" cellspacing="0">
  <%
   ArrayList msgLst = (ArrayList)request.getAttribute("msgList");
   if(msgLst!=null){%>
   <tr><td valign="top" class="tdLayout">
   <%for(int i=0;i<msgLst.size();i++){
   ArrayList msg = (ArrayList)msgLst.get(i);
   %>
    <span class="redLabel"><%=msg.get(1)%>,</span>
   <%}%>
   </td></tr>
   <%}%>

  <% 
  String view= util.nvl((String)request.getAttribute("view"));
  if(!view.equals("")){
  HashMap pktDtl = (session.getAttribute("pktDtl") == null)?new HashMap():(HashMap)session.getAttribute("pktDtl");
  ArrayList pktList = (session.getAttribute("pktlst") == null)?new ArrayList():(ArrayList)session.getAttribute("pktlst");
  HashMap syspktdtl = (session.getAttribute("syspktdtl") == null)?new HashMap():(HashMap)session.getAttribute("syspktdtl");
  ArrayList syscertnoLst = (session.getAttribute("syscertnoLst") == null)?new ArrayList():(ArrayList)session.getAttribute("syscertnoLst");
  ArrayList mappNotFnd = (session.getAttribute("mappNotFnd") == null)?new ArrayList():(ArrayList)session.getAttribute("mappNotFnd");
  ArrayList vwPrpLst = (session.getAttribute("RPT_PKT_VW") == null)?new ArrayList():(ArrayList)session.getAttribute("RPT_PKT_VW");
  if(pktList!=null && pktList.size()>0){
  HashMap dbinfo = info.getDmbsInfoLst();
  String gialink = (String)dbinfo.get("GIA_LINK");
  String igilink = (String)dbinfo.get("IGI_LINK");
  String hrdlink = (String)dbinfo.get("HRD_LINK");
  String hrdauth = (String)dbinfo.get("HRDAUTH");
  String cut = (String)dbinfo.get("CUT");
  int pktlstsz=pktList.size();
  int vwPrpLstsz=vwPrpLst.size();
  HashMap pktPrpMap = new HashMap();
  HashMap mprp = info.getMprp();
  HashMap prp = info.getPrp();
  HashMap pktdtl=new HashMap();
  int sr=0;
   String  reportno="";
  if(syscertnoLst!=null && syscertnoLst.size()>0){
  %>
  <tr><td valign="top" class="hedPg" align="left">
  <%for(int k=0; k < syscertnoLst.size(); k++ ){
  reportno=util.nvl((String)syscertnoLst.get(k));
  if(!pktList.contains(reportno)){
  %>
  <span class="redLabel"><%=reportno%>,</span>
  <%}}%>
  </td></tr>
  <%}%>
    <tr><td valign="top" class="hedPg">
   <html:form action="/masters/utility.do?method=saverpt" method="post" enctype="multipart/form-data">
  <html:hidden property="value(typ)" styleId="typ" />
  <table class="grid1">
  <tr><td valign="top" class="hedPg" colspan="<%=(vwPrpLstsz+10)%>"><html:submit property="value(save)" value="Save" styleClass="submit"/></td></tr>
  <tr>
  <th><input type="checkbox" name="checkAll" id="checkAll" onclick="checkALL('CHK_','count')" /> </th>
  <th>Sr No</th><th>Vnm/Ref Idn</th><th>Cert No</th><th>Lab</th>
  <%for(int j=0; j < vwPrpLstsz; j++ ){
    String lprp = (String)vwPrpLst.get(j);
    String hdr = util.nvl((String)mprp.get(lprp));
    String prpDsc = util.nvl((String)mprp.get(lprp+"D"));
    if(hdr.equals("")) {
        hdr = lprp;
       }
    if(lprp.equals(cut)){%>
    <th>Rate</th><th>Dis</th><th>Rap</th><th>Cmp</th><th>Amt</th>
    <%}%>
<th title="<%=prpDsc%>"><%=hdr%></th>
<%}%> 
  </tr>
    <%for(int i=0;i<pktlstsz;i++){
    sr = i+1;
 reportno=util.nvl((String)pktList.get(i));
 String checkFldId = "CHK_"+sr;
 String checkFldVal = "value("+reportno+")";
   pktPrpMap = new HashMap();
   pktdtl=new HashMap();
  pktPrpMap=(HashMap)pktDtl.get(reportno);
  pktdtl=(HashMap)syspktdtl.get(reportno);
  String lab=util.nvl((String)pktdtl.get("lab"));
  String cts=util.nvl((String)pktPrpMap.get("CRTWT"));
  String link="";
  if(lab.toUpperCase().equals("GIA"))
  link=gialink;
  if(lab.toUpperCase().equals("IGI"))
  link=igilink;
  if(lab.toUpperCase().equals("HRD"))
  link=hrdlink;
       link=link.replaceAll("~AUTH~", hrdauth);
       link=link.replaceAll("~CERTNO~", reportno);
       link=link.replaceAll("~CRTWT~", cts);
  String linkId ="<a href=\'"+link+"'\" target=\"_blank\">"+reportno+"</a>";
  %>
  <tr>
  <td><html:checkbox property="<%=checkFldVal%>" styleId="<%=checkFldId%>" name="atrDtlForm" value="yes" /> </td>
  <td align="right"><%=sr%></td>
  <td align="right"><%=util.nvl((String)pktdtl.get("vnm"))%></td>
  <td align="right" title="Click here to see Certificate"><%=linkId%></td>
  <td align="right"><%=lab%></td>
  <%for(int j=0; j < vwPrpLstsz; j++ ){
    String lprp = (String)vwPrpLst.get(j);
   
    if(lprp.equals(cut)){%>
    <td><%=util.nvl((String)pktdtl.get("rte"))%></td>
     <td><%=util.nvl((String)pktdtl.get("dis"))%></td>
     <td><%=util.nvl((String)pktdtl.get("rap"))%></td>
     <td><%=util.nvl((String)pktdtl.get("cmp"))%></td>
     <td><%=util.nvl((String)pktdtl.get("amt"))%></td>
     <%}
     String val=util.nvl((String)pktPrpMap.get(lprp));%>
     <td align="right" nowrap="nowrap">
     <%if(mappNotFnd.contains(reportno+"-"+lprp)){
     String prpTyp = util.nvl((String)mprp.get(lprp+"T"));
     String  fldName = "value("+lprp+"_"+reportno+")";
     if(prpTyp.equals("C")){
      ArrayList prpValLst =(ArrayList)prp.get(lprp+"V");%>
      <html:select property="<%=fldName%>"  name="atrDtlForm" >
      <html:option value="">--select--</html:option>
      <%
      for(int p=0;p<prpValLst.size();p++){
      String prpVal = (String)prpValLst.get(p);
      %>
      <html:option value="<%=prpVal%>"><%=prpVal%></html:option>
      <%}%>
      </html:select>
      <%}}%>
      <%=val%>
      </td>
      <%}%>
      </tr>
  <%}%>
  <input type="hidden" name="count" id="count" value="<%=sr%>" />
  </table>
  </html:form>  </td></tr>
    <%}else{%>
    <tr>
    <td valign="top" class="hedPg">Sorry No Result Found</td>
    </tr>
    <%}}%>

  </table>
  </body>
</html>