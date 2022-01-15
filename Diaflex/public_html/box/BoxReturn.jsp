<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import="java.util.ArrayList,java.util.HashMap,java.sql.ResultSet,ft.com.*, java.util.Date, java.util.ArrayList, ft.com.dao.*"%>
<jsp:useBean id="info" class="ft.com.InfoMgr" scope="session" />
<jsp:useBean id="util" class="ft.com.JspUtil" scope="page" />
<%@taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>
<%@taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>Box Return</title>
      <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/bse.css?v=<%=info.getJsVersion()%>"/>
      <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/style.css?v=<%=info.getJsVersion()%>"/>
      <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/general.css?v=<%=info.getJsVersion()%>"/>
      <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/bse.js?v=<%=info.getJsVersion()%> " > </script>
      <script language="javaScript" type="text/javascript" src="<%=info.getReqUrl()%>/calendar/calendar.js"></script>
      <link href="<%=info.getReqUrl()%>/calendar/calendar.css" rel="stylesheet" type="text/css" />
      <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/priceChange.js?v=<%=info.getJsVersion()%> " > </script>
      <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/box.js?v=<%=info.getJsVersion()%> " > </script>
      <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/memoScript.js?v=<%=info.getJsVersion()%> " > </script>
        <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/ajax.js?v=<%=info.getJsVersion()%> " > </script>
        <script type="text/javascript" src="<%=info.getReqUrl()%>/PopScripts/jquery-1.2.6.min.js" > </script>
        <script type="text/javascript" src="<%=info.getReqUrl()%>/PopScripts/popup.js?v=<%=info.getJsVersion()%> " > </script>
  </head>
<%  
int accessidn=Integer.parseInt(util.nvl((String)request.getAttribute("accessidn"),"0"));
ArrayList chargesLst=(ArrayList)session.getAttribute("chargesLst");
       String view = util.nvl((String)request.getAttribute("view"));
        String logId=String.valueOf(info.getLogId());
            HashMap prpList = info.getPrp();
    HashMap mprpList = info.getMprp();
        String onfocus="cook('"+logId+"')";
        %>
 <body onfocus="<%=onfocus%>" onload="MM_preloadImages('images/faunalogo.png')" topmargin="0" leftmargin="0" rightmargin="0" bottommargin="0">
<div id="backgroundPopup"></div>
<div id="popupContactSave" align="center" >
<img src="../images/loading_flame.gif" height="150px" width="150px" border="0" />
</div>
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
  <img src="../images/line.jpg" border="0" /></td><td valign="middle"> <span class="pgHed">Box Memo Return</span> </td>
</tr></table>
  </td>
  </tr>
  <%
       String memoIdn = util.nvl((String)request.getAttribute("memoIdn"));
     memoIdn=memoIdn.replaceAll("'", "")   ;  
 HashMap allPageDtl= (info.getPageDetails() == null)?new HashMap():(HashMap)info.getPageDetails();
 HashMap pageDtl=(HashMap)allPageDtl.get("BOX_MEMO_RTN");
ArrayList pageList=new ArrayList();
HashMap pageDtlMap=new HashMap();
String fld_nme="",fld_ttl="",val_cond="",dflt_val="",fld_typ="",form_nme="",lov_qry="";
  String saleId = (String)request.getAttribute("salId");
  if(saleId!=null){
  HashMap dbinfo = info.getDmbsInfoLst();
  String cnt = (String)dbinfo.get("CNT");
 String repPath = util.nvl((String)dbinfo.get("REP_PATH"));
  String url =repPath+"/reports/rwservlet?"+cnt+"&report="+info.getRportUrl()+"\\reports\\Box_Memo_Sale.jsp&p_id="+saleId+"&destype=CACHE&desformat=pdf&p_access="+accessidn;
  %>
  <tr><td class="tdLayout">
  <%=util.nvl((String)request.getAttribute("rtnMsg"))%>
  </td></tr>
   <tr><td class="tdLayout"><div onclick="displayDiv('refDiv')"> <a href="<%=url%>"  target="_blank"><span class="txtLink" >Click Here for Report</span></a> </div></td></tr>
  <%String performaLink=(String)request.getAttribute("performaLink");
        if(performaLink!=null){
        url=info.getReqUrl()+"/marketing/performa.do?method=perInv&idn="+request.getAttribute("saleId")+"&form=Y&typ=SL&pktTyp=MIX";%>
        <tr><td height="15"  valign="top" class="tdLayout"><a href="<%=url%>"  target="_blank"><span class="txtLink" >Performa Invoice</span></a></td></tr>
       <% }%>
  <%}%>
  <tr><td valign="top" class="tdLayout">
  <table><tr><td>
  <html:form action="/box/boxReturn.do?method=load" method="POST" onsubmit="return validatememoBoxsaleRadio()">
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

