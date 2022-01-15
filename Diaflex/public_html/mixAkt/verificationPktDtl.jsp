<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page errorPage="../error_page.jsp" %>
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import="java.util.ArrayList,java.util.LinkedHashMap,java.util.Set,ft.com.dao.*, java.util.ArrayList,java.util.HashMap,java.sql.ResultSet,ft.com.*, java.util.Date, java.net.URLEncoder"%>

<jsp:useBean id="util" class="ft.com.JspUtil" scope="page" />
<jsp:useBean id="info" class="ft.com.InfoMgr" scope="session" />
<jsp:useBean id="gtMgr" class="ft.com.GtMgr" scope="session" />
<%@taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>

<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
      <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/bse.css?v=<%=info.getJsVersion()%>"/>
      <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/general.css?v=<%=info.getJsVersion()%>"/>
      <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/style.css?v=<%=info.getJsVersion()%>"/>
      <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/Validation.js?v=<%=info.getJsVersion()%> " > </script>
        <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/newCalScript.js?v=<%=info.getJsVersion()%> " > </script>
        <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/ajax.js?v=<%=info.getJsVersion()%> " > </script>

    <title>Assort Pending Packet Detail</title>
    
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
  <img src="../images/line.jpg" border="0" /></td><td valign="middle"> <span class="pgHed">Assort Pending Packet Detail</span> </td>
</tr></table>
  </td>
  </tr>
   <%
   String msg = (String)request.getAttribute("msg");
     if(msg!=null){%>
    <tr><td valign="top" class="tdLayout"><span class="redLabel"><%=msg%></span></td></tr>
   <%}%>
<tr>
  <td valign="top" class="tdLayout">
  <html:form action="mixAkt/assortPending.do?method=save" method="post" onsubmit="return sumbitFormConfirm('CHK_','0','Do you want to save changes','Please select packet','checkbox')" >
  <%
  HashMap mprp = info.getMprp();
  String lstNme = (String)gtMgr.getValue("lstNmeASPND");
 
  HashMap pktDtl = (HashMap)gtMgr.getValue(lstNme);
  ArrayList stockIdnLst =new ArrayList();
  LinkedHashMap stockList = new LinkedHashMap(pktDtl); 
  Set<String> keys = stockList.keySet();
        for(String key: keys){
       stockIdnLst.add(key);
        }
    gtMgr.setValue(lstNme+"_SEL", stockIdnLst);
  if(pktDtl!=null && pktDtl.size()>0){
  ArrayList boxViewLst = (ArrayList)session.getAttribute("boxViewLst");
  String prpFld = "value('BOX_ID')";
    String chksrvAll = "chksrvAllFrm('BOX_TYP','0')";
    
    %>
    <table cellpadding="2" cellspacing="2">
    <tr><td> 
   
    
        <html:submit property="value(market)" value="Transfer To Marketing" styleClass="submit"/>
        <html:submit property="value(chnageTyp)" value="Modify Box Type" styleClass="submit"/>

            <html:button property="value(backpnd)" value="Back To Assort Pending" onclick="directLoad('assortPending.do?method=load')"  styleClass="submit"/>

    </td></tr>
    <tr><td>
  <table class="grid1">
   <tr> <th><input type="checkbox" name="checkAll" id="checkAll" onclick="checkAllpage(this,'CHK_')" />TFMKT </th>
  <th> <html:select  property="<%=prpFld%>" styleId="BOX_ID" name="verificationForm" onchange="<%=chksrvAll%>"  style="width:100px"   > 
      <html:optionsCollection property="boxList" name="verificationForm" label="boxDesc" value="boxVal" />
     </html:select></th>
     <th>Box ID</th>
   <th>Sr</th>
   
        <th>Packet No.</th>
        <th>Qty</th>
        <th>Cts</th>
       <%for(int j=0; j < boxViewLst.size(); j++ ){
    String prp = (String)boxViewLst.get(j);
   String hdr = util.nvl((String)mprp.get(prp));
    String prpDsc = util.nvl((String)mprp.get(prp+"D"));
    if(hdr == null) {
        hdr = prp;
       }  %>
     <th title="<%=prpDsc%>"><%=prp%></th>
     <%}%> </tr>
 
   <%for(int i=0;i<stockIdnLst.size();i++){
  String stkIdn = (String)stockIdnLst.get(i);
  GtPktDtl stockPkt = (GtPktDtl)stockList.get(stkIdn);
  String fldId = "CHK_"+stkIdn;
  String fldVal = "value("+fldId+")";
   String boxTyp = "BOX_TYP"+stkIdn;
  String boxTypVal = "value("+boxTyp+")";
  String boxId = "BOX_ID"+stkIdn;
  String boxTxtId = "BOX_ID_TXT"+stkIdn;
  String boxTxtId1 = "BOX_ID_TXT1"+stkIdn;
  int cnt = i+1;
  %>
  <tr>
  <td><input type="checkbox" name="<%=fldId%>" id="<%=fldId%>" value="<%=stkIdn%>" /> </td>
  <td>
  <html:select property="<%=boxTypVal%>" styleId="boxTyp" name="verificationForm" style="width:100px">
<html:optionsCollection property="boxList" name="verificationForm" 
            label="boxDesc" value="boxVal" />
            
</html:select>
  </td>
  <td>
  <% 
              String setDown = "setDown('"+boxId+"', '"+boxTxtId1+"', '"+ boxTxtId +"',event)";
              String keyStr = "doCompletionBOXTyp('"+ boxTxtId +"', '" + boxId + "', 'mixAktAjaxAction.do?method=BoxIdns', event)";
        %>
                  <input type="text" name="<%=boxTxtId%>" id="<%=boxTxtId%>" class="sugBox"
                  onKeyUp="<%=keyStr%>"   onKeyDown="<%=setDown%>"  />
                  <input type="hidden" name="<%=boxTxtId1%>" id="<%=boxTxtId1%>" />
                <div style="position: absolute;width:300px;">
                  <select id="<%=boxId%>" name="<%=boxId%>" class="sugBoxDiv" 
                    style="display:none;:width:300px;"  
                    onKeyDown="<%=setDown%>" 
                    onDblClick="setVal('<%=boxId%>', '<%=boxTxtId1%>', '<%=boxTxtId%>', event);hideObj('<%=boxId%>')" 
                    onClick="setVal('<%=boxId%>', '<%=boxTxtId1%>','<%=boxTxtId%>', event);" 
                    size="10">
                  </select>
                </div>
  </td>
  <td><%=cnt%></td>
  <td><%=stockPkt.getValue("vnm")%></td>
  <td><%=stockPkt.getQty()%></td>
  <td><%=stockPkt.getCts()%></td>
  <%for(int j=0; j < boxViewLst.size(); j++ ){
    String prp = (String)boxViewLst.get(j);%>
    <td><%=stockPkt.getValue(prp)%></td>
  <%}%>
  
  </tr>
  <%}%>
 </table></td></tr></table>
 <% }else{%>
  sorry no result found.
  <%}%></html:form>
  </td></tr>
   <tr>
    <td><jsp:include page="/footer.jsp" /> </td>
    </tr>
  </table>
     </body>
</html>