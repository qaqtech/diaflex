<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page errorPage="../error_page.jsp" %>
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import="java.util.ArrayList,java.util.HashMap,java.sql.ResultSet,ft.com.*, java.util.Date, java.util.ArrayList, ft.com.dao.*"%>
<jsp:useBean id="db" class="ft.com.DBMgr" scope="session" />
<jsp:useBean id="log" class="ft.com.LogMgr" scope="session" />
<jsp:useBean id="util" class="ft.com.DBUtil" scope="session" />
<jsp:useBean id="info" class="ft.com.InfoMgr" scope="session" />
<%@taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>
<%@taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>ProposedLive Comparison</title>
  <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/bse.css?v=<%=info.getJsVersion()%>"/>
    <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/style.css?v=<%=info.getJsVersion()%>"/>
     <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/bse.js?v=<%=info.getJsVersion()%> " > </script>
    <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/bse_prc.js " > </script>
  </head>
 <%
 ArrayList idnDtlList = (ArrayList)request.getAttribute("idnDtlList");
 HashMap matDtlList = (HashMap)session.getAttribute("matDtlMap");
 String grpNme = util.nvl((String)request.getAttribute("grpNme")).toUpperCase();
 %>
  <body onload="MM_preloadImages('images/faunalogo.png')" topmargin="0" leftmargin="0" rightmargin="0" bottommargin="0">
<table width="100%"  border="0" align="center" cellpadding="0" cellspacing="0" class="mainbg">
<tr>
    <td height="103" valign="top">
   <jsp:include page="/header.jsp" />
    
    </td>
  </tr>
 
  <tr>
    <td height="5" valign="top" class="greyline"><img src="<%=info.getReqUrl()%>/images/grey_linebig.png" width="685" height="5" align="right" /></td>
  </tr>
   <tr>
  <td valign="top" class="hedPg">
  <table cellpadding="1" cellspacing="5"><tr><td valign="middle">
  <img src="../images/line.jpg" border="0" /></td><td valign="middle"> <span class="pgHed">Proposed & Live Comparison</span> </td>
   </tr></table>
  </td>
  </tr>
  <tr>
  <td valign="top" class="tdLayout">
  <span class="txtLink"> <A href="<%=info.getReqUrl()%>/pricing/proposeToLive.do?method=Fetch&rem=<%=grpNme%>">Back To Proposed To Live Screen</a></span>
  </td></tr>
  <tr>
  <td valign="top" class="tdLayout">
  <table><tr>
<td><div class="Blue">&nbsp;</div></td><td>&nbsp;Proposed&nbsp;</td>
<td><div class="red">&nbsp;</div> </td><td>&nbsp;Live&nbsp;</td>
<td><div class="green">&nbsp;</div> </td><td>&nbsp;Same&nbsp;</td>
</tr></table>
  </td></tr>
   <tr><td valign="middle"><span class="pgHed" style="margin-left:30px;">Rem Name : <%=grpNme%></span></td></tr>
  <tr>
 
  <td valign="top" class="tdLayout">
  <%
    String prpCO = "COL", prpPU = "CLR" ;
    HashMap mprp = info.getSrchMprp();
