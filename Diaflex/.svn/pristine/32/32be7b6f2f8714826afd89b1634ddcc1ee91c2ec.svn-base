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
    <title>Proforma Invoice</title>
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
     HashMap dbinfo = info.getDmbsInfoLst();
      ArrayList perFormInvLst =(ArrayList)request.getAttribute("itemHdr");
       String cnt =(String)dbinfo.get("CNT");
        ArrayList chargeLst = (ArrayList)request.getAttribute("chargeLst");
        ArrayList pktList = (ArrayList)request.getAttribute("pktList");
        String inc_vlu = util.nvl((String)request.getAttribute("inc_vlu"));
        String typ = util.nvl((String)request.getAttribute("typ"));
        String msg = util.nvl((String)request.getAttribute("msg"));
        String consignee =  util.nvl((String)request.getAttribute("consignee"));
          String invrefno =  util.nvl((String)request.getAttribute("invrefno"));
        
//        if(typ.equals("MIX"))
//        perFormInvLst=(ArrayList)session.getAttribute("perInvBoxViewLst");
        StringBuffer mailmsg=new StringBuffer();
        HashMap performaDtl=(HashMap)request.getAttribute("performaDtl");
        String cfr = util.nvl((String)performaDtl.get("CFR"));
      if(cfr.equals(""))
     cfr="CFR";

    if(pktList!=null && pktList.size() > 0){
    String byrIdn=util.nvl((String)performaDtl.get("byrIdn"));
    String urlmail="performa.do?method=mail&byrIdn="+byrIdn;
    String onclick="newWindow('"+urlmail+"');";
    mailmsg.append("<table class=\"perInvoice\" style=\"border:1px solid Black ;\"  width=\"810\" align=\"center\" cellspacing=\"0\" cellpadding=\"0\">");
    mailmsg.append("<tr valign=\"top\"><td colspan=\"2\" rowspan=\"3\"  style=\"border:0px;\">");
    mailmsg.append("<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" style=\"border:1px solid white;\">");
    mailmsg.append("<tr valign=\"top\"><td style=\"border:1px solid white;\"><img src=\""+info.getReqUrl()+"/images/clientsLogo/"+util.nvl((String)performaDtl.get("comlogo"))+"\" />");
    mailmsg.append("</td>");
    mailmsg.append("<td style=\"border:1px solid white;\"><p>  <B>"+util.nvl((String)performaDtl.get("COMPANY"))+"</b><br>");
    mailmsg.append("<br>");
    mailmsg.append(util.nvl((String)performaDtl.get("combldg"))+"<br>");
    mailmsg.append(util.nvl((String)performaDtl.get("comadd1"))+"<br>");
    mailmsg.append(util.nvl((String)performaDtl.get("comadd2"))+"<br>");
    mailmsg.append(util.nvl((String)performaDtl.get("comcity"))+"<br>");
    mailmsg.append(util.nvl((String)performaDtl.get("comEmail"))+"<br>");
     mailmsg.append("Tel No:"+util.nvl((String)performaDtl.get("comTel"))+", Fax No :"+util.nvl((String)performaDtl.get("comFax"))+"<br>");
     mailmsg.append("</p></td></tr></table>");
     mailmsg.append("</td><td class=\"heightTd\" style=\"border-top:0px;height:25px;padding: 5px;border-left: 1px solid Black;border-top: 1px solid  Black ;\">");
     if(!invrefno.equals("")){
      mailmsg.append("Invoice No. & Date<br> <b>"+invrefno+"&nbsp;<br> Ref No. <br>  <b>"+util.nvl((String)performaDtl.get("invno"))+"&nbsp;");
      }else{
            mailmsg.append("Invoice No. & Date<br>   <b>"+util.nvl((String)performaDtl.get("invno"))+"&nbsp;");

      }
     HashMap allPageDtl= (info.getPageDetails() == null)?new HashMap():(HashMap)info.getPageDetails();
     HashMap pageDtl=(HashMap)allPageDtl.get("PERFORMA_INV");      
     ArrayList pageList=new ArrayList();
     HashMap pageDtlMap=new HashMap();
     String fld_ttl="",dflt_val="",lov_qry="",flg1="";
     String val_cond="",fld_nme="",fld_typ="";%>
<!--<html:button property="value(mail)" value="Send Mail" styleId="mail" onclick="<%=onclick%>" styleClass="submit" />  -->         
 <%  pageList=(ArrayList)pageDtl.get("MAIL");
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
<span class="no-print"><html:button property="<%=fld_nme%>" value="<%=fld_ttl%>" styleId="<%=fld_id%>" onclick="<%=val_cond%>" styleClass="submit" />  </span>         
<%}}%>
  <table class="perInvoice" width="810" align="center" cellspacing="0" cellpadding="3">
  <tr><td colspan="2" rowspan="3" style="border:0px;">
