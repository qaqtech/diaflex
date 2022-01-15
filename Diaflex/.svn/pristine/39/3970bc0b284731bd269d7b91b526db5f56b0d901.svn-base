<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import="java.util.ArrayList,java.util.ArrayList,java.util.List, java.util.HashMap, java.sql.*, java.util.Enumeration, java.util.logging.Level"%>
<jsp:useBean id="info" class="ft.com.InfoMgr" scope="session" />
<jsp:useBean id="util" class="ft.com.JspUtil" scope="page" />
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>

<title>Search Result</title>
<script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/bse.js?v=<%=info.getJsVersion()%> " > </script>
<link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/bse.css?v=<%=info.getJsVersion()%>"/>
<script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/ajax.js?v=<%=info.getJsVersion()%> " > </script>
<link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/style.css?v=<%=info.getJsVersion()%>"/>
<link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/general.css?v=<%=info.getJsVersion()%>"/>
<script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/memoScript.js?v=<%=info.getJsVersion()%> " > </script>
<script type="text/javascript" src="<%=info.getReqUrl()%>/PopScripts/popup.js?v=<%=info.getJsVersion()%> " > </script>
 <link rel="stylesheet" media="screen" type="text/css" href="css/zoomimage.css" />
 <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/tableFilter/actb.js " > </script>    
    <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/jquery.js"></script>
   <script src="<%=info.getReqUrl()%>/PopScripts/jquery-1.2.6.min.js" type="text/javascript"></script>
    <script type="text/javascript" src="<%=info.getReqUrl()%>/chartscript/amcharts.js?v=<%=info.getJsVersion()%> " > </script>
<script type="text/javascript" src="<%=info.getReqUrl()%>/chartscript/chartajax.js?v=<%=info.getJsVersion()%> " > </script>
<script type="text/javascript" src="<%=info.getReqUrl()%>/chartscript/pie.js?v=<%=info.getJsVersion()%> " > </script>
<script type="text/javascript" src="<%=info.getReqUrl()%>/chartscript/serial.js?v=<%=info.getJsVersion()%> " > </script>
    <script type="text/javascript">
    $(document).ready(function(){
    for(var i=1;i<=1;i++){
       $('#img_'+i).width(600);
       $('#img_'+i).click(function()
       {
          $(this).css("cursor","pointer");
          $(this).animate({width: "600px"}, 'slow');
          $(this).animate({height: "500px"}, 'slow');
       });
    
    $('#img_'+i).mouseout(function()
      {   
          $(this).animate({width: "600px"}, 'slow');
           $(this).animate({height: "500px"}, 'slow');
      });
    }});
    </script>
</head>
<%

