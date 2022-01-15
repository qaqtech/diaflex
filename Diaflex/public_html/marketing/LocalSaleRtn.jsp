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
    <title>Local Sale Return</title>
    <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/bse.css?v=<%=info.getJsVersion()%>"/>
    <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/style.css?v=<%=info.getJsVersion()%>"/>
    <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/memoScript.js?v=<%=info.getJsVersion()%> " > </script>
    <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/bse.js?v=<%=info.getJsVersion()%> " > </script>
    <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/assortScript.js?v=<%=info.getJsVersion()%> " > </script>
    <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/Validation.js " > </script>
    <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/priceChange.js?v=<%=info.getJsVersion()%> " > </script>
  </head>
<%
HashMap dbinfo = info.getDmbsInfoLst();
String cnt = (String)dbinfo.get("CNT");
        String logId=String.valueOf(info.getLogId());
        String onfocus="cook('"+logId+"')";
        HashMap allPageDtl= (info.getPageDetails() == null)?new HashMap():(HashMap)info.getPageDetails();
        HashMap pageDtl=(HashMap)allPageDtl.get("LOCAL_SALE");
     ArrayList prpDspBlocked = info.getPageblockList();
    ArrayList pageList=new ArrayList();
    HashMap pageDtlMap=new HashMap();
    String fld_nme="",fld_ttl="",val_cond="",dflt_val="",fld_typ="",form_nme="",lov_qry="";
  
        %>
 <body onfocus="<%=onfocus%>" >
 
 <% 
pageList= ((ArrayList)pageDtl.get("MEMODTL") == null)?new ArrayList():(ArrayList)pageDtl.get("MEMODTL");  
       if(pageList!=null && pageList.size() >0){%>
   <select id="memoDtl" name="memoDtl" style="display:none">
    <%   for(int j=0;j<pageList.size();j++){
            pageDtlMap=(HashMap)pageList.get(j);
            dflt_val=(String)pageDtlMap.get("dflt_val");
           fld_ttl=(String)pageDtlMap.get("fld_ttl");%>
       <option value="<%=dflt_val%>"><%=fld_ttl%></option>     
    <% }%>
    </select>
    <%}else{ %>
      <select id="memoDtl" name="memoDtl" style="display:none">
       <option value="id">Memo Id</option>     
        <option value="dte">Date</option>     
      </select>
    <%}%>
<input type="hidden" name="CNT" id="CNT" value="<%=cnt%>" />
<input type="hidden" name="REQURL" id="REQURL" value="<%=info.getReqUrl()%>" />
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
  <img src="../images/line.jpg" border="0" /></td><td valign="middle"> <span class="pgHed">Local Sales Return</span> </td>
</tr></table>
<tr><td valign="top" class="tdLayout">
 <table cellpadding="2" cellspacing="2" >
        <% if(request.getAttribute("RTMSG")!=null){%>
        <tr><td height="15"  valign="top" class="tdLayout"><span class="redLabel"> <%=request.getAttribute("RTMSG")%></span></td></tr>
       <% }
        %>
       <% if(request.getAttribute("CANMSG")!=null){%>
        <tr><td height="15"  valign="top" class="tdLayout"><span class="redLabel"> <%=request.getAttribute("CANMSG")%></span></td></tr>
       <% }
        %>
  </table>
  </td>
  </tr>
 <tr><td valign="top" class="tdLayout">
  <table>
  <tr><td>
  <html:form action="/marketing/localSaleRtn.do?method=loadPkt" method="POST" onsubmit="return validate_sale()">
   <table><tr><td valign="top"> <table class="grid1" >
  <tr><th colspan="2">Sale Search </th></tr>
 <tr>
<td>Buyer Name :</td>
<td>
 <html:select property="byrIdn" name="localSaleForm" onchange="getFinalByr(this,'LS')" styleId="byrId" >
    <html:option value="0">---select---</html:option>
    <html:optionsCollection property="byrLst" name="localSaleForm"  value="byrIdn" label="byrNme" />
                
  </html:select>
               
</td></tr> 
<tr>
<td>Billing  Party :</td><td>
<html:select property="prtyIdn" name="localSaleForm" onchange="getSaleTrms(this.value ,'SL');" styleId="prtyId"  >
     <html:option value="0">---select---</html:option>   
</html:select>
</td>
</tr>
<tr>
<td>Terms :</td><td>
<html:select property="relIdn" name="localSaleForm"  onchange="getSaleIdn('LS')" styleId="rlnId"  >
     <html:option value="0">---select---</html:option>
</html:select>

</td>
</tr>
 <tr><td>Packets. </td><td>
