
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
    <title>Stock Tally Return</title>
    <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/bse.css?v=<%=info.getJsVersion()%>"/>
    <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/style.css?v=<%=info.getJsVersion()%>"/>
    <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/bse.js?v=<%=info.getJsVersion()%> " > </script>
    <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/ajax.js?v=<%=info.getJsVersion()%> " > </script>
<script type="text/javascript" src="<%=info.getReqUrl()%>/chartscript/amcharts.js?v=<%=info.getJsVersion()%> " > </script>
<script type="text/javascript" src="<%=info.getReqUrl()%>/chartscript/chartajax.js?v=<%=info.getJsVersion()%> " > </script>
<script type="text/javascript" src="<%=info.getReqUrl()%>/chartscript/serial.js?v=<%=info.getJsVersion()%> " > </script>
      <script src="<%=info.getReqUrl()%>/PopScripts/jquery-1.2.6.min.js" type="text/javascript"></script>
        <script type="text/javascript" src="<%=info.getReqUrl()%>/PopScripts/popup.js?v=<%=info.getJsVersion()%> " > </script>
               <script src="<%=info.getReqUrl()%>/scripts/spacecode.js?v=<%=info.getJsVersion()%>"></script>
       <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/dispose.js?v=<%=info.getJsVersion()%> " > </script>
  </head>
  <%
  String tally = util.nvl((String)request.getAttribute("TALLY"));
  ArrayList seqList = (ArrayList)session.getAttribute("seqLst");
  String loadSeq = util.nvl((String)request.getAttribute("seqNo"));
  HashMap sttMap = (HashMap)request.getAttribute("sttMap");
    HashMap allPageDtl= (info.getPageDetails() == null)?new HashMap():(HashMap)info.getPageDetails();
    HashMap pageDtl=(HashMap)allPageDtl.get("STOCK_TALLY");
    ArrayList pageList=new ArrayList();
    HashMap pageDtlMap=new HashMap();
            HashMap dbinfo = info.getDmbsInfoLst();
            String BOX_RFID = (String)dbinfo.get("BOX_RFID");
            HashMap prp = info.getPrp();
            HashMap mprp = info.getMprp();
            ArrayList prpPrt2Size=null;
            ArrayList prpValSize=null;
            prpValSize = (ArrayList)prp.get(BOX_RFID+"V");
            prpPrt2Size = (ArrayList)prp.get(BOX_RFID+"P2");
    String fld_nme="",fld_ttl="",val_cond="",dflt_val="",fld_typ="",form_nme="",flg1="",lov_qry="";
        String logId=String.valueOf(info.getLogId());
        String onfocus="cook('"+logId+"')";
        %>
 <body onfocus="<%=onfocus%>" onload="MM_preloadImages('images/faunalogo.png')" topmargin="0" leftmargin="0" rightmargin="0" bottommargin="0">
   <input type="hidden" id="deviceip" name="deviceip" value="<%=util.nvl((String)info.getSpaceCodeIp())%>"/>
  <div id="backgroundPopup"></div>
  <div id="popupContactSave" align="center" >
<img src="../images/loading_flame.gif" height="150px" width="150px" border="0" />
</div>
<div id="popupContactASSFNL">
<table class="popUpTb">
 <tr><td height="5%">&nbsp;&nbsp;&nbsp;<html:button property="value(close)" styleClass="submit"  onclick="MKEmptyPopup('SC');disablePopupASSFNL()" value="Close"  /> </td></tr>
<tr><td valign="top" height="95%">
<iframe name="SC" class="frameStyle"   align="middle" frameborder="0">

