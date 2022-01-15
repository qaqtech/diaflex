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
/*
    db.setApplId(application.getInitParameter("ApplId"));
    db.setDbHost(application.getInitParameter("HostName"));
    db.setDbPort(application.getInitParameter("Port"));
    db.setDbSID(application.getInitParameter("SID"));
    db.setDbUsr(application.getInitParameter("UserName"));
    db.setDbPwd(application.getInitParameter("Password"));
*/    
    info.setId(session.getId());
    util.setInfo(info);
    util.setDb(db);
//    util.initPrp();
    
    info.setMachineName(request.getRemoteHost());
    info.setUsrIp(request.getRemoteAddr());
    info.setMdl("Pricing Defn");
    //util.setDBInfo();
    
  int port=request.getServerPort();
             String servPath = "http://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath();
            if(port==443)
             servPath = "https://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath();

             info.setReqUrl(servPath); 
            // System.out.println(servPath);
    //log.SOP("ServerName : "+ request.getServerName()+request.getServerPort());
    
    //log.SOP(" Path : "+ request.getContextPath()+request.getServletPath());
    // Initializing all the reqd property lists
    util.initPrcPrp();
    ResultSet rs = null;
    HashMap mprp = info.getMprp();
    HashMap prp = info.getPrp();
     
    ArrayList bsePrp = info.getPrcBsePrp();
    ArrayList disPrp = info.getPrcDisPrp();
    ArrayList refPrp = info.getPrcRefPrp();
    ArrayList defnPrp = new ArrayList();
    defnPrp.addAll(bsePrp);
    defnPrp.addAll(disPrp);
    
    util.SOP("@priceGrpDefn : "+ defnPrp.size());
    
    String dfltVal = "0" ;
    String dfltDsp = "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Select&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
       
    String grpNme = util.nvl(request.getParameter("grpNme")); 
    ArrayList grpPrp = util.getGrpPrp(grpNme);
    String defnGrpNme = "" ;
    String isMultiY = "", isMultiN="checked" ;
    
    if(grpNme.length() > 0) {
        String isMultiQ = " select nvl(multi_grp, 'N') multi_grp, nvl(defn_grp_nme,'') defn_grp_nme  "+
            " from prc_defn_grp a where upper(grp_nme) = upper(?)";
        ArrayList params = new ArrayList();
        params.add(grpNme);
        rs = db.execSql(" Multi Chk ", isMultiQ, params);
        if(rs.next()) {
            if(rs.getString("multi_grp").equals("Y")) {
                isMultiY = "checked" ;
                isMultiN="" ;
            }    
            defnGrpNme = rs.getString("defn_grp_nme");    
        }
        rs.close();
    }
    
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
  <form action="<%=info.getReqUrl()%>/savegrpdefn" method="POST">
  <input type="hidden" name="oldGrpNme" value="<%=grpNme%>"/>
  <table width="50%" cellpadding="5" cellspacing="5" align="left">
  <tr><td><table width="90%" cellpadding="5" cellspacing="5" align="left">
  <tr><td class="heder">Menu Name</td><td><input type="text" name="grpNme" size="30" maxlength="30" value="<%=grpNme%>"/></td></tr>
  <tr><td class="heder">Anyone in the Group</td>
  <td><input type="radio" name="multi_grp" id="multi_grp" value="N" <%=isMultiN%>/>&nbsp;No&nbsp;
  <input type="radio" name="multi_grp" id="multi_grp" value="Y" <%=isMultiY%>/>&nbsp;Yes&nbsp;
  </td></tr>
  <tr><td class="heder">Group Name</td><td><input type="text" name="defnGrpNme" size="30" maxlength="30" value="<%=defnGrpNme%>"/></td></tr> 
  <tr><td valign="top" colspan="2">
      
  <table width="300" cellpadding=""  class="info" align="left" cellspacing="1" border="0">
  <tr><td class="heder" colspan="3">Discount Group Properties</td></tr>
  <%
    
    for(int a=1; a <= 10; a++) {%>
        <tr>
            <td >Property <%=a%></td>
            <td><select id="disprp_<%=a%>" name="disprp_<%=a%>">
            <option value="<%=dfltVal%>"><%=dfltDsp%></option>   
            <%
            for(int i=0; i < defnPrp.size(); i++) {
                String lprp = (String)defnPrp.get(i);
                String prpDsc = (String)mprp.get(lprp+"D");
                String isSel = "";
                
                if(grpPrp.indexOf(lprp) == (a-1))
                    isSel = "selected" ;
            %>
            <option value="<%=lprp%>" <%=isSel%>><%=prpDsc%></option>   
            <%}%>
            </select>
            </td>
        </tr>
  <%  } 
  %>
  
  <tr><td align="center" colspan="3">
  
  <%
    if(grpNme.length() > 0) {%>
        <input type="submit" name="modify" value="Modify" />&nbsp;
        <input type="submit" name="delete" value="Delete" />
    <%} else {%>
    <input type="submit" name="addnew" value="Submit" />
  <% }
  %>
  </td></tr>
  
</table>
</td>
</tr>
</table>
</td>
<td>&nbsp;
</td>
</tr>
</table>
</form>
</body>
</html>