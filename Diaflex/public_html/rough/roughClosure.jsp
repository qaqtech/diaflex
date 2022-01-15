<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page errorPage="../error_page.jsp" %>
<%@ page contentType="text/html;charset=windows-1252"%>
<jsp:useBean id="info" class="ft.com.InfoMgr" scope="session" />
<jsp:useBean id="util" class="ft.com.JspUtil" scope="page" />
<jsp:useBean id="gtMgr" class="ft.com.GtMgr" scope="session" />

<%@ page import="java.util.ArrayList,java.util.LinkedHashMap,java.util.HashMap,java.util.Set,java.sql.ResultSet,ft.com.*, java.util.Date, java.util.ArrayList, ft.com.dao.*"%>
<%@taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>
<%@taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>

<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>Rough Closure</title>
    <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/bse.css?v=<%=info.getJsVersion()%>"/>
    <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/general.css?v=<%=info.getJsVersion()%>"/>
     <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/style.css?v=<%=info.getJsVersion()%>"/>
     <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/bse.js?v=<%=info.getJsVersion()%> " > </script>
      <script type="text/javascript" src="<%=info.getReqUrl()%>/PopScripts/popup.js?v=<%=info.getJsVersion()%> " > </script>
     <script type="text/javascript" src="<%=info.getReqUrl()%>/jqueryScript/jqueryScript.js?v=<%=info.getJsVersion()%> " > </script>
    <script src="<%=info.getReqUrl()%>/PopScripts/jquery-1.2.6.min.js" type="text/javascript"></script>
    <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/memoScript.js?v=<%=info.getJsVersion()%> " > </script>
    <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/assortScript.js?v=<%=info.getJsVersion()%> " > </script>
    <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/ajax.js?v=<%=info.getJsVersion()%> " > </script>
  <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/newCalScript.js?v=<%=info.getJsVersion()%> " > </script>

  
  </head>
        <%String logId=String.valueOf(info.getLogId());
        String onfocus="cook('"+logId+"')";
        String view = util.nvl((String)request.getAttribute("view"));
    boolean isMfgDis=true;
        %>
 <body onfocus="<%=onfocus%>" onload="MM_preloadImages('images/faunalogo.png')" topmargin="0" leftmargin="0" rightmargin="0" bottommargin="0" onkeypress="return disableEnterKey(event);" >
<input type="hidden" id="reqUrl" name="reqUrl" value="<%=info.getReqUrl()%>"/>
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
  <img src="../images/line.jpg" border="0" /></td><td valign="middle"> <span class="pgHed">Rough Closure</span> </td>
</tr></table>
  </td>
  </tr>
  
   <%String msg = (String)request.getAttribute("msg");
   if(msg!=null){
   %>
   <tr><td valign="top" class="tdLayout"> <span class="redLabel"><%=msg%></span></td></tr>
   <%}%>
   
     <tr><td valign="top" class="tdLayout">
  <table><tr><td>
  <html:form action="/rough/roughClosureAction.do?method=fetch" method="POST" >
<table class="grid1" >

<tr><th colspan="2">Memo Search </th></tr>


<tr>
<td colspan="2"> <html:radio property="value(memoSrch)" value="ByrSrch" onclick="DisplayMemoSrch('MS_1')" styleId="MS_1" /> Memos Search By Buyer </td>
</tr>
<tr style="display:none" id="DMS_1"><td colspan="2">

<table cellpadding="5"><tr>
<td>Buyer</td>
<td>
<html:select property="value(byrIdn)" styleId="byrId" onchange="getTrms(this,'SALE')" >
<html:option value="0">---select---</html:option>
<html:optionsCollection property="byrLstFetch" value="byrIdn" label="byrNme" />

</html:select>
</td> </tr>
<tr>
<td> <span class="txtBold" >Terms </span></td><td>

<html:select property="value(byrTrm)" styleId="rlnId" onchange="GetTypMemoRadioIdn()">
<html:option value="0">---select---</html:option>
<html:optionsCollection property="trmsLst" name="roughClosureForm"
label="trmDtl" value="relId" />
</html:select>

