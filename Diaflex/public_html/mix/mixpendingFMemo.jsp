<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page errorPage="../error_page.jsp" %>
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.ResultSet, java.util.ArrayList,java.util.Collections,java.util.ArrayList,java.util.HashMap,java.text.SimpleDateFormat, java.util.Calendar"%>

<jsp:useBean id="info" class="ft.com.InfoMgr" scope="session"/>
<jsp:useBean id="util" class="ft.com.JspUtil" scope="page" />
<%@taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>
<html>
   <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/bse.js?v=<%=info.getJsVersion()%> " > </script>
<link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/bse.css?v=<%=info.getJsVersion()%>"/>
<link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/general.css?v=<%=info.getJsVersion()%>"/>
<script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/ajax.js?v=<%=info.getJsVersion()%> " > </script>
  <script type="text/javascript" src="<%=info.getReqUrl()%>/PopScripts/popup.js?v=<%=info.getJsVersion()%> " > </script>
    <script src="<%=info.getReqUrl()%>/PopScripts/jquery-1.2.6.min.js" type="text/javascript"></script>
       <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/memoScript.js?v=<%=info.getJsVersion()%> " > </script>
   <script language="javaScript" type="text/javascript" src="<%=info.getReqUrl()%>/calendar/calendar.js"></script>
   <link href="<%=info.getReqUrl()%>/calendar/calendar.css" rel="stylesheet" type="text/css" />

 
      <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/style.css?v=<%=info.getJsVersion()%>"/>

  <title>Pending For Memo</title>
 
  </head>
        <%String logId=String.valueOf(info.getLogId());
        String onfocus="cook('"+logId+"')";
        %>
 <body onfocus="<%=onfocus%>" onkeypress="return disableEnterKey(event);" >
   
<table width="100%"  border="0" align="center" cellpadding="0" cellspacing="0" class="mainbg">
   <tr>
    <td height="103" valign="top">
   <jsp:include page="/header.jsp" />
    
    </td>
  </tr>
    <tr>
    <td height="5" valign="top" class="greyline"><img src="../images/grey_linebig.png" width="685" height="5" align="right" /></td>
  </tr>
   <tr><td valign="top" class="hedPg"  >
  <table cellpadding="1" cellspacing="5"><tr><td valign="middle">
  <img src="../images/line.jpg" border="0" /></td><td valign="middle"> <span class="pgHed">
<bean:write property="pendingNme" name="mixpendingForm" />
 
  </span> </td>
</tr></table>
  </td>
  </tr>
  <%
HashMap memoTotalsDtl=(HashMap)request.getAttribute("memoTotalsDtl");
HashMap memoDtl=(HashMap)request.getAttribute("memoDtl");
HashMap byrDtl=(HashMap)request.getAttribute("byrDtl");
ArrayList empList=(ArrayList)request.getAttribute("empList");
ArrayList memotyp=(ArrayList)request.getAttribute("memotyp");
HashMap Display_typ=(HashMap)request.getAttribute("Display_typ");
HashMap byr_idn=(HashMap)request.getAttribute("byr_idn");
 String pgtyp=util.nvl(request.getParameter("typ"));
 if(pgtyp.equals(""))
     pgtyp=util.nvl((String)session.getAttribute("memoTyp"));
 String emp="";
 String byr="";
 String copybyr="";
 ArrayList dtl=new ArrayList();
 int loop=0;
 String rtnUrl = (String)Display_typ.get("RTNURL");
 if(rtnUrl==null)
 rtnUrl="box/boxReturn.do?method=load";
 
%>

 <tr><td class="hedPg" >
