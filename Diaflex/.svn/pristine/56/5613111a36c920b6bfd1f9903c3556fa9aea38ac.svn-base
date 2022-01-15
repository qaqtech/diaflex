<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page errorPage="../error_page.jsp" %>
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.ResultSet, java.util.ArrayList,java.util.Collections,java.util.ArrayList,java.util.HashMap,java.text.SimpleDateFormat, java.util.Calendar"%>

<jsp:useBean id="info" class="ft.com.InfoMgr" scope="session"/>
<jsp:useBean id="util" class="ft.com.JspUtil" scope="page" />
<%@taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>
<%@taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<html> 
   <head>
        <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/bse.js?v=<%=info.getJsVersion()%>" ></script>
        <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
        <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/style.css?v=<%=info.getJsVersion()%>"/>
         <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/bse.css?v=<%=info.getJsVersion()%>"/>
        <script type="text/javascript"  src="<%=info.getReqUrl()%>/scripts/bse_prc.js"></script>
         <script type="text/javascript"  src="<%=info.getReqUrl()%>/scripts/pri.js?v=<%=info.getJsVersion()%>"></script>
           <script type="text/javascript"  src="<%=info.getReqUrl()%>/scripts/Validation.js"></script>
         <script type="text/javascript"  src="<%=info.getReqUrl()%>/PopScripts/popup.js?v=<%=info.getJsVersion()%>"></script>
            <script src="<%=info.getReqUrl()%>/PopScripts/jquery-1.2.6.min.js" type="text/javascript"></script>
             <script src="<%=info.getReqUrl()%>/jqueryScript/jqueryScript.js?v=<%=info.getJsVersion()%>" type="text/javascript"></script>
              <script src="<%=info.getReqUrl()%>/jqueryScript/jquery.min.js" type="text/javascript"></script>
   
  <title>Mix Clarity Mapping</title>
 
  </head>
  
  <%
  
  HashMap prp = info.getPrp();
  String view = (String)request.getAttribute("view");
  String criteria = util.nvl((String)request.getAttribute("criteria"));
  String display = (String)request.getAttribute("display");
  boolean disabled= false;
  if(view!=null)
  disabled= true;
  ArrayList deptList = (ArrayList)prp.get("DEPTV");
  ArrayList colList = (ArrayList)prp.get("COLV");
  ArrayList clrList = (ArrayList)prp.get("CLRV");
  ArrayList mixclrList = (ArrayList)prp.get("MIX_CLARITYV");
        String logId=String.valueOf(info.getLogId());
        String onfocus="cook('"+logId+"')";
        %>
 <body onfocus="<%=onfocus%>" onload="MM_preloadImages('images/faunalogo.png')" topmargin="0" leftmargin="0" rightmargin="0" bottommargin="0">
<div id="backgroundPopup"></div>
<div id="popupContactSave" align="center" >
<img src="../images/loading_flame.gif" height="150px" width="150px" border="0" />
</div>
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
  <img src="../images/line.jpg" border="0" /></td><td valign="middle"> <span class="pgHed">Mix Clarity Mapping</span> </td>
</tr></table>
  </td>
  </tr>
<html:form action="/mix/mixClrMappingnew.do?method=fetchview"  method="POST">
   <input type="hidden" name="TXTID" id="TXTID" value="" />
<tr><td valign="top" class="hedPg">
  <table  class="grid1">
  <tr>
  <td>Department</td>
    <td>
    <html:select property="value(dept)" styleId="dept"   >
    <%for(int i=0;i<deptList.size();i++){
    String deptprp=(String)deptList.get(i);%>
    <html:option value="<%=deptprp%>" ><%=deptprp%></html:option> 
    <%}%>   
    </html:select>
    </td>
  </tr>
  <tr>
  <td>Weight From</td>
  <td><html:text property="value(wtfr)" styleId="wtfr" name="mixClrMappingform" readonly="<%=disabled%>" /></td>
  </tr>
  <tr>
  <td>Weight To</td>
  <td><html:text property="value(wtto)" styleId="wtto" name="mixClrMappingform" readonly="<%=disabled%>"  /></td>
  </tr>
   <tr>
   <td colspan="2" align="center"><html:submit property="value(srch)" value="Fetch" styleClass="submit" onclick="return validatemixclrmapp()" />
   </td>
   </tr>
   </table>
</td>
</tr>
<tr>
<td valign="top" class="hedPg">
<html:submit property="value(save)" value="Save changes" styleClass="submit" onclick="loading()" />
<%if(!criteria.equals("")){%>
<span class="txtLink" ><%=criteria%></span>
<%}%>
<td>
<div id="MIX_CLARITY" class="floatingDiv">
  <div align="right"> 
  <span class="img" onclick="hideDiv('MIX_CLARITY')"> Close</span>
  </div>
  <div class="priDiv">
   <table><tr><td></td><td>Value</td></tr>
   <tr>
   <td><input type="radio" value="" id="FR_0" name="FR" onclick="setTXTVal(this,'FR','0')"/></td>
   <td>EMPTY</td>
  </tr>
  <%for(int j=0;j< mixclrList.size();j++){
  String val = (String)mixclrList.get(j);  
  %>
  
  <tr><td>
  <input type="radio" value="<%=val%>" id="FR_<%=j%>" name="FR" onclick="setTXTVal(this,'FR','<%=j%>')"/></td><td> <%=val%></td>
  </tr>
  <%}%>
 </table></div>
  </div>
  </td>
</tr>

<tr><td valign="top" class="hedPg">
<table class="grid1">
<tr>
<th>Color/Clarity</th>
<%for(int i=0;i<clrList.size();i++){
String clrv=util.nvl((String)clrList.get(i));
boolean isDis1 = true;
    if(clrv.indexOf("+")!=-1)   
     isDis1 = false;
     if(clrv.indexOf("-")!=-1)   
     isDis1 = false;
    if(isDis1){ 
%>
<th><%=clrList.get(i)%></th>
<%}}%>
</tr>
<%for(int i=0;i<colList.size();i++){
String col=(String)colList.get(i);
boolean isDis1 = true;
    if(col.indexOf("+")!=-1)   
     isDis1 = false;
     if(col.indexOf("-")!=-1)   
     isDis1 = false;
    if(isDis1){ 
%>
<tr>
<th><%=col%></th>
<%
for(int j=0;j<clrList.size();j++){
String clr=(String)clrList.get(j);
boolean isDis2 = true;
    if(clr.indexOf("+")!=-1)   
     isDis2 = false;
     if(clr.indexOf("-")!=-1)   
     isDis2 = false;
    if(isDis2){ 
String fldNm = "value("+col+"_"+clr+")";
String fldId =col+"_"+clr;
%>
<td><html:text property="<%=fldNm%>" styleId="<%=fldId%>" styleClass="txtStyle" onclick="showDivComm('MIX_CLARITY',this)" size="5"/></td>
<%}}%>
</tr>
<%}}%>
</table>
</td>
</tr>
</html:form>
  <tr><td>
<jsp:include page="/footer.jsp" />
</td></tr>
  </table>  
  </body>
</html>