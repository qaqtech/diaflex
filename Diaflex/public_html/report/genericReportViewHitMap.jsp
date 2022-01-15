<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page errorPage="../error_page.jsp" %>
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import="java.util.ArrayList,java.util.ArrayList,java.util.HashMap,ft.com.*, java.sql.*, java.util.Enumeration, java.util.logging.Level,java.util.Set,java.util.Iterator"%>
<jsp:useBean id="info" class="ft.com.InfoMgr" scope="session" />
<jsp:useBean id="util" class="ft.com.JspUtil" scope="page" />
<%@taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>

<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>Generic Report View</title>
    <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/bse.css?v=<%=info.getJsVersion()%>"/>
    <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/style.css?v=<%=info.getJsVersion()%>"/>
    <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/bse.js?v=<%=info.getJsVersion()%> " > </script>
    <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/assortScript.js?v=<%=info.getJsVersion()%> " > </script>
    <script type="text/javascript" src="<%=info.getReqUrl()%>/PopScripts/popup.js?v=<%=info.getJsVersion()%> " > </script>
   <script src="<%=info.getReqUrl()%>/PopScripts/jquery-1.2.6.min.js" type="text/javascript"></script>
    <script type="text/javascript" src="<%=info.getReqUrl()%>/jqueryScript/jquery.js " > </script>
    <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/general.css?v=<%=info.getJsVersion()%>"/>
        <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/ajax.js?v=<%=info.getJsVersion()%> " > </script>
    <script src="<%=info.getReqUrl()%>/jqueryScript/jqueryScript.js?v=<%=info.getJsVersion()%>" type="text/javascript"></script>
    <style type="text/css">
    table.grid10 td {
        padding:0px;
    }
    </style>
  </head>
<%
HashMap redSortedMap= ((HashMap)session.getAttribute("redSortedMap") == null)?new HashMap():(HashMap)session.getAttribute("redSortedMap");
HashMap greenSortedMap= ((HashMap)session.getAttribute("greenSortedMap") == null)?new HashMap():(HashMap)session.getAttribute("greenSortedMap");
HashMap yelloSortedwMap= ((HashMap)session.getAttribute("yelloSortedwMap") == null)?new HashMap():(HashMap)session.getAttribute("yelloSortedwMap");
HashMap dataDtl= ((HashMap)session.getAttribute("dataDtl") == null)?new HashMap():(HashMap)session.getAttribute("dataDtl");
  String processtm=util.nvl((String)request.getAttribute("processtm"));
  ArrayList srchids= (ArrayList)session.getAttribute("srchids");
  ArrayList statusLst = (ArrayList)session.getAttribute("statusLst");
  int sttLstSize=statusLst.size();
      DBUtil dbutil = new DBUtil();
    DBMgr db = new DBMgr(); 
    db.setCon(info.getCon());
    dbutil.setDb(db);
    dbutil.setInfo(info);
    db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));db.setDbUsr(info.getDbUsr());
    dbutil.setLogApplNm(info.getLoApplNm());
    HashMap dbSysInfo=info.getDmbsInfoLst();  
      HashMap allPageDtl= (info.getPageDetails() == null)?new HashMap():(HashMap)info.getPageDetails();
  HashMap pageDtl=(HashMap)allPageDtl.get("GENERIC_REPORT");
    ArrayList pageList=new ArrayList();
    HashMap pageDtlMap=new HashMap();
    String fld_nme="",fld_ttl="",val_cond="",dflt_val="",lov_qry="",fld_typ="",form_nme="",flg1="";
    
String perQTY="",perAVG="";
pageList=(ArrayList)pageDtl.get("PERDIFFQTY");
if(pageList!=null && pageList.size() >0){
for(int j=0;j<pageList.size();j++){
pageDtlMap=(HashMap)pageList.get(j);
perQTY=(String)pageDtlMap.get("dflt_val");
}
}
pageList=(ArrayList)pageDtl.get("PERDIFFAVG");
if(pageList!=null && pageList.size() >0){
for(int j=0;j<pageList.size();j++){
pageDtlMap=(HashMap)pageList.get(j);
perAVG=(String)pageDtlMap.get("dflt_val");
}
}
  int counter=0;
        String logId=String.valueOf(info.getLogId());
        String onfocus="cook('"+logId+"')";
        %>
