<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page errorPage="../error_page.jsp" %>
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.ResultSet, java.util.ArrayList,java.util.Collections,java.util.ArrayList,java.util.HashMap,java.text.SimpleDateFormat, java.util.Calendar,java.math.BigDecimal, java.util.Collections,java.math.RoundingMode;"%>
<jsp:useBean id="info" class="ft.com.InfoMgr" scope="session" />
<jsp:useBean id="util" class="ft.com.JspUtil" scope="page" />
<%@taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>
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
   <script language="javaScript" type="text/javascript" src="<%=info.getReqUrl()%>/calendar/calendar.js"></script>
   <link href="<%=info.getReqUrl()%>/calendar/calendar.css" rel="stylesheet" type="text/css" />
  <script src="<%=info.getReqUrl()%>/jqueryScript/jqueryScript.js?v=<%=info.getJsVersion()%>" type="text/javascript"></script>
 
      <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/style.css?v=<%=info.getJsVersion()%>"/>

  <title>Inward Report</title>
 
  </head>

        <%
String view=util.nvl((String)request.getAttribute("view"));
     HashMap dataDtl= (request.getAttribute("dataDtl") == null)?new HashMap():(HashMap)request.getAttribute("dataDtl");
     ArrayList rowlprpLst = (request.getAttribute("rowlprpLst") == null)?new ArrayList():(ArrayList)request.getAttribute("rowlprpLst");
        HashMap allPageDtl= (info.getPageDetails() == null)?new HashMap():(HashMap)info.getPageDetails();
        HashMap pageDtl=(HashMap)allPageDtl.get("RECPT_DT");
        ArrayList pageList=new ArrayList();
        HashMap pageDtlMap=new HashMap();
        String fld_nme="",fld_ttl="",val_cond="",lov_qry="",dflt_val="",fld_typ="",form_nme="",flg1="";
        String rowLprp="IFRS",columnLprp="PUR";
        pageList=(ArrayList)pageDtl.get("LEVEL1LPRP");
        if(pageList!=null && pageList.size() >0){
            for(int j=0;j<pageList.size();j++){
            pageDtlMap=(HashMap)pageList.get(j);
            rowLprp=(String)pageDtlMap.get("fld_nme");
            }
        }
        HashMap prp = info.getPrp();
        HashMap mprp = info.getMprp();
        String dta_typ=(String)mprp.get(rowLprp+"T");
        ArrayList prpValList = new ArrayList();
        ArrayList prpPrtList = new ArrayList();
        prpValList = (ArrayList)prp.get(rowLprp+"V");
        prpPrtList = (ArrayList)prp.get(rowLprp+"P");
        int rowlprpLstsz=rowlprpLst.size();
        int prpValListsz=prpValList.size();
        String logId=String.valueOf(info.getLogId());
        String onfocus="cook('"+logId+"')";
        %>
 <body onfocus="<%=onfocus%>" onload="MM_preloadImages('images/faunalogo.png')" topmargin="0" leftmargin="0" rightmargin="0" bottommargin="0">
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
  <img src="../images/line.jpg" border="0" /></td><td valign="middle"> <span class="pgHed">Inward Report</span> </td>
</tr></table>
  </td>
  </tr>

<tr><td valign="top" class="hedPg">
<html:form action="/report/orclRptAction.do?method=fetchRecptDt"  method="POST">
  <table  class="grid1">
   <tr>
   <th colspan="2" align="center">Search</th>
   </tr>
       <tr><td>Date : </td>  
       <td><html:text property="value(frmDte)" styleId="frmDte" name="orclReportForm" size="10" /><img src="<%=info.getReqUrl()%>/images/calender.png" onClick="setYears(2011, <%=info.getCurrentYear()%>);showCalender(this, 'frmDte');"> 
       To&nbsp; <html:text property="value(toDte)" styleId="toDte" name="orclReportForm"  size="10"/> <img src="<%=info.getReqUrl()%>/images/calender.png" onClick="setYears(2011, <%=info.getCurrentYear()%>);showCalender(this, 'toDte');"></td>
      </tr>
   <tr>
   <td colspan="2" align="center"><html:submit property="value(srch)" value="Fetch" styleClass="submit" />
   </td>
   </tr>
   </table>
