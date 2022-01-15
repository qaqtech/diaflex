 
<%@ page errorPage="../error_page.jsp" %>
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import="java.util.ArrayList,java.util.Set,java.util.ArrayList,java.util.List, java.util.HashMap, java.sql.*, java.util.Enumeration, java.util.logging.Level"%>

<jsp:useBean id="util" class="ft.com.JspUtil" scope="page" />
<jsp:useBean id="info" class="ft.com.InfoMgr" scope="session" />
<jsp:useBean id="gtMgr" class="ft.com.GtMgr" scope="session" />

<%@taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>BID Page</title>
  <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/bse.js?v=<%=info.getJsVersion()%> " > </script>
  <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/box.js?v=<%=info.getJsVersion()%> " > </script>
<link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/bse.css?v=<%=info.getJsVersion()%>"/>
<link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/filter.css"/>
<script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/ajax.js?v=<%=info.getJsVersion()%> " > </script>
<link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/style.css?v=<%=info.getJsVersion()%>"/>
<link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/general.css?v=<%=info.getJsVersion()%>"/>
<script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/memoScript.js?v=<%=info.getJsVersion()%> " > </script>
<script type="text/javascript" src="<%=info.getReqUrl()%>/PopScripts/popup.js?v=<%=info.getJsVersion()%> " > </script>
<script src="<%=info.getReqUrl()%>/PopScripts/jquery-1.2.6.min.js" type="text/javascript"></script>
<script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/tableFilter/actb.js " > </script>
<script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/tableFilter/tablefilter.js " > </script>
         <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/priceChange.js?v=<%=info.getJsVersion()%> " > </script>
</head>
        <%String logId=String.valueOf(info.getLogId());
        String onfocus="cook('"+logId+"')";
        %>
 <body onfocus="<%=onfocus%>" onload="MM_preloadImages('images/faunalogo.png')" topmargin="0" leftmargin="0" rightmargin="0" bottommargin="0">
  <%
  info.setViewForm("BID");
  String pgDef = "SEARCH_RESULT";
