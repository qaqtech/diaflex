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
   <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/bse.js?v=<%=info.getJsVersion()%> " > </script>
<link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/bse.css?v=<%=info.getJsVersion()%>"/>
<link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/style.css?v=<%=info.getJsVersion()%>"/>
<title>Report</title>
</head>
        <%String logId=String.valueOf(info.getLogId());
        String onfocus="cook('"+logId+"')";
        %>
 <body onfocus="<%=onfocus%>" onload="MM_preloadImages('images/faunalogo.png')" topmargin="0" leftmargin="0" rightmargin="0" bottommargin="0">
    <table width="100%"  border="0" align="center" cellpadding="0" cellspacing="0" >
  <tr>
    <td height="5" valign="top" class="greyline"><img src="../images/grey_linebig.png" width="685" height="5" align="right" /></td>
  </tr>
  <tr>
  <td valign="top" class="hedPg">
  <table cellpadding="1" cellspacing="5"><tr><td valign="middle">
  <img src="../images/line.jpg" border="0" /></td><td valign="middle"> 
  <span class="pgHed"><bean:write property="reportNme" name="orclReportForm" />
  </span></td></tr></table>
  </td></tr>
    <%
    HashMap dataDtl= (HashMap)request.getAttribute("dataDtl");
    ArrayList rowList= (ArrayList)request.getAttribute("rowList");
    ArrayList colList= (ArrayList)request.getAttribute("colList");
    String view= util.nvl((String)request.getAttribute("view"));
    String hdr= util.nvl((String)request.getAttribute("hdr"));
    String pkttyp= util.nvl((String)request.getAttribute("pkttyp"));
    String stylesingle="display:none;";
    String stylemix="display:none;";
    String pgDef="STOCK_SUMMARY";
    if(!pkttyp.equals("NR"))
    pgDef="MIX_STOCK_SUMMARY";
    if(hdr.equals("Color/Clarity")){
    stylesingle="";
    }else{
    stylemix="";
    }
  HashMap allPageDtl= (info.getPageDetails() == null)?new HashMap():(HashMap)info.getPageDetails();
  HashMap pageDtl=(HashMap)allPageDtl.get(pgDef);
    ArrayList pageList=new ArrayList();
    HashMap pageDtlMap=new HashMap();
    String fld_nme="",fld_ttl="",val_cond="",dflt_val="",fld_typ="",form_nme="",flg1="",lov_qry="";
    if(!view.equals("")){
    if(dataDtl!=null && dataDtl.size()>0){
    %>
    <tr><td valign="top" class="hedPg"><b>Show</b>
  <%pageList=(ArrayList)pageDtl.get("CHK");
    if(pageList!=null && pageList.size() >0){
    for(int j=0;j<pageList.size();j++){
    pageDtlMap=(HashMap)pageList.get(j);
    fld_nme=(String)pageDtlMap.get("fld_nme");
    fld_typ=(String)pageDtlMap.get("fld_typ");
    fld_ttl=(String)pageDtlMap.get("fld_ttl");
    val_cond=(String)pageDtlMap.get("val_cond");
    %>
       <input type="checkbox" name="<%=fld_nme%>" id="<%=fld_typ%>" value="" onclick="<%=val_cond%>"/><%=fld_ttl%>&nbsp;
    <%
    }
    }%> 
  <!--<input type="checkbox" name="CTS_dis" id="CTS_dis" value="" onclick="displayReports('data','CTS_dis','CTS')"/>CTS&nbsp;
   <input type="checkbox" name="AVG_dis" id="AVG_dis" value="" onclick="displayReports('data','AVG_dis','AVG')"/>AVG&nbsp;
   <input type="checkbox" name="RAP_dis" id="RAP_dis" value="" onclick="displayReports('data','RAP_dis','RAP')"/>RAP&nbsp;-->
  </td>
  </tr>
   <tr>
   <td valign="top" class="hedPg">
   <table class="grid1" id="data" >
   <tr>
   <th><%=hdr%></th>
   <%for(int i=0;i<colList.size();i++){%>
   <th colspan="5" align="center"><%=colList.get(i)%></th>
   <%}%>
   <th colspan="5" align="center">Total</th>
   </tr>
   <tr>
   <td></td>
   <%for(int i=0;i<colList.size();i++){%>
   <td align="center"><span id="QTY_<%=i%>" style="<%=stylesingle%>">QTY</span></td>
   <td align="center"><span id="CTS_<%=i%>" style="<%=stylemix%>">CTS</span></td>
   <td align="center"><span id="AVG_<%=i%>" style="<%=stylemix%>">AVG</span></td>
   <td align="center"><span id="RAP_<%=i%>" style="display:none;">RAP</span></td>
   <td align="center"><span id="VLU_<%=i%>" style="display:none;">VLU</span></td>
   <%}%>
   <td align="center"><span id="TOTALQTY" style="<%=stylesingle%>">QTY</span></td>
   <td align="center"><span id="TOTALCTS" style="<%=stylemix%>">CTS</span></td>
   <td align="center"><span id="TOTALAVG" style="<%=stylemix%>">AVG</span></td>
   <td align="center"><span id="TOTALRAP" style="display:none;">RAP</span></td>
   <td align="center"><span id="TOTALVLU" style="display:none;">VLU</span></td>
   </tr>
   <%for(int i=0;i<rowList.size();i++){
   String row=util.nvl((String)rowList.get(i));%>
   <tr>
   <th align="center"><%=row%></th>
   <%
   for(int j=0;j<colList.size();j++){
   String col=util.nvl((String)colList.get(j));
   %>
   <td align="right">
   <span id="QTY_<%=i%>_<%=j%>" style="<%=stylesingle%>"><%=util.nvl((String)dataDtl.get(row+"_"+col+"_QTY"))%></span></td>
   <td align="right">
   <span id="CTS_<%=i%>_<%=j%>" style="<%=stylemix%>"><%=util.nvl((String)dataDtl.get(row+"_"+col+"_CTS"))%></span></td>
   <td align="right">
   <span id="AVG_<%=i%>_<%=j%>" style="<%=stylemix%>"><%=util.nvl((String)dataDtl.get(row+"_"+col+"_AVG"))%></span></td>
   <td align="right">
   <span id="RAP_<%=i%>_<%=j%>" style="display:none;"><%=util.nvl((String)dataDtl.get(row+"_"+col+"_RAP"))%></span></td>
   <td align="right">
   <span id="VLU_<%=i%>_<%=j%>" style="display:none;"><%=util.nvl((String)dataDtl.get(row+"_"+col+"_VLU"))%></span></td>      
   <%}%>
   <td align="right">
   <span id="TOTALQTY_<%=i%>" style="<%=stylesingle%>"><%=util.nvl((String)dataDtl.get(row+"_QTY"))%></span></td>
   <td align="right">
   <span id="TOTALCTS_<%=i%>" style="<%=stylemix%>"><%=util.nvl((String)dataDtl.get(row+"_CTS"))%></span></td>
   <td align="right">
   <span id="TOTALAVG_<%=i%>" style="<%=stylemix%>"><%=util.nvl((String)dataDtl.get(row+"_AVG"))%></span></td>
   <td align="right">
   <span id="TOTALRAP_<%=i%>" style="display:none;"><%=util.nvl((String)dataDtl.get(row+"_RAP"))%></span></td>
   <td align="right">
   <span id="TOTALVLU_<%=i%>" style="display:none;"><%=util.nvl((String)dataDtl.get(row+"_VLU"))%></span></td>      
   </tr>
   <%}%>
   <tr>
   <th>Total</th>
   <%for(int j=0;j<colList.size();j++){
   String col=util.nvl((String)colList.get(j));%>
   <td align="right">
   <span id="TTLQTY_<%=j%>"  style="<%=stylesingle%>" ><%=util.nvl((String)dataDtl.get(col+"_QTY"))%></span></td>
   <td align="right">
   <span id="TTLCTS_<%=j%>" style="<%=stylemix%>"><%=util.nvl((String)dataDtl.get(col+"_CTS"))%></span>
   </td>
   <td align="right">
   <span id="TTLAVG_<%=j%>" style="<%=stylemix%>"><%=util.nvl((String)dataDtl.get(col+"_AVG"))%></span></td>
   <td align="right">
   <span id="TTLRAP_<%=j%>" style="display:none;"><%=util.nvl((String)dataDtl.get(col+"_RAP"))%></span></td>
   <td align="right">
   <span id="TTLVLU_<%=j%>" style="display:none;"><%=util.nvl((String)dataDtl.get(col+"_VLU"))%></span></td>
   <%}%>
   <td align="right">
   <span id="GTTLQTY"   style="<%=stylesingle%>">
   <%=util.nvl((String)dataDtl.get("TTL_QTY"))%></span></td>
   <td align="right">
   <span id="GTTLCTS" style="<%=stylemix%>"><%=util.nvl((String)dataDtl.get("TTL_CTS"))%></span></td>
   <td align="right">
   <span id="GTTLAVG" style="<%=stylemix%>"><%=util.nvl((String)dataDtl.get("TTL_AVG"))%></span></td>
   <td align="right">
   <span id="GTTLRAP" style="display:none;"><%=util.nvl((String)dataDtl.get("TTL_RAP"))%></span></td>
   <td align="right">
   <span id="GTTLVLU" style="display:none;"><%=util.nvl((String)dataDtl.get("TTL_VLU"))%></span></td>
   </tr>
   </table>
   </td>
   </tr>
<%}else{%>
<tr><td valign="top" class="hedPg">
Sorry No Result Found</td></tr>
<%}}%>

  </table>
    </body>
</html>