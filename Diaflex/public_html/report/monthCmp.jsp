<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page errorPage="../error_page.jsp" %>
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import="java.util.ArrayList,java.util.ArrayList,java.util.HashMap, java.sql.*, java.util.Enumeration, java.util.logging.Level"%>
<jsp:useBean id="info" class="ft.com.InfoMgr" scope="session" />
<jsp:useBean id="util" class="ft.com.JspUtil" scope="page" />
<%@taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<html> 
   <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/bse.js?v=<%=info.getJsVersion()%> " > </script>
<link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/bse.css?v=<%=info.getJsVersion()%>"/>
<link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/general.css?v=<%=info.getJsVersion()%>"/>
<script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/ajax.js?v=<%=info.getJsVersion()%> " > </script>
  <script type="text/javascript" src="<%=info.getReqUrl()%>/PopScripts/popup.js?v=<%=info.getJsVersion()%> " > </script>
    <script src="<%=info.getReqUrl()%>/PopScripts/jquery-1.2.6.min.js" type="text/javascript"></script>
   <script language="javaScript" type="text/javascript" src="<%=info.getReqUrl()%>/calendar/calendar.js"></script>
   <link href="<%=info.getReqUrl()%>/calendar/calendar.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="<%=info.getReqUrl()%>/chartscript/amcharts.js?v=<%=info.getJsVersion()%> " > </script>
<script type="text/javascript" src="<%=info.getReqUrl()%>/chartscript/chartajax.js?v=<%=info.getJsVersion()%> " > </script>
<script type="text/javascript" src="<%=info.getReqUrl()%>/chartscript/serial.js?v=<%=info.getJsVersion()%> " > </script>
 
      <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/style.css?v=<%=info.getJsVersion()%>"/>

  <title>Monthly Comparision</title>
 
  </head>
  <%String style="width: 100%; height: 600px; float:left;";%>
        <%String logId=String.valueOf(info.getLogId());
        String onfocus="cook('"+logId+"')";
  HashMap allPageDtl= (info.getPageDetails() == null)?new HashMap():(HashMap)info.getPageDetails();
  HashMap pageDtl=(HashMap)allPageDtl.get("MONTHLY_CMP");
    ArrayList pageList=new ArrayList();
    HashMap pageDtlMap=new HashMap();
    String graphkey="";
    String fld_nme="",fld_ttl="",val_cond="",lov_qry="",dflt_val="",fld_typ="",form_nme="",flg1="";
    ArrayList gridLst= (ArrayList)session.getAttribute("gridLst");
    ArrayList vWPrpList = (ArrayList)session.getAttribute("memocomp_vw");
        HashMap mprp = info.getMprp();
        %>
 <body onfocus="<%=onfocus%>" onload="MM_preloadImages('images/faunalogo.png')" topmargin="0" leftmargin="0" rightmargin="0" bottommargin="0">
  <div id="backgroundPopup"></div>
<div id="popupContactASSFNL">
<table class="popUpTb">
 <tr><td height="5%">&nbsp;&nbsp;&nbsp;<html:button property="value(close)" styleClass="submit"  onclick="MKEmptyPopup('SC');disablePopupASSFNL()" value="Close"  /> </td></tr>
<tr><td valign="top" height="95%">
<iframe name="SC" class="frameStyle"   align="middle" frameborder="0">

</iframe></td></tr>
</table>
</div>
<input type="hidden" id="reqUrl" name="reqUrl" value="<%=info.getReqUrl()%>"/>
<table width="100%"  border="0" align="center" cellpadding="0" cellspacing="0" class="mainbg">
   <tr>
    <td height="103" valign="top">
   <jsp:include page="/header.jsp" />
    
    </td>
  </tr>
  <tr>
    <td height="5" valign="top" class="greyline"><img src="../images/grey_linebig.png" width="685" height="5" align="right" /></td>
  </tr>
   <tr>
  <td valign="top" class="hedPg">
  <table cellpadding="1" cellspacing="5"><tr><td valign="middle">
  <img src="../images/line.jpg" border="0" /></td><td valign="middle"> <span class="pgHed">Monthly Comparision</span> </td>
</tr></table>
  </td>
  </tr>