<html:select property="value(byrTrm)" styleId="rlnId">
<html:option value="0">---select---</html:option>
<html:optionsCollection property="trmsLst" name="boxReturnForm"
label="trmDtl" value="relId" />
</html:select>

</td>
</tr>
<tr>
<td>Memo Type</td>
<td>
<html:select property="typ" styleId="typId" name="boxReturnForm" onchange="GetTypMemoRadioIdn()" >
<html:optionsCollection property="memoList" name="boxReturnForm"
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
<td>Memo Ids</td><td><html:text property="memoIdn" name="boxReturnForm" styleId="memoid"/></td></tr>

</table>
</td>
</tr>
</table>
<p><html:submit property="submit" value="View" styleClass="submit"/></p>
</html:form>
</td>
<td valign="top">
     <%if(!view.equals("")){%>
    <table class="grid1">
    <tr> <td></td> <td><b> Total</b></td><td><b>Selected</b></td> </tr>
    <tr> <td><b>Cts</b> </td> <td><bean:write property="cts" name="boxReturnForm" /></td><td><span id="cts">0</span></td>  </tr>
    <tr> <td><b>Avg Prc</b></td><td><bean:write property="avgPrc" name="boxReturnForm" /></td><td><span id="avgprc">0</span></td>  </tr>
    <tr> <td><b>Value </b></td><td><bean:write property="vlu" name="boxReturnForm" /></td><td><span id="vlu">0</span></td>  </tr>
    <tr><td><b>Memo Id </b></td><td colspan="2"><bean:write property="memoIdn" name="boxReturnForm" /></td></tr>
    <tr><td><b>Memo Typ </b></td><td colspan="2"><bean:write property="typ" name="boxReturnForm" /></td></tr>
    </table>
    <%}%>
    </td>
    <td valign="top">
    <table class="grid1">
    <tr><td colspan="3" align="center"><b>Buyer Details</b></td></tr>
    <tr>
    <td><b>Buyer</b></td><td><bean:write property="byr" name="boxReturnForm" /></td>
    </tr>
    <tr>
    <td><b>Email</b></td><td><bean:write property="value(email)" name="boxReturnForm" /></td>
    </tr>
    <tr>
    <td><b>Terms</b></td><td><bean:write property="value(trmsLb)" name="boxReturnForm" /></td>
    </tr>
        <tr>
    <td><b>Mobile No</b></td><td><bean:write property="value(mobile)" name="boxReturnForm" /></td>
    </tr>
        <tr>
    <td><b>Office No</b></td><td><bean:write property="value(office)" name="boxReturnForm" /></td></tr>
    
     <logic:present property="value(rmk1)" name="boxReturnForm" >
        <tr>  <td><b>Remark1 :</b></td><td> <bean:write property="value(rmk1)" name="boxReturnForm"/> </td></tr>
            </logic:present>
            <logic:present property="value(rmk2)" name="boxReturnForm" >
         <tr> <td><b>Remark2 :</b></td> <td> <bean:write property="value(rmk2)" name="boxReturnForm"/> </td></tr>
              </logic:present>
    </table>
    </td>
        <td  valign="top">
     <%
     if(!view.equals("")){
     if(chargesLst!=null && chargesLst.size()>0){%>
    <table class="grid1" id="chargesT">
    <tr>
    <td colspan="4">
    <span class="redLabel">*Uncheck if don't want to apply</span>
    </td>
    </tr>
    <%for(int i=0;i<chargesLst.size();i++){
    HashMap dtl=new HashMap();
    dtl=(HashMap)chargesLst.get(i);
    String dsc=(String)dtl.get("dsc");
    String flg=(String)dtl.get("flg");
    String autoopt=(String)dtl.get("autoopt");
    String typ=(String)dtl.get("typ");
    String fctr=(String)dtl.get("fctr");
    String fun=(String)dtl.get("fun");
    String rmk=(String)dtl.get("rmk");
    String field = "value("+typ+")";
    String fieldRmk = "value("+typ+"_rmk)";
    String onchang="chargesmanual('"+typ+"','"+i+"')";
    String onchangrmk="chargesmanualrmk('"+typ+"','"+i+"')";
    String fieldId = typ+"_rmk";
    if(flg.equals("MNL")){
    %>
    <tr><td nowrap="nowrap"><b><%=dsc%></b></td>
    <td nowrap="nowrap"><b><span id="<%=typ%>_dis"></span></b></td>
    <td nowrap="nowrap"><html:text property="<%=field%>" styleId="<%=typ%>" onchange="<%=onchang%>" name="boxReturnForm"/></td>
    <td nowrap="nowrap">
    <%if(rmk.equals("Y")){%>
    Remark<html:text property="<%=fieldRmk%>" styleId="<%=fieldId%>" onchange="<%=onchangrmk%>" name="boxReturnForm" size="10"/>
    <%}%>
    </td>
    </tr>
    <%}else{%>
    <tr><td nowrap="nowrap"><b><%=dsc%></b>
    <%if(flg.equals("AUTO") && autoopt.equals("OPT")){%>
    <input type="checkbox" name="<%=typ%>_AUTO" checked="checked" id="<%=typ%>_AUTO" onchange="validateAutoOpt('<%=typ%>_AUTO')" title="Uncheck To Optional"/>
    <%}%>
    </td>
    <td nowrap="nowrap"><b><span id="<%=typ%>_dis"></span></b></td>
    <td nowrap="nowrap">
    <html:hidden property="<%=field%>"  styleId="<%=typ%>" name="boxReturnForm"/>
    <!--<input type="button" name="charge_<%=typ%>" id="charge_<%=typ%>" value="Charge"  onclick="<%=onchang%>" class="submit" />-->
    </td><td></td></tr>
    <%}}
     String ttlDisplay="";
         
    %>
    <tr style="<%=ttlDisplay%>"><td nowrap="nowrap"><b>Total</b></td><td nowrap="nowrap"><b><span id="net_dis"></span></b></td><td></td><td></td></tr>
    </table>
    <%}}%>
    </td>