ArrayList summaryList = (ArrayList)session.getAttribute("summaryList");
 String typ = util.nvl(request.getParameter("typ"));
 String iosummary = (String)request.getAttribute("iosummary");
 String attbte="";
  HashMap dbSysInfo=info.getDmbsInfoLst();   
  String chartdivstyle="width: 50%; height: 362px;";
        String logId=String.valueOf(info.getLogId());
        String onfocus="cook('"+logId+"')";
        %>
 <body onfocus="<%=onfocus%>">
  <table  valign="top" class="hedPg" border="0" align="left" cellspacing="0" cellpadding="0" width="50%" >
  <tr>
  <td class="memo" <%=attbte%> valign="top" width="50%">
    <%if(typ.equals("MONTH")){
    attbte="style="+"width:700px; float:left; padding-top:50px; margin-left:0px;";
    %>

  <%}%>

  <%if(iosummary==null && summaryList!=null) {%>
  <table border="0" align="left" class="Orange" cellspacing="2" cellpadding="2" width="90%">
  <tr>
   <%if(typ.equals("BYR") || (typ.equals("EMP"))){
   if(typ.equals("BYR") && (!typ.equals("EMP"))){
   %>
  <th class="Orangeth" align="center" nowrap="nowrap">Buyer  </th> 
  <%}%>
  <th class="Orangeth" align="center" nowrap="nowrap"> Employee </th>
  <%}else if(typ.equals("WEEK")){
  %>
   <th class="Orangeth" align="center" nowrap="nowrap">WEEK</th> 
  <% }else{%>
   <th class="Orangeth" align="center" nowrap="nowrap">Month</th> 
  <%}%>
  <th class="Orangeth" align="center" nowrap="nowrap">Qty</th><th class="Orangeth" align="center" nowrap="nowrap">Cts</th>
  <th class="Orangeth" align="center" nowrap="nowrap">Avg Price</th><th class="Orangeth" align="center" nowrap="nowrap">Avg disc</th>
     <th align="center" class="Orangeth" nowrap="nowrap">Value</th>
   <%if(typ.equals("BYR") || (typ.equals("EMP"))){%>
   <th align="center" class="Orangeth" nowrap="nowrap">PL</th>
   <%}%>
   </tr>
  <%
  if(summaryList!=null && summaryList.size()>0){
  for(int i=0;i<summaryList.size();i++){
  HashMap summDtl = (HashMap)summaryList.get(i);
   %>
   <tr>
   <%if(typ.equals("BYR") || (typ.equals("EMP"))){
   if(typ.equals("BYR") && (!typ.equals("EMP"))){
   %>
   <td align="left"><%=summDtl.get("byr")%></td> 
   <%}%>
   <td align="left"><%=summDtl.get("emp")%></td>
   <%}if(typ.equals("WEEK")){%>
    <td align="right"><%=summDtl.get("wk")%></td>
   <%} if(typ.equals("MONTH")){%>
    <td align="right"><%=util.nvl((String)summDtl.get("dspMM"))%></td>
   <%}%>
    <td align="right"><%=summDtl.get("qty")%></td> <td align="right"><%=summDtl.get("cts")%></td>
    <td align="right"><%=summDtl.get("avg")%></td> <td align="right"><%=summDtl.get("avgDis")%></td>
        <td align="right"><%=util.nvl((String)summDtl.get("vlu"))%></td>
    <%if(typ.equals("BYR")|| (typ.equals("EMP"))){%>
    <td align="right"><%=summDtl.get("pl")%></td>
   <%}%>
 </tr>
  <%}}%>
  </table>
  <%}else{
  HashMap iosummDtl = (HashMap)request.getAttribute("iosummDtl");
  ArrayList colList = (ArrayList)request.getAttribute("colList");
  HashMap OPENSTR_LST = (HashMap)request.getAttribute("OPENSTR_LST");
  Integer OPENSTR = (Integer)OPENSTR_LST.get("OPENSTR");
  Double OPENSTR_CTS = (Double)OPENSTR_LST.get("OPENSTR_CTS");
  Double OPENSTR_VLU = (Double)OPENSTR_LST.get("OPENSTR_VLU");
  Double OPENSTR_NETVLU = (Double)OPENSTR_LST.get("OPENSTR_NETVLU");
  Double OPENSTR_PURAVG = (Double)OPENSTR_LST.get("OPENSTR_PURAVG");
  Double OPENSTR_PURVLU = (Double)OPENSTR_LST.get("OPENSTR_PURVLU");
  String net_pur = util.nvl((String)request.getAttribute("NET_PUR"));
  if(OPENSTR_PURAVG==null)
  OPENSTR_PURAVG=0.0;
    if(OPENSTR_PURVLU==null)
  OPENSTR_PURVLU=0.0;
  int colsapn=4;
  int colspanmonthly=21;
  int soldcolsapn=4;
  if(net_pur.equals("Y")){
  colsapn=6;
  soldcolsapn=8;
  colspanmonthly=35;
  }
  %>
  <table border="0" align="left" class="Orange" cellspacing="1" cellpadding="1">
  <tr><th class="Orangeth" colspan="<%=colspanmonthly%>" nowrap="nowrap">Month Wise</th></tr>
  <tr>
  <th class="Orangeth" align="left"  nowrap="nowrap"></th>
  <th class="Orangeth" align="center" colspan="<%=colsapn%>">Opening</th>
  <th class="Orangeth" align="center" colspan="<%=colsapn%>">NEW</th> 
  <th class="Orangeth" nowrap="nowrap" align="center" colspan="<%=soldcolsapn%>">NEW SOLD</th> 
  <th class="Orangeth" nowrap="nowrap" align="center" colspan="<%=soldcolsapn%>">SOLD</th> 
  <th class="Orangeth" align="center" colspan="<%=colsapn%>">Closing</th></tr>
  <tr>
  <td></td>
  <td align="center" nowrap="nowrap">QTY</td>
  <td align="center" nowrap="nowrap">CTS</td>
  <td align="center" nowrap="nowrap">VLU</td>
  <td align="center" nowrap="nowrap">NET VLU</td>
  <% if(net_pur.equals("Y")){%>
  <td align="center" nowrap="nowrap">PUR RTE</td>
  <td align="center" nowrap="nowrap">PUR VLU</td>
  <%}%>
    <td align="center" nowrap="nowrap">QTY</td>
  <td align="center" nowrap="nowrap">CTS</td>
  <td align="center" nowrap="nowrap">VLU</td>
  <td align="center" nowrap="nowrap">NET VLU</td>
    <% if(net_pur.equals("Y")){%>
  <td align="center" nowrap="nowrap">PUR RTE</td>
  <td align="center" nowrap="nowrap">PUR VLU</td>
  <%}%>
    <td align="center" nowrap="nowrap">QTY</td>
  <td align="center" nowrap="nowrap">CTS</td>
  <td align="center" nowrap="nowrap">VLU</td>
  <td align="center" nowrap="nowrap">NET VLU</td>
    <% if(net_pur.equals("Y")){%>
  <td align="center" nowrap="nowrap">PUR RTE</td>
  <td align="center" nowrap="nowrap">PUR VLU</td>
    <td align="center" nowrap="nowrap">PL VLU</td>
  <td align="center" nowrap="nowrap">PL %</td>
  <%}%>
    <td align="center" nowrap="nowrap">QTY</td>
  <td align="center" nowrap="nowrap">CTS</td>
  <td align="center" nowrap="nowrap">VLU</td>
  <td align="center" nowrap="nowrap">NET VLU</td>
  <% if(net_pur.equals("Y")){%>
  <td align="center" nowrap="nowrap">PUR RTE</td>
  <td align="center" nowrap="nowrap">PUR VLU</td>
    <td align="center" nowrap="nowrap">PL VLU</td>
  <td align="center" nowrap="nowrap">PL %</td>
  <%}%>
  <td align="center" nowrap="nowrap">QTY</td>
  <td align="center" nowrap="nowrap">CTS</td>
  <td align="center" nowrap="nowrap">VLU</td>
  <td align="center" nowrap="nowrap">NET VLU</td>
    <% if(net_pur.equals("Y")){%>
  <td align="center" nowrap="nowrap">PUR RTE</td>
  <td align="center" nowrap="nowrap">PUR VLU</td>
  <%}%>
  </tr>
  <%
  int newValIntTotal =0;
  int soldValIntTotal =0;
  int openingValTotal = 0;
  int closeValTotal = 0;
  int newSoldTotal = 0;
  
  double newValdouTotalCts =0;
  double soldValdouTotalCts =0;
  double openingValTotalCts = 0;
  double closeValTotalCts = 0;
  double newSoldTotalCts = 0;
  double newValdouTotalVlu =0;
  double soldValdouTotalVlu =0;
  double openingValTotalVlu = 0;
  double closeValTotalVlu = 0;
  double newSoldTotalVlu = 0;
  double newValdouTotalNetVlu =0;
  double soldValdouTotalNetVlu =0;
  double openingValTotalNetVlu = 0;
  double closeValTotalNetVlu = 0;
  double newSoldTotalNetVlu = 0;
  
  double newValdouTotalprteVlu =0;
  double soldValdouTotalprteVlu =0;
  double openingValTotalprteVlu = 0;
  double closeValTotalprteVlu = 0;
  double newSoldTotalprteVlu = 0;
  double newValdouTotalpvluVlu =0;
  double soldValdouTotalpvluVlu =0;
  double openingValTotalpvluVlu = 0;
  double closeValTotalpvluVlu = 0;
  double newSoldTotalpvluVlu = 0;
  
  openingValTotal = OPENSTR;
  openingValTotalCts = OPENSTR_CTS;
  openingValTotalVlu = OPENSTR_VLU;
  openingValTotalNetVlu = OPENSTR_NETVLU;
  openingValTotalprteVlu = OPENSTR_PURAVG;
  openingValTotalpvluVlu = OPENSTR_PURVLU;
  
  for(int i=0;i<colList.size();i++){
  String fldVal = (String)colList.get(i);
  
  String newVal = util.nvl((String)iosummDtl.get("NEW_"+fldVal+"_QTY"));
  String soldVal = util.nvl((String)iosummDtl.get("SOLD_"+fldVal+"_QTY"));
  String newsoldVal = util.nvl((String)iosummDtl.get("NEW_SOLD_"+fldVal+"_QTY"));
  
  String newValCts = util.nvl((String)iosummDtl.get("NEW_"+fldVal+"_CTS"));
  String soldValCts = util.nvl((String)iosummDtl.get("SOLD_"+fldVal+"_CTS"));
  String newsoldValCts = util.nvl((String)iosummDtl.get("NEW_SOLD_"+fldVal+"_CTS"));
  
  String newValVlu = util.nvl((String)iosummDtl.get("NEW_"+fldVal+"_VLU"));
  String soldValVlu = util.nvl((String)iosummDtl.get("SOLD_"+fldVal+"_VLU"));
  String newsoldValVlu = util.nvl((String)iosummDtl.get("NEW_SOLD_"+fldVal+"_VLU"));
  
  String newValNetVlu = util.nvl((String)iosummDtl.get("NEW_"+fldVal+"_NETVLU"));
  String soldValNetVlu = util.nvl((String)iosummDtl.get("SOLD_"+fldVal+"_NETVLU"));
  String newsoldValNetVlu = util.nvl((String)iosummDtl.get("NEW_SOLD_"+fldVal+"_NETVLU"));
  
  String newValprteVlu = util.nvl((String)iosummDtl.get("NEW_"+fldVal+"_PURAVG"));
  String soldValprteVlu = util.nvl((String)iosummDtl.get("SOLD_"+fldVal+"_PURAVG"));
  String newsoldValprteVlu = util.nvl((String)iosummDtl.get("NEW_SOLD_"+fldVal+"_PURAVG"));
  
  String newValpvluVlu = util.nvl((String)iosummDtl.get("NEW_"+fldVal+"_PURVLU"));
  String soldValpvluVlu = util.nvl((String)iosummDtl.get("SOLD_"+fldVal+"_PURVLU"));
  String newsoldValpvluVlu = util.nvl((String)iosummDtl.get("NEW_SOLD_"+fldVal+"_PURVLU"));
  int newValInt =0;
  int soldValInt =0;
  int openingVal = 0;
  int closeVal = 0;
  int newSold = 0;
  
  double newValdouCts =0;
  double soldValdouCts =0;
  double openingValCts = 0;
  double closeValCts = 0;
  double newSoldCts = 0;
    double newValdouVlu =0;
  double soldValdouVlu =0;
  double openingValVlu = 0;
  double closeValVlu = 0;
  double newSoldVlu = 0;
    double newValdouNetVlu =0;
  double soldValdouNetVlu =0;
  double openingValNetVlu = 0;
  double closeValNetVlu = 0;
  double newSoldNetVlu = 0;
  
      double newValdouprteVlu =0;
  double soldValdouprteVlu =0;
  double openingValprteVlu = 0;
  double closeValprteVlu = 0;
  double newSoldprteVlu = 0;
    double newValdoupvluVlu =0;
  double soldValdoupvluVlu =0;
  double openingValpvluVlu = 0;
  double closeValpvluVlu = 0;
  double newSoldpvluVlu = 0;
  
  if(!newVal.equals(""))
    newValInt = Integer.parseInt(newVal);
  if(!newValCts.equals(""))
    newValdouCts = Double.parseDouble(newValCts);
  if(!newValVlu.equals(""))
    newValdouVlu = Double.parseDouble(newValVlu);
  if(!newValCts.equals(""))
    newValdouNetVlu = Double.parseDouble(newValNetVlu);
      if(!newValprteVlu.equals(""))
    newValdouprteVlu = Double.parseDouble(newValprteVlu);
  if(!newValpvluVlu.equals(""))
    newValdoupvluVlu = Double.parseDouble(newValpvluVlu);
    
  if(!soldVal.equals(""))
    soldValInt = Integer.parseInt(soldVal);
  if(!soldValCts.equals(""))
    soldValdouCts = Double.parseDouble(soldValCts);
  if(!soldValVlu.equals(""))
    soldValdouVlu = Double.parseDouble(soldValVlu);
  if(!soldValNetVlu.equals(""))
    soldValdouNetVlu = Double.parseDouble(soldValNetVlu);
      if(!soldValprteVlu.equals(""))
    soldValdouprteVlu = Double.parseDouble(soldValprteVlu);
  if(!soldValpvluVlu.equals(""))
    soldValdoupvluVlu = Double.parseDouble(soldValpvluVlu);
    
  if(!newsoldVal.equals(""))
    newSold = Integer.parseInt(newsoldVal);
  if(!newsoldValCts.equals(""))
    newSoldCts = Double.parseDouble(newsoldValCts); 
  if(!newsoldValVlu.equals(""))
    newSoldVlu = Double.parseDouble(newsoldValVlu); 
  if(!newsoldValNetVlu.equals(""))
    newSoldNetVlu = Double.parseDouble(newsoldValNetVlu); 
  if(!newsoldValprteVlu.equals(""))
    newSoldprteVlu = Double.parseDouble(newsoldValprteVlu); 
  if(!newsoldValpvluVlu.equals(""))
    newSoldpvluVlu = Double.parseDouble(newsoldValpvluVlu); 
    
     openingVal = OPENSTR;
     closeVal = (openingVal + newValInt)-soldValInt-newSold;
     OPENSTR = closeVal;
     openingValCts = OPENSTR_CTS;
     closeValCts = (openingValCts + newValdouCts)-soldValdouCts-newSoldCts;
     OPENSTR_CTS = closeValCts;
     openingValVlu = OPENSTR_VLU;
     closeValVlu = (openingValVlu + newValdouVlu)-soldValdouVlu-newSoldVlu;
     OPENSTR_VLU = closeValVlu;
     openingValNetVlu = OPENSTR_NETVLU;
     closeValNetVlu = (openingValNetVlu + newValdouNetVlu)-soldValdouNetVlu-newSoldNetVlu;
     OPENSTR_NETVLU = closeValNetVlu;
     
     openingValprteVlu = OPENSTR_PURAVG;
     closeValprteVlu = (openingValprteVlu + newValdouprteVlu)-soldValdouprteVlu-newSoldprteVlu;
     OPENSTR_PURAVG = closeValprteVlu;
     openingValpvluVlu = OPENSTR_PURVLU;
     closeValpvluVlu = (openingValpvluVlu + newValdoupvluVlu)-soldValdouNetVlu-newSoldpvluVlu;
     OPENSTR_PURVLU = closeValpvluVlu;
  
  newValIntTotal+=newValInt;
  newSoldTotal+=newSold;
  soldValIntTotal+=soldValInt;
  
  newValdouTotalCts+=newValdouCts;
  newSoldTotalCts+=newSoldCts;
  soldValdouTotalCts+=soldValdouCts;
  newValdouTotalVlu+=newValdouVlu;
  newSoldTotalVlu+=newSoldVlu;
  soldValdouTotalVlu+=soldValdouVlu;
  newValdouTotalNetVlu+=newValdouNetVlu;
  newSoldTotalNetVlu+=newSoldNetVlu;
  soldValdouTotalNetVlu+=soldValdouNetVlu;
  newValdouTotalprteVlu+=newValdouprteVlu;
  newSoldTotalprteVlu+=newSoldprteVlu;
  soldValdouTotalprteVlu+=soldValdouprteVlu;
  newValdouTotalpvluVlu+=newValdoupvluVlu;
  newSoldTotalpvluVlu+=newSoldpvluVlu;
  soldValdouTotalpvluVlu+=soldValdoupvluVlu;
  %>
  <tr>
  <td nowrap="nowrap" ><%=fldVal%></td>
  <td align="right"><%=openingVal%></td>
  <td align="right"><%=util.Round(openingValCts,2)%></td>
    <td align="right"><%=Math.round(openingValVlu)%></td>
      <td align="right"><%=Math.round(openingValNetVlu)%></td>
       <% if(net_pur.equals("Y")){%>
           <td align="right"><%=Math.round(openingValprteVlu)%></td>
      <td align="right"><%=Math.round(openingValpvluVlu)%></td>
       <%}%>
  <td align="right"><%=newValInt%></td>
  <td align="right"><%=util.Round(newValdouCts,2)%></td>
    <td align="right"><%=Math.round(newValdouVlu)%></td>
      <td align="right"><%=Math.round(newValdouNetVlu)%></td>
         <% if(net_pur.equals("Y")){%>
    <td align="right"><%=Math.round(newValdouprteVlu)%></td>
      <td align="right"><%=Math.round(newValdoupvluVlu)%></td>
       <%}%>
  <td align="right"><%=newSold%></td>
  <td align="right"><%=util.Round(newSoldCts,2)%></td>
    <td align="right"><%=Math.round(newSoldVlu)%></td>
      <td align="right"><%=Math.round(newSoldNetVlu)%></td>
           <% if(net_pur.equals("Y")){%>
    <td align="right"><%=Math.round(newSoldprteVlu)%></td>
      <td align="right"><%=Math.round(newSoldpvluVlu)%></td>
                  <td align="right"><%=Math.round(newSoldNetVlu-newSoldpvluVlu)%></td>
                  <td align="right"><%=Math.round(((newSoldNetVlu-newSoldpvluVlu)/newSoldpvluVlu)*100)%></td>
       <%}%>
  <td align="right"><%=soldValInt%></td>
  <td align="right"><%=util.Round(soldValdouCts,2)%></td>
    <td align="right"><%=Math.round(soldValdouVlu)%></td>
      <td align="right"><%=Math.round(soldValdouNetVlu)%></td>
             <% if(net_pur.equals("Y")){%>
    <td align="right"><%=Math.round(soldValdouprteVlu)%></td>
      <td align="right"><%=Math.round(soldValdoupvluVlu)%></td>
                        <td align="right"><%=Math.round(soldValdouNetVlu-soldValdoupvluVlu)%></td>
                        <td align="right"><%=Math.round(((soldValdouNetVlu-soldValdoupvluVlu)/soldValdoupvluVlu)*100)%></td>
       <%}%>
  <td align="right"><%=closeVal%></td>
  <td align="right"><%=util.Round(closeValCts,2)%></td>
    <td align="right"><%=Math.round(closeValVlu)%></td>
      <td align="right"><%=Math.round(closeValNetVlu)%></td>
                   <% if(net_pur.equals("Y")){%>
    <td align="right"><%=Math.round(closeValprteVlu)%></td>
      <td align="right"><%=Math.round(closeValpvluVlu)%></td>
       <%}%>
 </tr>
  <%}
  closeValTotal = (openingValTotal + newValIntTotal)-soldValIntTotal-newSoldTotal;
  closeValTotalCts = (openingValTotalCts + newValdouTotalCts)-soldValdouTotalCts-newSoldTotalCts;
  closeValTotalVlu = (openingValTotalVlu + newValdouTotalVlu)-soldValdouTotalVlu-newSoldTotalVlu;
  closeValTotalNetVlu = (openingValTotalNetVlu + newValdouTotalNetVlu)-soldValdouTotalNetVlu-newSoldTotalNetVlu;
  closeValTotalprteVlu = (openingValTotalprteVlu + newValdouTotalprteVlu)-soldValdouTotalprteVlu-newSoldTotalprteVlu;
  closeValTotalpvluVlu = (openingValTotalpvluVlu + newValdouTotalpvluVlu)-soldValdouTotalpvluVlu-newSoldTotalpvluVlu;
  %>
  <tr>
  <td align="center"><b>Total</b></td>
  <td align="right"><%=openingValTotal%></td>
    <td align="right"><%=util.Round(openingValTotalCts,2)%></td>
        <td align="right"><%=Math.round(openingValTotalVlu)%></td>
            <td align="right"><%=Math.round(openingValTotalNetVlu)%></td>
                                   <% if(net_pur.equals("Y")){%>
        <td align="right"><%=Math.round(openingValTotalprteVlu)%></td>
            <td align="right"><%=Math.round(openingValTotalpvluVlu)%></td>
       <%}%>
  <td align="right"><%=newValIntTotal%></td>
    <td align="right"><%=util.Round(newValdouTotalCts,2)%></td>
    <td align="right"><%=newValdouTotalVlu%></td>
            <td align="right"><%=newValdouTotalNetVlu%></td>
                                       <% if(net_pur.equals("Y")){%>
    <td align="right"><%=Math.round(newValdouTotalprteVlu)%></td>
            <td align="right"><%=Math.round(newValdouTotalpvluVlu)%></td>
       <%}%>
   <td align="left"><%=newSoldTotal%></td>
      <td align="left"><%=util.Round(newSoldTotalCts,2)%></td>
            <td align="left"><%=Math.round(newSoldTotalVlu)%></td>
                  <td align="left"><%=Math.round(newSoldTotalNetVlu)%></td>
                                       <% if(net_pur.equals("Y")){%>
            <td align="left"><%=Math.round(newSoldTotalprteVlu)%></td>
                  <td align="left"><%=Math.round(newSoldTotalpvluVlu)%></td>
                  <td align="left"><%=Math.round(newSoldTotalNetVlu-newSoldTotalpvluVlu)%></td>
                  <td align="right"><%=Math.round(((newSoldTotalNetVlu-newSoldTotalpvluVlu)/newSoldTotalpvluVlu)*100)%></td>
       <%}%>
  <td align="right"><%=soldValIntTotal%></td>
    <td align="right"><%=util.Round(soldValdouTotalCts,2)%></td>
        <td align="right"><%=Math.round(soldValdouTotalVlu)%></td>
            <td align="right"><%=Math.round(soldValdouTotalNetVlu)%></td>
                                           <% if(net_pur.equals("Y")){%>
        <td align="right"><%=Math.round(soldValdouTotalprteVlu)%></td>
            <td align="right"><%=Math.round(soldValdouTotalpvluVlu)%></td>
                              <td align="left"><%=Math.round(soldValdouTotalNetVlu-soldValdouTotalpvluVlu)%></td>
            <td align="right"><%=Math.round(((soldValdouTotalNetVlu-soldValdouTotalpvluVlu)/soldValdouTotalpvluVlu)*100)%></td>
       <%}%>
  <td align="right"><%=closeValTotal%></td>
    <td align="right"><%=util.Round(closeValTotalCts,2)%></td>
        <td align="right"><%=Math.round(closeValTotalVlu)%></td>
            <td align="right"><%=Math.round(closeValTotalNetVlu)%></td>
                               <% if(net_pur.equals("Y")){%>
        <td align="right"><%=Math.round(closeValTotalprteVlu)%></td>
            <td align="right"><%=Math.round(closeValTotalpvluVlu)%></td>
       <%}%>
 </tr>
 </table>
  <%}%>
  </td>
  
  <td  valign="top">
  <%if(typ.equals("MONTH") && summaryList!=null && summaryList.size()>0){
  String styleId="chartdiv_HIDD";
  String styleIdTTl="chartdiv_TTL";
  String url="ajaxRptAction.do?method=monthwiselinegraph";
  chartdivstyle="width: 50%; height: 400px;";
  %>
  <input type="hidden" id="chartdiv_VLUTTL" value="Price">
  <input type="hidden" id="<%=styleId%>" value="chartdiv">
  <input type="hidden" id="<%=styleIdTTl%>" value="Quantity">
  <input type="hidden" id="lineGrp" value="Quantity_Price">
  <script type="text/javascript">
 <!--
$(window).bind("load", function() {
monthWiselinegraph('<%=url%>','Month Wise Summary','chartdiv','50','362');
});
 -->
</script>  
  <%}else if(typ.equals("BYR") || typ.equals("EMP")){
      HashMap allPageDtl= (info.getPageDetails() == null)?new HashMap():(HashMap)info.getPageDetails();
      HashMap pageDtl=(HashMap)allPageDtl.get("GENERIC_REPORT");
      int defaultdisplay=0;
      String basedon="byr";
      String piettl="";
      if(typ.equals("BYR")){
      defaultdisplay=Integer.parseInt(util.nvl((String)((HashMap)((ArrayList)pageDtl.get("BYRWISEDFLT")).get(0)).get("dflt_val"),"5"));
      piettl=util.nvl((String)((HashMap)((ArrayList)pageDtl.get("BYRWISEDFLT")).get(0)).get("fld_ttl"),"Pie");
      }else{
      defaultdisplay=Integer.parseInt(util.nvl((String)((HashMap)((ArrayList)pageDtl.get("EMPWISEDFLT")).get(0)).get("dflt_val"),"10"));
      basedon="emp";
      piettl=util.nvl((String)((HashMap)((ArrayList)pageDtl.get("EMPWISEDFLT")).get(0)).get("fld_ttl"),"Pie");
      }
  %>
<table>
<%ArrayList list=new ArrayList();
list.add("vlu");
for(int i=0;i<list.size();i++){
String itm=(String)list.get(i);
piettl=piettl.replaceAll("ITM",itm);
String url="ajaxRptAction.do?method=createbuyerwisePieChart&basedon="+basedon+"&itm="+itm+"&defaultdisplay="+defaultdisplay;
%>
<tr><td valign="top">
<script type="text/javascript">
 <!--
$(window).bind("load", function() {
createbuyerwisePieChart('<%=url%>','<%=piettl%>','chartdiv','50','362');
});
 -->
</script>  
    </td></tr>
  <%}%>
</table>
  <%}%>
  </td>
  </tr>
  </table>
  <div id="chartdiv" style="<%=chartdivstyle%>"></div>
  </body>
</html>