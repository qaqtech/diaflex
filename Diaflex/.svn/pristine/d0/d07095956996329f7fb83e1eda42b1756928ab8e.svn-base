 <!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
 
<%@ page errorPage="../error_page.jsp" %>
<%@ page contentType="text/html;charset=windows-1252"%>
<jsp:useBean id="util" class="ft.com.JspUtil" scope="page" />
<jsp:useBean id="info" class="ft.com.InfoMgr" scope="session" />
<%@ page import="java.util.ArrayList,java.util.HashMap,java.sql.ResultSet,ft.com.*, java.util.Date, java.util.ArrayList, ft.com.dao.*"%>
<%@taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>Stock Tally</title>
    <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/bse.css?v=<%=info.getJsVersion()%>"/>
    <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/general.css?v=<%=info.getJsVersion()%>"/>
     <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/style.css?v=<%=info.getJsVersion()%>"/>
      <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/bse.js?v=<%=info.getJsVersion()%> " > </script>
    <script language="javaScript" type="text/javascript" src="<%=info.getReqUrl()%>/calendar/calendar.js"></script>
   <link href="<%=info.getReqUrl()%>/calendar/calendar.css" rel="stylesheet" type="text/css">
  <script type="text/javascript" src="<%=info.getReqUrl()%>/chartscript/amcharts.js?v=<%=info.getJsVersion()%> " > </script>
<script type="text/javascript" src="<%=info.getReqUrl()%>/chartscript/chartajax.js?v=<%=info.getJsVersion()%> " > </script>
<script type="text/javascript" src="<%=info.getReqUrl()%>/chartscript/serial.js?v=<%=info.getJsVersion()%> " > </script>
      <script src="<%=info.getReqUrl()%>/PopScripts/jquery-1.2.6.min.js" type="text/javascript"></script>
  </head>
        <%
    HashMap allPageDtl= (info.getPageDetails() == null)?new HashMap():(HashMap)info.getPageDetails();
    HashMap pageDtl=(HashMap)allPageDtl.get("STOCK_TALLY");
    ArrayList pageList=new ArrayList();
    HashMap pageDtlMap=new HashMap();
    String fld_nme="",fld_ttl="",val_cond="",dflt_val="",fld_typ="",form_nme="",flg1="",lov_qry="";
        ArrayList seqList = (ArrayList)session.getAttribute("seqLst");
        String logId=String.valueOf(info.getLogId());
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
  <img src="../images/line.jpg" border="0" /></td><td valign="middle"> <span class="pgHed">Stock Tally History</span> </td>
