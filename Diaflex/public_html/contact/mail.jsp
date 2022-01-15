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
    <title>Mail Form</title>
   <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/style.css?v=<%=info.getJsVersion()%>"/>
    <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/bse.css?v=<%=info.getJsVersion()%>"/>

  <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/bse.js?v=<%=info.getJsVersion()%> " > </script>
   <script language="javaScript" type="text/javascript" src="<%=info.getReqUrl()%>/calendar/calendar.js"></script>
   <link href="<%=info.getReqUrl()%>/calendar/calendar.css" rel="stylesheet" type="text/css" />
 <script src="<%=info.getReqUrl()%>/RichText/yahoo-dom-event.js?v=<%=info.getJsVersion()%>"></script> 
<script src="<%=info.getReqUrl()%>/RichText/element-min.js?v=<%=info.getJsVersion()%>"></script> 
<script src="<%=info.getReqUrl()%>/RichText/container_core-min.js?v=<%=info.getJsVersion()%>"></script>
<script src="<%=info.getReqUrl()%>/RichText/menu-min.js?v=<%=info.getJsVersion()%>"></script>
<script src="<%=info.getReqUrl()%>/RichText/button-min.js?v=<%=info.getJsVersion()%>"></script>
<script src="<%=info.getReqUrl()%>/RichText/editor-min.js?v=<%=info.getJsVersion()%>"></script>
<script src="<%=info.getReqUrl()%>/RichText/yui-image-uploader26.js?v=<%=info.getJsVersion()%>"></script>
<script src="<%=info.getReqUrl()%>/RichText/connection-min.js?v=<%=info.getJsVersion()%>"></script>
<link rel="stylesheet" type="text/css" href="<%=info.getReqUrl()%>/css/skin.css">
   <script>
var myEditor = new YAHOO.widget.Editor('longdesc', {
    height: '300px',
    width: '522px',
    dompath: true, //Turns on the bar at the bottom
    animate: true //Animates the opening, closing and moving of Editor windows
});
 yuiImgUploader(myEditor,'longdesc','<%=info.getReqUrl()%>/servlet/ICEServlet','image');
myEditor.render();

 YAHOO.util.Event.on('add', 'click', function() {
    //Put the HTML back into the text area
    myEditor.saveHTML();
 
    //The var html will now have the contents of the textarea
  
    var html =myEditor.get('element').value;
  document.getElementById('msg').value= html;
	
});
</script>

  </head>
  <%
      HashMap dbmsInfo = info.getDmbsInfoLst();
  String senderID =(String)dbmsInfo.get("SENDERID");
  String senderNm =(String)dbmsInfo.get("SENDERNM");
  String cnt =(String)dbmsInfo.get("CNT");
 ArrayList ByrEmailIds = (ArrayList)request.getAttribute("ByrEmailIds");
String Checkflg = (String)request.getAttribute("Checkflg");
String load = util.nvl((String)request.getAttribute("load"));
   String formAction = util.nvl((String)request.getAttribute("formaction"));
   String isMassMail=util.nvl(request.getParameter("isMassMail"));
   if(formAction.equals("")){
     formAction = "/contact/massmail.do?method=send";
  
     }
     int loop=0;
  if(ByrEmailIds!=null){
   loop=ByrEmailIds.size();
  }

        String logId=String.valueOf(info.getLogId());
        String onfocus="cook('"+logId+"')";
        String onsubmit="return validatesendMail()";
        if(cnt.equals("pm") || cnt.equals("asha"))
        onsubmit="";
        
        %>
 <body onfocus="<%=onfocus%>" class="yui-skin-sam">
 <table width="100%"  border="0" align="center" cellpadding="0" cellspacing="0" >
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
  <img src="../images/line.jpg" border="0" /></td><td valign="middle"> <span class="pgHed">Mail Detail</span> </td>
</tr></table>
  </td>
  </tr>
   <tr>
  <td valign="top" >
   <html:form  action="<%=formAction%>"  enctype="multipart/form-data" onsubmit="<%=onsubmit%>">
   <input type="hidden" name="loopemailid" id="loopemailid" value="<%=loop%>"/>
   <input type="hidden" name="load" id="load" value="<%=load%>"/>
   <input type="hidden" name="isMassMail" id="isMassMail" value="<%=isMassMail%>"/>
    <table width="50%" align="center" class="grid1" >
 <tr>
   
<tr>
<th align="left" colspan="2">
Please Enter the Following Details</th>
</tr>

<tr>
<td align="left" colspan="2">
<font color="red"><html:errors/></font>
</tr>
<tr>
<td align="left">
 Sender ID
</td>
<td align="left">
<input type="text" name="senID" size="40" id="senID" value="<%=senderID%>" />
</td></tr>
<tr>
<td align="left">
 Sender Name:
</td>
<td align="left">
<input type="text" name="senNme" size="40" id="senNme" value="<%=senderNm%>" />
</td></tr>
<tr>
<td align="left">
Subject:
</td>
<td align="left">
<input type="text" name="subject" size="40" id="subject" />

</td>
</tr>
<%if(!isMassMail.equals("YES")){%>
<tr>
<td align="left">
Cc...
</td>
<td align="left">
<input type="text" name="Cc" size="40" id="Cc" />

</td>
</tr>

<tr>
<td align="left">
Bcc...
</td>
<td align="left">
<input type="text" name="Bcc" size="40" id="Bcc" />
</td>
</tr>
<%}%>
<tr>
<td align="left">
 Email Ids:
</td>
<td align="left">
<input type="text" name="eml" size="40" id="eml" />
</td></tr>

<%
if(ByrEmailIds!=null && ByrEmailIds.size()>0){
if(Checkflg!=null && !Checkflg.equals("")){
String emailid="";
String chkemailid="";
%>
<tr>
<td align="left">
<b>Buyer ID:</b>
</td>
<td align="left">
<%
int count=0;
for(int j=0;j< ByrEmailIds.size();j++){
emailid=(String)ByrEmailIds.get(j);
chkemailid="id_"+j;
if(count==3){
%>
</td></tr><tr><td></td><td>
<%
 } 
 count++;
%>
<input type="checkbox" name="<%=chkemailid%>" id="<%=chkemailid%>"  value="<%=emailid%>"/><%=emailid%>

<%}%>
</td></tr>

<%}}%>



<tr>
<td align="left">
Message:
</td>
<td align="left">
<html:textarea property="value(longdesc)" cols="50" rows="10" styleId="longdesc"/>
 <input type="hidden" name="msg" id="msg" />

</td>
</tr>

<tr>
<td align="left">
File Name:
</td>
<td align="left">
<html:file property="value(theFile)"/> 
</td>
</tr>

<tr>
<td align="centre" colspan="2">
   <input type="submit" id="add"  name="add" class="submit" value="Send" />
</td>
</tr>
</table>
  <tr>
  <td valign="top" width="90%" class="hedPg">
 
   <table width="95%">
   <tr>
    </tr>
  </table>
  
  
  </td></tr>
  
 </html:form>
  
    
<tr>
    <td><jsp:include page="/footer.jsp" /> </td>
    </tr>
   <%@include file="../calendar.jsp"%>
  </table></body></html>