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
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>Lab Result View</title>
    <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/bse.css?v=<%=info.getJsVersion()%>"/>
      <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/style.css?v=<%=info.getJsVersion()%>"/>
      <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/bse.js?v=<%=info.getJsVersion()%> " > </script>
      <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/assortScript.js?v=<%=info.getJsVersion()%> " > </script>
      </head>
        <%String logId=String.valueOf(info.getLogId());
        String onfocus="cook('"+logId+"')";
        %>
 <body onfocus="<%=onfocus%>">
  
  <%
   ArrayList issueList = (ArrayList)request.getAttribute("issueList");
   ArrayList prpList = (ArrayList)request.getAttribute("prpList");
   HashMap assortHisMap = (HashMap)request.getAttribute("assortHisMap");
   HashMap stkDtlMap = (HashMap)request.getAttribute("stkDtlMap");
   HashMap mprp = info.getMprp();
   HashMap prp = info.getPrp();
  %>
   <div class="memo">
  
  
  <table border="0" width="400" class="Orange"  cellspacing="1" cellpadding="1" >
     <tr><th class="Orangeth">Process</th><th class="Orangeth">Employee</th><th class="Orangeth">Date</th>
     <%for(int i=0 ; i < prpList.size();i++){
     String lprp = (String)prpList.get(i);
     %>
     <th class="Orangeth"><%=lprp%> </th>
     <%}%>
     </tr>
      <%for(int i=0 ; i < issueList.size();i++){
     String issueId = (String)issueList.get(i);
     
     %>
     <tr> <td><%=assortHisMap.get(issueId+"_PRC")%></td><td><%=assortHisMap.get(issueId+"_EMP")%></td>
     <td><%=assortHisMap.get(issueId+"_DTE")%></td>
     <%for(int j=0 ; j < prpList.size();j++){
     
     String lprp = (String)prpList.get(j);
     String asrVal = util.nvl((String)assortHisMap.get(issueId+"_"+lprp));
     String stkVal = util.nvl((String)stkDtlMap.get(lprp));
      String lprpTyp = util.nvl((String)mprp.get(lprp+"T"));
       String cellBG="";
     if(lprpTyp.equals("C") && (!asrVal.equals("NA") && !asrVal.equals(""))){
        int issSrt = 0;
        int rtnSrt = 0;
        String issValCmp = asrVal;
        String rtnValCmp = stkVal;
        issValCmp = issValCmp.replace('+',' ');
        issValCmp = issValCmp.replace('+',' ');
        issValCmp = issValCmp.replace('-',' ');
        issValCmp = issValCmp.replace('-',' ');
        issValCmp = issValCmp.trim();
     
        rtnValCmp = rtnValCmp.replace('+',' ');
        rtnValCmp = rtnValCmp.replace('+',' ');
        rtnValCmp = rtnValCmp.replace('-',' ');
        rtnValCmp = rtnValCmp.replace('-',' ');
        rtnValCmp = rtnValCmp.trim();
    
    ArrayList prpSrtLst = (ArrayList)prp.get(lprp+"S");
    ArrayList prpValLst =(ArrayList)prp.get(lprp+"V");
      if(prpValLst.indexOf(issValCmp)!=-1)
     issSrt  = Integer.parseInt((String)prpSrtLst.get(prpValLst.indexOf(issValCmp)));
       if(prpValLst.indexOf(rtnValCmp)!=-1)
     rtnSrt = Integer.parseInt((String)prpSrtLst.get(prpValLst.indexOf(rtnValCmp)));
    
    
   if(issSrt < rtnSrt)
      cellBG="background-color: Red;";
      
      
   if(issSrt > rtnSrt )
       cellBG ="background-color: Lime;";
     }
     %>
     <td  style="<%=cellBG%>"><%=asrVal%></td>
    <%}%></tr>
     <%}%>
    </table></div>
  
  </body>
</html>