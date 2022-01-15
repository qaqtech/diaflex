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
<link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/style.css?v=<%=info.getJsVersion()%>"/>
<script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/ajax.js?v=<%=info.getJsVersion()%> " > </script>
<script language="javaScript" type="text/javascript" src="<%=info.getReqUrl()%>/calendar/calendar.js"></script>
<link href="<%=info.getReqUrl()%>/calendar/calendar.css" rel="stylesheet" type="text/css" />
<link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/style.css?v=<%=info.getJsVersion()%>"/>
  </head>
   <%
    HashMap mprp = info.getMprp();
    HashMap prp = info.getPrp();
    String shmfg  = util.nvl(request.getParameter("shmfg"));
    DBUtil dbutil = new DBUtil();
    DBMgr db = new DBMgr(); 
    db.setCon(info.getCon());
    dbutil.setDb(db);
    dbutil.setInfo(info);
    db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));db.setDbUsr(info.getDbUsr());
    dbutil.setLogApplNm(info.getLoApplNm());
    int accessidn=dbutil.updAccessLog(request,response,"Reports parameter form", "Reports parameter form");
    String logId=String.valueOf(info.getLogId());
    String onfocus="cook('"+logId+"')";
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
  <img src="../images/line.jpg" border="0" /></td><td valign="middle"> <span class="pgHed">Stock Tracking Report </span> </td>
   </tr></table>
  </td>
  </tr>
  <tr>
  <td valign="top" class="tdLayout">
   <html:form action="jasperReport/stockTrackingAction.do?method=Fetch"  method="post"  >
   <table cellpadding="2" cellspacing="2">
   <tr><td>Packet Code</td><td><html:text property="value(vnm)" name="jasperReportForm"/> </td></tr>
   <tr><td colspan="2"><html:submit property="value(submit)"  value="Generate Report"/> </td></tr>
   </table>
   </html:form>
  </td></tr>
  <% ArrayList  pktDtlList = (ArrayList)request.getAttribute("finalStockTrackList");
    ArrayList stockTrkList = (ArrayList)session.getAttribute("STKTRK_VW");
    int count =0;
   if(pktDtlList!=null && pktDtlList.size()>0){
   String msg = (String)request.getAttribute("msg");
  if(msg!=null){
  %>
  <tr><td valign="top" class="hedPg"  ><span class="redLabel" > <%=msg%></span></td></tr>
  <%}%>
  <tr><td valign="top" class="hedPg"  > 
  <table class="dataTable" >
  <thead>
  <tr>
  <th>Trans. Date</th>
  <th>Return Date</th>
  <th>Process</th>
  <th>Party Name  </th>
  <th>Finish No</th>
  <%for(int j=0; j < stockTrkList.size(); j++ ){
    String prps = (String)stockTrkList.get(j);
     String hdr = (String)mprp.get(prps);
    String prpDsc = (String)mprp.get(prps+"D");
    if(hdr == null) {
        hdr = prps;
       }
%>
<th title="<%=prpDsc%>"><%=hdr%></th>
<%
}%>       

</tr>
</thead>
<%
String tableTD = "";
  
for(int m=0;m<pktDtlList.size();m++){
       count++;
      
       String sttTdBg = "";
        if(m%2==0){
        tableTD="even";
        }else{
        tableTD="odd";
        }
      HashMap pktDtl = (HashMap)pktDtlList.get(m);

      %>
<tr class="<%=tableTD%>">
<td title="Trans Date"><%=(String)pktDtl.get("Date")%></td>
<td title="Return Date"><%=(String)pktDtl.get("ISSDate")%></td>
<td title="Process"><%=(String)pktDtl.get("prc")%></td>
<td title="Party Name"><%=(String)pktDtl.get("Name")%></td>
<td title="Finish No"><%=(String)pktDtl.get("VNM")%></td>
  <%for(int p=0; p < stockTrkList.size(); p++ ){
    String prps = (String)stockTrkList.get(p);
    String prpValue =  (String)pktDtl.get(prps);
    String hdr = (String)mprp.get(prps);
    String prpDsc = (String)mprp.get(prps+"D");
    if(hdr == null) 
        hdr = prps;
%>
<td title="<%=prpDsc%>"><%=prpValue%></td>
<%
}%>       

<%}%>

</tbody></table>

<input type="hidden" name="count" id="count" value="<%=count%>" />
  </td></tr>

  <% 
  }else{%>
  <tr><td valign="top" class="hedPg">Sorry No Result Found</td></tr>
  <%}%>

  
  <tr>
  <td><jsp:include page="/footer.jsp" /> </td>
  </tr>
 </table>
 </body>
</html>