</html:form>
</td>
</tr> 

<%if(view.equals("Y")){
if(rowlprpLstsz>0){
%>
  <tr>
  <td class="hedPg">
  <table class="grid1">
  <tr>
  <td>Property / Purchase</td><th colspan="2">Yes</th><th colspan="2">No</th><th colspan="2">Total</th>
  </tr>
  <tr>
  <td></td>
  <td align="center">Cts</td><td align="center">Mfg Cts</td>
  <td align="center">Cts</td><td align="center">Mfg Cts</td>
  <td align="center">Cts</td><td align="center">Mfg Cts</td>
  </tr>
  <%for(int i=0;i<prpValListsz;i++){
  String row=util.nvl((String)prpValList.get(i));
  if(rowlprpLst.contains(row)){
  %>
  <tr>
  <th><%=row%></th>
  <td align="right">
  <a title="Packet Details" target="_blank"  href="orclRptAction.do?method=pktDtlRecptDt&rowLprpVal=<%=row%>&columnLprpVal=YES" >
  <%=util.nvl((String)dataDtl.get(row+"_YES_CTS"),"0")%>
  </a>
  </td>
  <td align="right"><%=util.nvl((String)dataDtl.get(row+"_YES_MFGCTS"),"0")%></td>
  <td align="right">
  <a title="Packet Details" target="_blank"  href="orclRptAction.do?method=pktDtlRecptDt&rowLprpVal=<%=row%>&columnLprpVal=NO" >
  <%=util.nvl((String)dataDtl.get(row+"_NO_CTS"),"0")%>
  </a>
  </td>
  <td align="right"><%=util.nvl((String)dataDtl.get(row+"_NO_MFGCTS"),"0")%></td>
  <td align="right">
  <b>
  <a title="Packet Details" target="_blank"  href="orclRptAction.do?method=pktDtlRecptDt&rowLprpVal=<%=row%>&columnLprpVal=ALL" >
  <%=util.nvl((String)dataDtl.get(row+"_CTS"),"0")%>
  </a>
  </b>
  </td>
  <td align="right"><b><%=util.nvl((String)dataDtl.get(row+"_MFGCTS"),"0")%></b></td>
  </tr>
  <%}%>
  <%}%>
  <tr>
  <th>Total</th>
  <td align="right"><b>
  <a title="Packet Details" target="_blank"  href="orclRptAction.do?method=pktDtlRecptDt&rowLprpVal=ALL&columnLprpVal=YES" >
  <%=util.nvl((String)dataDtl.get("YES_CTS"),"0")%>
  </a></b></td>
  <td align="right"><b><%=util.nvl((String)dataDtl.get("YES_MFGCTS"),"0")%></b></td>
  <td align="right"><b>
  <a title="Packet Details" target="_blank"  href="orclRptAction.do?method=pktDtlRecptDt&rowLprpVal=ALL&columnLprpVal=NO" >
  <%=util.nvl((String)dataDtl.get("NO_CTS"),"0")%>
  </a></b></td>
  <td align="right"><b><%=util.nvl((String)dataDtl.get("NO_MFGCTS"),"0")%></b></td>
  <td align="right"><b>
  <a title="Packet Details" target="_blank"  href="orclRptAction.do?method=pktDtlRecptDt&rowLprpVal=ALL&columnLprpVal=ALL" >
  <%=util.nvl((String)dataDtl.get("CTS"),"0")%>
  </a></b></td>
  <td align="right"><b><%=util.nvl((String)dataDtl.get("MFGCTS"),"0")%></b></td>
  </tr>
  </table>
  </td>
  </tr>
<%}else{%>
<tr><td valign="top" class="hedPg">
Sorry No Result Found
</td></tr>
<%}}%>
  <tr><td>
<jsp:include page="/footer.jsp" />
</td></tr>
  </table>  
       <%@include file="../calendar.jsp"%>
  </body>
</html>