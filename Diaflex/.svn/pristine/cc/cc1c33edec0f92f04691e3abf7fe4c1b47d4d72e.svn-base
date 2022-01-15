<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page errorPage="../error_page.jsp" %>
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.ResultSet, java.util.ArrayList,java.util.Collections,java.util.ArrayList,java.util.HashMap,java.text.SimpleDateFormat, java.util.Calendar"%>
<jsp:useBean id="info" class="ft.com.InfoMgr" scope="session" />
<jsp:useBean id="util" class="ft.com.JspUtil" scope="page" />

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

  <title>Repeat Customer</title>
 
  </head>
  
  <%
  HashMap prp = info.getPrp();
  ArrayList deptPrpDtl = (ArrayList)prp.get("DEPT"+"V");
  %>
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
  <img src="../images/line.jpg" border="0" /></td><td valign="middle"> <span class="pgHed">Repeat Customer</span> </td>
</tr></table>
  </td>
  </tr>
<tr><td valign="top" class="hedPg">
<html:form action="/report/orclReportAction.do?method=fetchrepetcust"  method="POST">
 <table class="grid1">
            <tr><th colspan="2">Dept Search </th></tr>
            <tr>
            <td>Department</td>
            <td>
            <html:select property="value(dept)"  styleId="dept" name="orclReportForm" >
             <html:option value="ALL" >ALL</html:option>
             <%for(int i=0;i<deptPrpDtl.size();i++){
                String dept=(String)deptPrpDtl.get(i);
             %>
             <html:option value="<%=dept%>" ><%=dept%></html:option>
             <%}%>
             </html:select>
            </td>
            </tr>
           <tr><td>Date  </td>  
       <td><html:text property="value(frmdte)" styleId="frmdte" name="orclReportForm" size="8" /><img src="<%=info.getReqUrl()%>/images/calender.png" onClick="setYears(2011, <%=info.getCurrentYear()%>);showCalender(this, 'frmdte');"> 
       To&nbsp; <html:text property="value(todte)" styleId="todte" name="orclReportForm"  size="8"/> <img src="<%=info.getReqUrl()%>/images/calender.png" onClick="setYears(2011, <%=info.getCurrentYear()%>);showCalender(this, 'todte');"></td>
      </tr>
      <tr><td colspan="2" align="center"><html:submit property="value(srch)" value="Fetch" styleClass="submit" onclick="return validatkaccha()" /></td> </tr>
 </table>
</html:form>
</td>
</tr>
  <%
    ArrayList byrDtl= (ArrayList)request.getAttribute("byrDtl");
    String view= util.nvl((String)request.getAttribute("view"));
    HashMap data=new HashMap();
    ArrayList keystt=new ArrayList();
    String stt="";
    if(view.equals("Y")){
    if(byrDtl!=null && byrDtl.size()>0){
    int cnt=0;
    int sr=0;
    int boxcnt=(byrDtl.size()/3)+1;
    int size=byrDtl.size();
     ArrayList itemHdr = new ArrayList();
     ArrayList pktList = new ArrayList();
     HashMap pktData=new HashMap();
  %>
  <tr><td valign="top" class="hedPg">Create Excel <img src="../images/ico_file_excel.png" onclick="goTo('orclReportAction.do?method=createrepetcustXL','','')" border="0"/> </td></tr>
  <tr><td valign="top" class="hedPg">
  <table class="grid1" align="left">
  <tr>
  <th>Sr No </th><th>Party</th><th>Value('000)</th>
  <th>Sr No </th><th>Party</th><th>Value('000)</th>
  <th>Sr No </th><th>Party</th><th>Value('000)</th>
  </tr>
  <%
  itemHdr.add("SrNo");itemHdr.add("Party");itemHdr.add("Value('000)");
  itemHdr.add("SrNo$");itemHdr.add("Party$");itemHdr.add("Value('000)$");
  itemHdr.add("SrNo$$");itemHdr.add("Party$$");itemHdr.add("Value('000)$$");
  %>
  <%for(int i=0;i<boxcnt;i++){
  sr=i+1;
  data=new HashMap();
  data=(HashMap)byrDtl.get(i);
  String party=util.nvl((String)data.get("BYR"));
  String vlu=util.nvl((String)data.get("VLU"));
  pktData=new HashMap();
  pktData.put("SrNo",String.valueOf(sr));
  pktData.put("Party",party);
  pktData.put("Value('000)",vlu);
  %>
  <tr>
  <td><%=sr%></td>
  <td nowrap="nowrap"><%=party%></td>
  <td nowrap="nowrap" align="right"><%=vlu%></td>
  <%
  data=new HashMap();
  sr=sr+boxcnt;
  data=(HashMap)byrDtl.get(sr-1);
  party=util.nvl((String)data.get("BYR"));
  vlu=util.nvl((String)data.get("VLU"));
  pktData.put("SrNo$",String.valueOf(sr));
  pktData.put("Party$",party);
  pktData.put("Value('000)$",vlu);
  %>
  <td><%=sr%></td>
  <td nowrap="nowrap"><%=party%></td>
  <td nowrap="nowrap" align="right"><%=vlu%></td>
  <%
  data=new HashMap();
  sr=sr+boxcnt;
  if(sr<=size){
  data=(HashMap)byrDtl.get(sr-1);
  party=util.nvl((String)data.get("BYR"));
  vlu=util.nvl((String)data.get("VLU"));
  pktData.put("SrNo$$",String.valueOf(sr));
  pktData.put("Party$$",party);
  pktData.put("Value('000)$$",vlu);
  %>
  <td><%=sr%></td>
  <td nowrap="nowrap"><%=party%></td>
  <td nowrap="nowrap" align="right"><%=vlu%></td>
  <%}else{%>
  <td></td>
  <td></td>
  <td></td>
  <%}
  pktList.add(pktData);
  %>
  </tr>
  <%}%>
  
  </table>
  
  </td>
  </tr>
  
<%
session.setAttribute("itemHdr", itemHdr);
session.setAttribute("pktList", pktList);
}else{%>
<tr><td valign="top" class="hedPg">
Sorry No Result Found</td></tr>
<%}}%>
  <tr><td>
<jsp:include page="/footer.jsp" />
</td></tr>
  </table>  
  </body>
  <%@include file="../calendar.jsp"%>
</html>