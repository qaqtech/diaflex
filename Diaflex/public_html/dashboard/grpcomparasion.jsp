<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page errorPage="../error_page.jsp" %>
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import="java.util.ArrayList,java.util.ArrayList,java.util.HashMap, java.sql.*, java.util.Collections, java.util.Enumeration, java.util.logging.Level,java.util.Calendar"%>
<jsp:useBean id="info" class="ft.com.InfoMgr" scope="session" />
<jsp:useBean id="util" class="ft.com.JspUtil" scope="page" />
<%@taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>
 
<html>
  <head>
  <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
  <title>Reports</title>
  <script src="<%=info.getReqUrl()%>/jqueryScript/jqueryScript.js?v=<%=info.getJsVersion()%>" type="text/javascript"></script>
     <script language="javaScript" type="text/javascript" src="<%=info.getReqUrl()%>/calendar/calendar.js"></script>
   <script src="<%=info.getReqUrl()%>/PopScripts/jquery-1.2.6.min.js" type="text/javascript"></script>
<script type="text/javascript" src="<%=info.getReqUrl()%>/chartscript/amcharts.js?v=<%=info.getJsVersion()%> " > </script>
<script type="text/javascript" src="<%=info.getReqUrl()%>/chartscript/chartajax.js?v=<%=info.getJsVersion()%> " > </script>
<script type="text/javascript" src="<%=info.getReqUrl()%>/chartscript/serial.js?v=<%=info.getJsVersion()%> " > </script>
<script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/bse.js?v=<%=info.getJsVersion()%> " > </script>
    <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/bse.css?v=<%=info.getJsVersion()%>"/>
    <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/style.css?v=<%=info.getJsVersion()%>"/>
          <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/general.css?v=<%=info.getJsVersion()%>"/>
	        <link rel="stylesheet" href="<%=info.getReqUrl()%>/css/jScrollbar.jquery.css" type="text/css" />
			   <link href="<%=info.getReqUrl()%>/calendar/calendar.css" rel="stylesheet" type="text/css" />
                           <script type="text/javascript" src="<%=info.getReqUrl()%>/chartscript/pie.js?v=<%=info.getJsVersion()%> " > </script>
                                   <script type="text/javascript" src="<%=info.getReqUrl()%>/PopScripts/popup.js?v=<%=info.getJsVersion()%> " > </script>
  </head>
        <%
    ArrayList prpDspBlocked = info.getPageblockList();
    HashMap allPageDtl= (info.getPageDetails() == null)?new HashMap():(HashMap)info.getPageDetails();
    HashMap pageDtl=(HashMap)allPageDtl.get("DASH_GRP_COMP");
    ArrayList pageList=new ArrayList();
    HashMap pageDtlMap=new HashMap();
    String fld_nme="",fld_ttl="",val_cond="",dflt_val="",fld_typ="",form_nme="",flg1="",lov_qry="",url="",barGrp="";
    HashMap mprp = info.getMprp();
    HashMap prp = info.getSrchPrp();
    ArrayList  vwPrpLst = (ArrayList)session.getAttribute("filterlprpLst");
    ArrayList  grpList = (ArrayList)session.getAttribute("dashboardgrpList");
    ArrayList vWPrpListAll = (ArrayList)session.getAttribute("DASH_GRP_COMP_ALL");
    String  days = util.nvl((String)request.getAttribute("days"),"0");
    String filterDsc=util.nvl((String)session.getAttribute("filterDsc"));
    ArrayList agegrplst=(ArrayList)session.getAttribute("agegrplst");
    if(filterDsc.equals(""))
    filterDsc="All Data ";
        String logId=String.valueOf(info.getLogId());
        String color="";
        String onfocus="cook('"+logId+"')";
        String firstprp=(String)vwPrpLst.get(0);
        String pieheight="500";
        %>
 <body onfocus="<%=onfocus%>">
 <div id="backgroundPopup"></div>
