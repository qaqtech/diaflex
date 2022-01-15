 <!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
 
<%@ page errorPage="../error_page.jsp" %>
<%@ page contentType="text/html;charset=windows-1252"%>
<jsp:useBean id="util" class="ft.com.JspUtil" scope="page" />
<jsp:useBean id="info" class="ft.com.InfoMgr" scope="session" />
<%@ page import="java.util.ArrayList,java.util.HashMap,java.sql.ResultSet,ft.com.*, java.util.Date, java.util.ArrayList, ft.com.dao.*"%>
<%@taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>Mix Stock Tally</title>
    <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/bse.css?v=<%=info.getJsVersion()%>"/>
    <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/general.css?v=<%=info.getJsVersion()%>"/>
     <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/style.css?v=<%=info.getJsVersion()%>"/>
      <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/bse.js?v=<%=info.getJsVersion()%> " > </script>
    <script language="javaScript" type="text/javascript" src="<%=info.getReqUrl()%>/calendar/calendar.js"></script>
   <link href="<%=info.getReqUrl()%>/calendar/calendar.css" rel="stylesheet" type="text/css">
  </head>
        <%
    HashMap allPageDtl= (info.getPageDetails() == null)?new HashMap():(HashMap)info.getPageDetails();
    HashMap pageDtl=(HashMap)allPageDtl.get("MIX_STK_TALLY");
    ArrayList pageList=new ArrayList();
    HashMap pageDtlMap=new HashMap();
    String fld_nme="",fld_ttl="",val_cond="",dflt_val="",fld_typ="",form_nme="",flg1="",lov_qry="";
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
  <img src="../images/line.jpg" border="0" /></td><td valign="middle"> <span class="pgHed">Stock Tally History</span> </td>
