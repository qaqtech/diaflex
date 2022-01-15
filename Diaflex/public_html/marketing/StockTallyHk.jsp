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
  <script type="text/javascript" src="<%=info.getReqUrl()%>/chartscript/amcharts.js?v=<%=info.getJsVersion()%> " > </script>
<script type="text/javascript" src="<%=info.getReqUrl()%>/chartscript/chartajax.js?v=<%=info.getJsVersion()%> " > </script>
<script type="text/javascript" src="<%=info.getReqUrl()%>/chartscript/serial.js?v=<%=info.getJsVersion()%> " > </script>
  </head>
        <%
        ArrayList seqList = (ArrayList)session.getAttribute("seqLst");
        String logId=String.valueOf(info.getLogId());
        String onfocus="cook('"+logId+"')";
    HashMap allPageDtl= (info.getPageDetails() == null)?new HashMap():(HashMap)info.getPageDetails();
    HashMap pageDtl=(HashMap)allPageDtl.get("STOCK_TALLY");
    ArrayList pageList=new ArrayList();
    HashMap pageDtlMap=new HashMap();
    String fld_nme="",fld_ttl="",val_cond="",dflt_val="",fld_typ="",form_nme="",flg1="",lov_qry="";
        %>
 <body onfocus="<%=onfocus%>" onload="MM_preloadImages('images/faunalogo.png')" topmargin="0" leftmargin="0" rightmargin="0" bottommargin="0">
<div id="backgroundPopup"></div>
<div id="popupContactASSFNL">
<table class="popUpTb">
 <tr><td height="5%" id="close">&nbsp;&nbsp;&nbsp;<html:button property="value(close)" styleClass="submit"  onclick="MKEmptyPopup('SC');disablePopupASSFNL()" value="Close"  /> </td>
  <td height="5%" id="closereload" style="display:none">&nbsp;&nbsp;&nbsp;<html:button property="value(close)" styleClass="submit"  onclick="MKEmptyPopup('SC');disablePopupASSFNL();reportUploadclose('TALLYHK')" value="Close"  /> </td></tr>
<tr><td valign="top" height="95%">
<iframe name="SC" class="frameStyle"   align="middle" frameborder="0">

</iframe></td></tr>
</table>
</div>
<input type="hidden" id="reqUrl" name="reqUrl" value="<%=info.getReqUrl()%>"/>
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
  <img src="../images/line.jpg" border="0" /></td><td valign="middle"> <span class="pgHed">Stock Tally</span> </td>