<body onfocus="<%=onfocus%>" onload="MM_preloadImages('images/faunalogo.png')" topmargin="0" leftmargin="0" rightmargin="0" bottommargin="0">
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

  <table width="100%"  border="0" align="center" cellpadding="0" cellspacing="0" >
 
  <tr>
    <td height="5" valign="top" class="greyline"><img src="../images/grey_linebig.png" width="685" height="5" align="right" /></td>
  </tr>
  <tr>
  <td valign="top" class="hedPg">
  <table cellpadding="1" cellspacing="5"><tr><td valign="middle" nowrap="nowrap">
  <img src="../images/line.jpg" border="0" /></td><td valign="middle"> 
  <span class="pgHed">Report View
  </span></td>
    <td nowrap="nowrap">
    <table class="grid1small" cellpadding="0" cellspacing="4">
    <tr>
    <th nowrap="nowrap" colspan="2" align="center"><b>Row Property : Column Property</b></th>
    </tr>
    <tr>
    <th nowrap="nowrap" align="center">MKT</th>
    <th nowrap="nowrap" align="center">SOLD</th>
    </tr>
    <tr>
    <td align="center" class="">QTY</td><td align="center" class="BlueTxt">CTS</td>
    </tr>
    <tr>
    <td align="center" class="">AVG</td><td align="center" class="BlueTxt">RAP</td>
    </tr>
    <tr>
<%pageList=(ArrayList)pageDtl.get("SHOW");
if(pageList!=null && pageList.size() >0){
for(int j=0;j<pageList.size();j++){
pageDtlMap=(HashMap)pageList.get(j);
fld_ttl=(String)pageDtlMap.get("fld_ttl");
if(!fld_ttl.equals("QTY") && !fld_ttl.equals("CTS") && !fld_ttl.equals("AVG") && !fld_ttl.equals("RAP")){
 String blue="";
 if(j%2!=0)
 blue=" class=\"BlueTxt\"";
%>
<td align="center"><span id="" style="" <%=blue%>><%=fld_ttl%></span></td>
 <%if(j%2!=0){%>
</tr><tr> 
<%}}}}%> 
</tr>
    </table>
  </td>
  <td valign="top" class="hedPg"><span class="txtLink"  nowrap="nowrap">
  <%if(!processtm.equals("")){%>
  <b><%=processtm%></b>
  <%}
  if(srchids!=null && srchids.size()>0){%>
  Search Description 
  <%for(int i=0;i<srchids.size();i++){%>
  <%=statusLst.get(i)%> : <%=dbutil.srchDscription((String)srchids.get(i))%>
  <%}}%>
  </span></td>
  </tr></table>
  </td></tr>
  
  <tr>
  <td valign="top" class="hedPg">
  <table>
  <tr>
  <td valign="top">
    <%pageList=(ArrayList)pageDtl.get("SHOW");
