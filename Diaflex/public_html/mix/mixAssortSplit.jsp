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
    <title>assortRtnUpd</title>
    <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/bse.css?v=<%=info.getJsVersion()%>"/>
      <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/style.css?v=<%=info.getJsVersion()%>"/>
      <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/assortScript.js?v=<%=info.getJsVersion()%> " > </script>
      <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/bse.js?v=<%=info.getJsVersion()%> " > </script>
        <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/ajax.js?v=<%=info.getJsVersion()%> " > </script>
          <script type="text/javascript" src="<%=info.getReqUrl()%>/PopScripts/popup.js?v=<%=info.getJsVersion()%> " > </script>
       <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/pri.js?v=<%=info.getJsVersion()%> " > </script>
   <script language="javaScript" type="text/javascript" src="<%=info.getReqUrl()%>/calendar/calendarFmt.js"></script>
   <link href="<%=info.getReqUrl()%>/calendar/calendar.css" rel="stylesheet" type="text/css">
      <script src="<%=info.getReqUrl()%>/PopScripts/jquery-1.2.6.min.js" type="text/javascript"></script>

      
  </head>
   <%
    //util.updAccessLog(request,response,"Assort Return", "Assort Return");
   String logId=String.valueOf(info.getLogId());
   String onfocus="cook('"+logId+"')";
   String noFd = util.nvl2(request.getParameter("COUNT"),"4");
   if(noFd==null)
      noFd="4";
      
      int noFdInt = Integer.parseInt(noFd);
   ArrayList vwPrpLst = (ArrayList)session.getAttribute("SPLIT_VIEW");
    HashMap mprp = info.getMprp();
    HashMap   prp = info.getPrp();
    String stkIdn = request.getParameter("mstkIdn");
       String issId = request.getParameter("issIdn");
       String ctsval = request.getParameter("ttlcts");

       ArrayList msgLst = (ArrayList)request.getAttribute("msgLst");
       ArrayList SplitStoneList = (ArrayList)request.getAttribute("SplitStoneList");
       if(SplitStoneList==null)
         SplitStoneList = new ArrayList();
         int lstSize = SplitStoneList.size();
         if(lstSize>noFdInt)
         noFdInt = lstSize;
      String isVerify="";
    %>
 <body onfocus="<%=onfocus%>" >
 <table cellpadding="4" cellspacing="4">
<%if(msgLst!=null && msgLst.size()>0){
isVerify = util.nvl((String)msgLst.get(0));
%>
<tr><td><span class="redLabel"><%=util.nvl((String)msgLst.get(1))%></span> </td></tr>
<%}%>
<html:form action="mix/mixAssortRtnAction.do?method=Verify" method="post" >
  <input type="hidden" value="<%=noFd%>" name="COUNT" id="COUNT" />
  <input type="hidden" value="<%=stkIdn%>" name="mstkIdn" id="mstkIdn" />
  <input type="hidden" value="<%=issId%>" name="issIdn" id="issIdn" />
    <input type="hidden" value="<%=ctsval%>" name="ttlcts" id="ttlcts" />
    <input type="hidden" id="reqUrl" name="reqUrl" value="<%=info.getReqUrl()%>"/>


<tr><td>
 <table cellpadding="2" cellspacing="2">
 <tr><td> 
 <%if(!isVerify.equals("YES")){%>
 <input type="submit" value="Verify" class="submit" name="verify" />
 <%}%>
 </td> <td> 

 <%if(isVerify.equals("YES")){%>
   <input type="button" value="Lock" name="lock" onclick="LOCKPKT('<%=stkIdn%>','<%=issId%>')" class="submit" /> 

 <%}%>
 </td><td>Total Carat :<b> <%=ctsval%></b></td>
</tr></table>
</td></tr>
<tr><td>
   <div class="memo">
 <table class="Orange" cellspacing="1" cellpadding="1">
 
  <%
   if(!noFd.equals("")){
   
   %><tr>
  <% for(int j=0;j<vwPrpLst.size();j++){
  String lprp =(String)vwPrpLst.get(j);
    String lprpD = (String)mprp.get(lprp+"D");

  %>
  <th class="Orangeth"><%=lprpD%></th>
 <% }%>
   </tr>
  <% for(int i=0;i<noFdInt;i++){

      HashMap stoneDtl = new HashMap();
     if(lstSize > i)
      stoneDtl= (HashMap)SplitStoneList.get(i);
      String cts = util.nvl((String)stoneDtl.get("cts"));
      String stk_idn = util.nvl((String)stoneDtl.get("stk_idn"));
  %>
  <tr><input type="hidden" name="IDN_<%=i%>" value="<%=stk_idn%>"/>
  <%for(int j=0;j<vwPrpLst.size();j++){
  String lprp =(String)vwPrpLst.get(j);
  String ltyp = (String)mprp.get(lprp+"T");
  String lval = util.nvl((String)stoneDtl.get(lprp));
  if(ltyp.equals("C")){
  ArrayList prpList = (ArrayList)prp.get(lprp+"V");
  ArrayList prpSrtLst = (ArrayList)prp.get(lprp+"S");
  %>
  <td>
  <select id="<%=lprp%>_<%=i%>"  name="<%=lprp%>_<%=i%>">
    <%for(int k=0;k<prpList.size();k++){
    String prpVal=(String)prpList.get(k);
    String prpSrt=(String)prpSrtLst.get(k);
    String selected="";
    if(prpSrt.equals(lval))
    selected="selected=\"selected\"";
    %>
    <option value="<%=prpVal%>" <%=selected%> ><%=prpVal%></option>
    <%}%>  
  </select>
  
  
  </td>
  <%}else{%>
  <td><input type="text" name="<%=lprp%>_<%=i%>" id="<%=lprp%>_<%=i%>" value="<%=lval%>" /></td>
  <%}%>
  <%}%>
  </tr>
  <%}}else{%>
 <tr><td> Please give no. of packets need to create.</td></tr>
  <%}%>
  </table></div></td></tr>
  </html:form>
  </table>
 </body>
</html>