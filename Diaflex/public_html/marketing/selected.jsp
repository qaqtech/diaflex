<%@ page errorPage="../error_page.jsp" %>
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import="java.util.ArrayList,java.util.ArrayList,java.util.List, java.util.HashMap, java.sql.*, java.util.Enumeration, java.util.logging.Level"%>
<jsp:useBean id="util" class="ft.com.JspUtil" scope="page" />
<jsp:useBean id="info" class="ft.com.InfoMgr" scope="session" />
<%@taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>
<%@taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>

<title>Search Result</title>
<script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/bse.js?v=<%=info.getJsVersion()%> " > </script>
<link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/bse.css?v=<%=info.getJsVersion()%>"/>
<link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/filter.css"/>
<script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/ajax.js?v=<%=info.getJsVersion()%> " > </script>
<link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/style.css?v=<%=info.getJsVersion()%>"/>
<link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/general.css?v=<%=info.getJsVersion()%>"/>
<script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/memoScript.js?v=<%=info.getJsVersion()%> " > </script>
<script type="text/javascript" src="<%=info.getReqUrl()%>/PopScripts/popup.js?v=<%=info.getJsVersion()%> " > </script>
<script src="<%=info.getReqUrl()%>/PopScripts/jquery-1.2.6.min.js" type="text/javascript"></script>
<script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/tableFilter/actb.js " > </script>
<script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/tableFilter/tablefilter.js " > </script>
</head>
        <%String logId=String.valueOf(info.getLogId());
        String onfocus="cook('"+logId+"')";
        %>
 <body onfocus="<%=onfocus%>" onload="MM_preloadImages('images/faunalogo.png')" topmargin="0" leftmargin="0" rightmargin="0" bottommargin="0">
<div id="popupContactMail">
<table align="center" cellpadding="10" cellspacing="10" class="pktTble1">
<tr><td>Email ID: </td><td><html:text property="value(emailId)" styleId="mail" size="30" value="<%=info.getByrEml()%>" name="searchForm" /> </td> </tr>
<tr><td align="right"><button type="button" onclick="validate_Mail()" Class="submit" >Send</button> </td>
<td align="left"><button type="button" onclick="disablePopupMail()" Class="submit" >Back</button> </td>
</tr>
</table>
</div>

<div id="popupContactRult" >

<table align="center" cellpadding="5" cellspacing="2" class="pktTble1">
<tr><td> <jsp:include page="/marketing/refineSearch.jsp"/></td></tr>

</table>
</div>

<div id="backgroundPopup"></div>
<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0" class="mainbg">
<tr>
<td height="103" valign="top">
<jsp:include page="/header.jsp" />

</td>
</tr>

<tr>
<td height="5" valign="top" class="greyline"><img src="../images/grey_linebig.png" width="685" height="5" align="right" /></td>
</tr>
<tr>
<td>
<table>
<tr valign="bottom">
<td valign="">
  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<img src="../images/line.jpg" border="0" /></td><td valign="middle"> <span class="pgHed">Search Result</span> </td>
<td>&nbsp;&nbsp;</td>
<td>
<%if(info.getDmbsInfoLst().get("DATA_DIR").equals("JBBROS")){%>
<div class="green">&nbsp;</div></td><td>&nbsp;Premium Plus&nbsp;</td>
<td ><div class="Maroon">&nbsp;</div></td><td>&nbsp; Look and Bid&nbsp;</td>
<%}%>
<td><div class="Blue">&nbsp;</div> </td><td>&nbsp;New Good&nbsp;</td>
<td><div class="red">&nbsp;</div></td><td>&nbsp; HOLD&nbsp; </td>
<td><div class="gray">&nbsp;</div></td><td>&nbsp; New Good And Hold&nbsp; </td>
<%
String desc=(String)request.getAttribute("searchCrt");
if(desc!=null){
%>

<td><span class="txtLink" >Search Description : <%=desc %></span>
<td>
<%}%>
</tr>
</table>
</td>



</tr>
<tr><td valign="top" class="hedPg">

<%
HashMap seachList = (HashMap)session.getAttribute("searchList");

ArrayList pktStkIdnList = (ArrayList)request.getAttribute("pktStkIdnList");
String memotyp = util.nvl((String)request.getAttribute("memoTyp"));
String isExpDay = "style=\"display:none\"";
if(memotyp.equals("E"))
 isExpDay ="";
