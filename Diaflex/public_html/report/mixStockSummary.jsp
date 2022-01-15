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
<link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/general.css?v=<%=info.getJsVersion()%>"/>
<script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/ajax.js?v=<%=info.getJsVersion()%> " > </script>
  <script type="text/javascript" src="<%=info.getReqUrl()%>/PopScripts/popup.js?v=<%=info.getJsVersion()%> " > </script>
    <script src="<%=info.getReqUrl()%>/PopScripts/jquery-1.2.6.min.js" type="text/javascript"></script>
   <script language="javaScript" type="text/javascript" src="<%=info.getReqUrl()%>/calendar/calendar.js"></script>
   <link href="<%=info.getReqUrl()%>/calendar/calendar.css" rel="stylesheet" type="text/css" />
  <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/style.css?v=<%=info.getJsVersion()%>"/>
  <title>Mix Stock Summary Report</title>
  </head>
        <%String logId=String.valueOf(info.getLogId());
        String onfocus="cook('"+logId+"')";
        %>
 <body onfocus="<%=onfocus%>" onload="MM_preloadImages('images/faunalogo.png')" topmargin="0" leftmargin="0" rightmargin="0" bottommargin="0">
   <%
   HashMap prp = info.getPrp();
    HashMap groupDsc =((HashMap)util.getFromMem(info.getMem_ip(),info.getMem_port(),info.getCnt()+"_groupDsc") == null)?new HashMap():(HashMap)util.getFromMem(info.getMem_ip(),info.getMem_port(),info.getCnt()+"_groupDsc"); 
    ArrayList deptLst= new ArrayList();
    ArrayList grpList= new ArrayList();
    HashMap dataTbl= new HashMap();
      String typ=util.nvl(request.getParameter("typ"));
    HashMap reportDtl= (session.getAttribute("reportDtl") == null)?new HashMap():(HashMap)session.getAttribute("reportDtl");
    String memoLst= util.nvl((String)request.getAttribute("memo"));
    String loc = util.nvl((String)request.getAttribute("loc"));
    if(loc.equals("NA"))
    loc="";
    String mlot= util.nvl((String)request.getAttribute("mlot"));
    if(reportDtl!=null && reportDtl.size()>0){
    deptLst=(ArrayList)reportDtl.get("GRP_HDR");
    grpList=(ArrayList)reportDtl.get("GRP_CONTENT");
    dataTbl=(HashMap)reportDtl.get("GRP_DATA");
    }
    HashMap data=new HashMap();
    String Qty="";
    String vlu="";
  String Cts="";
  String Avg="";
  String Dis="";
  String Fapri="";
  String Mfgpri="";
            String df="MIX_STOCK_SUMMARY";

            if(typ.equals("RGH")){
                df="RGH_STOCK_SUMMARY";

            }
  HashMap allPageDtl= (info.getPageDetails() == null)?new HashMap():(HashMap)info.getPageDetails();
  HashMap pageDtl=(HashMap)allPageDtl.get(df);
  ArrayList pageList=new ArrayList();
    HashMap pageDtlMap=new HashMap();
    String fld_nme="",fld_ttl="",val_cond="",dflt_val="",fld_typ="",form_nme="",flg1="",lov_qry="";
  boolean islevel2 = false;
  pageList=(ArrayList)pageDtl.get("LEVEL2ROW");
    if(pageList!=null && pageList.size() >0){
    islevel2 = true;
    }
  
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
  <% if(typ.equals("RGH")){%>
 Rrough Stock Summary
  <%}else{%>
Mix Stock Summary
<%}%>
  </span> </td>