</tr></table>
  </td>
  </tr>
  <%
  if(request.getAttribute("msg")!=null){%>
   <tr> <td valign="top" class="tdLayout"><%=request.getAttribute("msg")%></td></tr>
  <%}%>
   <tr> <td valign="top" class="tdLayout">
   <table><tr><td>
   <html:form action="mixAkt/mixStockTally.do?method=history" method="post" >
  <table  class="grid1">
  <tr><th colspan="2">Search</th></tr>
 <tr><td>Seq No : </td><td>
   <html:select property="value(seq)" name="mixStockTallyForm" >
        <html:option value="0">----select----</html:option>
        <html:optionsCollection property="seqList" label="dsc" value="nme" />
  </html:select>
  </td></tr>
  <tr>
        <td>Status :&nbsp;</td><td>
        <html:select property="value(stt)"  name="mixStockTallyForm" styleId="stt">
        <%pageList=(ArrayList)pageDtl.get("STATUS");
            if(pageList!=null && pageList.size() >0){
            for(int j=0;j<pageList.size();j++){
            pageDtlMap=(HashMap)pageList.get(j);
            fld_nme="value("+(String)pageDtlMap.get("fld_nme")+")";
            fld_ttl=(String)pageDtlMap.get("fld_ttl");
            fld_typ=(String)pageDtlMap.get("fld_typ");
            form_nme=(String)pageDtlMap.get("form_nme");
            lov_qry=(String)pageDtlMap.get("lov_qry");
            val_cond=(String)pageDtlMap.get("val_cond"); %>
            <html:option value="<%=lov_qry%>"><%=fld_ttl%></html:option>
            <%}}%>
        </html:select>
        </td>
        </tr>
        
      <tr><td>Issue Date : </td>  
       <td><html:text property="value(issdte)" styleId="issdte" name="mixStockTallyForm" size="15" /><img src="<%=info.getReqUrl()%>/images/calender.png" onClick="setYears(2011, <%=info.getCurrentYear()%>);showCalender(this, 'issdte');"> 
       </td>
      </tr>
          <tr><td>Return Date : </td>  
       <td><html:text property="value(rtndte)" styleId="rtndte" name="mixStockTallyForm" size="15" /><img src="<%=info.getReqUrl()%>/images/calender.png" onClick="setYears(2011, <%=info.getCurrentYear()%>);showCalender(this, 'rtndte');"> 
       </td>
      </tr>
   <tr><td colspan="2" align="center"><html:submit property="value(srch)" value="Fetch" styleClass="submit" /></td> </tr>
   </table>
   </html:form>
   </td></tr>
   
   </table>
  </td></tr>
    <tr> <td valign="top" class="tdLayout" height="10px"></td></tr>
    
     <%
     String view = (String)request.getAttribute("view");
     if(view!=null){
       ArrayList itemList = new ArrayList();
    itemList.add("Description");
    itemList.add("Status");
    itemList.add("Issue Date");
    itemList.add("Return Date");
    itemList.add("Iss Qty");
    itemList.add("Iss Cts");
    itemList.add("Rtn Qty");
    itemList.add("Rtn Cts");
    itemList.add("User");
    ArrayList pktDtlList = new ArrayList();
     ArrayList pktlist =(ArrayList)request.getAttribute("pktDtlList");
     if(pktlist!=null && pktlist.size()>0){%>
    <tr> <td valign="top" class="tdLayout">
       Create Excel <img src="../images/ico_file_excel.png" border="0" onclick="goTo('<%=info.getReqUrl()%>/report/genericReport.do?method=createXL','','')"/>
     </td></tr>
     <tr> <td valign="top" class="tdLayout">
      <table class="grid1">
   <tr>
    <th>Dsc </th><th>Satus</th>
   <th>Issue Date</th><th>Return Date</th>
   <th>Iss Qty</th><th>Iss Cts</th>
   <th>Rtn Qty</th><th>Rtn Cts</th><th>User</th></tr>
     <%for(int i=0;i<pktlist.size();i++){
     HashMap pktDtl = (HashMap)pktlist.get(i);
     HashMap pktDtlExcel = new HashMap();
  pktDtlExcel.put("Description",util.nvl((String)pktDtl.get("DSC")));
  pktDtlExcel.put("Status",util.nvl((String)pktDtl.get("STKSTT")));
  pktDtlExcel.put("Issue Date",util.nvl((String)pktDtl.get("ISS_DTE")));
  pktDtlExcel.put("Return Date",util.nvl((String)pktDtl.get("RTN_DTE")));
  pktDtlExcel.put("Iss Qty",util.nvl((String)pktDtl.get("ISS_QTY")));
  pktDtlExcel.put("Iss Cts",util.nvl((String)pktDtl.get("ISS_CTS")));
  pktDtlExcel.put("Rtn Qty",util.nvl((String)pktDtl.get("RTN_QTY")));
  pktDtlExcel.put("Rtn Cts",util.nvl((String)pktDtl.get("RTN_CTS")));
  pktDtlExcel.put("User",util.nvl((String)pktDtl.get("ISS_USR")));
  pktDtlList.add(pktDtlExcel);
     %>
     <tr>
      <td><%=pktDtl.get("DSC")%></td> <td><%=pktDtl.get("STKSTT")%></td> <td><%=pktDtl.get("ISS_DTE")%></td> <td><%=pktDtl.get("RTN_DTE")%></td>
      <td align="right"><%=pktDtl.get("ISS_QTY")%></td><td align="right"><%=pktDtl.get("ISS_CTS")%></td>
      <td align="right"><%=pktDtl.get("RTN_QTY")%></td><td align="right"><%=pktDtl.get("RTN_CTS")%></td>
      <td><%=pktDtl.get("ISS_USR")%></td>
      </tr>
     <%}%>
     </table></td></tr>
     <%
     session.setAttribute("pktList", pktDtlList);
     session.setAttribute("itemHdr", itemList);
     }else{%>
   <tr> <td valign="top" class="tdLayout">  <B> Sorry no result found...</b></td></tr>
     <%}}%>
     </table>
     <%@include file="/calendar.jsp"%>
    </body>
</html>