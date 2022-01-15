<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page errorPage="../error_page.jsp" %>
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import="java.util.ArrayList,java.util.ArrayList,java.util.HashMap, java.sql.*, java.util.Enumeration, java.util.logging.Level"%>
<jsp:useBean id="info" class="ft.com.InfoMgr" scope="session" />
<jsp:useBean id="util" class="ft.com.JspUtil" scope="page" />
<%@taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>Group Wise</title>
   <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/bse.css?v=<%=info.getJsVersion()%>"/>
    <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/general.css?v=<%=info.getJsVersion()%>"/>
     <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/style.css?v=<%=info.getJsVersion()%>"/>
     <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/bse.js?v=<%=info.getJsVersion()%> " > </script>
        <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/ajax.js?v=<%=info.getJsVersion()%> " > </script>
      <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/assortScript.js?v=<%=info.getJsVersion()%> " > </script>
     <script type="text/javascript" src="<%=info.getReqUrl()%>/PopScripts/popup.js?v=<%=info.getJsVersion()%> " > </script>
   <script src="<%=info.getReqUrl()%>/PopScripts/jquery-1.2.6.min.js" type="text/javascript"></script>
    <script type="text/javascript" src="<%=info.getReqUrl()%>/jqueryScript/jquery.js " > </script>

  </head>
        <%String logId=String.valueOf(info.getLogId());
        String onfocus="cook('"+logId+"')";
        %>
 <body onfocus="<%=onfocus%>">
 <div id="backgroundPopup"></div>
<div id="popupContactASSFNL">
<table class="popUpTb">
 <tr><td height="5%">&nbsp;&nbsp;&nbsp;<html:button property="value(close)" styleClass="submit"  onclick="MKEmptyPopup('SC');disablePopupASSFNL()" value="Close"  /> </td></tr>
<tr><td valign="top" height="95%">
<iframe name="SC" class="frameStyle"   align="middle" frameborder="0">

