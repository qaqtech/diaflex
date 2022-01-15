<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import="java.util.ArrayList,java.util.ArrayList,java.util.HashMap,ft.com.*, java.sql.*, java.util.Enumeration, java.util.logging.Level,java.util.Set,java.util.Iterator"%>
<jsp:useBean id="info" class="ft.com.InfoMgr" scope="session" />
<jsp:useBean id="util" class="ft.com.JspUtil" scope="page" />
<%@taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>
 
<html>
  <head>
  <title>T20 Report</title>
    <script language="javaScript" type="text/javascript" src="<%=info.getReqUrl()%>/scripts/ajax.js?v=<%=info.getJsVersion()%>"></script>
    <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/bse.css?v=<%=info.getJsVersion()%>"/>
    <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/style.css?v=<%=info.getJsVersion()%>"/>
    <script src="<%=info.getReqUrl()%>/jquery/jquery.min.js"></script>
        <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/bse.js?v=<%=info.getJsVersion()%> " > </script>
        <script type="text/javascript" src="<%=info.getReqUrl()%>/jquery/jquery-freezar.min.js"></script>
         <script language="javaScript" type="text/javascript" src="<%=info.getReqUrl()%>/jquery/jquery.freezeheader.js?v=<%=info.getJsVersion()%>"></script>
      
      <link  rel="stylesheet" href='<%=info.getReqUrl()%>/css/select2.min.css?v=12920%>'>
      <link rel="stylesheet" href="<%=info.getReqUrl()%>/css/select2.css?v=19220">
