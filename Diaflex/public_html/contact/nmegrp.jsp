<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page errorPage="../error_page.jsp" %>
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import="java.util.ArrayList,java.util.HashMap,java.sql.ResultSet,ft.com.*, java.util.Date, java.util.ArrayList, ft.com.dao.*"%>
<jsp:useBean id="info" class="ft.com.InfoMgr" scope="session" />
<jsp:useBean id="util" class="ft.com.JspUtil" scope="page" />
<%@taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>
<%@taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<bean:parameter id="reqNmeIdn" name="nmeIdn" value="0"/>
<%

    String formName = "nmegrp";
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
    
    int addRec = Integer.parseInt(util.nvl(uiForms.getREC(),"10"));
        
    HashMap lovFormFlds = new HashMap();
    //util.SOP(formName + " : Addr Idn : " + reqIdn + " : Nme Idn : " + reqNmeIdn);    
    String pgTtl = uiForms.getFORM_TTL();
    pgTtl = pgTtl.replaceAll("~nme", util.nvl((String)info.getValue("NME"),""));
    
    String formAction = "/contact/nmegrp.do?method=save&nmeIdn="+ reqNmeIdn;
  
%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>Name Groupings</title>
   <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/bse.css?v=<%=info.getJsVersion()%>"/>
      <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/style.css?v=<%=info.getJsVersion()%>"/>
       <script language="javaScript" type="text/javascript" src="<%=info.getReqUrl()%>/calendar/calendar.js"></script>
   <link href="<%=info.getReqUrl()%>/calendar/calendar.css" rel="stylesheet" type="text/css" />
    <script language="javaScript" type="text/javascript" src="<%=info.getReqUrl()%>/scripts/bse.js?v=<%=info.getJsVersion()%>"></script>

  </head>
        <%String logId=String.valueOf(info.getLogId());
        String onfocus="cook('"+logId+"')";
        %>
 <body onfocus="<%=onfocus%>" >
<table width="100%"  border="0" align="center" cellpadding="0" cellspacing="0" >
  
 
 
  <tr><td valign="top" width="50%">
  
 
    <html:form action="<%=formAction%>" method="post">
  <html:hidden property="nmeIdn" />
<html:hidden property="idn" />
<table width="100%">
   <tr>
    <td >
  <div class="memo"  >
  <table class="Orange" width="100%" >

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
    HashMap menus = new HashMap();
    ArrayList menuIds = new ArrayList();
    String getMenuIds = " select nme_grp_idn from nme_grp where nme_idn = ? and vld_dte is null order by 1 ";
    ArrayList ary = new ArrayList();
    ary.add(reqNmeIdn);
    ResultSet rs = db.execSql("get Menu Nme", getMenuIds, ary);
    while(rs.next()) {
        menuIds.add(rs.getString(1));    
    }
    rs.close();
    ArrayList dspOrder = new ArrayList();
    dspOrder.add("NEW");
    dspOrder.add("EXISTING");
    for(int dsp=0; dsp < dspOrder.size(); dsp++) {
        int sr = 0 ;
        String msg = (String)dspOrder.get(dsp);
        int lmt = menuIds.size();
        if(msg.equalsIgnoreCase("NEW"))
            lmt = addRec ;
        for(int i=0; i < lmt; i++) {
            int fldId = 0 ;
            fldId = i+1 ;
            String cbox = "";
            if(!(msg.equalsIgnoreCase("NEW"))) {
                fldId = Integer.parseInt((String)menuIds.get(i));
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
            String htmlFld = lFld + "_" +  fldId;
            String fldNm = "value(" + htmlFld + ")";
            String fldTyp = util.nvl(dao.getFLD_TYP(), "T");
            String valCond = util.nvl(dao.getVAL_COND(), "");
            if(valCond.indexOf("~fldId") > -1)
                valCond = valCond.replaceAll("~fldId", Integer.toString(fldId));
            
            String fldSz = util.nvl(dao.getFLD_SZ(),"10");
        %>
        <td>
        <%
            if(fldTyp.equals("T")) {
            %>
                <html:text property="<%=fldNm%>"  size="<%=fldSz%>"/>
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
            %>
        </td>    
        <%}
        
        if(msg.equalsIgnoreCase("NEW")) {%>
        <td>&nbsp;</td>
        <%} else {
            String link="/contact/nmegrpdtl.do?method=load&grpIdn="+ fldId ;
        %>
        <td><html:link page="<%=link%>" target="properties">Add/View Members</html:link></td>
        <%}%>
        </tr>  
    <%}
%>    
  
    <tr><td colspan="<%=colCnt%>" align="center">
<% if(msg.equalsIgnoreCase("NEW")) {%>    
    <html:submit property="addnew" value="Add New"  styleClass="submit"/>
<% } else { if(menuIds.size() > 0) {%>
    <html:submit property="modify" value="Save Changes"  onclick="return validateUpdate()"  styleClass="submit"/>
    &nbsp;<html:reset property="reset" value="Reset" styleClass="submit"/>
  
<% } }%>
    </td></tr>
        
<%  }%>
    
   </table></div></td></tr></table>
    </html:form></td>
    <td width="50%">
      <iframe name="properties" height="500" width="100%" frameborder="0" >
    </iframe>
    </td>
    
    </tr></table>
     <%@include file="../calendar.jsp"%>
  </body>
</html>
