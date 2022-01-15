<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
 
<%@ page errorPage="../error_page.jsp" %>
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import="java.util.ArrayList,java.util.HashMap,java.sql.ResultSet,ft.com.*, java.util.Date, java.util.ArrayList, ft.com.dao.*"%>
<jsp:useBean id="util" class="ft.com.JspUtil" scope="page" />
<jsp:useBean id="info" class="ft.com.InfoMgr" scope="session" />
<%@taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>
<%@taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>Merge Memo</title>
    <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/bse.css?v=<%=info.getJsVersion()%>"/>
     <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/general.css?v=<%=info.getJsVersion()%>"/>
      <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/style.css?v=<%=info.getJsVersion()%>"/>
      <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/ajax.js?v=<%=info.getJsVersion()%> " > </script>
      <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/bse.js?v=<%=info.getJsVersion()%> " > </script>
      <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/memoScript.js?v=<%=info.getJsVersion()%> " > </script>
          <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/priceChange.js?v=<%=info.getJsVersion()%> " > </script>
    <script type="text/javascript" src="<%=info.getReqUrl()%>/PopScripts/popup.js?v=<%=info.getJsVersion()%> " > </script>
<script src="<%=info.getReqUrl()%>/PopScripts/jquery-1.2.6.min.js" type="text/javascript"></script>
    <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/assortScript.js?v=<%=info.getJsVersion()%> " > </script>
    <script language="javaScript" type="text/javascript" src="<%=info.getReqUrl()%>/calendar/calendar.js"></script>
   <link href="<%=info.getReqUrl()%>/calendar/calendar.css" rel="stylesheet" type="text/css" />
  </head>
<%
        String logId=String.valueOf(info.getLogId());
        String onfocus="cook('"+logId+"')";
        %>
 <body onfocus="<%=onfocus%>" onload="MM_preloadImages('images/faunalogo.png')" topmargin="0" leftmargin="0" rightmargin="0" bottommargin="0">
 <div id="backgroundPopup"></div>

<div id="popupContactSave" align="center" >
<img src="../images/loading_flame.gif" height="150px" width="150px" border="0" />
</div>
<html:form action="marketing/memoReturn.do?method=mergeMemo" method="post" onsubmit="loading();">
<table width="100%"  border="0"  cellpadding="0" cellspacing="0" class="mainbg">
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
  <table cellpadding="1"  cellspacing="5"><tr><td valign="middle">
  <img src="../images/line.jpg" border="0" /></td><td valign="middle"> <span class="pgHed">Merge Memo</span> </td>
</tr></table>
  </td>
  </tr>
  <tr>
  <td class="tdLayout" valign="top">
  <html:hidden name="memoReturnForm" styleId="nmeIdn" property="value(nmeIdn)"  />
  <html:hidden name="memoReturnForm" styleId="typ" property="value(typ)"  />
    <html:hidden name="memoReturnForm" styleId="MemoTyp" property="value(MemoTyp)"  />
       <html:hidden name="memoReturnForm" styleId="PKT_TY" property="value(PKT_TY)"  />
  <table class="grid1" >
  <tr><th align="left" colspan="2">Terms</th></tr>
  <tr><td>Select Terms</td><td>
  <html:select property="terms" styleId="trms" onchange="callMemoMerge()" name="memoReturnForm" >
  <html:option value="0">---select---</html:option>
  <html:optionsCollection property="trmsLst" name="memoReturnForm"  label="trmDtl" value="relId" />
  </html:select>
  </td> </tr>
  
  <tr><td>Select Type</td><td>
  <html:select property="type" styleId="type"  onchange="callMemoMerge()" name="memoReturnForm" >
  <html:option value="0">---select---</html:option>
  <html:optionsCollection property="typeLst" name="memoReturnForm"  label="typeDtl" value="typeDtl" />
  </html:select>
  </td> </tr>
  
  </table>
  </td>
  </tr>
  <tr><td><div id="btnMge" class="tdLayout" style="display:none;padding-top:5px">
  <html:submit property="sumbit" styleClass="submit" value="Merge Memo" />&nbsp;&nbsp;&nbsp;
  <b>Remark/Comment</b>&nbsp;<html:text property="value(rmk)" size="15"  />&nbsp;&nbsp;&nbsp;
<%ArrayList notepersonList = ((ArrayList)info.getNoteperson() == null)?new ArrayList():(ArrayList)info.getNoteperson();
if(notepersonList.size()>0){%>
&nbsp;Note Person:&nbsp;
      <html:select name="memoReturnForm" property="value(noteperson)" styleId="noteperson">
      <html:option value="">---Select---</html:option><%
      for(int i=0;i<notepersonList.size();i++){
      ArrayList noteperson=(ArrayList)notepersonList.get(i);%>
      <html:option value="<%=(String)noteperson.get(0)%>"> <%=(String)noteperson.get(1)%> </html:option>
      <%}%>
      </html:select>
<%}%>
  </div>
  </td></tr>
  <tr><td class="tdLayout" valign="top">
  
    <div id="memoDtl" style="padding-top:10px;"  >
    
      </div>
  </td>
  </tr>
  
</table>
</html:form>
</body></html>