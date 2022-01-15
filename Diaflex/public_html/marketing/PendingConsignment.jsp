<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
 
<%@ page errorPage="../error_page.jsp" %>
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import="java.util.ArrayList,java.util.HashMap,java.sql.ResultSet,ft.com.*, java.util.Date, java.util.ArrayList, ft.com.dao.*"%>
<jsp:useBean id="util" class="ft.com.JspUtil" scope="page" />
<jsp:useBean id="info" class="ft.com.InfoMgr" scope="session" />

<%@taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>


<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>PendingConsignment</title>
    <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/bse.css?v=<%=info.getJsVersion()%>"/>
    <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/memoScript.js?v=<%=info.getJsVersion()%> " > </script>
   <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/bse.js?v=<%=info.getJsVersion()%> " > </script>
      <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/style.css?v=<%=info.getJsVersion()%>"/>
  </head>
        <%String logId=String.valueOf(info.getLogId());
        String onfocus="cook('"+logId+"')";
        %>
 <body onfocus="<%=onfocus%>" onload="MM_preloadImages('images/faunalogo.png')" topmargin="0" leftmargin="0" rightmargin="0" bottommargin="0">
<table width="100%"  border="0"  cellpadding="0" cellspacing="0" class="mainbg">
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
  <img src="../images/line.jpg" border="0" /></td><td valign="middle"> <span class="pgHed">Pending Consignment</span> </td>
</tr></table>
  </td>
  </tr>
  
  <%
    ArrayList penconsigndata= ((ArrayList)request.getAttribute("penconsigndata") == null)?new ArrayList():(ArrayList)request.getAttribute("penconsigndata");
    HashMap penconsigndataTtl= ((HashMap)request.getAttribute("penconsigndataTtl") == null)?new HashMap():(HashMap)request.getAttribute("penconsigndataTtl");
    int penconsigndatasz=penconsigndata.size();
    String pgTtl = "Pending Consignment as on "+ util.getDtTm();
     int ctr = 0 ;
      HashMap allPageDtl= (info.getPageDetails() == null)?new HashMap():(HashMap)info.getPageDetails();
     HashMap pageDtl=(HashMap)allPageDtl.get("PEND_CONSIGN");
     ArrayList pageList=new ArrayList();
      HashMap pageDtlMap=new HashMap();
      String fld_nme="",fld_ttl="",val_cond="",dflt_val="",fld_typ="",form_nme="";
       String memormk="N";
     pageList=(ArrayList)pageDtl.get("MEMO_RMK");
     if(pageList!=null && pageList.size() >0){
         for(int k=0;k<pageList.size();k++){
         memormk="Y";
     }}
     
    String rtnUrl="../box/boxReturn.do?method=load";
     pageList=(ArrayList)pageDtl.get("RTNURL");
     if(pageList!=null && pageList.size() >0){
     for(int k=0;k<pageList.size();k++){
       HashMap pgDtl = (HashMap)pageList.get(k);
       rtnUrl = (String)pgDtl.get("dflt_val");
     }}
  %>
  
    <tr><td class="tdLayout" valign="top">
        <html:hidden property="value(memormk)" styleId="rmk2"   value="<%=memormk%>"/>
        <input type="hidden" id="RTNURL" value="<%=rtnUrl%>" />

    <table class="memo" cellspacing="1"   cellpadding="2" >
    <%if(penconsigndatasz>0){%>
         <tr>
          <th nowrap="nowrap">Sr</th>
       
          <th nowrap="nowrap">Buyer</th>
           <th nowrap="nowrap">Sale Person</th>
          <th colspan="5" nowrap="nowrap">Issued</th>
       
        </tr>    
        <tr class="ttl">
          <td colspan="3">&nbsp;</td>
           
          <td>Cnt</td>
          <td>Qty</td>
          <td>Cts</td>
          <td>Value</td>
         <td>Detail</td>
         <!-- <td>Merge</td> -->
          
         
        </tr>
        <tr style="display:none;"><td><input type="hidden" name="typhidden" id="typhidden" value="">
        </td></tr>
    <%
        for(int i=0;i<penconsigndatasz;i++){
        HashMap dtl=(HashMap)penconsigndata.get(i);
        ++ctr ;
        String nmeIdn =util.nvl((String)dtl.get("nmeIdn"));
        String byr = util.nvl((String)dtl.get("byr"));
        String sale = util.nvl((String)dtl.get("sale"));
        String qty = util.nvl((String)dtl.get("qty"));
        String cts = util.nvl((String)dtl.get("cts"));
        String val = util.nvl((String)dtl.get("val"));
        String cnt = util.nvl((String)dtl.get("cnt"));
        String pkttyp = util.nvl((String)dtl.get("pkttyp"));
        String appLnk = ""
          , issLnk = "" ;
     %>     
      <tr>
        <td><%=ctr%></td>
      
        <td> <%=byr%></td>
          <td> <%=sale%></td>
          <td class="nm" style="text-align:center;"><%=cnt%></td>
        <td class="nm" style="text-align:center;"><%=qty%></td>
        <td class="nm" style="text-align:center;"><%=cts%></td>
        <td class="nm" style="text-align:center;"><%=val%></td>
        <td>
        <%if(!cts.equals("")){%>
        <span  class="img"><img src="../images/plus.png" id="IMG_CS_<%=ctr%>" title="click here for Detail" onclick="callMemoPkt('<%=nmeIdn%>','<%=ctr%>','CS','<%=pkttyp%>',this)" border="0"/></span>
        <%}else{%> 
        <span  class="img" style="display:none"><img src="../images/plus.png" id="IMG_CS_<%=ctr%>" title="click here for Detail" onclick="callMemoPkt('<%=nmeIdn%>','<%=ctr%>','CS','<%=pkttyp%>',this)" border="0"/></span>
        <%}%>
        </td>
        
        <!--
        <td>
         <%if(!qty.equals("")){%>
            <a href="memoReturn.do?method=merge&nmeIdn=<%=nmeIdn%>&typ=IS" title="click here for Merge Memo"><img src="../images/merge_icon.jpg" border="0"/></a>
            <%}%>
        </td>
        -->
       
     </tr>
     <tr>
      <td colspan="18"> 
      <div id="BYR_<%=ctr%>" align="center" style="display:none;" >
      </div>
      </td>
      </tr>
      <%  }
      String qty="",cts="",vlu="",cnt="";
      cnt = util.nvl((String)penconsigndataTtl.get("cnt"));
      qty = util.nvl((String)penconsigndataTtl.get("qty"));
      cts = util.nvl((String)penconsigndataTtl.get("cts"));
      vlu = util.nvl((String)penconsigndataTtl.get("vlu"));
    %>
    <tr>
    <td colspan="3" align="left"><b>Grand Total</b></td><td><b><%=cnt%></b></td>
    <td><b><%=qty%></b></td><td><b><%=cts%></b></td><td><b><%=vlu%></b></td><td></td>
    </tr>
    <%}else{%>
    <tr><td valign="top" class="hedPg"> Sorry no result found</td></tr>
    <%}%>
    </table>
   
     <input type="hidden" id="count" value="<%=ctr%>" />
   </td></tr>
   <tr>
    <td><jsp:include page="/footer.jsp" /> </td>
    </tr>
   </table>
 



  </body>
</html>