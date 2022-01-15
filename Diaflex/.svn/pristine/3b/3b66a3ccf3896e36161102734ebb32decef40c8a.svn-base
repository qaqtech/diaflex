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
    <title>Mix Return</title>
      <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/bse.css?v=<%=info.getJsVersion()%>"/>
      <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/style.css?v=<%=info.getJsVersion()%>"/>
      <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/general.css?v=<%=info.getJsVersion()%>"/>
      <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/bse.js?v=<%=info.getJsVersion()%> " > </script>
      <script language="javaScript" type="text/javascript" src="<%=info.getReqUrl()%>/calendar/calendar.js"></script>
      <link href="<%=info.getReqUrl()%>/calendar/calendar.css" rel="stylesheet" type="text/css" />
      <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/newCalScript.js?v=<%=info.getJsVersion()%> " > </script>
      <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/box.js?v=<%=info.getJsVersion()%> " > </script>
      <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/memoScript.js?v=<%=info.getJsVersion()%> " > </script>
        <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/ajax.js?v=<%=info.getJsVersion()%> " > </script>
        <script type="text/javascript" src="<%=info.getReqUrl()%>/PopScripts/jquery-1.2.6.min.js" > </script>
        <script type="text/javascript" src="<%=info.getReqUrl()%>/PopScripts/popup.js?v=<%=info.getJsVersion()%> " > </script>
          <script type="text/javascript" src="<%=info.getReqUrl()%>/jqueryScript/jqueryScript.js?v=<%=info.getJsVersion()%> " > </script>
     <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/priceChange.js?v=<%=info.getJsVersion()%> " > </script>

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
  <img src="../images/line.jpg" border="0" /></td><td valign="middle"> <span class="pgHed">Mix Sale Return</span> </td>
</tr></table>
  </td>
  </tr>
  <%
 HashMap allPageDtl= (info.getPageDetails() == null)?new HashMap():(HashMap)info.getPageDetails();
 HashMap pageDtl=(HashMap)allPageDtl.get("BOX_MEMO_RTN");
ArrayList pageList=new ArrayList();
HashMap pageDtlMap=new HashMap();
String fld_nme="",fld_ttl="",val_cond="",dflt_val="",fld_typ="",form_nme="",lov_qry="";
   if(request.getAttribute("rtnMsg")!=null){
 
    %>
  <tr><td class="tdLayout">
   <label  class="redLabel"><%=util.nvl((String)request.getAttribute("rtnMsg"))%></label>
  </td></tr>
  <%}%>
    <tr><td valign="top" class="tdLayout">
  <table><tr><td>
  <html:form action="/mixAkt/mixReturnAction.do?method=load" method="POST" onsubmit="return validatememoBoxsaleRadio()">
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
<html:optionsCollection property="trmsLst" name="mixReturnForm"
label="trmDtl" value="relId" />
</html:select>

</td>
</tr>
<tr>
<td>Memo Type</td>
<td>
<html:select property="typ" styleId="typId" name="mixReturnForm" onchange="GetTypMemoRadioIdn()" >
<html:optionsCollection property="memoList" name="mixReturnForm"
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
<td>Memo Ids</td><td><html:text property="memoIdn" name="mixReturnForm" styleId="memoid"/></td></tr>

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
    <tr> <td><b>Cts</b> </td> <td><bean:write property="cts" name="mixReturnForm" /></td><td><span id="cts">0</span></td>  </tr>
    <tr> <td><b>Avg Prc</b></td><td><bean:write property="avgPrc" name="mixReturnForm" /></td><td><span id="avgprc">0</span></td>  </tr>
    <tr> <td><b>Value </b></td><td><bean:write property="vlu" name="mixReturnForm" /></td><td><span id="vlu">0</span></td>  </tr>
    <tr><td><b>Memo Id </b></td><td colspan="2"><bean:write property="value(memoIdn)" name="mixReturnForm" /></td></tr>
    <tr><td><b>Memo Typ </b></td><td colspan="2"><bean:write property="typ" name="mixReturnForm" /></td></tr>
    </table>
    <%}%>
    </td>
    <td valign="top">
    <table class="grid1">
    <tr><td colspan="3" align="center"><b>Buyer Details</b></td></tr>
    <tr>
    <td><b>Buyer</b></td><td><bean:write property="byr" name="mixReturnForm" /></td>
    </tr>
    <tr>
    <td><b>Email</b></td><td><bean:write property="value(email)" name="mixReturnForm" /></td>
    </tr>
    <tr>
    <td><b>Terms</b></td><td><bean:write property="value(trmsLb)" name="mixReturnForm" /></td>
    </tr>
        <tr>
    <td><b>Mobile No</b></td><td><bean:write property="value(mobile)" name="mixReturnForm" /></td>
    </tr>
        <tr>
    <td><b>Office No</b></td><td><bean:write property="value(office)" name="mixReturnForm" /></td></tr>
    
     <logic:present property="value(rmk1)" name="mixReturnForm" >
        <tr>  <td><b>Remark1 :</b></td><td> <bean:write property="value(rmk1)" name="mixReturnForm"/> </td></tr>
            </logic:present>
            <logic:present property="value(rmk2)" name="mixReturnForm" >
         <tr> <td><b>Remark2 :</b></td> <td> <bean:write property="value(rmk2)" name="mixReturnForm"/> </td></tr>
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
    <td nowrap="nowrap"><html:text property="<%=field%>" styleId="<%=typ%>" onchange="<%=onchang%>" name="mixReturnForm"/></td>
    <td nowrap="nowrap">
    <%if(rmk.equals("Y")){%>
    Remark<html:text property="<%=fieldRmk%>" styleId="<%=fieldId%>" onchange="<%=onchangrmk%>" name="mixReturnForm" size="10"/>
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
    <html:hidden property="<%=field%>"  styleId="<%=typ%>" name="mixReturnForm"/>
    <!--<input type="button" name="charge_<%=typ%>" id="charge_<%=typ%>" value="Charge"  onclick="<%=onchang%>" class="submit" />-->
    </td><td></td></tr>
    <%}}
     String ttlDisplay="";
     pageList=(ArrayList)pageDtl.get("TTL_DISPLAY");
            if(pageList!=null && pageList.size() >0){
            for(int j=0;j<pageList.size();j++){
            pageDtlMap=(HashMap)pageList.get(j);
            ttlDisplay=(String)pageDtlMap.get("dflt_val"); 
    }}
    %>
    <tr style="<%=ttlDisplay%>"><td nowrap="nowrap"><b>Total</b></td><td nowrap="nowrap"><b><span id="net_dis"></span></b></td><td></td><td></td></tr>
    </table>
    <%}}%>
    </td>
