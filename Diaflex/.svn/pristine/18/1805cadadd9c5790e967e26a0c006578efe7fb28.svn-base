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
    <title>Purchase </title>
    <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/bse.css?v=<%=info.getJsVersion()%>"/>
    <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/style.css?v=<%=info.getJsVersion()%>"/>
    <script language="javaScript" type="text/javascript" src="<%=info.getReqUrl()%>/calendar/calendar.js"></script>
    <link href="<%=info.getReqUrl()%>/calendar/calendar.css" rel="stylesheet" type="text/css" />
      <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/ajax.js?v=<%=info.getJsVersion()%>"></script> 
       <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/bse.js?v=<%=info.getJsVersion()%>"></script> 
      <script src="<%=info.getReqUrl()%>/PopScripts/jquery-1.2.6.min.js" type="text/javascript"></script>
      <script src="<%=info.getReqUrl()%>/jqueryScript/jqueryScript.js?v=<%=info.getJsVersion()%>" type="text/javascript"></script>
      <script src="<%=info.getReqUrl()%>/jqueryScript/jquery.min.js" type="text/javascript"></script>  
  </head>
        <%String logId=String.valueOf(info.getLogId());
        String onfocus="cook('"+logId+"')";
        %>
 <body onfocus="<%=onfocus%>" onload="MM_preloadImages('images/faunalogo.png')"  onkeypress="return disableEnterKey(event);"  topmargin="0" leftmargin="0" rightmargin="0" bottommargin="0">
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
   int accessidn=0;
   HashMap dbinfo = info.getDmbsInfoLst();
String cnt = (String)dbinfo.get("CNT");
  String repPath = (String)dbinfo.get("REP_PATH");
  String msg = (String)request.getAttribute("fileMsg");
  if(msg!=null){
  String url = (String)request.getAttribute("url");

  %>
   <tr>
  <td valign="top" class="hedPg">
  <div>
  <span class="redLabel">
  <%=msg%></span>
  </div>
  </td></tr>
  <%}%>
  
  
  <%
   ArrayList params = new ArrayList();
    ResultSet rs = null;
    
    String formName = "mpurForm";
    DBUtil dbutil = new DBUtil();
    DBMgr db = new DBMgr(); 
    db.setCon(info.getCon());
    dbutil.setDb(db);
    dbutil.setInfo(info);
    db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));db.setDbUsr(info.getDbUsr());
    dbutil.setLogApplNm(info.getLoApplNm());
    String method = util.nvl((String)request.getAttribute("method"));
    HashMap allFields = (HashMap)info.getFormFields();
    HashMap formFields = (allFields.get(formName) == null)?dbutil.getFormFields(formName):(HashMap)allFields.get(formName);
    UIForms uiForms = (UIForms)formFields.get("DAOS");
    ArrayList daos = uiForms.getFields();
    HashMap lovFormFlds = new HashMap();
    String pgTtl = uiForms.getFORM_TTL();
    String style = "display:none;";
    String color="color:#ffffff";
    int addRec = Integer.parseInt(util.nvl(uiForms.getREC(),"10"));
    HashMap allPageDtl= (info.getPageDetails() == null)?new HashMap():(HashMap)info.getPageDetails();
    HashMap pageDtl=(HashMap)allPageDtl.get("PUR_FORM");
    ArrayList pageList=new ArrayList();
    HashMap pageDtlMap=new HashMap();
    String fld_nme="",fld_ttl="",val_cond="",dflt_val="",fld_typ="",form_nme="",flg1="",lov_qry="";
    String formAction = "/purchase/purchaseAction.do?method=save";
    String mixForm="";
    pageList= ((ArrayList)pageDtl.get("MIXFORM") == null)?new ArrayList():(ArrayList)pageDtl.get("MIXFORM");
    if(pageList!=null && pageList.size() >0){
    for(int j=0;j<pageList.size();j++){
    mixForm="Y";
    }
    }
 %>
 <tr>
  <td valign="top" class="hedPg">
  <table cellpadding="1" cellspacing="5"><tr><td valign="middle">
  <img src="../images/line.jpg" border="0" /></td><td valign="middle"> <span class="pgHed"><%=pgTtl%></span> </td>
