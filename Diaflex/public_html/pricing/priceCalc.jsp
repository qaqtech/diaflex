<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page errorPage="../error_page.jsp" %>
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import="java.util.ArrayList,java.util.List,ft.com.dao.PrcPrpBean,java.util.HashMap,java.util.ArrayList,java.sql.ResultSet,ft.com.*, java.util.Date, java.util.Enumeration, java.util.logging.Level"%>
<jsp:useBean id="db" class="ft.com.DBMgr" scope="session" />
<jsp:useBean id="log" class="ft.com.LogMgr" scope="session" />
<jsp:useBean id="util" class="ft.com.DBUtil" scope="session" />
<jsp:useBean id="info" class="ft.com.InfoMgr" scope="session" />
<jsp:useBean id="mail" class="ft.com.GenMail" scope="page" />
<%@taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>
<%@taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>Price Calculator</title>
    <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/bse.css?v=<%=info.getJsVersion()%>"/>
      <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/style.css?v=<%=info.getJsVersion()%>"/>
      <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/ajax.js?v=<%=info.getJsVersion()%> " > </script>
      <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/bse_prc.js " > </script>
      <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/bse.js?v=<%=info.getJsVersion()%> " > </script>
      <script language="javaScript" type="text/javascript" src="<%=info.getReqUrl()%>/calendar/calendar.js"></script>
   <link href="<%=info.getReqUrl()%>/calendar/calendar.css" rel="stylesheet" type="text/css" />
  </head>
  <body>
  <table width="100%"  border="0" align="center" cellpadding="0" cellspacing="0"  class="mainbg">
  <tr>
    <td height="103" valign="top">
      <jsp:include page="/header.jsp" />
    </td>
  </tr>
 
  <tr>
    <td height="5" valign="top" class="greyline"><img src="../images/grey_linebig.png" width="685" height="5" align="right" /></td>
  </tr>
  <%
      util.updAccessLog(request,response,"Price Calculator", "Price Calculator");
  String prcChkIdn =  (session.getAttribute("PRC_CHK")==null)?"0":(String)session.getAttribute("PRC_CHK");
  String goTo = "<a href=\"#calc\">View Calculation</a>";
  //if(prcChkIdn.length() <= 1)
    goTo = "";
%>

  <tr>
  <td valign="top" class="hedPg">
  <table cellpadding="1" cellspacing="5"><tr><td valign="middle">
  <img src="../images/line.jpg" border="0" /></td><td valign="middle"> 
    <span class="pgHed">Price Calculator&nbsp;<%=goTo%></span> </td>
