<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page errorPage="../error_page.jsp" %>
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import="java.util.ArrayList,java.util.ArrayList,java.util.HashMap, java.sql.*, java.util.Enumeration, java.util.logging.Level,java.math.BigDecimal, java.util.Collections"%>
<jsp:useBean id="info" class="ft.com.InfoMgr" scope="session" />
<jsp:useBean id="util" class="ft.com.JspUtil" scope="page" />
<%@taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>
 
<html>
  <head>
 <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
  
  <title>Generic Report View</title>
  <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/bse.js?v=<%=info.getJsVersion()%> " > </script>
 <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/bse.css?v=<%=info.getJsVersion()%>"/>
 <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/ajax.js?v=<%=info.getJsVersion()%> " > </script>
  <script language="javaScript" type="text/javascript" src="<%=info.getReqUrl()%>/calendar/calendar.js"></script>
   <link href="<%=info.getReqUrl()%>/calendar/calendar.css" rel="stylesheet" type="text/css">
    <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/style.css?v=<%=info.getJsVersion()%>"/>
  </head>
 
   <%
     HashMap dbinfo = info.getDmbsInfoLst();
     String level1lprp = util.nvl((String)request.getAttribute("gridbylprp"));
     String filterlprp = util.nvl((String)request.getAttribute("filterlprp"));
        double days = Double.parseDouble((String)session.getAttribute("days"));
        String perioddtlmsg = util.nvl((String)session.getAttribute("perioddtlmsg"));
     ArrayList stkoenclosegtlevel1List= ((ArrayList)session.getAttribute("stkoenclosegtList") == null)?new ArrayList():(ArrayList)session.getAttribute("stkoenclosegtList");
     ArrayList dspgrpList= ((ArrayList)session.getAttribute("dspgrpList") == null)?new ArrayList():(ArrayList)session.getAttribute("dspgrpList");
     ArrayList openclosefilterlprpLst= ((ArrayList)session.getAttribute("openclosefilterlprpLst") == null)?new ArrayList():(ArrayList)session.getAttribute("openclosefilterlprpLst");
     HashMap dscttlLst= ((HashMap)session.getAttribute("stkoenclosedataDtl") == null)?new HashMap():(HashMap)session.getAttribute("stkoenclosedataDtl");
        HashMap mprp = info.getSrchMprp();
        HashMap prp = info.getSrchPrp();
        String logId=String.valueOf(info.getLogId());
        String onfocus="cook('"+logId+"')";
        ArrayList commList=new ArrayList();
        String lprpTyp = util.nvl((String)mprp.get(level1lprp+"T"));
        
        if(lprpTyp.equals("C")){
        commList = (ArrayList)prp.get(level1lprp+"V");
        }else{
        Collections.sort(stkoenclosegtlevel1List);
        commList=stkoenclosegtlevel1List;
        }
        int dspgrpListsz=dspgrpList.size();
        int commListsz=commList.size();
        %>
 <body onfocus="<%=onfocus%>" onload="MM_preloadImages('images/faunalogo.png')" topmargin="0" leftmargin="0" rightmargin="0" bottommargin="0">
<table width="100%"  border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
  <td valign="top" class="hedPg">
  <table cellpadding="1" cellspacing="5"><tr><td valign="middle">
  <img src="../images/line.jpg" border="0" /></td><td valign="middle"> <span class="pgHed">
 Stock Report Opening/Closing</span> </td>
</tr></table>
  </td>
  </tr>
    <tr><td valign="top" class="hedPg"> 
  <b>Criteria :</b><%=perioddtlmsg%><bean:write property="value(criteria)" name="genericReportForm" /></td></tr>
  <tr>
<td valign="top" class="hedPg">
<html:hidden property="value(filterlprp)" styleId="filterlprp" value="<%=filterlprp%>" />
  Next Grid By : <html:select property="value(gridbylprp)" name="genericReportForm"  style="width:100px" styleId="gridbylprp" onchange=""  >
<%for (int i=0;i<openclosefilterlprpLst.size();i++){
    String prpflt = (String)openclosefilterlprpLst.get(i);
    String hdr = util.nvl((String)mprp.get(prpflt));
    String prpDsc = util.nvl((String)mprp.get(prpflt+"D"));
    if(hdr == null) {
    hdr = prpflt;
    }
%>
<html:option value="<%=prpflt%>" ><%=prpDsc%></html:option> 
<%}%>
</html:select>
&nbsp;
  <html:button property="value(dflt)" value="Load Original Grid"  onclick="goTo('genericReport.do?method=loadstkopenclosereset','','')" styleClass="submit" />
  </tr>
  <%
   String msg = (String)request.getAttribute("msg");
   if(msg!=null){%>
     <tr><td valign="top" class="tdLayout"><%=msg%></td></tr>
   <%}%>
