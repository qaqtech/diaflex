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
if(info.getUsr().length() == 0) {%>
        <jsp:include page="<%=info.getReqUrl()%>/login.jsp?timeout=true" flush="true"/>
    <%} else {
%>    
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>priceGrid</title>
      <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/prc_style.css"/>
      <script type="text/javascript"  src="<%=info.getReqUrl()%>/scripts/bse_prc.js"></script>

  </head>
  <body>
  <%
    info.setMdl("CopyPriceGrid");
    //util.setDBInfo();
int accessidn=util.updAccessLog(request,response,"CopyPriceGrid", "CopyPriceGrid");
    String grpNme = util.nvl(request.getParameter("grpNme")).toUpperCase();
    HashMap grpDtls = info.getGrpDtls();
    String prmtyp = util.nvl((String)grpDtls.get(grpNme+"T"));
        
    ArrayList grpPrp = util.getGrpPrp(grpNme);
    String dfltVal = "0" ;
    String dfltDsp = "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Select&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
    String gridChg = util.nvl(request.getParameter("gridChg"));
    String gridPct = "", gridVlu = "", gridRap = "" ;
    if(gridChg.equals("RAP"))
        gridRap ="checked" ;
    if(gridChg.equals("PCT"))
        gridPct ="checked" ;
    if(gridChg.equals("VLU"))
        gridVlu ="checked" ;
        
    String verify = util.nvl(request.getParameter("verify"));
    //verify = "Y";
    double gridChgVlu = Double.parseDouble(util.nvl2(request.getParameter("chgVlu"),"0")) ;
    util.SOP(" Verify : " + verify + " | GridChg : "+ gridChg + " | GridChgVlu : " + gridChgVlu);
    ArrayList selRange = null;
    if(session.getAttribute("RNG") != null) {
        selRange = (ArrayList)session.getAttribute("RNG");
        session.setAttribute("RNG", null);
    }else    
        selRange = new ArrayList();
    ResultSet rs = null;
    HashMap mprp = null, prp = null;
    ArrayList bsePrp = null, disPrp = null, refPrp = null;
    mprp = info.getMprp();
    prp = info.getPrp();
     
    bsePrp = info.getPrcBsePrp();
    disPrp = info.getPrcDisPrp();
    refPrp = info.getPrcRefPrp();
    
    HashMap matDtl = new HashMap();
    String loadMatIdn = util.nvl(request.getParameter("id"));
    if(loadMatIdn.length() > 0) {
        matDtl = util.getMatrixDtl(Integer.parseInt(loadMatIdn),"");
    }
    String loadFile = util.nvl(request.getParameter("load"));
    HashMap loadMatDtl = new HashMap();
    //if(loadFile.length() > 1) {
        loadMatDtl = (HashMap)session.getAttribute("MATDTL");
        if(loadMatDtl == null)
            loadMatDtl = new HashMap();
        session.setAttribute("MATDTL", new HashMap());    
    //}
    //util.SOP("@GenPrice MatDtl: " + matDtl.toString());
   // util.SOP("@GenPrice LoadMat: " + loadMatDtl.toString());
    String bsePrc = "", gridInPct = "checked",gridInVlu = "" ;
    if(grpNme.indexOf("BASE") > -1) {
        bsePrc = "Y";    
        gridInPct = "" ;
        gridInVlu = "checked";
    }   
    String showAllCO = util.nvl(request.getParameter("allCO"));
    String showAllPU = util.nvl(request.getParameter("allPU"));
    
 %>
   
  <p><label class="tblHdr">Menu Group</label>&nbsp;<label class="pgTtl"><%=grpNme%></label></p>

  <form action="<%=info.getReqUrl()%>/copypricegrid" method="POST">
  <input type="hidden" name="grpNme" value="<%=grpNme%>"/>
  <input type="hidden" name="matIdn" value="<%=loadMatIdn%>"/> 
  <input type="hidden" name="allCO" value="<%=showAllCO%>"/>
  <input type="hidden" name="allPU" value="<%=showAllPU%>"/> 
  <table  cellpadding="5" cellspacing="0" border="0"><tr><td>
<table  cellpadding="5" cellspacing="5" border="0">
    <tr><td align="left" valign="top">
        <table cellpadding="0" class="info" align="left" cellspacing="5" border="0">
        <tr>
        <td colspan="5">
          <strong>Refer %&nbsp;</strong>
          <input type="text" name="sub_pct" id="sub_pct" size="20" value=100 maxlength="3"/>
        </td>
        </tr>
        <tr><td>Property</td><td>Ref. From</td><td>Ref To</td><td>From</td><td>To</td></tr>
  
 <%
    for(int i=0; i < grpPrp.size(); i++) {
        util.SOP("@PriceGrid Starting DIS ");
            String reqDisPrp = (String)grpPrp.get(i);
            String lprp = reqDisPrp;
            String prpDsc = (String)mprp.get(lprp+"D");
            String prpTyp = util.nvl((String)mprp.get(lprp+"T"));
            ArrayList prpPrt = (ArrayList)prp.get(lprp+"P");
            ArrayList prpVal = (ArrayList)prp.get(lprp+"V");
            String fld1 = lprp + "_1" ;
            String fld2 = lprp + "_2" ;
            String valFr = util.nvl((String)matDtl.get(lprp+ "_FR"));
            String valTo = util.nvl((String)matDtl.get(lprp+ "_TO"));
            String lDspValFr = valFr ;
            String lDspValTo = valTo ;
        %>
            <tr><td bgcolor="White"><%=prpDsc%></td>
        <%
            if(prpTyp.equals("C")) {
              
              lDspValFr = (String)prpPrt.get(Math.max(prpVal.indexOf(valFr), 0));
              lDspValTo = (String)prpPrt.get(Math.max(prpVal.indexOf(valTo), 0));
            }
            else {
              
            }
          
         %>
          <td><input type="hidden" name="<%=fld1%>" value="<%=valFr%>"/><%=lDspValFr%></td>
          <td><input type="hidden" name="<%=fld1%>" value="<%=valTo%>"/><%=lDspValTo%></td>
         <%
         if(prpTyp.equals("C")) {%>
              <td><select class="dmddropdown" name="sub_<%=fld1%>" id="sub_<%=fld1%>" onchange="setFrTo('<%=fld1%>', '<%=fld2%>');">
             <option value="<%=dfltVal%>"><%=dfltDsp%></option>   
             <%
             for(int j=0; j<prpVal.size(); j++) {
                String pPrt = (String)prpPrt.get(j);
                String pSrt = (String)prpVal.get(j);
             %>
                <option value="<%=pSrt%>" ><%=pPrt%></option>  
             <%}
             %>
            </select>
        </td>
        <td><select class="dmddropdown" name="sub_<%=fld2%>" id="sub_<%=fld2%>" onchange="setFrTo('<%=fld1%>', '<%=fld2%>');">
             <option value="<%=dfltVal%>"><%=dfltDsp%></option>   
             <%
             for(int j=0; j<prpVal.size(); j++) {
                String pPrt = (String)prpPrt.get(j);
                String pSrt = (String)prpVal.get(j);
             %>
                <option value="<%=pSrt%>" ><%=pPrt%></option>  
             <%}
             %>
            </select>
        </td>  
            <%} else {%>
              <td><input type="text" name="sub_<%=fld1%>" id="sub_<%=fld1%>"  size="15"/></td>  
              <td><input type="text" name="sub_<%=fld2%>" id="sub_<%=fld2%>" size="15"/></td>  
            <%}
         %>
            </tr>
    <%   
        
    }
       
  %>
  </table>
  
  </td>
  </tr>
  </table>
  </td>
  <td align="left" valign="top" > 
  </td>
  </tr>
  </table>
    <p><input type="submit" name="save" value="Save Change" id="save"/>
    </p>
  
  </form>

  </body>
  </html>
 <%}%>