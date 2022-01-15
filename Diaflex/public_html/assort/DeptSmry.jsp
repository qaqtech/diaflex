<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page errorPage="../error_page.jsp" %>
<%@ page contentType="text/html;charset=windows-1252"%>

<jsp:useBean id="info" class="ft.com.InfoMgr" scope="session" />
<jsp:useBean id="util" class="ft.com.JspUtil" scope="page" />

<%@ page import="java.util.ArrayList,java.util.HashMap,java.util.ArrayList"%>
<%@taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>

<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>DeptSmry</title>
    
    <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/bse.css?v=<%=info.getJsVersion()%>"/>
    <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/general.css?v=<%=info.getJsVersion()%>"/>
    <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/style.css?v=<%=info.getJsVersion()%>"/>
    <script src="../scripts/bse.js" type="text/javascript"></script>
    <script src="../scripts/ajax.js" type="text/javascript"></script>
    
    <script language="Javascript">
    function displayiFrame(link, i)
      {
      //alert(link);
          document.getElementById('tr_'+i).style.display='';
          window.open(link, 'ifrm_'+i);
      }
    </script>
  </head>
 <%String logId=String.valueOf(info.getLogId());
        String onfocus="cook('"+logId+"')";
        %>
 <body onfocus="<%=onfocus%>" onload="MM_preloadImages('images/faunalogo.png')" topmargin="0" leftmargin="0" rightmargin="0" bottommargin="0">
  
  <table width="100%"  border="0" align="center" cellpadding="0" cellspacing="0" class="mainbg">
  <tr>
    <td height="103" valign="top">
      <jsp:include page="/header.jsp" />    
    </td>
  </tr>
 
  <tr>
    <td height="5" valign="top" class="greyline" ><img src="../images/grey_linebig.png" width="685" height="5" align="right" /></td>
  </tr>
  <tr>
  <td valign="top" class="hedPg">
  <table cellpadding="1" cellspacing="5"><tr><td valign="middle">
  <img src="../images/line.jpg" border="0" /></td><td valign="middle"> <span class="pgHed">Dept. Status Summary </span> </td>
</tr></table>
  </td>
  </tr>
  <tr><td>
  
  <tr><td valign="top" class="tdLayout">
    <table width="60%"  class="dataTable" >
    <tr> <td colspan="13">Dept. Allocation List</td> </tr>
      <tr>
        <th>Sr. No.</th>
        <th width="400px">Process</th>
        <th>Status</th>
        <th>Pcs</th>
        <th>Cts</th>
      </tr>
      
      <%
      ArrayList arrprc = (ArrayList) request.getAttribute("arrprc");
      
      for(int i=0; i<arrprc.size(); i++)
      {
        String prc = arrprc.get(i).toString();
        
        HashMap tempprc  = (HashMap) arrprc.get(i);
        String status = tempprc.get("stt").toString();
        String dsc = tempprc.get("dsc").toString();
        String prc_id = tempprc.get("prc_id").toString();
        
        String qty = "0";
        String cts = "0";
        
        if(!tempprc.get("qty").equals(""))
        {
            qty = tempprc.get("qty").toString();
        }
        if(!tempprc.get("cts").equals(""))
        {
            cts = tempprc.get("cts").toString();
        }
        
      %>
      <tr>
        <td><%=i+1%></td>
        <!--<td><a target="ifrm_<%=i%>" onclick="displayiFrame('assort/stkKeeperIssue.do?method=load&prc_id=<%=prc_id%>', '<%=i%>')" class=""><%=tempprc.get("dsc").toString()%></a></td>--->
        <td><a href="pendingRcv.do?method=load&prc_id=<%=prc_id%>"><%=tempprc.get("dsc").toString()%></a></td>
        <td><%=status%></td>
        <td><%=qty%></td>
        <td><%=cts%></td>
      </tr>
      <tr id="tr_<%=i%>" style="display:none;">
        <td><iframe name="ifrm_<%=i%>" width="100%" class="iframe" frameborder="0"> </iframe> </td>
      </tr>
      <%
      }
      %>
            
    </table>
  </td></tr>
  
  <tr><td>&nbsp;</td></tr>
  <tr><td>&nbsp;</td></tr>
  
  </td></tr>
   <tr>
    <td><jsp:include page="/footer.jsp" /> </td>
    </tr>
  </table>

  </body>
</html>