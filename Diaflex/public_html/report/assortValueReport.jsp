<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page errorPage="../error_page.jsp" %>
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import="java.util.ArrayList,java.util.ArrayList,java.util.HashMap, java.sql.*, java.util.Enumeration, java.util.logging.Level"%>
<jsp:useBean id="info" class="ft.com.InfoMgr" scope="session" />
<jsp:useBean id="gtMgr" class="ft.com.GtMgr" scope="session" />
<jsp:useBean id="util" class="ft.com.JspUtil" scope="page" />
<%@taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
 
  <html>
  <head>
  <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
  <title>Assort Value Report</title>
  <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/bse.js?v=<%=info.getJsVersion()%> " > </script>
  <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/bse.css?v=<%=info.getJsVersion()%>"/>
  <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/ajax.js?v=<%=info.getJsVersion()%> " > </script>
  <script language="javaScript" type="text/javascript" src="<%=info.getReqUrl()%>/calendar/calendar.js"></script>
  <link href="<%=info.getReqUrl()%>/calendar/calendar.css" rel="stylesheet" type="text/css" />
  <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/style.css?v=<%=info.getJsVersion()%>"/>
  <script language="javaScript" type="text/javascript" src="<%=info.getReqUrl()%>/calendar/calendar.js"></script>
   <link href="<%=info.getReqUrl()%>/calendar/calendar.css" rel="stylesheet" type="text/css" />

  </head>

        <%String logId=String.valueOf(info.getLogId());
        String onfocus="cook('"+logId+"')";
        String lstNme = (String)gtMgr.getValue("lstNmeASSDIFF");
        ArrayList dataDtl = (ArrayList)gtMgr.getValue(lstNme);
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
  Assort Value Report </span> </td></tr></table>
  </td>
  </tr>
    <tr>
  <td valign="top" class="hedPg">
  
    <html:form action="report/assortValueReport.do?method=load" method="post" >

   <table><tr><td>Date From : </td><td>
    <html:text property="value(dteFrm)" styleClass="txtStyle" name="assortLabPenForm" styleId="dtefr"  size="12"  /> <a href="#" style="margin-top:5px;"  onClick="setYears(1900, '<%=info.getCurrentYear()%>' );showCalender(this, 'dtefr');">
                <img src="<%=info.getReqUrl()%>/images/calender.png"></a></td>
                <td>Date To : </td><td>
    <html:text property="value(dteTo)" styleClass="txtStyle" name="assortLabPenForm" styleId="dteto"  size="12"  /> <a href="#"  onClick="setYears(1900, '<%=info.getCurrentYear()%>' );showCalender(this, 'dteto');">
                <img src="<%=info.getReqUrl()%>/images/calender.png"></a></td>
                       <td><html:submit property="value(submit)" value="Fetch" styleClass="submit"/>
</td>
                </tr></table>
           </html:form>     
                
     </td></tr>
   <tr>
   <td valign="top" class="hedPg" >
    <%if(dataDtl!=null && dataDtl.size()>0){%>
    <table class="grid1" cellpadding="2" cellspacing="2">
    <tr><th>Box Type</th><th>Iss Cts</th><th>Rtn Cts</th><th>Iss Val</th><th>Rtn Val</th><th>Diff Val</th> </tr>
    <%for(int i=0;i<dataDtl.size();i++){
    HashMap dtls = (HashMap)dataDtl.get(i);
    String boxTyp = (String)dtls.get("BOXTYP");
    
    %>
    <tr>
    <td><a href="assortValueReport.do?method=loadBoxId&boxTyp=<%=boxTyp%>"> <%=boxTyp%></a></td><td align="right"><%=dtls.get("ISSCTS")%></td><td align="right"><%=dtls.get("RTNCTS")%></td>
    <td align="right"><%=dtls.get("ISSVAL")%></td><td align="right"><%=dtls.get("RTNVAL")%></td><td align="right"><b><%=dtls.get("DIFF")%></b></td>
    </tr>
    <%}%>
    </table>
    <%}else{%>
    Sorry no result found.
    <%}%>
   </td></tr></table> 
     <%@include file="/calendar.jsp"%>

 </body>
</html>