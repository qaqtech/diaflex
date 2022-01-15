<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import="java.util.ArrayList,java.util.HashMap,java.sql.ResultSet,ft.com.*, java.util.Date, java.util.ArrayList, ft.com.dao.*"%>
<jsp:useBean id="db" class="ft.com.DBMgr" scope="session" />
<jsp:useBean id="log" class="ft.com.LogMgr" scope="session" />
<jsp:useBean id="util" class="ft.com.DBUtil" scope="session" />
<jsp:useBean id="info" class="ft.com.InfoMgr" scope="session" />

<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>testMatrix</title>
  </head>
  <body>
  <%
  HashMap prp = info.getPrp();
  ArrayList col1 = new ArrayList();
  ArrayList col2 = new ArrayList();
  ArrayList row1 = new ArrayList();
  ArrayList row2 = new ArrayList();
  
  col1.add("D");
  col1.add("E");
  col1.add("F");
  
  col2.add("IF");
  col2.add("VVS1");
  col2.add("VVS2");
  
  row1.add("1.00");
  row1.add("1.50");
  row1.add("2.00");
  
  row2.add("EX");
  row2.add("VG");
  row2.add("GD");
%>
<table>
<%  
  
  for(int a = 0 ; a < col1.size(); a++) {
      for(int b = 0 ; b < col1.size(); b++) {
        
      }
    
  }
%>
</table>
<%
  ArrayList rowList = new ArrayList();
  rowList.add("COL");
  rowList.add("CLR");
  
  ArrayList columList = new ArrayList();
  columList.add("CUT");
  columList.add("POL");
  
  ArrayList  columList1 = (ArrayList)prp.get("CUT");
  ArrayList  columList2 = (ArrayList)prp.get("POL");
  
  
 
  %>
  
  
  
  
  </body>
</html>