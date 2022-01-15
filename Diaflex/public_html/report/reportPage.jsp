
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page errorPage="../error_page.jsp" %>
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import="java.util.ArrayList,java.util.ArrayList,ft.com.*,java.util.HashMap, java.sql.*, java.util.Enumeration, java.util.logging.Level"%>
	<jsp:useBean id="info" class="ft.com.InfoMgr" scope="session" />
	<jsp:useBean id="util" class="ft.com.JspUtil" scope="page" />
<%@taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
 
<html>
  <head>
 <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
  
  <title>Reports</title>
  <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/bse.js?v=<%=info.getJsVersion()%> " > </script>
<link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/bse.css?v=<%=info.getJsVersion()%>"/>
<link href="<%=info.getReqUrl()%>/auto-search/auto-search.css?v=<%=info.getJsVersion()%>"
                rel="stylesheet" type="text/css"/>
<link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/style.css?v=<%=info.getJsVersion()%>"/>
<script src="<%=info.getReqUrl()%>/PopScripts/jquery-1.11.3.min.js" type="text/javascript"></script>
<script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/ajax.js?v=<%=info.getJsVersion()%> " > </script>
    <script language="javaScript" type="text/javascript" src="<%=info.getReqUrl()%>/calendar/calendar.js"></script>
   <link href="<%=info.getReqUrl()%>/calendar/calendar.css" rel="stylesheet" type="text/css" />
     <script src="<%=info.getReqUrl()%>/auto-search/auto-search.js?v=<%=info.getJsVersion()%>" type="text/javascript"></script>
           <script src="<%=info.getReqUrl()%>/auto-search/autoajaxjs.js?v=<%=info.getJsVersion()%>" type="text/javascript"></script>

 
      <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/style.css?v=<%=info.getJsVersion()%>"/>
  </head>
 
   <%
   HashMap mprp = info.getMprp();
   HashMap prp = info.getPrp();
   String shmfg  = util.nvl(request.getParameter("shmfg"));
   String suggScript="";
    DBUtil dbutil = new DBUtil();
    DBMgr db = new DBMgr(); 
    db.setCon(info.getCon());
    dbutil.setDb(db);
    dbutil.setInfo(info);
    db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));db.setDbUsr(info.getDbUsr());
    dbutil.setLogApplNm(info.getLoApplNm());
   int accessidn=dbutil.updAccessLog(request,response,"Reports parameter form", "Reports parameter form");
   %>
        <%String logId=String.valueOf(info.getLogId());
        String onfocus="cook('"+logId+"')";
        %>
 <body onfocus="<%=onfocus%>" onload="MM_preloadImages('images/faunalogo.png')" topmargin="0" leftmargin="0" rightmargin="0" bottommargin="0">
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
  <img src="../images/line.jpg" border="0" /></td><td valign="middle"> <span class="pgHed">Report </span> </td>
