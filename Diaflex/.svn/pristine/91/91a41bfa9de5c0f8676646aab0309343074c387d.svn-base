<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page errorPage="../error_page.jsp" %>
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import="java.util.ArrayList,java.util.HashMap,java.sql.ResultSet,ft.com.*, java.util.Date, java.util.ArrayList, ft.com.dao.*"%>
<jsp:useBean id="info" class="ft.com.InfoMgr" scope="session" />
<jsp:useBean id="util" class="ft.com.JspUtil" scope="page" />
<%@taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>
<%@taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>Copy Mapping</title>
        <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/bse.css?v=<%=info.getJsVersion()%>"/>
    <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/style.css?v=<%=info.getJsVersion()%>"/>
    <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/ajax.js?v=<%=info.getJsVersion()%>" ></script>
    <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/bse.js?v=<%=info.getJsVersion()%>" ></script>
<script language="javaScript" type="text/javascript" src="<%=info.getReqUrl()%>/calendar/calendar.js"></script>
   <link href="<%=info.getReqUrl()%>/calendar/calendar.css" rel="stylesheet" type="text/css" />
  </head>
        <%String logId=String.valueOf(info.getLogId());
        String onfocus="cook('"+logId+"')";
        %>
 <body onfocus="<%=onfocus%>"  onkeypress="return disableEnterKey(event);" >
  <table width="100%"  border="0" align="center" cellpadding="0" cellspacing="0" >
   <%
   ArrayList trmslst = (ArrayList)request.getAttribute("trmslst");
   ArrayList usrIdLst = (ArrayList)session.getAttribute("usrIdLst");
   HashMap userDtlMap = (HashMap)request.getAttribute("userDtlMap");
   String msg = util.nvl((String)request.getAttribute("msg"));
   String valid = util.nvl((String)request.getAttribute("valid"));
   if(!msg.equals("")){%>
   <tr><td valign="top" class="hedPg"><span class="redLabel"><%=msg%></span></td></tr>
   <%}if(!valid.equals("")){%>
   <tr><td valign="top" class="hedPg"><span class="redLabel"><%=valid%></span></td></tr>
   <%}
   if((trmslst!=null && trmslst.size()>0) && (usrIdLst!=null && usrIdLst.size()>0)){%>
   <tr>
   <td valign="top" class="hedPg">
   <html:form action="/contact/nmerel.do?method=savewebusr" method="post" enctype="multipart/form-data">
   <html:hidden property="value(lNmeIdn)"  />
   <html:hidden property="value(oldlRelIdn)"  />
   <table cellpadding="5" cellspacing="2" class="grid1">
   <tr>
   <th></th>
   <th>Web User</th><th>Current Term</th><th>New Term</th>
   </tr>
   <%for(int i=0;i<usrIdLst.size();i++){
   String usrid=util.nvl((String)usrIdLst.get(i));
   HashMap usrsdtl=(HashMap)userDtlMap.get(usrid);
   String checkFldId =usrid;
   String trmsfld = "TRM_"+usrid;
   String checkFldVal = "value(" + usrid + ")" ;
   String trmprpFld = "value(" + trmsfld + ")" ; 
   %>
   <tr>
   <td><html:checkbox property="<%=checkFldVal%>" styleId="<%=checkFldId%>" name="nmeRelForm" value="yes" /></td>
   <td><%=util.nvl((String)usrsdtl.get("USR"))%></td>
   <td><%=util.nvl((String)usrsdtl.get("TRM"))%></td>
   <td>
   <html:select property="<%=trmprpFld%>" styleId="<%=trmsfld%>" name="nmeRelForm" >
   <%for(int j=0;j<trmslst.size();j++){
   ArrayList trms=(ArrayList)trmslst.get(j);
   String fval=util.nvl((String)trms.get(0));
   String pval=util.nvl((String)trms.get(1));
   %>
   <html:option value="<%=fval%>"><%=pval%></html:option>
   <%}%>
   </html:select>
   </td>
   </tr>
   <%}%>
  <tr><td colspan="2" align="left"> <html:submit property="value(submit)" styleClass="submit" value="Update Terms" /> </td> </tr>
  </table>
   </html:form>
   </td>
   </tr>
   <%}%>
   </table>
  </body>
</html>