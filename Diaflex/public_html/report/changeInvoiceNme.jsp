<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page errorPage="../error_page.jsp" %>
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import="ft.com.*,java.util.ArrayList,java.util.ArrayList, java.util.Iterator,java.util.List,java.io.*, java.sql.Date,java.util.HashMap, ft.com.marketing.PacketLookupForm" %>

<jsp:useBean id="util" class="ft.com.JspUtil" scope="page" />
<jsp:useBean id="info" class="ft.com.InfoMgr" scope="session" />
<%@taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>
<%@taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<html>
  <head>
      <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/bse.js?v=<%=info.getJsVersion()%> " > </script>
<link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/bse.css?v=<%=info.getJsVersion()%>"/>
<link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/general.css?v=<%=info.getJsVersion()%>"/>
 <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/memoScript.js?v=<%=info.getJsVersion()%> " > </script>
<script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/ajax.js?v=<%=info.getJsVersion()%> " > </script>
  <script type="text/javascript" src="<%=info.getReqUrl()%>/PopScripts/popup.js?v=<%=info.getJsVersion()%> " > </script>
    <script src="<%=info.getReqUrl()%>/PopScripts/jquery-1.2.6.min.js" type="text/javascript"></script>
   <script language="javaScript" type="text/javascript" src="<%=info.getReqUrl()%>/calendar/calendar.js"></script>
   <link href="<%=info.getReqUrl()%>/calendar/calendar.css" rel="stylesheet" type="text/css" />

 
      <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/style.css?v=<%=info.getJsVersion()%>"/>
      <title>Daily Sales Report</title>
   </head>
        <%
        DBMgr db = new DBMgr();
        DBUtil dbutil = new DBUtil();
        db.setCon(info.getCon());
      dbutil.setDb(db);
      dbutil.setInfo(info);
      db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));db.setDbUsr(info.getDbUsr());
      dbutil.setLogApplNm(info.getLoApplNm());
        String logId=String.valueOf(info.getLogId());
        String onfocus="cook('"+logId+"')";
        String regnIdn=util.nvl((String)request.getAttribute("regnIdn"));
        String msg=util.nvl((String)request.getAttribute("msg"));
        String addrid= util.nvl((String)request.getAttribute("ADDRIDN"));
        String addr= util.nvl((String)request.getAttribute("ADDR"));
        String nme_idn= util.nvl((String)request.getAttribute("NMEIDN"));
        String nme= util.nvl((String)request.getAttribute("NAME"));
        String salidn= util.nvl((String)request.getAttribute("SALIDN"));
        String saledte= util.nvl((String)request.getAttribute("SAL_DTE"));
        String mnlsale_id= util.nvl((String)request.getAttribute("MNLSALE_ID"));
        %>
 <body onfocus="<%=onfocus%>">
  <input type="hidden" name="sal_idn" id="sal_idn" value="<%=salidn%>">

    <table align="center"> 
     <%  if(!msg.equals("") && msg!=null) {%>
    <tr><td valign="top" class="hedPg"> <span class="blueLabel" > <%=msg%></span></td></tr>
    <%}%>
<tr>
     <td>Buyer</td>
      <td nowrap="nowrap">
   
    <%
    String sbUrl ="ajaxRptAction.do?method=buyerSugg";
    %>
    <input type="text" id="nme" name="nme" autocomplete="off" class="sugBox"
      onKeyUp="doCompletion('nme', 'nmePop', 'ajaxRptAction.do?method=buyerSugg', event)" 
      onKeyDown="setDown('nmePop', 'nmeID', 'nme',event)" value="<%=nme%>"/>
 <img src="../images/addrow.jpg" width="17" height="17" title="Click To get Names" onclick="doCompletionMobile('nme', 'nmePop', 'ajaxRptAction.do?method=buyerSugg')">
 <input type="hidden" name="nmeID" id="nmeID" value="<%=nme_idn%>">
  <div style="position: absolute;">
      <select id="nmePop" name="nmePop"
        style="display:none;300px;" 
        class="sugBoxDiv"
        onKeyDown="setDown('nmePop', 'nmeID', 'nme',event);" 
        onDblClick="setVal('nmePop', 'nmeID', 'nme', event);hideObj('nmePop');setAddr('nmeID');" 
        onClick="setVal('nmePop', 'nmeID', 'nme', event);setAddr('nmeID');" 
        size="10">
      </select>
</div>
 </td>
   </tr>
<tr><td>Address </td><td><html:textarea  property="value(addr)" styleId="addr" value="<%=addr%>" rows="3" cols="30"  readonly="true"></html:textarea>
    <input type="hidden" name="addr_idn" id="addr_idn" value="<%=addrid%>">

</td></tr>
<tr>
<td>Sale Date</td>
<td>
<html:text property="value(sal_dte)" styleId="sal_dte" name="dailySalesReportForm" size="10" value="<%=saledte%>" />
<img src="<%=info.getReqUrl()%>/images/calender.png" onClick="setYears(2011, <%=info.getCurrentYear()%>);showCalender(this, 'sal_dte');"> 
</td>
</tr>
<tr>
<td>Sale Id</td>
<td>
<html:text property="value(mnlsale_id)" styleId="mnlsale_id" name="dailySalesReportForm" size="10" value="<%=mnlsale_id%>" />
</td>
</tr>
<tr> 
<br>
<br>
<td colspan="2" align="center"><button name="change" onclick="changeInvNme()" class="submit">Submit</button></td>
</tr>
  </table>
 </body>
   <%@include file="../calendar.jsp"%>
</html>