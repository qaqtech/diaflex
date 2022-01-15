<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import="java.util.ArrayList,java.util.HashMap,java.util.ArrayList,java.util.Iterator,
java.util.Set,java.sql.ResultSet,ft.com.*,ft.com.pri.PriceGPMatrixForm, java.util.Date"%>

<jsp:useBean id="util" class="ft.com.JspUtil" scope="page" />
<jsp:useBean id="info" class="ft.com.InfoMgr" scope="session" />

<%@taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<%@taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>
<%
    String grpNme = (String)request.getAttribute("grpNme");
%>
<html>
    <head>
    <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/bse.js?v=<%=info.getJsVersion()%>" ></script>
        <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
        <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/style.css?v=<%=info.getJsVersion()%>"/>
         <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/bse.css?v=<%=info.getJsVersion()%>"/>
        <script type="text/javascript"  src="<%=info.getReqUrl()%>/scripts/bse_prc.js"></script>
         <script type="text/javascript"  src="<%=info.getReqUrl()%>/scripts/pri.js?v=<%=info.getJsVersion()%>"></script>
           <script type="text/javascript"  src="<%=info.getReqUrl()%>/scripts/Validation.js"></script>
         <script type="text/javascript"  src="<%=info.getReqUrl()%>/PopScripts/popup.js?v=<%=info.getJsVersion()%>"></script>
            <script src="<%=info.getReqUrl()%>/PopScripts/jquery-1.2.6.min.js" type="text/javascript"></script>
             <script src="<%=info.getReqUrl()%>/jqueryScript/jqueryScript.js?v=<%=info.getJsVersion()%>" type="text/javascript"></script>
              <script src="<%=info.getReqUrl()%>/jqueryScript/jquery.min.js" type="text/javascript"></script>
        <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/general.css?v=<%=info.getJsVersion()%>"/>
        <title>Pricing : <bean:write property="value(grpNme)"  name="priceGPMatrixForm"/></title>
    </head>
    <%
      String matIdn = (String)request.getAttribute("matIdn");
     //util.updAccessLog(request,response,"Dsp Price Matrix", "Dsp Price Matrix");
      String newSheet = util.nvl((String)request.getParameter("new"));
     ArrayList refList = (ArrayList)session.getAttribute("refList"+matIdn);
     ArrayList rowList = (ArrayList)session.getAttribute("rowList"+matIdn);
     ArrayList columList = (ArrayList)session.getAttribute("columList"+matIdn);
     ArrayList commonList = (ArrayList)session.getAttribute("commonList"+matIdn);
     HashMap prpMaps = (HashMap)session.getAttribute("prpMaps"+matIdn);
     String  columnPrpSize = (String)session.getAttribute("columnPrpSize"+matIdn);
      String  rowPrpSize = (String)session.getAttribute("rowPrpSize"+matIdn);
      String  IsManual = util.nvl((String)session.getAttribute("IsManual"+matIdn));
       HashMap matDtl = (HashMap)request.getAttribute("matDtl");
       HashMap excelMatMap =(HashMap)session.getAttribute("EXCELMAP"+matIdn);
       if(matDtl==null)
       matDtl = new HashMap();
       HashMap MNDataMap =(HashMap)session.getAttribute("MNDataMap"+matIdn);
       if(MNDataMap==null || !newSheet.equals(""))
       MNDataMap =new HashMap();
     ArrayList keyList = new ArrayList();
     HashMap cellKeyMap = new HashMap();
     HashMap mprp = info.getMprp();
     HashMap prp = info.getPrp();
     HashMap csvFileMap = new HashMap();
    int colCnt = 1;
    int rowCnt = 1;
    int rowindex=0;
    int columnindex=0;
    int rowListsz=rowList.size();
    int columListsz=columList.size();
        String logId=String.valueOf(info.getLogId());
        String onfocus="cook('"+logId+"')";
        String prpSplit = info.getPrpSplit();
        String srchID = request.getParameter("srchID");
        if(srchID==null)
        srchID=(String)request.getAttribute("srchID");
        String reloadFn ="reloadParentWindow('"+grpNme+"','"+srchID+"')";
            HashMap allPageDtl= (info.getPageDetails() == null)?new HashMap():(HashMap)info.getPageDetails();
    HashMap pageDtl=(HashMap)allPageDtl.get("PRC_SHEET");
    ArrayList prpDspBlocked = info.getPageblockList();
    ArrayList pageList=new ArrayList();
    HashMap pageDtlMap=new HashMap();
    String fld_nme="",fld_ttl="",val_cond="",dflt_val="",fld_typ="",form_nme="",lov_qry="";
        %>
 <body onfocus="<%=onfocus%>" onload="MM_preloadImages('images/faunalogo.png');<%=reloadFn%>" topmargin="0" leftmargin="0" rightmargin="0" bottommargin="0">
 <div id="backgroundPopup"></div>
