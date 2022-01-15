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
    <title>Contact Search</title>
    <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/bse.css?v=<%=info.getJsVersion()%>"/>
      <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/style.css?v=<%=info.getJsVersion()%>"/>
       <script language="javaScript" type="text/javascript" src="<%=info.getReqUrl()%>/calendar/calendar.js"></script>
   <link href="<%=info.getReqUrl()%>/calendar/calendar.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/bse.js?v=<%=info.getJsVersion()%>"></script> 
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
  <tr><td valign="top">
  
  <%
    ArrayList params = new ArrayList();
    ResultSet rs = null;
    String formName = "searchcontact";
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
    pgTtl = pgTtl.replaceAll("~formName", formName);
    int addRec = Integer.parseInt(util.nvl(uiForms.getREC(),"10"));
    
  %>
    <html:form action="searchContact.do" method="post">  
  <label class="pgTtl"><%=pgTtl%></label>
  <table id="grid1" width="95%">
  
  <%
    for(int j=0; j < daos.size(); j++) {
            UIFormsFields dao = (UIFormsFields)daos.get(j);
            String lFld = dao.getFORM_FLD();
            String htmlFld = lFld;
            String fldNm = "value(" + htmlFld + ")";
            String fldTtl = util.nvl(dao.getDSP_TTL(), lFld);
            String fldTyp = util.nvl(dao.getFLD_TYP(), "T");
            String fldSz = util.nvl(dao.getFLD_SZ(),"10");
            
            String valCond = util.nvl(dao.getVAL_COND(), "");
              String isReq =util.nvl(dao.getREQ_YN());
    %>
        <tr>
            <th>
            <% if(isReq.equals("Y")){%>
            <span class="redTxt">*</span>
          <%  }else{%>
          &nbsp;&nbsp;
          <%}%>
            <%=fldTtl%></th>
            <td>
   <%
            if(fldTyp.equals("FT")) {
                String ftFld = "value(" + htmlFld + "_fltr)";
                %>
                <html:select property="<%=ftFld%>">
                    <html:option value="C">Contains</html:option>
                    <html:option value="S">Starts With</html:option>
                    <html:option value="E">Ends With</html:option>
                </html:select>
            <%}
            if((fldTyp.equals("T")) || (fldTyp.equals("FT"))) {
            %>
                <html:text property="<%=fldNm%>" size="<%=fldSz%>"/>
            <%} 
            if((fldTyp.equals("S")) || (fldTyp.equals("L"))) {
                String lovKey = lFld + "LOV";
                fldNm += "\" " + valCond;
                
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
                    <html:text property="<%=fldNm%>"/>
                <%} else {
                %>
                <html:select property="<%=fldNm%>" styleId="<%=htmlFld%>">
                    <html:option value="0">Select</html:option>
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
            </tr>
    <%
    }    
  %>
  </table>
  <html:submit property="search" value="Search"/>
  </html:form></td></tr></table>
   <%@include file="../calendar.jsp"%>
  </body>
</html>  
  