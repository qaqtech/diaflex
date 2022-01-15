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
  <script src="<%=info.getReqUrl()%>/jqueryScript/jqueryScript.js?v=<%=info.getJsVersion()%>" type="text/javascript"></script>
 
      <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/style.css?v=<%=info.getJsVersion()%>"/>

  <title>Pending Requests</title>
 
  </head>

        <%
     ArrayList pndPktsToIssueLst = (request.getAttribute("pndPktsToIssueLst") == null)?new ArrayList():(ArrayList)request.getAttribute("pndPktsToIssueLst");
     HashMap pndPktsToIssueDtl= (request.getAttribute("pndPktsToIssueDtl") == null)?new HashMap():(HashMap)request.getAttribute("pndPktsToIssueDtl");
     String days=util.nvl((String)request.getAttribute("days"));
     int pndPktsToIssueLstsz=pndPktsToIssueLst.size();
     HashMap allPageDtl= (info.getPageDetails() == null)?new HashMap():(HashMap)info.getPageDetails();
    HashMap pageDtl=(HashMap)allPageDtl.get("PKT_PND_TO_ISSUE");
    ArrayList pageList=new ArrayList();
    HashMap pageDtlMap=new HashMap();
    String fld_nme="",fld_ttl="",val_cond="",dflt_val="",fld_typ="",form_nme="",flg1="",lov_qry="",url="",barGrp="",color="";
    HashMap dbmsInfo = info.getDmbsInfoLst();
    String searchView =util.nvl((String)dbmsInfo.get("SEARCHVIEW"),"LIST");
        String logId=String.valueOf(info.getLogId());
        String onfocus="cook('"+logId+"')";
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
  <img src="../images/line.jpg" border="0" /></td><td valign="middle"> <span class="pgHed">Pending Requests</span> </td>
</tr></table>
  </td>
  </tr>
  <tr>
  <td class="hedPg">
  <table class="genericTB">
  <tr>
  <td>
  <html:form action="marketing/StockSearch.do?method=loadPndPktsToIssue" method="post" onsubmit="" >
  <html:hidden property="value(BTN_DAYS)" styleId="BTN_DAYS" value="" />
  <table>
  <tr>
            <%
            pageList= ((ArrayList)pageDtl.get("DAYS") == null)?new ArrayList():(ArrayList)pageDtl.get("DAYS");
            if(pageList!=null && pageList.size() >0){
            for(int j=0;j<pageList.size();j++){
            pageDtlMap=(HashMap)pageList.get(j);
            lov_qry=(String)pageDtlMap.get("lov_qry");
            if(lov_qry.equals(""))
            fld_nme="value("+(String)pageDtlMap.get("fld_nme")+")";
            else
            fld_nme=(String)pageDtlMap.get("fld_nme");
            fld_ttl=(String)pageDtlMap.get("fld_ttl");
            fld_typ=(String)pageDtlMap.get("fld_typ");
            dflt_val=(String)pageDtlMap.get("dflt_val");
            val_cond=(String)pageDtlMap.get("val_cond"); 
            if(days.equals(dflt_val)){
              color="color:#ffffff";
              }
              else{
              color="";
              }
            %>
            <td><span class="txtLinkdashboard" title="Click here To get data" id="<%=dflt_val%>_TAB" style="<%=color%>"  onclick="loadpktpndissue('<%=dflt_val%>')"> <%=fld_ttl%></span></td>
            <%}}%>
  </tr>
  </table>
  </html:form>
  </td>
  </tr>
  <%if(pndPktsToIssueLstsz>0){%>
  <tr>
  <td>
  <table class="grid1">
  <tr>
  <th>Buyer</th><th>All Packets</th><th>Memo Packets</th><th>Pending Packets</th>
  </tr>
  <%for(int i=0;i<pndPktsToIssueLstsz;i++){
  String nme_idn=util.nvl((String)pndPktsToIssueLst.get(i));
  HashMap pktPrpMap = new HashMap();
  pktPrpMap=(HashMap)pndPktsToIssueDtl.get(nme_idn);
  int all_pkts=Integer.parseInt(util.nvl((String)pktPrpMap.get("ALL_PKTS"),"0"));
  int memo_pkts=Integer.parseInt(util.nvl((String)pktPrpMap.get("MEMO_PKTS"),"0"));
  int bal_pkts=all_pkts-memo_pkts;
  %>
  <tr>
  <td><%=util.nvl((String)pktPrpMap.get("NME"))%></td>
  <td align="right"><%=all_pkts%></td>
  <td align="right"><%=memo_pkts%></td>
  <td align="right">
  <%if(bal_pkts!=0){%>
  <a href="StockSearch.do?method=loadsearchFromPndissue&nme_idn=<%=nme_idn%>&days=<%=days%>&searchView=<%=searchView%>" target="srchFix">
  <%=bal_pkts%>
  <%}else{%>
  0
  <%}%>
  </a>
  </td>
  </tr>
  <%}%>
  </table>
  </td>
  </tr>
  <%}else{%>
  <tr>
  <td>
  Sorry No result Found
  </td>
  </tr>
  <%}%>
  </table>
  </td>
  </tr>
  <tr><td>
<jsp:include page="/footer.jsp" />
</td></tr>
  </table>  
       <%@include file="../calendar.jsp"%>
  </body>
</html>