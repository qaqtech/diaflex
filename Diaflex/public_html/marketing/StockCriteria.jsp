<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page errorPage="../error_page.jsp" %>
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import="java.util.ArrayList,java.util.ArrayList,java.util.HashMap, java.sql.*, java.util.Enumeration, java.util.logging.Level"%>
 
<jsp:useBean id="util" class="ft.com.JspUtil" scope="page" />
<jsp:useBean id="info" class="ft.com.InfoMgr" scope="session" />
<%@taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>

<title>Stock criteria</title>
<script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/bse.js?v=<%=info.getJsVersion()%> " > </script>
<script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/memoScript.js?v=<%=info.getJsVersion()%> " > </script>
<link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/bse.css?v=<%=info.getJsVersion()%>"/>
<link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/general.css?v=<%=info.getJsVersion()%>"/>
<script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/ajax.js?v=<%=info.getJsVersion()%> " > </script>
<script type="text/javascript" src="<%=info.getReqUrl()%>/PopScripts/popup.js?v=<%=info.getJsVersion()%> " > </script>
<script src="<%=info.getReqUrl()%>/PopScripts/jquery-1.2.6.min.js" type="text/javascript"></script>
<script language="javaScript" type="text/javascript" src="<%=info.getReqUrl()%>/calendar/calendar.js"></script>
<link href="<%=info.getReqUrl()%>/calendar/calendar.css" rel="stylesheet" type="text/css" />


<link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/style.css?v=<%=info.getJsVersion()%>"/>
</head>
        <%String logId=String.valueOf(info.getLogId());
        String onfocus="cook('"+logId+"')";
        %>
 <body onfocus="<%=onfocus%>" onload="MM_preloadImages('images/faunalogo.png')" onkeypress="return disableEnterKey(event);"  topmargin="0" leftmargin="0" rightmargin="0" bottommargin="0">
<div id="backgroundPopup"></div>

<html:form action="/marketing/stockCrt.do?method=save" >
<html:hidden property="value(oldCrtId)" name="stockCriteriaForm" />

<%
ArrayList srchPrpList=(ArrayList)session.getAttribute("stkCrtprplist");
String modify=util.nvl((String)request.getAttribute("modify"));
String load=util.nvl((String)request.getAttribute("load"));
String lprp = "";
String lprpTyp = "";
String prpDsc ="";
String srt = "";
String val="";
String prt="";
ArrayList prpPrt=null;
ArrayList prpVal=null;
ArrayList prpSrt = null;
HashMap mprp = info.getMprp();
ResultSet rs = null;
HashMap prp = info.getPrp();
String dfltVal = "0" ;
String dfltDsp = "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;All&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";


%>
<select style="display:none" name="multiPrp" id="multiPrp">
<%
for(int s=0;s<srchPrpList.size();s++){
ArrayList srchLst = (ArrayList)srchPrpList.get(s);
lprp =(String)srchLst.get(0);
String flg=(String)srchLst.get(1);

if(flg.equals("M")){
%>
<option value="<%=lprp%>"></option>
<%}}
%>
</select>
<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0" class="mainbg">
<tr>
<td height="103" colspan="4" valign="top">
<jsp:include page="/header.jsp" />

</td>
</tr>
<tr>
<td height="5" colspan="4" valign="top" class="greyline"><img src="../images/grey_linebig.png" width="685" height="5" align="right" /></td>
</tr>
<tr>
<td valign="top" class="hedPg">
<table cellpadding="1" cellspacing="5"><tr><td valign="middle">
<img src="../images/line.jpg" border="0" /></td><td valign="middle"> <span class="pgHed">

Stock Criteria

</span> </td>

</tr></table>
</td>
</tr>




<tr><td valign="top" class="hedPg"><table cellpadding="0" cellspacing="0">
<%
ArrayList prpTable=(ArrayList) session.getAttribute("prpTable");
ArrayList typeList =((ArrayList)session.getAttribute("typeList"));
ArrayList exsCrtra=(ArrayList) session.getAttribute("exsCrtra");
%>
<!-- Drop down property -->

<tr><td><table>
<tr>

<td>Type</td>
<td>Description</td>
<td>Property</td>
<td id="valueDes">Value</td>
<td>Buyer Name</td>
<td></td>
<td></td>
<td>Existing Criteria</td>
<td colspan="2"></td>
</tr>
<tr>
<!-- Existing Criteria -->


<!-- type -->
<td><html:select property="value(type)" name="stockCriteriaForm" style="width:125px" onchange="" styleId="type" >

<html:option value="<%=dfltVal%>"><%=dfltDsp%></html:option>
<%
for(int j=0; j < typeList.size(); j++) {
ArrayList type=(ArrayList)typeList.get(j);
String typkey = (String)type.get(0);
String typdes = (String)type.get(1);

String isSelected = "" ;

%>
<html:option value="<%=typkey%>" ><%=typdes%></html:option>
<%}%>
</html:select></td>

<!--Description -->
<td><html:text property="value(dmdDsc)" onchange="return chkTpeDes();" styleId="dmdDsc" size="20" /></td>
<td>
<html:select property="value(prp)" name="stockCriteriaForm" style="width:125px" onchange="dspSubProp(this);" styleId="prp" >

