<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
 
<%@ page errorPage="../error_page.jsp" %>
<%@ page contentType="text/html;charset=windows-1252"%>
<jsp:useBean id="util" class="ft.com.JspUtil" scope="page" />
<jsp:useBean id="info" class="ft.com.InfoMgr" scope="session" />
<%@ page import="java.util.ArrayList,java.util.HashMap,java.util.List, java.sql.ResultSet,ft.com.*, java.util.Collections , java.util.Date, java.util.ArrayList, ft.com.dao.*"%>
<%@taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<html>
  <head>
   <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>Bulk Property Update</title>
   <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/bse.css?v=<%=info.getJsVersion()%>"/>
      <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/style.css?v=<%=info.getJsVersion()%>"/>
      <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/bse.js?v=<%=info.getJsVersion()%> " > </script>
      <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/ajax.js?v=<%=info.getJsVersion()%> " > </script>
       <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/Validation.js " > </script>
         <script type="text/javascript" src="<%=info.getReqUrl()%>/PopScripts/popup.js?v=<%=info.getJsVersion()%> " > </script>
      <script src="<%=info.getReqUrl()%>/PopScripts/jquery-1.2.6.min.js" type="text/javascript"></script>
   </head>
        <%String logId=String.valueOf(info.getLogId());
        String onfocus="cook('"+logId+"')";
        %>
 <body onfocus="<%=onfocus%>" onload="MM_preloadImages('images/faunalogo.png')" topmargin="0" leftmargin="0" rightmargin="0" bottommargin="0">
<div id="backgroundPopup"></div>
<div id="popupContactSave" align="center" >
<img src="../images/loading_flame.gif" height="150px" width="150px" border="0" />
</div>
<%
HashMap allPageDtl= (info.getPageDetails() == null)?new HashMap():(HashMap)info.getPageDetails();
HashMap pageDtl=(HashMap)allPageDtl.get("BULK_PRP_UPD");
    ArrayList pageList=new ArrayList();
    HashMap pageDtlMap=new HashMap();
    String fld_nme="",fld_ttl="",val_cond="",dflt_val="",fld_typ="",form_nme="";
HashMap dbinfo = info.getDmbsInfoLst();
String cnt = (String)dbinfo.get("CNT");
HashMap mprp = info.getMprp();
ArrayList prpLst = (ArrayList)mprp.get("ALL");
List prpValList = new ArrayList();
ArrayList prpDspBlocked = info.getPageblockList();
String usrFlg=util.nvl((String)info.getUsrFlg());
%>
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
  <img src="../images/line.jpg" border="0" /></td><td valign="middle"> <span class="pgHed">Bulk Properties Update</span> </td>
