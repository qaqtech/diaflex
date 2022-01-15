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
      <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/assortScript.js?v=<%=info.getJsVersion()%> " > </script>
      <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/bse.js?v=<%=info.getJsVersion()%> " > </script>
  </head>
        <%String logId=String.valueOf(info.getLogId());
        String onfocus="cook('"+logId+"')";
        %>
 <body onfocus="<%=onfocus%>">
  <%
   ArrayList stockList = (ArrayList)request.getAttribute("StockViewList");
   HashMap stockHisMap = (HashMap)request.getAttribute("StockHisList");
   ArrayList grpList = (ArrayList)request.getAttribute("issueList");
   String stt = util.nvl(request.getParameter("stt"));
   System.out.println("stuts"+stt);
   HashMap srchRcObList = (HashMap)session.getAttribute("srchReckObsMap");
   HashMap mprp = info.getMprp();
   HashMap prp = info.getPrp();
    if(stockList!=null && stockList.size() >0){
    HashMap stkUpd = null;
    String lprp =null;
    String flg = null;
    String mstkIdn = null;
  %>
  
  <table cellpadding="0" cellspacing="0">
 
  <tr><td>
  
  <div class="memo">
  
  
   <table border="0" width="400" class="Orange"  cellspacing="1" cellpadding="1" >
          
           <tr><th class="Orangeth">Check</th><th class="Orangeth">PROPERTY</th>
            <th class="Orangeth">Stock Value</th><th class="Orangeth">Issue Value</th><th class="Orangeth">Lab Value</th>
            <%for(int g=0;g < grpList.size();g++){
           String grp = (String)grpList.get(g);
           %>
           <th class="Orangeth">Lab Val <%=grp%></th>
           <%}%>
        
           </tr>
           
  <%
  int count=0;
  int srchSize = (stockList.size()/3)+1;
  for(int i=0;i < stockList.size() ;i++){
  stkUpd = (HashMap)stockList.get(i);
   lprp = (String)stkUpd.get("mprp");
   flg = (String)stkUpd.get("flg");
   
   mstkIdn = (String)stkUpd.get("mstkIdn");
   String stkVal = (String)stkUpd.get("stkVal");
   String issVal = (String)stkUpd.get("issVal");
   String rtnVal = (String)stkUpd.get("rtnVal");
   String lprpTyp = util.nvl((String)mprp.get(lprp+"T"));
    boolean isDisable = false;
   String cellBG = "";
     int issSrt = 0;
     int rtnSrt = 0;
     if(!lprp.equals("CERT")||!lprp.equals("LAB")){
  if(!(issVal.equals("")) && !(rtnVal.equals(""))){
  
   if(lprpTyp.equals("C")){
        String issValCmp = issVal;
        String rtnValCmp = rtnVal;
      if(issValCmp.indexOf("+")!=-1 || issValCmp.indexOf("-")!=-1){
              issValCmp = issValCmp.replaceAll("+","");
             issValCmp = issValCmp.replaceAll("-","");
     }
    if(rtnValCmp.indexOf("+")!=-1 || rtnValCmp.indexOf("-")!=-1){
             rtnValCmp = rtnValCmp.replaceAll("+","");
            rtnValCmp = rtnValCmp.replaceAll("-","");
    }
    ArrayList prpSrtLst = (ArrayList)prp.get(lprp+"S");
    ArrayList prpValLst =(ArrayList)prp.get(lprp+"V");
      if(prpValLst.indexOf(issVal)!=-1)
     issSrt  = Integer.parseInt((String)prpSrtLst.get(prpValLst.indexOf(issValCmp)));
       if(prpValLst.indexOf(rtnVal)!=-1)
     rtnSrt = Integer.parseInt((String)prpSrtLst.get(prpValLst.indexOf(rtnValCmp)));
     if(!issVal.equals("NA")){
   if(issSrt < rtnSrt){
      cellBG="background-color: Red;";
      isDisable =true;
      }
   if(issSrt > rtnSrt )
       cellBG ="background-color: Lime;";
     
   }}}}
   String str = util.nvl((String)srchRcObList.get(mstkIdn));
   String isChecked="";
   if(str.indexOf(lprp)!=-1)
   isChecked="checked=\"checked\"";
   %>

 <tr><td> 
 
 <input type="checkbox" id="<%=lprp%>" name="<%=lprp%>" value="<%=lprp%>" <%=isChecked%>  onclick="labResult('<%=mstkIdn%>','<%=lprp%>','<%=stt%>',this)"/>

 </td>
 <td><%=lprp%></td><td><%=stkVal%></td><td><%=issVal%></td><td style="<%=cellBG%>"><%=rtnVal%></td>
  <%for(int g=0;g < grpList.size();g++){
           String grp = (String)grpList.get(g);
           %>
           <td>  <%=util.nvl((String)stockHisMap.get(mstkIdn+"_"+lprp+"_1"))%></td>
    <%}%>
 
 </tr>

 
  <%}%>
  </table>

  </div>
  
   </td></tr>
  </table>
  
  
  <%}%>
 
  </body>
</html>