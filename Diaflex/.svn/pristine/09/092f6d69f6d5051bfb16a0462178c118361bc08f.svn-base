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
    <title>Transfer To Mix</title>
    <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/bse.css?v=<%=info.getJsVersion()%>"/>
    <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/style.css?v=<%=info.getJsVersion()%>"/>
    <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/memoScript.js?v=<%=info.getJsVersion()%> " > </script>
    <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/bse.js?v=<%=info.getJsVersion()%> " > </script>
    <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/assortScript.js?v=<%=info.getJsVersion()%> " > </script>
      <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/Validation.js " > </script>
  </head>
 <%
HashMap dbinfo = info.getDmbsInfoLst();
String cnt = (String)dbinfo.get("CNT");
        String logId=String.valueOf(info.getLogId());
        String onfocus="cook('"+logId+"')";
        %>
 <body onfocus="<%=onfocus%>"onload="MM_preloadImages('images/faunalogo.png')" topmargin="0" leftmargin="0" rightmargin="0" bottommargin="0">
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
  <img src="../images/line.jpg" border="0" /></td><td valign="middle"> <span class="pgHed">Transfer To Mix</span> </td>
</tr></table>
<tr><td valign="top" class="tdLayout">
  <table cellpadding="2" cellspacing="2" >
  <%
   String vnmNtFnd = (String)request.getAttribute("vnmNotFnd");
     if(vnmNtFnd!=null){%>
    <tr><td valign="top" class="tdLayout"><span class="redLabel"><%=vnmNtFnd%></span></td></tr>
   <%}%>
  <%
        if(request.getAttribute("msg")!=null){%>
        <tr><td height="15"  valign="top" class="tdLayout"><span class="redLabel"> <%=request.getAttribute("msg")%></span></td></tr>
       <% }
        %>
       
  </table>
  </td>
  </tr>
 <tr><td valign="top" class="tdLayout">
  <table>
  <tr><td>
  <html:form action="/marketing/transferToMix.do?method=loadPkt" method="POST" onsubmit="return validate_sale()">
  <table class="grid1" >
  <tr><th colspan="2">Sale Search </th></tr>
 <tr>
<td>Buyer Name :</td>
<td>
 <html:select property="byrIdn" name="transferToMixForm" onchange="getFinalByrLS(this,'LS')" styleId="byrId" >
    <html:option value="0">---select---</html:option>
    <html:optionsCollection property="byrLst" name="transferToMixForm"  value="byrIdn" label="byrNme" />
                
  </html:select>
               
</td></tr> 
<tr>
<td>Billing  Party :</td><td>
<html:select property="prtyIdn" name="transferToMixForm" onchange="getSaleTrmsLS(this.value , 'SL')" styleId="prtyId"  >
     
</html:select>

</td>

</tr>
   <tr><td colspan="2"><div id="memoIdn"></div> </td></tr>

<tr><td>Packets. </td><td>
<html:textarea property="value(vnmLst)" name="transferToMixForm" styleId="vnmLst" /> </td> </tr>
<tr><td colspan="2"><html:submit property="submit" value="View" styleClass="submit"/></td></tr>           
</table></html:form>
  </td></tr>
  
   <html:form action="/marketing/transferToMix.do?method=save" method="POST"  onsubmit="return confirmChanges()">
   <%if(request.getAttribute("view")!=null){
     ArrayList pkts = (info.getValue("PKTS") == null)?new ArrayList():(ArrayList)info.getValue("PKTS");
     if(pkts!=null && pkts.size()>0){
   %>
   <tr><td>
   <table>
            
                <tr>
                <td><span class="pgTtl" >Exiting Selection</span></td>
                <td> <b>Terms : </b> </span></td><td>
                <bean:write property="value(trmsLb)" name="transferToMixForm" />
                <html:hidden property="relIdn" name="transferToMixForm" />
               
                </td>
                 <td> <b>Buyer :</b> </span></td><td>
                <bean:write property="byr" name="transferToMixForm" />
                 <html:hidden property="nmeIdn" name="transferToMixForm" />
                </td>
                 <td> <b>Mix With :</b> </span></td><td>
                  <html:select property="value(mixStkIdn)" name="transferToMixForm" >
                
                   <html:optionsCollection property="mxPktList" name="transferToMixForm"  value="pktIdn" label="vnm" />
                  </html:select>
                </td>
                </tr></table>  </td></tr>
  
           
       
       
             
           
           
 
  <tr><td>
            <label class="pgTtl">Sale Packets</label>
            <%
                ArrayList prps = (info.getValue("MDL_MEMO_RTRN") == null)?new ArrayList():(ArrayList)info.getValue("MDL_MEMO_RTRN");
             %>  </td></tr>
             <tr><td>
            <table class="grid1">
                <tr>
                    <th>Sr</th>
                    <th>Select <input  type="checkbox" id="all" name="all" onclick="ALLCheckBox('all','MIX_')" /> </th>
                    <th>sale Id</th>
                      <th>Packet Code</th>
                     <%for(int j=0; j < prps.size(); j++) {%>
                        <th><%=(String)prps.get(j)%></th>
                    <%}%>
                    <th>RapRte</th>
                    <th>Dis</th>
                    <th>Prc / Crt </th>
                    <th>ByrDis</th>
                    <th>Quot</th>
                    
                </tr>
            <%
             int count =0 ;
              
                for(int i=0; i < pkts.size(); i++) {%>
                <tr>
                <td><%=(i+1)%></td>
               
                <%    count=i+1;
                    PktDtl pkt = (PktDtl)pkts.get(i);
                    long pktIdn = pkt.getPktIdn();
                    String cbPrp = "value(MIX_" + pktIdn + ")";
                    String sttPrp = "value(stt_" + pktIdn + ")" ;
                    String invPrp = "value(INV_" + pktIdn + ")" ;
                    String rdISId = "MIX_"+pktIdn;
                    String rdSLId = "SL_"+count;
                    String rdRTId = "RT_"+count;
                    String rdInvId = "INV_"+count;
                    String onClickAp = "SetCalculationDlv('"+pktIdn+"','DLV','"+pkt.getSaleId()+"')";
                    String onClickSl = "SetCalculationDlv('"+pktIdn+"','SL','"+pkt.getSaleId()+"')";
                    String onClickRt = "SetCalculationDlv('"+pktIdn+"','RT','"+pkt.getSaleId()+"')";
             %>
              <td><html:checkbox property="<%=cbPrp%>"  name="transferToMixForm" styleId ="<%=rdISId%>" value="no" /></td>
                <td><%=pkt.getSaleId()%></td>
                 <td><%=pkt.getVnm()%></td>
                <%for(int j=0; j < prps.size(); j++) {%>
                    <td><%=util.nvl(pkt.getValue((String)prps.get(j)))%></td>
                <%}
                %>
                <td><%=pkt.getRapRte()%></td>
                <td><%=pkt.getDis()%></td>
                <td><%=pkt.getRte()%></td>
                <td><%=pkt.getByrDis()%></td>
                <td><%=pkt.getMemoQuot()%></td>
                  </tr>
              <%  
                }
            %>
              <input type="hidden" id="rdCount" value="<%=count%>" />
            </table></td></tr>
             <tr><td>
            <p>
                <html:submit property="submit" value="Save" styleClass="submit"/>&nbsp;
               
                         
            </p></td></tr>
            
            <%}else{%>
            <tr><td>Sorry no result found</td></tr>
           <% }}%></html:form>
  </table>
</td></tr>
</table>
  </td>
  </tr>
  
  
  </table></body></html>