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
    <title>Mix Delivery Update</title>
    <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/bse.css?v=<%=info.getJsVersion()%>"/>
    <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/style.css?v=<%=info.getJsVersion()%>"/>
    <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/memoScript.js?v=<%=info.getJsVersion()%> " > </script>
    <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/bse.js?v=<%=info.getJsVersion()%> " > </script>
    <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/assortScript.js?v=<%=info.getJsVersion()%> " > </script>
    <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/Validation.js " > </script>
    <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/priceChange.js?v=<%=info.getJsVersion()%> " > </script>
        <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/ajax.js?v=<%=info.getJsVersion()%> " > </script>
    <script language="javaScript" type="text/javascript" src="<%=info.getReqUrl()%>/calendar/calendar.js"></script>
   <link href="<%=info.getReqUrl()%>/calendar/calendar.css" rel="stylesheet" type="text/css" />

  </head>
<%
HashMap dbinfo = info.getDmbsInfoLst();
String cnt = (String)dbinfo.get("CNT");
  String repPath = (String)dbinfo.get("REP_PATH");
 String accessidn=(String)request.getAttribute("accessidn");
     HashMap allPageDtl= (info.getPageDetails() == null)?new HashMap():(HashMap)info.getPageDetails();
     HashMap pageDtl=(HashMap)allPageDtl.get("MIXDELIVERY_CONFIRMATION");
ArrayList pageList=new ArrayList();
ArrayList prpDspBlocked = info.getPageblockList();
HashMap pageDtlMap=new HashMap();
String fld_nme="",fld_ttl="",val_cond="",dflt_val="",fld_typ="",form_nme="",lov_qry="";
 String view =(String)request.getAttribute("view");
 ArrayList setcharge = (request.getAttribute("setcharge") == null)?new ArrayList():(ArrayList)request.getAttribute("setcharge");
        String logId=String.valueOf(info.getLogId());
        String onfocus="cook('"+logId+"')";
        %>
 <body onfocus="<%=onfocus%>" >
 
  <% pageList=(ArrayList)pageDtl.get("MEMODTL");
       if(pageList!=null && pageList.size() >0){%>
   <select id="memoDtl" name="memoDtl" style="display:none">
    <%   for(int j=0;j<pageList.size();j++){
            pageDtlMap=(HashMap)pageList.get(j);
            dflt_val=(String)pageDtlMap.get("dflt_val");
           fld_ttl=(String)pageDtlMap.get("fld_ttl");%>
       <option value="<%=dflt_val%>"><%=fld_ttl%></option>     
    <% }%>
    </select>
    <%}else{ %>
      <select id="memoDtl" name="memoDtl" style="display:none">
       <option value="id">Memo Id</option>     
        <option value="dte">Date</option>     
      </select>
    <%}%>
<input type="hidden" name="CNT" id="CNT" value="<%=cnt%>" />
<input type="hidden" name="REQURL" id="REQURL" value="<%=info.getReqUrl()%>" />
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
  <img src="../images/line.jpg" border="0" /></td><td valign="middle"> <span class="pgHed">Mix Delivery Update</span> </td>
</tr></table></td></tr>

 <% if(request.getAttribute("msg")!=null){%>
  <tr><td valign="top" class="tdLayout">
   <span class="redLabel"> <%=request.getAttribute("msg")%></span>
  </td></tr>
 <%}%>

 <tr><td valign="top" class="tdLayout">
  <table>
  <tr><td><table><tr><td>
  <table><tr><td>
  <html:form action="/mixAkt/mixDeliveryUpdateAction.do?method=loadPkt" method="POST" onsubmit="return validate_sale()">
 
 <table cellpadding="0" cellspacing="0" border="0"><tr><td valign="top">
  <table class="grid1" >
  <tr><th colspan="2">Sale Search </th></tr>
 <tr>
<td>Buyer Name :</td>
<td>
 <html:select property="byrIdn" name="mixDeliveryUpdateForm" onchange="getFinalByrDlvMix(this,'DLV')" styleId="byrId" >
    <html:option value="0">---select---</html:option>
    <html:optionsCollection property="byrLst" name="mixDeliveryUpdateForm"  value="byrIdn" label="byrNme" />
                
  </html:select>
               
