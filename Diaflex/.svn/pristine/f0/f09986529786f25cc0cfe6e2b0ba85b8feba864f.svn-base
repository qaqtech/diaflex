<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
 
<%@ page errorPage="../error_page.jsp" %>
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import="java.util.ArrayList,java.util.HashMap,java.sql.ResultSet,ft.com.*, java.util.Date, java.util.ArrayList, ft.com.dao.*"%>
<jsp:useBean id="util" class="ft.com.JspUtil" scope="page" />
<jsp:useBean id="info" class="ft.com.InfoMgr" scope="session" />
<%@taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>

<%@taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>Offer History</title>
    <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/bse.css?v=<%=info.getJsVersion()%>"/>
      <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/style.css?v=<%=info.getJsVersion()%>"/>
      <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/memoScript.js?v=<%=info.getJsVersion()%> " > </script>
        <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/bse.js?v=<%=info.getJsVersion()%> " > </script>
         <script language="javaScript" type="text/javascript" src="<%=info.getReqUrl()%>/calendar/calendar.js"></script>
   <link href="<%=info.getReqUrl()%>/calendar/calendar.css" rel="stylesheet" type="text/css">
         <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/ajax.js?v=<%=info.getJsVersion()%> " > </script>
          <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/priceChange.js?v=<%=info.getJsVersion()%> " > </script>
        </head>
        <%String logId=String.valueOf(info.getLogId());
        String onfocus="cook('"+logId+"')";
        %>
 <body onfocus="<%=onfocus%>" onload="MM_preloadImages('images/faunalogo.png')" topmargin="0" leftmargin="0" rightmargin="0" bottommargin="0">
 <%
  
   HashMap mprp = info.getMprp();
  ArrayList prpDspBlocked = info.getPageblockList();
  HashMap prp = info.getPrp();
HashMap allPageDtl= (info.getPageDetails() == null)?new HashMap():(HashMap)info.getPageDetails();
HashMap pageDtl=(HashMap)allPageDtl.get("OFFER_HISTORY");
    ArrayList pageList=new ArrayList();
    HashMap pageDtlMap=new HashMap();
    String fld_nme="",fld_ttl="",val_cond="",dflt_val="",fld_typ="",form_nme="",flg1="",lov_qry="";
  HashMap dbinfo = info.getDmbsInfoLst();
  String cnt = (String)dbinfo.get("CNT");
              boolean byrplDis=false;
              boolean plDis=false;
              pageList= ((ArrayList)pageDtl.get("BYR_PL") == null)?new ArrayList():(ArrayList)pageDtl.get("BYR_PL");  
                        if(pageList!=null && pageList.size() >0){
                        for(int j=0;j<pageList.size();j++){
                        byrplDis=true;
                        }
             }
             pageList= ((ArrayList)pageDtl.get("PL") == null)?new ArrayList():(ArrayList)pageDtl.get("PL");  
                        if(pageList!=null && pageList.size() >0){
                        for(int j=0;j<pageList.size();j++){
                        plDis=true;
                        }
             }

  %>
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
  <img src="../images/line.jpg" border="0" /></td><td valign="middle"> <span class="pgHed">Offer History</span> </td>
