<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page errorPage="../error_page.jsp" %>
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.ResultSet, java.util.ArrayList,java.util.Collections,java.util.ArrayList,java.util.HashMap,java.text.SimpleDateFormat, java.util.Calendar"%>
<jsp:useBean id="info" class="ft.com.InfoMgr" scope="session" />
<jsp:useBean id="util" class="ft.com.JspUtil" scope="page" />
<%@taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
 

<html>
   <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/bse.js?v=<%=info.getJsVersion()%> " > </script>
<link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/bse.css?v=<%=info.getJsVersion()%>"/>
<link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/general.css?v=<%=info.getJsVersion()%>"/>
<script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/ajax.js?v=<%=info.getJsVersion()%> " > </script>
  <script type="text/javascript" src="<%=info.getReqUrl()%>/PopScripts/popup.js?v=<%=info.getJsVersion()%> " > </script>
    <script src="<%=info.getReqUrl()%>/PopScripts/jquery-1.2.6.min.js" type="text/javascript"></script>
       <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/memoScript.js?v=<%=info.getJsVersion()%> " > </script>
   <script language="javaScript" type="text/javascript" src="<%=info.getReqUrl()%>/calendar/calendar.js"></script>
   <link href="<%=info.getReqUrl()%>/calendar/calendar.css" rel="stylesheet" type="text/css" />

 
      <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/style.css?v=<%=info.getJsVersion()%>"/>

  <title>Daily Memo Report</title>
 
  </head>
        <%String logId=String.valueOf(info.getLogId());
        String onfocus="cook('"+logId+"')";
        %>
 <body onfocus="<%=onfocus%>" onkeypress="return disableEnterKey(event);" >
     <%
    HashMap allPageDtl= (info.getPageDetails() == null)?new HashMap():(HashMap)info.getPageDetails();
    HashMap pageDtl=(HashMap)allPageDtl.get("DAILY_MEMO_RPT");
    String pktTy=util.nvl((String)request.getAttribute("pktTy"));

    ArrayList pageList=new ArrayList();
    HashMap pageDtlMap=new HashMap();
    String fld_nme="",fld_ttl="",val_cond="",dflt_val="",fld_typ="",form_nme="",flg1="";
       HashMap dbinfo = info.getDmbsInfoLst();
       String cnt =(String)dbinfo.get("CNT");
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
Daily Memo Report
 
  </span> </td>
</tr></table>
  </td>
  </tr>
   <tr>
  <td valign="top" class="hedPg">
    <html:form action="report/dailyMemoReport.do?method=load" method="post" >
        <html:hidden property="value(memormk)" styleId="memormk"   value="<%=memormk%>"/>
    <html:hidden property="value(note_person)" styleId="note_person"   value="<%=note_person%>"/>
    <table><tr>
    <%if(dateFilter.equals("Y")){%>
    <td>Date From : </td><td>
    <html:text property="value(dtefr)" styleClass="txtStyle"  styleId="dtefr"  size="12"  /> <a href="#" style="margin-top:5px;"  onClick="setYears(1900, '<%=info.getCurrentYear()%>' );showCalender(this, 'dtefr');">
                <img src="<%=info.getReqUrl()%>/images/calender.png"></a></td>
                <td>Date To : </td><td>
    <html:text property="value(dteto)" styleClass="txtStyle"  styleId="dteto"  size="12"  /> <a href="#"  onClick="setYears(1900, '<%=info.getCurrentYear()%>' );showCalender(this, 'dteto');">
                <img src="<%=info.getReqUrl()%>/images/calender.png"></a></td>
                <%}%>
          <td><span class="txtBold" >Sale Person : </span>
      <%
       ArrayList salepersonList = ((ArrayList)info.getSaleperson() == null)?new ArrayList():(ArrayList)info.getSaleperson();
      %>
      <html:select name="dailymemoReportForm" property="value(styp)" styleId="saleEmp">
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
      <td><html:select name="dailymemoReportForm" property="value(group)" styleId="grp">
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
      <%pageList=(ArrayList)pageDtl.get("TYP");
      if(pageList!=null && pageList.size() >0){%>
      <td>Memo Type</td>
      <td><html:select property="value(typ)"  name="dailymemoReportForm">
      <html:option value="">----select----</html:option>
      <%for(int j=0;j<pageList.size();j++){
      pageDtlMap=(HashMap)pageList.get(j);
       dflt_val = util.nvl((String)pageDtlMap.get("dflt_val"));
       fld_ttl = util.nvl((String)pageDtlMap.get("fld_ttl"));
       %>
          <html:option value="<%=dflt_val%>"><%=fld_ttl%></html:option>
        <%}%>
        </html:select></td>
       <%}%>
       <td><html:submit property="value(submit)" value="Fetch" styleClass="submit"/>
       </td>
                </tr></table>
  </html:form>
  </td></tr>
 
  <%
HashMap memoDtl=(HashMap)request.getAttribute("memoDtl");
HashMap byrDtl=(HashMap)request.getAttribute("byrDtl");
ArrayList empList=(ArrayList)request.getAttribute("empList");
ArrayList memotyp=(ArrayList)request.getAttribute("memotyp");
ArrayList Display_typ=(ArrayList)request.getAttribute("Display_typ");
HashMap byr_idn=(HashMap)request.getAttribute("byr_idn");
 String emp="";
 String byr="";
 String copybyr="";
 ArrayList dtl=new ArrayList();
 int loop=0;
