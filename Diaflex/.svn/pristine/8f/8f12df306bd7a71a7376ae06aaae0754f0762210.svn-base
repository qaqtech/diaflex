<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page errorPage="../error_page.jsp" %>
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import="java.util.ArrayList,java.util.ArrayList,java.util.HashMap,java.sql.ResultSet,ft.com.*, java.util.Date, java.net.URLEncoder"%>

<jsp:useBean id="util" class="ft.com.JspUtil" scope="page" />
<jsp:useBean id="info" class="ft.com.InfoMgr" scope="session" />

<%@taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<%@taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>

<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
     <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/bse.js?v=<%=info.getJsVersion()%> " > </script>
      <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/pri.js?v=<%=info.getJsVersion()%> " > </script>
<link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/bse.css?v=<%=info.getJsVersion()%>"/>
<link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/general.css?v=<%=info.getJsVersion()%>"/>
    <link href="<%=info.getReqUrl()%>/calendar/calendar.css" rel="stylesheet" type="text/css" />
      <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/style.css?v=<%=info.getJsVersion()%>"/>
       <script type="text/javascript" src="<%=info.getReqUrl()%>/PopScripts/popup.js?v=<%=info.getJsVersion()%> " > </script>
     <script src="<%=info.getReqUrl()%>/PopScripts/jquery-1.2.6.min.js" type="text/javascript"></script>
    <title>Price Calculator</title>
      <!--     <script type="text/javascript"  src="<%=info.getReqUrl()%>/scripts/bse_prc.js"></script>-->
       <style type="text/css">
     div label input {
   margin-right:100px;
}
#ck-button {
    margin:1px;
    background-color:#FFFFFF;
    border-radius:5px;
    border: 2px solid #ACACAC;
    overflow:auto;
    float:left;
}

#ck-button label {
   float:left;
   width: 66px;
   position:relative;
}

#ck-button label span {
    text-align:center;
    padding:3px 0px;
    display:block;
    border-radius:2px;
    font-size:11px;
}

#ck-button label input {
    position:absolute;
    top:-20px;
}

#ck-button input:hover + span {
    background-color:#ACACAC;
}

#ck-button input:checked + span {
    background-color:#5A5A5A;
    color:#fff;
}

#ck-button input:checked:hover + span {
    background-color:#5A5A5A;
    color:#fff;
}
.selectnew{
     border:1px solid  #ACACAC;
     /*1px rgb(99,99,99) solid ;*/
     font-family: Calibri, Arial, Helvetica, sans-serif;
     font-size: 12px;
     overflow: hidden; 
      height:18px;
     /*height:20px;*/
}
.textNew{
     border:1px solid  #ACACAC; /*1px rgb(99,99,99) solid ;*/
     font-family: Calibri, Arial, Helvetica, sans-serif;
     font-size: 12px;
     height:15px;
}
.txtBoldNew{
     font-family: Calibri, Arial, Helvetica, sans-serif;
     font-size:14px;
    
    color: #5A5A5A;
}
.lft-border{
border-left:1px solid #E6A16D;
}
</style>   
    
  </head>
        <%String logId=String.valueOf(info.getLogId());
        String onfocus="cook('"+logId+"')";
         HashMap allPageDtl= (info.getPageDetails() == null)?new HashMap():(HashMap)info.getPageDetails();
     HashMap pageDtl=(HashMap)allPageDtl.get("PRICE_CALC");
    ArrayList pageList=new ArrayList();
    HashMap pageDtlMap=new HashMap();
    String fld_nme="",fld_ttl="",val_cond="",dflt_val="",fld_typ="",form_nme="",flg1="",lov_qry="";
    String cntn = util.nvl((String)info.getDmbsInfoLst().get("CNT"));
    String prcCalcGrp=util.nvl((String)request.getAttribute("prcCalcGrp"));
      String searchPriCalcView = util.nvl(info.getSearchPriCalcView(),"searchView");
      String displaysheetdata="";
      boolean validate=false;
      pageList=(ArrayList)pageDtl.get("DISPLAYGRPRIGHTS");
      if(pageList!=null && pageList.size() >0){
      for(int j=0;j<pageList.size();j++){
      pageDtlMap=(HashMap)pageList.get(j);
      dflt_val=(String)pageDtlMap.get("dflt_val");    
      if(dflt_val.equals("YES"))
      validate=true;
      }}
      if(validate){
      displaysheetdata="display:none";
      pageList=(ArrayList)pageDtl.get("SHEET_DATA");
      if(pageList!=null && pageList.size() >0){
      for(int j=0;j<pageList.size();j++){
      displaysheetdata="";
      }
      }
      }
       String sheetdatadisplay="Y";
       if(!displaysheetdata.equals(""))
       sheetdatadisplay="N";
       
       HashMap dbinfo = info.getDmbsInfoLst();%>
 <body onfocus="<%=onfocus%>" onload="MM_preloadImages('images/faunalogo.png')" topmargin="0" leftmargin="0" rightmargin="0" bottommargin="0">
 <input type="hidden" id="cut" value="<%=dbinfo.get("CUT")%>" />
