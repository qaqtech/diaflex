<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page errorPage="../error_page.jsp" %>
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import="java.util.ArrayList,java.util.HashMap,java.sql.ResultSet,ft.com.*, java.util.Date, java.util.ArrayList, ft.com.dao.*"%>
<jsp:useBean id="info" class="ft.com.InfoMgr" scope="session" />
<jsp:useBean id="util" class="ft.com.JspUtil" scope="page" />

<%@taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>Exchange Rate</title>
     <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/bse.css?v=<%=info.getJsVersion()%>"/>
    <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/style.css?v=<%=info.getJsVersion()%>"/>
     <script language="javaScript" type="text/javascript" src="<%=info.getReqUrl()%>/calendar/calendar.js"></script>
   <link href="<%=info.getReqUrl()%>/calendar/calendar.css" rel="stylesheet" type="text/css" />
     <script language="javaScript" type="text/javascript" src="<%=info.getReqUrl()%>/scripts/bse.js?v=<%=info.getJsVersion()%>"></script>

  </head>
<%
HashMap allPageDtl= (info.getPageDetails() == null)?new HashMap():(HashMap)info.getPageDetails();
HashMap pageDtl=(HashMap)allPageDtl.get("XRTRTE_FORM");
HashMap pageDtlMap=new HashMap();
ArrayList pageList=new ArrayList();
String fld_nme="",fld_ttl="",val_cond="",dflt_val="",fld_typ="",form_nme="",lov_qry="";
        
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
    <td height="5" valign="top" class="greyline"><img src="<%=info.getReqUrl()%>/images/grey_linebig.png" width="685" height="5" align="right" /></td>
  </tr>
   <tr>
  <td valign="top" class="hedPg">
  <table cellpadding="1" cellspacing="5"><tr><td valign="middle">
  <img src="../images/line.jpg" border="0" /></td><td valign="middle"> <span class="pgHed">Exchange Rate</span> </td>
</tr></table>
  </td>
  </tr>
  <%
  ArrayList curList = (ArrayList)session.getAttribute("curList");
  ArrayList dteLst= ((ArrayList)request.getAttribute("dteLst") == null)?new ArrayList():(ArrayList)request.getAttribute("dteLst");
 %>
 <tr>
  <td valign="top" class="hedPg">
  <table>
  <html:form action="masters/xrtRte.do?method=save" method="post" onsubmit="return validate_rte()" >
 
  <%if(curList!=null && curList.size() > 0){
  int size = curList.size();
  %>
   <input  type="hidden"  id="count" value="<%=size%>" />
   <tr><td>
  <table class="grid1">

    <tr><th>Sr</th>
        <th>Currency</th>
        <th>Rate</th>
        <th>From</th>
        </tr>
<%for(int i=0;i<curList.size();i++){
String curName =(String)curList.get(i);
String fldVal = "value("+curName+")";
String fldId = "rte_"+(i+1);
String fldValFr = "value("+curName+"_FRDTE)";
String fldIdFr = "dte_"+(i+1);
%>
<tr>
<td><%=i+1%></td>
<td> <%=curName%></td> 
<td><html:text property="<%=fldVal%>" styleId="<%=fldId%>" name="xrtRteForm"  /> </td>
<td><bean:write property="<%=fldValFr%>" name="xrtRteForm" /> </td>
</tr>
<%}%>
<tr> <td colspan="3" align="center"> <html:submit property="value(add)" styleClass="submit" value="Save Changes" /> </td></tr>
</table>
</td></tr>
            <%pageList=(ArrayList)pageDtl.get("XRTHISTORY");
            if(pageList!=null && pageList.size() >0){
            for(int j=0;j<pageList.size();j++){
            pageDtlMap=(HashMap)pageList.get(j);
            %>
<tr><td>&nbsp;</td></tr>           
<%if(dteLst!=null && dteLst.size()>0){
int dteLstsz=dteLst.size();
int curListsz =curList.size();%>
<tr>
<td>
<table class="grid1">
<tr><td colspan="<%=(curListsz+1)%>" align="center"><b>Exchange Rate History</b></td></tr>
<tr>
<td>Date/Currency</td>
<%for(int i=0;i<curListsz;i++){%>
<th><%=util.nvl((String)curList.get(i))%></th>
<%}%>
</tr>
<%for(int c=0;c<dteLstsz;c++){
String frdt =util.nvl((String)dteLst.get(c));%>
<tr>
<th><%=frdt%></th>
<%for(int d=0;d<curListsz;d++){
String curName =util.nvl((String)curList.get(d));
String fldValFr = "value("+curName+"_"+frdt+")";
%>
<td><bean:write property="<%=fldValFr%>" name="xrtRteForm" /> </td>
<%}%>
</tr>
<%}%>
</table>
</td>
</tr>
<%}%>
<%}}%>
  <%}%>
  </html:form>
  </table>
 </td></tr>
 <tr>
    <td><jsp:include page="/footer.jsp" /> </td>
    </tr>
 </table>
</body>
</html>
