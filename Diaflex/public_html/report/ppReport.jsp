<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page errorPage="../error_page.jsp" %>
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import="java.util.ArrayList,java.util.ArrayList,java.util.HashMap, java.sql.*, java.util.Enumeration, java.util.logging.Level"%>
<jsp:useBean id="info" class="ft.com.InfoMgr" scope="session" />
<jsp:useBean id="util" class="ft.com.JspUtil" scope="page" />
<%@taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
 
<html>
  <head>
 <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
  
  <title>PP Reports</title>
  <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/bse.js?v=<%=info.getJsVersion()%> " > </script>
<link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/bse.css?v=<%=info.getJsVersion()%>"/>
<script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/ajax.js?v=<%=info.getJsVersion()%> " > </script>
  

 
      <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/style.css?v=<%=info.getJsVersion()%>"/>
  </head>
 
   <%

   ArrayList memoRtnLst = info.getMomoRtnLst();
   %>
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
  <img src="../images/line.jpg" border="0" /></td><td valign="middle"> <span class="pgHed">
 
  <%=session.getAttribute("page")%>
 
 
  </span> </td>
</tr></table>
  </td>
  </tr>
  <tr><td valign="top" class="hedPg"  >
  <display:table name="sessionScope.reportForm.ppListMap" export="true"  sort="list" class="displayTable">
   <display:column property="Count"  title="SR NO."  />
  <display:column property="idn"  title="Idn" sortable="true" group="1" />
  <display:column property="vnm"  title="Packet No." />
   <display:column property="rnk"  title="RNK" sortable="true" />
    <display:column property="byr" style="text-align:left;" title="Buyer"  sortable="true" />
       <display:column property="raprte" style="text-align:right;"  title="Rap_rte"  />
     <display:column property="netrte" style="text-align:right;"  title="Net_Rte"  />
    <display:column property="netdis" style="text-align:right;" title="Net_Dis"  />
    <display:column property="ofrrte" style="text-align:right;" title="Ofr_Rte"  />
   <display:column property="ofrdis" title="Ofr_Dis"  />
    <display:column property="byrrte" style="text-align:right;"  title="Ouot_rte"  />
    <display:column property="byrdis" title="Ouot_Dis"  />
  
   
  <%
   for (int j = 0; j < memoRtnLst.size(); j++) {
                String prp = (String)memoRtnLst.get(j);
                if(prp.equals("SHAPE")){
                %>
                
               <% }else{%>
               <display:column property="<%=prp%>" title="<%=prp%>"  /> 
               <%}%>
   <% }
  %>
  <display:setProperty name="export.excel.filename" value="PP_LB_Details.xls"/>
  <display:setProperty name="export.xls" value="true" />
  <!--
  <display:setProperty name="export.pdf.filename" value="PP_LB_Details.pdf"/>
  <display:setProperty name="export.pdf" value="true" />
  -->
  </display:table>
  </td></tr>
  
  </table>
  
  </body>
</html>