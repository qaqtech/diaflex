<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=windows-1252"%>
<jsp:useBean id="util" class="ft.com.JspUtil" scope="page" />
<jsp:useBean id="info" class="ft.com.InfoMgr" scope="session" />
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
      <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/bse.js?v=<%=info.getJsVersion()%> " > </script>

    </head>
    <body>
    <%
    if(request.getAttribute("msg")==null){
     String rptUrl =(String)request.getAttribute("rptUrl");
     String mailUrl =util.nvl((String)request.getAttribute("mailUrl"));
    %>
    

    
   <% if(rptUrl!=null){
  %> 
    <input type="hidden" name="rptUrl" id="rptUrl" value="<%=rptUrl%>" />
    <input type="hidden" name="mailUrl" id="mailUrl" value="<%=mailUrl%>" />
 <script type="text/javascript">
 <!--
 var url = document.getElementById('rptUrl').value;
  var urlMail = document.getElementById('mailUrl').value;
 windowOpen(url,'_self');
 if(urlMail!='N')
 windowOpen(urlMail,'_blank');

 -->
 </script>
 <%}}else{%>
 <%=request.getAttribute("msg")%>
 <%}%>
    </body>
</html>