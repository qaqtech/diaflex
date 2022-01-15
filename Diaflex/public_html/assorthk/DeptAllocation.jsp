<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=windows-1252"%>
<jsp:useBean id="info" class="ft.com.InfoMgr" scope="session" />
<jsp:useBean id="util" class="ft.com.DBUtil" scope="session" />

<%@ page import="java.util.ArrayList,java.util.HashMap,java.util.ArrayList, ft.com.assorthk.DeptAllocationForm"%>
<%@taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>

<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>DeptAllocation</title>
    
    <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/bse.css?v=<%=info.getJsVersion()%>"/>
    <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/general.css?v=<%=info.getJsVersion()%>"/>
    <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/style.css?v=<%=info.getJsVersion()%>"/>
    <script src="../scripts/bse.js" type="text/javascript"></script>
    <script src="../scripts/ajax.js" type="text/javascript"></script>
    
    <script language="Javascript">
      function check(dept) 
      {
        var divList = document.getElementsByTagName("div");
        divList.style.display = 'none';
        var div = document.getElementById(dept);
        div.style.display='';
      }
      function DisplayiFrame(link)
      {
      //alert(link);
          document.getElementById('frameDiv').style.display='';
          window.open(link, 'subDept');
      }
      /*function newwin(wurl, cnt, thruPer) 
      {
        //alert(url);
        var rurl = document.getElementById("rurl").value;
        alert(rurl);
        var test = wurl+"/reports/rwservlet?"+cnt+"&report="+rurl+"\\reports\\assort_memo_salesdpt.jsp&p_thru_nme="+thruPer;
        //alert(test);
        window.open(test);
      }*/
      function autoSubmit() {
        var sub = false;
        sub = confirm("Do you want to process with Auto-Allocation?");
        
        if(sub==true)
        {
          //alert("auto submit");
          document.getElementById("myform").submit();
        }
      }
    </script>
  </head>
        <%String logId=String.valueOf(info.getLogId());
        String onfocus="cook('"+logId+"')";
        %>
 <body onfocus="<%=onfocus%>" onload="MM_preloadImages('images/faunalogo.png');" topmargin="0" leftmargin="0" rightmargin="0" bottommargin="0">
  <%
  util.updAccessLog(request,response,"Dept Allocation", "Dept Allocation");
  %>
  <table width="100%"  border="0" align="center" cellpadding="0" cellspacing="0" class="mainbg">
  <tr>
    <td height="103" valign="top">
      <jsp:include page="/header.jsp" />    
    </td>
  </tr>
 
  <tr>
    <td height="5" valign="top" class="greyline" ><img src="../images/grey_linebig.png" width="685" height="5" align="right" /></td>
  </tr>
  <tr>
  <td valign="top" class="hedPg">
  <table cellpadding="1" cellspacing="5"><tr><td valign="middle">
  <img src="../images/line.jpg" border="0" /></td><td valign="middle"> <span class="pgHed">Dept. Allocation List</span> </td>
