<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page errorPage="../error_page.jsp" %>
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import="java.util.ArrayList,java.util.HashMap,java.sql.ResultSet,ft.com.*, java.util.Date, java.util.List, ft.com.dao.*"%>
<jsp:useBean id="util" class="ft.com.JspUtil" scope="page" />
<jsp:useBean id="info" class="ft.com.InfoMgr" scope="session" />
<%@taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>
<%@taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>

<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title></title>
    <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/bse.css?v=<%=info.getJsVersion()%>"/>
      <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/style.css?v=<%=info.getJsVersion()%>"/>
      <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/bse.js?v=<%=info.getJsVersion()%> " > </script>
          <script type="text/javascript" src="<%=info.getReqUrl()%>/PopScripts/popup.js?v=<%=info.getJsVersion()%> " > </script>
      <script src="<%=info.getReqUrl()%>/PopScripts/jquery-1.2.6.min.js" type="text/javascript"></script>

      
  </head>
   <%
    //util.updAccessLog(request,response,"Assort Return", "Assort Return");
   String logId=String.valueOf(info.getLogId());
   String onfocus="cook('"+logId+"')";
   String noFd = request.getParameter("COUNT");
      String stt = request.getParameter("stt");

   String vwTMdl = (String)info.getValue("VWTMDL");

   if(noFd==null)
      noFd="4";
   ArrayList vwPrpLst = (ArrayList)session.getAttribute(vwTMdl);
   HashMap paramMap = (HashMap)session.getAttribute("ParamsMap");
    HashMap mprp = info.getMprp();
    HashMap   prp = info.getPrp();
    String msgLst = (String)request.getAttribute("msg");
      String isVerify="";
    %>
 <body onfocus="<%=onfocus%>" >
 <table cellpadding="4" cellspacing="4">
<%if(msgLst!=null){

%>
<tr><td><span class="redLabel"><%=msgLst%></span> </td></tr>
<%}%>
<html:form action="mix/transferPktAction.do?method=CreatePkt" method="post" >
  <input type="hidden" value="<%=noFd%>" name="COUNT" id="COUNT" />
    <input type="hidden" id="reqUrl" name="reqUrl" value="<%=info.getReqUrl()%>"/>
      <input type="hidden" value="<%=stt%>" name="stt" id="stt" />


<tr>
<td>   <input type="submit" value="Create" class="submit" name="create" />

</td>
</tr>
<tr><td>
 <table cellpadding="2" cellspacing="2">
 
 <tr><td>
   <div class="memo">
 <table class="Orange" cellspacing="1" cellpadding="1">
 
  <%
   if(!noFd.equals("")){
   int noFdInt = Integer.parseInt(noFd);
   %><tr>
  <% for(int j=0;j<vwPrpLst.size();j++){
  String lprp =(String)vwPrpLst.get(j);
    String lprpD = (String)mprp.get(lprp+"D");

  %>
  <th class="Orangeth"><%=lprpD%></th>
 <% }%>
   </tr>
  <% for(int i=0;i<noFdInt;i++){

     %>
  <tr>
  <%for(int j=0;j<vwPrpLst.size();j++){
  String lprp =(String)vwPrpLst.get(j);
  String ltyp = (String)mprp.get(lprp+"T");
     String fldVal="value("+lprp+"_"+i+")";

  if(ltyp.equals("C")){
    ArrayList srtList = (ArrayList)prp.get(lprp+"S");
    ArrayList prpList = (ArrayList)prp.get(lprp+"P");
      List prpLst=prpList;
if(paramMap!=null){
   String fldFm = util.nvl((String)paramMap.get(lprp+"_1"));
   String fldTo = util.nvl((String)paramMap.get(lprp+"_2"));
   if(!fldFm.equals("") && !fldTo.equals("")){
    int fmIndex = srtList.indexOf(fldFm);
   int toIndex = srtList.indexOf(fldTo);
    prpLst=prpList.subList(fmIndex,toIndex+1);
   }}
   %>
  <td>
  <html:select property="<%=fldVal%>"  >
   <%for(int k=0;k<prpLst.size();k++){
    String prpVal=(String)prpLst.get(k);

    %>
    <html:option value="<%=prpVal%>" ><%=prpVal%></html:option>
    <%}%>  
  
  </html:select>
  
  </td>
  <%}else{%>
  <td>
  <%if(lprp.equals("RTE")|| lprp.equals("CRTWT")){%>
  <html:text property="<%=fldVal%>"  readonly="true" value="0" />
  <%}else{%>
    <html:text property="<%=fldVal%>"   />

  <%}%>
  
  </td>
  <%}%>
  <%}%>
  </tr>
  <%}}else{%>
 <tr><td> Please give no. of packets need to create.</td></tr>
  <%}%>
  </table></div></td></tr></table></td></tr>
  </html:form>
  </table>
 </body>
</html>