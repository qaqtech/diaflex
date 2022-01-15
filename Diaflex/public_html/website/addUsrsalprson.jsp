<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page errorPage="../error_page.jsp" %>
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import="ft.com.*,java.util.ArrayList,java.util.ArrayList, java.util.Iterator,java.util.List,java.io.*, java.sql.Date,java.util.HashMap, ft.com.marketing.PacketLookupForm" %>

<jsp:useBean id="util" class="ft.com.JspUtil" scope="page" />
<jsp:useBean id="info" class="ft.com.InfoMgr" scope="session" />
<%@taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>
<%@taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<html>
  <head>
  <meta name="Keywords" content="javascript,dhtml,html,navigation,pop ups,web tools,graphics" />
  <meta name="Description" content="free javascrip, software and graphic programs,pop up windows,menus,mouseover effects" />
  <meta http-equiv="Content-Language" content="en-gb"/>
  <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
      <title>Multi Images Send</title>
      <script src="../scripts/bse.js?v=<%=info.getJsVersion()%>" type="text/javascript"></script>
      <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/bse.css?v=<%=info.getJsVersion()%>"/>
      <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/style.css?v=<%=info.getJsVersion()%>"/>
      <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/memoScript.js?v=<%=info.getJsVersion()%> " > </script>
      <script src="<%=info.getReqUrl()%>/PopScripts/jquery-1.2.6.min.js" type="text/javascript"></script>
      <script src="<%=info.getReqUrl()%>/jqueryScript/jqueryScript.js?v=<%=info.getJsVersion()%>" type="text/javascript"></script>
      <script src="<%=info.getReqUrl()%>/jqueryScript/jquery.min.js" type="text/javascript"></script>  
      <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/bse.js?v=<%=info.getJsVersion()%> " > </script>
      <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/ajax.js?v=<%=info.getJsVersion()%> " > </script>
      <script type="text/javascript" src="<%=info.getReqUrl()%>/PopScripts/popup.js?v=<%=info.getJsVersion()%> " > </script>
      <meta name="GENERATOR" content="Microsoft FrontPage 4.0">
      <meta name="ProgId" content="FrontPage.Editor.Document"><META HTTP-EQUIV="imagetoolbar" CONTENT="no">
   </head>
        <%
        DBMgr db = new DBMgr();
        DBUtil dbutil = new DBUtil();
        db.setCon(info.getCon());
      dbutil.setDb(db);
      dbutil.setInfo(info);
      db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));db.setDbUsr(info.getDbUsr());
      dbutil.setLogApplNm(info.getLoApplNm());
        String logId=String.valueOf(info.getLogId());
        String onfocus="cook('"+logId+"')";
        String regnIdn=util.nvl((String)request.getAttribute("regnIdn"));
        String edit_contact=util.nvl((String)request.getAttribute("edit_contact"));
        String NmeIdn=util.nvl((String)request.getAttribute("nme_idn"));
        String msg=util.nvl((String)request.getAttribute("msg"));
                 HashMap dbinfo = info.getDmbsInfoLst();
          String cnt = util.nvl((String)dbinfo.get("CNT"));
HashMap pageDtlMap=new HashMap();
String fld_nme="",fld_ttl="",val_cond="",dflt_val="",fld_typ="",form_nme="",lov_qry="";
HashMap allPageDtl= (info.getPageDetails() == null)?new HashMap():(HashMap)info.getPageDetails();
HashMap pageDtl=(HashMap)allPageDtl.get("PENDING_REG");
ArrayList pageList=new ArrayList();
        %>
        
 <body onfocus="<%=onfocus%>">
    <table align="center"> 
     <%  if(!msg.equals("") && msg!=null) {%>
    <tr><td valign="top" class="hedPg"> <span class="blueLabel" > <%=msg%></span></td></tr>
    <%}%>
    <tr>
    <td valign="top" class="hedPg">
    <%
      if(edit_contact.equals("Y")){
      pageList= ((ArrayList)pageDtl.get("EDIT_CONTACT") == null)?new ArrayList():(ArrayList)pageDtl.get("EDIT_CONTACT");
       if(pageList!=null && pageList.size() >0){
       String edtLnk = "<a target=\"fix\" href=\""+ info.getReqUrl() + "/contact/nme.do?method=load&fromFeedback=Y&nmeIdn="+ NmeIdn + "\">View / Edit Contact</a>";
       %>
       &nbsp;&nbsp;<b><%=edtLnk%></b>
       <%}}%>
    </td>
    </tr>
    <%if(edit_contact.equals("")){
    %>
<tr>
 <td>
<html:form  action="website/AcceptRegistration.do?method=saveRegn" method="post" onsubmit="return validsaleandgroup();" >
<html:hidden property="value(regnIdn)" name="pendingRegForm" styleId="regnIdn" value="<%=regnIdn%>"/>
<input type="hidden" id="cnt" name="cnt" value="<%=cnt%>"/>
<table>
  <tr>
   <%
  ArrayList  grpList=dbutil.groupcompany();
   session.setAttribute("grpcompanyList", grpList);
 
   if(grpList!=null && grpList.size()> 0){
   %>
      <td>Group Company :</td>
      <td><html:select name="pendingRegForm" property="value(groupIdn)" styleId="grp" onchange="populateSalePerson('grp','emp')">
      <html:option value="">---Select---</html:option>
      <%
      for(int i=0;i<grpList.size();i++){
      ArrayList grp=(ArrayList)grpList.get(i);
      %>
      <html:option value="<%=(String)grp.get(0)%>"> <%=(String)grp.get(1)%> </html:option>
      <%}%>
      </html:select>
      </td>
      <%}%>
   </tr>
   <tr><% ArrayList  empList=dbutil.allsaleperson();
    session.setAttribute("salepersonList", empList);
   
   if(empList!=null && empList.size()> 0){
   %>
   <td>Sale Person : </td>
     <td> <html:select name="pendingRegForm" property="value(empIdn)" styleId="emp">
      <html:option value="">---Select---</html:option>
      <%
      for(int i=0;i<empList.size();i++){
      ArrayList emp=(ArrayList)empList.get(i);
      %>
      <html:option value="<%=(String)emp.get(0)%>"> <%=(String)emp.get(1)%> </html:option>
      <%}%>
      </html:select>
      </td>
      <%}%>
   </tr>
   <tr></tr>
     <tr align="right"><td>
      <html:submit property="value(submit)" styleClass="submit" value="Add Contact"  />
      </td></tr>
     </table> 
    </html:form>
    </td>
   </tr>
   <%}%>
  </table>
 </body>
</html>