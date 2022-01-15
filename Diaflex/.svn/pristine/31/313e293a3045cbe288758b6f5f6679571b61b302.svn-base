<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
 
<%@ page errorPage="../error_page.jsp" %>
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import="java.util.ArrayList,java.util.HashMap,java.sql.ResultSet,ft.com.*, java.util.Date, java.util.ArrayList, ft.com.dao.*"%>
<jsp:useBean id="util" class="ft.com.JspUtil" scope="page" />
<jsp:useBean id="info" class="ft.com.InfoMgr" scope="session" />
<%@taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>
<%@taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>Stock Properties Update</title>
    <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/bse.css?v=<%=info.getJsVersion()%>"/>
      <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/style.css?v=<%=info.getJsVersion()%>"/>
      <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/memoScript.js?v=<%=info.getJsVersion()%> " > </script>
       <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/bse.js?v=<%=info.getJsVersion()%> " > </script>
        </head>
        <%String logId=String.valueOf(info.getLogId());
        String onfocus="cook('"+logId+"')";
        %>
 <body onfocus="<%=onfocus%>">
  <%
  ArrayList lookupMdlList = new ArrayList();
  lookupMdlList.add("LOOKUP_BSC_LST");
  lookupMdlList.add("LOOKUP_ADV_LST");
  lookupMdlList.add("LOOKUP_FIX_LST");
  HashMap lookupLstMap = new HashMap();
  lookupLstMap.put("LOOKUP_BSC_LST", "Basic");
  lookupLstMap.put("LOOKUP_ADV_LST", "Advance");
  lookupLstMap.put("LOOKUP_FIX_LST", "Fixed");
   HashMap stockPrpUpd = (HashMap)request.getAttribute("labDtlList");
   String mstkIdn = request.getParameter("mstkIdn");
   ArrayList grpList = (ArrayList)request.getAttribute("grpList");
 
   HashMap mprp = info.getMprp();
    HashMap prp = info.getPrp();
   if(grpList!=null && grpList.size() >0){
   String lprp="";
  %>
   
  <table cellpadding="0" cellspacing="0">
 <tr><td>
 <table><tr>
 <%for(int g=0;g < grpList.size();g++){
  String grp = (String)grpList.get(g);
  if(!grp.equals("1")){%>
  <td><span class="txtLink" onclick="DisplayGRP('<%=grp%>')">GRP <%=grp%></span></td>
  <%}}%></tr></table>
 </td></tr>
  <tr><td>
  
  
  <div class="memo">
  <table   cellpadding="0" cellspacing="2">
 <tr><td bgcolor="White">
  <%for(int i=0;i<lookupMdlList.size();i++){
  String mdl = (String)lookupMdlList.get(i);
  String tbID = "dataTable_"+mdl;
  %>
  <div class="float" style="padding:5px;">
  <div style="padding:5px;"><span class="txtLink" ><u><%=lookupLstMap.get(mdl)%></u></span></div>
  <div>
  <table border="0" class="Orange" id="<%=tbID%>" cellspacing="0" cellpadding="0" >
   <tr><th class="Orangeth" align="left">PROPERTY</th>
  <%for(int g=0;g < grpList.size();g++){
  String grp = (String)grpList.get(g);
  String isDisplay ="";
  if(!grp.equals("1"))
  isDisplay = "style=\"display:none\"";
  String thID = "TD_"+grp+"_"+mdl;
  %>
  <th class="Orangeth" id="<%=thID%>" align="left" <%=isDisplay%> >&nbsp;Grp <%=grp%>&nbsp; </th> 
  
  <%}%> </tr>
 <% ArrayList viewPrp = (ArrayList)session.getAttribute(mdl);
  %>
   <% for(int j=0;j<viewPrp.size() ;j++){
           lprp = (String)viewPrp.get(j);
           
           %>
  <tr> <td id=<%=lprp%>><%=lprp%></td>
 <%for(int g=0;g < grpList.size();g++){
      String grp = (String)grpList.get(g);
      String tdID = "TD_"+grp+"_"+lprp;
       String isDisplay ="";
  if(!grp.equals("1"))
  isDisplay = "style=\"display:none\"";
      String lprpVal = util.nvl((String)stockPrpUpd.get(mstkIdn+"_"+lprp+"_"+grp)); %>
      <td <%=isDisplay%> id="<%=tdID%>" > <%=lprpVal%></td>     
 <%}%>
 </tr>
 <%}%>
  </table></div></div>
 <%}%>
  </td></tr></table>
  
  </div>
  </td></tr>
  </table>
  
  <%}%>
  
  </body>
</html>