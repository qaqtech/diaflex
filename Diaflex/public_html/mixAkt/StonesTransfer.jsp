<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page errorPage="../error_page.jsp" %>
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import="java.util.LinkedHashMap,java.util.ArrayList,java.util.Set,java.util.HashMap,java.sql.ResultSet,ft.com.*, java.util.Date, java.net.URLEncoder"%>

<jsp:useBean id="util" class="ft.com.JspUtil" scope="page" />
<jsp:useBean id="info" class="ft.com.InfoMgr" scope="session" />
<jsp:useBean id="gtMgr" class="ft.com.GtMgr" scope="session" />
<%@taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>


<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
<link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/bse.css?v=<%=info.getJsVersion()%>"/>
 <script type="text/javascript" src="<%=info.getReqUrl()%>/PopScripts/popup.js?v=<%=info.getJsVersion()%> " > </script>
<link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/general.css?v=<%=info.getJsVersion()%>"/>
      <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/style.css?v=<%=info.getJsVersion()%>"/>
             <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/Validation.js?v=<%=info.getJsVersion()%> " > </script>
               <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/priceChange.js?v=<%=info.getJsVersion()%> " > </script>
 <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/bse.js?v=<%=info.getJsVersion()%> " > </script>
<script src="<%=info.getReqUrl()%>/PopScripts/jquery-1.2.6.min.js" type="text/javascript"></script>
    <title>Stones Transfer</title>
    
</head>
        <%String logId=String.valueOf(info.getLogId());
        String onfocus="cook('"+logId+"')";
        String lstNme = (String)request.getAttribute("lstNme");
        String typ = util.nvl((String)request.getAttribute("TYP"));
        HashMap mprp = info.getMprp();
        HashMap prp = info.getPrp();
    HashMap allPageDtl= (info.getPageDetails() == null)?new HashMap():(HashMap)info.getPageDetails();
    HashMap pageDtl=(HashMap)allPageDtl.get("STONESTRANS");
    ArrayList prpDspBlocked = info.getPageblockList();
    ArrayList pageList=new ArrayList();
    HashMap pageDtlMap=new HashMap();
    String fld_nme="",fld_ttl="",val_cond="",dflt_val="",fld_typ="",form_nme="",lov_qry="";
    String onfetchValid="";
     pageList=(ArrayList)pageDtl.get("PRPVALID");
      if(pageList!=null && pageList.size() >0){
      for(int j=0;j<pageList.size();j++){
      pageDtlMap=(HashMap)pageList.get(j);
      dflt_val=(String)pageDtlMap.get("dflt_val");
      onfetchValid = (String)pageDtlMap.get("val_cond");
      %>
    <input type="hidden" id="lprpValid" name="lprpValid" value="<%=dflt_val%>" />
     <% } }
     String avgcal="";
      pageList=(ArrayList)pageDtl.get("AVGCAL");
      if(pageList!=null && pageList.size() >0){
      for(int j=0;j<pageList.size();j++){
      pageDtlMap=(HashMap)pageList.get(j);
      avgcal=util.nvl((String)pageDtlMap.get("dflt_val"));
      if(avgcal.equals("Y")){
      %>
        <input type="hidden" id="avgCal" name="avgCal" value="<%=avgcal%>" />
      <%}}}%>
        
 <body onfocus="<%=onfocus%>" onload="MM_preloadImages('images/faunalogo.png')" topmargin="0" leftmargin="0" rightmargin="0" bottommargin="0">
 <img src="../images/loadbig.gif" vspace="15" id="load" style="display:none;" class="loadpktDiv" border="0" />
  <div id="backgroundPopup"></div>
<div id="popupContactSave" align="center" >
<img src="../images/loading_flame.gif" height="150px" width="150px" border="0" />
</div>
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
  <img src="../images/line.jpg" border="0" /></td><td valign="middle"> <span class="pgHed">Stones Transfer</span> </td>
