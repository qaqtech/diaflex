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
    <title>Lot Merge</title>
    <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/bse.css?v=<%=info.getJsVersion()%>"/>
    <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/general.css?v=<%=info.getJsVersion()%>"/>
     <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/style.css?v=<%=info.getJsVersion()%>"/>
      <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/bse.js?v=<%=info.getJsVersion()%> " > </script>
  </head>
        <%String logId=String.valueOf(info.getLogId());
        String onfocus="cook('"+logId+"')";
        HashMap allPageDtl= (info.getPageDetails() == null)?new HashMap():(HashMap)info.getPageDetails();
        HashMap pageDtl=(HashMap)allPageDtl.get("LOT_MERGE");
        ArrayList pageList=new ArrayList();
        HashMap pageDtlMap=new HashMap();
        String fld_nme="",fld_ttl="",val_cond="",dflt_val="",fld_typ="",form_nme="",flg1="";
        %>
 <body onfocus="<%=onfocus%>" onload="MM_preloadImages('images/faunalogo.png')" topmargin="0" leftmargin="0" rightmargin="0" bottommargin="0">
<table width="100%"  border="0" align="center" cellpadding="0" cellspacing="0" class="mainbg">
  <tr>
    <td height="103" valign="top">
   <jsp:include page="/header.jsp" /></td></tr>
  <tr>
    <td height="5" valign="top" class="greyline"><img src="../images/grey_linebig.png" width="685" height="5" align="right" /></td>
  </tr>
  <tr>
  <td valign="top" class="hedPg">
  <table cellpadding="1" cellspacing="5"><tr><td valign="middle">
  <img src="../images/line.jpg" border="0" /></td><td valign="middle"> <span class="pgHed">Lot Merge</span> </td>
