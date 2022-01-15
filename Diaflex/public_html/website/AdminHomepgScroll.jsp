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
    <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/style.css?v=<%=info.getJsVersion()%>"/>
    <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/bse.css?v=<%=info.getJsVersion()%>"/>
    <script language="javaScript" type="text/javascript" src="<%=info.getReqUrl()%>/calendar/calendar.js"></script>
   <link href="<%=info.getReqUrl()%>/calendar/calendar.css" rel="stylesheet" type="text/css" />
  <script language="javaScript" type="text/javascript" src="<%=info.getReqUrl()%>/scripts/bse.js?v=<%=info.getJsVersion()%>"></script>
   <title>Admin Panel - HomepageScroll</title>
   <script src="<%=info.getReqUrl()%>/RichText/yahoo-dom-event.js"></script> 
<script src="<%=info.getReqUrl()%>/RichText/element-min.js"></script> 
<script src="<%=info.getReqUrl()%>/RichText/container_core-min.js"></script>
<script src="<%=info.getReqUrl()%>/RichText/menu-min.js"></script>
<script src="<%=info.getReqUrl()%>/RichText/button-min.js"></script>
<script src="<%=info.getReqUrl()%>/RichText/editor-min.js"></script>
<link rel="stylesheet" type="text/css" href="<%=info.getReqUrl()%>/css/skin.css">
<script>
var myEditor = new YAHOO.widget.Editor('longdesc', {
    height: '300px',
    width: '522px',
    dompath: true, //Turns on the bar at the bottom
    animate: true //Animates the opening, closing and moving of Editor windows
});
myEditor.render();

 YAHOO.util.Event.on('add', 'click', function() {
    //Put the HTML back into the text area
    myEditor.saveHTML();
 
    //The var html will now have the contents of the textarea
  
    var html = escape(myEditor.get('element').value);
  
	
});

YAHOO.util.Event.on('update', 'click', function() {
    //Put the HTML back into the text area
    myEditor.saveHTML();
 
    //The var html will now have the contents of the textarea
  
    var html = escape(myEditor.get('element').value);
  
	
});
</script>
  </head>
  <%
  String method = util.nvl(request.getParameter("method"));
  String dsc = util.nvl((String)request.getAttribute("dsc"));
        String logId=String.valueOf(info.getLogId());
        String onfocus="cook('"+logId+"')";
        %>
 <body onfocus="<%=onfocus%>" onload="MM_preloadImages('images/faunalogo.png')" class="yui-skin-sam" topmargin="0" leftmargin="0" rightmargin="0" bottommargin="0">
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
 Home Page Scroll
 </span> </td>
</tr></table>
  </td>
  </tr>
  <tr><td valign="top" class="hedPg"  >
 
    <html:form action="website/AdminHomepgScroll.do" method="post" enctype="multipart/form-data">
   <html:hidden property="idn" name="adminHomepgScrollForm" />
  <table class="grid1">
  

  <tr>
  <td>Ctg Name</td><td>
  <html:select property="ctgIdn" name="adminHomepgScrollForm"   >
         
            <html:optionsCollection property="ctgList" name="adminHomepgScrollForm"
            label="dsc" value="idn" />
    </html:select>
  </td>
  <tr>
  <tr>
      <td>Scroll Image: </td>
      <td><html:file property="homepageimg" name="adminHomepgScrollForm"/></td>
  </tr>
  <tr>
      <td>Start Date: </td>
      <td><div class="float"><html:text  property="validfrom" styleId="frmdte" name="adminHomepgScrollForm"/></div>
       <div class="float">     <a href="#"  onClick="setYears(1900, <%=info.getCurrentYear()%>);showCalender(this, 'frmdte');">
                <img src="<%=info.getReqUrl()%>/images/calender.png"></a></div>
      </td>
  </tr>
  
  <tr>
      <td>End Date: </td>
      <td><div class="float"><html:text property="validtill" styleId="todte" name="adminHomepgScrollForm"/></div>
             <div class="float">     <a href="#"  onClick="setYears(1900, <%=info.getCurrentYear()%>);showCalender(this, 'todte');">
                <img src="<%=info.getReqUrl()%>/images/calender.png"></a></div>
      </td>
  </tr>  
  <tr>
      <td>Scroll Text: </td>
       <td><textarea name="longdesc" id="longdesc"   cols="50" rows="10"><%=dsc%>
          </textarea>
   </td>
  </tr>
  
  
  
  <tr>
    <td>
     <% if(method.equals("edit")){%>
      <input type="submit" id="update"  name="update" value="Update" />
     <%}else{%>
          <input type="submit" id="add"  name="add" value="Add" /><%}%>
    </td>
    <td>
        
    </td>
  </tr>
   
   
 
  <html:hidden value="save" property="method"/>
  
  </table>
  </html:form>
  
     
     
      <tr><td valign="top" class="hedPg">
      
      <%
      ArrayList dataList = (ArrayList)request.getAttribute("datalist");
  if(dataList!=null && dataList.size()>0)
  {%>
      <table id="dbtable" border="1" class="grid1">
      <tr> 
        <th>Sr. No.</th>
        <th>Ctg</th>
        <th>Image</th>
        <th>Scroll Text</th>
        <th>From Date</th>
        <th>End Date</th>
        <th>Action</th>
      </tr>
<%   for(int i =0; i<dataList.size(); i++){
    HashMap dataPkt = (HashMap)dataList.get(i);
    String idn = (String)dataPkt.get("idn");
 %>
        <tr>
            <td><%=i+1%></td>
            <td><%=dataPkt.get("nme")%></td>
            <td><%=dataPkt.get("img")%></td>
            <td><%=dataPkt.get("dsc")%></td>
           
            <td><%=dataPkt.get("frmDte")%></td>
            <td><%=dataPkt.get("vldDte")%></td>
           <td><A href="<%=info.getReqUrl()%>/website/AdminHomepgScroll.do?method=edit&idn=<%=idn%>"> Edit</a></td>
           
        </tr>
      
<% }%>      
      </table>
<%      
    }
%>    
    
    </td></tr></table>
    <%@include file="../calendar.jsp"%>
  </body>
</html>