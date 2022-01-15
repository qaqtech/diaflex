<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page errorPage="../error_page.jsp" %>
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import="java.util.ArrayList,java.util.HashMap,java.sql.ResultSet,ft.com.*, java.util.Date, java.util.ArrayList, ft.com.dao.*"%>
	<jsp:useBean id="info" class="ft.com.InfoMgr" scope="session" />
	<jsp:useBean id="util" class="ft.com.JspUtil" scope="page" />

<%@taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>
<%@taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>

<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>Memo</title>
    <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/box.js?v=<%=info.getJsVersion()%> " > </script>
    <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/bse.css?v=<%=info.getJsVersion()%>"/>
      <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/style.css?v=<%=info.getJsVersion()%>"/>
       <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/general.css?v=<%=info.getJsVersion()%>"/>
       <script type="text/javascript" src="<%=info.getReqUrl()%>/PopScripts/jquery-1.2.6.min.js" > </script>
        <script type="text/javascript" src="<%=info.getReqUrl()%>/PopScripts/popup.js?v=<%=info.getJsVersion()%> " > </script>
       <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/bse.js?v=<%=info.getJsVersion()%> " > </script>
          <script language="javaScript" type="text/javascript" src="<%=info.getReqUrl()%>/calendar/calendar.js"></script>
   <link href="<%=info.getReqUrl()%>/calendar/calendar.css" rel="stylesheet" type="text/css" />
          <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/priceChange.js?v=<%=info.getJsVersion()%> " > </script>
           <script language="javaScript" type="text/javascript" src="<%=info.getReqUrl()%>/scripts/ajax.js?v=<%=info.getJsVersion()%>"></script>
<script type="text/javascript" src="<%=info.getReqUrl()%>/chartscript/amcharts.js?v=<%=info.getJsVersion()%> " > </script>
<script type="text/javascript" src="<%=info.getReqUrl()%>/chartscript/chartajax.js?v=<%=info.getJsVersion()%> " > </script>
<script type="text/javascript" src="<%=info.getReqUrl()%>/chartscript/pie.js?v=<%=info.getJsVersion()%> " > </script>
  </head>
<%
String normal= util.nvl((String)request.getAttribute("NORMAL")); 
 String ketan= util.nvl((String)request.getParameter("KET"));
 if(ketan.equals(""))
 ketan= util.nvl((String)request.getAttribute("ketan")); 
  String ifrsBtn= util.nvl((String)request.getParameter("ifrsBtn"));
 if(ifrsBtn.equals(""))
 ifrsBtn= util.nvl((String)request.getAttribute("ifrsBtn")); 
        String logId=String.valueOf(info.getLogId());
        String onfocus="cook('"+logId+"')";
        %>
 <body onfocus="<%=onfocus%>" onload="MM_preloadImages('images/faunalogo.png')" topmargin="0" leftmargin="0" rightmargin="0" bottommargin="0">
<div id="backgroundPopup"></div>
<div id="popupContactASSFNL">
<table class="popUpTb">
 <tr><td height="5%">&nbsp;&nbsp;&nbsp;<html:button property="value(close)" styleClass="submit"  onclick="MKEmptyPopup('SC');disablePopupASSFNL()" value="Close"  /> </td></tr>
<tr><td valign="top" height="95%">
<iframe name="SC" class="frameStyle"   align="middle" frameborder="0">

</iframe></td></tr>
</table>
</div>
<input type="hidden" id="reqUrl" name="reqUrl" value="<%=info.getReqUrl()%>"/>
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
  <img src="../images/line.jpg" border="0" /></td><td valign="middle"> <span class="pgHed">Memo (Lot)</span> </td>
