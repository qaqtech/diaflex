<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import="java.util.ArrayList,java.util.HashMap,java.util.ArrayList,java.util.Iterator,
java.util.Set,java.sql.ResultSet,ft.com.*, java.util.Date"%>

<jsp:useBean id="util" class="ft.com.JspUtil" scope="page" />
<jsp:useBean id="info" class="ft.com.InfoMgr" scope="session" />

<%@taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%
    String grtNme = (String)request.getAttribute("grpNme");
%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
        <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/style.css?v=<%=info.getJsVersion()%>"/>
         <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/bse.css?v=<%=info.getJsVersion()%>"/>
        <script type="text/javascript"  src="<%=info.getReqUrl()%>/scripts/bse_prc.js"></script>
    <script type="text/javascript" src="<%=info.getReqUrl()%>/scripts/bse.js?v=<%=info.getJsVersion()%> " > </script>
    </head>
        <%String logId=String.valueOf(info.getLogId());
        String onfocus="cook('"+logId+"')";
        %>
 <body onfocus="<%=onfocus%>">
    <%
    //util.updAccessLog(request,response,"Price Matrix", "Price Matrix");
     ArrayList fnRowArray=new ArrayList();
      ArrayList fnColArray=new ArrayList();
     fnRowArray=(ArrayList)session.getAttribute("fnRowArray");
     fnColArray=(ArrayList)session.getAttribute("fnColArray"); 
     ArrayList rowList=(ArrayList) session.getAttribute("RowList");
     ArrayList colList=(ArrayList) session.getAttribute("ColList");
     ArrayList refList=(ArrayList) session.getAttribute("refList");
     ArrayList commnList=(ArrayList) request.getAttribute("commnList");
     HashMap dspallFlg=(HashMap) request.getAttribute("dspallFlg"); 
     HashMap prp_prpValAry=(HashMap)request.getAttribute("prp_prpValAry");
     HashMap bigColHashtable=(HashMap)session.getAttribute("colHeading");
     HashMap mprp = info.getMprp();
     HashMap prp = info.getPrp();
     String dfltVal = "0" ;
  
    String dfltDsp = "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Select&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
    %>
    <html:form action="/pri/pricegpmatrix?method=saveGrid"    method="POST">
    <table class="mainbg">
    
    <tr><td colspan="2">
    <table>
    <tr><td>
    <table class="grid1">
    <tr><td><html:hidden property="value(grpNme)" /></td></tr>
     <tr><th>Property</th><th>From</th><th>To</th></tr>
  
 <%
    for(int k=0; k < commnList.size(); k++) {
      
        String reqDisPrp = (String)commnList.get(k);
            String lprp = reqDisPrp;
            String prpDsc = (String)mprp.get(lprp+"D");
            String prpTyp = (String)mprp.get(lprp+"T");
            ArrayList prpPrt = (ArrayList)prp.get(lprp+"P");
            ArrayList prpVal = (ArrayList)prp.get(lprp+"V");
            String fld1 = lprp + "_1" ;
            String fld2 = lprp + "_2" ;
            String proFld1="value("+fld1+")";
            String proFld2="value("+fld2+")";
            String dspflg=(String)dspallFlg.get(lprp);
            
            ArrayList valLst = (ArrayList)prp_prpValAry.get(lprp);
            
        %>
            <tr>
            <td bgcolor="White"><%=prpDsc%></td>
        <%
            if(prpTyp.equals("C")) {%>
              <td><html:select styleClass="dmddropdown" name="PriceGPMatrixForm" property="<%=proFld1%>" styleId="<%=fld1%>" onchange="setFrTo('<%=fld1%>', '<%=fld2%>');">
             <option value="<%=dfltVal%>"><%=dfltDsp%></option>   
             <%
             for(int j=0; j<valLst.size(); j++) {
                String pSrt = (String)valLst.get(j);
                String selFr = "";
                %>
                <option value="<%=pSrt%>" <%=selFr%>><%=pSrt%></option>  
            <%}%>
            </html:select>
            
        </td>
        <td><html:select styleClass="dmddropdown" property="<%=proFld2%>" styleId="<%=fld2%>" onchange="setFrTo('<%=fld1%>', '<%=fld2%>');">
             <option value="<%=dfltVal%>"><%=dfltDsp%></option>   
            <%
             for(int j=0; j<valLst.size(); j++) {
                String pSrt = (String)valLst.get(j);
                String selFr = "";
                %>
                <option value="<%=pSrt%>" <%=selFr%>><%=pSrt%></option>  
            <%}%>

            </html:select>
        </td>  
        </tr>
    <%   
        
    }
    }
       
  %>
    <!--****************************************************************-->
    </table></td>
    
    
    </tr>
    </table>
    </td></tr>
    <tr></tr>
    <tr></tr>
    <tr>
    <td valign="top">
    <% if (refList.size() > 0) { %>
    <table class="dataTable" ><tr><th>
    Reference
    </th>
     </tr>
     <% for(int i=0;i<refList.size();i++)
     {%>
     <tr>
     <%
   //  ArrayList prpval=(ArrayList)prp_prpValAry.get((String)refList.get(i));
     String lprp = (String)refList.get(i);
     String prpDsc = (String)mprp.get(lprp+"D");
     String prpTyp = (String)mprp.get(lprp+"T");
     ArrayList prpPrt = (ArrayList)prp.get(lprp+"P");
     ArrayList prpSrt = (ArrayList)prp.get(lprp+"V");
      String tblId = "ref_"+lprp ;
      
     %>
       <td>    <table style="float:left"  >
                <tr><th colspan="2" align="center" class="hedinfo" onmouseover="priceShowBox('<%=tblId%>')" ><%=prpDsc%></th></tr>
                <tr><td>
                    <table id="<%=tblId%>" style="display:none" class="hedinfo" cellspacing="1">
                    <%for(int j=0; j<prpSrt.size(); j++) {
                        String lVal = (String)prpPrt.get(j);
                        String lSrt = (String)prpSrt.get(j);
                        String proTblidval="value("+tblId+"_"+lVal+")";
                        String stId=tblId+"_"+lVal;
                    %>
                    <tr>
                        <td bgcolor="White"><%=lVal%></td>
                        <td bgcolor="White"><html:text property="<%=proTblidval%>"   styleId="<%=stId%>"    value="" size="4"/></td>
                    </tr>
                    <%}
                    %>
                    </table>
                    </td>
                </tr>    
                
            </table></td
     </tr>
    <% }
     
     %>
     
    </table>
    <%}%>
    </td>
    
    <td valign="top">
    <table cellpadding="1" cellspacing="1" border="0" width="300px" class="dataTable" style=" white-space: nowrap;">
     <tr>
 <td  ></td>
 <%
 
 for(int i=0;i<rowList.size();i++)  // To display row name
 {
 
 %>
 <th class="matrix"><b><%=rowList.get(i) %></b></th>
<% }
 %>
 </tr>
 
 <%
