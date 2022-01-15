<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page errorPage="../error_page.jsp" %>
<%@ page import="java.util.ArrayList,java.util.ArrayList,java.util.HashMap, java.sql.*, java.util.Enumeration, java.util.logging.Level"%>
<%@ page contentType="text/html;charset=windows-1252"%>
<jsp:useBean id="info" class="ft.com.InfoMgr" scope="session" />
<jsp:useBean id="util" class="ft.com.JspUtil" scope="page" />
<%@taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>Buyer Setup</title>
    <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/bse.js?v=<%=info.getJsVersion()%> " > </script>
<link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/bse.css?v=<%=info.getJsVersion()%>"/>
<link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/general.css?v=<%=info.getJsVersion()%>"/>
<script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/ajax.js?v=<%=info.getJsVersion()%> " > </script>
  <script type="text/javascript" src="<%=info.getReqUrl()%>/PopScripts/popup.js?v=<%=info.getJsVersion()%> " > </script>
    <script src="<%=info.getReqUrl()%>/PopScripts/jquery-1.2.6.min.js" type="text/javascript"></script>
   <script language="javaScript" type="text/javascript" src="<%=info.getReqUrl()%>/calendar/calendar.js"></script>
   <link href="<%=info.getReqUrl()%>/calendar/calendar.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/memoScript.js?v=<%=info.getJsVersion()%> " > </script>
 
      <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/style.css?v=<%=info.getJsVersion()%>"/>
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
    <td height="5" valign="top" class="greyline"><img src="../images/grey_linebig.png" width="685" height="5" align="right" /></td>
  </tr>
  <tr>
  <td valign="top" class="hedPg">
  <table cellpadding="1" cellspacing="5"><tr><td valign="middle">
  <img src="../images/line.jpg" border="0" /></td><td valign="middle"> <span class="pgHed">Buyer Setup</span> </td>
</tr></table>
  </td>
  </tr>
  <%
   String display = "";
   String msg = (String)request.getAttribute("msg");
   if(msg!=null){
   %>
    <tr><td valign="top" class="tdLayout"><span class="redLabel"><%=msg%></span></td></tr>
   <%}%>
   <tr>
   
   <td valign="top" class="tdLayout">
           <table cellspacing="3" cellpadding="3"><tr>
           <html:form action="/masters/byrSetup.do?method=save" method="POST" enctype="multipart/form-data" onsubmit="return checkRln()">
           <%String setUpbyrNm=(String)info.getSetUpbyrNm();
           if(setUpbyrNm.equals("")){
           %>
           <tr><td>
           <span class="txtBold"> Select Employee : </span> </td>
           <td><html:select property="byrId" styleId="byrId" onchange="getEmployee(this)"  name="byrForm"   >
            <html:option value="0" >--select--</html:option>
            <html:optionsCollection property="byrList" name="byrForm" 
            label="byrNme" value="byrIdn" />
            </html:select></td></tr>
             <tr><td> <span class="txtBold" >Party Name: </span></td>
              <td nowrap="nowrap">      
         <html:text  property="value(partytext)"  styleId="partytext" style="width:230px" styleClass="sugBox" onkeypress="return disableEnterKey(event);" 
           onkeyup="doCompletionPartyNME('partytext', 'nmePop', '../ajaxAction.do?1=1&isSrch=N&UsrTyp=BUYER', event)"
      onkeydown="setDownSerchpage('nmePop', 'party', 'partytext',event)" onclick="document.getElementById('partytext').autocomplete='off'"  />
      <img src="../images/addrow.jpg" width="17" height="17" title="Click To get Names" onclick="doCompletionMobilePartyNME('partytext', 'nmePop', '../ajaxAction.do?1=1&isSrch=Y&UsrTyp=BUYER')">
      <img src="../images/Delete.png" width="15" height="15" title="Click To Clear Names List" onclick="clearsugBoxDiv('partytext','party','nmePop','SRCH');">
      <html:hidden property="party" styleId="party"  />
  <div style="position: absolute;">
      <select id="nmePop" name="nmePop"
        style="display:none;300px;" 
        class="sugBoxDiv" 
        onKeyDown="setDownSerchpage('nmePop', 'party', 'partytext',event)" 
        onDblClick="setVal('nmePop', 'party', 'partytext', event);hideObj('nmePop'),getTrmsNME('party','SRCH');" 
        onClick="setVal('nmePop', 'party', 'partytext', event)" 
        size="10">
      </select>
</div>
          
            
</td></tr>
           
            <tr><td><span class="txtBold" >Terms: </span></td><td>
             <html:select property="byrRln" name="byrForm" styleId="rlnId" style="width:200px"  onchange="getDropDwonXrt(this)">
           <html:option value="0" >---select---</html:option>
             <html:optionsCollection property="trmList" name="byrForm" 
            label="trmDtl" value="relId" />
            </html:select>
            </td></tr>
            <tr><td>Exchange Rate</td>
           <td>
           <html:text property="value(xrt)" styleId="xrtId" size="5" value="1" onchange="isNumericDecimal(this.id)" readonly="true"  name="byrForm" />
           </td></tr> 
           <%}else{
            display = "style=\"display:none\"";
            String setUppartyNm="",setUpTermNm="",setUpEX="";
            String setUpbyrId,setUppartyId,setUpTermId;
            setUpbyrId=String.valueOf(info.getSetUpbyrId());
            setUppartyNm=info.getSetUppartyNm();
            setUppartyId=String.valueOf(info.getSetUppartyId());
            setUpTermId=String.valueOf(info.getSetUpTermId());
            setUpTermNm=info.getSetUpTermNm();
            setUpEX=info.getSetUpEX();
            %>
           <tr>  <td>
           <span class="txtBold"> Select Employee</span></td><td>:</td><td>
           <label><b><%=setUpbyrNm%></b></label>
           <html:hidden property="byrId" name="searchForm" value="<%=setUpbyrId%>" />
           </td></tr>
            <tr><td> <span class="txtBold" >Party Name</span></td><td>:</td><td>
             <label><b><%=setUppartyNm%></b></label>
           <html:hidden property="party" name="searchForm" value="<%=setUppartyId%>" />
            </td></tr>
           
            <tr><td> <span class="txtBold" >Terms</span></td><td>:</td><td>
             <label><b><%=setUpTermNm%></b></label>
           <html:hidden property="byrRln" name="searchForm" value="<%=setUpTermId%>" />
            </td><tr>
            <tr><td>Exchange Rate</td><td>:</td><td>
           <label><b><%=setUpEX%></b></label>
           <html:hidden property="value(xrt)" name="searchForm" value="<%=setUpEX%>" />
           </td></tr>
           <%}%>
            <tr><td colspan="2" align="center" <%=display%>><html:submit property="value(srch)" value="Save SetUp" styleClass="submit" onclick="return byrsetupvalidate();" /></td> </tr>
           </html:form>
           </table>
            </td></tr>
   
    <tr>
    <td><jsp:include page="/footer.jsp" /> </td>
    </tr>
  </table>

  </body>
</html>