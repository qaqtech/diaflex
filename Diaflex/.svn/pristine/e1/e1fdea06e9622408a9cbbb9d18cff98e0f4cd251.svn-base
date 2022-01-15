<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page errorPage="../error_page.jsp" %>
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import="java.util.ArrayList,java.util.ArrayList,java.util.HashMap,ft.com.*, java.sql.*, java.util.Enumeration, java.util.logging.Level, java.util.Collections"%>
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
    <script src="<%=info.getReqUrl()%>/jqueryScript/jqueryScript.js?v=<%=info.getJsVersion()%>" type="text/javascript"></script>
    <style type="text/css">
    table.grid10 td {
        padding:0px;
    }
div@fixedheader {
	position:fixed;
	top:0px;
	left:0px;
	width:100%;
	color:#CCC;
	background:white;
}
    </style>
  </head>
  <%
    DBUtil dbutil = new DBUtil();
    DBMgr db = new DBMgr(); 
    db.setCon(info.getCon());
    dbutil.setDb(db);
    dbutil.setInfo(info);
    db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));db.setDbUsr(info.getDbUsr());
    dbutil.setLogApplNm(info.getLoApplNm());
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
  ArrayList defaultstatusLst= ((ArrayList)session.getAttribute("defaultstatusLst") == null)?new ArrayList():(ArrayList)session.getAttribute("defaultstatusLst");
  HashMap dscttlLst= ((HashMap)session.getAttribute("dscttlLst") == null)?new HashMap():(HashMap)session.getAttribute("dscttlLst");
HashMap sttColorCodeMap =((HashMap)new JspUtil().getFromMem(info.getMem_ip(),info.getMem_port(),info.getCnt()+"_sttColorCodeMap") == null)?new HashMap():(HashMap)new JspUtil().getFromMem(info.getMem_ip(),info.getMem_port(),info.getCnt()+"_sttColorCodeMap"); 
   ArrayList rowListGt = (ArrayList)session.getAttribute("rowList");
   ArrayList colListGt  = (ArrayList)session.getAttribute("colList");
   ArrayList showprpLst  = (ArrayList)session.getAttribute("showprpLst");
   int showprpLstsz=showprpLst.size();
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
    String gridByGrid = util.nvl((String)session.getAttribute("gridByGrid"),"PRP");
  HashMap mprp = info.getSrchMprp();
  HashMap prp = info.getSrchPrp();
  String checkClr="";
  String checkCol="";
  String checkrowVal="";
  String checkcolVal="",imageMap="";
  int defaultstatusLstsz=defaultstatusLst.size();
  int loopclr=0;
  int loopcol=0;
  int tcount=0;
  String style="";
  String tdstyle="";
  String hasrc="";
  String imgid="";
//  if(prp==null){
//  dbutil.initPrpSrch();
//  prp = info.getSrchPrp();
//  mprp = info.getSrchMprp();
//  }
  ArrayList rowList=new ArrayList();
  ArrayList colList=new ArrayList();
  if(gridByGrid.equals("PRP")){
  rowList = (ArrayList)prp.get(gridrowLst.get(0)+"V");
  colList = (ArrayList)prp.get(gridcolLst.get(0)+"V");
  }else{
    rowList = util.useDifferentArrayListUnique((ArrayList)prp.get(gridrowLst.get(0)+"G"));
    colList = util.useDifferentArrayListUnique((ArrayList)prp.get(gridcolLst.get(0)+"G"));
  }
  String rowlprpTyp = util.nvl((String)mprp.get(gridrowLst.get(0)+"T"));
  String collprpTyp = util.nvl((String)mprp.get(gridcolLst.get(0)+"T"));
  if(rowlprpTyp.equals("T") || rowlprpTyp.equals("N")){
  rowList=new ArrayList();
  rowList=rowListGt;
  Collections.sort(rowList);
  }
  if(collprpTyp.equals("T") || collprpTyp.equals("N")){
  colList=new ArrayList();
  colList=colListGt;
  Collections.sort(colList);
  }
  
  String GenStt=(String)session.getAttribute("GenStt");
  String processtm=util.nvl((String)request.getAttribute("processtm"));
  ArrayList srchids= (ArrayList)session.getAttribute("srchids");
  String mongosrchDsc=util.nvl((String)session.getAttribute("mongosrchDsc"));
  int sttLstSize=statusLst.size();
  int statusLstsz=statusLst.size();
  String rightbrdr="";
  int rownum=0;
  int colnum=0;
  int colnumstt=0;
  double perCentSold,perCentSoldQty,perCentMinTol,perCentMaxTol;
  double perdfvalAvg=0,perdfvalQty=0;
  ArrayList gridfmtLst = (ArrayList)session.getAttribute("gridfmtLst");
  HashMap allPageDtl= (info.getPageDetails() == null)?new HashMap():(HashMap)info.getPageDetails();
  HashMap pageDtl=(HashMap)allPageDtl.get("GENERIC_REPORT");
    ArrayList pageList=new ArrayList();
    HashMap pageDtlMap=new HashMap();
    String fld_nme="",fld_ttl="",val_cond="",dflt_val="",lov_qry="",fld_typ="",form_nme="",flg1="";
    String inventoryDis = util.nvl((String)sttColorCodeMap.get("INVDIS"));
    String isShowgraph = util.nvl((String)sttColorCodeMap.get("GRAPH"));
    
String perQTY="",perAVG="";
pageList=(ArrayList)pageDtl.get("PERDIFFQTY");
if(pageList!=null && pageList.size() >0){
for(int j=0;j<pageList.size();j++){
pageDtlMap=(HashMap)pageList.get(j);
perQTY=(String)pageDtlMap.get("dflt_val");
}
}
pageList=(ArrayList)pageDtl.get("PERDIFFAVG");
if(pageList!=null && pageList.size() >0){
for(int j=0;j<pageList.size();j++){
pageDtlMap=(HashMap)pageList.get(j);
perAVG=(String)pageDtlMap.get("dflt_val");
}
}

        int genericcolspan=2;
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
<div id="fixedheader">
  <table width="100%"  border="0" align="center" cellpadding="0" cellspacing="0" >
 
  <tr>
    <td height="5" valign="top" class="greyline"><img src="../images/grey_linebig.png" width="685" height="5" align="right" /></td>
  </tr>
  <tr>
  <td valign="top" class="hedPg">
  <table cellpadding="1" cellspacing="5"><tr><td valign="middle">
  <img src="../images/line.jpg" border="0" /></td><td valign="middle" nowrap="nowrap"> 
  <span class="pgHed">Report View
  </span></td>
  <td valign="top" class="hedPg">
  <%if(!processtm.equals("")){%>
  <span class="txtLink">
  <b><%=processtm%></b>
  </span>
  <%}
  if(mongosrchDsc.equals("")){
  if(srchids!=null && srchids.size()>0){%>
  &nbsp;
  <span class="txtLink" onclick="displayDiv('srchdsc')">
  Search Description 
  </span>
  <span class="txtLink" id="srchdsc" style="display:none">
  <%for(int i=0;i<srchids.size();i++){%>
  <%=statusLst.get(i)%> : <%=dbutil.srchDscription((String)srchids.get(i))%>
  <%}%>
  </span>
  <%}}else{%>
  &nbsp;
  <span class="txtLink" onclick="displayDiv('srchdsc')">Search Description</span> <span class="txtLink" id="srchdsc" style="display:none"><%=mongosrchDsc%></span>
  <%}%>
  </td>
  </tr></table>
  </td></tr>
  <tr><td valign="top" class="hedPg">
  <span class="txtLink" id="ADVFEAR_TAB" style="" onclick="displayDiv('ADVFEAR')">Advance Features</span>
 <table class="genericTB" id="ADVFEAR" style="display:none; width:50%;color:black;">
 <tr>
 <td>
 <table>
 <tr>
  <%        pageList=(ArrayList)pageDtl.get("GRIDFMT");
            if(pageList!=null && pageList.size() >0){
            for(int j=0;j<pageList.size();j++){
            pageDtlMap=(HashMap)pageList.get(j);
            dflt_val=(String)pageDtlMap.get("dflt_val"); %>
    <td style="<%=dflt_val%>">Grid Format:
    <html:select  property="value(gridfmt)" name="genericReportForm" styleId="gridfmt" onchange="redirectAnalysissave();" > 
     <html:option value="" >---Select---</html:option> 
     <%if(gridfmtLst!=null && gridfmtLst.size()>0){
     for(int i=0;i<gridfmtLst.size();i++){
     String gridfmtval=(String)gridfmtLst.get(i);%>
     <html:option value="<%=gridfmtval%>" ><%=gridfmtval%></html:option> 
     <%}}%>
    </html:select> 
    </td>
        <%}}%>
        
