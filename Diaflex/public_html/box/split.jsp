<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page errorPage="../error_page.jsp" %>
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.ResultSet, java.util.ArrayList,java.util.Collections,java.util.ArrayList,java.util.HashMap,java.text.SimpleDateFormat, java.util.Calendar"%>
<jsp:useBean id="info" class="ft.com.InfoMgr" scope="session" />
<jsp:useBean id="util" class="ft.com.JspUtil" scope="page" />
<%@taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>
<%@taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<html> 
   <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/bse.js?v=<%=info.getJsVersion()%> " > </script>
<link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/bse.css?v=<%=info.getJsVersion()%>"/>
<link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/general.css?v=<%=info.getJsVersion()%>"/>
<script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/ajax.js?v=<%=info.getJsVersion()%> " > </script>
<script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/box.js?v=<%=info.getJsVersion()%> " > </script>
  <script type="text/javascript" src="<%=info.getReqUrl()%>/PopScripts/popup.js?v=<%=info.getJsVersion()%> " > </script>
    <script src="<%=info.getReqUrl()%>/PopScripts/jquery-1.2.6.min.js" type="text/javascript"></script>
   <script language="javaScript" type="text/javascript" src="<%=info.getReqUrl()%>/calendar/calendar.js"></script>
   <link href="<%=info.getReqUrl()%>/calendar/calendar.css" rel="stylesheet" type="text/css" />

 
      <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/style.css?v=<%=info.getJsVersion()%>"/>

  <title>Split Form</title>
 
  </head>

        <%
          HashMap prp = info.getPrp();
        ArrayList bobTypList=new ArrayList();
        String logId=String.valueOf(info.getLogId());
        String onfocus="cook('"+logId+"')";
        bobTypList = (ArrayList)prp.get("BOX_TYPV");
        Collections.sort(bobTypList);
        HashMap allPageDtl= (info.getPageDetails() == null)?new HashMap():(HashMap)info.getPageDetails();
    HashMap pageDtl=(HashMap)allPageDtl.get("SPLIT_FORM");
    ArrayList pageList=new ArrayList();
    HashMap pageDtlMap=new HashMap();
    String fld_nme="",fld_ttl="",val_cond="",dflt_val="",fld_typ="",form_nme="",flg1="",lov_qry="";
        %>
 <body onfocus="<%=onfocus%>" onload="MM_preloadImages('images/faunalogo.png')" topmargin="0" leftmargin="0" rightmargin="0" bottommargin="0">
<div id="backgroundPopup"></div>

<div id="popupContactSave" align="center" >
<img src="../images/loading_flame.gif" height="150px" width="150px" border="0" />
</div>
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
  <img src="../images/line.jpg" border="0" /></td><td valign="middle"> <span class="pgHed">Split Form</span> </td>
<td id="loading"></td>
</tr></table>
  </td>
  </tr>
  <%String msg = (String)request.getAttribute("msg");
  if(msg!=null){
  %>
  <tr><td valign="top" class="tdLayout"> <span class="redLabel" > <%=msg%></span></td></tr>
  <%}%>
<tr><td valign="top" class="hedPg">
<html:form action="/box/split.do?method=fetch"  method="POST">
  <table  class="grid1">
   <tr>
   <th colspan="2" align="center">Search</th>
   </tr>
   <tr>
   <td colspan="2">
  <label>
  Box Type:
  <input type="text" name="boxtyp" list="characters" >
  <datalist id="characters">
    <%for(int i=0;i<bobTypList.size();i++){
    String boxprp=(String)bobTypList.get(i);%>
    <option value="<%=boxprp%>">
    <%}%>  
  </datalist>
  </label>
  </td>
  </tr>
  <tr><td colspan="2" align="center">OR</td></tr>
  <tr><td>Packets. </td><td>
<html:textarea property="value(vnmLst)" name="splitForm" styleId="vnmLst" />
</td>
</tr>
   <tr>
   <td>Status</td>
   <td align="left">
     <html:select  property="value(status)" name="splitForm" styleId="status"  > 
     <html:option value="0">---select---</html:option>
 <%
pageList= ((ArrayList)pageDtl.get("STT") == null)?new ArrayList():(ArrayList)pageDtl.get("STT");
if(pageList!=null && pageList.size() >0){
for(int c=0;c<pageList.size();c++){
pageDtlMap=(HashMap)pageList.get(c);
dflt_val=util.nvl((String)(String)pageDtlMap.get("dflt_val"));
%>
<html:option value="<%=dflt_val%>" ><%=dflt_val%></html:option> 
<%}}%>
    </html:select> 
   </td>
   </tr>
   <tr>
   <td colspan="2" align="center"><html:submit property="value(srch)" value="Fetch" styleClass="submit" />
   </td>
   </tr>
   </table>
