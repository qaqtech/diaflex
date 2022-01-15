<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.ResultSet, java.util.ArrayList,java.util.Collections,java.util.ArrayList,java.util.HashMap,java.text.SimpleDateFormat, java.util.Calendar"%>
<jsp:useBean id="info" class="ft.com.InfoMgr" scope="session" />
<jsp:useBean id="util" class="ft.com.DBUtil" scope="session" />
<%@taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>Diaflex : OTP</title>
    <script type="text/javascript" src="scripts/bse.js?v=2" ></script>
    <link type="text/css" rel="stylesheet" href="css/bse.css?v=2"/>
 </head>
  <body>
    <div class="container">
      <%
   String MSG = util.nvl((String)request.getAttribute("MSG"));
    if(MSG!=null){%>
    <div style="display:block"><span class="redLabel"><%=MSG%></span></div>
   <%}%>
  <html:form action="login.do?method=verifyOtp" method="post">
  <table class="grid1">
    <tr><th colspan="2">Verify One Time Password For Log Id <%=info.getLogId()%></th></tr>
    <tr><td class="lft">OTP</td>
        <td class="lft">
        <input type="text" size="30" name="dfOtp" id="dfOtp" maxlength="200"/></td>
    </tr>
   <tr><td colspan="2" class="cntr">&nbsp;&nbsp;
       <html:submit property="value(verify)" value="Verify" onclick="return validateOTP();" styleClass="submit" />
      &nbsp;&nbsp;
       <html:submit property="value(regenerate)" value="Regenerate OTP" styleClass="submit" />
    </td></tr>
  </table>
   </html:form>
  </div>
<%String sendsmsurl=util.nvl((String)request.getAttribute("sendsmsurl"));
if(!sendsmsurl.equals("")){
%>
<div style="display:none;">
<iframe name="loadhkurl" src="<%=sendsmsurl%>" id="loadhkurl" style="display:none;" align="middle" frameborder="0">
</iframe>
</div>
<%}%>
    </form>
  </body>
</html>