<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page errorPage="../error_page.jsp" %>
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import="java.util.ArrayList,java.util.HashMap,java.sql.ResultSet,ft.com.*, java.util.Date"%>
<jsp:useBean id="db" class="ft.com.DBMgr" scope="session" />
<jsp:useBean id="log" class="ft.com.LogMgr" scope="session" />
<jsp:useBean id="util" class="ft.com.DBUtil" scope="session" />
<jsp:useBean id="info" class="ft.com.InfoMgr" scope="session" />
<jsp:useBean id="mail" class="ft.com.GenMail" scope="page" />
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>priceGrid</title>
      <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/prc_style.css"/>
      <script type="text/javascript"  src="<%=info.getReqUrl()%>/scripts/bse_prc.js"></script>
   
  </head>
  <body>
  <form action="savepricegrid" method="POST">
  <table  cellpadding="5" cellspacing="5" border="0">
  <tr><td colspan="2"><a href="index.jsp"> BACK TO HOME</a></td></tr>
  <tr><td colspan="2"><table cellpadding="0" cellspacing="1">
  <tr><td colspan="2"><input type="checkbox" name="jb_prc" id="jb_prc" value="Y"/><strong>JB PriceList</strong></td></tr>
  <tr><td colspan="2"><input type="checkbox" name="sub" id="sub" value="Y"/><strong>Refer From</strong></td></tr>
  <tr>
    <td colspan="2"><input type="radio" name="grid_vlu" id="grid_vlu" value="PCT" checked="checked"/>in %&nbsp;
    <input type="radio" name="grid_vlu" id="grid_vlu" value="VLU"/>Value Wise</td>
  </tr>
  </table>
  </td></tr>
    <tr><td align="left" valign="top">
        <table cellpadding="0" class="info" align="left" cellspacing="5" border="0">
            
  <%
    String dfltVal = "0" ;
    String dfltDsp = "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Select&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";

    ResultSet rs = null;
    HashMap mprp = null, prp = null;
    ArrayList bsePrp = null, disPrp = null, refPrp = null;
  %>
  <%
    mprp = info.getMprp();
    prp = info.getPrp();
     
    bsePrp = info.getPrcBsePrp();
    disPrp = info.getPrcDisPrp();
    refPrp = info.getPrcRefPrp();
 %>
 <%
    util.SOP("@PriceGrid Starting Bse ");
    for(int i=0; i < bsePrp.size(); i++) {
        String lprp = (String)bsePrp.get(i);
        String prpDsc = (String)mprp.get(lprp+"D");
        ArrayList prpPrt = (ArrayList)prp.get(lprp+"P");
        ArrayList prpSrt = (ArrayList)prp.get(lprp+"V");
        
        String fld1 = lprp + "_1" ;
        String fld2 = lprp + "_2" ;
        
        String valFr = request.getParameter(fld1);
        String valTo = request.getParameter(fld2);
        
        String dspFr = (String)prpPrt.get(prpSrt.indexOf(valFr));
        String dspTo = (String)prpPrt.get(prpSrt.indexOf(valTo)); 
    %>    
        <tr><td><%=prpDsc%></td>
            <td><%=dspFr%><input type="hidden" name="<%=lprp%>_1" id="<%=lprp%>_1" value="<%=valFr%>"/></td>
            <td><%=dspTo%><input type="hidden" name="<%=lprp%>_2" id="<%=lprp%>_2" value="<%=valTo%>"/></td>
        </tr>
   <%}
    for(int i=1; i <= 5; i++) {
        util.SOP("@PriceGrid Starting DIS ");
        String reqDisPrp = request.getParameter("disprp_"+i);
        if(!(reqDisPrp.equals("0"))) {
            String lprp = reqDisPrp;
            String prpDsc = (String)mprp.get(lprp+"D");
            String prpTyp = (String)mprp.get(lprp+"T");
            ArrayList prpPrt = (ArrayList)prp.get(lprp+"P");
            ArrayList prpSrt = (ArrayList)prp.get(lprp+"V");
            String fld1 = lprp + "_1" ;
            String fld2 = lprp + "_2" ;
        %>
            <tr><td bgcolor="White"><%=prpDsc%></td>
        <%
            if(prpTyp.equals("C")) {%>
              <td><select class="dmddropdown" name="<%=fld1%>" id="<%=fld1%>" onchange="setFrTo('<%=fld1%>', '<%=fld2%>');">
             <option value="<%=dfltVal%>"><%=dfltDsp%></option>   
             <%
             for(int j=0; j<prpSrt.size(); j++) {
                String pPrt = (String)prpPrt.get(j);
                String pSrt = (String)prpSrt.get(j);
             %>
                <option value="<%=pSrt%>" ><%=pPrt%></option>  
             <%}
             %>
            </select>
        </td>
        <td><select class="dmddropdown" name="<%=fld2%>" id="<%=fld2%>" onchange="setFrTo('<%=fld1%>', '<%=fld2%>');">
             <option value="<%=dfltVal%>"><%=dfltDsp%></option>   
             <%
             for(int j=0; j<prpSrt.size(); j++) {
                String pPrt = (String)prpPrt.get(j);
                String pSrt = (String)prpSrt.get(j);
             %>
                <option value="<%=pSrt%>" ><%=pPrt%></option>  
             <%}
             %>
            </select>
        </td>  
            <%} else {%>
              <td><input type="text" name="<%=fld1%>" id="<%=fld1%>"  size="30"/></td>  
              <td><input type="text" name="<%=fld2%>" id="<%=fld2%>" size="30"/></td>  
            <%}
         %>
            </tr>
    <%   
        }
    }
       
  %>
   
  <tr><td colspan="3">
  <%
    String prpCO = "COL", prpPU = "CLR" ;
    
    String dscCO = (String)mprp.get(prpCO+"D");
    String dscPU = (String)mprp.get(prpPU+"D");
    
    ArrayList valCO = (ArrayList)prp.get(prpCO+"P");
    ArrayList srtCO = (ArrayList)prp.get(prpCO+"V");    
    
    ArrayList valPU = (ArrayList)prp.get(prpPU+"P");
    ArrayList srtPU = (ArrayList)prp.get(prpPU+"V");    
            
  %>
  <table cellpadding="0" cellspacing="1">
  <%
    util.SOP("@PriceGrid Starting CO/PU grid ");
    for(int i=-2; i<srtCO.size(); i++) {%>
        <tr>
        <%for(int j=-2; j < srtPU.size(); j++) {
            
            if((i >= 0) && (j >= 0)) {
                String fld = (String)srtCO.get(i) + "_" + (String)srtPU.get(j);
            %>
                <td><input type="text" name="<%=fld%>" id="<%=fld%>" size="4"  onchange="isNumeric(this)" maxlength="5" value=""/></td>
            <%} else {
                if((i==-2) && (j==-1)) {%>
                    <td><%=dscCO%></td>
                <%
                } else if((i==-1) && (j==-2)) {%>
                    <td><%=dscPU%></td>
                <%
                } else if((i >= 0) && (j== -1)) {%>
                    <td><%=(String)valCO.get(i)%></td>
                <%
                } else if((i == -1) && (j >=0)) {%>
                    <td><%=(String)valPU.get(j)%></td>
                <%} else {%>
                    <td>&nbsp;</td>
                <%}
            }
        }
        %>
        </tr>
    <%}
  %>
  </table>
  </td>
  </tr>
  
  </table>
  
  </td>
  <td align="left" valign="top" > 
 
  <%
    util.SOP("@PriceGrid Starting REF ");
    for(int i=0;i< refPrp.size();i++){
        String lprp = (String)refPrp.get(i);
        String prpDsc = (String)mprp.get(lprp+"D");
        String prpTyp = (String)mprp.get(lprp+"T");
        ArrayList prpPrt = (ArrayList)prp.get(lprp+"P");
        ArrayList prpSrt = (ArrayList)prp.get(lprp+"V");
        
        String reqVal = util.nvl(request.getParameter("ref_"+lprp));
        String tblId = "ref_"+lprp ;
        util.SOP("@PriceGrid Starting REF "+ tblId + " : "+ reqVal);
        if(reqVal.equals("Y")) {%>
            <table style="float:left"  >
                <tr><td colspan="2" align="center" class="hedinfo" onmouseover="showBox('<%=tblId%>')" ><%=prpDsc%></td></tr>
                <tr><td>
                    <table id="<%=tblId%>" style="display:none" class="hedinfo" cellspacing="1">
                    <%for(int j=0; j<prpSrt.size(); j++) {
                        String lVal = (String)prpPrt.get(j);
                        String lSrt = (String)prpSrt.get(j);
                    %>
                    <tr>
                        <td bgcolor="White"><%=lVal%></td>
                        <td  bgcolor="White"><input type="text" name="<%=tblId%>_<%=lVal%>" id="<%=tblId%>_<%=lVal%>" class="textBoxes" size="4"/></td>
                    </tr>
                    <%}
                    %>
                    </table>
                    </td>
                </tr>    
                
            </table>
        <%}
    }        
  %>
  </td>
  </tr>
  </table>
  <p><input type="submit" name="save" value="Save Change" id="save"/></p>
  </form>
  </body>
  </html>
 