<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page errorPage="../error_page.jsp" %>
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.ResultSet, java.util.ArrayList,java.util.Collections,java.util.ArrayList,java.util.HashMap,java.text.SimpleDateFormat, java.util.Calendar"%>
<jsp:useBean id="db" class="ft.com.DBMgr" scope="session"/>
<jsp:useBean id="log" class="ft.com.LogMgr" scope="session"/>
<jsp:useBean id="util" class="ft.com.DBUtil" scope="session"/>
<jsp:useBean id="info" class="ft.com.InfoMgr" scope="session"/>

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

  <title>Status Lab Report</title>
 
  </head>
        <%String logId=String.valueOf(info.getLogId());
        String onfocus="cook('"+logId+"')";
        %>
 <body onfocus="<%=onfocus%>" onload="MM_preloadImages('images/faunalogo.png')" topmargin="0" leftmargin="0" rightmargin="0" bottommargin="0">
   <div id="backgroundPopup"></div>
    <html:form>
   <%
    ArrayList labList= info.getLabList();
    HashMap lab_qtyctstable=info.getLab_qtyctstable();
    HashMap stt_qtyctstable=info.getStt_qtyctstable();
    String gndCtsQty=(String)session.getAttribute("gndCtsQty");
   %>
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
  <img src="../images/line.jpg" border="0" /></td><td valign="middle"> <span class="pgHed">
Status Lab Report
 
  </span> </td>
