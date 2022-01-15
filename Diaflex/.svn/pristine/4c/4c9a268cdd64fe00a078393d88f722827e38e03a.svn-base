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

  <title>Lot Report</title>
 
  </head>

        <%
        HashMap dbmsInfo = info.getDmbsInfoLst();
        HashMap prp = info.getPrp();
        HashMap mprp = info.getMprp();
        ArrayList prpList =(ArrayList)session.getAttribute("LOT_RPT_FLT");
        ArrayList prpListSummary =(ArrayList)session.getAttribute("LOT_RPT_FLTSUMM");
        String logId=String.valueOf(info.getLogId());
        String onfocus="cook('"+logId+"')";
        String cnt  = util.nvl((String)info.getDmbsInfoLst().get("CNT"));
                HashMap allPageDtl= (info.getPageDetails() == null)?new HashMap():(HashMap)info.getPageDetails();
                HashMap pageDtl=(HashMap)allPageDtl.get("LOT_REPORT");
                ArrayList pageList=new ArrayList();
                HashMap pageDtlMap=new HashMap();
                String fld_nme="",fld_ttl="",val_cond="",lov_qry="",dflt_val="",fld_typ="",form_nme="",flg1="",lotlprp="";
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
  <img src="../images/line.jpg" border="0" /></td><td valign="middle"> <span class="pgHed">Lot Report</span> </td>
</tr></table>
  </td>
  </tr>
  <% if(request.getAttribute("msg")!=null){%>
<tr><td valign="top" class="tdLayout">
<span class="redLabel"><%=request.getAttribute("msg")%></span>
</td></tr>
<%}%>
<tr><td valign="top" class="hedPg">
<html:form action="/report/orclRptAction.do?method=fetchlot"  method="POST">
<html:hidden property="value(MIX)" name="orclReportForm" />
  <table  class="grid1">
   <tr>
   <th colspan="2" align="center">Lot Search</th>
   </tr>
   <tr>
   <td>Lot </td>
   <td><html:text property="value(lot)" name="orclReportForm" styleId="lot"/></td>
   </tr>
   <tr>
   <td>
   By
   </td>
   <td>
<html:select name="orclReportForm" property="value(rowlprp)" styleId="rowlprp" >
      <%if(prpList!=null){
      for(int i=0;i<prpList.size();i++){
      String rowprp=(String)prpList.get(i);
      %>
      <html:option value="<%=(String)prpList.get(i)%>"> <%=(String)mprp.get(rowprp+"D")%></html:option>
      <%}}%>
      </html:select>
</td>
   </tr>
      <tr>
   <td>
   Summary By
   </td>
   <td>
<html:select name="orclReportForm" property="value(summarylprp)" styleId="summarylprp" >
      <%if(prpList!=null){
      for(int i=0;i<prpListSummary.size();i++){
      String summaryprp=(String)prpListSummary.get(i);
      %>
      <html:option value="<%=(String)prpListSummary.get(i)%>"> <%=(String)mprp.get(summaryprp+"D")%></html:option>
      <%}}%>
      </html:select>
</td>
   </tr>
   <tr>
   <td>Summary By(Division)</td>
   <td>
   <html:select name="orclReportForm" property="value(shapebyrightside)" styleId="shapebyrightside" >
   <html:option value="N">No</html:option>
   <html:option value="Y">Yes</html:option>
   </html:select>
   </td>
   </tr>
   <%       pageList=(ArrayList)pageDtl.get("ROUGH_CTS");
            if(pageList!=null && pageList.size() >0){
                for(int j=0;j<pageList.size();j++){
                pageDtlMap=(HashMap)pageList.get(j);
                dflt_val=(String)pageDtlMap.get("dflt_val");%>
   <tr>
   <td>Rough Crtwt</td>
   <td>
   <html:text property="value(rough_ctsfrom)" size="10" name="orclReportForm" styleId="rough_ctsfrom"/>
   <html:text property="value(rough_ctsto)" size="10" name="orclReportForm" styleId="rough_ctsto"/>
   </td>
   </tr>      
   <%}
   }%>
   <tr>
   <td align="center" colspan="2">
   <html:submit property="value(srch)" value="Fetch" styleClass="submit" />&nbsp;&nbsp;

    <%if(cnt.equalsIgnoreCase("KJ")){%>

   <html:submit property="value(commercial)" value="Commercial Format" styleClass="submit" />

  <%}%>

 
   </td>
   </tr>
   </table>
