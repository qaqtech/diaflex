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
<title>Status Lab Report</title>
 
  </head>
        <%String logId=String.valueOf(info.getLogId());
        String onfocus="cook('"+logId+"')";
        %>
 <body onfocus="<%=onfocus%>" onload="MM_preloadImages('images/faunalogo.png')" topmargin="0" leftmargin="0" rightmargin="0" bottommargin="0">
   <%
    ArrayList labLst= new ArrayList();
    ArrayList sttList= new ArrayList();
    HashMap dataTbl= new HashMap();
    HashMap reportDtl= (HashMap)session.getAttribute("reportDtl");
    if(reportDtl!=null && reportDtl.size()>0){
    labLst=(ArrayList)reportDtl.get("STT_HDR");
    sttList=(ArrayList)reportDtl.get("STT_CONTENT");
    dataTbl=(HashMap)reportDtl.get("STT_DATA");
    }
    String group= (String)request.getAttribute("group");
    String labGroup= (String)request.getAttribute("lab");
    String locS= (String)request.getAttribute("loc");
    String mlot= util.nvl((String)request.getAttribute("mlot"));
    String valueDisplay= util.nvl((String)request.getAttribute("valueDisplay"));
    HashMap data=new HashMap();
    String Qty="";
  String Cts="";
  String Avg="";
  String Dis="";
  String Vlu="";
  String Fapri="";
  String Mfgpri="";
  String age="";
  String style="display:none;";
  HashMap allPageDtl= (info.getPageDetails() == null)?new HashMap():(HashMap)info.getPageDetails();
  HashMap pageDtl=(HashMap)allPageDtl.get("STOCK_SUMMARY");
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
<bean:write property="reportNme" name="groupWiseForm" />
 
  </span> </td>
</tr></table>
  </td>
  </tr>
  <tr><td valign="top" class="hedPg">Create Excel <img src="../images/ico_file_excel.png" title="Click here to Generate Excel" onclick="goToCreateXl('groupGrp.do?method=createGridXL&excelNm=StatusLab&Report=STT')" border="0"/>&nbsp; 
  <b>Criteria :</b><bean:write property="criteria" name="groupWiseForm" /></td></tr>
  <tr><td valign="top" class="hedPg"><b>Show</b>
  <%