<table border="0" cellpadding="0" cellspacing="0" style="border:1px solid white;">
  <tr><td style="border:1px solid white;"><img src="../images/clientsLogo/<bean:write property="value(comlogo)" name="saleDeliveryForm" />" width="100px" height="100px" /></div>
  </td>
  
  <td style="border:1px solid white;"><p>  <B><bean:write property="value(COMPANY)" name="saleDeliveryForm" /></b><br>
<bean:write property="value(combldg)" name="saleDeliveryForm" /><br>
  <bean:write property="value(comadd1)" name="saleDeliveryForm" /><br>
  <bean:write property="value(comadd2)" name="saleDeliveryForm" /><br>
  <bean:write property="value(comcity)" name="saleDeliveryForm" /><br>
    <bean:write property="value(comEmail)" name="saleDeliveryForm" /><br>
   Tel No: <bean:write property="value(comTel)" name="saleDeliveryForm" /> , Fax No : <bean:write property="value(comFax)" name="saleDeliveryForm" /><br>
   </p>
</td>
  </tr>
</table>
</td><td class="heightTd">
<% if(!invrefno.equals("")){%>
Invoice No. & Date<br> <b><%=invrefno%>
&nbsp;<%=util.nvl((String)performaDtl.get("datePrtVal"))%></b> 
<br>
Ref. No.<br> <b><bean:write property="value(invno)" name="saleDeliveryForm" /></b>
<%}else{%>
Invoice No. & Date<br> <b><bean:write property="value(invno)" name="saleDeliveryForm" />
&nbsp;<%=util.nvl((String)performaDtl.get("datePrtVal"))%></b> 
<%}%>

