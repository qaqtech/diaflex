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
<link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/general.css?v=<%=info.getJsVersion()%>"/>
<script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/ajax.js?v=<%=info.getJsVersion()%> " > </script>
  <script type="text/javascript" src="<%=info.getReqUrl()%>/PopScripts/popup.js?v=<%=info.getJsVersion()%> " > </script>
    <script src="<%=info.getReqUrl()%>/PopScripts/jquery-1.2.6.min.js" type="text/javascript"></script>
   <script language="javaScript" type="text/javascript" src="<%=info.getReqUrl()%>/calendar/calendar.js"></script>
   <link href="<%=info.getReqUrl()%>/calendar/calendar.css" rel="stylesheet" type="text/css" />
  <script src="<%=info.getReqUrl()%>/jqueryScript/jqueryScript.js?v=<%=info.getJsVersion()%>" type="text/javascript"></script>
 
      <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/style.css?v=<%=info.getJsVersion()%>"/>

  <title>Generic Report</title>
 
  </head>

        <%
     ArrayList level1LstMix = (request.getAttribute("level1LstMix") == null)?new ArrayList():(ArrayList)request.getAttribute("level1LstMix");
     HashMap dataTbl= (request.getAttribute("dataTblMix") == null)?new HashMap():(HashMap)request.getAttribute("dataTblMix");
     String days=util.nvl((String)request.getAttribute("days"));
     HashMap allPageDtl= (info.getPageDetails() == null)?new HashMap():(HashMap)info.getPageDetails();
    HashMap pageDtl=(HashMap)allPageDtl.get("GENERIC_REPORT_MIX");
    ArrayList pageList=new ArrayList();
    HashMap pageDtlMap=new HashMap();
    String fld_nme="",fld_ttl="",val_cond="",dflt_val="",fld_typ="",form_nme="",flg1="",lov_qry="",url="",barGrp="",color="",level1="";
    HashMap dbmsInfo = info.getDmbsInfoLst();
    HashMap data=new HashMap();
            pageList=(ArrayList)pageDtl.get("LEVEL1");
            if(pageList!=null && pageList.size() >0){
            for(int j=0;j<pageList.size();j++){
            pageDtlMap=(HashMap)pageList.get(j);
            level1=(String)pageDtlMap.get("dflt_val");
            }}
      HashMap prp = info.getPrp();
      ArrayList prpList = (ArrayList)prp.get(level1+"V");
    int level1LstMixsz=level1LstMix.size();
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
  <img src="../images/line.jpg" border="0" /></td><td valign="middle"> <span class="pgHed">Generic Report Mix</span> </td>
</tr></table>
  </td>
  </tr>
  <tr>
  <td class="hedPg">
  <table class="genericTB">
  <tr>
  <td>
  <html:form action="report/genericReportMix.do?method=load" method="post" onsubmit="" >
  <table>
  <tr>
  <td>Days/Normal</td>
  <td>
  <html:select name="genericReportForm" property="value(daysfilter)" styleId="daysfilter" onchange="genericReportMixDays('daysfilter')">
            <%
            pageList= ((ArrayList)pageDtl.get("DAYS") == null)?new ArrayList():(ArrayList)pageDtl.get("DAYS");
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
            <html:option value="<%=dflt_val%>"><%=fld_ttl%></html:option>
            <%}}%>
  </html:select>
  </td>
  </tr>
  <tr id="datefilter" style="display:none">
  <td colspan="2">
    <table  class="grid1">
    <tr><td>Employee</td><td>
             <html:select property="value(empId)" name="genericReportForm"  onchange="getEmployeeGeneric(this)" styleId="empLst">
             <html:option value="" >--select--</html:option>
             <html:optionsCollection property="byrList" name="genericReportForm"  value="byrIdn" label="byrNme" />
             </html:select></td>
    </tr>
     <tr><td>Buyer</td>
     <td nowrap="nowrap">      
     <html:text  property="value(partytext)" name="genericReportForm" styleId="partytext" style="width:230px" styleClass="sugBox" onkeypress="return disableEnterKey(event);" 
     onkeyup="doCompletionPartyNMEGeneric('partytext', 'nmePop', '../ajaxAction.do?1=1&isSrch=Y&UsrTyp=BUYER','empLst', event)"
      onkeydown="setDown('nmePop', 'party', 'partytext',event)" onclick="document.getElementById('partytext').autocomplete='off'"  />
      <img src="../images/addrow.jpg" width="17" height="17" title="Click To get Names" onclick="doCompletionPartyNMEGeneric('partytext', 'nmePop', '../ajaxAction.do?1=1&isSrch=Y&UsrTyp=BUYER','empLst')">
      <html:hidden property="value(byrId)" styleId="party"  />
  <div style="position: absolute;">
      <select id="nmePop" name="nmePop"
        style="display:none;300px;" 
        class="sugBoxDiv" 
        onKeyDown="setDown('nmePop', 'party', 'partytext',event)" 
        onDblClick="setVal('nmePop', 'party', 'partytext', event);hideObj('nmePop');" 
        onClick="setVal('nmePop', 'party', 'partytext', event)" 
        size="10">
      </select>