if(pageList!=null && pageList.size() >0){
%> Show 
<%
for(int j=0;j<pageList.size();j++){
pageDtlMap=(HashMap)pageList.get(j);
fld_nme=(String)pageDtlMap.get("fld_nme");
fld_nme=fld_nme.replaceAll("LOOP",""+j+"");
fld_typ=(String)pageDtlMap.get("fld_typ");
fld_typ=fld_typ.replaceAll("LOOP","0");
fld_ttl=(String)pageDtlMap.get("fld_ttl");
val_cond=(String)pageDtlMap.get("val_cond");
val_cond=val_cond.replaceAll("KEY","TEST");
val_cond=val_cond.replaceAll("LOOP","0");
val_cond=val_cond.replaceAll("STTLSTSIZE",""+sttLstSize+"");
val_cond=val_cond.replaceAll("displayCTSAVG","displayCTSAVGHITMAP");
String chk="";
if(fld_ttl.equals("QTY")){
chk="checked=\"checked\"";
}
%>
   <input type="checkbox" name="<%=fld_nme%>" id="<%=fld_typ%>" <%=chk%> value="" onclick="<%=val_cond%>"/><%=fld_ttl%>&nbsp;
<%
}
}%>
</td>
</tr>
</table>
  </td>
  </tr>
  
  <tr>  <td valign="top" class="hedPg">
  <table class="grid10" id="TEST">
  <tr>
  <%
  int rownum=0;
  int colnum=0;
  if(redSortedMap!=null && redSortedMap.size()!=0){
  rownum++;
  Set set = redSortedMap.keySet();
  Iterator iter = set.iterator();
  ArrayList redkeyList=new ArrayList();
  while (iter.hasNext()) {
  redkeyList.add((String)iter.next());
  }
  int redkeyListsz= redkeyList.size();
  %>
  <%
  for(int r=0;r<redkeyListsz;r++) {
  String key=(String)redkeyList.get(r);
  String[] keysplit = key.split("@");
  String rowV=(String)keysplit[0];
  String colV=(String)keysplit[1];
  float ratio = (float) r / (float) redkeyListsz;
  int redcolor = (int) (255 * ratio + 255 * (1 - ratio));
  int greencolor = (int) (255 * ratio + 0 * (1 - ratio));
  int bluecolor = (int) (255 * ratio + 0* (1 - ratio));
  String style="background-color:rgb("+redcolor+","+greencolor+","+bluecolor+")";
  if(r!=0)
  counter++;
  if(counter%10==0){%>
  </tr><tr>
    <%}%>
    <td style="<%=style%>">
    <table class="wdth" cellpadding="0" cellspacing="4">
    <tr>
    <td nowrap="nowrap" colspan="2" align="center"><b><%=rowV%> : <%=colV%></b></td>
    </tr>
    <tr>
    <td nowrap="nowrap" align="center">MKT</td>
    <td nowrap="nowrap" align="center">SOLD</td>
    </tr>
    <tr>
  <%
    for(int st=0;st<statusLst.size();st++){
    colnum++;
    String stt=(String)statusLst.get(st);    
    String keyQty = key+"@"+stt+"@QTY";
    String keyCts = key+"@"+stt+"@CTS";
    String keyAvg = key+"@"+stt+"@AVG";
    String keyRap = key+"@"+stt+"@RAP";
    String keyAge = key+"@"+stt+"@AGE";
    String keyHit = key+"@"+stt+"@HIT";
    String keyVlu = key+"@"+stt+"@VLU";
    String diffavgkey=key+"@"+rowV+"@"+colV;
    String diffqtykey=key+"@"+rowV+"@"+colV;
    String qtyId=key.trim()+"@QTY@"+rownum+"@"+colnum;
    String ctsId=key.trim()+"@CTS@"+rownum+"@"+colnum;
    String avgId=key.trim()+"@AVG@"+rownum+"@"+colnum;
    String rapId=key.trim()+"@RAP@"+rownum+"@"+colnum;
    
    String ageId=key.trim()+"@AGE@"+rownum+"@"+colnum;
    String hitId=key.trim()+"@HIT@"+rownum+"@"+colnum;
    String vluId=key.trim()+"@VLU@"+rownum+"@"+colnum;
    String valQty = util.nvl((String)dataDtl.get(keyQty.trim()));
    String valCts = util.nvl((String)dataDtl.get(keyCts.trim()));
    String valAvg = util.nvl((String)dataDtl.get(keyAvg.trim()));
    String valRap = util.nvl((String)dataDtl.get(keyRap.trim()));
    String valAge = util.nvl((String)dataDtl.get(keyAge.trim()));
    String valHit = util.nvl((String)dataDtl.get(keyHit.trim()));
    String valVlu = util.nvl((String)dataDtl.get(keyVlu.trim()));%>
    <td style="">
    <table class="" cellpadding="0" cellspacing="">
    <tr>
    <%if(!valQty.equals("")){%>
        <td style=""><table class="wdth" cellpadding="0" cellspacing="4"><tr>	
    <td align="center" style="cursor: pointer;">
    <span id="<%=qtyId%>" style="">
    <%=valQty%>
    </span></td>
    <td align="center" class="BlueTxt"><span id="<%=ctsId%>" style="display:none;"><%=valCts%></span></td>
     </tr><tr>
    <td align="center"><span id="<%=avgId%>" style="display:none;">
    <%=valAvg%>
    </span></td>
    <td align="center"><span id="<%=rapId%>" style="display:none;" class="BlueTxt"><%=valRap%></span></td>
     </tr><tr>
<%pageList=(ArrayList)pageDtl.get("SHOW");
if(pageList!=null && pageList.size() >0){
for(int j=0;j<pageList.size();j++){
pageDtlMap=(HashMap)pageList.get(j);
fld_ttl=(String)pageDtlMap.get("fld_ttl");
if(!fld_ttl.equals("QTY") && !fld_ttl.equals("CTS") && !fld_ttl.equals("AVG") && !fld_ttl.equals("RAP")){
 String showprpId=key.trim()+"@"+fld_ttl+"@"+rownum+"@"+colnum;
 String valShowprp = util.nvl((String)dataDtl.get(key+"@"+stt+"@"+fld_ttl));
 String blue="";
 if(j%2!=0)
 blue=" class=\"BlueTxt\"";
%>
<td align="center"><span id="<%=showprpId%>" style="display:none;" <%=blue%>><%=valShowprp%></span></td>
 <%if(j%2!=0){%>
</tr><tr> 
<%}}}}%> 
</tr></table></td>
    <%}  else{%>
  <td><table class="wdth" width="100%"  cellpadding="0" cellspacing="1"><tr>	
  <td align="center"><span id="<%=qtyId%>" style=""></span></td>
  <td align="center"><span id="<%=ctsId%>" style="display:none;"></span></td>
   </tr><tr>
  <td align="center"><span id="<%=avgId%>" style="display:none;"></span></td>
  <td align="center"><span id="<%=rapId%>" style="display:none;"></span></td>
   </tr><tr>
<%pageList=(ArrayList)pageDtl.get("SHOW");
if(pageList!=null && pageList.size() >0){
for(int j=0;j<pageList.size();j++){
pageDtlMap=(HashMap)pageList.get(j);
fld_ttl=(String)pageDtlMap.get("fld_ttl");
if(!fld_ttl.equals("QTY") && !fld_ttl.equals("CTS") && !fld_ttl.equals("AVG") && !fld_ttl.equals("RAP")){
 String showprpId=key.trim()+"@"+fld_ttl+"@"+rownum+"@"+colnum;
 String valShowprp = util.nvl((String)dataDtl.get(key+"@"+stt+"@"+fld_ttl));
   String blue="";
 if(j%2!=0)
 blue=" class=\"BlueTxt\"";
%>
<td align="center"><span id="<%=showprpId%>" style="display:none;" <%=blue%> ></span></td>
 <%if(j%2!=0){%>
</tr><tr> 
<%}}}}%> 
</tr></table></td>
  <%}%>
    </tr>
</table></td>
    <%}%>
    </tr>
    </table>
    </td>
  <%}%>
  <%}%>
  
    <%
  
  if(yelloSortedwMap!=null && yelloSortedwMap.size()!=0){
  rownum++;
  Set set = yelloSortedwMap.keySet();
  Iterator iter = set.iterator();
  ArrayList redkeyList=new ArrayList();
  while (iter.hasNext()) {
  redkeyList.add((String)iter.next());
  }
  int redkeyListsz= redkeyList.size();
  %>

  <%
  for(int r=0;r<redkeyListsz;r++) {
  String key=(String)redkeyList.get(r);
  String[] keysplit = key.split("@");
  String rowV=(String)keysplit[0];
  String colV=(String)keysplit[1];
  float ratio = (float) r / (float) redkeyListsz;
  int redcolor = (int) (255 * ratio + 255 * (1 - ratio));
  int greencolor = (int) (252 * ratio + 255 * (1 - ratio));
  int bluecolor = (int) (49 * ratio + 255* (1 - ratio));
  String style="background-color:rgb("+redcolor+","+greencolor+","+bluecolor+")";
  counter++;
  if(counter%10==0){%>
  </tr><tr>
    <%}%>
    <td style="<%=style%>">
    <table class="wdth" cellpadding="0" cellspacing="4">
    <tr>
    <td nowrap="nowrap" colspan="2" align="center"><b><%=rowV%> : <%=colV%></b></td>
    </tr>
    <tr>
    <td nowrap="nowrap" align="center">MKT</td>
    <td nowrap="nowrap" align="center">SOLD</td>
    </tr>
    <tr>
  <%
    for(int st=0;st<statusLst.size();st++){
    colnum++;
    String stt=(String)statusLst.get(st);    
    String keyQty = key+"@"+stt+"@QTY";
    String keyCts = key+"@"+stt+"@CTS";
    String keyAvg = key+"@"+stt+"@AVG";
    String keyRap = key+"@"+stt+"@RAP";
    String keyAge = key+"@"+stt+"@AGE";
    String keyHit = key+"@"+stt+"@HIT";
    String keyVlu = key+"@"+stt+"@VLU";
    String diffavgkey=key+"@"+rowV+"@"+colV;
    String diffqtykey=key+"@"+rowV+"@"+colV;
    String qtyId=key.trim()+"@QTY@"+rownum+"@"+colnum;
    String ctsId=key.trim()+"@CTS@"+rownum+"@"+colnum;
    String avgId=key.trim()+"@AVG@"+rownum+"@"+colnum;
    String rapId=key.trim()+"@RAP@"+rownum+"@"+colnum;
    String ageId=key.trim()+"@AGE@"+rownum+"@"+colnum;
    String hitId=key.trim()+"@HIT@"+rownum+"@"+colnum;
    String vluId=key.trim()+"@VLU@"+rownum+"@"+colnum;
    String valQty = util.nvl((String)dataDtl.get(keyQty.trim()));
    String valCts = util.nvl((String)dataDtl.get(keyCts.trim()));
    String valAvg = util.nvl((String)dataDtl.get(keyAvg.trim()));
    String valRap = util.nvl((String)dataDtl.get(keyRap.trim()));
    String valAge = util.nvl((String)dataDtl.get(keyAge.trim()));
    String valHit = util.nvl((String)dataDtl.get(keyHit.trim()));
    String valVlu = util.nvl((String)dataDtl.get(keyVlu.trim()));%>
    <td style="">
    <table class="" cellpadding="0" cellspacing="">
    <tr>
    <%if(!valQty.equals("")){%>
        <td style=""><table class="wdth" cellpadding="0" cellspacing="4"><tr>	
    <td align="center" style="cursor: pointer;">
    <span id="<%=qtyId%>" style="">
    <%=valQty%>
    </span></td>
    <td align="center" class="BlueTxt"><span id="<%=ctsId%>" style="display:none;"><%=valCts%></span></td>
     </tr><tr>
    <td align="center"><span id="<%=avgId%>" style="display:none;">
    <%=valAvg%>
    </span></td>
    <td align="center"><span id="<%=rapId%>" style="display:none;" class="BlueTxt"><%=valRap%></span></td>
     </tr><tr>
<%pageList=(ArrayList)pageDtl.get("SHOW");
if(pageList!=null && pageList.size() >0){
for(int j=0;j<pageList.size();j++){
pageDtlMap=(HashMap)pageList.get(j);
fld_ttl=(String)pageDtlMap.get("fld_ttl");
if(!fld_ttl.equals("QTY") && !fld_ttl.equals("CTS") && !fld_ttl.equals("AVG") && !fld_ttl.equals("RAP")){
 String showprpId=key.trim()+"@"+fld_ttl+"@"+rownum+"@"+colnum;
 String valShowprp = util.nvl((String)dataDtl.get(key+"@"+stt+"@"+fld_ttl));
 String blue="";
 if(j%2!=0)
 blue=" class=\"BlueTxt\"";
%>
<td align="center"><span id="<%=showprpId%>" style="display:none;" <%=blue%>><%=valShowprp%></span></td>
 <%if(j%2!=0){%>
</tr><tr> 
<%}}}}%> 
</tr></table></td>
    <%}  else{%>
  <td><table class="wdth" width="100%"  cellpadding="0" cellspacing="1"><tr>	
  <td align="center"><span id="<%=qtyId%>" style=""></span></td>
  <td align="center"><span id="<%=ctsId%>" style="display:none;"></span></td>
   </tr><tr>
  <td align="center"><span id="<%=avgId%>" style="display:none;"></span></td>
  <td align="center"><span id="<%=rapId%>" style="display:none;"></span></td>
   </tr><tr>
<%pageList=(ArrayList)pageDtl.get("SHOW");
if(pageList!=null && pageList.size() >0){
for(int j=0;j<pageList.size();j++){
pageDtlMap=(HashMap)pageList.get(j);
fld_ttl=(String)pageDtlMap.get("fld_ttl");
if(!fld_ttl.equals("QTY") && !fld_ttl.equals("CTS") && !fld_ttl.equals("AVG") && !fld_ttl.equals("RAP")){
 String showprpId=key.trim()+"@"+fld_ttl+"@"+rownum+"@"+colnum;
 String valShowprp = util.nvl((String)dataDtl.get(key+"@"+stt+"@"+fld_ttl));
   String blue="";
 if(j%2!=0)
 blue=" class=\"BlueTxt\"";
%>
<td align="center"><span id="<%=showprpId%>" style="display:none;" <%=blue%> ></span></td>
 <%if(j%2!=0){%>
</tr><tr> 
<%}}}}%> 
</tr></table></td>
  <%}%>
    </tr>
</table></td>
    <%}%>
    </tr>
    </table>
    </td>
  <%}%>
  <%}%>
  
 <% if(greenSortedMap!=null && greenSortedMap.size()!=0){
  rownum++;
  Set set = greenSortedMap.keySet();
  Iterator iter = set.iterator();
  ArrayList redkeyList=new ArrayList();
  while (iter.hasNext()) {
  redkeyList.add((String)iter.next());
  }
  int redkeyListsz= redkeyList.size();
  %>
  <%
  for(int r=0;r<redkeyListsz;r++) {
  String key=(String)redkeyList.get(r);
  String[] keysplit = key.split("@");
  String rowV=(String)keysplit[0];
  String colV=(String)keysplit[1];
  float ratio = (float) r / (float) redkeyListsz;
  int redcolor = (int) (94 * ratio + 255 * (1 - ratio));
  int greencolor = (int) (251 * ratio + 255 * (1 - ratio));
  int bluecolor = (int) (110 * ratio + 255* (1 - ratio));
  String style="background-color:rgb("+redcolor+","+greencolor+","+bluecolor+")";
  counter++;
  if(counter%10==0){%>
  </tr><tr>
    <%}%>
    <td style="<%=style%>">
    <table class="wdth" cellpadding="0" cellspacing="4">
    <tr>
    <td nowrap="nowrap" colspan="2" align="center"><b><%=rowV%> : <%=colV%></b></td>
    </tr>
    <tr>
    <td nowrap="nowrap" align="center">MKT</td>
    <td nowrap="nowrap" align="center">SOLD</td>
    </tr>
    <tr>
  <%
    for(int st=0;st<statusLst.size();st++){
    colnum++;
    String stt=(String)statusLst.get(st);    
    String keyQty = key+"@"+stt+"@QTY";
    String keyCts = key+"@"+stt+"@CTS";
    String keyAvg = key+"@"+stt+"@AVG";
    String keyRap = key+"@"+stt+"@RAP";
    String keyAge = key+"@"+stt+"@AGE";
    String keyHit = key+"@"+stt+"@HIT";
    String keyVlu = key+"@"+stt+"@VLU";
    String diffavgkey=key+"@"+rowV+"@"+colV;
    String diffqtykey=key+"@"+rowV+"@"+colV;
    String qtyId=key.trim()+"@QTY@"+rownum+"@"+colnum;
    String ctsId=key.trim()+"@CTS@"+rownum+"@"+colnum;
    String avgId=key.trim()+"@AVG@"+rownum+"@"+colnum;
    String rapId=key.trim()+"@RAP@"+rownum+"@"+colnum;
    String ageId=key.trim()+"@AGE@"+rownum+"@"+colnum;
    String hitId=key.trim()+"@HIT@"+rownum+"@"+colnum;
    String vluId=key.trim()+"@VLU@"+rownum+"@"+colnum;
    String valQty = util.nvl((String)dataDtl.get(keyQty.trim()));
    String valCts = util.nvl((String)dataDtl.get(keyCts.trim()));
    String valAvg = util.nvl((String)dataDtl.get(keyAvg.trim()));
    String valRap = util.nvl((String)dataDtl.get(keyRap.trim()));
    String valAge = util.nvl((String)dataDtl.get(keyAge.trim()));
    String valHit = util.nvl((String)dataDtl.get(keyHit.trim()));
    String valVlu = util.nvl((String)dataDtl.get(keyVlu.trim()));%>
    <td style="">
    <table class="" cellpadding="0" cellspacing="">
    <tr>
    <%if(!valQty.equals("")){%>
        <td style=""><table class="wdth" cellpadding="0" cellspacing="4"><tr>	
    <td align="center" style="cursor: pointer;">
    <span id="<%=qtyId%>" style="">
    <%=valQty%>
    </span></td>
    <td align="center" class="BlueTxt"><span id="<%=ctsId%>" style="display:none;"><%=valCts%></span></td>
     </tr><tr>
    <td align="center"><span id="<%=avgId%>" style="display:none;">
    <%=valAvg%>
    </span></td>
    <td align="center"><span id="<%=rapId%>" style="display:none;" class="BlueTxt"><%=valRap%></span></td>
     </tr><tr>
<%pageList=(ArrayList)pageDtl.get("SHOW");
if(pageList!=null && pageList.size() >0){
for(int j=0;j<pageList.size();j++){
pageDtlMap=(HashMap)pageList.get(j);
fld_ttl=(String)pageDtlMap.get("fld_ttl");
if(!fld_ttl.equals("QTY") && !fld_ttl.equals("CTS") && !fld_ttl.equals("AVG") && !fld_ttl.equals("RAP")){
 String showprpId=key.trim()+"@"+fld_ttl+"@"+rownum+"@"+colnum;
 String valShowprp = util.nvl((String)dataDtl.get(key+"@"+stt+"@"+fld_ttl));
 String blue="";
 if(j%2!=0)
 blue=" class=\"BlueTxt\"";
%>
<td align="center"><span id="<%=showprpId%>" style="display:none;" <%=blue%>><%=valShowprp%></span></td>
 <%if(j%2!=0){%>
</tr><tr> 
<%}}}}%> 
</tr></table></td>
    <%}  else{%>
  <td><table class="wdth" width="100%"  cellpadding="0" cellspacing="1"><tr>	
  <td align="center"><span id="<%=qtyId%>" style=""></span></td>
  <td align="center"><span id="<%=ctsId%>" style="display:none;"></span></td>
   </tr><tr>
  <td align="center"><span id="<%=avgId%>" style="display:none;"></span></td>
  <td align="center"><span id="<%=rapId%>" style="display:none;"></span></td>
   </tr><tr>
   
<%pageList=(ArrayList)pageDtl.get("SHOW");
if(pageList!=null && pageList.size() >0){
for(int j=0;j<pageList.size();j++){
pageDtlMap=(HashMap)pageList.get(j);
fld_ttl=(String)pageDtlMap.get("fld_ttl");
if(!fld_ttl.equals("QTY") && !fld_ttl.equals("CTS") && !fld_ttl.equals("AVG") && !fld_ttl.equals("RAP")){
 String showprpId=key.trim()+"@"+fld_ttl+"@"+rownum+"@"+colnum;
 String valShowprp = util.nvl((String)dataDtl.get(key+"@"+stt+"@"+fld_ttl));
   String blue="";
 if(j%2!=0)
 blue=" class=\"BlueTxt\"";
%>
<td align="center"><span id="<%=showprpId%>" style="display:none;" <%=blue%> ></span></td>
 <%if(j%2!=0){%>
</tr><tr> 
<%}}}}%> 
</tr></table></td>
  <%}%>
    </tr>
</table></td>
    <%}%>
    </tr>
    </table>
    </td>
  <%}%>
  <%}%>
  

  </tr>
  </table>
  </td>
  </tr>
  
  </table>
</body>
</html>