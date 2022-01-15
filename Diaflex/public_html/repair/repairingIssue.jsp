<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page errorPage="../error_page.jsp" %>
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import="java.util.ArrayList,java.util.HashMap,java.sql.ResultSet,ft.com.*, java.util.Date, java.util.ArrayList, ft.com.dao.*"%>
<jsp:useBean id="info" class="ft.com.InfoMgr" scope="session" />
<jsp:useBean id="util" class="ft.com.JspUtil" scope="page" />
<%@taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>
<%@taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>


<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>Repairing Issue</title>
   <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/bse.css?v=<%=info.getJsVersion()%>"/>
    <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/style.css?v=<%=info.getJsVersion()%>"/>
    <script src="<%=info.getReqUrl()%>/scripts/bse.js?v=<%=info.getJsVersion()%>" type="text/javascript"></script>
    <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/ajax.js?v=<%=info.getJsVersion()%> " > </script>
     <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/bse.js?v=<%=info.getJsVersion()%> " > </script>
  </head>
        <%String logId=String.valueOf(info.getLogId());
        String onfocus="cook('"+logId+"')";
         HashMap allPageDtl= (info.getPageDetails() == null)?new HashMap():(HashMap)info.getPageDetails();
    HashMap pageDtl=(HashMap)allPageDtl.get("REPAIR_ISSUE");
    ArrayList pageList=new ArrayList();
    HashMap pageDtlMap=new HashMap();
    String fld_nme="",fld_ttl="",val_cond="",dflt_val="",fld_typ="",form_nme="";
    ArrayList issEditPrp = (session.getAttribute("RepairIssueEditPRP") == null)?new ArrayList():(ArrayList)session.getAttribute("RepairIssueEditPRP");
    HashMap prpLst = info.getPrp();
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
  <img src="../images/line.jpg" border="0" /></td><td valign="middle"> <span class="pgHed">Repairing Issue</span> </td>
