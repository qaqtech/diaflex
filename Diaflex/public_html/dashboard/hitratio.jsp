<%@ page errorPage="../error_page.jsp" %>
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import="java.util.ArrayList,java.util.ArrayList,java.util.HashMap, java.sql.*, java.util.Enumeration,ft.com.dao.MemoType, java.util.logging.Level"%>
<jsp:useBean id="info" class="ft.com.InfoMgr" scope="session" />
<jsp:useBean id="util" class="ft.com.JspUtil" scope="page" />
<%@taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>
 
<html>
  <head>
     <%
  String hdrnme = (String)request.getAttribute("hdrnme_hitratio");
  String  dsalerfsh = (String)request.getAttribute("hitratiorfsh");
  HashMap hitratiodata = (HashMap)request.getAttribute("hitratiodata");
  ArrayList  hitratiomemoTyp = (ArrayList)request.getAttribute("hitratiomemoTyp");
  ArrayList  hitratioprpLst = (ArrayList)request.getAttribute("hitratioprpLst");
  ArrayList  memoTypeList = (ArrayList)session.getAttribute("HitmemoTypeList");
  String  hitratioprp = (String)request.getAttribute("hitratioprp");
  String  hitratioClientTyp = (String)request.getAttribute("hitratioClientTyp");
  String  view = (String)request.getAttribute("view");
  HashMap prp = info.getPrp();
  HashMap mprp = info.getMprp();
  ArrayList prpval = (ArrayList)prp.get(hitratioprp+"V");
  ArrayList prpPrt = (ArrayList)prp.get(hitratioprp+"P");
  String prpDsc = (String)mprp.get(hitratioprp+"D");
  boolean dta=true;
  %>  
  <title>Reports</title>
    <script language="javaScript" type="text/javascript" src="<%=info.getReqUrl()%>/scripts/ajax.js?v=<%=info.getJsVersion()%>"></script>
    <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/bse.css?v=<%=info.getJsVersion()%>"/>
    <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/style.css?v=<%=info.getJsVersion()%>"/>
    <script src="<%=info.getReqUrl()%>/jquery/jquery.min.js"></script>
        <link rel="stylesheet" href="<%=info.getReqUrl()%>/css/jScrollbar.jquery.css" type="text/css" />
	<script src="<%=info.getReqUrl()%>/jquery/jquery.mousewheel.js"></script>
	<script src="<%=info.getReqUrl()%>/jquery/jquery-ui-draggable.js"></script>
        <script src="<%=info.getReqUrl()%>/jquery/jscroll.js"></script>
        <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/bse.js?v=<%=info.getJsVersion()%> " > </script>
  </head>
        <%String logId=String.valueOf(info.getLogId());
        String onfocus="cook('"+logId+"')";
        %>
 <body onfocus="<%=onfocus%>">
<table cellpadding="2" cellspacing="0" width="100%">
<tr><td valign="top"><b style="font-size:10px;"><%=hdrnme%> </b></td></tr>
</table>
<table width="100%"  cellpadding="0" cellspacing="0">
  <tr>  
<%if(view.equals("Y")){
if(hitratiomemoTyp!=null && hitratiomemoTyp.size()>0){
dta=false;
%>
  <tr>
  <td valign="top">
  <div id="main">
  <table class="boardgrid"   style="width:100%">
  <tr>
  <th>Memo Typ/<%=prpDsc%></th>
  <%for (int i=0;i<prpval.size();i++){
  String val=util.nvl((String)prpval.get(i));
  if(hitratioprpLst.contains(val)){
  %>
  <th><%=util.nvl((String)prpPrt.get(i))%></th>
  <%}}%>
  </tr>
  
  <%for (int i=0;i<memoTypeList.size();i++){
  MemoType memotypDtl=(MemoType)memoTypeList.get(i);
  String typ=(String)memotypDtl.getMemo_typ();
  if(hitratiomemoTyp.contains(typ)){
  String memotypDsc=(String)memotypDtl.getDsc();
  %>
  <tr>
  <th><%=memotypDsc%></th>
  <%for (int j=0;j<prpval.size();j++){
  String val=util.nvl((String)prpval.get(j));
  if(hitratioprpLst.contains(val)){
  %>
  <td><%=util.nvl((String)hitratiodata.get(typ+"_"+val))%></td>
  <%}}%>
  </tr>
  <%}}%>
  </table></div>
  </td></tr>
  <%}%>
  <%if(dta){%>
  <tr><td valign="top" class="hedPg">
  Sorry No Result Found</td></tr>
  <%}}%>
  </table>
  </body>
</html>
  
  