</tr></table>
</td></tr>
<tr><td>
<%
HashMap dbInfoSys = info.getDmbsInfoLst();
String cnt = (String)dbInfoSys.get("CNT");
int sr=0;
if(!view.equals("")){
ArrayList pktList = (ArrayList)info.getValue("PKTS");
String aadatcommdisplay="display:none",brk1commdisplay="display:none",brk2commdisplay="display:none",brk3commdisplay="display:none",brk4commdisplay="display:none";
ArrayList prps = (session.getAttribute("BOX_SAL_RTN") == null)?new ArrayList():(ArrayList)session.getAttribute("BOX_SAL_RTN");
ArrayList chargeLst = (ArrayList)session.getAttribute("chargeLst");
int chargeLstsz=chargeLst.size();
if(pktList!=null && pktList.size()>0){
%>
<html:form action="/box/boxReturn.do?method=save"  method="POST" onsubmit="return validateMemoRtn();">



<%if(!view.equals("")){
    if(chargesLst!=null && chargesLst.size()>0){
    for(int i=0;i<chargesLst.size();i++){
    HashMap dtl=new HashMap();
    dtl=(HashMap)chargesLst.get(i);
    String dsc=(String)dtl.get("dsc");
    String flg=(String)dtl.get("flg");
    String typ=(String)dtl.get("typ");
    String fctr=(String)dtl.get("fctr");
    String fun=(String)dtl.get("fun");
    String autoopt=(String)dtl.get("autoopt");
    String rmk=(String)dtl.get("rmk");
    String field = "value("+typ+"_save)";
    String fieldRmk = "value("+typ+"_rmksave)";
    String fieldId = typ+"_save";
    String fieldIdrmk = typ+"_rmksave";
    String fieldauto = "value("+typ+"_AUTOOPT)";
    String fieldIdauto = typ+"_AUTOOPT";
    %>
    <html:hidden property="<%=field%>" name="boxReturnForm" styleId="<%=fieldId%>"  />
   <%if(rmk.equals("Y")){%>
    <html:hidden property="<%=fieldRmk%>" name="boxReturnForm" styleId="<%=fieldIdrmk%>"  />
    <%}
    if(flg.equals("AUTO") && autoopt.equals("OPT")){%>
    <html:hidden property="<%=fieldauto%>" name="boxReturnForm" styleId="<%=fieldIdauto%>" value="N"  />
    <%}
    }%>
    <%}}%>
     <html:hidden property="value(vluamt)" name="boxReturnForm" styleId="vluamt"  />     
<html:hidden property="boxIdn" name="boxReturnForm" styleId="boxIdn"/>
<html:hidden property="nmeIdn" name="boxReturnForm"  styleId="nmeIdn" />
<html:hidden property="relIdn" name="boxReturnForm" styleId="relIdn" />
<html:hidden property="value(DLV_POPUP)" styleId="DLV_POPUP" name="boxReturnForm" />
<div id="popupContactSale" >
<table align="center" cellpadding="0" cellspacing="0" >
<tr><td>Do You Want To Confrim For delivery?</td></tr>
<tr><td><table><tr><td><html:radio property="value(isDLV)"   value="Yes"/>YES </td>
<td> <html:radio property="value(isDLV)" value="No"/>NO </td></tr></table> </td> </tr>
<tr><td><html:submit property="value(submit)" onclick="return confirmChangesWithPopup()"  value="Submit" styleClass="submit"/> </td> </tr>
</table>
</div>
<%if(chargeLst!=null && chargeLstsz>0){
for(int i=0;i<chargeLstsz;i++){
ArrayList Lst=new ArrayList();
Lst=(ArrayList)chargeLst.get(i);
String Fld = "value("+Lst.get(0)+")";
String lFldID=(String)Lst.get(0);%>
<html:hidden property="<%=Fld%>" name="boxReturnForm" styleId="<%=lFldID%>" />
<%}}%>
<!--<html:hidden property="value(lesstwy)" name="boxReturnForm" styleId="lesstwy" />
<html:hidden property="value(lessfty)" name="boxReturnForm" styleId="lessfty"  />
<html:hidden property="value(lesshun)" name="boxReturnForm" styleId="lesshun"  />-->
<tr><td valign="top" class="tdLayout">
<html:submit property="submit" value="Save Changes" onclick="return confirmChangesMIXSL();" styleClass="submit"/>
&nbsp;
<input type="button" class="submit" onclick="fullSaleRtn('S'); calculateAll();"  value="Full Sale"/>&nbsp;
<input type="button" class="submit" onclick="fullSaleRtn('R'); calculateAll();"  value="Full Return"/>
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
            val_cond=val_cond.replaceAll("~MEMOIDN~",memoIdn);
            if(fld_typ.equals("S")){
            %>
    <html:submit property="<%=fld_nme%>" value="<%=fld_ttl%>" onclick="<%=val_cond%>" styleClass="submit"/>&nbsp;
    <%}else if(fld_typ.equals("B")){%>
    <html:button property="<%=fld_nme%>" value="<%=fld_ttl%>" onclick="<%=val_cond%>" styleClass="submit"/>&nbsp;
    <%}}}
    %>
