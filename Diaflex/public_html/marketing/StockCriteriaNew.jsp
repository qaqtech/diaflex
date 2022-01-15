<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page errorPage="../error_page.jsp" %>
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.ResultSet, java.util.ArrayList,java.util.Collections,java.util.ArrayList,java.util.HashMap,java.text.SimpleDateFormat, java.util.Calendar"%>
<jsp:useBean id="info" class="ft.com.InfoMgr" scope="session" />
<jsp:useBean id="util" class="ft.com.JspUtil" scope="page" />
<%@taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>
<%@taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<html> 
   <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/bse.js?v=<%=info.getJsVersion()%> " > </script>
<link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/bse.css?v=<%=info.getJsVersion()%>"/>
<link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/general.css?v=<%=info.getJsVersion()%>"/>
<script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/ajax.js?v=<%=info.getJsVersion()%> " > </script>
  <script type="text/javascript" src="<%=info.getReqUrl()%>/PopScripts/popup.js?v=<%=info.getJsVersion()%> " > </script>
    <script src="<%=info.getReqUrl()%>/PopScripts/jquery-1.2.6.min.js" type="text/javascript"></script>
   <script language="javaScript" type="text/javascript" src="<%=info.getReqUrl()%>/calendar/calendar.js"></script>
   <link href="<%=info.getReqUrl()%>/calendar/calendar.css" rel="stylesheet" type="text/css" />
   <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/style.css?v=<%=info.getJsVersion()%>"/>
   <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/memoScript.js?v=<%=info.getJsVersion()%> " > </script>
  <title>Stock Criteria</title>
  </head>
        <%
        HashMap mprp = info.getMprp();
        HashMap prp = info.getPrp();
        ArrayList crtLst = (request.getAttribute("crtLst") == null)?new ArrayList():(ArrayList)request.getAttribute("crtLst");
        HashMap crtDtl=new HashMap();
        ArrayList prpLst=(ArrayList)mprp.get("ALL");
        Collections.sort(prpLst);
        String lprp = "";
        String lprpTyp = "";
        String prpDsc ="";
        String srt = "";
        String val="";
        String prt="";
        ArrayList prpPrt=null;
        ArrayList prpVal=null;
        ArrayList prpSrt = null;
        String dfltVal = "0" ;
        String dfltDsp = "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;All&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
        ArrayList srchPrpList=(ArrayList)info.getStkCrtprplist();
        String modify=util.nvl((String)request.getAttribute("modify"));
        String logId=String.valueOf(info.getLogId());
        String onfocus="cook('"+logId+"')";
        %>
 <body onfocus="<%=onfocus%>" onload="MM_preloadImages('images/faunalogo.png')" topmargin="0" leftmargin="0" rightmargin="0" bottommargin="0">
 
  <input type="hidden" id="reqUrl" name="reqUrl" value="<%=info.getReqUrl()%>"/>
<div id="popupContactASSFNL">
<table class="popUpTb">
<tr><td height="5%">&nbsp;&nbsp;&nbsp;<html:button property="value(close)" styleClass="submit"  onclick="MKEmptyPopup('SC');disablePopupASSFNL();" value="Close"  /></td></tr>
<tr><td valign="top" height="95%">
<iframe name="SC" class="frameStyle"   align="middle" frameborder="0">
</iframe></td></tr>
</table>
</div>
<div id="backgroundPopup"></div>

 
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
  <img src="../images/line.jpg" border="0" /></td><td valign="middle"> <span class="pgHed">Stock Criteria - <%=util.nvl((String)session.getAttribute("criteriatyp"))%></span> </td>
</tr></table>
  </td>
  </tr>
  <%String msg = (String)request.getAttribute("rtnmsg");
  if(msg!=null){
  %>
  <tr><td valign="top" class="tdLayout"> <span class="redLabel" > <%=msg%></span></td></tr>
  <%}%>


