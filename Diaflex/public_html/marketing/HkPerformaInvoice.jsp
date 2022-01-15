<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
 
<%@ page errorPage="../error_page.jsp" %>
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import="java.util.ArrayList,java.util.ArrayList , java.util.HashMap, java.sql.*, java.util.Enumeration, java.util.logging.Level"%>
<jsp:useBean id="util" class="ft.com.JspUtil" scope="page" />
<jsp:useBean id="info" class="ft.com.InfoMgr" scope="session" />
<%@taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>
<%@taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>Performa Invoice</title>
  <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/style.css?v=<%=info.getJsVersion()%>"/>
  <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/bse.js?v=<%=info.getJsVersion()%> " > </script>
      <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/general.css?v=<%=info.getJsVersion()%>"/>
      <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/bse.css?v=<%=info.getJsVersion()%>"/>
   </head>     
        <%String logId=String.valueOf(info.getLogId());
        String onfocus="cook('"+logId+"')";
        %>
 <body onfocus="<%=onfocus%>" onload="window.print()">
    <%
    HashMap mprp = info.getMprp();
     HashMap dbinfo = info.getDmbsInfoLst();
     String cnt =(String)dbinfo.get("CNT");
     ArrayList perFormInvLst = (request.getAttribute("itemHdr") == null)?new ArrayList():(ArrayList)request.getAttribute("itemHdr");
        String mixDis="Y";
        if(perFormInvLst.size()==1){
           if(perFormInvLst.get(0).equals("Description"))
           mixDis="N";
        }
        ArrayList chargeLst = (ArrayList)request.getAttribute("chargeLst");
        ArrayList pktList = (ArrayList)request.getAttribute("pktList");
        String typ = util.nvl((String)request.getAttribute("typ"));
    String msg = util.nvl((String)request.getAttribute("msg"));
    String fmt =util.nvl((String)request.getAttribute("fmt"));
    StringBuffer mailmsg=new StringBuffer();
    HashMap performaDtl=(HashMap)request.getAttribute("performaDtl");;
    String consign = util.nvl((String)request.getAttribute("consign"));
    String invrefno = util.nvl((String)request.getAttribute("invrefno"));