<tr><td valign="top" class="hedPg">
<html:form action="/report/monthCmp.do?method=fetch"  method="POST">
    <table class="grid1">
    <tr><th colspan="2">Generic Search/Employee/Date Search
  <%
  ArrayList rolenmLst=(ArrayList)info.getRoleLst();
  String usrFlg=util.nvl((String)info.getUsrFlg());
  if(usrFlg.equals("SYS") || rolenmLst.contains("ADMIN")){
  %>
  &nbsp;&nbsp;<img src="../images/edit.jpg" title="Edit Properties Module" onclick="newWindow('<%=info.getReqUrl()%>/dynamicProperty.do?method=load&mdl=MEMOCMP_SRCH&sname=MonthCmpGNCSrch&par=GENERIC')" border="0" width="15px" height="15px"/> 
  <%}%>
  </th></tr>
  <tr>
   <td colspan="2">
    <jsp:include page="/genericSrch.jsp"/>
   </td></tr>
    <tr><td>Employee</td><td>
             <html:select property="empLst" name="monthlyCmpForm" style="height:200px; width:180px;" styleId="empLst"  multiple="true" size="15">
             <html:optionsCollection property="empList" name="monthlyCmpForm"  value="emp_idn" label="emp_nme" />
             </html:select></td>
    </tr>
    <tr><td>Date From : </td><td>
    <html:text property="value(dtefr)" styleClass="txtStyle"  styleId="dtefr"  size="30"  /> <a href="#" style="margin-top:5px;"  onClick="setYears(1900, '<%=info.getCurrentYear()%>' );showCalender(this, 'dtefr');">
                <img src="<%=info.getReqUrl()%>/images/calender.png"></a></td></tr>
    <tr><td>Date To : </td><td>
    <html:text property="value(dteto)" styleClass="txtStyle"  styleId="dteto"  size="30"  /> <a href="#"  onClick="setYears(1900, '<%=info.getCurrentYear()%>' );showCalender(this, 'dteto');">
                <img src="<%=info.getReqUrl()%>/images/calender.png"></a>
                </td></tr>
             <%pageList=(ArrayList)pageDtl.get("RPTTYP");
             if(pageList!=null && pageList.size() >0){%>
             <tr>
               <td>Report Type</td>
               <td>
             <html:select property="value(rpttyp)" styleId="rpttyp" name="monthlyCmpForm" >
             <%
               for(int j=0;j<pageList.size();j++){
                 pageDtlMap=(HashMap)pageList.get(j);
                 fld_nme=(String)pageDtlMap.get("fld_nme");
                 fld_ttl=(String)pageDtlMap.get("fld_ttl");
                 %>
                <html:option value="<%=fld_nme%>"><%=fld_ttl%></html:option>
             <%}%>
            </html:select>
            </td>
            </tr>
            <%}%>
    <!--<tr>
    <td>Report Type</td>
    <td>
   <html:select property="value(rpttyp)" styleId="rpttyp" name="monthlyCmpForm" >
   <html:option value="D" >Detail</html:option>
   <html:option value="S" >Summary</html:option>
  </html:select>
    </td>
    </tr>-->
     <tr><td colspan="2" align="center">
     <!--<html:submit property="value(emp)" value="Employee Wise" styleClass="submit" onclick="return validatMonthCmp();"/>
     <html:submit property="value(memo)" value="Memo Wise" styleClass="submit"/>-->
            <%
            pageList=(ArrayList)pageDtl.get("SUBMIT");
            if(pageList!=null && pageList.size() >0){
            for(int j=0;j<pageList.size();j++){
            pageDtlMap=(HashMap)pageList.get(j);
            fld_nme="value("+(String)pageDtlMap.get("fld_nme")+")";
            fld_ttl=(String)pageDtlMap.get("fld_ttl");
            fld_typ=(String)pageDtlMap.get("fld_typ");
            lov_qry=(String)pageDtlMap.get("lov_qry");
            dflt_val=(String)pageDtlMap.get("dflt_val");
            val_cond=(String)pageDtlMap.get("val_cond"); 
            if(fld_typ.equals("S")){
            %>
    <html:submit property="<%=fld_nme%>" value="<%=fld_ttl%>" onclick="<%=val_cond%>" styleClass="submit"/>
    <%}else if(fld_typ.equals("B")){%>
    <html:button property="<%=fld_nme%>" value="<%=fld_ttl%>" onclick="<%=val_cond%>" styleClass="submit"/>
    <%}else if(fld_typ.equals("HB")){%>
    <button type="button" onclick="<%=val_cond%>" class="submit" accesskey="<%=lov_qry%>" id="<%=fld_nme%>" name="<%=fld_nme%>" ><%=fld_ttl%></button>   
    <%}}}
    %>
     </td></tr>
     </table>