<tr><td valign="top" class="hedPg">
<html:form action="/marketing/stockCrtNew.do?method=save"  method="POST">
<html:hidden property="value(typ)" styleId="typ" name="stockCriteriaForm"/>
<html:hidden property="value(crt_idn)" styleId="crt_idn" name="stockCriteriaForm"/>
 <table><tr><td>
  <table  class="grid1">
   <tr>
   <td align="center">Description</td>
   <td align="center">Property</td>
   <td align="center">Value</td>
   <td align="center">Buyer</td>
   <td></td>
   </tr>
   <tr>
    <td><html:text property="value(dsc)" styleId="dsc" size="20" /></td>
    <td>
    <html:select property="value(mprp)" name="stockCriteriaForm" style="width:125px" onchange="dspSubProp(this);" styleId="prp" >
    <html:option value="<%=dfltVal%>"><%=dfltDsp%></html:option>
    <%
    for(int j=0; j < prpLst.size(); j++) {
    String prop=(String)prpLst.get(j);
    String dta_typ = util.nvl((String)mprp.get(prop+"T"));
    if(dta_typ.equals("C") || dta_typ.equals("N")){
    String propdsc = util.nvl((String)mprp.get(prop+"D"));  
    if(propdsc.equals(""))
    propdsc=prop;
    %>
    <html:option value="<%=prop%>" ><%=propdsc%></html:option>
    <%}}%>
    </html:select>
    </td>
    <%
    if(crtLst.size()>0){
    crtDtl=new HashMap();
    crtDtl=(HashMap)crtLst.get(0);
    String property=(String)crtDtl.get("MPRP");
    String prpTyp = util.nvl((String)mprp.get(property+"T"));
    String styleC="display:block";
    String styleN="display:block";
    if(prpTyp.equals("C"))
    styleN="display:none";
    else
    styleC="display:none";
    %>
    <td> 
    <html:select property="value(subPrp)" name="stockCriteriaForm" style="<%=styleC%>" styleId="subPrp" >
    <%
    if(prpTyp.equals("C")){
    ArrayList prpPrtVec = (ArrayList)prp.get(property+"P");
    ArrayList prpValVec = (ArrayList)prp.get(property+"V");    
    for(int i=0;i<prpValVec.size();i++){%>
    <html:option value="<%=(String)prpValVec.get(i)%>" ><%=(String)prpValVec.get(i)%></html:option>
    <%}
    }%>
    </html:select>
    <html:text property="value(subPrpTN)" name="stockCriteriaForm" styleClass="txtStyle" size="15" style="<%=styleN%>"  styleId="subPrpTN"/>
    </td>
    <%}else{%>
    <td>
    <html:select property="value(subPrp)" name="stockCriteriaForm" style="display:none" styleId="subPrp" >
    </html:select>
    <html:text property="value(subPrpTN)" styleClass="txtStyle" size="15" style="display:none"  styleId="subPrpTN"/>    
    </td>
    <%}%>
    <td nowrap="nowrap">
    <%
    String sbUrl = info.getReqUrl() + "/AjaxAction.do?param=";
    %>
    <html:text property="value(byr)" name="stockCriteriaForm" styleId="nme" onclick="document.getElementById('partytext').autocomplete='off'" styleClass="sugBox" onkeypress="return disableEnterKey(event);"
    onkeyup="doCompletion('nme', 'nmePop', '../ajaxAction.do?1=1', event)" 
    onkeydown="setDown('nmePop', 'nmeID', 'nme',event)" />
    <img src="../images/addrow.jpg" width="17" height="17" title="Click To get Names" onclick="doCompletionMobile('nme', 'nmePop', '../ajaxAction.do?1=1')">
    <html:hidden property="nmeID" styleId="nmeID"/>
    <div style="position: absolute;">
    <select id="nmePop" name="nmePop"
    style="display:none;300px;"
    class="sugBoxDiv"
    onKeyDown="setDown('nmePop', 'nmeID', 'nme',event)"
    onDblClick="setVal('nmePop', 'nmeID', 'nme', event);hideObj('nmePop')"
    onClick="setVal('nmePop', 'nmeID', 'nme', event)"
    size="10">
    </select>
    </div>
    </td>
    <td>
     <logic:notEqual property="value(add)"  name="stockCriteriaForm"  value="N" >
     <html:submit property="value(addnew)" value="Add" onclick="" styleClass="submit"/>
     </logic:notEqual>
     <logic:notEqual property="value(edit)"  name="stockCriteriaForm"  value="N" >
     <html:submit property="value(update)" value="Save" onclick="" styleClass="submit"/>
     </logic:notEqual>
     <html:button property="value(reload)" value="Reload" styleClass="submit" onclick="goTo('stockCrtNew.do?method=load','','')"/>
    </td>
   </tr>
   </table>
   </td></tr>
   <tr><td>
