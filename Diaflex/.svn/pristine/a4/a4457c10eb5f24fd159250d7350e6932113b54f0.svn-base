<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page errorPage="../error_page.jsp" %>
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import="java.util.ArrayList,java.util.HashMap,java.sql.ResultSet,ft.com.*, java.util.Date, java.util.ArrayList, ft.com.dao.*"%>
<jsp:useBean id="info" class="ft.com.InfoMgr" scope="session" />
<jsp:useBean id="util" class="ft.com.JspUtil" scope="page" />
<%@taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>
<%@taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
<title>Web Access</title>
    <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/general.css?v=<%=info.getJsVersion()%>"/>
<script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/ajax.js?v=<%=info.getJsVersion()%>"></script>
<script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/bse.js?v=<%=info.getJsVersion()%>"></script>
<link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/bse.css?v=<%=info.getJsVersion()%>"/>
<link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/style.css?v=<%=info.getJsVersion()%>"/>
<script language="javaScript" type="text/javascript" src="<%=info.getReqUrl()%>/calendar/calendar.js"></script>
<link href="<%=info.getReqUrl()%>/calendar/calendar.css" rel="stylesheet" type="text/css" />
    <script type="text/javascript" src="<%=info.getReqUrl()%>/PopScripts/popup.js?v=<%=info.getJsVersion()%> " > </script>
   <script src="<%=info.getReqUrl()%>/PopScripts/jquery-1.2.6.min.js" type="text/javascript"></script>
    <script type="text/javascript" src="<%=info.getReqUrl()%>/jqueryScript/jquery.js " > </script>
</head>
        <%
        String cnt=util.nvl((String)info.getDmbsInfoLst().get("CNT"));
        String logId=String.valueOf(info.getLogId());
        String onfocus="cook('"+logId+"')";
        %>
 <body onfocus="<%=onfocus%>"   onkeypress="return disableEnterKey(event);"  >
<input type="hidden" id="reqUrl" name="reqUrl" value="<%=info.getReqUrl()%>"/>
<div id="backgroundPopup"></div>
<div id="popupContactASSFNL">
<table class="popUpTb">
 <tr><td height="5%">&nbsp;&nbsp;&nbsp;<html:button property="value(close)" styleClass="submit"  onclick="MKEmptyPopup('SC');disablePopupASSFNL();uncheckbox()" value="Close"  /> </td></tr>
<tr><td valign="top" height="95%">
<iframe name="SC" class="frameStyle" align="middle" frameborder="0">

</iframe></td></tr>
</table>
</div>
<bean:parameter id="reqNmeIdn" name="nmeIdn" value="0"/>
<bean:parameter id="reqIdn" name="idn" value="0"/>
<%
reqNmeIdn = util.nvl(request.getParameter("nmeIdn"), reqNmeIdn);
ArrayList params = new ArrayList();
ResultSet rs = null;
String method = util.nvl((String)request.getAttribute("method"));
String formName = "customerterms";
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
dbutil.SOP(formName + " : Rel Idn : " + reqIdn + " : Nme Idn : " + reqNmeIdn);
String pgTtl = uiForms.getFORM_TTL();
pgTtl = pgTtl.replaceAll("~nme", util.nvl((String)info.getValue("NME"),""));
int addRec = Integer.parseInt(util.nvl(uiForms.getREC(),"1"));

String view = util.nvl(request.getParameter("view"));

String approve = util.nvl(request.getParameter("approve"));

String formAction = "/contact/nmerel.do?method=save&nmeIdn="+reqNmeIdn;


%>
<table width="100%" border="0" cellpadding="0" cellspacing="0" id="chart">



<tr><td valign="top">
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
<% if(request.getAttribute("MSG")!=null){%>
<tr><td height="15"><span class="redLabel"> <%=request.getAttribute("MSG")%></span></td></tr>
<%}%>
<tr>
<td>
<table>
<tr><td>
<html:form action="/contact/nmerel.do?method=save" method="post">
<html:hidden property="nmeIdn" />
<html:hidden property="idn" />

<table width="95%">
<tr>
<td>
<div class="memo">
<table width="100%" class="Orange" cellpadding="1" cellspacing="1">
<tr><th colspan="4" class="Orangeth" align="left">Customer Terms</th></tr>
<tr>