<div id="popupContactSave" align="center" >
<img src="../images/loading_flame.gif" height="150px" width="150px" border="0" />
</div>
<table cellpadding="2" cellspacing="0" width="45%">
<tr><td valign="top"><b style="font-size:10px;">Quick View</b></td></tr>
</table>
<html:form action="dashboard/screen.do?method=fetchgrpcomp" method="post" onsubmit="" >
<html:hidden property="value(BTN_DAYS)" styleId="BTN_DAYS" />
<html:hidden property="value(GRAPH_BY)" styleId="GRAPH_BY" value="VLU" />
<html:hidden property="value(RPT_PRP)" styleId="RPT_PRP" value="<%=firstprp%>" />
<html:hidden property="value(PIE_TYP)" styleId="PIE_TYP" value="PRP" />
<table class="boardgrid" id="boardgrid">
<tr>
<td align="center" nowrap="nowrap">
<%
pageList= ((ArrayList)pageDtl.get("SUBMIT") == null)?new ArrayList():(ArrayList)pageDtl.get("SUBMIT");
            if(pageList!=null && pageList.size() >0){
            for(int j=0;j<pageList.size();j++){
            pageDtlMap=(HashMap)pageList.get(j);
            lov_qry=(String)pageDtlMap.get("lov_qry");
            if(lov_qry.equals(""))
            fld_nme="value("+(String)pageDtlMap.get("fld_nme")+")";
            else
            fld_nme=(String)pageDtlMap.get("fld_nme");
            fld_ttl=(String)pageDtlMap.get("fld_ttl");
            fld_typ=(String)pageDtlMap.get("fld_typ");
            dflt_val=(String)pageDtlMap.get("dflt_val");
            val_cond=(String)pageDtlMap.get("val_cond"); 
            if(days.equals(dflt_val)){
              color="color:#ffffff";
              pieheight=val_cond;
              }
              else{
              color="";
              }
            %>
            <span class="txtLinkdashboard" id="<%=dflt_val%>_TAB" style="<%=color%>"  onclick="showHideDivDashboard('boardgrid','<%=dflt_val%>',this,'_TAB')"> <%=fld_ttl%></span>
            <%}}%>
