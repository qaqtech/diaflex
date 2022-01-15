<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page errorPage="../error_page.jsp" %>
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import="java.util.ArrayList,java.util.HashMap,java.sql.ResultSet,ft.com.*, java.util.Date, java.util.ArrayList, ft.com.dao.*"%>
<jsp:useBean id="info" class="ft.com.InfoMgr" scope="session" />
<jsp:useBean id="util" class="ft.com.JspUtil" scope="page" />
<jsp:useBean id="gtMgr" class="ft.com.GtMgr" scope="session" />

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
   ArrayList issueList = (ArrayList)request.getAttribute("issueList");
   String stt = util.nvl(request.getParameter("stt"));

   HashMap srchRcObList = (HashMap)gtMgr.getValue("srchReckObsMap");
    HashMap srchList = (HashMap)gtMgr.getValue("srchRIMap");
   HashMap mprp = info.getMprp();
   HashMap prp = info.getPrp();
   HashMap dbinfo = info.getDmbsInfoLst();
   String cnt = (String)dbinfo.get("CNT");
    if(stockList!=null && stockList.size() >0){
    HashMap stkUpd = null;
    String lprp =null;
    String flg = null;
    String mstkIdn =(String)request.getAttribute("mstkIdn");
    String lab = util.nvl((String)request.getAttribute("lab"),"GIA");
  %>
  
  <table cellpadding="0" cellspacing="0">
  <tr><td align="left"><span class="txtLink" > <%=request.getParameter("vnm")%></span></td></tr>
 <tr><td align="left"> 
 <table><tr>
 <td><div class="Maroon">&nbsp;</div></td><td>Recheck</td>
 <td><div class="Blue">&nbsp;</div></td><td>Observation</td>
 <td><div class="gray">&nbsp;</div></td><td>Discussion</td>
 <td><span class="txtLink" onclick="DisplayProssDIV('labResult.do?method=ProcessHis&mstkIdn=<%=mstkIdn%>')"> <label id="prcLbl" class="img">Process History</label> </span> </td>

 </tr>
 </table>
 </td>
 
 </tr>
  <tr><td>
  <div id="hisDiv" style="display:none">
   <table>
   <tr><td><B>Assort History</b></td></tr>
   <tr><td>
   <iframe name="ASRT" class="frameStyle" align="middle" width="1200" height="100" frameborder="0">

   </iframe>
   </td></tr></table></div>
    <div id="prcDiv" style="display:none">
   <table>
   <tr><td><B>Process History</b> <span class="redLabel">IF grading change then only showing Issue value.</span> </td></tr>
   <tr><td>
   <iframe name="PRC" class="frameStyle" align="middle" width="1100" height="200" frameborder="0">

   </iframe>
   </td></tr></table></div>
   </td></tr>
  <tr><td>
  <table><tr><td>
  <div class="memo">
  
  
  <table border="0" width="150" class="Orange"  cellspacing="1" cellpadding="1" >
          
           <tr><th class="Orangeth">None</th><th class="Orangeth">RC</th><th class="Orangeth">OB</th><th class="Orangeth">DIS</th>
           <th class="Orangeth">PROPERTY</th><th class="Orangeth">Issue </th><th class="Orangeth">Result</th>
            <%for(int g=0;g < issueList.size();g++){
           String issue = (String)issueList.get(g);
           %>
           <th class="Orangeth"><%=util.nvl((String)stockHisMap.get(issue+"_HDR"))%>(<%=util.nvl((String)stockHisMap.get(issue+"_DTE"))%>)</th>
           <%}%>
        
           </tr>
           <tr><td colspan="4"></td><td><b> Issue Date</b></td><td colspan="2"></td>
            <%for(int g=0;g < issueList.size();g++){
           String issue = (String)issueList.get(g);
           %>
           <td><%=util.nvl((String)stockHisMap.get(issue+"_ISSDTE"))%></td>
           <%}%>
           </tr>
             <tr><td colspan="4"></td><td> <b>Upload Date</b></td>
             <td><%=request.getAttribute("iss_dt")%></td>
            <td><%=request.getAttribute("rtn_dt")%></td>
            <%for(int g=0;g < issueList.size();g++){
           String issue = (String)issueList.get(g);
           %>
           <td><%=util.nvl((String)stockHisMap.get(issue+"_RTNDTE"))%></td>
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
   String issVal = (String)stkUpd.get("issVal");
   String rtnVal = (String)stkUpd.get("rtnVal");
   String stkVal = (String)stkUpd.get("stkVal");
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
      if(issValCmp.indexOf("-")==issValCmp.length()-1 || issValCmp.indexOf("+")==issValCmp.length()-1 ){
        issValCmp = issValCmp.replace('+',' ');
        issValCmp = issValCmp.replace('+',' ');
        issValCmp = issValCmp.replace('-',' ');
        issValCmp = issValCmp.replace('-',' ');
        issValCmp = issValCmp.trim();
      }
       if(rtnValCmp.indexOf("-")==rtnValCmp.length()-1 || rtnValCmp.indexOf("+")==rtnValCmp.length()-1){
        rtnValCmp = rtnValCmp.replace('+',' ');
        rtnValCmp = rtnValCmp.replace('+',' ');
        rtnValCmp = rtnValCmp.replace('-',' ');
        rtnValCmp = rtnValCmp.replace('-',' ');
        rtnValCmp = rtnValCmp.trim();
    }
    ArrayList prpSrtLst = (ArrayList)prp.get(lprp+"S");
    ArrayList prpValLst =(ArrayList)prp.get(lprp+"V");
      if(prpValLst.indexOf(issValCmp)!=-1)
     issSrt  = Integer.parseInt((String)prpSrtLst.get(prpValLst.indexOf(issValCmp)));
       if(prpValLst.indexOf(rtnValCmp)!=-1)
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
   String isOBDisbled = "";
  String isRCDisbled = "";
   String isDISDisbled = "";
   String style = "";
   if(str.indexOf("Recheck "+lprp+"")!=-1){
    isRCDisbled = "disabled=\"disabled\"";
    isOBDisbled = "";
    isDISDisbled = "disabled=\"disabled\"";
    style = "background-color: Maroon;color:white";
   }else if(str.indexOf("Observer "+lprp+"")!=-1){
     isRCDisbled = "disabled=\"disabled\"";
    isOBDisbled = "disabled=\"disabled\"";
    isDISDisbled = "";
    style = "background-color: Blue;color:white";
   }else if(str.indexOf("DIS "+lprp+"")!=-1){
    isRCDisbled = "disabled=\"disabled\"";
    isOBDisbled = "disabled=\"disabled\"";
    isDISDisbled = "disabled=\"disabled\"";
     style = "background-color: rgb(165,198,255);color:white";
    }else{
     isRCDisbled = "";
    isOBDisbled = "disabled=\"disabled\"";
    isDISDisbled = "disabled=\"disabled\"";
    }
    String rcchecked="";
    String obchecked="";
    String dischecked="";
    
    if(srchList!=null && srchList.size()>0){
    String checkStr = util.nvl((String)srchList.get(mstkIdn));
     if(checkStr.indexOf("Recheck "+lprp+"")!=-1){
    rcchecked="checked=\"checked\"";
   }else if(checkStr.indexOf("Observer "+lprp+"")!=-1){
     obchecked="checked=\"checked\"";
   }else if(checkStr.indexOf("DIS "+lprp+"")!=-1){
    dischecked="checked=\"checked\"";
    }else{
     
    }
    }
    String onclickcert="";
    if(cnt.equals("hk"))
    onclickcert="labResultcertno('"+mstkIdn+"','"+lprp+"','Recheck "+lprp+"','"+lab+"')";
   %>

 <tr>
  <td> 
 <input type="radio"  id="NONE_<%=lprp%>" name="<%=lprp%>" value="NONE_<%=lprp%>"  onclick="labResult('<%=mstkIdn%>','<%=lprp%>','')"/>
</td>
<td nowrap="nowrap"> 
 <input type="radio"  id="RC_<%=lprp%>" name="<%=lprp%>" value="RC_<%=lprp%>"  <%=isRCDisbled%>  <%=rcchecked%>  onclick="labResult('<%=mstkIdn%>','<%=lprp%>','Recheck <%=lprp%>'); <%=onclickcert%>"/>
<select id="<%=mstkIdn%>_<%=lprp%>" style="display:none" onchange="labResultcertnosave('<%=mstkIdn%>','<%=lprp%>','Recheck <%=lprp%>')">
<option value="">Select</option>
</select>
</td>
<td> 
 <input type="radio"  id="OB_<%=lprp%>" name="<%=lprp%>" value="OB_<%=lprp%>" <%=isOBDisbled%> <%=obchecked%> onclick="labResult('<%=mstkIdn%>','<%=lprp%>','Observer <%=lprp%>')"/>
</td>
<td> 
 <input type="radio"  id="DIS_<%=lprp%>" name="<%=lprp%>" value="DIS_<%=lprp%>"  <%=isDISDisbled%> <%=dischecked%> onclick="labResult('<%=mstkIdn%>','<%=lprp%>','DIS <%=lprp%>')"/>
</td>
 
 <td style="<%=style%>" ><%=lprp%></td><td width="100px"><%=issVal%></td><td style="<%=cellBG%>" width="100px"><%=rtnVal%></td>
  <%for(int g=0;g < issueList.size();g++){
           String issue = (String)issueList.get(g);
           %>
           <td width="100px">  <%=util.nvl((String)stockHisMap.get(issue+"_"+lprp))%></td>
    <%}%>
 
 </tr>

 
  <%}%>
  </table>
  </div>
  </td></tr></table>
   </td></tr>
  
  </table>
  
  
  <%}%>
 
  </body>
</html>