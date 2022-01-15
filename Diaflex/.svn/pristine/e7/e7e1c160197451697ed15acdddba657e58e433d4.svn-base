<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page errorPage="../error_page.jsp" %>
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.ResultSet, java.util.ArrayList,java.util.Collections,java.util.ArrayList,java.util.HashMap,java.text.SimpleDateFormat, java.util.Calendar"%>
<jsp:useBean id="info" class="ft.com.InfoMgr" scope="session" />
<jsp:useBean id="util" class="ft.com.JspUtil" scope="page" />
<%@taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>
<%@taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<html> 
   <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/bse.js?v=<%=info.getJsVersion()%> " > </script>
<link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/bse.css?v=<%=info.getJsVersion()%>"/>
<link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/general.css?v=<%=info.getJsVersion()%>"/>
<script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/ajax.js?v=<%=info.getJsVersion()%> " > </script>
  <script type="text/javascript" src="<%=info.getReqUrl()%>/PopScripts/popup.js?v=<%=info.getJsVersion()%> " > </script>
    <script src="<%=info.getReqUrl()%>/PopScripts/jquery-1.2.6.min.js" type="text/javascript"></script>
   <script language="javaScript" type="text/javascript" src="<%=info.getReqUrl()%>/calendar/calendar.js"></script>
   <link href="<%=info.getReqUrl()%>/calendar/calendar.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="<%=info.getReqUrl()%>/chartscript/amcharts.js?v=<%=info.getJsVersion()%> " > </script>
<script type="text/javascript" src="<%=info.getReqUrl()%>/chartscript/chartajax.js?v=<%=info.getJsVersion()%> " > </script>
<script type="text/javascript" src="<%=info.getReqUrl()%>/chartscript/serial.js?v=<%=info.getJsVersion()%> " > </script>
 
      <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/style.css?v=<%=info.getJsVersion()%>"/>

  <title>Mail Report</title>
 
  </head>
  
  <%
  //util.updAccessLog(request,response,"Mail Report", "Mail Report");
  String logId=String.valueOf(info.getLogId());
        String onfocus="cook('"+logId+"')";
        HashMap allPageDtl= (info.getPageDetails() == null)?new HashMap():(HashMap)info.getPageDetails();
HashMap pageDtl=(HashMap)allPageDtl.get("MAIL_REPORT");
    ArrayList pageList=new ArrayList();
    HashMap pageDtlMap=new HashMap();
    String fld_nme="",fld_ttl="",val_cond="",dflt_val="",fld_typ="",form_nme="",lov_qry="";
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
  <img src="../images/line.jpg" border="0" /></td><td valign="middle"> <span class="pgHed">Mail Report</span> </td>
</tr></table>
  </td>
  </tr>
