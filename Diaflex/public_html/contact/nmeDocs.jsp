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
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>nmeDocs</title>
    <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/style.css?v=<%=info.getJsVersion()%>"/>
    <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/bse.css?v=<%=info.getJsVersion()%>"/>
  <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/bse.js?v=<%=info.getJsVersion()%> " > </script>
   <script language="javaScript" type="text/javascript" src="<%=info.getReqUrl()%>/calendar/calendar.js"></script>
   <link href="<%=info.getReqUrl()%>/calendar/calendar.css" rel="stylesheet" type="text/css" />

  </head>
        <%String logId=String.valueOf(info.getLogId());
        String onfocus="cook('"+logId+"')";
        %>
 <body onfocus="<%=onfocus%>" >
<table width="100%"  border="0" align="center" cellpadding="0" cellspacing="0" >
   <bean:parameter id="nmeIdn" name="nmeIdn" />
   <%
   HashMap lovFormFlds = new HashMap();
    ArrayList dspOrder = new ArrayList();
    dspOrder.add("EXISTING");
    dspOrder.add("NEW");
    
   String formName = "nmedocs";
       DBUtil dbutil = new DBUtil();
    DBMgr db = new DBMgr(); 
    db.setCon(info.getCon());
    dbutil.setDb(db);
    dbutil.setInfo(info);
    db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));db.setDbUsr(info.getDbUsr());
    dbutil.setLogApplNm(info.getLoApplNm());
  ArrayList docList = (ArrayList)session.getAttribute("docList");
    HashMap allFields = (HashMap)info.getFormFields();
    HashMap formFields = (allFields.get(formName) == null)?dbutil.getFormFields(formName):(HashMap)allFields.get(formName);
    UIForms uiForms = (UIForms)formFields.get("DAOS");
    ArrayList daos = uiForms.getFields();
    int addRec = Integer.parseInt(util.nvl(uiForms.getREC(),"5")); 
      String formAction = "/contact/nmedocs.do?method=save&nmeIdn="+nmeIdn;
    ArrayList editList = (ArrayList)session.getAttribute("editList");
   %>
   
  
 
   <tr>
   <td>
   <html:form action="<%=formAction%>" method="post" enctype="multipart/form-data">
   <table width="95%">
   <tr>
    <td>
  <div class="memo">
    <table width="100%" class="Orange" cellpadding="1"  cellspacing="1">
   
   
   
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
        NmeDoc lDao = null;
   for(int dsp=0; dsp < dspOrder.size(); dsp++) {
        lDao = null;
            int sr = 0 ;
            String msg = (String)dspOrder.get(dsp);
             int lmt = editList.size();;
            if(msg.equalsIgnoreCase("NEW"))
                lmt = addRec ;
             for(int i=0; i < lmt; i++) {
             
                int fldId = 0 ;
                fldId = i+1 ;
                String cbox = "";
              if(!(msg.equalsIgnoreCase("NEW"))) {
                 lDao = (NmeDoc)docList.get(i);
                 String lIdn = lDao.getNmn_doc_id();
                 fldId = Integer.parseInt(lIdn);
              }
                
                if(sr == 0) {%>
                    <tr><td colspan="<%=colCnt%>" class="ttl"><%=msg%></td></tr>   
                <%}%>
                <tr>
                    <td><%=++sr%></td>
                    <td>&nbsp;</td>
                 
                    
                     <%
        for(int j=0; j < daos.size(); j++) {
            UIFormsFields dao = (UIFormsFields)daos.get(j);
            String lFld = dao.getFORM_FLD();
            String htmlFld = lFld + "_" +  fldId;
            String fldNm = "value(" + htmlFld + ")";
            String link = "value(Link_" + fldId + ")";
            String fldTyp = util.nvl(dao.getFLD_TYP(), "T");
            String valCond = util.nvl(dao.getVAL_COND(), "");
            String valFun = "Validate("+fldId+" , this)";
            if(valCond.indexOf("~fldId") > -1)
                valCond = valCond.replaceAll("~fldId", Integer.toString(fldId));
            String conChange="isValidFileName('"+htmlFld+"')";
            HashMap dbinfo = info.getDmbsInfoLst();
            String cnt = (String)dbinfo.get("CNT");
            String docPathNew = util.nvl(util.nvl((String)dbinfo.get("DOC_S3DOC_PATH")).trim());
            if(cnt.equals("kj") || cnt.equals("ku"))
            conChange="";
            String fldSz = util.nvl(dao.getFLD_SZ(),"10");
        %>
       <td>
        <%
            if(fldTyp.equals("T")) {
            %>
                <html:text property="<%=fldNm%>" size="<%=fldSz%>" />
            <%}
             if(fldTyp.equals("FL")) {
                 if(!msg.equalsIgnoreCase("NEW")){
                  NmeDoc docDeo = (NmeDoc)docList.get(i);
                 String doc_lnk=util.nvl(docDeo.getDoc_lnk());
                 String doc_path=util.nvl(docDeo.getDoc_path());
                 doc_path=doc_path.replaceAll("&", "%26");
                 if(!docPathNew.equals("")){
                 doc_lnk=dbutil.getSignUrl(docPathNew,doc_lnk);
                 }
                 %>
               
             <html:link href="<%=doc_lnk%>" target="_blank" > <bean:write property="<%=fldNm%>" name="nmeDocForm" /> </html:link>
             <button type="button"  class="submit" onclick="goTo('nmedocs.do?method=download&sign=Y&path=<%=doc_path%>&typ=<%=docDeo.getDoc_typ()%>')">Download</button>
           
           <% }else{%>
                <html:file property="<%=fldNm%>" styleId="<%=htmlFld%>" onchange="<%=conChange%>"   size="<%=fldSz%>" />
            <%}}
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
                            lovQ = lovQ.replace("~1", nmeIdn);
                            lovKV = dbutil.getLOV(lovQ);
                        }    
                        else    
                            lovKV = dbutil.getLOVList(lovQ);
                        lovFormFlds.put(lovKey, lovKV);
                    }
                }
                keys = (lovKV.get("K")!= null) ? (ArrayList)lovKV.get("K"):new ArrayList();
                vals = (lovKV.get("V")!= null) ? (ArrayList)lovKV.get("V"):new ArrayList();
                if((msg.equalsIgnoreCase("EXISTING")) && (keys.size() == 0)) {%>
                    <html:text property="<%=fldNm%>" onchange="<%=valCond%>"/>
                <%} else {
                %>  
                <html:select  property="<%=fldNm%>" onchange="<%=valCond%>" >
                  
                    <%for(int a=0; a < keys.size(); a++) {
                        String lKey = (String)keys.get(a);
                        String ldspVal = (String)vals.get(a);%>
                        
                        <html:option value="<%=lKey%>"><%=ldspVal%></html:option>
                      <%}  
                    %>
                </html:select>
                <%}
            }
            if(fldTyp.equals("CH")) {
             if(!msg.equalsIgnoreCase("NEW")) {
             if(info.getUsrFlg().equals("SYS")){
             %> 
              <html:checkbox property="<%=fldNm%>" value="Y" onclick="<%=valFun%>" />
            <%}}}
            %>
        </td>    
        <%}
      
         if(msg.equalsIgnoreCase("NEW")) {%>
        <td>&nbsp;</td>
        <%} else {
            String link= info.getReqUrl() + "/contact/nmedocs.do?method=delete&delId="+fldId+"&nmeIdn="+nmeIdn;
            String onclick = "return setDeleteConfirm('"+link+"')";
        %>
        <td><html:link page="<%=link%>" onclick="<%=onclick%>" >Delete</html:link></td>
        <%}
                
               }%>
                <tr><td colspan="<%=colCnt%>" align="center">
<% if(msg.equalsIgnoreCase("NEW")) {%>    
    <html:submit property="addnew" value="Add New"  styleClass="submit"/>
<% } %>
    </td></tr>
  <% } %>
    </table>
   </div></td></tr></table>
   </html:form>
   </td>
   </tr>
   </table>
 </td></tr>
 </table>
 
   <%@include file="../calendar.jsp"%>
  
  </body>
</html>