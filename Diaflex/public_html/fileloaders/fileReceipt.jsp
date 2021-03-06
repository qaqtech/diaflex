<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page errorPage="../error_page.jsp" %>
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import="java.util.ArrayList,java.util.HashMap,java.sql.ResultSet,ft.com.*, java.util.Date, java.util.ArrayList, ft.com.dao.*"%>
<jsp:useBean id="util" class="ft.com.JspUtil" scope="page" />
<jsp:useBean id="info" class="ft.com.InfoMgr" scope="session" />
<%@taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>
<%@taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
 
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>Manufacturing Report</title>
    <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/bse.css?v=<%=info.getJsVersion()%>"/>
    <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/style.css?v=<%=info.getJsVersion()%>"/>
    <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/general.css?v=<%=info.getJsVersion()%>"/>
     <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/bse.js?v=<%=info.getJsVersion()%> " > </script>
     <script type="text/javascript" src="<%=info.getReqUrl()%>/PopScripts/popup.js?v=<%=info.getJsVersion()%> " > </script>
   <script src="<%=info.getReqUrl()%>/PopScripts/jquery-1.2.6.min.js" type="text/javascript"></script>

  </head>
  <%
  HashMap getTotal = (HashMap)request.getAttribute("totalMap");
  String formAction = info.getReqUrl()+"/genericexcelutil" ;
   String mdl =util.nvl((String)request.getAttribute("mdl"));
        String logId=String.valueOf(info.getLogId());
        String onfocus="cook('"+logId+"')";
        %>
 <body onfocus="<%=onfocus%>" onload="MM_preloadImages('images/faunalogo.png')" topmargin="0" leftmargin="0" rightmargin="0" bottommargin="0">
<div id="backgroundPopup"></div>
<div id="popupContactASSFNL">
<table class="popUpTb">
 <tr><td height="5%">&nbsp;&nbsp;&nbsp; </td></tr>
<tr><td valign="top" height="95%">

  <form action="<%=formAction%>" method="POST">
  <input type="hidden" name="filename" value="FileLoadDetail" />
 <% if(mdl.equals("SUR_RT")){%>
  <input type="hidden" name="SUTVAL" value="SUTVAL" />
  <%}%>
<jsp:include page="/excelFilter.jsp" />
</form>
</td></tr>
</table>
</div>
<table width="100%"  border="0" align="center" cellpadding="0" cellspacing="0" class="mainbg">
  <tr>
    <td height="103" valign="top">
   <jsp:include page="/header.jsp" />
    
    </td>
   </tr>
 
  <tr>
    <td height="5" valign="top" class="greyline"><img src="<%=info.getReqUrl()%>/images/grey_linebig.png" width="685" height="5" align="right" /></td>
  </tr>
  <tr>
  <td valign="top" class="hedPg">
  <table cellpadding="1" cellspacing="5"><tr><td valign="middle">
  <img src="../images/line.jpg" border="0" /></td><td valign="middle"> <span class="pgHed">Manufacturing Report</span> </td>
   </tr></table>
  </td>
  </tr>
  <%
 HashMap allPageDtl= (info.getPageDetails() == null)?new HashMap():(HashMap)info.getPageDetails();
 HashMap pageDtl=(HashMap)allPageDtl.get("MFG_EXL");
  ArrayList pageList=new ArrayList();
  HashMap pageDtlMap=new HashMap();
  String fld_nme="",fld_ttl="",val_cond="",dflt_val="",fld_typ="",form_nme="",flg1="",lov_qry="";
  
  String msg = (String)request.getAttribute("msg");
  if(msg!=null){
  %>
  <tr>
  <td valign="top" class="tdLayout"><span class="redLabel"> <%=msg%></span></td></tr>
  <%}%>
  <tr>
  <td valign="top" class="tdLayout">
  <html:form action="fileloaders/uploadFile.do?method=viewRC" method="post">
  <table class="grid1">
  <tr><th align="center" colspan="3">Generic Search 
  <%
  ArrayList rolenmLst=(ArrayList)info.getRoleLst();
  String usrFlg=util.nvl((String)info.getUsrFlg());
  if(usrFlg.equals("SYS") || rolenmLst.contains("ADMIN")){
  %>
  <img src="../images/edit.jpg" title="Edit Properties Module" onclick="newWindow('<%=info.getReqUrl()%>/dynamicProperty.do?method=load&mdl=MFT_SRCH&sname=MftGNCSrch&par=GENERIC')" border="0" width="15px" height="15px"/> 
  <%}%>
  </th> </tr>
  <tr><td colspan="3"><jsp:include page="/genericSrch.jsp" /></td></tr>
  <tr><td align="center" colspan="3">
  
  <%
