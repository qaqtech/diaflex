<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
 
<%@ page errorPage="../error_page.jsp" %>
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
    <title>PendingMemo</title>
    <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/bse.css?v=<%=info.getJsVersion()%>"/>
    <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/memoScript.js?v=<%=info.getJsVersion()%> " > </script>
     <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/bse.js?v=<%=info.getJsVersion()%> " > </script>
      <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/style.css?v=<%=info.getJsVersion()%>"/>
  </head>
        <%String logId=String.valueOf(info.getLogId());
        String onfocus="cook('"+logId+"')";
        %>
 <body onfocus="<%=onfocus%>" onload="MM_preloadImages('images/faunalogo.png')" topmargin="0" leftmargin="0" rightmargin="0" bottommargin="0">
<table width="100%"  border="0"  cellpadding="0" cellspacing="0" class="mainbg">
  <tr>
    <td height="103" valign="top">
   <jsp:include page="/header.jsp" />
    
    </td>
  </tr>
 <%
util.updAccessLog(request,response,"Pending memo", "Pending memo");
util.setMdl("PendingMemo");

%>
  <tr>
    <td height="5" valign="top" class="greyline"><img src="../images/grey_linebig.png" width="685" height="5" align="right" /></td>
  </tr>
  <tr>
  <td valign="top" class="hedPg">
  <table cellpadding="1" cellspacing="5"><tr><td valign="middle">
  <img src="../images/line.jpg" border="0" /></td><td valign="middle"> <span class="pgHed">Pending Memo</span> </td>
