<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page errorPage="../error_page.jsp" %>
<%@ page contentType="text/html;charset=windows-1252"%>
<jsp:useBean id="info" class="ft.com.InfoMgr" scope="session" />
<jsp:useBean id="util" class="ft.com.JspUtil" scope="page" />
<%@ page import="java.util.ArrayList,java.util.HashMap,java.sql.ResultSet,ft.com.*, java.util.Date, java.util.ArrayList, ft.com.dao.*,java.io.File"%>
<%@taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>Pricing History</title>
    <script language="javaScript" type="text/javascript" src="<%=info.getReqUrl()%>/scripts/ajax.js?v=<%=info.getJsVersion()%>"></script>
    <script src="<%=info.getReqUrl()%>/scripts/assortScript.js?v=<%=info.getJsVersion()%>" type="text/javascript"></script>
     <script src="<%=info.getReqUrl()%>/scripts/memoScript.js?v=<%=info.getJsVersion()%>" type="text/javascript"></script>
    <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/bse.css?v=<%=info.getJsVersion()%>"/>
    <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/general.css?v=<%=info.getJsVersion()%>"/>
     <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/style.css?v=<%=info.getJsVersion()%>"/>
     <script src="<%=info.getReqUrl()%>/scripts/priceChange.js?v=<%=info.getJsVersion()%>" type="text/javascript"></script>
     <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/bse.js?v=<%=info.getJsVersion()%> " > </script>
      <script type="text/javascript" src="<%=info.getReqUrl()%>/PopScripts/popup.js?v=<%=info.getJsVersion()%> " > </script>
     <script src="<%=info.getReqUrl()%>/PopScripts/jquery-1.2.6.min.js" type="text/javascript"></script>
    <script language="javaScript" type="text/javascript" src="<%=info.getReqUrl()%>/calendar/calendarFmt.js"></script>
   <link href="<%=info.getReqUrl()%>/calendar/calendar.css" rel="stylesheet" type="text/css">
   <script type="text/javascript">
 function loadPriceGroup(){ 
 //load data from the server using a HTTP POST request
 document.getElementById('grpDtl').innerHTML="";
 var img = document.createElement('img')
img.src = '../images/loadbig.gif';
document.getElementById('grpDtl').appendChild(img);
 var grpNme = document.getElementById('grpNme').value;
$.post('priHistory.do?method=PriGroup',{'grpNme':grpNme}, function(data){
document.getElementById('grpDtl').innerHTML="";
$("#grpDtl").append(data); //append received data into the element
});
 }

</script>
  
  </head>
        <%String logId=String.valueOf(info.getLogId());
        String onfocus="cook('"+logId+"')";
        %>
 <body onfocus="<%=onfocus%>" onload="MM_preloadImages('images/faunalogo.png')" topmargin="0" leftmargin="0" rightmargin="0" bottommargin="0">

<input type="hidden" id="reqUrl" name="reqUrl" value="<%=info.getReqUrl()%>"/>
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
  <img src="../images/line.jpg" border="0" /></td><td valign="middle"> <span class="pgHed">Pricing History</span> </td>
</tr></table>
  </td>
  </tr>
  <tr><td valign="top" class="tdLayout">
  <html:form action="pri/priHistory.do?method=Fetch" method="POST">
 <table>
 <tr><td>Group Name: </td>
 <td>
 <html:select  property="value(grp)" styleId="grpNme" name="priceHistoryForm" onchange="loadPriceGroup()" >
 <html:option value="0">---select---</html:option>
 <html:optionsCollection property="grpList"  label="nme" value="nme" name="priceHistoryForm" />
 </html:select>
 </td></tr>
 

 <tr><td colspan="2">
 <div id="grpDtl">
 
 </div>
 </td></tr>
 
 
 </table></html:form>
  </td></tr>
   <tr><td valign="top" class="tdLayout">
   <html:form action="pri/priHistory.do?method=priceHis" method="POST">
   <div class="memo" >
   <% ArrayList grpList = (ArrayList)request.getAttribute("grpDtlList");
  String pMatName="";
  if(grpList!=null){%>
  <table><tr><td><html:submit property="value(downLoad)" value="Download" styleClass="submit" /></td></tr>
  <tr><td>
  <table border="0"  class="Orange" cellspacing="0" cellpadding="0">
  <tr>
  <th class="Orangeth" align="left"><input type="checkbox" name="all" id="all" onclick="CheckBOXALLForm('1',this,'cb_mat_')" /> </th>
  <th class="Orangeth">Name</th> <th class="Orangeth">Properties</th><th class="Orangeth" >From</th>
  <th class="Orangeth" >To</th>
  </tr>
  <%
 
  for(int i=0;i<grpList.size();i++){
  HashMap grpDtl = (HashMap)grpList.get(i);
  String idn = (String)grpDtl.get("idn");
  String matNme =(String)grpDtl.get("nme");
  String dte = (String)grpDtl.get("dte");
  String PRMTYP = (String)grpDtl.get("PRMTYP");
  String disNme = "";
  if(pMatName.equals("")){
   pMatName = matNme;
   disNme = matNme;
   }
   if(!pMatName.equals(matNme)){
   disNme = matNme;
   pMatName = matNme;
   
   }
  String matCheck = "cb_mat_"+idn;
  String fileNme = "fileVal(cb_upl_"+idn+")";
  %>
  <tr>
  <td colspan="2">
  <%if(!disNme.equals("")){%>
  <table><tr><td>
  <input type="hidden" name="PRMTYP_<%=idn%>" id="PRMTYP_<%=idn%>" value="<%=PRMTYP%>" />
  <input type="checkbox" name="<%=matCheck%>" id="<%=matCheck%>" value="<%=idn%>" /></td><td>
    <%=disNme%>
  <input type="hidden" value="<%=disNme%>" name="<%=idn%>_SHTNME"/>
  </td><td><%=dte%></td> 
  </tr></table>
  <%}%>
  </td>
 
  <td><%=grpDtl.get("mprp")%></td><td><%=grpDtl.get("valFr")%></td>
  <td><%=grpDtl.get("valTo")%></td>

  </tr>
  <%}}%></table></td></tr></table></div>
  </html:form>
  </td></tr>
    
      <tr>
    <td><jsp:include page="/footer.jsp" /> </td>
    </tr>
    
   </table> 
    <%@include file="/calendar.jsp"%>
    </body>
</html>