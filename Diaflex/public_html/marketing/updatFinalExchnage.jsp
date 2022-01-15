<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
 
<%@ page errorPage="../error_page.jsp" %>
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import="java.util.ArrayList,java.util.HashMap,java.sql.ResultSet,ft.com.*, java.util.Date, java.util.ArrayList, ft.com.dao.*"%>
<jsp:useBean id="util" class="ft.com.JspUtil" scope="page" />
<jsp:useBean id="info" class="ft.com.InfoMgr" scope="session" />
<%@taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>
<%@taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>Exchange Rate</title>
    <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/bse.css?v=<%=info.getJsVersion()%>"/>
      <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/style.css?v=<%=info.getJsVersion()%>"/>
      <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/memoScript.js?v=<%=info.getJsVersion()%> " > </script>
        <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/bse.js?v=<%=info.getJsVersion()%> " > </script>
         <script type="text/javascript" src="<%=info.getReqUrl()%>/PopScripts/popup.js?v=<%=info.getJsVersion()%> " > </script>
   <script src="<%=info.getReqUrl()%>/PopScripts/jquery-1.2.6.min.js" type="text/javascript"></script>
     <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/general.css?v=<%=info.getJsVersion()%>"/>
     <script src="<%=info.getReqUrl()%>/scripts/assortScript.js?v=<%=info.getJsVersion()%>" type="text/javascript"></script>
    <script language="javaScript" type="text/javascript" src="<%=info.getReqUrl()%>/calendar/calendar.js"></script>
    <link href="<%=info.getReqUrl()%>/calendar/calendar.css" rel="stylesheet" type="text/css" />
        </head>
        <%String logId=String.valueOf(info.getLogId());
        String onfocus="cook('"+logId+"')";
        %>
 <body onfocus="<%=onfocus%>" onload="MM_preloadImages('images/faunalogo.png')" topmargin="0" leftmargin="0" rightmargin="0" bottommargin="0">
<input type="hidden" id="reqUrl" name="reqUrl" value="<%=info.getReqUrl()%>"/>
<div id="backgroundPopup"></div>
<div id="popupContactASSFNL">
<table class="popUpTb">
 <tr>
 <td height="5%" id="close">&nbsp;&nbsp;&nbsp;<html:button property="value(close)" styleClass="submit"  onclick="MKEmptyPopup('SC');disablePopupASSFNL();" value="Close"  /> </td>
 <td height="5%" id="closereload" style="display:none">&nbsp;&nbsp;&nbsp;<html:button property="value(close)" styleClass="submit"  onclick="MKEmptyPopup('SC');disablePopupASSFNL();reportUploadclose('STK')" value="Close"  /> </td>
 </tr>
<tr><td valign="top" height="95%">
<iframe name="SC" class="frameStyle"   align="middle" frameborder="0">

</iframe></td></tr>
</table>
</div>
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
  <img src="../images/line.jpg" border="0" /></td><td valign="middle"> <span class="pgHed">Update Final Exchange Rate</span> </td>