<script src="<%=info.getReqUrl()%>/jquery/select2.min.js?v=129020"></script>
<script src="<%=info.getReqUrl()%>/jquery/select2.js?v=129020" type="text/javascript"></script>
    
    <script type="text/javascript">
   $(document).ready(function () {
	    $("#table2").freezeHeader({ 'height': '500px' });
    })
 </script>
  </head>
        <%String logId=String.valueOf(info.getLogId());
        String onfocus="cook('"+logId+"')";
        ArrayList  t20TopList= (session.getAttribute("t20TopList") == null)?new ArrayList():(ArrayList)session.getAttribute("t20TopList");
        ArrayList  showprpLst= (request.getAttribute("showprpLst") == null)?new ArrayList():(ArrayList)request.getAttribute("showprpLst");
        
        String topListByPeriod=util.nvl((String)request.getAttribute("topListByPeriod"));
        String hardCodeGrpKey=util.nvl((String)request.getAttribute("hardCodeGrpKey"));
        String hardCodeGrpVal=util.nvl((String)request.getAttribute("hardCodeGrpVal"));
        String gridby=util.nvl((String)request.getAttribute("gridby"));
        String crtwtfor=util.nvl((String)request.getAttribute("crtwtfor"));
        String ratioby=util.nvl((String)request.getAttribute("ratioby"));
        String fmt=util.nvl((String)request.getAttribute("fmt"));
        int  topcount=(Integer)request.getAttribute("topcount");
        String vluLimit=util.nvl((String)request.getAttribute("vluLimit"));
        String avgPct=util.nvl((String)request.getAttribute("avgPct"));
        int  day=(Integer)request.getAttribute("day");
        String displayColumn=util.nvl((String)request.getAttribute("displayColumn"));
        String link="screen.do?method=loadT20Grid&gridby="+gridby+"&crtwtfor="+crtwtfor+"&ratioby="+ratioby+"&avgPct="+avgPct+"&vluLimit="+vluLimit+"&fmt="+fmt+"&topcount="+topcount+"&day="+day+"&displayColumn="+displayColumn+"&topListByPeriod=";
        String pktlink="screen.do?method=pktDtlt20&gridby="+gridby+"&crtwtfor="+crtwtfor+"&fmt="+fmt+"&soldP=";
        HashMap redSortedPeriodMap= ((HashMap)request.getAttribute("redSortedPeriodMap") == null)?new HashMap():(HashMap)request.getAttribute("redSortedPeriodMap");
        HashMap greenSortedPeriodMap= ((HashMap)request.getAttribute("greenSortedPeriodMap") == null)?new HashMap():(HashMap)request.getAttribute("greenSortedPeriodMap");
        HashMap dataDtl= ((HashMap)request.getAttribute("dataDtl") == null)?new HashMap():(HashMap)request.getAttribute("dataDtl");
        HashMap t20Sale= ((HashMap)session.getAttribute("t20Sale") == null)?new HashMap():(HashMap)session.getAttribute("t20Sale");
        ArrayList  t20SalePeriodList= (session.getAttribute("t20List") == null)?new ArrayList():(ArrayList)session.getAttribute("t20List");
        ArrayList  dataValidRedLst= (request.getAttribute("dataValidRedLst") == null)?new ArrayList():(ArrayList)request.getAttribute("dataValidRedLst");
        ArrayList  dataValidGreenLst= (request.getAttribute("dataValidGreenLst") == null)?new ArrayList():(ArrayList)request.getAttribute("dataValidGreenLst");
        ArrayList  vwPrpList= (session.getAttribute("T20") == null)?new ArrayList():(ArrayList)session.getAttribute("T20");
        HashMap mprp = info.getMprp();
        int t20SalePeriodListsz=t20SalePeriodList.size();
        HashMap allPageDtl= (info.getPageDetails() == null)?new HashMap():(HashMap)info.getPageDetails();
        HashMap pageDtl=(HashMap)allPageDtl.get("T20");
        ArrayList pageList=new ArrayList();
        HashMap pageDtlMap=new HashMap();
        String fld_nme="",fld_ttl="",val_cond="",lov_qry="",dflt_val="",fld_typ="",form_nme="",flg1="";
          ArrayList redkeyList=new ArrayList();
          if(redSortedPeriodMap!=null && redSortedPeriodMap.size()!=0){
          HashMap redMap= ((HashMap)redSortedPeriodMap.get("P"+topListByPeriod) == null)?new HashMap():(HashMap)redSortedPeriodMap.get("P"+topListByPeriod);
          Set set = redMap.keySet();
          Iterator iter = set.iterator();
          while (iter.hasNext()) {
          redkeyList.add((String)iter.next());
          }
          }
          int redkeyListsz= redkeyList.size();
          
          ArrayList greenkeyList=new ArrayList();
          if(greenSortedPeriodMap!=null && greenSortedPeriodMap.size()!=0){
          HashMap greenMap= ((HashMap)greenSortedPeriodMap.get("P"+topListByPeriod) == null)?new HashMap():(HashMap)greenSortedPeriodMap.get("P"+topListByPeriod);
          Set set = greenMap.keySet();
          Iterator iter = set.iterator();
          while (iter.hasNext()) {
          greenkeyList.add((String)iter.next());
          }
          }
          int greenkeyListsz= greenkeyList.size();
          
          int fnlSzLoop=0;
          if(redkeyListsz > greenkeyListsz)
          fnlSzLoop=redkeyListsz;
          else
          fnlSzLoop=greenkeyListsz;
          int tempredkeyListsz=redkeyListsz-1;
          int tempgreenkeyListsz=greenkeyListsz-1;
          int mktcolspan=5;
          int soldcolspan=7;
          boolean dis=false;
          if(ratioby.equals("MKTVSNEWMKT") ||  ratioby.equals("OLDVSNEW"))
          soldcolspan=6;
          if(!displayColumn.equals("QTY") && !displayColumn.equals("CTS")  && !displayColumn.equals("AVG") && !displayColumn.equals("VLU")){
          mktcolspan++;
          soldcolspan++;
          dis=true;
          }
          int srno=1;
          String mktsrtColor="background-color:#ffffc7";
          String soldColor="background-color:#e7f5fe";
          HashMap mktDsc=new HashMap();
          mktDsc.put("MKT","Mkt");
          mktDsc.put("OLDMKT","Old Mkt");
          mktDsc.put("NEWMKT","New Mkt");
          mktDsc.put("OLD","Old");
          mktDsc.put("NEW","New");
        %>
 <body onfocus="<%=onfocus%>">
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
  <img src="../images/line.jpg" border="0" /></td><td valign="middle"> <span class="pgHed">T20</span> </td>
