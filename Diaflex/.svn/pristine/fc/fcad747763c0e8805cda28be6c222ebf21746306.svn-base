<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import="java.util.ArrayList,java.util.HashMap,java.sql.ResultSet,ft.com.*, java.util.Date, java.util.ArrayList, ft.com.dao.*"%>
<jsp:useBean id="info" class="ft.com.InfoMgr" scope="session" />
<jsp:useBean id="util" class="ft.com.JspUtil" scope="page" />
<%@taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>
<%@taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>Mix Stock Tally Issue</title>
      <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/bse.css?v=<%=info.getJsVersion()%>"/>
      <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/style.css?v=<%=info.getJsVersion()%>"/>
      <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/general.css?v=<%=info.getJsVersion()%>"/>
      <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/bse.js?v=<%=info.getJsVersion()%> " > </script>
      <script language="javaScript" type="text/javascript" src="<%=info.getReqUrl()%>/calendar/calendar.js"></script>
      <link href="<%=info.getReqUrl()%>/calendar/calendar.css" rel="stylesheet" type="text/css" />
     
  </head>
<%  
   int accessidn=Integer.parseInt(util.nvl((String)request.getAttribute("accessidn"),"0"));
    String logId=String.valueOf(info.getLogId());
     String onfocus="cook('"+logId+"')";
      HashMap allPageDtl= (info.getPageDetails() == null)?new HashMap():(HashMap)info.getPageDetails();
    HashMap pageDtl=(HashMap)allPageDtl.get("MIX_STK_TALLY");
    ArrayList pageList=new ArrayList();
       HashMap pageDtlMap=new HashMap();
     String fld_nme="",fld_ttl="",val_cond="",lov_qry="",fld_typ="",form_nme="";
        %>
 <body onfocus="<%=onfocus%>" onload="MM_preloadImages('images/faunalogo.png')" topmargin="0" leftmargin="0" rightmargin="0" bottommargin="0">
<div id="backgroundPopup"></div>
<div id="popupContactSave" align="center" >
<img src="../images/loading_flame.gif" height="150px" width="150px" border="0" />
</div>
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
  <img src="../images/line.jpg" border="0" /></td><td valign="middle"> <span class="pgHed">Mix Stock Tally Issue</span> </td>
</tr></table>
  </td>
  </tr>
  <tr><td valign="top" class="tdLayout">
    <html:form action="/mixAkt/mixStockTally.do?method=fetch" method="POST" onsubmit="return validatememoBoxsaleRadio()">
 <table  class="grid1">
  <tr><th>Generic Search 
   <%
  ArrayList rolenmLst=(ArrayList)info.getRoleLst();
  String usrFlg=util.nvl((String)info.getUsrFlg());
  if(usrFlg.equals("SYS") || rolenmLst.contains("ADMIN")){
  %>
  <img src="../images/edit.jpg" title="Edit Properties Module" onclick="newWindow('<%=info.getReqUrl()%>/dynamicProperty.do?method=load&mdl=MIX_TLLY_SRCH&sname=MIX_TLLY_SRCH&par=GENERIC')" border="0" width="15px" height="15px"/> 
  <%}%>
  </th></tr>
  
   <tr>
        <td>Status :&nbsp;
        <html:select property="value(stt)"  name="mixStockTallyForm" styleId="stt">
        <%pageList=(ArrayList)pageDtl.get("STATUS");
            if(pageList!=null && pageList.size() >0){
            for(int j=0;j<pageList.size();j++){
            pageDtlMap=(HashMap)pageList.get(j);
            fld_nme="value("+(String)pageDtlMap.get("fld_nme")+")";
            fld_ttl=(String)pageDtlMap.get("fld_ttl");
            fld_typ=(String)pageDtlMap.get("fld_typ");
            form_nme=(String)pageDtlMap.get("form_nme");
            lov_qry=(String)pageDtlMap.get("lov_qry");
            val_cond=(String)pageDtlMap.get("val_cond"); %>
            <html:option value="<%=lov_qry%>"><%=fld_ttl%></html:option>
            <%}}%>
        </html:select></td>
        </tr>
  <tr><td><jsp:include page="/genericSrch.jsp"/> </td></tr>
   <tr><td><html:submit property="value(submit)" value="Combination" styleClass="submit"/>
     <%
      pageList= ((ArrayList)pageDtl.get("BUTTON") == null)?new ArrayList():(ArrayList)pageDtl.get("BUTTON");
      if(pageList!=null && pageList.size() >0){
      for(int j=0;j<pageList.size();j++){
      pageDtlMap=(HashMap)pageList.get(j);
      fld_typ=(String)pageDtlMap.get("fld_typ");
      if(!fld_typ.equals("HB"))
      fld_nme="value("+(String)pageDtlMap.get("fld_nme")+")";
      else
      fld_nme=(String)pageDtlMap.get("fld_nme");
      val_cond=(String)pageDtlMap.get("val_cond");
      fld_ttl=(String)pageDtlMap.get("fld_ttl");
      val_cond=(String)pageDtlMap.get("val_cond");      
      val_cond=val_cond.replaceAll("URL",info.getReqUrl());
      if(fld_typ.equals("S")){ %>
<html:submit property="<%=fld_nme%>" value="<%=fld_ttl%>"  onclick="<%=val_cond%>" styleClass="submit" />
      <%}else if(fld_typ.equals("HB")){
%>
<button type="button" onclick="<%=val_cond%>" class="submit" accesskey="S" id="<%=fld_nme%>" name="<%=fld_nme%>" ><%=fld_ttl%></button>
<%
}}}
%> 
</td>
   </tr>
   </table>
   </html:form>
  </td></tr>
  
  
  </table>
  
    </body>
</html>