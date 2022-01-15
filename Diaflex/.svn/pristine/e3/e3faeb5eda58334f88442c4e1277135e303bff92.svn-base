
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
 
<%@ page errorPage="../error_page.jsp" %>
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import="java.util.ArrayList,java.util.HashMap,java.sql.ResultSet,ft.com.*, java.util.Date, java.util.ArrayList, ft.com.dao.*"%>
<jsp:useBean id="util" class="ft.com.JspUtil" scope="page" />
<jsp:useBean id="info" class="ft.com.InfoMgr" scope="session" />
<%@taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>
<%@taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>Delivery Return</title>
    <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/bse.css?v=<%=info.getJsVersion()%>"/>
    <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/style.css?v=<%=info.getJsVersion()%>"/>
    <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/memoScript.js?v=<%=info.getJsVersion()%> " > </script>
    <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/bse.js?v=<%=info.getJsVersion()%> " > </script>
    <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/assortScript.js?v=<%=info.getJsVersion()%> " > </script>
      <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/Validation.js " > </script>
  </head>
<%
ArrayList prpDspBlocked = info.getPageblockList();
HashMap allPageDtl= (info.getPageDetails() == null)?new HashMap():(HashMap)info.getPageDetails();
HashMap pageDtl=(HashMap)allPageDtl.get("DELIVERY_RETURN");
HashMap pageDtlMap=new HashMap();
ArrayList pageList=new ArrayList();
String fld_nme="",fld_ttl="",val_cond="",dflt_val="",fld_typ="",form_nme="",lov_qry="";
        String logId=String.valueOf(info.getLogId());
        String onfocus="cook('"+logId+"')";
        %>
 <body onfocus="<%=onfocus%>" onload="MM_preloadImages('images/faunalogo.png')" topmargin="0" leftmargin="0" rightmargin="0" bottommargin="0">
<table width="100%"  border="0" align="center" cellpadding="0" cellspacing="0" class="mainbg">
  <tr>
    <td height="103" valign="top">
   <jsp:include page="/header.jsp" /></td>
  </tr>
 
  <tr>
    <td height="5" valign="top" class="greyline"><img src="../images/grey_linebig.png" width="685" height="5" align="right" /></td>
  </tr>
  <tr>
  <td valign="top" class="hedPg">
  <table cellpadding="1" cellspacing="5"><tr><td valign="middle">
  <img src="../images/line.jpg" border="0" /></td><td valign="middle"> <span class="pgHed">Delivery Return</span> </td>
</tr></table>
<% if(request.getAttribute("msg")!=null){%>
<tr><td valign="top" class="tdLayout">
<span class="redLabel"><%=request.getAttribute("msg")%></span>
</td></tr>
<%}%>
<% if(request.getAttribute("msgCan")!=null){%>
<tr><td valign="top" class="tdLayout">
<span class="redLabel"><%=request.getAttribute("msgCan")%></span>
</td></tr>
<%}%>
<% if(request.getAttribute("msgRv")!=null){%>
<tr><td valign="top" class="tdLayout">
<span class="redLabel"><%=request.getAttribute("msgRv")%></span>
</td></tr>
<%}%>
<% if(request.getAttribute("msgAv")!=null){%>
<tr><td valign="top" class="tdLayout">
<span class="redLabel"><%=request.getAttribute("msgAv")%></span>
</td></tr>
<%}%>
 <tr><td valign="top" class="tdLayout">
  <html:form action="/marketing/deliveryRtn.do?method=loadPkt" method="POST" onsubmit="return validate_sale()">
  <table><tr><td valign="top">
  <table class="grid1" >
  <tr><th colspan="2">Sale Search </th></tr>
 <tr>
<td>Buyer Name :</td>
<td>
 <html:select property="value(byrIdn)" name="deliveryRtnForm" onchange="getFinalByrDlv(this)" styleId="byrId" >
    <html:option value="0">---select---</html:option>
    <html:optionsCollection property="byrLst" name="deliveryRtnForm"  value="byrIdn" label="byrNme" />
                
  </html:select>
               
</td></tr> 
<tr>
<td>Billing  Party :</td><td>
<html:select property="value(prtyIdn)" name="deliveryRtnForm" onchange="getDlvIdn(this.value)"  styleId="prtyId"  >
     <html:option value="0">---select---</html:option>
</html:select>

</td>

</tr>
 

