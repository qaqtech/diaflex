<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page errorPage="../error_page.jsp" %>
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.ResultSet, java.util.ArrayList,java.util.Collections,java.util.ArrayList,java.util.HashMap,java.text.SimpleDateFormat, java.util.Calendar"%>
<jsp:useBean id="info" class="ft.com.InfoMgr" scope="session" />
<jsp:useBean id="util" class="ft.com.JspUtil" scope="page" />
<%@taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>
<%@taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<html> 
   <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/bse.js?v=<%=info.getJsVersion()%> " > </script>
<link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/bse.css?v=<%=info.getJsVersion()%>"/>
<link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/style.css?v=<%=info.getJsVersion()%>"/>
       <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/memoScript.js?v=<%=info.getJsVersion()%> " > </script>
  <title>Group Lab Report</title>
 
  </head>
        <%String logId=String.valueOf(info.getLogId());
        String onfocus="cook('"+logId+"')";
        String menu = util.nvl((String)request.getParameter("menu"));
        
        %>
 <body onfocus="<%=onfocus%>" onload="MM_preloadImages('images/faunalogo.png')" topmargin="0" leftmargin="0" rightmargin="0" bottommargin="0">
   <%
    HashMap dbinfo = info.getDmbsInfoLst();
    HashMap groupDsc =((HashMap)util.getFromMem(info.getMem_ip(),info.getMem_port(),info.getCnt()+"_groupDsc") == null)?new HashMap():(HashMap)util.getFromMem(info.getMem_ip(),info.getMem_port(),info.getCnt()+"_groupDsc"); 
    ArrayList labLst= new ArrayList();
    ArrayList grpList= new ArrayList();
    HashMap dataTbl= new HashMap();
    HashMap reportDtl= (session.getAttribute("reportDtl") == null)?new HashMap():(HashMap)session.getAttribute("reportDtl");
    String mlot= util.nvl((String)request.getAttribute("mlot"));
    if(reportDtl!=null && reportDtl.size()>0){
    labLst=(ArrayList)reportDtl.get("GRP_HDR");
    grpList=(ArrayList)reportDtl.get("GRP_CONTENT");
    dataTbl=(HashMap)reportDtl.get("GRP_DATA");
    }
    String firstLvl=request.getParameter("lprp");
   if(firstLvl==null)
    firstLvl="LAB";
    HashMap data=new HashMap();
    String Qty="";
  String Cts="";
  String Avg="";
  String Dis="";
  String Vlu="";
  String Fapri="";
  String Mfgpri="";
  String cmpvlu="";
  String actnetpurvlu="";
  String age="";
  HashMap prp = info.getPrp();
  String loc="";
  ArrayList locList=new ArrayList();
  ArrayList locListGt=new ArrayList();
  if(dbinfo!=null && dbinfo.size()>0){
  loc=util.nvl((String)dbinfo.get("LOC"));
  locList = (ArrayList)prp.get(loc+"V");
  locListGt=((ArrayList)session.getAttribute("DSP_LOC") == null)?new ArrayList():(ArrayList)session.getAttribute("DSP_LOC");
  }
  HashMap allPageDtl= (info.getPageDetails() == null)?new HashMap():(HashMap)info.getPageDetails();
  HashMap pageDtl=(HashMap)allPageDtl.get("STOCK_SUMMARY");
  ArrayList pageList=new ArrayList();
    String locS= util.nvl((String)request.getAttribute("loc"));
    String valueDisplay= util.nvl((String)request.getAttribute("valueDisplay"));
    String style="display:none;";
    HashMap pageDtlMap=new HashMap();
    String fld_nme="",fld_ttl="",val_cond="",dflt_val="",fld_typ="",form_nme="",flg1="",lov_qry="";
  %>
  <table  width="100%"  border="0" align="center" cellpadding="0" cellspacing="0" class="mainbg">
  <% if(!menu.equals("NO")){ %>
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
  <table><tr><td valign="middle">
  <img src="../images/line.jpg" border="0" /></td><td valign="middle"> <span class="pgHed">
<bean:write property="reportNme" name="groupWiseForm" />
  </span> </td>
</tr></table>
  </td>
  </tr>
 
   <tr>
    <td valign="top" class="hedPg">
  <html:form action="report/group.do?method=loadlocation" method="post">
  <html:hidden property="value(valueDisplay)" name="groupWiseForm" />
  <input type="hidden" name="lprp" id="lprp" value="<%=firstLvl%>" />
  <table>
   <%  String isHK = request.getParameter("ISHK");
  if(isHK==null){%>
    <tr>
    <%pageList=(ArrayList)pageDtl.get("LOCATION");
    if(pageList!=null && pageList.size() >0){
    for(int j=0;j<pageList.size();j++){
    pageDtlMap=(HashMap)pageList.get(j);
    fld_nme=(String)pageDtlMap.get("fld_nme");
    fld_typ=(String)pageDtlMap.get("fld_typ");
    fld_ttl=(String)pageDtlMap.get("fld_ttl");
    val_cond=(String)pageDtlMap.get("val_cond");
    dflt_val=(String)pageDtlMap.get("dflt_val");
    %>
    <td>Location :</td>
    <td>
    <html:select property="value(LOC)" styleId="LOC"  >
    <html:option value="" >--Select--</html:option> 
    <%for(int i=0;i<locList.size();i++){
    String locprp=(String)locList.get(i);
    if(locListGt.contains(locprp) || locListGt.size()==0){%>
    <html:option value="<%=locprp%>" ><%=locprp%></html:option> 
    <%}}%>   
    </html:select>
    </td>
    <td>&nbsp;<html:submit property="value(submit)" styleClass="submit" value="Fetch" /> </td>
    <%}
    }%> 
    </tr>
    <%}%>
  </table>
  </html:form></td>
  </tr>
   <% } %>
  <%if(grpList!=null && grpList.size()>0){%>
  <tr><td valign="top" class="hedPg"><span id="excel">Create Excel <img src="../images/ico_file_excel.png" title="Click here to Generate Excel" onclick="goToCreateXl('group.do?method=createGridXL&excelNm=GroupLab&Report=GRP')" border="0"/></span> </td></tr>
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
    }
  if(!valueDisplay.equals("")){
  style="";%> 
  <input type="checkbox" name="VLU_dis" id="VLU_dis" value="" checked="checked" onclick="displayReports('data','VLU_dis','VLU')"/>VLU&nbsp;
  <%}%>
   <!--<input type="checkbox" name="AVG_dis" id="AVG_dis" value="" onclick="displayReports('data','AVG_dis','AVG')"/>AVG&nbsp;
   <input type="checkbox" name="RAP_dis" id="RAP_dis" value="" onclick="displayReports('data','RAP_dis','RAP')"/>RAP&nbsp;-->
  </td>
  </tr>
  <tr><td valign="top" class="hedPg"><table class="grid1" id="data">
  <tr>
  <th>Group/Lab</th>
  <%for(int i=0;i<labLst.size();i++){%>
  <th colspan="10"><%=labLst.get(i)%></th>
  <%}%>
  <th colspan="10">Total</th>
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
  <td  align="center" nowrap="nowrap" style="padding:0px"><span id="CMP_VLU_<%=i%>" style="display:none;">CMP VLU</span></td>
  <td  align="center" nowrap="nowrap" style="padding:0px"><span id="ACT_NET_PUR_VLU_<%=i%>" style="display:none;">ACT_NET_PUR_VLU</span></td>
  <%}%>
  <td  align="center" style="padding:0px">QTY</td>
  <td  align="center" style="padding:0px"><span id="TOTALCTS" style="display:none;">CTS</span></td>
  <td  align="center" style="padding:0px"><span id="TOTALAVG" style="display:none;">AVG</span></td>
  <td  align="center" style="padding:0px"><span id="TOTALFAPRI" style="display:none;">FAPRI</span></td>
  <td  align="center" style="padding:0px"><span id="TOTALMFGPRI" style="display:none;">MFGPRI</span></td>
  <td  align="center" style="padding:0px"><span id="TOTALRAP" style="display:none;">RAP</span></td>
    <td  align="center" style="padding:0px"><span id="TOTALAGE" style="display:none;">AGE</span></td>
  <td  align="center" style="padding:0px"><span id="TOTALVLU" style="<%=style%>">VLU</span></td>
      <td  align="center" nowrap="nowrap" style="padding:0px"><span id="TOTALCMP_VLU" style="display:none;">CMP VLU</span></td>
  <td  align="center" nowrap="nowrap" style="padding:0px"><span id="TOTALACT_NET_PUR_VLU" style="display:none;">ACT_NET_PUR_VLU</span></td>
  </tr>
  <%for(int j=0;j<grpList.size();j++){
  String grp=(String)grpList.get(j);
  String dsc=util.nvl2((String)dataTbl.get(grp),grp);
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
  age="";
  Vlu="";
  Fapri="";
  Mfgpri="";
  cmpvlu="";
  actnetpurvlu="";
  data=(HashMap)dataTbl.get(grp+"_"+lab);
  if(data!=null && data.size()>0){
  Qty=util.nvl((String)data.get("QTY"));
  Cts=util.nvl((String)data.get("CTS"));
  Avg=util.nvl((String)data.get("AVG"));
  Dis=util.nvl((String)data.get("RAP"));
  Vlu=util.nvl((String)data.get("VLU"));
  Fapri=util.nvl((String)data.get("FAPRI"));
  Mfgpri=util.nvl((String)data.get("MFGPRI"));
  cmpvlu=util.nvl((String)data.get("CMP_VLU"));
  actnetpurvlu=util.nvl((String)data.get("ACT_NET_PUR_VLU"));
  age=util.nvl((String)data.get("AGE"));
  }
  %>
  <td align="right" style="padding:0px"><span><a  title="Status Wise report" href="group.do?method=loadStatus&group=<%=grp%>&lab=<%=lab%>&loc=<%=locS%>&valueDisplay=<%=valueDisplay%>&mlot=<%=mlot%>" ><%=Qty%></a></span></td>
  <td align="right" style="padding:0px"><span id="CTS_<%=j%>_<%=k%>" style="display:none;"><a title="Packet Details" target="_blank"  href="group.do?method=pktDtl&group=<%=grp%>&filLprp=<%=firstLvl%>&lab=<%=lab%>&loc=<%=locS%>&valueDisplay=<%=valueDisplay%>&mlot=<%=mlot%>" ><%=Cts%></a></span></td>
  <td align="right" style="padding:0px"><span id="AVG_<%=j%>_<%=k%>" style="display:none;"><%=Avg%></span></td>
  <td align="right" style="padding:0px"><span id="FAPRI_<%=j%>_<%=k%>" style="display:none;"><%=Fapri%></span></td>
  <td align="right" style="padding:0px"><span id="MFGPRI_<%=j%>_<%=k%>" style="display:none;"><%=Mfgpri%></span></td>
  <td align="right" style="padding:0px"><span id="RAP_<%=j%>_<%=k%>" style="display:none;"><%=Dis%></span></td>
    <td align="right" style="padding:0px"><span id="AGE_<%=j%>_<%=k%>" style="display:none;"><%=age%></span></td>
  <td align="right" style="padding:0px"><span id="VLU_<%=j%>_<%=k%>" style="<%=style%>"><%=Vlu%></span></td>
    <td align="right" style="padding:0px"><span id="CMP_VLU_<%=j%>_<%=k%>" style="<%=style%>"><%=cmpvlu%></span></td>
      <td align="right" style="padding:0px"><span id="ACT_NET_PUR_VLU_<%=j%>_<%=k%>" style="display:none;"><%=actnetpurvlu%></span></td>
  <%}%>
  <%
  Qty="";
  Cts="";
  Avg="";
  Dis="";
  age="";
  Vlu="";
  Fapri="";
  Mfgpri="";
  cmpvlu="";
  actnetpurvlu="";
  data=new HashMap();
  data=(HashMap)dataTbl.get(grp+"_TTL");
  if(data!=null && data.size()>0){
  Qty=util.nvl((String)data.get("QTY"));
  Cts=util.nvl((String)data.get("CTS"));
  Avg=util.nvl((String)data.get("AVG"));
  Dis=util.nvl((String)data.get("RAP"));
  age=util.nvl((String)data.get("AGE"));
  Vlu=util.nvl((String)data.get("VLU"));
  Fapri=util.nvl((String)data.get("FAPRI"));
  Mfgpri=util.nvl((String)data.get("MFGPRI"));
    cmpvlu=util.nvl((String)data.get("CMP_VLU"));
  actnetpurvlu=util.nvl((String)data.get("ACT_NET_PUR_VLU"));
  }
  %>
  <td align="right" style="padding:0px"><span><a  title="Status Wise report" href="group.do?method=loadStatus&group=<%=grp%>&lab=ALL&loc=<%=locS%>&valueDisplay=<%=valueDisplay%>&mlot=<%=mlot%>">  <%=Qty%></a></span></td>
  <td align="right" style="padding:0px"><span id="TOTALCTS_<%=j%>" style="display:none;"><a title="Packet Details" target="_blank"  href="group.do?method=pktDtl&group=<%=grp%>&lab=ALL&loc=<%=locS%>&valueDisplay=<%=valueDisplay%>&mlot=<%=mlot%>" ><%=Cts%></a></span></td>
  <td align="right" style="padding:0px"><span id="TOTALAVG_<%=j%>" style="display:none;"><%=Avg%></span></td>
  <td align="right" style="padding:0px"><span id="TOTALFAPRI_<%=j%>" style="display:none;"><%=Fapri%></span></td>
  <td align="right" style="padding:0px"><span id="TOTALMFGPRI_<%=j%>" style="display:none;"><%=Mfgpri%></span></td>
  <td align="right" style="padding:0px"><span id="TOTALRAP_<%=j%>" style="display:none;"><%=Dis%></span></td>
    <td align="right" style="padding:0px"><span id="TOTALAGE_<%=j%>" style="display:none;"><%=age%></span></td>
  <td align="right" style="padding:0px"><span id="TOTALVLU_<%=j%>" style="<%=style%>"><%=Vlu%></span></td>
      <td align="right" style="padding:0px"><span id="TOTALCMP_VLU_<%=j%>" style="display:none;"><%=cmpvlu%></span></td>
  <td align="right" style="padding:0px"><span id="TOTALACT_NET_PUR_VLU_<%=j%>" style="display:none;"><%=actnetpurvlu%></span></td>
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
  age="";
  Vlu="";
  Fapri="";
  Mfgpri="";
    cmpvlu="";
  actnetpurvlu="";
  data=new HashMap();
  data=(HashMap)dataTbl.get(lab+"_TTL");
  if(data!=null && data.size()>0){
  Qty=util.nvl((String)data.get("QTY"));
  Cts=util.nvl((String)data.get("CTS"));
  Avg=util.nvl((String)data.get("AVG"));
  Dis=util.nvl((String)data.get("RAP"));
  age=util.nvl((String)data.get("AGE"));
  Vlu=util.nvl((String)data.get("VLU"));
  Fapri=util.nvl((String)data.get("FAPRI"));
  Mfgpri=util.nvl((String)data.get("MFGPRI"));
    cmpvlu=util.nvl((String)data.get("CMP_VLU"));
  actnetpurvlu=util.nvl((String)data.get("ACT_NET_PUR_VLU"));
  }
  %>
  <td align="right" style="padding:0px"><span><a  title="Status Wise report" href="group.do?method=loadStatus&group=ALL&lab=<%=lab%>&loc=<%=locS%>&valueDisplay=<%=valueDisplay%>&mlot=<%=mlot%>" > <%=Qty%></a></span></td>
  <td align="right" style="padding:0px"><span id="TTLCTS_<%=i%>" style="display:none;"><a title="Packet Details" target="_blank"  href="group.do?method=pktDtl&group=ALL&filLprp=<%=firstLvl%>&lab=<%=lab%>&loc=<%=locS%>&valueDisplay=<%=valueDisplay%>&mlot=<%=mlot%>" ><%=Cts%></a></span></td>
  <td align="right" style="padding:0px"><span id="TTLAVG_<%=i%>" style="display:none;"><%=Avg%></span></td>
  <td align="right" style="padding:0px"><span id="TTLFAPRI_<%=i%>" style="display:none;"><%=Fapri%></span></td>
  <td align="right" style="padding:0px"><span id="TTLMFGPRI_<%=i%>" style="display:none;"><%=Mfgpri%></span></td>
  <td align="right" style="padding:0px"><span id="TTLRAP_<%=i%>" style="display:none;"><%=Dis%></span></td>
    <td align="right" style="padding:0px"><span id="TTLAGE_<%=i%>" style="display:none;"><%=age%></span></td>
  <td align="right" style="padding:0px"><span id="TTLVLU_<%=i%>" style="<%=style%>"><%=Vlu%></span></td>
      <td align="right" style="padding:0px"><span id="TTLCMP_VLU_<%=i%>" style="display:none;"><%=cmpvlu%></span></td>
  <td align="right" style="padding:0px"><span id="TTLACT_NET_PUR_VLU_<%=i%>" style="display:none;"><%=actnetpurvlu%></span></td>
  <%}%>
  <%Qty="";
  Cts="";
  Avg="";
  Dis="";
  Vlu="";
  Fapri="";
  Mfgpri="";
  age="";
    cmpvlu="";
  actnetpurvlu="";
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
  cmpvlu=util.nvl((String)data.get("CMP_VLU"));
  actnetpurvlu=util.nvl((String)data.get("ACT_NET_PUR_VLU"));
  }
  %>
  <td align="right" style="padding:0px"><span><a  title="Status Wise report" href="group.do?method=loadStatus&group=ALL&lab=ALL&loc=<%=locS%>&valueDisplay=<%=valueDisplay%>&mlot=<%=mlot%>" > <%=Qty%></a></span></td>
  <td align="right" style="padding:0px"><span id="GTTLCTS" style="display:none;"><a title="Packet Details" target="_blank"  href="group.do?method=pktDtl&group=ALL&lab=ALL&filLprp=<%=firstLvl%>&loc=<%=locS%>&valueDisplay=<%=valueDisplay%>&mlot=<%=mlot%>" ><%=Cts%></a></span></td>
  <td align="right" style="padding:0px"><span id="GTTLAVG" style="display:none;"><%=Avg%></span></td>
  <td align="right" style="padding:0px"><span id="GTTLFAPRI" style="display:none;"><%=Fapri%></span></td>
  <td align="right" style="padding:0px"><span id="GTTLMFGPRI" style="display:none;"><%=Mfgpri%></span></td> 
  <td align="right" style="padding:0px"><span id="GTTLRAP" style="display:none;"><%=Dis%></span></td>
  <td align="right" style="padding:0px"><span id="GTTLAGE" style="display:none;"><%=age%></span></td>
  <td align="right" style="padding:0px"><span id="GTTLVLU" style="<%=style%>"><%=Vlu%></span></td>
  <td align="right" style="padding:0px"><span id="GTTLCMP_VLU" style="display:none;"><%=cmpvlu%></span></td>
  <td align="right" style="padding:0px"><span id="GTTLACT_NET_PUR_VLU" style="display:none;"><%=actnetpurvlu%></span></td>
  </tr>

