<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=windows-1252"%>
<jsp:useBean id="info" class="ft.com.InfoMgr" scope="session" />
<jsp:useBean id="util" class="ft.com.DBUtil" scope="session" />
<%@ page import="java.util.ArrayList,java.util.HashMap, java.util.Date, java.util.ArrayList, ft.com.dao.*"%>
<%@taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<html>
    
  <head>
  <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <!---<title>Assort Issue</title>--->
    <title>Magnus Checker</title>
    <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/bse.css?v=<%=info.getJsVersion()%>"/>
    <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/general.css?v=<%=info.getJsVersion()%>"/>
     <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/style.css?v=<%=info.getJsVersion()%>"/>
      <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/bse.js?v=<%=info.getJsVersion()%> " > </script>
      
      <script language="Javascript">
      function showdiv(divid) {
        //alert("div");
        var div = document.getElementById(divid);
        div.style.display='';
      }
      
      function checkOKALL(checkId , count, id){
        var checked = document.getElementById(id).checked;
        var countval = document.getElementById(count).value;
        for(var i=1;i<=countval;i++){
          var objId = checkId+i;
          document.getElementById(objId).checked = checked;
        }
      }
      
      function calculateDiff(cts, newwtid, diffid) {
        var newwt = document.getElementById(newwtid).value;
        if(newwt=='')
        newwt = 0;
       
        var diffcts = parseFloat(cts) - parseFloat(newwt);
        
        document.getElementById(diffid).value = (new Number(diffcts)).toFixed(2);
      }
      
      function validate_magnuschk() {
        //alert("in validate_magnuschk");
        var vnmLst = document.getElementById("vnmLst").value;
        
        document.getElementById("subbutton").value = "srch";
        
        if(vnmLst.length != 0) {
          return true;
        }
        
        alert("Please enter Barcode and then click on Fetch. \nTo fetch all records please click Fetch All ")
        return false;
      }
      
      function fetchAll() {
        document.getElementById("subbutton").value = "srchall";
      }
      
      function unchkothr(eleid) {
      //alert(eleid);
        document.getElementById(eleid).checked = false;
      }
      
      function valSubmit() {
      //alert("in val submit");
        var test = validate_issue('CHK_' , 'count');
        //alert(test);
        //return false;
        if(test) {
          //return validate_issue('CHK_' , 'count');
          return valFail();
        }
        else 
          return false;
      }
      
      
      function valFail() 
      {
        var okChk = 'CHKOK_';
        var failChk = 'CHKFL_';
        var selectChk = 'sltChk';
        
        var appOK = "APPOK_";
        var appFL = "APPFL_";
        
        var selectApp = "sltApp";
        var diff = "diff";
        
        var countval = document.getElementById("count").value;
        //alert("countval: "+countval);
        for(var i=1;i<=countval;i++)
        {
          var objId = 'CHK_'+i;
          var diffval = document.getElementById(diff+i).value;
          if(document.getElementById(objId).checked)
          {
            //alert("checker checkbox: "+ document.getElementById(failChk+i).checked);
            
            if(document.getElementById(okChk+i).checked) {
              
            }
            else if(document.getElementById(failChk+i).checked) 
            {
              var chkRem = document.getElementById(selectChk+i).value;
              //alert("chkRem: "+ chkRem);
              if(chkRem == '0') 
              {
                alert("Please select Checker Remark. ");
                document.getElementById(selectChk+i).focus();
                return false;
              }
              else if(diffval.length <= 0) {
                alert("Please enter New Weight.");
                document.getElementById('newWt'+i).focus();
                return false;
              }
              else 
              {
                if(document.getElementById(appOK+i).checked || document.getElementById(appFL+i).checked) 
                {
                  var appRem = document.getElementById(selectApp+i).value;
                  if(appRem == '0') 
                  {
                    alert("Please select Approver Remark. ");
                    document.getElementById(selectApp+i).focus();
                    return false;
                  }
                }
                else 
                {
                  alert("Please select Approver OK or Fail and Approver remark.")
                  document.getElementById(appOK+i).focus();
                  return false;
                }
              }
            }
            else {
              alert("Please select Checker OK or Fail.");
              //document.getElementById(okChk+i).focus();
              document.getElementById(okChk+i).checked = true;
              return false;
            }
          }
        }
        
        return true;
      }
      
      function clearPg(url) {
      //alert("clearPg called");
      //var url = "issuetoMagnus.do?method=load&instt="+instt+"&head="+head;
        window.open(ReplaceAll(url, '*', '\''), '_self');
      }
      
      
      function ReplaceAll(Source,stringToFind,stringToReplace){

        var temp = Source;
        var index = temp.indexOf(stringToFind);
        
        while(index != -1){
            temp = temp.replace(stringToFind,stringToReplace);
            index = temp.indexOf(stringToFind);
        }
        
        return temp;
      
      }
      
      </script>
  </head>
        <%String logId=String.valueOf(info.getLogId());
        String onfocus="cook('"+logId+"')";
        %>
 <body onfocus="<%=onfocus%>" onload="MM_preloadImages('images/faunalogo.png')" topmargin="0" leftmargin="0" rightmargin="0" bottommargin="0">
