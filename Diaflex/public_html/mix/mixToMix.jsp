<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page errorPage="../error_page.jsp" %>
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.ResultSet, java.util.ArrayList,java.util.Collections,java.util.ArrayList,java.util.HashMap,java.text.SimpleDateFormat, java.util.Calendar"%>

<jsp:useBean id="util" class="ft.com.JspUtil" scope="page" />
<jsp:useBean id="info" class="ft.com.InfoMgr" scope="session"/>

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

  <title>Mix To Mix</title>
 
  </head>
        <%String logId=String.valueOf(info.getLogId());
        String onfocus="cook('"+logId+"')";
        %>
 <body onfocus="<%=onfocus%>" onload="MM_preloadImages('images/faunalogo.png')"  onkeypress="return disableEnterKey(event);"  topmargin="0" leftmargin="0" rightmargin="0" bottommargin="0">
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
  <img src="../images/line.jpg" border="0" /></td><td valign="middle"> <span class="pgHed">Mix To Mix</span> </td>
  <td id="loading"></td>
</tr></table>
  </td>
  </tr>
  <%
  
  ArrayList mixsttList = (ArrayList)request.getAttribute("mixtomixsttList");
  String validate="return validatemixtomixAllow();";
  %>
  <html:form action="/mix/mixtomix.do?method=save"   method="POST">
  <html:hidden property="value(flg)" styleId="flg"   name="mixToMixForm" />
  <tr><td valign="top" class="hedPg">
  <%if(mixsttList!=null && mixsttList.size()>0){
  validate="";%>
  Status :<html:select property="value(stt)" styleId="status" onchange="displayMemoFD(this)">  
  <html:option value="0" >----Status---</html:option> 
  <%for(int i=0;i<mixsttList.size();i++){
  ArrayList data=(ArrayList)mixsttList.get(i);
  String val=(String)data.get(0);
  String dsc=(String)data.get(1);
  %>
  <html:option value="<%=val%>" ><%=dsc%></html:option> 
  <%}%>
  </html:select>
  <%}else{%>
  <html:hidden property="value(stt)" styleId="status" value=""  name="mixToMixForm" />
  <%}%>
  </td></tr>
