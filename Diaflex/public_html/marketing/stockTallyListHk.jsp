<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
 
<%@ page errorPage="../error_page.jsp" %>
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import="java.util.ArrayList,java.util.HashMap,java.sql.ResultSet,ft.com.*, java.util.Date, java.util.ArrayList, ft.com.dao.*"%>
<jsp:useBean id="util" class="ft.com.JspUtil" scope="page" />
<jsp:useBean id="info" class="ft.com.InfoMgr" scope="session" />
<%@taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>
<%@taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>stockTallyList</title>
   <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/bse.css?v=<%=info.getJsVersion()%>"/>
      <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/style.css?v=<%=info.getJsVersion()%>"/>
      <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/memoScript.js?v=<%=info.getJsVersion()%> " > </script>
                <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/bse.js?v=<%=info.getJsVersion()%> " > </script>
        </head>
        <%String logId=String.valueOf(info.getLogId());
        String onfocus="cook('"+logId+"')";
        %>
 <body onfocus="<%=onfocus%>">
  <table>
  <tr><td valign="top" class="tdLayout">
  
 
  <table>
 
  
   <%
   int sr = 0;
   String view = util.nvl((String)request.getAttribute("view"));
   String ctwt=null;
 
   ArrayList stockList = (ArrayList)request.getAttribute("stockList");
   
    session.setAttribute("pktList", stockList);
   
   if(stockList!=null && stockList.size()>0){
    ArrayList vwPrpLst = (ArrayList)session.getAttribute("rfidPrpLst");
     ArrayList itemHdr = new ArrayList();
    ArrayList pktList = new ArrayList();
    HashMap pktPrpMap = new HashMap();
  
  
    HashMap mprp = info.getMprp();
    itemHdr.add("currentstt");
    itemHdr.add("iss_stt");
   %>
  <tr><td>Create Excel <img src="../images/ico_file_excel.png" border="0" onclick="goTo('<%=info.getReqUrl()%>/marketing/stockTallyhk.do?method=createXLstocktally')"/>
</td>
   </tr>
   <tr><td>
   <table class="dataTable">
   <tr> <th>Sr</th>
    <th>Live Status</th>
    <th>Opening Status</th>
        <th>Detail</th>
        <%if(view.equals("A")){ %>
         <th>Buyer</th>
          <th>Memo Id</th>
          <%itemHdr.add("byr");
           itemHdr.add("memoid");
          }%>
         <th>Packet No.</th>
     
        <%itemHdr.add("vnm");
        for(int j=0; j < vwPrpLst.size(); j++ ){
    String prp = (String)vwPrpLst.get(j);
     String hdr = (String)mprp.get(prp);
    String prpDsc = (String)mprp.get(prp+"D");
    if(hdr == null) {
        hdr = prp;
       }  
    itemHdr.add(prp);
%>
<th title="<%=prpDsc%>"><%=hdr%></th>

<%}%>

<th> Customer</th>
<%itemHdr.add("Customer");%>
<th> Memo_type</th>
<%itemHdr.add("Memo_type");%>
<th>Memo_Date</th>
<%itemHdr.add("Memo_Date");%>
<th> Cabin</th>
<%itemHdr.add("Cabin");%>
</tr>
 <%
 
 for(int i=0; i < stockList.size(); i++ ){
 HashMap stockPkt = (HashMap)stockList.get(i);
 String stkIdn = (String)stockPkt.get("stk_idn");
 
 sr = i+1;
 String checkFldId = "CHK_"+sr;
 String sttPrp = "value("+stkIdn+ ")";
 int vmSize = vwPrpLst.size();
 vmSize=vmSize+4;
  String color = util.nvl((String)stockPkt.get("class"));
 %>
<tr style="<%=color%>">

<td><%=sr%></td>


<td><%=stockPkt.get("currentstt")%></td>
<td><%=stockPkt.get("iss_stt")%></td>
<td><img src="../images/plus.png" border="0" onclick="getHoldByr('','<%=stkIdn%>','LST','')"/></td>
 <%if(view.equals("A")){ %>
<td nowrap="nowrap"><%=stockPkt.get("byr")%></td>
<td nowrap="nowrap"><%=stockPkt.get("memoid")%></td>
<%}%>
<td><%=stockPkt.get("vnm")%></td>
<%for(int j=0; j < vwPrpLst.size(); j++ ){
    String prp = (String)vwPrpLst.get(j);
   
    %>
    <td><%=stockPkt.get(prp)%></td>
    <%
    
}%>
<td><%=stockPkt.get("customer")%></td>
<td><%=stockPkt.get("memo_type")%></td>
<td><%=stockPkt.get("date")%></td>
<td><%=stockPkt.get("cabin")%></td>
</tr>
<tr>
<td colspan="<%=vmSize%>" nowrap="nowrap" id="TD_<%=stkIdn%>" style="display:none" align="left">
<span id="BYR_<%=stkIdn%>" > </span>

</td>
</tr>
   <%}%>
   </table>
    <input type="hidden" name="count" id="count" value="<%=sr%>" />
   </td></tr>
 
  
   <%
                
                 session.setAttribute("itemHdr", itemHdr);
   
   }%>
  
  
  
  
  
  </table>

  
   </td></tr>
  </table>
  
  </body>
</html>