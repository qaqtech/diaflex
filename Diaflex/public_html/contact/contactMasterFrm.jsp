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
    <title>Contact Form</title>
    <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/bse.css?v=<%=info.getJsVersion()%>"/>
    <script language="javaScript" type="text/javascript" src="<%=info.getReqUrl()%>/calendar/calendar.js"></script>
   <link href="<%=info.getReqUrl()%>/calendar/calendar.css" rel="stylesheet" type="text/css" />
     <script language="javaScript" type="text/javascript" src="<%=info.getReqUrl()%>/scripts/ajax.js?v=<%=info.getJsVersion()%>"></script>
      <script language="javaScript" type="text/javascript" src="<%=info.getReqUrl()%>/scripts/bse.js?v=<%=info.getJsVersion()%>"></script>
      <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/style.css?v=<%=info.getJsVersion()%>"/>
  </head>
<%
    DBUtil dbutil = new DBUtil();
    DBMgr db = new DBMgr(); 
    db.setCon(info.getCon());
    dbutil.setDb(db);
    dbutil.setInfo(info);
    db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));db.setDbUsr(info.getDbUsr());
    dbutil.setLogApplNm(info.getLoApplNm());
  dbutil.updAccessLog(request,response,"Contact Master", "Contact Name");
   String formName = "contact";
    String frompa=util.nvl((String)request.getAttribute("frompa"));
    HashMap allFields = (HashMap)info.getFormFields();
    HashMap formFields = (allFields.get(formName) == null)?dbutil.getFormFields(formName):(HashMap)allFields.get(formName);
    UIForms uiForms = (UIForms)formFields.get("DAOS");
    ArrayList daos = uiForms.getFields();
     HashMap lovFormFlds = new HashMap();
      HashMap allPageDtl= (info.getPageDetails() == null)?new HashMap():(HashMap)info.getPageDetails();
 HashMap pageDtl=(HashMap)allPageDtl.get("CONTACT_FORM");
    ArrayList pageList=new ArrayList();
    HashMap pageDtlMap=new HashMap();
    String fld_nme="",fld_ttl="",val_cond="",dflt_val="",fld_typ="",form_nme="";
     String nmeIdn = (String)request.getAttribute("nmeIdn");
        String logId=String.valueOf(info.getLogId());
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
    <td height="5" valign="top" class="greyline"><img src="../images/grey_linebig.png" width="685" height="5" align="right" /></td>
  </tr>
  <tr>
  <td valign="top" class="hedPg">
  <table cellpadding="1" cellspacing="5"><tr><td valign="middle">
  <img src="../images/line.jpg" border="0" /></td><td valign="middle"> <span class="pgHed">
  Contact Form
  </span></td></tr></table>
  </td></tr>
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
  <%}%>
  </td></tr>
   <html:form  action="contact/nme.do?method=save">
    <html:hidden property="value(btn)" styleId="btn" />
    <html:hidden property="nmeIdn"  />
    <html:hidden property="value(frompa)"  />
  <tr>
  <td valign="top" width="90%" class="hedPg">
 
   <table width="95%">
   <tr>
    <td>
  
    <table width="100%" cellpadding="1" class="grid1" cellspacing="1">
    <tr><th colspan="6" align="left">Name Details</th></tr>
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
            String displayN="";
            if(lFld.equals("typ") && !frompa.equals("")){
            displayN="display:none;";
            }
            if(count==3){
             count=0;
             
             %></tr><tr>
          
           <% }
          count++;
           %>
  
       <td> 
            <%
            if(isReq.equals("Y")){%>
             <span class="redTxt">*</span>
          <%  }
            %>
           <%=fldTtl%></td>
            <td>
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
            if((fldTyp.equals("T")) || (fldTyp.equals("FT")) || (fldTyp.equals("DT"))) {
            boolean readonly=false;
            if(lFld.equals("frm_dte") || lFld.equals("nme_idn")){
            readonly=true;
            }%>
                <html:text property="<%=fldNm%>" styleId="<%=htmlFld%>" readonly="<%=readonly%>" size="<%=fldSz%>"/>
            <%} 
            if(fldTyp.equals("DT") && !lFld.equals("frm_dte")) {%>
                <a href="#"  onClick="setYears(1900, <%=info.getCurrentYear()%>);showCalender(this, '<%=htmlFld%>');">
                <img src="<%=info.getReqUrl()%>/images/calender.png"></a>

            <%} 
            if((fldTyp.equals("S")) || (fldTyp.equals("L"))) {
                String lovKey = lFld + "LOV";
//                fldNm += "\" " + valCond;
                
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
                    <html:text property="<%=fldNm%>" onchange="<%=valCond%>"/>
                <%} else {
                %>
                <html:select property="<%=fldNm%>" onchange="<%=valCond%>" styleId="<%=htmlFld%>" style="<%=displayN%>">
                  
                    <%for(int a=0; a < keys.size(); a++) {
                        String lKey = (String)keys.get(a);
                        String ldspVal = (String)vals.get(a);%>
                        
                        <html:option value="<%=lKey%>"><%=ldspVal%></html:option>
                      <%}  
                    %>
                </html:select>
                <%}
            }
            
   %>
       </td>     
    <%
    }    
  %>
  </tr>
  <%if(request.getAttribute("upd")!=null){%>
  <tr><td class="tdLayout" colspan="6" align="center" valign="top">
     <%pageList= ((ArrayList)pageDtl.get("MASTERUPD") == null)?new ArrayList():(ArrayList)pageDtl.get("MASTERUPD");
      if(pageList!=null && pageList.size() >0){
      for(int j=0;j<pageList.size();j++){
      pageDtlMap=(HashMap)pageList.get(j);
      fld_nme="value("+(String)pageDtlMap.get("fld_nme")+")";
      fld_ttl=(String)pageDtlMap.get("fld_ttl");
      val_cond=(String)pageDtlMap.get("val_cond");
      val_cond=val_cond.replaceAll("NME",nmeIdn);
      fld_typ=(String)pageDtlMap.get("fld_typ");%>
&nbsp;<html:submit property="modify" value="Update" onclick="<%=val_cond%>"  styleClass="submit"/>
<%}}%>
 </td></tr>
 <%}else{%>
 <tr><td class="tdLayout" colspan="6" align="center" valign="top">
      <%pageList= ((ArrayList)pageDtl.get("MASTERADD") == null)?new ArrayList():(ArrayList)pageDtl.get("MASTERADD");
      if(pageList!=null && pageList.size() >0){
      for(int j=0;j<pageList.size();j++){
      pageDtlMap=(HashMap)pageList.get(j);
      fld_nme="value("+(String)pageDtlMap.get("fld_nme")+")";
      fld_ttl=(String)pageDtlMap.get("fld_ttl");
      val_cond=(String)pageDtlMap.get("val_cond");
      val_cond=val_cond.replaceAll("NME",nmeIdn);
      fld_typ=(String)pageDtlMap.get("fld_typ");%>
 &nbsp;<html:button property="addnew" value="Add New" onclick="<%=val_cond%>" styleClass="submit"/>
<%}}%>
 </td></tr>
 <%}%>
  </table>
  </div>
  </td></tr>
  </table>
  
  
  </td></tr>
  
 </html:form>
  <%if(request.getAttribute("upd")!=null){
  String typ = util.nvl((String)request.getAttribute("typ"));
    %>
  <tr><td class="tdLayout" valign="top">
  <table><tr>
  
   <%
         pageList= ((ArrayList)pageDtl.get("BUTTON") == null)?new ArrayList():(ArrayList)pageDtl.get("BUTTON");
      if(pageList!=null && pageList.size() >0){
      for(int j=0;j<pageList.size();j++){
      pageDtlMap=(HashMap)pageList.get(j);
      fld_nme="value("+(String)pageDtlMap.get("fld_nme")+")";
      fld_ttl=(String)pageDtlMap.get("fld_ttl");
      val_cond=(String)pageDtlMap.get("val_cond");
      val_cond=val_cond.replaceAll("NME",nmeIdn);
      fld_typ=(String)pageDtlMap.get("fld_typ");
%>
<td><button type="button" onclick="<%=val_cond%>" class="submit"  ><%=fld_ttl%></button> </td>
<%
}}
%>
  <!--<td><button type="button" onclick="DisPalyObj('address.do?method=load&nmeIdn=<%=nmeIdn%>')" class="submit" >Address</button> </td>
   <td><button type="button" onclick="DisPalyObj('nmedtl.do?method=load&nmeIdn=<%=nmeIdn%>')" class="submit">Additional Info</button></td>
   <td><button type="button"  onclick="DisPalyObj('nmerel.do?method=load&nmeIdn=<%=nmeIdn%>')" class="submit">Terms</button> </td>
   <td><button type="button" onclick="DisPalyObj('nmecomm.do?method=load&nmeIdn=<%=nmeIdn%>')" class="submit">Commission Agents</button></td>
   <td><button type="button" onclick="DisPalyObj('nmegrp.do?method=load&nmeIdn=<%=nmeIdn%>')" class="submit">Groups</button> </td>
   <td><button type="button" onclick="DisPalyObj('webaccess.do?method=load&nmeIdn=<%=nmeIdn%>')" class="submit">Web Access</button> </td>
   <td><button type="button" onclick="DisPalyObj('nmedocs.do?method=search&nmeIdn=<%=nmeIdn%>')" class="submit">Documents</button> </td>
   <td><button type="button" onclick="DisPalyObj('columnMap.do?method=load&nmeIdn=<%=nmeIdn%>')" class="submit">Column Mapping</button></td>
  -->
  </tr></table>
  </td>
  </tr>
  <%}%>
  <tr>
  <td class="tdLayout" style="display:none" id="frameDiv" width="90%" valign="top">
  <iframe name="subContact" height="400" width="95%" class="iframe" frameborder="0">
  
  </iframe>
  
  </td>
  
  </tr>
    
<tr>
    <td><jsp:include page="/footer.jsp" /> </td>
    </tr>
   <%@include file="../calendar.jsp"%>
  </table></body></html>