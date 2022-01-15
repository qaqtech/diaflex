<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page errorPage="../error_page.jsp" %>
<%@ page contentType="text/html;charset=windows-1252"%>

<%@ page import="java.util.ArrayList,java.util.HashMap,java.sql.ResultSet,ft.com.*, java.util.Date"%>
<jsp:useBean id="db" class="ft.com.DBMgr" scope="session" />
<jsp:useBean id="log" class="ft.com.LogMgr" scope="session" />
<jsp:useBean id="util" class="ft.com.DBUtil" scope="session" />
<jsp:useBean id="info" class="ft.com.InfoMgr" scope="session" />
<jsp:useBean id="mail" class="ft.com.GenMail" scope="page" />
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>deletePrice</title>
  </head>
  <body>
  <%
  String idn = request.getParameter("id");
    util.updAccessLog(request,response,"Delete Price", "Delete Price");
   String dbStr ="";
  ResultSet rs = db.execSql("pri_prop", "select user from dual", new ArrayList());
  if(rs.next());
  dbStr = rs.getString(1);
  rs.close();
  if(!dbStr.equals(""))
     dbStr = dbStr+".";
     HashMap matDtl = util.getMatrixDtl(Integer.parseInt(idn),dbStr);
     ArrayList ary = new ArrayList();
  ary.add(idn);
  db.execCall("delete ", "prc_data_pkg.del_matrix_idn(?)", ary);
   String grpNme = request.getParameter("grpNme");
   String nme = request.getParameter("nme");
   
   HashMap params = new HashMap();
   params.put("GRP", grpNme);
   params.put("NME", nme);
   params.put("DELMATDTL", matDtl);
   String sub = "Deleted Sheet From Live";
   if(dbStr.equalsIgnoreCase("JBUTL."))
   sub = "Deleted Sheet From Proposed";
   params.put("sub", sub);
   MailAction mailAction = new MailAction();
   mailAction.DeleteMatMail(params, request, response); 
   response.sendRedirect("loadPriceGrid.jsp?grpNme="+grpNme);
  
  %>
  </body>
</html>