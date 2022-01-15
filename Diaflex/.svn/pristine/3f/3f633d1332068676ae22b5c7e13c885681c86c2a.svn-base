<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
 
<%@ page errorPage="../error_page.jsp" %>
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import="java.util.ArrayList,java.util.HashMap,java.sql.ResultSet,ft.com.*, java.util.Date, java.util.ArrayList, ft.com.dao.*"%>
<jsp:useBean id="util" class="ft.com.JspUtil" scope="page" />
<jsp:useBean id="info" class="ft.com.InfoMgr" scope="session" />
<%@taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>
<%@taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>Buy Back</title>
    <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/bse.js?v=<%=info.getJsVersion()%> " > </script>
<link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/bse.css?v=<%=info.getJsVersion()%>"/>
<script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/ajax.js?v=<%=info.getJsVersion()%> " > </script>
<script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/memoScript.js?v=<%=info.getJsVersion()%> " > </script>

<script language="javaScript" type="text/javascript" src="<%=info.getReqUrl()%>/calendar/calendar.js"></script>
<link href="<%=info.getReqUrl()%>/calendar/calendar.css" rel="stylesheet" type="text/css" />


<link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/style.css?v=<%=info.getJsVersion()%>"/>

  </head>
   <%String logId=String.valueOf(info.getLogId());
     String onfocus="cook('"+logId+"')";
    HashMap allPageDtl= (info.getPageDetails() == null)?new HashMap():(HashMap)info.getPageDetails();
    HashMap pageDtl=(HashMap)allPageDtl.get("BUYBACK_CONFIRMATION");
    ArrayList pageList=new ArrayList();
    HashMap pageDtlMap=new HashMap();
    String fld_nme="",fld_ttl="",val_cond="",dflt_val="",fld_typ="",form_nme="",lov_qry="";
  HashMap dbinfo = info.getDmbsInfoLst();
String cnt = (String)dbinfo.get("CNT");
  String repPath = (String)dbinfo.get("REP_PATH");
int accessidn=Integer.parseInt(util.nvl((String)request.getAttribute("accessidn"),"0"));
   
        %>
 <body onfocus="<%=onfocus%>" onload="MM_preloadImages('images/faunalogo.png')" topmargin="0" leftmargin="0" rightmargin="0" bottommargin="0">
<img src="../images/loadbig.gif" vspace="15" id="load" style="display:none;" class="loadpktDiv" border="0" />

 <% pageList=(ArrayList)pageDtl.get("MEMODTL");
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
  <img src="../images/line.jpg" border="0" /></td><td valign="middle"> <span class="pgHed">Pre Buy Back</span> </td>
