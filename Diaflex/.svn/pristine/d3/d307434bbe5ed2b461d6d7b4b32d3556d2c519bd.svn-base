<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page errorPage="../error_page.jsp" %>
<%@ page contentType="text/html;charset=windows-1252"%>
<jsp:useBean id="info" class="ft.com.InfoMgr" scope="session" />
<jsp:useBean id="util" class="ft.com.JspUtil" scope="page" />

<%@ page import="java.util.ArrayList,java.util.HashMap,java.util.ArrayList, ft.com.assort.DeptAllocationForm"%>
<%@taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>

<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>SubDepartment Allocation</title>
    
    <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/bse.css?v=<%=info.getJsVersion()%>"/>
    <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/general.css?v=<%=info.getJsVersion()%>"/>
    <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/style.css?v=<%=info.getJsVersion()%>"/>
    <script src="../scripts/bse.js" type="text/javascript"></script>
    <script src="../scripts/ajax.js" type="text/javascript"></script>
  </head>
  <%
     //util.updAccessLog(request,response,"SubDepartment Allocation", "SubDepartment Allocation");
   String logId=String.valueOf(info.getLogId());
        String onfocus="cook('"+logId+"')";
        %>
 <body onfocus="<%=onfocus%>" topmargin="0" leftmargin="0" rightmargin="0" bottommargin="0">
    <table width="100%"  class="dataTable" >
      <tr> <td colspan="15">Dept Allocation Memo Size wise</td> </tr>
        <tr>
          <th>Sr. No.</th>
          <th>Date</th>
          <th>Memo</th>
          <th>Total Pkt</th>        
          <th>Cts</th>
          <th>Size</th>
          <!---<th>Person</th>--->
        </tr>        
        
        <%
        HashMap deptAloc =  (HashMap)request.getAttribute("deptAloc");
        ArrayList arrdept = (ArrayList) request.getAttribute("arrdept"); 
        
        HashMap deptEmpList = (HashMap) info.getHtdeptEmpRel();
        
        for(int i=0; i<arrdept.size(); i++)
        {
          String dept = request.getAttribute("dept").toString();
          String subdept = arrdept.get(i).toString();
          HashMap temp = (HashMap) deptAloc.get(subdept); 
          
          DeptAllocationForm deptAllocationForm = (DeptAllocationForm)request.getAttribute("form");
          deptAllocationForm.setEmpList((ArrayList) deptEmpList.get(dept));
          
          //DeptAllocationForm deptAllocationForm = (DeptAllocationForm)request.getAttribute("form");
          //deptAllocationForm.setSubDeptempList((ArrayList) deptEmpList.get(dept));
        %>
        
          <tr>
          <td><%=i+1%></td>
          <td></td>
          <td><%=temp.get("memo")%></td>
          <td><%=temp.get("pcs")%></td>
          <td><%=temp.get("cts")%></td>
          <td><%=temp.get("subdept")%></td>
          
          <!---<td>
            <html:select style="width:150px;" property="value(<%=subdept%>)"  styleId="<%=subdept%>_subdept" name="deptAllocationForm" >
            <html:option value="0" >--select--</html:option>
              <html:optionsCollection property="empList" name="deptAllocationForm" 
              label="emp_nme" value="emp_idn" />
          </html:select>
          </td>--->
          </tr>
        
        <%
        }
        %>
        
      </table>
    </body>
</html>