<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=windows-1252"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>Success</title>
  </head>
  <body>
  <%
  String succMsg = (String)request.getAttribute("successMsg");
  if(succMsg!=null){
  %>
  <center ><%=succMsg%> </center>
  <%}else{
   String REFID = (String)request.getAttribute("REFIDN");
  String msg="Mail delivered successfully...";
  if(REFID!=null){
  msg = "Mass Mail in process with Sequence No:- "+REFID;
   }%>
  <center>  
  <%=msg%>
  </center>
  <%}%>
  </body>
</html>