<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.ResultSet,ft.com.*,java.util.ArrayList"%>
<jsp:useBean id="info" class="ft.com.InfoMgr" scope="session" />
<%

  DBMgr db = new DBMgr();
  db.setCon(info.getCon());
  String authKeySql = "Select auth_key From df_login_log Where log_idn=?";
  ArrayList ary = new ArrayList();
  ary.add(String.valueOf(info.getLogId()));
  ResultSet rs = db.execSql("authKey", authKeySql,ary);
  String authKey ="";
  while(rs.next()){
   authKey = rs.getString("auth_key");
  }
  rs.close();
  request.setAttribute("KEY", "DS"); 
  String url="http://5g.diamondbyhk.com/jasReport/reportAction.do?method=load&DS="+info.getDbTyp()+"&KEY="+authKey;  
  response.sendRedirect(url);
  
   %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
        
    </head>
    <body>
   
    </body>
    
</html>