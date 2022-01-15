<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
 
<%@ page errorPage="../error_page.jsp" %>
<%@ page contentType="text/html;charset=windows-1252"%>
<jsp:useBean id="util" class="ft.com.JspUtil" scope="page" />
<jsp:useBean id="info" class="ft.com.InfoMgr" scope="session" />
<%@ page import="java.util.ArrayList,java.util.HashMap,java.sql.ResultSet,ft.com.*, java.util.Date, java.util.ArrayList, ft.com.dao.*"%>
<%@taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>
<%@taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>Memo Transfer</title>
   <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/bse.css?v=<%=info.getJsVersion()%>"/>
      <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/style.css?v=<%=info.getJsVersion()%>"/>
      <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/ajax.js?v=<%=info.getJsVersion()%> " > </script>
      <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/bse.js?v=<%=info.getJsVersion()%> " > </script>
      <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/memoScript.js?v=<%=info.getJsVersion()%> " > </script>
      <script language="javaScript" type="text/javascript" src="<%=info.getReqUrl()%>/calendar/calendar.js"></script>
   <link href="<%=info.getReqUrl()%>/calendar/calendar.css" rel="stylesheet" type="text/css" />

  </head>
  <%
   String logId=String.valueOf(info.getLogId());
   String onfocus="cook('"+logId+"')";
   String pktTy = util.nvl((String)request.getAttribute("PKT_TY"));
   HashMap allPageDtl= (info.getPageDetails() == null)?new HashMap():(HashMap)info.getPageDetails();
    HashMap pageDtl=(HashMap)allPageDtl.get("MEMO_TRF");
    ArrayList prpDspBlocked = info.getPageblockList();
    ArrayList pageList=new ArrayList();
    HashMap pageDtlMap=new HashMap();
    String fld_nme="",fld_ttl="",val_cond="",dflt_val="",fld_typ="",form_nme="",lov_qry="",currency="Y",memoTrftype="Y" ;
    pageList= ((ArrayList)pageDtl.get("CURRENCY") == null)?new ArrayList():(ArrayList)pageDtl.get("CURRENCY");     
    if(pageList!=null && pageList.size() >0){  
      for(int j=0;j<pageList.size();j++){
            pageDtlMap=(HashMap)pageList.get(j);
            currency=(String)pageDtlMap.get("dflt_val");
    }
    }
    pageList= ((ArrayList)pageDtl.get("MEMO_TRF_TYP") == null)?new ArrayList():(ArrayList)pageDtl.get("MEMO_TRF_TYP");     
    if(pageList!=null && pageList.size() >0){  
      for(int j=0;j<pageList.size();j++){
            pageDtlMap=(HashMap)pageList.get(j);
            memoTrftype=(String)pageDtlMap.get("dflt_val");
    }
    }
  %>
 <body onfocus="<%=onfocus%>" onload="MM_preloadImages('images/faunalogo.png')" topmargin="0" leftmargin="0" rightmargin="0" bottommargin="0">
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
  <img src="../images/line.jpg" border="0" /></td><td valign="middle"> <span class="pgHed">Memo Transfer</span> </td>
</tr></table>
  </td>
  </tr>
  <%if(request.getAttribute("msg")!=null){%>
  <tr>
  <td class="tdLayout"><span class="redLabel"> <%=request.getAttribute("msg")%></span></td>
  </tr>
  <%}%>
  
  
  <tr>
  <td valign="top" class="tdLayout">
  <html:form  action="marketing/memoTranserAction.do?method=view" method="post" >
  <table><tr><td>
  <input type="hidden" name="PKT_TY" id="PKT_TY" value="<%=pktTy%>" />
  <input type="hidden" name="currency" id="currency" value="<%=currency%>" />
   <input type="hidden" name="memoTrftype" id="memoTrftype" value="<%=memoTrftype%>" />
  <table cellpadding="5" cellspacing="5">
<tr>
<td>Buyer</td>
<td>
<html:select property="value(byrIdn)" name="memoTransferForm" styleId="byrIdn" onchange="getBuyTrms('byrIdn','rlnIdn','Y')" >
<html:option value="0">---select---</html:option>
<html:optionsCollection property="memoByrList" value="byrIdn" label="byrNme" />

</html:select>
</td> </tr>
<tr>
<td> <span class="txtBold" >Terms </span></td><td>

<html:select property="value(byrTrm)" styleId="rlnIdn" onchange="getMemoDtl()">
<html:option value="0">---select---</html:option>
<html:optionsCollection property="trmList" name="memoTransferForm"
label="trmDtl" value="relId" />
</html:select>