</tr></table>
  </td>
  </tr>
  <%if(mlot.equals("")){%>
  <tr>
    <td valign="top" class="hedPg">
  <html:form action="report/mixStockSummary.do?method=fetchMemo" method="post">
  <table>
    <tr>
    <!--<td>Memo ID:</td>
    <td><html:textarea property="memo" name="mixStockSummary" styleId="memoId"/></td>
    <td><html:submit property="value(submit)" styleClass="submit" value="Fetch" /> </td>-->
     <%pageList=(ArrayList)pageDtl.get("FILTER");
    if(pageList!=null && pageList.size() >0){
    for(int j=0;j<pageList.size();j++){
    pageDtlMap=(HashMap)pageList.get(j);
    fld_nme=(String)pageDtlMap.get("fld_nme");
    fld_typ=(String)pageDtlMap.get("fld_typ");
    fld_ttl=(String)pageDtlMap.get("fld_ttl");
    val_cond=(String)pageDtlMap.get("val_cond");
    dflt_val=(String)pageDtlMap.get("dflt_val");
    %>
    <td><%=fld_ttl%></td>
    <%
    if(fld_typ.equals("L")){
    ArrayList prpVal = (ArrayList)prp.get(dflt_val+"V");
    if(prpVal!=null && prpVal.size()>0){
    String fldVal ="value("+fld_nme+")";
    %>
    <td>
    <html:select property="<%=fldVal%>" name="mixStockSummary" >
  <html:option value="">---select---</html:option>
    <%for(int i=0;i<prpVal.size();i++){
    String lprp =(String)prpVal.get(i);
    %>
    <html:option value="<%=lprp%>"><%=lprp%></html:option>
    <%}%>
    </html:select>
   </td>
    <%}}if(fld_typ.equals("TA")){%>
    <td><html:textarea property="<%=fld_nme%>" name="mixStockSummary" styleId="<%=fld_ttl%>"/></td>
    <%}if(fld_typ.equals("S")){%>
    <td>&nbsp;<html:submit property="value(submit)" styleClass="submit" value="<%=dflt_val%>" /> </td>
    <%}}
    }%> 
    </tr>
  </table>
  </html:form></td>
  </tr>
    <%}%>
  <tr><td valign="top" class="hedPg"><span id="excel">Create Excel <img src="../images/ico_file_excel.png" title="Click here to Generate Excel" onclick="goToMixCreateXl('mixStockSummary.do?method=createGridXL&excelNm=GroupLab&Report=GRP')" border="0"/></span> &nbsp;<b>Show</b>
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
  <tr><td class="hedPg"><table class="grid1" id="data" >
  <tr>
  <th>Dept/Group</th>
  <%for(int i=0;i<grpList.size();i++){
  String grp=(String)grpList.get(i);
  String dsc=util.nvl2((String)dataTbl.get(grp),grp);%>
  <th colspan="6"><%=dsc%></th>
  <%}%>
  <th colspan="6" >Total</th>
  </tr>
  <tr>
  <td></td>
   <%for(int i=0;i<grpList.size();i++){%>
 
  <td><span id="CTS_<%=i%>" >CTS</span></td>
   <td><span id="AVG_<%=i%>" >AVG</span></td>
   <td><span id="FAPRI_<%=i%>" style="display:none;">FAPRI</span></td>
   <td><span id="MFGPRI_<%=i%>" style="display:none;">MFGPRI</span></td>
   <td><span id="QTY_<%=i%>" style="display:none;">QTY</span></td>
   <td><span id="VLU_<%=i%>" style="display:none;">VLU</span></td>
  

 
  
  <%}%>
 
  <td><span id="TOTALCTS" >CTS</span></td>
    <td><span id="TOTALAVG" >AVG</span></td>
    <td><span id="TOTALFAPRI" style="display:none;">FAPRI</span></td>
    <td><span id="TOTALMFGPRI" style="display:none;">MFGPRI</span></td>
   <td><span id="TOTALQTY" style="display:none;">QTY</span></td>
   <td><span id="TOTALVLU" style="display:none;">VLU</span></td>

  
  </tr>
  <%for(int j=0;j<deptLst.size();j++){
    String lab=(String)deptLst.get(j);%>
  <tr>
  <th><%=lab%></th>
  <%for(int k=0;k<grpList.size();k++){
  String grp=(String)grpList.get(k);
  data=new HashMap();
  Qty="";
  Cts="";
  Avg="";
  Dis="";
  Fapri="";
  Mfgpri="";
  vlu="";
  data=(HashMap)dataTbl.get(grp+"_"+lab);
  if(data!=null && data.size()>0){
  Qty=util.nvl((String)data.get("QTY"));
  Cts=util.nvl((String)data.get("CTS"));
  vlu=util.nvl((String)data.get("VLU"));
  Avg=util.nvl((String)data.get("AVG"));
  Fapri=util.nvl((String)data.get("FAPRI"));
  Mfgpri=util.nvl((String)data.get("MFGPRI"));
  if(Avg.equals(""))
  Avg = "0";
  Dis=util.nvl((String)data.get("RAP"));
  }
  %>
 
  <td><span id="CTS_<%=j%>_<%=k%>"><a title="Packet Details" target="_blank"  href="mixStockSummary.do?method=pktDtl&group=<%=grp%>&lab=<%=lab%>&memo=<%=memoLst%>&cts=<%=Cts%>&avg=<%=Avg%>&mlot=<%=mlot%>&typ=<%=typ%>&loc=<%=loc%>" ><%=Cts%></a></span></td>
  <%if(islevel2){%>
    <td><span id="AVG_<%=j%>_<%=k%>"><a title="Status Wise report"  href="mixStockSummary.do?method=loadmixSZCLR&group=<%=grp%>&lab=<%=lab%>&memo=<%=memoLst%>&mlot=<%=mlot%>&typ=<%=typ%>" ><%=Avg%></a></span></td>
    <%}else{%>
    <td><span id="AVG_<%=j%>_<%=k%>"><%=Avg%></span></td>
    <%}%>
  <td align="center"><span id="FAPRI_<%=j%>_<%=k%>" style="display:none;"><%=Fapri%></span></td>
  <td align="center"><span id="MFGPRI_<%=j%>_<%=k%>" style="display:none;"><%=Mfgpri%></span></td>
  <td align="center"><span id="QTY_<%=j%>_<%=k%>" style="display:none;"><%=Qty%></span></td>
  <td align="center"><span id="VLU_<%=j%>_<%=k%>" style="display:none;"><%=vlu%></span></td>

 
  <%}%>
  <%
  Qty="";
  Cts="";
  Avg="";
  Dis="";
  Fapri="";
  Mfgpri="";
  vlu="";
  data=new HashMap();
  data=(HashMap)dataTbl.get(lab+"_TTL");
  if(data!=null && data.size()>0){
  Qty=util.nvl((String)data.get("QTY"));
  Cts=util.nvl((String)data.get("CTS"));
  Avg=util.nvl((String)data.get("AVG"));
  Fapri=util.nvl((String)data.get("FAPRI"));
  Mfgpri=util.nvl((String)data.get("MFGPRI"));
  vlu=util.nvl((String)data.get("VLU"));
   if(Avg.equals(""))
    Avg = "0";
  Dis=util.nvl((String)data.get("RAP"));
  }
  %>
 
  <td><span id="TOTALCTS_<%=j%>" ><a title="Packet Details" target="_blank"  href="mixStockSummary.do?method=pktDtl&group=ALL&lab=<%=lab%>&memo=<%=memoLst%>&mlot=<%=mlot%>&loc=<%=loc%>" ><%=Cts%></a></span></td>
  <%if(islevel2){%>
    <td><span id="TOTALAVG_<%=j%>" ><A title="Status Wise report" href="mixStockSummary.do?method=loadmixSZCLR&group=ALL&lab=<%=lab%>&memo=<%=memoLst%>&mlot=<%=mlot%>"> <%=Avg%> </a></span></td>
    <%}else{%>
    <td><span id="TOTALAVG_<%=j%>" ><%=Avg%></span></td>
    <%}%>
    <td align="center"><span id="TOTALFAPRI_<%=j%>" style="display:none;"><%=Fapri%></span></td>
  <td align="center"><span id="TOTALMFGPRI_<%=j%>" style="display:none;"><%=Mfgpri%></span></td>
  <td align="center"><span id="TOTALQTY_<%=j%>" style="display:none;"><%=Qty%></span></td>
  <td align="center"><span id="TOTALVLU_<%=j%>" style="display:none;"><%=vlu%></span></td>
 
  </tr>
  <%}%>
  <tr>
  <th>Total</th>
   <%for(int i=0;i<grpList.size();i++){
  String grp=(String)grpList.get(i);
  Qty="";
  Cts="";
  Avg="";
  Dis="";
  Fapri="";
  Mfgpri="";
  vlu="";
  data=new HashMap();
  data=(HashMap)dataTbl.get(grp+"_TTL");
  if(data!=null && data.size()>0){
  Qty=util.nvl((String)data.get("QTY"));
  Cts=util.nvl((String)data.get("CTS"));
  Avg=util.nvl((String)data.get("AVG"));
  Fapri=util.nvl((String)data.get("FAPRI"));
  Mfgpri=util.nvl((String)data.get("MFGPRI"));
  vlu=util.nvl((String)data.get("VLU"));
  if(Avg.equals(""))
  Avg="0";
  Dis=util.nvl((String)data.get("RAP"));
  }
  %>
 
  <td><span id="TTLCTS_<%=i%>" ><a title="Packet Details" target="_blank"  href="mixStockSummary.do?method=pktDtl&group=<%=grp%>&lab=ALL&memo=<%=memoLst%>&mlot=<%=mlot%>&loc=<%=loc%>" ><%=Cts%></a></span></td>
    <%if(islevel2){%>
   <td><span id="TTLAVG_<%=i%>"><a  title="Status Wise report" href="mixStockSummary.do?method=loadmixSZCLR&group=<%=grp%>&lab=ALL&memo=<%=memoLst%>&mlot=<%=mlot%>" > <%=Avg%></a></span></td>
  <%}else{%>
  <td><span id="TTLAVG_<%=i%>"><%=Avg%></span></td>
  <%}%>
   <td  align="center"><span  id="TTLFAPRI_<%=i%>" style="display:none;"><%=Fapri%></span></td>
  <td  align="center"><span  id="TTLMFGPRI_<%=i%>" style="display:none;"><%=Mfgpri%></span></td>
   <td  align="center"><span  id="TTLQTY_<%=i%>" style="display:none;"><%=Qty%></span></td>
   <td  align="center"><span  id="TTLVLU_<%=i%>" style="display:none;"><%=vlu%></span></td>
  
  
  <%}%>
  <%Qty="";
  Cts="";
  Avg="";
  Dis="";
  Fapri="";
  Mfgpri="";
  vlu="";
  data=new HashMap();
  data=(HashMap)dataTbl.get("TTL");
  if(data!=null && data.size()>0){
  Qty=util.nvl((String)data.get("QTY"));
  Cts=util.nvl((String)data.get("CTS"));
  Avg=util.nvl((String)data.get("AVG"));
  Fapri=util.nvl((String)data.get("FAPRI"));
  Mfgpri=util.nvl((String)data.get("MFGPRI"));
  vlu=util.nvl((String)data.get("VLU"));
  if(Avg.equals(""))
  Avg="0";
  Dis=util.nvl((String)data.get("RAP"));
  }
  %>
 
  <td><span id="GTTLCTS" ><a title="Packet Details" target="_blank"  href="mixStockSummary.do?method=pktDtl&group=ALL&lab=ALL&memo=<%=memoLst%>&mlot=<%=mlot%>&loc=<%=loc%>" ><%=Cts%></a></span></td>
  <%if(islevel2){%>
  <td><span id="GTTLAVG" ><a  title="Status Wise report" href="mixStockSummary.do?method=loadmixSZCLR&group=ALL&lab=ALL&memo=<%=memoLst%>&mlot=<%=mlot%>" ><%=Avg%></a></span></td>
   <%}else{%>
   <td><span id="GTTLAVG" ><%=Avg%></span></td>
   <%}%>
    <td  align="center"><span id="GTTLFAPRI" style="display:none;"> <%=Fapri%></a></span></td>
  <td  align="center"><span id="GTTLMFGPRI" style="display:none;"> <%=Mfgpri%></a></span></td>
  <td  align="center"><span id="GTTLQTY" style="display:none;"> <%=Qty%></a></span></td>
  <td  align="center"><span id="GTTLVLU" style="display:none;"> <%=vlu%></a></span></td>
 
  
  </tr>

</table></td></tr>
  <tr><td>
<jsp:include page="/footer.jsp" />
</td></tr>
  </table>  
  </body>
</html>