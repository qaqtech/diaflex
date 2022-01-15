<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page errorPage="../error_page.jsp" %>
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import="java.util.ArrayList,java.util.HashMap,java.sql.ResultSet,ft.com.*,java.util.*, java.util.Date,java.util.Set, java.util.ArrayList, ft.com.dao.*"%>
<jsp:useBean id="util" class="ft.com.JspUtil" scope="page" />
<jsp:useBean id="info" class="ft.com.InfoMgr" scope="session" />
<%@taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>
<%@taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>File Upload</title>
  <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/bse.css?v=<%=info.getJsVersion()%>"/>
    <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/style.css?v=<%=info.getJsVersion()%>"/>
    <script type="text/javascript" src="<%=info.getReqUrl()%>/PopScripts/popup.js?v=<%=info.getJsVersion()%> " > </script>
     <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/bse.js?v=<%=info.getJsVersion()%> " > </script>
     <script language="javaScript" type="text/javascript" src="<%=info.getReqUrl()%>/calendar/calendar.js"></script>
       <script type="text/javascript" src="<%=info.getReqUrl()%>/jqueryScript/jqueryScript.js?v=<%=info.getJsVersion()%> " > </script>

   <link href="<%=info.getReqUrl()%>/calendar/calendar.css" rel="stylesheet" type="text/css" />
    <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/general.css?v=<%=info.getJsVersion()%>"/>
    <script src="<%=info.getReqUrl()%>/PopScripts/jquery-1.2.6.min.js" type="text/javascript"></script>
  </head>
        <%
        String logId=String.valueOf(info.getLogId());
        String onfocus="cook('"+logId+"')";
        HashMap dbinfo = info.getDmbsInfoLst();
         ArrayList fileUploadList=(ArrayList)session.getAttribute("fileUploadList");
        %>
 <body onfocus="<%=onfocus%>" onload="MM_preloadImages('images/faunalogo.png')" topmargin="0" leftmargin="0" rightmargin="0" bottommargin="0">
 <img src="../images/loadbig.gif" vspace="15" id="load" style="display:none;" class="loadpktDiv" border="0" />
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
    <td height="5" valign="top" class="greyline"><img src="<%=info.getReqUrl()%>/images/grey_linebig.png" width="685" height="5" align="right" /></td>
  </tr>
  <tr><td>
  <%
  String jsmsg = (String)request.getAttribute("msg");
  if(jsmsg!=null){%>
  <span class="redLabel">
  <%=jsmsg%></span>
   <%}%>
  </td></tr>
  <%
  String msg = (String)request.getAttribute("fileMsg");
  if(msg!=null){
  int accessidn=Integer.parseInt(util.nvl((String)request.getAttribute("accessidn"),"0"));
  String url = (String)request.getAttribute("url");
  url=url+"&p_access="+accessidn;
  %>
   <tr>
  <td valign="top" class="hedPg">
  <div><span class="redLabel"> No of Line of File upload = <%=request.getAttribute("lineNo")%></span></div>
  <div>
  <span class="redLabel">
  <%=msg%></span>
  </div>
  </td></tr>
  <tr>
  <td valign="top" class="hedPg">
   <a href="<%=url%>"  target="_blank"><span class="txtLink" >Click Here for Report</span></a> 
  </td></tr>
  
  <%}
    %>
  <tr>
  <td valign="top" class="hedPg">
  <table cellpadding="1" cellspacing="5"><tr><td valign="middle">
  <img src="../images/line.jpg" border="0" /></td><td valign="middle"> <span class="pgHed">
 File Upload
 </span> </td>
</tr></table>
  </td>
  </tr>
  <tr><td class="tdLayout" valign="top">
  <table>
  <html:form action="fileloaders/loadFile.do?method=upload" method="post" enctype="multipart/form-data" onsubmit="loading();">
