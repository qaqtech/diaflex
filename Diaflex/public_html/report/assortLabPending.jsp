<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page errorPage="../error_page.jsp" %>
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import="java.io.*,java.util.ArrayList,java.util.ArrayList,java.util.HashMap, java.sql.*, java.util.Enumeration, java.util.logging.Level"%>
<jsp:useBean id="info" class="ft.com.InfoMgr" scope="session" />
<jsp:useBean id="util" class="ft.com.JspUtil" scope="page" />
<%@taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>

<html>
  <head>
 <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
  
  <title>Issue Pending Report</title>
  <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/bse.js?v=<%=info.getJsVersion()%> " > </script>
 <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/bse.css?v=<%=info.getJsVersion()%>"/>
 <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/ajax.js?v=<%=info.getJsVersion()%> " > </script>
  <script language="javaScript" type="text/javascript" src="<%=info.getReqUrl()%>/calendar/calendar.js"></script>
   <link href="<%=info.getReqUrl()%>/calendar/calendar.css" rel="stylesheet" type="text/css">
    <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/style.css?v=<%=info.getJsVersion()%>"/>
  </head>
 
   <%
   ArrayList memoRtnLst = info.getMomoRtnLst();
    HashMap allPageDtl= (info.getPageDetails() == null)?new HashMap():(HashMap)info.getPageDetails();
    HashMap totals = (request.getAttribute("totalMap") == null)?new HashMap():(HashMap)request.getAttribute("totalMap");
    HashMap pageDtl=(HashMap)allPageDtl.get("ASSORT_LAB");
    ArrayList pageList=new ArrayList();
    HashMap pageDtlMap=new HashMap();
    String fld_nme="",fld_ttl="",val_cond="",dflt_val="",fld_typ="",form_nme="",flg1="",lov_qry="";
        String logId=String.valueOf(info.getLogId());
        String onfocus="cook('"+logId+"')";
        ArrayList PktDtlList = (ArrayList)request.getAttribute("PktDtlList");
        %>
 <body onfocus="<%=onfocus%>" onload="MM_preloadImages('images/faunalogo.png')" topmargin="0" leftmargin="0" rightmargin="0" bottommargin="0">
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
  <img src="../images/line.jpg" border="0" /></td><td valign="middle"> <span class="pgHed">
 Issue Pending Report</span> </td>
</tr></table>
  </td>
  </tr>
 
  
   <tr><td valign="top" class="hedPg"  >
    <html:form action="/report/assortLabPending.do?method=save" method="post" >
    <html:hidden property="value(grp)" name="assortLabPenForm"  />
    <table>
   <tr><td>Process </td>
   <td>
   <html:select property="value(prcIdn)"  styleId="mprcIdn" name="assortLabPenForm"    >
           <html:option value="0" >--select--</html:option>
            <html:optionsCollection property="mprcList" name="assortLabPenForm" 
            label="prc" value="mprcId" />
    </html:select>
   </td></tr>
   <tr>
    <td>
    Buyer/Employee : </td>
   <td>
   <%
   String urlsuggest="../ajaxAction.do?1=1";
   pageList=(ArrayList)pageDtl.get("SUGGESTION");
      if(pageList!=null && pageList.size() >0){
      for(int j=0;j<pageList.size();j++){
      pageDtlMap=(HashMap)pageList.get(j);
      urlsuggest=(String)pageDtlMap.get("dflt_val");
      }}
      String keyStr = "doCompletion('partytext', 'nmePop', '"+urlsuggest+"', event)";
      %>
  <html:text  property="value(partytext)" name="assortLabPenForm" styleId="partytext" style="width:180px" styleClass="sugBox" onkeypress="return disableEnterKey(event);" 
           onkeyup="<%=keyStr%>"
      onkeydown="setDownSerchpage('nmePop', 'party', 'partytext',event)" onclick="document.getElementById('partytext').autocomplete='off'"  />
      <img src="../images/addrow.jpg" width="17" height="17" title="Click To get Names" onclick="doCompletionMobile('partytext', 'nmePop', '<%=urlsuggest%>')"/>
      <html:hidden property="value(empIdn)" styleId="party"  />
  <div style="position: absolute;">
      <select id="nmePop" name="nmePop"
        style="display:none;300px;" 
        class="sugBoxDiv" 
        onKeyDown="setDownSerchpage('nmePop', 'party', 'partytext',event)" 
        onDblClick="setVal('nmePop', 'party', 'partytext', event);hideObj('nmePop')" 
        onClick="setVal('nmePop', 'party', 'partytext', event)" 
        size="10">
      </select>
