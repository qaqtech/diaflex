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
  <table cellpadding="" cellspacing="">
   
   <%
   int rowCnt = 5 ;
   String msg = (String)request.getAttribute("msg");
   ArrayList dataList = (ArrayList)request.getAttribute("dataList");     
   ArrayList empdepprpLst = (ArrayList)session.getAttribute("empdepprpLst");
   HashMap allPageDtl= (info.getPageDetails() == null)?new HashMap():(HashMap)info.getPageDetails();
   HashMap pageDtl=(HashMap)allPageDtl.get("CONTACT_FORM");
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
  <html:form action="/contact/bulkUpdate.do?method=saveSaleEx" method="post">
  <html:hidden property="value(nmeIdn)" name="advContactForm" styleId="nmeIdn" />
  <input type="hidden" id="ROWCNT" name="ROWCNT" value="<%=rowCnt%>" />
  <table  class="grid1">
  <tr><th>Sr</th><th>Employee</th>
  <% for(int k=0;k<empdepprpLst.size();k++){ %>
  <th><%=empdepprpLst.get(k)%></th>
  <%}%>
  </tr>
  <%
  for(int p=1;p<=rowCnt;p++){
  String empIdnID = "empIdn_"+p;
  String empIdnNm = "value(empIdn_" +  p + ")";
  %>
  <tr>
  <td><%=p%></td>
  <td>
   <html:select property="<%=empIdnNm%>" styleId="<%=empIdnID%>" name="advContactForm"   >
            <html:optionsCollection property="empList" name="advContactForm" 
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
    <html:select property="<%=fldNm%>" styleId="<%=fldID%>" name="advContactForm" >
    <%
    if(list!=null){
    for(int i=0;i<list.size();i++){
    String fldVal=(String)list.get(i);%>
    <html:option value="<%=fldVal%>"><%=fldVal%></html:option> 
    <%}}%>   
    </html:select>
  </td>
  <% } %>
  
  </tr>
  <%}%>
  <tr><td colspan="6" align="center"><html:submit property="value(submit)" value="Submit" styleClass="submit" /></td> </tr>
  </table>
  </html:form>
  </td></tr>
  
  <% if(dataList!=null && dataList.size()>0){ %>
  <tr><td valign="top" class="hedPg">
  <table  class="grid1">
  
  <tr>
  <th>Sr</th>
  <th>Employee</th>
  <%
  for(int q=0;q< empdepprpLst.size();q++){ %> 
  <th><%=util.nvl((String)empdepprpLst.get(q))%></th>
  <%}%>
  <th>Vld_Frm</th>
  <th>Vld_To</th>
  <th>Action</th>
  </tr>
  
  
  
  <%
  int sr = 0;
  for(int m=0;m< dataList.size();m++){ 
  sr++;
  HashMap pktDtl = (HashMap)dataList.get(m);
  String color = util.nvl((String)pktDtl.get("Color"));
  String idn = util.nvl((String)pktDtl.get("IDN"));
  String nme_idn = util.nvl((String)pktDtl.get("NMEIDN"));
  if(!color.equals(""))
  color = "color: "+color+";";
  String link = info.getReqUrl() + "/contact/bulkUpdate.do?method=delete&idn="+ idn+"&nme_idn="+ nme_idn  ;
  String onclick = "return setDeleteConfirm('"+link+"')";
  %> 
  <tr style="<%=color%>">
  <td><%=sr%></td>
  <td><%=util.nvl((String)pktDtl.get("EMP"))%></td>
  <%for(int j=0; j < empdepprpLst.size(); j++ ){
  String prps = (String)empdepprpLst.get(j); %>
  <td><%=pktDtl.get(prps)%></td>
  <%}%>
  <td><%=util.nvl((String)pktDtl.get("Vld_Frm"))%></td>
  <td><%=util.nvl((String)pktDtl.get("Vld_To"))%></td>
  <td><html:link page="<%=link%>" onclick="<%=onclick%>">Delete</html:link></td>
  </tr>
  <% } %>
  
  </table>
  </td></tr>
  
  <% } %>
  
  </table>  
  </body>
</html>