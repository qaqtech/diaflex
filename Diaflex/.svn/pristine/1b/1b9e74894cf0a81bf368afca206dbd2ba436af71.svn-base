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
     <title>Rough Purchase Detail</title>
     <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/bse.css?v=<%=info.getJsVersion()%>"/>
     <link href="<%=info.getReqUrl()%>/auto-search/auto-search.css?v=<%=info.getJsVersion()%>"
                rel="stylesheet" type="text/css"/>
     <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/style.css?v=<%=info.getJsVersion()%>"/>
     <script language="javaScript" type="text/javascript" src="<%=info.getReqUrl()%>/calendar/calendar.js"></script>
     <link href="<%=info.getReqUrl()%>/calendar/calendar.css" rel="stylesheet" type="text/css" />
         <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/ajax.js?v=<%=info.getJsVersion()%> " > </script>
  <script type="text/javascript" src="<%=info.getReqUrl()%>/PopScripts/popup.js?v=<%=info.getJsVersion()%> " > </script>
   <script src="<%=info.getReqUrl()%>/PopScripts/jquery-1.2.6.min.js" type="text/javascript"></script>
   <script src="<%=info.getReqUrl()%>/PopScripts/jquery-1.11.3.min.js" type="text/javascript"></script>
   <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/general.css?v=<%=info.getJsVersion()%>"/>
    <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/bse.js?v=<%=info.getJsVersion()%> " > </script>
    <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/box.js?v=<%=info.getJsVersion()%> " > </script>
     <script src="<%=info.getReqUrl()%>/auto-search/auto-search.js?v=<%=info.getJsVersion()%>" type="text/javascript"></script>
           <script src="<%=info.getReqUrl()%>/auto-search/autoajaxjs.js?v=<%=info.getJsVersion()%>" type="text/javascript"></script>
  </head>
        <%String logId=String.valueOf(info.getLogId());
        String onfocus="cook('"+logId+"')";
         String suggScript ="";
         String scriptForHideShow="";
        %>
 <body onfocus="<%=onfocus%>" onload="MM_preloadImages('images/faunalogo.png')" topmargin="0" leftmargin="0" rightmargin="0" bottommargin="0">
<input type="hidden" id="reqUrl" name="reqUrl" value="<%=info.getReqUrl()%>"/>
<div id="backgroundPopup"></div>
<div id="popupContactASSFNL">
<table class="popUpTb">
 <tr>
 <td height="5%" id="close">&nbsp;&nbsp;&nbsp;<html:button property="value(close)" styleClass="submit"  onclick="MKEmptyPopup('SC');disablePopupASSFNL();" value="Close"  /> </td>
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
  Integer incCount = (Integer)request.getAttribute("lstCnt");
  incCount++;
  String lotDsc = (String)request.getAttribute("lotDsc");
   ArrayList params = new ArrayList();
    ResultSet rs = null;
    String formName = "roughPurDtlForm";
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
    String formAction = "/rough/roughPurDtlAction.do?method=save";
    HashMap allPageDtl= (info.getPageDetails() == null)?new HashMap():(HashMap)info.getPageDetails();
    HashMap pageDtl=(HashMap)allPageDtl.get("PUR_DTL");
    ArrayList pageList=new ArrayList();
    HashMap pageDtlMap=new HashMap();
    String fld_nme="",fld_ttl="",val_cond="",dflt_val="",fld_typ="",form_nme="",flg1="",lov_qry="";
 %>
 <tr>
  <td valign="top" class="hedPg">
  <table cellpadding="1" cellspacing="5"><tr><td valign="middle">
  <img src="../images/line.jpg" border="0" /></td><td valign="middle"> <span class="pgHed">Rough Purchase Detail</span> </td>
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
  <%
  String ttlcts = util.nvl((String)request.getAttribute("ttlCts"),"0");
  String calCts = util.nvl((String)request.getAttribute("calCts"),"0");
  double ttlCtsd = Double.parseDouble(ttlcts);
  double calCtsd = Double.parseDouble(calCts);
  %>
   <tr><td class="tdLayout" valign="top">
   Lot Carat : <label id="TTLCTSMT" class="redLabel"><%=ttlcts%></label> &nbsp;&nbsp; varified Carat :
   <label id="ttlcts" class="redLabel"><%=calCts%></label>
    &nbsp;&nbsp;New Carat :  <label id="addcts" class="redLabel"></label>
   </td></tr>
  <%%>
  
    <tr><td class="tdLayout" valign="top">
    <table><tr><td valign="top">
      <html:form action="<%=formAction%>" method="post">
 <html:hidden property="value(lotDsc)"  styleId="lotDsc" />
  <html:hidden property="value(purIdn)"  styleId="purIdn" />
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
    <th>Action</th>
    </tr>
