<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page errorPage="../error_page.jsp" %>
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.ResultSet, java.util.ArrayList,java.util.Collections,java.util.ArrayList,java.util.HashMap, java.util.Enumeration,java.text.SimpleDateFormat, java.util.Calendar"%>
<jsp:useBean id="info" class="ft.com.InfoMgr" scope="session" />
<jsp:useBean id="util" class="ft.com.JspUtil" scope="page" />
<%@taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
 

<html>
   <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/bse.js?v=<%=info.getJsVersion()%> " > </script>
<link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/bse.css?v=<%=info.getJsVersion()%>"/>
<link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/general.css?v=<%=info.getJsVersion()%>"/>
 <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/memoScript.js?v=<%=info.getJsVersion()%> " > </script>
<script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/ajax.js?v=<%=info.getJsVersion()%> " > </script>
  <script type="text/javascript" src="<%=info.getReqUrl()%>/PopScripts/popup.js?v=<%=info.getJsVersion()%> " > </script>
    <script src="<%=info.getReqUrl()%>/PopScripts/jquery-1.2.6.min.js" type="text/javascript"></script>
   <script language="javaScript" type="text/javascript" src="<%=info.getReqUrl()%>/calendar/calendar.js"></script>
   <link href="<%=info.getReqUrl()%>/calendar/calendar.css" rel="stylesheet" type="text/css" />

 
      <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/style.css?v=<%=info.getJsVersion()%>"/>

  <title>Daily Sales Report</title>
 
  </head>
        <%String logId=String.valueOf(info.getLogId());
        String onfocus="cook('"+logId+"')";
        %>
 <body onfocus="<%=onfocus%>" onkeypress="return disableEnterKey(event);" >
   <input type="hidden" id="reqUrl" name="reqUrl" value="<%=info.getReqUrl()%>"/>

    <div id="backgroundPopup"></div>
<div id="popupContactASSFNL" style="height:200px;width:500px">
<table class="popUpTb">
 <tr><td height="5%">&nbsp;&nbsp;&nbsp;<html:button property="value(close)" styleClass="submit"  onclick="MKEmptyPopup('SC');disablePopupASSFNL();loadPerentUrl();" value="Close"  /> </td></tr>
<tr><td valign="top" height="95%">
<iframe name="SC" class="frameStyle" align="middle" frameborder="0" width="500" height="200" >

</iframe></td></tr>
</table>
</div>
   <%
int accessidn=Integer.parseInt(util.nvl((String)request.getAttribute("accessidn"),"0"));
String pktTy=util.nvl((String)request.getAttribute("pktTy"));
   HashMap dbinfo = info.getDmbsInfoLst();
   String cnt = (String)dbinfo.get("CNT");
   String repPath = util.nvl((String)dbinfo.get("REP_PATH"));
    HashMap allPageDtl= (info.getPageDetails() == null)?new HashMap():(HashMap)info.getPageDetails();
    HashMap pageDtl=(HashMap)allPageDtl.get("DAILY_SALE_RPT");
    ArrayList pageList=new ArrayList();
    HashMap pageDtlMap=new HashMap();
    String fld_nme="",fld_ttl="",val_cond="",dflt_val="",fld_typ="",form_nme="",flg1="";
    String displayvluInr="display:none";
      pageList=(ArrayList)pageDtl.get("VLUININR");
      if(pageList!=null && pageList.size() >0){
      for(int j=0;j<pageList.size();j++){
      pageDtlMap=(HashMap)pageList.get(j);
       dflt_val = util.nvl((String)pageDtlMap.get("dflt_val"));
      displayvluInr=dflt_val;      %>
      <%}}
     String memormk="N";
     pageList=(ArrayList)pageDtl.get("MEMO_RMK");
     if(pageList!=null && pageList.size() >0){
         for(int k=0;k<pageList.size();k++){
         memormk="Y";
     }}
     String note_person="N";
     pageList=(ArrayList)pageDtl.get("NOTE_PERSON");
     if(pageList!=null && pageList.size() >0){
         for(int k=0;k<pageList.size();k++){
         note_person="Y";
     }}
     
    String sl_slip="N";
     pageList=(ArrayList)pageDtl.get("SL_SLIP");
     if(pageList!=null && pageList.size() >0){
         for(int k=0;k<pageList.size();k++){
         sl_slip="Y";
     }}
    String performa="N";
     pageList=(ArrayList)pageDtl.get("PERFORMA");
     if(pageList!=null && pageList.size() >0){
         for(int k=0;k<pageList.size();k++){
         performa="Y";
     }}
     String vigat="N";
     pageList=(ArrayList)pageDtl.get("VIGAT");
     if(pageList!=null && pageList.size() >0){
         for(int k=0;k<pageList.size();k++){
         vigat="Y";
     }}
     
     String dateFilter="Y";
     pageList=(ArrayList)pageDtl.get("DATEFILTER");
     if(pageList!=null && pageList.size() >0){
         for(int k=0;k<pageList.size();k++){
         pageDtlMap=(HashMap)pageList.get(k);
         dflt_val = util.nvl((String)pageDtlMap.get("dflt_val"));
         ArrayList roleList = info.getRoleLst();
          String usrFlg=util.nvl((String)info.getUsrFlg());
         if(roleList==null)
         roleList=new ArrayList();
         if(roleList.indexOf(dflt_val)==-1)
           dateFilter="N";
          if(usrFlg.equals("SYS"))
             dateFilter="Y";
     }}
     String action="report/dailySalesReport.do?method=load";
     if(pktTy.equals("MIX") && cnt.equals("kj"))
      action="report/dailyMixSalesReport.do?method=load";
      %>
   <table cellpadding="" cellspacing="" width="80%" class="mainbg">
   <tr>
    <td height="103" valign="top">
   <jsp:include page="/header.jsp" />
    
    </td>
  </tr>
    <tr>
    <td height="5" valign="top" class="greyline"><img src="../images/grey_linebig.png" width="685" height="5" align="right" /></td>
  </tr>
   <tr><td valign="top" class="hedPg"  >
  <table cellpadding="1" cellspacing="5"><tr><td valign="middle">
  <img src="../images/line.jpg" border="0" /></td><td valign="middle"> <span class="pgHed">
