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
    info.setMdl("GenPriceGrid");
    //util.setDBInfo();
        util.updAccessLog(request,response,"Gen Price Grid", "Gen Price Grid");
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
//    if(loadMatIdn.length() > 0) {
//        matDtl = util.getMatrixDtl(Integer.parseInt(loadMatIdn));
//    }
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

  <form action="<%=info.getReqUrl()%>/loadfile" method="POST" ENCTYPE="multipart/form-data">
  <input type="hidden" name="grpNme" value="<%=grpNme%>"/>
  <input type="hidden" name="matIdn" value="<%=loadMatIdn%>"/> 
  <input type="hidden" name="allCO" value="<%=showAllCO%>"/>
  <input type="hidden" name="allPU" value="<%=showAllPU%>"/> 
  
  <table  cellpadding="5" cellspacing="5" border="1" width="600">
      <tr><td>Load file in CSV</td>
        <td><input type="file" name="matfile"/></td>
        <td><input type="submit" name="loadFile" value="Load File" id="loadFile"/></td>
      </tr>  
      
    </table>
 
  </form>
  <form action="<%=info.getReqUrl()%>/savepricegrid" method="POST">
  <input type="hidden" name="grpNme" value="<%=grpNme%>"/>
  <input type="hidden" name="matIdn" value="<%=loadMatIdn%>"/> 
  <input type="hidden" name="allCO" value="<%=showAllCO%>"/>
  <input type="hidden" name="allPU" value="<%=showAllPU%>"/> 
  <table  cellpadding="5" cellspacing="0" border="0"><tr><td>