<tr>
<td valign="top" class="hedPg">
<table class="grid1">
<tr>
<th></th>
<th colspan="5">Opening</th>
<th colspan="5">New</th>
<th colspan="6">Sold</th>
<th colspan="6">Closing</th>
<th></th>
</tr>
<tr>
<td></td>
<td align="center">QTY</td>
<td align="center">CTS</td>
<td align="center">AVG</td>
<td align="center">RAP</td>
<td align="center">VLU</td>
<td align="center">QTY</td>
<td align="center">CTS</td>
<td align="center">AVG</td>
<td align="center">RAP</td>
<td align="center">VLU</td>
<td align="center">QTY</td>
<td align="center">CTS</td>
<td align="center">AVG</td>
<td align="center">RAP</td>
<td align="center">VLU</td>
<td align="center">%</td>
<td align="center">QTY</td>
<td align="center">CTS</td>
<td align="center">AVG</td>
<td align="center">RAP</td>
<td align="center">VLU</td>
<td align="center">%</td>
<td align="center">Stock Days</td>
</tr>

<%
BigDecimal closingTTlVlu = ((BigDecimal)dscttlLst.get("CLOSE_VLU") == null)?new BigDecimal(0):(BigDecimal)dscttlLst.get("CLOSE_VLU");
BigDecimal openTTlVlu = ((BigDecimal)dscttlLst.get("OPEN_VLU") == null)?new BigDecimal(0):(BigDecimal)dscttlLst.get("OPEN_VLU");
BigDecimal soldTTlVlu= ((BigDecimal)dscttlLst.get("SOLD_VLU") == null)?new BigDecimal(0):(BigDecimal)dscttlLst.get("SOLD_VLU");
for (int i=0;i<commListsz;i++){
String prpVal=(String)commList.get(i);
if(stkoenclosegtlevel1List.contains(prpVal)){
BigDecimal closingQtyBig = (BigDecimal)dscttlLst.get(prpVal+"_CLOSE_QTY");
BigDecimal closingCtsBig = (BigDecimal)dscttlLst.get(prpVal+"_CLOSE_CTS");
BigDecimal closingVluBig = (BigDecimal)dscttlLst.get(prpVal+"_CLOSE_VLU");
BigDecimal closingRapVluBig = (BigDecimal)dscttlLst.get(prpVal+"_CLOSE_RAPVLU");
if(closingQtyBig==null)
closingQtyBig=new BigDecimal(0);
if(closingCtsBig==null)
closingCtsBig=new BigDecimal(0);
if(closingVluBig==null)
closingVluBig=new BigDecimal(0);
if(closingRapVluBig==null)
closingRapVluBig=new BigDecimal(0);
String closingQty=util.nvl((String)closingQtyBig.toString(),"0");
String closingCts=util.nvl((String)closingCtsBig.toString(),"0");
String closingVlu=util.nvl((String)closingVluBig.toString(),"0");
String closingRapVlu=util.nvl((String)closingRapVluBig.toString(),"0");
String closingAvg="0";
if(!closingCts.equals("0") && !closingVlu.equals("0")){
closingAvg=String.valueOf(Math.round(Double.parseDouble(closingVlu)/Double.parseDouble(closingCts)));
}
String closingRAP="";
if(!closingVlu.equals("0") && !closingRapVlu.equals("0")){
closingRAP=String.valueOf(util.roundToDecimals(((Double.parseDouble(closingVlu)/Double.parseDouble(closingRapVlu))*100)-100,2));
}
String closingAvgDis="";
if(!closingVlu.equals("0") && !closingRapVlu.equals("0")){
closingAvgDis=String.valueOf(util.roundToDecimals(((Double.parseDouble(closingVlu)/Double.parseDouble(closingRapVlu))*100)-100,2));
}

String perClosing ="";
if(!perClosing.equals("0")){
perClosing=String.valueOf(util.roundToDecimals((Double.parseDouble(closingVlu)*100/Double.parseDouble(String.valueOf(closingTTlVlu))),2));
}

BigDecimal newQtyBig = (BigDecimal)dscttlLst.get(prpVal+"_NEW_QTY");
BigDecimal newCtsBig = (BigDecimal)dscttlLst.get(prpVal+"_NEW_CTS");
BigDecimal newVluBig = (BigDecimal)dscttlLst.get(prpVal+"_NEW_VLU");
BigDecimal newRapVluBig = (BigDecimal)dscttlLst.get(prpVal+"_NEW_RAPVLU");
if(newQtyBig==null)
newQtyBig=new BigDecimal(0);
if(newCtsBig==null)
newCtsBig=new BigDecimal(0);
if(newVluBig==null)
newVluBig=new BigDecimal(0);
if(newRapVluBig==null)
newRapVluBig=new BigDecimal(0);
String newQty=util.nvl((String)newQtyBig.toString(),"0");
String newCts=util.nvl((String)newCtsBig.toString(),"0");
String newVlu=util.nvl((String)newVluBig.toString(),"0");
String newRapVlu=util.nvl((String)newRapVluBig.toString(),"0");
String newAvg="0";
if(!newCts.equals("0") && !newVlu.equals("0")){
newAvg=String.valueOf(Math.round(Double.parseDouble(newVlu)/Double.parseDouble(newCts)));
}
String newRAP="";
if(!newVlu.equals("0") && !newRapVlu.equals("0")){
newRAP=String.valueOf(util.roundToDecimals(((Double.parseDouble(newVlu)/Double.parseDouble(newRapVlu))*100)-100,2));
}

BigDecimal soldQtyBig = (BigDecimal)dscttlLst.get(prpVal+"_SOLD_QTY");
BigDecimal soldCtsBig = (BigDecimal)dscttlLst.get(prpVal+"_SOLD_CTS");
BigDecimal soldVluBig = (BigDecimal)dscttlLst.get(prpVal+"_SOLD_VLU");
BigDecimal soldRapVluBig = (BigDecimal)dscttlLst.get(prpVal+"_SOLD_RAPVLU");
BigDecimal soldTtlVluBig = new BigDecimal(0);
if(soldQtyBig==null)
soldQtyBig=new  BigDecimal(0);
if(soldCtsBig==null)
soldCtsBig=new  BigDecimal(0);
if(soldVluBig==null)
soldVluBig=new  BigDecimal(0);
if(soldRapVluBig==null)
soldRapVluBig=new  BigDecimal(0);
soldTtlVluBig=soldTtlVluBig.add(soldVluBig);
String soldQty=util.nvl((String)soldQtyBig.toString(),"0");
String soldCts=util.nvl((String)soldCtsBig.toString(),"0");
String soldVlu=util.nvl((String)soldVluBig.toString(),"0");
String soldRapVlu=util.nvl((String)soldRapVluBig.toString(),"0");
String soldAvg="0";
if(!soldCts.equals("0") && !soldVlu.equals("0")){
soldAvg=String.valueOf(Math.round(Double.parseDouble(soldVlu)/Double.parseDouble(soldCts)));
}
String soldRAP="";
if(!soldVlu.equals("0") && !soldRapVlu.equals("0")){
soldRAP=String.valueOf(util.roundToDecimals(((Double.parseDouble(soldVlu)/Double.parseDouble(soldRapVlu))*100)-100,2));
}
String perSold ="";
if(!perSold.equals("0")){
perSold=String.valueOf(util.roundToDecimals((Double.parseDouble(soldVlu)*100/Double.parseDouble(String.valueOf(soldTTlVlu))),2));
}

BigDecimal openQtyBig = (BigDecimal)dscttlLst.get(prpVal+"_OPEN_QTY");
BigDecimal openCtsBig = (BigDecimal)dscttlLst.get(prpVal+"_OPEN_CTS");
BigDecimal openVluBig = (BigDecimal)dscttlLst.get(prpVal+"_OPEN_VLU");
BigDecimal openRapVluBig = (BigDecimal)dscttlLst.get(prpVal+"_OPEN_RAPVLU");
BigDecimal openTtlVluBig = new BigDecimal(0);
if(openQtyBig==null)
openQtyBig=new  BigDecimal(0);
if(openCtsBig==null)
openCtsBig=new  BigDecimal(0);
if(openVluBig==null)
openVluBig=new  BigDecimal(0);
if(openRapVluBig==null)
openRapVluBig=new  BigDecimal(0);
openTtlVluBig=openTtlVluBig.add(openVluBig);
String openQty=util.nvl((String)openQtyBig.toString(),"0");
String openCts=util.nvl((String)openCtsBig.toString(),"0");
String openVlu=util.nvl((String)openVluBig.toString(),"0");
String openRapVlu=util.nvl((String)openRapVluBig.toString(),"0");
String openAvg="0";
if(!openCts.equals("0") && !openVlu.equals("0")){
openAvg=String.valueOf(Math.round(Double.parseDouble(openVlu)/Double.parseDouble(openCts)));
}
String openRAP="";
if(!openVlu.equals("0") && !openRapVlu.equals("0")){
openRAP=String.valueOf(util.roundToDecimals(((Double.parseDouble(openVlu)/Double.parseDouble(openRapVlu))*100)-100,2));
}
String peropen ="";
if(!peropen.equals("0")){
peropen=String.valueOf(util.roundToDecimals((Double.parseDouble(openVlu)*100/Double.parseDouble(String.valueOf(openTTlVlu))),2));
}

String soldTtlVlu =  (String)soldTtlVluBig.toString();
double mktvlu=Math.round(Double.parseDouble(openVlu)+Double.parseDouble(newVlu));
Double soldTtlVluDb =Double.parseDouble(soldTtlVlu);
Double stockDayDb = 0.0;
String stockDay= "";
if(soldTtlVluDb > 0 && mktvlu >0)
stockDay=String.valueOf(Math.round(mktvlu/(soldTtlVluDb/days)));
%>
<tr>
<td align="right" style="padding:0px"><span><a  title="report" href="#" onclick="filteropenclosescreen('<%=info.getReqUrl()%>','<%=prpVal%>');" ><%=prpVal%></a></span></td>
<td align="right" style="padding:0px"><span><%=openQty%></span></td>
<td align="right"><a  title="Packet Wise report" href="genericReport.do?method=pktDtlStockOpenClose&dsp_stt=OPEN&filterlprpval=<%=prpVal%>&filterlprp=<%=level1lprp%>"  target="_blank"><%=openCts%></a></td>
<td align="right"><%=openAvg%></td>
<td align="right"><%=openRAP%></td>
<td align="right"><%=openVlu%></td>
<td align="right" style="padding:0px"><span><%=newQty%></span></td>
<td align="right"><a  title="Packet Wise report" href="genericReport.do?method=pktDtlStockOpenClose&dsp_stt=NEW&filterlprpval=<%=prpVal%>&filterlprp=<%=level1lprp%>"  target="_blank"><%=newCts%></a></td>
<td align="right"><%=newAvg%></td>
<td align="right"><%=newRAP%></td>
<td align="right"><%=newVlu%></td>
<td align="right" style="padding:0px"><span><%=soldQty%></span></td>
<td align="right"><a  title="Packet Wise report" href="genericReport.do?method=pktDtlStockOpenClose&dsp_stt=SOLD&filterlprpval=<%=prpVal%>&filterlprp=<%=level1lprp%>"  target="_blank"><%=soldCts%></a></td>
<td align="right"><%=soldAvg%></td>
<td align="right"><%=soldRAP%></td>
<td align="right"><%=soldVlu%></td>
<td align="right"><%=perSold%></td>
<td align="right" style="padding:0px"><span><%=closingQty%></span></td>
<td align="right"><a  title="Packet Wise report" href="genericReport.do?method=pktDtlStockOpenClose&dsp_stt=CLOSE&filterlprpval=<%=prpVal%>&filterlprp=<%=level1lprp%>"  target="_blank"><%=closingCts%></a></td>
<td align="right"><%=closingAvg%></td>
<td align="right"><%=closingAvgDis%></td>
<td align="right"><%=closingVlu%></td>
<td align="right"><%=perClosing%></td>
<td align="right"><%=stockDay%></td>
</tr>
<%}}%>
<tr>
<td><span><a  title="report" href="#" onclick="filteropenclosescreen('<%=info.getReqUrl()%>','ALL');" >Total</a></span></td>
<%
BigDecimal closingQtyBig = (BigDecimal)dscttlLst.get("CLOSE_QTY");
BigDecimal closingCtsBig = (BigDecimal)dscttlLst.get("CLOSE_CTS");
BigDecimal closingVluBig = (BigDecimal)dscttlLst.get("CLOSE_VLU");
BigDecimal closingRapVluBig = (BigDecimal)dscttlLst.get("CLOSE_RAPVLU");
if(closingQtyBig==null)
closingQtyBig=new BigDecimal(0);
if(closingCtsBig==null)
closingCtsBig=new BigDecimal(0);
if(closingVluBig==null)
closingVluBig=new BigDecimal(0);
if(closingRapVluBig==null)
closingRapVluBig=new BigDecimal(0);
String closingQty=util.nvl((String)closingQtyBig.toString(),"0");
String closingCts=util.nvl((String)closingCtsBig.toString(),"0");
String closingVlu=util.nvl((String)closingVluBig.toString(),"0");
String closingRapVlu=util.nvl((String)closingRapVluBig.toString(),"0");
String closingAvg="0";
if(!closingCts.equals("0") && !closingVlu.equals("0")){
closingAvg=String.valueOf(Math.round(Double.parseDouble(closingVlu)/Double.parseDouble(closingCts)));
}
String closingRAP="";
if(!closingVlu.equals("0") && !closingRapVlu.equals("0")){
closingRAP=String.valueOf(util.roundToDecimals(((Double.parseDouble(closingVlu)/Double.parseDouble(closingRapVlu))*100)-100,2));
}