//    if(typ.equals("BOX"))
//        perFormInvLst=(ArrayList)session.getAttribute("perInvBoxViewLst");
    if(pktList!=null && pktList.size() > 0){
    int perFormInvLstsz=perFormInvLst.size();
    String byrIdn=util.nvl((String)performaDtl.get("byrIdn"));
    String location=util.nvl((String)performaDtl.get("location"));
    String urlmail="performa.do?method=mail&byrIdn="+byrIdn+"&location="+location;
    String onclick="newWindow('"+urlmail+"');";
    mailmsg.append("<table class=\"perInvoiceloc\" width=\"810\" align=\"center\" cellspacing=\"0\" cellpadding=\"0\"><tr><td colspan=\"3\" style=\"border-left: 0px solid white;\"><table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" style=\"border:1px solid white;\"><tr><td style=\"border:1px solid white;\">");
    HashMap allPageDtl= (info.getPageDetails() == null)?new HashMap():(HashMap)info.getPageDetails();
     HashMap pageDtl=(HashMap)allPageDtl.get("PERFORMA_INV");
     ArrayList pageList=new ArrayList();
     HashMap pageDtlMap=new HashMap();
     String fld_ttl="",dflt_val="",lov_qry="",flg1="";
     String val_cond="",fld_nme="",fld_typ="";%>
<!--<html:button property="value(mail)" value="Send Mail" styleId="mail" onclick="<%=onclick%>" styleClass="submit" />  -->         
 <%  
 pageList= ((ArrayList)pageDtl.get("MAIL") == null)?new ArrayList():(ArrayList)pageDtl.get("MAIL"); 
      if(pageList!=null && pageList.size() >0){
      for(int j=0;j<pageList.size();j++){
      pageDtlMap=(HashMap)pageList.get(j);
      fld_nme="value("+(String)pageDtlMap.get("fld_nme")+")";
      fld_ttl=(String)pageDtlMap.get("fld_ttl");
      val_cond=(String)pageDtlMap.get("val_cond");
      val_cond=val_cond.replace("URL",urlmail);
      fld_typ=(String)pageDtlMap.get("fld_typ");
     String fld_id = (String)pageDtlMap.get("dflt_val");
     %>
<span class="no-print"><html:button property="<%=fld_nme%>" value="<%=fld_ttl%>" styleId="<%=fld_id%>" onclick="<%=val_cond%>" styleClass="submit" />   </span>        
<%}}%>
  <table class="perInvoiceloc" width="820" align="center" cellspacing="0" cellpadding="0">
  <tr><td colspan="3" style="border-left: 0px solid white;">
  <table border="0" cellpadding="0" cellspacing="0" style="border:1px solid white;">
    <tr>
    <td style="border:1px solid white;">
    <logic:equal property="value(headerimg)" name="saleDeliveryForm"   value="Y">
    <img style="width:100%;" src="<%=info.getReqUrl()%>/images/clientsLogo/<bean:write property="value(logoloc)" name="saleDeliveryForm" />" />
    <%mailmsg.append("<img style=\"width:100%;\" src=\""+info.getReqUrl()+"/images/clientsLogo/"+util.nvl((String)performaDtl.get("comlogo"))+"\" />");%>
    </logic:equal>
    <logic:equal property="value(headertext)" name="saleDeliveryForm" value="Y">
    <table border="0" cellpadding="0" cellspacing="0" style="border:1px solid white;" width="810">
  <tr>
  <td style="border:1px solid white;" width="150"><p align="center"><img src="../images/clientsLogo/<bean:write property="value(comlogo)" name="saleDeliveryForm" />" width="120px" /></p></td>
  <td style="border:1px solid white;" width="660">
  <p align="center" style="font-size: 17px;"><B><bean:write property="value(COMPANY)" name="saleDeliveryForm" /></b><br>
  <span style="font-size: 12px;"><bean:write property="value(combldg)" name="saleDeliveryForm" /></span><br>
  <span style="font-size: 12px;"><bean:write property="value(comadd1)" name="saleDeliveryForm" /></span><br>
  <logic:notEqual property="value(comadd2)" name="saleDeliveryForm" value="" >
  <span style="font-size: 12px;"><bean:write property="value(comadd2)" name="saleDeliveryForm" /></span><br>
  </logic:notEqual>
  <logic:notEqual property="value(comcity)" name="saleDeliveryForm" value="" >
  <span style="font-size: 12px;"><bean:write property="value(comcity)" name="saleDeliveryForm" /></span><br>
   </logic:notEqual>
  <span style="font-size: 12px;"> Tel No: <bean:write property="value(comTel)" name="saleDeliveryForm" /> 
  <%mailmsg.append("<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" style=\"border:1px solid white;\" width=\"810\"><tr><td style=\"border:1px solid white;\" width=\"150px\"><p align=\"center\">");
  mailmsg.append("<img width=\"120\"  src=\""+info.getReqUrl()+"/images/clientsLogo/"+util.nvl((String)performaDtl.get("comlogo"))+"\" /></p></td>");
  mailmsg.append("<td style=\"border:1px solid white;\" width=\"660\">");
  mailmsg.append("<p align=\"center\" style=\"font-size: 17px;\"><B>"+util.nvl((String)performaDtl.get("COMPANY"))+"</b><br>");
  mailmsg.append("<span style=\"font-size: 12px;\">"+util.nvl((String)performaDtl.get("combldg"))+"</span><br>");
  mailmsg.append("<span style=\"font-size: 12px;\">"+util.nvl((String)performaDtl.get("comadd1"))+"</span><br>");
  mailmsg.append("<span style=\"font-size: 12px;\">"+util.nvl((String)performaDtl.get("comadd2"))+"</span><br>");
  mailmsg.append("<span style=\"font-size: 12px;\">"+util.nvl((String)performaDtl.get("comcity"))+"</span><br>");
  mailmsg.append("<span style=\"font-size: 12px;\"> Tel No:"+util.nvl((String)performaDtl.get("comTel")));
  %>
   <logic:notEqual property="value(comFax)" name="saleDeliveryForm" value="" >
   , Fax No : <bean:write property="value(comFax)" name="saleDeliveryForm" />
   <%mailmsg.append("&nbsp;&nbsp;, Fax No :"+util.nvl((String)performaDtl.get("comFax")));%>
   </logic:notEqual>
   Email Id:<bean:write property="value(comEmail)" name="saleDeliveryForm" />
   </span></p></td>
  </tr>
  </table>
  <%mailmsg.append("Email Id:"+util.nvl((String)performaDtl.get("comEmail")));
   mailmsg.append("</span></p></td></tr></table>");
  %>
    </logic:equal>
    </td>
    </tr> 
  </table>
  </td></tr>
  <%mailmsg.append("</td></tr></table></td></tr>");
  mailmsg.append("<tr><td colspan=\"3\" align=\"center\" style=\"border-left: 0px solid white;\"><b style=\"font-size: 15px; \">INVOICE</b></td></tr><tr><td class=\"paddinleftrightloc\" width=\"25%\" style=\"border-left: 0px solid white;\"><b>Buyer:</b>");
  mailmsg.append("<br><b>"+util.nvl((String)performaDtl.get("byrnme"))+"</b>");
  mailmsg.append("<br>"+util.nvl((String)performaDtl.get("unit_no"))+",");
  mailmsg.append(util.nvl((String)performaDtl.get("bldg")));
  mailmsg.append("<br>"+util.nvl((String)performaDtl.get("bldg")));
  mailmsg.append("<br>"+util.nvl((String)performaDtl.get("landMk")));
  mailmsg.append("<br>"+util.nvl((String)performaDtl.get("addr")));
  mailmsg.append("<br>Tel No-"+util.nvl((String)performaDtl.get("BYTEL_NO")));
  mailmsg.append("<span style=\"padding-left:20px;\">Fax-"+util.nvl((String)performaDtl.get("BYFAX"))+"</span></td>");
  %>
  <tr><td colspan="3" align="center" style="border-left: 0px solid white;"><b style="font-size: 15px; ">INVOICE</b></td></tr>
   <tr>
   <td class="paddinleftrightloc" width="25%" style="border-left: 0px solid white;">
   <b>Buyer:</b>
     <br><b><bean:write property="value(byrnme)" name="saleDeliveryForm" /></b>
     <br><bean:write property="value(unit_no)" name="saleDeliveryForm" />,
     <bean:write property="value(bldg)" name="saleDeliveryForm" />
     <br> <bean:write property="value(landMk)" name="saleDeliveryForm" />
     <br> <bean:write property="value(addr)" name="saleDeliveryForm"   />
     <br>Tel No-<bean:write property="value(BYTEL_NO)" name="saleDeliveryForm"   />
     <span style="padding-left:20px; white-space: nowrap">Fax-<bean:write property="value(BYFAX)" name="saleDeliveryForm"   /></span>
     </td>
   <%
   if(consign.equals("Yes")){
   mailmsg.append("<td class=\"paddinleftrightloc\" width=\"50%\" style=\"border-left: 0px solid white;\"><b>Consignee :</b><br>");
   mailmsg.append("<b>"+util.nvl((String)performaDtl.get("bankNme"))+"</b><br>");
   mailmsg.append(util.nvl((String)performaDtl.get("bankBldg"))+"<br>");
   mailmsg.append(util.nvl((String)performaDtl.get("bankLand"))+"<br>");
   mailmsg.append(util.nvl((String)performaDtl.get("bankCity"))+"<br>");
   }else{
      mailmsg.append("<td class=\"paddinleftrightloc\" width=\"50%\" style=\"border-left: 0px solid white;\"><b>Consignee :</b><br>");
      mailmsg.append("<b>"+consign+"</b><br>");
   }
   
   
   %>
   <td class="paddinleftrightloc" width="50%" style="border-left: 0px solid white;">
   <%
   if(consign.equals("Yes")){
   %>
   <b>Consignee :</b><br>
  <b><bean:write property="value(bankNme)" name="saleDeliveryForm" /></b><br>
  <bean:write property="value(bankBldg)" name="saleDeliveryForm" /><br>
  <bean:write property="value(bankLand)" name="saleDeliveryForm" /><br>
  <bean:write property="value(bankCity)" name="saleDeliveryForm" /><br>
 <logic:notEqual property="value(BYIFSC)" name="saleDeliveryForm" value="" >
 Ifsc Code :<bean:write property="value(BYIFSC)" name="saleDeliveryForm" />&nbsp;
 <%mailmsg.append("Ifsc Code :"+util.nvl((String)performaDtl.get("BYIFSC")));%>
 </logic:notEqual>
 <logic:notEqual property="value(BYBANKACCOUNT)" name="saleDeliveryForm" value="" >
 Account No :<bean:write property="value(BYBANKACCOUNT)" name="saleDeliveryForm" />
  <%mailmsg.append("Account No :"+util.nvl((String)performaDtl.get("BYBANKACCOUNT")));%>
  </logic:notEqual>
   <%}else{%>
    <b>Consignee :</b><br>
    <b><%=consign%></b>
   <%}
   
   
   mailmsg.append("</td>");
   mailmsg.append("<td class=\"paddinleftrightloc\" width=\"25%\" style=\"border-left: 0px solid white;\">");
   if(!cnt.equals("kj")){
   if(!invrefno.equals("")){
    mailmsg.append("Invoice No. :-"+invrefno+"<br>");
     mailmsg.append("Ref No. :-"+util.nvl((String)performaDtl.get("invno"))+"<br>");
   }else{
   mailmsg.append("Invoice No. :-"+util.nvl((String)performaDtl.get("invno"))+"<br>");
   }
   }
   mailmsg.append("Date :-"+util.nvl((String)performaDtl.get("datePrtVal"))+"<br>");
   if(cnt.equals("ag"))
   mailmsg.append("Due Date :- ____________<br>");
   mailmsg.append("Terms :-"+util.nvl((String)performaDtl.get("terms"))+"<br>");
   mailmsg.append("Country of Origin :-  INDIA");
   mailmsg.append("</td></tr>");
   %>
   </td>
   <td class="paddinleftrightloc" width="25%" style="border-left: 0px solid white;">
   <%if(!cnt.equals("kj")){
    if(!invrefno.equals("")){%>
    Invoice No. :-<%=invrefno%><br>
     Ref No. :-<bean:write property="value(invno)" name="saleDeliveryForm" /><br>
   <%}else{%>
   Invoice No. :-<bean:write property="value(invno)" name="saleDeliveryForm" /><br>
   <%}
   %>
  
   <%}%>
   Date :- <%=util.nvl((String)performaDtl.get("datePrtVal"))%><br>
   <% if(cnt.equals("ag")){%>
   Due Date :- ____________<br>
   <%}%>
   Terms :- <bean:write property="value(terms)" name="saleDeliveryForm" /><br>
   Country of Origin :-  INDIA
   </td>
   </tr>
   <%mailmsg.append("<tr><td colspan=\"3\" style=\"height:20px; border-left: 0px solid white;\" align=\"center\">CUT & POLISHED DIAMONDS</td></tr>");
   mailmsg.append("<tr><td colspan=\"3\" style=\"border-left: 0px solid white;\">");
   mailmsg.append("<table width=\"100%\" class=\"invSub\"  cellspacing=\"0\" >");
   mailmsg.append("<tr>");
   mailmsg.append("<td align=\"center\" style=\"border-left: 0px solid white; border-top: 0px solid white;\">Sr No</td>");
   if(!cnt.equals("ag")){
   mailmsg.append("<td colspan=\""+(perFormInvLstsz+1)+"\" align=\"center\" style=\"border-top: 0px solid white;\">Description Of Goods</td>");
   }else{
         for(int j=0; j < perFormInvLst.size(); j++) {
         String lprp=(String)perFormInvLst.get(j);
         String prpDsc = util.nvl((String)mprp.get(lprp+"D"),lprp);
         mailmsg.append("<td align=\"center\" style=\"border-top: 0px solid white;\">"+prpDsc+"</td>");
         }
         mailmsg.append("<td align=\"center\" style=\"border: 0px solid white;\"></td>");
   }
   mailmsg.append("<td align=\"center\" style=\"border-top: 0px solid white;\">Pcs</td>");
   mailmsg.append("<td align=\"center\" style=\"border-top: 0px solid white;\">Carats</td>");
   mailmsg.append("<td align=\"center\" style=\"border-top: 0px solid white;\">Price/Ct</td>");
   mailmsg.append("<td align=\"center\" style=\"border-top: 0px solid white;\">Amount US$</td>");
   mailmsg.append("<td align=\"center\" style=\"border-top: 0px solid white;\">Dis%</td>");
   mailmsg.append("<td align=\"center\" style=\"border-top: 0px solid white;\">Rapa</td></tr>");
   %>
   <tr><td colspan="3" style="height:20px; border-left: 0px solid white;" align="center">CUT & POLISHED DIAMONDS</td></tr>
   <tr><td colspan="3" style="border-left: 0px solid white;">
   <table width="100%" class="invSub"  cellspacing="0" >
   <tr>
   <td align="center" style="border-left: 0px solid white; border-top: 0px solid white;">Sr No</td>
   <%if(!cnt.equals("ag")){%>
   <td colspan="<%=(perFormInvLstsz+1)%>" align="center" style="border-top: 0px solid white;">Description Of Goods</td>
   <%}else{%>
                     <%for(int j=0; j < perFormInvLst.size(); j++) {
                     String lprp=(String)perFormInvLst.get(j);
                     String prpDsc = util.nvl((String)mprp.get(lprp+"D"),lprp);
                     %>
                     <td align="center" style="border-top: 0px solid white;font-size: 13px;"><%=prpDsc%></td>
                    <%}%>
                    <td align="center" style="border: 0px solid white;"></td>
   <%}%>
   <td align="center" style="border-top: 0px solid white;font-size: 13px;">Pcs</td>
   <td align="center" style="border-top: 0px solid white;font-size: 13px;">Carats</td>
   <td align="center" style="border-top: 0px solid white;font-size: 13px;"> Price/Ct</td>
   <td align="center" style="border-top: 0px solid white;font-size: 13px;">Amount US$</td>
   <%if(mixDis.equals("Y")){%>
   <td align="center" style="border-top: 0px solid white;font-size: 13px;">Dis%</td>
   <td align="center" style="border-top: 0px solid white;font-size: 13px;">Rapa</td>
   <%}%>
   </tr>
   <%int sr=1;%>
   <%for(int i=0;i<pktList.size();i++){
  HashMap pktDtl = (HashMap)pktList.get(i);
  %>
  <tr>
  <td align="center" style="border-left: 0px solid white;"><%=sr++%></td>
  <%
  mailmsg.append("<tr>");
  mailmsg.append("<td align=\"center\" style=\"border-left: 0px solid white;\">"+(sr)+"</td>");
  for(int j=0; j < perFormInvLst.size(); j++) {
   String prp = (String)perFormInvLst.get(j);
   prp=util.nvl((String)pktDtl.get(prp));
   if(prp.indexOf("+") > -1){
    prp=prp.replaceAll("\\+"," ");
   }
   
   if(prp.indexOf("-") > -1){
    prp=prp.replaceAll("\\-"," ");
   }
  %>
  <td align="center"><%=prp%></td>
  <%
  mailmsg.append("<td align=\"center\">"+prp+"</td>");
  }
  if(!cnt.equals("ag")){
  mailmsg.append("<td  align=\"center\" class=\"paddin\">"+util.nvl((String)pktDtl.get("cert_no"))+"</td>");
  }else{
  mailmsg.append("<td  style=\"border: 0px solid white;\"></td>");
  }
  mailmsg.append("<td  align=\"center\" class=\"paddin\">"+util.nvl((String)pktDtl.get("qty"))+"</td>");
  mailmsg.append("<td  align=\"center\" class=\"paddin\">"+util.nvl((String)pktDtl.get("cts"))+"</td>");
  mailmsg.append("<td  align=\"center\" class=\"paddin\">"+util.nvl((String)pktDtl.get("quot"))+"</td>");
  mailmsg.append("<td  align=\"center\" class=\"paddin\">"+util.nvl((String)pktDtl.get("vlu"))+"</td>");
  mailmsg.append("<td  align=\"center\" class=\"paddin\"><b>"+util.nvl((String)pktDtl.get("rapdis"))+"</b></td>");
  mailmsg.append("<td  align=\"center\" class=\"paddin\">"+util.nvl((String)pktDtl.get("rap_rte"))+"</td></tr>");
  if(!cnt.equals("ag")){
  %>
  <td  align="center" class="paddin"><%=util.nvl((String)pktDtl.get("cert_no"))%></td>
  <%}else{%>
  <td  style="border: 0px solid white;"></td>
  <%}%>
  <td  align="center" class="paddin"><%=util.nvl((String)pktDtl.get("qty"))%></td>
  <td  align="center" class="paddin"><%=util.nvl((String)pktDtl.get("cts"))%></td>
  <td  align="center" class="paddin"><%=util.nvl((String)pktDtl.get("quot"))%></td>
  <td  align="center" class="paddin"><%=util.nvl((String)pktDtl.get("vlu"))%></td>
  <%if(mixDis.equals("Y")){%>
  <td  align="center" class="paddin"><b><%=util.nvl((String)pktDtl.get("rapdis"))%></b></td>
  <td  align="center" class="paddin"><%=util.nvl((String)pktDtl.get("rap_rte"))%></td>
  <%}%>
  </tr>
 <%}%>
 <%mailmsg.append("<tr><td colspan=\""+(perFormInvLstsz+2)+"\" align=\"right\" style=\"border-bottom: 1px solid white; border-left: 0px solid white;\">Other Charges</td>");
 mailmsg.append("<td colspan=\"3\" align=\"center\"></td><td align=\"right\"><b>");
 mailmsg.append(util.nvl((String)performaDtl.get("echarge"))+"</b></td>");
 mailmsg.append("<td colspan=\"2\"></td></tr>");
 %>
  <tr><td colspan="<%=(perFormInvLstsz+2)%>" align="right" style="border-bottom: 1px solid white; border-left: 0px solid white;">Other Charges</td>
  <td colspan="3" align="center"></td>
  <td align="right"><b><bean:write property="value(echarge)" name="saleDeliveryForm" /></b></td>
  <%if(mixDis.equals("Y")){%>
  <td colspan="2"></td>
  <%}%>
 </tr>
  <logic:notEqual property="value(benefit)" name="saleDeliveryForm" value="" >
   <tr><td colspan="<%=(perFormInvLstsz+2)%>" align="right" style="border-bottom: 1px solid white; border-left: 0px solid white;">Additional Discount</td>
  <td colspan="3" align="center"></td>
  <td align="right"><b><bean:write property="value(benefit)" name="saleDeliveryForm" /></b></td>
  <td colspan="2"></td>
 </tr>
 <%
  mailmsg.append("<tr><td colspan=\""+(perFormInvLstsz+2)+"\" align=\"right\" style=\"border-bottom: 1px solid white; border-left: 0px solid white;\">Additional Discount</td>");
 mailmsg.append("<td colspan=\"3\" align=\"center\"></td><td align=\"right\"><b>");
 mailmsg.append(util.nvl((String)performaDtl.get("benefit"))+"</b></td>");
 mailmsg.append("<td colspan=\"2\"></td></tr>");
 %>
  </logic:notEqual>
  <%if(chargeLst!=null && chargeLst.size()>0){
 for(int k=0; k < chargeLst.size(); k++) {
 HashMap charData=new HashMap();
 charData=(HashMap)chargeLst.get(k);
 mailmsg.append("<tr><td colspan=\""+(perFormInvLstsz+2)+"\" align=\"right\" style=\"border-bottom: 1px solid white; border-left: 0px solid white;\">"+util.nvl((String)charData.get("DSC"))+" Charge </td>");
 mailmsg.append("<td colspan=\"3\" align=\"center\"></td>");
 mailmsg.append("<td align=\"right\"><b>"+util.nvl((String)charData.get("CHARGE"))+"</b></td>");
 mailmsg.append("<td colspan=\"2\"></td></tr>");
 %>
<tr><td colspan="<%=(perFormInvLstsz+2)%>" align="right" style="border-bottom: 1px solid white; border-left: 0px solid white;"><%=util.nvl((String)charData.get("DSC"))%> Charge </td>
 <td colspan="3" align="center"></td>
 <td align="right"><b><%=util.nvl((String)charData.get("CHARGE"))%></b></td>
 <td colspan="2"></td>
 </tr>
<%}}
mailmsg.append("<tr><td colspan=\""+(perFormInvLstsz+3)+"\" align=\"right\" style=\"border-left: 0px solid white;\"><b>Total</b></td>");
mailmsg.append("<td align=\"right\"><b>"+util.nvl((String)performaDtl.get("ttlCts"))+"</b></td>");
mailmsg.append("<td align=\"right\"><b>"+util.nvl((String)performaDtl.get("termsSign"))+"</b></td>");
mailmsg.append("<td align=\"right\"><b>"+util.nvl((String)performaDtl.get("grandttlVlu"))+"</b></td>");
mailmsg.append("<td></td><td></td></tr></table></td> </tr>");
mailmsg.append("<tr><td colspan=\"3\" width=\"100%\" class=\"paddinleftrightloc\" style=\"border-left: 0px solid white;\"><p>");
mailmsg.append("  <div style=\"width:100%;\" align=\"justify\" class=\"perInvoicelocfont\">The diamond herein invoiced has been purchased from legitimate sources not involved in funding conflict in compliance with United Nation Resolutions.The seller hereby guarantees that these diamonds are conflict free, based on personal knowledge and / or written guaranties provided by supplier of these diamonds.</div></p>");
mailmsg.append("<p style=\"margin-top:0px;\"><b>Payment Instruction</b></p>");
%>
  <tr>
  <td colspan="<%=(perFormInvLstsz+3)%>" align="right" style="border-left: 0px solid white;"><b>Total US$</b></td>
  <td align="right"><b><bean:write property="value(ttlCts)" name="saleDeliveryForm" /></b></td>
  <td align="right"><b><bean:write property="value(termsSign)" name="saleDeliveryForm" /></b></td>
  <td align="right"><b><bean:write property="value(grandttlVlu)" name="saleDeliveryForm" /></b></td>
  <%if(mixDis.equals("Y")){%>
  <td></td><td></td>
  <%}%>
  </tr>
   </table>
   </td> </tr>
<tr><td colspan="3" width="100%" class="paddinleftrightloc" style="border-left: 0px solid white;">
  <%if(!location.equals("HKE")){%>
   <p>
   <div style="width:100%;" align="justify" class="perInvoicelocfont">The diamond herein invoiced has been purchased from legitimate sources not involved in funding conflict in compliance with United Nation Resolutions.
The seller hereby guarantees that these diamonds are conflict free, based on personal knowledge and / or written guaranties provided by supplier of these diamonds.
</div></p>
<p style="margin-top:0px;"><b>Payment Instruction</b></p>
            <%pageList=(ArrayList)pageDtl.get("BANK_LOC");
            if(pageList!=null && pageList.size() >0){
            mailmsg.append("<p style=\"margin:0px;\"><b>Company Name : "+util.nvl((String)performaDtl.get("COMPANY"))+"</b></p>");
mailmsg.append("<p style=\"margin:0px;\"><b>Account Number :"+util.nvl((String)performaDtl.get("BANK ACCOUNT NO"))+"</b></p>");
mailmsg.append("<p style=\"margin:0px;\"><b>Bank Name : "+util.nvl((String)performaDtl.get("banklocnme"))+"</b></p>");
mailmsg.append("<p style=\"margin:0px;\"><b>Bank Address : "+util.nvl((String)performaDtl.get("banklocaddr"))+"</b></p>");
mailmsg.append("<p style=\"margin:0px;\"><b>Swift Code : "+util.nvl((String)performaDtl.get("Swift Code"))+"</b></p>");
}else{%>
<%mailmsg.append("<p style=\"margin:0px;\"><b>"+util.nvl((String)performaDtl.get("AC"))+"</b></p>");
mailmsg.append("<p style=\"margin:0px;\"><b>"+util.nvl((String)performaDtl.get("BANKNAME"))+"</b></p>");
mailmsg.append("<p style=\"margin:0px;\"><b>"+util.nvl((String)performaDtl.get("USAC"))+"</b></p>");
mailmsg.append("<p style=\"margin:0px;\"><b>"+util.nvl((String)performaDtl.get("SWIFT"))+"</b></p>");
}
mailmsg.append("<p align=\"justify\" class=\"perInvoicelocfont\">");
            pageList=(ArrayList)pageDtl.get("BANK_LOC");
            if(pageList!=null && pageList.size() >0){%>
            <p style="margin:0px;"><b>Company Name : <bean:write property="value(COMPANY)" name="saleDeliveryForm" /></b></p>
            <p style="margin:0px;"><b>Account Number : <bean:write property="value(BANK ACCOUNT NO)" name="saleDeliveryForm" /></b></p>
            <p style="margin:0px;"><b>Bank Name : <bean:write property="value(banklocnme)" name="saleDeliveryForm" /></b></p>
            <p style="margin:0px;"><b>Bank Address : <bean:write property="value(banklocaddr)" name="saleDeliveryForm" /></b></p>
            <p style="margin:0px;"><b>Swift Code : <bean:write property="value(Swift Code)" name="saleDeliveryForm" /></b></p>
            <%}else{
%>
<p style="margin:0px;"><b><bean:write property="value(AC)" name="saleDeliveryForm" /></b></p>
<p style="margin:0px;"><b><bean:write property="value(BANKNAME)" name="saleDeliveryForm" /></b></p>
<p style="margin:0px;"><b><bean:write property="value(USAC)" name="saleDeliveryForm" /></b></p>
<p style="margin:0px;"><b><bean:write property="value(SWIFT)" name="saleDeliveryForm" /></b></p>
<%}%>
<p align="justify" class="perInvoicelocfont">
   The ownership of the goods will not pass to the purchaser will remain with the company until payment in full of the purchase price of the goods (Including sales tax there of ) owing to the company by the purchaser (Before the ownership passes to the purchaser the following conditions apply.)<br>
<%if(!cnt.equals("sbs")){%>
1)            The purchaser hold the goods as fiduciary bailer and agent for the company.<br>
2)            The purchaser must return the goods immediately upon demand of thee company.<br>
3)            If the purchaser fails to return the goods when demanded the company is enlited to go on to and premises occupied by the purchaser and to do all things necessary in order in take possession of the goods. The purchaser shall be liable for all cost of whatsoever nature of and associated with the exercise of the company’s right under the clause, which shall be payable on demand.<br>
4)            The goods shall be stored separately and in a manner to enable them to be identified and cross-referenced to particular invoice.<br>
5)            Risk in the goods shall pass at the time of delivery and the purchaser shall insure (and keep insured) the goods.<br>
6)            Unless otherwise notified in writing the purchaser is entitled to sell the goods in the ordinary course of business. The purchaser shall not otherwise deal with the goods in such sale the purchaser shall be the company’s agent and must hold the proceeds in a separate account on trust for the company and should not mix the proceeds with any other money. Including funds of the purchaser.<br>
7)            A breach of any of the above conditions on the part of the purchaser shall also be construed as a breach of trust.<br>
8)            Overdue balances are subject to an additional charge 2% per month until paid in full.<br>
9)            Goods once sold are not returnable.<br>
10)          This invoice is only for bank transfer if you want to pay in cash then you’ll be charged by 0.30% extra from invoice amount.<br>
11)          We declare that this Invoice shows the actual price of the goods described and that all particulars are true and correct.<br>
12)          The diamonds herein invoiced are exclusively of natural origin and untreated based on personal Knowledge and/or written guarantees provided by the supplier of these diamonds.<br>
13)          The Acceptance of goods herein invoiced will be as per WFDB guideline.<br>
<%}else{%>
1)            The purchaser hold the goods as fiduciary bailer and agent for the company.<br>
2)            The purchaser must return the goods immediately upon demand of thee company.<br>
3)            We charge US$ 150 for any shipment which is below US$ 15000 and there are no charges for invoice above US$15000.<br>
4)            Prices based on the Latest RAPAPORT price for invoicing.<br>
5)            Risk in the goods shall pass at the time of delivery and the purchaser shall insure (and keep insured) the goods.<br>
6)            In case of delay in payment, customers should inform us about the same: via email or fax, if they are unable to do so, we reserves the right to release the goods.<br>
7)            A breach of any of the above conditions on the part of the purchaser shall also be construed as a breach of trust.<br>
8)            Goods once sold are not returnable.<br>
9)            The diamonds herein invoiced are exclusively of natural origin and untreated based on personal Knowledge and/or written guarantees provided by the supplier of these diamonds.<br>;
10)           The Acceptance of goods herein invoiced will be as per WFDB guideline.<br>;
<%}%>
</p>
<%mailmsg.append("The ownership of the goods will not pass to the purchaser will remain with the company until payment in full of the purchase price of the goods (Including sales tax there of ) owing to the company by the purchaser (Before the ownership passes to the purchaser the following conditions apply.)<br>");
if(!cnt.equals("sbs")){
mailmsg.append("1)            The purchaser hold the goods as fiduciary bailer and agent for the company.<br>");
mailmsg.append("2)            The purchaser must return the goods immediately upon demand of thee company.<br>");
mailmsg.append("3)            If the purchaser fails to return the goods when demanded the company is enlited to go on to and premises occupied by the purchaser and to do all things necessary in order in take possession of the goods. The purchaser shall be liable for all cost of whatsoever nature of and associated with the exercise of the company’s right under the clause, which shall be payable on demand.<br>");
mailmsg.append("4)            The goods shall be stored separately and in a manner to enable them to be identified and cross-referenced to particular invoice.<br>");
mailmsg.append("5)            Risk in the goods shall pass at the time of delivery and the purchaser shall insure (and keep insured) the goods.<br>");
mailmsg.append("6)            Unless otherwise notified in writing the purchaser is entitled to sell the goods in the ordinary course of business. The purchaser shall not otherwise deal with the goods in such sale the purchaser shall be the company’s agent and must hold the proceeds in a separate account on trust for the company and should not mix the proceeds with any other money. Including funds of the purchaser.<br>");
mailmsg.append("7)            A breach of any of the above conditions on the part of the purchaser shall also be construed as a breach of trust.<br>");
mailmsg.append("8)            Overdue balances are subject to an additional charge 2% per month until paid in full.<br>");
mailmsg.append("9)            Goods once sold are not returnable.<br>");
mailmsg.append("10)          This invoice is only for bank transfer if you want to pay in cash then you’ll be charged by 0.30% extra from invoice amount.<br>");
mailmsg.append("11)          We declare that this Invoice shows the actual price of the goods described and that all particulars are true and correct.<br>");
mailmsg.append("12)          The diamonds herein invoiced are exclusively of natural origin and untreated based on personal Knowledge and/or written guarantees provided by the supplier of these diamonds.<br>");
mailmsg.append("13)          The Acceptance of goods herein invoiced will be as per WFDB guideline.<br>");
}else{
mailmsg.append("1)            The purchaser hold the goods as fiduciary bailer and agent for the company.<br>");
mailmsg.append("2)            The purchaser must return the goods immediately upon demand of thee company.<br>");
mailmsg.append("3)            We charge US$ 150 for any shipment which is below US$ 15000 and there are no charges for invoice above US$15000.<br>");
mailmsg.append("4)            Prices based on the Latest RAPAPORT price for invoicing.<br>");
mailmsg.append("5)            Risk in the goods shall pass at the time of delivery and the purchaser shall insure (and keep insured) the goods.<br>");
mailmsg.append("6)            In case of delay in payment, customers should inform us about the same: via email or fax, if they are unable to do so, we reserves the right to release the goods.<br>");
mailmsg.append("7)            A breach of any of the above conditions on the part of the purchaser shall also be construed as a breach of trust.<br>");
mailmsg.append("8)            Goods once sold are not returnable.<br>");
mailmsg.append("9)          The diamonds herein invoiced are exclusively of natural origin and untreated based on personal Knowledge and/or written guarantees provided by the supplier of these diamonds.<br>");
mailmsg.append("10)          The Acceptance of goods herein invoiced will be as per WFDB guideline.<br>");
}
if(cnt.equals("ag")){
%>
<p>
CHEQUE USD/HKD - _______________________________________________________________________________________________<br><br>
CASH   USD/HKD - __________________________________________________________________________________________________<br><br>
TT USD/HKD - __________________________________________________________________________________________________<br>
</p>
<%}%>
<%}%>
<p><b>This contract is governed by the laws of Hong Kong</b></p>
<p align="right"><b style="font-size: 15px; padding:0px 50px 0px 0px;"><b>&nbsp;FOR <bean:write property="value(SIGN)" name="saleDeliveryForm" /></b></b></p><br><br>

