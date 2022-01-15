<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page errorPage="../error_page.jsp" %>
<%@ page contentType="text/html;charset=windows-1252"%>
<jsp:useBean id="info" class="ft.com.InfoMgr" scope="session" />
<jsp:useBean id="gtMgr" class="ft.com.GtMgr" scope="session" />

<jsp:useBean id="util" class="ft.com.JspUtil" scope="page" />
<%@ page import="java.util.ArrayList,java.util.LinkedHashMap, java.util.Set,java.util.HashMap,java.sql.ResultSet,ft.com.*, java.util.Date, java.util.ArrayList, ft.com.dao.*"%>
<%@taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>Mix Assort Issue</title>
    <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/bse.css?v=<%=info.getJsVersion()%>"/>
    <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/general.css?v=<%=info.getJsVersion()%>"/>
     <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/style.css?v=<%=info.getJsVersion()%>"/>
      <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/bse.js?v=<%=info.getJsVersion()%> " > </script>
            <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/ajax.js?v=<%=info.getJsVersion()%> " > </script>
       <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/assortScript.js?v=<%=info.getJsVersion()%> " > </script>
              <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/newCalScript.js?v=<%=info.getJsVersion()%> " > </script>

  </head>
        <%String logId=String.valueOf(info.getLogId());
        String onfocus="cook('"+logId+"')";
          HashMap allPageDtl= (info.getPageDetails() == null)?new HashMap():(HashMap)info.getPageDetails();
    HashMap pageDtl=(HashMap)allPageDtl.get("MIX_TRANS");
    ArrayList pageList=new ArrayList();
    HashMap pageDtlMap=new HashMap();
    String fld_nme="",fld_ttl="",val_cond="",dflt_val="",fld_typ="",form_nme="",lov_qry="";
   
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
  <img src="../images/line.jpg" border="0" /></td><td valign="middle"> <span class="pgHed">Mix Transaction</span> </td>
</tr></table>
  </td>
  </tr>
   <tr>
   <td valign="top" class="tdLayout">
     <html:form action="/mixre/mixTransaction.do?method=loadBOXTYP" method="POST">

     <table  class="grid1">
  <tr><th> </th><th>Generic Search 
  <%
  ArrayList rolenmLst=(ArrayList)info.getRoleLst();
  String usrFlg=util.nvl((String)info.getUsrFlg());
  if(usrFlg.equals("SYS") || rolenmLst.contains("ADMIN")){
  %>
  <img src="../images/edit.jpg" title="Edit Properties Module" onclick="newWindow('<%=info.getReqUrl()%>/dynamicProperty.do?method=load&mdl=MIXAST_SRCH&sname=MIXAST_SRCH&par=GENERIC')" border="0" width="15px" height="15px"/> 
  <%}%>
  </th></tr>
  <tr><td valign="top">
   <table>
   <tr>
   <td>Trans Typ</td><td>
   <%  pageList= ((ArrayList)pageDtl.get("TRANSTYP") == null)?new ArrayList():(ArrayList)pageDtl.get("TRANSTYP");
     if(pageList!=null && pageList.size() >0){%>
     <html:select property="value(TRANSTYP)"  name="mixTransactionForm">
     <html:option value="">----select----</html:option>    
    <% for(int j=0;j<pageList.size();j++){
             pageDtlMap=(HashMap)pageList.get(j);
             fld_nme=(String)pageDtlMap.get("fld_nme");
             fld_ttl=(String)pageDtlMap.get("fld_ttl");
             %>
          <html:option value="<%=fld_nme%>"><%=fld_ttl%></html:option>    
    <% }%>
     </html:select>
     <%}%>
   
   </td></tr>
   
   <tr><td>Packet No:</td><td> <html:textarea property="value(vnmLst)" rows="7"  cols="30" name="mixTransactionForm"  /></td>
   </tr>
  </table></td>
   <td>
   <table>
   
     <tr><td><table><tr>
  <td>Date From : </td><td>
    <html:text property="value(dtefr)" styleClass="txtStyle"  styleId="dtefr"  name="mixTransactionForm" size="12"  /> <a href="#" style="margin-top:5px;"  onClick="setYears(1900, '<%=info.getCurrentYear()%>' );showCalender(this, 'dtefr');">
                <img src="<%=info.getReqUrl()%>/images/calender.png"></a></td>
                <td>Date To : </td><td>
    <html:text property="value(dteto)" styleClass="txtStyle"  styleId="dteto"   name="mixTransactionForm" size="12"  /> <a href="#"  onClick="setYears(1900, '<%=info.getCurrentYear()%>' );showCalender(this, 'dteto');">
                <img src="<%=info.getReqUrl()%>/images/calender.png"></a></td></tr></table></td>
  
  </tr>
   <tr><td>
    <jsp:include page="/genericSrch.jsp"/></td></tr>
   </table>
  </td></tr>
   <tr><td colspan="2" align="center">
   <html:submit property="value(srch)" value="Fetch" styleClass="submit" /></td> </tr>
   </table></html:form>
   </td></tr>
   <tr><td valign="top" class="tdLayout" height="20px">
   
   </td></tr>
   <tr><td valign="top" class="tdLayout">
   <% String view =(String)request.getAttribute("view");
   if(view!=null){
   ArrayList pktDtlLst = (ArrayList)request.getAttribute("PKTDTL");
   if(pktDtlLst!=null &&  pktDtlLst.size()>0){%>
   <table class="grid1">
   <tr><th>Type</th><th>Date</th><th>FROM BOX</th><th>TO BOX</th><th>Qty</th><th>Cts</th><th>Rate</th><th>Remark</th></tr>
   <%for(int i=0;i<pktDtlLst.size();i++){
   HashMap pktDtl = (HashMap)pktDtlLst.get(i);
   %>
   <tr>
    <td><%=util.nvl((String)pktDtl.get("TYP"))%></td>
    <td><%=util.nvl((String)pktDtl.get("DTE"))%></td> <td><%=util.nvl((String)pktDtl.get("FRMBOX"))%></td>
   <td><%=util.nvl((String)pktDtl.get("TOBOX"))%></td> <td><%=util.nvl((String)pktDtl.get("QTY"))%></td>
   <td><%=util.nvl((String)pktDtl.get("CTS"))%></td> <td><%=util.nvl((String)pktDtl.get("UPR"))%></td><td><%=util.nvl((String)pktDtl.get("RMK"))%></td>
   </tr>
   <%}%>
   </table>
   <%}else{%>
   Sorry no result found..
   <%}}%>
   </td></tr>
 
  
   <tr>
    <td><jsp:include page="/footer.jsp" /> </td>
    </tr>
  </table>

  </body>
</html>