</html:form>
</td>
</tr>
    <tr>
    <td valign="top" class="hedPg">
    <%
    String commercial= util.nvl((String)request.getAttribute("commercial")); 
    if(commercial.equals("")){
    String normal= util.nvl((String)request.getAttribute("NORMAL")); 
    String view= util.nvl((String)request.getAttribute("view")); 
    HashMap data=new HashMap();
    HashMap datasum=new HashMap();
    if(view.equals("Y")){
        HashMap dataDtl= (HashMap)session.getAttribute("dataDtl");
        HashMap datattl= (HashMap)session.getAttribute("datattl");
        ArrayList keytable= (ArrayList)session.getAttribute("keytable");
        ArrayList sttLst= (ArrayList)session.getAttribute("sttLst");
        String shapebyrightside= util.nvl((String)request.getAttribute("shapebyrightside"),"N"); 
        HashMap dbinfo = info.getDmbsInfoLst();
        String sh = (String)dbinfo.get("SHAPE");
    if(dataDtl!=null && dataDtl.size()>0){
    ArrayList conatinsdata= new ArrayList();
    String shape="",summary="",sz="",qty="",faamt="",cts="",fancyrte="",faavg="",colper="",ttlonper="",colpersum="",mavg="",msumavg="",avg="",avgp1="",avgp2="",qtysum="",ctssum="",faamtsum="",faavgsum="",avgsum="",avgp1sum="",avgp2sum="",grandcts="",grandfaamt="",fac="",lot="",row="",summaryLprp="",mlotidn="";
        ArrayList prpSrtShape = null;
        ArrayList prpValShape = null;
        ArrayList prpSrtSummary = null;
        ArrayList prpValSummary = null;
        prpSrtShape =new ArrayList();
        prpSrtShape = (ArrayList)prp.get(sh+"S");
        prpValShape = (ArrayList)prp.get(sh+"V");
        int indexval=prpValShape.indexOf("ROUND");
        int colspan=5;
        if(indexval < 0)
        indexval=prpValShape.indexOf("RD");
        String roundSrt=(String)prpSrtShape.get(indexval);
                pageList=(ArrayList)pageDtl.get("TTLPER");
                if(pageList!=null && pageList.size() >0){
                for(int j=0;j<pageList.size();j++){
                pageDtlMap=(HashMap)pageList.get(j);
                ttlonper=(String)pageDtlMap.get("dflt_val");
                }
                }
                pageList=(ArrayList)pageDtl.get("FANCY_RTE");
                if(pageList!=null && pageList.size() >0){
                for(int j=0;j<pageList.size();j++){
                pageDtlMap=(HashMap)pageList.get(j);
                fancyrte=(String)pageDtlMap.get("dflt_val");
                }
                }
                pageList=(ArrayList)pageDtl.get("SHOWPRP");
                if(pageList!=null && pageList.size() >0){
                for(int j=0;j<pageList.size();j++){
                pageDtlMap=(HashMap)pageList.get(j);
                val_cond=util.nvl((String)pageDtlMap.get("val_cond"));
                if(!val_cond.equals("N"))
                colspan++;
                }
                }
    data=(HashMap)dataDtl.get("GRANDTTL");
    grandcts=util.nvl((String)data.get("CTS"));
    fac=util.nvl((String)data.get("FAC"));
    lot=util.nvl((String)data.get("LOT"));
    row=util.nvl((String)data.get("ROW"));
    summaryLprp=util.nvl((String)data.get("SUMMARY_LPRP"));
    avg=util.nvl((String)data.get("AVG"));
    grandfaamt=util.nvl((String)data.get(ttlonper),"0");
    mlotidn=util.nvl((String)data.get("IDN"));
    prpSrtSummary = (ArrayList)prp.get(summaryLprp+"S");
    prpValSummary = (ArrayList)prp.get(summaryLprp+"V");
    %>
    <table class="grid1" id="dataTable">
    <%String target = "SC_"+lot;%>
    <tr id="<%=target%>"><td>
     <%
  ArrayList rolenmLst=(ArrayList)info.getRoleLst();
  String usrFlg=util.nvl((String)info.getUsrFlg());
  if(usrFlg.equals("SYS") || rolenmLst.contains("ADMIN")){
  %>
  &nbsp;<img src="../images/edit.jpg" title="Edit Properties Module" onclick="newWindow('<%=info.getReqUrl()%>/dynamicProperty.do?method=load&mdl=LOT_RPT_VW&sname=LOT_RPT_VW&par=A')" border="0" width="15px" height="15px"/>
  <%}%>
  &nbsp;&nbsp;<a href="orclRptAction.do?method=LOTPKTDTL" target="_blank">Pkt Dtl</a>
    </td></tr>
    <tr><td valign="top">
    <table class="grid1" style="width:500px">
    <tr><td colspan=<%=colspan%> align="left">Lot -<%=lot%></td></tr>
    <%for(int l=0;l<sttLst.size();l++){
    String stt= (String)sttLst.get(l);
    String ttldisplay="N";
    %>
    <tr><td colspan=<%=colspan%>><a href="orclRptAction.do?method=LOTPKTDTL&dsp_stt=<%=stt%>" target="_blank"><%=stt%></a></td></tr>
    <tr>
    <th><%=(String)mprp.get(row+"D")%></th><th>Pcs</th><th>Cts</th>
    <%pageList=(ArrayList)pageDtl.get("SHOWPRP");
    if(pageList!=null && pageList.size() >0){
    for(int j=0;j<pageList.size();j++){
    pageDtlMap=(HashMap)pageList.get(j);
    fld_nme=(String)pageDtlMap.get("fld_nme");
    val_cond=util.nvl((String)pageDtlMap.get("val_cond"));
    if(!val_cond.equals("N")){%>
    <th><%=fld_nme%></th>
    <%}}}%>
    <th>Crt%</th><th>Tot%</th></th>
    </tr>
    
    <%for(int i=0;i<prpSrtShape.size();i++){
    String srtShape=(String)prpSrtShape.get(i);
    String key=stt+"_"+srtShape;
    conatinsdata= new ArrayList();
    datasum=new HashMap();
    conatinsdata=(ArrayList)dataDtl.get(key);
    if(conatinsdata!=null && conatinsdata.size()>0){
    datasum=(HashMap)datattl.get(key);
    shape=util.nvl((String)datasum.get("SH"));
    qtysum=util.nvl((String)datasum.get("QTY"));
    ctssum=util.nvl((String)datasum.get("CTS"));
    faamtsum=util.nvl((String)datasum.get(ttlonper),"0");
    String subkey=key.substring(key.lastIndexOf("_")+1,key.length());
    if(subkey.equals(roundSrt)){
    %>
    <tr><td colspan=<%=colspan%>><b>Shape :</b><%=shape%></td></tr>
    <%
    for(int j=0;j<conatinsdata.size();j++){
    data=new HashMap();
    data=(HashMap)conatinsdata.get(j);
    if(data!=null && data.size()>0){
    shape=util.nvl((String)data.get("SH"));
    sz=util.nvl((String)data.get("ROW"));
    qty=util.nvl((String)data.get("QTY"));
    cts=util.nvl((String)data.get("CTS"));
    faamt=util.nvl((String)data.get(ttlonper),"0");
    Double crt=(Double.parseDouble(cts)/Double.parseDouble(ctssum))*100;
    Double tot=0.0;
    if(!faamt.equals("0") && !grandfaamt.equals("0"))
    tot=(Double.parseDouble(faamt)/Double.parseDouble(grandfaamt))*100;
    %>
    <tr>
    <td>
    <%=sz%></td><td align="right"><%=qty%></td><td align="right"><%=cts%></td>
    <%pageList=(ArrayList)pageDtl.get("SHOWPRP");
    if(pageList!=null && pageList.size() >0){
    for(int sp=0;sp<pageList.size();sp++){
    pageDtlMap=(HashMap)pageList.get(sp);
    fld_ttl=(String)pageDtlMap.get("fld_ttl");
    val_cond=util.nvl((String)pageDtlMap.get("val_cond"));
    lov_qry=(String)pageDtlMap.get("lov_qry");
    if(!val_cond.equals("N")){%>
    <td align="right"><%=cts=util.nvl((String)data.get(fld_ttl))%></td>
    <%}}}%>
    <td align="right"><%=util.Round(crt, 2)%>%</td><td align="right"><%=util.Round(tot, 2)%>%</td>
    </tr>
    <%}
    }
    Double crt=(Double.parseDouble(ctssum)/Double.parseDouble(ctssum))*100;
    Double tot=0.0;
    if(!faamtsum.equals("0") && !grandfaamt.equals("0"))
    tot=(Double.parseDouble(faamtsum)/Double.parseDouble(grandfaamt))*100;
    %>
    <tr>
    <td nowrap="nowrap"><b>Round Total</b></td><td align="right"><%=qtysum%></td><td align="right"><%=ctssum%></td>
    <%pageList=(ArrayList)pageDtl.get("SHOWPRP");
    if(pageList!=null && pageList.size() >0){
    for(int sp=0;sp<pageList.size();sp++){
    pageDtlMap=(HashMap)pageList.get(sp);
    fld_ttl=(String)pageDtlMap.get("fld_ttl");
    val_cond=util.nvl((String)pageDtlMap.get("val_cond"));
    lov_qry=(String)pageDtlMap.get("lov_qry");
    if(!val_cond.equals("N")){%>
    <td align="right"><%=cts=util.nvl((String)datasum.get(fld_ttl))%></td>
    <%}}}%>
    <td align="right"><%=util.Round(crt, 2)%>%</td><td align="right"><%=util.Round(tot, 2)%>%</td>
    </tr>
    <%}else{
    Double crt=(Double.parseDouble(ctssum)/Double.parseDouble(ctssum))*100;
    Double tot=0.0;
    if(!faamtsum.equals("0") && !grandfaamt.equals("0"))
    tot=(Double.parseDouble(faamtsum)/Double.parseDouble(grandfaamt))*100;
    ttldisplay="Y";
    %>
    <tr>
    <td><%=shape%></td><td align="right"><%=qtysum%></td><td align="right"><%=ctssum%></td>
    <%pageList=(ArrayList)pageDtl.get("SHOWPRP");
    if(pageList!=null && pageList.size() >0){
    for(int sp=0;sp<pageList.size();sp++){
    pageDtlMap=(HashMap)pageList.get(sp);
    fld_ttl=(String)pageDtlMap.get("fld_ttl");
    val_cond=util.nvl((String)pageDtlMap.get("val_cond"));
    lov_qry=(String)pageDtlMap.get("lov_qry");
    if(!val_cond.equals("N")){%>
    <td align="right"><%=cts=util.nvl((String)datasum.get(fld_ttl))%></td>
    <%}}}%>
    <td align="right"><%=util.Round(crt, 2)%>%</td><td align="right"><%=util.Round(tot, 2)%>%</td>
    </tr>
    <%}%>
    <%}
    }
    if(ttldisplay.equals("Y")){
    data=new HashMap();
    data=(HashMap)dataDtl.get(stt+"_TTL");
    if(data!=null){
    qty=util.nvl((String)data.get("QTY"));
    cts=util.nvl((String)data.get("CTS"));
    faamt=util.nvl((String)data.get(ttlonper),"0");
    Double toto=0.0;
    if(!grandfaamt.equals("0") && !faamt.equals("0"))
    toto=(Double.parseDouble(faamt)/Double.parseDouble(grandfaamt))*100;
    %>
   <tr>
   <td nowrap="nowrap"><b>Fancy Total</b></td><td align="right"><label class="blueLabel"><%=qty%></label></td><td align="right"><label class="blueLabel"><%=cts%></label></td>
    <%pageList=(ArrayList)pageDtl.get("SHOWPRP");
    if(pageList!=null && pageList.size() >0){
    for(int sp=0;sp<pageList.size();sp++){
    pageDtlMap=(HashMap)pageList.get(sp);
    fld_ttl=(String)pageDtlMap.get("fld_ttl");
    val_cond=util.nvl((String)pageDtlMap.get("val_cond"));
    lov_qry=(String)pageDtlMap.get("lov_qry");
    if(!val_cond.equals("N")){%>
    <td align="right"><label class="blueLabel"><%=cts=util.nvl((String)data.get(fld_ttl))%></label></td>
    <%}}}%>
   <td align="right"></td><td align="right"><label class="blueLabel"><%=util.Round(toto, 2)%>%</label></td>
   </tr> 
    <%
    }}
    data=new HashMap();
    data=(HashMap)dataDtl.get(stt);
    qty=util.nvl((String)data.get("QTY"));
    cts=util.nvl((String)data.get("CTS"));
    faamt=util.nvl((String)data.get(ttlonper),"0");
    Double tot=0.0;
    if(!faamt.equals("0") && !grandfaamt.equals("0"))
    tot=(Double.parseDouble(faamt)/Double.parseDouble(grandfaamt))*100;
    %>
    <tr>
   <td nowrap="nowrap"><b>Status Total</b></td><td align="right"><label class="blueLabel"><%=qty%></label></td><td align="right"><label class="blueLabel"><%=cts%></label></td>
    <%pageList=(ArrayList)pageDtl.get("SHOWPRP");
    if(pageList!=null && pageList.size() >0){
    for(int sp=0;sp<pageList.size();sp++){
    pageDtlMap=(HashMap)pageList.get(sp);
    fld_ttl=(String)pageDtlMap.get("fld_ttl");
    val_cond=util.nvl((String)pageDtlMap.get("val_cond"));
    lov_qry=(String)pageDtlMap.get("lov_qry");
    if(!val_cond.equals("N")){
    %>
    <td align="right"><label class="blueLabel"><%=cts=util.nvl((String)data.get(fld_ttl))%></label></td>
    <%}}}%>
   <td align="right"></td><td align="right"><label class="blueLabel"><%=util.Round(tot, 2)%>%</label></td>
   </tr> 
    <%
    }
    data=new HashMap();
    data=(HashMap)dataDtl.get("GRANDTTL");
    qty=util.nvl((String)data.get("QTY"));
    cts=util.nvl((String)data.get("CTS"));
    %>
   <tr>
   <th>Grand Total</th><td align="right"><label class="redLabel"><%=qty%></label></td><td align="right"><label class="redLabel"><%=cts%></label></td>
    <%pageList=(ArrayList)pageDtl.get("SHOWPRP");
    if(pageList!=null && pageList.size() >0){
    for(int sp=0;sp<pageList.size();sp++){
    pageDtlMap=(HashMap)pageList.get(sp);
    fld_ttl=(String)pageDtlMap.get("fld_ttl");
    val_cond=util.nvl((String)pageDtlMap.get("val_cond"));
        lov_qry=(String)pageDtlMap.get("lov_qry");
    if(!val_cond.equals("N")){%>
    <td align="right"><label class="redLabel"><%=cts=util.nvl((String)data.get(fld_ttl))%></label></td>
    <%}}}%>
   <td align="right"></td><td align="right"><label class="redLabel">100.0%</label></td>
   </tr>   
   <tr>
   <td colspan=<%=colspan%>></td>
   </tr>
   <tr>
   <th colspan=<%=colspan%> align="center"><b><%=(String)mprp.get(summaryLprp+"D")%> Summary</b></th>
   </tr>
   <tr>
   <th><%=(String)mprp.get(summaryLprp+"D")%></th><th>Pcs</th><th>Cts</th>
    <%pageList=(ArrayList)pageDtl.get("SHOWPRP");
    if(pageList!=null && pageList.size() >0){
    for(int j=0;j<pageList.size();j++){
    pageDtlMap=(HashMap)pageList.get(j);
    fld_nme=(String)pageDtlMap.get("fld_nme");
    val_cond=util.nvl((String)pageDtlMap.get("val_cond"));
    if(!val_cond.equals("N")){%>
    <th><%=fld_nme%></th>
    <%}}}%>
   
   <th>Crt%</th><th>Tot%</th>
   </tr>
   <%for(int i=0;i<prpSrtSummary.size();i++){
    String srtSummary=(String)prpSrtSummary.get(i);
    String key=srtSummary;
    conatinsdata= new ArrayList();
    datasum=new HashMap();
    datasum=(HashMap)datattl.get(key);
    if(datasum!=null){
    summary=util.nvl((String)datasum.get("SUMMARY"));
    qtysum=util.nvl((String)datasum.get("QTY"));
    ctssum=util.nvl((String)datasum.get("CTS"));
    faamtsum=util.nvl((String)datasum.get(ttlonper),"0");
    Double crt=(Double.parseDouble(ctssum)/Double.parseDouble(ctssum))*100;
    Double tot=0.0;
    if(!faamtsum.equals("0") && !grandfaamt.equals("0"))
    tot=(Double.parseDouble(faamtsum)/Double.parseDouble(grandfaamt))*100;
    %>
    <tr>
    <td><%=summary%></td><td align="right">
    <a href="orclRptAction.do?method=LOTPKTDTL&summaryLprp=<%=summaryLprp%>&summaryLprpval=<%=summary%>" title="Click here to get Packet Details" target="_blank"><%=qtysum%></a></td>
    <td align="right"><%=ctssum%></td>
    <%pageList=(ArrayList)pageDtl.get("SHOWPRP");
    if(pageList!=null && pageList.size() >0){
    for(int sp=0;sp<pageList.size();sp++){
    pageDtlMap=(HashMap)pageList.get(sp);
    fld_ttl=(String)pageDtlMap.get("fld_ttl");
    val_cond=util.nvl((String)pageDtlMap.get("val_cond"));
    lov_qry=(String)pageDtlMap.get("lov_qry");
    if(!val_cond.equals("N")){%>
    <td align="right"><%=cts=util.nvl((String)datasum.get(fld_ttl))%></td>
    <%}}}%>
    <td align="right"><%=util.Round(crt, 2)%>%</td><td align="right"><%=util.Round(tot, 2)%>%</td>
    </tr>
    <%}}%>
    
   <tr>
   <th colspan=<%=colspan%> align="center"><b>Packet Type Wise</b></th>
   </tr>
   <%
   
    ArrayList pkt_ty=new ArrayList();
    
    pkt_ty.add("NR");pkt_ty.add("NR_MX");pkt_ty.add("MX");pkt_ty.add("MIX");pkt_ty.add("SMX");pkt_ty.add("TTL");
   
    for(int i=0;i<pkt_ty.size();i++){
    String pkt_tySummary=(String)pkt_ty.get(i);
    String key=pkt_tySummary;
    conatinsdata= new ArrayList();
    datasum=new HashMap();
    datasum=(HashMap)datattl.get(key);
    if(datasum!=null){
    qtysum=util.nvl((String)datasum.get("QTY"),"0");
    ctssum=util.nvl((String)datasum.get("CTS"),"0");
    faamtsum=util.nvl((String)datasum.get(ttlonper),"0");
    Double crt=(Double.parseDouble(ctssum)/Double.parseDouble(ctssum))*100;
    Double tot=0.0;
    if(!faamtsum.equals("0") && !grandfaamt.equals("0"))
    tot=(Double.parseDouble(faamtsum)/Double.parseDouble(grandfaamt))*100;
    String lbl="";
    if(key.equals("TTL"))
    lbl="redLabel";
    %>
    <tr>
    <%if(!key.equals("TTL")){%>
    <td><%=pkt_tySummary%></td>
    <%}else{%>
    <th>Grand Total</th>
    <%}%>
    <td align="right"><label class="<%=lbl%>"><%=qtysum%></label></td><td align="right"><label class="<%=lbl%>"><%=ctssum%></label></td>
    <%pageList=(ArrayList)pageDtl.get("SHOWPRP");
    if(pageList!=null && pageList.size() >0){
    for(int sp=0;sp<pageList.size();sp++){
    pageDtlMap=(HashMap)pageList.get(sp);
    fld_ttl=(String)pageDtlMap.get("fld_ttl");
    val_cond=util.nvl((String)pageDtlMap.get("val_cond"));
    lov_qry=(String)pageDtlMap.get("lov_qry");
    if(!val_cond.equals("N")){%>
    <td align="right"><label class="<%=lbl%>"><%=cts=util.nvl((String)datasum.get(fld_ttl))%></label></td>
    <%}}}%>
    <td align="right"><label class="<%=lbl%>"><%=util.Round(crt, 2)%>%</label></td><td align="right"><label class="<%=lbl%>"><%=util.Round(tot, 2)%>%</label></td>
    </tr>
    <%}}%>
    
    </table>
    </td>

    <%if(shapebyrightside.equals("Y")){%>
    <td valign="top">
    <table class="grid1" style="width:300px">
        <%for(int l=0;l<sttLst.size();l++){
    String stt=(String)sttLst.get(l);
    %>   
    <%for(int i=0;i<prpSrtShape.size();i++){
    String srtShape=(String)prpSrtShape.get(i);
    String key=stt+"_"+srtShape;
    conatinsdata= new ArrayList();
    datasum=new HashMap();
    conatinsdata=(ArrayList)dataDtl.get(key);
    if(conatinsdata!=null && conatinsdata.size()>0){
    datasum=(HashMap)datattl.get(key);
    shape=util.nvl((String)datasum.get("SH"));
    qtysum=util.nvl((String)datasum.get("QTY"));
    ctssum=util.nvl((String)datasum.get("CTS"));
    faavgsum=util.nvl((String)datasum.get(fancyrte));
    faamtsum=util.nvl((String)datasum.get(ttlonper),"0");
    String subkey=key.substring(key.lastIndexOf("_")+1,key.length());
    if(!subkey.equals(roundSrt)){
    %>
    <tr><th colspan="3"><%=shape%></th><th  colspan="2"><%=stt%></th></tr>
    <tr><th><%=(String)mprp.get(row+"D")%></th><th>Pcs</th><th>Carat</th><th>Rate</th><th>Per%</th></tr>
    <%
    for(int j=0;j<conatinsdata.size();j++){
    data=new HashMap();
    data=(HashMap)conatinsdata.get(j);
    if(data!=null && data.size()>0){
    shape=util.nvl((String)data.get("SH"));
    sz=util.nvl((String)data.get("ROW"));
    qty=util.nvl((String)data.get("QTY"));
    cts=util.nvl((String)data.get("CTS"));
    faavg=util.nvl((String)data.get(fancyrte));
    faamt=util.nvl((String)data.get(ttlonper),"0");
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
    <%}%>
    
    <%pageList=(ArrayList)pageDtl.get("ROUGH_DATA");
      if(pageList!=null && pageList.size() >0){
      for(int j=0;j<pageList.size();j++){
      ArrayList roughszLst= (dataDtl.get("roughszLst") == null)?new ArrayList():(ArrayList)dataDtl.get("roughszLst");
      HashMap roughdatattl=(HashMap)dataDtl.get("roughdatattl");
      String size=util.nvl((String)dbinfo.get("SIZE"));
      ArrayList szList = (ArrayList)prp.get(size+"V");
      if(roughszLst.size()>0){
      %>
      <td valign="top">
      <table class="grid1">
      <tr><th colspan="6">Rough Details</th></tr>
      <tr>
      <th>Rough Size</th>
      <th>Rough Cts</th>
      <th>Polish Vlu</th>
      <th>Rough Pending Vlu</th>
      <th>AVG</th>
      <th>%</th>
      </tr>
          <%
          String ttlro_cts=util.nvl((String)roughdatattl.get("TOTAL_ROUGHCTS"));
          String ttlpo_vlu=util.nvl((String)roughdatattl.get("TOTAL_POLVAL"));
          String ttlro_vlu=util.nvl((String)roughdatattl.get("TOTAL_ROUGHVLU"));
          double ttlroavg=0;
          if(!ttlro_cts.equals("") && !ttlro_cts.equals("0") && !ttlpo_vlu.equals("") && !ttlpo_vlu.equals("0"))
          ttlroavg=Double.parseDouble(ttlpo_vlu)/Double.parseDouble(ttlro_cts);
          
          for(int s=0;s<szList.size();s++){
          String roughsz=(String)szList.get(s);
          if(roughszLst.contains(roughsz)){
          String ro_cts=util.nvl((String)roughdatattl.get(roughsz+"_ROUGHCTS"));
          String po_vlu=util.nvl((String)roughdatattl.get(roughsz+"_POLVAL"));
          String ro_vlu=util.nvl((String)roughdatattl.get(roughsz+"_ROUGHVLU"));
          double roavg=0;
          double per=0;
          if(!po_vlu.equals("") && !po_vlu.equals("0") && !ro_cts.equals("") && !ro_cts.equals("0"))
          roavg=Double.parseDouble(po_vlu)/Double.parseDouble(ro_cts);
          
          if(!ro_cts.equals("") && !ro_cts.equals("0") && !ttlro_cts.equals("") && !ttlro_cts.equals("0"))
          per=(Double.parseDouble(ro_cts)/Double.parseDouble(ttlro_cts))*100;
          %>
          <tr>
          <td align="center"><%=roughsz%></td>
          <td align="right"><%=ro_cts%></td>
          <td align="right"><%=po_vlu%></td>
          <td align="right"><%=ro_vlu%></td>
          <td align="right"><%=util.Round(roavg, 2)%></td>
          <td align="right"><%=util.Round(per, 2)%></td>
          </tr>
          <%}}%>
          <tr>
          <td align="center"><b>Total</b></td>
          <td align="right"><%=ttlro_cts%></td>
          <td align="right"><%=ttlpo_vlu%></td>
          <td align="right"><%=ttlro_vlu%></td>
          <td align="right"><%=util.Round(ttlroavg, 2)%></td>
          <td align="right"><%=util.Round(100, 2)%></td>
          </tr>
          <%}%>
      </table>
      </td>
    <%}}%>
    </tr></table>
    <%}else{%>

Sorry No Result Found
<%}}}else{
        HashMap dataDtl= (HashMap)request.getAttribute("dataDtl");
        ArrayList rowGtLst= (ArrayList)request.getAttribute("rowGtLst");
        ArrayList colGtLst= (ArrayList)request.getAttribute("colGtLst");
        String rowlprpComm= (String)request.getAttribute("rowlprpComm");
        String collprpComm= (String)request.getAttribute("collprpComm");
        ArrayList prpValSummary = null;
        ArrayList colprpgrpLst = (ArrayList)prp.get(collprpComm+"G");
        ArrayList rowprpLst = (ArrayList)prp.get(rowlprpComm+"V");
        ArrayList colprpLst=new ArrayList();
        if(!colprpgrpLst.contains("NA"))
        colprpLst.add("NA");
        for(int i=0;i<colprpgrpLst.size();i++){
           String colgrpval=util.nvl((String)colprpgrpLst.get(i),"NA");
           if(!colprpLst.contains(colgrpval))
           colprpLst.add(colgrpval);
        }
        int colprpLstsz=colprpLst.size();
        int rowprpLstsz=rowprpLst.size();
        String ttl_cts=util.nvl((String)dataDtl.get("TTL_CTS"),"0");
        String ttl_vlu=util.nvl((String)dataDtl.get("TTL_VLU"),"0");
%>
   <table class="grid1">
   <tr>
   <td></td>
   <%for(int i=0;i<colprpLstsz;i++){
       String colval=util.nvl((String)colprpLst.get(i),"NA");
       if(colGtLst.contains(colval)){%>
       <th colspan="2"><%=colval%></th>
        <%}%>
   <%}%>
   <th colspan="2">Total</th>
   </tr>
   <tr>
   <td></td>
   <%for(int i=0;i<colprpLstsz;i++){
       String colval=util.nvl((String)colprpLst.get(i),"NA");
       if(colGtLst.contains(colval)){%>
       <td nowrap="nowrap" align="center">CTS %</td><td nowrap="nowrap" align="center">VLU %</td>
        <%}%>
   <%}%>
   <td nowrap="nowrap" align="center">CTS %</td><td nowrap="nowrap" align="center">VLU %</td>
   </tr>
   
   <%for(int r=0;r<rowprpLstsz;r++){
   String rowval=util.nvl((String)rowprpLst.get(r),"NA");
       if(rowGtLst.contains(rowval)){%>
       <tr> 
            <td align="center"><%=rowval%></td>
            <%for(int c=0;c<colprpLstsz;c++){
                String colval=util.nvl((String)colprpLst.get(c),"NA");
                if(colGtLst.contains(colval)){
                String cts=util.nvl((String)dataDtl.get(rowval+"_"+colval+"_CTS"),"0");
                String vlu=util.nvl((String)dataDtl.get(rowval+"_"+colval+"_VLU"),"0");
                Double totcts=0.0;Double totvlu=0.0;
                if(!ttl_cts.equals("0") && !cts.equals("0"))
                totcts=(Double.parseDouble(cts)/Double.parseDouble(ttl_cts))*100;
                if(!ttl_vlu.equals("0") && !vlu.equals("0"))
                totvlu=(Double.parseDouble(vlu)/Double.parseDouble(ttl_vlu))*100;
                %>
                <td align="right"><%=util.Round(totcts, 2)%>%</td>
                <td align="right"><%=util.Round(totvlu, 2)%>%</td>
                <%}%>
           <%}%>
       <%String cts=util.nvl((String)dataDtl.get(rowval+"_CTS"),"0");
         String vlu=util.nvl((String)dataDtl.get(rowval+"_VLU"),"0");
         Double totcts=0.0;Double totvlu=0.0;
         if(!ttl_cts.equals("0") && !cts.equals("0"))
         totcts=(Double.parseDouble(cts)/Double.parseDouble(ttl_cts))*100;
         if(!ttl_vlu.equals("0") && !vlu.equals("0"))
         totvlu=(Double.parseDouble(vlu)/Double.parseDouble(ttl_vlu))*100;
         %>
         <td align="right" nowrap="nowrap"><%=util.Round(totcts, 2)%>%</td>
         <td align="right" nowrap="nowrap"><%=util.Round(totvlu, 2)%>%</td>
       </tr>
       <%}%>
   <%}%>
   <tr>
   <th>Total</th>
   <%for(int i=0;i<colprpLstsz;i++){
       String colval=util.nvl((String)colprpLst.get(i),"NA");
       if(colGtLst.contains(colval)){
       String cts=util.nvl((String)dataDtl.get(colval+"_CTS"),"0");
       String vlu=util.nvl((String)dataDtl.get(colval+"_VLU"),"0");
       Double totcts=0.0;Double totvlu=0.0;
       if(!ttl_cts.equals("0") && !cts.equals("0"))
       totcts=(Double.parseDouble(cts)/Double.parseDouble(ttl_cts))*100;
       if(!ttl_vlu.equals("0") && !vlu.equals("0"))
       totvlu=(Double.parseDouble(vlu)/Double.parseDouble(ttl_vlu))*100;
       %>
       <td align="right" nowrap="nowrap"><%=util.Round(totcts, 2)%>%</td>
       <td align="right" nowrap="nowrap"><%=util.Round(totvlu, 2)%>%</td>
   <%}%>
   <%}%>
   <td align="right">100%</td>
   <td align="right">100%</td>
   </tr>
   
   </table>
<%}%>
</td></tr>
  <tr><td>
<jsp:include page="/footer.jsp" />
</td></tr>
  </table>  
       <%@include file="../calendar.jsp"%>
  </body>
</html>