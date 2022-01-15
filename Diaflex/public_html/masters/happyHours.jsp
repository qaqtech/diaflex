<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page errorPage="../error_page.jsp" %>
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import="java.util.ArrayList,java.util.HashMap,java.sql.ResultSet,ft.com.*, java.util.Date, java.util.ArrayList, ft.com.dao.*"%>
<jsp:useBean id="info" class="ft.com.InfoMgr" scope="session" />
<jsp:useBean id="util" class="ft.com.JspUtil" scope="page" />

<%@taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>
<html>
  <head>
  
  <%            HashMap dbinfo = info.getDmbsInfoLst();
         String cnt = (String)dbinfo.get("CNT");
         
         String ttl="Happy Hours";
         
         if(cnt.equals("kj")){
         ttl="Kapu Select";
         }%>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title><%=ttl%></title>
    <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/bse.css?v=<%=info.getJsVersion()%>"/>
    <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/style.css?v=<%=info.getJsVersion()%>"/>
    <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/bse.js?v=<%=info.getJsVersion()%> " > </script>
    <script language="javaScript" type="text/javascript" src="<%=info.getReqUrl()%>/calendar/calendar.js"></script>
    <link href="<%=info.getReqUrl()%>/calendar/calendar.css" rel="stylesheet" type="text/css" />

  </head>
        <%String logId=String.valueOf(info.getLogId());
        String onfocus="cook('"+logId+"')";
        %>
 <body onfocus="<%=onfocus%>" onload="MM_preloadImages('images/faunalogo.png')" topmargin="0" leftmargin="0" rightmargin="0" bottommargin="0">
<table width="100%"  border="0" align="center" cellpadding="0" cellspacing="0" class="mainbg">
  <tr>
    <td height="103" valign="top">
   <jsp:include page="/header.jsp" />
    
    </td>
  </tr>
 
  <tr>
    <td height="5" valign="top" class="greyline"><img src="<%=info.getReqUrl()%>/images/grey_linebig.png" width="685" height="5" align="right" /></td>
  </tr>
  <%
   ArrayList params = new ArrayList();
    ResultSet rs = null;

    String formName = "msgdtlform";
    DBUtil dbutil = new DBUtil();
    DBMgr db = new DBMgr(); 
    db.setCon(info.getCon());
    dbutil.setDb(db);
    dbutil.setInfo(info);
    String formAction = "/masters/happyhours.do?method=save";
    String msg=util.nvl((String)request.getAttribute("MSG"));
    ArrayList dspOrder = new ArrayList();
    ArrayList idnList=(ArrayList) session.getAttribute("idnList");
    dspOrder.add("NEW");
    if(idnList.size()>0)
    dspOrder.add("EXISTING");

 %>
 <tr>
  <td valign="top" class="hedPg">
  <table cellpadding="1" cellspacing="5"><tr><td valign="middle">
  <img src="../images/line.jpg" border="0" /></td><td valign="middle"> <span class="pgHed"><%=ttl%></span> </td>