</td>
</tr>
</table>
</html:form>
<div>
<html:form action="dashboard/screen.do?method=filtergrpcomp" method="post" onsubmit="" >
<table class="boardgridPrp" id="boardgridPrp">
<tr>
<td align="center" nowrap="nowrap">
<html:select property="value(filterlprp)" name="userrightsform"  style="width:100px" styleId="filterlprp" onchange="displayfilterQuick();"  >
<%for (int i=0;i<vwPrpLst.size();i++){
    String prpflt = (String)vwPrpLst.get(i);
    if(prpDspBlocked.contains(prpflt)){
       }else{
     String hdr = util.nvl((String)mprp.get(prpflt));
    String prpDsc = util.nvl((String)mprp.get(prpflt+"D"));
    if(hdr == null) {
        hdr = prpflt;
       }
       if(i==0){
              color="color:#ffffff";
              }
              else{
              color="";
              }
       %>
       <html:option value="<%=prpflt%>" ><%=prpDsc%></html:option> 
<%}}%>
</html:select>
</td>
 <td>
 <%for (int i=0;i<vwPrpLst.size();i++){
    String lprp = (String)vwPrpLst.get(i);
    if(prpDspBlocked.contains(lprp)){
       }else{
       String disp="display:none";
       if(i==0)
       disp="";
       %>
       <div id="TD_DIV_<%=lprp%>" style="<%=disp%>">
       <%
           ArrayList prpPrt = (ArrayList)prp.get(lprp+"P");
           ArrayList  prpSrt = (ArrayList)prp.get(lprp+"S");
            ArrayList prpVal = (ArrayList)prp.get(lprp+"V");
            String fld1 = lprp+"_1";
            String fld2 = lprp+"_2";

             String prpFld1 = "value(" + fld1 + ")" ;
            String prpFld2 = "value(" + fld2 + ")" ; 
          
              String onChg1 = "setFrTo('"+ fld1 + "','" + fld2 + "');setFrToCheck('"+ fld1 + "','" + fld2 + "','"+lprp+"')\"";
              String onChg2 = "setFrTo('"+ fld1 + "','" + fld2 + "');setFrToCheck('"+ fld1 + "','" + fld2 + "','"+lprp+"')\"";
                          String mutiTextId = "MTXT_"+lprp;
             String mutiText = "value("+ mutiTextId +")";
             String loadStrL = "ALL";
             String dfltVal = "0" ;
            String dfltDsp = "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;All&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
       %>
             <div  class="multiplePrpdiv" id="<%=lprp%>" align="center" style="display:none; padding-top:2px; margin-top:20px; z-index:1">
             <table cellpadding="0"  class="multipleBg" cellspacing="0">
             <tr><td>
             <table>
             <tr>
             <td> <input type="checkbox" name="selectALL" id="SALL_<%=lprp%>" onclick="CheckAlllprp('<%=lprp%>',this)" > Select All &nbsp; </td>
            
             <td>
    <html:text property="<%=mutiText%>" value="<%=loadStrL%>" name="userrightsform"  style="width:100px" styleId="<%=mutiTextId%>" styleClass="txtStyle" />
    </td>
            <td><img src="../images/cross.png" border="0" onclick="Hide('<%=lprp%>')" /> </td>
            </tr>
            </table>
            </td></tr>
            <tr><td><hr style="color:White;"></td></tr>
            <tr>
            <td>
             <%
             int listCnt=0;
             for(int m=0;m<prpSrt.size();m++){
                String isSelected = "";
                String pPrtl = (String)prpPrt.get(m);
                String pSrtl = (String)prpSrt.get(m);
                String vall = (String)prpVal.get(m);
                String chFldNme = "value("+lprp+"_"+vall+")" ;
                String onclick= "checkPrp(this, 'MTXT_"+lprp+"','"+lprp+"')";
                String checId = lprp+"_"+pSrtl;
               if(m==0){
                %>
                <table>
                <tr>
                <%}
                if(listCnt==7){%>
                </tr><tr>
                <% listCnt=0;} 
                listCnt++;
//                String fld = util.nvl((String)favMap.get(lprp+"_"+pSrtl));
//                 if(fld.equals(pSrtl+"_to_"+pSrtl)){
//                   isSelected = "checked=\"checked\"";
//                   loadStrL = loadStrL+" , "+pPrtl;
//                   }
             %>
             <td align="center"><html:checkbox  property="<%=chFldNme%>" name="userrightsform" styleId="<%=checId%>"  onclick="<%=onclick%>" value="<%=vall%>"/></td>
             <td align="left"><span style="margin-left:10px;"><%=pPrtl%></span></td>
             <%}%>
             </tr></table>
            </td>
            </tr>
            </table>
             <input type="hidden" id="HID_<%=lprp%>" value="<%=loadStrL%>" />
              
             </div>
             <table cellpadding="0" cellspacing="0">
              <tr><td>  
           <td>  &nbsp; 
             
           <html:select  property="<%=prpFld1%>" name="userrightsform"  style="width:100px" onchange="<%=onChg1%>" styleId="<%=fld1%>"  > 
     
            <html:option value="<%=dfltVal%>"><%=dfltDsp%></html:option>
            <%
            for(int j=0; j < prpSrt.size(); j++) {
                String pPrt = (String)prpPrt.get(j);
                String pSrt = (String)prpSrt.get(j);
            %>
              <html:option value="<%=pSrt%>" ><%=pPrt%></html:option> 
            <%}%>
              </html:select>
              </td><td >
            &nbsp;  &nbsp;
             
           <html:select property="<%=prpFld2%>" name="userrightsform"  style="width:100px" onchange="<%=onChg2%>" styleId="<%=fld2%>"  > 
     
            <html:option value="<%=dfltVal%>"><%=dfltDsp%></html:option>
            <%
            for(int j=0; j < prpSrt.size(); j++) {
                String pPrt = (String)prpPrt.get(j);
                String pSrt = (String)prpSrt.get(j);
            %>
              <html:option value="<%=pSrt%>" ><%=pPrt%></html:option> 
            <%}%>
              </html:select>   
             </td>
             <td>&nbsp;&nbsp;</td>
              <td><img src="../images/plus.jpg" id="IMGD_<%=lprp%>" class="img" onclick="DisplayDiv('<%=lprp%>')" style="display:block" border="0"/></td><td><img src="../images/minus.jpg" id="IMGU_<%=lprp%>" class="img" onclick="Hide('<%=lprp%>')" style="display:none" border="0"/></td>
              </tr></table>
              </div>
             <%}}%>
 </td>
 <td><html:submit property="submit" value="View / Filter" styleClass="submit" /></td>
 <td><html:submit property="value(Reset)" value="Reset" styleClass="submit" /></td>