<html:hidden property="value(filetyp)" />

  <tr>
  <td>File Upload Type: </td>
  <td><html:select property="value(typ)" name="fileUploadForm" onchange="showHidDivFile('dteDiv',this)" styleId="filety" >
  <html:optionsCollection property="uploadList" name="fileUploadForm" value="fileTyp" label="fileDsc" />
  
  </html:select>
  </td>
  <td>
   <%
  for(int i=0;i<fileUploadList.size();i++){
  FileUploadDao dao=(FileUploadDao)fileUploadList.get(i);
  String fileNme = dao.getFileTyp();
  String dteFmt = util.nvl(dao.getDate_fmt());
  if(dteFmt.equals(""))
  dteFmt="DD-MON-YYYY";
  String isDisplay="display:none";
  if(i==0)
  isDisplay="";
  %> 
  <input type="hidden" name="<%=dao.getFileTyp()%>" id="<%=dao.getFileTyp()%>" value="<%=dao.getFileExt()%>">
  <div id="DTEDIV_<%=fileNme%>" class="dteDiv" style="<%=isDisplay%>">
    <input type="text" class="textNew" name="DTE_<%=dao.getFileTyp()%>" id="DTE_<%=dao.getFileTyp()%>" value="<%=dao.getDate_fmt()%>"/>
  </div>
  <%}%></td>
  <td>
  <html:file property="fileUpload" name="fileUploadForm"  styleId="fileUpload"  onchange="check_file();"/>
  
  
  </td>
 </tr>

  <tr>
  <td colspan="2"> <html:submit property="upload" value="File Upload" styleClass="submit"/> </td>
  </tr>
  </html:form>
  </table>
  </td>
  </tr>
  <%ArrayList hedMappingLst = (ArrayList)request.getAttribute("MAPPINGNFLIST"); 
  if(hedMappingLst!=null && hedMappingLst.size()>0){
  
  HashMap allmprp = util.ALLMRPR(dbinfo);
  if(allmprp!=null){
  HashMap mprp = new HashMap();
  ArrayList allmprpList = new ArrayList();
  Set<String> keys = allmprp.keySet();
  for(String key: keys){
       boolean isNum = util.isNumeric(key);
       if(isNum){
       String val = (String)allmprp.get(key);
       mprp.put(val, key);
       allmprpList.add(val);
     }
    }
  Collections.sort(allmprpList);

  %><tr><td class="tdLayout" valign="top">
   <html:form action="fileloaders/loadFile.do?method=MappHeader" method="post"  onsubmit="loading();">
  <table cellpadding="2" cellspacing="2">
    <tr><td>
   <label class="redLabel"> <%=request.getAttribute("MSG")%></label>
    </td></tr>
    <tr><td>
     <html:submit property="save" value="Save Changes" styleClass="submit"/> 
    </td></tr>
    <tr><td>
    <table class="grid1">
    <tr><th>Mapp</th><th>Ignore</th> <th>File Value</th><th>Our Value</th> </tr>
    <%for(int j=0;j<hedMappingLst.size();j++){
    String hdrVal = (String)hedMappingLst.get(j);
   
   %>
    <tr>
    <td><input type="radio" name="RD_<%=hdrVal%>" value="MAP" /></td>
    <td><input type="radio" name="RD_<%=hdrVal%>" checked="checked" value="IGN" /></td>
    <td><input type="text" name="FILVAL_<%=hdrVal%>"  id="FILVAL_<%=hdrVal%>" value="<%=hdrVal%>" readonly="" /> </td>
    <td>
    <select name="FILOURVAL_<%=hdrVal%>" id="FILOURVAL_<%=hdrVal%>">
    <%for(int i=0;i<allmprpList.size();i++){
    String lprp = (String)allmprpList.get(i);
    String key =(String)mprp.get(lprp);
    %>
    <option value="<%=key%>" ><%=lprp%></option>
    <%}%>
    </select>
    
    </td>
   
    </tr>
    <%}%>
    </table></td>
    
    </tr>
    
    </table></html:form>
    </td></tr>
    <%}else{%>
  <tr><td>  Some issue with Coordinate with Fauna team</td></tr>
    <%}}%>
    
    
    <%
      
    HashMap PRPNOTFD = (HashMap)request.getAttribute("PRPNOTFN"); 
    String DataFormat="Data Formatting Problem :-";
  if(PRPNOTFD!=null && PRPNOTFD.size()>0){
  HashMap headerDtlMap = (HashMap)request.getAttribute("HEADDTL");
        ArrayList headerList = (ArrayList)headerDtlMap.get("VALLIST");
        ArrayList headerIdnList = (ArrayList)headerDtlMap.get("IDNLIST");
        ArrayList headerTypList = (ArrayList)headerDtlMap.get("TYPLIST");
HashMap allprp = util.ALLPRP(dbinfo);
  %><tr><td class="tdLayout" valign="top">
   <html:form action="fileloaders/loadFile.do?method=PrpHeader" method="post"  onsubmit="loading();">
  <table cellpadding="2" cellspacing="2">
    <tr><td>
    <%for(int i=0;i<headerTypList.size();i++){
    String hdTyp = util.nvl((String)headerTypList.get(i));
    if(!hdTyp.equals("C")){
     String hdIDn = util.nvl((String)headerIdnList.get(i));
     String hdVal = util.nvl((String)headerList.get(i));
     ArrayList prpList = (ArrayList)PRPNOTFD.get(hdIDn);
     if(prpList!=null && prpList.size()>0){
    %>
     <label class="redLabel"><%=DataFormat%> </br> <b><%=hdVal%></b> <%=prpList.toString()%></label>
    <%DataFormat="";
    }}}%>
  
    </td></tr>
    <tr><td>
     <html:submit property="save" value="Save Changes" styleClass="submit"/> 
    </td></tr>
    <tr><td>
    <table class="grid1">
    <tr><th>Mapp</th><th>Ignore</th><th>Header Prp</th> <th>File Value</th><th>Our Value</th> </tr>
    <%
    for(int i=0;i<headerIdnList.size();i++){
    String hedIdn = (String)headerIdnList.get(i);
    String hedVal = (String)headerList.get(i);
    ArrayList prpMapList = (ArrayList)PRPNOTFD.get(hedIdn);
    if(prpMapList!=null && prpMapList.size()>0){
    ArrayList prpValIdnList = (ArrayList)allprp.get(hedIdn+"I");
     ArrayList prpValList = (ArrayList)allprp.get(hedIdn+"V1");
    %>
   
    <%for(int j=0;j<prpMapList.size();j++){
    String prpVal = (String)prpMapList.get(j);
    %>
    <tr>
    <td>
    <input type="radio" name="RD_<%=hedIdn%>_<%=prpVal%>" value="MAP" /></td>
    <td><input type="radio" name="RD_<%=hedIdn%>_<%=prpVal%>" checked="checked" value="IGN" /></td>
    <td> <input type="hidden" name="HEDIDN_<%=hedIdn%>_<%=prpVal%>"  id="HEDIDN_<%=hedIdn%>_<%=prpVal%>" value="<%=hedIdn%>"  />
    <input type="text" name="HEDVAL_<%=hedIdn%>_<%=prpVal%>"  id="HEDVAL_<%=hedIdn%>_<%=prpVal%>" value="<%=hedVal%>" readonly="" />
    </td>
    <td>  <input type="text" name="PRPVAL_<%=hedIdn%>_<%=prpVal%>"  id="PRPVAL_<%=hedIdn%>_<%=prpVal%>" value="<%=prpVal%>" readonly="" /> </td>
    <td>
    <select name="FILOURVAL_<%=hedIdn%>_<%=prpVal%>" id="FILOURVAL_<%=hedIdn%>_<%=prpVal%>">
    <%for(int k=0;k<prpValIdnList.size();k++){
    String lprp = (String)prpValList.get(k);
    String key =(String)prpValIdnList.get(k);
    %>
    <option value="<%=key%>" ><%=lprp%></option>
    <%}%>
    </select>
    </td>
    </tr>
    <%}}}%>
    
    
    </table>
   
     </td>
    
    </tr>
    
    </table></html:form>
    </td></tr>
    <%}%>
  <tr>
    <td><jsp:include page="/footer.jsp" /> </td>
    </tr>
  </table>
   <%@include file="../calendar.jsp"%>
  </body></html>