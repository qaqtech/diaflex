<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import="java.util.HashMap,java.util.ArrayList,java.util.List,java.util.ArrayList, java.sql.ResultSet"%>
<jsp:useBean id="db" class="ft.com.DBMgr" scope="session" />
<jsp:useBean id="logger" class="ft.com.LogMgr" scope="session" />
<jsp:useBean id="util" class="ft.com.DBUtil" scope="session" />
<jsp:useBean id="info" class="ft.com.InfoMgr" scope="session" />
<jsp:useBean id="mail" class="ft.com.GenMail" scope="page" />
<%@taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>refine search</title>
            <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/bse.js?v=<%=info.getJsVersion()%> " > </script>
  </head>
        <%String logId=String.valueOf(info.getLogId());
        String onfocus="cook('"+logId+"')";
        %>
 <body onfocus="<%=onfocus%>">
  <html:form action="/marketing/StockSearch.do?method=srch" method="POST">
  <table cellpadding="5" cellspacing="5">
   <tr><td>
   <table><tr><td>
   
   <html:submit property="value(refine_srch)"  value="Refine Search" />
  </td><td>
   <button type="button" name="close" onclick="disablePopupRult()" >Close</button></td></tr></table>
   </td></tr>
   
  <tr><td>
  <%
  
  ResultSet rs = null;
  HashMap prp = info.getPrp();
  HashMap prpHashTb = new HashMap();
  HashMap srtHashTb = new HashMap();
  HashMap mprp = info.getMprp();
  ArrayList vwPrpLst = info.getVwPrpLst();

  String isFncy = info.getIsFancy();  
  ArrayList horizontalPrp = new ArrayList();
  horizontalPrp.add("SHAPE");
  horizontalPrp.add("CRTWT");
  horizontalPrp.add("CLR");
  horizontalPrp.add("COL");
  horizontalPrp.add("FANCY COLOR");
  horizontalPrp.add("INTENSITY");
  horizontalPrp.add("OVERTONE");
  
  ArrayList removePrp = new ArrayList();
  if(isFncy.equals("FCY")){
    removePrp.add("COL");
    removePrp.add("COL-SHADE");
  }else{
    removePrp.add("INTENSITY");
    removePrp.add("OVERTONE");
    removePrp.add("FANCY COLOR");
  }
 
  ArrayList srchPrp = info.getRefPrpLst();
  %>
  <%if(info.getIsFancy().equals("N")){ %>
  <div>
   <table align="center" class="upTable" >
   <tr><th colspan="3"> Upgrading Rap % </th></tr>
   <tr><td align="center"><input type="checkbox" name="CLRBY" value="CLR"/>  By Clarity</td>
   <td align="center"><input type="checkbox" name="COLBY" value="COL"/>  By Color</td>
   <td align="center"><input type="checkbox" name="BOTHBY" value="BOTH"/>  By Both</td></tr>
   </table>
  </div>
  <%}%>
  <table class="refTable">
  <%
  int colCt = 0;
      
  for(int i=0;i<srchPrp.size();i++){
      ArrayList srchV =(ArrayList)srchPrp.get(i);
      String lprp = (String)srchV.get(0);
      String flg = (String)srchV.get(1);
      String dtaTyp = util.nvl((String)mprp.get(lprp + "T"));
      String prpDsc = (String)mprp.get(lprp+"D");
      ArrayList prpPrt = (ArrayList)prp.get(lprp+"P");
      ArrayList prpSrt = (ArrayList)prp.get(lprp+"S");
      ArrayList prpVal = (ArrayList)prp.get(lprp+"V"); 
            
      int colspan=1;
      Boolean dspSingle = Boolean.valueOf(false);
      if(horizontalPrp.indexOf(lprp) > -1) {
        dspSingle = Boolean.valueOf(true);
        colspan=3;
      } 
      if(dspSingle.booleanValue()) {%>
        <tr><td colspan="<%=colspan%>" align="center"><%=prpDsc%></td></tr>
      <%} 
        if((colCt == 0) || (dspSingle.booleanValue())) {%>
        <tr>
      <%}  
        if(dtaTyp.equals("N")) {%>
          <td align="left" valign="middle" colspan="<%=colspan%>">
          </td>
        <%}
      %>
      <%
      if(!dspSingle.booleanValue())
        ++colCt;
      if((colCt == 3) || (dspSingle.booleanValue())) {
        colCt = 0;
      %>
        </tr>
      <%}  
     }
   %>
   </table>
   </td></tr>
   <tr><td>
   <table><tr><td>
   <button type="submit" name="refine_srch" id="popupContactCloseRult" >Refine Search</button>
  </td><td>
   <button type="button" name="close" id="popupContactCloseRultClose" >Close</button></td></tr></table>
   </td></tr></table>
  </html:form>

  </body>
</html>