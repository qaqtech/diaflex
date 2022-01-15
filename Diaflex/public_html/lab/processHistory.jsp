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
   HashMap processHisMap = (HashMap)request.getAttribute("ProcessHisMap");
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
     <tr> <td><%=processHisMap.get(issueId+"_PRC")%></td><td><%=processHisMap.get(issueId+"_EMP")%></td>
     <td><%=processHisMap.get(issueId+"_DTE")%></td>
     <%for(int j=0 ; j < prpList.size();j++){
     
     String lprp = (String)prpList.get(j);
     String rtnVal = util.nvl((String)processHisMap.get(issueId+"_"+lprp+"RTN"));
     String issVal = util.nvl((String)processHisMap.get(issueId+"_"+lprp+"ISS"));
      String lprpTyp = util.nvl((String)mprp.get(lprp+"T"));
       String cellBG="";
        String disVal=rtnVal;
     if(lprpTyp.equals("C") && (!rtnVal.equals("NA") && !rtnVal.equals(""))){
        int issSrt = 0;
        int rtnSrt = 0;
        String issValCmp = issVal;
        String rtnValCmp = rtnVal;
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
    
   
   if(issSrt < rtnSrt){
      cellBG="background-color: Red;";
      disVal=issVal+"&nbsp;|&nbsp;"+rtnVal;
      }
      
      
   if(issSrt > rtnSrt ){
       cellBG ="background-color: Lime;";
         disVal=issVal+"&nbsp;|&nbsp;"+rtnVal;
       }
     }
     %>
     <td  style="<%=cellBG%>"><%=disVal%></td>
    <%}%></tr>
     <%}%>
    </table></div>
  
  </body>
</html>