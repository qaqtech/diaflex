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
    <title>Inward Report</title>
    <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/bse.css?v=<%=info.getJsVersion()%>"/>
    <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/style.css?v=<%=info.getJsVersion()%>"/>
    <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/general.css?v=<%=info.getJsVersion()%>"/>
     <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/bse.js?v=<%=info.getJsVersion()%> " > </script>
     <script type="text/javascript" src="<%=info.getReqUrl()%>/PopScripts/popup.js?v=<%=info.getJsVersion()%> " > </script>
   <script src="<%=info.getReqUrl()%>/PopScripts/jquery-1.2.6.min.js" type="text/javascript"></script>

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
    <td height="5" valign="top" class="greyline"><img src="<%=info.getReqUrl()%>/images/grey_linebig.png" width="685" height="5" align="right" /></td>
  </tr>
  <tr>
  <td valign="top" class="hedPg">
  <table cellpadding="1" cellspacing="5"><tr><td valign="middle">
  <img src="../images/line.jpg" border="0" /></td><td valign="middle"> <span class="pgHed">Inward Report</span> </td>
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
  <tr><th></th><th align="center">From</th><th align="center">To</th> </tr>
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
    <tr><td class="tdLayout" valign="top" height="20px">
<%
String view = (String)request.getAttribute("view");
if(view!=null){
ArrayList pktList = (ArrayList)session.getAttribute("pktList");            
ArrayList itemHdr = (ArrayList)session.getAttribute("itemHdr");
String grpcts = "";
String grpamt = "";

if(pktList!=null && pktList.size()>0){
%>

<table cellpadding="5" cellspacing="5">
    <tr><td>Create Excel <img src="../images/ico_file_excel.png" onclick="goTo('uploadFile.do?method=createXL','','')" border="0"/> </td></tr>
    <tr><td valign="top">


<table class="dataTable" align="left" id="dataTable">
<tr>
    
        <%for(int j=0; j < itemHdr.size(); j++ ){
        String hdr = (String)itemHdr.get(j);
        
        %>
        <th title="<%=hdr%>"><%=hdr%></th>
        <%}
        %>
</tr>


    <%
    for(int m=0;m < pktList.size(); m++){    
    HashMap pktDtl = (HashMap)pktList.get(m);
    String kapanId =util.nvl((String)pktDtl.get("ISKAPAN"));
    String ttCts =util.nvl((String)pktDtl.get("ISTOTAL"));
    %>
    <%
    
        if(kapanId.equals("Y")){
         %>
           <tr><td style="text-align:left;font-weight: bold;" colspan="<%=itemHdr.size()%>">
             <%=util.nvl((String)pktDtl.get("KAPAN"))%></td></tr>
        <% } else if(ttCts.equals("Y")){
        %>    
             <tr>
               <% 
               
               int inxVnm = itemHdr.indexOf("PACKETCODE");
               int inxCts = itemHdr.indexOf("CTS");
               int inxAmt = itemHdr.indexOf("AMOUNT");        
               for(int j=0; j < itemHdr.size(); j++ ){
               String prp = (String)itemHdr.get(j);
               int inxPrp = itemHdr.indexOf(prp);
               if (inxPrp == inxVnm) { %>
               <td style="font-weight: bold;"><%=util.nvl((String)pktDtl.get("ttlqty"))%></td>     
               <%
               } else if (inxPrp == inxCts) { %>
               <td style="font-weight: bold;"><%=util.nvl((String)pktDtl.get("ttlCts"))%></td>     
               <%
               } else if (inxPrp == inxAmt) {
               %>
               <td style="font-weight: bold;"><%=util.nvl((String)pktDtl.get("ttlAmt"))%></td>     
               <% } else { %>
              <td></td>      
               <%  } } %>
             </tr>
             
    
    <% } else { %>
    <tr>
    <%
    for(int p=0; p < itemHdr.size(); p++ ){
    String prp = (String)itemHdr.get(p);
    String prpValue =  (String) pktDtl.get(prp);
    %>    
    <td><label><%=prpValue%></label></td>
    <%
    }
    %>
    </tr>
<%} 
%>

<%} 
%>
</table>
</td></tr></table>
<%
}}
%></td></tr></table></body></html>
