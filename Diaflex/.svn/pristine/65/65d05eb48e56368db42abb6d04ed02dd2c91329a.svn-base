<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page errorPage="../error_page.jsp" %>
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import="java.util.ArrayList,java.util.HashMap,java.sql.ResultSet,ft.com.*, java.util.Date, java.util.ArrayList, ft.com.dao.*"%>
<jsp:useBean id="info" class="ft.com.InfoMgr" scope="session" />
<jsp:useBean id="util" class="ft.com.JspUtil" scope="page" />
<%@taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>
<%@taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <logic:equal property="value(srch)" name="advContactForm"   value="Y">
    <title>Detail Search</title>
    </logic:equal>
    <logic:equal property="value(srch)" name="advContactForm"   value="N">
    <title>Contact Search</title>
    </logic:equal>
<link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/bse.css?v=<%=info.getJsVersion()%>"/>
    <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/style.css?v=<%=info.getJsVersion()%>"/>
    <script language="javaScript" type="text/javascript" src="<%=info.getReqUrl()%>/calendar/calendar.js"></script>
    <link href="<%=info.getReqUrl()%>/calendar/calendar.css" rel="stylesheet" type="text/css" />
      <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/ajax.js?v=<%=info.getJsVersion()%>"></script> 
       <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/bse.js?v=<%=info.getJsVersion()%>"></script> 
  <script type="text/javascript" src="<%=info.getReqUrl()%>/PopScripts/popup.js?v=<%=info.getJsVersion()%> " > </script>
      <script src="<%=info.getReqUrl()%>/PopScripts/jquery-1.2.6.min.js" type="text/javascript"></script>
  </head>
        <%String logId=String.valueOf(info.getLogId());
        String onfocus="cook('"+logId+"')";
        %>
 <body onfocus="<%=onfocus%>" onload="MM_preloadImages('images/faunalogo.png')"  onkeypress="return disableEnterKey(event);" topmargin="0" leftmargin="0" rightmargin="0" bottommargin="0">
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
    <td height="5" valign="top" class="greyline"><img src="<%=info.getReqUrl()%>/images/grey_linebig.png" width="685" height="5" align="right" /></td>
  </tr>
  <tr>
  <td valign="top" class="hedPg">
  <table cellpadding="1" cellspacing="5"><tr><td valign="middle">
  <img src="../images/line.jpg" border="0" /></td>
  <td valign="middle"> <span class="pgHed">
  <logic:equal property="value(srch)" name="advContactForm"   value="Y">
    Detail Search
    </logic:equal>
    <logic:equal property="value(srch)" name="advContactForm"   value="N">
    Contact Search
    </logic:equal>
  </span>
  </td><td id="loading"></td>
</tr></table>
  </td>
  </tr>
<%
ArrayList params = new ArrayList();
ResultSet rs = null;
String method = util.nvl((String)request.getAttribute("method"));
String formName = "contactsrch";
    DBUtil dbutil = new DBUtil();
    DBMgr db = new DBMgr(); 
    db.setCon(info.getCon());
    dbutil.setDb(db);
    dbutil.setInfo(info);
    db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));db.setDbUsr(info.getDbUsr());
    dbutil.setLogApplNm(info.getLoApplNm());
HashMap allFields = (HashMap)info.getFormFields();
HashMap formFields = (allFields.get(formName) == null)?dbutil.getFormFields(formName):(HashMap)allFields.get(formName);
UIForms uiForms = (UIForms)formFields.get("DAOS");
ArrayList daos = uiForms.getFields();
HashMap lovFormFlds = new HashMap();
String pgTtl = uiForms.getFORM_TTL();
pgTtl = pgTtl.replaceAll("~nme", util.nvl((String)info.getValue("NME"),""));
int addRec = Integer.parseInt(util.nvl(uiForms.getREC(),"1"));
HashMap dbinfo = info.getDmbsInfoLst();
String cnt = (String)dbinfo.get("CNT");
ArrayList rolenmLst=(ArrayList)info.getRoleLst();
String usrFlg=util.nvl((String)info.getUsrFlg());
String dfgrpnmeidn=util.nvl(info.getDfGrpNmeIdn());
String view = util.nvl(request.getParameter("view"));

