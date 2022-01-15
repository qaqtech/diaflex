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
  </head>
<%
        String logId=String.valueOf(info.getLogId());
        String onfocus="cook('"+logId+"')";
        %>
 <body onfocus="<%=onfocus%>" onload="MM_preloadImages('images/faunalogo.png')" topmargin="0" leftmargin="0" rightmargin="0" bottommargin="0">
<table width="100%"  border="0" align="center" cellpadding="0" cellspacing="0">
  <tr><td valign="top" class="tdLayout">
  <table><tr><td>
    <table width="100%">
    <tr>
    <td valign="top">
    <%
    String view= util.nvl((String)request.getAttribute("view"));
    String ketan= util.nvl((String)request.getAttribute("ketan")); 
    HashMap dataDtl= (HashMap)session.getAttribute("dataDtl");
    HashMap datattl= (HashMap)session.getAttribute("datattl");
    ArrayList keytable= (ArrayList)session.getAttribute("keytable");
    ArrayList deptLst= (ArrayList)session.getAttribute("deptLst");
    if(view.equals("Y")){
    if(dataDtl!=null && dataDtl.size()>0){
    ArrayList conatinsdata= new ArrayList();
    HashMap data=new HashMap();
    HashMap datasum=new HashMap();
    String shape="",sz="",qty="",display="display:none",mavg="",msumavg="",colper="",colpersum="",cts="",avg="",faavg="",qtysum="",ctssum="",avgsum="",faavgsum="",grandcts="",compdte="",fac="",memo="",mlotidn="";
        HashMap prp = info.getPrp();
        HashMap mprp = info.getMprp();
        ArrayList prpSrtDept = null;
        ArrayList prpValDept = null;
        ArrayList prpSrtShape = null;
        ArrayList prpValShape = null;
        prpSrtDept =new ArrayList();
        prpSrtDept = (ArrayList)prp.get("DEPT"+"S"); 
        prpValDept = (ArrayList)prp.get("DEPT"+"V");
        prpSrtShape =new ArrayList();
        prpSrtShape = (ArrayList)prp.get("SHAPE"+"S");
        prpValShape = (ArrayList)prp.get("SHAPE"+"V");
        String roundSrt=(String)prpSrtShape.get(prpValShape.indexOf("ROUND"));
    if(!ketan.equals(""))
    display="";
    data=(HashMap)dataDtl.get("GRANDTTL");
    grandcts=util.nvl((String)data.get("CTS"));
    compdte=util.nvl((String)data.get("CMPDTE"));
    fac=util.nvl((String)data.get("FAC"));
    memo=util.nvl((String)data.get("MEMO"));
    avg=util.nvl((String)data.get("AVG"));
    mlotidn=util.nvl((String)data.get("IDN"));
    %>
    <table>
    <tr><td>
    <span style="padding-left:10px;"><a onclick="goTo('orclReportAction.do?method=createPDFMEMOHK','','')"><img src="../images/PDFIconSmall.jpg" border="0"/> </a></span>
     <%
  ArrayList rolenmLst=(ArrayList)info.getRoleLst();
  String usrFlg=util.nvl((String)info.getUsrFlg());
  if(usrFlg.equals("SYS") || rolenmLst.contains("ADMIN")){
  %>
  &nbsp;<img src="../images/edit.jpg" title="Edit Properties Module" onclick="newWindow('<%=info.getReqUrl()%>/dynamicProperty.do?method=load&mdl=MEMO_LOTPKT_VW&sname=MEMOLOT_HKREPORT&par=A')" border="0" width="15px" height="15px"/>
  <%}%>
  &nbsp;&nbsp;&nbsp;&nbsp;<a href="orclReportAction.do?method=memohkPKTDTL" target="_blank">Pkt Dtl</a>
    </td></tr>
    <tr><td valign="top">
    <table class="grid1" style="width:500px">
    <tr><td  colspan="12" align="left">Memo -<%=memo%>&nbsp; Factory -<%=fac%>&nbsp; Complete Date -<%=compdte%></td></tr>
    <%for(int l=0;l<deptLst.size();l++){
    String srtDept=(String)deptLst.get(l);
    String dept=util.nvl((String)prpValDept.get(prpSrtDept.indexOf(srtDept)));  
    String ttldisplay="N";
    %>
    <tr><td  colspan="12"><b><%=dept%></b></td></tr>
    <tr>
    <th>Size</th><th>Pcs</th><th>Cts</th><th>Col%</th><th>Avg</th><th>F.Avg</th><th>Mfg.Avg</th><th>Mgmt Avg</th><th>50% Avg</th><th style="<%=display%>">ketan</th><th>Crt%</th><th>Tot%</th>
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
    faavgsum=util.nvl((String)datasum.get("FAAVG"));
    colpersum=util.nvl((String)datasum.get("COLPCT"));
    msumavg=util.nvl((String)datasum.get("MFGAVG"));
    String subkey=key.substring(key.indexOf("_")+1,key.length());
    if(subkey.equals(roundSrt)){
    %>
    <tr><td  colspan="12"><b>Shape :</b><%=shape%></td></tr>
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
    faavg=util.nvl((String)data.get("FAAVG"));
    colper=util.nvl((String)data.get("COLPCT"));
    mavg=util.nvl((String)data.get("MFGAVG"));
    Double crt=(Double.parseDouble(cts)/Double.parseDouble(ctssum))*100;
    Double tot=(Double.parseDouble(cts)/Double.parseDouble(grandcts))*100;
    %>
    <tr>
    <td>
     <%if(dept.indexOf("GIA")!=-1){%>GR.<%}%>
    <%=sz%></td><td align="right"><%=qty%></td><td align="right"><%=cts%></td><td align="right"><%=colper%></td><td align="right"><%=avg%></td><td align="right"><%=faavg%></td><td align="right"><%=mavg%></td><td></td><td></td><td style="<%=display%>"></td><td align="right"><%=util.Round(crt, 2)%>%</td><td align="right"><%=util.Round(tot, 2)%>%</td>
    </tr>
    <%}
    }
    Double crt=(Double.parseDouble(ctssum)/Double.parseDouble(ctssum))*100;
    Double tot=(Double.parseDouble(ctssum)/Double.parseDouble(grandcts))*100;
    %>
    <tr>
    <td><b>Total</b></td><td align="right"><%=qtysum%></td><td align="right"><%=ctssum%></td><td align="right"><%=colpersum%></td><td align="right"><%=avgsum%></td><td align="right"><%=faavgsum%></td><td align="right"><%=msumavg%></td><td></td><td></td><td style="<%=display%>"></td><td align="right"><%=util.Round(crt, 2)%>%</td><td align="right"><%=util.Round(tot, 2)%>%</td>
    </tr>
    <%}else{
    Double crt=(Double.parseDouble(ctssum)/Double.parseDouble(ctssum))*100;
    Double tot=(Double.parseDouble(ctssum)/Double.parseDouble(grandcts))*100;
    ttldisplay="Y";
    %>
    <tr>
    <td><%=shape%></td><td align="right"><%=qtysum%></td><td align="right"><%=ctssum%></td><td align="right"><%=colpersum%></td><td align="right"><%=avgsum%></td><td align="right"><%=faavgsum%></td><td align="right"><%=msumavg%></td><td></td><td></td><td style="<%=display%>"></td><td align="right"><%=util.Round(crt, 2)%>%</td><td align="right"><%=util.Round(tot, 2)%>%</td>
    </tr>
    <%}%>
    <%}}
    if(ttldisplay.equals("Y")){
    data=new HashMap();
    data=(HashMap)dataDtl.get(srtDept+"_TTL");
    if(data!=null){
    qty=util.nvl((String)data.get("QTY"));
    cts=util.nvl((String)data.get("CTS"));
    avg=util.nvl((String)data.get("AVG"));
    faavg=util.nvl((String)data.get("FAAVG"));
    colper=util.nvl((String)data.get("COLPCT"));
    mavg=util.nvl((String)data.get("MFGAVG"));
    Double toto=(Double.parseDouble(cts)/Double.parseDouble(grandcts))*100;
    %>
   <tr>
   <td nowrap="nowrap"><b>Fancy Total</b></td><td align="right"><label class="blueLabel"><%=qty%></label></td><td align="right"><label class="blueLabel"><%=cts%></label></td>
   <td align="right"><label class="blueLabel"><%=colper%></label></td>
   <td align="right"><label class="blueLabel"><%=avg%></label></td>
   <td align="right">
   <label class="blueLabel"><%=faavg%></label></td>
   <td align="right">
   <label class="blueLabel"><%=mavg%></label></td>
   <td align="center"></td>
   <td align="center"></td>
   <td align="center" style="<%=display%>"></td>
   <td align="right"></td><td align="right"><label class="blueLabel"><%=util.Round(toto, 2)%>%</label></td>
   </tr> 
    <%
    }}
    data=new HashMap();
    data=(HashMap)dataDtl.get(srtDept);
    qty=util.nvl((String)data.get("QTY"));
    cts=util.nvl((String)data.get("CTS"));
    avg=util.nvl((String)data.get("AVG"));
    faavg=util.nvl((String)data.get("FAAVG"));
    colper=util.nvl((String)data.get("COLPCT"));
    mavg=util.nvl((String)data.get("MFGAVG"));
    Double tot=(Double.parseDouble(cts)/Double.parseDouble(grandcts))*100;
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
   <td nowrap="nowrap"><b>Dept Total</b></td><td align="right"><label class="blueLabel"><%=qty%></label></td><td align="right"><label class="blueLabel"><%=cts%></label></td><td align="right"><label class="blueLabel"><%=colper%></label></td><td align="right"><label class="blueLabel"><%=avg%></label></td><td align="right"><label class="blueLabel"><%=faavg%></label></td>
   <td align="right">
   <label class="blueLabel"><%=mavg%></label></td>
   <td align="center"><html:text property="<%=accDis%>" size="6" styleId="<%=accAvgID%>" name="orclReportForm" onchange="<%=onchange%>"/></td>
   <td align="center"><html:text property="<%=avgDis%>" size="6" styleId="<%=avgID%>" onchange="<%=avgonchange%>" name="orclReportForm"/></td>
   <td align="center" style="<%=display%>"><html:text property="<%=avgketan%>" size="6" styleId="<%=avgketanID%>" onchange="<%=avgketanchange%>" name="orclReportForm"/></td>
   <td align="right"></td><td align="right"><label class="blueLabel"><%=util.Round(tot, 2)%>%</label></td>
   </tr> 
    <%
    }
    data=new HashMap();
    data=(HashMap)dataDtl.get("GRANDTTL");
    qty=util.nvl((String)data.get("QTY"));
    cts=util.nvl((String)data.get("CTS"));
    avg=util.nvl((String)data.get("AVG"));
    faavg=util.nvl((String)data.get("FAAVG"));
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
    String avgketanchange="savemlotDept('"+mlotidn+"','','"+ketanID+"','ketan_av')";
    %>
   <tr>
   <th>Grand Total</th><td align="right"><label class="redLabel"><%=qty%></label></td><td align="right"><label class="redLabel"><%=cts%></label></td><td align="right"><label class="redLabel"><%=colper%></label></td><td align="right"><label class="redLabel"><%=avg%></label></td><td align="right"><label class="redLabel"><%=faavg%></label></td>
   <td align="right"><label class="redLabel"><%=mavg%></label></td>
   <td align="center"><html:text property="<%=accDis%>" size="6" styleId="<%=accAvgID%>" onchange="<%=onchange%>" name="orclReportForm"/></td>
   <td align="center"><html:text property="<%=avgDis%>" size="6" styleId="<%=avgID%>" onchange="<%=avgonchange%>" name="orclReportForm"/></td>
   <td align="center" style="<%=display%>"><html:text property="<%=ketanDis%>" size="6" styleId="<%=ketanID%>" onchange="<%=avgketanchange%>" name="orclReportForm"/></td>
   <td align="right"></td><td align="right"><label class="redLabel">100.0%</label></td>
   </tr>    
    </table>
    </td>

    <td valign="top">
    <table class="grid1" style="width:300px">
        <%for(int l=0;l<deptLst.size();l++){
    String srtDept=(String)deptLst.get(l);
    String dept=util.nvl((String)prpValDept.get(prpSrtDept.indexOf(srtDept)));  
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
    Double crt=(Double.parseDouble(cts)/Double.parseDouble(ctssum))*100;
    Double tot=(Double.parseDouble(cts)/Double.parseDouble(grandcts))*100;
    %>
    <tr>
    <td nowrap="nowrap">
        <%if(dept.indexOf("GIA")!=-1){%>GR.<%}%>
    <%=sz%></td><td align="right"><%=qty%></td><td align="right"><%=cts%></td><td align="right"><%=faavg%></td><td align="right"><%=util.Round(tot, 2)%>%</td>
    </tr>
    <%}
    }
    Double crt=(Double.parseDouble(ctssum)/Double.parseDouble(ctssum))*100;
    Double tot=(Double.parseDouble(ctssum)/Double.parseDouble(grandcts))*100;
    %>
    <tr>
    <td><b>Total</b></td><td align="right"><%=qtysum%></td><td align="right"><%=ctssum%></td><td align="right"><%=faavgsum%></td><td align="right"><%=util.Round(tot, 2)%>%</td>
    </tr>
    <%}%>
    <%}}}%>
    </table>
    </td>
    </tr></table>
    <%}}%>
    </td></tr></table>
    </td></tr>

    </table>
    </td></tr>
    </table>
     <%@include file="../calendar.jsp"%>
  </body>
</html>