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
   <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/bse.js?v=<%=info.getJsVersion()%> " > </script>
   <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/bse.css?v=<%=info.getJsVersion()%>"/>
  </head>
  <body>
  <%
  int accessidn=util.updAccessLog(request,response,"Price Grid", "Price Grid");
    session.setAttribute("RNG",new ArrayList());
  HashMap dbinfo = info.getDmbsInfoLst();
    info.setMdl("GenPriceGrid");
    //util.setDBInfo();
    db.chkConn();
    String dfltVal = "0" ;
    String dfltDsp = "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Select&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
    String dbStr="";
    ResultSet rs = null;
    HashMap mprp = null, prp = null;
    ArrayList bsePrp = null, disPrp = null, refPrp = null;
       rs = db.execSql("pri_prop", "select user from dual", new ArrayList());
     if (rs.next()){
                dbStr = rs.getString(1);
     }
     rs.close();
  mprp = info.getSrchMprp();
//    if(mprp==null){
//       util.initPrpSrch();
//        mprp = info.getSrchMprp();
//       }
       prp = info.getSrchPrp();
     
    bsePrp = info.getPrcBsePrp();
    disPrp = info.getPrcDisPrp();
    refPrp = info.getPrcRefPrp();
    String grpNme = util.nvl(request.getParameter("grpNme"));
    HashMap dbinfoMap = info.getDmbsInfoLst();
    ArrayList grpPrp = util.getGrpPrp(grpNme);
 %>
   <form action="<%=info.getReqUrl()%>/searchpricegrid" method="POST">
   <input type="hidden" name="grpNme" id="grpNme" value="<%=grpNme%>"/>
  
   <p><label class="tblHdr">Menu Group</label>&nbsp;<label class="pgTtl"><%=grpNme%></label></p>
  <table  cellpadding="5" cellspacing="5" border="0" width="70%">
  <!-- <tr><td colspan="2"><a href="priceGrpDefn.jsp"> BACK TO HOME</a></td></tr> -->
  <!--
  <tr><td colspan="2">
  <table cellpadding="0" cellspacing="1" class="info">
  <tr><td colspan="2"><input type="checkbox" name="jb_prc" id="jb_prc" value="Y"/><strong>JB PriceList</strong></td></tr>
  <tr>
    <td colspan="2"><input type="radio" name="grid_vlu" id="grid_vlu" value="PCT" checked="checked"/>in %&nbsp;
    <input type="radio" name="grid_vlu" id="grid_vlu" value="VLU"/>Value Wise</td>
  </tr>
  </table>
  </td></tr>
  -->
    <tr><td align="left" valign="top">
        <table cellpadding="0" class="info" align="left" cellspacing="5" border="0">
       
        <tr><td>Property</td><td>From</td><td></td><td></td><td></td></tr>
 <%
    for(int i=0; i < grpPrp.size(); i++) {
        util.SOP("@PriceGrid Starting DIS ");
        String reqDisPrp = (String)grpPrp.get(i);
            String lprp = reqDisPrp;
            String prpDsc = (String)mprp.get(lprp+"D");
            String prpTyp = (String)mprp.get(lprp+"T");
            ArrayList prpPrt = (ArrayList)prp.get(lprp+"P");
            ArrayList prpSrt = (ArrayList)prp.get(lprp+"S");
            ArrayList prpVal = (ArrayList)prp.get(lprp+"V");
            String fld1 = lprp + "_1" ;
            String fld2 = lprp + "_2" ;
             String mutiTextId = "MTXT_"+lprp;
             
        %>
            <tr><td bgcolor="White"><%=prpDsc%></td>
        <%
            if(prpTyp.equals("C")) {%>
            <td colspan="2">
              <div class="multiplePrpdiv" id="<%=lprp%>" align="center" style="display:none; overflow:auto; margin-left:10px; margin-top:20px; height:200px; width=240px; padding:0px;">
             <table cellpadding="3" width="220px" class="multipleBg" cellspacing="3">
                    <%for(int m=0;m<prpSrt.size();m++){
                String isSelected = "";
                String pPrtl = (String)prpPrt.get(m);
                String pSrtl = (String)prpSrt.get(m);
                String vall = (String)prpVal.get(m);
                String chFldNme = lprp+"_"+vall;
                String onclick= "checkPrp(this, 'MTXT_"+lprp+"','"+lprp+"')";
                String checId = lprp+"_"+pSrtl;
               
                
//                String fld = util.nvl((String)favMap.get(lprp+"_"+pSrtl));
//                 if(fld.equals(pSrtl+"_to_"+pSrtl)){
//                   isSelected = "checked=\"checked\"";
//                   loadStrL = loadStrL+" , "+pPrtl;
//                   }
             %>
             <tr><td align="center">
             <input type="checkbox" name="<%=chFldNme%>" id="<%=checId%>" onclick="<%=onclick%>" value="<%=vall%>" >
            </td><td align="center"><%=pPrtl%></td></tr>
             <%}%>
           </table>
            
            
           </div>
             <table cellpadding="0" cellspacing="0"><tr><td>  
             <input type="text" value="" id="<%=mutiTextId%>" name="<%=mutiTextId%>" />
            
           
            </td>
              <td><img src="../images/plus.jpg" id="IMGD_<%=lprp%>" class="img" onclick="DisplayDiv('<%=lprp%>')" style="display:block" border="0"/></td><td><img src="../images/minus.jpg" id="IMGU_<%=lprp%>" class="img" onclick="Hide('<%=lprp%>')" style="display:none" border="0"/></td>
             
              </tr></table>
            </td>
            <%} else {%>
              <td><input type="text" name="<%=fld1%>" id="<%=fld1%>"  size="15"/></td>  
              <td><input type="text" name="<%=fld2%>" id="<%=fld2%>" size="15"/></td>  
            <%}%>
         
        
            </tr>
    <%   
        
    }
       
  %>
   
  </table>
  </td>
  </tr>
  </table>
  <p>
    <input type="submit" name="search" value="Search" id="search"/>&nbsp;
     <input type="button" name="print" value="Print" onclick="printSelect()"/>&nbsp;
     <input type="hidden" name="webUrl" id="webUrl" value="<%=dbinfo.get("REP_URL")%>"/>
     <input type="hidden" name="cnt" id="cnt" value="<%=dbinfo.get("CNT")%>"/>
     <input type="hidden" name="repUrl" id="repUrl" value="<%=dbinfo.get("HOME_DIR")%>"/>
     <input type="hidden" name="repPath" id="repPath" value="<%=dbinfo.get("REP_PATH")%>"/>
  <input type="hidden" name="accessidn" id="accessidn" value="<%=accessidn%>"/>
  </p>
  </form>
  <table class="srchList" width="400px" cellpadding="2" cellspacing="2">
  <%
  ArrayList MatIdnList = (ArrayList)session.getAttribute("MatIdnList");
  session.setAttribute("MatIdnList", new ArrayList());
    int pIdn = 0 ; 
    String getCmn = " select nvl(a.flg, '') flg, b.idn idn, b.mprp mprp, decode(b.val_fr, 'NA', to_char(b.num_fr), b.val_fr) val_fr, decode(b.val_to, 'NA', to_char(b.num_to), b.val_to) val_to "+
        " , a.nme from dyn_mst_t a, dyn_cmn_t b, rep_prp c where a.idn = b.idn and a.rem = ? and c.prp = b.mprp and c.mdl in ('PRC_DIS_PRP', 'PRC_BSE_PRP') and val_fr is not null ";
        if(MatIdnList!=null && MatIdnList.size()>0){
            String idns = MatIdnList.toString();
            idns = idns.replace('[','(');
            idns = idns.replace(']',')');
            idns = idns.replaceAll("'", "");
            getCmn = getCmn+ " and b.idn in "+idns ;
        }
       getCmn = getCmn+ " order by a.rem_srt, b.idn, c.rnk, b.mprp ";
     //    " order by b.idn, c.rnk, b.mprp ";
    ArrayList params = new ArrayList();
    params.add(grpNme);
    rs = db.execSql(" Get Cmn Grp ", getCmn, params);
    int count=0;
    while(rs.next()) {
       
        int lIdn = rs.getInt("idn");
        String flg = rs.getString("flg");
        String nme = rs.getString("nme");
    %>
        <tr><td>
        <%if(lIdn == pIdn) {%>
            &nbsp;
          <%} else {pIdn = lIdn;
           count++;
          %>
          <input type="checkbox" id="PR_<%=count%>" value="<%=lIdn%>" />&nbsp;|&nbsp;
          <a href="genPriceGrid.jsp?id=<%=lIdn%>&grpNme=<%=grpNme%>">Load</a>&nbsp;|&nbsp;
          <% if(!flg.equals("SUB")) { %>
            <a href="copyPriceGrid.jsp?id=<%=lIdn%>&grpNme=<%=grpNme%>">Copy</a>&nbsp;|&nbsp;
          <% }%>
          <a href="<%=dbinfoMap.get("REP_PATH")%>/reports/rwservlet?<%=dbinfoMap.get("CNT")%>&report=<%=dbinfoMap.get("HOME_DIR")%>/reports/Pricing_matrix_rpt_nw.jsp&p_access=<%=accessidn%>&p_idn=<%=lIdn%>&p_rem=<%=grpNme%>" target="_blank">Print</a>&nbsp;|&nbsp;
         <%if(dbStr.equalsIgnoreCase("JBUTL")){%>
          <a href="delMatrix.jsp?id=<%=lIdn%>&grpNme=<%=grpNme%>&nme=<%=nme%>">Delete</a>
          <%}}%>
            </td>
          
            <td><%=rs.getString("mprp")%></td>
            <td><%=rs.getString("val_fr")%></td>
            <td><%=rs.getString("val_to")%></td>
        </tr>
        
    <%}
    rs.close();
  %>
  <tr><td colspan="4"><input type="hidden" name="PNTCNT"  id="PNTCNT" value="<%=count%>" /> </td></tr>
  </table>
  </body>
  </html>
 <%}%>