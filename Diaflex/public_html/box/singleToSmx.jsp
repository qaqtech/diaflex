<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page errorPage="../error_page.jsp" %>
<%@ page contentType="text/html;charset=windows-1252"%>
<jsp:useBean id="info" class="ft.com.InfoMgr" scope="session" />
<jsp:useBean id="gtMgr" class="ft.com.GtMgr" scope="session" />

<jsp:useBean id="util" class="ft.com.JspUtil" scope="page" />
<%@ page import="java.util.ArrayList,java.util.LinkedHashMap, java.util.Set,java.util.HashMap,java.sql.ResultSet,ft.com.*,java.util.Collections, java.util.Date, java.util.ArrayList, ft.com.dao.*"%>
<%@taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>Single to Smx</title>
    <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/bse.css?v=<%=info.getJsVersion()%>"/>
    <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/general.css?v=<%=info.getJsVersion()%>"/>
     <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/style.css?v=<%=info.getJsVersion()%>"/>
      <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/bse.js?v=<%=info.getJsVersion()%> " > </script>
       <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/assortScript.js?v=<%=info.getJsVersion()%> " > </script>
              <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/newCalScript.js?v=<%=info.getJsVersion()%> " > </script>

  </head>
        <%String logId=String.valueOf(info.getLogId());
        String onfocus="cook('"+logId+"')";
        %>
 <body onfocus="<%=onfocus%>" onload="MM_preloadImages('images/faunalogo.png')" topmargin="0" leftmargin="0" rightmargin="0" bottommargin="0">
<%
String lstNme = (String)request.getAttribute("lstNme");
String method = util.nvl(request.getParameter("method"));
HashMap stockListMap = new HashMap();
boolean disabled= false;
if(method.equals("fecth")){
if(lstNme!=null)
stockListMap = (HashMap)gtMgr.getValue(lstNme);
if(stockListMap!=null && stockListMap.size()>0)
disabled= true;
}

%>


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
  <img src="../images/line.jpg" border="0" /></td><td valign="middle"> <span class="pgHed">Single to Smx</span> </td>