</td><td class="heightTd">Exporter's Ref.<br>
<b> <bean:write property="value(exportRef)" name="saleDeliveryForm" /></b>
<%mailmsg.append(util.nvl((String)performaDtl.get("datePrtVal"))+"</b> </td><td class=\"heightTd\" style=\"border-top:0px;height:25px;padding:5px;border-left: 1px solid Black;border-top: 1px solid  Black ;\">Exporter's Ref.<br><b>"+util.nvl((String)performaDtl.get("exportRef")));
mailmsg.append("</td></tr>");
mailmsg.append("<tr><td class=\"heightTd\"  style=\"height:25px;padding: 5px;border-left: 1px solid Black;border-top: 1px solid  Black ;\">Buyer's Order no. & Date <br><b>"+util.nvl((String)performaDtl.get("datePrtVal"))+"</b>  </td><td class=\"heightTd\" style=\"padding: 5px;border-left: 1px solid Black;border-top: 1px solid  Black ;\"><b>"+util.nvl((String)performaDtl.get("RBICODE"))+"<br>Pan NO:"+util.nvl((String)performaDtl.get("PAN NO"))+"</b></td></tr>");
mailmsg.append("<tr><td class=\"heightTd\"  style=\"height:25px;padding: 5px;border-left: 1px solid Black;border-top: 1px solid  Black ;\">Other Reference (s) & Date <br>GR NO</td><td class=\"heightTd\"  style=\"height:25px;padding: 5px;border-left: 1px solid Black;border-top: 1px solid  Black ;\">IE Code:"+util.nvl((String)performaDtl.get("exportRef"),"0388004")+"</td></tr>");
mailmsg.append(" <tr><td colspan=\"2\" rowspan=\"3\" style=\"padding:10px;border-left: 1px solid Black;border-top: 1px solid  Black ;\"  ><span >Consignee :");
mailmsg.append("<br> <b>");
%>
</td></tr>
   
 <tr><td class="heightTd">Buyer's Order no. & Date <br><b><%=util.nvl((String)performaDtl.get("datePrtVal"))%></b>  </td><td class="heightTd"><b><bean:write property="value(RBICODE)" name="saleDeliveryForm" /><br>Pan NO:<bean:write property="value(PAN NO)" name="saleDeliveryForm" /></b></td></tr>
 <tr><td class="heightTd">Other Reference (s) & Date <br>GR NO</td><td class="heightTd">IE Code: 
  <logic:equal property="value(exportRef)" name="saleDeliveryForm" value="" >
  0388004
  </logic:equal>
    <logic:notEqual property="value(exportRef)" name="saleDeliveryForm" value="" >
  <bean:write property="value(exportRef)" name="saleDeliveryForm" />
  </logic:notEqual>
 </td></tr>
 <tr><td colspan="2" rowspan="3" style="padding:10px;"><span >Consignee :
 <br>
 <%
 String[] consignLst = consignee.split("#ln");
 if(consignLst.length>0){
 for(int c=0;c<consignLst.length;c++){
%>
 <b>
 <%=consignLst[c]%>

 </b>

 <br>
 <%mailmsg.append("<b>"+consignLst[c]+"</b><br>");
 }}%>
 <%mailmsg.append("</span></td><td style=\"height:80px;border-left: 1px solid Black;border-top: 1px solid  Black ;\" class=\"heightTd\" colspan=\"2\">Buyer:<br><b>");
 mailmsg.append(util.nvl((String)performaDtl.get("byrnme"))+"</b><br>");
 mailmsg.append(util.nvl((String)performaDtl.get("unit_no"))+",");
  mailmsg.append(util.nvl((String)performaDtl.get("bldg"))+"<br>");
    mailmsg.append(util.nvl((String)performaDtl.get("landMk"))+"<br>");
    mailmsg.append(util.nvl((String)performaDtl.get("addr"))+"<br>");
    mailmsg.append("<br><b> Tel No-"+util.nvl((String)performaDtl.get("BYTEL_NO"))+"</b>");
    mailmsg.append("&nbsp;<span style=\"padding-left:20px;\"><b>Fax-"+util.nvl((String)performaDtl.get("BYFAX"))+"</b></span>");
 %>
 </span>
 </td>
 
 <td style="height:80px;" class="heightTd" colspan="2">Buyer:
 <br><b>  <bean:write property="value(byrnme)" name="saleDeliveryForm" /></b>
 <br>   <bean:write property="value(unit_no)" name="saleDeliveryForm" />,
 <bean:write property="value(bldg)" name="saleDeliveryForm" />
 <br> <bean:write property="value(landMk)" name="saleDeliveryForm" />
 <br> <bean:write property="value(addr)" name="saleDeliveryForm"   />
 <br><b> Tel No-</b><bean:write property="value(BYTEL_NO)" name="saleDeliveryForm"   />
 <span style="padding-left:20px;"><b>Fax-</b><bean:write property="value(BYFAX)" name="saleDeliveryForm"   /></span>
 <logic:notEqual property="value(BYATTN)" name="saleDeliveryForm" value="" >
<span style="padding-left:20px;"><b>ATTN.-</b><bean:write property="value(BYATTN)" name="saleDeliveryForm" /></span>
<%mailmsg.append("<span style=\"padding-left:20px;\"><b>ATTN.-"+util.nvl((String)performaDtl.get("BYATTN"))+"</span>");%>
</logic:notEqual>
<%mailmsg.append("</td></tr><tr><td class=\"heightTd\" style=\"border-left: 1px solid Black;border-top: 1px solid  Black ;\">Country of Origin of goods <br><b> INDIA</b></td><td class=\"heightTd\" style=\"border-left: 1px solid Black;border-top: 1px solid  Black ;\">");
mailmsg.append("Country of Final Destination<br><b>"+util.nvl((String)performaDtl.get("BYFINALDEST"))+"</b></td></tr>");
mailmsg.append("<tr><td colspan=\"2\" class=\"heightTd\" style=\"height:25px;border-left: 1px solid Black;border-top: 1px solid  Black ;\">Terms of Delivery and Payment<br><b>");
mailmsg.append(util.nvl((String)performaDtl.get("terms"))+"</b></td></tr>");
%>
 </td></tr>
 <tr><td class="heightTd">Country of Origin of goods <br><b> INDIA</b></td><td class="heightTd">Country of Final Destination
 <br><b> <bean:write property="value(BYFINALDEST)" name="saleDeliveryForm" /></b>
 </td></tr>
 <tr><td colspan="2" class="heightTd" style="height:25px;">Terms of Delivery and Payment<br><b>
 <bean:write property="value(terms)" name="saleDeliveryForm" />
 </b></td></tr>
 <%mailmsg.append("<tr><td  style=\"height: 25px; padding: 0px; border-left:0px;border-left: 1px solid Black;border-top: 1px solid  Black ;\">Pre-Carraige by <br> <b>"+util.nvl((String)performaDtl.get("courierS"))+"</b></td>");
 mailmsg.append("<td  style=\"height: 25px; padding: 0px;border-left: 1px solid Black;border-top: 1px solid  Black ;\">Place of Receipt by Pre-carrier <br><b>"+util.nvl((String)performaDtl.get("cityNme"))+"</b> </td>");
 mailmsg.append("<td colspan=\"2\" rowspan=\"3\" class=\"heightTd\" style=\"border-left: 1px solid Black;border-top: 1px solid  Black ;\">Our Bankers: <br>");
 mailmsg.append("<b>");
 mailmsg.append(util.nvl((String)performaDtl.get("bankNme"))+"<br>");
  mailmsg.append(util.nvl((String)performaDtl.get("bankBldg"))+"<br>");
   mailmsg.append(util.nvl((String)performaDtl.get("bankLand"))+"<br>"); 
   mailmsg.append(util.nvl((String)performaDtl.get("bankCity"))+"<br>");
 mailmsg.append("</b>");