<table><tr><td valign="top">
<table border="0" cellspacing="0" cellpadding="3" width="370" class="srch">
<thead>
<tr><th>PROPERTY</th><th>FROM</th><th>TO</th></tr>
</thead>

<%
String flg="";
String fld1 = "";
String fld2 = "";
String prpFld1="";
String prpFld2="";
String val1 = "";
String val2= "";
String onChg1 = "";
String onChg2= "";
int prpCount=0;
if(srchPrpList!=null){
int divis= ((srchPrpList.size())%3);
int srchSize = (((srchPrpList.size()+divis))/3);
for(int s=0;s<srchPrpList.size();s++){
ArrayList srchLst = (ArrayList)srchPrpList.get(s);
lprp =(String)srchLst.get(0);
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
if(prpCount==srchSize){
prpCount=0;
%></table></td><td valign="top">
<table border="0" cellspacing="0" cellpadding="3" width="370" class="srch">
<thead>
<tr><th>PROPERTY</th><th>FROM</th><th>TO</th></tr>
</thead>
<% }
prpCount++;
%>
<tr><td height="35px"><span class="txtBold"><%=prpDsc%></span></td>
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

<div class="multiplePrpdiv" id="<%=lprp%>" style="display:none;">
<table cellpadding="0" width="230px" class="multipleBg" cellspacing="0">
<tr><td>Carat</td>
<td ><html:text property="value(wt_fr_c)" size="6" styleId="wt_fr_c" onchange="<%=txtBoxOnChg1%>" styleClass="txtCWTWt" name="stockCriteriaForm" /></td>
<td ><html:text property="value(wt_to_c)" size="6" styleId="wt_to_c" onchange="<%=txtBoxOnChg2%>" styleClass="txtCWTWt" name="stockCriteriaForm"/></td>
</tr>
<%
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

%>
<tr><td><html:checkbox property="<%=chkFldId%>" name="stockCriteriaForm" styleId="<%=checkId%>" value="<%=valWt%>" onclick="<%=onclick%>" ></html:checkbox> &nbsp;<%=valWt%></td>
<td><html:text property="<%=cwtFr%>" size="6" styleId="<%=cwtFrTxtId%>" styleClass="txtCWTWt" /></td>
<td ><html:text property="<%=cwtTo%>" size="6" styleId="<%=cwtToTxtId%>" styleClass="txtCWTWt" /></td>
</tr>
<%}

%>

</table>
</div> <!-- div tag for crtwt -->
<table><tr>
<td>
<%
if(modify.equals("Y"))
{
%>
<html:text property="<%=mutiText%>" name="stockCriteriaForm" size="30" styleId="<%=mutiTextId%>" styleClass="txtStyle" />
<% }
else
{
%>
<html:text property="<%=mutiText%>" value="<%=loadStr%>" name="stockCriteriaForm" size="30" styleId="<%=mutiTextId%>" styleClass="txtStyle" />
<%
}
%>
</td>
<td><img src="../images/plus.jpg" id="IMGD_<%=lprp%>" class="img" onclick="DisplayDiv('<%=lprp%>')" style="display:block" border="0"/></td><td><img src="../images/minus.jpg" id="IMGU_<%=lprp%>" class="img" onclick="Hide('<%=lprp%>')" style="display:none" border="0"/></td>


</tr></table>

</td>
<%
} // lpprp crtwt
else
{
%>
<td colspan="2" align="center">

<% if(prpSrt != null) {
String loadStrL = "ALL";
%>
<div  class="multiplePrpdiv" id="<%=lprp%>" align="center" style="display:none; padding-top:2px; margin-top:20px; z-index:1">
             <table cellpadding="0"  class="multipleBg" cellspacing="0">
             <%if(!lprp.equals("LAB")){%>
             <tr><td>
             <table>
             <tr>
             <td> <input type="checkbox" name="selectALL" id="SALL_<%=lprp%>" onclick="CheckAlllprp('<%=lprp%>',this)" > Select All &nbsp; </td>
             <td> From: &nbsp; 
             
           <html:select  property="<%=prpFld1%>" style="width:75px" name="stockCriteriaForm" onchange="<%=onChg1%>" styleId="<%=fld1%>"  > 
     
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
             
           <html:select property="<%=prpFld2%>" style="width:75px" name="stockCriteriaForm" onchange="<%=onChg2%>" styleId="<%=fld2%>"  > 
     
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
            <%}%>
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
             %>
             <td align="center"><html:checkbox  property="<%=chFldNme%>" name="stockCriteriaForm" styleId="<%=checId%>"  onclick="<%=onclick%>" value="<%=vall%>"/></td>
             <!--<td align="left"><span style="margin-left:10px;"><%=pPrtl%></span></td>-->
             <td align="left"><span><%=pPrtl%></span></td>
             <%}%>
             </tr></table>
            </td>
            </tr>
            </table>
             <input type="hidden" id="HID_<%=lprp%>" value="<%=loadStrL%>" />
              
             </div>
<table><tr><td>
<%
if(modify.equals("Y"))
{
%>
<html:text property="<%=mutiText%>" name="stockCriteriaForm" size="30" styleId="<%=mutiTextId%>" styleClass="txtStyle" />
<% }
else
{
%>
<html:text property="<%=mutiText%>" value="<%=loadStrL%>" name="stockCriteriaForm" size="30" styleId="<%=mutiTextId%>" styleClass="txtStyle" />
<%
}
%>
</td>
<td><img src="../images/plus.jpg" id="IMGD_<%=lprp%>" class="img" onclick="DisplayDiv('<%=lprp%>')" style="display:block" border="0"/></td><td><img src="../images/minus.jpg" id="IMGU_<%=lprp%>" class="img" onclick="Hide('<%=lprp%>')" style="display:none" border="0"/></td>

</tr></table>

<% }
%>
</td>
<%
}
}// flg M
else
{ // flg S


if(prpSrt != null) {
%>
<td align="center">
<html:select property="<%=prpFld1%>" name="stockCriteriaForm" style="width:100px" onchange="<%=onChg1%>" styleId="<%=fld1%>" >

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

<html:select property="<%=prpFld2%>" name="stockCriteriaForm" style="width:100px" onchange="<%=onChg2%>" styleId="<%=fld2%>" >

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

<%}// prpsort not null in flg S
else{%>
<%if(lprpTyp.equals("D")){%>
<td bgcolor="#FFFFFF" align="center">
<html:text property="<%=prpFld1%>" name="stockCriteriaForm" styleId="<%=fld1%>" onkeypress="focout();" value="<%=val1%>" size="9" maxlength="25" /> <a href="#" onClick="setYears(1900, '<%=info.getCurrentYear()%>' );showCalender(this, '<%=prpFld1%>');">
<img src="<%=info.getReqUrl()%>/images/calender.png"></a></td>
<td bgcolor="#FFFFFF" align="center"><html:text property="<%=prpFld2%>" styleId="<%=fld2%>" name="stockCriteriaForm" value="<%=val2%>" size="9" maxlength="25" /> <a href="#" onClick="setYears(1900, '<%=info.getCurrentYear()%>' );showCalender(this, '<%=prpFld2%>');">
<img src="<%=info.getReqUrl()%>/images/calender.png"></a></td>

<%} else if(lprpTyp.equals("T")){%>
<td bgcolor="#FFFFFF" align="center" colspan="2">
<html:text property="<%=prpFld1%>" name="stockCriteriaForm" styleId="<%=fld1%>" value="<%=val1%>" size="30" maxlength="20" />
</td>

<%} else{%>
<td bgcolor="#FFFFFF" align="center"><html:text property="<%=prpFld1%>" name="stockCriteriaForm" styleId="<%=fld1%>" size="11" maxlength="25" onchange="isNumericDecimal(this.id)" /></td>
<td bgcolor="#FFFFFF" align="center"><html:text property="<%=prpFld2%>" name="stockCriteriaForm" size="11" maxlength="25" onchange="isNumericDecimal(this.id)" /></td>
<%}}

} //(not M)

}// end of srchloop

}
%>
</tr>
</table></td></tr></table>
</td></tr>
   
   </table>
   
