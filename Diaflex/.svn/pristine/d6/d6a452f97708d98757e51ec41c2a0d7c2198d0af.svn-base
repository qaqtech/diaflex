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
    <title>Diaflex : Group Aadat</title>
      <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/bse.css?v=<%=info.getJsVersion()%>"/>
      <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/style.css?v=<%=info.getJsVersion()%>"/>
                <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/bse.js?v=<%=info.getJsVersion()%> " > </script>

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
    <td height="5" valign="top" class="greyline"><img src="../images/grey_linebig.png" width="685" height="5" align="right" /></td>
  </tr>
   <%
    ArrayList params = new ArrayList();
    ResultSet rs = null;
    
    String formName = "jbgroupaadat";
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
    dbutil.SOP(" View "+ formName + " : fields = " + daos.size());
    int addRec = Integer.parseInt(util.nvl(uiForms.getREC(),"10"));
    String formAction = "/masters/brcGrpAadat.do?method=save";
  %>
<tr>
  <td valign="top" class="hedPg">
  <table cellpadding="1" cellspacing="5"><tr><td valign="middle">
  <img src="../images/line.jpg" border="0" /></td><td valign="middle"> <span class="pgHed"><%=pgTtl%></span> </td>
</tr></table>
  </td>
  </tr>
    <tr><td class="tdLayout" valign="top">
  <html:errors/>
  <html:form action="<%=formAction%>" method="post">
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
        ArrayList editList = new ArrayList();
        String getEditList = " select brc_grp_idn from brc_grp_aadat where vld_dte is null order by brc_grp_nme " ;
        rs = db.execSql(" uiDsp getEditList ", getEditList, new ArrayList());
        while(rs.next()) {
            editList.add(rs.getString(1));
        }
        rs.close();
        ArrayList dspOrder = new ArrayList();
        dspOrder.add("NEW");
        dspOrder.add("EXISTING");
        for(int dsp=0; dsp < dspOrder.size(); dsp++) {
            int sr = 0 ;
            String msg = (String)dspOrder.get(dsp);
            int lmt = editList.size();
            if(msg.equalsIgnoreCase("NEW"))
                lmt = addRec ;
            for(int i=0; i < lmt; i++) {
                int fldId = 0 ;
                fldId = i+1 ;
                String lFldId = Integer.toString(fldId);
                String cbox = "";
                if(!(msg.equalsIgnoreCase("NEW"))) {
                    //fldId = Integer.parseInt((String)editList.get(i));
                    lFldId = (String)editList.get(i);
                } else 
                    lFldId += "_new" ;
            
                if(sr == 0) {%>
                    <tr><td colspan="<%=colCnt%>" class="ttl"><%=msg%></td></tr>   
                <%}%>
                <tr>
                    <td><%=++sr%></td>
                    <td>&nbsp;
      <%
                if(!(msg.equalsIgnoreCase("NEW"))) {
                    String cboxPrp = "value(upd_"+ lFldId + ")" ;
      %>
                    <html:checkbox property="<%=cboxPrp%>"/>
                <%}%>
                    </td>
      <%
        for(int j=0; j < daos.size(); j++) {
            UIFormsFields dao = (UIFormsFields)daos.get(j);
            String lFld = dao.getFORM_FLD();
            String fldNm = "value(" + lFld + "_" +  lFldId + ")";
            String fldTyp = util.nvl(dao.getFLD_TYP(), "T");
            String fldSz = util.nvl(dao.getFLD_SZ(),"10");
        %>
        <td>
        <%
            if(fldTyp.equals("T")) {
            %>
                <html:text property="<%=fldNm%>" size="<%=fldSz%>"/>
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
        <%}
        
        if(msg.equalsIgnoreCase("NEW")) {%>
        <td>&nbsp;</td>
        <%} else {
            String url = info.getReqUrl();
            String link = url+"/masters/brcGrpAadat.do?method=delete&idn="+ lFldId ;
            String onclick = "return setDeleteConfirm('"+link+"')";
        %>
        <td><html:link page="<%=link%>" onclick="<%=onclick%>">Delete</html:link></td>
        <%}%>
        </tr>  
    <%}
%>    
    <tr><td colspan="<%=colCnt%>" align="center">
<% if(msg.equalsIgnoreCase("NEW")) {%>    
    <html:submit property="addnew" value="Add New"  styleClass="submit"/>
<% } else { if(editList.size() > 0) {%>
    <html:submit property="modify" value="Save Changes"  onclick="return validateUpdate()" styleClass="submit"/>
&nbsp;<html:submit property="delete" value="Delete" onclick="return confirmChangesMsg('Are you sure to delete: ?')" styleClass="submit"/>
<% } }%>
    </td></tr>
        
<%  }%>
    
    
    </table>
    
    </html:form></td></tr></table>
     <%@include file="../calendar.jsp"%>
  </body>
</html>
