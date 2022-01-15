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
    <title>Sales Authorisation</title>
    <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/bse.css?v=<%=info.getJsVersion()%>"/>
    <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/style.css?v=<%=info.getJsVersion()%>"/>
    <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/memoScript.js?v=<%=info.getJsVersion()%> " > </script>
    <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/bse.js?v=<%=info.getJsVersion()%> " > </script>
    <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/assortScript.js?v=<%=info.getJsVersion()%> " > </script>
    <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/Validation.js " > </script>
    <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/priceChange.js?v=<%=info.getJsVersion()%> " > </script>
        <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/ajax.js?v=<%=info.getJsVersion()%> " > </script>
  </head>
<%
HashMap dbinfo = info.getDmbsInfoLst();
String cnt = (String)dbinfo.get("CNT");
  String repPath = (String)dbinfo.get("REP_PATH");
int accessidn=Integer.parseInt(util.nvl((String)request.getAttribute("accessidn"),"0"));
HashMap allPageDtl= (info.getPageDetails() == null)?new HashMap():(HashMap)info.getPageDetails();
HashMap pageDtl=(HashMap)allPageDtl.get("SALE_AUTHENTICATION");
ArrayList pageList=new ArrayList();
ArrayList prpDspBlocked = info.getPageblockList();
HashMap pageDtlMap=new HashMap();
HashMap prpList = info.getPrp();
String fld_nme="",fld_ttl="",val_cond="",dflt_val="",fld_typ="",form_nme="",lov_qry="";
 ArrayList setcharge = (request.getAttribute("setcharge") == null)?new ArrayList():(ArrayList)request.getAttribute("setcharge");
 String view = util.nvl((String)request.getAttribute("view"));
        String logId=String.valueOf(info.getLogId());
        String onfocus="cook('"+logId+"')";
        %>
 <body onfocus="<%=onfocus%>"  >
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
  <img src="../images/line.jpg" border="0" /></td><td valign="middle"> <span class="pgHed">Sales Authorisation</span> </td>
</tr></table></td></tr>
<tr><td valign="top" class="tdLayout">
  <table cellpadding="2" cellspacing="2" >
  <%
   String vnmNtFnd = (String)request.getAttribute("vnmNotFnd");
     if(vnmNtFnd!=null){%>
    <tr><td valign="top" class="tdLayout"><span class="redLabel"><%=vnmNtFnd%></span></td></tr>
   <%}%>
  <%
        if(request.getAttribute("error")!=null){%>
        <tr><td height="15"  valign="top" class="tdLayout"><span class="redLabel"> <%=request.getAttribute("error")%></span></td></tr>
       <% }
        %>
        <% if(request.getAttribute("RTMSG")!=null){%>
        <tr><td height="15"  valign="top" class="tdLayout"><span class="redLabel"> <%=request.getAttribute("RTMSG")%></span></td></tr>
       <% }
        %>
        <% if(request.getAttribute("CANMSG")!=null){%>
        <tr><td height="15"  valign="top" class="tdLayout"><span class="redLabel"> <%=request.getAttribute("CANMSG")%></span></td></tr>
       <% }%>
       <% if(request.getAttribute("CONFIRMMSG")!=null){%>
        <tr><td height="15"  valign="top" class="tdLayout"><span class="redLabel"> <%=request.getAttribute("CONFIRMMSG")%></span></td></tr>
       <% }%>
    
  </table>
  </td>
  </tr>
 <tr><td valign="top" class="tdLayout">
  <table>
  <tr><td valign="top"  class="tdLayout">