if(!cnt.equals("hk")){
mailmsg.append("<b>Ifsc Code :</b>"+util.nvl((String)performaDtl.get("BYIFSC"))+"&nbsp;");
mailmsg.append("<b>Account No :</b>"+util.nvl((String)performaDtl.get("BYBANKACCOUNT")));
}
mailmsg.append("</td></tr><tr><td  style=\"height: 25px; padding: 0px;  border-left:0px;border-left: 1px solid Black;border-top: 1px solid  Black ;\">VesselFlight No<br><b>"+util.nvl((String)performaDtl.get("BYVESSELNO"))+"</b></td>");
mailmsg.append("<td style=\"height: 25px; padding: 0px;border-left: 1px solid Black;border-top: 1px solid  Black ;\">Port of Loading <br><b>"+util.nvl((String)performaDtl.get("cityNme"))+"</b></td></tr>");
mailmsg.append("<tr><td  style=\"height: 25px; padding: 0px;  border-left:0px;border-left: 1px solid Black;border-top: 1px solid  Black ;\">Port of Discharge<br><b>"+util.nvl((String)performaDtl.get("BYPORTDIS"))+" </b></td>");
mailmsg.append("<td style=\"height: 25px; padding: 0px;border-left: 1px solid Black;border-top: 1px solid  Black ;\">Final Destination<br><b>"+util.nvl((String)performaDtl.get("BYFINALDEST"))+" </b></td></tr>");
 %>
 <tr><td class="heightTd">Pre-Carraige by <br> <b><bean:write property="value(courierS)" name="saleDeliveryForm"   /></b></td>
 <td class="heightTd">Place of Receipt by Pre-carrier <br><b><bean:write property="value(cityNme)" name="saleDeliveryForm" /></b> </td><td colspan="2" rowspan="3" class="heightTd" >Our Bankers: <br>
 <b><bean:write property="value(bankNme)" name="saleDeliveryForm" /><br>
  <bean:write property="value(bankBldg)" name="saleDeliveryForm" /><br>
  <bean:write property="value(bankLand)" name="saleDeliveryForm" /><br>
  <bean:write property="value(bankCity)" name="saleDeliveryForm" /> </b><br>
