<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page errorPage="../error_page.jsp" %>
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.ResultSet, java.util.ArrayList,java.util.Collections,java.util.ArrayList,java.util.HashMap,java.text.SimpleDateFormat, java.util.Calendar"%>
	<jsp:useBean id="info" class="ft.com.InfoMgr" scope="session" />
	<jsp:useBean id="util" class="ft.com.JspUtil" scope="page" />

<%@taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>
<%@taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<html> 
   <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/bse.js?v=<%=info.getJsVersion()%> " > </script>
<link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/bse.css?v=<%=info.getJsVersion()%>"/>
<link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/general.css?v=<%=info.getJsVersion()%>"/>
<script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/ajax.js?v=<%=info.getJsVersion()%> " > </script>
  <script type="text/javascript" src="<%=info.getReqUrl()%>/PopScripts/popup.js?v=<%=info.getJsVersion()%> " > </script>
    <script src="<%=info.getReqUrl()%>/PopScripts/jquery-1.2.6.min.js" type="text/javascript"></script>
   <script language="javaScript" type="text/javascript" src="<%=info.getReqUrl()%>/calendar/calendar.js"></script>
   <link href="<%=info.getReqUrl()%>/calendar/calendar.css" rel="stylesheet" type="text/css" />

 
      <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/style.css?v=<%=info.getJsVersion()%>"/>

  <title>Repeat Customer</title>
 
  </head>
  
  <%
  HashMap prp = info.getPrp();
  ArrayList deptPrpDtl = (ArrayList)prp.get("DEPT"+"V");
      HashMap allPageDtl= (info.getPageDetails() == null)?new HashMap():(HashMap)info.getPageDetails();
    HashMap pageDtl=(HashMap)allPageDtl.get("REPEAT_CUSTOMER");
    ArrayList pageList=new ArrayList();
    HashMap pageDtlMap=new HashMap();
    String fld_nme="",fld_ttl="",val_cond="",dflt_val="",fld_typ="",form_nme="",flg1="",lov_qry="";
  String logId=String.valueOf(info.getLogId());
        String onfocus="cook('"+logId+"')";
        %>
 <body onfocus="<%=onfocus%>" onload="MM_preloadImages('images/faunalogo.png')" topmargin="0" leftmargin="0" rightmargin="0" bottommargin="0">
  <table cellpadding="" cellspacing="" class="mainbg">
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
  <img src="../images/line.jpg" border="0" /></td><td valign="middle"> <span class="pgHed">Repeat Customer</span> </td>
</tr></table>
  </td>
  </tr>
<tr><td valign="top" class="hedPg">
<html:form action="/report/orclRptAction.do?method=fetchcust"  method="POST">
 <table class="grid1">
            <tr><th colspan="2">Customer Search </th></tr>
            <%
            pageList=(ArrayList)pageDtl.get("DEPT");
            if(pageList!=null && pageList.size() >0){
            for(int j=0;j<pageList.size();j++){%>
            <tr>
            <td>Department</td>
            <td>
            <html:select property="value(dept)"  styleId="dept" name="orclReportForm" >
             <%for(int i=0;i<deptPrpDtl.size();i++){
                String dept=(String)deptPrpDtl.get(i);
             %>
             <html:option value="<%=dept%>" ><%=dept%></html:option>
             <%}%>
             </html:select>
            </td>
            </tr>
            <%}}
            
            pageList=(ArrayList)pageDtl.get("SALEX");
            if(pageList!=null && pageList.size() >0){
            for(int j=0;j<pageList.size();j++){%>
            <tr>
            <td><span class="txtBold" >Sale Person  </span></td>
            <td>
      <%
      ArrayList salepersonList = ((ArrayList)info.getSaleperson() == null)?new ArrayList():(ArrayList)info.getSaleperson(); 
      %>
      <html:select name="orclReportForm" property="value(styp)" styleId="saleEmp">
      <html:option value="">---Select---</html:option>
      <%
      for(int i=0;i<salepersonList.size();i++)
      {
      ArrayList saleperson=(ArrayList)salepersonList.get(i);
      %>
      <html:option value="<%=(String)saleperson.get(0)%>"> <%=(String)saleperson.get(1)%> </html:option>
      <%
      }
      %>
      </html:select>
      </td>
      </tr>
      <%}}
            pageList=(ArrayList)pageDtl.get("GRPCO");
            if(pageList!=null && pageList.size() >0){
            for(int j=0;j<pageList.size();j++){
   ArrayList grpList = ((ArrayList)info.getGroupcompany() == null)?new ArrayList():(ArrayList)info.getGroupcompany();
   if(grpList!=null && grpList.size()> 0){
   %>
      <tr>
      <td>Group Company</td>
      <td><html:select name="orclReportForm" property="value(group)" styleId="grp">
      <html:option value="">---Select---</html:option>
      <%
      for(int i=0;i<grpList.size();i++)
      {
      ArrayList grp=(ArrayList)grpList.get(i);
      %>
      <html:option value="<%=(String)grp.get(0)%>"> <%=(String)grp.get(1)%> </html:option>
      <%
      }
      %>
      </html:select>
      </td>
      </tr>
      <%}}}%>
           <tr>
           <td>Sale Period From</td>
           <td>
           <html:select property="value(salefrmon)"  styleId="salefrmon" name="orclReportForm" >
           <html:optionsCollection property="monthList" name="orclReportForm"  value="nme" label="dsc" />
           </html:select>&nbsp;
           <html:select property="value(salefryr)"  styleId="salefryr" name="orclReportForm" >
           <html:optionsCollection property="yrList" name="orclReportForm"  value="nme" label="nme" />
           </html:select>&nbsp;
           To
           <html:select property="value(saletomon)"  styleId="saletomon" name="orclReportForm" >
           <html:optionsCollection property="monthList" name="orclReportForm"  value="nme" label="dsc" />
           </html:select>&nbsp;
           <html:select property="value(saletoyr)"  styleId="saletoyr" name="orclReportForm" >
           <html:optionsCollection property="yrList" name="orclReportForm"  value="nme" label="nme" />
           </html:select>&nbsp;
           </td>
           </tr>
           <tr>
           <td>Comp Period From</td>  
           <td>
           <html:select property="value(compfrmon)"  styleId="compfrmon" name="orclReportForm" >
           <html:optionsCollection property="monthList" name="orclReportForm"  value="nme" label="dsc" />
           </html:select>&nbsp;
          <html:select property="value(compfryr)"  styleId="compfryr" name="orclReportForm" >
           <html:optionsCollection property="yrList" name="orclReportForm"  value="nme" label="nme" />
           </html:select>&nbsp;
           To
            <html:select property="value(comptomon)"  styleId="comptomon" name="orclReportForm" >
           <html:optionsCollection property="monthList" name="orclReportForm"  value="nme" label="dsc" />
           </html:select>&nbsp;
          <html:select property="value(comptoyr)"  styleId="comptoyr" name="orclReportForm" >
           <html:optionsCollection property="yrList" name="orclReportForm"  value="nme" label="nme" />
           </html:select>&nbsp;
           </td>
           </tr>
      <tr><td colspan="2" align="center"><html:submit property="value(srch)" value="Fetch" styleClass="submit" onclick="return validatkaccha()" /></td> </tr>
 </table>