pageList= ((ArrayList)pageDtl.get("BUTTON") == null)?new ArrayList():(ArrayList)pageDtl.get("BUTTON");
      if(pageList!=null && pageList.size() >0){
      for(int j=0;j<pageList.size();j++){
      pageDtlMap=(HashMap)pageList.get(j);
      fld_nme="value("+(String)pageDtlMap.get("fld_nme")+")";
      val_cond=(String)pageDtlMap.get("val_cond");
      fld_ttl=(String)pageDtlMap.get("fld_ttl");
      val_cond=(String)pageDtlMap.get("val_cond");
      fld_typ=(String)pageDtlMap.get("fld_typ");
      flg1=(String)pageDtlMap.get("flg1");
      if(fld_typ.equals("S")){ 
    %>
       <html:submit property="<%=fld_nme%>" value="<%=fld_ttl%>"  onclick="<%=val_cond%>" styleClass="submit" />
    <%
   } 
   }
   }
   %> 
  
 <!-- 
  <html:submit property="value(dtlRpt)" styleClass="submit" value="Detail Report" />&nbsp;&nbsp;
  <html:submit property="value(suratRt)" styleClass="submit" value="Surat Avg Report" />&nbsp;&nbsp;
  <html:submit property="value(summary)" styleClass="submit" value="Summery" />&nbsp;&nbsp;
  -->
  
  
  </td></tr> 
  </table></html:form>
  </td></tr>
   <tr><td class="tdLayout" valign="top" height="20px">
   </td></tr>
   <%%>
   <%
   String SUMMARY = (String)request.getAttribute("SUMMARY");
   if(SUMMARY!=null){
   int count =0;
   ArrayList pktSummryDtl = (ArrayList)session.getAttribute("pktDtl");
   if(pktSummryDtl!=null && pktSummryDtl.size() >0){%>
  
   <tr><td class="tdLayout" valign="top">
   <form action="uploadFile.do?method=rcpt" method="post" name="rcpt" onsubmit="return validate_SelectAll('rcpt','cb_rcpt_')">
   <table cellspacing="2" cellpadding="2"><tr><td>
    <input type="submit"  class="submit" value="Transfer To Weight Check" /></td></tr>
 <tr><td>
   <table class="grid1"><tr>
   <th><input type="checkbox" name="checkAll" id="checkAll" onclick="checkALL('cb_rcpt_', 'count')" /> Select ALL</th> <th>Receipt No</th> <th>Qty</th> <th>Cts</th></tr>
   <% 
   
   for(int i=0;i<pktSummryDtl.size();i++){
   HashMap pktSummry = (HashMap)pktSummryDtl.get(i);
   String recptNo = util.nvl((String)pktSummry.get("rcptNo"));
   String fldVal = "cb_rcpt_"+recptNo;
   count = i+1;
   String fldId = "cb_rcpt_"+count;
   %>
   <tr>
   <td><input type="checkbox" name="<%=fldVal%>" id="<%=fldId%>" value="<%=recptNo%>"/> </td>
   <td><%=pktSummry.get("rcptNo")%> </td>
   <td><%=pktSummry.get("qty")%> </td> 
   <td><%=pktSummry.get("cts")%> </td>
    </tr>
   <%}%>
   </table></td></tr></table>
   </form>
   <input type="hidden" name="count" id="count" value="<%=count%>" />
   </td></tr>
  <% }else{%>
  <tr><td class="tdLayout" valign="top"> Sorry no result found</td></tr>
  <%}%>
  <%}else{
   
   if(getTotal!=null){
   
   %>
   <tr><td class="tdLayout" valign="top">
   <table>
   <tr><td><B>Total: </b></td> <td><%=getTotal.get("qty")%>&nbsp;&nbsp;</td><td><b>Cts:</b></td> <td><%=getTotal.get("cts")%>&nbsp;&nbsp;</td>
   <%if(mdl.equals("SUR_RT")){%>
   <td><b>Value:</b></td> <td><%=getTotal.get("vlu")%>&nbsp;&nbsp;</td>
  <% }%>
   <td>Create Excel <img src="../images/ico_file_excel.png" id="img" onclick="loadASSFNLPop('img')" /> </td>
   </tr>
   </table>
   </td></tr>
   <%}%>
   
   <tr><td class="tdLayout" valign="top">
    <%
    ArrayList prpDspBlocked = info.getPageblockList();
    ArrayList vwPrpLst = (ArrayList)session.getAttribute("MftViewLst");
    HashMap mprp = info.getMprp();
   String view = (String)request.getAttribute("view");
  
   if(view!=null){
   
   %>
   <display:table name="sessionScope.fileUploadForm.pktDtlList" requestURI=""   sort="list" class="displayTable">
    <display:column property="sr"  title="Sr No"  />
   <display:column property="vnm"  title="Packet No"  />
  
      
    <%for(int j=0; j < vwPrpLst.size(); j++ ){
    String prp = (String)vwPrpLst.get(j);
    if(prpDspBlocked.contains(prp)){
    }else{
    String hdr = (String)mprp.get(prp);
    String prpDsc = (String)mprp.get(prp+"D");
    if(hdr == null) {
        hdr = prp;
    }  

%>
 <display:column property="<%=prp%>"  title="<%=prpDsc%>"  />

<%}}%> 
<% 
if(mdl.equals("SUR_RT")){%>
<display:column property="val"  title="Value"  />
<%}%>

</display:table>
   <%}%>
   </td></tr>
   <%}%>
   <tr>
    <td><jsp:include page="/footer.jsp" /> </td>
    </tr>
  </table>
  
  </body>
</html>