</tr></table>
  </td>
  </tr>
  <%if(!msg.equals("")){%>
   <tr> <td valign="top" class="hedPg" style="color:red;font-size:16px;"><%=msg%></td></tr>
  <%}%>
    <tr><td class="tdLayout" valign="top">
  <html:form action="<%=formAction%>" method="post">
    <table class="grid1">
     <tr><th align="center" colspan="10">SET <%=ttl%> </th></tr>
    <tr>
    <th>sr.</th><th></th><th><span class="redTxt">*</span>ORDER DATE</th><th><span class="redTxt">*</span>ORDER TIME(HH24:MM:SS)</th><th>   <span class="redTxt">*</span>START DATE</th><th>   <span class="redTxt">*</span>START TIME(HH24:MM:SS)</th><th>   <span class="redTxt">*</span>END DATE</th><th><span class="redTxt">*</span>END TIME(HH24:MM:SS)</th><th>   <span class="redTxt">*</span>ISSUE DATE</th><th>   <span class="redTxt">*</span>STATUS</th>
    </tr>
    <%  for(int dsp=0; dsp < dspOrder.size(); dsp++) {
        int sr = 0 ;
          String msgvw = (String)dspOrder.get(dsp);
          int   fldsize =3;
          if(!msgvw.equals("NEW") && idnList!=null)
             fldsize =idnList.size();
      %>
         <tr><td colspan="10" class="ttl"><%=msgvw%></td></tr>  
         
   <% for(int i=0; i<fldsize; i++){
     String idn=String.valueOf(i);
     if(!msgvw.equals("NEW"))
        idn =(String)idnList.get(i);
     String    startdte  ="value(startdte_"+idn+")";
     String    starttm   ="value(starttm_"+idn+")";
     String    enddte    ="value(enddte_"+idn+")";
     String    endtm     ="value(endtm_"+idn+")";
     String    biddte    ="value(biddte_"+idn+")";
     String    bidtm       ="value(bidtm_"+idn+")";
     String    stt       ="value(stt_"+idn+")";
     String    issdt       ="value(issdt_"+idn+")";
   
   %>
    <tr>
      <td><%=++sr%></td>
      <td>&nbsp;
         <%
      if(!(msgvw.equalsIgnoreCase("NEW"))) {
         String cboxPrp = "value(upd_"+ idn + ")" ;
      %>
        <html:checkbox property="<%=cboxPrp%>"/>
      <%}%>
      <td>
        <div class="float"><html:text property="<%=biddte%>"  size="8" styleId="<%=biddte%>" /></div>
        <div class="float">     <a href="#"  onClick="setYears(1900, <%=info.getCurrentYear()%>);showCalender(this, '<%=biddte%>');">
         <img src="<%=info.getReqUrl()%>/images/calender.png"></a></div>
         </td>
          <td><html:text property="<%=bidtm%>" styleId="<%=bidtm%>" size="12"  /></td>
           <td>
             <div class="float">   <html:text property="<%=startdte%>" size="8" styleId="<%=startdte%>" /></div>
             <div class="float">     <a href="#"  onClick="setYears(1900, <%=info.getCurrentYear()%>);showCalender(this, '<%=startdte%>');">
             <img src="<%=info.getReqUrl()%>/images/calender.png"></a></div>
          </td>
          <td><html:text property="<%=starttm%>" styleId="<%=starttm%>" size="12"  /></td>
        <td>
            <div class="float">   <html:text property="<%=enddte%>" size="8" styleId="<%=enddte%>" /></div>
            <div class="float">     <a href="#"  onClick="setYears(1900, <%=info.getCurrentYear()%>);showCalender(this, '<%=enddte%>');">
             <img src="<%=info.getReqUrl()%>/images/calender.png"></a></div>
        </td>
        <td><html:text property="<%=endtm%>" styleId="<%=endtm%>" size="12"  /></td>
                <td>
             <div class="float">   <html:text property="<%=issdt%>" size="8" styleId="<%=issdt%>" /></div>
             <div class="float">    <a href="#"  onClick="setYears(1900, <%=info.getCurrentYear()%>);showCalender(this, '<%=issdt%>');">
             <img src="<%=info.getReqUrl()%>/images/calender.png"></a></div>
          </td>
         <td><html:select property="<%=stt%>" >
        <html:option value="">--Select--</html:option>
        <html:option value="A">A</html:option>
        <html:option value="IA">IA</html:option>
        </html:select>   
        </td>
        </tr>
       <%}if((msgvw.equalsIgnoreCase("NEW"))) {%>
   <tr><td colspan="10" align="center"> <html:submit property="addnew" value="Add New"  styleClass="submit"/></td></tr>
   <%}else{%>
      <tr><td colspan="10" align="center"><html:submit property="modify" value="Save Changes" onclick="return validateUpdate()" styleClass="submit"/>
          &nbsp;<html:submit property="delete" value="Delete" onclick="return confirmChangesMsg('Are you sure to delete: ?')" styleClass="submit"/>

      </td></tr>
  <%}}%>
    </table>  
    </html:form></td></tr>
    <tr>
    <td><jsp:include page="/footer.jsp" /> </td>
    </tr>
    </table>
 <%@include file="../calendar.jsp"%>
</body>
</html>