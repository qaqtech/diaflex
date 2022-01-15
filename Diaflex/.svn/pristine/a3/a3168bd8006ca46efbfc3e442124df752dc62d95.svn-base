<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page errorPage="../error_page.jsp" %>
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.ResultSet, java.util.ArrayList,java.util.Collections,java.util.ArrayList,java.util.HashMap,java.text.SimpleDateFormat, java.util.Calendar"%>
<jsp:useBean id="info" class="ft.com.InfoMgr" scope="session" />
<jsp:useBean id="util" class="ft.com.JspUtil" scope="page" />
<%@taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>
<html> 
   <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/bse.js?v=<%=info.getJsVersion()%> " > </script>
  <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/bse.css?v=<%=info.getJsVersion()%>"/>
  <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/ajax.js?v=<%=info.getJsVersion()%> " > </script>
  <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/style.css?v=<%=info.getJsVersion()%>"/>
  <script language="javaScript" type="text/javascript" src="<%=info.getReqUrl()%>/calendar/calendar.js"></script>
   <link href="<%=info.getReqUrl()%>/calendar/calendar.css" rel="stylesheet" type="text/css" />
   <script src="<%=info.getReqUrl()%>/PopScripts/jquery-1.2.6.min.js" type="text/javascript"></script>
   <script type="text/javascript" src="<%=info.getReqUrl()%>/jqueryScript/jquery.js " > </script>
  <title>Sale Ex Allocation</title>
  </head>
        <%String logId=String.valueOf(info.getLogId());
        String onfocus="cook('"+logId+"')";
        %>
 <body onfocus="<%=onfocus%>" onload="MM_preloadImages('images/faunalogo.png')" topmargin="0" leftmargin="0" rightmargin="0" bottommargin="0">
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
  <img src="../images/line.jpg" border="0" /></td><td valign="middle"> <span class="pgHed">Sale Ex Allocation</span> </td>
