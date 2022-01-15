<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page errorPage="../error_page.jsp" %>
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import="java.util.ArrayList,java.util.HashMap,java.sql.ResultSet,ft.com.*, java.util.Date, java.util.ArrayList, ft.com.dao.*"%>
<jsp:useBean id="info" class="ft.com.InfoMgr" scope="session" />
<jsp:useBean id="util" class="ft.com.JspUtil" scope="page" />
<%@taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>
<%@taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
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
<div id="popupContactASSFNL">
<table class="popUpTb">
 <tr><td height="5%">&nbsp;&nbsp;&nbsp;<html:button property="value(close)" styleClass="submit"  onclick="disablePopupASSFNL()" value="Close"  /> </td></tr>
<tr><td valign="top" height="95%">
<jsp:include page="/contact/contactXlFilter.jsp" />
</td></tr>
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
  <tr><td valign="top" class="hedPg">
  

  <bean:parameter id="reqNmeIdn" name="nmeIdn" value="0"/>
  <%
      DBUtil dbutil = new DBUtil();
    DBMgr db = new DBMgr(); 
    db.setCon(info.getCon());
    dbutil.setDb(db);
    dbutil.setInfo(info);
   db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));db.setDbUsr(info.getDbUsr());
    dbutil.setLogApplNm(info.getLoApplNm());	
  int accessidn=dbutil.updAccessLog(request,response,"Contact", "Contact");
    ArrayList params = new ArrayList();
    ResultSet rs = null;
    HashMap dbinfo = info.getDmbsInfoLst();
     String cnt = (String)dbinfo.get("CNT");
    String repPath = (String)dbinfo.get("REP_PATH");
    String formName = "contact";
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
    
    if(search.equals("Y"))      
        pgTtl += " Search Result ";
    
    if(view.equals("Y")) 
        formAction = "/contact/nme.do?method=save&nmeIdn="+reqNmeIdn;
  
    session.setAttribute("NMEIDN", reqNmeIdn);
  %>
  <label class="pgTtl"><%=pgTtl%></label>
  <%
    if(search.equals("")) {    
        String subMenu=info.getReqUrl() + "/contactSubMenu.jsp?nmeIdn="+reqNmeIdn ;
  %>
  
  <table class="submenu"><tr>
<%
    HashMap pageLinks = dbutil.getContactLinks();
    ArrayList pages = (ArrayList)pageLinks.get("PAGES");
    ArrayList links = (ArrayList)pageLinks.get("LINKS");
    for(int i=0; i < pages.size(); i++) {
        String lnk = (String)links.get(i)+"&nmeIdn="+reqNmeIdn;
        String pg = (String)pages.get(i);
    %>    
    <td>&nbsp;|&nbsp;</td><td><a href="<%=lnk%>"><%=pg%></a></td>
<%}
%>
</tr></table>
<%
  ArrayList errors = (ArrayList)request.getAttribute("errors");
  if(errors!=null && errors.size() >0){%>
  <table>
   <%for(int m=0;m<errors.size();m++){%>
   <tr><td class="tdLayout" valign="top">
    <label class="redLabel"> <%=errors.get(m)%></label>
   </td></tr>
   <%}%>
   </table>
  <%}%>
<html:form action="<%=formAction%>" method="post" >  
 <html:hidden property="value(btn)" styleId="btn" />
  <table class="grid2">
  
  <%
    for(int j=0; j < daos.size(); j++) {
            UIFormsFields dao = (UIFormsFields)daos.get(j);
            String lFld = dao.getFORM_FLD();
            String htmlFld = lFld;
            String fldNm = "value(" + htmlFld + ")";
            String fldTtl = util.nvl(dao.getDSP_TTL(), lFld);
            String fldTyp = util.nvl(dao.getFLD_TYP(), "T");
            String isReq =util.nvl(dao.getREQ_YN());
            String fldSz = util.nvl(dao.getFLD_SZ(),"10");
            
            String valCond = util.nvl(dao.getVAL_COND(), "");
    %>
        <tr>
            <td>
            <%
            if(isReq.equals("Y")){%>
            <span class="redTxt">*</span>
          <%  }
            %>
            <%=fldTtl%></td>
            <td>
   <%
            if(fldTyp.equals("FT")) {
                String ftFld = "value(" + htmlFld + "_fltr)";
                %>
                <html:select property="<%=ftFld%>">
                    <html:option value="C">Contains</html:option>
                    <html:option value="S">Starts Witd</html:option>
                    <html:option value="E">Ends Witd</html:option>
                </html:select>
            <%}
            if((fldTyp.equals("T")) || (fldTyp.equals("FT")) || (fldTyp.equals("DT"))) {
            %>
                <html:text property="<%=fldNm%>" styleId="<%=htmlFld%>"   size="<%=fldSz%>"/>
            <%} 
            if(fldTyp.equals("DT")) {%>
                <a href="#"  onClick="setYears(1900, <%=info.getCurrentYear()%>);showCalender(this, '<%=htmlFld%>');">
                <img src="<%=info.getReqUrl()%>/images/calender.png"></a>

            <%}
            if((fldTyp.equals("S")) || (fldTyp.equals("L"))) {
                String lovKey = lFld + "LOV";
                fldNm += "\" " + valCond;
                
                String lovQ = dao.getLOV_QRY(); //util.nvl((String)formFlds.get(lovKey));
                HashMap lovKV = new HashMap();
                ArrayList keys = new ArrayList();
                ArrayList vals = new ArrayList();
                if(lovFormFlds.get(lovKey) != null) {
                    lovKV = (HashMap)lovFormFlds.get(lovKey);
                } else {
                    if(lovQ.length() > 0) {
                        if(fldTyp.equals("S"))
                            lovKV = dbutil.getLOV(lovQ);
                        else    
                            lovKV = dbutil.getLOVList(lovQ);
                        lovFormFlds.put(lovKey, lovKV);
                    }
                }
                keys = (lovKV.get("K")!= null) ? (ArrayList)lovKV.get("K"):new ArrayList();
                vals = (lovKV.get("V")!= null) ? (ArrayList)lovKV.get("V"):new ArrayList();
                if(keys.size() == 0) {%>
                    <html:text property="<%=fldNm%>"/>
                <%} else {
                %>
                <html:select property="<%=fldNm%>" styleId="<%=htmlFld%>">
                    <html:option value="0">Select</html:option>
                    <%for(int a=0; a < keys.size(); a++) {
                        String lKey = (String)keys.get(a);
                        String ldspVal = (String)vals.get(a);%>
                        
                        <html:option value="<%=lKey%>"><%=ldspVal%></html:option>
                      <%}  
                    %>
                </html:select>
                <%}
            }
            
   %>
            </td>
            </tr>
    <%
    }    
  %>
  </table><br/>
  <%
    String resetLnk = info.getReqUrl()+"/contacts/contact.jsp";
  %>
  &nbsp;<html:submit property="search" value="Search" styleClass="submit"/>
  <%if(view.equals("Y")) {%>
  &nbsp;<html:submit property="save" value="Update" styleClass="submit"/>
  <%}%>
  
  &nbsp;<html:button property="addnew" value="Add New" onclick="return CheckValidFname('add')" styleClass="submit"/>
  &nbsp;<html:reset property="reset" value="Reset" styleClass="submit"/>
  &nbsp;<html:link href="<%=resetLnk%>" styleClass="submit">Clear All</html:link>
  
  </html:form>
  <% } %>
  <%
    ArrayList nmeSrchList = info.getNmeSrchList();
     ArrayList NonUsrList = (ArrayList)request.getAttribute("NonUsrList");
    HashMap contViewMap =((HashMap)new JspUtil().getFromMem(info.getMem_ip(),info.getMem_port(),info.getCnt()+"_contViewMap") == null)?new HashMap():(HashMap)new JspUtil().getFromMem(info.getMem_ip(),info.getMem_port(),info.getCnt()+"_contViewMap");
      if(contViewMap==null)
      contViewMap = new HashMap();
    formAction = info.getReqUrl() + "/contacts/nmeload.jsp";
  %>
  <form action="<%=formAction%>" method="post" >  
  
  <label class="pgTtl">Search Results Found : <%=nmeSrchList.size()%> record matching</label>
 
  <%
    if(nmeSrchList.size() > 0) {
%>  
  <%
        HashMap allPageDtl= (info.getPageDetails() == null)?new HashMap():(HashMap)info.getPageDetails();
        HashMap pageDtl=(HashMap)allPageDtl.get("CONTACT_SRCH");
                ArrayList pageList=new ArrayList();
                HashMap pageDtlMap=new HashMap();
                String fld_ttl="";
                String dflt_val="";
                String lov_qry="";
                String flg1="";
                String val_cond="",fld_nme="",fld_typ="";;
                %>
             <%   
                   pageList= ((ArrayList)pageDtl.get("LINK") == null)?new ArrayList():(ArrayList)pageDtl.get("LINK");
                    if(pageList!=null && pageList.size() >0){
                    for(int j=0;j<pageList.size();j++){
                        pageDtlMap=(HashMap)pageList.get(j);
                        fld_nme="value("+(String)pageDtlMap.get("fld_nme")+")";
                        val_cond=(String)pageDtlMap.get("val_cond");
                        fld_ttl=(String)pageDtlMap.get("fld_ttl");
                        val_cond=(String)pageDtlMap.get("val_cond");
                        fld_typ=(String)pageDtlMap.get("fld_typ");
                        flg1=(String)pageDtlMap.get("flg1");
                        %>
                         
                         &nbsp;&nbsp;<html:button property="<%=fld_nme%>" styleClass="submit"  onclick="<%=val_cond%>" value="<%=fld_ttl%>"  />
     <%           }}%>
<!--<label class="pgTtl" style="text-align:right;">
<a href="massmail.do?method=loadM" target="_blank" onclick="return validate_massMail('check_')" >Send Massmail</a></label>-->
<!--&nbsp;&nbsp;Create Excel &nbsp;
 <img src="../images/ico_file_excel.png" id="img" onclick="loadASSFNLPop('img')" />
-->
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
<%}}}%>
  <table class="grid1">
  <tr>
  <%
  int loop=nmeSrchList.size();
 
  %>
  <th><input type="checkbox" name="checkAll_All" id="checkAll_All"  onclick="checkALLContact('<%=loop%>')"  /></th>
  <th>Sr</th>
  
  <%
    for(int j=0; j < daos.size(); j++) {
            UIFormsFields dao = (UIFormsFields)daos.get(j);
            String lFld = dao.getFORM_FLD();
            String fldTtl = util.nvl(dao.getDSP_TTL(), lFld);
            
            %>
            
            <th><%=fldTtl%></th>
    <%}
  %>  
    <th>View/Edit</th>
     <th>Complete Profile</th>
     <%if(contViewMap.get("ASSPRTY").equals("Y")){%>
     <th>Associated Parties</th>
     <%}%>
    <%if(contViewMap.get("REGMAIL").equals("Y")){%>
     <th>Mails</th>
     <%}%>
      <th>Delete</th>
  </tr>
  <%
    for(int i=0; i < nmeSrchList.size(); i++) {
        MNme nme = (MNme)nmeSrchList.get(i);
        String nmeIdn = nme.getIdn();
        String partyLink = repPath+"/reports/rwservlet?"+cnt+"&report="+info.getRportUrl()+"\\reports\\brk_associated_parties.jsp&p_brkid="+nmeIdn+"&p_access="+accessidn ;
        String edtLnk = "<a href=\""+ info.getReqUrl() + "/contact/nme.do?method=load&nmeIdn="+ nmeIdn + "\">View / Edit</a>";
        
        String link = info.getReqUrl() + "/contact/nme.do?method=delete&nmeIdn="+ nmeIdn;
        String onclick = "return setDeleteConfirm('"+link+"')";
        String delLnk = "<a href=\""+link +" \" onclick=\""+onclick+"  \">Delete</a>";
        
        // String delLnk = "<a href=\""+ info.getReqUrl() + "/contact/nme.do?method=delete&nmeIdn="+ nmeIdn + "\" >Delete</a>";
        
        String addrLnk = "<a href=\""+ info.getReqUrl()+ "/contact/address.do?method=search&nmeIdn="+ nmeIdn + "\" target=\"_blank\">Address</a>";
        String relLnk = "<a href=\""+ info.getReqUrl()+ "/contact/nmerel.do?method=search&nmeIdn="+ nmeIdn +"\" target=\"_blank\">Terms</a>";
        String checkcontact ="check_"+i;
    %>
        <tr>
        <td><input type="checkbox" id="<%=checkcontact%>" name="<%=checkcontact%>" onclick="setmailid('<%=nmeIdn%>', this)" value="<%=nmeIdn%>" />
        
        <td><%=(i+1)%></td>
   
 
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
        <%}%>
        <td><%=edtLnk%></td>
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
         <%}%>
       
        <td><%=delLnk%></td>
     
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
  