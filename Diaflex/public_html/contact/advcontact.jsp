<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page errorPage="../error_page.jsp" %>
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import="java.util.ArrayList,java.util.List,java.util.HashMap,java.sql.ResultSet,ft.com.*, java.util.Date, java.util.ArrayList, ft.com.dao.*"%>
<jsp:useBean id="info" class="ft.com.InfoMgr" scope="session" />
<jsp:useBean id="util" class="ft.com.JspUtil" scope="page" />
<%@taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>
<%@taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>Contact Search</title>
    <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/bse.css?v=<%=info.getJsVersion()%>"/>
    <script language="javaScript" type="text/javascript" src="<%=info.getReqUrl()%>/calendar/calendar.js"></script>
   <link href="<%=info.getReqUrl()%>/calendar/calendar.css" rel="stylesheet" type="text/css" />
   <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/bse.js?v=<%=info.getJsVersion()%> " > </script>
     <script language="javaScript" type="text/javascript" src="<%=info.getReqUrl()%>/scripts/ajax.js?v=<%=info.getJsVersion()%>"></script>
      <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/style.css?v=<%=info.getJsVersion()%>"/>
      <script type="text/javascript" src="<%=info.getReqUrl()%>/PopScripts/popup.js?v=<%=info.getJsVersion()%> " > </script>
   <script src="<%=info.getReqUrl()%>/PopScripts/jquery-1.2.6.min.js" type="text/javascript"></script>
    <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/general.css?v=<%=info.getJsVersion()%>"/>
        <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/assortScript.js?v=<%=info.getJsVersion()%> " > </script>
  </head>
        <%String logId=String.valueOf(info.getLogId());
        String onfocus="cook('"+logId+"')";
        %>
 <body onfocus="<%=onfocus%>" onload="MM_preloadImages('images/faunalogo.png')" topmargin="0" leftmargin="0" rightmargin="0" bottommargin="0">
 <input type="hidden" id="reqUrl" name="reqUrl" value="<%=info.getReqUrl()%>"/>
 <div id="backgroundPopup"></div>
<div id="popupDashPOP">
<table class="popUpTb">
 <tr>
 <td height="5%" id="close">&nbsp;&nbsp;&nbsp;<html:button property="value(close)" styleClass="submit"  onclick="MKEmptyPopup('SC');disablePopupASSFNL();" value="Close"  /> </td>
 <td height="5%" id="closereload" style="display:none">&nbsp;&nbsp;&nbsp;<html:button property="value(close)" styleClass="submit"  onclick="MKEmptyPopup('SC'); disablePopupASSFNL(); reportUploadclose('CNT');" value="Close"  /></td>
 </tr>