</tr></table>
</td></tr>
<tr><td>
<%
String jsScript="return confirmChangesMIXSL()";
  pageList=(ArrayList)pageDtl.get("JSSCRIPT");
  if(pageList!=null && pageList.size() >0){
            for(int j=0;j<pageList.size();j++){
            pageDtlMap=(HashMap)pageList.get(j);
            jsScript=(String)pageDtlMap.get("dflt_val"); 
    }}
HashMap dbInfoSys = info.getDmbsInfoLst();
String cnt = (String)dbInfoSys.get("CNT");
int sr=0;
if(!view.equals("")){
     String memoIdn = util.nvl((String)request.getAttribute("memoIdn"));
     memoIdn=memoIdn.replaceAll("'", "")   ;  
ArrayList pktList = (ArrayList)info.getValue("PKTS");
String aadatcommdisplay="display:none",brk1commdisplay="display:none",brk2commdisplay="display:none",brk3commdisplay="display:none",brk4commdisplay="display:none";
ArrayList prps = (session.getAttribute("BOX_SAL_RTN") == null)?new ArrayList():(ArrayList)session.getAttribute("BOX_SAL_RTN");
ArrayList chargeLst = (ArrayList)session.getAttribute("chargeLst");
int chargeLstsz=chargeLst.size();
if(pktList!=null && pktList.size()>0){
                String mnl_fields="N";
                pageList=(ArrayList)pageDtl.get("MNL_FIELDS");
                    if(pageList!=null && pageList.size() >0){
                        for(int j=0;j<pageList.size();j++){
                        pageDtlMap=(HashMap)pageList.get(j);
                        dflt_val=(String)pageDtlMap.get("dflt_val");
                        if(dflt_val.equals("Y")){
                            mnl_fields="Y";
                        }
                     }
                }
%>
<html:form action="/mixAkt/mixReturnAction.do?method=saveNW"  method="POST" >



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
    <html:hidden property="<%=field%>" name="mixReturnForm" styleId="<%=fieldId%>"  />
   <%if(rmk.equals("Y")){%>
    <html:hidden property="<%=fieldRmk%>" name="mixReturnForm" styleId="<%=fieldIdrmk%>"  />
    <%}
    if(flg.equals("AUTO") && autoopt.equals("OPT")){%>
    <html:hidden property="<%=fieldauto%>" name="mixReturnForm" styleId="<%=fieldIdauto%>" value="N"  />
    <%}
    }%>
    <%}}%>
     <html:hidden property="value(vluamt)" name="mixReturnForm" styleId="vluamt"  />     
<html:hidden property="boxIdn" name="mixReturnForm" styleId="boxIdn"/>
<html:hidden property="nmeIdn" name="mixReturnForm"  styleId="nmeIdn" />
<html:hidden property="relIdn" name="mixReturnForm" styleId="relIdn" />
<html:hidden property="value(DLV_POPUP)" styleId="DLV_POPUP" value="N" name="mixReturnForm" />
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
<html:hidden property="<%=Fld%>" name="mixReturnForm" styleId="<%=lFldID%>" />
<%}}%>
<!--<html:hidden property="value(lesstwy)" name="mixReturnForm" styleId="lesstwy" />
<html:hidden property="value(lessfty)" name="mixReturnForm" styleId="lessfty"  />
<html:hidden property="value(lesshun)" name="mixReturnForm" styleId="lesshun"  />-->
<tr><td valign="top" class="tdLayout">
<!--<html:submit property="value(sale)" value="Confirm Sale/Return" onclick="<%=jsScript%>" styleClass="submit"/>
&nbsp;
<html:submit property="value(dlv)" value="Confirm Delivery/Return" onclick="<%=jsScript%>" styleClass="submit"/>
&nbsp;
<html:submit property="value(localSale)" value="Sale + Delivery/Return" onclick="<%=jsScript%>" styleClass="submit"/>
&nbsp;
<html:submit property="value(savermk)" value="Change Remark"  styleClass="submit"/>
&nbsp;-->
 <%
    pageList= ((ArrayList)pageDtl.get("NEWBUTTON") == null)?new ArrayList():(ArrayList)pageDtl.get("NEWBUTTON");  
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
    <html:submit property="<%=fld_nme%>" value="<%=fld_ttl%>" onclick="<%=jsScript%>" styleClass="submit"/>&nbsp;
    <%}else if(fld_typ.equals("B")){%>
    <html:button property="<%=fld_nme%>" value="<%=fld_ttl%>" onclick="<%=jsScript%>" styleClass="submit"/>&nbsp;
    <%}}}
    %>
