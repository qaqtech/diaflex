<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import="java.util.ArrayList,java.util.ArrayList,java.util.HashMap, java.sql.*, java.util.Enumeration, java.util.logging.Level"%>
<jsp:useBean id="info" class="ft.com.InfoMgr" scope="session" />
<jsp:useBean id="gtMgr" class="ft.com.GtMgr" scope="session" />
<jsp:useBean id="util" class="ft.com.JspUtil" scope="page" />
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>Packet Detail</title>
    <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/bse.js?v=<%=info.getJsVersion()%> " > </script>
<link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/bse.css?v=<%=info.getJsVersion()%>"/>
<script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/ajax.js?v=<%=info.getJsVersion()%> " > </script>
  

 
      <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/style.css?v=<%=info.getJsVersion()%>"/>
  </head>
        <%String logId=String.valueOf(info.getLogId());
        String onfocus="cook('"+logId+"')";
        %>
 <body onfocus="<%=onfocus%>" onload="MM_preloadImages('images/faunalogo.png')" topmargin="0" leftmargin="0" rightmargin="0" bottommargin="0">
    <table>
   <%
   String lstNme = (String)request.getParameter("lstNme");
    ArrayList pktList = (ArrayList)gtMgr.getValue(lstNme+"_FILTPKTDTL"); 
  if(pktList!=null && pktList.size()>0){
  ArrayList vwPrpLst = (ArrayList)session.getAttribute("LAB_VIEW");
  ArrayList itemHdr = new ArrayList();
  itemHdr.add("PACKETCODE");
   itemHdr.add("QTY");
    itemHdr.add("CTS");
     itemHdr.add("STT");
  %>
 
<tr><td valign="top" class="hedPg">   Create Excel &nbsp;&nbsp;&nbsp;
  <img src="../images/ico_file_excel.png" border="0" onclick="goTo('<%=info.getReqUrl()%>/report/genericReport.do?method=createXL','','')"/>
 </td></tr>
  <tr><td valign="top" class="hedPg">
  <table class="dataTable" align="left" id="dataTable">
  <thead>
  <tr><th>PACKETCODE</th><th>QTY</th><th>CTS</th><th>STT</th>
  <%for(int i=0 ; i < vwPrpLst.size() ;i++){
  String lprp =(String)vwPrpLst.get(i);
  itemHdr.add(lprp);
  %>
  <th><%=vwPrpLst.get(i)%></th>
  <%}%>
  </tr>
  </thead>
  <tbody>
  <%for(int j=0 ; j <pktList.size() ;j++){
  HashMap pktDtl = (HashMap)pktList.get(j);
  %>
  <tr>
  <td><%=pktDtl.get("PACKETCODE")%></td><td><%=pktDtl.get("QTY")%></td>
  <td><%=pktDtl.get("CTS")%></td> <td><%=pktDtl.get("STT")%></td>
  <%for(int k=0;k<vwPrpLst.size() ;k++){
  String lprp = (String)vwPrpLst.get(k);
  
  %>
  <td><%=util.nvl((String)pktDtl.get(lprp))%></td>
  <%}%>
  </tr>
  <%}%>
  </tbody>
  </table></td></tr>
  <%
  session.setAttribute("pktList", pktList);
  session.setAttribute("itemHdr", itemHdr);
  }else{%>
<tr><td valign="top" class="hedPg">
Sorry No Result Found</td></tr>
  <%}%>
  </table>
    
    
    </body>
</html>