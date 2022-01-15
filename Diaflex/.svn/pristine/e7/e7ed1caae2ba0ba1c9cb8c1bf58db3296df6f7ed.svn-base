<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page errorPage="../error_page.jsp" %>
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.ResultSet, java.util.ArrayList,java.util.Collections,java.util.ArrayList,java.util.HashMap,java.text.SimpleDateFormat, java.util.Calendar"%>
<jsp:useBean id="info" class="ft.com.InfoMgr" scope="session" />
<jsp:useBean id="util" class="ft.com.JspUtil" scope="page" />
<%@taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>
<html> 
   <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/bse.js?v=<%=info.getJsVersion()%> " > </script>
<link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/bse.css?v=<%=info.getJsVersion()%>"/>
<link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/style.css?v=<%=info.getJsVersion()%>"/>
  <title>Shape Size Report</title>
 
  </head>
        <%String logId=String.valueOf(info.getLogId());
        String onfocus="cook('"+logId+"')";
        %>
 <body onfocus="<%=onfocus%>" onload="MM_preloadImages('images/faunalogo.png')" topmargin="0" leftmargin="0" rightmargin="0" bottommargin="0">
   <%
   ArrayList shList= ((ArrayList)session.getAttribute("stkoenclosegtshapeList") == null)?new ArrayList():(ArrayList)session.getAttribute("stkoenclosegtshapeList");
   ArrayList szLst= ((ArrayList)session.getAttribute("stkoenclosegtsizeList") == null)?new ArrayList():(ArrayList)session.getAttribute("stkoenclosegtsizeList");
   HashMap dataDtl= ((HashMap)session.getAttribute("stkoencloseshapesizedataDtl") == null)?new HashMap():(HashMap)session.getAttribute("stkoencloseshapesizedataDtl");
   String dsp_stt= util.nvl((String)request.getAttribute("dsp_stt"));
   String level1val= util.nvl((String)request.getAttribute("level1val"));
  HashMap allPageDtl= (info.getPageDetails() == null)?new HashMap():(HashMap)info.getPageDetails();
  HashMap pageDtl=(HashMap)allPageDtl.get("GENERIC_REPORT");
  ArrayList pageList=new ArrayList();
  HashMap pageDtlMap=new HashMap();
  String fld_nme="",fld_ttl="",val_cond="",dflt_val="",fld_typ="",form_nme="",flg1="",lov_qry="";
  %>
  <table cellpadding="" cellspacing="" class="mainbg">
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
    Shape Size Report 
  </span> </td>
</tr></table>
  </td>
  </tr>
  <tr><td valign="top" class="hedPg"> 
  <b>Criteria :</b><bean:write property="value(criteria)" name="genericReportFormMG" /></td></tr>
    <tr><td valign="top" class="hedPg"><b>Show</b>
    <%pageList=(ArrayList)pageDtl.get("CHK");
    if(pageList!=null && pageList.size() >0){
    for(int j=0;j<pageList.size();j++){
    pageDtlMap=(HashMap)pageList.get(j);
    fld_nme=(String)pageDtlMap.get("fld_nme");
    fld_typ=(String)pageDtlMap.get("fld_typ");
    fld_ttl=(String)pageDtlMap.get("fld_ttl");
    val_cond=(String)pageDtlMap.get("val_cond");
    %>
       <input type="checkbox" name="<%=fld_nme%>" id="<%=fld_typ%>" value="" onclick="<%=val_cond%>"/><%=fld_ttl%>&nbsp;
    <%
    }
    }%>
  </td>
  </tr>
<tr><td class="hedPg">
<table class="grid1" id="data"  >
<tr>
<th>Shape/Size</th>
   <%for(int i=0;i<szLst.size();i++){%>
  <th colspan="5"><%=szLst.get(i)%></th>
  <%}%>
  <th colspan="5">Total</th>
</tr>
<tr>
<td></td>
   <%for(int i=0;i<szLst.size();i++){%>
  <td  align="center" style="padding:0px"><span id="QTY_<%=i%>" style="">QTY</span></td>
  <td  align="center" style="padding:0px"><span id="CTS_<%=i%>" style="display:none;">CTS</span></td>
  <td  align="center" style="padding:0px"><span id="AVG_<%=i%>" style="display:none;">AVG</span></td>
  <td  align="center" style="padding:0px"><span id="RAP_<%=i%>" style="display:none;">RAP</span></td>
  <td  align="center" style="padding:0px"><span id="VLU_<%=i%>" style="display:none;">VLU</span></td>
  <%}%>
  <td  align="center" style="padding:0px"><span id="TOTALQTY" style="">QTY</span></td>
  <td  align="center" style="padding:0px"><span id="TOTALCTS" style="display:none;">CTS</span></td>
  <td  align="center" style="padding:0px"><span id="TOTALAVG" style="display:none;">AVG</span></td>
  <td  align="center" style="padding:0px"><span id="TOTALRAP" style="display:none;">RAP</span></td>
  <td  align="center" style="padding:0px"><span id="TOTALVLU" style="display:none;">VLU</span></td>
</tr>

