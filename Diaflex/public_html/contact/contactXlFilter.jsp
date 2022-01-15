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
    <title>contactXlFilter</title>
    <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/bse.css?v=<%=info.getJsVersion()%>"/>
    <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/style.css?v=<%=info.getJsVersion()%>"/>
     <script language="javaScript" type="text/javascript" src="<%=info.getReqUrl()%>/scripts/ajax.js?v=<%=info.getJsVersion()%>"></script>
       <script language="javaScript" type="text/javascript" src="<%=info.getReqUrl()%>/calendar/calendar.js"></script>
   <link href="<%=info.getReqUrl()%>/calendar/calendar.css" rel="stylesheet" type="text/css" />
   <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/bse.js?v=<%=info.getJsVersion()%> " > </script>

  </head>
        <%String logId=String.valueOf(info.getLogId());
        String onfocus="cook('"+logId+"')";
        %>
 <body onfocus="<%=onfocus%>">
  <form action="searchContact.do?method=CNTExcel" method="post">
  <%
        DBUtil dbutil = new DBUtil();
    DBMgr db = new DBMgr(); 
    db.setCon(info.getCon());
    dbutil.setDb(db);
    dbutil.setInfo(info);
    db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));db.setDbUsr(info.getDbUsr());
    dbutil.setLogApplNm(info.getLoApplNm());
    ArrayList repForms = new ArrayList();
    dbutil.updAccessLog(request,response,"Contact Excel", "Contact Excel");
    dbutil.updAccessLog(request,response,"Contact Excel", "Contact Excel");
  String contXlFilter = "select chr_fr, dsc from rule_dtl a, mrule b where a.rule_idn = b.rule_idn and "
                       +" b.mdl = 'JFLEX' and b.nme_rule =  'CONT_XL_FLT' and a.til_dte is null order by a.srt_fr ";
  ResultSet rs = db.execSql("contactXl", contXlFilter, new ArrayList());
  while(rs.next()){
    repForms.add(util.nvl(rs.getString("chr_fr")));
  }
  rs.close();
 session.setAttribute("CONTXLFLT", repForms);
 ArrayList addContList = new ArrayList();
 String contAddXl = "select chr_fr, dsc from rule_dtl a, mrule b where a.rule_idn = b.rule_idn and "
                       +" b.mdl = 'JFLEX' and b.nme_rule = 'CONT_XL_ADD' and a.til_dte is null order by a.srt_fr ";
  rs = db.execSql("contactXl", contAddXl, new ArrayList());
  while(rs.next()){
    ArrayList addXl = new ArrayList();
    addXl.add(util.nvl(rs.getString("dsc")));
    addXl.add(util.nvl(rs.getString("chr_fr")));
    addContList.add(addXl);
  }
  rs.close();
   session.setAttribute("ADDCONDTL",addContList);
   
    ArrayList webroleList = new ArrayList();
    if(repForms.contains("mrole")){
 String webroleAddXl = "select role_idn , nvl(role_dsc , role_nm) rolDsc , srt , typ , decode(typ,null,'CH','RD') fldTyp , decode(typ,null,role_nm,typ) fldNme  from mrole where stt='A' and to_dte is null order by typ , srt";
  rs = db.execSql("webroleAddXl", webroleAddXl, new ArrayList());
  while(rs.next()){
    ArrayList addwebroleXl = new ArrayList();
    addwebroleXl.add(util.nvl(rs.getString("rolDsc")));
    addwebroleXl.add(util.nvl(rs.getString("fldNme")));
    webroleList.add(addwebroleXl);
  }
  rs.close();
   session.setAttribute("WEBROLEDTL",webroleList);
   }
   form.init(db, dbutil, info);
   %>
   <input type="submit" name="submit" class="submit" value="Create Excel"/>
   <table><tr>
   <%
    for(int a=0; a < repForms.size(); a++) {
      String formName = util.nvl((String)repForms.get(a));
      if(!formName.equals("nmedtl") && !formName.equals("mrole")){
      UIForms uiForms = form.getUIForms(formName);
      ArrayList daos = form.getDAOS(uiForms);
      String tblTtl = util.nvl(uiForms.getFORM_TTL());
      tblTtl = tblTtl.replaceAll("~nme", "");
      %>
   <td valign="top"> <ul> <li> <input type="checkbox" name="<%=formName%>" checked="checked" onclick="CheckBOXALLForm('0',this,'<%=formName%>')" value="yes" /> <B><%=tblTtl%> </b>
    <ul>
     <% for(int i=0 ; i < daos.size();i++){
            UIFormsFields dao     = (UIFormsFields) daos.get(i);
            String    lTblFld = dao.getTBL_FLD().toLowerCase();
            String    dspTtl = dao.getDSP_TTL();
            String    fldNme = formName+"_"+lTblFld;
     %>
      <li> <input type="checkbox" name="<%=fldNme%>" id="<%=fldNme%>" checked="checked"  value="yes" /> <%=dspTtl%></li>
      <%}%>
      </ul></li></ul></td>
    <% }else if(formName.equals("mrole")){
    if(webroleList!=null && webroleList.size()>0){
    %>
      <td valign="top"> <ul> <li> <input type="checkbox" name="<%=formName%>" checked="checked" onclick="CheckBOXALLForm('0',this,'<%=formName%>')" value="yes" /> <B>Web Role Details :</b>
    <ul>
   <% for(int i=0 ; i < webroleList.size();i++){
            ArrayList addwebroleXl = (ArrayList)webroleList.get(i);
            String    dspTtl = (String)addwebroleXl.get(0);
             String   lTblFld = (String)addwebroleXl.get(1);
            String    fldNme = formName+"_"+lTblFld;
   %>
      <li> <input type="checkbox" name="<%=fldNme%>" id="<%=fldNme%>" checked="checked"  value="yes" /> <%=dspTtl%></li>
   <%}%>
    </ul></li></ul></td>
    <%}          
    }else{
    if(addContList!=null && addContList.size()>0){
    %>
      <td valign="top"> <ul> <li> <input type="checkbox" name="<%=formName%>" checked="checked" onclick="CheckBOXALLForm('0',this,'<%=formName%>')" value="yes" /> <B>Additional Details :</b>
    <ul>
   <% for(int i=0 ; i < addContList.size();i++){
            ArrayList contDtl = (ArrayList)addContList.get(i);
            String    dspTtl = (String)contDtl.get(0);
             String   lTblFld = (String)contDtl.get(1);
            String    fldNme = formName+"_"+lTblFld;
   %>
      <li> <input type="checkbox" name="<%=fldNme%>" id="<%=fldNme%>" checked="checked"  value="yes" /> <%=dspTtl%></li>
   <%}%>
    </ul></li></ul></td>
    <%}}}
  %>
  </tr>
  
  </table>
 </form>
  </body>
</html>