</iframe></td></tr>
</table>
</div>
<input type="hidden" id="reqUrl" name="reqUrl" value="<%=info.getReqUrl()%>"/>
  <img src="../images/loadbig.gif" vspace="15" id="load" style="display:none;" class="loadpktDiv" border="0" />
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
  <img src="../images/line.jpg" border="0" /></td><td valign="middle"> <span class="pgHed">Stock Tally Return</span> </td>
  </tr></table>
  </td>
  </tr>
    <%String msg = (String)request.getAttribute("msg");
  if(msg!=null){
  %>
  <tr><td valign="top"  class="tdLayout"><span class="redLabel"><%=msg%></span></td></tr>
  <%}%>
  <%String msg1 = (String)request.getAttribute("msg1");
  if(msg1!=null){
  %>
  <tr><td valign="top"  class="tdLayout"><span class="redLabel"><%=msg1%></span></td></tr>
  <%}%>
   <html:form action="marketing/newStockTally.do?method=Return" method="post">
     <html:hidden property="value(listname)" styleId="listname" value="pid" />
    <html:hidden property="value(boxrfidval)" styleId="boxrfidval"/>   
   <tr><td  valign="top" class="tdLayout" >
    <table  class="grid1">
    <tr><td><table>
   <tr>
   <th colspan="2" align="center">Packet Return</th>
   </tr>
   <tr><td>Sequence</td>
    <td>
  <html:select property="value(rtnseq)" styleId="rtnseq" name="stockTallyForm">

  <%for(int i=0 ; i < seqList.size();i++){
  String seq = (String)seqList.get(i);
  %>
  <html:option value="<%=seq%>"><%=seq%></html:option>
  <%}%>
  <html:option value="" >--Select--</html:option> 
  </html:select>
    </td>
    </tr>
   <%pageList=(ArrayList)pageDtl.get("BOXALLOC");
   if(pageList!=null && pageList.size() >0){%>
    <tr>
    <td valign="top">Box Rfid<br/>
    <html:textarea property="value(boxrfid)" onchange="getBoxRfidVal();" styleId="boxrfid" rows="1" cols="20" style="resize: none;"/><br/>
    <button type="button" onclick="getBoxRfidVal();" Class="submit" >Get Box</button><br/>
    Assign To Box<br/>
    </td>
    <td><span id="boxrfidvaldisplay"></span></td>
    </tr>
    <%}%>
   <tr><td>Packets</td><td><html:textarea property="value(vnm)" rows="8" cols="40" styleId="pid" name="stockTallyForm" /> 
   <label id="fldCtr" ></label></td></tr>
   <tr><td colspan="2" align="center" nowrap="nowrap">
     <%pageList=(ArrayList)pageDtl.get("BUTTON");
      if(pageList!=null && pageList.size() >0){
      for(int j=0;j<pageList.size();j++){
      pageDtlMap=(HashMap)pageList.get(j);
      fld_nme="value("+(String)pageDtlMap.get("fld_nme")+")";
      fld_ttl=(String)pageDtlMap.get("fld_ttl");
      val_cond=(String)pageDtlMap.get("val_cond");
      fld_typ=(String)pageDtlMap.get("fld_typ");
      flg1=(String)pageDtlMap.get("flg1");%>
      <html:submit property="<%=fld_nme%>" value="<%=fld_ttl%>"  onclick="<%=val_cond%>" styleClass="submit" />
      <%}}%>
   <!--<html:submit property="value(rtn)" value="Return" onclick="return validateNewStockTallyRtn();" styleClass="submit"  />
   <html:submit property="value(rtnassign)" value="Return + Assign" onclick="return validatepacketRfidBoxAllocRtn();" styleClass="submit"  />-->
   <%ArrayList rfiddevice=info.getRfiddevice();
 if(rfiddevice!=null && rfiddevice.size()>0){
 %>
 RFID Device: <html:select property="value(dvcLst)" styleId="dvcLst" name="stockTallyForm"  >
 <%for(int j=0;j<rfiddevice.size();j++){
 ArrayList device=new ArrayList();
 device=(ArrayList)rfiddevice.get(j);
 String val=(String)device.get(0);
 String disp=(String)device.get(1);
 %>
 <html:option value="<%=val%>"><%=disp%></html:option>
 <%}%>
 </html:select>&nbsp;
 <%if(info.getRfid_seq().length()==0){%>
 <html:button property="value(rfScan)" value="RF ID Scan" styleId="rfScan" onclick="doScan('pid','fldCtr','dvcLst','SCAN')"  styleClass="submit" />
 <html:button property="value(autorfScan)" value="Auto Scan" styleId="autorfScan" onclick="doScan('pid','fldCtr','dvcLst','AUTOSCAN')"  styleClass="submit" />
<%}%>
 <html:button property="value(stopAutorfScan)" value="Stop Auto Scan" onclick="doScan('pid','fldCtr','dvcLst','STOPAUTOSCAN')"  styleClass="submit" />
 
<%}%>
                 <%if(util.nvl((String)info.getDmbsInfoLst().get("RFID_DNK"),"N").equals("Y")){%>
                 Continue Scan <input type="checkbox" id="contscan" title="Select To Continue Scan"></input>
                        <span style="margin:0px; padding:0px; display:none;">
                        <label id="summary" style="display:none;"></label>&nbsp;&nbsp;
                        </span>
                        Count&nbsp;&nbsp;:&nbsp;<label id="count"></label>&nbsp;&nbsp;
                        <span style="margin:0px; padding:0px; display:none;">
                        Notify&nbsp;&nbsp;:&nbsp;<label id="notify"></label>
                        <input type="checkbox" id="accumulateMode" title="Accumulate Mode"></input>
                        </span>
                        <html:button property="value(myButton)" value="Scan" styleId="myButton" onclick="scanRf()"  styleClass="submit" />
                        <span id="loadingrfid"></span>
                        <!--<html:button property="value(myButton2)" value="Dispose" styleId="myButton2" onclick="dispose()"  styleClass="submit" />
                        <html:button property="value(myButton4)" value="LED On" styleId="myButton4" onclick="waitOn('pid')"  styleClass="submit" />
                        <html:button property="value(myButton5)" value="LED Off" styleId="myButton5" onclick="waitOff()"  styleClass="submit" />
                        <html:button property="value(myButton6)" value="TestDll" styleId="myButton6" onclick="testDll()"  styleClass="submit" />-->
             <%}%>  
   </td></tr>
   </table>
  </td>
    <%int counter=1;%>
    <td valign="top" class="hedPg">
    <table  class="grid1">
    <tr><th colspan="6">Return Summary</th></tr>
    <tr><th>BoxId/Tranaction Id</th><th>Rtn Usr</th><th>Return</th><th>Already Return</th><th>Extra</th><th>Not Found</th></tr>
    <%
    String rtFlg = "";
    String isFlg = "";
    String nfFlg = "";
    String exFlg = "";
    String alrtFlg="";
    if(prpValSize.size()==1 && sttMap.size()>0){
    prpValSize=(ArrayList)sttMap.get("BOXID");
    }
    for(int k=0 ;k<prpValSize.size();k++){
    String boxrfidval = util.nvl((String)prpValSize.get(k));
    rtFlg = util.nvl((String)sttMap.get(boxrfidval+"_RT"),"0");
    alrtFlg = util.nvl((String)sttMap.get(boxrfidval+"_AR"),"0");
    String rtcts = util.nvl((String)sttMap.get(boxrfidval+"_CRTWT"),"0");
    isFlg = util.nvl((String)sttMap.get(boxrfidval+"_IS"),"0");
    nfFlg = util.nvl((String)sttMap.get(boxrfidval+"_NF"),"0");
    exFlg = util.nvl((String)sttMap.get(boxrfidval+"_EX"),"0");
    if(!rtFlg.equals("0") || !exFlg.equals("0") || !nfFlg.equals("0") || !alrtFlg.equals("0")){
        if(counter==16){
    counter=0;
    %>
    </table>
    </td>
    <td valign="top">
    <table  class="grid1">
    <tr><th>BoxId/Tranaction Id</th><th>Rtn Usr</th><th>Return</th><th>Extra</th><th>Not Found</th></tr>
    <%}counter++;
    %>
    <tr>
    <td><%=boxrfidval%></td>
    <td><%=util.nvl((String)sttMap.get(boxrfidval+"_RTN_USR"))%></td>
    <td>
    <%if(!rtFlg.equals("0")){%>
    <A href="<%=info.getReqUrl()%>/marketing/newStockTally.do?method=PktDtlExtra&stt=RT&box=<%=boxrfidval%>&loadSeq=<%=loadSeq%>" target="_blank"><%=rtFlg%></a>
    <%if(!rtcts.equals("0")){%>  
    | <%=rtcts%>
    <%}}%>
    </td>
    <td>
    <%if(!alrtFlg.equals("0")){%>
     <A href="<%=info.getReqUrl()%>/marketing/newStockTally.do?method=PktDtlExtra&stt=AR&box=<%=boxrfidval%>&loadSeq=<%=loadSeq%>" target="_blank"><%=alrtFlg%></a>
    <%}%>
    </td>
    <td>
    <%if(!exFlg.equals("0")){%>
    <A href="<%=info.getReqUrl()%>/marketing/newStockTally.do?method=PktDtlExtra&stt=EX&box=<%=boxrfidval%>&loadSeq=<%=loadSeq%>" target="_blank"><%=exFlg%></a>
    <%}%>
    </td>
    <td>
    <%if(!nfFlg.equals("0")){%>
    <A href="<%=info.getReqUrl()%>/marketing/newStockTally.do?method=PktDtlExtra&stt=NF&box=<%=boxrfidval%>&loadSeq=<%=loadSeq%>" target="_blank"><%=nfFlg%></a>
    <%}%>
    </td>
    </tr>
   <%}}
    rtFlg = util.nvl((String)sttMap.get("TOTAL_RT"),"0");
    alrtFlg = util.nvl((String)sttMap.get("TOTAL_AR"),"0");
    isFlg = util.nvl((String)sttMap.get("TOTAL_IS"),"0");
    nfFlg = util.nvl((String)sttMap.get("TOTAL_NF"),"0");
    exFlg = util.nvl((String)sttMap.get("TOTAL_EX"),"0");
    if(!rtFlg.equals("0") || !exFlg.equals("0") || !nfFlg.equals("0") || !alrtFlg.equals("0")){
    %>
    <tr>
    <td><b>Total</b></td>
    <td><b></b></td>
    <td>
    <%if(!rtFlg.equals("0")){%>
    <A href="<%=info.getReqUrl()%>/marketing/newStockTally.do?method=PktDtlExtra&stt=RT&box=TOTAL&loadSeq=<%=loadSeq%>" target="_blank"><%=rtFlg%></a>
    <%}%>
    </td>
    <td>
    <%if(!alrtFlg.equals("0")){%>
    <A href="<%=info.getReqUrl()%>/marketing/newStockTally.do?method=PktDtlExtra&stt=AR&box=TOTAL&loadSeq=<%=loadSeq%>" target="_blank"><%=alrtFlg%></a>
    <%}%>
    </td>
    <td>
    <%if(!exFlg.equals("0")){%>
    <A href="<%=info.getReqUrl()%>/marketing/newStockTally.do?method=PktDtlExtra&stt=EX&box=TOTAL&loadSeq=<%=loadSeq%>" target="_blank"><%=exFlg%></a>
    <%}%>
    </td>
    <td>
    <%if(!nfFlg.equals("0")){%>
    <A href="<%=info.getReqUrl()%>/marketing/newStockTally.do?method=PktDtlExtra&stt=NF&box=TOTAL&loadSeq=<%=loadSeq%>" target="_blank"><%=nfFlg%></a>
    <%}%>
    </td>
    </tr>
   <%}%>
    <%rtFlg = util.nvl((String)sttMap.get("_RT"),"0");
    isFlg = util.nvl((String)sttMap.get("_IS"),"0");
    nfFlg = util.nvl((String)sttMap.get("_NF"),"0");
    exFlg = util.nvl((String)sttMap.get("_EX"),"0");
    if(!rtFlg.equals("0") || !exFlg.equals("0") || !nfFlg.equals("0")){
   %>
    <tr>
    <td></td>
    <td>
    <%if(!rtFlg.equals("0")){%>
    <A href="<%=info.getReqUrl()%>/marketing/newStockTally.do?method=SearchResult&stt=RT" target="pktDtl"><%=rtFlg%></a>
     <%}%>
    </td>
    <td>
    <%if(!exFlg.equals("0")){%>
    <A href="<%=info.getReqUrl()%>/marketing/newStockTally.do?method=SearchResult&stt=EX" target="pktDtl"><%=exFlg%></a>
     <%}%>
    </td>
    <td>
     <%if(!nfFlg.equals("0")){%>
    <A href="<%=info.getReqUrl()%>/marketing/newStockTally.do?method=SearchResult&stt=NF" target="pktDtl"><%=nfFlg%></a>
     <%}%>
    </td>
    </tr>
   <%}%>
   </table>
   </td>
  </tr>
  </table>
  </td>
  </tr>
 
   </html:form>
  <tr> <td valign="top" class="tdLayout" height="20px">
  
  </td></tr>
  
  <tr> <td valign="top" class="tdLayout" >
  <table id="dataTable">
  <%
  HashMap pktDtl = (HashMap)session.getAttribute("pktDtl");
  ArrayList grpList = (ArrayList)session.getAttribute("grpList");
 
  HashMap grp3list = (HashMap)session.getAttribute("grp3List");
   String target = "SC_0";
  %>
  <tr id="<%=target%>"><td valign="top">
  <%if(pktDtl!=null && pktDtl.size() > 0){
  %>
  <table><tr><td><table><tr><td>
  <html:form action="marketing/newStockTally.do?method=fetchSeq" method="post" >
  <html:hidden property="value(redirect)" name="stockTallyForm" value="loadRtn" />
  Select Sequence :
 <html:select property="value(seq)" name="stockTallyForm" onchange="SubmitForm('1')">
 <html:option value="0" >---select---</html:option>
  <%for(int i=0 ; i < seqList.size();i++){
  String seq = (String)seqList.get(i);
 
  %>
  <html:option value="<%=seq%>"><%=seq%></html:option>
  <%}%>
  </html:select>
  </html:form>
  </td>
  <%pageList=(ArrayList)pageDtl.get("HISDAYS");
  if(pageList!=null && pageList.size() >0){
      for(int j=0;j<pageList.size();j++){
      pageDtlMap=(HashMap)pageList.get(j);
      fld_ttl=(String)pageDtlMap.get("fld_ttl");
  %>
  <td>&nbsp;&nbsp;<a href="<%=info.getReqUrl()%>/marketing/newStockTally.do?method=dayWisehistory&reqstt=RT" id="LNK_0" onclick="loadASSFNL('SC_0','LNK_0')"  target="SC"><%=fld_ttl%></a></td>
  <%}}%>
  </tr>
  </table>
  </td>
  </tr>
  <tr><td valign="top">
  <table class="grid1"><tr><th>Status</th>
   <%
  pageList=(ArrayList)pageDtl.get("TTLPRP");
  if(pageList!=null && pageList.size() >0){
      for(int j=0;j<pageList.size();j++){
      pageDtlMap=(HashMap)pageList.get(j);
      String fldNme = (String)pageDtlMap.get("fld_nme");
    %>
    <th><%=fldNme%></th>
  <%}}%>
  
  <th colspan="2">Total </th><th colspan="2">Issue </th><th colspan="2">Return </th></tr>
  <tr><td></td>
     <%
  pageList=(ArrayList)pageDtl.get("TTLPRP");
  if(pageList!=null && pageList.size() >0){%><td></td><%}%>
  <td><b>Qty </b></td><td><b>Cts</b></td><td><b>Qty</b></td><td><b>Cts</b></td><td><b>Qty</b></td><td><b>Cts</b></td></tr>
  <%
  for(int i=0;i<grpList.size();i++){
  String mstkStt = (String)grpList.get(i);
  String issQ = util.nvl((String)pktDtl.get(mstkStt+"_IS_Q"));
  String issC = util.nvl((String)pktDtl.get(mstkStt+"_IS_C"));
  String rtQ = util.nvl((String)pktDtl.get(mstkStt+"_RT_Q"));
  String rtC = util.nvl((String)pktDtl.get(mstkStt+"_RT_C"));
 
  String  ttlQ ="";
  if(issQ.length()>0 && rtQ.length()>0){
  ttlQ = String.valueOf((Integer.parseInt(issQ) +Integer.parseInt(rtQ)));
  }else if(issQ.length()>0){
   ttlQ = String.valueOf(Integer.parseInt(issQ));
  }else if(rtQ.length()>0){
   ttlQ = String.valueOf(Integer.parseInt(rtQ));
  }
  String  ttlC ="";
  if(issC.length()>0 && rtC.length()>0){
  ttlC = String.valueOf(Float.valueOf(issC) + Float.valueOf(rtC));
  }else if(issC.length()>0){
   ttlC = String.valueOf(Float.valueOf(issC));
  }else  if(rtC.length()>0){
    ttlC = String.valueOf(Float.valueOf(rtC));
  }
  %>
   <tr><td><b><A href="<%=info.getReqUrl()%>/marketing/newStockTally.do?method=TallyCRT&stt=<%=mstkStt%>&loadSeq=<%=loadSeq%>" onclick="displayover('pktDtltd')" target="pktDtl"><%=grp3list.get(mstkStt)%></a> </b> </td>
    <%
  pageList=(ArrayList)pageDtl.get("TTLPRP");
  if(pageList!=null && pageList.size() >0){
      for(int j=0;j<pageList.size();j++){
      pageDtlMap=(HashMap)pageList.get(j);
      String fldNme = (String)pageDtlMap.get("fld_nme");
      dflt_val = (String)pageDtlMap.get("dflt_val");
    %>
    <td><A href="<%=info.getReqUrl()%>/marketing/newStockTally.do?method=TallyCRT&stt=<%=mstkStt%>&loadSeq=<%=loadSeq%>&prp=<%=dflt_val%>" onclick="displayover('pktDtltd')" target="pktDtl">
    <%=fldNme%></a> </td>
  <%}}%>
   <td><A href="<%=info.getReqUrl()%>/marketing/newStockTally.do?method=PktDtl&dfstt=<%=mstkStt%>&issTyp=ALL&loadSeq=<%=loadSeq%>" target="_blank"><%=ttlQ%> </a></td>
   <td><%=ttlC%></td>
   <td><A href="<%=info.getReqUrl()%>/marketing/newStockTally.do?method=PktDtl&dfstt=<%=mstkStt%>&issTyp=IS&loadSeq=<%=loadSeq%>" target="_blank"><%=util.nvl((String)pktDtl.get(mstkStt+"_IS_Q"))%></a></td>
   <td><%=util.nvl((String)pktDtl.get(mstkStt+"_IS_C"))%></td>
   <td><A href="<%=info.getReqUrl()%>/marketing/newStockTally.do?method=PktDtl&dfstt=<%=mstkStt%>&issTyp=RT&loadSeq=<%=loadSeq%>" target="_blank"><%=util.nvl((String)pktDtl.get(mstkStt+"_RT_Q"))%></a></td>
   <td><%=util.nvl((String)pktDtl.get(mstkStt+"_RT_C"))%></td>
   </tr>
  <%}
  String mstkStt = "TOTAL";
  String issQ = util.nvl((String)pktDtl.get(mstkStt+"_IS_Q"));
  String issC = util.nvl((String)pktDtl.get(mstkStt+"_IS_C"));
  String rtQ = util.nvl((String)pktDtl.get(mstkStt+"_RT_Q"));
  String rtC = util.nvl((String)pktDtl.get(mstkStt+"_RT_C"));
 
  String  ttlQ ="";
  if(issQ.length()>0 && rtQ.length()>0){
  ttlQ = String.valueOf((Integer.parseInt(issQ) +Integer.parseInt(rtQ)));
  }else if(issQ.length()>0){
   ttlQ = String.valueOf(Integer.parseInt(issQ));
  }else if(rtQ.length()>0){
   ttlQ = String.valueOf(Integer.parseInt(rtQ));
  }
  String  ttlC ="";
  if(issC.length()>0 && rtC.length()>0){
  ttlC = String.valueOf(Float.valueOf(issC) + Float.valueOf(rtC));
  }else if(issC.length()>0){
   ttlC = String.valueOf(Float.valueOf(issC));
  }else  if(rtC.length()>0){
    ttlC = String.valueOf(Float.valueOf(rtC));
  }
  %>
   <tr>
     <%
  pageList=(ArrayList)pageDtl.get("TTLPRP");
  if(pageList!=null && pageList.size() >0){%><td></td><%}%>
   <td><b>TOTAL</b> </td>
  
   <td><A href="<%=info.getReqUrl()%>/marketing/newStockTally.do?method=PktDtl&issTyp=ALL&loadSeq=<%=loadSeq%>" target="_blank"><%=ttlQ%> </a></td>
   <td><%=ttlC%></td>
   <td><A href="<%=info.getReqUrl()%>/marketing/newStockTally.do?method=PktDtl&issTyp=IS&loadSeq=<%=loadSeq%>" target="_blank"><%=util.nvl((String)pktDtl.get(mstkStt+"_IS_Q"))%></a></td>
   <td><%=util.nvl((String)pktDtl.get(mstkStt+"_IS_C"))%></td>
   <td><A href="<%=info.getReqUrl()%>/marketing/newStockTally.do?method=PktDtl&issTyp=RT&loadSeq=<%=loadSeq%>" target="_blank"><%=util.nvl((String)pktDtl.get(mstkStt+"_RT_Q"))%></a></td>
   <td><%=util.nvl((String)pktDtl.get(mstkStt+"_RT_C"))%></td>
   </tr>
  </table></td>
  </tr>
  <tr>
  <td valign="top" style="display:none;" id="pktDtltd">
   <iframe name="pktDtl" width="700"  height="700px" frameborder="0">

   </iframe>
  </td>
  </tr>
  </table>
  <%}%>
  </td></tr></table>
  
  
  </td></tr>
  </table>
  <input type="hidden" id="barGrp" value="Total_Issue_Return">
  <div style="margin:0px; padding:0px;">
  <input type="hidden" id="TALLY_HIDD" value="TALLY">
  <input type="hidden" id="TALLY_TTL" value="Stock">
  <div id="TALLY" style="width: 100%; height: 400px; float:left; margin:0px; padding:0px;">
  </div>
  </div>
