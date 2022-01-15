<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=windows-1252"%>
<jsp:useBean id="info" class="ft.com.InfoMgr" scope="session" />
<jsp:useBean id="util" class="ft.com.JspUtil" scope="page" />
<%@ page import="java.util.ArrayList,java.util.HashMap,java.util.Enumeration, java.sql.ResultSet,ft.com.*, java.util.Date, java.util.ArrayList, ft.com.dao.*"%>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
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



<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0" class="mainbg">
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
<img src="../images/line.jpg" border="0" /></td><td valign="middle"> <span class="pgHed">Box To Box</span> </td>
</tr></table>
</td>
</tr>
<%

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

<form action="boxSplit.do?method=save" method="post">

<%
String msg1 = (String)request.getAttribute("page");
if(msg1!=null){
if(msg1.equals("MIX")){
%>


<table>
<%
String view = (String)request.getAttribute("view");
String ctwt=null;
String qty="",cts="",rte="",value="",size="";
if(view!=null){
HashMap mprp = info.getMprp();
int sr = 0;
%>

<tr>
<td>
<table class="grid1">
<tr>

<th colspan="2"> FROM BOX</th>


</tr>
<tr>
<td>
<%
HashMap boxNme = (HashMap)session.getAttribute("boxnme");
ArrayList idns = new ArrayList();
idns = (ArrayList)session.getAttribute("idns");
//Enumeration e1=boxNme.keys();
int idnsize = idns.size();

String sidn="0";
String selidn=util.nvl((String)request.getAttribute("selidn"));
if(!selidn.equals(""))
sidn=selidn;
%>
<input type="hidden" name="selidn" id="selidn" value="<%=sidn%>"/>
<!--<select name="bnme" id="bnmefrom" style="width:95px; border:1px solid #c51212;" onchange="getBoxFromSplit(this);callBoxPkt(this)";>-->
<select name="bnme" id="bnmefrom" style="width:95px; border:1px solid #c51212;" onchange="getBoxFromSplit(this);">
<option value="">--Select Box--</option>
<%
String fromidn=util.nvl((String)request.getAttribute("fromidn"));
for(int k=0;k<idnsize;k++)
{
String boxnam=(String)idns.get(k);
String boxValue = "";
if(boxNme.get(boxnam) != null)
{
boxValue = (String)boxNme.get(boxnam);
}
String isSelected = "" ;
if(boxnam.equals(fromidn))
isSelected = "selected=\"selected\"";


%>
<option value="<%=boxnam%>" <%=isSelected%>> <%=boxValue%></option>
<%
}
String test = "";
String vnmLst=util.nvl((String)request.getAttribute("vnmLst"));
%>
</select><br>
Packet.
<textarea cols="25" rows="3" id="vnmLst" name="vnmLst"><%=vnmLst%></textarea>
<input type="submit" name="fetch" id="fetch" value="Fetch" class="submit"/>
</td>
<td>
<table class="grid1">
<tr><th></th><th>QTY</th><th>CTS</th><th>AVG VALUE</th><th>TOTAL VALUE</th><th>AVG SIZE</th></tr>
    <tr><td>Available</td>
        <td><span id="avlfrmqty"></span></td>
    <td><span id="avlfrmcts"></span></td>
    <td><span id="avlfrmrte"></span></td>
    <td><span id="avlfrmval"></span></td>
    <td><span id="avlfrmsize"></span></td>
    </tr>
<tr><td>On Hand</td>
<%
qty="";cts="";rte="";value="";size="";
HashMap FromBoxDtl = (HashMap)request.getAttribute("FromBoxDtl");
if(FromBoxDtl !=null && FromBoxDtl.size()>0){
qty=(String)FromBoxDtl.get("qty");
cts=(String)FromBoxDtl.get("cts");
rte=(String)FromBoxDtl.get("rte");
value=(String)FromBoxDtl.get("value");
size=(String)FromBoxDtl.get("size");
}
%>

<td><input type="text" id="fromqty" readonly="readonly" name="qty" class="sub" value="<%=qty%>" /></td>
<td><input type="text" id="fromcts" readonly="readonly" name="cts" class="sub" value="<%=cts%>" /></td>
<td><input type="text" id="fromrte" readonly="readonly" name="rte" class="sub" value="<%=rte%>" /></td>
<td><input type="text" id="fromvalue" name="fromvalue" class="sub" value="<%=value%>" readonly="readonly"/></td>
<td><input type="text" id="fromsize" name="fromsize" class="sub" value="<%=size%>" readonly="readonly"/></td>
    
</tr>
<tr><td>New</td><td><input type="text" readonly="readonly" id="newfromqty" name="newfromqty" class="sub" /></td><td><input type="text" id="newfromcts" readonly="readonly" name="newfromcts" class="sub"/></td><td><input type="text" id="newfromrte" readonly="readonly" name="newfromrte" class="sub" /></td><td><input type="text" name="newfromvalue" id="newfromvalue" class="sub" readonly="readonly" /></td><td><input type="text" name="newfromsize" id="newfromsize" class="sub" readonly="readonly" /></td></tr>
<tr><td>User</td><td><input type="text" name="userfromqty" id="userfromqty" class="sub" onchange="calqtydifffrom()"/></td><td><input type="text" name="userfromcts" id="userfromcts" class="sub" onchange="calctsdifffrom()" /></td><td><input type="text" name="userfromrte" id="userfromrte" class="sub"  onchange="calrtedifffrom()" /></td><td><input type="text" name="userfromvalue" id="userfromvalue" class="sub" readonly="readonly" /></td><td><input type="text" name="userfromsize" id="userfromsize" class="sub" readonly="readonly" /></td></tr>
<tr><td>Difference</td><td><input type="text" id="fromqtydiff" name="fromqtydiff" class="sub" readonly="readonly" /></td><td><input type="text" id="fromctsdiff" name="fromctsdiff" class="sub" readonly="readonly" /></td><td><input type="text" id="fromrtediff" name="fromrtediff" class="sub" readonly="readonly" /></td><td><input type="text" id="fromvaluediff" name="fromvaluediff" class="sub" readonly="readonly" /><td><input type="text" id="fromsizediff" name="fromsizediff" class="sub" readonly="readonly" /></tr>
</table>
</td>
</tr>
</table>
</td>


<td>
<table class="grid1" style="float:left;">
<tr>

<th colspan="2">TO BOX</th>


</tr>
<tr>
<td>
<%
// HashMap boxNme1 = (HashMap)session.getAttribute("boxnme");
// Enumeration e2=boxNme1.keys();
// int size2 = boxNme1.size();
%>

<select name="bnmeto" id="bnmeto" style="width:95px; border:1px solid #c51212;" onchange="getBoxToSplit(this)";>
<option value="">--Select Box--</option>
<%
String toidn=util.nvl((String)request.getAttribute("toidn"));
for(int k=0;k<idnsize;k++)
{
String boxnam=(String)idns.get(k);
String boxValue = "";
if(boxNme.get(boxnam) != null)
{
boxValue = (String)boxNme.get(boxnam);
}

String isSelected = "" ;
if(boxnam.equals(toidn))
isSelected = "selected=\"selected\"";
%>
<option value="<%=boxnam%>" <%=isSelected%>> <%=boxValue%></option>
<%
}
%>
</select>
</td>
<td>
<table class="grid1">
<tr><th></th><th>QTY</th><th>CTS</th><th>AVG VALUE</th><th>TOTAL VALUE</th><th>AVG SIZE</th></tr>
    <tr><td>Available</td>
            <td><span id="avltoqty"></span></td>
    <td><span id="avltocts"></span></td>
    <td><span id="avltorte"></span></td>
    <td><span id="avltoval"></span></td>
    <td><span id="avltosize"></span></td>
    </tr>
<tr><td>On Hand</td>
<%
qty="";cts="";rte="";value="";size="";
HashMap ToBoxDtl = (HashMap)request.getAttribute("ToBoxDtl");
if(ToBoxDtl !=null && ToBoxDtl.size()>0){
qty=(String)ToBoxDtl.get("qty");
cts=(String)ToBoxDtl.get("cts");
rte=(String)ToBoxDtl.get("rte");
value=(String)ToBoxDtl.get("value");
size=(String)ToBoxDtl.get("size");
}
%>
<td><input type="text" readonly="readonly" id="toqty" name="toqty" class="sub" value="<%=qty%>" /></td>
<td><input type="text" id="tocts" name="tocts" readonly="readonly" class="sub" value="<%=cts%>" /></td>
<td><input type="text" id="torte" readonly="readonly" name="torte" class="sub" value="<%=rte%>" /></td>
<td><input type="text" id="tovalue" name="tovalue" class="sub" value="<%=value%>" readonly="readonly"/></td>
<td><input type="text" id="tosize" name="tosize" class="sub" value="<%=size%>" readonly="readonly"/></td>

</tr>
<tr><td>New</td><td><input type="text" id="newtoqty" name="newtoqty" readonly="readonly" class="sub" /></td><td><input type="text" readonly="readonly" id="newtocts" name="newtocts" class="sub"/></td>
<td><input type="text" id="newtorte" readonly="readonly" name="newtorte" class="sub" /></td><td><input type="text" id="newtovalue" name="newtovalue" readonly="readonly" class="sub" /></td><td><input type="text" id="newtosize" name="newtosize" readonly="readonly" class="sub" /></td></tr>
<tr><td>User</td><td><input type="text" name="usertoqty" id="usertoqty" class="sub" onchange="calqtydiffto" /></td><td><input type="text" name="usertocts" id="usertocts" class="sub" onchange="calctsdiffto" /></td><td><input type="text" name="usertorte" id="usertorte" class="sub" onchange="calrtediffto" /></td><td><input type="text" name="usertovalue" id="usertovalue" class="sub" readonly="readonly" /></td><td><input type="text" name="usertosize" id="usertosize" class="sub" readonly="readonly"/></td></tr>
<tr><td>Difference</td><td><input type="text" id="toqtydiff" name="toqtydiff" class="sub" readonly="readonly" /></td><td><input type="text" id="toctsdiff" name="toctsdiff" class="sub" readonly="readonly" /></td><td><input type="text" id="tortediff" name="tortediff" class="sub"  readonly="readonly" /></td><td><input type="text" id="tovaluediff" name="tovaluediff" class="sub" readonly="readonly" /></td><td><input type="text" id="tosizediff" name="tosizediff" class="sub" readonly="readonly"/></td></tr>

</table>
</td>

</tr>

</table>
</td>
</tr>
<tr>
<td>
<table>
<tr><td>
<input type="submit" name="pb_lab" value="SAVE CHANGES" onclick="" class="submit" />
<html:button property="value(reset)" value="RESET" onclick="this.form.reset()" styleClass="submit" />
</td>
</tr>
</table>
</td>
</tr>

    <!---->
<tr>
<td colspan="2">
<%
HashMap pktDtlFrom = (HashMap)request.getAttribute("pktDtlFrom");
if(pktDtlFrom !=null && pktDtlFrom.size()>0){%>
<table  class="grid1" align="left" id="dataTable">
   <tr>
        <th>PACKET NO</th>
        <th>QTY</th>
        <th> CTS</th>
        <th>AVG VALUE</th>
        <th>TOTAL VALUE</th>
        <!--<th>BOX</th>
        <th>SINGLE</th>-->
        <th>BOX
        <%if(pktDtlFrom.size()<30){%>
        <input name="BOX_ALL" id="BOX_ALL" type="checkbox" onclick="pkttoboxALL(this.id,'BOX_')">
        <%}%>
        </th>
        <th>SINGLE
        <%if(pktDtlFrom.size()<30){%>
        <input name="SINGLE_ALL" id="SINGLE_ALL" type="checkbox" onclick="pkttoboxALL(this.id,'SINGLE_')">
        <%}%>
        </th>
  </tr>
<%
qty="";cts="";rte="";value="";size="";
String vnm="",idn="";
for(int i=0;i<pktDtlFrom.size();i++){
sr++;
String vnmID = "vnm_"+sr;
String idnID = "idn_"+sr;
String qtyID = "qty_"+sr;
String ctsID = "cts_"+sr;
String uprID = "rte_"+sr;
String totalID = "total_"+sr;
String boxID = "BOX_"+sr;
String singleID = "SINGLE_"+sr;
HashMap pktFrom=(HashMap)pktDtlFrom.get(sr);
idn=(String)pktFrom.get("idn");
vnm=(String)pktFrom.get("vnm");
qty=(String)pktFrom.get("qty");
cts=(String)pktFrom.get("cts");
rte=(String)pktFrom.get("rte");
value=(String)pktFrom.get("value");

%>
<tr>

<td><input type="hidden" id="<%=idnID%>" name="<%=idnID%>" value="<%=idn%>" />
<input type="text" id="<%=vnmID%>" name="<%=vnmID%>" value="<%=vnm%>" readonly="readonly"/></td>
<td><input type="text" id="<%=qtyID%>" name="<%=qtyID%>" value="<%=qty%>" readonly="readonly"/></td>
<td><input type="text" id="<%=ctsID%>" name="<%=ctsID%>" value="<%=cts%>" readonly="readonly"/></td>
<td><input type="text" id="<%=uprID%>" name="<%=uprID%>" value="<%=rte%>" readonly="readonly"/></td>
<td><input type="text" id="<%=totalID%>" name="<%=totalID%>" value="<%=value%>" readonly="readonly"/></td>
<td><input type="checkbox" id="<%=boxID%>" name="<%=boxID%>" onclick="pkttobox(this.id)"/></td>
<td><input type="checkbox" id="<%=singleID%>" name="<%=singleID%>" onclick="pkttoboxsingle(this.id)"/></td>
</tr>
<%}%>
<input type="hidden" name="count" id="count" value="<%=sr%>" />
</table>
<%}%>

</td>
</tr>
<%


}else{%>
<tr>
<td>Sorry Result not found </td></tr>
<%}}}%>


</table>



</form></td></tr>
<tr>
<td><jsp:include page="/footer.jsp" /> </td>
</tr>
</table>
</body>
</html>