<%
  ArrayList rolenmLst=(ArrayList)info.getRoleLst();
  String usrFlg=util.nvl((String)info.getUsrFlg());
  if(usrFlg.equals("SYS") || rolenmLst.contains("ADMIN")){
  %>
  &nbsp;&nbsp;<img src="../images/edit.jpg" title="Edit Properties Module" onclick="newWindow('<%=info.getReqUrl()%>/dynamicProperty.do?method=load&mdl=BOX_SAL_RTN&sname=BOX_SAL_RTN&par=A')" border="0" width="15px" height="15px"/>
  <%}%>
</td>
</tr>
 <tr><td valign="top" class="tdLayout">


          <table>
          
           <tr>
            <td><table><tr><td><span class="pgTtl" >Exchange Rate :</span> </td>
           <td>
           <logic:equal property="value(cur)" name="boxReturnForm"   value="USD" >
           <html:text property="value(exhRte)" styleId="exhRte" readonly="true" size="5" name="boxReturnForm"  />
           </logic:equal>
            <logic:notEqual property="value(cur)"  name="boxReturnForm"   value="USD" >
            <html:text property="value(exhRte)" styleId="exhRte" size="5" name="boxReturnForm" />
            </logic:notEqual>
           </td>
           <logic:equal property="value(fnlexhRteDIS)"  name="boxReturnForm"  value="Y" >
           <td><span class="pgTtl" >Final Exchange Rate :</span> </td>
           <td>
            <html:text property="value(fnlexhRte)"  styleId="fnlexhRte" size="5" name="boxReturnForm" />
           </td>
           </logic:equal>
           <%ArrayList prpValLst = (ArrayList)prpList.get("SL_TYPV");
           if(prpValLst!=null && prpValLst.size()>0){
           %>
           <td>Sale Type:</td><td>
           <html:select property="value(sale_typ)" styleId="saleTyp" name="boxReturnForm" >
           <html:option value="">---select sale type---</html:option>
           <%for(int k=0;k<prpValLst.size();k++){ 
           String lprpVal = (String)prpValLst.get(k);
           %>
           <html:option value="<%=lprpVal%>"><%=lprpVal%></html:option>
           <%}%>
           </html:select>
           <input type="hidden" name="isSalTyp" id="isSalTyp" value="Y"  />
           </td><%}else{%><td><input type="hidden" name="isSalTyp" id="isSalTyp" value="N"  /></td><%}%>
            </tr></table></td></tr>
          <tr><td>
            <table>
                <tr>
               
              
                <td><span class="pgTtl" >buyer List</span></td>
                <td>
                <html:select property="byrIdn" name="boxReturnForm"  styleId="byrId1" onchange="GetADDR();GetBank()" >
               <html:option value="0" >---select---</html:option>
                <html:optionsCollection property="byrLst" name="boxReturnForm"  value="byrIdn" label="byrNme" />
                
                </html:select>
                </td> <td><span class="pgTtl" >Address</span></td>
                <td><html:select property="value(addr)" styleId="addrId" name="boxReturnForm" >
                 <html:optionsCollection property="addrList" name="boxReturnForm"  value="idn" label="addr" />
                </html:select>
                </td><td><span class="pgTtl" >Terms</span></td><td>
             <html:select property="value(byrTrm)" name="boxReturnForm"  styleId="rlnId1" onchange="invTermsDtls();"  >
               <html:optionsCollection property="trmsLst" name="boxReturnForm"
            label="trmDtl" value="relId" />
             </html:select>
            </td>
                </tr>
            </table></td></tr>
             <logic:present property="bankList" name="boxReturnForm" >
            <tr><td><table>
             <tr>
            <td nowrap="nowrap"><span class="pgTtl" >Company Name</span></td>
          <td>
           <html:select property="value(grpIdn)" name="boxReturnForm"  styleId="grpIdn"  >
             <html:optionsCollection property="groupList" name="boxReturnForm"
             value="idn" label="addr" />
            </html:select>
          </td>
                <td nowrap="nowrap"><span class="pgTtl" >Bank Selection</span></td>
               <td>
             
             <html:select property="value(bankIdn)" name="boxReturnForm" onchange="SetBankAddr(this)"  styleId="bankIdn"  >
             <html:optionsCollection property="bankList" name="boxReturnForm"
             value="idn" label="addr" />
            
            </html:select>
            </td>
            <td> <html:select property="value(bankAddr)" name="boxReturnForm" style="dispaly:none" styleId="bankAddr">
            <html:optionsCollection property="bnkAddrList" name="boxReturnForm"
             value="idn" label="addr" />
            </html:select>
            
            </td>
            </tr>
            </table></td>
            
            </tr>
           </logic:present>
           <tr>
            <td nowrap="nowrap"><table><tr><td><span class="pgTtl" >Courier :</span> </td>
            <td> <html:select property="value(courier)" name="boxReturnForm" style="dispaly:none" styleId="courier">
            <html:optionsCollection property="courierList" name="boxReturnForm"
            value="idn" label="addr" />
            </html:select>
            </td>
              <td><span class="pgTtl" >Remark1 :</span></td><td>  <html:text property="value(rmk1)" name="boxReturnForm"/> </td>
            
          <td><span class="pgTtl" >Remark2 :</span></td> <td>  <html:text property="value(rmk2)" name="boxReturnForm"/> </td>
        
            <%if(cnt.equalsIgnoreCase("hk")){%>
            <td nowrap="nowrap"><span class="pgTtl" >Bank through :</span></td><td>
            <html:select property="value(throubnk)" name="boxReturnForm"  styleId="throubnk">
            <html:option value="0">-----select bank through-----</html:option>
            <html:optionsCollection property="thruBankList" name="boxReturnForm"
             value="idn" label="fnme" />
            </html:select>
            </td><%}%>
            </tr></table> </td>
            </tr>
            <tr>
