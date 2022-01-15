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
    <meta http-equiv="Content-Type" content="text/html;charset=windows-1252"/>
    <title>Web Access</title>
    <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/bse.css?v=<%=info.getJsVersion()%>"/>
    <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/style.css?v=<%=info.getJsVersion()%>"/>
    <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/bse.js?v=<%=info.getJsVersion()%> " > </script>
    <script language="javaScript" type="text/javascript" src="<%=info.getReqUrl()%>/calendar/calendar.js"></script>
   <link href="<%=info.getReqUrl()%>/calendar/calendar.css" rel="stylesheet" type="text/css" />
    <script language="javaScript" type="text/javascript" src="<%=info.getReqUrl()%>/scripts/ajax.js?v=<%=info.getJsVersion()%>"></script>
    <script language="javaScript" type="text/javascript" src="<%=info.getReqUrl()%>/scripts/Validation.js"></script>
   <link href="<%=info.getReqUrl()%>/calendar/calendar.css" rel="stylesheet" type="text/css" />

  </head>
        <%String logId=String.valueOf(info.getLogId());
        String onfocus="cook('"+logId+"')";
        %>
 <body onfocus="<%=onfocus%>">
  <bean:parameter id="reqNmeIdn" name="nmeIdn" value="0"/>
  <bean:parameter id="reqIdn" name="idn" value="0"/>
<%

    reqNmeIdn = util.nvl(request.getParameter("nmeIdn"), reqNmeIdn);
    ArrayList params = new ArrayList();
    ResultSet rs = null;
    String method = util.nvl((String)request.getAttribute("method"));
    String formName = "webaccess";
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
   
    String pgTtl = uiForms.getFORM_TTL();
    pgTtl = pgTtl.replaceAll("~nme", util.nvl((String)info.getValue("NME"),""));
    int addRec = Integer.parseInt(util.nvl(uiForms.getREC(),"1"));
     HashMap allPageDtl= (info.getPageDetails() == null)?new HashMap():(HashMap)info.getPageDetails();
     HashMap pageDtl=(HashMap)allPageDtl.get("CONTACT_FORM");
    ArrayList pageList=new ArrayList();
    HashMap pageDtlMap=new HashMap();
    String fld_nme="",fld_ttl="",val_cond="",dflt_val="",fld_typ="",form_nme="";
    String view = util.nvl(request.getParameter("view"));
    String approve = util.nvl(request.getParameter("approve"));
    
    String formAction = "/contact/webaccess.do?method=save";
    
   
  %>
      
<table width="100%"  border="0" align="center" cellpadding="0" cellspacing="0" >
  
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
  <tr><td>