<input type="hidden" id="sym" value="<%=dbinfo.get("SYM")%>" />
<input type="hidden" id="pol" value="<%=dbinfo.get("POL")%>" />
<% 
//util.updAccessLog(request,response,"Price Calculator", "Price Calculator");
  String target = "SC_0";
%>
<div id="backgroundPopup"></div>
<div id="popupContactASSFNL">
<table class="popUpTb">
 <tr><td height="5%">&nbsp;&nbsp;&nbsp;<html:button property="value(close)" styleClass="submit"  onclick="MKEmptyPopup('SC');disablePopupASSFNL()" value="Close"  /> </td></tr>
<tr><td valign="top" height="95%">
<iframe name="SC" class="frameStyle"   align="middle" frameborder="0">

</iframe></td></tr>
</table>
</div>
<div id="popupContactSave" align="center" >
<img src="../images/loading_flame.gif" height="150px" width="150px" border="0" />
</div>
<input type="hidden" id="reqUrl" name="reqUrl" value="<%=info.getReqUrl()%>"/>
<input type="hidden" id="cnt" name="cnt" value="<%=cntn%>"/>
<table width="100%"  border="0"  cellpadding="0" cellspacing="0" class="mainbg">
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
  <table cellpadding="1"  cellspacing="5"><tr><td valign="middle">
  <img src="../images/line.jpg" border="0" /></td><td valign="middle"> <span class="pgHed">Price Calculator (<%=prcCalcGrp.replaceAll("_", "")%>)</span> </td>
<td id="loading"></td>
</tr></table>
  </td>
  </tr>
   <tr>
  <td class="tdLayout" valign="top">
  <html:form action="pri/PriceCalc.do?method=fetch" method="POST">
  <html:hidden property="value(prcCalcGrp)" value="<%=prcCalcGrp%>" />
    <table cellpadding="5" cellspacing="0" border="0" class="tbl_prc_calc">
      <tr>
        <td><strong>Copy Value From VNM</strong></td>
        <td><html:text property="value(vnm)"  name="priceCalcForm" /></td>
        <td><html:submit property="submit" value="Fecth" styleClass="submit"/></td>
        <%
  ArrayList rolenmLst=(ArrayList)info.getRoleLst();
  String usrFlg=util.nvl((String)info.getUsrFlg());
  if(usrFlg.equals("SYS") || rolenmLst.contains("ADMIN")){
  %>
  <td><img src="../images/edit.jpg" title="Edit Properties Module" onclick="newWindow('<%=info.getReqUrl()%>/dynamicProperty.do?method=load&mdl=PRI_CALC&sname=prpCalcList&par=A')" border="0" width="15px" height="15px"/> </td>
  <%}%>
