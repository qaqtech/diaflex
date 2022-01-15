<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
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
    <title>File Upload Template</title>
  <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/bse.css?v=<%=info.getJsVersion()%>"/>
    <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/style.css?v=<%=info.getJsVersion()%>"/>
     <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/bse.js?v=<%=info.getJsVersion()%> " > </script>
    
  </head>
<%
        String logId=String.valueOf(info.getLogId());
        String onfocus="cook('"+logId+"')";
        %>
 <body onfocus="<%=onfocus%>" onload="MM_preloadImages('images/faunalogo.png')" topmargin="0" leftmargin="0" rightmargin="0" bottommargin="0">
<table width="100%"  border="0" align="center" cellpadding="0" cellspacing="0" class="mainbg">
  <tr>
    <td height="103" valign="top">
   <jsp:include page="/header.jsp" />
    
    </td>
  </tr>
 
  <tr>
    <td height="5" valign="top" class="greyline"><img src="<%=info.getReqUrl()%>/images/grey_linebig.png" width="685" height="5" align="right" /></td>
  </tr>
  <tr>
  <td valign="top" class="hedPg">
  <table cellpadding="1" cellspacing="5"><tr><td valign="middle">
  <img src="../images/line.jpg" border="0" /></td><td valign="middle"> <span class="pgHed">File Upload Template</span> </td>
   </tr></table>
  </td>
  </tr>
  <tr><td class="tdLayout" valign="top">
    <html:form action="fileloaders/uploadFile.do?method=getUploadStt" method="post">
  <table>
  <tr><td><table cellspacing="10px">
  <%
        HashMap allPageDtl= (info.getPageDetails() == null)?new HashMap():(HashMap)info.getPageDetails();
        HashMap pageDtl=(HashMap)allPageDtl.get("FILE_TEMPLATE");
                ArrayList pageList=new ArrayList();
                HashMap pageDtlMap=new HashMap();
                String fld_ttl="";
                String dflt_val="";
                String lov_qry="";
                String flg1="";
                if(pageDtl!=null && pageDtl.size() >0){
                    for(int i=0;i<pageDtl.size();i++){
                    pageList= ((ArrayList)pageDtl.get("LINK") == null)?new ArrayList():(ArrayList)pageDtl.get("LINK");
                    for(int j=0;j<pageList.size();j++){
                        pageDtlMap=(HashMap)pageList.get(j);
                        fld_ttl=(String)pageDtlMap.get("fld_ttl");
                        dflt_val=(String)pageDtlMap.get("dflt_val");
                        lov_qry=(String)pageDtlMap.get("lov_qry");
                        flg1=(String)pageDtlMap.get("flg1");
                        %>
                         <tr><td> <span class="txtLink">
                         <a href="#" onclick="goTo('<%=info.getReqUrl()%>/contact/nmedocs.do?method=download&path=<%=dflt_val%>&typ=application/csv')"><%=fld_ttl%> </a>
                         </span></td></tr>
     <%           }
     }
     }
  %>
  </table></td>
  </tr>
  </table>
  </html:form>
  </td>
  </tr>
   <tr>
     <td><jsp:include page="/footer.jsp" /> </td>
     </tr>
  </table>
  
  
  </body>
</html>