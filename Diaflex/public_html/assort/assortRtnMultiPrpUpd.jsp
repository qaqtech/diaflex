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
        <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/general.css?v=<%=info.getJsVersion()%>"/>

     <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/bse.css?v=<%=info.getJsVersion()%>"/>
      <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/style.css?v=<%=info.getJsVersion()%>"/>
      <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/assortScript.js?v=<%=info.getJsVersion()%> " > </script>
 <script type="text/javascript" src="<%=info.getReqUrl()%>/PopScripts/popup.js?v=<%=info.getJsVersion()%> " > </script>
     <script src="<%=info.getReqUrl()%>/PopScripts/jquery-1.2.6.min.js" type="text/javascript"></script>
    <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/bse.js?v=<%=info.getJsVersion()%> " > </script>
  </head>
        <%String logId=String.valueOf(info.getLogId());
        String onfocus="cook('"+logId+"')";
        %>
 <body onfocus="<%=onfocus%>" >
 <div id="backgroundPopup"></div>
<div id="popupContactSave" >
<img src="../images/loadbig.gif" />
</div>
  
  <html:form action="assort/assortReturn.do?method=bulkUpdate" method="post">
  <%
  //util.updAccessLog(request,response,"Assort Return", "Assort Return Muti Upd");
  HashMap mprp = info.getMprp();
  HashMap prp = info.getPrp();
  ArrayList multiPrpList = (ArrayList)session.getAttribute("prpList");
      String lstNme = (String)request.getAttribute("lstNme");
    String prcID = (String)request.getAttribute("prcID");
  if(multiPrpList!=null && multiPrpList.size()>0){%>
  <table style="height:300px">

 <tr><td valign="top" height="5%"> <label id="msg" class="redLabel"></label></td></tr>

<tr><td valign="top" height="95%">
  <div class="memo">
  <table border="0" width="200" class="Orange"  cellspacing="1" cellpadding="1" >
  <tr><th  class="Orangeth">Properties</th><th  class="Orangeth">Value</th></tr>
  <%for(int i=0;i<multiPrpList.size();i++){
  String lprp = (String)multiPrpList.get(i);
  String prpDsc = (String)mprp.get(lprp);
  String prpTyp = util.nvl((String)mprp.get(lprp+"T"));
  String fldName = "value("+lprp+")";
    String bulkUpdate = "bulkRtnUpdate(this,'"+lprp+"','"+lstNme+"','"+prcID+"')";
  %>
  <tr><td><%=prpDsc%></td><td>
  <%if(prpTyp.equals("C")){
    ArrayList prpValLst =(ArrayList)prp.get(lprp+"V");%>
  <html:select property="<%=fldName%>" styleId="<%=lprp%>"  onchange="<%=bulkUpdate%>" name="assortReturnForm" >
  <html:option value="0">--select--</html:option>
  <%
  for(int j=0;j<prpValLst.size();j++){
  String prpVal = (String)prpValLst.get(j);
  %>
  <html:option value="<%=prpVal%>"><%=prpVal%></html:option>
  <%}%>
  </html:select>
  <%}else{%>
  <html:text property="<%=fldName%>" size="5"  styleId="<%=lprp%>" onchange="<%=bulkUpdate%>" name="assortReturnForm"/>
  <%}%>
  </td> </tr>
  <%}%>
  </table></div></td></tr></table>
  <%}%>
  </html:form>
  </body>
</html>