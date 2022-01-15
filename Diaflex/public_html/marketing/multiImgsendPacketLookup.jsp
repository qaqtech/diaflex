<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
 
<%@ page errorPage="../error_page.jsp" %>
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import="java.util.ArrayList,java.util.ArrayList, java.util.Iterator,java.util.List,java.io.*, java.sql.Date,java.util.HashMap, ft.com.marketing.PacketLookupForm" %>
<jsp:useBean id="util" class="ft.com.JspUtil" scope="page" />
<jsp:useBean id="info" class="ft.com.InfoMgr" scope="session" />
<%@taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>
<%@taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<html>
  <head>
    <meta name="Keywords" content="javascript,dhtml,html,navigation,pop ups,web tools,graphics" />
  <meta name="Description" content="free javascrip, software and graphic programs,pop up windows,menus,mouseover effects" />
  <meta http-equiv="Content-Language" content="en-gb"/>
  <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>Multi Images Send</title>
    <script src="../scripts/bse.js?v=<%=info.getJsVersion()%>" type="text/javascript"></script>
    <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/bse.css?v=<%=info.getJsVersion()%>"/>
      <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/style.css?v=<%=info.getJsVersion()%>"/>
      <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/memoScript.js?v=<%=info.getJsVersion()%> " > </script>
      <script src="<%=info.getReqUrl()%>/PopScripts/jquery-1.2.6.min.js" type="text/javascript"></script>
      <script src="<%=info.getReqUrl()%>/jqueryScript/jqueryScript.js?v=<%=info.getJsVersion()%>" type="text/javascript"></script>
      <script src="<%=info.getReqUrl()%>/jqueryScript/jquery.min.js" type="text/javascript"></script>  
         <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/bse.js?v=<%=info.getJsVersion()%> " > </script>
      <meta name="GENERATOR" content="Microsoft FrontPage 4.0">
      <meta name="ProgId" content="FrontPage.Editor.Document"><META HTTP-EQUIV="imagetoolbar" CONTENT="no">
 
  </head>
        <%String logId=String.valueOf(info.getLogId());
        String onfocus="cook('"+logId+"')";
        ArrayList imagelistDtl= (info.getImagelistDtl() == null)?new ArrayList():(ArrayList)info.getImagelistDtl();
      String msg=util.nvl((String)request.getAttribute("msg"));
        %>
 <body onfocus="<%=onfocus%>">
    <table>
      <%  if(!msg.equals("") || !msg.equals("null")) {%>
    <tr><td valign="top" class="tdLayout"> <span class="redLabel" > <%=msg%></span></td></tr>
    <%}%>
    <tr>
 <td>
  <html:form styleId="multi" action="marketing/PacketLookup.do?method=multiImgSend" method="post" onsubmit="return checkBoxVald();" >
<html:hidden property="value(flg)" name="packetLookupForm" styleId="flg"/>
<html:hidden property="value(where)" name="packetLookupForm" styleId="where"/>
<table>
<tr>
<td>
<table>
<tr>
<td>Subject</td><td><html:textarea property="value(subject)" name="packetLookupForm" styleId="subject"/></td>
</tr>
<tr>
<td>To</td><td><html:text property="value(to_eml)" name="packetLookupForm" size="40" styleId="to_eml"/></td>
</tr>
<tr>
<td>cc</td><td><html:text property="value(cc_eml)" name="packetLookupForm" size="40" styleId="cc_eml"/></td>
</tr>
<tr>
<td>bcc</td><td><html:text property="value(bcc_eml)" name="packetLookupForm" size="40" styleId="bcc_eml"/></td>
</tr>
</table>
</td>
</tr>
<%

if(imagelistDtl!=null && imagelistDtl.size() >0){
int i=0;
for(int j=0;j<imagelistDtl.size();j++){
HashMap dtls=new HashMap();
dtls=(HashMap)imagelistDtl.get(j);
String typ=util.nvl((String)dtls.get("TYP"));
String allowon=util.nvl((String)dtls.get("ALLOWON"));
if( allowon.indexOf("MULTI") > -1){
String path=util.nvl((String)dtls.get("PATH"));
String gtCol=util.nvl((String)dtls.get("GTCOL"));
i++;
String cbnme="cb_img"+"_"+i;
%>
       <tr>
       <td>
       <input type="checkbox" id="<%=cbnme%>"  name="<%=cbnme%>"  value="<%=gtCol%>" checked="checked" >
       <%=gtCol%> 
       </td>
       </tr>
    <%}}}%>
    <tr><td>
      <html:submit property="value(submit)" styleClass="submit" value="Send" />
      </td></tr>
     </table>
    </html:form>
    </td>
   </tr>
  </table>
 </body>
</html>