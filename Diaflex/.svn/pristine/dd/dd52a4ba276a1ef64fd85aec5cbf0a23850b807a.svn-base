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
    <title>Final Lab Selection</title>
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



<table width="100%"  border="0" align="center" cellpadding="0" cellspacing="0" class="mainbg">
  <tr>
    <td height="103" valign="top">
   <jsp:include page="/header.jsp" />
    
    </td>
  </tr>
 
  <tr>
    <td height="5" valign="top" class="greyline" ><img src="../images/grey_linebig.png" width="685" height="5" align="right" /></td>
  </tr>
  <tr>
  <td valign="top" class="hedPg">
  <table cellpadding="1" cellspacing="5"><tr><td valign="middle">
  <img src="../images/line.jpg" border="0" /></td><td valign="middle"> <span class="pgHed">Final Lab Selection</span> </td>
</tr></table>
  </td>
  </tr>
  <%
   String vnmNtFnd = (String)request.getAttribute("vnmNotFnd");
     if(vnmNtFnd!=null){%>
    <tr><td valign="top" class="tdLayout"><span class="redLabel"><%=vnmNtFnd%></span></td></tr>
   <%}%>
  <% String msg = (String)request.getAttribute("msg");
      if(msg!=null){
   %>
   <tr> <td valign="top" class="tdLayout"> <span class="redLabel"> <%=msg%></span>
   </td></tr><%}%>
   
   <tr>
  <td valign="top" class="tdLayout">
  <html:form action="lab/finalLabSelection.do?method=view" method="post">
  <table  class="grid1">
  <tr><th>Generic Search 
  <%
  ArrayList rolenmLst=(ArrayList)info.getRoleLst();
  String usrFlg=util.nvl((String)info.getUsrFlg());
  if(usrFlg.equals("SYS") || rolenmLst.contains("ADMIN")){
  %>
  <img src="../images/edit.jpg" title="Edit Properties Module" onclick="newWindow('<%=info.getReqUrl()%>/dynamicProperty.do?method=load&mdl=LB_SRCH&sname=LBGNCSrch&par=GENERIC')" border="0" width="15px" height="15px"/> 
  <%}%>
  </th><th> Packets</th></tr>
  <tr><td>
   <jsp:include page="/genericSrch.jsp"/>
  </td><td valign="top">
  <html:textarea property="value(vnmLst)" rows="10" name="finalLabSelectionForm" />
  </td></tr>
  <tr><td  colspan="2" align="center"><html:submit property="view" value="View" styleClass="submit"/>
   <html:submit property="viewAll" value="View All" styleClass="submit"/> </td>
  </tr>
  </table>
  
 
  </html:form>
  </td></tr>
  <html:form action="lab/finalLabSelection.do?method=save" method="post" onsubmit="return validate_issue('CHK_' , 'count')">
   
  <tr>
  <td valign="top" class="tdLayout">
  
  <table>
 
  
   <%
   String view = (String)request.getAttribute("view");
   String ctwt=null;
   if(view!=null){
   ArrayList stockList = (ArrayList)session.getAttribute("StockList");
   HashMap dbInfoSys = info.getDmbsInfoLst();
   String cnt = (String)dbInfoSys.get("CNT");
   if(stockList!=null && stockList.size() > 0){
    ArrayList vwPrpLst = (ArrayList)session.getAttribute("asViewLst");
    int vWsize = vwPrpLst.size()+3;
    HashMap labMap =  (HashMap)session.getAttribute("labMap");
     HashMap totals = (HashMap)request.getAttribute("totalMap");
    ArrayList options = (ArrayList)request.getAttribute("OPTIONS");
    HashMap mprp = info.getMprp();
    int sr = 0;
    if(options == null)
        options = new ArrayList();
   %>
    <tr><td>
   <table>
   <tr><td>Total :&nbsp;&nbsp;</td><td><span id="ttlqty"><%=totals.get("qty")%></span> &nbsp;&nbsp;</td><td>cts&nbsp;&nbsp;</td><td><span id="ttlcts"> <%=totals.get("cts")%></span> &nbsp;&nbsp;</td><td>Selected:&nbsp;&nbsp;</td><td> Total :&nbsp;&nbsp;</td> <td><span id="qtyTtl">0&nbsp;&nbsp;</span></td><td>Cts&nbsp;&nbsp;</td><td><span id="ctsTtl">0</span> </td> </tr>
  
   </table>
   </td></tr>
    <tr>
  <td><table><tr><td>
  <html:submit property="value(pb_lab)" value="Save Changes"  styleClass="submit" />
  </td>
  <%if(cnt.equals("sd")){%>
  <td><div class="Blue">&nbsp;</div> </td>
  <td nowrap="nowrap">&nbsp;Marketing Available&nbsp;  </td>
  <%}%>
  </tr></table>
  </tr>
  
   <tr><td>
   <table class="grid1" id="dataTable">
   <tr><th nowrap="nowarp"><input type="checkbox" name="checkAll" id="checkAll" onclick="checkALL('CHK_','count')" /></th> <th>Sr</th>
        <th nowrap="nowrap">Packet No.</th>
        <%for(int j=0; j < vwPrpLst.size(); j++ ){
    String prp = (String)vwPrpLst.get(j);
     String hdr = (String)mprp.get(prp);
    String prpDsc = (String)mprp.get(prp+"D");
    if(hdr == null) {
        hdr = prp;
       }  

%>
<th title="<%=prpDsc%>"  nowrap="nowarp"><%=hdr%></th>

<%}%> 
<th nowrap="nowrap"><div class="float">Status</div> <div class="float">
<%if(options.size()>0){ %>
<%
for(int j=0; j < options.size(); j++) {


String lopt = (String)options.get(j);

%>
<div class="float"><input type="radio" name="STT" id="<%=lopt%>" onclick="ALLRedio('<%=lopt%>','<%=lopt%>_')" />&nbsp;<%=lopt%></div>
<%}
}%></div>
</th>
<th nowrap="nowarp">LAB

</th>
</tr>
 <%
 
 for(int i=0; i < stockList.size(); i++ ){
  String styleCode = "";
 HashMap stockPkt = (HashMap)stockList.get(i);
 String stkIdn = (String)stockPkt.get("stk_idn");
  String stt = util.nvl((String)stockPkt.get("stt"));
 ArrayList labList = (ArrayList)labMap.get(stkIdn);
 sr = i+1;
  if(stt.equals("MKAV"))
   styleCode = "style=\"color: Blue;\"";
  String cts = (String)stockPkt.get("cts");
 String checkFldId = "CHK_"+sr;
 String labVal = "value(lab_" +stkIdn+ ")";
 String labIdn = "lab_"+stkIdn ;
 String onclick="Labselect(this,"+stkIdn+")";
 String target = "src_"+stkIdn;
  String checkFldVal = "value("+stkIdn+")";
  String onclickCh = "AssortTotalCal(this,"+cts+",'','')";
 %>
<tr <%=styleCode%>>
<td nowrap="nowarp"><html:checkbox property="<%=checkFldVal%>"   styleId="<%=checkFldId%>" name="finalLabSelectionForm"  onclick="<%=onclickCh%>" value="yes" /> </td>
<td nowrap="nowarp"><%=sr%></td>
<td nowrap="nowarp"><a href="finalLabSelection.do?method=labDtl&mstkIdn=<%=stkIdn%>&mdl=LAB_VIEW" onclick="setBGColor('<%=stkIdn%>','DV_')" target="<%=target%>"> <%=stockPkt.get("vnm")%> </a></td>
<%for(int j=0; j < vwPrpLst.size(); j++ ){
    String prp = (String)vwPrpLst.get(j);
   
    %>
    <td nowrap="nowarp"><%=stockPkt.get(prp)%></td>
    <%
    
}%>
<td><table><tr><%
for(int j=0; j < options.size(); j++) {%>

<%
String lopt = (String)options.get(j);
String rfId = lopt+"_"+stkIdn;
String rfldNme = "value(STT_"+ stkIdn + ")";
%>
<td nowrap="nowarp"><html:radio property="<%=rfldNme%>" styleId="<%=rfId%>"  value="<%=lopt%>"/>&nbsp;<%=lopt%></td>
<%}
%>
</tr></table>

</td>
<td><table><tr>
<%if(labList !=null && labList.size() > 0){

for(int l=0;l<labList.size();l++){
String isChecked = "";  
labDet lab = (labDet)labList.get(l);
String dsc = lab.getLabDesc();
String grp = lab.getGrp();
String checkFld = "value("+dsc+"_"+stkIdn+"_"+grp+")";
 String rdFld = "value(RD_"+stkIdn+")";
 String rdVal = grp+"_"+dsc;
if(l==0)
isChecked = "checked=\"checked\"";  
%>
<td><%=dsc%></td><td><html:radio property="<%=rdFld%>" value="<%=rdVal%>"  /> </td>
<td><html:checkbox property="<%=checkFld%>"   value="yes" />   </td>
<%}}%>



</tr></table>

</td>
         
</tr>
<tr><td colspan="<%=vWsize%>"  style="display:none;" id="DV_<%=stkIdn%>" >
<iframe name="<%=target%>" height="320" width="800" frameborder="0">

</iframe>
</td></tr>
   <%}%>
   </table>
    <input type="hidden" name="count" id="count" value="<%=sr%>" />
   </td></tr>
   
   
  
   <%}else{%>
   <tr>
   <td>Sorry Result not found </td></tr>
   <%}}%>
  
  
  
  
  
  </table>
 
  </td></tr>
  </html:form>
   <tr>
    <td><jsp:include page="/footer.jsp" /> </td>
    </tr>
  </table>
  </body>
</html>