pageList= ((ArrayList)pageDtl.get("CHK") == null)?new ArrayList():(ArrayList)pageDtl.get("CHK");  
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
    }
    if(!valueDisplay.equals("")){
    style="";%> 
  <input type="checkbox" name="VLU_dis" id="VLU_dis" value="" checked="checked"  onclick="displayReports('data','VLU_dis','VLU')"/>VLU&nbsp;
  <%}%>
  <!--<input type="checkbox" name="CTS_dis" id="CTS_dis" value="" onclick="displayReports('data','CTS_dis','CTS')"/>CTS&nbsp;
   <input type="checkbox" name="AVG_dis" id="AVG_dis" value="" onclick="displayReports('data','AVG_dis','AVG')"/>AVG&nbsp;
   <input type="checkbox" name="RAP_dis" id="RAP_dis" value="" onclick="displayReports('data','RAP_dis','RAP')"/>RAP&nbsp;-->
  </td>
  </tr>
  <tr><td class="hedPg"><table class="grid1" id="data"  >
  <tr>
  <th>Status/Lab</th>
  <%for(int i=0;i<labLst.size();i++){%>
  <th colspan="8"><%=labLst.get(i)%></th>
  <%}%>
  <th colspan="8">Total</th>
  </tr>
  <tr>
  <td></td>
   <%for(int i=0;i<labLst.size();i++){%>
  <td  align="center" style="padding:0px">QTY</td>
  <td  align="center" style="padding:0px"><span id="CTS_<%=i%>" style="display:none;">CTS</span></td>
  <td  align="center" style="padding:0px"><span id="AVG_<%=i%>" style="display:none;">AVG</span></td>
  <td  align="center" style="padding:0px"><span id="FAPRI_<%=i%>" style="display:none;">FAPRI</span></td>
  <td  align="center" style="padding:0px"><span id="MFGPRI_<%=i%>" style="display:none;">MFGPRI</span></td> 
  <td  align="center" style="padding:0px"><span id="RAP_<%=i%>" style="display:none;">RAP</span></td>
    <td  align="center" style="padding:0px"><span id="AGE_<%=i%>" style="display:none;">AGE</span></td>
  <td  align="center" style="padding:0px"><span id="VLU_<%=i%>" style="<%=style%>">VLU</span></td>
  <%}%>
  <td  align="center" style="padding:0px">QTY</td>
  <td  align="center" style="padding:0px"><span id="TOTALCTS" style="display:none;">CTS</span></td>
  <td  align="center" style="padding:0px"><span id="TOTALAVG" style="display:none;">AVG</span></td>
  <td  align="center" style="padding:0px"><span id="TOTALFAPRI" style="display:none;">FAPRI</span></td>
  <td  align="center" style="padding:0px"><span id="TOTALMFGPRI" style="display:none;">MFGPRI</span></td> 
  <td  align="center" style="padding:0px"><span id="TOTALRAP" style="display:none;">RAP</span></td>
    <td  align="center" style="padding:0px"><span id="TOTALAGE" style="display:none;">AGE</span></td>
  <td  align="center" style="padding:0px"><span id="TOTALVLU" style="<%=style%>">VLU</span></td>
  </tr>
  <%for(int j=0;j<sttList.size();j++){
  String stt=(String)sttList.get(j);
  String dsc=util.nvl2((String)dataTbl.get(stt),stt);
  %>
  <tr>
  <th><%=dsc%></th>
  <%for(int k=0;k<labLst.size();k++){
  String lab=(String)labLst.get(k);
  data=new HashMap();
  Qty="";
  Cts="";
  Avg="";
  Dis="";
  Vlu="";
  Fapri="";
  Mfgpri="";
  age="";
  data=(HashMap)dataTbl.get(stt+"_"+lab);
  if(data!=null && data.size()>0){
  Qty=util.nvl((String)data.get("QTY"));
  Cts=util.nvl((String)data.get("CTS"));
  Avg=util.nvl((String)data.get("AVG"));
  Dis=util.nvl((String)data.get("RAP"));
  Vlu=util.nvl((String)data.get("VLU"));
  Fapri=util.nvl((String)data.get("FAPRI"));
  Mfgpri=util.nvl((String)data.get("MFGPRI"));
  age=util.nvl((String)data.get("AGE"));
  }
  %>
  <td align="right" style="padding:0px"><span><a  title="Shape Size report" href="groupGrp.do?method=loadSHSZ&group=<%=group%>&lab=<%=lab%>&stt=<%=stt%>&loc=<%=locS%>&valueDisplay=<%=valueDisplay%>&mlot=<%=mlot%>" ><%=Qty%></a></span></td>
  <td align="right" style="padding:0px"><span id="CTS_<%=j%>_<%=k%>" style="display:none;"><a title="Packet Details" target="_blank"  href="groupGrp.do?method=pktDtl&group=<%=group%>&lab=<%=lab%>&stt=<%=stt%>&loc=<%=locS%>&valueDisplay=<%=valueDisplay%>&mlot=<%=mlot%>" ><%=Cts%></a></span></td>
  <td align="right" style="padding:0px"><span id="AVG_<%=j%>_<%=k%>" style="display:none;"><%=Avg%></span></td>
  <td align="right" style="padding:0px"><span id="FAPRI_<%=j%>_<%=k%>" style="display:none;"><%=Fapri%></span></td>
  <td align="right" style="padding:0px"><span id="MFGPRI_<%=j%>_<%=k%>" style="display:none;"><%=Mfgpri%></span></td>
  <td align="right" style="padding:0px"><span id="RAP_<%=j%>_<%=k%>" style="display:none;"><%=Dis%></span></td>
    <td align="right" style="padding:0px"><span id="AGE_<%=j%>_<%=k%>" style="display:none;"><%=age%></span></td>
  <td align="right" style="padding:0px"><span id="VLU_<%=j%>_<%=k%>" style="<%=style%>"><%=Vlu%></span></td>
  <%}%>
  <%
  Qty="";
  Cts="";
  Avg="";
  Dis="";
  Vlu="";
  Fapri="";
  Mfgpri="";
  age="";
  data=new HashMap();
  data=(HashMap)dataTbl.get(stt+"_TTL");
  if(data!=null && data.size()>0){
  Qty=util.nvl((String)data.get("QTY"));
  Cts=util.nvl((String)data.get("CTS"));
  Avg=util.nvl((String)data.get("AVG"));
  Dis=util.nvl((String)data.get("RAP"));
  Vlu=util.nvl((String)data.get("VLU"));
  Fapri=util.nvl((String)data.get("FAPRI"));
  Mfgpri=util.nvl((String)data.get("MFGPRI"));
  age=util.nvl((String)data.get("AGE"));
  }
  %>
  <td align="right" style="padding:0px"><span><a  title="Shape Size report" href="groupGrp.do?method=loadSHSZ&group=<%=group%>&lab=<%=labGroup%>&stt=<%=stt%>&loc=<%=locS%>&valueDisplay=<%=valueDisplay%>&mlot=<%=mlot%>" ><%=Qty%></a></span></td>
   <td align="right" style="padding:0px"><span id="TOTALCTS_<%=j%>" style="display:none;"><a title="Packet Details" target="_blank"  href="groupGrp.do?method=pktDtl&group=<%=group%>&lab=<%=labGroup%>&stt=<%=stt%>&loc=<%=locS%>&valueDisplay=<%=valueDisplay%>&mlot=<%=mlot%>" ><%=Cts%></a></span></td>
  <td align="right" style="padding:0px"><span id="TOTALAVG_<%=j%>" style="display:none;"><%=Avg%></span></td>
  <td align="right" style="padding:0px"><span id="TOTALFAPRI_<%=j%>" style="display:none;"><%=Fapri%></span></td>
  <td align="right" style="padding:0px"><span id="TOTALMFGPRI_<%=j%>" style="display:none;"><%=Mfgpri%></span></td>
  <td align="right" style="padding:0px"><span id="TOTALRAP_<%=j%>" style="display:none;"><%=Dis%></span></td>
    <td align="right" style="padding:0px"><span id="TOTALAGE_<%=j%>" style="display:none;"><%=age%></span></td>
  <td align="right" style="padding:0px"><span id="TOTALVLU_<%=j%>" style="<%=style%>"><%=Vlu%></span></td>
  </tr>
  <%}%>
  <tr>
  <th>Total</th>
   <%for(int i=0;i<labLst.size();i++){
  String lab=(String)labLst.get(i); 
  Qty="";
  Cts="";
  Avg="";
  Dis="";
  Vlu="";
  Fapri="";
  Mfgpri="";
  age="";
  data=new HashMap();
  data=(HashMap)dataTbl.get(lab+"_TTL");
  if(data!=null && data.size()>0){
  Qty=util.nvl((String)data.get("QTY"));
  Cts=util.nvl((String)data.get("CTS"));
  Avg=util.nvl((String)data.get("AVG"));
  Dis=util.nvl((String)data.get("RAP"));
  Vlu=util.nvl((String)data.get("VLU"));
  Fapri=util.nvl((String)data.get("FAPRI"));
  Mfgpri=util.nvl((String)data.get("MFGPRI"));
  age=util.nvl((String)data.get("AGE"));
  }
  %>
   <td align="right" style="padding:0px"><span><a  title="Shape Size report" href="groupGrp.do?method=loadSHSZ&group=<%=group%>&lab=<%=lab%>&stt=ALL&loc=<%=locS%>&valueDisplay=<%=valueDisplay%>&mlot=<%=mlot%>" ><%=Qty%></a></span></td>
   <td align="right" style="padding:0px"><span id="TTLCTS_<%=i%>" style="display:none;"><a title="Packet Details" target="_blank"  href="groupGrp.do?method=pktDtl&group=<%=group%>&lab=<%=lab%>&stt=ALL&loc=<%=locS%>&valueDisplay=<%=valueDisplay%>&mlot=<%=mlot%>" ><%=Cts%></a></span></td>
  <td align="right" style="padding:0px"><span id="TTLAVG_<%=i%>" style="display:none;"><%=Avg%></span></td>
  <td align="right" style="padding:0px"><span id="TTLFAPRI_<%=i%>" style="display:none;"><%=Fapri%></span></td>
  <td align="right" style="padding:0px"><span id="TTLMFGPRI_<%=i%>" style="display:none;"><%=Mfgpri%></span></td>
  <td align="right" style="padding:0px"><span id="TTLRAP_<%=i%>" style="display:none;"><%=Dis%></span></td>
    <td align="right" style="padding:0px"><span id="TTLAGE_<%=i%>" style="display:none;"><%=age%></span></td>
  <td align="right" style="padding:0px"><span id="TTLVLU_<%=i%>" style="<%=style%>"><%=Vlu%></span></td>
  <%}%>
  <%Qty="";
  Cts="";
  Avg="";
  Dis="";
  Fapri="";
  Mfgpri="";
  age="";
  data=new HashMap();
  data=(HashMap)dataTbl.get("TTL");
  if(data!=null && data.size()>0){
  Qty=util.nvl((String)data.get("QTY"));
  Cts=util.nvl((String)data.get("CTS"));
  Avg=util.nvl((String)data.get("AVG"));
  Dis=util.nvl((String)data.get("RAP"));
  Vlu=util.nvl((String)data.get("VLU"));
  Fapri=util.nvl((String)data.get("FAPRI"));
  Mfgpri=util.nvl((String)data.get("MFGPRI"));
  age=util.nvl((String)data.get("AGE"));
  }
  %>
  <td align="right" style="padding:0px"><span><a  title="Shape Size report" href="groupGrp.do?method=loadSHSZ&group=<%=group%>&lab=<%=labGroup%>&stt=ALL&loc=<%=locS%>&valueDisplay=<%=valueDisplay%>&mlot=<%=mlot%>" ><%=Qty%></a></span></td>
   <td align="right" style="padding:0px"><span id="GTTLCTS" style="display:none;"><a title="Packet Details" target="_blank"  href="groupGrp.do?method=pktDtl&group=<%=group%>&lab=<%=labGroup%>&stt=ALL&loc=<%=locS%>&valueDisplay=<%=valueDisplay%>&mlot=<%=mlot%>" ><%=Cts%></a></span></td>
  <td align="right" style="padding:0px"><span id="GTTLAVG" style="display:none;"><%=Avg%></span></td>
  <td align="right" style="padding:0px"><span id="GTTLFAPRI" style="display:none;"><%=Fapri%></span></td>
  <td align="right" style="padding:0px"><span id="GTTLMFGPRI" style="display:none;"><%=Mfgpri%></span></td>
  <td align="right" style="padding:0px"><span id="GTTLRAP" style="display:none;"><%=Dis%></span></td>
    <td align="right" style="padding:0px"><span id="GTTLAGE" style="display:none;"><%=age%></span></td>
  <td align="right" style="padding:0px"><span id="GTTLVLU" style="<%=style%>"><%=Vlu%></span></td>
  </tr>

</table></td></tr>
  <tr><td>
<jsp:include page="/footer.jsp" />
</td></tr>
  </table>  
  </body>
</html>