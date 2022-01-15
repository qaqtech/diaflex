<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page errorPage="../error_page.jsp" %>
<%@ page contentType="text/html;charset=windows-1252"%>
<jsp:useBean id="info" class="ft.com.InfoMgr" scope="session" />
<jsp:useBean id="util" class="ft.com.JspUtil" scope="page" />
<%@ page import="java.util.ArrayList,java.util.HashMap,java.sql.ResultSet,ft.com.*, java.util.Date, java.util.ArrayList, ft.com.dao.*"%>
<%@taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>Price Detail Matrix</title>
    <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/bse.css?v=<%=info.getJsVersion()%>"/>
    <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/general.css?v=<%=info.getJsVersion()%>"/>
     <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/style.css?v=<%=info.getJsVersion()%>"/>
          <script language="javaScript" type="text/javascript" src="<%=info.getReqUrl()%>/scripts/ajax.js?v=<%=info.getJsVersion()%>"></script>
      <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/bse.js?v=<%=info.getJsVersion()%> " > </script>
  </head>
        <%String logId=String.valueOf(info.getLogId());
        String onfocus="cook('"+logId+"')";
    HashMap allPageDtl= (info.getPageDetails() == null)?new HashMap():(HashMap)info.getPageDetails();
    HashMap pageDtl=(HashMap)allPageDtl.get("PRICE_DETAIL_MATRIX");
    ArrayList prpDspBlocked = info.getPageblockList();
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
  <img src="../images/line.jpg" border="0" /></td><td valign="middle"> <span class="pgHed">Price Detail Matrix</span> </td>
