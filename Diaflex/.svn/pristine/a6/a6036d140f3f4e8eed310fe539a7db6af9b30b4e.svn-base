<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page errorPage="../error_page.jsp" %>
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import="java.util.ArrayList,java.util.HashMap,java.sql.ResultSet,ft.com.*, java.util.Date, java.util.ArrayList, ft.com.dao.*"%>
<jsp:useBean id="info" class="ft.com.InfoMgr" scope="session" />
<jsp:useBean id="util" class="ft.com.JspUtil" scope="page" />

<%@taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
     <title>Purchase Detail</title>
     <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/bse.css?v=<%=info.getJsVersion()%>"/>
     <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/style.css?v=<%=info.getJsVersion()%>"/>
     <script language="javaScript" type="text/javascript" src="<%=info.getReqUrl()%>/calendar/calendar.js"></script>
     <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/bse.js?v=<%=info.getJsVersion()%> " > </script>
     <link href="<%=info.getReqUrl()%>/calendar/calendar.css" rel="stylesheet" type="text/css" />
         <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/ajax.js?v=<%=info.getJsVersion()%> " > </script>
  <script type="text/javascript" src="<%=info.getReqUrl()%>/PopScripts/popup.js?v=<%=info.getJsVersion()%> " > </script>
   <script src="<%=info.getReqUrl()%>/PopScripts/jquery-1.2.6.min.js" type="text/javascript"></script>
   <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/general.css?v=<%=info.getJsVersion()%>"/>
    <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/bse.js?v=<%=info.getJsVersion()%> " > </script>
    <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/box.js?v=<%=info.getJsVersion()%> " > </script>
  </head>
        <%String logId=String.valueOf(info.getLogId());
        String onfocus="cook('"+logId+"')";
        ArrayList vwPrpLst = (ArrayList)request.getAttribute("vwPrpLst");
        HashMap mprp = info.getMprp();
        HashMap prp = info.getPrp();
        %>
 <body onfocus="<%=onfocus%>" onload="MM_preloadImages('images/faunalogo.png')" topmargin="0" leftmargin="0" rightmargin="0" bottommargin="0">
<input type="hidden" id="reqUrl" name="reqUrl" value="<%=info.getReqUrl()%>"/>
<div id="backgroundPopup"></div>
<div id="popupContactASSFNL">
<table class="popUpTb">
 <tr>
 <td height="5%" id="close">&nbsp;&nbsp;&nbsp;<html:button property="value(close)" styleClass="submit"  onclick="MKEmptyPopup('SC');disablePopupASSFNL();reportUploadclose('PUR')" value="Close"  /> </td>
 <td height="5%" id="closereload" style="display:none">&nbsp;&nbsp;&nbsp;<html:button property="value(close)" styleClass="submit"  onclick="MKEmptyPopup('SC');disablePopupASSFNL();reportUploadclose('PUR')" value="Close"  /> </td>
 </tr>
<tr><td valign="top" height="95%">
<iframe name="SC" class="frameStyle"   align="middle" frameborder="0">

</iframe></td></tr>
</table>
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
  <%
   ArrayList params = new ArrayList();
    ResultSet rs = null;
    String formName = "purDtlForm";
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
    ArrayList prpDspBlocked = info.getPageblockList();
    HashMap lovFormFlds = new HashMap();
    ArrayList reqList = (ArrayList)request.getAttribute(formName);
     HashMap avgDtl = ((HashMap)request.getAttribute("avgDtl") == null)?new HashMap():(HashMap)request.getAttribute("avgDtl");
    dbutil.SOP(" View "+ formName + " : fields = " + daos.size());
    int addRec = Integer.parseInt(util.nvl(uiForms.getREC(),"10"));
    String formAction = "/purchase/purchaseDtlAction.do?method=save";
    HashMap allPageDtl= (info.getPageDetails() == null)?new HashMap():(HashMap)info.getPageDetails();
    HashMap pageDtl=(HashMap)allPageDtl.get("PUR_DTL");
    ArrayList pageList=new ArrayList();
    HashMap pageDtlMap=new HashMap();
    String fld_nme="",fld_ttl="",val_cond="",dflt_val="",fld_typ="",form_nme="",flg1="",lov_qry="";
    HashMap jsValPrp = new HashMap();
      pageList=(ArrayList)pageDtl.get("JSPRP");
      if(pageList!=null && pageList.size() >0){
      for(int j=0;j<pageList.size();j++){
      pageDtlMap=(HashMap)pageList.get(j);
      fld_nme=(String)pageDtlMap.get("fld_nme");
      dflt_val=(String)pageDtlMap.get("val_cond");
      jsValPrp.put(fld_nme, dflt_val);
      }}
 %>
 <tr>
  <td valign="top" class="hedPg">
  <table cellpadding="1" cellspacing="5"><tr><td valign="middle">
  <img src="../images/line.jpg" border="0" /></td><td valign="middle"> <span class="pgHed">Purchase Detail</span> </td>
