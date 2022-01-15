
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
 
<%@ page errorPage="../error_page.jsp" %>
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import="java.util.ArrayList,java.util.HashMap,java.sql.ResultSet,ft.com.*, java.util.Date, java.util.ArrayList, ft.com.dao.*"%>
<jsp:useBean id="util" class="ft.com.JspUtil" scope="page" />
<jsp:useBean id="info" class="ft.com.InfoMgr" scope="session" />
<%@taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>
<%@taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>

<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>Recalculate Charges</title>
    <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/bse.css?v=<%=info.getJsVersion()%>"/>
     <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/general.css?v=<%=info.getJsVersion()%>"/>
      <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/style.css?v=<%=info.getJsVersion()%>"/>
      <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/ajax.js?v=<%=info.getJsVersion()%> " > </script>
      <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/bse.js?v=<%=info.getJsVersion()%> " > </script>
      <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/memoScript.js?v=<%=info.getJsVersion()%> " > </script>
          <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/priceChange.js?v=<%=info.getJsVersion()%> " > </script>
    <script type="text/javascript" src="<%=info.getReqUrl()%>/PopScripts/popup.js?v=<%=info.getJsVersion()%> " > </script>
<script src="<%=info.getReqUrl()%>/PopScripts/jquery-1.2.6.min.js" type="text/javascript"></script>
    <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/assortScript.js?v=<%=info.getJsVersion()%> " > </script>
    <script language="javaScript" type="text/javascript" src="<%=info.getReqUrl()%>/calendar/calendar.js"></script>
   <link href="<%=info.getReqUrl()%>/calendar/calendar.css" rel="stylesheet" type="text/css" />
  
  </head>
<%
HashMap dbinfo = info.getDmbsInfoLst();
String cnt = (String)dbinfo.get("CNT");
  String repPath = (String)dbinfo.get("REP_PATH");
    HashMap allPageDtl= (info.getPageDetails() == null)?new HashMap():(HashMap)info.getPageDetails();
    HashMap pageDtl=(HashMap)allPageDtl.get("RE_CAL_CHARGES");
ArrayList pageList=new ArrayList();
HashMap pageDtlMap=new HashMap();
String fld_nme="",fld_ttl="",val_cond="",dflt_val="",fld_typ="",form_nme="";
String performaLink = util.nvl((String)request.getAttribute("performaLink"));
        String logId=String.valueOf(info.getLogId());
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
  <img src="../images/line.jpg" border="0" /></td><td valign="middle"> <span class="pgHed">Recalculate Charges</span> </td>
