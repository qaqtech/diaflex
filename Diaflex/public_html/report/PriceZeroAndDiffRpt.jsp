<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page errorPage="../error_page.jsp" %>
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import="java.util.ArrayList,java.util.ArrayList,java.util.HashMap, java.sql.*, java.util.Enumeration, java.util.logging.Level"%>
<jsp:useBean id="info" class="ft.com.InfoMgr" scope="session" />
<jsp:useBean id="util" class="ft.com.JspUtil" scope="page" />
<%@taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%  ArrayList prpDspBlocked = info.getPageblockList();%>
<html>
  <head>
 <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
  
  <title>Difference Reports</title>
<script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/bse.js?v=<%=info.getJsVersion()%> " > </script>
<link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/bse.css?v=<%=info.getJsVersion()%>"/>
<link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/filter.css"/>
<script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/ajax.js?v=<%=info.getJsVersion()%> " > </script>
<link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/style.css?v=<%=info.getJsVersion()%>"/>
<link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/general.css?v=<%=info.getJsVersion()%>"/>
<script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/assortScript.js?v=<%=info.getJsVersion()%> " > </script>
<script type="text/javascript" src="<%=info.getReqUrl()%>/PopScripts/popup.js?v=<%=info.getJsVersion()%> " > </script>
<script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/memoScript.js?v=<%=info.getJsVersion()%> " > </script>
<script src="<%=info.getReqUrl()%>/PopScripts/jquery-1.2.6.min.js" type="text/javascript"></script>
<script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/tableFilter/actb.js " > </script>
<script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/tableFilter/tablefilter.js " > </script>
<script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/box.js?v=<%=info.getJsVersion()%> " > </script>
</head>
<%String logId=String.valueOf(info.getLogId());
  String onfocus="cook('"+logId+"')";
  HashMap allPageDtl= (info.getPageDetails() == null)?new HashMap():(HashMap)info.getPageDetails();
  HashMap pageDtl=(HashMap)allPageDtl.get("PRICE_ZERO_DIFF");
  ArrayList pageList=new ArrayList();
  HashMap pageDtlMap=new HashMap();
  String fld_nme="",fld_ttl="",val_cond="",dflt_val="",fld_typ="",form_nme="",flg1="",lov_qry="";
  ArrayList prpList =(ArrayList)session.getAttribute("PRC_ZERO_VW");
 %>
 <body onfocus="<%=onfocus%>" onload="MM_preloadImages('images/faunalogo.png')" topmargin="0" leftmargin="0" rightmargin="0" bottommargin="0">
<img src="../images/loadbig.gif" vspace="15" id="load" style="display:none;" class="loadpktDiv" border="0" />
<div id="backgroundPopup"></div>
<div id="popupContactSave" align="center" >
<img src="../images/loading_flame.gif" height="150px" width="150px" border="0" />
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
  <img src="../images/line.jpg" border="0" /></td><td valign="middle"> <span class="pgHed">
 Price Zero/Difference Report
 </span> </td>
</tr></table>
  </td>
  </tr>
  <tr><td valign="top" class="tdLayout">
  <table><tr><td>
  <table><tr><td valign="top">
    <html:form action="report/orclRptAction.do?method=viewPriceDiff"  method="POST"  onsubmit="disablePopupSale();">
        <table class="grid1">
        <tr><th colspan="2">Price Zero/Difference </th></tr>
      <tr> <%
            pageList=(ArrayList)pageDtl.get("STATUS");
             if(pageList!=null && pageList.size() >0){
              for(int i=0;i<pageList.size();i++){
                 pageDtlMap=(HashMap)pageList.get(i);
                 fld_nme="value("+(String)pageDtlMap.get("fld_nme")+")";
                 fld_ttl=(String)pageDtlMap.get("fld_ttl");
                 val_cond=(String)pageDtlMap.get("val_cond");
                 dflt_val=(String)pageDtlMap.get("dflt_val");
             %>
      <td><%=fld_ttl%></td>
      <td>
      <html:textarea property="<%=fld_nme%>" value="<%=dflt_val%>" styleId="<%=fld_ttl%>" rows="2"  cols="35" name="orclReportForm"/>
      </td> 
      <%}}%>
      </tr>
      <tr>
      <td>Packet Id</td>
      <td>
      <html:textarea property="value(vnm)" styleId="pid" rows="2"  cols="35" name="orclReportForm"/>
      </td> 
      </tr>
      <tr>
      <td>Caret</td>
      <td>
      From <html:text property="value(ctsfr)" name="orclReportForm" styleId="ctsfr"/>&nbsp;
      To <html:text property="value(ctsto)" name="orclReportForm" styleId="ctsto"/>&nbsp;
      </td>
      </tr>
      <tr>
             <%
            pageList=(ArrayList)pageDtl.get("SELECT");
             if(pageList!=null && pageList.size() >0){%>
             <td>Packet Type :</td>
             <td>
             <html:select property="value(pkttype)" styleId="pkttype" name="orclReportForm" >
           
             <%
               for(int i=0;i<pageList.size();i++){
                 pageDtlMap=(HashMap)pageList.get(i);
                 fld_nme="value("+(String)pageDtlMap.get("fld_nme")+")";
                 fld_ttl=(String)pageDtlMap.get("fld_ttl");
                 val_cond=(String)pageDtlMap.get("val_cond");
                 dflt_val=(String)pageDtlMap.get("dflt_val");
                 %>
                <html:option value="<%=dflt_val%>"><%=fld_ttl%></html:option>
             <%}%>
            </html:select>
            </td>
            <%}%>
    
          </tr>
            <tr>    <td valign="top" class="hedPg">
  <div id="popupContactSale" >
