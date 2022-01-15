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
         <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/memoScript.js?v=<%=info.getJsVersion()%> " > </script>
    <script src="<%=info.getReqUrl()%>/PopScripts/jquery-1.2.6.min.js" type="text/javascript"></script>
   <script language="javaScript" type="text/javascript" src="<%=info.getReqUrl()%>/calendar/calendar.js"></script>
   <link href="<%=info.getReqUrl()%>/calendar/calendar.css" rel="stylesheet" type="text/css" />

 
      <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/style.css?v=<%=info.getJsVersion()%>"/>

  <title>Daily Consignment Report</title>
 
  </head>
        <%String logId=String.valueOf(info.getLogId());
        String onfocus="cook('"+logId+"')";
        %>
 <body onfocus="<%=onfocus%>" onkeypress="return disableEnterKey(event);" >
   <%
     HashMap dbinfo = info.getDmbsInfoLst();
     String cnt =(String)dbinfo.get("CNT");
    HashMap allPageDtl= (info.getPageDetails() == null)?new HashMap():(HashMap)info.getPageDetails();
    HashMap pageDtl=(HashMap)allPageDtl.get("DAILY_CONSIGNMENT_RPT");
    ArrayList pageList=new ArrayList();
    HashMap pageDtlMap=new HashMap();
    String fld_nme="",fld_ttl="",val_cond="",dflt_val="",fld_typ="",form_nme="",flg1="";
    
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
Daily Consignment Report
 
  </span> </td>