String closingAvgDis="";
if(!closingVlu.equals("0") && !closingRapVlu.equals("0")){
closingAvgDis=String.valueOf(util.roundToDecimals(((Double.parseDouble(closingVlu)/Double.parseDouble(closingRapVlu))*100)-100,2));
}


BigDecimal newQtyBig = (BigDecimal)dscttlLst.get("NEW_QTY");
BigDecimal newCtsBig = (BigDecimal)dscttlLst.get("NEW_CTS");
BigDecimal newVluBig = (BigDecimal)dscttlLst.get("NEW_VLU");
BigDecimal newRapVluBig = (BigDecimal)dscttlLst.get("NEW_RAPVLU");
if(newQtyBig==null)
newQtyBig=new BigDecimal(0);
if(newCtsBig==null)
newCtsBig=new BigDecimal(0);
if(newVluBig==null)
newVluBig=new BigDecimal(0);
if(newRapVluBig==null)
newRapVluBig=new BigDecimal(0);
String newQty=util.nvl((String)newQtyBig.toString(),"0");
String newCts=util.nvl((String)newCtsBig.toString(),"0");
String newVlu=util.nvl((String)newVluBig.toString(),"0");
String newRapVlu=util.nvl((String)newRapVluBig.toString(),"0");
String newAvg="0";
if(!newCts.equals("0") && !newVlu.equals("0")){
newAvg=String.valueOf(Math.round(Double.parseDouble(newVlu)/Double.parseDouble(newCts)));
}
String newRAP="";
if(!newVlu.equals("0") && !newRapVlu.equals("0")){
newRAP=String.valueOf(util.roundToDecimals(((Double.parseDouble(newVlu)/Double.parseDouble(newRapVlu))*100)-100,2));
}

