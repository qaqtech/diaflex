<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page errorPage="../error_page.jsp" %>
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import="java.util.ArrayList,java.util.Set,java.util.LinkedHashMap,java.util.HashMap,java.sql.ResultSet,ft.com.*, java.util.Date, java.util.ArrayList, ft.com.dao.*"%>
<jsp:useBean id="util" class="ft.com.JspUtil" scope="page" />
<jsp:useBean id="info" class="ft.com.InfoMgr" scope="session" />
<%@taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>
<%@taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>

<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>assortRtnUpd</title>
    <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/bse.css?v=<%=info.getJsVersion()%>"/>
      <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/style.css?v=<%=info.getJsVersion()%>"/>
   <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/general.css?v=<%=info.getJsVersion()%>"/>

      <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/assortScript.js?v=<%=info.getJsVersion()%> " > </script>
      <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/bse.js?v=<%=info.getJsVersion()%> " > </script>
        <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/ajax.js?v=<%=info.getJsVersion()%> " > </script>
          <script type="text/javascript" src="<%=info.getReqUrl()%>/PopScripts/popup.js?v=<%=info.getJsVersion()%> " > </script>
       <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/pri.js?v=<%=info.getJsVersion()%> " > </script>
   <script language="javaScript" type="text/javascript" src="<%=info.getReqUrl()%>/calendar/calendarFmt.js"></script>
   <link href="<%=info.getReqUrl()%>/calendar/calendar.css" rel="stylesheet" type="text/css">
      <script src="<%=info.getReqUrl()%>/PopScripts/jquery-1.2.6.min.js" type="text/javascript"></script>

      
  </head>
    <%
    //util.updAccessLog(request,response,"Assort Return", "Assort Return");
   String logId=String.valueOf(info.getLogId());
   String onfocus="cook('"+logId+"')";
   String noFd = util.nvl2(request.getParameter("COUNT"),"4");
   if(noFd==null)
      noFd="4";
      
      int noFdInt = Integer.parseInt(noFd);
     ArrayList vwPrpLst = (ArrayList)session.getAttribute("SPLITVIEWLST");
    HashMap mprp = info.getMprp();
    HashMap   prp = info.getPrp();
    String stkIdn = request.getParameter("mstkIdn");
       String issId = request.getParameter("issIdn");
       String ctsval = request.getParameter("ttlcts");
         String qtyval = request.getParameter("ttlQty");
         String avgRte = util.nvl(request.getParameter("avgRte"),"0");
           String cp = util.nvl(request.getParameter("cp"),"0");
         String issStt = (String)request.getAttribute("isStt");
           String vnm = request.getParameter("vnm");
           String mdl = util.nvl(request.getParameter("mdl"),"RGH_VIEW");
       ArrayList msgLst = (ArrayList)request.getAttribute("msgLst");
       HashMap SplitStoneList = (HashMap)request.getAttribute("SplitStoneList");
       HashMap rte_dtl = (HashMap)request.getAttribute("rte_dtl");
   
       HashMap rootPrpMap = (HashMap)request.getAttribute("RootPrpMap");
       Integer cntNum = (Integer)request.getAttribute("lstCnt");
       String stt = (String)request.getAttribute("stt");
       String grp = (String)request.getAttribute("grp");
       String VRFCTS = (String)request.getAttribute("VRFCTS");
       String BALCTS = (String)request.getAttribute("BALCTS");
       String VRFQTY = (String)request.getAttribute("VRFQTY");
       String BALQTY = (String)request.getAttribute("BALQTY");
       String VRFCPVAL = util.nvl((String)request.getAttribute("TTLCPVAL"));
       String cnt = util.nvl((String)info.getDmbsInfoLst().get("CNT"));
       double ttlCpVal = 0.0;
       cntNum++;
       if(SplitStoneList==null)
         SplitStoneList = new HashMap();
         int lstSize = SplitStoneList.size();
         if(lstSize>noFdInt)
         noFdInt = lstSize;
      String isVerify="";
      
      HashMap allPageDtl= (info.getPageDetails() == null)?new HashMap():(HashMap)info.getPageDetails();
         HashMap pageDtl=(HashMap)allPageDtl.get(issStt+"_SPLIT");
    ArrayList pageList=new ArrayList();
    HashMap pageDtlMap=new HashMap();
    String fld_nme="",fld_ttl="",val_cond="",dflt_val="",fld_typ="",form_nme="",lov_qry="";
      String validRtn = "return loading()";
      String calQtyLock="calttlVNQTY(this)";
      String  calCtsLock = "calttlRGHCRTWT(this)";
      String calCpfun="";
      String calavgRte="0";
     boolean isCpValueLock = false;
      pageList= ((ArrayList)pageDtl.get("VERTIFY") == null)?new ArrayList():(ArrayList)pageDtl.get("VERTIFY");
         if(pageList!=null && pageList.size() >0){
          pageDtlMap=(HashMap)pageList.get(0);
          validRtn=util.nvl((String)pageDtlMap.get("dflt_val")); 
          }
           pageList= ((ArrayList)pageDtl.get("CALQTY") == null)?new ArrayList():(ArrayList)pageDtl.get("CALQTY");
         if(pageList!=null && pageList.size() >0){
          pageDtlMap=(HashMap)pageList.get(0);
          calQtyLock=util.nvl((String)pageDtlMap.get("dflt_val")); 
          }
          pageList= ((ArrayList)pageDtl.get("CALCTS") == null)?new ArrayList():(ArrayList)pageDtl.get("CALCTS");
         if(pageList!=null && pageList.size() >0){
          pageDtlMap=(HashMap)pageList.get(0);
          calCtsLock=util.nvl((String)pageDtlMap.get("dflt_val")); 
          }
          double balTtlCp = 0;
           pageList= ((ArrayList)pageDtl.get("CALCP") == null)?new ArrayList():(ArrayList)pageDtl.get("CALCP");
         if(pageList!=null && pageList.size() >0){
          pageDtlMap=(HashMap)pageList.get(0);
          String calCpLock=util.nvl((String)pageDtlMap.get("dflt_val")); 
          calCpfun= util.nvl((String)pageDtlMap.get("lov_qry"));
          if(calCpLock.equals("Y")){
          isCpValueLock=true;
          if(!ctsval.equals("") && !cp.equals("")){
            double ctsDval = Double.parseDouble(ctsval);
            double cpD = Double.parseDouble(cp);
           ttlCpVal = util.roundToDecimals(ctsDval*cpD,2);
           double vfyCp = Double.parseDouble(VRFCPVAL);
           balTtlCp = ttlCpVal-vfyCp;
           calavgRte=avgRte;
           }
          
          }
          }
         
      
    %>
 <body onfocus="<%=onfocus%>" >
 <div id="backgroundPopup"></div>
