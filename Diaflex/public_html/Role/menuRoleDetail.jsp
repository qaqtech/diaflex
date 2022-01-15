<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page errorPage="../error_page.jsp" %>
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import="java.util.ArrayList,java.util.HashMap,java.sql.ResultSet,ft.com.*, java.util.Date, java.util.ArrayList, ft.com.dao.*"%>
<jsp:useBean id="info" class="ft.com.InfoMgr" scope="session" />
<jsp:useBean id="util" class="ft.com.JspUtil" scope="page" />

<%@taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<%@taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>Menu Roles Details</title>
<script type="text/javascript" src="<%=info.getReqUrl()%>/jquery/jquery-freezar.min.js"></script>
  <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/bse.css?v=<%=info.getJsVersion()%>"/>
    <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/style.css?v=<%=info.getJsVersion()%>"/>
     <script language="javaScript" type="text/javascript" src="<%=info.getReqUrl()%>/scripts/ajax.js?v=<%=info.getJsVersion()%>"></script>
       <script language="javaScript" type="text/javascript" src="<%=info.getReqUrl()%>/calendar/calendar.js"></script>
   <link href="<%=info.getReqUrl()%>/calendar/calendar.css" rel="stylesheet" type="text/css" />
<script language="javaScript" type="text/javascript" src="<%=info.getReqUrl()%>/scripts/bse.js?v=<%=info.getJsVersion()%>"></script>
 <script language="javaScript" type="text/javascript" src="<%=info.getReqUrl()%>/jquery/jquery.freezeheader.js?v=<%=info.getJsVersion()%>"></script>

 <script type="text/javascript">
   $(document).ready(function () {
	    $("#table2").freezeHeader({ 'height': '500px' });
    })
 </script>
  </head>   
<%
 ArrayList roleList = (ArrayList)session.getAttribute("roleList");
ArrayList mainMenuLst = (ArrayList)request.getAttribute("menuHdr");
HashMap subManuLst = (HashMap)request.getAttribute("subMenuMap");
HashMap subtrdMap = (HashMap)request.getAttribute("trdLevelMap");
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
    <td height="5" valign="top" class="greyline"><img src="<%=info.getReqUrl()%>/images/grey_linebig.png" width="685" height="5" align="right" /></td>
  </tr>
  <tr>
  <td valign="top" class="hedPg">
  <table cellpadding="1" cellspacing="5"><tr><td valign="middle">
  <img src="../images/line.jpg" border="0" /></td><td valign="middle"> <span class="pgHed">Assign Roles</span> </td>
</tr></table>
  </td>
  </tr>
  <tr><td class="tdLayout" valign="top">
 <table>
 <tr>
 <td>
