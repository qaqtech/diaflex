<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
 
<%@ page errorPage="../error_page.jsp" %>
<%@ page contentType="text/html;charset=windows-1252"%>
<jsp:useBean id="util" class="ft.com.JspUtil" scope="page" />
<jsp:useBean id="info" class="ft.com.InfoMgr" scope="session" />
<%@ page import="java.util.ArrayList,java.util.HashMap,java.sql.ResultSet,ft.com.*, java.util.Date, java.util.ArrayList, ft.com.dao.*"%>
<%@taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>Memo Reports</title>
   <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/bse.css?v=<%=info.getJsVersion()%>"/>
      <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/style.css?v=<%=info.getJsVersion()%>"/>
      <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/ajax.js?v=<%=info.getJsVersion()%> " > </script>
      <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/bse.js?v=<%=info.getJsVersion()%> " > </script>
      <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/memoScript.js?v=<%=info.getJsVersion()%> " > </script>
      <script language="javaScript" type="text/javascript" src="<%=info.getReqUrl()%>/calendar/calendar.js"></script>
   <link href="<%=info.getReqUrl()%>/calendar/calendar.css" rel="stylesheet" type="text/css" />

  </head>
<%
ArrayList vwPrpLst = info.getVwPrpLst();
HashMap dbinfo = info.getDmbsInfoLst();
String cnt = (String)dbinfo.get("CNT");
 String method = util.nvl(request.getParameter("method"));
 String memoId = util.nvl((String)request.getAttribute("memoId"));
 String disCheckBoxPart = "display:none";
 String disSelectPart = "display:block";
 boolean isRead = false;
 
 if(method.equals("loadFmt")){
   disCheckBoxPart = "display:block";
   disSelectPart = "display:block";
    isRead = true;
 }
 
        DBUtil dbutil = new DBUtil();
        DBMgr db = new DBMgr(); 
        db.setCon(info.getCon());
        dbutil.setDb(db);
        dbutil.setInfo(info);
        db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));db.setDbUsr(info.getDbUsr());
        dbutil.setLogApplNm(info.getLoApplNm());
                     HashMap allPageDtl= (info.getPageDetails() == null)?new HashMap():(HashMap)info.getPageDetails();
            HashMap pageDtl=(HashMap)allPageDtl.get("MEMO_REPORT");
            if(pageDtl==null || pageDtl.size()==0){
            pageDtl=new HashMap();
            pageDtl=dbutil.pagedef("MEMO_REPORT");
            allPageDtl.put("MEMO_REPORT",pageDtl);
            }
            info.setPageDetails(allPageDtl);
            ArrayList pageList=new ArrayList();
            HashMap pageDtlMap=new HashMap();
            String fld_nme="",fld_ttl="",val_cond="",dflt_val="",lov_qry="",fld_typ="",form_nme="";
        int accessidn=Integer.parseInt(util.nvl((String)request.getAttribute("accessidn"),"0"));
        String logId=String.valueOf(info.getLogId());
        String onfocus="cook('"+logId+"')";
        String view = util.nvl(info.getViewForm());
        String onload="";
        if(view.equals("MP")){
        String url=info.getReqUrl()+"/marketing/memoReport.do?method=load&memoId="+memoId;
        onload="redirectToParentUrl('"+url+"')";
        }
        %>
 <body onfocus="<%=onfocus%>" onload="<%=onload%>" topmargin="0" leftmargin="0" rightmargin="0" bottommargin="0">
<html:form  action="marketing/memoReport.do?method=save" >
    <input type="hidden" id="reqUrlPage" name="reqUrl" value="<%=info.getReqUrl()%>"/>
<input type="hidden" name="webUrl" id="webUrl" value="<%=dbinfo.get("REP_URL")%>"/>
<input type="hidden" name="cnt" id="cnt" value="<%=cnt%>"/>
<input type="hidden" name="accessidn" id="accessidn" value="<%=accessidn%>"/>
<input type="hidden" name="repUrl" id="repUrl" value="<%=dbinfo.get("HOME_DIR")%>"/>
<input type="hidden" name="repPath" id="repPath" value="<%=dbinfo.get("REP_PATH")%>"/>
<html:hidden property="value(isUpdate)" name="memoReportForm" />
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
  <img src="../images/line.jpg" border="0" /></td><td valign="middle"> <span class="pgHed">Memo Reports</span> </td>