</tr></table>
  </td>
  </tr>
  <tr>
  <td valign="top" class="tdLayout">
  <%String msg=(String)request.getAttribute("msg");
  if(msg!=null){%>
   <span class="redLabel"> <%=msg%></span>
  <%}%>
  </td></tr>
  <tr>
  <td valign="top" class="tdLayout">
  <%
 
 
  ArrayList frmCrtList = (ArrayList)gtMgr.getValue("FRMSRCH"+typ);
   ArrayList toCrtList = (ArrayList)gtMgr.getValue("TOSRCH"+typ);
    ArrayList srchPrp = info.getGncPrpLst();
   
  
    ArrayList prpPrt =null;
    ArrayList prpSrt = null;
    ArrayList prpVal =null;
    String fld1 ="";
    String fld2 ="";
    String prpFld1 ="";
    String prpFld2 ="";
    String onChg1 ="";
    String onChg2 ="";
    String dfltDsp = "&nbsp;&nbsp;&nbsp;&nbsp;---select---&nbsp;&nbsp;&nbsp;&nbsp;";
    String dfltVal = "";
    String lprpTyp="";
    String lprpDsc="";
    String lprp ="";
    String flgtyp="";
      
    ArrayList prplist=null;
  %>
   <html:form action="mixAkt/stonesTransferAction.do?method=fetch" method="post" enctype="multipart/form-data" onsubmit="<%=onfetchValid%>">
  <table cellpadding="2" cellspacing="2">
  <tr>
  <td valign="top">
 
  <input type="hidden" name="lstNme" id="lstNme" value="<%=lstNme%>" />
   <input type="hidden" name="TYP" id="TYP" value="<%=typ%>" />
  <table class="grid1">
  <tr><th colspan="2">From</th></tr>
  <tr><th>Packets </th><th>Genric Search</th></tr>
  <tr><td colspan="2">Status : &nbsp;
  <html:select property="value(sttFm)" name="stonesTransferForm">
        <html:optionsCollection property="frmSttList" label="dsc" value="nme" />
  </html:select>
  </td></tr>
  <tr><td>
  <html:textarea property="value(vnmFrmLst)" rows="10" cols="25" name="stonesTransferForm"/>
   </td>
  <td valign="top">
  
  
  <table>
   <% if(frmCrtList!=null && frmCrtList.size()>0){
         for(int i=0;i<frmCrtList.size();i++){
      String val1 = "";
      String val2= "";
       prplist =(ArrayList)frmCrtList.get(i);
       if(prplist!=null){
       lprp = util.nvl((String)prplist.get(0));
        flgtyp =util.nvl((String)prplist.get(1));
       if(prpDspBlocked.contains(lprp)){
       }else{
        lprpTyp = util.nvl((String)mprp.get(lprp+"T"));
        lprpDsc = util.nvl((String)mprp.get(lprp+"D"));
      
       fld1 = lprp+"_F1";
      fld2 = lprp+"_F2";
       prpFld1 = "value(" + fld1 + ")" ;
       prpFld2 = "value(" + fld2 + ")" ; 
      onChg1 = "setFrTo('"+ fld1 + "','" + fld2 + "')";
       onChg2 = "setFrTo('"+ fld1 + "','" + fld2 + "')";
       if(flgtyp.equals("FT"))
          onChg1 = "setFrToSameVal('"+ fld1 + "','" + lprp+"_T1','F')";
       %>
       <tr>
       <td align="center" nowrap="nowrap"><%=lprpDsc%></td>
       <%
        if(lprpTyp.equals("C")  && !flgtyp.equals("CTA")) {
         prpPrt = (ArrayList)prp.get(lprp+"P");
         prpSrt = (ArrayList)prp.get(lprp+"S");
         prpVal = (ArrayList)prp.get(lprp+"V");
         if(flgtyp.equals("M")){
         onChg1 = "setFrTo('"+ fld1 + "','" + fld2 + "');setFrToCheck('"+ fld1 + "','" + fld2 + "','"+lprp+"')\"";
              onChg2 = "setFrTo('"+ fld1 + "','" + fld2 + "');setFrToCheck('"+ fld1 + "','" + fld2 + "','"+lprp+"')\"";
             String mutiTextId = "MTXTF_"+lprp;
             String mutiText = "value("+ mutiTextId +")";%>
             <td colspan="2" align="center">
            <% if(prpSrt != null) {
               String loadStrL = "ALL";
             %>
             <div  class="multiplePrpdiv" id="<%=lprp%>" align="center" style="display:none; padding-top:2px; margin-top:20px; z-index:1">
             <table cellpadding="0"  class="multipleBg" cellspacing="0">
             <tr><td>
             <table>
             <tr>
             <td> <input type="checkbox" name="selectALL" id="SALL_<%=lprp%>" onclick="CheckAlllprp('<%=lprp%>',this)" > Select All &nbsp; </td>
             <td> From: &nbsp; 
             
           <html:select  property="<%=prpFld1%>" style="width:75px" onchange="<%=onChg1%>" styleId="<%=fld1%>"  > 
     
            <html:option value="<%=dfltVal%>"><%=dfltDsp%></html:option>
            <%
            for(int j=0; j < prpSrt.size(); j++) {
                String pPrt = util.nvl((String)prpPrt.get(j));
                String pSrt = util.nvl((String)prpSrt.get(j));
               
                  String isSelected = "" ;
               if(pSrt.equals(val1))
                   isSelected = "selected=\"selected\"";
            %>
              <html:option value="<%=pSrt%>" ><%=pPrt%></html:option> 
            <%}%>
              </html:select>
              </td><td>
            To:&nbsp;
             
           <html:select property="<%=prpFld2%>" style="width:75px"  onchange="<%=onChg2%>" styleId="<%=fld2%>"  > 
     
            <html:option value="<%=dfltVal%>"><%=dfltDsp%></html:option>
            <%
            for(int j=0; j < prpSrt.size(); j++) {
                String pPrt = util.nvl((String)prpPrt.get(j));
                String pSrt = util.nvl((String)prpSrt.get(j));
               
                  String isSelected = "" ;
               if(pSrt.equals(val1))
                   isSelected = "selected=\"selected\"";
            %>
              <html:option value="<%=pSrt%>" ><%=pPrt%></html:option> 
            <%}%>
              </html:select>   
             </td> 
            <td><img src="../images/cross.png" border="0" onclick="Hide('<%=lprp%>')" /> </td>
            </tr>
            </table>
            </td></tr>
            <tr><td><hr style="color:White;"></td></tr>
            <tr>
            <td>
             <%
             int listCnt=0;
             for(int m=0;m<prpSrt.size();m++){
                String isSelected = "";
                String pPrtl = util.nvl((String)prpPrt.get(m));
                String pSrtl = util.nvl((String)prpSrt.get(m));
                String vall = util.nvl((String)prpVal.get(m));
                String chFldNme = "value("+lprp+"F_"+vall+")" ;
                String onclick= "checkPrp(this, 'MTXTF_"+lprp+"','"+lprp+"')";
                String checId = lprp+"T_"+pSrtl;
               if(m==0){
                %>
                <table>
                <tr>
                <%}
                if(listCnt==7){%>
                </tr><tr>
                <% listCnt=0;} 
                listCnt++;
//                String fld = util.nvl((String)favMap.get(lprp+"_"+pSrtl));
//                 if(fld.equals(pSrtl+"_to_"+pSrtl)){
//                   isSelected = "checked=\"checked\"";
//                   loadStrL = loadStrL+" , "+pPrtl;
//                   }
             %>
             <td align="center"><html:checkbox  property="<%=chFldNme%>" styleId="<%=checId%>"  onclick="<%=onclick%>" value="<%=vall%>"/></td>
             <!--<td align="left"><span style="margin-left:10px;"><%=pPrtl%></span></td>-->
             <td align="left"><span><%=pPrtl%></span></td>
             <%}%>
             </tr></table>
            </td>
            </tr>
            </table>
             <input type="hidden" id="HID_<%=lprp%>" value="<%=loadStrL%>" />
              
             </div>
                           <table cellpadding="0" cellspacing="0">
              <tr><td>  
            <html:text property="<%=mutiText%>"  value="<%=loadStrL%>" size="30" styleId="<%=mutiTextId%>"  styleClass="txtStyle" />
             </td><td>&nbsp;&nbsp;</td>
              <td><img src="../images/plus.jpg" id="IMGD_<%=lprp%>" class="img" onclick="DisplayDiv('<%=lprp%>')" style="display:block" border="0"/></td><td><img src="../images/minus.jpg" id="IMGU_<%=lprp%>" class="img" onclick="Hide('<%=lprp%>')" style="display:none" border="0"/></td>
             
              </tr></table>
             <%}%>
             </td>
         <%}else{
            %>
            <td align="center">
            <%if(flgtyp.equals("FT")){%>
            <span class="redLabel">*</span><%}%>
            <html:select property="<%=prpFld1%>"   style="width:100px" onchange="<%=onChg1%>" styleId="<%=fld1%>"  > 
     
            <html:option value="<%=dfltVal%>"><%=dfltDsp%></html:option>
            <%
            for(int j=0; j < prpSrt.size(); j++) {
                String pPrt = util.nvl((String)prpPrt.get(j));
                String pSrt = util.nvl((String)prpSrt.get(j));
             %>
              <html:option value="<%=pSrt%>" ><%=pPrt%></html:option> 
            <%}%>
              </html:select>
            
            </td>
            <%
              if(!flgtyp.equals("S") && !flgtyp.equals("FT")){
              %>
            <td   align="center">
           
               <html:select property="<%=prpFld2%>"   style="width:100px" onchange="<%=onChg2%>" styleId="<%=fld2%>"  > 
     
            <html:option value="<%=dfltVal%>"><%=dfltDsp%></html:option>
            <%
            for(int j=0; j < prpSrt.size(); j++) {
                String pPrt = util.nvl((String)prpPrt.get(j));
                String pSrt = util.nvl((String)prpSrt.get(j));
               
                
            %>
              <html:option value="<%=pSrt%>" ><%=pPrt%></html:option> 
            <%}%>
              </html:select>
            </td>
            
        <% }else{%>
        <td></td>
        <%}
        }}else if(lprpTyp.equals("D")){%>
        <td bgcolor="#FFFFFF" align="center">
        <html:text property="<%=prpFld1%>" styleClass="txtStyle"  styleId="<%=prpFld1%>"  size="9" maxlength="25" /> <a href="#"  onClick="setYears(1900, '<%=info.getCurrentYear()%>' );showCalender(this, '<%=prpFld1%>');">
                <img src="<%=info.getReqUrl()%>/images/calender.png"></a></td>
        <td bgcolor="#FFFFFF" align="center"><html:text property="<%=prpFld2%>" styleId="<%=prpFld2%>" styleClass="txtStyle"    size="9" maxlength="25"  /> <a href="#"  onClick="setYears(1900, '<%=info.getCurrentYear()%>' );showCalender(this, '<%=prpFld2%>');">
                <img src="<%=info.getReqUrl()%>/images/calender.png"></a></td>
        
        <%} else if(lprpTyp.equals("T")  || flgtyp.equals("CTA")){%>
        <td bgcolor="#FFFFFF" align="center" colspan="2">
        <html:text property="<%=prpFld1%>" styleClass="txtStyle"   styleId="<%=fld1%>"  size="35"  /> 
        </td>
       
        <%} else{%>
           <td bgcolor="#FFFFFF" align="center"><html:text property="<%=prpFld1%>"  styleClass="txtStyle"  styleId="<%=fld1%>"  size="13" maxlength="25" onchange="isNumericDecimal(this.id)" /></td>
        <td bgcolor="#FFFFFF" align="center"><html:text property="<%=prpFld2%>"  styleClass="txtStyle"    size="13" maxlength="25" onchange="isNumericDecimal(this.id)" /></td>
        <%}%>
        </tr>
       <% }}}%>
     
       <%}%> 
         </table>
  
  
  </td></tr>
  </table></td>
  <td width="10px"></td>
  <td valign="top">
  
 
   <table class="grid1">
   <tr><th colspan="2">To</th></tr>
  <tr><th>Packets </th><th>Genric Search</th></tr>
  <tr><td colspan="2">Status : &nbsp;
  <html:select property="value(sttTo)" name="stonesTransferForm">
        <html:optionsCollection property="toSttList" label="dsc" value="nme" />
  </html:select>
  <%pageList=(ArrayList)pageDtl.get("UPLOADFILE");
      if(pageList!=null && pageList.size() >0 && typ.equals("")){
      for(int j=0;j<pageList.size();j++){
      pageDtlMap=(HashMap)pageList.get(j);
      fld_ttl=(String)pageDtlMap.get("fld_ttl");
      val_cond=(String)pageDtlMap.get("val_cond");
      fld_typ=(String)pageDtlMap.get("fld_typ");
      dflt_val = (String)pageDtlMap.get("dflt_val");%>
   &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<html:file property="loadFile" size="40" name="stonesTransferForm"  onchange="check_file();" styleId="fileUpload" />
   <html:submit property="value(file)" value="Fetch" styleClass="submit"/> 
<%}}%>
  </td></tr>
  <tr><td>
  <html:textarea property="value(vnmToLst)" rows="10" cols="25" name="stonesTransferForm" />
   </td>
  <td valign="top">
  
  <table>
   <% if(toCrtList!=null && toCrtList.size()>0){
         for(int i=0;i<toCrtList.size();i++){
      String val1 = "";
      String val2= "";
       prplist =(ArrayList)toCrtList.get(i);
       if(prplist!=null){
       lprp = util.nvl((String)prplist.get(0));
        flgtyp =util.nvl((String)prplist.get(1));
       if(prpDspBlocked.contains(lprp)){
       }else{
        lprpTyp = util.nvl((String)mprp.get(lprp+"T"));
        lprpDsc = util.nvl((String)mprp.get(lprp+"D"));
      
       fld1 = lprp+"_T1";
      fld2 = lprp+"_T2";
       prpFld1 = "value(" + fld1 + ")" ;
       prpFld2 = "value(" + fld2 + ")" ; 
      onChg1 = "setFrTo('"+ fld1 + "','" + fld2 + "')";
       onChg2 = "setFrTo('"+ fld1 + "','" + fld2 + "')";
       if(flgtyp.equals("FT"))
        onChg1 = "setFrToSameVal('"+ lprp+"_F1','" + fld1 + "','T')";
       %>
       <tr>
       <td align="center" nowrap="nowrap"><%=lprpDsc%></td>
       <%
        if(lprpTyp.equals("C")  && !flgtyp.equals("CTA")) {
         prpPrt = (ArrayList)prp.get(lprp+"P");
         prpSrt = (ArrayList)prp.get(lprp+"S");
         prpVal = (ArrayList)prp.get(lprp+"V");
         if(flgtyp.equals("M")){
         onChg1 = "setFrTo('"+ fld1 + "','" + fld2 + "');setFrToCheck('"+ fld1 + "','" + fld2 + "','"+lprp+"')\"";
              onChg2 = "setFrTo('"+ fld1 + "','" + fld2 + "');setFrToCheck('"+ fld1 + "','" + fld2 + "','"+lprp+"')\"";
             String mutiTextId = "MTXTT_"+lprp;
             String mutiText = "value("+ mutiTextId +")";%>
             <td colspan="2" align="center">
            <% if(prpSrt != null) {
               String loadStrL = "ALL";
             %>
             <div  class="multiplePrpdiv" id="<%=lprp%>" align="center" style="display:none; padding-top:2px; margin-top:20px; z-index:1">
             <table cellpadding="0"  class="multipleBg" cellspacing="0">
             <tr><td>
             <table>
             <tr>
             <td> <input type="checkbox" name="selectALL" id="SALL_<%=lprp%>" onclick="CheckAlllprp('<%=lprp%>',this)" > Select All &nbsp; </td>
             <td> From: &nbsp; 
             
           <html:select  property="<%=prpFld1%>" style="width:75px" onchange="<%=onChg1%>" styleId="<%=fld1%>"  > 
     
            <html:option value="<%=dfltVal%>"><%=dfltDsp%></html:option>
            <%
            for(int j=0; j < prpSrt.size(); j++) {
                String pPrt = util.nvl((String)prpPrt.get(j));
                String pSrt = util.nvl((String)prpSrt.get(j));
               
                  String isSelected = "" ;
               if(pSrt.equals(val1))
                   isSelected = "selected=\"selected\"";
            %>
              <html:option value="<%=pSrt%>" ><%=pPrt%></html:option> 
            <%}%>
              </html:select>
              </td><td>
            To:&nbsp;
             
           <html:select property="<%=prpFld2%>" style="width:75px" onchange="<%=onChg2%>" styleId="<%=fld2%>"  > 
     
            <html:option value="<%=dfltVal%>"><%=dfltDsp%></html:option>
            <%
            for(int j=0; j < prpSrt.size(); j++) {
                String pPrt = util.nvl((String)prpPrt.get(j));
                String pSrt = util.nvl((String)prpSrt.get(j));
               
                  String isSelected = "" ;
               if(pSrt.equals(val1))
                   isSelected = "selected=\"selected\"";
            %>
              <html:option value="<%=pSrt%>" ><%=pPrt%></html:option> 
            <%}%>
              </html:select>   
             </td> 
            <td><img src="../images/cross.png" border="0" onclick="Hide('<%=lprp%>')" /> </td>
            </tr>
            </table>
            </td></tr>
            <tr><td><hr style="color:White;"></td></tr>
            <tr>
            <td>
             <%
             int listCnt=0;
             for(int m=0;m<prpSrt.size();m++){
                String isSelected = "";
                String pPrtl = util.nvl((String)prpPrt.get(m));
                String pSrtl = util.nvl((String)prpSrt.get(m));
                String vall = util.nvl((String)prpVal.get(m));
                String chFldNme = "value("+lprp+"T_"+vall+")" ;
                String onclick= "checkPrp(this, 'MTXTT_"+lprp+"','"+lprp+"')";
                String checId = lprp+"T_"+pSrtl;
               if(m==0){
                %>
                <table>
                <tr>
                <%}
                if(listCnt==7){%>
                </tr><tr>
                <% listCnt=0;} 
                listCnt++;
//                String fld = util.nvl((String)favMap.get(lprp+"_"+pSrtl));
//                 if(fld.equals(pSrtl+"_to_"+pSrtl)){
//                   isSelected = "checked=\"checked\"";
//                   loadStrL = loadStrL+" , "+pPrtl;
//                   }
             %>
             <td align="center"><html:checkbox  property="<%=chFldNme%>" styleId="<%=checId%>"  onclick="<%=onclick%>" value="<%=vall%>"/></td>
             <!--<td align="left"><span style="margin-left:10px;"><%=pPrtl%></span></td>-->
             <td align="left"><span><%=pPrtl%></span></td>
             <%}%>
             </tr></table>
            </td>
            </tr>
            </table>
             <input type="hidden" id="HID_<%=lprp%>" value="<%=loadStrL%>" />
              
             </div>
                           <table cellpadding="0" cellspacing="0">
              <tr><td>  
            <html:text property="<%=mutiText%>"  value="<%=loadStrL%>" size="30" styleId="<%=mutiTextId%>"  styleClass="txtStyle" />
             </td><td>&nbsp;&nbsp;</td>
              <td><img src="../images/plus.jpg" id="IMGD_<%=lprp%>" class="img" onclick="DisplayDiv('<%=lprp%>')" style="display:block" border="0"/></td><td><img src="../images/minus.jpg" id="IMGU_<%=lprp%>" class="img" onclick="Hide('<%=lprp%>')" style="display:none" border="0"/></td>
             
              </tr></table>
             <%}%>
             </td>
         <%}else{
            %>
            <td align="center">  <%if(flgtyp.equals("FT")){%>
            <span class="redLabel">*</span><%}%>
            <html:select property="<%=prpFld1%>"   style="width:100px" onchange="<%=onChg1%>" styleId="<%=fld1%>"  > 
     
            <html:option value="<%=dfltVal%>"><%=dfltDsp%></html:option>
            <%
            for(int j=0; j < prpSrt.size(); j++) {
                String pPrt = util.nvl((String)prpPrt.get(j));
                String pSrt = util.nvl((String)prpSrt.get(j));
             %>
              <html:option value="<%=pSrt%>" ><%=pPrt%></html:option> 
            <%}%>
              </html:select>
            
            </td>
            <%
              if(!flgtyp.equals("S") && !flgtyp.equals("FT")){
              %>
            <td   align="center">
           
               <html:select property="<%=prpFld2%>"   style="width:100px" onchange="<%=onChg2%>" styleId="<%=fld2%>"  > 
     
            <html:option value="<%=dfltVal%>"><%=dfltDsp%></html:option>
            <%
            for(int j=0; j < prpSrt.size(); j++) {
                String pPrt = util.nvl((String)prpPrt.get(j));
                String pSrt = util.nvl((String)prpSrt.get(j));
               
                
            %>
              <html:option value="<%=pSrt%>" ><%=pPrt%></html:option> 
            <%}%>
              </html:select>
            </td>
            
        <% }else{%>
        <td></td>
        <%}
        }}else if(lprpTyp.equals("D") ){%>
        <td bgcolor="#FFFFFF" align="center">
        <html:text property="<%=prpFld1%>" styleClass="txtStyle"  styleId="<%=prpFld1%>"  size="9" maxlength="25" /> <a href="#"  onClick="setYears(1900, '<%=info.getCurrentYear()%>' );showCalender(this, '<%=prpFld1%>');">
                <img src="<%=info.getReqUrl()%>/images/calender.png"></a></td>
        <td bgcolor="#FFFFFF" align="center"><html:text property="<%=prpFld2%>" styleId="<%=prpFld2%>" styleClass="txtStyle"    size="9" maxlength="25"  /> <a href="#"  onClick="setYears(1900, '<%=info.getCurrentYear()%>' );showCalender(this, '<%=prpFld2%>');">
                <img src="<%=info.getReqUrl()%>/images/calender.png"></a></td>
        
        <%} else if(lprpTyp.equals("T") || flgtyp.equals("CTA")){%>
        <td bgcolor="#FFFFFF" align="center" colspan="2">
        <html:text property="<%=prpFld1%>" styleClass="txtStyle"   styleId="<%=fld1%>"  size="35"  /> 
        </td>
       
        <%} else{%>
           <td bgcolor="#FFFFFF" align="center"><html:text property="<%=prpFld1%>"  styleClass="txtStyle"  styleId="<%=fld1%>"  size="13" maxlength="25" onchange="isNumericDecimal(this.id)" /></td>
        <td bgcolor="#FFFFFF" align="center"><html:text property="<%=prpFld2%>"  styleClass="txtStyle"    size="13" maxlength="25" onchange="isNumericDecimal(this.id)" /></td>
        <%}%>
        </tr>
       <% }}}%>
     
       <%}%> 
       
         </table>