String approve = util.nvl(request.getParameter("approve"));
String searchidn = util.nvl((String)request.getAttribute("searchidn"),"0");
String recordcount = util.nvl((String)request.getAttribute("count"),"0");
String formAction = "/contact/advcontact.do?method=contactsearch";
HashMap allPageDtl= (info.getPageDetails() == null)?new HashMap():(HashMap)info.getPageDetails();
 HashMap pageDtl=(HashMap)allPageDtl.get("CONTACT_SRCH");
    ArrayList pageList=new ArrayList();
    HashMap pageDtlMap=new HashMap();
    String fld_nme="",fld_ttl="",val_cond="",dflt_val="",fld_typ="",form_nme="",flg1="",lov_qry="";
String ststusStyleQ="display:none;";
pageList= ((ArrayList)pageDtl.get("STATUS") == null)?new ArrayList():(ArrayList)pageDtl.get("STATUS");
if(pageList!=null && pageList.size() >0){
for(int i=0;i<pageList.size();i++){
ststusStyleQ="";
}
}
%>



<tr><td valign="top" class="hedPg">
<%
ArrayList errors = (ArrayList)request.getAttribute("errors");
if(errors!=null && errors.size() >0){%>
<table>
<%for(int m=0;m<errors.size();m++){%>
<tr><td class="tdLayout" valign="top">
<label class="redLabel"> <%=errors.get(m)%></label>
</td></tr>
<%}%>
</table>
<%}%></td></tr>
<tr><td valign="top" class="hedPg">
<html:form action="<%=formAction%>" method="post" onsubmit="return loading()">
<html:hidden property="value(srch_id)" styleId="srchid" value="<%=searchidn%>" />
<table class="grid1">
<tr>

<logic:equal property="value(srch)" name="advContactForm"   value="Y">
<td valign="top"><table width="100%" cellpadding="2" cellspacing="2">
<tr><th colspan="2">Generic Search
<%
  if(usrFlg.equals("SYS") || rolenmLst.contains("ADMIN")){
  %>
  <img src="../images/edit.jpg" title="Edit Properties Module" onclick="newWindow('<%=info.getReqUrl()%>/dynamicProperty.do?method=load&mdl=CONTACT_SRCH&sname=csGNCSrch&par=GENERIC')" border="0" width="15px" height="15px"/> 
  <%}%>
  </th></tr>
<tr>
<td align="right"><span class="txtBold"> Search Type: </span></td>
<td><html:select property="value(typ)" name="advContactForm" >
<html:optionsCollection property="srchTypList" name="advContactForm" 
label="dsc" value="srch_typ" />
</html:select>  </td></tr>
<tr><td align="right">Srch/Access Date From : </td><td>
<html:text property="value(dtefr)" styleClass="txtStyle"  styleId="dtefr" maxlength="25" /> <a href="#"  onClick="setYears(1900, '<%=info.getCurrentYear()%>' );showCalender(this, 'dtefr');">
<img src="<%=info.getReqUrl()%>/images/calender.png"></a>
<tr><td align="right">To : </td><td>
<html:text property="value(dteto)" styleClass="txtStyle"  styleId="dteto" maxlength="25" /> <a href="#"  onClick="setYears(1900, '<%=info.getCurrentYear()%>' );showCalender(this, 'dteto');">
<img src="<%=info.getReqUrl()%>/images/calender.png"></a>
</td></tr>
<tr>
<td colspan="2">
<jsp:include page="/genericSrch.jsp"/>
</td></tr></table></td>
</logic:equal>

<td>
<table width="100%" cellpadding="2" cellspacing="2">
<tr><th colspan="4" align="left">Contact Search -  <span id="cnt"><%=recordcount%></span></th></tr>
<tr>