</tr></table>
  </td>
  </tr>
  
  <%
    String pgTtl = "Pending Memo as on "+ util.getDtTm();
     int ctr = 0 ;
  %>
  
    <tr><td class="tdLayout" valign="top">
    <table class="memo" cellspacing="1"   cellpadding="2" >
         <tr>
          <th nowrap="nowrap">Sr</th>
       
          <th nowrap="nowrap">Buyer</th>
           <th nowrap="nowrap">Sale Person</th>
          <th colspan="5" nowrap="nowrap">Issued</th>
       
          <th colspan="5" nowrap="nowrap">Web Confirmation</th>
            
            <th colspan="5" nowrap="nowrap">Approved</th>    
        </tr>    
        <tr class="ttl">
          <td colspan="3">&nbsp;</td>
           
          
          <td>Qty</td>
          <td>Cts</td>
          <td>Value</td>
         <td>Detail</td>
         <td>Merge</td>
           <td>Qty</td>
          <td>Cts</td>
          <td>Value</td>
         <td>Detail</td>
          <td>Merge</td>
          
          <td>Qty</td>
          <td>Cts</td>
          <td>Value</td>
          <td>Detail</td>
         <td>Merge</td>
        </tr>
        <tr style="display:none;"><td><input type="hidden" name="typhidden" id="typhidden" value=""></td></tr>
    <%
      String getData = " select a.nme_idn, initcap(byr) byr, appqty, appcts, appval, issqty, isscts, "+
                       " issval , waqty ,wacts , waval , byr.get_nm(b.emp_idn) salNme from jan_byr_pndg_v a , mnme b "+
                       " where a.nme_idn = b.nme_idn order by byr ";
      ResultSet rs = db.execSql(" get memo data", getData, new ArrayList());
     
      while(rs.next()) {
        ++ctr ;
        String nmeIdn = rs.getString("nme_idn");
        String byr = rs.getString("byr");
        String sale = rs.getString("salNme");
        String appqty = util.nvl(rs.getString("appqty"));
        String appcts = util.nvl(rs.getString("appcts"));
        String appval = util.nvl(rs.getString("appval"));
        String issqty = util.nvl(rs.getString("issqty"));
        String isscts = util.nvl(rs.getString("isscts"));
        String issval = util.nvl(rs.getString("issval"));
        String waqty = util.nvl(rs.getString("waqty"));
        String wacts = util.nvl(rs.getString("wacts"));
        String waval = util.nvl(rs.getString("waval"));
        String appLnk = ""
          , issLnk = "" ;
     %>     
      <tr>
        <td><%=ctr%></td>
      
        <td> <%=byr%></td>
          <td> <%=sale%></td>
        <td class="nm"><%=issqty%></td>
        <td class="nm"><%=isscts%></td>
        <td class="nm"><%=issval%></td>
        <td>
        <%if(!issqty.equals("")){%>
        <span  class="img"><img src="../images/plus.png" id="IMG_IS_<%=ctr%>" title="click here for Detail" onclick="callMemoPkt('<%=nmeIdn%>','<%=ctr%>','IS',this)" border="0"/></span>
        <%}else{%> 
        <span  class="img" style="display:none"><img src="../images/plus.png" id="IMG_IS_<%=ctr%>" title="click here for Detail" onclick="callMemoPkt('<%=nmeIdn%>','<%=ctr%>','IS',this)" border="0"/></span>
        <%}%>
        </td>
        
        <td>
         <%if(!issqty.equals("")){%>
        <a href="memoReturn.do?method=merge&nmeIdn=<%=nmeIdn%>&typ=IS" title="click here for Merge Memo"><img src="../images/merge_icon.jpg" border="0"/></a>
        <%}%>
        </td>
        <td class="nm"><%=waqty%></td>
        <td class="nm"><%=wacts%></td>
        <td class="nm"><%=waval%></td>
        <td>
         <%if(!waqty.equals("")){%>
        <span class="img"><img src="../images/plus.png" id="IMG_WA_<%=ctr%>"  onclick="callMemoPkt('<%=nmeIdn%>','<%=ctr%>','WA',this)"   border="0"/></span>
        <%}else{%> 
        <span class="img" style="display:none"><img src="../images/plus.png" id="IMG_WA_<%=ctr%>"  onclick="callMemoPkt('<%=nmeIdn%>','<%=ctr%>','WA',this)"   border="0"/></span>
        <%}%>
        </td>
       <td>
        <%if(!waqty.equals("")){%>
       <a href="memoReturn.do?method=merge&nmeIdn=<%=nmeIdn%>&typ=WA" title="click here for Merge Memo"><img src="../images/merge_icon.jpg" border="0"/></a>
       <%}%>
       </td>
       <td class="nm"><%=appqty%></td>
        <td class="nm"><%=appcts%></td>
        <td class="nm"><%=appval%></td>
        <td>
         <%if(!appqty.equals("")){%>
        <span  class="img"><img src="../images/plus.png" id="IMG_AP_<%=ctr%>" title="click here for Detail" onclick="callMemoPkt('<%=nmeIdn%>','<%=ctr%>','AP',this)" border="0"/></span>
        <%}else{%>
        <span  class="img" style="display:none"><img src="../images/plus.png" id="IMG_AP_<%=ctr%>" title="click here for Detail" onclick="callMemoPkt('<%=nmeIdn%>','<%=ctr%>','AP',this)" border="0"/></span>
        <%}%>
        </td>
           <td>
            <%if(!appqty.equals("")){%>
           <a href="memoReturn.do?method=merge&nmeIdn=<%=nmeIdn%>&typ=AP" title="click here for Merge Memo"><img src="../images/merge_icon.jpg" border="0"/></a>
           <%}%>
           </td>
     </tr>
     <tr>
      <td colspan="18"> 
      <div id="BYR_<%=ctr%>" align="center" style="display:none;" >
      </div>
      </td>
      </tr>
      <%  }
      rs.close();
    %>
    
    </table>
   
     <input type="hidden" id="count" value="<%=ctr%>" />
   </td></tr>
   <tr>
    <td><jsp:include page="/footer.jsp" /> </td>
    </tr>
   </table>
 



  </body>
</html>