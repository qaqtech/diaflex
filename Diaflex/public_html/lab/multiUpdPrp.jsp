<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page errorPage="../error_page.jsp" %>
<%@ page contentType="text/html;charset=windows-1252"%>
<jsp:useBean id="info" class="ft.com.InfoMgr" scope="session" />
<jsp:useBean id="util" class="ft.com.JspUtil" scope="page" />

<%@ page import="java.util.ArrayList,java.util.HashMap,java.sql.ResultSet,ft.com.*, java.util.Date, java.util.ArrayList, ft.com.dao.*"%>
<%@taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>multiUpdPrp</title>
     <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/bse.css?v=<%=info.getJsVersion()%>"/>
      <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/style.css?v=<%=info.getJsVersion()%>"/>
      <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/assortScript.js?v=<%=info.getJsVersion()%> " > </script>
       <script type="text/javascript" src="<%=info.getReqUrl()%>/PopScripts/popup.js?v=<%=info.getJsVersion()%> " > </script>
   <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/bse.js?v=<%=info.getJsVersion()%> " > </script>
  </head>
        <%String logId=String.valueOf(info.getLogId());
        String onfocus="cook('"+logId+"')";
        %>
 <body onfocus="<%=onfocus%>" >
  
 <html:form action="lab/labComparisonRtn.do?method=bulkUpdate" method="post">
  <%
  HashMap mprp = info.getMprp();
  HashMap prp = info.getPrp();
  ArrayList multiPrpList = (ArrayList)session.getAttribute("prpList");
  String prcId = util.nvl((String)request.getAttribute("prcId"));
  if(multiPrpList!=null && multiPrpList.size()>0){%>
  <table style="height:300px">
 <tr><td height="5%">&nbsp;&nbsp;&nbsp;<html:submit property="value(update)" styleClass="submit"   value="Update Properties"  />&nbsp;&nbsp;&nbsp;  </td></tr>
 <%String msg = (String)request.getAttribute("msg"); 
 if(msg!=null){
 %>
 <tr><td valign="top" height="5%"><%=msg%></td></tr>
 <%}%>
<tr><td valign="top" height="95%">
  <div class="memo">
  <table border="0" width="200" class="Orange"  cellspacing="1" cellpadding="1" >
  <tr><th  class="Orangeth">Properties</th><th  class="Orangeth">Value</th></tr>
  <%for(int i=0;i<multiPrpList.size();i++){
  String lprp = (String)multiPrpList.get(i);
  String prpDsc = (String)mprp.get(lprp);
  String prpTyp = util.nvl((String)mprp.get(lprp+"T"));
  String fldName = "value("+lprp+")";
  
  %>
  <tr><td><%=prpDsc%></td><td>
  <%if(prpTyp.equals("C")){
    ArrayList prpValLst =(ArrayList)prp.get(lprp+"V");%>
  <html:select property="<%=fldName%>" styleId="<%=lprp%>"  name="labComparisonRtnForm" >
  <html:option value="0">--select--</html:option>
  <%
  for(int j=0;j<prpValLst.size();j++){
  String prpVal = (String)prpValLst.get(j);
  %>
  <html:option value="<%=prpVal%>"><%=prpVal%></html:option>
  <%}%>
  </html:select>
  <%}else{%>
  <html:text property="<%=fldName%>" size="5"  name="labComparisonRtnForm"/>
  <%}%>
  </td> </tr>
  <%}%>
  </table></div></td></tr></table>
  <%}%>
  </html:form>
  </body>
</html>