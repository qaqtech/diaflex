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
    <title>Memo Search</title>
    <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/bse.css?v=<%=info.getJsVersion()%>"/>
    <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/style.css?v=<%=info.getJsVersion()%>"/>
    <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/ajax.js?v=<%=info.getJsVersion()%>" ></script>
    <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/bse.js?v=<%=info.getJsVersion()%>" ></script>
<script language="javaScript" type="text/javascript" src="<%=info.getReqUrl()%>/calendar/calendar.js"></script>
   <link href="<%=info.getReqUrl()%>/calendar/calendar.css" rel="stylesheet" type="text/css" />
         
  <script language="javaScript">
    function isNumeric(elem) {
      var numericExpression = /^[0-9]+$/;
      if(elem.value.match(numericExpression) || elem.value.length == 0){
        return true;
      }
      else{
        alert("Please enter numbers.");
        elem.focus();
        elem.select();
        return false;
      }
    }
    
    function checkForm() {
    //alert("in checkForm");
      var memoDte = document.getElementById("memoDte");
      var memotoDte = document.getElementById("memotoDte");
      
      var memoexpDte = document.getElementById("memoexpDte");
      var memoexptoDte = document.getElementById("memoexptoDte");
      
      var memoexpDay = document.getElementById("memoexpDay");
      var memoexptoDay = document.getElementById("memoexptoDay");
      
      if(memoDte.value.length == 0 && memotoDte.value.length != 0) {
        alert("Please enter Memo Create From Date");
        memoDte.focus();
        return false;
      }
      
      if(memoDte.value.length != 0 && memotoDte.value.length == 0) {
        alert("Please enter Memo Create To Date");
        memotoDte.focus();
        return false;
      }
      
      if(memoexpDte.value.length == 0 && memoexptoDte.value.length != 0) {
        alert("Please enter Memo Create From Date");
        memoexpDte.focus();
        return false;
      }
      
      if(memoexpDte.value.length != 0 && memoexptoDte.value.length == 0) {
        alert("Please enter Memo Create To Date");
        memoexptoDte.focus();
        return false;
      }
      
      if(memoexpDay.value.length == 0 && memoexptoDay.value.length != 0) {
        alert("Please enter Memo Create From Date");
        memoexpDay.focus();
        return false;
      }
      
      if(memoexpDay.value.length != 0 && memoexptoDay.value.length == 0) {
        alert("Please enter Memo Create To Date");
        memoexptoDay.focus();
        return false;
      }
      if(isNumeric(memoexpDay) && isNumeric(memoexptoDay)) {
        return true;
      }
      else {
        return false;
      }
      return true;
    }
    
  </script>
  
  </head>
        <%String logId=String.valueOf(info.getLogId());
        String onfocus="cook('"+logId+"')";
        ArrayList byrLst = (ArrayList)request.getAttribute("byrLst");
        HashMap byrDataDtl = (HashMap)request.getAttribute("byrDataDtl");
        ArrayList PktDtlList = (ArrayList)request.getAttribute("PktDtlList");
        %>
 <body onfocus="<%=onfocus%>" onload="MM_preloadImages('images/faunalogo.png')"  onkeypress="return disableEnterKey(event);" topmargin="0" leftmargin="0" rightmargin="0" bottommargin="0">
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
  <img src="../images/line.jpg" border="0" /></td><td valign="middle"> <span class="pgHed">Memo Search</span> </td>
</tr></table>
  </td>
  </tr>
  <tr>
  <td valign="top" class="hedPg">
  <html:form action="marketing/memoSrch.do?method=fetch" method="post" onsubmit=" return checkForm();">
  <table>
  <%
  String cnt = util.nvl((String)info.getDmbsInfoLst().get("CNT"));
