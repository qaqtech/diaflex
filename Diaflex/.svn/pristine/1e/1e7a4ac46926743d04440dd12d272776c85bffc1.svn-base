<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>Diaflex : Login</title>
    <script type="text/javascript" src="scripts/bse.js?v=1" ></script>
    <script src="PopScripts/jquery-1.11.3.min.js?v=1" type="text/javascript"></script>
    <script src="jqueryScript/jqueryScript.js?v=1" type="text/javascript"></script>
    <link type="text/css" rel="stylesheet" href="css/bse.css?v=1"/>
<script type="text/javascript">
$(document).ready( function() { $.getJSON( "https://jsonip.com/", function(data){ $('#ip_val').val(( data.ip)); } );});
</script>
  <%
  String load="checkAccountNmCookie();";
   session.invalidate();  // add my surekha bcz session was interchanges
  %>
 </head>
  <body>
<form action="login.do?method=login" method="POST" onsubmit="return validateLoginForm();">
<input type="hidden" name="ip_val" id="ip_val" value="" />
    <div class="container">
      <%
   String MSG = (String)request.getAttribute("MSG");
     if(MSG!=null){%>
    <div style="display:block"><span class="redLabel"><%=MSG%></span></div>
   <%}%>
    
  <table class="grid1">
    
    <tr><th colspan="2" >Login to DiaFlex</th></tr>
    <tr><td class="lft">Account Name</td>
        <td class="lft">
        <input type="text" size="30"  name="dfaccount" id="dfaccount" maxlength="30"/></td>
    </tr>
    <tr><td class="lft">Username</td>
        <td class="lft">
        <input type="text" size="30" name="dfusr" id="dfusr" maxlength="30"/></td>
    </tr>
    <tr><td class="lft">Password</td>
        <td class="lft">
            <input type="password" size="30" name="dfpwd" id="dfpwd" maxlength="30"/></td>
    </tr>
    <!--<tr><td class="lft">Connect To</td>
        <td class="lft">
            <input type="radio" name="dbTyp" id="dbTypLive" value="OracleKapu" checked="checked"/>&nbsp;Live&nbsp;
            <input type="radio" name="dbTyp" id="dbTypTest" value="OracleKapu"/>&nbsp;Test&nbsp;
   <input type="radio" name="dbTyp" id="dbTyputl" value="OracleKaputl"/>&nbsp;Proposed&nbsp;
         </td>
    </tr>-->
    <tr>
            <td class="lft"></td>
            <td class="lft" nowrap="nowrap">
            <select name="connectBy" id="connectBy">
            <option value="DS">Default</option>
            <option value="CP">Alternative</option>
            </select>
            </td>
          </tr>
    
    <tr><td colspan="2" class="cntr">
       <button type="submit" class="submit" value="Login" >Submit</button>
      
    </td></tr>
  </table>
  </div>
    
    </form>
  </body>
</html>