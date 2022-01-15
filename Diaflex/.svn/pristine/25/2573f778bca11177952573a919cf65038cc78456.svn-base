<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page errorPage="../error_page.jsp" %>
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import="ft.com.*,java.util.ArrayList,java.util.HashMap, java.util.Date, java.util.ArrayList, java.util.Enumeration, ft.com.dao.*"%>

<jsp:useBean id="util" class="ft.com.JspUtil" scope="page" />
<jsp:useBean id="info" class="ft.com.InfoMgr" scope="session" />
<%@taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<html>
  <head>
    <title>Web Login And Search Dtl </title>
    <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/bse.css?v=<%=info.getJsVersion()%>"/>
    <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/style.css?v=<%=info.getJsVersion()%>"/>
     <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/bse.js?v=<%=info.getJsVersion()%> " > </script>
     <script language="javaScript" type="text/javascript" src="<%=info.getReqUrl()%>/calendar/calendar.js"></script>
   <link href="<%=info.getReqUrl()%>/calendar/calendar.css" rel="stylesheet" type="text/css" />
   <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/ajax.js?v=<%=info.getJsVersion()%> " > </script>

 <script type="text/javascript">
 <!--
var myVar=setInterval(function(){callwebActivityScreen()},120000);
 -->
</script> 
 </head>

<%
 DBMgr db = new DBMgr();
        DBUtil dbutil = new DBUtil();
        db.setCon(info.getCon());
      dbutil.setDb(db);
      dbutil.setInfo(info);
      db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));db.setDbUsr(info.getDbUsr());
      dbutil.setLogApplNm(info.getLoApplNm());
HashMap dbinfo = info.getDmbsInfoLst();
String cnt = (String)dbinfo.get("CNT");
String group=util.nvl((String)request.getAttribute("group"));
String saleEmp=util.nvl((String)request.getAttribute("saleEmp"));
String dtefr=util.nvl((String)request.getAttribute("dtefr"));
String dteto=util.nvl((String)request.getAttribute("dteto"));
String buyerId=util.nvl((String)request.getAttribute("buyerId"));
String dfUsrtyp=util.nvl((String)info.getDfUsrTyp());
String frame1=info.getReqUrl()+"/website/webLoginAndSrchDtl.do?method=load&group="+group+"&saleEmp="+saleEmp+"&dtefr="+dtefr+"&dteto="+dteto+"&buyerId="+buyerId;
String frame2=info.getReqUrl()+"/website/webLoginAndSrchDtl.do?method=loadSrchDtl&group="+group+"&saleEmp="+saleEmp+"&dtefr="+dtefr+"&dteto="+dteto+"&buyerId="+buyerId;
String frame3=info.getReqUrl()+"/website/webLoginAndSrchDtl.do?method=load&group="+group+"&saleEmp="+saleEmp+"&typ=MOB";
String frame4=info.getReqUrl()+"/website/webLoginAndSrchDtl.do?method=loadSrchDtl&group="+group+"&saleEmp="+saleEmp+"&typ=MOB";
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
<img src="../images/line.jpg" border="0" /></td><td valign="middle"> <span class="pgHed">Web Activity Screen</span> </td>
</tr></table>
</td>
</tr>
<tr><td valign="middle" height="20"> <b><span style="margin-left:30px;">Web User Login Details</span></b></td></tr>
<tr>
<td valign="top" class="hedPg">
<table>
<tr>
<html:form action="website/webLoginAndSrchDtl.do?method=fetch" method="post" >
<%