</div>            
   </td>
   </tr>
    
   <tr><td colspan="2" align="center">
   <table><tr>
   <td>Date:</td><td> From : </td><td>
   <html:text property="value(frmdte)" name="assortLabPenForm" styleId="frmdte"/>
                <img src="<%=info.getReqUrl()%>/images/calender.png" onClick="setYears(2011, <%=info.getCurrentYear()%>);showCalender(this, 'frmdte');">
   </td><td>To : </td><td>
   <html:text property="value(todte)" name="assortLabPenForm" styleId="todte"/>
                <img src="<%=info.getReqUrl()%>/images/calender.png" onClick="setYears(2011, <%=info.getCurrentYear()%>);showCalender(this, 'todte');">

   </td>
   
   </tr></table></td></tr>
    <tr><td colspan="2" align="center"><html:submit property="value(srch)" value="Fetch" styleClass="submit" /></td> </tr>
  
   </table>
 </html:form>
   </td></tr>
<%pageList=(ArrayList)pageDtl.get("BUTTON");
      if(pageList!=null && pageList.size() >0){%>
      <tr>
      <td valign="top" class="hedPg"  >
      <%
      for(int j=0;j<pageList.size();j++){
      pageDtlMap=(HashMap)pageList.get(j);
      fld_nme="value("+(String)pageDtlMap.get("fld_nme")+")";
      fld_ttl=(String)pageDtlMap.get("fld_ttl");
      lov_qry=(String)pageDtlMap.get("lov_qry");
      fld_typ=(String)pageDtlMap.get("fld_typ");
      flg1=(String)pageDtlMap.get("flg1");%>
      <button type="button" class="submit" onclick="<%=lov_qry%>"><%=fld_ttl%></button>&nbsp;&nbsp;
      <%
      }%>
      </td>
      </tr>
      <%}
