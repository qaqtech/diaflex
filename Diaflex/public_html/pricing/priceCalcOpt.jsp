<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page errorPage="../error_page.jsp" %>
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic"%>

<%@ page import="java.util.ArrayList,java.util.HashMap,java.sql.ResultSet,ft.com.*, java.util.Date"%>
<jsp:useBean id="db" class="ft.com.DBMgr" scope="session" />
<jsp:useBean id="log" class="ft.com.LogMgr" scope="session" />
<jsp:useBean id="util" class="ft.com.DBUtil" scope="session" />
<jsp:useBean id="info" class="ft.com.InfoMgr" scope="session" />
<jsp:useBean id="mail" class="ft.com.GenMail" scope="page" />
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>Price Calculator</title>
    <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/bse.css?v=<%=info.getJsVersion()%>"/>
      <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/style.css?v=<%=info.getJsVersion()%>"/>
      <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/ajax.js?v=<%=info.getJsVersion()%> " > </script>
      <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/bse.js?v=<%=info.getJsVersion()%> " > </script>
  </head>
  <body>
  <html:form action="/pricing/PriceCalc.do?parameter=fetch" method="POST">
  <table width="100%"  border="0" align="center" cellpadding="0" cellspacing="0" class="mainbg">
  <tr>
    <td height="103" valign="top">
      <jsp:include page="/header.jsp" />
    </td>
  </tr>
 
  <tr>
    <td height="5" valign="top" class="greyline"><img src="../images/grey_linebig.png" width="685" height="5" align="right" /></td>
  </tr>
  <%
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
  <tr><td>
  <%
    int pIdx = 0 ;
  %>
  <table cellpadding="5" cellspacing="0" border="0" style="border:1px solid #666666">
    <logic:iterate id="prpLst" name="PriceCalc" property="PRPL">
      <bean:define id="mprp">
        <bean:write property="prp" name="prpLst" />
      </bean:define>
      <bean:define id="dtaTyp">
        <bean:write property="prpTyp" name="prpLst" />
      </bean:define>
        
    <% if (mprp.indexOf("KT") == -1) { %>
    <tr>
    <td style="border:1px solid #666666;background-color:#DDDDDD;font-family:Arial,Sans-Serif;font-size:10pt;font-weight:Bold;"> 
 
    <bean:write name="prpLst" property="prp"/>
    </td>
    <%}%>
    <% if (mprp.indexOf("KTCLW") > -1) {%>
        <tr><td style="border:1px solid #666666;font-family:Arial,Sans-Serif;font-size:10pt;font-weight:Bold;">KTS</td><td>
    <%}%>
    <% if (mprp.indexOf("KT") == -1) {%>
      <td style="border:1px solid #CCCCCC;font-family:Arial,Sans-Serif;font-size:10pt;">
    <%}%>
 <%
    String fldNm = "value("+ mprp + ")" ;
 %>
 <% if (mprp.indexOf("KT") > -1) { %>
    <bean:write name="prpLst" property="prp"/>&nbsp;
    <html:checkbox property="<%=fldNm%>" value="10" />
 <% } else {
  String listVal = "no"; 
  if(dtaTyp.equals("N")) {%>
    <html:text property="<%=fldNm%>" size="10"/>
  <%} else {
 %>
    <html:select property="<%=fldNm%>">
      <html:option value="">Select</html:option>
      <logic:iterate id="prpLst1" name="prpLst" property="prpL">
     
         <% if(mprp.indexOf("KT") > -1){%>
         <%}else{
            listVal = "yes";
         %>
             <bean:define id="prpSrtVal">
                             <bean:write property="val" name="prpLst1" />
              </bean:define>
   
   <!--      <html:radio property="<%=fldNm%>" value="<%=prpSrtVal%>"/>-->
      <html:option value="<%=prpSrtVal%>">
         <bean:write name="prpLst1" property="prt1" />
      </html:option>   
         <% } %>
      </logic:iterate>
    </html:select>
  <%}%>  
    
   <% } %>
  
       <% if(mprp.indexOf("KT") == -1){%>
    </td>
    
    </tr>
        <%}%>
    </logic:iterate>
    </table>
    <p><input type="submit" name="Submit" value="Submit"/>&nbsp;
        <input type="reset" name="Reset" value="Reset"/>&nbsp;
        <a href="<%=info.getReqUrl()%>/pricing/PriceCalc.do?parameter=setup">New Packet</a>
    </p>
    <hr/>
    <a name="#calc">Calculation Details</a>
<%
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
        <%}rs1.close();
     %>   
        </table>
        </td>
        </tr>
        </table>
    <%}
    session.setAttribute("PRC_CHK", "0");
%>
    </html:form>
  </body>
</html>