</tr></table>
  </td>
  </tr>
  <%
  
  //HashMap deptEmpList = (HashMap) request.getAttribute("deptEmpList");
  HashMap deptEmpList = (HashMap) info.getHtdeptEmpRel();
  HashMap deptAloc =  (HashMap)request.getAttribute("deptAloc");
  ArrayList arrdept = (ArrayList) request.getAttribute("arrdept");

  if(arrdept.size()>0)
  {
  %>
  
  <tr><td>
    <html:form styleId="myform" action="assorthk/DeptAllocation.do?method=processForm" method="post" >
      <table width="100%">
    
      <tr><td valign="top" class="tdLayout">
        <table width="70%">
          <tr> <td><html:hidden name="hkdeptAllocationForm" property="method" value="processForm"/></td></tr>
        </table>
      </td></tr>
      
      <tr><td valign="top" class="tdLayout">
        <table width="70%"  class="dataTable" >
        <tr> <td colspan="13">Dept. Allocation List</td> </tr>
          <tr>
            <th>Sr. No.</th>
            <th>Dept.</th>
            <th>Person</th>
            <th>Cts</th>
            <th>Pcs</th>
            <th>Detail</th>
            <th>Through</th>
          </tr>
          
          <%
          
          
          for(int i=0; i<arrdept.size(); i++)
          {
            String dept = arrdept.get(i).toString();
            
            ArrayList vecDeptAloc = (ArrayList) deptAloc.get(dept);
            HashMap temp = new HashMap();
            double cts = 0.0;
            int pcs = 0;
            
            for(int j=0; j<vecDeptAloc.size(); j++)
            {
                temp = (HashMap) vecDeptAloc.get(j);
                cts += Double.parseDouble(temp.get("cts").toString());
                pcs += Integer.parseInt(temp.get("pcs").toString());
                
            }        
            
            DeptAllocationForm deptAllocationForm = (DeptAllocationForm)request.getAttribute("form");
            ArrayList empList = new ArrayList();
            
            if(deptEmpList.get(dept)!=null)
            {
                empList = (ArrayList) deptEmpList.get(dept);
            }
            else
            { empList = new ArrayList();
            }
            deptAllocationForm.setEmpList(empList);
            
            ArrayList thruList = deptAllocationForm.getThruList();
            String fldVal = "value("+dept+")";
            
            String fldThru = "value(thru"+i+")";
          %>
          <tr>
            <td><%=i+1%></td>
            <td>
              <a  target="subDept" onclick="DisplayiFrame('DeptAllocation.do?method=dispSubdept&dept=<%=dept%>')">
                <%=dept%>
              </a>
              <input type="hidden" value="<%=dept%>" name="dept_<%=i%>" />
            </td>
            <td>
              <html:select style="width:150px;" property="<%=fldVal%>"  styleId="<%=dept%>" name="hkdeptAllocationForm" >
                <!---<html:option value="0" >--select--</html:option>--->
                  <html:optionsCollection property="empList" name="hkdeptAllocationForm" 
                  label="emp_nme" value="emp_idn" />
              </html:select>
            </td>
            <td><%=cts%></td>
            <td><%=pcs%></td>
            <td></td>
            <td>
              <html:select style="width:150px;" property="<%=fldThru%>"  styleId="thru_<%=i%>" name="hkdeptAllocationForm" >
                <!---<html:option value="0" >--select--</html:option>--->
                  <html:optionsCollection property="thruList" name="hkdeptAllocationForm" 
                  label="emp_nme" value="emp_idn" />
              </html:select>
            </td>
          </tr>
          <%
          }
          %>
                
        </table>
      </td></tr>
      
      <tr><td>&nbsp;</td></tr>
      <tr><td>&nbsp;</td></tr>
      
      
      <tr><td valign="top" class="tdLayout">
      <%
        String style = "display:none;";
        
      %>
        <div id="frameDiv" style="<%=style%>" >
          <iframe name="subDept" width="100%" class="iframe" frameborder="0">
          
          </iframe>
        </div>
      
      </td></tr>
      
      <%
        String thruPer = "Testthru";
        int arrsize = arrdept.size();
        
        //p_stt can be ALL or CRT
        
        HashMap dbinfo = info.getDmbsInfoLst();
        String cnt = (String)dbinfo.get("CNT");
        //String url =info.getWebUrl()+"/reports/rwservlet?"+cnt+"&report="+info.getRportUrl()+"\\reports\\PrtMemo.jsp&p_id="+memo+"&p_stt=ALL";
        //String rurl = info.getRportUrl().replaceAll("\\\","\\\");
      %>
      
      <tr><td valign="top" class="tdLayout">
        <table width="70%" class="dataTable" >
          <tr>
            <td>
              <input type="submit" name="submitDeptAloc" value="Confirm Department Allocation" class="submit"/>
              <input type="hidden" value="<%=info.getRportUrl()%>" name="rurl"/>
              <input type="hidden" value="<%=thruPer%>" name="thruper"/>
            </td>
            <!---<td>
              <input type="button" onclick="newwin('<%=info.getWebUrl()%>', '<%=cnt%>', '<%=thruPer%>');" value="Print Dept. Jangad" class="submit"/>
            </td>--->
          </tr>
        </table>
      </td></tr>
      </table>
    </html:form>
  <script language="Javascript">
  setTimeout('autoSubmit()', 500)
  </script>
  </td></tr>
  <%
  }
  else
  {
  %>
  <tr>
    <td>
      <table width="100%">
        <tr><td valign="top" class="tdLayout">Sorry No Data found </td></tr>
      </table>
    </td>
  </tr>
  <%
  }
  %>
   <tr>
    <td><jsp:include page="/footer.jsp" /> </td>
    </tr>
  </table>
  
  </body>
  
</html>

<script language="Javascript">
    //alert("end of page");
    /*var sub = false;
    sub = confirm("Do you want to process with Auto-Allocation?");
    
    if(sub==true)
    {
      //alert("auto submit");
      document.getElementById("myform").submit();
    }*/
</script>