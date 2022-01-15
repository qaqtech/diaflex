<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import="java.util.ArrayList,java.util.ArrayList,java.util.HashMap, java.sql.*, java.util.Enumeration, java.util.logging.Level"%>
<jsp:useBean id="info" class="ft.com.InfoMgr" scope="session" />
<jsp:useBean id="util" class="ft.com.JspUtil" scope="page" />
<%@taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>Packet Detail</title>
<link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/bse.css?v=<%=info.getJsVersion()%>"/>
      <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/style.css?v=<%=info.getJsVersion()%>"/>
       <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/general.css?v=<%=info.getJsVersion()%>"/>
        <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/ajax.js?v=<%=info.getJsVersion()%> " > </script>
        <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/memoScript.js?v=<%=info.getJsVersion()%> " > </script>
        <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/bse.js?v=<%=info.getJsVersion()%> " > </script>
        <script type="text/javascript" src="<%=info.getReqUrl()%>/PopScripts/popup.js?v=<%=info.getJsVersion()%> " > </script>
        <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/tableFilter/actb.js " > </script>
        <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/tableFilter/tablefilter.js " > </script>
        <script src="<%=info.getReqUrl()%>/PopScripts/jquery-1.2.6.min.js" type="text/javascript"></script>
       <script src="<%=info.getReqUrl()%>/scripts/spacecode.js?v=<%=info.getJsVersion()%>"></script>
       <script src="<%=info.getReqUrl()%>/scripts/rfid.js?v=<%=info.getJsVersion()%>"></script>
       <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/dispose.js?v=<%=info.getJsVersion()%> " > </script>
 

 
  </head>
        <%String logId=String.valueOf(info.getLogId());
        String onfocus="cook('"+logId+"')";
         HashMap allPageDtl= (info.getPageDetails() == null)?new HashMap():(HashMap)info.getPageDetails();
    HashMap pageDtl=(HashMap)allPageDtl.get("CUSTOM_SC");
    if(pageDtl==null)
    pageDtl = new HashMap();
    ArrayList rolenmLst=(ArrayList)info.getRoleLst();
         String usrFlg=util.nvl((String)info.getUsrFlg());
         String url = info.getReqUrl();
    ArrayList pageList=new ArrayList();
    HashMap pageDtlMap=new HashMap();
    String fld_nme="",fld_ttl="",val_cond="",dflt_val="",fld_typ="",form_nme="",flg1="",lov_qry="";
        %>
 <body onfocus="<%=onfocus%>" onload="MM_preloadImages('images/faunalogo.png')" topmargin="0" leftmargin="0" rightmargin="0" bottommargin="0">
  <input type="hidden" id="reqUrl" name="reqUrl" value="<%=info.getReqUrl()%>"/>

    <div id="backgroundPopup"></div>
<div id="popupContactASSFNL">
<table class="popUpTb">
 <tr><td height="5%">&nbsp;&nbsp;&nbsp;<html:button property="value(close)" styleClass="submit"  onclick="MKEmptyPopup('frame');disablePopupASSFNL()" value="Close"  /> </td></tr>
<tr><td valign="top" height="95%">
<iframe  name="frame" class="frameStyle" align="middle" frameborder="0">

