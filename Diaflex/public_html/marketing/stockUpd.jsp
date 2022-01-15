<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
 
<%@ page errorPage="../error_page.jsp" %>
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import="java.util.ArrayList,java.util.HashMap,java.sql.ResultSet,ft.com.*, java.util.Date, java.util.ArrayList, ft.com.dao.*"%>
<jsp:useBean id="util" class="ft.com.JspUtil" scope="page" />
<jsp:useBean id="info" class="ft.com.InfoMgr" scope="session" />
<%@taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>
<%@taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>Stock Properties Update</title>
    <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/bse.css?v=<%=info.getJsVersion()%>"/>
      <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/style.css?v=<%=info.getJsVersion()%>"/>
      <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/memoScript.js?v=<%=info.getJsVersion()%> " > </script>
      <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/bse.js?v=<%=info.getJsVersion()%> " > </script>
             <script language="javaScript" type="text/javascript" src="<%=info.getReqUrl()%>/calendar/calendarFmt.js"></script>
                     <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/ajax.js?v=<%=info.getJsVersion()%> " > </script>

                <link href="<%=info.getReqUrl()%>/calendar/calendar.css" rel="stylesheet" type="text/css">
        </head>
        <%String logId=String.valueOf(info.getLogId());
        String onfocus="cook('"+logId+"')";
        %>
 <body onfocus="<%=onfocus%>">
  <%
   HashMap allPageDtl= (info.getPageDetails() == null)?new HashMap():(HashMap)info.getPageDetails();
     HashMap pageDtl=(HashMap)allPageDtl.get("BULK_PRP_UPD");
      ArrayList pageList=new ArrayList();
      HashMap pageDtlMap=new HashMap();
      String fld_nme="",fld_ttl="",val_cond="",dflt_val="",fld_typ="",form_nme="",lov_qry="";
  HashMap dbInfoSys = info.getDmbsInfoLst();
  String mdl = util.nvl(request.getParameter("mdl"));
   ArrayList prpDspBlocked = info.getPageblockList();
   ArrayList viewPrp = (ArrayList)session.getAttribute("PRPUPDDFLTList");
   HashMap stockPrpUpd = (HashMap)request.getAttribute("stockList");
   ArrayList prpUpdList = (ArrayList)info.getValue("prpUpdLst");
   String mstkIdn = (String)request.getAttribute("mstkIdn");
   ArrayList grpList = (ArrayList)session.getAttribute("grpList");
   String grpVal = util.nvl((String)request.getAttribute("grp"));
   String cutClrVal = util.nvl((String)request.getAttribute("cutClr"));
   String viewMdl = util.nvl((String)request.getAttribute("viewMdl"));
   String editmdl = util.nvl((String)request.getAttribute("editmdl"));
  String col = util.nvl((String)dbInfoSys.get("COL"));
  String clr = util.nvl((String)dbInfoSys.get("CLR"));
   if(mdl.equals("AS_PRC_EDIT")){
   if(grpVal.equals("1")){
    grpList = new ArrayList();
    grpList.add("1");
   }
   }else{
     cutClrVal="0";
     }
   HashMap mprp = info.getMprp();
    HashMap prp = info.getPrp();
    HashMap dbinfo = info.getDmbsInfoLst();
    String cnt = (String)dbinfo.get("CNT");
   if(grpList!=null && grpList.size() >0){
   String lprp="";
  %>
    <html:form  action="/marketing/StockPrpUpd.do?method=save">
    <%for(int g=0;g < grpList.size();g++){
    String grp=(String)grpList.get(g);
    String labID= "lab_"+grp;
    String labname="value("+labID+")";
    %>
  <html:hidden property="<%=labname%>" styleId="<%=labID%>" name="stockPrpUpdForm" />
  <%}%>
    <html:hidden property="value(mstkIdn)" styleId="mstkIdn" name="stockPrpUpdForm" />
     <html:hidden property="value(mdl)" name="stockPrpUpdForm" />
    <html:hidden property="value(issueId)" styleId="issueId" name="stockPrpUpdForm" />
  <table cellpadding="0" cellspacing="0">
  <tr><td><label id="msgLbl" class="redLabel"></label> </td></tr>
  <tr><td>
  
  <%
  ArrayList rolenmLst=(ArrayList)info.getRoleLst();
  String usrFlg=util.nvl((String)info.getUsrFlg());
  if(usrFlg.equals("SYS") || rolenmLst.contains("ADMIN")){%>
  &nbsp;&nbsp;<img src="../images/edit.jpg" title="Edit Properties Module" onclick="newWindow('<%=info.getReqUrl()%>/dynamicProperty.do?method=load&mdl=<%=viewMdl%>&sname=PRPUPDDFLTList&par=A')" border="0" width="15px" height="15px"/>
  &nbsp;&nbsp;<img src="../images/edit.jpg" title="Edit Editable Properties Module" onclick="newWindow('<%=info.getReqUrl()%>/dynamicProperty.do?method=load&mdl=<%=editmdl%>&sname=&par=')" border="0" width="15px" height="15px"/>
  <%}%> 
  </td></tr>
 
    <% 
    pageList=(ArrayList)pageDtl.get("LINK");
      if(pageList!=null && pageList.size() >0){
  ArrayList items = (ArrayList) request.getAttribute("ITEMS");
  
  %>
   <tr><td>
   <table class="gridEmp" id="dataTable" >
  <tr>


        
  <%
    int iCounter=0;
    if(items!=null && items.size()>0){
    %>
     <td style="padding:0">
   <table>
             <tr><th></th></tr>
             <tr><td style="padding:0">Assort Price History</td></tr>   
             </table></td>
    <%
    for(int i=0; i < items.size(); i++) {
        iCounter++;
        HashMap dtls = (HashMap)items.get(i);
        String employee = (String)dtls.get("employee");
        String rate = (String)dtls.get("rate");
        %>
        <td  style="padding:0">
        <table>
             <tr><th ><%=employee%></th></tr>
             <tr><td style="padding:0"><%=rate%></td></tr>   
             </table>
          </td>
          
    <%    
    }
   
%>
<% } else { %>
   <td>Assort Price History No Data Found</td>
  <%}
  
  %>
  
   <td  style="padding:0">
        <table>
             <tr><th >Remark</th></tr>
             <tr><td style="padding:0">
               <html:text property="value(mstkRmk)" onchange="saveEmpRemark('empRemark')"  styleId="empRemark"  size="30" name="stockPrpUpdForm"/>

             
             </td>
             
             <td> <label id="remarkLabel" class="redLabel"></label></td>
             </tr>   
             </table>
          </td>
   </tr>
  </table>
  </td></tr>
  <%}%>
  
  <tr><td>
<logic:present name="stockPrpUpdForm" property="msg">
<span class="redLabel"> <bean:write name="stockPrpUpdForm" property="msg" /></span>
</logic:present>
&nbsp;
  </td></tr>
  <tr><td>
  <div class="memo">
  <table  width="790" cellpadding="0" cellspacing="2">
  
  <tr><td bgcolor="White" valign="top">
  <table border="0" class="Orange" cellspacing="0" cellpadding="0" >
          
           <tr><th class="Orangeth">PROPERTY</th>
           <%for(int g=0;g < grpList.size();g++){
           String grp = (String)grpList.get(g);
           %>
           <th class="Orangeth">Grp <%=grp%> </th>
           <%}%>
           </tr>
           
  <%
  int count=0;
  int srchSize = (viewPrp.size()/3)+1;
  for(int i=0;i<viewPrp.size() ;i++){

   lprp = (String)viewPrp.get(i);
if(prpDspBlocked.contains(lprp)){
}else{

  
    if(count==srchSize){
             count=0;
             %></table></td><td valign="top">
            <table border="0" class="Orange" cellspacing="0" cellpadding="0" >
          
         <tr><th class="Orangeth" nowrap="nowrap">PROPERTY</th>
         
          <%for(int g=0;g < grpList.size();g++){
           String grp = (String)grpList.get(g);
           %>
           <th class="Orangeth"  nowrap="nowrap">Grp <%=grp%> </th>
           <%}%>
         </tr>
          
           <% }
           count++;
           %>

<tr> <td><%=lprp%></td>
<%for(int g=0;g < grpList.size();g++){
        String grp = (String)grpList.get(g);
        String lprpVal = util.nvl((String)stockPrpUpd.get(mstkIdn+"_"+lprp+"_"+grp));
           %>
      <td>
<%if(prpUpdList.contains(lprp) && (grp.equals("1") || cnt.equals("hk"))){
       String typ = util.nvl((String)mprp.get(lprp+"T"));
       String fldId = lprp+"_"+grp;
       String  fldName = "value("+fldId+")";
       String onchange = "StockPrpUpdate(this,'"+lprp+"','"+grp+"','"+typ+"')";
       if(typ.equals("C")){
         ArrayList prpVal = (ArrayList)prp.get(lprp+"V");
  if(prpVal!=null){
  %>
  <html:select property="<%=fldName%>" styleId="<%=fldId%>" name="stockPrpUpdForm" onchange="<%=onchange%>" >
  <html:option value="0">--select--</html:option>
  <%for(int j=0;j<prpVal.size();j++){
  String prpPrt = (String)prpVal.get(j);
  boolean isDispaly = false;
  if(cutClrVal.equals("1") && (lprp.equals(col) || lprp.equals(clr))){
      String subStr = lprpVal ;
//      subStr = subStr.replaceAll("+", "");
//      subStr = subStr.replaceAll("-", "");
//      
      
     // if(lprpVal.indexOf("+") > -1)
     subStr = subStr.replace('+',' ');
      subStr = subStr.replace('+',' ');
   // if(lprpVal.indexOf("-") > -1)
     subStr = subStr.replace('-', ' ');
     subStr = subStr.replace('-', ' ');
     subStr = subStr.trim();
  if(prpPrt.indexOf(subStr) > -1)
     isDispaly = true;
    
  }else{
  isDispaly = true;
  }
  if(isDispaly){
  %>
  <html:option value="<%=prpPrt%>"><%=prpPrt%></html:option>
  <%}}%>
  </html:select>
  <%}%>
  <%} else if(typ.equals("N")){%>
   <html:text property="<%=fldName%>" onchange="<%=onchange%>" styleId="<%=fldId%>" size="4" name="stockPrpUpdForm"/>
   <%}else if(typ.equals("D")){%>
  <html:text property="<%=fldName%>" onfocus="<%=onchange%>" styleId="<%=fldId%>"  readonly="true"  name="stockPrpUpdForm" size="11" /><img src="<%=info.getReqUrl()%>/images/calender.png" onClick="setYears(2011, <%=info.getCurrentYear()%>);showCalender(this, '<%=fldId%>');"> 
  <%} else{%>
  <html:text property="<%=fldName%>" onchange="<%=onchange%>" styleId="<%=fldId%>" size="15" name="stockPrpUpdForm"/>
  <%}%>
       <%}else{%>
        <%=lprpVal%>
       <%}%>
      </td>     
<%}%>
</tr>
<%}}%>
  </table></td></tr></table>
  
  </div>
  </td></tr>
  </table>
  </html:form>
  <%}%>
  
  </body>
        <%@include file="../calendar.jsp"%>
</html>