</table></td></tr>
<%if(locS.equals("SHOW")){
HashMap memoDtl=(HashMap)request.getAttribute("memoDtl");
HashMap memoTotalsDtl=(HashMap)request.getAttribute("memoTotalsDtl");
HashMap byrDtl=(HashMap)request.getAttribute("byrDtl");
ArrayList empList=(ArrayList)request.getAttribute("empList");
ArrayList memotyp=(ArrayList)request.getAttribute("memotyp");
ArrayList byrList=(ArrayList)request.getAttribute("byrList");
HashMap byr_idn=(HashMap)request.getAttribute("byr_idn");
HashMap Display_typ=new HashMap();
Display_typ.put("S", "Show Issue");
Display_typ.put("SAP", "Show Approved");
 String emp="";
 String byr="";
 String copybyr="";
 ArrayList dtl=new ArrayList();
 int loop=0;
     String redirct_to_pricechange="N";
     pageList=(ArrayList)pageDtl.get("PRICE_CHANGE");
     if(pageList!=null && pageList.size() >0){
         for(int k=0;k<pageList.size();k++){
         redirct_to_pricechange="Y";
     }}
     String memormk="N";
     pageList=(ArrayList)pageDtl.get("MEMO_RMK");
     if(pageList!=null && pageList.size() >0){
         for(int k=0;k<pageList.size();k++){
         memormk="Y";
     }}
     String note_person="N";
     pageList=(ArrayList)pageDtl.get("NOTE_PERSON");
     if(pageList!=null && pageList.size() >0){
         for(int k=0;k<pageList.size();k++){
         note_person="Y";
     }}
%>
   <%if(memoDtl!=null && memoDtl.size()> 0){%>
<tr>
<td>
<tr><td class="hedPg" ><b>Show Memo Details</b></td></tr>
 <tr><td class="hedPg" >
   
   <table class="grid1"  id="table2">
   <input type="hidden" name="pktTy" id="pktTy" value="ALL" />
       <html:hidden property="value(redirct_to_pricechange)" styleId="redirct_to_pricechange"   value="<%=redirct_to_pricechange%>"/>
    <html:hidden property="value(memormk)" styleId="memormk"   value="<%=memormk%>"/>
    <html:hidden property="value(note_person)" styleId="note_person"   value="<%=note_person%>"/>
   <thead>
       <tr>
        <th><div style="width:150px;">Sale Person</div></th>
        <th><div style="width:250px;">Buyer</div></th>
         <% for(int k=0;k<memotyp.size();k++){
         String dsptyp=(String)Display_typ.get(memotyp.get(k));
         %>
        <th colspan="4"><%=util.nvl2(dsptyp,(String)memotyp.get(k))%></th>
        <%}%>
        <th colspan="4">Total</th>
      </tr>
  </thead>
  <tbody>	
      <tr>
      <td></td>
      <td></td>
       <% for(int k=0;k<memotyp.size();k++){%>
       <td align="right"><b>Cnt</b></td>
       <td align="right"><b>Qty</b></td>
       <td align="right"><b>Cts</b></td>
       <td align="right"><b>Vlu</b></td>
      <%}%>
       <td align="right"><b>Cnt</b></td>
       <td align="right"><b>Qty</b></td>
       <td align="right"><b>Cts</b></td>
       <td align="right"><b>Vlu</b></td>
      </tr>
          <%for(int i=0;i<empList.size();i++){
           HashMap Dtl=(HashMap)empList.get(i);
             emp=(String)Dtl.get("emp");
          %>
             <%
              for(int j=0;j<memoDtl.size();j++){
              byr=(String)byrDtl.get(emp+"_"+j);
              if(byr!=null && !byr.equals("") && !byr.equals(copybyr)){
                  %>
                  <tr><td><%=emp%></td>
                  <td>
                  <%=byr%>
                  </td>
                  <% for(int k=0;k<memotyp.size();k++){
                        dtl=(ArrayList)memoDtl.get(emp+"_"+byr+"_"+memotyp.get(k));
                        if(dtl!=null && dtl.size()> 0){
                            for(int l=k;l<=k;l++){
                            String qty = util.nvl((String)dtl.get(1));
                            String nmeIdn = util.nvl((String)dtl.get(4));
                            String typ = util.nvl((String)dtl.get(5));
                            %>
                            <td align="right"><a onclick="callpendfMemoPkt('<%=byr_idn.get(emp+"_"+byr)%>','<%=loop%>','<%=memotyp.get(k)%>',this)" title="Click Here To See Details" style="text-decoration:underline"><%=dtl.get(0)%></a> </td>
                            <td align="right"><%=dtl.get(1)%></td>
                            <td align="right"><%=dtl.get(2)%></td>
                            <td align="right"><%=dtl.get(3)%></td>
                            <%
                            }
                         }else{%>
                            <td align="center"></td>
                            <td align="center"></td>
                            <td align="center"></td>
                            <td align="center"></td>
                            <%
                          }
                    }%>
                  <td align="right"><%=util.nvl((String)memoTotalsDtl.get(emp+"_"+byr+"_CNT"))%></td>
                  <td align="right"><%=util.nvl((String)memoTotalsDtl.get(emp+"_"+byr+"_QTY"))%></td>
                  <td align="right"><%=util.nvl((String)memoTotalsDtl.get(emp+"_"+byr+"_CTS"))%></td>  
                  <td align="right"><%=util.nvl((String)memoTotalsDtl.get(emp+"_"+byr+"_VLU"))%></td>
                 </tr>
                  <tr id="BYRTRDIV_<%=loop%>" style="display:none">
                      <td colspan="18"> 
                      <div id="BYR_<%=loop%>"  align="center">
                      </div>
                      </td>
                 </tr>
                 <tr id="BYRTERMTRDIV_<%=loop%>" style="display:none">
                      <td colspan="18"> 
                      <div id="BYRTERM_<%=loop%>"  align="center">
                      </div>
                      <%
                      loop++;%>
                      </td>
                 </tr>
                <%}
                copybyr=byr;
              }
           }%>
           <tr>
           <td><b>Total</b></td>
           <td></td>
           <% for(int k=0;k<memotyp.size();k++){
           String typttl=util.nvl((String)memotyp.get(k));
           %>
                  <td align="right"><b><%=util.nvl((String)memoTotalsDtl.get(typttl+"_CNT"))%></b></td>
                  <td align="right"><b><%=util.nvl((String)memoTotalsDtl.get(typttl+"_QTY"))%></b></td>
                  <td align="right"><b><%=util.nvl((String)memoTotalsDtl.get(typttl+"_CTS"))%></b></td>  
                  <td align="right"><b><%=util.nvl((String)memoTotalsDtl.get(typttl+"_VLU"))%></b></td>
             <%}%>
                  <td align="right"><b><%=util.nvl((String)memoTotalsDtl.get("CNT"))%></b></td>
                  <td align="right"><b><%=util.nvl((String)memoTotalsDtl.get("QTY"))%></b></td>
                  <td align="right"><b><%=util.nvl((String)memoTotalsDtl.get("CTS"))%></b></td>  
                  <td align="right"><b><%=util.nvl((String)memoTotalsDtl.get("VLU"))%></b></td>   
           </tr>
      <input type="hidden" id="count" value="<%=loop%>" />
      </tbody>
  </table>
   </td>
   </tr>
</td>
</tr>
   <%}%>
<%}}else{%>
<tr><td valign="top" class="hedPg">Sorry no result found </td></tr>
<%}%>
  <tr><td>
<jsp:include page="/footer.jsp" />
</td></tr>
  </table>  
  </body>
</html>