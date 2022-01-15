<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=windows-1252"%>
<jsp:useBean id="info" class="ft.com.InfoMgr" scope="session" />
<jsp:useBean id="util" class="ft.com.DBUtil" scope="session" />
<jsp:useBean id="impl" class="ft.com.lab.LabIssueImpl" scope="session" />
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
      <script src="../scripts/hkajax.js" type="text/javascript"></script>
      
    <script language="Javascript">
    function check(type) {
    //alert(type);
      var div = document.getElementById("NEW");
      div.style.display='none';
      div = document.getElementById("REP");
      div.style.display='none';
      div = document.getElementById("REVIEW");
      div.style.display='none';
      
      var ele = document.getElementById(type);
      
      ele.style.display='';
      
      //clear all other divs 
      var sizewise = document.getElementById("sizewise");
      sizewise.style.display='none';
      
      var pktwise = document.getElementById("pktwise");
      pktwise.style.display='none';
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
      else {
        alert("Please select a stone for Verification.");
        return false;
      }
        
    }
    </script>
      
  </head>
        <%String logId=String.valueOf(info.getLogId());
        String onfocus="cook('"+logId+"')";
        %>
 <body onfocus="<%=onfocus%>" onload="MM_preloadImages('images/faunalogo.png')" topmargin="0" leftmargin="0" rightmargin="0" bottommargin="0">