<html:option value="<%=dfltVal%>"><%=dfltDsp%></html:option>
<%
for(int j=0; j < prpTable.size(); j++) {
ArrayList prpvec=(ArrayList)prpTable.get(j);
String prtDes = (String)prpvec.get(0);
String pprp = (String)prpvec.get(1);

String isSelected = "" ;

%>
<html:option value="<%=pprp%>" ><%=prtDes%></html:option>
<%}%>
</html:select></td>
<%
if(!load.equals("") && (load.equals("Y")))
{
%>
<%
String property=(String)request.getAttribute("property");
String propvalue=(String)request.getAttribute("propvalue");
String prpTyp = util.nvl((String)mprp.get(property+"T"));
String styleC="display:block";
String styleN="display:block";
if(prpTyp.equals("C"))
styleN="display:none";
else
styleC="display:none";
%>
<td> <html:select property="value(subPrp)" name="stockCriteriaForm" style="<%=styleC%>" styleId="subPrp" >
<%
if(prpTyp.equals("C")){
ArrayList prpPrtVec = (ArrayList)prp.get(property+"P");

ArrayList prpValVec = (ArrayList)prp.get(property+"V");


for(int i=0;i<prpValVec.size();i++)
{
%>
<html:option value="<%=(String)prpValVec.get(i)%>" ><%=(String)prpPrtVec.get(i)%></html:option>

<%

}
}
%>
</html:select>
<html:text property="value(subPrpTN)" name="stockCriteriaForm" styleClass="txtStyle" size="15" style="<%=styleN%>"  styleId="subPrpTN"/>
</td>
<%
}
else
{
%>
<td> 
<html:select property="value(subPrp)" name="stockCriteriaForm" style="display:none" styleId="subPrp" >
</html:select>
<html:text property="value(subPrpTN)" styleClass="txtStyle" size="15" style="display:none"  styleId="subPrpTN"/>
</td>
<% }
%>

<!-- Suggestion box -->
<td nowrap="nowrap">

<%
String sbUrl = info.getReqUrl() + "/AjaxAction.do?param=";
String nme = util.nvl((String)request.getParameter("nme"));
if(nme.equals(""))
 nme = util.nvl((String)request.getAttribute("nme"));
String value ="";
if(!nme.equals(""))
value = "value="+nme+"";
%>
<input type="text" id="nme" name="nme" autocomplete=off class="sugBox" <%=value%>
onKeyUp="doCompletion('nme', 'nmePop', '../ajaxAction.do?1=1', event)" 
onKeyDown="setDown('nmePop', 'nmeID', 'nme',event)" />
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
<td valign="top">
<html:submit property="value(filterCrt)" value="Filter" styleClass="submit" /></td>
<td valign="top">
<html:submit property="value(reset)" value="Reset" styleClass="submit" /></td>
<td><html:select property="value(exiCrt)" name="stockCriteriaForm" style="width:125px" onchange="selectCrt();" styleId="exiCrt" >

<html:option value="<%=dfltVal%>"><%=dfltDsp%></html:option>
<%
for(int j=0; j < exsCrtra.size(); j++) {
ArrayList crta=(ArrayList)exsCrtra.get(j);
String crtId = (String)crta.get(0);
String crtDsc = (String)crta.get(1);

String isSelected = "" ;

%>
<html:option value="<%=crtId%>" ><%=crtDsc%></html:option>
<%}%>
</html:select></td>
<td valign="top">
<html:submit property="value(loadFav)" value="Load" styleClass="submit" onclick="return validatestkcriteria();" /></td>
<td valign="top">
<html:submit property="value(removeCrt)" value="Remove" styleClass="submit" onclick="return validatestkcriteria();"  /></td>
<!-- -->

</tr> </table></td></tr>
</table></td></tr>

<!-- Button -->
<tr><td valign="top" class="hedPg"><table cellpadding="" width="80%" class="topPrpTbl" cellspacing="3">
<tr><td><table >
<tr><td valign="top">
<html:submit property="value(add)" value="Add Criteria" styleClass="submit" onclick="return dbaseChk();" /></td>

<td valign="top">
<html:submit property="value(updateCrt)" value="Update" styleClass="submit" /></td>
<%
  ArrayList rolenmLst=(ArrayList)info.getRoleLst();
  String usrFlg=util.nvl((String)info.getUsrFlg());
  if(usrFlg.equals("SYS") || rolenmLst.contains("ADMIN")){
  %>
  <td><img src="../images/edit.jpg" title="Edit Properties Module" onclick="newWindow('<%=info.getReqUrl()%>/dynamicProperty.do?method=load&mdl=MEMO_CRT&sname=stkCrtprplist&par=STK_CRT')" border="0" width="15px" height="15px"/> </td>
  <%}%>
</tr>
</table></td></tr>
<!-- end of Button row --->
<tr><td>
<table><tr><td>
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
int srchSize = (((srchPrpList.size()+divis))/3)+1;
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
if(modify.equals("y"))
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
// String loadStrL = "";
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
//                String fld = util.nvl((String)favMap.get(lprp+"_"+pSrtl));
//                 if(fld.equals(pSrtl+"_to_"+pSrtl)){
//                   isSelected = "checked=\"checked\"";
//                   loadStrL = loadStrL+" , "+pPrtl;
//                   }
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
if(modify.equals("y"))
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
</table></td></tr>
<tr><td>
<jsp:include page="/footer.jsp" />
</td></tr>
</table>
</html:form>
</body>
</html>
