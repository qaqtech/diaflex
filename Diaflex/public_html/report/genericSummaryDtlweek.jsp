<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import="java.util.ArrayList,java.util.ArrayList,java.util.List, java.util.HashMap, java.sql.*, java.util.Enumeration, java.util.logging.Level"%>
<jsp:useBean id="info" class="ft.com.InfoMgr" scope="session" />
<jsp:useBean id="util" class="ft.com.JspUtil" scope="page" />
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
<title>Search Result</title>
<script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/bse.js?v=<%=info.getJsVersion()%> " > </script>
<link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/bse.css?v=<%=info.getJsVersion()%>"/>
<script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/ajax.js?v=<%=info.getJsVersion()%> " > </script>
<link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/style.css?v=<%=info.getJsVersion()%>"/>
<link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/general.css?v=<%=info.getJsVersion()%>"/>
<script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/memoScript.js?v=<%=info.getJsVersion()%> " > </script>
<script type="text/javascript" src="<%=info.getReqUrl()%>/PopScripts/popup.js?v=<%=info.getJsVersion()%> " > </script>
<link rel="stylesheet" media="screen" type="text/css" href="css/zoomimage.css" />
<script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/tableFilter/actb.js " > </script>    
<script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/jquery.js"></script>
<script src="<%=info.getReqUrl()%>/PopScripts/jquery-1.2.6.min.js" type="text/javascript"></script>
<script type="text/javascript" src="<%=info.getReqUrl()%>/chartscript/amcharts.js?v=<%=info.getJsVersion()%> " > </script>
<script type="text/javascript" src="<%=info.getReqUrl()%>/chartscript/chartajax.js?v=<%=info.getJsVersion()%> " > </script>
<script type="text/javascript" src="<%=info.getReqUrl()%>/chartscript/serial.js?v=<%=info.getJsVersion()%> " > </script>
</head>
<%
Integer OPENSTR = (Integer)session.getAttribute("OPENSTRWEEK");
  HashMap iosummWeekDtl = (HashMap)session.getAttribute("iosummWeekDtl");
  ArrayList colWeekList = (ArrayList)session.getAttribute("colWeekList");
 String attbte="";
        String logId=String.valueOf(info.getLogId());
        String onfocus="cook('"+logId+"')";
        %>
 <body onfocus="<%=onfocus%>">
  <div>
  <div>
  <table   valign="top" class="hedPg" border="0" align="left" cellspacing="0" cellpadding="0" width="50%" >
  <tr>
  <td class="memo" <%=attbte%> valign="top" width="">
<table><tr>
  <td valign="top">
 <!--Mayur-->
 <table border="0" align="left" class="Orange" cellspacing="1" cellpadding="1">
   <tr><th class="Orangeth" colspan="6">Week Wise</th></tr>
  <tr><th class="Orangeth" align="left"></th><th class="Orangeth" align="left">Opening</th><th class="Orangeth" align="left">NEW</th> 
  <th class="Orangeth" align="left">NEW SOLD</th> 
  <th class="Orangeth" align="left">SOLD</th> <th class="Orangeth" align="left">Closing</th></tr>
  <%
  
  int newValIntTotal =0;
  int soldValIntTotal =0;
  int openingValTotal = 0;
  int closeValTotal = 0;
  int newSoldTotal = 0;
  openingValTotal = OPENSTR;
  for(int i=0;i<colWeekList.size();i++){
  String fldVal = (String)colWeekList.get(i);
  String newVal = util.nvl((String)iosummWeekDtl.get("NEW_"+fldVal));
  String soldVal = util.nvl((String)iosummWeekDtl.get("SOLD_"+fldVal));
  String newsoldVal = util.nvl((String)iosummWeekDtl.get("NEW_SOLD_"+fldVal));
  int newValInt =0;
  int soldValInt =0;
  int openingVal = 0;
  int closeVal = 0;
  int newSold = 0;
  if(!newVal.equals(""))
    newValInt = Integer.parseInt(newVal);
  if(!soldVal.equals(""))
    soldValInt = Integer.parseInt(soldVal);
  if(!newsoldVal.equals(""))
    newSold = Integer.parseInt(newsoldVal);
     openingVal = OPENSTR;
     closeVal = (openingVal + newValInt)-soldValInt-newSold;
     OPENSTR = closeVal;
  newValIntTotal+=newValInt;
  newSoldTotal+=newSold;
  soldValIntTotal+=soldValInt;
  %>
  <tr>
  <td><%=fldVal%></td>
  <td align="left"><%=openingVal%></td>
  <td align="left"><%=newValInt%></td>
   <td align="left"><%=newSold%></td>
  <td align="left"><%=soldValInt%></td>
  
  <td align="left"><%=closeVal%></td>
 </tr>
  <%}  
  closeValTotal = (openingValTotal + newValIntTotal)-soldValIntTotal-newSoldTotal;%>
  <tr>
  <td align="center"><b>Total</b></td>
  <td align="left"><%=openingValTotal%></td>
  <td align="left"><%=newValIntTotal%></td>
   <td align="left"><%=newSoldTotal%></td>
  <td align="left"><%=soldValIntTotal%></td>
  <td align="left"><%=closeValTotal%></td>
 </tr>
 </table></td></tr></table>
  </td>
  </tr>
  </table>
  </div>
  <div>
  <%
  String url="ajaxRptAction.do?method=ioWeekChart";
  String style="width: 50%; height: 362px; float:left;";
  String styleId="ioweek_HIDD";
  %>
  <input type="hidden" id="stackGrp" value="NEW_Opening_NEW SOLD_SOLD">
  <input type="hidden" id="<%=styleId%>" value="ioweek">
  <div id="ioweek" style="<%=style%>"></div>
  </div>
<script type="text/javascript">
 <!--
$(window).bind("load", function() {
ioWeekChart('<%=url%>','Weekly I/O Summary','50','362');
});
 -->
</script> 
  </div>
  </body>
</html>