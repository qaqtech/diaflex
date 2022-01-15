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
  <img src="../images/line.jpg" border="0" /></td><td valign="middle"> <span class="pgHed">Memo Comparison Report</span> </td>
</tr></table>
  </td>
  </tr>
  <tr><td valign="top" class="tdLayout">
  <table><tr><td>
  <table><tr><td valign="top">
    <html:form action="/report/orclReportAction.do?method=memohkcomp"  method="POST">
        <table class="grid1">
      <tr>
      <td>Memo Id1</td><td><html:text property="value(memoIdn1)" styleId="memoid1"/></td>
      <td>Memo Id2</td><td><html:text property="value(memoIdn2)" styleId="memoid2"/></td>
      </tr>
        </table>
        <p><html:submit property="submit" value="View" styleClass="submit" /></p>
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
    ArrayList memoidLst= (ArrayList)session.getAttribute("memoidLst");
    HashMap data=new HashMap();
    HashMap datasum=new HashMap();
    if(view.equals("Y")){
    if(dataDtl!=null && dataDtl.size()>0){
    ArrayList conatinsdata= new ArrayList();
    String shape="",sz="",qty="",cts="",avg="",mavg="",msumavg="",colper="",colpersum="",faavg="",qtysum="",ctssum="",faavgsum="",avgsum="",grandcts="",compdte="",fac="",memo="",mlotidn="";
        HashMap prp = info.getPrp();
        HashMap mprp = info.getMprp();
        ArrayList prpSrtDept = null;
        ArrayList prpValDept = null;
        ArrayList prpSrtShape = null;
        ArrayList prpValShape = null;
        ArrayList prpSrtMixSize = null;
        ArrayList prpValMixSize = null;
        prpSrtDept =new ArrayList();
        prpSrtDept = (ArrayList)prp.get("DEPT"+"S"); 
        prpValDept = (ArrayList)prp.get("DEPT"+"V");
        prpSrtShape =new ArrayList();
        prpSrtShape = (ArrayList)prp.get("SHAPE"+"S");
        prpValShape = (ArrayList)prp.get("SHAPE"+"V");
        prpSrtMixSize = (ArrayList)prp.get("MIX_SIZE"+"S");
        prpValMixSize = (ArrayList)prp.get("MIX_SIZE"+"V");
        ArrayList shLst1=new ArrayList();
        ArrayList shLst2=new ArrayList();
        ArrayList szLst1=new ArrayList();  
        ArrayList szLst2=new ArrayList();  
        String roundSrt=(String)prpSrtShape.get(prpValShape.indexOf("ROUND"));
    %>
    <table>
    <tr>
    <%
    ArrayList deptLst= (ArrayList)dataDtl.get("DEPT");
    for(int p=0;p<memoidLst.size();p++){
    String memoIdn=(String)memoidLst.get(p);
    data=new HashMap();
    data=(HashMap)dataDtl.get(memoIdn+"_GRANDTTL");
    grandcts=util.nvl((String)data.get("CTS"));
    compdte=util.nvl((String)data.get("CMPDTE"));
    fac=util.nvl((String)data.get("FAC"));
    avg=util.nvl((String)data.get("AVG"));
    memo=util.nvl((String)data.get("MEMO"));
    mlotidn=util.nvl((String)data.get("IDN"));
    %>
    <td valign="top">
    <table class="grid1" style="width:500px">
    <tr><td  colspan="11" align="left">Memo -<%=memo%>&nbsp; Factory -<%=fac%>&nbsp; Complete Date -<%=compdte%></td></tr>
    <%
    for(int l=0;l<prpSrtDept.size();l++){
    String srtDept=(String)prpSrtDept.get(l);
    if(deptLst.contains(srtDept)){
    String dept=(String)prpValDept.get(prpSrtDept.indexOf(srtDept));  
    %>
    <tr><td  colspan="11"><b><%=dept%></b></td></tr>
    <tr>
    <th>Size</th><th>Pcs</th><th>Cts</th><th>Avg</th><th>F.Avg</th><th>Mfg.Avg</th><th>ColWt%</th><th>SalVal%</th><th>Wt%</th><th>Crt%</th><th>Tot%</th>
    </tr>
    
    <%
    shLst1=new ArrayList();
    shLst2=new ArrayList();
    szLst1=new ArrayList();  
    szLst2=new ArrayList();
    shLst1=((ArrayList)dataDtl.get(memoidLst.get(0)+"_"+srtDept+"_SH") == null)?new ArrayList():(ArrayList)dataDtl.get(memoidLst.get(0)+"_"+srtDept+"_SH");
    szLst1=((ArrayList)dataDtl.get(memoidLst.get(0)+"_"+srtDept+"_SZ") == null)?new ArrayList():(ArrayList)dataDtl.get(memoidLst.get(0)+"_"+srtDept+"_SZ");
    if(memoidLst.size()>1){
    shLst2=((ArrayList)dataDtl.get(memoidLst.get(1)+"_"+srtDept+"_SH") == null)?new ArrayList():(ArrayList)dataDtl.get(memoidLst.get(1)+"_"+srtDept+"_SH");
    szLst2=((ArrayList)dataDtl.get(memoidLst.get(1)+"_"+srtDept+"_SZ") == null)?new ArrayList():(ArrayList)dataDtl.get(memoidLst.get(1)+"_"+srtDept+"_SZ");
    }
    for(int i=0;i<prpSrtShape.size();i++){
    String srtShape=(String)prpSrtShape.get(i);
    if(shLst1.contains(srtShape) || shLst2.contains(srtShape)){
    String key=memoIdn+"_"+srtDept+"_"+srtShape;
    shape=(String)prpValShape.get(prpSrtShape.indexOf(srtShape));
    qtysum=util.nvl2((String)datattl.get(key+"_QTY"),"0");
    ctssum=util.nvl2((String)datattl.get(key+"_CTS"),"0");
    avgsum=util.nvl2((String)datattl.get(key+"_AVG"),"0");
    faavgsum=util.nvl2((String)datattl.get(key+"_FAAVG"),"0");
    colpersum=util.nvl2((String)datattl.get(key+"_COLPCT"),"0");
    msumavg=util.nvl2((String)datattl.get(key+"_MFGAVG"),"0");
    String subkey=key.substring(key.lastIndexOf("_")+1,key.length());
    if(subkey.equals(roundSrt)){
    %>
    <tr><td  colspan="11"><b>Shape :</b><%=shape%></td></tr>
    <%
    for(int j=0;j<prpValMixSize.size();j++){
    String szval=(String)prpValMixSize.get(j);
    if(szLst1.contains(szval) || szLst2.contains(szval)){
    shape=util.nvl((String)dataDtl.get(key+"_"+szval+"_SH"));
    qty=util.nvl2((String)dataDtl.get(key+"_"+szval+"_QTY"),"0");
    cts=util.nvl2((String)dataDtl.get(key+"_"+szval+"_CTS"),"0");
    avg=util.nvl2((String)dataDtl.get(key+"_"+szval+"_AVG"),"0");
    faavg=util.nvl2((String)dataDtl.get(key+"_"+szval+"_FAAVG"),"0");
    mavg=util.nvl2((String)dataDtl.get(key+"_"+szval+"_MFGAVG"),"0");
    colper=util.nvl2((String)dataDtl.get(key+"_"+szval+"_COLPCT"),"0");
    Double crt=(Double.parseDouble(cts)/Double.parseDouble(ctssum))*100;
    Double tot=(Double.parseDouble(cts)/Double.parseDouble(grandcts))*100;
    %>
    <tr><td>
    <%=szval%></td><td align="right"><%=qty%></td><td align="right"><%=cts%></td><td align="right"><%=avg%></td><td align="right"><%=faavg%></td><td align="right"><%=mavg%></td><td align="right"><%=colper%></td><td></td><td></td><td align="right"><%=util.Round(crt, 2)%>%</td><td align="right"><%=util.Round(tot, 2)%>%</td>
    </tr>
    <%
    }}
    Double crt=(Double.parseDouble(ctssum)/Double.parseDouble(ctssum))*100;
    Double tot=(Double.parseDouble(ctssum)/Double.parseDouble(grandcts))*100;
    %>
    <tr>
    <td><b>Total</b></td><td align="right"><%=qtysum%></td><td align="right"><%=ctssum%></td><td align="right"><%=avgsum%></td><td align="right"><%=faavgsum%></td><td align="right"><%=msumavg%></td><td align="right"><%=colpersum%></td><td></td><td></td><td align="right"><%=util.Round(crt, 2)%>%</td><td align="right"><%=util.Round(tot, 2)%>%</td>
    </tr>
    <%}else{
    Double crt=(Double.parseDouble(ctssum)/Double.parseDouble(ctssum))*100;
    Double tot=(Double.parseDouble(ctssum)/Double.parseDouble(grandcts))*100;
    %>
    <tr>
    <td><%=shape%></td><td align="right"><%=qtysum%></td><td align="right"><%=ctssum%></td><td align="right"><%=avgsum%></td><td align="right"><%=faavgsum%></td><td align="right"><%=msumavg%></td><td align="right"><%=colpersum%></td><td></td><td></td><td align="right"><%=util.Round(crt, 2)%>%</td><td align="right"><%=util.Round(tot, 2)%>%</td>
    </tr>
    <%}%>
    <%}}
    data=new HashMap();
    data = ((HashMap)dataDtl.get(memoIdn+"_"+srtDept) == null)?new HashMap():(HashMap)dataDtl.get(memoIdn+"_"+srtDept);
    qty=util.nvl2((String)data.get("QTY"),"0");
    cts=util.nvl2((String)data.get("CTS"),"0");
    avg=util.nvl2((String)data.get("AVG"),"0");
    faavg=util.nvl2((String)data.get("FAAVG"),"0");
    colper=util.nvl2((String)data.get("COLPCT"),"0");
    mavg=util.nvl((String)data.get("MFGAVG"));
    Double tot=(Double.parseDouble(cts)/Double.parseDouble(grandcts))*100;
    %>
    <tr>
   <td nowrap="nowrap"><b>Dept Total</b></td><td align="right"><label class="blueLabel"><%=qty%></label></td><td align="right"><label class="blueLabel"><%=cts%></label></td><td align="right"><label class="blueLabel"><%=avg%></label></td><td align="right"><label class="blueLabel"><%=faavg%></label></td>
      <td align="right">
   <label class="blueLabel"><%=mavg%></label></td>
   <td align="right"><label class="blueLabel"><%=colper%></label></td><td></td><td></td>
   <td align="right"></td><td align="right"><label class="blueLabel"><%=util.Round(tot, 2)%>%</label></td>
   </tr> 
    <%
    }
    }
    data=new HashMap();
    data = ((HashMap)dataDtl.get(memoIdn+"_"+"GRANDTTL") == null)?new HashMap():(HashMap)dataDtl.get(memoIdn+"_"+"GRANDTTL");
    qty=util.nvl2((String)data.get("QTY"),"0");
    cts=util.nvl2((String)data.get("CTS"),"0");
    avg=util.nvl2((String)data.get("AVG"),"0");
    faavg=util.nvl2((String)data.get("FAAVG"),"0");
    colper=util.nvl2((String)data.get("COLPCT"),"0");
    mavg=util.nvl((String)data.get("MFGAVG"));
    %>
   <tr>
   <th>Grand Total</th><td align="right"><label class="redLabel"><%=qty%></label></td><td align="right"><label class="redLabel"><%=cts%></label></td>
   <td align="right"><label class="redLabel"><%=avg%></label></td>
   <td align="right"><label class="redLabel"><%=faavg%></label></td>
      <td align="right">
   <label class="blueLabel"><%=mavg%></label></td>
   <td align="right"><label class="redLabel"><%=colper%></label></td><td></td><td></td>
   <td align="right"></td><td align="right"><label class="redLabel">100.0%</label></td>
   </tr>    
    </table>
    </td>
    <%}%>
    </tr></table>
    <%}}%>
    </td></tr></table>
    </td></tr>

    </table>
    </td></tr>
       <tr>
    <td><jsp:include page="/footer.jsp" /> </td>
    </tr>
    </table>
     <%@include file="../calendar.jsp"%>
  </body>
</html>