</tr></table>
  </td>
  </tr>
  <%
  if(request.getAttribute("msg")!=null){%>
   <tr> <td valign="top" class="tdLayout"><%=request.getAttribute("msg")%></td></tr>
  <%}%>
    <%if(request.getAttribute("seqMsg")!=null){
    if(seqList!=null && seqList.size()>0){%>
   <tr> <td valign="top" class="tdLayout"><span class="redLabel"> Sequence No :<%=seqList.get(0)%> </span></td></tr>
  <%}}%>
   <tr> <td valign="top" class="tdLayout">
   <table><tr><td>
   <html:form action="marketing/stockTallyhk.do?method=fetch" method="post" >
  <table  class="grid1">
  <tr><th colspan="2">Generic Search 
  <%
  ArrayList rolenmLst=(ArrayList)info.getRoleLst();
  String usrFlg=util.nvl((String)info.getUsrFlg());
  if(usrFlg.equals("SYS") || rolenmLst.contains("ADMIN")){
  %>
  <img src="../images/edit.jpg" title="Edit Properties Module" onclick="newWindow('<%=info.getReqUrl()%>/dynamicProperty.do?method=load&mdl=STK_TLLY_SRCH&sname=StkTllySrch&par=GENERIC')" border="0" width="15px" height="15px"/> 
  <%}%>
  </th></tr>
  <tr><td align="center">Status</td><td align="center">
  <%  ArrayList sttList = (ArrayList)session.getAttribute("sttLst");
  if(sttList!=null && sttList.size()>0){
  
  %>
  <html:select property="sttValLst" name="stockTallyForm" onchange="" multiple="true" style=" height:100px;" size="4">
 <% for(int n=0;n<sttList.size();n++){
  ArrayList sttDtl = (ArrayList)sttList.get(n);
  String grp1 = (String)sttDtl.get(0);
  String dsc = (String)sttDtl.get(1);
   %>
   <html:option value="<%=dsc%>" ><%=dsc%></html:option>
  <%}%>
 </html:select>
  <%}%>
  </td> </tr>
  <tr> <td colspan="2"> <jsp:include page="/genericSrch.jsp"/>
   </td></tr>
   <tr><td  align="right"><html:submit property="value(srch)" value="Issue Stock Tally" styleClass="submit" /></td> 
   <td  align="left"><html:submit property="value(issuehk)" value="Issue Stock Tally (HK)" styleClass="submit" /></td> 
   </tr>
   </table>
   </html:form>
   </td></tr>
   
   </table>
  </td></tr>
  <tr> <td valign="top" class="tdLayout" height="20px">
  
  </td></tr>
  
  <tr> <td valign="top" class="tdLayout" >
  <table id="dataTable">
  <%
  HashMap pktDtl = (HashMap)session.getAttribute("pktDtl");
  ArrayList grpList = (ArrayList)session.getAttribute("grpList");
 
  HashMap grp3list = (HashMap)session.getAttribute("grp3List");
  String target = "SC_0";%>
  <tr id="<%=target%>"><td valign="top">
  <%if(seqList!=null && seqList.size() > 0){
  String loadSeq = util.nvl((String)request.getAttribute("seqNo"));
  %>
  <table><tr><td nowrap="nowrap"><table><tr><td>
  <html:form action="marketing/stockTallyhk.do?method=fetchSeq" method="post" >
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
  </td>
  <%
    pageList= ((ArrayList)pageDtl.get("HISDAYS") == null)?new ArrayList():(ArrayList)pageDtl.get("HISDAYS");   
  if(pageList!=null && pageList.size() >0){
      for(int j=0;j<pageList.size();j++){
      pageDtlMap=(HashMap)pageList.get(j);
      fld_ttl=(String)pageDtlMap.get("fld_ttl");
  %>
  <td>&nbsp;&nbsp;<a href="<%=info.getReqUrl()%>/marketing/stockTallyhk.do?method=dayWisehistory&reqstt=ALL" id="LNK_0" onclick="loadASSFNL('SC_0','LNK_0')"  target="SC"><%=fld_ttl%></a></td>
  <%}}%>
    <%pageList=(ArrayList)pageDtl.get("DELETE");
  if(pageList!=null && pageList.size() >0){
      for(int j=0;j<pageList.size();j++){
      pageDtlMap=(HashMap)pageList.get(j);
      fld_ttl=(String)pageDtlMap.get("fld_ttl");
  %>
  <td><input type="button" id="LNK_1" class="submit" onclick="purlab('LNK_1','SC','TALLYHK');"  value="Delete Sequence"/></td>
  <%}}%>
  </tr>
  </table>
  </td>
  </tr>
  <tr><td valign="top">
  <table class="grid1">
  <tr><th>Status</th><th colspan="2">Total </th><th colspan="2">Issue </th><th colspan="2">Return </th></tr>
  <tr><td></td><td><b>Qty </b></td><td><b>Cts</b></td><td><b>Qty</b></td><td><b>Cts</b></td><td><b>Qty</b></td><td><b>Cts</b></td></tr>
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
   <tr><td><b><A href="<%=info.getReqUrl()%>/marketing/stockTallyhk.do?method=TallyCRT&stt=<%=mstkStt%>&loadSeq=<%=loadSeq%>" onclick="displayover('pktDtltd')" target="pktDtl"><%=util.nvl((String)grp3list.get(mstkStt),mstkStt)%></a> </b> </td>
  
   <td><A href="<%=info.getReqUrl()%>/marketing/stockTallyhk.do?method=PktDtl&dfstt=<%=mstkStt%>&issTyp=ALL&loadSeq=<%=loadSeq%>" target="_blank"><%=ttlQ%> </a></td>
   <td><%=ttlC%></td>
   <td><A href="<%=info.getReqUrl()%>/marketing/stockTallyhk.do?method=PktDtl&dfstt=<%=mstkStt%>&issTyp=IS&loadSeq=<%=loadSeq%>" target="_blank"><%=util.nvl((String)pktDtl.get(mstkStt+"_IS_Q"))%></a></td>
   <td><%=util.nvl((String)pktDtl.get(mstkStt+"_IS_C"))%></td>
   <td><A href="<%=info.getReqUrl()%>/marketing/stockTallyhk.do?method=PktDtl&dfstt=<%=mstkStt%>&issTyp=RT&loadSeq=<%=loadSeq%>" target="_blank"><%=util.nvl((String)pktDtl.get(mstkStt+"_RT_Q"))%></a></td>
   <td><%=util.nvl((String)pktDtl.get(mstkStt+"_RT_C"))%></td>
   </tr>
  <%}%>
  </table></td>
  </tr>
  <tr>
  <td valign="top" style="display:none;" id="pktDtltd">
   <iframe name="pktDtl" width="500" style="height:inherit;" frameborder="0">

   </iframe>
  </td>
  </tr>
  </table>
  <%}%>
  </td>
  </tr></table>
  
  
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
    pageList= ((ArrayList)pageDtl.get("ISSUEGRAPH") == null)?new ArrayList():(ArrayList)pageDtl.get("ISSUEGRAPH"); 
if(pageList!=null && pageList.size() >0){
if(seqList!=null && seqList.size()>0){
String url="ajaxRptAction.do?method=stockTally";%>
<script type="text/javascript">
<!--
$(window).bind("load", function() {
callcreatedoubleBarGraph('<%=url%>','Tally','50','362');
});
 -->
</script>  
<%}}%>
  </body>
</html>