<table  cellpadding="5" cellspacing="5" border="0">
  <!--<tr><td colspan="2"><a href="priceGrpDefn.jsp"> BACK TO HOME</a></td></tr>-->
  <!--
  <tr><td colspan="2">
  <table cellpadding="0" cellspacing="1" class="info">
  <tr><td colspan="2" class="tblHdr">
  <input type="checkbox" name="jb_prc" id="jb_prc" value="Y"/><strong>JB PriceList</strong>
  <input type="hidden" name="jb_prc" id="jb_prc" value="<%=bsePrc%>"/>
  </td></tr>
  <tr>
    <td colspan="2"><input type="radio" name="grid_vlu" id="grid_vlu" value="PCT" checked="<%=gridInPct%>"/>in %&nbsp;
    <input type="radio" name="grid_vlu" id="grid_vlu" value="VLU" checked="<%=gridInVlu%>"/>Value Wise</td>
  </tr>
  </table>
  </td></tr>
  -->
    <tr><td align="left" valign="top">
        <table cellpadding="0" class="info" align="left" cellspacing="5" border="0">
        <tr><td colspan="3">
        <% if(grpNme.toUpperCase().indexOf("SUB")>-1) {%>
            <a href="genPriceGrid.jsp?id=<%=loadMatIdn%>&grpNme=<%=grpNme%>&allCO=Y">Load Sub Color</a>&nbsp;
            <a href="genPriceGrid.jsp?id=<%=loadMatIdn%>&grpNme=<%=grpNme%>&allPU=Y">Load Sub Clarity</a>&nbsp;
        <%}%>
            </td>
        <td><input type="checkbox" name="sub" id="sub" value="Y"/><strong>Refer To</strong></td>
        <td><input type="text" name="sub_pct" id="sub_pct" size="20" maxlength="4"/><strong>Refer %</strong></td>
        </tr>
        <tr><td>Property</td><td>From</td><td>To</td><td>Ref. From</td><td>Ref To</td></tr>
  
 <%
    for(int i=0; i < grpPrp.size(); i++) {
        util.SOP("@PriceGrid Starting DIS ");
        String reqDisPrp = (String)grpPrp.get(i);
            String lprp = reqDisPrp;
            String prpDsc = (String)mprp.get(lprp+"D");
            String prpTyp = (String)mprp.get(lprp+"T");
            ArrayList prpPrt = (ArrayList)prp.get(lprp+"P");
            ArrayList prpVal = (ArrayList)prp.get(lprp+"V");
            String fld1 = lprp + "_1" ;
            String fld2 = lprp + "_2" ;
            String valFr = util.nvl((String)matDtl.get(lprp+ "_FR"));
            String valTo = util.nvl((String)matDtl.get(lprp+ "_TO"));
            
            
        %>
            <tr><td bgcolor="White"><%=prpDsc%></td>
        <%
            if(prpTyp.equals("C")) {%>
              <td><select class="dmddropdown" name="<%=fld1%>" id="<%=fld1%>" onchange="setFrTo('<%=fld1%>', '<%=fld2%>');">
             <option value="<%=dfltVal%>"><%=dfltDsp%></option>   
             <%
             for(int j=0; j<prpVal.size(); j++) {
                String pPrt = (String)prpPrt.get(j);
                String pSrt = (String)prpVal.get(j);
                String selFr = "";
                if(pSrt.equals(valFr))
                    selFr = "selected";
             %>
                <option value="<%=pSrt%>" <%=selFr%>><%=pPrt%></option>  
             <%}
             %>
            </select>
        </td>
        <td><select class="dmddropdown" name="<%=fld2%>" id="<%=fld2%>" onchange="setFrTo('<%=fld1%>', '<%=fld2%>');">
             <option value="<%=dfltVal%>"><%=dfltDsp%></option>   
             <%
             for(int j=0; j<prpVal.size(); j++) {
                String pPrt = (String)prpPrt.get(j);
                String pSrt = (String)prpVal.get(j);
                String se1Fr = "", selTo="";
                if(pSrt.equals(valTo))
                    selTo = "selected";   
             %>
                <option value="<%=pSrt%>" <%=selTo%>><%=pPrt%></option>  
             <%}
             %>
            </select>
        </td>  
            <%} else {%>
              <td><input type="text" name="<%=fld1%>" id="<%=fld1%>"  size="15" value="<%=valFr%>"/></td>  
              <td><input type="text" name="<%=fld2%>" id="<%=fld2%>" size="15" value="<%=valTo%>"/></td>  
            <%}
         
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
              <td><input type="text" name=sub_"<%=fld2%>" id="sub_<%=fld2%>" size="15"/></td>  
            <%}
         %>
            </tr>
    <%   
        
    }
       
  %>

  <tr><td colspan="5">
  <%
    String prpCO = "COL", prpPU = "CLR" ;
    
    String dscCO = (String)mprp.get(prpCO+"D");
    String dscPU = (String)mprp.get(prpPU+"D");
    
    ArrayList valCO = (ArrayList)prp.get(prpCO+"P");
    ArrayList bseCO = (ArrayList)prp.get(prpCO+"B");
    ArrayList srtCO = (ArrayList)prp.get(prpCO+"V");    
    
    ArrayList dspCO = bseCO ;
    
