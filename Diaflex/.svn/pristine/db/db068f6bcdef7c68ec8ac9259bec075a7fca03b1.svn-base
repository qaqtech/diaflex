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
    <title>Assort Return</title>
    <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/bse.css?v=<%=info.getJsVersion()%>"/>
    <link href="<%=info.getReqUrl()%>/auto-search/auto-search.css?v=<%=info.getJsVersion()%>"
                rel="stylesheet" type="text/css"/>
    <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/general.css?v=<%=info.getJsVersion()%>"/>
     <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/style.css?v=<%=info.getJsVersion()%>"/>
     <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/bse.js?v=<%=info.getJsVersion()%> " > </script>
           <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/ajax.js?v=<%=info.getJsVersion()%> " > </script>

      <script type="text/javascript" src="<%=info.getReqUrl()%>/PopScripts/popup.js?v=<%=info.getJsVersion()%> " > </script>
     <script src="<%=info.getReqUrl()%>/PopScripts/jquery-1.2.6.min.js" type="text/javascript"></script>
     <script src="<%=info.getReqUrl()%>/PopScripts/jquery-1.11.3.min.js" type="text/javascript"></script>
          <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/newCalScript.js?v=<%=info.getJsVersion()%> " > </script>
           <script src="<%=info.getReqUrl()%>/auto-search/auto-search.js?v=<%=info.getJsVersion()%>" type="text/javascript"></script>
           <script src="<%=info.getReqUrl()%>/auto-search/autoajaxjs.js?v=<%=info.getJsVersion()%>" type="text/javascript"></script>


</head>

  <body>
  <div id="backgroundPopup"></div>
