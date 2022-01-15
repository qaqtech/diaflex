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
    <title>Offer </title>
    <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/bse.css?v=<%=info.getJsVersion()%>"/>
      <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/style.css?v=<%=info.getJsVersion()%>"/>
      <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/memoScript.js?v=<%=info.getJsVersion()%> " > </script>
        <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/bse.js?v=<%=info.getJsVersion()%> " > </script>
          <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/priceChange.js?v=<%=info.getJsVersion()%> " > </script>
           <script language="javaScript" type="text/javascript" src="<%=info.getReqUrl()%>/calendar/calendar.js"></script>
   <link href="<%=info.getReqUrl()%>/calendar/calendar.css" rel="stylesheet" type="text/css" />

        </head>
        <%String logId=String.valueOf(info.getLogId());
        String onfocus="cook('"+logId+"')";
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
  <img src="../images/line.jpg" border="0" /></td><td valign="middle"> <span class="pgHed">Offer</span> </td>
</tr></table></td></tr>
<tr>
<td> 
 <%
 if(request.getAttribute("msg")!=null){%>
   <tr>
  <td valign="top" class="tdLayout">
  <span class="redLabel"><%=request.getAttribute("msg")%> </span>
  </td></tr>
  <%}%>
<tr>
  <td valign="top" class="tdLayout">
 <%
 ArrayList msgList = (ArrayList)request.getAttribute("msgList");
 if(msgList!=null && msgList.size()>0){%>
 <table cellpadding="0" cellspacing="0">
 <%for(int k=0 ; k<msgList.size(); k++){
 %>
  <tr><td>
  <span class="redLabel"><%=msgList.get(k)%> </span>
 </td></tr>
  <%}%></table>
  <%}%>
   </td></tr>
   <tr>
  <td valign="top" class="tdLayout">
 <html:form action="marketing/offerPrice.do?method=pktList">
  <table class="grid1">
  <tr><th colspan="2">Offer Form</th></tr>
   <tr><td>Memo Id</td><td><html:text property="memoIdn" styleId="memoId" name="offerPriceForm"  /> </td></tr>
   
  
  <tr><td colspan="2" align="center"><button type="submit" class="submit"   name="verify" >Fetch</button>&nbsp;&nbsp; </td></tr>
  </table>
 </html:form>
  </td></tr>
  <tr><td valign="top" class="tdLayout">
  <%if(request.getAttribute("msg")==null){%>
  <html:form action="marketing/offerPrice.do?method=save" method="post" onsubmit="return confirmChanges()" >
  <logic:present property="view" name="offerPriceForm" >
   
      
        <table>
        <tr><td>
            <table >
                <tr>
                    <th>Buyer</th>
                    <th>Qty</th>
                    <th>Cts</th>
                    <th>Rap Value</th>
                    <th>Exchange Rate</th>
                
                </tr>
                <tr>
                   
                   
                
                    <td><html:text property="byr"  name="offerPriceForm" readonly="true"/></td> 
                    <td><html:text property="qty" size="5" name="offerPriceForm" readonly="true"/></td> 
                    <td><html:text property="cts" styleId="glbl_cts" size="5" name="offerPriceForm" readonly="true"/></td> 
                    <td><html:text property="rapVlu" styleId="glbl_rap_vlu" size="5" name="offerPriceForm" readonly="true"/>
                     <html:hidden property="memoIdn" name="offerPriceForm" />
                    <html:hidden property="nmeIdn" name="offerPriceForm" />
                      <html:hidden property="relIdn" name="offerPriceForm" />
                       
                    </td> 
                     <td><html:text property="value(exhRte)"  styleId="exhRte" name="offerPriceForm" readonly="true"/></td>
              
                </tr>
            </table></td></tr>
            
            <tr><td>
            <table><tr><td>
            <table>
            <tr><td></td><td>Value</td><td>Discount</td><td>Avg</td></tr>
            <tr><td>Old Value</td>
            <td><html:text property="vlu" styleId="old_vlu" size="5" name="offerPriceForm" readonly="true"/></td>
            <td><html:text property="avgDis" styleId="old_dis" size="5" name="offerPriceForm" readonly="true"/></td>
            <td><html:text property="avg" styleId="old_avg" size="5" name="offerPriceForm" readonly="true"/></td>
            </tr>
            <tr><td>New Value</td>
              <td><html:text property="vlu" styleId="new_vlu" size="5" name="offerPriceForm" readonly="true"/></td>
            <td><html:text property="avgDis" styleId="new_dis" size="5" name="offerPriceForm" readonly="true"/></td>
            <td><html:text property="avg" styleId="new_avg" size="5" name="offerPriceForm" readonly="true"/></td>
          
            </tr>
            </table>
            <td>
            <table>
            <tr><td></td><td>Value</td><td>Discount</td><td>Avg</td></tr>
            <tr><td>Old Selected Value</td>
            <td><html:text property="value(vlu)" styleId="old_vlusc" size="5" name="offerPriceForm" readonly="true"/></td>
            <td><html:text property="value(avgDis)" styleId="old_dissc" size="5" name="offerPriceForm" readonly="true"/></td>
            <td><html:text property="value(avg)" styleId="old_avgsc" size="5" name="offerPriceForm" readonly="true"/></td>
            </tr>
            <tr><td>New Selected Value</td>
              <td><html:text property="value(vlu)" styleId="new_vlusc" size="5" name="offerPriceForm" readonly="true"/></td>
            <td><html:text property="value(avgDis)" styleId="new_dissc" size="5" name="offerPriceForm" readonly="true"/></td>
            <td><html:text property="value(avg)" styleId="new_avgsc" size="5" name="offerPriceForm" readonly="true"/></td>
          
            </tr>
            </table>
            </td></tr></table>
            </td>
            </tr>
            
            <tr><td>
            <table>
            <tr><td>Update Price By </td><td> <html:select property="value(val)" styleId="glbl_typ"  name="offerPriceForm" >
  
 
       <html:option value="AVG_PRC">Avg Prc</html:option>
	<html:option value="AVG_DIS">Avg Dis</html:option>
	<html:option value="PER_CRT_USD">Per Crt USD</html:option>
	<html:option value="PER_CRT_PCT">Per Crt %</html:option>
        
	<html:option value="PER_STONE">Per Stone</html:option>
         <html:option value="PER_STONE_DIS">Per Stone Dis</html:option>
          <html:option value="FIX"> Fixed price</html:option>
  </html:select></td> <td><html:text property="value(diff)" styleId="glbl_chng" size="10" name="offerPriceForm"/> </td>
  <td><button type="button" class="submit" onClick="appVerify(0)" >Verify </button>
</td>
<td><button type="button" class="submit" onClick="appVerifySelect(0)" >Verify Selected</button>
</td>
  </tr>
            </table>
           </td> </tr>
           <tr><td><html:submit value="Confirm" onclick="" styleClass="submit" /> </td></tr>
            <tr><td>
            <label class="pgTtl">Memo Packets</label>
            <%
            ArrayList prpDspBlocked = info.getPageblockList();
                ArrayList prps = (ArrayList)session.getAttribute("prpList");
             %>  
            <table class="grid1">
                <tr>
                    <th>Sr</th>
                    <th>Check <input type="checkbox" name="chAll" id="chAll" onclick="checkedALL()" /> </th>
                    <th>Packet No.</th>
                     <%for(int j=0; j < prps.size(); j++) {
                     String lprp=(String)prps.get(j);
                     if(prpDspBlocked.contains(lprp)){
                        }else{%>
                        <th><%=lprp%></th>
                    <%}}%>
                    <th>RapRte</th>
                    <th>Dis</th>
                    <th>Prc / Crt </th>
                    <th>ByrDis</th>
                    <th>Quot</th>
                    <th>Valid Till</th>
                    <th colspan="2">Update Price By</th>
                    
                    <th>New Price</th>
                     <logic:greaterThan property="value(exhRte)"  value="1" name="offerPriceForm" >
                    <th>New Price $</th>
                    </logic:greaterThan>
                    <th>New Discout</th>
                    <th>Rap Val</th>
                    <th>Value</th>
                </tr>
            <%
                ArrayList pkts = (ArrayList)session.getAttribute("PKTS");
                //(info.getValue(reqIdn+ "_PKTS") == null)?new ArrayList():(ArrayList)info.getValue(reqIdn+ "_PKTS");
               int count = 0;
                for(int i=0; i < pkts.size(); i++) {%>
                <tr>
                <td><%=(i+1)%></td>
                <%    
                   count=i+1;
                    PktDtl pkt = (PktDtl)pkts.get(i);
                    long pktIdn = pkt.getPktIdn();
                     String typId = count+"_typ";
                    String diffId = count+"_chng";
                    String finalPriceId = count+"_fnl";
                    String finalDisId = count+"_fnl_dis";
                    String rapRteId = count+"_rap";
                    String rapDisId = count+"_dis";
                    String quotId = count+"_quot";
                    String ctsId = count+"_cts";
                    String newPriceIdINR = count+"_fnl$";
                    String rapValIdn = count+"_rapVal";
                    String valIDN = count+"_Val";
                    String onChange = "appVerify("+count+")";
                    String rapValId = "rapVal_"+String.valueOf(pktIdn);
                    String newPriceId = "nwprice_"+String.valueOf(pktIdn);
                    String valId = "val_"+String.valueOf(pktIdn);
                    String newDisId = "nwdis_"+String.valueOf(pktIdn);
                    String typVal ="value(typ_"+String.valueOf(pktIdn)+")";  
                    String diff = "value(chng_"+String.valueOf(pktIdn)+")";
                    String newPrice = "value("+newPriceId+")";
                    String newDis = "value("+newDisId+")";
                    String rapVal = "value("+rapValId+")";
                    String valFld = "value("+valId+")";
                    String newPriceINR = "value(nwprice$_"+String.valueOf(pktIdn)+")";
                     String check = "value("+String.valueOf(pktIdn)+")";
                   
                    String dteFld = "value(dte_"+String.valueOf(pktIdn)+")"; 
                    String dteFldID = "dte_"+String.valueOf(pktIdn);
                    String checkID = "ch_"+i;
                    
                %>
                <td><html:checkbox  property="<%=check%>" name="offerPriceForm" styleId="<%=checkID%>" value="Yes"/> </td>
                <td><%=pkt.getVnm()%></td>
                <%for(int j=0; j < prps.size(); j++) {
                String lprp=(String)prps.get(j);
                if(prpDspBlocked.contains(lprp)){
                }else{
                String val = util.nvl(pkt.getValue(lprp));
                %>
                    <td><%=val%>
                    <%if(j==1){%>
                    <html:hidden value="<%=val%>" property="value(cts)" styleId="<%=ctsId%>" />
                    <%}%>
                    </td>
                <%}}
                %>
                <td><%=pkt.getRapRte()%><html:hidden value="<%=pkt.getRapRte()%>" property="value(rap)" styleId="<%=rapRteId%>" /> </td>
                <td><%=pkt.getDis()%><html:hidden value="<%=pkt.getByrDis()%>"  property="value(dis)" styleId="<%=rapDisId%>" /></td>
                <td><%=pkt.getRte()%><html:hidden value="<%=pkt.getMemoQuot()%>"  property="value(rte)" styleId="<%=quotId%>" /></td>
                <td><%=pkt.getByrDis()%></td>
                <td><%=pkt.getMemoQuot()%></td>
                <td> <div class="float">   <html:text property="<%=dteFld%>"  styleId="<%=dteFldID%>" /></div>
            <div class="float">     <a href="#"  onClick="setYears(1900, <%=info.getCurrentYear()%>);showCalender(this, '<%=dteFldID%>');">
                <img src="<%=info.getReqUrl()%>/images/calender.png"></a></div> </td>
                <td> 
                 
                <html:select property="<%=typVal%>" styleId="<%=typId%>" onchange="<%=onChange%>"  name="offerPriceForm" >
 <html:option value="AVG_PRC">Avg Prc</html:option>
	<html:option value="AVG_DIS">Avg Dis</html:option>
	<html:option value="PER_CRT_USD">Per Crt USD</html:option>
	<html:option value="PER_CRT_PCT">Per Crt %</html:option>
        <html:option value="PER_STONE">Per Stone</html:option>
         <html:option value="PER_STONE_DIS">Per Stone Dis</html:option>
         <html:option value="FIX"> Fixed price</html:option>
  </html:select> </td><td><html:text property="<%=diff%>" styleId="<%=diffId%>" onchange="<%=onChange%>" size="10" name="offerPriceForm"/>
                </td>
              <td><html:text property="<%=newPrice%>" styleId="<%=finalPriceId%>" size="10" name="offerPriceForm"/></td>
               <logic:greaterThan property="value(exhRte)"  value="1" name="offerPriceForm" >
                 <td><html:text property="<%=newPriceINR%>" styleId="<%=newPriceIdINR%>"  size="10" name="offerPriceForm"/></td>
             </logic:greaterThan>
              <td><html:text property="<%=newDis%>" styleId="<%=finalDisId%>"  size="10" name="offerPriceForm"/></td>
              <td><html:text property="<%=rapVal%>" styleId="<%=rapValIdn%>" size="10" name="offerPriceForm"/></td>
              <td><html:text property="<%=valFld%>" styleId="<%=valIDN%>" size="10" name="offerPriceForm"/></td>
              
                </tr>
              <%  
                }
            
            %>
              <input type="hidden" name="ttl_cnt" id="ttl_cnt" value="<%=count%>" />
            
            </table>
          
           </td></tr>
           
           </table>
       </logic:present>
       </html:form>
       <%}%>
</td></tr>

</table>
 </td></tr>
 <tr>
    <td><jsp:include page="/footer.jsp" /> </td>
    </tr>
 </table>
  <%@include file="../calendar.jsp"%>
  </body>
</html>