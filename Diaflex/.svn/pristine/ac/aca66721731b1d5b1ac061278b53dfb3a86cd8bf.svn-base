<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page errorPage="../error_page.jsp" %>
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
 <body onfocus="<%=onfocus%>">
  <%
   HashMap stockPrpUpd = (HashMap)request.getAttribute("labDtlList");
   String mdl = (String)request.getAttribute("mdl");
    ArrayList viewPrp = (ArrayList)session.getAttribute(mdl);
   String mstkIdn = request.getParameter("mstkIdn");
   ArrayList grpList = (ArrayList)request.getAttribute("grpList");
 
   HashMap mprp = info.getMprp();
    HashMap prp = info.getPrp();
   if(grpList!=null && grpList.size() >0){
   String lprp="";
  %>
   
  <table cellpadding="0" cellspacing="0">
 
  <tr><td>
  <div class="memo">
  <table  width="790" cellpadding="0" cellspacing="2">
  
  <tr><td bgcolor="White">
  <table border="0" class="Orange" cellspacing="0" cellpadding="0" >
          
           <tr><th class="Orangeth" align="left">PROPERTY</th>
           <%for(int g=0;g < grpList.size();g++){
           String grp = (String)grpList.get(g);
           %>
           <th class="Orangeth" align="left">Grp <%=grp%> </th>
           <%}%>
           </tr>
           
  <%
  
  for(int i=0;i<viewPrp.size() ;i++){
 
   lprp = (String)viewPrp.get(i);

 %>
  

 <tr> <td><%=lprp%></td>
 <%for(int g=0;g < grpList.size();g++){
           String grp = (String)grpList.get(g);
        String lprpVal = util.nvl((String)stockPrpUpd.get(mstkIdn+"_"+lprp+"_"+grp));
           %>
      <td>
      
        <%=lprpVal%>
      
      </td>     
<%}%>
 </tr>
 <%}%>
  </table></td></tr></table>
  
  </div>
  </td></tr>
  </table>
  
  <%}%>
  
  </body>
</html>