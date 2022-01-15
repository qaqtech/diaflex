<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page errorPage="../error_page.jsp" %>
<%@ page contentType="text/html;charset=windows-1252"%>
<jsp:useBean id="info" class="ft.com.InfoMgr" scope="session" />
<jsp:useBean id="util" class="ft.com.JspUtil" scope="page" />
<%@ page import="java.util.ArrayList,java.util.HashMap,java.sql.ResultSet, java.util.Date, java.util.ArrayList,
java.text.DecimalFormat, java.util.HashMap, ft.com.dao.*"%>
<%@taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>Assort Selection</title>
    <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/bse.css?v=<%=info.getJsVersion()%>"/>
    <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/general.css?v=<%=info.getJsVersion()%>"/>
     <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/style.css?v=<%=info.getJsVersion()%>"/>
      <script src="../scripts/bse.js" type="text/javascript"></script>
      <script src="../scripts/ajax.js" type="text/javascript"></script>
      
    <script language="Javascript">
    function check(type) {
    //alert(type);
      var div = document.getElementById("new");
      div.style.display='none';
      div = document.getElementById("repair");
      div.style.display='none';
      div = document.getElementById("review");
      div.style.display='none';
      
      var ele = document.getElementById(type);
      
      ele.style.display='';
      
    }
    function newwin(wurl, cnt, thruPer) 
    {
      //alert(url);
      var rurl = document.getElementById("rurl").value;
      //alert(rurl);
      var test = wurl+"/reports/rwservlet?"+cnt+"&report="+rurl+"\\reports\\assort_memo_salesdpt.jsp&p_thru_nme="+thruPer;
      //alert(test);
      window.open(test);
    }
    
    function CheckAllChkbox(id, fldNme)
    {
    //alert(id);
    //alert(fldNme);
      var check =document.getElementById(id).checked;
      var frm_elements = document.forms['verification'].elements;
      
      for(i=0; i<frm_elements.length; i++) 
      {
        field_type = frm_elements[i].type.toLowerCase();
        fldName = frm_elements[i].name;
        //alert(fldName);
        
        if(field_type=='checkbox' && fldName.indexOf(fldNme)!= -1) 
        {          
          frm_elements[i].checked = check;
        }
      }
    }
    
    function chkSubmit() {
      var frm_elements = document.forms['verification'].elements;
      var flag = false;
      for(i=0; i<frm_elements.length; i++) 
      {
        field_type = frm_elements[i].type.toLowerCase();
        
        if(field_type=='checkbox' && frm_elements[i].checked) 
        {          
          flag = frm_elements[i].checked;
          break;
        }
      }
      //alert(flag);
      if(flag)
        return true;
      else 
        return false;
    }
    </script>
      
  </head>
   <%String logId=String.valueOf(info.getLogId());
        String onfocus="cook('"+logId+"')";
        %>
 <body onfocus="<%=onfocus%>" onload="MM_preloadImages('images/faunalogo.png')" topmargin="0" leftmargin="0" rightmargin="0" bottommargin="0">

<%
if(request.getAttribute("submitDeptAloc") != null)
{
  String rurl = request.getParameter("rurl").toString();
  String thruper = request.getParameter("thruper").toString();
  
  HashMap dbinfo = info.getDmbsInfoLst();
  String cnt = (String)dbinfo.get("CNT");
  
  //String url = info.getWebUrl()+"/reports/rwservlet?"+cnt+"&report="+info.getRportUrl()+"\\reports\\assort_memo_salesdpt.jsp&p_thru_nme="+thruper;
  %>
  <form name="tempForm" >
    <input type="hidden" id="rurl" value="<%=rurl%>" />
    <script language="Javascript">
      var conf = confirm("Do you want to print Jangad?");
      if(conf==true)
        newwin('<%=info.getWebUrl()%>', '<%=cnt%>', '<%=thruper%>');
    </script>
  </form>
  <%
  
}

