<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page errorPage="../error_page.jsp" %>
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import="java.util.ArrayList,java.util.HashMap,java.sql.ResultSet,ft.com.*, java.util.Date, java.util.ArrayList, ft.com.dao.*"%>
<jsp:useBean id="info" class="ft.com.InfoMgr" scope="session" />
<jsp:useBean id="util" class="ft.com.JspUtil" scope="page" />
<%@taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>Contact Search</title>
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
<table width="100%"  border="0" align="center" cellpadding="0" cellspacing="0">
<%

ArrayList params = new ArrayList();
ResultSet rs = null;
String method = util.nvl((String)request.getAttribute("method"));
String formName = "contactbulkupdate";
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

String view = util.nvl(request.getParameter("view"));

String approve = util.nvl(request.getParameter("approve"));

String formAction = "/contact/bulkUpdate.do?method=bulkUpdate";


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
<% if(request.getAttribute("RTMSG")!=null){%>
        <tr><td height="15"  valign="top" class="tdLayout"><span class="redLabel"> <%=request.getAttribute("RTMSG")%></span></td></tr>
       <% }
        %>
  <tr><td height="15"  valign="top" class="tdLayout"><span class="redLabel">   <span class="redTxt">*</span>
  <span>Please Check Checkbox To Update</span></td></tr>
<tr><td valign="top" class="hedPg">
<html:form action="<%=formAction%>" method="post">
<table class="grid1">
<tr>
<td align="left">
<html:submit property="submit" value="Bulk Update" styleClass="submit"/>
&nbsp;
<input type="button" name="Clear" value="Clear" onclick="goTo('contactBulkUpd.jsp','', '');" class="submit">
</td>
</tr>
<tr>
<td>
<table width="100%" cellpadding="2" cellspacing="2">
<tr><th colspan="4" align="left">Contact Bulk Update</th></tr>
<tr>