</td>
</tr>
<tr>
<td>Memo Type</td>
<td>
<html:select property="typ" styleId="typId" name="roughClosureForm" onchange="GetTypMemoRadioIdn()" >
<html:optionsCollection property="memoList" name="roughClosureForm"
label="dsc" value="memo_typ" />
</html:select>
</td>
</tr>
<tr><td colspan="2"><div id="memoIdn"></div> </td></tr>
</table>

</td>
</tr>
<tr>
<td colspan="2"> <html:radio property="value(memoSrch)" value="MemoSrch" styleId="MS_2" onclick="DisplayMemoSrch('MS_2')" /> Memos Search By Memo Ids</td>
</tr>
<tr style="display:none" id="DMS_2">
<td>
<table>
<tr>
<td>Memo Ids</td><td><html:text property="memoIdn" name="roughClosureForm" styleId="memoid"/></td></tr>

</table>
</td>
</tr>
</table>
<p><html:submit property="submit" value="View" styleClass="submit"/></p>
</html:form>
</td>
</tr></table>
  
  </td></tr>
 
   <%
  
   ArrayList viewList = (ArrayList)request.getAttribute("viewList");
   if(viewList==null)
   viewList = new ArrayList();
   ArrayList pktList = (ArrayList)request.getAttribute("pktList");
   if(pktList!=null && pktList.size()>0){
   int viewSz = viewList.size();
   int colspan = viewSz+7;
   
   
   %>
   
     <tr><td valign="top" class="tdLayout">
    
   <table>
   <tr>
   <td><b> Issue Id :</b></td> <td><bean:write property="value(MemoId)"  name="roughClosureForm" /></td>
   <td><b> Qty </b></td><td><bean:write property="qty"  name="roughClosureForm" /></td>
   <td><b> Carat </b></td><td><bean:write property="cts"  name="roughClosureForm" /></td>
   <td><b> Value</b></td><td><bean:write property="vlu"  name="roughClosureForm" /></td>
   </tr>
   
   </table>
   
   </td></tr>
     <html:form action="/rough/roughClosureAction.do?method=ReturnClosure" method="post" onsubmit="return confirmChanges()">
  <tr><td valign="top" class="tdLayout">
  <div id="Verify" style="display:block">
  <html:button property="value(btn)" value="Verify" onclick="valifiedRoughCarat()"  styleClass="submit"/>
  </div>
  <div id="Return" style="display:none">
  <html:submit property="submit" value="Return"    styleClass="submit"/>
  </div>
  </td></tr>
  <tr><td valign="top" class="tdLayout">&nbsp;</td></tr>
 
  <html:hidden property="value(mstkIdn)" name="roughClosureForm" styleId="mstkIdn" />
  <html:hidden property="value(memoId)" name="roughClosureForm" styleId="memoId"/>
  <html:hidden property="value(mlotidnVal)" name="roughClosureForm" styleId="mlotidnVal"/>
  <html:hidden property="value(upr)" name="roughClosureForm" styleId="upr"/>
   <tr><td valign="top" class="tdLayout">
   <table class="grid1">
   <tr>
    <th>Sr</th>
    <th>Total Stone</th>
    <th>Labour Per Qty</th>
    <th>LP Cts</th>
     <th>Reject cts</th>
     <th>Reject Rte</th>
     <th>Rough Qty</th>
    <th>Rough Cts</th>
   <th>Pol Qty</th>
    <th>Pol Cts</th>
    <th>Packet Code</th>
    <th>Qty</th>
    <th>Cts</th>
    <th>Rte</th>
    <%for(int j=0;j<viewSz;j++){
   String lprp = (String)viewList.get(j);
   %>
   <th><%=lprp%></th>
   <%}%>
   
   </tr>
  <% for(int i=0;i<pktList.size();i++){
   PktDtl pktDtl = (PktDtl)pktList.get(i);
   int sr=i+1;
   long pktIdn = pktDtl.getPktIdn();
   String mstkIdn = String.valueOf(pktIdn);
   String vnm = util.nvl((String)pktDtl.getVnm());
   String cts = util.nvl((String)pktDtl.getCts());
   String qty = util.nvl((String)pktDtl.getQty());
   String prc=util.nvl((String)pktDtl.getMemoQuot());
   String memoId = util.nvl((String)pktDtl.getMemoId());
   String fldVal = memoId+"_"+mstkIdn;
   %>
   <tr> <td><%=sr%></td>
  
      <td><input type="text"  name="totalStone" id="totalStone" onchange="valifiedStoneCnt()" size="10" style="txtStyle" /> </td>
     <td><input type="text" name="labourPerStone" id="labourPerStone" size="10" style="txtStyle" /> </td>
     <td><input type="text" name="lpCts" id="lpCts"  size="8" onchange="VerifiedTotalRghCarat(this)" style="txtStyle" /> </td>
     <td><input type="text" name="rejectcts" id="rejectcts" onchange="VerifiedTotalRghCarat(this)" size="10" style="txtStyle" /> </td>
         <td><input type="text" name="rejectrte" id="rejectrte" size="10" style="txtStyle" /> </td>
      <td><input type="text" name="rghqty" id="rghqty" readonly="readonly" size="7"  style="txtStyle" /> </td>
     <td><input type="text" name="rghcts" id="rghcts" readonly="readonly" size="10" onchange="VerifiedTotalRghCarat(this)" style="txtStyle" /> </td>
     <td> <input type="text" name="polqty" id="polqty" readonly="readonly" size="7"  style="txtStyle"/></td>
     <td><input type="text" name="polcts" id="polcts" readonly="readonly" size="10" style="txtStyle" /> </td>
  
   <td><%=vnm%></td>
   <td><%=qty%></td>
   <td><%=cts%>
       <input type="hidden" name="cts" id="cts" size="8" style="txtStyle" value="<%=cts%>" />

   </td>
   <td><%=prc%></td>
   <%for(int j=0;j<viewSz;j++){
   String lprp = (String)viewList.get(j);
   String lprpVal = util.nvl(pktDtl.getValue(lprp));
   
   %>
   <td><%=lprpVal%>
   <%if(lprp.equals("MFG_LOT_NO") && lprpVal.equals("")){
    isMfgDis=false;
   String SaveMfgLot= "SaveMfgLot("+mstkIdn+","+memoId+")";
   %>
   <input type="text" name="mfg_lot_no" id="mfg_lot_no" size="8" style="txtStyle" />
   <html:button property="value(saveMfg)" value="Save MFG Lot" onclick="<%=SaveMfgLot%>" styleClass="submit"/>
   <%}else{%>
    <input type="hidden" name="mfg_lot_no" id="mfg_lot_no" size="8" style="txtStyle" value="<%=lprpVal%>" />
   <%}%>
   </td>
   <%}%>
   </tr>
  
   <%}%>
   </table></td></tr>
   </html:form>
   <tr><td valign="top" class="tdLayout">
   
   <div id="pktDtl" class="floating" style="display:none">
    <iframe id="FRAME"   name="FRAME" width="1200" height="600"  align="middle" frameborder="0"></iframe>
  </div>
   </td></tr>
   <%}%>
  
  <tr>
  <td><jsp:include page="/footer.jsp" /> </td>
  </tr>
  
   </table> 
   <%if(isMfgDis){%>
         <script type="text/javascript">
    $(document).ready(function() {
    var mstkIdn = document.getElementById('mstkIdn').value;
    var memoId = document.getElementById('memoId').value;
   var mfg_lot_no = document.getElementById('mfg_lot_no').value;
   var cts = document.getElementById('cts').value;
    if(mstkIdn!='' && memoId!=''){
    showHidDiv('pktDtl');
    frameOpen('FRAME','/rough/roughClosureAction.do?method=loadPkt&mstkIdn='+mstkIdn+'&memoId='+memoId+'&MFGLOTNO='+mfg_lot_no+"&cts="+cts);
    }
    
     })
    </script>
 <%}%>
    </body>
</html>