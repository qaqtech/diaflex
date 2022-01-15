<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page errorPage="../error_page.jsp" %>
<%@ page contentType="text/html;charset=windows-1252"%>
<jsp:useBean id="info" class="ft.com.InfoMgr" scope="session" />
<jsp:useBean id="util" class="ft.com.JspUtil" scope="page" />
<%@ page import="java.util.ArrayList,java.util.HashMap,java.sql.ResultSet,ft.com.*, java.util.Date, java.util.ArrayList, ft.com.dao.*"%>
<%@taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>

<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>Link To Exiting User</title>
    <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/bse.css?v=<%=info.getJsVersion()%>"/>
    <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/general.css?v=<%=info.getJsVersion()%>"/>
     <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/style.css?v=<%=info.getJsVersion()%>"/>
      <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/bse.js?v=<%=info.getJsVersion()%> " > </script>
      <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/ajax.js?v=<%=info.getJsVersion()%>"></script> 
      
  </head>
        <%String logId=String.valueOf(info.getLogId());
        String onfocus="cook('"+logId+"')";
        %>
 <body onfocus="<%=onfocus%>" onload="MM_preloadImages('images/faunalogo.png')" onkeypress="return disableEnterKey(event);"  topmargin="0" leftmargin="0" rightmargin="0" bottommargin="0">
  <table width="100%"  border="0" align="center" cellpadding="0" cellspacing="0" class="mainbg">
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
  <table cellpadding="1" cellspacing="5"><tr><td valign="middle">
  <img src="../images/line.jpg" border="0" /></td><td valign="middle"> <span class="pgHed">Link To Exiting User</span> </td>
</tr></table>
  </td>
  </tr><tr>
   <td valign="top" class="tdLayout">
  
    <%
  String regnId=request.getParameter("regnIdn");
  %>
     
    <html:form action="website/AcceptRegistration.do?method=linkExistingUserRegn">
      <html:hidden property="regnIdn" value="<%=regnId%>"
                   name="pendingRegForm"/>
      <table border="0">
        <tr>
          <td>
            <span class="txtBold">Name</span>
          </td>
          <td>
            <input type="text" id="nme" name="nme" class="sugBox"
                   onkeyup="doCompletion('nme', 'nmePop', '../ajaxAction.do?1=1', event)"
                   onKeyDown="setDown('nmePop', 'nmeID', 'nme',event)" />
             
            <html:hidden property="nmeID" name="pendingRegForm"
                         styleId="nmeID"/>
            <div style="position: absolute;">
              <select id="nmePop" name="nmePop"  class="sugBoxDiv" style="display:none;300px;"
                      onKeyDown="setDown('nmePop', 'nmeID', 'nme',event)" 
                      ondblclick="setVal('nmePop', 'nmeID', 'nme', event);hideObj('nmePop');getTrms(this,'REG');getgrpcompanyLink(this)"
                      onclick="setVal('nmePop', 'nmeID', 'nme', event);getTrms(this,'REG');getgrpcompanyLink(this)"
                      size="10"></select>
            </div>
          </td>
         
         
        </tr>
         
       
         
        <tr>
          <td>
            <span class="txtBold">Terms</span>
          </td>
          <td>
            <html:select property="byrRln" name="pendingRegForm"
                         styleId="rlnId">
              <html:option value="0">---select---</html:option>
            
            </html:select>
          </td>
        </tr>
         <tr>
         <td>Group Company </td>
         <td><span id="grpcmpny">None</span></td>
         </tr>
        
         
        <tr>
          <td align="center" colspan="2">
               <html:submit property="submit" value="Submit" styleClass="submit" onclick="return validateLinkToExiting('nmeID','rlnId')" />
          </td>
        </tr>
      </table>
    </html:form></td></tr>
    
     <tr>
    <td><jsp:include page="/footer.jsp" /> </td>
    </tr>
    </table>
  </body>
</html>