<%for(int j=0;j<shList.size();j++){
String sh=(String)shList.get(j);%>
<tr>
<td align="right" style="padding:0px"><a  title="Color Purity report" href="genericReport.do?method=loadstkoencloselevel3Data&level1val=<%=level1val%>&sh=<%=sh%>&sz=ALL" ><%=sh%></a></td>
  <%for(int k=0;k<szLst.size();k++){
  String sz=(String)szLst.get(k);%>
  <td align="right" style="padding:0px"><span id="QTY_<%=j%>_<%=k%>" style=""><a  title="Color Purity report" href="genericReport.do?method=loadstkoencloselevel3Data&dsp_stt=<%=dsp_stt%>&level1val=<%=level1val%>&sh=<%=sh%>&sz=<%=sz%>" ><%=util.nvl((String)dataDtl.get(sh+"_"+sz+"_QTY"))%></a></span></td>
  <td align="right" style="padding:0px"><span id="CTS_<%=j%>_<%=k%>" style="display:none;"><a title="Packet Details" target="_blank"  href="genericReport.do?method=pktDtlStockOpenClose&dsp_stt=<%=dsp_stt%>&level1val=<%=level1val%>&sh=<%=sh%>&sz=<%=sz%>" ><%=util.nvl((String)dataDtl.get(sh+"_"+sz+"_CTS"))%></a></span></td>
  <td align="right" style="padding:0px"><span id="AVG_<%=j%>_<%=k%>" style="display:none;"><%=util.nvl((String)dataDtl.get(sh+"_"+sz+"_AVG"))%></span></td>
  <td align="right" style="padding:0px"><span id="RAP_<%=j%>_<%=k%>" style="display:none;"><%=util.nvl((String)dataDtl.get(sh+"_"+sz+"_RAP"))%></span></td>
  <td align="right" style="padding:0px"><span id="VLU_<%=j%>_<%=k%>" style="display:none;"><%=util.nvl((String)dataDtl.get(sh+"_"+sz+"_VLU"))%></span></td>
  <%}%>
   <td align="right" style="padding:0px"><span id="TOTALQTY_<%=j%>" style=""><a  title="Color Purity report" href="genericReport.do?method=loadstkoencloselevel3Data&dsp_stt=<%=dsp_stt%>&level1val=<%=level1val%>&sh=<%=sh%>&sz=ALL" ><%=util.nvl((String)dataDtl.get(sh+"_QTY"))%></a></span></td>
  <td align="right" style="padding:0px"><span id="TOTALCTS_<%=j%>" style="display:none;"><a title="Packet Details" target="_blank"  href="genericReport.do?method=pktDtlStockOpenClose&dsp_stt=<%=dsp_stt%>&level1val=<%=level1val%>&sh=<%=sh%>&sz=ALL" ><%=util.nvl((String)dataDtl.get(sh+"_CTS"))%></a></span></td>
  <td align="right" style="padding:0px"><span id="TOTALAVG_<%=j%>" style="display:none;"><%=util.nvl((String)dataDtl.get(sh+"_AVG"))%></span></td>
  <td align="right" style="padding:0px"><span id="TOTALRAP_<%=j%>" style="display:none;"><%=util.nvl((String)dataDtl.get(sh+"_RAP"))%></span></td>
  <td align="right" style="padding:0px"><span id="TOTALVLU_<%=j%>" style="display:none;"><%=util.nvl((String)dataDtl.get(sh+"_VLU"))%></span></td>
</tr>
<%}%>
<tr>
<th>Total</th>
<%for(int i=0;i<szLst.size();i++){
String sz=(String)szLst.get(i); %>
  <td align="right" style="padding:0px"><span id="TTLQTY_<%=i%>" style=""><a  title="Color Purity report" href="genericReport.do?method=loadstkoencloselevel3Data&dsp_stt=<%=dsp_stt%>&level1val=<%=level1val%>&sh=ALL&sz=<%=sz%>" ><%=util.nvl((String)dataDtl.get(sz+"_QTY"))%></a></span></td>
  <td align="right" style="padding:0px"><span id="TTLCTS_<%=i%>" style="display:none;"><a title="Packet Details" target="_blank"  href="genericReport.do?method=pktDtlStockOpenClose&dsp_stt=<%=dsp_stt%>&level1val=<%=level1val%>&sh=ALL&sz=<%=sz%>" ><%=util.nvl((String)dataDtl.get(sz+"_CTS"))%></a></span></td>
  <td align="right" style="padding:0px"><span id="TTLAVG_<%=i%>" style="display:none;"><%=util.nvl((String)dataDtl.get(sz+"_AVG"))%></span></td>
  <td align="right" style="padding:0px"><span id="TTLRAP_<%=i%>" style="display:none;"><%=util.nvl((String)dataDtl.get(sz+"_RAP"))%></span></td>
  <td align="right" style="padding:0px"><span id="TTLVLU_<%=i%>" style="display:none;"><%=util.nvl((String)dataDtl.get(sz+"_VLU"))%></span></td>
<%}%>
  <td align="right" style="padding:0px"><span id="GTTLQTY" style=""><a  title="Color Purity report" href="genericReport.do?method=loadstkoencloselevel3Data&dsp_stt=<%=dsp_stt%>&level1val=<%=level1val%>&sh=ALL&sz=ALL" ><%=util.nvl((String)dataDtl.get("TTL_QTY"))%></a></span></td>
  <td align="right" style="padding:0px"><span id="GTTLCTS" style="display:none;"><a title="Packet Details" target="_blank"  href="genericReport.do?method=pktDtlStockOpenClose&dsp_stt=<%=dsp_stt%>&level1val=<%=level1val%>&sh=ALL&sz=ALL" ><%=util.nvl((String)dataDtl.get("TTL_CTS"))%></a></span></td>
  <td align="right" style="padding:0px"><span id="GTTLAVG" style="display:none;"><%=util.nvl((String)dataDtl.get("TTL_AVG"))%></span></td>
  <td align="right" style="padding:0px"><span id="GTTLRAP" style="display:none;"><%=util.nvl((String)dataDtl.get("TTL_RAP"))%></span></td>
  <td align="right" style="padding:0px"><span id="GTTLVLU" style="display:none;"><%=util.nvl((String)dataDtl.get("TTL_VLU"))%></span></td>
</tr>
</table>
</td></tr>
  <tr><td>
<jsp:include page="/footer.jsp" />
</td></tr>
  </table>  
  </body>
</html>