if(bigColHashtable!=null)   // column heading
{

int tblsize=bigColHashtable.size();
int i=0;
 while(tblsize>0)
 {
 
 %>
 <tr>
 <%
  ArrayList prpDtl=(ArrayList)bigColHashtable.get(i);
 %>
 <th class="matrix"><b><%=(String)colList.get(i)%></b></th><th colspan="<%=rowList.size() %>"></th>
 <%for(int j=0;j<prpDtl.size();j++)
 {
 %>
<th class="matrix"><b><%=prpDtl.get(j) %></b></th>
<%
}
i++;
tblsize--;
%>
</tr>
<%
}
}    ///   column heading

 if(fnRowArray!=null)
 {
  String rowval="";
 for(int i=0;i<fnRowArray.size();i++)
 {
 %>
 <tr>
 <td></td>
 <%
 String row=util.nvl((String)fnRowArray.get(i));
  rowval=row;
  if(!row.contains("_"))
  {
  %>
 <th class="matrix"  align="center"><b><%= row %></b></th> 
<%  }
else
{
do
{
String tdrow="";
tdrow=row.substring(0, row.indexOf("_"));
%>
<th class="matrix" align="center" ><b><%= tdrow %></b></th>
<%
 row=row.substring(row.indexOf("_")+1,row.length());
 if(!row.contains("_"))
 {
 %>
<th class="matrix" align="center" ><b><%= row %></b></th> 
<% }
}
while(row.contains("_"));
%>

<%
  
 }
 // end of else
 if(fnColArray!=null)                         //  cells starting from herre
 {
 for(int j=0;j<fnColArray.size();j++)
 {
 String colval=util.nvl((String)fnColArray.get(j));
 String cellName="value("+rowval+"_"+colval+")";
 
 %>
 <td class="matrix" align="center"><html:text size="6" property="<%=cellName%>" value=""/> </td>

 <%
 }
 }else
 {
 String cellName="value("+rowval+")";
 %>
 <td class="matrix" align="center" ><html:text size="6" property="<%=cellName%>" value=""/> </td>
 <%
 }
 %>
 
</tr> 
<%
 }
 }
  else if(fnColArray!=null)        //  if there is no row selected
 {
 %>
 <tr>
 <td></td>
 <%
 for(int j=0;j<fnColArray.size();j++)
 {
 String colval=util.nvl((String)fnColArray.get(j));
 String cellName="value("+colval+")";
 %>
 <td class="matrix" align="center" ><html:text size="6" property="<%= cellName%>" value=""/> </td>
 
 <%
 }
 %>
 </tr>
 <%
 } 
 %>
   
    </table>
    </td></tr>
    <tr><td><html:submit property="submit" value="SUBMIT"/></td></tr>
    </table>
    </html:form>
    </body>
</html>