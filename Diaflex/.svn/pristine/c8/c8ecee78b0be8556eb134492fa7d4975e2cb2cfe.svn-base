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
    <title>Customer Terms Approve</title>
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
  <tr><td valign="top" class="hedPg">
  <label class="pgTtl">Pending List</label>
  <%
    String formName = "customerterms";
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
  
  %>
  <table class="grid1">
  <tr><th>Sr</th>
   <th>Action</th>
    <th>Buyer</th>
  <%
    for(int j=0; j < daos.size(); j++) {
            UIFormsFields dao = (UIFormsFields)daos.get(j);
            String lFld = dao.getFORM_FLD();
            String fldTtl = util.nvl(dao.getDSP_TTL(), lFld);
            String isReq =util.nvl(dao.getREQ_YN());
            %>
            
            <th nowrap="nowrap"> <%
            if(isReq.equals("Y")){%>
            <span class="redTxt">*</span>
          <%  }
            %><%=fldTtl%></th>
    <%}%>  
   <th nowrap="nowrap">Modified By</th>
   <th nowrap="nowrap">Log Date</th>
  </tr>
  <%
    ArrayList lists = dbutil.getTrmApprList() ;
    ArrayList list = (ArrayList)lists.get(0);
    ArrayList logList = (ArrayList)lists.get(1);
    String lIdn = "";
    for(int i=0; i < list.size(); i++) {%>
        <tr>
        <td><%=(i+1)%></td>
    <%
        NmeRel lDao = (NmeRel)list.get(i);
        lIdn = lDao.getIdn();
        String nmeIdn = lDao.getNmeIdn();
        //String byr = util.getNme(nmeIdn);
        String edtLnk = "<a href=\""+ info.getReqUrl() + "/contact/nmerel.do?method=load&nmeIdn="+ nmeIdn + "&idn="+lIdn+"\">Edit</a>";
        
         String link = info.getReqUrl() + "/contact/nmerel.do?method=del&nmeIdn="+nmeIdn+"&idn="+lIdn;
         String onclick = "return setDeleteConfirm('"+link+"')";
         String delLnk = "<a href=\""+link +" \" onclick=\""+onclick+"  \">Del</a>";
       
        
     //   String delLnk = "<a href=\""+ info.getReqUrl() + "/contact/nmerel.do?method=del&nmeIdn="+ nmeIdn + "&idn="+lIdn+"\">Del</a>";
     
        String appLnk = "<a href=\""+ info.getReqUrl() + "/contact/nmerel.do?method=approve&nmeIdn="+ nmeIdn + "&idn="+lIdn+"\">Approve</a>";
    %>
      <td>  <%=appLnk%></td>
    <td nowrap="nowrap"><%=lDao.getValue("byr")%></td>
    <%
        for(int j=0; j < daos.size(); j++) {
            UIFormsFields dao = (UIFormsFields)daos.get(j);
            String lFld = dao.getFORM_FLD();
            String lVal = util.nvl((String)lDao.getValue(lFld));%>
            <td nowrap="nowrap"><%=lVal%></td>  
        <%}%>
    <td nowrap="nowrap"><%=util.nvl((String)lDao.getValue("mod_usr"))%></td>
    <td nowrap="nowrap"></td>
     </tr>   
  <%
      for(int j=0; j < logList.size(); j++) {
        NmeRel logDao = (NmeRel)logList.get(j);
        String lLogIdn = logDao.getIdn();
        if(lIdn.equals(lLogIdn)) {%>
          <tr bgcolor="#CCCCCC">
            <td></td>
            <td>Log</td>
            <td>&nbsp;</td>
       <%
       for(int k=0; k < daos.size(); k++) {
            UIFormsFields dao = (UIFormsFields)daos.get(k);
            String lFld = dao.getFORM_FLD();
            String lVal = util.nvl((String)logDao.getValue(lFld));%>
            <td><%=lVal%></td>  
        <%}%>
        <td nowrap="nowrap"><%=util.nvl((String)logDao.getValue("mod_usr"))%></td>
         <td nowrap="nowrap"><%=util.nvl((String)logDao.getValue("log_dt"))%></td>
          </tr>
        <%}
      }
    }
  %>
  </table></td></tr>
  <tr>
    <td><jsp:include page="/footer.jsp" /> </td>
    </tr>
  </table>
    <%@include file="../calendar.jsp"%>
  </body>
</html>  
  