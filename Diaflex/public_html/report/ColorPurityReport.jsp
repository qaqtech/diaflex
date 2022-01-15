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

  <title>ColorPurityReport</title>
 
  </head>
        <%String logId=String.valueOf(info.getLogId());
        String onfocus="cook('"+logId+"')";
        %>
 <body onfocus="<%=onfocus%>" onload="MM_preloadImages('images/faunalogo.png')" topmargin="0" leftmargin="0" rightmargin="0" bottommargin="0">
   <div id="backgroundPopup"></div>
    <html:form>
   <%
    util.updAccessLog(request,response,"ColorPurityReport", "ColorPurityReport");
    ArrayList purityList=  info.getPurityList();
    ArrayList colorList=info.getColorList();
      String status=util.nvl((String)request.getParameter("status")); 
    String lab=util.nvl((String)request.getParameter("lab"));
      String shape=util.nvl((String)request.getParameter("shape")); 
    String size=util.nvl((String)request.getParameter("size"));
    String group=util.nvl((String)request.getParameter("group")); 
    HashMap col_ctsqty=info.getCol_ctsqty();
    HashMap clry_ctsqty=info.getClry_ctsqty();
    String grdqty_cts=(String) session.getAttribute("grndcolClr");
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
Color Purity Report
 
  </span> </td><td>Criteria :</td>
  <%
  String crt="";
  if(!status.equals(""))
  {
  crt=crt+"Status->"+status+";";
  }
  if(!lab.equals(""))
  {
  crt=crt+"Lab->"+lab+"";
  }
   if(!shape.equals(""))
  {
  crt=crt+"Shape->"+shape+";";
  }
  if(!size.equals(""))
  {
  crt=crt+"Size->"+size+"";
  }
  %>
  <td><%=crt%></td>
</tr></table>
  </td>
  </tr>
  <tr><td class="hedPg" ><table class="dataTable">
  <tr><td>Create Excel <img src="../images/ico_file_excel.png" title="Click here to Generate Excel" onclick="goTo('stockSummary.do?method=createColorPurityXL','','')" border="0"/> </td></tr>
  <tr><th>COLOR/PURITY</th>
  <%
 
  for(int i=0;i<purityList.size();i++)
  {
  %>
  <th colspan="2"><%=purityList.get(i) %></th>
  <%
  }
  %>
   <th colspan="2">Total</th>
  </tr>
  <tr><td></td>
  <%
   for(int i=0;i<=purityList.size();i++)
  {
   %>
  
  <td  width="50px">
  <span>Quantity</span>
  </td>
 
  <td  width="50px"><span>Cts</span></td>
  <%
  }
  %>
  </tr>
  
  <%
 
  for(int j=0;j<colorList.size();j++)
  {
  String color=(String)colorList.get(j);
  
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
  <td style="text-align:left;"><%=color%></td>
  <%
  
  for(int k=0;k<purityList.size();k++)
  {
  String purity=(String)purityList.get(k);
   HashMap QtyctsClrPurtbl= info.getQtyctsClrPurtbl();
   String qty_cts=util.nvl((String)QtyctsClrPurtbl.get(color+"_"+purity));
    String qty="";
   String cts="";
   if(!qty_cts.equals(""))
   {
   int indx=qty_cts.indexOf("|");
    qty=util.nvl(qty_cts.substring(0,indx));
    cts=util.nvl(qty_cts.substring(indx+1, qty_cts.length()));
    String idval="id"+cts;
     if(qty.contains("null"))
    {
    qty="0";
    }
     if(cts.contains("null"))
    {
    cts="0";
    }
%>
              <td align="center" width="50%"><span><a><%=qty%></a></span></td>
              <td align="center" width="50%"><span><a  title="Packet Details" href="stockSummary.do?method=pktDtl&status=<%=status%>&lab=<%=lab%>&shape=<%=shape%>&size=<%=size%>&color=<%=color%>&purity=<%=purity%>&group=<%=group%>" ><%=cts%></a></span></td>
             
 
   
   <%
   
  }
  else
  {
  %>
  <td> </td><td> </td>
  <%
  }

} 

   if(!(util.nvl((String)col_ctsqty.get(color))).equals(""))
   {
   String qty_cts=(String)col_ctsqty.get(color);
   int indx=qty_cts.indexOf("|");
  String  qty=util.nvl(qty_cts.substring(0,indx));
   String cts=util.nvl(qty_cts.substring(indx+1, qty_cts.length()));
   System.out.println("qty_cts"+qty_cts);
    if(qty.contains("null"))
    {
    qty="0";
    }
     if(cts.contains("null"))
    {
    cts="0";
    }
    %>
      <td  width="50%" align="center"><span><a > <%=qty%></a></span></td>
       <td width="50%"  align="center"><span ><a title="Packet Details"  href="stockSummary.do?method=pktDtl&status=<%=status%>&lab=<%=lab%>&shape=<%=shape%>&size=<%=size%>&color=<%=color%>&group=<%=group%>"  ><%=cts%></a></span></td>
  <% 
   }else
   {
   
%>
   <td> </td>  <td> </td>
   
   <%
   }
   
   System.out.println("shape"+color);
   %>
  
  
  </tr>
  <%
}  
  %>
 
  <tr><td>Total</td>
  <%
  for(int i=0;i<purityList.size();i++) 
  {
   String purity=util.nvl((String)purityList.get(i));
  String qtycts=util.nvl((String)clry_ctsqty.get(purity));
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

      <td  width="50%" align="center"><span><a  title="Shape-Size report"> <%=qty%></a></span></td>
      <td width="50%"  align="center"><span ><a   title="Packet Details" href="stockSummary.do?method=pktDtl&status=<%=status%>&lab=<%=lab%>&shape=<%=shape%>&size=<%=size%>&purity=<%=purity%>&group=<%=group%>"  ><%=cts%></a></span></td>
  <% 
  }
  else
  {
  %>
   <td> </td> <td> </td>
   
   <%
  }
 }

 if(!grdqty_cts.equals(""))
   {
 int indx=grdqty_cts.indexOf("|");
  String  qty=util.nvl(grdqty_cts.substring(0,indx));
   String cts=util.nvl(grdqty_cts.substring(indx+1, grdqty_cts.length()));
    if(qty.contains("null"))
    {
    qty="0";
    }
     if(cts.contains("null"))
    {
    cts="0";
    }
    %>
          <td  width="50%" align="center"><span><a  title="Shape-Size report"> <%=qty%></a></span></td>
                <td width="50%"  align="center"><span ><a   title="Packet Details"  ><%=cts%></a></span></td>
      
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