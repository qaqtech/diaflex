<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page errorPage="../error_page.jsp" %>
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import="java.util.ArrayList,java.util.HashMap,java.sql.ResultSet,ft.com.*, java.util.Date, java.util.ArrayList, ft.com.dao.*"%>
<jsp:useBean id="util" class="ft.com.JspUtil" scope="page" />
<jsp:useBean id="info" class="ft.com.InfoMgr" scope="session" />
<%@taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>
<html>
<head>
    <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/bse.js?v=<%=info.getJsVersion()%> " > </script>
<link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/general.css?v=<%=info.getJsVersion()%>"/>
<link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/menu.css?v=<%=info.getJsVersion()%>"/>
</head>
<body topmargin="0" leftmargin="0">
<input type="hidden" name="REQURLMSG" id="REQURLMSG" value="<%=info.getReqUrl()%>" />
<%
ArrayList mainMenuLst = info.getMainMenu();
HashMap subManuLst = info.getSubMenuMap();
HashMap subtrdMap = info.getTrdMenuMap();
String msgheader = "";
String usrFlg=util.nvl((String)info.getUsrFlg());
String usr=info.getUsr().toUpperCase();
String client=util.nvl((String)info.getDmbsInfoLst().get("CNT"));
String IDEABANK = util.nvl((String)info.getDmbsInfoLst().get("IDEABANK"));
if(usr.equals("DEMO"))
client="FAUNA";
String dashboardon =util.nvl((String)info.getDmbsInfoLst().get("DASHBOARDON"));
String dashboardNewUrl =util.nvl((String)info.getDmbsInfoLst().get("DASHBOARDNEW_URL"));
%>
<table  height="103" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td width="188"><img src="<%=info.getReqUrl()%>/images/diaflex_logo.png" width="108" height="59" hspace="40" /></td>
        <td width="1000" valign="middle" height="103" align="left">
        <table width="1000"  border="0" cellspacing="1" cellpadding="2">
       <tr>
            <td align="left" nowrap="nowrap"> 
            <span class="style1">Welcome | <%=session.getAttribute("logUSR")%> | <%=info.getLogId()%>
           
            | <%=client%>
          
            </span></td>
            <td align="center"></td>
            <td align="right" nowrap="nowrap">
            <div class="style2">
            <a href="<%=info.getReqUrl()%>/home.do?method=loadhome"> Home</a> 
            <%if(dashboardon.equals("Y")){%>
            |<a href="<%=info.getReqUrl()%>/dashboard/dashBoardHome.do?method=load" target="fixed"> DashBoard</a> 
            <%}%>
            <%if(dashboardNewUrl.equals("Y")){%>
            |<a href="<%=info.getReqUrl()%>/dashboard/dashBoardHome.do?method=load" target="fixed"> DashBoard</a> 
            <%}%>
            
            |
            <%if(info.getBkPrcOn().equals("N")){%>
            <a href="<%=info.getReqUrl()%>/home.do?method=logout"> Logout</a>
            <%}else{%>
            <img src="<%=info.getReqUrl()%>/images/load.gif" title="In Background Process Going On Wait a Movement to logout"/>
            <%}%>
            </div></td>
          </tr>
          <tr>
            <td colspan="3" >
            
           <table border="0" cellpadding="0" cellspacing="0"><tr><td  class="bgLeft"  valign="top">&nbsp;</td> <td >


