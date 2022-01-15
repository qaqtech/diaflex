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
   String logId=String.valueOf(info.getLogId());
   String onfocus="cook('"+logId+"')";
     HashMap pktDtl = (HashMap)request.getAttribute("purPrpDtl");
 ArrayList purdtlidnLst = (ArrayList)request.getAttribute("purdtlidnLst");
  String purIdn = util.nvl(request.getParameter("purIdn"));
   String purDtlIdn =  util.nvl(request.getParameter("purDtlIdn"));
    String rughForm = util.nvl(request.getParameter("rughForm"),"N");/*  */
    String lotdsc =  util.nvl(request.getParameter("lotDsc"));
  String ttlcts = util.nvl(request.getParameter("ttlcts"),"0");
  String cts = util.nvl((String)pktDtl.get("cts"),"0");
  String remCts = "";
  double remCtsd=0.0;
  if(!cts.equals("") && !ttlcts.equals("")){
     remCtsd= Double.parseDouble(ttlcts)-Double.parseDouble(cts);
    remCts = String.valueOf(remCtsd);
  }
  String lstCnt = util.nvl((String)request.getAttribute("lstCnt"),"0");
    int lstCntInt = Integer.parseInt(lstCnt)+1;
    ArrayList asViewPrp=null;
   if(rughForm.equals("Y"))
   asViewPrp= (session.getAttribute("RUGH_MIX_VW") == null)?new ArrayList():(ArrayList)session.getAttribute("RUGH_MIX_VW");
     else
   asViewPrp= (session.getAttribute("PUR_MIX_VW") == null)?new ArrayList():(ArrayList)session.getAttribute("PUR_MIX_VW");
 String msg = util.nvl((String)request.getAttribute("msg"));
 
 String vnm=lotdsc+"."+lstCntInt;
  %>
 <body onfocus="<%=onfocus%>" >
 <div id="backgroundPopup"></div>
<div id="popupContactSave" align="center" >
<img src="../images/loading_flame.gif" height="150px" width="150px" border="0" />
</div>
 <table cellpadding="4" cellspacing="4">
 <tr><td>
 <%

 
 if(!msg.equals("")){
 %>
  <tr><td><label class="redLabel"><%=msg%></label> </td> </tr>
 <%}%>
 <html:form action="purchase/purchaseDtlAction.do?method=SaveSplitStone" method="post" onsubmit="return loading()" >
<input type="hidden" id="reqUrl" name="reqUrl" value="<%=info.getReqUrl()%>"/>
    <input type="hidden" value="<%=purIdn%>" name="purIdn" id="purIdn" />
    <input type="hidden" value="<%=purDtlIdn%>" name="purDtlIdn" id="purDtlIdn" />
    <input type="hidden" value="<%=rughForm%>" name="rughForm" id="rughForm" />
    <input type="hidden" value="<%=lotdsc%>" name="lotDsc" id="lotDsc" />
     <input type="hidden" value="<%=ttlcts%>" name="ttlcts" id="ttlcts" />
   <table>
   <tr><td> <table cellpadding="2" cellspacing="3"><tr>
   <td> <label class="redLabel"> Total Carat :</label> &nbsp; <label class="redLabel"><%=ttlcts%></label> </td>
     <td> <label class="redLabel"> Split Carat :</label> &nbsp; <label class="redLabel"><%=cts%></label> </td>
       <td> <label class="redLabel"> Remaining Carat :</label> &nbsp; <label class="redLabel" id="remCts"><%=remCts%></label> </td>
   </tr></table>
   </td> </tr>
   <%if(remCtsd > 0){%>
   <tr><td>
   
   <table class="grid1" cellspacing="1" cellpadding="1"><tr>
    <th>Packet Code</th>
   <th>Qty</th>
   <th>Carat</th>
   </tr>
   <tr>
    <td><input type="text" name="vnm" id="vnm" size="10" value="<%=vnm%>" /></td>
  <td><input type="text" name="qty" id="qty" size="5" value="1" /></td>
  <%
 String calSplitAvlCts = "calSplitAvlCts('cts',"+remCts+")";
  %>
  <td><input type="text" name="cts" id="cts" size="5" onchange="<%=calSplitAvlCts%>"/></td>
   </tr>
   
   </table>
  
   </td> </tr>
   <tr><td> <input type="submit" value="Add" class="submit" name="Add" />  </td></tr>
    <%}%>
     <%if(purdtlidnLst!=null && purdtlidnLst.size()>0){%>
    <tr><td>
  
    <table cellpadding="2" cellspacing="2">
    <tr>
    <td>Total :-</td><td>Cts</td>
    <td align="right"><%=util.nvl((String)pktDtl.get("cts"))%></td>
     <td>Avg Rte</td><td align="right"><%=util.nvl((String)pktDtl.get("avgrte"))%></td>
    <td>Amount</td><td align="right"><%=util.nvl((String)pktDtl.get("vlu"))%></td>
    </tr>
   </table></td>
    </tr>
    <tr><td>
     <div class="memo">
 <table class="Orange" cellspacing="1" cellpadding="1">
    <tr>
    <%for(int i=0;i<asViewPrp.size();i++){
    String lprp = (String)asViewPrp.get(i);
    %>
    <th class="Orangeth"><%=lprp%></th>
    <%}%>
    </tr>
    <%for(int j=0;j<purdtlidnLst.size();j++){
    String prDtlIdn=(String)purdtlidnLst.get(j);
    %>
    <tr>
    <%for(int i=0;i<asViewPrp.size();i++){
    String lprp = (String)asViewPrp.get(i);
    String val = util.nvl((String)pktDtl.get(prDtlIdn+"_"+lprp));
    %>
    <td><%=val%></td>
    <%}%>
    </tr>
    <%}%>
    </table></div>
    </td></tr>
    <%}%>
    </table>
   

</html:form>
</td></tr>
 
 </table>
    
    
    
    </body></html>
    
    