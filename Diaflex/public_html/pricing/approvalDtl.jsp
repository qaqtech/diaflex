<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import="java.util.ArrayList,java.util.ArrayList,java.util.HashMap, java.sql.*, java.util.Enumeration, java.util.logging.Level"%>

<jsp:useBean id="info" class="ft.com.InfoMgr" scope="session" />
<jsp:useBean id="util" class="ft.com.JspUtil" scope="page" />
<%@taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>approvalDtl</title>
   <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/bse.js?v=<%=info.getJsVersion()%> " > </script>
<link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/bse.css?v=<%=info.getJsVersion()%>"/>
<script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/ajax.js?v=<%=info.getJsVersion()%> " > </script>
  <script src="<%=info.getReqUrl()%>/scripts/assortScript.js?v=<%=info.getJsVersion()%>" type="text/javascript"></script>
<script language="javaScript" type="text/javascript" src="<%=info.getReqUrl()%>/calendar/calendar.js"></script>
   <link href="<%=info.getReqUrl()%>/calendar/calendar.css" rel="stylesheet" type="text/css" />

      <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/style.css?v=<%=info.getJsVersion()%>"/>
  </head>
    <body onload="MM_preloadImages('images/faunalogo.png')" topmargin="0" leftmargin="0" rightmargin="0" bottommargin="0">
<table width="100%"  border="0" align="center" cellpadding="0" cellspacing="0" >
  <tr>
    <td height="103" valign="top">
    <%
    //util.updAccessLog(request,response,"Approval Dtl", "Approval Dtl");
    ArrayList pktDtlList = (ArrayList)request.getAttribute("pktList");
    if(pktDtlList!=null && pktDtlList.size()>0){
    %>
    <div class="memo">
    <table class="Orange"  cellspacing="1" cellpadding="1" >
    <tr><th class="Orangeth">Group</th><th class="Orangeth">New Val</th><th class="Orangeth">old Val</th><th class="Orangeth">Ref Name</th> </tr>
    <%for(int i=0;i<pktDtlList.size();i++){
    HashMap pktDtl = (HashMap)pktDtlList.get(i);
    %>
    <tr>
    <td><%=pktDtl.get("grp")%></td><td><%=pktDtl.get("nw_val")%></td>
    <td><%=pktDtl.get("old_val")%></td><td><%=pktDtl.get("ref_nme")%></td>
    </tr>
    <%}%>
    </table></div>
    <%}%>
    </td></tr>
    </table>
  </body>
</html>