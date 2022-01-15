<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import="java.util.ArrayList,java.util.HashMap,java.util.HashMap,java.util.Enumeration, java.sql.ResultSet,ft.com.*, java.util.Date, java.util.ArrayList, ft.com.dao.*"%>
<jsp:useBean id="info" class="ft.com.InfoMgr" scope="session" />
<jsp:useBean id="util" class="ft.com.JspUtil" scope="page" />
<%@taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>
<%@taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>Box Properties Update</title>
    <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/bse.css?v=<%=info.getJsVersion()%>"/>
      <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/style.css?v=<%=info.getJsVersion()%>"/>
      <!--<script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/memoScript.js?v=<%=info.getJsVersion()%> " > </script>-->
        <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/bse.js?v=<%=info.getJsVersion()%> " > </script>
        <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/box.js?v=<%=info.getJsVersion()%> " > </script>
                <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/ajax.js?v=<%=info.getJsVersion()%> " > </script>
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
  <img src="../images/line.jpg" border="0" /></td><td valign="middle"> <span class="pgHed">Box Master Form</span> </td>
</tr></table>
  </td>
  </tr>
  
  <tr><td valign="top" class="tdLayout">
  <%
  String formAction = "/box/BoxPrpUpd.do?method=save";
  %>
  <html:form action="<%=formAction%>"  method="POST"  >
  <html:hidden property="value(loc)" name="BoxPrpUpdForm"/>
        <table class="grid1">
        <tr><td colspan="9">NEW</td></tr>
        <tr>
       <th><span class="redTxt">*</span>Box Name</th><th><span class="redTxt">*</span>PKT Type</th>
<th><span class="redTxt">*</span>QTY</th><th><span class="redTxt">*</span>CTS</th><th>Box Price</th><th>Box Value</th>
        <th>FCPR</th><th>RFID</th><th>FLG</th>
        </tr>
        <tr>
            
        <td><html:text styleId="bname" name="BoxPrpUpdForm" property="bname" onchange="verifyvnm(this.id)" /></td>
                
    
    <td>
    <html:select name="BoxPrpUpdForm" property="ptyp"  style="width:95px; border:1px solid #c51212;">
    <html:option value="0">---Select---</html:option>
    <html:option value="MX">MX</html:option>
    <html:option value="MIX">MIX</html:option>
    
    </html:select>
    </td>
    
                <td><html:text name="BoxPrpUpdForm" property="qty"/></td>
                <td><html:text name="BoxPrpUpdForm" property="cts" onchange="calculateValue()" /></td>
                <td><html:text name="BoxPrpUpdForm" property="bprice" onchange="calculateValue()" /></td>
                <td><html:text name="BoxPrpUpdForm" property="vlu" onfocus="calculateValue()" /></td>
                <td><html:text name="BoxPrpUpdForm" property="fcpr"/></td>
                <td><html:text name="BoxPrpUpdForm" property="tfl3"/></td>
                <td>
                <html:select name="BoxPrpUpdForm" property="flgtyp"  style="width:95px; border:1px solid #c51212;">
              
                <html:option value="S">Singles</html:option>
                <html:option value="M">Multiple</html:option>
                </html:select>
                </td>
                
   
            </tr>
            <tr>
            </tr>
            <tr>
     <th colspan="9"><html:submit property="submit" value="Add Box" styleClass="submit"   /> 
     
            <html:submit property="upd" value="Update" styleClass="submit"/></th>
            
            </tr>
   
        </table>
  
        </html:form>
  
  
   </td></tr>
      
        
      
<%
ArrayList listtable=(ArrayList)session.getAttribute("listtable");
String loc=util.nvl((String)request.getAttribute("loc"));
if(listtable!=null && listtable.size()>0){
%>
 
 
 

<tr><td valign="top" class="tdLayout">
            <table class="grid1">
        <tr><td colspan="11">EXISTING</td></tr>
 
            <tr>
              <th>Box Name</th>
              <th>PKT Type</th>
               <th>QTY</th>
               <th>QTY ISS</th>
              <th>CTS</th>
              <th>CTS ISS</th>
              <th>BOX PRICE</th>
              <th>FCPR</th>
              <th>RFID</th>
              <th></th>
              <th></th>
            </tr>				
            
          <%
         
          for(int i=0;i<listtable.size();i++){
           HashMap listBox=(HashMap)listtable.get(i);
          String boxname = (String)listBox.get("vnm");
          
          int sr = i+1;
          String count = "bnma_"+sr;
          %>
          	 <tr>		
                  <td><input type="text" id="box" value="<%=boxname%>" disabled="true" />
                  </td>
                  <td><%=listBox.get("typ")%>
                  </td>
                  <td><%=listBox.get("qty")%>
                  </td>
                  <td><%=listBox.get("qty_iss")%>
                  </td>
                  <td><%=listBox.get("cts")%>
                  </td>
                  <td><%=listBox.get("cts_iss")%>
                  </td>
                  <td><%=listBox.get("upr")%>
                  </td>
                   <td><%=listBox.get("fcpr")%>
                  </td>
                   <td><%=listBox.get("tfl3")%>
                  </td>
                  <%
                   String fldId =(String) listBox.get("stk_idn");
                  String link1="/box/BoxPrpUpd.do?method=edit&loc="+loc+"&mstkIdn="+ fldId ;  
                   String target = "SC_"+fldId;
                   
                   %>
                  
                  <td><html:link page="<%=link1%>">Edit </html:link>
                  </td>
                <td> <a href="BoxPrpUpd.do?method=boxUpd&mstkIdn=<%=fldId%>&mdl=BOX_UPD" 
                onclick="setBGColor('<%=fldId%>','DV_')" target="<%=target%>">Update Properties</a></td>
                </tr>
                
                <tr><td colspan="10" style="display:none;" id="DV_<%=fldId%>">
                  
                <iframe name="<%=target%>" height="320" width="800" frameborder="0">

                </iframe>

             
       <%   
       }
       }
       %>
            
                				      
              </table>
              </td>
	          </tr>
            </table>
      
      
 </body>
</html>