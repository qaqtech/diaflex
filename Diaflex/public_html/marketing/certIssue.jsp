<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page errorPage="../error_page.jsp" %>
<%@ page contentType="text/html;charset=windows-1252"%>
<jsp:useBean id="info" class="ft.com.InfoMgr" scope="session" />
<jsp:useBean id="gtMgr" class="ft.com.GtMgr" scope="session" />

<jsp:useBean id="util" class="ft.com.JspUtil" scope="page" />
<%@ page import="java.util.ArrayList,java.util.LinkedHashMap, java.util.Set,java.util.HashMap,java.sql.ResultSet,ft.com.*, java.util.Date, java.util.ArrayList, ft.com.dao.*"%>
<%@taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>Certificate Issue</title>
    <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/bse.css?v=<%=info.getJsVersion()%>"/>
    <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/general.css?v=<%=info.getJsVersion()%>"/>
     <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/style.css?v=<%=info.getJsVersion()%>"/>
      <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/bse.js?v=<%=info.getJsVersion()%> " > </script>
       <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/assortScript.js?v=<%=info.getJsVersion()%> " > </script>
              <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/newCalScript.js?v=<%=info.getJsVersion()%> " > </script>

  </head>
        <%String logId=String.valueOf(info.getLogId());
        String onfocus="cook('"+logId+"')";
        %>
 <body onfocus="<%=onfocus%>" onload="MM_preloadImages('images/faunalogo.png')" topmargin="0" leftmargin="0" rightmargin="0" bottommargin="0">
<%
String lstNme = (String)gtMgr.getValue("lstNmeCRT");
String method = util.nvl(request.getParameter("method"));
HashMap stockListMap = new HashMap();
boolean disabled= false;
if(method.equals("fecth")){
if(lstNme!=null)
stockListMap = (HashMap)gtMgr.getValue(lstNme);
if(stockListMap!=null && stockListMap.size()>0)
disabled= true;
}

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
  <img src="../images/line.jpg" border="0" /></td><td valign="middle"> <span class="pgHed">Process Issue</span> </td>
</tr></table>
  </td>
  </tr>
  <%
   String msg = (String)request.getAttribute("msg");
   if(msg!=null){
     %>
    <tr><td valign="top" class="tdLayout"><span class="redLabel"><%=msg%></span></td></tr>
   <%}%>
    <%
   String vnmNtFnd = (String)request.getAttribute("vnmNotFnd");
     if(vnmNtFnd!=null){%>
    <tr><td valign="top" class="tdLayout"><span class="redLabel"><%=vnmNtFnd%></span></td></tr>
   <%}%>
   <tr>
   
   <td valign="top" class="tdLayout">
   <table><tr><td>
   <html:form action="marketing/certIssueAction.do?method=fecth" method="post" onsubmit="return validate_assort()">
  <table  class="grid1">
  <tr><th> </th><th>Generic Search 
  <%
  ArrayList rolenmLst=(ArrayList)info.getRoleLst();
  String usrFlg=util.nvl((String)info.getUsrFlg());
  if(usrFlg.equals("SYS") || rolenmLst.contains("ADMIN")){
  %>
  <img src="../images/edit.jpg" title="Edit Properties Module" onclick="newWindow('<%=info.getReqUrl()%>/dynamicProperty.do?method=load&mdl=MKT_SRCH&sname=asGNCSrch&par=GENERIC')" border="0" width="15px" height="15px"/> 
  <%}%>
  </th></tr>
  <tr><td valign="top">
   <table><tr><td>Process </td>
   <td>
   <html:select property="value(mprcIdn)"  styleId="mprcIdn" name="certIssueFrom" disabled="<%=disabled%>"   >
            <html:optionsCollection property="processLst" name="certIssueFrom" 
            label="prc" value="mprcId" />
    </html:select>
   </td></tr>
   <tr>
    <td>
    Buyer : </td>
   <td>
  <html:text  property="value(partytext)" name="certIssueFrom" styleId="partytext" style="width:180px" styleClass="sugBox" onkeypress="return disableEnterKey(event);" 
           onkeyup="doCompletion('partytext', 'nmePop', '../ajaxAction.do?1=1', event)"
      onkeydown="setDownSerchpage('nmePop', 'party', 'partytext',event)" onclick="document.getElementById('partytext').autocomplete='off'"  />
      <img src="../images/addrow.jpg" width="17" height="17" title="Click To get Names" onclick="doCompletionMobile('partytext', 'nmePop', '../ajaxAction.do?1=1')"/>
      <html:hidden property="value(empIdn)" styleId="party"  />
  <div style="position: absolute;">
      <select id="nmePop" name="nmePop"
        style="display:none;300px;" 
        class="sugBoxDiv" 
        onKeyDown="setDownSerchpage('nmePop', 'party', 'partytext',event)" 
        onDblClick="setVal('nmePop', 'party', 'partytext', event);hideObj('nmePop')" 
        onClick="setVal('nmePop', 'party', 'partytext', event)" 
        size="10">
      </select>