<td><table><tr><td><span class="pgTtl" >Commission :</span> </td>
<html:hidden property="value(aadatcommval)" styleId="aadatcommval" name="boxReturnForm" value="0"/>
<html:hidden property="value(brk1commval)" styleId="brk1commval" name="boxReturnForm" value="0"/>
<html:hidden property="value(brk2commval)" styleId="brk2commval" name="boxReturnForm" value="0"/>
<html:hidden property="value(brk3commval)" styleId="brk3commval" name="boxReturnForm" value="0"/>
<html:hidden property="value(brk4commval)" styleId="brk4commval" name="boxReturnForm" value="0"/>
            <logic:present property="value(aadatcomm)" name="boxReturnForm" >
            <%aadatcommdisplay="display:block";%>
            </logic:present>
            <td nowrap="nowrap">
            <div style="<%=aadatcommdisplay%>" id="aadatcommdisplay">
            <table>
            <tr>
            <td><span class="pgTtl" >Aadat :</span> </td>
            <td>
            <span id="aaDatNme"><bean:write property="value(aaDat)" name="boxReturnForm" /></span> :
            <span id="aaDatComm"><bean:write property="value(aadatcomm)" name="boxReturnForm" /></span> </td>
            <td><html:radio property="value(aadatpaid)" onchange="setBrokerComm('aadatcomm','Y')" styleId="aadatpaid1"  value="Y"  name="boxReturnForm"/> </td>
            <td>&nbsp;Yes</td> 
            <td><html:radio property="value(aadatpaid)"  onchange="setBrokerComm('aadatcomm','N')" styleId="aadatpaid2" value="N" name="boxReturnForm"/></td>
            <td>&nbsp;No</td>
            <td><html:text property="value(aadatcomm)" styleId="aadatcomm" readonly="true" size="3" name="boxReturnForm" /> </td>
            </tr>
            </table>
            </div>
            </td>
            <logic:present property="value(brk1comm)" name="boxReturnForm" >
            <%brk1commdisplay="display:block";%>
           </logic:present>
           <td nowrap="nowrap">
           <div  style="<%=brk1commdisplay%>" id="brk1commdisplay">
           <table>
           <tr>
           <td><span class="pgTtl" >Broker :</span> </td>
           <td id="brk1Nme"><bean:write property="value(brk1)" name="boxReturnForm" /></td><td><html:radio property="value(brk1paid)" styleId="brk1paid1" value="Y" name="boxReturnForm"/></td><td>&nbsp;Yes</td>  <td><html:radio property="value(brk2paid)"  styleId="brk1paid2"  value="N" name="boxReturnForm"/></td><td>&nbsp;No</td> <td><html:text property="value(brk1comm)"  styleId="brk1comm"  readonly="true" size="3" name="boxReturnForm" /> </td>
           </tr>
           </table>
           </div>
           </td>
            <logic:present property="value(brk2comm)" name="boxReturnForm" >
            <%brk2commdisplay="display:block";%>
            </logic:present>
           <td  nowrap="nowrap">
           <div  style="<%=brk2commdisplay%>" id="brk2commdisplay">
           <table>
           <tr>
           <td id="brk2Nme"><bean:write property="value(brk2)" name="boxReturnForm" /></td><td><html:radio property="value(brk2paid)"  styleId="brk2paid1" value="Y" name="boxReturnForm"/> </td><td>&nbsp;Yes</td> <td><html:radio property="value(brk2paid)"  styleId="brk2paid2"   value="N" name="boxReturnForm"/></td><td> &nbsp;No</td> <td><html:text property="value(brk2comm)" styleId="brk2comm" readonly="true" size="3" name="boxReturnForm" /> </td>
           </tr>
           </table>
           </div>
           </td>
           <logic:present property="value(brk3comm)" name="boxReturnForm" >
           <%brk3commdisplay="display:block";%> 
           </logic:present>
           <td  nowrap="nowrap">
           <div style="<%=brk3commdisplay%>" id="brk3commdisplay">
           <table>
           <tr>
           <td id="brk3Nme"><bean:write property="value(brk3)" name="boxReturnForm" /></td><td><html:radio property="value(brk3paid)"   styleId="brk3paid1" value="Y" name="boxReturnForm"/> </td><td>&nbsp;Yes</td> <td><html:radio property="value(brk3paid)"  styleId="brk3paid2" value="N" name="boxReturnForm"/></td><td> &nbsp;No</td>  <td><html:text property="value(brk3comm)" styleId="brk3comm" readonly="true" size="3" name="boxReturnForm" /> </td>
           </tr>
           </table>
           </div>
           </td>
            <logic:present property="value(brk4comm)" name="boxReturnForm" >
           <%brk4commdisplay="display:block";%>  
           </logic:present>
           <td  nowrap="nowrap">
           <div style="<%=brk4commdisplay%>" id="brk4commdisplay">
           <table>
           <tr>
           <td id="brk4Nme"><bean:write property="value(brk4)" name="boxReturnForm" /></td><td><html:radio property="value(brk4paid)"   styleId="brk4paid1"  value="Y" name="boxReturnForm"/> </td><td>&nbsp;Yes</td> <td><html:radio property="value(brk4paid)"   styleId="brk4paid2" value="N" name="boxReturnForm"/></td><td> &nbsp;No</td> <td><html:text property="value(brk4comm)" styleId="brk4comm" readonly="true" size="3" name="boxReturnForm" /> </td>
           </tr>
           </table>
           </div>
           </td>
            </tr></table> </td>
            </tr>
