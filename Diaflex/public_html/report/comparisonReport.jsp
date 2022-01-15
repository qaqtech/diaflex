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
    <title>Comparison Report</title>
     <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/bse.css?v=<%=info.getJsVersion()%>"/>
      <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/style.css?v=<%=info.getJsVersion()%>"/>
     <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/bse.js?v=<%=info.getJsVersion()%> " > </script>
      <script language="javaScript" type="text/javascript" src="<%=info.getReqUrl()%>/calendar/calendar.js"></script>
      <link href="<%=info.getReqUrl()%>/calendar/calendar.css" rel="stylesheet" type="text/css" />
  </head>
<%
String stt = util.nvl((String)request.getAttribute("stt"));
boolean isIssue = false;
if(stt.equals("LB_RS") || stt.equals("LB_RI"))
 isIssue = true;
        String logId=String.valueOf(info.getLogId());
        String onfocus="cook('"+logId+"')";
        
        HashMap allPageDtl= (info.getPageDetails() == null)?new HashMap():(HashMap)info.getPageDetails();
HashMap pageDtl=(HashMap)allPageDtl.get("COMPARISON_REPORT");
    ArrayList pageList=new ArrayList();
    HashMap pageDtlMap=new HashMap();
    String fld_nme="",fld_ttl="",val_cond="",dflt_val="",fld_typ="",form_nme="",lov_qry="";

        %>
 <body onfocus="<%=onfocus%>" onload="MM_preloadImages('images/faunalogo.png')" topmargin="0" leftmargin="0" rightmargin="0" bottommargin="0">
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
  <img src="../images/line.jpg" border="0" /></td><td valign="middle"> <span class="pgHed">
    Comparison Report</span> </td>
</tr></table>
  </td>
  </tr>
  <%
  String msg = (String)request.getAttribute("msg");
  if(msg!=null){
  %>
  <tr><td valign="top" class="hedPg"  ><%=msg%></td></tr>
  <%}%>
  <tr><td valign="top" class="hedPg"  >
  <table><tr><td>
  <html:form  action="report/comparisonReport.do?method=view" method="post" >
  <table class="grid1">
  
  <tr><td>Report Type</td>
  <td>
  <html:select property="value(reportTyp)" name="comparisonReportForm">
   <html:option value="NONE">None</html:option>
  <html:option value="OK">Color And Clarity OK</html:option>
  <html:option value="UP">Upgrades</html:option>
  <html:option value="CCDN">Color or Clarity Downgrade </html:option>
  <html:option value="OTHDN">Others Downgrade</html:option>
  </html:select>
  </td>
  </tr>

  <tr><td>Recheck Stones</td><td><html:checkbox property="value(recheck)" name="comparisonReportForm" value="YES" /> </td></tr>
 
    <tr><td colspan="2"><table><tr>
  <td colspan="2" >Lab Sequence </td><td colspan="3">
    <html:text property="value(seq)" name="comparisonReportForm" />
   </td></tr>
   <tr>
   <td colspan="5">Or</td></tr>
   <tr>
   <td>Date</td><td>From</td><td> <html:text property="value(frmdte)" styleId="frmdte" name="comparisonReportForm" /><img src="<%=info.getReqUrl()%>/images/calender.png" onClick="setYears(2011, <%=info.getCurrentYear()%>);showCalender(this, 'frmdte');"> </td>
   <td>To</td><td><html:text property="value(todte)" styleId="todte" name="comparisonReportForm" /> <img src="<%=info.getReqUrl()%>/images/calender.png" onClick="setYears(2011, <%=info.getCurrentYear()%>);showCalender(this, 'todte');"></td>
   </tr></table></td>
   </tr>
   
   
   
  <tr><td>Status</td>
  <td>
  <html:select property="value(stt)" name="comparisonReportForm" styleId="memoPg">
  <html:option value="PR">Post Return </html:option>
  <html:option value="IN">In Lab</html:option>
  
  </html:select>
  </td>
  </tr>
  <tr>
