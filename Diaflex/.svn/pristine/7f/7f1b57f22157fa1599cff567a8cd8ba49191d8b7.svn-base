<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page errorPage="../error_page.jsp" %>
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import="java.util.ArrayList,java.util.HashMap,java.sql.ResultSet,ft.com.*, java.util.Date, java.util.ArrayList, ft.com.dao.*"%>
<jsp:useBean id="db" class="ft.com.DBMgr" scope="session" />
<jsp:useBean id="log" class="ft.com.LogMgr" scope="session" />
<jsp:useBean id="util" class="ft.com.DBUtil" scope="session" />
<jsp:useBean id="info" class="ft.com.InfoMgr" scope="session" />
<%@taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>
<%@taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>Propos To Live</title>
  <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/bse.css?v=<%=info.getJsVersion()%>"/>
    <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/style.css?v=<%=info.getJsVersion()%>"/>
     <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/bse.js?v=<%=info.getJsVersion()%> " > </script>
    
  </head>
<%
  session.setAttribute("RNG",new ArrayList());
String remVal = util.nvl((String)request.getAttribute("rem"));
%>
  <body onload="MM_preloadImages('images/faunalogo.png')" topmargin="0" leftmargin="0" rightmargin="0" bottommargin="0">
<table width="100%"  border="0" align="center" cellpadding="0" cellspacing="0" class="mainbg">
  <tr>
    <td height="103" valign="top">
   <jsp:include page="/header.jsp" />
    
    </td>
  </tr>
 
  <tr>
    <td height="5" valign="top" class="greyline"><img src="<%=info.getReqUrl()%>/images/grey_linebig.png" width="685" height="5" align="right" /></td>
  </tr>
   <tr>
  <td valign="top" class="hedPg">
  <table cellpadding="1" cellspacing="5"><tr><td valign="middle">
  <img src="../images/line.jpg" border="0" /></td><td valign="middle"> <span class="pgHed">Propose To Live</span> </td>
   </tr></table>
  </td>
  </tr>
  <tr>
  <td valign="top" class="tdLayout"> <%
   HashMap dbinfo = info.getDmbsInfoLst();
     String repPath = (String)dbinfo.get("REP_PATH");
  String cnt = (String)dbinfo.get("CNT");
    String url =repPath+"/reports/rwservlet?"+cnt+"&report="+info.getRportUrl()+"\\reports\\PropPrice.jsp";
       
    %>
     <span class="txtLink"><a href="<%=url%>"  target="_blank"> Difference Report</a></span> </td>
  </tr>
  <tr>
  <td valign="top" class="tdLayout" height="10px">
  </td></tr>
  <tr>
  
  <td valign="top" class="tdLayout">
  <html:form action="pricing/proposeToLive.do?method=Fetch" method="post">
  <table class="grid1">
  <tr><td>Rem Name: </td>
  <td>
  <%ArrayList remList = (ArrayList)session.getAttribute("remList"); %>
  <select name="rem" >
  <% if(remList!=null && remList.size()>0){
  for(int i=0;i<remList.size();i++){
  ArrayList remDtl = (ArrayList)remList.get(i);
  String rem = (String)remDtl.get(0);
   String isSelected = "" ;
  if(remVal.equals(rem))
     isSelected = "selected=\"selected\"";
  %>
  <option value="<%=rem%>" <%=isSelected%>><%=rem%></option>
  <%}}%>
  </select>
  </td>
  </tr>
  <tr><td colspan="2"><input type="submit" name="sumit" class="submit" value="Fetch" /> </td> </tr>
  </table>
  </html:form>
  </td></tr>
   <tr>
  <td valign="top" class="tdLayout" ></td></tr>
  
  <tr>
  <td valign="top" class="tdLayout">
  <%
  
  
 

  %>
   <form action="proposeToLive.do?method=save" name="verify" method="post">
   
   <input type="hidden" name="rem" value="<%=remVal%>" />
 <% ArrayList remList = (ArrayList)request.getAttribute("remList");
 int count=0;
 if(remList!=null && remList.size() >0){
  String urlCom = info.getReqUrl()+"/pricing/proposeToLive.do?method=comparison";
 %>
 <p>
 <%
    HashMap allPageDtl= (info.getPageDetails() == null)?new HashMap():(HashMap)info.getPageDetails();
    HashMap pageDtl=(HashMap)allPageDtl.get("PROPOSE_TO_LIVE");
    ArrayList pageList=new ArrayList();
    HashMap pageDtlMap=new HashMap();
    String fld_nme="",fld_ttl="",val_cond="",dflt_val="",fld_typ="",form_nme="";
    pageList=(ArrayList)pageDtl.get("SUBMIT");
     if(pageList!=null && pageList.size() >0){
       for(int j=0;j<pageList.size();j++){
         pageDtlMap=(HashMap)pageList.get(j);
         fld_nme=(String)pageDtlMap.get("fld_nme");
         fld_ttl=(String)pageDtlMap.get("fld_ttl");
         val_cond=(String)pageDtlMap.get("val_cond"); %>
<input type="submit" name="<%=fld_nme%>" class="submit" onclick="<%=val_cond%>" value="<%=fld_ttl%>" id="verify"/>
   &nbsp;&nbsp;
 <%
}}
%>
 <!--
 <p>
 <input type="submit" name="verifyALL" class="submit" onclick="return confirmChanges()" value="Verify Changes ALL" id="verify"/>
   &nbsp;&nbsp;
   <input type="submit" name="verifySelected" class="submit" onclick="return validate_SelectAll('verify','cb_rem_')" value="Verify Changes Selected" id="verify"/>
    &nbsp;&nbsp;-->
   <input type="submit" name="comparison" class="submit"  value="Comparison" id="comparison"/>&nbsp;&nbsp;
   <input type="submit" name="CreatePDF" class="submit"  value="Create PDF" id="Create PDF"/>
</p>
 <table class="grid1">

 <tr><th><input type="checkbox" name="checkAll" id="checkAll" onclick="checkALL('cb_rem_', 'count')" />Select ALL </th><th>Name</th><th>Dsc</th> 
 <th>Date</th><th>Typ</th>
 <th>Propose</th><th>Live</th>
<th>Revert</th>
 
 </tr>
 <%
  for(int i=0;i<remList.size();i++){
  ArrayList remDtl = (ArrayList)remList.get(i);
  String rem = (String)remDtl.get(3);
  String nme = (String)remDtl.get(1);
  count = i+1;
  String fldVal = "cb_rem_"+count;
  String fldId = "cb_rem_"+count;
  String link1= info.getReqUrl()+"/pricing/proposeToLive.do?method=ReverttoLive&rem="+rem+"&nme="+nme ;
  String onclick = "return setConfirm('"+link1+"','Are you sure you want to Revert to Live','_blank')";
 %>
 <tr> <td><input type="checkbox" name="<%=fldVal%>" id="<%=fldId%>" value="<%=nme%>"/> </td>

  
 <td> <%=remDtl.get(1)%></td><td><%=remDtl.get(5)%></td> <td><%=remDtl.get(2)%></td> 
 <td><%=remDtl.get(6)%></td>
 

 <td><A href="<%=info.getReqUrl()%>/pricing/genPriceGrid.jsp?id=<%=remDtl.get(0)%>&plDb=Y&view=Y&grpNme=<%=rem%>" target="_blank">Propose</a></td>
 <td><A href="<%=info.getReqUrl()%>/pricing/genPriceGrid.jsp?id=<%=remDtl.get(4)%>&view=Y&grpNme=<%=rem%>&lvDb=Y" target="_blank">Live</a></td>
 <td> <A href="<%=link1%>"  onclick="<%=onclick%>" >  Revert to Live </a></td>
 </tr>
 <%}%>
 </table>
 <input type="hidden" name="count" id="count" value="<%=count%>" />
   
 <%}%>
 
 </form>
  </td></tr>
  <tr>
    <td><jsp:include page="/footer.jsp" /> </td>
    </tr>
  </table>
  </body>
</html>