BigDecimal soldQtyBig = (BigDecimal)dscttlLst.get("SOLD_QTY");
BigDecimal soldCtsBig = (BigDecimal)dscttlLst.get("SOLD_CTS");
BigDecimal soldVluBig = (BigDecimal)dscttlLst.get("SOLD_VLU");
BigDecimal soldRapVluBig = (BigDecimal)dscttlLst.get("SOLD_RAPVLU");
if(soldQtyBig==null)
soldQtyBig=new  BigDecimal(0);
if(soldCtsBig==null)
soldCtsBig=new  BigDecimal(0);
if(soldVluBig==null)
soldVluBig=new  BigDecimal(0);
if(soldRapVluBig==null)
soldRapVluBig=new  BigDecimal(0);
String soldQty=util.nvl((String)soldQtyBig.toString(),"0");
String soldCts=util.nvl((String)soldCtsBig.toString(),"0");
String soldVlu=util.nvl((String)soldVluBig.toString(),"0");
String soldRapVlu=util.nvl((String)soldRapVluBig.toString(),"0");
String soldAvg="0";
if(!soldCts.equals("0") && !soldVlu.equals("0")){
soldAvg=String.valueOf(Math.round(Double.parseDouble(soldVlu)/Double.parseDouble(soldCts)));
}
String soldRAP="";
if(!soldVlu.equals("0") && !soldRapVlu.equals("0")){
soldRAP=String.valueOf(util.roundToDecimals(((Double.parseDouble(soldVlu)/Double.parseDouble(soldRapVlu))*100)-100,2));
}
BigDecimal soldTtlVluBig = new BigDecimal(0);
soldTtlVluBig=soldTtlVluBig.add(soldVluBig);

