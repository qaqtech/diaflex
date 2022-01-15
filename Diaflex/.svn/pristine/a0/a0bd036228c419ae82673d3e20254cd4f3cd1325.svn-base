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
        ArrayList vWPrpSummaryList=(ArrayList)session.getAttribute("LOT_RPT_FLT");
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
<html:form action="/mixre/lotReport.do?method=fetchlot"  method="POST">
  <table  class="grid1">
   <tr>
   <th colspan="2" align="center">Lot Search</th>
   </tr>
   <tr>
   <td>Data Based On</td>
   <td>
   <html:select name="lotReportForm" property="value(lotlprp)" styleId="lotlprp" >
   <html:option value="LOTNO">Lot No</html:option>
   <html:option value="LOT_MMYR">Lot MMYR</html:option>
    <html:option value="CT_INWARD_NO">CT Inward</html:option>
   </html:select>
   </td>
   </tr>
   <tr>
   <td>Lot/YY </td>
   <td><html:text property="value(lot)" name="lotReportForm" styleId="lot"/></td>
   </tr>
   <tr>
   <td>
   By
   </td>
   <td>
<html:select name="lotReportForm" property="value(rowlprp)" styleId="rowlprp" >
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
   <td>Summary By(Division)</td>
   <td>
   <html:select name="lotReportForm" property="value(shapebyrightside)" styleId="shapebyrightside" >
   <html:option value="N">No</html:option>
   <html:option value="Y">Yes</html:option>
   </html:select>
   </td>
   </tr>
   <tr>
   <td>Recpt Date</td>
   <td>
 <html:text property="value(frmDte)" styleId="frmDte" name="lotReportForm" size="10" /><img src="<%=info.getReqUrl()%>/images/calender.png" onClick="setYears(2011, <%=info.getCurrentYear()%>);showCalender(this, 'frmDte');"> 
       To&nbsp; <html:text property="value(toDte)" styleId="toDte" name="lotReportForm"  size="10"/> <img src="<%=info.getReqUrl()%>/images/calender.png" onClick="setYears(2011, <%=info.getCurrentYear()%>);showCalender(this, 'toDte');">
      
   </td>
   </tr>
   <tr>
   <td align="center" colspan="2">
   <html:submit property="value(srch)" value="Fetch" styleClass="submit" />&nbsp;&nbsp;

 
   </td>
   </tr>
   </table>
