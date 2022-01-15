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
     ArrayList asOnDateList = (request.getAttribute("asOnDateList") == null)?new ArrayList():(ArrayList)request.getAttribute("asOnDateList");
     HashMap dataTbl= (request.getAttribute("dataDtl") == null)?new HashMap():(HashMap)request.getAttribute("dataDtl");
     String view=util.nvl((String)request.getAttribute("view"));
      String fetch=util.nvl((String)request.getAttribute("fetch"));
        ArrayList addtabList = new ArrayList();
      
   pageList=(ArrayList)pageDtl.get("ADDTAB");
     if(pageList!=null && pageList.size() >0){
        for(int j=0;j<pageList.size();j++){
                        HashMap pgDtl= (HashMap)pageList.get(j);
                        String addTab = (String)pgDtl.get("fld_nme");
                        String tabDsc= (String)pgDtl.get("fld_ttl");
                        addtabList.add(addTab);
                       
     }}
        ArrayList sttlist=new  ArrayList();
        HashMap sttDsc=new  HashMap();
        sttlist.add("OPEN");
        sttlist.add("MFGNEW");
        sttlist.add("PURNEW");
        sttlist.addAll(addtabList);
        sttlist.add("SOLD");
        
        sttlist.add("CLOSE");
        sttDsc.put("OPEN", "Opening");
        sttDsc.put("MFGNEW", "Mfg New");
        sttDsc.put("PURNEW", "Purchase New");
        sttDsc.put("RDMIXIN", "Round Mix");
        sttDsc.put("MIXOUT", "Mix Out");
        sttDsc.put("TRF_IN", "Transfer In");
         sttDsc.put("TRF_OUT", "Transfer Out");
        sttDsc.put("MIXOUT", "Mix Out");
        sttDsc.put("MIXIN", "Mix In");
        sttDsc.put("SOLD", "Sold");
         sttDsc.put("rep", "Repairing");
        sttDsc.put("CLOSE", "Balance");
        int sttlistsz=sttlist.size();
        int asOnDateListsz=asOnDateList.size();
        String logId=String.valueOf(info.getLogId());
        String onfocus="cook('"+logId+"')";
         HashMap prp = info.getPrp();
       ArrayList prpValVec = (ArrayList)prp.get("IFRSV");
       
    boolean displaySoldActDiff=true;
    pageList=(ArrayList)pageDtl.get("SOLD_ACT_DIFF");
    if(pageList!=null && pageList.size() >0){
    for(int j=0;j<pageList.size();j++){
    displaySoldActDiff=false;
    }
    }
    
  
                     
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
<html:form action="/report/ifrsactionag.do?method=fetch"  method="POST">
   <table>
   <tr>
   
      <td valign="top">
   <table  class="grid1">
   <tr>
   <th colspan="4" align="center">Search</th>
   </tr>
       <tr><td> Date : </td>  
       <td>From&nbsp; <html:text property="value(frmdte)" styleId="frmdte" name="orclReportForm" size="10" /><img src="<%=info.getReqUrl()%>/images/calender.png" onClick="setYears(2011, <%=info.getCurrentYear()%>);showCalender(this, 'frmdte');"> 
       To&nbsp; <html:text property="value(todte)" styleId="todte" name="orclReportForm" size="10" /><img src="<%=info.getReqUrl()%>/images/calender.png" onClick="setYears(2011, <%=info.getCurrentYear()%>);showCalender(this, 'todte');">
       </td>
       <td> Bucket : </td>  
       <td>
       <%
      %>
        <html:select property="value(bucket)" styleId="bucket" name="orclReportForm" style="width:75px">
        <html:option value="">ALL</html:option>
        <%for(int j=0;j<prpValVec.size();j++){
        String prpVal = (String)prpValVec.get(j);
        %>
        <html:option value="<%=prpVal%>"><%=prpVal%></html:option>
        <%}%>
        </html:select>
       </td>
      </tr>
      
   <tr>
   <td colspan="4" align="center">
   <html:submit property="value(srch)" value="Fetch" styleClass="submit" />
   <html:submit property="value(srch)" value="FetchBucketWise" styleClass="submit" />
   <html:submit property="value(srch)" value="SummaryBucketWise" styleClass="submit" /> 
   </td>
   </tr>
   </table>
   </td>
   
   </tr></table>
   
</html:form>
</td>

