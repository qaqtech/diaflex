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
    <title>Dash Board</title>
  <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/bse.css?v=<%=info.getJsVersion()%>"/>
    <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/style.css?v=<%=info.getJsVersion()%>"/>
    <script type="text/javascript" src="<%=info.getReqUrl()%>/PopScripts/popup.js?v=<%=info.getJsVersion()%> " > </script>
     <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/bse.js?v=<%=info.getJsVersion()%> " > </script>
     <script language="javaScript" type="text/javascript" src="<%=info.getReqUrl()%>/calendar/calendar.js"></script>
   <link href="<%=info.getReqUrl()%>/calendar/calendar.css" rel="stylesheet" type="text/css" />
    <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/general.css?v=<%=info.getJsVersion()%>"/>
    <script src="<%=info.getReqUrl()%>/PopScripts/jquery-1.2.6.min.js" type="text/javascript"></script>
             <script language="javaScript" type="text/javascript" src="<%=info.getReqUrl()%>/scripts/ajax.js?v=1"></script>

 <script type=text/javascript><!--
 function settm(min){
   //document.getElementById('intText').value=min;  
   document.getElementById('iframe').height= "2250";
   document.getElementById('iframe').width= "1450";
 }
 function setUrl(obj,page){
   var url = obj.href;
  document.getElementById('url').value=url;
  document.getElementById('urlPage').value=page;
   //setInterval(function(){refresh()},parseInt(document.getElementById('intText').value)*(60*1000));
   setTimeLable();
  }
  function checkTimeIntval(obj){
    var val = obj.value;
    if(val < 1){
       obj.value=1;
       alert("Time Interval can't be less than 1 Minute..");
    }
  }
  function refresh() {
    var url =  document.getElementById('url').value;
  document.getElementById("iframe").src=url;
  setTimeLable();
	  }
   
   function changesTimeInterval(){
   refresh();
   //setInterval(function(){refresh()},parseInt(document.getElementById('intText').value)*(60*1000));
   }
 
    function setTimeLable(){
      //var intText =  document.getElementById('intText').value ;
      var currentdate = new Date(); 
      var nextcurrentdate = new Date();
      
       //var intTextval = 1;
       //if(intText!='' && intText!='0'){
       //   intTextval = intText ;
       //}
      //var intVal = parseInt(intTextval);
      //nextcurrentdate.setMinutes(nextcurrentdate.getMinutes() + intVal);
      var hrs= currentdate.getHours();
      var sec= currentdate.getSeconds();
      var min= currentdate.getMinutes();
      var nexthrs= nextcurrentdate.getHours();
      var nextsec= nextcurrentdate.getSeconds();
      var nextmin= nextcurrentdate.getMinutes();
      if(parseInt(hrs)<10) {
      hrs='0'+hrs;
      } 
      if(parseInt(sec)<10) {
      sec='0'+sec;
      }
      if(parseInt(min)<10) {
      min='0'+min;
      }
      if(parseInt(nexthrs)<10) {
      nexthrs='0'+nexthrs;
      } 
      if(parseInt(nextsec)<10) {
      nextsec='0'+nextsec;
      }
      if(parseInt(nextmin)<10) {
      nextmin='0'+nextmin;
      }
       var currDateTm = "Last Refresh Time: "  
                + hrs + ":"  
                + min + ":" 
                + sec;
      var nextTm = "Next Refresh Time: "  
                + nexthrs + ":"  
                + nextmin+ ":" 
                + nextsec;
    document.getElementById('lastRef').innerHTML=currDateTm;
    //document.getElementById('nextRef').innerHTML=nextTm;
    //document.getElementById('intText').value =intTextval;
 
    }
--></script>
 

</head>
        <%
        String logId=String.valueOf(info.getLogId());
        String onfocus="cook('"+logId+"')";
        HashMap dbinfo = info.getDmbsInfoLst();
      int port=request.getServerPort();
             String servPath = "http://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath();
            if(port==443)
             servPath = "https://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath();

             info.setReqUrl(servPath); 
        String url=util.nvl((String)dbinfo.get("DIAFLEX_URL"));
        String diaflexMode = util.nvl((String)dbinfo.get("DIAFLEX_MODE"));
        String dashboardon =util.nvl((String)dbinfo.get("DASHBOARDON"));
        String usrFlg=util.nvl((String)info.getUsrFlg());
String usr=info.getUsr().toUpperCase();
String client=util.nvl((String)info.getDmbsInfoLst().get("CNT"));
if(usr.equals("DEMO"))
client="FAUNA";

        %>
 <body onfocus="<%=onfocus%>" onload="MM_preloadImages('images/faunalogo.png');" topmargin="0" leftmargin="0" rightmargin="0" bottommargin="0">
 <input type="hidden" name="url" id="url" />
  <input type="hidden" name="urlPage" id="urlPage" />

 <input type="hidden" id="reqUrl" name="reqUrl" value="<%=info.getReqUrl()%>"/> 
 <img src="../images/loadbig.gif" vspace="15" id="load" style="display:none;" class="loadpktDiv" border="0" />
  <div id="backgroundPopup"></div>