HashMap allPageDtl= (info.getPageDetails() == null)?new HashMap():(HashMap)info.getPageDetails();
HashMap pageDtl=(HashMap)allPageDtl.get(pgDef);
String memolmt = util.nvl(info.getMemo_lmt());
    ArrayList pageList=new ArrayList();
    HashMap pageDtlMap=new HashMap();
    String fld_nme="",fld_ttl="",val_cond="",dflt_val="",fld_typ="",form_nme="",lov_qry="",flg1="";
   String lstNme = util.nvl((String)gtMgr.getValue("lstNme"));
  if(lstNme!=null){
   if(lstNme.indexOf("BID")==-1)
          lstNme = "BID"+lstNme;
  HashMap ttls= (HashMap)gtMgr.getValue(lstNme+"_TTL");


  %>
  
  <html:form action="marketing/StockSearch.do?method=saveOffer" method="post">
  <%   
      pageList= ((ArrayList)pageDtl.get("BIDRDT") == null)?new ArrayList():(ArrayList)pageDtl.get("BIDRDT");
     if(pageList!=null && pageList.size() >0){
     for(int j=0;j<pageList.size();j++){
      pageDtlMap=(HashMap)pageList.get(j);
         dflt_val=util.nvl((String)pageDtlMap.get("dflt_val"));
     %>
             <html:hidden property="value(biddirt)" styleId="biddirt" name="searchForm" value="<%=dflt_val%>"  />  
    <%}}%>
  <html:hidden property="value(memoTyp)" styleId="memoTyp" name="searchForm" value="Z"  /> 
  <table width="100%" border="0" align="center" cellpadding="0" cellspacing="0" class="mainbg">
<tr>
<td height="103" valign="top">
<jsp:include page="/header.jsp" />

</td>
</tr>

<tr>
<td height="5" valign="top" class="greyline"><img src="../images/grey_linebig.png" width="685" height="5" align="right" /></td>
</tr>
<tr>
<td>
<table>
<tr valign="bottom">
<td valign="top" class="hedPg"><img src="../images/line.jpg" border="0" /> <span class="pgHed">Offer</span> </td>
</tr></table></td></tr>
<tr><td valign="top" height="15">

<table>
<tr>
<td class="hedPg" valign="top"> <table class="prcPrntTbl" cellspacing="1" cellpadding="3">
<tr>
<th height="15">&nbsp;</th>
<th height="15"><div align="center"><strong>Pcs</strong></div></th>
<th height="15"><div align="center"><strong>Cts</strong></div></th>




<th height="15"><div align="center"><strong>Avg disc </strong></div></th>
<th height="15"><div align="center"><strong>Avg Price</strong></div></th>
<th height="15"><div align="center"><strong>Value</strong></div></th>


</tr>
<tr>
<td><div align="center"><strong>Total</strong></div></td>
<td height="20">
<div align="center">
<label><%=util.nvl((String)ttls.get("BQ"))%></label>

</div>
</td>
<td><div align="center">
<label><%=util.nvl((String)ttls.get("BW"))%></label>

</div></td>



<td><div align="center">
<label><%=util.nvl((String)ttls.get("BD"))%></label>

</div></td>
<td><div align="center">
<label><%=util.nvl((String)ttls.get("BA"))%></label>

</div></td>
<td><div align="center">
<label><%=util.nvl((String)ttls.get("BV"))%></label>

</div></td>
</tr>

</table></td>

</tr>
</table>
</td></tr>
<tr>
<td class="hedPg" valign="top">
<table><tr><td>
<html:submit property="value(offer)" value="Save Offer" styleClass="submit"/></td>
<!--<td><html:submit property="value(memooffer)" value="Memo Offer" styleClass="submit"/></td>-->
<%   
      pageList= ((ArrayList)pageDtl.get("BID") == null)?new ArrayList():(ArrayList)pageDtl.get("BID");
            if(pageList!=null && pageList.size() >0){
            for(int j=0;j<pageList.size();j++){
            pageDtlMap=(HashMap)pageList.get(j);
            fld_nme="value("+(String)pageDtlMap.get("fld_nme")+")";
            fld_ttl=(String)pageDtlMap.get("fld_ttl");
            fld_typ=(String)pageDtlMap.get("fld_typ");
            lov_qry=(String)pageDtlMap.get("lov_qry");
            dflt_val=(String)pageDtlMap.get("dflt_val");
            val_cond=(String)pageDtlMap.get("val_cond"); 
            if(fld_typ.equals("S")){
            %>
    <td><html:submit property="<%=fld_nme%>" value="<%=fld_ttl%>" onclick="<%=val_cond%>" styleClass="submit"/></td>
    <%}else if(fld_typ.equals("B")){%>
    <td><html:button property="<%=fld_nme%>" value="<%=fld_ttl%>" onclick="<%=val_cond%>" styleClass="submit"/></td>
    <%}else if(fld_typ.equals("HB")){%>
    <td align="right"><button type="button" onclick="<%=val_cond%>" class="submit" accesskey="<%=lov_qry%>" id="<%=fld_nme%>" name="<%=fld_nme%>" ><%=fld_ttl%></button></td>    
    <%}}}
    %>
<td><html:button property="value(memo)" value="Back To Search Result" onclick="goTo('StockSearch.do?method=srchBack','','')" styleClass="submit"/></td>
</tr></table>
</td></tr>
<%pageList=(ArrayList)pageDtl.get("BID_OPTION");
if(pageList!=null && pageList.size() >0){%>
<tr><td class="hedPg" valign="top"><table>
<tr><td>Update Price By </td><td> 
      <html:select property="value(val)" styleId="typ_ALL"  name="searchForm" >
      <%
      for(int j=0;j<pageList.size();j++){
      pageDtlMap=(HashMap)pageList.get(j);
      fld_ttl=(String)pageDtlMap.get("fld_ttl");
      val_cond=(String)pageDtlMap.get("val_cond");
      fld_typ=(String)pageDtlMap.get("fld_typ");
      dflt_val = (String)pageDtlMap.get("dflt_val");
      if((!dflt_val.equals("XRT"))){
      %>
    <html:option value="<%=dflt_val%>"><%=fld_ttl%></html:option>
<%}}%>
      </html:select></td> <td><html:text property="value(diff)" styleId="chng_ALL" size="10" name="searchForm"/> </td>
  <td><button type="button" class="submit" onClick="PriceChangeMemoGiveOffer('ALL')" >Verify </button>
</td>
  </tr>
  </table>
  </td>
  </tr>
  <%}%>
<%String msg = util.nvl((String)request.getAttribute("msg")); 
if(!msg.equals("")){
%>
<tr>
<td class="hedPg" valign="top"><%=msg%> </td></tr>
<%}%>
<%
ArrayList msgLst = (ArrayList)request.getAttribute("msgLst");
if(msgLst!=null && msgLst.size()>0){%>
<tr>
<td class="hedPg" valign="top"><table><tr>
<%
for(int i =0 ; i <msgLst.size();i++){%>
<td><%=msgLst.get(i)%></td>
<%}%></tr></table></td></tr>
<%
}

%>
<tr>
<td class="tdLayout" valign="top">
<jsp:include page="/marketing/stockView.jsp" />
</td></tr>
</table></html:form>
<%}%>
  </body>
</html>