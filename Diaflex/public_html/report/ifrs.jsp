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
    <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/bse.js?v=<%=info.getJsVersion()%> " > </script>
<link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/bse.css?v=<%=info.getJsVersion()%>"/>
<link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/general.css?v=<%=info.getJsVersion()%>"/>
<script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/ajax.js?v=<%=info.getJsVersion()%> " > </script>
  <script type="text/javascript" src="<%=info.getReqUrl()%>/PopScripts/popup.js?v=<%=info.getJsVersion()%> " > </script>
    <script src="<%=info.getReqUrl()%>/PopScripts/jquery-1.2.6.min.js" type="text/javascript"></script>
   <script language="javaScript" type="text/javascript" src="<%=info.getReqUrl()%>/calendar/calendar.js"></script>
   <link href="<%=info.getReqUrl()%>/calendar/calendar.css" rel="stylesheet" type="text/css" />

 
      <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/style.css?v=<%=info.getJsVersion()%>"/>

  <title>Ifrs Report</title>
 
  </head>

   <%
       HashMap allPageDtl= (info.getPageDetails() == null)?new HashMap():(HashMap)info.getPageDetails();
    HashMap pageDtl=(HashMap)allPageDtl.get("IFRS");
    ArrayList pageList=new ArrayList();
    HashMap pageDtlMap=new HashMap();
    String fld_nme="",fld_ttl="",val_cond="",lov_qry="",dflt_val="",fld_typ="",form_nme="",flg1="";
     HashMap dbinfo = info.getDmbsInfoLst();
        String level1lprp = util.nvl((String)request.getAttribute("gridbylprp"));
        HashMap mprp = info.getSrchMprp();
        HashMap prp = info.getSrchPrp();
        String logId=String.valueOf(info.getLogId());
        String onfocus="cook('"+logId+"')";
        %>
 <body onfocus="<%=onfocus%>" onload="MM_preloadImages('images/faunalogo.png')" topmargin="0" leftmargin="0" rightmargin="0" bottommargin="0">
 
  <input type="hidden" id="reqUrl" name="reqUrl" value="<%=info.getReqUrl()%>"/>
<div id="popupContactASSFNL">
<table class="popUpTb">
<tr><td height="5%">&nbsp;&nbsp;&nbsp;<html:button property="value(close)" styleClass="submit"  onclick="MKEmptyPopup('SC');disablePopupASSFNL();" value="Close"  /></td></tr>
<tr><td valign="top" height="95%">
<iframe name="SC" class="frameStyle"   align="middle" frameborder="0">
</iframe></td></tr>
</table>
</div>
<div id="backgroundPopup"></div>

 
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
<html:form action="/report/ifrsaction.do?method=fetch"  method="POST">
  <table  class="grid1">
   <tr>
   <th colspan="2" align="center">Search</th>
   </tr>
       <tr><td>Date : </td>  
       <td><html:text property="value(recfrmdte)" styleId="recfrmdte" name="orclReportForm" size="10" /><img src="<%=info.getReqUrl()%>/images/calender.png" onClick="setYears(2011, <%=info.getCurrentYear()%>);showCalender(this, 'recfrmdte');"> 
       To&nbsp; <html:text property="value(restodte)" styleId="restodte" name="orclReportForm"  size="10"/> <img src="<%=info.getReqUrl()%>/images/calender.png" onClick="setYears(2011, <%=info.getCurrentYear()%>);showCalender(this, 'restodte');"></td>
      </tr>

   <tr>
   <td>Type</td>
   <td>
     <html:select  property="value(type)" name="orclReportForm">
      <html:option value="BOTH">BOTH</html:option> 
     <html:option value="NR">Single</html:option> 
     <html:option value="MIX">Mixing</html:option> 
     </html:select>
   
   </td>
   </tr>
      <tr>
<td>
Grid
</td>
<td>
      <%
      pageList=(ArrayList)pageDtl.get("LEVEL1LPRP");
      if(pageList!=null && pageList.size() >0){%>
      <html:select  property="value(level1openclose)" name="orclReportForm" styleId="level1openclose" style="width:100px"   > 
      <%for(int j=0;j<pageList.size();j++){
      pageDtlMap=(HashMap)pageList.get(j);
      fld_ttl=(String)pageDtlMap.get("fld_ttl");
      fld_nme=(String)pageDtlMap.get("fld_nme"); %>
      <html:option value="<%=fld_nme%>" ><%=fld_ttl%></html:option> 
    <%}%>
    </html:select> 
    <%}%>
</td>
</tr>
   <tr>
   <td align="center" colspan="2"><html:submit property="value(srch)" value="Fetch" styleClass="submit" />
   <html:submit property="value(laser)" value="Monthly Laser" styleClass="submit" /></td>
   </tr>
   </table>
