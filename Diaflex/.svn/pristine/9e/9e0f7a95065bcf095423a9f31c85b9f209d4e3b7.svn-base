<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page errorPage="../error_page.jsp" %>
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import="java.util.ArrayList,java.util.HashMap,java.sql.ResultSet,ft.com.*, java.util.Date, java.util.ArrayList, ft.com.dao.*"%>
<jsp:useBean id="util" class="ft.com.JspUtil" scope="page" />
<jsp:useBean id="info" class="ft.com.InfoMgr" scope="session" />
<%@taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>
<%@taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
     <title>Base Price Excel</title>
     <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/bse.css?v=<%=info.getJsVersion()%>"/>

      <script src="<%=info.getReqUrl()%>/scripts/bse.js?v=<%=info.getJsVersion()%>" type="text/javascript"></script>
     <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/style.css?v=<%=info.getJsVersion()%>"/>
    <script language="javaScript" type="text/javascript" src="<%=info.getReqUrl()%>/scripts/ajax.js?v=<%=info.getJsVersion()%>"></script>
 </head>
  
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
  <img src="../images/line.jpg" border="0" /></td><td valign="middle"> <span class="pgHed">Base Price Excel</span> </td>
</tr></table>
  </td>
  </tr>
  <%
  if(request.getAttribute("msg")!=null){%>
  <tr><td valign="top" class="tdLayout"><span class="redLabel"><%=request.getAttribute("msg")%>  </span>
  </td></tr>
  <%}%>
 <tr><td valign="top" class="tdLayout">
   <html:form action="/pricing/excelutilty.do?method=view"  method="POST">
        <table class="grid1">
        <tr><th colspan="2"> Search Sequence</th></tr>
         <tr>
        <td>Sequence</td><td>
          <html:select property="value(Sequence)"  styleId="seq"  name="excelutilityfrm"   >
         
            <html:optionsCollection property="seqList" name="excelutilityfrm" 
            label="idn" value="nmeIdn" />
    </html:select>
        <!--<html:text property="value(Sequence)" onchange="isNumericDecimal(this.id)" styleId="seq"/>-->
        </td>
        </tr>
        <tr><td colspan="2"><html:submit property="submit" value="View" styleClass="submit"/></td> </tr>
        </table>
        
    </html:form>
  </td></tr>
  <tr><td valign="top" class="tdLayout" height="20px">
  </td></tr>
<html:form action="/pricing/excelutilty.do?method=save"  method="POST">
   <%
   String view = (String)request.getAttribute("view");
   if(view!=null){
    ArrayList priExlDtlList = (ArrayList)session.getAttribute("priExlDtlList");
    int sr=0;
    if(priExlDtlList!=null && priExlDtlList.size()>0){   
   int count=priExlDtlList.size();
    %>   
   <tr><td valign="top" class="tdLayout">
   <table  class="grid1" align="left" id="dataTable">
   <tr><td><html:submit property="submit" value="Save Changes" styleClass="submit" onclick="return confirmChangesMsg('Are You Sure You Want To Save changes') "/></td></tr>
   <!--<tr><td><html:submit property="submit" value="revert" styleClass="submit" onclick="seqvalSet(); return confirmChangesMsg('Are You Sure You Want To Save changes') "/></td></tr>-->
   <tr>
        <th><label>Sr.No</label></th>
        
        <th>None <input type="radio" id="None" name="sttth" value="NA" onclick="checkALLExcelutility('None','None_','NA','<%=count%>')"/> </th>
        <th>Apply <input type="radio" id="APPROVE" name="sttth" value="Y" onclick="checkALLExcelutility('APPROVE','APPROVE_','Y','<%=count%>')"/></th>
        <th>Revert <input type="radio" id="REVERT" name="sttth" value="DEL" onclick="checkALLExcelutility('REVERT','REVERT_','DEL','<%=count%>')"/>  </th>
        
        <!--<th>Select All<input type="checkbox" name="checkAll_All" id="checkAll_All"  onclick="checkALLExcelutility('<%=count%>')"  /></th>-->
        <th><label>Sequence</label></th>
        <th>Shape</th>
        <th> Size From</th>
        <th>Size To</th>
        <th>Color From</th>
        <th>Color To</th>
        <th>CLR From</th>
        <th>CLR To</th>
        <th>Cts</th>
        <th>Flg</th>
    </tr>
    <tbody>
    <%
    for(int m=0;m<priExlDtlList.size();m++){
    HashMap exlDtl = (HashMap)priExlDtlList.get(m);
    sr = m+1;
    String idn=(String)exlDtl.get("idn");
    String flg=(String)exlDtl.get("flg");
    String checkEXutility ="check_"+sr;
    String noneID = "None_"+sr;
     String revertID = "REVERT_"+sr;
     String approveID = "APPROVE_"+sr;
     String sttPrp = "value(stt_" +idn+ ")";
     String noneCk = "setExcelid('"+idn+"', this,'NA')";
     String revertCk = "setExcelid('"+idn+"', this,'DEL')";
     String approveCk = "setExcelid('"+idn+"', this,'Y')";
     
      boolean isNoneDisbled = false;
      boolean isAppDisbled =  false;
      boolean isRevDisbled = false;
    if(flg.equals("NA"))
    isRevDisbled = true;
    
    if(flg.equals("Y"))
    isNoneDisbled = true;
    
    
    if(flg.equals("DEL")){
    isNoneDisbled = true;
    isAppDisbled=true;
    }
   
   
    
    
    
     
    %>
        <tr>
            <td align="center"><%=sr%></td>
            
            <td><html:radio property="<%=sttPrp%>" name="excelutilityfrm" styleId="<%=noneID%>" onclick="<%=noneCk%>" value="NA" disabled="<%=isNoneDisbled%>" /></td>
            <td><html:radio property="<%=sttPrp%>" name="excelutilityfrm"  styleId="<%=approveID%>" onclick="<%=approveCk%>" value="Y" disabled="<%=isAppDisbled%>" /></td>
            <td><html:radio property="<%=sttPrp%>" name="excelutilityfrm" styleId="<%=revertID%>" onclick="<%=revertCk%>" value="DEL" disabled="<%=isRevDisbled%>" /></td>
            
            <!--<td align="center"><input type="checkbox" id="<%=checkEXutility%>" name="<%=checkEXutility%>" onclick="setExcelid('<%=idn%>', this)" value="<%=sr%>" />-->
            <td><%=exlDtl.get("seq")%></td>
            <td><%=exlDtl.get("col01")%></td>
            <td><%=exlDtl.get("col02")%></td>
            <td><%=exlDtl.get("col03")%></td>
            <td><%=exlDtl.get("col04")%></td>
            <td><%=exlDtl.get("col05")%></td>
            <td><%=exlDtl.get("col06")%></td>
            <td><%=exlDtl.get("col07")%></td>
            <td><%=exlDtl.get("col08")%></td>
            <td><%=exlDtl.get("col09")%></td>       
        </tr>
    <%}%>
    </tbody>
    </table>
    </td>
    </tr>
<%}}%>    

</html:form>
 <tr>
     <td><jsp:include page="/footer.jsp" /> </td>
 </tr>
</table></body>
</html>