</html:form>
</td>
</tr>
<%String view=util.nvl((String)request.getAttribute("view"));
if(view.equals("Y")){
ArrayList splitpktList = (ArrayList)session.getAttribute("splitpktList");
int splitpktListsz=splitpktList.size();
if(splitpktListsz>0){
%>
<html:form action="box/split.do?method=save" method="post" > 
   <html:hidden property="value(stt)" name="splitForm" styleId="stt" />
   <html:hidden property="value(noofrows)" name="splitForm" styleId="noofrows" value="1"/>
  <tr>
  <td valign="top" class="tdLayout">
  <table>
  <tr>
  <td>
  <table class="grid1">
  <tr>
    <th colspan="2"> SOURCE</th>
  </tr>
  <tr>
    <td>
      Packet List
      <html:select  property="value(splitvnm)" name="splitForm" onchange="getSplit(this)"> 
     <html:option value="0">---select---</html:option>
      <%for(int j=0;j<splitpktListsz;j++){
      ArrayList splitpktListdtl=(ArrayList)splitpktList.get(j);
      %>
      <html:option value="<%=util.nvl((String)splitpktListdtl.get(0))%>" ><%=util.nvl((String)splitpktListdtl.get(1))%></html:option> 
    <%}%>
    </html:select> 
    </td>   
    <td>
    <table class="grid1">
    <tr><th></th><th>QTY</th><th>CTS</th><th>AVG VALUE</th><th>TOTAL VALUE</th></tr>
    <tr><td>On Hand</td>
    <td><html:text property="value(fromqty)" styleId="fromqty" styleClass="sub" name="splitForm" readonly="true" value="0"   /></td>
    <td><html:text property="value(fromcts)" styleId="fromcts" styleClass="sub" name="splitForm"  readonly="true" value="0" /></td>
    <td><html:text property="value(fromrte)" styleId="fromrte" styleClass="sub" name="splitForm"  readonly="true" value="0" /></td>
    <td><html:text property="value(fromvalue)" styleId="fromvalue" styleClass="sub" name="splitForm"  readonly="true" value="0" /></td>
    </tr>
    <tr><td>New</td><td><input type="" id="newfromqty" name="newfromqty" class="sub" readonly="readonly" value="0" /></td><td><input type="text" id="newfromcts" name="newfromcts" class="sub" readonly="readonly" value="0"/></td><td><input type="text" id="newfromrte" name="newfromrte" class="sub" readonly="readonly" value="0" /></td><td><input type="text" id="newfromvalue" name="newfromvalue" class="sub" readonly="readonly" value="0"/></td></tr>
    </table>
    </td>
    
  </tr>
  </table>
  </td>
  </tr>
  
  <tr>
  <td>
  <table class="grid1">
  <tr>
        <th>TARGET</th>
        </tr>
        <tr>
        <td>
        <table class="grid1" id="data">
        <tr>
        <th>Sr No</th>
        <th>Box Type</th>
        <th>New</th>
        <th>Existing</th>
        <th>Packet Id</th>
        <th>CUR.QTY</th><th>CUR.CTS</th><th>CUR.AVG</th><th>CUR.VAL</th><th>TRF.QTY</th><th>TRF.CTS</th><th>TRF.AVG</th><th>TRF.VAL</th>
    <th>TOT.QTY</th><th>TOT.CTS</th><th>TOT.AVG</th><th>TOT.VAL</th>
    </tr>
        <tr>
        <td>1</td>
        <td>
        <label>
        <input type="text" name="boxtyp_1" list="characters" onchange="getpkt(this,'1');">
        <datalist id="characters">
        <%for(int i=0;i<bobTypList.size();i++){
        String boxprp=(String)bobTypList.get(i);%>
        <option value="<%=boxprp%>">
        <%}%>  
          </datalist>
          </label>
          </td>
          <td align="center"><input type="radio" name="stt_1" id="NEW_1" value="NEW" checked="checked" onclick="splitNewEXISTING('NEW','1')"/></td>
          <td align="center">
          <input type="radio" name="stt_1" id="EXISTING_1" value="EXISTING" onclick="splitNewEXISTING('EXISTING','1')"/></td>
          <td>
          <html:select  property="value(tovnm_1)" name="splitForm" styleId="tovnm_1" onchange="getBoxTo(this,'1');"   > 
         <html:option value="0">---select---</html:option>
        </html:select> 
          </td>
          <td><input type="text" id="toqty_1" name="toqty_1" class="sub" value="0" readonly="readonly" /></td>
    <td><input type="text" id="tocts_1" name="tocts_1" class="sub" value="0" readonly="readonly"/></td>
    <td><input type="text" id="torte_1" name="torte_1" class="sub" value="0" readonly="readonly"/></td>
    <td><input type="text" id="toavg_1" name="toavg_1" class="sub" value="0" readonly="readonly"/></td>
    <td><input type="text" id="addqty_1" name="addqty_1" class="sub" value="0"   /></td>
    <td><input type="text" id="addcts_1" name="addcts_1" class="sub" value="0"  /></td>
    <td><input type="text" id="addrte_1" name="addrte_1" class="sub" value="0"/></td>
    <td><input type="text" id="addavg_1" name="addavg_1" class="sub" readonly="readonly" /></td>
    <td><input type="text" id="totalqty_1" name="totalqty_1"  class="sub" readonly="readonly" /></td>
    <td><input type="text" id="totalcts_1" name="totalcts_1"  class="sub" readonly="readonly" /></td>
    <td><input type="text" id="totalrte_1" name="totalrte_1" class="sub" readonly="readonly" /></td>
    <td><input type="text" id="totalavg_1" name="totalavg_1" class="sub" readonly="readonly" /></td>
          </tr>
          </table>
        </td>
        <td>
        </td>
        </tr>
        <tr>
        <td align="center">
         <input type="button" name="AddPacket" value="ADD PACKETS" class="submit" id="calculate" style="width=100px;" onclick="addpacketrow();"/>
         <input type="button" name="calculate" value="CALCULATE" class="submit" id="calculate" style="width=100px;" onclick="getsplitcalculation();"/>
        <html:submit property="value(pb_lab)" value="SAVE CHANGES" styleClass="submit" onclick="validatesplitform();" styleId="splitesave" />
  </td></tr>

        </table>
        </td>
        </tr>
  </table>
  </td>
  </tr>
</html:form>
<%}else{%>
<tr><td valign="top" class="hedPg"> Sorry no result found</td></tr>
<%}}%>
  <tr><td>
<jsp:include page="/footer.jsp" />
</td></tr>
  </table>  
       <%@include file="../calendar.jsp"%>
  </body>
</html>