<table align="center" cellpadding="0" cellspacing="0" >
<tr><td><div id="prp" ><table class="grid1">
<tr>
<td>Price Zero Of Property:</td>
<td>
<html:select name="orclReportForm" property="value(pricepro)" styleId="pricepro" >
      <%if(prpList!=null){
      for(int i=0;i<prpList.size();i++){
      %>
      <html:option value="<%=(String)prpList.get(i)%>"> <%=(String)prpList.get(i)%> </html:option>
      <%}}%>
      </html:select>
</td>
</tr>
<tr><td align="center" colspan="2">
<html:submit property="value(prczero)" value="Price Zero"  styleClass="submit"/>&nbsp;
<button type="button" onclick="disablePopupSale()" Class="submit" >Back</button> </td>
</tr>
</table></div>
</td></tr>
<tr>
<td>
<div id="diff">
<table class="grid1">
  <tr> <%
            pageList=(ArrayList)pageDtl.get("DIFF");
             if(pageList!=null && pageList.size() >0){
              for(int i=0;i<pageList.size();i++){
                 pageDtlMap=(HashMap)pageList.get(i);
                 fld_nme="value("+(String)pageDtlMap.get("fld_nme")+")";
                 fld_ttl=(String)pageDtlMap.get("fld_ttl");
                 val_cond=(String)pageDtlMap.get("val_cond");
                 dflt_val=(String)pageDtlMap.get("dflt_val");
             %>
      <td><%=fld_ttl%></td>
      <td>
      <html:text property="<%=fld_nme%>" value="<%=dflt_val%>" styleId="<%=fld_ttl%>"   name="orclReportForm"/>%
      </td> 
      <%}}%>
      </tr>
<tr><td> Price Diffrence </td>
<td>CMP 1
<html:select name="orclReportForm" property="value(frmpro)" styleId="frmpro" >
      <%if(prpList!=null){
      for(int i=0;i<prpList.size();i++){
      %>
      <html:option value="<%=(String)prpList.get(i)%>"> <%=(String)prpList.get(i)%> </html:option>
      <%}}%>
      </html:select>
CMP 2
<html:select name="orclReportForm" property="value(topro)" styleId="topro" >
      <%if(prpList!=null){
      for(int i=0;i<prpList.size();i++){
      %>
      <html:option value="<%=(String)prpList.get(i)%>"> <%=(String)prpList.get(i)%> </html:option>
      <%}}%>
      </html:select>
</td>
</tr>
<tr><td align="center" colspan="2">
<html:submit property="value(prcDiff)" value="Price Difference" onclick="return checkPricePro();" styleClass="submit"/>&nbsp;
<button type="button" onclick="disablePopupSale()" Class="submit" >Back</button> </td>
</tr>
</table> 
</div>
</td> 
</tr>
</table>
</div> 
 </td>
        </tr>  
        <tr>
        <td align="center" colspan="2">
        <html:button property="value(pricezero)" value="Price Zero" styleClass="submit" onclick="loadPricePRO('prp');"/>
        <html:submit property="value(rappricediff)" value="Rap Price Difference" styleClass="submit" onclick=""/>
        <html:button property="value(pricediff)" value="Price Difference " styleClass="submit"  onclick="loadPricePRO('diff');"/>
        </td>
        </tr>
        </table>
    </html:form>
    </td>
    </tr>
    </table>
    </td></tr>
  <%
                        
       ArrayList itemHdr =(ArrayList)session.getAttribute("PrcitemHdr");
       ArrayList pktList =(ArrayList)session.getAttribute("PrcpktList");
       String view=util.nvl((String)request.getAttribute("view"));
   if(!view.equals("")){ 
   if(pktList.size()>0){%>
 <tr><td valign="top">   Create Excel &nbsp;&nbsp;&nbsp;
  <img src="../images/ico_file_excel.png" border="0" onclick="goTo('<%=info.getReqUrl()%>/report/orclRptAction.do?method=createPRCXL','','')"/>
 </td></tr>
  <tr><td valign="top"> 
  <table class="grid1" >
  <thead>
  <tr>
  <%for(int i=0; i<itemHdr.size(); i++){%>
  <th nowrap="nowrap"><%=itemHdr.get(i)%></th>
  <%}%>
  </tr>
</thead>
<tbody>
<%for(int j=0; j<pktList.size(); j++){
HashMap pktDtl =(HashMap)pktList.get(j);
%>
  <tr>
  <%for(int k=0;k<itemHdr.size() ;k++){
  String hdr = (String)itemHdr.get(k);  
  %>
  <td nowrap="nowrap" align="right"><%=util.nvl((String)pktDtl.get(hdr))%></td>
  <%}%>
  </tr>
  <%}%>
</tbody>
</table></td>
</tr>
<%
}else{%>
<tr><td valign="top">
Sorry No Result Found</td></tr>
<%}}%>
</table></td></tr>
<tr>
    <td><jsp:include page="/footer.jsp" /> </td>
    </tr>   
  </table>  
  </body>
</html>