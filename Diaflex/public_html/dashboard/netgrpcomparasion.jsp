<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page errorPage="../error_page.jsp" %>
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import="java.util.ArrayList,java.util.ArrayList,java.util.HashMap, java.sql.*, java.util.Enumeration, java.util.logging.Level,java.util.Calendar"%>
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
    String fld_nme="",fld_ttl="",val_cond="",dflt_val="",fld_typ="",form_nme="",flg1="",lov_qry="";
    HashMap mprp = info.getMprp();
    ArrayList  vwPrpLst = (ArrayList)session.getAttribute("DASH_GRP_COMP");
    ArrayList  grpList = (ArrayList)session.getAttribute("grpList");
    String  days = util.nvl((String)request.getAttribute("days"),"0");
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
<html:form action="dashboard/screen.do?method=netfetchgrpcomp" method="post" onsubmit="" >
<html:hidden property="value(BTN_DAYS)" styleId="BTN_DAYS" />
<html:hidden property="value(PIE_TYP)" styleId="PIE_TYP" value="VLU" />
<html:hidden property="value(RPT_PRP)" styleId="RPT_PRP" value="<%=firstprp%>" />
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
            <span class="txtLinkdashboard" id="<%=dflt_val%>_TAB" style="<%=color%>"  onclick="netshowHideDivDashboard('boardgrid','<%=dflt_val%>',this,'_TAB')"> <%=fld_ttl%></span>
            <%}}%>
</td>
</tr>
</table>
</html:form>
<div>
<table class="boardgridPrp" id="boardgridPrp">
<tr>
<td align="center" nowrap="nowrap">
<%for (int i=0;i<vwPrpLst.size();i++){
    String prp = (String)vwPrpLst.get(i);
    if(prpDspBlocked.contains(prp)){
       }else{
     String hdr = util.nvl((String)mprp.get(prp));
    String prpDsc = util.nvl((String)mprp.get(prp+"D"));
    if(hdr == null) {
        hdr = prp;
       }
       if(i==0){
              color="color:#ffffff";
              }
              else{
              color="";
              }
       %>
<span class="txtLinkdashboard" id="<%=prp%>_TAB" style="<%=color%>"  onclick="netrefreshboardgridPrp('boardgridPrp','<%=prp%>',this,'_TAB')"> <%=prpDsc%></span>
<%}}%>
</td>
<td>
<span class="txtLinkdashboard" id="QTY_QTYVLUTAB" style=""  onclick="netrefreshboardgridPrpQTYVLU('boardgridPrp','QTY',this,'_QTYVLUTAB')"> QTY</span>
<span class="txtLinkdashboard" id="VLU_QTYVLUTAB" style="color:#ffffff"  onclick="netrefreshboardgridPrpQTYVLU('boardgridPrp','VLU',this,'_QTYVLUTAB')"> VLU</span>
</td>
</tr>
</table>
</div>
<div>
<%

  String url="screen.do?method=netgrpcomparasionpie&pietyp=VLU&lprp="+vwPrpLst.get(0);
  String style="width: 50%; height: 362px; float:left;";
  String title=" Chart";
  for(int i=0;i< grpList.size();i++){
  String stt=(String)grpList.get(i);
  String styleId=stt+"_DIV";
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
callcommonpieChart('<%=url%>','<%=title%>','75','<%=pieheight%>');
});
 -->
</script>
<div>
<%
String barGrp=grpList.toString();
    barGrp = barGrp.replaceAll("\\[", "");
    barGrp = barGrp.replaceAll("\\]", "");
    barGrp = barGrp.replaceAll("\\,", "_").trim();
    barGrp = barGrp.replaceAll(" ", "");
  url="screen.do?method=netgrpcomparasionbargraph&lprp="+vwPrpLst.get(0);
  style="width: 100%; height: 362px; float:left;";
  String key="TEST";
  String styleId=key+"_HIDD";%>
  <div>
  <input type="hidden" id="<%=styleId%>" value="<%=key%>">
  <input type="hidden" id="barGrp" value="<%=barGrp%>">
  <div id="<%=key%>" style="<%=style%>"></div>
  </div>
<script type="text/javascript">
<!--
$(window).bind("load", function() {
callcreateMultipleBarGraph('<%=url%>','Group Wise','50','362');
});
 -->
</script>  
</div>

  <%@include file="/calendar.jsp"%>
</body>
</html>
  
  