//    if(mprp==null){
//       util.initPrpSrch();
//        mprp = info.getSrchMprp();
//       }
    HashMap prp = info.getSrchPrp();
    String dscCO = (String)mprp.get(prpCO+"D");
    String dscPU = (String)mprp.get(prpPU+"D");
    
    ArrayList valCO = (ArrayList)prp.get(prpCO+"P");
    ArrayList bseCO = (ArrayList)prp.get(prpCO+"B");
    ArrayList srtCO = (ArrayList)prp.get(prpCO+"V");  
    
    ArrayList valPU = (ArrayList)prp.get(prpPU+"P");
    ArrayList bsePU = (ArrayList)prp.get(prpPU+"B");
    ArrayList srtPU = (ArrayList)prp.get(prpPU+"V");  
     ArrayList refPrp = info.getPrcRefPrp();
     ArrayList grpPrp = util.getGrpPrp(grpNme);
    %>
  
    <%
  for(int i=0;i< idnDtlList.size();i++){
  ArrayList idnDtl = (ArrayList)idnDtlList.get(i);
  String proposIdn = (String)idnDtl.get(0);
  String liveIdn = (String)idnDtl.get(1);
  String nme = (String)idnDtl.get(2);
  HashMap proMatDtl = (HashMap)matDtlList.get("PROP_"+proposIdn);
  HashMap liveMatDtl = (HashMap)matDtlList.get("LIVE_"+liveIdn);
  
  %> 
  <table>
   <tr><td colspan="2" align="left">
      <%
    HashMap allPageDtl= (info.getPageDetails() == null)?new HashMap():(HashMap)info.getPageDetails();
    HashMap pageDtl=(HashMap)allPageDtl.get("PROPOSE_COMPARISION");
    ArrayList pageList=new ArrayList();
    HashMap pageDtlMap=new HashMap();
    String fld_nme="",fld_ttl="",val_cond="",dflt_val="",fld_typ="",form_nme="";
    pageList=(ArrayList)pageDtl.get("BUTTON");
      if(pageList!=null && pageList.size() >0){
          for(int j=0;j<pageList.size();j++){
          pageDtlMap=(HashMap)pageList.get(j);
          fld_nme="value("+(String)pageDtlMap.get("fld_nme")+")";
          fld_ttl=(String)pageDtlMap.get("fld_ttl");
          val_cond=(String)pageDtlMap.get("val_cond");
          val_cond=val_cond.replaceAll("GRPNME",grpNme);
          val_cond=val_cond.replaceAll("NME",nme);
          val_cond=val_cond.replaceAll("LIVEIDN",liveIdn);
          fld_typ=(String)pageDtlMap.get("fld_typ");
            %>
            <input type="button" name="<%=fld_nme%>" class="submit" onclick="<%=val_cond%>" value="<%=fld_ttl%>" id="verify"/>
        <%
        }
      }%>
   <!--<input type="button" name="verifySelected" class="submit" onclick="return AppToLive('<%=grpNme%>','<%=nme%>','<%=liveIdn%>')" value="Verify Changes" id="verify"/>-->
     
     Sheet Name: &nbsp;&nbsp;<B><%=idnDtl.get(2)%></b>&nbsp;&nbsp;
    <span style="padding-left:30px;"><a title="Click Here To Dowenload PDF" onclick="createPdfPrppos('<%=proposIdn%>','<%=liveIdn%>','<%=nme%>','<%=grpNme%>')"><img src="../images/PDFIconSmall.jpg" border="0"/> </a></span>
      <label id="<%=nme%>" class="redTxt"></label></td></tr>
   <tr><td colspan="2" align="left">
   <table cellspacing="3" cellpadding="3">
  <tr><td colspan="3" align="left"><B>Basic Propeties:</b></td> </tr>
  <tr><td></td><td>Proposed</td><td> Live</td></tr>
   <%
   for(int j=0;j< grpPrp.size();j++){
   String lprp = (String)grpPrp.get(j);
    String prpDsc = (String)mprp.get(lprp+"D");
   String valProFr = util.nvl((String)proMatDtl.get(lprp+ "_FR"));
   String valProTo = util.nvl((String)proMatDtl.get(lprp+ "_TO"));
   String valLiveFr = util.nvl((String)liveMatDtl.get(lprp+ "_FR"));
   String valLiveTo = util.nvl((String)liveMatDtl.get(lprp+ "_TO"));
   
   %>
   <tr><td><%=prpDsc%></td><td><span  class="BlueTxt"> <B><%=valProFr%>&nbsp; to&nbsp; <%=valProTo%></b></span> </td><td>
   <span class="redTxt"> <%=valLiveFr%>&nbsp; to&nbsp; <%=valLiveTo%> </span> </td> </tr>
   <%}%>
   </table>
   </td></tr>
  <tr><td valign="top"><table>
  <tr><td>
  <table class="grid1"><tr><td></td>
  <%for(int x=0 ; x < bsePU.size(); x++){ %>
 <th><%=bsePU.get(x)%> </th>
  <%}%>
  </tr>

  <%
  for(int x=0 ; x < bseCO.size(); x++){
  String coVal = (String)bseCO.get(x);
  %>
 <tr> <th> <%=coVal%></th>
  <%
  for(int y=0 ; y < bsePU.size() ; y++){
  String puVal = (String)bsePU.get(y);
  String proPosVal = util.nvl((String)proMatDtl.get(coVal+"_"+puVal));
  float proValInt = 0;
  if(!proPosVal.equals(""))
  proValInt = Float.parseFloat(proPosVal);
  
  String liveVal = util.nvl((String)liveMatDtl.get(coVal+"_"+puVal));
  float liveValInt = 0;
  if(!liveVal.equals(""))
  liveValInt = Float.parseFloat(liveVal);
  
   if(liveValInt==0 && proValInt==0){
  %>
  <td> &nbsp;&nbsp;</td>
  <%}else if(liveValInt==proValInt){

  %>
 
  <td><span class="GreenTxt"> <%=util.nvl((String)proMatDtl.get(coVal+"_"+puVal))%></span> </td>  
 <% }else{
%>
  <td><span class="BlueTxt"><b><%=util.nvl((String)proMatDtl.get(coVal+"_"+puVal))%></b> </span><br> 
  <span class="redTxt"><%=util.nvl((String)liveMatDtl.get(coVal+"_"+puVal))%></span> 
 <%
if(liveValInt!=0 && grpNme.equals("JB BASE PRICE")){
 float diffResult=(((float)proValInt-(float)liveValInt)/(float)liveValInt)*100;
 %><br>
<%=Math.round(diffResult*100.0)/100.0%> %
 <% }%>
 </td>
<%}%>
  <%}%>
   </tr>
  <%}%>
 
  </table></td></tr></table></td>
  <td valign="top">
   <table>
 
 
  <tr><td>
  <table>
  <tr><td  colspan="3" align="left"><B>Reference Propeties:</b></td> </tr>
  <tr><td valign="top">
    <%
   for(int j=0;j< refPrp.size();j++){
        String lprp = (String)refPrp.get(j);
        String prpDsc = (String)mprp.get(lprp+"D");
        String prpTyp = (String)mprp.get(lprp+"T");
        ArrayList prpPrt = (ArrayList)prp.get(lprp+"P");
        ArrayList prpVal = (ArrayList)prp.get(lprp+"V");
          String tblId = "ref_"+lprp ;
          %>
         <table class="grid1"><tr><th colspan="2" align="center"><%=prpDsc%></th></tr>
          <%
          if(prpVal!=null && prpVal.size()>0){%>
         
          <%
        for(int k = 0 ; k < prpVal.size() ; k++){
           String lVal = (String)prpPrt.get(k);
           String lSrt = (String)prpVal.get(k);
           String proPct = util.nvl((String)proMatDtl.get(tblId + "_" + lSrt));
           String livePct = util.nvl((String)liveMatDtl.get(tblId + "_" + lSrt));
           float proValInt = 0;
           if(!proPct.equals(""))
            proValInt = Float.parseFloat(proPct);
           float liveValInt = 0;
           if(!livePct.equals(""))
           liveValInt = Float.parseFloat(livePct);
           
  %>
 <tr> <td><%=lVal%> : &nbsp;</td><td>  
  <% if(liveValInt==0 && proValInt==0){%>&nbsp;&nbsp;<%}else if(liveValInt==proValInt){%>
  <span class="GreenTxt"> <%=proPct%></span>
  <%}else{%>
 &nbsp; <span class="BlueTxt"><%=proPct%></span>/ &nbsp;<span class="redTxt"> <%=livePct%></span> 
 <%}%>
 </td></tr>
  <%}%>
  </table></td><td valign="top">
  <%}%>

 
   <%}%>
    </tr>
  </table>
  </td></tr></table>
  </td>
  </tr></table>
 <%}%>
 
  </td></tr>
  </table>
 
  </body>
</html>