</tr></table>
  </td>
  </tr>
  <tr>
  <td valign="top" class="tdLayout">
   <table><tr><td>
   <html:form action="report/orclReportAction.do?method=viewRT" target="_blank" method="post" >
   <input type="hidden" name="shmfg" id="shmfg" value="<%=shmfg%>"/>
   <input type="hidden" name="accessidn" id="accessidn" value="<%=accessidn%>"/>
   <table><tr><td>Reports : </td>
   <td>
  <%
  String dv = request.getParameter("DV");
  if(dv!=null){%>
  <html:hidden property="value(reportPag)" name="orclReportForm"  />
  <%=request.getAttribute("pageDsc")%>
  <%}else{
  %>
   <html:select property="value(reportPag)"  styleId="reportPag" onchange="sumitForm()" name="orclReportForm"   >
           <html:option value="ALL" >--ALL--</html:option>
            <html:optionsCollection property="memoList" name="orclReportForm" 
            label="dsc" value="flg" />
    </html:select>
   <%}%>
   </td></tr>
   <%
   int countM=0;
   ArrayList paramsList = (ArrayList)session.getAttribute("paramsList");
   if(paramsList!=null && paramsList.size() >0){%>
   
   
  <% for(int i=0 ; i< paramsList.size() ; i++){
   HashMap params = (HashMap)paramsList.get(i);
   String dataTyp = (String)params.get("dataTyp");
   String dsc = (String)params.get("dsc");
   String pNme = (String)params.get("pNme");
   String list = (String)params.get("list");
   String fldParmVal = "value("+pNme+")";
   %>
   
   <%if(dataTyp.equals("C")){
   ArrayList prpVal = (ArrayList)prp.get(dsc+"V");
   ArrayList prpDsc = (ArrayList)prp.get(dsc+"P");
   %>
   <tr><td><%=dsc%> </td><td>
   <html:select property="<%=fldParmVal%>" >
  
   <%
   for(int j=0 ; j < prpVal.size() ; j++){
   String fldVal = (String)prpVal.get(j);
   String fldDsc = (String)prpDsc.get(j);
   %>
   <html:option value="<%=fldVal%>"><%=fldDsc%></html:option>
   <%}%>
   </html:select></td></tr>
   <%}else if(dataTyp.equals("T")){%>
  <tr> <td><%=dsc%> </td><td>
   <html:text property="<%=fldParmVal%>"  /></td></tr>
   <%}else if(dataTyp.equals("L") || dataTyp.equals("S")){
    HashMap lovKV = null;
    if(dataTyp.equals("S"))
        lovKV = dbutil.getLOV(list);
     else
        lovKV = dbutil.getLOVList(list);
    ArrayList keys = (lovKV.get("K")!= null) ? (ArrayList)lovKV.get("K"):new ArrayList();
    ArrayList vals = (lovKV.get("V")!= null) ? (ArrayList)lovKV.get("V"):new ArrayList();
   %><tr><td><%=dsc%> </td><td>
    <html:select property="<%=fldParmVal%>" >
   
         <%for(int a=0; a < keys.size(); a++) {
             String lKey = (String)keys.get(a);
             String ldspVal = (String)vals.get(a);%>
     <html:option value="<%=lKey%>"><%=ldspVal%></html:option>
    <%}%>
    </html:select></td></tr>
  <%}else if(dataTyp.equals("M")){
    HashMap lovKV = dbutil.getLOV(list);
    ArrayList keys = (lovKV.get("K")!= null) ? (ArrayList)lovKV.get("K"):new ArrayList();
    ArrayList vals = (lovKV.get("V")!= null) ? (ArrayList)lovKV.get("V"):new ArrayList();
    if(countM==0){%>
    <tr><td colspan="2">
    <% }
    %> 
    <% if(countM==7){%>
    </td></tr><tr><td colspan="2">
    <% countM=0; }
    countM++;
    %>
    
   <div class="refDiv" ><table><tr>
   <td><%=dsc%> </td><td>
     <select  multiple="true" style="height:100px;width:190px"  size="8" name="<%=pNme%>" >
      <%for(int b=0; b < keys.size(); b++) {
             String lKey = (String)keys.get(b);
             String ldspVal = (String)vals.get(b);%>
     <option value="<%=lKey%>"><%=ldspVal%></option>
    <%}%>
    </select></td></tr></table></div>
   <%}else if(dataTyp.equals("SB")){%>
   <tr><td><%=dsc%> </td><td>
       <input type="text" id="nme" name="nme" autocomplete="off" class="sugBox"
      onKeyUp="doCompletion('nme', 'nmePop', '<%=list%>', event)" 
      onKeyDown="setDown('nmePop', '<%=pNme%>', 'nme',event)"/>
 <img src="../images/addrow.jpg" width="17" height="17" title="Click To get Names" onclick="doCompletionMobile('nme', 'nmePop', '<%=list%>')">
<html:hidden property="<%=fldParmVal%>" styleId="<%=pNme%>"  />
  <div style="position: absolute;">
      <select id="nmePop" name="nmePop"
        style="display:none;300px;" 
        class="sugBoxDiv"
        onKeyDown="setDown('nmePop', '<%=pNme%>', 'nme',event)" 
        onDblClick="setVal('nmePop', '<%=pNme%>', 'nme', event);hideObj('nmePop')" 
        onClick="setVal('nmePop', '<%=pNme%>', 'nme', event);" 
        size="10">
      </select>
</div>
</td>  </tr>  
   <%}else if(dataTyp.equals("TA")){%>
   <tr><td><%=dsc%> </td><td>  <html:textarea property="<%=fldParmVal%>" cols="30" styleId="<%=pNme%>" />
        </td>  </tr>  
   
   <%}else if(dataTyp.equals("SM")){
     suggScript=suggScript+"$('#"+list+"').on('keyup',function() {  "+
       " var timer = setTimeout(autoSearchData('"+list+"','"+list+"','true','value'), 300);  });"+
       " $('#"+list+"').keyup();";
      
      
   %>
    <tr> <td><%=dsc%> </td>  <td colspan="2">
           <div>
           <html:hidden property="<%=fldParmVal%>" styleId="hidden_select" value=""/>
            <input type="text"  name="" id="<%=list%>" class="magicsearch txtStyle"   style="width:200px;height:20px;"   autocomplete="off"  value="" placeholder="" ></input>
         
             </div>
             </td></tr>
  <% } else{%>
  <tr> <td><%=dsc%> </td><td> <div class="float">   <html:text property="<%=fldParmVal%>" styleId="<%=pNme%>" /></div>
            <div class="float">     <a href="#"  onClick="setYears(1900, <%=info.getCurrentYear()%>);showCalender(this, '<%=pNme%>');">
                <img src="<%=info.getReqUrl()%>/images/calender.png"></a></div></td></tr>
   <%}%>
  

   <%}%>
   
   <%}%>
   <%if(countM>0){%></td></tr><%}%>
  <tr> <td colspan="2" ><html:submit property="view" styleClass="submit" value="Report"/> <input type="reset" class="submit" value="Reset"/>
  <!--<button type="button" class="submit" onclick="goTo('orclReportAction.do?method=load','','')" name="load" >Reset</button>-->
  </td></tr>
  </table>
   </html:form>
   </td></tr>
  </table></td></tr>
  
  
  </table>
   <%String multiSugg="<script language=\"javascript\">"+"   "+suggScript+ "</script>";%>
 <%=multiSugg%>
 <script language="javascript">
 $(document).on('click','.magicsearch-box ul li',function() {
        var hidVal=$('.magicsearch').attr('data-id');
        $('#hidden_select').val(hidVal);
        });
        
        $(document).on('click','.multi-item-close',function() {
         setTimeout(function(){
         var hidVal=$('.magicsearch').attr('data-id');
        $('#hidden_select').val(hidVal);
         },500);
        });
 </script>
     <%@include file="../calendar.jsp"%>
  
  </body>
</html>