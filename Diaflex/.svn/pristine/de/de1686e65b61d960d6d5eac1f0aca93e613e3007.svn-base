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
    <title>Bidding Issue</title>
    <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/bse.css?v=<%=info.getJsVersion()%>"/>
    <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/general.css?v=<%=info.getJsVersion()%>"/>
     <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/style.css?v=<%=info.getJsVersion()%>"/>
      <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/bse.js?v=<%=info.getJsVersion()%> " > </script>
       <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/assortScript.js?v=<%=info.getJsVersion()%> " > </script>
    <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/priceChange.js?v=<%=info.getJsVersion()%> " > </script>
    <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/newCalScript.js?v=<%=info.getJsVersion()%> " > </script>

  </head>
        <%
        
        String logId=String.valueOf(info.getLogId());
        String onfocus="cook('"+logId+"')";
        String lstNme = (String)gtMgr.getValue("lstNmeBIDISS");
       HashMap stockListMap = (HashMap)gtMgr.getValue(lstNme);
      HashMap allPageDtl= (info.getPageDetails() == null)?new HashMap():(HashMap)info.getPageDetails();
      HashMap pageDtl=(HashMap)allPageDtl.get("BIDDING_ISSUE");
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
  <img src="../images/line.jpg" border="0" /></td><td valign="middle"> <span class="pgHed">Bidding Issue</span> </td>
