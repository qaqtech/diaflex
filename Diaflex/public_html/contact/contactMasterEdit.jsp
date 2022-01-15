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
    String formName = util.nvl(request.getParameter("formName"));
    String nmeIdn = util.nvl(request.getParameter("nmeIdn"));
    String currentform = util.nvl((String)request.getAttribute("form"));
    HashMap allFields = (HashMap)info.getFormFields();
    HashMap formFields = (allFields.get(formName) == null)?dbutil.getFormFields(formName):(HashMap)allFields.get(formName);
    UIForms uiForms = (UIForms)formFields.get("DAOS");
    ArrayList daos = uiForms.getFields();
    HashMap lovFormFlds = new HashMap();
        String logId=String.valueOf(info.getLogId());
        String onfocus="cook('"+logId+"')";
        %>
 <body onfocus="<%=onfocus%>" onload="MM_preloadImages('images/faunalogo.png')" topmargin="0" leftmargin="0" rightmargin="0" bottommargin="0">
<table width="100%"  border="0" align="center" cellpadding="0" cellspacing="0">
  <% if(request.getAttribute("msg")!=null){%>
  <tr><td height="15"  valign="top"><span class="redLabel"> <%=request.getAttribute("msg")%></span></td></tr>
  <% } else {
  %>
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
   <html:form  action="contact/nmeEdit.do?method=save">
    <html:hidden property="value(btn)" styleId="btn" />
    <html:hidden property="nmeIdn" value="<%=nmeIdn%>" />
    <html:hidden property="formName" value="<%=formName%>"  />
    
    
  <tr>
  <td valign="top" width="90%">
 
   <table width="95%">
   <tr>
    <td>
  
    <table width="100%" cellpadding="1" class="grid1" cellspacing="1">
    
    <thead>
                <tr>
                  <th>FIELD</th>
                  <th>OLD VALUE</th>
                  <%if(!currentform.equals("nmedtl")){%>
                  <th>NEW VALUE</th>
                  <th>FIELD</th>
                  <%}%>
                  <th>OLD VALUE</th>
                  <th>NEW VALUE</th>
                </tr>
              </thead>
    <tr>
  <%
  int count =0;
     ArrayList reqList = (ArrayList)session.getAttribute(formName);
     int lmt = reqList.size();
     for(int i=0; i < lmt; i++) {
            int fldId = 0 ;
            fldId = i+1 ;
     for(int j=0; j < daos.size(); j++) {
            UIFormsFields dao = (UIFormsFields)daos.get(j);
            String lFld = dao.getFORM_FLD();
            String htmlFld = lFld + "_" +  fldId;
            String htmlFldNew = lFld + "_" +  fldId+"_New";
            String fldTtl = util.nvl(dao.getDSP_TTL(), lFld);
            String fldNmNew = "value(" + htmlFldNew + ")";
            String fldNm = "value(" + htmlFld + ")";
            String fldTyp = util.nvl(dao.getFLD_TYP(), "T");
            if(fldTyp.equals("SB"))
                fldTyp = "T";
            String valCond = util.nvl(dao.getVAL_COND(), "");
            if(valCond.indexOf("~fldId") > -1)
                valCond = valCond.replaceAll("~fldId", Integer.toString(fldId));
            
            String fldSz = util.nvl(dao.getFLD_SZ(),"10");
            if(count==2){
             count=0;
             
             %></tr><tr>
          
        <% }
        count++;    
        if(!fldTtl.equals("Value")){
        %>
        
        <td nowrap="nowrap"><%=fldTtl%></td>
        <%}%>
        <td nowrap="nowrap">
        <%
            if(fldTyp.equals("T")) {
            %>
                <html:text property="<%=fldNm%>"  disabled="true" size="<%=fldSz%>" />
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
                <html:select property="<%=fldNm%>" disabled="true">
                    <%for(int a=0; a < keys.size(); a++) {
                        String lKey = (String)keys.get(a);
                        String ldspVal = (String)vals.get(a);%>
                        
                        <html:option value="<%=ldspVal%>"><%=ldspVal%></html:option>
                      <%}  
                    %>
                </html:select>
                <%
            }
            
             if(fldTyp.equals("DT")){%>
            <div class="float">   <html:text property="<%=fldNm%>"   disabled="true" /></div>
            <% }
            %>
        </td>
        <%
        if(!fldTtl.equals("Attribute")){
        if(fldTyp.equals("T")) {
        %>
        <td><html:text property="<%=fldNmNew%>" size="10"  /></td>
        <%}
        if(fldTyp.equals("S") || fldTyp.equals("L")) {
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
                <td>
                <html:select property="<%=fldNmNew%>">
                    <%for(int a=0; a < keys.size(); a++) {
                        String lKey = (String)keys.get(a);
                        String ldspVal = (String)vals.get(a);%>
                        
                        <html:option value="<%=ldspVal%>"><%=ldspVal%></html:option>
                      <%}  
                    %>
                </html:select>
        </td>
        <%}
        if(fldTyp.equals("DT")){%>
        <td>
        <div class="float"><html:text property="<%=fldNmNew%>"  styleId="<%=htmlFld%>" /></div>
        <div class="float"><a href="#"  onClick="setYears(1900, <%=info.getCurrentYear()%>);showCalender(this, '<%=htmlFld%>');">
        <img src="<%=info.getReqUrl()%>/images/calender.png"></a></div>
        </td>
        <% }}
        %>
        <%} 
        }%>
   
    
  </tr>
  <tr><td class="tdLayout" colspan="6" align="center" valign="top">&nbsp;
  <html:submit property="modify" value="Change Request"  styleClass="submit"/>
  </td></tr>
  </table>
  </div>
  </td></tr>
  </table>
  
  
  </td></tr>
  
 </html:form>
 <% } %>
  
   <%@include file="../calendar.jsp"%>
  </table></body></html>