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
    <title>Name Group Details</title>
    <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/bse.css?v=<%=info.getJsVersion()%>"/>
    <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/ajax.js?v=<%=info.getJsVersion()%>" ></script>
      <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/bse.js?v=<%=info.getJsVersion()%>"></script> 
       <script language="javaScript" type="text/javascript" src="<%=info.getReqUrl()%>/calendar/calendar.js"></script>
   <link href="<%=info.getReqUrl()%>/calendar/calendar.css" rel="stylesheet" type="text/css" />

      <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/style.css?v=<%=info.getJsVersion()%>"/>
  </head>
        <%String logId=String.valueOf(info.getLogId());
        String onfocus="cook('"+logId+"')";
        %>
 <body onfocus="<%=onfocus%>"   onkeypress="return disableEnterKey(event);"  onload="MM_preloadImages('images/faunalogo.png')" topmargin="0" leftmargin="0" rightmargin="0" bottommargin="0">
<table width="100%"  border="0" align="center" cellpadding="0" cellspacing="0" >
 
 
 
  <tr><td valign="top">
  <bean:parameter id="reqIdn" name="grpIdn" value="0"/>
 <%

    ArrayList params = new ArrayList();
    ResultSet rs = null;
    
    String formName = "nmegrpdtl";
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
    
    String dtl=(String)request.getAttribute("dtlIdn");
    
    String pgTtl = uiForms.getFORM_TTL();
    pgTtl = pgTtl.replaceAll("~formName", formName);
    reqIdn = util.nvl(request.getParameter("idn"), reqIdn);
    int addRec = Integer.parseInt(util.nvl(uiForms.getREC(),"10"));
    String formAction = "/contact/nmegrpdtl.do?method=save&idn="+reqIdn;
    
    String reqNmeIdn = info.getNmeIdn();
  %>
 

    <div class="memo"  >

  <html:form action="<%=formAction%>" method="post">
  
  <html:hidden property="grpIdn"  />
    <table class="Orange" width="100%">
        <tr><th class="Orangeth">Sr</th>
        <th class="Orangeth">&nbsp;</th>
<%
        for(int j=0; j < daos.size(); j++) {
            UIFormsFields dao = (UIFormsFields)daos.get(j);
            String lFld = dao.getFORM_FLD();
            String fldTtl = util.nvl(dao.getDSP_TTL(), lFld);
          String isReq =util.nvl(dao.getREQ_YN());
%>
    <th class="Orangeth">
    <% if(isReq.equals("Y")){%>
            <span class="redTxt">*</span>
          <%  }else{%>
          &nbsp;&nbsp;
          <%}%>
    <%=fldTtl%></th>
<%}%>
    <th class="Orangeth">Action</th>
    </tr>
    <%
        int colCnt = daos.size() + 3;
        
        HashMap vldInvalid=new HashMap();
        ArrayList editList = new ArrayList();
        String getEditList = " select NME_GRP_DTL_IDN,decode(vld_dte,'','','red') color from nme_grp_dtl where nme_grp_idn = ?  order by frm_dte " ;
        params = new ArrayList();
        params.add(reqIdn);
        rs = db.execSql(" uiDsp getEditList ", getEditList, params);
        while(rs.next()) {
            editList.add(rs.getString("NME_GRP_DTL_IDN"));
            vldInvalid.put(rs.getString("NME_GRP_DTL_IDN"),util.nvl((rs.getString("color"))));
        }
        rs.close();
