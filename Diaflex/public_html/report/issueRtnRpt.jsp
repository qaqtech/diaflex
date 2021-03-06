<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page errorPage="../error_page.jsp" %>
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import="java.util.ArrayList,java.util.ArrayList,java.util.HashMap, java.sql.*, java.util.Enumeration, java.util.logging.Level"%>
<jsp:useBean id="info" class="ft.com.InfoMgr" scope="session" />
<jsp:useBean id="util" class="ft.com.JspUtil" scope="page" />
<%@taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
 
<html>
  <head>
 <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
  
  <title>Issue Return Report</title>
  <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/bse.js?v=<%=info.getJsVersion()%> " > </script>
 <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/bse.css?v=<%=info.getJsVersion()%>"/>
 <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/ajax.js?v=<%=info.getJsVersion()%> " > </script>
  <script language="javaScript" type="text/javascript" src="<%=info.getReqUrl()%>/calendar/calendar.js"></script>
   <link href="<%=info.getReqUrl()%>/calendar/calendar.css" rel="stylesheet" type="text/css">
    <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/style.css?v=<%=info.getJsVersion()%>"/>
  </head>
 
   <%
   ArrayList memoRtnLst = info.getMomoRtnLst();
        String logId=String.valueOf(info.getLogId());
        String onfocus="cook('"+logId+"')";
        String allow_valid_prc=util.nvl((String)info.getDmbsInfoLst().get("ALLOW_VALID_PRC"),"N");
        %>
 <body onfocus="<%=onfocus%>" onload="MM_preloadImages('images/faunalogo.png')" topmargin="0" leftmargin="0" rightmargin="0" bottommargin="0">
<table width="100%"  border="0" align="center" cellpadding="0" cellspacing="0" class="mainbg">
  <tr>
    <td height="103" valign="top">
   <jsp:include page="/header.jsp" />
    
    </td>
  </tr>
 
  <tr>
    <td height="5" valign="top" class="greyline"><img src="../images/grey_linebig.png" width="685" height="5" align="right" /></td>
  </tr>
  <tr>
  <td valign="top" class="hedPg">
  <table cellpadding="1" cellspacing="5"><tr><td valign="middle">
  <img src="../images/line.jpg" border="0" /></td><td valign="middle"> <span class="pgHed">
 Issue Return Report</span> </td>
