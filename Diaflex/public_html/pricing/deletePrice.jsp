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
  util.updAccessLog(request,response,"Delete Price", "Delete Price");
  String nme="";
  String idn = request.getParameter("id");
  String sqlPrp  = "select a.mprp ,a. val_fr , a.val_to , b.nme , b.flg ,b.idn , b.srt from dyn_cmn_t a, dyn_mst_t b where a.idn = b.idn and a.idn=?";
   ArrayList ary = new ArrayList();
  ary.add(idn);
  ResultSet rs = db.execSql("getPrpr", sqlPrp, ary);
  while(rs.next()){
  nme = rs.getString("nme");
  }
  rs.close();
  ary = new ArrayList();
  ary.add(nme);
 
 ArrayList outv = new ArrayList();
 outv.add("V");
  db.execCall("delet", "prc.MatDel(?,?)", ary, outv);
  
  
  
  response.sendRedirect("index.jsp");
  
  %>
  </body>
</html>