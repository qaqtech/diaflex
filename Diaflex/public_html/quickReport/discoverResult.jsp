<%@ page contentType="text/html;charset=windows-1252"%>
<jsp:useBean id="info" class="ft.com.InfoMgr" scope="session" />
<jsp:useBean id="util" class="ft.com.JspUtil" scope="page" />
<%@ page import="java.util.ArrayList,java.util.List,java.util.HashMap,java.sql.ResultSet,ft.com.*, java.util.Date, java.util.ArrayList, ft.com.dao.*"%>


    <%
    int startR = Integer.parseInt(request.getParameter("startR"));
    int endR = Integer.parseInt(request.getParameter("endR"));
      System.out.println(startR);
        System.out.println(endR);
    ArrayList  pktList =  (ArrayList)session.getAttribute("PktList");
ArrayList vwPrpLst = (ArrayList)session.getAttribute("DIS_VW");
    List subStockList = pktList.subList(startR,endR);
    HashMap mprp = info.getMprp();
    if(subStockList!=null && subStockList.size()>0){
    HashMap prpDsc=new HashMap();
prpDsc.put("rap_val", "Rap Value");
prpDsc.put("total_val", "Total Value");
prpDsc.put("quot", "PriEsti");
prpDsc.put("sr", "SR NO.");
prpDsc.put("vnm", "Pkt Code");
prpDsc.put("stt", "Status");
prpDsc.put("CERT NO", "CERT NO");
prpDsc.put("flg", "Flg");
prpDsc.put("cst_val", "CstVlu");
prpDsc.put("net_vlu", "NetVlu");
prpDsc.put("ofr_rte", "Ofr Rte");
prpDsc.put("ofr_dis", "Ofr_dis");
    for(int i=0;i<subStockList.size();i++){
    HashMap pktDtl = (HashMap)subStockList.get(i);
    %>
    <tr>
    <td><%=pktDtl.get("sr")%></td><td><%=pktDtl.get("vnm")%></td><td><%=pktDtl.get("stt")%></td><td><%=pktDtl.get("qty")%></td>
    <%for(int j=0; j < vwPrpLst.size(); j++ ){
   String prp = (String)vwPrpLst.get(j);
   String prpVal =(String)pktDtl.get(prp);
 
   %>
  <td><%=prpVal%></td>
  <%if(prp.equals("RTE")){%>
   <td><%=(String)pktDtl.get("total_val")%></td>
 <%}%>
   <%if(prp.equals("RAP_RTE")){%>
   <td><%=(String)pktDtl.get("rap_val")%></td>
 <%}%>
  <% }%>
    
    </tr>
    <%}}%>
  