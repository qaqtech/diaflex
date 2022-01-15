<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import="java.util.ArrayList,java.util.HashMap,java.util.ArrayList,java.util.Iterator,
java.util.Set,java.sql.ResultSet,ft.com.*, java.util.Date"%>
<jsp:useBean id="util" class="ft.com.JspUtil" scope="page" />
<jsp:useBean id="info" class="ft.com.InfoMgr" scope="session" />

<%@taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/style.css?v=<%=info.getJsVersion()%>"/>
         <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/bse.css?v=<%=info.getJsVersion()%>"/>
        <script type="text/javascript"  src="<%=info.getReqUrl()%>/scripts/bse_prc.js"></script>
     <script type="text/javascript"  src="<%=info.getReqUrl()%>/scripts/bse.js?v=<%=info.getJsVersion()%>"></script>
     
    <title>srchGrpMat</title>
  </head>
        <%String logId=String.valueOf(info.getLogId());
        String onfocus="cook('"+logId+"')";
        %>
 <body onfocus="<%=onfocus%>">
  <%
  HashMap allPageDtl= (info.getPageDetails() == null)?new HashMap():(HashMap)info.getPageDetails();
  HashMap pageDtl=(HashMap)allPageDtl.get("PRICE_MATRIX");
    ArrayList pageList=new ArrayList();
    HashMap pageDtlMap=new HashMap();
    String fld_nme="",fld_ttl="",val_cond="",dflt_val="",fld_typ="",form_nme="",flg1="",lov_qry="";

 // util.updAccessLog(request,response,"Search Group", "Search Group");
  ArrayList grpList = (ArrayList)request.getAttribute("grpDtlList");
  String grpNme = request.getParameter("grpNme");
  if(grpNme==null)
  grpNme = (String)request.getAttribute("grpNme");
  
  String srchId = request.getParameter("srchID");
  if(srchId==null)
  srchId = (String)request.getAttribute("srchID");
  %>
  <table>
  <tr>
  <td valign="top" class="tdLayout">
  <span class="pgHed"><%=grpNme%> </span>
  </td></tr>
   <%
   ArrayList msgLst = (ArrayList)request.getAttribute("msgList");
   if(msgLst!=null){
   for(int i=0;i<msgLst.size();i++){
   String msg = (String)msgLst.get(i);
   %>
    <tr><td valign="top" class="tdLayout"><span class="redLabel"><%=msg%></span></td></tr>
   <%}}%>
  <tr><td valign="top" class="tdLayout">
  <html:form action="pri/pricegpmatrix.do?method=srch"  method="post">
  <table  class="grid1">
  <tr><th>Search </th></tr>
  <tr>
   <td>
    <jsp:include page="/genericSrch.jsp"/>
   </td>
  </tr>
  <tr><td align="center">
  <table><tr><td>
  <html:submit property="value(srch)" value="Search" styleClass="submit" /></td>
  <%

pageList= ((ArrayList)pageDtl.get("FETCHBUTTON") == null)?new ArrayList():(ArrayList)pageDtl.get("FETCHBUTTON");
            if(pageList!=null && pageList.size() >0){
            for(int j=0;j<pageList.size();j++){
            pageDtlMap=(HashMap)pageList.get(j);
            lov_qry=(String)pageDtlMap.get("lov_qry");
            if(lov_qry.equals(""))
            fld_nme="value("+(String)pageDtlMap.get("fld_nme")+")";
            else
            fld_nme=(String)pageDtlMap.get("fld_nme");
            fld_ttl=(String)pageDtlMap.get("fld_ttl");
            fld_typ=(String)pageDtlMap.get("fld_typ");
            dflt_val=(String)pageDtlMap.get("dflt_val");
            val_cond=(String)pageDtlMap.get("val_cond"); 
           
            if(fld_typ.equals("S") && dflt_val.indexOf(grpNme)!=-1){%>
           <td> <html:submit property="<%=fld_nme%>" value="<%=fld_ttl%>" onclick="<%=val_cond%>" styleClass="submit"/>&nbsp;</td>
            <%}else if(fld_typ.equals("B")){%>
           <td> <html:button property="<%=fld_nme%>" value="<%=fld_ttl%>" onclick="<%=val_cond%>" styleClass="submit"/>&nbsp;</td>
            <%}}}%>
  </tr></table>
  </td> </tr>
 </table>
  </html:form>