<tr><td>Packets. </td><td>
<html:textarea property="value(vnmLst)" name="deliveryRtnForm" styleId="vnmLst" /> </td> </tr>
<tr><td colspan="2"><html:submit property="submit" value="View" styleClass="submit"/></td></tr>           
</table></td><td valign="top">
<div id="memoIdn"></div>
</td></tr></table>
</html:form>
</td></tr>
<%String view = (String)request.getAttribute("view");
if(view!=null){
%>
<tr><td valign="top" class="tdLayout">
<html:form action="/marketing/deliveryRtn.do?method=RtnPkt" method="post">
<%  
ArrayList pkts = (ArrayList)session.getAttribute("PktList");
if(pkts!=null && pkts.size()>0){
%>

<table>
    <tr><td>
      <%
            pageList=(ArrayList)pageDtl.get("BUTTON");
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
            if(fld_typ.equals("S")){%>
            <html:submit property="<%=fld_nme%>" value="<%=fld_ttl%>" onclick="<%=val_cond%>" styleClass="submit"/>&nbsp;
            <%}else if(fld_typ.equals("B")){%>
            <html:button property="<%=fld_nme%>" value="<%=fld_ttl%>" onclick="<%=val_cond%>" styleClass="submit"/>&nbsp;
            <%}}}
            %>
           
             </td></tr>
