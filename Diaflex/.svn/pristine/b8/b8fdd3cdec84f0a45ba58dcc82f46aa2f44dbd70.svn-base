<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page errorPage="../error_page.jsp" %>
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import="java.util.ArrayList,java.util.HashMap,java.sql.ResultSet,ft.com.*, java.util.Date, java.util.ArrayList, ft.com.dao.*"%>

<jsp:useBean id="util" class="ft.com.JspUtil" scope="page" />
<jsp:useBean id="info" class="ft.com.InfoMgr" scope="session" />
<%@taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>
<%@taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>Rap Net Manager</title>
    <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/bse.css?v=<%=info.getJsVersion()%>"/>
    <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/style.css?v=<%=info.getJsVersion()%>"/>
     <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/bse.js?v=<%=info.getJsVersion()%> " > </script>
       <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/priceChange.js?v=<%=info.getJsVersion()%> " > </script>
    <script type="text/javascript"  src="<%=info.getReqUrl()%>/PopScripts/popup.js?v=<%=info.getJsVersion()%>"></script>
       <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/general.css?v=<%=info.getJsVersion()%>"/>
       <script type="text/javascript"  src="<%=info.getReqUrl()%>/PopScripts/jquery-1.2.6.min.js"></script>
     <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/bse_prc.js " > </script>
    </head>
  <body onload="MM_preloadImages('images/faunalogo.png')" topmargin="0" leftmargin="0" rightmargin="0" bottommargin="0">
<%
ArrayList rapNetLst = (ArrayList)request.getAttribute("rapMapList");

%>
<html:form action="pricing/rapNet.do?method=save" onsubmit="return confirmChanges()" method="post">
</html:form>
 <div id="backgroundPopup"></div>
   
<table width="100%"  border="0" align="center" cellpadding="0" cellspacing="0" class="mainbg">
  <tr>
    <td height="103" valign="top">
   <jsp:include page="/header.jsp" />
    
    </td>
  </tr>
 
  <tr>
    <td height="5" valign="top" class="greyline"><img src="<%=info.getReqUrl()%>/images/grey_linebig.png" width="685" height="5" align="right" /></td>
  </tr>
   <tr>
  <td valign="top" class="hedPg">
  <table cellpadding="1" cellspacing="5"><tr><td valign="middle">
  <img src="../images/line.jpg" border="0" /></td><td valign="middle"> <span class="pgHed">RapNet Manager</span> </td>
   </tr></table>
  </td>
  </tr>
  <%
  String msg = (String)request.getAttribute("msg");
  if(msg!=null){
  %>
 <tr>
  <td valign="top" class="tdLayout" ><span class="redLabel"><%=msg%> </span></td></tr>
  <%}%>
  <tr>
  <td valign="top" class="tdLayout">
  <html:form action="pricing/rapNet.do?method=save" onsubmit="return confirmChanges()" method="post">
 <div id="popupContactSale" >

<table align="center" width="300" cellpadding="0" cellspacing="0" >
<tr><td colspan="2">Apply to Live</td></tr>
<tr><td>Security Key </td><td>
<input type="text" name="security" id="security" />

</td></tr>
<tr><td><input type="button" name="modify" value="Save Change" onclick="return verifyKey()"  id="save"/> </td>
<td><input type="button" value="Back" onclick="disablePopupSale()" /> </td>
</tr>
</table>
</div>
   <input type="hidden" name="action" id="action" />
  <% if(rapNetLst!=null && rapNetLst.size()>0){
   int cnt=0;
  %>
  <table><tr><td>
  <html:button property="value(save)" onclick="loadSale('save')" styleClass="submit" value="Save Changes" />
  &nbsp;&nbsp;
  <html:button property="value(apply)" styleClass="submit" value="Apply %" onclick="ApplyNewRap()" />
  &nbsp;&nbsp;<input type="text" name="perVal" id="perVal" size="7" />
  </td></tr>
  <tr><td>
  <table class="grid1">
  <tr><th>Sr</th><th>Select All <input type="checkbox" name="checkAll" id="checkAll" onclick="checkALL('cb_rap_', 'count')"/></th>
  <th>Shape</th><th>Color</th><th>Purity</th><th>Wt_Max</th><th>Wt_Min</th><th>Old Price</th>
  <th>Price</th><th>Diff Pct</th><th>New Pct</th>
  </tr>
  <%
 
  for(int i=0 ; i<rapNetLst.size();i++){
  cnt++;
  HashMap rapNet = (HashMap)rapNetLst.get(i);
  String diffVAL = util.nvl((String)rapNet.get("diffPct"));
  String idn = util.nvl((String)rapNet.get("idn"));
  String fldNme = "value(NEWDIFF_"+idn+")";
  String fldIdn= "NEWDIFF_"+idn;
  %>
  <tr><td><%=cnt%></td>
  <td><input type="checkbox" id="cb_rap_<%=cnt%>" name="cb_rap_<%=idn%>" value="<%=idn%>" /> </td>
  <td><%=util.nvl((String)rapNet.get("shape"))%></td>
  
  <td><%=util.nvl((String)rapNet.get("colour"))%></td>
  <td><%=util.nvl((String)rapNet.get("purity"))%></td>
  <td><%=util.nvl((String)rapNet.get("wt_max"))%></td>
  <td><%=util.nvl((String)rapNet.get("wt_min"))%></td>
  <td><%=util.nvl((String)rapNet.get("price"))%></td>
   <td><%=util.nvl((String)rapNet.get("oldprice"))%></td>
    <td><%=util.nvl((String)rapNet.get("diffPct"))%><input type="hidden" name="DIFF_<%=idn%>" id="DIFF_<%=idn%>" value="<%=diffVAL%>" /> </td>
    <td><html:text property="<%=fldNme%>" styleId="<%=fldIdn%>" size="10" name="rapNetForm" /> </td>
  </tr><%}%>
  </table></td></tr></table>
  <input type="hidden" name="count" id="count" value="<%=cnt%>" />
  <%}else{%>
  Sorry no result found.
  <%}%>
  </html:form>
  </td></tr>
  <tr>
    <td><jsp:include page="/footer.jsp" /> </td>
    </tr>
  </table>
  
   </body>
</html>