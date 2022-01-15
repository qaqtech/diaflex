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
<%
if(info.getUsr().length() == 0) {%>
        <jsp:include page="login.jsp?timeout=true" flush="true"/>
    <%} else {
%>    
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>View Price Grid</title>
      <link type="text/css" rel="stylesheet" href="<%=info.getReqUrl()%>/css/prc_style.css"/>
      <script type="text/javascript"  src="<%=info.getReqUrl()%>/scripts/bse_prc.js"></script>

  </head>
  <%
   String nme="";
    String flg="", srt="", matidn="";
  ArrayList ary = null;
 String prpCO = "COL", prpPU = "CLR" ;
  HashMap  prp = info.getPrp();
  HashMap mprp = info.getMprp();
 ArrayList srtPU = (ArrayList)prp.get(prpPU+"V");  
 ArrayList srtCO = (ArrayList)prp.get(prpCO+"V");  
  String idn = request.getParameter("id");
  String priceGrid = "";
   String colNmes = "" ;
  %>
  <body>
  <form action="updatepricegrid" method="POST">
   <table  cellpadding="5" cellspacing="5" border="0">
  <tr><td colspan="2"><a href="index.jsp"> BACK TO HOME</a></td></tr>
  <tr><td align="left" valign="top">
   <table cellpadding="0"  align="left" cellspacing="5" border="0">
  <tr><td>
        <table cellpadding="0" class="info" align="left" cellspacing="5" border="0">
        <tr>
        <td><table cellpadding="5"  cellspacing="5" class="wap"><tr><td valign="middle">Pricing Criteria:</td>
  <%
 
 
 String matNme = "";
 
  ResultSet rs = null;
 
 String getMatNme = " select nme from dyn_mst_t where idn = ? " ;
 ArrayList params = new ArrayList();
 params.add(idn);
 rs = db.execSql(" get MatNme ", getMatNme, params);
 if(rs.next()) {
    matNme = rs.getString("nme");
 }
 rs.close();
  String sqlPrp  = "select a.mprp ,decode(a.val_fr, 'NA', to_char(a.num_fr),a.val_fr) val_fr "+
    ", decode(a.val_to, 'NA', to_char(a.num_to),a.val_to) val_to, b.nme , b.flg ,b.idn , b.srt "+
    " from dyn_cmn_t a, dyn_mst_t b where a.idn = b.idn and a.idn=?";
  ary = new ArrayList();
  ary.add(idn);
  rs = db.execSql("getPrpr", sqlPrp, ary);
  while(rs.next()){
  nme = rs.getString("nme");
  
  String lprp = rs.getString("mprp");
  String prpDsc = (String)mprp.get(lprp+"D");
  String val_fr = rs.getString("val_fr");
  String val_to = rs.getString("val_to");
   flg = rs.getString("flg");
   srt = rs.getString("srt");
   matidn = rs.getString("idn");

  %>
    <td  valign="middle"><%=prpDsc%>&nbsp;:&nbsp;<%=val_fr%> To <%=val_to%>
  
    </td>
   
  <%}rs.close();%></tr></table>
    <input type="hidden" name="matNme" value="<%=nme%>" />
    <input type="hidden" name="flg" value="<%=flg%>" />
    <input type="hidden" name="matsrt" value="<%=srt%>" />
    <input type="hidden" name="matidn" value="<%=matidn%>" />
 </td>
  </tr>
  <tr><td valign="top">
  <%
  
          priceGrid = "select idn, nme  ";
         String flds = "" ;
            for(int j=0; j < srtPU.size(); j++){
                String lpu = (String)srtPU.get(j);
                
                String colNm = "c";
                int colIdx = j+2;
                if(colIdx < 10)
                    colNm = "c00"+colIdx;
                else if(colIdx >= 10 && colIdx < 100)
                    colNm = "c0"+colIdx;
                else 
                    colNm = "c" + colIdx ;
                
                colNmes += "," + colNm;
                flds += ",?";
            }
    priceGrid = priceGrid +" "+colNmes+" from  dyn_dis_t where  nme in (?) and m001 is null order by S001";
    ary = new ArrayList();
    ary.add(nme);
    
    ResultSet priceRs = db.execSql("getPriceLst", priceGrid, ary);
   %>
   <table cellpadding="6" class="borTb"  cellspacing="0">
   <%
   int i = 0;
    while(priceRs.next()){
    %><tr>
    <%
    for(int j=0; j < srtPU.size(); j++){
                String lpu = (String)srtPU.get(j);
               
                String colNm = "c";
                int colIdx = j+2;
                if(colIdx < 10)
                    colNm = "c00"+colIdx;
                else if(colIdx >= 10 && colIdx < 100)
                    colNm = "c0"+colIdx;
                else 
                    colNm = "c" + colIdx ;
                
                colNmes += "," + colNm;
                String val = util.nvl(priceRs.getString(colNm));
               %>
               <%if(val.equals("CLR")){%>
               <td>Color/Purity</td>
               <%}else if(j==1){%>
               <td><%=val%></td>
               
               <%}else if(i==0){%>
               <td><%=val%></td>
                <%}else if(j==0){%>
                <td></td>
               <%}else{
                String fld = (String)srtCO.get(i-1) + "_" + (String)srtPU.get(j-2);
               %>
            <td>
            <%if(val.indexOf("-")==0){%>
            <input type="text" name="<%=fld%>" id="<%=fld%>" size="4" align="middle" class="blue" onchange="isNumeric(this)"  value="<%=val%>" >
            
            </input>
            <%}else{%>
            <input type="text" name="<%=fld%>" id="<%=fld%>" size="4" align="middle" class="red" onchange="isNumeric(this)"  value="<%=val%>" >
            
            </input>
            <%}%>
            </td>
          <% } } %>
            
            </tr>
  <%i++;
  }%>
  </table>
  </td></tr>
  </table>
  </td>
  <td valign="top"><table>
  <%
  String refNmePrp ="" ;
  ArrayList refNmeV = new ArrayList();
  HashMap refNmeTbl = new HashMap();
  String refNme="select idn, nme , sprp1 from dyn_mst_t where nme_idn=?";
  ary = new ArrayList();
  ary.add(idn);
   ResultSet refRs = db.execSql("ref IDN",refNme, ary);
   while(refRs.next()){
  
   refNmeV.add(util.nvl(refRs.getString("nme")));
   refNmeTbl.put(util.nvl(refRs.getString("nme")),util.nvl(refRs.getString("sprp1")));
   }
 for(int rf=0;rf<refNmeV.size();rf++){
 if(refNmePrp.equals("")){
  refNmePrp = "'"+(String)refNmeV.get(rf)+"'" ;
  }else{
  refNmePrp = refNmePrp +",'"+(String)refNmeV.get(rf)+"'" ;
  }
  String refHiddenNme = "ref_Hid_"+(String)refNmeTbl.get(refNmeV.get(rf));
  %>
 <tr><td> <input type="hidden" name="<%=refHiddenNme%>" value="<%=refNmeV.get(rf)%>" /></td></tr>
 <% }
 
   
    ary = new ArrayList();
    ary.add(refNmePrp);
    priceGrid = priceGrid.replace("and m001 is null", " " );
     priceGrid = priceGrid.replace("nme in (?)", "nme in("+refNmePrp+")" );
    ResultSet refPriceRs = db.execSql("getPriceLst", priceGrid, new ArrayList());
    int j=0;
    while(refPriceRs.next()){
    String reflPrp = util.nvl(refPriceRs.getString("c002"));
    String reflVal = util.nvl(refPriceRs.getString("c003"));
    String refFld = "ref_"+refNmeTbl.get(refPriceRs.getString("nme"))+"_"+reflPrp;
     %>
    <tr><td><%=reflPrp%></td><td> 
    <%if(!reflVal.equals("")){%>
    <input type="text" name="<%=refFld%>" id="<%=refFld%>" size="4" align="middle" class="red" onchange="isNumeric(this)" maxlength="5" value="<%=reflVal%>" />
    <%}%>
    </td>
   
    </tr>
    
    
 <% }
  %>
  </table>
  </td>
  </tr></table>
  </td>
  
  </tr>
  <tr><td><input type="submit" name="update" value="Update"/> </td></tr>
  </table>
  </form>
  </body>
</html>
<%}%>