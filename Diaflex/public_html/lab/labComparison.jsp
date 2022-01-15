<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page errorPage="../error_page.jsp" %>
<%@ page contentType="text/html;charset=windows-1252"%>
<jsp:useBean id="info" class="ft.com.InfoMgr" scope="session" />
<jsp:useBean id="util" class="ft.com.JspUtil" scope="page" />
<jsp:useBean id="gtMgr" class="ft.com.GtMgr" scope="session" />

<%@ page import="java.util.ArrayList,java.util.LinkedHashMap, ft.com.dao.GtPktDtl,java.util.Set,java.util.HashMap,java.sql.ResultSet,ft.com.*, java.util.Date, java.util.ArrayList, ft.com.dao.*"%>
<%@taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>Lab Final Check issue</title>
    <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/bse.css?v=<%=info.getJsVersion()%>"/>
    <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/general.css?v=<%=info.getJsVersion()%>"/>
     <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/style.css?v=<%=info.getJsVersion()%>"/>
<script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/bse.js?v=<%=info.getJsVersion()%> " > </script>
       <script src="../scripts/assortScript.js" type="text/javascript"></script>
  </head>
        <%String logId=String.valueOf(info.getLogId());
        String onfocus="cook('"+logId+"')";
         String lstNme = (String)gtMgr.getValue("lstNmeCOM");
      gtMgr.setValue(lstNme+"_SEL",new ArrayList());
 
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
  <img src="../images/line.jpg" border="0" /></td><td valign="middle"> <span class="pgHed">Lab Final Check Issue</span> </td>