<%
int count =0;
for(int j=0; j < daos.size(); j++) {
UIFormsFields dao = (UIFormsFields)daos.get(j);
String lFld = dao.getFORM_FLD();
String htmlFld = lFld;
String fldNm = "value(" + htmlFld + ")";
String fldTtl = util.nvl(dao.getDSP_TTL(), lFld);
String fldTyp = util.nvl(dao.getFLD_TYP(), "T");
String isReq =util.nvl(dao.getREQ_YN());
String fldSz = util.nvl(dao.getFLD_SZ(),"10");
String valCond = util.nvl(dao.getVAL_COND(), "");
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
%><%=fldTtl%></td>
<td nowrap="nowrap">
<%
if(fldTyp.equals("FT")) {
String ftFld = "value(" + htmlFld + "_fltr)";
%>
<html:select property="<%=ftFld%>">
<html:option value="C">Contains</html:option>
<html:option value="S">Starts Witd</html:option>
<html:option value="E">Ends Witd</html:option>
</html:select>
<%}
if((fldTyp.equals("T")) || (fldTyp.equals("FT"))) {
boolean readonly=false;
if(lFld.equals("ttl_trm_pct")){
readonly=true;
}
%>
<html:text property="<%=fldNm%>" styleId="<%=htmlFld%>" readonly="<%=readonly%>" onchange="<%=valCond%>"  size="<%=fldSz%>"/>
<%}
if(fldTyp.indexOf("SB") > -1) {
String fldCB = "value(" + htmlFld + "_CB)";
String fldCBid = htmlFld + "_CB";
String dspFld = htmlFld + "_dsp" ;
String dspFldV = "value("+ dspFld + ")";
String dspFldPop = htmlFld + "_dsp" + "_pop" ;
String dspFldPopV = "value("+ dspFldPop + ")";
String keyStr = "doCompletion('"+ dspFld +"', '" + dspFldPop + "', '../ajaxAction.do?1=1', event)";
String keyStrMobile = "doCompletionMobile('"+ dspFld +"', '" + dspFldPop + "', '../ajaxAction.do?1=1')";
String setDown = "setDown('"+dspFldPop+"', '"+htmlFld+"', '"+ dspFld +"',event)";
//onchange="resetVal('htmlFld','');
if(valCond.length() > 0)
valCond = valCond + "\"" ;

%>

<html:text property="<%=dspFldV%>" disabled="true"/>
<input type="text" name="<%=dspFldV%>_1" id="<%=dspFld%>" class="sugBox"
onKeyUp="<%=keyStr%>"   onKeyDown="<%=setDown%>"  value="" <%=valCond%> />
<img src="../images/addrow.jpg" width="17" height="17" title="Click To get Names" onclick="<%=keyStrMobile%>">
<html:hidden property="<%=fldNm%>"  onchange="<%=valCond%>" styleId="<%=htmlFld%>"/>
&nbsp;Remove&nbsp;<html:checkbox property="<%=fldCB%>" styleId="<%=fldCBid%>"  value="Y"/>
<div style="position: absolute;">
<select id="<%=dspFldPop%>" name="<%=dspFldPopV%>" class="sugBoxDiv"
style="display:none;300px;"
onKeyDown="<%=setDown%>" 
onDblClick="setVal('<%=dspFldPop%>', '<%=htmlFld%>', '<%=dspFld%>', event);hideObj('<%=dspFldPop%>')"
onClick="setVal('<%=dspFldPop%>', '<%=htmlFld%>','<%=dspFld%>', event);"
size="10">
</select>
</div>
<%}
if((fldTyp.equals("S")) || (fldTyp.equals("L"))) {
String lovKey = lFld + "LOV";
//if(valCond.length() > 0)
//fldNm += "\" " + valCond;

String lovQ = dao.getLOV_QRY(); //util.nvl((String)formFlds.get(lovKey));
HashMap lovKV = new HashMap();
ArrayList keys = new ArrayList();
ArrayList vals = new ArrayList();
if(lovFormFlds.get(lovKey) != null) {
lovKV = (HashMap)lovFormFlds.get(lovKey);
} else {
if(lovQ.length() > 0) {
if(fldTyp.equals("S"))
lovKV = dbutil.getLOV(lovQ);
else
lovKV = dbutil.getLOVList(lovQ);
lovFormFlds.put(lovKey, lovKV);
}
}
keys = (lovKV.get("K")!= null) ? (ArrayList)lovKV.get("K"):new ArrayList();
vals = (lovKV.get("V")!= null) ? (ArrayList)lovKV.get("V"):new ArrayList();
if(keys.size() == 0) {%>
<html:text property="<%=fldNm%>"/>
<%} else {
%>
<html:select property="<%=fldNm%>" styleId="<%=htmlFld%>" onchange="<%=valCond%>">
<%for(int a=0; a < keys.size(); a++) {
String lKey = (String)keys.get(a);
String ldspVal = (String)vals.get(a);%>

<html:option value="<%=lKey%>"><%=ldspVal%></html:option>
<%}
%>
</html:select>
<%}
}
if(lFld.equals("cur") || lFld.equals("trms_idn")){%>
<label><b id="<%=lFld%>_EXCL"></b></label>
<%}%>
</td>

<%
}
%>
</tr>
<tr>
<td colspan="4">

