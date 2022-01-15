<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
 
<%@ page errorPage="../error_page.jsp" %>
<%@ page contentType="text/html;charset=windows-1252"%>
<jsp:useBean id="util" class="ft.com.JspUtil" scope="page" />
<jsp:useBean id="info" class="ft.com.InfoMgr" scope="session" />
<%@ page import="java.util.ArrayList,java.util.HashMap,java.sql.ResultSet,ft.com.*, java.util.Date, java.util.ArrayList, ft.com.dao.*"%>
<%@taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>Receive Invoice</title>
   <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/bse.css?v=<%=info.getJsVersion()%>"/>
      <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/style.css?v=<%=info.getJsVersion()%>"/>
      <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/bse.js?v=<%=info.getJsVersion()%> " > </script>
        <link href="<%=info.getReqUrl()%>/calendar/calendar.css" rel="stylesheet" type="text/css" />
        <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/Validation.js?v=<%=info.getJsVersion()%> " > </script>
       <script language="javaScript" type="text/javascript" src="<%=info.getReqUrl()%>/calendar/calendar.js"></script>
   <script type="text/javascript" src="<%=info.getReqUrl()%>/PopScripts/popup.js?v=<%=info.getJsVersion()%> " > </script>
   <script src="<%=info.getReqUrl()%>/PopScripts/jquery-1.2.6.min.js" type="text/javascript"></script>

  </head> 
<%
        String logId=String.valueOf(info.getLogId());
        String onfocus="cook('"+logId+"')";
        DBUtil dbutil = new DBUtil();
        DBMgr db = new DBMgr(); 
        db.setCon(info.getCon());
        dbutil.setDb(db);
        dbutil.setInfo(info);
        db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));db.setDbUsr(info.getDbUsr());
        dbutil.setLogApplNm(info.getLoApplNm());
        String actPrp =util.nvl(request.getParameter("prp"));
         String ordD = util.nvl(request.getParameter("order"));
          String  sortImg="";
        String linkAsc="";
        String linkDsc = "";
        String useMap ="";
          
%>
<body onfocus="<%=onfocus%>"  topmargin="0" leftmargin="0" rightmargin="0" bottommargin="0">
  <input type="hidden" id="reqUrl" name="reqUrl" value="<%=info.getReqUrl()%>"/>

 <div id="backgroundPopup"></div>
<div id="popupContactASSFNL">
<table class="popUpTb">
 <tr><td height="5%">&nbsp;&nbsp;&nbsp;<html:button property="value(close)" styleClass="submit"  onclick="MKEmptyPopup('SC');disablePopupASSFNL()" value="Close"  /> </td></tr>
<tr><td valign="top" height="95%">
<iframe name="SC" class="frameStyle" align="middle" frameborder="0">

</iframe></td></tr>
</table>
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
  <img src="../images/line.jpg" border="0" /></td><td valign="middle"> <span class="pgHed">Receive Invoice</span> </td>
</tr></table>
  </td>
  </tr>
  <% if(request.getAttribute("msg")!=null){%>
        <tr><td class="tdLayout" height="15"><span class="redLabel"> <%=request.getAttribute("msg")%></span></td></tr>
  <%}%>
  <tr>
  <td valign="top" class="tdLayout">
  <html:form action="receipt/receviceInvAction.do?method=InvDtl" method="post">
  <table>
  <tr><td>Invoice Type : </td> 
  <td align="center">
  <html:select property="value(typ)" styleId="typ" name="receviceInvoiceForm"     >
           <html:option value="0" >--select--</html:option>
            <html:optionsCollection property="invTypLst" name="receviceInvoiceForm" 
            label="dsc" value="nme" />
    </html:select>
  
  </td> </tr>
  <tr><td>Date:</td><td>
  <table>
  <tr><td>From </td>
  <td> <html:text property="value(dtefr)" styleClass="txtStyle"  styleId="dtefr"  size="12"  /> <a href="#" style="margin-top:5px;"  onClick="setYears(1900, '<%=info.getCurrentYear()%>' );showCalender(this, 'dtefr');">
                <img src="<%=info.getReqUrl()%>/images/calender.png"></a>
  </td><td>To</td>
  <td>    <html:text property="value(dteto)" styleClass="txtStyle"  styleId="dteto"  size="12"  /> <a href="#"  onClick="setYears(1900, '<%=info.getCurrentYear()%>' );showCalender(this, 'dteto');">
                <img src="<%=info.getReqUrl()%>/images/calender.png"></a>