int pktListSize=pktStkIdnList.size();
String srchTyp = util.nvl(info.getIsFancy());
List subStockList = new ArrayList();
HashMap ttls = (HashMap)session.getAttribute("total");
info.setViewForm("SHOW");
ArrayList vwPrpLst = info.getVwPrpLst();
HashMap mprp = info.getMprp();
String selBkt = "", selWL = "",selML = "",selPEP="", selected = "checked=\"checked\"" ;
String cb_memo = null;
ArrayList prpDspBlocked = info.getPrpDspBlocked();
ArrayList sortPrpList = info.getSrtPrpLst();
if(!srchTyp.equals("FCY") ){
prpDspBlocked.add("INTENSITY");
prpDspBlocked.add("OVERTONE");
prpDspBlocked.add("FANCY COLOR");
}
int totalRecord = 0;
int startR=0;
int endR = 0;
String flg="Z";
int iTotalSearchRecords = 10;
int tdIndex = 5;
String chkSelFun="chkSele(this,"+pktListSize+",'memo')";
%>


<form action="StockSearch.do?method=memo" method="post" onsubmit="" name="stock">
<html:hidden property="srchTyp" value="<%=srchTyp%>" name="searchForm" />
<input type="hidden" name="<%=info.getReqUrl()%>" id="reqUrl" />
<table width="100%" align="left">

<tr><td valign="top" height="15">
<table>
<tr>
<td> <table class="prcPrntTbl" cellspacing="1" cellpadding="3">
<tr>
<th height="15">&nbsp;</th>
<th height="15"><div align="center"><strong>Pcs</strong></div></th>
<th height="15"><div align="center"><strong>Cts</strong></div></th>
<th height="15"><div align="center"><strong>Base</strong></div></th>
<th height="15"><div align="center"><strong>Avg disc </strong></div></th>
<th height="15"><div align="center"><strong>Avg Price</strong></div></th>
<th height="15"><div align="center"><strong>Value</strong></div></th>


</tr>
<tr>
<td><div align="center"><strong>Total</strong></div></td>
<td height="20">
<div align="center">
<label><%=util.nvl((String)ttls.get("ZQ"))%></label>

</div>
</td>
<td><div align="center">
<label><%=util.nvl((String)ttls.get("ZW"))%></label>

</div></td>

<td><div align="center">
<label><%=util.nvl((String)ttls.get("ZB"))%></label>

</div></td>


<td><div align="center">
<label><%=util.nvl((String)ttls.get("ZD"))%></label>

</div></td>
<td><div align="center">
<label><%=util.nvl((String)ttls.get("ZA"))%></label>

</div></td>
<td><div align="center">
<label><%=util.nvl((String)ttls.get("ZV"))%></label>

</div></td>
</tr>
<tr>
<td><div align="center"><strong>Selected</strong></div></td>
<td height="20">
<div align="center">
<label id="selectpcs"><%=util.nvl((String)ttls.get("MQ"))%></label>

</div>
</td>
<td><div align="center">
<label id="selectcts"><%=util.nvl((String)ttls.get("MW"))%></label>

</div></td>


<td><div align="center">
<label id="base"><%=util.nvl((String)ttls.get("MB"))%></label>

</div></td>

<td><div align="center">
<label id="avgdis"><%=util.nvl((String)ttls.get("MD"))%></label>

</div></td>
<td><div align="center">
<label id="avgprice"><%=util.nvl((String)ttls.get("MA"))%></label>

</div></td>
<td><div align="center">
<label id="values" ><%=util.nvl((String)ttls.get("MV"))%></label>

</div></td>
</tr>
</table></td>
<td>
<table class="prcPrntTbl">
<tr><th colspan="4">Customer Information</th></tr>
<tr><td align="left"><B>Customer name:</b></td><td align="left"><%=info.getByrNm()%></td><td align="left"><b>Terms :</b></td><td align="left"><%=info.getTrms()%></td></tr>
<tr><td align="left">

<b>Email ID:</b></td><td align="left"><input type="hidden" name="byrEml" value="Y" /> <%=info.getByrEml()%></td><td align="left"><b>Sales person :</b></td><td align="left"><%=info.getSaleExNme()%></td></tr>
</table>
</td>