<%
String resetLnk = info.getReqUrl() + "/contacts/nmerel.jsp?nmeIdn="+reqNmeIdn ;
%>
<%if(!method.equals("edit")){%>
<html:submit property="addnew" value="Add New" styleClass="submit"/>
<%}%>
<%if(!method.equals("reset")){%>
<html:submit property="modify" value="Update" styleClass="submit"/>
<%}%>

<html:submit property="reset" value="Reset" styleClass="submit"/>
</td>
</tr>
</table></div>
</td></tr>


</table>

</html:form>
</td></tr>
</table>
</td>
</tr>
<tr>
<td>
<table>
<tr><td height="10">
</td></tr>
<tr><td>
<%

ArrayList list = (ArrayList)request.getAttribute("NmeRelList");
HashMap vldInvalidterms = ((HashMap)request.getAttribute("vldInvalidterms") == null)?new HashMap():(HashMap)request.getAttribute("vldInvalidterms");
if( list!=null && list.size() > 0) {

%>
<label class="pgTtl">Search List</label>
<table class="grid1">
<tr><td>Sr</td>

<%
for(int j=0; j < daos.size(); j++) {
UIFormsFields dao = (UIFormsFields)daos.get(j);
String lFld = dao.getFORM_FLD();
String fldTtl = util.nvl(dao.getDSP_TTL(), lFld);%>

<td nowrap="nowrap"><%=fldTtl%></td>
<%}
%>
<td>Action</td>
</tr>
<%
for(int i=0; i < list.size(); i++) {%>
<%
GenDAO nmeRel = (GenDAO)list.get(i);
String lIdn = nmeRel.getIdn();
String nmeIdn = nmeRel.getNmeIdn();
//String byr = util.getNme(nmeIdn);
String edtLnk = "<a href=\""+ info.getReqUrl() + "/contact/nmerel.do?method=edit&nmeIdn="+ nmeIdn + "&relIdn="+lIdn+"\">Edit</a>";
String delLnk = "<a href=\""+ info.getReqUrl() + "/contact/nmerel.do?method=delete&nmeIdn="+ nmeIdn + "&relIdn="+lIdn+"\">Del</a>";
String appLnk = "<a href=\""+ info.getReqUrl() + "/contact/nmerel.do?method=approve&nmeIdn="+ nmeIdn + "&idn="+lIdn+"\">Approve</a>";
String activeLnk = "<a href=\""+ info.getReqUrl() + "/contact/nmerel.do?method=active&nmeIdn="+ nmeIdn + "&relIdn="+lIdn+"\">Active</a>";
String webusr = "<a target=\"SC\" onclick=\"loadASSFNLPop('chart')\" href=\""+ info.getReqUrl() + "/contact/nmerel.do?method=loadwebusr&nmeIdn="+ nmeIdn + "&relIdn="+lIdn+"\">Web User</a>";
String color=util.nvl((String)vldInvalidterms.get(lIdn));
if(!color.equals(""))
color = "color: "+color+";";
%>
<tr style="<%=color%>">
<td><%=(i+1)%></td>
<%
for(int j=0; j < daos.size(); j++) {
UIFormsFields dao = (UIFormsFields)daos.get(j);
String lFld = dao.getFORM_FLD();
String lVal = (String)nmeRel.getValue(lFld);
String aliasFld = util.nvl(dao.getALIAS());
if(aliasFld.length() > 0) {
lVal = util.nvl((String)nmeRel.getValue(aliasFld.substring(aliasFld.indexOf(" ")+1)));
}
%>
<td nowrap="nowrap"><%=lVal%></td>
<%}%>
<td nowrap="nowrap">
<%if(approve.equals("Y")) {%>
<%} else {
if(color.equals("")){%>
<%=edtLnk%>&nbsp;|&nbsp;<%=delLnk%>
<%}else{%>
<%=webusr%>|&nbsp;<%=activeLnk%>
<%}
}%>
</td>  
</tr>
<%}
%>
</table>
<%
}
%>
</td></tr>
</table>
</td>
</tr>
<tr><td height="20">
</td></tr>
</table>
<%if(cnt.equals("ag")){%>
<script type="text/javascript">
<!--
$(window).bind("load", function() {
getNmeRelExclALL();
});
-->
</script>  
<%}%>
<%@include file="../calendar.jsp"%>
</body>
</html>
