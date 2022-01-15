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
<link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/general.css?v=<%=info.getJsVersion()%>"/>
<script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/ajax.js?v=<%=info.getJsVersion()%> " > </script>
  <script type="text/javascript" src="<%=info.getReqUrl()%>/PopScripts/popup.js?v=<%=info.getJsVersion()%> " > </script>
    <script src="<%=info.getReqUrl()%>/PopScripts/jquery-1.2.6.min.js" type="text/javascript"></script>
   <script language="javaScript" type="text/javascript" src="<%=info.getReqUrl()%>/calendar/calendar.js"></script>
   <link href="<%=info.getReqUrl()%>/calendar/calendar.css" rel="stylesheet" type="text/css" />
  <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/style.css?v=<%=info.getJsVersion()%>"/>
  <title>Mix Stock Size Clearity Summary Report</title>
  </head>
        <%String logId=String.valueOf(info.getLogId());
        String onfocus="cook('"+logId+"')";
        String reqUrl = util.nvl((String)request.getAttribute("urlParam"));
        if(!reqUrl.equals("")){
        reqUrl=reqUrl.replace("method=loadmixSZCLR", "");
        }
        %>
 <body onfocus="<%=onfocus%>" onload="MM_preloadImages('images/faunalogo.png')" topmargin="0" leftmargin="0" rightmargin="0" bottommargin="0">
   <%
    ArrayList szLst= new ArrayList();
    ArrayList clrList= new ArrayList();
    ArrayList Lst= new ArrayList();
    HashMap dataTbl= new HashMap();
    HashMap reportDtl= (HashMap)session.getAttribute("reportDtl");
    if(reportDtl!=null && reportDtl.size()>0){
    szLst=(ArrayList)reportDtl.get("MIX_HDR");
    clrList=(ArrayList)reportDtl.get("MIX_CONTENT");
    dataTbl=(HashMap)reportDtl.get("MIX_DATA");
    Lst=(ArrayList)reportDtl.get("Lst");
    }
    HashMap data=new HashMap();
    String Qty="";
    String vlu="";
  String Cts="";
  String Avg="";
  String Dis="";
  String Fapri="";
  String Mfgpri="";
  HashMap allPageDtl= (info.getPageDetails() == null)?new HashMap():(HashMap)info.getPageDetails();
  HashMap pageDtl=(HashMap)allPageDtl.get("MIX_STOCK_SUMMARY");
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
Mix Stock Summary
  </span> </td>
