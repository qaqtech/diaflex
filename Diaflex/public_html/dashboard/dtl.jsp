<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page errorPage="../error_page.jsp" %>
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import="java.util.ArrayList,java.util.HashMap, java.util.Date, java.util.ArrayList, java.util.Enumeration, ft.com.dao.*"%>
<jsp:useBean id="info" class="ft.com.InfoMgr" scope="session" />
<jsp:useBean id="util" class="ft.com.JspUtil" scope="page" />
 
<%@taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>
<%@taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>

<html>
  <head>
       <%
  ArrayList itemHdr = (ArrayList)request.getAttribute("itemHdrweb");
  ArrayList dtlsList = (ArrayList)request.getAttribute("dtlListweb");
  String hdrnme = (String)request.getAttribute("hdrnmeweb");
   String  webrfsh = (String)request.getAttribute("webrfsh");%>
    <title>Web Login Dtl </title>
    <script language="javaScript" type="text/javascript" src="<%=info.getReqUrl()%>/scripts/ajax.js?v=<%=info.getJsVersion()%>"></script>
    <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/bse.css?v=<%=info.getJsVersion()%>"/>
    <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/style.css?v=<%=info.getJsVersion()%>"/>
    <script src="<%=info.getReqUrl()%>/jquery/jquery.min.js"></script>
        <link rel="stylesheet" href="<%=info.getReqUrl()%>/css/jScrollbar.jquery.css" type="text/css" />
	<script src="<%=info.getReqUrl()%>/jquery/jquery.mousewheel.js"></script>
	<script src="<%=info.getReqUrl()%>/jquery/jquery-ui-draggable.js"></script>
        <script src="<%=info.getReqUrl()%>/jquery/jscroll.js"></script>
        <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/bse.js?v=<%=info.getJsVersion()%> " > </script>
 </head>
        <%String logId=String.valueOf(info.getLogId());
        String onfocus="cook('"+logId+"')";
        %>
 <body onfocus="<%=onfocus%>">
<table cellpadding="2" cellspacing="0" width="100%">
<tr><td valign="top"><b style="font-size:10px;"><%=hdrnme%>  </b></td></tr>
</table>
    <div id="main">
    <display:table name="sessionScope.userrightsform.pktList" requestURI=""   sort="list" class="boardgrid"  style="width:100%;" >
    <%for(int i=0;i<itemHdr.size();i++){
    String hdr = util.nvl((String)itemHdr.get(i));
    if(hdr.equals("Buyer")||hdr.equals("Sale EX")){%>
    <display:column property="<%=hdr%>"   style="text-align:left;"  title="<%=hdr%>"/>
    <%}else{
    %>
    <display:column property="<%=hdr%>"   style="text-align:left;"  title="<%=hdr%>"  />
    <%}}%>
    
    </display:table>
    </div>
    <!--
  <table class="grid1" >
  <thead>
  <tr>
  <%for(int i=0;i<itemHdr.size();i++){%>
  <th><%=itemHdr.get(i)%></th>
  <%}%>
  </tr></thead>
  <tbody>
  </tr>
  
  <%for(int i=0;i<dtlsList.size();i++){
  HashMap dtls = (HashMap)dtlsList.get(i);
  %>
  <tr>
  <%for(int j=0;j<itemHdr.size();j++){
  String key = (String)itemHdr.get(j);
  %>
  
  <td class="lft"><%=dtls.get(key)%></td>
  <%}%>
  </tr>
  <%}%>
  </tbody></table>-->
  
  </body>
</html>