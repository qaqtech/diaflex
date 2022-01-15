<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page errorPage="../error_page.jsp" %>
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import="ft.com.*,java.util.ArrayList,java.util.ArrayList,java.util.HashMap, java.sql.*, java.util.Enumeration, java.util.logging.Level"%>

<jsp:useBean id="info" class="ft.com.InfoMgr" scope="session" />
<jsp:useBean id="util" class="ft.com.JspUtil" scope="page" />
<%@taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>

<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>Generic Report View</title>
    <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/bse.css?v=<%=info.getJsVersion()%>"/>
    <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/style.css?v=<%=info.getJsVersion()%>"/>
    <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/bse.js?v=<%=info.getJsVersion()%> " > </script>
    <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/assortScript.js?v=<%=info.getJsVersion()%> " > </script>
    <script type="text/javascript" src="<%=info.getReqUrl()%>/PopScripts/popup.js?v=<%=info.getJsVersion()%> " > </script>
   <script src="<%=info.getReqUrl()%>/PopScripts/jquery-1.2.6.min.js" type="text/javascript"></script>
    <script type="text/javascript" src="<%=info.getReqUrl()%>/jqueryScript/jquery.js " > </script>
    <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/general.css?v=<%=info.getJsVersion()%>"/>
        <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/ajax.js?v=<%=info.getJsVersion()%> " > </script>
   
    <style type="text/css">
    table.grid1 td {
        padding:0px;
    }
    </style>
  </head>
  <%
       
  HashMap dbSysInfo=info.getDmbsInfoLst();   

  String docPath = (String)dbSysInfo.get("GRAPH_PATH");
  
  double MIN_INVENTORY_DAYS = Double.parseDouble((String)dbSysInfo.get("MIN_INVENTORY_DAYS"));
  double TOLERANCE_MAX_PCT = Double.parseDouble((String)dbSysInfo.get("TOLERANCE_MAX_PCT"));
  double TOLERANCE_MIN_PCT = Double.parseDouble((String)dbSysInfo.get("TOLERANCE_MIN_PCT"));
  double days = Double.parseDouble((String)session.getAttribute("days"));
  
  String docDwnPath = (String)dbSysInfo.get("GRAPH_DWN");
  ArrayList commkeyList = (ArrayList)session.getAttribute("commkeyList");
  HashMap dataDtl = (HashMap)session.getAttribute("dataDtl");
  HashMap getGrandTotalList = (HashMap)session.getAttribute("getGrandTotalList");
  ArrayList statusLst = (ArrayList)session.getAttribute("statusLst");
HashMap sttColorCodeMap =((HashMap)new JspUtil().getFromMem(info.getMem_ip(),info.getMem_port(),info.getCnt()+"_sttColorCodeMap") == null)?new HashMap():(HashMap)new JspUtil().getFromMem(info.getMem_ip(),info.getMem_port(),info.getCnt()+"_sttColorCodeMap"); 
   ArrayList rowListGt = (ArrayList)session.getAttribute("rowList");
   ArrayList colListGt  = (ArrayList)session.getAttribute("colList");
   String age = (String)request.getAttribute("Age");
   String hit = (String)request.getAttribute("Hit");
  HashMap dbinfo = info.getDmbsInfoLst();
  String sh = (String)dbinfo.get("SHAPE");
  String szval = (String)dbinfo.get("SIZE");
  String rowVal = (String)dbinfo.get("COL");
  String colVal = (String)dbinfo.get("CLR");
    HashMap gridstructure = (HashMap)session.getAttribute("gridstructure");
    ArrayList gridcommLst=new ArrayList();
    ArrayList gridrowLst=new ArrayList();
    ArrayList gridcolLst=new ArrayList();
    gridcommLst=(ArrayList)gridstructure.get("COMM");
    gridrowLst=(ArrayList)gridstructure.get("ROW");
    gridcolLst=(ArrayList)gridstructure.get("COL");
  HashMap mprp = info.getSrchMprp();
  HashMap prp = info.getSrchPrp();
  String checkClr="";
  String checkCol="";
  String checkrowVal="";
  String checkcolVal="",imageMap="";
  int loopclr=0;
  int loopcol=0;
  int tcount=0;
  String style="";
  String tdstyle="";
  String hasrc="";
  String imgid="";
  if(prp==null){
   DBUtil dbutil = new DBUtil();
      DBMgr db = new DBMgr();
      db.setCon(info.getCon());
      dbutil.setDb(db);
//  dbutil.initPrpSrch();
//  dbutil.initPrpSrch();
  prp = info.getSrchPrp();
  mprp = info.getSrchMprp();
  }
  ArrayList rowList = (ArrayList)prp.get(gridrowLst.get(0)+"V");
  ArrayList colList = (ArrayList)prp.get(gridcolLst.get(0)+"V");
  String GenStt=(String)session.getAttribute("GenStt");
  String processtm=util.nvl((String)request.getAttribute("processtm"));
  ArrayList srchids= (ArrayList)request.getAttribute("srchids");
  int sttLstSize=statusLst.size();
  int rownum=0;
  int colnum=0;
  int colnumstt=0;
  double perCentSold,perCentSoldQty,perCentMinTol,perCentMaxTol;
  HashMap allPageDtl= (info.getPageDetails() == null)?new HashMap():(HashMap)info.getPageDetails();
  HashMap pageDtl=(HashMap)allPageDtl.get("GENERIC_REPORT");
    ArrayList pageList=new ArrayList();
    HashMap pageDtlMap=new HashMap();
    String fld_nme="",fld_ttl="",val_cond="",dflt_val="",lov_qry="",fld_typ="",form_nme="",flg1="";
    String inventoryDis = util.nvl((String)sttColorCodeMap.get("INVDIS"));
    String isShowgraph = util.nvl((String)sttColorCodeMap.get("GRAPH"));
        String logId=String.valueOf(info.getLogId());
        String onfocus="cook('"+logId+"')";
        %>
 <body onfocus="<%=onfocus%>" onload="MM_preloadImages('images/faunalogo.png')" topmargin="0" leftmargin="0" rightmargin="0" bottommargin="0">
 <input type="hidden" id="reqUrl" name="reqUrl" value="<%=info.getReqUrl()%>"/>