</iframe></td></tr>
</table>
</div>
    
    
    <table>
   <%
   String excl = util.nvl((String)request.getAttribute("EXCL"));
  ArrayList pktDtlList = (ArrayList)session.getAttribute("pktList");
  String TTLCTS = (String)request.getAttribute("TTLCTS");
  String TTLVLU = (String)request.getAttribute("TTLVLU");
  if(pktDtlList!=null && pktDtlList.size()>0){
  ArrayList itemHdr = (ArrayList)session.getAttribute("itemHdr");
  if(!excl.equals("NO")){%>
 <tr><td valign="top" class="hedPg">   Create Excel &nbsp;&nbsp;&nbsp;
  <img src="../images/ico_file_excel.png" border="0" onclick="goTo('<%=info.getReqUrl()%>/report/genericReport.do?method=createXL','','')"/>
 </td></tr>
  <%}%>
  
  <tr><td valign="top" class="hedPg">
  <table class="dataTable" align="left" id="dataTable">
  <thead>
  <tr>
  <%
      pageList= ((ArrayList)pageDtl.get("LINK") == null)?new ArrayList():(ArrayList)pageDtl.get("LINK");
if(pageList!=null && pageList.size() >0){%>
<th>History</th>
<%}%>
  <%for(int i=0 ; i < itemHdr.size() ;i++){%>
  <th><%=itemHdr.get(i)%></th>
  <%}%>
  </tr>
  </thead>
  <tbody>
  <%for(int j=0 ; j <pktDtlList.size() ;j++){
  HashMap pktDtl = (HashMap)pktDtlList.get(j);

    String vnm = util.nvl((String)pktDtl.get("VNM"));
    String upr = util.nvl((String)pktDtl.get("upr"));
    String cmp = util.nvl((String)pktDtl.get("upr"));
    String stkIdn = util.nvl((String)pktDtl.get("stk_idn"));
    String target = "TR_"+stkIdn;
    String targetSrc = "SRC_"+stkIdn;
     String sltarget = "SL_"+stkIdn;
  %>
  <tr id="<%=target%>">
  
   <%
      pageList= ((ArrayList)pageDtl.get("LINK") == null)?new ArrayList():(ArrayList)pageDtl.get("LINK");
if(pageList!=null && pageList.size() >0){%>
<td nowrap="nowrap">
<div id="common" style="visibility:visible;position:relative;" align="left" >
<ul class="css3menu11">
<li>
<a href="#">Details.....</a>
<ul>
<%


for(int i=0;i<pageList.size();i++){
pageDtlMap=(HashMap)pageList.get(i);
fld_nme="value("+(String)pageDtlMap.get("fld_nme")+")";
dflt_val=util.nvl((String)pageDtlMap.get("dflt_val"));
dflt_val=dflt_val.replaceAll("VNM",vnm);
dflt_val=dflt_val.replaceAll("STKIDN",stkIdn);
dflt_val=dflt_val.replaceAll("UPR",upr);
dflt_val=dflt_val.replaceAll("CMP",cmp);
dflt_val=dflt_val.replaceAll("URL",info.getReqUrl());
fld_ttl=(String)pageDtlMap.get("fld_ttl");
fld_ttl=fld_ttl.replaceAll("VNM",vnm);
val_cond=util.nvl((String)pageDtlMap.get("val_cond"));
val_cond=val_cond.replaceAll("TARGET",target);
val_cond=val_cond.replaceAll("VNM",vnm);
fld_typ=util.nvl((String)pageDtlMap.get("fld_typ"));
fld_typ=fld_typ.replaceAll("VNM",vnm);
fld_typ=fld_typ.replaceAll("VNM",vnm);
String roleStr=util.nvl((String)pageDtlMap.get("lov_qry"));
boolean isDis = true;
if(!roleStr.equals("") && !usrFlg.equals("SYS")){
isDis = false;
String [] roleLst = roleStr.split(",");
for(int m=0;m<roleLst.length;m++){
    if(rolenmLst.contains(roleLst[m])){
    isDis = true;
    break;
    }
 }
}

if(isDis){
%>
<li><A href="<%=dflt_val%>" id="<%=fld_typ%>" onclick="<%=val_cond%>" target="frame"><span><%=fld_ttl%></span></a></li>
<%}

}%>
</ul>
</li>
</ul>
</div>
</td>

<%}%>
  
  <%for(int k=0;k<itemHdr.size() ;k++){
  String hdr = (String)itemHdr.get(k);
  
  %>
  <td align="right"><%=util.nvl((String)pktDtl.get(hdr))%></td>
  <%}%>
  </tr>
  <%}%>
  </tbody>
  </table></td></tr>

  <%}else{%>
<tr><td valign="top" class="hedPg">
Sorry No Result Found</td></tr>
  <%}%>
  </table>
  </body>
</html>