<div id="popupContactSave" align="center" >
<img src="../images/loading_flame.gif" height="150px" width="150px" border="0" />
</div>
 <table cellpadding="4" cellspacing="4">
<%if(msgLst!=null && msgLst.size()>0){
isVerify = util.nvl((String)msgLst.get(0));
%>
<tr><td><span class="redLabel"><%=util.nvl((String)msgLst.get(1))%></span> </td></tr>
<%}%>
<html:form action="rough/roughReturnAction.do?method=Verify" method="post" onsubmit="<%=validRtn%>">
  <input type="hidden" value="<%=noFd%>" name="COUNT" id="COUNT" />
  <input type="hidden" value="<%=stkIdn%>" name="mstkIdn" id="mstkIdn" />
   <input type="hidden" value="<%=grp%>" name="grp" id="grp" />
  <input type="hidden" value="<%=stt%>" name="stt" id="stt" />
  <input type="hidden" value="<%=issId%>" name="issIdn" id="issIdn" />
   <input type="hidden" value="<%=vnm%>" name="vnm" id="vnm" />
    <input type="hidden" value="<%=mdl%>" name="mdl" id="mdl" />
     <input type="hidden" value="<%=avgRte%>" name="avgRte" id="avgRte" />
    <input type="hidden" value="<%=ctsval%>" name="ttlcts" id="ttlcts" />
    <input type="hidden" value="<%=qtyval%>" name="ttlQty" id="ttlQty" />
    <input type="hidden" id="reqUrl" name="reqUrl" value="<%=info.getReqUrl()%>"/>
   <input type="hidden" id="cp" name="cp" value="<%=cp%>" />

<tr><td>
 <table cellpadding="2" cellspacing="2">
 <tr><td> 
 <%if(!isVerify.equals("YES")){%>
 <input type="submit" value="Verify" class="submit" name="verify" />
 <%}%>
 </td> <td> 
 <%if(isVerify.equals("YES")){%>
   <input type="button" value="Lock" name="lock" onclick="LOCKPKT('<%=stkIdn%>','<%=issId%>')" class="submit" /> 

 <%}%>
 <%
 
 %>
 </td>
</tr></table>
</td></tr>
<tr><td>
<table>
<tr>
 <td>Total Qty :<b> <label id="TTLQTYSMT"> <%=qtyval%></label></b> </td>
 <td>Verified Qty :<b><label id="ttlqty"> <%=VRFQTY%></label></b> </td>
 <td>Balance Qty :<b><label id="balqty"> <%=BALQTY%></label></b> </td>
 <td>New Qty :<b><label id="addqty"> 0</label></b> </td>
 
</tr>
<tr>
  <td>Total Cts :<b> <label id="TTLCTSMT"> <%=ctsval%></label></b> </td>
  <td>Verified Cts :<b><label id="ttlcts"> <%=VRFCTS%></label></b> </td>
  <td>Balance Cts :<b><label id="balcts"> <%=BALCTS%></label></b> </td>
  <td>New Cts :<b><label id="addcts"> 0</label></b> </td>
  <td>&nbsp; <b>Avg RTE :<label id="avgRTE"> <%=calavgRte%></label></b> </td>
