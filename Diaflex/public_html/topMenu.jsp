<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import="java.util.ArrayList,java.util.HashMap,java.sql.ResultSet,ft.com.*, java.util.Date, java.util.ArrayList, ft.com.dao.*"%>
<jsp:useBean id="db" class="ft.com.DBMgr" scope="session" />
<jsp:useBean id="log" class="ft.com.LogMgr" scope="session" />
<jsp:useBean id="util" class="ft.com.DBUtil" scope="session" />
<jsp:useBean id="info" class="ft.com.InfoMgr" scope="session" />
<%@taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>

<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>Diaflex | topMenu</title>
    <link type="text/css" rel="stylesheet" href="css/bse.css"/>
  </head>
  <body>
    <table class="menu">
        <tr>
    <%
    String getMenu = " select df_menu_idn, dsp_lvl, nvl(grp_df_menu_idn, 0) grp, dsp, nvl(img, '') img, nvl(lnk, '') lnk, dsc from df_menu " +
                    " where to_dte is null and stt = 'A' and dsp_lvl = 1 order by dsp_lvl, srt ";    
   
    ResultSet rs = db.execSql(" Menu Level 1 ", getMenu, new ArrayList());
    while(rs.next()){
        String menuIdn = rs.getString("df_menu_idn");
        String grp = rs.getString("grp");
        String dsp = rs.getString("dsp");
        String img = util.nvl(rs.getString("img"));
        String lnk = util.nvl(rs.getString("lnk"));
        
        String menuLnk = lnk, target = "_parent" ;
        if(lnk.length() == 0) {
            menuLnk = "loadMenu.jsp?menuIdn="+ menuIdn ;
            target = "leftFrame" ;
        }    
        String menuDsp = "<a href=\""+ menuLnk + "\" target=\""+ target + "\">"+dsp+"</a>";
        %>
            <td>|</td>
            <td><%=menuDsp%></td>
        <%
    }
    rs.close();
    
    %>
         </tr>
    </table>
  </body>
</html>