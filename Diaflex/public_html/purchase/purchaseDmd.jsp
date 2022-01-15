<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page errorPage="../error_page.jsp" %>
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import="java.util.ArrayList,java.util.ArrayList,java.util.HashMap, java.sql.*, java.util.Enumeration, java.util.logging.Level"%>
<jsp:useBean id="info" class="ft.com.InfoMgr" scope="session" />
<jsp:useBean id="util" class="ft.com.JspUtil" scope="page" />
<%@taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<html>
  <head>
 <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
  
  <title>Search Form</title>
  <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/bse.js?v=<%=info.getJsVersion()%> " > </script>
<link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/bse.css?v=<%=info.getJsVersion()%>"/>
<link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/general.css?v=<%=info.getJsVersion()%>"/>
<script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/ajax.js?v=<%=info.getJsVersion()%> " > </script>
  <script type="text/javascript" src="<%=info.getReqUrl()%>/PopScripts/popup.js?v=<%=info.getJsVersion()%> " > </script>
    <script src="<%=info.getReqUrl()%>/PopScripts/jquery-1.2.6.min.js" type="text/javascript"></script>
   <script language="javaScript" type="text/javascript" src="<%=info.getReqUrl()%>/calendar/calendar.js"></script>
   <link href="<%=info.getReqUrl()%>/calendar/calendar.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/memoScript.js?v=<%=info.getJsVersion()%> " > </script>
 
      <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/style.css?v=<%=info.getJsVersion()%>"/>
  </head>
  
<%
String onload = "";
String excessPkt = util.nvl((String)request.getAttribute("excessPkt"));
HashMap dbinfo = info.getDmbsInfoLst();
        String logId=String.valueOf(info.getLogId());
        String onfocus="cook('"+logId+"')";
%>
    
<body onfocus="<%=onfocus%>" onload="MM_preloadImages('images/faunalogo.png')" topmargin="0" leftmargin="0" rightmargin="0" bottommargin="0">
<div id="backgroundPopup"></div>
  <%
  String lprp = "";
  String lprpTyp = "";
  String prpDsc ="";           
  String srt = "";
  String val="";
  String prt="";
  ArrayList prpPrt=null;
  ArrayList prpVal=null;
  ArrayList prpSrt = null;
  ArrayList ary = new ArrayList();
  String sz = (String)dbinfo.get("SIZE");
    HashMap prp = info.getSrchPrp();
      ArrayList prpDspBlocked = info.getPageblockList();
    int prpCount=0;
    String flg = "";
      String method = util.nvl(request.getParameter("method"));
    String isModify="N";
 if(util.nvl(request.getParameter("modify")).length() > 0 || request.getParameter("srchId")!=null)
   isModify = "Y";
   if(method.equals("loadDmd"))
    isModify = "Y";
     String Modify = (String)request.getAttribute("Modify");
    if(Modify!=null)
    isModify = "Y";
     String fld1 = "";
            String fld2 = "";
            String prpFld1="";
            String prpFld2="";
             String val1 = "";
             String val2= "";
            String dfltVal = "0" ;
             String onChg1 = "";
             String onChg2= "";
            String dfltDsp = "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;All&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
  ArrayList srchPrp = (session.getAttribute("purDmdSrch") == null)?new ArrayList():(ArrayList)session.getAttribute("purDmdSrch");
  HashMap mprp = info.getSrchMprp();
  %>
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
  <img src="../images/line.jpg" border="0" /></td><td valign="middle"> <span class="pgHed">
   Purchase Demand
   </span> </td>
</tr></table>
  </td>
  </tr>
      <%
  String dmdMsg =util.nvl((String)request.getAttribute("dmdMsg"));
  if(!dmdMsg.equals("")){%>
    <tr><td valign="top" class="hedPg">
     <span class="redLabel" ><%=dmdMsg%></span>
    </td></tr>
  <%}%>
    
    <tr><td>
    <html:form action="purchase/purchaseDmdAction.do?method=loadDmd" method="post" >
<input type="hidden" id="reqUrl" name="reqUrl" value="<%=info.getReqUrl()%>"/>
<input type="hidden" id="cut" value="<%=dbinfo.get("CUT")%>" />
<input type="hidden" id="sym" value="<%=dbinfo.get("SYM")%>" />
<input type="hidden" id="pol" value="<%=dbinfo.get("POL")%>" />
    <div id="popupContactDmd" align="center" >