<input type="hidden" id="RTNURL" value="<%=rtnUrl%>" />
 <%
  String isRmk =util.nvl((String)Display_typ.get("RMK2"));
  if(!isRmk.equals("")){
 %>
 <input type="hidden" name="rmk2" id="rmk2" /><%}%>
   <%if(empList!=null && empList.size()> 0){%>
   <table class="grid1" width="60%">
       <tr>
        <th><div style="width:150px;">Sale Person</div></th>
        <th><div style="width:250px;">Buyer</div></th>
         <% for(int k=0;k<memotyp.size();k++){
         String dsptyp=(String)Display_typ.get(memotyp.get(k));
         %>
        <th colspan="5"><%=util.nvl2(dsptyp,(String)memotyp.get(k))%></th>
        <%}%>
      </tr>

      <tr>
      <td></td>
      <td></td>
       <% for(int k=0;k<memotyp.size();k++){%>
       <td align="right"><b>Cnt</b></td>
       <td align="right"><b>Qty</b></td>
       <td align="right"><b>Cts</b></td>
       <td align="right"><b>Vlu</b></td>
       <td><b>Merge</b></td>
      <%}%>
      </tr>
          <%for(int i=0;i<empList.size();i++){
          emp=(String)empList.get(i);
          %>
             <%
              for(int j=0;j<memoDtl.size();j++){
              byr=(String)byrDtl.get(emp+"_"+j);
              if(byr!=null && !byr.equals("") && !byr.equals(copybyr)){
                  %>
                  <tr><td><%=emp%></td>
                  <td><%=byr%></td>
                  <% for(int k=0;k<memotyp.size();k++){
                        dtl=(ArrayList)memoDtl.get(emp+"_"+byr+"_"+memotyp.get(k));
                        if(dtl!=null && dtl.size()> 0){
                            for(int l=k;l<=k;l++){
                            String qty = util.nvl((String)dtl.get(1));
                            String nmeIdn = util.nvl((String)dtl.get(4));
                            String typ = util.nvl((String)dtl.get(5));
                            %>
                            <td align="right"><a onclick="callmixpendfMemoPkt('<%=byr_idn.get(emp+"_"+byr)%>','<%=loop%>','<%=memotyp.get(k)%>',this)" title="Click Here To See Details" style="text-decoration:underline"><%=dtl.get(0)%></a> </td>
                            <td align="right"><%=dtl.get(1)%></td>
                            <td align="right"><%=dtl.get(2)%></td>
                            <td align="right"><%=dtl.get(3)%></td>
                            <td align="center">
                            <a href="<%=info.getReqUrl()%>/mix/penfmemo.do?method=merge&nmeIdn=<%=nmeIdn%>&typ=<%=typ%>&MemoTyp=<%=pgtyp%>" title="click here for Merge Memo"><img src="../images/merge_icon.jpg" border="0"/></a>
                            </td>
                            <%
                            }
                         }else{%>
                            <td align="center"></td>
                            <td align="center"></td>
                            <td align="center"></td>
                            <td align="center"></td>
                             <td align="center"></td>
                            <%
                          }
                    }%>
                 </tr>
                  <tr id="BYRTRDIV_<%=loop%>" style="display:none">
                      <td colspan="18"> 
                      <div id="BYR_<%=loop%>"  align="center">
                      </div>
                      <%
                      loop++;%>
                      </td>
                 </tr>
                <%}
                copybyr=byr;
              }
           }%>
           <tr>
           <td><b>Total</b></td>
           <td>&nbsp;</td>
           <% for(int k=0;k<memotyp.size();k++){
           String typttl=util.nvl((String)memotyp.get(k));
           %>
                  <td align="right"><b><%=util.nvl((String)memoTotalsDtl.get(typttl+"_CNT"))%></b></td>
                  <td align="right"><b><%=util.nvl((String)memoTotalsDtl.get(typttl+"_QTY"))%></b></td>
                  <td align="right"><b><%=util.nvl((String)memoTotalsDtl.get(typttl+"_CTS"))%></b></td>  
                  <td align="right"><b><%=util.nvl((String)memoTotalsDtl.get(typttl+"_VLU"))%></b></td>
                  <td>&nbsp;</td>
             <%}%>
           </tr>
      <input type="hidden" id="count" value="<%=loop%>" />
  </table>
   <%}else{%>
   Sorry no result found
   <%}%>
   </td>
   </tr>
  <tr>
    <td><jsp:include page="/footer.jsp" /> </td>
    </tr>
  </table>
  <%@include file="/calendar.jsp"%>
  </body>
</html>