<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page errorPage="../error_page.jsp" %>
<%@ page contentType="text/html;charset=windows-1252"%>
<jsp:useBean id="info" class="ft.com.InfoMgr" scope="session" />
<jsp:useBean id="util" class="ft.com.JspUtil" scope="page" />
<jsp:useBean id="gtMgr" class="ft.com.GtMgr" scope="session" />

<%@ page import="java.util.ArrayList,java.util.LinkedHashMap,java.util.HashMap,java.util.Set,java.sql.ResultSet,ft.com.*, java.util.Date, java.util.ArrayList, ft.com.dao.*"%>
<%@taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>

<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>Mix Assort Return</title>
<link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/bse.css?v=<%=info.getJsVersion()%>"/>
    <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/general.css?v=<%=info.getJsVersion()%>"/>
     <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/style.css?v=<%=info.getJsVersion()%>"/>
     <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/bse.js?v=<%=info.getJsVersion()%> " > </script>
      <script type="text/javascript" src="<%=info.getReqUrl()%>/PopScripts/popup.js?v=<%=info.getJsVersion()%> " > </script>
     <script src="<%=info.getReqUrl()%>/PopScripts/jquery-1.2.6.min.js" type="text/javascript"></script>
          <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/newCalScript.js?v=<%=info.getJsVersion()%> " > </script>
<script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/assortScript.js?v=<%=info.getJsVersion()%> " > </script>
         <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/ajax.js?v=<%=info.getJsVersion()%> " > </script>
   <script src="<%=info.getReqUrl()%>/jqueryScript/jqueryScript.js?v=<%=info.getJsVersion()%>" type="text/javascript"></script>
              <script src="<%=info.getReqUrl()%>/jqueryScript/jquery.min.js" type="text/javascript"></script>  
    <link href="<%=info.getReqUrl()%>/css/ScrollTabla.css?v=<%=info.getJsVersion()%>" rel="stylesheet" media="screen" />
         
  </head>
        <%String logId=String.valueOf(info.getLogId());
        String onfocus="cook('"+logId+"')";
     
        %>
 <body onfocus="<%=onfocus%>" onload="MM_preloadImages('images/faunalogo.png')" topmargin="0" leftmargin="0" rightmargin="0" bottommargin="0">


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
  <img src="../images/line.jpg" border="0" /></td><td valign="middle"> <span class="pgHed">Mix Assort Return</span> </td>
</tr></table>
  </td>
  </tr>
   <%
   String msg = (String)request.getAttribute("msg");
   if(msg!=null){%>
  <tr><td valign="top" class="tdLayout"><span class="redLabel"><%=msg%></span></td></tr>
  <%}%>
   <tr>
  <td valign="top" class="hedPg">
