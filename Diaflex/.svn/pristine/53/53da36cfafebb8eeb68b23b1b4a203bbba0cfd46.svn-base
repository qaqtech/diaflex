<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page errorPage="../error_page.jsp" %>
<%@ page contentType="text/html;charset=windows-1252"%>
<jsp:useBean id="info" class="ft.com.InfoMgr" scope="session" />
<jsp:useBean id="util" class="ft.com.JspUtil" scope="page" />
<%@ page import="java.util.ArrayList,java.util.ArrayList,java.util.HashMap, java.sql.*, java.util.Enumeration, java.util.logging.Level"%>
<%@taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<html>
  <head>
   <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>Accept Registration</title>
    <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/bse.css?v=<%=info.getJsVersion()%>"/>
    <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/general.css?v=<%=info.getJsVersion()%>"/>
     <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/style.css?v=<%=info.getJsVersion()%>"/>
     <script type="text/javascript" src="<%=info.getReqUrl()%>/PopScripts/popup.js?v=<%=info.getJsVersion()%> " > </script>
          <script type="text/javascript" src="<%=info.getReqUrl()%>/PopScripts/jquery-1.2.6.min.js?v=<%=info.getJsVersion()%> " > </script> 
      <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/bse.js?v=<%=info.getJsVersion()%> " > </script>

 <script type="text/javascript">
 function loadPerentUrl(){
    parent.location.reload();
 }
 </script>
</head>
  <%
  
        String logId=String.valueOf(info.getLogId());
        String onfocus="cook('"+logId+"')";
        ArrayList cList =(ArrayList)request.getAttribute("cList");
         HashMap dbinfo = info.getDmbsInfoLst();
          String cnt = util.nvl((String)dbinfo.get("CNT"));
          HashMap pageDtlMap=new HashMap();
String fld_nme="",fld_ttl="",val_cond="",dflt_val="",fld_typ="",form_nme="",lov_qry="";
HashMap allPageDtl= (info.getPageDetails() == null)?new HashMap():(HashMap)info.getPageDetails();
HashMap pageDtl=(HashMap)allPageDtl.get("PENDING_REG");
ArrayList pageList=new ArrayList();
            String kaAddColumn="";
            pageList=(ArrayList)pageDtl.get("KAADDCOLUMN");
            if(pageList!=null && pageList.size() >0){
                for(int j=0;j<pageList.size();j++){
                pageDtlMap=(HashMap)pageList.get(j);
                dflt_val=(String)pageDtlMap.get("dflt_val");
                kaAddColumn="Y"; 
                }
            }
        %>
 <body onfocus="<%=onfocus%>" onload="MM_preloadImages('images/faunalogo.png')" topmargin="0" leftmargin="0" rightmargin="0" bottommargin="0">
  <input type="hidden" id="reqUrl" name="reqUrl" value="<%=info.getReqUrl()%>"/>
   <div id="backgroundPopup"></div>
<div id="popupContactASSFNL">
<table class="popUpTb">
 <tr><td height="5%">&nbsp;&nbsp;&nbsp;<html:button property="value(close)" styleClass="submit"  onclick="MKEmptyPopup('SC');disablePopupASSFNL();loadPerentUrl();" value="Close"  /> </td></tr>
<tr><td valign="top" height="95%">
<iframe name="SC" class="frameStyle" align="middle" frameborder="0" >

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
  <tr>
  <td valign="top" class="hedPg">
  <table cellpadding="1" cellspacing="5"><tr><td valign="middle">
  <img src="../images/line.jpg" border="0" /></td><td valign="middle"> <span class="pgHed">Accept Registration</span> </td>
