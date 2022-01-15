<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page errorPage="../error_page.jsp" %>
<%@ page contentType="text/html;charset=windows-1252"%>
<jsp:useBean id="info" class="ft.com.InfoMgr" scope="session" />
<jsp:useBean id="util" class="ft.com.JspUtil" scope="page" />

<%@ page import="java.util.ArrayList,java.util.HashMap,java.sql.ResultSet,ft.com.*, java.util.Date, java.util.ArrayList, ft.com.dao.*"%>
<%@taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>Stone Transfer</title>
    <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/bse.css?v=<%=info.getJsVersion()%>"/>
    <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/general.css?v=<%=info.getJsVersion()%>"/>
     <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/style.css?v=<%=info.getJsVersion()%>"/>
<script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/bse.js?v=<%=info.getJsVersion()%> " > </script>
       <script src="../scripts/assortScript.js" type="text/javascript"></script>
  </head>
        <%String logId=String.valueOf(info.getLogId());
        String onfocus="cook('"+logId+"')";
        %>
  <body onfocus="<%=onfocus%>" onload="MM_preloadImages('images/faunalogo.png')" topmargin="0" leftmargin="0" rightmargin="0" bottommargin="0">
<%
 int accessidn=Integer.parseInt(util.nvl((String)request.getAttribute("accessidn"),"0"));
HashMap prpLst = info.getPrp();
%>

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
  <img src="../images/line.jpg" border="0" /></td><td valign="middle"> <span class="pgHed">Stone Transfer</span> </td>
</tr></table>
  </td>
  </tr>
  <% String msg = (String)request.getAttribute("msg");
      if(msg!=null){
   %>
   <tr> <td valign="top" class="tdLayout"> <span class="redLabel"> <%=msg%></span>
   </td></tr><%}%>
    <tr>
  <td valign="top" class="hedPg">
   <html:form action="box/boxToBoxSje.do?method=fetch" method="post">
  <table  class="grid1">
  <tr><th>Generic Search 
  <%
  ArrayList rolenmLst=(ArrayList)info.getRoleLst();
  String usrFlg=util.nvl((String)info.getUsrFlg());
  if(usrFlg.equals("SYS") || rolenmLst.contains("ADMIN")){
  %>
  <img src="../images/edit.jpg" title="Edit Properties Module" onclick="newWindow('<%=info.getReqUrl()%>/dynamicProperty.do?method=load&mdl=LB_SRCH&sname=LBGNCSrch&par=GENERIC')" border="0" width="15px" height="15px"/> 
  <%}%>
  </th><th>Packet</th></tr>
  
  <tr><td valign="top">
  
   <jsp:include page="/genericSrch.jsp"/>
  </td> <td valign="top">
 <html:textarea property="value(vnmLst)" rows="10" name="boxtoBoxSjeForm" /></td> </tr>
  <tr><td align="center"><html:submit property="view" value="View" styleClass="submit"/>
   </td>
  </tr></table>
  </html:form>
  </td></tr>
   <html:form action="box/boxToBoxSje.do?method=save"  method="post" onsubmit="return validate_issue('CHK_' , 'count')">
  <%
  String view  = (String)request.getAttribute("view");
    ArrayList stockList = (ArrayList)session.getAttribute("StockList");
    if(view!=null){
   if(stockList!=null && stockList.size()>0){
    ArrayList vwPrpLst = (ArrayList)session.getAttribute("boxViewLst");
     HashMap mprp = info.getMprp(); 
     HashMap prp = info.getPrp();
     HashMap totals = (HashMap)session.getAttribute("totalMap");
    
  %>
    <tr><td valign="top" class="tdLayout">
   <table>
   <tr><td>
     Total :&nbsp;&nbsp;
     </td>
     <td><span id="ttlqty"><%=totals.get("qty")%></span> &nbsp;&nbsp;</td> 
     <td>cts&nbsp;&nbsp;</td>
     <td><span id="ttlcts"><%=totals.get("cts")%></span> &nbsp;&nbsp;</td>
     <td>Selected:&nbsp;&nbsp;</td>
     <td> Total :&nbsp;&nbsp;</td>
     <td><span id="qtyTtl">0&nbsp;&nbsp;</span></td>
     <td>Cts&nbsp;&nbsp;</td>
     <td><span id="ctsTtl">0</span>
  </td></tr>
   </table>
   </td></tr> 
    <tr><td valign="top" class="tdLayout">
    <table><tr><td>
    <html:submit property="value(pb_lab)" value="Save Changes" onclick="" styleClass="submit" /></td>
    <td> 
     
   <html:select property="value(comBoxTyp)" onchange="setLabToLabLst(this , 'box_')" name="boxtoBoxSjeForm" >
       <html:option value="0">---select---</html:option>
        <html:optionsCollection property="value(boxTypLst)" name="boxtoBoxSjeForm" label="boxVal" value="boxVal" />

    </html:select></td></tr></table>
    </td></tr>
    <tr><td valign="top" class="tdLayout">
    
    <table class="grid1" id="dataTable">
   <tr> <th>Sr</th>
   <th>Select All<input type="checkbox" name="checkAll" id="checkAll" onclick="checkALL('CHK_','count')"  /></th>
    <th>Packet No.</th>
   <%for(int j=0; j < vwPrpLst.size(); j++ ){
    String lprp = (String)vwPrpLst.get(j);
    String hdr = (String)mprp.get(lprp);
    String prpDsc = (String)mprp.get(lprp+"D");
  %>
   <th title="<%=prpDsc%>"><%=hdr%></th>
  <%}%>
  <th>Box Type</th>
  </tr>

  <% int sr =0 ;
   for(int i=0; i < stockList.size(); i++ ){
     sr=i+1;
   String styleCode = "";
   HashMap stockPkt = (HashMap)stockList.get(i);
   String stkIdn = (String)stockPkt.get("stk_idn");
   String cts = (String)stockPkt.get("cts");
   String target = "SC_"+stkIdn;
   String checkFldId = "CHK_"+sr;
     String checkFldNme = "value(CHK_"+sr+")";
   String onclick="Labselect(this,"+stkIdn+")";
   String boxNme = "value(box_" +stkIdn+ ")";
 %>
 <tr <%=styleCode%> id="<%=target%>">
 <td><%=sr%><input type="hidden" id="CTS_<%=stkIdn%>"  value="<%=cts%>" /></td>
 <td align="center"><html:checkbox property="<%=checkFldNme%>" styleId="<%=checkFldId%>" name="boxtoBoxSjeForm" onclick="AssortTotalCal(this,'<%=cts%>','','<%=stkIdn%>')"  value="yes" /></td>
 <td><%=stockPkt.get("vnm")%></td>
 <%for(int j=0; j < vwPrpLst.size(); j++ ){
    String lprp = (String)vwPrpLst.get(j);
   %>
  <td><%=stockPkt.get(lprp)%></td>
   <%}%>
   <td>
   <html:select property="<%=boxNme%>" styleId="<%=boxNme%>" name="boxtoBoxSjeForm" >
     <html:optionsCollection property="value(boxTypLst)" name="boxtoBoxSjeForm" label="boxVal" value="boxVal" />
    </html:select>
   </td>
 </tr>
 
 <%}%>
 <tr><td>
 <input type="hidden" id="count" name="count" value="<%=sr%>" /></td></tr>
 <%}}%>
  


</table> 
     
     </td></tr>
  </html:form>


  
  <tr><td>

  </td></tr>
  
  
  <tr>
<td><jsp:include page="/footer.jsp" /> </td>
</tr>
  </table>
  
  

  </body>
</html>