</tr>
<tr>
<td colspan="3">
<span class="txtLinkdashboard" id="QTY_QTYVLUTAB" style=""  onclick="refreshboardgridPrpQTYVLU('boardgridPrp','QTY',this,'_QTYVLUTAB')"> QTY</span>
<span class="txtLinkdashboard" id="VLU_QTYVLUTAB" style="color:#ffffff"  onclick="refreshboardgridPrpQTYVLU('boardgridPrp','VLU',this,'_QTYVLUTAB')"> VLU</span>
|
<span class="txtLinkdashboard" id="PRP_PRPGRPTAB" style="color:#ffffff"  onclick="refreshboardgridPrpPRPGRP('boardgridPrp','PRP',this,'_PRPGRPTAB')"> PRP Wise</span>
<span class="txtLinkdashboard" id="GRP_PRPGRPTAB" style=""  onclick="refreshboardgridPrpPRPGRP('boardgridPrp','GRP',this,'_PRPGRPTAB')"> GRP Wise</span>
</td>
 <tr>
<td style="display:none">
<select name="multiPrp" id="multiPrp">
<%
for(int s=0;s<vwPrpLst.size();s++){
   String lprp = (String)vwPrpLst.get(s);
      if(prpDspBlocked.contains(lprp)){
       }else{
%>
<option value="<%=lprp%>"></option>
<%}}%>
</select>
</td>
 </tr>
</table>
</html:form>
</div>
<div>
<%
                    barGrp="";
                    for (int j=0;j<vWPrpListAll.size();j++){
                    String vwlprp=(String)vWPrpListAll.get(j);
                    String lprpTyp = util.nvl((String)mprp.get(vwlprp+"T"));
                    vwlprp=vwlprp.replaceAll("_", "");
                    if(lprpTyp.equals("N")){
                    if(!barGrp.equals(""))
                    barGrp=barGrp+"_"+vwlprp;
                    else
                    barGrp=vwlprp;
                    }
                    }
%>
  <input type="hidden" id="barGrp" value="<%=barGrp%>">
  <div style="margin:0px; padding:0px;">
  <input type="hidden" id="GROUP_HIDD" value="GROUP">
  <input type="hidden" id="GROUP_TTL" value="<%=filterDsc%>">
  <div id="GROUP" style="width: 50%; height: 350px; float:left; margin:0px; padding:0px;">
  </div>
<%
url="screen.do?method=grpcomparasionBarChart&graphby=VLU";%>
<script type="text/javascript">
<!--
$(window).bind("load", function() {
callcreatedoubleBarGraph('<%=url%>','Group Wise','50','200');
});
 -->
