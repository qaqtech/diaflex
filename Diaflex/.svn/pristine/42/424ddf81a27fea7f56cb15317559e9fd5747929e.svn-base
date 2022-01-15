<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
 
<%@ page errorPage="../error_page.jsp" %>
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import="java.util.ArrayList,java.util.HashMap,java.sql.ResultSet,ft.com.*, java.util.Date, java.util.ArrayList, ft.com.dao.*"%>
<jsp:useBean id="util" class="ft.com.JspUtil" scope="page" />
<jsp:useBean id="info" class="ft.com.InfoMgr" scope="session" />
<%@taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>
<%@taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<html>
  <head>
   <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>Stock Tally Return</title>
    <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/bse.css?v=<%=info.getJsVersion()%>"/>
    <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/style.css?v=<%=info.getJsVersion()%>"/>
    <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/bse.js?v=<%=info.getJsVersion()%> " > </script>
    <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/ajax.js?v=<%=info.getJsVersion()%> " > </script>
       <script src="<%=info.getReqUrl()%>/scripts/spacecode.js?v=<%=info.getJsVersion()%>"></script>
       <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/dispose.js?v=<%=info.getJsVersion()%> " > </script>
  </head>
  <%
  String tally = util.nvl((String)request.getAttribute("TALLY"));
  HashMap sttMap = (HashMap)request.getAttribute("sttMap");
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
  <img src="../images/line.jpg" border="0" /></td><td valign="middle"> <span class="pgHed">Stock Tally Return</span> </td>
  </tr></table>
  </td>
  </tr>
   <%
   String msg = (String)request.getAttribute("msg");
   if(msg!=null){%>
    <tr><td valign="top" class="tdLayout"><span class="redLabel"><%=msg%></span></td></tr>
   <%}%>
   <%if(tally.equals("")){
  
   %>
   <tr><td valign="top" class="tdLayout">
   
    <html:form action="marketing/stockTally.do?method=view" method="post" >
    <html:hidden property="value(listname)" styleId="listname" value="pid" />
   <table class="grid1">
   <tr><th colspan="2">Select Criteria</th> </tr>
  <tr><td valign="top" colspan="2">
   <jsp:include page="/genericSrch.jsp"/>
  </td></tr>
  <%ArrayList stkCrt = (ArrayList)session.getAttribute("STK_TLY_CRT"); 
  if(stkCrt!=null && stkCrt.size() > 0){
  %>
  <tr><td>Stock Criteria</td><td>
  <html:select property="value(stkCrt)"  name="stockTallyForm" >
  <html:option value="0">--select--</html:option>
  <% for(int j=0 ; j < stkCrt.size();j++){
  ArrayList stkCrtDtl = (ArrayList)stkCrt.get(j);
 String stkCrtIdn = (String)stkCrtDtl.get(0);
 String stkDsc = (String)stkCrtDtl.get(1);
  %>
  <html:option value="<%=stkCrtIdn%>" ><%=stkDsc%></html:option>
  <%}%>
  </html:select>
  </td>
  </tr>
  <%}%>
  <tr><td>Status:</td><td>
  <html:select property="value(stt)"  name="stockTallyForm"   >
    
    <html:optionsCollection property="sttList" name="stockTallyForm"  label="FORM_TTL" value="FORM_NME" />
    </html:select>
  </td></tr>
   <tr><td><html:submit property="value(setCta)" styleClass="submit" value="Set Criteria"/></td> </tr>
  </table></html:form>
   </td></tr>
   <%}%>
   <html:form action="marketing/stockTally.do?method=Return" method="post" >
  <% if(sttMap!=null){
  String rtFlg = util.nvl((String)sttMap.get("RT"),"0");
  String isFlg = util.nvl((String)sttMap.get("IS"),"0");
  String nfFlg = util.nvl((String)sttMap.get("NF"),"0");
   String exFlg = util.nvl((String)sttMap.get("EX"),"0");
   String Total = (String)session.getAttribute("totalCnt");
   String vnmLnt = util.nvl((String)request.getAttribute("vnmLnt"));
   %>
    <tr><td valign="top" class="tdLayout">
    <table><tr><td>
    <span class="redLabel">Stock Tally Summary :
            Total =>  <A href="<%=info.getReqUrl()%>/marketing/stockTally.do?method=SearchResult&stt=ALL" target="pktDtl"><%=Total%></a>
    <%if(!rtFlg.equals("0")){%> &nbsp;|&nbsp;Scanned => <A href="<%=info.getReqUrl()%>/marketing/stockTally.do?method=SearchResult&stt=RT" target="pktDtl"><%=rtFlg%></a> , <%}%>
    <%if(!isFlg.equals("0")){%> &nbsp;|&nbsp;To be Scanned =><A href="<%=info.getReqUrl()%>/marketing/stockTally.do?method=SearchResult&stt=IS"  target="pktDtl"> <%=isFlg%></a> , <%}%>
    <br/>
    <%if(!exFlg.equals("0")){%> Extra  =><A href="<%=info.getReqUrl()%>/marketing/stockTally.do?method=SearchResult&stt=EX"  target="pktDtl"> <%=exFlg%></a><%}%>
    <%if(!nfFlg.equals("0")){%> &nbsp;|&nbsp;Not Found  =><%=nfFlg%><%}%>
    <br/>
     <%if(!vnmLnt.equals("")){%>Last No Of pakets given  for Return => <%=vnmLnt%> <%}%>
    </span>
    </td><td>   </td></tr></table>
    </td></tr>
   <%}%>
    <%if(!tally.equals("")){ 
    String dsc = util.nvl((String)request.getAttribute("srchCrt"));
    %>
   <tr><td  valign="top" class="tdLayout" >
   <table cellpadding="2" cellspacing="2" ><tr><td valign="top">
    
   <table>
   <tr><td align="left"><span class="txtLink" > Select Criteria : <bean:write property="tallyDsc" name="stockTallyForm" /> </span></td>
   <td align="left"> <html:submit property="value(close)" styleClass="submit" value="End / New"/></td>
   </tr>
   <tr><td colspan="2">
   <table>
   <tr><td>Packets</td><td><html:textarea property="value(vnm)" rows="8" cols="40" styleId="pid" name="stockTallyForm" />
   <label id="fldCtr" ></label>
   </td></tr>
   <tr><td colspan="2"><table><tr><td>
   <html:submit property="value(rtn)" styleClass="submit" value="Return"/></td><td>
    <html:submit property="value(reset)" styleClass="submit" value="Reset"/></td>
   <td>
          <%
     HashMap allPageDtl= (info.getPageDetails() == null)?new HashMap():(HashMap)info.getPageDetails();
     HashMap pageDtl=(HashMap)allPageDtl.get("STOCK_TALLY");
             ArrayList pageList=new ArrayList();
            pageList=(ArrayList)pageDtl.get("DIVCLIST");
             String fld_nme="",fld_ttl="",val_cond="",dflt_val="",fld_typ="",form_nme="",lov_qry="";
             if(pageList!=null && pageList.size() >0){%>
            <td> RFID Device: </td><td>
             <html:select property="value(dvcLst)" styleId="dvcLst" name="stockTallyForm"  >
             <%
               for(int j=0;j<pageList.size();j++){
                 HashMap pageDtlMap=(HashMap)pageList.get(j);
                 fld_nme="value("+(String)pageDtlMap.get("fld_nme")+")";
                 fld_ttl=(String)pageDtlMap.get("fld_ttl");
                 val_cond=(String)pageDtlMap.get("val_cond");
                 dflt_val=(String)pageDtlMap.get("dflt_val");
                 %>
                <html:option value="<%=dflt_val%>"><%=fld_ttl%></html:option>
             <%}%>
            </html:select>&nbsp;</td><td>
            <html:button property="value(rfScan)" value="RF ID Scan" onclick="doScan('pid','fldCtr','dvcLst')"  styleClass="submit" />
            </td>
            <%}%></tr></table>
   </td>
   <td>
                 <%if(util.nvl((String)info.getDmbsInfoLst().get("RFID_DNK"),"N").equals("Y")){%>
                 Continue Scan <input type="checkbox" id="contscan" title="Select To Continue Scan"></input>
                        <span style="margin:0px; padding:0px; display:none;">
                        <label id="summary" style="display:none;"></label>&nbsp;&nbsp;
                        </span>
                        Count&nbsp;&nbsp;:&nbsp;<label id="count"></label>&nbsp;&nbsp;
                        <span style="margin:0px; padding:0px; display:none;">
                        Notify&nbsp;&nbsp;:&nbsp;<label id="notify"></label>
                        <input type="checkbox" id="accumulateMode" title="Accumulate Mode"></input>
                        </span>
                        <html:button property="value(myButton)" value="Scan" styleId="myButton" onclick="scanRf()"  styleClass="submit" />
                        <span id="loadingrfid"></span>
                        <!--<html:button property="value(myButton2)" value="Dispose" styleId="myButton2" onclick="dispose()"  styleClass="submit" />
                        <html:button property="value(myButton4)" value="LED On" styleId="myButton4" onclick="waitOn('pid')"  styleClass="submit" />
                        <html:button property="value(myButton5)" value="LED Off" styleId="myButton5" onclick="waitOff()"  styleClass="submit" />
                        <html:button property="value(myButton6)" value="TestDll" styleId="myButton6" onclick="testDll()"  styleClass="submit" />-->
             <%}%>  
   </td>
   </tr>
   </table>
   </td>
   </tr>
   </table>
   </td><td>
   <iframe name="pktDtl"  height="800" width="600" frameborder="0">

   </iframe>
   </td></tr></table>
   </td></tr>
   <%}%>
   </html:form>
   
  </table>
  
  </body>
</html>