<tr><td valign="top" class="hedPg">
<table class="grid1">
    <tr>
    <th>Sr</th><th>Memo</th>
    <th colspan="4">From</th><th colspan="5">To</th>
    </tr>
    <tr><td></td><td></td>
    <td align="center">From</td><td align="center">Cts</td><td align="center">Tranfer</td><td align="center">Balance</td>
    <td align="center">To</td><td align="center">Cts</td><td align="center">Tranfer</td><td align="center">Total</td><td align="center">RTE</td>
    </tr>
    <%
    for(int i=1; i <=5; i++) {
    String ctsIdf = "CTSF_"+i;
    String trfIdf = "TRFF_"+i;
    String balIdf = "BAL_"+i;
    String ctsIdt = "CTST_"+i;
    String trfIdt = "TRFT_"+i;
    String ttlIdt = "TTL_"+i;
    String rteIdt = "RTET_"+i;
    String memoIdf = "MEMOF_"+i;

    String ctsf = "value(CTSF_"+i+")";
    String trff = "value(TRFF_"+i+")";
    String balf = "value(BAL_"+i+")";
    String ctst = "value(CTST_"+i+")";
    String trft = "value(TRFT_"+i+")";
    String ttlt = "value(TTL_"+i+")";
    String rtet = "value(RTET_"+i+")";
    String memof = "value(MEMOF_"+i+")";

    String nmef= "nmeIDF_"+i;
    String nmeIDf = "value(nmeIDf_"+i+")";
    String nmet= "nmeIDT_"+i;
    String nmeIDt = "value(nmeIDt_"+i+")";
    String onChangeTRFF = "mixtomixTrf("+i+")";
    
    String idnf= "IDNF_"+i;
    String idnIDf = "value(IDNF_"+i+")";
    String idnt= "IDNT_"+i;
    String idnIDt = "value(IDNT_"+i+")";
    String getMixIdn = "value(idnt_"+i+")";
    String getMixIdnf = "getMixIdn('"+i+"','F', event)";
    String getMixIdnt = "getMixIdn('"+i+"','T', event);";
    String onBlour = "mixtomixTrt('"+ i +"')";
    %>
    <tr><td><%=i%></td>
    <td><html:text property="<%=memof%>" styleId="<%=memoIdf%>"  disabled="true" size="6" name="mixToMixForm" /></td>
    <td>
    <html:hidden property="<%=idnIDf%>" styleId="<%=idnf%>"/>
        <input type="text" id="nmef_<%=i%>" name="nmef_<%=i%>" autocomplete="off" class="sugBox"
      onKeyUp="doCompletion('nmef_<%=i%>', 'nmePopf_<%=i%>', 'mixtomix.do?method=mixShapeszClr', event)"
      onKeyDown="setDown('nmePopf_<%=i%>', '<%=nmef%>', 'nmef_<%=i%>',event);" 
      />
       <span onclick="<%=getMixIdnf%>" title="Click Here To get Cts"><u>CTS</u></span>
    <html:hidden property="<%=nmeIDf%>" styleId="<%=nmef%>"/>
      <div style="position: absolute;">
          <select id="nmePopf_<%=i%>" name="nmePopf_<%=i%>"
            style="display:none;300px;" 
            class="sugBoxDiv" onkeypress="setEnterKey(event)"
            onKeyDown="setDown('nmePopf_<%=i%>', '<%=nmef%>', 'nmef_<%=i%>',event);" 
            onDblClick="setVal('nmePopf_<%=i%>', '<%=nmef%>', 'nmef_<%=i%>', event);hideObj('nmePopf_<%=i%>');<%=getMixIdnf%>" 
            onClick="setVal('nmePopf_<%=i%>', '<%=nmef%>', 'nmef_<%=i%>', event);" 
            size="10">
          </select>
    </div>
    </td>
    <td><html:text property="<%=ctsf%>" styleId="<%=ctsIdf%>"  size="12" name="mixToMixForm" readonly="true" /></td>
    <td><html:text property="<%=trff%>" styleId="<%=trfIdf%>" size="12" name="mixToMixForm"  onchange="<%=onChangeTRFF%>"  /></td>
    <td><html:text property="<%=balf%>" styleId="<%=balIdf%>" size="12" name="mixToMixForm" readonly="true" /></td>
    <td>
    <html:hidden property="<%=idnIDt%>" styleId="<%=idnt%>"/>
        <input type="text" id="nmet_<%=i%>" name="nmet_<%=i%>" onclick="mixtomixAllow('<%=i%>')" autocomplete="off" class="sugBox"
      onKeyUp="doCompletion('nmet_<%=i%>', 'nmePopt_<%=i%>', 'mixtomix.do?method=mixShapeszClr', event)"
      onKeyDown="setDown('nmePopt_<%=i%>', '<%=nmet%>', 'nmet_<%=i%>',event);" 
      />
      <span onclick="<%=getMixIdnt%>" title="Click Here To get Cts"><u>CTS</u></span>
    <html:hidden property="<%=nmeIDt%>" styleId="<%=nmet%>"/>
      <div style="position: absolute;">
          <select id="nmePopt_<%=i%>" name="nmePopt_<%=i%>"
            style="display:none;300px;" 
            class="sugBoxDiv" 
            onKeyDown="setDown('nmePopt_<%=i%>', '<%=nmet%>', 'nmet_<%=i%>',event);" 
            onDblClick="setVal('nmePopt_<%=i%>', '<%=nmet%>', 'nmet_<%=i%>', event);hideObj('nmePopt_<%=i%>');<%=getMixIdnt%>" 
            onClick="setVal('nmePopt_<%=i%>', '<%=nmet%>', 'nmet_<%=i%>', event);" 
            size="10">
          </select>
    </div>
    </td>
    <td><html:text property="<%=ctst%>" styleId="<%=ctsIdt%>" onfocus="<%=onBlour%>" size="12" name="mixToMixForm" readonly="true" /></td>
    <td><html:text property="<%=trft%>" styleId="<%=trfIdt%>" size="12" name="mixToMixForm" readonly="true"  /></td>
    <td><html:text property="<%=ttlt%>" styleId="<%=ttlIdt%>" size="12" name="mixToMixForm" readonly="true"  /></td>
    <td><html:text property="<%=rtet%>" styleId="<%=rteIdt%>" size="12" name="mixToMixForm" /></td>
    </tr>
    <%}%>
    </table>

</td>
</tr>
<tr><td valign="top" class="hedPg"><html:submit property="submit" value="Save Changes" styleClass="submit" onclick="<%=validate%>"/></td></tr>
</html:form>
  <tr><td>
<jsp:include page="/footer.jsp" />
</td></tr>
  </table>  
   <%@include file="/calendar.jsp"%>
  </body>
</html>