<div id="popupContactSave" align="center" >
<img src="../images/loading_flame.gif" height="150px" width="150px" border="0" />
</div>
<table width="100%"  border="0" align="center" cellpadding="0" cellspacing="0" >
  <tr>
    <td height="50" valign="top">
  <table   border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td width="108"><img src="<%=info.getReqUrl()%>/images/diaflex_logo.png" width="108" height="59" hspace="40" /></td>
       
          <td valign="middle" height="103" width="140" align="left"> <span class="style1">Welcome | <%=session.getAttribute("logUSR")%> | <%=info.getLogId()%>
            <%if(usrFlg.equals("SYS")){%>
            | <%=client%>
            <%}%>
            </span></td> <td  valign="middle" height="103" align="left"></td>
        </tr></table>
    </td>
  </tr>
 <tr>
    <td height="5" valign="top" class="greyline"><img src="../images/grey_linebig.png" width="685" height="5" align="right" /></td>
  </tr>
 
  <tr>
  <td valign="top" class="hedPg">
  <%if(!diaflexMode.equalsIgnoreCase("S") || info.getUsrFlg().equals("SYS")){
  String firstUrl = "";
  %>
  <table width="100%">
  
  
 
  <tr>
  <td width="20%" valign="top">
  <table width="100%" class="menutable" id="menutable" cellpadding="1" cellspacing="1">
  <tr><td colspan="2">
   <%
  ArrayList pageLst=new ArrayList();
  ArrayList list= new ArrayList();
  if(dashboardon.equals("Y")){
  pageLst= (info.getDashboardvisibility() == null)?new ArrayList():(ArrayList)info.getDashboardvisibility();
  }
  if(pageLst!=null && pageLst.size()>0){
  %>
 <table><tr> <td nowrap="nowrap">
<b>
  Visibility On</b></td><td>
  <select id="visibility" onchange="saveUserVisibility('/dashboard/dashBoardHome.do?method=load')">
  <option value="0">Select</option>
  <%for(int i=0;i<pageLst.size();i++){
  list= new ArrayList();
  list=(ArrayList)pageLst.get(i);%>
  
  <option value="<%=util.nvl((String)list.get(1))%>"><%=util.nvl((String)list.get(0))%></option>
  <%}%>
  </select>
  
  </td></tr></table>
  <%}%>
 </td>
  </tr>
  <tr><th colspan="2">DASH BOARD</th></tr>
  <%
  HashMap pageDtl=new HashMap();
    if(dashboardon.equals("Y")){
    HashMap allPageDtl= (info.getPageDetails() == null)?new HashMap():(HashMap)info.getPageDetails();
    pageDtl=(HashMap)allPageDtl.get("DASH_BOARD");
    }
    ArrayList pageList=new ArrayList();
    pageList= ((ArrayList)pageDtl.get("FRAME") == null)?new ArrayList():(ArrayList)pageDtl.get("FRAME");
    HashMap pageDtlMap=new HashMap();
    String fld_nme="",fld_ttl="",val_cond="",dflt_val="",fld_typ="",form_nme="",flg1="",lov_qry="",pgitmidn="";
      if(pageList!=null && pageList.size() >0){
      for(int j=0;j<pageList.size();j++){
     pageDtlMap=(HashMap)pageList.get(j);
      pgitmidn=(String)pageDtlMap.get("pgitmidn");
      fld_nme=(String)pageDtlMap.get("fld_nme");
      fld_ttl=(String)pageDtlMap.get("fld_ttl");
      dflt_val=(String)pageDtlMap.get("dflt_val");
      val_cond=(String)pageDtlMap.get("val_cond");
      dflt_val=dflt_val.replaceAll("IDN",pgitmidn);
      dflt_val=dflt_val.replaceAll("HDR",fld_ttl);
      dflt_val=info.getReqUrl()+dflt_val.replaceAll("RFSH",val_cond);
      fld_typ=(String)pageDtlMap.get("fld_typ");
      lov_qry=(String)pageDtlMap.get("lov_qry");
      String dashId="dash_"+j;
      if(j==0){
      firstUrl=dflt_val;
      %>
       <input type="hidden" name="firsturl" id="firsturl" value="<%=firstUrl%>" />

      <%}%>
   <tr>
   <td width="80%" nowrap="nowrap">
   <a href="<%=dflt_val%>" onclick="<%=fld_typ%>;setUrl(this,'<%=fld_nme%>');setSelected('<%=dashId%>','menutable')" target="src"><%=fld_nme%></a>
   <span id="<%=dashId%>">
   <%if(j==0){%>
   &nbsp;<b>>></b>
   <%}%>
   </span></td>
   <td width="20%"><div onclick="saveUserRightallow('<%=pgitmidn%>','N','/dashboard/dashBoardHome.do?method=load')" style="font-weight: bold;color: Blue;cursor: pointer;">Hide</div></td></tr>
   <%}}%>
</table>
  </td>
  <td width="80%" valign="top">
  <table width="100%"><tr><td> 
  <table cellpadding="1" cellspacing="1"><tr><td><label id="lastRef" style="font-weight: bold;color:red;"></label> </td>
  <!--<td><label id="nextRef" style="font-weight: bold;color: Blue;"></label></td><td><b>Time Interval:</b>&nbsp; <input type="text" name="tmIntval" size="5" value="4" id="intText" onchange="checkTimeIntval(this)" class="txtStyle"  />&nbsp; <b>Minute</b></td>
  <td> <input type="button" value="Set Time" onclick="changesTimeInterval()" class="submit"/></td>-->
  <td> <input type="button" value="Refresh" onclick="changesTimeInterval()" class="submit"/></td>
  
  </tr></table>
  
  </td></tr>
  
  <tr><td>
<iframe name="src"  height="600" width="100%" id="iframe" frameborder="0">
<jsp:include page="/emptyPg.jsp" />
    </iframe>
    </td></tr></table>
  
  </td></tr>
  </table>
  <%}%>
  </td></tr>
  
  </table>
<script type="text/javascript">
 <!--
    var framesrc = document.getElementById("iframe").src;
    if(framesrc==''){
        document.getElementById("url").value=document.getElementById("firsturl").value;
   }
   changesTimeInterval();
 -->
 </script>
 </body></html>