</tr></table>
  </td>
  </tr>
   <%
   
   String vnmNtFnd = (String)request.getAttribute("vnmNotFnd");
     if(vnmNtFnd!=null){%>
    <tr><td valign="top" class="tdLayout"><span class="redLabel"><%=vnmNtFnd%></span></td></tr>
   <%}%>
  <% String msg = (String)request.getAttribute("msg");
  if(msg!=null){
  %>
  <tr>
   <td valign="top" class="tdLayout"><span class="redLabel"><%=msg%></span></td></tr>
  <%}%>
   <tr>
   <td valign="top" class="tdLayout">
   <html:form  action="lab/labComparison.do?method=view" method="post" >
  <table  class="grid1">
  <tr><th colspan="2">Generic Search </th></tr>
  <tr><td align="center">Report Type </td><td>
  <html:select property="reportTyp" name="labComparison">
   <html:option value="NONE">None</html:option>
   <html:option value="OK">Color And Clarity OK</html:option>
  <html:option value="UP">Upgrades</html:option>
  <html:option value="CCDN">Color or Clarity Downgrade </html:option>
  <html:option value="OTHDN">Others Downgrade</html:option>
  </html:select>
  </td> </tr>
   <tr>
     <td align="center">Packets. </td><td>
    <html:textarea property="value(vnmLst)" name="labComparison" />
     </td>
    </tr>
    <tr>
     <td align="center">Sequence No </td><td>
    <html:text property="value(seq)" name="labComparison" />
     </td>
    </tr>
  <tr><td colspan="2">
   <jsp:include page="/genericSrch.jsp"/>
  </td></tr>
 <tr><td  align="center"><html:submit property="view" value="View" styleClass="submit"/> 
 </td>
  </tr>
  
  </table></html:form>
   </td></tr>
   <tr><td valign="top" height="30" class="tdLayout"></td></tr>
  
    <html:form  action="lab/labComparison.do?method=Issue" method="post" onsubmit="return validate_issue('CHK_' , 'count')" >
 <html:hidden property="value(lstNme)" styleId="lstNme"  value="<%=lstNme%>"  name="labComparison" />
  <%
   String view = (String)request.getAttribute("view");
   
   if(view!=null){
  HashMap stockListMap = (HashMap)gtMgr.getValue(lstNme);
if(stockListMap!=null && stockListMap.size()>0){
     HashMap dbInfoSys = info.getDmbsInfoLst();
    String col = util.nvl((String)dbInfoSys.get("COL"));
    String clr = util.nvl((String)dbInfoSys.get("CLR"));
    String cut = util.nvl((String)dbInfoSys.get("CUT"));
     String rptTyp = (String)request.getAttribute("rptTyp");
    HashMap mprp = info.getMprp();
     ArrayList vwPrpLst = (ArrayList)session.getAttribute("LbComViewLst");
     ArrayList prpDspBlocked = info.getPageblockList();
     int sr =0;
    %>
     <tr><td valign="top" class="tdLayout">
     <html:submit property="value(submit)" value="Submit" styleClass="submit"/> 
     </td></tr>
    <tr><td valign="top" class="tdLayout">
     <table><tr>
     <td></td>
      <td>
    Employee : </td>
   <td>
   <html:select property="value(empId)"  name="labComparison"   >
          
            <html:optionsCollection property="empList" name="labComparison" 
            label="emp_nme" value="emp_idn" />
            </html:select>
   </td>
     </tr>
     </table>
     </td></tr>
    <tr><td valign="top" class="tdLayout">
    <table cellspacing="2" class="dataTable">
      <tr><th>Sr</th>
         <th>Select  <input  type="checkbox" id="all" name="all" onclick="ALLCheckBox('all','ISS_')" /> </th>
        <th>Packet</th><th>Employee</th>
     <%for(int j=0; j < vwPrpLst.size(); j++ ){
      String prp = (String)vwPrpLst.get(j);
      if(prpDspBlocked.contains(prp)){
    }else{
     String hdr = (String)mprp.get(prp);
    String prpDsc = (String)mprp.get(prp+"D");
    if(hdr == null) {
        hdr = prp;
       }  
    if(prp.equals(cut) && !rptTyp.equals("CCDN")){%>
    <th title="<%=prpDsc%>">Our Val <%=prpDsc%></th>
    <%}
    if(prp.equals(col)||prp.equals(clr)){%>
    <th title="<%=prpDsc%>">Our Val  <%=prpDsc%></th>
   <% }%>
    <th title="<%=prpDsc%>"><%=hdr%></th>
  <%}}%>       
        
        
      </tr>
      <%
      
  ArrayList stockIdnLst =new ArrayList();
 Set<String> keys = stockListMap.keySet();
        for(String key: keys){
       stockIdnLst.add(key);
        }
for(int i=0; i < stockIdnLst.size(); i++ ){

     String stkIdn = (String)stockIdnLst.get(i);
     GtPktDtl pktDtl = (GtPktDtl)stockListMap.get(stkIdn);
      String vnm = util.nvl((String)pktDtl.getValue("vnm"));
      String emp = util.nvl((String)pktDtl.getValue("emp"));
      
   
     sr = i+1;
    String checkFldId = "ISS_"+stkIdn;
    String checkFldVal = "value("+stkIdn+")";
        
      %>
        <tr>
        <td align="left"><%=(i+1)%></td>
        <td><html:checkbox property="<%=checkFldVal%>"  name="labComparison" styleId ="<%=checkFldId%>"  value="yes" /></td>
        <td align="center"><%=vnm%></td>
        <td align="left"><%=emp%></td>
       
  <%for(int j=0; j < vwPrpLst.size(); j++ ){
    String prp = (String)vwPrpLst.get(j);
    if(prpDspBlocked.contains(prp)){
    }else{
     String hdr = (String)mprp.get(prp);
    String prpDsc = (String)mprp.get(prp+"D");
    if(hdr == null) {
        hdr = prp;
       }  
    if(prp.equals(col)){%>
   <td title="<%=prpDsc%>"><%=util.nvl((String)pktDtl.getValue("issCol"))%></td>
   <% }
    if(prp.equals(clr)){%>
 <td title="<%=prpDsc%>"><%=util.nvl((String)pktDtl.getValue("issClr"))%></td>
   <% }%>
     <td title="<%=prpDsc%>"><%=util.nvl((String)pktDtl.getValue(prp))%></td>
      <%}}%>       
    
        </tr>
      <%}%>
    </table>
     <input type="hidden" name="count" id="count" value="<%=sr%>" />
      </td></tr>
    <%}else{%>
    <tr><td>Sorry no result found.</td></tr>
   <% }
    }%>
   
    </html:form>
   
  </table>
  
  
  </body>
</html>