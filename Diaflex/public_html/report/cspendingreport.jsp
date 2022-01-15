<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page errorPage="../error_page.jsp" %>
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import="java.util.ArrayList,java.util.ArrayList,java.util.HashMap, java.sql.*, java.util.Enumeration, java.util.logging.Level"%>
<jsp:useBean id="info" class="ft.com.InfoMgr" scope="session" />
<jsp:useBean id="util" class="ft.com.JspUtil" scope="page" />
<%@taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>
 
<html>
  <head>
 <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
  
  <title>Pending  Reports</title>
  <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/bse.js?v=<%=info.getJsVersion()%> " > </script>
  <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/bse.css?v=<%=info.getJsVersion()%>"/>
  <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/ajax.js?v=<%=info.getJsVersion()%> " > </script>
  <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/style.css?v=<%=info.getJsVersion()%>"/>
  <script language="javaScript" type="text/javascript" src="<%=info.getReqUrl()%>/calendar/calendar.js"></script>
   <link href="<%=info.getReqUrl()%>/calendar/calendar.css" rel="stylesheet" type="text/css" />
  </head>
  
  <%
   ArrayList repMemoLst =(ArrayList)session.getAttribute("SAL_PND_VW");
  %>
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
  <tr>
  <td valign="top" class="hedPg">
  <table cellpadding="1" cellspacing="5"><tr><td valign="middle">
  <img src="../images/line.jpg" border="0" /></td><td valign="middle"> <span class="pgHed">
  <bean:write property="reportNme" name="orclReportForm" />
   </span> </td>