</tr></table>
  </td>
  </tr>
  <% if( cnt.equals("hk")){%>
  <tr>
  <td >
  <html:form action="website/AcceptRegistration.do?method=load" method="post">
  <table><tr><td valign="middle" class="tdLayout">&nbsp;&nbsp;&nbsp; Country :</td>
  <td><html:select property="value(country)" styleId="country" name="pendingRegForm"  >
             <html:option value="" >-----ALL------</html:option>
      
                      <%
                        for(int i=0;i< cList.size();i++){
                        HashMap cDtl=(HashMap)cList.get(i);
                        String cidn = util.nvl((String)cDtl.get("cidn"));
                        String cnme = util.nvl((String)cDtl.get("cnme"));
                         %>  
              <html:option value="<%=cnme%>" ><%=cnme%></html:option>
           <%}%>
          </html:select> </td>
        <td  align="center">&nbsp;&nbsp; <html:submit property="value(submit)" styleClass="submit" value="Fetch" /> </td> </tr>
  </table>
  </html:form></td></tr>
  <%}%>
   <tr>
   <%
   String displayrejectAll="";
   pageList= ((ArrayList)pageDtl.get("REJECTALL") == null)?new ArrayList():(ArrayList)pageDtl.get("REJECTALL");
       if(pageList!=null && pageList.size() >0){%>
    <%   for(int j=0;j<pageList.size();j++){
            pageDtlMap=(HashMap)pageList.get(j);
            displayrejectAll=(String)pageDtlMap.get("dflt_val");    
    }%>
    <%}%>
   <td valign="top" class="tdLayout" style="<%=displayrejectAll%>">
   <A href="AcceptRegistration.do?method=rejectALL">Reject All</a>
   </td>
   </tr>
   
  <tr>
   <td valign="top" class="tdLayout">
   <table><tr><td>
   <display:table name="sessionScope.pendingRegForm.userRegnInfo" export="true"
                         pagesize="15" class="displayTable" requestURI=""
                         decorator="ft.com.Decortor">
            <display:column property="reject" title="Reject"  media="html"/>
     <% pageList= ((ArrayList)pageDtl.get("ACCEPT_WITH_DTL") == null)?new ArrayList():(ArrayList)pageDtl.get("ACCEPT_WITH_DTL");
       if(pageList!=null && pageList.size() >0){ %>
            <display:column property="addDetail" title="Accept"  media="html" />
          <%}else{%>
            <display:column property="editDetail" title="Accept"  media="html" />
           <%}%>
            <display:column property="linkExistingUser"
                            title="Link Existing User" media="html" />
            <display:column property="userRegistrationInfo.userId"
                            title="User Name"/>
            <display:column property="userRegistrationInfo.name" title="Name"
                            group="1"/>
            <display:column property="userRegistrationInfo.companyName"
                            title="Company Name"/>
            <display:column property="userRegistrationInfo.address"
                            title="Address"/>
            <display:column property="userRegistrationInfo.city"
            title="City"/>
            <display:column property="userRegistrationInfo.country"
            title="Country"/>
            <%if(kaAddColumn.equals("Y")){%>
            <display:column property="userRegistrationInfo.state" 
                            title="State"/>
             <display:column property="userRegistrationInfo.zip"
                            title="Zip Code"/>
                            <%}%>
            <display:column property="userRegistrationInfo.telephoneNo1"
                            title="Telephone"/>
            <display:column property="userRegistrationInfo.mobile"
                            title="Mobile"/>
            <display:column property="userRegistrationInfo.email"
                            title="Email"/>
            <display:column property="userRegistrationInfo.biz" title="Biz"/>

            <display:column property="userRegistrationInfo.membership"
                            title="Member Id"/>
                            <%if(kaAddColumn.equals("Y")){%>
             <display:column property="userRegistrationInfo.council_mem"
                            title="Council Member"/>
                            <%}%>
            <display:column property="userRegistrationInfo.reg_dte"
                            title="Registration Date"/>
<%if(kaAddColumn.equals("Y")){%>
            <display:column property="userRegistrationInfo.birthdate"
                            title="Birth Date"/>
                            <%}%>
            

            <display:column property="userRegistrationInfo.fax"
                            title="Fax"/>
                            <%if(kaAddColumn.equals("Y")){%>
                                        <display:column property="userRegistrationInfo.verify"
                            title="Verify"/>
                            <%}%>
  <display:setProperty name="export.excel.filename" value="Details.xls"/>
  <display:setProperty name="export.xls" value="true" />
  <display:setProperty name="export.csv" value="false" />
  <display:setProperty name="export.xml" value="false" />
  </display:table>
   </td></tr>
  </table></td></tr>
  </table>
  </body>
</html>