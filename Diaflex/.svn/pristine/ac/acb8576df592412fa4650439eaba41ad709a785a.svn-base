<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page autoFlush="true" buffer="128kb" %>
<jsp:useBean id="info" class="ft.com.InfoMgr" scope="session" />
<jsp:useBean id="util" class="ft.com.JspUtil" scope="page" />
<%@ page import="java.util.ArrayList,java.util.HashMap,java.sql.ResultSet,ft.com.*, java.util.Date, java.util.ArrayList, ft.com.dao.*"%>
<%@taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>genericSrch</title>
   <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/bse.js?v=<%=info.getJsVersion()%> " > </script>
    <link href="<%=info.getReqUrl()%>/auto-search/auto-search.css?v=<%=info.getJsVersion()%>"
                rel="stylesheet" type="text/css"/>
<link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/bse.css?v=<%=info.getJsVersion()%>"/>
<link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/general.css?v=<%=info.getJsVersion()%>"/>
<script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/ajax.js?v=<%=info.getJsVersion()%> " > </script>
  <script type="text/javascript" src="<%=info.getReqUrl()%>/PopScripts/popup.js?v=<%=info.getJsVersion()%> " > </script>
   <script src="<%=info.getReqUrl()%>/PopScripts/jquery-1.2.6.min.js" type="text/javascript"></script>
     <script src="<%=info.getReqUrl()%>/PopScripts/jquery-1.11.3.min.js" type="text/javascript"></script>
   <script language="javaScript" type="text/javascript" src="<%=info.getReqUrl()%>/calendar/calendar.js"></script>
   <link href="<%=info.getReqUrl()%>/calendar/calendar.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/memoScript.js?v=<%=info.getJsVersion()%> " > </script>
 
  <script src="<%=info.getReqUrl()%>/auto-search/auto-search.js?v=<%=info.getJsVersion()%>" type="text/javascript"></script>
           <script src="<%=info.getReqUrl()%>/auto-search/autoajaxjs.js?v=<%=info.getJsVersion()%>" type="text/javascript"></script>
 
      <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/style.css?v=<%=info.getJsVersion()%>"/>
  
  </head>
  <body>
  <%
    ArrayList prpDspBlocked = info.getPageblockList();
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
    String suggScript ="";    
  %>
  <table border="0" cellspacing="0" cellpadding="3" width="330">
    <%
    if(srchPrp!=null){
         for(int i=0;i<srchPrp.size();i++){
      String val1 = "";
      String val2= "";
       prplist =(ArrayList)srchPrp.get(i);
       if(prplist!=null){
       String lprp = util.nvl((String)prplist.get(0));
       String flgtyp =util.nvl((String)prplist.get(1));
       if(prpDspBlocked.contains(lprp)){
       }else{
       String lprpTyp = util.nvl((String)mprp.get(lprp+"T"));
       String lprpDsc = util.nvl((String)mprp.get(lprp+"D"));
      
       fld1 = lprp+"_1";
       fld2 = lprp+"_2";
       prpFld1 = "value(" + fld1 + ")" ;
       prpFld2 = "value(" + fld2 + ")" ; 
       onChg1 = "setFrTo('"+ fld1 + "','" + fld2 + "')";
       onChg2 = "setFrTo('"+ fld1 + "','" + fld2 + "')";
       %>
       <tr>
       <td align="center" nowrap="nowrap"><%=lprpDsc%></td>
       <%
        if(lprpTyp.equals("C") && !flgtyp.equals("CTA")) {
         prpPrt = (ArrayList)prp.get(lprp+"P");
         prpSrt = (ArrayList)prp.get(lprp+"S");
         prpVal = (ArrayList)prp.get(lprp+"V");
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
         <%}else if(flgtyp.equals("SM")){
          suggScript=suggScript+"$('#"+lprp+"').on('keyup',function() {  "+
       " var timer = setTimeout(autoSearchData('"+lprp+"','"+lprp+"','true'), 300);  });"+
       " $('#"+lprp+"').keyup();";
       String proprtyVal = "value("+lprp+")";
           %>
           <td colspan="2">
           <div  >
           <html:hidden property="<%=proprtyVal%>" styleId="hidden_select"/>
            <input type="text" name="" id="<%=lprp%>" class="magicsearch txtStyle " value="" autocomplete="off" placeholder="" style="width:200px;height:20px" ></input>
             </div>
             </td>
        <% }else{
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
        
        <%} else if(lprpTyp.equals("T") || flgtyp.equals("CTA")){
        if(fld1.equals("VENDOR_1")){
        flgtyp="TS";
        }
        if(flgtyp.equals("TS")){%>
        <td bgcolor="#FFFFFF" align="left" colspan="2">
              <%String fldCB = "value(" + fld1 + "_CB)";
              String fldCBid = fld1 + "_CB";
              String dspFld = fld1 + "_dsp" ;
              String dspFldV = "value("+ dspFld + ")";
              String dspFldPop = fld1 + "_dsp" + "_pop" ;
              String dspFldPopV = "value("+ dspFldPop + ")";
              String keyStr = "doCompletion('"+ dspFld +"', '" + dspFldPop + "', '../ajaxAction.do?1=1&UsrTyp=VENDOR', event)";
              String keyStrMobile = "doCompletionMobile('"+ dspFld +"', '" + dspFldPop + "', '../ajaxAction.do?1=1&UsrTyp=VENDOR')";              
              String setDown = "setDown('"+dspFldPop+"', '"+fld1+"', '"+ dspFld +"',event)";%>
                <input type="text" name="<%=dspFldV%>" id="<%=dspFld%>" class="sugBox"
                  onKeyUp="<%=keyStr%>"   onKeyDown="<%=setDown%>" value="" />
                <img src="../images/addrow.jpg" width="17" height="17" title="Click To get Names" onclick="<%=keyStrMobile%>">
                <html:hidden property="<%=prpFld1%>" styleId="<%=fld1%>"/>
             
                <div style="position: absolute;">
                  <select id="<%=dspFldPop%>" name="<%=dspFldPopV%>" class="sugBoxDiv"
                    style="display:none;300px;"  
                    onKeyDown="<%=setDown%>"  
                    onDblClick="setVal('<%=dspFldPop%>', '<%=fld1%>', '<%=dspFld%>', event);hideObj('<%=dspFldPop%>')" 
                    onClick="setVal('<%=dspFldPop%>', '<%=fld1%>','<%=dspFld%>', event);" 
                    size="10">
                  </select>
                </div>
        </td>
        <%}else if(lprpTyp.equals("T") || flgtyp.equals("CTA")){%>
        <td bgcolor="#FFFFFF" align="center" colspan="2">
        <html:text property="<%=prpFld1%>" styleClass="txtStyle"   styleId="<%=fld1%>" /> 
        </td>
       
        <%}} else{%>
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
for(int s=0;s<srchPrp.size();s++){
   ArrayList srchLst = (ArrayList)srchPrp.get(s);
   if(srchLst!=null){
       String lprp =util.nvl((String)srchLst.get(0));
      String flg=util.nvl((String)srchLst.get(1));
      if(prpDspBlocked.contains(lprp)){
       }else{
       if(flg.equals("M")){
%>
<option value="<%=lprp%>"></option>
<%}}}}
   
%>
</select>
</td>
 </tr>
 <%}%>
  </table>
 <% String multiSugg="<script language=\"javascript\">"+"   "+suggScript+ "</script>";%>
 <%=multiSugg%>
 <script language="javascript">
        $(document).on('click','.magicsearch-box ul li',function() {
        var hidVal=$('.magicsearch').attr('data-id');
        $('#hidden_select').val(hidVal);
        });
        
        $(document).on('click','.multi-item-close',function() {
         setTimeout(function(){
         var hidVal=$('.magicsearch').attr('data-id');
        $('#hidden_select').val(hidVal);
         },500);
        });
 </script>
  <%@include file="calendar.jsp"%>
 
  </body>
</html>