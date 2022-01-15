<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page errorPage="../error_page.jsp" %>
<%@ page contentType="text/html;charset=windows-1252"%>
<jsp:useBean id="info" class="ft.com.InfoMgr" scope="session" />
<jsp:useBean id="util" class="ft.com.JspUtil" scope="page" />
<%@ page import="java.util.ArrayList,java.util.HashMap,java.sql.ResultSet,ft.com.*, java.util.Date, java.util.ArrayList, ft.com.dao.*"%>
<%@taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>Lot Pricing</title>
    <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/bse.css?v=<%=info.getJsVersion()%>"/>
    <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/general.css?v=<%=info.getJsVersion()%>"/>
     <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/style.css?v=<%=info.getJsVersion()%>"/>
      <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/bse.js?v=<%=info.getJsVersion()%> " > </script>
       <script type="text/javascript" src="<%=info.getReqUrl()%>/PopScripts/popup.js?v=<%=info.getJsVersion()%> " > </script>
     <script src="<%=info.getReqUrl()%>/PopScripts/jquery-1.2.6.min.js" type="text/javascript"></script>
  </head>
        <%String logId=String.valueOf(info.getLogId());
        String onfocus="cook('"+logId+"')";
        %>
 <body onfocus="<%=onfocus%>" onload="MM_preloadImages('images/faunalogo.png')" topmargin="0" leftmargin="0" rightmargin="0" bottommargin="0">


<table width="100%"  border="0" align="center" cellpadding="0" cellspacing="0">
   <tr>
   
   <td valign="top" class="tdLayout">
   <table>
    <%String msg = (String)request.getAttribute("msg");
     if(msg!=null){%>
    <tr><td><span class="redLabel"><%=msg%></span></td></tr>
   <%}%>
   <%
   String Idn = util.nvl((String)request.getAttribute("idn"));
   %>
   <html:form action="mix/lotpri.do?method=saveDept" method="post">
   <tr><td><html:submit property="value(save)" value="Save" styleClass="submit" /><html:hidden property="value(IDN)" /></td></tr>
   <tr><td><table class="grid1">
   <tr>
   <th>Department</th>
   <th>Surat Avg</th>
   <th>Polish Avg</th>
   <th>Account Avg</th>
   </tr>
   <%  HashMap prp = info.getPrp();
        ArrayList PrpDtl = (ArrayList)prp.get("DEPT"+"V");
   for(int j=0; j < PrpDtl.size(); j++ ){
   String dept=(String)PrpDtl.get(j);
   String surAvgID = dept+"_"+"SURT_"+Idn;
   String polAvgID = dept+"_"+"POL_"+Idn;
   String accAvgID = dept+"_"+"ACC_"+Idn;
   String surDis = "value("+dept+"_SURT_"+Idn+")";
   String polDis = "value("+dept+"_POL_"+Idn+")";
   String accDis = "value("+dept+"_ACC_"+Idn+")";
   %>
   <tr>
   <td><%=dept%></td>
   <td><html:text property="<%=surDis%>" size="8" styleId="<%=surAvgID%>"/></td>
    <td><html:text property="<%=polDis%>" size="8" styleId="<%=polAvgID%>"/></td>
    <td><html:text property="<%=accDis%>" size="8" styleId="<%=accAvgID%>"/></td>
   </tr>
   <%}%>
   </table></td>
   </tr>
   </html:form>
   </table>
   </td>
  </table>
  </body>
</html>