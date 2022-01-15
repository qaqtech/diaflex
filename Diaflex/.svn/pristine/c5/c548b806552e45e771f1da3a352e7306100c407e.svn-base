<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=windows-1252"%>
<jsp:useBean id="info" class="ft.com.InfoMgr" scope="session" />
<jsp:useBean id="util" class="ft.com.JspUtil" scope="page" />

<%@ page import="java.util.ArrayList,java.util.LinkedHashMap,java.util.HashMap" %>
<%@taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
          <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/bse.css?v=<%=info.getJsVersion()%>"/>
      <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/style.css?v=<%=info.getJsVersion()%>"/>
      <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/assortScript.js?v=<%=info.getJsVersion()%> " > </script>
      <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/bse.js?v=<%=info.getJsVersion()%> " > </script>
     <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/newCalScript.js?v=<%=info.getJsVersion()%> " > </script>
    <script src="<%=info.getReqUrl()%>/PopScripts/jquery-1.2.6.min.js" type="text/javascript"></script>
<script type="text/javascript" src="<%=info.getReqUrl()%>/PopScripts/popup.js?v=<%=info.getJsVersion()%> " > </script>
<link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/general.css?v=<%=info.getJsVersion()%>"/>

    </head>
    <body>
    <div id="backgroundPopup"></div>
<div id="popupContactSave" align="center" >
<img src="../images/loading_flame.gif" height="150px" width="150px" border="0" />
</div>
    <%
    HashMap mprp = info.getMprp();
    HashMap prp = info.getPrp();
    ArrayList pktDtlList = (ArrayList)request.getAttribute("pktDtlList");
    if(pktDtlList==null)
    pktDtlList = new ArrayList();
    HashMap pktSummary = (HashMap)request.getAttribute("pktSummary");
    if(pktSummary ==null)
    pktSummary = new HashMap();
    int pktSize = pktDtlList.size();
    String mstkIdn = (String)request.getParameter("mstkIdn");
    if(mstkIdn==null)
      mstkIdn = (String)request.getAttribute("mstkIdn");
    String memoId = (String)request.getParameter("memoId");
    if(memoId==null)
       memoId = (String)request.getAttribute("memoId");
     
    String MFGLOTNO = (String)request.getParameter("MFGLOTNO");
     if(MFGLOTNO==null)
       MFGLOTNO = (String)request.getAttribute("MFGLOTNO");
   
    String rghcts = request.getParameter("cts");
    if(rghcts==null)
     rghcts = (String)request.getAttribute("cts");
     
      ArrayList vwPrpList = (ArrayList)session.getAttribute("RGHCLU_PKT");
    if(vwPrpList==null)
    vwPrpList = new ArrayList();
    String qtylbId = "qtylb_"+mstkIdn;
   String ctslbId = "ctslb_"+mstkIdn;
   String qtyPollbId = "qtypollb_"+mstkIdn;
   String ctsPollbId = "ctspollb_"+mstkIdn;
   String prcSalId = "rtelb_"+mstkIdn;
     String qtylbhId = "qtylbh_"+mstkIdn;
   String ctslbhId = "ctslbh_"+mstkIdn;
   String qtyPollbhId = "qtypollbh_"+mstkIdn;
   String ctsPollbhId = "ctspollbh_"+mstkIdn;
   String prcSalIhd = "rtelbh_"+mstkIdn;
   
  
    int count =10;
   int sr=0;
    %>
     <html:form action="/rough/roughClosureAction.do?method=saveClosure" method="post" >
     <input type="hidden"  name="mstkIdn" id="mstkIdn" value="<%=mstkIdn%>"/>
      <input type="hidden"  name="memoId" id="memoId" value="<%=memoId%>"/>
      <input type="hidden"  name="MFGLOTNO" id="MFGLOTNO" value="<%=MFGLOTNO%>"/>
      <input type="hidden"  name="ttlcts" id="ttlcts" value="<%=rghcts%>"/>
    <input type="hidden" class="cnt_<%=mstkIdn%>" name="cnt_<%=mstkIdn%>" id="cnt_<%=mstkIdn%>" value="<%=count%>"/>
     <table cellpadding="2" cellspacing="2">
