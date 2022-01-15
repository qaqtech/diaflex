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
    <title>WT Change Approve</title>
      <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/bse.css?v=<%=info.getJsVersion()%>"/>
      <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/style.css?v=<%=info.getJsVersion()%>"/>
      <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/general.css?v=<%=info.getJsVersion()%>"/>
      <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/bse.js?v=<%=info.getJsVersion()%> " > </script>
      <script language="javaScript" type="text/javascript" src="<%=info.getReqUrl()%>/calendar/calendar.js"></script>
      <link href="<%=info.getReqUrl()%>/calendar/calendar.css" rel="stylesheet" type="text/css" />
       <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/ajax.js?v=<%=info.getJsVersion()%> " > </script>
      <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/memoScript.js?v=<%=info.getJsVersion()%> " > </script>
      <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/newCalScript.js?v=<%=info.getJsVersion()%> " > </script>

     
  </head>
<%  
HashMap dbinfo = info.getDmbsInfoLst();

   int accessidn=Integer.parseInt(util.nvl((String)request.getAttribute("accessidn"),"0"));
    String logId=String.valueOf(info.getLogId());
     String onfocus="cook('"+logId+"')";
       HashMap allPageDtl= (info.getPageDetails() == null)?new HashMap():(HashMap)info.getPageDetails();
    HashMap pageDtl=(HashMap)allPageDtl.get("MEMO_SALE");
    ArrayList prpDspBlocked = info.getPageblockList();
    ArrayList pageList=new ArrayList();
    HashMap pageDtlMap=new HashMap();
    String fld_nme="",fld_ttl="",val_cond="",dflt_val="",fld_typ="",form_nme="",lov_qry="";
    HashMap prpList = info.getPrp();
    HashMap mprpList = info.getMprp();
    String mgmt_Dflt = util.nvl((String)dbinfo.get("MGMT_DFLT"));

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
  <img src="../images/line.jpg" border="0" /></td><td valign="middle"> <span class="pgHed">WT Change Approve</span> </td>