<!--<tr><td><span class="pgTtl" >Extra Charge : </span><input type="text" id="echarge" name="echarge" onchange="isNumericDecimal(this.id)"/></td>
</tr>-->
<tr><td>
<table class="grid1">
<%String style="";
String salCol ="4";
String rtnCol ="3";
if(cnt.equals("hk")){
style="display:none";
salCol ="3";
 rtnCol ="2";
}
%>
<tr>
<th><input type="checkbox" name="checkAll" id="checkAll" onclick="CheckBOXALLForm('1',this,'CHK_'); activeBoxAll(this)" /></th>
<th>Sr</th><th>Issue Date</th><th>Packet Code</th>
<th>qty</th><th>cts</th><th>Prc</th>

<th>Remark</th>
<%for(int j=0; j < prps.size(); j++) {%>
<th><%=(String)prps.get(j)%></th>
<%}%>
<th colspan="<%=salCol%>">Sale</th>
<th colspan="<%=rtnCol%>">Return</th>
<%if(cnt.equals("hk")){%>
<th colspan="<%=chargeLstsz%>">Sale Prc</th>
<%}%>

</tr>
<tr>
<td colspan="8"></td>
<%for(int j=0; j < prps.size(); j++) {%>
<td></td>
<%}%>
<td  style="<%=style%>" nowrap="nowrap">
<B>Sal Qty</b>
</td><td><B>Sal Cts</b></td><td><B>Sal Prc</b></td><td><b>Sal Amt</b></td>
<td  style="<%=style%>">
<B>Rtn Qty</b>
</td><td><B>Rtn Cts</b></td><td><B>Rtn Prc</b></td>
<%
        pageList= ((ArrayList)pageDtl.get("HEADER") == null)?new ArrayList():(ArrayList)pageDtl.get("HEADER");
            if(pageList!=null && pageList.size() >0){
            for(int j=0;j<pageList.size();j++){
            pageDtlMap=(HashMap)pageList.get(j);
            fld_ttl=(String)pageDtlMap.get("fld_ttl");
            %>
            <td nowrap="nowrap"><b><%=fld_ttl%></b></td>
            <%}}
    %>
