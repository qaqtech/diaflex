<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page errorPage="../error_page.jsp" %>
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.ResultSet, java.util.ArrayList,java.util.Collections,java.util.ArrayList,java.util.HashMap,java.text.SimpleDateFormat, java.util.Calendar,java.math.BigDecimal, java.util.Collections,java.math.RoundingMode;"%>
<jsp:useBean id="info" class="ft.com.InfoMgr" scope="session" />
<jsp:useBean id="util" class="ft.com.JspUtil" scope="page" />
<%@taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>
<%@taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<html> 
   <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/bse.js?v=<%=info.getJsVersion()%> " > </script>
<link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/bse.css?v=<%=info.getJsVersion()%>"/>
<link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/general.css?v=<%=info.getJsVersion()%>"/>
<script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/ajax.js?v=<%=info.getJsVersion()%> " > </script>
  <script type="text/javascript" src="<%=info.getReqUrl()%>/PopScripts/popup.js?v=<%=info.getJsVersion()%> " > </script>
    <script src="<%=info.getReqUrl()%>/PopScripts/jquery-1.2.6.min.js" type="text/javascript"></script>
   <script language="javaScript" type="text/javascript" src="<%=info.getReqUrl()%>/calendar/calendar.js"></script>
   <link href="<%=info.getReqUrl()%>/calendar/calendar.css" rel="stylesheet" type="text/css" />
  <script src="<%=info.getReqUrl()%>/jqueryScript/jqueryScript.js?v=<%=info.getJsVersion()%>" type="text/javascript"></script>
 
      <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/style.css?v=<%=info.getJsVersion()%>"/>

  <title>Ifrs Report</title>
 
  </head>

        <%
    HashMap allPageDtl= (info.getPageDetails() == null)?new HashMap():(HashMap)info.getPageDetails();
    HashMap pageDtl=(HashMap)allPageDtl.get("IFRS");
    ArrayList pageList=new ArrayList();
    HashMap pageDtlMap=new HashMap();
    String fld_nme="",fld_ttl="",val_cond="",lov_qry="",dflt_val="",fld_typ="",form_nme="",flg1="";
     ArrayList shlistMix = (request.getAttribute("shlistMix") == null)?new ArrayList():(ArrayList)request.getAttribute("shlistMix");
     ArrayList shlistSingle = (request.getAttribute("shlistSingle") == null)?new ArrayList():(ArrayList)request.getAttribute("shlistSingle");
     HashMap dataTbl= (request.getAttribute("dataDtl") == null)?new HashMap():(HashMap)request.getAttribute("dataDtl");
     HashMap bucketLmtDsc= (session.getAttribute("bucketLmtDsc") == null)?new HashMap():(HashMap)session.getAttribute("bucketLmtDsc");
     ArrayList vwPrpLst = (session.getAttribute("IFRS_OPEN_CLOSE") == null)?new ArrayList():(ArrayList)session.getAttribute("IFRS_OPEN_CLOSE");
    String view=util.nvl((String)request.getAttribute("view"));
    String gridby=util.nvl((String)session.getAttribute("gridby"),"SH");
    String bucketby=util.nvl((String)session.getAttribute("bucketby"),"AUTO");
    HashMap dbmsInfo = info.getDmbsInfoLst();
    HashMap data=new HashMap();
    HashMap prp = info.getPrp();
    HashMap mprp = info.getMprp();
    String dta_typ=(String)mprp.get(gridby+"T");
    ArrayList prpValList = new ArrayList();
    ArrayList prpPrtList = new ArrayList();
    if(dta_typ.equals("C")){
    prpValList = (ArrayList)prp.get(gridby+"V");
    prpPrtList = (ArrayList)prp.get(gridby+"P");
    }else if(dta_typ.equals("T")){
        for(int j=0;j<shlistSingle.size();j++){
        if(!prpValList.contains(shlistSingle.get(j)))
        prpValList.add(shlistSingle.get(j));
        }
        for(int j=0;j<shlistMix.size();j++){
        if(!prpValList.contains(shlistMix.get(j)))
        prpValList.add(shlistMix.get(j));
        }
        Collections.sort(prpValList);
        prpPrtList.addAll(prpValList);
    }
    int prpValListsz=prpValList.size();
        ArrayList sttlist=new  ArrayList();
        HashMap sttDsc=new  HashMap();
        
        sttlist.add("OPEN");
        sttlist.add("MIXIN");
        sttlist.add("MFGNEW");
        sttlist.add("PURNEW");
        sttlist.add("SININ");
        sttlist.add("RDMIXIN");
        sttlist.add("SINOUT");
        sttlist.add("MIXOUT");
        if(gridby.equals("IFRS_BUCKET")){
        sttlist.add("BUCKETIN");
        sttlist.add("MIXBUCKETIN");
        }
        sttlist.add("SOLD");
        if(gridby.equals("IFRS_BUCKET")){
        sttlist.add("BUCKETOUT");
        sttlist.add("MIXBUCKETOUT");
        }
        sttlist.add("CLOSE");
        sttDsc.put("OPEN", "Opening");
        sttDsc.put("MFGNEW", "Mfg New");
        sttDsc.put("PURNEW", "Purchase New");
        sttDsc.put("RDMIXIN", "Round Mix");
        sttDsc.put("MIXOUT", "Mix Out");
        sttDsc.put("MIXIN", "Mix In");
        sttDsc.put("SINOUT", "Single Out");
        sttDsc.put("SININ", "Single In");
        sttDsc.put("BUCKETIN", "Bucket In");
        sttDsc.put("MIXBUCKETIN", "M In");
        sttDsc.put("SOLD", "Sold");
        sttDsc.put("BUCKETOUT", "Bucket Out");
        sttDsc.put("MIXBUCKETOUT", "M Out");
        sttDsc.put("CLOSE", "Balance");
        String logId=String.valueOf(info.getLogId());
        String onfocus="cook('"+logId+"')";
        %>
 <body onfocus="<%=onfocus%>" onload="MM_preloadImages('images/faunalogo.png')" topmargin="0" leftmargin="0" rightmargin="0" bottommargin="0">
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
    <td height="5" valign="top" class="greyline"><img src="../images/grey_linebig.png" width="685" height="5" align="right" /></td>
  </tr>
   <tr>
  <td valign="top" class="hedPg">
  <table cellpadding="1" cellspacing="5"><tr><td valign="middle">
  <img src="../images/line.jpg" border="0" /></td><td valign="middle"> <span class="pgHed">Ifrs Report</span> </td>