<tr><td align="left">
<html:submit property="submit" value="Save Change" styleClass="submit" onclick="loading()"/>
</td></tr>
<tr><td>
<table class="grid1" cellpadding="1" cellspacing="1">
<tr><th></th><th >Rgh Qty</th><th >Rgh Cts</th><th >Pol Qty</th><th >Pol Cts</th><th >Avg. Rate</th></tr>
<tr><th>Total</th>
<td> 
<input type="hidden" name="<%=qtylbhId%>" id="<%=qtylbhId%>" value="<%=util.nvl((String)pktSummary.get("qty"))%>" />
<label id="<%=qtylbId%>" ><%=util.nvl((String)pktSummary.get("qty"))%></label></td>
<td>
<input type="hidden" name="<%=ctslbhId%>" id="<%=ctslbhId%>" value="<%=util.nvl((String)pktSummary.get("cts"))%>" />
<label id="<%=ctslbId%>" ><%=util.nvl((String)pktSummary.get("cts"))%></label> </td>
<td>
<input type="hidden" name="<%=qtyPollbhId%>" id="<%=qtyPollbhId%>" value="<%=util.nvl((String)pktSummary.get("polQty"))%>" />
<label id="<%=qtyPollbId%>" ><%=util.nvl((String)pktSummary.get("polQty"))%></label>  </td>
<td>
<input type="hidden" name="<%=ctsPollbhId%>" id="<%=ctsPollbhId%>" value="<%=util.nvl((String)pktSummary.get("polcts"))%>" />
<label id="<%=ctsPollbId%>" ><%=util.nvl((String)pktSummary.get("polcts"))%></label></td>
<td>
<input type="hidden" name="<%=prcSalIhd%>" id="<%=prcSalIhd%>" value="<%=util.nvl((String)pktSummary.get("avgRte"))%>" />
<label id="<%=prcSalId%>" ><%=util.nvl((String)pktSummary.get("avgRte"))%></label></td>
</tr>

</table>
</td>
</tr>



<tr><td>

<table class="grid1" cellpadding="1" cellspacing="1">
<tr><th>Sr</th><th>Type</th><th>Status</th>
<th>Rgh Qty</th><th>Rgh Cts</th><th>Pol Qty</th><th>Pol Cts</th><th>Price</th>
<%for(int i=0;i<vwPrpList.size();i++){
String lprp = (String)vwPrpList.get(i);
%>
<th><%=lprp%></th>
<%}%>
</tr>
<%for(int i=0;i<pktSize;i++){
HashMap pktDtl = (HashMap)pktDtlList.get(i);
String qty="",qtyPolVal="",cts="",ctsPolVal="",idn="",upr="",pktTy="",stt="",rmk="";
sr=sr+1;
cts = util.nvl((String)pktDtl.get("cts"));
ctsPolVal = util.nvl((String)pktDtl.get("polcts"));
qty = util.nvl((String)pktDtl.get("qty"));
qtyPolVal = util.nvl((String)pktDtl.get("polQty"));
idn = util.nvl((String)pktDtl.get("idn"));
upr=util.nvl((String)pktDtl.get("upr"));
pktTy = util.nvl((String)pktDtl.get("pktTy"));
stt =  util.nvl((String)pktDtl.get("stt"));
%>
<tr><td><%=sr%></td><td><%=pktTy%></td><td><%=stt%></td><td><%=qty%></td><td><%=cts%></td>
<td><%=qtyPolVal%></td><td><%=ctsPolVal%></td><td><%=upr%></td>

<%for(int j=0;j<vwPrpList.size();j++){
String lprp = (String)vwPrpList.get(j);
String lprpVal = util.nvl((String)pktDtl.get(lprp));
%>
<td><%=lprpVal%></td>
<%}%>
</tr>

<%}%>

