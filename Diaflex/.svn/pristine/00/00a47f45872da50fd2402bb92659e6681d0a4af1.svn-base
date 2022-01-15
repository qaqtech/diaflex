<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page errorPage="../error_page.jsp" %>
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.ResultSet, java.util.ArrayList,java.util.Collections,java.util.ArrayList,java.util.HashMap,java.text.SimpleDateFormat, java.util.Calendar"%>
<jsp:useBean id="info" class="ft.com.InfoMgr" scope="session" />
<jsp:useBean id="util" class="ft.com.JspUtil" scope="page" />
<%@taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>
<%@taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<html> 
   <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/bse.js?v=<%=info.getJsVersion()%> " > </script>
<link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/bse.css?v=<%=info.getJsVersion()%>"/>
<link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/general.css?v=<%=info.getJsVersion()%>"/>
<script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/ajax.js?v=<%=info.getJsVersion()%> " > </script>
  <script type="text/javascript" src="<%=info.getReqUrl()%>/PopScripts/popup.js?v=<%=info.getJsVersion()%> " > </script>
    <script src="<%=info.getReqUrl()%>/PopScripts/jquery-1.2.6.min.js" type="text/javascript"></script>
   <script language="javaScript" type="text/javascript" src="<%=info.getReqUrl()%>/calendar/calendar.js"></script>
   <link href="<%=info.getReqUrl()%>/calendar/calendar.css" rel="stylesheet" type="text/css" />

 
      <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/style.css?v=<%=info.getJsVersion()%>"/>

  <title>Factory Running Stock Report</title>
 
  </head>
  
  <%
       HashMap mfgprpset= (session.getAttribute("MFGPRPFACTORYSET") == null)?new HashMap():(HashMap)session.getAttribute("MFGPRPFACTORYSET");
    ArrayList prpDspBlocked = info.getPageblockList();
    HashMap allPageDtl= (info.getPageDetails() == null)?new HashMap():(HashMap)info.getPageDetails();
    HashMap pageDtl=(HashMap)allPageDtl.get("FACRUN_RPT");
    ArrayList docLst= ((ArrayList)mfgprpset.get("docLst") == null)?new ArrayList():(ArrayList)mfgprpset.get("docLst");
    ArrayList lotLst= ((ArrayList)mfgprpset.get("lotLst") == null)?new ArrayList():(ArrayList)mfgprpset.get("lotLst");
        String logId=String.valueOf(info.getLogId());
        String onfocus="cook('"+logId+"')";
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
  <img src="../images/line.jpg" border="0" /></td><td valign="middle"> <span class="pgHed">Factory Running Stock Report</span> </td>
</tr></table>
  </td>
  </tr>
