<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import="java.util.ArrayList,java.util.HashMap,java.sql.ResultSet,ft.com.*, java.util.Date"%>
<jsp:useBean id="db" class="ft.com.DBMgr" scope="session" />
<jsp:useBean id="log" class="ft.com.LogMgr" scope="session" />
<jsp:useBean id="util" class="ft.com.DBUtil" scope="session" />
<jsp:useBean id="info" class="ft.com.InfoMgr" scope="session" />
<jsp:useBean id="mail" class="ft.com.GenMail" scope="page" />
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>priceGrid</title>
      <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/prc_style.css"/>
      <script type="text/javascript"  src="<%=info.getReqUrl()%>/scripts/bse_prc.js"></script>
   
  </head>
  <body>
  <%
   HashMap mprp = info.getMprp();
   ResultSet rs = null;
   HashMap prp = info.getPrp();
   ArrayList colorPrt =(ArrayList)prp.get("COP");
   ArrayList puPrt = (ArrayList)prp.get("PUP");
   ArrayList prpLst = new ArrayList();
   prpLst.add("SH");
   prpLst.add("SZ");
   ArrayList hedPrp = new ArrayList();
     hedPrp.add("CT");
     hedPrp.add("FL");
     hedPrp.add("TINGE");
    ArrayList srchPrp = info.getSrchPrpLst();
   
     ArrayList isHedPrp = new ArrayList();
   for(int i =0 ; i<hedPrp.size() ; i++){
    String lprp = (String)hedPrp.get(i);
    String isCheck = util.nvl(request.getParameter(lprp));
    if(isCheck.equals("Y"))
      isHedPrp.add(lprp);
   }
   int indexOfPu = puPrt.indexOf("I3");
   int indexOfCo = colorPrt.indexOf("Q");
   String mainPrp = request.getParameter("mainPrp");
   String mainPrpSrt = request.getParameter(mainPrp);
   String dfltVal = "0" ;
     String dfltDsp = "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Select&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
  
  %>
   <table  cellpadding="5" cellspacing="5" border="0">
   <tr><td align="left" valign="top">
   <table cellpadding="0" class="info" align="left" cellspacing="5" border="0">
   <tr><td><a href="index.jsp"> BACK TO HOME</a></td></tr>
   <tr><td>
   <%=mainPrp%>:<%=mainPrpSrt%>&nbsp;&nbsp;,&nbsp;&nbsp; Shape:<%=request.getParameter("SH")%>&nbsp;&nbsp;,&nbsp;&nbsp;Size:<%=request.getParameter("SZ")%>
   </td></tr>
  <tr><td class="heder">&nbsp;</td></tr>
  <tr><td>
  <table cellpadding="0" cellspacing="1">
  <tr>
  <td><span class="textFont"> Color\purity </span></td>
  <%for(int n=0;n<indexOfPu;n++){
  
  %>
  <td align="center"><span class="textFont"> <%=(String)puPrt.get(n)%></span> </td>
  <%}%>
  </tr>
  <%for(int m=0;m<indexOfCo;m++){ %>
  <tr>
  <td><span class="textFont"><%=(String)colorPrt.get(m)%></span></td>
  <%for(int n=0;n<indexOfPu;n++){
  
  %>
  <td><input type="text" size="4" class="textBoxes"/> </td>
  <%}%>
  
  </tr>
  
  <%}%>
  </table>
  </td>
  </tr>
  
  </table>
  
  </td>
  <td align="left" valign="top" > 
 
  <%for(int i=0;i< isHedPrp.size();i++){
   String hdPrp = (String)isHedPrp.get(i);  
    String prpDsc = (String)mprp.get(hdPrp+"D");
    ArrayList prpPrt = (ArrayList)prp.get(hdPrp+"P");
    ArrayList prpSrt = (ArrayList)prp.get(hdPrp+"S");
    String tableId="Tbl"+hdPrp; 
  
  %>
  <table style="float:left"  >
  <tr><td colspan="2" align="center" class="hedinfo" onmouseover="showBox('<%=tableId%>')" ><%=prpDsc%></td></tr>
 <tr><td><table id="<%=tableId%>" style="display:none" class="hedinfo" cellspacing="1">
  <%for(int k=0;k<prpSrt.size();k++){
  String pPrt = (String)prpPrt.get(k);
  String pSrt = (String)prpSrt.get(k);
              
  %>
  <tr><td bgcolor="White"><%=pPrt%></td><td  bgcolor="White"><input type="text" class="textBoxes" size="4"/></td></tr>
  <%}%>
  </table>
 </td></tr>
   </table>
  <%}%>

  </td>
  
  </tr>

</table>
  </body>
</html>