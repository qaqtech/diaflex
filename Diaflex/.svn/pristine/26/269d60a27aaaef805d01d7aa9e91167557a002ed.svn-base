<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import="java.util.ArrayList,java.util.HashMap,java.util.HashMap,java.util.Enumeration, java.sql.ResultSet,ft.com.*, java.util.Date, java.util.ArrayList, ft.com.dao.*"%>
<jsp:useBean id="info" class="ft.com.InfoMgr" scope="session" />
<jsp:useBean id="util" class="ft.com.JspUtil" scope="page" />
<%@taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>
<%@taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>Box Master Form</title>
    <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/bse.css?v=<%=info.getJsVersion()%>"/>
      <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/style.css?v=<%=info.getJsVersion()%>"/>
      <!--<script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/memoScript.js?v=<%=info.getJsVersion()%> " > </script>-->
        <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/bse.js?v=<%=info.getJsVersion()%> " > </script>
        <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/box.js?v=<%=info.getJsVersion()%> " > </script>
                <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/ajax.js?v=<%=info.getJsVersion()%> " > </script>
        </head>
        <%
    HashMap allPageDtl= (info.getPageDetails() == null)?new HashMap():(HashMap)info.getPageDetails();
    HashMap pageDtl=(HashMap)allPageDtl.get("BOX_MASTER");
    ArrayList prpDspBlocked = info.getPageblockList();
    ArrayList pageList=new ArrayList();
    HashMap pageDtlMap=new HashMap();
    String fld_nme="",fld_ttl="",val_cond="",dflt_val="",fld_typ="",form_nme="",lov_qry="";
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
  <img src="../images/line.jpg" border="0" /></td><td valign="middle"> <span class="pgHed">Box Master Form</span> </td>
</tr></table>
  </td>
  </tr>
   <%String msg = (String)request.getAttribute("msg");
   if(msg!=null){
   %>
    <tr><td valign="top" class="tdLayout"><span class="redLabel"><%=msg%></span></td></tr>
   <%}%>
  <tr><td valign="top" class="hedPg">
  <html:form action="/box/boxmaster.do?method=save"  method="POST" onsubmit="return validate_boxmaster();"  >
  <html:hidden property="value(loc)" name="boxMasterForm"/>
  <html:hidden property="value(mstkIdn)" name="boxMasterForm"/>
        <table class="grid1">
        <tr><td colspan="9" align="center">
        <logic:equal property="value(mstkIdn)"  name="boxMasterForm"  value="" >
        NEW
        </logic:equal>
        <logic:notEqual property="value(mstkIdn)"  name="boxMasterForm"  value="" >
        UPDATE
        </logic:notEqual>
        
        </td></tr>
        <tr>
       <th><span class="redTxt">*</span>Box Name</th>
       <th><span class="redTxt">*</span>Packet Type</th>
       <th><span class="redTxt">*</span>Qty</th>
       <th><span class="redTxt">*</span>Cts</th>
       <th>Box Price</th><th>Box Value</th>
       <th>Fcpr</th><th>Rfid</th><th>Flg</th>
        </tr>
        <tr>
       <td><html:text styleId="bname" name="boxMasterForm" property="bname" onchange="verifyvnm(this.id)" /></td>
    <td>
    <html:select name="boxMasterForm" property="ptyp" styleId="ptyp"  style="width:95px; border:1px solid #c51212;">
    <html:option value="0">---Select---</html:option>
    <html:option value="MX">MX</html:option>
    <html:option value="MIX">MIX</html:option>
    </html:select>
    </td>
    
                <td><html:text name="boxMasterForm" property="qty" styleId="qty" /></td>
                <td><html:text name="boxMasterForm" property="cts" styleId="cts" onchange="calculateValueboxMaster()" /></td>
                <td><html:text name="boxMasterForm" property="bprice" styleId="bprice" onchange="calculateValueboxMaster()" /></td>
                <td><html:text name="boxMasterForm" property="vlu" styleId="vlu" onfocus="calculateValueboxMaster()" /></td>
                <td><html:text name="boxMasterForm" property="fcpr"/></td>
                <td><html:text name="boxMasterForm" property="tfl3"/></td>
                <td>
                <html:select name="boxMasterForm" property="flgtyp"  style="width:95px; border:1px solid #c51212;">
              
                <html:option value="S">Singles</html:option>
                <html:option value="M">Multiple</html:option>
                </html:select>
                </td>
                
   
            </tr>
            <tr>
            </tr>
            <tr>
             <td colspan="9"  align="center">
             <logic:equal property="value(mstkIdn)"  name="boxMasterForm"  value="" >
             <html:submit property="value(save)" value="Add Box" styleClass="submit"   /> 
             </logic:equal>
             <logic:notEqual property="value(mstkIdn)"  name="boxMasterForm"  value="" >
            <html:submit property="value(save)" value="Update" styleClass="submit"/>
            </logic:notEqual>
            </td>
            </tr>
   
        </table>
  
        </html:form>
  
  
   </td></tr>
      
        
      