<div id="popupContactSave" align="center" >
<img src="../images/loading_flame.gif" height="150px" width="150px" border="0" />
</div>
<table width="100%"  border="0" align="center" cellpadding="0" cellspacing="0" >
  <tr>
    <td height="5" valign="top" class="greyline"><img src="../images/grey_linebig.png" width="685" height="5" align="right" /></td>
  </tr>
  <tr>
  <td valign="top" class="hedPg">
  <table cellpadding="1" cellspacing="5"><tr><td valign="middle">
  <img src="../images/line.jpg" border="0" /></td><td valign="middle"> <span class="pgHed">Price Grid : <bean:write property="value(grpNme)"  name="priceGPMatrixForm" /> </span> </td>
  
</tr></table>
  </td>
  </tr>
  <tr>
  <td  valign="top" class="tdLayout"> <b>Sheet Name : <bean:write property="value(sheetNme)"  name="priceGPMatrixForm" /></b> </td>
  </tr>
  <%if((columList!=null && columList.size()>0) || (rowList!=null && rowList.size()>0)){%>
   <tr>
  <td valign="top" class="tdLayout">
  <html:form action="pri/pricegpmatrix.do?method=loadfile" method="POST" enctype="multipart/form-data">
   <html:hidden property="value(srchID)" />
   <html:hidden property="value(matIdn)" />
   <html:hidden property="value(Sheet)" value=".csv" styleId="SHEETFILE" />
   <html:hidden property="value(filety)" value="SHEETFILE" styleId="filety" />
  <table  cellpadding="2" cellspacing="2" >
      <tr><td>Load file in CSV</td>
        <td><html:file property="priFile"  name="priceGPMatrixForm"   styleId="fileUpload"  onchange="check_file();"/> </td>
        <td>   <html:submit property="value(fileLoad)" styleClass="submit" value="Load File" />
 </td>
      </tr>  
      
    </table>
 
  </html:form>
</td></tr>
  <%String msg=(String)request.getAttribute("msg");
  if(msg!=null){
  %>
  <tr>
  <td valign="top" class="tdLayout">
  <span class="redLabel"> <%=msg%> </span>
  </td></tr>
  <%}%>
   <tr>
  <td valign="top" class="tdLayout">
 
  
  </td></tr>
  
   <html:form  action="pri/pricegpmatrix.do?method=saveGrid" method="post" onsubmit="return validateCommonPRP(this)" >
     <html:hidden property="value(matIdn)" />
      <html:hidden property="value(grpNme)" />
     <input type="hidden" name="IsManual<%=matIdn%>" value="<%=IsManual%>" />
   <tr>
  <td valign="top" class="tdLayout">
  <table><tr><td> <img src="../images/single_errow.png" border="0" width="5" height="8" /> Common Properties</td></tr>
 
  <tr>
  <td  valign="top" class="tdLayout"> 
  <%
 
 if(commonList!=null  && commonList.size()>0){
  String dfltDsp = "&nbsp;&nbsp;&nbsp;&nbsp;---select---&nbsp;&nbsp;&nbsp;&nbsp;";
    String dfltVal = "";%>
  <table class="grid1"><tr><th></th><th>From</th><th>To</th> </tr>
 <%for(int i=0;i<commonList.size();i++){
 String lprp = (String)commonList.get(i);
 String lprpTyp = util.nvl((String)mprp.get(lprp+"T"));
 ArrayList prpLst = (ArrayList)prpMaps.get(lprp);
 String fld1 = lprp+"_1";
 String fld2 = lprp+"_2";
  String fld1Id = "COM_"+lprp+"_1";
 String fld2Id = "COM_"+lprp+"_2";
 String prpFld1 = "value(" + fld1 + ")" ;
 String prpFld2 = "value(" + fld2 + ")" ; 
  String  onChg1 = "setFrTo('"+ fld1Id + "','" + fld2Id + "')";
  String  onChg2 = "setFrTo('"+ fld1Id + "','" + fld2Id + "')";
 
 %>
 <tr><td><%=lprp%></td>
 <%if(lprpTyp.equals("C")){%>
  <td align="center">
  <html:select property="<%=prpFld1%>"   style="width:100px" onchange="<%=onChg1%>" styleId="<%=fld1Id%>"  > 
      <html:option value="<%=dfltVal%>"><%=dfltDsp%></html:option>
            <%
            for(int j=0; j < prpLst.size(); j++) {
                String pPrt = (String)prpLst.get(j);
            %>
              <html:option value="<%=pPrt%>" ><%=pPrt%></html:option> 
            <%}%>
              </html:select>
            
            </td>
            <td   align="center">
           
               <html:select property="<%=prpFld2%>"   style="width:100px" onchange="<%=onChg2%>" styleId="<%=fld2Id%>"  > 
     
            <html:option value="<%=dfltVal%>"><%=dfltDsp%></html:option>
            <%
            for(int j=0; j < prpLst.size(); j++) {
                String pPrt = (String)prpLst.get(j);
               
                
            %>
              <html:option value="<%=pPrt%>" ><%=pPrt%></html:option> 
            <%}%>
              </html:select>
            </td>
 <%}else{%>
    <td bgcolor="#FFFFFF" align="center"><html:text property="<%=prpFld1%>"  styleClass="txtStyle"  styleId="<%=fld1%>"  size="11" maxlength="25" onchange="isNumericDecimal(this.id)" /></td>
    <td bgcolor="#FFFFFF" align="center"><html:text property="<%=prpFld2%>"  styleClass="txtStyle"    size="11" maxlength="25" onchange="isNumericDecimal(this.id)" /></td>
     <%}%>
 </tr>
 <%}%>
 </table>
 <%}%>
  </td>
  </tr>
  </table>
  </td>
  </tr>
  <tr>
  <td  valign="top" class="tdLayout" height="10"> </td></tr>
  <tr>
  <td  valign="top" class="tdLayout"> 
  <table><tr><td>
   <logic:present property="value(matIdn)" name="priceGPMatrixForm"  >
    <html:submit property="value(modify)" styleId="saveBtn" styleClass="submit" value="Modify Changes" />
    </logic:present>
    <logic:notPresent property="value(matIdn)" name="priceGPMatrixForm">
  <html:submit property="value(save)" styleId="saveBtn" styleClass="submit" value="Save Changes" />
  </logic:notPresent>
 
  </td>
  <td>
    <%

  String csvDwn=info.getReqUrl()+"/pri/pricegpmatrix.do?method=Excel&matIdn="+matIdn;
  %>
  <a href="<%=csvDwn%>" onclick="PriceCsv('<%=csvDwn%>')">Download Csv <img src="../images/ico_file_csv.png" /></a>
  </td>
  
    <%
    pageList= ((ArrayList)pageDtl.get("BUTTON") == null)?new ArrayList():(ArrayList)pageDtl.get("BUTTON");  
            if(pageList!=null && pageList.size() >0){
            for(int j=0;j<pageList.size();j++){
            pageDtlMap=(HashMap)pageList.get(j);
            lov_qry=(String)pageDtlMap.get("lov_qry");
            if(lov_qry.equals(""))
            fld_nme="value("+(String)pageDtlMap.get("fld_nme")+")";
            else
            fld_nme=(String)pageDtlMap.get("fld_nme");
            fld_ttl=(String)pageDtlMap.get("fld_ttl");
            fld_typ=(String)pageDtlMap.get("fld_typ");
            dflt_val=(String)pageDtlMap.get("dflt_val");
            val_cond=(String)pageDtlMap.get("val_cond"); 
            val_cond=val_cond.replaceAll("URL",info.getReqUrl());
            if(fld_typ.equals("")){
            %>
    <td><html:submit property="<%=fld_nme%>" value="<%=fld_ttl%>" onclick="<%=val_cond%>" styleClass="submit"/>&nbsp;</td>
    <%}else if(fld_typ.equals("B")){%>
    <td><html:button property="<%=fld_nme%>" value="<%=fld_ttl%>" onclick="<%=val_cond%>" styleClass="submit"/>&nbsp;</td>
    <%}}}%>  

    <td>
    <input type="radio" name="cal" id="CAL_1" value="ACTUAL" checked="checked" />Actual &nbsp;
    <input type="radio" name="cal" id="CAL_2" value="PER"  />Percentage &nbsp;
    <input type="radio" name="cal" id="CAL_3" value="STEPDWN"  />Step Down &nbsp;
    <!--<input type="radio" name="activity" id="ACTIVITY_1" value="BYROW" checked="checked" />By Row &nbsp;
    <input type="radio" name="activity" id="ACTIVITY_2" value="BYCOLUMN"  />By Column &nbsp;-->
  </td>
  <td>
    <%
    String onchang="gridCalculationPricingNew();";
    if(IsManual.equals("Y")){
    onchang="gridCalculationPricingManualNew();";
   }%>
  <input type="text" name="calText" id="calText" size="10">
  <input type="button" name="Calculate" id="Calculate" value="Apply" class="submit" onclick="<%=onchang%>">
  </td>

  </tr></table>
  </td></tr>
  <tr>
  <td  valign="top" class="tdLayout" height="10"> </td></tr>
  
  <%
  int addRow = 0;
  int addCol=0;
  String keyFmt="";
  if(IsManual.equals("Y")){
  int listcnt=rowList.size();
  listcnt+=columList.size();
  String scriptkey="";
  String scriptkeycon="";
  if(columList!=null && columList.size()>0){
  scriptkey=columList.toString();
  scriptkey = scriptkey.replaceAll("\\[","");
  scriptkey = scriptkey.replaceAll("\\]","#");
  scriptkey = scriptkey.replaceAll("\\,","#");
  }
  if(rowList!=null && rowList.size()>0){
  scriptkeycon=rowList.toString();
  scriptkeycon = scriptkeycon.replaceAll("\\[","");
  scriptkeycon = scriptkeycon.replaceAll("\\]",".");
  scriptkeycon = scriptkeycon.replaceAll("\\,",".");
  }
  scriptkey=scriptkey+scriptkeycon;
  %>
   <tr>
  <td  valign="top" class="tdLayout">
  <table id="priceGrd" class="grid1">
  <tr><%addRow++; addCol++;%>
  <td nowrap="nowrap"><input type="checkbox" id="rowall" onclick="checkAllpage(this,'R_')"  />\<input type="checkbox" id="colall" onclick="checkAllpage(this,'C_')"  /></td>
  <%
  if(rowList.size()>0)
     colCnt = 30;
    
 if(columList.size()>0){
  String totalRow = util.nvl(request.getParameter("addROW"));
  if(totalRow.equals(""))
     rowCnt=30;
 else{
   rowCnt=Integer.parseInt(util.nvl(request.getParameter("addRowVal"),"0"))+Integer.parseInt(util.nvl(request.getParameter("totalRow"),"0"));
  }
    
    totalRow = util.nvl((String)request.getAttribute("rowCnt"));
    if(!totalRow.equals("")){
     int dbCnt = Integer.parseInt(totalRow)+1;
     if(rowCnt < dbCnt)
      rowCnt = dbCnt;
    }

  }
  for(int i=0;i<columList.size();i++){
   String lprp = (String)columList.get(i);
   String lprpTyp = (String)mprp.get(lprp+"T");
    ArrayList valLst = (ArrayList)prp.get(lprp+"V");
  
    keyFmt+=lprp+"CMLCNT";
   %>
  <th><%=lprp%> <%addCol++;%>
   <%if(lprpTyp.equals("C")){%>
  <div id="<%=lprp%>" class="floatingDiv">
  <div align="right">
  <span class="img" onclick="hideDiv('<%=lprp%>')"> Close</span>
  </div>
  <div class="priDiv">
   <table><tr><td>From</td><td>Value</td><td>To</td></tr>
   <tr><td>
  &nbsp;</td><td>EMPTY</td>
  <td><input type="radio" value="" id="TO_0" name="TO" onclick="setTXTVal(this,'TO','0')"/></td>
  </tr>
  <%for(int j=0;j< valLst.size();j++){
  String val = (String)valLst.get(j);
  
  %>
  
  <tr><td>
  <input type="radio" value="<%=val%>" id="FR_<%=j%>" name="FR" onclick="setTXTVal(this,'FR','<%=j%>')"/></td><td> <%=val%></td>
  <td><input type="radio" value="<%=val%>" id="TO_<%=j%>" name="TO" onclick="setTXTVal(this,'TO','<%=j%>')"/></td>
  </tr>
  <%}%>
 </table></div>
  </div>
  <%}%>
  
  </th>
  <%}%>
  <%int hdrsr=0;
  for(int j=1;j<=colCnt;j++){
     String chk="value(C_"+hdrsr+")";
   String chkId="C_"+(hdrsr);
   String insertcolonClk="insertcol('"+hdrsr+++"')";
  %>
   <td  nowrap="nowrap"><%=j%>
    <html:checkbox  property="<%=chk%>" name="priceGPMatrixForm" styleId="<%=chkId%>" value="Yes"/>
    <img src="../images/addcolumn.jpg" onclick="<%=insertcolonClk%>" title="Click Here To Add Column"/>
   </td>
   <%}%>
  </tr>
   <%
  HashMap columnHdr = new HashMap();
 
  for(int i=0;i<rowList.size();i++){
  String lrowprp = (String)rowList.get(i);
   String lprpTyp = (String)mprp.get(lrowprp+"T");
   ArrayList valLst = (ArrayList)prp.get(lrowprp+"V");
    
   addRow++;
   String readonly="";
   keyFmt+=lrowprp+"ROWCNT";
   if(lprpTyp.equals("C"))
    readonly="readonly=\"readonly\"";
  %><tr>
  <th><%=lrowprp%>
   <%if(lprpTyp.equals("C")){%>
  <div id="<%=lrowprp%>" class="floatingDiv">
  <div align="right">
  <span class="img" onclick="hideDiv('<%=lrowprp%>')"> Close</span>
  </div>
  <div class="priDiv">
   <table><tr><td>From</td><td>Value</td><td>To</td></tr>
   <tr><td>
  &nbsp;</td><td>EMPTY</td>
  <td><input type="radio" value="" id="TO_0" name="TO" onclick="setTXTVal(this,'TO','0')"/></td>
  </tr>
  <%for(int j=0;j< valLst.size();j++){
  String val = (String)valLst.get(j);
  %>
  
  <tr><td>
  <input type="radio" value="<%=val%>" id="FR_<%=j%>" name="FR" onclick="setTXTVal(this,'FR','<%=j%>')"/></td><td> <%=val%></td>
  <td><input type="radio" value="<%=val%>" id="TO_<%=j%>" name="TO" onclick="setTXTVal(this,'TO','<%=j%>')"/></td>
  </tr>
  <%}%>
 </table></div>
  </div>
  <%}%>
  
  </th>
  <%if(columList.size()>0){%>
  <td colspan="<%=columList.size()%>"></td><%}
  for(int j=0;j<colCnt;j++){
  String fldNme = lrowprp+""+j;
   String columnKey = "COL_"+j ;
   String lColHdr = util.nvl((String)columnHdr.get(columnKey));
   if(lColHdr.length() > 0)
    lColHdr += "~" +fldNme ;
   else
    lColHdr = fldNme ;
   
   columnHdr.put(columnKey, lColHdr) ;
   String fldVal = util.nvl((String)MNDataMap.get(fldNme));
  %>
  <td><input type="text" name="<%=fldNme%>" <%=readonly%> onclick="showDiv('<%=lrowprp%>',this)" Class="txtStyle"  id="<%=fldNme%>" onkeypress="return disableEnterKey(event);" size="5" value="<%=fldVal%>" /></td>
  <%}%>
  </tr>
  <%}%>
 <%
 for(int n=0;n <rowCnt;n++){
    String chk="value(R_"+n+")";
   String chkId="R_"+(n);
   String insertrowonClk="insertrow('"+n+"')";
 %>
  <tr><td nowrap="nowrap"><%=n+1%>
   <html:checkbox  property="<%=chk%>" name="priceGPMatrixForm" styleId="<%=chkId%>" value="Yes"/>
   <img src="../images/addcolumn.jpg" onclick="<%=insertrowonClk%>" title="Click Here To Add Row" />
  </td>
    <%
     String rowKey = "";
    for(int i=0;i<columList.size();i++){
   String lcolprp = (String)columList.get(i);
   String lprpTyp = (String)mprp.get(lcolprp+"T");
 
   String readonly="";
   if(lprpTyp.equals("C"))
    readonly="readonly=\"readonly\"";
    String fldNme = lcolprp+""+n;
    rowKey += fldNme+"~" ;
     String fldVal = util.nvl((String)MNDataMap.get(fldNme));
  %>
  <td><input type="text" name="<%=fldNme%>" Class="txtStyle"  id="<%=fldNme%>" <%=readonly%> onclick="showDiv('<%=lcolprp%>',this)" value="<%=fldVal%>" size="5" /></td>
  <%}%>
    <%
    String flgKey = "";
    for(int j=0;j < colCnt;j++){
     String keyCol = util.nvl((String)columnHdr.get("COL_"+j));
     
     
     if(!keyCol.equals(""))
     flgKey = rowKey+keyCol;
     else
     flgKey= rowKey.substring(0,rowKey.length()-1);
     keyList.add(flgKey);
     cellKeyMap.put(flgKey+"_ROW", n);
     cellKeyMap.put(flgKey+"_COL", j); 
    
    String fldVal = util.nvl((String)matDtl.get(n+"_"+j));
   if(fldVal.equals(""))
      fldVal = util.nvl((String)MNDataMap.get(flgKey));
       csvFileMap.put(n+"_"+j, fldVal);
       String keypress="setByKeyManual('"+n+"','"+j+"',event)";
     %>
    <td><input type="text" name="<%=flgKey%>" id="<%=flgKey%>" Class="txtStyle" value="<%=fldVal%>" size="5" onkeydown="<%=keypress%>"   onkeypress="return disableEnterKey(event);"/></td>
   
   
   
    <%}%>
    </tr>
    <%}%>
     </table> 
     
     </td></tr>
     <tr>
  <td  valign="top" class="tdLayout">
     <input type="text" name="addRowVal" id="addRowVal" size="6" value="5" />
    <input type="submit" name="addROW" value="Add Row"  />
  <input type="hidden" name="totalRow" id="totalRow"  value="<%=rowCnt%>" />
   <input type="hidden" name="totalColumn" id="totalColumn"  value="<%=colCnt%>" />
   <input type="hidden" name="scriptkey" id="scriptkey"  value="<%=scriptkey%>" />
   <input type="hidden" name="rowListsz" id="rowListsz"  value="<%=rowListsz%>" />
   <input type="hidden" name="columListsz" id="columListsz"  value="<%=columListsz%>" />
     </td></tr>
  
  <%}else{
  
  int rowprpsize = 0;
  int colPrpLstCnt = Integer.parseInt(columnPrpSize);
  int rowPrpSizeCnt = Integer.parseInt(rowPrpSize);
  int colPrpSize = columList.size();
  %>
  <tr>
  <td  valign="top" class="tdLayout">
  <table class="grid1">
 <tr bgcolor="White"><td nowrap="nowrap"> <input type="checkbox" id="rowall" onclick="checkAllpage(this,'R_')"  />\<input type="checkbox" id="colall" onclick="checkAllpage(this,'C_')"  /></td>
  <%
 
   for(int i=0;i<columList.size();i++){
   String lprp = (String)columList.get(i);
   
   %>
  <th><%=lprp%></th>
  <%}
  if(rowList!=null && rowList.size()>0){
    int hdrsr=0;
  for(int i=0;i<1;i++){
  String lprp = (String)rowList.get(i);
  ArrayList lprpList = (ArrayList)prpMaps.get(lprp);
  hdrsr=lprpList.size();
  }
  for(int i=0;i<hdrsr;i++){
   String chk="value(C_"+(i)+")";
   String chkId="C_"+(i);
  %>
  <td nowrap="nowrap" align="center"> <html:checkbox  property="<%=chk%>" name="priceGPMatrixForm" styleId="<%=chkId%>" value="Yes"/></td>
  <%}}%>
  </tr>
  <%

  HashMap columnHdr = new HashMap();
 if(rowList.size()>0){
  for(int i=0;i<rowList.size();i++){
  String lprp = (String)rowList.get(i);
  ArrayList lprpList = (ArrayList)prpMaps.get(lprp);
  
  if(lprpList!=null && lprpList.size()>0){
  rowprpsize = lprpList.size();
 
  %>
  <tr class="even"><th><%=lprp%></th>
  <% for(int n=0;n < colPrpSize;n++){%>
  <td></td>
  <%
  csvFileMap.put(i+"_"+n, "");
  }
  for(int j=0;j<lprpList.size();j++){
   String lprpVal = (String)lprpList.get(j);
   String columnKey = "COL_"+j ;
   String lColHdr = util.nvl((String)columnHdr.get(columnKey));
   if(lColHdr.length() > 0)
    lColHdr +=prpSplit+ lprpVal ;
   else
    lColHdr = lprpVal ;
   int clCnt = j+colPrpSize;
   columnHdr.put(columnKey, lColHdr) ;
   csvFileMap.put(i+"_"+clCnt,lprpVal);
 %>
 <td nowrap="nowrap" align="center"><B><%=lprpVal%></b>
 </td>
 
 <%}%>
  </tr>
  <%}}}else{%>
  <tr class="even">
  <% for(int n=0;n < colPrpSize;n++){%>
  <td>&nbsp;</td>
  <%}%>
  <td></td></tr>
  <%}%>
  <% 
  if(colPrpLstCnt==0)
   colPrpLstCnt =1;
   rowCnt= colPrpLstCnt;
  for(int m=0; m < colPrpLstCnt; m++){
  String chk="value(R_"+m+")";
  String chkId="R_"+m;
  String chkrowId="RO_"+m;
  
  %>
  <tr id="<%=chkrowId%>" ondblclick="setBGColorOnClickTR('<%=chkrowId%>')">
  <td><html:checkbox  property="<%=chk%>" name="priceGPMatrixForm" styleId="<%=chkId%>" value="Yes"/></td>
  <%
  String rowKey = "";
  int rwsz = m+rowListsz;
  for(int n=0;n < columList.size();n++){
    String lcolPrp = (String)columList.get(n);
    ArrayList colPrpList = (ArrayList)prpMaps.get(lcolPrp);
    String lRowVal = (String)colPrpList.get(m);
    rowKey += lRowVal +prpSplit ;
    columnindex=0;
    
    csvFileMap.put(rwsz+"_"+n,colPrpList.get(m));
  %>
  <td><B><%=colPrpList.get(m)%></b></td>
  <%}
  String flgKey = "";
  if(rowprpsize > 0){
  colCnt = rowprpsize;
  for(int k=0; k < rowprpsize; k++){
  flgKey = rowKey + util.nvl((String)columnHdr.get("COL_"+k));
   keyList.add(flgKey);
  String fldNm = "value("+flgKey+")";
  String fldId=rowindex+"_"+columnindex;
  String keypress="setByKey('"+fldId+"',event)";
  columnindex++;
  if(colPrpSize==0)
  colPrpSize=1;
   int clsz = k+colPrpSize;
  %>
  <td>
  <% if(matDtl!=null && matDtl.size()>0){
    String fldVal = util.nvl((String)matDtl.get(rwsz+"_"+clsz));
   
    csvFileMap.put(rwsz+"_"+clsz, fldVal);
     %>
  <html:text property="<%=fldNm%>" styleClass="txtStyle" styleId="<%=fldId%>" onkeydown="<%=keypress%>"  onkeypress="return disableEnterKey(event);"   value="<%=fldVal%>"   size="5"/>
  <%}else{
  if(excelMatMap!=null && excelMatMap.size()>0)
  csvFileMap.put(rwsz+"_"+clsz, util.nvl((String)excelMatMap.get(flgKey)));
  %>
  <html:text property="<%=fldNm%>"  styleId="<%=fldId%>" onkeydown="<%=keypress%>"   onkeypress="return disableEnterKey(event);"  styleClass="txtStyle" size="5"/>
 
  <%}%>
  </td>
  <%}
  rowindex++;
  }else{
  rowCnt=m;
  colCnt=1;
  flgKey = rowKey.substring(0, rowKey.length()-1);
  String fldNm = "value("+flgKey+")";
  keyList.add(flgKey);
   %>
  <td><% if(matDtl!=null && matDtl.size()>0){
    String fldVal = util.nvl((String)matDtl.get(m+"_1"));
      csvFileMap.put(m+"_1", fldVal);
     %>
  <html:text property="<%=fldNm%>" styleClass="txtStyle" value="<%=fldVal%>"   size="5"/>
  <%}else{
   if(excelMatMap!=null && excelMatMap.size()>0)
  csvFileMap.put(m+"_1", util.nvl((String)excelMatMap.get(flgKey)));
  %>
  <html:text property="<%=fldNm%>" styleClass="txtStyle" size="5"/>
 
  <%}%></td>
  <%}%>
  </tr>
  <%}%>
  </table>
  </td>
  </tr>
  <%}%>
  <input type="hidden" name="columnindex" id="columnindex" value="<%=columnindex%>" />
  <input type="hidden" name="rowindex" id="rowindex" value="<%=rowindex%>" />
   <input type="hidden" name="TXTID" id="TXTID" value="" />
    <html:hidden property="value(SHAPE_1)" styleId="SHAPE"/>
     <html:hidden property="value(SIZE_1)" styleId="SIZE"/>
  </html:form>
  <%}else{%>
  <tr>
  <td valign="top" class="tdLayout">
 Please Check  group definition For <bean:write property="value(grpNme)"  name="priceGPMatrixForm" />
  
  </td></tr>
  <%}%>
    <tr>
    <td><jsp:include page="/footer.jsp" /> </td>
    </tr>
  </table>
<%
rowCnt = rowCnt+rowListsz;
colCnt = colCnt + columListsz;
session.setAttribute("EXCELROW"+matIdn, rowCnt);
session.setAttribute("EXCELCOL"+matIdn, colCnt);
session.setAttribute("cssFileMap"+matIdn, csvFileMap);
session.setAttribute("KeyList"+matIdn, keyList);
session.setAttribute("cellKeyMap"+matIdn,cellKeyMap);
%>
    
    </body>
    
   </html>