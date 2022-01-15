<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
 
<%@ page errorPage="../error_page.jsp" %>
<%@ page contentType="text/html;charset=windows-1252"%>
<jsp:useBean id="util" class="ft.com.JspUtil" scope="page" />
<jsp:useBean id="info" class="ft.com.InfoMgr" scope="session" />
<%@ page import="java.util.ArrayList,java.util.HashMap,java.sql.ResultSet,ft.com.*, java.util.Date, java.util.ArrayList, ft.com.dao.*"%>
<%@taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>Memo Reports</title>
   <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/bse.css?v=<%=info.getJsVersion()%>"/>
      <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/style.css?v=<%=info.getJsVersion()%>"/>
      <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/ajax.js?v=<%=info.getJsVersion()%> " > </script>
      <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/bse.js?v=<%=info.getJsVersion()%> " > </script>
      <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/memoScript.js?v=<%=info.getJsVersion()%> " > </script>
      <script language="javaScript" type="text/javascript" src="<%=info.getReqUrl()%>/calendar/calendar.js"></script>
   <link href="<%=info.getReqUrl()%>/calendar/calendar.css" rel="stylesheet" type="text/css" />

  </head>
<%
ArrayList vwPrpLst = (ArrayList)request.getAttribute("vwPrpLst");
HashMap dbinfo = info.getDmbsInfoLst();
String cnt = (String)dbinfo.get("CNT");
ArrayList prpDspBlocked = info.getPageblockList();
 String method = util.nvl(request.getParameter("method"));
 String disCheckBoxPart = "display:none";
 String disSelectPart = "display:block";
 boolean isRead = false;
 
 if(method.equals("loadFmt")){
   disCheckBoxPart = "display:block";
   disSelectPart = "display:block";
    isRead = true;
 }
        String logId=String.valueOf(info.getLogId());
        String onfocus="cook('"+logId+"')";
        %>
 <body onfocus="<%=onfocus%>" onload="MM_preloadImages('images/faunalogo.png')" topmargin="0" leftmargin="0" rightmargin="0" bottommargin="0">
<html:form  action="marketing/memoXL.do?method=save" >

<input type="hidden" name="webUrl" id="webUrl" value="<%=dbinfo.get("REP_URL")%>"/>
<input type="hidden" name="cnt" id="cnt" value="<%=cnt%>"/>
<input type="hidden" name="repUrl" id="repUrl" value="<%=dbinfo.get("HOME_DIR")%>"/>
<input type="hidden" name="repPath" id="repPath" value="<%=dbinfo.get("REP_PATH")%>"/>
<html:hidden property="value(isUpdate)" name="memoXLForm" />
<table width="100%"  border="0" align="center" cellpadding="2" cellspacing="0" class="mainbg">
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
  <img src="../images/line.jpg" border="0" /></td><td valign="middle"> <span class="pgHed">Memo Excel</span> </td>
</tr></table>
  </td>
  </tr>
  <%if(request.getAttribute("msg")!=null){%>
  <tr>
  <td class="tdLayout"><span class="redLabel"> <%=request.getAttribute("msg")%></span></td>
  </tr>
  <%}%>

  <tr style="<%=disSelectPart%>" id="exit"><td>
  <table style="margin-left:20px;">
  <tr><td>Select Exiting Memo Excel Format</td><td>
  <html:select property="value(mdl)" styleId="mdlLst" >
  <html:optionsCollection value="mdl" property="value(memoXlList)"  name="memoXLForm" label="mdl"/>
  </html:select>
 
  </td><td><button type="button" class="submit" onclick="loadMemoXLFmt()" name="load" >View / Update Format</button> </td> </tr>
 
  </table>
  </td></tr>
  <tr style="" id="new"><td>
  <table style="margin-left:20px;">
  <tr><td><input type="radio" name="checkALL" id="ALLCheck"  onclick="selectAllReport('check')" />Check ALL &nbsp;&nbsp;<input type="radio" name="checkALL" id="ALLUncheck" onclick="selectAllReport('uncheck')" />Uncheck ALL</td></tr>
  <tr><td>
  <table>
  
  <%int count =0;

  for(int i=0;i< vwPrpLst.size() ;i++){
    String prp = (String)vwPrpLst.get(i);
    if(prpDspBlocked.contains(prp)){
    }else{
    String fldNme= "value("+prp+")";
    String fldId="PRP_"+i;
    if(count==0){%>
     <tr>
   <% }
   count++;
  %>
  <td>
  <table style="margin-left:20px;">
  <tr><td><html:checkbox property="<%=fldNme%>" value="<%=prp%>" styleId="<%=fldId%>" name="memoXLForm" /> </td><td> <%=prp%> </td></tr>
  </table>
 </td>
 <%if(count==10){
 count=0;
 %>
 </tr>
 <%}%>
  <%}}%>
  
  
  </table>
  <input type="hidden" id="count" value="<%=vwPrpLst.size()%>" />
  </td></tr>
  
  <tr><td>Format Name <html:text property="value(formatNme)" readonly="<%=isRead%>" maxlength="20" styleId="formatTxt" name="memoXLForm" onchange="verifyMdl()"  /> 
  
  <button type="submit" class="submit"   name="save" >Save</button> 
    <button type="button" class="submit" onclick="goTo('memoXL.do?method=load','','')" name="load" >Reset</button>
 <%
  ArrayList rolenmLst=(ArrayList)info.getRoleLst();
  String usrFlg=util.nvl((String)info.getUsrFlg());
  if(usrFlg.equals("SYS") || rolenmLst.contains("ADMIN")){
  %>
  &nbsp;&nbsp;<img src="../images/edit.jpg" title="Edit Properties Module" onclick="newWindow('<%=info.getReqUrl()%>/dynamicProperty.do?method=load&mdl=MEMO_VW_XL&sname=vwPrpLst&par=A')" border="0" width="15px" height="15px"/>
  <%}%>
  </td></tr></table>
  </td></tr>
  </table>
  </td>
  </tr>
  </table>
  
  </td></tr></table>
  </td>
  </tr>
  <tr>
    <td><jsp:include page="/footer.jsp" /> </td>
    </tr>
  </table>
  </html:form>
  </body>
</html>