<%if(!cnt.equals("hk")){%>
 <b>Ifsc Code :</b><bean:write property="value(BYIFSC)" name="saleDeliveryForm" />&nbsp;
 <b>Account No :</b><bean:write property="value(BYBANKACCOUNT)" name="saleDeliveryForm" />
 <%}%>
 </td></tr>
 <tr><td class="heightTd">VesselFlight No<br><b> <bean:write property="value(BYVESSELNO)" name="saleDeliveryForm"   /></b></td>
 <td class="heightTd">Port of Loading <br><b><bean:write property="value(cityNme)" name="saleDeliveryForm" /></b></td></tr>
 <tr><td class="heightTd">Port of Discharge
  <br><b> <bean:write property="value(BYPORTDIS)" name="saleDeliveryForm"   /></b>
 </td><td class="heightTd">Final Destination
 <br><b> <bean:write property="value(BYFINALDEST)" name="saleDeliveryForm"   /></b>
 </td></tr>
 <tr><td colspan="4" valign="top">
 
 <table width="100%" class="invSub"  cellspacing="0">
 <%int colspn=perFormInvLst.size()+1;%>
 <%mailmsg.append("<tr><td colspan=\"4\" valign=\"top\" style=\"border-left:0px;border-left: 1px solid Black;border-top: 1px solid  Black ;\"><table width=\"100%\" class=\"invSub\"  cellspacing=\"0\" cellpadding=\"0\">");
 mailmsg.append("<tr><td class=\"paddin\" colspan=\""+colspn+"\" style=\" border-top:0px;border-left: 1px solid Black;border-top: 1px solid  Black ;\">Marks & Nos./ No. & kind of Pkgs. Description of Goods</td><td rowspan=\"2\" align=\"right\"  style=\" border-top:0px;border-left: 1px solid Black;border-top: 1px solid  Black ;\"> Quantity</td><td rowspan=\"2\" align=\"right\" style=\" border-top:0px;border-left: 1px solid Black;border-top: 1px solid  Black ;\">Carats</td><td rowspan=\"2\" align=\"right\" style=\" border-top:0px;border-left: 1px solid Black;border-top: 1px solid  Black ;\">Rate</td><td rowspan=\"2\" align=\"right\" style=\" border-top:0px;border-left: 1px solid Black;border-top: 1px solid  Black ;\">Amount</td></tr>");
 mailmsg.append("<tr><td class=\"paddin\" colspan=\""+colspn+"\" style=\"border-left:0px;border-left: 1px solid Black;border-top: 1px solid  Black ;\">ADD 1 TIN BOX CUT & POLISHED DIAMONDS</td> </tr>");
 %>
 
 <tr><td class="paddin" colspan="<%=colspn%>">Marks & Nos./ No. & kind of Pkgs. Description of Goods</td><td rowspan="2" align="right"> Quantity</td><td rowspan="2" align="right">Carats</td><td rowspan="2" align="right">Rate</td><td rowspan="2" align="right">Amount</td></tr>
 <tr><td class="paddin" colspan="<%=colspn%>">
