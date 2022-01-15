package ft.com.mixakt;

import ft.com.DBMgr;
import ft.com.DBUtil;
import ft.com.GtMgr;
import ft.com.InfoMgr;

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

public class MixTransactionReport extends DispatchAction {
    public MixTransactionReport() {
        super();
    }
    
    public ActionForward load(ActionMapping am, ActionForm af, HttpServletRequest req,
                              HttpServletResponse res) throws Exception {
      HttpSession session = req.getSession(false);
      InfoMgr info = (InfoMgr)session.getAttribute("info");
      GtMgr gtMgr = (GtMgr)session.getAttribute("gtMgr");
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
          util.updAccessLog(req,res,"Mix Transaction", "load ");
          MixTransactionForm mixForm = (MixTransactionForm)af;
          String boxid = util.nvl((String)mixForm.getValue("boxid"));
          String vnm = util.nvl((String)mixForm.getValue("vnm"));
          if(vnm.equals(""))
              vnm = util.nvl(req.getParameter("vnm"));
          String dtefr = util.nvl((String)mixForm.getValue("dtefr"));
          String dteto = util.nvl((String)mixForm.getValue("dteto"));
          String srchtyp = util.nvl(req.getParameter("TYP"));
          String TRANSTYP =  util.nvl((String)mixForm.getValue("TRANSTYP"));
          String dteFtr="";
          
          HashMap allPageDtl= (info.getPageDetails() == null)?new HashMap():(HashMap)info.getPageDetails();
          HashMap pageDtl=(HashMap)allPageDtl.get("MIX_TRANS");
          if(pageDtl==null || pageDtl.size()==0){
          pageDtl=new HashMap();
          pageDtl=util.pagedef("MIX_TRANS");
          allPageDtl.put("MIX_TRANS",pageDtl);
          }
          info.setPageDetails(allPageDtl);
          
          
          if(!dtefr.equals("") && !dteto.equals("")){
              dteFtr = " and trunc(log_dte) between to_date('"+dtefr+"' , 'dd-mm-yyyy') and to_date('"+dteto+"' , 'dd-mm-yyyy') ";
          }
          if(!TRANSTYP.equals("")){
              dteFtr = dteFtr +" and typ ='"+TRANSTYP+"' ";
          }
          String logDtl="";
          if(!vnm.equals("")){
          logDtl= "with pkt_Idn as  (select m.idn, m.vnm boxId, m.cts from mstk m  \n" + 
          " where  m.vnm =  '"+vnm+"'  )"+
          "select l.log_dte, to_char(l.log_dte, 'dd-Mon-rrrr HH24:mi') dt_tm \n" + 
          ", decode(typ, 'Stock Tally', '*', decode(l.frm_idn, p.idn, '-', '+')) trns_typ\n" + 
          ", l.typ, l.rmk, l.qty, l.cts, l.upr\n" + 
          ", decode(l.frm_idn, p.idn, p.boxId, '-') frmBox\n" + 
          ", decode(l.to_idn, p.idn, p.boxId, '-') toBox\n" + 
          "  from mix_trf_log l, pkt_idn p\n" + 
          "where (nvl(l.frm_idn, 0) = 0 or nvl(l.to_idn, 0) = 0)\n" + 
          "  and (l.frm_idn = p.idn or l.to_idn = p.idn) " +dteFtr+ 
          "UNION\n" + 
         "select l.log_dte, to_char(l.log_dte, 'dd-Mon-rrrr HH24:mi') dt_tm \n" + 
        ", decode(typ, 'Stock Tally', '*', decode(l.frm_idn, p.idn, '-', '+')) trns_typ\n" + 
        ", l.typ, l.rmk, l.qty, l.cts, l.upr\n" + 
        ", decode(l.frm_idn, p.idn, p.boxId, sd.vnm) frmBox\n" + 
        ", decode(l.to_idn, p.idn, p.boxId, sd.vnm) toBox\n" + 
        "  from mix_trf_log l, pkt_idn p, mstk sd\n" + 
        "where nvl(l.frm_idn, 0) <> 0 and nvl(l.to_idn, 0) <> 0\n" + 
        "  and l.frm_idn = p.idn\n" + 
         "  and nvl(l.to_idn, 0) = sd.idn "
           +dteFtr+ 
          "UNION\n" + 
          "select l.log_dte, to_char(l.log_dte, 'dd-Mon-rrrr HH24:mi') dt_tm \n" + 
          ", decode(typ, 'Stock Tally', '*', decode(l.frm_idn, p.idn, '-', '+')) trns_typ\n" + 
          ", l.typ, l.rmk, l.qty, l.cts, l.upr\n" + 
          ", decode(l.frm_idn, p.idn, p.boxId, sd.vnm) frmBox\n" + 
          ", decode(l.to_idn, p.idn, p.boxId, sd.vnm) toBox\n" + 
          "  from mix_trf_log l, pkt_idn p, mstk sd\n" + 
          "where nvl(l.frm_idn, 0) <> 0 and nvl(l.to_idn, 0) <> 0\n" + 
          "  and l.to_idn = p.idn\n" + 
          "  and nvl(l.frm_idn, 0) = sd.idn"+
          "  " +dteFtr+ 
          "order by 1 desc";
          }
          
          if(!boxid.equals("")){
                logDtl= "with pkt_Idn as  (select idn, sd.val boxId, m.cts from mstk m, stk_dtl sd " + 
              " where \n" + 
              " m.idn = SD.mstk_idn and SD.grp=1 and SD.mprp = '"+srchtyp+"'  and  (SD.Val ='"+boxid+"' or sd.prt1='"+boxid+"'))"+
              " select l.log_dte, to_char(l.log_dte, 'dd-Mon-rrrr HH24:mi') dt_tm \n" + 
              ", decode(typ, 'Stock Tally', '*', decode(l.frm_idn, p.idn, '-', '+')) trns_typ\n" + 
              ", l.typ, l.rmk, l.qty, l.cts, l.upr\n" + 
              ", decode(l.frm_idn, p.idn, p.boxId, '-') frmBox\n" + 
              ", decode(l.to_idn, p.idn, p.boxId, '-') toBox\n" + 
              "  from mix_trf_log l, pkt_idn p\n" + 
              "where (nvl(l.frm_idn, 0) = 0 or nvl(l.to_idn, 0) = 0)\n" + 
              "  and (l.frm_idn = p.idn or l.to_idn = p.idn) " +dteFtr+ 
              "UNION\n" + 
              "select l.log_dte, to_char(l.log_dte, 'dd-Mon-rrrr HH24:mi') dt_tm \n" + 
              ", decode(typ, 'Stock Tally', '*', decode(l.frm_idn, p.idn, '-', '+')) trns_typ\n" + 
              ", l.typ, l.rmk, l.qty, l.cts, l.upr\n" + 
              ", decode(l.frm_idn, p.idn, p.boxId, sd.val) frmBox\n" + 
              ", decode(l.to_idn, p.idn, p.boxId, sd.val) toBox\n" + 
              "  from mix_trf_log l, pkt_idn p, stk_dtl sd\n" + 
              "where nvl(l.frm_idn, 0) <> 0 and nvl(l.to_idn, 0) <> 0\n" + 
              "  and l.frm_idn = p.idn\n" + 
              "  and nvl(l.to_idn, 0) = sd.mstk_idn and sd.grp = 1 and sd.mprp = '"+srchtyp+"' " +dteFtr+ 
              "UNION\n" + 
              "select l.log_dte, to_char(l.log_dte, 'dd-Mon-rrrr HH24:mi') dt_tm \n" + 
              ", decode(typ, 'Stock Tally', '*', decode(l.frm_idn, p.idn, '-', '+')) trns_typ\n" + 
              ", l.typ, l.rmk, l.qty, l.cts, l.upr\n" + 
              ", decode(l.frm_idn, p.idn, p.boxId, sd.val) frmBox\n" + 
              ", decode(l.to_idn, p.idn, p.boxId, sd.val) toBox\n" + 
              "  from mix_trf_log l, pkt_idn p, stk_dtl sd\n" + 
              "where nvl(l.frm_idn, 0) <> 0 and nvl(l.to_idn, 0) <> 0\n" + 
              "  and l.to_idn = p.idn\n" + 
              "  and nvl(l.frm_idn, 0) = sd.mstk_idn and sd.grp = 1 and sd.mprp = '"+srchtyp+"' " +dteFtr+ 
              "order by 1 desc";
               }
         
          
           ArrayList pktDtlList = new ArrayList();
          if(!boxid.equals("") || !vnm.equals("")){
              ArrayList params = new ArrayList();
              ArrayList outLst = db.execSqlLst("prp List", logDtl, params);
              PreparedStatement pst = (PreparedStatement)outLst.get(0);
              ResultSet rs = (ResultSet)outLst.get(1);
              while(rs.next()){
                  HashMap pktDtl = new HashMap();
                  String ctsVal = util.nvl(rs.getString("cts"));
                  String typ = util.nvl(rs.getString("Typ")).toUpperCase();
                  String trns_typ = util.nvl(rs.getString("trns_typ"));
                  if(typ.indexOf("RETURN")!=-1 || typ.indexOf("NEW")!=-1 || typ.indexOf("PURCHASED")!=-1 ){
                      ctsVal="+"+ctsVal;
                  }else{
                      ctsVal=trns_typ+""+ctsVal;
                  }
                
                  
                    pktDtl.put("TYP", util.nvl(rs.getString("typ")));
                    pktDtl.put("RMK", util.nvl(rs.getString("rmk")));
                    pktDtl.put("QTY", util.nvl(rs.getString("qty")));
                    pktDtl.put("UPR", util.nvl(rs.getString("upr")));
                    pktDtl.put("CTS", ctsVal);
                    pktDtl.put("DTE", util.nvl(rs.getString("dt_tm")));
                    pktDtl.put("FRMBOX", util.nvl(rs.getString("frmBox")));
                    pktDtl.put("TOBOX", util.nvl(rs.getString("toBox")));

                    pktDtlList.add(pktDtl);
                }
              rs.close();
              pst.close();
           
          }
         req.setAttribute("PKTDTL", pktDtlList);
          req.setAttribute("view", "yes");
          if(!vnm.equals(""))
          return am.findForward("loadSJE"); 
          else
          return am.findForward("load"); 
      }
      }
    
    public ActionForward loadBOXTYP(ActionMapping am, ActionForm af, HttpServletRequest req,
                              HttpServletResponse res) throws Exception {
      HttpSession session = req.getSession(false);
      InfoMgr info = (InfoMgr)session.getAttribute("info");
      GtMgr gtMgr = (GtMgr)session.getAttribute("gtMgr");
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
          util.updAccessLog(req,res,"Mix Transaction", "load ");
          MixTransactionForm mixForm = (MixTransactionForm)af;
          String boxid = util.nvl((String)mixForm.getValue("boxid"));
          String dtefr = util.nvl((String)mixForm.getValue("dtefr"));
          String dteto = util.nvl((String)mixForm.getValue("dteto"));
          String dteFtr="";
          if(!dtefr.equals("") && !dteto.equals("")){
              dteFtr = " and trunc(log_dte) between to_date('"+dtefr+"' , 'dd-mm-yyyy') and to_date('"+dteto+"' , 'dd-mm-yyyy') ";
          }
          ArrayList pktDtlList = new ArrayList();
          if(!boxid.equals("")){
             
              String logDtl="with pkt_Idn as  (select idn, sd.val boxId, m.cts from mstk m, stk_dtl sd \n" + 
              " where m.stt in ('MKAV','MKSL') and\n" + 
              "   m.idn = SD.mstk_idn and SD.grp=1 and SD.mprp = 'BOX_TYP'  and  (SD.Val ='"+boxid+"' or sd.prt1='"+boxid+"'))"+
              "select l.log_dte, to_char(l.log_dte, 'dd-Mon-rrrr HH24:mi') dt_tm \n" + 
              ", decode(typ, 'Stock Tally', '*', decode(l.frm_idn, p.idn, '-', '+')) trns_typ\n" + 
              ", l.typ, l.rmk, l.qty, l.cts, l.upr\n" + 
              ", decode(l.frm_idn, p.idn, p.boxId, '-') frmBox\n" + 
              ", decode(l.to_idn, p.idn, p.boxId, '-') toBox\n" + 
              "  from mix_trf_log l, pkt_idn p\n" + 
              "where (nvl(l.frm_idn, 0) = 0 or nvl(l.to_idn, 0) = 0)\n" + 
              "  and (l.frm_idn = p.idn or l.to_idn = p.idn) " +dteFtr+ 
              "UNION\n" + 
              "select l.log_dte, to_char(l.log_dte, 'dd-Mon-rrrr HH24:mi') dt_tm \n" + 
              ", decode(typ, 'Stock Tally', '*', decode(l.frm_idn, p.idn, '-', '+')) trns_typ\n" + 
              ", l.typ, l.rmk, l.qty, l.cts, l.upr\n" + 
              ", decode(l.frm_idn, p.idn, p.boxId, sd.val) frmBox\n" + 
              ", decode(l.to_idn, p.idn, p.boxId, sd.val) toBox\n" + 
              "  from mix_trf_log l, pkt_idn p, stk_dtl sd\n" + 
              "where nvl(l.frm_idn, 0) <> 0 and nvl(l.to_idn, 0) <> 0\n" + 
              "  and l.frm_idn = p.idn\n" + 
              "  and nvl(l.to_idn, 0) = sd.mstk_idn and sd.grp = 1 and sd.mprp = 'BOX_TYP' " +dteFtr+ 
              "UNION\n" + 
              "select l.log_dte, to_char(l.log_dte, 'dd-Mon-rrrr HH24:mi') dt_tm \n" + 
              ", decode(typ, 'Stock Tally', '*', decode(l.frm_idn, p.idn, '-', '+')) trns_typ\n" + 
              ", l.typ, l.rmk, l.qty, l.cts, l.upr\n" + 
              ", decode(l.frm_idn, p.idn, p.boxId, sd.val) frmBox\n" + 
              ", decode(l.to_idn, p.idn, p.boxId, sd.val) toBox\n" + 
              "  from mix_trf_log l, pkt_idn p, stk_dtl sd\n" + 
              "where nvl(l.frm_idn, 0) <> 0 and nvl(l.to_idn, 0) <> 0\n" + 
              "  and l.to_idn = p.idn\n" + 
              "  and nvl(l.frm_idn, 0) = sd.mstk_idn and sd.grp = 1 and sd.mprp = 'BOX_TYP' " +dteFtr+ 
              "order by 1 desc";
             ArrayList params = new ArrayList();
             
              ArrayList outLst = db.execSqlLst("prp List", logDtl, params);
              PreparedStatement pst = (PreparedStatement)outLst.get(0);
              ResultSet rs = (ResultSet)outLst.get(1);
              while(rs.next()){
                  HashMap pktDtl = new HashMap();
                  String ctsVal = util.nvl(rs.getString("cts"));
                  String typ = util.nvl(rs.getString("Typ")).toUpperCase();
                  String trns_typ = util.nvl(rs.getString("trns_typ"));
                  if(typ.indexOf("RETURN")!=-1 || typ.indexOf("NEW")!=-1 || typ.indexOf("PURCHASED")!=-1 ){
                      ctsVal="+"+ctsVal;
                  }else{
                      ctsVal=trns_typ+""+ctsVal;
                  }
                
                  
                    pktDtl.put("TYP", util.nvl(rs.getString("typ")));
                    pktDtl.put("RMK", util.nvl(rs.getString("rmk")));
                    pktDtl.put("QTY", util.nvl(rs.getString("qty")));
                    pktDtl.put("UPR", util.nvl(rs.getString("upr")));
                    pktDtl.put("CTS", ctsVal);
                    pktDtl.put("DTE", util.nvl(rs.getString("dt_tm")));
                    pktDtl.put("FRMBOX", util.nvl(rs.getString("frmBox")));
                    pktDtl.put("TOBOX", util.nvl(rs.getString("toBox")));

                    pktDtlList.add(pktDtl);
                }
              rs.close();
              pst.close();
          }
         req.setAttribute("PKTDTL", pktDtlList);
          req.setAttribute("view", "yes");
          return am.findForward("load"); 
      }
      }
    
    
    public ActionForward loadVNM(ActionMapping am, ActionForm af, HttpServletRequest req,
                              HttpServletResponse res) throws Exception {
      HttpSession session = req.getSession(false);
      InfoMgr info = (InfoMgr)session.getAttribute("info");
      GtMgr gtMgr = (GtMgr)session.getAttribute("gtMgr");
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
          util.updAccessLog(req,res,"Mix Transaction", "load ");
          MixTransactionForm mixForm = (MixTransactionForm)af;
          String vnm = util.nvl((String)mixForm.getValue("vnm"));
          String dtefr = util.nvl((String)mixForm.getValue("dtefr"));
          String dteto = util.nvl((String)mixForm.getValue("dteto"));
          String srchtyp = util.nvl(req.getParameter("TYP"));
          String TRANSTYP =  util.nvl((String)mixForm.getValue("TRANSTYP"));
          String dteFtr="";
          
          HashMap allPageDtl= (info.getPageDetails() == null)?new HashMap():(HashMap)info.getPageDetails();
          HashMap pageDtl=(HashMap)allPageDtl.get("MIX_TRANS");
          if(pageDtl==null || pageDtl.size()==0){
          pageDtl=new HashMap();
          pageDtl=util.pagedef("MIX_TRANS");
          allPageDtl.put("MIX_TRANS",pageDtl);
          }
          info.setPageDetails(allPageDtl);
          
          
          if(!dtefr.equals("") && !dteto.equals("")){
              dteFtr = " and trunc(log_dte) between to_date('"+dtefr+"' , 'dd-mm-yyyy') and to_date('"+dteto+"' , 'dd-mm-yyyy') ";
          }
          if(!TRANSTYP.equals("")){
              dteFtr = dteFtr +" and typ ='"+TRANSTYP+"' ";
          }
           ArrayList pktDtlList = new ArrayList();
          if(!vnm.equals("") ){
             
              String logDtl="with pkt_Idn as  (select m.idn, m.vnm boxId, m.cts from mstk m \n" + 
              " where \n" + 
              "   m.vnm =  ? )"+
              "select l.log_dte, to_char(l.log_dte, 'dd-Mon-rrrr HH24:mi') dt_tm \n" + 
              ", decode(typ, 'Stock Tally', '*', decode(l.frm_idn, p.idn, '-', '+')) trns_typ\n" + 
              ", l.typ, l.rmk, l.qty, l.cts, l.upr\n" + 
              ", decode(l.frm_idn, p.idn, p.boxId, '-') frmBox\n" + 
              ", decode(l.to_idn, p.idn, p.boxId, '-') toBox\n" + 
              "  from mix_trf_log l, pkt_idn p\n" + 
              "where (nvl(l.frm_idn, 0) = 0 or nvl(l.to_idn, 0) = 0)\n" + 
              "  and (l.frm_idn = p.idn or l.to_idn = p.idn) " +dteFtr+ 
              "UNION\n" + 
              "select l.log_dte, to_char(l.log_dte, 'dd-Mon-rrrr HH24:mi') dt_tm \n" + 
              ", decode(typ, 'Stock Tally', '*', decode(l.frm_idn, p.idn, '-', '+')) trns_typ\n" + 
              ", l.typ, l.rmk, l.qty, l.cts, l.upr\n" + 
              ", decode(l.frm_idn, p.idn, p.boxId, sd.val) frmBox\n" + 
              ", decode(l.to_idn, p.idn, p.boxId, sd.val) toBox\n" + 
              "  from mix_trf_log l, pkt_idn p, stk_dtl sd\n" + 
              "where nvl(l.frm_idn, 0) <> 0 and nvl(l.to_idn, 0) <> 0\n" + 
              "  and l.frm_idn = p.idn\n" + 
              "  and nvl(l.to_idn, 0) = sd.mstk_idn and sd.grp = 1 and sd.mprp = '"+srchtyp+"' " +dteFtr+ 
              "UNION\n" + 
              "select l.log_dte, to_char(l.log_dte, 'dd-Mon-rrrr HH24:mi') dt_tm \n" + 
              ", decode(typ, 'Stock Tally', '*', decode(l.frm_idn, p.idn, '-', '+')) trns_typ\n" + 
              ", l.typ, l.rmk, l.qty, l.cts, l.upr\n" + 
              ", decode(l.frm_idn, p.idn, p.boxId, sd.val) frmBox\n" + 
              ", decode(l.to_idn, p.idn, p.boxId, sd.val) toBox\n" + 
              "  from mix_trf_log l, pkt_idn p, stk_dtl sd\n" + 
              "where nvl(l.frm_idn, 0) <> 0 and nvl(l.to_idn, 0) <> 0\n" + 
              "  and l.to_idn = p.idn\n" + 
              "  and nvl(l.frm_idn, 0) = sd.mstk_idn and sd.grp = 1 and sd.mprp = '"+srchtyp+"' " +dteFtr+ 
              "order by 1 desc";
              System.out.print(logDtl);
             ArrayList params = new ArrayList();
             
              ArrayList outLst = db.execSqlLst("prp List", logDtl, params);
              PreparedStatement pst = (PreparedStatement)outLst.get(0);
              ResultSet rs = (ResultSet)outLst.get(1);
              while(rs.next()){
                  HashMap pktDtl = new HashMap();
                  String ctsVal = util.nvl(rs.getString("cts"));
                  String typ = util.nvl(rs.getString("Typ")).toUpperCase();
                  String trns_typ = util.nvl(rs.getString("trns_typ"));
                  if(typ.indexOf("RETURN")!=-1 || typ.indexOf("NEW")!=-1 || typ.indexOf("PURCHASED")!=-1 ){
                      ctsVal="+"+ctsVal;
                  }else{
                      ctsVal=trns_typ+""+ctsVal;
                  }
                
                  
                    pktDtl.put("TYP", util.nvl(rs.getString("typ")));
                    pktDtl.put("RMK", util.nvl(rs.getString("rmk")));
                    pktDtl.put("QTY", util.nvl(rs.getString("qty")));
                    pktDtl.put("UPR", util.nvl(rs.getString("upr")));
                    pktDtl.put("CTS", ctsVal);
                    pktDtl.put("DTE", util.nvl(rs.getString("dt_tm")));
                    pktDtl.put("FRMBOX", util.nvl(rs.getString("frmBox")));
                    pktDtl.put("TOBOX", util.nvl(rs.getString("toBox")));

                    pktDtlList.add(pktDtl);
                }
              rs.close();
              pst.close();
          }
         req.setAttribute("PKTDTL", pktDtlList);
          req.setAttribute("view", "yes");
          return am.findForward("load"); 
      }
      }
    
    public ArrayList DataCollection (HttpServletRequest req , HttpServletResponse res,HashMap params){
        HttpSession session = req.getSession(false);
        InfoMgr info = (InfoMgr)session.getAttribute("info");
        DBUtil util = new DBUtil();
        DBMgr db = new DBMgr();
        ArrayList pktList = new ArrayList();
        if(info!=null){
        db.setCon(info.getCon());
        util.setDb(db);
        util.setInfo(info);
        db.setLogApplNm(info.getLoApplNm()); db.setLogid(String.valueOf(info.getLogId()));
        util.setLogApplNm(info.getLoApplNm());
         init(req,res,session,util);
        String dtefrm = util.nvl((String)params.get("DTEFRM"));
        String dteTo = util.nvl((String)params.get("DTETO"));
        String idn = util.nvl((String)params.get("IDN"));
        String dteQ=null;
        if(!dtefrm.equals("") && !dteTo.equals("")){
            dteQ=" and trunc(a.log_dte)  between "+dtefrm+" and "+dteTo;  
        }
        if(!idn.equals("")){
            
        }
        
        
        }
        return pktList;
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
                rtnPg=util.checkUserPageRights("",util.getFullURL(req));
                if(rtnPg.equals("unauthorized"))
                util.updAccessLog(req,res,"Mix Assort Rtn", "Unauthorized Access");
                else
            util.updAccessLog(req,res,"Mix Transaction", "init");
            }
            }
            return rtnPg;
            }
}
