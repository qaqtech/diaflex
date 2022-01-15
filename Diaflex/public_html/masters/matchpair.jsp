<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page errorPage="../error_page.jsp" %>
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.ResultSet, java.util.ArrayList,java.util.Collections,java.util.ArrayList,java.util.HashMap,java.text.SimpleDateFormat, java.util.Calendar"%>
<jsp:useBean id="info" class="ft.com.InfoMgr" scope="session" />
<jsp:useBean id="util" class="ft.com.JspUtil" scope="page" />
<%@taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>
<%@taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
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

  <title>Match Pair</title>
 
  </head>

        <%
        ArrayList  matchpairprpList= (session.getAttribute("matchpairprpList") == null)?new ArrayList():(ArrayList)session.getAttribute("matchpairprpList");
        HashMap mprp = info.getSrchMprp();
        HashMap prp = info.getSrchPrp();
        String logId=String.valueOf(info.getLogId());
        HashMap dbms = info.getDmbsInfoLst();
        String cnt = util.nvl((String)dbms.get("CNT"));
        String onfocus="cook('"+logId+"')";
        %>
 <body onfocus="<%=onfocus%>" onload="MM_preloadImages('images/faunalogo.png')" topmargin="0" leftmargin="0" rightmargin="0" bottommargin="0">
 
  <input type="hidden" id="reqUrl" name="reqUrl" value="<%=info.getReqUrl()%>"/>
<div id="popupContactSale">
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
  <img src="../images/line.jpg" border="0" /></td><td valign="middle"> <span class="pgHed">Match Pair</span> 
  <span id="loading"></span>
  </td>
</tr></table>
  </td>
  </tr>
 <%String msg = (String)request.getAttribute("rtnmsg");
  if(msg!=null){
  %>
  <tr><td valign="top" class="tdLayout"> <span class="redLabel" > <%=msg%></span></td></tr>
  <%}%>
<tr><td valign="top" class="hedPg">
<html:form action="/masters/match.do?method=add"  method="post" onsubmit="loading()">
  <table  class="grid1">
   <tr>
   <th colspan="6" align="center">Match Pair</th>
   </tr>
   <tr>
   <th>Master Property</th><th>Prp Val From</th><th>Prp Val To</th><th>Var From</th><th>Var To</th><th>Slab</th>
   </tr>
   <tr>
   <td>
    <html:select property="value(mprp)" name="matchPairForm" styleId="mprp" style="width:100px;" onchange="getPrpvaluesmatchPair(this,'')" >
    <html:optionsCollection property="value(matchpairPrp)" name="matchPairForm" value="FORM_NME" label="FORM_TTL" />
    </html:select>
   </td>
   <td>
   <html:text property="value(val_frm)" styleClass="txtStyle" size="10"  styleId="val_frm" name="matchPairForm"/>
   <html:select property="value(val_frmC)"  styleId="val_frmC" style="display:none; width:100px;"  name="matchPairForm">
   </html:select>
   </td>
   <td>
   <html:text property="value(val_to)" styleClass="txtStyle" size="10"  styleId="val_to" name="matchPairForm"/>
   <html:select property="value(val_toC)"  styleId="val_toC" style="display:none; width:100px;" name="matchPairForm" >
   </html:select>
   </td>
   <td>
   <html:text property="value(var_frm)" styleClass="txtStyle" size="10"  styleId="var_frm" name="matchPairForm"/>
   </td>
   <td>
   <html:text property="value(var_to)" styleClass="txtStyle" size="10"  styleId="var_to" name="matchPairForm"/>
   </td>
   
    <td>
   <html:text property="value(slab)" styleClass="txtStyle" size="10"  styleId="slab" value="1" name="matchPairForm"/>
   </td>
   
   </tr>
   <tr>
   <td colspan="5" align="center"><html:submit property="value(add)" value="Add Criteria" styleClass="submit" />
   </td>
   </tr>
   </table>
   </html:form>