HashMap allPageDtl= (info.getPageDetails() == null)?new HashMap():(HashMap)info.getPageDetails();
HashMap pageDtl=(HashMap)allPageDtl.get("MEMO_SRCH");
    ArrayList pageList=new ArrayList();
    HashMap pageDtlMap=new HashMap();
    String fld_nme="",fld_ttl="",val_cond="",dflt_val="",fld_typ="",form_nme="",lov_qry="",flg1="",fid="",tid="";
    %>
    <tr>
    <%
    pageList=(ArrayList)pageDtl.get("FIELDS");
            int counterfilter=0;
            if(pageList!=null && pageList.size() >0){
            for(int j=0;j<pageList.size();j++){
            pageDtlMap=(HashMap)pageList.get(j);
            form_nme=(String)pageDtlMap.get("form_nme");
            fld_nme="value("+(String)pageDtlMap.get("fld_nme")+")";
            fld_ttl=(String)pageDtlMap.get("fld_ttl");
            fld_typ=(String)pageDtlMap.get("fld_typ");
            lov_qry=(String)pageDtlMap.get("lov_qry");
            dflt_val=(String)pageDtlMap.get("dflt_val");
            val_cond=(String)pageDtlMap.get("val_cond"); 
            flg1=(String)pageDtlMap.get("flg1");
            if(flg1.equals("D") || flg1.equals("FT")){
            fid=(String)pageDtlMap.get("fld_nme");
            tid=(String)pageDtlMap.get("fld_typ");
            fld_typ="value("+(String)pageDtlMap.get("fld_typ")+")";
            }
            if(counterfilter==3){%>
            </tr>
            <tr>
            <%counterfilter=0;
            }%>
            
            <%
            if(flg1.equals("T")){
             %>
            <td nowrap="nowrap"><%=fld_ttl%></td><td nowrap="nowrap"> <html:text property="<%=fld_nme%>" name="<%=form_nme%>"/> </td>
            <% } else if(flg1.equals("TA")){%>
            <td nowrap="nowrap"><%=fld_ttl%></td><td nowrap="nowrap"><html:textarea property="<%=fld_nme%>" name="<%=form_nme%>"/> </td>
            <%} else if(flg1.equals("S")){%>
              <td nowrap="nowrap"><%=fld_ttl%></td><td nowrap="nowrap">
              <html:select property="<%=fld_nme%>" styleId="<%=fld_typ%>"  name="memoSrchForm"  >
                <html:option value="0" >--select--</html:option>
                <html:optionsCollection property="<%=dflt_val%>" name="memoSrchForm"
                        label="<%=lov_qry%>" value="<%=val_cond%>" />
             </html:select>
              </td>
            <%}else if(flg1.equals("SB")){%>
              <td nowrap="nowrap"><%=fld_ttl%></td><td nowrap="nowrap">
    <%
    String sbUrl = info.getReqUrl() + "/AjaxAction.do?UsrTyp=BUYER,VENDER,VENDOR";
    %>
    <input type="text" id="nme" name="nme" autocomplete="off" class="sugBox"
      onKeyUp="doCompletion('nme', 'nmePop', '../ajaxAction.do?UsrTyp=BUYER,VENDER,VENDOR', event)"
      onKeyDown="setDown('nmePop', 'nmeID', 'nme',event)" 
      />
      <img src="../images/addrow.jpg" width="17" height="17" title="Click To get Names" onclick="doCompletionMobile('nme', 'nmePop', '../ajaxAction.do?UsrTyp==BUYER,VENDER,VENDOR')">
    <html:hidden property="nmeID" styleId="nmeID"/>
  <div style="position: absolute;">
      <select id="nmePop" name="nmePop"
        style="display:none;300px;" 
        class="sugBoxDiv" 
        onKeyDown="setDown('nmePop', 'nmeID', 'nme',event)" 
        onDblClick="setVal('nmePop', 'nmeID', 'nme', event);hideObj('nmePop')" 
        onClick="setVal('nmePop', 'nmeID', 'nme', event)" 
        size="10">
      </select>
</div>
  </td>
            <%}else if(flg1.equals("SS")){%>
              <td nowrap="nowrap"><%=fld_ttl%></td><td nowrap="nowrap">
                  <html:select property="value(stt)" name="memoSrchForm" >
                    <html:option value="0">---select---</html:option>
                    <html:option value="IS">Issue</html:option>
                    <html:option value="AP">Approve</html:option>
                 
                  </html:select>
                  </td>
                  <%ArrayList notepersonList = ((ArrayList)info.getNoteperson() == null)?new ArrayList():(ArrayList)info.getNoteperson();
            if(notepersonList.size()>0){
            counterfilter++;%>
            <td nowrap="nowrap">Note Person:</td>
            <td nowrap="nowrap">
                  <html:select name="memoSrchForm" property="value(noteperson)" styleId="noteperson">
                  <html:option value="">---Select---</html:option><%
                  for(int i=0;i<notepersonList.size();i++){
                  ArrayList noteperson=(ArrayList)notepersonList.get(i);%>
                  <html:option value="<%=(String)noteperson.get(0)%>"> <%=(String)noteperson.get(1)%> </html:option>
                  <%}%>
                  </html:select>
            </td>
            <%}%>
            <%} else if(flg1.equals("D")){%>
             <td nowrap="nowrap">Memo <%=fld_ttl%></td>
              <td nowrap="nowrap">
              <%-- Start -- modified MemoCreateDate search for ticket 3932--%>
                <table><tr>
                  <td>From</td><td>
                    <div class="float"> <html:text property="<%=fld_nme%>"  styleId="<%=fid%>" /></div>
                    <div class="float"> <a href="#"  onClick="setYears(1900, <%=info.getCurrentYear()%>);showCalender(this, '<%=fid%>');">
                      <img src="<%=info.getReqUrl()%>/images/calender.png"></a></div>
                  </td>
                  <td>To</td><td>
                    <div class="float"> <html:text property="<%=fld_typ%>"  styleId="<%=tid%>" /></div>
                    <div class="float"> <a href="#"  onClick="setYears(1900, <%=info.getCurrentYear()%>);showCalender(this, '<%=tid%>');">
                      <img src="<%=info.getReqUrl()%>/images/calender.png"></a></div>
                  </td>
                </tr></table>
                <%-- End -- modified MemoCreateDate search for ticket 3932--%>
              </td>
              
            <%} else if(flg1.equals("FT")){%>
             <td nowrap="nowrap">Memo <%=fld_ttl%></td>
              <td nowrap="nowrap">
                <table><tr>
                  <td>From</td><td>
                    <div class="float"> <html:text property="<%=fld_nme%>"  styleId="<%=fid%>" onchange="return isNumeric(this);" size="10" /></div>
                  </td>
                  <td>To</td><td>
                    <div class="float"> <html:text property="<%=fld_typ%>"  styleId="<%=tid%>" onchange="return isNumeric(this);"  size="10"/></div>
                  </td>
                </tr></table>
              </td>
              
            
            <%}else if(flg1.equals("BC")){%>
            <td nowrap="nowrap">
                <span class="txtBold"> <%=fld_ttl%></span></td><td nowrap="nowrap">
                <html:select property="<%=fld_nme%>" name="memoSrchForm" >
                <html:option value="">---select---</html:option>
                <html:optionsCollection property="value(ByrCbList)" name="memoSrchForm" label="<%=lov_qry%>" value="<%=val_cond%>" />
                </html:select></td>
            <%}else if(flg1.equals("MT")){%>
            <td nowrap="nowrap">
                <span class="txtBold"> <%=fld_ttl%></span></td><td nowrap="nowrap">
            <html:select property="<%=fld_nme%>" styleId="memoTyp" name="memoSrchForm">
            <html:option value="">---select---</html:option>
            <%if(cnt.equals("kj")){%>
            <html:option value="CS">Consignment</html:option>
            <%}%>
            <html:option value="NG">New Goods(NG)</html:option>
            <html:optionsCollection property="value(memoList)" name="memoSrchForm"
            label="dsc" value="memo_typ" />
            </html:select></td>
            <%}%>
            <%counterfilter++;}}%>
            </tr>
            <tr>
            <%pageList=(ArrayList)pageDtl.get("STTSELECT");
             if(pageList!=null && pageList.size() >0){%>
             <td>
                <span class="txtBold">Memo Status</span></td><td>
             <html:select property="value(memostt)" styleId="memostt" name="memoSrchForm" >
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
            </html:select></td>
            <%}
             pageList=(ArrayList)pageDtl.get("PACKETTYP");
             if(pageList!=null && pageList.size() >0){%>
             <td>
                <span class="txtBold">Packet Type</span></td><td>
             <html:select property="value(pktty)" styleId="pktty" name="memoSrchForm" >
             <html:option value="">---select---</html:option>
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
            </html:select></td>
            <%}%>
            <% pageList=(ArrayList)pageDtl.get("DFLTPACKETTYP");
             if(pageList!=null && pageList.size() >0){%>
             <html:hidden property="value(dfltPktTyp)" value="NR"  name="memoSrchForm" />
             <%}%>
            </tr>
  <!--<tr><td>Memo Id</td><td> <html:text property="value(memoId)" name="memoSrchForm"/> </td></tr>
  <tr><td>Packet Number</td><td><html:textarea property="value(vnm)" name="memoSrchForm"/> </td></tr>
  <tr><td>Report Number</td><td><html:textarea property="value(refNo)" name="memoSrchForm"/> </td></tr>
  <tr><td>Co-ordinator</td><td>
  <html:select property="value(salCo)" styleId="byrId"  name="memoSrchForm"  >
    <html:option value="0" >--select--</html:option>
    <html:optionsCollection property="brokerList" name="memoSrchForm"
            label="byrNme" value="byrIdn" />
 </html:select>
  </td></tr>
  <tr><td>Party Name</td><td>
  <html:select property="value(byr)" name="memoSrchForm" styleId="party" style="width:200px" >
    <html:option value="0">---select---</html:option>
    <html:optionsCollection property="byrList" name="memoSrchForm" label="byrNme" value="byrIdn" />
  </html:select>
  </td></tr>
  <tr><td>Status</td><td>
  <html:select property="value(stt)" name="memoSrchForm" >
    <html:option value="0">---select---</html:option>
    <html:option value="IS">Issue</html:option>
    <html:option value="AP">Approve</html:option>
 
  </html:select>
  </td></tr>
  
  <tr><td>Memo Create Date</td>
  <td>
  <%-- Start -- modified MemoCreateDate search for ticket 3932--%>
    <table><tr>
      <td>From</td><td>
        <div class="float"> <html:text property="value(memoDte)"  styleId="memoDte" /></div>
        <div class="float"> <a href="#"  onClick="setYears(1900, <%=info.getCurrentYear()%>);showCalender(this, 'memoDte');">
          <img src="<%=info.getReqUrl()%>/images/calender.png"></a></div>
      </td>
      <td>To</td><td>
        <div class="float"> <html:text property="value(memotoDte)"  styleId="memotoDte" /></div>
        <div class="float"> <a href="#"  onClick="setYears(1900, <%=info.getCurrentYear()%>);showCalender(this, 'memotoDte');">
          <img src="<%=info.getReqUrl()%>/images/calender.png"></a></div>
      </td>
    </tr></table>
    <%-- End -- modified MemoCreateDate search for ticket 3932--%>
  </td>
  </tr>
  
  <%-- Start -- added memo expiray date and memo expiray days search for ticket 3932--%>
  <tr><td>Memo Expiry Date</td>
  <td>
    <table><tr>
      <td>From</td><td>
        <div class="float"> <html:text property="value(memoexpDte)"  styleId="memoexpDte" /></div>
        <div class="float"> <a href="#"  onClick="setYears(1900, <%=info.getCurrentYear()%>);showCalender(this, 'memoexpDte');">
          <img src="<%=info.getReqUrl()%>/images/calender.png"></a></div>
      </td>
      <td>To</td><td>
        <div class="float"> <html:text property="value(memoexptoDte)"  styleId="memoexptoDte" /></div>
        <div class="float"> <a href="#"  onClick="setYears(1900, <%=info.getCurrentYear()%>);showCalender(this, 'memoexptoDte');">
          <img src="<%=info.getReqUrl()%>/images/calender.png"></a></div>
      </td>
    </tr></table>
  </td>
  </tr>
  
  <tr><td>Memo Expiry Days</td>
  <td>
    <table><tr>
      <td>From</td><td>
        <div class="float"> <html:text property="value(memoexpDay)"  styleId="memoexpDay" onchange="return isNumeric(this);" /></div>
      </td>
      <td>To</td><td>
        <div class="float"> <html:text property="value(memoexptoDay)"  styleId="memoexptoDay" onchange="return isNumeric(this);" /></div>
      </td>
    </tr></table>
  </td>
  </tr>-->
  <%-- End -- modified MemoCreateDate search for ticket 3932--%>