<%
int count =0;
for(int j=0; j < daos.size(); j++) {
UIFormsFields dao = (UIFormsFields)daos.get(j);
String lFld = dao.getFORM_FLD();
String htmlFld = lFld;
String hiddenhtmlFld = "HIDD_"+lFld;
String htmlFldTbl = dao.getTBL_NME();
String fldID= htmlFldTbl+"_"+ htmlFld;
String fldNm = "value(" +fldID + ")";
String hiddenfldNm = "value(HIDD_" +fldID + ")";
String fldTtl = util.nvl(dao.getDSP_TTL(), lFld);
String fldTyp = util.nvl(dao.getFLD_TYP(), "T");
String isReq =util.nvl(dao.getREQ_YN());
String srchgrp =util.nvl(dao.getSRCH_GRP());
String lovQ = util.nvl(dao.getLOV_QRY());
String fldSz = util.nvl(dao.getFLD_SZ(),"10"); 
String valCond = util.nvl(dao.getVAL_COND(), "");
if(valCond.indexOf("~pTbl") > -1)
valCond = valCond.replaceAll("~pTbl", htmlFldTbl);
if(valCond.indexOf("~pFld") > -1)
valCond = valCond.replaceAll("~pFld", lFld);
if(valCond.indexOf("~pGrp") > -1)
valCond = valCond.replaceAll("~pGrp", srchgrp);
if(valCond.indexOf("~pFlg") > -1)
valCond = valCond.replaceAll("~pFlg", fldTyp);
if(count==2){
count=0;

%></tr><tr>

<% }
count++;
%>



<td> <%
if(isReq.equals("Y")){%>
<span class="redTxt">*</span>
<% }
%><%=fldTtl%>
<%if((fldTyp.equals("M"))){%>
<span class="redTxt" title="Multiple Selection">*</span>
<%}%>
</td>
<td>
<%
if(fldTyp.equals("FT")) {
String ftFld = "value(" + htmlFld + "_fltr)";
%>
<html:select property="<%=ftFld%>" styleId="<%=htmlFld%>"  name="advContactForm">
<html:option value="C">Contains</html:option>
<html:option value="S">Starts Witd</html:option>
<html:option value="E">Ends Witd</html:option>
</html:select>
<%}
if((fldTyp.equals("T")) || (fldTyp.equals("FT"))) {
%>
<html:text property="<%=fldNm%>" styleId="<%=htmlFld%>"  onchange="<%=valCond%>" size="<%=fldSz%>"  name="advContactForm"/>
<%if((fldTyp.equals("T"))){%>
<html:hidden property="<%=hiddenfldNm%>" styleId="<%=hiddenhtmlFld%>" value="N"  name="advContactForm"/>
<%}
}
if(fldTyp.indexOf("SB") > -1) {
              String fldCB = "value(" + htmlFld + "_CB)";
              String fldCBid = htmlFld + "_CB";
              String dspFld = htmlFld + "_dsp" ;
              String dspFldV = "value("+ dspFld + ")";
                String fldAlias = util.nvl(dao.getALIAS());
               String aliasFld = fldAlias.substring(fldAlias.indexOf(" ") + 1);
                String dspFldDis = "value("+aliasFld+ ")";
              String dspFldPop = htmlFld + "_dsp" + "_pop" ;
              String dspFldPopV = "value("+ dspFldPop + ")";
              String keyStr = "doCompletion('"+ dspFld +"', '" + dspFldPop + "', '"+lovQ+"&frm=ADV', event)";
              String keyStrMobile = "doCompletionMobile('"+ dspFld +"', '" + dspFldPop + "', '"+lovQ+"&frm=ADV')";
              String setDown = "setDown('"+dspFldPop+"', '"+htmlFld+"', '"+ dspFld +"',event)";
              //onchange="resetVal('htmlFld','');
              if(valCond.length() > 0)
                valCond = valCond + "\"" ;
            %>
                  <input type="text" name="<%=dspFldV%>" id="<%=dspFld%>" class="sugBox"
                  onKeyUp="<%=keyStr%>" onkeypress="keypresscall('<%=htmlFldTbl%>','<%=lFld%>','<%=srchgrp%>','<%=fldTyp%>',event)" onKeyDown="<%=setDown%>" value=""  autocomplete="off" />
                <html:hidden property="<%=fldNm%>"   onchange="<%=valCond%>" value="0" styleId="<%=htmlFld%>"  name="advContactForm"/>
             
                <div style="position: absolute;">
                  <select id="<%=dspFldPop%>" name="<%=dspFldPopV%>" class="sugBoxDiv"
                    style="display:none;300px;"  
                    onKeyDown="<%=setDown%>"  
                    onDblClick="setVal('<%=dspFldPop%>', '<%=htmlFld%>', '<%=dspFld%>', event);hideObj('<%=dspFldPop%>');<%=valCond%>" 
                    onClick="setVal('<%=dspFldPop%>', '<%=htmlFld%>','<%=dspFld%>', event);" 
                    size="10">
                  </select>
                </div>
            <%}
if((fldTyp.equals("S")) || (fldTyp.equals("L")) || (fldTyp.equals("M"))) {
String lovKey = lFld + "LOV";
String press="";
if(valCond.length() > 0 && (!fldTyp.equals("M")))
//fldNm += "\" "+ " onchange=\""+valCond;
if(fldTyp.equals("M")){
press="keypresscall('"+htmlFldTbl+"','"+lFld+"','"+srchgrp+"','"+fldTyp+"',event)";
//String press=" onkeypress=\"keypresscall('"+htmlFldTbl+"','"+lFld+"','"+srchgrp+"','"+fldTyp+"',event)\"";
//fldNm += "\" "+ " "+press+" onclick=\""+valCond+ "\" "+ " multiple=\"true\""+" style=\"height:70px"  ;
}
 //util.nvl((String)formFlds.get(lovKey));
HashMap lovKV = new HashMap();
ArrayList keys = new ArrayList();
ArrayList vals = new ArrayList();
if(lovFormFlds.get(lovKey) != null) {
lovKV = (HashMap)lovFormFlds.get(lovKey);
} else {
if(lovQ.length() > 0) {
if(fldTyp.equals("S") || fldTyp.equals("M")){
if(!lFld.equals("grp_nme_idn"))
lovKV = dbutil.getLOV(lovQ);
else{
if(usrFlg.equals("SYS") || rolenmLst.contains("ADMIN")){
lovKV = dbutil.getLOV(lovQ);
}else if(!dfgrpnmeidn.equals("") && !dfgrpnmeidn.equals("0")){
lovQ=lovQ.replaceFirst("order"," and nme_idn='"+dfgrpnmeidn+"' order");
lovKV = dbutil.getLOVNOTSEL(lovQ);
}else{
lovKV = dbutil.getLOV(lovQ);
}
}
}else
lovKV = dbutil.getLOVList(lovQ);
lovFormFlds.put(lovKey, lovKV);
}
}
keys = (lovKV.get("K")!= null) ? (ArrayList)lovKV.get("K"):new ArrayList();
vals = (lovKV.get("V")!= null) ? (ArrayList)lovKV.get("V"):new ArrayList();
if(keys.size() == 0) {%>
<html:text property="<%=fldNm%>" styleId="<%=htmlFld%>"  name="advContactForm"/>
<%} else if(!fldTyp.equals("M")) {%>
<html:select property="<%=fldNm%>" onchange="<%=valCond%>" styleId="<%=htmlFld%>"  name="advContactForm">
<%for(int a=0; a < keys.size(); a++) {
String lKey = (String)keys.get(a);
String ldspVal = (String)vals.get(a);%>

<html:option value="<%=lKey%>"><%=ldspVal%></html:option>
<%}
%>
</html:select>
<%}else{%>
<html:select property="<%=fldNm%>" onchange="<%=valCond%>" onkeypress="<%=press%>" onclick="<%=valCond%>" style="height:70px" multiple="true" styleId="<%=htmlFld%>"  name="advContactForm">
<%for(int a=0; a < keys.size(); a++) {
String lKey = (String)keys.get(a);
String ldspVal = (String)vals.get(a);%>

<html:option value="<%=lKey%>"><%=ldspVal%></html:option>
<%}
%>
</html:select>
<%}
}
if(fldTyp.equals("DT")){
%>
<html:text property="<%=fldNm%>" onblur="<%=valCond%>" size="<%=fldSz%>" styleClass="txtStyle"  name="advContactForm"  styleId="<%=htmlFld%>" maxlength="25" /> <a href="#"  onClick="setYears(1900, '<%=info.getCurrentYear()%>' );showCalender(this, '<%=htmlFld%>');">
<img src="<%=info.getReqUrl()%>/images/calender.png"></a>
            <% }
if(fldTyp.equals("DR")){
String fldNm1 = "value(" +fldID + "_1)";
String fldNm2 = "value(" +fldID + "_2)";
//fldNm1+="\" size=\""+fldSz;
//fldNm2+= "\" "+ " onblur=\""+valCond+"\" size=\""+fldSz;
String htmlFld1=htmlFld+"_1";
String htmlFld2=htmlFld+"_2";
%>
<html:text property="<%=fldNm1%>" size="<%=fldSz%>" styleClass="txtStyle"  styleId="<%=htmlFld1%>" maxlength="25" /> <a href="#"  onClick="setYears(1900, '<%=info.getCurrentYear()%>' );showCalender(this, '<%=htmlFld1%>');">
<img src="<%=info.getReqUrl()%>/images/calender.png"></a>&nbsp;To&nbsp;
<html:text property="<%=fldNm2%>" onblur="<%=valCond%>" size="<%=fldSz%>" styleClass="txtStyle"  styleId="<%=htmlFld2%>" maxlength="25" /> <a href="#"  onClick="setYears(1900, '<%=info.getCurrentYear()%>' );showCalender(this, '<%=htmlFld2%>');">
<img src="<%=info.getReqUrl()%>/images/calender.png"></a>
<% }if(fldTyp.equals("NR")){
String fldNm1 = "value(" +fldID + "_1)";
String fldNm2 = "value(" +fldID + "_2)";
String change=valCond.substring(0,valCond.indexOf(";"));
//fldNm1+="\" "+ " onchange=\""+change+"\" size=\""+fldSz;
//fldNm2+= "\" "+ " onchange=\""+valCond+"\" size=\""+fldSz;
String htmlFld1=htmlFld+"_1";
String htmlFld2=htmlFld+"_2";%>
<html:text property="<%=fldNm1%>" onchange="<%=change%>" size="<%=fldSz%>"  name="advContactForm" styleClass="txtStyle"  styleId="<%=htmlFld1%>" maxlength="25" />&nbsp;To&nbsp;
<html:text property="<%=fldNm2%>" onchange="<%=valCond%>" size="<%=fldSz%>"  name="advContactForm" styleClass="txtStyle"  styleId="<%=htmlFld2%>" maxlength="25" />
<%}%>
</td>

<%
}
%>
</tr>
<tr>
<td style="<%=ststusStyleQ%>" valign="top" class="hedPg">Contact Status</td>
<td style="<%=ststusStyleQ%>" valign="top" class="hedPg">
<html:select property="value(nmestatus)" styleId="nmestatus"  name="advContactForm">
 <option value="A">Active</option>
 <option value="IA">InActive</option>