</td></tr>
  </table>
  </td> </tr>
  <tr><td colspan="2"><html:submit property="value(submit)" value="Fetch" styleClass="submit" /></td></tr>
  </table>
  
  </html:form>
  </td></tr>
  <tr>
  <td valign="top"  class="tdLayout">
  &nbsp; &nbsp;
  </td></tr>
   <html:form action="receipt/receviceInvAction.do?method=save" method="post">
  <%
  ArrayList invDtlLst = (ArrayList)request.getAttribute("InvDataDtl");
  if(invDtlLst!=null && invDtlLst.size()>0){
  %>
  
  <tr>
  <td valign="top" class="tdLayout">
  <html:submit property="value(receive)" value="Receive" styleClass="submit"/>
   &nbsp; &nbsp;  <html:submit property="value(Remove)" value="Remove" styleClass="submit"/>

  </td></tr>
  <tr>
  <td valign="top" class="tdLayout" height="10px">
  
  </td></tr>
  <tr>
  <td valign="top" class="tdLayout">
  <table class="grid1" id="dataTable"> 
  <tr>
  <th><b>Select ALL</b> <input type="checkbox"  value="IS" id="IS" onclick="CheckBOXALLForm('1',this,'cb_inv_')" />&nbsp;</th>
  <th>Inv ID</th><th>Sal / Pur Idn
  <%
  
    sortImg="../images/sort_off.png";
    if(actPrp.equals("IDN") && ordD.equals("DSC") ){
        sortImg="../images/sort_up.png";
      }
     if(actPrp.equals("IDN") && ordD.equals("ASC") ){

      sortImg="../images/sort_down.png";
      }
   linkAsc="receviceInvAction.do?method=SORT&prp=IDN&order=ASC&typ=STR";
   linkDsc = "receviceInvAction.do?method=SORT&prp=IDN&order=DSC&typ=STR";
    useMap = "#idn";
  %> 
  <img src="<%=sortImg%>" id="idn1"  hspace="2" border="0" usemap="<%=useMap%>" />
 <map name="idn" id="idn">
 <area shape="rect" coords="0,0,6,5" href="<%=linkDsc%>" />
 <area shape="rect" coords="0,8,5,11" href="<%=linkAsc%>" />
 </map>
  </th><th>Party Name </th><th>Qty</th><th>Cts</th><th>Value ($)</th><th>Value</th>
  <th>Date
  <%
  sortImg="../images/sort_off.png";
  if(actPrp.equals("DTENUM") && ordD.equals("DSC") ){
   sortImg="../images/sort_up.png";
   }
   if(actPrp.equals("DTENUM") && ordD.equals("ASC") ){
       sortImg="../images/sort_down.png";
    }
    linkAsc="receviceInvAction.do?method=SORT&prp=DTENUM&order=ASC&typ=INT";
   linkDsc = "receviceInvAction.do?method=SORT&prp=DTENUM&order=DSC&typ=INT";
    useMap = "#dte";
  %>
    <img src="<%=sortImg%>" id="dte1"  hspace="2" border="0" usemap="<%=useMap%>" />
 <map name="dte" id="dte">
 <area shape="rect" coords="0,0,6,5" href="<%=linkDsc%>" />
 <area shape="rect" coords="0,8,5,11" href="<%=linkAsc%>" />
 </map>
  </th><th>Exchange Rate</th>
  <th>Brk Name</th> <th>Brk Slab</th><th>Bill</th>
  </tr>
  <%for(int i=0;i<invDtlLst.size();i++){
  HashMap invDtl = (HashMap)invDtlLst.get(i);
   String invId = util.nvl((String)invDtl.get("INVIDN"));
    String refIdn = util.nvl((String)invDtl.get("IDN"));
   String typ = util.nvl((String)invDtl.get("TYP")); 
   String xrt = util.nvl((String)invDtl.get("XRT"));
   String target = "TR_"+invId;
   String slabId = "SLAB_"+invId;
   String slabVal = "value("+slabId+")";
    String billId = "BILL_"+invId;
   String billVal = "value("+billId+")";
  %>
  <tr id="<%=target%>">
  <td><input type="checkbox" name="cb_inv_<%=invId%>" value="<%=invId%>_<%=refIdn%>" id="cb_inv_<%=invId%>"  /></td>
  <td align="right"><a href="receviceInvAction.do?method=PktDtl&refIdn=<%=invId%>&TYP=<%=typ%>" id="LNK_<%=invId%>" onclick="loadASSFNL('<%=target%>','LNK_<%=invId%>')"  target="SC" > <%=invDtl.get("INVIDN")%></a></td> 
  <td align="right"><%=invDtl.get("IDN")%></td>
  <td><%=invDtl.get("NME")%></td> <td align="right"><%=invDtl.get("QTY")%></td> <td align="right"><%=invDtl.get("CTS")%></td>
   <td align="right"><%=invDtl.get("USDVLU")%></td><td align="right"><%=invDtl.get("VLU")%></td><td><%=invDtl.get("DTE")%></td>
   <td>
   <input type="text" name="cb_xrt_<%=invId%>" id="cb_xrt_<%=invId%>" value="<%=xrt%>" size="10" />
   </td>
    <td><%=invDtl.get("BRKNME")%></td>
    <td>  
    <html:select property="<%=slabVal%>" styleId="<%=slabId%>" name="receviceInvoiceForm"     >
    <html:option value="0" >--select--</html:option>
   <html:optionsCollection property="slabTypLst" name="receviceInvoiceForm" 
            label="dsc" value="nme" />
    </html:select>
    </td>
     <td>  
    <html:select property="<%=billVal%>" styleId="<%=billId%>" name="receviceInvoiceForm"     >
  
   <html:optionsCollection property="billTypLst" name="receviceInvoiceForm" 
            label="dsc" value="nme" />
    </html:select>
    </td>
  </tr>
  <%}%>
  </table>
    </td></tr>
  <%}else{%>
   <tr>
  <td valign="top" class="tdLayout">
  Sorry no result found.
  </td></tr>
  <%}%>
</html:form>
<tr>
    <td><jsp:include page="/footer.jsp" /> </td>
    </tr>
  </table>
    
     <%@include file="/calendar.jsp"%>
    </body>
</html>