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
    <title>Multi Lab Selection</title>
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
  <img src="../images/line.jpg" border="0" /></td><td valign="middle"> <span class="pgHed">Multi Lab Selection</span> </td>
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
  <html:form action="lab/multilab.do?method=view" method="post">
  <table  class="grid1">
  <tr><th> Packets</th></tr>
  <tr>
  <td valign="top">
  <html:textarea property="value(vnmLst)" rows="3" name="multiLabForm" />
  </td>
  </tr>
  <tr><td align="center"><html:submit property="view" value="View" styleClass="submit"/></td>
  </tr>
  </table>
  
 
  </html:form>
  </td></tr>
  <html:form action="lab/multilab.do?method=save" method="post" onsubmit="return validate_issue('CHK_' , 'count')">
   
  <tr>
  <td valign="top" class="tdLayout">
  
  <table>
 
  
   <%
   String view = (String)request.getAttribute("view");
   String ctwt=null;
   if(view!=null){
   ArrayList stkIdnLst = (ArrayList)session.getAttribute("multilabstkIdnLst");
   HashMap pktDtlMap =  (HashMap)session.getAttribute("multilabpktDtlMap");
   HashMap dbInfoSys = info.getDmbsInfoLst();
   String cnt = (String)dbInfoSys.get("CNT");
   if(stkIdnLst!=null && stkIdnLst.size() > 0){
    ArrayList vwPrpLst = (ArrayList)session.getAttribute("MULTI_LAB_VW");
    int vWsize = vwPrpLst.size()+3;
    HashMap mprp = info.getMprp();
    int sr = 0;
   %>
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
   <tr>
   <th>Sr</th>
   <th nowrap="nowrap"><input type="checkbox" name="checkAll" id="checkAll" onclick="checkALL('CHK_','count')" /></th>
   <th>Default Lab</th>
   <th>Lab A/IA</th>
        <th nowrap="nowrap" nowrap="nowrap">Packet No.</th>
        <%for(int j=0; j < vwPrpLst.size(); j++ ){
    String prp = (String)vwPrpLst.get(j);
     String hdr = (String)mprp.get(prp);
    String prpDsc = (String)mprp.get(prp+"D");
    if(hdr == null) {
        hdr = prp;
       }  

%>
<th title="<%=prpDsc%>" nowrap="nowrap"><%=hdr%></th>
<%}%> 
</tr>
 <%
 
 for(int i=0; i < stkIdnLst.size(); i++ ){
  String stkIdn=util.nvl((String)stkIdnLst.get(i));
  ArrayList pktList=(ArrayList)pktDtlMap.get(stkIdn);
  sr = i+1;
  for(int l=0;l<pktList.size();l++){
  String styleCode = "";
  HashMap stockPkt = (HashMap)pktList.get(l);
  String stt = util.nvl((String)stockPkt.get("stt"));
  String lab = util.nvl((String)stockPkt.get("lab"));
  String grp = util.nvl((String)stockPkt.get("grp"));
  if(l==0)
   styleCode = "style=\"color: Blue;\"";
  String cts = (String)stockPkt.get("CRTWT");
 String checkFldId = "CHK_"+sr;
 String labVal = "value(lab_" +stkIdn+ ")";
 String labIdn = "lab_"+stkIdn ;
 String onclick="Labselect(this,"+stkIdn+")";
  String checkFldVal = "value("+stkIdn+")";
 String rdFld = "value(RD_"+stkIdn+")";
 String rdVal = grp+"_"+lab;
 String checkFld = "value("+lab+"_"+stkIdn+"_"+grp+")";
 %>
<tr <%=styleCode%>>
<td>
<%if(l==0){%>
<%=sr%>
<%}%>
</td>
<td>
<%if(l==0){%>
<html:checkbox property="<%=checkFldVal%>"   styleId="<%=checkFldId%>" name="multiLabForm"  onclick="" value="yes" /> 
<%}%>
</td>
<td align="center"><html:radio property="<%=rdFld%>" value="<%=rdVal%>"  /> </td>
<td align="center"><html:checkbox property="<%=checkFld%>"   value="yes" />   </td>
<td><%=stockPkt.get("vnm")%></td>
<%for(int j=0; j < vwPrpLst.size(); j++ ){
    String prp = (String)vwPrpLst.get(j);
    %>
    <td><%=stockPkt.get(prp)%></td>
    <%}%>
        
</tr>
   <%}}%>
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