</tr></table>
  </td>
  </tr>
  <tr><td valign="top" class="hedPg"><span id="excel">Create Excel <img src="../images/ico_file_excel.png" title="Click here to Generate Excel" onclick="goToMixCreateXl('mixStockSummary.do?method=createMixGridXL&excelNm=MixStockSummary&Report=MIX')" border="0"/></span> </td></tr>
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
  <!--<input type="checkbox" name="CTS_dis" id="CTS_dis" value="" onclick="displayReports('data','CTS_dis','CTS')"/>CTS&nbsp;
   <input type="checkbox" name="AVG_dis" id="AVG_dis" value="" onclick="displayReports('data','AVG_dis','AVG')"/>AVG&nbsp;
   <input type="checkbox" name="RAP_dis" id="RAP_dis" value="" onclick="displayReports('data','RAP_dis','RAP')"/>RAP&nbsp;-->
  </td>
  </tr>
  <tr><td><table  id="data" >
   <%for(int n=0;n<Lst.size();n++){
   String shLst=(String)Lst.get(n);
   %>
   <tr><td valign="top" class="hedPg"><B>Mix-<%=shLst%></B></%%></td></tr>
  <tr><td class="hedPg"><table class="dataTable">
  <tr>
  <th>Clearity/Mix Size</th>
  <%for(int i=0;i<szLst.size();i++){%>
  <th colspan="6"><%=szLst.get(i)%></th>
  <%}%>
  <th colspan="6">Total</th>
  </tr>
  <tr>
  <td></td>
   <%for(int i=0;i<szLst.size();i++){%>
 
  <td><span id="CTS_<%=n%>_<%=i%>" >CTS</span></td>
  <td><span id="AVG_<%=n%>_<%=i%>" >AVG</span></td>
  <td><span id="FAPRI_<%=n%>_<%=i%>" style="display:none;">FAPRI</span></td>
   <td><span id="MFGPRI_<%=n%>_<%=i%>" style="display:none;">MFGPRI</span></td>
   <td><span id="QTY_<%=n%>_<%=i%>" style="display:none;">QTY</span></td>
  <td><span id="VLU_<%=n%>_<%=i%>" style="display:none;">VLU</span></td>
  
  <%}%>
 
  <td><span id="TOTALCTS_<%=n%>" >CTS</span></td>
   <td><span id="TOTALAVG_<%=n%>">AVG</span></td>
     <td><span id="TOTALFAPRI_<%=n%>" style="display:none;">FAPRI</span></td>
   <td><span id="TOTALMFGPRI_<%=n%>" style="display:none;">MFGPRI</span></td>
   <td><span id="TOTALQTY_<%=n%>" style="display:none;">QTY</span></td>
   <td><span id="TOTALVLU_<%=n%>" style="display:none;">VLU</span></td>
 
  
  </tr>
  <%for(int j=0;j<clrList.size();j++){
  String clr=(String)clrList.get(j);
  %>
  <tr>
  <th><%=clr%></th>
  <%for(int k=0;k<szLst.size();k++){
  String sz=(String)szLst.get(k);
  data=new HashMap();
  Qty="";
  Cts="";
  Avg="";
  Dis="";
  Fapri="";
  Mfgpri="";
    vlu="";
  data=(HashMap)dataTbl.get(shLst+"_"+sz+"_"+clr);
  if(data!=null && data.size()>0){
  Qty=util.nvl((String)data.get("QTY"));
  Cts=util.nvl((String)data.get("CTS"));
  Avg=util.nvl((String)data.get("AVG"));
  Dis=util.nvl((String)data.get("RAP"));
  vlu=util.nvl((String)data.get("VLU"));
  Fapri=util.nvl((String)data.get("FAPRI"));
  Mfgpri=util.nvl((String)data.get("MFGPRI"));
  }
  %>
 
  <td align="right"><span id="CTS_<%=n%>_<%=j%>_<%=k%>">
  <a title="Packet Details" target="_blank"  href="mixStockSummary.do?method=pktDtl<%=reqUrl%>&level2=<%=sz%>&cts=<%=Cts%>&avg=<%=Avg%>" ><%=Cts%></a>
  </span></td>
  <td align="right"><span id="AVG_<%=n%>_<%=j%>_<%=k%>" ><%=Avg%></span></td>
  <td  align="right"><span id="FAPRI_<%=n%>_<%=j%>_<%=k%>" style="display:none;"><%=Fapri%></span></td>
  <td  align="right"><span id="MFGPRI_<%=n%>_<%=j%>_<%=k%>" style="display:none;"><%=Mfgpri%></span></td>
  <td  align="right"><span id="QTY_<%=n%>_<%=j%>_<%=k%>" style="display:none;"><%=Qty%></span></td>
  <td  align="right"><span id="VLU_<%=n%>_<%=j%>_<%=k%>" style="display:none;"><%=vlu%></span></td>
  
 
  <%}%>
  <%
  Qty="";
  Cts="";
  Avg="";
  Dis="";
  Fapri="";
    vlu="";
  Mfgpri="";
  data=new HashMap();
  data=(HashMap)dataTbl.get(shLst+"_"+clr+"_TTL");
  if(data!=null && data.size()>0){
  Qty=util.nvl((String)data.get("QTY"));
  Cts=util.nvl((String)data.get("CTS"));
  Avg=util.nvl((String)data.get("AVG"));
  Dis=util.nvl((String)data.get("RAP"));
    vlu=util.nvl((String)data.get("VLU"));
  Fapri=util.nvl((String)data.get("FAPRI"));
  Mfgpri=util.nvl((String)data.get("MFGPRI"));
  }
  %>
 
  <td align="right"><span id="TOTALCTS_<%=n%>_<%=j%>" >
   <a title="Packet Details" target="_blank"  href="mixStockSummary.do?method=pktDtl<%=reqUrl%>&cts=<%=Cts%>&avg=<%=Avg%>" >
  <%=Cts%></a></span>
  
  </td>
   <td align="right"><span id="TOTALAVG_<%=n%>_<%=j%>" ><%=Avg%></span></td>
    <td  align="right"><span id="TOTALFAPRI_<%=n%>_<%=j%>" style="display:none;"><%=Fapri%></span></td>
  <td  align="right"><span id="TOTALMFGPRI_<%=n%>_<%=j%>" style="display:none;"><%=Mfgpri%></span></td>
 
  <td align="right"><span id="TOTALQTY_<%=n%>_<%=j%>" style="display:none;">  <%=Qty%></span></td>
  <td align="right"><span id="TOTALVLU_<%=n%>_<%=j%>" style="display:none;">  <%=vlu%></span></td>
 
  </tr>
  <%}%>
  <tr>
  <th>Total</th>
   <%for(int i=0;i<szLst.size();i++){
  String sz=(String)szLst.get(i); 
  Qty="";
  Cts="";
  Avg="";
  Dis="";
  Fapri="";
  Mfgpri="";
    vlu="";
  data=new HashMap();
  data=(HashMap)dataTbl.get(shLst+"_"+sz+"_TTL");
  if(data!=null && data.size()>0){
  Qty=util.nvl((String)data.get("QTY"));
  Cts=util.nvl((String)data.get("CTS"));
  Avg=util.nvl((String)data.get("AVG"));
  Dis=util.nvl((String)data.get("RAP"));
    vlu=util.nvl((String)data.get("VLU"));
  Fapri=util.nvl((String)data.get("FAPRI"));
  Mfgpri=util.nvl((String)data.get("MFGPRI"));
  }
  %>
 
  <td align="right"><span id="TTLCTS_<%=n%>_<%=i%>" >
    <a title="Packet Details" target="_blank"  href="mixStockSummary.do?method=pktDtl<%=reqUrl%>&level2=<%=sz%>&cts=<%=Cts%>&avg=<%=Avg%>" >
  <%=Cts%></a></span></td>
  <td align="right"><span id="TTLAVG_<%=n%>_<%=i%>" ><%=Avg%></span></td>
   <td  align="right"><span id="TTLFAPRI_<%=n%>_<%=i%>" style="display:none;"><%=Fapri%></span></td>
  <td  align="right"><span id="TTLMFGPRI_<%=n%>_<%=i%>" style="display:none;"><%=Mfgpri%></span></td>
   <td  align="right"><span  id="TTLQTY_<%=n%>_<%=i%>" style="display:none;"> <%=Qty%></span></td>
   <td  align="right"><span  id="TTLVLU_<%=n%>_<%=i%>" style="display:none;"> <%=vlu%></span></td>
  
  
  <%}%>
  <%Qty="";
  Cts="";
  Avg="";
  Dis="";
  Fapri="";
  Mfgpri="";
  vlu="";
  data=new HashMap();
  data=(HashMap)dataTbl.get(shLst+"_"+"TTL");
  if(data!=null && data.size()>0){
  Qty=util.nvl((String)data.get("QTY"));
  Cts=util.nvl((String)data.get("CTS"));
  Avg=util.nvl((String)data.get("AVG"));
  Dis=util.nvl((String)data.get("RAP"));
    vlu=util.nvl((String)data.get("VLU"));
  Fapri=util.nvl((String)data.get("FAPRI"));
  Mfgpri=util.nvl((String)data.get("MFGPRI"));
  }
  %>
 
  <td align="right"><span id="GTTLCTS_<%=n%>" >
   <a title="Packet Details" target="_blank"  href="mixStockSummary.do?method=pktDtl<%=reqUrl%>&cts=<%=Cts%>&avg=<%=Avg%>" >
   <%=Cts%></a></span></td>
    <td align="right"><span id="GTTLAVG_<%=n%>" ><%=Avg%></span></td>
     <td  align="right"><span id="GTTLFAPRI_<%=n%>" style="display:none;"><%=Fapri%></span></td>
  <td  align="right"><span id="GTTLMFGPRI_<%=n%>" style="display:none;"><%=Mfgpri%></span></td>
  <td align="right"><span id="GTTLQTY_<%=n%>" style="display:none;"><%=Qty%></span></td>
  <td align="right"><span id="GTTLVLU_<%=n%>" style="display:none;"><%=vlu%></span></td>

  
  </tr>

</table></td></tr>

<%}%></table></td></tr>
  <tr><td>
<jsp:include page="/footer.jsp" />
</td></tr>
  </table>  
  </body>
</html>