</tr></table>
  </td>
  </tr>
  <% if(request.getAttribute("msg")!=null){%>
<tr><td valign="top" class="tdLayout">
<span class="redLabel"><%=request.getAttribute("msg")%></span>
</td></tr><%}%>
<%if(request.getAttribute("memoId")!=null){%>
<tr><td valign="top" class="tdLayout">
 <%if(cnt.equals("xljf")){
        String url1 =repPath+"/reports/rwservlet?"+cnt+"&report="+info.getRportUrl()+"\\reports\\HK_Performa_byback.jsp&p_access="+accessidn+"&P_ID="+request.getAttribute("memoId")+"&destype=CACHE&desformat=pdf";
        String url2 =repPath+"/reports/rwservlet?"+cnt+"&report="+info.getRportUrl()+"\\reports\\Ind_Performa_byback.jsp&p_access="+accessidn+"&P_ID="+request.getAttribute("memoId")+"&destype=CACHE&desformat=pdf";
           %>
           <table><tr>
          <td><a href="<%=url2%>"  target="_blank"><span class="txtLink" >India Invoice sale post</span></a></td>
        <td><a href="<%=url1%>"  target="_blank"><span class="txtLink" >HK Invoice Sale post</span></a></td></tr></table>
<%}%>
</td></tr>
<%}%>
<tr><td valign="top" class="tdLayout">
   <table><tr><td valign="top">
    <html:form action="/marketing/buyBack.do?method=Fetch"  method="POST">
        <table><tr><td valign="top">
    <table class="grid1" >
       
        <tr><th colspan="2">Memo Search </th></tr>
          
            
             <tr>
           <td colspan="2"> <html:radio property="value(memoSrch)" value="ByrSrch"  onclick="DisplayMemoSrch('MS_1')"  styleId="MS_1" />  Memos Search By Buyer </td>
           </tr>
             <tr style="display:none" id="DMS_1"><td colspan="2">
             
              <table cellpadding="5"><tr>
                <td>Buyer</td>
                <td>
                <html:select property="value(byrIdn)" styleId="byrId" onchange="getTrms(this,'SALE')" >
                <html:option value="0">---select---</html:option>
                <html:optionsCollection property="byrLstFetch"  value="byrIdn" label="byrNme" />
                </html:select>
                </td> </tr>
                 <tr>
            <td> <span class="txtBold" >Terms </span></td><td>
             
             <html:select property="value(byrTrm)"  styleId="rlnId" onchange="GetMemoIdn('BB')" >
            <html:option value="0">---select---</html:option>
            <html:optionsCollection property="trmsLst" name="buyBackForm"
            label="trmDtl" value="relId" />
            </html:select>
            </td>
            </tr>
          
                </table>
                
               </td> 
            </tr>
            <tr>
           <td colspan="2"> <html:radio property="value(memoSrch)" value="MemoSrch"    styleId="MS_2" onclick="DisplayMemoSrch('MS_2')"  /> Memos Search By Memo Ids/Packet Code</td>
           </tr>
            <tr style="display:none" id="DMS_2">
            <td>
            <table>
            <tr>
            <td>Memo Ids</td><td><html:text property="value(memoIdn)" name="buyBackForm" styleId="memoid"/></td></tr>
           <tr><td>Packets. </td><td>
            <html:textarea property="value(vnmLst)" name="buyBackForm" styleId="vnmLst" /> </td> </tr>
           </table>
            </td>
            </tr>
            <tr><td><table><tr><td>Type :</td><td>
            <html:select property="value(buytyp)" name="buyBackForm">
            <html:option value="ALL">ALL</html:option>
            <html:option value="IS">Pending</html:option>
            <html:option value="WFP">Wait payment</html:option>
             <html:option value="PR">Payment Recevice</html:option>
            </html:select>
            </td></tr></table> </td></tr>
              <tr><td align="center"><html:submit property="submit" value="View" styleClass="submit" onclick="return validatememosale()" /></td></tr>
  
        </table>
        </td><td valign="top">
        <div id="memoIdn"></div>
        </td></tr></table>
     
    </html:form></td>

     <%if(request.getAttribute("view")!=null ){%>
     <td  valign="top">
    <table class="grid1">
    <tr> <td></td><td><b>Selected(PN)</b></td><td><b>Selected(WFP)</b></td><td><b>Selected(PR)</b></td><td><b>Selected(DLV)</b></td><td><b>Selected(RT)</b></td> </tr>
    <tr> <td><b>Qty</b></td><td><span id="pn_qty"><bean:write property="value(pn_qty)" name="buyBackForm" /></span></td> <td><span id="wfp_qty"><bean:write property="value(wfp_qty)" name="buyBackForm" /></span></td><td><span id="pr_qty"><bean:write property="value(pr_qty)" name="buyBackForm" /></span></td><td><span id="qty">0</span></td><td><span id="rt_qty">0</span></td> </tr>
    <tr> <td><b>Cts</b> </td><td><span id="pn_cts"><bean:write property="value(pn_cts)" name="buyBackForm" /></span></td> <td><span id="wfp_cts"><bean:write property="value(wfp_cts)"  name="buyBackForm" /></span></td> <td><span id="pr_cts"><bean:write property="value(pr_cts)"  name="buyBackForm" /></span></td><td><span id="cts">0</span></td><td><span id="rt_cts">0</span></td>  </tr>
    <tr> <td><b>Avg Prc</b></td><td><span id="pn_avgPrc"><bean:write property="value(pn_avgPrc)" name="buyBackForm" /></span></td> <td><span id="wfp_avgPrc"><bean:write property="value(wfp_avgPrc)"  name="buyBackForm" /></span></td><td><span id="pr_avgPrc"><bean:write property="value(pr_avgPrc)"  name="buyBackForm" /></span></td><td><span id="avgPrc">0</span></td> <td><span id="rt_avgPrc">0</span></td>  </tr>
    <tr> <td><b>Avg Dis</b> </td> <td><span id="pn_avgDis"><bean:write property="value(pn_avgDis)" name="buyBackForm" /></span></td><td><span id="wfp_avgDis"><bean:write property="value(wfp_avgDis)"  name="buyBackForm" /></span></td> <td><span id="pr_avgDis"><bean:write property="value(pr_avgDis)"  name="buyBackForm" /></span></td><td><span id="avgDis">0</span></td><td><span id="rt_avgDis">0</span></td>  </tr>
    <tr> <td><b>Value </b></td><td><span id="pn_vlu"><bean:write property="value(pn_vlu)" name="buyBackForm" /></span></td> <td><span id="wfp_vlu"><bean:write property="value(wfp_vlu)"   name="buyBackForm" /></span></td><td><span id="pr_vlu"><bean:write property="value(pr_vlu)"   name="buyBackForm" /></span></td><td><span id="vlu">0</span></td><td><span id="rt_vlu">0</span></td>   </tr>
  <!--<tr><td><b>Buyer</b></td><td colspan="4"><bean:write property="byr" name="buyBackForm" /></td></tr>-->
   <tr><td><b>Date</b></td><td colspan="4"><bean:write property="dte" name="buyBackForm" /></td></tr>
   <tr><td><b>Type</b></td><td colspan="4"><bean:write property="typ" name="buyBackForm" /></td></tr>
      </table>
      </td>
     
     
      
     <td valign="top">
    <table class="grid1">
    <tr><td colspan="3" align="center"><b>Buyer Details</b></td></tr>
    <tr>
    <td><b>Buyer</b></td><td><bean:write property="byr" name="buyBackForm" /></td>
    </tr>
    <tr>
    <td><b>Email</b></td><td><bean:write property="value(email)" name="buyBackForm" /></td>
    </tr>
        <tr>
    <td><b>Mobile No</b></td><td><bean:write property="value(mobile)" name="buyBackForm" /></td>
    </tr>
        <tr>
    <td><b>Office No</b></td><td><bean:write property="value(office)" name="buyBackForm" /></td>
    </tr>
    </table>
    </td>
    <%}%>

      <td  valign="top">

    </td>
    </tr></table>
   </td></tr>
   <%
   String view = (String)request.getAttribute("view");
   if(view!=null){
   %>
   
    <tr><td valign="top" class="tdLayout">
    
    <%String formAction = "/marketing/buyBack.do?method=save" ; 
    
    %>
     <html:form action="<%=formAction%>" method="POST" onsubmit="return confirmChangesWithPopup()"  >
    <%
    ArrayList pkts = (ArrayList)info.getValue("RTRN_PKTS");
    if( pkts!=null && pkts.size()>0){
    ArrayList prpDspBlocked = info.getPageblockList();
      
    %>
       
     
            <html:hidden property="nmeIdn" name="buyBackForm" />
             <html:hidden property="relIdn" name="buyBackForm" />
            <html:hidden property="typ" name="buyBackForm" />
            <html:hidden property="value(exh_rte)" styleId="exhRte" name="buyBackForm" />
    
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
            if(fld_typ.equals("S")){
            %>
    <html:submit property="<%=fld_nme%>" value="<%=fld_ttl%>" onclick="<%=val_cond%>" styleClass="submit"/>&nbsp;
    <%}else if(fld_typ.equals("B")){%>
    <html:button property="<%=fld_nme%>" value="<%=fld_ttl%>" onclick="<%=val_cond%>" styleClass="submit"/>&nbsp;
    <%}}}
    %></td></tr>
    
    <tr><td>
        <%
     ArrayList chargesLst=(ArrayList)session.getAttribute("chargesLst");
     
     if(chargesLst!=null && chargesLst.size()>0){%>
    <table class="grid1">
     <tr>
    <%for(int i=0;i<chargesLst.size();i++){
    HashMap dtl=new HashMap();
    dtl=(HashMap)chargesLst.get(i);
    String dsc=(String)dtl.get("dsc");
    String autoopt=(String)dtl.get("autoopt");
    String flg=(String)dtl.get("flg");
    String typ=(String)dtl.get("typ");
    String fctr=(String)dtl.get("fctr");
    String fun=(String)dtl.get("fun");
    String rmk=(String)dtl.get("rmk");
    String field = "value("+typ+")";
    String fieldRmk = "value("+typ+"_rmk)";
    String onchang="chargesmanual('"+typ+"','"+i+"')";
    String onchangrmk="chargesmanualrmk('"+typ+"','"+i+"')";
    String fieldId = typ+"_rmk";
    if(flg.equals("MNL")){
    if(i==0){
    %>

    <td><b><bean:write property="value(CH_MEMO)" name="buyBackForm" /></b></td>
    <%}%>
     <td><b><%=dsc%></b></td>
    <td><html:text property="<%=field%>" size="6" styleId="<%=typ%>"  name="buyBackForm"/></td>
    <td>
    <%if(rmk.equals("Y")){%>
    Remark<html:text property="<%=fieldRmk%>" styleId="<%=fieldId%>" onchange="<%=onchangrmk%>" name="buyBackForm" size="10"/>
    <%}%>
    </td>
   
    <%}else{%>
   <td><b><%=dsc%></b>
    <%if(flg.equals("AUTO") && autoopt.equals("OPT")){%>
    <input type="checkbox" name="<%=typ%>_AUTO" id="<%=typ%>_AUTO" checked="checked" onchange="validateAutoOpt('<%=typ%>_AUTO')" title="Uncheck To Optional"/>
    <%}%>
    </td>
    <td><b><span id="<%=typ%>_dis"></span></b></td>
    <td>
    <html:hidden property="<%=field%>"  styleId="<%=typ%>" name="buyBackForm"/>
    <input type="button" name="charge_<%=typ%>" id="charge_<%=typ%>" value="Charge"  onclick="<%=onchang%>" class="submit" />
    </td><td></td>
    <%}}%>
     </tr>
    </table>
    <%}%></td>
   </tr>
                <!--<html:submit property="submit" value="Save"  styleClass="submit"/>&nbsp;
                 <html:submit property="value(consignment)" value="Save & Consignment" onclick="" styleClass="submit"/>&nbsp;
                <html:button property="fullReturn" onclick="SelectRD('RT');calculation('AP_')" value="Full Return" styleClass="submit"/>&nbsp;
                <html:button property="fullApprove" onclick="SelectRD('AP');calculation('AP_')" value="Full Approve" styleClass="submit"/>&nbsp;-->
  <tr><td>
            <label class="pgTtl">Memo Packets</label>
            <%
             String memoIdconQ="";
                ArrayList prps = (info.getValue("MDL_MEMO_RTRN") == null)?new ArrayList():(ArrayList)info.getValue("MDL_MEMO_RTRN");
             %>  
            <table class="grid1">
                <tr>
                    <th>Sr</th>
                   
                    <th>Packet Code</th>
                     <%for(int j=0; j < prps.size(); j++) {
                     String lprp=(String)prps.get(j);
                     if(prpDspBlocked.contains(lprp)){
                     }else{
                     %>
                        <th><%=lprp%></th>
                    <%}}%>
                    <th>RapRte</th>
                    <th>RapVlu</th>
                    <th>Dis</th>
                    <th>Prc / Crt </th>
                    <th>ByrDis</th>
                    <th>Quot</th>
                       <%pageList=(ArrayList)pageDtl.get("RADIOHDR");
            if(pageList!=null && pageList.size() >0){
            for(int j=0;j<pageList.size();j++){
            pageDtlMap=(HashMap)pageList.get(j);
            fld_nme="value("+(String)pageDtlMap.get("fld_nme")+")";
            fld_ttl=(String)pageDtlMap.get("fld_ttl");
            fld_typ=(String)pageDtlMap.get("fld_typ");
            dflt_val=(String)pageDtlMap.get("dflt_val");
            val_cond=(String)pageDtlMap.get("val_cond"); %>
            <th><html:radio property="<%=fld_nme%>" styleId="<%=fld_typ%>" name="buyBackForm" onclick="<%=val_cond%>"  value="<%=dflt_val%>"/><%=fld_ttl%> </th>
            <%}}%>
                </tr>
            <%
               
                //(info.getValue(reqIdn+ "_PKTS") == null)?new ArrayList():(ArrayList)info.getValue(reqIdn+ "_PKTS");
               int count = 0;
                for(int i=0; i < pkts.size(); i++) {%>
                <tr>
                <td><%=(i+1)%></td>
                <%    
                    count=i+1;
                    PktDtl pkt = (PktDtl)pkts.get(i);
                    long pktIdn = pkt.getPktIdn();
                    String cbPrp = "value(upd_" + pktIdn + ")";
                    String sttPrp = "value(stt_" + pktIdn + ")" ;
                    String rmkTxt =  "value(rmk_" + pktIdn + ")" ;
                   
                    String fnlsal=util.nvl(pkt.getMemoQuot(),pkt.getRte());
                     memoIdconQ+=","+util.nvl(pkt.getMemoId());
                   
                %>
               <td><%=pkt.getVnm()%></td>
                <%for(int j=0; j < prps.size(); j++) {
                String lprp = (String)prps.get(j);
                if(prpDspBlocked.contains(lprp)){
                }else{
                %>
                <td><%=util.nvl(pkt.getValue((String)prps.get(j)))%>
                <%}if(lprp.equals("CRTWT")){%>
                <input type="hidden" id="<%=count%>_cts" value="<%=util.nvl(pkt.getValue((String)prps.get(j)))%>" /> 
                <%}%></td>
                <%}
                %>
                <td><%=pkt.getRapRte()%> <input type="hidden" id="<%=count%>_rap" value="<%=pkt.getRapRte()%>" /> </td>
                <td><%=util.nvl(pkt.getValue("rapVlu"))%></td>
                <td><%=pkt.getDis()%></td>
                <td><%=pkt.getRte()%>  <input type="hidden" id="<%=count%>_quot" value="<%=pkt.getRte()%>" /> 
                <input type="hidden" id="<%=count%>_fnl" value="<%=fnlsal%>" /> 
                </td>
                <td><%=pkt.getByrDis()%></td>
                <td><%=pkt.getMemoQuot()%></td>
            <%
            String lStt = pkt.getStt();
            pageList=(ArrayList)pageDtl.get("RADIOBODY");
            if(pageList!=null && pageList.size() >0){
            for(int j=0;j<pageList.size();j++){
            pageDtlMap=(HashMap)pageList.get(j);
            fld_nme="value("+(String)pageDtlMap.get("fld_nme")+""+pktIdn+")";
            fld_ttl=(String)pageDtlMap.get("fld_ttl");
            fld_typ=(String)pageDtlMap.get("fld_typ")+count;
            dflt_val=util.nvl((String)pageDtlMap.get("dflt_val")).trim();
            if(dflt_val.equals("PKT"))
            dflt_val=String.valueOf(pktIdn);
            dflt_val=dflt_val.replaceAll("PKTIDN",String.valueOf(pktIdn));
            val_cond=(String)pageDtlMap.get("val_cond");
            val_cond=val_cond.replaceAll("PKTIDN",String.valueOf(pktIdn));
            %>
            <td nowrap="nowrap">
            <%if(lStt.equals("IS") && dflt_val.indexOf("DLV")!=-1){%>
            <html:radio property="<%=fld_nme%>" styleId="<%=fld_typ%>" name="buyBackForm" disabled="true"  onclick="<%=val_cond%>"  value="<%=dflt_val%>"/>
            <%}else{%>
             <html:radio property="<%=fld_nme%>" styleId="<%=fld_typ%>" name="buyBackForm" onclick="<%=val_cond%>"  value="<%=dflt_val%>"/>
           
            <%}%>
            <%if(dflt_val.equals("RT")){%>
            <html:text property="<%=rmkTxt%>" size="10"  />
            <%}%>
            </td>
            <%}}%>
                </tr>
              <%  
                }
                  memoIdconQ=memoIdconQ.replaceFirst("\\,", "");
            %>
              <html:hidden property="value(memoIdn)"  name="buyBackForm" styleId="memoIdnlst" value="<%=memoIdconQ%>"  />
            <input type="hidden" id="rdCount" value="<%=count%>" />
            
            </table>
            </td></tr>
       </table>
    <%}else{%>
   Sorry no result found
  <%  }
     %>
    
     </html:form>
    </td></tr>
    <%}%>
     
    <tr>
    <td><jsp:include page="/footer.jsp" /> </td>
    </tr>
    </table>
  
  
  </body>
</html>