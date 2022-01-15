<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page errorPage="../error_page.jsp" %>
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import="java.util.ArrayList,java.util.HashMap,java.sql.ResultSet,ft.com.*, java.util.Date, java.util.Enumeration, java.util.ArrayList, ft.com.dao.*"%>

<jsp:useBean id="util" class="ft.com.JspUtil" scope="page" />
<jsp:useBean id="info" class="ft.com.InfoMgr" scope="session" />

<%@taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<%@taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>Mixing Size Clarity</title>
  <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/bse.css?v=<%=info.getJsVersion()%>"/>
    <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/style.css?v=<%=info.getJsVersion()%>"/>
     <script language="javaScript" type="text/javascript" src="<%=info.getReqUrl()%>/scripts/ajax.js?v=<%=info.getJsVersion()%>"></script>
       <script language="javaScript" type="text/javascript" src="<%=info.getReqUrl()%>/calendar/calendar.js"></script>
   <link href="<%=info.getReqUrl()%>/calendar/calendar.css" rel="stylesheet" type="text/css" />
   <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/bse.js?v=<%=info.getJsVersion()%> " > </script>

  </head>
<%

  HashMap prp = info.getPrp();
  HashMap mprp = info.getMprp();
  ArrayList prpPrtSize=null;
  ArrayList prpValSize=null;
  ArrayList prpSrtSize = null;
  ArrayList prpPrtClr=null;
  ArrayList prpValClr=null;
  ArrayList prpSrtClr = null;
        String logId=String.valueOf(info.getLogId());
        String onfocus="cook('"+logId+"')";
        %>
 <body onfocus="<%=onfocus%>" onload="MM_preloadImages('images/faunalogo.png')" topmargin="0" leftmargin="0" rightmargin="0" bottommargin="0">
<table width="100%"  border="0" align="center" cellpadding="0" cellspacing="0">
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
  <img src="../images/line.jpg" border="0" /></td><td valign="middle"> <span class="pgHed">Mixing Size Clarity</span> </td>
</tr></table>
  </td>
  </tr>
  <tr><td class="tdLayout" valign="top">
 <table><tr><td>
<table class="grid1"><tr> 
<th>Clarity/Size</th>
<% 
              prpPrtSize = (ArrayList)prp.get("MIX_SIZE"+"P");
              prpSrtSize = (ArrayList)prp.get("MIX_SIZE"+"S");
              prpValSize = (ArrayList)prp.get("MIX_SIZE"+"V");
              for(int i=0;i<prpValSize.size();i++){
              String Sizevall = (String)prpValSize.get(i);
              %>
              <th><%=Sizevall%></th>
              <%}%>
              </tr>
              <%prpPrtClr=new ArrayList();
              prpValClr=new ArrayList();
              prpSrtClr =new ArrayList();
              prpPrtClr = (ArrayList)prp.get("MIX_CLARITY"+"P");
              prpSrtClr = (ArrayList)prp.get("MIX_CLARITY"+"S");
              prpValClr = (ArrayList)prp.get("MIX_CLARITY"+"V");
              for(int j=0;j<prpValClr.size();j++){
              String Clrvall = (String)prpValClr.get(j);
              String Clrsrt = (String)prpSrtClr.get(j);
              %>
              <tr><th><%=Clrvall%></th>
              <%
              for(int k=0;k<prpValSize.size();k++){
              String Sizesrt = (String)prpSrtSize.get(k);
              String fldName = Clrsrt+"_SZCLR_"+Sizesrt;
              String fldVal = "value("+fldName+")";
                   String onChange = "saveSizeClr("+Clrsrt+","+Sizesrt+",this)";
              %>
                <td><html:checkbox  property="<%=fldVal%>" value="<%=fldName%>" name="mixSizeClrform" onchange="<%=onChange%>"/></td>
                <%}%>
                </tr>
                <%}%>            
</table>
</td>
</tr>
</table>
</td>
</tr>
</table>  
</body>
</html>