</tr></table>
  </td>
  </tr>
  </table>
  <html:form action="/pricing/PriceCalc.do?parameter=fetch" method="POST">
  <table width="100%"  border="0" align="center" cellpadding="0" cellspacing="0" >
  
  <tr><td>
    <table cellpadding="5" cellspacing="0" border="0" class="tbl_prc_calc">
      <tr>
        <td><strong>Copy Value From VNM</strong></td>
        <td><html:text property="value(vnm)"/></td>
        <td><input type="submit" name="gen" value="Fetch"  class="submit"/></td>
      <%
  ArrayList rolenmLst=(ArrayList)info.getRoleLst();
  String usrFlg=util.nvl((String)info.getUsrFlg());
  if(usrFlg.equals("SYS") || rolenmLst.contains("ADMIN")){
  %>
  <td><img src="../images/edit.jpg" title="Edit Properties Module" onclick="newWindow('<%=info.getReqUrl()%>/dynamicProperty.do?method=load&mdl=PRC_PRP&sname=prpLst&par=A')" border="0" width="15px" height="15px"/> </td>
  <%}%>
      </tr>
    </table>
 </td></tr>
 </table>
 </html:form>
 <%
 ArrayList prpList = (ArrayList)session.getAttribute("prpLst");
 if(prpList!=null && prpList.size() >0){
 %>
  <html:form action="/pricing/PriceCalc.do?parameter=generate" method="POST">
   <html:hidden property="value(Rap)" styleId="rap" />
    <html:hidden property="value(quot)" styleId="quot" />
  <html:hidden property="value(Idn)" />
  <table width="100%"  border="0" align="center" cellpadding="0" cellspacing="0" >
  <tr><td>
  <%
    int pIdx = 0 ;
  %>
  <table cellpadding="3" cellspacing="0" border="0" class="tbl_prc_calc">
    <%for(int i=0;i< prpList.size() ;i++){
    PrcPrpBean prcPrp = (PrcPrpBean)prpList.get(i);
    String lprp = prcPrp.getPrp();
    String lprpTyp = prcPrp.getPrpTyp();
    String lprpDsc = prcPrp.getPrt1();
    %>
      <%
        ++pIdx; 
        if(pIdx%4 == 1) {%>
          <tr>
        <%}
        if((pIdx%4 == 1) && (pIdx > 1)) {%>
          </tr>
        <%}
      %>
      
     
      
      
      <td class="th_prc_calc"><%=lprpDsc%></td>
      <td class="td_prc_calc">
      
 <%
    String fldNm = "value("+lprp+")" ;
 %>
 <% if (lprp.indexOf("KT") ==0) { %>
    <%=lprpDsc%>&nbsp;
    <html:checkbox property="<%=fldNm%>" value="10" name="PriceCalc" />
 <% } else {
  String listVal = "no"; 
   String onModifyChg ="";
   if(lprp.equals("RAP_DIS"))
   onModifyChg = "CalculateDiffExtraAmt(this)";
  if(lprpTyp.equals("N") || lprpTyp.equals("T") ) {%>
    <html:text property="<%=fldNm%>" size="10" styleId="<%=lprp%>" onchange="<%=onModifyChg%>" name="PriceCalc"/>
  <%}else if(lprpTyp.equals("D")){%>
  <html:text property="<%=fldNm%>" styleId="<%=lprp%>" size="10" name="PriceCalc"/>
    <img src="<%=info.getReqUrl()%>/images/calender.png" onClick="setYears(1900, '<%=info.getCurrentYear()%>' );showCalender(this, '<%=lprp%>');">
 <%} else {
 %>
    <html:select property="<%=fldNm%>" name="PriceCalc" >
      <html:option value="">Select</html:option>
      
     
         <% if(lprp.indexOf("KT") == 0){%>
         <%}else{
            listVal = "yes";
            List prpValLst = prcPrp.getPrpL();
            for(int j=0 ; j < prpValLst.size();j++){
            PrcPrpBean subPrp = (PrcPrpBean)prpValLst.get(j);
            String prpSrt =Integer.toString(subPrp.getSrt());
            String prpPrt1 = subPrp.getPrt1();
             String prpVal = subPrp.getVal();
         %>
            
   
  
      <html:option value="<%=prpVal%>">
         <%=prpPrt1%>
      </html:option>   
         <% } }%>
    
    </html:select>
  <%} 
  }
  %>  
      
      </td>  
    <%}%>
    </table></td>
    </tr>
    </table>
    <p style="margin-left:20px;">
    <input type="submit" name="submit" value="Submit" class="submit"/>&nbsp;
        <input type="submit" name="submit" value="Compare" class="submit"/>&nbsp;
        <input type="button" name="Clear" value="Clear" onclick="goTo('PriceCalc.do?parameter=setup','', '');" class="submit">&nbsp;
        <a href="<%=info.getReqUrl()%>/pricing/PriceCalc.do?parameter=setup">New Packet</a>
    </p>
    </html:form>
    <%}%>
    <hr/>
    <p style="margin-left:20px;"><a name="#calc">Calculation Details</a></p>
    <%
     HashMap grp_idnList = (HashMap)request.getAttribute("grp_idnList");
     ArrayList idnDtl = (ArrayList)request.getAttribute("idnDtl");
     ArrayList grp = (ArrayList)request.getAttribute("grp");
    ArrayList mstkidn = (ArrayList)session.getAttribute("mstkidn");
    String submit = (String)request.getAttribute("submit");
    %>
        
     <%if(idnDtl!=null && idnDtl.size()>0){%>
       <table border=0 cellpadding="5" cellspacing="1" style="border:dotted 1px #CCCCCC; margin-left:20px;">
        <tr><th>Idn</th><th>Rap</th><th>RapDis</th><th>Rte</th><th>Amount</th></tr>
              <% for(int i=0;i<idnDtl.size();i++){
               HashMap idnDtlList=(HashMap)idnDtl.get(i);
               %> 
               <tr>

             
               <td><%=idnDtlList.get("vnm")%></td>
               <td><%=idnDtlList.get("Rap")%></td>
               <td><%=idnDtlList.get("RapDis")%></td>
               <td><%=idnDtlList.get("Rte")%></td>
               <td><%=idnDtlList.get("Vlu")%></td>

               </tr>
                <%}%>
        </table>
    <%}%>    
       
        
        
        <%if(grp_idnList!=null && grp_idnList.size()>0){%>
        <p style="margin-left:20px;">Premium/Discount Detail</p>
        
       <table border=0 cellpadding="5" cellspacing="1" style="border:dotted 1px #000000; margin-left:20px;">
       
              <%if(submit.equals("Compare") || submit.equals("Apply Difference")){
              if(submit.equals("Compare")){
              %>
              <tr>
              <html:form action="/pricing/PriceCalc.do?parameter=ApplyDiffer"  method="POST">
              <p style="margin-left:20px;">
              <input type="submit" name="submit" value="Apply Difference" onclick="return confirmChangesMsg('Are You Sure You Want To Apply Difference') "  class="submit"/>
            </p>
             </html:form>
              </tr>
              <%}%>
               <tr>
                <td style="border:dotted 1px #CCCCCC">Idn</td>
                <%
                for(int j=0;j<idnDtl.size();j++){
                 HashMap idnDtlList=(HashMap)idnDtl.get(j);
                %>
                 <td style="border:dotted 1px #CCCCCC"><%=idnDtlList.get("vnm")%></td>
                <%}%>
                </tr>
             <% for(int j=0;j<grp.size();j++){
              String grpName=util.nvl((String)grp.get(j));
              %>
              <tr>
               <td style="border:dotted 1px #CCCCCC"><%=grpName%></td>
               <%for(int k=0;k<mstkidn.size();k++){
               String mstk_idn=util.nvl((String)mstkidn.get(k));
               %> 
               <td style="border:dotted 1px #CCCCCC"><%=util.nvl((String)grp_idnList.get(mstk_idn+"_"+grpName))%></td>
               <%}%>
               </tr>
               <%}}
               else if(submit.equals("Submit")){
               %>
               <tr>
               <%
               for(int j=0;j<grp.size();j++){
               String grpName=util.nvl((String)grp.get(j));
               %>
               <td style="border:dotted 1px #CCCCCC"><%=grpName%></td>
               <%}%>
               </tr>
               <tr>
               <%for(int j=0;j<grp.size();j++){
               String grpName=util.nvl((String)grp.get(j));
               for(int k=0;k<mstkidn.size();k++){
               String mstk_idn=util.nvl((String)mstkidn.get(k));%> 
              <td style="border:dotted 1px #CCCCCC"><%=util.nvl((String)grp_idnList.get(mstk_idn+"_"+grpName))%></td>
               <%}}%>
               </tr>
               <%}%>
        </table>
    <%}%>    