</script>  
  </div>
  <div>
  <%
  Collections.sort(agegrplst);
    url="screen.do?method=grpcomparasionagegrpBarChart&graphby=VLU";
    barGrp="";
                    for (int j=0;j<agegrplst.size();j++){
                    String vwlprp=(String)agegrplst.get(j);
                    vwlprp=vwlprp.replaceAll("_", "");
                    if(!barGrp.equals(""))
                    barGrp=barGrp+"_"+vwlprp;
                    else
                    barGrp=vwlprp;
                    }%>
  <input type="hidden" id="diffgrp" value="<%=barGrp%>">
  <div style="margin:0px; padding:0px;">
  <input type="hidden" id="AGEGRP_MULTIAGE" value="AGEGRP">
  <input type="hidden" id="AGEGRP_TTL" value="<%=filterDsc%>">
  <div id="AGEGRP" style="width: 50%; height: 400px; float:left; margin:0px; padding:0px;">
  </div>
  </div>
<script type="text/javascript">
<!--
$(window).bind("load", function() {
callcreatedoubleBarGraphonpagemultiplepage('<%=url%>','Age Group Wise','50','362','diffgrp');
});
 -->
</script>  
  </div>
  </div>

<%
    barGrp=grpList.toString();
    barGrp = barGrp.replaceAll("\\[", "");
    barGrp = barGrp.replaceAll("\\]", "");
    barGrp = barGrp.replaceAll("\\,", "_").trim();
    barGrp = barGrp.replaceAll(" ", "");
  url="screen.do?method=grpcomparasionBarChartPrpWise&filterlprp="+vwPrpLst.get(0);
  String style="width: 100%; height: 350px; float:left;";
  String key="TEST";
  String styleId=key+"_HIDULTI";%>
<div>
  <div>
  <input type="hidden" id="<%=styleId%>" value="<%=key%>">
  <input type="hidden" id="barGrpmultionPg" value="<%=barGrp%>">
  <div id="<%=key%>" style="<%=style%>"></div>
  <input type="hidden" id="TEST_TTL" value="<%=filterDsc%>">
  </div> 
</div>
<script type="text/javascript">
<!--
$(window).bind("load", function() {
callcreateMultipleBarGraphmultionPg('<%=url%>','<%=filterDsc%> - <%=util.nvl((String)mprp.get(vwPrpLst.get(0)+"D"))%> Wise','50','362');
});
 -->
</script> 
<div>
<%

  url="screen.do?method=grpcomparasionPiePrpWise&graphby=VLU&filterlprp="+vwPrpLst.get(0);
  style="width: 50%; height: 362px; float:left;";
  String title=" Chart";
  pieheight="600";
  for(int i=0;i< grpList.size();i++){
  String stt=(String)grpList.get(i);
  styleId=stt+"_DIV";
  style="width: 550px; height: "+pieheight+"px; float:left;";

  %>
  <div style="margin-right:30px;">
  <input type="hidden" id="<%=styleId%>" value="<%=stt%>">
  <div id="<%=stt%>" style="<%=style%>">
  
  </div>
  </div>
  <%}%>
</div>
<script type="text/javascript">
 <!--
$(window).bind("load", function() {
callcommonpieChart('<%=url%>','<%=title%> Vlu Wise','75','<%=pieheight%>');
});
 -->
</script>
<%            pageList= ((ArrayList)pageDtl.get("DIFF_PRP") == null)?new ArrayList():(ArrayList)pageDtl.get("DIFF_PRP");
            if(pageList!=null && pageList.size() >0){
            for(int j=0;j<pageList.size();j++){
                pageDtlMap=(HashMap)pageList.get(j);
                dflt_val=util.nvl((String)pageDtlMap.get("dflt_val"));
            %>
<input type="hidden" id="diff_prp" value="<%=dflt_val%>">
<div id="griddatadiff" style="float:left">
</div>
<script type="text/javascript">
 <!--
$(window).bind("load", function() {
grpcomparasionDataGrid();
});
 -->
</script>
<%}}%>
 <%@include file="/calendar.jsp"%>
</body>
</html>
  
  