%>
  <tr><td class="hedPg">
    <%if(empList!=null && empList.size()> 0){%>
  <!--<tr><td class="hedPg">
  &nbsp;&nbsp;&nbsp;Create Excel <img src="../images/ico_file_excel.png" onclick="goToCreateExcel('dailySalesReport.do?method=pktDtlExcel','','')" border="0"/> 
  </td></tr>-->   
   <%if(empList!=null && empList.size()> 0){%>
   <%pageList=(ArrayList)pageDtl.get("PKTDTL");
      if(pageList!=null && pageList.size() >0){
      for(int j=0;j<pageList.size();j++){
      pageDtlMap=(HashMap)pageList.get(j);
       dflt_val = util.nvl((String)pageDtlMap.get("dflt_val"));
       fld_ttl = util.nvl((String)pageDtlMap.get("fld_ttl"));
       if(dflt_val.equals("")){
       dflt_val ="dailyMemoReport.do?method=pktDtlExcel&pktTy="+pktTy; 
       fld_ttl = "Packet Details";
       }
      %>
    
  &nbsp;&nbsp;&nbsp;<%=fld_ttl%> <img src="../images/ico_file_excel.png" onclick="goToCreateMemoExcel('<%=dflt_val%>','','')" border="0"/> 
 
        &nbsp;&nbsp;Check All&nbsp;<input type="checkbox" name="All" id="All" onclick="checkAllpage(this,'XL_')"/>

      <%}}}%>
 
      </td></tr>
 <tr><td class="hedPg" >
   <table class="grid1" width="60%">
       <tr>
     <% pageList=(ArrayList)pageDtl.get("CHECKBOX");
        if(pageList!=null && pageList.size() >0){%>
        <th><div style="width:10px;"></div></th>
        <%}%>
        <th><div style="width:150px;">Sale Person</div></th>
        <th><div style="width:250px;">Buyer</div></th>
         <% for(int k=0;k<Display_typ.size();k++){%>
        <th colspan="4"><%=Display_typ.get(k)%></th>
        <%}%>
      </tr>

      <tr>
      <td></td>
      <td></td>
       <% for(int k=0;k<memotyp.size();k++){%>
       <td align="center">Cnt</td>
       <td align="center">Qty</td>
       <td align="center">Cts</td>
       <td align="center">Vlu</td>
      <%}%>
      </tr>
          <%for(int i=0;i<empList.size();i++){
          emp=(String)empList.get(i);
        %>
          
             <%
              for(int j=0;j<memoDtl.size();j++){
              byr=(String)byrDtl.get(emp+"_"+j);
              if(byr!=null && !byr.equals("") && !byr.equals(copybyr)){
                  %>
                  <tr>     
                <% String byrid=util.nvl((String)byr_idn.get(emp+"_"+byr));
                   String memoDte=util.nvl((String)byr_idn.get(emp+"_"+byr+"_DTE"));
        pageList=(ArrayList)pageDtl.get("CHECKBOX");
      if(pageList!=null && pageList.size() >0){
      for(int k=0;k<pageList.size();k++){
      pageDtlMap=(HashMap)pageList.get(k);
      fld_nme=(String)pageDtlMap.get("fld_nme");
      dflt_val=(String)pageDtlMap.get("dflt_val");
      fld_nme=fld_nme.replaceAll("BYRID",byrid);
      fld_nme=fld_nme.replaceAll("DTE",memoDte);
      dflt_val=dflt_val.replaceAll("BYRID",byrid);
      dflt_val=dflt_val.replaceAll("DTE",memoDte);%>
    <td>   <input type="checkbox" name="<%=fld_nme%>" id="<%=fld_nme%>" value="<%=dflt_val%>" /></td>
      <%}}%>
                  <td><%=emp%></td>
                  <td><%=byr%></td>
                  <% for(int k=0;k<memotyp.size();k++){
                        dtl=(ArrayList)memoDtl.get(emp+"_"+byr+"_"+memotyp.get(k));
                        if(dtl!=null && dtl.size()> 0){
                            for(int l=k;l<=k;l++){
                            %>
                            <td align="center"><a onclick="callMemoReportPkt('<%=byr_idn.get(emp+"_"+byr)%>','<%=loop%>','<%=memotyp.get(k)%>')" title="Click Here To See Details" style="text-decoration:underline"><%=dtl.get(0)%></a> </td>
                            <td align="center"><%=dtl.get(1)%></td>
                            <td align="center"><%=dtl.get(2)%></td>
                            <td align="center"><%=dtl.get(3)%></td>
                            <%
                            }
                         }else{%>
                            <td align="center"></td>
                            <td align="center"></td>
                            <td align="center"></td>
                            <td align="center"></td>
                            <%
                          }
                    }%>
                 </tr>
                  <tr id="BYRTRDIV_<%=loop%>" style="display:none">
                      <td colspan="18"> 
                      <div id="BYR_<%=loop%>"  align="center">
                      </div>
                      <%
                      loop++;%>
                      </td>
                 </tr>
                <%}
                copybyr=byr;
              }
           }%>
      <input type="hidden" id="count" value="<%=loop%>" />
  </table>
   <%}else{%>
   Sorry no result found
   <%}%>
   </td>
   </tr>



   <tr>
    <td><jsp:include page="/footer.jsp" /> </td>
    </tr>
  </table>
  <%@include file="/calendar.jsp"%>
  </body>
</html>