<div id="backgroundPopup"></div>
<div id="popupContactASSFNL">
<table class="popUpTb">
 <tr><td height="5%">&nbsp;&nbsp;&nbsp;<html:button property="value(close)" styleClass="submit"  onclick="MKEmptyPopup('SC');disablePopupASSFNL();uncheckbox()" value="Close"  /> </td></tr>
<tr><td valign="top" height="95%">
<iframe name="SC" class="frameStyle" align="middle" frameborder="0">

</iframe></td></tr>
</table>
</div>
  <table width="100%"  border="0" align="center" cellpadding="0" cellspacing="0" >
 
  <tr>
    <td height="5" valign="top" class="greyline"><img src="../images/grey_linebig.png" width="685" height="5" align="right" /></td>
  </tr>
  <tr>
  <td valign="top" class="hedPg">
  <table cellpadding="1" cellspacing="5"><tr><td valign="middle">
  <img src="../images/line.jpg" border="0" /></td><td valign="middle"> 
  <span class="pgHed">Report View
  </span></td>
  </tr></table>
  </td></tr>
  <tr>
  <td valign="top" class="hedPg">
    <%  pageList=(ArrayList)pageDtl.get("CBUTTON");
            if(pageList!=null && pageList.size() >0){
            for(int j=0;j<pageList.size();j++){
            pageDtlMap=(HashMap)pageList.get(j);
            fld_nme="value("+(String)pageDtlMap.get("fld_nme")+")";
            fld_ttl=(String)pageDtlMap.get("fld_ttl");
            fld_typ=(String)pageDtlMap.get("fld_typ");
            lov_qry=(String)pageDtlMap.get("lov_qry");
            dflt_val=(String)pageDtlMap.get("dflt_val");
            val_cond=(String)pageDtlMap.get("val_cond"); 
            if(fld_typ.equals("S")){
            %>
    <html:submit property="<%=fld_nme%>" value="<%=fld_ttl%>" onclick="<%=val_cond%>" styleClass="submit"/>
    <%}else if(fld_typ.equals("B")){%>
    <html:button property="<%=fld_nme%>" value="<%=fld_ttl%>" onclick="<%=val_cond%>" styleClass="submit"/>
    <%}else if(fld_typ.equals("HB")){%>
    <button type="button" onclick="<%=val_cond%>" class="submit" accesskey="<%=lov_qry%>" id="<%=fld_nme%>" name="<%=fld_nme%>" ><%=fld_ttl%></button>   
    <%}}}
    pageList=(ArrayList)pageDtl.get("SHOWALL");
if(pageList!=null && pageList.size() >0){
%> Show All 
<%
for(int j=0;j<pageList.size();j++){
pageDtlMap=(HashMap)pageList.get(j);
fld_nme=(String)pageDtlMap.get("fld_nme");
fld_typ=(String)pageDtlMap.get("fld_typ");
fld_ttl=(String)pageDtlMap.get("fld_ttl");
val_cond=(String)pageDtlMap.get("val_cond");
val_cond=val_cond.replaceAll("STTLSTSIZE",""+sttLstSize+"");
%>
   <input type="checkbox" name="<%=fld_nme%>" id="<%=fld_typ%>" value="" onclick="<%=val_cond%>"/><%=fld_ttl%>&nbsp;
<%
}%>
<%}
   pageList=(ArrayList)pageDtl.get("COMMANXLS");
