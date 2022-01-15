<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import="java.util.ArrayList,java.util.HashMap,java.sql.ResultSet,ft.com.*, java.util.Date, java.util.ArrayList, ft.com.dao.*"%>
<jsp:useBean id="info" class="ft.com.InfoMgr" scope="session" />
<jsp:useBean id="util" class="ft.com.JspUtil" scope="page" />
<%@taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>
<%@taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>Mix Stock Tally Return</title>
      <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/bse.css?v=<%=info.getJsVersion()%>"/>
      <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/style.css?v=<%=info.getJsVersion()%>"/>
      <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/general.css?v=<%=info.getJsVersion()%>"/>
      <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/bse.js?v=<%=info.getJsVersion()%> " > </script>
     <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/Validation.js?v=<%=info.getJsVersion()%> " > </script>
       <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/newCalScript.js?v=<%=info.getJsVersion()%> " > </script>
        <script src="<%=info.getReqUrl()%>/PopScripts/jquery-1.2.6.min.js" type="text/javascript"></script>

      <script language="javaScript" type="text/javascript" src="<%=info.getReqUrl()%>/calendar/calendar.js"></script>
      <link href="<%=info.getReqUrl()%>/calendar/calendar.css" rel="stylesheet" type="text/css" />
     
  </head>
<%  
   int accessidn=Integer.parseInt(util.nvl((String)request.getAttribute("accessidn"),"0"));
    String logId=String.valueOf(info.getLogId());
    String onfocus="cook('"+logId+"')";
       ArrayList pktDtlLst = (ArrayList)request.getAttribute("PKTDTL");
%>
 <body onfocus="<%=onfocus%>" onload="MM_preloadImages('images/faunalogo.png')" topmargin="0" leftmargin="0" rightmargin="0" bottommargin="0">

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
  <img src="../images/line.jpg" border="0" /></td><td valign="middle"> <span class="pgHed">Mix Stock Tally Return</span> </td>
</tr></table>
  </td>
  </tr>
  <% String msg = util.nvl((String)request.getAttribute("msg"));
  if(!msg.equals("")){
  %>
   <tr><td valign="top" class="tdLayout"><span class="redLabel"><%=msg%></span></td></tr>
  <%}%>
  <tr><td valign="top" class="tdLayout">
 <html:form action="/mixAkt/mixStockTally.do?method=loadRtn" method="POST" >