</div>            
   </td>
   </tr>
  
   <tr>
   <td>Packet No/Cert No :</td><td> <html:textarea property="value(vnmLst)" rows="7"  cols="30" name="certIssueFrom"  /></td>
   </tr>
   <tr><td>
   <html:select property="value(TYP)" name="certIssueFrom">
   <html:option value="MEMO">MEMO</html:option>
    <html:option value="SALE">SALE</html:option>
    <html:option value="AP">APPROVE</html:option>
    <html:option value="DLV">DELIVERY</html:option>
   </html:select>
   </td><td> <html:text property="value(idn)" name="certIssueFrom" /> </td></tr>
  </table></td>
   <td>
   
    <jsp:include page="/genericSrch.jsp"/>
   </td></tr>
   <tr><td colspan="2" align="center"><html:submit property="value(srch)" value="Fetch" styleClass="submit" /></td> </tr>
   </table>
   </html:form>
   </td></tr>
   
   <%
   String view = (String)request.getAttribute("view");
   if(view!=null){
   ArrayList prpDspBlocked = info.getPageblockList();
   if(stockListMap!=null && stockListMap.size()>0){
    ArrayList vwPrpLst = (ArrayList)session.getAttribute("mktViewLst");
    HashMap totals = (HashMap)gtMgr.getValue(lstNme+"_TTL");
    HashMap mprp = info.getMprp();
    int sr = 0;
   %>
   <tr><td>
   <table>
   <tr><td>Total :&nbsp;&nbsp;</td><td><span id="ttlqty"> <%=totals.get("MQ")%>&nbsp;&nbsp;</span></td><td>cts&nbsp;&nbsp;</td><td><span id="ttlcts"><%=totals.get("MW")%>&nbsp;&nbsp;</span></td><td>Selected:&nbsp;&nbsp;</td><td> Total :&nbsp;&nbsp;</td> <td><span id="qtyTtl">0&nbsp;&nbsp;</span></td><td>Cts&nbsp;&nbsp;</td><td><span id="ctsTtl">0</span> </td> </tr>
  
   </table>
   </td></tr>
   <html:form action="marketing/certIssueAction.do?method=Issue" method="post" onsubmit="return validate_issue('CHK_' , 'count')">
   <html:hidden property="value(prcId)" name="certIssueFrom" />
   <html:hidden property="value(empId)" name="certIssueFrom" />
      <html:hidden property="value(lstNme)" name="certIssueFrom" value="<%=lstNme%>" />

   
   <tr><td>
   <table><tr><td>
   <html:submit property="value(issue)" styleClass="submit" value="Issue" /></td> 
   <%
  ArrayList rolenmLst=(ArrayList)info.getRoleLst();
  String usrFlg=util.nvl((String)info.getUsrFlg());
  if(usrFlg.equals("SYS") || rolenmLst.contains("ADMIN")){
  %>
  <td><img src="../images/edit.jpg" title="Edit Properties Module" onclick="newWindow('<%=info.getReqUrl()%>/dynamicProperty.do?method=load&mdl=AS_VIEW&sname=asViewLst&par=A')" border="0" width="15px" height="15px"/> </td>
  <%}%>
  </tr></table>
  </td>
   </tr>
   <tr><td>
   <table class="grid1">
   <tr> <th><input type="checkbox" name="checkAll" id="checkAll" onclick="ALLCheckBoxCal('checkAll','CHK_')" /> </th><th>Sr</th>
        <th>Packet No.</th>
      
    <%for(int j=0; j < vwPrpLst.size(); j++ ){
    String prp = (String)vwPrpLst.get(j);
    if(prpDspBlocked.contains(prp)){
       }else{
     String hdr = util.nvl((String)mprp.get(prp));
    String prpDsc = util.nvl((String)mprp.get(prp+"D"));
    if(hdr == null) {
        hdr = prp;
       }  
%>
<th title="<%=prpDsc%>"><%=prp%></th>
<%}}%>       
</tr>
 <%
 ArrayList stockIdnLst =new ArrayList();
 LinkedHashMap stockList = new LinkedHashMap(stockListMap); 

 Set<String> keys = stockList.keySet();
        for(String key: keys){
       stockIdnLst.add(key);
        }

 for(int i=0; i < stockIdnLst.size(); i++ ){
  String stkIdn = (String)stockIdnLst.get(i);
 GtPktDtl stockPkt = (GtPktDtl)stockList.get(stkIdn);
 String cts = (String)stockPkt.getCts();
 String onclick = "CalTtlOnChlick("+stkIdn+" , this , 'NO' )";
 sr = i+1;
 String checkFldId = "CHK_"+sr;
 String checkFldVal = "cb_stk_"+stkIdn ;
 %>
<tr>
<td><input type="checkbox" name="<%=checkFldVal%>" id="<%=checkFldId%>" value="<%=stkIdn%>" onclick="<%=onclick%>" /></td>
<td><%=sr%></td>
<td><%=stockPkt.getVnm()%><input type="hidden" id="CTS_<%=stkIdn%>"  value="<%=cts%>" /></td>

<%for(int j=0; j < vwPrpLst.size(); j++ ){
    String prp = (String)vwPrpLst.get(j);
    if(prpDspBlocked.contains(prp)){
       }else{
    %>
    <td><%=stockPkt.getValue(prp)%></td>
<%}}%>
</tr>
   <%}%>
   </table></td></tr>
   <input type="hidden" name="count" id="count" value="<%=sr%>" />
   </html:form>
   <%}
   else{%>
   <tr><td>Sorry no result found </td></tr>
   <%}
   }%>
   
   </table>
   
  
  
   <tr>
    <td><jsp:include page="/footer.jsp" /> </td>
    </tr>
  </table>

  </body>
</html>