</tr>
<%if(isCpValueLock){%>
<tr>

<td>Total Cp Val :<b> <label id="TTLCPMT"> <%=ttlCpVal%></label></b> </td>
  <td>Verified Cp Val :<b><label id="ttlcp"> <%=VRFCPVAL%></label></b> </td>
  <td>Balance Cp Val :<b><label id="balcp"> <%=balTtlCp%></label></b> </td>
   <td>&nbsp; <b>Avg CP :<label id="avgCP"> <%=cp%></label></b> </td>
 

</tr>
<%}%>
</table>
</td></tr>
<tr><td>
   <div class="memo">
 <table class="Orange" cellspacing="1" cellpadding="1">
 
  <%
   if(!noFd.equals("") || !noFd.equals("0")){
   
   %><tr><th class="Orangeth">Packet Code</th><th class="Orangeth">QTY</th>
  <% for(int j=0;j<vwPrpLst.size();j++){
  String lprp =(String)vwPrpLst.get(j);
    String lprpD = (String)mprp.get(lprp+"D");
    %>
  <th class="Orangeth"><%=lprpD%></th>
 <% }%>
   </tr>
  <% 
  
  ArrayList stockIdnLst =new ArrayList();
 LinkedHashMap stockList = new LinkedHashMap(SplitStoneList); 

 Set<String> keys = stockList.keySet();
        for(String key: keys){
       stockIdnLst.add(key);
        }

  for(int i=0;i<noFdInt;i++){

      GtPktDtl stoneDtl = new GtPktDtl();
      String stk_idn="";
     if(lstSize > i){
      stk_idn = (String)stockIdnLst.get(i);
      stoneDtl= (GtPktDtl)SplitStoneList.get(stk_idn);
      }else
      stoneDtl = new GtPktDtl();
      String cts = util.nvl((String)stoneDtl.getValue("cts"));
      String qty = util.nvl((String)stoneDtl.getValue("qty"));
      String curvnm = util.nvl((String)stoneDtl.getValue("vnm"));
      if(curvnm.equals("")){
      curvnm = vnm+"."+cntNum++;
      }
     
  %>
  <tr>
  <td><input type="text" name="VNM_<%=i%>" id="VNM_<%=i%>" value="<%=curvnm%>" readonly="readonly" />
  <input type="hidden" name="IDN_<%=i%>" value="<%=stk_idn%>"/>
  </td>
  <td><input type="text" name="QTY_<%=i%>" id="QTY_<%=i%>" size="5" onchange="<%=calQtyLock%>" value="<%=qty%>" /></td>
  <%for(int j=0;j<vwPrpLst.size();j++){
  String lprp =(String)vwPrpLst.get(j);
  String ltyp = (String)mprp.get(lprp+"T");
  String lval = util.nvl((String)stoneDtl.getValue(lprp));
  String dfltVal = util.nvl((String)rootPrpMap.get(lprp));
  String clval = "";
  if(lval.equals("")){
  if(!dfltVal.equals(""))
  lval = dfltVal;
  }
  String onChange="";
  if(lprp.equals("CRTWT")){
  onChange=calCtsLock;
  
  }
  
  if(lprp.equals("CP") && isCpValueLock && lval.equals("") && i==0)
  lval=cp;
  
  if(lprp.equals("CP"))
  onChange=calCpfun;
  
  onChange=onChange.replace("CURCNT", String.valueOf(i));
  if(ltyp.equals("C")){
  
  ArrayList prpList = (ArrayList)prp.get(lprp+"V");
  ArrayList prpSrtLst = (ArrayList)prp.get(lprp+"S");
  ArrayList prpPrtLst = (ArrayList)prp.get(lprp+"P");
  %>
  <td>
  <select id="CB@<%=lprp%>@<%=i%>"   name="<%=lprp%>_<%=i%>">
    <%for(int k=0;k<prpList.size();k++){
    String prpVal=(String)prpList.get(k);
    String prpSrt=(String)prpSrtLst.get(k);
    String prpPrt = (String)prpPrtLst.get(k);
    String selected="";
    if(prpVal.equals(lval))
    selected="selected=\"selected\"";
    else if(prpPrt.equals(lval))
    selected="selected=\"selected\"";
    %>
    <option value="<%=prpVal%>" <%=selected%> ><%=prpVal%></option>
    <%}%>  
  </select>
  
  
  </td>
  <%}else{
  String isredonly="";
  if(lprp.equals("ALT_LOTNO") || lprp.equals("CT_INWARD_NO"))
  isredonly="readonly=\"readonly\"";
  if(lprp.equals("RTE"))
  onChange="setAvgRte('"+i+"')";
  
  %>
  <td><input type="text" name="<%=lprp%>_<%=i%>" id="CB@<%=lprp%>@<%=i%>" size="8"  onchange="<%=onChange%>" value="<%=lval%>" <%=isredonly%>/></td>
  <%}%>
  <%}%>
  </tr>
  <%}}else{%>
 <tr><td> Please give no. of packets need to create.</td></tr>
  <%}%>
  </table></div></td></tr>
  </html:form>
  </table>
 </body>
</html>