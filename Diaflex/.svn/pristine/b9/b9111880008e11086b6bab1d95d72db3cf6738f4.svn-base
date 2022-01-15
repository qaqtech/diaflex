package ft.com.order;

import ft.com.DBMgr;
import ft.com.DBUtil;
import ft.com.InfoMgr;
import ft.com.dao.PktDtl;
import ft.com.pri.PriceCalcAction;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.ArrayList;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

public class OrderDeliveryAction extends DispatchAction {
    
  
    public ActionForward load(ActionMapping am, ActionForm af, HttpServletRequest req,
                              HttpServletResponse res) throws Exception {
      HttpSession session = req.getSession(false);
      InfoMgr info = (InfoMgr)session.getAttribute("info");
      DBUtil util = new DBUtil();
      DBMgr db = new DBMgr();
      String rtnPg="sucess";
      if(info!=null){  
      db.setCon(info.getCon());
      util.setDb(db);
      util.setInfo(info);
      db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
      util.setLogApplNm(info.getLoApplNm());
      rtnPg=init(req,res,session,util);
      }else
      rtnPg="sessionTO";
      if(!rtnPg.equals("sucess")){
          return am.findForward(rtnPg);   
      }else{
       OrderDeliveryForm udf=(OrderDeliveryForm)af;
          udf.resetAll();
          return am.findForward("load"); 
      }
    }
    
    
    public ActionForward fetch(ActionMapping am, ActionForm af, HttpServletRequest req,
                              HttpServletResponse res) throws Exception {
      HttpSession session = req.getSession(false);
      InfoMgr info = (InfoMgr)session.getAttribute("info");
      DBUtil util = new DBUtil();
      DBMgr db = new DBMgr();
      String rtnPg="sucess";
      if(info!=null){  
      db.setCon(info.getCon());
      util.setDb(db);
      util.setInfo(info);
      db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
      util.setLogApplNm(info.getLoApplNm());
      rtnPg=init(req,res,session,util);
      }else
      rtnPg="sessionTO";
      if(!rtnPg.equals("sucess")){
          return am.findForward(rtnPg);   
      }else{
          OrderDeliveryForm udf=(OrderDeliveryForm)af;
          String dlvIdn = util.nvl((String)udf.getValue("deliveryIdn"));
          String    vnmLst      = util.nvl((String)udf.getValue("vnmLst"));
          String dfr = util.nvl((String)udf.getValue("dtefr"));
          String dto = util.nvl((String)udf.getValue("dteto"));
          String dtefrom="";
          String dteto="";
          if(!dfr.equals(""))
            dtefrom = "to_date('"+dfr+"' , 'dd-mm-yyyy')";
          
          if(!dto.equals(""))
            dteto = "to_date('"+dto+"' , 'dd-mm-yyyy')";
          
          ArrayList vwprpLst = (info.getValue("MDL_MEMO_RTRN") == null)?new ArrayList():(ArrayList)info.getValue("MDL_MEMO_RTRN");
          ArrayList pkts = new ArrayList();
          String mprpStr = "";
          String mdlPrpsQ = "Select Decode ( Rank() Over ( Order By Srt),1,' ',',')||''''||Prp||''''||' '||\n" + 
          "Upper(replace(Replace(Replace(Replace(Replace(Replace(Prp,'-','_'),'&','_'),'COMMENT','COM1'),'REMARKS','REM1'),'SIZE','SIZE1'),'CERT NO.','CERT')) \n" + 
          "str From Rep_Prp Rp Where rp.MDL = ? order by srt " ;
          ArrayList ary = new ArrayList();
          ary.add("MEMO_RTRN");
          ArrayList rsList = db.execSqlLst("sql", mdlPrpsQ, ary);
          PreparedStatement pst=(PreparedStatement)rsList.get(0);
          ResultSet  rs = db.execSql("mprp ", mdlPrpsQ , ary);
          while(rs.next()) {
          String val = util.nvl((String)rs.getString("str"));
          mprpStr = mprpStr +" "+val;
          }
          rs.close();
          pst.close();
          String getPktData= " with STKDTL as " +
                  " ( Select b.sk1,b.cert_no certno,a.idn ,c.dte, a.qty ,nvl(nvl(nvl(st.txt,st.dte),st.num),st.prt1) atr , st.mprp, a.mstk_idn, nvl(fnl_sal, quot) memoQuot, quot, fnl_sal,a.rmk "+
            " , a.cts, nvl(b.upr, b.cmp) rate, b.rap_rte raprte, a.stt,to_char(trunc(a.cts,2) * b.rap_rte, '99999999990.00') rapVlu, DECODE(upper(NVL(pack_var.get_usr, 'NA')), 'DEMO', dbms_random.STRING('X', 12), b.vnm) vnm "+
           " , to_char(trunc(((nvl(b.upr, b.cmp)/greatest(b.rap_rte,1))*100)-100,2),9990.99) dis "+
           " from stk_dtl st,dlv_dtl a, mstk b , mdlv c" +
            " where st.mstk_idn=b.idn and a.mstk_idn = b.idn  and c.idn = a.idn and a.stt in ('DLV','IS','PD') and c.typ in ('DLV') and c.stt='IS' and nvl(b.prt2,'NA')=decode(nvl(b.prt2,'NA'),'SPECIAL',nvl(PACK_VAR.Get_Special,'NA'),nvl(b.prt2,'NA'))" ;
          if(!dtefrom.equals("") && !dteto.equals("")){
         getPktData=getPktData+ "  and trunc(c.dte) between "+dtefrom+" and "+dteto+"" ;
          }
        ArrayList  params = new ArrayList();
         
          if(!dlvIdn.equals("")){
          getPktData = getPktData+" and c.idn in ("+dlvIdn+")  " ;
          
          }
          if(!vnmLst.equals("")){
          vnmLst = util.getVnm(vnmLst);
          getPktData = getPktData + " and ( b.vnm in ("+vnmLst+") or b.tfl3 in ("+vnmLst+") ) ";
          }
          getPktData = getPktData +" \n"+
        
          " and exists (select 1 from rep_prp rp where rp.MDL = 'MEMO_RTRN' and st.mprp = rp.prp) " +
          " and not exists (select 1 from trns_item ti where b.idn=ti.stk_idn and ti.stt='CNF') " +         
           " And st.Grp = 1) " +
          " Select * from stkDtl PIVOT " +
          " ( max(atr) " +
          " for mprp in ( "+mprpStr+" ) ) order by dte desc " ;
          rsList = db.execSqlLst("sql", getPktData, params);
          pst=(PreparedStatement)rsList.get(0);
          rs = (ResultSet)rsList.get(1);
          while(rs.next()) {
          PktDtl pkt    = new PktDtl();
          long   pktIdn = rs.getLong("mstk_idn");

          pkt.setPktIdn(pktIdn);
          pkt.setSaleId(util.nvl(rs.getString("idn")));
          pkt.setRapRte(util.nvl(rs.getString("raprte")));
          pkt.setQty(util.nvl(rs.getString("qty")));
          pkt.setCts(util.nvl(rs.getString("cts")));
          pkt.setRte(util.nvl(rs.getString("rate")));
          pkt.setMemoQuot(util.nvl(rs.getString("memoQuot")));
          pkt.setByrRte(util.nvl(rs.getString("quot")));
          pkt.setFnlRte(util.nvl(rs.getString("fnl_sal")));
          pkt.setDis(rs.getString("dis"));
          pkt.setVnm(util.nvl(rs.getString("vnm")));
          String lStt = rs.getString("stt");

          pkt.setStt(lStt);
          udf.setValue("stt_" + pktIdn, lStt);
          pkt.setValue("rapVlu", (rs.getString("rapVlu")));
          pkt.setValue("rmk", rs.getString("rmk"));
          for (int v = 0; v < vwprpLst.size(); v++) {
          String vwPrp = (String)vwprpLst.get(v);
          String fldName = vwPrp;
          if(vwPrp.toUpperCase().equals("H&A"))
          fldName = "H_A";
          
          if(vwPrp.toUpperCase().equals("COMMENT"))
          fldName = "COM1";
          
          if(vwPrp.toUpperCase().equals("REMARKS"))
          fldName = "REM1";
          
          if(vwPrp.toUpperCase().equals("COL-DESC"))
          fldName = "COL_DESC";
          
          if(vwPrp.toUpperCase().equals("COL-SHADE"))
          fldName = "COL_SHADE";
          
          if(vwPrp.toUpperCase().equals("FL-SHADE"))
          fldName = "FL_SHADE";
          
          if(vwPrp.toUpperCase().equals("STK-CTG"))
          fldName = "STK_CTG";
          
          if(vwPrp.toUpperCase().equals("STK-CODE"))
          fldName = "STK_CODE";
          
          if(vwPrp.toUpperCase().equals("SUBSIZE"))
          fldName = "SUBSIZE1";
          
          if(vwPrp.toUpperCase().equals("SIZE"))
          fldName = "SIZE1";
          
          if(vwPrp.toUpperCase().equals("MIX_SIZE"))
          fldName = "MIX_SIZE1";
          
          if(vwPrp.toUpperCase().equals("STK-CTG"))
          fldName = "STK_CTG";
          
          if(vwPrp.toUpperCase().equals("CRN-OP"))
          fldName = "CRN_OP";
          
          if(vwPrp.toUpperCase().equals("CRTWT"))
          fldName = "cts";
          
          if(vwPrp.toUpperCase().equals("RAP_DIS"))
          fldName = "r_dis";
          
          if(vwPrp.toUpperCase().equals("RTE"))
          fldName = "rate";
          
          if (vwPrp.toUpperCase().equals("RAP_RTE"))
          fldName = "raprte";
              if (vwPrp.toUpperCase().equals("CERT NO."))
              fldName = "certno";
              if(vwPrp.toUpperCase().equals("MEM_COMMENT"))
              fldName = "MEM_COM1";
          String fldVal = util.nvl((String)rs.getString(fldName));
          
          pkt.setValue(vwPrp, fldVal);
          }
          pkts.add(pkt);
          }
          req.setAttribute("pktList", pkts);
          rs.close();
          pst.close();
          if(pkts!=null && pkts.size()>0){
              ArrayList orderLst = new ArrayList();
               String transodrSql = "select DISTINCT a.trns_idn \n" + 
               "           from trns_m a,trns_item b,(\n" + 
               "           select trns_idn,trns_item_idn,sum(qty) qty,sum(weight) weight from (\n" + 
               "           select a.trns_idn,b.trns_item_idn,nvl(qty,0) qty ,nvl(weight,0)*b.fctr weight\n" + 
               "           from\n" + 
               "           trns_m a,trns_item b\n" + 
               "           where  \n" + 
               "           a.trns_idn=b.trns_idn  and b.ref_trns_item_idn is null\n" + 
               "           union\n" + 
               "           select a.trns_idn,b.ref_trns_item_idn,sum(nvl(qty,0)*b.fctr) qty ,sum(nvl(weight,0)*b.fctr) weight\n" + 
               "           from\n" + 
               "           trns_m a,trns_item b\n" + 
               "           where  \n" + 
               "           a.trns_idn=b.trns_idn and b.ref_trns_item_idn is not null\n" + 
               "           group by a.trns_idn,b.ref_trns_item_idn\n" + 
               "           ) group by trns_idn,trns_item_idn\n" + 
               "           ) c\n" + 
               "           where c.weight > 0 and\n" + 
               "           a.trns_idn=b.trns_idn and b.trns_idn=c.trns_idn and b.trns_item_idn=c.trns_item_idn \n" ;
              rsList = db.execSqlLst("sql", transodrSql, new ArrayList());
              pst=(PreparedStatement)rsList.get(0);
              rs = (ResultSet)rsList.get(1);
              while(rs.next()) {
                  orderLst.add(rs.getString("trns_idn"));
              }
              req.setAttribute("orderList", orderLst);
              rs.close();
              pst.close();
          }
          req.setAttribute("view", "Y");
          return am.findForward("load"); 
      }
    }
    
    
    public ActionForward save(ActionMapping am, ActionForm af, HttpServletRequest req,
                              HttpServletResponse res) throws Exception {
      HttpSession session = req.getSession(false);
      InfoMgr info = (InfoMgr)session.getAttribute("info");
      DBUtil util = new DBUtil();
      DBMgr db = new DBMgr();
      String rtnPg="sucess";
      if(info!=null){  
      db.setCon(info.getCon());
      util.setDb(db);
      util.setInfo(info);
      db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
      util.setLogApplNm(info.getLoApplNm());
      rtnPg=init(req,res,session,util);
      }else
      rtnPg="sessionTO";
      if(!rtnPg.equals("sucess")){
          return am.findForward(rtnPg);   
      }else{
          OrderDeliveryForm udf=(OrderDeliveryForm)af;
          ArrayList stkIdnList = new ArrayList();
          Enumeration reqNme = req.getParameterNames(); 
                while (reqNme.hasMoreElements()) {
              String paramNm = (String) reqNme.nextElement();
              if(paramNm.indexOf("cb_inv_") > -1) {
                  String val = req.getParameter(paramNm);
                   stkIdnList.add(val);
            }}
          String msg ="";
          
          if(stkIdnList!=null && stkIdnList.size()>0){
              boolean isCommit = false;
                try {
                    db.setAutoCommit(false);
                   
                    for(int i=0;i<stkIdnList.size();i++){
                    String stkIdn = (String)stkIdnList.get(i);
                    String trnsIdn = util.nvl((String)udf.getValue("ORDER_"+stkIdn));
                    String trnsItmIdn = util.nvl((String)udf.getValue("ORDERITEM_"+stkIdn));
                    String qty = util.nvl((String)udf.getValue("QTY_"+stkIdn));
                    String cts = util.nvl((String)udf.getValue("CTS_"+stkIdn));
                    String rte = util.nvl((String)udf.getValue("RTE_"+stkIdn));
                    String quot = util.nvl((String)udf.getValue("QUOT_"+stkIdn));
                    String fnlsal = util.nvl((String)udf.getValue("FNLSAL_"+stkIdn));
                    String inserintoItm = "insert into trns_item(trns_item_idn, trns_idn, stk_idn, rte,quot, qty,weight,stt,  created_ts, created_by,fctr , ref_trns_typ,ref_trns_idn,ref_trns_item_idn) \n" + 
                    "values(TRNS_ITEM_SEQ.nextval,?,?,?,?,?,?,?,sysdate,?,?,?,?,?)";
                    ArrayList ary = new ArrayList();
                    ary.add(trnsIdn);
                    ary.add(stkIdn);
                    ary.add(rte);
                    ary.add(quot);
                    ary.add(qty);
                    ary.add(cts);
                    ary.add("CNF");
                    ary.add(info.getUsr());
                    ary.add("-1");
                    ary.add("DLV");
                    ary.add(trnsIdn);
                    ary.add(trnsItmIdn);
                    int ct=db.execDirUpd("inserintoItm", inserintoItm, ary);
                     if(ct>0)
                        isCommit=true;
                    }
                } catch (Exception e) {
                    // TODO: Add catch code
                    e.printStackTrace();
                    isCommit=false;
                } finally {
                    db.setAutoCommit(true);
                }
                if(isCommit){
                  db.doCommit();
                    msg="Order delivery done successfully";
                }else{
                  db.doRollBack();
                    msg="Error in process";
                }
          }else
              msg="Please Select Packets For order";
    
        req.setAttribute("msg", msg);
          return am.findForward("load");   
      }
    }
    public ActionForward GetItemIdn(ActionMapping am, ActionForm af, HttpServletRequest req, HttpServletResponse res) throws Exception {
        HttpSession session = req.getSession(false);
        InfoMgr info = (InfoMgr)session.getAttribute("info");
        DBUtil util = new DBUtil();
        DBMgr db = new DBMgr(); 
        db.setCon(info.getCon());
        util.setDb(db);
        util.setInfo(info);
        db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
        util.setLogApplNm(info.getLoApplNm());
   
      res.setContentType("text/xml"); 
      res.setHeader("Cache-Control", "no-cache"); 
      ArrayList ary = new ArrayList();
      StringBuffer sb = new StringBuffer();
      String orderId = req.getParameter("orderId");
      String itemIdnstr = "with pending_v as(\n" + 
      "           select  a.trns_idn,b.trns_item_idn, b.qty,b.weight,b.qty-c.qty rcQty,b.weight-c.weight rcWeight\n" + 
      "           from trns_m a,trns_item b,(\n" + 
      "           select trns_idn,trns_item_idn,sum(qty) qty,sum(weight) weight from (\n" + 
      "           select a.trns_idn,b.trns_item_idn,nvl(qty,0) qty ,nvl(weight,0)*b.fctr weight\n" + 
      "           from\n" + 
      "           trns_m a,trns_item b\n" + 
      "           where  \n" + 
      "           a.trns_idn=b.trns_idn  and b.ref_trns_item_idn is null\n" + 
      "           union\n" + 
      "           select a.trns_idn,b.ref_trns_item_idn,sum(nvl(qty,0)*b.fctr) qty ,sum(nvl(weight,0)*b.fctr) weight\n" + 
      "           from\n" + 
      "           trns_m a,trns_item b\n" + 
      "           where  \n" + 
      "           a.trns_idn=b.trns_idn and b.ref_trns_item_idn is not null\n" + 
      "           group by a.trns_idn,b.ref_trns_item_idn\n" + 
      "           ) group by trns_idn,trns_item_idn\n" + 
      "           ) c\n" + 
      "           where c.weight > 0 and\n" + 
      "           a.trns_idn=b.trns_idn and b.trns_idn=c.trns_idn and b.trns_item_idn=c.trns_item_idn \n" + 
      "           )\n" + 
      "           select distinct  a.trns_item_idn , b.txt\n" + 
      "           from pending_v a,trns_item_attr b where a.trns_item_idn=b.trns_item_idn and mprp='DTLS'\n" + 
      "              and a.trns_idn=? ";
      ary = new ArrayList();
      ary.add(orderId);
        ArrayList rsList = db.execSqlLst("sql", itemIdnstr, ary);
        PreparedStatement pst=(PreparedStatement)rsList.get(0);
        ResultSet  rs = db.execSql("mprp ", itemIdnstr , ary);
        while(rs.next()) {
           
                sb.append("<itemLst>");
                sb.append("<itemIdn>"+util.nvl(rs.getString(1).toLowerCase()) +"</itemIdn>");
                sb.append("<itemDsc>"+util.nvl(rs.getString(2)) +"</itemDsc>");
                sb.append("</itemLst>");
          
        }
        res.getWriter().write("<itemLsts>" +sb.toString()+ "</itemLsts>");
     rs.close();
     pst.close();
     
     return null;
    }
    
    public OrderDeliveryAction() {
        super();
    }
    
    public String init(HttpServletRequest req , HttpServletResponse res,HttpSession session,DBUtil util) {
            String rtnPg="sucess";
            String invalide="";
            String connExists=util.nvl(util.getConnExists());  
            if(!connExists.equals("N"))
            invalide=util.nvl(util.chkTimeOut(),"N");
            if(session.isNew())
            rtnPg="sessionTO";    
            if(connExists.equals("N"))
            rtnPg="connExists";     
            if(invalide.equals("Y"))
            rtnPg="chktimeout";
            if(rtnPg.equals("sucess")){
            boolean sout=util.getLoginsession(req,res,session.getId());
            if(!sout){
            rtnPg="sessionTO";
            System.out.print("New Session Id :="+session.getId());
            }else{
            util.updAccessLog(req,res,"Price Calc", "init");
            }
            }
            return rtnPg;
            }
}