</tr></table>
  </td>
  </tr>
  <tr>
  <td valign="top" class="hedPg">
  <html:form action="report/orclReportAction.do?method=fetchcsPnd" method="post">
    <html:hidden property="value(PKT_TYP)" name="orclReportForm" />

  <table>
  <!--<tr><td>Party Name</td><td>
  <html:select property="value(byr)" name="orclReportForm" styleId="party" style="width:200px" >
    <html:option value="0">---select---</html:option>
    <html:optionsCollection property="byrList" name="orclReportForm" label="byrNme" value="byrIdn" />
  </html:select>
  </td></tr>-->
  <%
    HashMap dbinfo = info.getDmbsInfoLst();
    String cnt = (String)dbinfo.get("CNT");
  HashMap allPageDtl= (info.getPageDetails() == null)?new HashMap():(HashMap)info.getPageDetails();
  HashMap pageDtl=(HashMap)allPageDtl.get("CSPENDING_REPORT");
    ArrayList pageList=new ArrayList();
    HashMap pageDtlMap=new HashMap();
    String fld_nme="",fld_ttl="",val_cond="",dflt_val="",fld_typ="",form_nme="",lov_qry="",flg1="",fid="",tid="";
    pageList=(ArrayList)pageDtl.get("FIELDS");
    if(pageList!=null && pageList.size() >0){
    for(int j=0;j<pageList.size();j++){
    pageDtlMap=(HashMap)pageList.get(j);
    form_nme=(String)pageDtlMap.get("form_nme");
            fld_nme=(String)pageDtlMap.get("fld_nme");
            flg1=(String)pageDtlMap.get("flg1");
            if(flg1.equals("BYR")){
           String dfUsrtyp=util.nvl((String)info.getDfUsrTyp()); %>
   <%if(!dfUsrtyp.equals("EMPLOYEE")){%>
   <tr>
   <td> Employee : </td>
   <td>
   <html:select property="value(empIdn)" styleId="empIdn" name="orclReportForm"   >
           <html:option value="0" >--select--</html:option>
            <html:optionsCollection property="empList" name="orclReportForm" 
            label="byrNme" value="byrIdn" />
   </html:select>
   </td>
   </tr>
   <%}%>
   <tr>
<td><%=fld_nme%></td>
<td>
 <html:select property="byrIdn" name="orclReportForm" styleId="byrId" >
    <html:option value="0">---select---</html:option>
    <html:optionsCollection property="byrList" name="orclReportForm"  value="byrIdn" label="byrNme" />      
  </html:select>     
</td></tr> 
<%if(cnt.equals("hk")){%>
   <tr>
   <%
   ArrayList grpList = ((ArrayList)info.getGroupcompany() == null)?new ArrayList():(ArrayList)info.getGroupcompany();
   if(grpList!=null && grpList.size()> 0){
   %>
      <td>Group Company</td>
      <td><html:select name="orclReportForm" property="value(group)" styleId="grp">
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
   <%}%>
<%}if(flg1.equals("ADATE")){%>
  <tr><td><%=fld_nme%></td>
  <td>
    <table><tr>
      <td>From</td><td>
        <div class="float"> <html:text property="value(appfrmDte)"  styleId="appfrmDte" /></div>
        <div class="float"> <a href="#"  onClick="setYears(1900, <%=info.getCurrentYear()%>);showCalender(this, 'appfrmDte');">
          <img src="<%=info.getReqUrl()%>/images/calender.png"></a></div>
      </td>
      <td>To</td><td>
        <div class="float"> <html:text property="value(apptoDte)"  styleId="apptoDte" /></div>
        <div class="float"> <a href="#"  onClick="setYears(1900, <%=info.getCurrentYear()%>);showCalender(this, 'apptoDte');">
          <img src="<%=info.getReqUrl()%>/images/calender.png"></a></div>
      </td>
    </tr></table>
  </td>
  </tr><%}}%>
  <tr>
  <td colspan="2" nowrap="nowrap"><jsp:include page="/genericSrch.jsp" /> </td>
  </tr> 
   <tr><td colspan="2" align="center"> <html:submit property="value(submit)" styleClass="submit" value="Submit" /> </td> </tr>
   <%}%>
  </table>
  </html:form></td>
  </tr>
  
  <%
  ArrayList prpDspBlocked = info.getPageblockList();
  ArrayList rolenmLst=(ArrayList)info.getRoleLst();
  String usrFlg=util.nvl((String)info.getUsrFlg());
  String allowData=util.nvl((String)request.getAttribute("allowData"),"Y");
  if(usrFlg.equals("SYS") || rolenmLst.contains("ADMIN")){
  %><tr><td valign="top" class="hedPg">
  <img src="../images/edit.jpg" title="Edit Properties Module" onclick="newWindow('<%=info.getReqUrl()%>/dynamicProperty.do?method=load&mdl=SAL_PND_VW&sname=SAL_PND_VW&par=A')" border="0" width="15px" height="15px"/>
  </td></tr>
  <%}%>
  
  <tr><td valign="top" class="hedPg" nowrap="nowrap"  >
  <%if(allowData.equals("Y")){%>
  <display:table name="sessionScope.orclReportForm.pktList" class="displayTable" requestURI="" export="true"  sort="list" decorator="ft.com.Decortor">
   <display:column property="sal" style="font-weight: bold;" title="Sale Person" group="1" />
    <display:column property="byr" style="font-weight: bold;" title="Buyer" group="2" />
    <display:column property="memoIdn"  title="Id" group="3" />
    <display:column property="vnm"  title="Packet No."  />
     <display:column property="dte"  title="Date"  />
     <display:column property="days"  title="Days"  />
     <display:column property="memoQuot"  title="Quot"  />
     <display:column property="rte"  title="Prc / Crt"  />
     <display:column property="amt"  title="Amount" media="html"   />
     <display:column property="amtex"  title="Amount" media="excel"  />
    <% for (int j = 0; j < repMemoLst.size(); j++) {
     String prp = (String)repMemoLst.get(j);
     if (prp.toUpperCase().equals("CERT NO.") || prp.toUpperCase().equals("CERT_NO")){
     prp="CERT_NO";
     }
    if(prpDspBlocked.contains(prp)){
    }else{%>
  <display:column property="<%=prp%>" title="<%=prp%>"  /> 
  <%}}%>
  <display:setProperty name="export.excel.filename" value="Details.xls"/>
  <display:setProperty name="export.xls" value="true" />
    <display:setProperty name="export.csv" value="false" />
     <display:setProperty name="export.xml" value="false" />
  </display:table>
  <%}%>
  </td></tr>
  <tr>
    <td><jsp:include page="/footer.jsp" /> </td>
    </tr>
  </table>
   <%@include file="../calendar.jsp"%>
  </body>
</html>
  
  