Daily Sales Report
 
  </span> </td>
</tr></table>
  </td>
  </tr>
   <tr>
   <td valign="top" class="hedPg">
    <html:form action="<%=action%>" method="post" >
        <input type="hidden" id="reqUrlPage" name="reqUrl" value="<%=info.getReqUrl()%>"/>
           <html:hidden property="value(isLS)" styleId="isLS"   />
        <html:hidden property="value(PKT_TY)" styleId="pkt_ty"   />
        <html:hidden property="value(memormk)" styleId="memormk"   value="<%=memormk%>"/>
    <html:hidden property="value(note_person)" styleId="note_person"   value="<%=note_person%>"/>
    <html:hidden property="value(sl_slip)" styleId="sl_slip"   value="<%=sl_slip%>"/>
    <html:hidden property="value(performa)" styleId="performa"   value="<%=performa%>"/>
    <html:hidden property="value(vigat)" styleId="vigat"   value="<%=vigat%>"/>
<input type="hidden" name="webUrl" id="webUrl" value="<%=dbinfo.get("REP_URL")%>"/>
<input type="hidden" name="cnt" id="cnt" value="<%=cnt%>"/>
<input type="hidden" name="repUrl" id="repUrl" value="<%=dbinfo.get("HOME_DIR")%>"/>
<input type="hidden" name="repPath" id="repPath" value="<%=dbinfo.get("REP_PATH")%>"/>
<input type="hidden" name="accessidn" id="accessidn" value="<%=accessidn%>"/>
<input type="hidden" name="vluininr" id="vluininr" value="<%=displayvluInr%>"/>
    <table>
    <tr>
    <%if(dateFilter.equals("Y")){%>
    <td>Date From : </td>
    <td>
    <html:text property="value(dtefr)" styleClass="txtStyle"  styleId="dtefr"  size="12"  /> <a href="#" style="margin-top:5px;"  onClick="setYears(1900, '<%=info.getCurrentYear()%>' );showCalender(this, 'dtefr');">
                <img src="<%=info.getReqUrl()%>/images/calender.png"></a>
    </td>
    <td>Date To : </td>
    <td>
    <html:text property="value(dteto)" styleClass="txtStyle"  styleId="dteto"  size="12"  /> <a href="#"  onClick="setYears(1900, '<%=info.getCurrentYear()%>' );showCalender(this, 'dteto');">
                <img src="<%=info.getReqUrl()%>/images/calender.png"></a>
    </td>
    <%}%>
      <td nowrap="nowrap"><span class="txtBold" >Sale Person : </span>
      <%
      ArrayList salepersonList = ((ArrayList)info.getSaleperson() == null)?new ArrayList():(ArrayList)info.getSaleperson();
      %>
      <html:select name="dailySalesReportForm" property="value(styp)" styleId="saleEmp">
      <html:option value="0">---Select---</html:option>
      <%
      for(int i=0;i<salepersonList.size();i++)
      {
      ArrayList saleperson=(ArrayList)salepersonList.get(i);
      %>
      <html:option value="<%=(String)saleperson.get(0)%>"> <%=(String)saleperson.get(1)%> </html:option>
      <%
      }
      %>
      </html:select>
      
      </td>
      <td>Buyer</td>
      <td nowrap="nowrap">
   
    <%
    String sbUrl ="ajaxRptAction.do?method=buyerSugg";
    %>
    <input type="text" id="nme" name="nme" autocomplete="off" class="sugBox"
      onKeyUp="doCompletion('nme', 'nmePop', 'ajaxRptAction.do?method=buyerSugg', event)" 
      onKeyDown="setDown('nmePop', 'nmeID', 'nme',event)"/>
 <img src="../images/addrow.jpg" width="17" height="17" title="Click To get Names" onclick="doCompletionMobile('nme', 'nmePop', 'ajaxRptAction.do?method=buyerSugg')">
 <input type="hidden" name="nmeID" id="nmeID" value="">
  <div style="position: absolute;">
      <select id="nmePop" name="nmePop"
        style="display:none;300px;" 
        class="sugBoxDiv"
        onKeyDown="setDown('nmePop', 'nmeID', 'nme',event)" 
        onDblClick="setVal('nmePop', 'nmeID', 'nme', event);hideObj('nmePop')" 
        onClick="setVal('nmePop', 'nmeID', 'nme', event);" 
        size="10">
      </select>