</tr></table>
  </td>
  </tr>
  <%
  ArrayList errors = (ArrayList)request.getAttribute("errorList");
  if(errors!=null && errors.size() >0){%>
   <tr><td class="tdLayout" valign="top" height="18">
   <%for(int i=0;i<errors.size();i++){
   ArrayList errorObj =(ArrayList)errors.get(i);
     if(errorObj!=null && errorObj.size() >0){
     %>
     <div>
   <% for(int k=0;k<errorObj.size();k++){
   %>
   <label class="redLabel"> <%=errorObj.get(k)%></label>&nbsp;&nbsp;
   <%}%>
   </div>
   <%}}%>
   </td></tr>
  <%}%>
  <%String msg1 = (String)request.getAttribute("msg"); 
  if(msg1!=null){
  %>
  <tr><td class="tdLayout" valign="top">
   <label class="redLabel"><%=msg1%></label>
  </td></tr>
  <%}%>
  
  <tr>
  <td valign="top" class="hedPg">
  <table class="grid1">
  <tr>
    <th height="15"><div align="center"><strong>Pur Idn</strong></div></th>
    <th height="15"><div align="center"><strong>Cts</strong></div></th>
    <th height="15"><div align="center"><strong>Avg Price</strong></div></th>
    <th height="15"><div align="center"><strong>Avg disc </strong></div></th>
    <th height="15"><div align="center"><strong>Vlu </strong></div></th>
    <th height="15"><div align="center"><strong>Currency Vlu</strong></div></th>
     <%pageList=(ArrayList)pageDtl.get("SUMMARY");
      if(pageList!=null && pageList.size() >0){
      for(int j=0;j<pageList.size();j++){
    if(!prpDspBlocked.contains("CP")){%>
    <th height="15"><div align="center"><strong>Net Avg Price</strong></div></th>
    <th height="15"><div align="center"><strong>Net Avg disc </strong></div></th>
    <th height="15"><div align="center"><strong>Net Vlu </strong></div></th>
    <th height="15"><div align="center"><strong>Net Currency Vlu </strong></div></th>
    <%}%>
    <%}}%>
  </tr>
  <tr>
  <td align="right"><%=util.nvl((String)info.getValue("purIdn"))%></td>
  <td align="right"><%=util.nvl((String)avgDtl.get("cts"))%></td>
    <td align="right"><%=util.nvl((String)avgDtl.get("avg"))%></td>
      <td align="right"><%=util.nvl((String)avgDtl.get("avg_dis"))%></td>
        <td align="right"><%=util.nvl((String)avgDtl.get("vlu"))%></td>
          <td align="right"><%=util.nvl((String)avgDtl.get("inrvlu"))%></td>
      <%pageList=(ArrayList)pageDtl.get("SUMMARY");
      if(pageList!=null && pageList.size() >0){
      for(int j=0;j<pageList.size();j++){
      if(!prpDspBlocked.contains("CP")){%>
      <td align="right"><%=util.nvl((String)avgDtl.get("netavg"))%></td>
      <td align="right"><%=util.nvl((String)avgDtl.get("netavg_dis"))%></td>
      <td align="right"><%=util.nvl((String)avgDtl.get("netvlu"))%></td>
      <td align="right"><%=util.nvl((String)avgDtl.get("netinrvlu"))%></td>
      <%}%>
      <%}}%>
  </tr>
  </table>
  </td>
  </tr>
  
    <tr><td class="tdLayout" valign="top">
    <table><tr><td valign="top">
  <html:form action="<%=formAction%>" method="post">
  <html:hidden property="value(purIdn)" styleId="purIdn" />
    <table class="grid1" id="dataTable">

    <tr><th>Sr</th>
        <th>&nbsp;</th>
<%
        for(int j=0; j < daos.size(); j++) {
            UIFormsFields dao = (UIFormsFields)daos.get(j);
            String lFld = dao.getFORM_FLD();
            String fldTtl = util.nvl(dao.getDSP_TTL(), lFld);
        String isReq =util.nvl(dao.getREQ_YN());
%>
    <th>
    <% if(isReq.equals("Y")){%>
            <span class="redTxt">*</span>
          <%  }else{%>
          &nbsp;&nbsp;
          <%}%>
    <%=fldTtl%></th>
<%}%>
<!-- for loop for prp list -->
<%
if(vwPrpLst!=null && vwPrpLst.size()>0){
for(int i=0;i<vwPrpLst.size();i++){
 String lprp = (String)vwPrpLst.get(i);
%>
<th><%=lprp%></th>
<% } }%>

    <th>Action</th>
    </tr>
