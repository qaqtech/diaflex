<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page errorPage="../error_page.jsp" %>
<%@ page contentType="text/html;charset=windows-1252"%>

<%@ page import="java.util.ArrayList,java.util.HashMap,java.sql.ResultSet,ft.com.*, java.util.Date"%>
<jsp:useBean id="db" class="ft.com.DBMgr" scope="session" />
<jsp:useBean id="log" class="ft.com.LogMgr" scope="session" />
<jsp:useBean id="util" class="ft.com.DBUtil" scope="session" />
<jsp:useBean id="info" class="ft.com.InfoMgr" scope="session" />
<jsp:useBean id="mail" class="ft.com.GenMail" scope="page" />
<%
    db.setApplId(application.getInitParameter("ApplId"));
    db.setDbHost(application.getInitParameter("HostName"));
    db.setDbPort(application.getInitParameter("Port"));
    db.setDbSID(application.getInitParameter("SID"));
    db.setDbUsr(application.getInitParameter("UserName"));
    db.setDbPwd(application.getInitParameter("Password"));
    info.setId(session.getId());
    util.setInfo(info);
    util.setDb(db);
    
   int port=request.getServerPort();
             String servPath = "http://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath();
            if(port==443)
             servPath = "https://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath();

             info.setReqUrl(servPath); 
            // System.out.println(servPath);
    log.SOP("ServerName : "+ request.getServerName()+request.getServerPort());
    
    log.SOP(" Path : "+ request.getContextPath()+request.getServletPath());
    // Initializing all the reqd property lists
//    util.initPrp();
     ResultSet rs = null;
     HashMap mprp = info.getMprp();
     HashMap prp = info.getPrp();
     
    ArrayList bsePrp = info.getPrcBsePrp();
    ArrayList disPrp = info.getPrcDisPrp();
    ArrayList refPrp = info.getPrcRefPrp();
 
     String dfltVal = "0" ;
     String dfltDsp = "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Select&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
       
     
%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>Index</title>
      <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/prc_style.css"/>
      <script type="text/javascript"  src="<%=info.getReqUrl()%>/scripts/bse_prc.js"></script>

    <script type="text/javascript">
        var http = false;

        if(navigator.appName == "Microsoft Internet Explorer") {
            http = new ActiveXObject("Microsoft.XMLHTTP");
        } else {
            http = new XMLHttpRequest();
        }

        function getPrpLst(inFld, outFld) {
            var lprp = document.getElementById(inFld);  
            http.abort();
            http.open("GET", "GetPrpLst.jsp?prp=" + lprp.value, true);
            http.onreadystatechange=function() {
                if(http.readyState == 4) {
		//if (req.status == 200) {
                    document.getElementById(outFld).innerHTML = http.responseText;
		//}  
                }
            }
            http.send(null);
        }
    </script>
    
  </head>
  <body>
  <form action="priceGrid.jsp" method="POST">
  <table width="90%" cellpadding="5" cellspacing="5" align="center">
  <tr><td valign="top">
 
  <table width="500" cellpadding=""  class="info" align="left" cellspacing="1" border="0">
  <tr><td class="heder" colspan="3" >Base Properties</td></tr>
  <%
    for(int i=0; i < bsePrp.size(); i++) {
        String lprp = (String)bsePrp.get(i);
        String prpDsc = (String)mprp.get(lprp+"D");
        ArrayList prpPrt = (ArrayList)prp.get(lprp+"P");
        ArrayList prpSrt = (ArrayList)prp.get(lprp+"V");
        
        String fld1 = lprp + "_1" ;
        String fld2 = lprp + "_2" ;
    %>
    <tr><td  ><%=prpDsc%></td>
        <td><select class="dmddropdown" name="<%=fld1%>" id="<%=fld1%>" onchange="setFrTo('<%=fld1%>', '<%=fld2%>');">
             <option value="<%=dfltVal%>"><%=dfltDsp%></option>   
             <%
             for(int j=0; j<prpSrt.size(); j++) {
                String pPrt = (String)prpPrt.get(j);
                String pSrt = (String)prpSrt.get(j);
             %>
                <option value="<%=pSrt%>" ><%=pPrt%></option>  
             <%}
             %>
            </select>
        </td>
        <td><select class="dmddropdown" name="<%=fld2%>" id="<%=fld2%>" onchange="setFrTo('<%=fld1%>', '<%=fld2%>');">
             <option value="<%=dfltVal%>"><%=dfltDsp%></option>   
             <%
             for(int j=0; j<prpSrt.size(); j++) {
                String pPrt = (String)prpPrt.get(j);
                String pSrt = (String)prpSrt.get(j);
             %>
                <option value="<%=pSrt%>" ><%=pPrt%></option>  
             <%}
             %>
            </select>
        </td>
    </tr>
  <% }
  %>
  <tr><td class="heder" colspan="3">Main Discount Properties</td></tr>
  <%
    
    for(int a=1; a <= 5; a++) {%>
        <tr>
            <td >Property <%=a%></td>
            <td><select id="disprp_<%=a%>" name="disprp_<%=a%>">
            <option value="<%=dfltVal%>"><%=dfltDsp%></option>   
            <%
            for(int i=0; i < disPrp.size(); i++) {
                String lprp = (String)disPrp.get(i);
                String prpDsc = (String)mprp.get(lprp+"D");
            %>
            <option value="<%=lprp%>"><%=prpDsc%></option>   
            <%}%>
            </select>
            </td>
        </tr>
  <%  } 
  %>
   <tr><td class="heder" colspan="3">Reference Properties</td></tr>
  <tr><td colspan="3">
  <%
            for(int i=0; i < refPrp.size(); i++) {
                String lprp = (String)refPrp.get(i);
                String prpDsc = (String)mprp.get(lprp+"D");
            %>
            <input type="checkbox" name="ref_<%=lprp%>" id="ref_<%=lprp%>" value="Y"/>&nbsp;<%=prpDsc%>   
<%}%>
  </td></tr>
  <tr><td align="center" colspan="3">
  <input type="submit" value="Submit" /></td></tr>
  </table>
  </td>
  <td valign="top" align="left">
  <table cellpadding="5" class="info">
  <tr><td class="heder">Definition List</td></tr>
  <%
    HashMap matLst = util.getPrcDefnLst();
    ArrayList keys = (ArrayList)matLst.get("KEYS");
    for(int i=0; i < keys.size(); i++) {
        String key = (String)keys.get(i);
        String val = (String)matLst.get(key);%>
        <tr><td><a href="viewPriceGrid.jsp?id=<%=key%>"><%=val%></a> <a href="deletePrice.jsp?id=<%=key%>" ><img src="images/delete.png" align="right" border="0" /> </a> </td></tr>
    <%}
  %>
  </table>
  </td>
  </tr>
  </table>
  </form>
 </body>
</html>