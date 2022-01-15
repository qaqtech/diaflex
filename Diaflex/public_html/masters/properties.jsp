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
    <title>Properties</title>
  <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/bse.css?v=<%=info.getJsVersion()%>"/>
    <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/style.css?v=<%=info.getJsVersion()%>"/>
    <script language="javaScript" type="text/javascript" src="<%=info.getReqUrl()%>/scripts/bse.js?v=<%=info.getJsVersion()%>"></script>
     <script language="javaScript" type="text/javascript" src="<%=info.getReqUrl()%>/calendar/calendar.js"></script>
   <link href="<%=info.getReqUrl()%>/calendar/calendar.css" rel="stylesheet" type="text/css" />

  </head>
        <%String logId=String.valueOf(info.getLogId());
        String onfocus="cook('"+logId+"')";
        %>
 <body onfocus="<%=onfocus%>" onload="MM_preloadImages('images/faunalogo.png')" topmargin="0" leftmargin="0" rightmargin="0" bottommargin="0">
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
    String formName = "prpfrm";
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
    ArrayList reqList = (ArrayList)request.getAttribute(formName);
    String pgTtl = util.nvl(uiForms.getFORM_TTL());
    String keyNme = util.nvl((String)info.getValue("mprp"));
    if(pgTtl.indexOf("~") > -1)
      pgTtl = pgTtl.replaceFirst("~nme", keyNme);
      
    dbutil.SOP(" View "+ formName + " : fields = " + daos.size());
    int addRec = Integer.parseInt(util.nvl(uiForms.getREC(),"10"));
    String formAction = "/masters/property.do?method=save";
    HashMap allPageDtl= (info.getPageDetails() == null)?new HashMap():(HashMap)info.getPageDetails();
    HashMap pageDtl=(HashMap)allPageDtl.get("PRP_FORM");
    ArrayList pageList=new ArrayList();
    HashMap pageDtlMap=new HashMap();
    String fld_nme="",fld_ttl="",val_cond="",dflt_val="",fld_typ="",form_nme="",flg1="",lov_qry="";
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
  <html:hidden property="value(mprp)" />
    <table class="grid1">

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
            
    for(int dsp=0; dsp < dspOrder.size(); dsp++) {
      lDao = null;
        int sr = 0 ;
        String msg = (String)dspOrder.get(dsp);
        int lmt = reqList.size();
        if(msg.equalsIgnoreCase("NEW"))
            lmt = addRec ;
        for(int i=0; i < lmt; i++) {
            int fld = i+1;
            String fldId = String.valueOf(fld);
            String lIdn ="";
            String cbox = "";
            String lsrt = "";
            if(!(msg.equalsIgnoreCase("NEW"))) {
             lDao = (GenDAO)reqList.get(i);
             lsrt = (String)lDao.getValue("srt");
              lIdn = lDao.getIdn();
              fldId = lIdn+"_"+lsrt;
            }
            
        if(sr == 0) {%>
         <tr><td colspan="<%=colCnt%>" class="ttl"><%=msg%></td></tr>   
        <%}%>
      <tr>
            <td><%=++sr%></td>
            <td>&nbsp;
      <%
      if(!(msg.equalsIgnoreCase("NEW"))) {
        String cboxPrp = "value(upd_"+ fldId + ")" ;
      %>
        <html:checkbox property="<%=cboxPrp%>"/>
      <%}%>
            </td>
      <%
        for(int j=0; j < daos.size(); j++) {
            UIFormsFields dao = (UIFormsFields)daos.get(j);
            String lFld = dao.getFORM_FLD();
            String htmlFld = lFld + "_" +fldId;
            String fldNm = "value(" + htmlFld + ")";
            String fldTyp = util.nvl(dao.getFLD_TYP(), "T");
            String valCond = util.nvl(dao.getVAL_COND(), "");
            if(valCond.indexOf("~fldId") > -1)
                valCond = valCond.replaceAll("~fldId",String.valueOf(fldId));
            
            String fldSz = util.nvl(dao.getFLD_SZ(),"10");
        %>
        <td>
        <%
            if(fldTyp.equals("T")) {
            %>
                <html:text property="<%=fldNm%>" size="<%=fldSz%>" />
            <%}
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
            <div class="float">   <html:text property="<%=fldNm%>"  styleId="<%=htmlFld%>" /></div>
            <div class="float">     <a href="#"  onClick="setYears(1900, <%=info.getCurrentYear()%>);showCalender(this, '<%=htmlFld%>');">
                <img src="<%=info.getReqUrl()%>/images/calender.png"></a></div>
            <% }
            %>
        </td>    
        <%}%>
      
     
        </tr>  
    <%}
%>    
  
    <tr><td colspan="<%=colCnt%>" align="center">
<% if(msg.equalsIgnoreCase("NEW")) {%>    
    <!--<html:submit property="addnew" value="Add New"  styleClass="submit"/>-->
        <%  pageList=(ArrayList)pageDtl.get("NEWBUTTON");
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
 } else { if(reqList.size() > 0) {%>
    <!--<html:submit property="modify" value="Save Changes" onclick="return validateUpdate()" styleClass="submit"/>
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
 } }%>
    </td></tr>
        
<%  }%>
    
    </table>
    </html:form>
  </td></tr></table>
 <%@include file="../calendar.jsp"%>
</body>
</html>