</tr></table>
  </td>
  </tr>
 
   <tr><td valign="top" class="tdLayout">
   <html:form action="pri/priceDtlMatrix.do?method=fetch" method="post">
  <table  class="grid1">
  <tr><th colspan="2">Generic Search </th></tr>
  <tr>
   <td colspan="2">
    <jsp:include page="/genericSrch.jsp"/>
   </td></tr>
      <%
    pageList= ((ArrayList)pageDtl.get("PRICEON_DATE") == null)?new ArrayList():(ArrayList)pageDtl.get("PRICEON_DATE");  
    if(pageList!=null && pageList.size() >0){
    for(int j=0;j<pageList.size();j++){
    %>
   <tr>
   <td>Date/Price On Date </td>  
   <td><html:text property="value(priceondte)" styleId="priceondte" name="priceDtlMatrixForm" size="8" /><img src="<%=info.getReqUrl()%>/images/calender.png" onClick="setYears(2011, <%=info.getCurrentYear()%>);showCalender(this, 'priceondte');"> &nbsp</td>
   </tr>
    <%}}%>
     <%
    pageList= ((ArrayList)pageDtl.get("COMPARE_DATE") == null)?new ArrayList():(ArrayList)pageDtl.get("COMPARE_DATE");  
    if(pageList!=null && pageList.size() >0){
    for(int j=0;j<pageList.size();j++){
    %>
   <tr>
   <td>Compare Date </td>  
   <td><html:text property="value(compondte)" styleId="compondte" name="priceDtlMatrixForm" size="8" /><img src="<%=info.getReqUrl()%>/images/calender.png" onClick="setYears(2011, <%=info.getCurrentYear()%>);showCalender(this, 'compondte');"> &nbsp</td>
   </tr>
   <%}}%>
   
      <%
    pageList= ((ArrayList)pageDtl.get("GRID") == null)?new ArrayList():(ArrayList)pageDtl.get("GRID");  
            if(pageList!=null && pageList.size() >0){%>
            <tr>
            <td>Grid</td> 
            <td>
            <%for(int j=0;j<pageList.size();j++){
            pageDtlMap=(HashMap)pageList.get(j);
            lov_qry=(String)pageDtlMap.get("lov_qry");
            fld_nme="value("+(String)pageDtlMap.get("fld_nme")+")";
            fld_ttl=(String)pageDtlMap.get("fld_ttl");
            fld_typ=(String)pageDtlMap.get("fld_typ");
            dflt_val=(String)pageDtlMap.get("dflt_val");
            val_cond=(String)pageDtlMap.get("val_cond"); 
            %>
    <html:checkbox property="<%=fld_nme%>" styleId="<%=fld_typ%>" name="priceDtlMatrixForm" value="<%=dflt_val%>"/> <%=fld_ttl%>
    <%}%>
   </td>
   </tr>
    <%}%>
   <tr><td colspan="3" align="center">
   <%
    pageList= ((ArrayList)pageDtl.get("SUBMIT") == null)?new ArrayList():(ArrayList)pageDtl.get("SUBMIT");  
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
            %>
    <html:submit property="<%=fld_nme%>" value="<%=fld_ttl%>" onclick="<%=val_cond%>" styleClass="submit"/>&nbsp;
    <%}}%>
   <!--<html:submit property="value(mfg)" value="Manufacturing Price" styleClass="submit" />
   &nbsp;<html:submit property="value(assort)" value="Assort Price" styleClass="submit" />
   &nbsp;<html:submit property="value(assortdiff)" value="Assort Price Diff" styleClass="submit" onclick="return validatepriceDtlMatrix();" />-->
   </td> </tr>
   </table>
   </html:form>
   </td></tr> 
   <tr><td>&nbsp;</td></tr>
  
  <tr> <td valign="top" class="tdLayout">
  <%
  String view = util.nvl((String)request.getAttribute("view"));
  String recal = util.nvl((String)request.getAttribute("recal"));
  ArrayList shSzList = (ArrayList)session.getAttribute("shSzList");
  ArrayList cheadList = (ArrayList)session.getAttribute("cheadList");
  ArrayList rheadList = (ArrayList)session.getAttribute("rheadList");
  ArrayList gridDtl = (ArrayList)request.getAttribute("gridDtl");
  HashMap dataDtl = (HashMap)session.getAttribute("dataDtl");
  String styleCol="";
  if(!view.equals("")){
  if(view.equals("Y")){
  if(shSzList!=null && shSzList.size()>0){
  %>  
    <table>
    <tr>
    <td>ALL &nbsp;<img src="../images/ico_file_excel.png" onclick="goTo('priceDtlMatrix.do?method=createXL&loopindex=','','')" border="0"/> 
    <%if(recal.equals("Y")){%>
    <%
    pageList= ((ArrayList)pageDtl.get("RECALCULATE") == null)?new ArrayList():(ArrayList)pageDtl.get("RECALCULATE");  
    if(pageList!=null && pageList.size() >0){
    for(int j=0;j<pageList.size();j++){%>
  &nbsp;&nbsp;<button type="button" class="submit" onclick="goTo('priceDtlMatrix.do?method=save&loopindex=','','')"  name="excel" >ReCaclulate All</button>
  <%}}%>
  <%}%>
    </td>
    </tr>
  <%for(int i=0 ;i <shSzList.size();i++ ){
  String key = (String)shSzList.get(i);
  String keyLable = key.replaceAll("_", "  SIZE :");
  keyLable = "Shape : "+keyLable;
  %>
  <tr><td><b><%=keyLable%></b>&nbsp; 
  <img src="../images/ico_file_excel.png" onclick="goTo('priceDtlMatrix.do?method=createXL&loopindex=<%=i%>','','')" border="0"/> 
  <%if(recal.equals("Y")){%>
      <%
    pageList= ((ArrayList)pageDtl.get("RECALCULATE") == null)?new ArrayList():(ArrayList)pageDtl.get("RECALCULATE");  
    if(pageList!=null && pageList.size() >0){
    for(int j=0;j<pageList.size();j++){%>
  &nbsp;&nbsp;<button type="button" class="submit" onclick="goTo('priceDtlMatrix.do?method=save&loopindex=<%=i%>','','')"  name="excel" >ReCaclulate</button>
  <%}}%>
  <%}%>
  </td></tr>
  <tr><td>
  <%if(gridDtl!=null && gridDtl.size()>0){
  String ttl_final="Manufacturing";
            pageList= ((ArrayList)pageDtl.get("TTL_FINAL") == null)?new ArrayList():(ArrayList)pageDtl.get("TTL_FINAL");  
            if(pageList!=null && pageList.size() >0){
            for(int j=0;j<pageList.size();j++){
            pageDtlMap=(HashMap)pageList.get(j);
            ttl_final=(String)pageDtlMap.get("fld_ttl");
            }}
  %>
  <div><b>&nbsp;&nbsp;<%=ttl_final%> Final</b></div>
  <%}%>
  <table class="grid1"><tr><th><%=util.nvl((String)dataDtl.get("title"))%></th>
  <%
  
  for(int j=0;j< cheadList.size();j++){
    String columnV = (String)cheadList.get(j);
    %>
 <th><%=columnV%></th>
  <%}%>
  </tr>
  <%for(int m=0;m< rheadList.size();m++){
    String rowV = (String)rheadList.get(m);
    %>
  <tr> <th><%=rowV%></th>
  <%for(int n=0;n< cheadList.size();n++){
    String columnV = (String)cheadList.get(n);
    String keymfg_dis = key+"_"+rowV+"_"+columnV;
    String valmfg_dis = util.nvl((String)dataDtl.get(keymfg_dis));
    %>
    <td align="right"><%=valmfg_dis%></td>
  <%}%>
  </tr>
  <%}%>
  </table>
  </td>
  <%
  if(gridDtl!=null && gridDtl.size()>0){
  for(int g=0;g<gridDtl.size();g++){
  String grid=util.nvl((String)gridDtl.get(g));
  String title="Singles";
  if(grid.equals("MIXCMP"))
  title="Mix";
  if(grid.equals("MFGRTE"))
  title="Mfg Rte";
  %>
  <!---->
  <td>
  <div><b>&nbsp;&nbsp;<%=title%></b></div>
  <table class="grid1"><tr><th><%=util.nvl((String)dataDtl.get("title"))%></th>
  <%
  
  for(int j=0;j< cheadList.size();j++){
    String columnV = (String)cheadList.get(j);
    %>
 <th><%=columnV%></th>
  <%}%>
  </tr>
  <%for(int m=0;m< rheadList.size();m++){
    String rowV = (String)rheadList.get(m);
    %>
  <tr> <th><%=rowV%></th>
  <%for(int n=0;n< cheadList.size();n++){
    String columnV = (String)cheadList.get(n);
    String keymfg_dis = key+"_"+rowV+"_"+columnV+"_"+grid;
    String valmfg_dis = util.nvl((String)dataDtl.get(keymfg_dis));
    %>
    <td align="right" title="<%=grid%>"><%=valmfg_dis%></td>
  <%}%>
  </tr>
  <%}%>
  </table>
  </td>
  <!---->
  <%}}%>
  </tr>
  <%}%>
  </table>
   <%}else{%>
   Sorry No Result Found
   <%}}else{
    double getvalcomp = 0;
    double getvalpriceon=0;
    String percent="";
  HashMap prp = info.getPrp();
  HashMap mprp = info.getMprp();
  rheadList=new ArrayList();
  String title=util.nvl((String)dataDtl.get("title"));
  String priceondte = (String)request.getAttribute("priceondte");
  String compondte = (String)request.getAttribute("compondte");
  if(title.equals("Mix Clarity/Mix Size"))
  rheadList = (ArrayList)prp.get("MIX_CLARITY"+"V");
  if(shSzList!=null && shSzList.size()>0){%> 
  <table>
  <%for(int i=0 ;i <shSzList.size();i++ ){
  String key = (String)shSzList.get(i);
  String keyLable = key.replaceAll("_", "  SIZE :");
  keyLable = "Shape : "+keyLable;
  %>
  <tr><td><b><%=keyLable%></b>&nbsp;</td></tr>
  <tr><!--New As per amit-->
  <td valign="top"><table class="grid1"><tr><th><%=title%></th>
  <%for(int j=0;j< cheadList.size();j++){
    String columnV = (String)cheadList.get(j);
    %>
  <th><%=columnV%></th>
  <%}%>
  </tr>
  <tr>
  <td align="center" nowrap="nowarp"><b>Comp Dt :<%=compondte%></b></td>
  <td align="center" nowrap="nowarp" colspan="cheadList.size()"></td>
  </tr>
  <%for(int m=0;m< rheadList.size();m++){
    String rowV = (String)rheadList.get(m);
    %>
  <tr><th><%=rowV%></th>
  <%for(int n=0;n< cheadList.size();n++){
    getvalcomp = 0;
    String columnV = (String)cheadList.get(n);
    String keycomp = key+"_"+rowV+"_"+columnV+"_COMP";
    String valcomp = util.nvl((String)dataDtl.get(keycomp));
    %>
    <td align="right"><%=valcomp%></td>
  <%}%>
  </tr>
  <%}%>
  </table>
  </td>
  <td valign="top"><table class="grid1"><tr><th><%=title%></th>
  <%for(int j=0;j< cheadList.size();j++){
    String columnV = (String)cheadList.get(j);
    %>
  <th><%=columnV%></th>
  <%}%>
  </tr>
  <tr>
    <td align="center" nowrap="nowarp"><b>Price On Dt :<%=priceondte%></b></td>
  <td align="center" nowrap="nowarp" colspan="cheadList.size()"></td>
  </tr>
  <%for(int m=0;m< rheadList.size();m++){
    String rowV = (String)rheadList.get(m);
    %>
  <tr><th><%=rowV%></th>
  <%for(int n=0;n< cheadList.size();n++){
    getvalcomp = 0;
    getvalpriceon=0;
    String columnV = (String)cheadList.get(n);
    String keypriceon = key+"_"+rowV+"_"+columnV+"_PRON";
    String valpriceon = util.nvl((String)dataDtl.get(keypriceon));
    %>
    <td align="right"><%=valpriceon%></td>
  <%}%>
  </tr>
  <%}%>
  </table>
  </td>
  <td valign="top"><table class="grid1"><tr><th><%=title%></th>
  <%for(int j=0;j< cheadList.size();j++){
    String columnV = (String)cheadList.get(j);
    %>
  <th><%=columnV%></th>
  <%}%>
  </tr>
  <tr>
  <td></td>
  <%for(int j=0;j< cheadList.size();j++){
    String columnV = (String)cheadList.get(j);
    %>
  <td align="center"><b>%</b></td>
  <%}%>
  </tr>
  <%for(int m=0;m< rheadList.size();m++){
    String rowV = (String)rheadList.get(m);
    %>
  <tr><th><%=rowV%></th>
  <%for(int n=0;n< cheadList.size();n++){
    getvalcomp = 0;
    getvalpriceon=0;
    String columnV = (String)cheadList.get(n);
    String keycomp = key+"_"+rowV+"_"+columnV+"_COMP";
    String valcomp = util.nvl((String)dataDtl.get(keycomp));
    String keypriceon = key+"_"+rowV+"_"+columnV+"_PRON";
    String valpriceon = util.nvl((String)dataDtl.get(keypriceon));
    percent="";
    styleCol="";
    if(!valcomp.equals("") && !valcomp.equals("0") && !valpriceon.equals("") && !valpriceon.equals("0")){
    getvalcomp=Double.parseDouble(valcomp);
    getvalpriceon=Double.parseDouble(valpriceon);
    percent=String.valueOf(util.roundToDecimals(((getvalcomp-getvalpriceon)*100)/getvalpriceon,2))+"%";
    if(getvalcomp<=getvalpriceon)
    styleCol = "color: #493935;";
    }
    %>
    <td align="right" style="<%=styleCol%>"><%=percent%></td>
  <%}%>
  </tr>
  <%}%>
  </table>
  </td>
  <!--New As per amit-->
  </tr>
  <%}%>
   </table>
   <%}
   }
   }%>
   </td>
   </tr>  
   
      <tr>
    <td><jsp:include page="/footer.jsp" /> </td>
    </tr>
  </table>
  
  </body>
</html>