</tr></table></td></tr>
<%
 String msg =(String)request.getAttribute("msg");
 if(msg!=null){
%>
<tr>
  <td valign="top" class="tdLayout" >
 <span class="redLabel"> <%=msg%></span>
  </td></tr>
<%}%>
<tr>
  <td valign="top" class="tdLayout" >
  <html:form action="marketing/offerPrice.do?method=srch" method="post" >
  <table class="grid1" >
  <tr><th>Generic Search 
  <%
  ArrayList rolenmLst=(ArrayList)info.getRoleLst();
  String usrFlg=util.nvl((String)info.getUsrFlg());
  if(usrFlg.equals("SYS") || rolenmLst.contains("ADMIN")){
  %>
  <img src="../images/edit.jpg" title="Edit Properties Module" onclick="newWindow('<%=info.getReqUrl()%>/dynamicProperty.do?method=load&mdl=OFFER_RPT&sname=OfferSrch&par=GENERIC')" border="0" width="15px" height="15px"/> 
  <%}%>
  </th><th></th></tr>
  <tr><td valign="top"> <jsp:include page="/genericSrch.jsp"/></td>
  
  <td><table>
  <%
   ArrayList cusList = (ArrayList)session.getAttribute("cusList");
  %>
  <tr><td>Buyer List</td><td colspan="2" align="center">
  <html:select  property="value(nmeIdn)" style="width:400px" name="offerPriceForm" > 
      <html:option value="" >-------Select buyer-------</html:option> 
  <%for(int l=0;l<cusList.size();l++){
  ArrayList cus = (ArrayList)cusList.get(l);
  String nmeId = (String)cus.get(0);
  String nmeNme = (String)cus.get(1);
  %>
   <html:option value="<%=nmeId%>" ><%=nmeNme%></html:option> 
  <%}%>
  </html:select>
  </td></tr>
  <%
   ArrayList empList = (ArrayList)session.getAttribute("empList");
  %>
  <tr><td>Employee  List</td><td colspan="2" align="center">
  <html:select  property="value(empIdn)" style="width:400px" name="offerPriceForm" > 
      <html:option value="" >-------Select-------</html:option> 
  <%for(int l=0;l<empList.size();l++){
  ArrayList emp = (ArrayList)empList.get(l);
  String nmeId = (String)emp.get(0);
  String nmeNme = (String)emp.get(1);
  %>
   <html:option value="<%=nmeId%>" ><%=nmeNme%></html:option> 
  <%}%>
  </html:select>
  </td></tr>
  <tr> <td>Date</td>
  <td>From : &nbsp;
   <html:text property="value(frmdte)" name="offerPriceForm"  styleId="frmdte"/>
                <img src="<%=info.getReqUrl()%>/images/calender.png" onClick="setYears(2011, <%=info.getCurrentYear()%>);showCalender(this, 'frmdte');">
   </td><td>To : &nbsp;
   <html:text property="value(todte)" name="offerPriceForm"  styleId="todte"/>
                <img src="<%=info.getReqUrl()%>/images/calender.png" onClick="setYears(2011, <%=info.getCurrentYear()%>);showCalender(this, 'todte');">

   </td>
  </tr>
   <tr><td>Packet No</td><td colspan="2" align="center"><html:textarea cols="40" rows="4" property="value(vnm)" name="offerPriceForm"   /> </td></tr>
 
 <%
            pageList=(ArrayList)pageDtl.get("CHK");
             if(pageList!=null && pageList.size() >0){%>
             <tr><td>Offer From</td>
             <td colspan="2" align="left">
             <%
               for(int j=0;j<pageList.size();j++){
                 pageDtlMap=(HashMap)pageList.get(j);
                 fld_nme="value("+(String)pageDtlMap.get("fld_nme")+")";
                 fld_ttl=(String)pageDtlMap.get("fld_ttl");
                 dflt_val=(String)pageDtlMap.get("dflt_val");
                 fld_typ=(String)pageDtlMap.get("fld_typ");
                 form_nme=(String)pageDtlMap.get("form_nme");
                 if(!fld_typ.equals("S")){
                 %>
               <html:checkbox property="<%=fld_nme%>" value="<%=dflt_val%>" name="<%=form_nme%>"/><%=fld_ttl%>&nbsp;
             <%}else{%>
             <html:submit property="<%=fld_nme%>" value="<%=dflt_val%>" styleClass="submit"/>
             <%}}%>
             </td> </tr>
            <%}%>
 <%
            pageList=(ArrayList)pageDtl.get("TYP");
             if(pageList!=null && pageList.size() >0){%>
             <tr><td>Type</td>
             <td colspan="2" align="left">
             <html:select  property="value(pepTyp)" style="width:400px" name="offerPriceForm" > 
             <%
               for(int j=0;j<pageList.size();j++){
                 pageDtlMap=(HashMap)pageList.get(j);
                 fld_nme=(String)pageDtlMap.get("fld_nme");
                 fld_ttl=(String)pageDtlMap.get("fld_ttl");
                 dflt_val=(String)pageDtlMap.get("dflt_val");
                 fld_typ=(String)pageDtlMap.get("fld_typ");
                 form_nme=(String)pageDtlMap.get("form_nme");
                 %>
                <html:option value="<%=fld_nme%>" ><%=fld_ttl%></html:option> 
             <%}%>
             </html:select>
             </td> </tr>
            <%}%>
  </table>
  
  </td></tr>
  <tr><td colspan="2" align="center"><html:submit property="value(submit)" value="Fetch" styleClass="submit" />
  
   <%
    pageList= ((ArrayList)pageDtl.get("BUTTON") == null)?new ArrayList():(ArrayList)pageDtl.get("BUTTON");  
            if(pageList!=null && pageList.size() >0){
            for(int j=0;j<pageList.size();j++){
            pageDtlMap=(HashMap)pageList.get(j);
            lov_qry=(String)pageDtlMap.get("lov_qry");
            if(lov_qry.equals(""))
            fld_nme="value("+(String)pageDtlMap.get("fld_nme")+")";
            else
            fld_nme=(String)pageDtlMap.get("fld_nme");
            fld_ttl=(String)pageDtlMap.get("fld_ttl");
            fld_typ=(String)pageDtlMap.get("fld_typ");
            dflt_val=(String)pageDtlMap.get("dflt_val");
            val_cond=(String)pageDtlMap.get("val_cond"); 
                 if(fld_typ.equals("S")){
            %>
    <html:submit property="<%=fld_nme%>" value="<%=fld_ttl%>" onclick="<%=val_cond%>" styleClass="submit"/>&nbsp;
    <%}else if(fld_typ.equals("B")){%>
    <html:button property="<%=fld_nme%>" value="<%=fld_ttl%>" onclick="<%=val_cond%>" styleClass="submit"/>&nbsp;
    <%}}}
    %>
  </td>
  
  </tr>
  </table>
 </html:form>
  </td></tr>
  <tr><td valign="top" class="tdLayout">
 <html:form action="marketing/offerPrice.do?method=delete" method="post" >
  <logic:present property="view" name="offerPriceForm" >
  <%
  ArrayList itemHdr=new ArrayList();
  ArrayList pktList = (ArrayList)session.getAttribute("OFFERPKTLIST");
  String actPrp =util.nvl(request.getParameter("prp"));
