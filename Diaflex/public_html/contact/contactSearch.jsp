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
    <title>Contact Search</title>
    <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/ajax.js?v=<%=info.getJsVersion()%>" ></script>
    <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/bse.js?v=<%=info.getJsVersion()%>" ></script>
     <script language="javaScript" type="text/javascript" src="<%=info.getReqUrl()%>/calendar/calendar.js"></script>
   <link href="<%=info.getReqUrl()%>/calendar/calendar.css" rel="stylesheet" type="text/css" />

    <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/bse.css?v=<%=info.getJsVersion()%>"/>
      <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/style.css?v=<%=info.getJsVersion()%>"/>
  </head>
        <%String logId=String.valueOf(info.getLogId());
        String onfocus="cook('"+logId+"')";
        %>
 <body onfocus="<%=onfocus%>" onload="MM_preloadImages('images/faunalogo.png')" onkeypress="return disableEnterKey(event);" topmargin="0" leftmargin="0" rightmargin="0" bottommargin="0">
<table width="100%"  border="0" align="center" cellpadding="0" cellspacing="0" class="mainbg">
  <tr>
    <td height="103" valign="top">
   <jsp:include page="/header.jsp" />
    
    </td>
  </tr>
 
  <tr>
    <td height="5" valign="top" class="greyline"><img src="../images/grey_linebig.png" width="685" height="5" align="right" /></td>
  </tr>
  <tr><td valign="top">
  
  <%
    ArrayList params = new ArrayList();
    ResultSet rs = null;
    
    String formName = "searchcontact";
    String pgTtl = "";//uiForms.getFORM_TTL();
    pgTtl = "Search Contacts"; //pgTtl.replaceAll("~formName", formName);
        DBUtil dbutil = new DBUtil();
    DBMgr db = new DBMgr(); 
    db.setCon(info.getCon());
    dbutil.setDb(db);
    dbutil.setInfo(info);
    db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));db.setDbUsr(info.getDbUsr());
    dbutil.setLogApplNm(info.getLoApplNm());
    String nodata = util.nvl(request.getParameter("nodata"));
    String msg = "", hint = "" ;
    if(nodata.length() > 0) {
        msg = "No Data Found" ;
        hint = "Hint : Either criteria not given or too narrow" ;
    }   
  %>
    <html:form action="/contact/searchContact.do?method=search" method="post" style="margin-left:30px;">  
  <label class="pgTtl"><%=pgTtl%></label>
  <label class="error"><%=msg%></label><br/>
  <label class="hints"><%=hint%></label><br/>
  <table class="grid2">
  <tr><td>Department</td>
  <td><html:select property="dpt" onchange="popEmpOnDpt(this)">
   <%
      String lovQD = "select idn , dept from mdept order by dept";
      HashMap lovKVD = new HashMap();
      ArrayList keys = new ArrayList();
      ArrayList vals = new ArrayList();
      lovKVD = dbutil.getLOV(lovQD);
      keys = (lovKVD.get("K")!= null) ? (ArrayList)lovKVD.get("K"):new ArrayList();
      vals = (lovKVD.get("V")!= null) ? (ArrayList)lovKVD.get("V"):new ArrayList();
    %>  
      <%for(int a=0; a < keys.size(); a++) {
          String lKey = (String)keys.get(a);
          String ldspVal = (String)vals.get(a);%>
          <html:option value="<%=lKey%>"><%=ldspVal%></html:option>
      <%}%>
  </html:select>
  </td>
  </tr>
  <tr><td>Employee</td>
    <td><html:select property="emp" styleId="emp">
    <%
      String lovQ = "select nme_idn, nme from nme_v where typ = 'EMPLOYEE' order by 2";
      HashMap lovKV = new HashMap();
      ArrayList keys = new ArrayList();
      ArrayList vals = new ArrayList();
      lovKV = dbutil.getLOV(lovQ);
      keys = (lovKV.get("K")!= null) ? (ArrayList)lovKV.get("K"):new ArrayList();
      vals = (lovKV.get("V")!= null) ? (ArrayList)lovKV.get("V"):new ArrayList();
    %>  
      <%for(int b=0; b < keys.size(); b++) {
          String lKey = (String)keys.get(b);
          String ldspVal = (String)vals.get(b);%>
          <html:option value="<%=lKey%>"><%=ldspVal%></html:option>
      <%}%>
      </html:select>
    </td>
  </tr>  
  <tr><td>Contact Type</td>
   <td>
      <html:select property="typ">
    <%
      String lovQ = "select distinct typ , typ  from MNME order by 1";
      HashMap lovKV = new HashMap();
      ArrayList keys = new ArrayList();
      ArrayList vals = new ArrayList();
      lovKV = dbutil.getLOV(lovQ);
      keys = (lovKV.get("K")!= null) ? (ArrayList)lovKV.get("K"):new ArrayList();
      vals = (lovKV.get("V")!= null) ? (ArrayList)lovKV.get("V"):new ArrayList();
    %>  
      <%for(int a=0; a < keys.size(); a++) {
          String lKey = (String)keys.get(a);
          String ldspVal = (String)vals.get(a);%>
          <html:option value="<%=lKey%>"><%=ldspVal%></html:option>
      <%}%>
      </html:select>
    
   </td></tr>
  <tr><td>Name</td><td nowrap="nowrap">
   
    <%
    String sbUrl = info.getReqUrl() + "/AjaxAction.do?param=";
    %>
    <input type="text" id="nme" name="nme" autocomplete="off" class="sugBox"
      onKeyUp="doCompletion('nme', 'nmePop', '../ajaxAction.do?1=1', event)"
      onKeyDown="setDown('nmePop', 'nmeID', 'nme',event)" 
      />
      <img src="../images/addrow.jpg" width="17" height="17" title="Click To get Names" onclick="doCompletionMobile('nme', 'nmePop', '../ajaxAction.do?1=1')">
    <html:hidden property="nmeID" styleId="nmeID"/>
  <div style="position: absolute;">
      <select id="nmePop" name="nmePop"
        style="display:none;300px;" 
        class="sugBoxDiv" 
        onKeyDown="setDown('nmePop', 'nmeID', 'nme',event)" 
        onDblClick="setVal('nmePop', 'nmeID', 'nme', event);hideObj('nmePop')" 
        onClick="setVal('nmePop', 'nmeID', 'nme', event)" 
        size="10">
      </select>
</div>
  </td>
  </tr>
  <tr><td>Address</td><td><html:text property="addr"/></td></tr>
  <tr><td>Additional Details</td><td><html:text property="attrDtl"/></td></tr>
 
   <tr><td>Web User</td><td><html:text property="usr"/></td></tr>
  </table><br/>
  <html:submit property="search" value="Search" styleClass="submit"/>
  </html:form>
  
  </td></tr>
  <tr>
     <td><jsp:include page="/footer.jsp" /> </td>
     </tr>
  </table>
   <%@include file="../calendar.jsp"%>
  </body>
</html>  
  