</td></tr> 
<tr>
<td>Billing  Party :</td><td>
<html:select property="prtyIdn" name="mixDeliveryUpdateForm" onchange="getSaleTrmsDlvMix(this.value , 'DLV')"  styleId="prtyId"  >
 <html:option value="0">---select---</html:option>    
</html:select>
</td>
</tr>
<tr>
<td>Terms :</td><td>
<html:select property="relIdn" name="mixDeliveryUpdateForm"  onchange="getSaleIdnDlvMix('DLV')" styleId="rlnId"  >
     <html:option value="0">---select---</html:option>
</html:select>

</td>
</tr>
<tr><td colspan="2">OR</td> </tr>
   <tr><td>Date</td><td>
<table><tr><td>Date From : </td><td>
    <html:text property="value(dtefr)" styleClass="txtStyle"  styleId="dtefr"  size="12"  /> <a href="#" style="margin-top:5px;"  onClick="setYears(1900, '<%=info.getCurrentYear()%>' );showCalender(this, 'dtefr');">
                <img src="<%=info.getReqUrl()%>/images/calender.png"></a></td>
                <td>Date To : </td><td>
    <html:text property="value(dteto)" styleClass="txtStyle"  styleId="dteto"  size="12"  /> <a href="#"  onClick="setYears(1900, '<%=info.getCurrentYear()%>' );showCalender(this, 'dteto');">
                <img src="<%=info.getReqUrl()%>/images/calender.png"></a></td></tr></table>
     </td> </tr>
<tr><td colspan="2">OR</td> </tr>
  <tr><td>Delivery ID </td><td>
<html:text property="value(dlvID)" name="mixDeliveryUpdateForm" styleId="dlvID" /> 


</td> </tr>


<tr><td colspan="2"><html:submit property="submit" value="View" styleClass="submit"/></td></tr>           
</table>
</td><td valign="top">
<div id="memoIdn"></div>
</td></tr></table>
</html:form>
  </td>
    <td  valign="top">
    
    </td>
  </tr></table>
  </td>
  
  </tr>
  

  </table>
</td></tr>