</tr></table>
  </td>
  </tr>
     <%String msg = (String)request.getAttribute("msg");
  if(msg!=null){
  %>
  <tr><td valign="top" class="tdLayout"> <span class="redLabel" > <%=msg%></span>
  <%
  String cnt = (String)dbinfo.get("CNT");
  String repPath = (String)dbinfo.get("REP_PATH");
    String url =repPath+"/reports/rwservlet?"+cnt+"&report="+info.getRportUrl()+"\\reports\\sale_dlv_rep.jsp&p_access="+accessidn+"&p_sal_idn="+request.getAttribute("saleId")+"&destype=CACHE&desformat=pdf";

  
  %>
        <div onclick="displayDiv('refDiv')"> <a href="<%=url%>"  target="_blank"><span class="txtLink" >Click Here for Report</span></a> </div></td>

  </td></tr>
  
  <%}else{%>
  <%
  ArrayList BOXDTLList = (ArrayList)request.getAttribute("BOXDTLList");
  String aadatcommdisplay="display:none",brk1commdisplay="display:none",brk2commdisplay="display:none",brk3commdisplay="display:none",brk4commdisplay="display:none";

  %>
    <tr><td valign="top" class="tdLayout">
    <html:form action="/marketing/memoReturn.do?method=WTCHNSAVE" method="POST" onsubmit="return confirmChanges()" >
    <table cellpadding="2" cellspacing="2">
    <tr><td>
    <table><tr><td>
          <table cellspacing="2">
          <tr><td> <span class="pgTtl" > Old Selection</span></td> <td><b>Buyer Name :</b></td><td> <bean:write property="byr" name="memoReturnForm"/> </td>
          <td><b>Sale Person :</b></td><td> <bean:write property="value(SALEMP)" name="memoReturnForm"/> </td>
          <td><b>Email :</b></td> <td> <bean:write property="value(email)" name="memoReturnForm"/> </td>
          <td><b>Mobile No :</b></td> <td> <bean:write property="value(mobile)" name="memoReturnForm"/> </td>
          <td><b>Office No :</b></td> <td> <bean:write property="value(office)" name="memoReturnForm"/> </td>          
           <logic:equal property="value(saleVlu)"  name="memoReturnForm"  value="Y" >
           <td>
            <b><bean:write property="value(saleVlubyr)" name="memoReturnForm" /></b>
           </td>
           </logic:equal>
           
           <td><b>Remark/Comment</b>&nbsp;<html:text property="value(rmk)" size="15"  /></td>
            <%ArrayList notepersonList = ((ArrayList)info.getNoteperson() == null)?new ArrayList():(ArrayList)info.getNoteperson();
            if(notepersonList.size()>0){%>
            <td>&nbsp;Note Person:&nbsp;</td>
            <td>
                  <html:select name="memoReturnForm" property="value(noteperson)" styleId="noteperson">
                  <html:option value="">---Select---</html:option><%
                  for(int i=0;i<notepersonList.size();i++){
                  ArrayList noteperson=(ArrayList)notepersonList.get(i);%>
                  <html:option value="<%=(String)noteperson.get(0)%>"> <%=(String)noteperson.get(1)%> </html:option>
                  <%}%>
                  </html:select>
            </td>
            <%}%>
          </tr>
          </table></td></tr>
          <tr>
            <td><table><tr><td><span class="pgTtl" >Exchange Rate : </span> </td>
           <td>
           <logic:equal property="value(cur)"  name="memoReturnForm"  value="USD" >
           <html:text property="value(exh_rte)" styleId="exhRte" readonly="true" size="5" name="memoReturnForm" />
           </logic:equal>
            <logic:notEqual property="value(cur)"  name="memoReturnForm"  value="USD" >
            <html:text property="value(exh_rte)"  styleId="exhRte" size="5" name="memoReturnForm" />
            </logic:notEqual>
           </td>
       <logic:equal property="value(fnlexhRteDIS)"  name="memoReturnForm"  value="Y" >
           
           <td><span class="pgTtl" >Final Exchange Rate :</span> </td>
           </logic:equal>
           <td>
            <html:text property="value(fnlexhRte)" onchange="SetCalculationOnFnlExrt()"  styleId="fnlexhRte" size="5" name="memoReturnForm" />
           </td>
           <%ArrayList prpValLst = (ArrayList)prpList.get("SL_TYPV");
           if(prpValLst!=null && prpValLst.size()>0){ 
           %>
           <td>Sale Type:</td><td>
           <html:select property="value(sale_typ)" styleId="saleTyp" name="memoReturnForm" >
           <html:option value="">---select sale type---</html:option>
           <%for(int k=0;k<prpValLst.size();k++){ 
           String lprpVal = (String)prpValLst.get(k);
           %>
           <html:option value="<%=lprpVal%>"><%=lprpVal%></html:option>
           <%}%>
           </html:select>
           <input type="hidden" name="isSalTyp" id="isSalTyp" value="Y"  />
           </td><%}else{%><td><input type="hidden" name="isSalTyp" id="isSalTyp" value="N"  /></td><%}%>
           <%pageList=(ArrayList)pageDtl.get("BROKER_SLAB");
            if(pageList!=null && pageList.size() >0){%>
            <td>Brokerage Slab:</td><td><html:select property="value(broker_slab)" styleId="broker_slab" name="memoReturnForm" >
           <html:option value="">---select---</html:option>
            <%for(int j=0;j<pageList.size();j++){
            pageDtlMap=(HashMap)pageList.get(j);
            dflt_val=(String)pageDtlMap.get("dflt_val"); 
            fld_ttl=(String)pageDtlMap.get("fld_ttl");%> 
            <html:option value="<%=dflt_val%>"><%=fld_ttl%></html:option>
            <%}%>
            </html:select>
            </td>
            <%}%>
            </tr></table></td></tr>
          <tr><td>
            <table>
                <tr>
               
                <td><span class="pgTtl" >buyer List</span></td>
                <td>
                <html:select property="byrIdn" name="memoReturnForm"  styleId="byrId1" onchange="GetADDR();GetBank()" >
               <html:option value="0" >---select---</html:option>
                <html:optionsCollection property="byrLst" name="memoReturnForm"  value="byrIdn" label="byrNme" />
                
                </html:select>
                </td> 
                
                <td><html:select property="value(addr)" styleId="addrId" name="memoReturnForm" >
                
                 <html:optionsCollection property="addrList" name="memoReturnForm"  value="idn" label="addr" />
                </html:select>
                <%String addrHiddval="Y";
            pageList=(ArrayList)pageDtl.get("ADDR_HIDD");
            if(pageList!=null && pageList.size() >0){
            for(int j=0;j<pageList.size();j++){
            pageDtlMap=(HashMap)pageList.get(j);
            fld_nme=(String)pageDtlMap.get("fld_nme");
            fld_ttl=(String)pageDtlMap.get("fld_ttl");
            fld_typ=(String)pageDtlMap.get("fld_typ");
            dflt_val=(String)pageDtlMap.get("dflt_val");
            val_cond=(String)pageDtlMap.get("val_cond");
            addrHiddval="N";
            }}%>
                <html:hidden property="value(addr_HIDD)" styleId="addr_HIDD" name="memoReturnForm" value="<%=addrHiddval%>"/>
               </td>
              
                <td><span class="pgTtl" >Terms </span></td><td>
             
             <html:select property="value(byrTrm)" name="memoReturnForm"  styleId="rlnId1" onchange="invTermsDtls();"   >
         
             <html:optionsCollection property="trmsLst" name="memoReturnForm"
            label="trmDtl" value="relId" />
            
            </html:select>
            </td>
               <%
            pageList= ((ArrayList)pageDtl.get("DAYTERMS") == null)?new ArrayList():(ArrayList)pageDtl.get("DAYTERMS");  
            if(pageList!=null && pageList.size() >0){
            for(int j=0;j<pageList.size();j++){
            pageDtlMap=(HashMap)pageList.get(j);
            dflt_val=util.nvl((String)pageDtlMap.get("dflt_val"));
            if(dflt_val.equals("Y")){ %>
            <td><span class="pgTtl" >Day Terms </span></td>
            <td>
             <html:select property="value(byrDayTrm)" name="memoReturnForm"  styleId="byrDayTrm">
             <html:option value="0">-----select-----</html:option>
             <html:optionsCollection property="daytrmsLst" name="memoReturnForm"
            label="trmDtl" value="relId" />
            </html:select>
            </td>
            <%}}}%>
                </tr>
            </table></td></tr>
             <logic:present property="bankList" name="memoReturnForm" >
            <tr><td><table>
            <tr>
            <td nowrap="nowrap"><span class="pgTtl" >Company Name</span></td>
          <td>
           <html:select property="value(grpIdn)" name="memoReturnForm"  styleId="grpIdn"  >
             <html:optionsCollection property="groupList" name="memoReturnForm"
             value="idn" label="addr" />
            </html:select>
          </td>
                <td nowrap="nowrap"><span class="pgTtl" >Bank Selection</span></td>
               <td>
             
             <html:select property="value(bankIdn)" name="memoReturnForm" onchange="SetBankAddr(this)"  styleId="bankIdn"  >
             <html:optionsCollection property="bankList" name="memoReturnForm"
             value="idn" label="addr" />
            
            </html:select>
            </td>
            <td> <html:select property="value(bankAddr)"  name="memoReturnForm" style="dispaly:none" styleId="bankAddr">
            <html:optionsCollection property="bnkAddrList" name="memoReturnForm"
             value="idn" label="addr" />
            </html:select>
            
            </td>
            <td nowrap="nowrap"><table><tr><td><span class="pgTtl" >Courier :</span> </td>
            <td> <html:select property="value(courier)" name="memoReturnForm" style="dispaly:none" styleId="courier">
            <html:optionsCollection property="courierList" name="memoReturnForm"
            value="idn" label="addr" />
            </html:select>
            </td>
          
            </tr></table> </td>
            </tr>
            
            </table></td>
            
            </tr>
           </logic:present>
<tr>
<td><table><tr><td><span class="pgTtl" >Commission :</span> </td>
<html:hidden property="value(aadatcommval)" styleId="aadatcommval" name="memoReturnForm" value="0"/>
<html:hidden property="value(brk1commval)" styleId="brk1commval" name="memoReturnForm" value="0"/>
<html:hidden property="value(brk2commval)" styleId="brk2commval" name="memoReturnForm" value="0"/>
<html:hidden property="value(brk3commval)" styleId="brk3commval" name="memoReturnForm" value="0"/>
<html:hidden property="value(brk4commval)" styleId="brk4commval" name="memoReturnForm" value="0"/>
            <logic:present property="value(aadatcomm)" name="memoReturnForm" >
            <%aadatcommdisplay="display:block";%>
            </logic:present>
            <td nowrap="nowrap">
            <div style="<%=aadatcommdisplay%>" id="aadatcommdisplay">
            <table>
            <tr>
            <td><span class="pgTtl" >Aadat :</span> </td>
            <td>
            <span id="aaDatNme"><bean:write property="value(aaDat)" name="memoReturnForm" /></span> :
            <span id="aadatcomm"><bean:write property="value(aadatcomm)" name="memoReturnForm" /></span> </td>
            <td><html:radio property="value(aadatpaid)"  styleId="aadatpaid1" onchange="setBrokerCommMIX('aadatcomm','Y')" value="Y"  name="memoReturnForm"/> </td>
            <td>&nbsp;Yes</td> 
            <td><html:radio property="value(aadatpaid)"   styleId="aadatpaid2" onchange="setBrokerCommMIX('aadatcomm','N')" value="N" name="memoReturnForm"/></td>
            <td>&nbsp;No</td>
            <td><html:text property="value(aadatcomm)" styleId="aadatcommtxt"  size="3" name="memoReturnForm" /> </td>
            </tr>
            </table>
            </div>
            </td>
            <logic:present property="value(brk1comm)" name="memoReturnForm" >
            <%brk1commdisplay="display:block";%>
           </logic:present>
           <td nowrap="nowrap">
           <div  style="<%=brk1commdisplay%>" id="brk1commdisplay">
           <table>
           <tr>
           <td><span class="pgTtl" >Broker :</span> </td>
           <td id="brk1Nme"><bean:write property="value(brk1)" name="memoReturnForm" />
           <span id="brk1comm"><bean:write property="value(brk1comm)" name="memoReturnForm" /></span></td><td><html:radio property="value(brk1paid)" styleId="brk1paid1" onchange="setBrokerCommMIX('brk1comm','Y')" value="Y" name="memoReturnForm"/></td><td>&nbsp;Yes</td>  <td><html:radio property="value(brk2paid)" onchange="setBrokerCommMIX('brk1comm','Y')" styleId="brk1paid2"  value="N" name="memoReturnForm"/></td><td>&nbsp;No</td> <td><html:text property="value(brk1comm)"  styleId="brk1commtxt"   size="3" name="memoReturnForm" /> </td>
           </tr>
           </table>
           </div>
           </td>
            <logic:present property="value(brk2comm)" name="memoReturnForm" >
            <%brk2commdisplay="display:block";%>
            </logic:present>
           <td  nowrap="nowrap">
           <div  style="<%=brk2commdisplay%>" id="brk2commdisplay">
           <table>
           <tr>
           <td id="brk2Nme"><bean:write property="value(brk2)" name="memoReturnForm" />
            <span id="brk2comm"><bean:write property="value(brk2comm)" name="memoReturnForm" /></span></td><td><html:radio property="value(brk2paid)"  styleId="brk2paid1" onchange="setBrokerCommMIX('brk2comm','Y')" value="Y" name="memoReturnForm"/> </td><td>&nbsp;Yes</td> <td><html:radio property="value(brk2paid)" onchange="setBrokerCommMIX('brk2comm','N')" styleId="brk2paid2"   value="N" name="memoReturnForm"/></td><td> &nbsp;No</td> <td><html:text property="value(brk2comm)" styleId="brk2commtxt"  size="3" name="memoReturnForm" /> </td>
           </tr>
           </table>
           </div>
           </td>
           <logic:present property="value(brk3comm)" name="memoReturnForm" >
           <%brk3commdisplay="display:block";%> 
           </logic:present>
           <td  nowrap="nowrap">
           <div style="<%=brk3commdisplay%>" id="brk3commdisplay">
           <table>
           <tr>
           <td id="brk3Nme"><bean:write property="value(brk3)" name="memoReturnForm" />
            <span id="brk2comm"><bean:write property="value(brk3comm)" name="memoReturnForm" /></span></td><td><html:radio property="value(brk3paid)"  onchange="setBrokerCommMIX('brk3comm','Y')" styleId="brk3paid1" value="Y" name="memoReturnForm"/> </td><td>&nbsp;Yes</td> <td><html:radio property="value(brk3paid)" onchange="setBrokerCommMIX('brk3comm','N')"  styleId="brk3paid2" value="N" name="memoReturnForm"/></td><td> &nbsp;No</td>  <td><html:text property="value(brk3comm)" styleId="brk3commtxt"  size="3" name="memoReturnForm" /> </td>
           </tr>
           </table>
           </div>
           </td>
            <logic:present property="value(brk4comm)" name="memoReturnForm" >
           <%brk4commdisplay="display:block";%>  
           </logic:present>
           <td  nowrap="nowrap">
           <div style="<%=brk4commdisplay%>" id="brk4commdisplay">
           <table>
           <tr>
           <td id="brk4Nme"><bean:write property="value(brk4)"  name="memoReturnForm" />
            <span id="brk4comm"><bean:write property="value(brk4comm)"  name="memoReturnForm" /></span></td><td><html:radio property="value(brk4paid)"  onchange="setBrokerCommMIX('brk4comm','Y')" styleId="brk4paid1"  value="Y" name="memoReturnForm"/> </td><td>&nbsp;Yes</td> <td><html:radio property="value(brk4paid)" onchange="setBrokerCommMIX('brk4comm','N')"  styleId="brk4paid2" value="N" name="memoReturnForm"/></td><td> &nbsp;No</td> <td><html:text property="value(brk4comm)" styleId="brk4commtxt" size="3" name="memoReturnForm" /> </td>
           </tr>
           </table>
           </div>
           </td>
            </tr></table> </td>
            </tr>
            <tr><td>
            <table cellpadding="2"  cellspacing="2">
            <tr>
            <td><span class="pgTtl" >Totals :</span> </td>
            <td><b>cts</b> : <span id="cts">0</span></td>
             <td><b>vlue</b>: <span id="vlu">0</span> <span id="net_dis"></span></td>
            </tr>
            </table>
            
            </td></tr>
          </table>
         </td>
         <td valign="top">
         <%if(request.getAttribute("view")!=null){
     ArrayList chargesLst=(ArrayList)session.getAttribute("chargesLst");
     HashMap fisalcharges=(HashMap)session.getAttribute("fisalcharges");
     if(chargesLst!=null && chargesLst.size()>0){%>
    <table class="grid1">
    <tr>
    <td colspan="4">
    <span class="redLabel">*Uncheck if don't want to apply</span>
    </td>
    </tr>
    <%for(int i=0;i<chargesLst.size();i++){
    HashMap dtl=new HashMap();
    dtl=(HashMap)chargesLst.get(i);
    String dsc=(String)dtl.get("dsc");
    String autoopt=(String)dtl.get("autoopt");
    String flg=(String)dtl.get("flg");
    String typ=(String)dtl.get("typ");
    String fctr=(String)dtl.get("fctr");
    String fun=(String)dtl.get("fun");
    String rmk=(String)dtl.get("rmk");
    String field = "value("+typ+")";
    String fieldRmk = "value("+typ+"_rmk)";
    String onchang="chargesmanual('"+typ+"','"+i+"')";
    String onchangrmk="chargesmanualrmk('"+typ+"','"+i+"')";
    String chargesfixed="chargesfixed('"+typ+"')";
    String fieldId = typ+"_rmk";
    if(flg.equals("MNL")){
    %>
    <tr>
    <td nowrap="nowrap"><b><%=dsc%></b></td> 
    <td nowrap="nowrap"><b><span id="<%=typ%>_dis"></span></b></td>
    <td nowrap="nowrap"><html:text property="<%=field%>" size="6" styleId="<%=typ%>" onchange="<%=onchang%>" name="memoReturnForm"/></td>
    <td nowrap="nowrap">
    <%if(rmk.equals("Y")){%>
    Remark<html:text property="<%=fieldRmk%>" styleId="<%=fieldId%>" onchange="<%=onchangrmk%>" name="memoReturnForm" size="10"/>
    <%}%>
    </td>
    </tr>
    <%}else{%>
    <tr nowrap="nowrap"><td nowrap="nowrap"><b><%=dsc%></b>
    <%if((flg.equals("AUTO") && autoopt.equals("OPT")) || (flg.equals("FIX") && autoopt.equals("OPT"))){
    String chk="checked=\"checked\"";
    if((typ.contains("IMP_DUTY") || typ.contains("SHP")))
    chk="";
    %>
    <input type="checkbox" name="<%=typ%>_AUTO" id="<%=typ%>_AUTO" <%=chk%> onchange="validateAutoOpt('<%=typ%>_AUTO')" title="Uncheck if don't wont apply"/>
    <%}%>
    </td>
    <td nowrap="nowrap"><b><span id="<%=typ%>_dis"></span></b></td>
    <td nowrap="nowrap">
    <%if(!flg.equals("FIX")){%>
    <html:hidden property="<%=field%>"  styleId="<%=typ%>" name="memoReturnForm"/>
    <!--<input type="button" name="charge_<%=typ%>" id="charge_<%=typ%>" value="Charge"  onclick="<%=onchang%>" class="submit" />-->
    <%}else{
    ArrayList dataLst=new ArrayList();
    ArrayList data=new ArrayList();
    dataLst=(ArrayList)fisalcharges.get(typ);
    %>
    <html:select property="<%=field%>" styleId="<%=typ%>" name="memoReturnForm" onchange="<%=chargesfixed%>">
    <%for(int c=0;c<dataLst.size();c++){
    data=new ArrayList();
    data=(ArrayList)dataLst.get(c);
    String fldval=util.nvl((String)data.get(1));
    String flddsp=util.nvl((String)data.get(0));
    %>
    <html:option value="<%=fldval%>" ><%=flddsp%></html:option>
    <%}%>
    </html:select>
    <%}%>
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
         
         </tr>
    <tr><td colspan="2">
    <html:hidden property="value(IDNSTR)" name="memoReturnForm" />
      <html:hidden property="value(MEMOID)" name="memoReturnForm" />
       <html:hidden property="value(exh_rte)" name="memoReturnForm" />
      <html:hidden property="value(globalrmk)" name="memoReturnForm" />
         <html:hidden property="value(MEMOTYP)" name="memoReturnForm" />
          <html:hidden property="nmeIdn" name="memoReturnForm" />
         <html:hidden property="relIdn" name="memoReturnForm" />
    <html:submit property="value(sale)" value="Confirm Sale" styleClass="submit"/>
    &nbsp;&nbsp;
     <html:submit property="value(LocalSale)" value="Sale + Delivery" styleClass="submit"/>
        &nbsp;&nbsp;
    </td></tr>
    
    <tr><td colspan="2">
    <%if(BOXDTLList!=null && BOXDTLList.size()>0){
    
    
    if(request.getAttribute("view")!=null){
    ArrayList chargesLst=(ArrayList)session.getAttribute("chargesLst");
    HashMap fisalcharges=(HashMap)session.getAttribute("fisalcharges");
    if(chargesLst!=null && chargesLst.size()>0){
    for(int i=0;i<chargesLst.size();i++){
    HashMap dtl=new HashMap();
    dtl=(HashMap)chargesLst.get(i);
    String dsc=(String)dtl.get("dsc");
    String autoopt=(String)dtl.get("autoopt");
    String flg=(String)dtl.get("flg");
    String typ=(String)dtl.get("typ");
    String fctr=(String)dtl.get("fctr");
    String fun=(String)dtl.get("fun");
    String rmk=(String)dtl.get("rmk");
    String field = "value("+typ+"_save)";
    String fieldRmk = "value("+typ+"_rmksave)";
    String fieldId = typ+"_save";
    String fieldIdrmk = typ+"_rmksave";
    String fieldauto = "value("+typ+"_AUTOOPT)";
    String fieldIdauto = typ+"_AUTOOPT";
    String fieldidval="";
    if(typ.equals("MGMT") && !mgmt_Dflt.equals(""))
    fieldidval=mgmt_Dflt;
    if(flg.equals("FIX")){
    ArrayList dataLst=new ArrayList();
    ArrayList data=new ArrayList();
    dataLst=(ArrayList)fisalcharges.get(typ);
    data=(ArrayList)dataLst.get(0);
    fieldidval=util.nvl((String)data.get(1));
    }
    %>
    <html:hidden property="<%=field%>" name="memoSaleForm" styleId="<%=fieldId%>" value="<%=fieldidval%>"  />
   <%if(rmk.equals("Y")){%>
    <html:hidden property="<%=fieldRmk%>" name="memoReturnForm" styleId="<%=fieldIdrmk%>"  />
    <%}
    if((flg.equals("AUTO") && autoopt.equals("OPT")) || (flg.equals("FIX") && autoopt.equals("OPT"))){
    String chk="N";
    if((typ.contains("IMP_DUTY") || typ.contains("SHP")))
    chk="Y";
    %>
    <html:hidden property="<%=fieldauto%>" name="memoReturnForm" styleId="<%=fieldIdauto%>" value="<%=chk%>"  />
    <%}}%>
   <html:hidden property="value(vluamt)" name="memoReturnForm" styleId="vluamt"  />
    <%}}

    %>
    <table class="grid1">
    <tr><th>Box Type</th><th>Box Id</th><th>QTY</th><th>Cts</th><th>Rate</th> </tr>
    <%for(int i=0;i<BOXDTLList.size();i++){
    HashMap boxMap = (HashMap)BOXDTLList.get(i);
    String boxTyp = (String)boxMap.get("BOXTYP");
    String boxId = (String)boxMap.get("BOXID");
    String cts = (String)boxMap.get("CTS");
     String qty = (String)boxMap.get("QTY");
     String rte = (String)boxMap.get("AVGRTE");
    %>
    <tr>
    <td>
    <input type="hidden" name="CHK_<%=boxTyp%>_<%=boxId%>" id="CHK_<%=boxTyp%>_<%=boxId%>" value="<%=boxTyp%>@<%=boxId%>" />
    <%=boxTyp%></td><td><%=boxId%></td><td>
    <input type="hidden" name="QTY_<%=boxTyp%>_<%=boxId%>" id="QTY_<%=boxTyp%>_<%=boxId%>" value="<%=qty%>"/>
    <%=qty%> </td> <td>
    <%=cts%> &nbsp;&nbsp; <input type="text" onchange="totalAmountCal()" name="CTS_<%=boxTyp%>_<%=boxId%>" id="CTS_<%=boxTyp%>_<%=boxId%>" size="9" value="<%=cts%>"/> </td>
    <td>
    <%=rte%> &nbsp;&nbsp; <input type="text"  onchange="totalAmountCal()" name="RTE_<%=boxTyp%>_<%=boxId%>" id="RTE_<%=boxTyp%>_<%=boxId%>" size="9" value="<%=rte%>"/> 
    </td>
    </tr>
    <%}%>
    </table>
     <script type="text/javascript">
 <!--
 totalAmountCal();
 -->
 </script>
    
    <%}else{%>
    Sorry no result found..
    <%}%>
    </td></tr>
    </table>
    </html:form>
    </td>
    </tr>
    <%}%>
    <tr><td>
    <jsp:include page="/footer.jsp" />
    </td></tr></table>
    </body>
</html>