<%
    int colCnt = daos.size() + 3;
   
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
        <html:checkbox property="<%=cboxPrp%>" styleId="<%=cboxId%>"  onchange="<%=onchg%>" />
      <%}%>
            </td>
      <%
        for(int j=0; j < daos.size(); j++) {
            UIFormsFields dao = (UIFormsFields)daos.get(j);
            String lFld = dao.getFORM_FLD();
            String htmlFld = lFld + "_" +  fldId;
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
               if((msg.equalsIgnoreCase("NEW"))) {
               String val ="";
               if(lFld.equals("ref_idn"))
                val=lotDsc+"_"+incCount++;
                 
                   %>
             <html:text property="<%=fldNm%>" size="<%=fldSz%>" value="<%=val%>" onchange="<%=valCond%>" styleId="<%=htmlFld%>"/>

              <% }else{ %>
                <html:text property="<%=fldNm%>" size="<%=fldSz%>"  onchange="<%=valCond%>" styleId="<%=htmlFld%>"/>
            <%}}
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
                <html:select property="<%=fldNm%>">
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
            <div class="float">   <html:text property="<%=fldNm%>"   styleId="<%=htmlFld%>" /></div>
            <div class="float">     <a href="#"  onClick="setYears(1900, <%=info.getCurrentYear()%>);showCalender(this, '<%=htmlFld%>');">
                <img src="<%=info.getReqUrl()%>/images/calender.png"></a></div>
            <% }
             if(fldTyp.equals("SM")){
              String lovQ = dao.getLOV_QRY();
             String inputTexId = lovQ+"_"+fldId;
            String hiddenId = "hidden_select_"+fldId;
            String smIdn="";
             if(!(msg.equalsIgnoreCase("NEW"))) {
             String        lFlds    = dao.getFORM_FLD();
              smIdn = (String)lDao.getValue(lFlds);
              }
           
             suggScript=suggScript+" $('#"+inputTexId+"').on('keyup',function() {  "+
       " var timer = setTimeout(autoSearchData('"+lovQ+"','"+inputTexId+"',false,'value'), 300);  });"+
       " $('#"+inputTexId+"').keyup();";
            %>         
             <div>
           <html:hidden property="<%=fldNm%>" styleId="<%=hiddenId%>" />
            <input type="text"  name="" id="<%=inputTexId%>" class="magicsearch " autocomplete="off"  style="width:200px;height:20px;"  value="<%=smIdn%>" placeholder="" data-value="<%=fldId%>" data-id="<%=smIdn%>"></input>
         
             </div>
           <% } %>
          
        </td>    
        <%}%>
           <% if(msg.equalsIgnoreCase("NEW")) {%>
        <td>&nbsp;</td>
        <%} else {
            String link1= "/rough/roughPurDtlAction.do?method=delete&purDtlIdn="+fldId ;
            String link2="roughPurDtlAction.do?method=loadPrp&purDtlIdn="+fldId+"&purIdn="+info.getValue("purIdn") ;
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
 <html:submit property="tfrToMkt" value="Transfer to stock" onclick="return validateUpdate()"  styleClass="submit"  />&nbsp;
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
      if(fld_typ.equals("S")){ 
      
      %>
      
<html:submit property="<%=fld_nme%>" value="<%=fld_ttl%>"  onclick="<%=val_cond%>" styleClass="submit"  />&nbsp;
<%}else if(fld_typ.equals("R")){%>
<html:reset property="<%=fld_nme%>" value="<%=fld_ttl%>" styleClass="submit"/>
<%}else if(fld_typ.equals("HB")){%>
<input type="button" id="<%=fld_nme%>" onclick="<%=val_cond%>"  value="<%=fld_ttl%>"  class="submit"/>
<%}else if(fld_typ.equals("cpc") && ttlCtsd==calCtsd){
 val_cond=val_cond.replaceFirst("~purIdCp~", util.nvl((String)info.getValue("purIdn")));
 
%>
<input type="button" id="<%=fld_nme%>" onclick="<%=val_cond%>"  value="<%=fld_ttl%>"  class="submit" styleId="calCp"/>
<%
}
if(fld_typ.equals("cpc")){
 scriptForHideShow = "<script language='javascript'>$(document).ready(function(){$('#tfrToMkt').hide(); })</script>";
}
}}
}}%>
<!--<input type="button" id="summary" class="submit" onclick="purlab('summary','SC','PUR');"  value="Upload"/>-->
    </td></tr>
        
<%  }%>
    
    </table>
    </html:form>
</td><td>
    <iframe name="addPrp" height="1000" width="500" frameborder="0">

     </iframe>
    
    </td></tr></table>
    </td></tr>
    <tr>
    <td><jsp:include page="/footer.jsp" /> </td>
    </tr>
    </table>
    <%=scriptForHideShow%>
    <%String multiSugg="<script language=\"javascript\">$( document ).ready(function() {"+"   "+suggScript+ " });</script>";%>
 <%=multiSugg%>
 
 <script language="javascript">
 
 $(document).on('click','.magicsearch-box ul li',function() {
 
       var rowId=$(this).closest("td").find("input.magicsearch").attr("data-value");
       var selectedValue=$(this).closest("td").find("input.magicsearch").attr("data-id");
      $("#hidden_select_"+rowId).val(selectedValue);
        });
        
         $(document).on('keydown','input.magicsearch',function() {
 
       var rowId=$(this).attr("data-value");
       $(this).closest("td").find("#hidden_select_"+rowId).val("");
        });
        
       
 </script>

    
 <%@include file="../calendar.jsp"%>
</body>
</html>