<html:textarea property="value(vnmLst)" name="localSaleForm" styleId="vnmLst" /> </td> </tr>
<tr><td colspan="2"><html:submit property="submit" value="View" styleClass="submit"/></td></tr>           
</table>
</td><td valign="top"><div id="memoIdn"></div> </td></tr></table>
</html:form>
  </td></tr>
    
    
    
   <html:form action="/marketing/localSaleRtn.do?method=save" method="POST"  onsubmit="return confirmChanges()">
   <% if(request.getAttribute("view")!=null){
   
     ArrayList pkts = (ArrayList)info.getValue("PKTS");
   if(pkts!=null && pkts.size()>0){
  %>
   
           
 
  <tr><td>
            <label class="pgTtl">Packets</label>
            <%
                ArrayList prps = (info.getValue("MDL_MEMO_RTRN") == null)?new ArrayList():(ArrayList)info.getValue("MDL_MEMO_RTRN");
             %>  </td></tr>
             <tr><td>
            <table class="grid1">
                <tr>
                    <th>Sr</th>
                                <%
pageList= ((ArrayList)pageDtl.get("RADIOHDR") == null)?new ArrayList():(ArrayList)pageDtl.get("RADIOHDR");            
            if(pageList!=null && pageList.size() >0){
            for(int j=0;j<pageList.size();j++){
            pageDtlMap=(HashMap)pageList.get(j);
            fld_nme="value("+(String)pageDtlMap.get("fld_nme")+")";
            fld_ttl=(String)pageDtlMap.get("fld_ttl");
            fld_typ=(String)pageDtlMap.get("fld_typ");
            dflt_val=(String)pageDtlMap.get("dflt_val");
            val_cond=(String)pageDtlMap.get("val_cond"); %>
            <th><html:radio property="<%=fld_nme%>" styleId="<%=fld_typ%>" name="localSaleForm" onclick="<%=val_cond%>"  value="<%=dflt_val%>"/><%=fld_ttl%> </th>
            <%}}%>
                    <!--<th><html:checkbox property="value(slRd)" styleId="checkAll" name="localSaleForm" onclick="checkALL('RT_' ,'rdCount');"  value="RT"/>&nbsp;Return</th>-->
                    <th>sale Id</th>
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
                    String rdRTId = "RT_"+count;
                    String rmkTxt =  "value(rmk_" + pktIdn + ")" ;
             %>
            <%
            pageList= ((ArrayList)pageDtl.get("RADIOBODY") == null)?new ArrayList():(ArrayList)pageDtl.get("RADIOBODY");            
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
            val_cond=val_cond.replaceAll("SALEID",pkt.getSaleId());%>
            <td nowrap="nowrap"><html:radio property="<%=fld_nme%>" styleId="<%=fld_typ%>"  name="localSaleForm"  onclick="<%=val_cond%>"  value="<%=dflt_val%>"/>
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
                    <td><%=util.nvl(pkt.getValue((String)prps.get(j)))%>
                    <%}if(lprp.equals("CRTWT")){%>
                    <input type="hidden" id="<%=count%>_cts" value="<%=util.nvl(pkt.getValue((String)prps.get(j)))%>" /> 
                    <%}%>
                    </td>
                <%}%>
                <td><%=pkt.getRapRte()%><input type="hidden" id="<%=count%>_rap" value="<%=pkt.getRapRte()%>" /></td>
                <td><%=util.nvl(pkt.getValue("rapVlu"))%></td>
                <td><%=pkt.getDis()%></td>
                <td><%=pkt.getRte()%>
                <input type="hidden" id="<%=count%>_quot" value="<%=pkt.getRte()%>" /> 
                <input type="hidden" id="<%=count%>_fnl" value="<%=pkt.getMemoQuot()%>" />
                </td>
                <td><%=pkt.getByrDis()%></td>
                <td><%=pkt.getMemoQuot()%></td>    
                 <!--<td><html:radio property="<%=sttPrp%>" styleId="<%=rdRTId%>" name="localSaleForm" value="RT"/></td>  --> 
                </tr>
              <%  
                }
            %>
              <input type="hidden" id="rdCount" value="<%=count%>" />
            </table></td></tr>
             <tr><td>
            <p>
                <html:submit property="submit" value="Return" styleClass="submit"/>&nbsp;
                     
            </p></td></tr>
            
            <%}else{%>
           <tr><td> Sorry no result found</td></tr>
          <%}}%>  
            </html:form>
  </table>
</td></tr>
<tr>
    <td><jsp:include page="/footer.jsp" /> </td>
    </tr>
</table>
  
  </body>
</html>