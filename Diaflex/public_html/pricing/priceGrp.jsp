<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page errorPage="../error_page.jsp" %>
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import="java.util.ArrayList,java.util.HashMap,java.sql.ResultSet,ft.com.*, java.util.Date, java.net.URLEncoder"%>
<jsp:useBean id="db" class="ft.com.DBMgr" scope="session" />
<jsp:useBean id="log" class="ft.com.LogMgr" scope="session" />
<jsp:useBean id="util" class="ft.com.DBUtil" scope="session" />
<jsp:useBean id="info" class="ft.com.InfoMgr" scope="session" />
<jsp:useBean id="mail" class="ft.com.GenMail" scope="page" />
<%
    if(info.getUsr().length() == 0) {%>
        <jsp:include page="login.jsp?timeout=true" flush="true"/>
    <%} else {
    db.setApplId(application.getInitParameter("ApplId"));
    db.setDbHost(application.getInitParameter("HostName"));
    db.setDbPort(application.getInitParameter("Port"));
    db.setDbSID(application.getInitParameter("SID"));
    db.setDbUsr(application.getInitParameter("UserName"));
    db.setDbPwd(application.getInitParameter("Password"));
    info.setId(session.getId());
    util.setInfo(info);
    util.setDb(db);
    
    info.setMachineName(request.getRemoteHost());
    info.setUsrIp(request.getRemoteAddr());
    info.setMdl("Pricing Defn");
    int accessidn=util.updAccessLog(request,response,"Price Group", "Price Group");
  int port=request.getServerPort();
             String servPath = "http://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath();
            if(port==443)
             servPath = "https://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath();

             info.setReqUrl(servPath); 
            // System.out.println(servPath);
    log.SOP("ServerName : "+ request.getServerName()+request.getServerPort());
    
    log.SOP(" Path : "+ request.getContextPath()+request.getServletPath());
    // Initializing all the reqd property lists
    util.initPrcPrp();
    ResultSet rs = null;
    HashMap mprp = info.getMprp();
    HashMap prp = info.getPrp();
     
    ArrayList bsePrp = info.getPrcBsePrp();
    ArrayList disPrp = info.getPrcDisPrp();
    ArrayList refPrp = info.getPrcRefPrp();
    ArrayList defnPrp = info.getPrcDefnPrp();
    
     HashMap dbinfoMap = info.getDmbsInfoLst();
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
  <%
    session.setAttribute("RNG",new ArrayList());
    String editGrp = util.nvl(request.getParameter("edit"));
    if(editGrp.equals("Y")) {%>
    <form action="<%=info.getReqUrl()%>/savegrpdefn" method="POST">
    <table cellpadding="5" class="info">
    <tr><th>Sr</th><th>Grp Nme</th><th>Srt</th><th>PrmTyp</th><th>Pct/Val</th><th>GrpId</th><th>GrpSrt</th></tr>
    <%
        int lsrt = 0 ;
        HashMap getGrpDtls = info.getGrpDtls();
        HashMap prmTyp = util.getPrmTyp();
        ArrayList keys = (ArrayList)prmTyp.get("K");
        ArrayList vals = (ArrayList)prmTyp.get("V");
        
        ArrayList pctVal = new ArrayList();
        pctVal.add("PCT");
        pctVal.add("VAL");
        String getGrpNmes = " select grp_nme "+
            " from prc_defn_grp where stt = 'A' order by srt, 1 ";
        rs = db.execSql(" Grp Nmes", getGrpNmes, new ArrayList());
        while(rs.next()) {
            String lGrpNme = rs.getString("grp_nme");
            String lSrt = (String)getGrpDtls.get(lGrpNme + "S");
            String lPrmTyp = (String)getGrpDtls.get(lGrpNme + "T");
            String lPctVal = (String)getGrpDtls.get(lGrpNme + "V");
            String lGrpId = (String)getGrpDtls.get(lGrpNme + "GI");
            String lGrpSrt = (String)getGrpDtls.get(lGrpNme + "GS");
        %>
          <tr>
          <td><%=++lsrt%></td>
          <td><%=lGrpNme%></td>
          <td><input type="text" name="<%=lGrpNme%>S" id="<%=lGrpNme%>S" value="<%=lSrt%>" size="15"/></td>
          <td><select name="<%=lGrpNme%>T" id="<%=lGrpNme%>T">
            <%
            for(int i=0; i < vals.size(); i++) {
                String lVal = (String)vals.get(i);
                String lKeys = (String)keys.get(i);
                String selected = "" ;
                if(lVal.equalsIgnoreCase(lPrmTyp))
                    selected="selected";
                %>        
                <option value="<%=lVal%>" <%=selected%>><%=lKeys%></option>
            <%}
            %>
            </select>
          </td>
          <td><select name="<%=lGrpNme%>V" id="<%=lGrpNme%>V">
            <%for(int i=0; i < pctVal.size(); i++) {
                String lVal = (String)pctVal.get(i);
                String selected = "";
                if(lVal.equalsIgnoreCase(lPctVal))
                    selected = "selected";
            %>
                <option value="<%=lVal%>" <%=selected%>><%=lVal%></option>
            <%}%>
            </select>
          </td>
          <td><input type="text" name="<%=lGrpNme%>GI" id="<%=lGrpNme%>GI" value="<%=lGrpId%>" size="10"/></td>
          <td><input type="text" name="<%=lGrpNme%>GS" id="<%=lGrpNme%>GS" value="<%=lGrpSrt%>" size="10"/></td>
          </tr>
        <%}rs.close();
     %>
     </table>
     <p><input type="submit" name="grpupd" id="grpupd" value="Update"/></p>
    </form>
  <% } else {
  %>
  <p><a href="priceGrp.jsp" class="tblHdr">Refresh</a>
   
        &nbsp;|&nbsp;<a href="priceGrpDefn.jsp" target="mainFrame" class="tblHdr">New</a>
        &nbsp;|&nbsp;<a href="priceGrp.jsp?edit=Y" target="mainFrame" class="tblHdr">Edit Groups</a>
    
    &nbsp;|&nbsp;<a href="<%=info.getReqUrl()%>/home.jsp" class="tblHdr" target="_parent">MainMenu</a>
    &nbsp;|&nbsp;<a href="<%=info.getReqUrl()%>/logout.jsp" class="tblHdr" target="_parent">Logout</a>
  </p>
  <table cellpadding="5" class="info">
<tr><th class="tblHdr">Price Menu</th><th></th><th></th><th></th><th></th></tr>
<%
String getGrpNmes = " select grp_nme from prc_defn_grp where stt = 'A' order by srt, 1 ";
rs = db.execSql(" Grp Nmes", getGrpNmes, new ArrayList());
while(rs.next()) {
    String lGrpNme = rs.getString("grp_nme");
    String linkGrpNme = lGrpNme ;
    if(lGrpNme.indexOf("&") > -1)
        linkGrpNme = linkGrpNme.replaceFirst("&", "%26");
        
    String editLink = "priceGrpDefn.jsp?grpNme="+linkGrpNme;
    String srchLink = "loadPriceGrid.jsp?grpNme="+linkGrpNme;
    String newLink = "genPriceGrid.jsp?grpNme="+linkGrpNme;
    String printLink = dbinfoMap.get("REP_PATH")+"/reports/rwservlet?"+dbinfoMap.get("CNT")+"&report="+dbinfoMap.get("HOME_DIR")+"/reports/Pricing_matrix_rpt_nw.jsp&p_access="+accessidn+"&p_rem="+linkGrpNme;
%>
    <tr><td class="tblHdr"><%=lGrpNme%></td>
      
            <td><a href="<%=editLink%>"  target="mainFrame">Edit</a></td>
      
        <td><a href="<%=srchLink%>" target="mainFrame">Search</a></td>
        <td><a href="<%=newLink%>" target="mainFrame">New</a></td>
         <td><a href="<%=printLink%>" target="_blank">Print</a></td>
    </tr>
<%}rs.close();
%>
</table>
<%}%>
</body>
</html>
<%}%>