%>
<tr>
<td valign="top" class="hedPg">
<table>
<tr>
<td>Total :&nbsp;Qty&nbsp;</td>
<td><span id="ttlqty"> <%=totals.get("qty")%></span></td>
<td>Cts&nbsp;</td>
<td><span id="ttlcts"><%=totals.get("cts")%></span></td>
<td>Vlu&nbsp;</td>
<td><span id="ttlcts"><%=totals.get("vlu")%></span></td>
</tr>
</table>
</td>
</tr>
  <tr><td valign="top" class="hedPg"  >
  <%System.out.println(PktDtlList.size());
  if(PktDtlList!=null && PktDtlList.size()>0){
  
  %>
  <table class="dataTable">
  <tr><th>SR NO.</th>
  <th>Action</th>
  <th>Issue Id</th>
  <th>Issue Date</th>
  <th>Process</th>
  <th>Employee Name</th>
  <th>Qty</th>
  <th>Cts</th>
  <th>Value</th>
</tr>
 <%for(int i=0;i<PktDtlList.size();i++){
    HashMap map = (HashMap)PktDtlList.get(i);
 
            String isIdn = (String)map.get("issIdn");
            String emp = util.nvl((String)map.get("emp")).toUpperCase();
            System.out.println(emp);
            String rtnVal=isIdn;
            String reqUrl = (String)map.get("requrl");
            String homeDir = (String)map.get("homeDir");
            String webDir = (String)map.get("webDir");
            String cnt = (String)map.get("cnt");
            System.out.println(emp.indexOf("LAB-"));
            if(emp.indexOf("LAB-")!=-1){
               String lab = (emp.toUpperCase()).replaceAll("LAB-", "");
                rtnVal="";
                pageList= ((ArrayList)pageDtl.get("CHK") == null)?new ArrayList():(ArrayList)pageDtl.get("CHK");                
                if(pageList!=null && pageList.size() >0){
                    for(int j=0;j<pageList.size();j++){
                        pageDtlMap=(HashMap)pageList.get(j);
                        fld_ttl=(String)pageDtlMap.get("fld_ttl");
                        dflt_val=(String)pageDtlMap.get("dflt_val");
                        fld_ttl = fld_ttl.replaceAll("ISSIDN", isIdn);
                        dflt_val = dflt_val.replaceAll("LAB", lab);
                        rtnVal = "<input type=\"checkbox\" name=\""+fld_ttl+"\" id=\""+fld_ttl+"\" value=\""+dflt_val+"\"> &nbsp;|&nbsp;";
                        }
                }
                pageList= ((ArrayList)pageDtl.get("LINK") == null)?new ArrayList():(ArrayList)pageDtl.get("LINK");
                if(pageList!=null && pageList.size() >0){
                    for(int j=0;j<pageList.size();j++){
                        pageDtlMap=(HashMap)pageList.get(j);
                        fld_ttl=(String)pageDtlMap.get("fld_ttl");
                        dflt_val=(String)pageDtlMap.get("dflt_val");
                        lov_qry=(String)pageDtlMap.get("lov_qry");
                        form_nme=(String)pageDtlMap.get("form_nme");
                        flg1=(String)pageDtlMap.get("flg1");
                        val_cond=(String)pageDtlMap.get("val_cond");    
                        if(val_cond.equals(""))
                        dflt_val = reqUrl+dflt_val.replaceAll("ISSIDN", isIdn);
                        else
                        dflt_val = webDir+dflt_val.replaceAll("ISSIDN", isIdn);    
                        dflt_val = dflt_val.replaceAll("LAB", lab);
                        if(form_nme.equals("")){
                        if(pageList.size()-1!=j)
                        rtnVal += "<a href=\""+dflt_val+"\" target=\""+flg1+"\">&nbsp;"+ fld_ttl +"</a>&nbsp;|&nbsp; " ;
                        else
                            rtnVal += "<a href=\""+dflt_val+"\" target=\""+flg1+"\">&nbsp;"+ fld_ttl +"</a>" ; 
                        }else if(form_nme.equals(lab)){
                            rtnVal += "<a href=\""+dflt_val+"\" target=\""+flg1+"\">&nbsp;"+ fld_ttl +"</a>" ;
                        }
                        }
                }                
            }else{

                rtnVal = "<a href=\""+reqUrl+"/report/assortLabPending.do?method=loadPkt&issueIdn="+isIdn+"\" >Packets Detail</a>";
                pageList=(ArrayList)pageDtl.get("ISSUEFNLASSORT_VIEW");
                if(pageList!=null && pageList.size() >0){
                for(int j=0;j<pageList.size();j++){
                    pageDtlMap=(HashMap)pageList.get(j);
                    fld_ttl=(String)pageDtlMap.get("fld_ttl");
                    rtnVal =rtnVal+" | <a href=\""+reqUrl+"/report/assortLabPending.do?method=loadPktFnlAssortdata&issueIdn="+isIdn+"\" >"+fld_ttl+"</a>";
                }}
            }
  %>
  <tr>
 <td><%=(String)map.get("Count")%></td>
 <td><%=rtnVal%></td>
 <td><%=(String)map.get("issIdn")%></td>
 <td><%=(String)map.get("issDte")%></td>
 <td><%=(String)map.get("prc")%></td>
 <td><%=(String)map.get("emp")%></td>
 <td><%=(String)map.get("qty")%></td>
 <td><%=(String)map.get("cts")%></td>
 <td><%=(String)map.get("vlu")%></td>
 </tr>
   <%}%>
   </table>
 <% }%>

  </td></tr>
  <tr>
    <td><jsp:include page="/footer.jsp" /> </td>
    </tr>
  </table>
  
  </body>
  <%@include file="../calendar.jsp"%>
</html>