<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page errorPage="../error_page.jsp" %>
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import="java.util.ArrayList,java.util.ArrayList,java.util.HashMap, java.sql.*, java.util.Enumeration, java.util.logging.Level"%>
<jsp:useBean id="info" class="ft.com.InfoMgr" scope="session" />
<jsp:useBean id="util" class="ft.com.JspUtil" scope="page" />
<%@taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%  ArrayList prpDspBlocked = info.getPageblockList();%>
<html>
  <head>
 <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
  
  <title>Offer Reports</title>
  <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/bse.js?v=<%=info.getJsVersion()%> " > </script>
<link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/bse.css?v=<%=info.getJsVersion()%>"/>
<script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/ajax.js?v=<%=info.getJsVersion()%> " > </script>
           <script language="javaScript" type="text/javascript" src="<%=info.getReqUrl()%>/calendar/calendar.js"></script>

  <link href="<%=info.getReqUrl()%>/calendar/calendar.css" rel="stylesheet" type="text/css">
 
 
      <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/style.css?v=<%=info.getJsVersion()%>"/>
  </head>
        <%String logId=String.valueOf(info.getLogId());
        String onfocus="cook('"+logId+"')";
        

  HashMap allPageDtl= (info.getPageDetails() == null)?new HashMap():(HashMap)info.getPageDetails();
  HashMap pageDtl=(HashMap)allPageDtl.get("OFFER_REPORT");
    ArrayList pageList=new ArrayList();
    HashMap pageDtlMap=new HashMap();
    String fld_nme="",fld_ttl="",val_cond="",dflt_val="",fld_typ="",form_nme="",flg1="",lov_qry="";
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
  <img src="../images/line.jpg" border="0" /></td><td valign="middle"> <span class="pgHed">
 Offer Report
 </span> </td>
