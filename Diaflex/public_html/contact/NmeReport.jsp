<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page errorPage="../error_page.jsp" %>
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import="java.util.ArrayList,java.util.HashMap,java.sql.ResultSet,ft.com.*, java.util.Date, java.util.ArrayList, ft.com.dao.*"%>
<jsp:useBean id="info" class="ft.com.InfoMgr" scope="session" />
<jsp:useBean id="util" class="ft.com.JspUtil" scope="page" />
<jsp:useBean id="form" class="ft.com.FormsUtil" scope="session" />

<%@taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>Nme Report</title>
    <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/bse.css?v=<%=info.getJsVersion()%>"/>
      <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/style.css?v=<%=info.getJsVersion()%>"/>
        <script language="javaScript" type="text/javascript" src="<%=info.getReqUrl()%>/calendar/calendar.js"></script>
   <link href="<%=info.getReqUrl()%>/calendar/calendar.css" rel="stylesheet" type="text/css" />
   <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/general.css?v=<%=info.getJsVersion()%>"/>
   <script type="text/javascript" src="<%=info.getReqUrl()%>/PopScripts/popup.js?v=<%=info.getJsVersion()%> " > </script>
   <script src="<%=info.getReqUrl()%>/PopScripts/jquery-1.2.6.min.js" type="text/javascript"></script>
   <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/bse.js?v=<%=info.getJsVersion()%> " > </script> 
  </head>
        <%String logId=String.valueOf(info.getLogId());
        String onfocus="cook('"+logId+"')";
        %>
 <body onfocus="<%=onfocus%>" onload="MM_preloadImages('images/faunalogo.png')" topmargin="0" leftmargin="0" rightmargin="0" bottommargin="0">
<input type="hidden" id="reqUrl" name="reqUrl" value="<%=info.getReqUrl()%>"/> 

<div id="backgroundPopup"></div>
<div id="popupEditPOP1">
<table class="popUpTb">
<tr><td height="5%">&nbsp;&nbsp;&nbsp;
<button type="button" onclick="MKEmptyPopup('SC');disableEditPOP1();" class="submit">Close</button></td></tr>
<tr><td valign="top" height="95%">
<iframe name="SC" class="frameStyle" width="800px" height="800px"   align="middle" frameborder="0">
<jsp:include page="/emptyPg.jsp" />
</iframe></td></tr>
</table>
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
  <tr><td valign="top" style="padding:20px;">
  <bean:parameter id="reqNmeIdn" name="nmeIdn" value="0"/>
<%
    DBUtil dbutil = new DBUtil();
    DBMgr db = new DBMgr(); 
    db.setCon(info.getCon());
    dbutil.setDb(db);
    dbutil.setInfo(info);
    db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));db.setDbUsr(info.getDbUsr());
    dbutil.setLogApplNm(info.getLoApplNm());
    dbutil.updAccessLog(request,response,"Contact Report", "Contact Report");
   reqNmeIdn = util.nvl(request.getParameter("nmeIdn"), reqNmeIdn);
        HashMap allPageDtl= (info.getPageDetails() == null)?new HashMap():(HashMap)info.getPageDetails();
        HashMap pageDtl=(HashMap)allPageDtl.get("CONTACT_SRCH");
                ArrayList pageList=new ArrayList();
                HashMap pageDtlMap=new HashMap();
                String fld_ttl="";
                String dflt_val="";
                String lov_qry="";
                String flg1="";
                String val_cond="",fld_nme="",fld_typ="";              
    String pgTtl = " Systems Data for : "+ util.nvl((String)info.getValue("NME")) ;
    
%>
<br/>