</html:form>
</td>
</tr>
  <%
    HashMap datatable= (HashMap)session.getAttribute("datatable");
    ArrayList rowLst = (session.getAttribute("rowLst") == null)?new ArrayList():(ArrayList)session.getAttribute("rowLst");
    ArrayList colLst = (session.getAttribute("colLst") == null)?new ArrayList():(ArrayList)session.getAttribute("colLst");
    String view=util.nvl((String)request.getAttribute("View"));
    int headSpan=0; 
    if(!view.equals("")){
    int rowLstsz=rowLst.size();
    String ht="400";
    if(rowLstsz>5){
    ht=String.valueOf(rowLstsz*50);
    }
    style="width: 50%; height: "+ht+"px; float:left;";
    ArrayList dscLst=new ArrayList();
    dscLst.add("+");dscLst.add("=");dscLst.add("-"); 
    HashMap dscLstDtl=new HashMap();
    dscLstDtl.put("+","P");
    dscLstDtl.put("=","E"); 
    dscLstDtl.put("-","M"); 
    String srchdsc=util.nvl((String)request.getAttribute("srchDscription"));
  %>
  <tr><td valign="top" class="hedPg" nowarp="nowrap"><span class="txtLink" >Search Description : <%= srchdsc%></span>&nbsp;&nbsp;
  <td></tr>
    <%if(view.equals("Y")){
    if(datatable!=null && datatable.size()>0){
    HashMap data=new HashMap();
    HashMap dataGnt=new HashMap();
    headSpan=colLst.size();
    int colLstsz=colLst.size();
    HashMap ttlmap= (HashMap)session.getAttribute("ttlmap");
    String key=util.nvl((String)datatable.get("ROW")); 
    graphkey=key;
    String headTTl=util.nvl((String)datatable.get("HEAD"));
    String rpttyp=util.nvl((String)datatable.get("RPTTYP"));
    String CNT=""; 
    String CNTGNT="";
    HashMap dbinfo = info.getDmbsInfoLst();
  %>
  <tr><td valign="top" class="hedPg">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Create Excel <img src="../images/ico_file_excel.png" onclick="goTo('monthCmp.do?method=createMonthCmpXL','','')" border="0"/>
  &nbsp;&nbsp;&nbsp;<a href="monthCmp.do?method=pktList"  target="_blank">PKTDTL </a>
  <%
  ArrayList rolenmLst=(ArrayList)info.getRoleLst();
  String usrFlg=util.nvl((String)info.getUsrFlg());
  if(usrFlg.equals("SYS") || rolenmLst.contains("ADMIN")){
  %>
  &nbsp;<img src="../images/edit.jpg" title="Edit Properties Module" onclick="newWindow('<%=info.getReqUrl()%>/dynamicProperty.do?method=load&mdl=MTHPKT_COMP&sname=moncmpPrprViw&par=A')" border="0" width="15px" height="15px"/>
  <%}%> 
              <%
            pageList=(ArrayList)pageDtl.get("GRPWISE");
            if(pageList!=null && pageList.size() >0){
            for(int j=0;j<pageList.size();j++){
            pageDtlMap=(HashMap)pageList.get(j);
            fld_nme="value("+(String)pageDtlMap.get("fld_nme")+")";
            fld_ttl=(String)pageDtlMap.get("fld_ttl");
            fld_typ=(String)pageDtlMap.get("fld_typ");
            lov_qry=(String)pageDtlMap.get("lov_qry");
            dflt_val=(String)pageDtlMap.get("dflt_val");
            val_cond=(String)pageDtlMap.get("val_cond"); 
            %>
            &nbsp;&nbsp;<a href="monthCmp.do?method=grpWiseRpt" target="_blank" title="Group Wise Report">Group Wise Report</a>
            <%}}%>
  </td></tr>
  <tr><td class="hedPg"><table class="grid1" id="dataTable">
  <%for(int l=0;l<gridLst.size();l++){
  String grid=(String)gridLst.get(l); 
  int grLn = vWPrpList.indexOf(grid)+1;
  %> 
  <tr> <td colspan="<%=1+(headSpan*3)+headSpan+3%>" align="center"><%=util.nvl((String)mprp.get(grid+"D"))%> - Monthly Comparision</td> </tr>
  <tr><th><%=headTTl%></th>
  <%if(rpttyp.equals("D")){%>
  <th colspan="<%=headSpan*3%>"><%=util.nvl((String)mprp.get(grid+"D"))%></th>
  <%}%>
  <th colspan="<%=headSpan%>">Total</th><th colspan="3">Grand Total</th></tr>
  <tr>
  <td></td>
  <%if(rpttyp.equals("D")){%>
  <th colspan="<%=headSpan%>">+</th>
  <th colspan="<%=headSpan%>">=</th>
  <th colspan="<%=headSpan%>">-</th>
  <%}%>
  <td colspan="<%=headSpan%>"></td>
  <td align="center">+</td>
  <td align="center">=</td>
  <td align="center">-</td>
  </tr>
  <tr>
  <td></td>
  <%
  if(rpttyp.equals("D")){
  for(int i=0;i<dscLst.size();i++){
  for(int j=0;j<colLstsz;j++){
  String col =(String)colLst.get(j);%>
 <td nowrap="nowrap"><%=util.nvl((String)datatable.get(col),col)%></td>
  <%}}}
  for(int j=0;j<colLstsz;j++){
  String col =(String)colLst.get(j);%>
  <td nowrap="nowrap"><%=util.nvl((String)datatable.get(col),col)%></td>
  <%}%>
  <td></td>
  <td></td>
  <td></td>
  </tr>
  <%
  int colspan=1;
  for(int i=0;i<rowLst.size();i++){
  String row =(String)rowLst.get(i);
  String target = "SC_"+row;
  %>
  <tr id="<%=target%>">
  <td nowrap="nowrap"><%=util.nvl((String)datatable.get(row),row)%>&nbsp; 
  <%if(rpttyp.equals("D")){%>
  <img src="../images/plus.jpg" id="src_<%=grLn%>_<%=row%>" onclick="MonthlyCmp('<%=grLn%>','<%=row%>','<%=key%>')" /> 
  <%}%>
  </td>
  <%
  if(rpttyp.equals("D")){
  for(int j=0;j<dscLst.size();j++){
  String dsc=(String)dscLst.get(j); 
  int ttl=0;
  for(int k=0;k<colLstsz;k++){
  CNT="0";CNTGNT="0";
  double per=0.0;
  String column=(String)colLst.get(k); 
  data=new HashMap();
  dataGnt=new HashMap();
  data=(HashMap)datatable.get(row+"_"+column+"_"+dsc+"_"+grid);
  dataGnt=(HashMap)datatable.get(row+"_"+column+"_Total"+"_"+grid);
  if(data!=null && data.size()>0){
  CNT=util.nvl((String)data.get("CNT"));
  CNTGNT=util.nvl((String)dataGnt.get("CNT"));
  per=(Double.parseDouble(CNT)/Double.parseDouble(CNTGNT))*100;
  }
  ttl=ttl+Integer.parseInt(CNT);
  colspan++;
  %>
  <td nowrap="nowrap"><%=CNT%> | <%=Math.round(per)%>%
  <%if(k==(colLstsz-1)){%>
  <a href="monthCmp.do?method=moncmpPKTDTL&sign=<%=dscLstDtl.get(dsc)%>&cnt=<%=grLn%>&row=<%=row%>&key=<%=key%>" id="LNK_<%=grLn%>_<%=row%>" onclick="loadASSFNL('<%=target%>','LNK_<%=grLn%>_<%=row%>')"  target="SC"><img src="../images/plus.png" border="0" title="Click Here to See Packet Details"></a>
  <%}%>
  </td>
  <%}
  }}
  for(int j=0;j<colLst.size();j++){
  CNT="0";
  String column=(String)colLst.get(j);
  data=new HashMap();
  data=(HashMap)datatable.get(row+"_"+column+"_Total"+"_"+grid);
  if(data!=null && data.size()>0)
  CNT=util.nvl((String)data.get("CNT"));
  %>
  <td nowrap="nowrap"><%=CNT%></td>
  <%}
  for(int j=0;j<dscLst.size();j++){
  String dsc=(String)dscLst.get(j);
  CNT=util.nvl((String)ttlmap.get(row+"_"+dsc+"_"+grid),"0");
  String curval=util.nvl((String)ttlmap.get(row+"_"+dsc+"_"+grid+"_CURVAL"),"0");
  String asrtval=util.nvl((String)ttlmap.get(row+"_"+dsc+"_"+grid+"_ASRTVAL"),"0");
  String GRANDCNT=util.nvl((String)String.valueOf(ttlmap.get(row+"_Total_"+grid)));
  String grandcurval=util.nvl((String)String.valueOf(ttlmap.get(row+"_Total_"+grid+"_CURVAL")),"0");
  String grandasrtval=util.nvl((String)String.valueOf(ttlmap.get(row+"_Total_"+grid+"_ASRTVAL")),"0");
  double per=0.0;
  per=(Double.parseDouble(CNT)/Double.parseDouble(GRANDCNT))*100;
  double curper=0.0;
  curper=(Double.parseDouble(curval)/Double.parseDouble(grandcurval))*100;
  double asrtper=0.0;
  asrtper=(Double.parseDouble(asrtval)/Double.parseDouble(grandasrtval))*100;
  %>
  <td nowrap="nowrap"><%=CNT%> | <%=Math.round(per)%>% | <b title="<%=curval%>"><%=curval%>&nbsp<%=Math.round(curper)%>%</b> |<%=asrtval%>&nbsp; <%=Math.round(asrtper)%>%
  <%}%>
  </tr>
  <tr style="display:none" id="<%=row%>_<%=grLn%>"><td></td>

    <%for(int m=0;m<dscLst.size();m++){
    String dsc = (String)dscLst.get(m);
  for(int n=0;n<colLst.size();n++){
   String col = (String)colLst.get(n);%>
  <td valign="top" nowrap="nowrap" width="100%">
  <table width="100%"><tr><td width="100%">
  <div id="<%=row%>_<%=grLn%>_<%=dsc%>_<%=col%>" style="width:100%;vertical-align: top; margin:0px;"></div>
  </td></tr></table></td>
  <%}}%>
  <td colspan="<%=headSpan%>" nowrap="nowrap"></td>
  </tr>
  <%}}%>
  
</table></td>
</tr>
<%}}else if(view.equals("N")){
ArrayList memoLst= (ArrayList)session.getAttribute("memoLst");
if(memoLst!=null && memoLst.size()>0){
int memoLstsz=memoLst.size();
ArrayList itemLst=new ArrayList();
ArrayList itemtypLst=new ArrayList();
itemtypLst.add("COL");
itemtypLst.add("CLA");
String key="",cnt="";
int countdisplay=0;
String percent="";
double eqttl=0;
int colttl=0;
int clrttl=0;
String target = "SC_0";
%>
<tr style="display:none">
<td>
<table class="grid1" id="dataTable">
<tr id="<%=target%>"><td></td></tr>
</table>
</td>
</tr>
<tr>
<td class="hedPg">
<table>
<tr>
<%for(int i=0;i<memoLstsz;i++){
String memoVal=util.nvl((String)memoLst.get(i));
countdisplay=countdisplay+1;
if(countdisplay==4){%>
</tr><tr>
<%countdisplay=1;}%>
<%for(int it=0;it<itemtypLst.size();it++){
String itymtyp=util.nvl((String)itemtypLst.get(it));
int cntttl=0;
int grLn = it+1;
%>

<td valign="top">
<table class="grid1">
<tr>
<th align="center"><%=memoVal%></th>
<th colspan="2" align="center"><%=itymtyp%></th><th colspan="2"></th><th colspan="2"></th>
</tr>
<tr>
<td></td>
<%for(int j=0;j<dscLst.size();j++){%>
<td colspan="2" align="center"><%=dscLst.get(j)%></td>
<%}%>
</tr>
<%itemLst=new ArrayList();
  key=memoVal+"_"+itymtyp;
  itemLst=(ArrayList)datatable.get(key);
  for(int co=0;co<itemLst.size();co++){
  String itm=util.nvl((String)itemLst.get(co));
  %>
  <tr>
  <td align="center"><b><%=itm%></b></td>
  <%for(int k=0;k<dscLst.size();k++){
  String dsc=util.nvl((String)dscLst.get(k));
  key=memoVal+"_"+itm+"_"+dsc+"_"+itymtyp;
  cnt=util.nvl((String)datatable.get(key));
  %>
  <td colspan="2" align="center"><%=cnt%></td>
  <%}%>
  </tr>
  <%}%>
  <tr>
  <%for(int tt=0;tt<dscLst.size();tt++){
  String dsc=util.nvl((String)dscLst.get(tt));
  key=memoVal+"_"+dsc+"_"+itymtyp+"_TTL";
  cnt=util.nvl2((String)datatable.get(key),"0");
  cntttl=cntttl+Integer.parseInt(cnt);%>
  <%}%>
  <td nowrap="nowrap" align="center"><b>Total : </b><%=cntttl%></td>
  <%for(int tt=0;tt<dscLst.size();tt++){
  String dsc=util.nvl((String)dscLst.get(tt));
  key=memoVal+"_"+dsc+"_"+itymtyp+"_TTL";
  cnt=util.nvl((String)datatable.get(key));
  double getcnt=0;
  percent="";
  if(!cnt.equals("")){
  getcnt=Double.parseDouble(cnt);
  percent=String.valueOf(util.roundToDecimals(((getcnt/cntttl)*100),1))+"%";
  }
  %>
  <td align="center"><%=cnt%></td>
  <td align="center" nowrap="nowrap">
  <%=percent%>
  <a href="monthCmp.do?method=moncmpPKTDTL&sign=<%=dscLstDtl.get(dsc)%>&cnt=<%=grLn%>&memoVal=<%=memoVal%>" id="LNK_<%=grLn%>_<%=memoVal%>" onclick="loadASSFNL('<%=target%>','LNK_<%=grLn%>_<%=memoVal%>')"  target="SC">
  <img src="../images/plus.png" border="0" title="Click Here to See Packet Details"></a>
  </td>
  <%}%>
  </tr>
  <%
  eqttl=0;
  if(itymtyp.equals("COL")){
   key=memoVal+"_=_COL_TTL";
   cnt=util.nvl2((String)datatable.get(key),"0");
   eqttl=eqttl+Double.parseDouble(cnt);
   key=memoVal+"_=_CLA_TTL";
   cnt=util.nvl2((String)datatable.get(key),"0");
   eqttl=eqttl+Double.parseDouble(cnt);
   cntttl=0;
   for(int a=0;a<itemtypLst.size();a++){
   itymtyp=util.nvl((String)itemtypLst.get(a));
   for(int b=0;b<dscLst.size();b++){
  String dsc=util.nvl((String)dscLst.get(b));
  key=memoVal+"_"+dsc+"_"+itymtyp+"_TTL";
  cnt=util.nvl2((String)datatable.get(key),"0");
  cntttl=cntttl+Integer.parseInt(cnt);%>
  <%}}
  percent=String.valueOf(util.roundToDecimals(((eqttl/cntttl)*100),1))+"%";
  %>
  <tr>
  <td colspan="7"><b><%=eqttl%>&nbsp;|&nbsp;<%=percent%></b></td>
  </tr>
  <%}%>
</table>
</td>
<%}}%>
</tr>
</table>
</td>
</tr>
<tr>
<td valign="top" class="hedPg">
<table class="grid1">
<tr><td colspan="14"><b>Final</b></td></tr>
<tr>
<%for(int it=0;it<itemtypLst.size();it++){
String itymtyp=util.nvl((String)itemtypLst.get(it));%>
<th><%=itymtyp%></th>
<%
for(int k=0;k<dscLst.size();k++){
String dsc=util.nvl((String)dscLst.get(k));%>
<th colspan="2"><%=dsc%></th>
<%}}%>
</tr>
<tr>
<%

for(int k=0;k<dscLst.size();k++){
String dsc=util.nvl((String)dscLst.get(k));
for(int i=0;i<memoLstsz;i++){
String memoVal=util.nvl((String)memoLst.get(i));
key=memoVal+"_"+dsc+"_COL_TTL";
cnt=util.nvl2((String)datatable.get(key),"0");
colttl=colttl+Integer.parseInt(cnt);
%>
<%}%>
<%}%>
<td><%=colttl%></td>
<%
int cntttl=0;
double getcnt=0;
for(int it=0;it<itemtypLst.size();it++){
String itymtyp=util.nvl((String)itemtypLst.get(it));
for(int k=0;k<dscLst.size();k++){
String dsc=util.nvl((String)dscLst.get(k));
cntttl=0;
for(int i=0;i<memoLstsz;i++){
String memoVal=util.nvl((String)memoLst.get(i));
key=memoVal+"_"+dsc+"_"+itymtyp+"_TTL";
cnt=util.nvl2((String)datatable.get(key),"0");
cntttl=cntttl+Integer.parseInt(cnt);
getcnt=Double.parseDouble(String.valueOf(cntttl));
if(itymtyp.equals("COL")){
percent=String.valueOf(util.roundToDecimals(((getcnt/colttl)*100),1))+"%";
}if(itymtyp.equals("CLA")){
percent=String.valueOf(util.roundToDecimals(((getcnt/clrttl)*100),1))+"%";
}%>
<%}%>
<td><%=cntttl%></td>
<td><%=percent%></td>
<%}
if(it==0){
for(int kk=0;kk<dscLst.size();kk++){
String clrdsc=util.nvl((String)dscLst.get(kk));
for(int ii=0;ii<memoLstsz;ii++){
String memoclrVal=util.nvl((String)memoLst.get(ii));
key=memoclrVal+"_"+clrdsc+"_CLA_TTL";
cnt=util.nvl2((String)datatable.get(key),"0");
clrttl=clrttl+Integer.parseInt(cnt);
%>
<%}%>
<%}%>
<td><%=clrttl%></td>
<%}}%>
</tr>
<%
eqttl=0;
int cntgrandttl=clrttl+colttl;
for(int i=0;i<memoLstsz;i++){
String memoVal=util.nvl((String)memoLst.get(i));
   key=memoVal+"_=_COL_TTL";
   cnt=util.nvl2((String)datatable.get(key),"0");
   eqttl=eqttl+Double.parseDouble(cnt);
   key=memoVal+"_=_CLA_TTL";
   cnt=util.nvl2((String)datatable.get(key),"0");
   eqttl=eqttl+Double.parseDouble(cnt);
  }
  percent=String.valueOf(util.roundToDecimals(((eqttl/cntgrandttl)*100),1))+"%";
  %>
  <tr>
  <td  valign="top" class="hedPg" colspan="14"><b><%=eqttl%>&nbsp;|&nbsp;<%=percent%></b></td>
  </tr>
</table>
</td>
</tr>
<%}}else{%>
<tr><td valign="top" class="hedPg">
Sorry No Result Found</td></tr>
<%}}%>  </table> 
<%
if(!graphkey.equals("")){%>
<input type="hidden" id="barGrp" value="P_E_M">
<%for(int l=0;l<gridLst.size();l++){
  String grid=(String)gridLst.get(l); 
  int grLn = vWPrpList.indexOf(grid)+1;
  String styleId=grid+"_HIDD";
  String styleIdTTl=grid+"_TTL";%>
  <div>
  <input type="hidden" id="<%=styleId%>" value="<%=grid%>">
  <input type="hidden" id="<%=styleIdTTl%>" value="<%=util.nvl((String)mprp.get(grid+"D"))%>">
  <div id="<%=grid%>" style="<%=style%>">
  </div>
  </div>
<%}
String url="ajaxRptAction.do?method=monthlyCmpBarGraph&key="+graphkey;%>
<script type="text/javascript">
<!--
$(window).bind("load", function() {
callcreatedoubleBarGraph('<%=url%>','Monthly Comparision','50','362');
});
 -->
</script>  
<%}%>
   <%@include file="/calendar.jsp"%>
  </body>
</html>