<div class="paddinloc">
<div style="float:left; width:320px;">
____________________________________________
</div>
<div style="float:right">
____________________________________________&nbsp;&nbsp;&nbsp;&nbsp; 
</div>
</div>

<div class="paddinloc">
<div  class="paddinloc" style="float:left; width:310px; padding-bottom:10px">
Buyer’s Signature & Co. Chop Confirming the Purchase 
Of above mentioned goods as per terms set out.
</div>
<div style="float:right">
(AUTHORISED SIGNATURE(S))&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
</div>
</div>
</td></tr>
  </table>
  <%
  mailmsg.append("</p><p><b>This contract is governed by the laws of Hong Kong</b></p>");
  if(cnt.equals("ag")){
  mailmsg.append("<p>CHEQUE USD/HKD - ____________________________________________________________________________________________________<br><br>CASH   USD/HKD  - ____________________________________________________________________________________________________<br><br>TT USD/HKD - ________________________________________________________________________________________</p>");  
  }
  mailmsg.append("<p align=\"right\"><b style=\"font-size: 15px; padding:0px 50px 0px 0px;\"><b>&nbsp;FOR "+util.nvl((String)performaDtl.get("SIGN"))+"</b></b></p><br><br><span class=\"paddinloc\">");
  mailmsg.append("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<label align=\"left\" style=\"width:320px;\">____________________________________________");
  mailmsg.append("</label><label align=\"right\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;___________________________________________ </label></label><br>");
  mailmsg.append("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<label  class=\"paddinloc\" style=\"float:left; width:310px; padding-bottom:10px\">Buyers Signature & Co. Chop Confirming the Purchase<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; Of above mentioned goods as per terms set out.</label></td></tr></table>");
  session.setAttribute("mailmsg",mailmsg);
  }else{%>
  <table align="center"><tr><td>
  <%if(msg.equals("")){%>
  Please select pakets for Performa Invoice...
  <%}else{%>
  <%=msg%>
  <%}%>
  </td></tr>
  </table>
  <%}%>
  </body>
</html>
