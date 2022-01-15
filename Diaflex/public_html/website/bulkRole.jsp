<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page errorPage="../error_page.jsp" %>
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import="java.util.ArrayList,java.util.HashMap,org.apache.commons.collections.iterators.IteratorEnumeration, java.util.Date, java.util.ArrayList, java.util.Enumeration, ft.com.dao.*"%>

<jsp:useBean id="util" class="ft.com.JspUtil" scope="page" />
<jsp:useBean id="info" class="ft.com.InfoMgr" scope="session" />

<%@taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>Bulk Role </title>
    <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/bse.css?v=<%=info.getJsVersion()%>"/>
    <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/style.css?v=<%=info.getJsVersion()%>"/>
     <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/assortScript.js?v=<%=info.getJsVersion()%> " > </script>
       <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/memoScript.js?v=<%=info.getJsVersion()%> " > </script>
       <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/bse.js?v=<%=info.getJsVersion()%> " > </script>
 </head>
<%


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
<img src="../images/line.jpg" border="0" /></td><td valign="middle"> <span class="pgHed">Bulk Role</span> </td>
</tr></table>
</td>
</tr>
<tr><td>
<html:form  action="/website/webrole.do?method=save" >

  <table class="grid1" style="margin-left:30px;">
      
      <tr>
<td><span class="txtBold" > Select Sale Person : </span>
<%
HashMap saleperson =(HashMap)request.getAttribute("saleperson");
Enumeration e=new IteratorEnumeration(saleperson.keySet().iterator());
%>
<html:select name="BulkRoleForm" property="value(styp)" styleId="saleEmp" onchange="saleperson()" >
<html:option value="0">---Select---</html:option>
<%
for(int i=0;i<saleperson.size();i++)
{
String idn=String.valueOf(e.nextElement());
String name=(String)saleperson.get(idn);
%>
<html:option value="<%=idn%>"> <%=name%> </html:option>
<%
}
%>
</html:select>

</td>
</tr>
  </table>
</html:form>
</td></tr>
<tr>
<td class="tdLayout" style="display:none" id="frameDiv" width="100%" height="90%" valign="top">
           <iframe name="subContact"  height="320" width="95%" class="iframe" frameborder="0">
  
     </iframe>
    </td>
</tr>
  <tr>
    <td><jsp:include page="/footer.jsp" /> </td>
    </tr>
  </table>
  </body>
</html>