</div>       
</td>
    </tr>
 <tr>
 <td>Date</td><td>
 <table><tr>
 <td>
    <html:text property="value(frmdte)" styleClass="txtStyle"  styleId="frmdte"  size="12"  /> <a href="#" style="margin-top:5px;"  onClick="setYears(1900, '<%=info.getCurrentYear()%>' );showCalender(this, 'frmdte');">
                <img src="<%=info.getReqUrl()%>/images/calender.png"></a></td>
               <td>
    <html:text property="value(todte)" styleClass="txtStyle"  styleId="todte"  size="12"  /> <a href="#"  onClick="setYears(1900, '<%=info.getCurrentYear()%>' );showCalender(this, 'todte');">
                <img src="<%=info.getReqUrl()%>/images/calender.png"></a></td></tr></table></td>
 
 </tr>
   </table>
  </td>
  </tr>
  <tr>
  <td colspan="2" align="center"><html:submit property="value(search)" value="Search" styleClass="submit" /></td>
  </tr>
  </table>
  </html:form>
  </td>
  </tr>
  <%if(level1LstMixsz>0){%>
  <tr>
  <td>
  <table class="grid1">
  <tr><th></th><th>New</th><th colspan="5">Marketing</th><th colspan="4">Memo</th><th colspan="7">Sold</th></tr>
  <tr>
  <td></td>
  <td align="center">CTS</td>
  <td align="center">QTY</td><td align="center">CTS</td><td align="center">AVG</td><td align="center">VLU</td><td align="center">MSD</td>
  <td align="center">QTY</td><td align="center">CTS</td><td align="center">AVG</td><td align="center">VLU</td>
  <td align="center">QTY</td><td align="center">CTS</td><td align="center">AVG</td><td align="center">VLU</td><td align="center">SVPD</td><td align="center">DIFF</td><td align="center">DIFF PCT</td>
  </tr>
  <%for(int i=0;i<prpList.size();i++){
  String vall=(String)prpList.get(i);
  if(level1LstMix.contains(vall)){
   String svpd ="";
   String msd ="";
   String mktVlu = util.nvl((String)dataTbl.get(vall+"_MKT_VLU"),"0");
   String soldVlu = util.nvl((String)dataTbl.get(vall+"_SOLD_VLU"),"0");
   String soldBseVlu = util.nvl((String)dataTbl.get(vall+"_SOLD_BSEVLU"),"0");
   String diff="",diff_pct="";
     if(!soldVlu.equals("") && !soldVlu.equals("0")){
     svpd=util.nvl(String.valueOf(Math.round(Double.parseDouble(soldVlu)/Double.parseDouble(days))),"0");
     if(!mktVlu.equals("") && !mktVlu.equals("0") && !svpd.equals("0"))
     msd=util.nvl(String.valueOf(Math.round(Double.parseDouble(mktVlu)/Double.parseDouble(svpd))),"0");
     if(svpd.equals("0"))
     svpd="";
     if(msd.equals("0"))
     msd="";
     if(!soldBseVlu.equals("") && !soldBseVlu.equals("0")){
     diff=String.valueOf(Double.parseDouble(soldVlu)-Double.parseDouble(soldBseVlu));
     diff_pct=String.valueOf(util.roundToDecimals(((Double.parseDouble(soldVlu) - Double.parseDouble(soldBseVlu))*100/Double.parseDouble(soldBseVlu)), 2));
     }
     }
  %>
  <tr>
  <th><%=vall%></th>
    <td align="right">
  <A href="<%=info.getReqUrl()%>/report/genericReportMix.do?method=pktlist&stt=NEW&level1=<%=level1%>&prpval=<%=vall%>" target="SC"  title="Click Here To See Packets">
  <%=util.nvl((String)dataTbl.get(vall+"_NEW_CTS"))%>
  </a>
  </td>
  <td align="right">
  <A href="<%=info.getReqUrl()%>/report/genericReportMix.do?method=pktlist&stt=MKT&level1=<%=level1%>&prpval=<%=vall%>" target="SC"  title="Click Here To See Packets">
  <%=util.nvl((String)dataTbl.get(vall+"_MKT_QTY"))%>
  </a>
  </td>
  <td align="right"><%=util.nvl((String)dataTbl.get(vall+"_MKT_CTS"))%></td>
  <td align="right"><%=util.nvl((String)dataTbl.get(vall+"_MKT_AVG"))%></td>
  <td align="right"><%=util.nvl((String)dataTbl.get(vall+"_MKT_VLU"))%></td>
  <td align="right"><%=msd%></td>
  <td align="right">
  <A href="<%=info.getReqUrl()%>/report/genericReportMix.do?method=pktlist&stt=MEMO&level1=<%=level1%>&prpval=<%=vall%>" target="SC"  title="Click Here To See Packets">
  <%=util.nvl((String)dataTbl.get(vall+"_MEMO_QTY"))%>
  </a>
  </td>
  <td align="right"><%=util.nvl((String)dataTbl.get(vall+"_MEMO_CTS"))%></td>
  <td align="right"><%=util.nvl((String)dataTbl.get(vall+"_MEMO_AVG"))%></td>
  <td align="right"><%=util.nvl((String)dataTbl.get(vall+"_MEMO_VLU"))%></td>
  <td align="right">
  <A href="<%=info.getReqUrl()%>/report/genericReportMix.do?method=pktlist&stt=SOLD&level1=<%=level1%>&prpval=<%=vall%>" target="SC"  title="Click Here To See Packets">
  <%=util.nvl((String)dataTbl.get(vall+"_SOLD_QTY"))%>
  </a>
  </td>
  <td align="right"><%=util.nvl((String)dataTbl.get(vall+"_SOLD_CTS"))%></td>
  <td align="right"><%=util.nvl((String)dataTbl.get(vall+"_SOLD_AVG"))%></td>
  <td align="right"><%=util.nvl((String)dataTbl.get(vall+"_SOLD_VLU"))%></td>
  <td align="right"><%=svpd%></td>
  <td align="right"><%=diff%></td>
  <td align="right"><%=diff_pct%></td>
  </tr>
  <%}}%>
  <tr>
  <th>Total</th>
   <%String svpd ="";
   String msd ="";
      String diff="",diff_pct="";
   String mktVlu = util.nvl((String)dataTbl.get("MKT_VLU_TTL"),"0");
   String soldVlu = util.nvl((String)dataTbl.get("SOLD_VLU_TTL"),"0");
      String soldBseVlu = util.nvl((String)dataTbl.get("SOLD_BSEVLU_TTL"),"0");
     if(!soldVlu.equals("") && !soldVlu.equals("0")){
     svpd=util.nvl(String.valueOf(Math.round(Double.parseDouble(soldVlu)/Double.parseDouble(days))),"0");
     if(!mktVlu.equals("") && !mktVlu.equals("0") && !svpd.equals("0"))
     msd=util.nvl(String.valueOf(Math.round(Double.parseDouble(mktVlu)/Double.parseDouble(svpd))),"0");
     if(svpd.equals("0"))
     svpd="";
     if(msd.equals("0"))
     msd="";
     if(!soldBseVlu.equals("") && !soldBseVlu.equals("0")){
     diff=String.valueOf(Double.parseDouble(soldVlu)-Double.parseDouble(soldBseVlu));
     diff_pct=String.valueOf(util.roundToDecimals(((Double.parseDouble(soldVlu) - Double.parseDouble(soldBseVlu))*100/Double.parseDouble(soldBseVlu)), 2));
     }
     }%>
       <td>
  <A href="<%=info.getReqUrl()%>/report/genericReportMix.do?method=pktlist&stt=NEW&level1=<%=level1%>" target="SC"  title="Click Here To See Packets">
  <%=util.nvl((String)dataTbl.get("NEW_CTS_TTL"))%>
  </a>
  </td>
  <td>
  <A href="<%=info.getReqUrl()%>/report/genericReportMix.do?method=pktlist&stt=MKT&level1=<%=level1%>" target="SC"  title="Click Here To See Packets">
  <%=util.nvl((String)dataTbl.get("MKT_QTY_TTL"))%>
  </a>
  </td>
  <td align="right"><%=util.nvl((String)dataTbl.get("MKT_CTS_TTL"))%></td>
  <td align="right"><%=util.nvl((String)dataTbl.get("MKT_AVG_TTL"))%></td>
  <td align="right"><%=util.nvl((String)dataTbl.get("MKT_VLU_TTL"))%></td>
  <td align="right"><%=msd%></td>
  <td align="right">
  <A href="<%=info.getReqUrl()%>/report/genericReportMix.do?method=pktlist&stt=MEMO&level1=<%=level1%>" target="SC"  title="Click Here To See Packets">
  <%=util.nvl((String)dataTbl.get("MEMO_QTY_TTL"))%>
  </a>
  </td>
  <td align="right"><%=util.nvl((String)dataTbl.get("MEMO_CTS_TTL"))%></td>
  <td align="right"><%=util.nvl((String)dataTbl.get("MEMO_AVG_TTL"))%></td>
  <td align="right"><%=util.nvl((String)dataTbl.get("MEMO_VLU_TTL"))%></td>
  <td align="right">
  <A href="<%=info.getReqUrl()%>/report/genericReportMix.do?method=pktlist&stt=SOLD&level1=<%=level1%>" target="SC"  title="Click Here To See Packets">
  <%=util.nvl((String)dataTbl.get("SOLD_QTY_TTL"))%>
  </a>
  </td>
  <td align="right"><%=util.nvl((String)dataTbl.get("SOLD_CTS_TTL"))%></td>
  <td align="right"><%=util.nvl((String)dataTbl.get("SOLD_AVG_TTL"))%></td>
  <td align="right"><%=util.nvl((String)dataTbl.get("SOLD_VLU_TTL"))%></td>
  <td align="right"><%=diff%></td>
  <td align="right"><%=diff_pct%></td>
  <td align="right"><%=svpd%></td>
  </tr>
  </table>
  </td>
  </tr>
  <%}else{%>
  <tr>
  <td>
  Sorry No result Found
  </td>
  </tr>
  <%}%>
  </table>
  </td>
  </tr>
  <tr><td>
<jsp:include page="/footer.jsp" />
</td></tr>
  </table>  
       <%@include file="../calendar.jsp"%>
  </body>
</html>