</tr></table>
  </td>
  </tr>
  <tr>
  <td valign="top" class="hedPg">
  <table>
  <tr>
  <html:form  action="report/pepReport.do?method=loadbid" method="post">
  <%
            pageList=(ArrayList)pageDtl.get("CHK");
             if(pageList!=null && pageList.size() >0){%>
             <%
               for(int j=0;j<pageList.size();j++){
                 pageDtlMap=(HashMap)pageList.get(j);
                 fld_nme="value("+(String)pageDtlMap.get("fld_nme")+")";
                 fld_ttl=(String)pageDtlMap.get("fld_ttl");
                 dflt_val=(String)pageDtlMap.get("dflt_val");
                 fld_typ=(String)pageDtlMap.get("fld_typ");
                 form_nme=(String)pageDtlMap.get("form_nme");
                 if(!fld_typ.equals("S")){
                 %>
            <td>   <html:checkbox property="<%=fld_nme%>" value="<%=dflt_val%>" name="<%=form_nme%>"/><%=fld_ttl%>&nbsp;</td>
             <%}else{%>
           
             <%}}%>
            <%}%>
             <td>Date</td>
  <td>From : &nbsp;
   <html:text property="value(frmdte)"   styleId="frmdte"/>
                <img src="<%=info.getReqUrl()%>/images/calender.png" onClick="setYears(2011, <%=info.getCurrentYear()%>);showCalender(this, 'frmdte');">
   </td><td>To : &nbsp;
   <html:text property="value(todte)"  styleId="todte"/>
                <img src="<%=info.getReqUrl()%>/images/calender.png" onClick="setYears(2011, <%=info.getCurrentYear()%>);showCalender(this, 'todte');">

   </td>
  <td>  <html:submit property="value(submit)" value="Fetch" styleClass="submit"/></td>
  <!--
  <html:checkbox property="value(web)" value="WEB" name="reportForm"/>Website&nbsp;
  <html:checkbox property="value(diaflex)" value="DF" name="reportForm"/>Diaflex&nbsp;
  <html:submit property="value(submit)" value="Fetch" styleClass="submit"/>
  -->
  </html:form></tr></table>
  </td>
  </tr>
  <%
   ArrayList repMemoLst =(ArrayList)session.getAttribute("memoVwList");
  
  int count =0;
   ArrayList itemHdr = new ArrayList();
   ArrayList  pktDtlList = (ArrayList)request.getAttribute("pktList");
   HashMap mprp = info.getMprp();
   if(pktDtlList!=null && pktDtlList.size()>0){
  %>
  <html:form  action="report/pepReport.do?method=Allocate" method="post">
  <tr><td valign="top" class="hedPg"  >
  <html:submit property="value(submit)" value="Save Changes(Allocate)" styleClass="submit"/>
  <%
  ArrayList rolenmLst=(ArrayList)info.getRoleLst();
  String usrFlg=util.nvl((String)info.getUsrFlg());
  if(usrFlg.equals("SYS") || rolenmLst.contains("ADMIN")){
  %>
  &nbsp;&nbsp;<img src="../images/edit.jpg" title="Edit Properties Module" onclick="newWindow('<%=info.getReqUrl()%>/dynamicProperty.do?method=load&mdl=MEMO_VW&sname=memoVwList&par=A')" border="0" width="15px" height="15px"/>
  <%}%>
  
  &nbsp;&nbsp; Excel <img src="../images/ico_file_excel.png" onclick="goTo('<%=info.getReqUrl()%>/report/pepReport.do?method=createXL','','')" border="0"/> 
  </td></tr>
  <%String msg = (String)request.getAttribute("msg");
  if(msg!=null){
  %>
  <tr><td valign="top" class="hedPg"  ><span class="redLabel" > <%=msg%></span></td></tr>
  <%}%>
  <tr><td valign="top" class="hedPg"  > 
  <table class="dataTable" >
  <thead>
  <tr>
  <%itemHdr.add("byrNm");itemHdr.add("vnm");itemHdr.add("rnk");%>
  <th>SR NO.</th>
  <th>None</th>
  <th>Select  </th>
  <th>Buyer Name</th>
  <th>Offer Date</th>
  <th>Packet</th>
  <th>Rank</th>
  <%for(int j=0; j < repMemoLst.size(); j++ ){
    String prp = (String)repMemoLst.get(j);
     String hdr = (String)mprp.get(prp);
    String prpDsc = (String)mprp.get(prp+"D");
    if(hdr == null) {
        hdr = prp;
       }
if(prpDspBlocked.contains(prp)){
}else if(prp.equals("RAP_DIS")) {%>
<th title="<%=prpDsc%>"><%=hdr%></th>
<th title="Offer Discout">Offer Dis</th>
<th title="Net Discout">Net Dis</th>
<%
itemHdr.add(prp);itemHdr.add("offer_dis");itemHdr.add("net_dis");
}else if(prp.equals("RTE")) {%>
<th title="<%=prpDsc%>"><%=hdr%></th>
<th title="Offer Price">Offer Prc</th>
<th title="Net Price">Net Rte</th>
 <%
 itemHdr.add(prp);itemHdr.add("offer_rte");itemHdr.add("net_rte");
 pageList=(ArrayList)pageDtl.get("LMT");
  if(pageList!=null && pageList.size() >0){%>
  <th title="Offer Limit">Offer Limit</th>
   <%   itemHdr.add("ofr_lmt");
   }%>
<%}else{%>
<th title="<%=prpDsc%>"><%=hdr%></th>
<%
itemHdr.add(prp);
}}%>       

</tr>
</thead>
<%
String tableTD = "";
  
for(int m=0;m<pktDtlList.size();m++){
       count++;
      
       String sttTdBg = "";
        if(m%2==0){
        tableTD="even";
        }else{
        tableTD="odd";
        }
      String certImgLink =null;
      String imgLink = "NO IMG" ;
      HashMap pktDtl = (HashMap)pktDtlList.get(m);
      int sr = m+1;
      String stt = (String)pktDtl.get("stt");
      String ofr_rte = (String)pktDtl.get("offer_rte");
      String ofr_dis = (String)pktDtl.get("offer_dis");
      String net_dis = (String)pktDtl.get("net_dis");
      String net_rte = (String)pktDtl.get("net_rte");
      String rnk = (String)pktDtl.get("rnk");
      String byrNm = (String)pktDtl.get("byrNm");
      String bidIdn = (String)pktDtl.get("bidIdn");
      String byrIdn = (String)pktDtl.get("byrIdn");
      String stk_idn = (String)pktDtl.get("stk_idn");
      String lmt = (String)pktDtl.get("ofr_lmt");
      String onChange ="UpdateGT('"+stk_idn+"','"+bidIdn+"',this)";
      String chkId = "CHK_"+count;
      %>
<tr class="<%=tableTD%>">
<td><%=sr%></td>
<td><input type="radio" name="<%=stk_idn%>"  onclick="<%=onChange%>" value="NONE" /> </td>
<td><input type="radio" name="<%=stk_idn%>"  onclick="<%=onChange%>" value="ALLOW" /> </td>
<td title="buyer Name"><B><%=byrNm%></b></td>
<td title="Packet"><%=(String)pktDtl.get("offer_dte")%></td>
<td title="Packet"><%=(String)pktDtl.get("vnm")%></td>
<td title="Rank"><%=(String)pktDtl.get("rnk")%></td>
  <%for(int p=0; p < repMemoLst.size(); p++ ){
    String prp = (String)repMemoLst.get(p);
    String prpValue =  (String)pktDtl.get(prp);
    String hdr = (String)mprp.get(prp);
    String prpDsc = (String)mprp.get(prp+"D");
    if(hdr == null) 
        hdr = prp;
if(prpDspBlocked.contains(prp)){
}else if(prp.equals("RAP_DIS")) {%>
<td title="<%=prpDsc%>"><B><%=prpValue%></b></td>
<td title="Offer Discout"><b><%=ofr_dis%></b></td>
<td title="Offer Discout"><b><%=net_dis%></b></td>
<%
}else if(prp.equals("RTE")) {%>
<td title="<%=prpDsc%>"><B><%=prpValue%></b></td>
<td title="Offer Price"><b><%=ofr_rte%></b></td>
<td title="Net Price"><B><%=net_rte%></b></td>
<%
pageList=(ArrayList)pageDtl.get("LMT");
  if(pageList!=null && pageList.size() >0){%>
  <td title="Offer Limit"><B><%=lmt%></b></td>
   <%   }%>
<%}else{%>
<td title="<%=prpDsc%>"><%=prpValue%></td>
<%
}}%>       

<%}%>

</tbody></table>

<input type="hidden" name="count" id="count" value="<%=count%>" />
  </td></tr>
  </html:form>
  <%
  session.setAttribute("itemHdr", itemHdr);
  session.setAttribute("peppktDtlList", pktDtlList);
  
  }else{%>
  <tr><td valign="top" class="hedPg">Sorry No Result Found</td></tr>
  <%}%>
<tr>
    <td><jsp:include page="/footer.jsp" /> </td>
    </tr>
  
  
  </table>
   <%@include file="../calendar.jsp"%>
  </body>
</html>