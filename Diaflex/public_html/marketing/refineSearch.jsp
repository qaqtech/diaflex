<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
 
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page errorPage="../error_page.jsp" %>
<%@ page import="java.util.HashMap,java.util.ArrayList,java.util.List,java.util.ArrayList, java.sql.ResultSet"%>
<jsp:useBean id="util" class="ft.com.JspUtil" scope="page" />
<jsp:useBean id="info" class="ft.com.InfoMgr" scope="session" />
<jsp:useBean id="gtMgr" class="ft.com.GtMgr" scope="session" />

<%@taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>refResultTest</title>
<script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/ajax.js?v=<%=info.getJsVersion()%> " > </script>
          <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/bse.js?v=<%=info.getJsVersion()%> " > </script>
           <script src="<%=info.getReqUrl()%>/scripts/newCalScript.js" type="text/javascript"></script>
  </head>
 <body>
 <html:form action="/marketing/StockSearch.do?method=refSrchObj" method="POST">
 <div id="refsrchdv">
 <table cellpadding="2"  cellspacing="2" id="refineTblALL">
 <tr><td><button type="submit" class="submit" name="refine_srch" >Refine Search</button> </td></tr>
 <% 
 HashMap mprp = info.getMprp();
 HashMap prp = info.getPrp();
 String lstNme = (String)gtMgr.getValue("lstNme");
 HashMap srchPktDtl =  (HashMap)gtMgr.getValue(lstNme+"_SRCHLST");
 HashMap refPktDtl = (HashMap)gtMgr.getValue(lstNme+"_REF");
 
 ArrayList vwPrpLst =info.getRefPrpLst();
 for(int i=0;i<vwPrpLst.size();i++){
 String lprp = (String)vwPrpLst.get(i);
 String lprpDsc = (String)mprp.get(lprp+"D");
 String lprpTyp = (String)mprp.get(lprp+"T");
 ArrayList lprpSrt = (ArrayList)prp.get(lprp+"S");
  ArrayList lprpPrtVal = (ArrayList)prp.get(lprp+"P");
   ArrayList lprpValLst = (ArrayList)prp.get(lprp+"V");

 ArrayList lprpSrchValLst = (ArrayList)srchPktDtl.get(lprp);
 ArrayList lprpRefValLst = (ArrayList)refPktDtl.get(lprp);
 if(lprpRefValLst==null)
 lprpRefValLst = new ArrayList();
 %>
 <tr><td nowrap="nowrap"><b><%=lprpDsc%></b>
 <%if(lprpTyp.equals("C")){
 %>
 <input type="checkbox" id="<%=lprp%>_REFINE" value="<%=lprp%>"  onclick="refineselectall('<%=lprp%>','REFINE_<%=lprp%>')" checked="checked"/>&nbsp;
 <%}%>
 
 </td></tr>
 <%if(lprpTyp.equals("C")){%>
 
 <tr><td><table><tr>
 <%
 int cnt=0;
 for(int j=0;j< lprpValLst.size();j++){
 String val = (String)lprpValLst.get(j);
 boolean isTure=false;
 
if(lprpSrchValLst.indexOf(val)!=-1)
    isTure=true;
if(!isTure){
 val = (String)lprpPrtVal.get(j);
 if(lprpSrchValLst.indexOf(val)!=-1)
    isTure=true;
 
}
if(isTure){
 String isChecked = "";
 if(lprpRefValLst.contains(val))
  isChecked="checked=\"checked\"";
 cnt++;
 if(cnt==4){%>
 </tr><tr>
 <%cnt=0;}
 %>
 <td><input type="checkbox" id="REFINE_<%=lprp%>_<%=j%>" value="<%=val%>"  onclick="refineParam('<%=lprp%>',this,'C')" <%=isChecked%>  />&nbsp; <%=val%> </td>
 <%}}%>
 </tr></table></td></tr>
 <%}else{%>
 <tr>
 <td><table><tr><td>From : <input type="text" name="<%=lprp%>_MIN"  id="<%=lprp%>_MIN" class="txtStyle" onchange="refineParam('<%=lprp%>',this,'N')" size="6" value="<%=lprpRefValLst.get(0)%>" /> </td>
 <td>&nbsp; To : <input type="text" name="<%=lprp%>_MAX"  id="<%=lprp%>_MAX" class="txtStyle" size="6" onchange="refineParam('<%=lprp%>',this,'N')" value="<%=lprpRefValLst.get(1)%>" /> </td></tr></table> </td>
 </tr>
 <%}}%>
 </table></div></html:form>
 </body>
 </html>