<%
 util.updAccessLog(request,response,"Weight Check", "Weight Check");
    String method = util.nvl(request.getParameter("method"));
    ArrayList stockList = (ArrayList)session.getAttribute("StockList");
    boolean disabled= false;
    if(method.equals("fetch")){
      if(stockList!=null && stockList.size()>0)
        disabled= true;
    }
    
    String isstt = (String) request.getAttribute("isstt");
    
%>


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
      <img src="../images/line.jpg" border="0" /></td><td valign="middle"> <span class="pgHed">Weight Checker</span> </td>
      </tr></table>
    </td>
  </tr>
  <%
   String msg = (String)request.getAttribute("msg");
   if(msg!=null){%>
    <tr><td valign="top" class="tdLayout"><span class="redLabel"><%=msg%></span></td></tr>
   <%
   }%>
    <%
   String vnmNtFnd = (String)request.getAttribute("vnmNotFnd");
     if(vnmNtFnd!=null){ %>
      <tr><td valign="top" class="tdLayout"><span class="redLabel"><%=vnmNtFnd%></span></td></tr>
     <%
     } %>
     
 
    <%
      ArrayList prcidList = (ArrayList) session.getAttribute("prcidList");
      String prcid = prcidList.get(0).toString();
      
    %>
    
   <tr>
   
   <td valign="top" class="tdLayout">
   <table><tr><td>
   <html:form action="assorthk/weightCheck.do?method=fetch" method="post">
   
    <table  class="grid1">
      <tr><th> </th> <!---<th>Generic Search </th>---></tr>
      <tr>
        <td valign="top">
         <table>
         
         <!---<tr><td>Process </td>
         <td>
         <html:select property="value(mprcIdn)"  styleId="mprcIdn" name="hkwtCheckForm" disabled="<%=disabled%>"   >
                 <html:option value="0" >--select--</html:option>
                  <html:optionsCollection property="mprcList" name="hkwtCheckForm" 
                  label="prc" value="mprcId" />
          </html:select>
          
         </td></tr>
         
         <tr>
           <td>  Employee : </td>
           <td>
           <html:select property="value(empIdn)"  styleId="empIdn" name="hkwtCheckForm" disabled="<%=disabled%>"  >
                   <html:option value="0" >--select--</html:option>
                    <html:optionsCollection property="empList" name="hkwtCheckForm" 
                    label="emp_nme" value="emp_idn" />
                    </html:select>
           </td>
         </tr>--->
         <tr>
            <td>Barcode:</td><td> <html:textarea property="value(vnmLst)" styleId="vnmLst" rows="7"  cols="30" name="hkwtCheckForm"  /></td>
         </tr>
        </table>
        
        <html:hidden property="value(mprcIdn)" value="<%=prcid%>" />
        <html:hidden property="value(subbutton)" styleId="subbutton" value="" />
        <html:hidden property="value(isstt)" value="<%=isstt%>"/>
        </td>
        <!---<td><jsp:include page="/genericSrch.jsp"/></td>--->
        
      </tr>
      <tr>
      <td><table>
        <tr>
          <td align="center"><html:submit property="value(srch)" value="Fetch" styleClass="submit" onclick="return validate_magnuschk()" /></td> 
          <td align="center"><html:submit property="value(srchall)" value="Fetch All" styleClass="submit" onclick="fetchAll()" /></td> 
        </tr>
      </table></td>
      </tr>
     </table>
   </html:form>
   </td></tr>
   
   <%
   String view = (String)request.getAttribute("view");
   //String stktyp = (String)request.getAttribute("stktyp");
   
   String url = "weightCheck.do?method=load&isstt="+isstt.replaceAll("'", "*")+"&mdl="+session.getAttribute("mdl").toString();
   
   if(view!=null)
   {
   
      if(stockList!=null && stockList.size()>0)
      {
      ArrayList prpDspBlocked = info.getPageblockList();
        ArrayList vwPrpLst = (ArrayList)session.getAttribute("asViewLst");
        HashMap totals = (HashMap)request.getAttribute("totalMap");
        HashMap mprp = info.getMprp();
        int sr = 0;
        %>
        <tr>
         <td>
           <table>
           <tr>
              <td>Total :&nbsp;&nbsp;</td>
              <td><span id="ttlqty"> <%=totals.get("qty")%>&nbsp;&nbsp;</span></td>
              <td>cts&nbsp;&nbsp;</td>
              <td><span id="ttlcts"><%=totals.get("cts")%>&nbsp;&nbsp;</span></td>
              <td>Selected:&nbsp;&nbsp;</td>
              <td> Total :&nbsp;&nbsp;</td> 
              <td><span id="qtyTtl">0&nbsp;&nbsp;</span></td>
              <td>Cts&nbsp;&nbsp;</td>
              <td><span id="ctsTtl">0</span> </td> 
           </tr>
           </table>
         </td>
        </tr>
       
        <html:form action="assorthk/weightCheck.do?method=save" method="post" onsubmit="return valSubmit();">
         <html:hidden property="value(prcId)" name="hkwtCheckForm" value="<%=prcid%>" />
         <%--<html:hidden property="value(empId)" name="hkwtCheckForm" />--%>
         <html:hidden property="value(isstt)" value="<%=isstt%>"/>
         
         <tr>
          <td><table><tr>
          <td><html:submit property="value(issue)" styleClass="submit" value="Save" /> </td>
          <td><input type="button" class="submit" value="Clear" onclick="clearPg('<%=url%>');" /> </td>
          </tr></table></td>
        </tr>
         <tr>
           <td>
            <table class="grid1">
              <tr> 
                  <th><input type="checkbox" name="checkAll" id="checkAll" onclick="checkALL('CHK_','count')" /> </th><th>Sr</th>
                  <th>Packet No.</th>
                
                  <%for(int j=0; j < vwPrpLst.size(); j++ ){
                      String prp = (String)vwPrpLst.get(j);
                      if(prpDspBlocked.contains(prp)){
                      }else{
                      String hdr = (String)mprp.get(prp);
                      String prpDsc = (String)mprp.get(prp+"D");
                      if(hdr == null) {
                        hdr = prp;
                       }
                      %>
                      <th title="<%=prpDsc%>"><%=hdr%></th>
                      
                      <%
                    }}%>
                  
                  <th>Weight Check OK <input type="checkbox" name="checkOKAll" id="checkOKAll" onclick="checkOKALL('CHKOK_','count', 'checkOKAll', 'CHKFL_')" /></th>
                  <th>Weight Check Fail</th>
                  <th>Remark</th>
                  
                  <th>New Weight</th>
                  <th>Difference</th>
                  
                  <th>Approver OK <input type="checkbox" name="appOKAll" id="appOKAll" onclick="checkOKALL('APPOK_','count', 'appOKAll', 'APPFL_' )" /></th>
                  <th>Approver Fail</th>
                  <th>Remark</th>
                  
                  <!--<th>Clear Selection </th>-->
              </tr>
              <%
              
              for(int i=0; i < stockList.size(); i++ )
              {
                 HashMap stockPkt = (HashMap)stockList.get(i);
                 String stkIdn = (String)stockPkt.get("stk_idn");
                 String cts = (String)stockPkt.get("CRTWT");
                 String onclick = "AssortTotalCal(this,"+cts+",'','')";
                 sr = i+1;
                 String checkFldId = "CHK_"+sr;
                 String checkFldVal = "value("+stkIdn+")";
                 
                 String chkOK = "CHKOK_"+sr;
                 String chkFL = "CHKFL_"+sr;
                 
                 String chkOKVal = "value(CHKOK_"+sr+")";
                 String chkFLVal = "value(CHKFL_"+sr+")";
                 
                 String selectChk = "sltChk"+sr;
                 String selectChkVal = "value(chkRem_"+sr+")";
                 
                 String appOK = "APPOK_"+sr;
                 String appFL = "APPFL_"+sr;
                 
                 String appOKVal = "value(APPOK_"+sr+")";
                 String appFLVal = "value(APPFL_"+sr+")";
                 
                 String selectApp = "sltApp"+sr;
                 String selectAppVal = "value(appRem_"+sr+")";
                 
                 String unchkCOK = "unchkothr('"+chkOK+"');";
                 String unchkCFL = "unchkothr('"+chkFL+"');";
                 String unchkAOK = "unchkothr('"+appOK+"');";
                 String unchkAFL = "unchkothr('"+appFL+"');";
                 
                 String newWt = "newWt"+sr;
                 String diff = "diff"+sr;
                 
                 String newWtVal = "value(newWt_"+sr+")";
                 String diffVal = "value(diff_"+sr+")";
                 
                 String calculateDiff = "calculateDiff('"+cts+"', '"+newWt+"', '"+diff+"')";
                 
                 %>
                  <tr>
                  <td><html:checkbox property="<%=checkFldVal%>" styleId="<%=checkFldId%>" name="hkwtCheckForm" onclick="<%=onclick%>" value="yes" /> </td>
                  <td><%=sr%></td>
                  <td><%=stockPkt.get("vnm")%><input type="hidden" id="CTS_<%=stkIdn%>"  value="<%=cts%>" /></td>
                  
                  <%for(int j=0; j < vwPrpLst.size(); j++ ){
                      String prp = (String)vwPrpLst.get(j);
                      if(prpDspBlocked.contains(prp)){
                      }else{
                      %>
                      <td><%=stockPkt.get(prp)%></td>
                  <%}}%>
                  
                  <td><html:checkbox property="<%=chkOKVal%>" styleId="<%=chkOK%>" onclick="<%=unchkCFL%>" name="hkwtCheckForm" value="yes"/></td>
                  <td><html:checkbox property="<%=chkFLVal%>" styleId="<%=chkFL%>" onclick="<%=unchkCOK%>" name="hkwtCheckForm" value="yes"/></td>
                  <!--<td><html:radio property="<%=chkOKVal%>" styleId="<%=chkOK%>" value="ok" /></td>-->
                  <!--<td><html:radio property="<%=chkOKVal%>" styleId="<%=chkOK%>" value="fail" /></td>-->
                  
                  <td>
                    <html:select property="<%=selectChkVal%>" styleId="<%=selectChk%>" name="hkwtCheckForm">
                      <html:option value="0" >--select--</html:option>
                      <html:optionsCollection property="chkRem" name="hkwtCheckForm" label="emp_nme" value="emp_idn" />
                    </html:select>
                  </td>
                  
                  <td><html:text property="<%=newWtVal%>" name="hkwtCheckForm" styleId="<%=newWt%>" onchange="<%=calculateDiff%>" /></td>
                  <td><html:text property="<%=diffVal%>" name="hkwtCheckForm" styleId="<%=diff%>" /></td>
                  
                  <td><html:checkbox property="<%=appOKVal%>" styleId="<%=appOK%>" name="hkwtCheckForm" onclick="<%=unchkAFL%>" value="yes"/></td>
                  <td><html:checkbox property="<%=appFLVal%>" styleId="<%=appFL%>" name="hkwtCheckForm" onclick="<%=unchkAOK%>" value="yes"/></td>
                  <!--<td><html:radio property="<%=appOKVal%>" styleId="<%=appOK%>" value="ok" />-->
                  <!--<td><html:radio property="<%=appOKVal%>" styleId="<%=appOK%>" value="fail" />-->
                  <td>
                    <html:select property="<%=selectAppVal%>" styleId="<%=selectApp%>" name="hkwtCheckForm">
                      <html:option value="0" >--select--</html:option>
                      <html:optionsCollection property="appRem" name="hkwtCheckForm" label="emp_nme" value="emp_idn" />
                    </html:select>
                  </td>
                  
                  <!--Clear the selection-->
                  <!--<td> <input type="button" value="Clear" onclick="clearSel('<%=sr%>');" /> </td>-->
                  </tr>
              <%
              }%>
           </table>
           </td>
         </tr>
         <input type="hidden" name="count" id="count" value="<%=sr%>" />
        </html:form>
      <%
      }
      else
      {%>
        <tr><td>Sorry no result found </td></tr>
      <%
      }
      }%>
   
   </table>
   
  
  
   <tr>
    <td><jsp:include page="/footer.jsp" /> </td>
    </tr>
  </table>

  </body>
</html>