<tr><td valign="top" class="hedPg">
<html:form action="/contact/advcontact.do?method=fetchreport"  method="POST">
  <table  class="grid1">
   <tr>
   <th colspan="2" align="center">Mail Log Search</th>
   </tr>
             <%
                      pageList= ((ArrayList)pageDtl.get("MAILTYP") == null)?new ArrayList():(ArrayList)pageDtl.get("MAILTYP");
             if(pageList!=null && pageList.size() >0){%>
             <tr>
               <td>Mail Type</td>
               <td>
             <html:select property="value(mailTyp)" styleId="mailTyp" name="advContactForm" >
             <html:option value="">All</html:option>
             <%
               for(int j=0;j<pageList.size();j++){
                 pageDtlMap=(HashMap)pageList.get(j);
                 fld_nme=(String)pageDtlMap.get("fld_nme");
                 fld_ttl=(String)pageDtlMap.get("fld_ttl");
                 %>
                <html:option value="<%=fld_nme%>"><%=fld_ttl%></html:option>
             <%}%>
            </html:select>
            </td>
            </tr>
            <%}%>
    <%
    pageList= ((ArrayList)pageDtl.get("SNO") == null)?new ArrayList():(ArrayList)pageDtl.get("SNO");
    if(pageList!=null && pageList.size() >0){%>
   <tr>
   <td>Seq No/Ref No/Memo No </td>
   <td><html:text property="value(seqNo)" name="advContactForm" styleId="seqNo"/></td>
   </tr>
   <%}%>
    <%
        pageList= ((ArrayList)pageDtl.get("DATE") == null)?new ArrayList():(ArrayList)pageDtl.get("DATE");
    if(pageList!=null && pageList.size() >0){%>
    <tr><td>Date From  </td><td>
    <html:text property="value(dtefr)" styleClass="txtStyle"  styleId="dtefr"  size="15"  /> <a href="#" style="margin-top:5px;"  onClick="setYears(1900, '<%=info.getCurrentYear()%>' );showCalender(this, 'dtefr');">
                <img src="<%=info.getReqUrl()%>/images/calender.png"></a></td></tr>
    <tr><td>Date To  </td><td>
    <html:text property="value(dteto)" styleClass="txtStyle"  styleId="dteto"  size="15"  /> <a href="#"  onClick="setYears(1900, '<%=info.getCurrentYear()%>' );showCalender(this, 'dteto');">
                <img src="<%=info.getReqUrl()%>/images/calender.png"></a>
                </td></tr>
    <%}%>            
             <%
                     pageList= ((ArrayList)pageDtl.get("APPTYP") == null)?new ArrayList():(ArrayList)pageDtl.get("APPTYP");
             if(pageList!=null && pageList.size() >0){%>
                <tr><td>Type</td>
                <td>
             <html:select property="value(typ)" styleId="typ" name="advContactForm" >
             <html:option value="">All</html:option>
             <%
               for(int j=0;j<pageList.size();j++){
                 pageDtlMap=(HashMap)pageList.get(j);
                 fld_nme=(String)pageDtlMap.get("fld_nme");
                 fld_ttl=(String)pageDtlMap.get("fld_ttl");
                 %>
                <html:option value="<%=fld_nme%>"><%=fld_ttl%></html:option>
             <%}%>
            </html:select>
            </td>
            </tr>
            <%}%>
             <%
             pageList= ((ArrayList)pageDtl.get("MAILSTT") == null)?new ArrayList():(ArrayList)pageDtl.get("MAILSTT");
             if(pageList!=null && pageList.size() >0){%>
                <tr><td>Mail Status</td>
                <td>
             <html:select property="value(stt)" styleId="stt" name="advContactForm" >
             <html:option value="">All</html:option>
             <%
               for(int j=0;j<pageList.size();j++){
                 pageDtlMap=(HashMap)pageList.get(j);
                 fld_nme=(String)pageDtlMap.get("fld_nme");
                 fld_ttl=(String)pageDtlMap.get("fld_ttl");
                 %>
                <html:option value="<%=fld_nme%>"><%=fld_ttl%></html:option>
             <%}%>
            </html:select>
            </td>
            </tr>
            <%}%>
   <tr>
   <td colspan="2" align="center"><html:submit property="value(srch)" value="Mail Report" styleClass="submit" />
   </td>
   </tr>
   </table>
</html:form>
</td>
</tr>
  <%
    ArrayList dtlList= (ArrayList)request.getAttribute("dtlList");
    String view= util.nvl((String)request.getAttribute("View"));
    if(!view.equals("")){
    if(dtlList!=null && dtlList.size()>0){%>
<tr>
<td class="hedPg">
<table class="grid1">
<tr>
<th>Mail Type</th>
<th>Mail Status</th>
<th>Through App</th>
<th>User</th>
<th>Delivery</th>
<th>Date</th>
<th>To Email</th>
<th>Cc Email</th>
<th>Ref Idn/Seq No/Memo No</th>
</tr>
<%for(int i=0;i<dtlList.size();i++){
HashMap data=(HashMap)dtlList.get(i);
%>
<tr>
<td nowrap="nowrap"><%=util.nvl((String)data.get("REFTYP"))%></td>
<td nowrap="nowrap"><%=util.nvl((String)data.get("STT"))%></td>
<td nowrap="nowrap"><%=util.nvl((String)data.get("FLG"))%></td>
<td nowrap="nowrap"><%=util.nvl((String)data.get("UNM"))%></td>
<td nowrap="nowrap"><%=util.nvl((String)data.get("DELYN"))%></td>
<td nowrap="nowrap"><%=util.nvl((String)data.get("DTE"))%></td>
<td><%=util.nvl((String)data.get("TOEML"))%></td>
<td><%=util.nvl((String)data.get("CCEML"))%></td>
<td nowrap="nowrap"><%=util.nvl((String)data.get("REFIDN"))%></td>
</tr>
<%}%>
</table>
</td>
</tr>
    <%}else{%>
<tr><td valign="top" class="hedPg">
Sorry No Result Found</td></tr>
<%}}%>
  <tr><td>
<jsp:include page="/footer.jsp" />
</td></tr>
  </table> 
     <%@include file="/calendar.jsp"%>
  </body>
</html>