</tr></table>
  </td>
  </tr>
  <%if(request.getAttribute("msg")!=null){%>
  <tr>
  <td class="tdLayout"><span class="redLabel"> <%=request.getAttribute("msg")%></span></td>
  </tr>
  <%}%>
  <tr>
  <td valign="top" class="tdLayout">
  <html:hidden property="value(rlnId)" styleId="rln" name="memoReportForm" />
  <html:hidden property="value(formatVersion)" styleId="formatVersion1" onclick="" value="Exit" />
  <table><tr><td valign="top">
  <table class="grid1">
  <tr><th colspan="2">Reports Form</th></tr>
  <tr><td>Memo Id</td><td><html:text property="value(memoId)"  styleId="memoId"  onchange="getPrice(this)" /> </td></tr>
  <tr><td>Buyer</td><td><label id="byr"><bean:write property="value(byr)" name="memoReportForm"/></label></td></tr>
  <tr><td>Qty</td><td><html:text property="value(qty)" readonly="true" styleId="qty" /> </td></tr>
   <tr><td>Cts</td><td><html:text property="value(cts)" readonly="true" styleId="cts" /> </td></tr>
    <tr><td>Value</td><td><html:text property="value(vlu)" readonly="true" styleId="vlu" /> </td></tr>
    <tr><td>Memo Type</td><td><html:text property="value(typ)" readonly="true" styleId="typ" /> </td></tr>
    <%    pageList=(ArrayList)pageDtl.get("box_remark");
            if(pageList!=null && pageList.size() >0){
            for(int j=0;j<pageList.size();j++){
            pageDtlMap=(HashMap)pageList.get(j);
            fld_nme=(String)pageDtlMap.get("fld_nme");
            fld_ttl=(String)pageDtlMap.get("fld_ttl");
            fld_typ=(String)pageDtlMap.get("fld_typ");
            lov_qry=(String)pageDtlMap.get("lov_qry");
            dflt_val=(String)pageDtlMap.get("dflt_val");
            val_cond=(String)pageDtlMap.get("val_cond"); 
            val_cond=val_cond.replaceAll("URL",info.getReqUrl());
            if(dflt_val.equals("Y")){
            %>
             <tr><td>Box Remark</td><td><html:text property="value(p_boxrmk)"  styleId="p_boxrmk" /> </td></tr>
            <% } %>
              <% } %>
                <% } %>
    
  <tr><td>Memo Page</td><td>
  <html:select property="value(memoPag)" name="memoReportForm" styleId="memoPg">
  <html:optionsCollection property="memoPrintList" label="FORM_TTL" value="FORM_NME" />
  </html:select>
  </td></tr>
   <tr> <td></td><td><table cellpadding="0" cellspacing="0"><tr><td>
   <html:radio property="value(repTyp)" styleId="ALL" value="ALL" onclick="getmemoAllCurcts(this)" /> ALL</td>
   <td><html:radio property="value(repTyp)" styleId="CRT" value="CRT" onclick="getmemoAllCurcts(this)" /> Current</td>
   </tr></table></td>
   </tr>
 
  <tr><td> </td><td>
  
  <table cellpadding="5" cellspacing="2">
  
  <tr><td>Certificate No</td><td> <input type="radio" name="P_withoutcertno" checked="checked" id="P_withoutcertno" value="Y"> </td><td>Yes</td><td><input type="radio" name="P_withoutcertno" id="P_withoutcertno1" value="N"></td><td>No</td> </tr>

  <tr><td>Price</td><td> <input type="radio" name="p_withoutprice" checked="checked" id="p_withoutprice" value="Y"> </td><td>Yes</td><td><input type="radio" name="p_withoutprice" id="p_withoutprice2" value="N"></td><td>No</td></tr>
  
  <tr><td>Quot</td><td> <input type="radio" name="p_quot" id="p_quot" value="Y"> </td><td>Yes</td><td><input type="radio" name="p_quot" id="p_quot2" checked="checked" value="N"></td><td>No</td></tr>
 
   <tr><td>Packet No</td><td> <input type="radio" name="P_WIthoutPacketcode" checked="checked" id="P_WIthoutPacketcode" value="Y"> </td><td>Yes</td><td><input type="radio" name="P_WIthoutPacketcode" id="P_WIthoutPacketcode3" value="N"></td><td>No</td></tr>
 <%pageList=(ArrayList)pageDtl.get("RADIO");
            if(pageList!=null && pageList.size() >0){
            for(int j=0;j<pageList.size();j++){
            pageDtlMap=(HashMap)pageList.get(j);
            fld_nme=(String)pageDtlMap.get("fld_nme");
            fld_ttl=(String)pageDtlMap.get("fld_ttl");
            dflt_val=(String)pageDtlMap.get("dflt_val");
            String YChecked="";
            String NChecked="";

            if(dflt_val.equals("Y"))
            YChecked="checked=\"checked\"";
            else
             NChecked="checked=\"checked\"";
            %>
     <tr><td><%=fld_ttl%></td><td> <input type="radio" name="<%=fld_nme%>"  id="<%=fld_nme%>" value="Y" <%=YChecked%>> </td><td>Yes</td><td><input type="radio" name="<%=fld_nme%>" id="<%=fld_nme%>" value="N" <%=NChecked%>></td><td>No</td></tr>

    <% }}
    %>
  </table>
 
  </td></tr>
  <tr><td>Excel Format</td><td>
  <html:select property="value(mdl)" styleId="mdlLst" onchange="setFmt()">
  <html:optionsCollection value="mdl" property="memoXlList"  name="memoReportForm" label="mdl"/>
  </html:select>
  </tr>
  <tr>
  <td colspan="2" align="center"><html:button styleClass="submit" property="value(openWn)" value="Create Report" onclick="openReport()" /> 
  <button type="submit" class="submit" onclick="return validateReport()"  name="excel" >Download excel</button>
  <html:button styleClass="submit" property="value(redirect)" value="Redirect page" onclick="openRedirectPage()" />
  <%    pageList=(ArrayList)pageDtl.get("BUTTON");
            if(pageList!=null && pageList.size() >0){
            for(int j=0;j<pageList.size();j++){
            pageDtlMap=(HashMap)pageList.get(j);
            fld_nme=(String)pageDtlMap.get("fld_nme");
            fld_ttl=(String)pageDtlMap.get("fld_ttl");
            fld_typ=(String)pageDtlMap.get("fld_typ");
            lov_qry=(String)pageDtlMap.get("lov_qry");
            dflt_val=(String)pageDtlMap.get("dflt_val");
            val_cond=(String)pageDtlMap.get("val_cond"); 
            val_cond=val_cond.replaceAll("URL",info.getReqUrl());
            if(fld_typ.equals("S")){
            %>
    <html:submit property="<%=fld_nme%>" value="<%=fld_ttl%>" onclick="<%=val_cond%>" styleClass="submit"/>
    <%}else if(fld_typ.equals("B")){%>
    <button type="button" class="submit" onclick="<%=val_cond%>"  name="<%=fld_nme%>" ><%=fld_ttl%></button>
    <%}}}%>
      <button type="submit" class="submit" onclick="return validateReport();"  name="mail" />Mail Excel
  </td>
  </tr>
  
 
   <!--<tr><td><html:radio property="value(memoTyp)" styleId="memoALL" value="NN" /> ALL</td><td><html:radio property="value(memoTyp)" styleId="memoCRT" value="IS" /> Current</td></tr>-->
   
 

   
 
  </table>
  </td></tr>
  </table>
  </td>
  </tr>
  <tr>
    <td><jsp:include page="/footer.jsp" /> </td>
    </tr>
<tr>
<td>
<div style="display:none;">
<%
 String externalLnk=util.nvl((String)request.getAttribute("externalLnk"));
  System.out.println(externalLnk);
 if(externalLnk.length()>0){%>
<iframe name="loadhkurl" src="<%=externalLnk%>" id="loadhkurl" style="display:none;" align="middle" frameborder="0">
</iframe>
 <%}%>
</div>
</td>
</tr>
  </table>
  </html:form>
  </body>
</html>