String ordD = util.nvl(request.getParameter("order"));

String linkAsc="offerPrice.do?method=sort&prp=byrNm&order=asc";
String linkDsc = "offerPrice.do?method=sort&prp=byrNm&order=desc";

 if(pktList!=null && pktList.size()>0){
  int stoneNo = pktList.size();
  String nmeIdn = util.nvl((String)request.getAttribute("nmeIdn"),"0");
  String pepTyp = util.nvl((String)request.getAttribute("pepTyp"));
                ArrayList prps = (ArrayList)session.getAttribute("prpList");
             %>  
             <table><tr><td>
                          <%
            pageList= ((ArrayList)pageDtl.get("BUTTONF") == null)?new ArrayList():(ArrayList)pageDtl.get("BUTTONF");
            if(pageList!=null && pageList.size() >0){
            for(int j=0;j<pageList.size();j++){
            pageDtlMap=(HashMap)pageList.get(j);
            lov_qry=(String)pageDtlMap.get("lov_qry");
            if(lov_qry.equals(""))
            fld_nme="value("+(String)pageDtlMap.get("fld_nme")+")";
            else
            fld_nme=(String)pageDtlMap.get("fld_nme");
            fld_ttl=(String)pageDtlMap.get("fld_ttl");
            fld_typ=(String)pageDtlMap.get("fld_typ");
            dflt_val=(String)pageDtlMap.get("dflt_val");
            val_cond=(String)pageDtlMap.get("val_cond"); 
            if(fld_typ.equals("S")){%>
            <html:submit property="<%=fld_nme%>" value="<%=fld_ttl%>" onclick="<%=val_cond%>" styleClass="submit"/>&nbsp;
            <%}else if(fld_typ.equals("B")){%>
            <html:button property="<%=fld_nme%>" value="<%=fld_ttl%>" onclick="<%=val_cond%>" styleClass="submit"/>&nbsp;
            <%}}}else if(!cnt.equals("kj")){%>
              <html:submit property="value(submit)" value="Delete" onclick="return confirmChangesMsg('Are You Sure You Want To Delete')"  styleClass="submit" />
              &nbsp;&nbsp;
              <%}%>
              <%if(!pepTyp.equals("PP") && !pepTyp.equals("KS") && !pepTyp.equals("KO")){%>
              <html:submit property="value(allocate)" value="Save Changes(Allocate)" onclick="return confirmChangesMsg('Are You Sure You Want To Do Allocation')"  styleClass="submit" />
              <%}%>
              <%if(pepTyp.equals("KS")){%>
              <html:submit property="value(allocateKS)" value="Allocate kapu Select" onclick="return confirmChangesMsg('Are You Sure You Want To Do Allocation')"  styleClass="submit" />
              <%}%>
             <%if(pepTyp.equals("KO")){%>
             <%
            pageList= ((ArrayList)pageDtl.get("KO") == null)?new ArrayList():(ArrayList)pageDtl.get("KO");
            if(pageList!=null && pageList.size() >0){
            for(int j=0;j<pageList.size();j++){
            pageDtlMap=(HashMap)pageList.get(j);
            lov_qry=(String)pageDtlMap.get("lov_qry");
            if(lov_qry.equals(""))
            fld_nme="value("+(String)pageDtlMap.get("fld_nme")+")";
            else
            fld_nme=(String)pageDtlMap.get("fld_nme");
            fld_ttl=(String)pageDtlMap.get("fld_ttl");
            fld_typ=(String)pageDtlMap.get("fld_typ");
            dflt_val=(String)pageDtlMap.get("dflt_val");
            val_cond=(String)pageDtlMap.get("val_cond"); 
            if(fld_typ.equals("S")){%>
            <html:submit property="<%=fld_nme%>" value="<%=fld_ttl%>" onclick="<%=val_cond%>" styleClass="submit"/>&nbsp;
            <%}else if(fld_typ.equals("B")){%>
            <html:button property="<%=fld_nme%>" value="<%=fld_ttl%>" onclick="<%=val_cond%>" styleClass="submit"/>&nbsp;
            <%}}}%>
              <!--<html:submit property="value(allocateKO)" value="Allocate PBB" onclick="return confirmChangesMsg('Are You Sure You Want To Do Allocation')"  styleClass="submit" />-->
              <%}%>
            &nbsp;&nbsp;  Total No Of Stones <%=stoneNo%>
            <%String url="";
              pageList=(ArrayList)pageDtl.get("EXCEL");
             if(pageList!=null && pageList.size() >0){
             for(int j=0;j<pageList.size();j++){
                 pageDtlMap=(HashMap)pageList.get(j);
                
                 fld_ttl=(String)pageDtlMap.get("fld_ttl");
                 dflt_val=(String)pageDtlMap.get("dflt_val");
                 url= info.getReqUrl()+""+dflt_val;
            %> &nbsp;&nbsp;<%=fld_ttl%>
  <img src="../images/ico_file_excel.png" border="0" onclick="goTo('<%=url%>','','')"/>
                <%}}%>
  &nbsp;&nbsp;Create Excel
  <img src="../images/ico_file_excel.png" border="0" onclick="goTo('<%=info.getReqUrl()%>/marketing/offerPrice.do?method=excel','','')"/>
    &nbsp;&nbsp;Mail Excel
  <img src="../images/ico_file_excel.png" border="0" onclick="newWindow('<%=info.getReqUrl()%>/marketing/offerPrice.do?method=mailexcel')"/>
 
              </td></tr>
            
            
            
            <tr><td>
             <input type="hidden" name="ttl_cnt" id="ttl_cnt" value="<%=stoneNo%>" />
            <table class="dataTable" >
            
                <tr>
                <th><input type="checkbox" name="chAll" id="chAll" onclick="checkedALL()" value="yes" /></th>
                <th>None</th>
                <th>Select  </th>
               <%String sortImg="../images/sort_off.png";
               if(ordD.equals("desc") && actPrp.equals("rnk") ){
                sortImg="../images/sort_up.png";
                }
                if(ordD.equals("asc") && actPrp.equals("rnk")){
                sortImg="../images/sort_down.png";
                  }
                %>
                   <th>Rank &nbsp;
                       <img src="<%=sortImg%>" id="RNKNME"  hspace="2" border="0" usemap="#RNKNME" />
                        <map name="RNKNME" id="RNKNME">
                        <area shape="rect" coords="0,0,6,5" href="<%=linkDsc.replace("byrNm", "rnk")%>" />
                         <area shape="rect" coords="0,8,5,11" href="<%=linkAsc.replace("byrNm", "rnk")%>" />
                   </th>
                <%
                itemHdr.add("rnk");
                sortImg="../images/sort_off.png";
               if(ordD.equals("desc") && actPrp.equals("byrNm") ){
                sortImg="../images/sort_up.png";
                }
                if(ordD.equals("asc") && actPrp.equals("byrNm")){
                sortImg="../images/sort_down.png";
                  }
                  itemHdr.add("byrNm");
                %>
                   <th>Buyer Name &nbsp;
                       <img src="<%=sortImg%>" id="BYRNME"  hspace="2" border="0" usemap="#BYRNME" />
                        <map name="BYRNME" id="BYRNME">
                        <area shape="rect" coords="0,0,6,5" href="<%=linkDsc%>" />
                         <area shape="rect" coords="0,8,5,11" href="<%=linkAsc%>" /></map>
                   </th>
                   <th>Term</th>
                <%
                sortImg="../images/sort_off.png";
               if(ordD.equals("desc") && actPrp.equals("emp") ){
                sortImg="../images/sort_up.png";
                }
                if(ordD.equals("asc") && actPrp.equals("emp")){
                sortImg="../images/sort_down.png";
                  }
                  itemHdr.add("term");itemHdr.add("emp");
                  %>
                   <th>Employee&nbsp;
                       <img src="<%=sortImg%>" id="EMPNME"  hspace="2" border="0" usemap="#EMPNME" />
                        <map name="EMPNME" id="EMPNME">
                        <area shape="rect" coords="0,0,6,5" href="<%=linkDsc.replace("byrNm", "emp")%>" />
                         <area shape="rect" coords="0,8,5,11" href="<%=linkAsc.replace("byrNm", "emp")%>" />
                   </th>
                    <%
                sortImg="../images/sort_off.png";
               if(ordD.equals("desc") && actPrp.equals("vnm") ){
                sortImg="../images/sort_up.png";
                }
                if(ordD.equals("asc") && actPrp.equals("vnm")){
                sortImg="../images/sort_down.png";
                  }
                  itemHdr.add("vnm");
                %>
                   <th>Packet No.&nbsp;<img src="<%=sortImg%>" id="VNM1"  hspace="2" border="0" usemap="#VNM" />
                        <map name="VNM" id="VNM">
                        <area shape="rect" coords="0,0,6,5" href="<%=linkDsc.replace("byrNm", "vnm")%>" />
                         <area shape="rect" coords="0,8,5,11" href="<%=linkAsc.replace("byrNm", "vnm")%>" />
                         </map>
                 </th>
                 <%if(cnt.equals("kj")){%>
                     <%for(int j=0; j < prps.size(); j++) {
                     String lprp=(String)prps.get(j);
                     if(prpDspBlocked.contains(lprp)){
                     }else{%>
                     <%
                    sortImg="../images/sort_off.png";
                   if(ordD.equals("desc") && actPrp.equals(lprp) ){
                    sortImg="../images/sort_up.png";
                    }
                    if(ordD.equals("asc") && actPrp.equals(lprp)){
                    sortImg="../images/sort_down.png";
                      }
                        %>
                        <th><%=lprp%>
                        &nbsp;<img src="<%=sortImg%>" id="<%=lprp%>1"  hspace="2" border="0" usemap="#<%=lprp%>" />
                        <map name="<%=lprp%>" id="<%=lprp%>">
                        <area shape="rect" coords="0,0,6,5" href="<%=linkDsc.replace("byrNm", lprp)%>" />
                        <area shape="rect" coords="0,8,5,11" href="<%=linkAsc.replace("byrNm", lprp)%>" />
                        </map>
                        
                        </th>
                    <%
                    itemHdr.add(lprp);
                    if(cnt.equals("kj") && lprp.equals("FL")){%>
                    <th>Offer Price</th>
                    <% 
                    itemHdr.add("offer_rte");
                    pageList=(ArrayList)pageDtl.get("LMT");
                     if(pageList!=null && pageList.size() >0){
                     itemHdr.add("ofr_lmt");%>
                     <th>Offer Limit</th>
                    <%}
                    itemHdr.add("cmp");
                    %>
                    <th>Mem Price</th>
                    <%if(!cnt.equals("ag")){
                    itemHdr.add("net_rte");%>
                    <th>Net Price</th>
                    <%}
                     itemHdr.add("quot");
                     if(plDis){
                    itemHdr.add("plper");
                    }
                    if(byrplDis){
                    itemHdr.add("plperByr");
                    }
                    itemHdr.add("offer_dis");itemHdr.add("mr_dis");%>
                     <th>Byr Rte</th>
                     <%if(plDis){%>
                    <th>Pl %</th>
                    <%}
                    if(byrplDis){%>
                    <th>Byr Pl %</th>
                    <%}%>
                     <th>Offer Discout</th>
                    <th>Mem Dis</th>
                    
                    <%if(!cnt.equals("ag")){
                    itemHdr.add("net_dis");
                    itemHdr.add("dis");%>
                    <th>Net dis</th>
                    <th>Dis</th>
                    <%}%>
                    <%}%>
                    <%}}%>
                 <%}itemHdr.add("typ");itemHdr.add("frmDte");itemHdr.add("toDte");%>
                    <th>Type</th>
                    <th>Offer Date</th>
                    <th>valid till Date</th>
                    
                    <%if(!cnt.equals("kj")){
                    itemHdr.add("dis");
                    itemHdr.add("offer_dis");itemHdr.add("mr_dis");%>
                    <th>Dis</th>
                     <th>Offer Discout</th>
                    <th>Mem Dis</th>
                    <%if(!cnt.equals("ag")){
                    itemHdr.add("net_rte");itemHdr.add("net_dis");
                    %>
                      <th>Net Rte</th>
                    <th>Net dis</th>
                    <%}%>
                    <%}
                    %>
                     
                     <%if(!cnt.equals("kj")){
                     itemHdr.add("quot");
                     itemHdr.add("offer_rte");%>
                     <th>Quot</th>
                    <th>Offer Price</th>
                    <% pageList=(ArrayList)pageDtl.get("LMT");
                     if(pageList!=null && pageList.size() >0){
                     itemHdr.add("ofr_lmt");%>
                     <th>Offer Limit</th>
                    <%}
                    itemHdr.add("cmp");
                    %>
                    <th>Mem Price</th>
                    <%if(!cnt.equals("ag")){
                    itemHdr.add("net_rte");%>
                    <th>Net Price</th>
                    <%}%>
                    <%}%>
                    <%if(!cnt.equals("kj")){%>
                     <%for(int j=0; j < prps.size(); j++) {
                     String lprp=(String)prps.get(j);
                     if(prpDspBlocked.contains(lprp)){
                     }else{%>
                        <th><%=lprp%></th>
                    <%itemHdr.add(lprp);}}%>
                    <%}%>
                </tr>
                <%
                for(int m=0;m<pktList.size();m++){
                HashMap pkt = (HashMap)pktList.get(m);
                String relIdn = (String)pkt.get("relIdn");
                String stkIdn = (String)pkt.get("stk_idn");
                String checkFld = "cb_off_"+stkIdn;
                String checkId = "ch_"+m;
                String checkVal = stkIdn+"_"+relIdn;
                String bidIdn = (String)pkt.get("bidIdn");
                 String onChange ="UpdateGT('"+stkIdn+"','"+bidIdn+"',this)";
                %>
                <tr>
                <td><input type="checkbox" name="<%=checkFld%>" id="<%=checkId%>" value="<%=checkVal%>" /> </td>
                <td><input type="radio" name="<%=stkIdn%>"  onclick="<%=onChange%>" value="NONE" /> </td>
                <td><input type="radio" name="<%=stkIdn%>"  onclick="<%=onChange%>" value="ALLOW" /> </td>
                <td><%=util.nvl((String)pkt.get("rnk"))%></td>
                <td><%=pkt.get("byrNm")%></td> <td><%=pkt.get("term")%></td> <td><%=pkt.get("emp")%></td>
                <td><%=pkt.get("vnm")%></td>
                <%if(cnt.equals("kj")){%>
                <%for(int j=0; j < prps.size(); j++) {
                  String lprp = (String)prps.get(j);
                if(prpDspBlocked.contains(lprp)){
                  }else{%>
                        <td><%=pkt.get(lprp)%></td>
                    <%if(cnt.equals("kj") && lprp.equals("FL")){%>
                                        <td><%=pkt.get("offer_rte")%></td>
                    <% pageList=(ArrayList)pageDtl.get("LMT");
                     if(pageList!=null && pageList.size() >0){%>
                     <td><%=pkt.get("ofr_lmt")%></td>
                    <%}%>
                   <td><%=pkt.get("cmp")%></td>
                    <%if(!cnt.equals("ag")){%>
                   <td><%=pkt.get("net_rte")%></td>
                   <%}%>
                   <td><%=pkt.get("quot")%></td>
                    <%if(plDis){%>
                   <td><label ><%=pkt.get("plper")%></label></td>
                   <%}
                   if(byrplDis){%>
                   <td><label ><%=pkt.get("plperByr")%></label></td>
                   <%}%>
                    <td><label style="color:Blue"><%=pkt.get("offer_dis")%></label></td>
                     <td><label style="color:red"><%=pkt.get("mr_dis")%></label></td>
                     <%if(!cnt.equals("ag")){%>
                      <td><label ><%=pkt.get("net_dis")%></label></td>
                       <td><label style="color:red"><%=pkt.get("dis")%></label></td>
                      <%}%>
                      <%}%>
                    <%}}}%>
                <td><%=pkt.get("typ")%></td>
                   <td><%=pkt.get("frmDte")%></td>
                     <td><%=pkt.get("toDte")%></td>
                       
                       <%if(!cnt.equals("kj")){%>
                       <td> <label style="color:Maroon"> <%=pkt.get("dis")%></label></td>
                    <td><label style="color:Blue"><%=pkt.get("offer_dis")%></label></td>
                     <td><label style="color:red"><%=pkt.get("mr_dis")%></label></td>
                     <%if(!cnt.equals("ag")){%>
                      <td><label ><%=pkt.get("net_rte")%></label></td>
                      <td><label ><%=pkt.get("net_dis")%></label></td>
                      <%}%>
                      <%}%>
                   
                   <%if(!cnt.equals("kj")){%>
                   <td><%=pkt.get("quot")%></td>
                   <td><%=pkt.get("offer_rte")%></td>
                    <% pageList=(ArrayList)pageDtl.get("LMT");
                     if(pageList!=null && pageList.size() >0){%>
                     <td><%=pkt.get("ofr_lmt")%></td>
                    <%}%>
                   <td><%=pkt.get("cmp")%></td>
                    <%if(!cnt.equals("ag")){%>
                   <td><%=pkt.get("net_rte")%></td>
                   <%}%>
                   <%}%>
                   <%if(!cnt.equals("kj")){%>
                  <%for(int j=0; j < prps.size(); j++) {
                  String lprp = (String)prps.get(j);
                if(prpDspBlocked.contains(lprp)){
                  }else{%>
                        <td><%=pkt.get(lprp)%></td>
                    <%}}}%>
                       </tr>
                <%}
                session.setAttribute("itemHdr", itemHdr);%>
  
  </table> </td></tr></table>
  <%}else{%>
  Sorry no result found.
  <%}%>
  </logic:present></html:form>
  </td></tr>
  <tr>
    <td><jsp:include page="/footer.jsp" /> </td>
    </tr>
  </table>
   <%@include file="../calendar.jsp"%>
  </body></html>