</tr></table>
  </td>
  </tr>
  <%
  if(request.getAttribute("msg")!=null){%>
   <tr> <td valign="top" class="tdLayout"><%=request.getAttribute("msg")%></td></tr>
  <%}%>
   <tr> <td valign="top" class="tdLayout">
   <table><tr><td>
   <html:form action="marketing/newStockTally.do?method=history" method="post" >
  <table  class="grid1">
  <tr><th colspan="2">Search</th></tr>
  <tr><td>Sequence Id</td><td><html:text property="value(seq)" styleId="seq" name="stockTallyForm" size="8" /></td></tr>
  <tr><td>Status</td><td align="left">
  <%  ArrayList sttList = (ArrayList)session.getAttribute("sttLst");
  if(sttList!=null && sttList.size()>0){
  %>
  <html:select property="sttValLst" name="stockTallyForm" onchange="" multiple="true" style="height:80px;" size="4">
 <html:option value="" >All</html:option>
 <% for(int n=0;n<sttList.size();n++){
  ArrayList sttDtl = (ArrayList)sttList.get(n);
  String grp1 = (String)sttDtl.get(0);
  String dsc = (String)sttDtl.get(1);
   %>
   <html:option value="<%=grp1%>" ><%=dsc%></html:option>
  <%}%>
 </html:select>
  <%}%>
  </td> </tr>
      <tr><td>Issue Date : </td>  
       <td><html:text property="value(issdte)" styleId="issdte" name="stockTallyForm" size="15" /><img src="<%=info.getReqUrl()%>/images/calender.png" onClick="setYears(2011, <%=info.getCurrentYear()%>);showCalender(this, 'issdte');"> 
       </td>
      </tr>
          <tr><td>Return Date : </td>  
       <td><html:text property="value(rtndte)" styleId="rtndte" name="stockTallyForm" size="15" /><img src="<%=info.getReqUrl()%>/images/calender.png" onClick="setYears(2011, <%=info.getCurrentYear()%>);showCalender(this, 'rtndte');"> 
       </td>
      </tr>
   <tr><td colspan="2" align="center"><html:submit property="value(srch)" value="Fetch" styleClass="submit" /></td> </tr>
   </table>
   </html:form>
   </td></tr>
   
   </table>
  </td></tr>
  <tr> <td valign="top" class="tdLayout" height="20px">
  
  </td></tr>
  
  <tr> <td valign="top" class="tdLayout" >
  <table>
  <%
  String view = util.nvl((String)request.getAttribute("view"));
  HashMap pktDtl = (HashMap)session.getAttribute("pktDtl");
  ArrayList grpList = (ArrayList)session.getAttribute("grpList");
  HashMap grp3list = (HashMap)session.getAttribute("grp3List");
  %>
  <tr><td valign="top">
  <%if(!view.equals("")){%>
  <%if(pktDtl!=null && pktDtl.size() > 0){
  String loadSeq = util.nvl((String)request.getAttribute("seqNo"));
  String issdte = util.nvl((String)request.getAttribute("issdte"));
  String rtndte = util.nvl((String)request.getAttribute("rtndte"));
  %>
  <table><tr><td>
  <html:form action="marketing/newStockTally.do?method=history" method="post" >
  Select Sequence :
 <html:select property="value(seq)" name="stockTallyForm" onchange="SubmitForm('1')">
 <html:option value="0" >---select---</html:option>
  <%for(int i=0 ; i < seqList.size();i++){
  String seq = (String)seqList.get(i);
 
  %>
  <html:option value="<%=seq%>"><%=seq%></html:option>
  <%}%>
  </html:select>
  </html:form>
  </td></tr>
  <tr><td valign="top">
  <table class="grid1"><tr><th>Status</th>
  <%
  pageList=(ArrayList)pageDtl.get("TTLPRP");
  if(pageList!=null && pageList.size() >0){
      for(int j=0;j<pageList.size();j++){
      pageDtlMap=(HashMap)pageList.get(j);
      String fldNme = (String)pageDtlMap.get("fld_nme");
    %>
    <th><%=fldNme%></th>
  <%}}%>
  <th colspan="2">Total </th><th colspan="2">Issue </th><th colspan="2">Return </th></tr>
  <tr><td></td>
     <%
  pageList=(ArrayList)pageDtl.get("TTLPRP");
  if(pageList!=null && pageList.size() >0){%><td></td><%}%>
  <td><b>Qty </b></td><td><b>Cts</b></td><td><b>Qty</b></td><td><b>Cts</b></td><td><b>Qty</b></td><td><b>Cts</b></td></tr>
  <%
  for(int i=0;i<grpList.size();i++){
  String mstkStt = (String)grpList.get(i);
  String issQ = util.nvl((String)pktDtl.get(mstkStt+"_IS_Q"));
  String issC = util.nvl((String)pktDtl.get(mstkStt+"_IS_C"));
  String rtQ = util.nvl((String)pktDtl.get(mstkStt+"_RT_Q"));
  String rtC = util.nvl((String)pktDtl.get(mstkStt+"_RT_C"));
 
  String  ttlQ ="";
  if(issQ.length()>0 && rtQ.length()>0){
  ttlQ = String.valueOf((Integer.parseInt(issQ) +Integer.parseInt(rtQ)));
  }else if(issQ.length()>0){
   ttlQ = String.valueOf(Integer.parseInt(issQ));
  }else if(rtQ.length()>0){
   ttlQ = String.valueOf(Integer.parseInt(rtQ));
  }
  String  ttlC ="";
  if(issC.length()>0 && rtC.length()>0){
  ttlC = String.valueOf(Float.valueOf(issC) + Float.valueOf(rtC));
  }else if(issC.length()>0){
   ttlC = String.valueOf(Float.valueOf(issC));
  }else  if(rtC.length()>0){
    ttlC = String.valueOf(Float.valueOf(rtC));
  }
  %>
   <tr><td><b><A href="<%=info.getReqUrl()%>/marketing/newStockTally.do?method=TallyCRTHistory&stt=<%=mstkStt%>&loadSeq=<%=loadSeq%>&issdte=<%=issdte%>&rtndte=<%=rtndte%>" onclick="displayover('pktDtltd')"  target="pktDtl"><%=util.nvl((String)grp3list.get(mstkStt))%></a> </b> </td>
    <%
  pageList=(ArrayList)pageDtl.get("TTLPRP");
  if(pageList!=null && pageList.size() >0){
      for(int j=0;j<pageList.size();j++){
      pageDtlMap=(HashMap)pageList.get(j);
      String fldNme = (String)pageDtlMap.get("fld_nme");
      dflt_val = (String)pageDtlMap.get("dflt_val");
    %>
    <td><A href="<%=info.getReqUrl()%>/marketing/newStockTally.do?method=TallyCRT&stt=<%=mstkStt%>&loadSeq=<%=loadSeq%>&prp=<%=dflt_val%>" onclick="displayover('pktDtltd')" target="pktDtl">
    <%=fldNme%></a> </td>
  <%}}%>
   <td><A href="<%=info.getReqUrl()%>/marketing/newStockTally.do?method=PktDtlHistory&dfstt=<%=mstkStt%>&issTyp=ALL&loadSeq=<%=loadSeq%>&issdte=<%=issdte%>&rtndte=<%=rtndte%>" target="_blank"><%=ttlQ%> </a></td>
   <td><%=ttlC%></td>
   <td><A href="<%=info.getReqUrl()%>/marketing/newStockTally.do?method=PktDtlHistory&dfstt=<%=mstkStt%>&issTyp=IS&loadSeq=<%=loadSeq%>&issdte=<%=issdte%>&rtndte=<%=rtndte%>" target="_blank"><%=util.nvl((String)pktDtl.get(mstkStt+"_IS_Q"))%></a></td>
   <td><%=util.nvl((String)pktDtl.get(mstkStt+"_IS_C"))%></td>
   <td><A href="<%=info.getReqUrl()%>/marketing/newStockTally.do?method=PktDtlHistory&dfstt=<%=mstkStt%>&issTyp=RT&loadSeq=<%=loadSeq%>&issdte=<%=issdte%>&rtndte=<%=rtndte%>" target="_blank"><%=util.nvl((String)pktDtl.get(mstkStt+"_RT_Q"))%></a></td>
   <td><%=util.nvl((String)pktDtl.get(mstkStt+"_RT_C"))%></td>
   </tr>
  <%}%>
  </table></td>
  </tr>
  <tr>
  <td valign="top" style="display:none;" id="pktDtltd">
   <iframe name="pktDtl" width="700"  height="700px" frameborder="0">

   </iframe>
  </td>
  </tr>
  </table>
  <%}else{%>
  Sorry No Result Found
  <%}}%>
  </td></tr></table>
  
  
  </td></tr>
   
  
  </table>
    <input type="hidden" id="barGrp" value="Total_Issue_Return">
  <div style="margin:0px; padding:0px;">
  <input type="hidden" id="TALLY_HIDD" value="TALLY">
  <input type="hidden" id="TALLY_TTL" value="Stock">
  <div id="TALLY" style="width: 100%; height: 400px; float:left; margin:0px; padding:0px;">
  </div>
  </div>
<%
pageList=(ArrayList)pageDtl.get("HISGRAPH");
if(pageList!=null && pageList.size() >0){
if(seqList!=null && seqList.size()>0){
String url="ajaxRptAction.do?method=stockTally";%>
<script type="text/javascript">
<!--
$(window).bind("load", function() {
callcreatedoubleBarGraph('<%=url%>','Tally History','50','362');
});
 -->
</script>  
<%}}%>
  </body>
  <%@include file="../calendar.jsp"%>
</html>