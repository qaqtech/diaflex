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
  
<%
HashMap pktDtl = (HashMap)request.getAttribute("pktDtl");
ArrayList crtList =(ArrayList)request.getAttribute("crtList");
String loadSeq = util.nvl((String)request.getAttribute("seqNo"));
ArrayList tlyList = new ArrayList();
tlyList.add("IS");
tlyList.add("RT");
  if(crtList!=null && crtList.size() > 0){%>
  <table class="grid1">
  <tr><th colspan="8"><%=util.nvl((String)pktDtl.get("GRP"))%> Tally</th></tr>
  <tr><th>Dsc</th><th>Status</th><th colspan="2">Total </th><th colspan="2">Issue </th><th colspan="2">Return </th></tr>
  <tr><td></td><td></td><td><b>Qty </b></td><td><b>Cts</b></td><td><b>Qty</b></td><td><b>Cts</b></td><td><b>Qty</b></td><td><b>Cts</b></td></tr>
  <%
  for(int i=0;i< crtList.size();i++){
  String mstkStt = (String)crtList.get(i);
  String issQ = util.nvl((String)pktDtl.get(mstkStt+"_IS_Q"));
  String issC = util.nvl((String)pktDtl.get(mstkStt+"_IS_C"));
  String rtQ = util.nvl((String)pktDtl.get(mstkStt+"_RT_Q"));
  String rtC = util.nvl((String)pktDtl.get(mstkStt+"_RT_C"));
  String dsc = util.nvl((String)pktDtl.get(mstkStt));
  String  ttlQ ="";
  if(issQ.length()>0 && rtQ.length()>0){
  ttlQ = String.valueOf((Integer.parseInt(issQ) +Integer.parseInt(rtQ)));
  }else if(issQ.length()>0){
   ttlQ = String.valueOf(Integer.parseInt(issQ));
  }else if(rtQ.length()>0){
   ttlQ = String.valueOf(Integer.parseInt(rtQ));
  }
  String  ttlC ="";
  if(issC.length()>0 && rtC.length()>0){
  ttlC = String.valueOf(Float.valueOf(issC) + Float.valueOf(rtC));
  }else if(issC.length()>0){
   ttlC = String.valueOf(Float.valueOf(issC));
  }else if(rtC.length()>0){
    ttlC = String.valueOf(Float.valueOf(rtC));
  }
  %>
   <tr><td nowrap="nowrap"><%=dsc%></td> <td><b> <%=mstkStt%></b></td>
   <td><A href="<%=info.getReqUrl()%>/marketing/stockTallyhk.do?method=PktDtl&mstkstt=<%=mstkStt%>&issTyp=ALL&loadSeq=<%=loadSeq%>" target="_blank"><%=ttlQ%> </a></td>
   <td><%=ttlC%></td>
    <td><A href="<%=info.getReqUrl()%>/marketing/stockTallyhk.do?method=PktDtl&mstkstt=<%=mstkStt%>&issTyp=IS&loadSeq=<%=loadSeq%>" target="_blank"><%=util.nvl((String)pktDtl.get(mstkStt+"_IS_Q"))%></a></td>
   <td><%=util.nvl((String)pktDtl.get(mstkStt+"_IS_C"))%></td>
   <td><A href="<%=info.getReqUrl()%>/marketing/stockTallyhk.do?method=PktDtl&mstkstt=<%=mstkStt%>&issTyp=RT&loadSeq=<%=loadSeq%>" target="_blank"><%=util.nvl((String)pktDtl.get(mstkStt+"_RT_Q"))%></a></td>
   <td><%=util.nvl((String)pktDtl.get(mstkStt+"_RT_C"))%></td>
  
  </tr>
  <%}%>
  </table>
  <%}%>
  
  
  </body>
</html>