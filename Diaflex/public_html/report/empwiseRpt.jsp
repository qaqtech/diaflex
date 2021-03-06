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
  
  <title>Employee Wise Report</title>
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
 Employee Wise Report</span> </td>
</tr></table>
  </td>
  </tr>
  <%
   String msg = (String)request.getAttribute("msg");
   if(msg!=null){%>
     <tr><td valign="top" class="tdLayout"><%=msg%></td></tr>
   <%}%>
  
   <tr><td valign="top" class="hedPg"  >
    <html:form action="/report/empWiseReport?method=fetch" method="post"  >
    <table>
   <tr><td>Process :</td>
   <td>
   <html:select property="value(prcIdn)"  styleId="mprcIdn" name="empWiseForm"    >
           <html:option value="" >--select--</html:option>
            <html:optionsCollection property="mprcList" name="empWiseForm" 
            label="prc" value="mprcId" />
    </html:select>
   </td></tr>
       <tr><td>Employee :</td><td>
             <html:select property="empLst" name="empWiseForm" style="height:200px; width:180px;" styleId="empLst"  multiple="true" size="15">
             <html:optionsCollection property="empList" name="empWiseForm"  value="emp_idn" label="emp_nme" />
             </html:select></td>
    </tr>
       <tr><td>Issue Date : </td>  
       <td><html:text property="value(issfrmdte)" styleId="issfrmdte" name="empWiseForm" size="8" /><img src="<%=info.getReqUrl()%>/images/calender.png" onClick="setYears(2011, <%=info.getCurrentYear()%>);showCalender(this, 'issfrmdte');"> 
       To&nbsp; <html:text property="value(isstodte)" styleId="isstodte" name="empWiseForm"  size="8"/> <img src="<%=info.getReqUrl()%>/images/calender.png" onClick="setYears(2011, <%=info.getCurrentYear()%>);showCalender(this, 'isstodte');"></td>
      </tr>
    <tr><td>Return Date : </td>  
       <td><html:text property="value(rtnfrmdte)" styleId="rtnfrmdte" name="empWiseForm" size="8" /><img src="<%=info.getReqUrl()%>/images/calender.png" onClick="setYears(2011, <%=info.getCurrentYear()%>);showCalender(this, 'rtnfrmdte');"> 
       To&nbsp; <html:text property="value(rtntodte)" styleId="rtntodte" name="empWiseForm"  size="8"/> <img src="<%=info.getReqUrl()%>/images/calender.png" onClick="setYears(2011, <%=info.getCurrentYear()%>);showCalender(this, 'rtntodte');"></td>
      </tr>
    <tr><td colspan="2" align="center"><html:submit property="value(srch)" value="Fetch" styleClass="submit" /></td> </tr>
  
   </table>
 </html:form>
   </td></tr>
   <%
   String view  = util.nvl((String)request.getAttribute("view"));
   if(view.equals("Y")){
    HashMap allPageDtl= (info.getPageDetails() == null)?new HashMap():(HashMap)info.getPageDetails();
    HashMap pageDtl=(HashMap)allPageDtl.get("EMP_WISE_RPT");
    ArrayList pageList=new ArrayList();
    HashMap pageDtlMap=new HashMap();
    String fld_nme="",fld_ttl="",val_cond="",dflt_val="",fld_typ="",form_nme="",flg1="",groupby="DEPT";
    
          pageList= ((ArrayList)pageDtl.get("GROUPBY") == null)?new ArrayList():(ArrayList)pageDtl.get("GROUPBY");             
         if(pageList!=null && pageList.size() >0){
         for(int j=0;j<pageList.size();j++){
         pageDtlMap=(HashMap)pageList.get(j);
         groupby=(String)pageDtlMap.get("dflt_val");
         }
         }
    HashMap dataDtl = (HashMap)request.getAttribute("dataDtl");
   ArrayList groupbyLst = (dataDtl.get(groupby) == null)?new ArrayList():(ArrayList)dataDtl.get(groupby);
   if(groupbyLst!=null && groupbyLst.size()>0){
   for(int i=0;i<groupbyLst.size();i++){
   String dept=util.nvl((String)groupbyLst.get(i));
   ArrayList empgroupbyLst = (dataDtl.get(dept+"_EMP") == null)?new ArrayList():(ArrayList)dataDtl.get(dept+"_EMP");
   ArrayList prcLst = (dataDtl.get(dept+"_PRC") == null)?new ArrayList():(ArrayList)dataDtl.get(dept+"_PRC");
   int empgroupbyLstsz=empgroupbyLst.size();
   int prcLstsz=prcLst.size();
   %>
    <tr valign="top" class="tdLayout">
    <td valign="top" class="tdLayout">
    <table  class="grid1" align="left">
    <tr><th align="center" colspan="<%=(empgroupbyLstsz*3)+4%>"><%=dept%></th></tr>
    <tr>
    <td></td>
    <%for(int h=0;h<empgroupbyLstsz;h++){%>
    <td align="center" colspan="3"><%=util.nvl((String)empgroupbyLst.get(h))%></td>
    <%}%>
    <td align="center" colspan="3">TOTAL</td>
    </tr>
    <tr>
    <td></td>
    <%for(int h=0;h<empgroupbyLstsz;h++){%>
    <td align="center">QTY</td>
    <td align="center">CTS</td>
    <td align="center">VLU</td>
    <%}%>
    <td align="center">QTY</td>
    <td align="center">CTS</td>
    <td align="center">VLU</td>
    </tr>
    <%for(int p=0;p<prcLstsz;p++){
    String prc=util.nvl((String)prcLst.get(p));%>
    <tr>
    <th align="center"><%=prc%></th>
    <%for(int e=0;e<empgroupbyLstsz;e++){
    String emp=util.nvl((String)empgroupbyLst.get(e));
    %>
    <td  align="right"><%=util.nvl((String)dataDtl.get(dept+"_"+prc+"_"+emp+"_QTY"))%></td>
    <td align="right"><%=util.nvl((String)dataDtl.get(dept+"_"+prc+"_"+emp+"_CTS"))%></td>
        <td align="right"><%=util.nvl((String)dataDtl.get(dept+"_"+prc+"_"+emp+"_VLU"))%></td>
    <%}%>
    <td align="right"><b><%=util.nvl((String)dataDtl.get(dept+"_"+prc+"_QTY"))%></b></td>
    <td align="right"><b><%=util.nvl((String)dataDtl.get(dept+"_"+prc+"_CTS"))%></b></td>
        <td align="right"><b><%=util.nvl((String)dataDtl.get(dept+"_"+prc+"_VLU"))%></b></td>
    </tr>
    <%}%>
    <tr>
    <th align="center">TOTAL</th>
    <%for(int e=0;e<empgroupbyLstsz;e++){
    String emp=util.nvl((String)empgroupbyLst.get(e));
    %>
    <td align="right"><b><%=util.nvl((String)dataDtl.get(dept+"_"+emp+"_QTY"))%></b></td>
    <td align="right"><b><%=util.nvl((String)dataDtl.get(dept+"_"+emp+"_CTS"))%></b></td>
        <td align="right"><b><%=util.nvl((String)dataDtl.get(dept+"_"+emp+"_VLU"))%></b></td>
    <%}%>
    <td align="right"><b><%=util.nvl((String)dataDtl.get(dept+"_QTY"))%></b></td>
    <td align="right"><b><%=util.nvl((String)dataDtl.get(dept+"_CTS"))%></b></td>
        <td align="right"><b><%=util.nvl((String)dataDtl.get(dept+"_VLU"))%></b></td>
    </tr>
    </table>
    </td>
    </tr>
    <tr></tr>
    <%}%>
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