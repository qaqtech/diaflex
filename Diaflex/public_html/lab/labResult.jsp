<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page errorPage="../error_page.jsp" %>
<%@ page contentType="text/html;charset=windows-1252"%>
<jsp:useBean id="info" class="ft.com.InfoMgr" scope="session" />
<jsp:useBean id="util" class="ft.com.JspUtil" scope="page" />
<jsp:useBean id="gtMgr" class="ft.com.GtMgr" scope="session" />

<%@ page import="java.util.ArrayList,java.util.HashMap,java.util.LinkedHashMap,java.util.Set,java.sql.ResultSet,ft.com.*, java.util.Date, java.util.ArrayList, ft.com.dao.*"%>
<%@taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html;charset=windows-1252"/>
    <title>Lab Result</title>
    <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/bse.css?v=<%=info.getJsVersion()%>"/>
    <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/general.css?v=<%=info.getJsVersion()%>"/>
     <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/style.css?v=<%=info.getJsVersion()%>"/>
        <script src="../scripts/ajax.js" type="text/javascript"></script>
            <script src="<%=info.getReqUrl()%>/scripts/assortScript.js?v=<%=info.getJsVersion()%>" type="text/javascript"></script>
       <script language="javaScript" type="text/javascript" src="<%=info.getReqUrl()%>/calendar/calendar.js"></script>
   <link href="<%=info.getReqUrl()%>/calendar/calendar.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/bse.js?v=<%=info.getJsVersion()%> " > </script>
     <script type="text/javascript" src="<%=info.getReqUrl()%>/PopScripts/popup.js?v=<%=info.getJsVersion()%> " > </script>
   <script src="<%=info.getReqUrl()%>/PopScripts/jquery-1.2.6.min.js" type="text/javascript"></script>

  </head>
        <%String logId=String.valueOf(info.getLogId());
        String onfocus="cook('"+logId+"')";
        %>
 <body onfocus="<%=onfocus%>" onload="MM_preloadImages('images/faunalogo.png')" topmargin="0" leftmargin="0" rightmargin="0" bottommargin="0">
  <input type="hidden" id="reqUrl" name="reqUrl" value="<%=info.getReqUrl()%>"/>
  <div id="backgroundPopup"></div>
<div id="popupContactASSFNL">
<table class="popUpTb">
 <tr><td height="5%">&nbsp;&nbsp;&nbsp;<html:button property="value(close)" styleClass="submit"  onclick="MKEmptyPopup('SC');disablePopupASSFNL();selectRecheck();" value="Close"  /> </td></tr>
<tr><td valign="top" height="95%">
<iframe name="SC" class="frameStyle" align="middle" frameborder="0">

</iframe></td></tr>
</table>
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
  <img src="../images/line.jpg" border="0" /></td><td valign="middle"> <span class="pgHed">Lab Result</span> </td>
</tr></table>
  </td>
  </tr>
   <%
    ArrayList error = (ArrayList)request.getAttribute("errorList");
      if(error!=null && error.size() > 0){
   %>
  <!-- tr for cts and total -->
  
   
   <tr> <td valign="top" class="tdLayout">
   <table>
   <%for(int i=0;i<error.size();i++){%>
   <tr><td><span class="redLabel"> <%=error.get(i)%></span></td></tr>
   <%}%>
   </table>
   </td></tr>
   <%}%>
    <%
    HashMap mprp = info.getMprp();
    HashMap prpList = info.getPrp();
    HashMap dbmsSysInfo = info.getDmbsInfoLst();
    String col = (String)dbmsSysInfo.get("COL");
     String clr = (String)dbmsSysInfo.get("CLR");
          String cut = util.nvl((String)dbmsSysInfo.get("CUT"));
     String symval = (String)dbmsSysInfo.get("SYM");
     String polval = (String)dbmsSysInfo.get("POL");
     String flval = (String)dbmsSysInfo.get("FL");
    String vnmNtFnd = (String)request.getAttribute("vnmNotFnd");