</tr></table>
  </td>
  </tr>
   <%
  ArrayList errors = (ArrayList)request.getAttribute("errors");
  if(errors!=null && errors.size() >0){%>
   <tr><td class="tdLayout" valign="top">
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
    <tr><td class="tdLayout" valign="top">
  <html:form action="<%=formAction%>" method="post">
  <html:hidden property="value(pur_idn)" />
  <table>
  <tr>
  <!--<td><span class="txtLink" id="PUR_TAB" style="<%=color%>" onclick="showHideDiv('.pktDtl','PUR',this)">Purchase Form</span></td>
  <td><span class="txtLink" id="SEARCH_TAB" style="" onclick="showHideDiv('.pktDtl','SEARCH',this)">Purchase Search</span></td>-->
    <%pageList=(ArrayList)pageDtl.get("TABLINK");
            if(pageList!=null && pageList.size() >0){
            for(int j=0;j<pageList.size();j++){
            pageDtlMap=(HashMap)pageList.get(j);
            fld_nme=(String)pageDtlMap.get("fld_nme");
            fld_ttl=(String)pageDtlMap.get("fld_ttl");
            fld_typ=(String)pageDtlMap.get("fld_typ");
            dflt_val=(String)pageDtlMap.get("dflt_val");
            val_cond=(String)pageDtlMap.get("val_cond"); %>
<td><span class="txtLink" id="<%=fld_nme%>_TAB" style="<%=fld_typ%>" onclick="showHideDiv('.pktDtl','<%=fld_nme%>',this)"><%=fld_ttl%></span></td>
            <%}}%>
  </tr>
  </table>
    <div id="PUR" class="pktDtl"> 
    <table width="70%" class="grid1" cellpadding="1"  cellspacing="1">
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
            String fldSz = util.nvl(dao.getFLD_SZ(),"10");            
            String valCond = util.nvl(dao.getVAL_COND(), "");
            String lovQ = util.nvl(dao.getLOV_QRY());
            String isReq =util.nvl(dao.getREQ_YN());
          if(count==2){
             count=0;
             
             %></tr><tr>
          
           <% }
          count++;
           %>
        
            <td> <%
            if(isReq.equals("Y")){%>
            <span class="redTxt">*</span>
          <%  }
            %><%=fldTtl%></td>
            <td>
   <%
           
            if((fldTyp.equals("T")) || (fldTyp.equals("FT")) || (fldTyp.equals("DT"))) {
            if(fldTyp.equals("DT")){
            %>
            <html:text property="<%=fldNm%>" styleId="<%=htmlFld%>" onblur="<%=valCond%>" size="<%=fldSz%>"/>
            <%}else{%>
            
             <html:text property="<%=fldNm%>" styleId="<%=htmlFld%>"  size="<%=fldSz%>"/>
           <% }} 
            if(fldTyp.equals("TA")) {
            %>
                <html:textarea property="<%=fldNm%>" styleId="<%=htmlFld%>" cols="<%=fldSz%>"/>
            <%}
            if(fldTyp.equals("P")) {
            %>
                <html:password property="<%=fldNm%>" styleId="<%=htmlFld%>"  size="<%=fldSz%>"  />
                
            <%} 
            if(fldTyp.equals("DT")) {%>
                <a href="#"  onClick="setYears(2011, <%=info.getCurrentYear()%>);showCalender(this, '<%=htmlFld%>');">
                <img src="<%=info.getReqUrl()%>/images/calender.png"></a>

            <%}
            
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
              String keyStr = "doCompletion('"+ dspFld +"', '" + dspFldPop + "', '"+lovQ+"', event)";
              String keyStrMobile = "doCompletionMobile('"+ dspFld +"', '" + dspFldPop + "', '"+lovQ+"')";              
              String setDown = "setDown('"+dspFldPop+"', '"+htmlFld+"', '"+ dspFld +"',event)";
              //onchange="resetVal('htmlFld','');
              if(valCond.length() > 0)
                valCond = valCond + "\"" ;
            %>
              <%if(method.equals("edit")){%>
                 <html:text property="<%=dspFldDis%>" disabled="true"/><%}%>
                  <input type="text" name="<%=dspFldV%>" id="<%=dspFld%>" class="sugBox"
                  onKeyUp="<%=keyStr%>"   onKeyDown="<%=setDown%>" value="" <%=valCond%> />
                <img src="../images/addrow.jpg" width="17" height="17" title="Click To get Names" onclick="<%=keyStrMobile%>">
                <html:hidden property="<%=fldNm%>" styleId="<%=htmlFld%>"/>
             
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
//                String lovQ = dao.getLOV_QRY(); //util.nvl((String)formFlds.get(lovKey));
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
            
   %>
            </td>
           
    <%
    }    
  %>
  </tr>
   <tr>
   <td colspan="4">
 <%if(!method.equals("edit")){%>
  <html:submit property="addnew" value="Add New" styleClass="submit"/>
  <%}%>
  <%if(method.equals("edit")){%>
  <html:submit property="modify" value="Update" styleClass="submit"/>
  <%}%>
  <html:submit property="reset" value="Reset" styleClass="submit"/>
   </td>
   </tr>
  </table>
    </div>
    <div id="SEARCH" style="display:none;"  class="pktDtl">
    <table class="grid1" cellpadding="1"  cellspacing="1">
    <tr>
          <td>Vendor</td>
      <td nowrap="nowrap">
    <input type="text" id="nme" name="nme" autocomplete="off" class="sugBox"
      onKeyUp="doCompletion('nme', 'nmePop', '../ajaxAction.do?1=1&UsrTyp=VENDOR,VENDER,BUYER', event)" 
      onKeyDown="setDown('nmePop', 'nmeID', 'nme',event)"/>
 <img src="../images/addrow.jpg" width="17" height="17" title="Click To get Names" onclick="doCompletionMobile('nme', 'nmePop', '../ajaxAction.do?1=1&UsrTyp=VENDOR,VENDER,BUYER')">
 <input type="hidden" name="nmeID" id="nmeID" value="">
  <div style="position: absolute;">
      <select id="nmePop" name="nmePop"
        style="display:none;300px;" 
        class="sugBoxDiv"
        onKeyDown="setDown('nmePop', 'nmeID', 'nme',event)" 
        onDblClick="setVal('nmePop', 'nmeID', 'nme', event);hideObj('nmePop')" 
        onClick="setVal('nmePop', 'nmeID', 'nme', event);" 
        size="10">
      </select>
