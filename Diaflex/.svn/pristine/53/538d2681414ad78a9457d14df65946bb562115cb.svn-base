<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
 
<%@ page errorPage="../error_page.jsp" %>
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import="java.util.ArrayList,java.util.ArrayList, java.util.Iterator,java.io.*, java.sql.Date,java.util.HashMap, ft.com.marketing.PacketLookupForm" %>
<jsp:useBean id="util" class="ft.com.JspUtil" scope="page" />
<jsp:useBean id="info" class="ft.com.InfoMgr" scope="session" />
<%@taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>
<%@taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>Compare</title>
<script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/bse.js?v=<%=info.getJsVersion()%> " > </script>
<link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/bse.css?v=<%=info.getJsVersion()%>"/>
<link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/filter.css"/>
<script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/ajax.js?v=<%=info.getJsVersion()%> " > </script>
<link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/style.css?v=<%=info.getJsVersion()%>"/>
<link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/general.css?v=<%=info.getJsVersion()%>"/>
<script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/memoScript.js?v=<%=info.getJsVersion()%> " > </script>
<script type="text/javascript" src="<%=info.getReqUrl()%>/PopScripts/popup.js?v=<%=info.getJsVersion()%> " > </script>
<script src="<%=info.getReqUrl()%>/PopScripts/jquery-1.2.6.min.js" type="text/javascript"></script>
<script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/tableFilter/actb.js " > </script>
<script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/tableFilter/tablefilter.js " > </script>
 <link rel="stylesheet" media="screen" type="text/css" href="css/zoomimage.css" />
    
    <script type="text/javascript" src="scripts/jquery.js"></script>
    <script type="text/javascript" src="scripts/eye.js"></script>
    <script type="text/javascript" src="scripts/utils.js"></script>
    <script type="text/javascript" src="scripts/zoomimage.js"></script>
    <script type="text/javascript" src="scripts/layout.js"></script>
        </head>
        <%String logId=String.valueOf(info.getLogId());
        String onfocus="cook('"+logId+"')";
        %>
 <body onfocus="<%=onfocus%>" style="margin:40px">
   <table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
<tr>

</tr>
<tr>
<td  valign="top">
<table>
<tr valign="top">

   <%if(request.getAttribute("msg")!=null){%>
    <td>
    <table>
    <tr>
    <td valign="top" class="tdLayout">
    <span class="redLabel"><%=request.getAttribute("msg")%> </span>
    </td>
    </tr>
    </table>
    </td>
  <%}
  else{
HashMap allPageDtl= (info.getPageDetails() == null)?new HashMap():(HashMap)info.getPageDetails();
ArrayList imagelistDtl= (info.getImagelistDtl() == null)?new ArrayList():(ArrayList)info.getImagelistDtl();
  HashMap stockPrp = (HashMap)request.getAttribute("stockList");
  ArrayList viewPrp = (ArrayList)request.getAttribute("PRPPKT_PRP_VW");
  ArrayList idnList = (ArrayList)request.getAttribute("idnList"); 
  ArrayList prpDspBlocked = info.getPageblockList();
  HashMap mprp = info.getMprp();
  HashMap dtls=new HashMap();
  String mstkIdn=null;
  String lprp=null;
  String lprpVal=null;
  for(int i=0;i<idnList.size();i++){
    mstkIdn = (String)idnList.get(i);
    String vnm=util.nvl((String)stockPrp.get(mstkIdn+"_VNM"));
    %>
    <td>
    <table>
    <tr valign="top"><td>
    <table width="290px;" style="float:left;" class="grid1">
    <tr valign="top">
    <th>Property</th><th>Value</th></tr>
    <tr><td>Vnm</td> <td><%=vnm%></td> </tr>
    <%
    for(int j=0;j<viewPrp.size();j++){
      lprp = (String)viewPrp.get(j);
      if(prpDspBlocked.contains(lprp)){
      }else{
      lprpVal = util.nvl((String)stockPrp.get(mstkIdn+"_"+lprp));
      String prpDsc = (String)mprp.get(lprp+"D");
     // if(!lprpVal.equals("")){
      %> 
        <tr>
        <td>
        <%=prpDsc%></td><td><%=lprpVal%>
        </td>
        </tr>
      <% 
    //  }
    }}%>
    
    </table>
    </td></tr>
<%if(imagelistDtl!=null && imagelistDtl.size() >0){
for(int j=0;j<imagelistDtl.size();j++){
dtls=new HashMap();
dtls=(HashMap)imagelistDtl.get(j);
String typ=util.nvl((String)dtls.get("TYP"));
String allowon=util.nvl((String)dtls.get("ALLOWON"));
if(typ.equals("I") && (allowon.indexOf("COMP") > -1)){
String path=util.nvl((String)dtls.get("PATH"));
String gtCol=util.nvl((String)dtls.get("GTCOL"));
String val=util.nvl((String)stockPrp.get(mstkIdn+"_"+gtCol));
if(!val.equals("N")){
path=path+val;
String dtlpath="../zoompic.jsp?fileNme="+path+"&ht=600";
String url = "<A href='"+dtlpath+"' target=\"_blank\" ><img src='"+path+"' border=\"0\" width=\"180\" height=\"150\" /></a>";
%>
<tr>
    <td align="center">
    <%=url%>
    </td>
    </tr>
<%}else{%>
    <tr>
    <td align="center">
    <img src="../images/na.jpg" width="180" height="150" />
    </td>
    </tr>
<%}
}}}%>
    </table></td>
    <%
  }
 } %>
</tr>
    
</table></td>

</tr>
</table>
  </body>
</html>