<!--<%
    String getPrcDtl = " select idn, cmp, rap_rte rap, trunc(((cmp/rap_rte)*100) - 100,2) rap_dis "+
      " from mstk where idn = ? " ;
    ArrayList ary = new ArrayList();
    ary.add(prcChkIdn);
    ResultSet rs = db.execSql(" Prc Dtl ", getPrcDtl, ary);
    if(rs.next()) {%>
        <table border=0 cellpadding="5" cellspacing="1" style="border:dotted 1px #CCCCCC">
        <tr><th>Idn</th><th>Rap</th><th>RapDis</th><th>Rte</th></tr>
        <tr>
            <td><%=rs.getString("idn")%></td>
            <td><%=rs.getString("rap")%></td>
            <td><%=rs.getString("rap_dis")%></td>
            <td><%=rs.getString("cmp")%></td>
            
        </tr>
        </table>
        <p>Premium/Discount Detail</p>
        <table border=0 cellpadding="5" cellspacing="1" style="border:dotted 1px #000000">
        
     <%   
        String prmDisDtl = " select pct, grp "+
         " from ITM_PRM_DIS_V where mstk_idn = ? order by grp_srt, sub_grp_srt ";
        ary = new ArrayList();
        ary.add(prcChkIdn);
        ResultSet rs1 = db.execSql(" Dis Dtl", prmDisDtl, ary);
        while(rs1.next()) {%>
            <tr><td style="border:dotted 1px #CCCCCC"><%=rs1.getString("grp")%></td>
                <td style="border:dotted 1px #CCCCCC"><%=rs1.getString("pct")%></td>
                
            </tr>    
        <%}
     %>   
        </table>
        </td>
        </tr>
        </table>
    <%}%>-->
    <%
    session.setAttribute("PRC_CHK", "0");
%>
    
  </body>
   <%@include file="../calendar.jsp"%>
</html>