</html:form>
</td>
</tr>
  <%
    ArrayList byrLst= (ArrayList)session.getAttribute("byrLstRpt");
    String view= util.nvl((String)request.getAttribute("view"));
    String count= util.nvl((String)session.getAttribute("countrpt"));
    HashMap data=new HashMap();
    ArrayList keystt=new ArrayList();
    String stt="";
    if(view.equals("Y")){
    if(byrLst!=null && byrLst.size()>0){
    int cnt=0;
    int sr=0;
    int boxcnt=(byrLst.size()/3)+1;
    int follow=byrLst.size()-Integer.parseInt(count);
    int size=byrLst.size();
    
  %>
  <tr><td valign="top" class="hedPg">
  <table>
  <tr><td>
  Create Excel <img src="../images/ico_file_excel.png" onclick="goTo('orclReportAction.do?method=createrepetcustXL','','')" border="0"/> 
   </td>
   <td>&nbsp;Total&nbsp; :- <%=size%></td>
   <td><div class="green">&nbsp;</div></td><td>Repeat Customer :- <%=count%> | 
   <%=util.Round((((Double.parseDouble(count))/Double.parseDouble(String.valueOf(size)))*100),2)%>%&nbsp;</td>
   <td><div class="red">&nbsp;</div></td><td> Follow Up :- <%=follow%> | 
   <%=util.Round((((Double.parseDouble(String.valueOf(follow)))/Double.parseDouble(String.valueOf(size)))*100),2)%>%</td>
 </table>
  </td>
  </tr>
  <tr><td valign="top" class="hedPg">
  <table class="grid1" align="left">
  <tr>
  <th>Sr No </th><th>Party</th>
  <th>Sr No </th><th>Party</th>
  <th>Sr No </th><th>Party</th>
  </tr>
  <%for(int i=0;i<boxcnt;i++){
  sr=i+1;
  data=new HashMap();
  data= ((HashMap)byrLst.get(i) == null)?new HashMap():(HashMap)byrLst.get(i);
  String party=util.nvl((String)data.get("BYR"));
  String color=util.nvl((String)data.get("COLOR"));
  String cla="color:"+color;
  %>
  <tr>
  <td style="<%=cla%>"><%=sr%></td>
  <td nowrap="nowrap" style="<%=cla%>"><%=party%></td>
  <%
  data=new HashMap();
  sr=sr+boxcnt;
  data= ((HashMap)byrLst.get(sr-1) == null)?new HashMap():(HashMap)byrLst.get(sr-1);
  party=util.nvl((String)data.get("BYR"));
  color=util.nvl((String)data.get("COLOR"));
  cla="color:"+color;
  %>
  <td style="<%=cla%>"><%=sr%></td>
  <td nowrap="nowrap" style="<%=cla%>"><%=party%></td>
  <%
  data=new HashMap();
  sr=sr+boxcnt;
  if(sr<=size){
  data= ((HashMap)byrLst.get(sr-1) == null)?new HashMap():(HashMap)byrLst.get(sr-1);
  party=util.nvl((String)data.get("BYR"));
  color=util.nvl((String)data.get("COLOR"));
  cla="color:"+color;
  %>
  <td style="<%=cla%>"><%=sr%></td>
  <td nowrap="nowrap" style="<%=cla%>"><%=party%></td>
  <%}else{%>
  <td></td>
  <td></td>
  <td></td>
  <%}%>
  </tr>
  <%}%>
  
  </table>
  
  </td>
  </tr>
  
<%
}else{%>
<tr><td valign="top" class="hedPg">
Sorry No Result Found</td></tr>
<%}}%>
  <tr><td>
<jsp:include page="/footer.jsp" />
</td></tr>
  </table>  
  </body>
  <%@include file="../calendar.jsp"%>
</html>