if(pageList!=null && pageList.size() >0){ %>
<span style="padding-left:10px;">Create Excel<a onclick="createXlComnAnal('All')"><img src="../images/ico_file_excel.png" border="0"/> </a></span> 
<%}%>
</td>
  </tr>
  <tr>
  <td valign="top" class="hedPg">

  <%
  // HashMap piePrpDtl=new HashMap();
  if(commkeyList!=null && commkeyList.size()>0){%>
  <table id="gridall">
  <%for(int i=0 ;i <commkeyList.size();i++ ){
  String key = (String)commkeyList.get(i);
  String keyLable = key;
  keyLable = (String)mprp.get(gridcommLst.get(0))+" : "+keyLable;
  
  for(int g=1 ;g <gridcommLst.size();g++ ){
  keyLable=keyLable.replaceFirst("_", " "+(String)mprp.get(gridcommLst.get(g))+"   :");
  }
  
  loopclr=0;
  loopcol=0;
  String replaceplus="";
  if(key.indexOf("+") > -1)
    replaceplus=key.replaceAll("\\+","~"); 
  else
  replaceplus=key;
  %>
  
  <tr>
  <td valign="middle"><%=keyLable%> <span style="padding-left:10px;"><a onclick="createPdfAnal('<%=i%>')"><img src="../images/PDFIconSmall.jpg" border="0"/> </a></span>
<%   pageList=(ArrayList)pageDtl.get("DATAWISEXLS");
if(pageList!=null && pageList.size() >0){ %>
 <span style="padding-left:10px;"><a onclick="createXlAnal('<%=i%>')"><img src="../images/ico_file_excel.png" border="0"/> </a></span> 
 <%}%>
  <button type="button" onclick="viewDetailGeneric('<%=key%>')" class="submit" >View Details</button>
  <!--<button type="button" onclick="viewDetailGenericGraph('<%=replaceplus%>','BAR','CLR');loadASSFNLPop('chart');"  class="submit" >Color Bar Graph</button>
  <button type="button" onclick="viewDetailGenericGraph('<%=replaceplus%>','BAR','COL');loadASSFNLPop('chart');"  class="submit" >Clarity Bar Graph</button>-->
    <%pageList=(ArrayList)pageDtl.get("BUTTON");
if(pageList!=null && pageList.size() >0){
for(int j=0;j<pageList.size();j++){
pageDtlMap=(HashMap)pageList.get(j);
fld_ttl=(String)pageDtlMap.get("fld_ttl");
val_cond=(String)pageDtlMap.get("val_cond");
val_cond=val_cond.replaceAll("KEY",replaceplus);
%>
  <button type="button" onclick="<%=val_cond%>"  class="submit" ><%=fld_ttl%></button>
<%}
}%>

  <%pageList=(ArrayList)pageDtl.get("SHOW");
if(pageList!=null && pageList.size() >0){
%> Show 
<%
for(int j=0;j<pageList.size();j++){
pageDtlMap=(HashMap)pageList.get(j);
fld_nme=(String)pageDtlMap.get("fld_nme");
fld_nme=fld_nme.replaceAll("LOOP",""+i+"");
fld_typ=(String)pageDtlMap.get("fld_typ");
fld_typ=fld_typ.replaceAll("LOOP",""+i+"");
fld_ttl=(String)pageDtlMap.get("fld_ttl");
val_cond=(String)pageDtlMap.get("val_cond");
val_cond=val_cond.replaceAll("KEY",key);
val_cond=val_cond.replaceAll("LOOP",""+i+"");
val_cond=val_cond.replaceAll("STTLSTSIZE",""+sttLstSize+"");
%>
   <input type="checkbox" name="<%=fld_nme%>" id="<%=fld_typ%>" value="" onclick="<%=val_cond%>"/><%=fld_ttl%>&nbsp;
<%
}
}%>
    <!--<input type="checkbox" name="AGE_dis_<%=i%>" id="AGE_dis_<%=i%>" value="" onclick="displayCTSAVG('<%=key%>','AGE_dis_<%=i%>','AGE_','<%=sttLstSize%>')"/>AGE&nbsp;
   <input type="checkbox" name="HIT_dis_<%=i%>" id="HIT_dis_<%=i%>" value="" onclick="displayCTSAVG('<%=key%>','HIT_dis_<%=i%>','HIT_','<%=sttLstSize%>')"/>HIT&nbsp;
   <!--<input type="checkbox" name="RAP_dis_<%=i%>" id="RAP_dis_<%=i%>" value="" onclick="displayCTSAVG('<%=key%>','RAP_dis_<%=i%>','RAP_','<%=sttLstSize%>')"/>RAP&nbsp;-->
<!--<A href="<%=info.getReqUrl()%>/report/pieChart.jsp?key=<%=replaceplus%>" target="SC"  onclick="loadASSFNLPop('chart')" title="Click Here To See Pie Chart">
<span class="txtLink" id="chart">Pie Chart</span></a>-->
  </td>
  
  </tr>
  <tr><td valign="top"><table class="grid1" id="<%=key%>">
  <tr><th></th>
  <%
  
  for(int j=0;j< colList.size();j++){
    String colV = (String)colList.get(j);
    if(colListGt.contains(colV)){
    checkClr=key+"_col_"+loopclr;
    loopclr++;
    %>
 <th colspan="<%=sttLstSize*6%>"><%=colV%><input type="checkbox" name="checkClr" id="<%=checkClr%>" value="<%=colV%>"/>
 <%if(isShowgraph.equals("Y")){%>
 <A href="<%=info.getReqUrl()%>/report/barGraph.jsp?key=<%=replaceplus%>&row=&col=<%=colV%>" target="SC"  onclick="loadASSFNLPop('chart')" title="Click Here To See Bar Graph">
<img src="../images/barchart.png" id="chart" border="0" />
</a>
<A href="<%=info.getReqUrl()%>/report/commonpieChart.jsp?key=<%=replaceplus%>&row=&col=<%=colV%>" target="SC"  onclick="loadASSFNLPop('chart')" title="Click Here To See Pie Chart">
<img src="../images/piechart.png" id="chart" border="0" />
</a><%}%>
 </th>
  <%}}%>
  <input type="hidden" name="loopclr" id="loopclr" value="<%=loopclr%>"/>
  <th  colspan="<%=sttLstSize*6%>">Total
   <%if(isShowgraph.equals("Y")){%>
  <A href="<%=info.getReqUrl()%>/report/barGraph.jsp?key=<%=replaceplus%>&row=&col=ALL" target="SC"  onclick="loadASSFNLPop('chart')" title="Click Here To See Bar Graph">
<img src="../images/barchart.png" id="chart" border="0" /></a>
<A href="<%=info.getReqUrl()%>/report/commonpieChart.jsp?key=<%=replaceplus%>&row=&col=ALL" target="SC"  onclick="loadASSFNLPop('chart')" title="Click Here To See Pie Chart">
<img src="../images/piechart.png" id="chart" border="0" /></a>
<%}%>
  </th>
  </tr>
  
  <tr>
  <td></td>
    <%for(int k=0;k< colList.size();k++){
     String colV = (String)colList.get(k);
     if(colListGt.contains(colV)){
     colnumstt++;
     for(int st=0;st<statusLst.size();st++){
  String stt=(String)statusLst.get(st);  
  String bocolor=(String)sttColorCodeMap.get(stt);
  style = "color: "+bocolor+";";
  %>
 <td align="center" colspan="6" style="<%=style%>"><%=statusLst.get(st)%></td>
  <%}}}
  colnumstt=0;
  for(int st=0;st<statusLst.size();st++){
  colnumstt++;
  String stt=(String)statusLst.get(st);  
  String bocolor=(String)sttColorCodeMap.get(stt);
  style = "color: "+bocolor+";";
  %>
  <td align="center" colspan="6" style="<%=style%>"><%=statusLst.get(st)%></td>
  <%}%>
  </tr>
  
  <tr><td></td>
  <%for(int k=0;k< colList.size();k++){
     String colV = (String)colList.get(k);
     if(colListGt.contains(colV)){
  for(int st=0;st<statusLst.size();st++){
  colnum++;
  String stt=(String)statusLst.get(st);  
  String bocolor=(String)sttColorCodeMap.get(stt);
  style = "color: "+bocolor+";";
  %>
 <td align="center" style="<%=style%>">QTY</td>
 <td align="center"><span id="<%=key%>_CTS_<%=colnum%>_<%=st%>_HTTL" style="<%=style%> display:none;">CTS</span></td>
 <td align="center"><span id="<%=key%>_AVG_<%=colnum%>_<%=st%>_HTTL" style="<%=style%> display:none;">AVG</span></td>
 <td align="center"><span id="<%=key%>_RAP_<%=colnum%>_<%=st%>_HTTL" style="<%=style%> display:none;">RAP</span></td>
 <td align="center"><span id="<%=key%>_AGE_<%=colnum%>_<%=st%>_HTTL" style="<%=style%> display:none;">AGE</span></td>
 <td align="center"><span id="<%=key%>_HIT_<%=colnum%>_<%=st%>_HTTL" style="<%=style%> display:none;">HIT</span></td>
  <%}}}
  for(int st=0;st<statusLst.size();st++){
  String stt=(String)statusLst.get(st);  
  String bocolor=(String)sttColorCodeMap.get(stt);
  style = "color: "+bocolor+";";
  %>
  <td align="center" style="<%=style%>">QTY</td>
 <td align="center"><span id="<%=key%>_TOTALCTS_<%=rownum%>_<%=st%>_HTTL" style="<%=style%> display:none;">CTS</span></td>
 <td align="center"><span id="<%=key%>_TOTALAVG_<%=rownum%>_<%=st%>_HTTL" style="<%=style%> display:none;">AVG</span></td>
 <td align="center"><span id="<%=key%>_TOTALRAP_<%=rownum%>_<%=st%>_HTTL" style="<%=style%> display:none;">RAP</span></td>
 <td align="center"><span id="<%=key%>_TOTALAGE_<%=rownum%>_<%=st%>_HTTL" style="<%=style%> display:none;">AGE</span></td>
 <td align="center"><span id="<%=key%>_TOTALHIT_<%=rownum%>_<%=st%>_HTTL" style="<%=style%> display:none;">HIT</span></td>
 <%}%>
  </tr>
  <%for(int m=0;m< rowList.size();m++){
    String rowV = (String)rowList.get(m);
       boolean isDis = true;
        int totalQtyCol=0;
        int sumtotalQtyCol=0;
        int totalQtySold=0;
        int sumtotalQtySold=0;
        
    if(rowV.indexOf("+")!=-1)   
     isDis = false;
     if(rowV.indexOf("-")!=-1)   
     isDis = false;
    if(isDis){
    if(rowListGt.contains(rowV)){
    checkCol=key+"_row_"+loopcol;
    loopcol++;
    colnum=0;
    rownum++;
    %>
  <tr> <th><%=rowV%><input type="checkbox" name="checkCol" id="<%=checkCol%>" value="<%=rowV%>"/>
   <%if(isShowgraph.equals("Y")){%>
  <A href="<%=info.getReqUrl()%>/report/barGraph.jsp?key=<%=replaceplus%>&row=<%=rowV%>&col=" target="SC"  onclick="loadASSFNLPop('chart')" title="Click Here To See Bar Graph">
<img src="../images/barchart.png" border="0" id="chart"/></a>
<A href="<%=info.getReqUrl()%>/report/commonpieChart.jsp?key=<%=replaceplus%>&row=<%=rowV%>&col=" target="SC"  onclick="loadASSFNLPop('chart')" title="Click Here To See Pie Chart">
<img src="../images/piechart.png" border="0" id="chart"/></a><%}%>
  </th>
  
  <%for(int n=0;n< colList.size();n++){
    String colV = (String)colList.get(n);
     boolean isDis1 = true;
    
    if(colV.indexOf("+")!=-1)   
     isDis1 = false;
     if(colV.indexOf("-")!=-1)   
     isDis1 = false;
    if(isDis1){   
    if(colListGt.contains(colV)){
    for(int st=0;st<statusLst.size();st++){
    colnum++;
    String stt=(String)statusLst.get(st);    
    String keyQty = key+"_"+rowV+"_"+colV+"_"+stt+"_QTY";
    String keyCts = key+"_"+rowV+"_"+colV+"_"+stt+"_CTS";
    String keyAvg = key+"_"+rowV+"_"+colV+"_"+stt+"_AVG";
    String keyRap = key+"_"+rowV+"_"+colV+"_"+stt+"_RAP";
    String keyAge = key+"_"+rowV+"_"+colV+"_"+stt+"_AGE";
    String keyHit = key+"_"+rowV+"_"+colV+"_"+stt+"_HIT";
    String ctsId=key.trim()+"_CTS_"+rownum+"_"+colnum;
    String avgId=key.trim()+"_AVG_"+rownum+"_"+colnum;
    String rapId=key.trim()+"_RAP_"+rownum+"_"+colnum;
    String ageId=key.trim()+"_AGE_"+rownum+"_"+colnum;
    String hitId=key.trim()+"_HIT_"+rownum+"_"+colnum;
    String valQty = util.nvl((String)dataDtl.get(keyQty.trim()));
    String valCts = util.nvl((String)dataDtl.get(keyCts.trim()));
    String valAvg = util.nvl((String)dataDtl.get(keyAvg.trim()));
    String valRap = util.nvl((String)dataDtl.get(keyRap.trim()));
    String valAge = util.nvl((String)dataDtl.get(keyAge.trim()));
    String valHit = util.nvl((String)dataDtl.get(keyHit.trim()));
   
      if(!valQty.equals("")){
      totalQtyCol=Integer.parseInt(valQty);
     sumtotalQtyCol+=totalQtyCol;
     String bocolor=(String)sttColorCodeMap.get(stt);
     style = "color: "+bocolor+";";
     tdstyle="";
     if(statusLst.contains("SOLD") && stt.equals("MKT") && inventoryDis.equals("Y")) {
     double mktQty=Double.parseDouble(valQty);
     String soldKeyQty = key+"_"+rowV+"_"+colV+"_SOLD_QTY";
     String soldQty = util.nvl2((String)dataDtl.get(soldKeyQty),"0");
     
     if(!soldQty.equals("")){
     perCentSold=Math.round((Double.parseDouble(soldQty)/days)*100);
     perCentSoldQty = Math.round((MIN_INVENTORY_DAYS*perCentSold)/100);
     perCentMinTol=perCentSoldQty-Math.round((perCentSoldQty/100)*TOLERANCE_MIN_PCT);
     perCentMaxTol=perCentSoldQty+Math.round((perCentSoldQty/100)*TOLERANCE_MAX_PCT);
     if(mktQty>=perCentMinTol && mktQty<=perCentMaxTol)
      tdstyle = "bgcolor=\"#FFFC31\"";
     if(mktQty<perCentMinTol)
     tdstyle = "bgcolor=\"#5EFB6E\"";
     if(mktQty > perCentMaxTol)
     tdstyle = "bgcolor=\"#FF7676\"";
     }
     }
     
    %>
    <td align="center" <%=tdstyle%> style="cursor: pointer;" onclick="genericDtl('genericReport.do?method=loadDtl&key=<%=keyQty%>')" title="Click To See Details"><span style="<%=style%>"><%=valQty%></span></td>
    <td align="center"  <%=tdstyle%>><span id="<%=ctsId%>" style="<%=style%> display:none;" class="BlueTxt"><%=valCts%></span></td>
    <td align="center"  <%=tdstyle%>><span id="<%=avgId%>" style="<%=style%> display:none;" class="redTxt"><%=valAvg%></span></td>
    <td align="center"  <%=tdstyle%>><span id="<%=rapId%>" style="<%=style%> display:none;" class="redTxt"><%=valRap%></span></td>
     <td align="center"  <%=tdstyle%>><span id="<%=ageId%>" style="<%=style%> display:none;" class="redTxt"><%=valAge%></span></td>
      <td align="center"  <%=tdstyle%>><span id="<%=hitId%>" style="<%=style%> display:none;" class="redTxt"><%=valHit%></span></td>
  <%}
  else{%>
  <td></td>
  <td align="center"><span id="<%=ctsId%>" style="display:none;" class="BlueTxt"></span></td>
  <td align="center"><span id="<%=avgId%>" style="display:none;" class="redTxt"></span></td>
  <td align="center"><span id="<%=rapId%>" style="display:none;" class="redTxt"></span></td>
  <td align="center"><span id="<%=ageId%>" style="display:none;" class="redTxt"></span></td>
  <td align="center"><span id="<%=hitId%>" style="display:none;" class="redTxt"></span></td>
  <%}
  }}}}
  for(int sti=0;sti<statusLst.size();sti++){
  String stt=(String)statusLst.get(sti);
    String getcolttlqty=util.nvl((String)dataDtl.get(key+"_"+rowV+"_ALL_"+stt+"_QTY"));
    String getcolttlcts=util.nvl((String)dataDtl.get(key+"_"+rowV+"_ALL_"+stt+"_CTS"));
    String getcolttlavg=util.nvl((String)dataDtl.get(key+"_"+rowV+"_ALL_"+stt+"_AVG"));
    String getcolttlrap=util.nvl((String)dataDtl.get(key+"_"+rowV+"_ALL_"+stt+"_RAP"));
    String getcolttlage=util.nvl((String)dataDtl.get(key+"_"+rowV+"_ALL_"+stt+"_AGE"));
    String getcolttlhit=util.nvl((String)dataDtl.get(key+"_"+rowV+"_ALL_"+stt+"_HIT"));
     if(!getcolttlqty.equals("")){
     tdstyle="";
     String bocolor=(String)sttColorCodeMap.get(stt);
//     piePrpDtl.put(key+"_"+stt+"_"+rowV+"_"+"COL",getcolttlqty);
     style = "color: "+bocolor+";";
     if(statusLst.contains("SOLD") && stt.equals("MKT") && inventoryDis.equals("Y")) {
     double mktQty=Double.parseDouble(getcolttlqty);
     String soldKeyQty = key+"_"+rowV+"_ALL_"+"SOLD_QTY";
     String soldQty = util.nvl2((String)dataDtl.get(soldKeyQty),"0");
     if(!soldQty.equals("")){
     perCentSold=Math.round((Double.parseDouble(soldQty)/days)*100);
     perCentSoldQty = Math.round((MIN_INVENTORY_DAYS*perCentSold)/100);
     perCentMinTol=perCentSoldQty-Math.round((perCentSoldQty/100)*TOLERANCE_MIN_PCT);
     perCentMaxTol=perCentSoldQty+Math.round((perCentSoldQty/100)*TOLERANCE_MAX_PCT);
     if(mktQty>=perCentMinTol && mktQty<=perCentMaxTol)
      tdstyle = "bgcolor=\"#FFFC31\"";
     if(mktQty<perCentMinTol)
     tdstyle = "bgcolor=\"#5EFB6E\"";
     if(mktQty > perCentMaxTol)
     tdstyle = "bgcolor=\"#FF7676\"";
     }
     }
    %>
  <td align="center"  <%=tdstyle%> style="cursor: pointer;" onclick="genericDtl('genericReport.do?method=loadDtl&key=<%=key%>_<%=rowV%>_ALL_<%=stt%>')" title="Click To See Details"><span style="<%=style%>"><%=getcolttlqty%></span></td>
   <td align="center"  <%=tdstyle%>><span id="<%=key%>_TTLCOLCTS_<%=rownum%>_<%=sti%>" style="<%=style%> display:none; " class="BlueTxt"><%=getcolttlcts%></span></td>
    <td align="center"  <%=tdstyle%>><span id="<%=key%>_TTLCOLAVG_<%=rownum%>_<%=sti%>" style="<%=style%> display:none; " class="redTxt"><%=getcolttlavg%></span></td>
     <td align="center"  <%=tdstyle%>><span id="<%=key%>_TTLCOLRAP_<%=rownum%>_<%=sti%>" style="<%=style%> display:none; " class="redTxt"><%=getcolttlrap%></span></td>
     <td align="center"  <%=tdstyle%>><span id="<%=key%>_TTLCOLAGE_<%=rownum%>_<%=sti%>" style="<%=style%> display:none; " class="redTxt"><%=getcolttlage%></span></td>
     <td align="center"  <%=tdstyle%>><span id="<%=key%>_TTLCOLHIT_<%=rownum%>_<%=sti%>" style="<%=style%> display:none; " class="redTxt"><%=getcolttlhit%></span></td>
  <%} else{%>
  <td align="center" style="cursor: pointer;" onclick="genericDtl('genericReport.do?method=loadDtl&key=<%=key%>_<%=rowV%>_ALL_<%=stt%>')" title="Click To See Details"><span></span></td>
   <td align="center"><span id="<%=key%>_TTLCOLCTS_<%=rownum%>_<%=sti%>" style="display:none; " class="BlueTxt"></span></td>
    <td align="center"><span id="<%=key%>_TTLCOLAVG_<%=rownum%>_<%=sti%>" style="display:none; " class="redTxt"></span></td>
    <td align="center"><span id="<%=key%>_TTLCOLRAP_<%=rownum%>_<%=sti%>" style="display:none; " class="redTxt"></span></td>
    <td align="center"><span id="<%=key%>_TTLCOLAGE_<%=rownum%>_<%=sti%>" style="display:none; " class="redTxt"></span></td>
    <td align="center"><span id="<%=key%>_TTLCOLHIT_<%=rownum%>_<%=sti%>" style="display:none; " class="redTxt"></span></td>
  <%}
  }%>
  </tr>
  
  <%}}
  
  }%>
  <input type="hidden" name="loopcol" id="loopcol" value="<%=loopcol%>"/>
  
  <tr><th>Total
   <%if(isShowgraph.equals("Y")){%>
  <A href="<%=info.getReqUrl()%>/report/barGraph.jsp?key=<%=replaceplus%>&row=ALL&col=" target="SC"  onclick="loadASSFNLPop('chart')" title="Click Here To See Bar Graph">
<img src="../images/barchart.png" border="0" id="chart"/></a>
<A href="<%=info.getReqUrl()%>/report/commonpieChart.jsp?key=<%=replaceplus%>&row=ALL&col=" target="SC"  onclick="loadASSFNLPop('chart')" title="Click Here To See Pie Chart">
<img src="../images/piechart.png" border="0" id="chart"/></a><%}%>
  </th>
  <%  
  colnum=0;
  for(int j=0;j< colList.size();j++){
    String colV = (String)colList.get(j);
    if(colListGt.contains(colV)){
    for(int st=0;st<statusLst.size();st++){
    colnum++;
    String stt=(String)statusLst.get(st);
    String getclrttlqty=util.nvl((String)dataDtl.get(key+"_ALL_"+colV+"_"+stt+"_QTY"));
    String getclrttlcts=util.nvl((String)dataDtl.get(key+"_ALL_"+colV+"_"+stt+"_CTS"));
    String getclrttlavg=util.nvl((String)dataDtl.get(key+"_ALL_"+colV+"_"+stt+"_AVG"));
    String getclrttlrap=util.nvl((String)dataDtl.get(key+"_ALL_"+colV+"_"+stt+"_RAP"));
    String getclrttlage=util.nvl((String)dataDtl.get(key+"_ALL_"+colV+"_"+stt+"_AGE"));
    String getclrttlhit=util.nvl((String)dataDtl.get(key+"_ALL_"+colV+"_"+stt+"_HIT"));
    String GCLRCTSID="GCLRIDCTS_"+colnum;
    String GCLRAVGID="GCLRIDAVG_"+colnum;
    String GCLRRAPID="GCLRIDRAP_"+colnum;
    String GCLRAGEID="GCLRIDAGE_"+colnum;
    String GCLRHITID="GCLRIDHIT_"+colnum;
//    if(!getclrttlqty.equals("")){
//    piePrpDtl.put(key+"_"+stt+"_"+colV+"_"+"CLR",getclrttlqty);
//    }   
    if(!getclrttlqty.equals("")){
    String bocolor=(String)sttColorCodeMap.get(stt);
     style = "color: "+bocolor+";";
     tdstyle="";
     if(statusLst.contains("SOLD") && stt.equals("MKT") && inventoryDis.equals("Y")) {
     double mktQty=Double.parseDouble(getclrttlqty);
     String soldKeyQty = key+"_ALL_"+colV+"_"+"SOLD_QTY";
     String soldQty = util.nvl2((String)dataDtl.get(soldKeyQty),"0");
     if(!soldQty.equals("")){
     perCentSold=Math.round((Double.parseDouble(soldQty)/days)*100);
     perCentSoldQty = Math.round((MIN_INVENTORY_DAYS*perCentSold)/100);
     perCentMinTol=perCentSoldQty-Math.round((perCentSoldQty/100)*TOLERANCE_MIN_PCT);
     perCentMaxTol=perCentSoldQty+Math.round((perCentSoldQty/100)*TOLERANCE_MAX_PCT);
     if(mktQty>=perCentMinTol && mktQty<=perCentMaxTol)
      tdstyle = "bgcolor=\"#FFFC31\"";
     if(mktQty<perCentMinTol)
     tdstyle = "bgcolor=\"#5EFB6E\"";
     if(mktQty > perCentMaxTol)
     tdstyle = "bgcolor=\"#FF7676\"";
     }
     }
    %>
 <td align="center"  <%=tdstyle%>  style="cursor: pointer;" onclick="genericDtl('genericReport.do?method=loadDtl&key=<%=key%>_ALL_<%=colV%>_<%=stt%>')" title="Click To See Details"><span style="<%=style%>"><%=getclrttlqty%></span></td>
 <td align="center" <%=tdstyle%> ><span id="<%=key%>_<%=GCLRCTSID%>" style="<%=style%> display:none;" class="BlueTxt"><%=getclrttlcts%></span></td>
 <td align="center"  <%=tdstyle%> ><span id="<%=key%>_<%=GCLRAVGID%>" style="<%=style%> display:none;" class="redTxt"><%=getclrttlavg%></span></td>
 <td align="center"  <%=tdstyle%> ><span id="<%=key%>_<%=GCLRRAPID%>" style="<%=style%> display:none;" class="redTxt"><%=getclrttlrap%></span></td>
 <td align="center"  <%=tdstyle%> ><span id="<%=key%>_<%=GCLRAGEID%>" style="<%=style%> display:none;" class="redTxt"><%=getclrttlage%></span></td>
 <td align="center"  <%=tdstyle%> ><span id="<%=key%>_<%=GCLRHITID%>" style="<%=style%> display:none;" class="redTxt"><%=getclrttlhit%></span></td>
  <%}else{%>
  <td></td>
  <td align="center"><span id="<%=key%>_<%=GCLRCTSID%>" style="display:none;" class="BlueTxt"></span></td>
 <td align="center"><span id="<%=key%>_<%=GCLRAVGID%>" style="display:none;" class="redTxt"></span></td>
 <td align="center"><span id="<%=key%>_<%=GCLRRAPID%>" style="display:none;" class="redTxt"></span></td>
 <td align="center"><span id="<%=key%>_<%=GCLRAGEID%>" style="display:none;" class="redTxt"></span></td>
 <td align="center"><span id="<%=key%>_<%=GCLRHITID%>" style="display:none;" class="redTxt"></span></td>
  <%
  }}}}
   for(int st=0;st<statusLst.size();st++){
   String stt=(String)statusLst.get(st);
   String getttlqty=util.nvl((String)getGrandTotalList.get(key+"_"+stt+"_QTY"));
    String getttlcts=util.nvl((String)getGrandTotalList.get(key+"_"+stt+"_CTS"));
    String getttlavg=util.nvl((String)getGrandTotalList.get(key+"_"+stt+"_AVG"));
    String getttlrap=util.nvl((String)getGrandTotalList.get(key+"_"+stt+"_RAP"));
    String getttlage=util.nvl((String)getGrandTotalList.get(key+"_"+stt+"_AGE"));
    String getttlhit=util.nvl((String)getGrandTotalList.get(key+"_"+stt+"_HIT"));
    String GCTSID="GIDCTS_"+colnum+"_"+st;
    String GAVGID="GIDAVG_"+colnum+"_"+st;
    String GRAPID="GIDRAP_"+colnum+"_"+st;
    String GAGEID="GIDAGE_"+colnum+"_"+st;
    String GHITID="GIDHIT_"+colnum+"_"+st;
    String bocolor=(String)sttColorCodeMap.get(stt);
     style = "color: "+bocolor+";";

     if(!getttlqty.equals("")){
     tdstyle="";
      if(statusLst.contains("SOLD") && stt.equals("MKT") && inventoryDis.equals("Y")) {
     double mktQty=Double.parseDouble(getttlqty);
     String soldKeyQty = key+"_SOLD_QTY";
     String soldQty = util.nvl2((String)getGrandTotalList.get(soldKeyQty),"0");
     if(!soldQty.equals("")){
     perCentSold=Math.round((Double.parseDouble(soldQty)/days)*100);
     perCentSoldQty = Math.round((MIN_INVENTORY_DAYS*perCentSold)/100);
     perCentMinTol=perCentSoldQty-Math.round((perCentSoldQty/100)*TOLERANCE_MIN_PCT);
     perCentMaxTol=perCentSoldQty+Math.round((perCentSoldQty/100)*TOLERANCE_MAX_PCT);
     if(mktQty>=perCentMinTol && mktQty<=perCentMaxTol)
     tdstyle = "bgcolor=\"#FFFC31\"";
     if(mktQty<perCentMinTol)
     tdstyle = "bgcolor=\"#5EFB6E\"";
     if(mktQty > perCentMaxTol)
     tdstyle = "bgcolor=\"#FF7676\"";
     }
     }
   %>
  <td align="center" <%=tdstyle%> style="cursor: pointer;" onclick="genericDtl('genericReport.do?method=loadDtl&key=<%=key%>_ALL_ALL_<%=stt%>')" title="Click To See Details"><span style="<%=style%>"> <%=getttlqty%></span></td>
  <td align="center" <%=tdstyle%>><span id="<%=key%>_<%=GCTSID%>" style="<%=style%> display:none;" class="BlueTxt"><%=getttlcts%></span></td>
  <td align="center" <%=tdstyle%>><span id="<%=key%>_<%=GAVGID%>" style="<%=style%> display:none;" class="redTxt"><%=getttlavg%></span></td>
  <td align="center" <%=tdstyle%>><span id="<%=key%>_<%=GRAPID%>" style="<%=style%> display:none;" class="redTxt"><%=getttlrap%></span></td>
  <td align="center" <%=tdstyle%>><span id="<%=key%>_<%=GAGEID%>" style="<%=style%> display:none;" class="redTxt"><%=getttlage%></span></td>
  <td align="center" <%=tdstyle%>><span id="<%=key%>_<%=GHITID%>" style="<%=style%> display:none;" class="redTxt"><%=getttlhit%></span></td>
  <%}else{%>
  <td></td>
  <td align="center"><span id="<%=key%>_<%=GCTSID%>" style="<%=style%> display:none;" class="BlueTxt"></span></td>
 <td align="center"><span id="<%=key%>_<%=GAVGID%>" style="<%=style%> display:none;" class="redTxt"></span></td>
 <td align="center"><span id="<%=key%>_<%=GRAPID%>" style="<%=style%> display:none;" class="redTxt"></span></td>
  <td align="center"><span id="<%=key%>_<%=GAGEID%>" style="<%=style%> display:none;" class="redTxt"></span></td>
   <td align="center"><span id="<%=key%>_<%=GHITID%>" style="<%=style%> display:none;" class="redTxt"></span></td>
  <%}}%>
  </tr>
  
  </table>
  </td>
  </tr>
  <%} 
//  if(piePrpDtl!=null && piePrpDtl.size()>0){
//  session.setAttribute("piePrpDtl", piePrpDtl);
//  }
  %>
  </table>
  <%}else{%>
  Sorry no result found.
 <%}%>  
  </td></tr></table>
  </body>
</html>