</tr></table>
  </td>
  </tr>
    <%
   int rowCnt = 5 ;
   String msg = (String)request.getAttribute("msg");
   ArrayList empdepprpLst = (ArrayList)session.getAttribute("empdepprpLst");
   HashMap allPageDtl= (info.getPageDetails() == null)?new HashMap():(HashMap)info.getPageDetails();
   HashMap pageDtl=(HashMap)allPageDtl.get("EMP_DEP_FORM");
   HashMap prp = info.getPrp();
   ArrayList pageList=new ArrayList();
   HashMap pageDtlMap=new HashMap();
   String fld_nme="",fld_ttl="",val_cond="",dflt_val="",fld_typ="",form_nme="";
   pageList= ((ArrayList)pageDtl.get("ROW") == null)?new ArrayList():(ArrayList)pageDtl.get("ROW");
   if(pageList!=null && pageList.size() >0){
   for(int j=0;j<pageList.size();j++){
   pageDtlMap=(HashMap)pageList.get(j);
   dflt_val=(String)pageDtlMap.get("dflt_val");
   rowCnt = Integer.parseInt(dflt_val);
   }
   }
   %>
   
   <%
   if(msg!=null){
   %>
    <tr><td valign="top" class="tdLayout"><span class="redLabel"><%=msg%></span></td></tr>
   <%}%>
  <tr><td valign="top" class="hedPg">
  <html:form action="/masters/EmpDep.do?method=save" method="post">
  <input type="hidden" id="ROWCNT" name="ROWCNT" value="<%=rowCnt%>" />
  <table  class="grid1">
  <tr><th>Sr</th><th>Buyer</th><th>Employee</th>
  <% for(int k=0;k<empdepprpLst.size();k++){ %>
  <th><%=empdepprpLst.get(k)%></th>
  <%}%>
  <th>Valid From</th><th>Valid To</th></tr>
  <%
  for(int p=1;p<=rowCnt;p++){
  String nmeID = "nmeID_"+p;
  String nmeNm = "nme_"+p;
  String nmePop = "nmePop_"+p;
  String empIdnID = "empIdn_"+p;
  String empIdnNm = "value(empIdn_" +  p + ")";
  String frmdteID = "frmdte_"+p;
  String frmdteNm = "value(frmdte_" +  p + ")";
  String todteID = "todte_"+p;
  String todteNm = "value(todte_" +  p + ")";
  %>
  <tr>
  <td><%=p%></td>
  <td nowrap="nowrap">
   <%
    String sbUrl ="ajaxRptAction.do?method=buyerSugg";
   %>
    <input type="text" id="<%=nmeNm%>" name="<%=nmeNm%>" autocomplete="off" class="sugBox"
      onKeyUp="doCompletion('<%=nmeNm%>', '<%=nmePop%>', 'ajaxRptAction.do?method=buyerSugg', event)" 
      onKeyDown="setDown('<%=nmePop%>', '<%=nmeID%>', '<%=nmeNm%>',event)"/>
 <img src="../images/addrow.jpg" width="17" height="17" title="Click To get Names" onclick="doCompletionMobile('<%=nmeNm%>', '<%=nmePop%>', 'ajaxRptAction.do?method=buyerSugg')">
 <input type="hidden" name="<%=nmeID%>" id="<%=nmeID%>" value="">
  <div style="position: absolute;">
      <select id="<%=nmePop%>" name="<%=nmePop%>"
        style="display:none;300px;" 
        class="sugBoxDiv"
        onKeyDown="setDown('<%=nmePop%>', '<%=nmeID%>', '<%=nmeNm%>',event)" 
        onDblClick="setVal('<%=nmePop%>', '<%=nmeID%>', '<%=nmeNm%>', event);hideObj('<%=nmePop%>')" 
        onClick="setVal('<%=nmePop%>', '<%=nmeID%>', '<%=nmeNm%>', event);" 
        size="10">
      </select>
</div>
  </td>
   <td>
   <html:select property="<%=empIdnNm%>" styleId="<%=empIdnID%>" name="EmpDepForm"   >
            <html:optionsCollection property="empList" name="EmpDepForm" 
            label="byrNme" value="byrIdn" />
   </html:select>
   </td>
   
    <select style="display:none" name="multiPrp" id="multiPrp">
    <%
    for(int s=0;s<empdepprpLst.size();s++){
    String lprp = util.nvl((String)empdepprpLst.get(s));
    %>
    <option value="<%=lprp%>"></option>
    <%}%>
    </select>
    
   <%
   for(int j=0;j<empdepprpLst.size();j++){
   String fld = util.nvl((String)empdepprpLst.get(j));
   String fldNm = "value("+fld+"_"+p+")";
   String fldID = fld+"_"+p;
   ArrayList list = (ArrayList)prp.get(fld+"V");
   %>
   <td>
    <html:select property="<%=fldNm%>" styleId="<%=fldID%>" name="EmpDepForm" >
    <html:option value="" >--Select--</html:option> 
    <%
    if(list!=null){
    for(int i=0;i<list.size();i++){
    String fldVal=(String)list.get(i);%>
    <html:option value="<%=fldVal%>"><%=fldVal%></html:option> 
    <%}}%>   
    </html:select>
  </td>
  <% } %>
  
  <td><html:text property="<%=frmdteNm%>" styleId="<%=frmdteID%>" name="EmpDepForm" size="8" />&nbsp;<img src="<%=info.getReqUrl()%>/images/calender.png" onClick="setYears(2011, <%=info.getCurrentYear()%>);showCalender(this, '<%=frmdteID%>');"></td>
  <td><html:text property="<%=todteNm%>" styleId="<%=todteID%>" name="EmpDepForm"  size="8" />&nbsp;<img src="<%=info.getReqUrl()%>/images/calender.png" onClick="setYears(2011, <%=info.getCurrentYear()%>);showCalender(this, '<%=todteID%>');"></td>
  </tr>
  <%}%>
  <tr><td colspan="6" align="center"><html:submit property="value(submit)" value="Submit" styleClass="submit" /></td> </tr>
  </table>
  </html:form>
  </td></tr>
  <tr><td>
<jsp:include page="/footer.jsp" />
</td></tr>
  </table>  
   <%@include file="../calendar.jsp"%>
  </body>
</html>