</td>
</tr>
<tr>
<td>Memo Type</td>
<td>
<html:select property="value(memosTyp)" styleId="memosTyp" onchange="getMemoDtl()"  name="memoTransferForm" >
<html:optionsCollection property="memoTypList" name="memoTransferForm"
label="dsc" value="memo_typ" />
</html:select>
</td>
</tr>
<tr>
<td>Packet Code</td><td>
<textarea rows="5" cols="20" name="vnm" id="vnm"></textarea>
&nbsp;&nbsp;
<html:submit property="value(pktDtl)" styleClass="submit" onclick="return VerifiedVNMTransfer()" value="View Packet Dtl" />
</td>
</tr>
</table>



  </td></tr>
  
  <tr><td>
        <html:hidden property="value(trans)" styleId="trans" value=""  />

  <div id="memoDtl" style="display:none">
  <table cellpadding="1" cellspacing="1"><tr><td>Buyer List :</td>
  <td>
  
  <html:text  property="value(tnsByrIdn)" name="memoTransferForm" styleId="partytext" style="width:180px" styleClass="sugBox" onkeypress="return disableEnterKey(event);" 
           onkeyup="doCompletion('partytext', 'nmePop', '../ajaxAction.do?1=1&isSrch=Y', event)"
      onkeydown="setDownSerchpage('nmePop', 'party', 'partytext',event)" onclick="document.getElementById('partytext').autocomplete='off'"  />
      <html:hidden property="value(party)" styleId="party"  />
  <div style="position: absolute;">
      <select id="nmePop" name="nmePop"
        style="display:none;300px;" 
        class="sugBoxDiv" 
        onKeyDown="setDownSerchpage('nmePop', 'party', 'partytext',event)" 
        onDblClick="setVal('nmePop', 'party', 'partytext', event);hideObj('nmePop'),getBuyTrms('party','rlnId','N');" 
        onClick="setVal('nmePop', 'party', 'partytext', event)" 
        size="10">
      </select>
</div>   
  
  </td>
 <td> <span class="txtBold" >Terms </span></td><td>

<html:select property="value(trnsByrTrm)" styleId="rlnId"  >
<html:option value="0">---select---</html:option>
<html:optionsCollection property="trmList" name="memoTransferForm"
label="trmDtl" value="relId" />
</html:select>

</td>
<td> <span class="txtBold" >Memo Type </span></td><td>

<html:select property="value(memoTyp)" styleId="memoTyp" onchange="transferMemotoSametype();"  >
<html:optionsCollection property="memoTypList" name="memoTransferForm"
label="dsc" value="memo_typ" />
</html:select>

</td>
<td valign="top"><div id="expDay" style="display:none">
<logic:present property="value(ExpDayList)" name="memoTransferForm" >
<table><tr><td>
<span class="txtBold"> Expiry days : </span></td><td>
<html:select property="value(day)" name="memoTransferForm" >
<html:optionsCollection property="value(ExpDayList)" name="memoTransferForm" value="FORM_NME" label="FORM_TTL" />
</html:select></td>
<%if(util.nvl((String)info.getDmbsInfoLst().get("EXTME")).equals("Y")){ %>
<td><span class="txtBold">Expiry Time: </span></td><td>

<html:text property="value(extme)" name="memoTransferForm" size="8" /> 

</td>
<%}else{%>
<html:hidden property="value(extme)" name="memoTransferForm"  /> 
<%}%>
<%if(util.nvl((String)info.getDmbsInfoLst().get("EXTCONF")).equals("Y")){ %>
<td><span class="txtBold">Ext Conf %:</span></td><td><html:text property="value(extconf)" name="memoTransferForm" size="10" /> </td> 
<%}else{%>
<html:hidden property="value(extconf)" name="memoTransferForm"  /> 
<%}%>
</tr></table>
</logic:present>
</div></td>


 <td><html:button styleClass="submit" value="Transfer Memo" onclick="return verifyRelationCur()"  property="value(transbtn)" /> </td>