<%
 util.updAccessLog(request,response,"Verification", "Verification");
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
  String empMsg = "";
  if(request.getAttribute("empMsg") != null)
  {
    empMsg = request.getAttribute("empMsg").toString();
  }
  
  if(alocationDone.equals("true"))
  //if(alocationDone != null)
  {
    
  %>
    <script language="Javascript">
    alert("Dept Allocation is done. <%=empMsg %>" );
    </script>
  <%
  }
}
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
      
      //adding grand total to all the tables
      int gttl18dpc = 0;
      double gttl18dct = 0;
      int gttl18mpc = 0;
      double gttl18mct = 0;
      int gttl18gpc = 0;
      double gttl18gct = 0;
      int gttl96ugpc = 0;
      double gttl96ugct = 0;
      int gttl96umpc = 0;
      double gttl96umct = 0;
      int gttlpc = 0;
      double gttlct = 0;
          
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
            <td><%=size%></td><td><%=(double) Math.round(crtwt * 1000)/1000%></td>
          <%
            ttlpc += size;
            ttlcts += crtwt;
            
            if(i==0)
            {
              gttl18dpc += size;
              gttl18dct += crtwt;
            }
            else if(i==1)
            {
              gttl18mpc += size;
              gttl18mct += crtwt;
            }
            else if(i==2)
            {
              gttl18gpc += size;
              gttl18gct += crtwt;
            }
            else if(i==3)
            {
              gttl96ugpc += size;
              gttl96ugct += crtwt;
            }
            else if(i==4)
            {
              gttl96umpc += size;
              gttl96umct += crtwt;
            }
          }
          
          gttlpc += ttlpc;
          gttlct += ttlcts;
        %>
        <td><%=ttlpc%></td><td><%=(double) Math.round(ttlcts * 1000)/1000%></td>
        
      </tr>
      <%
      }
      %>
      
      <tr style="font-weight:bold;">
          <td>Grand Total</td>
          <td><%=gttl18dpc%></td>
          <td><%=gttl18dct%></td>
          <td><%=gttl18mpc%></td>
          <td><%=gttl18mct%></td>
          <td><%=gttl18gpc%></td>
          <td><%=gttl18gct%></td>
          <td><%=gttl96ugpc%></td>
          <td><%=gttl96ugct%></td>
          <td><%=gttl96umpc%></td>
          <td><%=gttl96umct%></td>
          <td><%=gttlpc%></td>
          <td><%=(double) Math.round(gttlct * 1000)/1000%></td>
          
        </tr>
    </table>
  </td></tr>
  
  <tr><td>&nbsp;</td></tr>
  <tr><td>&nbsp;</td></tr>
  
  <tr><td valign="top" class="tdLayout">
    <table width="20%" >
    <tr align="left">
    <td width="10%">Type</td>
    <td width="10%">
      <select id="type" onchange="check(this.value)"> 
        <option value="NEW">New</option>
        <option value="REP">Repairing</option>
        <option value="REVIEW">Review</option>               
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
      typeName = "NEW";
      style = "display:'';";
    }
    else if(type==1)
    {  
      memo = (ArrayList) request.getAttribute("repairmemo");
      typeName = "REP";
      style = "display:none;";
    }
    else if(type==2)
    {
      memo = (ArrayList) request.getAttribute("reviewmemo");
      typeName = "REVIEW";
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
        
        gttl18dct = 0;
        gttl18mct = 0;
        gttl18gpc = 0;
        gttl18gct = 0;
        gttl96ugpc = 0;
        gttl96ugct = 0;
        gttl96umpc = 0;
        gttl96umct = 0;
        gttlpc = 0;
        gttlct = 0;
        
        for(int i=0; i<memo.size(); i++)
        {
          memono = memo.get(i).toString();
          vals = (HashMap)htmemo.get(memono + "_" + typeName);
          
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
          
          //calculate grand total
          gttl18dct += Double.parseDouble(ct18d);
          gttl18mct += Double.parseDouble(ct18mix);
          gttl18gpc += Integer.parseInt(pc18gia);
          gttl18gct += Double.parseDouble(ct18gia);
          gttl96ugpc += Integer.parseInt(pc96gia);
          gttl96ugct += Double.parseDouble(ct96gia);
          gttl96umpc += Integer.parseInt(pc96mix);
          gttl96umct += Double.parseDouble(ct96mix);
          
          int ttlpcs = Integer.parseInt(pc18d) + Integer.parseInt(pc18mix) + 
          Integer.parseInt(pc18gia) + Integer.parseInt(pc96gia) + Integer.parseInt(pc96mix);
          
          double ttlcts = Double.parseDouble(ct18d) + Double.parseDouble(ct18mix) + 
          Double.parseDouble(ct18gia) + Double.parseDouble(ct96gia) + Double.parseDouble(ct96mix);
          
          gttlpc += ttlpcs;
          gttlct += ttlcts;
          
          %>
          <tr>
          <td><input value="<%=memono%>" type="checkbox" name="memowisechk" id="<%=memono%>_memowisechk"/></td>
          <td></td>
          <td><a onclick="showSizeWise('<%=typeName%>','<%=memono%>', '');"><%=memono%></a></td>
          <!--<td><div style="text-decoration:underline; color:blue;" onclick="showSizeWise('<%=typeName%>','<%=memono%>');"><%=memono%></div></td>--->
          <td></td>
          <td><a onclick="showSizeWise('<%=typeName%>','<%=memono%>', '18-DN');"><%=ct18d%></a></td>
          <td><a onclick="showSizeWise('<%=typeName%>','<%=memono%>', '18-96-MIX');"><%=ct18mix%></a></td>
          <td><a onclick="showSizeWise('<%=typeName%>','<%=memono%>', '18-96-GIA');"><%=pc18gia%></a></td>
          <td><a onclick="showSizeWise('<%=typeName%>','<%=memono%>', '18-96-GIA');"><%=ct18gia%></a></td>
          <td><a onclick="showSizeWise('<%=typeName%>','<%=memono%>', '96-UP-GIA');"><%=pc96gia%></a></td>
          <td><a onclick="showSizeWise('<%=typeName%>','<%=memono%>', '96-UP-GIA');"><%=ct96gia%></a></td>
          <td><a onclick="showSizeWise('<%=typeName%>','<%=memono%>', '96-UP-MIX');"><%=pc96mix%></a></td>
          <td><a onclick="showSizeWise('<%=typeName%>','<%=memono%>', '96-UP-MIX');"><%=ct96mix%></a></td>
          <td><%=ttlpcs%></a></td><td><%=(double) Math.round(ttlcts * 1000)/1000%></a></td>
          <td></td>
          </tr>
        <%
        }
        %>
        
        <tr style="font-weight:bold;">
          <td></td>
          <td></td>
          <td></td>
          <td>Grand Total</td>
          <td><%=(double) Math.round(gttl18dct * 1000)/1000%></td>
          <td><%=(double) Math.round(gttl18mct * 1000)/1000%></td>
          <td><%=gttl18gpc%></td>
          <td><%=(double) Math.round(gttl18gct * 1000)/1000%></td>
          <td><%=gttl96ugpc%></td>
          <td><%=(double) Math.round(gttl96ugct * 1000)/1000%></td>
          <td><%=gttl96umpc%></td>
          <td><%=(double) Math.round(gttl96umct * 1000)/1000%></td>
          <td><%=gttlpc%></td>
          <td><%=(double) Math.round(gttlct * 1000)/1000%></td>
          
        </tr>
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