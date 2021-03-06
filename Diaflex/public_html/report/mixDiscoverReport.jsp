<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page errorPage="../error_page.jsp" %>
<%@ page contentType="text/html;charset=windows-1252"%>
<jsp:useBean id="info" class="ft.com.InfoMgr" scope="session" />
<jsp:useBean id="util" class="ft.com.JspUtil" scope="page" />
<%@ page import="java.util.ArrayList,java.util.HashMap,java.sql.ResultSet,ft.com.*, java.util.Date, java.util.ArrayList, ft.com.dao.*"%>
<%@taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
 
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title> Mix Discover Report</title>
    <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/bse.css?v=<%=info.getJsVersion()%>"/>
    <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/general.css?v=<%=info.getJsVersion()%>"/>
     <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/style.css?v=<%=info.getJsVersion()%>"/>
      <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/bse.js?v=<%=info.getJsVersion()%> " > </script>
       <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/assortScript.js?v=<%=info.getJsVersion()%> " > </script>
  </head>
        <%String logId=String.valueOf(info.getLogId());
        String onfocus="cook('"+logId+"')";
        %>
 <body onfocus="<%=onfocus%>" onload="MM_preloadImages('images/faunalogo.png')" topmargin="0" leftmargin="0" rightmargin="0" bottommargin="0">

<%
 HashMap allPageDtl= (info.getPageDetails() == null)?new HashMap():(HashMap)info.getPageDetails();
    HashMap pageDtl=(HashMap)allPageDtl.get("DISCOVER_MIX");
    ArrayList pageList=new ArrayList();
    HashMap pageDtlMap=new HashMap();
    String fld_nme="",lov_qry="",fld_ttl="",val_cond="",dflt_val="",fld_typ="",form_nme="";
    pageList= ((ArrayList)pageDtl.get("STATUS") == null)?new ArrayList():(ArrayList)pageDtl.get("STATUS");
         if(pageList!=null && pageList.size() >0){
           pageDtlMap=(HashMap)pageList.get(0);
          dflt_val=(String)pageDtlMap.get("dflt_val");
          }
%>

<table width="100%"   align="center" cellpadding="0" cellspacing="0" class="mainbg">
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
  <img src="../images/line.jpg" border="0" /></td><td valign="middle"> <span class="pgHed"> Mix Discover Report</span> </td>
</tr></table>
  </td>
  </tr>
  <tr><td valign="top" class="hedPg">
  <html:form action="report/mixdiscover.do?method=fecth" method="post" onsubmit="return validate_assort()">
<table class="grid1">
   <tr><th>Generic Search
   <%
  ArrayList rolenmLst=(ArrayList)info.getRoleLst();
  String usrFlg=util.nvl((String)info.getUsrFlg());
  if(usrFlg.equals("SYS") || rolenmLst.contains("ADMIN")){
  %>
   <img src="../images/edit.jpg" title="Edit Properties Module" onclick="newWindow('<%=info.getReqUrl()%>/dynamicProperty.do?method=load&mdl=DIS_MIX_SRCH&sname=DISGNCSrchMix&par=GENERIC')" border="0" width="15px" height="15px"/> 
  <%}%>
   </th><th></th></tr>
  <tr>
  <td><jsp:include page="/genericSrch.jsp" /> </td>
  <td colspan="2" valign="top">
 <html:radio property="value(srchRef)"  styleId="vnm" value="vnm" /> Packet Code/Rfid No.
 <html:radio property="value(srchRef)"  styleId="vnm" value="cert_no" /> Cert No.
 <table>
 <tr>
<td>Packet Id: </td>
<td><html:textarea property="value(vnm)" name="discoverReportForm" cols="30" rows="8" styleId="pid"/></td>
</tr>
 </table>
 </td>
  </tr>
   <%    pageList=(ArrayList)pageDtl.get("SALDATE");
         if(pageList!=null && pageList.size() >0){
         for(int k=0;k<pageList.size();k++){
         pageDtlMap=(HashMap)pageList.get(k);
         fld_nme="value("+(String)pageDtlMap.get("fld_nme")+")";
         fld_ttl=(String)pageDtlMap.get("fld_ttl");
         val_cond=(String)pageDtlMap.get("val_cond");
    %>
   <tr>
    <td><%=fld_ttl%>
    <html:text property="value(dtefr)" styleClass="txtStyle"  styleId="dtefr"  size="12"  /> <a href="#" style="margin-top:5px;"  onClick="setYears(1900, '<%=info.getCurrentYear()%>' );showCalender(this, 'dtefr');">
                <img src="<%=info.getReqUrl()%>/images/calender.png"></a>
     To
    <html:text property="value(dteto)" styleClass="txtStyle"  styleId="dteto"  size="12"  /> <a href="#"  onClick="setYears(1900, '<%=info.getCurrentYear()%>' );showCalender(this, 'dteto');">
                <img src="<%=info.getReqUrl()%>/images/calender.png"></a>
    </td>
    </tr>
      <%}}%>
   <tr> 
  <td valign="top" align="center">Status :<html:textarea property="value(stt)" cols="40" rows="2" value="<%=dflt_val%>" name="discoverReportForm" /> </td>
  <td>(Add Multiple status with comma separated)</td>
  </tr>
