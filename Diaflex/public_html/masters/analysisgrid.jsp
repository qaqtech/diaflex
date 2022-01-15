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
    <title>Analysis Report Grid Format</title>
    <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/bse.css?v=<%=info.getJsVersion()%>"/>
    <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/general.css?v=<%=info.getJsVersion()%>"/>
     <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/style.css?v=<%=info.getJsVersion()%>"/>
          <script language="javaScript" type="text/javascript" src="<%=info.getReqUrl()%>/scripts/ajax.js?v=<%=info.getJsVersion()%>"></script>
      <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/bse.js?v=<%=info.getJsVersion()%> " > </script>
  </head>
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
  <img src="../images/line.jpg" border="0" /></td><td valign="middle"> <span class="pgHed">Analysis Report Grid Format</span> </td>
</tr></table>
  </td>
  </tr>
   <% if(request.getAttribute("MSG")!=null){%>
   <tr><td valign="top" class="hedPg">
   <span class="redLabel"> <%=request.getAttribute("MSG")%></span></td></tr>
   <% }%>
  <%ArrayList vwPrpLst = (ArrayList)session.getAttribute("gridfmtPrp");
    ArrayList gridfmt = (ArrayList)request.getAttribute("gridfmt");
    ArrayList prpDspBlocked = info.getPageblockList();
    ArrayList gridfmttyp=new ArrayList();
    gridfmttyp.add("COMM");
    gridfmttyp.add("ROW");
    gridfmttyp.add("COL");
    HashMap mprp = info.getMprp();
  %>
   <tr><td valign="top" class="tdLayout">
   <table>
   <html:form action="/masters/analysisgrid.do?method=delete" method="POST" >
   <tr>
   <td>Select Exiting Format</td>
   <td>
  <html:select property="value(gridfmt)" name="analysisGridFmtForm">
  <%for (int i=0;i<gridfmt.size();i++){
   String val=(String)gridfmt.get(i);
  %>
  <html:option value="<%=val%>" ><%=val%></html:option>
  <%}%>
  </html:select>
   </td>
   <td>
   <html:submit property="value(delete)" value="Delete" onclick="return confirmChangesMsg('Are You Sure You Want To Delete?');" styleClass="submit"/>
   </td>
   </tr>
   </html:form>
   </table>
   </td></tr> 
   <tr><td valign="top" class="tdLayout"><table id="analysis">
   <html:form action="/masters/analysisgrid.do?method=save" method="POST" >
   <%for(int g=0;g<gridfmttyp.size();g++){
     String gridtyp=(String)gridfmttyp.get(g);%>
    <tr><td><table><tr>
    <td><%=gridtyp%></td>
    <td>
    <table>
  
  <%int count =0;
  for(int i=0;i< vwPrpLst.size() ;i++){ 
    String prp = (String)vwPrpLst.get(i);
    if(prpDspBlocked.contains(prp)){
    }else{
    String fldNme= "value("+gridtyp+"_"+prp+")";
    String fldId=gridtyp+"_"+i;
    String onClick="selectgridAna('"+gridtyp+"','"+prp+"','"+i+"')";
    String hdr = util.nvl((String)mprp.get(prp));
    String prpDsc = util.nvl((String)mprp.get(prp+"D"));
    if(hdr == null) {
        hdr = prp;
       } 
    if(count==0){%>
     <tr>
   <% }
   count++;
  %>
  <td>
  <table style="margin-left:20px;">
  <tr><td><html:checkbox property="<%=fldNme%>" value="<%=prp%>" styleId="<%=fldId%>" onclick="<%=onClick%>" name="analysisGridFmtForm" /> </td><td> <%=prpDsc%> </td></tr>
  </table>
 </td>
 <%if(count==10){
 count=0;
 %>
 </tr>
 <%}%>
  <%}}%>
  
  
  </table>
   </td>
   </tr>
   </table></td></tr>
   <%}%>
   <tr>
   <td valign="top" class="tdLayout" align="left">
   <html:text property="value(formatNme)" styleId="formatTxt" name="analysisGridFmtForm" />
   <html:submit property="value(save)" value="Save" onclick="return validateanalysisgrid()" styleClass="submit"/>
   </td>
   </tr>
   </html:form>
   </table></td></tr>
    <tr>
    <td><jsp:include page="/footer.jsp" /> </td>
    </tr>
  </table>
  
  </body>
</html>