<div id="popupContactSave" align="center" >
<img src="../images/loading_flame.gif" height="150px" width="150px" border="0" />
</div>
  <%
  String suggScript="";
  HashMap SPLITPRPMAP = (HashMap)request.getAttribute("SPLITPRPMAP");
  ArrayList pktDtlList =  new ArrayList();
   ArrayList SPLITVIEWLST = (ArrayList)session.getAttribute("SPLITVIEWLST");
   ArrayList SPLITCOMPVIEWLST=(ArrayList)request.getAttribute("SPLITCOMPVIEWLST");
   if(SPLITCOMPVIEWLST==null)
   SPLITCOMPVIEWLST = new ArrayList();
   HashMap SPLITDIFF = (HashMap)session.getAttribute("SPLITDIFF");
   String rejcts = util.nvl(request.getParameter("COUNT"),"0");
   if(rejcts.equals(""))
   rejcts="0";
    String issID =util.nvl(request.getParameter("issID"));
     String stt = util.nvl(request.getParameter("stt"));
     String ISSTT = util.nvl((String)request.getAttribute("ISSTT"));
     String TYP  = util.nvl((String)request.getAttribute("TYP"));
   HashMap prp = info.getPrp();
  if(SPLITPRPMAP!=null){
  HashMap mprp = info.getMprp();
  String issQty = util.nvl((String)SPLITPRPMAP.get("IQ"),"0");
  String issCts = util.nvl((String)SPLITPRPMAP.get("IC"),"0");
  String issRte = util.nvl((String)SPLITPRPMAP.get("IA"),"0");
  String issAmt = util.nvl((String)SPLITPRPMAP.get("IV"),"0");
   String pendQty = util.nvl((String)SPLITPRPMAP.get("PQ"),"0");
  String pendCts = util.nvl((String)SPLITPRPMAP.get("PC"),"0");
   String pendRte = util.nvl((String)SPLITPRPMAP.get("PA"),"0");
  String pendAmt = util.nvl((String)SPLITPRPMAP.get("PV"),"0");
   String rtnQty = util.nvl((String)SPLITPRPMAP.get("RQ"),"0");
  String rtnCts = util.nvl((String)SPLITPRPMAP.get("RC"),"0");
  String rtnRte = util.nvl((String)SPLITPRPMAP.get("RA"),"0");
  String rtnAmt = util.nvl((String)SPLITPRPMAP.get("RV"),"0");
  
  int remQty =Integer.parseInt(issQty)-(Integer.parseInt(pendQty)+Integer.parseInt(rtnQty));
   double remCts = Double.parseDouble(issCts)-(Double.parseDouble(pendCts)+Double.parseDouble(rtnCts));
   remCts=util.roundToDecimals(remCts, 3);
   String rghQty = util.nvl((String)request.getParameter("rghQty")).trim();
   String rghCts = util.nvl((String)request.getParameter("rghCts")).trim();
   String form = util.nvl((String)request.getParameter("form")).trim();

    ArrayList suggBoxLst = info.getSuggBoxLst();
      if(suggBoxLst==null)
      suggBoxLst = new ArrayList();
   String msg = util.nvl((String)request.getAttribute("msg"));
     String mdl= util.nvl((String)request.getAttribute("mdl"));
   ArrayList NextSttList = (ArrayList)session.getAttribute("NextSttList");
   if(NextSttList==null)
   NextSttList=new ArrayList();
   String mstkIdn = util.nvl(request.getParameter("mstkIdn"));
        String dfPg = "MIX_SPLIT";
        if(form.equals("RGH"))
        dfPg = "RGH_SPLIT";
     HashMap allPageDtl= (info.getPageDetails() == null)?new HashMap():(HashMap)info.getPageDetails();
         HashMap pageDtl=(HashMap)allPageDtl.get(dfPg);
       ArrayList pageList=new ArrayList();
        HashMap pageDtlMap=new HashMap();
       String fld_nme="",fld_ttl="",val_cond="",dflt_val="",fld_typ="",form_nme="",lov_qry="";
      String validRtn = "return loading()";
      String calQtyLock="verifyBulkSplitQty(this)";
      pageList= ((ArrayList)pageDtl.get("VERTIFY") == null)?new ArrayList():(ArrayList)pageDtl.get("VERTIFY");
         if(pageList!=null && pageList.size() >0){
          pageDtlMap=(HashMap)pageList.get(0);
          validRtn=util.nvl((String)pageDtlMap.get("dflt_val")); 
          }
           pageList= ((ArrayList)pageDtl.get("CALQTY") == null)?new ArrayList():(ArrayList)pageDtl.get("CALQTY");
         if(pageList!=null && pageList.size() >0){
          pageDtlMap=(HashMap)pageList.get(0);
          dflt_val=util.nvl((String)pageDtlMap.get("dflt_val"));
          val_cond=util.nvl((String)pageDtlMap.get("val_cond"));
          if(dflt_val.equals("N"))
          calQtyLock="";
          else
          calQtyLock=val_cond;
          
          }
    boolean pendingCheckBoxDis = false;
    double vrRem = remCts-0.1;
    if(TYP.equals("FULL") && vrRem > 0)
    pendingCheckBoxDis = true;
  %> <input type="hidden" id="mstkIdn" name="mstkIdn" value="<%=mstkIdn%>" />
   <table cellpadding="4" cellspacing="4">
   <%if(!msg.equals("")){%>
   <tr><td><label class="redLabel" ><b><%=msg%></b></label></td></tr>
   <%}%>
 
   <tr><td><table><tr>
   <td><label class="redLabel" >Total Issue :-</label> </td><td><label class="redLabel"  > Qty </label></td><td>:</td><td><label class="redLabel" id="issQty"><%=issQty%></label> </td> <td>&nbsp;&nbsp;</td> <td> <label class="redLabel" >Carats</label></td><td>:</td><td><label class="redLabel" id="issCts"><%=issCts%></label> </td>
<td>&nbsp;&nbsp;</td>   <td> <label class="redLabel" >Avg. Rate</label></td><td>:</td><td><label class="redLabel" ><%=issRte%></label> </td><td>&nbsp;&nbsp;</td><td> <label class="redLabel" >Amount</label></td><td>:</td><td><label class="redLabel" ><%=issAmt%></label> </td>
  <td>&nbsp;&nbsp;&nbsp; </td> <td>|</td><td>&nbsp;&nbsp;</td><td><label class="redLabel" >Balance :- </label></td><td><label class="redLabel" > Qty</label></td><td>:</td><td><label class="redLabel" id="remqty"><%=remQty%></label> </td> <td>&nbsp;&nbsp;</td> <td> <label class="redLabel" >Carats </label></td>
   <td>:</td><td><label class="redLabel" id="remcts"><%=remCts%></label> </td>
   
   </tr> </table> 
   </td></tr>
   <%if(remCts>0){%>
   <tr><td>
   <html:form action="rough/roughReturnAction.do?method=bulkSavePkt" onsubmit="return verifiedRoughReturn()">
     <input type="hidden" id="mdl" name="mdl" value="<%=mdl%>" />
   <input type="hidden" id="issID" name="issID" value="<%=issID%>" />
    <input type="hidden" id="mstkIdn" name="mstkIdn" value="<%=mstkIdn%>" />
     <input type="hidden" id="rghQty" name="rghQty" value="<%=rghQty%>" />
    <input type="hidden" id="rghCts" name="rghCts" value="<%=rghCts%>" />
      <input type="hidden" id="form" name="form" value="<%=form%>" />
      <input type="hidden" id="ISSTT" name="ISSTT" value="<%=ISSTT%>"/>
   <table>
   <tr><td><input type="submit" value="Add" class="submit" name="Add" /></td></tr>
    <tr>
    <td>
    
 <table class="grid1" cellspacing="1" cellpadding="1">
   <tr>
    <th>Pkt Type</th>
 
   <th>QTY</th>
  <% for(int j=0;j<SPLITVIEWLST.size();j++){
  String lprp =(String)SPLITVIEWLST.get(j);
    String lprpD = (String)mprp.get(lprp+"D");
   
    %>
  <th><%=lprpD%>
   <% if(SPLITCOMPVIEWLST.contains(lprp)){%>
   <span class="redLabel">*</span>
   <%}%>
  </th>
 <% }%>
   </tr>
    <tr>
     <td>
    <select id="cb_pkt_ty"   name="cb_pkt_ty">
    <%if(form.equals("RGH")){%>
      <option value="RGH" >Rough</option>
   <%}else{%>
    <option value="MIX" >Mix</option>
   <%}%>
   <option value="NR" >Single</option>
   
  
  </select>
    
    </td>
   
    
    <td><input type="text" name="CB_QTY" id="CB_QTY" size="5" onchange="<%=calQtyLock%>"/></td>
    
      <%for(int j=0;j<SPLITVIEWLST.size();j++){
  String lprp =(String)SPLITVIEWLST.get(j);
  String ltyp = (String)mprp.get(lprp+"T");
  String ldftVal = util.nvl((String)SPLITDIFF.get(lprp));
  if(ldftVal.equals("DIFF"))
  ldftVal="";
  String onChange="";
  if(lprp.equals("CRTWT"))
  onChange="verifyBulkSplitCarats(this)";
  String rghCtsId="CB_RGH_CTS";
  String polCtsId="CB_CRTWT";
    pageList= ((ArrayList)pageDtl.get(lprp) == null)?new ArrayList():(ArrayList)pageDtl.get(lprp);
     if(pageList!=null && pageList.size() >0){
         pageDtlMap=(HashMap)pageList.get(0);
        dflt_val=util.nvl((String)pageDtlMap.get("dflt_val")); 
        if(dflt_val.equals("Y")){
          val_cond=util.nvl((String)pageDtlMap.get("val_cond")); 
          val_cond=val_cond.replace("~mstkIdn~", mstkIdn);
          val_cond=val_cond.replace("~rghCtsId~", rghCtsId);
          val_cond=val_cond.replace("~polCtsId~", polCtsId);
          onChange=val_cond;
        }
        }
%>
  <td>
  <% if(SPLITCOMPVIEWLST.contains(lprp)){%>
  <input type="hidden" name="CB#ICMP#<%=lprp%>" id="CB#ICMP#<%=lprp%>" value="Y" />
  <%}else{%>
  <input type="hidden" name="CB#ICMP_]#<%=lprp%>" id="CB#ICMP#<%=lprp%>" value="N" />
  <%}%>
<%
  if(ltyp.equals("C")){
  
   if(suggBoxLst.contains(lprp)){
  String inputTexId = lprp;
    String hiddenId = "CB_"+lprp;
     suggScript=suggScript+"$('#"+lprp+"').on('keyup',function() {  "+
       " var timer= setTimeout(function(){autoSearchData('"+lprp+"','"+lprp+"',false,'value')}, 300); });"+
       " $(document).on('click','.magicsearch-box ul li',function() { var hidVal=$('#"+inputTexId+"').attr('data-id');"+
      " $('#"+hiddenId+"').val(hidVal)});"+        
       " $('#"+lprp+"').keyup();";
           %>
         
           <div>
           <input type="hidden" name="<%=hiddenId%>" id="<%=hiddenId%>"/>
            <input type="text" name="" id="<%=inputTexId%>" class="magicsearch txtStyle"   autocomplete="off" value="" placeholder="" style="width:150px;height:20px" ></input>
             </div>
             
 <% }else{
  
  ArrayList prpList = (ArrayList)prp.get(lprp+"V");
  ArrayList prpSrtLst = (ArrayList)prp.get(lprp+"S");
  ArrayList prpPrtLst = (ArrayList)prp.get(lprp+"P");
  
     
  
  %>

  <select id="CB_<%=lprp%>"   name="CB_<%=lprp%>">
    <%for(int k=0;k<prpList.size();k++){
    String prpVal=(String)prpList.get(k);
    String prpSrt=(String)prpSrtLst.get(k);
    String prpPrt = (String)prpPrtLst.get(k);
    String selected="";
   if(ldftVal.equals(prpVal))
   selected="selected=\"selected\"";
    %>
    <option value="<%=prpVal%>" <%=selected%>  ><%=prpVal%></option>
    <%}%>  
  </select>
  
  

  <%}}else{
  
  %>
 <input type="text" id="CB_<%=lprp%>" onchange="<%=onChange%>" value="<%=ldftVal%>" name="CB_<%=lprp%>" size="8"  />
  <%}%>
  </td>
  <%}%>
    </tr></table> </td></tr></table></html:form> </td></tr>
    <%}else{%>
    <tr><td>
    <script type="text/javascript" >
           $(document).ready(function() {
           var mstkIdn = document.getElementById('mstkIdn').value;
          
           $("#CHK_"+mstkIdn, window.parent.document).attr("disabled", false);
           });
          </script></td></tr>
    <%}%>
     <tr><td>
   <table cellpadding="1" cellspacing="1">
   <tr><td>
   <b>Pending for retrun </b>
   </td></tr>
  
   <%
  
   pktDtlList = (ArrayList)SPLITPRPMAP.get("P");
   if(pktDtlList!=null && pktDtlList.size()>0){
   String isdisabled="";
   if(pendingCheckBoxDis)
  isdisabled= "disabled=\"disabled\"";
   %>
    <html:form action="rough/roughReturnAction.do?method=bulkSplitRtn" method="post" onsubmit="return loading()">
     <input type="hidden" id="mdl" name="mdl" value="<%=mdl%>" />
   <input type="hidden" id="issID" name="issID" value="<%=issID%>" />
    <input type="hidden" id="mstkIdn" name="mstkIdn" value="<%=mstkIdn%>" />
     <input type="hidden" id="rghQty" name="rghQty" value="<%=rghQty%>" />
    <input type="hidden" id="rghCts" name="rghCts" value="<%=rghCts%>" />
        <input type="hidden" id="form" name="form" value="<%=form%>" />
     <%
        pageList= ((ArrayList)pageDtl.get("DPAPPLY") == null)?new ArrayList():(ArrayList)pageDtl.get("DPAPPLY");
      if(pageList!=null && pageList.size() >0){
      for(int j=0;j<pageList.size();j++){
      pageDtlMap=(HashMap)pageList.get(j);
      fld_nme=(String)pageDtlMap.get("fld_nme");
      if(fld_nme.equals("Y")){
       %>
        <input type="hidden" id="dpApply" name="dpApply" value="Y" />
     <%}}}%>
    <tr><td><input type="submit" value="Return" class="submit" name="return" /></td></tr>
   <tr><td> <table><tr><td><label class="redLabel" > Total Pending :- </label></td>
   <td><label class="redLabel" > Qty </label></td><td>:</td><td><label class="redLabel" id="pndQty"><%=pendQty%></label> </td> <td>&nbsp;&nbsp;</td> <td><label class="redLabel" > Carats</label> </td><td>:</td><td><label class="redLabel" id="pndCts"><%=pendCts%></label> </td>
  <td>&nbsp;&nbsp;</td> <td><label class="redLabel" > Avg. Rate </label></td><td>:</td><td><label class="redLabel" id="pndRte"><%=pendRte%></label> </td>
  <td>&nbsp;&nbsp;</td> <td><label class="redLabel" > Amount </label></td><td>:</td><td>&nbsp;&nbsp;<label class="redLabel" id="pndAmt"><%=pendAmt%></label> </td>
  <td>
  <label id="prpChgMsg"></label>
  
  </td>
  
   </tr> </table> 
   </td></tr>
    <tr><td>
   <div class="memo">
  
 <table class="Orange" cellspacing="1" cellpadding="1">
   <tr><th class="Orangeth"><input type="checkbox" name="all"  id="all" <%=isdisabled%> onclick="CheckBOXALLForm('1',this,'cb_pkt_')"/> </th> <th class="Orangeth">Packet Code</th>   <th class="Orangeth">Out Status</th> <th class="Orangeth">Status</th><th class="Orangeth">QTY</th>
 
  <% for(int j=0;j<SPLITVIEWLST.size();j++){
  String lprp =(String)SPLITVIEWLST.get(j);
    String lprpD = (String)mprp.get(lprp+"D");
    %>
  <th class="Orangeth"><%=lprpD%></th>
 <% }%>
   </tr>
   <%
   for(int i=0;i<pktDtlList.size();i++){
   GtPktDtl pktDtl = (GtPktDtl)pktDtlList.get(i);
   String stkIdn = util.nvl((String)pktDtl.getValue("stk_idn"));
   String checkBoxNam  = "cb_pkt_"+stkIdn;
   String sttFldId = "cb_stt_"+stkIdn;
      String qty=util.nvl((String)pktDtl.getValue("qty"));

   %>
  
   <tr> <td><input type="checkbox" name="<%=checkBoxNam%>" <%=isdisabled%> id="<%=checkBoxNam%>" value="<%=stkIdn%>" /></td> <td><%=pktDtl.getValue("vnm")%></td>
   
     <%
   if( NextSttList.size()==0){
   HashMap sttDtl = new HashMap();
   sttDtl.put("key", stt);
   sttDtl.put("val", stt);
   NextSttList.add(sttDtl);
   }
   %>
    <td>
    <select id="<%=sttFldId%>"   name="<%=sttFldId%>">
    <%for(int k=0;k<NextSttList.size();k++){
    HashMap nextsttMap=(HashMap)NextSttList.get(k);
   String key =(String)nextsttMap.get("key");
   String val = (String)nextsttMap.get("val");
    %>
    <option value="<%=key%>"  ><%=val%></option>
    <%}%>  
  </select>
    
    </td>
   
   <td><%=pktDtl.getValue("stt")%></td>
   <td>
   <%=qty%>
       <input type="text" id="PN_QTY_<%=stkIdn%>"  value="<%=qty%>" onchange="SplitPrpChanges(this,'QTY','<%=issID%>','<%=stkIdn%>')" name="CB_QTY_<%=stkIdn%>" size="3"  />

   
   </td>
  
  
   <% for(int j=0;j<SPLITVIEWLST.size();j++){
   String lprp =(String)SPLITVIEWLST.get(j);
    String ltyp = (String)mprp.get(lprp+"T");
    String ldftVal = pktDtl.getValue(lprp);
    String fldNme= "PN_"+lprp+"_"+stkIdn;
    String onChange="SplitPrpChanges(this,'"+lprp+"','"+issID+"','"+stkIdn+"','"+ldftVal+"')";
   %>
   <td>&nbsp;&nbsp;
   <%if(ltyp.equals("C")){
   
    if(suggBoxLst.contains(lprp)){%>
  <%=ldftVal%>
 <% }else{
  ArrayList prpList = (ArrayList)prp.get(lprp+"V");
  ArrayList prpSrtLst = (ArrayList)prp.get(lprp+"S");
  ArrayList prpPrtLst = (ArrayList)prp.get(lprp+"P");
   %>
  <select id="<%=fldNme%>"   name="<%=fldNme%>" onchange="<%=onChange%>">
    <%for(int k=0;k<prpList.size();k++){
    String prpVal=(String)prpList.get(k);
    String prpSrt=(String)prpSrtLst.get(k);
    String prpPrt = (String)prpPrtLst.get(k);
    String selected="";
   if(ldftVal.equals(prpVal))
   selected="selected=\"selected\"";
    %>
    <option value="<%=prpVal%>" <%=selected%>  ><%=prpVal%></option>
    <%}%>  
  </select>
  <%}%>
   <%}else{%>
    <input type="text" id="<%=fldNme%>"  value="<%=ldftVal%>" onchange="<%=onChange%>" name="<%=fldNme%>" size="8"  />

   <%}%>
   </td>
  <%}%>
   
   </tr>
   <%}%>
   
   </table></div>
    </td></tr>
    </html:form> 
  <%}else{%>
   <tr><td><b> Sorry no result found</b></td></tr>
  <%}%>
   </table></td></tr>
   
   
  <tr><td>
  <table>
   <tr><td>
   <b>Return Packets </b>
   </td></tr>
   
    <%
  
   pktDtlList = (ArrayList)SPLITPRPMAP.get("R");
   if(pktDtlList!=null && pktDtlList.size()>0){
   %>
    <tr><td> <table><tr><td><label class="redLabel" > Total Return :-</label> </td>
   <td> <label class="redLabel" > Qty</label></td><td>:</td><td><label class="redLabel" > <%=rtnQty%></label> </td> <td>&nbsp;&nbsp;</td> 
   <td><label class="redLabel" >  Carats</label></td><td>:</td><td><label class="redLabel" id="rtnCtsLb" > <%=rtnCts%></label> </td>
  <td>&nbsp;&nbsp;</td> <td><label class="redLabel" >  Avg. Rate</label></td><td>:</td><td><label class="redLabel" > <%=rtnRte%></label> </td>
 <td>&nbsp;&nbsp;</td>  <td><label class="redLabel" >  Amount</label></td><td>:</td><td><label class="redLabel" > <%=rtnAmt%></label> </td>
   </tr> </table> 
   </td></tr>
    <tr><td>
  
   <div class="memo">
 <table class="Orange" cellspacing="1" cellpadding="1">
   <tr><th class="Orangeth">Packet Code</th><th class="Orangeth">Status</th><th class="Orangeth">QTY</th>
  <% for(int j=0;j<SPLITVIEWLST.size();j++){
  String lprp =(String)SPLITVIEWLST.get(j);
    String lprpD = (String)mprp.get(lprp+"D");
    %>
  <th class="Orangeth"><%=lprpD%></th>
 <% }%>
   </tr>
   <%
   for(int i=0;i<pktDtlList.size();i++){
   GtPktDtl pktDtl = (GtPktDtl)pktDtlList.get(i);
   %>
  
   <tr><td><%=pktDtl.getValue("vnm")%></td><td><%=pktDtl.getValue("stt")%></td><td><%=pktDtl.getValue("qty")%></td>
   <% for(int j=0;j<SPLITVIEWLST.size();j++){
   String lprp =(String)SPLITVIEWLST.get(j);
   %>
   <td><%=pktDtl.getValue(lprp)%></td>
  <%}%>
   
   </tr>
   <%}%>
   
   </table></div></td></tr>
   
  <%}else{%>
 <tr><td>  Sorry no result found</td></tr>
  <%}%>
   </table></td></tr>
  
   </table>
  
  <%}%>
   <% String multiSugg="<script language=\"javascript\">"+"   "+suggScript+ "</script>";%>
 <%=multiSugg%>

  </body>
</html>