</div>
  </td>
    </tr>
   <tr>
   <td>Type:</td>
   <td>
             <html:select property="value(Typ)" styleId="Typ" name="mpurForm" >
             <html:option value="" >--select--</html:option>
             <!--<html:option value="B">Buyer</html:option>
             <html:option value="R">Review</html:option>-->
            <%pageList=(ArrayList)pageDtl.get("TYP");
            if(pageList!=null && pageList.size() >0){
            for(int j=0;j<pageList.size();j++){
            pageDtlMap=(HashMap)pageList.get(j);
            fld_nme=(String)pageDtlMap.get("fld_nme");
            fld_ttl=(String)pageDtlMap.get("fld_ttl");
            fld_typ=(String)pageDtlMap.get("fld_typ");
            dflt_val=(String)pageDtlMap.get("dflt_val");
            val_cond=(String)pageDtlMap.get("val_cond"); %>
            <html:option value="<%=dflt_val%>" ><%=fld_ttl%></html:option>
            <%}}%>
            </html:select>
            </td>
   </tr>
   <tr>
   <%pageList=(ArrayList)pageDtl.get("PKT_TYP");
            if(pageList!=null && pageList.size() >0){%>
           <td>Pkt Type:</td>
           <td>
             <html:select property="value(pkt_ty)" styleId="pkt_ty" name="mpurForm" >
             <html:option value="" >--select--</html:option>
             <!--<html:option value="B">Buyer</html:option>
             <html:option value="R">Review</html:option>-->
            <%for(int j=0;j<pageList.size();j++){
            pageDtlMap=(HashMap)pageList.get(j);
            fld_nme=(String)pageDtlMap.get("fld_nme");
            fld_ttl=(String)pageDtlMap.get("fld_ttl");
            fld_typ=(String)pageDtlMap.get("fld_typ");
            dflt_val=(String)pageDtlMap.get("dflt_val");
            val_cond=(String)pageDtlMap.get("val_cond"); %>
            <html:option value="<%=dflt_val%>" ><%=fld_ttl%></html:option>
            <%}%>
            </html:select>
            </td>
            <%}%>
   </tr>
     <%pageList=(ArrayList)pageDtl.get("CUR_TYP");
            if(pageList!=null && pageList.size() >0){%>
       <tr>    <td>CUR :</td>
           <td>
             <html:select property="value(cur_ty)" styleId="cur_ty" name="mpurForm" >
             <html:option value="" >--select--</html:option>
             <!--<html:option value="B">Buyer</html:option>
             <html:option value="R">Review</html:option>-->
            <%for(int j=0;j<pageList.size();j++){
            pageDtlMap=(HashMap)pageList.get(j);
            fld_nme=(String)pageDtlMap.get("fld_nme");
            fld_ttl=(String)pageDtlMap.get("fld_ttl");
            fld_typ=(String)pageDtlMap.get("fld_typ");
            dflt_val=(String)pageDtlMap.get("dflt_val");
            val_cond=(String)pageDtlMap.get("val_cond"); %>
            <html:option value="<%=dflt_val%>" ><%=fld_ttl%></html:option>
            <%}%>
            </html:select>
            </td></tr>
            <%}%>
      <tr>
   <td>Purchase Idn</td>
   <td><html:textarea property="value(searchpur_idn)" name="mpurForm" styleId="searchpur_idn"/></td>
   </tr>
   <tr>
   <td colspan="2">
   <table>
   <tr><td>Pur Date From : </td><td>
    <html:text property="value(dtefr)" styleClass="txtStyle"  styleId="dtefr"  size="12"  /> <a href="#" style="margin-top:5px;"  onClick="setYears(1900, '<%=info.getCurrentYear()%>' );showCalender(this, 'dtefr');">
                <img src="<%=info.getReqUrl()%>/images/calender.png"></a></td>
                <td>Date To : </td><td>
    <html:text property="value(dteto)" styleClass="txtStyle"  styleId="dteto"  size="12"  /> <a href="#"  onClick="setYears(1900, '<%=info.getCurrentYear()%>' );showCalender(this, 'dteto');">
                <img src="<%=info.getReqUrl()%>/images/calender.png"></a></td>
   </tr>
   </table>
   </td>
   </tr>
   <tr>
   <td colspan="2">
   <html:submit property="value(submit)" value="Fetch" styleClass="submit"/>
   </td>
   </tr>
    </table>
    </div>
    </html:form></td></tr>
    
    <tr><td class="tdLayout" valign="top">
     <%
  
    ArrayList list = (ArrayList)request.getAttribute("purchageList");
    if(list.size() > 0) {
  %>  
   <label class="pgTtl">Search List</label>
 
   <table cellpadding="2" cellspacing="2"><tr><td valign="top">
     <html:form action="/purchase/purchaseAction.do?method=loadFile" method="post" enctype="multipart/form-data">
     <html:hidden property="value(loadFileIdn)" styleId="loadFileIdn" />
  <table class="grid1">
  <tr><td>Sr</td>
    <td>Pur Id</td>
  <%
    for(int j=0; j < daos.size(); j++) {
            UIFormsFields dao = (UIFormsFields)daos.get(j);
            String lFld = dao.getFORM_FLD();
            String fldTtl = util.nvl(dao.getDSP_TTL(), lFld);%>
            
            <td><%=fldTtl%></td>
    <%}
  %>  
  <td>Upload File </td>
    <td>Action  </td>
  </tr>
 <%
    for(int i=0; i < list.size(); i++) {%>
        <tr>
        <td><%=(i+1)%></td>
    <%
         GenDAO nmeRel = (GenDAO)list.get(i);
        String lIdn = nmeRel.getIdn();
       String fileFld ="value("+lIdn+")";
       String lot_dsc= util.nvl((String)nmeRel.getValue("lot_dsc"));
       String flg= util.nvl((String)nmeRel.getValue("flg"));
       String onclick = "uploadPurfile("+lIdn+")";
        String edtLnk = "<a href=\""+ info.getReqUrl() + "/purchase/purchaseAction.do?method=edit&purIdn="+lIdn+"\" >Edit</a>";
        String addLnk = "<a href=\""+ info.getReqUrl() + "/purchase/purchaseDtlAction.do?method=load&mixForm="+mixForm+"&flg="+flg+"&purIdn="+lIdn+"&lotDsc="+lot_dsc+"\" >Add</a>";
//         String delLnk = "<a href=\""+ info.getReqUrl() + "/purchase/purchaseAction.do?method=delete&purIdn="+lIdn+"\" >Del</a>";
         String report = "<a href="+repPath+"/reports/rwservlet?"+cnt+"&report="+info.getRportUrl()+"\\reports\\purchase_report.jsp&p_access="+accessidn+"&p_pur_id="+lIdn+" target=\"_blank\">Report</a>";
       
        String link = info.getReqUrl() + "/purchase/purchaseAction.do?method=delete&purIdn="+lIdn;
        String onclickdel = "return setDeleteConfirm('"+link+"')";
        String delLnk = "<a href=\""+link +" \" onclick=\""+onclickdel+"  \">Del</a>";
        String authlink = info.getReqUrl() + "/purchase/purchaseAction.do?method=authenticate&purIdn="+lIdn;
        String onclickauth = "return setConfirm('"+authlink+"','Are you sure to Authenticate: ?','_self')";
        String authLnk = "<a href=\""+authlink +" \" onclick=\""+onclickauth+"  \">Authenticate</a>";
         String poreport = "<a href="+repPath+"/reports/rwservlet?"+cnt+"&report="+info.getRportUrl()+"\\reports\\po_report.jsp&p_access="+accessidn+"&p_pur_id="+lIdn+" target=\"_blank\">PO Report</a>";
       String poDtlreport = "<a href="+repPath+"/reports/rwservlet?"+cnt+"&report="+info.getRportUrl()+"\\reports\\po_report1_details.jsp&p_access="+accessidn+"&p_pur_id="+lIdn+" target=\"_blank\">PO Dtl Report</a>";
       
        
        %>
        <td><%=lIdn%></td>
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
        <td><html:file property="<%=fileFld%>" name="mpurForm" /> <html:submit property="value(submit)" onclick="<%=onclick%>" styleClass="submit" /> </td>
        <td nowrap="nowrap">
       <!--<%=edtLnk%>&nbsp;|&nbsp;<%=addLnk%>&nbsp;|&nbsp;<%=delLnk%>&nbsp;|&nbsp;<%=report%>-->
        <%pageList=(ArrayList)pageDtl.get("ACTION");
            if(pageList!=null && pageList.size() >0){
            for(int j=0;j<pageList.size();j++){
            pageDtlMap=(HashMap)pageList.get(j);
            fld_ttl=(String)pageDtlMap.get("fld_ttl");
            dflt_val=(String)pageDtlMap.get("dflt_val");
            dflt_val=dflt_val.replaceAll("EDIT",edtLnk);
            dflt_val=dflt_val.replaceAll("ADDLNK",addLnk);
            dflt_val=dflt_val.replaceAll("DELLNK",delLnk);
            dflt_val=dflt_val.replaceAll("REPORT",report);
            dflt_val=dflt_val.replaceAll("AUTH",authLnk);
            dflt_val=dflt_val.replaceAll("poreport",poreport);
             if(fld_ttl.equals("podtlreport")){%>
               |&nbsp;<%=poDtlreport%>
           <%}else
             if(fld_ttl.equals("poreport")){%>
               |&nbsp;<%=poreport%>
           <%} else if(fld_ttl.equals("Report")){%>
            |&nbsp;<%=report%>
            <%}else{%>
            <%=dflt_val%>
            <%}}}%>
            
        </td>
     </tr>   
  <%}
  %>
  </table>
  </html:form>
  </td><td>
  <iframe name="roleFrame" width="400" height="300" frameborder="0">
  
  </iframe>
  </td></tr></table>
  <% 
     }
  %>
    
    </td></tr>
    <tr>
    <td><jsp:include page="/footer.jsp" /> </td>
    </tr>
    </table>
 <%@include file="../calendar.jsp"%>
</body>
</html>