</tr></table>
  </td>
  </tr>
    <%String msg = (String)request.getAttribute("msg");
  if(msg!=null){
  %>
  <tr><td valign="top" class="tdLayout"> <span class="redLabel" > <%=msg%></span></td></tr>
  <%}%>
    <%
   String vnmNtFnd = (String)request.getAttribute("vnmNotFnd");
     if(vnmNtFnd!=null){%>
    <tr><td valign="top" class="tdLayout"><span class="redLabel"><%=vnmNtFnd%></span></td></tr>
   <%}%>
   <tr>
   
   <td valign="top" class="tdLayout">
   <table><tr><td>
   <html:form action="box/singleToSmx.do?method=fecth" method="post" onsubmit="return validate_assort()">
  <table  class="grid1">
  <tr><th colspan="2" align="center">Generic Search 
  <%
  ArrayList rolenmLst=(ArrayList)info.getRoleLst();
  String usrFlg=util.nvl((String)info.getUsrFlg());
  if(usrFlg.equals("SYS") || rolenmLst.contains("ADMIN")){
  %>
  <img src="../images/edit.jpg" title="Edit Properties Module" onclick="newWindow('<%=info.getReqUrl()%>/dynamicProperty.do?method=load&mdl=SNGL_SMX_SRCH&sname=SNGL_SMX_SRCH&par=GENERIC')" border="0" width="15px" height="15px"/> 
  <%}%>
  </th></tr>
  <tr>
   <td><jsp:include page="/genericSrch.jsp"/></td>
   <td> <html:textarea property="value(vnmLst)" rows="7"  cols="30" name="singleToSmxForm"  /></td>
   </tr>
   <tr><td colspan="2" align="center"><html:submit property="value(srch)" value="Fetch" styleClass="submit" /></td> </tr>
   </table>
   </html:form>
   </td></tr>
   
   <%
   String view = (String)request.getAttribute("view");
   if(view!=null){
   ArrayList prpDspBlocked = info.getPageblockList();
   if(stockListMap!=null && stockListMap.size()>0){
    ArrayList vwPrpLst = (ArrayList)session.getAttribute("SNGL_SMX_VW");
    HashMap mprp = info.getMprp();
        HashMap prp = info.getPrp();
        ArrayList boxIdList = (ArrayList)prp.get("BOX_IDV");
    int sr = 0;
   %>
   <html:form action="box/singleToSmx.do?method=save" method="post" onsubmit="return validate_issue('CHK_' , 'count')">
      <html:hidden property="value(lstNme)" name="assortIssueForm" value="<%=lstNme%>" />

   
   <tr><td>
   <table><tr><td>
   <html:submit property="value(save)" styleClass="submit" value="Transfer To Marketing" /></td> 
   <%
  ArrayList rolenmLst=(ArrayList)info.getRoleLst();
  String usrFlg=util.nvl((String)info.getUsrFlg());
  if(usrFlg.equals("SYS") || rolenmLst.contains("ADMIN")){
  %>
  <td><img src="../images/edit.jpg" title="Edit Properties Module" onclick="newWindow('<%=info.getReqUrl()%>/dynamicProperty.do?method=load&mdl=SNGL_SMX_VW&sname=SNGL_SMX_VW&par=A')" border="0" width="15px" height="15px"/> </td>
  <%}%>
  </tr></table>
  </td>
   </tr>
   <tr><td>
   <table class="grid1">
   <tr> <th><input type="checkbox" name="checkAll" id="checkAll" onclick="ALLCheckBoxCal('checkAll','CHK_')" /> </th><th>Sr</th>
        <th>Packet No.</th>
         <th>BOX ID
         <%String chksrvAll = "chksrvAlltext('BOX_ID')";%>
         <label>
        <input type="text" name="BOX_ID" id="BOX_ID" autocomplete="off"  list="characters" onchange="<%=chksrvAll%>">
        <datalist id="characters">
        <%for(int b=0;b<boxIdList.size();b++){
        String boxprp=(String)boxIdList.get(b);%>
        <option value="<%=boxprp%>">
        <%}%>  
          </datalist>
          </label>
         </th>
    <%for(int j=0; j < vwPrpLst.size(); j++ ){
    String lprp = (String)vwPrpLst.get(j);
    if(prpDspBlocked.contains(lprp)){
       }else{
     String hdr = util.nvl((String)mprp.get(lprp));
    String prpDsc = util.nvl((String)mprp.get(lprp+"D"));
    if(hdr == null) {
        hdr = lprp;
       }  
%>
<th title="<%=prpDsc%>"><%=lprp%></th>
<%}}%>       
</tr>
 <%
 ArrayList stockIdnLst =new ArrayList();
 LinkedHashMap stockList = new LinkedHashMap(stockListMap); 

 Set<String> keys = stockList.keySet();
        for(String key: keys){
       stockIdnLst.add(key);
        }
        Collections.sort(boxIdList);
 for(int i=0; i < stockIdnLst.size(); i++ ){
  String stkIdn = (String)stockIdnLst.get(i);
 GtPktDtl stockPkt = (GtPktDtl)stockList.get(stkIdn);
 String cts = (String)stockPkt.getCts();
 sr = i+1;
 String checkFldId = "CHK_"+sr;
 String checkFldVal = "cb_stk_"+stkIdn ;
 %>
<tr>
<td><input type="checkbox" name="<%=checkFldVal%>" id="<%=checkFldId%>" value="<%=stkIdn%>" onclick="" /></td>
<td><%=sr%></td>
<td><%=stockPkt.getVnm()%></td>
        <td>
        <label>
        <input type="text" name="BOX_ID_<%=stkIdn%>" id="BOX_ID_<%=stkIdn%>" autocomplete="off"  list="characters" onchange="">
        <datalist id="characters">
        <%for(int b=0;b<boxIdList.size();b++){
        String boxprp=(String)boxIdList.get(b);%>
        <option value="<%=boxprp%>">
        <%}%>  
          </datalist>
          </label>
          </td>
<%for(int j=0; j < vwPrpLst.size(); j++ ){
    String lprp = (String)vwPrpLst.get(j);
    if(prpDspBlocked.contains(lprp)){
       }else{
    %>
    <td><%=stockPkt.getValue(lprp)%></td>
<%}}%>
</tr>
   <%}%>
   </table></td></tr>
   <input type="hidden" name="count" id="count" value="<%=sr%>" />
   </html:form>
   <%}
   else{%>
   <tr><td>Sorry no result found </td></tr>
   <%}
   }%>
   
   </table>
   
  
  
   <tr>
    <td><jsp:include page="/footer.jsp" /> </td>
    </tr>
  </table>

  </body>
</html>