<td>
<span class="" id="" style="" onclick="" title="Search Form By Grid View"><img src="../images/grid.jpg" id="" class="img" width="20" height="24" onclick="goTo('PriceCalc.do?method=load&searchPriCalcView=GRID&prcCalcGrp=<%=prcCalcGrp.replaceAll("_","")%>','','')" style="display:block" border="0"/></span>
</td>
<td>
<span class="" id="" style="" onclick="" title="Search Form By List View"><img src="../images/ListView.png" id="" width="20" height="20" class="img" onclick="goTo('PriceCalc.do?method=load&searchPriCalcView=LIST&prcCalcGrp=<%=prcCalcGrp.replaceAll("_","")%>','','')" style="display:block" border="0"/></span>
</td>
      </tr>
    </table>
 
 </html:form></td></tr>
  <html:form action="pri/PriceCalc.do?method=generate" method="POST">
  <html:hidden property="value(stkIdn)" styleId="stkIdn" name="priceCalcForm" />
  <html:hidden property="value(Idn)" />
  <html:hidden property="value(prcCalcGrp)" value="<%=prcCalcGrp%>" />
  <%
  ArrayList prpCalcList = (ArrayList)session.getAttribute(prcCalcGrp+"prpCalcList");
  ArrayList packetHis = (ArrayList)session.getAttribute("packetHis");
  HashMap mprp = info.getMprp();
  HashMap prp = info.getPrp();
  if(prpCalcList!=null && prpCalcList.size()>0){%>
      <tr><td><table class="grid1" id="dataTable"><tr id="<%=target%>"></tr></table></td></tr>
    <tr>
  <td class="tdLayout" valign="top">
 <html:submit property="value(generate)" styleClass="submit" value="Generate" />&nbsp;&nbsp;
       <logic:present property="value(stkIdn)"  name="priceCalcForm" >
  <html:submit property="value(submit)" styleClass="submit" value="RePricing" />&nbsp;&nbsp;
      </logic:present>
    <input type="submit" name="submit" value="Compare" class="submit"/>&nbsp;&nbsp;
  <html:submit property="value(reset)" styleClass="submit" value="Reset" />
  <%if(packetHis!=null && packetHis.size()>0){%>
  <a href="PriceCalc.do?method=history" id="LNK_0" onclick="loadASSFNL('<%=target%>','LNK_0')"  target="SC"><span class="txtLink">Current History</span></a>
<%}
 if(prcCalcGrp.indexOf("MFG") > -1){
%>
  Pkt Type:
  <html:select property="value(pkt_ty)" styleId="pkt_ty" name="priceCalcForm" >
  <html:option value="" >--Select--</html:option>
  <html:option value="NR" >Single</html:option>
  <html:option value="SMX" >Single Mix</html:option>
  <html:option value="MIX" >Mix</html:option>
  </html:select>
  <%}%>
  <html:checkbox property="value(is3Ex)" value="is3Ex"   styleId="is3Ex" name="priceCalcForm" onclick="setEXVGPrcCalc('is3Ex','EX')" />3 EX
  <html:checkbox property="value(is3VG)" value="is3VG"   styleId="is3VG" name="priceCalcForm" onclick="setEXVGPrcCalc('is3VG','VG')" />3 VG
  </td></tr>
    <tr>
  <td class="tdLayout" height="30" valign="middle">
  
    <logic:present property="value(vnm)"  name="priceCalcForm" >
     <!--<input type="radio" id="AU" value="AU" checked="checked" name="changes" />Auto&nbsp;&nbsp;&nbsp;
     <input type="radio" id="MN" value="MN"  name="changes" />Manual-->
     <%
      pageList=(ArrayList)pageDtl.get("RADIO");
      if(pageList!=null && pageList.size() >0){
      for(int j=0;j<pageList.size();j++){
      pageDtlMap=(HashMap)pageList.get(j);
      fld_nme=(String)pageDtlMap.get("fld_nme");
      val_cond=(String)pageDtlMap.get("val_cond");
      fld_ttl=(String)pageDtlMap.get("fld_ttl");
      dflt_val=(String)pageDtlMap.get("dflt_val");
      fld_typ=(String)pageDtlMap.get("fld_typ");
      flg1=(String)pageDtlMap.get("flg1");
      %>
   <input type="radio" id="<%=fld_typ%>" value="<%=dflt_val%>" <%=val_cond%> name="<%=fld_nme%>" /><%=fld_ttl%>
<%}}%> 
    </logic:present>
   </td></tr>
  <tr>
  <td class="tdLayout" valign="top">
  <%if(searchPriCalcView.equals("LIST")){%>
  <table cellspacing="0px" bgcolor="#ededed" class="genericTBNew" style="padding-bottom:20px">
  <%
  for(int i=0 ; i < prpCalcList.size() ; i++){
  String lprp =(String)prpCalcList.get(i);
  String lprpDsc = util.nvl((String)mprp.get(lprp+"D"));
  String lprpTyp = util.nvl((String)mprp.get(lprp+"T"));
  ArrayList prpLst = (ArrayList)prp.get(lprp+"V");
  ArrayList prpSrt = (ArrayList)prp.get(lprp+"S");
  ArrayList prpLstPc = (ArrayList)prp.get(lprp+"PC");
  if(lprpTyp.equals("C")){
  String fldVal = "value("+lprp+")";
  if(lprpDsc.equals(""))
   lprpDsc = lprp;
  %>
  <tr>
  <td align="center" nowrap="nowrap"><span class="txtBoldNew"><%=lprpDsc%></span></td><td class="lft-border"></td>
  <%if(prpLst!=null && prpLst.size()>0){%>
  <%
  int listCnt=0,prpcountinline=14;
  for(int j=0 ; j< prpLst.size() ; j++){
  String lprpVal = util.nvl((String)prpLst.get(j));
  String lprpSrt = util.nvl((String)prpSrt.get(prpLst.indexOf(lprpVal)));
  String lprpPcVal = util.nvl((String)prpLstPc.get(j),"Y");
  boolean dis=true;
      if(((!(prcCalcGrp.replaceAll("\\_", "").replaceAll("NN", "MKT")).equals(lprpPcVal) && !lprpPcVal.equals("Y")) || lprpPcVal.equals("N")) && !prcCalcGrp.equals(""))
  dis=false;
//  if(prcCalcGrp.indexOf("MFG") > -1){
//      String plusminusvall =(lprpVal.replaceAll("\\+","")).replaceAll("\\-","");
//      int indexplusminusvall=prpLst.indexOf((lprpVal.replaceAll("\\+","")).replaceAll("\\-",""));
//      if(indexplusminusvall==-1 || lprpVal.equals(plusminusvall))
//      dis=true;
//      else
//      dis=false;
//  }
  if(dis){
  String styleId=lprp+"_"+lprpSrt;
  String onClick = "priCalcList('"+lprp+"','"+prcCalcGrp+"','"+styleId+"','"+sheetdatadisplay+"')";
                if(listCnt==prpcountinline){%>
                </tr><tr><td></td><td class="lft-border"></td>
                <% listCnt=0;} 
                listCnt++;%>
             <td align="left">
             <div id="ck-button" >
             <label style="">
             <html:radio  property="<%=fldVal%>"  name="priceCalcForm" styleId="<%=styleId%>" onchange="<%=onClick%>" value="<%=lprpVal%>"/>
             <span><%=lprpVal%></span>
             </label>
             </div>
             </td>
  <%} }%>
   <%}%>
   </tr>
   <%}else{
  prpLst = (ArrayList)prp.get(lprp+"V");
  prpSrt = (ArrayList)prp.get(lprp+"S");
  String fldVal = "value("+lprp+")";
  if(lprpDsc.equals(""))
   lprpDsc = lprp;
  %>
  <tr><td align="center" nowrap="nowrap"><span class="txtBoldNew"><%=lprpDsc%></span></td><td class="lft-border"></td>
  <%
    String onClick = "priCalc('"+lprp+"','"+prcCalcGrp+"','"+sheetdatadisplay+"')";%>
             <td align="left" colspan="3">
              <html:text property="<%=fldVal%>" name="priceCalcForm" styleId="<%=lprp%>" styleClass="textNew" style="width:90px" onchange="<%=onClick%>" size="40"/> 
              <%if(lprp.equals("RAP_RTE")){%>
               <bean:write property="value(RAP_DTE)" name="priceCalcForm" />
              <%}%>
             </td>
             </tr>
   <%
   }}%>
  </table>  
  <%}else{%>
  <table><tr>
  <%
  int cnt=0;
  for(int i=0 ; i < prpCalcList.size() ; i++){
  cnt++;
  String lprp =(String)prpCalcList.get(i);
  String lprpDsc = util.nvl((String)mprp.get(lprp+"D"));
  ArrayList prpLst = (ArrayList)prp.get(lprp+"V");
  ArrayList prpSrt = (ArrayList)prp.get(lprp+"S");
  ArrayList prpLstPc = (ArrayList)prp.get(lprp+"PC");
  String fldVal = "value("+lprp+")";
  String onClick = "priCalc('"+lprp+"','"+prcCalcGrp+"','"+sheetdatadisplay+"')";
  if(lprpDsc.equals(""))
   lprpDsc = lprp;
  %>
  <td><%=lprpDsc%></td>
  <td>
  <%if(prpLst!=null && prpLst.size()>0){%>
  <html:select property="<%=fldVal%>" name="priceCalcForm" styleId="<%=lprp%>" onchange="<%=onClick%>" style="width:75px" >
  <%
  for(int j=0 ; j< prpLst.size() ; j++){
  String lprpVal = (String)prpLst.get(j);
  String lprpSrt = (String)prpSrt.get(prpLst.indexOf(lprpVal));
  String lprpPcVal = util.nvl((String)prpLstPc.get(j),"Y");
    boolean dis=true;
    if(((!(prcCalcGrp.replaceAll("\\_", "").replaceAll("NN", "MKT")).equals(lprpPcVal) && !lprpPcVal.equals("Y")) || lprpPcVal.equals("N")) && !prcCalcGrp.equals(""))
    dis=false;
//  if(prcCalcGrp.indexOf("MFG") > -1){
//      String plusminusvall =(lprpVal.replaceAll("\\+","")).replaceAll("\\-","");
//      int indexplusminusvall=prpLst.indexOf((lprpVal.replaceAll("\\+","")).replaceAll("\\-",""));
//      if(indexplusminusvall==-1 || lprpVal.equals(plusminusvall))
//      dis=true;
//      else
//      dis=false;
//  }
  if(dis){
  %>
 <html:option value="<%=lprpVal%>" ><%=lprpVal%></html:option>
  <% }}%>
 </html:select>
  <%}else{%>
  <html:text property="<%=fldVal%>" name="priceCalcForm" styleId="<%=lprp%>" onchange="<%=onClick%>" size="10"/> 
  <%if(lprp.equals("RAP_RTE")){%>
   <bean:write property="value(RAP_DTE)" name="priceCalcForm" />
  <%}%>
  <%}%>
  </td>
   <%if(cnt==6){%>
   </tr><tr>
  <% cnt=0;}}%>
  </table>
  <%}%>
  </td></tr>
  <%}%>
  </html:form>
  <tr>
  <td class="tdLayout" valign="top">
  <div id="prcDis">