</html:form>
</td>
</tr>
<%
String laser = util.nvl((String)request.getAttribute("laser"));
if(laser.equals("")){
     String filterlprp = util.nvl((String)request.getAttribute("filterlprp"));
     ArrayList stkoenclosegtlevel1List= ((ArrayList)session.getAttribute("stkoenclosegtList") == null)?new ArrayList():(ArrayList)session.getAttribute("stkoenclosegtList");
     ArrayList dspgrpList= ((ArrayList)session.getAttribute("dspgrpList") == null)?new ArrayList():(ArrayList)session.getAttribute("dspgrpList");
     ArrayList openclosefilterlprpLst= ((ArrayList)session.getAttribute("openclosefilterlprpLst") == null)?new ArrayList():(ArrayList)session.getAttribute("openclosefilterlprpLst");
     HashMap dscttlLst= ((HashMap)session.getAttribute("stkoenclosedataDtl") == null)?new HashMap():(HashMap)session.getAttribute("stkoenclosedataDtl");
        String fldVal1recpt_dt=util.nvl((String)session.getAttribute("fldVal1recpt_dt"),"0");
        String fldVal2recpt_dt=util.nvl((String)session.getAttribute("fldVal2recpt_dt"),"0");
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
 <tr><td valign="top" class="hedPg"> 
  <b>Criteria :</b><bean:write property="value(criteria)" name="orclReportForm" />
  &nbsp;&nbsp;&nbsp;&nbsp;,<b style=""><font color="red">Hint*</font></b>Mix Data Value/Cost Value Column are in millions Convertion.</td>
  </tr>
  <tr>
<td valign="top" class="hedPg" nowrap="nowrap">
<html:hidden property="value(filterlprp)" styleId="filterlprp" value="<%=filterlprp%>" />
  Next Grid By : <html:select property="value(gridbylprp)" name="orclReportForm"  style="width:100px" styleId="gridbylprp" onchange=""  >
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
  <html:button property="value(dflt)" value="Load Original Grid"  onclick="goTo('ifrsaction.do?method=loadstkopenclosereset','','')" styleClass="submit" />
  </td>
  </tr>
  <%
   String msg = (String)request.getAttribute("msg");
   if(msg!=null){%>
     <tr><td valign="top" class="tdLayout"><%=msg%></td></tr>
   <%}%>
<tr>
<td valign="top" class="hedPg">
<table class="grid1small">
<tr>
<th></th>
<th colspan="8">Opening</th>
<th colspan="8">Mfg New</th>
<th colspan="8">Purchase New</th>
<th colspan="8">Dept Trf In</th>
<th colspan="9">Sold</th>
<th colspan="8">Balance</th>
</tr>
<tr>
<td></td>
<td align="center" nowrap="nowrap">QTY</td>
<td align="center" nowrap="nowrap">CTS</td>
<td align="center" nowrap="nowrap">RTE</td>
<td align="center" nowrap="nowrap">RAP</td>
<td align="center" nowrap="nowrap">VLU</td>
<td align="center" nowrap="nowrap">CP</td>
<td align="center" nowrap="nowrap">CP DIS</td>
<td align="center" nowrap="nowrap">CPVLU</td>
<td align="center" nowrap="nowrap">QTY</td>
<td align="center" nowrap="nowrap">CTS</td>
<td align="center" nowrap="nowrap">RTE</td>
<td align="center" nowrap="nowrap">RAP</td>
<td align="center" nowrap="nowrap">VLU</td>
<td align="center" nowrap="nowrap">CP</td>
<td align="center" nowrap="nowrap">CP DIS</td>
<td align="center" nowrap="nowrap">CPVLU</td>
<td align="center" nowrap="nowrap">QTY</td>
<td align="center" nowrap="nowrap">CTS</td>
<td align="center" nowrap="nowrap">RTE</td>
<td align="center" nowrap="nowrap">RAP</td>
<td align="center" nowrap="nowrap">VLU</td>
<td align="center" nowrap="nowrap">CP</td>
<td align="center" nowrap="nowrap">CP DIS</td>
<td align="center" nowrap="nowrap">CPVLU</td>
<td align="center" nowrap="nowrap">QTY</td>
<td align="center" nowrap="nowrap">CTS</td>
<td align="center" nowrap="nowrap">RTE</td>
<td align="center" nowrap="nowrap">RAP</td>
<td align="center" nowrap="nowrap">VLU</td>
<td align="center" nowrap="nowrap">CP</td>
<td align="center" nowrap="nowrap">CP DIS</td>
<td align="center" nowrap="nowrap">CPVLU</td>
<td align="center" nowrap="nowrap">QTY</td>
<td align="center" nowrap="nowrap">CTS</td>
<td align="center" nowrap="nowrap">RTE</td>
<td align="center" nowrap="nowrap">RAP</td>
<td align="center" nowrap="nowrap">VLU</td>
<td align="center" nowrap="nowrap">CP</td>
<td align="center" nowrap="nowrap">CP DIS</td>
<td align="center" nowrap="nowrap">CPVLU</td>
<td align="center" nowrap="nowrap">G/P %</td>
<td align="center" nowrap="nowrap">QTY</td>
<td align="center" nowrap="nowrap">CTS</td>
<td align="center" nowrap="nowrap">RTE</td>
<td align="center" nowrap="nowrap">RAP</td>
<td align="center" nowrap="nowrap">VLU</td>
<td align="center" nowrap="nowrap">CP</td>
<td align="center" nowrap="nowrap">CP DIS</td>
<td align="center" nowrap="nowrap">CPVLU</td>
</tr>

<%
BigDecimal closingTTlVlu = ((BigDecimal)dscttlLst.get("CLOSE_VLU") == null)?new BigDecimal(0):(BigDecimal)dscttlLst.get("CLOSE_VLU");
BigDecimal openTTlVlu = ((BigDecimal)dscttlLst.get("OPEN_VLU") == null)?new BigDecimal(0):(BigDecimal)dscttlLst.get("OPEN_VLU");
BigDecimal soldTTlVlu= ((BigDecimal)dscttlLst.get("SOLD_VLU") == null)?new BigDecimal(0):(BigDecimal)dscttlLst.get("SOLD_VLU");
for (int i=0;i<commListsz;i++){
String prpVal=(String)commList.get(i);
if(stkoenclosegtlevel1List.contains(prpVal)){
BigDecimal closingQtyBig = (BigDecimal)dscttlLst.get(prpVal+"_CLOSE_QTY");
BigDecimal closingMfgCtsBig = (BigDecimal)dscttlLst.get(prpVal+"_CLOSE_MFGCTS");
BigDecimal closingCtsBig = (BigDecimal)dscttlLst.get(prpVal+"_CLOSE_CTS");
BigDecimal closingVluBig = (BigDecimal)dscttlLst.get(prpVal+"_CLOSE_VLU");
BigDecimal closingcpVluBig = (BigDecimal)dscttlLst.get(prpVal+"_CLOSE_CPVLU");
BigDecimal closingRapVluBig = (BigDecimal)dscttlLst.get(prpVal+"_CLOSE_RAPVLU");
if(closingQtyBig==null)
closingQtyBig=new BigDecimal(0);
if(closingMfgCtsBig==null)
closingMfgCtsBig=new BigDecimal(0);
if(closingCtsBig==null)
closingCtsBig=new BigDecimal(0);
if(closingVluBig==null)
closingVluBig=new BigDecimal(0);
if(closingcpVluBig==null)
closingcpVluBig=new BigDecimal(0);
if(closingRapVluBig==null)
closingRapVluBig=new BigDecimal(0);
String closingQty=util.nvl((String)closingQtyBig.toString(),"0");
String closingCts=util.nvl((String)closingCtsBig.toString(),"0");
String closingMfgCts=util.nvl((String)closingMfgCtsBig.toString(),"0");
String closingVlu=util.nvl((String)closingVluBig.toString(),"0");
String closingcpVlu=util.nvl((String)closingcpVluBig.toString(),"0");
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
String closingcpAvg="0";
if(!closingCts.equals("0") && !closingcpVlu.equals("0")){
closingcpAvg=String.valueOf(util.roundToDecimals(Double.parseDouble(closingcpVlu)/Double.parseDouble(closingCts),2));
}
String closingcpRAP="";
if(!closingcpVlu.equals("0") && !closingRapVlu.equals("0")){
closingcpRAP=String.valueOf(util.roundToDecimals(((Double.parseDouble(closingcpVlu)/Double.parseDouble(closingRapVlu))*100)-100,2));
}
String closingcpAvgDis="";
if(!closingcpVlu.equals("0") && !closingRapVlu.equals("0")){
closingcpAvgDis=String.valueOf(util.roundToDecimals(((Double.parseDouble(closingcpVlu)/Double.parseDouble(closingRapVlu))*100)-100,2));
}


String perClosing ="";
if(!perClosing.equals("0")){
perClosing=String.valueOf(util.roundToDecimals((Double.parseDouble(closingVlu)*100/Double.parseDouble(String.valueOf(closingTTlVlu))),2));
}

BigDecimal mfgnewQtyBig = (BigDecimal)dscttlLst.get(prpVal+"_MFGNEW_QTY");
BigDecimal mfgnewMfgCtsBig = (BigDecimal)dscttlLst.get(prpVal+"_MFGNEW_MFGCTS");
BigDecimal mfgnewCtsBig = (BigDecimal)dscttlLst.get(prpVal+"_MFGNEW_CTS");
BigDecimal mfgnewVluBig = (BigDecimal)dscttlLst.get(prpVal+"_MFGNEW_VLU");
BigDecimal mfgnewcpVluBig = (BigDecimal)dscttlLst.get(prpVal+"_MFGNEW_CPVLU");
BigDecimal mfgnewRapVluBig = (BigDecimal)dscttlLst.get(prpVal+"_MFGNEW_RAPVLU");
if(mfgnewQtyBig==null)
mfgnewQtyBig=new BigDecimal(0);
if(mfgnewMfgCtsBig==null)
mfgnewMfgCtsBig=new BigDecimal(0);
if(mfgnewCtsBig==null)
mfgnewCtsBig=new BigDecimal(0);
if(mfgnewVluBig==null)
mfgnewVluBig=new BigDecimal(0);
if(mfgnewcpVluBig==null)
mfgnewcpVluBig=new BigDecimal(0);
if(mfgnewRapVluBig==null)
mfgnewRapVluBig=new BigDecimal(0);
String mfgnewQty=util.nvl((String)mfgnewQtyBig.toString(),"0");
String mfgnewMfgCts=util.nvl((String)mfgnewMfgCtsBig.toString(),"0");
String mfgnewCts=util.nvl((String)mfgnewCtsBig.toString(),"0");
String mfgnewVlu=util.nvl((String)mfgnewVluBig.toString(),"0");
String mfgnewcpVlu=util.nvl((String)mfgnewcpVluBig.toString(),"0");
String mfgnewRapVlu=util.nvl((String)mfgnewRapVluBig.toString(),"0");
String mfgnewAvg="0";
if(!mfgnewCts.equals("0") && !mfgnewVlu.equals("0")){
mfgnewAvg=String.valueOf(Math.round(Double.parseDouble(mfgnewVlu)/Double.parseDouble(mfgnewCts)));
}
String mfgnewRAP="";
if(!mfgnewVlu.equals("0") && !mfgnewRapVlu.equals("0")){
mfgnewRAP=String.valueOf(util.roundToDecimals(((Double.parseDouble(mfgnewVlu)/Double.parseDouble(mfgnewRapVlu))*100)-100,2));
}
String mfgnewcpAvg="0";
if(!mfgnewCts.equals("0") && !mfgnewcpVlu.equals("0")){
mfgnewcpAvg=String.valueOf(util.roundToDecimals(Double.parseDouble(mfgnewcpVlu)/Double.parseDouble(mfgnewCts),2));
}
String mfgnewcpRAP="";
if(!mfgnewcpVlu.equals("0") && !mfgnewRapVlu.equals("0")){
mfgnewcpRAP=String.valueOf(util.roundToDecimals(((Double.parseDouble(mfgnewcpVlu)/Double.parseDouble(mfgnewRapVlu))*100)-100,2));
}

BigDecimal purnewQtyBig = (BigDecimal)dscttlLst.get(prpVal+"_PURNEW_QTY");
BigDecimal purnewMfgCtsBig = (BigDecimal)dscttlLst.get(prpVal+"_PURNEW_MFGCTS");
BigDecimal purnewCtsBig = (BigDecimal)dscttlLst.get(prpVal+"_PURNEW_CTS");
BigDecimal purnewVluBig = (BigDecimal)dscttlLst.get(prpVal+"_PURNEW_VLU");
BigDecimal purnewcpVluBig = (BigDecimal)dscttlLst.get(prpVal+"_PURNEW_CPVLU");
BigDecimal purnewRapVluBig = (BigDecimal)dscttlLst.get(prpVal+"_PURNEW_RAPVLU");
if(purnewQtyBig==null)
purnewQtyBig=new BigDecimal(0);
if(purnewMfgCtsBig==null)
purnewMfgCtsBig=new BigDecimal(0);
if(purnewCtsBig==null)
purnewCtsBig=new BigDecimal(0);
if(purnewVluBig==null)
purnewVluBig=new BigDecimal(0);
if(purnewcpVluBig==null)
purnewcpVluBig=new BigDecimal(0);
if(purnewRapVluBig==null)
purnewRapVluBig=new BigDecimal(0);
String purnewQty=util.nvl((String)purnewQtyBig.toString(),"0");
String purnewMfgCts=util.nvl((String)purnewMfgCtsBig.toString(),"0");
String purnewCts=util.nvl((String)purnewCtsBig.toString(),"0");
String purnewVlu=util.nvl((String)purnewVluBig.toString(),"0");
String purnewcpVlu=util.nvl((String)purnewcpVluBig.toString(),"0");
String purnewRapVlu=util.nvl((String)purnewRapVluBig.toString(),"0");
String purnewAvg="0";
if(!purnewCts.equals("0") && !purnewVlu.equals("0")){
purnewAvg=String.valueOf(Math.round(Double.parseDouble(purnewVlu)/Double.parseDouble(purnewCts)));
}
String purnewRAP="";
if(!purnewVlu.equals("0") && !purnewRapVlu.equals("0")){
purnewRAP=String.valueOf(util.roundToDecimals(((Double.parseDouble(purnewVlu)/Double.parseDouble(purnewRapVlu))*100)-100,2));
}
String purnewcpAvg="0";
if(!purnewCts.equals("0") && !purnewcpVlu.equals("0")){
purnewcpAvg=String.valueOf(util.roundToDecimals(Double.parseDouble(purnewcpVlu)/Double.parseDouble(purnewCts),2));
}
String purnewcpRAP="";
if(!purnewcpVlu.equals("0") && !purnewRapVlu.equals("0")){
purnewcpRAP=String.valueOf(util.roundToDecimals(((Double.parseDouble(purnewcpVlu)/Double.parseDouble(purnewRapVlu))*100)-100,2));
}

BigDecimal mixtosingleQtyBig = (BigDecimal)dscttlLst.get(prpVal+"_MIXTOSNGL_QTY");
BigDecimal mixtosingleMfgCtsBig = (BigDecimal)dscttlLst.get(prpVal+"_MIXTOSNGL_MFGCTS");
BigDecimal mixtosingleCtsBig = (BigDecimal)dscttlLst.get(prpVal+"_MIXTOSNGL_CTS");
BigDecimal mixtosingleVluBig = (BigDecimal)dscttlLst.get(prpVal+"_MIXTOSNGL_VLU");
BigDecimal mixtosinglecpVluBig = (BigDecimal)dscttlLst.get(prpVal+"_MIXTOSNGL_CPVLU");
BigDecimal mixtosingleRapVluBig = (BigDecimal)dscttlLst.get(prpVal+"_MIXTOSNGL_RAPVLU");
if(mixtosingleQtyBig==null)
mixtosingleQtyBig=new BigDecimal(0);
if(mixtosingleMfgCtsBig==null)
mixtosingleMfgCtsBig=new BigDecimal(0);
if(mixtosingleCtsBig==null)
mixtosingleCtsBig=new BigDecimal(0);
if(mixtosingleVluBig==null)
mixtosingleVluBig=new BigDecimal(0);
if(mixtosinglecpVluBig==null)
mixtosinglecpVluBig=new BigDecimal(0);
if(mixtosingleRapVluBig==null)
mixtosingleRapVluBig=new BigDecimal(0);
String mixtosingleQty=util.nvl((String)mixtosingleQtyBig.toString(),"0");
String mixtosingleMfgCts=util.nvl((String)mixtosingleMfgCtsBig.toString(),"0");
String mixtosingleCts=util.nvl((String)mixtosingleCtsBig.toString(),"0");
String mixtosingleVlu=util.nvl((String)mixtosingleVluBig.toString(),"0");
String mixtosinglecpVlu=util.nvl((String)mixtosinglecpVluBig.toString(),"0");
String mixtosingleRapVlu=util.nvl((String)mixtosingleRapVluBig.toString(),"0");
String mixtosingleAvg="0";
if(!mixtosingleCts.equals("0") && !mixtosingleVlu.equals("0")){
mixtosingleAvg=String.valueOf(Math.round(Double.parseDouble(mixtosingleVlu)/Double.parseDouble(mixtosingleCts)));
}
String mixtosingleRAP="";
if(!mixtosingleVlu.equals("0") && !mixtosingleRapVlu.equals("0")){
mixtosingleRAP=String.valueOf(util.roundToDecimals(((Double.parseDouble(mixtosingleVlu)/Double.parseDouble(mixtosingleRapVlu))*100)-100,2));
}
String mixtosinglecpAvg="0";
if(!mixtosingleCts.equals("0") && !mixtosinglecpVlu.equals("0")){
mixtosinglecpAvg=String.valueOf(util.roundToDecimals(Double.parseDouble(mixtosinglecpVlu)/Double.parseDouble(mixtosingleCts),2));
}
String mixtosinglecpRAP="";
if(!mixtosinglecpVlu.equals("0") && !mixtosingleRapVlu.equals("0")){
mixtosinglecpRAP=String.valueOf(util.roundToDecimals(((Double.parseDouble(mixtosinglecpVlu)/Double.parseDouble(mixtosingleRapVlu))*100)-100,2));
}

BigDecimal soldQtyBig = (BigDecimal)dscttlLst.get(prpVal+"_SOLD_QTY");
BigDecimal soldMfgCtsBig = (BigDecimal)dscttlLst.get(prpVal+"_SOLD_MFGCTS");
BigDecimal soldCtsBig = (BigDecimal)dscttlLst.get(prpVal+"_SOLD_CTS");
BigDecimal soldVluBig = (BigDecimal)dscttlLst.get(prpVal+"_SOLD_VLU");
BigDecimal soldcpVluBig = (BigDecimal)dscttlLst.get(prpVal+"_SOLD_CPVLU");
BigDecimal soldRapVluBig = (BigDecimal)dscttlLst.get(prpVal+"_SOLD_RAPVLU");
BigDecimal soldTtlVluBig = new BigDecimal(0);
if(soldQtyBig==null)
soldQtyBig=new  BigDecimal(0);
if(soldMfgCtsBig==null)
soldMfgCtsBig=new  BigDecimal(0);
if(soldCtsBig==null)
soldCtsBig=new  BigDecimal(0);
if(soldVluBig==null)
soldVluBig=new  BigDecimal(0);
if(soldcpVluBig==null)
soldcpVluBig=new  BigDecimal(0);
if(soldRapVluBig==null)
soldRapVluBig=new  BigDecimal(0);
soldTtlVluBig=soldTtlVluBig.add(soldVluBig);
String soldQty=util.nvl((String)soldQtyBig.toString(),"0");
String soldMfgCts=util.nvl((String)soldCtsBig.toString(),"0");
String soldCts=util.nvl((String)soldCtsBig.toString(),"0");
String soldVlu=util.nvl((String)soldVluBig.toString(),"0");
String soldcpVlu=util.nvl((String)soldcpVluBig.toString(),"0");
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
String soldcpAvg="0";
if(!soldCts.equals("0") && !soldcpVlu.equals("0")){
soldcpAvg=String.valueOf(util.roundToDecimals(Double.parseDouble(soldcpVlu)/Double.parseDouble(soldCts),2));
}
String soldcpRAP="";
if(!soldcpVlu.equals("0") && !soldRapVlu.equals("0")){
soldcpRAP=String.valueOf(util.roundToDecimals(((Double.parseDouble(soldcpVlu)/Double.parseDouble(soldRapVlu))*100)-100,2));
}
String percpSold ="";
if(!percpSold.equals("0")){
percpSold=String.valueOf(util.roundToDecimals((Double.parseDouble(soldcpVlu)*100/Double.parseDouble(String.valueOf(soldTTlVlu))),2));
}

BigDecimal openQtyBig = (BigDecimal)dscttlLst.get(prpVal+"_OPEN_QTY");
BigDecimal openMfgCtsBig = (BigDecimal)dscttlLst.get(prpVal+"_OPEN_MFGCTS");
BigDecimal openCtsBig = (BigDecimal)dscttlLst.get(prpVal+"_OPEN_CTS");
BigDecimal openVluBig = (BigDecimal)dscttlLst.get(prpVal+"_OPEN_VLU");
BigDecimal opencpVluBig = (BigDecimal)dscttlLst.get(prpVal+"_OPEN_CPVLU");
BigDecimal openRapVluBig = (BigDecimal)dscttlLst.get(prpVal+"_OPEN_RAPVLU");
BigDecimal openTtlVluBig = new BigDecimal(0);
if(openQtyBig==null)
openQtyBig=new  BigDecimal(0);
if(openMfgCtsBig==null)
openMfgCtsBig=new  BigDecimal(0);
if(openCtsBig==null)
openCtsBig=new  BigDecimal(0);
if(openVluBig==null)
openVluBig=new  BigDecimal(0);
if(opencpVluBig==null)
opencpVluBig=new  BigDecimal(0);
if(openRapVluBig==null)
openRapVluBig=new  BigDecimal(0);
openTtlVluBig=openTtlVluBig.add(openVluBig);
String openQty=util.nvl((String)openQtyBig.toString(),"0");
String openMfgCts=util.nvl((String)openMfgCtsBig.toString(),"0");
String openCts=util.nvl((String)openCtsBig.toString(),"0");
String openVlu=util.nvl((String)openVluBig.toString(),"0");
String opencpVlu=util.nvl((String)opencpVluBig.toString(),"0");
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
String opencpAvg="0";
if(!openCts.equals("0") && !opencpVlu.equals("0")){
opencpAvg=String.valueOf(util.roundToDecimals(Double.parseDouble(opencpVlu)/Double.parseDouble(openCts),2));
}
String opencpRAP="";
if(!opencpVlu.equals("0") && !openRapVlu.equals("0")){
opencpRAP=String.valueOf(util.roundToDecimals(((Double.parseDouble(opencpVlu)/Double.parseDouble(openRapVlu))*100)-100,2));
}

String soldTtlVlu =  (String)soldTtlVluBig.toString();
%>
<tr>
<td align="right" style="padding:0px"><span><a  title="report" href="#" onclick="filteropenclosescreenifrs('<%=info.getReqUrl()%>','<%=prpVal%>');" ><%=prpVal%></a></span></td>
<td align="right" style="padding:0px"><span><%=openQty%></span></td>
<td align="right"><a  title="Packet Wise report" href="ifrsaction.do?method=pktDtlStockOpenClose&dsp_stt=OPEN&filterlprpval=<%=prpVal%>&filterlprp=<%=level1lprp%>"  target="_blank"><%=openCts%></a></td>
<td align="right"><%=openAvg%></td>
<td align="right"><%=openRAP%></td>
<td align="right" title="Vlu / 1000000" ><%=util.roundToDecimals(Double.parseDouble(openVlu)/1000000,2)%></td>
<td align="right"><%=opencpAvg%></td>
<td align="right"><%=opencpRAP%></td>
<td align="right" title="Vlu / 1000000" ><%=util.roundToDecimals(Double.parseDouble(opencpVlu)/1000000,2)%></td>

<td align="right" style="padding:0px"><span><%=mfgnewQty%></span></td>
<td align="right"><a  title="Packet Wise report" href="ifrsaction.do?method=pktDtlStockOpenClose&dsp_stt=MFGNEW&filterlprpval=<%=prpVal%>&filterlprp=<%=level1lprp%>"  target="_blank"><%=mfgnewCts%></a></td>
<td align="right"><%=mfgnewAvg%></td>
<td align="right"><%=mfgnewRAP%></td>
<td align="right" title="Vlu / 1000000" ><%=util.roundToDecimals(Double.parseDouble(mfgnewVlu)/1000000,2)%></td>
<td align="right"><%=mfgnewcpAvg%></td>
<td align="right"><%=mfgnewcpRAP%></td>
<td align="right" title="Vlu / 1000000" ><%=util.roundToDecimals(Double.parseDouble(mfgnewcpVlu)/1000000,2)%></td>

<td align="right" style="padding:0px"><span><%=purnewQty%></span></td>
<td align="right"><a  title="Packet Wise report" href="ifrsaction.do?method=pktDtlStockOpenClose&dsp_stt=PURNEW&filterlprpval=<%=prpVal%>&filterlprp=<%=level1lprp%>"  target="_blank"><%=purnewCts%></a></td>
<td align="right"><%=purnewAvg%></td>
<td align="right"><%=purnewRAP%></td>
<td align="right" title="Vlu / 1000000" ><%=util.roundToDecimals(Double.parseDouble(purnewVlu)/1000000,2)%></td>
<td align="right"><%=purnewcpAvg%></td>
<td align="right"><%=purnewcpRAP%></td>
<td align="right" title="Vlu / 1000000" ><%=util.roundToDecimals(Double.parseDouble(purnewcpVlu)/1000000,2)%></td>

<td align="right" style="padding:0px"><span><%=mixtosingleQty%></span></td>
<td align="right"><a  title="Packet Wise report" href="ifrsaction.do?method=pktDtlStockOpenClose&dsp_stt=MIXTOSNGL&filterlprpval=<%=prpVal%>&filterlprp=<%=level1lprp%>"  target="_blank"><%=mixtosingleCts%></a></td>
<td align="right"><%=mixtosingleAvg%></td>
<td align="right"><%=mixtosingleRAP%></td>
<td align="right" title="Vlu / 1000000" ><%=util.roundToDecimals(Double.parseDouble(mixtosingleVlu)/1000000,2)%></td>
<td align="right"><%=mixtosinglecpAvg%></td>
<td align="right"><%=mixtosinglecpRAP%></td>
<td align="right" title="Vlu / 1000000" ><%=util.roundToDecimals(Double.parseDouble(mixtosinglecpVlu)/1000000,2)%></td>

<td align="right" style="padding:0px"><span><%=soldQty%></span></td>
<td align="right"><a  title="Packet Wise report" href="ifrsaction.do?method=pktDtlStockOpenClose&dsp_stt=SOLD&filterlprpval=<%=prpVal%>&filterlprp=<%=level1lprp%>"  target="_blank"><%=soldCts%></a></td>
<td align="right"><%=soldAvg%></td>
<td align="right"><%=soldRAP%></td>
<td align="right" title="Vlu / 1000000" ><%=util.roundToDecimals(Double.parseDouble(soldVlu)/1000000,2)%></td>
<td align="right"><%=soldcpAvg%></td>
<td align="right"><%=soldcpRAP%></td>
<td align="right" title="Vlu / 1000000" ><%=util.roundToDecimals(Double.parseDouble(soldcpVlu)/1000000,2)%></td>
<td align="right"><%=String.valueOf(util.roundToDecimals((Double.parseDouble(soldVlu)-Double.parseDouble(soldcpVlu))/Double.parseDouble(soldVlu),2))%></td>

<td align="right" style="padding:0px"><span><%=closingQty%></span></td>
<td align="right"><a  title="Packet Wise report" href="ifrsaction.do?method=pktDtlStockOpenClose&dsp_stt=CLOSE&filterlprpval=<%=prpVal%>&filterlprp=<%=level1lprp%>"  target="_blank"><%=closingCts%></a></td>
<td align="right"><%=closingAvg%></td>
<td align="right"><%=closingAvgDis%></td>
<td align="right" title="Vlu / 1000000" ><%=util.roundToDecimals(Double.parseDouble(closingVlu)/1000000,2)%></td>
<td align="right"><%=closingcpAvg%></td>
<td align="right"><%=closingcpAvgDis%></td>
<td align="right" title="Vlu / 1000000" ><%=util.roundToDecimals(Double.parseDouble(closingcpVlu)/1000000,2)%></td>
</tr>
<%}}%>
<tr>
<td><span><a  title="report" href="#" onclick="filteropenclosescreenifrs('<%=info.getReqUrl()%>','ALL');" >Total</a></span></td>
<%
BigDecimal closingQtyBig = (BigDecimal)dscttlLst.get("CLOSE_QTY");
BigDecimal closingMfgCtsBig = (BigDecimal)dscttlLst.get("CLOSE_MFGCTS");
BigDecimal closingCtsBig = (BigDecimal)dscttlLst.get("CLOSE_CTS");
BigDecimal closingVluBig = (BigDecimal)dscttlLst.get("CLOSE_VLU");
BigDecimal closingcpVluBig = (BigDecimal)dscttlLst.get("CLOSE_CPVLU");
BigDecimal closingRapVluBig = (BigDecimal)dscttlLst.get("CLOSE_RAPVLU");
if(closingQtyBig==null)
closingQtyBig=new BigDecimal(0);
if(closingMfgCtsBig==null)
closingMfgCtsBig=new BigDecimal(0);
if(closingCtsBig==null)
closingCtsBig=new BigDecimal(0);
if(closingVluBig==null)
closingVluBig=new BigDecimal(0);
if(closingcpVluBig==null)
closingcpVluBig=new BigDecimal(0);
if(closingRapVluBig==null)
closingRapVluBig=new BigDecimal(0);
String closingQty=util.nvl((String)closingQtyBig.toString(),"0");
String closingMfgCts=util.nvl((String)closingMfgCtsBig.toString(),"0");
String closingCts=util.nvl((String)closingCtsBig.toString(),"0");
String closingVlu=util.nvl((String)closingVluBig.toString(),"0");
String closingcpVlu=util.nvl((String)closingcpVluBig.toString(),"0");
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
String closingcpAvg="0";
if(!closingCts.equals("0") && !closingcpVlu.equals("0")){
closingcpAvg=String.valueOf(util.roundToDecimals(Double.parseDouble(closingcpVlu)/Double.parseDouble(closingCts),2));
}
String closingcpRAP="";
if(!closingcpVlu.equals("0") && !closingRapVlu.equals("0")){
closingcpRAP=String.valueOf(util.roundToDecimals(((Double.parseDouble(closingcpVlu)/Double.parseDouble(closingRapVlu))*100)-100,2));
}
String closingcpAvgDis="";
if(!closingcpVlu.equals("0") && !closingRapVlu.equals("0")){
closingcpAvgDis=String.valueOf(util.roundToDecimals(((Double.parseDouble(closingcpVlu)/Double.parseDouble(closingRapVlu))*100)-100,2));
}


BigDecimal mfgnewQtyBig = (BigDecimal)dscttlLst.get("MFGNEW_QTY");
BigDecimal mfgnewMfgCtsBig = (BigDecimal)dscttlLst.get("MFGNEW_MFGCTS");
BigDecimal mfgnewCtsBig = (BigDecimal)dscttlLst.get("MFGNEW_CTS");
BigDecimal mfgnewVluBig = (BigDecimal)dscttlLst.get("MFGNEW_VLU");
BigDecimal mfgnewcpVluBig = (BigDecimal)dscttlLst.get("MFGNEW_CPVLU");
BigDecimal mfgnewRapVluBig = (BigDecimal)dscttlLst.get("MFGNEW_RAPVLU");
if(mfgnewQtyBig==null)
mfgnewQtyBig=new BigDecimal(0);
if(mfgnewMfgCtsBig==null)
mfgnewMfgCtsBig=new BigDecimal(0);
if(mfgnewCtsBig==null)
mfgnewCtsBig=new BigDecimal(0);
if(mfgnewVluBig==null)
mfgnewVluBig=new BigDecimal(0);
if(mfgnewcpVluBig==null)
mfgnewcpVluBig=new BigDecimal(0);
if(mfgnewRapVluBig==null)
mfgnewRapVluBig=new BigDecimal(0);
String mfgnewQty=util.nvl((String)mfgnewQtyBig.toString(),"0");
String mfgnewMfgCts=util.nvl((String)mfgnewMfgCtsBig.toString(),"0");
String mfgnewCts=util.nvl((String)mfgnewCtsBig.toString(),"0");
String mfgnewVlu=util.nvl((String)mfgnewVluBig.toString(),"0");
String mfgnewcpVlu=util.nvl((String)mfgnewcpVluBig.toString(),"0");
String mfgnewRapVlu=util.nvl((String)mfgnewRapVluBig.toString(),"0");
String mfgnewAvg="0";
if(!mfgnewCts.equals("0") && !mfgnewVlu.equals("0")){
mfgnewAvg=String.valueOf(Math.round(Double.parseDouble(mfgnewVlu)/Double.parseDouble(mfgnewCts)));
}
String mfgnewRAP="";
if(!mfgnewVlu.equals("0") && !mfgnewRapVlu.equals("0")){
mfgnewRAP=String.valueOf(util.roundToDecimals(((Double.parseDouble(mfgnewVlu)/Double.parseDouble(mfgnewRapVlu))*100)-100,2));
}
String mfgnewcpAvg="0";
if(!mfgnewCts.equals("0") && !mfgnewcpVlu.equals("0")){
mfgnewcpAvg=String.valueOf(util.roundToDecimals(Double.parseDouble(mfgnewcpVlu)/Double.parseDouble(mfgnewCts),2));
}
String mfgnewcpRAP="";
if(!mfgnewcpVlu.equals("0") && !mfgnewRapVlu.equals("0")){
mfgnewcpRAP=String.valueOf(util.roundToDecimals(((Double.parseDouble(mfgnewcpVlu)/Double.parseDouble(mfgnewRapVlu))*100)-100,2));
}

BigDecimal purnewQtyBig = (BigDecimal)dscttlLst.get("PURNEW_QTY");
BigDecimal purnewMfgCtsBig = (BigDecimal)dscttlLst.get("PURNEW_MFGCTS");
BigDecimal purnewCtsBig = (BigDecimal)dscttlLst.get("PURNEW_CTS");
BigDecimal purnewVluBig = (BigDecimal)dscttlLst.get("PURNEW_VLU");
BigDecimal purnewcpVluBig = (BigDecimal)dscttlLst.get("PURNEW_CPVLU");
BigDecimal purnewRapVluBig = (BigDecimal)dscttlLst.get("PURNEW_RAPVLU");
if(purnewQtyBig==null)
purnewQtyBig=new BigDecimal(0);
if(purnewMfgCtsBig==null)
purnewMfgCtsBig=new BigDecimal(0);
if(purnewCtsBig==null)
purnewCtsBig=new BigDecimal(0);
if(purnewVluBig==null)
purnewVluBig=new BigDecimal(0);
if(purnewcpVluBig==null)
purnewcpVluBig=new BigDecimal(0);
if(purnewRapVluBig==null)
purnewRapVluBig=new BigDecimal(0);
String purnewQty=util.nvl((String)purnewQtyBig.toString(),"0");
String purnewCts=util.nvl((String)purnewCtsBig.toString(),"0");
String purnewMfgCts=util.nvl((String)purnewMfgCtsBig.toString(),"0");
String purnewVlu=util.nvl((String)purnewVluBig.toString(),"0");
String purnewcpVlu=util.nvl((String)purnewcpVluBig.toString(),"0");
String purnewRapVlu=util.nvl((String)purnewRapVluBig.toString(),"0");
String purnewAvg="0";
if(!purnewCts.equals("0") && !purnewVlu.equals("0")){
purnewAvg=String.valueOf(Math.round(Double.parseDouble(purnewVlu)/Double.parseDouble(purnewCts)));
}
String purnewRAP="";
if(!purnewVlu.equals("0") && !purnewRapVlu.equals("0")){
purnewRAP=String.valueOf(util.roundToDecimals(((Double.parseDouble(purnewVlu)/Double.parseDouble(purnewRapVlu))*100)-100,2));
}
String purnewcpAvg="0";
if(!purnewCts.equals("0") && !purnewcpVlu.equals("0")){
purnewcpAvg=String.valueOf(util.roundToDecimals(Double.parseDouble(purnewcpVlu)/Double.parseDouble(purnewCts),2));
}
String purnewcpRAP="";
if(!purnewcpVlu.equals("0") && !purnewRapVlu.equals("0")){
purnewcpRAP=String.valueOf(util.roundToDecimals(((Double.parseDouble(purnewcpVlu)/Double.parseDouble(purnewRapVlu))*100)-100,2));
}

BigDecimal mixtosingleQtyBig = (BigDecimal)dscttlLst.get("MIXTOSNGL_QTY");
BigDecimal mixtosingleMfgCtsBig = (BigDecimal)dscttlLst.get("MIXTOSNGL_MFGCTS");
BigDecimal mixtosingleCtsBig = (BigDecimal)dscttlLst.get("MIXTOSNGL_CTS");
BigDecimal mixtosingleVluBig = (BigDecimal)dscttlLst.get("MIXTOSNGL_VLU");
BigDecimal mixtosinglecpVluBig = (BigDecimal)dscttlLst.get("MIXTOSNGL_CPVLU");
BigDecimal mixtosingleRapVluBig = (BigDecimal)dscttlLst.get("MIXTOSNGL_RAPVLU");
if(mixtosingleQtyBig==null)
mixtosingleQtyBig=new BigDecimal(0);
if(mixtosingleMfgCtsBig==null)
mixtosingleMfgCtsBig=new BigDecimal(0);
if(mixtosingleCtsBig==null)
mixtosingleCtsBig=new BigDecimal(0);
if(mixtosingleVluBig==null)
mixtosingleVluBig=new BigDecimal(0);
if(mixtosinglecpVluBig==null)
mixtosinglecpVluBig=new BigDecimal(0);
if(mixtosingleRapVluBig==null)
mixtosingleRapVluBig=new BigDecimal(0);
String mixtosingleQty=util.nvl((String)mixtosingleQtyBig.toString(),"0");
String mixtosingleCts=util.nvl((String)mixtosingleCtsBig.toString(),"0");
String mixtosingleMfgCts=util.nvl((String)mixtosingleMfgCtsBig.toString(),"0");
String mixtosingleVlu=util.nvl((String)mixtosingleVluBig.toString(),"0");
String mixtosinglecpVlu=util.nvl((String)mixtosinglecpVluBig.toString(),"0");
String mixtosingleRapVlu=util.nvl((String)mixtosingleRapVluBig.toString(),"0");
String mixtosingleAvg="0";
if(!mixtosingleCts.equals("0") && !mixtosingleVlu.equals("0")){
mixtosingleAvg=String.valueOf(Math.round(Double.parseDouble(mixtosingleVlu)/Double.parseDouble(mixtosingleCts)));
}
String mixtosingleRAP="";
if(!mixtosingleVlu.equals("0") && !mixtosingleRapVlu.equals("0")){
mixtosingleRAP=String.valueOf(util.roundToDecimals(((Double.parseDouble(mixtosingleVlu)/Double.parseDouble(mixtosingleRapVlu))*100)-100,2));
}
String mixtosinglecpAvg="0";
if(!mixtosingleCts.equals("0") && !mixtosinglecpVlu.equals("0")){
mixtosinglecpAvg=String.valueOf(util.roundToDecimals(Double.parseDouble(mixtosinglecpVlu)/Double.parseDouble(mixtosingleCts),2));
}
String mixtosinglecpRAP="";
if(!mixtosinglecpVlu.equals("0") && !mixtosingleRapVlu.equals("0")){
mixtosinglecpRAP=String.valueOf(util.roundToDecimals(((Double.parseDouble(mixtosinglecpVlu)/Double.parseDouble(mixtosingleRapVlu))*100)-100,2));
}

BigDecimal soldQtyBig = (BigDecimal)dscttlLst.get("SOLD_QTY");
BigDecimal soldMfgCtsBig = (BigDecimal)dscttlLst.get("SOLD_MFGCTS");
BigDecimal soldCtsBig = (BigDecimal)dscttlLst.get("SOLD_CTS");
BigDecimal soldVluBig = (BigDecimal)dscttlLst.get("SOLD_VLU");
BigDecimal soldcpVluBig = (BigDecimal)dscttlLst.get("SOLD_CPVLU");
BigDecimal soldRapVluBig = (BigDecimal)dscttlLst.get("SOLD_RAPVLU");
if(soldQtyBig==null)
soldQtyBig=new  BigDecimal(0);
if(soldCtsBig==null)
soldCtsBig=new  BigDecimal(0);
if(soldMfgCtsBig==null)
soldMfgCtsBig=new  BigDecimal(0);
if(soldVluBig==null)
soldVluBig=new  BigDecimal(0);
if(soldcpVluBig==null)
soldcpVluBig=new  BigDecimal(0);
if(soldRapVluBig==null)
soldRapVluBig=new  BigDecimal(0);
String soldQty=util.nvl((String)soldQtyBig.toString(),"0");
String soldCts=util.nvl((String)soldCtsBig.toString(),"0");
String soldMfgCts=util.nvl((String)soldMfgCtsBig.toString(),"0");
String soldVlu=util.nvl((String)soldVluBig.toString(),"0");
String soldcpVlu=util.nvl((String)soldcpVluBig.toString(),"0");
String soldRapVlu=util.nvl((String)soldRapVluBig.toString(),"0");
String soldAvg="0";
if(!soldCts.equals("0") && !soldVlu.equals("0")){
soldAvg=String.valueOf(Math.round(Double.parseDouble(soldVlu)/Double.parseDouble(soldCts)));
}
String soldRAP="";
if(!soldVlu.equals("0") && !soldRapVlu.equals("0")){
soldRAP=String.valueOf(util.roundToDecimals(((Double.parseDouble(soldVlu)/Double.parseDouble(soldRapVlu))*100)-100,2));
}
String soldcpAvg="0";
if(!soldCts.equals("0") && !soldcpVlu.equals("0")){
soldcpAvg=String.valueOf(util.roundToDecimals(Double.parseDouble(soldcpVlu)/Double.parseDouble(soldCts),2));
}
String soldcpRAP="";
if(!soldcpVlu.equals("0") && !soldRapVlu.equals("0")){
soldcpRAP=String.valueOf(util.roundToDecimals(((Double.parseDouble(soldcpVlu)/Double.parseDouble(soldRapVlu))*100)-100,2));
}
BigDecimal soldTtlVluBig = new BigDecimal(0);
soldTtlVluBig=soldTtlVluBig.add(soldVluBig);

BigDecimal openQtyBig = (BigDecimal)dscttlLst.get("OPEN_QTY");
BigDecimal openMfgCtsBig = (BigDecimal)dscttlLst.get("OPEN_MFGCTS");
BigDecimal openCtsBig = (BigDecimal)dscttlLst.get("OPEN_CTS");
BigDecimal openVluBig = (BigDecimal)dscttlLst.get("OPEN_VLU");
BigDecimal opencpVluBig = (BigDecimal)dscttlLst.get("OPEN_CPVLU");
BigDecimal openRapVluBig = (BigDecimal)dscttlLst.get("OPEN_RAPVLU");
if(openQtyBig==null)
openQtyBig=new  BigDecimal(0);
if(openMfgCtsBig==null)
openMfgCtsBig=new  BigDecimal(0);
if(openCtsBig==null)
openCtsBig=new  BigDecimal(0);
if(openVluBig==null)
openVluBig=new  BigDecimal(0);
if(opencpVluBig==null)
opencpVluBig=new  BigDecimal(0);
if(openRapVluBig==null)
openRapVluBig=new  BigDecimal(0);
String openQty=util.nvl((String)openQtyBig.toString(),"0");
String openMfgCts=util.nvl((String)openMfgCtsBig.toString(),"0");
String openCts=util.nvl((String)openCtsBig.toString(),"0");
String openVlu=util.nvl((String)openVluBig.toString(),"0");
String opencpVlu=util.nvl((String)opencpVluBig.toString(),"0");
String openRapVlu=util.nvl((String)openRapVluBig.toString(),"0");
String openAvg="0";
if(!openCts.equals("0") && !openVlu.equals("0")){
openAvg=String.valueOf(Math.round(Double.parseDouble(openVlu)/Double.parseDouble(openCts)));
}
String openRAP="";
if(!openVlu.equals("0") && !openRapVlu.equals("0")){
openRAP=String.valueOf(util.roundToDecimals(((Double.parseDouble(openVlu)/Double.parseDouble(openRapVlu))*100)-100,2));
}
String opencpAvg="0";
if(!openCts.equals("0") && !opencpVlu.equals("0")){
opencpAvg=String.valueOf(util.roundToDecimals(Double.parseDouble(opencpVlu)/Double.parseDouble(openCts),2));
}
String opencpRAP="";
if(!opencpVlu.equals("0") && !openRapVlu.equals("0")){
opencpRAP=String.valueOf(util.roundToDecimals(((Double.parseDouble(opencpVlu)/Double.parseDouble(openRapVlu))*100)-100,2));
}
BigDecimal openTtlVluBig = new BigDecimal(0);
openTtlVluBig=openTtlVluBig.add(openVluBig);

String soldTtlVlu =  (String)soldTtlVluBig.toString();

String soldTtlVluttl =  (String)soldTtlVluBig.toString();
%>
<td align="right" style="padding:0px"><span><%=openQty%></span></td>
<td align="right"><a  title="Packet Wise report" href="ifrsaction.do?method=pktDtlStockOpenClose&dsp_stt=OPEN&filterlprpval=ALL&filterlprp=<%=level1lprp%>"  target="_blank"><%=openCts%></a></td>
<td align="right"><%=openAvg%></td>
<td align="right"><%=openRAP%></td>
<td align="right" title="Vlu / 1000000" ><%=util.roundToDecimals(Double.parseDouble(openVlu)/1000000,2)%></td>
<td align="right"><%=opencpAvg%></td>
<td align="right"><%=opencpRAP%></td>
<td align="right" title="Cp Vlu / 1000000" ><%=util.roundToDecimals(Double.parseDouble(opencpVlu)/1000000,2)%></td>
<td align="right" style="padding:0px"><span><%=mfgnewQty%></span></td>
<td align="right"><a  title="Packet Wise report" href="ifrsaction.do?method=pktDtlStockOpenClose&dsp_stt=MFGNEW&filterlprpval=ALL&filterlprp=<%=level1lprp%>"  target="_blank"><%=mfgnewCts%></a></td>
<td align="right"><%=mfgnewAvg%></td>
<td align="right"><%=mfgnewRAP%></td>
<td align="right" title="Vlu / 1000000" ><%=util.roundToDecimals(Double.parseDouble(mfgnewVlu)/1000000,2)%></td>
<td align="right"><%=mfgnewcpAvg%></td>
<td align="right"><%=mfgnewcpRAP%></td>
<td align="right" title="Cp Vlu / 1000000" ><%=util.roundToDecimals(Double.parseDouble(mfgnewcpVlu)/1000000,2)%></td>

<td align="right" style="padding:0px"><span><%=purnewQty%></span></td>
<td align="right"><a  title="Packet Wise report" href="ifrsaction.do?method=pktDtlStockOpenClose&dsp_stt=PURNEW&filterlprpval=ALL&filterlprp=<%=level1lprp%>"  target="_blank"><%=purnewCts%></a></td>
<td align="right"><%=purnewAvg%></td>
<td align="right"><%=purnewRAP%></td>
<td align="right" title="Vlu / 1000000" ><%=util.roundToDecimals(Double.parseDouble(purnewVlu)/1000000,2)%></td>
<td align="right"><%=purnewcpAvg%></td>
<td align="right"><%=purnewcpRAP%></td>
<td align="right" title="Cp Vlu / 1000000" ><%=util.roundToDecimals(Double.parseDouble(purnewcpVlu)/1000000,2)%></td>

<td align="right" style="padding:0px"><span><%=mixtosingleQty%></span></td>
<td align="right"><a  title="Packet Wise report" href="ifrsaction.do?method=pktDtlStockOpenClose&dsp_stt=MIXTOSNGL&filterlprpval=ALL&filterlprp=<%=level1lprp%>"  target="_blank"><%=mixtosingleCts%></a></td>
<td align="right"><%=mixtosingleAvg%></td>
<td align="right"><%=mixtosingleRAP%></td>
<td align="right" title="Vlu / 1000000" ><%=util.roundToDecimals(Double.parseDouble(mixtosingleVlu)/1000000,2)%></td>
<td align="right"><%=mixtosingleAvg%></td>
<td align="right"><%=mixtosinglecpRAP%></td>
<td align="right" title="Cp Vlu / 1000000" ><%=util.roundToDecimals(Double.parseDouble(mixtosinglecpVlu)/1000000,2)%></td>

<td align="right" style="padding:0px"><span><%=soldQty%></span></td>
<td align="right"><a  title="Packet Wise report" href="ifrsaction.do?method=pktDtlStockOpenClose&dsp_stt=SOLD&filterlprpval=ALL&filterlprp=<%=level1lprp%>"  target="_blank"><%=soldCts%></a></td>
<td align="right"><%=soldAvg%></td>
<td align="right"><%=soldRAP%></td>
<td align="right" title="Vlu / 1000000" ><%=util.roundToDecimals(Double.parseDouble(soldVlu)/1000000,2)%></td>
<td align="right"><%=soldcpAvg%></td>
<td align="right"><%=soldcpRAP%></td>
<td align="right" title="Cp Vlu / 1000000" ><%=util.roundToDecimals(Double.parseDouble(soldcpVlu)/1000000,2)%></td>
<td align="right"><%=String.valueOf(util.roundToDecimals((Double.parseDouble(soldVlu)-Double.parseDouble(soldcpVlu))/Double.parseDouble(soldVlu),2))%></td>
<td align="right" style="padding:0px"><span><%=closingQty%></span></td>
<td align="right"><a  title="Packet Wise report" href="ifrsaction.do?method=pktDtlStockOpenClose&dsp_stt=CLOSE&filterlprpval=ALL&filterlprp=<%=level1lprp%>"  target="_blank"><%=closingCts%></a></td>
<td align="right"><%=closingAvg%></td>
<td align="right"><%=closingAvgDis%></td>
<td align="right" title="Vlu / 1000000" ><%=util.roundToDecimals(Double.parseDouble(closingVlu)/1000000,2)%></td>
<td align="right"><%=closingcpAvg%></td>
<td align="right"><%=closingcpAvgDis%></td>
<td align="right" title="Cp Vlu / 1000000" ><%=util.roundToDecimals(Double.parseDouble(closingcpVlu)/1000000,2)%></td>
</tr>

</table>
</td>
</tr>
<%
double grandopenMixQty=0.0,grandopenMixCts=0.0,grandopenMixVlu=0.0,grandopenMixCPVlu=0.0,grandopenMixAvg=0.0,grandopenMixCPAvg=0.0;
double grandnewMixQty=0.0,grandnewMixCts=0.0,grandnewMixVlu=0.0,grandnewMixCPVlu=0.0,grandnewMixAvg=0.0,grandnewMixCPAvg=0.0;
double grandpurnewMixQty=0.0,grandpurnewMixCts=0.0,grandpurnewMixVlu=0.0,grandpurnewMixCPVlu=0.0,grandpurnewMixAvg=0.0,grandpurnewMixCPAvg=0.0;
double granddeptinnewMixQty=0.0,granddeptinnewMixCts=0.0,granddeptinnewMixVlu=0.0,granddeptinnewMixCPVlu=0.0,granddeptinnewMixAvg=0.0,granddeptinnewMixCPAvg=0.0;
double grandsoldMixQty=0.0,grandsoldMixCts=0.0,grandsoldMixVlu=0.0,grandsoldMixCPVlu=0.0,grandsoldMixAvg=0.0,grandsoldMixCPAvg=0.0;
double grandcloseMixQty=0.0,grandcloseMixCts=0.0,grandcloseMixVlu=0.0,grandcloseMixCPVlu=0.0,grandcloseMixAvg=0.0,grandcloseMixCPAvg=0.0;
HashMap summaryMixMap= ((HashMap)session.getAttribute("summaryMixMap") == null)?new HashMap():(HashMap)session.getAttribute("summaryMixMap");
ArrayList deptList = (ArrayList)prp.get("DEPTV");
int deptListsz=deptList.size();
%>
<tr>
<td valign="top" class="hedPg">
<table class="grid1small">
<tr>
<th></th>
<th colspan="5">Opening</th>
<th colspan="5">Mfg New</th>
<th colspan="5">Purchase New</th>
<th colspan="5">Dept Trf In</th>
<th colspan="6">Sold</th>
<th colspan="5">Balance</th>
</tr>
<tr>
<td></td>
<td align="center" nowrap="nowrap">CTS</td>
<td align="center" nowrap="nowrap">RTE</td>
<td align="center" nowrap="nowrap">VLU</td>
<td align="center" nowrap="nowrap">CP</td>
<td align="center" nowrap="nowrap">CPVLU</td>
<td align="center" nowrap="nowrap">CTS</td>
<td align="center" nowrap="nowrap">RTE</td>
<td align="center" nowrap="nowrap">VLU</td>
<td align="center" nowrap="nowrap">CP</td>
<td align="center" nowrap="nowrap">CPVLU</td>
<td align="center" nowrap="nowrap">CTS</td>
<td align="center" nowrap="nowrap">RTE</td>
<td align="center" nowrap="nowrap">VLU</td>
<td align="center" nowrap="nowrap">CP</td>
<td align="center" nowrap="nowrap">CPVLU</td>
<td align="center" nowrap="nowrap">CTS</td>
<td align="center" nowrap="nowrap">RTE</td>
<td align="center" nowrap="nowrap">VLU</td>
<td align="center" nowrap="nowrap">CP</td>
<td align="center" nowrap="nowrap">CPVLU</td>
<td align="center" nowrap="nowrap">CTS</td>
<td align="center" nowrap="nowrap">RTE</td>
<td align="center" nowrap="nowrap">VLU</td>
<td align="center" nowrap="nowrap">CP</td>
<td align="center" nowrap="nowrap">CPVLU</td>
<td align="center" nowrap="nowrap">G/P %</td>
<td align="center" nowrap="nowrap">CTS</td>
<td align="center" nowrap="nowrap">RTE</td>
<td align="center" nowrap="nowrap">VLU</td>
<td align="center" nowrap="nowrap">CP</td>
<td align="center" nowrap="nowrap">CPVLU</td>
</tr>
<%

for(int i=0;i<deptListsz;i++){
String dept=(String)deptList.get(i);
if(!dept.equals("NA") && !dept.equals("18-DN") && !dept.equals("S1") &&  !dept.equals("S2") &&  !dept.equals("18-96-GIA") &&  !dept.equals("96-UP-GIA")){
double newMixQty= ((Double)summaryMixMap.get(dept+"@QTY@NEW") == null)?0:(Double)summaryMixMap.get(dept+"@QTY@NEW");
double newMixCts= ((Double)summaryMixMap.get(dept+"@CTS@NEW") == null)?0:(Double)summaryMixMap.get(dept+"@CTS@NEW");
double newMixVlu= ((Double)summaryMixMap.get(dept+"@VLU@NEW") == null)?0:(Double)summaryMixMap.get(dept+"@VLU@NEW");
double newMixCPVlu= ((Double)summaryMixMap.get(dept+"@CPVLU@NEW") == null)?0:(Double)summaryMixMap.get(dept+"@CPVLU@NEW");

//ADJ
if(dept.equals("18-96-MIX") || dept.equals("18-96-FANCY")){
newMixVlu=newMixCPVlu;
}

double newMixAvg=0.0,newMixCPAvg=0.0;
if(newMixVlu > 0 && newMixCts > 0){
newMixAvg=Math.round(newMixVlu/newMixCts);
}
if(newMixCPVlu > 0 && newMixCts > 0){
newMixCPAvg=Math.round(newMixCPVlu/newMixCts);
}

double purnewMixQty= ((Double)summaryMixMap.get(dept+"@QTY@PURNEW") == null)?0:(Double)summaryMixMap.get(dept+"@QTY@PURNEW");
double purnewMixCts= ((Double)summaryMixMap.get(dept+"@CTS@PURNEW") == null)?0:(Double)summaryMixMap.get(dept+"@CTS@PURNEW");
double purnewMixVlu= ((Double)summaryMixMap.get(dept+"@VLU@PURNEW") == null)?0:(Double)summaryMixMap.get(dept+"@VLU@PURNEW");
double purnewMixCPVlu= ((Double)summaryMixMap.get(dept+"@CPVLU@PURNEW") == null)?0:(Double)summaryMixMap.get(dept+"@CPVLU@PURNEW");
double purnewMixAvg=0.0,purnewMixCPAvg=0.0;
if(purnewMixVlu > 0 && purnewMixCts > 0){
purnewMixAvg=Math.round(purnewMixVlu/purnewMixCts);
}
if(purnewMixCPVlu > 0 && purnewMixCts > 0){
purnewMixCPAvg=Math.round(purnewMixCPVlu/purnewMixCts);
}

//DEPTIN
double deptinnewMixQty= ((Double)summaryMixMap.get(dept+"@QTY@DEPTIN") == null)?0:(Double)summaryMixMap.get(dept+"@QTY@DEPTIN");
double deptinnewMixCts= ((Double)summaryMixMap.get(dept+"@CTS@DEPTIN") == null)?0:(Double)summaryMixMap.get(dept+"@CTS@DEPTIN");
double deptinnewMixVlu= ((Double)summaryMixMap.get(dept+"@VLU@DEPTIN") == null)?0:(Double)summaryMixMap.get(dept+"@VLU@DEPTIN");
double deptinnewMixCPVlu= ((Double)summaryMixMap.get(dept+"@CPVLU@DEPTIN") == null)?0:(Double)summaryMixMap.get(dept+"@CPVLU@DEPTIN");
double deptinnewMixAvg=0.0,deptinnewMixCPAvg=0.0;
if(deptinnewMixVlu > 0 && deptinnewMixCts > 0){
deptinnewMixAvg=Math.round(deptinnewMixVlu/deptinnewMixCts);
}
if(deptinnewMixCPVlu > 0 && deptinnewMixCts > 0){
deptinnewMixCPAvg=Math.round(deptinnewMixCPVlu/deptinnewMixCts);
}
//DEPTIN

double soldMixQty= ((Double)summaryMixMap.get(dept+"@QTY@SOLD") == null)?0:(Double)summaryMixMap.get(dept+"@QTY@SOLD");
double soldMixCts= ((Double)summaryMixMap.get(dept+"@CTS@SOLD") == null)?0:(Double)summaryMixMap.get(dept+"@CTS@SOLD");
double soldMixVlu= ((Double)summaryMixMap.get(dept+"@VLU@SOLD") == null)?0:(Double)summaryMixMap.get(dept+"@VLU@SOLD");
double soldMixCPVlu= ((Double)summaryMixMap.get(dept+"@CPVLU@SOLD") == null)?0:(Double)summaryMixMap.get(dept+"@CPVLU@SOLD");
double soldMixAvg=0.0,soldMixCPAvg=0.0;
if(soldMixVlu > 0 && soldMixCts > 0){
soldMixAvg=Math.round(soldMixVlu/soldMixCts);
}
if(soldMixCPVlu > 0 && soldMixCts > 0){
soldMixCPAvg=Math.round(soldMixCPVlu/soldMixCts);
}

double stkMixQty= ((Double)summaryMixMap.get(dept+"@QTY@STK") == null)?0:(Double)summaryMixMap.get(dept+"@QTY@STK");
double stkMixCts= ((Double)summaryMixMap.get(dept+"@CTS@STK") == null)?0:(Double)summaryMixMap.get(dept+"@CTS@STK");
double stkMixVlu= ((Double)summaryMixMap.get(dept+"@VLU@STK") == null)?0:(Double)summaryMixMap.get(dept+"@VLU@STK");
double stkMixCPVlu= ((Double)summaryMixMap.get(dept+"@CPVLU@STK") == null)?0:(Double)summaryMixMap.get(dept+"@CPVLU@STK");

double p2newMixQty= ((Double)summaryMixMap.get(dept+"@QTY@P2NEW") == null)?0:(Double)summaryMixMap.get(dept+"@QTY@P2NEW");
double p2newMixCts= ((Double)summaryMixMap.get(dept+"@CTS@P2NEW") == null)?0:(Double)summaryMixMap.get(dept+"@CTS@P2NEW");
double p2newMixVlu= ((Double)summaryMixMap.get(dept+"@VLU@P2NEW") == null)?0:(Double)summaryMixMap.get(dept+"@VLU@P2NEW");
double p2newMixCPVlu= ((Double)summaryMixMap.get(dept+"@CPVLU@P2NEW") == null)?0:(Double)summaryMixMap.get(dept+"@CPVLU@P2NEW");

double p2soldMixQty= ((Double)summaryMixMap.get(dept+"@QTY@P2SOLD") == null)?0:(Double)summaryMixMap.get(dept+"@QTY@P2SOLD");
double p2soldMixCts= ((Double)summaryMixMap.get(dept+"@CTS@P2SOLD") == null)?0:(Double)summaryMixMap.get(dept+"@CTS@P2SOLD");
double p2soldMixVlu= ((Double)summaryMixMap.get(dept+"@VLU@P2SOLD") == null)?0:(Double)summaryMixMap.get(dept+"@VLU@P2SOLD");
double p2soldMixCPVlu= ((Double)summaryMixMap.get(dept+"@CPVLU@P2SOLD") == null)?0:(Double)summaryMixMap.get(dept+"@CPVLU@P2SOLD");

double closeMixQty=0.0,closeMixCts=0.0,closeMixVlu=0.0,closeMixCPVlu=0.0,closeMixAvg=0.0,closeMixCPAvg=0.0;;

closeMixQty=(stkMixQty+p2soldMixQty)-p2newMixQty;
closeMixCts=(stkMixCts+p2soldMixCts)-p2newMixCts;
closeMixVlu=(stkMixVlu+p2soldMixVlu)-p2newMixVlu;
closeMixCPVlu=(stkMixCPVlu+p2soldMixCPVlu)-p2newMixCPVlu;

double openMixQty=0.0,openMixCts=0.0,openMixVlu=0.0,openMixCPVlu=0.0,openMixAvg=0.0,openMixCPAvg=0.0;;
openMixQty=(closeMixQty+soldMixQty)-(newMixQty+purnewMixQty+deptinnewMixQty);
openMixCts=(closeMixCts+soldMixCts)-(newMixCts+purnewMixCts+deptinnewMixCts);
openMixVlu=(closeMixVlu+soldMixVlu)-(newMixVlu+purnewMixVlu+deptinnewMixVlu);
openMixCPVlu=(closeMixCPVlu+soldMixCPVlu)-(newMixCPVlu+purnewMixCPVlu+deptinnewMixCPVlu);


if(dept.equals("S3")){
double s3rte=Double.parseDouble(util.nvl((String)dbinfo.get("S3_RTE"),"990"));
openMixVlu=openMixCts*s3rte;
openMixCPVlu=openMixCts*s3rte;
closeMixVlu=closeMixCts*s3rte;
closeMixCPVlu=closeMixCts*s3rte;
}else if(dept.equals("18-96-MIX")){
if(openMixVlu > 0 && openMixCts > 0){
openMixVlu=openMixCts*(Math.round(openMixCPVlu/openMixCts)*((1 + Double.parseDouble(util.nvl((String)dbinfo.get("18-96-MIX_PER"),"0.05")))));
}
}else if(dept.equals("18-96-FANCY")){
if(openMixVlu > 0 && openMixCts > 0){
openMixVlu=openMixCts*(Math.round(openMixCPVlu/openMixCts)*((1 + Double.parseDouble(util.nvl((String)dbinfo.get("18-96-FANCY_PER"),"0.05")))));
}
}else if(dept.equals("96-UP-MIX")){
if(openMixVlu > 0 && openMixCts > 0){
openMixCPVlu=openMixCts*(Math.round(openMixVlu/openMixCts)*((1 - Double.parseDouble(util.nvl((String)dbinfo.get("96-UP-MIX_PER"),"0.1")))));
}
}

if(closeMixVlu > 0 && closeMixCts > 0){
closeMixAvg=Math.round(closeMixVlu/closeMixCts);
}
if(closeMixCPVlu > 0 && closeMixCts > 0){
closeMixCPAvg=Math.round(closeMixCPVlu/closeMixCts);
}

if(openMixVlu > 0 && openMixCts > 0){
openMixAvg=Math.round(openMixVlu/openMixCts);
}
if(openMixCPVlu > 0 && openMixCts > 0){
openMixCPAvg=Math.round(openMixCPVlu/openMixCts);
}

grandopenMixQty+=openMixQty;
grandnewMixQty+=newMixQty;
grandpurnewMixQty+=purnewMixQty;
granddeptinnewMixQty+=deptinnewMixQty;
grandsoldMixQty+=soldMixQty;
grandcloseMixQty+=closeMixQty;

grandopenMixCts+=openMixCts;
grandnewMixCts+=newMixCts;
grandpurnewMixCts+=purnewMixCts;
granddeptinnewMixCts+=deptinnewMixCts;
grandsoldMixCts+=soldMixCts;
grandcloseMixCts+=closeMixCts;

grandopenMixVlu+=openMixVlu;
grandnewMixVlu+=newMixVlu;
grandpurnewMixVlu+=purnewMixVlu;
granddeptinnewMixVlu+=deptinnewMixVlu;
grandsoldMixVlu+=soldMixVlu;
grandcloseMixVlu+=closeMixVlu;

grandopenMixCPVlu+=openMixCPVlu;
grandnewMixCPVlu+=newMixCPVlu;
grandpurnewMixCPVlu+=purnewMixCPVlu;
granddeptinnewMixCPVlu+=deptinnewMixCPVlu;
grandsoldMixCPVlu+=soldMixCPVlu;
grandcloseMixCPVlu+=closeMixCPVlu;

//ADJ

%>
<tr>
<td align="center" nowrap="nowrap"><%=dept%></td>
<td align="right">
<a  title="Shape/Mix Size Grid" href="ifrsaction.do?method=getDataMixIfrs2Level&dsp_stt=OPEN&dept=<%=dept%>"  target="_blank">
<%=util.roundToDecimals(openMixCts,2)%>
</a>
</td>
<td align="right"><%=util.roundToDecimals(openMixAvg,2)%></td>
<td align="right" title="Vlu / 1000000"><%=util.roundToDecimals((openMixAvg*openMixCts)/1000000,2)%></td>
<td align="right"><%=util.roundToDecimals(openMixCPAvg,2)%></td>
<td align="right" title="Vlu / 1000000"><%=util.roundToDecimals((openMixCPAvg*openMixCts)/1000000,2)%></td>
<td align="right">
<a  title="Packet Wise report" href="ifrsaction.do?method=pktDtlMix&dsp_stt=NEW&dept=<%=dept%>"  target="_blank">
<%=util.roundToDecimals(newMixCts,2)%>
</a>
</td>
<td align="right"><%=util.roundToDecimals(newMixAvg,2)%></td>
<td align="right" title="Vlu / 1000000"><%=util.roundToDecimals(newMixVlu/1000000,2)%></td>
<td align="right"><%=util.roundToDecimals(newMixCPAvg,2)%></td>
<td align="right" title="Vlu / 1000000"><%=util.roundToDecimals(newMixCPVlu/1000000,2)%></td>
<td align="right">
<a  title="Packet Wise report" href="ifrsaction.do?method=pktDtlMix&dsp_stt=PURNEW&dept=<%=dept%>"  target="_blank">
<%=util.roundToDecimals(purnewMixCts,2)%>
</a>
</td>
<td align="right"><%=util.roundToDecimals(purnewMixAvg,2)%></td>
<td align="right" title="Vlu / 1000000"><%=util.roundToDecimals(purnewMixVlu/1000000,2)%></td>
<td align="right"><%=util.roundToDecimals(purnewMixCPAvg,2)%></td>
<td align="right" title="Vlu / 1000000"><%=util.roundToDecimals(purnewMixCPVlu/1000000,2)%></td>
<td align="right">
<a  title="Packet Wise report" href="ifrsaction.do?method=pktDtlMix&dsp_stt=DEPTIN&dept=<%=dept%>"  target="_blank">
<%=util.roundToDecimals(deptinnewMixCts,2)%>
</a>
</td>
<td align="right"><%=util.roundToDecimals(deptinnewMixAvg,2)%></td>
<td align="right" title="Vlu / 1000000"><%=util.roundToDecimals(deptinnewMixVlu/1000000,2)%></td>
<td align="right"><%=util.roundToDecimals(deptinnewMixCPAvg,2)%></td>
<td align="right" title="Vlu / 1000000"><%=util.roundToDecimals(deptinnewMixCPVlu/1000000,2)%></td>
<td align="right">
<a  title="Packet Wise report" href="ifrsaction.do?method=pktDtlMix&dsp_stt=SOLD&dept=<%=dept%>"  target="_blank">
<%=util.roundToDecimals(soldMixCts,2)%>
</a>
</td>
<td align="right"><%=util.roundToDecimals(soldMixAvg,2)%></td>
<td align="right" title="Vlu / 1000000"><%=util.roundToDecimals(soldMixVlu/1000000,2)%></td>
<td align="right"><%=util.roundToDecimals(soldMixCPAvg,2)%></td>
<td align="right" title="Vlu / 1000000"><%=util.roundToDecimals(soldMixCPVlu/1000000,2)%></td>
<td align="right"><%=String.valueOf(util.roundToDecimals((soldMixVlu-soldMixCPVlu)/soldMixVlu,2))%></td>
<td align="right">
<a  title="Shape/Mix Size Grid" href="ifrsaction.do?method=getDataMixIfrs2Level&dsp_stt=CLOSE&dept=<%=dept%>"  target="_blank">
<%=util.roundToDecimals(closeMixCts,2)%>
</a>
</td>
<td align="right"><%=util.roundToDecimals(closeMixAvg,2)%></td>
<td align="right" title="Vlu / 1000000"><%=util.roundToDecimals(closeMixVlu/1000000,2)%></td>
<td align="right"><%=util.roundToDecimals(closeMixCPAvg,2)%></td>
<td align="right" title="Vlu / 1000000"><%=util.roundToDecimals(closeMixCPVlu/1000000,2)%></td>
</tr>
<%}}
if(grandopenMixVlu > 0 && grandopenMixCts > 0){
grandopenMixAvg=Math.round(grandopenMixVlu/grandopenMixCts);
}

if(grandnewMixVlu > 0 && grandnewMixCts > 0){
grandnewMixAvg=Math.round(grandnewMixVlu/grandnewMixCts);
}
if(grandpurnewMixVlu > 0 && grandpurnewMixCts > 0){
grandpurnewMixAvg=Math.round(grandpurnewMixVlu/grandpurnewMixCts);
}
if(granddeptinnewMixVlu > 0 && granddeptinnewMixCts > 0){
granddeptinnewMixAvg=Math.round(granddeptinnewMixVlu/granddeptinnewMixCts);
}

if(grandsoldMixVlu > 0 && grandsoldMixCts > 0){
grandsoldMixAvg=Math.round(grandsoldMixVlu/grandsoldMixCts);
}

if(grandcloseMixVlu > 0 && grandcloseMixCts > 0){
grandcloseMixAvg=Math.round(grandcloseMixVlu/grandcloseMixCts);
}

if(grandopenMixCPVlu > 0 && grandopenMixCts > 0){
grandopenMixCPAvg=Math.round(grandopenMixCPVlu/grandopenMixCts);
}

if(grandnewMixCPVlu > 0 && grandnewMixCts > 0){
grandnewMixCPAvg=Math.round(grandnewMixCPVlu/grandnewMixCts);
}
if(grandpurnewMixCPVlu > 0 && grandpurnewMixCts > 0){
grandpurnewMixCPAvg=Math.round(grandpurnewMixCPVlu/grandpurnewMixCts);
}

if(granddeptinnewMixCPVlu > 0 && granddeptinnewMixCts > 0){
granddeptinnewMixCPAvg=Math.round(granddeptinnewMixCPVlu/granddeptinnewMixCts);
}

if(grandsoldMixCPVlu > 0 && grandsoldMixCts > 0){
grandsoldMixCPAvg=Math.round(grandsoldMixCPVlu/grandsoldMixCts);
}

if(grandcloseMixCPVlu > 0 && grandcloseMixCts > 0){
grandcloseMixCPAvg=Math.round(grandcloseMixCPVlu/grandcloseMixCts);
}

//ADJ
//grandopenMixCPAvg=grandcloseMixCPAvg;
//grandopenMixAvg=grandcloseMixAvg;
%>
<tr>
<td align="center">Total</td>
<td align="right">
<a  title="Shape/Mix Size Grid" href="ifrsaction.do?method=getDataMixIfrs2Level&dsp_stt=OPEN&dept=ALL"  target="_blank">
<%=util.roundToDecimals(grandopenMixCts,2)%>
</a>
</td>
<td align="right"><%=util.roundToDecimals(grandopenMixAvg,2)%></td>
<td align="right" title="Vlu / 1000000" ><%=util.roundToDecimals((grandopenMixAvg*grandopenMixCts)/1000000,2)%></td>
<td align="right"><%=util.roundToDecimals(grandopenMixCPAvg,2)%></td>
<td align="right" title="Vlu / 1000000" ><%=util.roundToDecimals((grandopenMixCPAvg*grandopenMixCts)/1000000,2)%></td>
<td align="right">
<a  title="Packet Wise report" href="ifrsaction.do?method=pktDtlMix&dsp_stt=NEW&dept=ALL"  target="_blank">
<%=util.roundToDecimals(grandnewMixCts,2)%>
</a>
</td>
<td align="right"><%=util.roundToDecimals(grandnewMixAvg,2)%></td>
<td align="right" title="Vlu / 1000000" ><%=util.roundToDecimals(grandnewMixVlu/1000000,2)%></td>
<td align="right"><%=util.roundToDecimals(grandnewMixCPAvg,2)%></td>
<td align="right" title="Vlu / 1000000" ><%=util.roundToDecimals(grandnewMixCPVlu/1000000,2)%></td>
<td align="right">
<a  title="Packet Wise report" href="ifrsaction.do?method=pktDtlMix&dsp_stt=PURNEW&dept=ALL"  target="_blank">
<%=util.roundToDecimals(grandpurnewMixCts,2)%>
</a>
</td>
<td align="right"><%=util.roundToDecimals(grandpurnewMixAvg,2)%></td>
<td align="right" title="Vlu / 1000000" ><%=util.roundToDecimals(grandpurnewMixVlu/1000000,2)%></td>
<td align="right"><%=util.roundToDecimals(grandpurnewMixCPAvg,2)%></td>
<td align="right" title="Vlu / 1000000" ><%=util.roundToDecimals(grandpurnewMixCPVlu/1000000,2)%></td>
<td align="right">
<a  title="Packet Wise report" href="ifrsaction.do?method=pktDtlMix&dsp_stt=DEPTIN&dept=ALL"  target="_blank">
<%=util.roundToDecimals(granddeptinnewMixCts,2)%>
</a>
</td>
<td align="right"><%=util.roundToDecimals(granddeptinnewMixAvg,2)%></td>
<td align="right" title="Vlu / 1000000" ><%=util.roundToDecimals(granddeptinnewMixVlu/1000000,2)%></td>
<td align="right"><%=util.roundToDecimals(granddeptinnewMixCPAvg,2)%></td>
<td align="right" title="Vlu / 1000000" ><%=util.roundToDecimals(granddeptinnewMixCPVlu/1000000,2)%></td>
<td align="right">
<a  title="Packet Wise report" href="ifrsaction.do?method=pktDtlMix&dsp_stt=SOLD&dept=ALL"  target="_blank">
<%=util.roundToDecimals(grandsoldMixCts,2)%>
</a>
</td>
<td align="right"><%=util.roundToDecimals(grandsoldMixAvg,2)%></td>
<td align="right" title="Vlu / 1000000" ><%=util.roundToDecimals(grandsoldMixVlu/1000000,2)%></td>
<td align="right"><%=util.roundToDecimals(grandsoldMixCPAvg,2)%></td>
<td align="right" title="Vlu / 1000000" ><%=util.roundToDecimals(grandsoldMixCPVlu/1000000,2)%></td>
<td align="right"><%=String.valueOf(util.roundToDecimals((grandsoldMixVlu-grandsoldMixCPVlu)/grandsoldMixVlu,2))%></td>
<td align="right">
<a  title="Shape/Mix Size Grid" href="ifrsaction.do?method=getDataMixIfrs2Level&dsp_stt=CLOSE&dept=ALL"  target="_blank">
<%=util.roundToDecimals(grandcloseMixCts,2)%>
</a>
</td>
<td align="right"><%=util.roundToDecimals(grandcloseMixAvg,2)%></td>
<td align="right" title="Vlu / 1000000" ><%=util.roundToDecimals(grandcloseMixVlu/1000000,2)%></td>
<td align="right"><%=util.roundToDecimals(grandcloseMixCPAvg,2)%></td>
<td align="right" title="Vlu / 1000000" ><%=util.roundToDecimals(grandcloseMixCPVlu/1000000,2)%></td>
</tr>
</table>
</td>
</tr>
<%}else{
HashMap summaryMixMap= ((HashMap)session.getAttribute("summaryMixMap") == null)?new HashMap():(HashMap)session.getAttribute("summaryMixMap");
ArrayList deptList = (ArrayList)prp.get("DEPTV");
int deptListsz=deptList.size();
ArrayList monthLst= ((ArrayList)session.getAttribute("monthLst") == null)?new ArrayList():(ArrayList)session.getAttribute("monthLst");
int monthLstsz=monthLst.size();
%>
<%for(int i=0;i<monthLstsz;i++){
String month=util.nvl((String)monthLst.get(i));%>
<tr>
<td valign="top" class="hedPg">
<table class="grid1small">
<tr><td align="center" colspan="32"><b><%=month%></b></td></tr>
<tr>
<th></th>
<th colspan="5">Opening</th>
<th colspan="5">Mfg New</th>
<th colspan="5">Purchase New</th>
<th colspan="5">Dept Trf In</th>
<th colspan="6">Sold</th>
<th colspan="5">Balance</th>
</tr>
<tr>
<td></td>
<td align="center" nowrap="nowrap">CTS</td>
<td align="center" nowrap="nowrap">RTE</td>
<td align="center" nowrap="nowrap">VLU</td>
<td align="center" nowrap="nowrap">CP</td>
<td align="center" nowrap="nowrap">CPVLU</td>
<td align="center" nowrap="nowrap">CTS</td>
<td align="center" nowrap="nowrap">RTE</td>
<td align="center" nowrap="nowrap">VLU</td>
<td align="center" nowrap="nowrap">CP</td>
<td align="center" nowrap="nowrap">CPVLU</td>

<td align="center" nowrap="nowrap">CTS</td>
<td align="center" nowrap="nowrap">RTE</td>
<td align="center" nowrap="nowrap">VLU</td>
<td align="center" nowrap="nowrap">CP</td>
<td align="center" nowrap="nowrap">CPVLU</td>

<td align="center" nowrap="nowrap">CTS</td>
<td align="center" nowrap="nowrap">RTE</td>
<td align="center" nowrap="nowrap">VLU</td>
<td align="center" nowrap="nowrap">CP</td>
<td align="center" nowrap="nowrap">CPVLU</td>

<td align="center" nowrap="nowrap">CTS</td>
<td align="center" nowrap="nowrap">RTE</td>
<td align="center" nowrap="nowrap">VLU</td>
<td align="center" nowrap="nowrap">CP</td>
<td align="center" nowrap="nowrap">CPVLU</td>
<td align="center" nowrap="nowrap">G/P %</td>

<td align="center" nowrap="nowrap">CTS</td>
<td align="center" nowrap="nowrap">RTE</td>
<td align="center" nowrap="nowrap">VLU</td>
<td align="center" nowrap="nowrap">CP</td>
<td align="center" nowrap="nowrap">CPVLU</td>
</tr>
<%
double grandopenQty=0.0,grandopenCts=0.0,grandopenVlu=0.0,grandopenCPVlu=0.0,grandopenVluAvg=0.0,grandopenCPVluAvg=0.0;
double grandnewQty=0.0,grandnewCts=0.0,grandnewVlu=0.0,grandnewCPVlu=0.0,grandnewVluAvg=0.0,grandnewCPVluAvg=0.0;
double grandpurnewQty=0.0,grandpurnewCts=0.0,grandpurnewVlu=0.0,grandpurnewCPVlu=0.0,grandpurnewVluAvg=0.0,grandpurnewCPVluAvg=0.0;
double granddeptinnewQty=0.0,granddeptinnewCts=0.0,granddeptinnewVlu=0.0,granddeptinnewCPVlu=0.0,granddeptinnewVluAvg=0.0,granddeptinnewCPVluAvg=0.0;
double grandsoldQty=0.0,grandsoldCts=0.0,grandsoldVlu=0.0,grandsoldCPVlu=0.0,grandsoldVluAvg=0.0,grandsoldCPVluAvg=0.0;
double grandcloseQty=0.0,grandcloseCts=0.0,grandcloseVlu=0.0,grandcloseCPVlu=0.0,grandcloseVluAvg=0.0,grandcloseCPVluAvg=0.0;
for(int j=0;j<deptListsz;j++){
String dept=util.nvl((String)deptList.get(j));
if(!dept.equals("NA") && !dept.equals("18-DN") && !dept.equals("S1") &&  !dept.equals("S2")){
double openQty= ((Double)summaryMixMap.get(dept+"@QTY@"+month+"@OPEN") == null)?0:(Double)summaryMixMap.get(dept+"@QTY@"+month+"@OPEN");
double openCts= ((Double)summaryMixMap.get(dept+"@CTS@"+month+"@OPEN") == null)?0:(Double)summaryMixMap.get(dept+"@CTS@"+month+"@OPEN");
double openVlu= ((Double)summaryMixMap.get(dept+"@VLU@"+month+"@OPEN") == null)?0:(Double)summaryMixMap.get(dept+"@VLU@"+month+"@OPEN");
double openCPVlu= ((Double)summaryMixMap.get(dept+"@CPVLU@"+month+"@OPEN") == null)?0:(Double)summaryMixMap.get(dept+"@CPVLU@"+month+"@OPEN");
double newQty= ((Double)summaryMixMap.get(dept+"@QTY@"+month+"@NEW") == null)?0:(Double)summaryMixMap.get(dept+"@QTY@"+month+"@NEW");
double newCts= ((Double)summaryMixMap.get(dept+"@CTS@"+month+"@NEW") == null)?0:(Double)summaryMixMap.get(dept+"@CTS@"+month+"@NEW");
double newVlu= ((Double)summaryMixMap.get(dept+"@VLU@"+month+"@NEW") == null)?0:(Double)summaryMixMap.get(dept+"@VLU@"+month+"@NEW");
double newCPVlu= ((Double)summaryMixMap.get(dept+"@CPVLU@"+month+"@NEW") == null)?0:(Double)summaryMixMap.get(dept+"@CPVLU@"+month+"@NEW");
double purnewQty= ((Double)summaryMixMap.get(dept+"@QTY@"+month+"@PURNEW") == null)?0:(Double)summaryMixMap.get(dept+"@QTY@"+month+"@PURNEW");
double purnewCts= ((Double)summaryMixMap.get(dept+"@CTS@"+month+"@PURNEW") == null)?0:(Double)summaryMixMap.get(dept+"@CTS@"+month+"@PURNEW");
double purnewVlu= ((Double)summaryMixMap.get(dept+"@VLU@"+month+"@PURNEW") == null)?0:(Double)summaryMixMap.get(dept+"@VLU@"+month+"@PURNEW");
double purnewCPVlu= ((Double)summaryMixMap.get(dept+"@CPVLU@"+month+"@PURNEW") == null)?0:(Double)summaryMixMap.get(dept+"@CPVLU@"+month+"@PURNEW");
double deptinnewQty= ((Double)summaryMixMap.get(dept+"@QTY@"+month+"@DEPTIN") == null)?0:(Double)summaryMixMap.get(dept+"@QTY@"+month+"@DEPTIN");
double deptinnewCts= ((Double)summaryMixMap.get(dept+"@CTS@"+month+"@DEPTIN") == null)?0:(Double)summaryMixMap.get(dept+"@CTS@"+month+"@DEPTIN");
double deptinnewVlu= ((Double)summaryMixMap.get(dept+"@VLU@"+month+"@DEPTIN") == null)?0:(Double)summaryMixMap.get(dept+"@VLU@"+month+"@DEPTIN");
double deptinnewCPVlu= ((Double)summaryMixMap.get(dept+"@CPVLU@"+month+"@DEPTIN") == null)?0:(Double)summaryMixMap.get(dept+"@CPVLU@"+month+"@DEPTIN");
double soldQty= ((Double)summaryMixMap.get(dept+"@QTY@"+month+"@SOLD") == null)?0:(Double)summaryMixMap.get(dept+"@QTY@"+month+"@SOLD");
double soldCts= ((Double)summaryMixMap.get(dept+"@CTS@"+month+"@SOLD") == null)?0:(Double)summaryMixMap.get(dept+"@CTS@"+month+"@SOLD");
double soldVlu= ((Double)summaryMixMap.get(dept+"@VLU@"+month+"@SOLD") == null)?0:(Double)summaryMixMap.get(dept+"@VLU@"+month+"@SOLD");
double soldCPVlu= ((Double)summaryMixMap.get(dept+"@CPVLU@"+month+"@SOLD") == null)?0:(Double)summaryMixMap.get(dept+"@CPVLU@"+month+"@SOLD");
double closeQty= ((Double)summaryMixMap.get(dept+"@QTY@"+month+"@CLOSE") == null)?0:(Double)summaryMixMap.get(dept+"@QTY@"+month+"@CLOSE");
double closeCts= ((Double)summaryMixMap.get(dept+"@CTS@"+month+"@CLOSE") == null)?0:(Double)summaryMixMap.get(dept+"@CTS@"+month+"@CLOSE");
double closeVlu= ((Double)summaryMixMap.get(dept+"@VLU@"+month+"@CLOSE") == null)?0:(Double)summaryMixMap.get(dept+"@VLU@"+month+"@CLOSE");
double closeCPVlu= ((Double)summaryMixMap.get(dept+"@CPVLU@"+month+"@CLOSE") == null)?0:(Double)summaryMixMap.get(dept+"@CPVLU@"+month+"@CLOSE");
double openVluAvg=0.0,newVluAvg=0.0,purnewVluAvg=0.0,deptinnewVluAvg=0.0,soldVluAvg=0.0,closeVluAvg=0.0;
double openCPVluAvg=0.0,newCPVluAvg=0.0,purnewCPVluAvg=0.0,deptinnewCPVluAvg=0.0,soldCPVluAvg=0.0,closeCPVluAvg=0.0;

if(openVlu > 0 && openCts > 0){
openVluAvg=Math.round(openVlu/openCts);
}
if(openCPVlu > 0 && openCts > 0){
openCPVluAvg=Math.round(openCPVlu/openCts);
}
if(newVlu > 0 && newCts > 0){
newVluAvg=Math.round(newVlu/newCts);
}
if(newCPVlu > 0 && newCts > 0){
newCPVluAvg=Math.round(newCPVlu/newCts);
}
if(purnewVlu > 0 && purnewCts > 0){
purnewVluAvg=Math.round(purnewVlu/purnewCts);
}
if(purnewCPVlu > 0 && purnewCts > 0){
purnewCPVluAvg=Math.round(purnewCPVlu/purnewCts);
}
if(deptinnewVlu > 0 && deptinnewCts > 0){
deptinnewVluAvg=Math.round(deptinnewVlu/deptinnewCts);
}
if(deptinnewCPVlu > 0 && deptinnewCts > 0){
deptinnewCPVluAvg=Math.round(deptinnewCPVlu/deptinnewCts);
}
if(soldVlu > 0 && soldCts > 0){
soldVluAvg=Math.round(soldVlu/soldCts);
}
if(soldCPVlu > 0 && soldCts > 0){
soldCPVluAvg=Math.round(soldCPVlu/soldCts);
}
if(closeVlu > 0 && closeCts > 0){
closeVluAvg=Math.round(closeVlu/closeCts);
}
if(closeCPVlu > 0 && closeCts > 0){
closeCPVluAvg=Math.round(closeCPVlu/closeCts);
}

grandopenQty+=openQty;
grandopenCts+=openCts;
grandopenVlu+=openVlu;
grandopenCPVlu+=openCPVlu;

grandnewQty+=newQty;
grandnewCts+=newCts;
grandnewVlu+=newVlu;
grandnewCPVlu+=newCPVlu;

grandpurnewQty+=purnewQty;
grandpurnewCts+=purnewCts;
grandpurnewVlu+=purnewVlu;
grandpurnewCPVlu+=purnewCPVlu;

granddeptinnewQty+=deptinnewQty;
granddeptinnewCts+=deptinnewCts;
granddeptinnewVlu+=deptinnewVlu;
granddeptinnewCPVlu+=deptinnewCPVlu;
grandsoldQty+=soldQty;
grandsoldCts+=soldCts;
grandsoldVlu+=soldVlu;
grandsoldCPVlu+=soldCPVlu;
grandcloseQty+=closeQty;
grandcloseCts+=closeCts;
grandcloseVlu+=closeVlu;
grandcloseCPVlu+=closeCPVlu;
%>
<tr>
<td><%=dept%></td>
<td align="right"><%=util.roundToDecimals(openCts,3)%></td>
<td align="right"><%=openVluAvg%></td>
<td align="right" title="Vlu / 1000000" ><%=util.roundToDecimals(openVlu/1000000,2)%></td>
<td align="right"><%=openCPVluAvg%></td>
<td align="right" title="Vlu / 1000000" ><%=util.roundToDecimals(openCPVlu/1000000,2)%></td>

<td align="right"><%=util.roundToDecimals(newCts,3)%></td>
<td align="right"><%=newVluAvg%></td>
<td align="right" title="Vlu / 1000000" ><%=util.roundToDecimals(newVlu/1000000,2)%></td>
<td align="right"><%=newCPVluAvg%></td>
<td align="right" title="Vlu / 1000000" ><%=util.roundToDecimals(newCPVlu/1000000,2)%></td>
<td align="right"><%=util.roundToDecimals(purnewCts,3)%></td>
<td align="right"><%=purnewVluAvg%></td>
<td align="right" title="Vlu / 1000000" ><%=util.roundToDecimals(purnewVlu/1000000,2)%></td>
<td align="right"><%=purnewCPVluAvg%></td>
<td align="right" title="Vlu / 1000000" ><%=util.roundToDecimals(purnewCPVlu/1000000,2)%></td>
<td align="right"><%=util.roundToDecimals(deptinnewCts,3)%></td>
<td align="right"><%=deptinnewVluAvg%></td>
<td align="right" title="Vlu / 1000000" ><%=util.roundToDecimals(deptinnewVlu/1000000,2)%></td>
<td align="right"><%=deptinnewCPVluAvg%></td>
<td align="right" title="Vlu / 1000000" ><%=util.roundToDecimals(deptinnewCPVlu/1000000,2)%></td>
<td align="right"><%=util.roundToDecimals(soldCts,3)%></td>
<td align="right"><%=soldVluAvg%></td>
<td align="right" title="Vlu / 1000000" ><%=util.roundToDecimals(soldVlu/1000000,2)%></td>
<td align="right"><%=soldCPVluAvg%></td>
<td align="right" title="Vlu / 1000000" ><%=util.roundToDecimals(soldCPVlu/1000000,2)%></td>
<td align="right"><%=String.valueOf(util.roundToDecimals(((soldVlu-soldCPVlu)/soldVlu),2))%></td>
<td align="right"><%=util.roundToDecimals(closeCts,3)%></td>
<td align="right"><%=closeVluAvg%></td>
<td align="right" title="Vlu / 1000000" ><%=util.roundToDecimals(closeVlu/1000000,2)%></td>
<td align="right"><%=closeCPVluAvg%></td>
<td align="right" title="Vlu / 1000000" ><%=util.roundToDecimals(closeCPVlu/1000000,2)%></td>
</tr>
<%}}
if(grandopenVlu > 0 && grandopenCts > 0){
grandopenVluAvg=Math.round(grandopenVlu/grandopenCts);
}
if(grandopenCPVlu > 0 && grandopenCts > 0){
grandopenCPVluAvg=Math.round(grandopenCPVlu/grandopenCts);
}
if(grandnewVlu > 0 && grandnewCts > 0){
grandnewVluAvg=Math.round(grandnewVlu/grandnewCts);
}
if(grandnewCPVlu > 0 && grandnewCts > 0){
grandnewCPVluAvg=Math.round(grandnewCPVlu/grandnewCts);
}
if(grandpurnewVlu > 0 && grandpurnewCts > 0){
grandpurnewVluAvg=Math.round(grandpurnewVlu/grandpurnewCts);
}
if(grandpurnewCPVlu > 0 && grandpurnewCts > 0){
grandpurnewCPVluAvg=Math.round(grandpurnewCPVlu/grandpurnewCts);
}
if(granddeptinnewVlu > 0 && granddeptinnewCts > 0){
granddeptinnewVluAvg=Math.round(granddeptinnewVlu/granddeptinnewCts);
}
if(granddeptinnewCPVlu > 0 && granddeptinnewCts > 0){
granddeptinnewCPVluAvg=Math.round(granddeptinnewCPVlu/granddeptinnewCts);
}
if(grandsoldVlu > 0 && grandsoldCts > 0){
grandsoldVluAvg=Math.round(grandsoldVlu/grandsoldCts);
}
if(grandsoldCPVlu > 0 && grandsoldCts > 0){
grandsoldCPVluAvg=Math.round(grandsoldCPVlu/grandsoldCts);
}
if(grandcloseVlu > 0 && grandcloseCts > 0){
grandcloseVluAvg=Math.round(grandcloseVlu/grandcloseCts);
}
if(grandcloseCPVlu > 0 && grandcloseCts > 0){
grandcloseCPVluAvg=Math.round(grandcloseCPVlu/grandcloseCts);
}
%>
<tr>
<td align="center"><b>Total</b></td>
<td align="right"><%=util.roundToDecimals(grandopenCts,3)%></td>
<td align="right"><%=grandopenVluAvg%></td>
<td align="right" title="Vlu / 1000000" ><%=util.roundToDecimals(grandopenVlu/1000000,2)%></td>
<td align="right"><%=grandopenCPVluAvg%></td>
<td align="right" title="Vlu / 1000000" ><%=util.roundToDecimals(grandopenCPVlu/1000000,2)%></td>

<td align="right"><%=util.roundToDecimals(grandnewCts,3)%></td>
<td align="right"><%=grandnewVluAvg%></td>
<td align="right" title="Vlu / 1000000" ><%=util.roundToDecimals(grandnewVlu/1000000,2)%></td>
<td align="right"><%=grandnewCPVluAvg%></td>
<td align="right" title="Vlu / 1000000" ><%=util.roundToDecimals(grandnewCPVlu/1000000,2)%></td>
<td align="right"><%=util.roundToDecimals(grandpurnewCts,3)%></td>
<td align="right"><%=grandpurnewVluAvg%></td>
<td align="right" title="Vlu / 1000000" ><%=util.roundToDecimals(grandpurnewVlu/1000000,2)%></td>
<td align="right"><%=grandpurnewCPVluAvg%></td>
<td align="right" title="Vlu / 1000000" ><%=util.roundToDecimals(grandpurnewCPVlu/1000000,2)%></td>
<td align="right"><%=util.roundToDecimals(granddeptinnewCts,3)%></td>
<td align="right"><%=granddeptinnewVluAvg%></td>
<td align="right" title="Vlu / 1000000" ><%=util.roundToDecimals(granddeptinnewVlu/1000000,2)%></td>
<td align="right"><%=granddeptinnewCPVluAvg%></td>
<td align="right" title="Vlu / 1000000" ><%=util.roundToDecimals(granddeptinnewCPVlu/1000000,2)%></td>
<td align="right"><%=util.roundToDecimals(grandsoldCts,3)%></td>
<td align="right"><%=grandsoldVluAvg%></td>
<td align="right" title="Vlu / 1000000" ><%=util.roundToDecimals(grandsoldVlu/1000000,2)%></td>
<td align="right"><%=grandsoldCPVluAvg%></td>
<td align="right" title="Vlu / 1000000" ><%=util.roundToDecimals(grandsoldCPVlu/1000000,2)%></td>
<td align="right"><%=String.valueOf(util.roundToDecimals(((grandsoldVlu-grandsoldCPVlu)/grandsoldVlu),2))%></td>
<td align="right"><%=util.roundToDecimals(grandcloseCts,3)%></td>
<td align="right"><%=grandcloseVluAvg%></td>
<td align="right" title="Vlu / 1000000" ><%=util.roundToDecimals(grandcloseVlu/1000000,2)%></td>
<td align="right"><%=grandcloseCPVluAvg%></td>
<td align="right" title="Vlu / 1000000" ><%=util.roundToDecimals(grandcloseCPVlu/1000000,2)%></td>
</tr>
</table>
</td>
</tr>
<%}%>
<%}%>
  <tr><td>
<jsp:include page="/footer.jsp" />
</td></tr>
  </table>  
       <%@include file="../calendar.jsp"%>
  </body>
</html>