<html:form action="/mixLkhi/mixAssortReturn.do?method=fetch" method="post" onsubmit="return validate_AssortMixreturn()">
   <html:hidden property="value(grpList)" name="mixAssortReturnLKForm" />
   <table class="grid1"><tr><th colspan="2">Search Criteria </th></tr> <tr><td>Process : </td>
   <td>
   <html:select property="value(mprcIdn)"  styleId="mprcIdn" onchange="SetPrpOnPrcID()" name="mixAssortReturnLKForm"   >
           <html:option value="0" >--select--</html:option>
            <html:optionsCollection property="mprcList" name="mixAssortReturnLKForm" 
            label="prc" value="mprcId" />
    </html:select>
   </td></tr>
   <tr>
    <td>
    Employee : </td>
   <td>
   <html:select property="value(empIdn)"  styleId="empIdn" name="mixAssortReturnLKForm"  >
           <html:option value="0" >--select--</html:option>
            <html:optionsCollection property="empList" name="mixAssortReturnLKForm" 
            label="emp_nme" value="emp_idn" />
            </html:select>
   </td>
   </tr>
    <tr>
   <td colspan="2" >
   
   <% ArrayList srchPrp = info.getGncPrpLst(); 
   %>
    <table border="0" cellspacing="0" cellpadding="3" width="330">
    <%
    String dfltDsp = "&nbsp;&nbsp;&nbsp;&nbsp;---select---&nbsp;&nbsp;&nbsp;&nbsp;";
    String dfltVal = "0";
    if(srchPrp!=null){
     String commPrpLst = util.nvl((String)request.getAttribute("commPrpLst"));
  
    HashMap mprp = info.getMprp();
    HashMap prp = info.getPrp();
    for(int i=0;i<srchPrp.size();i++){
      String val1 = "";
      String val2= "";
      ArrayList prplist =(ArrayList)srchPrp.get(i);
       if(prplist!=null){
       String lprp = util.nvl((String)prplist.get(0));
       String flgtyp =util.nvl((String)prplist.get(1));
      
       String lprpTyp = util.nvl((String)mprp.get(lprp+"T"));
       String lprpDsc = util.nvl((String)mprp.get(lprp+"D"));
         String trId = lprp+"_TR";
       String fld1 = lprp+"_1";
      String fld2 = lprp+"_2";
      String prpFld1 = "value(" + fld1 + ")" ;
      String prpFld2 = "value(" + fld2 + ")" ; 
      String onChg1 = "setFrTo('"+ fld1 + "','" + fld2 + "')";
      String onChg2 = "setFrTo('"+ fld1 + "','" + fld2 + "')";
      String isDisplay = "style=\"display:none\"";
      if(commPrpLst.indexOf(lprp)!=-1)
      isDisplay="";
       %>
    <tr id="<%=trId%>" class="tr" <%=isDisplay%>>
         <td align="center" nowrap="nowrap"><%=lprpDsc%></td>
      <% if(lprpTyp.equals("C")) {
       ArrayList  prpPrt = (ArrayList)prp.get(lprp+"P");
       ArrayList  prpSrt = (ArrayList)prp.get(lprp+"S");
       ArrayList  prpVal = (ArrayList)prp.get(lprp+"V");
    %>
    
     <td colspan="2"  align="center">
           
               <html:select property="<%=prpFld1%>"   style="width:100px" onchange="<%=onChg1%>" styleId="<%=fld1%>"  > 
     
            <html:option value="<%=dfltVal%>"><%=dfltDsp%></html:option>
            <%
            for(int j=0; j < prpSrt.size(); j++) {
                String pPrt = util.nvl((String)prpPrt.get(j));
                String pSrt = util.nvl((String)prpSrt.get(j));
               
                
            %>
              <html:option value="<%=pSrt%>" ><%=pPrt%></html:option> 
            <%}%>
              </html:select>
            </td>
            
          <%}else if(lprpTyp.equals("D")){%>
        <td bgcolor="#FFFFFF" align="center">
        <html:text property="<%=prpFld1%>" styleClass="txtStyle"  styleId="<%=prpFld1%>"  size="9" maxlength="25" /> <a href="#"  onClick="setYears(1900, '<%=info.getCurrentYear()%>' );showCalender(this, '<%=prpFld1%>');">
                <img src="<%=info.getReqUrl()%>/images/calender.png"></a></td>
        <td bgcolor="#FFFFFF" align="center"><html:text property="<%=prpFld2%>" styleId="<%=prpFld2%>" styleClass="txtStyle"    size="9" maxlength="25"  /> <a href="#"  onClick="setYears(1900, '<%=info.getCurrentYear()%>' );showCalender(this, '<%=prpFld2%>');">
                <img src="<%=info.getReqUrl()%>/images/calender.png"></a></td>
        
        <%} else if(lprpTyp.equals("T")){%>
        <td bgcolor="#FFFFFF" align="center" colspan="2">
        <html:text property="<%=prpFld1%>" styleClass="txtStyle"   styleId="<%=fld1%>"  size="35"  /> 
        </td>
       
        <%} else{%>
           <td bgcolor="#FFFFFF" align="center"><html:text property="<%=prpFld1%>"  styleClass="txtStyle"  styleId="<%=fld1%>"  size="13" maxlength="25" onchange="isNumericDecimal(this.id)" /></td>
        <td bgcolor="#FFFFFF" align="center"><html:text property="<%=prpFld2%>"  styleClass="txtStyle"    size="13" maxlength="25" onchange="isNumericDecimal(this.id)" /></td>
        <%}%>
    </tr>
    <%}}}%></table>
   </td></tr>
   
   <tr>
   <td colspan="2" align="center">
   <html:submit property="value(srch)" value="Fetch" styleClass="submit" />
   </td></tr>
    </table></html:form></td></tr>
     <tr>
  <td valign="top" class="hedPg">
  <%
  String view = (String)request.getAttribute("view");
  if(view!=null){
    HashMap prp = info.getPrp();
   String ttlcts = (String)request.getAttribute("TTLCTS");
    String lstNme = (String)gtMgr.getValue("lstNmeMIXRTN");
    ArrayList grpPrpLst = (ArrayList)gtMgr.getValue(lstNme+"_GRPLST");
    
      if(grpPrpLst!=null && grpPrpLst.size()==2){ 
        String colLprp = (String)grpPrpLst.get(0);
      String rowLprp = (String)grpPrpLst.get(1);
    
      ArrayList rowPrpSLst = (ArrayList)prp.get(rowLprp+"S");
      ArrayList colPrpSLst = (ArrayList)prp.get(colLprp+"S");
       ArrayList rowPrpVLst = (ArrayList)prp.get(rowLprp+"V");
      ArrayList colPrpVLst = (ArrayList)prp.get(colLprp+"V");
      if(rowPrpSLst!=null && colPrpSLst!=null){
        
      %>
  <html:form action="/mixLkhi/mixAssortReturn.do?method=Return" method="post" onsubmit="return verifedTtlMIX()">
<html:hidden property="value(issueIdn)" styleId="issueIdn" name="mixAssortReturnLKForm" />
<html:hidden property="value(commonLprp)" styleId="commonLprp" name="mixAssortReturnLKForm" />
<html:hidden property="value(commonLprpSrt)" styleId="commonLprpSrt" name="mixAssortReturnLKForm" />

<table><tr>
<td> <html:submit property="value(return)" value="Return" styleClass="submit" /></td>
</tr>
<tr><td>Total Issue Carat :<label id="issCts" class="redLabel"> <%=util.nvl((String)request.getAttribute("ttlIssCts"))%></label> &nbsp;&nbsp;Varified Carat : <label id="rtnCts" class="redLabel"> <%=util.nvl((String)request.getAttribute("ttlRtnCts"))%> </label>  </td></tr>
<tr><td>
<div class="ContenedorTabla">
   <table id="pruebatabla" class="grid1">
   <thead>
    <tr>
   <th><%=colLprp%>/<%=rowLprp%></th>
   <%for(int i=0;i<rowPrpVLst.size();i++){ %>
   <th><%=rowPrpVLst.get(i)%> </th>
   <%}%>
   </tr>
   <tr>
   <td></td>
   <%for(int i=0;i<rowPrpVLst.size();i++){ %>
   <td align="center">CTS|RTE</td>
   <%}%>
   </tr>
   </thead>
   <tbody>
   <% for(int i=0;i<colPrpVLst.size();i++){
   String colVal = (String)colPrpVLst.get(i);
   %>
   <tr>
   <td><%=colVal%></td>
  <% for(int j=0;j<rowPrpSLst.size();j++){
  String rowVal = (String)rowPrpSLst.get(j);
  String lprpLst = colLprp+"@"+rowLprp;
  String lprpval = colPrpSLst.get(i)+"@"+rowPrpSLst.get(j);
   String fldTxtId= "CTS_"+colPrpSLst.get(i)+"_"+rowPrpSLst.get(j);
  String fldId= colPrpSLst.get(i)+"_"+rowPrpSLst.get(j);
  String fldVal = "value("+fldTxtId+")";
 
  String fldRteTxtId= "RTE_"+colPrpSLst.get(i)+"_"+rowPrpSLst.get(j);
  String fldRteId= colPrpSLst.get(i)+"_"+rowPrpSLst.get(j);
  String fldRteVal = "value("+fldRteTxtId+")";
   String onRteChange = "GenratePkt(this,'"+lprpLst+"','"+lprpval+"','RTE')";
  String onChange = "GenratePkt(this,'"+lprpLst+"','"+lprpval+"','CRTWT');calCullateMix(this,'issCts','rtnCts','CTS_','F','1')";
  %>
   <td style="white-space: nowrap;">
   <html:text  property="<%=fldVal%>"   styleId="<%=fldTxtId%>" name="mixAssortReturnLKForm" size="6" onchange="<%=onChange%>" />
   |   <html:text  property="<%=fldRteVal%>"   styleId="<%=fldRteTxtId%>" name="mixAssortReturnLKForm" size="6" onchange="<%=onRteChange%>" />

  </td>
   <%}%>
   </tr>
   <%}%>
   </tbody>
   </table>
   </div>
   </td></tr></table>
   
   </html:form>
       <%}}else{%>
        some error in group property..
        <%}}%>
  </td></tr>
  
    <tr>
  <td valign="top" class="hedPg">
  <%
  String viewPkt = (String)request.getAttribute("viewPkt");
  if(viewPkt!=null){
  String lstNme = (String)gtMgr.getValue("lstNmeMIXRTN");
  HashMap stockListMap = (HashMap)gtMgr.getValue(lstNme+"_FNLDTL");
  if(stockListMap!=null && stockListMap.size()>0){
    ArrayList vwPrpLst = (ArrayList)session.getAttribute("MIX_VIEW");
  %>
   <table class="grid1">
   <tr> <th>SR.</th>
        <th>Packet No.</th>
      
    <%for(int j=0; j < vwPrpLst.size(); j++ ){
    String prp = (String)vwPrpLst.get(j);
    
%>
<th title="<%=prp%>"><%=prp%></th>
<%}%>       
</tr>
 <%
 ArrayList stockIdnLst =new ArrayList();
 LinkedHashMap stockList = new LinkedHashMap(stockListMap); 

 Set<String> keys = stockList.keySet();
        for(String key: keys){
       stockIdnLst.add(key);
        }
int sr=0;
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
<td><%=sr%></td>
<td><%=stockPkt.getVnm()%><input type="hidden" id="CTS_<%=stkIdn%>"  value="<%=cts%>" /></td>

<%for(int j=0; j < vwPrpLst.size(); j++ ){
    String prp = (String)vwPrpLst.get(j);
   
    %>
    <td><%=stockPkt.getValue(prp)%></td>
<%}%>
</tr>
   <%}%>
   </table>
 <% }else{%>
 Sorry no result found..
 <%}}%>
  </td></tr>
    <tr>
    <td><jsp:include page="/footer.jsp" /> </td>
    </tr>
  </table> 
  <script src="<%=info.getReqUrl()%>/jquery/jquery-1.12.4.min.js"></script>
	<script src="<%=info.getReqUrl()%>/jquery/jquery.CongelarFilaColumna.js"></script>
	<script type="text/javascript">
		$(document).ready(function(){
			$("#pruebatabla").CongelarFilaColumna();
		});
	</script>
  </body>
</html>