if(cnt.equals("hk") || cnt.equals("dj")){

   if(cnt.equals("hk")){
   ArrayList grpList = (ArrayList)session.getAttribute("grpcompanyList");
   if(grpList==null || grpList.size()==0){
   grpList=dbutil.groupcompany();
   session.setAttribute("grpcompanyList", grpList);
   }
   if(grpList!=null && grpList.size()> 0){
   %>
      <td>Group Company</td>
      <td><html:select name="webLoginAndSrchDtlForm" property="value(group)" styleId="grp">
      <html:option value="">---Select---</html:option>
      <%
      for(int i=0;i<grpList.size();i++)
      {
      ArrayList grp=(ArrayList)grpList.get(i);
      %>
      <html:option value="<%=(String)grp.get(0)%>"> <%=(String)grp.get(1)%> </html:option>
      <%
      }
      %>
      </html:select>
      </td>
      <%}
      if(!dfUsrtyp.equals("EMPLOYEE")){
      %>
      <td><span class="txtBold" >Sale Person : </span>
      <%
      ArrayList salepersonList = dbutil.saleperson();
      %>
      <html:select name="webLoginAndSrchDtlForm" property="value(saleEmp)" styleId="saleEmp">
      <html:option value="">---Select---</html:option>
      <%
      for(int i=0;i<salepersonList.size();i++)
      {
      ArrayList saleperson=(ArrayList)salepersonList.get(i);
      %>
      <html:option value="<%=(String)saleperson.get(0)%>"> <%=(String)saleperson.get(1)%> </html:option>
      <%
      }
      %>
      </html:select>
      
      </td>
      <%}%>
      <%}%>
      
      <%if(cnt.equals("dj")){%>
      <td>Date From : </td><td>
      <html:text property="value(dtefr)" styleClass="txtStyle"  styleId="dtefr"  size="12"  /> <a href="#" style="margin-top:5px;"  onClick="setYears(1900, '<%=info.getCurrentYear()%>' );showCalender(this, 'dtefr');">
      <img src="<%=info.getReqUrl()%>/images/calender.png"></a></td>
      <td>Date To : </td><td>
      <html:text property="value(dteto)" styleClass="txtStyle"  styleId="dteto"  size="12"  /> <a href="#"  onClick="setYears(1900, '<%=info.getCurrentYear()%>' );showCalender(this, 'dteto');">
      <img src="<%=info.getReqUrl()%>/images/calender.png"></a></td>
      <td>Buyer</td>
      <td nowrap="nowrap">
   
    <%
    String sbUrl ="ajaxRptAction.do?method=buyerSugg";
    %>
    <input type="text" id="nme" name="nme" autocomplete="off" class="sugBox"
      onKeyUp="doCompletion('nme', 'nmePop', 'ajaxRptAction.do?method=buyerSugg', event)" 
      onKeyDown="setDown('nmePop', 'nmeID', 'nme',event)"/>
 <img src="../images/addrow.jpg" width="17" height="17" title="Click To get Names" onclick="doCompletionMobile('nme', 'nmePop', 'ajaxRptAction.do?method=buyerSugg')">
 <input type="hidden" name="nmeID" id="nmeID" value="">
  <div style="position: absolute;">
      <select id="nmePop" name="nmePop"
        style="display:none;300px;" 
        class="sugBoxDiv"
        onKeyDown="setDown('nmePop', 'nmeID', 'nme',event)" 
        onDblClick="setVal('nmePop', 'nmeID', 'nme', event);hideObj('nmePop')" 
        onClick="setVal('nmePop', 'nmeID', 'nme', event);" 
        size="10">
      </select>
    </div>
  </td>
  <%}%>
  
      <td><html:submit property="value(submit)" value="Fetch" styleClass="submit"/></td>
      <%}%>
</html:form>
</tr>
</table>
</td>
</tr>
<tr><td class="tdLayout">

<iframe src="<%=frame1%>"  height="250" width="98%" id="frame1" class="iframe" frameborder="0">
</iframe>

</td></tr>
<tr><td valign="middle" height="20"><b><span style="margin-left:30px;">Web User Search Details</span></b></td></tr>
<tr><td class="tdLayout">

<iframe src="<%=frame2%>"  height="250" width="98%"  id="frame2" class="iframe" frameborder="0">
</iframe>

</td></tr>

<%if(cnt.equals("hk") || cnt.equals("kj")){%>
<tr><td valign="middle" height="20"><b><span style="margin-left:30px;">Mobile User Login Details</span></b></td></tr>
<tr><td class="tdLayout">

<iframe src="<%=frame3%>"  height="250" width="98%"  id="frame3" class="iframe" frameborder="0">
</iframe>

</td></tr>
<tr><td valign="middle" height="20"><b><span style="margin-left:30px;">Mobile User Search Details</span></b></td></tr>
<tr><td class="tdLayout">

<iframe src="<%=frame4%>"  height="250" width="98%"  id="frame4" class="iframe" frameborder="0">
</iframe>

</td></tr>
<%}%>
<tr>
<td><jsp:include page="/footer.jsp" /> </td>
</tr>
</table>
  
  
    <%@include file="/calendar.jsp"%>
  </body>
</html>