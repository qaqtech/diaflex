<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
 
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import="java.util.ArrayList,java.util.HashMap,java.sql.ResultSet,ft.com.*, java.util.Date, java.util.ArrayList, ft.com.dao.*"%>
<jsp:useBean id="util" class="ft.com.JspUtil" scope="page" />
<jsp:useBean id="info" class="ft.com.InfoMgr" scope="session" />
<%@taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>
<%@taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>


<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>MemoReturn</title>
    <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/bse.css?v=<%=info.getJsVersion()%>"/>
      <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/style.css?v=<%=info.getJsVersion()%>"/>
       <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/bse.js?v=<%=info.getJsVersion()%> " > </script>
  </head>
<%
String view = "";
        String logId=String.valueOf(info.getLogId());
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
  <img src="../images/line.jpg" border="0" /></td><td valign="middle"> <span class="pgHed">Memo Return</span> </td>
</tr></table>
  </td>
  </tr>
  
  <tr><td valign="top" class="tdLayout">
  <table><tr><td>
  
  <bean:parameter id="reqIdn" name="memoIdn" value="0"/>
  <%
    String pgTtl = " Memo Return " ;
    view = util.nvl(request.getParameter("view"));
    String lMemoIdn = "", viewAll="";
    
    viewAll = util.nvl((String)request.getAttribute("viewAll"));
    lMemoIdn = util.nvl((String)request.getAttribute("lMemoIdn"));
    if(lMemoIdn.length() == 0)
      lMemoIdn = reqIdn ;
    /*
    if(util.nvl((String)request.getAttribute("viewAll")).equalsIgnoreCase("yes")) {
      lMemoIdn = (String)request.getAttribute("lMemoIdn");
    }
    */
    /*
    if(reqIdn=="0")
      reqIdn = request.getParameter("memoId");
    */  
    if(view.equals("Y"))
        pgTtl += " Id : " + lMemoIdn ;
        
  %>
 
    <html:form action="/marketing/memoReturn.do?method=load"  method="POST">
        <table class="grid1">
        <tr><th colspan="2">Memo Search </th></tr>
            <tr>
                <td>Memo Id</td><td><html:text property="memoIdn"/></td>
            </tr>
            <tr>
                <td>Type</td>
                <td><html:radio property="view" value="NORMAL"/>&nbsp;Normal
                   
                </td>    
            </tr>
            <tr>
            <td>Packets. </td><td>
            <html:textarea property="value(vnmLst)" name="memoReturnForm" />
            </td>
            </tr>
        </table>
        <p><html:submit property="submit" value="View" styleClass="submit"/> &nbsp;<html:submit property="submit" value="View All" styleClass="submit"/></p>
    </html:form></td></tr>
   
    <%
    if(view.equals("Y")) {
        String formAction = "/marketing/memoReturn.do?method=save&memoIdn="+ lMemoIdn ; 
    %>
        <html:form action="<%=formAction%>" method="POST">
        <tr><td>
            <table class="grid1">
                <tr><th>Memo Id</th>
                    <th>Date</th>
                    <th>Typ</th>
                    <th>Buyer</th>
                    <th>Qty</th>
                    <th>Cts</th>
                    <th>Value</th>
                </tr>
                <tr>
                    <td>
                    <html:text property="oldMemoIdn" name="memoReturnForm" readonly="true"/>
                    </td> 
                    <td><html:text property="dte" name="memoReturnForm"  readonly="true"/>
                    </td> 
                    <td><html:text property="typ" name="memoReturnForm" readonly="true"/></td> 
                    <td><html:text property="byr" name="memoReturnForm" readonly="true"/></td> 
                    <td><html:text property="qty" name="memoReturnForm" readonly="true"/></td> 
                    <td><html:text property="cts" name="memoReturnForm" readonly="true"/></td> 
                    <td><html:text property="vlu" name="memoReturnForm" readonly="true"/>
                    <html:hidden property="nmeIdn" name="memoReturnForm" />
                      <html:hidden property="relIdn" name="memoReturnForm" />
                    </td> 
              
                </tr>
            </table></td></tr>
            <tr><td>
            <label class="pgTtl">Memo Packets</label>
            <%
                ArrayList prps = (info.getValue("MDL_MEMO_RTRN") == null)?new ArrayList():(ArrayList)info.getValue("MDL_MEMO_RTRN");
             %>  
            <table class="grid1">
                <tr>
                    <th>Sr</th>
                    <th>Packet No.</th>
                     <%for(int j=0; j < prps.size(); j++) {%>
                        <th><%=(String)prps.get(j)%></th>
                    <%}%>
                    <th>RapRte</th>
                    <th>Dis</th>
                    <th>Prc / Crt </th>
                    <th>ByrDis</th>
                    <th>Quot</th>
                    <th>Issue</th>
                    <th>Return</th>
                    <th>APprove</th>
                </tr>
            <%
                ArrayList pkts = (info.getValue("RTRN_PKTS") == null)?new ArrayList():(ArrayList)info.getValue("RTRN_PKTS"); 
                //(info.getValue(reqIdn+ "_PKTS") == null)?new ArrayList():(ArrayList)info.getValue(reqIdn+ "_PKTS");
               int count = 0;
                for(int i=0; i < pkts.size(); i++) {%>
                <tr>
                <td><%=(i+1)%></td>
                <%    
                   count=i+1;
                    PktDtl pkt = (PktDtl)pkts.get(i);
                    long pktIdn = pkt.getPktIdn();
                    String cbPrp = "value(upd_" + pktIdn + ")";
                    String sttPrp = "value(stt_" + pktIdn + ")" ;
                    String rdSTId = "ST_"+count;
                    String rdRTId = "RT_"+count;
                    String rdAPId = "AP_"+count;
                   
                %>
                
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
                <td><html:radio property="<%=sttPrp%>" styleId="<%=rdSTId%>"  value="IS"/></td>
                <td><html:radio property="<%=sttPrp%>" styleId="<%=rdRTId%>" value="RT"/></td>
                <td><html:radio property="<%=sttPrp%>" styleId="<%=rdAPId%>" value="AP"/></td>
                </tr>
              <%  
                }
                
            %>
            <input type="hidden" id="rdCount" value="<%=count%>" />
            
            </table>
            <p>
           
                <html:submit property="submit" value="Save" styleClass="submit"/>&nbsp;
                <html:button property="fullReturn" onclick="SelectRD('RT')" value="Full Return" styleClass="submit"/>&nbsp;
                <html:button property="fullApprove" onclick="SelectRD('AP')" value="Full Approve" styleClass="submit"/>&nbsp;
            </p></td></tr>
        </html:form>
    <%}
    %>
    </table>
    </td></tr></table>
  </body>
</html>