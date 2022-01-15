<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page errorPage="../error_page.jsp" %>
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.ResultSet, java.util.ArrayList,java.util.Collections,java.util.ArrayList,java.util.HashMap,java.text.SimpleDateFormat, java.util.Calendar"%>
<jsp:useBean id="info" class="ft.com.InfoMgr" scope="session" />
<jsp:useBean id="util" class="ft.com.JspUtil" scope="page" />
<%@taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<html>
   <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/bse.js?v=<%=info.getJsVersion()%> " > </script>
<link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/bse.css?v=<%=info.getJsVersion()%>"/>
<link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/general.css?v=<%=info.getJsVersion()%>"/>
<script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/ajax.js?v=<%=info.getJsVersion()%> " > </script>
  <script type="text/javascript" src="<%=info.getReqUrl()%>/PopScripts/popup.js?v=<%=info.getJsVersion()%> " > </script>
    <script src="<%=info.getReqUrl()%>/PopScripts/jquery-1.2.6.min.js" type="text/javascript"></script>
   <script language="javaScript" type="text/javascript" src="<%=info.getReqUrl()%>/calendar/calendar.js"></script>
   <script language="javaScript" type="text/javascript" src="<%=info.getReqUrl()%>/scripts/memoScript.js?v=<%=info.getJsVersion()%>"></script>
   <link href="<%=info.getReqUrl()%>/calendar/calendar.css" rel="stylesheet" type="text/css" />

 
      <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/style.css?v=<%=info.getJsVersion()%>"/>

  <title>Combination Report</title>
 
  </head>
        <%String logId=String.valueOf(info.getLogId());
        String onfocus="cook('"+logId+"')";
        %>
 <body onfocus="<%=onfocus%>">
   <html:form action="report/combinationReport.do?method=view" method="post">
   
  <table cellpadding="" cellspacing="" width="80%" class="mainbg">
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
  <img src="../images/line.jpg" border="0" /></td><td valign="middle"> <span class="pgHed">
Combination Report
 
  </span> </td>
</tr></table>
  </td>
  </tr>
  <%

  Boolean vecLoad=false;
   ArrayList sttVec=(ArrayList)request.getAttribute("sttvec");
   if(request.getAttribute("vecload")!=null)
   {
    vecLoad=(Boolean)request.getAttribute("vecload");
    }
   HashMap mprp = info.getMprp();
   HashMap prp = info.getPrp();
   %>
   
    <tr><td class="hedPg">
  <table>
  <tr><td>Status:</td><td>
  <html:select property="value(stt)"  name="CombinationReportForm"   >
    
    <html:optionsCollection property="sttList" name="CombinationReportForm"  label="FORM_TTL" value="FORM_NME" />
    </html:select>
  </td></tr>
  
  <tr><td valign="top">Row1 :</td><td>
    <html:select property="value(prpRow1)" onchange="" styleId="row1" name="CombinationReportForm" >
   <html:option  value="select">select</html:option>
    <html:optionsCollection property="mprpArryRow1" name="CombinationReportForm" label="dsc" value="prp"/>
    </html:select>
    </td>
    </tr>
    <tr><td valign="top">Row2 :</td><td>
    <html:select property="value(prpRow2)" onchange="" styleId="row2" name="CombinationReportForm" >
   <html:option  value="select">select</html:option>
    <html:optionsCollection property="mprpArryRow1" name="CombinationReportForm" label="dsc" value="prp" />
    </html:select>
    </td>
    </tr>
    <tr>
  <td width="60px" valign="top" >Col1 :</td>
    
    <td width="250px" valign="top" > <html:select property="value(prpCol1)" onchange="" styleId="col1" name="CombinationReportForm" >
    <html:option value="select">select</html:option>
    <html:optionsCollection property="mprpArryCol1" name="CombinationReportForm" label="dsc" value="prp" />
    </html:select></td>
    
    <td width="60px" valign="top" >Col2 :</td>
    
    <td width="250px" valign="top" > <html:select property="value(prpCol2)" onchange="" styleId="col2" name="CombinationReportForm" >
    <html:option value="select">select</html:option>
    <html:optionsCollection property="mprpArryCol1" name="CombinationReportForm" label="dsc" value="prp" />
    </html:select></td>
  </tr>
  
  </table></td></tr>
  <tr><td class="hedPg"><table>
  <%
      ArrayList gncPrpLst = (ArrayList)session.getAttribute("COMB_SRCH");
      info.setGncPrpLst(gncPrpLst); 
  for(int i=0;i<gncPrpLst.size();i++)
    {
    String  lprp=(String)gncPrpLst.get(i);
    ArrayList prpPrt = (ArrayList)prp.get(lprp+"P");
    ArrayList prpSrt = (ArrayList)prp.get(lprp+"S");
    ArrayList prpVal = (ArrayList)prp.get(lprp+"V");
    String mutiTextId = "MTXT_"+lprp;
    String mutiText = "value("+ mutiTextId +")";
    String loadStrL="ALL";
    %>
  <tr><td ><span class="txtBold"> <%=lprp%> </span></td>
         <%    
                
            if(prpSrt != null) {
               
               %>
                <td  align="center">
                 <div class="multiplePrpdiv" id="<%=lprp%>" align="center" style="display:none;">
                <table cellpadding="3" width="170px" class="multipleBg" cellspacing="3">
                   <%for(int m=0;m<prpSrt.size();m++){
                String isSelected = "";
                String pPrtl = (String)prpPrt.get(m);
                String pSrtl = (String)prpSrt.get(m);
                String vall = (String)prpVal.get(m);
                String chFldNme = "value("+lprp+"_"+pSrtl+")" ;
                String onclick= "checkPrp(this, 'MTXT_"+lprp+"','"+lprp+"')";
                String checId = lprp+"_"+pSrtl;
               
                
//                String fld = util.nvl((String)favMap.get(lprp+"_"+pSrtl));
//                 if(fld.equals(pSrtl+"_to_"+pSrtl)){
//                   isSelected = "checked=\"checked\"";
//                   loadStrL = loadStrL+" , "+pPrtl;
//                   }
             %>
             <tr ><td align="center"><html:checkbox  property="<%=chFldNme%>"  name="CombinationReportForm" styleId="<%=checId%>"  onclick="<%=onclick%>" value="<%=pPrtl%>"/></td><td align="center"><%=pPrtl%></td></tr>
             <%}%> <!--  change value to pPrtl-->
            </table>
             <input type="hidden" id="HID_<%=lprp%>" value="<%=loadStrL%>" />
              </div>
          <%   
          }%>
             <table cellpadding="0" cellspacing="0"><tr><td>
              <html:text property="<%=mutiText%>" value="<%=loadStrL%>" name="CombinationReportForm"  size="30" styleId="<%=mutiTextId%>" styleClass="txtStyle" /></td>
             
             <td>&nbsp;&nbsp;</td>
     <td><img src="../images/plus.jpg" id="IMGD_<%=lprp%>" class="img" onclick="DisplayDiv('<%=lprp%>')" style="display:block" border="0"/></td><td><img src="../images/minus.jpg" id="IMGU_<%=lprp%>" class="img" onclick="Hide('<%=lprp%>')" style="display:none" border="0"/></td>
     
  </tr></table></td>
  
 
 <%
 //}  //end of else
 %>
 </tr>
 
<% }
  %>
  </table>
  </td></tr>
  
  <tr><td class="hedPg"><table><tr><td width="60px"></td><td><input value="Submit" type="submit"/></td></tr></table></td></tr>
  </table></html:form>
  
  
 
            
              
           
            
  </body>
</html>