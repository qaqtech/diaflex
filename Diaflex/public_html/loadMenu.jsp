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
    <title>loadMenu</title>
    <link type="text/css" rel="stylesheet" href="css/bse.css"/>
  </head>
  <body>
  <ul class="menuhdr">
  <%
  String menuIdn = util.nvl(request.getParameter("menuIdn"),"0");
  ResultSet rs = null;
  ArrayList params = new ArrayList() ;
  String menuQ = " select df_menu_idn, dsp_lvl, dsp, img, lnk from df_menu where grp_df_menu_idn = ? or df_menu_idn = ? and stt = 'A' order by dsp_lvl, srt ";
  params.add(menuIdn);
  params.add(menuIdn);
  rs = db.execSql(" Menu Details ", menuQ, params);
  while(rs.next()) {
    String lMenuIdn = rs.getString("df_menu_idn");
    String dsp = rs.getString("dsp");
    String img = rs.getString("img");
    String lnk = util.nvl(rs.getString("lnk"));
    String menuDsp = "<a href=\""+ info.getReqUrl() + lnk + "\" target=\"main\">"+ dsp + "</a>" ;
    if(lnk.length() == 0)
        menuDsp = dsp ;
  %>
  <li><%=menuDsp%></li>
  <ul>
  <%
    String getMenuItems = " select df_menu_itm_idn, img, dsp, srt, lnk from df_menu_itm where df_menu_idn = ? and stt = 'A' order by srt";
    params = new ArrayList();
    params.add(lMenuIdn);
    
    int ctr=0;
    ResultSet rs1 = db.execSql(" Menu Items", getMenuItems, params);
    while(rs1.next()) {
        ++ctr ;
        String lItmIdn = rs1.getString("df_menu_itm_idn");
        String lImg = rs1.getString("img");
        String lDsp = rs1.getString("dsp");
        String lSrt = rs1.getString("srt");    
        String lLnk = rs1.getString("lnk");
        
        String lMenuDsp = "<a href=\""+ info.getReqUrl() + lLnk + "\" target=\"main\">"+ lDsp + "</a>" ;
        if(lLnk.length() == 0)
            lMenuDsp = lDsp ;
        %>
        <li><%=lMenuDsp%></li>
        <%
    }
    rs1.close();%>
    </ul>
    <%
 }
 rs.close();
  %>
  
  </ul>
  </body>
</html>