<html:hidden property="value(oldDmdId)" name="purchaseDmdForm" />
<table align="center" cellpadding="10" cellspacing="10" class="pktTble1">
<tr><td>Demand Description </td><td><html:text property="value(dmdDsc)" size="30" onchange="checkDmdDsc()" styleId="dmdNme" name="purchaseDmdForm" /> </td> </tr>
<tr><td colspan="3" align="center"><table><tr>
<logic:equal property="value(oldDmdId)"  name="purchaseDmdForm"  value="" >
<td><html:submit property="value(pb_dmd)" value="Save" onclick="return valDmddsc();"  styleClass="submit" /> </td>
</logic:equal>
<td><html:submit property="value(upd_dmd)" value="Update" onclick="return valDmddsc();"  styleClass="submit" /> </td>
<td><button type="button" onclick="disablePopupDmd()" Class="submit" >Back</button> </td>
</tr></table></td>
</tr>
</table>
</div>
    <table cellpadding="3" cellspacing="3" class="hedPg">  
            <tr>
            <td><table><tr>
            <td nowrap="nowrap"><span class="txtBold">Employee : </span> </td>
            <td nowrap="nowrap">
            <html:select property="empId" styleId="empId" onchange="getPurDmd(this)"  name="purchaseDmdForm">
            <html:option value="0" >--select--</html:option>
            <html:optionsCollection property="empList" name="purchaseDmdForm" 
            label="byrNme" value="byrIdn" />
            </html:select>
            </td>
            <td>
            Demand : 
            </td>
            <td>
           <html:select property="dmdId" name="purchaseDmdForm" styleId="dmdId" style="width:200px">
           <html:option value="0" >---select---</html:option>
            <html:optionsCollection property="dmdList" name="purchaseDmdForm" 
            label="trmDtl" value="relId" />
            </html:select>
            </td>
            <td><html:submit property="value(load)" value="Load" styleClass="submit" /> </td>
            <td><html:submit property="value(remove)" value="Remove" styleClass="submit" /> </td>
             <!--<td>
             <html:checkbox property="value(is2Ex)" value="is2Ex"   styleId="is2Ex" name="purchaseDmdForm" onclick="setEX('is2Ex')" />2 EX</td>
         <td> <html:checkbox property="value(is3Ex)" value="is3Ex"   styleId="is3Ex" name="purchaseDmdForm" onclick="setEX('is3Ex')" />3 EX</td>
           <td> <html:checkbox property="value(is3VG)" value="is3VG"   styleId="is3VG" name="purchaseDmdForm" onclick="setEX('is3VG')" />3 VG</td>-->
            
            </tr></table></td>
            </tr>
             <% if(srchPrp!=null && srchPrp.size()>0){%>
            <tr>
            <td valign="top">
            <table>
            <tr><td valign="top">
            <table border="0" cellspacing="0" cellpadding="3" width="330" class="srch">
            <thead>
           <tr><th>PROPERTY</th><th>FROM</th><th>TO</th></tr>
           </thead>
            <%
           
            if(srchPrp!=null && srchPrp.size()>0){           
            int divis= (((srchPrp.size()))%3);
            int basicPrpSz=srchPrp.size();
            int srchSize = (((srchPrp.size()+divis))/3);
            for(int i=0 ;i<srchPrp.size();i++){
            
            ArrayList srchLst = (ArrayList)srchPrp.get(i);
             lprp = (String)srchLst.get(0);
             flg=(String)srchLst.get(1);
             lprpTyp = util.nvl((String)mprp.get(lprp+"T"));             
             prpDsc = (String)mprp.get(lprp+"D");
             if(prpDsc==null)
                prpDsc =lprp;
             prpPrt = (ArrayList)prp.get(lprp+"P");
             prpSrt = (ArrayList)prp.get(lprp+"S");
             prpVal = (ArrayList)prp.get(lprp+"V");
               fld1 = lprp+"_1";
              fld2 = lprp+"_2";

              prpFld1 = "value(" + fld1 + ")" ;
              prpFld2 = "value(" + fld2 + ")" ; 
             
              onChg1 = "setFrTo('"+ fld1 + "','" + fld2 + "')";
              onChg2 = "setFrTo('"+ fld1 + "','" + fld2 + "')";
//               val1 = util.nvl((String)favMap.get(fld1));
//                val2 = util.nvl((String)favMap.get(fld2));
             if(prpCount==srchSize){
             prpCount=0;
             %></table></td><td valign="top">
              <table border="0" cellspacing="0" cellpadding="3" width="330" class="srch">
            <thead>
           <tr><th>PROPERTY</th><th>FROM</th><th>TO</th></tr>
           </thead>
           <%  }
             String blockprp="";
             if(prpDspBlocked.contains(lprp)){
             blockprp="style=\"display:none\"";
             }else{
             prpCount++;
             }
            %>
            <tr <%=blockprp%>><td ><span class="txtBold"> <%=prpDsc%> </span></td>
              <% if(flg.equals("M")){
              onChg1 = "setFrTo('"+ fld1 + "','" + fld2 + "');setFrToCheck('"+ fld1 + "','" + fld2 + "','"+lprp+"')\"";
              onChg2 = "setFrTo('"+ fld1 + "','" + fld2 + "');setFrToCheck('"+ fld1 + "','" + fld2 + "','"+lprp+"')\"";
             String mutiTextId = "MTXT_"+lprp;
             String mutiText = "value("+ mutiTextId +")";
             if(lprp.equals("CRTWT")){
              String hidCwt="MTXT_"+lprp;
              String loadStr="ALL";
              String txtBoxOnChg1="writeText(this,'"+hidCwt+"','1');";
               String txtBoxOnChg2="writeText(this,'"+hidCwt+"','2');";
            %>
             <td colspan="2" align="center">
           
             <div class="multiplePrpdiv" id="<%=lprp%>" align="center" style="display:none; padding-top:2px; margin-top:20px;">
             <table cellpadding="0"  class="multipleBg" cellspacing="0">
            <tr><td><table>
             <tr><td>Carat</td> 
            <td><html:text property="value(wt_fr_c)"  size="6" styleId="wt_fr_c" onchange="<%=txtBoxOnChg1%>"  styleClass="txtStyle" name="purchaseDmdForm"  /></td>
            <td><html:text property="value(wt_to_c)"  size="6" styleId="wt_to_c" onchange="<%=txtBoxOnChg2%>" styleClass="txtStyle" name="purchaseDmdForm"/></td>
            <td><img src="../images/cross.png" border="0" onclick="Hide('<%=lprp%>')" /> </td>
            </tr></table></td></tr>
            <tr><td >
            <table ><tr>
             <%
             int cnt=0;
             ArrayList crtwtPrp = (info.getCrtwtPrpLst() == null)?new ArrayList():(ArrayList)info.getCrtwtPrpLst();
            for(int crt=0;crt<crtwtPrp.size();crt++) {
            ArrayList crtPrp=(ArrayList)crtwtPrp.get(crt);
            String srtWt = util.nvl((String)crtPrp.get(0));
            String valWt = util.nvl((String)crtPrp.get(1));
            String wt_fr = util.nvl((String)crtPrp.get(2));
            String wt_to = util.nvl((String)crtPrp.get(3));
             String val_fr = "" ;
            String val_to = "" ;
            String isChecked = "" ;
            String szPrp = "CRTWT";
            String cwtFr ="value("+ szPrp + "_1_"+srtWt+")";
            String cwtTo ="value("+ szPrp + "_2_"+srtWt+")";
            String checkId = szPrp + "_"+srtWt;
            String cwtToTxtId = szPrp + "_2_"+srtWt;
            String cwtFrTxtId = szPrp + "_1_"+srtWt;
            String chkFldId = "value("+ checkId + ")";
            String onclick = "setCrtWtSrch("+srtWt+","+wt_fr+", "+wt_to+",this, MTXT_"+lprp+" )";
           
//             if(favMap.get("CRTWT_"+valWt) != null){
//              String vwVal = (String)favMap.get("CRTWT_"+valWt);
//              val_fr = vwVal.substring(0, vwVal.indexOf("_to_"));
//               val_to = vwVal.substring(vwVal.indexOf("_to_")+4);
//              isChecked = "checked";  
//              if(loadStr.equals("")){
//              loadStr = valWt;
//              }else{
//               loadStr = loadStr+" , "+valWt;
//              }
//             }
        if(cnt==3){%>
        </tr><tr>
        <% cnt=0;}
        cnt++;
        %>
        <td><html:checkbox property="<%=chkFldId%>"  name="purchaseDmdForm" styleId="<%=checkId%>" value="<%=valWt%>"  onclick="<%=onclick%>" ></html:checkbox> &nbsp;<%=valWt%></td>
        <td><html:text property="<%=cwtFr%>" size="6" styleId="<%=cwtFrTxtId%>" styleClass="txtStyle"  /></td>
        <td ><html:text property="<%=cwtTo%>" size="6" styleId="<%=cwtToTxtId%>" styleClass="txtStyle"   /></td>
        <%}%>
        </tr></table>
        </td></tr>
             
             </table>
             </div>
     <table cellpadding="0" cellspacing="0"><tr><td>
     <%if(isModify.equals("Y")){%>
    <html:text property="<%=mutiText%>"  name="purchaseDmdForm"  size="32" styleId="<%=mutiTextId%>" styleClass="txtStyle" />
   <%}else{%>
    <html:text property="<%=mutiText%>" value="<%=loadStr%>" name="purchaseDmdForm"  size="32" styleId="<%=mutiTextId%>" styleClass="txtStyle" />
   <%}%>
     </td><td>&nbsp;&nbsp;</td>
     <td><img src="../images/plus.jpg" id="IMGD_<%=lprp%>" class="img" onclick="DisplayDiv('<%=lprp%>')" style="display:block" border="0"/></td><td><img src="../images/minus.jpg" id="IMGU_<%=lprp%>" class="img" onclick="Hide('<%=lprp%>')" style="display:none" border="0"/></td>
     
     </tr></table>

             </td>
             <%
             
             
             
             
             }else{
             %>
             <td colspan="2" align="center">
            
             <% if(prpSrt != null) {
               String loadStrL = "ALL";
             %>
             <div  class="multiplePrpdiv" id="<%=lprp%>" align="center" style="display:none; padding-top:2px; margin-top:20px;">
             <table cellpadding="0"  class="multipleBg" cellspacing="0">
             <tr><td>
             <table>
             <tr>
             <td> <input type="checkbox" name="selectALL" id="SALL_<%=lprp%>" onclick="CheckAlllprp('<%=lprp%>',this)" > Select All &nbsp; </td>
                 <!--<div class="multiplePrpdiv" id="<%=lprp%>" align="center" style="display:none; overflow:auto; margin-left:10px; margin-top:20px; height:200px; width:240px; padding:0px;">
             <table cellpadding="3" width="220px" class="multipleBg" cellspacing="3">-->
             
             <td> From: &nbsp; 
             
           <html:select  property="<%=prpFld1%>" name="purchaseDmdForm"  style="width:75px" onchange="<%=onChg1%>" styleId="<%=fld1%>"  > 
     
            <html:option value="<%=dfltVal%>"><%=dfltDsp%></html:option>
            <%
            for(int j=0; j < prpSrt.size(); j++) {
                String pPrt = (String)prpPrt.get(j);
                String pSrt = (String)prpSrt.get(j);
               
                  String isSelected = "" ;
               if(pSrt.equals(val1))
                   isSelected = "selected=\"selected\"";
            %>
              <html:option value="<%=pSrt%>" ><%=pPrt%></html:option> 
            <%}%>
              </html:select>
              </td><td>
            To:&nbsp;
             
           <html:select property="<%=prpFld2%>" name="purchaseDmdForm"  style="width:75px" onchange="<%=onChg2%>" styleId="<%=fld2%>"  > 
     
            <html:option value="<%=dfltVal%>"><%=dfltDsp%></html:option>
            <%
            for(int j=0; j < prpSrt.size(); j++) {
                String pPrt = (String)prpPrt.get(j);
                String pSrt = (String)prpSrt.get(j);
               
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
                String pPrtl = (String)prpPrt.get(m);
                String pSrtl = (String)prpSrt.get(m);
                String vall = (String)prpVal.get(m);
                String chFldNme = "value("+lprp+"_"+vall+")" ;
                String onclick= "checkPrp(this, 'MTXT_"+lprp+"','"+lprp+"')";
                String checId = lprp+"_"+pSrtl;
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
             <td align="center"><html:checkbox  property="<%=chFldNme%>"  name="purchaseDmdForm" styleId="<%=checId%>"  onclick="<%=onclick%>" value="<%=vall%>"/></td>
             <td align="left"><span style="margin-left:10px;"><%=pPrtl%></span></td>
             <%}%>
             </tr></table>
            </td>
            </tr>
            </table>
             <input type="hidden" id="HID_<%=lprp%>" value="<%=loadStrL%>" />
              
             </div>
              <table cellpadding="0" cellspacing="0">
              <tr><td>  
            <%if(isModify.equals("Y")){%>
              <html:text property="<%=mutiText%>"   name="purchaseDmdForm"  size="32" styleId="<%=mutiTextId%>"  styleClass="txtStyle" />
             <%}else{%>
            <html:text property="<%=mutiText%>"  value="<%=loadStrL%>" name="purchaseDmdForm"  size="32" styleId="<%=mutiTextId%>"  styleClass="txtStyle" />

             <%}%>
             </td><td>&nbsp;&nbsp;</td>
              <td><img src="../images/plus.jpg" id="IMGD_<%=lprp%>" class="img" onclick="DisplayDiv('<%=lprp%>')" style="display:block" border="0"/></td><td><img src="../images/minus.jpg" id="IMGU_<%=lprp%>" class="img" onclick="Hide('<%=lprp%>')" style="display:none" border="0"/></td>
             
              </tr></table>
             <%}%>
             </td>
             <%}%>
           
             
             
             
           <%  }else{
             
              if(prpSrt != null) {
            %>
            <td align="center">
            <html:select property="<%=prpFld1%>" name="purchaseDmdForm"  style="width:100px" onchange="<%=onChg1%>" styleId="<%=fld1%>"  > 
     
            <html:option value="<%=dfltVal%>"><%=dfltDsp%></html:option>
            <%
            for(int j=0; j < prpSrt.size(); j++) {
                String pPrt = (String)prpPrt.get(j);
                String pSrt = (String)prpSrt.get(j);
               
                  String isSelected = "" ;
               if(pSrt.equals(val1))
                   isSelected = "selected=\"selected\"";
            %>
              <html:option value="<%=pSrt%>" ><%=pPrt%></html:option> 
            <%}%>
              </html:select>
            
            </td>
            <td align="center">
           
               <html:select property="<%=prpFld2%>" name="purchaseDmdForm"  style="width:100px" onchange="<%=onChg2%>" styleId="<%=fld2%>"  > 
     
            <html:option value="<%=dfltVal%>"><%=dfltDsp%></html:option>
            <%
            for(int j=0; j < prpSrt.size(); j++) {
                String pPrt = (String)prpPrt.get(j);
                String pSrt = (String)prpSrt.get(j);
               
                  String isSelected = "" ;
               if(pSrt.equals(val1))
                   isSelected = "selected=\"selected\"";
            %>
              <html:option value="<%=pSrt%>" ><%=pPrt%></html:option> 
            <%}%>
              </html:select>
            </td>
            
        <%}else{%>
        <%if(lprpTyp.equals("D")){%>
        <td bgcolor="#FFFFFF" align="center">
        <html:text property="<%=prpFld1%>"  styleClass="txtStyle"  name="purchaseDmdForm" styleId="<%=fld1%>"   size="12" maxlength="25" /> <a href="#" id="cid" onClick="setYears(1900, '<%=info.getCurrentYear()%>' );showCalender(this, '<%=prpFld1%>');">
                <img src="<%=info.getReqUrl()%>/images/calender.png"></a></td>
        <td bgcolor="#FFFFFF" align="center"><html:text property="<%=prpFld2%>"  styleClass="txtStyle"  name="purchaseDmdForm"   size="12" maxlength="25" /> <a href="#"  onClick="setYears(1900, '<%=info.getCurrentYear()%>' );showCalender(this, '<%=prpFld2%>');">
                <img src="<%=info.getReqUrl()%>/images/calender.png"></a></td>
        
        <%} else if(lprpTyp.equals("T")){%>
        <td bgcolor="#FFFFFF" align="center" colspan="2">
        <html:text property="<%=prpFld1%>"  styleClass="txtStyle"  name="purchaseDmdForm" styleId="<%=fld1%>"  size="30" maxlength="20" /> 
        </td>
       
        <%} else{%>
           <td bgcolor="#FFFFFF" align="center"><html:text property="<%=prpFld1%>"  styleClass="txtStyle"  name="purchaseDmdForm" styleId="<%=fld1%>"  size="14" maxlength="25" onchange="isNumericDecimal(this.id)" /></td>
        <td bgcolor="#FFFFFF" align="center"><html:text property="<%=prpFld2%>"   styleClass="txtStyle" name="purchaseDmdForm"   size="14" maxlength="25" onchange="isNumericDecimal(this.id)" /></td>
        <%}}
            
             }%>
            
            
            
           </tr> 
            <%}}%>
             
            
            </table>
                                </td></tr>
        
              
                </table>              
                                                </td></tr>
                <%}%>
          
    <tr><td valign="top"><html:button property="value(pbDmd)" value="Save/Update Demand" onclick="loadDmd()" styleClass="submit" /> </td></tr>   
    </table></html:form></td></tr>           
<tr>
<td class="hedPg">
<jsp:include page="/footer.jsp" />
</td>
</tr>
</table>
 <%@include file="../calendar.jsp"%>         
          </body>
</html>