</td>
</tr>
<%if(matchpairprpList.size()>0){%>
<tr>
<td valign="top" class="hedPg">
<html:form action="/masters/match.do?method=update"  method="post" onsubmit="loading()">
<table class="grid1">
<tr>
<th></th><th>Master Property</th><th>Prp Val From</th><th>Prp Val To</th><th>Var From</th><th>Var To</th><th>Slab</th> <th>Action</th>
</tr>
<%
for(int i=0;i<matchpairprpList.size();i++){
HashMap dtl=(HashMap)matchpairprpList.get(i);
String idn=util.nvl((String)dtl.get("idn"));
String lprp=util.nvl((String)dtl.get("mprp"));
String mprpFld = "value(" + idn + "_mprp)" ;
String chkFld = "value(" + idn + ")" ;
String chkFldId = idn;
String val_frmFldC = "value(" + idn + "_val_frmC)" ;
String val_toFldC = "value(" + idn + "_val_toC)" ;
String val_frmFldIdC =  idn + "_val_frmC" ;
String val_toFldIdC = idn + "_val_toC" ;

String val_frmFld = "value(" + idn + "_val_frm)" ;
String val_toFld = "value(" + idn + "_val_to)" ;
String val_frmFldId =  idn + "_val_frm" ;
String val_toFldId = idn + "_val_to" ;

String var_frmFld = "value(" + idn + "_var_frm)" ;
String var_toFld = "value(" + idn + "_var_to)" ;
String var_frmFldId = idn + "_var_frm" ;
String var_toFldId = idn + "_var_to" ;

String slabFldId = idn + "_slab" ;
String slabFld = "value(" + slabFldId + ")" ;
String displayC="display:none;";
String display="display:none;";
String onchange="getPrpvaluesmatchPair(this,'"+idn+"_')"; 
String lprpTyp = util.nvl((String)mprp.get(lprp+"T")); 
if(lprpTyp.equals("C")){
displayC="width:100px;";
display="display:none;width:100px;";
}else{
display="width:100px;";
displayC="display:none;width:100px;";
}
String link = info.getReqUrl() + "/masters/match.do?method=delete&idn="+ idn ;
String onclick = "return setDeleteConfirm('"+link+"')";
%>
<tr>
<td>
<html:checkbox property="<%=chkFld%>"  name="matchPairForm" styleId="<%=chkFldId%>" value="Y"></html:checkbox>
</td>
<td>
<html:select property="<%=mprpFld%>" name="matchPairForm" onchange="<%=onchange%>"  >
<html:optionsCollection property="value(matchpairPrp)" name="matchPairForm" value="FORM_NME" label="FORM_TTL" />
</html:select>
</td>
<td>
<html:text property="<%=val_frmFld%>" style="<%=display%>" size="10"  styleId="<%=val_frmFldId%>" name="matchPairForm"/>
<html:select property="<%=val_frmFldC%>"  styleId="<%=val_frmFldIdC%>" style="<%=displayC%>" name="matchPairForm">
<%if(lprpTyp.equals("C")){
ArrayList prpPrt = (ArrayList)prp.get(lprp+"P");
ArrayList prpVal = (ArrayList)prp.get(lprp+"V");
for(int m=0;m<prpPrt.size();m++){
String pPrtl = (String)prpPrt.get(m);
String vall = (String)prpVal.get(m);
%>
<html:option value="<%=vall%>"><%=pPrtl%></html:option>
<%}%>
<%}%>
</html:select>
</td>
<td>
<html:text property="<%=val_toFld%>" style="<%=display%>" size="10"  styleId="<%=val_toFldId%>" name="matchPairForm"/>
<html:select property="<%=val_toFldC%>"  styleId="<%=val_toFldIdC%>" style="<%=displayC%>"  name="matchPairForm">
<%if(lprpTyp.equals("C")){
ArrayList prpPrt = (ArrayList)prp.get(lprp+"P");
ArrayList prpVal = (ArrayList)prp.get(lprp+"V");
for(int m=0;m<prpPrt.size();m++){
String pPrtl = (String)prpPrt.get(m);
String vall = (String)prpVal.get(m);
%>
<html:option value="<%=vall%>"><%=pPrtl%></html:option>
<%}%>
<%}%>
</html:select>
</td>
<td>
<html:text property="<%=var_frmFld%>" styleClass="txtStyle" size="10"  styleId="<%=var_frmFldId%>" name="matchPairForm"/>
</td>
<td>
<html:text property="<%=var_toFld%>" styleClass="txtStyle" size="10"  styleId="<%=var_toFldId%>" name="matchPairForm"/>
</td>
<td>
<html:text property="<%=slabFld%>" styleClass="txtStyle" size="10"  styleId="<%=slabFldId%>" name="matchPairForm"/>
</td>
<td><html:link page="<%=link%>" onclick="<%=onclick%>">Delete</html:link></td>
</tr>
<%}%>
   <tr>
   <td colspan="7" align="center"><html:submit property="value(update)" value="Update Criteria" styleClass="submit" />
   </td>
   </tr>
</table>
</html:form>
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