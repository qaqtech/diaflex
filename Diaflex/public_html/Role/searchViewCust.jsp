<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page errorPage="../error_page.jsp" %>
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import="java.util.ArrayList,java.util.HashMap,java.sql.ResultSet,ft.com.*, java.util.Date, java.util.ArrayList, ft.com.dao.*"%>
<jsp:useBean id="info" class="ft.com.InfoMgr" scope="session" />
<jsp:useBean id="util" class="ft.com.JspUtil" scope="page" />

<%@taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>User Details</title>
  <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/bse.css?v=<%=info.getJsVersion()%>"/>
    <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/style.css?v=<%=info.getJsVersion()%>"/>
     <script language="javaScript" type="text/javascript" src="<%=info.getReqUrl()%>/calendar/calendar.js"></script>
   <link href="<%=info.getReqUrl()%>/calendar/calendar.css" rel="stylesheet" type="text/css" />
   <script language="javaScript" type="text/javascript" src="<%=info.getReqUrl()%>/scripts/bse.js?v=<%=info.getJsVersion()%>"></script>
  <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/ajax.js?v=<%=info.getJsVersion()%>" ></script>
  </head>
        <%String logId=String.valueOf(info.getLogId());
        String onfocus="cook('"+logId+"')";
        %>
 <body onfocus="<%=onfocus%>" onload="MM_preloadImages('images/faunalogo.png')"  onkeypress="return disableEnterKey(event);"  topmargin="0" leftmargin="0" rightmargin="0" bottommargin="0">
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
  <img src="../images/line.jpg" border="0" /></td><td valign="middle"> <span class="pgHed">Search Result View</span> </td>
</tr></table>
  </td>
  </tr>
    <tr><td class="tdLayout" valign="top">
    <html:form action="role/srchCustViewAction.do?method=save" method="post" onsubmit="return collectValue()">
     <html:hidden property="value(usrIdn)" name="searchCustViewForm" />
      <table  cellpadding="2"  cellspacing="2">
      <tr><td>
      <html:submit property="value(save)" value="Save Changes" styleClass="submit"/>
       </td></tr>
         <tr><td>
    <table class="grid1" cellpadding="2" cellspacing="2">
    <tr><th>From<th><th>To</th></tr>
    <tr><td>
    <html:select property="leftPrp" name="searchCustViewForm" multiple="true" style="height:500px;width:90px" styleId="leftListXL" size="10">
    <html:optionsCollection property="lefXlPrp" label="prp" value="prp"/>
    </html:select>
    
    </td>
    <td>
     <table>
       <tr><td align="center"><button type="button" class="submit" name="moveAll" onclick="moveALL('L')">ALL >> </button></td></tr>
        <tr><td align="center"><button type="button"  class="submit" onclick="MoveUP('XL')">UP </button> </td></tr>
       <tr><td align="center"> <button type="button"  class="submit" onclick="fnMoveItemslr('XL')" > >> </button></td></tr>
       <tr><td align="center">  <button type="button"  class="submit" onclick="fnMoveItemsrl('xlPrp')"> << </button></td></tr>
        <tr><td align="center"><button type="button"  class="submit" onclick="MoveDown('XL')">DOWN </button></td></tr>
         <tr><td align="center"><button type="button"  class="submit" name="moveAll" onclick="moveALL('R')"><< ALL</button></td></tr>
        </table>
    
    </td>
    <td>
     <html:select property="rightPrp" name="searchCustViewForm" multiple="true" style="height:500px;width:90px" styleId="rightListXL" size="10">
    <html:optionsCollection property="rtXlPrp" label="prp" value="prp"/>
    </html:select>
    
  
    
    </td> </tr>
    </table></td></tr></table>
    </html:form>
    </td></tr>
     <tr>
    <td><jsp:include page="/footer.jsp" /> </td>
    </tr>
    </table>
    </body>
</html>