</html:form>
</td>
</tr>

<%if(crtLst.size()>0){%>
<%int sr=1;%>
<tr>
<td class="hedPg">
<table class="grid1"> 
<tr><td colspan="9">Existing Criteria</td></tr>
<tr>
<th>Sr No</th>
<th>Criteria Idn</th>
<th>Type</th>
<th>Dsc</th>
<th>Property</th>
<th>Value</th>
<th>Buyer</th>
<th></th>
<th>Status</th>
<th>Pkts</th>
<th>Detail Dsc</th>
</tr>
<%for(int i=0;i<crtLst.size();i++){
    crtDtl=new HashMap();
    crtDtl=(HashMap)crtLst.get(i);
    String crt_idn=util.nvl((String)crtDtl.get("CRT_IDN"));
    String typ=util.nvl((String)crtDtl.get("TYP"));
    String prop=util.nvl((String)crtDtl.get("MPRP"));
    String propval=util.nvl((String)crtDtl.get("VAL"));
    String dta_typ = util.nvl((String)mprp.get(prop+"T"));
    if(dta_typ.equals("N"))
    propval=util.nvl((String)crtDtl.get("NUM"));
%>
<tr>
<td><%=sr++%></td>
<td><%=crt_idn%></td>
<td><%=typ%></td>
<td nowrap="nowrap"><%=util.nvl((String)crtDtl.get("DSC"))%></td>
<td nowrap="nowrap"><%=util.nvl((String)crtDtl.get("MPRP"))%></td>
<td nowrap="nowrap"><%=propval%></td>
<td><%=util.nvl((String)crtDtl.get("BYR"))%></td>
<td nowrap="nowrap" align="center">
<%
        String edtLnk = "<a href=\""+ info.getReqUrl() + "/marketing/stockCrtNew.do?method=edit&typ="+typ+"&crt_idn="+crt_idn+"\" >Edit</a>";
//        String delLnk = "<a href=\""+ info.getReqUrl() + "/marketing/stockCrtNew.do?method=removeCrt&typ="+typ+"&crt_idn="+crt_idn+"\" >Delete</a>";
        String url = info.getReqUrl();
        String link= url+"/marketing/stockCrtNew.do?method=removeCrt&typ="+typ+"&crt_idn="+crt_idn;
        String onclick = "return setDeleteConfirm('"+link+"')";
%>
<%=edtLnk%>&nbsp;|
<html:link page="<%=link%>" onclick="<%=onclick%>">Delete</html:link>
</td>
<td nowrap="nowrap">
<%
String fld_nmeA="value(STT_"+crt_idn+")";
String onclk="saveStockCriteria(this,'"+crt_idn+"');";
%>
   &nbsp;&nbsp;<html:radio property="<%=fld_nmeA%>" name="stockCriteriaForm" onclick="<%=onclk%>"   value="A" />&nbsp;A&nbsp;
   &nbsp;&nbsp;<html:radio property="<%=fld_nmeA%>" name="stockCriteriaForm" onclick="<%=onclk%>"   value="IA" />&nbsp;IA
</td>
<td><a href="<%=url%>/marketing/stockCrtNew.do?method=getPackets&crt_idn=<%=crt_idn%>" target="_blank">Pkts</a></td>
<td nowrap="nowrap"><%=util.nvl((String)crtDtl.get("DTL_DSC"))%></td>
</tr>
<%}%>
</table>
</td>
</tr>
<%}%>

  <tr><td>
<jsp:include page="/footer.jsp" />
</td></tr>
  </table>  
       <%@include file="../calendar.jsp"%>
  </body>
</html>