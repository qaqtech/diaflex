<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page errorPage="../error_page.jsp" %>
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import="java.util.ArrayList,java.util.HashMap,java.sql.ResultSet,ft.com.*, java.util.Date, java.util.ArrayList, ft.com.dao.*"%>
<jsp:useBean id="info" class="ft.com.InfoMgr" scope="session" />
<jsp:useBean id="util" class="ft.com.JspUtil" scope="page" />
<%@taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>
<%@taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<jsp:useBean id="gtMgr" class="ft.com.GtMgr" scope="session" />
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>Lab ReCheck</title>
    <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/bse.css?v=<%=info.getJsVersion()%>"/>
      <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/style.css?v=<%=info.getJsVersion()%>"/>
      <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/assortScript.js?v=<%=info.getJsVersion()%> " > </script>
      <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/ajax.js?v=<%=info.getJsVersion()%> " > </script>
      <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/bse.js?v=<%=info.getJsVersion()%> " > </script>
  </head>
        <%String logId=String.valueOf(info.getLogId());
        String onfocus="cook('"+logId+"')";
        %>
 <body onfocus="<%=onfocus%>">
  <%
   ArrayList stockList = (ArrayList)request.getAttribute("StockViewList");
   String stt = util.nvl(request.getParameter("stt"));
    HashMap srchRcObList = (HashMap)gtMgr.getValue("srchReckObsMap");
   HashMap mprp = info.getMprp();
   HashMap prp = info.getPrp();
    if(stockList!=null && stockList.size() >0){
    HashMap stkUpd = null;
    String lprp =null;
    String mstkIdn = null;
  %>
  
  <table cellpadding="0" cellspacing="0">
  <tr><td align="left"><span class="txtLink" > <%=request.getParameter("vnm")%></span></td></tr>
 
  <tr><td>
  
  <div class="memo">
  
  
  <table border="0" width="400" class="Orange"  cellspacing="1" cellpadding="1" >
          
           <tr>
           <th class="Orangeth">NONE</th>
           <th class="Orangeth">RC</th>
            <th class="Orangeth">OS</th>
           <th class="Orangeth">PROPERTY</th> <th class="Orangeth">Stock Value</th>        
           </tr>
           
  <%
  int count=0;
  int srchSize = (stockList.size()/3)+1;
  for(int i=0;i < stockList.size() ;i++){
  stkUpd = (HashMap)stockList.get(i);
   lprp = (String)stkUpd.get("mprp");
   String stkVal = (String)stkUpd.get("stkVal");
   mstkIdn = (String)stkUpd.get("mstkIdn"); 
   %>

 <tr>
 <td> 
 <input type="radio"  id="NONE_<%=lprp%>" name="<%=lprp%>" value="NONE <%=lprp%>" onclick="labRechk('<%=mstkIdn%>',this)"/>
</td>
 <td> 
 <input type="radio"  id="RC_<%=lprp%>" name="<%=lprp%>" value="Recheck <%=lprp%>" onclick="labRechk('<%=mstkIdn%>',this)"/>
</td>
<td> 
 <input type="radio"  id="RC_<%=lprp%>" name="<%=lprp%>" value="Observer <%=lprp%>" onclick="labRechk('<%=mstkIdn%>',this)"/>
</td>
 <td><%=lprp%></td><td><%=stkVal%></td>
 
 </tr>

 
  <%}%>
  </table>
  </div>
  
   </td></tr>
  </table>
  
  
  <%}%>
 
  </body>
</html>