</tr></table>
  </td>
  </tr>
<tr><td valign="top" class="hedPg">
<html:form action="/dashboard/screen.do?method=loadT20Grid"  method="POST">
  <table  class="grid1small">
   <tr>
   <th colspan="19" align="center">T20 Search</th>
   </tr>
   <tr>
   <td nowrap="nowrap">Format </td>
   <td>
   <html:select  property="fmtLst" name="userrightsform" styleId="fmtLst" styleClass="taggingSelect2" multiple="true"> 
    <%for(int j=0;j<vwPrpList.size();j++){
    String lprp=(String)vwPrpList.get(j);
    String lprptyp = util.nvl((String)mprp.get(lprp+"T"));
    String lprpDsc = util.nvl((String)mprp.get(lprp+"D"));
    if(lprptyp.equals("C")){%>
    <html:option value="<%=lprp%>" ><%=lprpDsc%></html:option>
    <%}}%>
    </html:select> 
   </td>
   <td>Top</td>
   <td>
    <html:select  property="value(topcount)" name="userrightsform" styleId="topcount" > 
    <%for(int j=0;j<t20TopList.size();j++){
    String topcountS=(String)t20TopList.get(j);%>
    <html:option value="<%=topcountS%>" ><%=topcountS%></html:option>
    <%}%>
    </html:select> 
   </td>
   <td nowrap="nowrap">View By</td>
   <td>
   <%pageList=(ArrayList)pageDtl.get("GROUPBY");
    if(pageList!=null && pageList.size() >0){%>
    <html:select  property="value(gridby)" name="userrightsform" styleId="gridby"> 
    <%for(int j=0;j<pageList.size();j++){
    pageDtlMap=(HashMap)pageList.get(j);
    fld_ttl=(String)pageDtlMap.get("fld_ttl");
    fld_nme=(String)pageDtlMap.get("fld_nme"); 
    %>
    <html:option value="<%=fld_nme%>" ><%=fld_ttl%></html:option>
    <%}%>
    </html:select> 
    <%}%>  
   </td>
   <td  nowrap="nowrap">T20 By</td>
   <td>
    <html:select  property="value(displayColumn)" name="userrightsform" styleId="displayColumn" > 
    <%pageList=(ArrayList)pageDtl.get("SHOW");
    if(pageList!=null && pageList.size() >0){%>
    <%for(int j=0;j<pageList.size();j++){
    pageDtlMap=(HashMap)pageList.get(j);
    fld_ttl=(String)pageDtlMap.get("fld_ttl");
    fld_nme=(String)pageDtlMap.get("fld_nme"); %>
    <html:option value="<%=fld_nme%>" ><%=fld_ttl%></html:option>
    <%}%>
    <%}%>  
    <%for(int j=0;j<showprpLst.size();j++){
    String showprp=(String)showprpLst.get(j);%>
    <html:option value="<%=showprp%>" ><%=showprp%></html:option>
    <%}%>
    </html:select> 
   </td>
   <td nowrap="nowrap">Ratio By</td>
   <td>
   <%pageList=(ArrayList)pageDtl.get("RATIOBY");
    if(pageList!=null && pageList.size() >0){%>
    <html:select  property="value(ratioby)" name="userrightsform" styleId="ratioby"> 
    <%for(int j=0;j<pageList.size();j++){
    pageDtlMap=(HashMap)pageList.get(j);
    fld_ttl=(String)pageDtlMap.get("fld_ttl");
    fld_nme=(String)pageDtlMap.get("fld_nme"); 
    %>
    <html:option value="<%=fld_nme%>" ><%=fld_ttl%></html:option>
    <%}%>
    </html:select> 
    <%}%>  
   </td>
   <td nowrap="nowrap" title="To Limit Mkt Vlu or Sold Vlu must be Greater than Limit">Vlu Limit</td>
   <td><html:text property="value(vluLimit)" size="8" name="userrightsform" styleId="vluLimit"/></td>
   <td nowrap="nowrap" title="Avg Comp">Avg Diff %</td>
   <td><html:text property="value(avgPct)" size="3" name="userrightsform" styleId="avgPct"/></td>
   <td nowrap="nowrap">Carat Range</td>
   <td>
   <%pageList=(ArrayList)pageDtl.get("CRTWTFOR");
    if(pageList!=null && pageList.size() >0){%>
    <html:select  property="value(crtwtfor)" name="userrightsform" styleId="crtwtfor"> 
    <%for(int j=0;j<pageList.size();j++){
    pageDtlMap=(HashMap)pageList.get(j);
    fld_ttl=(String)pageDtlMap.get("fld_ttl");
    fld_nme=(String)pageDtlMap.get("fld_nme"); 
    %>
    <html:option value="<%=fld_nme%>" ><%=fld_ttl%></html:option>
    <%}%>
    </html:select> 
    <%}%>  
   </td>

   <%pageList=(ArrayList)pageDtl.get("DAY");
    if(pageList!=null && pageList.size() >0){%>
   <td nowrap="nowrap" title="No Of Days">No Of Days</td>
   <td>
    <html:select  property="value(day)" name="userrightsform" styleId="day"> 
    <%for(int j=0;j<pageList.size();j++){
    pageDtlMap=(HashMap)pageList.get(j);
    fld_ttl=(String)pageDtlMap.get("fld_ttl");
    fld_nme=(String)pageDtlMap.get("fld_nme"); 
    %>
    <html:option value="<%=fld_nme%>" ><%=fld_ttl%></html:option>
    <%}%>
    </html:select> 
    </td>
    <%}%>  
   <td><html:submit property="value(filter)" value="Filter" styleClass="submit" /></td>
   </tr>
   </table>