<%  pageList=(ArrayList)pageDtl.get("PERFORMDSC");
      if(pageList!=null && pageList.size() >0){
      for(int j=0;j<pageList.size();j++){
      pageDtlMap=(HashMap)pageList.get(j);
       fld_ttl=(String)pageDtlMap.get("dflt_val");
         lov_qry=(String)pageDtlMap.get("lov_qry");
      %>
      <%=fld_ttl%> <%=lov_qry%>
     <% }}else{%>
 ADD 1 TIN BOX CUT & POLISHED DIAMONDS
 <%}%>
 </td> </tr>
 <%for(int i=0;i<pktList.size();i++){
  HashMap pktDtl = (HashMap)pktList.get(i);
  String pkt_ty = util.nvl((String)pktDtl.get("pkttyp"));
  if(!pkt_ty.equals("NR"))
  pktDtl.put("VNM","");
   mailmsg.append("<tr><td  style=\"border-left:0px;border-left: 1px solid Black;border-top: 1px solid  Black ;\">"+(i+1)+"</td>");
  %>
  <tr>
  <td><%=i+1%></td>
  <%for(int j=0; j < perFormInvLst.size(); j++) {
   String prp = (String)perFormInvLst.get(j);
   prp=util.nvl((String)pktDtl.get(prp));
   if(cnt.equals("kj")){
   if(prp.indexOf("+") > -1){
    prp=prp.replaceAll("\\+"," ");
   }
   
   if(prp.indexOf("-") > -1){
    prp=prp.replaceAll("\\-"," ");
   }}
   mailmsg.append("<td style=\"border-left: 1px solid Black;border-top: 1px solid  Black ;\">"+prp+"</td>");
  %>
  <td><%=prp%></td>
  <%}%>
  <%mailmsg.append("<td align=\"right\" style=\"border-left: 1px solid Black;border-top: 1px solid  Black ;\">"+util.nvl((String)pktDtl.get("qty"))+"</td>");
  mailmsg.append("<td align=\"right\" style=\"border-left: 1px solid Black;border-top: 1px solid  Black ;\">"+util.nvl((String)pktDtl.get("cts"))+"</td>");
    mailmsg.append("<td align=\"right\" style=\"border-left: 1px solid Black;border-top: 1px solid  Black ;\">"+util.nvl((String)pktDtl.get("quot"))+"</td>");
      mailmsg.append("<td align=\"right\" style=\"border-left: 1px solid Black;border-top: 1px solid  Black ;\">"+util.nvl((String)pktDtl.get("vlu"))+"</td></tr>");
  %>
  <td align="right"><%=util.nvl((String)pktDtl.get("qty"))%></td>
  <td align="right"><%=util.nvl((String)pktDtl.get("cts"))%></td>

  <td align="right"><%=util.nvl((String)pktDtl.get("quot"))%></td>
  <td align="right"><%=util.nvl((String)pktDtl.get("vlu"))%></td>
  </tr>
 <%}%>
 <%
 mailmsg.append("<tr><td colspan=\""+(colspn+4)+"\" style=\"height:10px; border-left:0px;border-left: 1px solid Black;border-top: 1px solid  Black ;\">&nbsp;</td></tr>");
 mailmsg.append("<tr><td colspan=\""+(colspn)+"\"  style=\"border-left:0px;border-left: 1px solid Black;border-top: 1px solid  Black ;\">Total</td><td align=\"right\" style=\"border-left: 1px solid Black;border-top: 1px solid  Black ;\">"+util.nvl((String)performaDtl.get("ttlQty"))+"</td><td align=\"right\" style=\"border-left: 1px solid Black;border-top: 1px solid  Black ;\">  "+util.nvl((String)performaDtl.get("ttlCts"))+"</td><td align=\"right\" style=\"border-left: 1px solid Black;border-top: 1px solid  Black ;\">"+util.nvl((String)performaDtl.get("termsSign"))+"</td><td align=\"right\" style=\"border-left: 1px solid Black;border-top: 1px solid  Black ;\">"+util.nvl((String)performaDtl.get("ttlVlu"))+"</td></tr>");
 %>
 <tr><td colspan="<%=colspn+4%>" style="height:10px;">&nbsp;</td></tr>
 <tr><td colspan="<%=colspn%>">Total</td><td align="right"><bean:write property="value(ttlQty)" name="saleDeliveryForm" /></td><td align="right"><bean:write property="value(ttlCts)" name="saleDeliveryForm" /></td><td align="right"><bean:write property="value(termsSign)" name="saleDeliveryForm" /></td><td align="right"><bean:write property="value(ttlVlu)" name="saleDeliveryForm" /></td></tr>
 <%String echarge=util.nvl((String)performaDtl.get("echarge"));
 if(!echarge.equals("") && !echarge.equals("0")){
 mailmsg.append("<tr><td colspan=\""+(colspn+2)+"\" style=\"border-bottom: 1px solid white;border-left: 1px solid Black;border-top: 1px solid  Black ;\">Extra Charge</td>");
 mailmsg.append("<td align=\"right\" style=\"border-left: 1px solid Black;border-top: 1px solid  Black ;\">"+util.nvl((String)performaDtl.get("termsSign"))+"</td><td align=\"right\" style=\"border-left: 1px solid Black;border-top: 1px solid  Black ;\"><b>"+util.nvl((String)performaDtl.get("echarge"))+"</b></td>");
 mailmsg.append("</tr>");
 
 %>
 <tr><td colspan="<%=colspn+2%>" style="border-bottom: 1px solid white;">Extra Charge</td>
 <td align="right"> <bean:write property="value(termsSign)" name="saleDeliveryForm" /></td><td align="right"><b><bean:write property="value(echarge)" name="saleDeliveryForm" /></b></td>
 </tr>
 <%}%>
 <%if(chargeLst!=null && chargeLst.size()>0){
 for(int k=0; k < chargeLst.size(); k++) {
 HashMap charData=new HashMap();
 charData=(HashMap)chargeLst.get(k);
 mailmsg.append("<tr><td colspan=\""+(colspn+2)+"\" style=\"border-bottom: 1px solid white; border-left:0px;border-top: 1px solid  Black ;\">"+util.nvl((String)charData.get("DSC"))+"("+util.nvl((String)charData.get("RMK"))+")"+"</td>");
 mailmsg.append("<td align=\"right\" style=\"border-left: 1px solid Black;border-top: 1px solid  Black ;\">"+util.nvl((String)performaDtl.get("termsSign"))+"</td><td align=\"right\" style=\"border-left: 1px solid Black;border-top: 1px solid  Black ;\"><b>"+util.nvl((String)charData.get("CHARGE"))+"</b></td></tr>");
 %>
<tr><td colspan="<%=colspn+2%>" style="border-bottom: 1px solid white;"><%=util.nvl((String)charData.get("DSC"))%> (<%=util.nvl((String)charData.get("RMK"))%>)</td>
 <td align="right"> <bean:write property="value(termsSign)" name="saleDeliveryForm" /></td><td align="right"><b><%=util.nvl((String)charData.get("CHARGE"))%></b></td>
 </tr>
<%}}%>
 <%if(inc_vlu.equals("Y")){
 mailmsg.append("<tr><td colspan=\""+(colspn+2)+"\" style=\"border-bottom: 1px solid white; border-left:0px;border-top: 1px solid  Black ;\">Incentive Value</td>");
 mailmsg.append("<td align=\"right\"  style=\"border-bottom: 1px solid white; border-left:1px;border-top: 1px solid  Black\">"+util.nvl((String)performaDtl.get("termsSign"))+"</td><td align=\"right\"  style=\"border-bottom: 1px solid white; border-left:1px;border-top: 1px solid  Black\"><b>"+util.nvl((String)performaDtl.get("inc_vlu"))+"</b></td></tr>");
 %>
 <tr><td colspan="<%=colspn+2%>" style="border-bottom: 1px solid white;"> Incentive Value</td>
 <td align="right"><bean:write property="value(termsSign)" name="saleDeliveryForm" /></td><td align="right"><b><bean:write property="value(inc_vlu)" name="saleDeliveryForm" /></b></td>
 </tr>
 <%}
 mailmsg.append("<tr><td colspan=\""+(colspn+4)+"\" style=\"height:20px;border-left: 1px solid Black;border-top: 1px solid  Black ;\">&nbsp;</td></tr>");
 mailmsg.append("<tr><td colspan=\""+(colspn+2)+"\" style=\"border-bottom: 1px solid white; border-left:0px;border-top: 1px solid  Black\">Amount Chargeable&nbsp;&nbsp;<b>(In Words)</b>  "+cfr+" "+util.nvl((String)performaDtl.get("termsSign"))+" "+util.nvl((String)performaDtl.get("inwords")));
 mailmsg.append("</td><td align=\"right\" style=\"border-bottom: 1px solid white; border-left:1px solid  Black;border-top: 1px solid  Black\">"+util.nvl((String)performaDtl.get("termsSign"))+"</td><td align=\"right\" style=\"border-left: 1px solid Black;border-top: 1px solid  Black ;\"><b>"+util.nvl((String)performaDtl.get("grandttlVlu"))+"</b></td></tr>");
 mailmsg.append("</table></td></tr><tr><td colspan=\"4\" style=\"border-left:0px;border-left: 1px solid Black;border-top: 1px solid  Black ;\"><p><u><b>PAYMENT INSTRUCTIONS:</b></u><b>");
 if(!cnt.equals("hk")){
 mailmsg.append("PLEASE REMIT THE PROCEEDS TO");
 }
 mailmsg.append(""+util.nvl((String)performaDtl.get("rmk"))+"</b></p>");
 %>
 
  <tr><td colspan="<%=colspn+4%>" style="height:20px;">&nbsp;</td></tr>
 <tr><td colspan="<%=colspn+2%>" style="border-bottom: 1px solid white;">Amount Chargeable&nbsp;&nbsp;<b>(In Words)</b>
 
 <%=cfr%> <bean:write property="value(termsSign)" name="saleDeliveryForm" /> <bean:write property="value(inwords)" name="saleDeliveryForm" />

 </td><td align="right"><bean:write property="value(termsSign)" name="saleDeliveryForm" /></td><td align="right"><b><bean:write property="value(grandttlVlu)" name="saleDeliveryForm" /></b></td></tr>
 </table>
 </td></tr>
 <tr><td colspan="4">
 