</tr></table>
  </td>
  </tr><tr>
   <td valign="top" class="tdLayout">
     <html:form action="mix/lotMerge.do?method=loadGrid" method="post" >
  <table  class="grid1">
  <tr><th>Generic Search <%
  ArrayList rolenmLst=(ArrayList)info.getRoleLst();
  String usrFlg=util.nvl((String)info.getUsrFlg());
  if(usrFlg.equals("SYS") || rolenmLst.contains("ADMIN")){
  %>
  <img src="../images/edit.jpg" title="Edit Properties Module" onclick="newWindow('<%=info.getReqUrl()%>/dynamicProperty.do?method=load&mdl=LOTMRG_SRCH&sname=LOTMRG_SRCH&par=GENERIC')" border="0" width="15px" height="15px"/> 
  <%}%> </th></tr>
  <tr><td>Status: &nbsp;&nbsp;
  <%
   pageList= ((ArrayList)pageDtl.get("STT") == null)?new ArrayList():(ArrayList)pageDtl.get("STT");
      if(pageList!=null && pageList.size() >0){%>
  <html:select property="value(stt)"  styleId="stt" name="lotMergeForm"  >
     <% for(int j=0;j<pageList.size();j++){
      pageDtlMap=(HashMap)pageList.get(j);
      fld_nme=(String)pageDtlMap.get("fld_nme");
      fld_ttl=(String)pageDtlMap.get("fld_ttl");
  %>
  <html:option value="<%=fld_nme%>"><%=fld_ttl%></html:option>
  <%}%>
  </html:select>
  <%}else{%>
  <html:hidden property="value(stt)"  styleId="stt" name="lotMergeForm" value="LOT_MRG_AV@MKAV"  />
  <%}%>
  </td></tr>
  
  <%
   pageList= ((ArrayList)pageDtl.get("PARTY") == null)?new ArrayList():(ArrayList)pageDtl.get("PARTY");
    if(pageList!=null && pageList.size() >0){%>
  <tr><td>
  <span class="txtBold" >&nbsp;Party:&nbsp;&nbsp; </span>
              
     <html:text  property="value(partytext)"  styleId="partytext" style="width:180px" styleClass="sugBox" onkeypress="return disableEnterKey(event);" 
           onkeyup="doCompletionPartyNME('partytext', 'nmePop', '../ajaxAction.do?1=1&isSrch=Y&UsrTyp=BUYER', event)"
      onkeydown="setDownSerchpage('nmePop', 'party', 'partytext',event)" onclick="document.getElementById('partytext').autocomplete='off'"  />
      <img src="../images/addrow.jpg" width="17" height="17" title="Click To get Names" onclick="doCompletionMobilePartyNME('partytext', 'nmePop', '../ajaxAction.do?1=1&isSrch=Y&UsrTyp=BUYER')">
      <img src="../images/Delete.png" width="15" height="15" title="Click To Clear Names List" onclick="clearsugBoxDiv('partytext','party','nmePop','SRCH');">
      <html:hidden property="value(party)" styleId="party"  />
  <div style="position: absolute;">
      <select id="nmePop" name="nmePop"
        style="display:none;300px;" 
        class="sugBoxDiv" 
        onKeyDown="setDownSerchpage('nmePop', 'party', 'partytext',event)" 
        onDblClick="setVal('nmePop', 'party', 'partytext', event);hideObj('nmePop'),getTrmsNME('party','SRCH');" 
        onClick="setVal('nmePop', 'party', 'partytext', event)" 
        size="10">
      </select>
</div>            

  </td></tr>
  <%}%>
  <tr>
   <td>
    <jsp:include page="/genericSrch.jsp"/>
   </td></tr>
   <tr><td align="center">
   <html:submit property="value(srch)" value="Fetch" styleClass="submit" />
   &nbsp;&nbsp;  <html:submit property="value(fetchall)" value="Fetch All" styleClass="submit" />
   </td> </tr>
   </table>
   </html:form>
   </td></tr>
    <%
   
   ArrayList msgLst = (ArrayList)request.getAttribute("msgLst");
   if(msgLst!=null){
   for(int i=0;i<msgLst.size();i++){
   String msg = (String)msgLst.get(i);
   %>
    <tr><td valign="top" class="tdLayout"><span class="redLabel"><%=msg%></span></td></tr>
   <%}}%>
   <tr><td height="20">&nbsp;</td></tr>
   <%
    HashMap prp = info.getPrp();
    ArrayList lotList = (ArrayList)session.getAttribute("LOTLIST");
    ArrayList pktDtlList = (ArrayList)request.getAttribute("pktDtlList");
    HashMap pktTTLMap = (HashMap)request.getAttribute("PKTTTLMAP");
    String view  = (String)request.getAttribute("view");
    if(view!=null){
      ArrayList shVal = (ArrayList)prp.get("MIX_SHAPEV");
      if(shVal==null)
      shVal = (ArrayList)prp.get("SHAPEV");
    if(lotList!=null && lotList.size()>0){
    String lprpStr = (String)request.getAttribute("lprpStr");
    String lprpValStr = (String)request.getAttribute("lprpValStr");
    %>
   <tr>
   <td valign="top" class="tdLayout">
    <html:form action="mix/lotMerge.do?method=save" method="post"   >
    <input type="hidden" id="lprpStr" name="lprpStr" value="<%=lprpStr%>"  />
     <input type="hidden" id="lprpValStr" name="lprpValStr" value="<%=lprpValStr%>"  />
     <html:hidden property="value(INSTT)"  styleId="instt" name="lotMergeForm"   />
     <html:hidden property="value(OUTSTT)"  styleId="outstt" name="lotMergeForm"   />
   <table>
   <tr><td valign="top">
   
  <table><tr><td>
   Employee :
 &nbsp;
  <html:select property="value(empIdn)"  styleId="empIdn" name="lotMergeForm"  >
           <html:option value="0" >--select--</html:option>
            <html:optionsCollection property="empList" name="lotMergeForm" 
            label="emp_nme" value="emp_idn" />
 </html:select>
           
  </td><td>
   <html:submit property="value(submit)" value="Merge" styleClass="submit" />
   </td><td>Selected </td><td> QTY :</td><td><label id="selectQty">0</label> </td>
   <td> Cts:</td><td><label id="selectCts">0</label> </td>
   </tr></table>
   
   </td></tr>
    <tr><td valign="top">
     <table  class="grid1" cellpadding="1">
     <tr><th>Merge<input type="checkbox" name="All" onclick="CheckBOXALLForm('1',this,'merge_');AlllotTotalCts(this)"/></th><th>Lot No</th><th>Shape</th><th>QTY</th> <th>CTS</th><th>AVGPRC</th><th>VALUE</th></tr>
     <%
     HashMap ttlsDtls = (HashMap)pktTTLMap.get("GTTTL");
     for(int i=0;i<lotList.size();i++){
     String lotNo = (String)lotList.get(i);
     String fldId="merge_"+i;
     boolean isDis=true;
     for(int j=0;j<shVal.size();j++){
     String sh = (String)shVal.get(j);
     HashMap avgDtls = (HashMap)pktTTLMap.get(sh+"_"+lotNo);
     if(avgDtls!=null && avgDtls.size()>0){
     %>
     <%if(isDis){
     HashMap lotDtls = (HashMap)pktTTLMap.get(lotNo);
     %>
      <tr>
      <td> <input type="checkbox" name="<%=fldId%>" id="<%=fldId%>" value="<%=lotNo%>" onclick="lotTotalCts('<%=i%>',this)"/></td>
       <td><%=lotNo%></td><td></td><td align="right"><b> <%=lotDtls.get("QTY")%></b>  <input type="hidden" name="qty_<%=i%>" id="qty_<%=i%>" value="<%=lotDtls.get("QTY")%>" /></td>
       <td align="right"><b> <%=lotDtls.get("CTS")%></b>
       <input type="hidden" name="cts_<%=i%>" id="cts_<%=i%>" value="<%=lotDtls.get("CTS")%>" />
       
       </td><td align="right"><b><%=lotDtls.get("AVGPRC")%></b></td><td align="right"><b><%=lotDtls.get("VAL")%> </b></td>
      </tr>
      <%isDis=false;}%>
     <tr>
     <td></td>
     <td> 
     </td><td><%=sh%> </td><td align="right"><%=avgDtls.get("QTY")%></td>
     <td align="right"><%=avgDtls.get("CTS")%></td><td align="right"><%=avgDtls.get("AVGPRC")%></td><td align="right"><%=avgDtls.get("VAL")%> </td></tr>
     <%}}}%>
     <tr><td align="right" colspan="3"><b>Total</b></td><td align="right"><label id="ttlOty"> <%=ttlsDtls.get("QTY")%></label></td><td align="right"><label id="ttlCts"> <%=ttlsDtls.get("CTS")%></label></td>
     <td align="right"><%=ttlsDtls.get("AVGPRC")%></td><td align="right"><%=ttlsDtls.get("VAL")%> </td></tr>
     
     </table></td></tr>
     </table>
   </html:form>
   </td></tr>
   <%}}%>
      <tr>
    <td><jsp:include page="/footer.jsp" /> </td>
    </tr>
   </table>
  
</body>
</html>