</tr></table>
  </td>
  </tr>

<tr><td valign="top" class="hedPg">
<html:form action="/report/ifrsactionkg.do?method=fetch"  method="POST">
  <table  class="grid1">
   <tr>
   <th colspan="2" align="center">Search</th>
   </tr>
       <tr><td>Date : </td>  
       <td><html:text property="value(frmDte)" styleId="frmDte" name="orclReportForm" size="10" /><img src="<%=info.getReqUrl()%>/images/calender.png" onClick="setYears(2011, <%=info.getCurrentYear()%>);showCalender(this, 'frmDte');"> 
       To&nbsp; <html:text property="value(toDte)" styleId="toDte" name="orclReportForm"  size="10"/> <img src="<%=info.getReqUrl()%>/images/calender.png" onClick="setYears(2011, <%=info.getCurrentYear()%>);showCalender(this, 'toDte');"></td>
      </tr>
<tr>
<td>
Grid
</td>
<td>
      <html:select  property="value(gridby)" name="orclReportForm" styleId="gridby" style="width:100px"   > 
      <%for(int j=0;j<vwPrpLst.size();j++){
      String lprp=util.nvl((String)vwPrpLst.get(j));
      String lprpTyp = util.nvl((String)mprp.get(lprp+"T"));
      if(lprpTyp.equals("C") || lprpTyp.equals("T") ){
      String prpDsc = util.nvl((String)mprp.get(lprp+"D"));
      %>
      <html:option value="<%=lprp%>" ><%=prpDsc%></html:option> 
    <%}}%>
    </html:select>