//util.SOP(srtCO.toString());
    
    String SPRP1 = "JCT" ;
    String PDIR1 = "-1" ;
    
    /*
    util.SOP(" ShowAllCO : "+ showAllCO);
    util.SOP("Bse : "+ bseCO.toString());
    util.SOP("Srt : "+ srtCO.toString());
    util.SOP("Val : "+ valCO.toString());
    */
    if(showAllCO.length() > 0) {
        dspCO = new ArrayList();
        for(int a=0; a < srtCO.size(); a++) {
            String lval = (String)srtCO.get(a);
            if(bseCO.indexOf(lval) == -1)
                dspCO.add(lval);
        }
    //    dspCO = srtCO ;
        SPRP1 = "COL";
        PDIR1 = "1" ;
    }    
    
    ArrayList valPU = (ArrayList)prp.get(prpPU+"P");
    ArrayList bsePU = (ArrayList)prp.get(prpPU+"B");
    ArrayList srtPU = (ArrayList)prp.get(prpPU+"V");    
    /*
    util.SOP("Bse : "+ bsePU.toString());
    util.SOP("Srt : "+ srtPU.toString());
    util.SOP("Val : "+ valPU.toString());
    */
    ArrayList dspPU = bsePU ;
    if(showAllPU.length() > 0) {
        dspPU = new ArrayList();
        for(int a=0; a < srtPU.size(); a++) {
            String lval = (String)srtPU.get(a);
            if(bsePU.indexOf(lval) == -1)
                dspPU.add(lval);
        }
//        dspPU = srtPU ;
        SPRP1 = "CLR";
        PDIR1 = "1" ;
    }    
        
        
  %>
  <% if (prmtyp.equals("PR")) {%>
    <input type="hidden" name="SPRP1" value="<%=SPRP1%>"/>
    <input type="hidden" name="PDIR1" value="<%=PDIR1%>"/>  
 <%}%>
  
  <table cellpadding="0" cellspacing="1">
  <%
    int idxCO = 0, idxPU = 0 ;
    util.SOP("@PriceGrid Starting CO/PU grid ");
    for(int i=-2; i<dspCO.size(); i++) {
        String lValCO="";
        if(i >=0) {
            
            lValCO = (String)dspCO.get(i);
            idxCO = srtCO.indexOf(lValCO);
        }    
    %>
        <tr>
        <%for(int j=-2; j < dspPU.size(); j++) {
            String lValPU = "";
            String txtClass = "txtBox" ;
            if(j >=0) {
                lValPU = (String)dspPU.get(j);
                idxPU = srtPU.indexOf(lValPU);
                //util.SOP(" Val : " + lValPU + " | SrtIDx : "+ idxPU + " DspVal : "+ (String)valPU.get(idxPU));
            }
            if((i >= 0) && (j >= 0)) {
                String calcVal = "";
                idxCO = srtCO.indexOf(lValCO);
                //util.SOP(" Val : " + lValCO + " | SrtIDx : "+ idxCO + " DspVal : "+ (String)valCO.get(idxCO));
                String fld = lValCO + "_" + lValPU;
                String lval = "" ;
                if(loadMatDtl.size() > 0)
                    lval = util.nvl((String)loadMatDtl.get(i + "_" + j));
                else
                    lval = util.nvl((String)matDtl.get(fld));
                if((lval.equals("0")) || (lval.length() == 0))
                    lval = "";
                else {
                    int selIdx = selRange.indexOf(fld);
                    if((verify.length() > 0) && (selIdx > -1)) {
                        if(gridChgVlu != 0) {
                            if(gridChg.equals("VLU"))
                                calcVal = Double.toString(Double.parseDouble(lval) + gridChgVlu) ;
                            if(gridChg.equals("PCT"))
                                calcVal = Double.toString(Double.parseDouble(lval)*(1 + (gridChgVlu/100))) ;    
                            if(gridChg.equals("RAP")) {
                                calcVal = util.calcRapVal(matDtl, lValCO, lValPU, Double.parseDouble(lval), gridChgVlu); 
                                //lval = Double.toString(Double.parseDouble(lval)*(1 + (gridChgVlu/100))) ;        
                            }    
                            if(!(lval.equals(calcVal))) {
                                lval = calcVal ;
                                txtClass = "txtBoxYellow" ;     
                            }
                        }
                    }
                }
                
                
            %>
                <td><input type="text" name="<%=fld%>" id="<%=fld%>" size="4"  onchange="isNumeric(this)" maxlength="5" value="<%=lval%>" class="<%=txtClass%>"/></td>
            <%} else {
                if((i==-2) && (j==-1)) {%>
                    <td nowrap><input type="checkbox" name="co_all" id="co_all" value="Y" onclick="chkAll('cb_co', 'co_all');"/>&nbsp;All
                        <%=dscCO%>
                    </td>
                <%
                } else if((i==-1) && (j==-2)) {%>
                    <td nowrap><input type="checkbox" name="pu_all" id="pu_all" value="Y" onclick="chkAll('cb_pu', 'pu_all');"/>&nbsp;All
                        <%=dscPU%>
                    </td>
                <%
                } else if((i >= 0) && (j== -1)) {%>
                    <td nowrap="nowrap">
                        <input type="checkbox" name="cb_co_<%=lValCO%>" id="cb_co_<%=lValCO%>" value="<%=lValCO%>"/>
                        <%=(String)valCO.get(idxCO)%>
                    </td>
                <%
                } else if((i == -1) && (j >=0)) {%>
                    <td nowrap="nowrap">
                        <input type="checkbox" name="cb_pu_<%=lValPU%>" id="cb_pu_<%=lValPU%>" value="<%=lValPU%>"/>
                        <%=(String)valPU.get(idxPU)%>
                    </td>
                <%} else {%>
                    <td>&nbsp;</td>
                <%}
            }
        }
        %>
        </tr>
    <%}
  %>
  </table>
  </td>
  </tr>
  
  </table>
  
  </td>
  <td align="left" valign="top" > 
 
  <%
    util.SOP("@PriceGrid Starting REF ");
    for(int i=0;i< refPrp.size();i++){
        String lprp = (String)refPrp.get(i);
        String prpDsc = (String)mprp.get(lprp+"D");
        String prpTyp = (String)mprp.get(lprp+"T");
        ArrayList prpPrt = (ArrayList)prp.get(lprp+"P");
        ArrayList prpVal = (ArrayList)prp.get(lprp+"V");
        
        String reqVal = util.nvl(request.getParameter("ref_"+lprp));
        String tblId = "ref_"+lprp ;
        util.SOP("@PriceGrid Starting REF "+ tblId + " : "+ reqVal);
        //if(reqVal.equals("Y")) {
        %>
            <table style="float:left"  >
                <tr><td colspan="2" align="center" class="hedinfo" onmouseover="showBox('<%=tblId%>')" ><%=prpDsc%></td></tr>
                <tr><td>
                    <table id="<%=tblId%>" style="display:none" class="hedinfo" cellspacing="1">
                    <%for(int j=0; j<prpVal.size(); j++) {
                        String lVal = (String)prpPrt.get(j);
                        String lSrt = (String)prpVal.get(j);
                        String pct = util.nvl((String)matDtl.get(tblId + "_" + lSrt));
                    %>
                    <tr>
                        <td bgcolor="White"><%=lVal%></td>
                        <td  bgcolor="White"><input type="text" name="<%=tblId%>_<%=lSrt%>" id="<%=tblId%>_<%=lSrt%>" value="<%=pct%>" class="textBoxes" size="4"/></td>
                    </tr>
                    <%}
                    %>
                    </table>
                    </td>
                </tr>    
                
            </table>
        <%//}
    }        
  %>
  </td>
  </tr>
  </table>
  </td></tr>
  <tr><td>
    <table class="srchList" width="300px">
        <tr><td class="tblHdr">Bulk Change by</td></tr>
        <tr><td><input type="radio" name="grid_chg" id="grid_chg" value="PCT" <%=gridPct%>/>in %&nbsp;
                <input type="radio" name="grid_chg" id="grid_chg" value="VLU" <%=gridVlu%>/>Value&nbsp;
            <%  if(grpNme.indexOf("BASE") > -1) { %>
                <input type="radio" name="grid_chg" id="grid_chg" value="RAP" <%=gridRap%>/>Rap %&nbsp;
            <%}%>    
                <input type="text" size="10" name="grid_chg_vlu" id="grid_chg_vlu" class="txtBoxYellow" value="<%=gridChgVlu%>"/></td>
        </tr>
    </table>
  </td></tr>
  </table>
  
  <% if(loadMatIdn.length() > 0) {%>
    <p><input type="submit" name="modify" value="Save Change" id="save"/>&nbsp;
    <input type="submit" name="verify" value="Verify Changes" id="verify"/>
    
    </p>
   <%} else {%>
    <p><input type="submit" name="save" value="Save Change" id="save"/>
    
    </p>
  <%}%>  
  </form>

  </body>
  </html>
 <%}%>