</tr>
</table>





</td>

</tr>



<%
String msg =(String)request.getAttribute("memoMsg");
if(msg!=null){%>
<tr>
<td valign="top">
<label class="redLabel" ><%=msg%></label>
</td></tr>
<%}
%>
<%if(info.getDmbsInfoLst().get("DATA_DIR").equals("JBBROS")){%>
<tr>
<td valign="top">
<table><tr>

<td><input type="checkbox" name="deal" value="deal" /></td><td><span class="txtBold">Deal</span></td>
</tr></table> </td>
</tr>
<%}%>
<tr><td>
<table><tr>
<td><span class="txtBold" > Select Memo Type : </span></td><td>
<html:select property="value(typ)" styleId="memoTyp" name="searchForm" onchange="displayExDay(this)" >
<html:optionsCollection property="memoList" name="searchForm"
label="dsc" value="memo_typ" />
</html:select></td>
<td valign="top"><div id="expDay" <%=isExpDay%>>
<logic:present property="value(ExpDayList)" name="searchForm" >
<table><tr><td>
<span class="txtBold"> Expiry days : </span></td><td>
<html:select property="value(day)" name="searchForm" >
<html:optionsCollection property="value(ExpDayList)" name="searchForm" value="FORM_NME" label="FORM_TTL" />
</html:select></td>
<td><span class="txtBold">Expiry Time: </span></td><td><html:text property="value(extme)" name="searchForm" size="8" /> </td> 
<td><span class="txtBold">Ext Conf %:</span></td><td><html:text property="value(extconf)" name="searchForm" size="10" /> </td> 
</tr></table>

</logic:present>
</div></td>


<%
     HashMap allPageDtl= (info.getPageDetails() == null)?new HashMap():(HashMap)info.getPageDetails();
     HashMap pageDtl=(HashMap)allPageDtl.get("SRCH_RSLT_SELECT");
    ArrayList pageList=new ArrayList();
    HashMap pageDtlMap=new HashMap();
    String fld_nme="",fld_ttl="",val_cond="",dflt_val="",fld_typ="",form_nme="",lov_qry="";
    pageList=(ArrayList)pageDtl.get("BUTTON");
            if(pageList!=null && pageList.size() >0){
            for(int j=0;j<pageList.size();j++){
            pageDtlMap=(HashMap)pageList.get(j);
            fld_nme="value("+(String)pageDtlMap.get("fld_nme")+")";
            fld_ttl=(String)pageDtlMap.get("fld_ttl");
            fld_typ=(String)pageDtlMap.get("fld_typ");
            lov_qry=(String)pageDtlMap.get("lov_qry");
            dflt_val=(String)pageDtlMap.get("dflt_val");
            val_cond=(String)pageDtlMap.get("val_cond"); 
            if(fld_typ.equals("S")){
            %>
    <td><html:submit property="<%=fld_nme%>" value="<%=fld_ttl%>" onclick="<%=val_cond%>" styleClass="submit"/></td>
    <%}else if(fld_typ.equals("B")){%>
    <td><html:button property="<%=fld_nme%>" value="<%=fld_ttl%>" onclick="<%=val_cond%>" styleClass="submit"/></td>
    <%}else if(fld_typ.equals("HB")){%>
    <td align="right"><button type="button" onclick="<%=val_cond%>" class="submit" accesskey="<%=lov_qry%>" id="<%=fld_nme%>" name="<%=fld_nme%>" ><%=fld_ttl%></button></td>    
    <%}}}
    %>

<td><a onclick="chkSele('<%=pktListSize%>','excel');"> Create Excel <img src="../images/ico_file_excel.png" title="Click here to create excel"/></a></td>
<td>Thru Person:</td><td><input type="text"  name="thruPer" size="20" /> </td>



</tr></table>

</td></tr>
<%
String srchRefMsg = (String)request.getAttribute("vnmNotFnd");
if(srchRefMsg!=null){%>
<tr><td><label class="redLabel" ><%=srchRefMsg%></label></td></tr>
<%}%>
<tr>
<td>
<jsp:include page="/marketing/stockView.jsp" />
</td></tr>
</table>
</form>
</td></tr></table>
</body>
</html>