</html:form>
</td>
</tr>
<tr>
<td valign="top" class="hedPg">
<table width="100%"  cellpadding="0" cellspacing="0" style="padding-top:10px">
<tr>
<td valign="top">
<table class="grid1small"  id="table2">
<thead>
<tr>
<th>Sr No</th><th>Top</th><th colspan="<%=mktcolspan%>"><%=util.nvl((String)mktDsc.get(hardCodeGrpVal))%></th>
<%for(int j=0;j<t20SalePeriodListsz;j++){
String soldP=(String)t20SalePeriodList.get(j);
String style="";
if(soldP.equals(topListByPeriod))
style="font-weight: bold;color:Blue;";
%>
<th colspan="<%=soldcolspan%>" style="<%=style%>">&nbsp;
<%=util.nvl((String)t20Sale.get(soldP),util.nvl((String)mktDsc.get(soldP)))%>
<a href="<%=link%><%=soldP%>"><img src="../images/sorttop.png" height="12px" width="15px" title="Sort Data By Clicked Period"/></a>
</th>
<%}%>
</tr>
<tr>
<td></td><td></td><td style="<%=mktsrtColor%>">QTY</td><td style="<%=mktsrtColor%>">CTS</td><td style="<%=mktsrtColor%>">AVG</td>
<td style="<%=mktsrtColor%>">VLU</td><td style="<%=mktsrtColor%>">AGE</td>
<%if(dis){%>
<td><%=displayColumn%></td>
<%}%>
<%for(int j=0;j<t20SalePeriodListsz;j++){
String soldP=(String)t20SalePeriodList.get(j);
String localsoldColor=mktsrtColor;
if(!topListByPeriod.equals(soldP))
localsoldColor=soldColor;
%>
<td style="<%=localsoldColor%>">RNK</td><td style="<%=localsoldColor%>">QTY</td>
<td style="<%=localsoldColor%>">CTS</td><td style="<%=localsoldColor%>">AVG</td>
<td style="<%=localsoldColor%>">VLU</td><td style="<%=localsoldColor%>">AGE</td>
<%if(!ratioby.equals("MKTVSNEWMKT") &&  !ratioby.equals("OLDVSNEW")){%>
<td style="<%=localsoldColor%>">MSD</td>
<%}if(dis){%>
<td><%=displayColumn%></td>
<%}%>
<%}%>
</tr>
</thead>
<tbody>
<%for(int loop=0;loop<greenkeyListsz;loop++){
String greenKey="";
String mktgreenkey="";
  float ratio = (float) loop / (float) greenkeyListsz;
  int redcolor = (int) (251 * ratio + 0 * (1 - ratio));
  int greencolor = (int) (251 * ratio + 251 * (1 - ratio));
  int bluecolor = (int) (251 * ratio + 0* (1 - ratio));
  String style="background-color:rgb("+redcolor+","+greencolor+","+bluecolor+")";
%>
<tr>
<td align="right"><%=srno++%></td>
<td  style="<%=style%>" nowrap="nowrap">
<%
greenKey=(String)greenkeyList.get(loop);
mktgreenkey=greenKey.replaceAll("@P"+topListByPeriod, "@"+hardCodeGrpVal);
String mktAvg=util.nvl((String)dataDtl.get(mktgreenkey.replaceAll("@"+displayColumn, "@AVG")));
double rang=Math.round((Double.parseDouble(mktAvg)/100.0)* Double.parseDouble(avgPct));
double mktMinAvg=Double.parseDouble(mktAvg)-rang;
double mktMaxAvg=Double.parseDouble(mktAvg)+rang;
%>
<%=greenKey.replaceAll("@"+displayColumn,"").replaceAll("@P"+topListByPeriod, "").replaceAll("@", " ")%>
</td>
<td align="right" style="<%=mktsrtColor%>">
<a href="<%=pktlink%>&key=<%=greenKey.replaceAll("\\+", "%2B").replaceAll("\\-", "%2D")%>&hardCodeGrp=<%=hardCodeGrpVal%>&hardCodeGrpKey=<%=hardCodeGrpKey%>" target="_blank">
<%=util.nvl((String)dataDtl.get(mktgreenkey.replaceAll("@"+displayColumn, "@QTY")))%>
</a>
</td>
<td align="right" style="<%=mktsrtColor%>"><%=util.nvl((String)dataDtl.get(mktgreenkey.replaceAll("@"+displayColumn, "@CTS")))%></td>
<td align="right" style="<%=mktsrtColor%>"><%=mktAvg%></td>
<td align="right" title="Vlu / 100000" style="<%=mktsrtColor%>"><%=util.compressVlu(util.nvl((String)dataDtl.get(mktgreenkey.replaceAll("@"+displayColumn, "@VLU"))),"100000")%></td>
<td align="right" style="<%=mktsrtColor%>"><%=util.nvl((String)dataDtl.get(mktgreenkey.replaceAll("@"+displayColumn, "@AGE")))%></td>
<%if(dis){%>
<td align="right" style="<%=mktsrtColor%>"><%=util.nvl((String)dataDtl.get(mktgreenkey))%></td>
<%}%>
<%for(int s=0;s<t20SalePeriodListsz;s++){
String soldP=(String)t20SalePeriodList.get(s);
String soldPgreenkey=greenKey.replaceAll("@P"+topListByPeriod, "@P"+soldP);
String localsoldColor=mktsrtColor;
if(!topListByPeriod.equals(soldP))
localsoldColor=soldColor;
%>
<%
String soldPAvg=util.nvl((String)dataDtl.get(soldPgreenkey.replaceAll("@"+displayColumn, "@AVG")));
String compColour="background-color:#6666ff";
if(!soldPAvg.equals("")){
if(Double.parseDouble(soldPAvg) >= mktMaxAvg)
compColour="background-color:#66ff66";
else if(Double.parseDouble(soldPAvg) <= mktMinAvg)
compColour="background-color:#ff6666";
}else{
compColour="";
}
if(dataValidGreenLst.contains(soldPgreenkey)){
%>
<td align="right" style="<%=localsoldColor%>"><%=util.nvl((String)dataDtl.get(soldPgreenkey+"_GREEN_RNK"))%></td>
<%}else{%>
<td align="right" style="<%=localsoldColor%>">-</td>
<%}%>
<td align="right" style="<%=localsoldColor%>">
<a href="<%=pktlink%><%=soldP%>&key=<%=greenKey.replaceAll("\\+", "%2B").replaceAll("\\-", "%2D")%>" target="_blank">
<%=util.nvl((String)dataDtl.get(soldPgreenkey.replaceAll("@"+displayColumn, "@QTY")))%>
</a>
</td>
<td align="right" style="<%=localsoldColor%>"><%=util.nvl((String)dataDtl.get(soldPgreenkey.replaceAll("@"+displayColumn, "@CTS")))%></td>
<td align="right" style="<%=compColour%>"><%=soldPAvg%></td>
<td align="right" title="Vlu / 100000" style="<%=localsoldColor%>"><%=util.compressVlu(util.nvl((String)dataDtl.get(soldPgreenkey.replaceAll("@"+displayColumn, "@VLU"))),"100000")%></td>
<td align="right" style="<%=localsoldColor%>"><%=util.nvl((String)dataDtl.get(soldPgreenkey.replaceAll("@"+displayColumn, "@AGE")))%></td>
<%if(!ratioby.equals("MKTVSNEWMKT") && !ratioby.equals("OLDVSNEW")){%>
<td align="right" style="<%=localsoldColor%>"><%=util.nvl((String)dataDtl.get(soldPgreenkey.replaceAll("@"+displayColumn, "@MSD")))%></td>
<%}if(dis){%>
<td align="right" style="<%=localsoldColor%>"><%=util.nvl((String)dataDtl.get(soldPgreenkey))%></td>
<%}%>
<%}%>
</tr>
<%}%>
<%
for(int loop=0;loop<redkeyListsz;loop++){
String redKey="";
String mktredkey="";
  float ratio = (float) loop / (float) redkeyListsz;
  int redcolor = (int) (255 * ratio + 255 * (1 - ratio));
  int greencolor = (int) (255 * ratio + 0 * (1 - ratio));
  int bluecolor = (int) (255 * ratio + 0* (1 - ratio));
  String style="background-color:rgb("+redcolor+","+greencolor+","+bluecolor+")";
%>
<tr>
<td align="right"><%=srno++%></td>
<td style="<%=style%>" nowrap="nowrap">
<%
redKey=(String)redkeyList.get(loop);
mktredkey=redKey.replaceAll("@P"+topListByPeriod, "@"+hardCodeGrpVal);
String mktAvg=util.nvl((String)dataDtl.get(mktredkey.replaceAll("@"+displayColumn, "@AVG")));
double rang=Math.round((Double.parseDouble(mktAvg)/100.0)* Double.parseDouble(avgPct));
double mktMinAvg=Double.parseDouble(mktAvg)-rang;
double mktMaxAvg=Double.parseDouble(mktAvg)+rang;
%>
<%=redKey.replaceAll("@"+displayColumn,"").replaceAll("@P"+topListByPeriod, "").replaceAll("@", " ")%>
</td>
<td align="right" style="<%=mktsrtColor%>">
<a href="<%=pktlink%>&key=<%=redKey.replaceAll("\\+", "%2B").replaceAll("\\-", "%2D")%>&hardCodeGrp=<%=hardCodeGrpVal%>&hardCodeGrpKey=<%=hardCodeGrpKey%>" target="_blank">
<%=util.nvl((String)dataDtl.get(mktredkey.replaceAll("@"+displayColumn, "@QTY")))%>
</a>
</td>
<td align="right" style="<%=mktsrtColor%>"><%=util.nvl((String)dataDtl.get(mktredkey.replaceAll("@"+displayColumn, "@CTS")))%></td>
<td align="right" style="<%=mktsrtColor%>"><%=mktAvg%></td>
<td align="right" title="Vlu / 100000" style="<%=mktsrtColor%>"><%=util.compressVlu(util.nvl((String)dataDtl.get(mktredkey.replaceAll("@"+displayColumn, "@VLU"))),"100000")%></td>
<td align="right" style="<%=mktsrtColor%>"><%=util.nvl((String)dataDtl.get(mktredkey.replaceAll("@"+displayColumn, "@AGE")))%></td>
<%if(dis){%>
<td align="right" style="<%=mktsrtColor%>"><%=util.nvl((String)dataDtl.get(mktredkey))%></td>
<%}%>
<%for(int s=0;s<t20SalePeriodListsz;s++){
String soldP=(String)t20SalePeriodList.get(s);
String soldPredKey=redKey.replaceAll("@P"+topListByPeriod, "@P"+soldP);
String localsoldColor=mktsrtColor;
if(!topListByPeriod.equals(soldP))
localsoldColor=soldColor;
%>
<%
String soldPAvg=util.nvl((String)dataDtl.get(soldPredKey.replaceAll("@"+displayColumn, "@AVG")));
String compColour="background-color:#6666ff";
if(!soldPAvg.equals("")){
if(Double.parseDouble(soldPAvg) >= mktMaxAvg)
compColour="background-color:#66ff66";
else if(Double.parseDouble(soldPAvg) <= mktMinAvg)
compColour="background-color:#ff6666";
}else{
compColour="";
}
if(dataValidRedLst.contains(soldPredKey)){
%>
<td align="right" style="<%=localsoldColor%>"><%=util.nvl((String)dataDtl.get(soldPredKey+"_RED_RNK"))%></td>
<%}else{%>
<td align="right" style="<%=localsoldColor%>">-</td>
<%}%>
<td align="right" style="<%=localsoldColor%>">
<a href="<%=pktlink%><%=soldP%>&key=<%=redKey.replaceAll("\\+", "%2B").replaceAll("\\-", "%2D")%>" target="_blank">
<%=util.nvl((String)dataDtl.get(soldPredKey.replaceAll("@"+displayColumn, "@QTY")))%>
</a>
</td>
<td align="right" style="<%=localsoldColor%>"><%=util.nvl((String)dataDtl.get(soldPredKey.replaceAll("@"+displayColumn, "@CTS")))%></td>
<td align="right" style="<%=compColour%>"><%=soldPAvg%></td>
<td align="right" title="Vlu / 100000" style="<%=localsoldColor%>"><%=util.compressVlu(util.nvl((String)dataDtl.get(soldPredKey.replaceAll("@"+displayColumn, "@VLU"))),"100000")%></td>
<td align="right" style="<%=localsoldColor%>"><%=util.nvl((String)dataDtl.get(soldPredKey.replaceAll("@"+displayColumn, "@AGE")))%></td>
<%if(!ratioby.equals("MKTVSNEWMKT") && !ratioby.equals("OLDVSNEW")){%>
<td align="right" style="<%=localsoldColor%>"><%=util.nvl((String)dataDtl.get(soldPredKey.replaceAll("@"+displayColumn, "@MSD")))%></td>
<%}if(dis){%>
<td align="right" style="<%=localsoldColor%>"><%=util.nvl((String)dataDtl.get(soldPredKey))%></td>
<%}%>
<%}%>
</tr>
<%}%>
</tbody>
</table>
</td>
</tr>
</table>
</td>
</tr>
</table>
</body>
</html>
  
  