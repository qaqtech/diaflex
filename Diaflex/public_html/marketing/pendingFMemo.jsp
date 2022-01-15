<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page errorPage="../error_page.jsp" %>
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import=" java.util.ArrayList,java.util.Collections,java.util.ArrayList,java.util.HashMap,java.text.SimpleDateFormat, java.util.Calendar"%>
<jsp:useBean id="util" class="ft.com.JspUtil" scope="page" />
<jsp:useBean id="info" class="ft.com.InfoMgr" scope="session" />
<%@taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>
<html>
   <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
<script type="text/javascript" src="<%=info.getReqUrl()%>/jquery/jquery-freezar.min.js"></script>
  <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/bse.css?v=<%=info.getJsVersion()%>"/>
  <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/style.css?v=<%=info.getJsVersion()%>"/>
 <script language="javaScript" type="text/javascript" src="<%=info.getReqUrl()%>/scripts/ajax.js?v=<%=info.getJsVersion()%>"></script>
 <script language="javaScript" type="text/javascript" src="<%=info.getReqUrl()%>/calendar/calendar.js"></script>
   <link href="<%=info.getReqUrl()%>/calendar/calendar.css" rel="stylesheet" type="text/css" />
<script language="javaScript" type="text/javascript" src="<%=info.getReqUrl()%>/scripts/bse.js?v=<%=info.getJsVersion()%>"></script>
 <script language="javaScript" type="text/javascript" src="<%=info.getReqUrl()%>/jquery/jquery.freezeheader.js?v=<%=info.getJsVersion()%>"></script>
       <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/memoScript.js?v=<%=info.getJsVersion()%> " > </script>
  <title>Pending For Memo</title>
  <script type="text/javascript">
   $(document).ready(function () {
	    $("#table2").freezeHeader({ 'height': '500px' });
    })
 </script>
  </head>
  
        <%
         System.out.println("line 31");
        String logId=String.valueOf(info.getLogId());
        String onfocus="cook('"+logId+"')";
        %>
 <body onfocus="<%=onfocus%>" onkeypress="return disableEnterKey(event);" >
<table width="100%"  border="0" align="center" cellpadding="0" cellspacing="0" class="mainbg">
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
<bean:write property="pendingNme" name="pendingForm" />
 
  </span> </td>
</tr></table>
  </td>
  </tr>
  <%
   System.out.println("line 54");
HashMap memoDtl=(HashMap)request.getAttribute("memoDtl");
HashMap memoTotalsDtl=(HashMap)request.getAttribute("memoTotalsDtl");
HashMap byrDtl=(HashMap)request.getAttribute("byrDtl");
ArrayList empList=(ArrayList)request.getAttribute("empList");
ArrayList memotyp=(ArrayList)request.getAttribute("memotyp");
ArrayList byrList=(ArrayList)request.getAttribute("byrList");
HashMap Display_typ=(HashMap)request.getAttribute("Display_typ");
HashMap byr_idn=(HashMap)request.getAttribute("byr_idn");
String pendf=util.nvl((String)request.getAttribute("page"));
String pgtyp=util.nvl(request.getParameter("typ"));
HashMap dbinfo = info.getDmbsInfoLst();
String cnt = util.nvl((String)dbinfo.get("CNT"));
 if(pgtyp.equals(""))
     pgtyp=util.nvl((String)session.getAttribute("memoTyp"));
 String emp="";
 String byr="";
 String copybyr="";
 ArrayList dtl=new ArrayList();
 int loop=0;
    System.out.println("line 74");

     HashMap allPageDtl= (info.getPageDetails() == null)?new HashMap():(HashMap)info.getPageDetails();
     HashMap pageDtl=(HashMap)allPageDtl.get("PEND_MEMO");
     ArrayList pageList=new ArrayList();
      HashMap pageDtlMap=new HashMap();
      String fld_nme="",fld_ttl="",val_cond="",dflt_val="",fld_typ="",form_nme="";
      String formAction = "marketing/penfmemo.do?method=load";
    System.out.println("line 82");

     String redirct_to_pricechange="N";
     pageList=(ArrayList)pageDtl.get("PRICE_CHANGE");
     if(pageList!=null && pageList.size() >0){
         for(int k=0;k<pageList.size();k++){
         redirct_to_pricechange="Y";
     }}
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
     String redirct_to_memoreport="N";
     pageList=(ArrayList)pageDtl.get("MEMO_REPORT");
     if(pageList!=null && pageList.size() >0){
         for(int k=0;k<pageList.size();k++){
         redirct_to_memoreport="Y";
     }}
     System.out.println("line 105");
