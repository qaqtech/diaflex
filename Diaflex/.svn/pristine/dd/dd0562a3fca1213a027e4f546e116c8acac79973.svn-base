package ft.com.order;

import ft.com.DBMgr;
import ft.com.DBUtil;
import ft.com.InfoMgr;

import ft.com.generic.GenericImpl;
import ft.com.generic.GenericInterface;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.ArrayList;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

public class OpenOrderAction extends DispatchAction {
    public OpenOrderAction() {
        super();
    }
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
          util.updAccessLog(req,res,"Open Order", "load start");
       OpenOrderForm udf=(OpenOrderForm)af;
       getOpenOrderData(req,res,udf);
       udf.resetAll();
          util.updAccessLog(req,res,"Open Order", "load start");
       return am.findForward("load"); 
      }
    }
    
    public void getOpenOrderData(HttpServletRequest req,HttpServletResponse res,OpenOrderForm udf)throws Exception {
        HttpSession session = req.getSession(false);
        InfoMgr info = (InfoMgr)session.getAttribute("info");
        DBUtil util = new DBUtil();
        DBMgr db = new DBMgr();
        db.setCon(info.getCon());
        util.setDb(db);
        util.setInfo(info);
        db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
        util.setLogApplNm(info.getLoApplNm());
        ArrayList pktDtlList = new ArrayList();
        GenericInterface genericInt = new GenericImpl();
        String frmtrns_dt = util.nvl((String)udf.getValue("frmtrns_dt"));
        String totrns_dt = util.nvl((String)udf.getValue("totrns_dt"));
        String frmtrns_due_dt = util.nvl((String)udf.getValue("frmtrns_due_dt"));
        String totrns_due_dt = util.nvl((String)udf.getValue("totrns_due_dt"));
        String frmest_cmp_dt = util.nvl((String)udf.getValue("frmest_cmp_dt"));
        String toest_cmp_dt = util.nvl((String)udf.getValue("toest_cmp_dt"));
        String groupby = util.nvl((String)udf.getValue("groupby"),"TRNS_IDN");
        String openClose = util.nvl((String)udf.getValue("openClose"),"OPEN");
        String conQdte="";
        String conOpenCloseQ="";
        ArrayList itemHdr = new ArrayList();
        if(!frmtrns_dt.equals("") && !totrns_dt.equals(""))
        conQdte += " and trunc(a.trns_dt) between to_date('"+frmtrns_dt+"' , 'dd-mm-yyyy') and to_date('"+totrns_dt+"' , 'dd-mm-yyyy') ";
        
        if(!frmtrns_due_dt.equals("") && !totrns_due_dt.equals(""))
        conQdte += " and trunc(a.trns_due_dt) between to_date('"+frmtrns_due_dt+"' , 'dd-mm-yyyy') and to_date('"+totrns_due_dt+"' , 'dd-mm-yyyy') ";
        
        if(!frmest_cmp_dt.equals("") && !toest_cmp_dt.equals(""))
        conQdte += " and trunc(a.est_cmp_dt) between to_date('"+frmest_cmp_dt+"' , 'dd-mm-yyyy') and to_date('"+toest_cmp_dt+"' , 'dd-mm-yyyy') ";
        
        if(openClose.equals("OPEN")){
            conOpenCloseQ=" c.weight > 0 ";
        }else{
            conOpenCloseQ=" c.weight <= 0 ";
        }
        if(groupby.equals("TRNS_IDN")){
        String sql="            with pending_v as(\n" + 
        "            select  a.trns_idn,b.trns_item_idn, b.qty,b.weight,b.qty-c.qty rcQty,b.weight-c.weight rcWeight,a.est_cmp_dt,a.trns_dt,a.trns_due_dt\n" + 
        "            from trns_m a,trns_item b,(\n" + 
        "            select trns_idn,trns_item_idn,sum(qty) qty,sum(weight) weight from (\n" + 
        "            select a.trns_idn,b.trns_item_idn,nvl(qty,0) qty ,nvl(weight,0)*b.fctr weight\n" + 
        "            from\n" + 
        "            trns_m a,trns_item b\n" + 
        "            where  \n" + 
        "            a.trns_idn=b.trns_idn  and b.ref_trns_item_idn is null\n" + conQdte+
        "            union\n" + 
        "            select a.trns_idn,b.ref_trns_item_idn,sum(nvl(qty,0)*b.fctr) qty ,sum(nvl(weight,0)*b.fctr) weight\n" + 
        "            from\n" + 
        "            trns_m a,trns_item b\n" + 
        "            where  \n" + 
        "            a.trns_idn=b.trns_idn and b.ref_trns_item_idn is not null\n" + conQdte+
        "            group by a.trns_idn,b.ref_trns_item_idn\n" + 
        "            ) group by trns_idn,trns_item_idn\n" + 
        "            ) c\n" + 
        "            where "+conOpenCloseQ+" and\n" + 
        "            a.trns_idn=b.trns_idn and b.trns_idn=c.trns_idn and b.trns_item_idn=c.trns_item_idn \n" + 
        "            )\n" + 
        "            select a.trns_idn,a.trns_item_idn,a.qty,a.weight,a.rcQty,a.rcWeight,a.qty-a.rcQty pcQty,a.weight-a.rcWeight pcWeight,b.txt dtls ,to_char(a.est_cmp_dt, 'DD-MM-YYYY') est_cmp_dt\n" + 
        "            ,to_char(a.trns_dt, 'DD-MM-YYYY') trns_dt,to_char(a.trns_due_dt, 'DD-MM-YYYY') trns_due_dt\n" + 
        "            from pending_v a,trns_item_attr b where a.trns_item_idn=b.trns_item_idn and mprp='DTLS'\n" + 
        "            order by a.trns_idn desc";
        ArrayList rsList = db.execSqlLst("sql", sql, new ArrayList());
        PreparedStatement pst=(PreparedStatement)rsList.get(0);
        ResultSet rs = (ResultSet)rsList.get(1);
        while(rs.next()) {
            HashMap pktDtl = new HashMap();
            pktDtl.put("ORDER IDN", util.nvl(rs.getString("trns_idn")));
            pktDtl.put("ORDER ITEM IDN", util.nvl(rs.getString("trns_item_idn")));
            pktDtl.put("QTY", util.nvl(rs.getString("qty")));
            pktDtl.put("CRTWT", util.nvl(rs.getString("weight")));
            pktDtl.put("RECIVE.QTY", util.nvl(rs.getString("rcQty")));
            pktDtl.put("RECIVE.CRTWT", util.nvl(rs.getString("rcWeight")));
            pktDtl.put("PENDING.QTY", util.nvl(rs.getString("pcQty")));
            pktDtl.put("PENDING.CRTWT", util.nvl(rs.getString("pcWeight")));
            pktDtl.put("DTLS", util.nvl(rs.getString("dtls")));
            pktDtl.put("COMPLETE DATE", util.nvl(rs.getString("est_cmp_dt")));
            pktDtl.put("ORDER DATE", util.nvl(rs.getString("trns_dt")));
            pktDtl.put("ORDER DUE DATE", util.nvl(rs.getString("trns_due_dt")));
            pktDtlList.add(pktDtl);
        }
        rs.close();
        pst.close();
        itemHdr = new ArrayList();
        itemHdr.add("ORDER IDN");
        itemHdr.add("ORDER DATE");
        itemHdr.add("ORDER DUE DATE");
        itemHdr.add("COMPLETE DATE");
        itemHdr.add("QTY");
        itemHdr.add("CRTWT");
        itemHdr.add("RECIVE.QTY");
        itemHdr.add("RECIVE.CRTWT");
        itemHdr.add("PENDING.QTY");
        itemHdr.add("PENDING.CRTWT");
        itemHdr.add("DTLS");
        }else{
            String sql="            with pending_v as(\n" + 
            "            select  a.trns_idn,b.trns_item_idn, b.qty,b.weight,b.qty-c.qty rcQty,b.weight-c.weight rcWeight,a.est_cmp_dt,a.trns_dt,a.trns_due_dt\n" + 
            "            from trns_m a,trns_item b,(\n" + 
            "            select trns_idn,trns_item_idn,sum(qty) qty,sum(weight) weight from (\n" + 
            "            select a.trns_idn,b.trns_item_idn,nvl(qty,0) qty ,nvl(weight,0)*b.fctr weight\n" + 
            "            from\n" + 
            "            trns_m a,trns_item b\n" + 
            "            where  \n" + 
            "            a.trns_idn=b.trns_idn  and b.ref_trns_item_idn is null\n" + conQdte+ 
            "            union\n" + 
            "            select a.trns_idn,b.ref_trns_item_idn,sum(nvl(qty,0)*b.fctr) qty ,sum(nvl(weight,0)*b.fctr) weight\n" + 
            "            from\n" + 
            "            trns_m a,trns_item b\n" + 
            "            where  \n" + 
            "            a.trns_idn=b.trns_idn and b.ref_trns_item_idn is not null\n" + conQdte+
            "            group by a.trns_idn,b.ref_trns_item_idn\n" + 
            "            ) group by trns_idn,trns_item_idn\n" + 
            "            ) c\n" + 
            "            where "+conOpenCloseQ+" and\n" + 
            "            a.trns_idn=b.trns_idn and b.trns_idn=c.trns_idn and b.trns_item_idn=c.trns_item_idn \n" + 
            "            )\n" + 
            "            select b.txt dtls,sum(a.qty) qty,sum(a.weight) weight,sum(a.rcQty) rcQty,sum(a.rcWeight) rcWeight,sum(a.qty)-sum(a.rcQty) pcQty,sum(a.weight)-sum(a.rcWeight) pcWeight \n" + 
            "            from pending_v a,trns_item_attr b where a.trns_item_idn=b.trns_item_idn and mprp='DTLS'\n" + 
            "            group by b.txt\n" + 
            "            order by 1";
            ArrayList rsList = db.execSqlLst("sql", sql, new ArrayList());
            PreparedStatement pst=(PreparedStatement)rsList.get(0);
            ResultSet rs = (ResultSet)rsList.get(1);
            while(rs.next()) {
                HashMap pktDtl = new HashMap();
                pktDtl.put("QTY", util.nvl(rs.getString("qty")));
                pktDtl.put("CRTWT", util.nvl(rs.getString("weight")));
                pktDtl.put("RECIVE.QTY", util.nvl(rs.getString("rcQty")));
                pktDtl.put("RECIVE.CRTWT", util.nvl(rs.getString("rcWeight")));
                pktDtl.put("PENDING.QTY", util.nvl(rs.getString("pcQty")));
                pktDtl.put("PENDING.CRTWT", util.nvl(rs.getString("pcWeight")));
                pktDtl.put("DTLS", util.nvl(rs.getString("dtls")));
                pktDtlList.add(pktDtl);
            }
            rs.close();
            pst.close();
            itemHdr = new ArrayList();
            itemHdr.add("DTLS");
            itemHdr.add("QTY");
            itemHdr.add("CRTWT");
            itemHdr.add("RECIVE.QTY");
            itemHdr.add("RECIVE.CRTWT");
            itemHdr.add("PENDING.QTY");
            itemHdr.add("PENDING.CRTWT");
        }
        
        int pktDtlListsz=pktDtlList.size();
        if(pktDtlListsz > 0){
            req.setAttribute("view", "Y");
        }
        session.setAttribute("openorderList", pktDtlList);
        session.setAttribute("openitemHdr",itemHdr);
        req.setAttribute("openClose", openClose);
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
            util.updAccessLog(req,res,"Open Order", "init");
            }
            }
            return rtnPg;
            }
}