<%ArrayList pndsalepktList= (session.getAttribute("pndsalepktList") == null)?new ArrayList():(ArrayList)session.getAttribute("pndsalepktList");
int pndsalepktListsz=pndsalepktList.size();
%>
<%if(pndsalepktListsz>0){%>
<html:form action="/marketing/memoSale.do?method=saveauthenticate" method="POST" onsubmit="">
<table class="grid1">
<tr>
            <th>Sr No</th>
            <%pageList=(ArrayList)pageDtl.get("RADIOHDR");
            if(pageList!=null && pageList.size() >0){
            for(int j=0;j<pageList.size();j++){
            pageDtlMap=(HashMap)pageList.get(j);
            fld_nme="value("+(String)pageDtlMap.get("fld_nme")+")";
            fld_ttl=(String)pageDtlMap.get("fld_ttl");
            fld_typ=(String)pageDtlMap.get("fld_typ");
            dflt_val=(String)pageDtlMap.get("dflt_val");
            val_cond=(String)pageDtlMap.get("val_cond"); %>
            <th><html:radio property="<%=fld_nme%>" styleId="<%=fld_typ%>" name="memoSaleForm" onclick="<%=val_cond%>"  value="<%=dflt_val%>"/><%=fld_ttl%> </th>
            <%}}%>
            <th>SALE IDN</th>
            <th>SALE TYP</th>
            <th>BUYER</th>
            <th>SALE DTE</th>
            <th>QTY</th>
            <th>CTS</th>
            <th>RTE</th>
            <th>FNL_SAL</th>
            <!--<th>VLU</th>
            <th>AVGAMT</th>-->
            <th>DIS</th>
            <th>ADDL_DIS</th>
            <th>MB</th>
            <th>MBCOMM</th>
            <th>TRMS</th>
            <th>FNL_XRT</th>
            <th>MEMOIDN</th>
            <th>APPROVEDTE</th>
            <th>TYP</th>
            <th>CREATEDBY</th>
</tr>

<%for(int i=0;i<pndsalepktListsz;i++){
                HashMap pktPrpMap = new HashMap();
                pktPrpMap=(HashMap)pndsalepktList.get(i);
                String saleIdn = util.nvl((String)pktPrpMap.get("SALEIDN"));
                String rmkTxt =  "value(rmk_" + saleIdn + ")" ;
%>
<tr>
<td><%=util.nvl((String)pktPrpMap.get("SR"))%></td>
            <%pageList=(ArrayList)pageDtl.get("RADIOBODY");
            if(pageList!=null && pageList.size() >0){
            for(int j=0;j<pageList.size();j++){
            pageDtlMap=(HashMap)pageList.get(j);
            fld_nme="value("+(String)pageDtlMap.get("fld_nme")+""+saleIdn+")";
            fld_ttl=(String)pageDtlMap.get("fld_ttl");
            fld_typ=(String)pageDtlMap.get("fld_typ")+util.nvl((String)pktPrpMap.get("SR"));
            dflt_val=util.nvl((String)pageDtlMap.get("dflt_val")).trim();
            if(dflt_val.equals("PKT"))
            dflt_val=String.valueOf(saleIdn);
            val_cond=(String)pageDtlMap.get("val_cond");
            val_cond=val_cond.replaceAll("PKTIDN",String.valueOf(saleIdn));
            %>
            <td nowrap="nowrap"><html:radio property="<%=fld_nme%>" styleId="<%=fld_typ%>"  name="memoSaleForm"  onclick="<%=val_cond%>"  value="<%=dflt_val%>"/>
            <%if(dflt_val.equals("RT")){%>
            <html:text property="<%=rmkTxt%>" size="10"  />
            <%}%>
            </td>
            <%}}%>
<td nowrap="nowrap">
<A href="<%=info.getReqUrl()%>/marketing/memoSale.do?method=pktDtl&saleId=<%=util.nvl((String)pktPrpMap.get("SALEIDN"))%>" target="pkt" title="Click here To get Packet Details">
<%=util.nvl((String)pktPrpMap.get("SALEIDN"))%>
</a>
</td>
<td nowrap="nowrap"><%=util.nvl((String)pktPrpMap.get("SALETYP"))%></td>
<td nowrap="nowrap"><%=util.nvl((String)pktPrpMap.get("BYR"))%></td>
<td nowrap="nowrap"><%=util.nvl((String)pktPrpMap.get("SALEDTE"))%></td>
<td nowrap="nowrap"><%=util.nvl((String)pktPrpMap.get("QTY"))%></td>
<td nowrap="nowrap"><%=util.nvl((String)pktPrpMap.get("CTS"))%></td>
<td nowrap="nowrap"><%=util.nvl((String)pktPrpMap.get("RTE"))%></td>
<td nowrap="nowrap"><%=util.nvl((String)pktPrpMap.get("FNL_SAL"))%></td>
<!--<td nowrap="nowrap"><%=util.nvl((String)pktPrpMap.get("VLU"))%></td>
<td nowrap="nowrap"><%=util.nvl((String)pktPrpMap.get("AVGAMT"))%></td>-->
<td nowrap="nowrap"><%=util.nvl((String)pktPrpMap.get("DIS"))%></td>
<td nowrap="nowrap"><%=util.nvl((String)pktPrpMap.get("ADDL_DIS"))%></td>
<td nowrap="nowrap"><%=util.nvl((String)pktPrpMap.get("MB"))%></td>
<td nowrap="nowrap"><%=util.nvl((String)pktPrpMap.get("MBCOMM"))%></td>
<td nowrap="nowrap"><%=util.nvl((String)pktPrpMap.get("TRMS"))%></td>
<td nowrap="nowrap"><%=util.nvl((String)pktPrpMap.get("FNL_XRT"))%></td>
<td nowrap="nowrap"><%=util.nvl((String)pktPrpMap.get("MEMOIDN"))%></td>
<td nowrap="nowrap"><%=util.nvl((String)pktPrpMap.get("APPROVEDTE"))%></td>
<td nowrap="nowrap"><%=util.nvl((String)pktPrpMap.get("TYP"))%></td>
<td><%=util.nvl((String)pktPrpMap.get("CREATEDBY"))%></td>
</tr>
<%}%>
</table>
<table>
<tr> <td colspan="3">
<html:submit property="value(submit)" value="Save" styleClass="submit"/>
</td></tr>
</table>
</html:form>
<%}else{%>
Sorry No Result Found
<%}%>
</td>
</tr>
</table>
</td>
</tr>
     <tr>
    <td><jsp:include page="/footer.jsp" /> </td>
    </tr>
</table>
</body>