<%
ArrayList pktList=((ArrayList)session.getAttribute("pktList") == null)?new ArrayList():(ArrayList)session.getAttribute("pktList");
String loc=util.nvl((String)request.getAttribute("loc"));
if(pktList!=null && pktList.size()>0){
%>
 
 
 

<tr><td valign="top" class="hedPg">
            <table class="grid1">
        <tr><td colspan="12"  align="center">EXISTING</td></tr>
 
            <tr>
              <th>Sr No</th>
              <th>Box Name</th>
              <th>Packet Type</th>
               <th>Qty</th>
               <th>Qty Iss</th>
              <th>Cts</th>
              <th>Cts Iss</th>
              <th>Box Price</th>
              <th>Fcpr</th>
              <th>Rfid</th>
            <%pageList= ((ArrayList)pageDtl.get("EDIT") == null)?new ArrayList():(ArrayList)pageDtl.get("EDIT");     
            if(pageList!=null && pageList.size() >0){%>
            <%for(int j=0;j<pageList.size();j++){
            pageDtlMap=(HashMap)pageList.get(j);
            fld_ttl=(String)pageDtlMap.get("fld_ttl");%>
            <th><%=fld_ttl%></th>   
            <%}}%>
            <%pageList= ((ArrayList)pageDtl.get("UPDATE_PRP") == null)?new ArrayList():(ArrayList)pageDtl.get("UPDATE_PRP");     
            if(pageList!=null && pageList.size() >0){%>
            <%for(int j=0;j<pageList.size();j++){
            pageDtlMap=(HashMap)pageList.get(j);
            fld_ttl=(String)pageDtlMap.get("fld_ttl");%>
            <th><%=fld_ttl%></th>   
            <%}}%>
            </tr>				
            
          <%
          int counter=1;
          for(int i=0;i<pktList.size();i++){
           HashMap pktPrpMap=(HashMap)pktList.get(i);
          String stk_idn = (String)pktPrpMap.get("stk_idn");
          %>
          	 <tr>	
                 <td align="center"><%=counter++%></td>
                  <td  align="right"><%=util.nvl((String)pktPrpMap.get("vnm"))%></td>
                  <td align="right"><%=util.nvl((String)pktPrpMap.get("typ"))%>
                  </td>
                  <td align="right"><%=util.nvl((String)pktPrpMap.get("qty"))%>
                  </td>
                  <td align="right"><%=util.nvl((String)pktPrpMap.get("qty_iss"))%>
                  </td>
                  <td align="right"><%=util.nvl((String)pktPrpMap.get("cts"))%>
                  </td>
                  <td align="right"><%=util.nvl((String)pktPrpMap.get("cts_iss"))%>
                  </td>
                  <td align="right"><%=util.nvl((String)pktPrpMap.get("upr"))%>
                  </td>
                   <td align="right"><%=util.nvl((String)pktPrpMap.get("fcpr"))%>
                  </td>
                   <td align="right"><%=util.nvl((String)pktPrpMap.get("tfl3"))%>
                  </td>
                <%pageList= ((ArrayList)pageDtl.get("EDIT") == null)?new ArrayList():(ArrayList)pageDtl.get("EDIT");     
                if(pageList!=null && pageList.size() >0){%>
                <%for(int j=0;j<pageList.size();j++){
                pageDtlMap=(HashMap)pageList.get(j);
                fld_ttl=(String)pageDtlMap.get("fld_ttl");
                String link1="/box/boxmaster.do?method=edit&loc="+loc+"&mstkIdn="+ stk_idn ;  
                %>
                <td><html:link page="<%=link1%>">Edit </html:link>  </td>
                <%}}%>
                  
                
                <%
                String target = "SC_"+stk_idn;
                pageList= ((ArrayList)pageDtl.get("UPDATE_PRP") == null)?new ArrayList():(ArrayList)pageDtl.get("UPDATE_PRP");     
                if(pageList!=null && pageList.size() >0){%>
                <%for(int j=0;j<pageList.size();j++){
                pageDtlMap=(HashMap)pageList.get(j);
                fld_ttl=(String)pageDtlMap.get("fld_ttl");
                String link1="/box/boxmaster.do?method=edit&loc="+loc+"&mstkIdn="+ stk_idn ;  
                %>
                <td> <a href="boxmaster.do?method=boxUpd&mstkIdn=<%=stk_idn%>" onclick="setBGColor('<%=stk_idn%>','DV_')" target="<%=target%>">Update Properties</a></td>
                <%}}%>
                </tr>
                
                <tr><td colspan="10" style="display:none;" id="DV_<%=stk_idn%>">
                  
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