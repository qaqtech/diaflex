<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page errorPage="../error_page.jsp" %>
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import="java.util.ArrayList,java.util.HashMap,java.sql.ResultSet,ft.com.*, java.util.Date, java.net.URLEncoder"%>

<jsp:useBean id="util" class="ft.com.JspUtil" scope="page" />
<jsp:useBean id="info" class="ft.com.InfoMgr" scope="session" />

<%
%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
     <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/bse.js?v=<%=info.getJsVersion()%> " > </script>
<link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/bse.css?v=<%=info.getJsVersion()%>"/>
<link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/general.css?v=<%=info.getJsVersion()%>"/>
<script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/ajax.js?v=<%=info.getJsVersion()%> " > </script>
  <script type="text/javascript" src="<%=info.getReqUrl()%>/PopScripts/popup.js?v=<%=info.getJsVersion()%> " > </script>
    <script src="<%=info.getReqUrl()%>/PopScripts/jquery-1.2.6.min.js" type="text/javascript"></script>
   <script language="javaScript" type="text/javascript" src="<%=info.getReqUrl()%>/calendar/calendar.js"></script>
   <script language="javaScript" type="text/javascript" src="<%=info.getReqUrl()%>/scripts/memoScript.js?v=<%=info.getJsVersion()%>"></script>
   <link href="<%=info.getReqUrl()%>/calendar/calendar.css" rel="stylesheet" type="text/css" />

 
      <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/style.css?v=<%=info.getJsVersion()%>"/>
    <title>Pricing Group</title>
      <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/prc_style.css"/>
 <!--     <script type="text/javascript"  src="<%=info.getReqUrl()%>/scripts/bse_prc.js"></script>-->
  </head>
        <%String logId=String.valueOf(info.getLogId());
        String onfocus="cook('"+logId+"')";
        %>
 <body onfocus="<%=onfocus%>" onload="MM_preloadImages('images/faunalogo.png')" topmargin="0" leftmargin="0" rightmargin="0" bottommargin="0">
<input type="hidden" id="reqUrl" name="reqUrl" value="<%=info.getReqUrl()%>"/>
<%
         DBMgr db = new DBMgr();
      db.setCon(info.getCon());
      db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));db.setDbUsr(info.getDbUsr());
      String flg =util.nvl(request.getParameter("typ"));
      
%>
<table width="100%"  border="0"  cellpadding="0" cellspacing="0" class="mainbg">
  <tr>
    <td height="103" valign="top">
   <jsp:include page="/header.jsp" />
    
    </td>
  </tr>
 
  <tr>
    <td height="5" valign="top" class="greyline"><img src="../images/grey_linebig.png" width="685" height="5" align="right" /></td>
  </tr>
  <tr>
  <td valign="top" class="hedPg">
  <table cellpadding="1"  cellspacing="5"><tr><td valign="middle">
  <img src="../images/line.jpg" border="0" /></td><td valign="middle"> <span class="pgHed">Pricing Group</span> </td>
</tr></table>
  </td>
  </tr>
  <tr>
  <td class="tdLayout" valign="top">
  <table><tr><td valign="top">
   <table  class="grid1">
  
<tr><th>Sr No.</th><th>Price Menu</th><th width="100px"></th><th width="100px"></th></tr>
<%
ArrayList ary = new ArrayList();
String getGrpNmes = " select nme from pri_grp where stt=? ";
ary.add("A");
if(!flg.equals("")){
getGrpNmes = getGrpNmes+" and flg2= ?  ";
ary.add(flg);
}

getGrpNmes = getGrpNmes+"order by srt ";
try
{
int i=0;
ResultSet rs = db.execSql(" Grp Nmes", getGrpNmes, ary);
while(rs.next()) {
i++;
    String lGrpNme = rs.getString("nme");
    String linkGrpNme = lGrpNme ;
//    if(lGrpNme.indexOf("&") > -1)
//        linkGrpNme = linkGrpNme.replaceFirst("&", "%26");
        
    String editLink = "pricegpmatrix.do?method=edit&grpNme="+linkGrpNme;
    String srchLink = "loadPriceGrid.jsp?grpNme="+linkGrpNme;
    String newLink = "pricegpmatrix.do?method=load&grpNme="+linkGrpNme+"&new=Y";
   
%>
    <tr> 
    <td><%=i%></td>
    <td><%=lGrpNme%></td>
      
     <!--       <td><a href="<%=editLink%>"  target="mainFrame">Edit</a></td>-->
      

        <td><a href="<%=editLink%>" target="mainFrame" onclick="closewindow('NEWWINDOW');">List</a></td>

        <td><a href="<%=newLink%>" target="NEWWINDOW">New</a></td>
   
    </tr>
<%
 }
 rs.close();
 }catch(Exception e)
 {
 e.printStackTrace();
 }
%>
</table></td>
<td valign="top">
<iframe name="mainFrame" height="900"   width="900" align="left" frameborder="0" >

</iframe>
</td>
</tr></table>

</td>

</tr>

</table>
</body> 
  



</html>
