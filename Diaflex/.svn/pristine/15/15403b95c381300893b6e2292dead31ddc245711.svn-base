<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page errorPage="../error_page.jsp" %>
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import="java.util.ArrayList,java.util.HashMap,java.sql.ResultSet,ft.com.*, java.util.Date, java.util.ArrayList, ft.com.dao.*"%>
<jsp:useBean id="info" class="ft.com.InfoMgr" scope="session" />
<jsp:useBean id="util" class="ft.com.JspUtil" scope="page" />
<%@taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>

<%@taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>
<html>
  <head>
 <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
  
  <title>Column Mapping</title>
  <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/bse.js?v=<%=info.getJsVersion()%> " > </script>
<link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/bse.css?v=<%=info.getJsVersion()%>"/>
<script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/ajax.js?v=<%=info.getJsVersion()%> " > </script>
   <script language="javaScript" type="text/javascript" src="<%=info.getReqUrl()%>/calendar/calendar.js"></script>
   <link href="<%=info.getReqUrl()%>/calendar/calendar.css" rel="stylesheet" type="text/css" />
 <script language="javaScript" type="text/javascript" src="<%=info.getReqUrl()%>/scripts/bse.js?v=<%=info.getJsVersion()%>"></script>

 
      <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/style.css?v=<%=info.getJsVersion()%>"/>
  </head>
        <%String logId=String.valueOf(info.getLogId());
        String onfocus="cook('"+logId+"')";
        %>
 <body onfocus="<%=onfocus%>">
<table width="100%"  border="0" align="center" cellpadding="0" cellspacing="0" >
 
 
 
  <%

   ArrayList params = new ArrayList();
    ResultSet rs = null;
    
    String formName = "prpwebmap";
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
    String nmeIdn = util.nvl((String)request.getAttribute("namIdn"));
    String pgTtl = uiForms.getFORM_TTL();
    dbutil.SOP(" View "+ formName + " : fields = " + daos.size());
    int addRec = Integer.parseInt(util.nvl(uiForms.getREC(),"10"));
   String formAction = "/contact/columnMap.do?method=save&nmeIdn="+nmeIdn ;
 %>
 
  
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
 <html:hidden property="value(namIdn)"  />
 <table width="99%" cellpadding="1" cellspacing="1"><tr><td valign="top"  width="45%" >
  <div class="memo">
    <table class="Orange" cellpadding="1" cellspacing="1">

    <tr><th class="Orangeth">Sr</th>
        <th class="Orangeth">&nbsp;</th>
<%
        for(int j=0; j < daos.size(); j++) {
            UIFormsFields dao = (UIFormsFields)daos.get(j);
            String lFld = dao.getFORM_FLD();
            String fldTtl = util.nvl(dao.getDSP_TTL(), lFld);
            String isReq =util.nvl(dao.getREQ_YN());
%>
    <th class="Orangeth">
    <% if(isReq.equals("Y")){%>
            <span class="redTxt">*</span>
          <%  }else{%>
          &nbsp;&nbsp;
          <%}%>
    <%=fldTtl%></th>
<%}%>
    <th class="Orangeth">Action</th>
    </tr>
<%
    int colCnt = daos.size() + 3;
   
    ArrayList dspOrder = new ArrayList();
     dspOrder.add("EXISTING");
    dspOrder.add("NEW");
   
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
            if(!(msg.equalsIgnoreCase("NEW"))) {
             lDao = (GenDAO)reqList.get(i);
              lIdn = lDao.getIdn();
              fldId = lIdn+"_"+fld;
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
        String mprpVal = "";
        String colVal = "";
        for(int j=0; j < daos.size(); j++) {
            UIFormsFields dao = (UIFormsFields)daos.get(j);
            String lFld = dao.getFORM_FLD();
            String htmlFld = lFld + "_" +fldId;
            String fldNm = "value(" + htmlFld + ")";
             if(j==0)
             colVal = (String)request.getAttribute(htmlFld);
            if(j==1)
             mprpVal = (String)request.getAttribute(htmlFld);
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
                <html:text property="<%=fldNm%>" size="<%=fldSz%>"/>
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
               
                <html:select property="<%=fldNm%>"  >
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
               <html:text property="<%=fldNm%>"  />
                <a href="#"  onClick="setYears(1900, '<%=info.getCurrentYear()%>' );showCalender(this, '<%=htmlFld%>');">
                <img src="<%=info.getReqUrl()%>/images/calender.png"></a>
            <% }
            %>
        </td>    
        <%}%>
       <% if(msg.equalsIgnoreCase("NEW")) {%>
        <td>&nbsp;</td>
        <%} else {
        if (mprpVal.indexOf("&") > -1) {
                mprpVal = mprpVal.replaceAll("&", "%26");
            }
            String link1="/contact/propertieMap.do?method=load&mprp="+mprpVal+"&col="+colVal+"&nmeIdn="+nmeIdn;
           String link2= info.getReqUrl() + "/contact/columnMap.do?method=delete&mprp="+mprpVal+"&col="+colVal+"&nmeIdn="+nmeIdn;
           String onclick = "return setDeleteConfirm('"+link2+"')";
     
        %>
        <td nowrap="nowrap"><html:link page="<%=link1%>" target="properties" >Add Properties </html:link>
        &nbsp|&nbsp;<html:link page="<%=link2%>" onclick="<%=onclick%>"   >Delete </html:link>
        </td>
        <%}%>
     
        </tr>  
    <%}
%>    
  
    <tr><td colspan="<%=colCnt%>" align="center" nowrap="nowrap">
<% if(msg.equalsIgnoreCase("NEW")) {%>    
    <html:submit property="addnew" value="Add New"  styleClass="submit"/>
<% } else { if(reqList.size() > 0) {%>
    <html:submit property="modify" value="Save Changes" onclick="return validateUpdate()"  styleClass="submit"/>
    &nbsp;<html:reset property="reset" value="Reset" styleClass="submit"/>
          &nbsp;&nbsp;<a href="columnMap.do?method=dwmapping&nmeIdn=<%=nmeIdn%>" target="_blank">D/W Format</a>
<% } }%>
    </td></tr>
        
<%  }%>
    
    </table></div>
    </td>
    <td valign="top" width="45%" >
    <iframe name="properties" height="1000" width="100%" frameborder="0" >
    </iframe>
    </td></tr></table>
   </html:form>
  </td></tr></table>
  <%@include file="../calendar.jsp"%>
</body>
</html>