<ul id="menu">
<%
int mainMCnt = mainMenuLst.size()/2;
for(int i =0 ; i<mainMenuLst.size();i++){
DFMenu dfMenu = (DFMenu)mainMenuLst.get(i);
int dfId = dfMenu.getDfMenuIdn();
String hdr = dfMenu.getDsp();
String lnk =util.nvl(dfMenu.getLnk());
lnk=lnk.replace("LOGIDN", String.valueOf(info.getLogId()));
String target = util.nvl(dfMenu.getFlg());
if(target.equals(""))
target = "_self";


if(lnk.equals("")){
ArrayList subManu = (ArrayList)subManuLst.get(Integer.toString(dfId));
%>
<li>
<a href="#" class="drop" title="<%=hdr%>" target="<%=target%>" ><%=hdr%></a>
<%
int mnucnt=0;
if(subManu!=null){
String sndClass="dropdown_4columns";
if(i > mainMCnt)
sndClass="dropdown_4columns align_right";
%>
<div class="<%=sndClass%>">
<table cellpadding="2" cellspacing="2"><tr><td nowrap="nowrap" valign="top">
<%
for(int j=0 ; j<subManu.size();j++){

DFMenu subMenu = (DFMenu)subManu.get(j);
int sdfId = subMenu.getDfMenuIdn();
String shdr = subMenu.getDsp();
String slnk = util.nvl(subMenu.getLnk());
String rmk = util.nvl(subMenu.getRmk());
rmk=rmk.replace("LOGIDN", String.valueOf(info.getLogId()));
rmk=rmk.replace("IDEABANK", IDEABANK);
rmk=rmk.replace("ACNT", client.toUpperCase());
String starget = util.nvl(subMenu.getFlg());
if(starget.equals(""))
starget = "_self";
if(slnk.equals("")){
mnucnt++;
ArrayList trdLst = (ArrayList)subtrdMap.get(Integer.toString(sdfId));
if(mnucnt==20){
mnucnt=0;
%>
</td><td valign="top" nowrap="nowrap" >
<%}%>


<h3>&#x25ba;<%=shdr%></h3>


<%
if(trdLst!=null){%>
<ul>
<%


for(int k=0 ; k< trdLst.size();k++){
DFMenu trdMenu = (DFMenu)trdLst.get(k);
int tdfId = trdMenu.getDfMenuIdn();
String thdr = util.nvl(trdMenu.getDsp());
String tlnk = util.nvl(trdMenu.getLnk());
String ttarget = util.nvl(trdMenu.getFlg());
if(ttarget.equals(""))
ttarget = "_self";
%>

<li>
<%if(ttarget.equals("_self")){%>
<a href="<%=info.getReqUrl()%>/<%=tlnk%>" title="<%=thdr%>"  target="<%=ttarget%>"><span>&#149;</span>&nbsp;<%=thdr%></a>
<%}else{%>
<a href="<%=tlnk%>" title="<%=thdr%>" target="<%=ttarget%>"><span>&#149;</span>&nbsp;<%=thdr%></a>
<%}%></li>

<%
mnucnt++;
if(mnucnt>=20){
mnucnt=0;
%>
</ul></td><td valign="top" nowrap="nowrap" ><ul>
<%}}%>

</ul>
<%}%>

<%}else{%>


<%if(starget.equals("_self")){%>

<h3><a href="<%=info.getReqUrl()%>/<%=slnk%>" title="<%=shdr%>"  target="<%=starget%>">&#x25ba;<%=shdr%></a></h3>

<%}else{%>

<h3><a href="<%=slnk%>" title="<%=shdr%>" target="<%=starget%>">&#x25ba;<%=shdr%></a></h3>

<%}%>
<%mnucnt++;
if(mnucnt==20){
mnucnt=0;
%>
</td><td valign="top" nowrap="nowrap" >
<%}%>

<% }%>

<% } %>
</td></tr></table>
</div>
<%}%>
</li>
<%}else {%>

<%if(target.equals("_self")){%> <li>
<a href="<%=info.getReqUrl()%>/<%=lnk%>" class="drop" title="<%=hdr%>" target="<%=target%>"><%=hdr%></a>
</li>
<%}else{%>
<li>
<a href="<%=lnk%>" title="<%=hdr%>" class="drop" target="<%=target%>"><%=hdr%></a>
</li>
<%}%>



<% } %>

<%} %>




</ul>
</td>  <td class="bgRight" valign="top"> &nbsp;</td>   </tr>
</table>
            
          
            </td>
        
            </tr>
          
        </table></td>
       <td align="left" height="20" valign="middle" width="188"><div align="right"><a href="#"><img src="<%=info.getReqUrl()%>/images/flame.png" name="faunalogo"  hspace="40" border="0" id="faunalogo" onmouseover="MM_swapImage('faunalogo','','<%=info.getReqUrl()%>/images/faunalogo.png',1)" onmouseout="MM_swapImgRestore()"></a></div></td>
      </tr> 
    </table>
    </body>
    </html>