<td><html:submit styleClass="submit" value="View Memo" onclick="DisplayDivDtl('block')" property="value(fetch)" /> </td>
  </tr>
  <tr><td colspan="8">
  <div id="lstMemoDiv" style="width:100%"></div>
   </td></tr></table>
  </div>
  </td></tr>
    <tr><td>
    <div id="memoDtlLst" >
     <%
     String view = (String)request.getAttribute("view");
     if(view!=null){
     ArrayList pktList = (ArrayList)request.getAttribute("pktList");
     if(pktList!=null && pktList.size()>0){
    ArrayList prps = (info.getValue("MDL_MEMO_RTRN") == null)?new ArrayList():(ArrayList)info.getValue("MDL_MEMO_RTRN");

     %>
  <input type="hidden" name="PKT_TY" id="PKT_TY" value="<%=pktTy%>" />

     <table cellpadding="1" cellspacing="1">
     <tr><td><b>Memo Detail </b></td></tr>
     <tr><td><table><tr>
     <td>Buyer List :</td>
  <td>   

  <html:text  property="value(tnsByrIdn)" name="memoTransferForm" styleId="fpartytext" style="width:180px" styleClass="sugBox" onkeypress="return disableEnterKey(event);" 
           onkeyup="doCompletion('fpartytext', 'fnmePop', '../ajaxAction.do?1=1&isSrch=Y', event)"
      onkeydown="setDownSerchpage('fnmePop', 'fparty', 'fpartytext',event)" onclick="document.getElementById('partytext').autocomplete='off'"  />
      <html:hidden property="value(fparty)" styleId="fparty"  />
  <div style="position: absolute;">
      <select id="fnmePop" name="fnmePop"
        style="display:none;300px;" 
        class="sugBoxDiv" 
        onKeyDown="setDownSerchpage('fnmePop', 'fparty', 'fpartytext',event)" 
        onDblClick="setVal('fnmePop', 'fparty', 'fpartytext', event);hideObj('fnmePop'),getBuyTrms('fparty','trnsrlnId','N');" 
        onClick="setVal('fnmePop', 'fparty', 'fpartytext', event)" 
        size="10">
      </select>
</div>   
  
          </td>
           <td> <span class="txtBold" >Terms </span></td><td>

<html:select property="value(trnsfByrTrm)" styleId="frlnId"  >
<html:option value="0">---select---</html:option>
<html:optionsCollection property="trmList" name="memoTransferForm"
label="trmDtl" value="relId" />
</html:select>

</td>
<td> <span class="txtBold" >Memo Type </span></td><td>

<html:select property="value(memofTyp)" styleId="memofTyp"  onchange="transferMemotoSametypepacket();"   >
<html:optionsCollection property="memoTypList" name="memoTransferForm"
label="dsc" value="memo_typ" />
</html:select>
</td>

<td valign="top"><div id="expDayf" style="display:none">
<logic:present property="value(ExpDayList)" name="memoTransferForm" >
<table><tr><td>
<span class="txtBold"> Expiry days : </span></td><td>
<html:select property="value(dayf)" name="memoTransferForm" >
<html:optionsCollection property="value(ExpDayList)" name="memoTransferForm" value="FORM_NME" label="FORM_TTL" />
</html:select></td>
<%if(util.nvl((String)info.getDmbsInfoLst().get("EXTME")).equals("Y")){ %>
<td><span class="txtBold">Expiry Time: </span></td><td>

<html:text property="value(extmef)" name="memoTransferForm" size="8" /> 

</td>
<%}else{%>
<html:hidden property="value(extmef)" name="memoTransferForm"  /> 
<%}%>
<%if(util.nvl((String)info.getDmbsInfoLst().get("EXTCONF")).equals("Y")){ %>
<td><span class="txtBold">Ext Conf %:</span></td><td><html:text property="value(extconff)" name="memoTransferForm" size="10" /> </td> 
<%}else{%>
<html:hidden property="value(extconff)" name="memoTransferForm"  /> 
<%}%>
</tr></table>
</logic:present>
</div></td>

     <td>
        <html:button styleClass="submit" value="Transfer Packets" onclick="return verifyRelationCurPacket('PKT');" property="value(transPkt)" />

     </td></tr></table> </td></tr>
     <tr><td>
     <table class="grid1">
                <tr>
                    <th>Sr</th>
                    <th><input type="checkbox" id="ALL" onclick="ALLCheckBoxTyp('ALL','cb_memo_','0')"></th>
                    <th>Memo Id</th>
                      <th>Packet Code</th>
                     <%for(int j=0; j < prps.size(); j++) {
                     String lprp=(String)prps.get(j);
                    %>    <th><%=lprp%></th>
                    <%}%>
                    <th>RapRte</th>
                    <th>RapVlu</th>
                    <th>Dis</th>
                    <th>Prc / Crt </th>
                    <th>ByrDis</th>
                    <th>Quot</th></tr>
                <% for(int i=0; i < pktList.size(); i++) {
                PktDtl pkt = (PktDtl)pktList.get(i);
                String pktIdn =String.valueOf(pkt.getPktIdn());
                String cb_memo = "cb_memo_"+pktIdn;
                String val =pktIdn+"_"+pkt.getMemoId();
                %>
                <tr>
                <td><%=(i+1)%></td>
                <td><input type="checkbox" name="<%=cb_memo%>" id="<%=cb_memo%>" value="<%=val%>" ></td>
               
                <td><%=pkt.getMemoId()%>    
               </td>
                 <td><%=pkt.getVnm()%></td>
                <%for(int j=0; j < prps.size(); j++) {
                      String lprp = (String)prps.get(j);
                    %>
                    <td nowrap="nowrap"><%=util.nvl(pkt.getValue((String)prps.get(j)))%> </td>
                <%}
                %>
                <td><%=util.nvl(pkt.getRapRte())%></td>
                <td><%=util.nvl(pkt.getValue("rapVlu"))%></td>
                <td><%=util.nvl(pkt.getDis())%></td>
                <td><%=util.nvl(pkt.getRte())%>
                </td>
                <td><%=util.nvl(pkt.getByrDis())%></td>
                <td><%=util.nvl(pkt.getMemoQuot())%></td>
                </tr><%}%></table>
              
        
     </td>
     </tr>
     </table>
   
     <%}else{%>
     Sorry no result found...
     <%}}%></div>
    </td></tr>
    </table>
      </html:form>
    </td></tr>
  
    <tr>
    <td><jsp:include page="/footer.jsp" /> </td>
    </tr>

</table>
  
  
  
  </body>
</html>