<%
   String view =(String)request.getAttribute("view");
  if(view!=null){
  pageList=(ArrayList)pageDtl.get("GRPDTE");
       boolean isGRPDte = false;
      if(pageList!=null && pageList.size() >0)
        isGRPDte = true;
 
  HashMap pktDtlList = (HashMap)request.getAttribute("pktDtlList"); 
 HashMap grp_idnList = (HashMap)request.getAttribute("grp_idnList");
     ArrayList idnDtl = (ArrayList)request.getAttribute("idnDtl");
     ArrayList mstkidnLst = (ArrayList)request.getAttribute("mstkidnLst");
     ArrayList pktTypLst = (ArrayList)request.getAttribute("pktTypLst");
     HashMap grpPktTyp = (HashMap)request.getAttribute("grpPktTyp");
     String submit = (String)request.getAttribute("submit");
  %>
  <table><tr><td>
  <B><label id="prcStn"> Pricing Details For Packet :- <%=util.nvl((String)request.getAttribute("mstkIdn"))%></label></b>
  </td></tr>
  <tr><td>
  <div id="prcDtl">
  <%if(idnDtl!=null && idnDtl.size()>0){
  %>
  <p style="margin-left:20px;"><a name="#calc">Calculation Details</a></p>
       <table border=0 cellpadding="5" cellspacing="1" style="border:dotted 1px #CCCCCC;">
        <tr><th>Packet Id</th>
       
        <%if(prcCalcGrp.indexOf("NN") > -1 || prcCalcGrp.equals("") || prcCalcGrp.indexOf("MKT") > -1){%>
        <th>Rte</th>
        <th>Amount</th>
        <th>Dis</th>
        <%}%>
         <%if(prcCalcGrp.indexOf("MFG") > -1 || prcCalcGrp.equals("") || prcCalcGrp.indexOf("MKT") > -1){%>
         <th>MFG Price</th><th>MFG Discount</th>
         <%}%>
         <th>Mix Rte</th>
    
        <th>Rap Rte</th>
        <%if(cntn.equalsIgnoreCase("KJ")){%>
        <%if(prcCalcGrp.indexOf("MFG") > -1 || prcCalcGrp.equals("") ||prcCalcGrp.indexOf("MKT") > -1){%>
        <th>MFG CMP</th><th>MFG CMP Discount</th><th>MFG CMP VLU</th>
        <%}}%>
        
        </tr>
              <% for(int i=0;i<idnDtl.size();i++){
               HashMap idnDtlList=(HashMap)idnDtl.get(i);
               String mfg_pri =util.nvl((String)idnDtlList.get("MFG_PRI"));
                String mfg_dis =util.nvl((String)idnDtlList.get("MFG_DIS"));
               %> 
               <tr>
               <td><%=idnDtlList.get("vnm")%></td>
               <%if(prcCalcGrp.indexOf("NN") > -1 || prcCalcGrp.equals("") || prcCalcGrp.indexOf("MKT") > -1){%>
                <td><%=idnDtlList.get("CMP_RTE")%></td>
                <td><%=idnDtlList.get("AMT")%></td>
                <td><%=idnDtlList.get("CMP_DIS")%></td>
               <%}%>
                <%if(prcCalcGrp.indexOf("MFG") > -1 || prcCalcGrp.equals("") || prcCalcGrp.indexOf("MKT") > -1){%>
                <td><%=idnDtlList.get("MFG_PRI")%></td>
                <td><%=idnDtlList.get("MFG_DIS")%></td>
                <%}%>
                <td><%=idnDtlList.get("MIX_RTE")%></td>
                
                
            
              
            
               <td><%=idnDtlList.get("RAP_RTE")%></td>
               <%if(cntn.equalsIgnoreCase("KJ")){%>
               <%if(prcCalcGrp.indexOf("MFG") > -1 || prcCalcGrp.equals("") || prcCalcGrp.indexOf("MKT") > -1){%>
               <td><%=idnDtlList.get("MFG_CMP")%></td>
                <td><%=idnDtlList.get("MFG_CMP_DIS")%></td>
                <td><%=idnDtlList.get("MFG_CMP_VLU")%></td>
              <%}}%>
              
              </tr>
                <%}%>
        </table>
    <%}%>    
  </div>
  </td></tr>
  <tr>
  <td>
  <div style="<%=displaysheetdata%>">
  <%for(int i=0;i< pktTypLst.size();i++){
  String pktTyp = util.nvl((String)pktTypLst.get(i));
  ArrayList grp = (ArrayList)grpPktTyp.get(pktTyp);
  String hdr="Single Stone Pricing";
  if(pktTyp.equals("M"))
  hdr="Mix Stone Pricing";
  %>
  <div class="float" style="padding-right:5px;">
  <table class="grid1">
  <tr><th colspan="<%=mstkidnLst.size()+1%>"><%=hdr%></th>
    <%if(isGRPDte){%>
                <th>Last Update Date</th>
                <%}%>
  </tr>
  <tr><td>Packet Id</td>
  <%
  
                for(int j=0;j<idnDtl.size();j++){
                 HashMap idnDtlList=(HashMap)idnDtl.get(j);
                %>
                 <td><%=idnDtlList.get("vnm")%></td>
                <%}%>
                <%if(isGRPDte){%>
                <td></td>
                <%}%>
  </tr>
   <% for(int j=0;j<grp.size();j++){
              String grpName=util.nvl((String)grp.get(j));
              %>
              <tr>
               <td style="border:dotted 1px #CCCCCC"><%=grpName%></td>
               <%for(int k=0;k<mstkidnLst.size();k++){
               String mstk_idn=util.nvl((String)mstkidnLst.get(k));
               String pct = util.nvl((String)grp_idnList.get(mstk_idn+"_"+grpName+"_"+pktTyp));
               %> 
               <td style="border:dotted 1px #CCCCCC"><%=pct%></td>
               <%if(isGRPDte){
            String dte = util.nvl((String)grp_idnList.get(mstk_idn+"_"+grpName+"_"+pktTyp+"_DTE")); %>
            <td style="border:dotted 1px #CCCCCC"><%=dte%></td>

               <%}%>
               <%}%>
               </tr>
               <%}%>
  </table></div><%}%>
  </div>
  </td></tr>
  </table>
 <%}%>
  </div>
  </td></tr>
  </table>
  
  
  
  </body>
</html>