</tr></table>
  </td>
  </tr>
    <%
   String msg = (String)request.getAttribute("msg");
   if(msg!=null){%>
   <tr><td valign="top" class="tdLayout"><span class="redLabel"><%=msg%></span></td></tr>
   <%}%>
  <tr><td valign="top" class="tdLayout">
  <html:form action="/marketing/recalcharges.do?method=fetchExistsCharges"  method="POST">     
        <table class="grid1" >
        <tr>
        <th colspan="2">Charges Search</th>
        </tr>
        <tr>
        <td>Reference Type</td>
        <td>
        <html:select property="value(ref_typ)"  name="reCalChargeForm" styleId="ref_typ">
        <%pageList= ((ArrayList)pageDtl.get("TYPE") == null)?new ArrayList():(ArrayList)pageDtl.get("TYPE");
       if(pageList!=null && pageList.size() >0){%>
        <%   for(int j=0;j<pageList.size();j++){
                pageDtlMap=(HashMap)pageList.get(j);
                dflt_val=(String)pageDtlMap.get("dflt_val");
               fld_ttl=(String)pageDtlMap.get("fld_ttl");%>
           <option value="<%=dflt_val%>"><%=fld_ttl%></option>     
        <% }%>
        </select>
        <%}%>
        </html:select>
        </td>
        </tr>
        <tr>
        <td>Reference Idn</td>
        <td><html:text property="value(ref_idn)" styleId="ref_idn" size="20" name="reCalChargeForm"  /></td>
        </tr>
        <tr>
        <td align="center" colspan="2">
        <html:submit property="value(fetch)" value="Fetch" styleClass="submit"/>&nbsp;
        </td></tr>
        </table>
        </html:form>
    </td>
    </tr>
    <%String view =util.nvl((String)request.getAttribute("view"));
    if(view.equals("Y")){
    ArrayList chargesLst= ((ArrayList)session.getAttribute("ReCalchargesLst") == null)?new ArrayList():(ArrayList)session.getAttribute("ReCalchargesLst");
    if (chargesLst != null || chargesLst.size()>0) {
    %>
    <tr>
    <td valign="top" class="hedPg">
    <html:form action="/marketing/recalcharges.do?method=saveCharges"  method="POST">   
    <html:hidden property="value(idn)" name="reCalChargeForm" />
    <html:hidden property="value(typ)" name="reCalChargeForm"  />
    <table class="grid1">
    <tr><td colspan="6" align="center">Charge Details</td></tr>
    <tr>
    <th>Select</th>
    <th>Charges</th>
    <th>Auto Optional</th>
    <th>Current</th>
    <th>New</th>
    <th>Remark</th>
    </tr>
    <%for(int i=0;i<chargesLst.size();i++){
    HashMap dtl=new HashMap();
    dtl=(HashMap)chargesLst.get(i);
    String dsc=(String)dtl.get("dsc");
    String autoopt=(String)dtl.get("autoopt");
    String flg=(String)dtl.get("flg");
    String typ=(String)dtl.get("typ");
    String fctr=(String)dtl.get("fctr");
    String fun=(String)dtl.get("fun");
    String rmk=(String)dtl.get("rmk");
    String field = "value("+typ+"_charges)";
    String fieldRmk = "value("+typ+"_rmksave)";
    String fieldrmkId = typ+"_rmksave";
    String existingfield = "value("+typ+"_existscharges)";
    String chkFld="value("+typ+")";
    String chkFldID="CHK_"+typ;
    %>
    <tr>
    <td align="center">
    <html:checkbox property="<%=chkFld%>" styleId="<%=chkFldID%>" name="reCalChargeForm" value="yes" />
    </td>
    <td nowrap="nowrap"><b><%=dsc%></b></td>
    <%
    if(flg.equals("MNL")){
    %>
    <td nowrap="nowrap"></td>
    <td><bean:write property="<%=existingfield%>" name="reCalChargeForm" /></td>
    <td nowrap="nowrap">
    <html:text property="<%=field%>" size="6" styleId="<%=typ%>" name="reCalChargeForm"/>
    </td>
    <td nowrap="nowrap">
    <%if(rmk.equals("Y")){%>
    <html:text property="<%=fieldRmk%>" styleId="<%=fieldrmkId%>" name="reCalChargeForm" size="10"/>
    <%}%>
    </td>
    <%}else{%>
    <td align="center">
    <%if(flg.equals("AUTO") && autoopt.equals("OPT")){
    String checkFldId = typ+"_save";
    String checkFldVal = "value("+typ+"_AUTOOPT)";
    %>
    <html:checkbox property="<%=checkFldVal%>" styleId="<%=checkFldId%>" name="reCalChargeForm" value="N" />
    <%}%>
    </td>
    <td><bean:write property="<%=existingfield%>" name="reCalChargeForm" /></td>
    <td nowrap="nowrap">
    <html:text property="<%=field%>" size="6" styleId="<%=typ%>" name="reCalChargeForm"/>
    </td>
    <td>
    </td>
    
    <%}%>
    </tr>
    <%}%>
    <tr><td colspan="6" align="center"><html:submit property="value(save)" value="Save Changes" onclick="return validate_SelectAll('1','CHK_')" styleClass="submit"/></td></tr>
    </table>
    </html:form>
    </td>
    </tr>
    <%}}%>
    </table>
    
    <%@include file="../calendar.jsp"%>
  <%if(!performaLink.equals("")){%>
 <script type="text/javascript">
 <!--
 var url = document.getElementById('rptUrl').value;
 windowOpen(url,'_blank')
 -->
 </script>
 <%}%>
  </body>
</html>