<td>Packet Id </td>
<td><html:textarea property="value(vnm)" name="comparisonReportForm" styleId="pid"/>
</td>
</tr>
  <tr><td colspan="2"><B>Properties List</b>
  <%
  ArrayList rolenmLst=(ArrayList)info.getRoleLst();
  String usrFlg=util.nvl((String)info.getUsrFlg());
  if(usrFlg.equals("SYS") || rolenmLst.contains("ADMIN")){
  %>
  <img src="../images/edit.jpg" title="Edit Properties Module" onclick="newWindow('<%=info.getReqUrl()%>/dynamicProperty.do?method=load&mdl=COM_RPT_SRCH&sname=COM_RPT_SRCH&par=GENERIC')" border="0" width="15px" height="15px"/> 
  <%}%>
  </td> </tr>
  <tr><td colspan="2">
  <jsp:include page="/genericSrch.jsp" />
  </td> </tr>
  <tr><td colspan="2" align="center"><html:submit property="value(submit)" value="Submit" styleClass="submit" /> </td> </tr>
  </table></html:form></td>
  <td valign="top">
 
  <%
  ArrayList genViewPrp = (session.getAttribute("LAB_UP_DW") == null)?new ArrayList():(ArrayList)session.getAttribute("LAB_UP_DW"); 
  HashMap colClrUPDWMap = (HashMap)request.getAttribute("colClrUPDWMap");
  if(colClrUPDWMap!=null && colClrUPDWMap.size()>0){
  HashMap mprp = info.getMprp();
       %>
<table class="grid1">
 <tr><th>Propety</th><th>+</th><th>-</th><th>=</th></tr> 
 <%for(int i=0;i<genViewPrp.size();i++){
 String prpVal=util.nvl((String)genViewPrp.get(i));
 int ttlprp = Integer.parseInt(util.nvl((String)colClrUPDWMap.get(prpVal+"_+"),"0")) +  Integer.parseInt(util.nvl((String)colClrUPDWMap.get(prpVal+"_-"),"0")) + Integer.parseInt(util.nvl((String)colClrUPDWMap.get(prpVal+"_="),"0"));  
 %>
 <tr> <th><%=util.nvl((String)mprp.get(prpVal+"D"),prpVal)%></th>
 <td>
 <a title="Packet Details" target="pktDtl"  href="comparisonReport.do?method=pktDtl&lprp=<%=prpVal%>&sign=P" >
 <%=util.nvl((String)colClrUPDWMap.get(prpVal+"_+"),"0")%></a></td>
 <td>
  <a title="Packet Details" target="pktDtl"  href="comparisonReport.do?method=pktDtl&lprp=<%=prpVal%>&sign=M" >
 <%=util.nvl((String)colClrUPDWMap.get(prpVal+"_-"),"0")%></a></td>
 <td>
   <a title="Packet Details" target="pktDtl"  href="comparisonReport.do?method=pktDtl&lprp=<%=prpVal%>&sign=E" >
 <%=util.nvl((String)colClrUPDWMap.get(prpVal+"_="),"0")%></a></td>
 </tr>
 <tr> <th> % </th><td><b><%=Math.round((Float.parseFloat(util.nvl((String)colClrUPDWMap.get(prpVal+"_+"),"0"))/ttlprp)*100)%>%</b> </td><td><b><%=Math.round((Float.parseFloat(util.nvl((String)colClrUPDWMap.get(prpVal+"_-"),"0"))/ttlprp)*100)%>%</b></td><td><b><%=Math.round((Float.parseFloat(util.nvl((String)colClrUPDWMap.get(prpVal+"_="),"0"))/ttlprp)*100)%>%</b></td></tr>
 <%}%>
</table>
 <%}%>
 
  </td>
   </tr></table>
  
  </td></tr>
    <tr><td valign="top" class="tdLayout">
    &nbsp;
    </td></tr>
     <html:form  action="report/comparisonReport.do?method=Issue" method="post" onsubmit="return validate_issue('CHK_' , 'count')">
    <tr><td valign="top" class="tdLayout">
 <%
 
  String plusCom = "";
    pageList= ((ArrayList)pageDtl.get("PLUSMNCOM") == null)?new ArrayList():(ArrayList)pageDtl.get("PLUSMNCOM");
         if(pageList!=null && pageList.size() >0){
          pageDtlMap=(HashMap)pageList.get(0);
          plusCom=util.nvl((String)pageDtlMap.get("dflt_val")); 
          if(plusCom.equals(""))
            plusCom="N";
          }
 String view = (String)request.getAttribute("view");
 if(view!=null){
    ArrayList prpDspBlocked = info.getPageblockList();
    String rptTyp = (String)request.getAttribute("rptTyp");
    ArrayList pktLst = (ArrayList)session.getAttribute("pktList");
    if(pktLst != null && pktLst.size() > 0){
     ArrayList itemHdr = new ArrayList();
    HashMap mprp = info.getMprp();
    HashMap prpList = info.getPrp();
    HashMap dbInfoSys = info.getDmbsInfoLst();
    String col = util.nvl((String)dbInfoSys.get("COL"));
    String clr = util.nvl((String)dbInfoSys.get("CLR"));
     String cut = util.nvl((String)dbInfoSys.get("CUT"));
     String symval = (String)dbInfoSys.get("SYM");
     String polval = (String)dbInfoSys.get("POL");
     String flval = (String)dbInfoSys.get("FL");
      ArrayList vwPrpLst = (ArrayList)session.getAttribute("LbComViewLst");
     int sr =0;
     HashMap dbinfo = info.getDmbsInfoLst();
    String cnt = (String)dbinfo.get("CNT");
    %>
    
  <table><tr>
  <%if(isIssue){%>
  <td><html:submit property="value(issue)" value="Issue"   styleClass="submit" />&nbsp;&nbsp; </td><%}%>
  <td><html:button onclick="goTo('comparisonReport.do?method=createXL','','')" property="value(crtExl)" value="Create Excel" styleClass="submit" /> </td>
  <td><html:button onclick="goTo('comparisonReport.do?method=assortLabXL','','')" property="value(alabExl)" value="Assort Lab Comparison" styleClass="submit" /></td>
   <td><html:button onclick="goTo('comparisonReport.do?method=multiComXL','','')" property="value(multiComExl)" value="Multi Comparison" styleClass="submit" /></td>
   <td><html:button onclick="goTo('comparisonReport.do?method=multiLabComXL','','')" property="value(multiLabComXL)" value="Multi Lab Comparison" styleClass="submit" /></td>
   <td><html:button onclick="goTo('comparisonReport.do?method=labExcel','','')" property="value(labExcel)" value="Lab Result Comparision Excel" styleClass="submit" /></td>
   <%if(cnt.equals("ag")){%>
   <td><html:button onclick="goTo('comparisonReport.do?method=fnlAssortXL','','')" property="value(fnlAssortXL)" value="Packets Details With Fnl Assort" styleClass="submit" /></td>
   <%}%>
  <%
  ArrayList rolenmLst=(ArrayList)info.getRoleLst();
  String usrFlg=util.nvl((String)info.getUsrFlg());
  if(usrFlg.equals("SYS") || rolenmLst.contains("ADMIN")){
  %>
  <td><img src="../images/edit.jpg" title="Edit Properties Module" onclick="newWindow('<%=info.getReqUrl()%>/dynamicProperty.do?method=load&mdl=LBCOM_VIEW&sname=LbComViewLst&par=A')" border="0" width="15px" height="15px"/></td>
  <%}
  if(cnt.equals("kj") || cnt.equals("ku") ){
  %>
  <td>&nbsp;&nbsp;<a href="comparisonReport.do?method=grpWiseRpt" target="_blank" title="Group Wise Report">Group Wise Report</a></td>
  <%}%>
  </tr></table>
    <table cellspacing="2" class="dataTable">
    
      <tr><th>Sr</th>
       <th><input type="checkbox" name="checkExlAll" id="checkExlAll" onclick="checkExlALL('CHKEXL_','count');compExlAll();" /> Excel </th>
        <%if(isIssue){%>
        <th><input type="checkbox" name="checkAll" id="checkAll" onclick="checkALL('CHK_','count')" /> Issue All</th>
         <%}%>
        <th>Packet</th><th>Employee</th>
     <%
     itemHdr.add("vnm");
     itemHdr.add("emp");
     for(int j=0; j < vwPrpLst.size(); j++ ){
      String prp = (String)vwPrpLst.get(j);
      if(prpDspBlocked.contains(prp)){
      }else{
     String hdr = (String)mprp.get(prp);
    String prpDsc = (String)mprp.get(prp+"D");
    if(hdr == null) {
        hdr = prp;
       }  
    if(prp.equals(cut) && !rptTyp.equals("CCDN")){%>
    <th title="<%=prpDsc%>">Our Val <%=prpDsc%></th>
    <%}
    if(prp.equals(col)||prp.equals(clr) || prp.equals(symval)||prp.equals(polval)||prp.equals(flval) ||prp.equals("CRTWT")){%>
    <th title="<%=prpDsc%>">Our Val  <%=prpDsc%></th>
   <% 
   
       
   }  %>
    <th title="<%=prpDsc%>"><%=hdr%></th>
  <%
 if(prp.equals(col)){
    itemHdr.add("issCol");
    itemHdr.add("rtnCol");
    }else if(prp.equals(clr)){   
    itemHdr.add("issClr");
     itemHdr.add("rtnClr");
    }else if(prp.equals(polval)){   
    itemHdr.add("issPol");
     itemHdr.add("rtnPol");
    }else if(prp.equals(symval)){   
    itemHdr.add("issSym");
     itemHdr.add("rtnSym");
    }else if(prp.equals(flval)){   
    itemHdr.add("issFl");
     itemHdr.add("rtnFl");
    }else if(prp.equals("CRTWT")){   
    itemHdr.add("issCRTWT");
     itemHdr.add("rtnCRTWT");
    }else if (prp.equals(cut) && !rptTyp.equals("CCDN")){
    itemHdr.add("issCut");
    itemHdr.add("rtnCut");
    } else{
    itemHdr.add(prp);
    }
  }}
 session.setAttribute("itemHdrCOMP", itemHdr);
  %>       
        
      </tr>
      <%
      
    for(int i=0; i < pktLst.size(); i++) {
      HashMap pktDtl = (HashMap)pktLst.get(i);
      String vnm = util.nvl((String)pktDtl.get("vnm"));
      String stkIdn = util.nvl((String)pktDtl.get("stkIdn"));
      String emp = util.nvl((String)pktDtl.get("emp"));
       sr = i+1;
      String checkFldId = "CHK_"+sr;
      String checkFldVal = "value("+stkIdn+")";
     String checkExlFldId = "CHKEXL_"+sr;
     String onclick = "compExl("+stkIdn+", this);";
     %>
        <tr>
        <td align="left"><%=(i+1)%></td>
        <td><html:checkbox property="<%=checkFldVal%>" styleId="<%=checkExlFldId%>"  name="comparisonReportForm" onclick="<%=onclick%>" value="yes"  /> </td>
         <%if(isIssue){%>
        <td align="left"><html:checkbox property="<%=checkFldVal%>" styleId="<%=checkFldId%>" name="comparisonReportForm"   value="yes" /></td>
        <%}%>
        <td align="center"><%=vnm%></td>
        <td align="left"><%=emp%></td>
       
  <%for(int j=0; j < vwPrpLst.size(); j++ ){
    String prp = (String)vwPrpLst.get(j);
    if(prpDspBlocked.contains(prp)){
    }else{
     String hdr = (String)mprp.get(prp);
    String prpDsc = (String)mprp.get(prp+"D");
    if(hdr == null) {
        hdr = prp;
       }  
     String cellBG="";
     if(prp.equals(col) || prp.equals("CRTWT") || prp.equals(clr) || prp.equals(symval)||prp.equals(polval)||prp.equals(flval) || (prp.equals(cut) && !rptTyp.equals("CCDN"))){
     String getIsPrpStr="issCut";
     String getrtnPrpStr="rtnCut";
     
     if(prp.equals(col)){
     getIsPrpStr="issCol";
     getrtnPrpStr="rtnCol";
     }
     if(prp.equals(clr)){
     getIsPrpStr="issClr";
     getrtnPrpStr="rtnClr";
     }
     if(prp.equals(polval)){
     getIsPrpStr="issPol";
     getrtnPrpStr="rtnPol";
     }
     if(prp.equals(symval)){
     getIsPrpStr="issSym";
     getrtnPrpStr="rtnSym";
     }
     if(prp.equals(flval)){
     getIsPrpStr="issFl";
     getrtnPrpStr="rtnFl";
     }
     if(prp.equals("CRTWT")){
     getIsPrpStr="issCRTWT";
     getrtnPrpStr="rtnCRTWT";
     }
     String issVal = util.nvl((String)pktDtl.get(getIsPrpStr));
     if(!issVal.equals("") && !prp.equals("CRTWT") ){
        String rtnVal = util.nvl((String)pktDtl.get(getrtnPrpStr));
       String issValCmp = util.nvl((String)pktDtl.get(getIsPrpStr));
        String rtnValCmp = util.nvl((String)pktDtl.get(getrtnPrpStr));
       if(prp.equals(cut) || prp.equals(symval) || prp.equals(polval)) {
        if(plusCom.equals("N")){
        issValCmp = issValCmp.replaceAll("\\+","");
        issValCmp = issValCmp.replaceAll("\\-","");
        issValCmp = issValCmp.replace('1',' ');
        issValCmp = issValCmp.replace('2',' ');
        issValCmp = issValCmp.replace('3',' ');
        issValCmp = issValCmp.replace('4',' ');
        
        rtnValCmp = rtnValCmp.replaceAll("\\+","");
        rtnValCmp = rtnValCmp.replaceAll("\\-","");
        rtnValCmp = rtnValCmp.replace('1',' ');
        rtnValCmp = rtnValCmp.replace('2',' ');
        rtnValCmp = rtnValCmp.replace('3',' ');
        rtnValCmp = rtnValCmp.replace('4',' ');
        }
         issValCmp = issValCmp.trim();
        rtnValCmp = rtnValCmp.trim();
       }else{ 
         if(plusCom.equals("N")){
             String issstr = issValCmp.substring(issValCmp.length()-1, issValCmp.length());
           while(issstr.equals("+") || issstr.equals("-")){
           issValCmp = issValCmp.substring(0,issValCmp.length()-1);
           issstr = issValCmp.substring(issValCmp.length()-1, issValCmp.length());
          }
           
         String rtnstr = rtnValCmp.substring(rtnValCmp.length()-1, rtnValCmp.length());
           while(rtnstr.equals("+") || rtnstr.equals("-")){
           rtnValCmp = rtnValCmp.substring(0,rtnValCmp.length()-1);
           rtnstr = rtnValCmp.substring(rtnValCmp.length()-1, rtnValCmp.length());
          }}
           issValCmp = issValCmp.trim();
           rtnValCmp = rtnValCmp.trim();
    }
    ArrayList prpSrtLst = (ArrayList)prpList.get(prp+"S");
    ArrayList prpValLst =(ArrayList)prpList.get(prp+"V");
    int issSrt=0;
    int rtnSrt=0;
      if(prpValLst.indexOf(issValCmp)!=-1)
     issSrt  = Integer.parseInt((String)prpSrtLst.get(prpValLst.indexOf(issValCmp)));
       if(prpValLst.indexOf(rtnValCmp)!=-1)
     rtnSrt = Integer.parseInt((String)prpSrtLst.get(prpValLst.indexOf(rtnValCmp)));
     if(!issVal.equals("NA")){
     if(issSrt!=0 && rtnSrt!=0){
   if(issSrt < rtnSrt){
      cellBG="background-color: Red;";
      
      }
   if(issSrt > rtnSrt )
       cellBG ="background-color: Lime;";
     
   }}}
     }
    if(prp.equals(cut) && !rptTyp.equals("CCDN")){%>
    <td title="<%=prpDsc%>" style="<%=cellBG%>"><%=util.nvl((String)pktDtl.get("issCut"))%></td>
     <td title="<%=prpDsc%>" style="<%=cellBG%>"><%=util.nvl((String)pktDtl.get("rtnCut"))%></td>
    <%}else if(prp.equals(col)){%>
   <td title="<%=prpDsc%>" style="<%=cellBG%>"><%=util.nvl((String)pktDtl.get("issCol"))%></td>
    <td title="<%=prpDsc%>" style="<%=cellBG%>"><%=util.nvl((String)pktDtl.get("rtnCol"))%></td>
   <%}else if(prp.equals(clr)){%>
    <td title="<%=prpDsc%>" style="<%=cellBG%>"><%=util.nvl((String)pktDtl.get("issClr"))%></td>
     <td title="<%=prpDsc%>" style="<%=cellBG%>"><%=util.nvl((String)pktDtl.get("rtnClr"))%></td>
   <%}else if(prp.equals(polval)){%>
    <td title="<%=prpDsc%>" style="<%=cellBG%>"><%=util.nvl((String)pktDtl.get("issPol"))%></td>
     <td title="<%=prpDsc%>" style="<%=cellBG%>"><%=util.nvl((String)pktDtl.get("rtnPol"))%></td>
   <%}else if(prp.equals(symval)){%>
    <td title="<%=prpDsc%>" style="<%=cellBG%>"><%=util.nvl((String)pktDtl.get("issSym"))%></td>
     <td title="<%=prpDsc%>" style="<%=cellBG%>"><%=util.nvl((String)pktDtl.get("rtnSym"))%></td>
   <%}else if(prp.equals(flval)){%>
    <td title="<%=prpDsc%>" style="<%=cellBG%>"><%=util.nvl((String)pktDtl.get("issFl"))%></td>
     <td title="<%=prpDsc%>" style="<%=cellBG%>"><%=util.nvl((String)pktDtl.get("rtnFl"))%></td>
   <%}else if(prp.equals("CRTWT")){%>
    <td title="<%=prpDsc%>" style="<%=cellBG%>"><%=util.nvl((String)pktDtl.get("issCRTWT"))%></td>
     <td title="<%=prpDsc%>" style="<%=cellBG%>"><%=util.nvl((String)pktDtl.get("rtnCRTWT"))%></td>
   <%}else{%>
     <td title="<%=prpDsc%>"><%=util.nvl((String)pktDtl.get(prp))%></td>
   <%}}}%>       
    
        </tr>
      <%}%>
    </table>
     <input type="hidden" name="count" id="count" value="<%=sr%>" />
    
    <%}else{%>
    Sorry no result found.
   <%}}%>
   </td></tr>
 </html:form>
  </td></tr>
   <tr>
    <td><jsp:include page="/footer.jsp" /> </td>
    </tr>
  </table>
  
  </body>
</html>