<%pageList=(ArrayList)pageDtl.get("GROUPBY");
if(pageList!=null && pageList.size() >0){%>
<td style="">Grid By:
<html:select  property="value(gridby)" name="genericReportForm" styleId="gridby" onchange="redirectAnalysissaveByGroup();"  >
     <html:option value="" >---Select---</html:option> 
<%for(int j=0;j<pageList.size();j++){
pageDtlMap=(HashMap)pageList.get(j);
fld_ttl=(String)pageDtlMap.get("fld_ttl");
fld_nme=(String)pageDtlMap.get("fld_nme"); %>
<html:option value="<%=fld_nme%>" ><%=fld_ttl%></html:option>
<%}%>
</html:select> 
</td>
<%}%>  
</tr>
</table>
</td>
        </tr>
  <tr>
  <td valign="top">
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
</table>
</td></tr>
</table>
</div>
<div >
  <table width="100%"  border="0" align="center" cellpadding="0" cellspacing="0" >
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
  keyLable=keyLable.replaceFirst("@", " "+(String)mprp.get(gridcommLst.get(g))+"   :");
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
  <td valign="middle" nowrap="nowrap"><%=keyLable%> <span style="padding-left:10px;"><a onclick="createPdfAnal('<%=i%>')"><img src="../images/PDFIconSmall.jpg" border="0"/> </a></span>
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
String chk="";
if(fld_ttl.equals("QTY")){
chk="checked=\"checked\"";
}
%>
   <input type="checkbox" name="<%=fld_nme%>" id="<%=fld_typ%>" <%=chk%> value="" onclick="<%=val_cond%>"/><%=fld_ttl%>&nbsp;
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
  <tr><td valign="top"><table class="grid10" id="<%=key%>">
  <tr><th></th>
  <%
  
  for(int j=0;j< colList.size();j++){
    String colV = (String)colList.get(j);
    if(colListGt.contains(colV)){
    checkClr=key+"_col_"+loopclr;
    loopclr++;
    %>
 <th colspan="<%=sttLstSize*genericcolspan%>"><%=colV%><input type="checkbox" name="checkClr" id="<%=checkClr%>" value="<%=colV%>"/>
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
  <th  colspan="<%=sttLstSize*genericcolspan%>">Total
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
  String bocolor=util.nvl((String)sttColorCodeMap.get(stt),"black");
  style = "";
  if(st==(statusLstsz-1))
  rightbrdr="rghtbrdrend";
  else
  rightbrdr="rghtbrdr";
  %>
 <td align="center" colspan="<%=genericcolspan%>" class="<%=rightbrdr%>" style="<%=style%> font-weight:bold;"><%=util.nvl((String)dscttlLst.get(stt),stt)%></td>
  <%}}}
  colnumstt=0;
  for(int st=0;st<statusLst.size();st++){
  colnumstt++;
  String stt=(String)statusLst.get(st);  
   String bocolor=util.nvl((String)sttColorCodeMap.get(stt),"black");
  style = "";
  if(st==(statusLstsz-1))
  rightbrdr="rghtbrdrend";
  else
  rightbrdr="rghtbrdr";
  %>
  <td align="center" colspan="<%=genericcolspan%>" class="<%=rightbrdr%>" style="<%=style%> font-weight:bold;"><%=util.nvl((String)dscttlLst.get(stt),stt)%></td>
  <%}%>
  </tr>
  
  <tr><td></td>
  <%for(int k=0;k< colList.size();k++){
     String colV = (String)colList.get(k);
     if(colListGt.contains(colV)){
  for(int st=0;st<statusLst.size();st++){
  colnum++;
  String stt=(String)statusLst.get(st);  
  String bocolor=util.nvl((String)sttColorCodeMap.get(stt),"black");
  style = "";
  if(st==(statusLstsz-1))
  rightbrdr="rghtbrdrend";
  else
  rightbrdr="rghtbrdr";
  %>
  <td><table class="wdth" width="100%" cellpadding="0" cellspacing="2"><tr>	
 <td align="center"><span id="<%=key%>@QTY@<%=colnum%>@<%=st%>@HTTL" style="<%=style%>">QTY</span></td>
 <td align="center"><span id="<%=key%>@CTS@<%=colnum%>@<%=st%>@HTTL" style="<%=style%> display:none;">CTS</span></td>
 </tr><tr>
 <td align="center"><span id="<%=key%>@AVG@<%=colnum%>@<%=st%>@HTTL" style="<%=style%> display:none;">AVG</span></td>
 <td align="center"><span id="<%=key%>@RAP@<%=colnum%>@<%=st%>@HTTL" style="<%=style%> display:none;">RAP</span></td>
 </tr><tr>
  <%pageList=(ArrayList)pageDtl.get("SHOW");
if(pageList!=null && pageList.size() >0){
for(int pp=0;pp<pageList.size();pp++){
pageDtlMap=(HashMap)pageList.get(pp);
fld_ttl=(String)pageDtlMap.get("fld_ttl");
if(!fld_ttl.equals("QTY") && !fld_ttl.equals("CTS") && !fld_ttl.equals("AVG") && !fld_ttl.equals("RAP")){
%>
 <td align="center"><span id="<%=key%>@<%=fld_ttl%>@<%=colnum%>@<%=st%>@HTTL" style="<%=style%> display:none;"><%=fld_ttl%></span></td>
 <%if(pp%2!=0){%>
</tr><tr> 
<%}}}}%> 
  </tr></table></td>
  <td class="<%=rightbrdr%>"></td>
  <%}}}
  
  for(int st=0;st<statusLst.size();st++){
  String stt=(String)statusLst.get(st);  
String bocolor=util.nvl((String)sttColorCodeMap.get(stt),"black");
  style = "";
  if(st==(statusLstsz-1))
  rightbrdr="rghtbrdrend";
  else
  rightbrdr="rghtbrdr";
  %>
  <td><table class="wdth" width="100%" cellpadding="0" cellspacing="2"><tr>	
  <td align="center"><span id="<%=key%>@TOTALQTY@<%=rownum%>@<%=st%>@HTTL" style="<%=style%>">QTY</span></td>
 <td align="center"><span id="<%=key%>@TOTALCTS@<%=rownum%>@<%=st%>@HTTL" style="<%=style%> display:none;">CTS</span></td>
 </tr><tr>
 <td align="center"><span id="<%=key%>@TOTALAVG@<%=rownum%>@<%=st%>@HTTL" style="<%=style%> display:none;">AVG</span></td>
 <td align="center"><span id="<%=key%>@TOTALRAP@<%=rownum%>@<%=st%>@HTTL" style="<%=style%> display:none;">RAP</span></td>
 </tr><tr>
  <%pageList=(ArrayList)pageDtl.get("SHOW");
if(pageList!=null && pageList.size() >0){
for(int pp=0;pp<pageList.size();pp++){
pageDtlMap=(HashMap)pageList.get(pp);
fld_ttl=(String)pageDtlMap.get("fld_ttl");
if(!fld_ttl.equals("QTY") && !fld_ttl.equals("CTS") && !fld_ttl.equals("AVG") && !fld_ttl.equals("RAP")){
%>
 <td align="center"><span id="<%=key%>@TOTAL<%=fld_ttl%>@<%=rownum%>@<%=st%>@HTTL" style="<%=style%> display:none;"><%=fld_ttl%></span></td>
 <%if(pp%2!=0){%>
</tr><tr> 
<%}}}}%> 
 </tr></table></td>
  <td class="<%=rightbrdr%>"></td>
 <%}%>
  </tr>
  <%for(int m=0;m< rowList.size();m++){
    String rowV = (String)rowList.get(m);
       boolean isDis = true;
        int totalQtyCol=0;
        int sumtotalQtyCol=0;
        int totalQtySold=0;
        int sumtotalQtySold=0;
        
    if(rowV.indexOf("+")!=-1 && !rowlprpTyp.equals("T") && !rowlprpTyp.equals("N") && !gridByGrid.equals("GRP"))   
     isDis = false;
     if(rowV.indexOf("-")!=-1 && !rowlprpTyp.equals("T") && !rowlprpTyp.equals("N") && !gridByGrid.equals("GRP"))   
     isDis = false;
    if(isDis){
    if(rowListGt.contains(rowV)){
    checkCol=key+"_row_"+loopcol;
    loopcol++;
    colnum=0;
    rownum++;
    %>
  <tr> <td nowrap="nowrap"><%=rowV%><input type="checkbox" name="checkCol" id="<%=checkCol%>" value="<%=rowV%>"/>
   <%if(isShowgraph.equals("Y")){%>
  <A href="<%=info.getReqUrl()%>/report/barGraph.jsp?key=<%=replaceplus%>&row=<%=rowV%>&col=" target="SC"  onclick="loadASSFNLPop('chart')" title="Click Here To See Bar Graph">
<img src="../images/barchart.png" border="0" id="chart"/></a>
<A href="<%=info.getReqUrl()%>/report/commonpieChart.jsp?key=<%=replaceplus%>&row=<%=rowV%>&col=" target="SC"  onclick="loadASSFNLPop('chart')" title="Click Here To See Pie Chart">
<img src="../images/piechart.png" border="0" id="chart"/></a><%}%>
  </td>
  
  <%for(int n=0;n< colList.size();n++){
    String colV = (String)colList.get(n);
     boolean isDis1 = true;
    
    if(colV.indexOf("+")!=-1 && !collprpTyp.equals("T") && !collprpTyp.equals("N") && !gridByGrid.equals("GRP"))   
     isDis1 = false;
     if(colV.indexOf("-")!=-1 && !collprpTyp.equals("T") && !collprpTyp.equals("N") && !gridByGrid.equals("GRP"))   
     isDis1 = false;
    if(isDis1){   
    if(colListGt.contains(colV)){
    for(int st=0;st<statusLst.size();st++){
    colnum++;
    String stt=(String)statusLst.get(st);    
    String keyQty = key+"@"+rowV+"@"+colV+"@"+stt+"@QTY";

    String keyCts = key+"@"+rowV+"@"+colV+"@"+stt+"@CTS";
    String keyAvg = key+"@"+rowV+"@"+colV+"@"+stt+"@AVG";
    String keyRap = key+"@"+rowV+"@"+colV+"@"+stt+"@RAP";
    String keyAge = key+"@"+rowV+"@"+colV+"@"+stt+"@AGE";
    String keyHit = key+"@"+rowV+"@"+colV+"@"+stt+"@HIT";
    String keyVlu = key+"@"+rowV+"@"+colV+"@"+stt+"@VLU";
    String diffavgkey=key+"@"+rowV+"@"+colV;
    String diffqtykey=key+"@"+rowV+"@"+colV;
    String qtyId=key.trim()+"@QTY@"+rownum+"@"+colnum;
    String ctsId=key.trim()+"@CTS@"+rownum+"@"+colnum;
    String avgId=key.trim()+"@AVG@"+rownum+"@"+colnum;
    String rapId=key.trim()+"@RAP@"+rownum+"@"+colnum;
    String ageId=key.trim()+"@AGE@"+rownum+"@"+colnum;
    String hitId=key.trim()+"@HIT@"+rownum+"@"+colnum;
    String vluId=key.trim()+"@VLU@"+rownum+"@"+colnum;
    String valQty = util.nvl((String)dataDtl.get(keyQty.trim()));
    String valCts = util.nvl((String)dataDtl.get(keyCts.trim()));
    String valAvg = util.nvl((String)dataDtl.get(keyAvg.trim()));
    String valRap = util.nvl((String)dataDtl.get(keyRap.trim()));
    String valAge = util.nvl((String)dataDtl.get(keyAge.trim()));
    String valHit = util.nvl((String)dataDtl.get(keyHit.trim()));
    String valVlu = util.nvl((String)dataDtl.get(keyVlu.trim()));
    String svpd="";
    String msd="";
    if(st==(statusLstsz-1))
    rightbrdr="rghtbrdrend";
    else
    rightbrdr="rghtbrdr";
      if(!valQty.equals("")){
      totalQtyCol=Integer.parseInt(valQty);
     sumtotalQtyCol+=totalQtyCol;
String bocolor=util.nvl((String)sttColorCodeMap.get(stt),"black");
     style = "";
     tdstyle="";
     if(statusLst.contains("SOLD") && stt.equals("MKT") && inventoryDis.equals("Y")) {
     double mktQty=Double.parseDouble(valQty);
     String soldKeyQty = key+"@"+rowV+"@"+colV+"@SOLD@QTY";
     String soldQty = util.nvl2((String)dataDtl.get(soldKeyQty),"0");
     
     if(!soldQty.equals("")){
     perCentSold=Math.round((Double.parseDouble(soldQty)/days)*100);
     perCentSoldQty = Math.round((MIN_INVENTORY_DAYS*perCentSold)/100);
     perCentMinTol=perCentSoldQty-Math.round((perCentSoldQty/100)*TOLERANCE_MIN_PCT);
     perCentMaxTol=perCentSoldQty+Math.round((perCentSoldQty/100)*TOLERANCE_MAX_PCT);
     if(mktQty>=perCentMinTol && mktQty<=perCentMaxTol)
     style = "border: 3px solid #FFFF00;";
     if(mktQty<perCentMinTol)
     style = "border: 3px solid #006600;";
     if(mktQty > perCentMaxTol)
     style = "border: 3px solid #FF7676;";
     }
     }
     if(statusLst.contains("MKT") && statusLst.contains("SOLD") && (stt.equals("SOLD") || stt.equals("MKT")) && inventoryDis.equals("Y")) {
     String mktKeyVlu = key+"@"+rowV+"@"+colV+"@MKT@VLU";
     String mktVlu = util.nvl2((String)dataDtl.get(mktKeyVlu),"0");
     String soldKeyVlu = key+"@"+rowV+"@"+colV+"@SOLD@VLU";
     String soldVlu = util.nvl2((String)dataDtl.get(soldKeyVlu),"0");
     if(!soldVlu.equals("") && !soldVlu.equals("0")){
     svpd=util.nvl(String.valueOf(Math.round(Double.parseDouble(soldVlu)/days)),"0");
     if(!mktVlu.equals("") && !mktVlu.equals("0") && !svpd.equals("0"))
     msd=util.nvl(String.valueOf(Math.round(Double.parseDouble(mktVlu)/Double.parseDouble(svpd))),"0");
     if(svpd.equals("0"))
     svpd="";
     if(stt.equals("SOLD"))
     msd="";
     if(stt.equals("MKT"))
     svpd="";
     }
     }
     boolean displayper=false;
     String styleqty = "color: red";
     String styleavg = "color: red";
     if(defaultstatusLstsz>1){
     String firststt=util.nvl((String)defaultstatusLst.get(0));
     perdfvalAvg=0;perdfvalQty=0;
    if(stt.indexOf(firststt)<0){
    for(int df=1;df<defaultstatusLst.size();df++){
    String dfstt=util.nvl((String)defaultstatusLst.get(df));
        if(stt.indexOf(dfstt)>=0){
            diffavgkey=diffavgkey+"@"+stt.replaceAll(dfstt,util.nvl((String)defaultstatusLst.get(df-1)))+"@AVG";
            diffqtykey=diffqtykey+"@"+stt.replaceAll(dfstt,util.nvl((String)defaultstatusLst.get(df-1)))+"@QTY";
            String dfvalAvg = util.nvl((String)dataDtl.get(diffavgkey.trim()));
            String dfvalQty = util.nvl((String)dataDtl.get(diffqtykey.trim()));
            if(!valAvg.equals("") && !dfvalAvg.equals("")){
            perdfvalAvg=util.roundTopercentage(Double.parseDouble(valAvg),Double.parseDouble(dfvalAvg));
            displayper=true;
            if(perdfvalAvg>0)
            styleavg = "color: #52D017";
            }
            if(!valQty.equals("") && !dfvalQty.equals("")){
            perdfvalQty=util.roundTopercentage(Double.parseDouble(valQty),Double.parseDouble(dfvalQty));
            if(perdfvalQty>0)
            styleqty = "color: #52D017";
            }
            break;
        }
    }
    }
    }
    %>
    <td style="<%=style%>"><table class="wdth" cellpadding="0" cellspacing="4"><tr>	
    <td align="center" style="cursor: pointer;" onclick="genericDtl('genericReport.do?method=loadDtl&key=<%=keyQty%>')" title="Click To See Details">
    <span id="<%=qtyId%>" style="">
    <%=valQty%>
    <%if(!perQTY.equals("") && displayper){%>
    <br>
    <span style="<%=styleqty%>"><b><%=perdfvalQty%></b></span>
    <%}%>
    </span></td>
    <td align="center" class="BlueTxt"><span id="<%=ctsId%>" style="display:none;"><%=valCts%></span></td>
     </tr><tr>
    <td align="center"><span id="<%=avgId%>" style="display:none;">
    <%=valAvg%>
     <%if(!perAVG.equals("") && displayper){%>
    <br>
     <span style="<%=styleavg%>"><b><%=perdfvalAvg%></b></span>
    <%}%>
    </span></td>
    <td align="center"><span id="<%=rapId%>" style="display:none;" class="BlueTxt"><%=valRap%></span></td>
     </tr><tr>
<%pageList=(ArrayList)pageDtl.get("SHOW");
if(pageList!=null && pageList.size() >0){
for(int j=0;j<pageList.size();j++){
pageDtlMap=(HashMap)pageList.get(j);
fld_ttl=(String)pageDtlMap.get("fld_ttl");
if(!fld_ttl.equals("QTY") && !fld_ttl.equals("CTS") && !fld_ttl.equals("AVG") && !fld_ttl.equals("RAP")){
 String showprpId=key.trim()+"@"+fld_ttl+"@"+rownum+"@"+colnum;
 String valShowprp =" ";
 if(fld_ttl.equals("SVPD"))
 valShowprp=svpd;
 else if(fld_ttl.equals("MSD"))
 valShowprp=msd;
 else
 valShowprp = util.nvl((String)dataDtl.get(key+"@"+rowV+"@"+colV+"@"+stt+"@"+fld_ttl));
 String blue="";
 if(j%2!=0)
 blue=" class=\"BlueTxt\"";
%>
<td align="center"><span id="<%=showprpId%>" style="display:none;" <%=blue%>><%=valShowprp%></span></td>
 <%if(j%2!=0){%>
</tr><tr> 
<%}}}}%> 
</tr></table></td>
   <td class="<%=rightbrdr%>"></td>
  <%}
  else{%>
  <td><table class="wdth" width="100%"  cellpadding="0" cellspacing="1"><tr>	
  <td align="center"><span id="<%=qtyId%>" style=""></span></td>
  <td align="center"><span id="<%=ctsId%>" style="display:none;"></span></td>
   </tr><tr>
  <td align="center"><span id="<%=avgId%>" style="display:none;"></span></td>
  <td align="center"><span id="<%=rapId%>" style="display:none;"></span></td>
   </tr><tr>
<%pageList=(ArrayList)pageDtl.get("SHOW");
if(pageList!=null && pageList.size() >0){
for(int j=0;j<pageList.size();j++){
pageDtlMap=(HashMap)pageList.get(j);
fld_ttl=(String)pageDtlMap.get("fld_ttl");
if(!fld_ttl.equals("QTY") && !fld_ttl.equals("CTS") && !fld_ttl.equals("AVG") && !fld_ttl.equals("RAP")){
 String showprpId=key.trim()+"@"+fld_ttl+"@"+rownum+"@"+colnum;
 String valShowprp = util.nvl((String)dataDtl.get(key+"@"+rowV+"@"+colV+"@"+stt+"@"+fld_ttl));
   String blue="";
 if(j%2!=0)
 blue=" class=\"BlueTxt\"";
%>
<td align="center"><span id="<%=showprpId%>" style="display:none;" <%=blue%> ></span></td>
 <%if(j%2!=0){%>
</tr><tr> 
<%}}}}%> 
</tr></table></td>
   <td class="<%=rightbrdr%>"></td>
  <%}
  }}}}
  for(int sti=0;sti<statusLst.size();sti++){
  String stt=(String)statusLst.get(sti);
    String getcolttlqty=util.nvl((String)dataDtl.get(key+"@"+rowV+"@ALL@"+stt+"@QTY"));
    String getcolttlcts=util.nvl((String)dataDtl.get(key+"@"+rowV+"@ALL@"+stt+"@CTS"));
    String getcolttlavg=util.nvl((String)dataDtl.get(key+"@"+rowV+"@ALL@"+stt+"@AVG"));
    String getcolttlrap=util.nvl((String)dataDtl.get(key+"@"+rowV+"@ALL@"+stt+"@RAP"));
    String getcolttlage=util.nvl((String)dataDtl.get(key+"@"+rowV+"@ALL@"+stt+"@AGE"));
    String getcolttlhit=util.nvl((String)dataDtl.get(key+"@"+rowV+"@ALL@"+stt+"@HIT"));
    String getcolttlvlu=util.nvl((String)dataDtl.get(key+"@"+rowV+"@ALL@"+stt+"@VLU"));
    String svpd="";
    String msd="";
    if(sti==(statusLstsz-1))
    rightbrdr="rghtbrdrend";
    else
    rightbrdr="rghtbrdr";
    style = "";
     if(!getcolttlqty.equals("")){
     tdstyle="";
  String bocolor=util.nvl((String)sttColorCodeMap.get(stt),"black");
//     piePrpDtl.put(key+"_"+stt+"_"+rowV+"_"+"COL",getcolttlqty);
     style = "";
     if(statusLst.contains("SOLD") && stt.equals("MKT") && inventoryDis.equals("Y")) {
     double mktQty=Double.parseDouble(getcolttlqty);
     String soldKeyQty = key+"@"+rowV+"@ALL@"+"SOLD@QTY";
     String soldQty = util.nvl2((String)dataDtl.get(soldKeyQty),"0");
     if(!soldQty.equals("")){
     perCentSold=Math.round((Double.parseDouble(soldQty)/days)*100);
     perCentSoldQty = Math.round((MIN_INVENTORY_DAYS*perCentSold)/100);
     perCentMinTol=perCentSoldQty-Math.round((perCentSoldQty/100)*TOLERANCE_MIN_PCT);
     perCentMaxTol=perCentSoldQty+Math.round((perCentSoldQty/100)*TOLERANCE_MAX_PCT);
     if(mktQty>=perCentMinTol && mktQty<=perCentMaxTol)
     style = "border: 3px solid #FFFF00;";
     if(mktQty<perCentMinTol)
     style = "border: 3px solid #006600;";
     if(mktQty > perCentMaxTol)
     style = "border: 3px solid #FF7676;";
     }
     }
     
     if(statusLst.contains("MKT") && statusLst.contains("SOLD") && (stt.equals("SOLD") || stt.equals("MKT")) && inventoryDis.equals("Y")) {
     String mktKeyVlu = key+"@"+rowV+"@ALL@MKT@VLU";
     String mktVlu = util.nvl2((String)dataDtl.get(mktKeyVlu),"0");
     String soldKeyVlu = key+"@"+rowV+"@ALL@SOLD@VLU";
     String soldVlu = util.nvl2((String)dataDtl.get(soldKeyVlu),"0");
     if(!soldVlu.equals("") && !soldVlu.equals("0")){
     svpd=util.nvl(String.valueOf(Math.round(Double.parseDouble(soldVlu)/days)),"0");
     if(!mktVlu.equals("") && !mktVlu.equals("0") && !svpd.equals("0"))
     msd=util.nvl(String.valueOf(Math.round(Double.parseDouble(mktVlu)/Double.parseDouble(svpd))),"0");
     if(svpd.equals("0"))
     svpd="";
     if(stt.equals("SOLD"))
     msd="";
     if(stt.equals("MKT"))
     svpd="";
     }
     }
     boolean displayper=false;
     String styleqty = "color: red";
     String styleavg = "color: red";
     if(defaultstatusLstsz>1){
     String firststt=util.nvl((String)defaultstatusLst.get(0));
     perdfvalAvg=0;perdfvalQty=0;
    if(stt.indexOf(firststt)<0){
    for(int df=1;df<defaultstatusLst.size();df++){
    String dfstt=util.nvl((String)defaultstatusLst.get(df));
        if(stt.indexOf(dfstt)>=0){
            String dfgetcolttlqty=util.nvl((String)dataDtl.get(key+"@"+rowV+"@ALL@"+stt.replaceAll(dfstt,util.nvl((String)defaultstatusLst.get(df-1)))+"@QTY"));
            String dfgetcolttlavg=util.nvl((String)dataDtl.get(key+"@"+rowV+"@ALL@"+stt.replaceAll(dfstt,util.nvl((String)defaultstatusLst.get(df-1)))+"@AVG"));
            if(!getcolttlavg.equals("") && !dfgetcolttlavg.equals("")){
            perdfvalAvg=util.roundTopercentage(Double.parseDouble(getcolttlavg),Double.parseDouble(dfgetcolttlavg));
            displayper=true;
            if(perdfvalAvg>0)
            styleavg = "color: #52D017";
            }
            if(!getcolttlqty.equals("") && !dfgetcolttlqty.equals("")){
            perdfvalQty=util.roundTopercentage(Double.parseDouble(getcolttlqty),Double.parseDouble(dfgetcolttlqty));
            if(perdfvalQty>0)
            styleqty = "color: #52D017";
            }
            break;
        }
    }
    }
    }
    %>
  <td style="<%=style%>"><table class="wdth" cellpadding="0" cellspacing="4"><tr>	
  <td align="center" style="cursor: pointer;" onclick="genericDtl('genericReport.do?method=loadDtl&key=<%=key%>@<%=rowV%>@ALL@<%=stt%>')" title="Click To See Details">
  <span id="<%=key%>@TTLCOLQTY@<%=rownum%>@<%=sti%>" style="">
  <%=getcolttlqty%>
    <%if(!perQTY.equals("") && displayper){%>
    <br>
    <span style="<%=styleqty%>"><b><%=perdfvalQty%></b></span>
    <%}%>
  </span></td>
   <td align="center"><span id="<%=key%>@TTLCOLCTS@<%=rownum%>@<%=sti%>" style="display:none; " class="BlueTxt"><%=getcolttlcts%></span></td>
    </tr><tr>
    <td align="center"><span id="<%=key%>@TTLCOLAVG@<%=rownum%>@<%=sti%>" style="display:none; "><%=getcolttlavg%>
    <%if(!perAVG.equals("") && displayper){%>
    <br>
     <span style="<%=styleavg%>"><b><%=perdfvalAvg%></b></span>
    <%}%>
    </span></td>
     <td align="center"><span id="<%=key%>@TTLCOLRAP@<%=rownum%>@<%=sti%>" style=" display:none; " class="BlueTxt"><%=getcolttlrap%></span></td>
      </tr><tr>
<%pageList=(ArrayList)pageDtl.get("SHOW");
if(pageList!=null && pageList.size() >0){
for(int j=0;j<pageList.size();j++){
pageDtlMap=(HashMap)pageList.get(j);
fld_ttl=(String)pageDtlMap.get("fld_ttl");
if(!fld_ttl.equals("QTY") && !fld_ttl.equals("CTS") && !fld_ttl.equals("AVG") && !fld_ttl.equals("RAP")){
 String getcolttlshowprp =" ";
 if(fld_ttl.equals("SVPD"))
 getcolttlshowprp=svpd;
 else if(fld_ttl.equals("MSD"))
 getcolttlshowprp=msd;
 else
 getcolttlshowprp=util.nvl((String)dataDtl.get(key+"@"+rowV+"@ALL@"+stt+"@"+fld_ttl));
  String blue="";
 if(j%2!=0)
 blue=" class=\"BlueTxt\"";
%>
 <td align="center"><span id="<%=key%>@TTLCOL<%=fld_ttl%>@<%=rownum%>@<%=sti%>" style="display:none; " <%=blue%>><%=getcolttlshowprp%></span></td>
 <%if(j%2!=0){%>
</tr><tr> 
<%}}}}%> 
</tr></table></td>
   <td class="<%=rightbrdr%>"></td>
  <%} else{%>
  <td style="<%=style%>"><table class="wdth" cellpadding="0" cellspacing="4"><tr>	
  <td align="center" style="cursor: pointer;" onclick="genericDtl('genericReport.do?method=loadDtl&key=<%=key%>@<%=rowV%>@ALL@<%=stt%>')" title="Click To See Details">
  <span id="<%=key%>@TTLCOLQTY@<%=rownum%>@<%=sti%>" style="display:none; ">
  </span></td>
   <td align="center"><span id="<%=key%>@TTLCOLCTS@<%=rownum%>@<%=sti%>" style="display:none; "></span></td>
    </tr><tr>
    <td align="center"><span id="<%=key%>@TTLCOLAVG@<%=rownum%>@<%=sti%>" style="display:none; "></span></td>
    <td align="center"><span id="<%=key%>@TTLCOLRAP@<%=rownum%>@<%=sti%>" style="display:none; "></span></td>
     </tr><tr>
<%pageList=(ArrayList)pageDtl.get("SHOW");
if(pageList!=null && pageList.size() >0){
for(int j=0;j<pageList.size();j++){
pageDtlMap=(HashMap)pageList.get(j);
fld_ttl=(String)pageDtlMap.get("fld_ttl");
if(!fld_ttl.equals("QTY") && !fld_ttl.equals("CTS") && !fld_ttl.equals("AVG") && !fld_ttl.equals("RAP")){
  String blue="";
 if(j%2!=0)
 blue=" class=\"BlueTxt\"";
%>
<td align="center"><span id="<%=key%>@TTLCOL<%=fld_ttl%>@<%=rownum%>@<%=sti%>" style="display:none; " <%=blue%>></span></td>
 <%if(j%2!=0){%>
</tr><tr> 
<%}}}}%> 
</tr></table></td>
  <td class="<%=rightbrdr%>"></td>
  <%}
  }%>
  </tr>
  
  <%}}
  
  }%>
  <input type="hidden" name="loopcol" id="loopcol" value="<%=loopcol%>"/>
  
  <tr><td nowrap="nowrap">Total
   <%if(isShowgraph.equals("Y")){%>
  <A href="<%=info.getReqUrl()%>/report/barGraph.jsp?key=<%=replaceplus%>&row=ALL&col=" target="SC"  onclick="loadASSFNLPop('chart')" title="Click Here To See Bar Graph">
<img src="../images/barchart.png" border="0" id="chart"/></a>
<A href="<%=info.getReqUrl()%>/report/commonpieChart.jsp?key=<%=replaceplus%>&row=ALL&col=" target="SC"  onclick="loadASSFNLPop('chart')" title="Click Here To See Pie Chart">
<img src="../images/piechart.png" border="0" id="chart"/></a><%}%>
  </td>
  <%  
  colnum=0;
  for(int j=0;j< colList.size();j++){
    String colV = (String)colList.get(j);
    if(colListGt.contains(colV)){
    for(int st=0;st<statusLst.size();st++){
    colnum++;
    String stt=(String)statusLst.get(st);
    String getclrttlqty=util.nvl((String)dataDtl.get(key+"@ALL@"+colV+"@"+stt+"@QTY"));
    String getclrttlcts=util.nvl((String)dataDtl.get(key+"@ALL@"+colV+"@"+stt+"@CTS"));
    String getclrttlavg=util.nvl((String)dataDtl.get(key+"@ALL@"+colV+"@"+stt+"@AVG"));
    String getclrttlrap=util.nvl((String)dataDtl.get(key+"@ALL@"+colV+"@"+stt+"@RAP"));
    String getclrttlage=util.nvl((String)dataDtl.get(key+"@ALL@"+colV+"@"+stt+"@AGE"));
    String getclrttlhit=util.nvl((String)dataDtl.get(key+"@ALL@"+colV+"@"+stt+"@HIT"));
    String getclrttlvlu=util.nvl((String)dataDtl.get(key+"@ALL@"+colV+"@"+stt+"@VLU"));
    String GCLRQTYID="GCLRIDQTY@"+colnum;
    String GCLRCTSID="GCLRIDCTS@"+colnum;
    String GCLRAVGID="GCLRIDAVG@"+colnum;
    String GCLRRAPID="GCLRIDRAP@"+colnum;
    String GCLRAGEID="GCLRIDAGE@"+colnum;
    String GCLRHITID="GCLRIDHIT@"+colnum;
    String GCLRVLUID="GCLRIDVLU@"+colnum;
    String svpd="";
    String msd="";
    if(st==(statusLstsz-1))
    rightbrdr="rghtbrdrend";
    else
    rightbrdr="rghtbrdr";  
    style = "";
    if(!getclrttlqty.equals("")){
String bocolor=util.nvl((String)sttColorCodeMap.get(stt),"black");
     style = "";
     tdstyle="";
     if(statusLst.contains("SOLD") && stt.equals("MKT") && inventoryDis.equals("Y")) {
     double mktQty=Double.parseDouble(getclrttlqty);
     String soldKeyQty = key+"@ALL@"+colV+"@"+"SOLD@QTY";
     String soldQty = util.nvl2((String)dataDtl.get(soldKeyQty),"0");
     if(!soldQty.equals("")){
     perCentSold=Math.round((Double.parseDouble(soldQty)/days)*100);
     perCentSoldQty = Math.round((MIN_INVENTORY_DAYS*perCentSold)/100);
     perCentMinTol=perCentSoldQty-Math.round((perCentSoldQty/100)*TOLERANCE_MIN_PCT);
     perCentMaxTol=perCentSoldQty+Math.round((perCentSoldQty/100)*TOLERANCE_MAX_PCT);
     if(mktQty>=perCentMinTol && mktQty<=perCentMaxTol)
     style = "border: 3px solid #FFFF00;";
     if(mktQty<perCentMinTol)
     style = "border: 3px solid #006600;";
     if(mktQty > perCentMaxTol)
     style = "border: 3px solid #FF7676;";
     }
     }
     if(statusLst.contains("MKT") && statusLst.contains("SOLD") && (stt.equals("SOLD") || stt.equals("MKT")) && inventoryDis.equals("Y")) {
     String mktKeyVlu = key+"@ALL@"+colV+"@MKT@VLU";
     String mktVlu = util.nvl2((String)dataDtl.get(mktKeyVlu),"0");
     String soldKeyVlu = key+"@ALL@"+colV+"@SOLD@VLU";
     String soldVlu = util.nvl2((String)dataDtl.get(soldKeyVlu),"0");
     if(!soldVlu.equals("") && !soldVlu.equals("0")){
     svpd=util.nvl(String.valueOf(Math.round(Double.parseDouble(soldVlu)/days)),"0");
     if(!mktVlu.equals("") && !mktVlu.equals("0") && !svpd.equals("0"))
     msd=util.nvl(String.valueOf(Math.round(Double.parseDouble(mktVlu)/Double.parseDouble(svpd))),"0");
     if(svpd.equals("0"))
     svpd="";
     if(stt.equals("SOLD"))
     msd="";
     if(stt.equals("MKT"))
     svpd="";
     }
     }
     boolean displayper=false;
     String styleqty = "color: red";
     String styleavg = "color: red";
     if(defaultstatusLstsz>1){
     String firststt=util.nvl((String)defaultstatusLst.get(0));
     perdfvalAvg=0;perdfvalQty=0;
    if(stt.indexOf(firststt)<0){
    for(int df=1;df<defaultstatusLst.size();df++){
    String dfstt=util.nvl((String)defaultstatusLst.get(df));
        if(stt.indexOf(dfstt)>=0){
            String dfgetclrttlqty=util.nvl((String)dataDtl.get(key+"@ALL@"+colV+"@"+stt.replaceAll(dfstt,util.nvl((String)defaultstatusLst.get(df-1)))+"@QTY"));
            String dfgetclrttlqtyavg=util.nvl((String)dataDtl.get(key+"@ALL@"+colV+"@"+stt.replaceAll(dfstt,util.nvl((String)defaultstatusLst.get(df-1)))+"@AVG"));
            if(!getclrttlavg.equals("") && !dfgetclrttlqtyavg.equals("")){
            perdfvalAvg=util.roundTopercentage(Double.parseDouble(getclrttlavg),Double.parseDouble(dfgetclrttlqtyavg));

            displayper=true;
            if(perdfvalAvg>0)
            styleavg = "color: #52D017";
            }
            if(!getclrttlqty.equals("") && !dfgetclrttlqty.equals("")){
            perdfvalQty=util.roundTopercentage(Double.parseDouble(getclrttlqty),Double.parseDouble(dfgetclrttlqty));
            if(perdfvalQty>0)
            styleqty = "color: #52D017";
            }
            break;
        }
    }
    }
    }
    %>
  <td style="<%=style%>"><table class="wdth" cellpadding="0" cellspacing="4"><tr>	
 <td align="center"  style="cursor: pointer;" onclick="genericDtl('genericReport.do?method=loadDtl&key=<%=key%>@ALL@<%=colV%>@<%=stt%>')" title="Click To See Details">
 <span id="<%=key%>@<%=GCLRQTYID%>" style="">
 <%=getclrttlqty%>
    <%if(!perQTY.equals("") && displayper){%>
    <br>
    <span style="<%=styleqty%>"><b><%=perdfvalQty%></b></span>
    <%}%>
 </span></td>
 <td align="center"><span id="<%=key%>@<%=GCLRCTSID%>" style="display:none;" class="BlueTxt"><%=getclrttlcts%></span></td>
  </tr><tr>
 <td align="center" ><span id="<%=key%>_<%=GCLRAVGID%>" style="display:none;"><%=getclrttlavg%>
    <%if(!perAVG.equals("") && displayper){%>
    <br>
    <span style="<%=styleavg%>"><b><%=perdfvalAvg%></b></span>
    <%}%>
 
 </span></td>
 <td align="center" ><span id="<%=key%>@<%=GCLRRAPID%>" style="display:none;" class="BlueTxt"><%=getclrttlrap%></span></td>
  </tr><tr>
<%pageList=(ArrayList)pageDtl.get("SHOW");
if(pageList!=null && pageList.size() >0){
for(int pp=0;pp<pageList.size();pp++){
pageDtlMap=(HashMap)pageList.get(pp);
fld_ttl=(String)pageDtlMap.get("fld_ttl");
if(!fld_ttl.equals("QTY") && !fld_ttl.equals("CTS") && !fld_ttl.equals("AVG") && !fld_ttl.equals("RAP")){
 String GCLRSHOWPRPID="GCLRID"+fld_ttl+"@"+colnum;
  String getclrttlshowprp =" ";
 if(fld_ttl.equals("SVPD"))
 getclrttlshowprp=svpd;
 else if(fld_ttl.equals("MSD"))
 getclrttlshowprp=msd;
 else
 getclrttlshowprp=util.nvl((String)dataDtl.get(key+"@ALL@"+colV+"@"+stt+"@"+fld_ttl));
   String blue="";
 if(pp%2!=0)
 blue=" class=\"BlueTxt\"";
%>
 <td align="center"><span id="<%=key%>@<%=GCLRSHOWPRPID%>" style="display:none;" <%=blue%>><%=getclrttlshowprp%></span></td>
 <%if(pp%2!=0){%>
</tr><tr> 
<%}}}}%> 
 </tr></table></td>
   <td class="<%=rightbrdr%>"></td>
  <%}else{%>
  <td style="<%=style%>"><table class="wdth" cellpadding="0" cellspacing="4"><tr>	
  <td align="center"><span id="<%=key%>@<%=GCLRQTYID%>" style="display:none;"></span></td>
  <td align="center"><span id="<%=key%>@<%=GCLRCTSID%>" style="display:none;"></span></td>
   </tr><tr>
 <td align="center"><span id="<%=key%>@<%=GCLRAVGID%>" style="display:none;"></span></td>
 <td align="center"><span id="<%=key%>@<%=GCLRRAPID%>" style="display:none;"></span></td>
  </tr><tr>
<%pageList=(ArrayList)pageDtl.get("SHOW");
if(pageList!=null && pageList.size() >0){
for(int pp=0;pp<pageList.size();pp++){
pageDtlMap=(HashMap)pageList.get(pp);
fld_ttl=(String)pageDtlMap.get("fld_ttl");
if(!fld_ttl.equals("QTY") && !fld_ttl.equals("CTS") && !fld_ttl.equals("AVG") && !fld_ttl.equals("RAP")){
 String GCLRSHOWPRPID="GCLRID"+fld_ttl+"_"+colnum;
   String blue="";
 if(pp%2!=0)
 blue=" class=\"BlueTxt\"";
%>
  <td align="center"><span id="<%=key%>@<%=GCLRSHOWPRPID%>" style="display:none;"></span></td>
 <%if(pp%2!=0){%>
</tr><tr> 
<%}}}}%>  
  </tr></table></td>
  <td class="<%=rightbrdr%>"></td>
  <%
  }}}}
   for(int st=0;st<statusLst.size();st++){
   String stt=(String)statusLst.get(st);
   String getttlqty=util.nvl((String)getGrandTotalList.get(key+"@"+stt+"@QTY"));
    String getttlcts=util.nvl((String)getGrandTotalList.get(key+"@"+stt+"@CTS"));
    String getttlavg=util.nvl((String)getGrandTotalList.get(key+"@"+stt+"@AVG"));
    String getttlrap=util.nvl((String)getGrandTotalList.get(key+"@"+stt+"@RAP"));
    String getttlage=util.nvl((String)getGrandTotalList.get(key+"@"+stt+"@AGE"));
    String getttlhit=util.nvl((String)getGrandTotalList.get(key+"@"+stt+"@HIT"));
    String getttlvlu=util.nvl((String)getGrandTotalList.get(key+"@"+stt+"@VLU"));
    String GQTYID="GIDQTY@"+colnum+"@"+st;
    String GCTSID="GIDCTS@"+colnum+"@"+st;
    String GAVGID="GIDAVG@"+colnum+"@"+st;
    String GRAPID="GIDRAP@"+colnum+"@"+st;
    String GAGEID="GIDAGE@"+colnum+"@"+st;
    String GHITID="GIDHIT@"+colnum+"@"+st;
    String GVLUID="GIDVLU@"+colnum+"@"+st;
String bocolor=util.nvl((String)sttColorCodeMap.get(stt),"black");
style = "";
    String svpd="";
    String msd="";
    if(st==(statusLstsz-1))
    rightbrdr="rghtbrdrend";
    else
    rightbrdr="rghtbrdr"; 
     if(!getttlqty.equals("")){
     tdstyle="";
      if(statusLst.contains("SOLD") && stt.equals("MKT") && inventoryDis.equals("Y")) {
     double mktQty=Double.parseDouble(getttlqty);
     String soldKeyQty = key+"@SOLD@QTY";
     String soldQty = util.nvl2((String)getGrandTotalList.get(soldKeyQty),"0");
     if(!soldQty.equals("")){
     perCentSold=Math.round((Double.parseDouble(soldQty)/days)*100);
     perCentSoldQty = Math.round((MIN_INVENTORY_DAYS*perCentSold)/100);
     perCentMinTol=perCentSoldQty-Math.round((perCentSoldQty/100)*TOLERANCE_MIN_PCT);
     perCentMaxTol=perCentSoldQty+Math.round((perCentSoldQty/100)*TOLERANCE_MAX_PCT);
     if(mktQty>=perCentMinTol && mktQty<=perCentMaxTol)
     style = "border: 3px solid #FFFF00;";
     if(mktQty<perCentMinTol)
     style = "border: 3px solid #006600;";
     if(mktQty > perCentMaxTol)
     style = "border: 3px solid #FF7676;";
     }
     }
     
     if(statusLst.contains("MKT") && statusLst.contains("SOLD") && (stt.equals("SOLD") || stt.equals("MKT")) && inventoryDis.equals("Y")) {
     String mktKeyVlu = key+"@MKT@VLU";
     String mktVlu = util.nvl2((String)getGrandTotalList.get(mktKeyVlu),"0");
     String soldKeyVlu = key+"@SOLD@VLU";
     String soldVlu = util.nvl2((String)getGrandTotalList.get(soldKeyVlu),"0");
     if(!soldVlu.equals("") && !soldVlu.equals("0")){
     svpd=util.nvl(String.valueOf(Math.round(Double.parseDouble(soldVlu)/days)),"0");
     if(!mktVlu.equals("") && !mktVlu.equals("0") && !svpd.equals("0"))
     msd=util.nvl(String.valueOf(Math.round(Double.parseDouble(mktVlu)/Double.parseDouble(svpd))),"0");
     if(svpd.equals("0"))
     svpd="";
     if(stt.equals("SOLD"))
     msd="";
     if(stt.equals("MKT"))
     svpd="";
     }
     }
          boolean displayper=false;
     String styleqty = "color: red";
     String styleavg = "color: red";
     if(defaultstatusLstsz>1){
     String firststt=util.nvl((String)defaultstatusLst.get(0));
     perdfvalAvg=0;perdfvalQty=0;
    if(stt.indexOf(firststt)<0){
    for(int df=1;df<defaultstatusLst.size();df++){
    String dfstt=util.nvl((String)defaultstatusLst.get(df));
        if(stt.indexOf(dfstt)>=0){
            String dfgetttlqty=util.nvl((String)getGrandTotalList.get(key+"@"+stt.replaceAll(dfstt,util.nvl((String)defaultstatusLst.get(df-1)))+"@QTY"));
            String dfgetttlavg=util.nvl((String)getGrandTotalList.get(key+"@"+stt.replaceAll(dfstt,util.nvl((String)defaultstatusLst.get(df-1)))+"@AVG"));
            if(!getttlavg.equals("") && !dfgetttlavg.equals("")){
            perdfvalAvg=util.roundTopercentage(Double.parseDouble(getttlavg),Double.parseDouble(dfgetttlavg));
            displayper=true;
            if(perdfvalAvg>0)
            styleavg = "color: #52D017";
            }
            if(!getttlqty.equals("") && !dfgetttlqty.equals("")){
            perdfvalQty=util.roundTopercentage(Double.parseDouble(getttlqty),Double.parseDouble(dfgetttlqty));
            if(perdfvalQty>0)
            styleqty = "color: #52D017";
            }
            break;
        }
    }
    }
    }
   %>
  <td style="<%=style%>"><table class="wdth" cellpadding="0" cellspacing="4"><tr>	
  <td align="center" style="cursor: pointer;" onclick="genericDtl('genericReport.do?method=loadDtl&key=<%=key%>@ALL@ALL@<%=stt%>')" title="Click To See Details">
  <span id="<%=key%>@<%=GQTYID%>" style=""> <%=getttlqty%>
      <%if(!perQTY.equals("") && displayper){%>
    <br>
    <span style="<%=styleqty%>"><b><%=perdfvalQty%></b></span>
    <%}%>
  
  </span></td>
  <td align="center"><span id="<%=key%>@<%=GCTSID%>" style="display:none;" class="BlueTxt"><%=getttlcts%></span></td>
   </tr><tr>
  <td align="center"><span id="<%=key%>@<%=GAVGID%>" style="display:none;"><%=getttlavg%>
    <%if(!perAVG.equals("") && displayper){%>
    <br>
    <span style="<%=styleavg%>"><b><%=perdfvalAvg%></b></span>
    <%}%>
  </span></td>
  <td align="center" <%=tdstyle%>><span id="<%=key%>@<%=GRAPID%>" style="display:none;" class="BlueTxt"><%=getttlrap%></span></td>
   </tr><tr>
<%pageList=(ArrayList)pageDtl.get("SHOW");
if(pageList!=null && pageList.size() >0){
for(int pp=0;pp<pageList.size();pp++){
pageDtlMap=(HashMap)pageList.get(pp);
fld_ttl=(String)pageDtlMap.get("fld_ttl");
if(!fld_ttl.equals("QTY") && !fld_ttl.equals("CTS") && !fld_ttl.equals("AVG") && !fld_ttl.equals("RAP")){
 String GSHOWPRPID="GID"+fld_ttl+"@"+colnum+"@"+st;
 String getttlshowprp =" ";
 if(fld_ttl.equals("SVPD"))
 getttlshowprp=svpd;
 else if(fld_ttl.equals("MSD"))
 getttlshowprp=msd;
 else
 getttlshowprp=util.nvl((String)getGrandTotalList.get(key+"@"+stt+"@"+fld_ttl));
  String blue="";
 if(pp%2!=0)
 blue=" class=\"BlueTxt\"";
%>
 <td align="center"><span id="<%=key%>@<%=GSHOWPRPID%>" style="display:none;" <%=blue%>><%=getttlshowprp%></span></td>
 <%if(pp%2!=0){%>
</tr><tr> 
<%}}}}%>     
  </tr></table></td>
   <td class="<%=rightbrdr%>"></td>
  <%}else{%>
  <td style="<%=style%>"><table class="wdth" cellpadding="0" cellspacing="4"><tr>	
    <td align="center"><span id="<%=key%>@<%=GQTYID%>" style=""></span></td>
  <td align="center"><span id="<%=key%>@<%=GCTSID%>" style=" display:none;"></span></td>
   </tr><tr>
 <td align="center"><span id="<%=key%>@<%=GAVGID%>" style="display:none;"></span></td>
 <td align="center"><span id="<%=key%>@<%=GRAPID%>" style=" display:none;"></span></td>   
  </tr><tr>
<%pageList=(ArrayList)pageDtl.get("SHOW");
if(pageList!=null && pageList.size() >0){
for(int pp=0;pp<pageList.size();pp++){
pageDtlMap=(HashMap)pageList.get(pp);
fld_ttl=(String)pageDtlMap.get("fld_ttl");
if(!fld_ttl.equals("QTY") && !fld_ttl.equals("CTS") && !fld_ttl.equals("AVG") && !fld_ttl.equals("RAP")){
 String GSHOWPRPID="GID"+fld_ttl+"@"+colnum+"@"+st;
%>
 <td align="center"><span id="<%=key%>@<%=GSHOWPRPID%>" style="display:none;" class="redTxt"></span></td>
 <%if(pp%2!=0){%>
</tr><tr> 
<%}}}}%> 
</tr></table></td>
   <td class="<%=rightbrdr%>"></td>
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
  </div>
  </body>
</html>