</tr>  
<%if(view.equals("Y")){

boolean isBkt=true;
if(fetch.equals("BucketWise"))
    isBkt=false;

if(asOnDateListsz >0){
ArrayList dteList = asOnDateList;
if(!isBkt)
dteList = prpValVec;

for(int i=0;i<dteList.size();i++){
String as_on_date=util.nvl((String)dteList.get(i));
if(asOnDateList.contains(as_on_date)){

ArrayList ifrsList=(ArrayList)dataTbl.get(as_on_date);
String frmDte = "";
String toDte = "";
if(fetch.equals("BucketWise")){
     frmDte = util.nvl((String)ifrsList.get((ifrsList.size())-1));
     toDte = util.nvl((String)ifrsList.get(0));
  }
if(fetch.equals("Summary")){
     String dte[] = as_on_date.split("To");
     frmDte = dte[0];
     toDte = dte[1];
  }
%>
<tr>
  <td class="hedPg">
  <table class="grid1">
  <tr>
  <td></td><th colspan="<%=(sttlistsz*3)+3%>" align="center"><%=as_on_date%></th>
  </tr>
  <tr>
  <td></td>
  <%for(int s=0;s<sttlist.size();s++){
   String stt = util.nvl((String)sttlist.get(s));
   String colspan="3";
   if(stt.equals("SOLD") && displaySoldActDiff)
   colspan="5";
    if(stt.equals("CLOSE") && displaySoldActDiff)
   colspan="4";
  %>
  <th align="center" colspan="<%=colspan%>"><%=util.nvl((String)sttDsc.get((String)sttlist.get(s)))%></th>
  <%}%>
  </tr>
  <tr>
  <td></td>
  <%for(int s=0;s<sttlist.size();s++){
  String stt = util.nvl((String)sttlist.get(s));
  %>
  <td align="center">CTS</td>
  <td align="center">CP</td>
  <td align="center">CP VLU</td>
  <% if(stt.equals("SOLD") && displaySoldActDiff){%>
  <td align="center">SOLD ACT VLU</td>
  <td align="center">DIFF</td>
  <%}%>
   <% if(stt.equals("CLOSE") && displaySoldActDiff){%>
  <td align="center">NRV VLU</td>
  <%}%>
  <%}%>
  </tr> 
  <%
  ArrayList buketList =ifrsList;
  if(isBkt)
  buketList = prpValVec;
  if(buketList!=null && buketList.size()>0){
  for(int z=0;z<buketList.size();z++){
  String ifrs=util.nvl((String)buketList.get(z));
  if(ifrsList.contains(ifrs)){
   %>
  <tr>
  <th><%=ifrs%></th>
  <%for(int s=0;s<sttlist.size();s++){
  String stt=util.nvl((String)sttlist.get(s));
  String sttName = util.nvl((String)sttDsc.get((String)sttlist.get(s)));
  String cts = util.nvl((String)dataTbl.get(as_on_date+"_"+ifrs+"_"+stt+"_CTS"),"0");
  String ctsLink = cts;
  if(!sttName.equalsIgnoreCase("Opening")){
      cts = cts.replaceAll(" ","");
      String status = "";
      if(sttName.equals("Mfg New")){
        status = "MFG";
      }else if(sttName.equals("Purchase New")){
        status = "PUR";
      }else if(sttName.equals("Balance")){
       status = "CLOSE";
     } else{
         status = "DLV";
      }
      String asondate = "";
      String ifrsData = "";
       String asontodate = "";
      if(fetch.equals("BucketWise")){
          asondate =ifrs;
          ifrsData =as_on_date;
      }else if(fetch.equals("Summary")){
          asondate = frmDte;
          asontodate = toDte;
          ifrsData =ifrs;
      }else{
          asondate =as_on_date;
          ifrsData =ifrs;
      }
      
      String serPathPktDtl = "ifrsactionag.do?method=getPktDtl&asondate="+asondate+"&bkt="+ifrsData+"&sttName="+status+"&asontodate="+asontodate;
      if(!cts.equals("0.00") &&  !cts.equals("0")){ 
           ctsLink = "<a href=\""+serPathPktDtl+"\"  target=\"PKTDTL\">"+cts+"</a>";
      }
  }
  %>
  <td align="right"><%=ctsLink%></td>
  <td align="right"><%=util.nvl((String)dataTbl.get(as_on_date+"_"+ifrs+"_"+stt+"_RTE"),"0")%></td>
  <td align="right"><%=util.nvl((String)dataTbl.get(as_on_date+"_"+ifrs+"_"+stt+"_VLU"),"0")%></td>
  <% if(stt.equals("SOLD") && displaySoldActDiff){%>
  <td align="right"><%=util.nvl((String)dataTbl.get(as_on_date+"_"+ifrs+"_"+stt+"_ACTVLU"),"0")%></td>
  <td align="right"><%=util.nvl((String)dataTbl.get(as_on_date+"_"+ifrs+"_"+stt+"_DIFF"),"0")%></td>
  <%}%>
   <% if(stt.equals("CLOSE") && displaySoldActDiff){%>
   <td align="right"><%=util.nvl((String)dataTbl.get(as_on_date+"_"+ifrs+"_"+stt+"_NRVVLU"),"0")%></td>
  <%}%>
  <%}%>
  </tr>
  <%}}}%>
  <%if(dataTbl!=null && dataTbl.size()>0){%>
  <tr>
  <td>Total</td>
  <%for(int s=0;s<sttlist.size();s++){
  BigDecimal avgRte = new BigDecimal("0");
  String stt=util.nvl((String)sttlist.get(s));
  String sttName = util.nvl((String)sttDsc.get((String)sttlist.get(s)));
  String cts = util.nvl((String)dataTbl.get(as_on_date+"_"+stt+"_CTS"),"0").trim();
  String vlu = util.nvl((String)dataTbl.get(as_on_date+"_"+stt+"_VLU"),"0").trim();
  double ctsd = Double.parseDouble(cts) ;
  double vluD = Double.parseDouble(vlu) ;
  if(ctsd > 0 && vluD > 0){ 
   BigDecimal ttlcts = new BigDecimal(cts);
  BigDecimal ttlVlu = new BigDecimal(vlu);
    avgRte= ttlVlu.divide(ttlcts, 4,RoundingMode.HALF_EVEN);
}
  String ctsLink = cts;
  if(!sttName.equalsIgnoreCase("Opening") ){
      cts = cts.replaceAll(" ","");
      String status = "";
      if(sttName.equals("Mfg New")){
        status = "MFG";
      }else if(sttName.equals("Purchase New")){
        status = "PUR";
      }else if(sttName.equals("Balance")){
       status = "CLOSE";
      } else{
         status = "DLV";
      }
      String bkt="";
      String asondate = "";
      String asontodate = "";
      if(fetch.equals("BucketWise")){
          asondate = frmDte;
          asontodate = toDte;
          bkt =as_on_date;
      }else if(fetch.equals("Summary")){
          asondate = frmDte;
          asontodate = toDte;
           bkt="ALL";
      }else{
         asondate = as_on_date;
         bkt="ALL";
      }
      String serPathPktDtl = "ifrsactionag.do?method=getPktDtl&asondate="+asondate+"&bkt="+bkt+"&sttName="+status+"&asontodate="+asontodate;
      if(!cts.equals("0.00")  &&  !cts.equals("0")){ 
           ctsLink = "<a href=\""+serPathPktDtl+"\"  target=\"PKTDTL\">"+cts+"</a>";
      }
  }
  %>
  <td align="right"><%=ctsLink%></td>
  <td align="right"><%=String.valueOf(avgRte)%></td>
  <td align="right"><%=util.nvl((String)dataTbl.get(as_on_date+"_"+stt+"_VLU"),"0")%></td>
  <% if(stt.equals("SOLD") && displaySoldActDiff){%>
  <td align="right"><%=util.nvl((String)dataTbl.get(as_on_date+"_"+stt+"_ACT"),"0")%></td>
  <td align="right"><%=util.nvl((String)dataTbl.get(as_on_date+"_"+stt+"_DIFF"),"0")%></td>
  <%}%>
   <% if(stt.equals("CLOSE") && displaySoldActDiff){%>
  <td align="right"><%=util.nvl((String)dataTbl.get(as_on_date+"_"+stt+"_NRVVLU"),"0")%></td>
  <%}%>
  <%}%>
  </tr><%}%>
  </table>
  </td>
</tr>
  <%}}%>
  <%}else{%>
  <tr>  <td class="hedPg">Sorry No Result Found</td></tr>
  <%}}%>
  <tr><td>
<jsp:include page="/footer.jsp" />
</td></tr>
  </table>  
       <%@include file="../calendar.jsp"%>
  </body>
</html>