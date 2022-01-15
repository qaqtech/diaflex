 <!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page errorPage="error_page.jsp" %>
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import="java.util.ArrayList,java.util.ArrayList,java.util.HashMap,javax.servlet.http.Cookie,ft.com.*" %>
<jsp:useBean id="util" class="ft.com.JspUtil" scope="page" />
<jsp:useBean id="info" class="ft.com.InfoMgr" scope="session" />
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>DiaFlex : Home</title>
    <link type="text/css" rel="stylesheet" href="css/bse.css?v=1"/>
     <link type="text/css" rel="stylesheet" href="css/style.css?v=1"/>
     <script type="text/javascript" src="scripts/bse.js?v=1" ></script>
     <link type="text/css" rel="stylesheet" href="css/general.css?v=1"/>
         <script language="javaScript" type="text/javascript" src="scripts/ajax.js?v=1"></script>
    <script type="text/javascript" src="PopScripts/popup.js?v=1 " > </script>
     <script src="PopScripts/jquery-1.2.6.min.js?v=1" type="text/javascript"></script>
     <script type="text/javascript" src="jqueryScript/jquery.marquee.js" > </script>
<script type="text/javascript">
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
     
    document.getElementById('lastRef').innerHTML=currDateTm;
    //document.getElementById('nextRef').innerHTML=nextTm;
    //document.getElementById('intText').value =intTextval;
 
    }
</script>
  </head>
 <%
             HashMap dbinfo = info.getDmbsInfoLst();
             int port=request.getServerPort();
             String servPath = "http://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath();
            if(port==443)
             servPath = "https://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath();

             info.setReqUrl(servPath); 
             String url=util.nvl((String)dbinfo.get("DIAFLEX_URL"));
             if(servPath.indexOf("kapu") > -1){
             url="http://kapu.faunacloud.com/diaflex/index.jsp";
             dbinfo.put("REP_PATH","http://kapu.faunacloud.com");
             dbinfo.put("REP_URL","http://kapu.faunacloud.com");
             }
             DBUtil dbutil = new DBUtil();
                DBMgr db = new DBMgr(); 
                db.setCon(info.getCon());
                dbutil.setDb(db);
                dbutil.setInfo(info);
                db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));db.setDbUsr(info.getDbUsr());
                dbutil.setLogApplNm(info.getLoApplNm());
                dbutil.updAccessLog(request,response,"Login url", info.getReqUrl());
                dbutil.getheadermsg();
             Cookie cookie = new Cookie ("APPURL",url);
             cookie.setMaxAge(365 * 24 * 60 * 60);
             response.addCookie(cookie);
             String jsVersion=util.nvl((String)dbinfo.get("JSCSSVERSION"));
             info.setJsVersion(jsVersion);
        String dashboardon =util.nvl((String)dbinfo.get("DASHBOARDON"));
        String diaflexMode = util.nvl((String)dbinfo.get("DIAFLEX_MODE"));
        String logId=String.valueOf(info.getLogId());
        String msgheader = util.nvl((String)info.getMsgheader());
        String onfocus="cook('"+logId+"')";
        int userId = info.getUsrId();
        String dashbordurl= util.nvl((String)info.getDmbsInfoLst().get("dashboard"));
       dashbordurl=dashbordurl.replace(":userId",String.valueOf(userId));
        %>
 <body onfocus="<%=onfocus%>" onload="MM_preloadImages('images/faunalogo.png');setTimeLable()" topmargin="0" leftmargin="0" rightmargin="0" bottommargin="0">
<input type="hidden" id="reqUrl" name="reqUrl" value="<%=info.getReqUrl()%>"/> 
<div id="popupDashPOP">
<table class="popUpTb">
 <tr><td height="5%">&nbsp;&nbsp;&nbsp;
 <button type="button" onclick="MKEmptyPopup('SC');disablePopupDashPOP();" class="submit">Close</button></td></tr>