</td></tr>
 
</table>

  
  </td>
  
  </tr>
  <tr><td colspan="3" align="center"><html:submit property="value(submit)" value="Fetch" styleClass="submit"/> </td></tr>
 </table></html:form>
 </td></tr>
 
 <tr>
  <td valign="top" class="tdLayout">
  
  <%
  String view = (String)request.getAttribute("view");
  if(view!=null){
  %>
    <html:form action="mixAkt/stonesTransferAction.do?method=Save" method="post" >
    <input type="hidden" name="lstNme" id="lstNme" value="<%=lstNme%>" />
   <input type="hidden" name="TYP" id="TYP" value="<%=typ%>" />
  <table cellpadding="2" cellspacing="2">
  
  <tr><td><table cellpadding="1" cellspacing="1">
  <tr><td><html:submit property="value(submit)" value="Transfer" onclick="return VerifiedTransStoneAmt(this)" styleClass="submit"/>  </td>
  
 <%        pageList= ((ArrayList)pageDtl.get("BUTTON") == null)?new ArrayList():(ArrayList)pageDtl.get("BUTTON");
          
  if(pageList!=null && pageList.size() >0){
            for(int j=0;j<pageList.size();j++){
            pageDtlMap=(HashMap)pageList.get(j);
            fld_nme=(String)pageDtlMap.get("fld_nme");
            fld_ttl=(String)pageDtlMap.get("fld_ttl");
            fld_typ=(String)pageDtlMap.get("fld_typ");
            dflt_val=(String)pageDtlMap.get("dflt_val");
            val_cond=(String)pageDtlMap.get("val_cond"); 
         
           
          
         if(fld_typ.equals("S")){
            %>
   <td> <html:submit property="<%=fld_nme%>" value="<%=fld_ttl%>" onclick="<%=val_cond%>" styleClass="submit"/></td>
    <%}else if(fld_typ.equals("B")){%>
    <td><html:button property="<%=fld_nme%>" value="<%=fld_ttl%>" onclick="<%=val_cond%>" styleClass="submit"/></td>
    <%}}}
    %>


  <td>Total Tansfer:-</td><td>Qty :</td><td><label id="TRN_QTY_LBL" class="redLabel">0</label> </td>
  <td>Cts :</td><td><label id="TRN_CTS_LBL" class="redLabel" >0</label> </td>
  <td>Avg Rate :</td><td><label id="TRN_RTE_LBL" class="redLabel" >0</label> </td>
  <td> </td>
  </tr></table></td><td></td><td>
  
  <table cellpadding="1" cellspacing="1">
  <tr>
  <td>Total Added:-</td><td>Qty :</td><td><label id="ADD_QTY_LBL" class="redLabel">0</label> </td>
  <td>Cts :</td><td><label id="ADD_CTS_LBL" class="redLabel" >0</label> </td>
   <td>Avg Rate :</td><td><label id="ADD_RTE_LBL" class="redLabel" >0</label> </td>
  <td> </td>
  </tr></table>
  
  </td></tr>
  <tr><td valign="top">
  <%
   HashMap stockListMap = (HashMap)gtMgr.getValue(lstNme+"_FMLST");
  if(stockListMap!=null && stockListMap.size()>0){
 
  String mdl="TRN_SIN_VW";
    if(typ.equals("MIX"))
              mdl="TRN_MIX_VW";
    if(typ.equals("RGH"))
        mdl="TRN_RGH_VW";
    ArrayList vwPrpLst = (ArrayList)session.getAttribute(mdl);
    if(vwPrpLst==null)
    vwPrpLst=new ArrayList();
    LinkedHashMap stockList = new LinkedHashMap(stockListMap); 
    ArrayList stockIdnLst =new ArrayList();
 Set<String> keys = stockList.keySet();
        for(String key: keys){
       stockIdnLst.add(key);
        }
    String onClick="ALLCheckBoxTyp('check','CHK_','1');StoneTransCalculation('CHK_','TRN')";
    if(typ.equals("MIX") || typ.equals("RGH"))
     onClick="ApplyCurrVal(this,'ALL','TRN','CHK_');StoneTransCalculation('CHK_','TRN')";
  %>
   <table class="grid1">
   <tr> <th>Sr</th>
        <th><input type="checkbox" name="check" id="check" onclick="<%=onClick%>" /></th>
        <th>Packet No.</th>
        <th>Qty</th>
        <th>Cts</th>
        <th>Rate</th>
        <th>Amount</th>
        <%if(typ.equals("MIX") || typ.equals("RGH")){%>
        <th>Trans Qty</th>
        <th>Trans Cts</th>
        <th>Trans Rate</th>
        <th>Final Qty</th>
        <th>Final Cts</th>
        <%if(typ.equals("RGH")){%>
        <th>Final Rate</th>
        <%}%>
        <%}%>
    <%for(int j=0; j < vwPrpLst.size(); j++ ){
    String lvprp = (String)vwPrpLst.get(j);
     String hdr = util.nvl((String)mprp.get(lvprp));
    String prpDsc = util.nvl((String)mprp.get(lvprp+"D"));
    if(hdr == null) {
        hdr = lvprp;
       }  
%>
<th title="<%=prpDsc%>"><%=hdr%></th>
<%}%>       
</tr>
  <%
  for(int i=0;i<stockIdnLst.size();i++){
  String stk_idn = (String)stockIdnLst.get(i);
  HashMap pktDtl = (HashMap)stockListMap.get(stk_idn);
  String vnm = util.nvl((String)pktDtl.get("vnm"));
  String qty =  util.nvl((String)pktDtl.get("qty"));
  String cts =  util.nvl((String)pktDtl.get("cts"));
  String quot =  util.nvl((String)pktDtl.get("quot"));
    String fnlPri =  util.nvl((String)pktDtl.get("fnlPri"));
    if(fnlPri.equals("") || fnlPri.equals("0") )
    fnlPri = quot;
  int sr=i+1;
  String checkFldId = "CHK_"+stk_idn;
 String checkFldVal = "value("+stk_idn+")";
 String checkQtyId = "TRN_QTY_"+stk_idn;
 String checkQtyVal = "value("+checkQtyId+")";
 String checkCTSId = "TRN_CTS_"+stk_idn;
 String checkCTSVal = "value("+checkCTSId+")";
 String checkRTEId = "TRN_RTE_"+stk_idn;
 String checkRTEVal = "value("+checkRTEId+")";
 String fnlFQty = "TRN_FQTY_"+stk_idn;
 String fnlFCts = "TRN_FCTS_"+stk_idn;
  String fnlFRte = "TRN_FRTE_"+stk_idn;
  String onchange="CalculateDiff(this,'"+stk_idn+"','F');StoneTransCalculation('CHK_','TRN')";
   onClick="StoneTransCalculation('CHK_','TRN')";
    if(typ.equals("MIX") || typ.equals("RGH"))
     onClick="ApplyCurrVal(this,this.id,'TRN','CHK_')";
   %>
  <tr><td>
  <%=sr%></td>
  <td>
<input type="checkbox" name="<%=checkFldId%>" id="<%=checkFldId%>" onclick="<%=onClick%>" value="<%=stk_idn%>" />
</td>
  <td><%=vnm%></td>
   <td><%=(String)pktDtl.get("qty")%>
   <input type="hidden" name="CUR_FQTY_<%=stk_idn%>" id="CUR_FQTY_<%=stk_idn%>" value="<%=(String)pktDtl.get("qty")%>" />
   </td>
  <td> <%=(String)pktDtl.get("cts")%>
   <input type="hidden" name="CUR_FCTS_<%=stk_idn%>" id="CUR_FCTS_<%=stk_idn%>" value="<%=(String)pktDtl.get("cts")%>" />
  </td>
   <td> <label id="CUR_FRTE_<%=stk_idn%>"><%=(String)pktDtl.get("quot")%></label>
      <input type="hidden" name="CUR_FRTE_<%=stk_idn%>"  value="<%=(String)pktDtl.get("quot")%>" />

   </td>
   <td><%=(String)pktDtl.get("amt")%></td>
   <%if(typ.equals("MIX") || typ.equals("RGH")){%>
  <td>
  <html:text property="<%=checkQtyVal%>" disabled="true" size="4"  onchange="<%=onchange%>" styleId="<%=checkQtyId%>"   name="stonesTransferForm"/>
  </td>
  <td>
  <html:text property="<%=checkCTSVal%>"  disabled="true" size="5" onchange="<%=onchange%>" styleId="<%=checkCTSId%>"  name="stonesTransferForm"/>
  </td><td>
  <html:text property="<%=checkRTEVal%>"  disabled="true" size="5"  styleId="<%=checkRTEId%>" onchange="<%=onchange%>" value="<%=fnlPri%>"  name="stonesTransferForm"/>
  </td>
  <td><input type="text" name="<%=fnlFQty%>" size="4" id="<%=fnlFQty%>" onchange="<%=onchange%>"   disabled="disabled"/> </td>
  <td><input type="text" name="<%=fnlFCts%>" size="5" id="<%=fnlFCts%>" onchange="<%=onchange%>"  disabled="disabled"/> </td>
  <%if(typ.equals("RGH")){
 %>
  <td><input type="text" name="<%=fnlFRte%>" size="5" id="<%=fnlFRte%>"   disabled="disabled"/> </td>
        <%}%>
  <%}else{%>
   <input type="hidden" name="<%=checkQtyId%>" id="<%=checkQtyId%>" value="<%=(String)pktDtl.get("qty")%>" />
    <input type="hidden" name="<%=checkCTSId%>" id="<%=checkCTSId%>" value="<%=(String)pktDtl.get("cts")%>" />
    <input type="hidden" name="<%=checkRTEId%>" id="<%=checkRTEId%>" value="<%=(String)pktDtl.get("quot")%>" />
  <%}%>
  <%for(int j=0; j < vwPrpLst.size(); j++ ){
    String lpprp = (String)vwPrpLst.get(j);%>
    <td><%= util.nvl((String)pktDtl.get(lpprp))%></td>
    <%}%>
  </tr>
  <%}%></table>
  <%}else{%>
  Sorry no result found...
  <%}%>
  </td><td width="20px"></td><td valign="top">
  
   <%

    ArrayList vwPrpToLst = (ArrayList)session.getAttribute("TRN_MIX_VW");
     if(vwPrpToLst==null)
    vwPrpToLst=new ArrayList();
    if(typ.equals("RGH"))
     vwPrpToLst = (ArrayList)session.getAttribute("TRN_RGH_VW");
  HashMap stockListToMap = (HashMap)gtMgr.getValue(lstNme+"_TOLST");
  if(stockListToMap!=null && stockListToMap.size()>0){
    LinkedHashMap stockToList = new LinkedHashMap(stockListToMap); 
    ArrayList stockIdnToLst =new ArrayList();
 Set<String> keysT = stockToList.keySet();
        for(String key: keysT){
       stockIdnToLst.add(key);
        }
  %>
   <table class="grid1">
   <tr> <th><input type="checkbox" name="check" id="check" onclick="ApplyCurrVal(this,'ALL','ADD','CHKR_');StoneTransCalculation('CHKR_','FNL')" /></th><th>Sr</th>
        <th></th>
        <th>Packet No.</th>
        <th>Qty</th>
        <th>Cts</th>
        <th>Rate</th>
        <th>Add Qty</th>
        <th>Add Cts</th>
         <th> Rate</th>
        <th>Final Qty</th>
        <th>Final Cts</th>
    <%for(int j=0; j < vwPrpToLst.size(); j++ ){
    String lTprp = (String)vwPrpToLst.get(j);
     String hdr = util.nvl((String)mprp.get(lTprp));
    String prpDsc = util.nvl((String)mprp.get(lTprp+"D"));
    if(hdr == null) {
        hdr = lTprp;
       }  
%>
<th title="<%=prpDsc%>"><%=hdr%></th>
<%}%>       
</tr>
 <%
 
 
 for(int i=0;i<stockIdnToLst.size();i++){
  String stk_idn = (String)stockIdnToLst.get(i);
  HashMap pktDtl = (HashMap)stockListToMap.get(stk_idn);
  String vnm = util.nvl((String)pktDtl.get("vnm"));
  String qty =  util.nvl((String)pktDtl.get("qty"));
  String cts =  util.nvl((String)pktDtl.get("cts"));
  String quot =  util.nvl((String)pktDtl.get("quot"));
  String fnlPri =  util.nvl((String)pktDtl.get("fnlPri"));
  int sr=i+1;
  String checkFldId = "CHKR_"+sr;
 String checkFldVal = "value("+stk_idn+")";
  String checkQtyId = "ADD_QTY_"+stk_idn;
 String checkQtyVal = "value("+checkQtyId+")";
 String checkCTSId = "ADD_CTS_"+stk_idn;
 String checkCTSVal = "value("+checkCTSId+")";
 String checkRTEId = "ADD_RTE_"+stk_idn;
 String checkRTEVal = "value("+checkRTEId+")";
 String fnlTQty = "ADD_TQTY_"+stk_idn;
 String fnlTCts = "ADD_TCTS_"+stk_idn;
 String appalyOn="ApplyCalcultion("+stk_idn+")";
 String onchange="CalculateDiff(this,'"+stk_idn+"','T');StoneTransCalculation('CHKR_','ADD')";
   %>
  <tr><td><input type="checkbox" name="<%=checkFldId%>" id="<%=checkFldId%>" value="<%=stk_idn%>" onclick="ApplyCurrVal(this,this.id,'ADD','CHKR_')" /> </td>
  <td>
  <html:button property="value(apply)" value="Apply" styleClass="submit" onclick="<%=appalyOn%>"/> 
  
  </td>
  <td><%=sr%></td>
  <td><%=vnm%></td>
  <td><%=(String)pktDtl.get("qty")%>
   <input type="hidden" name="CUR_TQTY_<%=stk_idn%>" id="CUR_TQTY_<%=stk_idn%>" value="<%=(String)pktDtl.get("qty")%>" />
   </td>
  <td> <%=(String)pktDtl.get("cts")%>
   <input type="hidden" name="CUR_TCTS_<%=stk_idn%>" id="CUR_TCTS_<%=stk_idn%>" value="<%=(String)pktDtl.get("cts")%>" />
  </td>
    <td> <label id="CUR_TRTE_<%=stk_idn%>"><%=(String)pktDtl.get("quot")%></label> </td>
  <td><html:text property="<%=checkQtyVal%>"  onchange="<%=onchange%>" size="4" disabled="true" styleId="<%=checkQtyId%>"  name="stonesTransferForm"/></td>
  <td><html:text property="<%=checkCTSVal%>" onchange="<%=onchange%>" size="5" disabled="true" styleId="<%=checkCTSId%>"  name="stonesTransferForm"/></td>
  <td><html:text property="<%=checkRTEVal%>" styleId="<%=checkRTEId%>"   size="7" disabled="true"  name="stonesTransferForm" value="<%=fnlPri%>"/></td>
  <td><input type="text" name="<%=fnlTQty%>" size="4" id="<%=fnlTQty%>" onchange="<%=onchange%>"  disabled="disabled"/> </td>
  <td><input type="text" name="<%=fnlTQty%>" size="5" id="<%=fnlTCts%>" onchange="<%=onchange%>"  disabled="disabled"/> </td>
  <%for(int j=0; j < vwPrpToLst.size(); j++ ){
    String lTpprp = (String)vwPrpToLst.get(j);%>
    <td><%= util.nvl((String)pktDtl.get(lTpprp))%></td>
    <%}%>
  </tr>
  
  
  <%}%></table>
  <%}else{%>
 <b>Sorry there are no packets in selected criteria. plz checked...
 OR Create New packet </b>
 <table class="grid1">
   <tr> <th>NEW PKT</th>
        <th></th>
        <th>Final Qty</th>
        <th>Final Cts</th>
        <th>Final Rate</th>
    <%for(int j=0; j < vwPrpToLst.size(); j++ ){
    String lTprp = (String)vwPrpToLst.get(j);
     String hdr = util.nvl((String)mprp.get(lTprp));
    String prpDsc = util.nvl((String)mprp.get(lTprp+"D"));
    if(hdr == null) {
        hdr = lTprp;
       }  
      %>
      <th title="<%=prpDsc%>"><%=hdr%></th>
        <%}%>       
       </tr>
       
       <tr><td><input type="checkbox" name="toID" id="CHKR_NEW" checked="checked" value="NEW" /> </td>
        <td>
        
        <html:button property="value(apply)" value="Apply" styleClass="submit" onclick="ApplyCalcultion('')"/>
        
        </td>
       <td><label id="CUR_TQTY_NEW"></label> <html:text property="value(FNL_QTY_NEW)"  size="4" onchange="calNewPkt()" styleId="FNL_QTY_NEW"  name="stonesTransferForm"/></td>
  <td><label id="CUR_TCTS_NEW"></label><html:text property="value(FNL_CTS_NEW)"  size="5"  onchange="calNewPkt()" styleId="FNL_CTS_NEW"  name="stonesTransferForm"/></td>
  <td><label id="CUR_TRTE_NEW"></label><html:text property="value(FNL_RTE_NEW)"  size="5"  styleId="FNL_RTE_NEW"  name="stonesTransferForm"/></td>
  <%for(int j=0; j < vwPrpToLst.size(); j++ ){
    String lTpprp = (String)vwPrpToLst.get(j);
    String prpTyp = util.nvl((String)mprp.get(lTpprp+"T"));
    String fldNme="value("+lTpprp+")";
     ArrayList prpTVal = (ArrayList)prp.get(lTpprp+"V");
    ArrayList prpTPrt = (ArrayList)prp.get(lTpprp+"P");
    %>
   <%if(prpTyp.equals("C")){%>
   <td>  <html:select  property="<%=fldNme%>" styleId="<%=lTpprp%>" name="stonesTransferForm"  style="width:100px"   > 
      <html:option value="0">--select--</html:option>
            <%
            for(int k=0; k < prpTVal.size(); k++) {
              
             %>
              <html:option value="<%=(String)prpTVal.get(k)%>" ><%=prpTPrt.get(k)%></html:option> 
            <%}%>
     </html:select>  </td>
     <%}else{%>
     <td><html:text property="<%=fldNme%>" styleId="<%=lTpprp%>" name="stonesTransferForm"  style="width:100px" /></td>
     <%}%>
    <%}%>
  </tr>
       </table>
 
  <%}%>
  
  </td></tr></table></html:form>
 <%}%>
  </td>
  </tr>
  </table>
    
    
    
    </body>
</html>