</table></td></tr>
<%
if(view!=null){
%>
  <tr> <td valign="top" class="tdLayout">
       Create Excel <img src="../images/ico_file_excel.png" border="0" onclick="goTo('<%=info.getReqUrl()%>/report/genericReport.do?method=createXL','','')"/>
     </td></tr>
<tr><td valign="top" class="tdLayout">
<html:form action="/mixAkt/mixDeliveryUpdateAction.do?method=save" method="post">
<%  

ArrayList pkts = (ArrayList)request.getAttribute("PktList");
if(pkts!=null && pkts.size()>0){
%>

<table>
<tr><td>
<label class="pgTtl">Delivery Packets</label>
  </td></tr>
  <tr><td>
  <html:submit property="value(submit)" value="Save" styleClass="submit"/>
  </td></tr>
<tr><td>
<% ArrayList   itemHdr = new ArrayList();  
    itemHdr.add("SR");
    itemHdr.add("Delivery Id");
    itemHdr.add("Buyer");
    itemHdr.add("Packet Code");
    itemHdr.add("Qty");
          
HashMap prp = info.getPrp();
ArrayList prpColList = (ArrayList)prp.get("COLV");
ArrayList prpClrList = (ArrayList)prp.get("CLRV");
ArrayList prps = (ArrayList)session.getAttribute("mixdlvViewLst"); %>
<table class="grid1">
                <tr>
                <th><input type="checkbox"  value="IS" id="IS" onclick="CheckBOXALLForm('1',this,'cb_dlv_')" />&nbsp;  </th>
                    <th>Sr</th>
                   
                    <th>Delivery Id</th>
                     <th>Buyer</th>
                      <th>Packet Code</th>
                      
                       <th>Qty</th>
                        
                    
                     <%for(int j=0; j < prps.size(); j++) {
                     String lprp=(String)prps.get(j);
                     if(prpDspBlocked.contains(lprp)){
                     }else{%>
                        <th><%=lprp%></th>
                    <%
                     itemHdr.add(lprp);
                    }}
                     itemHdr.add("rate");
                     itemHdr.add("Quot");
                     itemHdr.add("Amount");
                    
                    %>
                    <th>Col</th><th>Clarity</th><th>DIAMETER</th><th>Height</th><th>Length</th><th>Width</th>
                    <th>Remark</th>
                   <th>Prc / Crt </th>
                   <th>Quot</th>
                   <th>Amount</th>
                  
                </tr>
            <%
             int count =0 ;
             ArrayList pktList = new ArrayList();
              
                for(int i=0; i < pkts.size(); i++) {
                HashMap pktPrpMap = new HashMap();
                 count=i+1;
                    PktDtl pkt = (PktDtl)pkts.get(i);
                    String slRmk = util.nvl(pkt.getValue("RMK"));
                    long pktIdn = pkt.getPktIdn();
                  String saleIdn=  pkt.getSaleId();
                String qtyTxt="value(QTY_"+pktIdn+")";
                String rmkTxt="value(RMK_"+pktIdn+")";
                String colTxt="value(COL_"+pktIdn+")";
                String clrTxt="value(CLR_"+pktIdn+")";
                String diaTxt="value(DIA_"+pktIdn+")";
                String htTxt="value(HT_"+pktIdn+")";
                String lnTxt="value(LN_"+pktIdn+")";
                String wdTxt="value(WD_"+pktIdn+")";
                pktPrpMap.put("SR",String.valueOf(count));
                 pktPrpMap.put("Delivery Id",pkt.getSaleId());
                 pktPrpMap.put("Buyer",pkt.getValue("byr"));
                  pktPrpMap.put("Packet Code",pkt.getVnm());
                   pktPrpMap.put("Qty",pkt.getQty());
                %>
                <tr>
                <td><input type="checkbox" name="cb_dlv_<%=saleIdn%>" value="<%=saleIdn%>_<%=pktIdn%>" id="cb_dlv_<%=saleIdn%>"  /></td>
                <td><%=(i+1)%></td>
             
                         
                <td><%=pkt.getSaleId()%></td><td><%=pkt.getValue("byr")%></td>
                 <td><%=pkt.getVnm()%></td>
                  <td>
                  <html:text property="<%=qtyTxt%>"  name="mixDeliveryUpdateForm" size="7" /></td>
                
                <%for(int j=0; j < prps.size(); j++) {
                String lprp = (String)prps.get(j);
                pktPrpMap.put(lprp,util.nvl(pkt.getValue((String)prps.get(j))));
                if(prpDspBlocked.contains(lprp)){
                  }else{
                   %>
                    <td>
                 
                    <%=util.nvl(pkt.getValue((String)prps.get(j)))%></td>
                <%}}
                %>
                   <td> <html:text property="<%=colTxt%>"  name="mixDeliveryUpdateForm" size="5" /></td>
                 <td> <html:text property="<%=clrTxt%>"  name="mixDeliveryUpdateForm" size="5" /></td>
                 <td> <html:text property="<%=diaTxt%>"  name="mixDeliveryUpdateForm" size="5" /></td>
                 <td> <html:text property="<%=htTxt%>"  name="mixDeliveryUpdateForm" size="5" /></td>
                 <td> <html:text property="<%=lnTxt%>"  name="mixDeliveryUpdateForm" size="5" /></td>
                 <td> <html:text property="<%=wdTxt%>"  name="mixDeliveryUpdateForm" size="5" /></td>
               <td> <html:text property="<%=rmkTxt%>"  name="mixDeliveryUpdateForm"  size="20" /></td>
               
                <td><%=pkt.getRte()%></td>
                <td><%=pkt.getMemoQuot()%></td>
                 <td><%=pkt.getValue("vlu")%></td>
                </tr>
              <% 
                 pktPrpMap.put("rate",pkt.getRte());
                   pktPrpMap.put("Quot",pkt.getMemoQuot());
                    pktPrpMap.put("Amount",pkt.getValue("vlu"));
                     pktList.add(pktPrpMap); 
                }
                 session.setAttribute("pktList", pktList);
                session.setAttribute("itemHdr", itemHdr);
            %>
          
            </table></td></tr>
          
             </table>
           <%
           
           }else{%>
            Sorry no result found.
            <%}%>
            </html:form>
</td>
</tr>
<%}%>
 <tr>
    <td><jsp:include page="/footer.jsp" /> </td>
    </tr>
</table>
 <%@include file="/calendar.jsp"%>
</body>
</html>