<div><label class="pgTtl"><%=pgTtl%></label> as on <%=util.getDtTm()%></div>
<%
    
    ResultSet rs = null;
    
    ArrayList repForms = new ArrayList();
    repForms.add("contact");
    repForms.add("address");
    repForms.add("customercomm");
    repForms.add("customerterms");
    repForms.add("webaccess");
    repForms.add("nmedtl");
    repForms.add("nmegrpview");
    /*
    repForms.add("nmegrp");
    repForms.add("nmegrpdtl");
    */
    repForms.add("nmedocs");
    form.init(db, dbutil, info);
    
    for(int a=0; a < repForms.size(); a++) {
      String formName = (String)repForms.get(a);
      UIForms uiForms = form.getUIForms(formName);
      ArrayList daos = form.getDAOS(uiForms);
      String tblTtl = uiForms.getFORM_TTL();
      tblTtl = tblTtl.replaceAll("~nme", "");
      String tblNme = util.nvl(uiForms.getFRM_TBL());
      if(tblNme.length() > 0) {
        if(tblNme.indexOf(",") > -1) 
          tblNme = tblNme.substring(0, tblNme.indexOf(","));
        tblNme = tblNme+".";
      }  
       
      String bseFlds = tblNme+ "nme_idn idn, "+ tblNme + "nme_idn "; 
      bseFlds = "1 idn, 2 nme_idn";
      
      
      if(formName.equals("address"))
            bseFlds = "addr_idn  idn, 2 nme_idn ";
      
      if(formName.equals("nmedtl"))
            bseFlds = "nme_dtl_idn idn, 2 nme_idn ";
      
      if(formName.equals("customerterms"))
            bseFlds = "nmerel_idn idn, 2 nme_idn ";
      
      if(formName.equals("customercomm"))
            bseFlds = "nmerel_idn idn, 2 nme_idn ";
      
            
      String srchFields = form.getSrchFields(daos, bseFlds);
      
      String srchQ = " and nme_idn = ? ";
   
      ArrayList params = new ArrayList();
      params.add(reqNmeIdn);
      if(formName.equals("nmedocs") || formName.equals("customerterms"))
      srchQ += " and "+tblNme+"vld_dte is null ";
      
      ArrayList list = form.getSrchList(uiForms, tblNme, srchFields, srchQ, params, daos, "SRCH"); 
      if(list.size() > 0) {
  %>  
  <p><label class="pgTtl"><%=tblTtl%></label></p>
  <table class="grid1">
  <tr><td>Sr</td>
  <%
        pageList= ((ArrayList)pageDtl.get("CHANGE") == null)?new ArrayList():(ArrayList)pageDtl.get("CHANGE");
  if(pageList!=null && pageList.size() >0){
      for(int j=0;j<pageList.size();j++){
      pageDtlMap=(HashMap)pageList.get(j);
      dflt_val=(String)pageDtlMap.get("dflt_val");
      if(dflt_val.equals("Y")){
  %>
  <td>Edit</td>
  <%}}}
    for(int j=0; j < daos.size(); j++) {
            UIFormsFields dao = (UIFormsFields)daos.get(j);
            String lFld = dao.getFORM_FLD();
            String fldTtl = util.nvl(dao.getDSP_TTL(), lFld);%>
      <td><%=fldTtl%></td>
    <%}
  %>  
  </tr>
  <%
    for(int i=0; i < list.size(); i++) {
        
        GenDAO lDao = (GenDAO)list.get(i);
        String lIdn = lDao.getIdn();
        String nmeIdn = lDao.getNmeIdn();
        String DTL_IDN = lIdn+"_"+nmeIdn;
%>
     <tr id="<%=DTL_IDN%>">
     <td><%=(i+1)%></td>
     <%
             pageList= ((ArrayList)pageDtl.get("CHANGE") == null)?new ArrayList():(ArrayList)pageDtl.get("CHANGE");
      if(pageList!=null && pageList.size() >0){
      for(int j=0;j<pageList.size();j++){
      pageDtlMap=(HashMap)pageList.get(j);
      dflt_val=(String)pageDtlMap.get("dflt_val");
      if(dflt_val.equals("Y")){
      %>
     <td><a href="<%=info.getReqUrl()%>/contact/nmeEdit.do?method=load&idn=<%=lIdn%>&nmeIdn=<%=reqNmeIdn%>&formName=<%=formName%>" target="SC" title="Click Here To Edit Details" onclick="loadPOP1('<%=DTL_IDN%>')"><img src="../images/edit.jpg" border="0" width="15px" height="15px">
     </img></a>
     </td>   
     <% }}}  
        for(int j=0; j < daos.size(); j++) {
            UIFormsFields dao = (UIFormsFields)daos.get(j);
            String lFld = dao.getFORM_FLD();
            String lVal = (String)lDao.getValue(lFld);
            String aliasFld = util.nvl(dao.getALIAS());
            String fldTyp = util.nvl(dao.getFLD_TYP(), "T");
            if(aliasFld.length() > 0) {
                lVal = util.nvl((String)lDao.getValue(aliasFld.substring(aliasFld.lastIndexOf(" ")+1)));
            }
            if(fldTyp.equals("P")){
            %>
            <td>
            <input type="password" value="<%=lVal%>" style="border:0px;"></td> 
            <%}else if(lFld.equals("doc_nme")){
            String doclnk=util.nvl((String)lDao.getValue("doclnk"));%>
            <td><html:link href="<%=doclnk%>" target="_blank" > <%=lVal%></html:link></td>
            <%}else{%>
            <td><%=lVal%></td>  
        <%}}%>
        
     </tr>   
  <%}
  %>
  </table>
  <% 
     }
   }  
  %>
</td></tr></table>
   <%@include file="../calendar.jsp"%>
  </body>
</html>  
  
