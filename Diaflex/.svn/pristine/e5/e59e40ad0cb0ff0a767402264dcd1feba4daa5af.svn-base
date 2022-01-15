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
    <title>Contact Address</title>
      <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/ajax.js?v=<%=info.getJsVersion()%>"></script> 
      <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/bse.js?v=<%=info.getJsVersion()%>"></script> 
      <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/bse.css?v=<%=info.getJsVersion()%>"/>
      <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/style.css?v=<%=info.getJsVersion()%>"/>
       <script language="javaScript" type="text/javascript" src="<%=info.getReqUrl()%>/calendar/calendar.js"></script>
   <link href="<%=info.getReqUrl()%>/calendar/calendar.css" rel="stylesheet" type="text/css" />

  </head>
        <%String logId=String.valueOf(info.getLogId());
        String onfocus="cook('"+logId+"')";
        %>
 <body onfocus="<%=onfocus%>" onkeypress="return disableEnterKey(event);" >

  
<%

    String method = util.nvl((String)request.getAttribute("method"));
    ArrayList params = new ArrayList();
    ResultSet rs = null;
    
    String formName = "address";
    DBUtil dbutil = new DBUtil();
    DBMgr db = new DBMgr(); 
    db.setCon(info.getCon());
    dbutil.setDb(db);
    dbutil.setInfo(info);
    db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));db.setDbUsr(info.getDbUsr());
    dbutil.setLogApplNm(info.getLoApplNm());	
    HashMap allFields = (HashMap)info.getFormFields();
    HashMap formFields = (allFields.get(formName) == null)?dbutil.getFormFields(formName):(HashMap)allFields.get(formName);
    UIForms uiForms = (UIForms)formFields.get("DAOS");
    ArrayList daos = uiForms.getFields();
   
    HashMap lovFormFlds = new HashMap();
  
    
   
  %>
<table width="100%"  border="0" align="center" cellpadding="0" cellspacing="0" >
  
  
  <tr><td valign="top">
  

    
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
  </td></tr>
  <tr><td>
