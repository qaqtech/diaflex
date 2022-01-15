<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=windows-1252"%>
<jsp:useBean id="info" class="ft.com.InfoMgr" scope="session" />
<jsp:useBean id="util" class="ft.com.JspUtil" scope="page" />
<%@ page import="java.util.ArrayList,java.util.HashMap,java.util.Enumeration,org.apache.commons.collections.iterators.IteratorEnumeration, java.sql.ResultSet,ft.com.*, java.util.Date, java.util.ArrayList, ft.com.dao.*"%>
<%@taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>Box Selection</title>
    <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/bse.css?v=<%=info.getJsVersion()%>"/>
    <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/general.css?v=<%=info.getJsVersion()%>"/>
     <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/style.css?v=<%=info.getJsVersion()%>"/>
      <script src="../scripts/bse.js" type="text/javascript"></script>
       <!--<script src="<%=info.getReqUrl()%>/scripts/assortScript.js?v=<%=info.getJsVersion()%>" type="text/javascript"></script>-->
       <!--<script src="<%=info.getReqUrl()%>/scripts/ajax.js?v=<%=info.getJsVersion()%>" type="text/javascript"></script>-->
       <script src="<%=info.getReqUrl()%>/scripts/box.js?v=<%=info.getJsVersion()%>" type="text/javascript"></script>
       <script src="<%=info.getReqUrl()%>/scripts/priceChange.js?v=<%=info.getJsVersion()%>" type="text/javascript"></script>
             <script src="../scripts/bse.js" type="text/javascript"></script>
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
    <td height="5" valign="top" class="greyline" ><img src="../images/grey_linebig.png" width="685" height="5" align="right" /></td>
  </tr>
  <tr>
  <td valign="top" class="hedPg">
  <table cellpadding="1" cellspacing="5"><tr><td valign="middle">
  <img src="../images/line.jpg" border="0" /></td><td valign="middle"> <span class="pgHed">Single To Box</span> </td>
  </tr></table>
  </td>
  </tr>
  <%

   String vw = (String)request.getAttribute("PageVW");
   String vnmNtFnd = (String)request.getAttribute("vnmNotFnd");
     if(vnmNtFnd!=null){%>
    <tr><td valign="top" class="tdLayout"><span class="redLabel"><%=vnmNtFnd%></span></td></tr>
   <%}%>
    <%
   String msg = (String)request.getAttribute("msg");
     if(msg!=null){%>
    <tr><td valign="top" class="tdLayout"><span class="redLabel"><%=msg%></span></td></tr>
   <%}%>
   <tr><td valign="top" class="tdLayout">
    <html:form action="box/boxSelection.do?method=SetBoxTyp" method="post">
   <html:submit property="value(allbox)" value="ALLOCATE TO BOXES"  styleClass="submit" />
   </html:form>
   </td></tr>
    <tr><td valign="top" class="tdLayout" height="10"></td></tr>
  <tr>
  <td valign="top" class="tdLayout" >
  <html:form action="box/boxSelection.do?method=view" method="post">
   
  
  
 <table  class="grid1">
  <!--<tr><th colspan="2">BOX TYPE</th><th colspan="3">VNM</th></tr>
  <tr><td colspan="2">
  <%
            ArrayList listtable=(ArrayList)session.getAttribute("boxnme");
          
  %>
    <select name="boxnme" id="boxnme" style="width:95px; border:1px solid #c51212;" >
    <option value="">---Select---</option>
    <%
    for(int i=0;i<listtable.size();i++){
    String boxname = (String)listtable.get(i);
    %>
    <option value="<%=boxname%>"> <%=boxname%></option>
    <% 
    }
    %>
    </select>
  
  
  </td>
  
  <td valign="top" colspan="3" >
    <html:textarea property="value(vnmLst)" rows="2" name="boxSelectionForm" /></td>
    </tr> -->  
    <%ArrayList srchPrp =info.getGncPrpLst();
    if(srchPrp!=null && srchPrp.size()>1){
    %>
   <tr><th>Generic Search </th><th>Packets</th></tr>  
   <tr>
  <td valign="top">
  <table>
   <tr>
  <td>
   <jsp:include page="/genericSrch.jsp"/>
   <%
  ArrayList rolenmLst=(ArrayList)info.getRoleLst();
  String usrFlg=util.nvl((String)info.getUsrFlg());
  if(usrFlg.equals("SYS") || rolenmLst.contains("ADMIN")){
  %>
  <img src="../images/edit.jpg" title="Edit Properties Module" onclick="newWindow('<%=info.getReqUrl()%>/dynamicProperty.do?method=load&mdl=SINGLE_TO_BOX&sname=BOXGNCSrch&par=GENERIC')" border="0" width="15px" height="15px"/> 
  <%}%>
   </td></tr>
  </table>
  </td>
  <td valign="top">
  <html:textarea property="value(vnmLst)" rows="10" name="boxSelectionForm" />
  </td>
  </tr>
  <%}else{%>
   <tr><th>Generic Search</th></tr> 
  <tr>
  <td valign="top">
  <table>
   <tr>
  <td>
   <jsp:include page="/genericSrch.jsp"/></td></tr>
  </table>
  </td>
  </tr>
  <tr>
   <td valign="top">
   &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Packets&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
 
  <html:textarea property="value(vnmLst)" rows="10" name="boxSelectionForm" />
  </td>
  </tr>
  <%}%>
 
  <tr><td colspan="5" align="center"><html:submit property="view" value="VIEW" styleClass="submit"  /> 
  <html:submit property="viewAll" value="VIEW ALL" styleClass="submit"/> </td>
  </tr>
  
  </table>
 
 
  </html:form>
  </td></tr>
  
   
  <html:form action="box/boxSelection.do?method=save" method="post"   >
  
  <%
  String msg1 = (String)request.getAttribute("page");
  if(msg1!=null){
   if(msg1.equals("MIX")){ 
  %>
  <tr>
  <td valign="top" class="tdLayout">
  
  <table>
   <%
   String view = (String)request.getAttribute("view");
   String ctwt=null;
   if(view!=null){
    ArrayList prpDspBlocked = info.getPageblockList();
    ArrayList stockList = (ArrayList)session.getAttribute("StockList");
    if(stockList!=null && stockList.size()>0){
    ArrayList vwPrpLst = (ArrayList)session.getAttribute("BoxViewLst");
    HashMap mprp = info.getMprp();
    int sr = 0;
   %>
   
  <tr>
  <td>
  <table><tr><td>
  <html:submit property="value(pb_lab)" value="SAVE CHANGES" styleClass="submit" />
  <html:button property="value(reset)" value="RESET" onclick="this.form.reset()" styleClass="submit" />
  <html:submit property="value(excel)" value="CREATE EXCEL"  styleClass="submit" />
  <%
  ArrayList rolenmLst=(ArrayList)info.getRoleLst();
  String usrFlg=util.nvl((String)info.getUsrFlg());
  if(usrFlg.equals("SYS") || rolenmLst.contains("ADMIN")){
  %>
  &nbsp;&nbsp;<img src="../images/edit.jpg" title="Edit Properties Module" onclick="newWindow('<%=info.getReqUrl()%>/dynamicProperty.do?method=load&mdl=BOX_VIEW&sname=BoxViewLst&par=A')" border="0" width="15px" height="15px"/>
  <%}%>
  </td>
  </tr></table>
  </td></tr>
  
  <tr>
  <td>
  <table class="grid1">
  <tr>
        
        <th>BOX NAME</th>
        <th>TOTAL</th>
        
    </tr>
    <tr>
    <td>
    <%
    HashMap boxList = (HashMap)session.getAttribute("boxlst");
    Enumeration e1=new IteratorEnumeration(boxList.keySet().iterator());
    int size1 = boxList.size();
    %>
    <select name="bnme" id="bnme" style="width:95px; border:1px solid #c51212;" onchange="getBoxselection(this)";>
    <option value="">---Select---</option>
    <%
    for(int k=0;k<size1;k++)
    {
    String boxValue=(String)e1.nextElement();
    String boxnam=(String)boxList.get(boxValue);
    %>
    <option value="<%=boxValue%>"> <%=boxnam%></option>
    <% 
    }
    %>
    </select> 
    </td>
    <td>
    <table class="grid1">
    <tr><th></th><th>QTY</th><th>CTS</th><th>AVG VALUE</th><th>TOTAL VALUE</th><th>AVG SIZE</th></tr>
    <tr><td>Available</td>
    <td><span id="avlqty"></span></td>
    <td><span id="avlcts"></span></td>
    <td><span id="avlrte"></span></td>
    <td><span id="avlval"></span></td>
    <td><span id="avlsize"></span></td>
    </tr>
    <tr><td>On Hand</td><td><input type="text" id="qty" name="qty" class="sub" value="" readonly="readonly"/></td>
    <td><input type="text" id="cts" name="cts" class="sub" value="" readonly="readonly" /></td>
    <td><input type="text" id="rte" name="rte" class="sub" value="" readonly="readonly"/></td>
    <td><input type="text" id="val" name="val" class="sub" value="" readonly="readonly"/></td>
    <td><input type="text" id="size" name="size" class="sub" value="" readonly="readonly"/></td>
    </tr>
    <tr><td>New</td><td><input type="text" name="newqty" id="newqty" class="sub" readonly="readonly" /></td><td><input type="text" name="newcts" id="newcts" class="sub" readonly="readonly"/></td><td><input type="text" name="newrte" id="newrte" class="sub" readonly="readonly" /></td><td><input type="text" name="newvalue" id="newvalue" class="sub" readonly="readonly" /></td><td><input type="text" name="newsize" id="newsize" class="sub" readonly="readonly" /></td></tr>
    <tr><td>User</td><td><input type="text" name="userqty" id="userqty" class="sub" onchange="calqtydiff()" /></td><td><input type="text" name="usercts" id="usercts" class="sub" onchange="calctsdiff()" /></td><td><input type="text" name="userrte" id="userrte" class="sub" onchange="calrtediff()" /></td><td><input type="text" name="uservalue" id="uservalue" class="sub"  readonly="readonly" /></td><td><input type="text" name="usersize" id="usersize" class="sub"  readonly="readonly" /></td></tr>
    <tr><td>Difference</td><td><input type="text" id="qtydiff" class="sub" readonly="readonly" /></td><td><input type="text" id="ctsdiff" class="sub" readonly="readonly" /></td><td><input type="text" id="rtediff" class="sub" readonly="readonly"/></td><td><input type="text" id="valuediff" class="sub" readonly="readonly" /></td><td><input type="text" id="sizediff" class="sub" readonly="readonly" /></td></tr>
    </table>
    </td>
    
    </tr>
  
    </table>
    </td></tr>
  
  
  
  
  
  
   <tr><td>
   <table class="grid1">
   <tr> <th>Sr</th>
   <th>MIX <input type="checkbox" name="checkAll" id="checkAll"  onclick="checkALLBOX('MIX_','count'),singletobox(),singletomixall('MIX_','count')"  /></th>
   <th>Excel<input type="checkbox" name="checkAllEXC" id="checkAllEXC"  onclick="checkALLLABEXC('EXC_','count')"  /> </th>
        <th>Packet No.</th>
    <%for(int j=0; j < vwPrpLst.size(); j++ ){
    String prp = (String)vwPrpLst.get(j);
    if(prpDspBlocked.contains(prp)){
    }else{
     String hdr = (String)mprp.get(prp);
    String prpDsc = (String)mprp.get(prp+"D");
    if(hdr == null) {
        hdr = prp;
       }  
%>
<th title="<%=prpDsc%>"><%=hdr%></th>
<%}}%>
<th>QTY</th>
<th>CTS</th>
<th>AVG VALUE</th>
<th>TOTAL VALUE</th>

</tr>
 <%
 for(int i=0; i < stockList.size(); i++ ){
 String styleCode = "";
 HashMap stockPkt = (HashMap)stockList.get(i);
 String stkIdn = (String)stockPkt.get("stk_idn");
 String stt = util.nvl((String)stockPkt.get("stt"));
 if(stt.equals("LB_RT"))
  styleCode = "style=\"color: red;\"";
  if(stt.equals("AS_FN"))
 styleCode = "style=\"color: Blue;\"";
 sr = i+1;
 String checkFldId = "CHK_"+sr;
 String sttPrp = "value(stt_" +stkIdn+ ")";
 String labVal = "value(lab_" +stkIdn+ ")";
 String labIdn = "lab_"+stkIdn ;
 String onclick="Labselect(this,"+stkIdn+")";
 String lbID = "LB_"+sr;
 String repID = "REP_"+sr;
 String mixID = "MIX_"+sr;
 String noneID = "None_"+sr;
 String excId = "EXC_"+sr;
 String cts1 = "cts1_"+sr;
 String qty1 = "qty1_"+sr;
 String rte1 = "rte1_"+sr;
 String tvalue1 = "tvalue1_"+sr;
 String counter = "mixpkt('"+sr+"');";
 String checkFldVal="value(EXC_"+stkIdn+")";
 %>
<tr <%=styleCode%>>

<td><%=sr%></td>
<td>&nbsp;&nbsp;<html:checkbox property="<%=sttPrp%>" name="boxSelectionForm"  styleId="<%=mixID%>" value="MIX" onclick="<%=counter%>"   /></td>         
<td><html:checkbox property="<%=checkFldVal%>"  styleId="<%=excId%>" value="yes" /> </td>
<td><%=stockPkt.get("vnm")%></td>

<%for(int j=0; j < vwPrpLst.size(); j++ ){
    String prp = (String)vwPrpLst.get(j);
   if(prpDspBlocked.contains(prp)){
   }else{
    %>
    <td><%=stockPkt.get(prp)%></td>
    <%
    
}}%>

<%
String qty=util.nvl((String)stockPkt.get("qty"),"0");
String cts=(String)stockPkt.get("cts");
String quot=(String)stockPkt.get("quot");
if(qty == null || qty.equals(""))
cts="0";
if(cts == null || cts.equals(""))
qty="0";
if(quot == null || quot.equals(""))
quot="0";

Float pp = Float.parseFloat(cts);
Float qq = Float.parseFloat(quot);
Float rr;
rr = pp * qq;
String tvalue = Float.toString(rr);

%>
<input type="hidden" id="rvalue" name="rvalue" value="" />
<td><input type="text" id="<%=qty1%>" class="sub" value=<%=qty%> readonly="readonly" /></td>
<td><input type="text" id="<%=cts1%>" class="sub" value=<%=cts%>  readonly="readonly"/></td>
<td><input type="text" id="<%=rte1%>" class="sub" value=<%=quot%> readonly="readonly" /></td>
<td><input type="text" id="<%=tvalue1%>" class="sub" value=<%=tvalue%> readonly="readonly" /></td>
<!--<td><html:radio property="<%=sttPrp%>" name="boxSelectionForm"  styleId="<%=mixID%>" value="MIX" onclick="<%=counter%>"   /></td>-->

</tr>
   <%}%>
   </table>
   <input type="hidden" name="count" value="<%=sr%>" id="count" />
   </td></tr>
   
   
  
   <%
   } else {
   %>
   <tr>
   <td>Sorry Result not found </td></tr>
   <%
   }
   
   }else{%>
  <tr>
   <td>Sorry Result not found </td></tr>
   <%}}}%>
  
  
  
  
  
  </table>
 
  </td></tr>
  </html:form>
   <tr>
    <td><jsp:include page="/footer.jsp" /> </td>
    </tr>
  </table>
  </body>
</html>