</tr></table>
  </td>
  </tr>
  <tr><td valign="top" class="tdLayout">
  <table><tr><td>
  <table><tr><td valign="top">
    <html:form action="/report/orclReportAction.do?method=memohk"  method="POST">
       <html:hidden property="value(ketan)" name="orclReportForm" value="<%=ketan%>" /> 
       <html:hidden property="value(ifrsBtn)" name="orclReportForm" value="<%=ifrsBtn%>" /> 
        <table class="grid1">
        <tr><th colspan="2">Lot Search</th></tr>
         <tr><td>Start </td>  
       <td>From&nbsp; <html:text property="value(startfrmdte)" styleId="startfrmdte" name="orclReportForm" size="8" /><img src="<%=info.getReqUrl()%>/images/calender.png" onClick="setYears(2011, <%=info.getCurrentYear()%>);showCalender(this, 'startfrmdte');"> 
       To&nbsp; <html:text property="value(starttodte)" styleId="starttodte" name="orclReportForm"  size="8"/> <img src="<%=info.getReqUrl()%>/images/calender.png" onClick="setYears(2011, <%=info.getCurrentYear()%>);showCalender(this, 'starttodte');"></td>
      </tr>
      <tr><td>Complete </td>  
       <td>From&nbsp; <html:text property="value(compfrdte)" styleId="compfrdte" name="orclReportForm" size="8" /><img src="<%=info.getReqUrl()%>/images/calender.png" onClick="setYears(2011, <%=info.getCurrentYear()%>);showCalender(this, 'compfrdte');"> 
       To&nbsp; <html:text property="value(comptodte)" styleId="comptodte" name="orclReportForm"  size="8"/> <img src="<%=info.getReqUrl()%>/images/calender.png" onClick="setYears(2011, <%=info.getCurrentYear()%>);showCalender(this, 'comptodte');"></td>
      </tr>
      <tr><td>Status</td>
      <td>
      <html:select property="value(stt)" styleId="status"> 
       <html:option value="" >All</html:option> 
       <html:option value="COMPLETED" >COMPLETED</html:option> 
       <html:option value="UNCOMPLETED" >UNCOMPLETED</html:option> 
      </html:select>
      </td>
      </tr>
      <tr><td>Management Avg</td>
      <td>
      <html:select property="value(mgmt)" styleId="status"> 
       <html:option value="" >All</html:option> 
       <html:option value="Y" >YES</html:option> 
       <html:option value="N" >NO</html:option> 
      </html:select>
      </td>
      </tr>
      <tr><td colspan="2">Or</td></tr>
      <tr>
      <td>Memo Id</td><td><html:text property="value(memoIdn)" styleId="memoid"/></td>
      </tr>
       <tr><td>Date </td>  
       <td>P1&nbsp; <html:text property="value(dteFrm)" styleId="dteFrm" name="orclReportForm" size="8" /><img src="<%=info.getReqUrl()%>/images/calender.png" onClick="setYears(2011, <%=info.getCurrentYear()%>);showCalender(this, 'dteFrm');"> 
       P2&nbsp; <html:text property="value(dteTo)" styleId="dteTo" name="orclReportForm"  size="8"/> <img src="<%=info.getReqUrl()%>/images/calender.png" onClick="setYears(2011, <%=info.getCurrentYear()%>);showCalender(this, 'dteTo');"></td>
       </tr>
        </table>
        <p>
        <%if(!ifrsBtn.equals("")){%>
        <html:submit property="value(memolotifrs)" value="View Ifrs" styleClass="submit" />
        <%}else{%>
        <html:submit property="submit" value="View" styleClass="submit" />
        <%}%>
        <html:submit property="value(deptsummary)" value="Dept Wise Summary" styleClass="submit" />
        <html:submit property="value(memopri)" value="Memo Price Comp" styleClass="submit" onclick="return validatemlotpri();" /></p>
    </html:form>
    </td>
    </tr>
    </table>
    <table width="100%">
    <tr>
    <td valign="top">
    <%
    String view= util.nvl((String)request.getAttribute("view")); 
    HashMap dataDtl= (HashMap)session.getAttribute("dataDtl");
    HashMap datattl= (HashMap)session.getAttribute("datattl");
    ArrayList keytable= (ArrayList)session.getAttribute("keytable");
    ArrayList deptLst= (ArrayList)session.getAttribute("deptLst");
    HashMap data=new HashMap();
    HashMap datasum=new HashMap();
    if(view.equals("Y")){
        HashMap prp = info.getPrp();
        HashMap mprp = info.getMprp();
        ArrayList prpSrtDept = null;
        ArrayList prpValDept = null;
        prpSrtDept =new ArrayList();
        prpSrtDept = (ArrayList)prp.get("DEPT"+"S"); 
        prpValDept = (ArrayList)prp.get("DEPT"+"V");
    if(normal.equals("Y")){
    if(dataDtl!=null && dataDtl.size()>0){
    String pDteFrm= util.nvl((String)request.getAttribute("pDteFrm")); 
    String pDteTo= util.nvl((String)request.getAttribute("pDteTo"));
    ArrayList conatinsdata= new ArrayList();
    String shape="",sz="",qty="",display="display:none",displaymemopri="display:none",faamt="",cts="",faavg="",cpavg="",colper="",colpersum="",mavg="",msumavg="",avg="",avgp1="",avgp2="",qtysum="",ctssum="",faamtsum="",faavgsum="",cpsumavg="",avgsum="",avgp1sum="",avgp2sum="",grandcts="",grandfaamt="",compdte="",fac="",memo="",mlotidn="";
        ArrayList prpSrtShape = null;
        ArrayList prpValShape = null;
        prpSrtShape =new ArrayList();
        prpSrtShape = (ArrayList)prp.get("SHAPE"+"S");
        prpValShape = (ArrayList)prp.get("SHAPE"+"V");
        String roundSrt=(String)prpSrtShape.get(prpValShape.indexOf("ROUND"));
    if(!ketan.equals(""))
    display="";
    if(!pDteFrm.equals("") && !pDteTo.equals(""))
    displaymemopri="";
    data=(HashMap)dataDtl.get("GRANDTTL");
    grandcts=util.nvl((String)data.get("CTS"));
    compdte=util.nvl((String)data.get("CMPDTE"));
    fac=util.nvl((String)data.get("FAC"));
    memo=util.nvl((String)data.get("MEMO"));
    avg=util.nvl((String)data.get("AVG"));
    grandfaamt=util.nvl((String)data.get("FAAMT"),"0");
    mlotidn=util.nvl((String)data.get("IDN"));
    %>
    <table class="grid1" id="dataTable">
    <%String target = "SC_"+memo;%>
    <tr id="<%=target%>"><td>
    <span style="padding-left:10px;"><a onclick="goTo('orclReportAction.do?method=createPDFMEMOHK','','')"><img src="../images/PDFIconSmall.jpg" border="0"/> </a></span>
     <%
  ArrayList rolenmLst=(ArrayList)info.getRoleLst();
  String usrFlg=util.nvl((String)info.getUsrFlg());
  if(usrFlg.equals("SYS") || rolenmLst.contains("ADMIN")){
  %>
  &nbsp;<img src="../images/edit.jpg" title="Edit Properties Module" onclick="newWindow('<%=info.getReqUrl()%>/dynamicProperty.do?method=load&mdl=MEMO_LOTPKT_VW&sname=MEMOLOT_HKREPORT&par=A')" border="0" width="15px" height="15px"/>
  <%}%>
  &nbsp;&nbsp;<a href="orclReportAction.do?method=memohkPKTDTL" target="_blank">Pkt Dtl</a>
  &nbsp;&nbsp;<a href="group.do?method=load&mlot=Y&PKT_TYP=Y" target="_blank" title="Single Stock">Single Stock</a>
  &nbsp;&nbsp;<a href="mixStockSummary.do?method=load&mlot=Y" target="_blank" title="Mix Stock">Mix Stock</a>
  &nbsp;&nbsp;<a href="orclReportAction.do?method=mlotrpt&PKT_TYP=NR" target="_blank" title="Color Clarity Report">Color Clarity Report</a>
  &nbsp;&nbsp;<a href="orclReportAction.do?method=mlotrpt&PKT_TYP=MIX" target="_blank" title="Mix Size Clarity Report">Mix Size Clarity Report</a>
  &nbsp;&nbsp;<a href="orclReportAction.do?method=piememo&memo=<%=memo%>" id="LNK_<%=memo%>" onclick="loadASSFNL('<%=target%>','LNK_<%=memo%>')"  target="SC">Pie Graph</a>
    </td></tr>
    <tr><td valign="top">
    <table class="grid1" style="width:500px">
    <tr><td colspan="15" align="left">Memo -<%=memo%>&nbsp; Factory -<%=fac%>&nbsp; Complete Date -<%=compdte%></td></tr>
    <%for(int l=0;l<deptLst.size();l++){
    String srtDept=(String)deptLst.get(l);
    String dept=util.nvl((String)prpValDept.get(prpSrtDept.indexOf(srtDept))); 
    String ttldisplay="N";
    %>
    <tr><td colspan="15"><b><%=dept%></b>&nbsp;&nbsp; 
    <%if(dept.indexOf("MIX")!=-1){%> 
    <a href="orclReportAction.do?method=mlotAsstDtl&dept=<%=dept%>&memo=<%=memo%>" target="_blank"> Assort Detail </a>
    <%}%>
    </td></tr>
    <tr>
    <th>Size</th><th>Pcs</th><th>Cts</th><th>Col%</th><th>Cp.Avg</th><th>Avg</th><th>F.Avg</th><th>Mfg.Avg</th><th>Mgmt Avg</th><th>50% Avg</th><th style="<%=display%>">ketan</th><th>Crt%</th><th>Tot%</th><th style="<%=displaymemopri%>"><%=pDteFrm%></th><th style="<%=displaymemopri%>"><%=pDteTo%></th>
    </tr>
    
    <%for(int i=0;i<prpSrtShape.size();i++){
    String srtShape=(String)prpSrtShape.get(i);
    String key=srtDept+"_"+srtShape;
    conatinsdata= new ArrayList();
    datasum=new HashMap();
    conatinsdata=(ArrayList)dataDtl.get(key);
    if(conatinsdata!=null && conatinsdata.size()>0){
    datasum=(HashMap)datattl.get(key);
    shape=util.nvl((String)datasum.get("SH"));
    qtysum=util.nvl((String)datasum.get("QTY"));
    ctssum=util.nvl((String)datasum.get("CTS"));
    avgsum=util.nvl((String)datasum.get("AVG"));
    avgp1sum=util.nvl((String)datasum.get("P1"));
    avgp2sum=util.nvl((String)datasum.get("P2"));
    faavgsum=util.nvl((String)datasum.get("FAAVG"));
    cpsumavg=util.nvl((String)datasum.get("CPAVG"));
    faamtsum=util.nvl((String)datasum.get("FAAMT"),"0");
    colpersum=util.nvl((String)datasum.get("COLPCT"));
    msumavg=util.nvl((String)datasum.get("MFGAVG"));
    String subkey=key.substring(key.indexOf("_")+1,key.length());
    if(subkey.equals(roundSrt)){
    %>
    <tr><td colspan="15"><b>Shape :</b><%=shape%></td></tr>
    <%
    for(int j=0;j<conatinsdata.size();j++){
    data=new HashMap();
    data=(HashMap)conatinsdata.get(j);
    if(data!=null && data.size()>0){
    shape=util.nvl((String)data.get("SH"));
    sz=util.nvl((String)data.get("SZ"));
    qty=util.nvl((String)data.get("QTY"));
    cts=util.nvl((String)data.get("CTS"));
    avg=util.nvl((String)data.get("AVG"));
    avgp1=util.nvl((String)data.get("P1"));
    avgp2=util.nvl((String)data.get("P2"));
    faavg=util.nvl((String)data.get("FAAVG"));
    cpavg=util.nvl((String)data.get("CPAVG"));
    faamt=util.nvl((String)data.get("FAAMT"),"0");
    colper=util.nvl((String)data.get("COLPCT"));
    mavg=util.nvl((String)data.get("MFGAVG"));
    Double crt=(Double.parseDouble(cts)/Double.parseDouble(ctssum))*100;
    Double tot=0.0;
    if(!faamt.equals("0") && !grandfaamt.equals("0"))
    tot=(Double.parseDouble(faamt)/Double.parseDouble(grandfaamt))*100;
    %>
    <tr>
    <td>
    <%if(dept.indexOf("GIA")!=-1){%>GR.<%}%>
    <%=sz%></td><td align="right"><%=qty%></td><td align="right"><%=cts%></td><td align="right"><%=colper%></td><td align="right"><%=cpavg%></td><td align="right"><%=avg%></td><td align="right"><%=faavg%></td><td align="right"><%=mavg%></td><td></td><td></td><td style="<%=display%>"></td><td align="right"><%=util.Round(crt, 2)%>%</td><td align="right"><%=util.Round(tot, 2)%>%</td><td align="right" style="<%=displaymemopri%>"><%=avgp1%></td><td align="right" style="<%=displaymemopri%>"><%=avgp2%></td>
    </tr>
    <%}
    }
    Double crt=(Double.parseDouble(ctssum)/Double.parseDouble(ctssum))*100;
    Double tot=0.0;
    if(!faamtsum.equals("0") && !grandfaamt.equals("0"))
    tot=(Double.parseDouble(faamtsum)/Double.parseDouble(grandfaamt))*100;
    %>
    <tr>
    <td nowrap="nowrap"><b>Round Total</b></td><td align="right"><%=qtysum%></td><td align="right"><%=ctssum%></td><td align="right"><%=colpersum%><td align="right"><%=cpsumavg%></td></td><td align="right"><%=avgsum%></td><td align="right"><%=faavgsum%></td><td align="right"><%=msumavg%></td><td></td><td></td><td style="<%=display%>"></td><td align="right"><%=util.Round(crt, 2)%>%</td><td align="right"><%=util.Round(tot, 2)%>%</td><td align="right" style="<%=displaymemopri%>"><%=avgp1sum%></td><td align="right" style="<%=displaymemopri%>"><%=avgp2sum%></td>
    </tr>
    <%}else{
    Double crt=(Double.parseDouble(ctssum)/Double.parseDouble(ctssum))*100;
    Double tot=0.0;
    if(!faamtsum.equals("0") && !grandfaamt.equals("0"))
    tot=(Double.parseDouble(faamtsum)/Double.parseDouble(grandfaamt))*100;
    ttldisplay="Y";
    %>
    <tr>
    <td><%=shape%></td><td align="right"><%=qtysum%></td><td align="right"><%=ctssum%></td><td align="right"><%=colpersum%></td><td align="right"><%=cpsumavg%></td><td align="right"><%=avgsum%></td><td align="right"><%=faavgsum%></td><td align="right"><%=msumavg%></td><td></td><td></td><td style="<%=display%>"></td><td align="right"><%=util.Round(crt, 2)%>%</td><td align="right"><%=util.Round(tot, 2)%>%</td><td align="right" style="<%=displaymemopri%>"><%=avgp1sum%></td><td align="right" style="<%=displaymemopri%>"><%=avgp2sum%></td>
    </tr>
    <%}%>
    <%}
    }
    if(ttldisplay.equals("Y")){
    data=new HashMap();
    data=(HashMap)dataDtl.get(srtDept+"_TTL");
    if(data!=null){
    qty=util.nvl((String)data.get("QTY"));
    cts=util.nvl((String)data.get("CTS"));
    avg=util.nvl((String)data.get("AVG"));
    avgp1=util.nvl((String)data.get("P1"));
    avgp2=util.nvl((String)data.get("P2"));
    faavg=util.nvl((String)data.get("FAAVG"));
    cpavg=util.nvl((String)data.get("CPAVG"));
    faamt=util.nvl((String)data.get("FAAMT"),"0");
    colper=util.nvl((String)data.get("COLPCT"));
    mavg=util.nvl((String)data.get("MFGAVG"));
    Double toto=0.0;
    if(!grandfaamt.equals("0") && !faamt.equals("0"))
    toto=(Double.parseDouble(faamt)/Double.parseDouble(grandfaamt))*100;
    %>
   <tr>
   <td nowrap="nowrap"><b>Fancy Total</b></td><td align="right"><label class="blueLabel"><%=qty%></label></td><td align="right"><label class="blueLabel"><%=cts%></label></td>
   <td align="right"><label class="blueLabel"><%=colper%></label></td>
   <td align="right">
   <label class="blueLabel"><%=cpavg%></label></td>
   <td align="right"><label class="blueLabel"><%=avg%></label></td>
   <td align="right">
   <label class="blueLabel"><%=faavg%></label></td>
   <td align="right">
   <label class="blueLabel"><%=mavg%></label></td>
   <td align="center"></td>
   <td align="center"></td>
   <td align="center" style="<%=display%>"></td>
   <td align="right"></td><td align="right"><label class="blueLabel"><%=util.Round(toto, 2)%>%</label></td>
   <td align="right" style="<%=displaymemopri%>"><label class="blueLabel"><%=avgp1%></label></td>
   <td align="right" style="<%=displaymemopri%>"><label class="blueLabel"><%=avgp2%></label></td>
   </tr> 
    <%
    }}
    data=new HashMap();
    data=(HashMap)dataDtl.get(srtDept);
    qty=util.nvl((String)data.get("QTY"));
    cts=util.nvl((String)data.get("CTS"));
    avg=util.nvl((String)data.get("AVG"));
    avgp1=util.nvl((String)data.get("P1"));
    avgp2=util.nvl((String)data.get("P2"));
    faavg=util.nvl((String)data.get("FAAVG"));
    cpavg=util.nvl((String)data.get("CPAVG"));
    faamt=util.nvl((String)data.get("FAAMT"),"0");
    colper=util.nvl((String)data.get("COLPCT"));
    mavg=util.nvl((String)data.get("MFGAVG"));
    Double tot=0.0;
    if(!faamt.equals("0") && !grandfaamt.equals("0"))
    tot=(Double.parseDouble(faamt)/Double.parseDouble(grandfaamt))*100;
    String accDis = "value(ACC_"+mlotidn+"_"+srtDept+")";
    String accAvgID = "ACC_"+mlotidn+"_"+srtDept;
    String onchange="savemlotDept('"+mlotidn+"','"+srtDept+"','"+accAvgID+"','acc_av')";
    String avgDis = "value(AVG_"+mlotidn+"_"+srtDept+")";
    String avgID = "AVG_"+mlotidn+"_"+srtDept;
    String avgonchange="savemlotDept('"+mlotidn+"','"+srtDept+"','"+avgID+"','avg_50')";
    String avgketan = "value(KET_"+mlotidn+"_"+srtDept+")";
    String avgketanID = "KET_"+mlotidn+"_"+srtDept;
    String avgketanchange="savemlotDept('"+mlotidn+"','"+srtDept+"','"+avgketanID+"','ketan_av')";
    %>
    <tr>
   <td nowrap="nowrap"><b>Dept Total</b></td><td align="right"><label class="blueLabel"><%=qty%></label></td><td align="right"><label class="blueLabel"><%=cts%></label></td>
   <td align="right"><label class="blueLabel"><%=colper%></label></td>
      <td><label class="blueLabel"><%=cpavg%></label></td>
   <td align="right"><label class="blueLabel"><%=avg%></label></td>
   <td align="right">
   <input type="hidden" name="DEPTFA_<%=srtDept%>" id="DEPTFA_<%=srtDept%>" value="<%=faavg%>">
   <label class="blueLabel"><%=faavg%></label></td>
   <td align="right">
   <label class="blueLabel"><%=mavg%></label></td>
   <td align="center"><html:text property="<%=accDis%>" size="6" styleId="<%=accAvgID%>" onchange="<%=onchange%>" name="orclReportForm"/></td>
   <td align="center"><html:text property="<%=avgDis%>" size="6" styleId="<%=avgID%>" onchange="<%=avgonchange%>" name="orclReportForm"/></td>
   <td align="center" style="<%=display%>"><html:text property="<%=avgketan%>" size="6" styleId="<%=avgketanID%>" onchange="<%=avgketanchange%>" onfocus="<%=avgketanchange%>" name="orclReportForm"/></td>
   <td align="right"></td><td align="right"><label class="blueLabel"><%=util.Round(tot, 2)%>%</label></td>
   <td align="right" style="<%=displaymemopri%>"><label class="blueLabel"><%=avgp1%></label></td>
   <td align="right" style="<%=displaymemopri%>"><label class="blueLabel"><%=avgp2%></label></td>
   </tr> 
    <%
    }
    data=new HashMap();
    data=(HashMap)dataDtl.get("GRANDTTL");
    qty=util.nvl((String)data.get("QTY"));
    cts=util.nvl((String)data.get("CTS"));
    avg=util.nvl((String)data.get("AVG"));
    avgp1=util.nvl((String)data.get("P1"));
    avgp2=util.nvl((String)data.get("P2"));
    faavg=util.nvl((String)data.get("FAAVG"));
    cpavg=util.nvl((String)data.get("CPAVG"));
    colper=util.nvl((String)data.get("COLPCT"));
    mavg=util.nvl((String)data.get("MFGAVG"));
    String accDis = "value(ACC_"+mlotidn+")";
    String accAvgID = "ACC_"+mlotidn;
    String onchange="savemlotDept('"+mlotidn+"','','"+accAvgID+"','acc_av')";
    String avgDis = "value(AVG_"+mlotidn+")";
    String avgID = "AVG_"+mlotidn;
    String avgonchange="savemlotDept('"+mlotidn+"','','"+avgID+"','avg_50')";
    String ketanDis = "value(KET_"+mlotidn+")";
    String ketanID = "KET_"+mlotidn;
    String avgketanchange="savemlotDept('"+mlotidn+"','','"+ketanID+"','ketan_av');ketanavg('"+ketanID+"')";
    %>
   <tr>
   <th>Grand Total</th><td align="right"><label class="redLabel"><%=qty%></label></td><td align="right"><label class="redLabel"><%=cts%></label></td>
   <td align="right"><label class="redLabel"><%=colper%></label></td>
         <td align="right"><label class="redLabel"><%=cpavg%></label></td>
   <td align="right"><label class="redLabel"><%=avg%></label></td>
   <td align="right"><label class="redLabel"><%=faavg%></label>
   <input type="hidden" name="TTLFA" id="TTLFA" value="<%=faavg%>">
   </td>
   <td align="right"><label class="redLabel"><%=mavg%></label></td>
   <td align="center"><html:text property="<%=accDis%>" size="6" styleId="<%=accAvgID%>" onchange="<%=onchange%>" name="orclReportForm"/></td>
   <td align="center"><html:text property="<%=avgDis%>" size="6" styleId="<%=avgID%>" onchange="<%=avgonchange%>" name="orclReportForm"/></td>
   <td align="center" style="<%=display%>"><html:text property="<%=ketanDis%>" size="6" styleId="<%=ketanID%>" onchange="<%=avgketanchange%>" name="orclReportForm"/></td>
   <td align="right"></td><td align="right"><label class="redLabel">100.0%</label></td>
   <td align="right" style="<%=displaymemopri%>"><label class="redLabel"><%=avgp1%></label></td>
   <td align="right" style="<%=displaymemopri%>"><label class="redLabel"><%=avgp2%></label></td>
   </tr>   
   <tr>
   <td colspan="15"></td>
   </tr>
   <tr>
   <td colspan="15" align="center">Shape Summary</td>
   </tr>
   <tr>
   <th>Shape</th><th>Pcs</th><th>Cts</th><th>Col%</th><th>Cp.Avg</th><th>Avg</th><th>F.Avg</th><th>Mfg.Avg</th><th>Mgmt Avg</th><th>50% Avg</th><th style="<%=display%>">ketan</th><th>Crt%</th><th>Tot%</th><th style="<%=displaymemopri%>"><%=pDteFrm%></th><th style="<%=displaymemopri%>"><%=pDteTo%></th>
   </tr>
   <%for(int i=0;i<prpSrtShape.size();i++){
    String srtShape=(String)prpSrtShape.get(i);
    String key=srtShape;
    conatinsdata= new ArrayList();
    datasum=new HashMap();
    datasum=(HashMap)datattl.get(key);
    if(datasum!=null){
    shape=util.nvl((String)datasum.get("SH"));
    qtysum=util.nvl((String)datasum.get("QTY"));
    ctssum=util.nvl((String)datasum.get("CTS"));
    avgsum=util.nvl((String)datasum.get("AVG"));
    avgp1sum=util.nvl((String)datasum.get("P1"));
    avgp2sum=util.nvl((String)datasum.get("P2"));
    faavgsum=util.nvl((String)datasum.get("FAAVG"));
    cpsumavg=util.nvl((String)datasum.get("CPAVG"));
    faamtsum=util.nvl((String)datasum.get("FAAMT"),"0");
    colpersum=util.nvl((String)datasum.get("COLPCT"));
    msumavg=util.nvl((String)datasum.get("MFGAVG"));
    Double crt=(Double.parseDouble(ctssum)/Double.parseDouble(ctssum))*100;
    Double tot=0.0;
    if(!faamtsum.equals("0") && !grandfaamt.equals("0"))
    tot=(Double.parseDouble(faamtsum)/Double.parseDouble(grandfaamt))*100;
    %>
    <tr>
    <td><%=shape%></td><td align="right"><%=qtysum%></td><td align="right"><%=ctssum%></td><td align="right"><%=colpersum%></td>
    <td align="right"><%=cpsumavg%></td>
    <td align="right"><%=avgsum%></td><td align="right"><%=faavgsum%></td><td align="right"><%=msumavg%></td>
    <td></td><td></td><td style="<%=display%>"></td><td align="right"><%=util.Round(crt, 2)%>%</td><td align="right"><%=util.Round(tot, 2)%>%</td><td align="right" style="<%=displaymemopri%>"><%=avgp1sum%></td><td align="right" style="<%=displaymemopri%>"><%=avgp2sum%></td>
    </tr>
    <%}}%>
    </table>
    </td>

    <td valign="top">
    <table class="grid1" style="width:300px">
        <%for(int l=0;l<deptLst.size();l++){
    String srtDept=(String)deptLst.get(l);
    String dept=(String)prpValDept.get(prpSrtDept.indexOf(srtDept));  
    %>   
    <%for(int i=0;i<prpSrtShape.size();i++){
    String srtShape=(String)prpSrtShape.get(i);
    String key=srtDept+"_"+srtShape;
    conatinsdata= new ArrayList();
    datasum=new HashMap();
    conatinsdata=(ArrayList)dataDtl.get(key);
    if(conatinsdata!=null && conatinsdata.size()>0){
    datasum=(HashMap)datattl.get(key);
    shape=util.nvl((String)datasum.get("SH"));
    qtysum=util.nvl((String)datasum.get("QTY"));
    ctssum=util.nvl((String)datasum.get("CTS"));
    faavgsum=util.nvl((String)datasum.get("FAAVG"));
    faamtsum=util.nvl((String)datasum.get("FAAMT"),"0");
    String subkey=key.substring(key.indexOf("_")+1,key.length());
    if(!subkey.equals(roundSrt)){
    %>
    <tr><th colspan="3"><%=shape%></th><th  colspan="2"><%=dept%></th></tr>
    <tr><th>Size</th><th>Pcs</th><th>Carat</th><th>Rate</th><th>Per%</th></tr>
    <%
    for(int j=0;j<conatinsdata.size();j++){
    data=new HashMap();
    data=(HashMap)conatinsdata.get(j);
    if(data!=null && data.size()>0){
    shape=util.nvl((String)data.get("SH"));
    sz=util.nvl((String)data.get("SZ"));
    qty=util.nvl((String)data.get("QTY"));
    cts=util.nvl((String)data.get("CTS"));
    faavg=util.nvl((String)data.get("FAAVG"));
    faamt=util.nvl((String)data.get("FAAMT"),"0");
    Double crt=(Double.parseDouble(cts)/Double.parseDouble(ctssum))*100;
    Double tot=0.0;
    if(!faamt.equals("0") && !faamtsum.equals("0"))
    tot=(Double.parseDouble(faamt)/Double.parseDouble(faamtsum))*100;
    %>
    <tr>
    <td nowrap="nowrap"><%=sz%></td><td align="right"><%=qty%></td><td align="right"><%=cts%></td><td align="right"><%=faavg%></td><td align="right"><%=util.Round(tot, 2)%>%</td>
    </tr>
    <%}
    }
    Double crt=(Double.parseDouble(ctssum)/Double.parseDouble(ctssum))*100;
    Double tot=0.0;
    if(!faamtsum.equals("0") && !grandfaamt.equals("0"))
    tot=(Double.parseDouble(faamtsum)/Double.parseDouble(grandfaamt))*100;
    %>
    <tr>
    <td><b>Total</b></td><td align="right"><%=qtysum%></td><td align="right"><%=ctssum%></td><td align="right"><%=faavgsum%></td><td align="right"><%=util.Round(tot, 2)%>%</td>
    </tr>
    <%}%>
    <%}}}%>
    </table>
    </td>
    </tr></table>
    <%}}else if(normal.equals("DEPTSUMMY")){
    ArrayList memoList= (ArrayList)session.getAttribute("memoList"); 
    if(memoList!=null && memoList.size()>0){
    %>
    <table>
    <tr><td><a href="orclReportAction.do?method=memohkPKTDTL" target="_blank">Pkt Dtl</a></td></tr>
    <tr><td valign="top">
    <table  class="grid1small" id="dataTable" style="width:500px">
    <%
    ArrayList keyrowLst=new ArrayList();
    HashMap keyrowDsc=new HashMap();
    keyrowLst.add("ACT");keyrowLst.add("CUR");keyrowLst.add("WMKSD");keyrowLst.add("MKSD");
    keyrowDsc.put("ACT","Actual");keyrowDsc.put("CUR","Current");keyrowDsc.put("WMKSD","LIVE");keyrowDsc.put("MKSD","SOLD");
    for(int mo=0;mo<memoList.size();mo++){
    String memo=util.nvl((String)memoList.get(mo));
    String target = "SC_"+memo;
    double actcts=0;
    double actFavg=0;
    %>
    <tr id="<%=target%>">
    <td>
    <table>
    <tr>
    <th align="center">Memo</th>
    <th align="center">Status</th>
    <th colspan="5" align="center">Total</th>
    <th align="center">Diff</th>
    <%for(int dt=0;dt<prpValDept.size();dt++){
    String dept=util.nvl((String)prpValDept.get(dt));//shiv
    if(!dept.equals("NA")){
    %>
    <th colspan="5" align="center"><%=util.nvl((String)prpValDept.get(dt))%></th>
    <%}}%>
    </tr>
    <tr>
    <td><a href="orclReportAction.do?method=memohkPop&memo=<%=memo%>&ketan=<%=ketan%>" id="LNK_<%=memo%>" onclick="loadASSFNL('<%=target%>','LNK_<%=memo%>')"  target="SC"><%=memo%></a></td>
    <td></td>
    <td align="center">Cts</td><td align="center">Avg</td><td align="center">F.Avg</td><td align="center">Mfg.Avg</td><td align="center" nowrap="nowrap">Sale Rte</td>
    <td></td>
    <%for(int dt=0;dt<prpValDept.size();dt++){
    String dept=util.nvl((String)prpValDept.get(dt));
    if(!dept.equals("NA")){%>
    <td align="center">Cts</td><td align="center">Avg</td><td align="center">F.Avg</td><td align="center">Mfg.Avg</td><td align="center" nowrap="nowrap">Sale Rte</td>
    <%}}%>
    </tr>
    <%for(int kr=0;kr<keyrowLst.size();kr++){
    String keyrow=util.nvl((String)keyrowLst.get(kr));
  
    if(keyrow.equals("ACT")){
    actcts =util.roundToDecimals(Math.round(Float.parseFloat(util.nvl((String)dataDtl.get(memo+"_TOTAL_"+keyrow+"_CTS")))),0);
    actFavg =util.roundToDecimals(Math.round(Float.parseFloat(util.nvl((String)dataDtl.get(memo+"_TOTAL_"+keyrow+"_FAAVG")))),0);
    }
    if(!keyrow.equals("CUR")){
    %>
    <tr>
    <td></td>
    <td align="center" nowrap="nowrap"><%=util.nvl((String)keyrowDsc.get(keyrow))%></td>
    <td align="right"><%=util.nvl((String)dataDtl.get(memo+"_TOTAL_"+keyrow+"_CTS"))%></td>
    <td align="right"><%=util.nvl((String)dataDtl.get(memo+"_TOTAL_"+keyrow+"_AVG"))%></td>
    <td align="right"><%=util.nvl((String)dataDtl.get(memo+"_TOTAL_"+keyrow+"_FAAVG"))%></td>
    <td align="right"><%=util.nvl((String)dataDtl.get(memo+"_TOTAL_"+keyrow+"_MFGAVG"))%></td>
    <td align="right"><%=util.nvl((String)dataDtl.get(memo+"_TOTAL_"+keyrow+"_SLRTE"))%></td>
    <td></td>
    <%    
    for(int dt=0;dt<prpValDept.size();dt++){
    String dept=util.nvl((String)prpValDept.get(dt));
    if(!dept.equals("NA")){%>
    <td align="right"><%=util.nvl((String)dataDtl.get(memo+"_"+dept+"_"+keyrow+"_CTS"))%></td>
    <td align="right"><%=util.nvl((String)dataDtl.get(memo+"_"+dept+"_"+keyrow+"_AVG"))%></td>
    <td align="right"><%=util.nvl((String)dataDtl.get(memo+"_"+dept+"_"+keyrow+"_FAAVG"))%></td>
    <td align="right"><%=util.nvl((String)dataDtl.get(memo+"_"+dept+"_"+keyrow+"_MFGAVG"))%></td>
    <td align="right"><%=util.nvl((String)dataDtl.get(memo+"_"+dept+"_"+keyrow+"_SLRTE"))%></td>
    <%}}%>
    </tr>
    <%}else{
    double totalavg = 0,totalfaavg=0,totalmfgavg=0,totalslrte=0,totalctc=0,diff=0;
    totalctc=Float.parseFloat(util.nvl((String)dataDtl.get(memo+"_TOTAL_WMKSD_CTS"),"0"))+Float.parseFloat(util.nvl((String)dataDtl.get(memo+"_TOTAL_MKSD_CTS"),"0"));
    totalavg=util.roundToDecimals(Math.round((Float.parseFloat(util.nvl((String)dataDtl.get(memo+"_TOTAL_WMKSD_AVG"),"0"))*Float.parseFloat(util.nvl((String)dataDtl.get(memo+"_TOTAL_WMKSD_CTS"),"0"))+(Float.parseFloat(util.nvl((String)dataDtl.get(memo+"_TOTAL_MKSD_AVG"),"0"))*Float.parseFloat(util.nvl((String)dataDtl.get(memo+"_TOTAL_MKSD_CTS"),"0"))))/totalctc),0);
    totalfaavg=util.roundToDecimals(Math.round((Float.parseFloat(util.nvl((String)dataDtl.get(memo+"_TOTAL_WMKSD_FAAVG"),"0"))*Float.parseFloat(util.nvl((String)dataDtl.get(memo+"_TOTAL_WMKSD_CTS"),"0"))+(Float.parseFloat(util.nvl((String)dataDtl.get(memo+"_TOTAL_MKSD_FAAVG"),"0"))*Float.parseFloat(util.nvl((String)dataDtl.get(memo+"_TOTAL_MKSD_CTS"),"0"))))/totalctc),0);
    totalmfgavg=util.roundToDecimals(Math.round((Float.parseFloat(util.nvl((String)dataDtl.get(memo+"_TOTAL_WMKSD_MFGAVG"),"0"))*Float.parseFloat(util.nvl((String)dataDtl.get(memo+"_TOTAL_WMKSD_CTS"),"0"))+(Float.parseFloat(util.nvl((String)dataDtl.get(memo+"_TOTAL_MKSD_MFGAVG"),"0"))*Float.parseFloat(util.nvl((String)dataDtl.get(memo+"_TOTAL_MKSD_CTS"),"0"))))/totalctc),0);
    totalslrte=util.roundToDecimals(Math.round((Float.parseFloat(util.nvl((String)dataDtl.get(memo+"_TOTAL_WMKSD_SLRTE"),"0"))*Float.parseFloat(util.nvl((String)dataDtl.get(memo+"_TOTAL_WMKSD_CTS"),"0"))+(Float.parseFloat(util.nvl((String)dataDtl.get(memo+"_TOTAL_MKSD_SLRTE"),"0"))*Float.parseFloat(util.nvl((String)dataDtl.get(memo+"_TOTAL_MKSD_CTS"),"0"))))/totalctc),0);
    diff =util.roundToDecimals((double)(((totalslrte)/(totalfaavg))*100)-100,2);
    System.out.println(totalctc);
    System.out.println(totalslrte);
    System.out.println(actcts);
    System.out.println(actFavg);
    System.out.println((int)(((totalctc * totalslrte)/(actcts * actFavg ))*100)-100);
    %>
    <tr>
    <td></td>
    <td align="center" nowrap="nowrap"><%=util.nvl((String)keyrowDsc.get(keyrow))%></td>
    <td align="right"><%=util.Round(totalctc,3)%></td>
    <td align="right"><%=totalavg%></td>
    <td align="right"><%=totalfaavg%></td>
    <td align="right"><%=totalmfgavg%></td>
    <td align="right"><%=totalslrte%></td>
    <td align="right"><%=diff%>%</td>
    <%for(int dt=0;dt<prpValDept.size();dt++){//shiv
    String dept=util.nvl((String)prpValDept.get(dt));
    if(!dept.equals("NA")){
    totalavg = 0;totalfaavg=0;totalmfgavg=0;totalslrte=0;totalctc=0;
    totalctc=Float.parseFloat(util.nvl((String)dataDtl.get(memo+"_"+dept+"_WMKSD_CTS"),"0"))+Float.parseFloat(util.nvl((String)dataDtl.get(memo+"_"+dept+"_MKSD_CTS"),"0"));
    totalavg=util.roundToDecimals(Math.round((Float.parseFloat(util.nvl((String)dataDtl.get(memo+"_"+dept+"_WMKSD_AVG"),"0"))*Float.parseFloat(util.nvl((String)dataDtl.get(memo+"_"+dept+"_WMKSD_CTS"),"0"))+(Float.parseFloat(util.nvl((String)dataDtl.get(memo+"_"+dept+"_MKSD_AVG"),"0"))*Float.parseFloat(util.nvl((String)dataDtl.get(memo+"_"+dept+"_MKSD_CTS"),"0"))))/totalctc),0);
    totalfaavg=util.roundToDecimals(Math.round((Float.parseFloat(util.nvl((String)dataDtl.get(memo+"_"+dept+"_WMKSD_FAAVG"),"0"))*Float.parseFloat(util.nvl((String)dataDtl.get(memo+"_"+dept+"_WMKSD_CTS"),"0"))+(Float.parseFloat(util.nvl((String)dataDtl.get(memo+"_"+dept+"_MKSD_FAAVG"),"0"))*Float.parseFloat(util.nvl((String)dataDtl.get(memo+"_"+dept+"_MKSD_CTS"),"0"))))/totalctc),0);
    totalmfgavg=util.roundToDecimals(Math.round((Float.parseFloat(util.nvl((String)dataDtl.get(memo+"_"+dept+"_WMKSD_MFGAVG"),"0"))*Float.parseFloat(util.nvl((String)dataDtl.get(memo+"_"+dept+"_WMKSD_CTS"),"0"))+(Float.parseFloat(util.nvl((String)dataDtl.get(memo+"_"+dept+"_MKSD_MFGAVG"),"0"))*Float.parseFloat(util.nvl((String)dataDtl.get(memo+"_"+dept+"_MKSD_CTS"),"0"))))/totalctc),0);
    totalslrte=util.roundToDecimals(Math.round((Float.parseFloat(util.nvl((String)dataDtl.get(memo+"_"+dept+"_WMKSD_SLRTE"),"0"))*Float.parseFloat(util.nvl((String)dataDtl.get(memo+"_"+dept+"_WMKSD_CTS"),"0"))+(Float.parseFloat(util.nvl((String)dataDtl.get(memo+"_"+dept+"_MKSD_SLRTE"),"0"))*Float.parseFloat(util.nvl((String)dataDtl.get(memo+"_"+dept+"_MKSD_CTS"),"0"))))/totalctc),0);
    %>
    <td align="right"><%=util.Round(totalctc,3)%></td>
    <td align="right"><%=totalavg%></td>
    <td align="right"><%=totalfaavg%></td>
    <td align="right"><%=totalmfgavg%></td>
    <td align="right"><%=totalslrte%></td>
    <%}}%>
    </tr>    
    <%}}%>
    
    </table>
    </td>
    </tr>
    <%}%>
    </table>
    </td></tr></table>
    <%}else{%>
    <%}
    }else{
    ArrayList pktList= (ArrayList)request.getAttribute("pktList");
    if(pktList!=null && pktList.size()>0){
    if(!ketan.equals("")){
    %>
    <table><tr><td valign="top">
     Ketan Excel <img src="../images/ico_file_excel.png" onclick="goTo('<%=info.getReqUrl()%>/report/orclReportAction.do?method=ketanXL','','')" border="0"/> 
    </td></tr></table>
    <%}%>
    <table><tr><td valign="top">
    <table  class="grid1" id="dataTable" style="width:500px">
    <tr><th>Memo</th><th>Qty</th><th>Cts</th><th>F.Avg</th></tr>
    <%for(int j=0;j<pktList.size();j++){
    data=new HashMap();
    data=(HashMap)pktList.get(j);
    if(data!=null && data.size()>0){
    String memo=(String)data.get("DSC");
    String target = "SC_"+memo;
    %>
    <tr id="<%=target%>"><td align="center"><a href="orclReportAction.do?method=memohkPop&memo=<%=memo%>&ketan=<%=ketan%>" id="LNK_<%=memo%>" onclick="loadASSFNL('<%=target%>','LNK_<%=memo%>')"  target="SC"><%=memo%></a></td>
    <td align="right"><%=data.get("QTY")%></td>
    <td align="right"><%=data.get("CTS")%></td>
    <td align="right"><%=data.get("FAAVG")%></td>
    </tr>
    <%}}%>
    </table>
    </td></tr></table>
    <%}}}%>
    </td></tr></table>
    </td></tr>

    </table>
    </td></tr>

    </table>
  <%
  if(normal.equals("Y")){
  HashMap piedataDtl = (HashMap)session.getAttribute("PIEdataDtl");
    ArrayList grpLst=new ArrayList();
ArrayList piedeptLst=new ArrayList();
String memo=(String)request.getParameter("memo");
  String url="ajaxRptAction.do?method=creatememoPieChart";
  String style="width: 33%; height: 362px; float:left;";
  if(piedataDtl!=null && piedataDtl.size()>0){
  ArrayList prpLst=(ArrayList)piedataDtl.get("PRO");
  for(int i=0;i<prpLst.size();i++){
  String prp=(String)prpLst.get(i);
  piedeptLst=new ArrayList();
  piedeptLst=(ArrayList)piedataDtl.get(prp+"_DEPT");
  if(piedeptLst!=null){
  %>
  <div>
  <%
  for(int j=0;j<piedeptLst.size();j++){
  String dept=(String)piedeptLst.get(j);
  String styleId=prp+"_"+dept+"_HIDD";
  String styleval=prp+"_"+dept;
  %>
  <div>
  <input type="hidden" id="<%=styleId%>" value="<%=styleval%>">
  <div id="<%=styleval%>" style="<%=style%>">
  </div>
  </div>
  
  <%}}%>
  </div>
  <%}}%>

<script type="text/javascript">
 <!--
$(window).bind("load", function() {
callcreatememoPieChart('<%=url%>',' Chart','50','362');
});
 -->
</script>  
<%}%>
     <%@include file="../calendar.jsp"%>
  </body>
</html>