<html:form action="contact/address.do?method=save" method="post">  
<html:hidden property="nmeIdn" />
<html:hidden property="idn" />
 
  <table width="95%">
   <tr>
    <td>
  <div class="memo">
    <table width="100%" class="Orange" cellpadding="1"  cellspacing="1">
    <tr><th colspan="4" class="Orangeth" align="left">Address Details</th></tr>
    <tr>
  
  <%
   int count =0;
    for(int j=0; j < daos.size(); j++) {
            UIFormsFields dao = (UIFormsFields)daos.get(j);
            String lFld = dao.getFORM_FLD();
            String htmlFld = lFld;
            String fldNm = "value(" + htmlFld + ")";
            String fldTtl = util.nvl(dao.getDSP_TTL(), lFld);
            String fldTyp = util.nvl(dao.getFLD_TYP(), "T");
            String fldSz = util.nvl(dao.getFLD_SZ(),"10");
             String isReq =util.nvl(dao.getREQ_YN());
            String valCond = util.nvl(dao.getVAL_COND(), "");
            if(count==2){
             count=0;
             
             %></tr><tr>
          
           <% }
          count++;
           %>
  
    
        <td><%
            if(isReq.equals("Y")){%>
            <span class="redTxt">*</span>
          <%  }else{%>
          &nbsp;&nbsp;
          <%}%>
            <%=fldTtl%></td>
            <td>
   <%
            if(fldTyp.equals("FT")) {
                String ftFld = "value(" + htmlFld + "_fltr)";
                %>
                <html:select  property="<%=ftFld%>">
                    <html:option value="C">Contains</html:option>
                    <html:option value="S">Starts Witd</html:option>
                    <html:option value="E">Ends Witd</html:option>
                </html:select>
            <%}
            if((fldTyp.equals("T")) || (fldTyp.equals("FT"))) {
            %>
                <html:text property="<%=fldNm%>" size="<%=fldSz%>"/>
            <%} 
            
            if(fldTyp.equals("SB")) {
              String dspFld = htmlFld + "_dsp" ;
              String dspFldV = "value("+ dspFld + ")";
              String dspFldPop = htmlFld + "_dsp" + "_pop" ;
              String dspFldPopV = "value("+ dspFldPop + ")";
              String keyStr = "doAjaxList('"+ dspFld +"', '" + dspFldPop + "', '../ajaxAction.do?typ=city', event)";
              String setVal = "setVal('"+dspFldPop+"', '"+htmlFld+"', '"+ dspFld +"',event)";
              String setDown = "setDown('"+dspFldPop+"', '"+htmlFld+"', '"+ dspFld +"',event)";
              String onClick = setVal + ";hideObj('"+ dspFldPop + "')";
            %>
            <html:text property="<%=dspFldV%>" disabled="true" />
            
            <input type="text" name="<%=dspFldV%>_1" id="<%=dspFld%>" class="sugBox"
                  onKeyUp="<%=keyStr%>"  onKeyDown="<%=setDown%>" value="" />
                <html:hidden property="<%=fldNm%>" styleId="<%=htmlFld%>"/>
                <div style="position: fixed;">
                  <select id="<%=dspFldPop%>" name="<%=dspFldPopV%>"
                    style="display:none;300px;" 
                    class="sugBoxDiv"
                    onKeyDown="<%=setDown%>" 
                    onDblClick="<%=onClick%>" 
                    onClick="<%=setVal%>" 
                    size="10">
                  </select>
                </div>
            
            <%}
            if((fldTyp.equals("S")) || (fldTyp.equals("L"))) {
                String lovKey = lFld + "LOV";
                if(valCond.length() > 0)
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
                    <!--<html:option value="0">Select</html:option>-->
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
            
    <%
    }    
  %>
   </tr>
   <tr>
   <td colspan="4">
  <%if(!method.equals("edit")){%>
  <html:submit property="addnew" value="Add New" styleClass="submit"/>
  <%}%>
  <%if(!method.equals("reset")){%>
  <html:submit property="modify" value="Update" styleClass="submit"/>
  <%}%>
  <html:submit property="reset" value="Reset" styleClass="submit"/>
   </td>
   </tr>
  </table></div>
 </td></tr>
 
 
 </table>
 
 
  </html:form>
  </td></tr>
  <tr><td height="10">
  </td></tr>
  <tr><td>
  
  <%
    ArrayList addrList =  (ArrayList)request.getAttribute("addrList");
    if( addrList!=null && addrList.size() > 0) {
    
  %>  
  

  <table cellpadding="1" class="grid1" cellspacing="1">
  
  <tr><th>Sr</th>
  <%
    for(int j=0; j < daos.size(); j++) {
            UIFormsFields dao = (UIFormsFields)daos.get(j);
            String lFld = dao.getFORM_FLD();
            String fldTtl = util.nvl(dao.getDSP_TTL(), lFld);%>
            
            <th ><%=fldTtl%></th>
    <%}
  %>  
    <th >Action</th>
  </tr>
 
  <%
    for(int i=0; i < addrList.size(); i++) {%>
        <tr>
        <td><%=(i+1)%></td>
    <%
        //MAddr addr = (MAddr)addrList.get(i);
        GenDAO addr = (GenDAO)addrList.get(i);
        String addrIdn = addr.getIdn();
        String nmeIdn = addr.getNmeIdn();
        String edtLnk =  info.getReqUrl() + "/contact/address.do?method=edit&nmeIdn="+nmeIdn+"&addrIdn="+addrIdn ;
        String delLnk = info.getReqUrl() + "/contact/address.do?method=delete&nmeIdn="+nmeIdn+"&addrIdn="+addrIdn ;
        String onclick = "return setDeleteConfirm('"+delLnk+"')";
        for(int j=0; j < daos.size(); j++) {
            UIFormsFields dao = (UIFormsFields)daos.get(j);
            String lFld = dao.getFORM_FLD();
            String lVal = (String)addr.getValue(lFld);
            String aliasFld = util.nvl(dao.getALIAS());
            if(aliasFld.length() > 0) {
                lVal = util.nvl((String)addr.getValue(aliasFld.substring(aliasFld.indexOf(" ")+1)));
            }
        %>
            <td><%=lVal%></td>  
        <%}%>
        <td> <a href="<%=edtLnk%>">Edit</a>&nbsp;|&nbsp;<a href="<%=delLnk%>" onclick="<%=onclick%>" >Delete</a></td>
     </tr>   
  <%}
  %>
  </table>
  <% 
     }
  %>
 </td></tr></table>
  <%@include file="../calendar.jsp"%>
  </body>
</html>  
  