<html:form action="<%=formAction%>" method="post">  
<html:hidden property="nmeIdn" styleId="nid" />
<html:hidden property="idn" />
   <table width="95%">
   <tr>
    <td>
  <div class="memo">
    <table width="100%" class="Orange" cellpadding="1"  cellspacing="1">
    <tr><th colspan="4" class="Orangeth" align="left">Web Access</th></tr>
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
            if(valCond.indexOf("~pID") > -1)
            valCond = valCond.replaceAll("~pID", htmlFld);
              String isReq =util.nvl(dao.getREQ_YN());
          if(count==2){
             count=0;
             
             %></tr><tr>
          <%}count++;
           %>
        
            <td> <%
            if(isReq.equals("Y")){%>
            <span class="redTxt">*</span>
          <%  }
            %><%=fldTtl%></td>
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
            %>
                <html:text property="<%=fldNm%>" styleId="<%=htmlFld%>"  onchange="<%=valCond%>" size="<%=fldSz%>"/>
                
            <%} 
            if(fldTyp.equals("P")) {
            %>
                <html:password property="<%=fldNm%>" styleId="<%=htmlFld%>" onchange="<%=valCond%>"  size="<%=fldSz%>" />
                
            <%} 
            if(fldTyp.equals("DT")) {%>
                <a href="#"  onClick="setYears(2011, 2020);showCalender(this, '<%=htmlFld%>');">
                <img src="<%=info.getReqUrl()%>/images/calender.png"></a>

            <%}
            
            if((fldTyp.equals("S")) || (fldTyp.equals("L"))) {
                String lovKey = lFld + "LOV";                
                String lovQ = dao.getLOV_QRY(); //util.nvl((String)formFlds.get(lovKey));
                HashMap lovKV = new HashMap();
                ArrayList keys = new ArrayList();
                ArrayList vals = new ArrayList();
                if(lovFormFlds.get(lovKey) != null) {
                    lovKV = (HashMap)lovFormFlds.get(lovKey);
                } else {
                    if(lovQ.length() > 0) {
                        if(fldTyp.equals("S")) {
                            if(lFld.equalsIgnoreCase("rel_idn"))
                              lovQ += " where nme_idn = "+ reqNmeIdn ;
                            lovKV = dbutil.getLOV(lovQ);
                         }   
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
                <html:select property="<%=fldNm%>" onchange="<%=valCond%>" styleId="<%=htmlFld%>">
                  
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
   <tr>
   <td colspan="4">
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
  
  <%@include file="../calendar.jsp"%>
 
 
  <%
    HashMap vldInvalid = ((HashMap)request.getAttribute("vldInvalid") == null)?new HashMap():(HashMap)request.getAttribute("vldInvalid");
    ArrayList list = (ArrayList)request.getAttribute("webaccesslst");
    if(list.size() > 0) {
  %>  
   <label class="pgTtl">Search List</label>
   <table cellpadding="2" cellspacing="2"><tr><td valign="top">
  <table class="grid1">
  <tr><td>Sr</td>
    
  <%
    for(int j=0; j < daos.size(); j++) {
            UIFormsFields dao = (UIFormsFields)daos.get(j);
            String lFld = dao.getFORM_FLD();
            String fldTtl = util.nvl(dao.getDSP_TTL(), lFld);%>
            
            <td><%=fldTtl%></td>
    <%}
  %>  
    <td>Action</td>
  </tr>
  <%
    for(int i=0; i < list.size(); i++) {%>
    <%
        WebUsrs lDao = (WebUsrs)list.get(i);
        String lIdn = lDao.getIdn();
        String nmeIdn = lDao.getNmeIdn();
        String rln_idn = (String)lDao.getValue("rel_idn");
        String appLnk = "<a href=\""+ info.getReqUrl() + "/contact/webaccess.do?method=approve&nmeIdn="+ nmeIdn + "&idn="+lIdn+"\">Approve</a>";
        
        String color=util.nvl((String)vldInvalid.get(lIdn));
        if(!color.equals(""))
        color = "color: "+color+";";
        %>
        <tr style="<%=color%>">
        <td><%=(i+1)%></td>
     <%   
        for(int j=0; j < daos.size(); j++) {
            UIFormsFields dao = (UIFormsFields)daos.get(j);
            String lFld = dao.getFORM_FLD();
            String lVal = (String)lDao.getValue(lFld);
            String aliasFld = util.nvl(dao.getALIAS());
            String fldTyp = util.nvl(dao.getFLD_TYP(), "T");
            if(aliasFld.length() > 0) {
                lVal = util.nvl((String)lDao.getValue(aliasFld.substring(aliasFld.lastIndexOf(" ")+1)));
            }
            if(fldTyp.equals("P"))
             lVal = "******";
            %>
           
            <td>
            <%if(lFld.equals("pwd")){
            String fldnme="value("+lFld+"_"+j+")";%>
            <html:text property="<%=fldnme%>" value="<%=lVal%>"/>
            <%}else{%>
            <%=lVal%>
            <%}%>
            </td><%}%>
        <td nowrap="nowrap">
        <%if(approve.equals("Y")) {%>
        <%} else {
        String  target ="";
       pageList= ((ArrayList)pageDtl.get("LINK") == null)?new ArrayList():(ArrayList)pageDtl.get("LINK");
      if(pageList!=null && pageList.size() >0){
      for(int j=0;j<pageList.size();j++){
      pageDtlMap=(HashMap)pageList.get(j);
      fld_nme="value("+(String)pageDtlMap.get("fld_nme")+")";
      fld_ttl=(String)pageDtlMap.get("fld_ttl");
      dflt_val=(String)pageDtlMap.get("dflt_val");
      dflt_val=dflt_val.replaceAll("NME",nmeIdn);
      dflt_val=dflt_val.replaceAll("IDN",lIdn);
      dflt_val=dflt_val.replaceAll("RLID",rln_idn);
      dflt_val =info.getReqUrl()+dflt_val;
      val_cond=(String)pageDtlMap.get("val_cond");
      val_cond=val_cond.replaceAll("URL",dflt_val);
      fld_typ=(String)pageDtlMap.get("fld_typ"); 
      if(fld_typ.equals("ROLE")){
         target="roleFrame";
      }
      if(color.equals("") && !fld_typ.equals("ACTIVE")){
      %>
        <a href="<%=dflt_val%>" onclick="<%=val_cond%>" target="<%=target%>" ><%=fld_ttl%></a>&nbsp;
      <%   if(!fld_typ.equals("ROLE") && !fld_typ.equals("ACTIVE")){%>
       |
       
       <%}}else if(!color.equals("") && fld_typ.equals("ACTIVE")){
      %>
        <a href="<%=dflt_val%>" onclick="<%=val_cond%>" ><%=fld_ttl%></a>&nbsp;
       <%}}}}%>    
        </td>
     </tr>   
  <%}
  %>
  </table></td><td>
  <iframe name="roleFrame" width="400" height="300" frameborder="0">
  
  </iframe>
  </td></tr></table>
  <% 
     }
  %>
 </td></tr></table>
 <%@include file="../calendar.jsp"%>
  </body>
</html>  
  