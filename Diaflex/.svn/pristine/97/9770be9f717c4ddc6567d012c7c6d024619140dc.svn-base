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
    <title>Stock Properties Update</title>
    <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/bse.css?v=<%=info.getJsVersion()%>"/>
      <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/style.css?v=<%=info.getJsVersion()%>"/>
      <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/memoScript.js?v=<%=info.getJsVersion()%> " > </script>
        <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/bse.js?v=<%=info.getJsVersion()%> " > </script>
          <script type="text/javascript" src="<%=info.getReqUrl()%>/PopScripts/popup.js?v=<%=info.getJsVersion()%> " > </script>
            <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/general.css?v=<%=info.getJsVersion()%>"/>
                <script src="<%=info.getReqUrl()%>/PopScripts/jquery-1.2.6.min.js" type="text/javascript"></script>
        </head>
        <%String logId=String.valueOf(info.getLogId());
        String onfocus="cook('"+logId+"')";
        %>
 <body onfocus="<%=onfocus%>">
  <%
    
  ArrayList prpDspBlocked = info.getPageblockList();
  HashMap stockPrpUpd = (HashMap)request.getAttribute("labDtlList");
    HashMap labList = (HashMap)request.getAttribute("labList");
   String mstkIdn = request.getParameter("mstkIdn");
   String vnm = util.nvl((String)request.getParameter("vnm"));
   ArrayList grpList = (ArrayList)request.getAttribute("grpList");
   ArrayList vwPrpLst = (ArrayList)session.getAttribute("pktViewLst");
   int vwPrpLstsz=vwPrpLst.size();
   HashMap mprp = info.getMprp();
   HashMap prp = info.getPrp();
   if(grpList!=null && grpList.size() >0){%>
   <table cellpadding="0" cellspacing="0">
 <tr><td>
 <table><tr>
  <td><span class="txtLink"><%=vnm%></span></td>
  </tr></table>
 </td></tr>
  <tr><td valign="top" class="hedPg">
  <table class="grid1">
  <tr><th>LAB</th>
  <th>GRP</th>
  <th nowrap="nowrap"><label title="Price Detail">Detail</label></th>
  <%for(int j=0; j < vwPrpLst.size(); j++ ){
    String lprp = (String)vwPrpLst.get(j);
     String hdr = (String)mprp.get(lprp);
    String prpDsc = (String)mprp.get(lprp+"D");
    if(hdr == null) {
        hdr = lprp;
       }  
if(prpDspBlocked.contains(lprp)){
}else{
%>
<th title="<%=prpDsc%>"><%=hdr%></th>

<%}}%>
  </tr>
  <%for(int g=0;g < grpList.size();g++){
  String grp = (String)grpList.get(g);
  String target = "DV_"+grp;
  %>
  <tr>
  <td  nowrap="nowrap"><%=util.nvl((String)labList.get(grp))%></td>
  <td  nowrap="nowrap">GRP -<%=grp%></td>
  <td nowrap="nowrap"><span  class="img"><img src="../images/plus.png"  id="IMG_<%=grp%>"  onclick="PktGRPPriceDtl('<%=mstkIdn%>','<%=grp%>')" border="0"/></span></td>
  <%for(int i=0;i<vwPrpLstsz;i++){
  String lprp=(String)vwPrpLst.get(i);
  if(prpDspBlocked.contains(lprp)){
  }else{
  %>
  <td nowrap="nowrap"><%=util.nvl((String)stockPrpUpd.get(mstkIdn+"_"+lprp+"_"+grp))%></td>
  <%}}%>
  </tr>
  <tr id="<%=grp%>" style="display:none"><td colspan="<%=(vwPrpLstsz+2)%>">
  <div id="<%=target%>"></div>
  </td></tr>
  <%}%>
  </table>
  </td></tr></table>
   <%}%>
  </body>
</html>