<%for(int j=1;j<=count;j++){

sr=sr+1;
String fldId=mstkIdn+"_"+j;
String typId="TYP_"+fldId;
String typFld="value("+typId+")";
String ctsId="CTS_"+fldId;
String ctsFld = "value("+ctsId+")";
String polctsId="POLCTS_"+fldId;
String polctsFld = "value("+polctsId+")";
String qtyId="QTY_"+fldId;
String qtyFld = "value("+qtyId+")";
String polqtyId="POLQTY_"+fldId;
String polqtyFld = "value("+polqtyId+")";
String rteId="RTE_"+fldId;
String rteFld = "value("+rteId+")";
String rmkId="RMK_"+fldId;
String rmkFld = "value("+rmkId+")";
String ONclick="RoughRtnCalCul('"+mstkIdn+"')";
String ONclickCts="PolctsValid('"+mstkIdn+"','"+ctsId+"','"+polctsId+"');RoughRtnCalCul('"+mstkIdn+"');";

%>
<tr><td><%=sr%></td><td>
<html:select property="<%=typFld%>" styleId="<%=typId%>" name="roughClosureForm">
<html:option value="MIX">Mixing</html:option>
<html:option value="NR">Single</html:option>
</html:select>
</td><td>MF_IN</td>
<td><input type="text" id="<%=qtyId%>"  size="5" class="txtStyle" onchange="<%=ONclick%>"  name="<%=qtyId%>" /> </td>
<td><input type="text" id="<%=ctsId%>"  size="10" class="txtStyle" onchange="<%=ONclick%>"  name="<%=ctsId%>" /> </td>
<td><input type="text" id="<%=polqtyId%>" size="5" class="txtStyle" onchange="<%=ONclick%>"  name="<%=polqtyId%>" /> </td>
<td><input type="text" id="<%=polctsId%>"  size="10" class="txtStyle" onchange="<%=ONclickCts%>"  name="<%=polctsId%>" />  </td>
<td><input type="text" id="<%=rteId%>"  size="20" class="txtStyle" onchange="<%=ONclick%>"  name="<%=rteId%>" />  </td>
<%for(int x=0;x<vwPrpList.size();x++){
String lprp = util.nvl((String)vwPrpList.get(x));
String lprpTyp = util.nvl((String)mprp.get(lprp+"T"));
String fldName = fldId+"_"+lprp;
if(lprpTyp.equals("C")){

 ArrayList prpList = (ArrayList)prp.get(lprp+"V");
  ArrayList prpSrtLst = (ArrayList)prp.get(lprp+"S");
  ArrayList prpPrtLst = (ArrayList)prp.get(lprp+"P");
  %>
  <td>
  <select id="<%=fldName%>"   name="<%=fldName%>">
  
    <%for(int k=0;k<prpList.size();k++){
    String prpVal=(String)prpList.get(k);
    String prpSrt=(String)prpSrtLst.get(k);
    String prpPrt = (String)prpPrtLst.get(k);
  %>
    <option value="<%=prpVal%>"  ><%=prpVal%></option>
    <%}%>  
  </select>
  
  
  </td>
<%}else{%>
  <td><input type="text" name="<%=fldName%>" id="<%=fldName%>" size="8"  /></td>

<%}%>

<%}%>
</tr>
<%}%>
</table>
</td></tr></table>
   </html:form> 
    
     <script type="text/javascript">
      $(document).ready(function() {
       var mstkIdn = document.getElementById('mstkIdn').value;
       var rghCts = document.getElementById('ctslbh_'+mstkIdn).value;
        var rghQty = document.getElementById('qtylbh_'+mstkIdn).value;
        var polCts = document.getElementById('ctspollbh_'+mstkIdn).value;
        var polQty = document.getElementById('qtypollbh_'+mstkIdn).value;
    window.parent.document.getElementById("polcts").value=new Number(polCts).toFixed(2);
      window.parent.document.getElementById("polqty").value=polQty;
     window.parent.document.getElementById("rghcts").value=new Number(rghCts).toFixed(2);
      window.parent.document.getElementById("rghqty").value= rghQty;
        })
    </script>
    </body>
</html>