<!--<logic:present property="value(ByrCbList)" name="memoSrchForm" >
<tr>
<td>
<span class="txtBold"> Buyer Cabin : </span></td><td>
<html:select property="value(cabin)" name="memoSrchForm" >
<html:option value="">---select---</html:option>
<html:optionsCollection property="value(ByrCbList)" name="memoSrchForm" value="FORM_NME" label="FORM_TTL" />
</html:select></td>
</tr>
</logic:present>-->
  <tr><td colspan="2" align="center"> <html:submit property="value(submit)" styleClass="submit" value="Submit" /> </td> </tr>
  </table>
  </html:form>
  </td></tr>
  <% 
  String view = util.nvl((String)request.getAttribute("view"));
  if(!view.equals("")){
  String client = (String)info.getDmbsInfoLst().get("CNT");
  
  %>
  <tr>
  <td valign="top" class="hedPg">
 <%if(PktDtlList!=null && PktDtlList.size()>0){%>
  <table class="dataTable">
  <tr><th>SR NO.</th>
  <th>Date</th>
  <th>Memo Id</th>
  <th>Memo Id</th>
  <th>Party Name</th>
  <th>Sale Ex</th>
  <th>Memo Type</th>
  <th>Memo Status</th>
   <%if(!client.equalsIgnoreCase("AG")){%>
    <th>Pcs</th>
    <th>Cts</th>
     <th>Value</th>
   <%}%>
   <th>Current Qty</th>
    <th>Current Cts</th>
     <th>Rmk</th>
       <%if(client.equals("ag")){%>
        <th>Note Person</th>
         <th>Byr Cabin</th>
         <%}%>
      <th>Expiry days</th>
       <th>Terms</th>
       </tr>
 <%for(int i=0;i<PktDtlList.size();i++){
 HashMap memoMap = (HashMap)PktDtlList.get(i);
 String memo=(String)memoMap.get("memo");
 String memoLink=info.getReqUrl()+"/marketing/memoReturn.do?method=load&memoId="+memo+"&pnd=Y&view=Y";
 %>
  
 <tr>
 <td><%=(String)memoMap.get("sr")%></td>
  <td><%=(String)memoMap.get("dte")%></td>
   <td><a href="<%=memoLink%>"><%=(String)memoMap.get("memo")%></a></td>
    <td><%=(String)memoMap.get("memoidn")%></td>
     <td><%=(String)memoMap.get("emp")%></td>
      <td><%=(String)memoMap.get("typ")%></td>
       <td><%=(String)memoMap.get("stt")%></td>
    <%if(!client.equalsIgnoreCase("AG")){%>
       <td><%=(String)memoMap.get("qty")%></td>
       <td><%=(String)memoMap.get("cts")%></td>
      <td><%=(String)memoMap.get("vlu")%></td>
    <%}%>
     <td><%=(String)memoMap.get("on_memo_qty")%></td>
      <td><%=(String)memoMap.get("on_memo_cts")%></td>
       <td><%=(String)memoMap.get("on_memo_amt")%></td>
        <td><%=(String)memoMap.get("rmk")%></td>
         <%if(client.equals("ag")){%>
          <td><%=(String)memoMap.get("note_person")%></td>
          <td><%=(String)memoMap.get("byr_cabin")%></td>
         <%}%>
         <td><%=(String)memoMap.get("exp_dys")%></td>
          <td><%=(String)memoMap.get("dtls")%></td>
</tr>
 
  
 
  <%}%>
   </table>
 <% }%>
  
 
  </td></tr>
  <tr>
  <td valign="top" class="hedPg">
  <table class="grid1">
  <tr><th colspan="18">Summary Details</th></tr>
  <tr><th>Employee</th><th>Byr</th><th colspan="4">Issue</th><th colspan="4">Approve</th><th colspan="4">Return</th><th colspan="4">Total</th></tr>
  <tr>
  <td></td>
  <td></td>
  <th>Cnt</th><th>Qty</th><th>Cts</th><th>Vlu</th>
  <th>Cnt</th><th>Qty</th><th>Cts</th><th>Vlu</th>
  <th>Cnt</th><th>Qty</th><th>Cts</th><th>Vlu</th>
  <th>Cnt</th><th>Qty</th><th>Cts</th><th>Vlu</th>
  </tr>
  <%for(int i=0;i<byrLst.size();i++){
  String byrNm=(String)byrLst.get(i);
  double ttlQty=Double.parseDouble(util.nvl((String)byrDataDtl.get(byrNm+"_TTL_QTY"),"0"));
  double ttlvlu=Double.parseDouble(util.nvl((String)byrDataDtl.get(byrNm+"_TTL_VLU"),"0"));
  String isQty=util.nvl((String)byrDataDtl.get(byrNm+"_IS_QTY"));
  String apQty=util.nvl((String)byrDataDtl.get(byrNm+"_AP_QTY"));
  String rtQty=util.nvl((String)byrDataDtl.get(byrNm+"_RT_QTY"));
  String isVlu=util.nvl((String)byrDataDtl.get(byrNm+"_IS_VLU"));
  String apVlu=util.nvl((String)byrDataDtl.get(byrNm+"_AP_VLU"));
  String rtVlu=util.nvl((String)byrDataDtl.get(byrNm+"_RT_VLU"));
  %>
  <tr>
  <td align="center"><%=util.nvl((String)byrDataDtl.get(byrNm))%></td>
  <td align="center"><%=byrNm%></td>
  <td align="right"><%=util.nvl((String)byrDataDtl.get(byrNm+"_IS_CNT"))%></td>
  <td align="right" nowrap="nowrap"><%=isQty%> 
  <%if(!isQty.equals("")){%>
  | <%=util.roundToDecimals(((Double.parseDouble(isQty)/ttlQty)*100),1)%>%
  <%}%>
  </td>
  <td align="right"><%=util.nvl((String)byrDataDtl.get(byrNm+"_IS_CTS"))%></td>
  <td align="right" nowrap="nowrap"><%=isVlu%>
  <%if(!isVlu.equals("")){%>
  | <%=util.roundToDecimals(((Double.parseDouble(isVlu)/ttlvlu)*100),1)%>%
  <%}%>
  </td>
    <td align="right"><%=util.nvl((String)byrDataDtl.get(byrNm+"_AP_CNT"))%></td>
  <td align="right" nowrap="nowrap"><%=apQty%>
  <%if(!apQty.equals("")){%>
  | <%=util.roundToDecimals(((Double.parseDouble(apQty)/ttlQty)*100),1)%>%
  <%}%>
  </td>
  <td align="right"><%=util.nvl((String)byrDataDtl.get(byrNm+"_AP_CTS"))%></td>
  <td align="right" nowrap="nowrap"><%=apVlu%>
  <%if(!apVlu.equals("")){%>
  | <%=util.roundToDecimals(((Double.parseDouble(apVlu)/ttlvlu)*100),1)%>%
  <%}%>
  </td>
    <td align="right"><%=util.nvl((String)byrDataDtl.get(byrNm+"_RT_CNT"))%></td>
  <td align="right" nowrap="nowrap"><%=rtQty%>
  <%if(!rtQty.equals("")){%>
  | <%=util.roundToDecimals(((Double.parseDouble(rtQty)/ttlQty)*100),1)%>%
  <%}%>
  </td>
  <td align="right"><%=util.nvl((String)byrDataDtl.get(byrNm+"_RT_CTS"))%></td>
  <td align="right" nowrap="nowrap"><%=rtVlu%>
  <%if(!rtVlu.equals("")){%>
  | <%=util.roundToDecimals(((Double.parseDouble(rtVlu)/ttlvlu)*100),1)%>%
  <%}%>
  </td>
    <td align="right"><%=util.nvl((String)byrDataDtl.get(byrNm+"_TTL_CNT"))%></td>
  <td align="right"><%=util.nvl((String)byrDataDtl.get(byrNm+"_TTL_QTY"))%></td>
  <td align="right"><%=util.nvl((String)byrDataDtl.get(byrNm+"_TTL_CTS"))%></td>
  <td align="right"><%=util.nvl((String)byrDataDtl.get(byrNm+"_TTL_VLU"))%></td>
  </tr>
  <%}%>
  </table>
  </td>
  </tr>
  
  <%}%>
  <tr>
    <td><jsp:include page="/footer.jsp" /> </td>
    </tr>
  </table>
  <%@include file="../calendar.jsp"%>
  </body>
</html>