<%
    int colCnt = daos.size() + vwPrpLst.size() + 3 ;
   
    ArrayList dspOrder = new ArrayList();
    dspOrder.add("NEW");
    dspOrder.add("EXISTING"); 
    GenDAO lDao = null;
    String target="";
    for(int dsp=0; dsp < dspOrder.size(); dsp++) {
      lDao = null;
        int sr = 0 ;
        String msg = (String)dspOrder.get(dsp);
        int lmt = reqList.size();
        if(msg.equalsIgnoreCase("NEW"))
            lmt = addRec ;
        for(int i=0; i < lmt; i++) {
            int fldId = 0 ;
            fldId = i+1 ;
            String cbox = "";
            if(!(msg.equalsIgnoreCase("NEW"))) {
             lDao = (GenDAO)reqList.get(i);
             String lIdn = lDao.getIdn();
             fldId = Integer.parseInt(lIdn);
              target = "TR_"+fldId;
            }
            
        if(sr == 0) {%>
         <tr><td colspan="<%=colCnt%>" class="ttl"><%=msg%></td></tr>   
         <%if(!msg.equals("NEW"))%>
         <tr> <td>All</td><td>&nbsp; <input type="checkbox" name="check" id="check" onclick="CheckBOXALLForm('0',this,'upd_')"  </td><td colspan="<%=colCnt-2%>" ></td></tr> 
        <%}%>
      <tr id="<%=target%>">
            <td><%=++sr%></td>
            <td>&nbsp;
      <%
      if(!(msg.equalsIgnoreCase("NEW"))) {
        String cboxPrp = "value(upd_"+ fldId + ")" ;
        String cboxId = "upd_"+fldId;
        String onchg="setBGColorOnCHK(this,'"+target+"')";
      %>
        <html:checkbox property="<%=cboxPrp%>" styleId="<%=cboxId%>" onchange="<%=onchg%>" />
      <%}%>
            </td>
      <%
        for(int j=0; j < daos.size(); j++) {
            UIFormsFields dao = (UIFormsFields)daos.get(j);
            String lFld = dao.getFORM_FLD();
            String htmlFld = lFld + "_" +  fldId;
            String htmlFldID = lFld + "_" +  fldId;
               String fldNm = "value(" + htmlFld + ")";
            String fldTyp = util.nvl(dao.getFLD_TYP(), "T");
            String valCond = util.nvl(dao.getVAL_COND(), "");
            String fldSz = util.nvl(dao.getFLD_SZ(),"10");
            if(valCond.indexOf("~fldId") > -1)
                valCond = valCond.replaceAll("~fldId", Integer.toString(fldId));
         
        %>
        <td>
        <%
            if(fldTyp.equals("T")) {
            %>
                <html:text property="<%=fldNm%>"  size="<%=fldSz%>" onchange="<%=valCond%>" styleId="<%=htmlFldID%>"/>
            <% } 
            if((fldTyp.equals("S")) || (fldTyp.equals("L"))){
                String lovKey = lFld + "LOV";
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
                
                %>
                <html:select property="<%=fldNm%>" styleId="<%=htmlFldID%>">
                    <%for(int a=0; a < keys.size(); a++) {
                        String lKey = (String)keys.get(a);
                        String ldspVal = (String)vals.get(a);%>
                        
                        <html:option value="<%=lKey%>"><%=ldspVal%></html:option>
                      <%}  
                    %>
                </html:select>
                <%
            }
            if(fldTyp.equals("DT")){%>
            <div class="float">   <html:text property="<%=fldNm%>"  styleId="<%=htmlFldID%>" /></div>
            <div class="float">     <a href="#"  onClick="setYears(1900, <%=info.getCurrentYear()%>);showCalender(this, '<%=htmlFldID%>');">
                <img src="<%=info.getReqUrl()%>/images/calender.png"></a></div>
            <% }
            %>
        </td>    
        <%}%>
        <% if(vwPrpLst!=null && vwPrpLst.size()>0){ 
        for(int k=0;k<vwPrpLst.size();k++){
        String lprp = (String)vwPrpLst.get(k);
        String typ = (String)mprp.get(lprp+"T");
        ArrayList prpLst = (ArrayList)prp.get(lprp+"V");
        String fldNm="value("+lprp+"_"+fldId+")";
        String idn = lprp+"_"+fldId;
        String addValCond=util.nvl((String)jsValPrp.get(lprp));
        if(!addValCond.equals(""))
        addValCond=addValCond.replace("~purIdn~", util.nvl((String)info.getValue("purIdn")));
          addValCond=addValCond.replace("~fldIdn~",String.valueOf(fldId));
           addValCond=addValCond.replace("~purDtlIdn~",String.valueOf(fldId));
      if(msg.equalsIgnoreCase("NEW")) {
      addValCond = "calRteForNewOnPur(this,'"+util.nvl((String)info.getValue("purIdn"))+"','"+String.valueOf(fldId)+"')";
      }
        if(typ.equals("C")){
    if(prpLst!=null && prpLst.size()>0){
        %>
         <td>
     <html:select property="<%=fldNm%>" onchange="<%=addValCond%>">
     <%for(int l=0;l<prpLst.size();l++){
     String val = (String)prpLst.get(l);
     %>
     <html:option value="<%=val%>"><%=val%></html:option>
     <%}%>
     </html:select></td>
        <% }}else{  
       
        %>
         <td>
    <html:text property="<%=fldNm%>" onchange="<%=addValCond%>" styleId="<%=idn%>"/>
    </td>
        <% } %>
        
        <% } } %>
        
        <!-- for loop for prp list -->
        
           <% if(msg.equalsIgnoreCase("NEW")) {%>
        <td>&nbsp;</td>
        <%} else {
            String link1= "/purchase/purchaseDtlAction.do?method=delete&purDtlIdn="+fldId ;
            String link2="purchaseDtlAction.do?method=loadPrp&purDtlIdn="+fldId+"&purIdn="+info.getValue("purIdn") ;
            String onclick = "return setDeleteConfirm('"+link1+"')";
        %>

  <td><html:link page="<%=link1%>">Delete</html:link> &nbsp;|&nbsp;
        <a href="<%=link2%>" id="LNK_<%=fldId%>" onclick="loadASSFNL('<%=target%>','LNK_<%=fldId%>')"  target="SC">Add Property</a>

     </td>

       
        <%}%>
       
        </tr>  
    <%}
%>    
  
    <tr><td colspan="<%=colCnt%>" align="center">
<% if(msg.equalsIgnoreCase("NEW")) {%>    
    <html:submit property="addnew" value="Add New"  styleClass="submit"/>
<% } else { if(reqList.size() > 0) {%>
 <!--<html:submit property="tfrToMkt" value="Transfer to stock" onclick="return validateUpdate()"  styleClass="submit"/>&nbsp;
    <html:submit property="modify" value="Save Changes" onclick="return validateUpdate()"  styleClass="submit"/>
    &nbsp;<html:reset property="reset" value="Reset" styleClass="submit"/>-->
  <%  pageList=(ArrayList)pageDtl.get("BUTTON");
      if(pageList!=null && pageList.size() >0){
      for(int j=0;j<pageList.size();j++){
      pageDtlMap=(HashMap)pageList.get(j);
      fld_nme=(String)pageDtlMap.get("fld_nme");
      val_cond=(String)pageDtlMap.get("val_cond");
      fld_ttl=(String)pageDtlMap.get("fld_ttl");
      val_cond=(String)pageDtlMap.get("val_cond");
      fld_typ=(String)pageDtlMap.get("fld_typ");
      flg1=(String)pageDtlMap.get("flg1");
      if(fld_typ.equals("S")){ %>
<html:submit property="<%=fld_nme%>" value="<%=fld_ttl%>"  onclick="<%=val_cond%>" styleClass="submit" />&nbsp;
<%}else if(fld_typ.equals("R")){%>
<html:reset property="<%=fld_nme%>" value="<%=fld_ttl%>" styleClass="submit"/>
<%}else if(fld_typ.equals("HB")){%>
<input type="button" id="<%=fld_nme%>" onclick="<%=val_cond%>"  value="<%=fld_ttl%>"  class="submit"/>
<%}}}
}}%>
<!--<input type="button" id="summary" class="submit" onclick="purlab('summary','SC','PUR');"  value="Upload"/>-->
    </td></tr>
        
<%  }%>
    
    </table>
    
    </html:form></td><td>
    <iframe name="addPrp" height="1000" width="500" frameborder="0">

     </iframe>
    
    </td></tr></table>
    </td></tr>
    <tr>
    <td><jsp:include page="/footer.jsp" /> </td>
    </tr>
    </table>
 <%@include file="../calendar.jsp"%>
</body>
</html>