HashMap allPageDtl= (info.getPageDetails() == null)?new HashMap():(HashMap)info.getPageDetails();
HashMap pageDtl=(HashMap)allPageDtl.get("LAB_RESULT");
    ArrayList pageList=new ArrayList();
    HashMap pageDtlMap=new HashMap();
    String fld_nme="",fld_ttl="",val_cond="",dflt_val="",fld_typ="",form_nme="",lov_qry="";
     if(vnmNtFnd!=null){%>
    <tr><td valign="top" class="tdLayout"><span class="redLabel"><%=vnmNtFnd%></span></td></tr>
   <%}%>
   <tr>
  <td valign="top" class="tdLayout">
  <table>
  <tr><td valign="top">
  <html:form action="lab/labResult.do?method=fetch" method="post">
  
  
 <table  class="grid1">
  <tr><th colspan="2">&nbsp;</th></tr>
  <tr><td align="center">Report Type </td><td>
  <%  pageList= ((ArrayList)pageDtl.get("LABRPTTYP") == null)?new ArrayList():(ArrayList)pageDtl.get("LABRPTTYP");
     if(pageList!=null && pageList.size() >0){%>
     <html:select property="value(reportTyp)" name="labResultForm">
    <% for(int j=0;j<pageList.size();j++){
             pageDtlMap=(HashMap)pageList.get(j);
             fld_nme=(String)pageDtlMap.get("fld_nme");
             fld_ttl=(String)pageDtlMap.get("fld_ttl");
             %>
          <html:option value="<%=fld_nme%>"><%=fld_ttl%></html:option>    
    <% }%>
     </html:select>
     <%}else{%>
  
  <html:select property="value(reportTyp)" name="labResultForm">
  <html:option value="NONE">None</html:option>
  <html:option value="OK">Color And Clarity OK</html:option>
  <html:option value="UP">Upgrades</html:option>
  <html:option value="CCDN">Color or Clarity Downgrade </html:option>
  <html:option value="OTHDN">Others Downgrade</html:option>
  </html:select>
  <%}%>
  </td> </tr>
  <tr><td>Lab</td><td>
   <html:select property="value(labIdn)"  styleId="labIdn"  name="labResultForm"   >
         
            <html:optionsCollection property="labList" name="labResultForm" 
            label="labDesc" value="labVal" />
    </html:select>
  </td></tr>
  <tr><td colspan="2"><table><tr>
  <td align="center" colspan="2">Sequence No </td><td colspan="3">
    <html:text property="value(seq)" name="labResultForm" />
   </td></tr>
   <tr>
   <td colspan="5">Or</td></tr>
   <tr>
   <td>Date</td><td>From</td><td> <html:text property="value(frmdte)" styleId="frmdte" name="labResultForm" /><img src="<%=info.getReqUrl()%>/images/calender.png" onClick="setYears(2011, <%=info.getCurrentYear()%>);showCalender(this, 'frmdte');"> </td>
   <td>To</td><td><html:text property="value(todte)" styleId="todte" name="labResultForm" /> <img src="<%=info.getReqUrl()%>/images/calender.png" onClick="setYears(2011, <%=info.getCurrentYear()%>);showCalender(this, 'todte');"></td>
   </tr>
   <tr>
   <td colspan="5">Or</td></tr>
   <tr>
   <tr>
  <td align="center" colspan="2">Issue Id </td><td colspan="3">
    <html:text property="value(issue)" name="labResultForm" />
   </td></tr>
   </table></td>
   </tr>
   <tr>
       <%
                    pageList= ((ArrayList)pageDtl.get("LABSTATUS") == null)?new ArrayList():(ArrayList)pageDtl.get("LABSTATUS");
         if(pageList!=null && pageList.size() >0){
           for(int j=0;j<pageList.size();j++){
             pageDtlMap=(HashMap)pageList.get(j);
             fld_nme="value("+(String)pageDtlMap.get("fld_nme")+")";
             fld_ttl=(String)pageDtlMap.get("fld_ttl");
             val_cond=(String)pageDtlMap.get("val_cond"); %>
            <td><%=fld_ttl%> </td>
      <%}}%>
           <td>
            <%
                    pageList= ((ArrayList)pageDtl.get("LABSELECT") == null)?new ArrayList():(ArrayList)pageDtl.get("LABSELECT");            
             if(pageList!=null && pageList.size() >0){%>
             <html:select property="value(labStt)" styleId="labStt" name="labResultForm" >
             <%
               for(int j=0;j<pageList.size();j++){
                 pageDtlMap=(HashMap)pageList.get(j);
                 fld_nme="value("+(String)pageDtlMap.get("fld_nme")+")";
                 fld_ttl=(String)pageDtlMap.get("fld_ttl");
                 val_cond=(String)pageDtlMap.get("val_cond");
                 dflt_val=(String)pageDtlMap.get("dflt_val");
                 %>
                <html:option value="<%=dflt_val%>"><%=fld_ttl%></html:option>
             <%}%>
            </html:select>
            <%}%>
           </td>
   </tr>
    <tr>
       <%
        pageList= ((ArrayList)pageDtl.get("LABSERVICE") == null)?new ArrayList():(ArrayList)pageDtl.get("LABSERVICE");
         if(pageList!=null && pageList.size() >0){
           for(int j=0;j<pageList.size();j++){
             pageDtlMap=(HashMap)pageList.get(j);
             fld_nme="value("+(String)pageDtlMap.get("fld_nme")+")";
             fld_ttl=(String)pageDtlMap.get("fld_ttl");
             val_cond=(String)pageDtlMap.get("val_cond"); %>
            <td><%=fld_ttl%> </td>
      <%}
       ArrayList prpPrt = (ArrayList)prpList.get("SERVICEP");
       ArrayList prpVal = (ArrayList)prpList.get("SERVICEV");
       if(prpPrt!=null && prpPrt.size() >0){%>
   <td> <html:select property="value(SVR)"  >
    <html:option value="0" >--select--</html:option>
   <%for(int k=0; k < prpPrt.size() ; k++){
   String fldVal = (String)prpVal.get(k);
   String fldPrt = (String)prpVal.get(k);%>
    <html:option value="<%=fldVal%>" ><%=fldVal%></html:option>
     <%}%></html:select></td>
    <%}}%>
    <tr><td colspan="2">
    <table class="grid1">
    <tr><th>Generic Search</th><th> Packets</th></tr>
    <tr><td>
    <jsp:include page="/genericSrch.jsp" /> </td> <td valign="top">
  <html:textarea property="value(vnmLst)" rows="10" name="labResultForm" /></td></tr></table>
  </td>
     </tr>
  
 <tr><td align="center" colspan="2"><html:submit property="view" value="View" styleClass="submit"/> 
  </td>
  </tr>
  
  </table>
  </html:form></td><td valign="top">
 
  <%
  ArrayList genViewPrp = (session.getAttribute("LAB_UP_DW") == null)?new ArrayList():(ArrayList)session.getAttribute("LAB_UP_DW"); 
 
  HashMap colClrUPDWMap = (HashMap)request.getAttribute("colClrUPDWMap");
  if(colClrUPDWMap!=null && colClrUPDWMap.size()>0){
       %>
<table class="grid1">
 <tr><th>Propety</th><th>+</th><th>-</th><th>=</th></tr> 
 <%for(int i=0;i<genViewPrp.size();i++){
 String prpVal=util.nvl((String)genViewPrp.get(i));
 int ttlprp = Integer.parseInt(util.nvl((String)colClrUPDWMap.get(prpVal+"_+"),"0")) +  Integer.parseInt(util.nvl((String)colClrUPDWMap.get(prpVal+"_-"),"0")) + Integer.parseInt(util.nvl((String)colClrUPDWMap.get(prpVal+"_="),"0"));  
 %>
 <tr> <th><%=util.nvl((String)mprp.get(prpVal+"D"),prpVal)%></th>
 <td>
 <a title="Packet Details" target="pktDtl"  href="labResult.do?method=pktDtl&lprp=<%=prpVal%>&sign=P" >
 <%=util.nvl((String)colClrUPDWMap.get(prpVal+"_+"),"0")%></a></td>
 <td>
  <a title="Packet Details" target="pktDtl"  href="labResult.do?method=pktDtl&lprp=<%=prpVal%>&sign=M" >
 <%=util.nvl((String)colClrUPDWMap.get(prpVal+"_-"),"0")%></a></td>
 <td>
   <a title="Packet Details" target="pktDtl"  href="labResult.do?method=pktDtl&lprp=<%=prpVal%>&sign=E" >
 <%=util.nvl((String)colClrUPDWMap.get(prpVal+"_="),"0")%></a></td>
 </tr>
 <tr> <th> % </th><td><b><%=Math.round((Float.parseFloat(util.nvl((String)colClrUPDWMap.get(prpVal+"_+"),"0"))/ttlprp)*100)%>%</b> </td><td><b><%=Math.round((Float.parseFloat(util.nvl((String)colClrUPDWMap.get(prpVal+"_-"),"0"))/ttlprp)*100)%>%</b></td><td><b><%=Math.round((Float.parseFloat(util.nvl((String)colClrUPDWMap.get(prpVal+"_="),"0"))/ttlprp)*100)%>%</b></td></tr>
 <%}%>
</table>
 <%}%>
 
  </td>
   </tr>
  </table>
  
  </td></tr>
    <%
    String plusCom = "";
    pageList= ((ArrayList)pageDtl.get("PLUSMNCOM") == null)?new ArrayList():(ArrayList)pageDtl.get("PLUSMNCOM");
         if(pageList!=null && pageList.size() >0){
          pageDtlMap=(HashMap)pageList.get(0);
          plusCom=util.nvl((String)pageDtlMap.get("dflt_val")); 
          if(plusCom.equals(""))
            plusCom="N";
          }
    
   String view = (String)request.getAttribute("view");
   String ctwt=null;
   if(view!=null){
     ArrayList issRtnComVw = (session.getAttribute("ISS_RTN_COM") == null)?new ArrayList():(ArrayList)session.getAttribute("ISS_RTN_COM"); 
  issRtnComVw.add(col);
     issRtnComVw.add(clr);
    issRtnComVw.add(cut);
    issRtnComVw.add(polval);
    issRtnComVw.add(symval);
    issRtnComVw.add(flval);
   String lstNme = (String)gtMgr.getValue("lstNmeRLT");
   HashMap stockListMap = (HashMap)gtMgr.getValue(lstNme);
 
   if(stockListMap!=null && stockListMap.size() >0){
    String target ="";
   ArrayList prpDspBlocked = info.getPageblockList();
    ArrayList vwPrpLst = (ArrayList)session.getAttribute("LabViewLst");
    ArrayList labList = (ArrayList)request.getAttribute("labList");
    String lab = util.nvl((String)request.getAttribute("lab"),"GIA");
     HashMap totals = (HashMap)gtMgr.getValue(lstNme+"_TTL");
    ArrayList options = (ArrayList)request.getAttribute("OPTIONS");
    String prcId = util.nvl((String)request.getAttribute("prcId"));
    String openPopUp = "openPopUp('assortReturn.do?method=Openpop&prcID="+prcId+"' ,'SC','multiprp')";
    HashMap labresultVaration =((HashMap)new JspUtil().getFromMem(info.getMem_ip(),info.getMem_port(),info.getCnt()+"_labresultVaration") == null)?new HashMap():(HashMap)new JspUtil().getFromMem(info.getMem_ip(),info.getMem_port(),info.getCnt()+"_labresultVaration"); 
    if(options == null)
        options = new ArrayList();
  
    
    ArrayList prpPrt = (ArrayList)prpList.get("SERVICEP");
    ArrayList prpVal = (ArrayList)prpList.get("SERVICEV");
    int sr = 0;
   %>
   
   <tr><td valign="top" class="tdLayout">
   <table>
   <tr><td>
     Total :&nbsp;&nbsp;
     </td>
     <td><span id="ttlqty"><%=totals.get("qty")%></span> &nbsp;&nbsp;</td> 
     <td>cts&nbsp;&nbsp;</td>
     <td><span id="ttlcts"><%=totals.get("cts")%></span> &nbsp;&nbsp;</td>
     <td>Selected:&nbsp;&nbsp;</td>
     <td> Total :&nbsp;&nbsp;</td>
     <td><span id="qtyTtl">0&nbsp;&nbsp;</span></td>
     <td>Cts&nbsp;&nbsp;</td>
     <td><span id="ctsTtl">0</span>
  </td>
  
  </tr>
   </table>
   </td></tr>
   
  
   <html:form action="lab/labResult.do?method=save"   method="post">
   
   <html:hidden property="value(lab)" styleId="labId" name="labResultForm"/>
    <tr> <td valign="top" class="tdLayout">
    <table><tr>
    <%
        pageList= ((ArrayList)pageDtl.get("SUBMIT") == null)?new ArrayList():(ArrayList)pageDtl.get("SUBMIT");    
     if(pageList!=null && pageList.size() >0){
       for(int j=0;j<pageList.size();j++){
         pageDtlMap=(HashMap)pageList.get(j);
         fld_nme="value("+(String)pageDtlMap.get("fld_nme")+")";
         fld_ttl=(String)pageDtlMap.get("fld_ttl");
         val_cond=(String)pageDtlMap.get("val_cond"); %>
        <td><html:submit property="<%=fld_nme%>" value="<%=fld_ttl%>"  onclick="<%=val_cond%>" styleClass="submit" /> </td>

 <%
}}
pageList= ((ArrayList)pageDtl.get("BUTTON") == null)?new ArrayList():(ArrayList)pageDtl.get("BUTTON");    
     if(pageList!=null && pageList.size() >0){
       for(int j=0;j<pageList.size();j++){
         pageDtlMap=(HashMap)pageList.get(j);
         fld_nme="value("+(String)pageDtlMap.get("fld_nme")+")";
         fld_ttl=(String)pageDtlMap.get("fld_ttl");
         val_cond=(String)pageDtlMap.get("val_cond"); %>
        <td><html:button property="<%=fld_nme%>" value="<%=fld_ttl%>"  onclick="<%=val_cond%>" styleClass="submit" /> </td>

 <%
}}
%>
<%
  ArrayList rolenmLst=(ArrayList)info.getRoleLst();
  String usrFlg=util.nvl((String)info.getUsrFlg());
  if(usrFlg.equals("SYS") || rolenmLst.contains("ADMIN")){
  %>
  <td><img src="../images/edit.jpg" title="Edit Properties Module" onclick="newWindow('<%=info.getReqUrl()%>/dynamicProperty.do?method=load&mdl=LAB_VIEW&sname=LabViewLst&par=A')" border="0" width="15px" height="15px"/> </td>
  <%}%>
 </tr></table>
   </td></tr>
     
   <tr> <td valign="top" class="tdLayout">
   <table><tr><td valign="top">
   <table class="grid1" id="dataTable">
   <tr> <th>Sr</th>
    <th>Save <input type="checkbox" name="checkAll" id="checkAll"  onclick="checkALLLAB('CHK_','count')"  /> </th>

   <th>Excel<input type="checkbox" name="checkAll" id="checkAllEXC"    onclick="checkALLLabResult('EXC_',this,'count');excelList(this,'ALL')"/> </th>
  <th><input type="radio" id="RS" name="sttth" onclick="ALLRedio('RS','RS_')"/>RS  </th>
<th> <input type="radio" id="RI" name="sttth" onclick="ALLRedio('RI','RI_')"/>RI</th>
<th> 
<%
pageList= ((ArrayList)pageDtl.get("RADIOHDR") == null)?new ArrayList():(ArrayList)pageDtl.get("RADIOHDR");
            if(pageList!=null && pageList.size() >0){
            for(int j=0;j<pageList.size();j++){
            pageDtlMap=(HashMap)pageList.get(j);
            fld_nme=(String)pageDtlMap.get("fld_nme");
            fld_ttl=(String)pageDtlMap.get("fld_ttl");
            fld_typ=(String)pageDtlMap.get("fld_typ");
            dflt_val=(String)pageDtlMap.get("dflt_val");
            val_cond=(String)pageDtlMap.get("val_cond");
            lov_qry=(String)pageDtlMap.get("lov_qry");
            %>
        
        <input type="radio" id="<%=fld_typ%>" name="<%=fld_nme%>" onclick="<%=val_cond%>"/><%=fld_ttl%>
    <%if(lov_qry.equals("Y")){%>
    <%
String prpFld = "value(SV)";;
String chksrvAll = "chksrvAll('SV')";
String selectALL = "SV";
if(prpPrt!=null && prpPrt.size() >0){
%>
<html:select  property="<%=prpFld%>" styleId="<%=selectALL%>" name="labResultForm" onchange="<%=chksrvAll%>" >
<html:option value="" >--select--</html:option>
<%for(int k=0; k < prpPrt.size() ; k++){
String fldVal = (String)prpVal.get(k);
String fldPrt = (String)prpVal.get(k);
%>
<html:option value="<%=fldVal%>" ><%=fldVal%></html:option>
<%}%>
</html:select>
<%}%>
    <%}
    }}
    %>
  </th>                      
                        

<th>View</th>
   
        <th nowrap="nowrap">Packet No.</th>
        <%for(int j=0; j < vwPrpLst.size(); j++ ){
    String prp = (String)vwPrpLst.get(j);
    if(prpDspBlocked.contains(prp)){
    }else{
     String hdr = (String)mprp.get(prp);
    String prpDsc = (String)mprp.get(prp+"D");
    if(hdr == null) {
        hdr = prp;
       }  

%>
<%if(issRtnComVw.contains(prp)){%>
<th title="<%=prpDsc%>">Issue Val  (<%=hdr%>)</th>
<%}%>
<th title="<%=prpDsc%>"><%=hdr%></th>
<%
}
}%>   




</tr>
 <%
 int colSpan = vwPrpLst.size()+9;
 HashMap colClrMap = (HashMap)request.getAttribute("ColClrMap");
 ArrayList stockIdnLst =new ArrayList();
   LinkedHashMap stockList = new LinkedHashMap(stockListMap); 
 Set<String> keys = stockList.keySet();
        for(String key: keys){
       stockIdnLst.add(key);
        }
 for(int i=0; i < stockIdnLst.size(); i++ ){
 boolean isRSDisbled = false;
 boolean isOBDisbled = false;
 boolean isSaveCheckDis = false;
 String stkIdn = (String)stockIdnLst.get(i);
 GtPktDtl stockPkt = (GtPktDtl)stockList.get(stkIdn);
 String stt = (String)stockPkt.getValue("stt");
 String sttCheck =(String)stockPkt.getValue("sttChk");
String vnm = (String)stockPkt.getValue("vnm");
 String targetHis = "HIS_"+stkIdn;
 if(stt.equals("LB_RS"))
  isOBDisbled = true;
 if(stt.equals("LB_RC"))
  isRSDisbled = true;
  if(stt.equals("LB_RI"))
  isRSDisbled = true;
  if(labList.contains(stkIdn))
  isSaveCheckDis = true;
 
 sr = i+1;
String checkFldId = "CHK_"+sr;
String checkExcId = "EXC_"+sr;
String checkFldVal="value(EXC_"+stkIdn+")";
String sttPrp = "value(stt_"+stkIdn+")";
String labCmp =  "value("+stkIdn+ ")";
 String onchange="selChkbox(this,"+ctwt+")";
 String rdRSId = "RS_"+stkIdn;
 String rdRCId = "RC_"+stkIdn;
 String rdOBId = "OB_"+stkIdn;
 String rdCFId = "CF_"+stkIdn;
 String rdRIId = "RI_"+stkIdn;
 String rdCFRSId = "CFRS_"+stkIdn;
 target = "TR_"+stkIdn;
 %>
 
  <%

 String cts = (String)stockPkt.getValue("cts");
 String onclick = "LabTotalCal(this,'"+cts+"','','"+stkIdn+"');excelList(this,'"+stkIdn+"')"; 
 sr = i+1;

 %>
<tr id="<%=target%>"><td><%=sr%><input type="hidden" id="CTS_<%=stkIdn%>"  value="<%=cts%>" /></td>

<td><html:checkbox property="<%=labCmp%>" disabled="<%=isSaveCheckDis%>" styleId="<%=checkFldId%>" value="yes" /> </td>

<td><html:checkbox property="<%=checkFldVal%>"  styleId="<%=checkExcId%>" name="labResultForm" onclick="<%=onclick%>"  value="yes"  /> </td>
<td><html:radio property="<%=sttPrp%>" name="labResultForm" styleId="<%=rdRSId%>" disabled="<%=isRSDisbled%>" value="LB_RS"/> </td>
<td><html:radio property="<%=sttPrp%>" name="labResultForm" styleId="<%=rdRIId%>"  value="LB_RI"/> </td>
<td><table class="wrap"><tr><td>
<%
pageList= ((ArrayList)pageDtl.get("RADIOBODY") == null)?new ArrayList():(ArrayList)pageDtl.get("RADIOBODY");
            if(pageList!=null && pageList.size() >0){
            for(int j=0;j<pageList.size();j++){
            pageDtlMap=(HashMap)pageList.get(j);
            form_nme=(String)pageDtlMap.get("form_nme");
            fld_nme="value("+(String)pageDtlMap.get("fld_nme")+""+stkIdn+")";
            fld_ttl=(String)pageDtlMap.get("fld_ttl");
            fld_typ=(String)pageDtlMap.get("fld_typ")+""+stkIdn;
            dflt_val=(String)pageDtlMap.get("dflt_val");
            val_cond=(String)pageDtlMap.get("val_cond"); %>
        
        <td><html:radio property="<%=fld_nme%>" name="<%=form_nme%>" styleId="<%=fld_typ%>"  value="<%=dflt_val%>"/></td><td><%=fld_ttl%></td>
          <%}}
    %>       
<td> 
<%
String servcieFld = "value(SV_" +stkIdn+ ")";
String selectApp = "SV_"+stkIdn;
if(prpPrt!=null && prpPrt.size() >0){
%>
<html:select property="<%=servcieFld%>"  styleId="<%=selectApp%>">
<html:option value="" >--select--</html:option>
<%for(int k=0; k < prpPrt.size() ; k++){
String fldVal = (String)prpVal.get(k);
String fldPrt = (String)prpVal.get(k);
%>
<html:option value="<%=fldVal%>" ><%=fldVal%></html:option>
<%}%>
</html:select>
<%}%></td></tr></table>
</td>
<td>
<%if(!isSaveCheckDis){%>
<a href="labResult.do?method=viewRS&mstkIdn=<%=stkIdn%>&stt=<%=stt%>&vnm=<%=vnm%>&lab=<%=lab%>" id="LNK_<%=stkIdn%>" onclick="loadASSFNL('<%=target%>','LNK_<%=stkIdn%>')"  target="SC" >View</a>
<%}%>
</td>


<td><%=stockPkt.getValue("vnm")%></td>
<%
  
for(int j=0; j < vwPrpLst.size(); j++ ){
    String prp = (String)vwPrpLst.get(j);  
    if(prpDspBlocked.contains(prp)){
   }else{
    %>
     <%
     String cellBG="";
     
     if(issRtnComVw.contains(prp)){
     String issVal = util.nvl((String)colClrMap.get(stkIdn+"_"+prp));
     if(!issVal.equals("")){
        String rtnVal = (String)stockPkt.getValue(prp);
       String issValCmp = util.nvl((String)colClrMap.get(stkIdn+"_"+prp));
        String rtnValCmp = util.nvl((String)stockPkt.getValue(prp));
        if(prp.equals(cut) || prp.equals(symval) || prp.equals(polval)) {
        if(plusCom.equals("N")){
             issValCmp = issValCmp.replaceAll("\\+","");
            issValCmp = issValCmp.replaceAll("\\-","");
            issValCmp = issValCmp.replace('1',' ');
            issValCmp = issValCmp.replace('2',' ');
            issValCmp = issValCmp.replace('3',' ');
            issValCmp = issValCmp.replace('4',' ');
            
            
            rtnValCmp = rtnValCmp.replaceAll("\\+","");
            rtnValCmp = rtnValCmp.replaceAll("\\-","");
            rtnValCmp = rtnValCmp.replace('1',' ');
            rtnValCmp = rtnValCmp.replace('2',' ');
            rtnValCmp = rtnValCmp.replace('3',' ');
            rtnValCmp = rtnValCmp.replace('4',' ');
           
            }
            issValCmp = issValCmp.trim();
             rtnValCmp = rtnValCmp.trim();
           }else{ 
              if(plusCom.equals("N")){
             String issstr = issValCmp.substring(issValCmp.length()-1, issValCmp.length());
           while(issstr.equals("+") || issstr.equals("-")){
           issValCmp = issValCmp.substring(0,issValCmp.length()-1);
           issstr = issValCmp.substring(issValCmp.length()-1, issValCmp.length());
          }
           
         String rtnstr = rtnValCmp.substring(rtnValCmp.length()-1, rtnValCmp.length());
           while(rtnstr.equals("+") || rtnstr.equals("-")){
           rtnValCmp = rtnValCmp.substring(0,rtnValCmp.length()-1);
           rtnstr = rtnValCmp.substring(rtnValCmp.length()-1, rtnValCmp.length());
          }}
           issValCmp = issValCmp.trim();
           rtnValCmp = rtnValCmp.trim();
        }
    ArrayList prpSrtLst = (ArrayList)prpList.get(prp+"S");
    ArrayList prpValLst =(ArrayList)prpList.get(prp+"V");
    double issSrt=0;
    double rtnSrt=0;
    if(prpValLst!=null){    
     if(!issVal.equals("NA")){
     int indexissValCmp=0;
     int indexrtnValCmp=0;
     
     if(prpValLst.indexOf(issValCmp)!=-1){
     indexissValCmp=prpValLst.indexOf(issValCmp);
     issSrt  = Integer.parseInt((String)prpSrtLst.get(indexissValCmp));
     }
     if(prpValLst.indexOf(rtnValCmp)!=-1){
     indexrtnValCmp=prpValLst.indexOf(rtnValCmp);
     rtnSrt = Integer.parseInt((String)prpSrtLst.get(indexrtnValCmp));
     }
     
     if(issSrt!=0 && rtnSrt!=0){
     if(issSrt < rtnSrt){
      cellBG="background-color: Red;";
//      if(indexissValCmp!=(indexrtnValCmp-1) && plusCom.equals("N"))
//      cellBG="background-color: yellow;";
      }
     if(issSrt > rtnSrt ){
      cellBG ="background-color: Lime;";
//      if(indexrtnValCmp!=(indexissValCmp-1)  && plusCom.equals("N"))
//      cellBG="background-color: yellow;";
     }
     }}
     }else{
     issSrt=Double.parseDouble(util.nvl(issValCmp,"0"));
     rtnSrt=Double.parseDouble(util.nvl(rtnValCmp,"0"));
     String varationPt=util.nvl((String)labresultVaration.get(prp));
     if(!varationPt.equals("")){
     double varation=Double.parseDouble(varationPt);
     if(issSrt!=0 && rtnSrt!=0){
     double varationfrm=issSrt-varation;
     double varationto=issSrt+varation;
     if(rtnSrt >=varationfrm && rtnSrt <=varationto ){
     issSrt=rtnSrt;
     }else{
     cellBG="background-color: royalblue;";
     }
     }
     }
     }
     }%>
      <td nowrap="nowrap" style="<%=cellBG%>"><%=util.nvl((String)colClrMap.get(stkIdn+"_"+prp))%></td>
     <%}%>
       <td nowrap="nowrap" style="<%=cellBG%>"><%=stockPkt.getValue(prp)%></td>
   <%
  }}%>
  
  

</tr>

   
   
   <%}%>
   </table>
   </td></tr></table>
   </td></tr>
   <input type="hidden" name="count" id="count" value="<%=sr%>" />
     
   
   </html:form>
   <%}else{%>
  <tr><td valign="top" class="hedPg"> Sorry no result found</td></tr>
   <%}%>
  <% }%>
  
  
  
  
  
  <tr>
    <td><jsp:include page="/footer.jsp" /> </td>
    </tr>
  </table>
  <%@include file="../calendar.jsp"%>
  </body>
</html>
   