<tr><td valign="top" class="hedPg">
<html:form action="/report/orclRptAction.do?method=fetchfactoryrun"  method="POST">
  <table  class="grid1">
    <tr><th colspan="2">Generic Search</th></tr>
  <tr>
  <td>Lot Number</td>
  <td>
  <html:select  property="lotLst" styleId="lot"  multiple="true" style=" height:80px;" size="4" >
    <html:option value="">All</html:option>
  <%for(int j=0; j < lotLst.size(); j++) {
    String pPrt = util.nvl((String)lotLst.get(j));
    %>
  <html:option value="<%=pPrt%>"><%=pPrt%></html:option>
  <%}%>
  </html:select>
  </td>
  </tr>
      <tr><td>Weight </td>  
       <td>From&nbsp; <html:text property="value(CRTWT_1)" styleId="CRTWT_1" name="orclReportForm" size="8" /> 
       To&nbsp; <html:text property="value(CRTWT_2)" styleId="CRTWT_2" name="orclReportForm"  size="8"/> </td>
      </tr>
       <tr><td>Packet </td>  
       <td>From&nbsp; <html:text property="value(pkt_1)" styleId="pkt_1" name="orclReportForm" size="10" /> 
       To&nbsp; <html:text property="value(pkt_2)" styleId="pkt_2" name="orclReportForm"  size="10"/> </td>
      </tr>
   <tr>
   <td colspan="2">
     <%
    ArrayList pageList=new ArrayList();
    HashMap pageDtlMap=new HashMap();
    String fld_nme="",fld_ttl="",val_cond="",dflt_val="",fld_typ="",form_nme="",flg1="",lov_qry="";
    ArrayList srchPrp = info.getGncPrpLst();
    HashMap mprp = info.getMprp();
    HashMap prp = info.getPrp();
    mprp = info.getMprp();
    ArrayList prpPrt =null;
    ArrayList prpSrt = null;
    ArrayList prpVal =null;
    String fld1 ="";
    String fld2 ="";
    String prpFld1 ="";
    String prpFld2 ="";
    String onChg1 ="";
    String onChg2 ="";
    String dfltDsp = "&nbsp;&nbsp;&nbsp;&nbsp;---select---&nbsp;&nbsp;&nbsp;&nbsp;";
    String dfltVal = "";
    ArrayList prplist=null;
  %>
  <table border="0" cellspacing="0" cellpadding="3" width="330">
    <%
            pageList=(ArrayList)pageDtl.get("PRPFLT");
            if(pageList!=null && pageList.size() >0){
            for(int k=0;k<pageList.size();k++){
            pageDtlMap=(HashMap)pageList.get(k);
            String lprp=(String)pageDtlMap.get("dflt_val");
            String flgtyp=(String)pageDtlMap.get("val_cond");
      String val1 = "";
      String val2= "";
       if(prpDspBlocked.contains(lprp)){
       }else{
       String lprpTyp = (String)pageDtlMap.get("fld_typ");
       String lprpDsc = (String)pageDtlMap.get("fld_ttl");
      
       fld1 = lprp+"_1";
       fld2 = lprp+"_2";
       prpFld1 = "value(" + fld1 + ")" ;
       prpFld2 = "value(" + fld2 + ")" ; 
       onChg1 = "setFrTo('"+ fld1 + "','" + fld2 + "')";
       onChg2 = "setFrTo('"+ fld1 + "','" + fld2 + "')";
       %>
       <tr>
       <td align="center"><%=lprpDsc%></td>
       <%
        if(lprpTyp.equals("C")) {
         prpPrt = ((ArrayList)mfgprpset.get(lprp+"_VAL") == null)?new ArrayList():(ArrayList)mfgprpset.get(lprp+"_VAL");
         prpSrt = ((ArrayList)mfgprpset.get(lprp+"_SRT") == null)?new ArrayList():(ArrayList)mfgprpset.get(lprp+"_SRT");
         prpVal = ((ArrayList)mfgprpset.get(lprp+"_VAL") == null)?new ArrayList():(ArrayList)mfgprpset.get(lprp+"_VAL");
         if(flgtyp.equals("M")){
         onChg1 = "setFrTo('"+ fld1 + "','" + fld2 + "');setFrToCheck('"+ fld1 + "','" + fld2 + "','"+lprp+"')\"";
              onChg2 = "setFrTo('"+ fld1 + "','" + fld2 + "');setFrToCheck('"+ fld1 + "','" + fld2 + "','"+lprp+"')\"";
             String mutiTextId = "MTXT_"+lprp;
             String mutiText = "value("+ mutiTextId +")";%>
             <td colspan="2" align="center">
            <% if(prpSrt != null) {
               String loadStrL = "ALL";
             %>
             <div  class="multiplePrpdiv" id="<%=lprp%>" align="center" style="display:none; padding-top:2px; margin-top:20px; z-index:1">
             <table cellpadding="0"  class="multipleBg" cellspacing="0">
             <tr><td>
             <table>
             <tr>
             <td> <input type="checkbox" name="selectALL" id="SALL_<%=lprp%>" onclick="CheckAlllprp('<%=lprp%>',this)" > Select All &nbsp; </td>
             <td> From: &nbsp; 
             
           <html:select  property="<%=prpFld1%>" style="width:75px" onchange="<%=onChg1%>" styleId="<%=fld1%>"  > 
     
            <html:option value="<%=dfltVal%>"><%=dfltDsp%></html:option>
            <%
            for(int j=0; j < prpSrt.size(); j++) {
                String pPrt = util.nvl((String)prpPrt.get(j));
                String pSrt = util.nvl((String)prpSrt.get(j));
               
                  String isSelected = "" ;
               if(pSrt.equals(val1))
                   isSelected = "selected=\"selected\"";
            %>
              <html:option value="<%=pSrt%>" ><%=pPrt%></html:option> 
            <%}%>
              </html:select>
              </td><td>
            To:&nbsp;
             
           <html:select property="<%=prpFld2%>" style="width:75px" onchange="<%=onChg2%>" styleId="<%=fld2%>"  > 
     
            <html:option value="<%=dfltVal%>"><%=dfltDsp%></html:option>
            <%
            for(int j=0; j < prpSrt.size(); j++) {
                String pPrt = util.nvl((String)prpPrt.get(j));
                String pSrt = util.nvl((String)prpSrt.get(j));
               
                  String isSelected = "" ;
               if(pSrt.equals(val1))
                   isSelected = "selected=\"selected\"";
            %>
              <html:option value="<%=pSrt%>" ><%=pPrt%></html:option> 
            <%}%>
              </html:select>   
             </td> 
            <td><img src="../images/cross.png" border="0" onclick="Hide('<%=lprp%>')" /> </td>
            </tr>
            </table>
            </td></tr>
            <tr><td><hr style="color:White;"></td></tr>
            <tr>
            <td>
             <%
             int listCnt=0;
             for(int m=0;m<prpSrt.size();m++){
                String isSelected = "";
                String pPrtl = util.nvl((String)prpPrt.get(m));
                String pSrtl = util.nvl((String)prpSrt.get(m));
                String vall = util.nvl((String)prpVal.get(m));
                String chFldNme = "value("+lprp+"_"+vall+")" ;
                String onclick= "checkPrp(this, 'MTXT_"+lprp+"','"+lprp+"')";
                String checId = lprp+"_"+pSrtl;
               if(m==0){
                %>
                <table>
                <tr>
                <%}
                if(listCnt==7){%>
                </tr><tr>
                <% listCnt=0;} 
                listCnt++;
//                String fld = util.nvl((String)favMap.get(lprp+"_"+pSrtl));
//                 if(fld.equals(pSrtl+"_to_"+pSrtl)){
//                   isSelected = "checked=\"checked\"";
//                   loadStrL = loadStrL+" , "+pPrtl;
//                   }
             %>
             <td align="center"><html:checkbox  property="<%=chFldNme%>" styleId="<%=checId%>"  onclick="<%=onclick%>" value="<%=vall%>"/></td>
             <!--<td align="left"><span style="margin-left:10px;"><%=pPrtl%></span></td>-->
             <td align="left"><span><%=pPrtl%></span></td>
             <%}%>
             </tr></table>
            </td>
            </tr>
            </table>
             <input type="hidden" id="HID_<%=lprp%>" value="<%=loadStrL%>" />
              
             </div>
                           <table cellpadding="0" cellspacing="0">
              <tr><td>  
            <html:text property="<%=mutiText%>"  value="<%=loadStrL%>" size="30" styleId="<%=mutiTextId%>"  styleClass="txtStyle" />
             </td><td>&nbsp;&nbsp;</td>
              <td><img src="../images/plus.jpg" id="IMGD_<%=lprp%>" class="img" onclick="DisplayDiv('<%=lprp%>')" style="display:block" border="0"/></td><td><img src="../images/minus.jpg" id="IMGU_<%=lprp%>" class="img" onclick="Hide('<%=lprp%>')" style="display:none" border="0"/></td>
             
              </tr></table>
             <%}%>
             </td>
         <%}else{
            %>
            <td align="center">
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
            <%
              if(!flgtyp.equals("S")){
              %>
            <td   align="center">
           
               <html:select property="<%=prpFld2%>"   style="width:100px" onchange="<%=onChg2%>" styleId="<%=fld2%>"  > 
     
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
            
        <% }else{%>
        <td></td>
        <%}
        }}else if(lprpTyp.equals("D")){%>
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
       <% }}%>
       
       <%}%> 

 <tr>
<td style="display:none">
<select name="multiPrp" id="multiPrp">
<%
            pageList=(ArrayList)pageDtl.get("PRPFLT");
            if(pageList!=null && pageList.size() >0){
            for(int k=0;k<pageList.size();k++){
            pageDtlMap=(HashMap)pageList.get(k);
            String lprp=(String)pageDtlMap.get("dflt_val");
            String flg=(String)pageDtlMap.get("val_cond");
      if(prpDspBlocked.contains(lprp)){
       }else{
       if(flg.equals("M")){
%>
<option value="<%=lprp%>"></option>
<%}}}}%>
</select>
</td>
 </tr>
  </table>
   
   </td>
   </tr>
  <tr>
  <td>Flg</td>
  <td>
  <html:select  property="value(flg)" styleId="flg"  >
  <html:option value="">ALL</html:option>
  <html:option value="MFG_FCT">MFG_FCT</html:option>
  <html:option value="MFG_PLS">MFG_PLS</html:option>
  </html:select>
  </td>
  </tr>
   <tr>
   <td colspan="2" align="center">
   <html:submit property="value(srch)" value="Fetch" styleClass="submit" />
   </td>
   </tr>
   </table>
</html:form>
</td>
</tr>
<%String view=util.nvl((String)request.getAttribute("view"));
if(!view.equals("")){
ArrayList pktsummryLst= ((ArrayList)request.getAttribute("pktsummryLst") == null)?new ArrayList():(ArrayList)request.getAttribute("pktsummryLst");
ArrayList pktLst= ((ArrayList)request.getAttribute("pktLst") == null)?new ArrayList():(ArrayList)request.getAttribute("pktLst");
int pktsummryLstsz=pktsummryLst.size();
int pktLstsz=pktLst.size();
if(pktLstsz>0){
%>
<tr>
<td valign="top" class="hedPg">
<table class="grid1">
<tr>
<td>
<table class="grid1">
<tr><th colspan="5"> Summary Details</th></tr>
<tr>
<th>Description</th>
<th>Pcs</th>
<th>Carets</th>
<th>Polish Caret</th>
<th>Value</th>
</tr>

<%
    for(int j=0; j < pktsummryLstsz; j++) {
    HashMap pktDtlMap=new HashMap();
    pktDtlMap=(HashMap)pktsummryLst.get(j);
    %>
    <tr>
    <td align="center"><%=util.nvl((String)pktDtlMap.get("dsc"))%></td>
    <td align="center"><%=util.nvl((String)pktDtlMap.get("tot_pcs"))%></td>
    <td  align="right"><%=util.nvl((String)pktDtlMap.get("tot_crts"))%></td>
    <td  align="right"><%=util.nvl((String)pktDtlMap.get("tot_pls_crt"))%></td>
    <td  align="right"><%=util.nvl((String)pktDtlMap.get("tot_fnl_vlu"))%></td>
    </tr>
    <%}%>
</table>
</td>
</tr>
</table>
</td>
</tr>
<tr>
<td valign="top" class="hedPg">
<table class="grid1">
<tr><th colspan="21">Packet Details</th></tr>
<tr>
<th>Description</th>
<th>Lot Number</th>
<th>Packet Number</th>
<th>Pcs</th>
<th>Making Carets</th>
<th>Current Location</th>
<th>Value</th>
<th>Rap</th>
<th>Dis</th>
<th>Polish Carets</th>
<th>Shape</th>
<th>Color</th>
<th>Clarity</th>
<th>Cut</th>
<th>DP</th>
<th>Lus</th>
<th>Fl</th>
<th>Bis</th>
<th>Dia</th>
<th>POl</th>
<th>SYM</th>
</tr>
<%
    for(int j=0; j < pktLstsz; j++) {
    HashMap pktDtlMap=new HashMap();
    pktDtlMap=(HashMap)pktLst.get(j);
    %>
    <tr>
    <td  align="center"><%=util.nvl((String)pktDtlMap.get("dscr"))%></td>
    <td align="center"><%=util.nvl((String)pktDtlMap.get("lt_nmbr"))%></td>
    <td align="right"><%=util.nvl((String)pktDtlMap.get("pckt_nmbr"))%></td>
    <td align="right"><%=util.nvl((String)pktDtlMap.get("pcs"))%></td>
    <td align="right"><%=util.nvl((String)pktDtlMap.get("mk_crt"))%></td>
    <td align="right"><%=util.nvl((String)pktDtlMap.get("cur_loc"))%></td>
    <td align="right"><%=util.nvl((String)pktDtlMap.get("vlu"))%></td>
    <td align="right"><%=util.nvl((String)pktDtlMap.get("fnl_rap_rt"))%></td>
    <td><%=util.nvl((String)pktDtlMap.get("fnl_dis_pct"))%></td>
    <td><%=util.nvl((String)pktDtlMap.get("pls_crt"))%></td>
    <td><%=util.nvl((String)pktDtlMap.get("sha"))%></td>
    <td><%=util.nvl((String)pktDtlMap.get("col"))%></td>
    <td><%=util.nvl((String)pktDtlMap.get("clr"))%></td>
    <td><%=util.nvl((String)pktDtlMap.get("cut"))%></td>
    <td><%=util.nvl((String)pktDtlMap.get("dp"))%></td>
    <td><%=util.nvl((String)pktDtlMap.get("lus"))%></td>
    <td><%=util.nvl((String)pktDtlMap.get("flo"))%></td>
    <td><%=util.nvl((String)pktDtlMap.get("bis"))%></td>
    <td><%=util.nvl((String)pktDtlMap.get("dia"))%></td>
    <td><%=util.nvl((String)pktDtlMap.get("pol"))%></td>
    <td><%=util.nvl((String)pktDtlMap.get("sym"))%></td>
    </tr>
    <%}%>
    </table>
    </td>
    </tr>
<%}else{%>
<tr><td valign="top" class="hedPg">
Sorry No Result Found</td></tr>
<%}}%>
  <tr><td>
<jsp:include page="/footer.jsp" />
</td></tr>
  </table>  
         <%@include file="../calendar.jsp"%>
  </body>
</html>