%>
  <tr>
  <td valign="top" class="hedPg">
 
  <html:form action="<%=formAction%>" method="post">
  <html:hidden property="value(typ)" styleId="typ"   value="<%=pendf%>"/>
    <html:hidden property="value(redirct_to_pricechange)" styleId="redirct_to_pricechange"   value="<%=redirct_to_pricechange%>"/>
        <html:hidden property="value(redirct_to_memoreport)" styleId="redirct_to_memoreport"   value="<%=redirct_to_memoreport%>"/>

    <html:hidden property="value(memormk)" styleId="memormk"   value="<%=memormk%>"/>
    <html:hidden property="value(note_person)" styleId="note_person"   value="<%=note_person%>"/>
  <table><tr>
  <%   pageList=(ArrayList)pageDtl.get("SALER");
        if(pageList!=null && pageList.size() >0){
         for(int k=0;k<pageList.size();k++){
         pageDtlMap=(HashMap)pageList.get(k);
         fld_nme="value("+(String)pageDtlMap.get("fld_nme")+")";
         fld_ttl=(String)pageDtlMap.get("fld_ttl");
         val_cond=(String)pageDtlMap.get("val_cond");
    %>
      <td nowrap="nowrap"><%=fld_ttl%> :</td>
        <td nowrap="nowrap"><html:select property="<%=fld_nme%>"  styleId="<%=fld_nme%>" name="pendingForm" >
             <html:option value="" >-----ALL------</html:option>
              <% for(int i=0;i< empList.size();i++){
                     HashMap Dtl=(HashMap)empList.get(i);
                     String empid = util.nvl((String)Dtl.get("empid"));
                     String empnme = util.nvl((String)Dtl.get("emp"));
              %>  
              <html:option value="<%=empid%>" ><%=empnme%></html:option>
             <%}%>
          </html:select> </td>  
  
  <%}}    
  pageList=(ArrayList)pageDtl.get("BUYER");
        if(pageList!=null && pageList.size() >0){
         for(int k=0;k<pageList.size();k++){
         pageDtlMap=(HashMap)pageList.get(k);
         fld_nme="value("+(String)pageDtlMap.get("fld_nme")+")";
         fld_ttl=(String)pageDtlMap.get("fld_ttl");
    %>
      <td nowrap="nowrap"><%=fld_ttl%> :</td>
        <td nowrap="nowrap"><html:select property="<%=fld_nme%>"  styleId="<%=fld_nme%>" name="pendingForm"  >
             <html:option value="" >-----ALL------</html:option>
              <% for(int i=0;i< byrList.size();i++){
                     HashMap Dtl=(HashMap)byrList.get(i);
                     String byrid = util.nvl((String)Dtl.get("nmeidn"));
                     String byrnme = util.nvl((String)Dtl.get("byr"));
              %>  
              <html:option value="<%=byrid%>" ><%=byrnme%></html:option>
             <%}%>
          </html:select> </td>
  <%}}  pageList=(ArrayList)pageDtl.get("GROUP");
         if(pageList!=null && pageList.size() >0){
          for(int k=0;k<pageList.size();k++){
          pageDtlMap=(HashMap)pageList.get(k);
          fld_nme="value("+(String)pageDtlMap.get("fld_nme")+")";
          fld_ttl=(String)pageDtlMap.get("fld_ttl");
          ArrayList grpList = (ArrayList)session.getAttribute("grpcompanyList");
     %>
       <td nowrap="nowrap"><%=fld_ttl%> :</td>
       <td nowrap="nowrap">
       <html:select name="pendingForm" property="<%=fld_nme%>" styleId="grp">
       <html:option value="">-----ALL------</html:option>
       <%
       for(int i=0;i<grpList.size();i++){
       ArrayList grp=(ArrayList)grpList.get(i);
       %>
       <html:option value="<%=(String)grp.get(0)%>"> <%=(String)grp.get(1)%> </html:option>
       <%}%>
       </html:select>
       </td>


  <%}} 
  
   pageList=(ArrayList)pageDtl.get("PKTTY");
         if(pageList!=null && pageList.size() >0){
          for(int k=0;k<pageList.size();k++){
          pageDtlMap=(HashMap)pageList.get(k);
          fld_nme="value("+(String)pageDtlMap.get("fld_nme")+")";
          fld_ttl=(String)pageDtlMap.get("fld_ttl");
     %>
       <td nowrap="nowrap"><%=fld_ttl%> :</td>
       <td nowrap="nowrap">
       <html:select name="pendingForm" property="<%=fld_nme%>" styleId="pktTy">
       <html:option value="">-----ALL------</html:option>
       <html:option value="NR">Single</html:option>
       <html:option value="SMX">SingleMix</html:option>
       </html:select>
       </td>


  <%}} else{%>
  <input type="hidden" name="pktTy" id="pktTy" value="ALL" />
  <%}
     pageList=(ArrayList)pageDtl.get("SUBMIT");
     if(pageList!=null && pageList.size() >0){
         for(int k=0;k<pageList.size();k++){
         pageDtlMap=(HashMap)pageList.get(k);
         fld_nme="value("+(String)pageDtlMap.get("fld_nme")+")";
         fld_ttl=(String)pageDtlMap.get("fld_ttl");
       
  %>
 
 <td align="center" nowrap="nowrap">&nbsp;&nbsp; <html:submit property="<%=fld_nme%>" styleClass="submit" value="<%=fld_ttl%>" /> </td> 
 </tr>
  
  <%}}%>
  </table>
  </html:form></td></tr>
 <tr><td class="hedPg" >
   <%if(memoDtl!=null && memoDtl.size()> 0){%>
   
   <table class="grid1"  id="table2">
   <thead>
       <tr>
        <th><div style="width:150px;">Sale Person</div></th>
        <th><div style="width:250px;">Buyer</div></th>
        <%if(!pendf.equals("PNDF_MEMO")){%>
        <th>Memo By Term</th>
        <%}%>
         <% for(int k=0;k<memotyp.size();k++){
         String dsptyp=(String)Display_typ.get(memotyp.get(k));
         %>
        <th colspan="5"><%=util.nvl2(dsptyp,(String)memotyp.get(k))%></th>
        <%}%>
        <th colspan="4">Total</th>
      </tr>
  </thead>
  <tbody>	
      <tr>
      <td>&nbsp;</td>
      <td>&nbsp;</td>
      <%if(!pendf.equals("PNDF_MEMO")){%>
         <td>&nbsp;</td>
        <%}%>
       <% for(int k=0;k<memotyp.size();k++){%>
       <td align="right"><b>Cnt</b></td>
       <td align="right"><b>Qty</b></td>
       <td align="right"><b>Cts</b></td>
       <td align="right"><b>Vlu</b></td>
       <td><b>Merge</b></td>
      <%}%>
       <td align="right"><b>Cnt</b></td>
       <td align="right"><b>Qty</b></td>
       <td align="right"><b>Cts</b></td>
       <td align="right"><b>Vlu</b></td>
      </tr>
          <%for(int i=0;i<empList.size();i++){
           HashMap Dtl=(HashMap)empList.get(i);
             emp=(String)Dtl.get("emp");
          %>
             <%
              for(int j=0;j<=memoDtl.size();j++){
              byr=(String)byrDtl.get(emp+"_"+j);
              if(byr!=null && !byr.equals("") && !byr.equals(copybyr)){
                  %>
                  <tr><td><%=emp%></td>
                  <td>
                  <%=byr%>
                  </td>
                  <%if(!pendf.equals("PNDF_MEMO")){%>
                  <td align="center"><img src="../images/terms.png" title="Get Memo Terms Wise" onclick="callpendfMemosametermsPkt('<%=byr_idn.get(emp+"_"+byr)%>','<%=loop%>','S',this)" border="0" width="15px" height="15px"/> </td>
                  <%}%>
                  <% for(int k=0;k<memotyp.size();k++){
                        dtl=(ArrayList)memoDtl.get(emp+"_"+byr+"_"+memotyp.get(k));
                        if(dtl!=null && dtl.size()> 0){
                            for(int l=k;l<=k;l++){
                            String qty = util.nvl((String)dtl.get(1));
                            String nmeIdn = util.nvl((String)dtl.get(4));
                            String typ = util.nvl((String)dtl.get(5));
                            %>
                            <td align="right"><a onclick="callpendfMemoPkt('<%=byr_idn.get(emp+"_"+byr)%>','<%=loop%>','<%=memotyp.get(k)%>',this)" title="Click Here To See Details" style="text-decoration:underline"><%=dtl.get(0)%></a> </td>
                            <td align="right"><%=dtl.get(1)%></td>
                            <td align="right"><%=dtl.get(2)%></td>
                            <td align="right"><%=dtl.get(3)%></td>
                            <td align="center">
                            <%if(!qty.equals("")){%>
                            <a href="memoReturn.do?method=merge&nmeIdn=<%=nmeIdn%>&typ=<%=typ%>&MemoTyp=<%=pgtyp%>" title="click here for Merge Memo"><img src="../images/merge_icon.jpg" border="0"/></a>
                            <%}%>
                            </td>
                            <%
                            }
                         }else{%>
                            <td align="center">&nbsp;</td>
                            <td align="center">&nbsp;</td>
                            <td align="center">&nbsp;</td>
                            <td align="center">&nbsp;</td>
                             <td align="center">&nbsp;</td>
                            <%
                          }
                    }%>
                  <td align="right"><%=util.nvl((String)memoTotalsDtl.get(emp+"_"+byr+"_CNT"))%></td>
                  <td align="right"><%=util.nvl((String)memoTotalsDtl.get(emp+"_"+byr+"_QTY"))%></td>
                  <td align="right"><%=util.nvl((String)memoTotalsDtl.get(emp+"_"+byr+"_CTS"))%></td>  
                  <td align="right"><%=util.nvl((String)memoTotalsDtl.get(emp+"_"+byr+"_VLU"))%></td>
                 </tr>
                  <tr id="BYRTRDIV_<%=loop%>" style="display:none">
                      <td colspan="18"> 
                      <div id="BYR_<%=loop%>"  align="center">
                      </div>
                      </td>
                 </tr>
                 <tr id="BYRTERMTRDIV_<%=loop%>" style="display:none">
                      <td colspan="18"> 
                      <div id="BYRTERM_<%=loop%>"  align="center">
                      </div>
                      <%
                      loop++;%>
                      </td>
                 </tr>
                <%}
                copybyr=byr;
              }
           }%>
           <tr>
           <td><b>Total</b></td>
           <td>&nbsp;</td>
           <%if(!pendf.equals("PNDF_MEMO")){%>
             <td>&nbsp;</td>
            <%}%>
           <% for(int k=0;k<memotyp.size();k++){
           String typttl=util.nvl((String)memotyp.get(k));
           %>
                  <td align="right"><b><%=util.nvl((String)memoTotalsDtl.get(typttl+"_CNT"))%></b></td>
                  <td align="right"><b><%=util.nvl((String)memoTotalsDtl.get(typttl+"_QTY"))%></b></td>
                  <td align="right"><b><%=util.nvl((String)memoTotalsDtl.get(typttl+"_CTS"))%></b></td>  
                  <td align="right"><b><%=util.nvl((String)memoTotalsDtl.get(typttl+"_VLU"))%></b></td>
                  <td>&nbsp;</td>
             <%}%>
                  <td align="right"><b><%=util.nvl((String)memoTotalsDtl.get("CNT"))%></b></td>
                  <td align="right"><b><%=util.nvl((String)memoTotalsDtl.get("QTY"))%></b></td>
                  <td align="right"><b><%=util.nvl((String)memoTotalsDtl.get("CTS"))%></b></td>  
                  <td align="right"><b><%=util.nvl((String)memoTotalsDtl.get("VLU"))%></b></td>   
           </tr>
      <input type="hidden" id="count" value="<%=loop%>" />
      </tbody>
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