<input type="button" class="submit" onclick="fullSaleRtn('SS'); calculateAll();"  value="Full Sale"/>&nbsp;
<input type="button" class="submit" onclick="fullSaleRtn('RR'); calculateAll();"  value="Full Return"/>

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
           <logic:equal property="value(cur)" name="mixReturnForm"   value="USD" >
           <html:text property="value(exhRte)" styleId="exhRte" readonly="true" size="5" name="mixReturnForm"  />
           </logic:equal>
            <logic:notEqual property="value(cur)"  name="mixReturnForm"   value="USD" >
            <html:text property="value(exhRte)" styleId="exhRte" size="5" name="mixReturnForm" />
            </logic:notEqual>
           </td>
           <logic:equal property="value(fnlexhRteDIS)"  name="mixReturnForm"  value="Y" >
           <td><span class="pgTtl" >Final Exchange Rate :</span> </td>
           <td>
            <html:text property="value(fnlexhRte)"  styleId="fnlexhRte" size="5" name="mixReturnForm" />
           </td>
           </logic:equal>
           <%ArrayList prpValLst = (ArrayList)prpList.get("SL_TYPV");
           if(prpValLst!=null && prpValLst.size()>0){
           %>
           <td>Sale Type:</td><td>
           <html:select property="value(sale_typ)" styleId="saleTyp" name="mixReturnForm" >
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
                <html:select property="byrIdn" name="mixReturnForm"  styleId="byrId1" onchange="GetADDR();GetBank()" >
               <html:option value="0" >---select---</html:option>
                <html:optionsCollection property="byrLst" name="mixReturnForm"  value="byrIdn" label="byrNme" />
                
                </html:select>
                </td> <td><span class="pgTtl" >Address</span></td>
                <td><html:select property="value(addr)" styleId="addrId" name="mixReturnForm" >
                 <html:optionsCollection property="addrList" name="mixReturnForm"  value="idn" label="addr" />
                </html:select>
                </td><td><span class="pgTtl" >Terms</span></td><td>
             <html:select property="value(byrTeam)" name="mixReturnForm"  styleId="rlnId1" onchange="invTermsDtls();"  >
               <html:optionsCollection property="trmsLst" name="mixReturnForm"
            label="trmDtl" value="relId" />
             </html:select>
            </td>
            <td><span class="pgTtl" >Days Terms</span></td><td>
             <html:select property="value(trms_idn)" name="mixReturnForm"  styleId="rlnId1" onchange="invTermsDtls();"  >
               <html:optionsCollection property="dayTermsList" name="mixReturnForm"
            label="trmDtl" value="relId" />
             </html:select>
            </td>
                </tr>
            </table></td></tr>
             <logic:present property="bankList" name="mixReturnForm" >
            <tr><td><table>
             <tr>
            <td nowrap="nowrap"><span class="pgTtl" >Company Name</span></td>
          <td>
           <html:select property="value(grpIdn)" name="mixReturnForm"  styleId="grpIdn"  >
             <html:optionsCollection property="groupList" name="mixReturnForm"
             value="idn" label="addr" />
            </html:select>
          </td>
                <td nowrap="nowrap"><span class="pgTtl" >Bank Selection</span></td>
               <td>
             
             <html:select property="value(bankIdn)" name="mixReturnForm" onchange="SetBankAddr(this)"  styleId="bankIdn"  >
             <html:optionsCollection property="bankList" name="mixReturnForm"
             value="idn" label="addr" />
            
            </html:select>
            </td>
            <td> <html:select property="value(bankAddr)" name="mixReturnForm" style="dispaly:none" styleId="bankAddr">
            <html:optionsCollection property="bnkAddrList" name="mixReturnForm"
             value="idn" label="addr" />
            </html:select>
            
            </td>
            </tr>
            </table></td>
            
            </tr>
           </logic:present>
           <tr>
            <td nowrap="nowrap"><table><tr><td><span class="pgTtl" >Courier :</span> </td>
            <td> <html:select property="value(courier)" name="mixReturnForm" style="dispaly:none" styleId="courier">
            <html:optionsCollection property="courierList" name="mixReturnForm"
            value="idn" label="addr" />
            </html:select>
            </td>
            <%if(cnt.equalsIgnoreCase("hk")){%>
            <td nowrap="nowrap"><span class="pgTtl" >Bank through :</span></td><td>
            <html:select property="value(throubnk)" name="mixReturnForm"  styleId="throubnk">
            <html:option value="0">-----select bank through-----</html:option>
            <html:optionsCollection property="thruBankList" name="mixReturnForm"
             value="idn" label="fnme" />
            </html:select>
            </td><%}%>
            
         <td><span class="pgTtl" >Remark1 :</span></td><td>  <html:text property="value(rmk1)" name="mixReturnForm"/> </td>
            
          <td><span class="pgTtl" >Remark2 :</span></td> <td>  <html:text property="value(rmk2)" name="mixReturnForm"/> </td>
           
            
            </tr></table> </td>
            </tr>
            <%if(mnl_fields.equals("N")){%>
            <tr>
            <td><table><tr><td><span class="pgTtl" >Commission :</span> </td>
            <html:hidden property="value(aadatcommval)" styleId="aadatcommval" name="mixReturnForm" value="0"/>
            <html:hidden property="value(brk1commval)" styleId="brk1commval" name="mixReturnForm" value="0"/>
            <html:hidden property="value(brk2commval)" styleId="brk2commval" name="mixReturnForm" value="0"/>
            <html:hidden property="value(brk3commval)" styleId="brk3commval" name="mixReturnForm" value="0"/>
            <html:hidden property="value(brk4commval)" styleId="brk4commval" name="mixReturnForm" value="0"/>
            <logic:present property="value(aadatcomm)" name="mixReturnForm" >
            <%aadatcommdisplay="display:block";%>
            </logic:present>
            <td nowrap="nowrap">
            <div style="<%=aadatcommdisplay%>" id="aadatcommdisplay">
            <table>
            <tr>
            <td><span class="pgTtl" >Aadat :</span> </td>
            <td>
            <span id="aaDatNme"><bean:write property="value(aaDat)" name="mixReturnForm" /></span> :
            <span id="aadatcomm"><bean:write property="value(aadatcomm)" name="mixReturnForm" /></span> </td>
            <td><html:radio property="value(aadatpaid)"  styleId="aadatpaid1" onchange="setBrokerCommMIX('aadatcomm','Y')" value="Y"  name="mixReturnForm"/> </td>
            <td>&nbsp;Yes</td> 
            <td><html:radio property="value(aadatpaid)"   styleId="aadatpaid2" onchange="setBrokerCommMIX('aadatcomm','N')" value="N" name="mixReturnForm"/></td>
            <td>&nbsp;No</td>
            <td><html:radio property="value(aadatpaid)"  styleId="aadatpaid3" onchange="setBrokerCommMIX('aadatcomm','NN')" value="NN" name="mixReturnForm"/></td>
            <td>&nbsp;Net</td>
            <td><html:text property="value(aadatcomm)" styleId="aadatcommtxt"  size="3" name="mixReturnForm" /> </td>
            </tr>
            </table>
            </div>
            </td>
            <logic:present property="value(brk1comm)" name="mixReturnForm" >
            <%brk1commdisplay="display:block";%>
           </logic:present>
           <td nowrap="nowrap">
           <div  style="<%=brk1commdisplay%>" id="brk1commdisplay">
           <table>
           <tr>
           <td><span class="pgTtl" >Aadat2 :</span> </td>
           <td id="brk1Nme"><bean:write property="value(brk1)" name="mixReturnForm" />
           <span id="brk1comm"><bean:write property="value(brk1comm)" name="mixReturnForm" /></span></td>
           <td><html:radio property="value(brk1paid)" styleId="brk1paid1" onchange="setBrokerCommMIX('brk1comm','Y')" value="Y" name="mixReturnForm"/></td>
           <td>&nbsp;Yes</td>  
           <td><html:radio property="value(brk2paid)" onchange="setBrokerCommMIX('brk1comm','Y')" styleId="brk1paid2"  value="N" name="mixReturnForm"/></td>
           <td>&nbsp;No</td> 
           <td><html:radio property="value(brk2paid)"  styleId="brk1paid3" onchange="setBrokerCommMIX('brk1comm','NN')" value="NN" name="mixReturnForm"/></td>
            <td>&nbsp;Net</td>
           <td><html:text property="value(brk1comm)"  styleId="brk1commtxt"   size="3" name="mixReturnForm" /> </td>
           </tr>
           </table>
           </div>
           </td>
            <logic:present property="value(brk2comm)" name="mixReturnForm" >
            <%brk2commdisplay="display:block";%>
            </logic:present>
           <td  nowrap="nowrap">
           <div  style="<%=brk2commdisplay%>" id="brk2commdisplay">
           <table>
           <tr>
           <td id="brk2Nme"><bean:write property="value(brk2)" name="mixReturnForm" />
            <span id="brk2comm"><bean:write property="value(brk2comm)" name="mixReturnForm" /></span></td><td><html:radio property="value(brk2paid)"  styleId="brk2paid1" onchange="setBrokerCommMIX('brk2comm','Y')" value="Y" name="mixReturnForm"/> </td><td>&nbsp;Yes</td> <td><html:radio property="value(brk2paid)" onchange="setBrokerCommMIX('brk2comm','N')" styleId="brk2paid2"   value="N" name="mixReturnForm"/></td><td> &nbsp;No</td> <td><html:text property="value(brk2comm)" styleId="brk2commtxt"  size="3" name="mixReturnForm" /> </td>
           </tr>
           </table>
           </div>
           </td>
           <logic:present property="value(brk3comm)" name="mixReturnForm" >
           <%brk3commdisplay="display:block";%> 
           </logic:present>
           <td  nowrap="nowrap">
           <div style="<%=brk3commdisplay%>" id="brk3commdisplay">
           <table>
           <tr>
           <td id="brk3Nme"><bean:write property="value(brk3)" name="mixReturnForm" />
            <span id="brk2comm"><bean:write property="value(brk3comm)" name="mixReturnForm" /></span></td><td><html:radio property="value(brk3paid)"  onchange="setBrokerCommMIX('brk3comm','Y')" styleId="brk3paid1" value="Y" name="mixReturnForm"/> </td><td>&nbsp;Yes</td> <td><html:radio property="value(brk3paid)" onchange="setBrokerCommMIX('brk3comm','N')"  styleId="brk3paid2" value="N" name="mixReturnForm"/></td><td> &nbsp;No</td>  <td><html:text property="value(brk3comm)" styleId="brk3commtxt"  size="3" name="mixReturnForm" /> </td>
           </tr>
           </table>
           </div>
           </td>
            <logic:present property="value(brk4comm)" name="mixReturnForm" >
           <%brk4commdisplay="display:block";%>  
           </logic:present>
           <td  nowrap="nowrap">
           <div style="<%=brk4commdisplay%>" id="brk4commdisplay">
           <table>
           <tr>
           <td id="brk4Nme"><bean:write property="value(brk4)"  name="mixReturnForm" />
            <span id="brk4comm"><bean:write property="value(brk4comm)"  name="mixReturnForm" /></span></td><td><html:radio property="value(brk4paid)"  onchange="setBrokerCommMIX('brk4comm','Y')" styleId="brk4paid1"  value="Y" name="mixReturnForm"/> </td><td>&nbsp;Yes</td> <td><html:radio property="value(brk4paid)" onchange="setBrokerCommMIX('brk4comm','N')"  styleId="brk4paid2" value="N" name="mixReturnForm"/></td><td> &nbsp;No</td> <td><html:text property="value(brk4comm)" styleId="brk4commtxt" size="3" name="mixReturnForm" /> </td>
           </tr>
           </table>
           </div>
           </td>
            </tr></table> </td>
            </tr>
            <%}else{%>
            <tr>
            <td><table><tr><td><span class="pgTtl" >Commission :</span> </td>
            <html:hidden property="value(aadatcommval)" styleId="aadatcommval" name="mixReturnForm" value="0"/>
            <html:hidden property="value(brk1commval)" styleId="brk1commval" name="mixReturnForm" value="0"/>
            <html:hidden property="value(brk2commval)" styleId="brk2commval" name="mixReturnForm" value="0"/>
            <html:hidden property="value(brk3commval)" styleId="brk3commval" name="mixReturnForm" value="0"/>
            <html:hidden property="value(brk4commval)" styleId="brk4commval" name="mixReturnForm" value="0"/>
            <td nowrap="nowrap">
            <div id="aadatcommdisplay">
            <table>
            <tr>
            <td><span class="pgTtl" >Aadat :</span> </td>
            <td>
            <html:text  property="value(aaDat)" name="mixReturnForm" styleId="aaDat" style="width:180px" styleClass="sugBox" onkeypress="return disableEnterKey(event);" 
            onkeyup="doCompletion('aaDat', 'nmePopaaDat', '../ajaxAction.do?1=1&UsrTyp=AADAT', event)"
            onkeydown="setDown('nmePopaaDat', 'aadat_idn', 'aaDat',event)" onclick="document.getElementById('aaDat').autocomplete='off'"  />
                        
            <img src="../images/addrow.jpg" width="17" height="17" title="Click To get Names" onclick="doCompletionMobile('aaDat', 'nmePopaaDat', '../ajaxAction.do?1=1&UsrTyp=AADAT')">
            <html:hidden property="value(aadat_idn)" styleId="aadat_idn"  />
            <div style="position: absolute;">
                  <select id="nmePopaaDat" name="nmePopaaDat"
                    style="display:none;300px;" 
                    class="sugBoxDiv"
                    onKeyDown="setDown('nmePopaaDat', 'aadat_idn', 'aaDat',event)" 
                    onDblClick="setVal('nmePopaaDat', 'aadat_idn', 'aaDat', event);hideObj('nmePopaaDat')" 
                    onClick="setVal('nmePopaaDat', 'aadat_idn', 'aaDat', event);" 
                    size="10">
                  </select>
            </div>
            :
            <span id="aadatcomm"><bean:write property="value(aadatcomm)" name="mixReturnForm" /></span> </td>
            <td><html:radio property="value(aadatpaid)"  styleId="aadatpaid1" onchange="setBrokerCommMIX('aadatcomm','Y')" value="Y"  name="mixReturnForm"/> </td>
            <td>&nbsp;Yes</td> 
            <td><html:radio property="value(aadatpaid)"   styleId="aadatpaid2" onchange="setBrokerCommMIX('aadatcomm','N')" value="N" name="mixReturnForm"/></td>
            <td>&nbsp;No</td>
            <td><html:radio property="value(aadatpaid)"  styleId="aadatpaid3" onchange="setBrokerCommMIX('aadatcomm','NN')" value="NN" name="mixReturnForm"/></td>
            <td>&nbsp;Net</td>
            <td><html:text property="value(aadatcomm)" styleId="aadatcommtxt"  size="3" name="mixReturnForm" /> </td>
            </tr>
            </table>
            </div>
            </td>
           <td nowrap="nowrap">
           <div id="brk1commdisplay">
           <table>
           <tr>
           <td nowrap="nowrap"><span class="pgTtl" >Aadat2 :</span> </td>
           <td id="brk1Nme">
            <html:text  property="value(brk1)" name="mixReturnForm" styleId="brk1" style="width:180px" styleClass="sugBox" onkeypress="return disableEnterKey(event);" 
            onkeyup="doCompletion('brk1', 'nmePopbrk1', '../ajaxAction.do?1=1&UsrTyp=AADAT', event)"
            onkeydown="setDown('nmePopbrk1', 'mbrk1_idn', 'brk1',event)" onclick="document.getElementById('brk1').autocomplete='off'"  />
            
            <img src="../images/addrow.jpg" width="17" height="17" title="Click To get Names" onclick="doCompletionMobile('brk1', 'nmePopbrk1', '../ajaxAction.do?1=1&UsrTyp=AADAT')">
            <html:hidden property="value(mbrk1_idn)" styleId="mbrk1_idn"  />
            <div style="position: absolute;">
                  <select id="nmePopbrk1" name="nmePopbrk1"
                    style="display:none;300px;" 
                    class="sugBoxDiv"
                    onKeyDown="setDown('nmePopbrk1', 'mbrk1_idn', 'brk1',event)" 
                    onDblClick="setVal('nmePopbrk1', 'mbrk1_idn', 'brk1', event);hideObj('nmePopbrk1')" 
                    onClick="setVal('nmePopbrk1', 'mbrk1_idn', 'brk1', event);" 
                    size="10">
                  </select>
            </div>
           <span id="brk1comm"><bean:write property="value(brk1comm)" name="mixReturnForm" /></span></td>
           <td><html:radio property="value(brk1paid)" styleId="brk1paid1" onchange="setBrokerCommMIX('brk1comm','Y')" value="Y" name="mixReturnForm"/></td>
           <td>&nbsp;Yes</td>  
           <td><html:radio property="value(brk2paid)" onchange="setBrokerCommMIX('brk1comm','Y')" styleId="brk1paid2"  value="N" name="mixReturnForm"/></td>
           <td>&nbsp;No</td> 
           <td><html:radio property="value(brk2paid)"  styleId="brk1paid3" onchange="setBrokerCommMIX('brk1comm','NN')" value="NN" name="mixReturnForm"/></td>
            <td>&nbsp;Net</td>
           <td><html:text property="value(brk1comm)"  styleId="brk1commtxt"   size="3" name="mixReturnForm" /> </td>
           </tr>
           </table>
           </div>
           </td>
           </tr>
           <tr>
           <td colspan="5">
           <table>
           <tr>
           <td nowrap="nowrap"><span class="pgTtl" >Broker :</span> </td>
           <td  nowrap="nowrap">
           <div id="brk2commdisplay">
           <table>
           <tr>
           <td id="brk2Nme">
            <html:text  property="value(brk2)" name="mixReturnForm" styleId="brk2" style="width:180px" styleClass="sugBox" onkeypress="return disableEnterKey(event);" 
            onkeyup="doCompletion('brk2', 'nmePopbrk2', '../ajaxAction.do?1=1&UsrTyp=BROKER', event)"
            onkeydown="setDown('nmePopbrk2', 'mbrk2_idn', 'brk2',event)" onclick="document.getElementById('brk2').autocomplete='off'"  />
            
            <img src="../images/addrow.jpg" width="17" height="17" title="Click To get Names" onclick="doCompletionMobile('brk2', 'nmePopbrk2', '../ajaxAction.do?1=1&UsrTyp=BROKER')">
            <html:hidden property="value(mbrk2_idn)" styleId="mbrk2_idn"  />
            <div style="position: absolute;">
                  <select id="nmePopbrk2" name="nmePopbrk2"
                    style="display:none;300px;" 
                    class="sugBoxDiv"
                    onKeyDown="setDown('nmePopbrk2', 'mbrk2_idn', 'brk2',event)" 
                    onDblClick="setVal('nmePopbrk2', 'mbrk2_idn', 'brk2', event);hideObj('nmePopbrk2')" 
                    onClick="setVal('nmePopbrk2', 'mbrk2_idn', 'brk2', event);" 
                    size="10">
                  </select>
            </div>
            <span id="brk2comm"><bean:write property="value(brk2comm)" name="mixReturnForm" /></span></td><td><html:radio property="value(brk2paid)"  styleId="brk2paid1" onchange="setBrokerCommMIX('brk2comm','Y')" value="Y" name="mixReturnForm"/> </td><td>&nbsp;Yes</td> <td><html:radio property="value(brk2paid)" onchange="setBrokerCommMIX('brk2comm','N')" styleId="brk2paid2"   value="N" name="mixReturnForm"/></td><td> &nbsp;No</td> <td><html:text property="value(brk2comm)" styleId="brk2commtxt"  size="3" name="mixReturnForm" /> </td>
           </tr>
           </table>
           </div>
           </td>
           <td  nowrap="nowrap">
           <div id="brk3commdisplay">
           <table>
           <tr>
           <td id="brk3Nme">
            <html:text  property="value(brk3)" name="mixReturnForm" styleId="brk3" style="width:180px" styleClass="sugBox" onkeypress="return disableEnterKey(event);" 
            onkeyup="doCompletion('brk3', 'nmePopbrk3', '../ajaxAction.do?1=1&UsrTyp=BROKER', event)"
            onkeydown="setDown('nmePopbrk3', 'mbrk3_idn', 'brk3',event)" onclick="document.getElementById('brk3').autocomplete='off'"  />
            <img src="../images/addrow.jpg" width="17" height="17" title="Click To get Names" onclick="doCompletionMobile('brk3', 'nmePopbrk3', '../ajaxAction.do?1=1&UsrTyp=BROKER')">
            <html:hidden property="value(mbrk3_idn)" styleId="mbrk3_idn"  />
            <div style="position: absolute;">
                  <select id="nmePopbrk3" name="nmePopbrk3"
                    style="display:none;300px;" 
                    class="sugBoxDiv"
                    onKeyDown="setDown('nmePopbrk3', 'mbrk3_idn', 'brk3',event)" 
                    onDblClick="setVal('nmePopbrk3', 'mbrk3_idn', 'brk3', event);hideObj('nmePopbrk3')" 
                    onClick="setVal('nmePopbrk3', 'mbrk3_idn', 'brk3', event);" 
                    size="10">
                  </select>
            </div>
            <span id="brk2comm"><bean:write property="value(brk3comm)" name="mixReturnForm" /></span></td><td><html:radio property="value(brk3paid)"  onchange="setBrokerCommMIX('brk3comm','Y')" styleId="brk3paid1" value="Y" name="mixReturnForm"/> </td><td>&nbsp;Yes</td> <td><html:radio property="value(brk3paid)" onchange="setBrokerCommMIX('brk3comm','N')"  styleId="brk3paid2" value="N" name="mixReturnForm"/></td><td> &nbsp;No</td>  <td><html:text property="value(brk3comm)" styleId="brk3commtxt"  size="3" name="mixReturnForm" /> </td>
           </tr>
           </table>
           </div>
           </td>
           <td  nowrap="nowrap">
           <div id="brk4commdisplay">
           <table>
           <tr>
           <td id="brk4Nme">
            <html:text  property="value(brk4)" name="mixReturnForm" styleId="brk4" style="width:180px" styleClass="sugBox" onkeypress="return disableEnterKey(event);" 
            onkeyup="doCompletion('brk4', 'nmePopbrk4', '../ajaxAction.do?1=1&UsrTyp=BROKER', event)"
            onkeydown="setDown('nmePopbrk4', 'mbrk4_idn', 'brk4',event)" onclick="document.getElementById('brk4').autocomplete='off'"  />
            <img src="../images/addrow.jpg" width="17" height="17" title="Click To get Names" onclick="doCompletionMobile('brk4', 'nmePopbrk4', '../ajaxAction.do?1=1&UsrTyp=BROKER')">
            <html:hidden property="value(mbrk4_idn)" styleId="mbrk4_idn"  />
            <div style="position: absolute;">
                  <select id="nmePopbrk4" name="nmePopbrk4"
                    style="display:none;300px;" 
                    class="sugBoxDiv"
                    onKeyDown="setDown('nmePopbrk4', 'mbrk4_idn', 'brk4',event)" 
                    onDblClick="setVal('nmePopbrk4', 'mbrk4_idn', 'brk4', event);hideObj('nmePopbrk4')" 
                    onClick="setVal('nmePopbrk4', 'mbrk4_idn', 'brk4', event);" 
                    size="10">
                  </select>
            </div>
            <span id="brk4comm"><bean:write property="value(brk4comm)"  name="mixReturnForm" /></span></td><td><html:radio property="value(brk4paid)"  onchange="setBrokerCommMIX('brk4comm','Y')" styleId="brk4paid1"  value="Y" name="mixReturnForm"/> </td><td>&nbsp;Yes</td> <td><html:radio property="value(brk4paid)" onchange="setBrokerCommMIX('brk4comm','N')"  styleId="brk4paid2" value="N" name="mixReturnForm"/></td><td> &nbsp;No</td> <td><html:text property="value(brk4comm)" styleId="brk4commtxt" size="3" name="mixReturnForm" /> </td>
           </tr>
           </table>
           </div>
           </td>
            </tr>
            </table>
            </td>
            </tr></table> </td>
            </tr>
            <%}%>
<!--<tr><td><span class="pgTtl" >Extra Charge : </span><input type="text" id="echarge" name="echarge" onchange="isNumericDecimal(this.id)"/></td>
</tr>-->
<tr><td>
<table class="grid1">
<%
int colSpan=0;
String style="";

String rtnCol ="3";

%>
<tr>
<th><input type="checkbox" name="checkAll" id="checkAll" onclick="CheckBOXALLForm('1',this,'CHK_'); activeBoxAll(this)" /></th>
<th>Sr</th><th>Date</th><th>Packet Code</th>
<th>qty</th><th>cts</th><th>Prc</th><th>Remark</th>
<%for(int j=0; j < prps.size(); j++) {%>
<th><%=(String)prps.get(j)%></th>
<%}%>

<th>Sale</th>
<th colspan="<%=rtnCol%>">Return</th>
<%if(cnt.equals("hk")){%>
<th colspan="<%=chargeLstsz%>">Sale Prc</th>
<%}%>

</tr>

<tr>
<td colspan="7"></td>
<%for(int j=0; j < prps.size(); j++) {%>
<td></td>
<%}
colSpan=prps.size()+11;
%>
<td></td><td></td>
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
String onChangeQS = "";
String onChangeCS = "";
String onChangeQR = "";
String onChangeCR = "";
String onChangeAmt = "";
String ONclick="MixRtnCalCul('"+mstkIdn+"')";
String onclick="boxreturn('"+mstkIdn+"','"+checkId+"')";
String cts=util.nvl((String)pktDtl.getValue("cts"));
String prc=util.nvl((String)pktDtl.getMemoQuot());
if(prc.equals(""))
prc="0";
if(cts.equals(""))
cts="0";
%>
<tr>
<td>
<input type="checkbox" name="<%=checkId%>" id="<%=checkId%>" value="<%=mstkIdn%>" />
<input type="hidden" name="VNM_<%=sr%>"  id="VNM_<%=sr%>" value="<%=mstkIdn%>" />
</td>
<td><%=sr%></td>
<td><%=pktDtl.getValue("dte")%></td>
<td><%=pktDtl.getVnm()%></td>
<td><%=pktDtl.getValue("qty")%><input type="hidden" name="qty_<%=mstkIdn%>"  id="qty_<%=mstkIdn%>" value="<%=pktDtl.getValue("qty")%>" /> </td><td><%=pktDtl.getValue("cts")%> <input type="hidden" name="cts_<%=mstkIdn%>"  id="cts_<%=mstkIdn%>" value="<%=pktDtl.getValue("cts")%>" /></td><td><%=pktDtl.getMemoQuot()%><input type="hidden" name="prc_<%=mstkIdn%>"  id="prc_<%=mstkIdn%>" value="<%=pktDtl.getMemoQuot()%>" /></td>
<td><html:text property="<%=rmkTxt%>" styleId="<%=rmkTxt%>"  name="mixReturnForm" /> </td>
<%for(int j=0; j < prps.size(); j++) {
String lprp = (String)prps.get(j);
%>
<td><%=util.nvl(pktDtl.getValue((String)prps.get(j)))%></td>
<%}%>
<td><span class="txtLink" onclick="ShowHIDDiv('TR_<%=mstkIdn%>')"> SALE</span> </td>
<td style="<%=style%>"><html:text property="<%=qtyRtn%>"   styleId="<%=qtyRtnId%>" onchange="<%=ONclick%>" size="9" styleClass="txtStyle" name="mixReturnForm" /></td>
<td><html:text property="<%=ctsRtn%>"   size="9" styleId="<%=ctsRtnId%>" onchange="<%=ONclick%>" styleClass="txtStyle" name="mixReturnForm" /></td>
<td><html:text property="<%=prcRtn%>"  size="9" styleId="<%=prcRtnId%>" styleClass="txtStyle" name="mixReturnForm" /></td>
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
<tr id="TR_<%=mstkIdn%>" style="display:none" >
<td colspan="<%=colSpan%>" >
<table>
<tr><td>Total Sale Qty : <label id="<%=qtySalId%>" class="redLabel"></label>  Cts : <label id="<%=ctsSalID%>" class="redLabel"></label> Avg: <label id="<%=prcSalId%>" class="redLabel"></label> Amount:  <label id="<%=amtSalId%>" class="redLabel"></label> <img src="../images/refresh.gif" onclick="CearSalBox('<%=mstkIdn%>')" /> </td></tr>
<tr><td>

<table class="Orange" cellpadding="1" cellspacing="1">
<tr><th class="Orangeth">Sr</th><th class="Orangeth">Qty</th><th class="Orangeth">Cts</th><th class="Orangeth">Price</th><th class="Orangeth">Amount</th><th class="Orangeth">Remark</th></tr>
<%for(int j=1;j<=5;j++){
String fldId=mstkIdn+"_"+j;
String qtyId="QTY_"+fldId;
String qtyFld="value("+qtyId+")";
String ctsId="CTS_"+fldId;
String ctsFld = "value("+ctsId+")";
String rteId="PRC_"+fldId;
String rteFld = "value("+rteId+")";
String amtId="AMT_"+fldId;
String amtFld = "value("+amtId+")";
String rmkId="RMK_"+fldId;
String rmkFld = "value("+rmkId+")";

%>
<tr><td><%=j%></td>
<td><html:text property="<%=qtyFld%>" size="10" styleId="<%=qtyId%>" onchange="<%=ONclick%>"  styleClass="txtStyle" name="mixReturnForm" />  </td>
<td><html:text property="<%=ctsFld%>" size="15" styleId="<%=ctsId%>"  onchange="<%=ONclick%>" styleClass="txtStyle" name="mixReturnForm" />    </td>
<td><html:text property="<%=rteFld%>" size="20" styleId="<%=rteId%>"  onchange="<%=ONclick%>" styleClass="txtStyle" name="mixReturnForm" />   </td>
<td><html:text property="<%=amtFld%>" size="20" styleId="<%=amtId%>"   styleClass="txtStyle" name="mixReturnForm" />   </td>
<td><html:text property="<%=rmkFld%>" size="20" styleId="<%=rmkId%>"   styleClass="txtStyle" name="mixReturnForm" />   </td>

</tr>
<%}%>
</table>
</td></tr></table>

</td>
</tr>
<%}}%>