</tr>
<%
boolean readonly=true;
if(!cnt.equals("hk")){
readonly=false;
}
if(pktList!=null & pktList.size()>0){
for(int i=0;i<pktList.size();i++){
sr = sr +1;
PktDtl pktDtl = (PktDtl)pktList.get(i);
long pktIdn = pktDtl.getPktIdn();
String mstkIdn = String.valueOf(pktIdn);
String qtyRtnId = "QR_"+mstkIdn;
String ctsRtnId = "CR_"+mstkIdn;
String qtySalId = "QS_"+mstkIdn;
String ctsSalID = "CS_"+mstkIdn;
String amtSalId = "AS_"+mstkIdn;
String prcSalId = "PS_"+mstkIdn;
String prcRtnId = "PR_"+mstkIdn;
String qtyRtn = "value(QR_"+mstkIdn+")";
String ctsRtn = "value(CR_"+mstkIdn+")";
String prcRtn = "value(PRCR_"+mstkIdn+")";
String qtySal = "value(QS_"+mstkIdn+")";
String ctsSal = "value(CS_"+mstkIdn+")";
String prcSal = "value(PRCS_"+mstkIdn+")";
String rmkTxt = "value(RMK_"+mstkIdn+")";
String checkFld = "value("+mstkIdn+")";
String checkId = "CHK_"+sr;
String onChangeQS = "calculateQty('QS','QR',"+mstkIdn+");calculateAll();";
String onChangeCS = "calculateCts('CS','CR',"+mstkIdn+");calculateAll();";
String onChangeQR = "calculateQty('QR','QS',"+mstkIdn+");calculateAll();";
String onChangeCR = "calculateCts('CR','CS',"+mstkIdn+");calculateAll();";
String onChangeAmt = "calculateAmt("+mstkIdn+");calculateAll();";
String onclick="boxreturn('"+mstkIdn+"','"+checkId+"')";
String cts=util.nvl((String)pktDtl.getValue("cts"));
String prc=util.nvl((String)pktDtl.getMemoQuot());
if(prc.equals(""))
prc="0";
if(cts.equals(""))
cts="0";
%>
<tr>
<td><html:checkbox property="<%=checkFld%>" styleId="<%=checkId%>" value="yes" name="boxReturnForm"  onclick="<%=onclick%>"/> 
<input type="hidden" name="VNM_<%=sr%>"  id="VNM_<%=sr%>" value="<%=mstkIdn%>" />
</td>
<td><%=sr%></td>
<td nowrap="nowrap"><%=pktDtl.getValue("dte")%></td>
<td><%=pktDtl.getVnm()%></td>
<td><%=pktDtl.getValue("qty")%><input type="hidden" name="qty_<%=mstkIdn%>"  id="qty_<%=mstkIdn%>" value="<%=pktDtl.getValue("qty")%>" /> </td><td><%=pktDtl.getValue("cts")%> <input type="hidden" name="cts_<%=mstkIdn%>"  id="cts_<%=mstkIdn%>" value="<%=pktDtl.getValue("cts")%>" /></td><td><%=pktDtl.getMemoQuot()%><input type="hidden" name="prc_<%=mstkIdn%>"  id="prc_<%=mstkIdn%>" value="<%=pktDtl.getMemoQuot()%>" /></td>
<td><html:text property="<%=rmkTxt%>" styleId="<%=rmkTxt%>"  name="boxReturnForm" /> </td>
<%for(int j=0; j < prps.size(); j++) {
String lprp = (String)prps.get(j);
%>
<td><%=util.nvl(pktDtl.getValue((String)prps.get(j)))%></td>
<%}%>
<td style="<%=style%>"><html:text property="<%=qtySal%>"  readonly="true" styleId="<%=qtySalId%>" onchange="<%=onChangeQS%>" size="9" styleClass="txtStyle" name="boxReturnForm" /> </td>
<td><html:text property="<%=ctsSal%>" readonly="true" size="9" styleId="<%=ctsSalID%>"  onchange="<%=onChangeCS%>" styleClass="txtStyle" name="boxReturnForm" /></td>
<td><html:text property="<%=prcSal%>" size="9" styleId="<%=prcSalId%>" onchange="<%=onChangeAmt%>" readonly="true"  styleClass="txtStyle" name="boxReturnForm" /></td>
<td><html:text property="value(amt)"  styleId="<%=amtSalId%>" size="9" readonly="true" styleClass="txtStyle" name="boxReturnForm" /></td>
<td style="<%=style%>"><html:text property="<%=qtyRtn%>"  readonly="true" styleId="<%=qtyRtnId%>" onchange="<%=onChangeQR%>" size="9" styleClass="txtStyle" name="boxReturnForm" /></td>
<td><html:text property="<%=ctsRtn%>"  readonly="true" size="9" styleId="<%=ctsRtnId%>" onchange="<%=onChangeCR%>" styleClass="txtStyle" name="boxReturnForm" /></td>
<td><html:text property="<%=prcRtn%>"  readonly="true" size="9" styleId="<%=prcRtnId%>" styleClass="txtStyle" name="boxReturnForm" /></td>
<%
        pageList= ((ArrayList)pageDtl.get("BODY") == null)?new ArrayList():(ArrayList)pageDtl.get("BODY");
            if(pageList!=null && pageList.size() >0){
            for(int j=0;j<pageList.size();j++){
            pageDtlMap=(HashMap)pageList.get(j);
            fld_ttl=(String)pageDtlMap.get("fld_ttl");
            ArrayList Lst=new ArrayList();
            Lst=(ArrayList)chargeLst.get(j);
            String outper=(String)Lst.get(1);
            double salprc=Math.round(Float.parseFloat(prc)*Float.parseFloat(outper));
            %>
            <td><b><%=salprc%></b></td>
            <%}}
    %>
</tr>
<%}}%>

</table>

 <input type="hidden" name="count" id="count" value="<%=sr%>" />
  </td></tr></table></td></tr>
  </html:form>
  <%}}%></td></tr>
  </table></body></html>
