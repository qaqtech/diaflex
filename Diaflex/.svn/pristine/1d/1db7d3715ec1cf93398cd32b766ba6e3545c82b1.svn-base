<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page errorPage="../error_page.jsp" %>
<%@ page contentType="text/html;charset=windows-1252"%>
<jsp:useBean id="info" class="ft.com.InfoMgr" scope="session" />
<jsp:useBean id="util" class="ft.com.JspUtil" scope="page" />
<%@ page import="java.util.ArrayList,java.util.HashMap,java.sql.ResultSet,ft.com.*, java.util.Date, java.util.ArrayList, ft.com.dao.*"%>
<%@taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>Lot Pricing</title>
    <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/bse.css?v=<%=info.getJsVersion()%>"/>
    <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/general.css?v=<%=info.getJsVersion()%>"/>
     <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/style.css?v=<%=info.getJsVersion()%>"/>
      <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/bse.js?v=<%=info.getJsVersion()%> " > </script>
       <script type="text/javascript" src="<%=info.getReqUrl()%>/PopScripts/popup.js?v=<%=info.getJsVersion()%> " > </script>
     <script src="<%=info.getReqUrl()%>/PopScripts/jquery-1.2.6.min.js" type="text/javascript"></script>
  </head>
        <%String logId=String.valueOf(info.getLogId());
        String onfocus="cook('"+logId+"')";
        %>
 <body onfocus="<%=onfocus%>" onload="MM_preloadImages('images/faunalogo.png')" topmargin="0" leftmargin="0" rightmargin="0" bottommargin="0">


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
  <img src="../images/line.jpg" border="0" /></td><td valign="middle"> <span class="pgHed">Lot Pricing</span> </td>
<%String SucessID = util.nvl((String)request.getAttribute("SucessID"));
if(!SucessID.equals("")){
SucessID=SucessID.replaceFirst(",","");
%>
  <td><span class="redLabel" style="font-size:12px;" >Lot Pricing OF Above Memo Id Sucess: <%=SucessID%></span></td>
<%}%>
</tr></table>
  </td>
  </tr>
   <tr>
   
   <td valign="top" class="tdLayout">
   <table><tr><td>
   <html:form action="mix/lotpri.do?method=fecth" method="post" >
  <table  class="grid1">
  <tr><th>Memo Search </th></tr>
  <tr><td valign="top">
   <table><tr>
   <td>Memo No:</td><td> <html:textarea property="value(memoLst)" rows="7" styleId="memoId"  cols="30" name="lotPricingForm"  /></td>
   </tr>
  </table></td>
</tr>
   <tr><td colspan="2" align="center">
   <html:submit property="value(pricing)" value="Pricing" styleClass="submit" onclick="return validatpriceChange()" />
   <html:submit property="value(pricing+make)" value="Pricing + Complete" styleClass="submit" onclick="return validatpriceChange()" />
   </td> </tr>
   </table>
   </html:form>
   </td></tr>
   <tr><td>&nbsp;</td></tr>
   <% String view = (String)request.getAttribute("view");
   if(view!=null){
   ArrayList memoLst = (ArrayList)session.getAttribute("memoLst");
   HashMap memoIdnLst = (HashMap)session.getAttribute("memoIdnLst");
   if(memoLst!=null && memoLst.size()>0){
   int sr = 0;
   %>
   <html:form action="mix/lotpri.do?method=save" method="post"  onsubmit="return validate_issue('CHK_' , 'count')">
   <tr><td><html:submit property="value(save)" value="Save" styleClass="submit" /></td></tr>
   <tr><td><table class="grid1" id="dataTable">
   <tr>
   <th><input type="checkbox" name="checkAll" id="checkAll" onclick="checkALL('CHK_','count')" /> </th>
   <th>Memo ID</th>
   <th>Surat Avg</th>
   <th>Polish Avg</th>
   <th>Account Avg</th>
   <th></th>
   </tr>
   <%for(int j=0; j < memoLst.size(); j++ ){
   String memo=(String)memoLst.get(j);
   String surAvgID = "SURT_"+memo;
   String polAvgID = "POL_"+memo;
   String accAvgID = "ACC_"+memo;
   String surDis = "value(SURT_"+memo+")";
   String polDis = "value(POL_"+memo+")";
   String accDis = "value(ACC_"+memo+")";
   sr = j+1;
    String checkFldId = "CHK_"+sr;
    String checkFldVal = "value("+memo+")";
    String idn=(String)memoIdnLst.get(memo);
    String target = "SC_"+idn;
   %>
   <tr id="<%=target%>">
   <td><html:checkbox property="<%=checkFldVal%>" styleId="<%=checkFldId%>" name="lotPricingForm" value="yes" /> </td>
   <td><%=memo%></td>
   <td><html:text property="<%=surDis%>" size="8" styleId="<%=surAvgID%>"/></td>
    <td><html:text property="<%=polDis%>" size="8" styleId="<%=polAvgID%>"/></td>
    <td><html:text property="<%=accDis%>" size="8" styleId="<%=accAvgID%>"/></td>
    <td align="center"><a href="lotpri.do?method=loadDept&Idn=<%=idn%>" id="LNK_<%=idn%>" onclick="loadASSFNL('<%=target%>','LNK_<%=idn%>')"  target="SC">Add</a></td>
   </tr>
   <%}%>
   </table></td>
   </tr>
   <input type="hidden" name="count" id="count" value="<%=sr%>" />
   </html:form>
   
  <%}}%>
   
   </table>
   </td>
  
  
   <tr>
    <td><jsp:include page="/footer.jsp" /> </td>
    </tr>
  </table>

  </body>
</html>