</tr></table>
  </td>
  </tr>          
     <% 
        ArrayList memoLst= (ArrayList)session.getAttribute("MemoLst");
        String view = (String)request.getAttribute("view");
        String  msg =util.nvl((String)request.getAttribute("msg"));
        if(msg!=null && msg!=""){
     %>
    <tr><td class="hedPg" valign="top" style="color:red"><%=msg%></td></tr> 
     <%}%>
  <tr><td valign="top" class="tdLayout">
        <table class="grid1">
        <html:form action="/marketing/UpdateFinalExchangeRate.do?method=Fetch"  method="POST">
        <tr><th colspan="2">Memo Search </th></tr>
            <tr>
                <td>Memo Id</td><td><html:textarea property="value(memoIdn)"/></td>
            </tr>
            <tr><td colspan="2" align="center">OR</td></tr>
             <tr>
            <td>Date :</td><td><table><tr>
           <td>From</td><td>
           <div class="float"> <html:text property="value(frmDte)"  styleId="frmDte" size="10" /></div>
           <div class="float"> <a href="#"  onClick="setYears(1900, <%=info.getCurrentYear()%>);showCalender(this, 'frmDte');">
          <img src="<%=info.getReqUrl()%>/images/calender.png"></a></div>
          </td>
         <td>To</td><td>
          <div class="float"> <html:text property="value(toDte)"  styleId="toDte" size="10"  /></div>
         <div class="float"> <a href="#"  onClick="setYears(1900, <%=info.getCurrentYear()%>);showCalender(this, 'toDte');">
          <img src="<%=info.getReqUrl()%>/images/calender.png"></a></div>
        </td>
        </tr></table>
            </td>
            </tr>
            <tr>
            <td align="center" colspan="4"> <html:submit property="submit" value="Fetch " styleClass="submit" /></td>
            </tr>
            </html:form>
            </table>
            </td></tr>
            <tr><td class="hedPg" valign="top"></td></tr>
             <tr><td class="hedPg" valign="top">              
             <table class="grid1" id="dataTable">
             
           <%  
           if(view!=null){
           if(memoLst!=null && memoLst.size()>0){ %>
           <html:form action="/marketing/UpdateFinalExchangeRate.do?method=update" onsubmit="return validate_prc('CHK_')"  method="POST">
           <tr> <td colspan="10" align="left">&nbsp&nbsp <html:submit property="submit" value="save" styleClass="submit" />&nbsp&nbsp</td>
            </tr>
           <tr><th><input type="checkbox" name="checkAll" id="checkAll" onclick="CheckBOXALLForm('1',this,'CHK_');" /></th>
          <th nowrap="nowrap">Memo Id</th> <th nowrap="nowrap">Type</th> <th nowrap="nowrap">Qty</th> <th nowrap="nowrap">Cts</th> <th nowrap="nowrap">Value</th> <th nowrap="nowrap">Date</th>
          <th nowrap="nowrap">Exchange Rate</th>
          <th nowrap="nowrap">Final Exchange Rate</th>
          <th nowrap="nowrap"><html:text property="value(COMMFNLEXH)" styleId="COMMFNLEXH" onchange="setcommonValLst(this , 'FNLEXH_','text')" size="8" name="UpdateFinalExChangeRateForm" value="" /></th>
         </tr>
        <%for(int j=0; j<memoLst.size(); j++){ 
        HashMap Dtl=(HashMap)memoLst.get(j); 
        String memoid=util.nvl((String)Dtl.get("MemoId"));
         String checkFldId = "CHK_"+memoid;
         String checkFldVal = "value("+memoid+")";
         String fnlexhFld = "value(FNLEXH_"+memoid+")";
         String fnlexhFldId = "FNLEXH_"+memoid;
        %>
        <tr>
        <td><html:checkbox property="<%=checkFldVal%>" styleId="<%=checkFldId%>" name="UpdateFinalExChangeRateForm" value="yes" /></td>
   
       <td><%=util.nvl((String)Dtl.get("MemoId"))%></td><td><%=util.nvl((String)Dtl.get("Type"))%></td><td><%=util.nvl((String)Dtl.get("Qty"))%></td>
       <td><%=util.nvl((String)Dtl.get("Cts"))%></td><td><%=util.nvl((String)Dtl.get("Value"))%></td><td><%=util.nvl((String)Dtl.get("Date"))%></td>
       <td><%=util.nvl((String)Dtl.get("exh_rte"))%></td>
       <td><%=util.nvl((String)Dtl.get("fnl_xrt"))%></td>
       <td><html:text property="<%=fnlexhFld%>" styleId="<%=fnlexhFldId%>" size="8" name="UpdateFinalExChangeRateForm" value="" /></td>
        
       </tr>
       <%}%>
       </html:form>
       <%}else{%>
    <tr><td>Sorry no result found </td></tr>
    <%}}%>
      </table></td>
        </tr>        
  <tr>
    <td><jsp:include page="/footer.jsp" /> </td>
    </tr>
  </table>
  <%@include file="../calendar.jsp"%>
 </body>
</html>
        