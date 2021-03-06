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
    <title>Price Reduction Report</title>
    <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/bse.css?v=<%=info.getJsVersion()%>"/>
    <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/general.css?v=<%=info.getJsVersion()%>"/>
     <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/style.css?v=<%=info.getJsVersion()%>"/>
      <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/bse.js?v=<%=info.getJsVersion()%> " > </script>
       <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/assortScript.js?v=<%=info.getJsVersion()%> " > </script>
       <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/rfid.js?v=<%=info.getJsVersion()%> " > </script>
       <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/dispose.js?v=<%=info.getJsVersion()%> " > </script>
  </head>
        <%String logId=String.valueOf(info.getLogId());
        String onfocus="cook('"+logId+"')";
        %>
 <body onfocus="<%=onfocus%>" onload="MM_preloadImages('images/faunalogo.png')" topmargin="0" leftmargin="0" rightmargin="0" bottommargin="0">

<%
 ArrayList seqList = (ArrayList)session.getAttribute("reductionseq");
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
  <img src="../images/line.jpg" border="0" /></td><td valign="middle"> <span class="pgHed">Price Reduction Report</span> </td>
</tr></table>
  </td>
  </tr>
  <tr><td valign="top" class="hedPg">
  <html:form action="report/orclRptAction.do?method=priceReductionfetch" method="post">
  <table class="grid1">
         <tr>
<td>Sequence :</td>
<td>
 <html:select property="value(seqid)" name="orclReportForm" styleId="seqid" >
    <html:option value="0">--------------select-------------</html:option>
         <%
      for(int i=0;i<seqList.size();i++){
      ArrayList seq=(ArrayList)seqList.get(i);
      %>
      <html:option value="<%=(String)seq.get(0)%>"> <%=(String)seq.get(1)%> </html:option>
      <%}%>
      </html:select>
               
</td></tr> 
<tr><td align="center" colspan="2">
<html:submit property="value(submit)" styleClass="submit" value="Submit" />
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
prpDsc.put("rap_val", "Rap Value");
prpDsc.put("total_val", "Total Value");
prpDsc.put("quot", "PriEsti");
prpDsc.put("sr", "SR NO.");
prpDsc.put("Vnm", "Pkt Code");
prpDsc.put("stt", "Status");
prpDsc.put("CERT NO", "CERT NO");
prpDsc.put("flg", "Flg");
prpDsc.put("cst_val", "CstVlu");
prpDsc.put("net_vlu", "NetVlu");
prpDsc.put("ofr_rte", "Ofr Rte");
prpDsc.put("ofr_dis", "Ofr_dis");
prpDsc.put("REVPCT", "Rev %");
//prpDsc.put("sl_dte", "SALE DTE");
HashMap mprp = info.getMprp();
int sr = 0;
%>
<tr><td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
  <%
  ArrayList rolenmLst=(ArrayList)info.getRoleLst();
  String usrFlg=util.nvl((String)info.getUsrFlg());
  if(usrFlg.equals("SYS") || rolenmLst.contains("ADMIN")){
  %>
  <img src="../images/edit.jpg" title="Edit Properties Module" onclick="newWindow('<%=info.getReqUrl()%>/dynamicProperty.do?method=load&mdl=REDUCT_VIEW&sname=REDUCT_VIEW&par=V')" border="0" width="15px" height="15px"/>
  <%}%>
   </td></tr>
<tr><td valign="top" class="hedPg">

<display:table name="sessionScope.orclReportForm.pktList" requestURI=""  export="true" class="displayTable" >
    <%for(int j=0; j < vwPrpLst.size(); j++ ){
   String prp = (String)vwPrpLst.get(j);
   
   String hdr = util.nvl((String)mprp.get(prp));
   if(hdr.equals(""))
   hdr = util.nvl((String)prpDsc.get(prp),prp);
   %>
  <display:column property="<%=prp%>"  title="<%=hdr%>"  />
  <%}%>
  <display:setProperty name="export.pdf" value="true" />
  <display:setProperty name="export.xls" value="true" />
  <display:setProperty name="export.excel.filename" value="PriceReduction.xls"/>
  <display:setProperty name="export.pdf.filename" value="PriceReduction.pdf"/>
  <display:setProperty name="export.csv.filename" value="PriceReduction.csv"/>
  <display:setProperty name="export.csv" value="true" />
  <display:setProperty name="export.xml" value="false" />
</display:table>

</td></tr>
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