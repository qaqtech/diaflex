<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page errorPage="../error_page.jsp" %>
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.ResultSet, java.util.ArrayList,java.util.Collections,java.util.ArrayList,java.util.HashMap,java.text.SimpleDateFormat, java.util.Calendar"%>
<jsp:useBean id="info" class="ft.com.InfoMgr" scope="session" />
<jsp:useBean id="util" class="ft.com.JspUtil" scope="page" />
<%@taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>
<html> 
   <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/bse.js?v=<%=info.getJsVersion()%> " > </script>
  <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/bse.css?v=<%=info.getJsVersion()%>"/>
  <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/ajax.js?v=<%=info.getJsVersion()%> " > </script>
  <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/style.css?v=<%=info.getJsVersion()%>"/>
  <script language="javaScript" type="text/javascript" src="<%=info.getReqUrl()%>/calendar/calendar.js"></script>
   <link href="<%=info.getReqUrl()%>/calendar/calendar.css" rel="stylesheet" type="text/css" />
   <script src="<%=info.getReqUrl()%>/PopScripts/jquery-1.2.6.min.js" type="text/javascript"></script>
   <script type="text/javascript" src="<%=info.getReqUrl()%>/jqueryScript/jquery.js " > </script>
   <script src="<%=info.getReqUrl()%>/jqueryScript/jqueryScript.js?v=<%=info.getJsVersion()%>" type="text/javascript"></script>
  <title>Sale Ex Allocation</title>
  </head>
        <%String logId=String.valueOf(info.getLogId());
        String onfocus="cook('"+logId+"')";
        %>
  <body onfocus="<%=onfocus%>" onload="MM_preloadImages('images/faunalogo.png')" topmargin="0" leftmargin="0" rightmargin="0" bottommargin="0">
  <table cellpadding="" cellspacing="" >
  <%
  ArrayList prpLst = (ArrayList)session.getAttribute("EMP_MDL");
  HashMap prp = info.getPrp();
  ArrayList chkNmeIdnList = (info.getChkNmeIdnList() == null)?new ArrayList():(ArrayList)info.getChkNmeIdnList();
  String msg = util.nvl((String)request.getAttribute("msg"));
  %>
  <tr>
  <td valign="top" class="hedPg">
  <table>
  <html:form action="/contact/bulkUpdate.do?method=saveSaleExAlloc" method="post">
  
  
  <%if(chkNmeIdnList.size()==0) {%>
  <span class="redLabel"> Please Select Buyer/Vendor</span>
  <%
  }else{
  %>
  
  <%if(msg!=null){%>
  <tr><td valign="top"><span class="redLabel"> <%=msg%></span></td></tr>
  <%}%>
  
  <tr><td valign="top">
  <table  class="grid1">
  <tr><th>Employee</th><td>
  <html:select property="value(empIdn)%>" styleId="empIdn" name="advContactForm"   >
  <html:optionsCollection property="empList" name="advContactForm" 
  label="byrNme" value="byrIdn" />
   </html:select>
  </td></tr>
  <% for(int k=0;k<prpLst.size();k++){ 
  String fld = util.nvl((String)prpLst.get(k));
  String fldNm = "value("+fld+")";
  ArrayList list = (ArrayList)prp.get(fld+"V");
  %>
  <tr>
  <th><%=fld%></th>
  <td>
  <html:select property="<%=fldNm%>" styleId="<%=fld%>" name="advContactForm" >
  <%if(list!=null){
  for(int i=0;i<list.size();i++){
  String fldVal=(String)list.get(i);%>
  <html:option value="<%=fldVal%>"><%=fldVal%></html:option> 
  <%}}%>   
  </html:select>
  </td>
  </tr>
  <%}%>
  <tr><td colspan="6" align="center"><html:submit property="value(submit)" value="Submit" styleClass="submit" /></td> </tr>
  </table>
  <%}%>
  </td></tr>
  </html:form>
  </table>
  
  </td></tr>
  
  </table>  
   <%@include file="../calendar.jsp"%>
  </body>
</html>