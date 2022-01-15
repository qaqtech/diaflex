<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import="java.util.ArrayList,java.util.HashMap,java.sql.ResultSet,ft.com.*, java.util.Date, java.util.ArrayList, ft.com.dao.*"%>
<jsp:useBean id="info" class="ft.com.InfoMgr" scope="session" />
<jsp:useBean id="util" class="ft.com.JspUtil" scope="page" />
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
 <body onfocus="<%=onfocus%>" >
  <%
   ArrayList viewPrp=((ArrayList)session.getAttribute("BOXUPDDFLTList") == null)?new ArrayList():(ArrayList)session.getAttribute("BOXUPDDFLTList");
   ArrayList prpUpdList=((ArrayList)session.getAttribute("prpUpdLst") == null)?new ArrayList():(ArrayList)session.getAttribute("prpUpdLst");
   HashMap stockPrpUpd = (HashMap)request.getAttribute("stockList");
   String mstkIdn = (String)request.getAttribute("mstkIdn");
   ArrayList grpList = (ArrayList)request.getAttribute("grpList");
   HashMap mprp = info.getMprp();
    HashMap prp = info.getPrp();
   if(grpList!=null && grpList.size() >0){
   String lprp="";
  %>
    <html:form  action="/box/boxmaster.do?method=saveprp" method="POST">
  <html:hidden property="value(lab)" name="boxMasterForm" />
   <html:hidden property="value(mstkIdn)" name="boxMasterForm" value="<%=mstkIdn%>" />
  <table cellpadding="0" cellspacing="0">
  <tr><td>
  <html:submit property="submit" value="Update Box Properties " styleClass="submit"/>
  </td>
 <logic:present name="boxMasterForm" property="msg">
  &nbsp;<span class="redLabel"> <bean:write name="boxMasterForm" property="msg" /></span>
 </logic:present>
  </tr>
  <tr><td>
  <div class="memo">
  <table  width="790" cellpadding="0" cellspacing="2">
  
  <tr><td bgcolor="White" valign="top">
  <table border="0" class="Orange" cellspacing="0" cellpadding="0" >
          
           <tr><th class="Orangeth">PROPERTY</th>
           <%for(int g=0;g < grpList.size();g++){
           String grp = (String)grpList.get(g);
           %>
           <th class="Orangeth">Grp <%=grp%> </th>
           <%}%>
           </tr>
           
  <%
  int count=0;
  int srchSize = (viewPrp.size()/3)+1;
  for(int i=0;i<viewPrp.size() ;i++){
 
   lprp = (String)viewPrp.get(i);

 
  
    if(count==srchSize){
             count=0;
             %></table></td><td valign="top">
            <table border="0" class="Orange" cellspacing="0" cellpadding="0" >
          
         <tr><th class="Orangeth">PROPERTY</th>
         
          <%for(int g=0;g < grpList.size();g++){
           String grp = (String)grpList.get(g);
           %>
           <th class="Orangeth">Lab <%=grp%> </th>
           <%}%>
         </tr>
          
           <% }
           count++;
           %>

 <tr> <td><%=lprp%></td>
 <%for(int g=0;g < grpList.size();g++){
           String grp = (String)grpList.get(g);
        String lprpVal = util.nvl((String)stockPrpUpd.get(mstkIdn+"_"+lprp+"_"+grp));
           %>
      <td>
       <%if(prpUpdList.contains(lprp) && grp.equals("1")){
       String typ = (String)mprp.get(lprp+"T");
       String  fldName = "value("+lprp+")";
       if(typ.equals("C")){
         ArrayList prpVal = (ArrayList)prp.get(lprp+"V");
  
  %>
  <html:select property="<%=fldName%>" name="boxMasterForm" >
  <html:option value="0">--select--</html:option>
  <%
  for(int j=0;j<prpVal.size();j++){
  String prpPrt = (String)prpVal.get(j);
  %>
  <html:option value="<%=prpPrt%>"><%=prpPrt%></html:option>
  <%}%>
  </html:select>
  <%}else{%>
  <html:text property="<%=fldName%>" size="3" name="boxMasterForm"/>
  <%}%>
       <%}else{%>
        <%=lprpVal%>
       <%}%>
      </td>     
<%}%>
 </tr>
 <%}%>
  </table></td></tr></table>
  
  </div>
  </td></tr>
  </table>
  </html:form>
  <%}%>
  
  </body>
</html>