if(request.getAttribute("alocationDone") != null)
{
  String alocationDone = request.getAttribute("alocationDone").toString();
  
  if(alocationDone.equals("true"))
  //if(alocationDone != null)
  {
  %>
    <script language="Javascript">
    alert("Dept Allocation is done.");
    </script>
  <%
  }
}
//util.updAccessLog(request,response,"Verification", "Verification");
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
  <img src="../images/line.jpg" border="0" /></td><td valign="middle"> <span class="pgHed">Verification</span> </td>
</tr></table>
  </td>
  </tr>
  <tr><td><form action="Verification.do" name="verification" onsubmit="return chkSubmit();"><table width="100%">
  
  <tr><td valign="top" class="tdLayout">
    <table width="70%">
      <tr> <td><input type="hidden" name="method" value="processForm"/></td></tr>
    </table>
  </td></tr>
  
  <tr><td valign="top" class="tdLayout">
    <table width="70%"  class="dataTable" >
    <tr> <td colspan="13">Stock Type Summary</td> </tr>
      <tr>
        <th>Type</th>
        <th colspan="2">.18 Down</th>
        <th colspan="2">18-96 MIX</th>
        <th colspan="2">18-96 GIA</th>
        <th colspan="2">0.96UP GIA</th>
        <th colspan="2">0.96 UP MIX</th>
        <th colspan="2">Total</th>
      </tr>
      <tr>
        <th></th>
        <th>Pcs</th><th>Cts</th>
        <th>Pcs</th><th>Cts</th>
        <th>Pcs</th><th>Cts</th>
        <th>Pcs</th><th>Cts</th>
        <th>Pcs</th><th>Cts</th>
        <th>Pcs</th><th>Cts</th>        
      </tr>
      
      <%
      HashMap vals = new HashMap();
          
      // loop thru all 3 types
      for(int type=0; type<3; type++)
      {
        String typeName = "";
        HashMap newtab = new HashMap();
        if(type==0)
        {
          newtab = (HashMap) request.getAttribute("newtab");
          typeName = "New";
        }
        else if(type==1)
        {  
          newtab = (HashMap) request.getAttribute("repair");
          typeName = "Repairing";
        }
        else if(type==2)
        {
          newtab = (HashMap) request.getAttribute("review");
          typeName = "Review";
        }
      %>
      <tr>
        <td><%=typeName%></td>
        <%
          
          int ttlpc = 0;
          double ttlcts = 0;
          
          for(int i=0; i<5; i++)
          {
            if(i==0)
              vals = (HashMap) newtab.get("18-DN");
            else if(i==1)
              vals = (HashMap) newtab.get("18-96-MIX");
            else if(i==2)
              vals = (HashMap) newtab.get("18-96-GIA");
            else if(i==3)
              vals = (HashMap) newtab.get("96-UP-GIA");
            else if(i==4)
              vals = (HashMap) newtab.get("96-UP-MIX");
            
            double crtwt = 0.0;
            int size = 0;
            
            if(vals != null)
            {
              crtwt = Double.parseDouble(vals.get("CTS").toString());
              size = Integer.parseInt(vals.get("PCS").toString());
              
            }
            else
            {
              crtwt = 0.0;
              size = 0;
            }
          %>
            <td><%=size%></td><td><%=String.valueOf(crtwt)%></td>
          <%
          ttlpc += size;
          ttlcts += crtwt;
          }
        
        %>
        <td><%=ttlpc%></td><td><%=String.valueOf(ttlcts)%></td>
        
      </tr>
      <%
      }
      %>
            
    </table>
  </td></tr>
  
  <tr><td>&nbsp;</td></tr>
  <tr><td>&nbsp;</td></tr>
  
  <tr><td valign="top" class="tdLayout">
    <table width="70%"  class="dataTable" >
    <tr>
    <td>Type</td>
    <td>
      <select id="type" onchange="check(this.value)"> 
        <option value="new">New</option>
        <option value="repair">Repairing</option>
        <option value="review">Review</option>               
      </select>
    </td>
    </tr>
    </table>
  </td></tr>
  
  <tr><td>&nbsp;</td></tr>
  <tr><td>&nbsp;</td></tr>
  
  <tr><td valign="top" class="tdLayout">
  <%
  HashMap htmemo = (HashMap)request.getAttribute("htmemo");
        
  for(int type=0; type<3; type++)
  {
    String typeName = "";
    String style = "display:none;";
    ArrayList memo = new ArrayList();    
        
    if(type==0)
    {
      memo = (ArrayList)request.getAttribute("newmemo");
      typeName = "new";
      style = "display:'';";
    }
    else if(type==1)
    {  
      memo = (ArrayList) request.getAttribute("repairmemo");
      typeName = "repair";
      style = "display:none;";
    }
    else if(type==2)
    {
      memo = (ArrayList) request.getAttribute("reviewmemo");
      typeName = "review";
      style = "display:none;";
    }
      
  %>
    <div id="<%=typeName%>" style="<%=style%>" >
      <table width="70%"  class="dataTable" >
      <tr> <td colspan="15">Memo Wise Details</td> </tr>
        <tr>
          <th rowspan="2" style="vertical-align:top">Sr. No. <input name="ALLMemoWise" id="ALLMemoWise" type="checkbox" onclick="CheckAllChkbox(this.id, 'memowisechk')" /></th>
          <th rowspan="2" style="vertical-align:top">Date</th>
          <th rowspan="2" style="vertical-align:top">Memo</th>
          <th rowspan="2" style="vertical-align:top">Old Carats</th>        
          <th>.18 Down</th>
          <th>18-96 MIX</th>
          <th colspan="2">18-96 GIA</th>
          <th colspan="2">0.96UP GIA</th>
          <th colspan="2">0.96 UP MIX</th>
          <th colspan="2">Total</th>
          <th rowspan="2" style="vertical-align:top">Status</th>
        </tr>
        <tr>
          
          <th>Carats</th>
          <th>Carats</th>
          <th>Pcs</th><th>Cts</th>
          <th>Pcs</th><th>Cts</th>
          <th>Pcs</th><th>Cts</th>
          <th>Pcs</th><th>Cts</th>        
        </tr>
        
        <%
        //logic to group by memo.
        
        String memono = "";
        HashMap intvals = new HashMap();
        vals = new HashMap();
        String tempid = "";
        
        for(int i=0; i<memo.size(); i++)
        {
          memono = memo.get(i).toString();
          vals = (HashMap)htmemo.get(memono);
          
          String pc18d, pc18mix, pc18gia, pc96gia, pc96mix;
          String ct18d, ct18mix, ct18gia, ct96gia, ct96mix;
          
          if(vals.get("18-DN") != null)
          {        
            intvals = (HashMap)vals.get("18-DN");        
            pc18d =   intvals.get("PCS").toString();
            ct18d =   intvals.get("CTS").toString();
          }
          else
          {
            pc18d =   "0";
            ct18d =   "0";
          }
          
          if(vals.get("18-96-MIX") != null)
          {        
            intvals = (HashMap)vals.get("18-96-MIX");
            pc18mix = intvals.get("PCS").toString();
            ct18mix = intvals.get("CTS").toString();
          }
          else
          {
            pc18mix =   "0";
            ct18mix =   "0";
          }
          
          if(vals.get("18-96-GIA") != null)
          {        
            intvals = (HashMap)vals.get("18-96-GIA");
            pc18gia = intvals.get("PCS").toString();
            ct18gia = intvals.get("CTS").toString();
          }
          else
          {
            pc18gia =   "0";
            ct18gia =   "0";
          }
          
          if(vals.get("96-UP-GIA") != null)
          {        
            intvals = (HashMap)vals.get("96-UP-GIA");
            pc96gia = intvals.get("PCS").toString();
            ct96gia = intvals.get("CTS").toString();
          }
          else
          {
            pc96gia =   "0";
            ct96gia =   "0";
          }
          
          if(vals.get("96-UP-MIX") != null)
          {        
            intvals = (HashMap)vals.get("96-UP-MIX");
            pc96mix = intvals.get("PCS").toString();
            ct96mix = intvals.get("CTS").toString();
          }
          else
          {
            pc96mix =   "0";
            ct96mix =   "0";
          }        
          
          int ttlpcs = Integer.parseInt(pc18d) + Integer.parseInt(pc18mix) + 
          Integer.parseInt(pc18gia) + Integer.parseInt(pc96gia) + Integer.parseInt(pc96mix);
          
          double ttlcts = Double.parseDouble(ct18d) + Double.parseDouble(ct18mix) + 
          Double.parseDouble(ct18gia) + Double.parseDouble(ct96gia) + Double.parseDouble(ct96mix);
          
          %>
          <tr>
          <td><input value="<%=memono%>" type="checkbox" name="memowisechk" id="<%=memono%>_memowisechk"/></td>
          <td></td>
          <td><a onclick="showSizeWise('<%=typeName%>','<%=memono%>');"><%=memono%></a></td>
          <!--<td><div style="text-decoration:underline; color:blue;" onclick="showSizeWise('<%=typeName%>','<%=memono%>');"><%=memono%></div></td>--->
          <td></td>
          <td><%=ct18d%></td>
          <td><%=ct18mix%></td>
          <td><%=pc18gia%></td><td> <%=ct18gia%></td>
          <td><%=pc96gia%></td><td> <%=ct96gia%></td>
          <td><%=pc96mix%></td><td> <%=ct96mix%></td>
          <td><%=ttlpcs%></td><td> <%=(double) Math.round(ttlcts * 1000)/1000%></td>
          <td></td>
          </tr>
        <%
        }
        %>
        
      </table>
    </div>
  <%
  }
  %>
  </td></tr>
  
  <tr><td>&nbsp;</td></tr>
  <tr><td>&nbsp;</td></tr>
  
  <tr><td valign="top" class="tdLayout">
  <div id="sizewise" style="display:none;" >
    <table width="70%"  class="dataTable" id="dbtable" >
      <!---<tr> <td colspan="7">Size Wise Detail of Memo No</td> </tr>
      <tr>
        <th>Sr. No. [ ? ]</th>
        <th>Date</th>
        <th>Size</th>
        <th>TotalPkt</th>
        <th>Cts</th>
        <th>Verification fail</th>
        <th>Fail Remark</th>        
      </tr>
      <div id="dbtablenewrows">
      </div>--->
    </table>
  </div>
  </td></tr>
  
  <tr><td>&nbsp;</td></tr>
  <tr><td>&nbsp;</td></tr>
  
  <tr><td valign="top" class="tdLayout">
  <div id="pktwise" style="display:none;" >
    <table width="70%" id="pkttable"  class="dataTable" >
      <tr> <td colspan="9">Pkt Wise Detail of Memo No</td> </tr>
      <tr>
        <th>Sr. No.</th>
        <th>Date</th>
        <th>Memo No.</th>
        <th>Pkt No.</th>
        <th>Cts</th>
        <th>Dept.</th>
        <th>Size</th>
        <th>Verification fail</th>
        <th>Fail Remark</th>
      </tr>
      
      <!---<tr>
        <td><input type="checkbox"/></td>
        <td></td>
        <td></td>
        <td></td>
        <td></td>
        <td></td>
        <td></td>
        <td></td>
        <td></td>        
      </tr>--->
    </table>
  </div>
  </td></tr>
  
  <tr><td valign="top" class="tdLayout">
    <table width="70%" class="dataTable" >
      <tr><td><input type="submit" value="Proceed Verification" class="submit"/></td></tr>
    </table>
  </td></tr>
  </table></form></td></tr>
   <tr>
    <td><jsp:include page="/footer.jsp" /> </td>
    </tr>
  </table>
  </body>
</html>