<%
int count =0;
for(int j=0; j < daos.size(); j++) {
UIFormsFields dao = (UIFormsFields)daos.get(j);
String lFld = dao.getFORM_FLD();
String htmlFld = lFld;
String htmlFldTbl = dao.getTBL_NME();
String fldID= htmlFldTbl+"_"+ htmlFld;
String fldNm = "value(" +fldID + ")";
String chkfldNm = "value(" +fldID + "_CHK)";
String fldTtl = util.nvl(dao.getDSP_TTL(), lFld);
String fldTyp = util.nvl(dao.getFLD_TYP(), "T");
String isReq =util.nvl(dao.getREQ_YN());
String srchgrp =util.nvl(dao.getSRCH_GRP());
String lovQ = util.nvl(dao.getLOV_QRY());
int fldSz = Integer.parseInt(dao.getFLD_SZ());
String valCond = util.nvl(dao.getVAL_COND(), "");
if(valCond.indexOf("~pTbl") > -1)
valCond = valCond.replaceAll("~pTbl", htmlFldTbl);
if(valCond.indexOf("~pFld") > -1)
valCond = valCond.replaceAll("~pFld", lFld);
if(valCond.indexOf("~pGrp") > -1)
valCond = valCond.replaceAll("~pGrp", srchgrp);
if(valCond.indexOf("~pFlg") > -1)
valCond = valCond.replaceAll("~pFlg", fldTyp);
String sz = "\" size=\""+fldSz;
            sz = "\" "+ " onchange=\""+valCond + sz; 
if(count==2){
count=0;

%></tr><tr>

<% }
count++;
%>



<td nowrap="nowrap"> <%
if(isReq.equals("Y")){%>
<span class="redTxt">*</span>
<% }
%>
<html:checkbox property="<%=chkfldNm%>" value="Y" name="advContactForm"/>
<%=fldTtl%>
<%if((fldTyp.equals("M"))){%>
<span class="redTxt" title="Multiple Selection">*</span>
<%}%>
</td>
<td>
<%
if(fldTyp.equals("FT")) {
String ftFld = "value(" + htmlFld + "_fltr)";
%>
<html:select property="<%=ftFld%>" styleId="<%=htmlFld%>">
<html:option value="C">Contains</html:option>
<html:option value="S">Starts Witd</html:option>
<html:option value="E">Ends Witd</html:option>
</html:select>
<%}
if((fldTyp.equals("T")) || (fldTyp.equals("FT"))) {
fldNm += sz ;
%>
<html:text property="<%=fldNm%>" styleId="<%=htmlFld%>"/>
<%}
if(fldTyp.indexOf("SB") > -1) {
              fldNm += sz ;
              String fldCB = "value(" + htmlFld + "_CB)";
              String fldCBid = htmlFld + "_CB";
              String dspFld = htmlFld + "_dsp" ;
              String dspFldV = "value("+ dspFld + ")";
                String fldAlias = util.nvl(dao.getALIAS());
               String aliasFld = fldAlias.substring(fldAlias.indexOf(" ") + 1);
                String dspFldDis = "value("+aliasFld+ ")";
              String dspFldPop = htmlFld + "_dsp" + "_pop" ;
              String dspFldPopV = "value("+ dspFldPop + ")";
              String keyStr = "doCompletion('"+ dspFld +"', '" + dspFldPop + "', '"+lovQ+"', event)";
              String keyStrMobile = "doCompletionMobile('"+ dspFld +"', '" + dspFldPop + "', '"+lovQ+"')";
              String setDown = "setDown('"+dspFldPop+"', '"+htmlFld+"', '"+ dspFld +"',event)";
              //onchange="resetVal('htmlFld','');
              if(valCond.length() > 0)
                valCond = valCond + "\"" ;
            %>
                  <input type="text" name="<%=dspFldV%>" id="<%=dspFld%>" class="sugBox"
                  onKeyUp="<%=keyStr%>" onkeypress="keypresscall('<%=htmlFldTbl%>','<%=lFld%>','<%=srchgrp%>','<%=fldTyp%>',event)" onKeyDown="<%=setDown%>" value=""  autocomplete="off" />
                <img src="../images/addrow.jpg" width="17" height="17" title="Click To get Names" onclick="<%=keyStrMobile%>">
                <html:hidden property="<%=fldNm%>" value="0" styleId="<%=htmlFld%>"/>
             
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
if(valCond.length() > 0 && (!fldTyp.equals("M")))
fldNm += "\" "+ " onchange=\""+valCond;
if(fldTyp.equals("M")){
String press=" onkeypress=\"keypresscall('"+htmlFldTbl+"','"+lFld+"','"+srchgrp+"','"+fldTyp+"',event)\"";
fldNm += "\" "+ " "+press+" onclick=\""+valCond+ "\" "+ " multiple=\"true\""+" style=\"height:70px"  ;
}
 //util.nvl((String)formFlds.get(lovKey));
HashMap lovKV = new HashMap();
ArrayList keys = new ArrayList();
ArrayList vals = new ArrayList();
if(lovFormFlds.get(lovKey) != null) {
lovKV = (HashMap)lovFormFlds.get(lovKey);
} else {
if(lovQ.length() > 0) {
if(fldTyp.equals("S") || fldTyp.equals("M"))
lovKV = dbutil.getLOV(lovQ);
else
lovKV = dbutil.getLOVList(lovQ);
lovFormFlds.put(lovKey, lovKV);
}
}
keys = (lovKV.get("K")!= null) ? (ArrayList)lovKV.get("K"):new ArrayList();
vals = (lovKV.get("V")!= null) ? (ArrayList)lovKV.get("V"):new ArrayList();
if(keys.size() == 0) {%>
<html:text property="<%=fldNm%>" styleId="<%=htmlFld%>"/>
<%} else {%>
<html:select property="<%=fldNm%>" styleId="<%=htmlFld%>">
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
fldNm += "\" "+ " onblur=\""+valCond+"\" size=\""+fldSz;
%>
<html:text property="<%=fldNm%>" styleClass="txtStyle"  styleId="<%=htmlFld%>" maxlength="25" /> <a href="#"  onClick="setYears(1900, '<%=info.getCurrentYear()%>' );showCalender(this, '<%=htmlFld%>');">
<img src="<%=info.getReqUrl()%>/images/calender.png"></a>
            <% }
if(fldTyp.equals("DR")){
String fldNm1 = "value(" +fldID + "_1)";
String fldNm2 = "value(" +fldID + "_2)";
fldNm1+="\" size=\""+fldSz;
fldNm2+= "\" "+ " onblur=\""+valCond+"\" size=\""+fldSz;
String htmlFld1=htmlFld+"_1";
String htmlFld2=htmlFld+"_2";
%>
<html:text property="<%=fldNm1%>" styleClass="txtStyle"  styleId="<%=htmlFld1%>" maxlength="25" /> <a href="#"  onClick="setYears(1900, '<%=info.getCurrentYear()%>' );showCalender(this, '<%=htmlFld1%>');">
<img src="<%=info.getReqUrl()%>/images/calender.png"></a>&nbsp;To&nbsp;
<html:text property="<%=fldNm2%>" styleClass="txtStyle"  styleId="<%=htmlFld2%>" maxlength="25" /> <a href="#"  onClick="setYears(1900, '<%=info.getCurrentYear()%>' );showCalender(this, '<%=htmlFld2%>');">
<img src="<%=info.getReqUrl()%>/images/calender.png"></a>
<% }if(fldTyp.equals("NR")){
String fldNm1 = "value(" +fldID + "_1)";
String fldNm2 = "value(" +fldID + "_2)";
String change=valCond.substring(0,valCond.indexOf(";"));
fldNm1+="\" "+ " onchange=\""+change+"\" size=\""+fldSz;
fldNm2+= "\" "+ " onchange=\""+valCond+"\" size=\""+fldSz;
String htmlFld1=htmlFld+"_1";
String htmlFld2=htmlFld+"_2";%>
<html:text property="<%=fldNm1%>" styleClass="txtStyle"  styleId="<%=htmlFld1%>" maxlength="25" />&nbsp;To&nbsp;
<html:text property="<%=fldNm2%>" styleClass="txtStyle"  styleId="<%=htmlFld2%>" maxlength="25" />
<%}%>
</td>

<%
}
%>
</tr>
</table>
</td></tr>
</table>

</html:form>
</td></tr>

</table>
<%@include file="../calendar.jsp"%>
</body>
</html>