<p><u><b>PAYMENT INSTRUCTIONS:</b></u> <b>

 <logic:equal property="value(rmk)" name="saleDeliveryForm" value="" >
 PLEASE REMIT THE PROCEEDS TO
  </logic:equal>
    <logic:notEqual property="value(rmk)" name="saleDeliveryForm" value="" >
 <bean:write property="value(rmk)" name="saleDeliveryForm" />
  </logic:notEqual>

</b></p>
<%
String courierS=util.nvl((String)performaDtl.get("courierS"));
if(courierS.equals("Vamaship"))
courierS=util.nvl((String)performaDtl.get("COMPANY"));
mailmsg.append("<p>DOOR TO DOOR INSURANCE COVERED BY "+courierS+" </p><P><b>NOTE:</b>TT Bank fee remain the responsobility of the remitter and must be charged to the remitter's debiting account. (All charge including inter mediatory bank charge to your account.)</p></td></tr>");
mailmsg.append("<tr><td  class=\"paddin\" colspan=\"2\" width=\"55%\" style=\"text-align:justify; text-justify:inter-word; padding:0px; font-size:12px;border-left: 1px solid Black;border-top: 1px solid  Black ;\"><label style=\"padding:2\"><b>On Confirmed outright sale basis Declaration:</b><br> 1) We declare that this invoice shows the actual price of the goods described and that all particulars are true and correct.<br> 2) The diamonds herein invoiced have been purchased from legitimate sources not involved in funding conflict and in compliance with the  United Nations Resolutions.  The seller hereby guarantees that these diamonds are conflict  free,  based on personal knowledge and/or written guarantees provided by the supplier of these diamonds.</label></td>");
%>
<p>DOOR TO DOOR INSURANCE COVERED BY <%=courierS%></p>

 <P><b>NOTE:</b>
 TT Bank fee remain the responsobility of the remitter and must be charged to the remitter's debiting account.
 (All charge including inter mediatory bank charge to your account.)
 </p>
 </td></tr>
