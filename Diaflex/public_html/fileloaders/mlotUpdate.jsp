<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page errorPage="../error_page.jsp" %>
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import="java.util.ArrayList,java.util.HashMap,java.sql.ResultSet,ft.com.*, java.util.Date, java.util.ArrayList, ft.com.dao.*"%>
<jsp:useBean id="util" class="ft.com.JspUtil" scope="page" />
<jsp:useBean id="info" class="ft.com.InfoMgr" scope="session" />
<jsp:useBean id="gtMgr" class="ft.com.GtMgr" scope="session" />
<%@taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>
<%@taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
 
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>Mlot</title>
    <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/bse.css?v=<%=info.getJsVersion()%>"/>
    <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/style.css?v=<%=info.getJsVersion()%>"/>
    <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/general.css?v=<%=info.getJsVersion()%>"/>
     <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/bse.js?v=<%=info.getJsVersion()%> " > </script>
            <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/Validation.js?v=<%=info.getJsVersion()%> " > </script>

  </head>
     <%String logId=String.valueOf(info.getLogId());
        String onfocus="cook('"+logId+"')";
          String lstNme = (String)gtMgr.getValue("lstNmeMLOT");
    HashMap mlotLst = (HashMap)gtMgr.getValue(lstNme);
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
  <img src="../images/line.jpg" border="0" /></td><td valign="middle"> <span class="pgHed">
 Lot Master
 </span> </td>
</tr></table>
  </td>
  </tr>
    <tr><td class="tdLayout" valign="top">
   <table cellpadding="2" cellspacing="2"><tr><td>
   <html:form action="fileloaders/mlotUpdate.do?method=save" method="post" >
   </html:form> <!--this form tag not in use but don't remove  tag add for scripts chksrvAll -->
    <html:form action="fileloaders/mlotUpdate.do?method=save" onsubmit="return sumbitFormConfirm('cb_','1','Are you sure you want to save changes?','Please select Lot.','checkbox')" method="post" >
    <table>
    <tr><td><html:submit property="value(save)" styleClass="submit"  value="Save Chnages"/> </td></tr>
    <tr><td>
    <%
  
    ArrayList modLst = (ArrayList)mlotLst.get("MOD");
    if(modLst!=null && modLst.size()>0){%>
    <table class="grid1"><tr><th><input type="checkbox" name="all" id="all" onclick="checkAllpage(this,'cb_')"/> </th> <th>Dsc</th><th>Date</th>
    <th>RO   <html:select property="value(RO)"  styleId="RO" name="mlotUpdateForm"  onchange="chksrvAll('RO')"    >
           <html:option value="0" >--select--</html:option>
            <html:optionsCollection property="prpRoLst" name="mlotUpdateForm" 
            label="dsc" value="dsc" />
    </html:select></th>
    <th>MINING
    
       <html:select property="value(MIN)"  styleId="MIN" name="mlotUpdateForm"  onchange="chksrvAll('MIN')" >
           <html:option value="0" >--select--</html:option>
            <html:optionsCollection property="prpMinLst" name="mlotUpdateForm" 
            label="dsc" value="dsc" />
    </html:select>
    </th></tr>
    <%for(int i=0;i<modLst.size();i++){
    ObjBean mlot = (ObjBean)modLst.get(i);
    String idn = util.nvl((String)mlot.getValue("idn"));
    String roId = "RO_"+idn;
    String minId = "MIN_"+idn;
    String roNme = "value("+roId+")";
    String minNme = "value("+minId+")";
    %>
    <tr><td><input type="checkbox" name="cb_<%=idn%>" value="<%=idn%>" id="cb_<%=idn%>" /> </td>
    <td><%=mlot.getValue("dsc")%> </td>  <td><%=mlot.getValue("dte")%></td>
    <td> 
      <html:select property="<%=roNme%>"  styleId="<%=roId%>" name="mlotUpdateForm"  >
           <html:option value="0" >--select--</html:option>
            <html:optionsCollection property="prpRoLst" name="mlotUpdateForm" 
            label="dsc" value="dsc" />
    </html:select>
    </td>  
    <td>
      <html:select property="<%=minNme%>"  styleId="<%=minId%>" name="mlotUpdateForm"   >
           <html:option value="0" >--select--</html:option>
            <html:optionsCollection property="prpMinLst" name="mlotUpdateForm" 
            label="dsc" value="dsc" />
    </html:select>
    </td>  
    </tr>
     <%}%></table>
     <%}else{%>
     Sorry no result found.
     <%}%>
     </td></tr></table>
     </html:form>
     
     </td>
     <td valign="top">
     <table>
    <tr><td><b> Last 30 days updated Lot</b> </td></tr>
    <tr><td>
        <% ArrayList dspLst = (ArrayList)mlotLst.get("DSP");
    if(dspLst!=null && dspLst.size()>0){%>
    <table class="grid1"><tr> <th>Dsc</th><th>Date</th><th>RO</th><th>MINING</th><th>Mod User</th><th>Mod Date</th></tr>
    <%for(int i=0;i<dspLst.size();i++){
    ObjBean mlot = (ObjBean)dspLst.get(i);
    String idn = util.nvl((String)mlot.getValue("idn"));
    %>
    <tr>
    <td><%=mlot.getValue("dsc")%> </td>  <td><%=mlot.getValue("dte")%></td>
      <td><%=mlot.getValue("ro")%> </td>  <td><%=mlot.getValue("mining")%></td>
          <td><%=mlot.getValue("mod_usr")%> </td>  <td><%=mlot.getValue("mod_dte")%></td>
    </tr>
     <%}%></table>
      <%}else{%>
     Sorry no result found.
     <%}%>
     </td>
     </tr></table></td></tr></table>
     </td></tr>
       
    </table>
    </body>
</html>