</tr></table>
  </td>
  </tr>
  <%
   String msg = (String)request.getAttribute("msg");
   if(msg!=null){
     %>
    <tr><td valign="top" class="tdLayout"><span class="redLabel"><%=msg%></span></td></tr>
   <%}%>
   
   <tr>
   <td valign="top" class="tdLayout">
      <html:form action="marketing/biddingIssueAction.do?method=fetch" method="post" onsubmit="return validate_assort()">

   <table class="grid1">
   <tr><th>Packets</th><th>Generic Search</th></tr>
   <tr><td>
   <table><tr><td>Process </td>
   <td>
   <html:select property="value(mprcIdn)"  styleId="mprcIdn" name="biddingIssueForm"    >
            <html:optionsCollection property="mprcList" name="biddingIssueForm" 
            label="prc" value="mprcId" />
    </html:select>
   </td></tr>
   <tr>
    <td>
   Employee : </td>
   <td>
    <html:select property="value(empIdn)"  styleId="empIdn" name="biddingIssueForm"   >
           <html:option value="0" >--select--</html:option>
            <html:optionsCollection property="empList" name="biddingIssueForm" 
            label="emp_nme" value="emp_idn" />
            </html:select>
   
   </td></tr>
   <tr>
   <td>Packet No :</td><td> <html:textarea property="value(vnmLst)" rows="7"  cols="30" name="biddingIssueForm"  /></td>
   </tr>
   </table>
   </td><td valign="top">
       <jsp:include page="/genericSrch.jsp"/>
   </td></tr>
   
   <tr><td colspan="2" align="center"><html:submit property="view" value="View" styleClass="submit"/></td></tr>
   </table></html:form>
   </td></tr>
   <tr>
   <td valign="top" class="tdLayout">
   <table>
     <%
   String view = (String)request.getAttribute("view");
   if(view!=null){
  
   if(stockListMap!=null && stockListMap.size()>0){
    ArrayList vwPrpLst = (ArrayList)session.getAttribute("mktViewLst");
    HashMap totals = (HashMap)gtMgr.getValue(lstNme+"_TTL");
    HashMap mprp = info.getMprp();
    int sr = 0;
   %>
   <tr><td>
            <table>
            <tr><td></td><td>Value</td><td>Discount</td><td>Avg</td></tr>
            <tr><td>Old Value</td>
            <td><input type="text" id="old_vlu" size="15" value="<%=totals.get("MV")%>" readonly="readonly" /></td>
            <td><input type="text" id="old_dis" size="15" value="<%=totals.get("MD")%>" readonly="readonly" /> </td>
            <td><input type="text" id="old_avg" size="15" value="<%=totals.get("MA")%>" readonly="readonly" /> </td>
             </tr>
            <tr><td>New Value</td>
              <td><html:text property="value(vlu)" styleId="new_vlu" size="15" name="biddingIssueForm" readonly="true"/></td>
            <td><html:text property="value(avgDis)" styleId="new_dis" size="15" name="biddingIssueForm" readonly="true"/></td>
            <td><html:text property="value(avg)" styleId="new_avg" size="15" name="biddingIssueForm" readonly="true"/></td>
                </tr>
            </table>
            </td>
            </tr>
   <html:form action="marketing/biddingIssueAction.do?method=Issue" method="post" onsubmit="return validate_issue('CHK_' , 'count')">
      <html:hidden property="value(lstNme)" name="biddingIssueForm" value="<%=lstNme%>" />
            <html:hidden property="value(memo)" name="biddingIssueForm" styleId="memoId" value="100" />

    <tr><td>
     <table>
            <tr><td>Update Price By </td><td> 
        <html:select property="value(val)" styleId="typ_ALL"  name="biddingIssueForm" >
        <%pageList=(ArrayList)pageDtl.get("OPTION");
      if(pageList!=null && pageList.size() >0){
      for(int j=0;j<pageList.size();j++){
      pageDtlMap=(HashMap)pageList.get(j);
      fld_ttl=(String)pageDtlMap.get("fld_ttl");
      val_cond=(String)pageDtlMap.get("val_cond");
      fld_typ=(String)pageDtlMap.get("fld_typ");
      dflt_val = (String)pageDtlMap.get("dflt_val");
      %>
          <html:option value="<%=dflt_val%>"><%=fld_ttl%></html:option>

     <% } }%>
       </html:select></td> <td><html:text property="value(diff)" styleId="chng_ALL" size="10" name="biddingIssueForm"/> </td>
       <td><button type="button" class="submit" onClick="PriceChangeMemo('ALL')" >Verify </button>
         </td>
  </tr>
            </table>
    
    </td></tr>
      
  <tr><td>
   <table><tr><td>
   <html:submit property="value(issue)" styleClass="submit" value="Issue" /></td> 
   <%
  ArrayList rolenmLst=(ArrayList)info.getRoleLst();
  String usrFlg=util.nvl((String)info.getUsrFlg());
  if(usrFlg.equals("SYS") || rolenmLst.contains("ADMIN")){
  %>
  <td><img src="../images/edit.jpg" title="Edit Properties Module" onclick="newWindow('<%=info.getReqUrl()%>/dynamicProperty.do?method=load&mdl=AS_VIEW&sname=asViewLst&par=A')" border="0" width="15px" height="15px"/> </td>
  <%}%>
  </tr></table>
  </td>
   </tr>
   <tr><td>
   <table class="grid1">
   <tr> <th><input type="checkbox" name="checkAll" id="checkAll" onclick="ALLCheckBoxCal('checkAll','CHK_')" /> </th><th>Sr</th>
        <th>Packet No.</th>
      
    <%for(int j=0; j < vwPrpLst.size(); j++ ){
    String prp = (String)vwPrpLst.get(j);
    
     String hdr = util.nvl((String)mprp.get(prp));
    String prpDsc = util.nvl((String)mprp.get(prp+"D"));
    if(hdr == null) {
        hdr = prp;
       }  
%>
<th title="<%=prpDsc%>"><%=prp%></th>
<%}%> 
<th>Update Price By</th><th>New Price</th>
<th>New Discout</th><th>Rap Val</th><th>Value</th>
</tr>
 <%
  ArrayList stockIdnLst =new ArrayList();
  LinkedHashMap stockList = new LinkedHashMap(stockListMap); 
  Set<String> keys = stockList.keySet();
 for(String key: keys){
       stockIdnLst.add(key);
  }

 for(int i=0; i < stockIdnLst.size(); i++ ){
  String stkIdn = (String)stockIdnLst.get(i);
 GtPktDtl stockPkt = (GtPktDtl)stockList.get(stkIdn);
 String cts = (String)stockPkt.getCts();
 String onclick = "CalTtlOnChlick("+stkIdn+" , this , 'NO' )";
 sr = i+1;
 String typId = "typ_"+stkIdn;
 String diffId = "chng_"+stkIdn;
 String checkFldId = "CHK_"+sr;
 String checkFldVal = "cb_stk_"+stkIdn ;
 String onChange = "PriceChangeMemo("+stkIdn+")";
 String rapValId = "rapVal_"+String.valueOf(stkIdn);
 String newPriceId = "nwprice_"+String.valueOf(stkIdn);
 String valId = "val_"+String.valueOf(stkIdn);
 String newDisId = "nwdis_"+String.valueOf(stkIdn);
 String typVal ="value(typ_"+String.valueOf(stkIdn)+")";  
 String diff = "value(chng_"+String.valueOf(stkIdn)+")";
 String newPrice = "value("+newPriceId+")";
 String newDis = "value("+newDisId+")";
 String rapVal = "value("+rapValId+")";
 String valFld = "value("+valId+")";
 String rte = stockPkt.getValue("RTE");
  String rap_dis = stockPkt.getValue("RAP_DIS");

 %>
<tr>
<td><input type="checkbox" name="<%=checkFldVal%>" id="<%=checkFldId%>" value="<%=stkIdn%>" onclick="<%=onclick%>" /></td>
<td><%=sr%></td>
<td><%=stockPkt.getVnm()%><input type="hidden" id="CTS_<%=stkIdn%>"  value="<%=cts%>" /></td>

<%for(int j=0; j < vwPrpLst.size(); j++ ){
    String prp = (String)vwPrpLst.get(j);
 %>
    <td><%=stockPkt.getValue(prp)%></td>
<%}%>
<td>
  <table><tr><td>
        <html:select property="<%=typVal%>" styleId="<%=typId%>" onchange="<%=onChange%>"  name="biddingIssueForm" >
        <%pageList=(ArrayList)pageDtl.get("OPTION");
      if(pageList!=null && pageList.size() >0){
      for(int j=0;j<pageList.size();j++){
      pageDtlMap=(HashMap)pageList.get(j);
      fld_ttl=(String)pageDtlMap.get("fld_ttl");
      val_cond=(String)pageDtlMap.get("val_cond");
      fld_typ=(String)pageDtlMap.get("fld_typ");
      dflt_val = (String)pageDtlMap.get("dflt_val");
      %>
     <html:option value="<%=dflt_val%>"><%=fld_ttl%></html:option>
    <%}}%>
       
        </html:select></td>
        <td><html:text property="<%=diff%>" styleId="<%=diffId%>"   onchange="<%=onChange%>" size="10" name="biddingIssueForm"/>
             </td></tr></table>  
</td>
 <td><html:text property="<%=newPrice%>" styleId="<%=newPriceId%>" value="<%=rte%>"  size="10" name="biddingIssueForm"/></td>
 <td><html:text property="<%=newDis%>" styleId="<%=newDisId%>" value="<%=rap_dis%>" size="10" name="biddingIssueForm"/></td>
 <td><html:text property="<%=rapVal%>" styleId="<%=rapValId%>" size="10" name="biddingIssueForm"/></td>
 <td><html:text property="<%=valFld%>" styleId="<%=valId%>" size="10" name="biddingIssueForm"/></td>
           
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
   </td></tr>
   <tr>
<td><jsp:include page="/footer.jsp" /> </td>
</tr>
   </table>
 </body>
</html>