<tr><td valign="top" height="95%">
<iframe name="SC" class="frameStyle" width="500px" height="500px"   align="middle" frameborder="0">
<jsp:include page="/emptyPg.jsp" />
</iframe></td></tr>
</table>
</div>
<table width="100%"  border="0" align="center" cellpadding="0" cellspacing="0" class="mainbg">
  <tr>
    <td height="103" valign="top">
   <jsp:include page="/header.jsp" />
    
    </td>
  </tr>
 
  <tr>
    <td height="5" valign="top" class="greyline"><img src="images/grey_linebig.png" width="685" height="5" align="right" /></td>
  </tr>
  <tr>
  <td align="center"><marquee scrollamount="5"  class="marquee-with-options" behavior="scroll" direction="left" onmouseover="this.stop();" onmouseout="this.start();"><span id="headermsginterval"><%=msgheader%></span></marquee></td>
  </tr>
  <%if(dashbordurl.length()>4){%>
    <tr>
  <td valign="top" class="hedPg">
  <table cellpadding="1" cellspacing="5"><tr><td valign="middle">
  <img src="images/line.jpg" border="0" /></td><td valign="middle"> <span class="pgHed">Dashboard</span>&nbsp;&nbsp;&nbsp;<a href="javascript:void(0)"  ><img src="images/refresh.gif" /></a>
  &nbsp;&nbsp;&nbsp;<label id="lastRef"/>
  </td>
</tr></table>
  </td>
  </tr>
     <tr><td valign="top" class="hedPg">
    
     <iframe src="<%=dashbordurl%>" width="99%" height="900px" frameborder="0" id="iframeId"  onload="setTimeLable(); hidePopUp(this)" >
     
     </iframe>
      </td></tr>
      <%}%>
  <!--<%if(!diaflexMode.equalsIgnoreCase("SECURE")){%>
  <tr>
  <%
  ArrayList pageLst=new ArrayList();
  ArrayList list= new ArrayList();
  if(dashboardon.equals("Y")){
  pageLst= (info.getDashboardvisibility() == null)?new ArrayList():(ArrayList)info.getDashboardvisibility();
  }
  if(pageLst!=null && pageLst.size()>0){
  %>
  <td class="tdLayout">
  <div style="padding-top:5px;">
  Visibility On 
  <select id="visibility" onchange="saveUserVisibility('/home.do?method=loadhome')">
  <option value="0">Select</option>
  <%for(int i=0;i<pageLst.size();i++){
  list= new ArrayList();
  list=(ArrayList)pageLst.get(i);%>
  
  <option value="<%=util.nvl((String)list.get(1))%>"><%=util.nvl((String)list.get(0))%></option>
  <%}%>
  </select>
  </div>
  </td>
  <%}%>
  </tr>
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
    int sr=0;
    int id=0;
    %>
  <tr>
    <td>
    <table width="90%" cellspacing="10">
    <tr id="DTL_<%=sr%>" >
    <%
//    String sleep=util.nvl((String)dbsysInfo.get("SLEEP"));
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
      sr++;
      if(sr==4){
      sr=1;
      id++;
      %>
      </tr><tr  id="DTL_<%=id%>">
      <%}%>
    <td valign="top" class="hedboardPg" width="33%" align="left">
    <div style="float:left; position: relative; width:100%;" onmouseover="displayover('<%=lov_qry%>');" onmouseout="displayout('<%=lov_qry%>')">
    <div style="float:left; width:98%; position: absolute; padding: 0px 5px 5px 0px; margin-top: 2px; display:none;" id="<%=lov_qry%>">
    <div style="float:left; margin-left:10px;"><b></b></div>
    <div style="float:right;">
    <img border="0" title="Click Here To Refresh" onclick="refreshdash('<%=lov_qry%>_Frame')" src="images/refresh.jpg"  width="16px" height="16px">
    <a href="<%=dflt_val%>&dtl=Y"  target="SC" title="Click Here To See Details" onclick="loadDashPOP('<%=id%>','DTL_<%=id%>')">
    <img border="0" title="Click Here To See Datails" src="images/magicon.png" width="16px" height="16px"></a>&nbsp;
    <img border="0" title="Click Here To Hide" onclick="saveUserRightallow('<%=pgitmidn%>','N','/home.do?method=loadhome')" src="images/cross.png">
    </div>
    </div>
    <div style="float:left; width:100%;">
    <iframe src="<%=dflt_val%>"  style="height:170px; width:100%;" name="<%=lov_qry%>_Frame" id="<%=lov_qry%>_Frame" class="iframe" frameborder="0">
<jsp:include page="/emptyPg.jsp" />
    </iframe>
    </div>
    </div>
    </td>
    <%}}%>
    </tr>
    </table>
    </td>
  </tr>
  <%}%>-->
</table>

</body>
</html>