</tr></table>
  </td>
  </tr>
   <tr>
   <td valign="top" class="hedPg">
    <html:form action="report/dailyconsignmentReport.do?method=load" method="post" onsubmit="return verifyDailyApproveRpt();" >
        <html:hidden property="value(memormk)" styleId="memormk"   value="<%=memormk%>"/>
    <html:hidden property="value(note_person)" styleId="note_person"   value="<%=note_person%>"/>
    <html:hidden property="value(memotbl)" styleId="memotbl" name="dailyConsignmentReportForm" />
    <table id="dailyApprove"><tr>
    <td><table><tr>
    <%if(dateFilter.equals("Y")){%>
    <td nowrap="nowrap">Date From : </td><td nowrap="nowrap">
    <html:text property="value(dtefr)" styleClass="txtStyle"  styleId="dtefr"  size="12"  /> <a href="#" style="margin-top:5px;"  onClick="setYears(1900, '<%=info.getCurrentYear()%>' );showCalender(this, 'dtefr');">
                <img src="<%=info.getReqUrl()%>/images/calender.png"></a></td>
                <td nowrap="nowrap">Date To : </td><td nowrap="nowrap">
    <html:text property="value(dteto)" styleClass="txtStyle"  styleId="dteto"  size="12"  /> <a href="#"  onClick="setYears(1900, '<%=info.getCurrentYear()%>' );showCalender(this, 'dteto');">
                <img src="<%=info.getReqUrl()%>/images/calender.png"></a></td>
                <%}%>
         <td nowrap="nowrap"><span class="txtBold" >Sale Person : </span>
      <%
      ArrayList salepersonList =((ArrayList)info.getSaleperson() == null)?new ArrayList():(ArrayList)info.getSaleperson();
      %>
      <html:select name="dailyConsignmentReportForm" property="value(styp)" styleId="saleEmp">
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
      <td nowrap="nowrap">Buyer</td>
      <td  nowrap="nowrap">
   
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
      
      <td nowrap="nowrap">Group Company</td>
      <td nowrap="nowrap"><html:select name="dailyConsignmentReportForm" property="value(group)" styleId="grp">
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
      </tr>
      </table></td></tr>
      <tr><td><table><tr>
      <%pageList=(ArrayList)pageDtl.get("MEMO_TYP");
      if(pageList!=null && pageList.size() >0){%>
      <td nowrap="nowrap">Memo Type</td>
      <td nowrap="nowrap">
     <html:select property="value(typ)" styleId="typId" name="dailyConsignmentReportForm">
             <html:option value="">--Select--</html:option>
             <%
               for(int j=0;j<pageList.size();j++){
                 pageDtlMap=(HashMap)pageList.get(j);
                 fld_ttl=(String)pageDtlMap.get("fld_ttl");
                 dflt_val=(String)pageDtlMap.get("dflt_val");
                 %>
                <html:option value="<%=dflt_val%>"><%=fld_ttl%></html:option>
             <%}%>
            </html:select>
            </td>
            <%}%>
      <%pageList=(ArrayList)pageDtl.get("MEMOID");
      if(pageList!=null && pageList.size() >0){%>
      <td>Memo Id</td> 
             <%
               for(int j=0;j<pageList.size();j++){
                 pageDtlMap=(HashMap)pageList.get(j);
                 fld_nme="value("+(String)pageDtlMap.get("fld_nme")+")";
                 fld_ttl=(String)pageDtlMap.get("fld_ttl");
                 dflt_val=(String)pageDtlMap.get("dflt_val");
                 %>
     <td><%=dflt_val%><html:text property="<%=fld_nme%>" styleClass="txtStyle"  styleId="<%=fld_ttl%>"  size="10"  /></td>
             <%}%>
            <%}%>
       <td><html:submit property="value(submit)" value="Fetch" styleClass="submit"/>
       </td></tr></table></td>
       </tr></table>
  </html:form>
  </td>
  </tr>
 
  <%
 HashMap approveTbl=(HashMap)request.getAttribute("approveTbl");
 HashMap totalapprove=(HashMap)request.getAttribute("totalapprove");
 HashMap grandDtl=(HashMap)request.getAttribute("grandtotal");
  %>
  
   <tr><td class="hedPg" >
   <%if(approveTbl!=null && approveTbl.size()> 0){%>
   <table class="grid1" width="60%">
      <%pageList=(ArrayList)pageDtl.get("PKTDTL");
      if(pageList!=null && pageList.size() >0){%>
      <tr><td>
     <% for(int j=0;j<pageList.size();j++){
      pageDtlMap=(HashMap)pageList.get(j);
       dflt_val = util.nvl((String)pageDtlMap.get("dflt_val"));
       fld_ttl = util.nvl((String)pageDtlMap.get("fld_ttl"));
       if(dflt_val.equals("")){
       dflt_val ="dailyconsignmentReport.do?method=pktDtlExcel"; 
       fld_ttl = "Packet Details";
       }
      %>
     
   &nbsp;&nbsp;&nbsp;<%=fld_ttl%> <img src="../images/ico_file_excel.png" onclick="goToCreateApproveExcel('<%=dflt_val%>','','')" border="0"/> 
  
      <%}%>
      &nbsp;&nbsp;Check All&nbsp;<input type="checkbox" name="All" id="All" onclick="checkAllpage(this,'XL_')"/>
      <%}%>
       
 
       </td></tr>
      <%
   String salename="";
   String newapproveid="";
   String approveid="";
   String dpslName="";
   String lastTtlqty="";
   String lastTtlcts="";
   String lastTtlvlu="";
   String grndTtlqty="";
   String grndTtlcts="";
   String grndTtlvlu="";
   int loop=0;
   for(int i=1;i<=approveTbl.size();i++)
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
     HashMap approvedtl=(HashMap)approveTbl.get(i);
     
      salename=util.nvl((String)approvedtl.get("salename"));
     newapproveid=util.nvl((String)approvedtl.get("approve_id"));
      
       %>
      <td><table width="100%" >
      <%
       if(dpslName.equals("") || !dpslName.equals(salename) )
      {
      if(dpslName.equals(""))
      {
      dpslName=salename;
      approveid=newapproveid;
            %>
      <tr><td><b>Sales Person : <%= (String)approvedtl.get("salename") %></b></td><td colspan="4" width=""></td>
      </tr>
      <tr ><th>Buyer</th><th>Date</th><th>Qty</th><th>Cts</th><th>Vlu</th></tr>
       <%
      }
      if(!dpslName.equals(salename))
      {
      dpslName=salename;
      HashMap totalDtl=(HashMap)totalapprove.get(approveid);
      String ttlqty=util.nvl((String)totalDtl.get("qty"));
      String ttlcts=util.nvl((String)totalDtl.get("cts"));
      String ttlvlu=util.nvl((String)totalDtl.get("vlu"));
      approveid=newapproveid;
      HashMap lasttotalDtl=(HashMap)totalapprove.get(newapproveid);
      lastTtlqty=util.nvl((String)lasttotalDtl.get("qty"));
      lastTtlcts=util.nvl((String)lasttotalDtl.get("cts"));
      lastTtlvlu=util.nvl((String)lasttotalDtl.get("vlu"));
      %>
      <tr><td align="left"><b>Total:</b></td><td></td><td align="right"><B><%=ttlqty%></b></td><td align="right"><B><%=ttlcts%></b></td><td align="right"><B><%=ttlvlu%></b></td>
      </tr>
      <tr><td><b>Sales Person : <%=(String)approvedtl.get("salename") %></b></td><td colspan="4" width=""></td>
      </tr>
        <tr ><th>Buyer</th><th>Date</th><th>Qty</th><th>Cts</th><th>Vlu</th></tr>
    <%  }
      }
    
      %>
      <tr> <td  align="left"  width="40%">
      <%
      String byrid=util.nvl((String)approvedtl.get("byrid"));
      String typ=util.nvl((String)approvedtl.get("typ"));
      String dte=(util.nvl((String)approvedtl.get("dte"))).trim();
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
      <%=(util.nvl((String)approvedtl.get("byr"))).trim() %></td>
      <td align="center" width="10%"><%=(util.nvl((String)approvedtl.get("dte"))).trim() %></td>
      <td align="right" width="10%">
      <!--<a onclick="callMemoApproveReportPkt('<%=util.nvl((String)approvedtl.get("byrid"))%>','<%=loop%>','<%=util.nvl((String)approvedtl.get("typ"))%>','<%=(util.nvl((String)approvedtl.get("dte"))).trim() %>')" title="Click Here To See Details" style="text-decoration:underline">-->
      <a onclick="callMemoConsignmentReportPkt('<%=util.nvl((String)approvedtl.get("byrid"))%>','<%=loop%>','DAP','<%=cnt%>','<%=(util.nvl((String)approvedtl.get("dte"))).trim() %>')" title="Click Here To See Details" style="text-decoration:underline">
      <%=(util.nvl((String)approvedtl.get("qty"))).trim() %></a> 
      
      </td>
      <td align="right" width="10%"><%=(util.nvl((String)approvedtl.get("cts"))).trim() %></td>
      <td align="right" width="20%"><%=(util.nvl((String)approvedtl.get("vlu"))).trim() %></td></tr>
      </table></td>
     </tr>
                 <tr id="BYRTRDIV_<%=loop%>" style="display:none">
                      <td colspan="18"> 
                      <div id="BYR_<%=loop%>" align="center">
                      </div>
                      <%
                      loop++;%>
                      </td>
                </tr>
     <%
     }
       if(approveTbl.size()==1)
      {
        HashMap lasttotalDtl=(HashMap)totalapprove.get(newapproveid);
      lastTtlqty=util.nvl((String)lasttotalDtl.get("qty"));
      lastTtlcts=util.nvl((String)lasttotalDtl.get("cts"));
      lastTtlvlu=util.nvl((String)lasttotalDtl.get("vlu"));
      }
     %>
     <tr>
     <td><table width="100%">
     <tr><td  align="left"   width="50%"><b>Total:</b></td><td align="right" width="10%"><b><%=lastTtlqty%></b></td><td align="right" width="10%"><b><%=lastTtlcts%></b></td><td align="right" width="20%" ><b><%=lastTtlvlu%></b></td>
      </tr>
     </table></td>
     </tr>
     <%
      grndTtlqty=util.nvl((String)grandDtl.get("qty"));
      grndTtlcts=util.nvl((String)grandDtl.get("cts"));
      grndTtlvlu=util.nvl((String)grandDtl.get("vlu"));
     %>
      <tr><td><table width="100%">
     <tr><td  align="left"   width="50%"><b>Grand Total:</b></td><td align="right" width="10%"><B><%=grndTtlqty%></b></td><td align="right" width="10%"><B><%=grndTtlcts%></b></td><td align="right" width="20%" ><B><%=grndTtlvlu%></b></td>
      </tr>
      <input type="hidden" id="count" value="<%=loop%>" />
     </table></td></tr>
   </table><%}else{%>
   Sorry no result found
   <%}%>
   </td></tr>
   <tr>
    <td><jsp:include page="/footer.jsp" /> </td>
    </tr>
  </table>
  <%@include file="/calendar.jsp"%>
  </body>
</html>