</html:select>
</td>
<td valign="top" class="hedPg">Count per Page</td>
<td><html:select property="value(page)" styleId="page"  name="advContactForm">
 <option value="25">25</option>
 <option value="50">50 </option>
 <option value="100">100 </option>
 <option value="200">200</option>
 <option value="500">500</option>
 <option value="1000">1000</option>
</html:select>
&nbsp;&nbsp;<html:text property="value(pagemnl)" size="6" styleId="pagemnl"/>
</td> </tr>
</table>
</td>
</tr>
<tr>
<td colspan="4" align="center">
<logic:equal property="value(srch)" name="advContactForm"   value="N">
<html:submit property="submit" value="View" styleClass="submit"/>&nbsp;
<html:submit property="value(merge)" value="Merge View" styleClass="submit" onclick="return verifycontactmerge()"/>&nbsp;
<input type="button" name="Clear" value="Clear" onclick="goTo('advcontact.do?method=load&srch=N','', '');" class="submit">
</logic:equal>
<logic:equal property="value(srch)" name="advContactForm"   value="Y">
<b>History Filter</b>&nbsp;
<html:select property="value(hisfilter)" styleId="hisfilter" name="advContactForm" >
<html:option value="Y">YES</html:option>
<html:option value="N">NO</html:option>
</html:select>&nbsp;
             <%
                   pageList= ((ArrayList)pageDtl.get("SELECT") == null)?new ArrayList():(ArrayList)pageDtl.get("SELECT");
             if(pageList!=null && pageList.size() >0){%>
             <b>Report Type</b>&nbsp;
             <html:select property="value(reporttyp)" styleId="reporttyp" name="advContactForm" >
             <%
               for(int i=0;i<pageList.size();i++){
                 pageDtlMap=(HashMap)pageList.get(i);
                 fld_nme=(String)pageDtlMap.get("fld_nme");
                 fld_ttl=(String)pageDtlMap.get("fld_ttl");
                 %>
                <html:option value="<%=fld_nme%>"><%=fld_ttl%></html:option>
             <%}%>
            </html:select>
           <%}%>