<tr>
<td colspan="2" align="center">
<html:submit property="value(mfg)" value="Search Packet" styleClass="submit" />
<!--<html:submit property="value(dwldExcel)" value="Create Excel" styleClass="submit" />-->
<%pageList=(ArrayList)pageDtl.get("SUBMIT");
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
    <html:submit property="<%=fld_nme%>" value="<%=fld_ttl%>" onclick="<%=val_cond%>" styleClass="submit"/>
    <%}else if(fld_typ.equals("B")){%>
    <html:button property="<%=fld_nme%>" value="<%=fld_ttl%>" onclick="<%=val_cond%>" styleClass="submit"/>
    <%}else if(fld_typ.equals("HB")){%>
    <button type="button" onclick="<%=val_cond%>" class="submit" accesskey="<%=lov_qry%>" id="<%=fld_nme%>" name="<%=fld_nme%>" ><%=fld_ttl%></button>   
    <%}}}
    %> 
</td>
</tr>
  </table>
    </html:form>
   </td></tr>
 
 <% 
  String  view =  util.nvl((String)request.getAttribute("view"));
  if(!view.equals("")){
  ArrayList vwPrpLst = (ArrayList)request.getAttribute("itemHdr");
 ArrayList  pktList =  (ArrayList)request.getAttribute("PktList");
 if(pktList!=null && pktList.size()>0){

HashMap prpDsc=new HashMap();
prpDsc.put("sr", "SR NO.");
prpDsc.put("vnm", "Pkt Code");
prpDsc.put("stt", "Status");
prpDsc.put("CERT NO", "CERT NO");
prpDsc.put("flg", "Flg");
prpDsc.put("cst_val", "CstVlu");
prpDsc.put("net_vlu", "NetVlu");
prpDsc.put("ofr_rte", "Ofr Rte");
prpDsc.put("ofr_dis", "Ofr_dis");

HashMap totals = (HashMap)request.getAttribute("totalMap");
HashMap mprp = info.getMprp();
int sr = 0;
%>
<tr><td valign="top" class="hedPg">
<table>
<tr><td>Total :&nbsp;&nbsp;</td><td><span id="ttlqty"> <%=totals.get("qty")%>&nbsp;&nbsp;</span></td><td>cts&nbsp;&nbsp;</td><td><span id="ttlcts"><%=totals.get("cts")%>&nbsp;&nbsp;</span></td>
<%
ArrayList rolenmLst=(ArrayList)info.getRoleLst();
  String usrFlg=util.nvl((String)info.getUsrFlg());
  if(usrFlg.equals("SYS") || rolenmLst.contains("ADMIN")){
  %>
  <td><img src="../images/edit.jpg" title="Edit Properties Module" onclick="newWindow('<%=info.getReqUrl()%>/dynamicProperty.do?method=load&mdl=DIS_MIX_VIEW&sname=DIS_MIX_VW&par=V')" border="0" width="15px" height="15px"/></td> 
  <%}%>
</tr>
</table>
</td></tr>
 <tr><td valign="top" class="hedPg">
  Create Excel &nbsp;&nbsp;&nbsp;
  <img src="../images/ico_file_excel.png" border="0" onclick="goTo('<%=info.getReqUrl()%>/report/mixdiscover.do?method=createXL','','')"/>
 
  </td></tr>
<tr><td valign="top" class="hedPg">
<table class="grid1"><tr>
    <%for(int j=0; j < vwPrpLst.size(); j++ ){
   String prp = (String)vwPrpLst.get(j);
   
   String hdr = util.nvl((String)mprp.get(prp));
   if(hdr.equals(""))
   hdr = util.nvl((String)prpDsc.get(prp));
   %>
  <th nowrap="nowrap"><%=hdr%></th>
  <%}%>
</tr>
<%for(int i=0;i<pktList.size();i++){
HashMap pktdtl =(HashMap)pktList.get(i); 
%>
<tr>
<%for(int j=0; j < vwPrpLst.size(); j++ ){
   String prp = (String)vwPrpLst.get(j);
%>
<td><%=pktdtl.get(prp)%></td>
<%}%>
</tr>
<%}%>
</table></td></tr>
<%}else{%>
<tr><td  valign="top" class="hedPg">Sorry no result found.</td></tr>
<%}
}%> 
  
  <tr>
    <td><jsp:include page="/footer.jsp" /> </td>
    </tr>
  </table>

  </body>
</html>