</tr></table>
  </td>
  </tr>
  
  <tr><td class="hedPg"><table class="dataTable" >
  <tr><td>Create Excel <img src="../images/ico_file_excel.png" title="Click here to Generate Excel" onclick="goTo('stockSummary.do?method=createStatusLabXL','','')" border="0"/> </td></tr>
  <tr><th>STATUS/LAB</th>
  <%
 
  for(int i=0;i<labList.size();i++)
  {
  %>
  <th colspan="2"><%=labList.get(i) %></th>
  <%
  }
  %>
   <th colspan="2">Total</th>
  </tr>
 <tr>
  <td></td>
  <%

  for(int i=0;i<=labList.size();i++)
  {
  %>

  <td> <span>Quantity</span></td>
 
  <td ><span>Cts</span></td>
  <%
  }
  %>
 </tr>
  <%
  ArrayList dfStkStt=info.getDfStkStt();
  ArrayList sttlist=info.getSttList();
  ArrayList dfStt=null;
  for(int j=0;j<dfStkStt.size();j++)
  {
  dfStt=new ArrayList();
  dfStt=(ArrayList)dfStkStt.get(j);
  String stt=util.nvl((String)dfStt.get(0));
   String dsc=util.nvl((String)dfStt.get(1));
  //String stt=(String)sttlist.get(j);
//  if(dfStkStt.containsKey(stt)){
//  dsc=(String)dfStkStt.get(stt);
//  }else{
//  dsc=stt;
//  }
     String styleClass=null;
   int cls=j % 2;
   if(cls==0)
   {
    styleClass = "odd" ;
   }else
   {
    styleClass = "even" ;
   }
  %>
   <tr  class="<%=styleClass%>"> 
  <td class="rowtyp" style="text-align:left;"><%=dsc%></td>
  <%
  int totcolm=labList.size()*2;
  for(int k=0;k<labList.size();k++)
  {
  String lab=(String)labList.get(k);
  if(lab.equals("NA") && stt.equals("MKAV"))
  {
  System.out.println("NA");
  }
   HashMap qty_ctstbl=info.getQty_ctstbl();
   String qty_cts=util.nvl((String)qty_ctstbl.get(lab+"_"+stt));
   String qty="";
   String cts="";
   if(!qty_cts.equals(""))
   {
   int indx=qty_cts.indexOf("|");
    qty=util.nvl(qty_cts.substring(0,indx));
    if(qty.contains("null"))
    {
    qty="0";
    }
    cts=util.nvl(qty_cts.substring(indx+1, qty_cts.length()));
    if(cts.contains("null"))
    {
    cts="0";
    }
    String idval="id"+cts;
    %>
 
  <!--    <td>
      <table cellpadding="1" cellspacing="1" width="100%"><tr><td width="50%"  align="center"><span ><a   title="Packet Details"  href="stockSummary.do?method=pktDtl&status=<%=stt%>&lab=<%=lab%>" ><%=qty%></a></span></td><td> <img  src="../images/ttlovr_img.jpg" border="0" /></td><td  width="50%" align="center"><span><a  title="Shape-Size report" href="stockSummary.do?method=loadShSize&status=<%=stt%>&lab=<%=lab%>" ><%=cts%></a></span></td></tr></table>
      </td> -->
     <td   align="center"><span><a  title="Shape-Size report" href="stockSummary.do?method=loadShSize&status=<%=stt%>&lab=<%=lab%>" ><%=qty%></a></span></td>
     <td align="center"><span ><a   title="Packet Details"  href="stockSummary.do?method=pktDtl&status=<%=stt%>&lab=<%=lab%>" ><%=cts%></a></span></td> 
  <% 
   }else
   {
   
%>
   <td> </td> <td> </td>
   
   <%
   }
  }

  
 
   if(stt_qtyctstable.containsKey(stt))
   {
   if(!(stt_qtyctstable.get(stt)).equals("")){
   String qty_cts=(String)stt_qtyctstable.get(stt);
   int indx=qty_cts.indexOf("|");
  String  qty=util.nvl(qty_cts.substring(0,indx));
   String cts=util.nvl(qty_cts.substring(indx+1, qty_cts.length()));
    if(qty.contains("null"))
    {
    qty="0";
    }
     if(cts.contains("null"))
    {
    cts="0";
    }
   
    %>
 
 <!--     <td>
      <table cellpadding="1" cellspacing="1" width="100%"><tr><td width="50%"  align="center"><span ><a   title="Packet Details"  ><%=qty%></a></span></td><td> <img  src="../images/ttlovr_img.jpg" border="0" /></td><td  width="50%" align="center"><span><a  title="Shape-Size report" <%=cts%></a></span></td></tr></table>
      </td> -->
      <td   align="center"><span><a  title="Shape-Size report" href="stockSummary.do?method=loadShSize&status=<%=stt%>">  <%=qty%></a></span></td>
            <td   align="center"><span ><a   title="Packet Details" href="stockSummary.do?method=pktDtl&status=<%=stt%>"   ><%=cts%></a></span></td>
  <% 
   }else
   {
%>
   <td> </td> <td> </td>
   <%
   }
   }else
   { 
%>
   <td> </td> <td> </td>   
   <%
   }
   %>
  </tr>
  <%
} 
 %>
  <tr><td>Total</td>
  <%
  if(lab_qtyctstable.size()>0)
  {
  for(int i=0;i<labList.size();i++) 
  {
   String lab=util.nvl((String)labList.get(i));
  String qtycts=util.nvl((String)lab_qtyctstable.get(lab));
   if(!qtycts.equals(""))
   {
 int indx=qtycts.indexOf("|");
  String  qty=util.nvl(qtycts.substring(0,indx));
   String cts=util.nvl(qtycts.substring(indx+1, qtycts.length()));
    if(qty.contains("null"))
    {
    qty="0";
    }
     if(cts.contains("null"))
    {
    cts="0";
    }
    %>
 
<!--      <td>
      <table cellpadding="1" cellspacing="1" width="100%"><tr><td width="50%"  align="center"><span ><a   title="Packet Details"  ><%=qty%></a></span></td><td> <img  src="../images/ttlovr_img.jpg" border="0" /></td><td  width="50%" align="center"><span><a  title="Shape-Size report" <%=cts%></a></span></td></tr></table>
      </td> -->
      <td   align="center"><span><a  title="Shape-Size report" href="stockSummary.do?method=loadShSize&&lab=<%=lab%>" > <%=qty%></a></span></td>
      <td  align="center"><span ><a   title="Packet Details"  href="stockSummary.do?method=pktDtl&lab=<%=lab%>"    ><%=cts%></a></span></td>
  <% 
  }
  else
  {
  %>
   <td> </td> <td> </td>
   
   <%
  }
 }
}
 if(!gndCtsQty.equals(""))
   {
 int indx=gndCtsQty.indexOf("|");
  String  qty=util.nvl(gndCtsQty.substring(0,indx));
   String cts=util.nvl(gndCtsQty.substring(indx+1, gndCtsQty.length()));
    if(qty.contains("null"))
    {
    qty="0";
    }
     if(cts.contains("null"))
    {
    cts="0";
    }
    %>
 
 <!--     <td>
      <table cellpadding="1" cellspacing="1" width="100%"><tr><td width="50%"  align="center"><span ><a   title="Packet Details"  ><%=qty%></a></span></td><td> <img  src="../images/ttlovr_img.jpg" border="0" /></td><td  width="50%" align="center"><span><a  title="Shape-Size report" <%=cts%></a></span></td></tr></table>
      </td> -->
      <td   align="center"><span ><a   title="Packet Details"  ><%=qty%></a></span></td>
    <!-- <td> <img  src="../images/ttlovr_img.jpg" border="0" /></td>-->
      <td   align="center"><span><a  title="Shape-Size report"> <%=cts%></a></span></td>
  <% 
  }
  else
  {
  %>
   <td> </td> <td> </td>
   
   <%
  }
%>
  
  </tr>
 
  
  </table></td></tr>
  <tr><td>
<jsp:include page="/footer.jsp" />
</td></tr>
  </table>  
   </html:form>
  </body>
</html>