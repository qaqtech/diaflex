<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=windows-1252"%>
<jsp:useBean id="info" class="ft.com.InfoMgr" scope="session" />
<jsp:useBean id="util" class="ft.com.JspUtil" scope="page" />
<%@ page import="java.util.ArrayList,java.util.HashMap,org.apache.commons.collections.iterators.IteratorEnumeration,java.util.Enumeration, java.sql.ResultSet,ft.com.*, java.util.Date, java.util.ArrayList, ft.com.dao.*"%>
<%@taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>
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
  <img src="../images/line.jpg" border="0" /></td><td valign="middle"> <span class="pgHed">Box Split & Merge</span> </td>
<td id="loading"></td>
</tr></table>
  </td>
  </tr>
  <%

   String msg1 = (String)request.getAttribute("page1");
   String vnmNtFnd = (String)request.getAttribute("vnmNotFnd");
     if(vnmNtFnd!=null){%>
    <tr><td valign="top" class="tdLayout"><span class="redLabel"><%=vnmNtFnd%></span></td></tr>
   <%}%>
    <%
   String msg = (String)request.getAttribute("msg");
     if(msg!=null){%>
    <tr><td valign="top" class="tdLayout"><span class="redLabel"><%=msg%></span></td></tr>
   <%}%>
  
  <tr>
  <td valign="top" class="tdLayout">
  <html:form action="box/boxSplitMerge.do?method=view" method="post" >
  <table class="grid1">
  <tr><td> Select Box 
  </td>
  <td>
    <%
    HashMap boxNme5 = (HashMap)session.getAttribute("boxnme");
    Enumeration e5=new IteratorEnumeration(boxNme5.keySet().iterator());
    int size5 = boxNme5.size();
    %>
    <select name="bnme" id="bnmefrom" style="width:95px; border:1px solid #c51212;" ;>
    <option value="">--Select Box--</option>
    <%
    for(int p=0;p<size5;p++)
    {
    String boxValue=(String)e5.nextElement();
    String boxnam=(String)boxNme5.get(boxValue);
    %>
    <option value="<%=boxValue%>"> <%=boxnam%></option>
    <% 
    }
    %>
    </select> 
    </td> </tr> 
   
     <tr><td colspan="2" align="center"><html:submit property="view" value="VIEW" styleClass="submit" onclick="return validatesplit()" /> </td>
  </tr>
  
    </table>
  
  </html:form>
  
  </td>
  </tr>
  
  
  <html:form action="box/boxSplitMerge.do?method=save" method="post" >
  
  
  <%
  String view = (String)request.getAttribute("view");
  if(view!=null){
   if(view.equals("Y")){
   int count = 3;
   String rowupd = "getboxsplit('"+count+"');";
  %>
  
  
  <tr>
  <td valign="top" class="tdLayout">
  
  <table>
  <tr>
  <td>
  <table>
  
  <tr><td><html:submit property="value(pb_lab)" value="SAVE CHANGES" styleClass="submit" disabled="true" styleId="splitesave" />
<input type="button" name="calculate" value="CALCULATE" class="submit" id="calculate" style="width=100px;" onclick="<%=rowupd%>"/>
<html:button property="value(reset)" value="RESET" onclick="this.form.reset()" styleClass="submit" />

  </td></tr>
  
  <tr><td><html:radio property="value(split)" name="boxtoBoxForm"  styleId="splitID" value="SPLIT"  /> SPLIT</td>
  <td><html:radio property="value(split)" name="boxtoBoxForm"  styleId="mergeID" value="MERGE"  /> MERGE</td></tr>
  <!--
   <tr><td><html:radio property="value(split)" name="boxtoBoxForm"  styleId="splitID" value="SPLIT"  onclick="<%=rowupd%>" /> SPLIT</td>
  <td><html:radio property="value(split)" name="boxtoBoxForm"  styleId="mergeID" value="MERGE"  onclick="<%=rowupd%>" /> MERGE</td></tr>
  -->
  
  
  </table>
  </td></tr>
  
  <tr>
  <td>
  <table class="grid1">
  <tr>
    <th colspan="2"> SOURCE BOX</th>
  </tr>
    <tr>
    <td>
    <html:text property="vnm" styleId="vnm" name="boxtoBoxForm" readonly="true"  />
    </td>   
    <td>
    <table class="grid1">
    <tr><th></th><th>QTY</th><th>CTS</th><th>AVG VALUE</th><th>TOTAL VALUE</th><th>AVG SIZE</th></tr>
    <tr><td>Available</td>
    <td><B><bean:write property="value(avlqty)" name="boxtoBoxForm" /></b></td>
    <td><B><bean:write property="value(avlcts)" name="boxtoBoxForm" /></b></td>
    <td><B><bean:write property="value(avlrte)" name="boxtoBoxForm" /></b></td>
    <td><B><bean:write property="value(avlvalue)" name="boxtoBoxForm" /></b></td>
    <td><B><bean:write property="value(avlsize)" name="boxtoBoxForm" /></b></td>
    </tr>
    <tr><td>On Hand</td>
    <td><html:text property="fromqty" styleId="fromqty" styleClass="sub" name="boxtoBoxForm" readonly="true"    /></td>
    <td><html:text property="fromcts" styleId="fromcts" styleClass="sub" name="boxtoBoxForm"  readonly="true" /></td>
    <td><html:text property="fromrte" styleId="fromrte" styleClass="sub" name="boxtoBoxForm"  readonly="true" /></td>
    <td><html:text property="fromvalue" styleId="fromvalue" styleClass="sub" name="boxtoBoxForm"  readonly="true" /></td>
    <td><html:text property="fromsize" styleId="fromsize" styleClass="sub" name="boxtoBoxForm"  readonly="true" /></td>
    </tr>
    <tr><td>New</td><td><input type="" id="newfromqty" name="newfromqty" class="sub" readonly="readonly" /></td><td><input type="text" id="newfromcts" name="newfromcts" class="sub" readonly="readonly"/></td><td><input type="text" id="newfromrte" name="newfromrte" class="sub" readonly="readonly" /></td><td><input type="text" id="newfromvalue" name="newfromvalue" class="sub" readonly="readonly"/></td><td><input type="text" id="newfromsize" name="newfromsize" class="sub" readonly="readonly" /></td></tr>
    <tr><td>User</td><td><input type="text" name="userfromqty" id="userfromqty" class="sub" onchange="calqtydifffrom()" /></td><td><input type="text" name="userfromcts" id="userfromcts" class="sub" onchange="calctsdifffrom()"  /></td><td><input type="text" name="userfromrte" id="userfromrte" class="sub" onchange="calrtedifffrom()"  /></td><td><input type="text" name="userfromvalue" id="userfromvalue" class="sub" readonly="readonly" /></td><td><input type="text" name="userfromsize" id="userfromsize" class="sub" readonly="readonly"/></td></tr>
    <tr><td>Difference</td><td><input type="text" id="fromqtydiff" name="fromqtydiff" class="sub"  readonly="readonly"/></td><td><input type="text" id="fromctsdiff" name="fromctsdiff" class="sub" readonly="readonly" /></td><td><input type="text" id="fromrtediff" name="fromrtediff" class="sub" readonly="readonly" /></td><td><input type="text" id="fromvaluediff" name="fromvaluediff" class="sub" readonly="readonly" /></td><td><input type="text" id="fromsizediff" name="fromsizediff" class="sub" readonly="readonly" /></td></tr>
    
    </table>
    </td>
    
</tr>
  
  </table>
  </td>
  
  <tr>
  <td>
  <table class="grid1">
  <tr>
        
        <th colspan="2">TARGET BOX</th>
        
  </tr>
  
   <%
        int lmt = 3;
        for(int i=0; i < lmt; i++) {
        int sr = i + 1; 
        String cts = "tocts_"+sr;
        String qty = "toqty_"+sr;
        String rte = "torte_"+sr;
        String avg = "toavg_"+sr;
        String addqty = "addqty_"+sr;
        String addcts = "addcts_"+sr;
        String addrte = "addrte_"+sr;
        String addavg = "addavg_"+sr;
        String totalqty = "totalqty_"+sr;
        String totalcts = "totalcts_"+sr;
        String totalrte = "totalrte_"+sr;
        String totalavg = "totalavg_"+sr;
        String userqty = "userqty_"+sr;
        String usercts = "usercts_"+sr;
        String userrte = "userrte_"+sr;
        String useravg = "useravg_"+sr;
        
        String bnmeto = "bnmeto_"+sr;
        String counter = "getBoxTo(this,'"+sr+"');";
        String avgvalue = "useravgvalue('"+lmt+"');";
        
        
    %>    
  
   
        <tr>
    <td>
    <%
    HashMap boxNme1 = (HashMap)session.getAttribute("boxnme");
    Enumeration e1=new IteratorEnumeration(boxNme1.keySet().iterator());
    int size1 = boxNme1.size();
    %>
    <select name="<%=bnmeto%>" id="<%=bnmeto%>" style="width:95px; border:1px solid #c51212;" onchange="<%=counter%>" >
    <option value="">--Select Box--</option>
    <%
    for(int k=0;k<size1;k++)
    {
    String boxValue=(String)e1.nextElement();
    String boxnam=(String)boxNme1.get(boxValue);
    %>
    <option value="<%=boxValue%>"> <%=boxnam%></option>
    <% 
    }
    %>
    </select> 
    </td>
    <td>
    <table class="grid1">
    <tr><th>CUR.QTY</th><th>CUR.CTS</th><th>CUR.AVG</th><th>CUR.VAL</th><th>TRF.QTY</th><th>TRF.CTS</th><th>TRF.AVG</th><th>TRF.VAL</th>
    <th>TOT.QTY</th><th>TOT.CTS</th><th>TOT.AVG</th><th>TOT.VAL</th><th>USR.QTY</th><th>USR.CTS</th><th>USR.AVG</th><th>USR.VAL</th> </tr>
    <tr><td><input type="text" id="<%=qty%>" name="<%=qty%>" class="sub" value="0" readonly="readonly" /></td>
    <td><input type="text" id="<%=cts%>" name="<%=cts%>" class="sub" value="0" readonly="readonly"/></td>
    <td><input type="text" id="<%=rte%>" name="<%=rte%>" class="sub" value="0" readonly="readonly"/></td>
    <td><input type="text" id="<%=avg%>" name="<%=avg%>" class="sub" value="0" readonly="readonly"/></td>
    <td><input type="text" id="<%=addqty%>" name="<%=addqty%>" class="sub" value="0"   /></td>
    <td><input type="text" id="<%=addcts%>" name="<%=addcts%>" class="sub" value="0"  /></td>
    <td><input type="text" id="<%=addrte%>" name="<%=addrte%>" class="sub" value="0"/></td>
    <td><input type="text" id="<%=addavg%>" name="<%=addavg%>" class="sub" readonly="readonly" /></td>
    <td><input type="text" id="<%=totalqty%>" name="<%=totalqty%>"  class="sub" readonly="readonly" /></td>
    <td><input type="text" id="<%=totalcts%>" name="<%=totalcts%>"  class="sub" readonly="readonly" /></td>
    <td><input type="text" id="<%=totalrte%>" name="<%=totalrte%>" class="sub" readonly="readonly" /></td>
    <td><input type="text" id="<%=totalavg%>" name="<%=totalavg%>" class="sub" readonly="readonly" /></td>
    <td><input type="text" id="<%=userqty%>" name="<%=userqty%>" class="sub"  /></td>
    <td><input type="text" id="<%=usercts%>" name="<%=usercts%>" class="sub"/></td>
    <td><input type="text" id="<%=userrte%>" name="<%=userrte%>" class="sub" onchange="<%=avgvalue%>" /></td>
    <td><input type="text" id="<%=useravg%>" name="<%=useravg%>" class="sub" /></td>
    
    </tr>
    
    </table>
    </td></tr>
    <%
    }
    %>

 </table>
 <%
 }}
 %>
  </html:form>
   <tr>
    <td><jsp:include page="/footer.jsp" /> </td>
    </tr>
  </table>
  </body>
</html>