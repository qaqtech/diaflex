<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page errorPage="../error_page.jsp" %>
<%@ page contentType="text/html;charset=windows-1252"%>
<jsp:useBean id="info" class="ft.com.InfoMgr" scope="session" />
<jsp:useBean id="util" class="ft.com.JspUtil" scope="page" />
<%@ page import="java.util.ArrayList,java.util.HashMap,java.sql.ResultSet,ft.com.*, java.util.Date, java.util.ArrayList, ft.com.dao.*"%>
 
<%@taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>
<%@taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>Memo Deatils</title>
    <meta http-equiv="refresh" content="180;" />
    <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/bse.css?v=<%=info.getJsVersion()%>"/>
    <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/style.css?v=<%=info.getJsVersion()%>"/>
    <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/bse.js?v=<%=info.getJsVersion()%> " > </script>

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
    <td height="5" valign="top" class="greyline"><img src="<%=info.getReqUrl()%>/images/grey_linebig.png" width="685" height="5" align="right" /></td>
  </tr>
<tr>
<td valign="top" class="hedPg">
<table cellpadding="1" cellspacing="5"><tr><td valign="middle">
<img src="../images/line.jpg" border="0" /></td><td valign="middle"> <span class="pgHed">Memo Deatils</span> </td>
<td><b><span style="margin-left:30px;">Last Refresh :<%=util.getTime()%></span></b></td>
</tr></table>
</td>
</tr>
<%
ArrayList pktList=(ArrayList)request.getAttribute("pktList");
HashMap prp = info.getPrp();
ArrayList deptList=new ArrayList();
deptList = (ArrayList)prp.get("DEPT"+"V");
  HashMap allPageDtl= (info.getPageDetails() == null)?new HashMap():(HashMap)info.getPageDetails();
  HashMap pageDtl=(HashMap)allPageDtl.get("MEMO_DTL");
    ArrayList pageList=new ArrayList();
    HashMap pageDtlMap=new HashMap();
    String fld_nme="",fld_ttl="",val_cond="",dflt_val="",fld_typ="",form_nme="",lov_qry="";
    int maxmemo=Integer.parseInt(util.nvl((String)session.getAttribute("maxmemo"),"0"));
        HashMap dbinfo = info.getDmbsInfoLst();
        String cnt = (String)dbinfo.get("CNT");
%>
<tr>
<td valign="top" class="hedPg">
  <html:form action="report/memoDtl.do?method=load" method="post">
  <table class="datatable"><tr>
            <td valign="top" class="hedPg">Memo Type</td>
            <td><html:select property="typ"   styleId="typId" name="memoDetailForm" >
            <html:option value="" >--select--</html:option>
             <html:optionsCollection property="memoList" name="memoDetailForm" 
             label="dsc" value="memo_typ" />
             <%if(cnt.equals("hk")){%>
             <html:option value="WR" >Web Request</html:option>
             <%}%>
            </html:select></td>
             <%
        pageList=(ArrayList)pageDtl.get("STATUS");
         if(pageList!=null && pageList.size() >0){
           for(int j=0;j<pageList.size();j++){
             pageDtlMap=(HashMap)pageList.get(j);
             fld_nme="value("+(String)pageDtlMap.get("fld_nme")+")";
             fld_ttl=(String)pageDtlMap.get("fld_ttl");
             val_cond=(String)pageDtlMap.get("val_cond"); %>
            <td><%=fld_ttl%> </td>
      <%}}%>
           
            <%
            pageList=(ArrayList)pageDtl.get("SELECT");
             if(pageList!=null && pageList.size() >0){%>
             <td>
             <html:select property="value(stt)" styleId="stt" name="memoDetailForm" >
             <%
               for(int i=0;i<pageList.size();i++){
                 pageDtlMap=(HashMap)pageList.get(i);
                 fld_nme="value("+(String)pageDtlMap.get("fld_nme")+")";
                 fld_ttl=(String)pageDtlMap.get("fld_ttl");
                 val_cond=(String)pageDtlMap.get("val_cond");
                 dflt_val=(String)pageDtlMap.get("dflt_val");
                 %>
                <html:option value="<%=dflt_val%>"><%=fld_ttl%></html:option>
             <%}%>
            </html:select>
            </td>
            <%}%>
            <td>&nbsp;<html:submit property="value(submit)" styleClass="submit" value="Fetch" /> </td>
            </tr>
   </table>
   </html:form>
    </td></tr>
    <tr><td class="tdLayout">
   <%if(pktList!=null && pktList.size()>0){%>
   <table class="grid1" id="datatable">
   <tr>
   <%
    pageList=(ArrayList)pageDtl.get("DISPLAY");
            if(pageList!=null && pageList.size() >0){
            for(int j=0;j<pageList.size();j++){
            pageDtlMap=(HashMap)pageList.get(j);
            fld_ttl=(String)pageDtlMap.get("fld_ttl");
            %>
       <th><%=fld_ttl%></th>
    <%}}%>  
   </tr>
   <%for(int i=0;i<pktList.size();i++){
   HashMap pktDtl = (HashMap)pktList.get(i);
   %>
   <tr>
    <%
    pageList=(ArrayList)pageDtl.get("DISPLAY");
            if(pageList!=null && pageList.size() >0){
            for(int j=0;j<pageList.size();j++){
            pageDtlMap=(HashMap)pageList.get(j);
            fld_nme=(String)pageDtlMap.get("fld_nme");
            val_cond=(String)pageDtlMap.get("val_cond");
            if(fld_nme.equals("IDN")){
            int memo=Integer.parseInt((String)pktDtl.get(fld_nme));
            if(((maxmemo==0 && i==0) || i==0) && maxmemo!=memo){
            session.setAttribute("maxmemo", String.valueOf(memo));
            val_cond="color: red;";
            }else if(maxmemo<memo && maxmemo!=0 && maxmemo!=memo){
            val_cond="color: red;";
            }
            }
            if(fld_nme.equals("ISSQTY")){
            %>
            <td style="<%=val_cond%>">
            <A href="<%=info.getReqUrl()%>/report/memoDtl.do?method=pktDtlXL&memoIdn=<%=util.nvl((String)pktDtl.get("IDN"))%>" target="_blank"><%=util.nvl((String)pktDtl.get(fld_nme))%></a>           
            </td>
            <%}else{%>
       <td style="<%=val_cond%>"><%=util.nvl((String)pktDtl.get(fld_nme))%></td>
    <%}}}%>
    </tr>
   <%}%>
   </table>
   <%}%>
   </td></tr>
   <tr><td><jsp:include page="/footer.jsp" /> </td></tr>
   </table>
  </body>
</html>