</tr></table>
  </td>
  </tr>
  <%
   String msg = (String)request.getAttribute("msg");
   if(msg!=null){%>
     <tr><td valign="top" class="tdLayout"><%=msg%></td></tr>
   <%}%>
  
   <tr><td valign="top" class="hedPg"  >
    <html:form action="/report/issueReturnRpt?method=fetch" method="post"  >
          <html:hidden property="value(allow_valid_prc)" styleId="allow_valid_prc" name="issueReturnRptForm" value="<%=allow_valid_prc%>" />
    <table>
   <tr>
   <td>Issue Id:</td><td> <html:text property="value(issueId)" styleId="issueId" name="issueReturnRptForm"  /></td>
   </tr>
   <tr>
   <td>Type:</td>
   <td>
             <html:select property="value(Typ)" styleId="Typ" name="issueReturnRptForm" >
             <html:option value="0" >--select--</html:option>
               <!-- <html:option value="IS">Issue and Return</html:option>-->
                <html:option value="RT">Return</html:option>
            </html:select>
            </td>
   </tr>
   <tr><td>Process :</td>
   <td>
   <html:select property="value(prcIdn)"  styleId="mprcIdn" name="issueReturnRptForm"  onchange="getvalidPrcEmp('mprcIdn')"    >
           <html:option value="0" >--select--</html:option>
            <html:optionsCollection property="mprcList" name="issueReturnRptForm" 
            label="prc" value="mprcId" />
    </html:select>
   </td></tr>
   <tr>
    <td>
    Employee : </td>
   <td>
   <html:select property="value(empIdn)"  styleId="empIdn" name="issueReturnRptForm"  >
           <html:option value="0" >--select--</html:option>
            <html:optionsCollection property="empList" name="issueReturnRptForm" 
            label="emp_nme" value="emp_idn" />
            </html:select>
   </td>
   </tr>
       <tr><td>Issue Date : </td>  
       <td><html:text property="value(issfrmdte)" styleId="issfrmdte" name="issueReturnRptForm" size="8" /><img src="<%=info.getReqUrl()%>/images/calender.png" onClick="setYears(2011, <%=info.getCurrentYear()%>);showCalender(this, 'issfrmdte');"> 
       To&nbsp; <html:text property="value(isstodte)" styleId="isstodte" name="issueReturnRptForm"  size="8"/> <img src="<%=info.getReqUrl()%>/images/calender.png" onClick="setYears(2011, <%=info.getCurrentYear()%>);showCalender(this, 'isstodte');"></td>
      </tr>
    <tr><td>Return Date : </td>  
       <td><html:text property="value(rtnfrmdte)" styleId="rtnfrmdte" name="issueReturnRptForm" size="8" /><img src="<%=info.getReqUrl()%>/images/calender.png" onClick="setYears(2011, <%=info.getCurrentYear()%>);showCalender(this, 'rtnfrmdte');"> 
       To&nbsp; <html:text property="value(rtntodte)" styleId="rtntodte" name="issueReturnRptForm"  size="8"/> <img src="<%=info.getReqUrl()%>/images/calender.png" onClick="setYears(2011, <%=info.getCurrentYear()%>);showCalender(this, 'rtntodte');"></td>
      </tr>
   <tr>
   <td>Packet:</td><td> <html:textarea property="value(vnmLst)" styleId="vnm" rows="5"  cols="20" name="issueReturnRptForm"  /></td>
   </tr>
   <tr><td colspan="2">
    <jsp:include page="/genericSrch.jsp"/>
   </td></tr>
    <tr><td colspan="2" align="center"><html:submit property="value(srch)" value="Fetch" styleClass="submit" /></td> </tr>
  
   </table>
 </html:form>
   </td></tr>
   <%
   String view  = (String)request.getAttribute("view");
   if(view!=null){
   ArrayList pktList = (ArrayList)session.getAttribute("pktList");
   ArrayList itemHdr = (ArrayList)session.getAttribute("itemHdr");
   if(pktList!=null && pktList.size()>0){
   HashMap pktPrpMap = new HashMap();
   %>
   <tr><td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Create Excel <img src="../images/ico_file_excel.png" onclick="goTo('issueReturnRpt.do?method=createXL','','')" border="0"/> 
  <%
  ArrayList rolenmLst=(ArrayList)info.getRoleLst();
  String usrFlg=util.nvl((String)info.getUsrFlg());
  if(usrFlg.equals("SYS") || rolenmLst.contains("ADMIN")){
  %>
  <img src="../images/edit.jpg" title="Edit Properties Module" onclick="newWindow('<%=info.getReqUrl()%>/dynamicProperty.do?method=load&mdl=ISSUE_RTN_RPT&sname=ISSUE_RTN_RPT&par=V')" border="0" width="15px" height="15px"/>
  <%}%>
   </td></tr>
   <tr>
   <td valign="top" class="tdLayout">
   <table  class="grid1" align="left" id="dataTable">
        <tr>
        <th>Sr No.</th>
        <%for(int i=0;i<itemHdr.size();i++){%>
        <th><%=itemHdr.get(i)%></th>
        <%}%>
        </tr>
        <%for(int j=0;j<pktList.size();j++){
        pktPrpMap=(HashMap)pktList.get(j);%>
        <tr>
        <td><%=(j+1)%></td>
        <%
        for(int i=0;i<itemHdr.size();i++){%>
        <td nowrap="nowrap"><%=pktPrpMap.get(itemHdr.get(i))%></td>
        <%}%>
        </tr>
        <%}%>
    </table>
    </td> </tr>
    <%} else{%>
    <tr><td valign="top" class="hedPg">
   Sorry No Result Found
   </td></tr>
   <%}}%>




  <tr>
    <td><jsp:include page="/footer.jsp" /> </td>
    </tr>
  </table>
  
  </body>
  <%@include file="../calendar.jsp"%>
</html>