</iframe></td></tr>
</table>
</div>
<input type="hidden" id="reqUrl" name="reqUrl" value="<%=info.getReqUrl()%>"/>

  <table width="100%"  border="0" align="center" cellpadding="0" cellspacing="0" >
  
 
  <tr>
    <td height="5" valign="top" class="greyline"><img src="../images/grey_linebig.png" width="685" height="5" align="right" /></td>
  </tr>
  <tr>
  <td valign="top" class="hedPg">
  <table cellpadding="1" cellspacing="5"><tr><td valign="middle">
  <img src="../images/line.jpg" border="0" /></td><td valign="middle"> 
  <span class="pgHed">Comparision Group Wise
  </span></td></tr></table>
  </td></tr>
  
  <tr><td valign="top" class="tdLayout">
     <table id="dataTable">
   <%
   ArrayList allowGrpVw = (session.getAttribute("ALLOWGRP_PRPVW") == null)?new ArrayList():(ArrayList)session.getAttribute("ALLOWGRP_PRPVW");
   HashMap datatable = (session.getAttribute("grpdatatable") == null)?new HashMap():(HashMap)session.getAttribute("grpdatatable");
   if(datatable!=null && datatable.size()>0){
   HashMap mprp = info.getMprp();
   HashMap prp = info.getPrp();
   ArrayList szGrpLst =(ArrayList)datatable.get("SIZEGRP");
   ArrayList shGrpLst=(ArrayList)datatable.get("SHAPEGRP");
    ArrayList dscLst=new ArrayList();
    dscLst.add("+");dscLst.add("=");dscLst.add("-"); 
    HashMap dscLstDtl=new HashMap();
    dscLstDtl.put("+","P");
    dscLstDtl.put("=","E"); 
    dscLstDtl.put("-","M"); 
    int allowGrpVwsz=allowGrpVw.size();
    int shGrpLstsz=shGrpLst.size();
    int szGrpLstsz=szGrpLst.size();
    String bgcolor="";
    String CNT="",CNTGNT="",SZCNT="";double per=0.0;
    for(int al=0;al<allowGrpVwsz;al++){
    String lprp=(String)allowGrpVw.get(al);
    ArrayList lprpGrpLst =(ArrayList)datatable.get(lprp);
    ArrayList lprpGrpLst_fromPrp = (ArrayList)prp.get(lprp+"G");
    int lprpGrpLstsz=lprpGrpLst.size();
    int lprpGrpLst_fromPrpsz=lprpGrpLst_fromPrp.size();
    for(int s=0;s<shGrpLstsz;s++){
    String shape=(String)shGrpLst.get(s);
    String target = "SC_"+shape+"_"+lprp;
    ArrayList lprpGrpLst_alreadyDis=new ArrayList();
  
   %>
   <tr id="<%=target%>"><td>
   <table class="grid1small">
   <tr><th colspan="<%=5+((szGrpLstsz*3)*2)+szGrpLstsz%>"><%=shape %> - <%=util.nvl((String)mprp.get(lprp+"D"))%> Comparision</th></tr>
   <tr>
   <td></td>
   <%for(int sz=0;sz<szGrpLstsz;sz++){%>
   <th colspan="6" align="center"><%=util.nvl((String)szGrpLst.get(sz))%></th>
   <th>TOT</th>
   <%}%>
   <th colspan="3" align="center">Sub Total</th>
   <th colspan="" align="center">Total</th>
   </tr>
   <tr>
   <td></td>
   <%for(int sz=0;sz<szGrpLstsz;sz++){%>
   <td align="center" colspan="2">+</td><td align="center" colspan="2">=</td><td align="center" colspan="2">-</td><td></td>
   <%}%>
   <td align="center" colspan="">+</td><td align="center" colspan="">=</td><td align="center" colspan="">-</td><td></td>
  </tr>
   <%
   for(int lp=0;lp<lprpGrpLst_fromPrpsz;lp++){
   String lprpgrp=(String)lprpGrpLst_fromPrp.get(lp);
   if(lprpGrpLst.contains(lprpgrp) && !lprpGrpLst_alreadyDis.contains(lprpgrp)){
   lprpGrpLst_alreadyDis.add(lprpgrp);
   bgcolor="";
   %> 
   <tr>
   <th><%=lprpgrp%> &nbsp;&nbsp;
   <img src="../images/plus.jpg" id="src_<%=shape%>_<%=lprpgrp%>" onclick="irCmpGrpWise('<%=lprpgrp%>','<%=shape%>','<%=lprp%>',this)" /> 
   </th>
   <%
   HashMap rowTotal = new HashMap();
   for(int sz=0;sz<szGrpLstsz;sz++){
   String szgrp=(String)szGrpLst.get(sz);
   if(bgcolor.equals("") || bgcolor.equals("#f8f8f8"))
   bgcolor="#efefef";
   else
   bgcolor="#f8f8f8";
   SZCNT="0";
   for(int j=0;j<dscLst.size();j++){
   String dsc=(String)dscLst.get(j); 
   CNT=util.nvl((String)datatable.get(shape+"_"+szgrp+"_"+lprpgrp+"_"+dsc+"_"+lprp));
   String ttlCnt = util.nvl((String)rowTotal.get(dsc));
   if(ttlCnt.equals(""))
   ttlCnt="0";
   if(CNT.equals(""))
   CNT="0";
   int calCnt = Integer.parseInt(ttlCnt)+Integer.parseInt(CNT);
   rowTotal.put(dsc, String.valueOf(calCnt));
   %>
   <td align="right" bgcolor="<%=bgcolor%>">
   <%if(!CNT.equals("")){
   CNTGNT="0";
   for(int ttlp=0;ttlp<lprpGrpLstsz;ttlp++){
   String ttllprpgrp=(String)lprpGrpLst.get(ttlp);
   CNTGNT=String.valueOf(Integer.parseInt(CNTGNT)+Integer.parseInt(util.nvl((String)datatable.get(shape+"_"+szgrp+"_"+ttllprpgrp+"_"+dsc+"_"+lprp),"0")));
   }
   SZCNT=String.valueOf(Integer.parseInt(SZCNT)+Integer.parseInt(CNT));
   %>
   <a href="comparisonReport.do?method=moncmpGrpPKTDTL&sign=<%=dscLstDtl.get(dsc)%>&lprp=<%=lprp%>&grp=<%=lprpgrp%>&szgrp=<%=szgrp%>&shape=<%=shape%>" id="LNK_<%=shape%>_<%=lprp%>" onclick="loadASSFNL('<%=target%>','LNK_<%=shape%>_<%=lprp%>')"  target="SC" title="Click Here to get Packets"><%=CNT%></a>
   <%}%>
   </td>
   <td nowrap="nowrap" bgcolor="<%=bgcolor%>" align="right">
   <%if(!CNT.equals("")){%>
    <%=util.roundToDecimals(((Double.parseDouble(CNT)/Double.parseDouble(CNTGNT))*100),2)%>%
   <%}%>
   </td>
   <%}%>
   <td bgcolor="<%=bgcolor%>" align="right"><b><%=SZCNT%></b></td>
   <%}%>
   <td bgcolor="<%=bgcolor%>" align="right"><%=rowTotal.get("+")%></td>
   <td bgcolor="<%=bgcolor%>" align="right"><%=rowTotal.get("=")%></td>
   <td bgcolor="<%=bgcolor%>" align="right"><%=rowTotal.get("-")%></td>
   <td bgcolor="<%=bgcolor%>" align="right"><b><%=Integer.parseInt((String)rowTotal.get("+"))+Integer.parseInt((String)rowTotal.get("="))+Integer.parseInt((String)rowTotal.get("-"))%></b></td><td></td>
   </tr>
   <tr style="display:none" id="<%=shape%>_<%=lprp%>_<%=lprpgrp%>"><td></td>
   <%
   for(int sz=0;sz<szGrpLstsz;sz++){
   String szgrp=(String)szGrpLst.get(sz);
   for(int m=0;m<dscLst.size();m++){
    String dsc = (String)dscLst.get(m);%>
  <td valign="top" nowrap="nowrap" colspan="2">
  <table width="100%"><tr><td width="100%" nowrap="nowrap">
  <div id="<%=shape%>_<%=lprp%>_<%=lprpgrp%>_<%=szgrp%>_<%=dsc%>" style="width:100%;vertical-align: top; margin:0px;"></div>
  </td></tr></table></td>
  <%}%>
  <td></td>
  <%}%>
  </tr>
   <%}%>
   <%}%>
   <tr>
   <th>TOTAL
   &nbsp;&nbsp;
   <img src="../images/plus.jpg" id="src_<%=shape%>_TOTAL" onclick="irCmpGrpWise('TOTAL','<%=shape%>','<%=lprp%>',this)" /> 
   </th>
   <%
   bgcolor="";
   HashMap rowTotal = new HashMap();
   for(int sz=0;sz<szGrpLstsz;sz++){
   String szgrp=(String)szGrpLst.get(sz);
   CNTGNT=util.nvl((String)datatable.get(shape+"_"+szgrp+"_GRPTotal_Total_"+lprp),"0");
   if(bgcolor.equals("") || bgcolor.equals("white"))
   bgcolor="#eeeeee";
   else
   bgcolor="white";
   SZCNT="0";
   for(int j=0;j<dscLst.size();j++){
   String dsc=(String)dscLst.get(j); 
   CNT="0";
   String ttlCnt = util.nvl((String)rowTotal.get(dsc));
   if(ttlCnt.equals(""))
   ttlCnt="0";
   for(int lp=0;lp<lprpGrpLstsz;lp++){
   String lprpgrp=(String)lprpGrpLst.get(lp);
   CNT=String.valueOf(Integer.parseInt(CNT)+Integer.parseInt(util.nvl((String)datatable.get(shape+"_"+szgrp+"_"+lprpgrp+"_"+dsc+"_"+lprp),"0")));
   %>
   <%}
   SZCNT=String.valueOf(Integer.parseInt(SZCNT)+Integer.parseInt(CNT));
   int calCnt = Integer.parseInt(ttlCnt)+Integer.parseInt(CNT);
   rowTotal.put(dsc, String.valueOf(calCnt));
   %>
   <td nowrap="nowrap" bgcolor="<%=bgcolor%>" align="right">
   <%if(!CNT.equals("0")){%>
   <a href="comparisonReport.do?method=moncmpGrpPKTDTL&sign=<%=dscLstDtl.get(dsc)%>&lprp=<%=lprp%>&grp=TOTAL&szgrp=<%=szgrp%>&shape=<%=shape%>" id="LNK_<%=shape%>_<%=lprp%>" onclick="loadASSFNL('<%=target%>','LNK_<%=shape%>_<%=lprp%>')"  target="SC" title="Click Here to get Packets"><%=CNT%></a>
   
   <%}else{%>
   0  
   <%}%>
   </td>
   <td nowrap="nowrap" bgcolor="<%=bgcolor%>" align="right">
   <%if(!CNT.equals("0")){%>
   &nbsp; <%=util.roundToDecimals(((Double.parseDouble(CNT)/Double.parseDouble(CNTGNT))*100),2)%>%
   <%}else{%>
    0%
   <%}%>
   </td>
   <%}%>
   <td bgcolor="<%=bgcolor%>" align="right"><b><%=SZCNT%></b></td>
   <%}%>
   <td bgcolor="<%=bgcolor%>" align="right"><%=rowTotal.get("+")%></td>
   <td bgcolor="<%=bgcolor%>" align="right"><%=rowTotal.get("=")%></td>
   <td bgcolor="<%=bgcolor%>" align="right"><%=rowTotal.get("-")%></td>
   <td bgcolor="<%=bgcolor%>" align="right"><b><%=Integer.parseInt((String)rowTotal.get("+"))+Integer.parseInt((String)rowTotal.get("="))+Integer.parseInt((String)rowTotal.get("-"))%></b></td><td></td>
   </tr>
   <tr style="display:none" id="<%=shape%>_<%=lprp%>_TOTAL"><td></td>
   <%
   for(int sz=0;sz<szGrpLstsz;sz++){
   String szgrp=(String)szGrpLst.get(sz);
   for(int m=0;m<dscLst.size();m++){
    String dsc = (String)dscLst.get(m);%>
  <td valign="top" nowrap="nowrap" colspan="2">
  <table width="100%"><tr><td width="100%" nowrap="nowrap">
  <div id="<%=shape%>_<%=lprp%>_TOTAL_<%=szgrp%>_<%=dsc%>" style="width:100%;vertical-align: top; margin:0px;"></div>
  </td></tr></table></td>
  <%}%>
  <td></td>
  <%}%>
  </tr>
   </table>
   <%}%>
   <%}%>
   
   <table class="grid1">
   <tr><th colspan="<%=(1+(shGrpLstsz*3)+3+3)%>">Shape Wise Summary</th></tr>
   <tr>
   <td></td>
   <%for(int s=0;s<shGrpLstsz;s++){%>
   <td colspan="3" align="center"><%=shGrpLst.get(s)%></td>
   <td></td>
   <%}%>
   <td colspan="3" align="center">Sub Total</td>
   <td colspan="" align="center">Total</td>
   </tr>
   <tr>
   <td></td>
   <%for(int s=0;s<shGrpLstsz;s++){
   for(int j=0;j<dscLst.size();j++){
   String dsc=(String)dscLst.get(j);
   %>
   <td align="center"><%=dscLst.get(j)%></td>
   <%}%>
   <td>TOT</td>
   <%}%>
   <%
   for(int j=0;j<dscLst.size();j++){
   String dsc=(String)dscLst.get(j);
   %>
   <td align="center"><%=dscLst.get(j)%></td>
   <%}%>
      <td></td>
   </tr>
    <%
    for(int al=0;al<allowGrpVwsz;al++){
    String lprp=(String)allowGrpVw.get(al);
    %>
    <tr>
    <th><%=util.nvl((String)mprp.get(lprp+"D"))%></th>
   <%for(int s=0;s<shGrpLstsz;s++){
   String shape=(String)shGrpLst.get(s);
   CNTGNT=util.nvl((String)datatable.get(shape+"_Total_"+lprp),"0");
   SZCNT="0";
   for(int j=0;j<dscLst.size();j++){
   String dsc=(String)dscLst.get(j); 
   CNT=util.nvl((String)datatable.get(shape+"_"+dsc+"_"+lprp),"0");
   SZCNT=String.valueOf(Integer.parseInt(SZCNT)+Integer.parseInt(CNT));
   %>
   <td><%=CNT%> | <%=util.roundToDecimals(((Double.parseDouble(CNT)/Double.parseDouble(CNTGNT))*100),2)%>%</td>
   <%}%>
   <td align="right"><b><%=SZCNT%></b></td>
   <%}%>
   
   <%CNTGNT=util.nvl((String)datatable.get("SHTotal_Total_"+lprp),"0");
   for(int j=0;j<dscLst.size();j++){
   String dsc=(String)dscLst.get(j); 
   CNT="0";
   for(int s=0;s<shGrpLstsz;s++){
   String shape=(String)shGrpLst.get(s);
   CNT=String.valueOf(Integer.parseInt(CNT)+Integer.parseInt(util.nvl((String)datatable.get(shape+"_"+dsc+"_"+lprp),"0")));
   %>
   <%}%>
   <td nowrap="nowrap"><%=CNT%> | <%=util.roundToDecimals(((Double.parseDouble(CNT)/Double.parseDouble(CNTGNT))*100),2)%>%</td>
   <%}%>
   <td align="right"><b><%=CNTGNT%></b></td>
    </tr>
    <%}%>
   </table></td></tr>
   <%}else{%>
   <tr><td> no result found</td></tr>
   <%}%>
   </table>
   </td></tr>
   
    <tr><td>
<jsp:include page="/footer.jsp" />
</td></tr>
  </table>
  </body>
</html>