<table cellpadding="2" cellspacing="2">
  <tr><td>Seq No : </td><td>
   <html:select property="value(SEQNO)" name="mixStockTallyForm" >
        <html:optionsCollection property="seqList" label="dsc" value="nme" />
  </html:select>
  </td></tr>
  <tr><td colspan="2"> <jsp:include page="/genericSrch.jsp"/> </td></tr>
  <tr> <td> <html:submit property="value(view)" value="View" styleClass="submit"/></td></tr>
  </table>
  </html:form>
  </td></tr>
  
   <tr><td valign="top" class="tdLayout">
       <html:form action="/mixAkt/mixStockTally.do?method=save" method="POST" onsubmit="return sumbitFormConfirm('CHK_','1','Do you want to save changes','Please select Checkbox ','checkbox')">

   <% 
      ArrayList asViewPrp = (ArrayList)session.getAttribute("MIX_TLLY_VW");
    
   if(pktDtlLst!=null && pktDtlLst.size()>0){
   String ttlRtnQty = (String)request.getAttribute("ttlRtnQty");
   String ttlIssQty = (String)request.getAttribute("ttlIssQty");
   String ttlRtnCts = (String)request.getAttribute("ttlRtnCts");
   String ttlIssCts = (String)request.getAttribute("ttlIssCts");
   %>
   <table cellpadding="2" cellspacing="2">
<tr><td> <html:submit property="value(return)" value="Return" styleClass="submit"/>
 &nbsp;&nbsp;<B>Issue :-</b> Qty &nbsp;:&nbsp;<%=ttlIssQty%> &nbsp;&nbsp;Cts&nbsp;: &nbsp;<%=ttlIssCts%>  &nbsp;&nbsp;<B> Return :-</b>
 Qty &nbsp;:&nbsp;<label id="ttlRtnQty"> <%=ttlRtnQty%></label>
 Cts &nbsp;:&nbsp; <label id="ttlRtnCts"><%=ttlRtnCts%></label>
     <input type="hidden" id="ttlRtnLbQty" name="ttlRtnLbQty" value="<%=ttlRtnQty%>"  />
      <input type="hidden" id="ttlRtnLbCts" name="ttlRtnLbCts" value="<%=ttlRtnCts%>"  />
      &nbsp;&nbsp;<B>Selected Return :-</b>
 Qty &nbsp;:&nbsp;<label id="sttlRtnQty"> 0</label>
 Cts &nbsp;:&nbsp; <label id="sttlRtnCts">0.0</label>
</td></tr>
<tr><td>
   <table class="grid1">
   <tr><th><input type="checkbox"  value="IS" id="IS" onclick="CheckBOXALLForm('1',this,'CHK_');stockTallyRtnCalPkt()" />&nbsp; </th>
    <th>Dsc </th>
    <th>Remark</th>
   <th>Cnt</th>
   <th>Iss Qty</th><th>Iss Cts</th>
   <th>Rtn Qty</th><th>Rtn Cts</th>
  </tr>
  <% for(int i=0;i<pktDtlLst.size();i++){
  HashMap pktDtl = (HashMap)pktDtlLst.get(i);
  String idn =util.nvl((String)pktDtl.get("IDN"));
  String boxId = util.nvl((String)pktDtl.get("BOX_ID"));
  String checkIdn = "CHK_"+idn;
  String boxIdn = "BOX_"+idn;
  String rmkFldId ="RMK_"+idn;
  String rtnQty = "RTNQTY_"+idn;
  String rtnCts = "RTNCTS_"+idn;
  String rtnLBQty = "RTNQTYLB_"+idn;
  String rtnLBCts = "RTNCTSLB_"+idn;
  String sttIdn = "STT_"+idn;
  String rtnQtyVal = "value(RTNQTY_"+idn+")";
  String rtnCtsVal = "value(RTNCTS_"+idn+")";
  String dscFld = "value(DSC_"+idn+")";
  String rmkFld = "value("+rmkFldId+")";
  String stt = util.nvl((String)pktDtl.get("STT"));
  String dsc=util.nvl((String)pktDtl.get("DSC"));
  String issCts =util.nvl((String)pktDtl.get("ISSCTS"));
  String issCtsId = "ISSCTS_"+idn;
  String issCtsFld = "value(ISSCTS_"+idn+")";
  String onChangeQty="MatchReturnQty('"+idn+"');stockTallyRtnCalPkt()";
 String onchange="MatchReturnCts('"+idn+"');stockTallyRtnCalPkt()";
    %>
   <tr><td> <%if(stt.equals("IS")){%>
   <input type="checkbox" id="<%=checkIdn%>" name="<%=checkIdn%>" onclick="stockTallyRtnCalPkt()" value="<%=idn%>"  />
      <input type="hidden" id="<%=boxIdn%>" name="<%=boxIdn%>" value="<%=boxId%>"  />
       <input type="hidden" id="<%=sttIdn%>" name="<%=sttIdn%>" value="<%=stt%>"  />
   <%}%>
   </td>
   <td><%=util.nvl((String)pktDtl.get("DSC"))%>
  
   <html:hidden property="<%=dscFld%>" value="<%=dsc%>" name="mixStockTallyForm" />
   </td>
      <td><html:text property="<%=rmkFld%>" styleId="<%=rmkFldId%>" name="mixStockTallyForm" styleClass="txtStyle" />  </td>

   <td><%=util.nvl((String)pktDtl.get("CNT"))%></td>
   <td><%=util.nvl((String)pktDtl.get("ISSQTY"))%></td><td><%=util.nvl((String)pktDtl.get("ISSCTS"))%>
    <html:hidden property="<%=issCtsFld%>" styleId="<%=issCtsId%>" value="<%=issCts%>"  name="mixStockTallyForm" />
   </td>
   <td>
   
   <%if(stt.equals("RT")){%>
   <%=util.nvl((String)pktDtl.get("RTNQTY"))%>
   <%}else{%>
  <html:text property="<%=rtnQtyVal%>" styleId="<%=rtnQty%>" size="8" onchange="<%=onChangeQty%>" name="mixStockTallyForm" styleClass="txtStyle" /> 
   <%}%>
   </td><td>
 
   <%if(stt.equals("RT")){%>
     <%=util.nvl((String)pktDtl.get("RTNCTS"))%>
   <%}else{%>
  <html:text property="<%=rtnCtsVal%>" styleId="<%=rtnCts%>" onchange="<%=onchange%>" size="8" name="mixStockTallyForm" styleClass="txtStyle" />  
  <%}%></td>
   </tr>
   <%}%>
   </table> </td></tr></table>
   <%}%>
  
   </html:form>
   </td></tr>
   <tr><td><jsp:include page="/footer.jsp" /> </td></tr>
    </table>

    </body>
      
</html>