<table class="grid1" id="table2">
<thead>
<tr><th nowrap="nowrap"> Menu List </th>
<%for(int i=0;i<roleList.size();i++){
Role roleDtl = (Role)roleList.get(i);  
%>
<th><%=roleDtl.getRole_dsc()%></th>
<%}%>
</tr>
</thead>
<tbody>	
	<%
        for(int i =0 ; i<mainMenuLst.size();i++){
        DFMenu dfMenu = (DFMenu)mainMenuLst.get(i);
        int dfId = dfMenu.getDfMenuIdn();
        String MMenuIdn = "MMu_"+dfId;
        String hdr = dfMenu.getDsp();
        String lnk =util.nvl(dfMenu.getLnk());
        if(lnk.equals("")){
        ArrayList subManu = (ArrayList)subManuLst.get(Integer.toString(dfId));
        %>
       <tr id="<%=MMenuIdn%>" ondblclick="setBGColorOnClickTR('<%=MMenuIdn%>')"><td nowrap="nowrap"><span><b><%=hdr%></b></span></td>
       <%for(int j=0;j<roleList.size();j++){
         Role roleDtl = (Role)roleList.get(j);
           String roleIdn = String.valueOf(roleDtl.getRoleIdn());
           String fldName = dfId+"_NA_"+roleIdn ;
           String onChange = "saveMenuRole("+dfId+",'NA',"+roleIdn+",this)";
           String fldVal = "value("+fldName+")";
           %>
             <td align="center"><html:checkbox  property="<%=fldVal%>" value="<%=fldName%>" name="roleForm" onchange="<%=onChange%>"/></td>
      <%}%>
       </tr>
	
        <% 
        if(subManu!=null)
        for(int j=0 ; j< subManu.size();j++){
        DFMenu subMenu = (DFMenu)subManu.get(j);
        int sdfId = subMenu.getDfMenuIdn();
         String SMenuIdn = "SMu_"+sdfId;
        String shdr = subMenu.getDsp();
        String slnk = util.nvl(subMenu.getLnk());
        if(slnk.equals("")){
        ArrayList trdLst = (ArrayList)subtrdMap.get(Integer.toString(sdfId));
        %>
        <tr  id="<%=SMenuIdn%>" ondblclick="setBGColorOnClickTR('<%=SMenuIdn%>')"><td>&nbsp;&nbsp;&nbsp;&nbsp;<%=shdr%></td>
        <%for(int n=0;n<roleList.size();n++){
        Role roleDtl = (Role)roleList.get(n);
        String roleIdn = String.valueOf(roleDtl.getRoleIdn());
         String fldName = sdfId+"_NA_"+roleIdn ;
           String onChange = "saveMenuRole("+sdfId+",'NA',"+roleIdn+",this)";
           String fldVal = "value("+fldName+")";
             
           %>
             <td align="center"><html:checkbox  property="<%=fldVal%>" value="<%=fldName%>" name="roleForm" onchange="<%=onChange%>"/></td>
      <%}%>
        
        </tr>
      
        
        <%  
         if(trdLst!=null)
        for(int k=0 ; k< trdLst.size();k++){
        DFMenu trdMenu = (DFMenu)trdLst.get(k);
        int tdfId = trdMenu.getDfmenuitmidn();
        String TMenuIdn="TMu"+tdfId;
        String thdr = trdMenu.getDsp();
        String tlnk = util.nvl(trdMenu.getLnk());
          %>
          <tr  id="<%=TMenuIdn%>" ondblclick="setBGColorOnClickTR('<%=TMenuIdn%>')"><td>
          &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
          <%=thdr%>
          </td>
            <%for(int n=0;n<roleList.size();n++){
           Role roleDtl = (Role)roleList.get(n);
           String roleIdn = String.valueOf(roleDtl.getRoleIdn());
           String fldName = sdfId+"_"+tdfId+"_"+roleIdn ;
           String onChange = "saveMenuRole("+sdfId+","+tdfId+","+roleIdn+",this)";
           String fldVal = "value("+fldName+")";
              
           %>
             <td align="center"><html:checkbox  property="<%=fldVal%>" value="<%=fldName%>" name="roleForm" onchange="<%=onChange%>"/></td>
      <%}%>
          </tr>  
          <%}%>
      
        <%}else{%>
      <tr id="<%=SMenuIdn%>" ondblclick="setBGColorOnClickTR('<%=SMenuIdn%>')"><td>&nbsp;&nbsp;&nbsp;&nbsp;<%=shdr%></td>
      
      <%for(int k=0;k<roleList.size();k++){
         Role roleDtl = (Role)roleList.get(k);
          String roleIdn = String.valueOf(roleDtl.getRoleIdn());
          String fldName = sdfId+"_NA_"+roleIdn ;
           String onChange = "saveMenuRole("+sdfId+",'NA',"+roleIdn+",this)";
           String fldVal = "value("+fldName+")";       
           %>
             <td align="center"><html:checkbox  property="<%=fldVal%>" value="<%=fldName%>" name="roleForm" onchange="<%=onChange%>"/></td>
      <%}%>
      </tr>
       <% }
        }
       %>
        
       
        <%}else{%>
        <tr id="<%=MMenuIdn%>" ondblclick="setBGColorOnClickTR('<%=MMenuIdn%>')"><td><b><%=hdr%></b></td>
         <%for(int k=0;k<roleList.size();k++){
         Role roleDtl = (Role)roleList.get(k);
          String roleIdn = String.valueOf(roleDtl.getRoleIdn());
         String fldName = dfId+"_NA_"+roleIdn ;
           String onChange = "saveMenuRole("+dfId+",'NA',"+roleIdn+",this)";
           String fldVal = "value("+fldName+")";
       %>
             <td align="center"><html:checkbox  property="<%=fldVal%>" value="<%=fldName%>" name="roleForm" onchange="<%=onChange%>"/></td>
      <%}%>
        
        </tr>
        
       <% } 
        
       } %>  

<tbody>
</table>
</td>
</tr>
 </table>
  </td>
  </tr>
  <tr>
    <td><jsp:include page="/footer.jsp" /> </td>
    </tr>
  </table>
   
</body>
</html>