<%
pageList=(ArrayList)pageDtl.get("RETURNGRAPH");
if(pageList!=null && pageList.size() >0){
if(seqList!=null && seqList.size()>0){
String url="ajaxRptAction.do?method=stockTally";%>
<script type="text/javascript">
<!--
$(window).bind("load", function() {
callcreatedoubleBarGraph('<%=url%>','Tally','50','362');
});
 -->
</script>  
<%}}%>
<script language="javascript">
	var device = new Spacecode.Device(document.getElementById("deviceip").value);
	var lastInventory = null;
	var tagscanList = [];


	device.on('connected', function ()
	{
		if(device.isInitialized())
		{
			device.requestScan();		
			// Connection with the remote device established
			console.log('Connected to Device: '+ device.getDeviceType() + ' ('+device.getSerialNumber()+')');
		}
	});

	device.on('disconnected', function ()
	{
		if(device.isInitialized())
		{
			// The connection to the remote device has been lost
			console.log(device.getSerialNumber() + ' - Disconnected');
			return;
		}
	});

	device.on('scanstarted', function ()
	{
		count =0;
		console.log("Scan started.");
	});

	device.on('scancompleted', function ()
	{
		device.getLastInventory(function(inventory)
		{
			lastInventory = inventory;
			summaryElem2 = document.getElementById("notify");
			summaryElem2.innerHTML = inventory.getNumberTotal() +" tags have been scanned.";				
			tagscanList = inventory.getTagsAll() || [];
            var str = tagscanList.join();
                        var chk =document.getElementById("contscan").checked;
                        var listnametextContent=document.getElementById(document.getElementById("listname").value).textContent;
                        if(chk && listnametextContent!=''){
                          if(str!=''){
                              str=listnametextContent+","+str;
                          }
                        }
                        document.getElementById(document.getElementById("listname").value).textContent = str;
                        var listnametextContentCount=document.getElementById(document.getElementById("listname").value).textContent;
                        if(listnametextContentCount!=''){
                        var resCount = listnametextContentCount.split(",");
                        document.getElementById("count").innerHTML=resCount.length;
                        }
                        document.getElementById('loadingrfid').innerHTML = "";
			// Note: with remote devices, getLastInventory can return 'null' in case of communication errors
			console.log(inventory.getNumberTotal() +" tags have been scanned.");
			// prints: "X tags have been scanned." (with X the result of getNumberTotal)
		});	
		// the inventory (resulting from the scan) is ready
		console.log("Scan completed.");
	});

	device.on('tagadded', function (tagUid)
	{
		// a tag has been detected during the scan
		console.log("Tag scanned: "+ tagUid);
	});	
	

	
	function scanRf()
	{	
		document.getElementById('loadingrfid').innerHTML = "<img src=\"../images/processing1.gif\" align=\"middle\" border=\"0\" />";
                count =0;
		if(device.isConnected() == false)
		{
			device.connect();			
		}
		else
		{
			device.requestScan();		
		}
	}
	
	function callBackLED(result)
	{
		if(!result)
		{
			console.debug("Could not start the lighting operation.");
		}

		// let the LED's blink untill User press OK.
		alert("Press OK to Turn LED Off");
		
		device.stopLightingTagsLed(function(result)
		{
			if(!result)
			{
				console.debug("Could not stop the lighting operation.");
			}
		});		
	}	
	
	function ligtOn(txtarea)
	{
                var li = document.getElementById(txtarea).value;
                var arr = li.split(',');
		device.startLightingTagsLed(callBackLED,arr);	
		
	}
	
	function ligtOff()
	{
		device.stopLightingTagsLed(function(result)
		{
			if(!result)
			{
				console.debug("Could not stop the lighting operation.");
			}
		});
	}	
		
	//device.connect();
</script>
  </body>
</html>