</tr></table>
  </td>
  </tr>
  <%String msg = (String)request.getAttribute("msg");
  if(msg!=null){
  %>
  <tr><td valign="top"  class="tdLayout"><span class="redLabel"><%=msg%></span></td></tr>
  <%}%>
  <%String msg1 = (String)request.getAttribute("msg1");
  if(msg1!=null){
  %>
  <tr><td valign="top"  class="tdLayout"><span class="redLabel"><%=msg1%></span></td></tr>
  <%}%>
  <tr><td valign="top"  class="tdLayout">
   <html:form action="/marketing/StockPrpUpd.do?method=updatePrp" method="POST" onsubmit="return loading()">
           <html:hidden property="value(pktTyp)" name="stockPrpUpdForm" styleId="pktTyp"  />
           <logic:equal property="value(ISMIX)"  name="stockPrpUpdForm"  value="N" >
          <%for(int n=0 ; n < prpLst.size() ; n++)
            prpValList.add(prpLst.get(n)); %>
           </logic:equal>
            <logic:notEqual property="value(ISMIX)"  name="stockPrpUpdForm"  value="N" >
            <%pageList=(ArrayList)pageDtl.get("ALLOW");
            if(pageList!=null && pageList.size() >0){
            for(int j=0;j<pageList.size();j++){
            pageDtlMap=(HashMap)pageList.get(j);
            dflt_val=(String)pageDtlMap.get("dflt_val");
            prpValList.add(dflt_val.trim());
            %>
            <%}}%>
            </logic:notEqual>
        <%
        pageList=(ArrayList)pageDtl.get("RESTRICT");
            if(pageList!=null && pageList.size() >0){
            for(int j=0;j<pageList.size();j++){
            pageDtlMap=(HashMap)pageList.get(j);
            dflt_val=(String)pageDtlMap.get("dflt_val");
            prpValList.remove(dflt_val.trim());
        }}
        if(!usrFlg.equals("SYS")){
            pageList=(ArrayList)pageDtl.get("BLOCKPRPUSER");
            if(pageList!=null && pageList.size() >0){
            prpValList = new ArrayList();
            for(int j=0;j<pageList.size();j++){
            pageDtlMap=(HashMap)pageList.get(j);
            dflt_val=(String)pageDtlMap.get("dflt_val");
            prpValList.add(dflt_val.trim());
        }}
        prpValList.removeAll(prpDspBlocked);
        }
        Collections.sort(prpValList);%>
        <table class="grid1">
        <tr><th colspan="3">Properties Update</th></tr>
            <tr>
            <td>Propeties</td><td colspan="2" align="left">
            <html:select property="value(lprp)" styleId="prp" onchange="getPrpvalues(this)" >
            <html:option value="0">---select---</html:option>
            <%for(int i=0;i<prpValList.size();i++){
            String lprp = (String)prpValList.get(i);
            String lprpP = (String)mprp.get(lprp);
            String dsc= lprp+" "+lprpP;
            
            %>
            <html:option value="<%=lprp%>"> <%=dsc%></html:option>
            <%}%>
            </html:select>
            </td>
            </tr>
             <tr>
             <td colspan="3"><table><tr>
            <td>Memo<br/>
            <html:text property="value(memo)" styleClass="txtStyle"  size="30" styleId="memo"/>
            </td><td>Values<br/>
           <html:text property="value(memoValTN)" styleClass="txtStyle" size="30"  styleId="memoValTN"/>
           <html:select property="value(memoValC)"  styleId="memoValC" style="display:none" >
           </html:select>
            </td>
            </tr></table></td></tr>
             <tr>
             <td colspan="3"><table><tr>
            <td>Packets<br/>
            <html:textarea property="value(vnmLst)" cols="25" rows="8" styleId="vnm"/>
            </td><td>Values<br/>
           <html:textarea property="value(prpVal)" cols="25"  rows="8" styleId="prpVal"/>
            </td>
            </tr></table></td></tr>
             <%if(cnt.equalsIgnoreCase("kj")){%>
           <tr>
            <td>Status: </td><td>
            <html:text property="value(stt)"  styleId="stt"/>
            </td><td> Flag:
           <html:text property="value(flg)"  styleId="flg"/>
            </td>
            </tr>
        <%}%>
            <tr>
            <td colspan="3" align="center">
            <%
   
      pageList=(ArrayList)pageDtl.get("SUBMIT");
      if(pageList!=null && pageList.size() >0){
      for(int j=0;j<pageList.size();j++){
      pageDtlMap=(HashMap)pageList.get(j);
      fld_nme="value("+(String)pageDtlMap.get("fld_nme")+")";
      fld_ttl=(String)pageDtlMap.get("fld_ttl");
      val_cond=(String)pageDtlMap.get("val_cond"); %>
       <html:submit property="<%=fld_nme%>" value="<%=fld_ttl%>"  onclick="<%=val_cond%>" styleClass="submit" />
       <%}}%>
                 
            <!--<html:submit property="value(view)"  value="View" styleClass="submit"/>
            <html:submit property="value(prpUp)"  onclick="return validateUpd(this)" value="Properties Update" styleClass="submit"/>
            <html:submit property="value(sttUp)" value="Status Update" onclick="return validateStt(this)" styleClass="submit"/>
            <html:submit property="value(appDirPrice)" value="Apply Direct Price" onclick="return confirmChangesMsg('Are You Sure You Want To Apply Direct Price') "  styleClass="submit"/>-->
            </td>
           
            </tr>
        </table>
       
    </html:form></td></tr>
     <tr><td valign="top" class="tdLayout" height="10px"></td></tr>
     <%   String srchRef = (String)request.getAttribute("srchRefMsg");
     if(srchRef!=null){%>
     <tr><td valign="top" class="tdLayout"> <label class="redLabel" ><%=srchRef%></label></td></tr>
    <% }
     %>
       <tr><td valign="top" class="tdLayout">
    <%
    HashMap pktList = (HashMap)request.getAttribute("pktList");
    ArrayList pktStkIdnList = (ArrayList)request.getAttribute("pktStkIdnList");
    String selPrp = util.nvl((String)request.getAttribute("selPrp"));
    String view = util.nvl((String)request.getAttribute("view"));
    ArrayList vwPrpLst=new ArrayList();
    if(!view.equals("")){
    %>
    <table cellpadding="5" cellspacing="5">
    <%if(pktList!=null && pktList.size()>0){
    if(selPrp.equals(""))
    vwPrpLst = (ArrayList)info.getValue("prpUpdViewLst");
    else
    vwPrpLst =(ArrayList)request.getAttribute("viewSelPrp");%>
    <tr><td valign="top">
    <table class="grid1" align="left" id="dataTable">
<thead>
<tr>
<th><label title="Packet No">Packet No</label></th>
<%for(int j=0; j < vwPrpLst.size(); j++ ){
    String prp = (String)vwPrpLst.get(j);
     String hdr = (String)mprp.get(prp);
    String prpDsc = (String)mprp.get(prp+"D");
    if(hdr == null) {
        hdr = prp;
       }  
%>
<th title="<%=prpDsc%>"><%=hdr%></th>
<%}%>
</tr>
</thead>
<tbody>
<%


int colSpan = vwPrpLst.size()+3;
for(int m=0;m< pktStkIdnList.size();m++){ 
String stkIdn = (String)pktStkIdnList.get(m);
HashMap pktData = (HashMap)pktList.get(stkIdn);

%>
<tr id="<%=stkIdn%>">

<td><%=pktData.get("vnm")%></td>
<%
 for(int l=0;l<vwPrpLst.size();l++){
   String prp = (String)vwPrpLst.get(l);
    String prpValue =  util.nvl((String) pktData.get(prp));
    if(prpValue.equals("NA"))
     prpValue = "";
    String prpDsc = (String)mprp.get(prp+"D");
 
 %>
<td title="<%=prpDsc%>"><%=prpValue%></td>
<%}%>       
 
</tr>

<%}%>        
</tbody>
</table>
</td></tr>
    <%}else{%>
    <tr><td> Sorry no result found</td></tr>
    <%}%>
    </table>
    <%}%>
    </td></tr>
    
    <tr><td>
    <jsp:include page="/footer.jsp" />
    </td></tr></table>
  </body>
</html>
