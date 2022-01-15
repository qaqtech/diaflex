<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=windows-1252"%>
<jsp:useBean id="info" class="ft.com.InfoMgr" scope="session" />
<jsp:useBean id="util" class="ft.com.JspUtil" scope="page" />

<%@ page import="java.util.ArrayList,java.util.HashMap,java.sql.ResultSet, java.util.Date, java.util.ArrayList,
java.text.DecimalFormat, java.util.HashMap, ft.com.dao.*"%>
<%@taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>Verification</title>
    <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/bse.css?v=<%=info.getJsVersion()%>"/>
    <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/general.css?v=<%=info.getJsVersion()%>"/>
     <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/style.css?v=<%=info.getJsVersion()%>"/>
      <script src="../scripts/bse.js" type="text/javascript"></script>
      <script src="../scripts/hkajax.js" type="text/javascript"></script>
        <script type="text/javascript" src="<%=info.getReqUrl()%>/PopScripts/popup.js?v=<%=info.getJsVersion()%> " > </script>
    <script src="<%=info.getReqUrl()%>/PopScripts/jquery-1.2.6.min.js" type="text/javascript"></script>
  </head>
        <%String logId=String.valueOf(info.getLogId());
        String onfocus="cook('"+logId+"')";
        HashMap allPageDtl= (info.getPageDetails() == null)?new HashMap():(HashMap)info.getPageDetails();
HashMap pageDtl=(HashMap)allPageDtl.get("VERIFICATION");
String memolmt = util.nvl(info.getMemo_lmt());
    ArrayList pageList=new ArrayList();
    HashMap pageDtlMap=new HashMap();
    String fld_nme="",fld_ttl="",val_cond="",dflt_val="",fld_typ="",form_nme="",lov_qry="",flg1="";
        %>
 <body onfocus="<%=onfocus%>" onload="MM_preloadImages('images/faunalogo.png')" topmargin="0" leftmargin="0" rightmargin="0" bottommargin="0">
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
    <td height="5" valign="top" class="greyline" ><img src="../images/grey_linebig.png" width="685" height="5" align="right" /></td>
  </tr>
  <tr>
  <td valign="top" class="hedPg">
  <table cellpadding="1" cellspacing="5"><tr><td valign="middle">
  <img src="../images/line.jpg" border="0" /></td><td valign="middle"> <span class="pgHed">Verification</span> </td>
<%

String sucessMemoId = util.nvl((String)request.getAttribute("sucessMemoId"));
String msg = util.nvl((String)request.getAttribute("msg"));
if(!sucessMemoId.equals("")){
sucessMemoId=sucessMemoId.replaceFirst(",","");
%>
  <td><span class="redLabel" style="font-size:12px;" >Verifaction Sucess OF Above Memo Id : <%=sucessMemoId%></span></td>
<%}
if(!msg.equals("")){

%>
  <td><span class="redLabel" style="font-size:12px;" > <%=msg%></span></td>
<%}%>