BigDecimal openQtyBig = (BigDecimal)dscttlLst.get("OPEN_QTY");
BigDecimal openCtsBig = (BigDecimal)dscttlLst.get("OPEN_CTS");
BigDecimal openVluBig = (BigDecimal)dscttlLst.get("OPEN_VLU");
BigDecimal openRapVluBig = (BigDecimal)dscttlLst.get("OPEN_RAPVLU");
if(openQtyBig==null)
openQtyBig=new  BigDecimal(0);
if(openCtsBig==null)
openCtsBig=new  BigDecimal(0);
if(openVluBig==null)
openVluBig=new  BigDecimal(0);
if(openRapVluBig==null)
openRapVluBig=new  BigDecimal(0);
String openQty=util.nvl((String)openQtyBig.toString(),"0");
String openCts=util.nvl((String)openCtsBig.toString(),"0");
String openVlu=util.nvl((String)openVluBig.toString(),"0");
String openRapVlu=util.nvl((String)openRapVluBig.toString(),"0");
String openAvg="0";
if(!openCts.equals("0") && !openVlu.equals("0")){
openAvg=String.valueOf(Math.round(Double.parseDouble(openVlu)/Double.parseDouble(openCts)));
}
String openRAP="";
if(!openVlu.equals("0") && !openRapVlu.equals("0")){
openRAP=String.valueOf(util.roundToDecimals(((Double.parseDouble(openVlu)/Double.parseDouble(openRapVlu))*100)-100,2));
}
BigDecimal openTtlVluBig = new BigDecimal(0);
openTtlVluBig=openTtlVluBig.add(openVluBig);