<tr><td  class="paddin" colspan="2" width="55%" style="text-align:justify; text-justify:inter-word; padding:5px; font-size:12px;"><b>On Confirmed outright sale basis
Declaration:</b>
<%if(cnt.equalsIgnoreCase("fa")){%>
<br>
<br>
I/We hereby declare that all the goods mentioned in this memo are lab – grown diamonds/man – made diamonds. The buyer agrees to purchase these with the clear understanding that he/she is buying lab – grown diamonds/man – made diamonds. He/She also agrees explicitly to sell them as lab – grown diamonds/man – made diamonds, by making full and clear disclosure in his/her invoice to his/her customer. The goods mentioned in this memo ARE NOT natural diamonds/mined – diamonds.

<%}else{%>
<br>
1) We declare that this invoice shows the actual price of the goods described and that all particulars are true and correct.<br>
2) The diamonds herein invoiced have been purchased from legitimate sources not involved in funding conflict and in compliance with the  United Nations Resolutions.  The seller hereby  
guarantees that these diamonds are conflict  free,  based on personal knowledge and/or written guarantees provided by the
supplier of these diamonds.
<%}%>
</td>
<%
mailmsg.append("<td class=\"paddin\" colspan=\"2\" style=\"border-left: 1px solid Black;border-top: 1px solid  Black ;\"><table style=\"border:1px solid white;\" ><tr><td  nowrap=\"nowrap\" style=\"border:1px solid white; padding:20px;\" ><b>Signature & Date</b></td><td  nowrap=\"nowrap\" style=\"border:1px solid white; padding:20px;\" align=\"center\" ><b>&nbsp;"+util.nvl((String)performaDtl.get("SIGN"))+"</b></td></tr>");
mailmsg.append("<tr><td  nowrap=\"nowrap\" style=\"border:1px solid white; padding:35px 20px 20px 25px;\" ><b>"+util.getToDte()+"</b></td><td nowrap=\"nowrap\" style=\"border:1px solid white; padding:35px 20px 20px 20px;\" align=\"center\"  ><b>&nbsp;Partner / Auth.Sign.</b></td></tr></table></td></tr></table>");
%>
<td class="paddin" colspan="2">

<table style="border:1px solid white;" >
<tr>
<td  nowrap="nowrap" style="border:1px solid white; padding:20px;" ><b>Signature & Date</b></td><td  nowrap="nowrap" style="border:1px solid white; padding:20px;" align="center" ><b>&nbsp;FOR, <bean:write property="value(SIGN)" name="saleDeliveryForm" /></b></td>
</tr>

<tr>
<td  nowrap="nowrap" style="border:1px solid white; padding:35px 20px 20px 25px;" ><b><%=util.getToDte()%></b></td><td nowrap="nowrap" style="border:1px solid white; padding:35px 20px 20px 20px;" align="center"  ><b>&nbsp;Partner / Auth.Sign.</b></td>
</tr>
</table>
</td></tr>
  </table>
  <%
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