</html:form>
</td>
</tr>
    <tr>
    <td>
    <table><tr>
    <%
    String normal= util.nvl((String)request.getAttribute("NORMAL")); 
    String view= util.nvl((String)request.getAttribute("view")); 
    HashMap data=new HashMap();
    HashMap datasum=new HashMap();
    if(view.equals("Y")){
        ArrayList pktTyGrid= (ArrayList)session.getAttribute("pktTyGrid");
        for(int z=0;z<pktTyGrid.size();z++){
        String pkt_tygrid=util.nvl((String)pktTyGrid.get(z)); 
        HashMap dataDtl= (HashMap)session.getAttribute(pkt_tygrid+"_dataDtl");
        HashMap datattl= (HashMap)session.getAttribute(pkt_tygrid+"_datattl");
        ArrayList keytable= (ArrayList)session.getAttribute(pkt_tygrid+"_keytable");
        ArrayList sttLst= (ArrayList)session.getAttribute(pkt_tygrid+"_sttLst");
        String shapebyrightside= util.nvl((String)request.getAttribute("shapebyrightside"),"N"); 
        HashMap dbinfo = info.getDmbsInfoLst();
        String sh = (String)dbinfo.get("SHAPE");
        if(pkt_tygrid.equals("MIX")){
        sh="MIX_SHAPE";
        }
    if(dataDtl!=null && dataDtl.size()>0){
    ArrayList conatinsdata= new ArrayList();
    String shape="",summary="",sz="",qty="",faamt="",cts="",fancyrte="",faavg="",colper="",ttlonper="",colpersum="",mavg="",msumavg="",avg="",avgp1="",avgp2="",qtysum="",ctssum="",faamtsum="",faavgsum="",avgsum="",avgp1sum="",avgp2sum="",grandcts="",grandfaamt="",fac="",lot="",row="",mlotidn="";
        ArrayList prpSrtShape = null;
        ArrayList prpValShape = null;
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
    avg=util.nvl((String)data.get("AVG"));
    grandfaamt=util.nvl((String)data.get(ttlonper),"0");
    mlotidn=util.nvl((String)data.get("IDN"));
    %>
    <td valign="top" class="hedPg">
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
  &nbsp;&nbsp;<a href="lotReport.do?method=LOTPKTDTL&pkt_tygrid=<%=pkt_tygrid%>" target="_blank">Pkt Dtl</a>
    </td></tr>
    <tr><td valign="top">
    <table class="grid1" style="width:500px">
    <tr><td colspan=<%=colspan%> align="left"><%=lot%></td></tr>
    <%for(int l=0;l<sttLst.size();l++){
    String stt= (String)sttLst.get(l);
    String ttldisplay="N";
    %>
    <tr><td colspan=<%=colspan%>><a href="lotReport.do?method=LOTPKTDTL&dsp_stt=<%=stt%>&pkt_tygrid=<%=pkt_tygrid%>" target="_blank"><%=stt%></a></td></tr>
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
    <!--<tr>
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
   </tr> -->
    <%
    }
    data=new HashMap();
    data=(HashMap)dataDtl.get("GRANDTTL");
    qty=util.nvl((String)data.get("QTY"));
    cts=util.nvl((String)data.get("CTS"));
    %>
   <tr>
   <th>Total</th><td align="right"><label class="redLabel"><%=qty%></label></td><td align="right"><label class="redLabel"><%=cts%></label></td>
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
   <%
    for(int h=0;h<vWPrpSummaryList.size();h++){
    String summaryLprp=util.nvl((String)vWPrpSummaryList.get(h));
                if(pkt_tygrid.equals("MIX") && (summaryLprp.equals("CO") || summaryLprp.equals("COL")))
                summaryLprp="MIX_COLOR";
                if(pkt_tygrid.equals("MIX") && (summaryLprp.equals("PU") || summaryLprp.equals("CLR")))
                summaryLprp="MIX_CLARITY";
                if(pkt_tygrid.equals("MIX") && (summaryLprp.equals("SH") || summaryLprp.equals("SHAPE")))
                summaryLprp="MIX_SHAPE";
                if(pkt_tygrid.equals("MIX") && (summaryLprp.equals("SZ") || summaryLprp.equals("SIZE")))
                summaryLprp="MIX_SIZE";
    ArrayList prpSrtSummary = (ArrayList)prp.get(summaryLprp+"S");
    ArrayList prpValSummary = (ArrayList)prp.get(summaryLprp+"V");
    String dta_typ=util.nvl((String)mprp.get(summaryLprp+"T"));
    if(dta_typ.equals("T"))
    prpSrtSummary = (datattl.get(summaryLprp+"_GTPRP") == null)?new ArrayList():(ArrayList)datattl.get(summaryLprp+"_GTPRP");
    prpValSummary = new ArrayList();
    prpValSummary = prpSrtSummary;
   %>
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
    datasum=(HashMap)datattl.get(summaryLprp+"_"+key);
    if(datasum!=null){
    summary=util.nvl((String)datasum.get("SUMMARY"));
    qtysum=util.nvl((String)datasum.get("QTY"));
    ctssum=util.nvl((String)datasum.get("CTS"),"0");
    faamtsum=util.nvl((String)datasum.get(ttlonper),"0");
    data=new HashMap();
    data=(HashMap)dataDtl.get("GRANDTTL");
     Double crt=0.0;
     if(!ctssum.equals("0"))
     crt=(Double.parseDouble(ctssum)/Double.parseDouble(util.nvl((String)data.get("CTS"))))*100;
    Double tot=0.0;
    if(!faamtsum.equals("0") && !grandfaamt.equals("0"))
    tot=(Double.parseDouble(faamtsum)/Double.parseDouble(grandfaamt))*100;
    %>
    <tr>
    <td><%=summary%></td><td align="right">
    <a href="lotReport.do?method=LOTPKTDTL&summaryLprp=<%=summaryLprp%>&summaryLprpval=<%=summary%>&pkt_tygrid=<%=pkt_tygrid%>" title="Click here to get Packet Details" target="_blank"><%=qtysum%></a></td>
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
    <%}}}%>
    
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
    data=(HashMap)datattl.get("TTL");
    qty=util.nvl((String)data.get("QTY"));
    cts=util.nvl((String)data.get("CTS"),"0");
     Double crt=0.0;
     if(!cts.equals("0"))
     crt=(Double.parseDouble(ctssum)/Double.parseDouble(cts))*100;
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
    <td align="right"><label class="<%=lbl%>"><%=util.Round(crt, 2)%>%</label></td><td align="right"><label class="<%=lbl%>"></label></td>
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
    Double crt=0.0;
    if(!ctssum.equals("0"))
     crt=(Double.parseDouble(cts)/Double.parseDouble(ctssum))*100;
    Double tot=0.0;
    if(!faamt.equals("0") && !faamtsum.equals("0"))
    tot=(Double.parseDouble(faamt)/Double.parseDouble(faamtsum))*100;
    %>
    <tr>
    <td nowrap="nowrap"><%=sz%></td><td align="right"><%=qty%></td><td align="right"><%=cts%></td><td align="right"><%=faavg%></td><td align="right"><%=util.Round(tot, 2)%>%</td>
    </tr>
    <%}
    }
      Double crt=0.0;
    if(!ctssum.equals("0"))
     crt=(Double.parseDouble(ctssum)/Double.parseDouble(ctssum))*100;
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
    </tr></table></td>
    <%}}}%>
    </tr>
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