<tr><td valign="top" height="95%">
<iframe name="SC" class="frameStyle" width="500px" height="500px"   align="middle" frameborder="0">
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
<% if(request.getAttribute("MSG")!=null){%>
<tr><td height="15" valign="top" class="hedPg"><span class="redLabel"> <%=request.getAttribute("MSG")%></span></td></tr>
<%}%>
  <tr><td valign="top" class="hedPg">


  <bean:parameter id="reqNmeIdn" name="nmeIdn" value="0"/>
  <%
    ArrayList params = new ArrayList();
    ResultSet rs = null;
    HashMap dbinfo = info.getDmbsInfoLst();
     String cnt = (String)dbinfo.get("CNT");
    String repPath = (String)dbinfo.get("REP_PATH");
    String formName = "contact";
        DBUtil dbutil = new DBUtil();
    DBMgr db = new DBMgr(); 
    db.setCon(info.getCon());
    dbutil.setDb(db);
    dbutil.setInfo(info);
   db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));db.setDbUsr(info.getDbUsr());
    dbutil.setLogApplNm(info.getLoApplNm());	
    int accessidn=dbutil.updAccessLog(request,response,"Contact", "Contact");
    HashMap allFields = (HashMap)info.getFormFields();
    HashMap formFields = (allFields.get(formName) == null)?dbutil.getFormFields(formName):(HashMap)allFields.get(formName);
    UIForms uiForms = (UIForms)formFields.get("DAOS");
    ArrayList daos = uiForms.getFields();
        
    HashMap lovFormFlds = new HashMap();
    
    String pgTtl = uiForms.getFORM_TTL();
    pgTtl = pgTtl.replaceAll("~nme", util.nvl((String)info.getValue("NME"),""));
    int addRec = Integer.parseInt(util.nvl(uiForms.getREC(),"1"));

    String view = util.nvl(request.getParameter("view"));
    String search = util.nvl(request.getParameter("search"));
    String formAction = "/contact/nme.do?method=save";
    pgTtl += " Search Result ";
    
    if(view.equals("Y")) 
        formAction = "/contact/nme.do?method=save&nmeIdn="+reqNmeIdn;
  
    session.setAttribute("NMEIDN", reqNmeIdn);
  %>    
  <%
    ArrayList nmeSrchList = (info.getNmeSrchList() == null)?new ArrayList():(ArrayList)info.getNmeSrchList();
    ArrayList chkNmeIdnList = (info.getChkNmeIdnList() == null)?new ArrayList():(ArrayList)info.getChkNmeIdnList();
    ArrayList NonUsrList = ((ArrayList)request.getAttribute("NonUsrList")== null)?new ArrayList():(ArrayList)request.getAttribute("NonUsrList");
    HashMap contViewMap =((HashMap)new JspUtil().getFromMem(info.getMem_ip(),info.getMem_port(),info.getCnt()+"_contViewMap") == null)?new HashMap():(HashMap)new JspUtil().getFromMem(info.getMem_ip(),info.getMem_port(),info.getCnt()+"_contViewMap"); 
    ArrayList alphaList = (session.getAttribute("alphaList") == null)?new ArrayList():(ArrayList)session.getAttribute("alphaList");
    String srchby = util.nvl((String)session.getAttribute("srchby"));
    String cntmrg = util.nvl((String)session.getAttribute("cntmrg"));
    String nmestatus=util.nvl((String)info.getNmestatus());
    if(contViewMap==null)
    contViewMap = new HashMap();
    int nmeSrchListsz=nmeSrchList.size();
    int subnmeListsz;
    String selected = "checked=\"checked\"";
    formAction = info.getReqUrl() + "/contacts/nmeload.jsp";
  %>
  <form action="<%=formAction%>" method="post" >  
  <label id="loading"></label>
  <input type="button" id="BackToForm" onclick="goTo('<%=info.getReqUrl()%>//contact/advcontact.do?method=load&srch=<%=srchby%>','','')"  value="Back To Form"  class="submit"/>
  <%
    if(nmeSrchListsz > 0) {%>  
  <%
  HashMap gstDtl= (session.getAttribute("gstDtl") == null)?new HashMap():(HashMap)session.getAttribute("gstDtl");
 HashMap allPageDtl= (info.getPageDetails() == null)?new HashMap():(HashMap)info.getPageDetails();
 HashMap pageDtl=(HashMap)allPageDtl.get("CONTACT_SRCH");
                ArrayList pageList=new ArrayList();
                HashMap pageDtlMap=new HashMap();
                String fld_ttl="";
                String dflt_val="";
                String lov_qry="";
                String flg1="";
                String val_cond="",fld_nme="",fld_typ="";%>
  <%                
          pageList= ((ArrayList)pageDtl.get("BUTTON") == null)?new ArrayList():(ArrayList)pageDtl.get("BUTTON");
                    if(pageList!=null && pageList.size() >0){
      for(int j=0;j<pageList.size();j++){
      pageDtlMap=(HashMap)pageList.get(j);
      fld_nme="value("+(String)pageDtlMap.get("fld_nme")+")";
      fld_ttl=(String)pageDtlMap.get("fld_ttl");
      val_cond=(String)pageDtlMap.get("val_cond");
      fld_typ=(String)pageDtlMap.get("fld_typ");
     String fld_id = (String)pageDtlMap.get("dflt_val");
      if(fld_typ.equals("S")){ %>
<html:submit property="<%=fld_nme%>" value="<%=fld_ttl%>"  onclick="<%=val_cond%>" styleClass="submit" />
      <%}else if(fld_typ.equals("B")){%>
<html:button property="<%=fld_nme%>" value="<%=fld_ttl%>" styleId="<%=fld_id%>" onclick="<%=val_cond%>" styleClass="submit" />           
<%}else if(fld_typ.equals("HB")){%>
<input type="button" id="<%=fld_id%>" onclick="<%=val_cond%>"  value="<%=fld_ttl%>"  class="submit"/>
<%}}}
if(cntmrg.equals("Y")){
%>
<input type="button" onclick="goTocontactmerge('<%=info.getReqUrl()%>//contact/advcontact.do?method=contactmerge')"  value="Merge Contact"  class="submit"/>
<%}%>
<label id="loadinggst"></label>
<br>  
        <div style="padding:5px;">
         <% //// index of pages 
String pageNo =util.nvl(request.getParameter("iPageNo"));
if(!pageNo.equals("")){
info.setIPageNo(Integer.parseInt(pageNo));
}else{
info.setIPageNo(0);

}
List subnmeList = new ArrayList();
int iPageNo =info.getIPageNo();;
int recordShow =info.getPageCt();
int totalRecord = 0;
int startR=0;
int endR = 0;
totalRecord= nmeSrchListsz;
int iTotalSearchRecords = 10;
 if(iPageNo==0)
 startR=0;
else
 startR = (iPageNo-1)*recordShow;
endR = startR + recordShow;
if(totalRecord < endR){
    endR = totalRecord;
   
 }%>
 <span class="paginationLink"> <b>Showing</b> <%=startR+1%> - <%=endR%> from Total Result  <%=totalRecord%> </span>
 <%
     int iTotalPages=((int)(Math.ceil((double)totalRecord/recordShow)));
        int nextPg=0;
        int cNextPg=0;
        int p=0;
        int cPage=0;
        if(totalRecord!=0)
        {
        cPage=((int)(Math.ceil((double)endR/(iTotalSearchRecords*recordShow))));
        
        int prePageNo=(cPage*iTotalSearchRecords)-((iTotalSearchRecords-1)+iTotalSearchRecords);
        if((cPage*iTotalSearchRecords)-(iTotalSearchRecords)>0)
        { 
         %>
          <a href="advcontact.jsp?iPageNo=<%=prePageNo%>&cPageNo=<%=prePageNo%>&srch_recd=<%=recordShow%>" onclick="isPagination('<%=prePageNo%>','<%=prePageNo%>')" class="paginationLink"><img src="../images/left_errow.png" border="0"  alt="<< Previous"/> </a>
          
         <%
        }
        
        for(p=((cPage*iTotalSearchRecords)-(iTotalSearchRecords-1));p<=(cPage*iTotalSearchRecords);p++)
        {
          
          if(p==((iPageNo/recordShow)+1))
          {
           if(p==iPageNo){%>
            <a href="advcontact.jsp?iPageNo=<%=p%>&cPageNo='-1'&srch_recd=<%=recordShow%>" onclick="isPagination('<%=p%>','-1')" style="color:red" class="paginationLink"> <%=p%></a>
         <% }else{
          %>
           <a href="advcontact.jsp?iPageNo=<%=p%>&cPageNo='-1'&srch_recd=<%=recordShow%>" onclick="isPagination('<%=p%>','-1')" class="paginationLink"><%=p%></a>
          <% 
          }
          }
          else if(p<=iTotalPages)
          {  
          if(p==iPageNo){%>
            <a href="advcontact.jsp?iPageNo=<%=p%>&cPageNo='-1'&srch_recd=<%=recordShow%>" style="color:red" onclick="isPagination('<%=p%>','-1')" class="paginationLink"><%=p%></a>
         <% }else{
          %>
           <a href="advcontact.jsp?iPageNo=<%=p%>&cPageNo='-1'&srch_recd=<%=recordShow%>" onclick="isPagination('<%=p%>','-1')" class="paginationLink"><%=p%></a>
          <% 
          }}
        }
        if(iTotalPages>iTotalSearchRecords && p<iTotalPages)
        { 
         %>
         <a href="advcontact.jsp?iPageNo=<%=p%>&cPageNo=<%=p%>&srch_recd=<%=recordShow%>" onclick="isPagination('<%=p%>','<%=p%>')" class="paginationLink"><img src="../images/right_errow.png"  border="0" alt=">> Next" /></a> 
         <%
        }
        }
        %>
        <input type="hidden" id="pagination" name="pagination" value="NO" />
        <input type="hidden" id="iPageNo"  name="iPageNo"  />
        <input type="hidden" id="srch_recd"  name="srch_recd" value="<%=recordShow%>" />
        <input type="hidden" id="cPageNo"  name="cPageNo" />
        &nbsp;&nbsp;<b>Filter By</b> <a href="<%=info.getReqUrl()%>/contact/advcontact.do?method=loadSearch&namelike=">All</a> 
        <%for(int a=0;a<alphaList.size();a++){
        String letter=(String)alphaList.get(a);
        %>
        <a href="<%=info.getReqUrl()%>/contact/advcontact.do?method=loadSearch&namelike=<%=letter%>"><%=letter%></a> 
        <%}%></div>
  <table class="grid1" id="dataTable">
  <tr  id="DTL_1">
  <%
   subnmeList = nmeSrchList.subList(startR,endR);
   subnmeListsz=subnmeList.size();
  %>
  <th><input type="checkbox" name="checkAll_All" id="checkAll_All"  onclick="contactEmailIdAll('<%=subnmeListsz%>')"  />
</th>
<%if(cntmrg.equals("Y")){%>
  <th>Merge</th>
  <%}%>
  <th>Sr</th>
  
  <%
    for(int j=0; j < daos.size(); j++) {
            UIFormsFields dao = (UIFormsFields)daos.get(j);
            String lFld = dao.getFORM_FLD();
            String fldTtl = util.nvl(dao.getDSP_TTL(), lFld);
            
            %>
            
            <th><%=fldTtl%></th>
    <%}
      pageList= ((ArrayList)pageDtl.get("GSTIN") == null)?new ArrayList():(ArrayList)pageDtl.get("GSTIN");
      if(pageList!=null && pageList.size() >0){
      for(int j=0;j<pageList.size();j++){
      pageDtlMap=(HashMap)pageList.get(j);
      dflt_val=(String)pageDtlMap.get("dflt_val");
      fld_ttl=(String)pageDtlMap.get("fld_ttl");
      %>
      <th><%=fld_ttl%></th>
      <%}}
      pageList= ((ArrayList)pageDtl.get("VIEW") == null)?new ArrayList():(ArrayList)pageDtl.get("VIEW");
      if(pageList!=null && pageList.size() >0){
      for(int j=0;j<pageList.size();j++){
      pageDtlMap=(HashMap)pageList.get(j);
      dflt_val=(String)pageDtlMap.get("dflt_val");
      fld_ttl=(String)pageDtlMap.get("fld_ttl");
      if(dflt_val.equals("Y")){%>
      <th><%=fld_ttl%></th>
      <%}}}%>
     <th>Complete Profile</th>
     <%if(util.nvl((String)contViewMap.get("ASSPRTY")).equals("Y")){%>
     <th>Associated Parties</th>
     <%}%>
    <%if(util.nvl((String)contViewMap.get("REGMAIL")).equals("Y")){%>
     <th>Mails</th>
     <%}
      pageList= ((ArrayList)pageDtl.get("DELETE") == null)?new ArrayList():(ArrayList)pageDtl.get("DELETE");
      if(pageList!=null && pageList.size() >0){
      for(int j=0;j<pageList.size();j++){
      pageDtlMap=(HashMap)pageList.get(j);
      dflt_val=(String)pageDtlMap.get("dflt_val");
      fld_ttl=(String)pageDtlMap.get("fld_ttl");
      if(dflt_val.equals("Y") && nmestatus.equals("A")){%>
      <th><%=fld_ttl%></th>
      <%}}}
      if(nmestatus.equals("IA")){%>
      <th>Active</th>
      <%}%>
  </tr>
  <%    for(int i=0; i < subnmeListsz; i++) {
        MNme nme = (MNme)subnmeList.get(i);
        String nmeIdn = nme.getIdn();
        String target = "SC_"+nmeIdn;
        String partyLink = repPath+"/reports/rwservlet?"+cnt+"&report="+info.getRportUrl()+"\\reports\\brk_associated_parties.jsp&p_brkid="+nmeIdn+"&p_access="+accessidn ;
        String edtLnk = "<a href=\""+ info.getReqUrl() + "/contact/nme.do?method=load&nmeIdn="+ nmeIdn + "\">View / Edit</a>";
        
        String link = info.getReqUrl() + "/contact/advcontact.do?method=delete&nmeIdn="+ nmeIdn;
        String activelink = info.getReqUrl() + "/contact/advcontact.do?method=active&nmestatus=IA&nmeIdn="+ nmeIdn;
        String onclick = "return setDeleteConfirm('"+link+"')";
        String delLnk = "<a href=\""+link +" \" onclick=\""+onclick+"  \">Delete</a>";
        String activeLnk = "<a href=\""+activelink +" \">Active</a>";
        // String delLnk = "<a href=\""+ info.getReqUrl() + "/contact/nme.do?method=delete&nmeIdn="+ nmeIdn + "\" >Delete</a>";
        
        String addrLnk = "<a href=\""+ info.getReqUrl()+ "/contact/address.do?method=search&nmeIdn="+ nmeIdn + "\" target=\"_blank\">Address</a>";
        String relLnk = "<a href=\""+ info.getReqUrl()+ "/contact/nmerel.do?method=search&nmeIdn="+ nmeIdn +"\" target=\"_blank\">Terms</a>";
        String checkcontact ="check_"+i;
        String isSel="";
        String style="";
        if(chkNmeIdnList.contains(nmeIdn)){
        isSel=selected ;
        style="background-color:#FFC977;";
        }
        String mergePrp = "merge_0" ;
        String mergeISId = "merge_"+startR;
    %>
        <tr id="<%=target%>" style="<%=style%>">
        <td><input type="checkbox" id="<%=checkcontact%>" name="<%=checkcontact%>" <%=isSel%> onclick="contactEmailId('<%=nmeIdn%>', this);setBGColorOnCHK(this,'<%=target%>')" value="<%=nmeIdn%>" />
        <%if(cntmrg.equals("Y")){%>
        <td><input type="radio" name="<%=mergePrp%>" id="<%=mergeISId%>"  value="<%=nmeIdn%>" title="Master Idn"/></td>
        <%}%>
        <td><%=++startR%></td>
   
 
    <%    
        for(int j=0; j < daos.size(); j++) {
            UIFormsFields dao = (UIFormsFields)daos.get(j);
            String lFld = dao.getFORM_FLD();
            String lVal = (String)nme.getValue(lFld);
            String fldAlias = util.nvl(dao.getALIAS());
            String aliasFld = fldAlias.substring(fldAlias.indexOf(" ") + 1);
            if(aliasFld.length() > 0)
                lVal = util.nvl((String)nme.getValue(aliasFld));
           
            %>
            <td><%=lVal%></td>  
        <%}
       pageList= ((ArrayList)pageDtl.get("GSTIN") == null)?new ArrayList():(ArrayList)pageDtl.get("GSTIN");
       if(pageList!=null && pageList.size() >0){
       String fldNmId = "gstin_" + nmeIdn;
       String gstin=util.nvl((String)gstDtl.get(nmeIdn));
       String onChange = "return contactGstSave('"+nmeIdn+"','" + fldNmId+"')";
       %>
       <td><input type="text" name="<%=fldNmId%>" id="<%=fldNmId%>" onchange="<%=onChange%>" size="15" value="<%=gstin%>"/></td>
       <%}
        pageList= ((ArrayList)pageDtl.get("VIEW") == null)?new ArrayList():(ArrayList)pageDtl.get("VIEW");
       if(pageList!=null && pageList.size() >0){
       for(int j=0;j<pageList.size();j++){
       pageDtlMap=(HashMap)pageList.get(j);
       dflt_val=(String)pageDtlMap.get("dflt_val");
       fld_ttl=(String)pageDtlMap.get("fld_ttl");
       if(dflt_val.equals("Y")){%>
       <td><%=edtLnk%></td>
       <%}}}%>
        <td><a href="<%=info.getReqUrl()%>/contact/NmeReport.jsp?view=Y&nmeIdn=<%=nmeIdn%>" >Complete Profile</a></td>
       <%if(util.nvl((String)contViewMap.get("ASSPRTY")).equals("Y")){%>
        <td><A href="<%=partyLink%>" target="_blank">Associated Parties</a></td>
       <%}%>
        <%if(util.nvl((String)contViewMap.get("REGMAIL")).equals("Y")){%>
        <td>
        <%if(NonUsrList.contains(nmeIdn)){%>
        <A href="<%=info.getReqUrl()%>/webusermail?nmeIdn=<%=nmeIdn%>" target="_blank">Pending Registration Mails</a> 
        <%}%>
       
        </td>
         <%}
      pageList= ((ArrayList)pageDtl.get("DELETE") == null)?new ArrayList():(ArrayList)pageDtl.get("DELETE");
      if(pageList!=null && pageList.size() >0){
      for(int j=0;j<pageList.size();j++){
      pageDtlMap=(HashMap)pageList.get(j);
      dflt_val=(String)pageDtlMap.get("dflt_val");
      fld_ttl=(String)pageDtlMap.get("fld_ttl");
      if(dflt_val.equals("Y") && nmestatus.equals("A")){%>
       <td><%=delLnk%></td>
      <%}}}
      if(nmestatus.equals("IA")){%>
      <td><%=activeLnk%></td>
      <%}%>
      </tr>   
  <%}
  %>
  </table>
  <% 
     }
  %>
  </form>
  
 <%@include file="../calendar.jsp"%>
 </td></tr>
 <tr>
    <td><jsp:include page="/footer.jsp" /> </td>
    </tr>
 </table>
  </body>
</html>  
  