</td></tr>
<tr><td>
  <table><tr><td>
  <%
  if(grpList!=null && grpList.size()>0){
  boolean isfileUpload=true;
  %>
  <html:form action="pri/pricegpmatrix.do?method=delete"  method="post" enctype="multipart/form-data" >
  <html:hidden property="value(grpNme)" styleId="grpNme" value="<%=grpNme%>" />
  <html:hidden property="value(SRCHID)" styleId="srchId" value="<%=srchId%>" />
  <table>
  <tr>
  <td valign="top" class="tdLayout">
  <table cellpadding="1" cellspacing="1"><tr><td><a href="pricegpmatrix.do?method=edit&grpNme=<%=grpNme%>"><img src="../images/refresh.gif" border="0"/></a>
</td><td><html:submit property="value(delete)" onclick="return sumbitConfirm('Are you sure to delete: ?')" styleClass="submit" value="Delete" /> </td>
<!--<td><html:submit property="value(upload)" onclick="return sumbitConfirm('Are you sure to upload file ?')" styleClass="submit" value="Upload file" /></td>-->
      

<%

pageList= ((ArrayList)pageDtl.get("BUTTON") == null)?new ArrayList():(ArrayList)pageDtl.get("BUTTON");
            if(pageList!=null && pageList.size() >0){
            for(int j=0;j<pageList.size();j++){
            pageDtlMap=(HashMap)pageList.get(j);
            lov_qry=(String)pageDtlMap.get("lov_qry");
            if(lov_qry.equals(""))
            fld_nme="value("+(String)pageDtlMap.get("fld_nme")+")";
            else
            fld_nme=(String)pageDtlMap.get("fld_nme");
            fld_ttl=(String)pageDtlMap.get("fld_ttl");
            fld_typ=(String)pageDtlMap.get("fld_typ");
            dflt_val=(String)pageDtlMap.get("dflt_val");
            val_cond=(String)pageDtlMap.get("val_cond"); 
            if(fld_ttl.equals("Upload file"))
             isfileUpload = true;
            if(fld_typ.equals("S")){%>
           <td> <html:submit property="<%=fld_nme%>" value="<%=fld_ttl%>" onclick="<%=val_cond%>" styleClass="submit"/>&nbsp;</td>
            <%}else if(fld_typ.equals("B")){%>
           <td> <html:button property="<%=fld_nme%>" value="<%=fld_ttl%>" onclick="<%=val_cond%>" styleClass="submit"/>&nbsp;</td>
            <%}}}%>
            <% pageList= ((ArrayList)pageDtl.get(grpNme) == null)?new ArrayList():(ArrayList)pageDtl.get(grpNme);
        if(pageList!=null && pageList.size() >0){
            %>
<td><html:submit property="value(prisheet)"  styleClass="submit" value="Download For MFG" /></td>
<%}%>
  </tr>
  </table>
  </td></tr>
  <tr>
  <td valign="top" class="tdLayout">
  
  <div class="memo">
  <table border="0" class="Orange" cellspacing="0" cellpadding="0">
  <tr>
  <th class="Orangeth" align="left"><input type="checkbox" name="all" id="all" onclick="CheckBOXALLForm('1',this,'cb_mat_')" /> </th>
  <th class="Orangeth">Name</th> <th class="Orangeth">Properties</th><th class="Orangeth" >From</th>
  <th class="Orangeth" >To</th>
  
 <%if(isfileUpload){%> <th class="Orangeth">file</th><%}%>
  </tr>
  <%
  String pMatName="";
  for(int i=0;i<grpList.size();i++){
  HashMap grpDtl = (HashMap)grpList.get(i);
  String idn = (String)grpDtl.get("idn");
  String matNme =(String)grpDtl.get("nme");
  String dte = (String)grpDtl.get("dte");
  String PRMTYP = (String)grpDtl.get("PRMTYP");
  String disNme = "";
  if(pMatName.equals("")){
   pMatName = matNme;
   disNme = matNme;
   }
   if(!pMatName.equals(matNme)){
   disNme = matNme;
   pMatName = matNme;
   
   }
  String matCheck = "cb_mat_"+idn;
  String fileNme = "fileVal(cb_upl_"+idn+")";
  %>
  <tr>
  <td colspan="2">
  <%if(!disNme.equals("")){%>
  <table><tr><td>
  <input type="hidden" name="PRMTYP_<%=idn%>" id="PRMTYP_<%=idn%>" value="<%=PRMTYP%>" />
  <input type="checkbox" name="<%=matCheck%>" id="<%=matCheck%>" value="<%=idn%>" /></td><td>
  <A href="pricegpmatrix.do?method=load&matIdn=<%=idn%>&srchID=<%=srchId%>" target="NEWWINDOW">
  <%=disNme%></a>
  <input type="hidden" value="<%=disNme%>" name="<%=idn%>_SHTNME"/>
  </td><td><%=dte%></td> 
  </tr></table>
  <%}%>
  </td>
 
  <td><%=grpDtl.get("mprp")%></td><td><%=grpDtl.get("valFr")%></td>
  <td><%=grpDtl.get("valTo")%></td>
   <%if(isfileUpload){%>
  <td>
   <%if(!disNme.equals("")){%>
  <html:file property="<%=fileNme%>"   styleId="fileUpload"   onchange="check_file();"/> <%}%> </td><%}%>
  </tr>
  <%}%></table></div>
 
  </td></tr></table></html:form>
  <%}else{%>
  Sorry no result found.
  <%}%>
  </td></tr></table></td></tr></table>
  </body>
</html>