String soldTtlVlu =  (String)soldTtlVluBig.toString();
BigDecimal daysBig =new BigDecimal(days);

String soldTtlVluttl =  (String)soldTtlVluBig.toString();


soldTtlVlu =  (String)soldTtlVluBig.toString();
double mktvlu=Math.round(Double.parseDouble(openVlu)+Double.parseDouble(newVlu));
Double soldTtlVluDb =Double.parseDouble(soldTtlVlu);
String stockDay= "";
if(soldTtlVluDb > 0 && mktvlu >0)
stockDay=String.valueOf(Math.round(mktvlu/(soldTtlVluDb/days)));
%>
<td align="right" style="padding:0px"><span><%=openQty%></span></td>
<td align="right"><a  title="Packet Wise report" href="genericReport.do?method=pktDtlStockOpenClose&dsp_stt=OPEN&filterlprpval=ALL&filterlprp=<%=level1lprp%>"  target="_blank"><%=openCts%></a></td>
<td align="right"><%=openAvg%></td>
<td align="right"><%=openRAP%></td>
<td align="right"><%=openVlu%></td>
<td align="right" style="padding:0px"><span><%=newQty%></span></td>
<td align="right"><a  title="Packet Wise report" href="genericReport.do?method=pktDtlStockOpenClose&dsp_stt=NEW&filterlprpval=ALL&filterlprp=<%=level1lprp%>"  target="_blank"><%=newCts%></a></td>
<td align="right"><%=newAvg%></td>
<td align="right"><%=newRAP%></td>
<td align="right"><%=newVlu%></td>

<td align="right" style="padding:0px"><span><%=soldQty%></span></td>
<td align="right"><a  title="Packet Wise report" href="genericReport.do?method=pktDtlStockOpenClose&dsp_stt=SOLD&filterlprpval=ALL&filterlprp=<%=level1lprp%>"  target="_blank"><%=soldCts%></a></td>
<td align="right"><%=soldAvg%></td>
<td align="right"><%=soldRAP%></td>
<td align="right"><%=soldVlu%></td>
<td align="right">100%</td>
<td align="right" style="padding:0px"><span><%=closingQty%></span></td>
<td align="right"><a  title="Packet Wise report" href="genericReport.do?method=pktDtlStockOpenClose&dsp_stt=CLOSE&filterlprpval=ALL&filterlprp=<%=level1lprp%>"  target="_blank"><%=closingCts%></a></td>
<td align="right"><%=closingAvg%></td>
<td align="right"><%=closingAvgDis%></td>
<td align="right"><%=closingVlu%></td>
<td align="right">100%</td>

<td align="right"><%=stockDay%></td>
</tr>

</table>
</td>
</tr>
  <tr>
    <td><jsp:include page="/footer.jsp" /> </td>
    </tr>
  </table>
  
  </body>
  <%@include file="../calendar.jsp"%>
</html>