</tr></table>
  </td>
  </tr>
  <%if(request.getAttribute("msg")!=null){%>
  <tr><td valign="top" height="15" class="tdLayout">
  <span class="redLabel"> <%=request.getAttribute("msg")%></span>
  </td></tr>
   <tr><td valign="top" height="15" class="tdLayout">
   <%
   int accessidn=Integer.parseInt(util.nvl((String)request.getAttribute("accessidn"),"0"));
   HashMap dbinfo = info.getDmbsInfoLst();
   String cnt = (String)dbinfo.get("CNT");
     String repPath = (String)dbinfo.get("REP_PATH");
   String memoIdn = (String)request.getAttribute("issueId");
   String url =repPath+"/reports/rwservlet?"+cnt+"&report="+info.getRportUrl()+"\\reports\\Repairing_Prt.jsp&p_idn="+memoIdn+"&p_access="+accessidn ;
   %>
  <a href="<%=url%>"  target="_blank"><span class="txtLink" >Click Here for Report</span></a>
  <%if(cnt.equalsIgnoreCase("ag")){
   url =repPath+"/reports/rwservlet?"+cnt+"&report="+info.getRportUrl()+"\\reports\\lab_dtl.jsp&P_id="+memoIdn+"&p_access="+accessidn ;
  %>
   <a href="<%=url%>"  target="_blank"><span class="txtLink" >Detail List Report</span></a>
 
  <%}%>
  </td></tr>
  <%}%>
  <tr><td valign="top" class="tdLayout">
  <html:form action="repair/repairIssue.do?method=view" method="post" onsubmit="return repairIssue('Manufacturing Unit.')">
   <table class="grid1">
  <tr><td>Process </td>
   <td>
   <html:select property="value(mprcIdn)"  styleId="mprcIdn" name="repairIssueForm"   >
            <html:optionsCollection property="mprcList" name="repairIssueForm" 
            label="prc" value="mprcId" />
    </html:select>
   </td></tr>
   <%
   pageList=(ArrayList)pageDtl.get("STT");
     if(pageList!=null && pageList.size() >0){
        %>
       <tr> <td>&nbsp;&nbsp;&nbsp;Status</td><td>
  <html:select property="value(stt)"  styleId="stt" name="repairIssueForm"  >
    <%for(int j=0;j<pageList.size();j++){
         pageDtlMap=(HashMap)pageList.get(j);
         fld_nme=(String)pageDtlMap.get("fld_nme");
         fld_ttl=(String)pageDtlMap.get("fld_ttl");
         val_cond=(String)pageDtlMap.get("val_cond");%>
         <option value="<%=fld_nme%>"><%=fld_ttl%></option>
         <% } %>
         
  </html:select>
     </td>
  </tr>
   <%
   }%>
   <tr>
  <td><span class="redLabel">*</span> Manufacturing Unit</td><td>
  <html:select property="value(empIdn)"  styleId="empIdn" name="repairIssueForm" onchange="getTrms(this,'SRCH')" >
    <html:option value="0" >--select--</html:option>
    <html:optionsCollection property="empList" name="repairIssueForm" 
            label="emp_nme" value="emp_idn" />
  </html:select>
  </td>
  </tr>
  <tr>
  <td><span class="redLabel">*</span>Terms</td><td>
     <html:select property="value(byrRln)" name="repairIssueForm"  styleId="rlnId"  >
         
             <html:optionsCollection property="trmsLst" name="repairIssueForm"
            label="trmDtl" value="relId" />
            
            </html:select>
    </td></tr>
  <tr><td>Packets</td>
  <td valign="top">
  <html:textarea property="value(vnmLst)" rows="5" cols="30" name="repairIssueForm" />
  </td></tr>
 
  <tr><td align="center" colspan="2">
  <%   
    
    pageList=(ArrayList)pageDtl.get("SUBMIT");
     if(pageList!=null && pageList.size() >0){
       for(int j=0;j<pageList.size();j++){
         pageDtlMap=(HashMap)pageList.get(j);
         fld_nme=(String)pageDtlMap.get("fld_nme");
         fld_ttl=(String)pageDtlMap.get("fld_ttl");
         val_cond=(String)pageDtlMap.get("val_cond"); %>
        <html:submit property="<%=fld_nme%>" value="<%=fld_ttl%>" styleClass="submit" />

 <%
}}
%>
  <!--<html:submit property="view" value="View" styleClass="submit"/>
   <html:submit property="viewAll" value="View All" styleClass="submit"/>-->
   </td>
  </tr>
  </table></html:form></td></tr>
     <%
   String view = (String)request.getAttribute("view");
   ArrayList stockList = (ArrayList)session.getAttribute("StockList");
   if(view!=null){
   
   if(stockList!=null && stockList.size()>0){
   ArrayList prpDspBlocked = info.getPageblockList();
    ArrayList vwPrpLst = (ArrayList)session.getAttribute("repViewLst");
    HashMap totals = (HashMap)request.getAttribute("totalMap");
    HashMap mprp = info.getMprp();
    int sr = 0;
   %>
   
   <tr><td valign="top" class="tdLayout">
   <table>
   <tr><td>Total :&nbsp;&nbsp;</td><td><span id="ttlqty"> <%=totals.get("qty")%>&nbsp;&nbsp;</span></td><td>cts&nbsp;&nbsp;</td><td><span id="ttlcts"><%=totals.get("cts")%>&nbsp;&nbsp;</span></td><td>Selected:&nbsp;&nbsp;</td><td> Total :&nbsp;&nbsp;</td> <td><span id="qtyTtl">0&nbsp;&nbsp;</span></td><td>Cts&nbsp;&nbsp;</td><td><span id="ctsTtl">0</span> </td> </tr>
  
   </table>
   </td></tr>
   <html:form action="repair/repairIssue.do?method=Issue" method="post" onsubmit="return validate_issue('CHK_' , 'count')">
   <html:hidden property="value(emp)" name="repairIssueForm"/>
    <html:hidden property="value(rln)" name="repairIssueForm"/>
   <tr><td valign="top" class="tdLayout"><table><tr><td>
   <html:submit property="value(issue)" styleClass="submit" value="Issue" /> 
  </td>
  <%
  ArrayList rolenmLst=(ArrayList)info.getRoleLst();
  String usrFlg=util.nvl((String)info.getUsrFlg());
  if(usrFlg.equals("SYS") || rolenmLst.contains("ADMIN")){
  %>
  <td><img src="../images/edit.jpg" title="Edit Properties Module" onclick="newWindow('<%=info.getReqUrl()%>/dynamicProperty.do?method=load&mdl=REP_VIEW&sname=repViewLst&par=A')" border="0" width="15px" height="15px"/> </td>
  <%}%>
  </tr></table></td></tr>
   
   <tr><td valign="top" class="tdLayout">
   <table class="grid1">
   <tr> <th><input type="checkbox" name="checkAll" id="checkAll" onclick="checkALL('CHK_','count')" /> </th><th>Sr</th>
        <th>Packet No.</th>
      
        <%for(int j=0; j < vwPrpLst.size(); j++ ){
    String prp = (String)vwPrpLst.get(j);
    if(prpDspBlocked.contains(prp)){
    }else{
     String hdr = (String)mprp.get(prp);
    String prpDsc = (String)mprp.get(prp+"D");
    if(hdr == null) {
        hdr = prp;
       }  

%>
<th title="<%=prpDsc%>"><%=hdr%></th>

<%}}%>  

<%for(int s=0; s < issEditPrp.size();s++){
    String lprp = (String)issEditPrp.get(s);
    ArrayList prpVal = (ArrayList)prpLst.get(lprp+"V");
    ArrayList prpPrt = (ArrayList)prpLst.get(lprp+"P");
    String prpDsc = (String)mprp.get(lprp+"D");
    String prpTyp = util.nvl((String)mprp.get(lprp+"T"));
    String prpFld = "value("+lprp+")";
    String chksrvAll = "chksrvAll('"+lprp+"')";
     String chksrvTxtAll = "chksrvTXTAll('"+lprp+"')";
    String selectALL = lprp;
    %>
    <th><%if(prpTyp.equals("C")){%>
     <html:select  property="<%=prpFld%>" styleId="<%=selectALL%>" name="repairIssueForm" onchange="<%=chksrvAll%>"  style="width:100px"   > 
      <html:option value="0">--select--</html:option>
            <%
            for(int k=0; k < prpVal.size(); k++) {
                String pPrt = (String)prpVal.get(k);
             %>
              <html:option value="<%=pPrt%>" ><%=prpPrt.get(k)%></html:option> 
            <%}%>
     </html:select>  
     <%}else{%>
     <html:text property="<%=prpFld%>" styleId="<%=selectALL%>" name="repairIssueForm"  style="width:100px" onchange="<%=chksrvTxtAll%>" />
     <%}%>
     <%=prpDsc%>
     </th>
    <%}%>
</tr>
 <%
 
 for(int i=0; i < stockList.size(); i++ ){
 HashMap stockPkt = (HashMap)stockList.get(i);
 String stkIdn = (String)stockPkt.get("stk_idn");
 String cts = (String)stockPkt.get("CRTWT");
 String onclick = "AssortTotalCal(this,"+cts+",'','')";
 sr = i+1;
 String checkFldId = "CHK_"+sr;
 String checkFldVal = "value("+stkIdn+")";
 %>
<tr>
<td><html:checkbox property="<%=checkFldVal%>" styleId="<%=checkFldId%>" name="repairIssueForm" onclick="<%=onclick%>" value="yes" /> </td>
<td><%=sr%></td>
<td><%=stockPkt.get("vnm")%><input type="hidden" id="CTS_<%=stkIdn%>"  value="<%=cts%>" /></td>

<%for(int j=0; j < vwPrpLst.size(); j++ ){
    String prp = (String)vwPrpLst.get(j);
    if(prpDspBlocked.contains(prp)){
    }else{
    %>
    <td><%=stockPkt.get(prp)%></td>
<%}}%>
<%for(int s=0; s < issEditPrp.size();s++){
String lprp = (String)issEditPrp.get(s);
String prpTyp = util.nvl((String)mprp.get(lprp+"T"));
ArrayList prpVal = (ArrayList)prpLst.get(lprp+"V");
ArrayList prpDsc = (ArrayList)prpLst.get(lprp+"P");
String fldVal = "value("+lprp+"_"+stkIdn+")";
String fldId = lprp+"_"+sr;
%>
<td>
<%if(prpTyp.equals("C")){%>
<html:select property="<%=fldVal%>" styleId="<%=fldId%>" name="repairIssueForm" style="width:100px">
<html:option value="0" >--select--</html:option>
<%for(int p=0;p<prpVal.size();p++){
String val = (String)prpVal.get(p);
%>
<html:option value="<%=val%>" ><%=prpDsc.get(p)%></html:option>
<%}%></html:select>
<%}else{%>
<html:text property="<%=fldVal%>" styleId="<%=fldId%>" style="width:100px" name="repairIssueForm" />
<%}%>
</td>
<%}%>
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
   <tr>
    <td><jsp:include page="/footer.jsp" /> </td>
    </tr>
  </table>
  </body>
</html>