</tr></table>
  </td>
  </tr>
  <%HashMap prp = info.getPrp();
      ArrayList PrpDtl = (ArrayList)prp.get("DEPT"+"V");
      ArrayList typ= (ArrayList)session.getAttribute("typ");
      HashMap stkTypDtl= (HashMap)request.getAttribute("stkTypDtl");
      HashMap GrandTtlDtl= (HashMap)request.getAttribute("GrandTtlDtl");
      int colspn=(PrpDtl.size()*2)+3;
      String PCS="";
      String CTS="";
      HashMap stkTypDeptDtl=null;
      HashMap GrandTtl=null;
      String type="";
  %>
 <tr><td valign="top" class="tdLayout">
 <table class="dataTable">
 <tr> <td colspan="<%=colspn%>">Stock Type Summary</td> </tr>
 <tr>
 <th>Type</th>
 <%for(int i=0;i<PrpDtl.size();i++){%>
 <th colspan="2"><%=PrpDtl.get(i)%></th>
 <%}%>
 <th colspan="2">Total</th>
 </tr>
 <tr>
 <th></th>
  <%for(int i=0;i<PrpDtl.size();i++){%>
 <th>Pcs</th><th>Cts</th>
 <%}%>
 <th>Pcs</th><th>Cts</th>
 </tr>
 <%for(int i=0;i<typ.size();i++){
 type=(String)typ.get(i);%>
 <tr>
 <td><%=type%></td>
  <%for(int j=0;j<PrpDtl.size();j++){
  String key=type+"_"+PrpDtl.get(j);
  PCS="0";CTS="0.0"; 
  stkTypDeptDtl=new HashMap();
  stkTypDeptDtl=(HashMap)stkTypDtl.get(key);
  if(stkTypDeptDtl!=null){
  PCS=util.nvl2((String)stkTypDeptDtl.get("PCS"),"0");
  CTS=util.nvl2((String)stkTypDeptDtl.get("CTS"),"0");}
  %>
  <td><%=PCS%></td>
  <td><%=CTS%></td>
   <%}
   GrandTtl=new HashMap();
   GrandTtl=(HashMap)GrandTtlDtl.get(type);
   PCS="0";CTS="0.0";
    if(GrandTtl!=null){
   PCS=util.nvl2((String)GrandTtl.get("PCS"),"0");
   CTS=util.nvl2((String)GrandTtl.get("CTS"),"0");}
   %>
  <td><%=PCS%></td>
  <td><%=CTS%></td>
 </tr>
 <%}%>
 <tr style="font-weight:bold;">
  <td>Grand Total</td>
  <%for(int j=0;j<PrpDtl.size();j++){
  String key=(String)PrpDtl.get(j);
  GrandTtl=new HashMap();
  GrandTtl=(HashMap)GrandTtlDtl.get(key);
  PCS="0";CTS="0.0";
    if(GrandTtl!=null){
  PCS=util.nvl2((String)GrandTtl.get("PCS"),"0");
   CTS=util.nvl2((String)GrandTtl.get("CTS"),"0");}
  %>
  <td><%=PCS%></td>
  <td><%=CTS%></td>
   <%}%> 
  <%
  GrandTtl=new HashMap();
  GrandTtl=(HashMap)GrandTtlDtl.get("GTOTAL");
   PCS="0";CTS="0.0";
    if(GrandTtl!=null){
   PCS=util.nvl2((String)GrandTtl.get("PCS"),"0");
   CTS=util.nvl2((String)GrandTtl.get("CTS"),"0");}
   %>
  <td><%=PCS%></td>
  <td><%=CTS%></td>        
 </tr>
  </table><table width="100%">
    <tr align="left"><td>
    <%String color="";
    for(int j=0;j<typ.size();j++){
    type=(String)typ.get(j);
    color="color:#ffffff";
    if(!type.equals("NEW")){
    color="";  
      }
    
    %>
    <span class="txtLink" id="<%=type%>_TAB" style="<%=color%>"  onclick="displayVerification('<%=type%>')"> <%=type%></span>
    <%}%>
    </tr>
  <tr><td valign="top">
  <form action="verificationhk.do" name="verification" onsubmit="return chkSubmit();">
  <input type="hidden" name="method" value="processForm"/>
  <input type="hidden" name="vertyp" id="vertyp" value="NEW"/>
  <%  
  ArrayList memoIDN= (ArrayList)request.getAttribute("memoIDN");
      HashMap memoDtl= (HashMap)session.getAttribute("memoDtl");
      HashMap memoTypDtl= (HashMap)request.getAttribute("memoTypDtl");
      colspn=(PrpDtl.size()*2)+5;
      for(int j=0;j<typ.size();j++){
      type=(String)typ.get(j);
      String style = "display:'';";
      if(!type.equals("NEW")){
      style = "display:none; ";
      
      }
      %>

  <div style="<%=style%>" id="<%=type%>">
  <table class="dataTable" >
  <tr> <td colspan="<%=colspn%>">Memo Wise Details</td> </tr>
  <tr>
  <th><input name="ALLMemoWise" id="ALLMemoWise_<%=type%>" type="checkbox" onclick="CheckAllChkbox(this.id, 'memowisechk_<%=type%>')" /></th>
  <th>Sr. No.</th>
  <th>Memo</th>
   <%for(int i=0;i<PrpDtl.size();i++){%>
 <th colspan="2"><%=PrpDtl.get(i)%></th>
 <%}%>
 <th colspan="2">Total</th>
 <th>Status</th>
  </tr>
  <tr>
  <td></td><td></td><td></td>
  <%for(int i=0;i<PrpDtl.size();i++){%>
 <th>Pcs</th><th>Cts</th>
 <%}%>
 <th>Pcs</th><th>Cts</th>
 <th></th>
  </tr>
  

   <%for(int k=0;k<memoIDN.size();k++){
   String memoId=(String)memoIDN.get(k);
   String chk=(String)memoTypDtl.get(memoId);
   if(chk.equals(type)){
   %>
     <tr>
<td><input value="<%=memoId%>" type="checkbox" name="memowisechk" id="<%=memoId%>_memowisechk_<%=type%>"/></td>
<td><%=k+1%></td>
   <td><%=memoId%></td>
    <%for(int l=0;l<PrpDtl.size();l++){
  String dept=(String)PrpDtl.get(l);
  String key=type+"_"+memoId+"_"+dept;
  HashMap memoDtlQC=(HashMap)memoDtl.get(key);
  PCS="0";CTS="0.0";
    if(memoDtlQC!=null){
  PCS=util.nvl2((String)memoDtlQC.get("PCS"),"0");
   CTS=util.nvl2((String)memoDtlQC.get("CTS"),"0");}
  %>
  <td><%=PCS%></td>
  <td><%=CTS%></td>
 <%}%>
 <%
 GrandTtl=new HashMap();
   GrandTtl=(HashMap)GrandTtlDtl.get(type+"_"+memoId);
   PCS="0";CTS="0.0";
   String STT="";
    if(GrandTtl!=null){
   PCS=util.nvl2((String)GrandTtl.get("PCS"),"0");
   CTS=util.nvl2((String)GrandTtl.get("CTS"),"0");
    STT=util.nvl2((String)GrandTtl.get("STT"),"0");}
   %>
  <td><%=PCS%></td>
  <td><%=CTS%></td>
  <td><%=STT%></td>
 <%}%>
 </tr>
 <%}%>
  
  <tr style="font-weight:bold;">
  <td></td>
  <td></td>
  <td>Grand Total</td>
  <%for(int m=0;m<PrpDtl.size();m++){
  String key=type+"_"+PrpDtl.get(m);
  PCS="0";CTS="0.0";
  stkTypDeptDtl=new HashMap();
  stkTypDeptDtl=(HashMap)stkTypDtl.get(key);
  if(stkTypDeptDtl!=null){
  PCS=util.nvl2((String)stkTypDeptDtl.get("PCS"),"0");
  CTS=util.nvl2((String)stkTypDeptDtl.get("CTS"),"0");}
  %>
  <td><%=PCS%></td>
  <td><%=CTS%></td>
   <%}
   HashMap GrandTtll=(HashMap)GrandTtlDtl.get(type);
   PCS="0";CTS="0.0";
    if(GrandTtll!=null){
   PCS=util.nvl2((String)GrandTtll.get("PCS"),"0");
   CTS=util.nvl2((String)GrandTtll.get("CTS"),"0");}
   %>
  <td><%=PCS%></td>
  <td><%=CTS%></td>
  <td></td>
  </tr>
  
  
  </table>
  </div>
  <%}%>
  <%    pageList=(ArrayList)pageDtl.get("BUTTON");
            if(pageList!=null && pageList.size() >0){
            for(int j=0;j<pageList.size();j++){
            pageDtlMap=(HashMap)pageList.get(j);
            fld_nme="value("+(String)pageDtlMap.get("fld_nme")+")";
            fld_ttl=(String)pageDtlMap.get("fld_ttl");
            fld_typ=(String)pageDtlMap.get("fld_typ");
            lov_qry=(String)pageDtlMap.get("lov_qry");
            dflt_val=(String)pageDtlMap.get("dflt_val");
            val_cond=(String)pageDtlMap.get("val_cond"); 
            if(fld_typ.equals("S")){
            %>
    <html:submit property="<%=fld_nme%>" value="<%=fld_ttl%>" onclick="<%=val_cond%>" styleClass="submit"/>
    <%}else if(fld_typ.equals("B")){%>
   <html:button property="<%=fld_nme%>" value="<%=fld_ttl%>" onclick="<%=val_cond%>" styleClass="submit"/>
    <%}else if(fld_typ.equals("HB")){%>
    <button type="button" onclick="<%=val_cond%>" class="submit" accesskey="<%=lov_qry%>" id="<%=fld_nme%>" name="<%=fld_nme%>" ><%=fld_ttl%></button>
    <%}else if(fld_typ.equals("IN")){%>
    <input type="submit" value="<%=fld_ttl%>" class="submit"/>
    <%}}}
    %>
</form> </td></tr>
 </table>
 </td>
 </tr>
   <tr>
    <td><jsp:include page="/footer.jsp" /> </td>
    </tr>
  </table>
  </body>
</html>