<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page errorPage="../error_page.jsp" %>
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import="java.util.ArrayList,java.util.HashMap,java.sql.ResultSet,ft.com.*, java.util.Date, java.util.ArrayList, ft.com.dao.*"%>
<jsp:useBean id="util" class="ft.com.JspUtil" scope="page" />
<jsp:useBean id="info" class="ft.com.InfoMgr" scope="session" />
<%@taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>
<%@taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>


<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>Re Pricing</title>
    <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/bse.css?v=<%=info.getJsVersion()%>"/>
      <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/style.css?v=<%=info.getJsVersion()%>"/>
      <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/ajax.js?v=<%=info.getJsVersion()%> " > </script>
      <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/bse.js?v=<%=info.getJsVersion()%> " > </script>
      <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/memoScript.js?v=<%=info.getJsVersion()%> " > </script>
  </head>
<%
HashMap dbinfo = info.getDmbsInfoLst();
String cnt = (String)dbinfo.get("CNT");
HashMap mprp = info.getMprp();
HashMap prp = info.getPrp();
String pgDef = "REPRICE";
HashMap allPageDtl= (info.getPageDetails() == null)?new HashMap():(HashMap)info.getPageDetails();
HashMap pageDtl=(HashMap)allPageDtl.get(pgDef);             
ArrayList pageList=new ArrayList();
HashMap pageDtlMap=new HashMap();
String fld_nme="",fld_ttl="",val_cond="",dflt_val="",fld_typ="",form_nme="",lov_qry="";


%>
 <body onload="MM_preloadImages('images/faunalogo.png')" topmargin="0" leftmargin="0" rightmargin="0" bottommargin="0">
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
  <img src="../images/line.jpg" border="0" /></td><td valign="middle"> <span class="pgHed">Re Pricing</span> </td>