<%
      pageList= ((ArrayList)pageDtl.get("SUBMIT") == null)?new ArrayList():(ArrayList)pageDtl.get("SUBMIT");
      if(pageList!=null && pageList.size() >0){
      for(int j=0;j<pageList.size();j++){
      pageDtlMap=(HashMap)pageList.get(j);
      fld_nme="value("+(String)pageDtlMap.get("fld_nme")+")";
      fld_ttl=(String)pageDtlMap.get("fld_ttl");
      val_cond=(String)pageDtlMap.get("val_cond");
      fld_typ=(String)pageDtlMap.get("fld_typ");
     String fld_id = (String)pageDtlMap.get("dflt_val");
      if(fld_typ.equals("S")){ %>
<html:submit property="<%=fld_nme%>" value="<%=fld_ttl%>"  onclick="<%=val_cond%>" styleClass="submit" />
      <%}else if(fld_typ.equals("B")){%>
<html:button property="<%=fld_nme%>" value="<%=fld_ttl%>" styleId="<%=fld_id%>" onclick="<%=val_cond%>" styleClass="submit" />           
<%}else if(fld_typ.equals("HB")){%>
<input type="button" id="<%=fld_id%>" onclick="<%=val_cond%>"  value="<%=fld_ttl%>"  class="submit"/>
<%}}}%>
<!--<html:submit property="submit" value="View" styleClass="submit" onclick="return validatecontactreporttyp();"/>&nbsp;
<html:submit property="value(history)" value="Srch History" onclick="return validatecontacthis();" styleClass="submit"/>
<html:submit property="value(webaccess)" value="Web Access" styleClass="submit"/>
<input type="button" name="Clear" value="Clear" onclick="goTo('advcontact.do?method=load&srch=Y','', '');" class="submit">-->
</logic:equal>
</td>
</tr>

</table>

</html:form>
</td></tr>
<tr>
    <td><jsp:include page="/footer.jsp" /> </td>
    </tr>
</table>
<%@include file="../calendar.jsp"%>
</body>
</html>