</div>
  </td>
     <%
     if(cnt.equals("hk")){
ArrayList grpList = ((ArrayList)info.getGroupcompany() == null)?new ArrayList():(ArrayList)info.getGroupcompany();
   if(grpList!=null && grpList.size()> 0){
   %>
      
      <td>Group Company</td>
      <td><html:select name="dailySalesReportForm" property="value(group)" styleId="grp">
      <html:option value="">---Select---</html:option>
      <%
      for(int i=0;i<grpList.size();i++)
      {
      ArrayList grp=(ArrayList)grpList.get(i);
      %>
      <html:option value="<%=(String)grp.get(0)%>"> <%=(String)grp.get(1)%> </html:option>
      <%
      }
      %>
      </html:select>
      </td>
      <%}}%>
     
      <%pageList=(ArrayList)pageDtl.get("USRLOC");
      if(pageList!=null && pageList.size() >0){%>
      <td>User Location</td>
       <td><html:select property="value(usrLoc)"  name="dailySalesReportForm">
          <html:option value="">----select----</html:option>
          <html:option value="IND">INDIA</html:option><html:option value="HK">HK</html:option>
        </html:select></td>
        <td>Status</td>
        <td><html:select property="value(sttTyp)"  name="dailySalesReportForm">
          <html:option value="">----select----</html:option>
          <html:option value="IS">Waiting For Payment</html:option>
          <html:option value="PR">Payment Recive</html:option>
        </html:select></td>
       <%}%>
      
       <td><html:submit property="value(submit)" value="Fetch" styleClass="submit"/>
       </td>
                
                
    
    </tr></table>
  </html:form>
  </td>

  </tr>
   <tr><td class="hedPg">
  <%
 HashMap saletbl=(HashMap)request.getAttribute("saleTbl");
 HashMap totalSale=(HashMap)request.getAttribute("totalSaleTbl");
 HashMap grandDtl=(HashMap)request.getAttribute("grandtotal");
  %>
  <!--<tr><td class="hedPg">
  &nbsp;&nbsp;&nbsp;Create Excel <img src="../images/ico_file_excel.png" onclick="goToCreateExcel('dailySalesReport.do?method=pktDtlExcel','','')" border="0"/> 
  </td></tr>-->   
   <%if(saletbl!=null && saletbl.size()> 0){%>
   <%pageList=(ArrayList)pageDtl.get("PKTDTL");
      if(pageList!=null && pageList.size() >0){
      for(int j=0;j<pageList.size();j++){
      pageDtlMap=(HashMap)pageList.get(j);
       dflt_val = util.nvl((String)pageDtlMap.get("dflt_val"));
       fld_ttl = util.nvl((String)pageDtlMap.get("fld_ttl"));
       if(dflt_val.equals("")){
       dflt_val ="dailySalesReport.do?method=pktDtlExcel&pktTy="+pktTy; 
       if(pktTy.equals("MIX") && cnt.equals("kj"))
       dflt_val ="dailyMixSalesReport.do?method=pktDtlExcel&pktTy="+pktTy; 
       fld_ttl = "Packet Details";
       }
      %>
    
  &nbsp;&nbsp;&nbsp;<%=fld_ttl%> <img src="../images/ico_file_excel.png" onclick="goToCreateExcel('<%=dflt_val%>','','')" border="0"/> 
 
  
      <%}}%>
      &nbsp;&nbsp;Check All&nbsp;<input type="checkbox" name="All" id="All" onclick="checkAllpage(this,'XL_')"/>
 
      </td></tr>
   <tr><td class="hedPg" >
   <table class="grid1" width="60%">
      <%
   String salename="";
   String newsaleId="";
   String saleid="";
   String dpslName="";
   String lastTtlqty="";
   String lastTtlcts="";
   String lastTtlvlu="";
   String lastTtlfnlexhvlu="";
   String grndTtlqty="";
   String grndTtlcts="";
   String grndTtlvlu="";
   String grndTtlfnlexhvlu="";
   int loop=0;
   for(int i=1;i<=saletbl.size();i++)
   {
      String styleClass=null;
   int cls=i % 2;
   if(cls==0)
   {
    styleClass = "odd" ;
   }else
   {
    styleClass = "even" ;
   }//class="<%= styleClass
   %>
    <tr>
    <%
     HashMap saledtl=(HashMap)saletbl.get(i);
     
      salename=util.nvl((String)saledtl.get("saleName"));
     newsaleId=util.nvl((String)saledtl.get("sale_id"));
      
       %>
      <td><table width="100%" >
      <%
       if(dpslName.equals("") || !dpslName.equals(salename) )
      {
      if(dpslName.equals(""))
      {
      dpslName=salename;
      saleid=newsaleId;
            %>
      <tr><td><b>Sales Person : <%=(String)saledtl.get("saleName") %></b></td><td colspan="5" width=""></td>
      </tr>
      <tr ><th>Buyer</th><th>Date</th><th>Qty</th><th>Cts</th><th>Vlu</th>
      <th style="<%=displayvluInr%>">Vlu In INR</th>
      
      </tr>
       <%
      }
      if(!dpslName.equals(salename))
      {
      dpslName=salename;
      HashMap totalDtl=(HashMap)totalSale.get(saleid);
      String ttlqty=util.nvl((String)totalDtl.get("qty"));
      String ttlcts=util.nvl((String)totalDtl.get("cts"));
      String ttlvlu=util.nvl((String)totalDtl.get("vlu"));
      String ttlfnlexhvlu=util.nvl((String)totalDtl.get("fnlexhvlu"));
      saleid=newsaleId;
      HashMap lasttotalDtl=(HashMap)totalSale.get(newsaleId);
      lastTtlqty=util.nvl((String)lasttotalDtl.get("qty"));
      lastTtlcts=util.nvl((String)lasttotalDtl.get("cts"));
      lastTtlvlu=util.nvl((String)lasttotalDtl.get("vlu"));
      lastTtlfnlexhvlu=util.nvl((String)lasttotalDtl.get("fnlexhvlu"));
      %>
      <tr><td align="left"><b>Total:</b></td><td></td><td align="right"><B><%=ttlqty%></b></td><td align="right"><B><%=ttlcts%></b></td><td align="right"><B><%=ttlvlu%></b></td><td align="right" style="<%=displayvluInr%>"><B><%=ttlfnlexhvlu%></b></td>
      </tr>
      <tr><td><b>Sales Person : <%=(String)saledtl.get("saleName") %></b></td><td colspan="5" width=""></td>
      </tr>
        <tr><th>Buyer</th><th>Date</th><th>Qty</th><th>Cts</th><th>Vlu</th><th style="<%=displayvluInr%>">Vlu In INR</th></tr>
    <%  }
      }
    
      %>
      <tr> <td  align="left"  width="40%">
      <%
      String byrid=util.nvl((String)saledtl.get("byrid"));
      String typ=util.nvl((String)saledtl.get("typ"));
      String dte=(util.nvl((String)saledtl.get("dte"))).trim();
      pageList=(ArrayList)pageDtl.get("CHECKBOX");
      if(pageList!=null && pageList.size() >0){
      for(int j=0;j<pageList.size();j++){
      pageDtlMap=(HashMap)pageList.get(j);
      fld_nme=(String)pageDtlMap.get("fld_nme");
      dflt_val=(String)pageDtlMap.get("dflt_val");
      fld_nme=fld_nme.replaceAll("BYRID",byrid);
      fld_nme=fld_nme.replaceAll("DTE",dte);
      dflt_val=dflt_val.replaceAll("BYRID",byrid);
      dflt_val=dflt_val.replaceAll("DTE",dte);%>
      <input type="checkbox" name="<%=fld_nme%>" id="<%=fld_nme%>" value="<%=dflt_val%>" />
      <%}}%>
      <%=(util.nvl((String)saledtl.get("byr"))).trim() %></td>
      <td align="center" width="10%"><%=(util.nvl((String)saledtl.get("dte"))).trim() %></td>
      <td align="right" width="10%"><a onclick="callDailySalPkt('<%=byrid%>','<%=loop%>','<%=typ%>','<%=dte%>')" itle="Click Here To See Details" style="text-decoration:underline">
      <%=(util.nvl((String)saledtl.get("qty"))).trim() %></a> </td>
      <td align="right" width="10%"><%=(util.nvl((String)saledtl.get("cts"))).trim() %></td>
      <td align="right" width="20%"><%=(util.nvl((String)saledtl.get("vlu"))).trim() %></td>
      <td align="right" width="20%" style="<%=displayvluInr%>"><%=(util.nvl((String)saledtl.get("fnlexhvlu"))).trim() %></td></tr>
      </table></td>
     </tr>
       <tr id="BYRTRDIV_<%=loop%>" style="display:none">
                      <td colspan="18"> 
                      <div id="BYR_<%=loop%>"  align="center">
                      </div>
                      <%
                      loop++;%>
                      </td>
        </tr>
     <%
     }
       if(saletbl.size()==1)
      {
        HashMap lasttotalDtl=(HashMap)totalSale.get(newsaleId);
      lastTtlqty=util.nvl((String)lasttotalDtl.get("qty"));
      lastTtlcts=util.nvl((String)lasttotalDtl.get("cts"));
      lastTtlvlu=util.nvl((String)lasttotalDtl.get("vlu"));
      lastTtlfnlexhvlu=util.nvl((String)lasttotalDtl.get("fnlexhvlu"));
      }
     %>
     <tr><td><table width="100%">
     <tr><td  align="left"   width="50%"><b>Total:</b></td><td align="right" width="10%"><b><%=lastTtlqty%></b></td><td align="right" width="10%"><b><%=lastTtlcts%></b></td><td align="right" width="20%" ><b><%=lastTtlvlu%></b></td><td align="right" width="20%" style="<%=displayvluInr%>" ><b><%=lastTtlfnlexhvlu%></b></td>
      </tr>
     </table></td></tr>
     <%
      grndTtlqty=util.nvl((String)grandDtl.get("qty"));
      grndTtlcts=util.nvl((String)grandDtl.get("cts"));
      grndTtlvlu=util.nvl((String)grandDtl.get("vlu"));
      grndTtlfnlexhvlu=util.nvl((String)grandDtl.get("fnlexhvlu"));
     %>
      <tr><td><table width="100%">
     <tr><td  align="left"   width="50%"><b>Grand Total:</b></td><td align="right" width="10%"><B><%=grndTtlqty%></b></td><td align="right" width="10%"><B><%=grndTtlcts%></b></td><td align="right" width="20%" ><B><%=grndTtlvlu%></b></td><td align="right" width="20%"  style="<%=displayvluInr%>"><B><%=grndTtlfnlexhvlu%></b></td>
      </tr>
       <input type="hidden" id="count" value="<%=loop%>" />
     </table></td></tr>
   </table></td></tr><%}else{%>
   <tr><td class="hedPg">Sorry no result found </td></tr>
   <%}%>
   </td></tr>
   <tr>
    <td><jsp:include page="/footer.jsp" /> </td>
    </tr>
  </table>
  <%@include file="/calendar.jsp"%>
  </body>
</html>