<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page errorPage="../error_page.jsp" %>
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import="java.util.ArrayList,java.util.List,java.util.HashMap,java.sql.ResultSet,ft.com.*, java.util.Date, java.util.ArrayList, ft.com.dao.*"%>
<jsp:useBean id="info" class="ft.com.InfoMgr" scope="session" />
<jsp:useBean id="util" class="ft.com.JspUtil" scope="page" />
<%@taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>
<%@taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>Contact Search</title>
    <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/bse.css?v=<%=info.getJsVersion()%>"/>
    <script language="javaScript" type="text/javascript" src="<%=info.getReqUrl()%>/calendar/calendar.js"></script>
   <link href="<%=info.getReqUrl()%>/calendar/calendar.css" rel="stylesheet" type="text/css" />
   <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/bse.js?v=<%=info.getJsVersion()%> " > </script>
     <script language="javaScript" type="text/javascript" src="<%=info.getReqUrl()%>/scripts/ajax.js?v=<%=info.getJsVersion()%>"></script>
      <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/style.css?v=<%=info.getJsVersion()%>"/>
      <script type="text/javascript" src="<%=info.getReqUrl()%>/PopScripts/popup.js?v=<%=info.getJsVersion()%> " > </script>
   <script src="<%=info.getReqUrl()%>/PopScripts/jquery-1.2.6.min.js" type="text/javascript"></script>
    <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/general.css?v=<%=info.getJsVersion()%>"/>
        <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/assortScript.js?v=<%=info.getJsVersion()%> " > </script>
  </head>
        <%String logId=String.valueOf(info.getLogId());
        String onfocus="cook('"+logId+"')";
        %>
 <body onfocus="<%=onfocus%>" onload="MM_preloadImages('images/faunalogo.png')" topmargin="0" leftmargin="0" rightmargin="0" bottommargin="0">
 <input type="hidden" id="reqUrl" name="reqUrl" value="<%=info.getReqUrl()%>"/>
 <div id="backgroundPopup"></div>
<div id="popupDashPOP" >
<table class="popUpTb">
 <tr>
 <td height="5%" id="close">&nbsp;&nbsp;&nbsp;<html:button property="value(close)" styleClass="submit"  onclick="MKEmptyPopup('SC');disablePopupASSFNL();" value="Close"  /> </td>
 <td height="5%" id="closereload" style="display:none">&nbsp;&nbsp;&nbsp;<html:button property="value(close)" styleClass="submit"  onclick="MKEmptyPopup('SC'); disablePopupASSFNL();reportUploadclose('CNTFEEDBACK');" value="Close"  /></td>
 </tr>
<tr><td valign="top" height="95%">
<iframe name="SC" class="frameStyle" width="500px" height="500px"   align="middle" frameborder="0">
<jsp:include page="/emptyPg.jsp" />
</iframe></td></tr>
</table>
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
<% if(request.getAttribute("MSG")!=null){%>
<tr><td height="15" valign="top" class="hedPg"><span class="redLabel"> <%=request.getAttribute("MSG")%></span></td></tr>
<%}%>
  <tr><td valign="top" class="hedPg">


  <bean:parameter id="reqNmeIdn" name="nmeIdn" value="0"/>
    
  <%
    String srchby = util.nvl((String)session.getAttribute("srchby"));
    String cntmrg = util.nvl((String)session.getAttribute("cntmrg"));
     ArrayList byrList = (ArrayList)session.getAttribute("byrList");
     ArrayList itemHdr = (ArrayList)session.getAttribute("Feeditemhdr");
 HashMap allPageDtl= (info.getPageDetails() == null)?new HashMap():(HashMap)info.getPageDetails();
 HashMap pageDtl=(HashMap)allPageDtl.get("CONTACT_SRCH");
                ArrayList pageList=new ArrayList();
                HashMap pageDtlMap=new HashMap();
                String fld_ttl="";
                String dflt_val="";
                String lov_qry="";
                String flg1="";
                String val_cond="",fld_nme="",fld_typ="";%>
  
  <label id="loading"></label>
  <input type="button" id="BackToForm" onclick="goTo('<%=info.getReqUrl()%>//contact/advcontact.do?method=load&srch=<%=srchby%>','','')"  value="Back To Form"  class="submit"/>
<br>  
<br><br>
  <table class="grid1" id="dataTable">
   <%
  if(byrList.size() > 0 && byrList!=null) { %>
  <tr  id="DTL_1">
  <th>Sr No.</th>
   <th>Feedback</th>
<%
  for(int i=0; i<itemHdr.size(); i++){ %>
  <th nowrap="nowrap"><%=itemHdr.get(i)%></th>
  <%}%>
      <%pageList= ((ArrayList)pageDtl.get("VIEW") == null)?new ArrayList():(ArrayList)pageDtl.get("VIEW");
      if(pageList!=null && pageList.size() >0){
      for(int j=0;j<pageList.size();j++){
      pageDtlMap=(HashMap)pageList.get(j);
      dflt_val=(String)pageDtlMap.get("dflt_val");
      fld_ttl=(String)pageDtlMap.get("fld_ttl");
      if(dflt_val.equals("Y")){%>
      <th nowrap="nowrap"><%=fld_ttl%></th>
      <%}}}%>
     <th nowrap="nowrap">Complete Profile</th>
  </tr>

  <% 
  for(int j=0; j < byrList.size(); j++) {
              HashMap data = (HashMap)byrList.get(j);
              String nme=(String)data.get("Nme");
              String NmeIdn=(String)data.get("NmeIdn");
              String EmpIdn=(String)data.get("EmpIdn");
              String id="BYR_"+NmeIdn;
  %>
  
     <tr >
     
        <td><%=j+1%></td>
        <td align="center"><img src="../images/plus.png" border="0" onclick="clientFeedback('<%=EmpIdn%>','<%=NmeIdn%>')" id="<%=id%>"></img></td>
          <%for(int k=0;k<itemHdr.size() ;k++){
                String hdr = (String)itemHdr.get(k);  
            %>
  <td nowrap="nowrap"><%=util.nvl((String)data.get(hdr))%></td>
  <%}%>
       <%
       String edtLnk = "<a target=\"fix\" href=\""+ info.getReqUrl() + "/contact/nme.do?method=load&fromFeedback=Y&nmeIdn="+ NmeIdn + "\">View / Edit</a>";
       pageList= ((ArrayList)pageDtl.get("VIEW") == null)?new ArrayList():(ArrayList)pageDtl.get("VIEW");
       if(pageList!=null && pageList.size() >0){
       for(int k=0;k<pageList.size();k++){
       pageDtlMap=(HashMap)pageList.get(k);
       dflt_val=(String)pageDtlMap.get("dflt_val");
       fld_ttl=(String)pageDtlMap.get("fld_ttl");
       if(dflt_val.equals("Y")){%>
       <td nowrap="nowrap"><%=edtLnk%></td>
       <%}}}%>
        <td nowrap="nowrap"><a target="fix" href="<%=info.getReqUrl()%>/contact/NmeReport.jsp?view=Y&nmeIdn=<%=NmeIdn%>" >Complete Profile</a></td>
      </tr>   
  <%}}else{%>
  <tr><td valign="top" style="font-size:14px">
Sorry No Result Found</td></tr> 
 <% }%>
  </table>

  </form>
  
 <%@include file="../calendar.jsp"%>
 </td></tr>
 <tr>
    <td><jsp:include page="/footer.jsp" /> </td>
    </tr>
 </table>
  </body>
</html>  
  