</tr></table>
  </td>
  </tr>
  <%
  String  lseq =(String)request.getAttribute("seqNo");
  if(lseq!=null){%>
    <tr><td valign="top" class="tdLayout"> <span class="redLabel"><%=lseq%></span> </td></tr>
    <%}%>
   <tr><td valign="top" class="tdLayout">
   <html:form action="pricing/rePrice.do?parameter=status" target="_blank" method="post">
   <table class="grid1">
        <tr><th>Reprice Sequence</th>
            <td><html:text property="seq"/></td>
            
        </tr>
        <tr>
        <%
            pageList=(ArrayList)pageDtl.get("BUTTON");
            if(pageList!=null && pageList.size() >0){
            for(int j=0;j<pageList.size();j++){
            pageDtlMap=(HashMap)pageList.get(j);
            fld_nme=(String)pageDtlMap.get("fld_nme");
            //fld_nme="value("+(String)pageDtlMap.get("fld_nme")+")";
            fld_ttl=(String)pageDtlMap.get("fld_ttl");
            fld_typ=(String)pageDtlMap.get("fld_typ");
            lov_qry=(String)pageDtlMap.get("lov_qry");
            dflt_val=(String)pageDtlMap.get("dflt_val");
            val_cond=(String)pageDtlMap.get("val_cond"); 
            %>
            <td colspan="2"><html:submit property="<%=fld_nme%>" value="<%=fld_ttl%>" onclick="<%=val_cond%>" styleClass="submit"/></td>
            <%
            }}
            %>
        <!-- <td colspan="2"><html:submit property="getstatus" styleClass="submit" value="Get Status"/></td>-->
        
        </tr>
   </table> 
   </html:form>
   </td></tr>
    <tr><td valign="top" height="10px" class="tdLayout"></td></tr>
    <tr><td valign="top" class="tdLayout">
   <html:form action="pricing/rePrice.do?parameter=reprc" method="post">
   <table class="grid1">
        <tr><th colspan="2" align="left">Repricing </th></tr>
            <tr>
            <td>Memo Id</td><td><html:text property="memoIdn"  name="repricefrm" /></td>
            </tr>
            <tr>
            <td>Packets.</td><td><html:textarea property="vnm"  name="repricefrm" /></td>
            </tr>
            <tr>
            <td colspan="2">Properies List </td>
            </tr>
            <tr>
            <td colspan="2">
            <table>
                           
            <%
             String lprp = "";
             String lprpTyp = "";
             String prpDsc ="";           
             String srt = "";
             String val="";
             String prt="";
             ArrayList prpPrt=null;
             ArrayList prpVal=null;
             ArrayList prpSrt = null;
             String flg = null;
             String fld1 = null;
             String fld2 = null;
             String prpFld1 = null;
             String prpFld2 = null;
             String onChg1 =  null;
             String  onChg2 = null;
           
            ArrayList prcPrpList = (ArrayList)session.getAttribute("prcPrpList");
             String dfltDsp = "&nbsp;&nbsp;&nbsp;&nbsp;---select---&nbsp;&nbsp;&nbsp;&nbsp;";
             String dfltVal = "0";
            if(prcPrpList!=null && prcPrpList.size()>0){%>
             
                 <tr>
                <td id="advSrchDiv" >
             
                 <table>
            <tr><td valign="top">
            <table border="0" cellspacing="0" cellpadding="3" width="370" class="srch">
            <thead>
           <tr><th>PROPERTY</th><th>FROM</th><th>TO</th></tr>
           </thead>
           
           <%
           
           for(int k=0 ; k < prcPrpList.size() ;k++){
          ArrayList prpLst = (ArrayList)prcPrpList.get(k);
             lprp = (String)prpLst.get(0);
             flg=(String)prpLst.get(1);
             lprpTyp = util.nvl((String)mprp.get(lprp+"T"));
           
             
             prpDsc = (String)mprp.get(lprp+"D");
             if(prpDsc==null)
                prpDsc =lprp;
             prpPrt = (ArrayList)prp.get(lprp+"P");
             prpSrt = (ArrayList)prp.get(lprp+"S");
             prpVal = (ArrayList)prp.get(lprp+"V");
             fld1 = lprp+"_1";
            fld2 = lprp+"_2";

             prpFld1 = "value(" + fld1 + ")" ;
            prpFld2 = "value(" + fld2 + ")" ; 
          
            onChg1 = "setFrTo('"+ fld1 + "','" + fld2 + "')";
             onChg2 = "setFrTo('"+ fld1 + "','" + fld2 + "')";
           %>
          
           <tr><td height="35px"><span class="txtBold"> <%=prpDsc%> </span></td>
           <%if(flg.equals("M")){
             
              onChg1 = "setFrTo('"+ fld1 + "','" + fld2 + "');setFrToCheck('"+ fld1 + "','" + fld2 + "','"+lprp+"')\"";
              onChg2 = "setFrTo('"+ fld1 + "','" + fld2 + "');setFrToCheck('"+ fld1 + "','" + fld2 + "','"+lprp+"')\"";
             String mutiTextId = "MTXT_"+lprp;
             String mutiText = "value("+ mutiTextId +")";
             
             %>
             <td colspan="2" align="center">
            
             <% if(prpSrt != null) {
               String loadStrL = "ALL";
             %>
             <div class="multiplePrpdiv" id="<%=lprp%>" align="center" style="display:none;">
             <table cellpadding="3" width="170px" class="multipleBg" cellspacing="3">
             
             <%if(!lprp.equals("LAB")){
                
             %>
             <tr><td> 
             
           <html:select  property="<%=prpFld1%>" name="repricefrm"  style="width:75px" onchange="<%=onChg1%>" styleId="<%=fld1%>"  > 
     
            <html:option value="<%=dfltVal%>"><%=dfltDsp%></html:option>
            <%
            for(int j=0; j < prpSrt.size(); j++) {
                String pPrt = (String)prpPrt.get(j);
                String pSrt = (String)prpSrt.get(j);
               
                
            %>
              <html:option value="<%=pSrt%>" ><%=pPrt%></html:option> 
            <%}%>
              </html:select>        </td><td>
             
           <html:select property="<%=prpFld2%>" name="repricefrm"  style="width:75px" onchange="<%=onChg2%>" styleId="<%=fld2%>"  > 
     
            <html:option value="<%=dfltVal%>"><%=dfltDsp%></html:option>
            <%
            for(int j=0; j < prpSrt.size(); j++) {
                String pPrt = (String)prpPrt.get(j);
                String pSrt = (String)prpSrt.get(j);
              
            %>
              <html:option value="<%=pSrt%>" ><%=pPrt%></html:option> 
            <%}%>
              </html:select>   
             
             </td> </tr><%}%>
             <%for(int n=0;n<prpSrt.size();n++){
                String isSelected = "";
                String pPrtl = (String)prpPrt.get(n);
                String pSrtl = (String)prpSrt.get(n);
                String vall = (String)prpVal.get(n);
                String chFldNme = "value("+lprp+"_"+vall+")" ;
                String onclick= "checkPrp(this, 'MTXT_"+lprp+"','"+lprp+"')";
                String checId = lprp+"_"+pSrtl;
               
                
//                String fld = util.nvl((String)favMap.get(lprp+"_"+pSrtl));
//                 if(fld.equals(pSrtl+"_to_"+pSrtl)){
//                   isSelected = "checked=\"checked\"";
//                   loadStrL = loadStrL+" , "+pPrtl;
//                   }
             %>
             <tr ><td align="center"><html:checkbox  property="<%=chFldNme%>"  name="repricefrm" styleId="<%=checId%>"  onclick="<%=onclick%>" value="<%=vall%>"/></td><td align="center"><%=pPrtl%></td></tr>
             <%}%>
             </table>
             <input type="hidden" id="HID_<%=lprp%>" value="<%=loadStrL%>" />
              
             </div>
              <table><tr><td>  
         
            <html:text property="<%=mutiText%>"  value="<%=loadStrL%>" name="repricefrm"  size="30" styleId="<%=mutiTextId%>"  styleClass="txtStyle" />

          
 </td>
              <td><img src="../images/plus.jpg" id="IMGD_<%=lprp%>" class="img" onclick="DisplayDiv('<%=lprp%>')" style="display:block" border="0"/></td><td><img src="../images/minus.jpg" id="IMGU_<%=lprp%>" class="img" onclick="Hide('<%=lprp%>')" style="display:none" border="0"/></td>
             
              </tr></table>
             <%}%>
             </td>
           
           
            <% }else{ if(prpSrt != null) {
            %>
            <td align="center">
            <html:select property="<%=prpFld1%>" name="repricefrm"  style="width:100px" onchange="<%=onChg1%>" styleId="<%=fld1%>"  > 
     
            <html:option value="<%=dfltVal%>"><%=dfltDsp%></html:option>
            <%
            for(int j=0; j < prpSrt.size(); j++) {
                String pPrt = (String)prpPrt.get(j);
                String pSrt = (String)prpSrt.get(j);
               
               
               
            %>
              <html:option value="<%=pSrt%>" ><%=pPrt%></html:option> 
            <%}%>
              </html:select>
            
            </td>
            <td   align="center">
           
               <html:select property="<%=prpFld2%>" name="repricefrm"  style="width:100px" onchange="<%=onChg2%>" styleId="<%=fld2%>"  > 
     
            <html:option value="<%=dfltVal%>"><%=dfltDsp%></html:option>
            <%
            for(int j=0; j < prpSrt.size(); j++) {
                String pPrt = (String)prpPrt.get(j);
                String pSrt = (String)prpSrt.get(j);
               
                
            %>
              <html:option value="<%=pSrt%>" ><%=pPrt%></html:option> 
            <%}%>
              </html:select>
            </td>
            
        <%}else{%>
        <%if(lprpTyp.equals("D")){%>
        <td bgcolor="#FFFFFF" align="center">
        <html:text property="<%=prpFld1%>" styleClass="txtStyle"  name="repricefrm" styleId="<%=fld1%>"  size="9" maxlength="25" /> <a href="#"  onClick="setYears(1900, '<%=info.getCurrentYear()%>' );showCalender(this, '<%=prpFld1%>');">
                <img src="<%=info.getReqUrl()%>/images/calender.png"></a></td>
        <td bgcolor="#FFFFFF" align="center"><html:text property="<%=prpFld2%>"  styleClass="txtStyle" name="repricefrm"   size="9" maxlength="25"  /> <a href="#"  onClick="setYears(1900, '<%=info.getCurrentYear()%>' );showCalender(this, '<%=prpFld2%>');">
                <img src="<%=info.getReqUrl()%>/images/calender.png"></a></td>
        
        <%} else if(lprpTyp.equals("T")){%>
        <td bgcolor="#FFFFFF" align="center" colspan="2">
        <html:text property="<%=prpFld1%>" styleClass="txtStyle"  name="repricefrm" styleId="<%=fld1%>"  size="30" maxlength="20" /> 
        </td>
       
        <%} else{%>
           <td bgcolor="#FFFFFF" align="center"><html:text property="<%=prpFld1%>"  styleClass="txtStyle" name="repricefrm" styleId="<%=fld1%>"  size="11" maxlength="25" onchange="isNumericDecimal(this.id)" /></td>
        <td bgcolor="#FFFFFF" align="center"><html:text property="<%=prpFld2%>"  styleClass="txtStyle" name="repricefrm"   size="11" maxlength="25" onchange="isNumericDecimal(this.id)" /></td>
        <%}}}%>
            
            </tr>
             <%}%>
             
             
             
             </table></td></tr></table>
             </td></tr>
                <%}%>
            </table>
            
            </td>
            
            </tr>
            <tr>
            <td colspan="2">
            <%
            pageList=(ArrayList)pageDtl.get("SUBMIT");
            if(pageList!=null && pageList.size() >0){
            for(int j=0;j<pageList.size();j++){
            pageDtlMap=(HashMap)pageList.get(j);
            fld_nme=(String)pageDtlMap.get("fld_nme");
            //fld_nme="value("+(String)pageDtlMap.get("fld_nme")+")";
            fld_ttl=(String)pageDtlMap.get("fld_ttl");
            fld_typ=(String)pageDtlMap.get("fld_typ");
            lov_qry=(String)pageDtlMap.get("lov_qry");
            dflt_val=(String)pageDtlMap.get("dflt_val");
            val_cond=(String)pageDtlMap.get("val_cond"); 
            %>
            <html:submit property="<%=fld_nme%>" value="<%=fld_ttl%>" onclick="<%=val_cond%>" styleClass="submit"/>
            <%
            }}
            %>
            </td>
            <!-- <td colspan="2"><html:submit property="submit" styleClass="submit" onclick="return confirmPRC()" value="Reprice" /> &nbsp;&nbsp;<html:submit property="repriceAll" onclick="return confirmPRC()" styleClass="submit" value="RepriceAll" />&nbsp;&nbsp;<html:submit property="view" styleClass="submit" value="View"  /> </td> -->
            </tr>
            
            
            </table> </html:form>
   </td></tr>
    
    
   
    <%
    if(request.getAttribute("view")!=null){
    HashMap pktList = (HashMap)request.getAttribute("pktList");
    ArrayList pktStkIdnList = (ArrayList)request.getAttribute("pktStkIdnList");
    if(pktList!=null && pktList.size()>0){
     ArrayList vwPrpLst = (ArrayList)session.getAttribute("PRCPRPViewLst");
    HashMap totals = (HashMap)request.getAttribute("totalMap");
    
    %>
     <tr><td valign="top" class="tdLayout">
   <table>
   <tr><td>Total :&nbsp;&nbsp;</td><td><span id="ttlqty"> <%=totals.get("qty")%>&nbsp;&nbsp;</span></td>
   <td>Cts :&nbsp;&nbsp;</td><td><span id="ttlqty"> <%=totals.get("cts")%>&nbsp;&nbsp;</span></td>
   </tr>
  
   </table>
   </td></tr>
    <tr><td valign="top" class="tdLayout">
    <table  class="dataTable" align="left" id="dataTable">

<tr>

<th>Sr No.</th>
<th><label title="Packet No">Packet No</label></th>

<th><label title="CMP">CMP</label></th>
<th><label title="MNL">MNL</label></th>
<%for(int j=0; j < vwPrpLst.size(); j++ ){
    String lprp = (String)vwPrpLst.get(j);
     String hdr = (String)mprp.get(lprp);
    String prpDsc = (String)mprp.get(lprp+"D");
    if(hdr == null) {
        hdr = lprp;
       }  

%>
<th title="<%=prpDsc%>"><%=hdr%></th>

<%}%>


      
 
</tr>

<tbody>
<%

String tableTD="";
int colSpan = vwPrpLst.size()+4;
for(int m=0;m< pktStkIdnList.size();m++){ 
if(m%2==0){
tableTD="even";
}else{
tableTD="odd";
}
String stkIdn = (String)pktStkIdnList.get(m);
HashMap pktData = (HashMap)pktList.get(stkIdn);
String target = "DV_"+stkIdn;
%>
<tr class="<%=tableTD%>">
<td><%=m+1%></td>
<td><%=pktData.get("vnm")%></td>

<td><%=pktData.get("quot")%></td>
<td><%=pktData.get("fquot")%></td>

<%
 for(int l=0;l<vwPrpLst.size();l++){
   String lprp = (String)vwPrpLst.get(l);
    String prpValue =  (String) pktData.get(lprp);
    if(prpValue.equals("NA"))
     prpValue = "";
    String prpDsc = (String)mprp.get(lprp+"D");
 
 %>
<td title="<%=prpDsc%>"><%=prpValue%></td>
<%}%>       
 
</tr>

<%}%>        


</tbody>
</table>
</td></tr>

    <%}else{%>
      <tr><td>Sorry no result found </td></tr>
    <%}}%>
    
   </table></body>
</body>
</html>