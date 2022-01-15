<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
 
<%@ page errorPage="../error_page.jsp" %>
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import="java.util.ArrayList,java.util.ArrayList,java.util.List, java.util.HashMap, java.sql.*, java.util.Enumeration, java.util.logging.Level"%>
<jsp:useBean id="util" class="ft.com.JspUtil" scope="page" />
<jsp:useBean id="info" class="ft.com.InfoMgr" scope="session" />
<%@taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
<title>Active demands</title>
<script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/bse.js?v=<%=info.getJsVersion()%> " > </script>
<link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/bse.css?v=<%=info.getJsVersion()%>"/>
<script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/ajax.js?v=<%=info.getJsVersion()%> " > </script>
<script language="javaScript" type="text/javascript" src="<%=info.getReqUrl()%>/calendar/calendar.js"></script>
<link href="<%=info.getReqUrl()%>/calendar/calendar.css" rel="stylesheet" type="text/css" />


<link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/style.css?v=<%=info.getJsVersion()%>"/>

</head>
<%
ArrayList actWishLst = (ArrayList)request.getAttribute("dmdList");
        String logId=String.valueOf(info.getLogId());
        String onfocus="cook('"+logId+"')";
        HashMap dbinfo = info.getDmbsInfoLst();
String cnt = (String)dbinfo.get("CNT");
String pgDef = "SEARCH_RESULT";
HashMap allPageDtl= (info.getPageDetails() == null)?new HashMap():(HashMap)info.getPageDetails();
HashMap pageDtl=(HashMap)allPageDtl.get(pgDef);
String memolmt = util.nvl(info.getMemo_lmt());
    ArrayList pageList=new ArrayList();
    HashMap pageDtlMap=new HashMap();
    String fld_nme="",fld_ttl="",val_cond="",dflt_val="",fld_typ="",form_nme="",lov_qry="",flg1="";
        %>
 <body onfocus="<%=onfocus%>">

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
<td valign="top" class="hedPg">
<table cellpadding="1" cellspacing="5"><tr><td valign="middle">
<img src="../images/line.jpg" border="0" /></td><td valign="middle"> <span class="pgHed">
Active Demand
</span> </td>
</tr></table>
</td>
</tr>
<tr><td valign="top" class="hedPg"><span id="msggenerate"></span></td></tr>
<tr>
<td valign="top" class="hedPg">
<html:form action="/marketing/StockSearch.do?method=DmdList" method="POST">

<input type="hidden" name="webUrl" id="webUrl" value="<%=dbinfo.get("REP_URL")%>"/>
<input type="hidden" name="cnt" id="cnt" value="<%=cnt%>"/>

<input type="hidden" name="repUrl" id="repUrl" value="<%=dbinfo.get("HOME_DIR")%>"/>
<input type="hidden" name="repPath" id="repPath" value="<%=dbinfo.get("REP_PATH")%>"/>
<table>
<tr>
 <td>
 <span class="txtBold"> Employee : </span> 
 <html:select property="byrId" styleId="byrId" onchange="getEmployeeDmd(this)"  name="searchForm"   >
 <html:option value="0" >--select--</html:option>
 <html:optionsCollection property="byrList" name="searchForm" 
  label="byrNme" value="byrIdn" />
  </html:select></td>
<td> <span class="txtBold" >Party Name:</span></td><td>
<html:select property="party" name="searchForm" styleId="party" >
<html:option value="0">---select---</html:option>
<html:optionsCollection property="partyList" name="searchForm"
label="byrNme" value="byrIdn" />

</html:select>
</td>
<td><html:submit property="value(ftchDmd)" styleClass="submit" /> </td>

<%    pageList=(ArrayList)pageDtl.get("GENERATE");
            if(pageList!=null && pageList.size() >0){
            for(int j=0;j<pageList.size();j++){
            pageDtlMap=(HashMap)pageList.get(j);
            fld_nme=(String)pageDtlMap.get("fld_nme");
            fld_ttl=(String)pageDtlMap.get("fld_ttl");
            fld_typ=(String)pageDtlMap.get("fld_typ");
            lov_qry=(String)pageDtlMap.get("lov_qry");
            dflt_val=(String)pageDtlMap.get("dflt_val");
            val_cond=(String)pageDtlMap.get("val_cond"); 
            %>
    <td align="right"><button type="button" onclick="<%=val_cond%>" class="submit" id="<%=fld_nme%>" name="<%=fld_nme%>" ><%=fld_ttl%></button></td>    
    <%}}
    %>
    <!--<td align="right"><button type="button" onclick="generatesrnoxl('party','msggenerate');" class="submit" id="generatesrno" name="generatesrno" >Generate SrNo</button></td>-->    
</tr>
</table>
</html:form>
</td></tr>
<tr>
<td valign="top" class="hedPg">
<%if(actWishLst!=null && actWishLst.size()>0){%>
<table class="dataTable" >
<tr><th>Sr No</th><th>Wishlist ID</th><th>Buyer Name</th><th>Wishlist Name</th><th></th><th></th> </tr>
<%for(int i=0 ; i<actWishLst.size();i++){
ArrayList actDmd = (ArrayList)actWishLst.get(i);
String dmdId=(String)actDmd.get(0);
%>
<tr><td align="center" ><%=i+1%></td><td align="center"><%=actDmd.get(0)%> </td><td align="center"><%=actDmd.get(1)%></td><td align="center"><%=actDmd.get(2)%> </td>
<td align="center"><div align="center"> <button name="viewWish" type="submit" class="submit" onclick="loadDmdPrp('dmd','<%=dmdId%>')">View Your Active Demand List</button></div> </td><td align="center">
<div align="center"><button type="submit" onclick="removeDmd('<%=dmdId%>')" class="submit" name="deletWish">Delete Your Demand List</button></div> </td>
</tr>
<%}%>
</table>
<%}%>
</td></tr></table>


</td></tr>
<tr>
    <td><jsp:include page="/footer.jsp" /> </td>
    </tr>
</table>

<%@include file="../calendar.jsp"%>

</body>
</html>