</td>
</tr>
      <%
      pageList=(ArrayList)pageDtl.get("BUCKET");
      if(pageList!=null && pageList.size() >0){%>
      <tr>
      <td>Bucket </td>
      <td>
      <html:select  property="value(bucketby)" name="orclReportForm" styleId="bucketby" style="width:100px"   > 
      <%for(int j=0;j<pageList.size();j++){
      pageDtlMap=(HashMap)pageList.get(j);
      fld_ttl=(String)pageDtlMap.get("fld_ttl");
      fld_nme=(String)pageDtlMap.get("fld_nme"); %>
      <html:option value="<%=fld_nme%>" ><%=fld_ttl%></html:option> 
      <%}%>
      </html:select> 
      </td>
      </tr>
    <%}%>
   <tr>
   <td colspan="2" align="center"><html:submit property="value(srch)" value="Fetch" styleClass="submit" />
   </td>
   </tr>
   </table>
</html:form>
</td>
</tr>  
<%if(view.equals("Y")){%>
<tr>
<td valign="top" class="hedPg">
<table>
<tr>
<html:form action="/report/ifrsactionkg.do?method=fetch"  method="POST">
<td>
Grid
</td>
<td>
      <html:select  property="value(gridbyExisting)" name="orclReportForm" styleId="gridbyExisting" style="width:100px"   > 
      <%for(int j=0;j<vwPrpLst.size();j++){
      String lprp=util.nvl((String)vwPrpLst.get(j));
      String lprpTyp = util.nvl((String)mprp.get(lprp+"T"));
      if(lprpTyp.equals("C") || lprpTyp.equals("T") ){
      String prpDsc = util.nvl((String)mprp.get(lprp+"D"));
      %>
      <html:option value="<%=lprp%>" ><%=prpDsc%></html:option> 
    <%}}%>
    </html:select>
</td>
<td align="center"><html:submit property="value(ReGenerate)" value="ReGenerate" styleClass="submit" /></td>
</html:form>
</tr>
</table>
</td>
</tr>
  <tr>
  <td class="hedPg">
  <table class="grid1">
  <tr>
  <td align="center"><b>Property/Status</b></td>
  <%for(int i=0;i<sttlist.size();i++){
  String stt=util.nvl((String)sttlist.get(i));
  if(!stt.equals("MIXIN") && !stt.equals("RDMIXIN")  && !stt.equals("SINOUT") && !stt.equals("MIXBUCKETIN")  && !stt.equals("MIXBUCKETOUT")){%>
  <th align="center" colspan="9"><%=util.nvl((String)sttDsc.get((String)sttlist.get(i)))%></th>
  <%}}%>
  </tr>
  <tr>
  <td></td>
  <%for(int i=0;i<sttlist.size();i++){
  String stt=util.nvl((String)sttlist.get(i));
  if(!stt.equals("MIXIN") && !stt.equals("RDMIXIN")  && !stt.equals("SINOUT") && !stt.equals("MIXBUCKETIN")  && !stt.equals("MIXBUCKETOUT")){%>
  <td align="center">QTY</td>
  <td align="center">CTS</td>
  <td align="center" nowrap="nowrap">MFG CTS</td>
  <td align="center" nowrap="nowrap">Rs AVG</td>
  <td align="center" nowrap="nowrap">Rs VLU</td>
  <td align="center" nowrap="nowrap">$ AVG</td>
  <td align="center" nowrap="nowrap">$ VLU</td>
  <td align="center" nowrap="nowrap">Rs NRV</td>
  <td align="center" nowrap="nowrap" class="rghtbrdrend">$ NRV</td>
  <%}}%>
  </tr>
 <%for(int i=0;i<prpValListsz;i++){ 
  String sh=util.nvl((String)prpValList.get(i));
  if(shlistSingle.contains(sh)){
  String prt=util.nvl((String)prpPrtList.get(i));
  %>
  <tr>
  <th><%=prt%><%=util.nvl((String)bucketLmtDsc.get(sh+"_"+bucketby))%></th>
  <%
  for(int j=0;j<sttlist.size();j++){
  String stt=util.nvl((String)sttlist.get(j));
  if(!stt.equals("MIXIN") && !stt.equals("RDMIXIN")  && !stt.equals("SINOUT") && !stt.equals("MIXBUCKETIN")  && !stt.equals("MIXBUCKETOUT")){%>
  <td align="right"><%=util.nvl((String)dataTbl.get(sh+"_"+stt+"_QTY_NR"),"0")%></td>
  <td align="right">
  <a title="Packet Details" target="_blank"  href="ifrsactionkg.do?method=pktDtlStockOpenClose&stt=<%=stt%>&pkt_ty=NR&lprpval=<%=sh%>&lprp=<%=gridby%>" >
  <%=util.nvl((String)dataTbl.get(sh+"_"+stt+"_CTS_NR"),"0")%>
  </a>
  </td>
  <td align="right"><%=util.nvl((String)dataTbl.get(sh+"_"+stt+"_MFGCTS_NR"),"0")%></td>
  <td align="right"><%=util.nvl((String)dataTbl.get(sh+"_"+stt+"_AVGRS_NR"),"0")%></td>
  <td align="right"><%=util.nvl((String)dataTbl.get(sh+"_"+stt+"_VLURS_NR"),"0")%></td>
  <td align="right"><%=util.nvl((String)dataTbl.get(sh+"_"+stt+"_AVGUSD_NR"),"0")%></td>
  <td align="right"><%=util.nvl((String)dataTbl.get(sh+"_"+stt+"_VLUUSD_NR"),"0")%></td>
    <td align="right"><%=util.nvl((String)dataTbl.get(sh+"_"+stt+"_NRVVLURS_NR"),"0")%></td>
    <td align="right" class="rghtbrdrend"><%=util.nvl((String)dataTbl.get(sh+"_"+stt+"_NRVVLUUSD_NR"),"0")%></td>
  <%}}%>
  </tr>
  <%}}%>
  <tr>
  <td align="center" nowrap="nowrap"><b>Total</b></td>
  <%
  for(int j=0;j<sttlist.size();j++){
  String stt=util.nvl((String)sttlist.get(j));
  if(!stt.equals("MIXIN") && !stt.equals("RDMIXIN")  && !stt.equals("SINOUT") && !stt.equals("MIXBUCKETIN")  && !stt.equals("MIXBUCKETOUT")){%>
  <td align="right"><b><%=util.nvl((String)dataTbl.get(stt+"_QTY_NR"),"0")%></b></td>
  <td align="right"><b>
  <a title="Packet Details" target="_blank"  href="ifrsactionkg.do?method=pktDtlStockOpenClose&stt=<%=stt%>&pkt_ty=NR" >
  <%=util.nvl((String)dataTbl.get(stt+"_CTS_NR"),"0")%>
  </a>
  </b></td>
  <td align="right"><b><%=util.nvl((String)dataTbl.get(stt+"_MFGCTS_NR"),"0")%></b></td>
  <td align="right"><b><%=util.nvl((String)dataTbl.get(stt+"_AVGRS_NR"),"0")%></b></td>
  <td align="right"><b><%=util.nvl((String)dataTbl.get(stt+"_VLURS_NR"),"0")%></b></td>
  <td align="right"><b><%=util.nvl((String)dataTbl.get(stt+"_AVGUSD_NR"),"0")%></b></td>
  <td align="right"><b><%=util.nvl((String)dataTbl.get(stt+"_VLUUSD_NR"),"0")%></b></td>
  <td align="right"><b><%=util.nvl((String)dataTbl.get(stt+"_NRVVLURS_NR"),"0")%></b></td>
  <td align="right" class="rghtbrdrend"><b><%=util.nvl((String)dataTbl.get(stt+"_NRVVLUUSD_NR"),"0")%></b></td>
  <%}}%>
  </tr>
  </table>
  </td>
  </tr>
  
  <tr>
  <td class="hedPg">
  <table class="grid1">
  <tr>
  <td align="center"><b>Property/Status</b></td>
  <%
  for(int i=0;i<sttlist.size();i++){
  String stt=util.nvl((String)sttlist.get(i));
  if(!stt.equals("MIXOUT") && !stt.equals("MFGNEW")  && !stt.equals("SININ") && !stt.equals("BUCKETIN") && !stt.equals("BUCKETOUT")){%>
  <th align="center" colspan="7"><%=util.nvl((String)sttDsc.get((String)sttlist.get(i)))%></th>
  <%}}%>
  </tr>
  <tr>
  <td></td>
  <%for(int i=0;i<sttlist.size();i++){
  String stt=util.nvl((String)sttlist.get(i));
  if(!stt.equals("MIXOUT") && !stt.equals("MFGNEW")  && !stt.equals("SININ") && !stt.equals("BUCKETIN") && !stt.equals("BUCKETOUT")){%>
  <td align="center">CTS</td>
  <td align="center" nowrap="nowrap">Rs AVG</td>
  <td align="center" nowrap="nowrap">Rs VLU</td>
  <td align="center" nowrap="nowrap">$ AVG</td>
  <td align="center" nowrap="nowrap">$ VLU</td>
  <td align="center" nowrap="nowrap">Rs NRV</td>
  <td align="center" nowrap="nowrap" class="rghtbrdrend">$ NRV</td>
  <%}}%>
  </tr>
 <%for(int i=0;i<prpValListsz;i++){ 
  String sh=util.nvl((String)prpValList.get(i));
  if(shlistMix.contains(sh)){
  String prt=util.nvl((String)prpPrtList.get(i));%>
  <tr>
  <th><%=prt%><%=util.nvl((String)bucketLmtDsc.get(sh+"_"+bucketby))%></th>
  <%
  BigDecimal closeTtlCtsBig = new BigDecimal(0);
  BigDecimal closeTtlVluRsBig = new BigDecimal(0);
  BigDecimal closeTtlVluUsdBig = new BigDecimal(0);
  BigDecimal closeTtlNrvVluRsBig = new BigDecimal(0);
  BigDecimal closeTtlNrvVluUsdBig = new BigDecimal(0);
  for(int j=0;j<sttlist.size();j++){
  String stt=util.nvl((String)sttlist.get(j));
  if(!stt.equals("MIXOUT") && !stt.equals("MFGNEW") && !stt.equals("CLOSE")  && !stt.equals("SININ") && !stt.equals("BUCKETIN") && !stt.equals("BUCKETOUT")){
  BigDecimal ctsBig =new BigDecimal(util.nvl((String)dataTbl.get(sh+"_"+stt+"_CTS_MIX"),"0").trim());
  BigDecimal vluRsBig =new BigDecimal(util.nvl((String)dataTbl.get(sh+"_"+stt+"_VLURS_MIX"),"0").trim());
  BigDecimal vluUsdBig =new BigDecimal(util.nvl((String)dataTbl.get(sh+"_"+stt+"_VLUUSD_MIX"),"0").trim());
  BigDecimal nrvvluRsBig =new BigDecimal(util.nvl((String)dataTbl.get(sh+"_"+stt+"_NRVVLURS_MIX"),"0").trim());
  BigDecimal nrvvluUsdBig =new BigDecimal(util.nvl((String)dataTbl.get(sh+"_"+stt+"_NRVVLUUSD_MIX"),"0").trim());
  if(!stt.equals("SOLD") && !stt.equals("SINOUT") && !stt.equals("MIXBUCKETOUT")){
  closeTtlCtsBig=closeTtlCtsBig.add(ctsBig);
  closeTtlVluRsBig=closeTtlVluRsBig.add(vluRsBig);
  closeTtlVluUsdBig=closeTtlVluUsdBig.add(vluUsdBig);
  closeTtlNrvVluRsBig=closeTtlNrvVluRsBig.add(nrvvluRsBig);
  closeTtlNrvVluUsdBig=closeTtlNrvVluUsdBig.add(nrvvluUsdBig);
  }else{
  closeTtlCtsBig=closeTtlCtsBig.subtract(ctsBig);
  closeTtlVluRsBig=closeTtlVluRsBig.subtract(vluRsBig);
  closeTtlVluUsdBig=closeTtlVluUsdBig.subtract(vluUsdBig);
  closeTtlNrvVluRsBig=closeTtlNrvVluRsBig.subtract(nrvvluRsBig);
  closeTtlNrvVluUsdBig=closeTtlNrvVluUsdBig.subtract(nrvvluUsdBig);
  }
  %>
  <td align="right">
  <a title="Packet Details" target="_blank"  href="ifrsactionkg.do?method=pktDtlStockOpenClose&stt=<%=stt%>&pkt_ty=MIX&lprpval=<%=sh%>&lprp=<%=gridby%>" >
  <%=util.nvl((String)dataTbl.get(sh+"_"+stt+"_CTS_MIX"),"0")%>
  </a>
  </td>
  <td align="right"><%=util.nvl((String)dataTbl.get(sh+"_"+stt+"_AVGRS_MIX"),"0")%></td>
  <td align="right"><%=util.nvl((String)dataTbl.get(sh+"_"+stt+"_VLURS_MIX"),"0")%></td>
  <td align="right"><%=util.nvl((String)dataTbl.get(sh+"_"+stt+"_AVGUSD_MIX"),"0")%></td>
  <td align="right"><%=util.nvl((String)dataTbl.get(sh+"_"+stt+"_VLUUSD_MIX"),"0")%></td>
  <td align="right"><%=util.nvl((String)dataTbl.get(sh+"_"+stt+"_NRVVLURS_MIX"),"0")%></td>
  <td align="right" class="rghtbrdrend"><%=util.nvl((String)dataTbl.get(sh+"_"+stt+"_NRVVLUUSD_MIX"),"0")%></td>
  <%}else if (stt.equals("CLOSE")){
  BigDecimal closeTtlAvgRsBig = new BigDecimal(0);
  BigDecimal closeTtlAvgUsdBig = new BigDecimal(0);
  if(closeTtlCtsBig.equals(BigDecimal.ZERO) || closeTtlVluRsBig.equals(BigDecimal.ZERO))
  closeTtlAvgRsBig = new BigDecimal(0);
  else
  closeTtlAvgRsBig =closeTtlVluRsBig.divide(closeTtlCtsBig,0,RoundingMode.HALF_EVEN);
  
  if(closeTtlCtsBig.equals(BigDecimal.ZERO) || closeTtlVluUsdBig.equals(BigDecimal.ZERO))
  closeTtlAvgUsdBig = new BigDecimal(0);
  else
  closeTtlAvgUsdBig =closeTtlVluUsdBig.divide(closeTtlCtsBig,0,RoundingMode.HALF_EVEN);
  %>
  <td align="right"><%=util.nvl((String)closeTtlCtsBig.toString(),"0")%></td>
  <td align="right"><%=(String)closeTtlAvgRsBig.toString()%></td>
  <td align="right"><%=util.nvl((String)closeTtlVluRsBig.toString(),"0")%></td>
  <td align="right"><%=(String)closeTtlAvgUsdBig.toString()%></td>
  <td align="right"><%=util.nvl((String)closeTtlVluUsdBig.toString(),"0")%></td>
  <td align="right"><%=util.nvl((String)closeTtlNrvVluRsBig.toString(),"0")%></td>
  <td align="right" class="rghtbrdrend"><%=util.nvl((String)closeTtlNrvVluUsdBig.toString(),"0")%></td>
  <%}}%>
  </tr>
  <%}}%>
  <tr>
  <td align="center" nowrap="nowrap"><b>Total</b></td>
  <%
  BigDecimal closeTtlCtsBig = new BigDecimal(0);
  BigDecimal closeTtlVluRsBig = new BigDecimal(0);
  BigDecimal closeTtlVluUsdBig = new BigDecimal(0);
  BigDecimal closeTtlNrvVluRsBig = new BigDecimal(0);
  BigDecimal closeTtlNrvVluUsdBig = new BigDecimal(0);
  for(int j=0;j<sttlist.size();j++){
  String stt=util.nvl((String)sttlist.get(j));
  if(!stt.equals("MIXOUT") && !stt.equals("MFGNEW") && !stt.equals("CLOSE")  && !stt.equals("SININ") && !stt.equals("BUCKETIN") && !stt.equals("BUCKETOUT")){
  BigDecimal ctsBig =new BigDecimal(util.nvl((String)dataTbl.get(stt+"_CTS_MIX"),"0").trim());
  BigDecimal vluRsBig =new BigDecimal(util.nvl((String)dataTbl.get(stt+"_VLURS_MIX"),"0").trim());
  BigDecimal vluUsdBig =new BigDecimal(util.nvl((String)dataTbl.get(stt+"_VLUUSD_MIX"),"0").trim());
  BigDecimal nrvvluRsBig =new BigDecimal(util.nvl((String)dataTbl.get(stt+"_NRVVLURS_MIX"),"0").trim());
  BigDecimal nrvvluUsdBig =new BigDecimal(util.nvl((String)dataTbl.get(stt+"_NRVVLUUSD_MIX"),"0").trim());
  if(!stt.equals("SOLD") && !stt.equals("SINOUT") && !stt.equals("MIXBUCKETOUT")){
  closeTtlCtsBig=closeTtlCtsBig.add(ctsBig);
  closeTtlVluRsBig=closeTtlVluRsBig.add(vluRsBig);
  closeTtlVluUsdBig=closeTtlVluUsdBig.add(vluUsdBig);
  closeTtlNrvVluRsBig=closeTtlNrvVluRsBig.add(nrvvluRsBig);
  closeTtlNrvVluUsdBig=closeTtlNrvVluUsdBig.add(nrvvluUsdBig);
  }else{
  closeTtlCtsBig=closeTtlCtsBig.subtract(ctsBig);
  closeTtlVluRsBig=closeTtlVluRsBig.subtract(vluRsBig);
  closeTtlVluUsdBig=closeTtlVluUsdBig.subtract(vluUsdBig);
  closeTtlNrvVluRsBig=closeTtlNrvVluRsBig.subtract(nrvvluRsBig);
  closeTtlNrvVluUsdBig=closeTtlNrvVluUsdBig.subtract(nrvvluUsdBig);
  }%>
  <td align="right"><b>
  <a title="Packet Details" target="_blank"  href="ifrsactionkg.do?method=pktDtlStockOpenClose&stt=<%=stt%>&pkt_ty=MIX" >
  <%=util.nvl((String)dataTbl.get(stt+"_CTS_MIX"),"0")%>
  </a>
  </b></td>
  <td align="right"><b><%=util.nvl((String)dataTbl.get(stt+"_AVGRS_MIX"),"0")%></b></td>
  <td align="right"><b><%=util.nvl((String)dataTbl.get(stt+"_VLURS_MIX"),"0")%></b></td>
  <td align="right"><b><%=util.nvl((String)dataTbl.get(stt+"_AVGUSD_MIX"),"0")%></b></td>
  <td align="right"><b><%=util.nvl((String)dataTbl.get(stt+"_VLUUSD_MIX"),"0")%></b></td>
  <td align="right"><b><%=util.nvl((String)dataTbl.get(stt+"_NRVVLURS_MIX"),"0")%></b></td>
  <td align="right" class="rghtbrdrend"><b><%=util.nvl((String)dataTbl.get(stt+"_NRVVLUUSD_MIX"),"0")%></b></td>
  <%}else if (stt.equals("CLOSE")){
  BigDecimal closeTtlAvgRsBig = new BigDecimal(0);
  BigDecimal closeTtlAvgUsdBig = new BigDecimal(0);
  if(closeTtlCtsBig.equals(BigDecimal.ZERO) || closeTtlVluRsBig.equals(BigDecimal.ZERO))
  closeTtlAvgRsBig = new BigDecimal(0);
  else
  closeTtlAvgRsBig =closeTtlVluRsBig.divide(closeTtlCtsBig,0,RoundingMode.HALF_EVEN);
  
  if(closeTtlCtsBig.equals(BigDecimal.ZERO) || closeTtlVluUsdBig.equals(BigDecimal.ZERO))
  closeTtlAvgUsdBig = new BigDecimal(0);
  else
  closeTtlAvgUsdBig =closeTtlVluUsdBig.divide(closeTtlCtsBig,0,RoundingMode.HALF_EVEN);
  %>
  <td align="right"><b><%=util.nvl((String)closeTtlCtsBig.toString(),"0")%></b></td>
  <td align="right"><b><%=(String)closeTtlAvgRsBig.toString()%></b></td>
  <td align="right"><b><%=util.nvl((String)closeTtlVluRsBig.toString(),"0")%></b></td>
  <td align="right"><b><%=(String)closeTtlAvgUsdBig.toString()%></b></td>
  <td align="right"><b><%=util.nvl((String)closeTtlVluUsdBig.toString(),"0")%></b></td>
  <td align="right"><b><%=util.nvl((String)closeTtlNrvVluRsBig.toString(),"0")%></b></td>
  <td align="right" class="rghtbrdrend"><b><%=util.nvl((String)closeTtlNrvVluUsdBig.toString(),"0")%></b></td>
  <%}}%>
  </tr>
  </table>
  </td>
  </tr>
  <%}%>
  <tr><td>
<jsp:include page="/footer.jsp" />
</td></tr>
  </table>  
       <%@include file="../calendar.jsp"%>
  </body>
</html>