<tr><td>
<label class="pgTtl">Delivery Packets</label>
<% ArrayList prps = (info.getValue("MDL_MEMO_RTRN") == null)?new ArrayList():(ArrayList)info.getValue("MDL_MEMO_RTRN"); %>  </td></tr>
<tr><td>
<table class="grid1">
                <tr>
                    <th>Sr</th>
                    <%pageList=(ArrayList)pageDtl.get("RADIOHDR");
                    if(pageList!=null && pageList.size() >0){
                    for(int j=0;j<pageList.size();j++){
                    pageDtlMap=(HashMap)pageList.get(j);
                    fld_nme="value("+(String)pageDtlMap.get("fld_nme")+")";
                    fld_ttl=(String)pageDtlMap.get("fld_ttl");
                    fld_typ=(String)pageDtlMap.get("fld_typ");
                    dflt_val=(String)pageDtlMap.get("dflt_val");
                    val_cond=(String)pageDtlMap.get("val_cond"); %>
                    <th><html:radio property="<%=fld_nme%>" styleId="<%=fld_typ%>" name="deliveryRtnForm" onclick="<%=val_cond%>"  value="<%=dflt_val%>"/><%=fld_ttl%> </th>
                    <%}}%>
                    <th>Delivery Id</th>
                      <th>Packet Code</th>
                     <%for(int j=0; j < prps.size(); j++) {
                     String lprp=(String)prps.get(j);
                     if(prpDspBlocked.contains(lprp)){
                     }else{%>
                        <th><%=lprp%></th>
                    <%}}%>
                    <th>RapRte</th>
                    <th>RapVlu</th>
                    <th>Dis</th>
                    <th>Prc / Crt </th>
                    <th>ByrDis</th>
                    <th>Quot</th>
                     <%pageList=(ArrayList)pageDtl.get("COLUMN");
            if(pageList!=null && pageList.size() >0){
            for(int j=0;j<pageList.size();j++){
            pageDtlMap=(HashMap)pageList.get(j);
            fld_nme="value("+(String)pageDtlMap.get("fld_nme")+")";
            fld_ttl=(String)pageDtlMap.get("fld_ttl");
            fld_typ=(String)pageDtlMap.get("fld_typ");
            dflt_val=(String)pageDtlMap.get("dflt_val");
            val_cond=(String)pageDtlMap.get("val_cond"); %>
            <th><%=fld_ttl%> </th>
            <%}}%>
                     <!--
                     <th><html:radio property="value(slRd)" styleId="rtRd" name="deliveryRtnForm" onclick="ALLRedio('rtRd' ,'RT_')"  value="RT"/>&nbsp;Return</th>
                     <th><html:radio property="value(slRd)" styleId="canRd" name="deliveryRtnForm" onclick="ALLRedio('canRd' ,'CAN_')"  value="RT"/>&nbsp;Cancel</th>
                     <th><html:radio property="value(slRd)"  styleId="noneRd" name="deliveryRtnForm" onclick="ALLRedio('noneRd' ,'NONE_')"  value="RT"/>&nbsp;None</th>
                 -->
                 
                </tr>
            <%
             int count =0 ;
             
              
                for(int i=0; i < pkts.size(); i++) {%>
                <tr>
                <td><%=(i+1)%></td>
                <%    count=i+1;
                    PktDtl pkt = (PktDtl)pkts.get(i);
                    long pktIdn = pkt.getPktIdn();
                    String cbPrp = "value(upd_" + pktIdn + ")";
                    String sttPrp = "value(stt_" + pktIdn + ")" ;
                    String invPrp = "value(INV_" + pktIdn + ")" ;
                    
                    String rdRTId = "RT_"+count;
                    String rdCANId = "CAN_"+count;
                    String rdNONEId = "NONE_"+count;
                     String onClickRt = "SetCalculationDlv('"+pktIdn+"','RT','"+pkt.getSaleId()+"')";
                     String onClickCan = "SetCalculationDlv('"+pktIdn+"','CAN','"+pkt.getSaleId()+"')";
                     String onClickNone = "SetCalculationDlv('"+pktIdn+"','NONE','"+pkt.getSaleId()+"')";
                     String rmkTxt =  "value(rmk_" + pktIdn + ")" ;
             %>
                         <%pageList=(ArrayList)pageDtl.get("RADIOBODY");
            if(pageList!=null && pageList.size() >0){
            for(int j=0;j<pageList.size();j++){
            pageDtlMap=(HashMap)pageList.get(j);
            fld_nme="value("+(String)pageDtlMap.get("fld_nme")+""+pktIdn+")";
            fld_ttl=(String)pageDtlMap.get("fld_ttl");
            fld_typ=(String)pageDtlMap.get("fld_typ")+count;
            dflt_val=util.nvl((String)pageDtlMap.get("dflt_val")).trim();
            if(dflt_val.equals("PKT"))
            dflt_val=String.valueOf(pktIdn);
            val_cond=(String)pageDtlMap.get("val_cond");
            val_cond=val_cond.replaceAll("PKTIDN",String.valueOf(pktIdn));
            val_cond=val_cond.replaceAll("DLVID",pkt.getSaleId());%>
            <td nowrap="nowrap"><html:radio property="<%=fld_nme%>" styleId="<%=fld_typ%>"  name="deliveryRtnForm"  onclick="<%=val_cond%>"  value="<%=dflt_val%>"/>
            <%if(dflt_val.equals("RT")){%>
            <html:text property="<%=rmkTxt%>" size="10"  />
            <%}%>
            </td>
            <%}}%>
                <td><%=pkt.getSaleId()%></td>
                 <td><%=pkt.getVnm()%></td>
                <%for(int j=0; j < prps.size(); j++) {
                String lprp = (String)prps.get(j);
                if(prpDspBlocked.contains(lprp)){
                  }else{%>
                    <td><%=util.nvl(pkt.getValue((String)prps.get(j)))%></td>
                <%}}
                %>
                <td><%=pkt.getRapRte()%></td>
                <td><%=util.nvl(pkt.getValue("rapVlu"))%></td>
                <td><%=pkt.getDis()%></td>
                <td><%=pkt.getRte()%></td>
                <td><%=pkt.getByrDis()%></td>
                <td><%=pkt.getMemoQuot()%></td>
                <%pageList=(ArrayList)pageDtl.get("COLUMN");
            if(pageList!=null && pageList.size() >0){
            for(int j=0;j<pageList.size();j++){
            pageDtlMap=(HashMap)pageList.get(j);
            fld_nme=(String)pageDtlMap.get("fld_nme");
            fld_ttl=(String)pageDtlMap.get("fld_ttl");
            fld_typ=(String)pageDtlMap.get("fld_typ");
            dflt_val=(String)pageDtlMap.get("dflt_val");
            val_cond=(String)pageDtlMap.get("val_cond"); %>
            <td><%=util.nvl(pkt.getValue(fld_nme))%> </td>
            <%}}%>
                <!-- 
                 <td><html:radio property="<%=sttPrp%>" styleId="<%=rdRTId%>" name="deliveryRtnForm" onclick="<%=onClickRt%>" value="RT"/></td>
                 <td><html:radio property="<%=sttPrp%>" styleId="<%=rdCANId%>" name="deliveryRtnForm" onclick="<%=onClickCan%>" value="CAN"/></td>
                 <td><html:radio property="<%=sttPrp%>" styleId="<%=rdNONEId%>" name="deliveryRtnForm" onclick="<%=onClickNone%>" value="NONE"/></td>
                -->
                
                
                </tr>
              <%  
                }
            %>
            <input type="hidden" id="rdCount" value="<%=count%>" />
            </table></td></tr>
         
             </table>
           <%}else{%>
            Sorry no result found.
            <%}%>
            </html:form>
</td>
</tr>
<%}%>
 <tr>
    <td><jsp:include page="/footer.jsp" /> </td>
    </tr>
</table>
</body>
</html>