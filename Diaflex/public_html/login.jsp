<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=windows-1252"%>
<jsp:useBean id="db" class="ft.com.DBMgr" scope="session" />
<jsp:useBean id="log" class="ft.com.LogMgr" scope="session" />
<jsp:useBean id="util" class="ft.com.DBUtil" scope="session" />
<jsp:useBean id="info" class="ft.com.InfoMgr" scope="session" />

<%@taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>Diaflex : Login</title>
    <link type="text/css" rel="stylesheet" href="css/bse.css"/>
  </head>
  <body>
<%if(request.getParameter("MSG")!=null){%>
<div align="left"><label class="pgTtl">Thank you for using Diaflex</label></div>
<%}%>
    <html:javascript formName="loginForm" />
    <html:form action="login.do?method=login" method="POST" onsubmit="return validateLoginForm(this);">
    <div class="container">
  <table class="grid1">
    <tr><th colspan="2">Login to DiaFlex</th></tr>
    <%
        String err=util.nvl(request.getParameter("err"));
        String caperr=util.nvl(request.getParameter("caperr"));
        
        String msg = ""; 
        if(err.length() > 0) {
            msg = "Username / Password does not exist ";
    %>
        <tr><td colspan="2"><font color="Red"><%=msg%></font></td></tr>
    <% }%>
    <tr><td class="lft">Username</td>
        <td class="lft"><html:text property="dfusr" name="loginForm"/>
        <!-- <input type="text" size="30" name="dfusr" id="dfusr" maxlength="30"/> --></td>
    </tr>
    <tr><td class="lft">Password</td>
        <td class="lft"><html:password property="dfpwd" name="loginForm"/>
            <!--<input type="password" size="30" name="dfpwd" id="dfpwd" maxlength="30"/>--></td>
    </tr>
    <tr><td class="lft">Connect To</td>
        <td class="lft">
            <html:radio property="dbTyp" value="OracleJBros"/>&nbsp;Live&nbsp;
            <html:radio property="dbTyp" value="JBrosLeo"/>&nbsp;Test&nbsp; 
            <!--
            <input type="radio" name="connect_to" id="connect_to" value="live" checked="checked"/>&nbsp;Live&nbsp;
            <input type="radio" name="connect_to" id="connect_to" value="test"/>&nbsp;Test&nbsp;
            -->
        </td>
    </tr>
    <%
    msg = "";
    if(caperr.length() > 0)
            msg = " Text did not match ";     
       
    %>
    <tr><td colspan="2"><font color="Red"><%=msg%></font>
      <img src="stickyImg" /><input name="answer" />
    </td></tr>
    <tr><td colspan="2" class="cntr">
        <html:submit value="Login" property="login" styleClass="submit"/>
        <!--<label class="button">Login</label>-->
    </td></tr>
  </table>
  </div>
 
    <html:hidden value="login" property="method"/>
    </html:form>
  </body>
</html>