</table>

 <input type="hidden" name="count" id="count" value="<%=sr%>" />
  </td></tr>
  <tr><td>
  <table>
  
  <tr>
  <td>
  
  <%HashMap SUMMRYDLT= (HashMap)request.getAttribute("SUMMRYDLT");
  if(SUMMRYDLT!=null){%>
  <table><tr>
  <td valign="top">
  
   <%ArrayList DTESMMY = (ArrayList)SUMMRYDLT.get("DTESMMY");
  if(DTESMMY!=null && DTESMMY.size()>0){%>
  <b>Date Wise Sale Details</b>
  <table class="Orange"><tr><th class="Orangeth">Box</th><th class="Orangeth">Box Id</th>
  <th class="Orangeth">Date</th> <th class="Orangeth">Sale Qty</th> <th class="Orangeth">Sale Cts</th> <th class="Orangeth">Avg. Rte</th>
    <th class="Orangeth">Amount</th> 
  </tr> 
  <%for(int i=0;i<DTESMMY.size();i++){
  HashMap pktDtl = (HashMap)DTESMMY.get(i);
  %>
  <tr>
  <td><%=util.nvl((String)pktDtl.get("BOX"))%></td><td><%=util.nvl((String)pktDtl.get("BOXID"))%></td>
   <td><%=util.nvl((String)pktDtl.get("DTE"))%></td><td><%=util.nvl((String)pktDtl.get("QTYSAL"))%></td>
    <td><%=util.nvl((String)pktDtl.get("CTSSAL"))%></td><td><%=util.nvl((String)pktDtl.get("RTE"))%></td>
  <td><%=util.nvl((String)pktDtl.get("VLU"))%></td>
  </tr>
  <%}%>
  </table>
  <%}%>
  
  </td>
  
  <td valign="top">
 
  <%ArrayList TTLSMMY = (ArrayList)SUMMRYDLT.get("TTLSMMY");
  if(TTLSMMY!=null && TTLSMMY.size()>0){%>
   <b>Box wise Sale Details</b>
  <table class="Orange"><tr><th class="Orangeth">Box</th><th class="Orangeth">Box Id</th>
  <th class="Orangeth">Iss Qty</th> <th class="Orangeth">Iss Cts</th> <th class="Orangeth">Sal Qty</th> <th class="Orangeth">Sal Cts</th>
    <th class="Orangeth">Rtn Qty</th> <th class="Orangeth">Rtn Cts</th> <th class="Orangeth">Ttl Qty</th> <th class="Orangeth">Ttl Cts</th>
  </tr> 
  <%for(int i=0;i<TTLSMMY.size();i++){
  HashMap pktDtl = (HashMap)TTLSMMY.get(i);
  %>
  <tr>
  <td><%=util.nvl((String)pktDtl.get("BOX"))%></td><td><%=util.nvl((String)pktDtl.get("BOXID"))%></td>
   <td><%=util.nvl((String)pktDtl.get("CURQTY"))%></td><td><%=util.nvl((String)pktDtl.get("CURCTS"))%></td>
    <td><%=util.nvl((String)pktDtl.get("SALQTY"))%></td><td><%=util.nvl((String)pktDtl.get("SALCTS"))%></td>
    <td><%=util.nvl((String)pktDtl.get("RTNQTY"))%></td><td><%=util.nvl((String)pktDtl.get("RTNCTS"))%></td>
     <td><%=util.nvl((String)pktDtl.get("QTY"))%></td><td><%=util.nvl((String)pktDtl.get("CTS"))%></td>

  </tr>
  <%}%>
  </table>
  <%}%>
  </td>
  </tr></table>
  <%}%>
  </td>
  </tr>
  </table>
  </td></tr>
  </table></td></tr>
  </html:form>
  <%}else{%>
  Sorry no result found
  <%}}%></td></tr>
  </table></body></html>