//        String getActivList = "select NME_GRP_DTL_IDN,flg from nme_grp_dtl where nme_grp_idn = ? and flg='DEL' order by frm_dte " ;
//          rs = db.execSql(" uiDsp getEditList ", getActivList, params);
//          while(rs.next()) {
//            editList.add(rs.getString(1));
//            flg=util.nvl(rs.getString("flg"));
       // }
      
       // rs.close();
        ArrayList dspOrder = new ArrayList();
        dspOrder.add("NEW");
        dspOrder.add("EXISTING");
        for(int dsp=0; dsp < dspOrder.size(); dsp++) {
            int sr = 0 ;
            String msg = (String)dspOrder.get(dsp);
            int lmt = editList.size();
            if(msg.equalsIgnoreCase("NEW"))
                lmt = addRec ;
            for(int i=0; i < lmt; i++) {
                int fldId = 0 ;
                String flg="";
                fldId = i+1 ;
                String cbox = "";
                if(!(msg.equalsIgnoreCase("NEW"))) {
                    fldId = Integer.parseInt((String)editList.get(i));
                }
               String color=util.nvl((String)vldInvalid.get(String.valueOf(fldId)));
               if(!color.equals(""))
               color = "color: "+color+";";
                if(sr == 0) {%>
                    <tr><td colspan="<%=colCnt%>" class="ttl"><%=msg%></td></tr>   
                <%}%>
                <tr style="<%=color%>">
                    <td><%=++sr%></td>
                    <td>&nbsp;
      <%
                if(!(msg.equalsIgnoreCase("NEW"))) {
                    String cboxPrp = "value(upd_"+ fldId + ")" ;
      %>
                    <html:checkbox property="<%=cboxPrp%>" value="upd_<%=fldId%>"/>
                <%}%>
                    </td>
      <%
        for(int j=0; j < daos.size(); j++) {
            UIFormsFields dao = (UIFormsFields)daos.get(j);
            String lFld = dao.getFORM_FLD();
            String htmlFld = lFld + "_" +  fldId;
            String fldNm = "value(" + htmlFld + ")";
            String fldTyp = util.nvl(dao.getFLD_TYP(), "T");
            String valCond = util.nvl(dao.getVAL_COND(), "");
            if(valCond.indexOf("~fldId") > -1)
                valCond = valCond.replaceAll("~fldId", Integer.toString(fldId));
                
            String fldSz = util.nvl(dao.getFLD_SZ(),"10");
        %>
        <td>
        <%
            if(fldTyp.equals("T")) {
            %>
                <html:text property="<%=fldNm%>" size="<%=fldSz%>" />
            <%}
            if(fldTyp.equals("SB")) {
              String dspFld = htmlFld + "_dsp" ;
              String dspFldV = "value("+ dspFld + ")";
              String dspFldPop = htmlFld + "_dsp" + "_pop" ;
              String dspFldPopV = "value("+ dspFldPop + ")";
              String setDown = "setDown('"+dspFldPop+"', '"+htmlFld+"', '"+ dspFld +"',event)";
              String keyStr = "doCompletion('"+ dspFld +"', '" + dspFldPop + "', '../ajaxAction.do?1=1', event)";
              String keyStrMobile = "doCompletionMobile('"+ dspFld +"', '" + dspFldPop + "', '../ajaxAction.do?1=1')";
              if(valCond.length() > 0)
                valCond = valCond + "\"" ;
            %>
            
              <html:text property="<%=dspFldV%>" disabled="true"/>
                  <input type="text" name="<%=dspFldV%>_1" id="<%=dspFld%>" class="sugBox"
                  onKeyUp="<%=keyStr%>"   onKeyDown="<%=setDown%>" value="" <%=valCond%> />
                <img src="../images/addrow.jpg" width="17" height="17" title="Click To get Names" onclick="<%=keyStrMobile%>">
                <html:hidden property="<%=fldNm%>" styleId="<%=htmlFld%>"/>
                <div style="position: absolute;">
                  <select id="<%=dspFldPop%>" name="<%=dspFldPopV%>" class="sugBoxDiv"
                    style="display:none;300px;"  
                    onKeyDown="<%=setDown%>" 
                    onDblClick="setVal('<%=dspFldPop%>', '<%=htmlFld%>', '<%=dspFld%>', event);hideObj('<%=dspFldPop%>')" 
                    onClick="setVal('<%=dspFldPop%>', '<%=htmlFld%>','<%=dspFld%>', event);" 
                    size="10">
                  </select>
                </div>
            <%}
            
            if((fldTyp.equals("S")) || (fldTyp.equals("L"))) {
                String lovKey = lFld + "LOV";
//                fldNm += "\" " + valCond;
                
                String lovQ = dao.getLOV_QRY(); //util.nvl((String)formFlds.get(lovKey));
                HashMap lovKV = new HashMap();
                ArrayList keys = new ArrayList();
                ArrayList vals = new ArrayList();
                if(lovFormFlds.get(lovKey) != null) {
                    lovKV = (HashMap)lovFormFlds.get(lovKey);
                } else {
                    if(lovQ.length() > 0) {
                        if(fldTyp.equals("S")) {
                            lovQ = lovQ.replace("~1", reqIdn);
                            lovKV = dbutil.getLOV(lovQ);
                        }    
                        else    
                            lovKV = dbutil.getLOVList(lovQ);
                        lovFormFlds.put(lovKey, lovKV);
                    }
                }
                keys = (lovKV.get("K")!= null) ? (ArrayList)lovKV.get("K"):new ArrayList();
                vals = (lovKV.get("V")!= null) ? (ArrayList)lovKV.get("V"):new ArrayList();
                if((msg.equalsIgnoreCase("EXISTING")) && (keys.size() == 0)) {%>
                    <html:text property="<%=fldNm%>"/>
                <%} else {
                %>  
                <html:select property="<%=fldNm%>" onchange="<%=valCond%>" styleId="<%=htmlFld%>">
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
        <%}
        
        if(msg.equalsIgnoreCase("NEW")) {%>
        <td>&nbsp;</td>
        <%}else {
        if(color.equals("")){
         String link= "/contact/nmegrpdtl.do?method=delete&delId="+ fldId + "&grpIdn=" + reqIdn;
           %>
         <td><html:link page="<%=link%>" >Delete</html:link></td>
        <%}else {
         String activlink= "/contact/nmegrpdtl.do?method=active&delId="+ fldId + "&grpIdn=" + reqIdn;
        %>
           <td><html:link page="<%=activlink%>" >Active</html:link></td>
            <%}}%>   
        </tr>  
    <%}
%>    
    <tr><td colspan="<%=colCnt%>" align="center">
<% if(msg.equalsIgnoreCase("NEW")) {%>    
    <html:submit property="addnew" value="Add New"  styleClass="submit"/>
<% } else { if(editList.size() > 0) {%>
    <html:submit property="modify" value="Save Changes" onclick="return validateUpdate()"  styleClass="submit"/>
<% } }%>
    </td></tr>
        
<%  }%>
    
    
    </table>
    
    </html:form></div></td></tr></table>
     <%@include file="../calendar.jsp"%>
  </body>
</html>
